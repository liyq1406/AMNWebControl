alter procedure upg_hand_personManger_anon          
(        
 @compid  varchar(10),                         
 @billNo  varchar(20)           
)        
as        
begin        
	--当前日期        
	declare @curdate varchar(10)            
	select @curdate=substring(convert(varchar(20),getdate(),102),1,4) + substring(convert(varchar(20),getdate(),102),6,2)+ substring(convert(varchar(20),getdate(),102),9,2)                    
         
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
	declare @note			varchar(80)   --备注               
	declare @billtype		int     --单据类型  0-薪资调整 1--离职申请 2--入职申请 3--重回公司申请 4--请假申请,5--本店调动,6--跨店调动        
	declare @validDate		varchar(8)          --生效日期        
	declare @inlineno		varchar(20)   --员工内部编号            
	declare @changemark		varchar(200)   --员工内部编号            
    declare @roleid			varchar(10)		--角色编号      
         
	select @oldcompid=appchangecompid, @oldstaffno=changestaffno,@olddepartment=beforedepartment,@oldposition=beforepostation,@oldsalary=beforesalary,        
        @oldyejitype=beforeyejitype,@oldyejirate=beforeyejirate,@oldyejiamt=beforeyejiamt,@newstaffno=afterstaffno,@newcompid=aftercompid,        
        @newdepartment=afterdepartment,@newposition=afterpostation,@newsalary=aftersalary,@newyejitype=afteryejitype,@newyejirate=afteryejirate,        
        @newyejiamt=afteryejiamt,@leaveltype=leaveltype,@note=remark,@validDate=validatestartdate,@inlineno=staffmangerno,@billtype=changetype,@changemark=remark        
	from staffchangeinfo where changecompid=@compid and changebillid=@billNo and billflag=3        
         
	--保留历史算法 0本店调动  1：跨店调动  2：薪资调整  3:员工离职 4：入职申请  5：重回公司        
	if(@billtype = 2)--入职申请                       
	begin              
		insert staffhistory(manageno,changetype,oldcompid,oldempid,olddepid,oldpostion,oldsalary,oldyjtype, oldyjrate,oldyjamt,newcompid,newempid,newdepid,newpostion,newsalary,newyjtype,newyjrate,newyjramt,effectivedate,optionbill,changemark)                
		values(@inlineno,4,@oldcompid,@oldstaffno,@olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,@newcompid,@newstaffno,        
		@newdepartment,@newposition,@newsalary,@newyejitype,@newyejirate,@newyejiamt,@curdate,@billNo,@changemark)        
          
		update staffinfo set curstate='2',arrivaldate=@curdate where manageno=@inlineno        
		update staffchangeinfo set billflag=8  where changecompid=@compid and changebillid=@billNo
		
		--根据职位创建角色账户
		select @roleid=roleno from sysroletoposition where position=@newposition  
		if(ISNULL(@roleid,'')<>'')
		begin
			delete sysuserinfo where userno=@newstaffno
			insert sysuserinfo(userno,userpwd,enableflag,userrole,frominnerno,fromcompno)
			values(@newstaffno,'42a12b9150770ead',1,@roleid,@inlineno,@newcompid) 
			delete usereditright where userno=@newstaffno
			delete useroverall where userno=@newstaffno 
			insert useroverall(userno,modetype,modevalue) values(@newstaffno,'1',@newcompid) 
		end 
		      
    end            
    else if(@billtype = 1) --离职申请                       
    begin                  
		insert staffhistory(manageno,changetype,oldcompid,oldempid,olddepid,oldpostion,oldsalary,oldyjtype, oldyjrate,oldyjamt,newcompid,newempid,newdepid,newpostion,newsalary,newyjtype,newyjrate,newyjramt,effectivedate,optionbill,changemark)                
		values(@inlineno,3,@oldcompid,@oldstaffno,@olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,'99999',@inlineno+'LZ',        
				@olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,@curdate,@billNo,@changemark)        
        update staffinfo set curstate='3',compno='99999',staffno=@inlineno+'LZ',leavedate=@curdate,leveltype=@leaveltype,fillno=REPLACE(fillno,'FN','LZ')    
			where manageno=@inlineno  and  compno= @oldcompid  
		update staffinfo set curstate='3',compno='99999',staffno=@inlineno+'LZ',leavedate=@curdate,leveltype=@leaveltype ,fillno=REPLACE(fillno,'FN','LZ')   
			where manageno=@inlineno and  ISNULL(stafftype,0)=1  
		---------------------------------------------------------------              
		delete sysuserinfo where   frominnerno=@inlineno         
		delete useroverall where userno=@oldstaffno    
        update staffchangeinfo set billflag=8  where changecompid=@compid and changebillid=@billNo                  
	end           
	else if(@billtype = 3) --重回公司        
	begin        
		insert staffhistory(manageno,changetype,oldcompid,oldempid,olddepid,oldpostion,oldsalary,oldyjtype, oldyjrate,oldyjamt,newcompid,newempid,newdepid,newpostion,newsalary,newyjtype,newyjrate,newyjramt,effectivedate,optionbill,changemark)                
		values(@inlineno,5,@oldcompid,@oldstaffno,@olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,@newcompid,@newstaffno,        
        @newdepartment,@newposition,@newsalary,@newyejitype,@newyejirate,@newyejiamt,@curdate,@billNo,@changemark)        
        
        declare @fillno varchar(20)
		declare @needfid int	
		select @needfid=MIN(needfid) from (select cast(SUBSTRING(fillno,4,6) as int ) as curfid, row_number() over (order by fillno) as needfid
		from staffinfo where ISNULL(fillno,'') like 'FN%' ) as curresult where curfid<>needfid
		if(ISNULL(@needfid,0)=0)
			select @needfid=max(cast(SUBSTRING(fillno,4,6) as int))+1 from staffinfo where ISNULL(fillno,'') like 'FN%' 
		if(ISNULL(@needfid,0)<10)
			set @fillno='FN10000'+cast(@needfid as varchar(1))
		else if(ISNULL(@needfid,0)<100 and ISNULL(@needfid,0)>=10 )
			set @fillno='FN1000'+cast(@needfid as varchar(2))
		else if(ISNULL(@needfid,0)<1000 and ISNULL(@needfid,0)>=100 )
			set @fillno='FN100'+cast(@needfid as varchar(3))
		else if(ISNULL(@needfid,0)<10000 and ISNULL(@needfid,0)>=1000 )
			set @fillno='FN10'+cast(@needfid as varchar(4))
				
		update staffinfo set curstate='2',compno=@newcompid,staffno=@newstaffno,department=@newdepartment,position=@newposition,basesalary=@newsalary,        
                       resulttye=@newyejitype,resultrate=@newyejirate,baseresult=@newyejiamt,arrivaldate=@curdate,fillno=@fillno        
		where manageno=@inlineno and  isnull(stafftype,0)=0          
		---------------------------------------------------------------              
          
        update staffchangeinfo set billflag=8  where changecompid=@compid and changebillid=@billNo              
	end        
	else if(@billtype = 5) --本店调动        
	begin        
		insert staffhistory(manageno,changetype,oldcompid,oldempid,olddepid,oldpostion,oldsalary,oldyjtype, oldyjrate,oldyjamt,newcompid,newempid,newdepid,newpostion,newsalary,newyjtype,newyjrate,newyjramt,effectivedate,optionbill,changemark)                
		values(@inlineno,0,@oldcompid,@oldstaffno,@olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,@newcompid,@newstaffno,        
        @newdepartment,@newposition,@newsalary,@newyejitype,@newyejirate,@newyejiamt,@curdate,@billNo,@changemark)        
        
		update staffinfo set staffno=@newstaffno,department=@newdepartment,position=@newposition,basesalary=@newsalary,        
                       resulttye=@newyejitype,resultrate=@newyejirate,baseresult=@newyejiamt        
		where manageno=@inlineno and  isnull(stafftype,0)=0       
		---------------------------------------------------------------       
        update staffchangeinfo set billflag=8  where changecompid=@compid and changebillid=@billNo            
	end        
	else if(@billtype = 6) --本店调动        
	begin        
		insert staffhistory(manageno,changetype,oldcompid,oldempid,olddepid,oldpostion,oldsalary,oldyjtype, oldyjrate,oldyjamt,newcompid,newempid,newdepid,newpostion,newsalary,newyjtype,newyjrate,newyjramt,effectivedate,optionbill,changemark)                
		values(@inlineno,1,@oldcompid,@oldstaffno,@olddepartment,@oldposition,@oldsalary,@oldyejitype,@oldyejirate,@oldyejiamt,@newcompid,@newstaffno,        
        @newdepartment,@newposition,@newsalary,@newyejitype,@newyejirate,@newyejiamt,@curdate,@billNo,@changemark)        
        
		update staffinfo set compno=@newcompid,staffno=@newstaffno,department=@newdepartment,position=@newposition,basesalary=@newsalary,        
                       resulttye=@newyejitype,resultrate=@newyejirate,baseresult=@newyejiamt        
		where manageno=@inlineno  and  isnull(stafftype,0)=0         
		---------------------------------------------------------------              
		delete sysuserinfo where   frominnerno=@inlineno         
		delete useroverall where userno=@oldstaffno    
        update staffchangeinfo set billflag=8  where changecompid=@compid and changebillid=@billNo            
	end              
                      
end 