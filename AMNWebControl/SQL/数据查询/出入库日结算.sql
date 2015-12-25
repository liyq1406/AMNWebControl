	 declare @yesterday datetime                          
	declare @curdate varchar(20)                        
	select @yesterday = getdate()-1  
	select @curdate=substring(convert(varchar(20),@yesterday,102),1,4) + substring(convert(varchar(20),@yesterday,102),6,2)+ substring(convert(varchar(20),@yesterday,102),9,2)
	declare @beforedate varchar(10)
	set @beforedate= SUBSTRING(@curdate,1,6)+'01'

	declare @tmpdate varchar(8)                                      
	declare @tmpenddate varchar(8)                                      
	set @tmpenddate = @beforedate                                      
	set @tmpdate = @beforedate                                      
    while (@tmpenddate <= @curdate)                                      
    begin                                      
        exec upg_goods_begin_result_daybyday '001',@tmpenddate
		execute upg_date_plus @tmpdate,1,@tmpenddate output                                      
		set @tmpdate = @tmpenddate                                      
    end 