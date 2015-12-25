if exists(select 1 from sysobjects where type='P' and name='upg_compute_comp_classed_trade_daybyday')
	drop procedure upg_compute_comp_classed_trade_daybyday
go
create procedure upg_compute_comp_classed_trade_daybyday    
(
	@compid			varchar(10),		--门店号	
	@datefrom		varchar(10),		--起始日期
	@dateto			varchar(10)			--结束日期
)  
as
begin
	delete compclasstraderesult from compclasstraderesult,compchaininfo where curcomp=@compid and relationcomp=compid and ddate between @datefrom and @dateto
	create table #cls_yeji_result_search(                              
		  compid varchar(10) not null,    
		  ddate varchar(10) null,                           
		  beaut_yeji float null,                              
		  hair_yeji float null,                              
		  foot_yeji float null,                              
		  finger_yeji float null,                              
		  total_yeji float null,                              
		  real_beaut_yeji float null,                              
		  real_hair_yeji float null,                              
		  real_foot_yeji float null,                              
		  real_finger_yeji float null,                              
		  real_total_yeji  float null                              
		 ) 
 
	declare @tmpdate varchar(8)                                    
	declare @tmpenddate varchar(8)                                    
	set @tmpenddate = @datefrom                                    
	set @tmpdate = @datefrom                                    
    while (@tmpenddate <= @dateto)                                    
    begin                                    
	  --插入选择的门店编号到#diarialBill_byday_fromShops中                                        
		  
		declare @tocompid varchar(10)
		declare cur_each_comp cursor for
		select relationcomp from compchaininfo where curcomp=@compid 
		open cur_each_comp
		fetch cur_each_comp into @tocompid
		while @@fetch_status = 0
		begin
			exec upg_compute_comp_classed_trade	@tocompid,@tmpenddate,@tmpenddate,2
			fetch cur_each_comp into @tocompid
		end
		close cur_each_comp
		deallocate cur_each_comp                                  
		execute upg_date_plus @tmpdate,1,@tmpenddate output                                    
		set @tmpdate = @tmpenddate                                    
    end 
    insert compclasstraderesult(compid,ddate,beautyeji,hairyeji,footyeji,fingeryeji,totalyeji,realbeautyeji,realhairyeji,realfootyeji,realfingeryeji,realtotalyeji)
		    select compid,ddate,beaut_yeji,hair_yeji,foot_yeji,finger_yeji,total_yeji,real_beaut_yeji,real_hair_yeji,real_foot_yeji,real_finger_yeji,real_total_yeji from #cls_yeji_result_search	
    drop table #cls_yeji_result_search
end
go
--exec upg_compute_comp_classed_trade_daybyday '001','20130901','20131127'
--go
--select * from compclasstraderesult 

select SUM(realtotalyeji) from compclasstraderesult where compid='002'
exec upg_compute_comp_classed_trade '002','20131201','20131231',1

