--2013年美发卖品
declare @sqltitle varchar(600)
set @sqltitle = '[201301],[201302],[201303],[201304],[201305],[201306],[201307],[201308],[201309],[201310],[201311],[201312]'
declare @sql varchar(1000)
set @sql='select b.cscompid,compname,csitemamt=isnull(sum(b.csitemamt),0),financedate=SUBSTRING(financedate,1,6) from mconsumeinfo a,dconsumeinfo b,companyinfo
where a.cscompid=b.cscompid and a.csbillid=b.csbillid and isnull(b.csinfotype,0)=2
and a.financedate between  ''20140101'' and ''20141231'' and b.csitemno like ''3%'' and compno=b.cscompid 
group by  b.cscompid,compname,SUBSTRING(financedate,1,6) '
declare @targsql varchar(4000)
set @targsql=' select * from ( ' +@sql+' ) c pivot (max(csitemamt) for financedate in (' + @sqltitle + ')) d order by  cscompid'
exec (@targsql)
go
--2013年美容卖品
declare @sqltitle varchar(600)
set @sqltitle = '[201301],[201302],[201303],[201304],[201305],[201306],[201307],[201308],[201309],[201310],[201311],[201312]'
declare @sql varchar(1000)
set @sql='select b.cscompid,compname,csitemamt=isnull(sum(b.csitemamt),0),financedate=SUBSTRING(financedate,1,6) from mconsumeinfo a,dconsumeinfo b,companyinfo
where a.cscompid=b.cscompid and a.csbillid=b.csbillid and isnull(b.csinfotype,0)=2
and a.financedate between  ''20140101'' and ''20141231'' and b.csitemno like ''4%'' and compno=b.cscompid 
group by  b.cscompid,compname,SUBSTRING(financedate,1,6) '
declare @targsql varchar(4000)
set @targsql=' select * from ( ' +@sql+' ) c pivot (max(csitemamt) for financedate in (' + @sqltitle + ')) d order by  cscompid'
exec (@targsql)

go
--2013年美发卖品
declare @sqltitle varchar(600)
set @sqltitle = '[201301],[201302],[201303],[201304],[201305],[201306],[201307],[201308],[201309],[201310],[201311],[201312]'
declare @sql varchar(1000)
set @sql='select b.cscompid,compname,csitemamt=isnull(sum(isnull(b.csitemcount,0)*ISNULL(costamtbysale,0)),0),financedate=SUBSTRING(financedate,1,6) from mconsumeinfo a,dconsumeinfo b,companyinfo,goodsinfo
where a.cscompid=b.cscompid and a.csbillid=b.csbillid and isnull(b.csinfotype,0)=2
and a.financedate between  ''20140101'' and ''20141231'' and b.csitemno like ''3%'' and compno=b.cscompid  and goodsno=b.csitemno  
group by  b.cscompid,compname,SUBSTRING(financedate,1,6) '
declare @targsql varchar(4000)
set @targsql=' select * from ( ' +@sql+' ) c pivot (max(csitemamt) for financedate in (' + @sqltitle + ')) d order by  cscompid'
exec (@targsql)
go
--2013年美容卖品
declare @sqltitle varchar(600)
set @sqltitle = '[201301],[201302],[201303],[201304],[201305],[201306],[201307],[201308],[201309],[201310],[201311],[201312]'
declare @sql varchar(1000)
set @sql='select b.cscompid,compname,csitemamt=isnull(sum(isnull(b.csitemcount,0)*ISNULL(costamtbysale,0)),0),financedate=SUBSTRING(financedate,1,6) from mconsumeinfo a,dconsumeinfo b,companyinfo,goodsinfo
where a.cscompid=b.cscompid and a.csbillid=b.csbillid and isnull(b.csinfotype,0)=2
and a.financedate between  ''20140101'' and ''20141231'' and b.csitemno like ''4%'' and compno=b.cscompid  and goodsno=b.csitemno  
group by  b.cscompid,compname,SUBSTRING(financedate,1,6) '
declare @targsql varchar(4000)
set @targsql=' select * from ( ' +@sql+' ) c pivot (max(csitemamt) for financedate in (' + @sqltitle + ')) d order by  cscompid'
exec (@targsql)