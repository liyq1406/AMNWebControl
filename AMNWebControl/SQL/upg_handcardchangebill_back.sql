alter procedure upg_handcardchangebill_back  
@compid   varchar(10), --兑换公司        
@billid   varchar(20)  --兑换单号     
as        
begin   
 declare @changetype  int    --转卡类型  
 declare @oldcardno  varchar(20)  --转卡老卡号  
 declare @newcardno  varchar(20)  --转卡新卡号  
 declare @rechargeamt float   --充值金额  
 declare @detamt   float   --欠款金额  
 declare @oldkeepamt  float   --原金额  
 declare @olddetamt  float   --原欠款金额  
  --获取单据信息  
  select @changetype=changetype,@oldcardno=changebeforcardno,  
         @oldkeepamt=curaccountkeepamt,@olddetamt=curaccountdebtamt,  
         @rechargeamt=changefillamt,@detamt=changdebtamt,@newcardno=changeaftercardno  
  from mcardchangeinfo where changecompid=@compid and changebillid=@billid  
    
  if(isnull(@changetype,0)=0)  --折扣转卡  
  begin  
    --将会员信息还原到老卡  
    delete memberinfo where memberno=@oldcardno  
    update memberinfo set memberno=@oldcardno where memberno=@newcardno  
    --将原先的账户历史清除  
    delete cardaccountchangehistory where changecardno=@newcardno and changebilltype='ZK' and changecompid=@compid and changebillno=@billid   
    --再将原有的卡历史转移过去  
    update cardaccountchangehistory set changecardno=@oldcardno where changecardno=@newcardno  
    --将交易历史转移过去  
    update cardtransactionhistory set transactioncardno=@oldcardno where transactioncardno=@newcardno  
    --将异动历史转移过去  
    delete cardchangehistory where changecardno=@oldcardno  
    delete cardchangehistory where changecardno=@newcardno  
  
    --将老卡卡状态置为开卡状态  
    update cardinfo set cardstate=4 where cardno=@oldcardno  
     --将新卡卡状态置为未销售  
    update cardinfo set cardstate=1 where cardno=@newcardno  
     delete cardsoninfo where parentcardno=@newcardno  
    --将老卡账户还原  
    if not exists(select 1 from cardaccount where   cardno = @oldcardno and accounttype=2)        
    begin        
    insert into cardaccount(cardvesting,cardno,accounttype,accountbalance,accountdebts)         
    values(@compid,@oldcardno,2,ISNULL(@oldkeepamt,0),ISNULL(@olddetamt,0))        
    end      
    else  
    begin  
    update cardaccount set accountbalance=ISNULL(@oldkeepamt,0),accountdebts=ISNULL(@olddetamt,0) where cardno=@oldcardno and accounttype=2  
    end  
      
    update cardaccount set cardno= @oldcardno where  cardno=@newcardno  and ISNULL(accounttype,0)<>2  
    --将疗程账户更新到老卡  
    delete cardproaccount where cardno=@oldcardno  
    update cardproaccount set cardno=@oldcardno where cardno=@newcardno  
    --将新卡账户清0  
    update cardaccount set accountbalance=0 where cardno=@newcardno   
  end  
  else if(isnull(@changetype,0)=1)  --收购转卡  
  begin  
    --将会员信息还原到老卡  
    delete memberinfo where memberno=@oldcardno  
    update memberinfo set memberno=@oldcardno where memberno=@newcardno  
    --将原先的账户历史清除  
    delete cardaccountchangehistory where changecardno=@newcardno and changebilltype='ZK' and changecompid=@compid and changebillno=@billid   
    --再将原有的卡历史转移过去  
    update cardaccountchangehistory set changecardno=@oldcardno where changecardno=@newcardno  
    --将交易历史转移过去  
    update cardtransactionhistory set transactioncardno=@oldcardno where transactioncardno=@newcardno  
     delete cardchangehistory where changecardno=@oldcardno  
     delete cardchangehistory where changecardno=@newcardno  
    --将老卡卡状态置为开卡状态  
    update cardinfo set cardstate=4 where cardno=@oldcardno  
     --将新卡卡状态置为未销售  
    update cardinfo set cardstate=1 where cardno=@newcardno  
    delete cardsoninfo where parentcardno=@newcardno  
    --将老卡账户还原  
    if not exists(select 1 from cardaccount where   cardno = @oldcardno and accounttype=5)        
    begin        
    insert into cardaccount(cardvesting,cardno,accounttype,accountbalance,accountdebts)         
    values(@compid,@oldcardno,5,ISNULL(@oldkeepamt,0),ISNULL(@olddetamt,0))        
    end      
    else  
    begin  
    update cardaccount set accountbalance=ISNULL(@oldkeepamt,0),accountdebts=ISNULL(@olddetamt,0) where cardno=@oldcardno and accounttype=5  
    end  
    --将新卡账户清0  
    update cardaccount set accountbalance=0 where cardno=@newcardno   
  end  
  else if(isnull(@changetype,0)=2)  --竞争转卡  
  begin  
    --将原先的账户历史清除  
    delete cardaccountchangehistory where changecardno=@newcardno and changebilltype='ZK' and changecompid=@compid and changebillno=@billid   
    --将新卡卡状态置为未销售  
    update cardinfo set cardstate=1 where cardno=@newcardno  
    delete cardsoninfo where parentcardno=@newcardno  
    delete cardchangehistory where changecardno=@newcardno  
    delete cardtransactionhistory where transactioncardno=@newcardno  
    delete memberinfo where memberno=@newcardno  
    --将新卡账户清0  
    update cardaccount set accountbalance=0 where cardno=@newcardno   
  end  
  
  update mcardchangeinfo set backflag=1 where changecompid=@compid and changebillid=@billid
  declare @curdate varchar(20)  
  declare @backday datetime  
  select @backday = getdate()
  select @curdate=substring(convert(varchar(20),@backday,102),1,4) + substring(convert(varchar(20),@backday,102),6,2)+ substring(convert(varchar(20),@backday,102),9,2)
  
  insert mcardchangeinfo(changecompid,changebillid,changetype,changedate,changetime,changebeforcardno,changecardfrom,changebeforcardstate,changebeforcardtype,
    memberphone,membername,curaccountkeepamt,curaccountdebtamt,curproaccountkeepamt,curproaccountdebtamt,changelowamt,changefillamt,changdebtamt,
    changeaftercardno,changeaftercardstate,changeaftercardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
    thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
    sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
    ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,
    financedate,operationer,operationdate,backflag)  
    select changecompid,changebillid+'bak',changetype,changedate,changetime,changebeforcardno,changecardfrom,changebeforcardstate,changebeforcardtype,
    memberphone,membername,curaccountkeepamt,curaccountdebtamt,curproaccountkeepamt,curproaccountdebtamt,changelowamt,isnull(changefillamt,0)*(-1),isnull(changdebtamt,0)*(-1),
    changeaftercardno,changeaftercardstate,changeaftercardtype,firstsalerid,firstsalerinid,isnull(firstsaleamt,0)*(-1),secondsalerid,secondsalerinid,isnull(secondsaleamt,0)*(-1),
    thirdsalerid,thirdsalerinid,isnull(thirdsaleamt,0)*(-1),fourthsalerid,fourthsalerinid,isnull(fourthsaleamt,0)*(-1),fifthsalerid,fifthsalerinid,isnull(fifthsaleamt,0)*(-1),
    sixthsalerid,sixthsalerinid,isnull(sixthsaleamt,0)*(-1),seventhsalerid,seventhsalerinid,isnull(seventhsaleamt,0)*(-1),eighthsalerid,eighthsalerinid,isnull(eighthsaleamt,0)*(-1),
    ninthsalerid,ninthsalerinid,isnull(ninthsaleamt,0)*(-1),tenthsalerid,tenthsalerinid,isnull(tenthsaleamt,0)*(-1),
    @curdate,operationer,operationdate,1
    from mcardchangeinfo where changecompid=@compid and changebillid=@billid
    
    insert dpayinfo(paycompid,paybillid,paybilltype,payseqno,paymode,payamt)
    select paycompid,paybillid+'bak',paybilltype,payseqno,paymode,isnull(payamt,0)*(-1) from dpayinfo where paycompid=@compid and paybillid=@billid and paybilltype='ZK'  
   
       
end  