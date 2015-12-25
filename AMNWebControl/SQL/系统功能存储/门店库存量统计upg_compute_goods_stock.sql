--exec upg_compute_goods_stock '001','20131114','30101101','30101101',''
----------------计算门店库存-----------------
if exists(select 1 from sysobjects where type='P' and name='upg_compute_goods_stock')
	drop procedure upg_compute_goods_stock
go
create procedure upg_compute_goods_stock   
(      
 @compid   varchar(10),   --会馆编号      
 @date   varchar(10),   --日期       
 @goodsfrom  varchar(20),  --物品编号from       
 @goodsto  varchar(20),   --物品编号to       
 @garagefrom  varchar(20)        --仓库编号from   
  
)      
as  
begin      
   
    --存放入库的数量(进货单位向标准单位转换)         
   create table #inser_stock     
   (      
		id				int identity	not null,      
		goodsno			varchar(20)		null,--物品编号      
		goodsname		varchar(80)		null,--物品名称      
		garageno		varchar(20)		null,--仓库编号      
		garagename		varchar(20)		null,--仓库名称      
		goodstype		varchar(20)		null,--产品类型    
		goodstypename	varchar(20)		null,--产品类型名称 
		unit			varchar(10)		null,--单位    
		price			float			null,--单价  
		quantity		float			null,--数量          
		amt				float			null,--金额        
		primary key (id),      
   )      
   -- 存放出库、销货、耗用的数（消耗单位向标准单位转换）   
   create table #outser_stock       
   (      
	   id				int identity        not null,      
	   goodsno			varchar(20)				null,--物品编号      
	   goodsname		varchar(80)				null,--物品名称      
	   garageno			varchar(20)				null,--仓库编号      
	   garagename		varchar(20)				null,--仓库名称      
	   unit				varchar(10)				null,--单位      
	   quantity			float					null,--数量      
	   primary key (id),      
   )      
   insert #inser_stock(goodsno,goodsname,garageno,garagename,unit,quantity)      
   select  changegoodsno,'',changewareid,'','',isnull(sum(changecount),0)     
                from  mgoodsstockinfo a,dgoodsstockinfo b  
                where a.changecompid= b.changecompid       
                  and a.changetype= b.changetype       
                  and a.changebillno= b.changebillno      
                  and a.changetype='1'       
                  and a.changedate<=@date   
                  and a.changecompid= @compid  
                  and (changegoodsno between @goodsfrom and @goodsto or ISNULL(@goodsfrom,'')='')  
                  and (changewareid = @garagefrom or ISNULL(@garagefrom,'')='')  
       group by changegoodsno,changewareid      
         
             
      insert #outser_stock(goodsno,goodsname,garageno,garagename,unit,quantity)      
      select  changegoodsno,'',changewareid,'','',isnull(sum(changecount),0)     
                from  mgoodsstockinfo a,dgoodsstockinfo b  
                where a.changecompid= b.changecompid       
                  and a.changetype= b.changetype       
                  and a.changebillno= b.changebillno      
                  and a.changetype<>'1'       
                  and a.changedate<=@date  
                  and a.changecompid= @compid      
                  and (changegoodsno between @goodsfrom and @goodsto or ISNULL(@goodsfrom,'')='')  
                  and (changewareid = @garagefrom or ISNULL(@garagefrom,'')='')   
      group by changegoodsno,changewareid    
	  --更新仓库名称      
	  update a      
	  set a.garagename =b.warehousename      
	  from #inser_stock a,compwarehouse b      
	  where a.garageno = b.warehouseno      
		and b.compno = @compid      
          
     
      
   --统计出库(包括销货)的数量更新库存量      
      
  update t      
  set t.quantity = isnull(t.quantity,0)- isnull(a.quantity,0)       
  from #outser_stock a, #inser_stock t      
  where  a.goodsno=t.goodsno       
   and a.garageno=t.garageno           
        
  --更新产品名      
  update a set  a.goodsname=b.goodsname,a.unit=b.saleunit ,
				a.goodstype=b.goodspricetype ,
				a.goodstypename=c.parentcodevalue,
				a.price=b.storesalseprice,
				a.amt=isnull(a.quantity,0)*isnull(b.storesalseprice,0)
  from #inser_stock a, goodsinfo b ,commoninfo  c ,compchaininfo 
  where curcomp=goodssource and relationcomp=@compid and a.goodsno = b.goodsno   
  and  b.goodspricetype=c.parentcodekey and c.infotype='WPTJ'
          
        
   
    select goodsno,goodsname,garageno,garagename,unit,goodstype,goodstypename,quantity,price,amt      
    from #inser_stock  
    order by goodsno     
      
  drop table #inser_stock      
  drop table #outser_stock      
       
end 