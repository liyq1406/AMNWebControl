
if exists(select 1 from sysobjects where type='P' and name='upg_compute_comp_tradedata')
	drop procedure upg_compute_comp_tradedata
go
create procedure upg_compute_comp_tradedata    
(
	@compid			varchar(10),		--门店号	
	@datefrom			varchar(10),	--起始日期
	@dateto			varchar(10)			--结束日期
)  
as
begin
	create table #comptrade   --门店营业临时表
	(
		compid				varchar(10)		null,	--门店编号
		compname			varchar(30)		null,	--门店名称
		tradecashamt		float			null,	--现金总收入
		tradebankamt		float			null,	--银行卡总收入
		tradefingeramt		float			null,	--指付通总收入
		tradeokcardamt		float			null,	--OK卡总收入
		tradetgcardamt		float			null,	--团购卡总收入
		tradeczcardamt		float			null,	--储值卡消费
		tradelccardamt		float			null,	--疗程卡消费
		tradecashdyamt		float			null,	--现金抵用券消费
		tradeprojdyamt		float			null,	--项目抵用券消费
		tradetmcardamt		float			null,	--条码卡消费
		trademrfakeamt		float			null,	--美容虚业绩
		trademffakeamt		float			null,	--美发虚业绩
		tradetrfakeamt		float			null,	--烫染虚业绩
		tradefgfakeamt		float			null,	--美甲虚业绩
		tradetotalamt		float			null,	--总需业绩
		traderealtotalamt	float			null,	--总需业绩
	)
	
	CREATE TABLE    #dpayinfo_billcount             --  单据--支付明细
	(
		paycompid		varchar(10)      NULL,   --公司编号
		paydate			varchar(10)      NULL,   --支付日期
		paybilltype		varchar(5)		 NULL,   --单据类别  SY 收银  SK  售卡  CZ 充值 ZK  转卡 HZ 合作项目 TK 退卡
		paymode			varchar(5)		 NULL,   --支付方式
		payamt			float			 NULL,   --支付金额
	)

	exec upg_compute_comp_trade_payinfo @compid,@datefrom,@dateto
	
	
	insert #comptrade(compid,compname,tradetotalamt,tradecashamt,tradebankamt,tradefingeramt,tradeokcardamt,tradetgcardamt,tradeczcardamt,
	tradelccardamt,tradecashdyamt,tradeprojdyamt,tradetmcardamt)
	select paycompid,compname,
	SUM(case when  paymode in ('1','6','14','15','16','2') and  isnull(paybilltype,'')='TK' then isnull(payamt,0)*(-1)
			 when  paymode in ('1','6','14','15','16','2') and  isnull(paybilltype,'')<>'TK' then isnull(payamt,0) else 0 end ) ,
	SUM(case when  paymode='1' then payamt else 0 end ) ,
	SUM(case when  paymode='6' then payamt else 0 end ) ,
	SUM(case when  paymode='14' then payamt else 0 end ) ,
	SUM(case when  paymode='15' then payamt else 0 end ) ,
	SUM(case when  paymode='16' then payamt else 0 end ) ,
	SUM(case when paybilltype	in ('SY_P','SY_G1','SY_G2') and paymode in ('4','5')then payamt else 0 end ) ,
	SUM(case when paybilltype	in ('SY_P','SY_G1','SY_G2') and paymode='9' then payamt else 0 end ) ,
	SUM(case when paybilltype	in ('SY_P','SY_G1','SY_G2') and paymode='11' then payamt else 0 end ) ,
	SUM(case when paybilltype	in ('SY_P','SY_G1','SY_G2') and paymode='12' then payamt else 0 end ) ,
	SUM(case when paybilltype	in ('SY_P','SY_G1','SY_G2') and paymode='13' then payamt else 0 end ) 
	from #dpayinfo_billcount,companyinfo where paycompid=compno  group by paycompid,compname
	

	create table #cls_yeji_result_search(                              
		  compid varchar(10) not null,    
		  ddate varchar(10) null,                           
		  beaut_yeji float null,                              
		  hair_yeji float null,                              
		  foot_yeji float null,                              
		  finger_yeji float null,                              
		  total_yeji float null,                              
		  real_beaut_yeji float null,                              
		  real_hair_yeji float null,                              
		  real_foot_yeji float null,                              
		  real_finger_yeji float null,                              
		  real_total_yeji  float null                              
		 ) 
	--获取分类统计
	exec upg_compute_comp_classed_trade  @compid,@datefrom,@dateto,2

	update a set trademrfakeamt=beaut_yeji,trademffakeamt=hair_yeji,tradetrfakeamt=foot_yeji,tradefgfakeamt=finger_yeji,traderealtotalamt=real_total_yeji
	  from #comptrade a,#cls_yeji_result_search b where a.compid=b.compid

	select compid,compname,tradecashamt,tradebankamt,tradefingeramt,tradeokcardamt,tradetgcardamt,tradeczcardamt,tradelccardamt,
	tradecashdyamt,tradeprojdyamt,tradetmcardamt,trademrfakeamt,trademffakeamt,tradetrfakeamt,tradefgfakeamt,tradetotalamt,traderealtotalamt
	from #comptrade
	
	drop table #cls_yeji_result_search
	drop table #dpayinfo_billcount
	drop table #comptrade
end

go

--exec upg_compute_comp_tradedata '014','20130801','20130831'