alter procedure upg_inoutstock_analysis(          
 @compid   varchar(10),   --门店编号          
 @datefrom  varchar(10),   --日期         
 @dateto   varchar(10),   --日期        
 @goodsfrom  varchar(20),   --物品编号         
 @goodsto  varchar(20),   --物品编号         
 @wareid   varchar(20)   --仓库编号      
)          
as      
begin          
      
  create table #begstockquan    --存放期初量          
  (          
  id    int identity not null,            
  goodsno   varchar(20)  null,--物品编号            
  goodsname  varchar(80)  null,--物品名称            
  garageno  varchar(20)  null,--仓库编号            
  garagename  varchar(20)  null,--仓库名称            
  goodstype  varchar(20)  null,--产品类型          
  goodstypename varchar(20)  null,--产品类型名称       
  unit   varchar(10)  null,--单位          
  price   float   null,--单价        
  quantity  float   null,--数量                
  amt    float   null,--金额         
  primary key (id),          
  )          
  create table #endstockquan    -- 存放期末量          
  (          
  id    int identity not null,            
  goodsno   varchar(20)  null,--物品编号            
  goodsname  varchar(80)  null,--物品名称            
  garageno  varchar(20)  null,--仓库编号            
  garagename  varchar(20)  null,--仓库名称            
  goodstype  varchar(20)  null,--产品类型          
  goodstypename varchar(20)  null,--产品类型名称       
  unit   varchar(10)  null,--单位          
  price   float   null,--单价        
  quantity  float   null,--数量                
  amt    float   null,--金额           
   primary key (id),          
  )          
  create table #instock      --存放入库量          
  (          
  id    int identity   not null,          
  goodsno   varchar(20)     null,--物品编号          
  garageno  varchar(20)     null,--仓库编号          
  quantity  float      null,--数量          
  amt    float      null,--入库金额       
   primary key (id),          
  )          
  create table #outstock      --存放出库量          
  (          
  id    int identity   not null,          
  goodsno   varchar(20)     null,--物品编号          
  garageno  varchar(20)     null,--仓库编号       
  quantity  float      null,--数量          
  amt    float      null,--出库金额       
   primary key (id),          
  )           
   create table #sendstock      -- 发货途中       
  (          
  id    int identity   not null,          
  goodsno   varchar(20)     null,--物品编号          
  garageno  varchar(20)     null,--仓库编号       
  quantity  float      null,--数量          
  amt    float      null,--发货金额       
   primary key (id),          
  )           
        
            
   --对日期处理,对1天的数据进行查询          
   declare @start_date   varchar(8)     
   declare @end_date   varchar(8)        
   declare @strtmp     varchar(10)          
              
   set @strtmp = convert(varchar(10),dateadd(day,-1,@datefrom),120)          
   set @start_date = substring(@strtmp,1,4)+substring(@strtmp,6,2)+substring(@strtmp,9,2) 
   
   set @strtmp = convert(varchar(10),dateadd(day,1,@dateto),120)          
   set @end_date = substring(@strtmp,1,4)+substring(@strtmp,6,2)+substring(@strtmp,9,2)            
            
         
  insert #instock(goodsno,garageno,quantity,amt)          
  select  changegoodsno,changewareid,isnull(sum(changecount),0),isnull(sum(changeamt),0)           
                from  mgoodsstockinfo a,dgoodsstockinfo b        
                where a.changecompid= b.changecompid             
                  and a.changetype= b.changetype             
                  and a.changebillno= b.changebillno            
                  and a.changetype='1'             
                  and a.changedate  between @datefrom and @dateto        
                  and a.changecompid= @compid        
                  and (changegoodsno between @goodsfrom and @goodsto or ISNULL(@goodsfrom,'')='')        
                  and (changewareid = @wareid or ISNULL(@wareid,'')='')        
                  and b.changebillno not in ('00120131231001x','00120131231002x')      
       group by changegoodsno,changewareid           
               
       insert #outstock(goodsno,garageno,quantity,amt)          
      select  changegoodsno,changewareid,isnull(sum(changecount),0),isnull(sum(changeamt),0)           
                from  mgoodsstockinfo a,dgoodsstockinfo b        
            where a.changecompid= b.changecompid             
                  and a.changetype= b.changetype             
                  and a.changebillno= b.changebillno            
        and a.changetype<>'1'            
                  and a.changedate  between @datefrom and @dateto        
                  and a.changecompid= @compid        
                  and (changegoodsno between @goodsfrom and @goodsto or ISNULL(@goodsfrom,'')='')        
                  and (changewareid = @wareid or ISNULL(@wareid,'')='')        
                   and b.changebillno not in ('00120131231001x','00120131231002x')      
       group by changegoodsno,changewareid            
         
        
        
     insert #sendstock(goodsno,garageno,quantity,amt)          
      select  ordergoodsno,headwareno,isnull(sum(downordercount),0),isnull(sum(downorderamt),0)           
               from mgoodsorderinfo a,dgoodsorderinfo b      
               where a.ordercompid=b.ordercompid and a.orderbillid=b.orderbillid            
                  and ISNULL(a.invalid,0)<>'1'            
                  and a.downorderdate  between @datefrom and @dateto        
                  and a.downordercompid= @compid and isnull(orderstate,0)=2      
                  and (ordergoodsno between @goodsfrom and @goodsto or ISNULL(@goodsfrom,'')='')        
                  and (headwareno = @wareid or ISNULL(@wareid,'')='')        
       group by ordergoodsno,headwareno           
           
     --期初库存量     
    if(@compid<>'001' and @datefrom<'20140101')
    begin     
     insert #begstockquan(goodsno,goodsname,garageno,garagename,unit,goodstype,goodstypename,quantity,price,amt)          
     exec upg_compute_goods_stock @compid,@start_date,@goodsfrom,@goodsto,@wareid  
    end
    else
    begin    
		insert #begstockquan(goodsno,quantity,amt) 
		select changegoodsno,begincount,beginamt from dgoodssetlebegin where changecompid=@compid and ( changegoodsno between @goodsfrom and @goodsto  or ISNULL(@goodsfrom,'')='') and begindate=@datefrom
	end
	
  --期末库存量          
  
    if(@compid<>'001' and @datefrom<'20140101')
    begin     
		   insert #endstockquan(goodsno,goodsname,garageno,garagename,unit,goodstype,goodstypename,quantity,price,amt)          
			exec upg_compute_goods_stock @compid,@dateto,@goodsfrom,@goodsto,@wareid
    end
    else
    begin   
		if exists(select 1 from dgoodssetlebegin where begindate=@end_date)
		begin 
			insert #endstockquan(goodsno,quantity,amt) 
			select changegoodsno,begincount,beginamt from dgoodssetlebegin where changecompid=@compid and ( changegoodsno between @goodsfrom and @goodsto  or ISNULL(@goodsfrom,'')='') and begindate=@end_date
		end
		else
		begin
			  insert #endstockquan(goodsno,goodsname,garageno,garagename,unit,goodstype,goodstypename,quantity,price,amt)          
				exec upg_compute_goods_stock @compid,@dateto,@goodsfrom,@goodsto,@wareid
		end
	end
	  
       
   select goodsno=a.goodsno,goodsname=a.goodsname,garageno=a.garageno,garagename=a.garagename,          
   pquan=sum(isnull(b.quantity,0)),unit=a.unit,          
   pamt=sum(isnull(b.amt,0)),          
   inquan=sum(isnull(c.quantity,0)),         
   inamt =sum(isnull(c.amt,0)) ,      
   outquan=sum(isnull(d.quantity,0)),          
   outamt=sum(isnull(d.amt,0)),      
   equan=sum(isnull(a.quantity,0)),          
   eamt=sum(isnull(a.quantity,0)*isnull(x.purchaseprice,0))  ,      
   squan=sum(isnull(e.quantity,0)),          
   samt=sum(isnull(e.amt,0))         
  from goodsinfo x,   #endstockquan a        
  left outer join #begstockquan b on a.goodsno = b.goodsno      
  left outer join #instock c on a.goodsno = c.goodsno          
  left outer join #outstock d on a.goodsno = d.goodsno       
  left outer join #sendstock e on a.goodsno = e.goodsno       
  where x.goodsno=a.goodsno and x.goodsmodeid='SGM'  and isnull(x.useflag,0)=0     
  group by a.goodsno,a.goodsname,a.garageno,a.garagename,a.unit        
  order by a.goodsno        
             
  drop table #begstockquan          
  drop table #endstockquan          
  drop table #instock          
  drop table #outstock          
           
end 