alter procedure upg_create_trade_dailydata    
(    
 @compid   varchar(10),  --门店号     
 @datefrom  varchar(10),  --起始日期    
 @dateto   varchar(10),  --结束日期    
 @showtype  int     --显示类型(1 日记账 2 对账表)    
)      
as     
begin    
     
 CREATE TABLE    #dpayinfo_billcount               --  单据--支付明细    
 (    
  paycompid  varchar(10)      NULL,   --公司编号    
  paydate   varchar(10)      NULL,   --支付日期    
  paybilltype  varchar(5)   NULL,   --单据类别  SY 收银  SK  售卡  CZ 充值 ZK  转卡 HZ 合作项目 TK 退卡    
  paymode   varchar(5)   NULL,   --支付方式    
  payamt   float    NULL,   --支付金额    
 )    
    
 exec upg_compute_comp_trade_payinfo @compid,@datefrom,@dateto    
     
 if(@showtype=1)    
 begin    
  create table #tradedailydata   --门店营业临时表    
  (    
   seqno   int identity(1,1) NOT NULL, --默认编号    
   tradeTitle  varchar(50)    NULL, --营业内容    
   tradeAmt  varchar(30)    NULL, --营业金额    
   valueflag  int      NULL --显示标示    
  )    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '门店名称',compname,1 from companyinfo where compno=@compid    
    
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '总营业额',    
  convert(numeric(20,1),SUM(case when  paymode in ('1','6','14','15','16','2') and  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1)    
     when  paymode in ('1','6','14','15','16','2') and  isnull(paybilltype,'')<>'TK' then isnull(payamt,0) else 0 end )) ,2    
  from #dpayinfo_billcount where paycompid=@compid     
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '员工购买产品',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,3    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode in ('1','6','14','15','16','2') and  isnull(paybilltype,'')='SY_G2'    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '现金服务',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),4     
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='1' and  isnull(paybilltype,'')='SY_P'    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '现金产品',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),5     
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='1' and  isnull(paybilltype,'') in  ('SY_G1','SY_G2')    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '现金退卡',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0))*(-1),6     
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='1' and  isnull(paybilltype,'')='TK'    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '现金卡异动',convert(numeric(20,1),isnull(  sum(case when  isnull(paybilltype,'')='TK' then 0   
             when  isnull(paybilltype,'') in ('SK','CZ','ZK') then isnull(payamt,0) else 0 end),0)),7     
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='1' and  isnull(paybilltype,'') in ('TK','SK','CZ','ZK')    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '现金条码卡',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),8    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='1' and  isnull(paybilltype,'')='TMK'    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '现金疗程兑换',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),9     
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='1' and  isnull(paybilltype,'')='LXDH'    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '现金合作项目',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,10    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='1' and  isnull(paybilltype,'')='HZ'    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '现金合计',convert(numeric(20,1),isnull(sum(case when  isnull(paybilltype,'')='TK' then 0 else isnull(payamt,0) end),0)),11     
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='1'    
      
  -------------------------银行卡-----------------------------------    
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '银行卡服务',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),12     
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='6' and  isnull(paybilltype,'')='SY_P'    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '银行卡产品',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),13     
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='6' and  isnull(paybilltype,'') in  ('SY_G1','SY_G2')    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '银行卡退卡',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0))*(-1),14     
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='6' and  isnull(paybilltype,'')='TK'    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '银行卡卡异动',convert(numeric(20,1),isnull(  sum(case when  isnull(paybilltype,'')='TK' then 0  
             when  isnull(paybilltype,'') in ('SK','CZ','ZK') then isnull(payamt,0) else 0 end),0)) ,15    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='6' and  isnull(paybilltype,'') in ('TK','SK','CZ','ZK')    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '银行卡条码卡',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),16    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='6' and  isnull(paybilltype,'')='TMK'    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '银行卡疗程兑换',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,17    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='6' and  isnull(paybilltype,'')='LXDH'    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '银行卡合作项目',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),18    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='6' and  isnull(paybilltype,'')='HZ'    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '银行卡合计',convert(numeric(20,1),isnull(sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1) else isnull(payamt,0) end),0)),19     
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='6'    
      
   -------------------------支票-----------------------------------    
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '支票服务',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),20     
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='2' and  isnull(paybilltype,'')='SY_P'    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '支票产品',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,21    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='2' and  isnull(paybilltype,'') in  ('SY_G1','SY_G2')    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '支票退卡',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0))*(-1) ,22    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='2' and  isnull(paybilltype,'')='TK'    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '支票卡异动',convert(numeric(20,1),isnull(  sum(case when  isnull(paybilltype,'')='TK' then 0  
             when  isnull(paybilltype,'') in ('SK','CZ','ZK') then isnull(payamt,0) else 0 end),0)) ,23    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='2' and  isnull(paybilltype,'') in ('TK','SK','CZ','ZK')    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '支票条码卡',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),24     
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='2' and  isnull(paybilltype,'')='TMK'    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '支票疗程兑换',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),25     
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='2' and  isnull(paybilltype,'')='LXDH'    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '支票合作项目',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),26    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='2' and  isnull(paybilltype,'')='HZ'    
      
       
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '支票合计',convert(numeric(20,1),isnull(sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1) else isnull(payamt,0) end),0)),27     
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='2'    
      
  -------------------------指付通-----------------------------------    
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '指付通服务',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,28    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='14' and  isnull(paybilltype,'')='SY_P'    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '指付通产品',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,29    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='14' and  isnull(paybilltype,'') in  ('SY_G1','SY_G2')    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '指付通退卡',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0))*(-1) ,30    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='14' and  isnull(paybilltype,'')='TK'    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '指付通卡异动',convert(numeric(20,1),isnull(  sum(case when  isnull(paybilltype,'')='TK' then 0  
             when  isnull(paybilltype,'') in ('SK','CZ','ZK') then isnull(payamt,0) else 0 end),0)) ,31    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='14' and  isnull(paybilltype,'') in ('TK','SK','CZ','ZK')    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '指付通条码卡',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,32    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='14' and  isnull(paybilltype,'')='TMK'    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '指付通疗程兑换',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,33    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='14' and  isnull(paybilltype,'')='LXDH'    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '指付通合作项目',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,34    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='14' and  isnull(paybilltype,'')='HZ'    
      
       
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '指付通合计',convert(numeric(20,1),isnull(sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1) else isnull(payamt,0) end),0)) ,35    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='14'    
      
  -------------------------OK卡-----------------------------------    
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select 'OK卡服务',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,36    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='15' and  isnull(paybilltype,'')='SY_P'    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select 'OK卡产品',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,37    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='15' and  isnull(paybilltype,'') in  ('SY_G1','SY_G2')    
      
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select 'OK卡异动',convert(numeric(20,1),isnull(  sum(case when  isnull(paybilltype,'')='TK' then 0  
             when  isnull(paybilltype,'') in ('SK','CZ','ZK') then isnull(payamt,0) else 0 end),0)) ,38    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='15' and  isnull(paybilltype,'') in ('TK','SK','CZ','ZK')    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select 'OK卡条码卡',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),39     
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='15' and  isnull(paybilltype,'')='TMK'    
      
    
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select 'OK卡合计',convert(numeric(20,1),isnull(sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1) else isnull(payamt,0) end),0)) ,40    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='15'    
      
  -------------------------团购卡-----------------------------------    
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '团购卡服务',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)),41    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='16' and  isnull(paybilltype,'')='SY_P'    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '团购卡产品',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,42    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='16' and  isnull(paybilltype,'') in  ('SY_G1','SY_G2')    
      
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '团购卡异动',convert(numeric(20,1),isnull(  sum(case when  isnull(paybilltype,'')='TK' then 0  
             when  isnull(paybilltype,'') in ('SK','CZ','ZK') then isnull(payamt,0) else 0 end),0)),43     
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='16' and  isnull(paybilltype,'') in ('TK','SK','CZ','ZK')    
      
    
    
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '团购卡合计',convert(numeric(20,1),isnull(sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1) else isnull(payamt,0) end),0)) ,44    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='16'    
      
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '卡异动合计',convert(numeric(20,1),isnull(  sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1)    
             when  isnull(paybilltype,'') in ('SK','CZ','ZK') then isnull(payamt,0) else 0 end),0)),45    
  from #dpayinfo_billcount    
  where paycompid=@compid and  isnull(paybilltype,'') in ('TK','SK','CZ','ZK')    
      
  ------------------------------------销卡---------------------------------------    
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '销卡服务',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,46    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode in ('4','17') and  isnull(paybilltype,'')='SY_P'    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '疗程服务',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,47    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='9' and  isnull(paybilltype,'')='SY_P'    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '收购卡服务',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,48    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='A' and  isnull(paybilltype,'')='SY_P'    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '销卡产品',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,49    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode in ('4','17') and  isnull(paybilltype,'') in  ('SY_G1','SY_G2')    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '积分账户服务',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,50    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='7' and  isnull(paybilltype,'')='SY_P'    
      
      
      
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '现金抵用券服务',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,51    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='12' and  isnull(paybilltype,'')='SY_P'    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '项目抵用券服务',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,52    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='11' and  isnull(paybilltype,'')='SY_P'    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '条码卡服务',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0) ),53    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='13' and  isnull(paybilltype,'')='SY_P'    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '支付登记',0,54    
      
  insert #tradedailydata(tradeTitle,tradeAmt,valueflag)    
  select '经理签单',convert(numeric(20,1),isnull(sum(isnull(payamt,0)),0)) ,55    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='8'     
     
  select tradeTitle,tradeAmt,valueflag from #tradedailydata    
  drop table #tradedailydata    
 end    
 else    
 begin    
  create table #tradedatedata   --门店新建对账    
  (    
   tradedate   varchar(50)     NULL, --营业日期    
   totalcashamt  float     NULL, --现金合计    
   staffcashamt  float     NULL, --员工现金产品    
   hezuocashamt  float     NULL --员工现金产品    
  )    
  insert #tradedatedata(tradedate,totalcashamt,staffcashamt,hezuocashamt)    
   select paydate,isnull(sum(case when  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1) else isnull(payamt,0) end),0) ,    
         isnull(sum(case when  isnull(paybilltype,'')='SP_G2' then isnull(payamt,0) else 0 end),0),    
         isnull(sum(case when  isnull(paybilltype,'')='HZ' then isnull(payamt,0) else 0 end),0)    
  from #dpayinfo_billcount    
  where paycompid=@compid and paymode ='1'     
  group by paydate    
      
  insert #tradedatedata(tradedate,totalcashamt,staffcashamt,hezuocashamt)    
  select '',sum(isnull(totalcashamt,0)),sum(isnull(staffcashamt,0)),sum(isnull(hezuocashamt,0))    
  from #tradedatedata    
      
  select tradedate,totalcashamt,staffcashamt,hezuocashamt from #tradedatedata    
  drop table #tradedatedata    
 end    
 drop table #dpayinfo_billcount    
end 