alter procedure upg_prepare_ticheng_analysis(              
 @compid    varchar(10), -- 公司别              
 @fromdate   varchar(10), -- 开始日期              
 @todate    varchar(10)  -- 截至日期    
)     
as    
begin    
  create table #staff_work_salary    
  (    
   strCompId    varchar(10)  null,      --门店编号    
   staffno     varchar(20)  null,      --员工编号    
   staffinid    varchar(20)  null,      --员工内部编号    
   staffname    varchar(30)  null,      --员工名称    
   staffposition   varchar(10)  null,      --员工职位    
   staffpcid    varchar(20)  null,      --员工身份证    
   staffmark    varchar(300) null,      --员工备注    
   staffbankaccountno  varchar(30)  null,      --员工银行账号    
   resulttye    varchar(5)  NULL,      --业绩方式 0--额度方式 -美发虚业绩  2 美发实业绩    
   resultrate    float   NULL,      --业绩系数    
   baseresult    float   NULL,      --业绩基数    
   computedays    int    NULL,      --计算天数    
   workdays    int    NULL,      --工作天数    
   stafftotalyeji   float   null,      --员工总业绩    
   staffshopyeji   float   null,      --员工门店业绩    
   salaryflag    int    null,      --税前 ,税后     
   staffbasesalary   float   null,      --员工基本工资    
   beatysubsidy   float   null,      --美容补贴    
   leaveldebit    float   null,      --离职扣款    
   staffsubsidy   float   null,      --补贴    
   staffdebit    float   null,      --扣款    
   latdebit    float   null,      --吃迟到    
   staffreward    float   null,      --奖励    
   otherdebit    float   null,      --其他扣款    
   staffsocials   float   null,      --社保    
   needpaysalary   float   null,      --应付工资    
   staydebit    float   null,      --住宿    
   studydebit    float   null,      --学习费用    
   staffcost    float   null,      --成本    
   salarydebit    float   null,      --扣税    
   factpaysalary   float   null,      --应付工资    
   staffpositionname  varchar(30)  null,      --员工职位    
  )     
     
       
  insert #staff_work_salary(strCompId,staffinid,stafftotalyeji,workdays,computedays)    
  select compid,person_inid,SUM(ISNULL(staffyeji,0)) ,COUNT(distinct salary_date),datediff(day,@fromdate,@todate)+1    
  from staff_work_salary     
  where compid=@compid and salary_date between @fromdate and @todate group by  compid,person_inid    
      
  insert #staff_work_salary(strCompId,staffinid,stafftotalyeji,workdays,computedays)    
  select @compid,manageno,0,0,datediff(day,@fromdate,@todate)+1    
  from staffhistory where effectivedate between @fromdate and @todate and oldcompid=@compid    
  and manageno not in ( select person_inid from staff_work_salary     
  where compid=@compid and salary_date between @fromdate and @todate)    
  group by manageno    
  --添加没有业绩的人员    
  insert #staff_work_salary(strCompId,staffinid,stafftotalyeji)    
  select compno,Manageno,0 from staffinfo where Manageno not in (select staffinid from #staff_work_salary) and compno=@compid    
  group by compno,Manageno    
      
  insert #staff_work_salary(strCompId,staffinid,stafftotalyeji)    
  select oldcompid,Manageno,0 from staffhistory where Manageno not in (select staffinid from #staff_work_salary) and oldcompid=@compid and effectivedate>@todate    
  group by  oldcompid,Manageno    
      
      
  update a set a.staffno=b.staffno,a.staffname=b.staffname,a.staffposition=b.position,a.staffpcid=b.pccid,a.staffbankaccountno=b.bankno,    
  --a.staffbasesalary=isnull(b.basesalary,0)*ISNULL(workdays,0)/ISNULL(computedays,1) ,    
  a.staffbasesalary=isnull(b.basesalary,0) ,    
      a.resulttye=b.resulttye,a.resultrate=b.resultrate,a.baseresult=b.baseresult,    
      a.salaryflag=b.salaryflag,a.staffsocials=b.socialsecurity,a.staffmark=b.remark    
  from #staff_work_salary a,staffinfo b     
  where a.staffinid=b.Manageno     
      
   --计算门店业绩提成（管理层）                                                                  
  declare @beaut_yeji  float                                                                  
  declare @hair_yeji  float                              
  declare @trh_yeji  float          
  declare @trh_yejifact   float                
  declare @total_yeji  float        
  select @beaut_yeji=sum(isnull(beautyeji,0)),@hair_yeji=sum(isnull(hairyeji,0)),@trh_yeji=sum(isnull(footyeji,0)),@total_yeji=sum(isnull(totalyeji,0)) ,@trh_yejifact=sum(isnull(realfootyeji,0))    
  from compclasstraderesult  where compid=@compid and ddate between @fromdate and @todate    
      
  --烫染总监 008 拿的是烫染业绩基数    
  update   #staff_work_salary set staffshopyeji=(ISNULL(@trh_yeji,0) -isnull(baseresult,0))*0.02                        
   where  staffposition='008' and ISNULL(@trh_yeji,0) >=isnull(baseresult,0)           
   --烫染总监 00801 拿的是烫染业绩基数      
   update   #staff_work_salary set staffshopyeji=ISNULL(@trh_yejifact,0)*0.02                        
   where  staffposition='00801'       
   update   #staff_work_salary set staffshopyeji=isnull(staffshopyeji,0)+2000                
   where  staffposition='00801' and ISNULL(@trh_yeji,0) >=isnull(baseresult,0)      
                                                       
         --其他管理层来的是分享业绩    
   update #staff_work_salary                                                                   
   set staffshopyeji = case when isnull(resulttye,'')='1' then isnull(@hair_yeji,0)*isnull(resultrate,0)                                                              
   when isnull(resulttye,'')='2' then isnull(@beaut_yeji,0)*isnull(resultrate,0)     
   when isnull(resulttye,'')='3' then isnull(@trh_yeji,0)*isnull(resultrate,0)               
   when isnull(resulttye,'')='4' then isnull(@total_yeji,0)*isnull(resultrate,0) else 0 end      
     where   staffposition<>'008'       
            
     
        --美容师补贴                      
  --美容师入职超过一年每月补贴200, 两年 400 (七天没上班没有补贴)                      
  update a                                       
  set beatysubsidy=isnull((case when DATEDIFF ( day, arrivaldate ,ISNULL(@fromdate,'') ) between 364 and 727 then 200                  
    when DATEDIFF ( day, arrivaldate ,ISNULL(@fromdate,'') )> 727 then 400                      
     else 0 end),0)                      
  from #staff_work_salary a,staffinfo  b                      
  where a.staffinid=b.Manageno and b.position in ('004','00401','00402') and isnull(a.workdays,0)>23   and ISNULL(curstate,0)=2       
      
  --收银小单提成    
     declare @ccount int     
   select @ccount=COUNT(distinct csbillid)-2000 from mconsumeinfo with(nolock)         
  where cscompid=@compid         
   and financedate between @fromdate and @todate       
   and ISNULL(backcsflag,0)=0        
   and ISNULL(backcsbillid,'')=''         
         
   if(@ccount>0)        
   begin        
   update #staff_work_salary set staffshopyeji=200+isnull(staffshopyeji,0)+ISNULL(@ccount,0)*0.1 where staffposition in('01201','01202')        
   end         
   set @ccount=0    
    select @ccount=count(distinct a.csbillid)-300        
   from mconsumeinfo a with(nolock),dconsumeinfo b with(nolock),projectnameinfo c        
   where a.cscompid=b.cscompid        
     and a.csbillid=b.csbillid    
     and csitemno=prjno        
     and prjpricetype=1        
     and prjtype='4'        
     and a.cscompid=@compid        
     and a.financedate between @fromdate and @todate        
      and ISNULL(backcsflag,0)=0        
   and ISNULL(backcsbillid,'')=''             
          
           
   if(@ccount>0)        
   begin        
   update #staff_work_salary set staffshopyeji=isnull(staffshopyeji,0)+ISNULL(@ccount,0)*2 where staffposition='002'        
   end     
   update #staff_work_salary set staffshopyeji=isnull(staffshopyeji,0)+ISNULL(@ccount,0)*2 where staffposition='002'     
   --罚款                                     
                
  update #staff_work_salary                                       
  set staffdebit=isnull((select sum(isnull(checkrewardamt,0)) from staffrewardinfo where handcompid=@compid and checkflag=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype='02' ),0)                               
   
   
                  
         --奖励                                    
                
  update #staff_work_salary                                       
  set staffreward=isnull((select sum(isnull(checkrewardamt,0)) from staffrewardinfo where handcompid=@compid and checkflag=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype='03' ),0)                               
  
    
      
              
  --迟到早退                                     
                
  update #staff_work_salary                                       
  set latdebit=isnull((select sum(isnull(checkrewardamt,0)) from staffrewardinfo where handcompid=@compid and checkflag=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype='04' ),0)                                  
 
      
  --其他扣款                                    
                
  update #staff_work_salary                                       
  set otherdebit=isnull((select sum(isnull(checkrewardamt,0)) from staffrewardinfo where handcompid=@compid and checkflag=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype='05' ),0)                                
  
   
      
      
  --学习费                                     
                
  update #staff_work_salary                                       
  set studydebit=isnull((select sum(isnull(checkrewardamt,0)) from staffrewardinfo where handcompid=@compid and checkflag=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype='09' ),0)                                
  
   
                          
  --口成本                                     
                
  update #staff_work_salary                                       
  set staffcost=isnull((select sum(isnull(checkrewardamt,0)) from staffrewardinfo where handcompid=@compid and checkflag=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype='08' ),0)                                 
  
                           
  ----住宿                                    
  update #staff_work_salary                                       
  set staydebit=isnull((select sum(isnull(checkrewardamt,0)) from staffrewardinfo where handcompid=@compid and checkflag=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype='07' ),0)                                 
  
      
  --补贴                                    
                
  update #staff_work_salary                                       
  set staffsubsidy=isnull((select sum(isnull(checkrewardamt,0)) from staffrewardinfo where handcompid=@compid and checkflag=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype='06' ),0)                              
  
     
      
     ----离职异动单增加离职种类 正常离职,自动离职, 自动离职需扣款600                                   
  update #staff_work_salary set leaveldebit=600  where staffinid in (select staffmangerno from staffchangeinfo where changetype=1 and leaveltype=2 and validatestartdate  between @fromdate and @todate )                    
  update #staff_work_salary     
      set needpaysalary=ISNULL(stafftotalyeji,0)+ ISNULL(staffshopyeji,0)+    
         ISNULL(staffbasesalary,0)+ISNULL(beatysubsidy,0)-    
         ISNULL(leaveldebit,0)+ISNULL(staffsubsidy,0)-    
         ISNULL(staffdebit,0)-ISNULL(latdebit,0)+    
         ISNULL(staffreward,0)-ISNULL(otherdebit,0)-    
         ISNULL(staffsocials,0),    
    factpaysalary=ISNULL(stafftotalyeji,0)+ ISNULL(staffshopyeji,0)+    
         ISNULL(staffbasesalary,0)+ISNULL(beatysubsidy,0)-    
         ISNULL(leaveldebit,0)+ISNULL(staffsubsidy,0)-    
         ISNULL(staffdebit,0)-ISNULL(latdebit,0)+    
         ISNULL(staffreward,0)-ISNULL(otherdebit,0)-    
         ISNULL(staffsocials,0)    
   declare @total_salary float,@deduct_tax int,@empid_ex varchar(20),@inid_ey varchar(20)  ,@sebao float                                                                
   declare cur_emp_salary cursor for                                                                  
   select staffinid,staffno,needpaysalary,salaryflag,staffsocials from #staff_work_salary                                                           
   open cur_emp_salary                                                                  
   fetch from cur_emp_salary into @inid_ey,@empid_ex,@total_salary,@deduct_tax ,@sebao                                                                 
   while(@@fetch_status=0)                                                                  
   begin                      
    if(ISNULL(@sebao,0)>0)                          
    begin                                                              
     declare @tmpTax float, @tmpIncome float                                                                  
     set @tmpTax=0                                                                  
     set @tmpIncome=0                                                                  
     if(isnull(@deduct_tax,0)=0)                                                                  
     begin                                  
     if(@total_salary>8000)                                                        
     exec upg_caculate_personal_tax 3500,8000,@tmpTax output,@tmpIncome output                                                                  
     else                                                      
     exec upg_caculate_personal_tax 3500,@total_salary,@tmpTax output,@tmpIncome output                              
     update #staff_work_salary                                                                  
     set salarydebit = -1*isnull(@tmpTax,0),factpaysalary=isnull(needpaysalary,0)-isnull(@tmpTax,0)                                                                                     
   where staffinid = @inid_ey                                                 
                                                 
     end                                                   
    end                                     
    fetch from cur_emp_salary into @inid_ey,@empid_ex,@total_salary,@deduct_tax,@sebao                                                                  
   end                                                                  
   close cur_emp_salary                                                                  
   deallocate cur_emp_salary      
       
   update #staff_work_salary set staffpositionname=parentcodevalue    
   from commoninfo,#staff_work_salary    
   where parentcodekey=staffposition and infotype='GZGW'    
     
  update #staff_work_salary set factpaysalary=factpaysalary-ISNULL(staydebit,0)-ISNULL(studydebit,0)-ISNULL(staffcost,0)    
            
  select strCompId,staffno,staffinid,staffname,staffposition,staffpositionname,staffpcid,staffbankaccountno,workdays,computedays,    
         stafftotalyeji,staffshopyeji,staffbasesalary,beatysubsidy,leaveldebit,staffsubsidy,staffdebit,latdebit,staffcost,    
         staffreward,otherdebit,staffsocials,needpaysalary,staydebit,studydebit,salarydebit,factpaysalary,staffmark    
  from #staff_work_salary  where staffno not in (RTRIM(strCompId)+'300',RTRIM(strCompId)+'400',RTRIM(strCompId)+'500',RTRIM(strCompId)+'600') order by staffno    
  drop table #staff_work_salary    
end 