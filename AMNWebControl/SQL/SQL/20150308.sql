create table vouchers
(
	compid varchar(20) not null,--公司编号
	vchno varchar(50) not null,--类别编号
	vchname varchar(50) null,--名称
	vchtype int  null,--类型
	amt  float null,--金额
	PRIMARY KEY(compid,vchno)
)

create table vouchersdetal
(
	compid varchar(20) not null,--公司编号
	vchno varchar(50) not null,--抵用券类别
	itemno varchar(20) not null,--抵用项目
	vchtype int null,--类型
	amt float null,--金额
	PRIMARY KEY(compid,vchno,itemno)
)

create table weixinclent
(
	operid varchar(100) null,--openid
	clientname varchar(50) null,--客户姓名
	mobile varchar(20) null,--手机号码
	qdyno varchar(20)  not null primary key,--
	vchno varchar(20) null,--抵用大类
	billid varchar(50) null,--单号
	createdate varchar(20) null
)

create table wxbandcard
(
	uuid varchar(50) not null primary key,--唯一编号
	cardno varchar(50) not null,--会员卡号
	randomno varchar(50) not null,--18位随机码
	createdate varchar(20) null,--创建时间yyyyMMddhhmmss
	validate varchar(20) null,--截至有效日期yyyyMMddhhmmss
)
