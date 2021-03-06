---------------------------------------
-----sysuserinfo 系统模板信息 CREATE by liujie 20130628
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='sysmodeinfo')

CREATE table sysmodeinfo
(
	sysversion			varchar(10)			Not NULL	,-- 版本号
	upmoduleno			varchar(20)			Not NULL	,-- 上级模板目录
	curmoduleno			varchar(20)			Not NULL	,-- 模组ID
	modulename	        varchar(40)			NULL		,-- 模组名称
	modulevel           int					NULL		,-- 目录级别  0总模组 1 模组 2 模块
	moduletype			varchar(5)			NULL		,--	模块类型 F功能 R 报表
	remark				varchar(100)		NULL		,--	备注说明
	moduletitle			varchar(20)			NULL		,-- 模块显示标题
	moduleurl			varchar(50)			NULL		,-- 模块路径
	modulewidth			int					NULL		,--	模块宽度
	moduleheight		int					NULL		,--	模块高度
	showtype			int					null		,-- 显示顺序
	CONSTRAINT PK_sysmodeinfo PRIMARY KEY NONCLUSTERED(sysversion,upmoduleno,curmoduleno)
)
go


---------------------------------------
-----sysmodepurview 系统模板权限信息 CREATE by liujie 20130628
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='sysmodepurview')

CREATE table sysmodepurview
(
	curRoleno			varchar(10)			Not NULL	,-- 模组ID
	curpurviewno		varchar(10)			Not NULL	,-- 权限ID
	purviewname	        varchar(40)			NULL		,-- 权限名称
	remark				varchar(50)			NULL		,--	备注说明
	
	CONSTRAINT PK_sysmodepurview PRIMARY KEY NONCLUSTERED(curpurviewno,curRoleno)
)
go


---------------------------------------
-----sysuserinfo 系统使用者信息 CREATE by liujie 20130628(sysuserlogininfo) 
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='sysuserinfo')

CREATE table sysuserinfo 
(
	userno				varchar(20)		Not NULL,   --用户名
	userpwd				varchar(100)	NULL,		--密码
	enableflag			int				NULL,		--是否启用 1 启用 0 不启用
	userrole			varchar(5)		NULL,		--用户角色 1 系统管理者 2 门店管理者 3人事专员 4财务专员 5 信息专员 6收银员			
	frominnerno			varchar(20)		NULL,		--内部编号
	fromcompno			varchar(10)		NULL,		--门店编号
	datefrom			varchar(10)		NULL,		--起始日期
	dateto				varchar(10)		NULL,		--结束日期
	callcenterqueue		varchar(30)		NULL,		--呼叫系统机组编号
	callcenterinterface	varchar(40)		NULL,		--座机所在机组席别编号
	CONSTRAINT PK_sysuserinfo PRIMARY KEY NONCLUSTERED(userno)
)
go




if not  exists(select 1 from sysobjects where type='U' and name='sysuserinfobak')

CREATE table sysuserinfobak 
(
	seqno			int identity(1,1)	Not NULL,
	userno			varchar(20)		Not NULL,   --用户名
	userpwd			varchar(100)	NULL,		--密码
	enableflag		int				NULL,		--是否启用 1 启用 0 不启用
	userrole		varchar(5)		NULL,		--用户角色 1 系统管理者 2 门店管理者 3人事专员 4财务专员 5 信息专员 6收银员			
	frominnerno		varchar(20)		NULL,		--内部编号
	fromcompno		varchar(10)		NULL,		--门店编号
	datefrom		varchar(10)		NULL,		--起始日期
	dateto			varchar(10)		NULL,		--结束日期
	operationerno	varchar(20)		NULL,		--操作人员
	operationdate	varchar(10)		NULL,		--操作日期
	operationtime	varchar(10)		NULL,		--操作日期
	CONSTRAINT PK_sysuserinfobak PRIMARY KEY NONCLUSTERED(seqno)
)
go

-----user 用户分组信息 CREATE by liujie 20130628(sysuserlogininfo) 
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='useroverall')

CREATE table useroverall 
(
	userno			varchar(20)	Not NULL,   --用户名
	modetype		varchar(5)	Not NULL,	--1 登录门店 2 禁用模组 3禁用门店 4 禁用功能  5禁用特殊权限
	modevalue		varchar(20)	Not NULL,	--设置值
	descriptions	varchar(10)	NULL,		--描述
	CONSTRAINT PK_useroverall PRIMARY KEY NONCLUSTERED(userno,modetype,modevalue)
)
go

-----user 用户功能信息编辑 CREATE by liujie 20130628(usereditright) 
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='usereditright')

CREATE table usereditright
(
	userno			varchar(10)	Not NULL,   --用户编号
	sysmodeno		varchar(20)	Not NULL,	--模块编号
	functionno		varchar(20)	Not NULL,	--功能编号
	browsepurview	varchar(5)	NULL,		--查看权限Y 是 N否
	editpurview		varchar(5)	NULL,		--编辑权限Y 是 N否
	exportpurview	varchar(5)	NULL,		--导出权限Y 是 N否
	postpurview		varchar(5)	NULL,		--保存权限Y 是 N否
	confirmpurview	varchar(5)	NULL,		--审核权限Y 是 N否
	invalidpurview	varchar(5)	NULL,		--作废权限Y 是 N否
	disabledflag	int			NULL,		--是否启用  0 禁用 1 启用
	CONSTRAINT PK_usereditright PRIMARY KEY NONCLUSTERED(userno,sysmodeno,functionno)
)
go

-----sysrolemode 角色模板 CREATE by liujie 20130628(sysuserlogininfo) 
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='sysrolemode')

CREATE table sysrolemode 
(
	roleno			varchar(5)	Not NULL,   --角色编号
	sysmodeno		varchar(20)	Not NULL,		--模块编号
	functionno		varchar(20)	Not NULL,		--功能编号
	browsepurview	varchar(5)	NULL,		--查看权限Y 是 N否
	editpurview		varchar(5)	NULL,		--编辑权限Y 是 N否
	exportpurview	varchar(5)	NULL,		--导出权限Y 是 N否
	postpurview		varchar(5)	NULL,		--保存权限Y 是 N否
	confirmpurview	varchar(5)	NULL,		--审核权限Y 是 N否
	invalidpurview	varchar(5)	NULL,		--作废权限Y 是 N否
	disabledflag	int			NULL,		--是否启用  0 禁用 1 启用
	CONSTRAINT PK_sysrolemode PRIMARY KEY NONCLUSTERED(roleno,sysmodeno,functionno)
)
go



---------------------------------------
-----sysuserlogininfo -用户登录日志 CREATE by liujie 20130628
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='sysuserlogininfo')
CREATE table sysuserlogininfo 
(
	seqno				int identity(1,1)	Not NULL,
	logindate			varchar(8)			NULL, -- login date
	logintime			varchar(6)			NULL, -- login time
	loginipno			varchar(50)			NULL, -- login machine's ip
	loginmacno			varchar(50)			NULL, -- login machine's mac address
	loginuserid			varchar(30)			NULL, -- user id
	loginusername		varchar(40)			NULL, -- user name
	logincompid			varchar(10)			NULL, -- login company id 
	logincompname		varchar(50)			NULL, -- login company name
	CONSTRAINT PK_sysuserlogininfo PRIMARY KEY NONCLUSTERED(seqno)
)
go


---------------------------------------
-----company 门店信息 CREATE by liujie 20130628
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='companyinfo')
CREATE table companyinfo
(
	compno				varchar(10)		Not NULL,	-- 门店编号
	compname			varchar(60)     NULL,		-- 门店名称 
	compstate			varchar(2)      NULL,		-- 门店状态
	compphone			varchar(15)     NULL,		-- 门店电话  
	compaddress			varchar(60)     NULL,		-- 门店地址
	comptradelicense	varchar(40)     NULL,		-- 门店营业执照
	compfex				varchar(15)     NULL,		-- 门店传真 
	compzipcode			varchar(6)      NULL,		-- 门店邮政编码
	compadslno			varchar(20)     NULL,		-- 门店ADsL账号
	compadslpassword	varchar(20)		NULL,		-- 门店ADsL密码
	comparea			float		    NULL,		-- 门店面积  
	comprent			float		    NULL,		-- 门店租金  
	compresponsible		varchar(20)		NULL,		-- 门店责任人
	compmode			varchar(5)		NULL,		-- 门店模式
	CREATEdate			varchar(10)		NULL,		-- 创建日期
	region				varchar(100)	NULL,       -- 行政区
	xcoordinate			varchar(20)		NULL,		-- X坐标
	ycoordinate			varchar(20)		NULL,		-- Y坐标
	CONSTRAINT PK_companyinfo PRIMARY KEY NONCLUSTERED(compno)
)
go





---------------------------------------
-----compchainstruct 门店连锁结构 CREATE by liujie 20130628
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='compchainstruct')
CREATE table compchainstruct
(
	curcompno				varchar(10)		Not NULL,	-- 当前门店
	parentcompno			varchar(10)		NULL,		-- 上一级门店
	complevel				int				NULl,		-- 门店级别    1 总部 2 事业部 3 区域 4 门店
	CREATEdate				varchar(10)		NULL,		-- 创建日期	
	CONSTRAINT PK_compchainstruct PRIMARY KEY NONCLUSTERED(curcompno)	
)
go
---------------------------------------
-----compchaininfo 门店连锁明细 CREATE by liujie 20130628
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='compchaininfo')
CREATE table compchaininfo
(
	curcomp				varchar(10)  Not  NULL,-- 当前门店
	relationcomp		varchar(10)  Not  NULL,-- 当前子门店
	CONSTRAINT PK_compchaininfo PRIMARY KEY NONCLUSTERED(curcomp,relationcomp)	
)
go

---------------------------------------
-----a3area 区域表
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='a3area')
CREATE table a3area
(
	id		int			not null,
	code	varchar(10)	not null,
	name	varchar(50)	not null,
	cityId	int			not null,
	CONSTRAINT PK_a3area PRIMARY KEY NONCLUSTERED(id,code,name,cityId)	
)
CREATE NONCLUSTERED index idx_a3area_code on a3area(code)


---------------------------------------
-----a3city 城市表
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='a3city')
CREATE table a3city
(
	id			int			not null,
	code		varchar(10)	not null,
	name		varchar(50)	not null,
	provinceId	int			not null,
	CONSTRAINT PK_a3city PRIMARY KEY NONCLUSTERED(id,code,name,provinceId)
)
CREATE NONCLUSTERED index idx_a3city_code on a3city(code)


---------------------------------------
-----a3province 省份表
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='a3province')
CREATE table a3province
(
	id			int			 not null,
	code		varchar(10)	 not null,
	name		varchar(50)	 not null,
	CONSTRAINT PK_a3province PRIMARY KEY NONCLUSTERED(id,code,name)
)
CREATE NONCLUSTERED index idx_a3province_code on a3province(code)

---------------------------------------
-----compwarehouse 门店仓库 CREATE by liujie 20130628
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='compwarehouse')
CREATE table compwarehouse
(
	compno				varchar(10)		Not  NULL,-- 当前门店
	warehouseno			varchar(10)		Not  NULL,-- 仓库编号
	warehousename		varchar(30)		NULL	  ,-- 仓库名称
	warehousecontact	varchar(10)		NULL,       -- 仓管联络人 
	warehousephone		varchar(20)		NULL,       -- 联络电话
	warehousefax		varchar(20)		NULL,       -- 仓库传真
	warehouseaddress	varchar(40)		NULL,       -- 仓库地址
	CONSTRAINT PK_compwarehouse PRIMARY KEY NONCLUSTERED(compno,warehouseno)	
)
go

---------------------------------------
-----warehousepurview   仓库负责人设定 CREATE by liujie 20130628
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='warehousepurview')
CREATE tAbLE    warehousepurview              
(
	compno			varchar(10) Not NULL,   -- 公司别
	warehouseno		varchar(10) Not NULL,   -- 仓库编号
	staffno			varchar(20) Not NULL,   -- 仓库负责人员工工号
	staffname		varchar(30) NULL    , 	-- 仓库负责人姓名	
	CONSTRAINT PK_warehousepurview PRIMARY KEY CLUSTERED(compno,warehouseno,staffno)
)
go


---------------------------------------
-----staffinfo   门店员工资料 CREATE by liujie 20130628
---------------------------------------

if not exists(select 1 from sysobjects where type='U' and name='staffinfo')
CREATE tAbLE    staffinfo              
(
	compno			varchar(10)		Not NULL,   -- 公司别
	staffno			varchar(20)		Not NULL,   -- 员工编号
	staffname		varchar(20)		NULL    , 	-- 员工姓名
	staffename		varchar(20)		NULL    , 	-- 英文名
	staffsex		int				NULL    ,--性别(1,男;0女)        
	department		varchar(10)		NULL    ,--部门编号
	position		varchar(20 )	NULL    ,--职位
	arrivaldate		varchar(8 )		NULL    ,--到职日期
	leavedate		varchar(8 )		NULL    ,--离职日期
	contractdate	varchar(8 )		NULL    ,--合约到期日
	pccid			varchar(20)		NULL    ,--身份证号
	educational		varchar(5 )		NULL    ,--最高学历(编号)常用资料
	birthdate		varchar(8  )	NULL    ,--出生日期
	height			float			NULL    ,--身高(公分)
	bodyweight		float			NULL   ,--体重(公斤)
	aaddress		varchar(160)	NULL    ,--联络地址
	qqno			varchar(20 )	NULL    ,--QQ号码
	mobilephone		varchar(20 )	NULL    ,--行动电话
	email			varchar(20 )	NULL    ,--Email
	healthno		varchar(20 )	NULL    ,--健康证号码
	healthdate		varchar(20 )	NULL    ,--健康证有效期
	curstate		varchar(1)		NULL    ,--目前状况(1-未到职 ,2-到职 ,3 -离职)
	socialsecurity  float			NULL    ,-- 社保
	socialsource    varchar(10)		NULL    ,-- 社保所属公司
	remark			varchar(200)	NULL    ,--工资批注
	staffmark		varchar(200)	null	,--员工批注
	searchpassword	varchar(10)		NULL    ,--查询密码
	staffpassword	varchar(30)		NULL    ,--员工卡密码 ,Added by MZH, 2005/08/02	 ---经理签单的密码							
	manageno		varchar(20)		NULL    ,--内部管理号
	reservecontect	varchar(30)		NULL    ,--紧急联系人     
	reservephone	varchar(30)		NULL    ,--紧急联系人电话 
	reserveaddress  varchar(160)	NULL    ,--紧急联系人地址     
	introductioner  varchar(20)		NULL    ,--介绍人
	leveltype		int				NULL    ,--离职类型 1 正常离职 2 自动离职
	basesalary		float			NULL    ,--基本工资
	businessflag	int				NULL    ,--是否为业务人员 0--不是 1--是
	banktype		varchar(5)		NULL    ,--银行卡类型
	bankno			varchar(30)		NULL    ,--银行卡号
    resulttye		varchar(5)		NULL    ,--业绩方式 0--额度方式 -美发虚业绩  2 美发实业绩
	resultrate		float			NULL    ,--业绩系数
    baseresult		float			NULL    ,--业绩基数
    salaryflag      int				NULL    ,--(工资)税前税后0税前  1税后
    fingerno		int				NULL	,--指纹编号
    fingernotext	varchar(500)	NULL	,--指纹文件
    positiontitle   varchar(20) NULL,
	CONSTRAINT PK_staffinfo PRIMARY KEY CLUSTERED(compno,staffno)
)
go
CREATE NONCLUSTERED index idx_staffinfo_comp on staffinfo(compno,staffno)
go
CREATE NONCLUSTERED index idx_staffinfo_manager on staffinfo(manageno)
go




---------------------------------------
-----staffinfodispatch   门店员工派遣表 CREATE by liujie 20130628
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='staffinfodispatch')
CREATE tAbLE    staffinfodispatch              
(
	seqno			int identity		Not NULL,   --序号
	manageno		varchar(20)			NULL,		--内部管理编号 
	oldcompid		varchar(10)         NULL,		--老门店编号 
	newcompid		varchar(10)         NULL,		--老门店编号 
	oldempid		varchar(20)         NULL,		--老员工编号
	olddepid		varchar(10)			NULL,		--老部门
	oldpostion		varchar(10)         NULL,		--老门店职位
    oldyjtype		varchar(5)          NULL,		--原业绩方式
    oldyjrate		float               NULL,		--原业绩系数
    oldyjamt		float               NULL,		--原业绩基数
    effectivedate	varchar(8)          NULL,		--实际派遣日期
    teffectivedate	varchar(8)          NULL,		--实际派遣日期
    dispatchstate	int					NULL,		--派遣状态     0 登记  1 专员审核 2 人事经理审核 3人事经理驳回
    checkinheadcompid		varchar(10)			null,	--总部审核公司
	checkinheadstaffid		varchar(20)			null,	--总部审核人
	checkinheaddate			varchar(8)			null,	--总部审核日期
	comfirmcompid			varchar(10)			null,	--人事审核公司
	comfirmstaffid			varchar(20)			null,	--人事审核人
	comfirmdate				varchar(8)			null,	--人事审核日期
	CONSTRAINT PK_staffinfodispatch PRIMARY KEY NONCLUSTERED(seqno)
)


-----staffabsenceinfo   门店员工缺勤表 CREATE by liujie 20130628
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='staffabsenceinfo')
CREATE tAbLE    staffabsenceinfo              
(
	seqno			int identity		Not NULL,		--序号
	manageno		varchar(20)				NULL,		--内部管理编号 
	compid			varchar(10)				NULL,		--门店编号 
	empid			varchar(20)				NULL,		--员工编号
    absencedate		varchar(8)				NULL,		--缺勤日期
	CONSTRAINT PK_staffabsenceinfo PRIMARY KEY NONCLUSTERED(seqno)
)


---------------------------------------
-----staffinfo   门店员工异动历史 CREATE by liujie 20130628
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='staffhistory')
CREATE tAbLE    staffhistory              
(
	seqno			int identity    Not NULL,   --序号
	manageno		varchar(20)      NULL,   --内部管理编号 
    changetype		varchar(20)         NULL,   --异动类型 0本店调动  1：跨店调动  2：薪资调整  3:员工离职 4：入职申请  5：重回公司
	oldcompid		varchar(10)         NULL,   --老门店编号 
	oldempid		varchar(20)         NULL,   --老员工编号
	olddepid		varchar(10)			null,	--老部门
	oldpostion		varchar(10)          NULL,   --老门店职位
    oldsalary		float               NULL,   --原工资
    oldyjtype		varchar(5)          NULL,   --原业绩方式
    oldyjrate		float               NULL,   --原业绩系数
    oldyjamt		float               NULL,   --原业绩系数
	newcompid		varchar(10)         NULL,   --新门店编号 
	newempid		varchar(20)         NULL,   --新员工编号
	newdepid		varchar(10)			null,	--新部门
	newpostion		varchar(5)          NULL,   --新门店职位
    newsalary		float               NULL,   --新工资
    newyjtype		varchar(5)          NULL,   --新业绩方式
    newyjrate		float               NULL,   --新业绩系数
    newyjramt		float               NULL,   --新业绩系数
    effectivedate	varchar(8)          NULL,   --实际生效日期
	optionbill		varchar(20)			null,	--单据编号
	changemark      varchar(300)			NULL,
	CONSTRAINT PK_staffhistory PRIMARY KEY NONCLUSTERED(seqno)
)

---------------------------------------
-----commoninfo   固有资料 CREATE by liujie 20130708
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='commoninfo')
CREATE tAbLE    commoninfo
(
	infotype		varchar(5)		Not NULL,	--固有资料类型
	infoname		varchar(40)		NULL,	--固有资料名称
	parentcodekey	varchar(10)		Not NULL,
	parentcodevalue	varchar(40)		NULL,
	codekey			varchar(10)		Not NULL,	--固有资料键	
	codevalue		varchar(40)		NULL,	--固有资料值
	codesource		varchar(10)		NULL,	--默认来源 D (default) 其他个性为门店
	CONSTRAINT PK_commoninfo PRIMARY KEY CLUSTERED(infotype,parentcodekey,codekey)
)		


---------------------------------------
-----projectinfo   项目资料 CREATE by liujie 20130708
---------------------------------------

if not exists(select 1 from sysobjects where type='U' and name='projectinfo')
CREATE tAbLE    projectinfo
(
	prjmodeId			varchar(10)     Not NULL,   --项目模板编号
	prjno				varchar(20)		Not NULL,   --项目编号 
	prjname				varchar(50)		NULL    ,   --项目简称
	prjtype				varchar(5)		NULL    ,   --项目类别
	prjpricetype		int 			NULL	,   ---项目项别--常用资料类别  1大项 2小项
	prjreporttype		varchar(10)		NULL    ,   --统计分类 
	saleunit			varchar(5)		NULL    ,   --计价单位
	saleprice			float			NULL    ,   --标准价格
	msalecount			float			NULL,       -- 价格1次数,月
	msaleprice			float			NULL,       -- 价格1金额,月
	rsalecount			float			NULL,       -- 价格2次数,季度
	rsaleprice			float			NULL,       -- 价格2金额,季度
	hsalecount			float			NULL,       -- 价格3次数,半年
	hsaleprice			float			NULL,       -- 价格3金额,半年
	ysalecount			float			NULL,       -- 价格4次数,年
	ysaleprice			float			NULL,       -- 价格4金额,年
	onecountprice		float			NULL		,   --单次价
	onepageprice		float			NULL		,   --体验价格(散客价)
	memberprice		    float			NULL		,   --体验价格(会员价)
	salelowprice		float			NULL		,   --最低价
	needhairflag		int				NULL		,	--是否需要洗头费	 1是  2否
	useflag				int				NULL		,   --启用标志			 1正常 2停用
	saleflag			int				NULL		,	--购买是否启售   1是  2否
	rateflag			int				NULL		,	--购买是否打折		 1是  2否
	prjsaletype			int				NULL		,	--是否是疗程		 1是  2否
	editflag			int				NULL,			-- 可否编辑价格		 1是  2否
	pointtype			int				NULL		, -- 积分方式
	pointvalue			float			NULL,       -- 积分或比率
	costtype			int				NULL,     -- 项目是否必须现金支付，1为必须现金支付，0为未必
	costprice			float			NULL,       -- 成本
	kyjrate				float			NULL,       -- 业绩比率
	ktcrate				float			NULL,       -- 工资比率
	lyjrate				float			NULL,       -- 疗程业绩比率
	ltcrate				float			NULL,       -- 疗程工资比率
	finaltype			int				NULL,       -- 固有资料  0 附加 1固有
	prisource			varchar(10)		 Not  NULL,	--项目来源
	prjabridge			varchar(10)		NULL,		--项目缩写
	CONSTRAINT PK_projectinfo PRIMARY KEY CLUSTERED(prjmodeId,prjno,prisource)
)


go
CREATE NONCLUSTERED index idx_projectinfo_prjno on projectinfo(prjno)


---------------------------------------
-----goodsinfo   产品资料 CREATE by liujie 20130708
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='goodsinfo')
CREATE tAbLE    goodsinfo               -- 产品资料档
(
	goodsmodeid			varchar(10)     Not NULL,   -- 产品模板编号
	goodsno				varchar(20)		Not NULL,   -- 产品编号
	goodsuniquebar		varchar(10)		NULL,       --唯一码前缀
	goodsbarno			varchar(100)		NULL,       -- 条码编号
	goodsname			varchar(80)		NULL,       -- 产品名称
	CREATEdate			varchar(8)		NULL,       -- 建档日期
	goodstype			varchar(5)		NULL,       -- 产品大类
	goodspricetype		varchar(10)		NULL,		-- 统计分类 
	goodsappsource		int				NULL,		-- 0的时候是仓库 1--是供应商
	goodswarehouse		varchar(10)		NULL,		-- 直属中心仓库编号
	goodssupplier		varchar(5)		NULL,       -- 产品供应商
	costunit			varchar(5)		NULL,       -- 消耗单位
	saleunit			varchar(5)		NULL,       -- 销售单位(标准单位)	
	purchaseunit		varchar(5)		NULL,		-- 进货单位
	goodsformat			varchar(20)		NULL,       -- 产品规格
	saletocostcount		float			NULL,       -- 销售单位对应消耗单位数量
	purtocostcount		float			NULL,       -- 进货单位对应销售单位数量
	purchaseprice		float			NULL,       -- 产品进价(标准单位)	--总部进货价(入库)
	costamtbysale		float			NULL,       -- 销售成本(标准单位)   --门店进货价(总部出库/发货)
	standprice			float			NULL,       -- 标准价格(销售单位)
	storesalseprice		float			NULL,       -- 总部销售门店价格		--门店销售顾客价
	shelflife			int				NULL,       -- 保质期
	lowstock			float			NULL,       -- 安全存量(标准单位)
	heightstock			float			NULL,       -- 最高存量		
	appflag				int				NULL,       -- 是否允许采购
	useflag				int				NULL,       -- 是否停用
	goodsusetype		int				NULL,       -- 产品使用性质 1卖品  2非卖品
    stopdate			varchar(8)		NULL,       -- 停止采购日期
	stopmark			varchar(100)	NULL,		-- 停止采购原因	 
	pointtype			int				NULL,       -- 积分方式
	pointvalue			float			NULL,       -- 积分或比率
	yetype				int				NULL,       -- 业绩方式
	yevalue				float			NULL,       -- 金额或比率
	tctype				int				NULL,       -- 提成方式
	tcvalue				float			NULL,       -- 金额或比率
	finaltype			int				NULL,       -- 固有资料  0 附加 1固有
	goodssource			varchar(10)		Not NULL,		-- 产品来源
	goodsabridge		varchar(10)		NULL,		--产品缩写
	minordercount		float NULL,
	CONSTRAINT PK_goodsinfo PRIMARY KEY CLUSTERED(goodsmodeid,goodsno,goodssource)
)



go
if not exists(select 1 from sysobjects where type='U' and name='goodsnameinfo')
CREATE tAbLE    goodsnameinfo
(
	goodsno					varchar(20)		Not NULL,		--产品编号 
	goodsbarno				varchar(40)			NULL    ,   --条码编号
	goodsname				varchar(80)			NULL    ,   --产品名称
	goodstype				varchar(5) 			NULL	,   --产品大类
	goodspricetype			varchar(5)			NULL    ,   --统计分类
	maxbarcode				varchar(20)			NULL,		--产品最大条码 
	CONSTRAINT PK_goodsnameinfo PRIMARY KEY CLUSTERED(goodsno)
)



if not exists(select 1 from sysobjects where type='U' and name='projectnameinfo')
CREATE tAbLE    projectnameinfo
(
	prjno				varchar(20)		Not NULL,   --项目编号 
	prjname				varchar(50)		NULL    ,   --项目简称
	prjtype				varchar(5)		NULL    ,   --项目类别
	prjpricetype		int 			NULL	,   ---项目项别--常用资料类别  1大项 2小项
	prjreporttype		varchar(10)		NULL    ,   --统计分类 
	CONSTRAINT PK_projectnameinfo PRIMARY KEY CLUSTERED(prjno)
)


if not exists(select 1 from sysobjects where type='U' and name='cardtypenameinfo')
CREATE tAbLE    cardtypenameinfo
(
	cardtypeno			varchar(10)		Not NULL,   --类别代码
	cardtypename		varchar(30)		NULL       --类别名称 
)

---------------------------------------
-----cardtypeinfo   会员卡类别设定 CREATE by liujie 20130708
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='cardtypeinfo')
CREATE tAbLE    cardtypeinfo               -- 
(
	cardtypemodeid		varchar(10)     Not NULL,   -- 类别模板编号
	cardtypeno			varchar(10)		Not NULL,   --类别代码
	cardtypename		varchar(30)		NULL    ,   --类别名称 
	cardusetype			int				NULL    ,   --大类(1-储值卡, 2- 资格卡,3-计次卡,4-单次卡)
	cardchiptype		int				NULL    ,   --卡介质类型 0磁卡 1Ic卡，
	carduselife			float			NULL    ,   --有效期限
	cardsaleprice		float			NULL    ,   --标准售价
	cardcost			float			NULL    ,   --成本
	saletctype			int				NULL    ,   --卡销售提成方式
	saletcvalue			float			NULL    ,   --卡销售金额或比率	
	saleyjtype			int				NULL    ,   --卡销售业绩方式
	saleyjvalue			float			NULL    ,   --卡销售金额或比率
	fillyjtype			int				NULL    ,   --卡充值业绩方式
	fillyjvalue			float			NULL    ,   --卡充值金额或比率
	filltctype			int				NULL    ,   --卡充值提成方式
	filltcvalue			float			NULL    ,   --卡充值金额或比率
	pointtype			int				NULL    ,   --赠送积分的方式
	prjpointvalue		float			NULL    ,   --项目消费积分折算比率
	goodspointvalue		float			NULL    ,   --产品消费积分折算比率
	lowfillamt			float			NULL    ,   --最低冲值额度
	lowopenamt			float			NULL    ,   --买卡的时候最低充值额度
	salegoodsflag		int  			NULL    ,   --是否可以购买物品1:'是',0:'否'
	slaeproerate		float  			NULL    ,   --购买疗程折扣
	slaegoodsrate		float  			NULL    ,   --购买产品折扣
	changerule			int	  			NULL    ,   --转卡规则 1:'标准转卡',0:'余额转卡' 
	openflag			int				NULL default(1), --允许开卡 1:'是',0:'否'
    fillflag			int				NULL default(1),  -- 允许充值 1:'是',0:'否'
    changeflag			int				NULL default(1), --允许转卡 1:'是',0:'否'
    finaltype			int				NULL,       -- 固有资料  0 附加 1固有
    cardtypesource		varchar(10)		Not NULL,		--卡类型来源
	CONSTRAINT PK_cardtypeinfo PRIMARY KEY CLUSTERED(cardtypemodeid,cardtypeno,cardtypesource)
)
go 


---------------------------------------
-----cardchangerule   转卡规则 CREATE by liujie 20130708
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='cardchangerule')
CREATE table cardchangerule
(
	rulemodeid		varchar(10)         not null, --类别模板编号
	cardtypeno		varchar(20)			not null, --会员卡类别
	seqno			float				not null, --序号
	tocardtypeno	varchar(20)			null,     --转卡类别
	changeamt		float				null,     --转卡最低金额
	cardtypesource	varchar(10)		NULL,		--卡类型来源
	CONSTRAINT	PK_cardchangerule PRIMARY key CLUSTERED(rulemodeid,cardtypeno,seqno)
)
go

---------------------------------------
-----cardchangecostrate   会员卡消费折扣 CREATE by liujie 20130708
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='cardchangecostrate')
CREATE table cardchangecostrate
(
	compid				varchar(10)					not null,		--门店编号
	projecttypeid		varchar(10)					not null,		--项目大类
	cardtypeno			varchar(10)					not null,		--卡类型编号
	acounttypeno		varchar(10)					not null,		--账户类型编号
	startdate			varchar(8)						null,		--起始日期
	enddate				varchar(8)						null,		--结束日期
	costrate			float(10)						null,		--项目折扣
	CONSTRAINT	PK_cardchangecostrate PRIMARY key CLUSTERED(compid,projecttypeid,cardtypeno,acounttypeno)
)
go


---------------------------------------
-----cardcostgoodsrate   会员卡消费折扣 CREATE by liujie 20130708
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='cardcostgoodsrate')
CREATE table cardcostgoodsrate
(
	compid				varchar(10)					not null,		--门店编号
	goodstypeid		varchar(10)					not null,		--项目大类
	cardtypeno			varchar(10)					not null,		--卡类型编号
	acounttypeno		varchar(10)					not null,		--账户类型编号
	startdate			varchar(8)						null,		--起始日期
	enddate				varchar(8)						null,		--结束日期
	costrate			float(10)						null,		--项目折扣
	CONSTRAINT	PK_cardcostgoodsrate PRIMARY key CLUSTERED(compid,goodstypeid,cardtypeno,acounttypeno)
)
go



---------------------------------------
-----sysparaminfo   系统参数 CREATE by liujie 20130708
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='sysparaminfo')
CREATE table sysparaminfo
(
	compid			varchar(10)			not null, --门店号
	paramid			varchar(10)			not null, --参数编号
	paramname		varchar(100)		null,	  --参数值
	paramvalue		varchar(100)		null,	  --参数值
	parammark		varchar(50)			null,	  --参数值备注
	CONSTRAINT	PK_sysparaminfo PRIMARY key CLUSTERED(compid,paramid)
)
go

---------------------------------------
-----supplierinfo   供应商基本资料 CREATE by liujie 20130708
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='supplierinfo')
CREATE tAbLE supplierinfo   --供应商基本资料
(
   supplierid			varchar(20)			Not NULL,--供应商ID
   suppliername			varchar(40)			NULL,--供应商名称
   suppliersname		varchar(30)			NULL,--供应商简称
   supplierphone		varchar(20)			NULL,--电话
   supplierfex			varchar(20)			NULL,--传真
   supplieremail		varchar(20)			NULL,--电子邮件
   supplierurl			varchar(20)			NULL,--主页
   supplieraddress      varchar(80)			NULL,--供应商发票地址
   supplierpos			varchar(20)			NULL,--邮政编码
   supplierremark		varchar(40)			NULL,--供应商备注 
   miantoucher			varchar(40)			NULL,--主要联系人姓名
   supplierpassword     varchar(40)			NULL,--供应商密码
   suppliermobilephone 	varchar(20)  		NULL,--手机号码
   supplierstate   		int  				NULL,--0.没有合作，1正在合作
   CONSTRAINT PK_supplierinfo PRIMARY KEY CLUSTERED(supplierid)
)
go

---------------------------------------
-----promotionsinfo   门店促销设定 CREATE by liujie 20130708
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='promotionsinfo')
CREATE tAbLE    promotionsinfo             -- 
(
	compid				varchar(10)   Not NULL,			--公司编号
    billid				varchar(20)   Not NULL,			--单号
	promotionstype		int			  NULL,				--项目类别或项目标识，1-项目单价，2-开卡金额 3充值金额 ,4-单卡开卡金额 5单卡充值金额 
	promotionscode		varchar(20)   NULL,				--项目编号或卡类型或卡号
    promotionsstore		varchar(10)   NULL,				--促销门店
    promotionsvalue		float		  NULL,				--促销价格
	startdate			varchar(10)   NULL,				--折扣开始日期
	enddate				varchar(10)   NULL,				--折扣截止日期
    promotionsreason	varchar(200)  NULL,				--促销原因
	promotionsstate		int			  NULL,				--是否审核，0-没审核，1-已审核    
	promotionsempid		varchar(10)	  NULL,				--审核人编码
	promotionsdate		varchar(10)   NULL,				--审核日期
	invalid				int			  NULl,				--是否作废 0 未作废  1 已作废
	CONSTRAINT PK_promotionsinfo PRIMARY KEY CLUSTERED(compid,billid)
)	
go
CREATE NONCLUSTERED index idx_promotionsinfo_promotionstype on promotionsinfo(promotionstype,promotionscode)




---------------------------------------
-----syscommoninfomode 常用信息模板    CREATE by liujie 20130708
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='syscommoninfomode')
CREATE tAbLE    syscommoninfomode             -- 
(
	modeid				varchar(10)   Not NULL,			--模板编号
    modetype			int			  Not NULL,			--模板类型   1 项目模板  2产品模板  3会员卡类型模板 4薪资模板
	modename 			varchar(40)	  NULL,				--模板名称
	modesource			varchar(20)   NULL,				--模板归属
    parentmodeid		varchar(10)	  NULL,				--继承模板编号
    CREATEdate			varchar(10)	  NULL,				--建模日期
    CREATEemp			varchar(20)	  NULL,				--建模员工
    invalid				int			  NULl,				--是否作废 0 未作废  1 已作废
	CONSTRAINT PK_syscommoninfomode PRIMARY KEY CLUSTERED(modeid,modetype)
)	
go

---------------------------------------
-----mcardnoinsert 会员卡入库单主档
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='mcardnoinsert')
CREATE table mcardnoinsert
(
	cinsertcompid			varchar(10)		not null,	--入库公司
	cinsertbillid			varchar(20)		not null,	--入库单号
	cinsertdate				varchar(8)      null,		--入库日期
	cinserttime				varchar(8)      null,		--入库日期
	cinsertware				varchar(10)     null,		--入库仓库
	CREATEcompname			varchar(60)     null,		--卡制作公司
	checkoutflag			int				null, --是否结帐 0--没有结帐 1--结帐
	billflag				int				null, --是否开票 0--不需要开票 1--需要开票
	billno					varchar(50)     null, --发票编号
	checkoutmark			varchar(120)    null, --结帐备注
	checkoutimgurl			varchar(120)    null, --结帐图片地址
	reportimage				image           NULL,   --报表照片
	cinsertper				varchar(20)     null,		--验货人
	optionconfrimdate		varchar(10)     null, --复核日期
	optionconfrimper		varchar(20)     null, --复核人
	optioncanceldate		varchar(10)     null, --取消复核日期
	optioncancelper			varchar(20)     null, --取消复核人
	invalid					int				null, --是否作废 0 正常 1 作废
	CONSTRAINT	PK_mcardnoinsert PRIMARY key CLUSTERED(cinsertcompid,cinsertbillid)
)
go


---------------------------------------
-----mcardnoinsert 会员卡入库单明细
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='dcardnoinsert')
CREATE table dcardnoinsert
(
	cinsertcompid   varchar(10)		not null, --入库公司
	cinsertbillid	varchar(20)		not null, --入库单号
	seqno			float			not null, --流水号
	cardtypeid		varchar(10)     null, --卡类别
	cardnofrom		varchar(20)     null, --卡代码开始号码
	cardnoto		varchar(20)     null, --卡代码结束号码
	cardnum			float			null, --卡数量
	cardprice		float			null, --卡单价
	cardamt			float			null, --卡金额
	CONSTRAINT	PK_dcardnoinsert PRIMARY key CLUSTERED(cinsertcompid,cinsertbillid,seqno)	
)
go



---------------------------------------
-----cardstock 会员卡号段库存
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='cardstock')
CREATE table cardstock
(
	rid				int identity(1,1)		not null,
	cardclass		varchar(10)				null,	-- 卡类别
	cardfrom		varchar(20)				null,	-- 起始卡号
	cardto			varchar(20)				null,	-- 截止卡号
	ccount			float					null,	-- 该范围内卡数量
	storage			varchar(10)				null,	-- 仓库编号
	compid			varchar(10)				null,   -- 公司编号
	CONSTRAINT PK_cardstock PRIMARY key CLUSTERED(rid)
)
go


---------------------------------------
-----cardstockchange 会员卡进出库历史 
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='cardstockchange')
CREATE table cardstockchange          --会员卡库存日异动统计明细档(所有异动单据明细 update) 
(
	changecompid		varchar(10)			Not NULL,		-- 会馆别       
	changetype			varchar(2)			Not NULL,		-- 异动别 (1-入库,2-领用)                     
	changebill			varchar(20)			Not NULL,		-- 异动单号     
	changeseqno			float				Not NULL,		-- 输入流水号       
	cardtype			varchar(30)				NULL,   	-- 会员卡类别     
	changecardfromno	varchar(20)				NULL,       -- 会员卡开始卡号     
	changecardtono		varchar(20)				NULL,       -- 会员卡结束卡号              
	changecount			float			  		NULL,       -- 会员数量        
	changeprice			float			  		NULL,       -- 会员单价    
	changeamt			float			  		NULL,       -- 会员金额       
	changedate			varchar(8)				NULL,		-- 日期
	changeware			varchar(10)				NULL,      	--仓库编号
	CONSTRAINT PK_cardstockchange PRIMARY KEY CLUSTERED (changecompid, changetype, changebill, changeseqno)
)
go

---------------------------------------
-----mcardapponline 在线卡申请主档
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='mcardapponline')
CREATE table mcardapponline
(
	cappcompid				varchar(10)		not null ,--申请公司
	cappcompbillid			varchar(20)		not null, --申请单号
	cappdate				varchar(8)		null,     --申请日期
	capptime				varchar(6)		null,     --申请时间
	cappempid				varchar(20)		null,     --申请人
	cappbillflag			int				null,     --流程标志  0--申请中 1--总部同意 2--已经领用
	cappopationper			varchar(20)		null,     --操作人
	cappopationdate			varchar(8)		null,	  --操作日期
	cappconfirmper			varchar(20)		null,     --总部复核人
	cappconfirmdate			varchar(8)		null,     --总部复核日期
	cappconfirmcompid		varchar(10)		null,     --审核公司
	invalid					int				null,	  --是否作废 0 正常 1 作废
	CONSTRAINT	PK_mcardapponline PRIMARY key CLUSTERED(cappcompid,cappcompbillid)
)
go

---------------------------------------
-----dcardapponline 在线卡申请明细
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='dcardapponline')
CREATE table dcardapponline
(
	cappcompid			varchar(10)  not null ,--申请公司
	cappcompbillid		varchar(20)  not null, --申请单号
	cappseqno			float	     not null, --流水号
	cappcardtypeid		varchar(10)  null,     --卡类型
	cappcount			float		 null,     --数量
	CONSTRAINT	PK_dcardapponline PRIMARY key CLUSTERED(cappcompid,cappcompbillid,cappseqno)
)
go

---------------------------------------
-----mcardallotment 在线卡配发主档
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='mcardallotment')
CREATE table mcardallotment
(
	callotcompid			varchar(10)		not null ,--公司编号
	callotbillid			varchar(20)		not null, --单号
	callotdate				varchar(8)		null,     --配发日期
	callottime				varchar(6)		null,     --配发时间
	callotempid				varchar(20)		null,     --配发人
	recevieempid			varchar(20)		null,     --领用人
	callotopationempid		varchar(20)		null,     --操作人
	callotopationdate		varchar(8)		null,	  --操作日期
	checkoutflag			int 			null,     --是否结帐
	checkoutdate			varchar(8)		null,     --结帐日期
	checkoutemp				varchar(20)		null,     --结帐人员
	cappbillid				varchar(20)		null,     --申请单号
	cappcompid				varchar(10)		null,     --申请单公司
	callotwareid			varchar(10)		null,     --出库仓库编号
	invalid					int				null,		--是否作废 0 正常 1 作废
	CONSTRAINT	PK_mcardallotment PRIMARY key CLUSTERED(callotcompid,callotbillid)
)
go


---------------------------------------
-----dcardallotment 在线卡配发明细
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='dcardallotment')
CREATE table dcardallotment
(
	callotcompid	varchar(10)		not null ,	--领用公司
	callotbillid	varchar(20)		not null,	--领用单号
	callotseqno		float			not null,	--流水号
	cardtypeid		varchar(10)			null,	--卡类别
	cardnofrom		varchar(20)			null,	--卡代码开始号码
	cardnoto		varchar(20)			null,	--卡代码结束号码
	ccount			float				null,	-- 该范围内卡申请数量
	allotcount		float				null,	-- 配发数量
	CONSTRAINT	PK_dcardallotment PRIMARY key CLUSTERED(callotcompid,callotbillid,callotseqno)
)

go


---------------------------------------
-----sysoperationlog 系统操作日志
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='sysoperationlog')
CREATE table    sysoperationlog        
(
		log_id      int IDENtItY(1,1),
		
		userid          varchar(10)     Not NULL,			--User ID    
		program         varchar(10)     Not NULL ,			--程式代码
		operation       varchar(1)      Not NULL,			--操作 
		operationdate   varchar(8)      Not NULL,           --异动日期
		operationtime   varchar(8)      Not NULL,           --异动时间
		origatedate     varchar(8)			NULL,           --单据作业时间.
		compid          varchar(10)			 NULL,			--company ID  
		keyvalue1       varchar(20)         NULL,
		keyvalue2       varchar(20)         NULL,
		keyvalue3       varchar(20)         NULL,
		keyvalue4       varchar(20)         NULL,
		CONSTRAINT PK_sysoperationlog_id PRIMARY KEY (log_id)
)

---------------------------------------
-----sysaccountforpaymode 系统账户与支付
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='sysaccountforpaymode')
CREATE table sysaccountforpaymode(
	paymode			varchar(5)  Not NULL, -- 支付方式
	accounttype		varchar(5)      NULL, -- 账户
	CONSTRAINT PK_sysaccountforpaymode PRIMARY KEY CLUSTERED(paymode)
)
go



---------------------------------------
----cardinfo----- 会员卡资料主档
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='cardinfo')
CREATE tAbLE    cardinfo              
(
	cardvesting			varchar(10)     Not NULL,   --卡归属门店 
	cardno				varchar(20)		Not NULL,	--卡号 
	cardtype			varchar(10)		NULL,		--卡种(会员卡类别设定) 
	membernotocard		varchar(20)		NULL    ,   --会员编号
	salecarddate		varchar(8)		NULL    ,   --售卡日期
	cutoffdate			varchar(8)		NULL    ,   --截止有效日期
	cardstate			int				NULL    ,   --状态(未销售, 销售未开卡, 正常使用中, 挂失转卡,越期可续卡 , 越期作废卡)
	salebillno			varchar(20)		NULL    ,   --销售单号
	costpassword		varchar(10)		NULL    ,   --消费密码
	searchpassword		varchar(10)		NULL    ,   --查询密码
	cardremark			varchar(180)	NULL    ,   --备注
	cardsource			int				NULL    ,   --卡的来源 0--本公司的卡 1--收购卡
	costcountbydebts	int				NULL    ,   --如果有欠款的时候 此栏位表示他还可以消费几次
	costamtbydebts		float			NULL    ,   --如果有欠款的时候 此栏位表示他可以消费的金额
	costamt				float			NUll	,	--欠款剩余消费额度
	CONSTRAINT PK_cardinfo PRIMARY KEY CLUSTERED(cardvesting,cardno)
)
go
CREATE NONCLUSTERED index idx_cardinfo_cardno on cardinfo(cardno)
go


CREATE table cardsoninfo
(
	cardvesting			varchar(10)			Not NULL,   --卡归属门店 
	cardno				varchar(20)			Not NULL,	--卡号 
	cardtype			varchar(10)			NULL,		--卡种(会员卡类别设定) 
	salecarddate		varchar(8)			NULL    ,   --售卡日期
	parentcardno		varchar(20)			NULL,		--父卡号 
	membername			varchar(20)			NULL,		--子卡姓名
	memberphone			varchar(20)			NULL,		--子卡手机号码
	salebillno			varchar(20)			NULL,		--销售单号
	saleamt				float				NULL,		--销售金额
	songfalg			varchar(10)			NULL,		--子卡标示
	CONSTRAINT PK_cardsoninfo PRIMARY KEY CLUSTERED(cardvesting,cardno)
)
go
CREATE NONCLUSTERED index idx_cardsoninfo_cardno on cardsoninfo(cardno)
go
CREATE NONCLUSTERED index idx_cardsoninfo_parentcardno on cardsoninfo(parentcardno)
go



CREATE table cardspecialcost
(
	cardno				varchar(20)			Not NULL,	--卡号 
	costxc1				float				NULL	,	--设计师洗吹
	costxc2				float				NULL    ,   --首席洗吹
	costxc3				float				NULL,		--总监洗吹 
	costxc4				float				NULL,		--创意总监洗吹
	costxc5				float				NULL,		--设计师洗吹
	costxc6				float				NULL,		--首席洗剪吹
	costxc7				float				NULL,		--总监洗剪吹
	costxc8				float				NULL,		--创意总监洗剪吹
	costxc9				float				NULL,		--店长洗剪吹
	CONSTRAINT PK_cardspecialcost PRIMARY KEY CLUSTERED(cardno)
)
go
CREATE NONCLUSTERED index idx_cardsoninfo_cardno on cardsoninfo(cardno)
go
CREATE NONCLUSTERED index idx_cardsoninfo_parentcardno on cardsoninfo(parentcardno)
go
---------------------------------------
----memberinfo----- 会员基本资料主档
---------------------------------------


if not exists(select 1 from sysobjects where type='U' and name='memberinfo')
CREATE tAbLE    memberinfo              
(
	membervesting			varchar(10)     Not NULL,		-- 公司编号
	memberno				varchar(20)		Not NULL,		-- 会员编号
	membercreatedate		varchar(8)			NULL,		-- 资料输入日期
	membername				varchar(40)			NULL,		-- 会员姓名
	memberaddress			varchar(160)		NULL,		-- address
	membertphone			varchar(20)			NULL,		-- 家庭tele No.
	membermphone			varchar(20)			NULL,		-- Mobile No.
	memberFax				varchar(20)			NULL,		-- Fax No.-1
	memberemail				varchar(40)			NULL,       -- E-Mail地址
	memberzip				varchar(6)			NULL,       -- 邮编
	membersex				int					NULL,       -- 性别(0- female, 1- male )
	memberpaperworkno		varchar(20)			NULL    ,   -- 证件编号 
	memberbirthday			varchar(8)			NULL    ,   -- 出生日期
	memberjob				varchar(30)			NULL    ,   -- 职业
	cardnotomemberno		varchar(20)			NULL    ,   -- 会员卡号
	memberqqno				varchar(20)			NULL	,   -- QQ号码
	membermsnno				varchar(60)			NULL	,   -- MsN号码
	membertype				varchar(2)			NULL	,   --会员类别
	weixinno				varchar(20) NULL,
	autopassword			varchar(20) NULL,
	CONSTRAINT PK_memberinfo PRIMARY KEY CLUSTERED(membervesting,memberno)
)
go
CREATE NONCLUSTERED index idx_memberinfo_memberno on memberinfo(memberno)
CREATE NONCLUSTERED index idx_memberinfo_cardnotomemberno on memberinfo(cardnotomemberno)
go
--会员资料修改表
if not exists(select 1 from sysobjects where type='U' and name='memberinfoedit')
CREATE tAbLE    memberinfoedit              
(
	seqno			int identity(1,1)	Not NULL,
	memberno				varchar(20)		Not NULL,		-- 会员编号
	membername				varchar(40)			NULL,		-- 会员姓名
	membermphone			varchar(20)			NULL,		-- Mobile No.
	memberpaperworkno		varchar(20)			NULL    ,   -- 证件编号 
	memberbirthday			varchar(8)			NULL    ,   -- 出生日期
	edituserno				varchar(20)			NULL	,	-- 修改人
	editusername			varchar(30)			NULL	,	-- 修改人
	editdate				varchar(10)			NULL	,	-- 修改日期
	edittime				varchar(10)			NULL	,	-- 修改时间
)
go


---------------------------------------
----cardaccount--- 会员卡--帐户
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='cardaccount')
CREATE tAbLE    cardaccount               
(
	cardvesting			varchar(10)     Not NULL,   --卡归属门店
	cardno				varchar(20)		Not NULL,	--卡号 
	accounttype			int				Not NULL,   --帐号类别(常用资料)
	accountbalance		float			NULL	,	--账户余额
	accountdebts		float           NULL,		--帐户欠款
	accountdatefrom		varchar(20)     NULL,		--账号开立日期
	accountdateend		varchar(20)     NULL,		--账号截至日期
	accountremark		varchar(60)     NULL,		--账号备注
	CONSTRAINT PK_cardaccount PRIMARY KEY CLUSTERED(cardvesting,cardno,accounttype)
)
go
CREATE NONCLUSTERED index idx_cardaccount_cardno on cardaccount(cardno)
go


---------------------------------------
----cardproaccount--- 会员卡疗程帐户
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='cardproaccount')
CREATE tAbLE    cardproaccount               -- 会员卡--疗程
(
	cardvesting			varchar(10)     Not NULL,   --卡归属门店 
	cardno				varchar(20)		Not NULL,   --卡号 
	projectno			varchar(20)		Not NULL,   --疗程编号
	proseqno			float			Not NULL,   --疗程序号
	propricetype		int					NULL,   --疗程类型
	salecount			float				NULl,	--购买总次数
	costcount			float				NULL,   --已经使用次数
	lastcount			float				NULL,   --剩余次数
	saleamt				float				NULL,   --疗程金额
	costamt				float				NULL,   --已经使用金额
	lastamt				float				NULL,   --剩余金额
	saledate			varchar(8)			NULL,   --账户开立日
	cutoffdate			varchar(8)			NULL,   --账户截至日
	proremark			varchar(60)			NULL,   --疗程备注
	prostopeflag		int					NULL,   --是否停用
	exchangeseqno		float				null,	--疗程兑换序号
	changecompid		varchar(10)			NULL,   --兑换门店 
	changebillid		varchar(20)			NULL,   --兑换单号
	createbilltype		varchar(10)			NULL,   --生成门店
	createbillno		varchar(20)			NULL,   --生成单号
	createseqno			float				NULL,
	CONSTRAINT PK_cardproaccount PRIMARY KEY CLUSTERED(cardvesting,cardno,projectno,proseqno)
)
go
CREATE NONCLUSTERED index idx_cardproaccount_cardno on cardproaccount(cardno)
go
CREATE NONCLUSTERED index idx_cardproaccount_createbill on cardproaccount(cardno,createbillno,createbilltype)
go


---------------------------------------
----cardchangehistory--- 会员卡异动历史
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='cardchangehistory')
CREATE tAbLE    cardchangehistory             
(
	changecompid		varchar(10)     Not NULL,   --异动 门店
	changecardno		varchar(20)		Not NULL,   --异动卡号 
	changeseqno			float			Not NULL,   --异动序号
	changetype			int					NULL    ,   --异动性质(2- 开卡, 3-转卡, 4- 续卡 ,5-退卡) 
	changebillid		varchar(20)			NULL    ,   --异动单号 
	beforestate			int					NULL    ,   --异动前状态  
	afterstate			int					NULL    ,   --异动後状态
	chagedate			varchar(8)			NULL    ,   --异动日期
	targetcardno		varchar(20)			NULL    ,   --对应卡号
	CONSTRAINT PK_cardchangehistory PRIMARY KEY CLUSTERED(changecompid,changecardno,changeseqno)
)
go 
CREATE NONCLUSTERED index idx_cardchangehistory_changecardno on cardchangehistory(changecardno)
go


---------------------------------------
----cardaccountchangehistory--- 会员卡账户历史
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='cardaccountchangehistory')
CREATE tAbLE    cardaccountchangehistory     
(
	changecompid		varchar(10)     Not NULL,   --公司编号 
	changecardno		varchar(20)		Not NULL,   --卡号 
	changeaccounttype	int				Not NULL,   --帐号类别(1-电子钱包 , 2-储值帐号)
	changeseqno			int				Not NULL,   --帐号序号
	changetype			int             NULL,   --异动类别(0- 充值 ,1-取款 2-消费 ,3-转入, 4-转出)
	changeamt			float           NULL,   --异动金额
	changebilltype		varchar(5)      NULL,   --异动单据类别
	changebillno		varchar(20)     NULL,   --异动单号
	chagedate			varchar(8)      NULL,   --异动日期
	changebeforeamt		float			NULL,   --前次余额
	CONSTRAINT PK_cardaccountchangehistory PRIMARY KEY CLUSTERED(changecompid,changecardno,changeaccounttype,changeseqno)
)
go
CREATE NONCLUSTERED index idx_cardaccountchangehistory_changecardno on cardaccountchangehistory(changecardno)
go
---------------------------------------
----cardtransactionhistory--- 会员卡交易历史
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='cardtransactionhistory')
CREATE tAbLE    cardtransactionhistory             
(
	transactioncompid		varchar(10)			Not NULL,   --交易编号 
	transactionseqno		int identity		Not NULL,   --交易序号 
	transactioncardno		varchar(20)				NULL,   --卡号 
	transactiondate			varchar(8 )				NULL,   --日期
	transactiontype			varchar(10)				NULL,   --代码的类别--1-卡销售 2-疗程销售 3-项目 4-产品销售 - 5卡充值 - 6疗程充值
	codeno					varchar(20)				NULL,   --代码
	codename				varchar(50)				NULL,   --名称
	ccount					float					NULL,   --数量
	price					float					NULL,   --价格
	billtype				varchar(10)				NULL,   --单据类别
	billno					varchar(20)				NULL,   --单据单号
	firstempid				varchar(20)				NULL,   --员工1
	secondempid				varchar(20)				NULL,   --员工2
	thirthempid				varchar(20)				NULL,   --员工3
	paymode					varchar(10)				NULL,   --支付方式
	CONSTRAINT PK_cardtransactionhistory PRIMARY KEY NONCLUSTERED(transactioncompid,transactionseqno)
)
go
CREATE NONCLUSTERED index idx_cardtransactionhistory_transactioncardno on cardtransactionhistory(transactioncardno)
go

---------------------------------------
----mconsumeinfo--- 消费主档
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='mconsumeinfo')
CREATE tAbLE    mconsumeinfo  
(
	cscompid			varchar(10)     Not NULL,   --公司编号
	csbillid			varchar(20)		Not NULL,   --消费单号
	cskeyno				varchar(20)		NULL    ,   --匙牌号
	csmanualno			varchar(20)		NULL    ,   --手工小单
	csdate				varchar(8)		NULL    ,   --消费日期
	csstarttime			varchar(6)		NULL    ,   --消费开始时间
	csendtime			varchar(6)		NULL    ,   --消费结束时间
	cscardno			varchar(20)		NULL    ,   --会员卡号
	csname				varchar(20)		NULL    ,   --客户姓名，如果是会员则是会员姓名
	cscardtype			varchar(10)		NULL    ,   --卡类型
	csersex				int				NULL    ,  	--顾客性别
	csertype			int				NULL    ,   --是否为老客
	csercount			int				NULL    ,   --客人数量
	csopationerid		varchar(20)		NULL    ,   --操作收银员
	csopationdate		varchar(10)		NULL    ,   --操作日期
	financedate			varchar(8)		NULL    ,   --帐务日期 ,
	backcsflag			int				NULL    ,   --是否已经返销: 0-没有返销 1--已经返销
	backcsbillid		varchar(20)		NULL    ,   --返销单号
	cscurkeepamt		float			NULL	,	--消费当前余额(储值或收购账户)
	cscurdepamt			float			NULL	,	--消费当前欠款
	tuangoucardno		varchar(20)		NUlL	,	--团购卡号
	tiaomacardno		varchar(20)		NUlL	,	--条码卡号
	diyongcardno		varchar(20)		NUlL	,	--抵用券号
	reservationflag			int				NULL    ,   --是否预约: 0-没有预约 1--已经预约
	reserveStaffinfo	varchar(100)	NUlL	,	--预约员工
	CONSTRAINT PK_mconsumeinfo PRIMARY KEY NONCLUSTERED(cscompid,csbillid)
)
go 		 
CREATE NONCLUSTERED index idx_mconsumeinfo_cardno on mconsumeinfo(cscardno)
go
CREATE NONCLUSTERED index idx_mconsumeinfo_financedate on mconsumeinfo(financedate)
go
CREATE NONCLUSTERED index idx_mconsumeinfo_saledate on mconsumeinfo(cscompid,financedate)
INCLUDE ([csbillid],[cscardno],[cscardtype],[diyongcardno])
go



	
---------------------------------------
----dconsumeinfo--- 消费明细
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='dconsumeinfo')
CREATE tAbLE    dconsumeinfo  
(
	cscompid		varchar(10)     Not NULL,   --公司编号
	csbillid		varchar(20)		Not NULL,   --消费单号
	csinfotype		int				Not	NULL,	--消费类型  1 项目  2 产品
	csseqno			float			Not NULL,   --序列号
	csitemno		varchar(20)     NULL,		--项目/产品代码
	csitemunit		varchar(5)      NULL,		--单位
	csitemcount		float           NULL,		--数量
	csunitprice		float           NULL,		--销售单价
	csdiscount		float           NULL,		--折让
	csdisprice		float           NULL,		--折让单价
	csitemamt		float           NULL,		--金额
	cspaymode		varchar(5)		NULL,		--支付方式
	csfirstsaler	varchar(20)     NULL,		--大工工号
	csfirsttype		varchar(5)     NULL,		--大工类型
    csfirstinid		varchar(20)		NULL,		--大工内部编号
	csfirstshare	float           NULL,		--大工分享
	csfirstreserve	int				NULL,
	cssecondsaler	varchar(20)     NULL,		--中工工号
	cssecondtype	varchar(5)     NULL,		--中工类型
    cssecondinid	varchar(20)		NULL,		--中工内部编号
	cssecondshare	float           NULL,		--中工分享
	cssecondreserve	int				NULL,		
	csthirdsaler	varchar(20)     NULL,		--小工工号
	csthirdtype		varchar(5)		NULL,		--小工类型
    csthirdinid		varchar(20)		NULL,		--小工内部编号
	csthirdshare	float           NULL,		--小工分享
	csthirdreserve	int				NULL,		
	csortherpayid	varchar(30)		NULL,		--其他支付(现金抵用券,项目抵用券,条码卡,团购卡)
	csproseqno		float			NULL,		--疗程消耗序号
	saletype		int             NULL,		-- 购买标识 1 顾客，2 门店	
	goodsbarno		varchar(20)     NULL,		--产品条码
	CONSTRAINT PK_dconsumeinfo PRIMARY KEY NONCLUSTERED(cscompid,csbillid,csinfotype,csseqno)
)
go 	




---------------------------------------
----dpayinfo--- 支付明细
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='dpayinfo')
CREATE tAbLE    dpayinfo               --  单据--支付明细
(
	paycompid		varchar(10)     Not NULL,   --公司编号
	paybillid		varchar(20)		Not NULL,   --单据编号
	paybilltype		varchar(5)		Not NULL,   --单据类别  sY 收银  sK  售卡  cZ 充值 ZK  转卡 HZ 合作项目 tK 退卡
	payseqno		float			Not NULL,   --序号
	paymode			varchar(5)      NULL,   --支付方式
	payamt			float           NULL,   --支付金额
	payremark		varchar(30)     NULL,   --备注说明
	CONSTRAINT PK_dpayinfo PRIMARY KEY NONCLUSTERED(paycompid,paybillid,paybilltype,payseqno)
)
Go



---------------------------------------
----sendpointcard -- 赠送积分卡
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='sendpointcard')
CREATE tAbLE sendpointcard                  
(
	sendcompid			varchar(10)		Not NULL,		--赠送编号 
	sendbillid			varchar(20)		Not NULL,		--赠送单号
	sendtype			int				Not NULL,       --赠送类型(0- 开卡 ,1-充值  ,2-折扣转卡, 3-收购转卡，5-并卡 )
	senddate			varchar(10)		NULL,			--赠送日期
	sendempid			varchar(20)		NULL,			--赠送员工
	sourcebillid		varchar(20)     NULL,			--原始单号
	sourcecardno		varchar(20)     NULL,			--原单卡号
	sourcedate			varchar(10)     NULL,			--原单日期
	sourceamt			float		    NULL,			--原单金额
	sendcardno			varchar(20)     NULL,			--赠送卡号
	sendamt				float		    NULL,			--赠送金额
	sendmark			varchar(100)    NULL,			--备注
	operation			varchar(20)     NULL,			--操作人
	membername			varchar(20)     NULL,			--会员姓名
	memberphone			varchar(20)     NULL,			--会员手机号码
	sendrateflag		int				NULL,			--1,10%上，2 充新卡 15%下
	sendpicflag 		int				NULL,			--1 积分 , 2 条码卡
	picno				varchar(20)		NULL,			--项目抵用券卡号
	firstdateno			varchar(20)		NULL,			--日历抵用券号
	CONSTRAINT PK_sendpointcard PRIMARY KEY CLUSTERED(sendcompid,sendbillid,sendtype)
)
Go


---------------------------------------
----corpsbuyinfo -- 团购卡内容
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='corpsbuyinfo')
CREATE table corpsbuyinfo
(
	corpscardno		varchar(20)		not null,--团购卡号
	corpstype		int				not	null,--项目/卡类型 1 团购项目 2 团购卡金
	corpssource		varchar(10)		not	null,--团购类型 常用资料
	corpspicno		varchar(20)		not null,--项目/卡类型编号
	corpsamt		float			null	,--团购金额
	operationer		varchar(20)		null	,--登记人
	operationdate	varchar(10)		null	,--登记日期
	corpssate		int				null	,--团购号状态 1:未试用 2已使用
	useincompid		varchar(10)		null	,--使用门店
	useinbillno		varchar(20)		null	,--使用单号
	useindate		varchar(10)		null	,--使用日期
	CONSTRAINT PK_corpsbuyinfo PRIMARY KEY CLUSTERED(corpscardno)
)
go



---------------------------------------
----nointernalcardinfo -- 非系统内部卡信息 条码卡/项目抵用券/现金抵用券
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='nointernalcardinfo')
CREATE table nointernalcardinfo
(
	cardvesting		varchar(10)		not null,	--公司编号
	cardno			varchar(20)		not null,	--非内部卡号
	cardtype 		int 				null	,--卡类型 // 1 抵用券，2条码卡
	cardfaceamt		float				null	,--面值(抵用券有效)
	carduseflag		int 				null	,--使用类型 1 项目  2 现金 (抵用券有效,条码卡默认项目)
	entrytype		int 				null	,--登记类型  // 0  正常登记,1 赠送
	cardstate		int 				null	,--卡状态  //0 登记     1，正常使用  2 已用
	usedate			varchar(10) 		null	,--使用日期  
	useinproject	varchar(80) 		null	,--抵用项目(现金券有效)  
	oldvalidate		varchar(10) 		null	,--原始有效日期  
	lastvalidate	varchar(10) 		null	,--最新有效日期  
	enabledate		varchar(10)			null	,--启用日期
	uespassward		int					null	,--是否需要密码  -- 0 不需要 1 需要
	cardpassward	varchar(20)			null	,--消费密码
	CONSTRAINT PK_nointernalcardinfo PRIMARY KEY CLUSTERED(cardvesting,cardno)
)
go
CREATE NONCLUSTERED index idx_nointernalcardinfo_cardno on nointernalcardinfo(cardno)
go


---------------------------------------
----dnointernalcardinfo -- 非系统内部卡项目信息 条码卡/项目抵用券
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='dnointernalcardinfo')
CREATE table dnointernalcardinfo
(
	cardvesting		varchar(10)		not null,--公司编号
	cardno			varchar(20)		not null,--非内部卡号
	seqno			float			not null,--序号
	ineritemno		varchar(20)		null	,--项目
	entrycount		float			null	,--登记次数
	usecount		float			null	,--使用次数
	lastcount		float			null	,--剩余次数
	entryamt		float			null	,--登记余额
	useamt			float			null	,--使用余额
	lastamt			float			null	,--剩余余额
	entryremark	varchar(200)		null	,--备注
	CONSTRAINT PK_dnointernalcardinfo PRIMARY KEY CLUSTERED(cardvesting,cardno,seqno)
)
go
CREATE NONCLUSTERED index idx_dnointernalcardinfo_cardno on dnointernalcardinfo(cardno)
go

---------------------------------------
----msalebarcodecardinfo -- 系统条码卡销售主档
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='msalebarcodecardinfo')
CREATE table msalebarcodecardinfo
(
	salecompid			varchar(10)		not null	,--销售门店
	salebillid			varchar(20)		not null	,--销售单号
	saledate			varchar(8)			null	,--销售日期
	saletime			varchar(8)			null	,--销售时间
	operationer			varchar(20)			null	,--单据操作人
	barcodecardno		varchar(20)			null	,--销售条码卡卡号
	firstpaymode		varchar(20)			null	,--第一支付方式
	firstpayamt			float				null	,--第一支付金额
	secondpaymode		varchar(20)			null	,--第二支付方式
	secondpayamt		float				null	,--第二支付金额
	saleamt				float				null	,--销售总额
	firstsaleempid		varchar(20)			null	,--第一销售工号
	firstsaleempinid	varchar(20)			null	,--第一销售内部工号
	firstsaleamt		float				null	,--第一销售分享金额
	secondsaleempid		varchar(20)			null	,--第二销售工号
	secondsaleempinid	varchar(20)			null	,--第二销售内部工号
	secondsaleamt		float				null	,--第二销售分享金额
	thirdsaleempid		varchar(20)			null	,--第三销售工号
	thirdsaleempinid	varchar(20)			null	,--第三销售内部工号
	thirdsaleamt		float				null	,--第三销售分享金额
	salebakflag			int NULL,
	CONSTRAINT PK_msalebarcodecardinfo PRIMARY KEY CLUSTERED(salecompid,salebillid)
)
go



---------------------------------------                                                                                           
----dsalebarcodecardinfo -- 系统条码卡销售明细
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='dsalebarcodecardinfo')
CREATE table dsalebarcodecardinfo
(
	salecompid		varchar(10)		not null	,--销售门店
	salebillid		varchar(20)		not null	,--销售单号
	saleseqno		float			not null	,--销售序号
	saleproid		varchar(20)			null	,--条码卡项目编号
	saleprocount	float				null	,--条码卡项目次数
	saleproamt		float				null	,--条码卡项目金额
	saleremark		varchar(200)		null	,--备注
	CONSTRAINT PK_dsalebarcodecardinfo PRIMARY KEY CLUSTERED(salecompid,salebillid,saleseqno)
)
go




---------------------------------------
----mpackageinfo  -- 套餐资料主档
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='mpackageinfo')
CREATE tAbLE    mpackageinfo             
(
	compid			varchar(10)     	Not NULL,   	--公司编号
	packageno		varchar(20) 	Not NULL,	--套餐编号
	packagename 	varchar(30) 	NULL,		--套餐名称
	packageprice	float         	NULL,		--标准价格
	paceageremark	varchar(100)	NULL,		--套餐简介
	usedate			varchar(10)		NULL,		--套餐截止使用日期		
	usetype			int				NULL,		--套餐使用范围  1 条码卡  2 疗程兑换
	CONSTRAINT PK_mpackageinfo PRIMARY KEY CLUSTERED(compid,packageno)
)
go



---------------------------------------
----dmpackageinfo  -- 套餐资料明细
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='dmpackageinfo')
CREATE tAbLE    dmpackageinfo               
(
	compid			varchar(10)     	Not NULL,   	--公司编号
	packageno		varchar(20) 		Not NULL,	--套餐编号
	packageprono 	varchar(20) 		Not NULL,	--项目编号
	packageprocount	float				NULL,			--项目次数
	packageproamt	float				NULL,			--核算金额
	CONSTRAINT PK_dmpackageinfo PRIMARY KEY CLUSTERED(compid,packageno,packageprono)
)
go



---------------------------------------
----msalecardinfo  -- 会员卡销售单主档
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='msalecardinfo')
CREATE tAbLE	msalecardinfo               -- 会员卡销售单
(
	salecompid			varchar(10)			Not NULL,   --公司编号
	salebillid			varchar(20)			Not NULL,   --销售单号
	saledate			varchar(8)				NULL,   --销售日期
	saletime			varchar(8)				NULL,   --销售时间
	salecardno			varchar(20)				NULL,   --销售卡号
	salecardtype		varchar(20)				NULL,   --销售卡类型
	membername			varchar(30)				NULL,   --客户名称
	memberphone			varchar(20)				NULL,   --会员手机号,
	membersex			int						NULL,	--客户性别1--男 0--女
	memberpcid			varchar(30)				NULL,   --客户身份证号
	memberbirthday		varchar(8)				NULL,   --客户生日
	salekeepamt			float					NULL,   --储值金额
	saledebtamt			float					NULL,   --欠款金额
	saletotalamt		float					NULL,   --实收总额
	firstsalerid		varchar(20)				NULL,   --第一销售工号
    firstsalerinid		varchar(20)				NULL,   --第一销售内部编号
    firstsaleamt		float					NULL,   --第一销售分享金额
	secondsalerid		varchar(20)				NULL,   --第二销售工号
    secondsalerinid		varchar(20)				NULL,   --第二销售内部编号
    secondsaleamt		float					NULL,   --第二销售分享金额
	thirdsalerid		varchar(20)				NULL,   --第三销售工号
    thirdsalerinid		varchar(20)				NULL,   --第三销售内部编号
    thirdsaleamt		float					NULL,   --第三销售分享金额
	fourthsalerid		varchar(20)				NULL,   --第四销售工号
    fourthsalerinid		varchar(20)				NULL,   --第四销售内部编号
    fourthsaleamt		float					NULL,   --第四销售分享金额
	fifthsalerid		varchar(20)				NULL,   --第五销售工号 -----烫染师
    fifthsalerinid		varchar(20)				NULL,   --第五销售内部编号
    fifthsaleamt		float					NULL,   --第五销售分享金额
	sixthsalerid		varchar(20)				NULL,   --第六销售工号----- 烫染师
    sixthsalerinid		varchar(20)				NULL,   --第六销售内部编号
    sixthsaleamt		float					NULL,   --第六销售分享金额
	seventhsalerid		varchar(20)				NULL,   --第七销售工号 -----烫染师
    seventhsalerinid	varchar(20)				NULL,   --第七销售内部编号
    seventhsaleamt		float					NULL,   --第七销售分享金额
	eighthsalerid		varchar(20)				NULL,   --第八销售工号----- 烫染师
    eighthsalerinid		varchar(20)				NULL,   --第八销售内部编号
    eighthsaleamt		float					NULL,   --第八销售分享金额
    
    ninthsalerid		varchar(20)				NULL,   --第九销售工号-----
    ninthsalerinid		varchar(20)				NULL,   --第九销售内部编号
    ninthsaleamt		float					NULL,   --第九销售分享金额
    tenthsalerid		varchar(20)				NULL,   --第十销售工号----- 烫染师
    tenthsalerinid		varchar(20)				NULL,   --第十销售内部编号
    tenthsaleamt		float					NULL,   --第十销售分享金额
    
	financedate			varchar(8)				NULL,   --帐务日期 
	saleroperator		varchar(20)				NULL,   --登陆人员代码
	saleroperdate		varchar(8)				NULL,   --登陆日期
	cardappbillid		varchar(20)				NULL,   --会员卡申请单号
	corpscardno			varchar(10)				NULL,	--团购号
	saleremark			varchar(160)			NULL,   --备注
	backconfirmflag		varchar(5) 				null,  --在反充时单据是否总部审批  Y 审批  N 未审批
	salebakflag			int						null,  --单据类型: 0--正常单 1--已作废
	firstpaymode		varchar(5)				NULL,   --支付方式1
	firstpayamt			float      				NULL,   --支付方式1金额
	secondpaymode		varchar(5)				NULL,   --支付方式2
	secondpayamt		float      				NULL,   --支付方式2金额
	thirdpaymode		varchar(5)				NULL,   --支付方式3
	thirdpayamt			float      				NULL,   --支付方式3金额
	fourthpaymode		varchar(5)				NULL,   --支付方式4
	fourthpayamt		float      				NULL,   --支付方式4金额
	sendpointflag			int					NULL    ,  --是否赠送积分: 0--不赠送 1--赠送
	CONSTRAINT PK_INDEX_msalecardinfo PRIMARY KEY CLUSTERED(salecompid,salebillid)
)
         
   
go 
CREATE NONCLUSTERED index idx_msalecardinfo_cardno on msalecardinfo(salecardno)
go
CREATE NONCLUSTERED index idx_msalecardinfo_financedate on msalecardinfo(financedate)
go
CREATE NONCLUSTERED index idx_msalecardinfo_saledate on msalecardinfo(salecompid,saledate)
go



---------------------------------------
----dsalecardproinfo -- 会员卡销售--疗程明细
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='dsalecardproinfo')
CREATE tAbLE    dsalecardproinfo               
(
	salecompid			varchar(10)     Not NULL,   --公司编号
	salebillid			varchar(20)		Not NULL,   --销售单号
	salebilltype		int				Not NULL,   --单据类型   1 开卡  2 充值
	seleproseqno		float			Not	NULL,   --疗程序号
	saleproid			varchar(20)			NULL,   --项目编号
	saleprocardcount	float				NULL,	--购买疗程数
	saleprotype			int					NULL,   --价格序号
	saleprocount		float				NULL,   --次数
	sendprocount		float				NULL,   --赠送次数
	saleproamt			float				NULL,   --疗程金额
	saleprodebtamt		float				NULL,   --疗程欠款
	procutoffdate		varchar(8)			NULL,   --截至日期
	saleproremark		varchar(200)		NULL,   --备注
	firthpaymode		varchar(5)			NULL,   --支付方式1
	firthpayamt			float      			NULL,   --支付方式1金额
	secondpaymode		varchar(5)			NULL,   --支付方式2
	secondpayamt		float      			NULL,   --支付方式2金额
	thirdpaymode		varchar(5)			NULL,   --支付方式3
	thirdpayamt			float      			NULL,   --支付方式3金额
	fourthpaymode		varchar(5)			NULL,   --支付方式4
	fourthpayamt		float      			NULL,   --支付方式4金额
	CONSTRAINT PK_dsalecardproinfo PRIMARY KEY CLUSTERED(salecompid,salebillid,seleproseqno,salebilltype)
)
go


---------------------------------------
----mcardrechargeinfo  -- 会员卡续费单主档
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='mcardrechargeinfo')
CREATE tAbLE    mcardrechargeinfo               -- 帐户异动单
(
	rechargecompid			varchar(10)			Not NULL,   --充值门店
	rechargebillid			varchar(20)			Not NULL,   --充值单号 
	rechargedate			varchar(8)				NULL,   --充值日期 
	rechargetime			varchar(6)				NULL,   --充值时间 
	rechargecardno			varchar(20)				NULL,   --会员卡号
	rechargecardtype		varchar(10)				NULL,   --卡类型
	rechargeaccounttype		varchar(10)				NULL,   --充值账户
	rechargetype			int						NULL,   --续费方式( 0充值 ,6还款 ,)
	membername				varchar(20)				NULL,   --会员姓名
	rechargekeepamt			float					NULL,   --充值金额
	rechargedebtamt			float					NULL,   --欠款金额
	curcardamt				float					NULL,   --异动前余额
	curcarddebtamt			float					NULL,   --异动前欠款
	firstsalerid			varchar(20)				NULL,   --第一销售工号
    firstsalerinid			varchar(20)				NULL,   --第一销售内部编号
    firstsaleamt			float					NULL,   --第一销售分享金额
	secondsalerid			varchar(20)				NULL,   --第二销售工号
    secondsalerinid			varchar(20)				NULL,   --第二销售内部编号
    secondsaleamt			float					NULL,   --第二销售分享金额
	thirdsalerid			varchar(20)				NULL,   --第三销售工号
    thirdsalerinid			varchar(20)				NULL,   --第三销售内部编号
    thirdsaleamt			float					NULL,   --第三销售分享金额
	fourthsalerid			varchar(20)				NULL,   --第四销售工号
    fourthsalerinid			varchar(20)				NULL,   --第四销售内部编号
    fourthsaleamt			float					NULL,   --第四销售分享金额
	fifthsalerid			varchar(20)				NULL,   --第五销售工号 -----烫染师
    fifthsalerinid			varchar(20)				NULL,   --第五销售内部编号
    fifthsaleamt			float					NULL,   --第五销售分享金额
	sixthsalerid			varchar(20)				NULL,   --第六销售工号----- 烫染师
    sixthsalerinid			varchar(20)				NULL,   --第六销售内部编号
    sixthsaleamt			float					NULL,   --第六销售分享金额
	seventhsalerid			varchar(20)				NULL,   --第七销售工号 -----烫染师
    seventhsalerinid		varchar(20)				NULL,   --第七销售内部编号
    seventhsaleamt			float					NULL,   --第七销售分享金额
	eighthsalerid			varchar(20)				NULL,   --第八销售工号----- 烫染师
    eighthsalerinid			varchar(20)				NULL,   --第八销售内部编号
    eighthsaleamt			float					NULL,   --第八销售分享金额
    ninthsalerid		varchar(20)				NULL,   --第九销售工号-----
    ninthsalerinid		varchar(20)				NULL,   --第九销售内部编号
    ninthsaleamt		float					NULL,   --第九销售分享金额
    tenthsalerid		varchar(20)				NULL,   --第十销售工号----- 烫染师
    tenthsalerinid		varchar(20)				NULL,   --第十销售内部编号
    tenthsaleamt		float					NULL ,  --第十销售分享金额
	financedate				varchar(8)				NULL,   --帐务日期 
	operationer				varchar(20)				NULL,   --操作人员
	operationdate			varchar(8)				NULL,   --操作日期
	firstpaymode			varchar(5)				NULL,   --支付方式1
	firstpayamt				float      				NULL,   --支付方式1金额
	secondpaymode			varchar(5)				NULL,   --支付方式2
	secondpayamt			float      				NULL,   --支付方式2金额
	thirdpaymode			varchar(5)				NULL,   --支付方式3
	thirdpayamt				float      				NULL,   --支付方式3金额
	fourthpaymode			varchar(5)				NULL,   --支付方式4
	fourthpayamt			float      				NULL,   --支付方式4金额
	rechargeremark			varchar(160)			NULL,   --备注
	backbillid				varchar(160)			NULL,   --反冲原始单
	backconfirmflag			varchar(5) 				NULL,  --在反充时单据是否总部审批  Y 审批  N 未审批
	salebakflag				int						NULL,  --单据类型: 0--正常单 1--已作废
	sendpointflag			int						NULL    ,  --是否赠送积分: 0--不赠送 1--赠送
	CONSTRAINT PK_mcardrechargeinfo PRIMARY KEY NONCLUSTERED(rechargecompid,rechargebillid)
)
Go

CREATE NONCLUSTERED index idx_mcardrechargeinfo_rechargecardno on mcardrechargeinfo(rechargecardno)
go
CREATE NONCLUSTERED index idx_mcardrechargeinfo_financedate on mcardrechargeinfo(financedate)
go
CREATE NONCLUSTERED index idx_mcardrechargeinfo_rechargedate on mcardrechargeinfo(rechargecompid,rechargedate)
go

---------------------------------------
----mproexchangeinfo--- 储值收购账户兑换疗程主档
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='mproexchangeinfo')
CREATE tAbLE    mproexchangeinfo  
(
	changecompid			varchar(10)			Not NULL,   --兑换门店
	changebillid			varchar(20)			Not NULL,   --兑换单号
	changedate				varchar(8)			NULL    ,   --兑换日期
	changetime				varchar(6)			NULL    ,   --兑换开始时间
	changecardno			varchar(20)			NULL    ,   --会员卡号
	changeaccounttype		varchar(10)			NULL    ,   --卡账户类型
	curaccountkeepamt		float				NULL    ,   --当前账户余额
	curaccountdebtamt		float				NULL    ,   --当前账户欠款
	curproaccountamt		float				NULL    ,   --疗程账户余额
	changecardtype			varchar(10)			NULL    ,   --卡类型
	membername				varchar(20)			NULL    ,   --客户姓名，如果是会员则是会员姓名
	memberphone				varchar(20)			NULL    ,  	--顾客性别
	changeopationerid		varchar(20)			NULL    ,   --操作收银员
	changeopationdate		varchar(10)			NULL    ,   --操作日期
	financedate				varchar(8)			NULL    ,   --帐务日期 ,
	backcsflag				int					NULL    ,   --是否已经撤回: 0--正常单 1--已作废
	backcsbillid			varchar(20)			NULL    ,   --撤回单号
	CONSTRAINT PK_mproexchangeinfo PRIMARY KEY NONCLUSTERED(changecompid,changebillid)
)
go 	
CREATE NONCLUSTERED index idx_mproexchangeinfo_changecardno on mproexchangeinfo(changecardno)
go
CREATE NONCLUSTERED index idx_mproexchangeinfo_financedate on mproexchangeinfo(financedate)
go
CREATE NONCLUSTERED index idx_mproexchangeinfo_compid_financedate on mproexchangeinfo(changecompid,financedate)
INCLUDE (changebillid,changedate,changecardno,changecardtype)
go

---------------------------------------
----dproexchangeinfo--- 储值收购账户兑换疗程明细
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='dproexchangeinfo')
CREATE table dproexchangeinfo( 
	changecompid			varchar(10)				Not NULL, 		--兑换门店
	changebillid			varchar(20)				Not NULL, 		--兑换单号
	changeseqno				float					Not NULL, 		--流水号 
	changeproid				varchar(20) 			NULL,			--项目编号
	procount				float					NULL,			--疗程数
	changeprocount			float					NULL,			--次数
	changeprorate			float					NULL,			--折扣
	changeproamt			float					NULL,			--金额
	changebyproaccountamt	float					NULL,			--金额(疗程账户)
	changebyaccountamt		float					NULL,			--金额(账户金额)\
	changepaymode			varchar(5)				NULL,			--支付方式
	changebycashamt			float					NULL,			--充值金额（现金金额）
	nointernalcardno		varchar(20)				NULL,			--抵用券号
    changebydyqamt			float					NULL,			--金额(抵用券金额－)
	firstsalerid			varchar(20)				NULL,			--第一销售工号
    firstsalerinid			varchar(20)				NULL,			--第一销售内部编号
    firstsaleamt			float					NULL,			--第一销售分享金额
	secondsalerid			varchar(20)				NULL,			--第二销售工号
    secondsalerinid			varchar(20)				NULL,			--第二销售内部编号
    secondsaleamt			float					NULL,			--第二销售分享金额
	thirdsalerid			varchar(20)				NULL,			--第三销售工号----- 烫染师
    thirdsalerinid			varchar(20)				NULL,			--第三销售内部编号
    thirdsaleamt			float					NULL,			--第三销售分享金额
	fourthsalerid			varchar(20)				NULL,			--第四销售工号----- 烫染师
    fourthsalerinid			varchar(20)				NULL,			--第四销售内部编号
    fourthsaleamt			float					NULL,			--第四销售分享金额
    changemark				varchar(200)			NULL,			--折扣
    salebakflag				int						NULL,  --单据类型: 0--正常单 1--已作废
    CONSTRAINT PK_dproexchangeinfo PRIMARY KEY NONCLUSTERED(changecompid,changebillid,changeseqno)
)
go






---------------------------------------
----dproexchangeinfo--- 储值收购账户兑换疗程明细
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='dproexchangeinfobypro')
CREATE table dproexchangeinfobypro
(
	changecompid			varchar(10)				Not NULL, 		--公司别
	changebillid			varchar(20)				Not NULL, 		--会员卡号
	changeseqno				float					Not NULL, 		--流水号 
	changeproid				varchar(20) 			NULL,			--项目编号
	bproseqno				float					NULL,			--项目序号
	lastcount				float					NULL,			--剩余次数
	lastamt					float					NULL,			--剩余金额
	changeprocount			float					NULL,			--次数
	changeproamt			float					NULL,			--金额
	CONSTRAINT PK_dproexchangeinfobypro PRIMARY KEY NONCLUSTERED(changecompid,changebillid,changeseqno)
)
go


---------------------------------------
----mcardchangeinfo--- 会员卡异动
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='mcardchangeinfo')
CREATE table mcardchangeinfo
(
	changecompid				varchar(10)     Not NULL,   --异动门店
	changebillid				varchar(20)		Not NULL,   --异动单号
	changetype					int				Not NULL,   --异动类别  0 折扣转卡 1 收购转卡 2竞争转卡 3换卡 4挂失卡 5补卡 6老卡并老卡 7老卡并新卡 8退卡
	changedate					varchar(8)			NULL,   --异动日期
	changetime					varchar(8)			NULL,   --异动时间
	changebeforcardno			varchar(20)			NULL,   --异动前会员卡号
	changecardfrom				varchar(20)			NULL,   --异动前会员卡归属
	changebeforcardstate		int					NULL,   --异动前状态  
	changebeforcardtype			varchar(10)			NULL,	--异动前卡类型
	membername					varchar(20)			NULL,   --姓名
	memberphone					varchar(20)			NULL,   --手机号码
	curaccountkeepamt			float				NULL,   --储值账户余额
	curaccountdebtamt			float				NULL,   --储值账户欠款
	curproaccountkeepamt		float				NULL,   --疗程账户余额
	curproaccountdebtamt		float				NULL,   --疗程账户欠款
	changelowamt				float				NULL,   --转卡最低余额
	changefillamt				float				NULL,   --转卡充值
	changdebtamt				float				NULL,   --转卡欠款
	changeaftercardno			varchar(20)			NULL,   --异动前会员卡号
	changeaftercardstate		int					NULL,   --异动前状态  
	changeaftercardtype			varchar(10)			NULL,	--异动前卡类型
	firstsalerid			varchar(20)				NULL,   --第一销售工号
    firstsalerinid			varchar(20)				NULL,   --第一销售内部编号
    firstsaleamt			float					NULL,   --第一销售分享金额
	secondsalerid			varchar(20)				NULL,   --第二销售工号
    secondsalerinid			varchar(20)				NULL,   --第二销售内部编号
    secondsaleamt			float					NULL,   --第二销售分享金额
	thirdsalerid			varchar(20)				NULL,   --第三销售工号
    thirdsalerinid			varchar(20)				NULL,   --第三销售内部编号
    thirdsaleamt			float					NULL,   --第三销售分享金额
	fourthsalerid			varchar(20)				NULL,   --第四销售工号
    fourthsalerinid			varchar(20)				NULL,   --第四销售内部编号
    fourthsaleamt			float					NULL,   --第四销售分享金额
	fifthsalerid			varchar(20)				NULL,   --第五销售工号 -----烫染师
    fifthsalerinid			varchar(20)				NULL,   --第五销售内部编号
    fifthsaleamt			float					NULL,   --第五销售分享金额
	sixthsalerid			varchar(20)				NULL,   --第六销售工号----- 烫染师
    sixthsalerinid			varchar(20)				NULL,   --第六销售内部编号
    sixthsaleamt			float					NULL,   --第六销售分享金额
	seventhsalerid			varchar(20)				NULL,   --第七销售工号 -----烫染师
    seventhsalerinid		varchar(20)				NULL,   --第七销售内部编号
    seventhsaleamt			float					NULL,   --第七销售分享金额
	eighthsalerid			varchar(20)				NULL,   --第八销售工号----- 烫染师
    eighthsalerinid			varchar(20)				NULL,   --第八销售内部编号
    eighthsaleamt			float					NULL,   --第八销售分享金额
    ninthsalerid		varchar(20)				NULL,   --第九销售工号-----
    ninthsalerinid		varchar(20)				NULL,   --第九销售内部编号
    ninthsaleamt		float					NULL,   --第九销售分享金额
    tenthsalerid		varchar(20)				NULL,   --第十销售工号----- 烫染师
    tenthsalerinid		varchar(20)				NULL,   --第十销售内部编号
    tenthsaleamt		float					NULL,   --第十销售分享金额
    rechargeremark			varchar(160)			NULL,   --备注
	financedate				varchar(8)				NULL,   --帐务日期 
	operationer				varchar(20)				NULL,   --操作人员
	operationdate			varchar(8)				NULL,   --操作日期
	confirmer				varchar(20)				NULL,   --审核人员
	confirmdate				varchar(8)				NULL,   --审核日期
	backconfirmflag			varchar(5) 				NULL,   --在反充时单据是否总部审批  Y 审批  N 未审批
	salebakflag				int						NULL,  --单据类型:0--正常单 1--已作废
	billflag				int						NULL,  --单据类型: 转卡登记单 0已登记 1已挂失/已保存 2 已解挂 3已补卡 4已驳回
																--	   退卡登记单 0单据录入 1已登记 2 已审核 3已驳回 4已退卡生效
	sendpointflag			int						NULL    ,  --是否赠送积分: 0--不赠送 1--赠送
	CONSTRAINT PK_mcardchangeinfo PRIMARY KEY CLUSTERED(changecompid,changebillid,changetype)
)
go
CREATE NONCLUSTERED index idx_mcardchangeinfo_changebeforcardno on mcardchangeinfo(changebeforcardno)
go
CREATE NONCLUSTERED index idx_mcardchangeinfo_changeaftercardno on mcardchangeinfo(changeaftercardno)
go
CREATE NONCLUSTERED index idx_mcardchangeinfo_financedate on mcardchangeinfo(financedate)
go
CREATE NONCLUSTERED index idx_mcardchangeinfo_rechargedate on mcardchangeinfo(changecompid,changedate)
go


---------------------------------------
----dcardchangeinfo--- 会员卡异动(并卡明细)
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='dcardchangeinfo')
CREATE tAbLE  dcardchangeinfo              
(
	changecompid		varchar(10)     Not NULL,   --异动门店
	changebillid		varchar(20)		Not NULL,   --异动单号
	oldcardno			varchar(20)		Not NULL,   --老会员卡号
	oldcardtype			varchar(10)     null,   --会员卡类别
	oldcardname			varchar(20)		Not NULL,   --老会员卡号
    curaccountkeepamt	float           null,   --账户余额
    curaccountdebtamt	float           null,   --账户欠款
    proaccountkeepamt	float           null,   --疗程余额
    proaccountdebtamt	float           null,   --疗程欠款
	CONSTRAINT PK_dcardchangeinfo PRIMARY KEY CLUSTERED(changecompid,changebillid,oldcardno)
)



---------------------------------------
----mcooperatesaleinfo--- 合作项目录入主档
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='mcooperatesaleinfo')
CREATE tAbLE    mcooperatesaleinfo             
(
	salecompid				char(10)				Not NULL,   --公司编号
	salebillid				varchar(20)				Not NULL,   --异动单号
	saledate				varchar(8)				NULL    ,   --异动日期
	saletime				varchar(8)				NULL    ,   --异动日期
	salecooperid			varchar(30)				NULL    ,   --合作单位
	slaepaymode				varchar(5)				NULL    ,   --支付方向 1 店内支付，2 合作单位支付
	salecostproamt			float					NULL    ,   --项目金额
	salecostcardno			varchar(20)				NULL    ,   --会员卡号
	salecostcardtype		varchar(20)				NULL    ,   --会员卡类型
	membername				varchar(20)				NULL    ,   --姓名
	memberphone				varchar(20)				NULL    ,	--手机
	salemark				varchar(80)				NULL    ,   --备注
	salefactpaycode			varchar(20)				NULL    ,   --实际支付方式 明细化
	slaefactpayamt			float					NULL    ,   --实际支付金额 明细化
	salebillflag			int						NULL    ,   --单据状态 0 新增录入1 已登记 2 总部审核
	firstsalerid			varchar(20)				NULL,   --第一销售工号
    firstsalerinid			varchar(20)				NULL,   --第一销售内部编号
    firstsaleamt			float					NULL,   --第一销售分享金额
	secondsalerid			varchar(20)				NULL,   --第二销售工号
    secondsalerinid			varchar(20)				NULL,   --第二销售内部编号
    secondsaleamt			float					NULL,   --第二销售分享金额
	thirdsalerid			varchar(20)				NULL,   --第三销售工号
    thirdsalerinid			varchar(20)				NULL,   --第三销售内部编号
    thirdsaleamt			float					NULL,   --第三销售分享金额
	fourthsalerid			varchar(20)				NULL,   --第四销售工号
    fourthsalerinid			varchar(20)				NULL,   --第四销售内部编号
    fourthsaleamt			float					NULL,   --第四销售分享金额
	fifthsalerid			varchar(20)				NULL,   --第五销售工号 -----烫染师
    fifthsalerinid			varchar(20)				NULL,   --第五销售内部编号
    fifthsaleamt			float					NULL,   --第五销售分享金额
	sixthsalerid			varchar(20)				NULL,   --第六销售工号----- 烫染师
    sixthsalerinid			varchar(20)				NULL,   --第六销售内部编号
    sixthsaleamt			float					NULL,   --第六销售分享金额
	seventhsalerid			varchar(20)				NULL,   --第七销售工号 -----烫染师
    seventhsalerinid		varchar(20)				NULL,   --第七销售内部编号
    seventhsaleamt			float					NULL,   --第七销售分享金额
	eighthsalerid			varchar(20)				NULL,   --第八销售工号----- 烫染师
    eighthsalerinid			varchar(20)				NULL,   --第八销售内部编号
    eighthsaleamt			float					NULL,   --第八销售分享金额
	ninthsalerid		varchar(20)				NULL,   --第九销售工号-----
    ninthsalerinid		varchar(20)				NULL,   --第九销售内部编号
    ninthsaleamt		float					NULL,   --第九销售分享金额
    tenthsalerid		varchar(20)				NULL,   --第十销售工号----- 烫染师
    tenthsalerinid		varchar(20)				NULL,   --第十销售内部编号
    tenthsaleamt		float					NULL,   --第十销售分享金额
    financedate				varchar(8)				NULL,   --帐务日期 
	operationer				varchar(20)				NULL,   --操作人员
	operationdate			varchar(8)				NULL,   --操作日期
	salebakflag				int						NULL,  --单据类型:0--正常单 1--已作废
	CONSTRAINT PK_mcooperatesaleinfo PRIMARY KEY CLUSTERED(salecompid,salebillid)
)
go 
CREATE NONCLUSTERED index idx_mcooperatesaleinfo_salecostcardno on mcooperatesaleinfo(salecostcardno)
go


CREATE NONCLUSTERED index idx_mcooperatesaleinfo_financedate on mcooperatesaleinfo(financedate)
go
CREATE NONCLUSTERED index idx_mcooperatesaleinfo_rechargedate on mcooperatesaleinfo(salecompid,saledate)
go

---------------------------------------
----payinfodaybyday-- --  单据--支付明细日结
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='payinfodaybyday')
CREATE tAbLE    payinfodaybyday              
(
	paycompid		varchar(10)      Not NULL,   --公司编号
	paydate			varchar(10)      Not NULL,   --结算日期
	paybilltype		varchar(5)		 Not NULL,   --单据类别  sY 收银  sK  售卡  cZ 充值 ZK  转卡 HZ 合作项目 tK 退卡
	paymode			varchar(5)		 Not NULL,   --支付方式
	payamt			float				 NULL,   --支付金额
	CONSTRAINT PK_payinfodaybyday PRIMARY KEY CLUSTERED(paycompid,paydate,paybilltype,paymode)
)
CREATE NONCLUSTERED index idx_payinfodaybyday_paydate on payinfodaybyday(paycompid,paydate)	
CREATE NONCLUSTERED index idx_payinfodaybyday_paymode on payinfodaybyday(paycompid,paydate,paymode)	

---------------------------------------
----detial_trade_byday_fromshops-- --  单据--营业数据结算
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='detial_trade_byday_fromshops')
CREATE table detial_trade_byday_fromshops                                 
(                                                                              
		 shopId					varchar(10)			Not NULL,	--门店编号                                           
		 shopName				varchar(20)			NULL,	--门店名称                                           
		 dateReport					varchar(8)			Not NULL,	--结账日期                                           
		 total						float				NULL,	--总收入                                                                  
		 cashservice				float				NULL,	--现金服务                                                 
		 cashprod					float				NULL,	--现金产品                                                 
		 cashcardtrans				float				NULL,	--现金(卡异动)                                    
		 cashbackcard				float				NULL,	--现金(退卡)                                              
		 cashtotal					float				NULL,	--现金合计(扣除现金退卡)                                                
		                                                                                                 
		 creditservice				float				NULL,	--银行卡服务                                                
		 creditprod					float				NULL,	--银行卡产品                                                
		 credittrans				float				NULL,	--银行卡(卡异动)                                      
		 creditbackcard				float				NULL,	---银行卡(退卡)                                           
		 credittotal				float				NULL,	--银行卡合计(扣除银行卡退卡)                                                
		                                                                                                  
		 checkservice				float				NULL,	--支票服务                                                 
		 checkprod					float				NULL,	--支票产品                                                 
		 checktrans					float				NULL,	--支票(卡异动)                                       
		 checkbackcard				float				NULL,	--支票(退卡)                                           
		 checktotal					float				NULL,	--支票合计(扣除支票退卡)                                   
		                               
		 zftservice					float				NULL,	--指付通服务                                                 
		 zftprod					float				NULL,	--指付通产品                                                 
		 zfttrans					float				NULL,	--指付通(卡异动)                                       
		 zftbackcard				float				NULL,	--指付通(退卡)                                           
		 zfttotal					float				NULL,	--指付通合计(扣除支付通退卡)                                 
		                               
		 ockservice					float				NULL,	--oK卡服务                                                 
		 ockkprod					float				NULL,	--oK卡产品                                                 
		 ocktrans					float				NULL,	--oK卡(卡异动)                                       
		 ockbackcard				float				NULL,	--oK卡(退卡)                                           
		 ocktotal					float				NULL,	--oK卡合计(扣除oK卡退卡) 
		 
		 tgkservice					float				NULL,	--团购卡服务                                                 
		 tgkkprod					float				NULL,	--团购卡产品                                   
		 tgktrans					float				NULL,	--团购卡(卡异动)                                                  
		 tgktotal					float				NULL,	--团购卡合计                                
	
		 
		 totalcardtrans				float				NULL,	--卡异动(卡销售,卡充值,卡升级)	
		                           
		 cashchangesale				float				NULL,	--现金兑换销售                          
		 bankchangesale				float				NULL,	--银行卡兑换销售 
		  
		 cashbytmksale				float				NULL,	--现金条码卡销售                              
		 bankbytmksale				float				NULL,	--银行卡条码卡销售                              
		 checkbytmksale				float				NULL ,	--支票条码卡销售                              
		 fingerbytmksale			float				NULL,	--指付通条码卡销售                              
		 okpqypwybytmksale			float				NULL,	--oK卡条码卡销售
		  
		 cashhezprj					float				NULL,	--现金合作项目                           
		 bankhezprj					float				NULL,	--银行卡合作项目                  
		 sumcashhezprj				float				NULL,	--现金合作项目(店内支付的现金)                                    
		                                                                                                  
		                                                  
		                                                                                                             
		 cardsalesservices          float				NULL,	--销卡服务                                                 
		 cardsalesprod				float				NULL,	--销卡产品 
		 staffsallprod				float				NULL,	--员工产品             
		 acquisitioncardservices	float				NULL,	--收购转卡服务 
		 costpointtotal				float				NULL,	--积分服务   
		 cashdyservice				float				NULL,   --现金抵用券服务                              
		 prjdyservice				float				NULL,   --项目抵用券服务
		 tmkservice					float				NULL,   --条码卡 服务  
		    
		 managesigning				float				NULL,	--经理签单                                         
		 payoutRegister				float				NULL,	--支出登记  
		CONSTRAINT PK_detial_trade_byday_fromshops PRIMARY KEY CLUSTERED(shopId,dateReport)                                    
)         

---------------------------------------
----compclasstraderesult-- --  分类业绩--数据结算
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='compclasstraderesult')
CREATE table compclasstraderesult(                              
		  compid			varchar(10) not null,   
		  ddate				varchar(10) not null,                             
		  beautyeji			float			null,                              
		  hairyeji			float			null,                              
		  footyeji			float			null,                              
		  fingeryeji		float			null,                              
		  totalyeji			float			null,                              
		  realbeautyeji		float			null,                              
		  realhairyeji		float			null,                              
		  realfootyeji		float			null,                              
		  realfingeryeji	float			null,                              
		  realtotalyeji		float			null,
		  CONSTRAINT PK_compclasstraderesult PRIMARY KEY CLUSTERED(compid,ddate)                              
)  
go





---------------------------------------
-----staffchangeinfo   门店员工异动信息 CREATE by liujie 20130628
---------------------------------------

if not exists(select 1 from sysobjects where type='U' and name='staffchangeinfo')
CREATE tAbLE    staffchangeinfo     
(
	changecompid			varchar(10)     not null,	--公司别
	changebillid			varchar(20) 	not null,	--申请单号
	changetype				int				not	null,	--申请类型  0-薪资调整 1--离职申请 2--入职申请 3--重回公司申请 4--请假申请,5--本店调动,6--跨店调动
	changestaffno			varchar(20)			null,	--异动前员工编号
	appchangecompid			varchar(10)			null,	--异动前申请公司
	staffpcid				varchar(20)			null,	--员工身份证
	staffphone				varchar(20)			null,   --手机号码
    staffmangerno			varchar(20)			null,   --员工内部编号
	changedate				varchar(8)			null,	--申请日期
	validatestartdate		varchar(8)			null,	--当changetype=0 的时候此值是可以重用的日期 
														--当changetype=1 的时候此值是实际离职日期 
														--当changetype=2 的时候此值是实际入职日期 
														--当changetype=3 的时候此值是实际重回公司日期 
														--当changetype=4 的时候此值是 请假开始日期 
														--当changetype=5 的时候此值是本店调动开始日期 
														--当changetype=6 的时候此值是跨店调动开始日期 
	validateenddate			varchar(8)			null,	--当changetype=4 的时候此值是 请假结束日期 --当fhb02i=3的时候此值回家时间
	beforedepartment		varchar(20)			null,	--异动前部门
	beforepostation			varchar(10)			null,	--异动前职位
	beforesalary			float				null,	--异动前薪资
    beforesalarytype		int					null,	--异动前 0，税前 1 税后  
    beforeyejitype			varchar(5)			null,   --异动前业绩方式 1-美发业绩  2-美容业绩  3-总业绩
    beforeyejirate			float				null,   --异动前业绩系数
    beforeyejiamt			float				null,   --异动前业绩基数
    aftercompid				varchar(20)			null,	--异动后门店
    afterstaffno			varchar(20)			null,	--异动后工号
    afterdepartment			varchar(20)			null,	--异动后部门
	afterpostation			varchar(10)			null,	--异动后职位
	aftersalary				float				null,	--异动后薪资
    aftersalarytype			int					null,	--异动后 0，税前 1 税后  
    afteryejitype			varchar(5)			null,   --异动后业绩方式 1-美发业绩  2-美容业绩  3-总业绩
    afteryejirate			float				null,   --异动后业绩系数
    afteryejiamt			float				null,   --异动后业绩基数
    leaveltype				int					null,	--职类型  1 正常离职 2 自动离职
	checkcompid				varchar(10)			null,	--审核公司
	checkstaffid			varchar(20)			null,	--门店审核人
	checkdate				varchar(8)			null,	--门店审核日期
	checkflag				int					null,	--门店是否已经审核 0--没有审核 1--已经审核
	
	checkinheadcompid		varchar(10)			null,	--总部审核公司
	checkinheadstaffid		varchar(20)			null,	--总部审核人
	checkinheaddate			varchar(8)			null,	--总部审核日期
	checkinheadflag			int					null,	--总部是否已经审核 0--没有审核 1--已经审核
	
	comfirmcompid			varchar(10)			null,	--人事审核公司
	comfirmstaffid			varchar(20)			null,	--人事审核人
	comfirmdate				varchar(8)			null,	--人事审核日期
	comfirmflag				int					null,	--人事是否已经审核 0--没有审核 1--已经审核 2 已经驳回
	billflag				int					null,	--流程审核状态  0 登记 1--门店审核状态 2--总部审核状态 3--人事审核状态 8 已生效 5 已驳回
	remark					varchar(200)		null,	--备注
	CONSTRAINT PK_staffchangeinfo PRIMARY KEY CLUSTERED(changecompid, changebillid,changetype)
)
go

---------------------------------------
-----staffrewardinfo   门店员工奖罚信息 CREATE by liujie 20130628
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='staffrewardinfo')
create table staffrewardinfo
(
	entrycompid 				varchar(10) 	        not null,		--	登记公司
	entrybillid 				varchar(20)				not null,		--	登记单号
	entryflag 					int							null,		--	奖罚标志   0--奖   2--罚
	handcompid					varchar(10)					null,		--	奖罚员工所在门店
	handstaffid 				varchar(20)					null,		--	奖罚员工
	handstaffinid 				varchar(20)					null,		--	奖罚员工
	entryreason					varchar(200)				null,		--奖罚原因
	entrydate 					varchar(8)					null,		--登记日期
	entrytype 					varchar(8)					null,		--奖罚类型
	billflag					int							null,		--单据类型 标志 0-登记；1-门店审核 2总部审核 
	checkrewardstaff 			varchar(20)					null,		--门店奖罚员工号 
	checkrewardstaffname 		varchar(40)					null,		--门店奖罚员工名称 
	checkrewardremark			varchar(800)				null,		--门店奖罚备注
	checkrewardamt				float						null,		--门店奖罚金额
	checkpersonid				varchar(20)					null,		--门店审批人
	checkdate					varchar(8)					null,		--门店审批日期
	checkflag					int							 null,		--门店核实（审批）标志 0-未核实；1-已核实
	checkinheadrewardstaff 		varchar(20)					null,		--总部奖罚员工号 
	checkinheadrewardstaffname 	varchar(40)					null,		--总部奖罚员工名称 
	checkinheadrewardremark		varchar(800)				null,		--总部奖罚备注
	checkinheadrewardamt		float						null,		--总部奖罚金额
	checkinheadpersonid			varchar(20)					null,		--总部审批人
	checkinheaddate				varchar(8)					null,		--总部审批日期
	checkinheadflag				int							 null,		--总部核实（审批）标志 0-未核实；1-已核实
	invalid						int							null,		--是否作废
	constraint PK_staffrewardinfo	primary key clustered(entrycompid,entrybillid)
) 
go

--奖罚主档
if not exists(select 1 from sysobjects where type='U' and name='mstaffrewardinfo')
create table mstaffrewardinfo
(
	entrycompid 				varchar(10) 	        not null,		--	登记公司
	entrybillid 				varchar(20)				not null,		--	登记单号
	entryflag 					int							null,		--	奖罚标志   0--奖   1--罚
	handcompid					varchar(10)					null,		--	奖罚员工所在门店
	billflag					int							null,		--	单据类型 标志 0-登记；1-门店审核 2 门店驳回
	invalid						int							null,		--	是否作废
	operationer					varchar(20)					null,		--	操作人
	operationdate				varchar(10)					null,		--	操作时间
	constraint PK_mstaffrewardinfo	primary key clustered(entrycompid,entrybillid)
) 
go



--奖罚明细
if not exists(select 1 from sysobjects where type='U' and name='dstaffrewardinfo')
create table dstaffrewardinfo
(
	entrycompid 				varchar(10) 	        not null,		--	登记公司
	entrybillid 				varchar(20)				not null,		--	登记单号
	entryseqno					float					not null, 		--  流水号 
	handstaffid 				varchar(20)					null,		--	奖罚员工
	handstaffinid 				varchar(20)					null,		--	奖罚员工
	entryreason					varchar(200)				null,		--  奖罚原因
	entrydate 					varchar(8)					null,		--  奖罚日期
	entrytype 					varchar(8)					null,		--  奖罚类型
	rewardamt					float						null,		--总部奖罚金额
	billflag					int							null,		--	单据类型 标志 0-登记；1-门店审核 2 门店驳回
	constraint PK_dstaffrewardinfo	primary key clustered(entrycompid,entrybillid,entryseqno)
) 
go



--门店员工保底主档
if not exists(select 1 from sysobjects where type='U' and name='mstaffsubsidyinfo')
create table mstaffsubsidyinfo
(
	entrycompid 				varchar(10) 	        not null,		--	登记公司
	entrybillid 				varchar(20)				not null,		--	登记单号
	handcompid					varchar(10)					null,		--	门店
	handstaffid 				varchar(20)					null,		--	员工
	handstaffinid 				varchar(20)					null,		--	员工
	subsidyamt					float						null,		--  保底额度
	subsidyflag					int							null,		--  保底方式 全部满足 部分满足
	conditionnum				int							null,		--  满足条件数
	billflag					int							null,		--	单据类型 标志 0-登记；1-审核  2 驳回 3 作废
	invalid						int							null,		--	是否作废
	operationer					varchar(20)					null,		--	操作人
	operationdate				varchar(10)					null,		--	操作时间
	startdate					varchar(10) 				null,		--	起始日期
	enddate						varchar(10) 				null,		--	结束日期
	subsidycondition			varchar(100) 				null,		--	满足条件备注
	subsidyconditiontext		varchar(200) 				null,		--	满足条件备注
	constraint PK_mstaffsubsidyinfo	primary key clustered(entrycompid,entrybillid)
) 
go




--奖罚明细
if not exists(select 1 from sysobjects where type='U' and name='dstaffsubsidyinfo')
create table dstaffsubsidyinfo
(
	entrycompid 				varchar(10) 	        not null,		--	登记公司
	entrybillid 				varchar(20)				not null,		--	登记单号
	entryseqno					float					not null, 		--  流水号 
	handstaffinid 				varchar(20)					null,		--	员工
	subsidytype					int							null,		--	保底方式
	subsidyamt					float						null,		--	保底额度
	
	constraint PK_dstaffsubsidyinfo	primary key clustered(entrycompid,entrybillid,entryseqno)
) 
go


--门店员工指标主档
if not exists(select 1 from sysobjects where type='U' and name='mstafftargetinfo')
create table mstafftargetinfo
(
	entrycompid 				varchar(10) 	        not null,		--	登记公司
	entrybillid 				varchar(20)				not null,		--	登记单号
	handcompid					varchar(10)					null,		--	门店
	handstaffid 				varchar(20)					null,		--	员工
	handstaffinid 				varchar(20)					null,		--	员工
	targemode					int							null,		--  保底方式 1 额度 2 比率
	targeyejitype				int							null,		--  算取业绩方式 1 个人虚业绩 2 个人实业绩 3个人总业绩
	targeamt					float						null,		--  保底额度
	targeflag					int							null,		--  保底方式 全部满足 部分满足
	conditionnum				int							null,		--  满足条件数
	billflag					int							null,		--	单据类型 标志 0-登记；1-审核  2 驳回
	invalid						int							null,		--	是否作废
	operationer					varchar(20)					null,		--	操作人
	operationdate				varchar(10)					null,		--	操作时间
	startdate					varchar(10) 				null,		--	起始日期
	enddate						varchar(10) 				null,		--	结束日期
	subsidycondition			varchar(100) 				null,		--	满足条件备注
	subsidyconditiontext		varchar(200) 				null,		--	满足条件备注
	constraint PK_mstafftargetinfo	primary key clustered(entrycompid,entrybillid)
) 
go




--指标明细
if not exists(select 1 from sysobjects where type='U' and name='dstafftargetinfo')
create table dstafftargetinfo
(
	entrycompid 				varchar(10) 	        not null,		--	登记公司
	entrybillid 				varchar(20)				not null,		--	登记单号
	entryseqno					float					not null, 		--  流水号 
	handstaffinid 				varchar(20)					null,		--	员工
	targettype					int							null,		--	指标方式
	targetamt					float						null,		--	指标额度
	startdate					varchar(10) 				null,		--	起始日期
	enddate						varchar(10) 				null,		--	结束日期
	constraint PK_dstafftargetinfo	primary key clustered(entrycompid,entrybillid,entryseqno)
) 
go


---------------------------------------
-----noperformanceemp   门店零业绩人员 CREATE by liujie 20130628
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='noperformanceemp')
create table noperformanceemp
(
		compid			varchar(10)		null,
		empid			varchar(20)		null,
		empinnerno		varchar(20)		null,
		ddate			varchar(10)		null,
)
---------------------------------------
-----staff_work_salary   门店员工提成表
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='staff_work_salary')
create table staff_work_salary
(
		compid				varchar(10)			NOT NULL,	--门店编号
		person_inid			varchar(20)			NOT NULL,	--员工内部编号
		salary_date			varchar(10)			NOT NULL,	--结算日期
		oldcostcount		float				NULL,	--老客项目数
		newcostcount		float				NULL,	--新客项目数
		trcostcount			float				NULL,	--烫染项目数
		cashbigcost			float				NULL,	--现金大项
		cashsmallcost		float				NULL,	--现金小项
		cashhulicost		float				NULL,	--现金护理
		cardbigcost			float				NULL,	--销卡大项
		cardsmallcost		float				NULL,	--销卡小项
		cardhulicost		float				NULL,	--销卡护理
		cardprocost			float				NULL,	--疗程消费
		cardsgcost			float				NULL,	--收购卡消费
		cardpointcost		float				NULL,	--积分消费
		projectdycost		float				NULL,	--项目抵用券
		cashdycost			float				NULL,	--现金抵用券
		tmcardcost			float				NULL,	--条卡卡消费
		salegoodsamt		float				NULL,	--产品销售
		salecardsamt		float				NULL,	--卡销售
		prochangeamt		float				NULL,	--疗程兑换
		saletmkamt			float				NULL,	--条码卡销售
		qhpayinner			float				NULL,	--全韩店内支付
		qhpayouter			float				NULL,	--全韩对方支付
		jdpayinner			float				NULL,	--暨大店内支付
		smpayinner			float				NULL,	--私密店内支付
		staffyeji			float				NULL,	--员工提成合计
		staffcashyeji		float				 NULL,
		stafftotalyeji		float				 NULL,
		constraint PK_staff_work_salary	primary key clustered(compid,person_inid,salary_date)
) 

---------------------------------------
-----mgoodsinsert    -- 产品入库单主档
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='mgoodsinsert')
CREATE TABLE    mgoodsinsert              
(
	insercompid			varchar(10)				NOT NULL	,   --公司编号
	inserbillid			varchar(30)				NOT NULL	,   --入库单号
	inserdate			varchar(8)					NULL    ,   --入库日期
	insertime			varchar(8)					NULL    ,   --入库时间
	inserwareid			varchar(10)					NULL    ,   --仓库代码
	inserstaffid		varchar(10)					NULL    ,   --采购人员
	insertype			int							NULL    ,   --入库性质(1- 正常入库 2- 盘点冲抵,3-退货)
	checkbillflag		int							NULL    ,	--是否开票 0-没有 1--需要
	checkbillno			varchar(50)					NULL    ,	--发票编号
	storesendbill		varchar(30)					NULL	,	--收货单号
    exitstoreno			varchar(20)					NULL	,	--退货门店
    exidbillno			varchar(10)					NULL	,	--门店仓库
    billflag			int							NULL	,	--单据状态 0--申请待批  1--驳回  2--复核
    inseropationerid	varchar(20)					NULL    ,   --操作收银员
	inseropationdate	varchar(10)					NULL    ,   --操作日期
	invalid				int							NULL	,	--是否作废 0--保留  1--作废
	CONSTRAINT PK_mgoodsinsert PRIMARY KEY CLUSTERED(insercompid,inserbillid)
)
go 
---------------------------------------
-----dgoodsinsert    -- 产品入库单明细
---------------------------------------
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

---------------------------------------
  -- dgoodsinsertpc    --产品入库批次管理      
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='dgoodsinsertpc')
create table dgoodsinsertpc
(
	insercompid			varchar(10)				NOT NULL,		--公司编号
	insergoodsno		varchar(20)				NOT NULL,		--产品编号
	inserbillid 		varchar(20)				NOT NULL,		--进库批号
	inserseqno			float					NOT NULL,		--序号
	producedate			varchar(8)					NULL,		--生产日期
	expireddate			varchar(8)					NULL,		--保质期到期日期
	curlavecount		float						NULL,		--该批当前剩余数量
	inserwareid			varchar(10)					NULL    ,   --仓库代码
	inserdate			varchar(8)					NULL    ,   --入库日期
	CONSTRAINT PK_dgoodsinsertpc PRIMARY KEY CLUSTERED(insercompid,insergoodsno,inserbillid,inserseqno)
)
go

-----mgoodsouter    -- 产品出库单主档
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='mgoodsouter')
CREATE TABLE    mgoodsouter   
(
	outercompid			varchar(10)     NOT NULL	,   --公司编号
	outerbillid			varchar(30)		NOT NULL	,   --出库单号
	outerdate			varchar(8)			NULL	,   --出库日期
	outertime			varchar(8)			NULL	,   --出库日期
	outerwareid			varchar(10)			NULL	,   --仓库代码
	outertype			int					NULL	,	--出库性质(1- 正常出库 2- 盘点冲抵 3-供应商退货)
	revicetype			int					NULL    ,   --领取类别 1:员工 2门店
	outerstaffid		varchar(10)			NULL	,   --采购人员
	sendbillid			varchar(40)			NULL    ,   --发货单号
	billflag			int					NULL	,	--单据状态 0--申请待批  1--驳回  2--复核
    outeropationerid	varchar(20)			NULL    ,   --操作收银员
	outeropationdate	varchar(10)			NULL    ,   --操作日期
	invalid				int					NULL	,	--是否作废 0--保留  1--作废
	CONSTRAINT PK_mgoodsouter PRIMARY KEY CLUSTERED(outercompid,outerbillid)
)
go 
---------------------------------------
-----dgoodsouter    -- 产品出库单明细
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='dgoodsouter')
CREATE TABLE    dgoodsouter              
(
	outercompid			varchar(10)			NOT NULL,   --公司编号
	outerbillid			varchar(30)			NOT NULL,   --出库单号
	outerseqno			float				NOT NULL,   --序号
	outergoodsno		varchar(20)				NULL,   --产品代码
	standunit			varchar(5)				NULL,   --标准单位
	standprice			float					NULL,   --单价(标准单位)
	curgoodsstock		float					NULL,   --现库存数量(出库单位)
	outerunit			varchar(5)				NULL,   --出库单位
	outercount			float					NULL,   --(出库单位)数量
	outerprice			float					NULL,   --单价(出库单位)
	outeramt			float					NULL,   --金额
    outerrate			float					NULL,   --折扣  默认1
   	frombarcode			varchar(20)				NULL,   --起始编码
	tobarcode			varchar(20)				NULL,   --结束编码
	CONSTRAINT PK_dgoodsouter PRIMARY KEY CLUSTERED(outercompid,outerbillid,outerseqno)
)
go 

---------------------------------------
  -- dgoodsouterpc    --产品出库批次管理      
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='dgoodsouterpc')
create table dgoodsouterpc
(
	outercompid			varchar(10)			NOT NULL,	--公司编号
	outerbillid			varchar(30)			NOT NULL,	--出库单号
	outergoodsno 		varchar(20)			NOT NULL,	--产品编号
	outerseqno			float				NOT NULL,   --序号
	inserbillid 		varchar(20)				NULL,	--进库批号
	outercount			float					NULL,	--该批出库数量
	outerprice			float					NULL,	--该批出库单价	
	CONSTRAINT PK_dgoodsouterpc PRIMARY KEY CLUSTERED(outercompid,outerbillid,outergoodsno,outerseqno)
)
go


---------------------------------------
-----mgoodsstockinfo    -- 产品入出库单主档
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='mgoodsstockinfo')
CREATE TABLE mgoodsstockinfo     
(
	changecompid		varchar(10)				NOT NULL,   -- 公司编号             
	changetype			varchar(2)				NOT NULL,   -- 异动别 (1-入库,2-出库,3-销货,4-耗用)                
	changebillno		varchar(30)				NOT NULL,   -- 异动单号             
	changedate			varchar(8)					NULL,   -- 异动日期                
	changetime			varchar(8)					NULL,   -- 异动时间
	changewareid		varchar(10)					NULL,   -- 仓库代号(不能以此为准,此栏位不用)             
	changeoption		int							NULL,   --入库或出库方式
	changestaffid		varchar(20)					NULL,   --入库或出库人或销售人员
	changeflag			int							NULL,   --明细类别(默认为1,员工领取)

	CONSTRAINT PK_mgoodsstockinfo PRIMARY KEY CLUSTERED (changecompid, changetype, changebillno)
)
go
---------------------------------------
-----dgoodsstockinfo    -- 产品入出库单明细
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='dgoodsstockinfo')
CREATE TABLE dgoodsstockinfo          -- 库存日异动统计明细档(所有异动单据明细 update) 
(
	changecompid		varchar(10)				NOT NULL,   -- 公司编号             
	changetype			varchar(2)				NOT NULL,   -- 异动别 (1-入库,2-出库,3-销货,4-耗用)                
	changebillno		varchar(30)				NOT NULL,   -- 异动单号      
	changeseqno			float					NOT NULL,   -- 输入流水号       
	changegoodsno		varchar(30)					NULL,   -- 产品代号     
	standunit			varchar(8)					NULL,   -- 入库或出库单位
	standcount			float						NULL,   -- 标准单位数量     
	standprice			float						NULL,   -- 标准单位单价     
	producedate			varchar(8)					NULL,   -- 生产日期
	changeunit			varchar(8)					NULL,   -- 入库或出库单位
	changeprice			float						NULL,   -- 入库或出库单位
	changecount			float						NULL,   -- 入库单位或出库单位数量
	changeamt			float						NULL,   -- 金额
	CONSTRAINT PK_dgoodsstockinfo PRIMARY KEY CLUSTERED (changecompid, changetype, changebillno, changeseqno)
)
go

---------------------------------------
-----dgoodssetlebegin  --产品期初结算表
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='dgoodssetlebegin')
CREATE TABLE dgoodssetlebegin          -- 库存日异动统计明细档(所有异动单据明细 update) 
(
	changecompid		varchar(10)				NOT NULL,   -- 结算门店          
	changegoodsno		varchar(30)				NOT	NULL,   -- 产品代号   
	begindate			varchar(10)				NOT	null,	-- 结算日期  
	begincount			float						NULL,   -- 结算数量
	beginamt			float						NULL,   -- 金额
	CONSTRAINT PK_dgoodssetlebegin PRIMARY KEY CLUSTERED (changecompid, changegoodsno,begindate)
)
go

---------------------------------------
-----dgoodsbarinfo    --产品唯一条码表
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='dgoodsbarinfo')
CREATE TABLE dgoodsbarinfo         						
(     
	goodsno				varchar(20)			NOT NULL,				-- 产品编号 
	goodsbarno			varchar(20)			NOT NULL,				-- 唯一条码
	barnostate			int						NULL,				-- 条码状态 0:生成 1:入库 2:发货/出库 3:销售 4:消耗 5:损坏 6:退货中
	createdate			varchar(10)				NULL,				-- 生成日期
	createstaffno		varchar(20)				NULL,				-- 生成操作者
	inserdate			varchar(10)				NULL,				-- 入库日期
	inserbillno			varchar(20)				NULL,				-- 入库单号
	outerdate			varchar(10)				NULL,				-- 发货/出库日期
	outerbill			varchar(20)				NULL,				-- 发货/出库单号
	receivestore		varchar(10)				NULL,				-- 收货门店
	costdate			varchar(10)				NULL,				-- 消费日期/消耗日期
	costbillo			varchar(20)				NULL,				-- 消费单号/消耗单号
	coststore			varchar(10)				NULL				-- 消费门店
	CONSTRAINT PK_dgoodsbarinfo PRIMARY KEY CLUSTERED (goodsno,goodsbarno)
)
go  

---------------------------------------
-----staffkqrecordinfo    --员工考情表
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='staffkqrecordinfo')
CREATE table staffkqrecordinfo 
(
	seqno				int identity(1,1)	Not NULL,
	machineid			varchar(20)				NULL,   --考勤机号
	personid			varchar(10)				NULL,	--考勤手指号
	stat				int						NULL,	--考勤状态	
	ddate				varchar(10)				NULL,	--考勤日期
	ttime				varchar(10)				NULL,	--考勤时间
	worktype			int						NULL,	--		
	operationer			varchar(20)				NULL,	--获取考勤人员
	operationdate		varchar(10)				NULL,	--获取考勤日期
	invalid				int						NULL	,	--是否作废 0--保留  1--作废
	CONSTRAINT PK_staffkqrecordinfo PRIMARY KEY NONCLUSTERED(seqno)
)
go

---------------------------------------
-----mgoodsinventory     -- 产品盘点单主档
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='mgoodsinventory')
CREATE TABLE    mgoodsinventory              
(
	inventcompid			varchar(10)			NOT NULL,			--公司编号
	inventbillid			varchar(30)			NOT NULL,			--盘点单号
	inventdate				varchar(8)				NULL    ,		--盘点日期
	inventtime				varchar(8)				NULL    ,		--盘点日期
	inventwareid			varchar(10)				NULL    ,		--仓库代码
	inventstaffid			varchar(10)				NULL    ,		--盘点人员代码
	inventopationerid		varchar(20)				NULL    ,		--操作收银员
	inventopationdate		varchar(10)				NULL    ,		--操作日期
	inventinserbillid		varchar(20)				NULL    ,		--入库单号
	inventouterbillid		varchar(20)				NULL    ,		--出库单号
	invalid					int						NULL	,		--是否作废
	inventflag				int						NUll	,		--是否盘点过 0未保存 1 已保存 2已盘点
	CONSTRAINT PK_mgoodsinventory PRIMARY KEY CLUSTERED(inventcompid,inventbillid)
)
go 


---------------------------------------
-----dgoodsinventory     -- 产品盘点单明细
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='dgoodsinventory')
CREATE TABLE    dgoodsinventory             
(
	inventcompid			varchar(10)			NOT NULL,   --公司编号
	inventbillid			varchar(30)			NOT NULL,   --盘点单号
	inventseqno				float				NOT NULL,   --序号
	inventgoodsno			varchar(20)				NULL,   --产品代码
	inventunit				varchar(5)				NULL,   --基本单位
	inventcount				float					NULL,   --盘点数量
	curstockcount			float					NULL,   --现库存量
	discount				float					NULL,   --差异数量
	inventfrombarno			varchar(20)				NULL    ,--起始条码号
	inventtobarno			varchar(20)				NULL    ,--结束条码号
	inventcreateflag		int						NULL	,--是否生成话条码
	inserunit				varchar(10)				NULL	,--进货单位
	inserprice				float					NUll	,--进货单价
	outerunit				varchar(10)				NULL	,--出库单位
	outerprice				float					NUll	,--出库单价
	CONSTRAINT PK_dgoodsinventory PRIMARY KEY CLUSTERED(inventcompid,inventbillid,inventseqno)
)
go

---------------------------------------
-----mgoodsorderinfo     --订单主表主档
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='mgoodsorderinfo')
create table mgoodsorderinfo	
(
	ordercompid 			varchar(10)			NOT NULL,		--公司编号
	orderbillid				varchar(30)			NOT NULL,		--订单号
	orderdate				varchar(8)				NULL,		--订单日期
	ordertime				varchar(8)				NULL,		--采购时间
	orderstaffid			varchar(20)				NULL,		--采购人
	orderstate				int						NULL,		--订单状态  0未处理 1已经确认(复核) 2完整下单 3全部发货 4已收货 5 作废
	downordercompid			varchar(20)				NULL,		--下单分公司
	downorderstaffid		varchar(20)				NULL,		--下单人
	downorderdate			varchar(8)				NULL,		--下单日期
	downordertime			varchar(8)				NULL,		--下单时间
	sendbillno				varchar(80)				NULL,		--发货单
	revicebillno			varchar(80)				NULL,		--收货单
	ordersource   			int  					NULL,		--物品来源0上级仓库,1供应商
	storewareid				varchar(20)				NULL,		--门店的仓库编号
	headwareid   			varchar(100)			NULL,		--总部的仓库编号
	headwarename   			varchar(100)			NULL,		--总部的仓库编号
	orderbilltype   		int  					NULL,		--采购单类型 员工/门店 
	orderopationerid		varchar(20)				NULL,		--操作收银员
	orderopationdate		varchar(10)				NULL,		--操作日期	
	invalid   				int  					NULL,		--是否作废
	constraint PK_mgoodsorderinfo  primary key clustered(ordercompid,orderbillid)
)
go
create nonclustered index idx_mgoodsorderinfo_date on mgoodsorderinfo(ordercompid,orderdate)
go



---------------------------------------
-----dgoodsorderinfo     --订单主表明细
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='dgoodsorderinfo')
create table dgoodsorderinfo
(
	ordercompid				varchar(10)			NOT NULL,		--公司编号
	orderbillid				varchar(30)			NOT NULL,		--订单号
	orderseqno				float				NOT NULL,		--产品序号
	ordergoodsno			varchar(20)				NULL,		--产品编号
	ordergoodscount			float					NULL,		--数量
	ordergoodsunit			varchar(5)				NULL,		--单位
	ordergoodsprice			float					NULL,		--订单单价
	ordergoodsamt			float					NULL,		--金额
	headstockcount			float					NULL,		--总部库存
	downordercount			float					NULL,		--总下单量
	downorderamt			float					NULL,		--总下单金额
	nodowncount				float					NULL,		--未下单量
	norevicecount			float					NULL,		--未收货量
	supplierno				varchar(20)				NULL,		--供应商编号
	headwareno				varchar(20)				NULL,		--上级仓库编号
	goodssource				int  default(0)			NULL,		--物品来源0上级仓库,1供应商
	ordergoodstype			int						NULL,		-- 0未处理 1已经确认(复核) 2完整下单 3全部发货 4已收货 
	ordermark				varchar(200)			NULL,		-- 下单欠货备注
	constraint pk_dgoodsorderinfo primary key clustered(ordercompid,orderbillid,orderseqno)		
)
go



---------------------------------------
-----mgoodssendinfo     --总部发货单主表主档
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='mgoodssendinfo')
create table mgoodssendinfo	
(
	sendcompid 				varchar(10)				NOT NULL,		--公司编号
	sendbillid				varchar(30)				NOT NULL,		--订单号
	senddate				varchar(8)				NULL,			--发货日期
	sendtime				varchar(8)				NULL,			--发货时间
	sendstaffid				varchar(20)				NULL,			--发货人
	sendstate				int						NULL,			--发货单状态  0未处理 1已经确认(复核) 2已作废
	storewareid				varchar(20)				NULL,			--门店的仓库编号
	headwareid   			varchar(20)				NULL,			--总部的仓库编号
	orderdate				varchar(8)				NULL,			--订单日期
	ordertime				varchar(8)				NULL,			--采购时间
	ordercompid   			varchar(10)	  			NULL,			--门店采购单
	orderbill   			varchar(20)	  			NULL,			--门店采购单
	orderbilltype   		int  					NULL,			--采购单类型 员工/门店 
	storestaffid			varchar(20)				NULL,			--门店联系人
	storeaddress			varchar(80)				NULL,			--门店地址
	sendopationerid			varchar(20)				NULL,			--操作收银员
	sendopationdate			varchar(10)				NULL,			--操作日期	
	invalid   				int  					NULL,			--是否作废
	constraint PK_mgoodssendinfo  primary key clustered(sendcompid,sendbillid)
)
go

---------------------------------------
-----dgoodssendinfo     --总部发货单主表明细
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='dgoodssendinfo')
create table dgoodssendinfo
(
	sendcompid				varchar(10)			NOT NULL,		--公司编号
	sendbillid				varchar(30)			NOT NULL,		--订单号
	sendseqno				float				NOT NULL,		--产品序号
	sendgoodsno				varchar(20)				NULL,		--产品编号
	sendgoodsunit			varchar(5)				NULL,		--单位
	ordergoodscount			float					NULL,		--申请数量
	ordergoodsamt			float					NULL,		--申请金额	
	downordercount			float					NULL,		--已下单量
	nodowncount				float					NULL,		--未下单量
	sendgoodprice			float					NULL,		--发货单价
	sendgoodrate			float					NULL,		--发货折扣 
	sendgoodscount			float					NULL,		--发货量 
	sendgoodsamt			float					NULL,		--发货金额 
	frombarcode				varchar(20)				NULL,		--起始编码
	tobarcode				varchar(20)				NULL,		--结束编码
	constraint pk_dgoodssendinfo primary key clustered(sendcompid,sendbillid,sendseqno)		
)
go

---------------------------------------
-----dgoodssendbarinfo     --总部发货单条码表明细
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='dgoodssendbarinfo')
create table dgoodssendbarinfo
(
	sendcompid				varchar(10)			NOT NULL,		--公司编号
	sendbillid				varchar(30)			NOT NULL,		--订单号
	sendseqno				float				NOT NULL,		--产品序号
	sendgoodsno				varchar(20)				NULL,		--产品编号
	frombarcode				varchar(20)				NULL,		--起始编码
	tobarcode				varchar(20)				NULL,		--结束编码
	sendbarcount			float					NULL,		--条码数量
	constraint pk_dgoodssendbarinfo primary key clustered(sendcompid,sendbillid,sendseqno)		
)
go


---------------------------------------
-----mgoodsreceipt     --门店收货主档表
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='mgoodsreceipt')
create table mgoodsreceipt
(
	receiptcompid					varchar(10)			NOT NULL,		--公司编号
	receiptbillid					varchar(30)			NOT NULL,		--收货单号
	receiptdate						varchar(8)				NULL,		--收货日期
	receipttime						varchar(8)				NULL,		--收货日期
	receiptwareid					varchar(10)				NULL,		--仓库代码
	receiptstaffid					varchar(10)				NULL,		--收货人
	receiptsendbillid				varchar(30)				NULL,		--发货单号
	receiptorderbillid				varchar(30)				NULL,		--订单号
	orderbilltype   				int  					NULL,			--采购单类型 员工/门店 
	receiptstate					int						NULL,       --收货单状态：0：未处理；1：已收货
	receiptopationerid				varchar(20)				NULL,		--操作收银员
	receiptopationdate				varchar(10)				NULL,		--操作日期			
	constraint pk_mgoodsreceipt primary key clustered(receiptcompid,receiptbillid)
)
go	

---------------------------------------
-----dgoodsreceipt     --门店收货明细表
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='dgoodsreceipt')
create table dgoodsreceipt	--收货单明细表
(
	receiptcompid  			varchar(10)				NOT NULL,		--公司编号
	receiptbillid  			varchar(30)				NOT NULL,		--收货单号
	receiptseqno			float					NOT NULL,		--序号
	receiptgoodsno			varchar(20)					NULL,		--产品编号
	receiptgoodsunit		varchar(10)					NULL,		--单位
	receiptprice			float						NULL,		--标准单价(为库存异动)
	receiptgoodscount		float						NULL,		--收货数量
	receiptgoodsamt			float						NULL,		--收货数量
	sendgoodscount			float						NULL,		--发货数量
	damagegoodscount		float						NULL,		--损坏数量
	debegiidscount			float						NULL,		--欠货数量
	ordergoodscount			float						NULL		--订单数量
	constraint pk_dgoodsreceipt primary key clustered(receiptcompid,receiptbillid,receiptseqno)
)			
go



---------------------------------------
-----mreturngoodsinfo     --门店退货主档
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='mreturngoodsinfo')
create table mreturngoodsinfo
(
	returncompid				varchar(10)			NOT NULL,		--公司编号
	returnbillid				varchar(20)			NOT NULL,		--退货单号
	returndate					varchar(8)			NULL    ,		--退货日期
    returntime					varchar(6)			NULL    ,		--退货时间  
	returnwareid				varchar(10)			NULL    ,		--门店仓库
	returnstaffid				varchar(10)			NULL    ,		--退货人员
    returnstate					int     			NULL    ,		--单据状态 0 新增 1--申请待批 2--总部审核  3--驳回
    returnopationerid			varchar(20)			NULL,			--操作收银员
	returnopationdate			varchar(10)			NULL,			--操作日期	
	confirmopationerid			varchar(20)			NULL,			--审核或驳回人员
	confirmopationdate			varchar(10)			NULL,			--审核或驳回日期	
	confirmcompid				varchar(10)			NULL,			--审核门店			
    CONSTRAINT PK_mreturngoodsinfo PRIMARY KEY CLUSTERED(returncompid,returnbillid)
)

---------------------------------------
-----mreturngoodsinfo     --门店退货明细
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='dreturngoodsinfo')
create table dreturngoodsinfo
(
	returncompid				varchar(10)			NOT NULL,		--公司编号
	returnbillid				varchar(20)			NOT NULL,		--退货单号
	returnseqno					float				NOT NULL,		--序号
	returngoodsno				varchar(20)				NULL	,	--产品代码
	returngoodsunit				varchar(5)				NULL	,	--退货单位
	returncount					float					NULL	,	--退货数量
    returntype					int						NULL	,	--退货性质(0-待定 1- 退货到总部 2- 退货到供应商)
   	revicestoreno				varchar(20)				NULL	,	--退货供应商编号/总部仓库代码
   	factreturncount				float					NULL	,	--实际退货数量
    factreturnprice				float					NULL	,	--实际退货单价
    factreturnamt				float					NULL	,	--实际退货金额
	CONSTRAINT PK_dreturngoodsinfo PRIMARY KEY CLUSTERED(returncompid,returnbillid,returnseqno)
)
GO

---------------------------------------
-----dgoodsreturnbarinfo     --总部退货单条码表明细
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='dgoodsreturnbarinfo')
create table dgoodsreturnbarinfo
(
	returncompid				varchar(10)			NOT NULL,		--公司编号
	returnbillid				varchar(30)			NOT NULL,		--订单号
	returnseqno					float				NOT NULL,		--产品序号
	returngoodsno				varchar(20)				NULL,		--产品编号
	frombarcode					varchar(20)				NULL,		--起始编码
	tobarcode					varchar(20)				NULL,		--结束编码
	returncount					float					NULL,		--条码数量
	constraint pk_dgoodsreturnbarinfo primary key clustered(returncompid,returnbillid,returnseqno)		
)
go



---------------------------------------
-----mpayoutinfo     --门店支出登记
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='mpayoutinfo')
create table mpayoutinfo
(
	payoutcompid				varchar(10)			NOT NULL,		--公司编号
	payoutbillid				varchar(20)			NOT NULL,		--支出单号
	payoutdate					varchar(8)			NULL    ,		--支出日期
    payouttime					varchar(6)			NULL    ,		--支出时间  
	payoutopationerid			varchar(20)			NULL	,		--操作收银员
	payoutopationdate			varchar(10)			NULL	,		--操作日期
	billstate					int					NULL	,		--单据状态  0 登记  1 已保存 2 已受理
    CONSTRAINT PK_mpayoutinfo	PRIMARY KEY CLUSTERED(payoutcompid,payoutbillid)
)
go
create nonclustered index idx_mgoodsorderinfo_date on mpayoutinfo(payoutcompid,payoutdate)
go


---------------------------------------
-----dpayoutinfo     --门店支出登记
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='dpayoutinfo')
create table dpayoutinfo
(
	payoutcompid				varchar(10)			NOT NULL,		--公司编号
	payoutbillid				varchar(20)			NOT NULL,		--支出单号
	payoutseqno					float				NOT	NULL,		--序号
	payoutitemid				varchar(20)			NULL    ,		--支出项目
	payoutitemamt				float				NULL    ,		--支出金额
	checkbookflag				int 				NULL	,		--有无发票   0-无发票,1-有发票 2 冲账
	checkbookno					varchar(200)			NULL	,	--发票编号
	payoutmark					varchar(400)		NULL	,		--支出备注
	payoutbillstate				int     			NULL    ,		--单据状态 0 新增 1--门店审核 2--财务专员审核  3--财务经理核 11 门店驳回  22 财务专员驳回  33 财务经理驳回
	checkedopationerid			varchar(20)			NULL	,		--总部审核人员
	checkedopationdate			varchar(10)			NULL	,		--总部审核回日期	
	confirmopationerid			varchar(20)			NULL	,		--财务部审核或驳回人员
	confirmopationdate			varchar(10)			NULL	,		--财务部审核或驳回日期	
    CONSTRAINT PK_dpayoutinfo	PRIMARY KEY CLUSTERED(payoutcompid,payoutbillid,payoutseqno)
)
---------------------------------------
-----accountclosureinfo     --门店封帐表
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='accountclosureinfo')
create table accountclosureinfo  
(
	closecompid			varchar(10)	NOT NULL,   --公司别
	closedate			varchar(8)	NOT NULL,   --封账日期	
	closeoptioner		varchar(20)	NULL,	    --操作人
	closeoptiondate		varchar(8)	NULL,	    --操作日期	
	closeoptiontime		varchar(6)	NULL,		--操作时间
	constraint PK_accountclosureinfo primary key(closecompid,closedate)
)
go

---------------------------------------
-----purchaseregister     --收购卡登记
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='purchaseregister')
CREATE TABLE    purchaseregister               -- 收购卡登记
(
	registercompid 		varchar(10) 		NOT NULL, 		--登记公司
	registercardno  	varchar(20)			NOT NULL,  	 	--卡号
	registercardtype   	varchar(10)  		NULL,			--卡类别
	membername  		varchar(20) 		NULL,    		--姓名
	memberphone     	varchar(20) 		NULL,    		--手机号码
	memberbrithday     	varchar(10) 		NULL,    		--生日
	membersex     		int     			NULL,			--性别
	cardbalance     	float				NULL,			--余额
	registerperson		varchar(20)  		NULL,			--登记人
	registerdate		varchar(8)  		NULL,			--登记日期
	registertime		varchar(8)  		NULL,			--登记时间
	cardvesting			varchar(10)  		NULL,			--卡归属公司
	registerpcid		varchar(30) 		NULL,   		--身份证号码
	CONSTRAINT PK_purchaseregister PRIMARY KEY CLUSTERED(registercompid,registercardno)
)
go



---------------------------------------
----defaultlist--- 默认发送列表
---------------------------------------
if not exists(select 1 from sysobjects where type='U' and name='defaultlist')
CREATE table defaultlist
(
  defaultid int identity(1,1) Not NULL,--默认编号
  defaultname varchar(20),--默认姓名
  defaultphone varchar(20),--默认电话号码
  CONSTRAINT PK_defaultlist PRIMARY KEY NONCLUSTERED(defaultid)
)


--发送信息主明细
if not exists(select 1 from sysobjects where type='U' and name='smgInfo')
CREATE table smgInfo
(
	sendcompid				varchar(10)		Not NULL,	--门店编号
	sendbillid              varchar(30)		Not NULL,   --单据编号
	senddate				varchar(20)		NUll,		--发送日期
	sendtime				varchar(20)     NULL,		--发送时间
	sendstate				int             NULL,		--发送状态
	userid					varchar(20)		NULL,		--登录人
	sendcontent				varchar(500)	NULL,		--短信内容
	CONSTRAINT PK_smgInfo PRIMARY KEY NONCLUSTERED(sendcompid,sendbillid)
)
go

--发送明细
if not exists(select 1 from sysobjects where type='U' and name='smgdetails')
CREATE table smgdetails
(
	sendcompid  		varchar(30)		Not NULL,   --门店编号
	sendbillid  		varchar(30)		Not NULL,   --单据编号
	smgbernme		varchar(20)		NULL,		--姓名
	smgphone		varchar(15)		NULL,		--号码
	cardno			varchar(50)		NULL,		--卡号
	cardtype		varchar(20)		NULL		--卡类型
	CONSTRAINT PK_smgdetails PRIMARY KEY NONCLUSTERED(sendcompid,sendbillid)
)
go

--任务主明细
if not exists(select 1 from sysobjects where type='U' and name='missioninfo')
CREATE table missioninfo
(
	missionbillid       varchar(30)		Not NULL,   --单据编号
	missionname			varchar(20)		NULL,		--任务名称
	missiontype			varchar(10)		NULL,		--任务类型
	missionkey			varchar(30)		NULL,		--任务值
	missiondetails		varchar(1000)   NULL,		--任务内容
	templatestate		int				NULL,		--任务状态
	CONSTRAINT PK_missioninfo PRIMARY KEY NONCLUSTERED(missionbillid)
)
go

--任务明细
if not exists(select 1 from sysobjects where type='U' and name='missiontails')
CREATE table missiontails(
	missionbillid		varchar(30)		Not NULL,	--单据编号（外键，联系missioninfo）
	missionnames		varchar(20)		NULL,		--任务发送的姓名
	missionphone		varchar(20)		NULL		--任务发送的手机号
	CONSTRAINT PK_missiontails PRIMARY KEY NONCLUSTERED(missionbillid)
)
go
--呼叫等待
if not exists(select 1 from sysobjects where type='U' and name='callwaiting')
create table callwaiting(
			callid			INT  IDENTITY(1, 1)		PRIMARY KEY,  --等待编号
			callbillid		varchar(30)				NOT NULL, --单据编号
			calluserid		varchar(50)					NULL,  --登陆工号+坐席
			callon			varchar(20)					NULL,   --呼入电话号码
			calledon		varchar(20)					NULL, --被呼入的号码
			agentnum		varchar(20)					NULL, --振铃坐席
			offertime		varchar(50)					NULL, --摘机时间
			calledtime		varchar(50)					NULL, --挂机时间
			calltime		varchar(50)					NULL, --登陆时间
			calltype		int							NULL,           --类型（0表示预约，1表示咨询，2表示挂失，3表示投诉，4表示退卡）
			callstate		int							NULL,          --状态（0表示未处理，1表示处理）
			callmark		varchar(600)				NULL,
)

--预约
if not exists(select 1 from sysobjects where type='U' and name='orders')
  create table orders(
        ordersid			INT  IDENTITY(1, 1)  PRIMARY KEY,  --预约编号
		calluserid			varchar(50)		null,
		callbillid			varchar(30)		null,	--预约单据编号 
		orderphone			varchar(20)		null,	--预约来电	
		orderconply			varchar(100)	null,--	预约门店
		orderusermf			varchar(80)		null,
		orderusertrh		varchar(80)		null,
		orderusermr			varchar(80)		null,
		orderproject		varchar(1000)	null,--	预约项目
		ordertime			varchar(100)	null,--	预约时间
		ordertimes			varchar(100)	NULL,		--	确认时间
		orderdetail			varchar(2000)	NULL,		--	预约备注
		complydetail		varchar(2000)	NULL,		--	门店备注
		orderstate			int				NULL--	状态(0表示确认单据，1表示单据未处理完)
  )



--咨询
if not exists(select 1 from sysobjects where type='U' and name='refer')
create table refer(
		 referid			INT  IDENTITY(1, 1)  PRIMARY KEY,  --咨询编号
		 callbillid			varchar(30)				NOT NULL, --咨询单据编号
	     calluserid			varchar(50)					NULL,    -- 登陆工号+坐席
	     refertime			varchar(100)				NULL,	 --	咨询时间
         refercomply		varchar(1000)				NULL,     --咨询门店信息
		 refercards			varchar(1000)				NULL,			--咨询会员卡信息
		 referproject		varchar(1000)				NULL,	--项目信息
	     referelse			varchar(100)				NULL, --其他
	     referdetails		varchar(2000)				NULL,	 --	备注
		 feferstate			int							NULL		--状态	
)


--投诉
if not exists(select 1 from sysobjects where type='U' and name='peiier')
create table peiier(
		 peiierid			INT  IDENTITY(1, 1)  PRIMARY KEY,			--投诉编号
		 callbillid			varchar(30)					NOT NULL,		--投诉单据编号
	     calluserid			varchar(50)						NULL,		-- 登陆工号+坐席
	     peiiertime			varchar(100)					NULL,		--	投诉时间
		 peiiercontent		varchar(1000)					NULL,		--投诉类容
	     peiierelse			varchar(100)					NULL,		--其他
	     peiierdetails		varchar(2000)					NULL,		--	备注	

		 peiierstate     int   NULL		--状态	
)
--退卡
if not exists(select 1 from sysobjects where type='U' and name='cardreturning')
create table cardreturning(
		 cardreturningid			INT  IDENTITY(1, 1)  PRIMARY KEY,  --退卡编号
		 callbillid					varchar(30)				NOT NULL, --退卡单据编号
	     calluserid					varchar(50)				NULL,    -- 登陆工号+坐席
	     cardreturningtime			varchar(100)			NULL,	 --	退卡时间
		 cardreturningcontent		varchar(1000)			NULL,     --退卡类容
	     cardelse					varchar(100)			NULL, --其他
	     cardreturningdetails		varchar(2000)			NULL,	 --	备注	
		 cardreturningstate			int						NULL		--状态
)
go

if not exists(select 1 from sysobjects where type='U' and name='intention')
CREATE TABLE    intention     --培训经历主档
		(
				intid			INT  IDENTITY(1, 1)  PRIMARY KEY,--培训编号	
				intcomplyno		varchar(10)		Not NULL,--公司编号
				intbillid		varchar(30)		Not NULL,--单号
				intdproject		varchar(10)		NULL    ,--岗位课程（0：初级技师，1：高级技师，2，预备发型师，3：发型师，4：首席，5：总监，6：美发经理，7：选修课）
				intdstage		int				NULL    ,--阶段（0：无，1：第一阶段,2:第二阶段，3：第三阶段，4：第四阶段，其它任意填）
				intdstarttime	varchar(20 )		NULL    ,--培训开始时间
				intdendtime		varchar(20 )		NULL    ,--培训结束时间
				intuser			varchar(20)		NULL    ,--登记人
				intdata			varchar(30)		NULL    ,--登记日期
				intetionstate	int				NULL     --登记状态
		)
if not exists(select 1 from sysobjects where type='U' and name='intentiondetail')
CREATE TABLE    intentiondetail   --培训经历明细
		(
				intdid			INT		IDENTITY(1, 1)  PRIMARY KEY, --流水号	
				intdcomplyno	varchar(10)		Not NULL,--公司编号
				intdbillid		varchar(30)		Not NULL,--对应单号
				intdwaite		varchar(16)		NULL    ,--预留
				intstuno		varchar(18)		NULL    ,--学生手册号码
				incardno		varchar(18)		NULL    ,--身份证号码
				instaffno		varchar(20)		NULL    ,--员工编号
				instaffname		varchar(20)		NULL    ,--员工姓名
				intposition		varchar(20)		NULL    ,--职位
				intbirthday		varchar(20)		NULL    ,--出生日期			
				intdscore		int				NULL    ,--成绩（0：不合格，1：合格）
				intpositions	int				NULL	,--建议可担当岗位（0：初级技师，1：高级技师，2，预备发型师，3：发型师，4：首席，5：总监，6：美发经理）
				intdproname		varchar(20)		NULL    ,--选修课名字
				intdpunish		varchar(255)	NULL    ,--奖罚情况
				intdremark		varchar(255)	NULL     --备注
		)

		
--录音日期表
if not exists(select 1 from sysobjects where type='U' and name='calldata')
create table calldata
(
	id   INT  IDENTITY(1, 1)  PRIMARY KEY,
	callbillid 	varchar(40)	NULL,  --编号	
	calltime varchar(30)	NULL,  --录音日期
	callstate int			NULL   --录音状态（0：未上传；1：已上传）
)

--录音表
if not exists(select 1 from sysobjects where type='U' and name='callditails')
create table callditails
(
	id   INT  IDENTITY(1, 1)  PRIMARY KEY,
  callbillid 	varchar(40)	NULL,  --编号	
	calltime varchar(30)	NULL,  --录音日期	
	calltimes varchar(30)	NULL,  --录音日期
	callon varchar(30)	NULL,   --主叫
	calledon varchar(30)	NULL,   --被叫
 calledons varchar(50)	NULL,   --被叫
	callhoues time(7)	NULL,   --通话时间
  callall varchar(300)	NULL
)


	--门店分类总分析
	create table jsanalysisresult  
	(  
		compno				varchar(10) null,	--门店号
		mmonth				varchar(6)	null,	--月份
		resusttyep			int			null,	--类型
		resusttyeptext		varchar(60) null,	--类型名称
		resultamt			float		null	--结果
	)
	
	go
	--门店审核流
	create table storeconfirmflow
	(
		appitemno				varchar(5)		null,--申请项 1换卡 2补卡 3退卡 4开卡额度  5充值额度 
													 --       6修改会员资料 7退疗程 8项目变价 9 产品变价
		checkcommissioner		varchar(200)	null,--审核专员
		checkcommissionertext	varchar(300)	null,--审核专员
		checkmanager			varchar(200)	null,--审核经理
		checkmanagertext		varchar(300)	null,--审核经理
	)
	go
	--门店流程信息
	CREATE tAbLE    storeflowinfo             -- 
	(
		compid						varchar(10)   Not NULL,			--公司编号
		billid						varchar(20)   Not NULL,			--单号
		appflowtype					int			  NULL,				----申请项 1换卡 2补卡 3退卡 4开卡额度  5充值额度 
																	--       6修改会员资料 7退疗程 8项目变价 9 产品变价
		appflowcode					varchar(20)   NULL,				--项目编号或卡类型或卡号
		appflowstore				varchar(10)   NULL,				--申请门店
		appflowvalue				varchar(30)	  NULL,				--申请卡号/价格
		startdate					varchar(10)   NULL,				--生效开始日期
		enddate						varchar(10)   NULL,				--生效截止日期
		appflowreason				varchar(200)  NULL,				--申请原因
		appflowstate				int			  NULL,				--是否审核，0-登记，1-专员审核，2经理审核    
		appflowconfirmempid			varchar(10)	  NULL,				--专员审核人编码
		appflowconfirmdate			varchar(10)   NULL,				--专员审核日期
		appflowcheckempid			varchar(10)	  NULL,				--经理审核人编码
		appflowcheckdate			varchar(10)   NULL,				--经理审核日期
		invalid						int			  NULl,				--是否作废 0 未作废  1 已作废
		CONSTRAINT PK_storeflowinfo PRIMARY KEY CLUSTERED(compid,billid)
	)	
	go
	CREATE NONCLUSTERED index idx_storeflowinfo_promotionstype on storeflowinfo(appflowtype,appflowcode)

   --员工指标月结表
   create table staffresultset
   (
		compid				varchar(10)		null,	--门店编号
		staffinid			varchar(20)		null,	--员工内部工号
		ddate				varchar(10)		null,	--结算日期
		stafftotalyeji		float			null,	--个人虚业绩
		stafftotalxuyeji		float			null,	--个人虚业绩
		staffrealtotalyeji	float			null,	--个人实业绩
		oldcustomercount	float			null,	--个人老客数
		beatyprjcount		float			null,	--美容大项数
		tangrancount		float			null,	--烫染疗程数
		
   )

