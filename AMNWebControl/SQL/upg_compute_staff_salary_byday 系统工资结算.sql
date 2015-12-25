
  --create table compute_staff_work_salary_byday     
  --(  
		--computeday				varchar(10)		null,	--计算日期
		--strCompId				varchar(10)		null,   --门店编号      
		--strCompName				varchar(10)		null,   --门店名称  
		--staffno					varchar(20)		null,   --员工编号      
		--staffinid				varchar(20)		null,   --员工内部编号 
		--arrivaldate				varchar(8 )		NULL,	--到职日期     
		--staffname				varchar(30)		null,   --员工名称      
		--staffposition			varchar(10)		null,   --员工职位    
		--staffsocialsource		varchar(20)		null,	--社保所属  
		--staffpcid				varchar(20)		null,   --员工身份证      
		--staffmark				varchar(300)	null,   --员工备注      
		--staffbankaccountno		varchar(30)		null,   --员工银行账号   
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
		--afterstaffreward		float			null,   --奖励 
		--yearreward				float			null,	--年终奖
		--staffamtchange			float			null,   --差异调整   
		--zerenjinback			float			null,   --责任金返还
		--zerenjincost			float			null,   --责任金扣款
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
		--staffcurstate			int				null,	--员工在职状态  
		--businessflag			int				NULL    ,--是否为业务人员 0--不是 1--是
		--markflag				int				NULL,	--标记
		--stafftype				int				NULL	,-- 0 正常 1 派遣
  --) 
  
alter procedure upg_compute_staff_salary_byday 
(  
 @compid   varchar(10),  --门店号   
 @datefrom   varchar(10), --起始日期  
 @dateto   varchar(10)   --结束日期  
)    
as  
begin  
	declare @tocompid varchar(10)  
	declare cur_each_comp cursor for  
	select relationcomp from compchaininfo where curcomp=@compid   
	open cur_each_comp  
	fetch cur_each_comp into @tocompid  
	while @@fetch_status = 0  
	begin
	
	    exec upg_prepare_ticheng_analysis_after_byday @tocompid,@datefrom,@dateto
		fetch cur_each_comp into @tocompid  
	end  
	close cur_each_comp  
	deallocate cur_each_comp  
end  
go

--exec upg_compute_staff_salary_byday '001','20140301','20140331'
select * from compute_staff_work_salary_byday,commoninfo 
where staffsocials>0 and infotype='SBGS' and staffsocialsource=parentcodekey

 exec upg_compute_staff_salary_byday '00101','20140301','20140331' 