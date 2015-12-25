select * from MFQC
select * from MRQC


insert mgoodsouter(outercompid,outerbillid,outerdate,outertime,outerwareid,outertype,revicetype,outerstaffid,sendbillid,billflag,outeropationerid,outeropationdate)
select '001','00120131231001x','20131231','202020','01',1,1,'amani','',2,'amani','20131231'


insert mgoodsouter(outercompid,outerbillid,outerdate,outertime,outerwareid,outertype,revicetype,outerstaffid,sendbillid,billflag,outeropationerid,outeropationdate)
select '001','00120131231002x','20131231','202020','02',1,1,'amani','',2,'amani','20131231'

insert dgoodsouter(outercompid,outerbillid,outerseqno,outergoodsno,standunit,standprice,curgoodsstock,outerunit,outercount,outerprice,outeramt,outerrate)
select '001','00120131231001x',row_number() over(order by goodsno desc),goodsno,'瓶',goodsprice,goodscount,'瓶',goodscount,goodsprice,goodsamt,1
from MFQC

insert dgoodsouter(outercompid,outerbillid,outerseqno,outergoodsno,standunit,standprice,curgoodsstock,outerunit,outercount,outerprice,outeramt,outerrate)
select '001','00120131231002x',row_number() over(order by goodsno desc),goodsno,'瓶',goodsprice,goodscount,'瓶',goodscount,goodsprice,goodsamt,1
from MRQC


insert mgoodsinsert(insercompid,inserbillid,inserdate,insertime,inserwareid,inserstaffid,insertype,checkbillflag,checkbillno,storesendbill,exitstoreno,exidbillno,billflag)
select '001','00120131231001x','20131231','202020','01','amani',1,0,'','','','',0
insert mgoodsinsert(insercompid,inserbillid,inserdate,insertime,inserwareid,inserstaffid,insertype,checkbillflag,checkbillno,storesendbill,exitstoreno,exidbillno,billflag)
select '001','00120131231002x','20131231','202020','02','amani',1,0,'','','','',0

insert dgoodsinsert(insercompid,inserbillid,inserseqno,insergoodsno,inserunit,insercount,goodsprice,goodsamt,standunit,standcount,standprice)
select '001','00120131231001x',row_number() over(order by goodsno desc),goodsno,'瓶',goodscount,goodsprice,goodsamt,'瓶',goodscount,goodsprice
from MFQC

insert dgoodsinsert(insercompid,inserbillid,inserseqno,insergoodsno,inserunit,insercount,goodsprice,goodsamt,standunit,standcount,standprice)
select '001','00120131231002x',row_number() over(order by goodsno desc),goodsno,'瓶',goodscount,goodsprice,goodsamt,'瓶',goodscount,goodsprice
from MRQC



if not exists(select 1 from sysobjects where type='U' and name='dgoodsinsert')
CREATE TABLE    dgoodsinsert             
(
	insercompid			varchar(10)				NOT NULL,   --公司编号
	inserbillid			varchar(30)				NOT NULL,   --入库单号
	inserseqno			float					NOT NULL,   --序号
	insergoodsno		varchar(20)					NULL,   --产品代码
	inserunit			varchar(5)					NULL,   --进货单位
	insercount			float						NULL,   --(进货单位)数量
	goodsprice			float						NULL,   --单价
	goodsamt			float						NULL,   --金额
	standunit			varchar(5)					NULL,   --标准单位
	standcount			float   					NULL,	--标准单位数量
	standprice			float	  					NULL,	--标准单位价格
	producedate			varchar(8)					NULL,   --生产日期
	producenorm			varchar(20)					NULL,   --产品规格
	frombarcode			varchar(20)					NULL,   --起始编码
	tobarcode			varchar(20)					NULL,   --结束编码
	CONSTRAINT PK_dgoodsinsert PRIMARY KEY CLUSTERED(insercompid,inserbillid,inserseqno)
)
go 


update mgoodsouter set billflag=0 ,outeropationerid='',outeropationdate='' where outerbillid='00120131231001x'
update mgoodsouter set billflag=0 ,outeropationerid='',outeropationdate='' where outerbillid='00120131231002x'


select * from  mgoodsstockinfo where changebillno='00120131231002x' and changetype='1'
select * from dgoodsstockinfo where changebillno='00120131231002x' and changetype='1'

insert mgoodsouter(outercompid,outerbillid,outerdate,outertime,outerwareid,outertype,revicetype,outerstaffid,sendbillid,billflag,outeropationerid,outeropationdate)

insert dgoodsouter(outercompid,outerbillid,outerseqno,outergoodsno,standunit,standprice,curgoodsstock,outerunit,outercount,outerprice,outeramt,outerrate)

insert mgoodsstockinfo(changecompid,changetype,changebillno,changedate,changetime,changewareid,changeoption,changestaffid,changeflag)
select changecompid,changetype,'20130513001_02',changedate,changetime,'02',changeoption,changestaffid,changeflag
 from mgoodsstockinfo where changebillno='20130513001_01'
 
 insert dgoodsstockinfo( changecompid,changetype,changebillno,changeseqno,changegoodsno,standunit,standcount,standprice,producedate,changeunit,changeprice,changecount,changeamt)
select changecompid,changetype,'20130513001_02',changeseqno,changegoodsno,standunit,standcount,standprice,producedate,changeunit,changeprice,changecount,changeamt
 from dgoodsstockinfo where changebillno='20130513001_01' and changegoodsno='41209103'
delete dgoodsstockinfo where changebillno='20130513001_01' and changegoodsno='41209103'


select * from MRQC where goodsno='50209'

select * from MfQC where goodsno='50209'



update dgoodsstockinfo set changecompid=changecompid+'X' where changegoodsno in (
select goodsno from goodsinfo where goodsno ='50211'  )

