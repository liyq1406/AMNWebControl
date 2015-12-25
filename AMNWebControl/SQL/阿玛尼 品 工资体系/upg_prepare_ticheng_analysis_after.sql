alter procedure upg_prepare_ticheng_analysis_after(                
 @compid    varchar(10), -- 公司别                
 @fromdate   varchar(10), -- 开始日期                
 @todate    varchar(10)  -- 截至日期      
)       
as      
begin      
  --工资不够扣 离职优先,再算罚款
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
	afterstaffreward		float			null,   --奖励 
	yearreward				float			null,	--年终奖
	oldcustomerreward		float			null,	--老客补贴
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
	stafftype				int				NULL	,-- 0 正常 1 派遣
	tichengmode				int				NULL    ,--提成模式 1 保底+奖金+底薪 2 只享受底薪
	managershareflag		int				NULL	,-- 0 正常 1 区域经理
  )       
   declare @afterFromMonthDay varchar(10)
   declare @afterToMonthDay varchar(10)
	exec upg_date_plus @fromdate,30,@afterFromMonthDay output
	exec upg_date_plus @todate,30,@afterToMonthDay output    
         
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
  delete #empinfobydate where datediff(day,datefrom,@todate)<3
    
  --添加没有业绩的人员      
  insert #staff_work_salary(strCompId,staffinid,stafftotalyeji)      
  select @compid,Manageno,0 from staffinfo,#empinfobydate
   where Manageno not in (select staffinid from #staff_work_salary) and compid=@compid  and   Manageno=inid    and ISNULL(stafftype,0)=0 
  group by compno,Manageno     
   

         
  --insert #staff_work_salary(strCompId,staffinid,stafftotalyeji)      
  --select oldcompid,Manageno,0 from staffhistory where Manageno not in (select staffinid from #staff_work_salary) and oldcompid=@compid and effectivedate>@todate      
  --group by  oldcompid,Manageno      
  
  --设置派遣的人
  update a set a.strCompId=@compid,a.staffno=b.staffno,a.staffname=b.staffname,a.staffposition=b.position,a.staffpcid=b.pccid,a.staffbankaccountno=b.bankno,
  a.staffbasesalary=isnull(b.basesalary,0) ,a.tichengmode=b.tichengmode ,   
      a.resulttye=b.resulttye,a.resultrate=b.resultrate,a.baseresult=b.baseresult,      
      a.salaryflag=b.salaryflag,a.staffsocials=b.socialsecurity,a.staffmark=b.remark,a.staffsocialsource=b.socialsource ,
      a.computedays=datediff(day,@fromdate,@todate)+1 ,a.absencesalary=ISNULL(b.absencesalary,0) ,
      a.arrivaldate=b.arrivaldate,a.businessflag=b.businessflag,a.stafftype=b.stafftype 
  from #staff_work_salary a,staffinfo b       
  where a.staffinid=b.Manageno  and b.compno=@compid and ISNULL(b.stafftype,0)=1
        
  --设置非派遣的人     
  update a set a.staffno=b.staffno,a.staffname=b.staffname,a.staffposition=b.position,a.staffpcid=b.pccid,a.staffbankaccountno=b.bankno,   
  a.staffbasesalary=isnull(b.basesalary,0)  ,a.tichengmode=b.tichengmode  ,      
      a.resulttye=b.resulttye,a.resultrate=b.resultrate,a.baseresult=b.baseresult,      
      a.salaryflag=b.salaryflag,a.staffsocials=b.socialsecurity,a.staffmark=b.remark,a.staffsocialsource=b.socialsource ,
      a.computedays=datediff(day,@fromdate,@todate)+1 ,a.absencesalary=ISNULL(b.absencesalary,0) ,a.arrivaldate=b.arrivaldate,a.businessflag=b.businessflag      
  from #staff_work_salary a,staffinfo b        
  where a.staffinid=b.Manageno   and ISNULL(a.stafftype,0)=0 and ISNULL(b.stafftype,0)=0
  
  
--update a set a.staffno=b.staffno,a.staffname=b.staffname,a.staffposition=b.position,a.staffpcid=b.pccid,a.staffbankaccountno=b.bankno,      
--  --a.staffbasesalary=isnull(b.basesalary,0)*ISNULL(workdays,0)/ISNULL(computedays,1) ,      
--  a.staffbasesalary=isnull(b.basesalary,0) ,      
--      a.resulttye=b.resulttye,a.resultrate=b.resultrate,a.baseresult=b.baseresult,      
--      a.salaryflag=b.salaryflag,a.staffsocials=b.socialsecurity,a.staffmark=b.remark,a.staffsocialsource=b.socialsource ,
--      a.computedays=datediff(day,@fromdate,@todate)+1 ,a.absencesalary=ISNULL(b.absencesalary,0) ,a.arrivaldate=b.arrivaldate,a.businessflag=b.businessflag       
--  from #staff_work_salary a,staffinfo b       
--  where a.staffinid=b.Manageno  and b.compno=@compid
  
  
  
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
  from #staff_work_salary a ,
  (select compid,inid,salary,position from (select  row_number() over(PARTITION BY inid order by max(ISNULL(datefrom,'')) desc)  fid, compid,inid,salary,position 
     from  #empinfobydate  group by compid,inid,salary,position ) as c where fid=1 ) as b 
   where a.staffinid=b.inid and @compid=b.compid
  --and a.staffposition='01202' and position='01202' 
  
   --有薪资调动的算最后一次的调动的职位
  update a set  staffposition=oldpostion
  from #staff_work_salary a,( select manageno,oldsalary=isnull(oldsalary,0),oldpostion from (select  row_number() over( PARTITION BY manageno order by max(ISNULL(effectivedate,0)) asc)  fid,manageno,oldsalary=isnull(oldsalary,0),oldpostion from  staffhistory b,#staff_work_salary c where c.staffinid=b.Manageno and effectivedate>@todate and isnull(changetype,0)<>5 group by manageno,oldsalary,effectivedate,oldpostion) e where fid=1   ) d  
  where a.staffinid=d.Manageno   
   
  drop table #empinfobydate
 
  
  --门店总经理的基本工资
  update a set a.staffbasesalary=sharesalary
  from #staff_work_salary a,managershareinfo b
  where a.staffinid=b.manageno and b.compid=@compid 
  
  
  --(0010201)南昌3家店在 3,4,5 月的基本工资是 1000 (二级烫染师 00902 ,三级烫染师 00903)
  if(@todate>'20140301' and @todate<='20140531')
  begin
		update a set a.staffbasesalary=1000
		from #staff_work_salary a,compchaininfo b
		where curcomp='0010201' and relationcomp=strCompId
		and staffposition in ('00902','00903')
		
		update a set a.staffbasesalary=500
		from #staff_work_salary a,compchaininfo b
		where curcomp in ('00101','00105','0010102','0010103','0010104') and relationcomp=strCompId
		and staffposition = '00902'
		
		update a set a.staffbasesalary=800
		from #staff_work_salary a,compchaininfo b
		where curcomp in ('00101','00105','0010102','0010103','0010104') and relationcomp=strCompId
		and staffposition = '00903'
  end
    --计算缺勤天数
  update a set workdays=ISNULL((  select COUNT(absencedate) from staffabsenceinfo where   compid=@compid and manageno=staffinid and  absencedate between @fromdate and @todate ),0)
  from #staff_work_salary a
  
  
  --    --计算缺勤天数
  --update a set workdays=ISNULL((  select COUNT(absencedate) from staffabsenceinfo where  compid=@compid and  manageno=staffinid and  absencedate between @fromdate and @todate ),0)
  --from #staff_work_salary a where staffinid in (
  --select manageno from (select manageno,compid  from staffabsenceinfo where absencedate>='20140201' group by manageno,compid ) a
  --group by manageno having COUNT(manageno)>1)


  update #staff_work_salary set computedays=16,workdays=case when workdays>16 then 16 else workdays end where staffposition in ('010','01201','01202','01203') and SUBSTRING(@todate,5,2)='02'
  update #staff_work_salary set computedays=20,workdays=case when workdays>20 then 20 else workdays end where staffposition in ('010','01201','01202','01203') and SUBSTRING(@todate,5,2)<>'02'
  
  --计算缺勤工资
  
   update #staff_work_salary set staffbasesalary=ISNULL(staffbasesalary,0)*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0)  where ISNULL(computedays,0)>0 
        
   --计算门店业绩提成（管理层）                                                                    
  declare @beaut_yeji  float                                                                    
  declare @hair_yeji  float                                
  declare @trh_yeji  float            
  declare @trh_yejifact   float                  
  declare @total_yeji  float  
  declare @realbeautyeji float  
  declare @realtotal_yeji	float    
  declare @costcount		float
  declare @staffleavelcount	float   
  
    
  select @beaut_yeji=sum(isnull(beautyeji,0)),@hair_yeji=sum(isnull(hairyeji,0)),
		 @trh_yeji=sum(isnull(footyeji,0)),@total_yeji=sum(isnull(totalyeji,0)) ,
		 @trh_yejifact=sum(isnull(realfootyeji,0)),@realbeautyeji=sum(isnull(realbeautyeji,0))      
  from compclasstraderesult  where compid=@compid and ddate between @fromdate and @todate      
        
        

  --本店分类账
  select @beaut_yeji=sum(isnull(beautyeji,0)),@hair_yeji=sum(isnull(hairyeji,0)),
		 @trh_yeji=sum(isnull(footyeji,0)),@total_yeji=sum(isnull(totalyeji,0)) ,
		 @trh_yejifact=sum(isnull(realfootyeji,0)),@realbeautyeji=sum(isnull(realbeautyeji,0)),@realtotal_yeji=SUM(ISNULL(realtotalyeji,0))     
  from compclasstraderesult  where compid=@compid and ddate between @fromdate and @todate      
        
   --本店客单数
   select @costcount=COUNT(distinct csbillid) from mconsumeinfo
   where financedate between @fromdate and @todate
   and ISNULL(backcsbillid,'')='' and ISNULL(backcsflag,0)=0 and cscompid=@compid
   

      --本店流失人数
   select @staffleavelcount=COUNT(distinct staffmangerno)
   from  staffchangeinfo a,staffinfo b 
   where changetype=1 and validatestartdate between @fromdate and @todate and appchangecompid=@compid and ISNULL(billflag,0)=8
   and a.staffmangerno=b.manageno and b.department in ('002','003','004','006','007') 
   and  datediff(DAY,arrivaldate,validateenddate)>60
   


	declare @ttotalyeji				float	--	总虚业绩指标
	declare @trealtotalyeji			float	--	总实业绩指标
	declare @tbeatyyeji				float	--  总美容虚业绩
	declare @ttrhyeji				float	--	烫染指标
	declare @tcostcount				float	--	总客单指标
	declare @tstaffleavelcount		float	--	门店流失数

	select @ttotalyeji=ttotalyeji,@trealtotalyeji=trealtotalyeji,@tbeatyyeji=tbeatyyeji,@ttrhyeji=ttrhyeji,@tcostcount=tcostcount,@tstaffleavelcount=tstaffleavelcount
	from storetargetinfo where compid=@compid and targetmonth=SUBSTRING(@fromdate,1,6) and ISNULL(targetflag,0)=1
	
	
	 --select @ttotalyeji,@trealtotalyeji,@tcostcount,@tstaffleavelcount
	 --	 select @total_yeji,@realtotal_yeji,@costcount,@staffleavelcount
	 
  --烫染督导 008 拿的是烫染业绩基数      
   update   a set staffshopyeji=(ISNULL(@trh_yeji,0) -isnull(@ttrhyeji,0))*0.02  
   from #staff_work_salary a                
   where  staffposition='008' and ISNULL(@trh_yeji,0) >=isnull(@ttrhyeji,0)  and isnull(@ttrhyeji,0)>0 
   and  isnull(a.workdays,0)<7  

 --    --美发经理拿总虚业绩 0.006
	----刘际根 020107   ID00004187
	----嵇助阳 026101   ID00007233
	----张士艮 027105   ID00000416
	----马玉银 048101   014014606
	----张磊   047108   SHYQ00001940
 --   --徐军杰 006101   ID00003344
 --   --吴百顺 015108  ID00001312
 --   --季学奎 016811  001016319
 --   --孙其耀 033105  014014302
 --   --杜彦城店长 031102 ID00006353
 --  update   a set staffshopyeji=(ISNULL(@total_yeji,0)-ISNULL(totalyeji,0))*0.006  
 --  from #staff_work_salary a
 --  left join  (select b.manageno ,totalyeji=SUM(ISNULL(totalyeji,0)) from compclasstraderesult a,staffabsenceinfo b
	--							where b.manageno in('ID00006353','014014302','001016319','ID00001312','ID00004187','ID00007233','ID00000416','014014606','SHYQ00001940','ID00003344') and a.compid=b.compid and b.compid=@compid and b.absencedate  between @fromdate and @todate 
	--							and a.ddate=absencedate
	--							group by b.manageno ) d   on a.staffinid=d.manageno                  
 --  where  staffinid in('ID00006353','014014302','001016319','ID00001312','ID00004187','ID00007233','ID00000416','014014606','SHYQ00001940','ID00003344')
   
	-- --美发经理 店业绩>=个人业绩 个人业绩为0
 --    update #staff_work_salary set staffshopyeji=0
 --    where  staffinid in('ID00006353','014014302','001016319','ID00001312','ID00004187','ID00007233','ID00000416','014014606','SHYQ00001940','ID00003344') and ISNULL(stafftotalyeji,0)>=ISNULL(staffshopyeji,0)
 --     --美发经理 店业绩<个人业绩 店业绩0
 --    update #staff_work_salary set stafftotalyeji=0
 --    where  staffinid in('ID00006353','014014302','001016319','ID00001312','ID00004187','ID00007233','ID00000416','014014606','SHYQ00001940','ID00003344') and ISNULL(stafftotalyeji,0)<ISNULL(staffshopyeji,0)
     
     
     
   ----只有美容经理 00101    0.02 美容部实业绩	6  ,门店月度奖金
   --update   a set staffshopyeji=(ISNULL(@realbeautyeji,0)-ISNULL(realbeautyeji,0))*0.02
   --from #staff_work_salary a
   --left join  (select c.staffinid ,realbeautyeji=SUM(ISNULL(realbeautyeji,0)) from compclasstraderesult a,staffabsenceinfo b,#staff_work_salary c
			--					where staffposition='00101' and c.staffinid=b.manageno and a.compid=c.strCompId and c.strCompId=b.compid and b.absencedate  between @fromdate and @todate 
			--					and a.ddate=absencedate
			--					group by c.staffinid ) d   on a.staffinid=d.staffinid                  
   --where  staffposition='00101' and ISNULL(tichengmode,1)<>2
   

           
   ----美容顾问     00104    0.02 美容部实业绩	6  ,门店月度奖金
   --update   a set staffshopyeji=(ISNULL(@realbeautyeji,0)-ISNULL(realbeautyeji,0))*0.02
   --from #staff_work_salary a
   --left join  (select c.staffinid ,realbeautyeji=SUM(ISNULL(realbeautyeji,0)) from compclasstraderesult a,staffabsenceinfo b,#staff_work_salary c
			--					where staffposition='00104' and c.staffinid=b.manageno and a.compid=c.strCompId and c.strCompId=b.compid and b.absencedate  between @fromdate and @todate 
			--					and a.ddate=absencedate
			--					group by c.staffinid ) d   on a.staffinid=d.staffinid                  
   --where  staffposition='00104' and ISNULL(tichengmode,1)<>2


   ----美容经理 014107 陈俊
   --update   a set staffshopyeji=(ISNULL(@realbeautyeji,0)-ISNULL(realbeautyeji,0))*0.015 ,workdays=fworkdays ,staffbasesalary=ISNULL(staffbasesalary,0)*(datediff(day,@fromdate,@todate)+1-ISNULL(fworkdays,0))/(datediff(day,@fromdate,@todate)+1)
   --from #staff_work_salary a
   --left join  (select c.staffinid ,realbeautyeji=SUM(ISNULL(realbeautyeji,0)),fworkdays=count(absencedate) from compclasstraderesult a,staffabsenceinfo b,#staff_work_salary c
			--					where staffposition='00101' and c.staffinid=b.manageno and a.compid=c.strCompId and c.strCompId=b.compid and b.absencedate  between @fromdate and @todate 
			--					and a.ddate=absencedate and c.staffinid='014014420' and c.strCompId='014'
			--					group by c.staffinid ) d   on a.staffinid=d.staffinid                  
   --where  a.staffinid='014014420' and a.strCompId='014' and ISNULL(tichengmode,1)<>2
   
   --  --美容经理 033106 黄丹
   --update   a set staffshopyeji=(ISNULL(@realbeautyeji,0)-ISNULL(realbeautyeji,0))*0.015 ,workdays=fworkdays ,staffbasesalary=ISNULL(staffbasesalary,0)*(datediff(day,@fromdate,@todate)+1-ISNULL(fworkdays,0))/(datediff(day,@fromdate,@todate)+1)
   --from #staff_work_salary a
   --left join  (select c.staffinid ,realbeautyeji=SUM(ISNULL(realbeautyeji,0)),fworkdays=count(absencedate) from compclasstraderesult a,staffabsenceinfo b,#staff_work_salary c
			--					where staffposition='00101' and c.staffinid=b.manageno and a.compid=c.strCompId and c.strCompId=b.compid and b.absencedate  between @fromdate and @todate 
			--					and a.ddate=absencedate and c.staffinid='ID00003520' and c.strCompId='033'
			--					group by c.staffinid ) d   on a.staffinid=d.staffinid                  
   --where  a.staffinid='ID00003520' and a.strCompId='033' and ISNULL(tichengmode,1)<>2
   
    
   ----美容经理 014107 陈俊
   --update   a set staffshopyeji=(ISNULL(@realbeautyeji,0)-ISNULL(realbeautyeji,0))*0.015 ,workdays=fworkdays 
   --from #staff_work_salary a
   --left join  (select c.staffinid ,realbeautyeji=SUM(ISNULL(realbeautyeji,0)),fworkdays=count(absencedate) from compclasstraderesult a,staffabsenceinfo b,#staff_work_salary c
			--					where staffposition='00101' and c.staffinid=b.manageno and a.compid=c.strCompId and c.strCompId=b.compid and b.absencedate  between @fromdate and @todate 
			--					and a.ddate=absencedate and c.staffinid='014014420' and c.strCompId='041'
			--					group by c.staffinid ) d   on a.staffinid=d.staffinid                  
   --where  a.staffinid='014014420' and a.strCompId='041' and ISNULL(tichengmode,1)<>2
   
   --  --美容经理 033106 黄丹
   --update   a set staffshopyeji=(ISNULL(@realbeautyeji,0)-ISNULL(realbeautyeji,0))*0.015 ,workdays=fworkdays 
   --from #staff_work_salary a
   --left join  (select c.staffinid ,realbeautyeji=SUM(ISNULL(realbeautyeji,0)),fworkdays=count(absencedate) from compclasstraderesult a,staffabsenceinfo b,#staff_work_salary c
			--					where staffposition='00101' and c.staffinid=b.manageno and a.compid=c.strCompId and c.strCompId=b.compid and b.absencedate  between @fromdate and @todate 
			--					and a.ddate=absencedate and c.staffinid='ID00003520' and c.strCompId='046'
			--					group by c.staffinid ) d   on a.staffinid=d.staffinid                  
   --where  a.staffinid='ID00003520' and a.strCompId='046' and ISNULL(tichengmode,1)<>2
   
   -- update   a set staffshopyeji=(ISNULL(@realbeautyeji,0)-ISNULL(realbeautyeji,0))*0.015 ,workdays=fworkdays 
   --from #staff_work_salary a
   --left join  (select c.staffinid ,realbeautyeji=SUM(ISNULL(realbeautyeji,0)),fworkdays=count(absencedate) from compclasstraderesult a,staffabsenceinfo b,#staff_work_salary c
			--					where staffposition='00101' and c.staffinid=b.manageno and a.compid=c.strCompId and c.strCompId=b.compid and b.absencedate  between @fromdate and @todate 
			--					and a.ddate=absencedate and c.staffinid='ID00003520' and c.strCompId='048'
			--					group by c.staffinid ) d   on a.staffinid=d.staffinid                  
   --where  a.staffinid='ID00003520' and a.strCompId='048' and ISNULL(tichengmode,1)<>2
   
 
   

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
   
   
    update   a set staffshopyeji=(ISNULL(@total_yeji,0)-ISNULL(totalyeji,0))*0.002  
   from #staff_work_salary a
   left join  (select c.staffinid ,totalyeji=SUM(ISNULL(totalyeji,0)) from compclasstraderesult a,staffabsenceinfo b,#staff_work_salary c
								where staffposition='0010502' and c.staffinid=b.manageno and a.compid=c.strCompId and c.strCompId=b.compid and b.absencedate  between @fromdate and @todate 
								and a.ddate=absencedate
								group by c.staffinid ) d   on a.staffinid=d.staffinid                  
   where  staffposition='0010502' and a.staffinid='ID00006985'
   
   --大堂经理(A) 0010503   0.004总虚业绩		4
     update   a set staffshopyeji=(ISNULL(@total_yeji,0)-ISNULL(totalyeji,0))*0.004 
   from #staff_work_salary a
   left join  (select c.staffinid ,totalyeji=SUM(ISNULL(totalyeji,0)) from compclasstraderesult a,staffabsenceinfo b,#staff_work_salary c
								where staffposition='0010503' and c.staffinid=b.manageno and a.compid=c.strCompId and c.strCompId=b.compid and b.absencedate  between @fromdate and @todate 
								and a.ddate=absencedate
								group by c.staffinid ) d   on a.staffinid=d.staffinid                  
   where  staffposition='0010503' 
   
  
  
  --美容师老客补贴 (新战略)
  if exists(select 1 from compchaininfo where curcomp in ('0010104','0010102') and relationcomp=@compid and @compid not in ('028','035') )
  begin
		
	update a                                         
    set oldcustomerreward=isnull((select COUNT(distinct d.csbillid) from mconsumeinfo d with(nolock) ,dconsumeinfo e with(nolock) 
     where d.csbillid=e.csbillid and d.cscompid=e.cscompid and d.financedate between @fromdate and @todate and d.cscompid=@compid and ISNULL(reservationflag,0)=1 and ((ISNULL(e.csfirstinid,'')=a.staffinid and ISNULL(csfirstreserve,0)=1) or (ISNULL(e.cssecondinid,'')=a.staffinid and ISNULL(cssecondreserve,0)=1) or (ISNULL(e.csthirdinid,'')=a.staffinid and ISNULL(csthirdreserve,0)=1))),0)*20                        
    from #staff_work_salary a,staffinfo  b                        
    where a.staffinid=b.Manageno and b.position  in ('004','00401') 
    and ( ISNULL(curstate,0)=2   or ( ISNULL(curstate,0)=3 and   manageno in ( select manageno from staffhistory where  changetype=3 and  effectivedate between @afterFromMonthDay and @afterToMonthDay ) ))
    
    
  end 
  else
  begin
		if(@todate>'20140531')
		begin
				update a                                         
				set oldcustomerreward=isnull((select COUNT(distinct d.csbillid) from mconsumeinfo d with(nolock) ,dconsumeinfo e with(nolock) 
				where d.csbillid=e.csbillid and d.cscompid=e.cscompid and d.financedate between @fromdate and @todate and d.cscompid=@compid and ISNULL(reservationflag,0)=1 and ((ISNULL(e.csfirstinid,'')=a.staffinid and ISNULL(csfirstreserve,0)=1) or (ISNULL(e.cssecondinid,'')=a.staffinid and ISNULL(cssecondreserve,0)=1) or (ISNULL(e.csthirdinid,'')=a.staffinid and ISNULL(csthirdreserve,0)=1)) ),0)*20                        
				from #staff_work_salary a,staffinfo  b                        
				where a.staffinid=b.Manageno and b.position  in ('004','00401') 
				and ( ISNULL(curstate,0)=2   or ( ISNULL(curstate,0)=3 and   manageno in ( select manageno from staffhistory where  changetype=3 and  effectivedate between @afterFromMonthDay and @afterToMonthDay ) ))
		end
  end 
       
  --美容师补贴                        
  --美容师入职超过一年每月补贴200, 两年 400 (七天没上班没有补贴)   
  --新入职员工无补贴                     
  update a                                         
  set beatysubsidy=isnull((case when DATEDIFF ( day, b.arrivaldate ,ISNULL('20140430','') ) between 364 and 727 then 200                    
    when DATEDIFF ( day, b.arrivaldate ,ISNULL('20140430','') )> 727 then 400                        
     else 0 end),0)                        
  from #staff_work_salary a,staffinfo  b                        
  where a.staffinid=b.Manageno and b.position in ('004','00401','00402') and isnull(a.inserworkdays,0)>=23   and ISNULL(curstate,0)=2         
  
  update a set  beatysubsidy=0
  from #staff_work_salary a,( select manageno,effectivedate
 from (select  row_number() over( PARTITION BY manageno order by min(ISNULL(effectivedate,0)) asc)  fid,manageno,effectivedate
 from  staffhistory b,#staff_work_salary c 
 where c.staffinid=b.Manageno and newpostion in ('004','00401','00402') and oldpostion not in ('004','00401','00402')  group by manageno,oldsalary,effectivedate,oldpostion) e where fid=1  ) d
 where a.staffinid=d.manageno and d.effectivedate>'20130430'
 

  update a                                         
  set beatysubsidy=0                       
  from #staff_work_salary a,staffinfo  b                        
  where a.staffinid=b.Manageno and b.position in ('004','00401','00402')        
  and strCompId  in ('008','018')  
       
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
   --update a set basestaffpayamt=isnull(outamt ,0)-isnull(inseramt,0)
   --from #staff_work_salary a,staffbaseamt b
   --where a.staffinid=b.staffinid  and ISNULL(a.stafftype,0)=0
   --罚款                                               
  update #staff_work_salary                                         
  set staffdebit=isnull((select sum(isnull(rewardamt,0)) from mstaffrewardinfo a,dstaffrewardinfo b where a.entrycompid=b.entrycompid and a.entrybillid=b.entrybillid and handcompid=@compid and isnull(b.billflag,0)=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype  in ('01','02','05') ),0)                                 
	--代扣                                       
  update #staff_work_salary                                         
  set staffdaikou=isnull((select sum(isnull(rewardamt,0)) from mstaffrewardinfo a,dstaffrewardinfo b where a.entrycompid=b.entrycompid and a.entrybillid=b.entrybillid and handcompid=@compid and isnull(b.billflag,0)=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype ='12' ),0)                                          
  --迟到早退      
  update #staff_work_salary                                         
  set latdebit=isnull((select sum(isnull(rewardamt,0)) from mstaffrewardinfo a,dstaffrewardinfo b where a.entrycompid=b.entrycompid and a.entrybillid=b.entrybillid and handcompid=@compid and isnull(b.billflag,0)=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype='04' ),0)                                    
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
  --保底后奖金
  update #staff_work_salary                                         
  set afterstaffreward=isnull((select sum(isnull(rewardamt,0)) from mstaffrewardinfo a,dstaffrewardinfo b where a.entrycompid=b.entrycompid and a.entrybillid=b.entrybillid and handcompid=@compid and isnull(b.billflag,0)=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype in ('17') ),0)                                 
  --员工年终奖 yearreward
  update #staff_work_salary                                         
  set yearreward=isnull((select sum(isnull(rewardamt,0)) from mstaffrewardinfo a,dstaffrewardinfo b where a.entrycompid=b.entrycompid and a.entrybillid=b.entrybillid and handcompid=@compid and isnull(b.billflag,0)=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype in ('13') ),0)                                 
  --差异调整          
  update #staff_work_salary                                         
  set staffamtchange=isnull((select sum( case when isnull(entryflag,0)=0 then isnull(rewardamt,0) else isnull(rewardamt,0)*(-1) end) from mstaffrewardinfo a,dstaffrewardinfo b where a.entrycompid=b.entrycompid and a.entrybillid=b.entrybillid and handcompid=@compid and isnull(b.billflag,0)=1 and entrydate>=@fromdate and entrydate<=@todate
  and handstaffinid=staffinid and entrytype='10' ),0)                                 
  --责任金抵押
  --update #staff_work_salary                                         
  --set zerenjincost=isnull((select sum(isnull(rewardamt,0)) from mstaffrewardinfo a,dstaffrewardinfo b where a.entrycompid=b.entrycompid and a.entrybillid=b.entrybillid and handcompid=@compid and isnull(b.billflag,0)=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype ='15' ),0)                                 
  ----责任金返回
  update #staff_work_salary                                         
  set zerenjinback=isnull((select sum(isnull(rewardamt,0)) from mstaffrewardinfo a,dstaffrewardinfo b where a.entrycompid=b.entrycompid and a.entrybillid=b.entrybillid and handcompid=@compid and isnull(b.billflag,0)=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype='16' ),0)                                 
  --门店补贴           
  update #staff_work_salary                                         
  set storesubsidy=isnull((select sum(isnull(rewardamt,0)) from mstaffrewardinfo a,dstaffrewardinfo b where a.entrycompid=b.entrycompid and a.entrybillid=b.entrybillid and handcompid=@compid and isnull(b.billflag,0)=1 and entrydate>=@fromdate and entrydate<=@todate and handstaffinid=staffinid and entrytype in ('06','14') ),0)                                 
    
       

   
    
    -- 烫染督导 008 完成额度>指标额度 奖励2000 
   update   a set staffreward=isnull(staffreward,0)+2000*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0)
   from #staff_work_salary a 
   where staffposition ='008' and isnull(@trh_yeji,0)>ISNULL(@ttrhyeji,0)  and ISNULL(a.stafftype,0)=0 and ISNULL(@ttrhyeji,0)>0
   and  isnull(a.workdays,0)<7 
   
	--南昌不需要
	----if not exists (select 1 from  compchaininfo where curcomp='0010201' and relationcomp=@compid)
 --   --begin
	--	--美容顾问+美容经理完成额度/指标额度 *1800 *到勤率 90% 总虚业绩 
	--	update   a set staffreward=isnull(staffreward,0)+isnull(1800*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0),0)
	--	from #staff_work_salary a 
	--	where staffposition in ('00101','00104') 
	--	and ISNULL(a.stafftype,0)=0 and ISNULL(@ttotalyeji,0)>0
	--	 and ISNULL(@total_yeji,0)/isnull(@ttotalyeji,1)>=0.9  
 --  -- end      
   --店长指标奖励
   
  -- select @ttotalyeji,@trealtotalyeji,@ttrhyeji,@tcostcount,@tstaffleavelcount
     --总虚业绩指标 做业绩店长 2000的指标 不做业绩的店长 1000的指标 (缺勤不能超过7天)
     update  a set staffreward= ISNULL(staffreward,0)+ISNULL(case when isnull(b.stafftype,1)=1 then 1000 else 2000 end,0)
     from   #staff_work_salary a,shopownerinfo b
     where a.staffinid=b.staffinid and b.compid=@compid and b.mmonth=SUBSTRING(@fromdate,1,6)
     and  isnull(a.workdays,0)<7  
     and isnull(@total_yeji,0)>=ISNULL(@ttotalyeji,0) and ISNULL( @ttotalyeji,0)>0
  
     --总实业绩指标 做业绩店长 2000的指标 不做业绩的店长 1000的指标 (缺勤不能超过7天)
     update  a set staffreward= ISNULL(staffreward,0)+ISNULL(case when isnull(b.stafftype,1)=1 then 1000 else 2000 end,0)
     from   #staff_work_salary a,shopownerinfo b
     where a.staffinid=b.staffinid and b.compid=@compid and b.mmonth=SUBSTRING(@fromdate,1,6)
     and  isnull(a.workdays,0)<7  
     and isnull(@realtotal_yeji,0)>=ISNULL(@trealtotalyeji,0) and ISNULL(@trealtotalyeji,0)>0
     
     
	 --美容虚业绩指标达到后奖励2000
     update  a set staffreward= ISNULL(staffreward,0)+2000    
     from   #staff_work_salary a  
     where staffposition in ('00101','00104')  
     and  isnull(a.workdays,0)<7      
     and isnull(@beaut_yeji,0)>=ISNULL(@tbeatyyeji,0) and ISNULL( @tbeatyyeji,0)>0  
     
      --美容虚业绩指标达到后超出部分4%
     update  a set staffshopyeji= ISNULL(staffshopyeji,0)+0.04*(isnull(@beaut_yeji,0)-ISNULL(@tbeatyyeji,0))    
     from   #staff_work_salary a  
     where staffposition in ('00101','00104')  
     and  isnull(a.workdays,0)<7      
     and isnull(@beaut_yeji,0)>=ISNULL(@tbeatyyeji,0) and ISNULL( @tbeatyyeji,0)>0  
          
     /*--总客单数
     update  a set staffreward= ISNULL(staffreward,0)+ISNULL(case when isnull(b.stafftype,1)=1 then 1000 else 2000 end,0)
     from   #staff_work_salary a,shopownerinfo b
     where a.staffinid=b.staffinid and b.compid=@compid and b.mmonth=SUBSTRING(@fromdate,1,6)
     and  isnull(a.workdays,0)<7  
     and isnull(@costcount,0)>=ISNULL(@tcostcount,0) and ISNULL(@tcostcount,0)>0*/
     
     
     --离职率
     --update  a set staffreward= ISNULL(staffreward,0)+ISNULL(case when isnull(b.stafftype,1)=1 then 1000 else 2000 end,0)
     --from   #staff_work_salary a,shopownerinfo b
     --where a.staffinid=b.staffinid and b.compid=@compid and b.mmonth=SUBSTRING(@fromdate,1,6)
     --and  isnull(a.workdays,0)<7  
     --and isnull(@staffleavelcount,0)<=ISNULL(@tstaffleavelcount,0) and ISNULL(@tstaffleavelcount,0)>0
     
     
     update  a set staffreward= ISNULL(staffreward,0)+ISNULL(case when isnull(b.stafftype,1)=1 then 1000 else 2000 end,0)
     from   #staff_work_salary a,shopownerinfo b
     where a.staffinid=b.staffinid and b.compid=@compid and b.mmonth=SUBSTRING(@fromdate,1,6)
     
     
      update a set staffcurstate=b.curstate
  from #staff_work_salary a,staffinfo b
  where a.staffinid=b.manageno
  
  /*update a set staffcurstate=4
  from #staff_work_salary a,( select person_inid,staffyeji=SUM(isnull(staffyeji,0)) from staff_work_salary where compid= @compid and salary_date between '20140701' and '20140715' group by person_inid) b
  where a.staffinid=b.person_inid and isnull(a.businessflag,0)=1 and ISNULL(staffcurstate,2)=2 and ISNULL(b.staffyeji,0)=0
 
   update #staff_work_salary set staffcurstate=4
   where staffinid not in ( select person_inid  from staff_work_salary where compid= @compid and salary_date between '20140701' and '20140715' group by person_inid) 
   and isnull(businessflag,0)=1 and ISNULL(staffcurstate,2)=2 and  (staffno like @compid+'3__' or staffno like @compid+'4__'  or staffno like @compid+'5__'  or staffno like @compid+'6__'  or staffno like @compid+'8__' )
 */
    if(@compid not  in ('301','302','303'))
    begin
		
		
		update #staff_work_salary set staffcurstate=4 
		where staffinid not in( select manageno from staffkqrecordinfo,staffinfo where  personid=fingerno and ISNULL(stafftype,0)=0 and ddate between @afterFromMonthDay and  @afterToMonthDay group by manageno  )
		and isnull(businessflag,0)=1 and ISNULL(staffcurstate,2)=2 and  (staffno like @compid+'3__' or staffno like @compid+'4__'  or staffno like @compid+'5__'  or staffno like @compid+'6__'  or staffno like @compid+'8__' )   
    end
   
   
   --美容师 到薪资计算结束日期未满3个月的计3000的考勤补贴
   UPDATE a 
   set staffsubsidy=3000*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0)
   from #staff_work_salary a,staffhistory b 
   where staffposition ='00402'  and ISNULL(computedays,0)>0
   and staffinid=manageno and changetype=4 and oldcompid=@compid and  datediff(MONTH,effectivedate,@todate)<3 and ISNULL(staffcurstate,0) not in (3,4)
   
   --烫染师1200的补贴
   UPDATE #staff_work_salary set staffsubsidy=1200*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0)
   where staffposition in ('00901','00902','00903','00904') and datediff(MONTH,arrivaldate,@todate)<3 and ISNULL(computedays,0)>0 and ISNULL(staffcurstate,0) not in (3,4)
   
   

      
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
  where a.staffinid=d.staffinid and ISNULL(staffcurstate,0) not in (3,4)
 
  
   drop table #staffsubsidy
   

  update a set staffsubsidy=0
  from #staff_work_salary a,staffinfo b
  where a.staffinid=b.manageno and b.compno='99999' 

   --派遣人员门店业绩为0
   update #staff_work_salary set staffshopyeji=0 ,staffsubsidy=0 where   ISNULL(stafftype,0)=1
   
   
     ----离职异动单增加离职种类 正常离职,自动离职, 自动离职需扣款600                                     
    update #staff_work_salary set leaveldebit=600  where staffinid in (select staffmangerno from staffchangeinfo where changetype=1 and leaveltype=2 and validatestartdate  between @fromdate and @todate  and billflag=8)                      
    update #staff_work_salary       
      set needpaysalary=ISNULL(stafftotalyeji,0)+ ISNULL(staffshopyeji,0)+      
         ISNULL(staffbasesalary,0)+ISNULL(beatysubsidy,0)+ISNULL(staffreward,0)+ISNULL(oldcustomerreward,0),      
    factpaysalary=ISNULL(stafftotalyeji,0)+ ISNULL(staffshopyeji,0)+      
         ISNULL(staffbasesalary,0)+ISNULL(beatysubsidy,0)+ISNULL(staffreward,0)+ISNULL(oldcustomerreward,0) 
      
    --有补贴的人
    update #staff_work_salary set staffsubsidy =(case when  isnull(staffsubsidy,0)<=ISNULL(needpaysalary,0) then 0 else  isnull(staffsubsidy,0)-(ISNULL(needpaysalary,0)) end)
    
	update #staff_work_salary set  needpaysalary=ISNULL(needpaysalary,0)+ isnull(staffsubsidy,0),factpaysalary=isnull(factpaysalary,0)+ isnull(staffsubsidy,0)
     
   --美容经理,美容顾问 税前-差异调整 不到5000 补成5000     
   --update   a set   factpaysalary=5000*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0),
			--		needpaysalary=5000*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0),
   --staffsubsidy=5000*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0)-ISNULL(needpaysalary,0)
   --from #staff_work_salary a 
   --where staffposition in ('00101','00104') 
   --and (5000*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0))>ISNULL(needpaysalary,0) and ISNULL(computedays,0)>0
   --and ISNULL(a.stafftype,0)=0 and ISNULL(staffcurstate,0)<>3 and ISNULL(tichengmode,1)<>2
   --and staffinid not in ('SHYQ00000540','ID00004184','009009428','SHYQ00000691','SHYQ00001264','ID00000908','ID00004144','ID00003853','014014420','ID00006354','ID00002142','SHYQ00001836','SHYQ00001958','ID00005013','ID00005073','ID00001192','ID00007056','ID00000113','ID00006330')
   -- 不拿美容补贴SHYQ00000540  ID00004184 009009428 SHYQ00000691 SHYQ00001264 ID00000908 ID00004144 ID00003853 014014420 ID00006354 ID00002142 SHYQ00001836 SHYQ00001958 ID00005013 ID00005073 ID00001192 ID00007056  ID00000113 ID00006330
   --督导 税前-差异调整 不到5000 补成5000     
   update   a set	needpaysalary=5000*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0),
					factpaysalary=5000*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0),
					staffsubsidy=5000*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0)-ISNULL(needpaysalary,0)
   from #staff_work_salary a 
   where staffposition='008' and ISNULL(@trh_yeji,0) >=isnull(@ttrhyeji,0) 
   and ISNULL(needpaysalary,0)<5000*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0) and ISNULL(computedays,0)>0
    and ISNULL(a.stafftype,0)=0 and ISNULL(staffcurstate,0)<>3
   
    --调整额度(个人差异调整+年终奖-社保)
   update #staff_work_salary set  needpaysalary=ISNULL(needpaysalary,0)+ isnull(staffamtchange,0)+ISNULL(storesubsidy,0)+ISNULL(yearreward,0)-ISNULL(staffsocials,0)-      
         ISNULL(leaveldebit,0)-ISNULL(staffdebit,0)-ISNULL(latdebit,0)-ISNULL(otherdebit,0)+ISNULL(basestaffpayamt,0)+ISNULL(afterstaffreward,0),
								  factpaysalary=isnull(factpaysalary,0)+ isnull(staffamtchange,0)+ISNULL(storesubsidy,0)+ISNULL(yearreward,0)-ISNULL(staffsocials,0)-      
         ISNULL(leaveldebit,0)-ISNULL(staffdebit,0)-ISNULL(latdebit,0)-ISNULL(otherdebit,0)+ISNULL(basestaffpayamt,0)+ISNULL(afterstaffreward,0),
								  staffreward=ISNULL(yearreward,0)+ISNULL(staffreward,0)+ISNULL(afterstaffreward,0)
   --update #staff_work_salary set  needpaysalary=ISNULL(needpaysalary,0)+ isnull(staffamtchange,0)+ISNULL(storesubsidy,0),factpaysalary=isnull(factpaysalary,0)+ isnull(staffamtchange,0)+ISNULL(storesubsidy,0)  
  
   --新增区域经理的基本工资
    --insert #staff_work_salary(strCompId,strCompName,staffno,staffinid,staffname,staffposition,staffpositionname,staffpcid,staffbankaccountno,staffbasesalary,needpaysalary,factpaysalary)
    --select a.compid,compname,a.empid,a.manageno,b.staffname,b.position,parentcodevalue,b.pccid,b.bankno,sharesalary,sharesalary,sharesalary=ISNULL(sharesalary,0)*(datediff(day,@fromdate,@todate)+1)/COUNT(absencedate)
    --from managershareinfo a,staffinfo b,commoninfo,companyinfo c,staffabsenceinfo d
    --where a.compid=@compid and a.manageno not in (select staffinid from #staff_work_salary ) and a.manageno=b.manageno
    --and infotype='GZGW' and parentcodekey=b.position and a.compid=c.compno
    --and a.compid=d.compid and d.manageno=a.manageno and  absencedate between @fromdate and @todate
    --group by a.compid,compname,a.empid,a.manageno,b.staffname,b.position,parentcodevalue,b.pccid,b.bankno,sharesalary,sharesalary,sharesalary

    insert #staff_work_salary(strCompId,strCompName,staffno,staffinid,staffname,staffposition,staffpositionname,staffpcid,staffbankaccountno,staffbasesalary,needpaysalary,factpaysalary,computedays,managershareflag)
    select a.compid,compname,a.empid,a.manageno,b.staffname,b.position,parentcodevalue,b.pccid,b.bankno,sharesalary,sharesalary,sharesalary,datediff(day,@fromdate,@todate)+1,1
    from managershareinfo a,staffinfo b,commoninfo,companyinfo c
    where a.compid=@compid and a.manageno not in (select staffinid from #staff_work_salary ) and a.manageno=b.manageno
    and infotype='GZGW' and parentcodekey=b.position and a.compid=c.compno and ISNULL(stafftype,0)=0
   
   --计算区域经理缺勤
	update a set workdays=ISNULL((  select COUNT(absencedate) from staffabsenceinfo where   compid=@compid and manageno=staffinid and  absencedate between @fromdate and @todate ),0)
	from #staff_work_salary a where ISNULL(managershareflag,0)=1
	
	
	--计算缺勤工资
  
   update #staff_work_salary set staffbasesalary=ISNULL(staffbasesalary,0)*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0),
							     needpaysalary=ISNULL(needpaysalary,0)*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0),
							     factpaysalary=ISNULL(factpaysalary,0)*(ISNULL(computedays,0)-ISNULL(workdays,0))/ISNULL(computedays,0)  where ISNULL(computedays,0)>0  and ISNULL(managershareflag,0)=1
    
    

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
		if(@total_salary>6500)                                                          
			exec upg_caculate_personal_tax 3500,6500,@tmpTax output,@tmpIncome output                                                                    
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
  
   update #staff_work_salary set strCompName='',basestaffamt=0,factpaysalary=factpaysalary-ISNULL(staydebit,0)-ISNULL(studydebit,0)-ISNULL(staffcost,0)-ISNULL(staffdaikou,0)+ISNULL(zerenjinback,0)-ISNULL(zerenjincost,0)     
  from #staff_work_salary where strCompId= '99999'  
  
  update a  set markflag=1
  from #staff_work_salary a,staffasarymark b
  where a.staffinid=b.manageno and a.strCompId=b.compid and absencedate=SUBSTRING(@todate,1,6)
  
  update #staff_work_salary set factpaysalary=0,needpaysalary=0 where ISNULL(factpaysalary,0)<0
  
  
 
  select strCompId,strCompName,staffno,staffinid,staffname,staffposition,staffpositionname,staffpcid,staffbankaccountno,workdays,computedays,      
         stafftotalyeji,staffshopyeji,staffbasesalary,beatysubsidy,oldcustomerreward,leaveldebit,staffsubsidy,staffdebit,staffdaikou,latdebit,staffcost,      
         staffreward,otherdebit,staffsocials,needpaysalary,staydebit,studydebit,salarydebit,factpaysalary,staffmark ,  
         basestaffamt,staffsocialsource,staffamtchange,stafftargetreward,basestaffpayamt,storesubsidy,yearreward ,markflag=isnull(markflag,0),staffcurstate,zerenjinback,zerenjincost
  from #staff_work_salary  where staffno not in (RTRIM(strCompId)+'000',RTRIM(strCompId)+'300',RTRIM(strCompId)+'400',RTRIM(strCompId)+'500',RTRIM(strCompId)+'600') 
  order by staffcurstate,staffno      
  drop table #staff_work_salary      
end 