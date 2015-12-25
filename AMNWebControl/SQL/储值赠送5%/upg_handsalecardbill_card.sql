alter procedure upg_handsalecardbill_card  
@compid   varchar(10), --销售公司        
@billid   varchar(20), --销售单号   
@cardtype  varchar(20) --卡种  
as        
begin   
 --更新内部工号  
 update msalecardinfo set firstsalerinid=manageno from msalecardinfo,staffinfo where salecompid=@compid and salebillid=@billid and firstsalerid=staffno and compno=@compid  
 update msalecardinfo set secondsalerinid=manageno from msalecardinfo,staffinfo where salecompid=@compid and salebillid=@billid and secondsalerid=staffno and compno=@compid  
 update msalecardinfo set thirdsalerinid=manageno from msalecardinfo,staffinfo where salecompid=@compid and salebillid=@billid and thirdsalerid=staffno and compno=@compid  
 update msalecardinfo set fourthsalerinid=manageno from msalecardinfo,staffinfo where salecompid=@compid and salebillid=@billid and fourthsalerid=staffno and compno=@compid  
 update msalecardinfo set fifthsalerinid=manageno from msalecardinfo,staffinfo where salecompid=@compid and salebillid=@billid and fifthsalerid=staffno and compno=@compid  
 update msalecardinfo set sixthsalerinid=manageno from msalecardinfo,staffinfo where salecompid=@compid and salebillid=@billid and sixthsalerid=staffno and compno=@compid  
 update msalecardinfo set seventhsalerinid=manageno from msalecardinfo,staffinfo where salecompid=@compid and salebillid=@billid and seventhsalerid=staffno and compno=@compid  
 update msalecardinfo set eighthsalerinid=manageno from msalecardinfo,staffinfo where salecompid=@compid and salebillid=@billid and eighthsalerid=staffno and compno=@compid  
 declare @salecardno   varchar(20)  --销售卡号  
 declare @saledate   varchar(8)  --销售日期  
 declare @saletime   varchar(9)  --销售时间  
 declare @membername   varchar(30)  --会员姓名  
 declare @memberphone  varchar(20)  --会员手机  
 declare @membersex   int    --会员性别  
 declare @memberpcid   varchar(30)  --会员生分证号  
 declare @memberbirthday  varchar(8)  --会员生日  
 declare @acc2keepamt  float   --储值金额  
 declare @debtamt   float   --储值欠款(AMN不允许欠款)  
 declare @acc2totalamt  float   --储值总额  
 declare @acc4totalamt  float   --疗程总额  
 declare @paykeeptotalamt float   --支付总额  
 declare @paydebttotalamt float   --支付总欠款  
 declare @corpscardno  varchar(10)  --团购号  
  
 select @salecardno=salecardno,@saledate=saledate,@saletime=saletime,@membername=membername,@memberphone=memberphone, 
 @membersex=membersex,@memberpcid=memberpcid,@memberbirthday=memberbirthday,@corpscardno=corpscardno   
 from msalecardinfo with(nolock) where salecompid=@compid and salebillid=@billid  
   
  
 select @paykeeptotalamt=SUM(ISNULL(payamt,0)) from dpayinfo with(nolock)  where paycompid=@compid and paybillid=@billid and paybilltype='SK' and ISNULL(paymode,'')<>'5'  
 select @paydebttotalamt=SUM(ISNULL(payamt,0)) from dpayinfo with(nolock)  where paycompid=@compid and paybillid=@billid and paybilltype='SK' and ISNULL(paymode,'')='5'  
 select @acc4totalamt=sum(ISNULL(saleproamt,0)) from dsalecardproinfo with(nolock) where salecompid=@compid and salebillid=@billid and salebilltype=1  
 set @acc2totalamt=ISNULL(@paykeeptotalamt,0)-ISNULL(@acc4totalamt,0)  
   
 --更新会员卡号  
  --获得该卡种的有效期限  
  declare @carduselife float  
  select @carduselife=carduselife from cardtypeinfo where cardtypeno=@cardtype and cardtypemodeid='SCM'  
  declare @cutoffdate varchar(10)  
  select @cutoffdate=convert(varchar(10),dateadd(day,@carduselife,cast(@saledate as datetime)),120)  
  select @cutoffdate=substring(@cutoffdate,1,4)+substring(@cutoffdate,6,2)+substring(@cutoffdate,9,2)  
  update cardinfo set salecarddate=@saledate,cutoffdate=@cutoffdate,cardstate=4,salebillno=@billid,cardsource=0 where cardno=@salecardno  
 --新增账户金额  
  if exists(select 1 from cardaccount where cardno=@salecardno and accounttype=2)  
   update cardaccount set accountbalance=@acc2totalamt,accountdatefrom=@saledate,accountdateend=@cutoffdate where cardno=@salecardno and accounttype=2  
  else  
  begin  
   insert cardaccount(cardvesting,cardno,accounttype,accountbalance,accountdebts,accountdatefrom,accountdateend,accountremark)  
   values(@compid,@salecardno,2,@acc2totalamt,0,@saledate,@cutoffdate,'')  
  end  
  if exists(select 1 from cardaccount where cardno=@salecardno and accounttype=4)  
   update cardaccount set accountbalance=@acc4totalamt,accountdatefrom=@saledate,accountdateend=@cutoffdate where cardno=@salecardno and accounttype=4  
  else  
  begin  
   insert cardaccount(cardvesting,cardno,accounttype,accountbalance,accountdebts,accountdatefrom,accountdateend,accountremark)  
   values(@compid,@salecardno,4,@acc4totalamt,0,@saledate,@cutoffdate,'')  
  end  
 --新增会员基本资料  
  if exists(select 1 from memberinfo where cardnotomemberno=@salecardno )  
   update memberinfo set membervesting=@compid,memberno=@salecardno,membercreatedate=@saledate,  
    membername=@membername,membertphone=@memberphone,membersex=@membersex,memberpaperworkno=@memberpcid,  
    memberbirthday=@memberbirthday where cardnotomemberno=@salecardno  
  else  
  begin  
   insert memberinfo(membervesting,memberno,membercreatedate,membername,membermphone,membersex,memberpaperworkno,memberbirthday,cardnotomemberno)  
   values(@compid,@salecardno,@saledate,@membername,@memberphone,@membersex,@memberpcid,@memberbirthday,@salecardno)  
  end  
 --新增账户历史  
  declare @costaccountseqno  float  
  declare @costaccount2lastamt float  --储值账户前次余额  
  declare @costaccount4lastamt float  --疗程账户前次余额  
  
  select @costaccountseqno=MAX(changeseqno)+1 from cardaccountchangehistory where changecardno=@salecardno   
  if(ISNULL(@acc2totalamt,0)>0) --储值账户  
  begin  
    --生成账户历史  
    select top 1 @costaccount2lastamt=(case when changetype in (0,6,7,8,9,10,11) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@salecardno  and changeaccounttype=2 order by chagedate desc,changeseqno desc   
    insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)  
    values(@compid,@salecardno,2,isnull(@costaccountseqno,0),0,@acc2totalamt,'SK',@billid,@saledate,@costaccount2lastamt)  
    set @costaccountseqno=isnull(@costaccountseqno,0)+1  
  end  
   
  if(ISNULL(@acc4totalamt,0)>0) --疗程账户  
  begin   
    --生成账户历史  
    select top 1 @costaccount4lastamt=(case when changetype in (0,6,7,8,9,10,11) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@salecardno  and changeaccounttype=4 order by chagedate desc,changeseqno desc   
    insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)  
    values(@compid,@salecardno,4,isnull(@costaccountseqno,0),0,@acc4totalamt,'SK',@billid,@saledate,@costaccount4lastamt)  
    set @costaccountseqno=isnull(@costaccountseqno,0)+1  
      
  end  
 --新增异动历史  
  declare @saleaccountseqno  float  
  select @saleaccountseqno=MAX(changeseqno)+1 from cardchangehistory where changecardno=@salecardno   
  insert cardchangehistory(changecompid,changecardno,changeseqno,changetype,changebillid,beforestate,afterstate,chagedate,targetcardno)  
  values(@compid,@salecardno,isnull(@saleaccountseqno,0),2,@billid,0,4,@saledate,'')  
   
 --生成疗程信息  
  declare @proseqno float  
  select @proseqno=MAX(proseqno)+1 from cardproaccount with(nolock) where cardno=@salecardno   
  insert cardproaccount(cardvesting,cardno,projectno,proseqno,propricetype,salecount,costcount,lastcount,saleamt,costamt,lastamt,saledate,cutoffdate,proremark,createbilltype,createbillno)  
  select @compid,@salecardno,saleproid,ISNULL(@proseqno,0)+ISNULL(seleproseqno,0),saleprotype,ISNULL(saleprocount,0)+ISNULL(sendprocount,0),0,ISNULL(saleprocount,0)+ISNULL(sendprocount,0),  
  ISNULL(saleproamt,0),0,ISNULL(saleproamt,0),@saledate,procutoffdate,saleproremark,'SK', @billid  
  from dsalecardproinfo where salecompid=@compid and salebillid=@billid and salebilltype=1  
    
 --处理团购信息  
 if(ISNULL(@corpscardno,'')<>'')  
 begin  
  update corpsbuyinfo set corpssate=2,useincompid=@compid,useinbillno=@billid,useindate =@saledate where corpscardno=@corpscardno  
 end  
 
  
  declare @SP106 varchar(2)
  select @SP106=paramvalue from sysparaminfo where compid=@compid and paramid='SP106'
  -- 充值金额2W上送5%的卡金  账户编号 7
  if(ISNULL(@acc2totalamt,0)>=20000  and ISNULL(@SP106,'')='1')
  begin
	if exists(select 1 from cardaccount where cardno=@salecardno and accounttype=6)  
			update cardaccount set accountbalance=ISNULL(@acc2totalamt,0)*0.05,accountdatefrom=@saledate,accountdateend=@cutoffdate where cardno=@salecardno and accounttype=6  
	else  
	begin  
		insert cardaccount(cardvesting,cardno,accounttype,accountbalance,accountdebts,accountdatefrom,accountdateend,accountremark)  
		values(@compid,@salecardno,6,ISNULL(@acc2totalamt,0)*0.05,0,@saledate,@cutoffdate,'')  
	end  
	insert sendpointcard(sendcompid,sendbillid,sendtype,senddate,sourcebillid,sourcecardno,sourcedate,sourceamt,sendamt,sendpicflag)
	values(@compid,@billid+'ZS',0,@saledate,@billid,@salecardno,@saledate,@acc2totalamt,ISNULL(@acc2totalamt,0)*0.05,3)
  end
end   