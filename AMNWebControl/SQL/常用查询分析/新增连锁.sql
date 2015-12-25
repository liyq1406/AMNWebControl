
--insert companyinfo(compno,compname)
--values('0010104','°¢ÂêÄá.ÐÂÕ½ÂÔ')
--insert compchainstruct(curcompno,parentcompno,complevel,CREATEdate)
--values('0010104','001','2','20140428')

--update compchainstruct set complevel=3,parentcompno='00101' where curcompno='0010104'

--update compchainstruct  set complevel='4',parentcompno='0010104' where  curcompno in ('033','014','041','046')

--insert compchaininfo(curcomp,relationcomp)
--values('001','0010104')

--insert compchaininfo(curcomp,relationcomp)
--values('00101','0010104')

--insert compchaininfo(curcomp,relationcomp)
--values('0010104','0010104')

--update compchaininfo set curcomp='0010104'  where relationcomp  in ('033','014','041','046') and curcomp='0010101'


--update syscommoninfomode set modesource='0010104',parentmodeid='SPM0010104' where modeid='SPM0010104'

--insert projectinfo(prjmodeId,prjno,prjname,prjtype,prjpricetype,prjreporttype,saleunit,saleprice,msalecount,msaleprice,rsalecount,rsaleprice
--,hsalecount,hsaleprice,ysalecount,ysaleprice,onecountprice,onepageprice,salelowprice,needhairflag,useflag,saleflag,rateflag,
--prjsaletype,editflag,pointtype,pointvalue,costtype,costprice,kyjrate,ktcrate,lyjrate,ltcrate,prisource,finaltype)
--select 'SPM0010104',prjno,prjname,prjtype,prjpricetype,prjreporttype,saleunit,saleprice,msalecount,msaleprice,rsalecount,rsaleprice
--,hsalecount,hsaleprice,ysalecount,ysaleprice,onecountprice,onepageprice,salelowprice,needhairflag,useflag,saleflag,rateflag,
--prjsaletype,editflag,pointtype,pointvalue,costtype,costprice,kyjrate,ktcrate,lyjrate,ltcrate,'0010104',finaltype
--from projectinfo where prjmodeId='SPM00101'

--insert projectinfo( prjmodeid,prjno,prjname,prjtype,prjreporttype,prjpricetype,saleunit,saleprice,ysalecount,ysaleprice,newcosttc,oldcosttc,saleflag,useflag,rateflag,prjsaletype,prisource)
--select prjmodeid,prjno,prjname,prjtype,prjreporttype,prjpricetype,saleunit,saleprice,ysalecount,ysaleprice,newcosttc,oldcosttc,saleflag,useflag,rateflag,prjsaletype,prisource 
--from [A320140131].dbo.LCPRJ20140430

--insert projectinfo( prjmodeid,prjno,prjname,prjtype,prjreporttype,prjpricetype,saleunit,saleprice,onepageprice,memberprice,newcosttc,oldcosttc,saleflag,useflag,rateflag,prjsaletype,prisource )
--select  prjmodeid,prjno,prjname,prjtype,prjreporttype,prjpricetype,saleunit,saleprice,onepageprice,memberprice,newcosttc,oldcosttc,saleflag,useflag,rateflag,prjsaletype,prisource 
-- from [A320140131].dbo.DCPRJ20140430
 

--update projectinfo set saleflag=2 where prjmodeId='SPM0010104' and prjtype=4 and ISNULL(prjsaletype,0)=1
--update projectinfo set useflag=2 where prjmodeId='SPM0010104' and prjtype=4 and ISNULL(prjsaletype,0)<>1







--insert projectinfo( prjmodeid,prjno,prjname,prjtype,prjreporttype,prjpricetype,saleunit,saleprice,ysalecount,ysaleprice,newcosttc,oldcosttc,saleflag,useflag,rateflag,prjsaletype,prisource)
--select 'SPM0010102',prjno,prjname,prjtype,prjreporttype,prjpricetype,saleunit,saleprice,ysalecount,ysaleprice,newcosttc,oldcosttc,saleflag,useflag,rateflag,prjsaletype,'0010102' 
--from [A320140131].dbo.LCPRJ20140430

--insert projectinfo( prjmodeid,prjno,prjname,prjtype,prjreporttype,prjpricetype,saleunit,saleprice,onepageprice,memberprice,newcosttc,oldcosttc,saleflag,useflag,rateflag,prjsaletype,prisource )
--select  'SPM0010102',prjno,prjname,prjtype,prjreporttype,prjpricetype,saleunit,saleprice,onepageprice,memberprice,newcosttc,oldcosttc,saleflag,useflag,rateflag,prjsaletype,'0010102' 
-- from [A320140131].dbo.DCPRJ20140430


update projectinfo set saleflag=2 where prjmodeId='SPM0010102' and prjtype=4 and ISNULL(prjsaletype,0)=1
update projectinfo set useflag=2 where prjmodeId='SPM0010102' and prjtype=4 and ISNULL(prjsaletype,0)<>1

update  projectinfo set saleflag=1,useflag=1  where  prjmodeId='SPM0010102'  and prjno like '498000%'

update projectinfo set useflag=2 where prjmodeId='SPM0010104' and prjno in ('300','3002')

update sysparaminfo set paramvalue='SPM0010104' where compid  in ('033','014','041','046') and paramid='SP059'
update sysparaminfo set paramvalue='1' where compid  in ('006','047','033','014','041','046') and paramid='SP097'
update sysparaminfo set paramvalue='1' where compid  in ('006','047','033','014','041','046') and paramid='SP098'


-- CC011 CC012 CC008 CC009 BC008 BC013 Common->liger.js OrgManager.java  model

select * from projectinfo a where a.prjmodeId='SPM0010104' 

