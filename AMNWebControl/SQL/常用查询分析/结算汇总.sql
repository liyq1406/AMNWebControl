 create table staff_work_salary_CW_sum    
  (      
	strCompId				varchar(10)		null,   --门店编号      
	strCompName				varchar(10)		null,   --门店名称  
	factpaysalary			float			null,   --在职工资
	levelpaysalary			float			null,   --离职职工资
	daifapaysalary			float			null   --待发工资
  )       
       
       
     delete staff_work_salary_CW_sum
	declare @tocompid varchar(10)  
	declare cur_each_comp cursor for  
	select curcompno from compchainstruct where complevel=4 
	and curcompno not in ('035','036','038','043','045','301','302','303') order by  curcompno
	open cur_each_comp  
	fetch cur_each_comp into @tocompid  
	while @@fetch_status = 0  
	begin  
		exec upg_prepare_ticheng_analysis_after_CW_sum @tocompid,'20140201','20140228' 
		fetch cur_each_comp into @tocompid  
	end  
	close cur_each_comp  
	deallocate cur_each_comp 
	
update  staff_work_salary_CW_sum set strCompName=compname
from staff_work_salary_CW_sum,companyinfo 
where strCompId=compno


select * from staff_work_salary_CW_sum where ISNULL(factpaysalary,0)>0
order by strCompId