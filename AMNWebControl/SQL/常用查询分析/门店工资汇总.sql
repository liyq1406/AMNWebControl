alter procedure upg_prepare_ticheng_analysis_after_CW_sum(                
 @compid    varchar(10), -- 公司别                
 @fromdate   varchar(10), -- 开始日期                
 @todate    varchar(10)  -- 截至日期      
)       
as      
begin      
  create table #staff_work_salary      
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
	businessflag			int				NULL    ,--是否为业务人员 0--不是 1--是
	markflag				int				NULL,	--标记
  )       
       
         
  insert #staff_work_salary(strCompId,staffinid,stafftotalyeji,computedays,inserworkdays)      
  select compid,person_inid,SUM(ISNULL(staffyeji,0)) ,datediff(day,@fromdate,@todate)+1  ,COUNT(distinct salary_date)     
  from staff_work_salary       
  where compid=@compid and salary_date between @fromdate and @todate group by  compid,person_inid 
  

 
        
  insert #staff_work_salary(strCompId,staffinid,stafftotalyeji,workdays,computedays)      
  select @compid,manageno,0,0,datediff(day,@fromdate,@todate)+1      
  from staffhistory where effectivedate between @fromdate and @todate and oldcompid=@compid      
  and manageno not in ( select person_inid from staff_work_salary       
  where compid=@compid and salary_date between @fromdate and @todate)      
  group by manageno   
  
   create table #empinfobydate(                                    
		seqno  int identity  not null,                        
		compid  varchar(10)   null,                            
		inid  varchar(20)   null,                                    
		empid  varchar(20)   null,                                    
		datefrom varchar(8)   null,                                    
		dateto  varchar(8)   null,                                    
		position varchar(10)   null,                                    
		salary  float    null,                                    
		sharetype varchar(5)   null,                                    
		sharerate float    null,                                    
		deducttax int     null,                                     
  )                           
                       
  insert into #empinfobydate(compid,inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)                               
  exec upg_get_empinfo_by_date_comps @compid,@fromdate,@todate    
 
 delete #empinfobydate where dateto<@fromdate
  delete #empinfobydate where datefrom>@todate 

    
  --添加没有业绩的人员      
  insert #staff_work_salary(strCompId,staffinid,stafftotalyeji)      
  select compno,Manageno,0 from staffinfo,#empinfobydate
   where Manageno not in (select staffinid from #staff_work_salary) and compid=@compid  and   Manageno=inid   and staffno not like '9%'   
  group by compno,Manageno     
     


         
  --insert #staff_work_salary(strCompId,staffinid,stafftotalyeji)      
  --select oldcompid,Manageno,0 from staffhistory where Manageno not in (select staffinid from #staff_work_salary) and oldcompid=@compid and effectivedate>@todate      
  --group by  oldcompid,Manageno      
        
        
  update a set a.staffno=b.staffno,a.staffname=b.staffname,a.staffposition=b.position,a.staffpcid=b.pccid,a.staffbankaccountno=b.bankno,      
  --a.staffbasesalary=isnull(b.basesalary,0)*ISNULL(workdays,0)/ISNULL(computedays,1) ,      
  a.staffbasesalary=isnull(b.basesalary,0) ,      
      a.resulttye=b.resulttye,a.resultrate=b.resultrate,a.baseresult=b.baseresult,      
      a.salaryflag=b.salaryflag,a.staffsocials=b.socialsecurity,a.staffmark=b.remark,a.staffsocialsource=b.socialsource ,
      a.computedays=datediff(day,@fromdate,@todate)+1 ,a.absencesalary=ISNULL(b.absencesalary,0) ,a.arrivaldate=b.arrivaldate,a.businessflag=b.businessflag      
  from #staff_work_salary a,staffinfo b       
  where a.staffinid=b.Manageno   
  
  
update a set a.staffno=b.staffno,a.staffname=b.staffname,a.staffposition=b.position,a.staffpcid=b.pccid,a.staffbankaccountno=b.bankno,      
  --a.staffbasesalary=isnull(b.basesalary,0)*ISNULL(workdays,0)/ISNULL(computedays,1) ,      
  a.staffbasesalary=isnull(b.basesalary,0) ,      
      a.resulttye=b.resulttye,a.resultrate=b.resultrate,a.baseresult=b.baseresult,      
      a.salaryflag=b.salaryflag,a.staffsocials=b.socialsecurity,a.staffmark=b.remark,a.staffsocialsource=b.socialsource ,
      a.computedays=datediff(day,@fromdate,@todate)+1 ,a.absencesalary=ISNULL(b.absencesalary,0) ,a.arrivaldate=b.arrivaldate,a.businessflag=b.businessflag       
  from #staff_work_salary a,staffinfo b       
  where a.staffinid=b.Manageno  and b.compno=@compid and b.staffno like '9%'  
  
  
  
  update a set a.staffsocials=0 from   #staff_work_salary a,staffhistory b
  where  a.staffinid=b.Manageno  and b.effectivedate between @fromdate and @todate and changetype=1 and oldcompid= @compid


  update c set c.staffsocials=0 from   #staff_work_salary c,(select oldcompid,manageno from (select row_number() over( PARTITION BY manageno order by max(ISNULL(effectivedate,0)) asc) fid, oldcompid,manageno from   #staff_work_salary a,staffhistory b
    where  a.staffinid=b.Manageno  and b.effectivedate> @todate group by  oldcompid,manageno,effectivedate ) e where fid=1 ) d
  where  c.staffinid=d.Manageno and d.oldcompid<>@compid
  
  
   


   
    --收银有薪资调动的算最后一次的调动的工资  
  update a set a.staffbasesalary=oldsalary,staffposition='01202'  
  from #staff_work_salary a,( select manageno,oldsalary=isnull(oldsalary,0) from (select  row_number() over( PARTITION BY manageno order by max(ISNULL(effectivedate,0)) desc)  fid,manageno,oldsalary=isnull(oldsalary,0) from  staffhistory b,#staff_work_salary c where c.staffinid=b.Manageno and effectivedate between @fromdate and @todate  and oldpostion='01202' and oldcompid=@compid and isnull(changetype,0)<>5   group by manageno,oldsalary,effectivedate) e where fid=1   ) d  
  where a.staffinid=d.Manageno   
  
  --有调动的根据调动走
   update a set a.staffbasesalary=b.salary,  staffposition=position
  from #staff_work_salary a ,#empinfobydate b
  where a.staffinid=b.inid and @compid=b.compid
  --and a.staffposition='01202' and position='01202' 
  
      --有薪资调动的算最后一次的调动的工资  
  update a set  staffposition=oldpostion
  from #staff_work_salary a,( select manageno,oldsalary=isnull(oldsalary,0),oldpostion from (select  row_number() over( PARTITION BY manageno order by max(ISNULL(effectivedate,0)) asc)  fid,manageno,oldsalary=isnull(oldsalary,0),oldpostion from  staffhistory b,#staff_work_salary c where c.staffinid=b.Manageno and effectivedate>@todate and isnull(changetype,0)<>5 group by manageno,oldsalary,effectivedate,oldpostion) e where fid=1   ) d  
  where a.staffinid=d.Manageno   
   
  drop table #empinfobydate
 
  
  --门店总经理的基本工资
  update a set a.staffbasesalary=sharesalary
  from #staff_work_salary a,managershareinfo b
  where a.staffinid=b.manageno and b.compid=@compid
  
  
  --ID00003032 梁欢欢 
  --ID00000475 罗东艳
  update #staff_work_salary set staffbasesalary=5000 where staffinid='ID00003032'
  update #staff_work_salary set staffbasesalary=5000 where staffinid='ID00000475'
  --ID00006749 尤坤
   update #staff_work_salary set staffbasesalary=1800 where staffinid='ID00006749'
    --计算缺勤天数
  update a set workdays=ISNULL((  select COUNT(absencedate) from staffabsenceinfo where   manageno=staffinid and  absencedate between @fromdate and @todate ),0)
  from #staff_work_salary a
  
  
      --计算缺勤天数
  update a set workdays=ISNULL((  select COUNT(absencedate) from staffabsenceinfo where  compid=@compid and  manageno=staffinid and  absencedate between @fromdate and @todate ),0)
  from #staff_work_salary a where staffinid in (
  select manageno from (select manageno,compid  from staffabsenceinfo where absencedate>='20140201' group by manageno,compid ) a
  group by manageno having COUNT(manageno)>1)


  update #staff_work_salary set computedays=16,workdays=case when workdays>16 then 16 else workdays end where staffposition in ('010','01201','01202','01203') and SUBSTRING(@todate,5,2)='02'
  
  --计算缺勤工资
  
   update #staff_work_salary set staffbasesalary=ISNULL(staffbasesalary,0)*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0)  where ISNULL(computedays,0)>0 
        
   --计算门店业绩提成（管理层）                                                                    
  declare @beaut_yeji  float                                                                    
  declare @hair_yeji  float                                
  declare @trh_yeji  float            
  declare @trh_yejifact   float                  
  declare @total_yeji  float  
  declare @realbeautyeji float        
  select @beaut_yeji=sum(isnull(beautyeji,0)),@hair_yeji=sum(isnull(hairyeji,0)),
		 @trh_yeji=sum(isnull(footyeji,0)),@total_yeji=sum(isnull(totalyeji,0)) ,
		 @trh_yejifact=sum(isnull(realfootyeji,0)),@realbeautyeji=sum(isnull(realbeautyeji,0))      
  from compclasstraderesult  where compid=@compid and ddate between @fromdate and @todate      
        
        


  --烫染督导 008 拿的是烫染业绩基数      
   update   a set staffshopyeji=(ISNULL(@trh_yeji,0)-ISNULL(footyeji,0) -isnull(baseresult,0))*0.02  
   from #staff_work_salary a
   left join  (select c.staffinid ,footyeji=SUM(ISNULL(footyeji,0)) from compclasstraderesult a,staffabsenceinfo b,#staff_work_salary c
								where staffposition='008' and c.staffinid=b.manageno and a.compid=c.strCompId and c.strCompId=b.compid and b.absencedate  between @fromdate and @todate 
								and a.ddate=absencedate
								group by c.staffinid ) d   on a.staffinid=d.staffinid                  
   where  staffposition='008' and ISNULL(@trh_yeji,0) >=isnull(baseresult,0)   
   

  --美发经理拿总虚业绩 0.006
	--刘际根 020107   ID00004187
	--嵇助阳 026101   ID00007233
	--张士艮 027105   ID00000416
	--马玉银 048101   014014606
	--张磊   047108   SHYQ00001940
    --徐军杰 006101   ID00003344
    --吴百顺 015108  ID00001312
    --季学奎 016811  001016319
    --孙其耀 033105  014014302
    --杜彦城店长 031102 ID00006353
   update   a set staffshopyeji=(ISNULL(@total_yeji,0)-ISNULL(totalyeji,0))*0.006  
   from #staff_work_salary a
   left join  (select b.manageno ,totalyeji=SUM(ISNULL(totalyeji,0)) from compclasstraderesult a,staffabsenceinfo b
								where b.manageno in('ID00006353','014014302','001016319','ID00001312','ID00004187','ID00007233','ID00000416','014014606','SHYQ00001940','ID00003344') and a.compid=b.compid and b.compid=@compid and b.absencedate  between @fromdate and @todate 
								and a.ddate=absencedate
								group by b.manageno ) d   on a.staffinid=d.manageno                  
   where  staffinid in('ID00006353','014014302','001016319','ID00001312','ID00004187','ID00007233','ID00000416','014014606','SHYQ00001940','ID00003344')
   
	 --美发经理 店业绩>=个人业绩 个人业绩为0
     update #staff_work_salary set staffshopyeji=0
     where  staffinid in('ID00006353','014014302','001016319','ID00001312','ID00004187','ID00007233','ID00000416','014014606','SHYQ00001940','ID00003344') and ISNULL(stafftotalyeji,0)>=ISNULL(staffshopyeji,0)
      --美发经理 店业绩<个人业绩 店业绩0
     update #staff_work_salary set stafftotalyeji=0
     where  staffinid in('ID00006353','014014302','001016319','ID00001312','ID00004187','ID00007233','ID00000416','014014606','SHYQ00001940','ID00003344') and ISNULL(stafftotalyeji,0)<ISNULL(staffshopyeji,0)
     
     
     
   --只有美容经理 00101    0.02 美容部实业绩	6  ,门店月度奖金
   update   a set staffshopyeji=(ISNULL(@realbeautyeji,0)-ISNULL(realbeautyeji,0))*0.02  
   from #staff_work_salary a
   left join  (select c.staffinid ,realbeautyeji=SUM(ISNULL(realbeautyeji,0)) from compclasstraderesult a,staffabsenceinfo b,#staff_work_salary c
								where staffposition='00101' and c.staffinid=b.manageno and a.compid=c.strCompId and c.strCompId=b.compid and b.absencedate  between @fromdate and @todate 
								and a.ddate=absencedate
								group by c.staffinid ) d   on a.staffinid=d.staffinid                  
   where  staffposition='00101'
   
   
   if(@compid='014')
   begin
		--美容经理 014107 陈俊
	   update   a set staffshopyeji=(ISNULL(@realbeautyeji,0)-ISNULL(realbeautyeji,0))*0.015 
	   from #staff_work_salary a
	   left join  (select c.staffinid ,realbeautyeji=SUM(ISNULL(realbeautyeji,0)) from compclasstraderesult a,staffabsenceinfo b,#staff_work_salary c
									where staffposition='00101' and c.staffinid=b.manageno and a.compid=c.strCompId and c.strCompId=b.compid and b.absencedate  between @fromdate and @todate 
									and a.ddate=absencedate and c.staffinid='014014420' and c.strCompId='014'
									group by c.staffinid ) d   on a.staffinid=d.staffinid                  
	   where  a.staffinid='014014420' and a.strCompId='014'
   end
   
    if(@compid='033')
   begin
		--美容经理 033106 黄丹
	   update   a set staffshopyeji=(ISNULL(@realbeautyeji,0)-ISNULL(realbeautyeji,0))*0.015
	   from #staff_work_salary a
	   left join  (select c.staffinid ,realbeautyeji=SUM(ISNULL(realbeautyeji,0)) from compclasstraderesult a,staffabsenceinfo b,#staff_work_salary c
									where staffposition='00101' and c.staffinid=b.manageno and a.compid=c.strCompId and c.strCompId=b.compid and b.absencedate  between @fromdate and @todate 
									and a.ddate=absencedate and c.staffinid='ID00003520' and c.strCompId='033'
									group by c.staffinid ) d   on a.staffinid=d.staffinid                  
	   where  a.staffinid='ID00003520' and a.strCompId='033'
   end
   
   --013013102 陈华 不需要门店业绩
   update #staff_work_salary set staffshopyeji=0 where staffinid='013013102'
           
   --美容顾问     00103    0.02 美容部实业绩	6  ,门店月度奖金
   update   a set staffshopyeji=(ISNULL(@realbeautyeji,0)-ISNULL(realbeautyeji,0))*0.02  
   from #staff_work_salary a
   left join  (select c.staffinid ,realbeautyeji=SUM(ISNULL(realbeautyeji,0)) from compclasstraderesult a,staffabsenceinfo b,#staff_work_salary c
								where staffposition='00103' and c.staffinid=b.manageno and a.compid=c.strCompId and c.strCompId=b.compid and b.absencedate  between @fromdate and @todate 
								and a.ddate=absencedate
								group by c.staffinid ) d   on a.staffinid=d.staffinid                  
   where  staffposition='00103' 
   
   

   --大堂经理(C) 00105		0.001总虚业绩		4
     update   a set staffshopyeji=(ISNULL(@total_yeji,0)-ISNULL(totalyeji,0))*0.001  
   from #staff_work_salary a
   left join  (select c.staffinid ,totalyeji=SUM(ISNULL(totalyeji,0)) from compclasstraderesult a,staffabsenceinfo b,#staff_work_salary c
								where staffposition='00105' and c.staffinid=b.manageno and a.compid=c.strCompId and c.strCompId=b.compid and b.absencedate  between @fromdate and @todate 
								and a.ddate=absencedate
								group by c.staffinid ) d   on a.staffinid=d.staffinid                  
   where  staffposition='00105'
   
   --大堂经理(B) 0010502   0.001总虚业绩		4
    update   a set staffshopyeji=(ISNULL(@total_yeji,0)-ISNULL(totalyeji,0))*0.001  
   from #staff_work_salary a
   left join  (select c.staffinid ,totalyeji=SUM(ISNULL(totalyeji,0)) from compclasstraderesult a,staffabsenceinfo b,#staff_work_salary c
								where staffposition='0010502' and c.staffinid=b.manageno and a.compid=c.strCompId and c.strCompId=b.compid and b.absencedate  between @fromdate and @todate 
								and a.ddate=absencedate
								group by c.staffinid ) d   on a.staffinid=d.staffinid                  
   where  staffposition='0010502' 
   
   --大堂经理(A) 0010503   0.004总虚业绩		4
     update   a set staffshopyeji=(ISNULL(@total_yeji,0)-ISNULL(totalyeji,0))*0.004  
   from #staff_work_salary a
   left join  (select c.staffinid ,totalyeji=SUM(ISNULL(totalyeji,0)) from compclasstraderesult a,staffabsenceinfo b,#staff_work_salary c
								where staffposition='0010503' and c.staffinid=b.manageno and a.compid=c.strCompId and c.strCompId=b.compid and b.absencedate  between @fromdate and @todate 
								and a.ddate=absencedate
								group by c.staffinid ) d   on a.staffinid=d.staffinid                  
   where  staffposition='0010503' 
   
    
  
   
   
   
   --烫染总监 00801 拿的是烫染业绩基数        
   /*update   #staff_work_salary set staffshopyeji=ISNULL(@trh_yejifact,0)*0.02                          
   where  staffposition='00801'         
   update   #staff_work_salary set staffshopyeji=isnull(staffshopyeji,0)+2000                  
   where  staffposition='00801' and ISNULL(@trh_yeji,0) >=isnull(baseresult,0)   */     
                                                         
         --其他管理层来的是分享业绩      
   /*update #staff_work_salary                                                                     
   set staffshopyeji = case when isnull(resulttye,'')='1' then isnull(@hair_yeji,0)*isnull(resultrate,0)                                                                
   when isnull(resulttye,'')='2' then isnull(@beaut_yeji,0)*isnull(resultrate,0)       
   when isnull(resulttye,'')='3' then isnull(@trh_yeji,0)*isnull(resultrate,0)                 
   when isnull(resulttye,'')='4' then isnull(@total_yeji,0)*isnull(resultrate,0) else 0 end        
     where   staffposition<>'008'  */       
              
       
  --美容师补贴                        
  --美容师入职超过一年每月补贴200, 两年 400 (七天没上班没有补贴)                        
  update a                                         
  set beatysubsidy=isnull((case when DATEDIFF ( day, b.arrivaldate ,ISNULL(@fromdate,'') ) between 364 and 727 then 200                    
    when DATEDIFF ( day, b.arrivaldate ,ISNULL(@fromdate,'') )> 727 then 400                        
     else 0 end),0)                        
  from #staff_work_salary a,staffinfo  b                        
  where a.staffinid=b.Manageno and b.position in ('004','00401','00402') and isnull(a.inserworkdays,0)>23   and ISNULL(curstate,0)=2         
        
   --收银小单补贴      
   declare @ccount int       
   select @ccount=COUNT(distinct csbillid)-2000 from mconsumeinfo with(nolock)           
   where cscompid=@compid           
   and financedate between @fromdate and @todate         
   and ISNULL(backcsflag,0)=0          
   and ISNULL(backcsbillid,'')=''           
           
   if(@ccount>0)          
   begin          
	update #staff_work_salary set staffshopyeji=isnull(staffshopyeji,0)+ISNULL(@ccount,0)*0.1 where staffposition in('01201','01202')       
   end    
    update #staff_work_salary set staffshopyeji=(200+isnull(staffshopyeji,0))*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0) where staffposition in('01201','01202')                
    --调配师补贴
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
   update #staff_work_salary set staffshopyeji=(isnull(staffshopyeji,0)+600)*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0) where staffposition='002'   and ISNULL(computedays,0)>0    
   

   
   --关爱基金支付
   update a set basestaffpayamt=isnull(outamt ,0)-isnull(inseramt,0)
   from #staff_work_salary a,staffbaseamt b
   where a.staffinid=b.staffinid and   a.staffno not like '9%'
   --罚款                                       
                  
  update #staff_work_salary                                         
  set staffdebit=isnull((select sum(isnull(rewardamt,0)) from mstaffrewardinfo a,dstaffrewardinfo b where a.entrycompid=b.entrycompid and a.entrybillid=b.entrybillid and handcompid=@compid and isnull(b.billflag,0)=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype  in ('02','05') ),0)                                 
     
	--代扣                                       
                  
  update #staff_work_salary                                         
  set staffdaikou=isnull((select sum(isnull(rewardamt,0)) from mstaffrewardinfo a,dstaffrewardinfo b where a.entrycompid=b.entrycompid and a.entrybillid=b.entrybillid and handcompid=@compid and isnull(b.billflag,0)=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype ='12' ),0)                                 
        
                    
  
        
                
  --迟到早退                                       
                  
  update #staff_work_salary                                         
  set latdebit=isnull((select sum(isnull(rewardamt,0)) from mstaffrewardinfo a,dstaffrewardinfo b where a.entrycompid=b.entrycompid and a.entrybillid=b.entrybillid and handcompid=@compid and isnull(b.billflag,0)=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype='04' ),0)                                    
   
        
  --其他扣款                                      
                  
  --update #staff_work_salary                                         
  --set otherdebit=isnull((select sum(isnull(rewardamt,0)) from mstaffrewardinfo a,dstaffrewardinfo b where a.entrycompid=b.entrycompid and a.entrybillid=b.entrybillid and handcompid=@compid and isnull(b.billflag,0)=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype='05' ),0)                                  
    
     
        
        
  --学习费                                       
                  
  update #staff_work_salary                                         
  set studydebit=isnull((select sum(isnull(rewardamt,0)) from mstaffrewardinfo a,dstaffrewardinfo b where a.entrycompid=b.entrycompid and a.entrybillid=b.entrybillid and handcompid=@compid and isnull(b.billflag,0)=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype='09' ),0)                                  
    
     
                            
  --口成本                                       
                  
  update #staff_work_salary                                         
  set staffcost=isnull((select sum(isnull(rewardamt,0)) from mstaffrewardinfo a,dstaffrewardinfo b where a.entrycompid=b.entrycompid and a.entrybillid=b.entrybillid and handcompid=@compid and isnull(b.billflag,0)=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype='08' ),0)                                   
    
                             
  ----住宿                                      
  update #staff_work_salary                                         
  set staydebit=isnull((select sum(isnull(rewardamt,0)) from mstaffrewardinfo a,dstaffrewardinfo b where a.entrycompid=b.entrycompid and a.entrybillid=b.entrybillid and handcompid=@compid and isnull(b.billflag,0)=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype='07' ),0)                                   
    
        
  --奖励           
  update #staff_work_salary                                         
  set staffreward=isnull((select sum(isnull(rewardamt,0)) from mstaffrewardinfo a,dstaffrewardinfo b where a.entrycompid=b.entrycompid and a.entrybillid=b.entrybillid and handcompid=@compid and isnull(b.billflag,0)=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype in ('11') ),0)                                 
   
   --员工年终奖 yearreward
    update #staff_work_salary                                         
  set yearreward=isnull((select sum(isnull(rewardamt,0)) from mstaffrewardinfo a,dstaffrewardinfo b where a.entrycompid=b.entrycompid and a.entrybillid=b.entrybillid and handcompid=@compid and isnull(b.billflag,0)=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype in ('13') ),0)                                 
   
   --差异调整          
  update #staff_work_salary                                         
  set staffamtchange=isnull((select sum( case when isnull(entryflag,0)=0 then isnull(rewardamt,0) else isnull(rewardamt,0)*(-1) end) from mstaffrewardinfo a,dstaffrewardinfo b where a.entrycompid=b.entrycompid and a.entrybillid=b.entrybillid and handcompid=@compid and isnull(b.billflag,0)=1 and entrydate>=@fromdate and entrydate<=@todate
 and handstaffinid=staffinid and entrytype='10' ),0)                                 
   
    --责任金抵押
    update #staff_work_salary                                         
  set zerenjincost=isnull((select sum(isnull(rewardamt,0)) from mstaffrewardinfo a,dstaffrewardinfo b where a.entrycompid=b.entrycompid and a.entrybillid=b.entrybillid and handcompid=@compid and isnull(b.billflag,0)=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype ='15' ),0)                                 
   
    --责任金返回
    update #staff_work_salary                                         
  set zerenjinback=isnull((select sum(isnull(rewardamt,0)) from mstaffrewardinfo a,dstaffrewardinfo b where a.entrycompid=b.entrycompid and a.entrybillid=b.entrybillid and handcompid=@compid and isnull(b.billflag,0)=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype='16' ),0)                                 
   
  --门店补贴           
  update #staff_work_salary                                         
  set storesubsidy=isnull((select sum(isnull(rewardamt,0)) from mstaffrewardinfo a,dstaffrewardinfo b where a.entrycompid=b.entrycompid and a.entrybillid=b.entrybillid and handcompid=@compid and isnull(b.billflag,0)=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype in ('06','14') ),0)                                 
    
       

   
        -- 烫染督导 008 完成额度>指标额度 奖励2000 
   update   a set staffreward=isnull(staffreward,0)+2000*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0)
   from #staff_work_salary a 
   where staffposition ='008' and isnull(@trh_yeji,0)>ISNULL(baseresult,0) and staffno not like '9%'
   
  -- 美容顾问+美容经理完成额度/指标额度 *2000 *到勤率
   update   a set staffreward=isnull(staffreward,0)+isnull((case when ISNULL(@realbeautyeji,0)/isnull(baseresult,0)>1 then 2000 else  ISNULL(@realbeautyeji,0)/isnull(baseresult,0)*2000 end)*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0),0)
   from #staff_work_salary a 
   where staffposition in ('00101','00103') and isnull(baseresult,0)>0 and ISNULL(@realbeautyeji,0)/isnull(baseresult,0)>0.8 and staffno not like '9%'
   
   
   
   --美容师 到薪资计算结束日期未满3个月的计3000的考勤补贴
   
   UPDATE a 
   set staffsubsidy=3000*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0)
   from #staff_work_salary a,staffhistory b 
   where staffposition ='00402'  and ISNULL(computedays,0)>0
   and staffinid=manageno and changetype=4 and oldcompid=@compid and  datediff(MONTH,effectivedate,@todate)<3
   
   


    UPDATE #staff_work_salary set staffsubsidy=1200*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0)
   where staffposition in ('00901','00902','00903','00904') and datediff(MONTH,arrivaldate,@todate)<3 and ISNULL(computedays,0)>0
   
      
  create table #staffsubsidy
  (
		staffinid		varchar(20)		null,
		staffsubsidy	float			null
  )
  --计算保底补贴
  --    
   exec upg_prepare_compute_staff_subsidy @compid,@fromdate,@todate
   
   --update a set staffsubsidy=isnull((select sum(isnull(staffsubsidy,0))*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0) from #staffsubsidy b where a.staffinid=b.staffinid and ISNULL(computedays,0)>0 ),0)        
   --from #staff_work_salary a
   
  update a set staffsubsidy=d.staffsubsidy
  from #staff_work_salary a,(select b.staffinid,staffsubsidy=(isnull(b.staffsubsidy,0))*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0) from #staff_work_salary c,#staffsubsidy b where c.staffinid=b.staffinid and ISNULL(computedays,0)>0 ) d
  where a.staffinid=d.staffinid
 
  
   drop table #staffsubsidy
   

  update a set staffsubsidy=0
  from #staff_work_salary a,staffinfo b
  where a.staffinid=b.manageno and b.compno='99999' 

   --派遣人员门店业绩为0
   update #staff_work_salary set staffshopyeji=0 ,staffsubsidy=0 where staffno like '9%'
   
   
     ----离职异动单增加离职种类 正常离职,自动离职, 自动离职需扣款600                                     
  update #staff_work_salary set leaveldebit=600  where staffinid in (select staffmangerno from staffchangeinfo where changetype=1 and leaveltype=2 and validatestartdate  between @fromdate and @todate )                      
  update #staff_work_salary       
      set needpaysalary=ISNULL(stafftotalyeji,0)+ ISNULL(staffshopyeji,0)+      
         ISNULL(staffbasesalary,0)+ISNULL(beatysubsidy,0)+ISNULL(staffreward,0),      
    factpaysalary=ISNULL(stafftotalyeji,0)+ ISNULL(staffshopyeji,0)+      
         ISNULL(staffbasesalary,0)+ISNULL(beatysubsidy,0)+ISNULL(staffreward,0) 
      
    --有补贴的人
    update #staff_work_salary set staffsubsidy =(case when  isnull(staffsubsidy,0)<=ISNULL(needpaysalary,0) then 0 else  isnull(staffsubsidy,0)-(ISNULL(needpaysalary,0)) end)
    
	update #staff_work_salary set  needpaysalary=ISNULL(needpaysalary,0)+ isnull(staffsubsidy,0),factpaysalary=isnull(factpaysalary,0)+ isnull(staffsubsidy,0)
     
   --美容经理,美容顾问 税前-差异调整 不到5000 补成5000     
   update   a set   factpaysalary=5000*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0),
					needpaysalary=5000*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0),
   staffsubsidy=5000*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0)-ISNULL(needpaysalary,0)
   from #staff_work_salary a 
   where staffposition in ('00101','00103') 
   and (5000*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0))>ISNULL(needpaysalary,0) and ISNULL(computedays,0)>0
   and staffno not like '9%'
   and staffinid not in ('SHYQ00000540','ID00004184','009009428','SHYQ00000691','SHYQ00001264','ID00000908','ID00004144','ID00003853','014014420','ID00006354','ID00002142','SHYQ00001836','SHYQ00001958','ID00005013','ID00005073','ID00001192','ID00007056','ID00000113','ID00006330')
   -- 不拿美容补贴SHYQ00000540  ID00004184 009009428 SHYQ00000691 SHYQ00001264 ID00000908 ID00004144 ID00003853 014014420 ID00006354 ID00002142 SHYQ00001836 SHYQ00001958 ID00005013 ID00005073 ID00001192 ID00007056  ID00000113 ID00006330
  --督导 税前-差异调整 不到5000 补成5000     
   update   a set	needpaysalary=5000*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0),
					factpaysalary=5000*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0),
					staffsubsidy=5000*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0)-ISNULL(needpaysalary,0)
   from #staff_work_salary a 
   where staffposition='008' and ISNULL(@trh_yeji,0) >=isnull(baseresult,0) 
   and ISNULL(needpaysalary,0)<5000*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0) and ISNULL(computedays,0)>0
   and staffno not like '9%'
   
    --调整额度(个人差异调整+年终奖-社保)
   update #staff_work_salary set  needpaysalary=ISNULL(needpaysalary,0)+ isnull(staffamtchange,0)+ISNULL(storesubsidy,0)+ISNULL(yearreward,0)-ISNULL(staffsocials,0)-      
         ISNULL(leaveldebit,0)-ISNULL(staffdebit,0)-ISNULL(latdebit,0)-ISNULL(otherdebit,0)+ISNULL(basestaffpayamt,0),
								  factpaysalary=isnull(factpaysalary,0)+ isnull(staffamtchange,0)+ISNULL(storesubsidy,0)+ISNULL(yearreward,0)-ISNULL(staffsocials,0)-      
         ISNULL(leaveldebit,0)-ISNULL(staffdebit,0)-ISNULL(latdebit,0)-ISNULL(otherdebit,0)+ISNULL(basestaffpayamt,0),
								  staffreward=ISNULL(yearreward,0)+ISNULL(staffreward,0)
   --update #staff_work_salary set  needpaysalary=ISNULL(needpaysalary,0)+ isnull(staffamtchange,0)+ISNULL(storesubsidy,0),factpaysalary=isnull(factpaysalary,0)+ isnull(staffamtchange,0)+ISNULL(storesubsidy,0)  
  
   --新增区域经理的基本工资
    --insert #staff_work_salary(strCompId,strCompName,staffno,staffinid,staffname,staffposition,staffpositionname,staffpcid,staffbankaccountno,staffbasesalary,needpaysalary,factpaysalary)
    --select a.compid,compname,a.empid,a.manageno,b.staffname,b.position,parentcodevalue,b.pccid,b.bankno,sharesalary,sharesalary,sharesalary=ISNULL(sharesalary,0)*(datediff(day,@fromdate,@todate)+1)/COUNT(absencedate)
    --from managershareinfo a,staffinfo b,commoninfo,companyinfo c,staffabsenceinfo d
    --where a.compid=@compid and a.manageno not in (select staffinid from #staff_work_salary ) and a.manageno=b.manageno
    --and infotype='GZGW' and parentcodekey=b.position and a.compid=c.compno
    --and a.compid=d.compid and d.manageno=a.manageno and  absencedate between @fromdate and @todate
    --group by a.compid,compname,a.empid,a.manageno,b.staffname,b.position,parentcodevalue,b.pccid,b.bankno,sharesalary,sharesalary,sharesalary

    insert #staff_work_salary(strCompId,strCompName,staffno,staffinid,staffname,staffposition,staffpositionname,staffpcid,staffbankaccountno,staffbasesalary,needpaysalary,factpaysalary)
    select a.compid,compname,a.empid,a.manageno,b.staffname,b.position,parentcodevalue,b.pccid,b.bankno,sharesalary,sharesalary,sharesalary
    from managershareinfo a,staffinfo b,commoninfo,companyinfo c
    where a.compid=@compid and a.manageno not in (select staffinid from #staff_work_salary ) and a.manageno=b.manageno
    and infotype='GZGW' and parentcodekey=b.position and a.compid=c.compno
   
  
   --美容经理 014107 陈俊
   update   a set staffshopyeji=(ISNULL(@realbeautyeji,0)-ISNULL(realbeautyeji,0))*0.015 ,workdays=fworkdays ,staffbasesalary=ISNULL(staffbasesalary,0)*isnull(fworkdays,0)/(datediff(day,@fromdate,@todate)+1)
   from #staff_work_salary a
   left join  (select c.staffinid ,realbeautyeji=SUM(ISNULL(realbeautyeji,0)),fworkdays=count(absencedate) from compclasstraderesult a,staffabsenceinfo b,#staff_work_salary c
								where staffposition='00101' and c.staffinid=b.manageno and a.compid=c.strCompId and c.strCompId=b.compid and b.absencedate  between @fromdate and @todate 
								and a.ddate=absencedate and c.staffinid='014014420' and c.strCompId='041'
								group by c.staffinid ) d   on a.staffinid=d.staffinid                  
   where  a.staffinid='014014420' and a.strCompId='041'
   
     --美容经理 033106 黄丹
   update   a set staffshopyeji=(ISNULL(@realbeautyeji,0)-ISNULL(realbeautyeji,0))*0.015 ,workdays=fworkdays ,staffbasesalary=ISNULL(staffbasesalary,0)*isnull(fworkdays,0)/(datediff(day,@fromdate,@todate)+1)
   from #staff_work_salary a
   left join  (select c.staffinid ,realbeautyeji=SUM(ISNULL(realbeautyeji,0)),fworkdays=count(absencedate) from compclasstraderesult a,staffabsenceinfo b,#staff_work_salary c
								where staffposition='00101' and c.staffinid=b.manageno and a.compid=c.strCompId and c.strCompId=b.compid and b.absencedate  between @fromdate and @todate 
								and a.ddate=absencedate and c.staffinid='ID00003520' and c.strCompId='046'
								group by c.staffinid ) d   on a.staffinid=d.staffinid                  
   where  a.staffinid='ID00003520' and a.strCompId='046'
   
   update #staff_work_salary set needpaysalary=ISNULL(staffshopyeji,0)+ISNULL(staffbasesalary,0),
								 factpaysalary=ISNULL(staffshopyeji,0)+ISNULL(staffbasesalary,0)
   where ( (staffinid='014014420' and strCompId='041') or (staffinid='ID00003520' and strCompId='046') )
   
    

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
     
   update #staff_work_salary set staffsocialsource=parentcodevalue      
   from commoninfo,#staff_work_salary      
   where parentcodekey=staffsocialsource and infotype='SBGS'     
       
       
  update #staff_work_salary set strCompName=compname,basestaffamt=0,factpaysalary=factpaysalary-ISNULL(staydebit,0)-ISNULL(studydebit,0)-ISNULL(staffcost,0)-ISNULL(staffdaikou,0)+ISNULL(zerenjinback,0)-ISNULL(zerenjincost,0)     
  from #staff_work_salary,companyinfo where compno= strCompId  
  
  update a  set markflag=1
  from #staff_work_salary a,staffasarymark b
  where a.staffinid=b.manageno and a.strCompId=b.compid and absencedate=SUBSTRING(@todate,1,6)
  
  update #staff_work_salary set factpaysalary=0,needpaysalary=0 where ISNULL(factpaysalary,0)<0
  
  update a set staffcurstate=b.curstate
  from #staff_work_salary a,staffinfo b
  where a.staffinid=b.manageno
  
  update a set staffcurstate=4
  from #staff_work_salary a,( select person_inid,staffyeji=SUM(isnull(staffyeji,0)) from staff_work_salary where compid= @compid and salary_date between '20140311' and '20140318' group by person_inid) b
  where a.staffinid=b.person_inid and isnull(a.businessflag,0)=1 and ISNULL(staffcurstate,2)=2 and ISNULL(b.staffyeji,0)=0
 
   update #staff_work_salary set staffcurstate=4
   where staffinid not in ( select person_inid  from staff_work_salary where compid= @compid and salary_date between '20140311' and '20140318' group by person_inid) 
   and isnull(businessflag,0)=1 and ISNULL(staffcurstate,2)=2 and  (staffno like @compid+'3__' or staffno like @compid+'4__'  or staffno like @compid+'5__'  or staffno like @compid+'6__'  or staffno like @compid+'8__' )
 
  insert staff_work_salary_CW_sum(strCompId,factpaysalary,levelpaysalary,daifapaysalary)
  select @compid,sum(case when staffcurstate=2 then convert(numeric(20,0),factpaysalary) else 0 end )
  ,sum(case when staffcurstate=3 then convert(numeric(20,0),factpaysalary) else 0 end )
  ,sum(case when staffcurstate=4 then convert(numeric(20,0),factpaysalary) else 0 end )
  from #staff_work_salary  where staffno not in (RTRIM(strCompId)+'000',RTRIM(strCompId)+'300',RTRIM(strCompId)+'400',RTRIM(strCompId)+'500',RTRIM(strCompId)+'600') 
   
  drop table #staff_work_salary      
end 