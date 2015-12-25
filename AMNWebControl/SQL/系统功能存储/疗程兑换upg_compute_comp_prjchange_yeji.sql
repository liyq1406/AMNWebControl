if exists(select 1 from sysobjects where type='P' and name='upg_compute_comp_prjchange_yeji')
	drop procedure upg_compute_comp_prjchange_yeji
go
CREATE procedure upg_compute_comp_prjchange_yeji                      
(                                
 @compid varchar(10) ,                                
 @datefrom varchar(8),                                
 @dateto varchar(8)                              
)                                
as                                
begin                                                      
    
	 create table #prjchange_yeji_resultx(                                
		compid			varchar(10)		null,	--门店编号         
		compname		varchar(50)		null,	--门店名称 
		projecttype		varchar(10)		null,	--类型编号    
		projectamt		float			null,	--类型金额              
	 )               
 
	insert #prjchange_yeji_resultx(compid,projecttype,projectamt )      
	select a.changecompid,prjreporttype+'Amt',sum(isnull(changebyaccountamt,0)+ISNULL(changebycashamt,0))
	from mproexchangeinfo a ,dproexchangeinfo b,commoninfo,compchaininfo,projectnameinfo
	where infotype='XMTJ' and prjno=b.changeproid and prjreporttype=parentcodekey
	and a.changecompid=b.changecompid and a.changebillid=b.changebillid
	and a.changecompid =relationcomp and curcomp=@compid and changedate between @datefrom and @dateto    
	group by a.changecompid,prjreporttype       
        
    update a set compname= b.compname  from  #prjchange_yeji_resultx a,companyinfo b  where a.compid=b.compno
    
	declare @sqltitle varchar(600)
	select @sqltitle = isnull(@sqltitle + '],[' , '') + parentcodekey+'Amt' from commoninfo where infotype='XMTJ' 
	set @sqltitle = '[' + @sqltitle + ']'

	exec ('select * from (select * from #prjchange_yeji_resultx ) a pivot (max(projectamt) for projecttype in (' + @sqltitle + ')) b order by compid')
	         
	drop table #prjchange_yeji_resultx                           
end     
go
--exec upg_compute_comp_prjchange_yeji '001','20130801','20130831'