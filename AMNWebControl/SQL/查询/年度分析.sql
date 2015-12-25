if exists(select 1 from sysobjects where type='P' and name='upg_analysis_system_2013')
	drop procedure upg_analysis_system_2013
go
CREATE procedure upg_analysis_system_2013                        
(                        
 @compid varchar(10),                        
 @fromdate varchar(8),                        
 @todate varchar(8)                        
)  
as  
begin  
 create table #analysisresult  
 (  
  
  compno     varchar(10) null, --门店号  
  resusttyep    int   null, --业绩类型 1总业绩 2 美发虚业绩 3 美容虚业绩 4 美甲需业绩 5 总实业绩 6 耗卡率 7 美容部消耗占比  
               -- 8美发实业绩  10 洗剪吹消耗 11 烫发消耗 12 染发消耗 13 护理消耗 14 头皮消耗  
               -- 15美发总项目数 16洗剪吹项目数 17 烫发项目数 18染发项目数 19 护理项目数 20 头皮项目数  
               -- 21美发总均价 22 洗剪吹均价 23 烫发均价 24染发均价 25护理均价 26头皮均价  
               -- 27美发总客单数 28美发总客单价 29 美发疗程客单数 30 美发疗程客单价 31美发疗程客单数占比  
               -- 32美容实业绩  33养生类SPA总消耗 34面部类消耗 35胸部类消耗 36 其他类消耗 37 老疗程消耗  
               -- 38美容总项目数 39养生项目数 40面部项目数 41胸部项目数 42其他类项目数 43 老项目项目数  
               -- 44美容均价 45养生类均价 46面部类均价 47胸部类均价 48其他类均价 49老项目均价  
               -- 50美容部客单数 51美容部客单价 52 美容疗程客单数 53 美容疗程客单价 54 美容疗程客单占比  
               -- 55 总流失人数 56美发部流人数 57烫染部流失人数 58 美容部流失人数 59 大堂接待收银流失人数 60 后勤流失人数  
               -- 61 3个月内总流失人数 62 3个月内美发部流人数 63 3个月内烫染部流失人数 64 3个月内美容部流失人数 65 3个月内大堂接待收银流失人数 66 3个月内后勤流失人数  
               -- 67 6个月内总流失人数 68 6个月内美发部流人数 69 6个月内烫染部流失人数 70 6个月内美容部流失人数 71 6个月内大堂接待收银流失人数 62 6个月内后勤流失人数  
               -- 73 12个月内总流失人数 74 12个月内美发部流人数 75 12个月内烫染部流失人数 76 12个月内美容部流失人数 77 12个月内大堂接待收银流失人数 78 12个月内后勤流失人数  
               -- 79 3-6个月低于3000 的员工 80 美发部3-6个月低于3000 的员工 81 烫染部3-6个月低于3000 的员工 82 美容部3-6个月低于3000 的员工  
               -- 83 6-12个月低于5000 的员工 84 美发部6-12个月低于5000 的员工 85 烫染部6-12个月低于5000 的员工 86 美容部6-12个月低于5000 的员工  
               -- 87 12个月上低于7000 的员工 88 美发部12个月上低于7000 的员工 89 烫染部12个月上低于7000 的员工 89 美容部12个月上低于7000 的员工  
               -- 90 洗剪吹消耗占比 91 烫发消耗占比 92 染发消耗占比 93 护理消耗占比 94 头皮消耗占比  
               -- 95洗剪吹项目数占比 96 烫发项目数占比 97染发项目数占比 98 护理项目数占比 99 头皮项目数占比  
               -- 100养生类SPA总消耗 101面部类消耗 102胸部类消耗 103其他类消耗 104 老疗程消耗  
               -- 105养生项目数 106面部项目数 107胸部项目数 108其他类项目数 109 老项目项目数  
               -- 110 美容营业额占比    -- 112 门店总客单  
  month1r     float  null, --1月  
  month2r     float  null, --2月  
  month3r     float  null, --3月  
  month4r     float  null, --4月  
  month5r     float  null, --5月  
  month6r     float  null, --6月  
  month7r     float  null, --7月  
  month8r     float  null, --8月  
  month9r     float  null, --9月  
  month10r    float  null, --10月  
  month11r    float  null, --11月  
  month12r    float  null, --12月  
  months_12r    float  null, --总合计  
  monthf_5r    float  null, --前5名平均  
  montha_12r    float  null, --总平均  
  montha_5r    float  null, --后5名平均  
 )  
 declare @sqltitle varchar(600)  
 set @sqltitle = '[201301],[201302],[201303],[201304],[201305],[201306],[201307],[201308],[201309],[201310],[201311],[201312]'  
   
 declare @targetsql varchar(800)  
 --1总业绩       
  set @targetsql='select ddate=SUBSTRING(ddate,1,6),totalyeji=convert(numeric(20,1),SUM(ISNULL(totalyeji,0)))  
  from compclasstraderesult where compid='+@compid+'   
  group by SUBSTRING(ddate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  1,* from ('+@targetsql+') a pivot (max(totalyeji) for ddate in (' + @sqltitle + ')) b  ')  
      
  --2 美发虚业绩  
  set @targetsql='select ddate=SUBSTRING(ddate,1,6),hairyeji=convert(numeric(20,1),SUM(ISNULL(hairyeji,0)))  
  from compclasstraderesult where compid='+@compid+'   
  group by SUBSTRING(ddate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  2,* from ('+@targetsql+') a pivot (max(hairyeji) for ddate in (' + @sqltitle + ')) b  ')  
   
  --3 美容虚业绩  
  set @targetsql='select ddate=SUBSTRING(ddate,1,6),beautyeji=convert(numeric(20,1),SUM(ISNULL(beautyeji,0)))  
  from compclasstraderesult where compid='+@compid+'   
  group by SUBSTRING(ddate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  3,* from ('+@targetsql+') a pivot (max(beautyeji) for ddate in (' + @sqltitle + ')) b  ')  
   
  --4 美甲需业绩  
  set @targetsql='select ddate=SUBSTRING(ddate,1,6),fingeryeji=convert(numeric(20,1),SUM(ISNULL(fingeryeji,0)))  
  from compclasstraderesult where compid='+@compid+'   
  group by SUBSTRING(ddate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  4,* from ('+@targetsql+') a pivot (max(fingeryeji) for ddate in (' + @sqltitle + ')) b  ')  
   
   --5 总实业绩  
  set @targetsql='select ddate=SUBSTRING(ddate,1,6),realtotalyeji=convert(numeric(20,1),SUM(ISNULL(realtotalyeji,0)))  
  from compclasstraderesult where compid='+@compid+'   
  group by SUBSTRING(ddate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  5,* from ('+@targetsql+') a pivot (max(realtotalyeji) for ddate in (' + @sqltitle + ')) b  ')  
   
    
   
  --6 耗卡率(储值销卡/卡异动)  
  set @targetsql='select ddate=SUBSTRING(dateReport,1,6),realrate=convert(numeric(20,4),SUM(ISNULL(cardsalesservices,1))/ISNULL(sum(ISNULL(totalcardtrans,0)),0))  
  from detial_trade_byday_fromshops where shopId='+@compid+' and ISNULL(totalcardtrans,0)>0  
  group by SUBSTRING(dateReport,1,6)'  
   insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  6,* from ('+@targetsql+') a pivot (max(realrate) for ddate in (' + @sqltitle + ')) b  ')  
   
  --7 美容部消耗占比  
  set @targetsql='select ddate=SUBSTRING(ddate,1,6),realbeautyeji=convert(numeric(20,4),SUM(ISNULL(realbeautyeji,4))/SUM(ISNULL(realtotalyeji,0)))  
  from compclasstraderesult where compid='+@compid+'  and isnull(realtotalyeji,0)>0  
  group by SUBSTRING(ddate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  7,* from ('+@targetsql+') a pivot (max(realbeautyeji) for ddate in (' + @sqltitle + ')) b  ')  
    
  -- 110 美容营业额占比  
   set @targetsql='select ddate=SUBSTRING(ddate,1,6),realbeautyeji=convert(numeric(20,4),SUM(ISNULL(beautyeji,4))/SUM(ISNULL(totalyeji,0)))  
  from compclasstraderesult where compid='+@compid+'  and isnull(realtotalyeji,0)>0  
  group by SUBSTRING(ddate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  110,* from ('+@targetsql+') a pivot (max(realbeautyeji) for ddate in (' + @sqltitle + ')) b  ')  
    
  --8美发实业绩  
   set @targetsql='select ddate=SUBSTRING(ddate,1,6),realhairyeji=convert(numeric(20,1),SUM(ISNULL(realhairyeji,0)))  
  from compclasstraderesult where compid='+@compid+'   
  group by SUBSTRING(ddate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  8,* from ('+@targetsql+') a pivot (max(realhairyeji) for ddate in (' + @sqltitle + ')) b  ')  
   
  --10 洗剪吹消耗   
  set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemamt=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''01'')  
  and a.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  10,* from ('+@targetsql+') a pivot (max(csitemamt) for ddate in (' + @sqltitle + ')) b  ')  
   
  --11 烫发消耗   
  set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemamt=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''03'')  
  and a.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  11,* from ('+@targetsql+') a pivot (max(csitemamt) for ddate in (' + @sqltitle + ')) b  ')  
   
  --12 染发消耗   
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemamt=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''02'')  
  and a.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  12,* from ('+@targetsql+') a pivot (max(csitemamt) for ddate in (' + @sqltitle + ')) b  ')  
   
  --13 护理消耗   
  set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemamt=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''04'',''05'',''07'',''14'')  
  and a.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  13,* from ('+@targetsql+') a pivot (max(csitemamt) for ddate in (' + @sqltitle + ')) b  ')  
   
  --14 头皮消耗  
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemamt=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''06'')  
  and a.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  14,* from ('+@targetsql+') a pivot (max(csitemamt) for ddate in (' + @sqltitle + ')) b  ')  
    
  -- 90 洗剪吹项目数占比 91 烫发项目数占比 92 染发消耗占比 93 护理消耗占比 94 头皮消耗占比  
    set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''01'') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
   and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(a.financedate,1,6)'  
   
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  90,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
   
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''03'') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
   and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  91,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
  
     
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''02'') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
   and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  92,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''04'',''05'',''07'',''14'') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
   and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  93,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''06'') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
   and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  94,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
  -- 95洗剪吹项目数占比 96 烫发项目数占比 97染发项目数占比 98 护理项目数占比 99 头皮项目数占比  
      set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''01'') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
   and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  95,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''03'') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
   and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  96,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''02'') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
   and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  97,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''04'',''05'',''07'',''14'') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
   and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  98,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''06'') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
   and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  99,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
     
  -- 15美发总项目数  
  set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemcount=convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
  and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and a.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  15,* from ('+@targetsql+') a pivot (max(csitemcount) for ddate in (' + @sqltitle + ')) b  ')  
   
  -- 16洗剪吹项目数   
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemcount=convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''01'')  
  and a.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  16,* from ('+@targetsql+') a pivot (max(csitemcount) for ddate in (' + @sqltitle + ')) b  ')  
   
  --17 烫发项目数   
  set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemcount=convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''03'')  
  and a.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  17,* from ('+@targetsql+') a pivot (max(csitemcount) for ddate in (' + @sqltitle + ')) b  ')  
   
   
  --18染发项目数   
    set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemcount=convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''02'')  
  and a.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  18,* from ('+@targetsql+') a pivot (max(csitemcount) for ddate in (' + @sqltitle + ')) b  ')  
   
  --19 护理项目数   
  set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemcount=convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''04'',''05'',''07'',''14'')  
  and a.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  19,* from ('+@targetsql+') a pivot (max(csitemcount) for ddate in (' + @sqltitle + ')) b  ')  
   
  --20 头皮项目数  
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemcount=convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''06'')  
  and a.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  20,* from ('+@targetsql+') a pivot (max(csitemcount) for ddate in (' + @sqltitle + ')) b  ')  
                  
  -- 21美发总均价   
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemaprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
   and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  21,* from ('+@targetsql+') a pivot (max(csitemaprice) for ddate in (' + @sqltitle + ')) b  ')  
   
  --22 洗剪吹均价   
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemaprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno  and isnull(c.prjreporttype,'''') in (''01'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  22,* from ('+@targetsql+') a pivot (max(csitemaprice) for ddate in (' + @sqltitle + ')) b  ')  
   
    
  --23 烫发均价   
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemaprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno  and isnull(c.prjreporttype,'''') in (''03'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  23,* from ('+@targetsql+') a pivot (max(csitemaprice) for ddate in (' + @sqltitle + ')) b  ')  
   
  --24染发均价   
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemaprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''02'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  24,* from ('+@targetsql+') a pivot (max(csitemaprice) for ddate in (' + @sqltitle + ')) b  ')  
   
  --25护理均价   
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemaprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''04'',''05'',''07'',''14'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  25,* from ('+@targetsql+') a pivot (max(csitemaprice) for ddate in (' + @sqltitle + ')) b  ')  
   
  --26头皮均价  
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemaprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''06'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  26,* from ('+@targetsql+') a pivot (max(csitemaprice) for ddate in (' + @sqltitle + ')) b  ')  
   
 -- 27美发总客单数   
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billcount=count(distinct b.csbillid)   
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
   and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  27,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
   
   
 --28美发总客单价   
  set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/count(distinct b.csbillid))   
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
   and isnull(c.prjreporttype,'''') in (''01'',''03'',''02'',''04'',''05'',''07'',''14'',''06'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  28,* from ('+@targetsql+') a pivot (max(billprice) for ddate in (' + @sqltitle + ')) b  ')  
   
   
 --29 美发疗程客单数  
  set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billcount=count(distinct b.csbillid)   
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0 and isnull(cspaymode,'''')=''9''  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  29,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
 --30 美发疗程客单价   
  set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/count(distinct b.csbillid))   
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0 and isnull(cspaymode,'''')=''9''  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  30,* from ('+@targetsql+') a pivot (max(billprice) for ddate in (' + @sqltitle + ')) b  ')  
                
  --32美容实业绩  
    set @targetsql='select ddate=SUBSTRING(ddate,1,6),realbeautyeji=convert(numeric(20,1),SUM(ISNULL(realbeautyeji,0)))  
  from compclasstraderesult where compid='+@compid+'   
  group by SUBSTRING(ddate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  32,* from ('+@targetsql+') a pivot (max(realbeautyeji) for ddate in (' + @sqltitle + ')) b  ')  
   
  -- 33养生类SPA总消耗   
  set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemamt=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0 
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  33,* from ('+@targetsql+') a pivot (max(csitemamt) for ddate in (' + @sqltitle + ')) b  ')  
   
  -- 34面部类消耗   
  set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemamt=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''10'',''17'')  
  and a.cscompid='+@compid+' and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0 
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  34,* from ('+@targetsql+') a pivot (max(csitemamt) for ddate in (' + @sqltitle + ')) b  ')  
   
  --35胸部类消耗   
  set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemamt=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''18'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0 
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  35,* from ('+@targetsql+') a pivot (max(csitemamt) for ddate in (' + @sqltitle + ')) b  ')  
   
  --36 其他类消耗   
  set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemamt=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''09'',''11'',''13'',''22'',''23'')  
  and a.cscompid='+@compid+' and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0 
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  36,* from ('+@targetsql+') a pivot (max(csitemamt) for ddate in (' + @sqltitle + ')) b  ')  
   
  --37 老疗程消耗  
 set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemamt=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''15'')  
  and a.cscompid='+@compid+' and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0 
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  37,* from ('+@targetsql+') a pivot (max(csitemamt) for ddate in (' + @sqltitle + ')) b  ')  
   
   
  -- 100养生类SPA总消耗 101面部类消耗 102胸部类消耗 103其他类消耗 104 老疗程消耗  
    set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
   and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  100,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''10'',''17'') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
   and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  101,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''18'') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
  and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  102,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''09'',''11'',''13'',''22'',''23'') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
  and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  103,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''15'') then ISNULL(b.csitemamt,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemamt,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
   and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  104,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
    
  -- 105养生项目数 106面部项目数 107胸部项目数 108其他类项目数 109 老项目项目数  
  set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
   and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
 and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  105,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''10'',''17'') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
   and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
 and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  106,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''18'') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
   and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
 and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  107,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''09'',''11'',''13'',''22'',''23'') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0))) 
 
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
  and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  108,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billcount=convert(numeric(20,4),(sum(case when isnull(c.prjreporttype,'''') in (''15'') then ISNULL(b.csitemcount,0) else 0 end ))*1.0/SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
   and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  109,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
    
    
                 
  -- 38美容总项目数   
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemcount=convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')   
  and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
  and a.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  38,* from ('+@targetsql+') a pivot (max(csitemcount) for ddate in (' + @sqltitle + ')) b  ')  
   
   
  --39养生项目数   
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemcount=convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'')  
  and a.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  39,* from ('+@targetsql+') a pivot (max(csitemcount) for ddate in (' + @sqltitle + ')) b  ')  
   
  --40面部项目数   
    set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemcount=convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in  (''10'',''17'')  
  and a.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  40,* from ('+@targetsql+') a pivot (max(csitemcount) for ddate in (' + @sqltitle + ')) b  ')  
   
  --41胸部项目数   
    set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemcount=convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''18'')  
  and a.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  41,* from ('+@targetsql+') a pivot (max(csitemcount) for ddate in (' + @sqltitle + ')) b  ')  
   
  --42其他类项目数   
    set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemcount=convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjreporttype,'''') in (''09'',''11'',''13'',''22'',''23'')  
  and a.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  42,* from ('+@targetsql+') a pivot (max(csitemcount) for ddate in (' + @sqltitle + ')) b  ')  
   
  --43 老项目项目数  
  set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemcount=convert(numeric(20,0),SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjreporttype,'''')  in (''15'')  
  and a.cscompid='+@compid+'   and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  43,* from ('+@targetsql+') a pivot (max(csitemcount) for ddate in (' + @sqltitle + ')) b  ')  
                
 -- 44美容均价   
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemaprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
  and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  44,* from ('+@targetsql+') a pivot (max(csitemaprice) for ddate in (' + @sqltitle + ')) b  ')  
   
 --45养生类均价   
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemaprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno  and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  45,* from ('+@targetsql+') a pivot (max(csitemaprice) for ddate in (' + @sqltitle + ')) b  ')  
   
 --46面部类均价   
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemaprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno  and isnull(c.prjreporttype,'''') in (''10'',''17'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  46,* from ('+@targetsql+') a pivot (max(csitemaprice) for ddate in (' + @sqltitle + ')) b  ')  
   
 --47胸部类均价   
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemaprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno  and isnull(c.prjreporttype,'''') in (''18'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  47,* from ('+@targetsql+') a pivot (max(csitemaprice) for ddate in (' + @sqltitle + ')) b  ')  
   
 --48其他类均价   
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemaprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno  and isnull(c.prjreporttype,'''') in (''09'',''11'',''13'',''22'',''23'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  48,* from ('+@targetsql+') a pivot (max(csitemaprice) for ddate in (' + @sqltitle + ')) b  ')  
   
 --49老项目均价  
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),csitemaprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/SUM(ISNULL(b.csitemcount,0)))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno  and isnull(c.prjreporttype,'''') in (''15'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  49,* from ('+@targetsql+') a pivot (max(csitemaprice) for ddate in (' + @sqltitle + ')) b  ')  
                
 -- 50美容部客单数   
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billcount=count(distinct b.csbillid)   
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
  and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  50,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
   
 -- 51美容部客单价   
 set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/count(distinct b.csbillid))   
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
  and isnull(c.prjreporttype,'''') in (''08'',''12'',''19'',''20'',''21'',''10'',''17'',''18'',''09'',''11'',''13'',''22'',''23'',''15'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  51,* from ('+@targetsql+') a pivot (max(billprice) for ddate in (' + @sqltitle + ')) b  ')  
   
 -- 52 美容疗程客单数   
  set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billcount=count(distinct b.csbillid)   
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0 and isnull(cspaymode,'''')=''9''  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  52,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
 -- 53 美容疗程客单价   
  set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billprice=convert(numeric(20,1),SUM(ISNULL(b.csitemamt,0))/count(distinct b.csbillid))   
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0 and isnull(cspaymode,'''')=''9''  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  53,* from ('+@targetsql+') a pivot (max(billprice) for ddate in (' + @sqltitle + ')) b  ')  
       
  --31美发疗程客单数占比   
  set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billcount=convert(numeric(20,4),(count(distinct case when  isnull(cspaymode,'''')=''9'' then b.csbillid else '''' end )-1)*1.0/count(distinct b.csbillid))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''3'',''6'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  31,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
 -- 54 美容疗程客单占比   
 set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billcount=convert(numeric(20,4),(count(distinct case when  isnull(cspaymode,'''')=''9'' then b.csbillid else '''' end )-1)*1.0/count(distinct b.csbillid))  
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and b.csitemno=c.prjno and isnull(c.prjtype,'''') in (''4'')  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0   
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  54,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
    
    
   -- 112 总客单数  
   set @targetsql='select ddate=SUBSTRING(a.financedate,1,6),billcount=count(distinct b.csbillid)   
  from mconsumeinfo a,dconsumeinfo b,projectnameinfo c  
  where a.csbillid=b.csbillid and a.cscompid=b.cscompid and a.financedate between ''20130101'' and ''20131231''  
  and a.cscompid='+@compid+'  and  isnull(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0  
  group by  SUBSTRING(a.financedate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  112,* from ('+@targetsql+') a pivot (max(billcount) for ddate in (' + @sqltitle + ')) b  ')  
   
   
 -- 55 总流失人数 56美发部流人数 57烫染部流失人数 58 美容部流失人数 59 大堂接待收银流失人数 60 后勤流失人数  
 --计算离职指标  
  create table #leavelstaff      
  (      
   compid  varchar(10) null,      
   inid  varchar(20) null,      
   inserdate varchar(10) null,      
   leveldate varchar(10) null,      
   department  varchar(10) null,      
  )    
    
  insert #leavelstaff(compid,inid,leveldate,department)      
  select oldcompid,a.manageno,max(effectivedate),department     
    from staffhistory a,compchaininfo ,staffinfo b with(nolock)                    
    where changetype='3' and oldcompid=relationcomp and curcomp=@compid      
   and a.manageno=b.Manageno and ISNULL(curstate,'1')='3'        
   and effectivedate>='20130101'                                             
   and effectivedate<='20131231'                          
    group by oldcompid,a.manageno,department      
      
   update #leavelstaff set inserdate=isnull((select MIN(effectivedate) from staffhistory where manageno=inid and changetype='4' ),'')   
      update #leavelstaff set inserdate='20120101' where ISNULL(inserdate,'')=''     
      
   
       
     set @targetsql=' select ddate=substring(leveldate,1,6), leavelcorecount=count(distinct inid) from #leavelstaff group by substring(leveldate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  55,* from ('+@targetsql+') a pivot (max(leavelcorecount) for ddate in (' + @sqltitle + ')) b  ')  
    
  set @targetsql=' select ddate=substring(leveldate,1,6), leavelcorecount=count(distinct inid) from #leavelstaff  where   department=''004'' group by substring(leveldate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  56,* from ('+@targetsql+') a pivot (max(leavelcorecount) for ddate in (' + @sqltitle + ')) b  ')  
    
  set @targetsql=' select ddate=substring(leveldate,1,6), leavelcorecount=count(distinct inid) from #leavelstaff where   department=''006''  group by substring(leveldate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  57,* from ('+@targetsql+') a pivot (max(leavelcorecount) for ddate in (' + @sqltitle + ')) b  ')  
    
  set @targetsql=' select ddate=substring(leveldate,1,6), leavelcorecount=count(distinct inid) from #leavelstaff where   department=''003'' group by substring(leveldate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  58,* from ('+@targetsql+') a pivot (max(leavelcorecount) for ddate in (' + @sqltitle + ')) b  ')  
    
  set @targetsql=' select ddate=substring(leveldate,1,6), leavelcorecount=count(distinct inid) from #leavelstaff where   department=''007'' group by substring(leveldate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  59,* from ('+@targetsql+') a pivot (max(leavelcorecount) for ddate in (' + @sqltitle + ')) b  ')  
    
  set @targetsql=' select ddate=substring(leveldate,1,6), leavelcorecount=count(distinct inid) from #leavelstaff where   department=''008'' group by substring(leveldate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  60,* from ('+@targetsql+') a pivot (max(leavelcorecount) for ddate in (' + @sqltitle + ')) b  ')  
    
    
    
 -- 61 3个月内总流失人数 62 3个月内美发部流人数 63 3个月内烫染部流失人数 64 3个月内美容部流失人数 65 3个月内大堂接待收银流失人数 66 3个月内后勤流失人数  
   
  set @targetsql=' select ddate=substring(leveldate,1,6), leavelcorecount=count(distinct inid) from #leavelstaff  where DATEDIFF ( MONTH ,inserdate ,leveldate )<=3   group by substring(leveldate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  61,* from ('+@targetsql+') a pivot (max(leavelcorecount) for ddate in (' + @sqltitle + ')) b  ')  
    
  set @targetsql=' select ddate=substring(leveldate,1,6), leavelcorecount=count(distinct inid) from #leavelstaff  where DATEDIFF ( MONTH ,inserdate ,leveldate )<=3   and    department=''004'' group by substring(leveldate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  62,* from ('+@targetsql+') a pivot (max(leavelcorecount) for ddate in (' + @sqltitle + ')) b  ')  
    
  set @targetsql=' select ddate=substring(leveldate,1,6), leavelcorecount=count(distinct inid) from #leavelstaff where DATEDIFF ( MONTH ,inserdate ,leveldate )<=3  and  department=''006''  group by substring(leveldate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  63,* from ('+@targetsql+') a pivot (max(leavelcorecount) for ddate in (' + @sqltitle + ')) b  ')  
    
  set @targetsql=' select ddate=substring(leveldate,1,6), leavelcorecount=count(distinct inid) from #leavelstaff where DATEDIFF ( MONTH ,inserdate ,leveldate )<=3  and  department=''003'' group by substring(leveldate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  64,* from ('+@targetsql+') a pivot (max(leavelcorecount) for ddate in (' + @sqltitle + ')) b  ')  
    
  set @targetsql=' select ddate=substring(leveldate,1,6), leavelcorecount=count(distinct inid) from #leavelstaff where DATEDIFF ( MONTH ,inserdate ,leveldate )<=3  and  department=''007'' group by substring(leveldate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  65,* from ('+@targetsql+') a pivot (max(leavelcorecount) for ddate in (' + @sqltitle + ')) b  ')  
    
  set @targetsql=' select ddate=substring(leveldate,1,6), leavelcorecount=count(distinct inid) from #leavelstaff where DATEDIFF ( MONTH ,inserdate ,leveldate )<=3  and  department=''008'' group by substring(leveldate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  66,* from ('+@targetsql+') a pivot (max(leavelcorecount) for ddate in (' + @sqltitle + ')) b  ')  
    
    
    
 -- 67 6个月内总流失人数 68 6个月内美发部流人数 69 6个月内烫染部流失人数 70 6个月内美容部流失人数 71 6个月内大堂接待收银流失人数 62 6个月内后勤流失人数  
  set @targetsql=' select ddate=substring(leveldate,1,6), leavelcorecount=count(distinct inid) from #leavelstaff  where DATEDIFF ( MONTH ,inserdate ,leveldate )<=6   group by substring(leveldate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  67,* from ('+@targetsql+') a pivot (max(leavelcorecount) for ddate in (' + @sqltitle + ')) b  ')  
    
  set @targetsql=' select ddate=substring(leveldate,1,6), leavelcorecount=count(distinct inid) from #leavelstaff  where DATEDIFF ( MONTH ,inserdate ,leveldate )<=6  and    department=''004'' group by substring(leveldate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  68,* from ('+@targetsql+') a pivot (max(leavelcorecount) for ddate in (' + @sqltitle + ')) b  ')  
    
  set @targetsql=' select ddate=substring(leveldate,1,6), leavelcorecount=count(distinct inid) from #leavelstaff where DATEDIFF ( MONTH ,inserdate ,leveldate )<=6  and department=''006''  group by substring(leveldate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  69,* from ('+@targetsql+') a pivot (max(leavelcorecount) for ddate in (' + @sqltitle + ')) b  ')  
    
  set @targetsql=' select ddate=substring(leveldate,1,6), leavelcorecount=count(distinct inid) from #leavelstaff where DATEDIFF ( MONTH ,inserdate ,leveldate )<=6  and department=''003'' group by substring(leveldate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  70,* from ('+@targetsql+') a pivot (max(leavelcorecount) for ddate in (' + @sqltitle + ')) b  ')  
    
  set @targetsql=' select ddate=substring(leveldate,1,6), leavelcorecount=count(distinct inid) from #leavelstaff where DATEDIFF ( MONTH ,inserdate ,leveldate )<=6  and department=''007'' group by substring(leveldate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  71,* from ('+@targetsql+') a pivot (max(leavelcorecount) for ddate in (' + @sqltitle + ')) b  ')  
    
  set @targetsql=' select ddate=substring(leveldate,1,6), leavelcorecount=count(distinct inid) from #leavelstaff where DATEDIFF ( MONTH ,inserdate ,leveldate )<=6  and department=''008'' group by substring(leveldate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  72,* from ('+@targetsql+') a pivot (max(leavelcorecount) for ddate in (' + @sqltitle + ')) b  ')  
    
 -- 73 12个月内总流失人数 74 12个月内美发部流人数 75 12个月内烫染部流失人数 76 12个月内美容部流失人数 77 12个月内大堂接待收银流失人数 78 12个月内后勤流失人数                 
  set @targetsql=' select ddate=substring(leveldate,1,6), leavelcorecount=count(distinct inid) from #leavelstaff  where DATEDIFF ( MONTH ,inserdate ,leveldate )>12   group by substring(leveldate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  73,* from ('+@targetsql+') a pivot (max(leavelcorecount) for ddate in (' + @sqltitle + ')) b  ')  
    
  set @targetsql=' select ddate=substring(leveldate,1,6), leavelcorecount=count(distinct inid) from #leavelstaff  where DATEDIFF ( MONTH ,inserdate ,leveldate )>12  and    department=''004'' group by substring(leveldate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  74,* from ('+@targetsql+') a pivot (max(leavelcorecount) for ddate in (' + @sqltitle + ')) b  ')  
    
  set @targetsql=' select ddate=substring(leveldate,1,6), leavelcorecount=count(distinct inid) from #leavelstaff where DATEDIFF ( MONTH ,inserdate ,leveldate )>12 and  department=''006''  group by substring(leveldate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  75,* from ('+@targetsql+') a pivot (max(leavelcorecount) for ddate in (' + @sqltitle + ')) b  ')  
    
  set @targetsql=' select ddate=substring(leveldate,1,6), leavelcorecount=count(distinct inid) from #leavelstaff where DATEDIFF ( MONTH ,inserdate ,leveldate )>12 and  department=''003'' group by substring(leveldate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  76,* from ('+@targetsql+') a pivot (max(leavelcorecount) for ddate in (' + @sqltitle + ')) b  ')  
    
  set @targetsql=' select ddate=substring(leveldate,1,6), leavelcorecount=count(distinct inid) from #leavelstaff where DATEDIFF ( MONTH ,inserdate ,leveldate )>12 and department=''007'' group by substring(leveldate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  77,* from ('+@targetsql+') a pivot (max(leavelcorecount) for ddate in (' + @sqltitle + ')) b  ')  
    
  set @targetsql=' select ddate=substring(leveldate,1,6), leavelcorecount=count(distinct inid) from #leavelstaff where DATEDIFF ( MONTH ,inserdate ,leveldate )>12  and department=''008'' group by substring(leveldate,1,6)'  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  78,* from ('+@targetsql+') a pivot (max(leavelcorecount) for ddate in (' + @sqltitle + ')) b  ')  
        
    drop table #leavelstaff    
      
    create table #salarystaff      
 (      
     
   inid   varchar(20) null,      
   inserdate  varchar(10) null,    
   salary_date  varchar(10) null,   
   difdate   int   null,    
   department  varchar(10) null,   
   staffyeji  float  null,    
     
 )    
 insert  #salarystaff(inid,staffyeji,salary_date,department)  
    select person_inid,staffyeji=SUM(staffyeji),salary_date=SUBSTRING(salary_date,1,6),department     
    from staff_work_salary,staffinfo b with(nolock)     
    where compid=@compid and salary_date between '20130101' and '20131231'  
    and person_inid=manageno  
    group by person_inid,SUBSTRING(salary_date,1,6),department  
    update #salarystaff set inserdate=isnull((select MIN(effectivedate) from staffhistory where manageno=inid and changetype='4' ),'')   
    update #salarystaff set inserdate='20120101' where ISNULL(inserdate,'')=''  
      
    update #salarystaff set difdate=DATEDIFF (MONTH ,inserdate ,salary_date+'28')  
       
      -- 79 3-6个月低于3000 的员工 80 美发部3-6个月低于3000 的员工 81 烫染部3-6个月低于3000 的员工 82 美容部3-6个月低于3000 的员工  
     
     set @targetsql=' select ddate=salary_date,salarycount=COUNT(inid) from #salarystaff where staffyeji<3000 and ISNULL(staffyeji,0)>0 and difdate>=3 and difdate<=6 group by  salary_date '  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  79,* from ('+@targetsql+') a pivot (max(salarycount) for ddate in (' + @sqltitle + ')) b  ')  
    
  set @targetsql=' select ddate=salary_date,salarycount=COUNT(inid) from #salarystaff where staffyeji<3000 and ISNULL(staffyeji,0)>0 and department=''004'' and difdate>=3 and difdate<=6 group by  salary_date '  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  80,* from ('+@targetsql+') a pivot (max(salarycount) for ddate in (' + @sqltitle + ')) b  ')  
    
     set @targetsql=' select ddate=salary_date,salarycount=COUNT(inid) from #salarystaff where staffyeji<3000 and ISNULL(staffyeji,0)>0 and department=''006'' and difdate>=3 and difdate<=6 group by  salary_date '  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  81,* from ('+@targetsql+') a pivot (max(salarycount) for ddate in (' + @sqltitle + ')) b  ')  
    
      set @targetsql=' select ddate=salary_date,salarycount=COUNT(inid) from #salarystaff where staffyeji<3000 and ISNULL(staffyeji,0)>0 and department=''003'' and difdate>=3 and difdate<=6 group by  salary_date '  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  82,* from ('+@targetsql+') a pivot (max(salarycount) for ddate in (' + @sqltitle + ')) b  ')  
    
  -- 83 6-12个月低于5000 的员工 84 美发部6-12个月低于5000 的员工 85 烫染部6-12个月低于5000 的员工 86 美容部6-12个月低于5000 的员工  
    
    set @targetsql=' select ddate=salary_date,salarycount=COUNT(inid) from #salarystaff where staffyeji<5000 and ISNULL(staffyeji,0)>0 and difdate>6 and difdate<=12 group by  salary_date '  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  83,* from ('+@targetsql+') a pivot (max(salarycount) for ddate in (' + @sqltitle + ')) b  ')  
    
  set @targetsql=' select ddate=salary_date,salarycount=COUNT(inid) from #salarystaff where staffyeji<5000 and ISNULL(staffyeji,0)>0 and department=''004'' and difdate>6 and difdate<=12 group by  salary_date '  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  84,* from ('+@targetsql+') a pivot (max(salarycount) for ddate in (' + @sqltitle + ')) b  ')  
    
     set @targetsql=' select ddate=salary_date,salarycount=COUNT(inid) from #salarystaff where staffyeji<5000 and ISNULL(staffyeji,0)>0 and department=''006'' and difdate>6 and difdate<=12 group by  salary_date '  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  85,* from ('+@targetsql+') a pivot (max(salarycount) for ddate in (' + @sqltitle + ')) b  ')  
    
      set @targetsql=' select ddate=salary_date,salarycount=COUNT(inid) from #salarystaff where staffyeji<5000 and ISNULL(staffyeji,0)>0 and department=''003'' and difdate>6 and difdate<=12 group by  salary_date '  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  86,* from ('+@targetsql+') a pivot (max(salarycount) for ddate in (' + @sqltitle + ')) b  ')  
  -- 87 12个月上低于7000 的员工 88 美发部12个月上低于7000 的员工 89 烫染部12个月上低于7000 的员工 89 美容部12个月上低于7000 的员工  
    set @targetsql=' select ddate=salary_date,salarycount=COUNT(inid) from #salarystaff where staffyeji<7000 and ISNULL(staffyeji,0)>0 and difdate>12 group by  salary_date '  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  87,* from ('+@targetsql+') a pivot (max(salarycount) for ddate in (' + @sqltitle + ')) b  ')  
    
  set @targetsql=' select ddate=salary_date,salarycount=COUNT(inid) from #salarystaff where staffyeji<7000 and ISNULL(staffyeji,0)>0 and department=''004'' and difdate>12  group by  salary_date '  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  88,* from ('+@targetsql+') a pivot (max(salarycount) for ddate in (' + @sqltitle + ')) b  ')  
    
     set @targetsql=' select ddate=salary_date,salarycount=COUNT(inid) from #salarystaff where staffyeji<7000 and ISNULL(staffyeji,0)>0 and department=''006'' and difdate>12 group by  salary_date '  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  89,* from ('+@targetsql+') a pivot (max(salarycount) for ddate in (' + @sqltitle + ')) b  ')  
    
      set @targetsql=' select ddate=salary_date,salarycount=COUNT(inid) from #salarystaff where staffyeji<7000 and ISNULL(staffyeji,0)>0 and department=''003'' and difdate>12 group by  salary_date '  
  insert #analysisresult(resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r)  
  exec (' select  111,* from ('+@targetsql+') a pivot (max(salarycount) for ddate in (' + @sqltitle + ')) b  ')  
   
    drop table #salarystaff  
      
      
                 
 update #analysisresult set months_12r=convert(numeric(20,4),ISNULL(month1r,0)+ISNULL(month2r,0)+ISNULL(month3r,0)+ISNULL(month4r,0)+ISNULL(month5r,0)+ISNULL(month6r,0)  
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
  
  --montha_5r    float  null, --后5名平均  
    delete analysisresult where compno= @compid  
    insert analysisresult(compno,resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r,months_12r,monthf_5r,montha_12r,montha_5r)  
    select @compid,resusttyep,month1r,month2r,month3r,month4r,month5r,month6r,month7r,month8r,month9r,month10r,month11r,month12r,months_12r,monthf_5r,montha_12r,montha_5r  
  from #analysisresult order by resusttyep  
   
 drop table #analysisresult  
end  
go








