
	declare @datefrom varchar(10)
	declare @dateto   varchar(10)
	set @datefrom='20140601'
	set @dateto='20140626'
	declare @tmpdate varchar(8)                                      
	declare @tmpenddate varchar(8)                                      
	set @tmpenddate = @datefrom                                      
	set @tmpdate = @datefrom                                      
    while (@tmpenddate <= @dateto)                                      
    begin   
		select   @tmpenddate                          
        exec upg_goods_begin_result_daybyday_goodno '001','2011600102','2011600102',@tmpenddate
		execute upg_date_plus @tmpdate,1,@tmpenddate output                                      
		set @tmpdate = @tmpenddate                                      
    end   


select * from  dgoodsstockinfo where changebillno='04520140410001_e02'
 











	alter procedure upg_goods_begin_result_daybyday_goodno
	(
		@compid			varchar(10),
		@xfromgoodsno	varchar(20),
		@xtogoodsno		varchar(20),
		@absencedate	varchar(8)
	)
	as    
	begin
		 create table #instock      --存放入库量          
		(          
			id			int identity		not null,          
			goodsno		varchar(20)				null,--物品编号         
			quantity	float					null,--数量          
			amt			float					null,--入库金额       
			primary key (id),          
		)          
		create table #outstock      --存放出库量          
		(          
			id			int identity		not null,          
			goodsno		varchar(20)				null,--物品编号  
			quantity	float					null,--数量          
			amt			float					null,--出库金额       
			primary key (id),          
		)           
		
	
		insert #instock(goodsno,quantity,amt)          
		select  changegoodsno,isnull(sum(changecount),0),isnull(sum(changeamt),0)           
		from  mgoodsstockinfo a,dgoodsstockinfo b        
		where a.changecompid= b.changecompid             
		and a.changetype= b.changetype             
		and a.changebillno= b.changebillno            
		and a.changetype='1'        and ISNULL(changeoption,0)<>6         
		and a.changedate  =@absencedate
		and a.changecompid= @compid 
		and changegoodsno between @xfromgoodsno and @xtogoodsno
		group by changegoodsno    
		
		insert #instock(goodsno,quantity,amt)          
		select  changegoodsno,isnull(sum(changecount),0),0      
		from  mgoodsstockinfo a,dgoodsstockinfo b        
		where a.changecompid= b.changecompid             
		and a.changetype= b.changetype             
		and a.changebillno= b.changebillno            
		and a.changetype='1'   and ISNULL(changeoption,0)=6              
		and a.changedate  =@absencedate
		and a.changecompid= @compid 
		and changegoodsno between @xfromgoodsno and @xtogoodsno
		group by changegoodsno  
	    
		 insert #outstock(goodsno,quantity,amt)          
		 select  changegoodsno,isnull(sum(changecount),0),isnull(sum(changeamt),0)           
		 from  mgoodsstockinfo a,dgoodsstockinfo b        
		 where a.changecompid= b.changecompid             
		   and a.changetype= b.changetype             
		   and a.changebillno= b.changebillno            
		   and a.changetype<>'1'            
		   and a.changedate=@absencedate    
		   and a.changecompid= @compid  
		   and changegoodsno between @xfromgoodsno and @xtogoodsno       
		   group by changegoodsno      
	 
		declare @targetdate	varchar(10)
		exec upg_date_plus @absencedate,1,@targetdate output
		delete dgoodssetlebegin where begindate=@targetdate and changegoodsno between @xfromgoodsno and @xtogoodsno
		
		delete dgoodsbatchbegin where begindate=@targetdate and changegoodsno between @xfromgoodsno and @xtogoodsno
		
		insert dgoodsbatchbegin(changecompid,begindate,changegoodsno,beginbillno,begincount,beginprice)
		select changecompid,@targetdate,changegoodsno,beginbillno,begincount,beginprice 
		from dgoodsbatchbegin where begindate=@absencedate and changegoodsno between @xfromgoodsno and @xtogoodsno
		
		insert dgoodsbatchbegin(changecompid,begindate,changegoodsno,beginbillno,begincount,beginprice)
		select a.changecompid,@targetdate, changegoodsno,a.changebillno,sum(changecount),b.changeprice         
		from  mgoodsstockinfo a,dgoodsstockinfo b        
		where a.changecompid= b.changecompid             
		and a.changetype= b.changetype             
		and a.changebillno= b.changebillno            
		and a.changetype='1'          and ISNULL(changeoption,0)<>6      
		and a.changedate  =@absencedate
		and a.changecompid= @compid 
		and changegoodsno between @xfromgoodsno and @xtogoodsno
		group by a.changecompid,a.changebillno,changegoodsno ,changeprice  
		
 
		insert dgoodsbatchbegin(changecompid,begindate,changegoodsno,beginbillno,begincount,beginprice)
		select a.changecompid,@targetdate, changegoodsno,a.changebillno,sum(changecount),0       
		from  mgoodsstockinfo a,dgoodsstockinfo b        
		where a.changecompid= b.changecompid             
		and a.changetype= b.changetype             
		and a.changebillno= b.changebillno            
		and a.changetype='1'        and ISNULL(changeoption,0)=6        
		and a.changedate  =@absencedate
		and a.changecompid= @compid 
		and changegoodsno between @xfromgoodsno and @xtogoodsno
		group by a.changecompid,a.changebillno,changegoodsno ,changeprice  
		
		declare @togoodsno				varchar(20)  
		declare @toquantity				float
		declare @beginbillno			varchar(30)
		declare @begincount				float
		
		declare cur_each_outstock cursor for  
		select goodsno,quantity from #outstock 
		open cur_each_outstock  
		fetch cur_each_outstock into @togoodsno,@toquantity  
		while @@fetch_status = 0  
		begin  
				declare cur_each_dgoodsbatchbegin cursor for  
				select beginbillno,begincount from dgoodsbatchbegin where changegoodsno=@togoodsno and isnull(begincount,0)>0 and begindate=@targetdate
				open cur_each_dgoodsbatchbegin  
				fetch cur_each_dgoodsbatchbegin into @beginbillno,@begincount  
				while @@fetch_status = 0  
				begin  
					if(ISNULL(@toquantity,0)>ISNULL(@begincount,0))
					begin
						set @toquantity=ISNULL(@toquantity,0)-ISNULL(@begincount,0)
						update dgoodsbatchbegin set begincount=0 where beginbillno=@beginbillno and changegoodsno=@togoodsno and begindate=@targetdate
					end
					else 
					begin
						update dgoodsbatchbegin set begincount=ISNULL(@begincount,0)-ISNULL(@toquantity,0) where beginbillno=@beginbillno and changegoodsno=@togoodsno and begindate=@targetdate
						break;
					end
					fetch cur_each_dgoodsbatchbegin into @beginbillno,@begincount  
				end
				close cur_each_dgoodsbatchbegin  
				deallocate cur_each_dgoodsbatchbegin 
		    
			fetch cur_each_outstock into @togoodsno,@toquantity    
		end  
		close cur_each_outstock  
		deallocate cur_each_outstock    
  
		
		insert dgoodssetlebegin(changecompid,begindate,changegoodsno,begincount)
		select @compid,@targetdate,a.goodsno,isnull(begincount,0)+ISNULL(c.quantity,0)-ISNULL(d.quantity,0)
		from goodsinfo a
		left join  dgoodssetlebegin b  on b.changegoodsno=a.goodsno and begindate=@absencedate and changecompid=@compid 
		left join  #instock  c on c.goodsno=a.goodsno
		left join  #outstock d  on d.goodsno=a.goodsno 
		where   a.goodsmodeid='SGM' and ISNULL(useflag,0)=0 and a.goodsno between @xfromgoodsno and @xtogoodsno
		
		
		update a set beginamt=isnull((select SUM(isnull(b.begincount,0)*ISNULL(b.beginprice,0)) 
		from dgoodsbatchbegin b 
		where a.changegoodsno=b.changegoodsno and a.begindate=b.begindate and a.begindate=@targetdate and b.changegoodsno between @xfromgoodsno and @xtogoodsno  ),0)
	    from dgoodssetlebegin a
		where a.begindate=@targetdate and a.changegoodsno between @xfromgoodsno and @xtogoodsno

		
		drop table #instock
		drop table #outstock
	end
	






	--declare @datefrom varchar(10)
	--declare @dateto   varchar(10)
	--set @datefrom='20140301'
	--set @dateto='20140309'
	--declare @tmpdate varchar(8)                                      
	--declare @tmpenddate varchar(8)                                      
	--set @tmpenddate = @datefrom                                      
	--set @tmpdate = @datefrom                                      
 --   while (@tmpenddate <= @dateto)                                      
 --   begin                                      
 --       exec upg_goods_begin_result_daybyday '001',@tmpenddate
	--	execute upg_date_plus @tmpdate,1,@tmpenddate output                                      
	--	set @tmpdate = @tmpenddate                                      
 --   end   
 
 
--update  dgoodsbarinfo set costdate=outerdate,costbillo=outerbill,coststore=receivestore where len(receivestore)>3

--update  dgoodsbarinfo set costdate='',costbillo='',coststore='' where len(receivestore)>3  and barnostate=2

--update dgoodsbarinfo set outerdate=gfh08d,outerbill=gfh09c,receivestore=gfh10c from dgoodsbarinfo,[S3GOS2016].dbo.gfm08 where len(receivestore)>3 and goodsbarno=gfh02c and gfh03i<>1


--update dgoodsbarinfo set outerdate=SUBSTRING(sendbillid,4,8),outerbill=sendbillid,receivestore=SUBSTRING(sendbillid,1,3)  
--from dgoodsbarinfo,Dgoodssendbarinfo where len(receivestore)>3 and goodsbarno between frombarcode and tobarcode

