alter procedure upg_all_personal_result_rijie_daybyday      
(  
 @compid   varchar(10),  --门店号   
 @datefrom  varchar(10),  --起始日期  
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
		exec upg_all_personal_result_rijie @tocompid,@datefrom,@dateto 
		fetch cur_each_comp into @tocompid  
	end  
	close cur_each_comp  
	deallocate cur_each_comp 
end  


--exec upg_all_personal_result_rijie_daybyday '001','20140201','20140228'
