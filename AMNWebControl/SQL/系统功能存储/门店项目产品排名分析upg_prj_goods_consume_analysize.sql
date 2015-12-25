if exists(select 1 from sysobjects where type='P' and name='upg_prj_goods_consume_analysize')
	drop procedure upg_prj_goods_consume_analysize
go
create procedure upg_prj_goods_consume_analysize              
(              
  @compid			varchar(10),			--公司编号          
  @topnum			int,					--前多少名      
  @fromdate			varchar(18),			--开始日期             
  @todate			varchar(18),			--结束日期      
  @prjsmallamt      float,					--项目开始金额      
  @prjbigamt        float,					--项目结束金额  
  @goodssmallamt    float,					--产品开始金额      
  @goodsbigamt      float					--产品结束金额           
                
)              
as -- 项目消耗统计分析          
begin                 
     create table #prj_info (                
        compid			varchar(150)     null,       -- 公司编号
        compname		varchar(150)     null,       -- 公司编号             
        iseqno			int			     null,       -- 项目编号 
        prjno			varchar(20)      null,       -- 项目编号         
        prjname			varchar(40)      null,       -- 项目名称                  
        prjtype			varchar(30)      null,       -- 类别        
        prjcnt          float            null,       -- 数量      
        prjamt          float            null,       -- 金额            
        goodsno			varchar(20)      null,       -- 项目编号      
        goodsname		varchar(40)      null,       -- 项目名称                  
        goodstype		varchar(30)      null,       -- 类别        
        goodscnt        float            null,       -- 数量      
        goodsamt        float            null       -- 金额
     )      
     
 
     declare @strSql varchar(2000)   
     set @strSql='insert #prj_info(compid,iseqno,prjno,prjcnt,prjamt)   select  top '+str(@topnum)+' curcomp ,row_number() over(order by  SUM(ISNULL(csitemamt,0)) desc), csitemno,SUM(ISNULL(csitemcount,0)),csitemamt=SUM(ISNULL(csitemamt,0)) 
     from mconsumeinfo a with(nolock), dconsumeinfo b with(nolock), compchaininfo
     where curcomp= '+ str(@compid) +' and a.cscompid=relationcomp
       and financedate between '+str(@fromdate)+'  and '+str(@todate)+' 
       and a.cscompid=b.cscompid and a.csbillid=b.csbillid and csinfotype=1
     group by curcomp,csitemno
     having SUM(ISNULL(csitemamt,0))  between '+str(@prjsmallamt)+'  and '+str(@prjbigamt)+' 
     order by  SUM(ISNULL(csitemamt,0)) desc'
     execute(@strSql)   
     set @strSql='update c set goodsno=goodsno_s,goodscnt=goodscnt_s,goodsamt=goodsamt_s
     from  #prj_info c,(  select  top '+str(@topnum)+' compid=curcomp ,iseqno=row_number() over(order by  SUM(ISNULL(csitemamt,0)) desc), goodsno_s=csitemno,goodscnt_s=SUM(ISNULL(csitemcount,0)),goodsamt_s=SUM(ISNULL(csitemamt,0)) 
     from mconsumeinfo a with(nolock), dconsumeinfo b with(nolock), compchaininfo
     where curcomp= '+ str(@compid) +' and a.cscompid=relationcomp
       and financedate between '+str(@fromdate)+'  and '+str(@todate)+' 
       and a.cscompid=b.cscompid and a.csbillid=b.csbillid and csinfotype=2
     group by curcomp,csitemno
     having SUM(ISNULL(csitemamt,0))  between '+str(@goodssmallamt)+'  and '+str(@goodsbigamt)+' 
     ) as d where c.compid=d.compid and c.iseqno=d.iseqno '
 
	 execute(@strSql)   
       
        
     update a set prjname=b.prjname,prjtype=prjreporttype
     from #prj_info a,projectnameinfo b where a.prjno=b.prjno
     
     update a set goodsname=b.goodsname,goodstype=goodspricetype
     from #prj_info a,goodsnameinfo b where a.goodsno=b.goodsno
    
	 update a set compname=b.compname
	 from #prj_info a,companyinfo b where b.compno=a.compid 
	 
	 select compid,iseqno,compname,prjno,prjname,prjtype,prjcnt,prjamt,goodsno,goodsname,goodstype,goodscnt,goodsamt 
	 from #prj_info order by iseqno asc
     drop table #prj_info      
       
end  
go
--exec upg_prj_goods_consume_analysize '001',10,'20130801','20130831',0,100000,0,10000