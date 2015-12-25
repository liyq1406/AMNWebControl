	
	--declare @tocompid varchar(10)  
	--declare cur_each_comp cursor for  
	--select curcompno from compchainstruct where complevel=4 
	--and curcompno  in ('035','036','038','043','045','301','302','303') order by  curcompno
	--open cur_each_comp  
	--fetch cur_each_comp into @tocompid  
	--while @@fetch_status = 0  
	--begin  
	--	exec upg_prepare_ticheng_analysis_after_Sum @tocompid,'20140201','20140228' 
	--	fetch cur_each_comp into @tocompid  
	--end  
	--close cur_each_comp  
	--deallocate cur_each_comp 
	
	select strCompId,strCompName,
		 SUM(convert(numeric(20,0),case  when  ISNULL(staffcurstate,0)=2 then ISNULL(factpaysalary,0) else 0 end) ),
		 SUM(convert(numeric(20,0),case  when  ISNULL(staffcurstate,0)=4  then ISNULL(factpaysalary,0) else 0 end )),
		 SUM(convert(numeric(20,0),case  when  ISNULL(staffcurstate,0)=3  then ISNULL(factpaysalary,0) else 0 end ))
	 from compute_staff_work_salary_byday 
	 where  computeday='20140301'
	 group by  strCompId,strCompName
	 order by strCompId
	
	--门店总额
	select strCompId,strCompName,
		SUM(convert(numeric(20,0),case  when  ISNULL(staffcurstate,2)=2 and ISNULL(staffsocials,0)>0  and ISNULL(staffsocialsource,'')<>'南昌分公司' then ISNULL(factpaysalary,0) else 0 end )),
		SUM(convert(numeric(20,0),case  when  ISNULL(staffcurstate,2)=2 and ISNULL(staffsocials,0)=0 and strCompId not in   ('035','036','038','043','045','301','302','303') then ISNULL(factpaysalary,0) else 0 end )),
		SUM(convert(numeric(20,0),case  when  ISNULL(staffcurstate,2)=4  and ISNULL(staffsocialsource,'')<>'南昌分公司' and strCompId not in   ('035','036','038','043','045','301','302','303') then ISNULL(factpaysalary,0) else 0 end )),
		SUM(convert(numeric(20,0),case  when  ISNULL(staffcurstate,2)=3  and ISNULL(staffsocialsource,'')<>'南昌分公司' and strCompId not in   ('035','036','038','043','045','301','302','303') then ISNULL(factpaysalary,0) else 0 end ))
	 from compute_staff_work_salary_byday 
	 where  computeday='20140301' 
	 group by  strCompId,strCompName
	 order by strCompId
	


	 
	 --有社保,有银行卡,在职(8000内)
	 select strCompId,strCompName,staffno,staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno
	 ,factpaysalary= convert(numeric(20,0),case when isnull(needpaysalary,0)>8000 then   convert(numeric(20,0),isnull(factpaysalary,0))-convert(numeric(20,0),(isnull(needpaysalary,0)-8000)) else isnull(factpaysalary,0) end) 
	 from compute_staff_work_salary_byday 
	 where  computeday='20140301' and ISNULL(staffsocials,0)>0 and len(ISNULL(staffbankaccountno,''))>10 and ISNULL(staffcurstate,2)=2   and isnull(factpaysalary,0)>0 and ISNULL(staffsocialsource,'')<>'南昌分公司'
 	  --and strCompId not in   ('035','036','038','043','045','301','302','303')
 	 order by staffsocialsource,strCompId,strCompName 
 	 
 	 
 	  --有社保,有银行卡,在职(8000外补发)
	 select strCompId,strCompName,staffno,staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno
	 ,factpaysalary=convert(numeric(20,0),(isnull(needpaysalary,0)-8000))
	 from compute_staff_work_salary_byday 
	 where computeday='20140301' and ISNULL(staffsocials,0)>0 and len(ISNULL(staffbankaccountno,''))>10 and ISNULL(staffcurstate,2)=2 and  isnull(needpaysalary,0)>8000  and ISNULL(staffsocialsource,'')<>'南昌分公司'
 	  -- and strCompId not in   ('035','036','038','043','045','301','302','303')
 	 order by staffsocialsource,strCompId,strCompName 
 	 
 	 --无社保,有银行卡,在职
 	  select strCompId,strCompName,staffno,staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno,convert(numeric(20,0),isnull(factpaysalary,0))
		 from compute_staff_work_salary_byday 
	 where computeday='20140301' and  ISNULL(staffsocials,0)=0 and len(ISNULL(staffbankaccountno,''))>10 and ISNULL(staffcurstate,2)=2  and isnull(factpaysalary,0)>0
 	   and strCompId not in   ('035','036','038','043','045','301','302','303')
 	 order by strCompId,strCompName 
 	 

 	 

 	 --离职 有银行卡 有社保(8000内)
	 select strCompId,strCompName,staffno,staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno
	 ,factpaysalary= convert(numeric(20,0),case when isnull(needpaysalary,0)>8000 then   convert(numeric(20,0),isnull(factpaysalary,0))-convert(numeric(20,0),(isnull(needpaysalary,0)-8000)) else isnull(factpaysalary,0) end) 
	 from compute_staff_work_salary_byday 
	 where computeday='20140301' and ISNULL(staffsocials,0)>0 and len(ISNULL(staffbankaccountno,''))>10 and ISNULL(staffcurstate,0)=3   and isnull(factpaysalary,0)>0
 	  and ISNULL(staffsocialsource,'')<>'南昌分公司'
 	 order by staffsocialsource,strCompId,strCompName 
 	 
 	 
 	  --离职 有社保,有银行卡(8000外补发)
	 select strCompId,strCompName,staffno,staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno
	 ,factpaysalary=convert(numeric(20,0),(isnull(needpaysalary,0)-8000))
	 from compute_staff_work_salary_byday 
	 where computeday='20140301' and ISNULL(staffsocials,0)>0 and len(ISNULL(staffbankaccountno,''))>10 and ISNULL(staffcurstate,0)=3 and  isnull(needpaysalary,0)>8000 
 	 and ISNULL(staffsocialsource,'')<>'南昌分公司'
 	 order by staffsocialsource,strCompId,strCompName 
 	 
 	  --无社保,有银行卡,离职
 	  select strCompId,strCompName,staffno,staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno,convert(numeric(20,0),isnull(factpaysalary,0))
	 from compute_staff_work_salary_byday 
	 where computeday='20140301' and ISNULL(staffsocials,0)=0 and len(ISNULL(staffbankaccountno,''))>10 and ISNULL(staffcurstate,0)=3  and isnull(factpaysalary,0)>0
 	 and strCompId not in   ('035','036','038','043','045','301','302','303')
 	 order by strCompId,strCompName 
 	 
 	 --在职 无银行卡 
 	  select strCompId,strCompName,staffno,staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno,convert(numeric(20,0),isnull(factpaysalary,0))
	 from compute_staff_work_salary_byday 
	 where computeday='20140301'  and len(ISNULL(staffbankaccountno,''))<10 and ISNULL(staffcurstate,2)=2   and isnull(factpaysalary,0)>0
 	 	 and strCompId not in   ('035','036','038','043','045','301','302','303')
 	 order by strCompId,strCompName 
 	 

 	 
 	 
 	  --离职 无银行卡
 	  select strCompId,strCompName,staffno,staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno,convert(numeric(20,0),isnull(factpaysalary,0))
	 from compute_staff_work_salary_byday 
	 where computeday='20140301' and  len(ISNULL(staffbankaccountno,''))<10 and ISNULL(staffcurstate,0)=3  and isnull(factpaysalary,0)>0
 	and strCompId not in   ('035','036','038','043','045','301','302','303')
 	 order by strCompId,strCompName 
 	 
 	 --待发
 	 select strCompId,strCompName,staffno,staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno,convert(numeric(20,0),isnull(factpaysalary,0))
	 from compute_staff_work_salary_byday 
	 where computeday='20140301' and  ISNULL(staffcurstate,0)=4  and isnull(factpaysalary,0)>0
 	and strCompId not in   ('035','036','038','043','045','301','302','303')
 	 order by strCompId,strCompName 
 	 
 	 
		
		
		 --有社保
	 select 门店编号=strCompId,门店名称=strCompName,总个人业绩=convert(numeric(20,2),sum(isnull(stafftotalyeji,0))),
								  总门店业绩=convert(numeric(20,2),sum(isnull(staffshopyeji,0))),
								  总底薪=convert(numeric(20,2),sum(isnull(staffbasesalary,0))),
								  总美容师补贴=convert(numeric(20,2),sum(isnull(beatysubsidy,0))),
								  总保底补贴=convert(numeric(20,2),sum(isnull(staffsubsidy,0))),
								  总门店补贴=convert(numeric(20,2),sum(isnull(storesubsidy,0))),
								  总奖励=convert(numeric(20,2),sum(isnull(staffreward,0))),
								  总差异调整=convert(numeric(20,2),sum(isnull(staffamtchange,0))),
								  总离职扣款=convert(numeric(20,2),sum(isnull(leaveldebit,0))),
								  总迟到扣款=convert(numeric(20,2),sum(isnull(latdebit,0))),
								  总员工代扣=convert(numeric(20,2),sum(isnull(staffdaikou,0))),
								  总罚款=convert(numeric(20,2),sum(isnull(staffdebit,0))),
								  总社保=convert(numeric(20,2),sum(isnull(staffsocials,0))),
								  总住宿费=convert(numeric(20,2),sum(isnull(staydebit,0))),
								  总学习费=convert(numeric(20,2),sum(isnull(studydebit,0))),
								  总成本=convert(numeric(20,2),sum(isnull(staffcost,0))),
								  总扣税=convert(numeric(20,2),sum(isnull(salarydebit,0))),
								  总责任金返回=convert(numeric(20,2),sum(isnull(zerenjinback,0))),
								  总责任金扣款=convert(numeric(20,2),sum(isnull(zerenjincost,0))),
								  总工资=convert(numeric(20,2),sum(isnull(factpaysalary,0)))
	 from compute_staff_work_salary_byday 
	 where computeday='20140301'  and ISNULL(factpaysalary,0)>0
	 group by strCompId,strCompName
 	 order by strCompId
 	 
 	 select 门店编号=strCompId,门店名称=strCompName,b.staffno,b.staffname,b.pccid,
 								  总个人业绩=convert(numeric(20,2),(isnull(stafftotalyeji,0))),
								  总门店业绩=convert(numeric(20,2),(isnull(staffshopyeji,0))),
								  总底薪=convert(numeric(20,2),(isnull(staffbasesalary,0))),
								  总美容师补贴=convert(numeric(20,2),(isnull(beatysubsidy,0))),
								  总保底补贴=convert(numeric(20,2),(isnull(staffsubsidy,0))),
								  总门店补贴=convert(numeric(20,2),(isnull(storesubsidy,0))),
								  总奖励=convert(numeric(20,2),(isnull(staffreward,0))),
								  老客提成=convert(numeric(20,2),(isnull(oldcustomerreward,0))),
								  总差异调整=convert(numeric(20,2),(isnull(staffamtchange,0))),
								  总离职扣款=convert(numeric(20,2),(isnull(leaveldebit,0))),
								  总迟到扣款=convert(numeric(20,2),(isnull(latdebit,0))),
								  总员工代扣=convert(numeric(20,2),(isnull(staffdaikou,0))),
								  总罚款=convert(numeric(20,2),(isnull(staffdebit,0))),
								  社保名称=isnull(staffsocialsource,0),
								  总社保=convert(numeric(20,2),(isnull(staffsocials,0))),
								  总住宿费=convert(numeric(20,2),(isnull(staydebit,0))),
								  总学习费=convert(numeric(20,2),(isnull(studydebit,0))),
								  总成本=convert(numeric(20,2),(isnull(staffcost,0))),
								  总扣税=convert(numeric(20,2),(isnull(salarydebit,0))),
								  总责任金返回=convert(numeric(20,2),(isnull(zerenjinback,0))),
								  总责任金扣款=convert(numeric(20,2),(isnull(zerenjincost,0))),
								  总工资=convert(numeric(20,2),(isnull(factpaysalary,0)))
	 from compute_staff_work_salary_byday a,staffinfo b
	 where computeday='20140601'  and a.staffinid=b.manageno and ISNULL(b.stafftype,0)=0
 	 order by strCompId
 	 
 	 
 	  --无社保,有银行卡,在职(8000外补发)
	 select  strCompId,strCompName,staffno,staffname,staffpositionname,staffsocialsource,staffpcid,staffbankaccountno,
		workdays,stafftotalyeji,staffshopyeji,staffbasesalary,beatysubsidy,staffsubsidy,storesubsidy,staffreward,
		staffamtchange,basestaffpayamt,leaveldebit,latdebit,staffdaikou,staffdebit,needpaysalary=convert(numeric(20,0),isnull(needpaysalary,0)),staffsocials,
		staydebit,studydebit,staffcost,salarydebit,zerenjinback,zerenjincost,factpaysalary=convert(numeric(20,0),isnull(factpaysalary,0))
	 from compute_staff_work_salary_byday 
	 where computeday='20140301'  ISNULL(staffsocials,0)=0
 	 order by staffsocialsource,strCompId,strCompName