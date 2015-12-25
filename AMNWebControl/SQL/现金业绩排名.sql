  declare @tocompid varchar(10)  
  declare cur_each_comp cursor for  
  select curcompno from compchainstruct where complevel=4    order by curcompno
  open cur_each_comp  
  fetch cur_each_comp into @tocompid  
  while @@fetch_status = 0  
  begin  
	 exec upg_all_personal_comm_paymode_04 @tocompid,'20140401','20140430',3
   fetch cur_each_comp into @tocompid  
  end  
  close cur_each_comp  
  deallocate cur_each_comp  
  
  select compno,compname,staffno,staffname,parentcodevalue,convert(numeric(20,1),staffyeji),fid
  from  (select row_number() over(PARTITION BY compno order by max(ISNULL(staffyeji,'')) desc)  fid,compno,position,compname,staffno,staffname,staffyeji
   from ( select  b.compno,c.compname, b.staffno,b.staffname,b.position,staffyeji=SUM(ISNULL(staffyeji,0))
   from allstaff_work_analysis_04 a,staffinfo b,companyinfo c
    where mmonth='20140401' and a.person_inid=b.manageno  and b.compno=c.compno  and b.position in ('004','00401','00402')
    group by b.compno,c.compname, b.staffno,b.position,b.staffname) as ff
    group by compno,compname,staffno,staffname,position,staffyeji ) ee,commoninfo gg
    where fid<=5 and gg.infotype='GZGW' and gg.parentcodekey=position 
    order by  compno,fid
    
  select  top 5 b.compno,c.compname, b.staffno,b.staffname,b.position,sstaffyeji=convert(numeric(20,1),SUM(ISNULL(staffyeji,0)))
   from allstaff_work_analysis_04 a,staffinfo b,companyinfo c
    where mmonth='20140401' and a.person_inid=b.manageno  and b.compno=c.compno  and b.position in ('004','00401','00402')
    and b.staffno not like '%400'  and ISNULL(stafftype,0)=0
    group by b.compno,c.compname, b.staffno,b.position,b.staffname
    order by sstaffyeji desc
    
    
     select  top 5 b.compno,c.compname, b.staffno,b.staffname,b.position,sstaffyeji=convert(numeric(20,1),SUM(ISNULL(staffyeji,0)))
   from allstaff_work_analysis_04 a,staffinfo b,companyinfo c
    where mmonth='20140401' and a.person_inid=b.manageno  and b.compno=c.compno  and b.position in ('003','006','007','00701','00702')
    and b.staffno not like '%400' and ISNULL(stafftype,0)=0
    group by b.compno,c.compname, b.staffno,b.position,b.staffname
    order by sstaffyeji desc
    
       select  top 5 b.compno,c.compname, b.staffno,b.staffname,b.position,sstaffyeji=convert(numeric(20,1),SUM(ISNULL(staffyeji,0)))
   from allstaff_work_analysis_04 a,staffinfo b,companyinfo c
    where mmonth='20140401' and a.person_inid=b.manageno  and b.compno=c.compno  and b.position in ('008','00901','00902','00903','00904')
    and b.staffno not like '%400' and ISNULL(stafftype,0)=0
    group by b.compno,c.compname, b.staffno,b.position,b.staffname
    order by sstaffyeji desc
    
    
     select  top 5 b.compno,c.compname, b.staffno,b.staffname,b.position,sstaffyeji=convert(numeric(20,1),SUM(ISNULL(staffyeji,0)))
   from staff_work_salary a,staffinfo b,companyinfo c
    where salary_date between '20140401' and '20140430' 
    and a.person_inid=b.manageno  and b.compno=c.compno  and b.position in ('004','00401','00402')
    and b.staffno not like '%400'  and ISNULL(stafftype,0)=0
    group by b.compno,c.compname, b.staffno,b.position,b.staffname
    order by sstaffyeji desc
    
    
         select  top 5 b.compno,c.compname, b.staffno,b.staffname,b.position,sstaffyeji=convert(numeric(20,1),SUM(ISNULL(staffyeji,0)))
   from staff_work_salary a,staffinfo b,companyinfo c
    where salary_date between '20140401' and '20140430' 
    and a.person_inid=b.manageno  and b.compno=c.compno  and b.position in ('003','006','007','00701','00702')
    and b.staffno not like '%400'  and ISNULL(stafftype,0)=0
    group by b.compno,c.compname, b.staffno,b.position,b.staffname
    order by sstaffyeji desc
    
    
         select  top 5 b.compno,c.compname, b.staffno,b.staffname,b.position,sstaffyeji=convert(numeric(20,1),SUM(ISNULL(staffyeji,0)))
   from staff_work_salary a,staffinfo b,companyinfo c
    where salary_date between '20140401' and '20140430' 
    and a.person_inid=b.manageno  and b.compno=c.compno  and b.position in ('008','00901','00902','00903','00904')
    and b.staffno not like '%400'  and ISNULL(stafftype,0)=0
    group by b.compno,c.compname, b.staffno,b.position,b.staffname
    order by sstaffyeji desc