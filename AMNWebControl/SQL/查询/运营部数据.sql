declare @sqltitle varchar(600)
set @sqltitle = '[02],[03],[04],[05],[06],[07],[14]'
declare @sql varchar(1000)
set @sql='select compno,compname,prjreporttype,sumamt from compchainstruct ,companyinfo d with(nolock) left join ( select a.cscompid,prjreporttype,sumamt=sum(isnull(csitemamt,0)) 
from dconsumeinfo b with(nolock),projectnameinfo c with(nolock), mconsumeinfo a with(nolock)  
where a.cscompid=b.cscompid and a.csbillid=b.csbillid 
and b.csitemno=c.prjno and c.prjreporttype in (''02'',''03'',''04'',''05'',''06'',''07'',''14'')
and ISNULL(backcsbillid,'''')='''' and ISNULL(backcsflag,0)=0 and a.cscompid<>''001''
 and financedate between ''20130501'' and ''20130531'' 
group by a.cscompid,prjreporttype) as cck on cck.cscompid=d.compno where curcompno=d.compno and complevel=4 '

declare @targsql varchar(4000)
set @targsql=' select * from ( ' +@sql+' ) c pivot (max(sumamt) for prjreporttype in (' + @sqltitle + ')) d order by  compno'


exec (@targsql)