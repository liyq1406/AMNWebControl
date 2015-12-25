alter procedure upg_get_empinfo_by_date_comps                
(                
  @compid varchar(10),                
  @datefrom varchar(8),                
  @dateto varchar(8)                
)                
as                
begin                
 if(isnull(@datefrom,'')='') return;                
 create table #empinfobydate(                
    seqno int identity not null,                
    inid varchar(20) null,                
    empid varchar(20) null,                
    datefrom varchar(8) null,                
    dateto  varchar(8) null,                
    position varchar(10) null,                
    salary  float null,                
    sharetype varchar(5) null,                
    sharerate float null,                
    deducttax int null,                 
 )                
 declare @curdate varchar(8)                
 declare @tomorrow varchar(8)                
 declare @datetoplus varchar(8)                 
 select @curdate=substring(convert(varchar(20),getdate(),102),1,4) + substring(convert(varchar(20),getdate(),102),6,2)+ substring(convert(varchar(20),getdate(),102),9,2)                
 select @tomorrow= substring(convert(varchar(20),dateadd(day,1,getdate()),102),1,4)                 
      + substring(convert(varchar(20),dateadd(day,1,getdate()),102),6,2)                
      + substring(convert(varchar(20),dateadd(day,1,getdate()),102),9,2)                
 select @datetoplus= substring(convert(varchar(20),dateadd(day,1,@dateto),102),1,4)                   
      + substring(convert(varchar(20),dateadd(day,1,@dateto),102),6,2)                  
      + substring(convert(varchar(20),dateadd(day,1,@dateto),102),9,2)                  
                 
 if(@datefrom = @dateto and @datefrom=@curdate)                 
 begin                
    insert into #empinfobydate(inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)                
    select manageno,staffno,@curdate,@tomorrow,position,basesalary,resulttye,resultrate,salaryflag                
    from staffinfo with (NOLOCK)                
    where compno=@compid          
                    
    select @compid,inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax                
    from #empinfobydate                
    return                
 end                
                
CREATE TABLE    #staffhistory                    
(      
 seqno   int identity    NOT NULL,   --序号      
 manageno  varchar(20)      NULL,   --内部管理编号       
    changetype  varchar(20)         NULL,   --异动类型 0本店调动  1：跨店调动  2：薪资调整  3:员工离职 4：入职申请  5：重回公司      
 oldcompid  varchar(10)         NULL,   --老门店编号       
 oldempid  varchar(20)         NULL,   --老员工编号      
 olddepid  varchar(10)   null, --老部门      
 oldpostion  varchar(10)          NULL,   --老门店职位      
    oldsalary  float               NULL,   --原工资      
    oldyjtype  varchar(5)          NULL,   --原业绩方式      
    oldyjrate  float               NULL,   --原业绩系数      
 newcompid  varchar(10)         NULL,   --新门店编号       
 newempid  varchar(20)         NULL,   --新员工编号      
 newdepid  varchar(10)   null, --新部门      
 newpostion  varchar(10)          NULL,   --新门店职位      
    newsalary  float               NULL,   --新工资      
    newyjtype  varchar(5)          NULL,   --新业绩方式      
    newyjrate  float               NULL,   --新业绩系数      
    effectivedate varchar(8)          NULL,   --实际生效日期      
 optionbill  varchar(20)   null, --单据编号      
)              
  insert into #staffhistory(manageno,changetype,oldcompid,oldempid,olddepid,oldpostion,oldsalary,oldyjtype,oldyjrate,      
 newcompid,newempid,newdepid,newpostion,newsalary,newyjtype,newyjrate,effectivedate,optionbill)                
  select manageno,changetype,oldcompid,oldempid,olddepid,oldpostion,oldsalary,oldyjtype,oldyjrate,      
 newcompid,newempid,newdepid,newpostion,newsalary,newyjtype,newyjrate,effectivedate,optionbill      
  from staffhistory with (NOLOCK)                
  where oldcompid=@compid --and hal19c between @datefrom and @dateto     
     or (changetype='5' and newcompid=@compid) or (changetype='1' and newcompid=@compid)        
     
  --过滤掉“从本店做跨店调动到本店的错误记录”              
  delete #staffhistory where changetype='1' and newcompid=oldcompid               
  insert into #empinfobydate(inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)              
  select manageno,staffno,@datefrom,@datetoplus,position,basesalary,resulttye,resultrate,salaryflag                 
  from staffinfo with (NOLOCK)                
  where manageno not in (select manageno from #staffhistory) and compno=@compid               
    and isnull(curstate,'')<>'1'    --wjg 2009/10/29 不包括未到职的新员工              
      
      
  declare @originDate varchar(8)                
  select @originDate = '20090101'--min(hal19c) from #ham12                
  declare @changeStartDate varchar(8),@lastemp varchar(20),@lasttype varchar(20),@lastnewcompid varchar(10),@lastdatefrom varchar(8)                  
  set @changeStartDate=@originDate--@datefrom                
  set @lastemp=''                
  set @lasttype=''                
  set @lastnewcompid=''              
  set @lastdatefrom=''              
  declare @isfirst int               
  set @isfirst = 1                
  declare @inid varchar(20),@empid varchar(20),@validdate varchar(8),@position varchar(10),@changetype varchar(20)                
  declare @salary float,@sharetype varchar(5),@sharerate float,@deducttax int                
  declare @newcompid varchar(10),@newempid varchar(20),@newposition varchar(10),@newsalary float              
  declare @newsharetype varchar(5),@newsharerate float,@newdeducttax int                
                 
  declare cur_staffhistory cursor for                 
  select manageno,changetype,oldempid,effectivedate,oldpostion,oldsalary,oldyjtype,oldyjrate,0,newempid,newpostion,newsalary,newyjtype,newyjrate,0,newcompid                
  from #staffhistory order by manageno ASC,effectivedate ASC                
  open cur_staffhistory                
  fetch from cur_staffhistory into @inid,@changetype,@empid,@validdate,@position,@salary,@sharetype,@sharerate,@deducttax,@newempid,                
    @newposition,@newsalary,@newsharetype,@newsharerate,@newdeducttax,@newcompid                
  while(@@fetch_status=0)                
  begin                
            
  if(@inid <> @lastemp )                
  begin                
   if(@changeStartDate<=@dateto)                 
   begin                
    insert into #empinfobydate(inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)                
    select manageno,staffno,@changeStartDate,@datetoplus,position,basesalary,resulttye,resultrate,salaryflag                 
    from staffinfo WITH (nolock) where manageno=@lastemp and @lasttype in ('0','2','4')    and ISNULL(stafftype,0)=0   --haa34c加索引                
              
    if((@lasttype='1' and @lastnewcompid=@compid) or (@lasttype='5'))              
    begin              
     update #empinfobydate set dateto=@datetoplus               
     where inid=@lastemp and dateto=@changeStartDate and datefrom=@lastdatefrom              
    end              
                  
   end                
                   
     set @lastemp = @inid                
     set @lasttype = @changetype               
     set @lastnewcompid=@newcompid               
     --set @originDate = @validdate              
     if(@changetype='4' or @changetype='5' or (@changetype='1' and @newcompid=@compid))              
    set @changeStartDate = @validdate              
     else              
    set @changeStartDate = @originDate--@datefrom                
  end                
                  
  if(@changetype='5' or (@changetype='1' and @newcompid=@compid))                
  begin          
      
     insert into #empinfobydate(inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)                
     values(@inid,@newempid,@validdate,@validdate,@newposition,@newsalary,@newsharetype,@newsharerate,@newdeducttax)               
     set @lastdatefrom=@validdate               
  end                
  else               
  begin                
     insert into #empinfobydate(inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)                
     values(@inid,@empid,@changeStartDate,@validdate,@position,@salary,@sharetype,@sharerate,@deducttax)              
     set @lastdatefrom=@changeStartDate                
  end                
              
           
              
  set @lastemp = @inid                
  set @changeStartDate = @validdate                
  set @lasttype = @changetype                
  set @lastnewcompid=@newcompid              
  fetch from cur_staffhistory into @inid,@changetype,@empid,@validdate,@position,@salary,@sharetype,@sharerate,@deducttax,@newempid,@newposition,@newsalary,@newsharetype,@newsharerate,@newdeducttax,@newcompid                
  end                
 close cur_staffhistory                
  deallocate cur_staffhistory                 
 --                
          
  if(@changeStartDate<=@dateto)                 
  begin                
  insert into #empinfobydate(inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)                
  select manageno,staffno,@changeStartDate,@datetoplus,position,basesalary,resulttye,resultrate,salaryflag                 
  from staffinfo WITH (nolock) where manageno=@lastemp and @lasttype in ('0','2','4')  and ISNULL(stafftype,0)=0     --haa34c加索引        
                
                  
  if((@lasttype='1' and @lastnewcompid=@compid) or (@lasttype='5'))              
  begin              
   update #empinfobydate set dateto=@datetoplus               
   where inid=@lastemp and dateto=@changeStartDate and datefrom=@lastdatefrom              
  end              
                  
  end                
                
   
       
    --insert into #empinfobydate(inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)                
    --select manageno,staffno,@curdate,@tomorrow,position,basesalary,resulttye,resultrate,salaryflag                
    --from staffinfo with (NOLOCK)                
    --where compno=@compid and isnull(stafftype,0)=1 and manageno not in (select inid from #empinfobydate)            
    
    update a set datefrom=substring(@datefrom,1,6)+'01',dateto=replace(convert(varchar(10),dateadd(MONTH,1,cast(substring(@dateto,1,6)+'01' as datetime)),120) ,'-','') 
    from #empinfobydate a,  staffinfo b
    where a.inid=b.manageno and isnull(b.stafftype,0)=1  and b.compno=@compid
            
  select @compid,inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax                
  from #empinfobydate                
  where isnull(inid,'')<>''   order by      empid        
                 
  drop table #empinfobydate                
  drop table #staffhistory                
end 