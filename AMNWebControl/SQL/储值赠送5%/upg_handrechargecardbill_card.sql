alter procedure upg_handrechargecardbill_card    
@compid   varchar(10), --续卡公司          
@billid   varchar(20), --续卡单号     
@cardtype  varchar(20) --卡种    
as          
begin     
 --更新内部工号    
 update mcardrechargeinfo set firstsalerinid=manageno from mcardrechargeinfo,staffinfo where rechargecompid=@compid and rechargebillid=@billid and firstsalerid=staffno and compno=@compid    
 update mcardrechargeinfo set secondsalerinid=manageno from mcardrechargeinfo,staffinfo where rechargecompid=@compid and rechargebillid=@billid and secondsalerid=staffno and compno=@compid    
 update mcardrechargeinfo set thirdsalerinid=manageno from mcardrechargeinfo,staffinfo where rechargecompid=@compid and rechargebillid=@billid and thirdsalerid=staffno and compno=@compid    
 update mcardrechargeinfo set fourthsalerinid=manageno from mcardrechargeinfo,staffinfo where rechargecompid=@compid and rechargebillid=@billid and fourthsalerid=staffno and compno=@compid    
 update mcardrechargeinfo set fifthsalerinid=manageno from mcardrechargeinfo,staffinfo where rechargecompid=@compid and rechargebillid=@billid and fifthsalerid=staffno and compno=@compid    
 update mcardrechargeinfo set sixthsalerinid=manageno from mcardrechargeinfo,staffinfo where rechargecompid=@compid and rechargebillid=@billid and sixthsalerid=staffno and compno=@compid    
 update mcardrechargeinfo set seventhsalerinid=manageno from mcardrechargeinfo,staffinfo where rechargecompid=@compid and rechargebillid=@billid and seventhsalerid=staffno and compno=@compid    
 update mcardrechargeinfo set eighthsalerinid=manageno from mcardrechargeinfo,staffinfo where rechargecompid=@compid and rechargebillid=@billid and eighthsalerid=staffno and compno=@compid    
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
 select @carduselife=carduselife from cardtypeinfo where cardtypeno=@cardtype and cardtypemodeid='SCM'    
 declare @cutoffdate varchar(10)    
 select @cutoffdate=convert(varchar(10),dateadd(day,@carduselife,cast(@saledate as datetime)),120)    
     
 select @salecardno=rechargecardno,@saledate=rechargedate,@saletime=rechargetime,@rechargetype=rechargetype,    
        @rechargekeepamt=rechargekeepamt,@rechargedebtamt=rechargedebtamt,@curcardamt=curcardamt,@curcarddebtamt=curcarddebtamt,@rechargeaccounttype=rechargeaccounttype    
 from mcardrechargeinfo with(nolock) where rechargecompid=@compid and rechargebillid=@billid    
     
 select @cutoffdate=substring(@cutoffdate,1,4)+substring(@cutoffdate,6,2)+substring(@cutoffdate,9,2)    
 select @acc4totalamt=sum(ISNULL(saleproamt,0)) from dsalecardproinfo with(nolock) where salecompid=@compid and salebillid=@billid and salebilltype=2    
     
 --更新账户金额    
 if(@rechargetype=0)--充值    
 begin    
  if exists(select 1 from cardaccount where cardno=@salecardno and accounttype=CAST(@rechargeaccounttype as int))    
  begin    
    update cardaccount set accountbalance=isnull(accountbalance,0)+ISNULL(@rechargekeepamt,0),accountdebts=isnull(accountdebts,0)+ISNULL(@rechargedebtamt,0) where cardno=@salecardno and accounttype=CAST(@rechargeaccounttype as int)    
  end    
  else    
  begin    
   insert cardaccount(cardvesting,cardno,accounttype,accountbalance,accountdebts,accountdatefrom,accountdateend,accountremark)    
   values(@compid,@salecardno,CAST(@rechargeaccounttype as int),ISNULL(@rechargekeepamt,0),ISNULL(@rechargedebtamt,0),@saledate,@cutoffdate,'')    
  end    
 end    
 else --还款    
 begin    
   update cardaccount set accountbalance=isnull(accountbalance,0)+ISNULL(@rechargekeepamt,0),accountdebts=isnull(accountdebts,0)-ISNULL(@rechargekeepamt,0) where cardno=@salecardno and accounttype=CAST(@rechargeaccounttype as int)    
 end    
 --新增账户历史    
  declare @costaccountseqno  float    
  select @costaccountseqno=MAX(changeseqno)+1 from cardaccountchangehistory where changecardno=@salecardno     
  insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)    
  values(@compid,@salecardno,CAST(@rechargeaccounttype as int),isnull(@costaccountseqno,0),0,@rechargekeepamt,'CZ',@billid,@saledate,@curcardamt)    
  set @costaccountseqno=isnull(@costaccountseqno,0)+1    
      
     
 --生成疗程信息    
  declare @proseqno float    
  select @proseqno=MAX(proseqno)+1 from cardproaccount with(nolock) where cardno=@salecardno     
  insert cardproaccount(cardvesting,cardno,projectno,proseqno,propricetype,salecount,costcount,lastcount,saleamt,costamt,lastamt,saledate,cutoffdate,proremark,createbilltype,createbillno)    
  select @compid,@salecardno,saleproid,ISNULL(@proseqno,0)+ISNULL(seleproseqno,0),saleprotype,ISNULL(saleprocount,0)+ISNULL(sendprocount,0),0,ISNULL(saleprocount,0)+ISNULL(sendprocount,0),    
  ISNULL(saleproamt,0),0,ISNULL(saleproamt,0),@saledate,procutoffdate,saleproremark,'CZ',@billid     
  from dsalecardproinfo where salecompid=@compid and salebillid=@billid and salebilltype=2    
      
  if(ISNULL(@acc4totalamt,0)>0)    
  begin    
	if exists(select 1 from cardaccount where cardno=@salecardno and accounttype=4)    
		update cardaccount set accountbalance=isnull(accountbalance,0)+@acc4totalamt where cardno=@salecardno and accounttype=4    
	else    
	begin    
		insert cardaccount(cardvesting,cardno,accounttype,accountbalance,accountdebts,accountdatefrom,accountdateend,accountremark)    
		values(@compid,@salecardno,4,@acc4totalamt,0,@saledate,@cutoffdate,'')    
	end    
	declare @costaccount4lastamt float     
	select top 1 @costaccount4lastamt=(case when changetype in (0,6) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@salecardno  and changeaccounttype=4 order by chagedate desc,changeseqno desc     
	insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)    
	values(@compid,@salecardno,4,isnull(@costaccountseqno,0),0,@acc4totalamt,'CZ',@billid,@saledate,isnull(@costaccount4lastamt,0))    
  end 
  declare @SP106 varchar(2)
  select @SP106=paramvalue from sysparaminfo where compid=@compid and paramid='SP106'
   -- 充值金额2W上送5%的卡金  账户编号 7
  if(ISNULL(@rechargekeepamt,0)>=20000 and ISNULL(@SP106,'')='1')
  begin
	if exists(select 1 from cardaccount where cardno=@salecardno and accounttype=6)  
			update cardaccount set accountbalance=isnull(accountbalance,0)+ISNULL(@rechargekeepamt,0)*0.05,accountdatefrom=@saledate,accountdateend=@cutoffdate where cardno=@salecardno and accounttype=6  
	else  
	begin  
		insert cardaccount(cardvesting,cardno,accounttype,accountbalance,accountdebts,accountdatefrom,accountdateend,accountremark)  
		values(@compid,@salecardno,6,ISNULL(@rechargekeepamt,0)*0.05,0,@saledate,@cutoffdate,'')  
	end  
	insert sendpointcard(sendcompid,sendbillid,sendtype,senddate,sourcebillid,sourcecardno,sourcedate,sourceamt,sendamt,sendpicflag)
	values(@compid,@billid+'ZS',1,@saledate,@billid,@salecardno,@saledate,@rechargekeepamt,ISNULL(@rechargekeepamt,0)*0.05,3)
  end   
end     