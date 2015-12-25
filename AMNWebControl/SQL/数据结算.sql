 use MasterDatabase2014
 go
 declare @yesterday datetime                          
 declare @curdate varchar(20)                        
 select @yesterday = getdate()-1  
 select @curdate=substring(convert(varchar(20),@yesterday,102),1,4) + substring(convert(varchar(20),@yesterday,102),6,2)+ substring(convert(varchar(20),@yesterday,102),9,2)
 declare @beforedate varchar(10)
 set @beforedate= SUBSTRING(@curdate,1,6)+'01'
 exec upg_compute_comp_trade_payinfo_daybyday '001',@beforedate, @curdate --支付方式日结
 exec upg_compute_comp_detial_trade '001',@beforedate, @curdate		--日记账日结
 exec upg_compute_comp_classed_trade_daybyday '001',@beforedate, @curdate  --分类日记账日结
 exec upg_hand_personManger_byday @beforedate, @curdate
 exec upg_compute_comp_satff_salary_daybyday '001',@beforedate, @curdate   --工资日结


style="background-attachment fixed; background-image:url(loding.jpg); background-repeat:no-repeat; "