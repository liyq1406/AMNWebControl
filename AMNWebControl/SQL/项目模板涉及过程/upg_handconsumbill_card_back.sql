alter procedure upg_handconsumbill_card_back    
@compid   varchar(10), --消费公司          
@sendid   varchar(20), --消费单号     
@costdate  varchar(8),  --消费日期    
@costtype  int ,   -- 1 散客消费 2 会员消费    
@oldsendid  varchar(20)  --原始消费单号     
as          
begin     
 --更新内部工号    
 update dconsumeinfo set csfirstinid=manageno from dconsumeinfo,staffinfo where cscompid=@compid and csbillid=@sendid and csfirstsaler=staffno and compno=@compid    
 update dconsumeinfo set cssecondinid=manageno from dconsumeinfo,staffinfo where cscompid=@compid and csbillid=@sendid and cssecondsaler=staffno and compno=@compid    
 update dconsumeinfo set csthirdinid=manageno from dconsumeinfo,staffinfo where cscompid=@compid and csbillid=@sendid and csthirdsaler=staffno and compno=@compid    
     
 declare @cardno varchar(20)   --消费卡号    
 declare @costaccount2 float  --储值账户    
 declare @costaccount3 float  --积分账户    
 declare @costaccount4 float  --疗程账户    
 declare @costaccount5 float  --收购卡账户    
 declare @tuangoucardno varchar(20) --团购卡号    
 declare @tiaomacardno varchar(20) --条码卡号    
 declare @diyongcardno varchar(20) --抵用券号    
     
 select @cardno=cscardno,@tuangoucardno=isnull(tuangoucardno,''),@tiaomacardno=isnull(tiaomacardno,''),@diyongcardno=isnull(diyongcardno,''),    
 @costaccount2=SUM(case when paymode='4' then isnull(payamt,0) else 0 end ),    
 @costaccount3=SUM(case when paymode='7' then isnull(payamt,0) else 0 end ),    
 @costaccount4=SUM(case when paymode='9' then isnull(payamt,0) else 0 end ),    
 @costaccount5=SUM(case when paymode='A' then isnull(payamt,0) else 0 end )    
 from mconsumeinfo with(nolock),dpayinfo with(nolock)    
 where cscompid=paycompid and csbillid=paybillid and paybilltype='SY'    
 and cscompid=@compid and  csbillid=@sendid    
 group by cscardno,isnull(tuangoucardno,''),isnull(tiaomacardno,''),isnull(diyongcardno,'')    
    
 --团购卡    
 if(isnull(@tuangoucardno,'')<>'')    
 begin    
  update corpsbuyinfo set corpssate=1,useincompid='', useinbillno='',useindate='' where corpscardno=@tuangoucardno    
 end    
     
 --抵用券    
 if(isnull(@diyongcardno,'')<>'')    
 begin    
  update nointernalcardinfo set cardstate=1,usedate='', useinproject='' where cardno=@diyongcardno and cardtype=1    
  --update a set a.usecount=ISNULL(a.usecount,0)-ISNULL(b.csitemcount,0),a.lastcount=ISNULL(a.lastcount,0)+ISNULL(b.csitemcount,0),    
               --a.useamt=ISNULL(a.useamt,0)-ISNULL(b.csitemamt,0),a.lastamt=ISNULL(a.lastamt,0)+ISNULL(b.csitemamt,0)     
   --from dnointernalcardinfo a,dconsumeinfo b    
  --where cscompid=@compid and csbillid=@oldsendid and cspaymode='11' and a.ineritemno=b.csitemno and a.cardno=@diyongcardno    
      
 end    
     
 --条码卡    
 if(isnull(@tiaomacardno,'')<>'')    
 begin    
  update a set a.usecount=ISNULL(a.usecount,0)-ISNULL(b.csitemcount,0),a.lastcount=ISNULL(a.lastcount,0)+ISNULL(b.csitemcount,0),    
               a.useamt=ISNULL(a.useamt,0)-ISNULL(b.csitemamt,0),a.lastamt=ISNULL(a.lastamt,0)+ISNULL(b.csitemamt,0)     
   from dnointernalcardinfo a,dconsumeinfo b    
  where cscompid=@compid and csbillid=@oldsendid and cspaymode='13' and a.ineritemno=b.csitemno and a.cardno=@tiaomacardno    
      
 end    
     
 --唯一产品条码    
 update dgoodsbarinfo set barnostate=2,costbillo='',coststore='' where costbillo=@oldsendid   and coststore=@compid    
 if(@costtype=1)    
  return     
 declare @costaccountseqno float  --最新账户序号    
 declare @costaccount2lastamt float  --储值账户前次余额    
 declare @costaccount3lastamt float  --积分账户前次余额    
 declare @costaccount4lastamt float  --疗程账户前次余额    
 declare @costaccount5lastamt float  --收购卡账户前次余额    
     
 select @costaccountseqno=MAX(changeseqno)+1 from cardaccountchangehistory where changecardno=@cardno     
 --更新会员卡账户+账户历史 (若是疗程账户大于0,则需扣除相应的疗程明细)    
 if(ISNULL(@costaccount2,0)<>0) --储值账户    
 begin    
    --生成账户历史    
   select top 1 @costaccount2lastamt=(case when changetype in (0,6) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@cardno  and changeaccounttype=2 order by chagedate desc,changeseqno desc     
   if(ISNULL(@costaccount2lastamt,0)=0)    
    select @costaccount2lastamt=ISNULL(accountbalance,0) from cardaccount where cardno=@cardno and accounttype=2    
   insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)    
   values(@compid,@cardno,2,ISNULL(@costaccountseqno,0),2,@costaccount2,'SY',@sendid,@costdate,@costaccount2lastamt)    
   set @costaccountseqno=ISNULL(@costaccountseqno,0)+1    
   --更新账户    
   update cardaccount set accountbalance=ISNULL(accountbalance,0)-ISNULL(@costaccount2,0) where cardno=@cardno and accounttype='2'    
      
 end    
 if(ISNULL(@costaccount3,0)<>0) --积分账户    
 begin    
   --生成账户历史    
   select top 1 @costaccount3lastamt=(case when changetype in (0,6) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@cardno  and changeaccounttype=3 order by chagedate desc,changeseqno desc     
   if(ISNULL(@costaccount3lastamt,0)=0)    
    select @costaccount3lastamt=ISNULL(accountbalance,0) from cardaccount where cardno=@cardno and accounttype=3    
   insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)    
   values(@compid,@cardno,3,ISNULL(@costaccountseqno,0),2,@costaccount3,'SY',@sendid,@costdate,@costaccount3lastamt)    
   set @costaccountseqno=ISNULL(@costaccountseqno,0)+1    
   --更新账户    
   update cardaccount set accountbalance=ISNULL(accountbalance,0)-ISNULL(@costaccount3,0) where cardno=@cardno and accounttype='3'    
       
 end    
 if(ISNULL(@costaccount4,0)<>0) --疗程账户    
 begin     
   --生成账户历史    
   select top 1 @costaccount4lastamt=(case when changetype in (0,6,7,8,9,10,11) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@cardno  and changeaccounttype=4 order by chagedate desc,changeseqno desc     
   if(ISNULL(@costaccount4lastamt,0)=0)    
    select @costaccount4lastamt=ISNULL(accountbalance,0) from cardaccount where cardno=@cardno and accounttype=4    
   insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)    
   values(@compid,@cardno,4,ISNULL(@costaccountseqno,0),2,@costaccount4,'SY',@sendid,@costdate,@costaccount4lastamt)    
   set @costaccountseqno=ISNULL(@costaccountseqno,0)+1    
   --更新账户    
   update cardaccount set accountbalance=ISNULL(accountbalance,0)-ISNULL(@costaccount4,0) where cardno=@cardno and accounttype='4'    
       
   --消耗疗程明细    
   update cardproaccount set costcount=isnull(costcount,0)+isnull(csitemcount,0),    
           lastcount=isnull(lastcount,0)-isnull(csitemcount,0),    
           costamt=isnull(costamt,0)+isnull(csitemamt,0),    
           lastamt=isnull(lastamt,0)-isnull(csitemamt,0)    
   from cardproaccount,    
   (select csitemno,csproseqno,csitemcount=sum(ISNULL(csitemcount,0)),csitemamt=SUM(isnull(csitemamt,0)) from dconsumeinfo where cspaymode='9' and cscompid=@compid and  csbillid=@sendid group by csitemno,csproseqno) as costdetial    
    where cardno=@cardno and projectno=csitemno and proseqno=csproseqno    
 end    
 if(ISNULL(@costaccount5,0)<>0) --收购账户    
 begin    
    --生成账户历史    
   select top 1 @costaccount5lastamt=(case when changetype in (0,6,7,8,9,10,11) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@cardno  and changeaccounttype=5 order by chagedate desc,changeseqno desc     
   if(ISNULL(@costaccount5lastamt,0)=0)    
   select @costaccount5lastamt=ISNULL(accountbalance,0) from cardaccount where cardno=@cardno and accounttype=5    
   insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)    
   values(@compid,@cardno,5,ISNULL(@costaccountseqno,0),2,@costaccount5,'SY',@sendid,@costdate,@costaccount5lastamt)    
   --更新账户    
   update cardaccount set accountbalance=ISNULL(accountbalance,0)-ISNULL(@costaccount5,0) where cardno=@cardno and accounttype='5'    
      
 end    
     
 --生成项目交易明细    
 insert cardtransactionhistory(transactioncompid,transactioncardno,transactiondate,transactiontype,codeno,codename,ccount,price,billtype,billno,firstempid,secondempid,thirthempid,paymode)    
 select cscompid,@cardno,@costdate,3,csitemno,prjname,csitemcount,csitemamt,'SY',csbillid,csfirstsaler,cssecondsaler,csthirdsaler,cspaymode    
  from dconsumeinfo a,sysparaminfo b,projectinfo c    
   where  csinfotype=1  and cscompid=@compid and  csbillid=@sendid    
   and  c.prjno = a.csitemno and  b.compid=@compid and b.paramid='SP059' and b.paramvalue=c.prjmodeId    
    
    
 --生成产品交易明细    
 insert cardtransactionhistory(transactioncompid,transactioncardno,transactiondate,transactiontype,codeno,codename,ccount,price,billtype,billno,firstempid,secondempid,thirthempid,paymode)    
 select cscompid,@cardno,@costdate,4,csitemno,goodsname,csitemcount,csitemamt,'SY',csbillid,csfirstsaler,cssecondsaler,csthirdsaler,cspaymode    
  from dconsumeinfo a,compchaininfo b,goodsinfo c    
   where  csinfotype=2  and cscompid=@compid and  csbillid=@sendid    
   and  c.goodsno = a.csitemno and  b.curcomp=c.goodssource and b.relationcomp=@compid    
    
  --删除库存  
   delete mgoodsstockinfo where changecompid=@compid and changebillno=@oldsendid and changetype=3  
    delete dgoodsstockinfo where changecompid=@compid and changebillno=@oldsendid and changetype=3   
end 