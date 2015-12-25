if exists(select 1 from sysobjects where type='P' and name='upg_diarialBill_byday_analysis')
	drop procedure upg_diarialBill_byday_analysis
go
create procedure upg_diarialBill_byday_analysis 
(             
  @compid  varchar(10),   -- 已经能支持各级别的公司              
  @fromdate varchar(10),  --开始日期              
  @todate   varchar(10)  --结束日期      
)            
as              
begin                      
	CREATE TABLE    #dpayinfo_billcount             --  单据--支付明细
	(
		paycompid		varchar(10)      NULL,   --公司编号
		paydate			varchar(10)      NULL,   --支付日期
		paybilltype		varchar(5)		 NULL,   --单据类别  SY 收银  SK  售卡  CZ 充值 ZK  转卡 HZ 合作项目 TK 退卡
		paymode			varchar(5)		 NULL,   --支付方式
		payamt			float			 NULL,   --支付金额
	)

	exec upg_compute_comp_trade_payinfo @compid,@fromdate,@todate
    
    
    select  paycompid,paydate,paybilltype,paymode,payamt=sum(isnull(payamt,0)) 
    from #dpayinfo_billcount group by paycompid,paydate,paybilltype,paymode
    drop table #dpayinfo_billcount
end 