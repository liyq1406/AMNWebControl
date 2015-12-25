alter procedure upg_handrechargecardbill_card_back      
@compid   varchar(10), --续卡公司            
@billid   varchar(20) --续卡单号      
as            
begin       
 declare @salecardno    varchar(20)  --续卡卡号      
 declare @saledate    varchar(8)  --续卡日期      
 declare @saletime    varchar(9)  --续卡时间      
       
 declare @rechargetype   int    --续费方式( 0充值 ,6还款)        
 declare @rechargekeepamt  float   --充值金额      
 declare @rechargedebtamt  float   --欠款金额      
 declare @curcardamt    float   --异动前余额      
 declare @curcarddebtamt   float   --异动前欠款      
 declare @rechargeaccounttype varchar(10)  --充值账户      
 declare @acc4totalamt  float   --疗程总额      
 declare @carduselife float      
      
       
 select @salecardno=rechargecardno,@saledate=rechargedate,@saletime=rechargetime,@rechargetype=rechargetype,      
  @rechargekeepamt=rechargekeepamt,@rechargedebtamt=rechargedebtamt,@curcardamt=curcardamt,@curcarddebtamt=curcarddebtamt,@rechargeaccounttype=rechargeaccounttype      
 from mcardrechargeinfo with(nolock) where rechargecompid=@compid and rechargebillid=@billid      
       
 select @acc4totalamt=sum(ISNULL(saleproamt,0)) from dsalecardproinfo with(nolock) where salecompid=@compid and salebillid=@billid and salebilltype=2      
       
 --更新账户金额      
 if(@rechargetype=0)--充值      
 begin      
  update cardaccount set accountbalance=isnull(accountbalance,0)+ISNULL(@rechargekeepamt,0)*(-1),accountdebts=isnull(accountdebts,0)+ISNULL(@rechargedebtamt,0)*(-1) where cardno=@salecardno and accounttype=CAST(@rechargeaccounttype as int)      
 end      
 else --还款      
 begin      
  update cardaccount set accountbalance=isnull(accountbalance,0)+ISNULL(@rechargekeepamt,0)*(-1),accountdebts=isnull(accountdebts,0)-ISNULL(@rechargekeepamt,0)*(-1) where cardno=@salecardno and accounttype=CAST(@rechargeaccounttype as int)      
 end      
 --新增账户历史      
 declare @costaccountseqno  float      
 select @costaccountseqno=MAX(changeseqno)+1 from cardaccountchangehistory where changecardno=@salecardno       
 insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)      
 values(@compid,@salecardno,CAST(@rechargeaccounttype as int),isnull(@costaccountseqno,0),0,isnull(@rechargekeepamt,0)*(-1),'CZ',@billid,@saledate,ISNULL(@curcardamt,0)+ISNULL(@rechargekeepamt,0))      
 set @costaccountseqno=@costaccountseqno+1      
        
       
 --生成疗程信息      
  delete cardproaccount where cardno=@salecardno and createbilltype='CZ' and createbillno=@billid    
      
 if(ISNULL(@acc4totalamt,0)>0)      
 begin      
  update cardaccount set accountbalance=isnull(accountbalance,0)+isnull(@acc4totalamt,0)*(-1) where cardno=@salecardno and accounttype=4        
  declare @costaccount4lastamt float       
  select top 1 @costaccount4lastamt=(case when changetype in (0,6) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@salecardno  and changeaccounttype=4 order by chagedate desc,changeseqno desc       
  insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)      
  values(@compid,@salecardno,4,isnull(@costaccountseqno,0),0,isnull(@acc4totalamt,0)*(-1),'CZ',@billid,@saledate,isnull(@costaccount4lastamt,0))      
 end      
 
  
   
 --积分反冲  
 if exists(select 1 from sendpointcard where sourcebillid=@billid and sendcompid=@compid )  
 begin  
   insert sendpointcard(sendcompid,sendbillid,sendtype,senddate,sendempid,sourcebillid,sourcecardno,sourcedate,sourceamt,sendamt,sendpicflag,sendrateflag)  
   select sendcompid,sendbillid+'F',sendtype,senddate,sendempid,sourcebillid,sourcecardno,sourcedate,sourceamt*(-1),sendamt*(-1),sendpicflag,sendrateflag  
   from sendpointcard where sourcebillid=@billid and sendcompid=@compid  
  
   update cardaccount set accountbalance=ISNULL(accountbalance,0)-ISNULL(sendamt,0)  from cardaccount,sendpointcard,mcardrechargeinfo  
   where rechargebillid=sourcebillid and rechargecompid=sendcompid and sendtype=1  and sendpicflag=1  
   and rechargecardno=cardno and accounttype=3  
   and rechargebillid=@billid and rechargecompid=@compid  
 end  
 
  update mcardrechargeinfo set backflag=1 where rechargecompid=@compid and rechargebillid=@billid 
   
  declare @curdate varchar(20)  
  declare @backday datetime  
  select @backday = getdate()
  select @curdate=substring(convert(varchar(20),@backday,102),1,4) + substring(convert(varchar(20),@backday,102),6,2)+ substring(convert(varchar(20),@backday,102),9,2)
  insert mcardrechargeinfo(rechargecompid,rechargebillid,rechargedate,rechargetime,rechargecardno,rechargecardtype,rechargeaccounttype,rechargetype,
							membername,rechargekeepamt,rechargedebtamt,curcardamt,curcarddebtamt,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
							thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
							sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
							ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,
							financedate,operationer,operationdate,backbillid,backflag)
  select rechargecompid,rechargebillid+'bak',rechargedate,rechargetime,rechargecardno,rechargecardtype,rechargeaccounttype,rechargetype,
		membername,isnull(rechargekeepamt,0)*(-1),isnull(rechargedebtamt,0)*(-1),curcardamt,curcarddebtamt,firstsalerid,firstsalerinid,isnull(firstsaleamt,0)*(-1),secondsalerid,secondsalerinid,isnull(secondsaleamt,0)*(-1),
        thirdsalerid,thirdsalerinid,isnull(thirdsaleamt,0)*(-1),fourthsalerid,fourthsalerinid,isnull(fourthsaleamt,0)*(-1),fifthsalerid,fifthsalerinid,isnull(fifthsaleamt,0)*(-1),
        sixthsalerid,sixthsalerinid,isnull(sixthsaleamt,0)*(-1),seventhsalerid,seventhsalerinid,isnull(seventhsaleamt,0)*(-1),eighthsalerid,eighthsalerinid,isnull(eighthsaleamt,0)*(-1),
        ninthsalerid,ninthsalerinid,isnull(ninthsaleamt,0)*(-1),tenthsalerid,tenthsalerinid,isnull(tenthsaleamt,0)*(-1),
		@curdate,operationer,operationdate,backbillid,1
	from  mcardrechargeinfo where rechargecompid=@compid and rechargebillid=@billid  
	
	insert dpayinfo(paycompid,paybillid,paybilltype,payseqno,paymode,payamt)
    select paycompid,paybillid+'bak',paybilltype,payseqno,paymode,isnull(payamt,0)*(-1) from dpayinfo where paycompid=@compid and paybillid=@billid and paybilltype='CZ'  
   
end 