declare @sqltitle varchar(600)
set @sqltitle = '[1],[2],[3],[4],[5],[6],[7],[8],[9],[10],[11],[12],[13],[14],[15],[16],[17],[18],[19],[20]'
declare @sql varchar(800)	
	
set @sql='(select gaz01c,gae03c,gda03c=gda03c+''@@''+cast (convert(numeric(20,1),isnull(ggb11f,0)) as varchar(10)),fid
from (select gaz01c,gae03c,ggb03c,gda03c,ggb11f=SUM(isnull(ggb11f,0)),
row_number() over( PARTITION BY gaz01c,gae03c order by SUM(ISNULL(ggb11f,0)) desc) fid
 from ggm02,gdm01,gam26,gam05 where SUBSTRING(ggb01c,4,6) between ''201301'' and ''201312'' and gda04c in (''3'',''6'')
and gda30i = ''1'' and gda00c= ggb00c and gda01c = ggb03c and gda01c not in (''399001'',''499001'',''499002'') and gaz02c=ggb00c and gae01c=gaz01c
group by gaz01c,gae03c,ggb03c,gda03c) a
where fid<=50)  '

declare @targsql varchar(3200)
set @targsql='select gaz01c,gae03c'
	declare @i int 
	set @i=1
	while(@i<=20) 
	begin 
		set @targsql=@targsql+',Ãû³Æ=substring(['+cast( @i as varchar(2))+'],1, charindex(''@@'',['+cast( @i as varchar(2))+'])-1),'
		set @targsql=@targsql+'½ð¶î=substring(['+cast( @i as varchar(2))+'],charindex(''@@'',['+cast( @i as varchar(2))+'])+2, len(['+cast( @i as varchar(2))+'])-charindex(''@@'',['+cast( @i as varchar(2))+'])-1)'
		set @i=@i+1 
	end
set @targsql=@targsql+' from ( ' +@sql+' ) c pivot (max(gda03c) for fid in (' + @sqltitle + ')) d order by  gaz01c'
print @targsql
exec (@targsql)

