

delete staffchangeinfo
insert staffchangeinfo(changecompid,changebillid,changetype,changestaffno,appchangecompid,staffpcid,staffphone,staffmangerno,
changedate,validatestartdate,validateenddate,beforedepartment,beforepostation,beforesalary,beforesalarytype,beforeyejitype,beforeyejirate,
aftercompid,afterstaffno,afterdepartment,afterpostation,aftersalary,aftersalarytype,afteryejitype,afteryejirate,leaveltype,
checkcompid,checkstaffid,checkdate,checkflag,checkinheadcompid,checkinheadstaffid,checkinheaddate,checkinheadflag,
comfirmcompid,comfirmstaffid,comfirmdate,comfirmflag,billflag,remark)

select fhb00c,fhb01c,fhb02i,fhb03c,fhb04c,fhb14c,fhb17c,fhb18c,fhb05c,fhb06c,fhb07c,fhb23c,fhb09c,fhb15f,fhb25i,fhb19c,fhb20f,
fhb08c,fhb15c,fhb24c,fhb12c,fhb16f,fhb26i,fhb21c,fhb22f,fhb27i,
fhb60c,fhb61c,fhb62c,fhb63i,fhb64c,fhb65c,fhb66c,fhb67i,
 fhb68c,fhb69c,fhb70c,fhb71i,fhb72i,fhb10c from [20131008].dbo.fah02
 
 insert staffchangeinfo(changecompid,changebillid,changetype,changestaffno,appchangecompid,staffpcid,staffphone,staffmangerno,
changedate,validatestartdate,validateenddate,beforedepartment,beforepostation,beforesalary,beforesalarytype,beforeyejitype,beforeyejirate,
aftercompid,afterstaffno,afterdepartment,afterpostation,aftersalary,aftersalarytype,afteryejitype,afteryejirate,
checkcompid,checkstaffid,checkdate,checkflag,checkinheadcompid,checkinheadstaffid,checkinheaddate,checkinheadflag,
comfirmcompid,comfirmstaffid,comfirmdate,comfirmflag,billflag,remark)
select fha00c,fha01c,fha02i+5,fha03c,fha04c,fha11c,fha15c,fha16c,
fha08c,fha13c,fha13c,fha17c,fha06c,fha09f,fha23i,fha19c,fha20f,
fha05c,fha12c,fha18c,fha07c,fha10f,fha24i,fha21c,fha22f,
fha60c,fha61c,fha62c,fha63i,fha64c,fha65c,fha66c,fha67i,fha68c,fha69c,fha70c,fha70i,fha71i,fha14c from [20131008].dbo.fah01

update staffchangeinfo set  afterpostation=fhb12c
from staffchangeinfo,[20131008].dbo.fah02
where changecompid=fhb00c and changebillid=fhb01c and changetype=fhb02i and fhb02i=1



delete cardinfo
insert cardinfo(cardvesting,cardno,cardtype,membernotocard,salecarddate,cutoffdate,cardstate,salebillno,costpassword,searchpassword,cardremark,cardsource,costcountbydebts,costamtbydebts,costamt)
select gca00c,gca01c,gca02c,gca04c,gca05d,gca07d,gca08i,gca11c,gca12c,gca18c,gca27c,gca32i,gca33i,gca34f,0 
from [10.0.0.9].S3GOS2016.dbo.gcm01 where gca00c=gca13d






delete cardaccount
insert cardaccount(cardvesting,cardno,accounttype,accountbalance,accountdebts,accountdatefrom,accountdateend,accountremark)
select gcc00c,gcc01c,gcc03i,gcc06f,gcc10f,gcc08c,gcc09d,gcc11c 
from [10.0.0.9].S3GOS2016.dbo.gcm01,[10.0.0.9].S3GOS2016.dbo.gcm03
 where gcc00c=gca00c and gcc01c=gca01c and gca00c=gca13d
 
delete memberinfo
insert memberinfo(membervesting,memberno,memberCREATEdate,membername,memberaddress,membertphone,membermphone,memberFax,memberemail,memberzip,membersex,memberpaperworkno,memberbirthday,cardnotomemberno,memberqqno,membertype)
select gba00c,gba01c,gba02c,gba03c,gba05c,gba07c,gba08c,gba09c,gba11c,gba12c,gba14i,gba16c,gba17d,gba23c,gba32c,gba41c 
from [10.0.0.9].S3GOS2016.dbo.gbm01

delete d from memberinfo d,cardinfo a
where d.memberno=a.cardno and d.membervesting<>a.cardvesting
and d.memberno in (select b.cardno from memberinfo c, cardinfo b where c.membervesting=b.cardvesting and  c.memberno=b.cardno )


delete cardchangehistory
insert cardchangehistory(changecompid,changecardno,changeseqno,changetype,changebillid,beforestate,afterstate,chagedate,targetcardno)
select gcb00c,gcb01c,gcb02f,gcb03i,gcb04c,gcb05i,gcb06i,gcb07d,gcb08c 
from [10.0.0.9].S3GOS2016.dbo.gcm02

delete cardaccountchangehistory
insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)
select gcd00c,gcd01c,gcd02i,gcd04f,gcd05i,gcd06f,gcd07c,gcd08c,gcd09d,gcd10f  
from [10.0.0.9].S3GOS2016.dbo.gcm04 where gcd01c not in (
select gcd01c from [10.0.0.9].S3GOS2016.dbo.gcm04
  group by gcd00c,gcd01c,gcd02i,gcd04f having coUNt(gcd01c)>1)
  order by gcd04f asc
  
delete cardproaccount
insert cardproaccount(cardvesting,cardno,projectno,proseqno,propricetype,salecount,costcount,lastcount,saleamt,costamt,lastamt,saledate,cutoffdate,proremark,prostopeflag,exchangeseqno)
select gcf00c, gcf01c,gcf02c,gcf23i,gcf17i,gcf05f,gcf06f,gcf07f,gcf08f,gcf09f,gcf10f,gcf11d,gcf12d,gcf15c,gcf19i,gcf25f 
from [10.0.0.9].S3GOS2016.dbo.gcm06,[10.0.0.9].S3GOS2016.dbo.gcm01
 where gcf00c=gca00c and gcf01c=gca01c and gca00c=gca13d

delete cardtransactionhistory
insert cardtransactionhistory(transactioncompid,transactioncardno,transactiondate,transactiontype,codeno,codename,ccount,price,billtype,billno,firstempid,secondempid,thirthempid)
select gct00c,gct02c,gct04d,gct06c,gct07c,gct08c,gct11f,gct12f,gct13c,gct14c,gct15c,gct16c,gct17c 
from [10.0.0.9].S3GOS2016.dbo.gcm20

delete oldcardinfo
insert oldcardinfo(cardvesting,cardno,cardtype,membernotocard,salecarddate,cutoffdate,cardstate,salebillno,costpassword,
searchpassword,cardremark,cardsource,costcountbydebts,costamtbydebts,costamt)
select cardvesting,cardno,cardtype,membernotocard,salecarddate,cutoffdate,cardstate,salebillno,costpassword,
searchpassword,cardremark,cardsource,costcountbydebts,costamtbydebts,costamt from cardinfo where cardstate<>1

delete cardinfo where cardstate<>1

insert cardinfo(cardvesting,cardno,cardtype,membernotocard,salecarddate,cutoffdate,cardstate,salebillno,costpassword,
searchpassword,cardremark,cardsource,costcountbydebts,costamtbydebts,costamt)
select cardvesting,cardno,cardtype,membernotocard,salecarddate,cutoffdate,cardstate,salebillno,costpassword,
searchpassword,cardremark,cardsource,costcountbydebts,costamtbydebts,costamt 
from oldcardinfo where cardno in (
select cscardno from mconsumeinfo where financedate between '20130601' and '20131231' and cscardno<>'散客' group by cscardno
)

delete oldcardinfo  where cardno in (
select cscardno from mconsumeinfo where financedate between '20130601' and '20131231' and cscardno<>'散客' group by cscardno
)



insert syscommoninfomode(modeid,modetype,modename,modesource,parentmodeid)
values('SPM',1,'标准模板','001','')
insert syscommoninfomode(modeid,modetype,modename,modesource,parentmodeid)
values('SGM',2,'标准模板','001','')
insert syscommoninfomode(modeid,modetype,modename,modesource,parentmodeid)
values('SCM',3,'标准模板','001','')


--select * into staffinfobak20131230 from staffinfo
delete staffinfo where compno  in ('006','008','045','019','030','029')
insert staffinfo(compno,staffno,staffname,staffsex,department,arrivaldate,pccid,aaddress,mobilephone,position,curstate,manageno,businessflag,banktype,bankno)
select haa00c,haa01c,haa02c,haa05i,haa06c,haa08d,haa12c,haa18c,haa20c,haa25c,haa27c,haa34c,haa46i,haa47c,haa48c
 from [10.0.0.9].S3GOS2016.dbo.ham01 where haa00c  not in ('006','008','045','019','030','029')

insert staffinfo(compno,staffno,staffname,staffsex,department,arrivaldate,pccid,aaddress,mobilephone,position,curstate,manageno,businessflag,banktype,bankno)
select haa00c,haa01c,haa02c,haa05i,haa06c,haa08d,haa12c,haa18c,haa20c,haa25c,haa27c,haa34c,haa46i,haa47c,haa48c
 from [10.0.0.9].S3GOS2016.dbo.ham01 where haa34c not in (select manageno from staffinfo)
 
insert staffinfo(compno,staffno,staffname,staffsex,department,arrivaldate,pccid,aaddress,mobilephone,position,curstate,manageno,businessflag,banktype,bankno)
select compno,staffno,staffname,staffsex,department,arrivaldate,pccid,aaddress,mobilephone,position,curstate,manageno,businessflag,banktype,bankno
 from [10.0.0.10].TestAMNDATA.dbo.staffinfo where compno in ('006','008','045','019','030','029')
 
 
update b set fingerno=staffnoseqno 
from staffinfo b,(select staffno,staffnoseqno=row_number() over(order by a.staffno asc)+21000 from staffinfo a ) c
where b.staffno=c.staffno and compno not in ('006','008','045','019','030','029')
	
	select max(fingerno) from staffinfo


update staffinfo set salaryflag=haa51i,basesalary=haa44f,resulttye=haa49c,resultrate=haa50f,baseresult=haa50f,socialsecurity=haa29f from staffinfo,[10.0.0.9].S3GOS2016.dbo.ham01
where manageno=haa34c 

update staffinfo set resultrate=0 where position  in ('008','00801')
update staffinfo set baseresult=0 where position  not in ('008','00801')

delete staffhistory
insert staffhistory(manageno,changetype,oldcompid,oldempid,olddepid,oldpostion,oldsalary,oldyjtype,oldyjrate,
newcompid,newempid,newdepid,newpostion,newsalary,newyjtype,newyjrate,effectivedate,optionbill)
select hal01c,hal02c,hal03c,hal04c,hal22c,hal05c,hal06f,hal07c,hal08f,hal10c,hal11c,hal23c,hal12c,hal13f,hal14c,hal15f,hal19c,hal21c 
from [10.0.0.9].S3GOS2016.dbo.ham12 order by hal02i asc 


delete companyinfo
insert companyinfo(compno,compname,compphone,compaddress)
select gae01c,gae03c,gae06c,gae08c from [10.0.0.9].S3GOS2016.dbo.gam05

delete companyinfo
insert companyinfo(compno,compname,compphone,compaddress)
select gae01c,gae03c,gae06c,gae08c from [10.0.0.9].S3GOS2016.dbo.gam05

delete compwarehouse
insert compwarehouse(compno,warehouseno,warehousename,warehousecontact,warehousephone,warehousefax,warehouseaddress)
select gad00c,gad01c,gad02c,gad03c,gad04c,gad05c,gad06c from [10.0.0.9].S3GOS2016.dbo.gam04


delete compchainstruct
insert compchainstruct(curcompno,parentcompno,complevel,CREATEdate ) 
select bran_id,parent_id,lvl,mmonth from [10.0.0.9].S3GOS2016.dbo.b_perf

delete compchaininfo
insert compchaininfo(curcomp,relationcomp)
select gaz01c,gaz02c from [10.0.0.9].S3GOS2016.dbo.gam26 

DELEtE projectinfo
insert projectinfo(prjmodeId,prjno,prjname,prjtype,prjpricetype,prjreporttype,saleunit,saleprice,msalecount,msaleprice,rsalecount,rsaleprice
,hsalecount,hsaleprice,ysalecount,ysaleprice,onecountprice,onepageprice,salelowprice,needhairflag,useflag,saleflag,rateflag,
prjsaletype,editflag,pointtype,pointvalue,costtype,costprice,kyjrate,ktcrate,lyjrate,ltcrate,prisource,finaltype)
select 'SPM',gda01c,gda03c,gda04c,gda30i,gda13c,gda09c,gda10f,gda22f,gda23f,gda24f,gda25f,gda26f,gda27f,gda28f,gda29f,gda11f,gda12f,gda41f,
gda42i,gda14i,gda44i,gda43i,gda40i,gda39i,gda17i,gda18f,gda19i,gda34f,gda35f,gda36f,gda37f,gda38f,'',1
from [10.0.0.9].S3GOS2016.dbo.gdm01 where gda00c='001'


insert projectinfo(prjmodeId,prjno,prjname,prjtype,prjpricetype,prjreporttype,saleunit,saleprice,msalecount,msaleprice,rsalecount,rsaleprice
,hsalecount,hsaleprice,ysalecount,ysaleprice,onecountprice,onepageprice,salelowprice,needhairflag,useflag,saleflag,rateflag,
prjsaletype,editflag,pointtype,pointvalue,costtype,costprice,kyjrate,ktcrate,lyjrate,ltcrate,prisource,finaltype)
select 'SPM'+gda00c,gda01c,gda03c,gda04c,gda30i,gda13c,gda09c,gda10f,gda22f,gda23f,gda24f,gda25f,gda26f,gda27f,gda28f,gda29f,gda11f,gda12f,gda41f,
gda42i,gda14i,gda44i,gda43i,gda40i,gda39i,gda17i,gda18f,gda19i,gda34f,gda35f,gda36f,gda37f,gda38f,gda00c,1
from [10.0.0.9].S3GOS2016.dbo.gdm01,[10.0.0.9].S3GOS2016.dbo.b_perf where gda00c=bran_id and lvl=2

delete projectinfo where  prjmodeId='SPM00105'
insert projectinfo(prjmodeId,prjno,prjname,prjtype,prjpricetype,prjreporttype,saleunit,saleprice,msalecount,msaleprice,rsalecount,rsaleprice
,hsalecount,hsaleprice,ysalecount,ysaleprice,onecountprice,onepageprice,salelowprice,needhairflag,useflag,saleflag,rateflag,
prjsaletype,editflag,pointtype,pointvalue,costtype,costprice,kyjrate,ktcrate,lyjrate,ltcrate,prisource,finaltype)
select 'SPM00105',gda01c,gda03c,gda04c,gda30i,gda13c,gda09c,gda10f,gda22f,gda23f,gda24f,gda25f,gda26f,gda27f,gda28f,gda29f,gda11f,gda12f,gda41f,
gda42i,gda14i,gda44i,gda43i,gda40i,gda39i,gda17i,gda18f,gda19i,gda34f,gda35f,gda36f,gda37f,gda38f,'00105',1
from [10.0.0.9].S3GOS2016.dbo.gdm01 where gda00c='501'

update projectinfo set editflag=2 where editflag=1 and prjno<>'500'


update a set a.prjname=b.prjname
from projectinfo a ,projectinfo b
where  a.prjno=b.prjno and b.prjmodeId='SPM00101' and b.prjno in (select prjno from (select prjno,prjname from  projectinfo group by prjno,prjname ) as c group by prjno having COUNT(prjno)>1 )
delete projectnameinfo
insert projectnameinfo(prjno,prjname,prjtype,prjpricetype,prjreporttype)
select prjno,prjname,prjtype,prjpricetype,prjreporttype from  projectinfo group by prjno,prjname,prjtype,prjpricetype,prjreporttype


delete syscommoninfomode where modetype=1


insert syscommoninfomode(modeid,modetype,modename,modesource,parentmodeid)
select 'SPM'+bran_id,1,gae03c+'项目模板',bran_id,'SPM'+bran_id 
from  [10.0.0.9].S3GOS2016.dbo.gam05,[10.0.0.9].S3GOS2016.dbo.b_perf 
where gae01c=bran_id and  lvl=2

update sysparaminfo set paramvalue='SPM'+curcomp from sysparaminfo,compchaininfo,compchainstruct 
where paramid='SP059' and complevel=2 and curcompno=curcomp
and relationcomp=compid


delete goodsinfo
insert goodsinfo(goodsmodeid,goodsno,goodsuniquebar,goodsbarno,goodsname,CREATEdate,goodstype,goodspricetype,goodsappsource,goodswarehouse,
goodssupplier,costunit,saleunit,purchaseunit,goodsformat,saletocostcount,purtocostcount,purchaseprice,costamtbysale,standprice,
storesalseprice,shelflife,lowstock,heightstock,appflag,useflag,stopdate,stopmark,pointtype,pointvalue,
yetype,yevalue,tctype,tcvalue,goodssource,finaltype)
select 'SGM',gfa01c,gfa49c,gfa02c,gfa03c,gfa04d,gfa05c,gfa24c,gfa45i,gfa46c,
gfa23c,gfa08c,gfa07c,gfa20c,gfa12c,gfa09f,gfa21f,gfa22f,gfa19f,gfa11f,
gfa30f,gfa28i,gfa18f,gfa29f,gfa38i,gfa10c,gfa47d,gfa48c,gfa25i,gfa26f,
gfa16i,gfa17f,gfa13i,gfa14f,'001',1
from [10.0.0.9].S3GOS2016.dbo.gfm01 where gfa00c='001'



delete cardtypeinfo
insert cardtypeinfo(cardtypemodeid,cardtypeno,cardtypename,cardusetype,cardchiptype,carduselife,cardsaleprice,cardcost,saletctype,saletcvalue,
saleyjtype,saleyjvalue,fillyjtype,fillyjvalue,filltctype,filltcvalue,pointtype,prjpointvalue,goodspointvalue,lowfillamt,lowopenamt,
salegoodsflag,slaeproerate,changerule,openflag,fillflag,cardtypesource,finaltype)
select 'SCM',gak01c,gak02c,gak03i,gak29i,gak07f,gak09f,gak19f,gak17i,gak18f,gak20i,gak21f,gak25i,gak26f,gak27i,gak28f,gak30i,gak33f,gak34f,
  gak40f,gak42f,gak43i,gak44f,gak45i,gak46i,gak47i,'001',1 from [10.0.0.9].S3GOS2016.dbo.gam10 where gak00c='001'
  
insert syscommoninfomode(modeid,modetype,modename,modesource,parentmodeid)
values('SCM024',3,'024卡类型模板','024','SCM')
insert syscommoninfomode(modeid,modetype,modename,modesource,parentmodeid)
values('SCM029',3,'029卡类型模板','029','SCM')
insert syscommoninfomode(modeid,modetype,modename,modesource,parentmodeid)
values('SCM048',3,'048卡类型模板','048','SCM')
insert syscommoninfomode(modeid,modetype,modename,modesource,parentmodeid)
values('SCM301',3,'301卡类型模板','301','SCM')
insert syscommoninfomode(modeid,modetype,modename,modesource,parentmodeid)
values('SCM302',3,'302卡类型模板','302','SCM')
insert syscommoninfomode(modeid,modetype,modename,modesource,parentmodeid)
values('SCM303',3,'303卡类型模板','303','SCM')
insert syscommoninfomode(modeid,modetype,modename,modesource,parentmodeid)
values('SCM00102',3,'00102卡类型模板','00102','SCM')
insert syscommoninfomode(modeid,modetype,modename,modesource,parentmodeid)
values('SCM501',3,'501卡类型模板','501','SCM')

  insert cardtypeinfo(cardtypemodeid,cardtypeno,cardtypename,cardusetype,cardchiptype,carduselife,cardsaleprice,cardcost,saletctype,saletcvalue,
  saleyjtype,saleyjvalue,fillyjtype,fillyjvalue,filltctype,filltcvalue,pointtype,prjpointvalue,goodspointvalue,lowfillamt,lowopenamt,
salegoodsflag,slaeproerate,changerule,openflag,fillflag,cardtypesource,finaltype)
select  'SCM024',gak01c,gak02c,gak03i,gak29i,gak07f,gak09f,gak19f,gak17i,gak18f,gak20i,gak21f,gak25i,gak26f,gak27i,gak28f,gak30i,gak33f,gak34f,
  gak40f,gak42f,gak43i,gak44f,gak45i,gak46i,gak47i,'024',1 from  [10.0.0.9].S3GOS2016.dbo.gam10  
  where gak01c not in (  select gak01c from  [10.0.0.9].S3GOS2016.dbo.gam10 where gak00c='001') and gak00c='024'
  
  insert cardtypeinfo(cardtypemodeid,cardtypeno,cardtypename,cardusetype,cardchiptype,carduselife,cardsaleprice,cardcost,saletctype,saletcvalue,
  saleyjtype,saleyjvalue,fillyjtype,fillyjvalue,filltctype,filltcvalue,pointtype,prjpointvalue,goodspointvalue,lowfillamt,lowopenamt,
salegoodsflag,slaeproerate,changerule,openflag,fillflag,cardtypesource,finaltype)
select  'SCM029',gak01c,gak02c,gak03i,gak29i,gak07f,gak09f,gak19f,gak17i,gak18f,gak20i,gak21f,gak25i,gak26f,gak27i,gak28f,gak30i,gak33f,gak34f,
  gak40f,gak42f,gak43i,gak44f,gak45i,gak46i,gak47i,'029',1 from  [10.0.0.9].S3GOS2016.dbo.gam10  
  where gak01c not in (  select gak01c from  [10.0.0.9].S3GOS2016.dbo.gam10 where gak00c='001') and gak00c='029'



  insert cardtypeinfo(cardtypemodeid,cardtypeno,cardtypename,cardusetype,cardchiptype,carduselife,cardsaleprice,cardcost,saletctype,saletcvalue,
  saleyjtype,saleyjvalue,fillyjtype,fillyjvalue,filltctype,filltcvalue,pointtype,prjpointvalue,goodspointvalue,lowfillamt,lowopenamt,
salegoodsflag,slaeproerate,changerule,openflag,fillflag,cardtypesource,finaltype)
select  'SCM048',gak01c,gak02c,gak03i,gak29i,gak07f,gak09f,gak19f,gak17i,gak18f,gak20i,gak21f,gak25i,gak26f,gak27i,gak28f,gak30i,gak33f,gak34f,
  gak40f,gak42f,gak43i,gak44f,gak45i,gak46i,gak47i,'048',1 from  [10.0.0.9].S3GOS2016.dbo.gam10  
  where gak01c not in (  select gak01c from  [10.0.0.9].S3GOS2016.dbo.gam10 where gak00c='001') and gak00c='048'
  
  
    insert cardtypeinfo(cardtypemodeid,cardtypeno,cardtypename,cardusetype,cardchiptype,carduselife,cardsaleprice,cardcost,saletctype,saletcvalue,
  saleyjtype,saleyjvalue,fillyjtype,fillyjvalue,filltctype,filltcvalue,pointtype,prjpointvalue,goodspointvalue,lowfillamt,lowopenamt,
salegoodsflag,slaeproerate,changerule,openflag,fillflag,cardtypesource,finaltype)
select  'SCM301',gak01c,gak02c,gak03i,gak29i,gak07f,gak09f,gak19f,gak17i,gak18f,gak20i,gak21f,gak25i,gak26f,gak27i,gak28f,gak30i,gak33f,gak34f,
  gak40f,gak42f,gak43i,gak44f,gak45i,gak46i,gak47i,'301',1 from  [10.0.0.9].S3GOS2016.dbo.gam10  
  where gak01c not in (  select gak01c from  [10.0.0.9].S3GOS2016.dbo.gam10 where gak00c='001') and gak00c='301'


    insert cardtypeinfo(cardtypemodeid,cardtypeno,cardtypename,cardusetype,cardchiptype,carduselife,cardsaleprice,cardcost,saletctype,saletcvalue,
  saleyjtype,saleyjvalue,fillyjtype,fillyjvalue,filltctype,filltcvalue,pointtype,prjpointvalue,goodspointvalue,lowfillamt,lowopenamt,
salegoodsflag,slaeproerate,changerule,openflag,fillflag,cardtypesource,finaltype)
select  'SCM302',gak01c,gak02c,gak03i,gak29i,gak07f,gak09f,gak19f,gak17i,gak18f,gak20i,gak21f,gak25i,gak26f,gak27i,gak28f,gak30i,gak33f,gak34f,
  gak40f,gak42f,gak43i,gak44f,gak45i,gak46i,gak47i,'302',1 from  [10.0.0.9].S3GOS2016.dbo.gam10  
  where gak01c not in (  select gak01c from  [10.0.0.9].S3GOS2016.dbo.gam10 where gak00c='001') and gak00c='302'


    insert cardtypeinfo(cardtypemodeid,cardtypeno,cardtypename,cardusetype,cardchiptype,carduselife,cardsaleprice,cardcost,saletctype,saletcvalue,
  saleyjtype,saleyjvalue,fillyjtype,fillyjvalue,filltctype,filltcvalue,pointtype,prjpointvalue,goodspointvalue,lowfillamt,lowopenamt,
salegoodsflag,slaeproerate,changerule,openflag,fillflag,cardtypesource,finaltype)
select  'SCM303',gak01c,gak02c,gak03i,gak29i,gak07f,gak09f,gak19f,gak17i,gak18f,gak20i,gak21f,gak25i,gak26f,gak27i,gak28f,gak30i,gak33f,gak34f,
  gak40f,gak42f,gak43i,gak44f,gak45i,gak46i,gak47i,'303',1 from  [10.0.0.9].S3GOS2016.dbo.gam10  
  where gak01c not in (  select gak01c from  [10.0.0.9].S3GOS2016.dbo.gam10 where gak00c='001') and gak00c='303'



  insert cardtypeinfo(cardtypemodeid,cardtypeno,cardtypename,cardusetype,cardchiptype,carduselife,cardsaleprice,cardcost,saletctype,saletcvalue,
  saleyjtype,saleyjvalue,fillyjtype,fillyjvalue,filltctype,filltcvalue,pointtype,prjpointvalue,goodspointvalue,lowfillamt,lowopenamt,
salegoodsflag,slaeproerate,changerule,openflag,fillflag,cardtypesource,finaltype)
select  'SCM501',gak01c,gak02c,gak03i,gak29i,gak07f,gak09f,gak19f,gak17i,gak18f,gak20i,gak21f,gak25i,gak26f,gak27i,gak28f,gak30i,gak33f,gak34f,
  gak40f,gak42f,gak43i,gak44f,gak45i,gak46i,gak47i,'501',1 from  [10.0.0.9].S3GOS2016.dbo.gam10  
  where gak01c not in (  select gak01c from  [10.0.0.9].S3GOS2016.dbo.gam10 where gak00c='001') and gak00c='501'



delete cardchangerule
insert cardchangerule(rulemodeid,cardtypeno,seqno,tocardtypeno,changeamt,cardtypesource)
select 'SCM',fed01c,fed02f,fed03c,fed04f,'001'  from [10.0.0.9].S3GOS2016.dbo.fae04 where fed00c='001'

delete goodsnameinfo
insert goodsnameinfo(goodsno,goodsbarno,goodsname,goodstype,goodspricetype)
select goodsno,goodsbarno,goodsname,goodstype,goodspricetype from goodsinfo group by goodsno,goodsbarno,goodsname,goodstype,goodspricetype
delete projectnameinfo

insert projectnameinfo(prjno,prjname,prjtype,prjpricetype,prjreporttype)
select prjno,prjname,prjtype,prjpricetype,prjreporttype from  projectinfo group by prjno,prjname,prjtype,prjpricetype,prjreporttype
delete cardtypenameinfo
insert cardtypenameinfo(cardtypeno,cardtypename)
select cardtypeno,cardtypename from cardtypeinfo group by cardtypeno,cardtypename


insert promotionsinfo(compid,billid,promotionstype,promotionscode,promotionsstore,promotionsvalue,startdate,enddate,promotionsreason,promotionsstate)
select gdx00c,gdx01c,gdx02i,gdx03c,gdx12c,gdx05f,gdx06d,gdx07d,gdx08c,gdx09i from [10.0.0.9].S3GOS2016.dbo.gdm24


insert mcardnoinsert(cinsertcompid,cinsertbillid,cinsertdate,cinserttime,cinsertware,cinsertper,optionconfrimdate,optionconfrimper,invalid,checkoutflag,billflag,CREATEcompname,billno,checkoutmark,checkoutimgurl)
select fba00c,fba01c,fba03d,fba04t,fba05c,fba08c,fba16c,fba17c,0,fba10i,fba12i,fba02c,fba13c,fba14c,'' 
from [10.0.0.9].S3GOS2016.dbo.fab01

insert dcardnoinsert(cinsertcompid,cinsertbillid,seqno,cardtypeid,cardnofrom,cardnoto,cardnum,cardprice,cardamt)
select fbb00c,fbb01c,fbb02f,fbb03c,fbb04c,fbb05c,fbb06f,fbb07f,fbb08f
 from [10.0.0.9].S3GOS2016.dbo.fab02

insert cardstockchange(changecompid,changetype,changebill,changeseqno,cardtype,changecardfromno,changecardtono,changecount,changeprice,changeamt,changedate,changeware)
select fdc00c,fdc01c,fdc02c,fdc03f,fdc04c,fdc05c,fdc06c,fdc07f,fdc08f,fdc09f,fdc10d,fdc11c 
from [10.0.0.9].S3GOS2016.dbo.fad03

insert mcardallotment(callotcompid,callotbillid,callotdate,callottime,recevieempid,callotempid,callotopationempid,callotopationdate,checkoutflag,checkoutdate,checkoutemp,cappbillid,cappcompid,callotwareid)
select fda00c,fda01c,fda02d,fda03t,fda04c,fda05c,fda05c,fda06c,fda09i,fda10c,fda11c,fda12c,fda13c,fda14c
 from [10.0.0.9].S3GOS2016.dbo.fad01

insert dcardallotment(callotcompid,callotbillid,callotseqno,cardtypeid,cardnofrom,cardnoto,ccount)
select fdb00c,fdb01c,fdb02f,fdb03c,fdb04c,fdb05c,fdb06f from [10.0.0.9].S3GOS2016.dbo.fad02


insert cardstock(cardclass,cardfrom,cardto,ccount,storage,compid)
select cardclass,cardfrom,cardto,count,storage,compid from [10.0.0.9].S3GOS2016.dbo.fab02_cache

insert sendpointcard(sendcompid,sendbillid,sendtype,senddate,sendempid,sourcebillid,sourcecardno,sourcedate,sourceamt,sendcardno,sendamt,sendmark,operation,membername,memberphone,sendrateflag,sendpicflag,picno)
select gxi00c,gxi01c,gxi02i,gxi03d,gxi04c,gxi05c,gxi06c,gxi07d,gxi08f,gxi09c,gxi10f,gxi11c,gxi12c,gxi13c,gxi14c,gxi15i,gxi16i,gxi17c 
from [10.0.0.9].S3GOS2016.dbo.gxm08


insert sysaccountforpaymode(paymode,accounttype)
select gsn01c,gsn02c from [10.0.0.9].S3GOS2016.dbo.gsm14

insert cardsoninfo(cardvesting,cardno,cardtype,salecarddate,parentcardno,membername,memberphone,salebillno,saleamt,songfalg)
select gcs00c,gcs01c,gcs02c,gcs03d,gcs06c,gcs04c,gcs05c,gcs08c,gcs07f,gcs09i 
from [10.0.0.9].S3GOS2016.dbo.Gcm01_son


insert corpsbuyinfo(corpscardno,corpstype,corpssource,corpspicno,corpsamt,operationer,operationdate,corpssate,useincompid,useinbillno,useindate)
select fej01c,fej02i,'01',fej03c,fej04f,fej05c,fej06d,fej07i,fej08c,fej09c,fej10d 
from [10.0.0.9].S3GOS2016.dbo.fex09

delete dnointernalcardinfo
insert dnointernalcardinfo(cardvesting,cardno,seqno,ineritemno,entrycount,usecount,lastcount,entryamt,useamt,lastamt,entryremark)
select fei00c,fei01c,fei02f,fei03c,fei04f,fei05f,fei06f,fei07f,fei08f,fei09f, fei10c from [10.0.0.9].S3GOS2016.dbo.fex08

delete nointernalcardinfo	
insert nointernalcardinfo(cardvesting,cardno,cardtype,cardfaceamt,carduseflag,entrytype,cardstate,usedate,useinproject,oldvalidate,lastvalidate)
select feh00c,feh01c,feh05i,feh04f,feh07i,feh11i,feh06i,feh08d,feh09c,feh12d,feh13d from [10.0.0.9].S3GOS2016.dbo.fex07


insert mpackageinfo(compid,packageno,packagename,packageprice,paceageremark)
select gta00c,gta01c,gta02c,gta03f,gta04c from [10.0.0.9].S3GOS2016.dbo.gtc01
insert dmpackageinfo(compid,packageno,packageprono,packageprocount,packageproamt)
select gtb00c,gtb01c,gtb02c,gtb03f,gtb04f from [10.0.0.9].S3GOS2016.dbo.gtc02


update sysparaminfo set paramvalue='SPM' where paramid='SP059' and compid='001'
update sysparaminfo set paramvalue='SGM' where paramid='SP061'
update sysparaminfo set paramvalue='SCM' where paramid='SP063'

update sysparaminfo set paramvalue='SCM024' where paramid='SP063' and compid='024'
update sysparaminfo set paramvalue='SCM029' where paramid='SP063' and compid='029'
update sysparaminfo set paramvalue='SCM048' where paramid='SP063' and compid='048'
update sysparaminfo set paramvalue='SCM301' where paramid='SP063' and compid='301'
update sysparaminfo set paramvalue='SCM302' where paramid='SP063' and compid='302'
update sysparaminfo set paramvalue='SCM303' where paramid='SP063' and compid='303'
update sysparaminfo set paramvalue='SCM501' where paramid='SP063' and compid='501'


--insert mgoodsinsert(insercompid,inserbillid,inserdate,inserwareid,inserstaffid,insertype,storesendbill)
--select gha00c,gha01c,gha03d,gha04c,gha05c,gha07i,gha16c from [S3GOS2016].dbo.ghm01
  
--insert  dgoodsinsert(insercompid,inserbillid,inserseqno,insergoodsno,inserunit,insercount,goodsprice,goodsamt,standunit,standcount,standprice)
--select ghb00c,ghb01c,ghb02f,ghb03c,ghb04c,ghb07f,ghb10f,ghb11f,ghb04c,ghb05f,ghb10f from [S3GOS2016].dbo.ghm02

--insert mgoodsstockinfo(changecompid,changetype,changebillno,changedate,changetime,changewareid,changeoption,changestaffid,changeflag)
--select gfc00c,gfc01c,gfc02c,gfc03c,'',gfc05c,gfc06i,gfc07c,gfc08i from [S3GOS2016].dbo.gfm03

--insert dgoodsstockinfo(changecompid,changetype,changebillno,changeseqno,changegoodsno,standunit,standcount,standprice,producedate,changeunit,changeprice,changecount,changeamt)
--select gfd00c,gfd01c,gfd02c,gfd03f,gfd04c,gfd11c,gfd12f,gfd06f,'',gfd11c,gfd06f,gfd12f,gfd13f from [S3GOS2016].dbo.gfm04 where isnull(gfd04c,'')<>''
--insert dgoodsbarinfo(goodsno,goodsbarno,barnostate,createdate,createstaffno,inserdate,inserbillno,outerdate,outerbill,receivestore,costdate,costbillo,coststore)
--select gfh01c,gfh02c,gfh03i,gfh04d,gfh05c,gfh06d,gfh07c,gfh08d,gfh09c,gfh10c,gfh11d,gfh12c,gfh13c from [S3GOS2016].dbo.gfm08

insert mgoodsreceipt(receiptcompid,receiptbillid,receiptdate,receiptwareid,receiptorderbillid,receiptsendbillid,receiptstate,orderbilltype)
select gva00c,gva01c,gva02d,gva03c,gva05c,gva06c,gva07i,1 from [S3GOS2016].dbo.gvm01 where gva01c='0382013122900101'

insert dgoodsreceipt(receiptcompid,receiptbillid,receiptseqno,receiptgoodsno,sendgoodscount,ordergoodscount,receiptgoodsunit,receiptprice)
select gvb00c,gvb01c,gvb02f,gvb03c,gvb05f,gvb08f,gvb09c,gvb12f from  [S3GOS2016].dbo.gvm02 where gvb01c='038201'

 insert goodsnameinfo(goodsno,goodsbarno,goodsname,goodstype,goodspricetype)
select goodsno,goodsbarno,goodsname,goodstype,goodspricetype 
from goodsinfo where goodsno not in (select goodsno from  goodsnameinfo)
 group by goodsno,goodsbarno,goodsname,goodstype,goodspricetype
 
 
 insert projectnameinfo(prjno,prjname,prjtype,prjpricetype,prjreporttype)
select prjno,prjname,prjtype,prjpricetype,prjreporttype 
from  projectinfo where prjno not in (select prjno from projectnameinfo)
group by prjno,prjname,prjtype,prjpricetype,prjreporttype

insert cardtypenameinfo(cardtypeno,cardtypename)
select cardtypeno,cardtypename 
from cardtypeinfo where cardtypeno not in (select cardtypeno from cardtypenameinfo ) group by cardtypeno,cardtypename
