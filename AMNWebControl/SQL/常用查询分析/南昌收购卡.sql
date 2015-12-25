ª·‘±ø®			SGNC100
8.5’€VIP		SGNC85
7’€vipø®		SGNC70
5.5’€VIP		SGNC55
5’€VIPø®		SGNC50
6’€VIPø®		SGNC60
6.8’€VIP		SGNC68
7.5’€VIP		SGNC75
4.5’€VIP		SGNC45
4.8’€VIP		SGNC48
6.5’€VIP		SGNC65

insert cardtypeinfo(cardtypemodeid,cardtypeno,cardtypename,cardusetype,cardchiptype,carduselife,cardtypesource,changeflag)
values('SCM00102','SGNC100','∆Æ…≥∑ªª·‘±ø®',1,0,100,'00102',1)
insert cardtypeinfo(cardtypemodeid,cardtypeno,cardtypename,cardusetype,cardchiptype,carduselife,cardtypesource,changeflag)
values('SCM00102','SGNC85','∆Æ…≥∑ª8.5’€ª·‘±ø®',1,0,100,'00102',1)
insert cardtypeinfo(cardtypemodeid,cardtypeno,cardtypename,cardusetype,cardchiptype,carduselife,cardtypesource,changeflag)
values('SCM00102','SGNC70','∆Æ…≥∑ª7’€ª·‘±ø®',1,0,100,'00102',1)
insert cardtypeinfo(cardtypemodeid,cardtypeno,cardtypename,cardusetype,cardchiptype,carduselife,cardtypesource,changeflag)
values('SCM00102','SGNC55','∆Æ…≥∑ª5.5’€ª·‘±ø®',1,0,100,'00102',1)
insert cardtypeinfo(cardtypemodeid,cardtypeno,cardtypename,cardusetype,cardchiptype,carduselife,cardtypesource,changeflag)
values('SCM00102','SGNC50','∆Æ…≥∑ª5’€ª·‘±ø®',1,0,100,'00102',1)
insert cardtypeinfo(cardtypemodeid,cardtypeno,cardtypename,cardusetype,cardchiptype,carduselife,cardtypesource,changeflag)
values('SCM00102','SGNC60','∆Æ…≥∑ª6’€ª·‘±ø®',1,0,100,'00102',1)
insert cardtypeinfo(cardtypemodeid,cardtypeno,cardtypename,cardusetype,cardchiptype,carduselife,cardtypesource,changeflag)
values('SCM00102','SGNC68','∆Æ…≥∑ª6.8’€ª·‘±ø®',1,0,100,'00102',1)
insert cardtypeinfo(cardtypemodeid,cardtypeno,cardtypename,cardusetype,cardchiptype,carduselife,cardtypesource,changeflag)
values('SCM00102','SGNC75','∆Æ…≥∑ª7.5’€ª·‘±ø®',1,0,100,'00102',1)
insert cardtypeinfo(cardtypemodeid,cardtypeno,cardtypename,cardusetype,cardchiptype,carduselife,cardtypesource,changeflag)
values('SCM00102','SGNC45','∆Æ…≥∑ª4.5’€ª·‘±ø®',1,0,100,'00102',1)
insert cardtypeinfo(cardtypemodeid,cardtypeno,cardtypename,cardusetype,cardchiptype,carduselife,cardtypesource,changeflag)
values('SCM00102','SGNC48','∆Æ…≥∑ª4.8’€ª·‘±ø®',1,0,100,'00102',1)
insert cardtypeinfo(cardtypemodeid,cardtypeno,cardtypename,cardusetype,cardchiptype,carduselife,cardtypesource,changeflag)
values('SCM00102','SGNC65','∆Æ…≥∑ª6.5’€ª·‘±ø®',1,0,100,'00102',1)


update syscommoninfomode set parentmodeid='SCM00102' where modeid='SCM301'
update syscommoninfomode set parentmodeid='SCM00102' where modeid='SCM302'
update syscommoninfomode set parentmodeid='SCM00102' where modeid='SCM303'


select * from cardinfo where cardvesting='301' and cardstate=4

exec sp_addlinkedserver '10.0.0.10','SQL Server'
exec sp_addlinkedsrvlogin '10.0.0.10','false',null,'sa','qwerasdf123~'



insert cardinfo(cardvesting,cardno,cardtype,membernotocard,salecarddate,cutoffdate,cardstate,salebillno,costpassword,searchpassword,cardremark,cardsource,costcountbydebts,costamtbydebts,costamt)
select '00102','SGNC'+gca01c,gca02c,gca04c,gca05d,gca07d,gca08i,gca11c,gca12c,gca18c,gca27c,1,1,0,0 
from [10.0.0.10].SG20140303.dbo.gcm01 where gca00c=gca13d


 insert cardaccount(cardvesting,cardno,accounttype,accountbalance,accountdebts,accountdatefrom,accountdateend,accountremark)
 select '00102','SGNC'+gca01c,2,0,0,'','','' 
from [10.0.0.10].SG20140303.dbo.gcm01 where gca00c=gca13d

insert cardaccount(cardvesting,cardno,accounttype,accountbalance,accountdebts,accountdatefrom,accountdateend,accountremark)
select '00102','SGNC'+gcc01c,5,gcc06f,gcc10f,gcc08c,gcc09d,gcc11c 
from [10.0.0.10].SG20140303.dbo.gcm01,[10.0.0.10].SG20140303.dbo.gcm03
 where gcc00c=gca00c and gcc01c=gca01c and gca00c=gca13d and gcc03i=2
 
 insert memberinfo(membervesting,memberno,memberCREATEdate,membername,memberaddress,membertphone,membermphone,memberFax,memberemail,memberzip,membersex,memberpaperworkno,memberbirthday,cardnotomemberno,memberqqno,membertype)
select '00102','SGNC'+gba01c,gba02c,gba03c,gba05c,gba07c,gba08c,gba09c,gba11c,gba12c,gba14i,gba16c,gba17d,'SGNC'+gba23c,gba32c,'' 
from [10.0.0.10].SG20140303.dbo.gbm01
 
