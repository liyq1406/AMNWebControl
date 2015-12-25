alter procedure upg_compute_comp_satff_salary_daybyday          
(          
 @compid   varchar(10) ,          
 @datefrom  varchar(8) ,          
 @dateto   varchar(8)          
)          
as          
begin          
 declare @targetcompid varchar(10)          
 declare @tmpdate varchar(8)                                              
 declare @tmpenddate varchar(8)          
 declare @targetinid  varchar(20)                                            
 set @tmpenddate = @datefrom                                              
 set @tmpdate = @datefrom           
           
 delete a from staff_work_salary a,compchaininfo b where  curcomp= @compid  and compid=relationcomp and salary_date between @datefrom and   @dateto                                       
          
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
                           
      
   
         
    while (@tmpenddate <= @dateto)                                              
    begin                                              
   --插入选择的门店编号到#diarialBill_byday_fromShops中                                                  
            
 declare cur_each_comp cursor for          
 select relationcomp from compchaininfo where curcomp= @compid            
 open cur_each_comp          
 fetch cur_each_comp into @targetcompid          
 while @@fetch_status=0          
 begin          
 exec upg_all_personal_comm_paymode @targetcompid,@tmpenddate,@tmpenddate,2           
 if(@tmpenddate=@dateto)          
 begin          
  create  table #emp_yeji_total_daybyday                                           
  (                                          
   inid  varchar(20) null,  --员工内部编号             
   postation varchar(10) null,                                          
   yeji  float  null, -- 业绩                                                 
  )           
		insert #emp_yeji_total_daybyday(inid,yeji)                                                 
		select  person_inid,sum(isnull(staffyeji,0)) from allstaff_work_detail_daybyday           
		where compid=@targetcompid and srvdate between @datefrom and @dateto            
		and ISNULL(action_id,-1)<>4 and paycode in ('1','2','6','0','14','15')          
		group by person_inid      
              
        delete #empinfobydate  
		insert into #empinfobydate(compid,inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)                                   
		exec upg_get_empinfo_by_date_comps @compid,@datefrom,@dateto    
    
       
		delete #empinfobydate where dateto<@datefrom    
		delete #empinfobydate where datefrom>@dateto     
		delete #empinfobydate where datediff(day,datefrom,@dateto)<3   
    
		update a set   a.postation=b.position           
		from   #emp_yeji_total_daybyday a,staffinfo b where a.inid=b.manageno          
          
		--有调动的根据调动走    
		update a set  postation=position    
		from #emp_yeji_total_daybyday a ,    
		(select compid,inid,salary,position from (select  row_number() over(PARTITION BY inid order by max(ISNULL(datefrom,'')) desc)  fid, compid,inid,salary,position     
		from  #empinfobydate  group by compid,inid,salary,position ) as c where fid=1 ) as b     
		where a.inid=b.inid and @compid=b.compid    
               
		declare cur_each_salaryOther cursor for          
		select  inid from #emp_yeji_total_daybyday           
		where (postation='003' and ISNULL(yeji,0)>=30000 )           
		or (postation='006' and ISNULL(yeji,0)>=50000 )           
		or (postation='007' and ISNULL(yeji,0)>=70000 )           
		or (postation in ('00701','00702')  and ISNULL(yeji,0)>=90000 )           
		or (postation='004' and ISNULL(yeji,0)>=50000 )           
		open cur_each_salaryOther          
		fetch cur_each_salaryOther into @targetinid          
		while @@fetch_status=0          
		begin          
			exec upg_compute_comp_other_satff_salary_daybyday @targetcompid, @targetinid,@datefrom,@dateto          
			fetch cur_each_salaryOther into @targetinid          
		end          
		close cur_each_salaryOther          
		deallocate cur_each_salaryOther          
               
		drop table #emp_yeji_total_daybyday                               
                          
                            
              
	end          
	fetch cur_each_comp into @targetcompid          
	end          
	close cur_each_comp          
	deallocate cur_each_comp          
            
               
		execute upg_date_plus @tmpdate,1,@tmpenddate output                                              
	set @tmpdate = @tmpenddate                                              
	end           
    truncate table allstaff_work_detail_daybyday     
    drop table  #empinfobydate       
end 