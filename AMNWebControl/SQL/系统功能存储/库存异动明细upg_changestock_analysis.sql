if exists(select 1 from sysobjects where type='P' and name='upg_changestock_analysis')
	drop procedure upg_changestock_analysis
go
create procedure upg_changestock_analysis(    
	@compid			varchar(10),			--门店编号    
	@datefrom		varchar(10),			--日期   
	@dateto			varchar(10),			--日期  
	@goodsfrom		varchar(20),			--物品编号   
	@goodsto		varchar(20),			--物品编号   
	@wareid			varchar(20)			--仓库编号
)    
as
begin    

 create table #changedetail      
 (      
		id					int identity		not null,      
		itemno				varchar(30)				null, -- item no      
		itemname			varchar(50)				null, -- item no 
		storage				varchar(20)				null, -- 仓库      
		storagename			varchar(60)				null, -- 仓库      
		ddate				varchar(8)				null , -- 异动日期     
		ttime				varchar(8)				null , --  异动时间 
		insercount			float					null , -- 入库数量      
		outrecount			float					null , -- 出库数量      
		changetype			varchar(20)				null , -- 异动类别      
		billno				varchar(30)				null , -- 异动单号      
		stock				float					null , -- 库存数量  
		primary key (id),      
 )  
 
	insert #changedetail( itemno, storage, ddate, ttime,insercount, outrecount ,changetype, billno )
	select  changegoodsno,changewareid,changedate,changetime,
  	case a.changetype when 1 then changecount else 0 end,
  	case a.changetype when 1 then 0 else changecount end,      
	case a.changetype when '1' then case changeoption when 1 then '正常入库' when 2 then '盘盈入库' when 3 then '客户退货' when 4 then '调拨入库' end     
                        when '2' then case changeoption when 1 then '正常出库' when 2 then '盘亏出库' when 3 then '供应商退货' when 4 then '调拨出库' when 5 then '损坏' when 6 then '赠送' else '出库' end    
                        when '3' then '销货'     
                        when '5' then '套餐'     
                        when '6' then '套餐'     
                        else '消耗' end,a.changebillno
                from  mgoodsstockinfo a,dgoodsstockinfo b  
                where a.changecompid= b.changecompid       
                  and a.changetype= b.changetype       
                  and a.changebillno= b.changebillno 
                  and a.changedate  between @datefrom and @dateto  
                  and a.changecompid= @compid  
                  and (changegoodsno between @goodsfrom and @goodsto or ISNULL(@goodsfrom,'')='')  
                  and (changewareid = @wareid or ISNULL(@wareid,'')='')  
       order by a.changecompid,changegoodsno,changedate,changetime     
       
        update #changedetail set stock =       
  isnull((select sum(insercount) from #changedetail A       
  where A.id <= #changedetail.id and A.itemno = #changedetail.itemno and A.storage = #changedetail.storage ), 0)    
      
 update #changedetail set stock = stock -       
  isnull((select sum(outrecount) from #changedetail A       
  where A.id <= #changedetail.id and A.itemno = #changedetail.itemno and A.storage = #changedetail.storage ), 0)      
      
      
 delete #changedetail where ddate < @datefrom or ddate > @dateto     
   --更新仓库名称      
 update a      
	  set a.storagename =b.warehousename      
	  from #changedetail a,compwarehouse b      
	  where a.storage = b.warehouseno      
		and b.compno = @compid      
  --更新产品名      
  update a set  a.itemname=b.goodsname
  from #changedetail a, goodsnameinfo b 
  where a.itemno = b.goodsno  
       

  select itemno,itemname,storage,storagename,ddate,insercount,outrecount,changetype,billno,stock from #changedetail order by ddate,ttime
  drop table #changedetail
     
end  