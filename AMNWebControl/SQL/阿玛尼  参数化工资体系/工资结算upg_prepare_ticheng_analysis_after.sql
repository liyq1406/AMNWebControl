--短信未处理单据
if not exists(select 1 from sysobjects where type='U' and name='messagecall')
create table messagecall(
			callid			INT  IDENTITY(1, 1)		PRIMARY KEY, 
			callbillid		varchar(30)				NOT NULL, --单据编号
			calluserid		varchar(50)					NULL,  --登陆工号
			callon			varchar(20)					NULL,   --呼入电话号码
			offertime		varchar(50)					NULL, --摘机时间（短息接收时间）
			calledtime		varchar(50)					NULL, --短信进入系统时间
			calltype		int							NULL,           --类型（1 表示满意 ，2 表示不满意）
			callstate		int							NULL,          --状态（0表示未处理，1表示总部受理，2表示处理中，3已结案）
			callmark		varchar(600)				NULL, --备注
)
select * from messagecall
select * from callwaiting
insert messagecall (callbillid,calluserid,callon,offertime,calledtime,calltype,callstate,callmark) 
values('00120141013001','0010303','13524503341','2013_11_11_09_11_55','201311091155',2,0,'');
insert messagecall (callbillid,calluserid,callon,offertime,calledtime,calltype,callstate,callmark) 
values('00120141013002','0010303','13524503341','2013_11_11_09_11_55','201311091155',2,0,'');
insert messagecall (callbillid,calluserid,callon,offertime,calledtime,calltype,callstate,callmark) 
values('00120141013003','0010303','13524503341','2013_11_11_09_11_55','201311091155',2,0,'');
insert messagecall (callbillid,calluserid,callon,offertime,calledtime,calltype,callstate,callmark) 
values('00120141013004','0010303','13524503341','2013_11_11_09_11_55','201311091155',2,3,'');
select * from messagecall  where callstate<>3 order by callbillid desc