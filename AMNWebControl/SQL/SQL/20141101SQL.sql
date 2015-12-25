
create table gooddiscount
(
	compid varchar(20) not null,--公司编号
	bprojecttypeid varchar(20) not null,--产品类别
	iscard   int   null,--是否可以卡付
	CONSTRAINT PK_gooddiscount PRIMARY KEY NONCLUSTERED(compid,bprojecttypeid)
)
go


alter table Dsalebarcodecardinfo
add packageNo varchar(50)  --套餐编号
go

alter table dnointernalcardinfo
add packageNo varchar(50)  --套餐编号
go


alter table projectinfo
add ipadname varchar(50) null   --ipad显示名称
go

alter table companyinfo
add roomnumber int, --房间数量
mirrornumber int --镜台数量
go

alter table staffinfo
add displayname	varchar(200)	NULL,--展示名称
staffintroduction	varchar(500)	null	--员工介绍
go

-----categoryinfo 排班分类设定 
---------------------------------------
CREATE table categoryinfo
(
	compno				varchar(10)		Not  NULL,	-- 当前门店
	categoryno			varchar(20)		Not  NULL,	-- 类别编号
	categoryname		varchar(50)		Not  NULL,	-- 类别名称
	categorymark			varchar(200)		 NULL,	-- 类别备注
)
go
-----categoryinfoid 排班分类与职位绑定 
---------------------------------------
CREATE table categoryinfoid
(
	compno				varchar(10)		Not  NULL,	-- 当前门店
	categoryno			varchar(20)		Not  NULL,	-- 类别编号
	postationid		varchar(20)		Not  NULL,	-- 职位编号
	postationname			varchar(20)		 NULL,	-- 职位名称
)
go



insert into sysmodeinfo 
(sysversion,upmoduleno,curmoduleno,modulename,modulevel,moduletype,remark,moduletitle,moduleurl,modulewidth,moduleheight) values
 ('2013001','AdvancedOperations','AC020','排班分类设定',2,'R','','排班分类设定','AdvancedOperations/AC020/index.jsp',900,600);
 go


