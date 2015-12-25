if exists(select 1 from sysobjects where type='P' and name='upg_analysis_system_shop')
	drop procedure upg_analysis_system_shop
go
CREATE procedure upg_analysis_system_shop                        
(                        
 @compid varchar(10),                        
 @fromdate varchar(8),                        
 @todate varchar(8)                        
)  
as  
begin  
 create table #analysisresult  
 (  
  
  compno			varchar(10) null, --门店号
  resusttyep		int   null,
  showtype			int   null,		--1 门店分析  2 美发分析 3 美容分析
  resusttyeptext  varchar(60) null, 
  month1r		float  null, --1月  
  month2r		float  null, --2月  
  month3r		float  null, --3月  
  month4r		float  null, --4月  
  month5r		float  null, --5月  
  month6r		float  null, --6月  
  month7r		float  null, --7月  
  month8r		float  null, --8月  
  month9r		float  null, --9月  
  month10r		float  null, --10月  
  month11r		float  null, --11月  
  month12r		float  null, --12月  
  months_12r    float  null, --总合计  
  monthf_5r		float  null, --前5名平均  
  montha_12r    float  null, --总平均  
  montha_5r		float  null, --后5名平均  
 )  
 declare @searyear varchar(4)
 set @searyear=SUBSTRING(@fromdate,1,4)
 declare @sqltitle varchar(600)  
 set @sqltitle = '['+@searyear+''+'01],['+@searyear+''+'02],['+@searyear+''+'03],['+@searyear+''+'04],['+@searyear+''+'05],['+@searyear+''+'06],['+@searyear+''+'07],['+@searyear+''+'08],['+@searyear+''+'09],['+@searyear+''+'10],['+@searyear+''+'11],['+@searyear+''+'12]'  

 declare @targetsql varchar(800)  
 --1总业绩       
  set @targetsql='select ddate=SUBSTRING(ddate,1,6),totalyeji=convert(numeric(20,1),SUM(ISNULL(totalyeji,0)))  
  from compclasstraderesult where compid='+@compid+' and  ddate between '+@fromdate+' and '+@todate+'  
  group by SUBSTRING(ddate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select 1,''总业绩'',1,* from ('+@targetsql+') a pivot (max(totalyeji) for ddate in (' + @sqltitle + ')) b  ')  


  --2 美发虚业绩  
  set @targetsql='select ddate=SUBSTRING(ddate,1,6),hairyeji=convert(numeric(20,1),SUM(ISNULL(hairyeji,0)))  
  from compclasstraderesult where compid='+@compid+' and  ddate between '+@fromdate+' and '+@todate+'     
  group by SUBSTRING(ddate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  2,''美发虚业绩'',1,* from ('+@targetsql+') a pivot (max(hairyeji) for ddate in (' + @sqltitle + ')) b  ')  
  
  --3 美容虚业绩  
  set @targetsql='select ddate=SUBSTRING(ddate,1,6),beautyeji=convert(numeric(20,1),SUM(ISNULL(beautyeji,0)))  
  from compclasstraderesult where compid='+@compid+' and  ddate between '+@fromdate+' and '+@todate+'        
  group by SUBSTRING(ddate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  3,''美容虚业绩'',1,* from ('+@targetsql+') a pivot (max(beautyeji) for ddate in (' + @sqltitle + ')) b  ')  
   
   
     -- 4 美容营业额占比  
   set @targetsql='select ddate=SUBSTRING(ddate,1,6),realbeautyeji=convert(numeric(20,4),SUM(ISNULL(beautyeji,4))/SUM(ISNULL(totalyeji,0)))  
  from compclasstraderesult where compid='+@compid+'  and isnull(realtotalyeji,0)>0   and  ddate between '+@fromdate+' and '+@todate+'    
  group by SUBSTRING(ddate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  4,''美容营业额占比'',1,* from ('+@targetsql+') a pivot (max(realbeautyeji) for ddate in (' + @sqltitle + ')) b  ')  
    

      --5 总实业绩  
  set @targetsql='select ddate=SUBSTRING(ddate,1,6),realtotalyeji=convert(numeric(20,1),SUM(ISNULL(realtotalyeji,0)))  
  from compclasstraderesult where compid='+@compid+'  and  ddate between '+@fromdate+' and '+@todate+'       
  group by SUBSTRING(ddate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  5,''总实业绩'',1,* from ('+@targetsql+') a pivot (max(realtotalyeji) for ddate in (' + @sqltitle + ')) b  ')  
   
    --6 耗卡率(储值销卡/卡异动)  
  set @targetsql='select ddate=SUBSTRING(dateReport,1,6),realrate=convert(numeric(20,4),SUM(ISNULL(cardsalesservices,1))/ISNULL(sum(ISNULL(totalcardtrans,0)),0))  
  from detial_trade_byday_fromshops where shopId='+@compid+' and ISNULL(totalcardtrans,0)>0   and  dateReport between '+@fromdate+' and '+@todate+'
  group by SUBSTRING(dateReport,1,6)'  
   insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  6,''耗卡率(储值销卡/卡异动)'',1,* from ('+@targetsql+') a pivot (max(realrate) for ddate in (' + @sqltitle + ')) b  ')  
   
   
   
   
     --7 美容部消耗占比  
  set @targetsql='select ddate=SUBSTRING(ddate,1,6),realbeautyeji=convert(numeric(20,4),SUM(ISNULL(realbeautyeji,4))/SUM(ISNULL(realtotalyeji,0)))  
  from compclasstraderesult where compid='+@compid+'  and isnull(realtotalyeji,0)>0   and  ddate between '+@fromdate+' and '+@todate+'    
  group by SUBSTRING(ddate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  7,''美容部消耗占比'',1,* from ('+@targetsql+') a pivot (max(realbeautyeji) for ddate in (' + @sqltitle + ')) b  ')  
    


   
   
  --9美发实业绩  
   set @targetsql='select ddate=SUBSTRING(ddate,1,6),realhairyeji=convert(numeric(20,1),SUM(ISNULL(realhairyeji,0)))  
  from compclasstraderesult where compid='+@compid+'  and  ddate between '+@fromdate+' and '+@todate+'      
  group by SUBSTRING(ddate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  9,''美发实业绩[包含美发产品]'',2,* from ('+@targetsql+') a pivot (max(realhairyeji) for ddate in (' + @sqltitle + ')) b  ')  
   
   create table  #m_dconsumeinfo
   (
		cscompid		varchar(10)     NULL,   --公司编号
		csbillid		varchar(20)	    NULL,   --消费单号
		financedate		varchar(8)		NULL    ,   --帐务日期
		backcsflag		int				NULL    ,   --是否已经返销: 0-没有返销 1--已经返销
		backcsbillid	varchar(20)		NULL    ,   --返销单号
		csitemno		varchar(20)     NULL,		--项目/产品代码
		csitemunit		varchar(5)      NULL,		--单位
		csitemcount		float           NULL,		--数量
		csitemamt		float           NULL,		--金额
		cspaymode		varchar(5)		NULL,		--支付方式
   )
   
   insert #m_dconsumeinfo(cscompid,csbillid,financedate,backcsflag,backcsbillid,csitemno,csitemunit,csitemcount,csitemamt,cspaymode)
   select a.cscompid,a.csbillid,financedate,backcsflag,backcsbillid,csitemno,csitemunit,csitemcount,csitemamt,cspaymode
   from mconsumeinfo a,dconsumeinfo b
   where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between @fromdate and @todate and a.cscompid=@compid
   
        -- 8 总客单数  
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billcount=count(distinct b.csbillid)   
   from #m_dconsumeinfo b where b.cscompid='+@compid+'  
   group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  8,''总客单数'',1,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
   
   
     --10 洗剪吹消耗   
  set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemamt=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''01'')  
  and b.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  10,''洗剪吹消耗'',2,* from ('+@targetsql+') a pivot (max(csitemamt) for ddate in (' + @sqltitle + ')) b  ')  
   
   
  --11 烫发消耗   
  set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemamt=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''03'')  
  and b.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  11,''烫发消耗'',2,* from ('+@targetsql+') a pivot (max(csitemamt) for ddate in (' + @sqltitle + ')) b  ')  
   
     --12 染发消耗   
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemamt=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''02'')  
  and b.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  12,''染发消耗'',2,* from ('+@targetsql+') a pivot (max(csitemamt) for ddate in (' + @sqltitle + ')) b  ')  
   
  --13 护理消耗   
  set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemamt=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''04'',''05'',''07'',''14'')  
  and b.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  13,''护理消耗'',2,* from ('+@targetsql+') a pivot (max(csitemamt) for ddate in (' + @sqltitle + ')) b  ')  
   
  --14 头皮消耗  
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemamt=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where  b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''06'')  
  and b.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  14,''头皮消耗'',2,* from ('+@targetsql+') a pivot (max(csitemamt) for ddate in (' + @sqltitle + ')) b  ')  
    
   --15 洗剪吹项目数占比
     set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''01'') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
   and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(b.financedate,1,6)' 
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  15,''洗剪吹项目数占比'',2,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
   --16 烫发消耗占比
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''03'') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where  b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
   and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  16,''烫发消耗占比'',2,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
  
    --17 染发消耗占比
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''02'') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
   and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  17,''染发消耗占比'',2,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
    --18 护理消耗占比
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''04'',''05'',''07'',''14'') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
   and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  18,''护理消耗占比'',2,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
    --19头皮消耗占比
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''06'') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
   from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
   and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  19,''头皮消耗占比'',2,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
  
  
    -- 20美发总项目数  
  set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemcount=convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
  and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and b.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  20,''美发总项目数[包含洗剪吹]'',2,* from ('+@targetsql+') a pivot (max(csitemcount) for ddate in (' + @sqltitle + ')) b  ')  
   
  -- 21 洗剪吹项目数   
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemcount=convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''01'')  
  and b.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  21,''洗剪吹项目数'',2,* from ('+@targetsql+') a pivot (max(csitemcount) for ddate in (' + @sqltitle + ')) b  ')  
   
  --22 烫发项目数   
  set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemcount=convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''03'')  
  and b.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  22,''烫发项目数'',2,* from ('+@targetsql+') a pivot (max(csitemcount) for ddate in (' + @sqltitle + ')) b  ')  
   
   
  --23 染发项目数   
    set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemcount=convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''02'')  
  and b.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  23,''染发项目数'',2,* from ('+@targetsql+') a pivot (max(csitemcount) for ddate in (' + @sqltitle + ')) b  ')  
   
  --24 护理项目数   
  set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemcount=convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''04'',''05'',''07'',''14'')  
  and b.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  24,''护理项目数'',2,* from ('+@targetsql+') a pivot (max(csitemcount) for ddate in (' + @sqltitle + ')) b  ')  
   
  --25 头皮项目数  
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemcount=convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''06'')  
  and b.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  25,''头皮项目数'',2,* from ('+@targetsql+') a pivot (max(csitemcount) for ddate in (' + @sqltitle + ')) b  ')  
         
  
     
  -- 26 洗剪吹项目数占比
      set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''01'') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
   and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  26,''洗剪吹项目数占比'',2,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
    -- 27 烫发项目数占比 
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''03'') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
   and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  27,''烫发项目数占比'',2,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
    --28 染发项目数占比 
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''02'') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
   and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  28,''染发项目数占比'',2,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
    --29 护理项目数占比 
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''04'',''05'',''07'',''14'') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
   and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  29,''护理项目数占比'',2,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
    --30 头皮项目数占比  
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''06'') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
   and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  30,''头皮项目数占比'',2,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
       
  -- 31美发总均价   
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemaprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
   and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  31,''美发总均价'',2,* from ('+@targetsql+') a pivot (max(csitemaprice) for ddate in (' + @sqltitle + ')) b  ')  
   
  --32 洗剪吹均价   
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemaprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno  and isnull(c.prjreporttype,'''') in (''01'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  32,''洗剪吹均价'',2,* from ('+@targetsql+') a pivot (max(csitemaprice) for ddate in (' + @sqltitle + ')) b  ')  
   
    
  --33 烫发均价   
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemaprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno  and isnull(c.prjreporttype,'''') in (''03'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  33,''烫发均价'',2,* from ('+@targetsql+') a pivot (max(csitemaprice) for ddate in (' + @sqltitle + ')) b  ')  
   
  --34染发均价   
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemaprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''02'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  34,''染发均价'',2,* from ('+@targetsql+') a pivot (max(csitemaprice) for ddate in (' + @sqltitle + ')) b  ')  
   
  --35护理均价   
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemaprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''04'',''05'',''07'',''14'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  35,''护理均价'',2,* from ('+@targetsql+') a pivot (max(csitemaprice) for ddate in (' + @sqltitle + ')) b  ')  
   
  --36头皮均价  
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemaprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''06'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  36,''头皮均价'',2,* from ('+@targetsql+') a pivot (max(csitemaprice) for ddate in (' + @sqltitle + ')) b  ')  
   
   
   
 -- 37美发总客单数[包含洗剪吹]   
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billcount=count(distinct b.csbillid)   
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
   and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  37,''美发总客单数[包含洗剪吹]'',2,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
   
   
 --38美发总客单价   
  set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/count(distinct b.csbillid))   
   from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
   and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  38,''美发总客单价'',2,* from ('+@targetsql+') a pivot (max(billprice) for ddate in (' + @sqltitle + ')) b  ')  
   
   
 --39 美发疗程客单数  
  set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billcount=count(distinct b.csbillid)   
   from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0 and isnull(cspaymode,'''')=''9''  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  39,''美发疗程客单数'',2,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
 --40 美发疗程客单价   
  set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/count(distinct b.csbillid))   
   from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0 and isnull(cspaymode,'''')=''9''  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  40,''美发疗程客单价'',2,* from ('+@targetsql+') a pivot (max(billprice) for ddate in (' + @sqltitle + ')) b  ') 
          
   --41美发疗程客单数占比   
  set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billcount=convert(numeric(20,4),(count(distinct case when  isnull(cspaymode,'''')=''9'' then b.csbillid else '''' end )-1)*1.0/count(distinct b.csbillid))  
   from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  41,''美发疗程客单数占比'',2,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
  
  
    --42美容实业绩[包含美容产品.干洗水洗]  
    set @targetsql='select ddate=SUBSTRING(ddate,1,6),realbeautyeji=convert(numeric(20,1),SUM(ISNULL(realbeautyeji,0)))  
  from compclasstraderesult where compid='+@compid+' and  ddate between '+@fromdate+' and '+@todate+'       
  group by SUBSTRING(ddate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  42,''美容实业绩[包含美容产品.干洗水洗]'',3,* from ('+@targetsql+') a pivot (max(realbeautyeji) for ddate in (' + @sqltitle + ')) b  ')  
   
  -- 43养生类SPA总消耗   
  set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemamt=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0 
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  43,''养生类SPA总消耗'',3,* from ('+@targetsql+') a pivot (max(csitemamt) for ddate in (' + @sqltitle + ')) b  ')  
   
  -- 44面部类消耗   
  set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemamt=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''10'',''17'')  
  and b.cscompid='+@compid+' and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0 
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  44,''面部类消耗'',3,* from ('+@targetsql+') a pivot (max(csitemamt) for ddate in (' + @sqltitle + ')) b  ')  
   
  --45胸部类消耗   
  set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemamt=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''18'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0 
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  45,''胸部类消耗'',3,* from ('+@targetsql+') a pivot (max(csitemamt) for ddate in (' + @sqltitle + ')) b  ')  
   
  --46 其他类消耗   
  set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemamt=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''09'',''11'',''13'',''22'',''23'')  
  and b.cscompid='+@compid+' and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0 
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  46,''其他类消耗'',3,* from ('+@targetsql+') a pivot (max(csitemamt) for ddate in (' + @sqltitle + ')) b  ')  
   
  --47 老疗程消耗  
 set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemamt=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
 from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''15'')  
  and b.cscompid='+@compid+' and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0 
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  47,''老疗程消耗'',3,* from ('+@targetsql+') a pivot (max(csitemamt) for ddate in (' + @sqltitle + ')) b  ')  
   
    
    
  -- 48养生类SPA总消耗占比 
    set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
   and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  48,''养生类SPA总消耗占比'',3,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
    --49面部类消耗占比 
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''10'',''17'') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
   and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  49,''面部类消耗占比'',3,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
    --50胸部类消耗占比 
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''18'') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
 from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
  and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  50,''胸部类消耗占比'',3,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
    --51其他类消耗占比
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''09'',''11'',''13'',''22'',''23'') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
  and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  51,''其他类消耗占比'',3,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
    -- 52 老疗程消耗占比  
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''15'') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
   and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  52,''老疗程消耗占比'',3,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
   
     -- 53美容总项目数[不包含干洗水洗]   
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemcount=convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
   from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')   
  and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
  and b.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  53,''美容总项目数[不包含干洗水洗]'',3,* from ('+@targetsql+') a pivot (max(csitemcount) for ddate in (' + @sqltitle + ')) b  ')  
   
   
  --54养生项目数   
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemcount=convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
   from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'')  
  and b.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  54,''养生项目数'',3,* from ('+@targetsql+') a pivot (max(csitemcount) for ddate in (' + @sqltitle + ')) b  ')  
   
  --55面部项目数   
    set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemcount=convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in  (''10'',''17'')  
  and b.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  55,''面部项目数'',3,* from ('+@targetsql+') a pivot (max(csitemcount) for ddate in (' + @sqltitle + ')) b  ')  
   
  --56胸部项目数   
    set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemcount=convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
   from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''18'')  
  and b.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  56,''胸部项目数'',3,* from ('+@targetsql+') a pivot (max(csitemcount) for ddate in (' + @sqltitle + ')) b  ')  
   
  --57其他类项目数   
    set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemcount=convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
   from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''09'',''11'',''13'',''22'',''23'')  
  and b.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  57,''其他类项目数'',3,* from ('+@targetsql+') a pivot (max(csitemcount) for ddate in (' + @sqltitle + ')) b  ')  
   
  --58 老项目项目数  
  set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemcount=convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
   from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjreporttype,'''')  in (''15'')  
  and b.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  58,''老项目项目数'',3,* from ('+@targetsql+') a pivot (max(csitemcount) for ddate in (' + @sqltitle + ')) b  ')  
        
   
       
  -- 59养生项目数占比 
  set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0)))  
   from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
   and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
 and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  59,''养生项目数占比'',3,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
   
   --60面部项目数占比
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''10'',''17'') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0)))  
   from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
   and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
 and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  60,''面部项目数占比'',3,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
    -- 61胸部项目数占比
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''18'') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0)))  
   from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
   and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
 and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  61,''胸部项目数占比'',3,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
    -- 62其他类项目数占比 
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''09'',''11'',''13'',''22'',''23'') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0))) 
	from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
  and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  62,''其他类项目数占比'',3,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
    --63 老项目项目数占比   
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''15'') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0)))  
   from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
   and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  63,''老项目项目数占比'',3,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
          
        
 -- 64美容均价   
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemaprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
  and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  64,''美容均价'',3,* from ('+@targetsql+') a pivot (max(csitemaprice) for ddate in (' + @sqltitle + ')) b  ')  
   
 --65养生类均价   
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemaprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno  and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  65,''养生类均价'',3,* from ('+@targetsql+') a pivot (max(csitemaprice) for ddate in (' + @sqltitle + ')) b  ')  
   
 --66面部类均价   
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemaprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno  and isnull(c.prjreporttype,'''') in (''10'',''17'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  66,''面部类均价'',3,* from ('+@targetsql+') a pivot (max(csitemaprice) for ddate in (' + @sqltitle + ')) b  ')  
   
 --67胸部类均价   
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemaprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno  and isnull(c.prjreporttype,'''') in (''18'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  67,''胸部类均价'',3,* from ('+@targetsql+') a pivot (max(csitemaprice) for ddate in (' + @sqltitle + ')) b  ')  
   
 --68其他类均价   
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemaprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno  and isnull(c.prjreporttype,'''') in (''09'',''11'',''13'',''22'',''23'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  68,''其他类均价'',3,* from ('+@targetsql+') a pivot (max(csitemaprice) for ddate in (' + @sqltitle + ')) b  ')  
   
 --69老项目均价  
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),csitemaprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno  and isnull(c.prjreporttype,'''') in (''15'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  69,''老项目均价'',3,* from ('+@targetsql+') a pivot (max(csitemaprice) for ddate in (' + @sqltitle + ')) b  ')  
                
 -- 70美容部客单数[不包含干洗水洗]   
   set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billcount=count(distinct b.csbillid)   
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
  and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  70,''美容部客单数[不包含干洗水洗]'',3,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
   
 -- 71美容部客单价   
 set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/count(distinct b.csbillid))   
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
  and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  71,''美容部客单价'',3,* from ('+@targetsql+') a pivot (max(billprice) for ddate in (' + @sqltitle + ')) b  ')  
   
 -- 72 美容疗程客单数   
  set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billcount=count(distinct b.csbillid)   
 from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0 and isnull(cspaymode,'''')=''9''  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  72,''美容疗程客单数'',3,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
 -- 73 美容疗程客单价   
  set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/count(distinct b.csbillid))   
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0 and isnull(cspaymode,'''')=''9''  
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  73,''美容疗程客单价'',3,* from ('+@targetsql+') a pivot (max(billprice) for ddate in (' + @sqltitle + ')) b  ')  
       
 
 -- 74 美容疗程客单占比   
 set @targetsql='select ddate=SUBSTRING(b.financedate,1,6),billcount=convert(numeric(20,4),(count(distinct case when  isnull(cspaymode,'''')=''9'' then b.csbillid else '''' end )-1)*1.0/count(distinct b.csbillid))  
  from #m_dconsumeinfo b,projectnameinfo c  
  where b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
  and b.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(b.financedate,1,6)'  
  insert #analysisresult(resusttyep,resusttyeptext,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  74,''美容疗程客单占比'',3,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
        
	update #analysisresult set compno=@compid,months_12r=convert(numeric(20,4),ISNULL(month1r,0)+ISNULL(month2r,0)+ISNULL(month3r,0)+ISNULL(month4r,0)+ISNULL(month5r,0)+ISNULL(month6r,0)  
           +ISNULL(month7r,0)+ISNULL(month8r,0)+ISNULL(month9r,0)+ISNULL(month10r,0)+ISNULL(month11r,0)+ISNULL(month12r,0))  
             
 
	 --1总业绩 2 美发虚业绩 3 美容虚业绩 4 美甲需业绩 5 总实业绩 8美发实业绩  32美容实业绩  
	 update #analysisresult set montha_12r=convert(numeric(20,1),ISNULL(months_12r,0)/(  
	  (case when ISNULL(month1r,0)>0 then 1 else 0 end )  
	 +(case when ISNULL(month2r,0)>0 then 1 else 0 end )  
	 +(case when ISNULL(month3r,0)>0 then 1 else 0 end )  
	 +(case when ISNULL(month4r,0)>0 then 1 else 0 end )  
	 +(case when ISNULL(month5r,0)>0 then 1 else 0 end )  
	 +(case when ISNULL(month6r,0)>0 then 1 else 0 end )  
	 +(case when ISNULL(month7r,0)>0 then 1 else 0 end )  
	 +(case when ISNULL(month8r,0)>0 then 1 else 0 end )  
	 +(case when ISNULL(month9r,0)>0 then 1 else 0 end )  
	 +(case when ISNULL(month10r,0)>0 then 1 else 0 end )  
	 +(case when ISNULL(month11r,0)>0 then 1 else 0 end )  
	 +(case when ISNULL(month12r,0)>0 then 1 else 0 end )))  
	 where ISNULL(months_12r,0)>0  
 

    
  --monthf_5r    float  null, --前5名平均  
    update A set A.monthf_5r=convert(numeric(20,4),D.montha_5r)
	from #analysisresult A ,(select resusttyep,montha_5r=sum(isnull(resultamt,0))/5  from 
	   (select resusttyep,compno,resultamt ,row_number() over( PARTITION BY resusttyep order by SUM(ISNULL(resultamt,0)) desc) fid
	      from   ( select resusttyep,compno,resultamt=convert(numeric(20,1),sum(ISNULL(resultamt,0))/sum((case when ISNULL(resultamt,0)>0 then 1 else 0 end ))) 
				from jsanalysisresult  where compno not in ('040','037','004','010') and ISNULL(resultamt,0)<>0 and mmonth between substring(@fromdate,1,6) and substring(@todate,1,6)
				group by resusttyep,compno) B  where ISNULL(resultamt,0)<>0
	    group by resusttyep,compno,resultamt) C
	     where fid<=5  group by resusttyep ) D
	where A.resusttyep=D.resusttyep
  
	--montha_5r    float  null, --后5名平均  
    update A set A.montha_5r=convert(numeric(20,4),D.montha_5r)
	from #analysisresult A ,(select resusttyep,montha_5r=sum(isnull(resultamt,0))/5  from 
	   (select resusttyep,compno,resultamt ,row_number() over( PARTITION BY resusttyep order by SUM(ISNULL(resultamt,0)) asc) fid
	      from   ( select resusttyep,compno,resultamt=convert(numeric(20,1),sum(ISNULL(resultamt,0))/sum((case when ISNULL(resultamt,0)>0 then 1 else 0 end ))) 
				from jsanalysisresult  where compno not in ('040','037','004','010') and ISNULL(resultamt,0)<>0 and mmonth between substring(@fromdate,1,6) and substring(@todate,1,6)
				group by resusttyep,compno) B  where ISNULL(resultamt,0)<>0
	    group by resusttyep,compno,resultamt) C
	     where fid<=5  group by resusttyep ) D
	where A.resusttyep=D.resusttyep
    
	select a.compno,compname,resusttyeptext,resusttyep,showtype,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r,months_12r,monthf_5r,montha_12r,montha_5r  
	  from #analysisresult a,companyinfo b  where a.compno=b.compno order by resusttyep 
  
	drop table #m_dconsumeinfo
	drop table #analysisresult  
end  
go








