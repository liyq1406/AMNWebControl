CREATE TABLE    intention     --培训经历主档
		(
				intid			INT  IDENTITY(1, 1)  PRIMARY KEY,--培训编号	
				intcomplyno		varchar(10)		Not NULL,--公司编号
				intbillid		varchar(30)		Not NULL,--单号
				intdproject		int				NULL    ,--岗位课程（0：初级技师，1：高级技师，2，预备发型师，3：发型师，4：首席，5：总监，6：美发经理，7：选修课）
				intdstage		int				NULL    ,--阶段（0：无，1：第一阶段,2:第二阶段，3：第三阶段，4：第四阶段，其它任意填）
				intdstarttime	varchar(20 )		NULL    ,--培训开始时间
				intdendtime		varchar(20 )		NULL    ,--培训结束时间
				intuser			varchar(20)		NULL    ,--登记人
				intdata			varchar(30)		NULL    ,--登记日期
				intetionstate	int				NULL     --登记状态
		)

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