alter procedure upg_hand_personManger_byday          
(        
 @fromdate varchar(8),                        
 @todate  varchar(8)        
)        
as        
begin        
 CREATE tAbLE    #staffchangeinfo             
 (        
  changecompid   varchar(10)     not null, --公司别        
  changebillid   varchar(20)  not null, --申请单号        
  changetype    int    not null, --申请类型  0-薪资调整 1--离职申请 2--入职申请 3--重回公司申请 4--请假申请,5--本店调动,6--跨店调动        
  changestaffno   varchar(20)   null, --异动前员工编号        
  appchangecompid   varchar(10)   null, --异动前申请公司        
  staffpcid    varchar(20)   null, --员工身份证        
  staffphone    varchar(20)   null,   --手机号码        
  staffmangerno   varchar(20)   null,   --员工内部编号        
  changedate    varchar(8)   null, --申请日期        
  validatestartdate  varchar(8)   null, --当changetype=0 的时候此值是可以重用的日期         
               --当changetype=1 的时候此值是实际离职日期         
               --当changetype=2 的时候此值是实际入职日期         
               --当changetype=3 的时候此值是实际重回公司日期         
               --当changetype=4 的时候此值是 请假开始日期         
               --当changetype=5 的时候此值是本店调动开始日期         
               --当changetype=6 的时候此值是跨店调动开始日期         
  validateenddate   varchar(8)   null, --当changetype=4 的时候此值是 请假结束日期 --当fhb02i=3的时候此值回家时间        
  beforedepartment  varchar(20)   null, --异动前部门        
  beforepostation   varchar(10)   null, --异动前职位        
  beforesalary   float    null, --异动前薪资        
  beforesalarytype  int     null, --异动前 0，税前 1 税后          
  beforeyejitype   varchar(5)   null,   --异动前业绩方式 1-美发业绩  2-美容业绩  3-总业绩        
  beforeyejirate   float    null,   --异动前业绩系数        
  beforeyejiamt   float    null,   --异动前业绩基数        
  aftercompid    varchar(20)   null, --异动后门店        
  afterstaffno   varchar(20)   null, --异动后工号        
  afterdepartment   varchar(20)   null, --异动后部门        
  afterpostation   varchar(10)   null, --异动后职位        
  aftersalary    float    null, --异动后薪资        
  aftersalarytype   int     null, --异动后 0，税前 1 税后          
  afteryejitype   varchar(5)   null,   --异动后业绩方式 1-美发业绩  2-美容业绩  3-总业绩        
  afteryejirate   float    null,   --异动后业绩系数        
  afteryejiamt   float    null,   --异动后业绩基数        
  leaveltype    int     null, --职类型  1 正常离职 2 自动离职        
  remark     varchar(200)  null, --备注        
          
 )        
 insert #staffchangeinfo(changecompid,changebillid,changetype,changestaffno,appchangecompid,staffpcid,staffphone,staffmangerno,        
 changedate,validatestartdate,validateenddate,beforedepartment,beforepostation,beforesalary,beforeyejiamt,beforeyejitype,beforeyejirate,        
 aftercompid,afterstaffno,afterdepartment,afterpostation,aftersalary,afteryejiamt,afteryejitype,afteryejirate,remark,leaveltype)        
 select changecompid,changebillid,changetype,changestaffno,appchangecompid,staffpcid,staffphone,staffmangerno,        
 changedate,validatestartdate,validateenddate,beforedepartment,beforepostation,beforesalary,beforeyejiamt,beforeyejitype,beforeyejirate,        
 aftercompid,afterstaffno,afterdepartment,afterpostation,aftersalary,afteryejiamt,afteryejitype,afteryejirate,remark,leaveltype        
 from staffchangeinfo where validatestartdate between @fromdate and @todate and billflag=3        
         
 declare @changecompid  varchar(10)   --异动门店        
 declare @changebillid  varchar(20)   --异动单据        
 declare @oldcompid   varchar(10)   --原门店编号                        
 declare @oldstaffno   varchar(20)         --原工号             
 declare @olddepartment  varchar(10)   --原部门           
 declare @oldposition  varchar(10)   --原职位             
 declare @oldsalary   float    --原工资          
 declare @oldyejitype  varchar(5)   --异动前业绩方式 1-美发业绩  2-美容业绩  3-总业绩        
    declare @oldyejirate  float    --异动前业绩系数        
    declare @oldyejiamt   float    --异动前业绩基数            
            
    declare @newcompid   varchar(10)   --新门店编号             
 declare @newstaffno   varchar(20)   --新工号         
 declare @newdepartment  varchar(10)   --新部门编号            
 declare @newposition  varchar(10)   --新职位           
declare @newsalary   float    --原工资          
 declare @newyejitype  varchar(5)   --异动前业绩方式 1-美发业绩  2-美容业绩  3-总业绩        
    declare @newyejirate  float    --异动前业绩系数        
    declare @newyejiamt   float    --异动前业绩基数            
    declare @leaveltype   int     --离职类型  1 正常离职 2 自动离职        
 declare @note    varchar(80)   --备注               
 declare @billtype   int     --单据类型  0-薪资调整 1--离职申请 2--入职申请 3--重回公司申请 4--请假申请,5--本店调动,6--跨店调动        
 declare @validDate   varchar(8)          --生效日期        
 declare @inlineno   varchar(20)   --员工内部编号        
        
         
 declare cursor_staffchange cursor                  
 for            
  select changecompid,changebillid,changetype,changestaffno,appchangecompid,staffmangerno,validatestartdate,beforedepartment,beforepostation,beforesalary,beforeyejitype,beforeyejirate,beforeyejiamt,        
    aftercompid,afterstaffno,afterdepartment,afterpostation,aftersalary,afteryejitype,afteryejirate,afteryejiamt,remark,leaveltype        
  from #staffchangeinfo        
 open cursor_staffchange        
 fetch cursor_staffchange into @changecompid,@changebillid,@billtype, @oldstaffno,@oldcompid,@inlineno,@validDate,@olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,        
    @newcompid,@newstaffno ,@newdepartment,@newposition,@newsalary,@newyejitype,@newyejirate,@newyejiamt,@note,@leaveltype        
 while @@fetch_status=0                        
 begin          
   --保留历史算法 0本店调动  1：跨店调动  2：薪资调整  3:员工离职 4：入职申请  5：重回公司        
   if(@billtype = 2)--入职申请                       
   begin              
    insert staffhistory(manageno,changetype,oldcompid,oldempid,olddepid,oldpostion,oldsalary,oldyjtype, oldyjrate,oldyjamt,newcompid,newempid,newdepid,newpostion,newsalary,newyjtype,newyjrate,newyjramt,effectivedate,optionbill,changemark)                
    values(@inlineno,4,@oldcompid,@oldstaffno,@olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,@newcompid,@newstaffno,        
    @newdepartment,@newposition,@newsalary,@newyejitype,@newyejirate,@newyejiamt,@validDate,@changebillid,@note)        
            
    update staffinfo set curstate='2',arrivaldate=@validDate where manageno=@inlineno   
    update staffchangeinfo set billflag=8  where changecompid=@changecompid and changebillid=@changebillid         
            
                           
   end            
   else if(@billtype = 1) --离职申请                       
   begin                  
  insert staffhistory(manageno,changetype,oldcompid,oldempid,olddepid,oldpostion,oldsalary,oldyjtype, oldyjrate,oldyjamt,newcompid,newempid,newdepid,newpostion,newsalary,newyjtype,newyjrate,newyjramt,effectivedate,optionbill,changemark)                
  values(@inlineno,3,@oldcompid,@oldstaffno,@olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,'99999',@inlineno+'LZ',        
     @olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,@validDate,@changebillid,@note)        
         
  update staffinfo set curstate='3',compno='99999',staffno=@inlineno+'LZ',leavedate=@validDate,leveltype=@leaveltype 
  where manageno=@inlineno and  compno= @oldcompid
   update staffinfo set curstate='3',compno='99999',staffno=@inlineno+'LZ',leavedate=@validDate,leveltype=@leaveltype 
  where manageno=@inlineno and  ISNULL(stafftype,0)=1       
  ---------------------------------------------------------------              
  delete sysuserinfo where   frominnerno=@inlineno       
  delete useroverall where userno=@oldstaffno    
  update staffchangeinfo set billflag=8  where changecompid=@changecompid and changebillid=@changebillid                  
   end           
   else if(@billtype = 3) --重回公司        
   begin        
    insert staffhistory(manageno,changetype,oldcompid,oldempid,olddepid,oldpostion,oldsalary,oldyjtype, oldyjrate,oldyjamt,newcompid,newempid,newdepid,newpostion,newsalary,newyjtype,newyjrate,newyjramt,effectivedate,optionbill,changemark)                
    values(@inlineno,5,@oldcompid,@oldstaffno,@olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,@newcompid,@newstaffno,        
          @newdepartment,@newposition,@newsalary,@newyejitype,@newyejirate,@newyejiamt,@validDate,@changebillid,@note)        
        
    update staffinfo set curstate='2',compno=@newcompid,staffno=@newstaffno,department=@newdepartment,position=@newposition,basesalary=@newsalary,        
          resulttye=@newyejitype,resultrate=@newyejirate,baseresult=@newyejiamt ,arrivaldate=@validDate       
     where manageno=@inlineno   and  isnull(stafftype,0)=0          
    ---------------------------------------------------------------              
            
    update staffchangeinfo set billflag=8  where changecompid=@changecompid and changebillid=@changebillid              
   end        
   else if(@billtype = 5) --本店调动        
   begin        
    insert staffhistory(manageno,changetype,oldcompid,oldempid,olddepid,oldpostion,oldsalary,oldyjtype, oldyjrate,oldyjamt,newcompid,newempid,newdepid,newpostion,newsalary,newyjtype,newyjrate,newyjramt,effectivedate,optionbill,changemark)                
    values(@inlineno,0,@oldcompid,@oldstaffno,@olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,@newcompid,@newstaffno,        
          @newdepartment,@newposition,@newsalary,@newyejitype,@newyejirate,@newyejiamt,@validDate,@changebillid,@note)        
        
    update staffinfo set staffno=@newstaffno,department=@newdepartment,position=@newposition,basesalary=@newsalary,        
          resulttye=@newyejitype,resultrate=@newyejirate,baseresult=@newyejiamt        
     where manageno=@inlineno    and  isnull(stafftype,0)=0         
    ---------------------------------------------------------------              
            
    update staffchangeinfo set billflag=8  where changecompid=@changecompid and changebillid=@changebillid            
   end        
   else if(@billtype = 6) --本店调动        
   begin        
  insert staffhistory(manageno,changetype,oldcompid,oldempid,olddepid,oldpostion,oldsalary,oldyjtype, oldyjrate,oldyjamt,newcompid,newempid,newdepid,newpostion,newsalary,newyjtype,newyjrate,newyjramt,effectivedate,optionbill,changemark)                
  values(@inlineno,1,@oldcompid,@oldstaffno,@olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,@newcompid,@newstaffno,        
          @newdepartment,@newposition,@newsalary,@newyejitype,@newyejirate,@newyejiamt,@validDate,@changebillid,@note)        
        
  update staffinfo set compno=@newcompid,staffno=@newstaffno,department=@newdepartment,position=@newposition,basesalary=@newsalary,        
          resulttye=@newyejitype,resultrate=@newyejirate,baseresult=@newyejiamt        
  where manageno=@inlineno   and  isnull(stafftype,0)=0         
  ---------------------------------------------------------------              
  delete sysuserinfo where   frominnerno=@inlineno       
  delete useroverall where userno=@oldstaffno       
  update staffchangeinfo set billflag=8  where changecompid=@changecompid and changebillid=@changebillid            
   end              
          
  fetch cursor_staffchange into @changecompid,@changebillid,@billtype, @oldstaffno,@oldcompid,@inlineno,@validDate,@olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,        
    @newcompid,@newstaffno ,@newdepartment,@newposition,@newsalary,@newyejitype,@newyejirate,@newyejiamt,@note,@leaveltype        
 end                        
 close cursor_staffchange                        
 deallocate cursor_staffchange               
 drop table #staffchangeinfo        
end 