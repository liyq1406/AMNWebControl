package com.amani.tools;

public interface SystemFinal {


		public static final String LOGIN_VERTIFY_FAIL="用户认证失败，无法登录！\n可能您的帐号密码不一致！";//"login vertify failly";
		public static final String LOGIN_SUCCESS="login successfully";
		public static final String LOGIN_FAIL="login failly";
		public static final String LOGIN_OUTDATE="login dateout";
		public static final String LOGIN_OUTCOMPID="login compidout";
		public static final String SQL_EXCEPTION = "系统在执行SQL语句过程中出现异常";
		public static final String SYS_EXCEPTION = "系统出现运行时异常";
		public static final String EXCEP_FROM_DAO = "DaoException";
		public static final String EXCEP_FROM_SERVICE = "ServiceException";
		public static final String EXCEP_FROM_ACTION = "ActionException";
		
		public static final String COMP_NOT_EXIST="登录公司不存在";//"company not exist";
		
		public static final String GLOABL_USER_INFO = "userInfo";
		
		public static final String GLOABL_SYSETEM_INFO = "sysInfo";
		
		public static final String MOZU_BEAN = "mozu_bean";
		public static final String MODULE_BEAN = "module_bean";
		
		public static final String SESSION_KEY_COMPID = "compId";
		
		public static final String MODIFIY = "M";
		public static final String DELETE = "D";
		public static final String INSERT = "A";
		
		public static final String SUCCESS = "success";
		String OPERATION_SUCCESS = "operation_success";
		String OPERATION_FAILURE = "operation_failure";
		String OPERATION_SUCCESS_MSG = "操作成功";
		String OPERATION_FAILURE_MSG = "操作失败";
		
		String LOAD_SUCCESS = "load_success";
		String LOAD_FAILURE = "load_failure";
		String LOAD_FAILURE_MSG = "数据载入失败!";
		String LOAD_SUCCESS_MSG = "数据载入成功!";
		
	    String POST_SUCCESS = "post_success";
		String POST_FAILURE = "post_failure";
		String POST_FAILURE_MSG = "数据保存失败!";
		String POST_SUCCESS_MSG = "保存成功!";
		
		String ADD_SUCCESS = "add_success";
		String ADD_FAILURE = "add_failure";
		String ADD_FAILURE_MSG = "数据新增失败!";
		String ADD_SUCCESS_MSG = "数据新增成功!";
		
		String DELETE_SUCCESS = "delete_success";
		String DELETE_FAILURE = "delete_failure";
		String DELETE_FAILURE_MSG = "数据删除失败!";
		String DELETE_SUCCESS_MSG = "数据删除成功!";
		
		
		String REFRESH_SUCCESS = "refresh_success";
		String REFRESH_FAILURE = "refresh_failure";
		String REFRESH_FAILURE_MSG = "数据刷新失败!";
		
		String VALIDATE_SUCCESS = "validate_success";
		String VALIDATE_FAILURE = "validate_failure";
		String CANCEL_SUCCESS = "cancel_success";
		int DS_ADD = 1;			//数据状态新增状态
		int DS_EDIT = 2;		//数据是在编辑状态
		int DS_BROWSER = 3;     //数据是在浏览状态
		String CONFIRM_ON      = "Y"; //正在保存
		String CONFIRM_OFF      = "N";//不在保存状态
		String NO_OPERATE_RIGHT = "对不起您没有操作权限";
		String OPERATE_SUCCESS = "操作成功";
		String OPERATE_FAILURE = "操作失败";
		String BILLSTATEERROR  = "单据正在保存状态不可以重复保存！";
		String REQUIRED_FIELD = "required field";
		int    RETURN_ROWSCOUNT = -1;
		
		String FROMDATE_BILLS     = "账务单据";
		String FROMDATE_DAY       = "日记账";
		String FROMDATE_DETAIL    = "消费明细";
		
		
		//以下是权限操作的相关动作
		String UR_ADD = "UR_ADD";		    			//新增
		String UR_MODIFY = "UR_MODIFY";     			//修改
		String UR_DELETE = "UR_DELETE";					//删除
		String UR_QUERY = "UR_QUERY";					//查询
		String UR_PRINT = "UR_PRINT";					//打印
		String UR_POST = "UR_POST";     			//修改
		String UR_SPECIAL_COST = "UR_SPECIAL_COST";		//成本
		String UR_SPECIAL_CHECK = "UR_SPECIAL_CHECK"; 	//覆核
		String UR_SPECIAL_SET = "UR_SPECIAL_SET";   	//设定
		String UR_SPECIAL1 = "UR_SPECIAL1"; 			//特殊权限1
		String UR_SPECIAL2 = "UR_SPECIAL2";				//特殊权限2
		String UR_SPECIAL3 = "UR_SPECIAL3";				//特殊权限3
		String UR_SPECIAL4 = "UR_SPECIAL4";				//特殊权限4
		String UR_SPECIAL5 = "UR_SPECIAL5";				//特殊权限5
		
		
		String USER_NO_LOGINF = "对不起您还没有登录!";
		String COMP_CENTER = "001";
		int	   CENTER_COMP = 1;//公司的职级是总部
		int	   UILITY_COMP = 2;//公司的职级是事业部
		int	   AREA_COMP   = 3;//公司的职级是区域
		int	   SHOP_COMP   = 4;//公司的职级是门店
		
		
		//系统小数位数
		String DIG_QUANTITY  = "SP005-3";     //数量小数位数
		String DIG_PRICE     = "SP011-3";     //单价小数位数
		String DIG_SUM       = "SP004-3";     //金额小数位数
		String DIG_PERCENT   = "SP006-3";     //百分率小数位数
		
		String KING_USER     = "AMANI";
	
		String ADMIN_USER    = "ADMIN";
		
		String WAREHOUSE_ID  = "01";
		
		
		
		
		//卡种变更
		int CHANGESTATE_REGISTER    =        1; //注册
		int CHANGESTATE_CARDSTART   =        2; //开卡
		int CHANGESTATE_CARDEXTEND  =        3; //续卡
		int CHANGESTATE_CARDCHANGE  =        4; //卡种变更
		int CHANGESTATE_RECORDHOLD  =        5; //会籍保留
		int CHANGESTATE_RECORDTRANS =        6; //会籍转让
		int CHANGESTATE_LOSEFILL    =        7; //遗失补卡
		int CHANGESTATE_CARDQUIT    =        11; //退卡
		int CHANGESTATE_BACHPOSTPONE=        9; //批量延期
//		会员卡状态
		int STATE_NOTSALE      = 1; // 未销售
		int STATE_ISSALE       = 2; // 已销售
		int STATE_REGISTER     = 3; // 已注册
		int STATE_OPENCARD     = 4; // 已开卡
		int STATE_CARDUSE      = 5; // 正常使用中
		int STATE_CARDCHANGE   = 6; // 已转卡
		int STATE_CARDQUIT     = 7; // 已退卡
		int STATE_PASSED       = 8; // 已过期
		int STATE_MISSED       = 9; // 已挂失
		//单据异动类型
		String MONACT_FILL          = "0" ;  //充值
		String MONACT_DRAW          = "1" ;  //取款
		String MONACT_CONSUME       = "2" ;  //消费
		String MONACT_TRANSIN       = "3" ;  //转入
		String MONACT_TRANSOUT      = "4" ;  //转出
		String MONACT_DEFICT        = "5" ;  //欠款
		String MONACT_RETURN        = "6" ;  //欠款返回
		
		String PAYMENT_CASH         = "1";  //,'现金',
		String PAYMENT_EWALLET      = "2";  //,'电子钱包',
		String PAYMENT_BANK         = "3";  //,'银行转帐',
		String PAYMENT_POOL         = "4";  //,'充值帐户',
		String PAYMENT_DEFICT       = "5";  //,'签单挂帐',
		String PAYMENT_CREDIT       = "6";  //,'信用卡支付'
		String PAYMENT_POINTS       = "7";  //,'积分支付'
		String PAYMENT_MANAGER      = "8";  //,'经理签单'
		String PAYMENT_POOL2        = "9";  //,'储值帐户二'
		String PAYMENT_POOL3        = "A";  //,'储值帐户三'
//		折扣相关
		String DISPAGETYPE_NORMAL   = "3";
		String DISPAGETYPE_MEMBER   =  "1";
		String DISPAGETYPE_CARDSALE =  "1";
		String DISPAGETYPE_CARDFILL =  "4";
		int DISMODE_ONLY_NORMAL     =   3;
		int DISMODE_ONLY_MEMBER     =   1;
		int DISMODE_ALL             =   2;

		int COMPID_CENTER       = 1;
		int COMPID_UTILITY      = 2;
		int COMPID_AREA         = 3;
		int COMPID_SHOP         = 4;

		//折扣相关
		int CT_STRING   = -3;
		String RIGHT_DELETE	 = "您没有删除权限";
		String RIGHT_ADD			= "您没有新增权限";
		String RIGHT_EDIT			= "您没有编辑权限";
		String RIGHT_SPECIAL		= "您没有特殊权限";
		
	}

