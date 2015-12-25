 --exec upg_compute_comp_classed_trade_daybyday '001','20130101', '20130131'  --分类日记账日结
 --go
 --exec upg_compute_comp_classed_trade_daybyday '001','20130201', '20130228'  --分类日记账日结
 -- go
 --exec upg_compute_comp_classed_trade_daybyday '001','20130301', '20130331'  --分类日记账日结
 -- go
 --exec upg_compute_comp_classed_trade_daybyday '001','20130401', '20130430'  --分类日记账日结
 -- go
 --exec upg_compute_comp_classed_trade_daybyday '001','20130501', '20130531'  --分类日记账日结
 -- go
 --exec upg_compute_comp_classed_trade_daybyday '001','20130601', '20130630'  --分类日记账日结
 -- go
 --exec upg_compute_comp_classed_trade_daybyday '001','20130701', '20130731'  --分类日记账日结
 -- go
 --exec upg_compute_comp_classed_trade_daybyday '001','20130801', '20130831'  --分类日记账日结
 -- go
 --exec upg_compute_comp_classed_trade_daybyday '001','20130901', '20130930'  --分类日记账日结
 -- go
 --exec upg_compute_comp_classed_trade_daybyday '001','20131001', '20131031'  --分类日记账日结
 -- go
 --exec upg_compute_comp_classed_trade_daybyday '001','20131101', '20131131'  --分类日记账日结
 -- go
 --exec upg_compute_comp_classed_trade_daybyday '001','20131201', '20131231'  --分类日记账日结
 
 --select * into compclasstraderesultbak20140116 from compclasstraderesult
 
 --select * into detial_trade_byday_fromshops20140116 from detial_trade_byday_fromshops
 
 --exec upg_compute_comp_trade_payinfo_daybyday '001','20130101', '20130131' --支付方式日结
 --exec upg_compute_comp_detial_trade '001','20130101', '20130131'	--日记账日结
 --go
 -- exec upg_compute_comp_trade_payinfo_daybyday '001','20130201', '20130228' --支付方式日结
 --exec upg_compute_comp_detial_trade '001','20130201', '20130228'	--日记账日结
 --go
 -- exec upg_compute_comp_trade_payinfo_daybyday '001','20130301', '20130331' --支付方式日结
 --exec upg_compute_comp_detial_trade '001','20130301', '20130331'	--日记账日结
 --go
 -- exec upg_compute_comp_trade_payinfo_daybyday '001','20130401', '20130430' --支付方式日结
 --exec upg_compute_comp_detial_trade '001','20130401', '20130430'	--日记账日结
 --go
 -- exec upg_compute_comp_trade_payinfo_daybyday '001','20130501', '20130531' --支付方式日结
 --exec upg_compute_comp_detial_trade '001','20130501', '20130531'	--日记账日结
 --go
 -- exec upg_compute_comp_trade_payinfo_daybyday '001','20130601', '20130630' --支付方式日结
 --exec upg_compute_comp_detial_trade '001','20130601', '20130630'	--日记账日结
 --go
 -- exec upg_compute_comp_trade_payinfo_daybyday '001','20130701', '20130731' --支付方式日结
 --exec upg_compute_comp_detial_trade '001','20130701', '20130731'	--日记账日结
 --go
 -- exec upg_compute_comp_trade_payinfo_daybyday '001','20130801', '20130831' --支付方式日结
 --exec upg_compute_comp_detial_trade '001','20130801', '20130831'	--日记账日结
 --go
 -- exec upg_compute_comp_trade_payinfo_daybyday '001','20130901', '20130930' --支付方式日结
 --exec upg_compute_comp_detial_trade '001','20130901', '20130930'	--日记账日结
 --go
 -- exec upg_compute_comp_trade_payinfo_daybyday '001','20131001', '20131031' --支付方式日结
 --exec upg_compute_comp_detial_trade '001','20131001', '20131031'	--日记账日结
 --go
 -- exec upg_compute_comp_trade_payinfo_daybyday '001','20131101', '20131130' --支付方式日结
 --exec upg_compute_comp_detial_trade '001','20131101', '20131130'	--日记账日结
 --go
 -- exec upg_compute_comp_trade_payinfo_daybyday '001','20131201', '20131231' --支付方式日结
 --exec upg_compute_comp_detial_trade '001','20131201', '20131231'	--日记账日结
 --go
 
 select * into staff_work_salarybak20140116bak from staff_work_salary
 exec upg_compute_comp_satff_salary_daybyday '001','20130101', '20130131'  --工资日结
 go
 exec upg_compute_comp_satff_salary_daybyday '001','20130201', '20130228'  --工资日结
  go
 exec upg_compute_comp_satff_salary_daybyday '001','20130301', '20130331'  --工资日结
  go
 exec upg_compute_comp_satff_salary_daybyday '001','20130401', '20130430'  --工资日结
  go
 exec upg_compute_comp_satff_salary_daybyday '001','20130501', '20130531'  --工资日结
  go
 exec upg_compute_comp_satff_salary_daybyday '001','20130601', '20130630'  --工资日结
  go
 exec upg_compute_comp_satff_salary_daybyday '001','20130701', '20130731'  --工资日结
  go
 exec upg_compute_comp_satff_salary_daybyday '001','20130801', '20130831'  --工资日结
  go
 exec upg_compute_comp_satff_salary_daybyday '001','20130901', '20130930'  --工资日结
  go
 exec upg_compute_comp_satff_salary_daybyday '001','20131001', '20131031'  --工资日结
  go
 exec upg_compute_comp_satff_salary_daybyday '001','20131101', '20131131'  --工资日结
  go
 exec upg_compute_comp_satff_salary_daybyday '001','20131201', '20131231'  --工资日结
 

