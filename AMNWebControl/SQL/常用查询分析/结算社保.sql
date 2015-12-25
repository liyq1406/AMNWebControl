
    delete staff_work_salary_jiesuan
	declare @tocompid varchar(10)  
	declare cur_each_comp cursor for  
	select curcompno from compchainstruct where complevel=4 and curcompno in ('035','036','038','043','045') order by  curcompno
	open cur_each_comp  
	fetch cur_each_comp into @tocompid  
	while @@fetch_status = 0  
	begin  
		exec upg_prepare_ticheng_analysis_after_jiesuan @tocompid,'20140201','20140228' 
		fetch cur_each_comp into @tocompid  
	end  
	close cur_each_comp  
	deallocate cur_each_comp 
	select 	strCompId,strCompName	,staffno,	staffinid,	staffname,	staffpositionname,	staffsocialsource,	staffpcid,	staffbankaccountno,
		workdays,	stafftotalyeji,	staffshopyeji,	staffbasesalary,	beatysubsidy,	staffsubsidy,	storesubsidy,	staffreward,
		staffamtchange,	basestaffpayamt,	leaveldebit,	latdebit,	staffdaikou,	staffdebit,	needpaysalary,	staffsocials,
			staydebit,	studydebit,	staffcost,	salarydebit,	factpaysalary
	 from staff_work_salary_jiesuan order by strCompId,strCompName
	 
	 
	 	select 		convert(numeric(20,1),sum(isnull(needpaysalary,0)))	,	convert(numeric(20,5),sum(isnull(factpaysalary,0)))
	 from staff_work_salary_jiesuan

	 
	
																									
  --insert staff_work_salary_sebao(strCompId,strCompName,staffno,staffinid,staffname,staffposition,staffpositionname,staffpcid,staffbankaccountno,workdays,computedays,          
  --       stafftotalyeji,staffshopyeji,staffbasesalary,beatysubsidy,leaveldebit,staffsubsidy,staffdebit,staffdaikou,latdebit,staffcost,          
  --       staffreward,otherdebit,staffsocials,needpaysalary,staydebit,studydebit,salarydebit,factpaysalary,staffmark ,      
  --       basestaffamt,staffsocialsource,staffamtchange,stafftargetreward,basestaffpayamt,storesubsidy ,markflag )    
  
  
  
 -- create table staff_work_salary_jiesuan     
 -- (      
	--strCompId				varchar(10)		null,   --门店编号      
	--strCompName				varchar(10)		null,   --门店名称  
	--staffno					varchar(20)		null,   --员工编号      
	--staffinid				varchar(20)		null,   --员工内部编号 
	--arrivaldate				varchar(8 )		NULL    ,--到职日期     
	--staffname				varchar(30)		null,   --员工名称      
	--staffposition			varchar(10)		null,   --员工职位    
	--staffsocialsource		varchar(20)		null,	--社保所属  
	--staffpcid				varchar(20)		null,   --员工身份证      
	--staffmark				varchar(300)	null,   --员工备注      
	--staffbankaccountno		varchar(30)		null,   --员工银行账号      
	--resulttye				varchar(5)		NULL,	--业绩方式 0--额度方式 -美发虚业绩  2 美发实业绩      
	--resultrate				float			NULL,	--业绩系数      
	--baseresult				float			NULL,   --业绩基数      
	--computedays				int				NULL,   --计算天数      
	--workdays				int				NULL,   --缺勤天数    
	--inserworkdays			int				NULL,   --业绩天数    
	--stafftotalyeji			float			null,   --员工总业绩      
	--staffshopyeji			float			null,   --员工门店业绩      
	--salaryflag				int				null,   --税前 ,税后       
	--staffbasesalary			float			null,   --员工基本工资      
	--beatysubsidy			float			null,   --美容补贴      
	--leaveldebit				float			null,   --离职扣款   
	--basestaffamt			float			null,   --关爱基金   
	--basestaffpayamt			float			null,   --关爱基金支付  
	--staffsubsidy			float			null,   --员工补贴  
	--storesubsidy			float			null,   --门店补贴(饭贴+门店补贴)      
	--staffdebit				float			null,   --罚款
	--staffdaikou				float			null,   --代扣
	--latdebit				float			null,   --迟到      
	--staffreward				float			null,   --奖励 
	--yearreward				float			null,	--年终奖
	--staffamtchange			float			null,   --差异调整   
	--stafftargetreward		float			null,   --指标奖励     
	--otherdebit				float			null,   --其他扣款      
	--staffsocials			float			null,   --社保      
	--needpaysalary			float			null,   --应付工资      
	--staydebit				float			null,   --住宿      
	--studydebit				float			null,   --学习费用      
	--staffcost				float			null,   --成本      
	--salarydebit				float			null,   --扣税      
	--factpaysalary			float			null,   --应付工资      
	--staffpositionname		varchar(30)		null,   --员工职位  
	--absencesalary			int				NULL,	--缺勤底薪算法    
	--markflag				int				NULL,	--标记
 -- )       
       
 
 
 create table staff_work_salary_sum     
  (      
	strCompId				varchar(10)		null,   --门店编号      
	strCompName				varchar(10)		null,   --门店名称  
	staffno					varchar(20)		null,   --员工编号      
	staffinid				varchar(20)		null,   --员工内部编号 
	arrivaldate				varchar(8 )		NULL    ,--到职日期     
	staffname				varchar(30)		null,   --员工名称      
	staffposition			varchar(10)		null,   --员工职位    
	staffsocialsource		varchar(20)		null,	--社保所属  
	staffpcid				varchar(20)		null,   --员工身份证      
	staffmark				varchar(300)	null,   --员工备注      
	staffbankaccountno		varchar(30)		null,   --员工银行账号      
	resulttye				varchar(5)		NULL,	--业绩方式 0--额度方式 -美发虚业绩  2 美发实业绩      
	resultrate				float			NULL,	--业绩系数      
	baseresult				float			NULL,   --业绩基数      
	computedays				int				NULL,   --计算天数      
	workdays				int				NULL,   --缺勤天数    
	inserworkdays			int				NULL,   --业绩天数    
	stafftotalyeji			float			null,   --员工总业绩      
	staffshopyeji			float			null,   --员工门店业绩      
	salaryflag				int				null,   --税前 ,税后       
	staffbasesalary			float			null,   --员工基本工资      
	beatysubsidy			float			null,   --美容补贴      
	leaveldebit				float			null,   --离职扣款   
	basestaffamt			float			null,   --关爱基金   
	basestaffpayamt			float			null,   --关爱基金支付  
	staffsubsidy			float			null,   --员工补贴  
	storesubsidy			float			null,   --门店补贴(饭贴+门店补贴)      
	staffdebit				float			null,   --罚款
	staffdaikou				float			null,   --代扣
	latdebit				float			null,   --迟到      
	staffreward				float			null,   --奖励 
	yearreward				float			null,	--年终奖
	staffamtchange			float			null,   --差异调整   
	zerenjinback			float			null,   --责任金返还
	zerenjincost			float			null,   --责任金扣款
	stafftargetreward		float			null,   --指标奖励     
	otherdebit				float			null,   --其他扣款      
	staffsocials			float			null,   --社保      
	needpaysalary			float			null,   --应付工资      
	staydebit				float			null,   --住宿      
	studydebit				float			null,   --学习费用      
	staffcost				float			null,   --成本      
	salarydebit				float			null,   --扣税      
	factpaysalary			float			null,   --应付工资      
	staffpositionname		varchar(30)		null,   --员工职位  
	absencesalary			int				NULL,	--缺勤底薪算法  
	staffcurstate			int				null,	--员工在职状态  
	markflag				int				NULL,	--标记
  )       