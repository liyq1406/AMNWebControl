var StandPrintControl = null;
var StandPrintBarControl = null;
var StandPrintErrorMsg = "";
var StandExistPrintControl = false;
var StandPrintCopies = 1;

//打印内容的类型枚举
var StandPRINT_CONTENT_TYPE_HTML  	 = 1;//HTML超文本
var StandPRINT_CONTENT_TYPE_TABLE 	 = 2;//表格
var StandPRINT_CONTENT_TYPE_URL   	 = 3;//URL
var StandPRINT_CONTENT_TYPE_TEXT  	 = 4;//纯文本
var StandPRINT_CONTENT_TYPE_IMAGE 	 = 5;//图片
var StandPRINT_CONTENT_TYPE_RECT  	 = 6;//矩形线
var StandPRINT_CONTENT_TYPE_ELLIPSE = 7;//椭圆线
var StandPRINT_CONTENT_TYPE_LINE	 = 8;//直线


StandPrintControl = parent.document.getElementById("PrintControl");
//--------------------------------------------------------------
function Stand_CheckPrintControl()
{
	try{
		var oldVersion=StandPrintControl.Version;
	   	var newVerion="4.4.3.0";	
	    if (oldVersion==null)
	   	{
			document.write("<h3><font color='#FF00FF'>打印控件未安装!点击这里<a href='ocx/install_lodop.exe'>执行安装</a>,安装后请刷新页面。</font></h3>");
			if(navigator.appName=="Netscape")
				document.write("<h3><font color='#FF00FF'>（Firefox浏览器用户需先点击这里<a href='ocx/npActiveXFirefox4x.xpi'>安装运行环境</a>）</font></h3>");
	   	} 
	   	else if (oldVersion<newVerion)
	   	{
			document.write("<h3><font color='#FF00FF'>打印控件需要升级!点击这里<a href='ocx/install_lodop.exe'>执行升级</a>,升级后请重新进入。</font></h3>");
		}
		else
		{
			StandExistPrintControl = true;
			StandPrintControl.SET_LICENSES("成都泰林科贸有限公司","649686669748688748719056235623","","");
		}
	}catch(e){

		StandExistPrintControl = false;
	}
}
//------------------------------------------------------------
function Stand_ExistPrintControl()
{
	return StandExistPrintControl;
}
//------------------------------------------------------------------------
/*
名称：打印初始化
格式：Stand_InitPrint(strTaskName)
功能：初始化运行环境，清理异常打印遗留的系统资源，设定打印任务名。
参数：
	strTaskName：
		打印任务名，字符型参数，由开发者自主设定，未限制长度，
		字符要求符合Windows文件起名规则，一般地，打印控件会根据该名记忆相关的打印设置、打印维护信息。
		注意：并不保证所有打印控件都具备记忆功能！
		若strTaskName空，控件则不保存本地化信息，打印全部由页面程序控制。
结果：返回逻辑值
	返回逻辑真表示初始化成功，逻辑假表示初始化失败，失败原因有：前一个打印事务没有完成；
	操作系统没有打印机(驱动)等。
建议或要求：
	该函数与SET_PRINT_PAPER都有初始化功能，每个打印事务至少初始化一次，建议打印程序首先调用该函数。
	任务名要尽量区别于其它打印任务，譬如用“XX单位_XX管理信息系统_XX子系统_XX模块_XX打印作业”字样。
	不希望最终用户更改打印布局时，则设strTaskName空。
*/
function Stand_InitPrint(strTaskName)
{
	var bRet = true;
	try{
		bRet = StandPrintControl.PRINT_INIT(strTaskName);
	}catch(e){
		StandPrintErrorMsg = "打印初始化失败！错误原因："+e.message;
		bRet = false;
	}
	return bRet;
}
//------------------------------------------------------------------------
/*
名称：设定纸张大小
格式：Stand_SetPrintPageSize(intOrient, intPageWidth,intPageHeight,strPageName)
功能：设定打印纸张为固定纸张，并设定其大小值或纸张类型名及打印方向。
参数：
	intOrient：
		设定打印方向，数字型，0(或其它)-默认方向1-纵(正)向打印 2-横向打印。
	intPageWidth：
		设定自定义纸张宽度，整数型，单位为0.1毫米。
	intPageHeight：
		设定自定义纸张高度，整数型，单位为0.1毫米。高或宽小于等于0时strPageName才起作用。
	strPageName：
		设为标准纸张，纸张类型名，字符型，只能在如下名称中选择，不限大小写：
		Letter, LetterSmall, Tabloid, Ledger, Legal,Statement, Executive, 
		A3, A4, A4Small, A5, B4, B5, Folio, Quarto, qr10X14, qr11X17, Note, 
		Env9, Env10, Env11, Env12,Env14, Sheet, DSheet, Esheet
	注： intPageWidth、intPageHeight 和strPageName都无效时，本函数对纸张大小不起作用，
		控件则采用所选打印机的默认纸张，但intOrient仍可起作用。
		如果打印程序未采用扩展方式（SET_PRINT_PAPER）初始化，本函数所定制的纸张大小，
		会起到SET_PRINT_PAPER中intWidth和intHeight的相同功能。
结果：无
建议或要求：
	打印初始化之后调用。
	如果打印纸张不固定，希望由操作者自主选择纸张时，则不要调用本函数。
*/
function Stand_SetPrintPageSize(intOrient, intPageWidth,intPageHeight,strPageName)
{
	try{
		StandPrintControl.SET_PRINT_PAGESIZE(intOrient, intPageWidth,intPageHeight,strPageName);
	}catch(e){
		StandPrintErrorMsg = "设置纸张大小错误！错误原因："+e.message;
	}
}
//-------------------------------------------------------------------------------
/*
名称：增加打印项
格式：Stand_AddPrintContent(intContentType,intTop,intLeft,intWidth,intHeight,strContent,intLineStyle, intLineWidth)
功能：增加超文本打印项，设定该打印项在纸张内的位置和区域大小，实现超文本控制打印。
参数：
	intContentType:
		打印项的类型，其值如下：
			StandPRINT_CONTENT_TYPE_HTML 	 ：HTML超文本
			StandPRINT_CONTENT_TYPE_TABLE 	 ：HTML超文本表格
			StandPRINT_CONTENT_TYPE_URL   	 ：URL
			StandPRINT_CONTENT_TYPE_TEXT  	 ：纯文本
			StandPRINT_CONTENT_TYPE_IMAGE 	 ：图片（获得图片的超文本（用标签IMG））
			StandPRINT_CONTENT_TYPE_RECT  	 ：矩形线
			StandPRINT_CONTENT_TYPE_ELLIPSE ：椭圆线
			StandPRINT_CONTENT_TYPE_LINE	 ：直线
	intTop：
		所增打印项在纸张内的上边距，整数型，单位是px。
		注意：intContentType = StandPRINT_CONTENT_TYPE_LINE时，该参数代表端点1的上边距。
	intLeft：
		所增打印项在纸张内的左边距，整数型，单位是px。
		注意：intContentType = StandPRINT_CONTENT_TYPE_LINE时，该参数代表端点1的左边距。
	intWidth：
		打印区域的宽度，整数型，单位是px。
		注意：intContentType = StandPRINT_CONTENT_TYPE_LINE时，该参数代表端点2的上边距。
	intHeight：
		打印区域的高度，整数型，单位是px，控件按这个值自动分页。
		注意：intContentType = StandPRINT_CONTENT_TYPE_TABLE时，intHeight为表格数据体(tbody)区域的高度
			 intContentType = StandPRINT_CONTENT_TYPE_LINE时，该参数代表端点2的左边距。
		
	strContent：
		打印内容，字符型，未限制长度。可以是一个完整的页面，也可以是代码段。
		
	*以下两个参数主要用于打印线性内容项
	intLineStyle：
		线条类型，数字型，0(或其它)代表实线 1－破折线 2－点线 3－点划线 4－双点划线
		缺省线条是实线。
	intLineWidth：
		线条宽，整数型，单位是px，缺省值是1px，非实线的线条宽也是1px。
	
结果：无
建议或要求：
	要求在打印初始化后调用，建议在画线类函数之后调用。
*/
function Stand_AddPrintContent(intContentType,intTop,intLeft,intWidth,intHeight,strContent,intLineStyle, intLineWidth)
{
	try{
		if(intContentType == StandPRINT_CONTENT_TYPE_HTML)
		{
			StandPrintControl.ADD_PRINT_HTM(intTop,intLeft,intWidth,intHeight,strContent);
		}
		else if(intContentType == StandPRINT_CONTENT_TYPE_TABLE)
		{
			StandPrintControl.ADD_PRINT_TABLE(intTop,intLeft,intWidth,intHeight,strContent);
		}
		else if(intContentType == StandPRINT_CONTENT_TYPE_URL)
		{
			StandPrintControl.ADD_PRINT_URL(intTop,intLeft,intWidth,intHeight,strContent);
		}
		else if(intContentType == StandPRINT_CONTENT_TYPE_TEXT)
		{
			StandPrintControl.ADD_PRINT_TEXT(intTop,intLeft,intWidth,intHeight,strContent);
		}
		else if(intContentType == StandPRINT_CONTENT_TYPE_IMAGE)
		{
			StandPrintControl.ADD_PRINT_IMAGE(intTop,intLeft,intWidth,intHeight,strContent);
		}
		else if(intContentType == StandPRINT_CONTENT_TYPE_RECT)
		{
			StandPrintControl.ADD_PRINT_RECT(intTop, intLeft, intWidth, intHeight,intLineStyle, intLineWidth);	
		}
		else if(intContentType == StandPRINT_CONTENT_TYPE_ELLIPSE)
		{
			StandPrintControl.ADD_PRINT_ELLIPSE(intTop, intLeft, intWidth, intHeight, intLineStyle, intLineWidth);
		}
		else if(intContentType == StandPRINT_CONTENT_TYPE_LINE)
		{
			StandPrintControl.ADD_PRINT_LINE(intTop, intLeft, intWidth, intHeight,intLineStyle, intLineWidth);
		} 
	}catch(e){
		StandPrintErrorMsg = "增加打印内容错误！错误原因："+e.message;
	}
}
//------------------------------------------------------------------------------------------
/*
名称：设置打印项风格
格式：
	Stand_SetPrintStyle(strStyleName,varStyleValue)
功能：设置打印项的输出风格，成功执行该函数，此后再增加的打印项按此风格输出。
参数：
	strStyleName：打印风格名，风格名称及其含义如下：
		“FontName”： 设定文本打印项的字体名称。
		“FontSize”： 设定文本打印项的字体大小。
		“FontColor”： 设定文本打印项的字体颜色。
		“Bold”： 设定文本打印项是否粗体。
		“Italic”： 设定文本打印项是否斜体。
		“Underline”： 设定文本打印项是否下滑线。
		“Alignment”： 设定文本打印项的内容左右靠齐方式。
		“Angle”： 设定文本打印项的旋转角度。
		“ItemType”：设定打印项的基本属性。
		“HOrient”：设定打印项在纸张内的水平位置锁定方式。
		“VOrient”：设定打印项在纸张内的垂直位置锁定方式。
		“PenWidth”：线条宽度。
		“PenStyle”：线条风格。
		“Stretch”：图片截取缩放模式。
	varStyleValue：打印风格值，相关值如下：
		FontName的值： 字符型，与操作系统字体名一致，缺省是“宋体”。
		FontSize的值：数字型，单位是pt，缺省值是9。
		FontColor的值：整数或字符型，整数时是颜色的十进制RGB值；字符时是超文本颜色值，可以是“#”加三色16进制值组合，也可以是英文颜色名；
		Bold的值：数字型，1代表粗体，0代表非粗体，缺省值是0。
		Italic的值：数字型，1代表斜体，0代表非斜体，缺省值是0。
		Underline的值：数字型，1代表有下划线，0代表无下划线，缺省值是0。
		Alignment的值：数字型，1(或其它)-左靠齐 2-居中 3-右靠齐，缺省值是1。
		Angle的值：数字型，逆时针旋转角度数，单位是度，0度表示不旋转。
		ItemType的值：数字型，0(或其它)-普通项 1-页眉页脚 2-页号项 3-页数项 4-多页项 
				缺省（不调用本函数时）值0。普通项只打印一次；页眉页脚项则每页都在固定位置重复打印；
				页号项和页数项是特殊的页眉页脚项，其内容包含当前页号和全部页数；多页项每页都打印，
				直到把内容打印完毕，打印时在每页上的位置和区域大小固定一样（多页项只对纯文本有效）
		HOrient的值：数字型，0(或其它) -左边距锁定 1-右边距锁定 2-水平方向居中 3-左边距和右边距同时锁定（中间拉伸），缺省值是0。
		VOrient的值：数字型，0(或其它)-上边距锁定 1-下边距锁定 2-垂直方向居中 3-上边距和下边距同时锁定（中间拉伸），缺省值是0。
		PenWidth的值：整数型，单位是px，缺省值是1px，非实线的线条宽也是1px。
		PenStyle的值：数字型，0(或其它)代表实线 1-破折线 2-点线 3-点划线 4-双点划线 
				缺省值是0。
		Stretch的值：数字型，0(或其它)截取图片 1扩展（可变形）缩放 2-按原图长和宽比例（不变形）缩放。缺省值是0。
结果：无
建议或要求：
	打印初始化后、增加打印项之前调用本函数。
*/
function Stand_SetPrintStyle(strStyleName,varStyleValue)
{
	try{
		StandPrintControl.SET_PRINT_STYLE(strStyleName,varStyleValue);
	}catch(e){
		StandPrintErrorMsg = "设置打印项风格错误！错误原因："+e.message;
	}
}
//---------------------------------------------------------------------------
/*
名称：强制分页
格式：Stand_NewPrintPage()
功能：强制分页。执行该函数之后所增加的内容会在下一页输出。在执行该函数之前，需要至少有一项内容，否则该函数不动作。
参数：无
结果：返回逻辑值
	返回逻辑真表示强制分页成功，逻辑假表示强制分页失败。
建议或要求：
	打印初始化后调用本函数。
*/
function Stand_NewPrintPage()
{
	try{
		StandPrintControl.NEWPAGE();
	}catch(e){
		StandPrintErrorMsg = "强制分页错误！错误原因："+e.message;
	}
}
//---------------------------------------------------------------------------
/*
名称：打印预览
格式：Stand_Preview()
功能：打印预览输出页。
参数：无
结果：弹出打印预览界面，详见打印预览说明。
建议或要求：
	建立打印页之后运行。
*/
function Stand_Preview()
{
	try{
		StandPrintControl.PREVIEW();
	}catch(e){
		StandPrintErrorMsg = "打印预览错误！错误原因："+e.message;
	}
}
//---------------------------------------------------------------------------
/*
名称：直接打印
格式：Stand_Print()
功能：不经打印预览的直接打印。
参数：无
结果：打印机开始实际打印，返回逻辑结果，正确打印时返回真，打印出错时返回假。
建议或要求：
	建立打印页之后运行。
*/
function Stand_Print()
{	
	if(Stand_ExistPrintControl() == false)
	{
		window.print();
	}
	try{
		StandPrintControl.PRINT();
	}catch(e){
		StandPrintErrorMsg = "打印错误！错误原因："+e.message;
	}
}
//---------------------------------------------------------------------------
/*
名称：打印维护
格式：Stand_PrintSetup()
功能：对整页的打印布局和打印风格进行界面维护，它与打印设计的区别是不具有打印项增删功能，目标使用者是最终用户。
参数：无
结果：弹出打印维护界面，详见打印维护说明。
建议或要求：
建立打印页之后运行。
*/
function Stand_PrintSetup()
{
	try{
		StandPrintControl.PRINT_SETUP();
	}catch(e){
		StandPrintErrorMsg = "调用打印维护错误！错误原因："+e.message;
	}
}
//----------------------------------------------------------------------------
/*
名称：打印设计
格式：Stand_PrintDesign()
功能：对整页的打印布局和打印风格进行界面设计，它与打印维护的区别是具有打印项增删功能，目标使用者是开发者。
参数：无
结果：弹出打印设计界面，详见打印设计说明。
建议或要求：
	建立打印页之后运行。
*/
function Stand_PrintDesign()
{
	try{
		StandPrintControl.PRINT_DESIGN();
	}catch(e){
		StandPrintErrorMsg = "调用打印设计错误！错误原因："+e.message;
	}
}
//---------------------------------------------------------------------------
/*
名称：获得打印设备个数
格式：GET_PRINTER_COUNT()
功能：获得操作系统内打印设备的个数。
参数：无
结果：返回数字
	返回数字结果表示操作系统内的打印设备个数，0表示失败或无打印设备。
建议或要求：
	任何时间调用均可。
*/
function Stand_GetPrinterCount()
{
	var count = 0;
	try{
		count = StandPrintControl.GET_PRINTER_COUNT();
	}catch(e){
		StandPrintErrorMsg = "Stand_GetPrinterCount错误！错误原因："+e.message;
	}
	return count;
}
//------------------------------------------------------------------------
/*
名称：获得打印设备名称
格式：Stand_GetPrinterName(intPrinterIndex)
功能：按打印设备序号获得其名称。
参数：
	intPrinterIndex：
	打印设备序号，数字型，序号从0开始，最大序号是GET_PRINTER_COUNT()减1。
结果：返回字符
	返回字符结果表示操作系统内的打印设备的名称，空表示失败或无该设备。
建议或要求：
	任何时间调用均可。
*/
function Stand_GetPrinterName(intPrinterIndex)
{
	var printerName = "";
	try{
		printerName = StandPrintControl.GET_PRINTER_NAME(intPrinterIndex);
	}catch(e){
		StandPrintErrorMsg = "调用Stand_GetPrinterName错误！错误原因："+e.message;
	}	
	return printerName;
}
//----------------------------------------------------------------------
/*
名称：指定打印设备
格式：Stand_SetPrinterIndex(intIndex)
功能：指定用某打印设备输出。
参数：
intIndex：
	所指定打印设备的序号，数字型，序号从0开始，最大序号是GET_PRINTER_COUNT()减1。
结果：返回逻辑值
	返回逻辑真表示指定成功，逻辑假表示指定失败，失败原因有：该打印设备不存在。
建议或要求：
	初始化之后，直接打印前或打印预览前调用。

*/
function Stand_SetPrinterIndex(intIndex)
{
	try{
		StandPrintControl.SET_PRINTER_INDEX(intIndex);
	}catch(e){
		StandPrintErrorMsg = "调用Stand_SetPrinterIndex错误！错误原因："+e.message;
	}
}
//-----------------------------------------------------------------------
/*
名称：选择打印设备
格式：Stand_SelectPrinter
功能：弹出界面选定某打印设备为固定输出设备。
参数：无
结果：返回数字
	返回数字结果表示选定的设备序号，返回-1表示放弃选择，没有任何动作。
建议或要求：
	直接打印前或打印预览前调用。本函数与SET_PRINTER_INDEX的功能效果一样。
*/
function Stand_SelectPrinter()
{
	var intPrinterIndex = -1;
	try{
		intPrinterIndex = StandPrintControl.SELECT_PRINTER;
	}catch(e){
		StandPrintErrorMsg = "调用Stand_SelectPrinter错误！错误原因："+e.message;
	}
	return intPrinterIndex;
}
//-------------------------------------------------------------------------
/*
名称：设置显示模式
格式：
	Stand_SetPrintShowMode(strModeType,varModeValue)
功能：设置打印预览、打印维护和打印设计的显示模式，设置打印预览时是否包含背景图等。
参数：
	strModeType 显示模式的名称，字符型，如下是类型名及其含义：
		“PREVIEW_IN_BROWSE”：打印预览界面是否内嵌到网页内部。
		“SETUP_IN_BROWSE”：  打印维护界面是否内嵌到网页内部。
		“DESIGN_IN_BROWSE”： 打印设计界面是否内嵌到网页内部。
		“StandIMG_IN_PREVIEW”： 打印预览时是否包含背景图。
		“StandIMG_IN_FIRSTPAGE”：打印预览时是否仅首页包含背景图。
		“SETUP_ENABLESS”：打印维护界面工具显示控制（权限控制字串）
		“SKIN_TYPE”：界面皮肤类型
		“SKIN_CUSTOM_COLOR”：界面自定义皮肤颜色
		“HTM_FONT_ZOOM”：超文本的字体缩放级别
		“SHOW_MODAL”：控件的打印维护及打印设计的窗口modal模式

	varModeValue 显示模式的值，整数或字符型，相关值如下：
		PREVIEW_IN_BROWSE的值：整数或字符型，1或“1”或“True”=是,否则不是。
		SETUP_IN_BROWSE的值：整数或字符型，1或“1”或“True”=是,否则不是。
		DESIGN_IN_BROWSE的值：整数或字符型，1或“1”或“True”=是,否则不是。
		StandIMG_IN_PREVIEW的值：整数或字符型，1或“1”或“True”=是,否则不是。
		StandIMG_IN_FIRSTPAGE的值：整数或字符型，1或“1”或“True”=是,否则不是。
		SETUP_ENABLESS的值：字符型，由“1”和“0”组成的字符串，最多14个字符，
		按如下顺序控制打印维护的界面功能，“1”-允许，“0”-禁止：
		位置移动和宽高调整1+颜色选择2+字体名选择3+字大小选择4+旋角调整5+粗斜体功能条6+线型功能条7+对齐功能条8+删除功能9+页眉设置10+页脚设置11+位置锁定功能12+ 属性设置13+显示关闭钮（界面内嵌时）14
		缺省的SETUP_ENABLES值：“11111111000001”
		例如：如想允许操作者“删除”对象，可以执行如下语句;
		LODOP.SET_SHOW_MODE("SETUP_ENABLES","11111111100001");
		SKIN_TYPE的值：整数型，固定皮肤如下：
		0-银灰色(缺省)；1-经典绿; 2-熏衣草紫；3: -淡钢青；4-茶色棕；5-茶色棕；
		6-麦色；7-紫罗兰；8-天蓝；9-镀银；10-沙滩棕；11-鲜肉色；12-粉末蓝；13-钒矿色；
		14-浅绿；15-浅蓝；16-卡其布； 17-秋麒麟；18-深海绿；19-深卡其布；20-番茄桔
		SKIN_CUSTOM_COLOR的值：整数或字符型，整数时是颜色的十进制RGB值；字符时是超文本颜色值，可以是“#”加三色16进制值组合，也可以是英文颜色名。
		HTM_FONT_ZOOM的值：整数型，表示超文本整体缩放程度，类似浏览器的“文字大小”的设置。0-最小 1-较小 2-中 3-较大 4-最大  缺省值是2
		SHOW_MODAL的值：整数或字符型，1或“1”或“True”=是,否则不是。控件窗口按modal模式显示时，后面的脚本代码在控件窗口退出之前不执行。这种方式适合把控件放到独立的浏览器窗口中自动执行，并希望浏览器窗口在控件窗口关闭时也随之关闭的情况等。

结果：返回逻辑结果，成功时返回真，失败时返回假。
建议或要求：
	初始化之后，进入功能（打印预览、打印维护或打印设计）界面前调用本函数。
*/
function Stand_SetPrintShowMode(strModeType,varModeValue)
{
	var bRet = false;
	try
	{
		bRet = StandPrintControl.SET_SHOW_MODE(strModeType,varModeValue);
	}catch(e){
		StandPrintErrorMsg = "调用Stand_SetPrintShowMode错误！错误原因："+e.message;
	}
	return bRet;
}
//--------------------------------------------------------------------------------
/*
名称：设置打印模式
格式：
	Stand_SetPrintMode (strModeType,varModeValue)
功能：设置人工双面打印模式等。
参数：
	strModeType模式类型名，字符型，如下是类型名及其含义：
	“DOUBLE_SIDED_PRINT”：设置是否人工双面打印。
	varModeValue模式类型值，整数或字符型，相关值如下：
	DOUBLE_SIDED_PRINT的值：整数或字符型，1或“1”或“True”=是,否则不是。
结果：返回逻辑结果，成功时返回真，失败时返回假。
建议或要求：
	直接打印前或打印预览前调用。
*/
function Stand_SetPrintMode(strModeType,varModeValue)
{
	var bRet = true;
	try{
		bRet = StandPrintControl.SET_PRINT_MODE(strModeType,varModeValue);
	}catch(e){
		StandPrintErrorMsg = "调用Stand_SetPrintMode错误！错误原因："+e.message;
		bRet = false;
	}
	return bRet;
}
//-----------------------------------------------------------------------------
/*
名称：设置预览窗口
格式：Stand_SetPreviewWindow(intDispMode, intToolMode,blDirectPrint,inWidth,intHeight,strTitleButtonCaptoin)
功能：设置预览窗口的显示模式和大小。
参数：
	intDispMode 预览比例，数字型，0-适高1-正常大小2-适宽。
	intToolMode工具条和按钮，数字型 0-显示工具条1-显示按钮 2-两个都显示 3-两个都不显示 
	blDirectPrint打印按钮是否“直接打印” 1-是 0-否（弹出界面“选机打印”）
	inWidth   窗口宽，整数型，单位是px  
	intHeight  窗口高，整数型，单位是px
	当inWidth 或intHeight 小于等于0时窗口最大化。 
	strTitleButtonCaptoin  预览窗口和打印按钮的名称组合，字符型，用“点”分隔，譬如“预览查看.开始打印”，表示预览窗口的标题是“预览查看”，按钮名是“开始打印”。
结果：无
建议或要求：
	直接打印前或打印预览前调用。

*/
function Stand_SetPreviewWindow(intDispMode, intToolMode,blDirectPrint,inWidth,intHeight,strTitleButtonCaptoin)
{
	try{
		StandPrintControl.SET_PREVIEW_WINDOW(intDispMode,intToolMode,blDirectPrint,inWidth,intHeight,strTitleButtonCaptoin);
	}catch(e){
		StandPrintErrorMsg = "调用Stand_SetPreviewWindow错误！错误原因："+e.message;
	}
}
//---------------------------------------------------------------------------
/*
名称：指定背景图
格式：ADD_PRINT_SETUP_StandIMG(strImgHtml)
功能：用程序方式指定打印维护或打印设计的背景图。
参数：
	strImgHtml：
		获得背景图的超文本代码，字符型，用IMG标签设定图片的URL和大小。
结果：无
建议或要求：
	初始化之后调用。
*/
function Stand_AddPrintSetupStandImg(strImgHtml)
{
	try{
		StandPrintControl.ADD_PRINT_SETUP_StandIMG(strImgHtml);
	}catch(e){
		StandPrintErrorMsg = "调用Stand_AddPrintSetupStandImg错误！错误原因："+e.message;
	}
}
//-------------------------------------------------------------------------
/*
名称：发送原始数据
格式：Stand_SendPrintRawData(strRawData)
功能：向打印机发送原始数据或指令。
参数：
	strRawData：数据或指令值，字符型，未限制长度。
结果：返回逻辑结果，发送成功时返回真，发送失败时返回假。
建议或要求：
	任何时间调用。	
*/
function Stand_SendPrintRawData(strRawData)
{
	var bRet = true;
	try{
		bRet = StandPrintControl.SEND_PRINT_RAWDATA(strRawData);
	}catch(e){
		StandPrintErrorMsg = "调用Stand_SendPrintRawData错误！错误原因："+e.message;
		bRet = false;
	}
	return bRet;
}
//-------------------------------------------------------------------------
/*
名称：写端口数据
格式：Stand_WritePrintPortData(strPortName,strData)
功能：直接向端口写数据或指令。
参数：
	strPortName：端口名，同操作系统的端口名，名称如下：
	LPT1、LPT2、LPT3、COM1、COM2、COM3…
	strData：数据或指令值，字符型，未限制长度。
	当设置端口通讯参数时strData格式如下：
	mode com1:波特率,校验,数据位,停止位,读时限,写时限
	其中mode为固定关键字，com1要和strPortName保持一直。
	校验值有：N(noparity) O(oddparity) E(evenparity) M(markparity) S(spaceparity)
	读时限和写时限的时间单位为毫秒，举例如下：
	WRITE_PORT_DATA(“com1”,“mode com1:2400,n,8,1”)
	或WRITE_PORT_DATA(“com2”,“mode com2:2400,n,7,2,5000,2000”)
结果：返回逻辑结果，发送成功时返回真，发送失败时返回假。
建议或要求：
	任何时间调用。	
*/
function Stand_WritePrintPortData(strPortName,strData)
{
	var bRet = true;
	try{
		bRet = StandPrintControl.WRITE_PORT_DATA(strPortName,strData);
	}catch(e){
		StandPrintErrorMsg = "调用Stand_WritePortData错误！错误原因："+e.message;
		bRet = false;
	}
	return bRet;
}
//-----------------------------------------------------------------------
/*
名称：读端口数据
格式：Stand_ReadPrintPortData(strPortName)
功能：直接从端口读数据。
参数：
	strPortName：端口名，同操作系统的端口名，名称如下：
	LPT1、LPT2、LPT3、COM1、COM2、COM3…
结果：返回字符数据。
建议或要求：
	任何时间调用
*/
function Stand_ReadPrintPortData(strPortName)
{
	var strData = "";
	try{
		strData = StandPrintControl.READ_PORT_DATA(strPortName);
	}catch(e){
		StandPrintErrorMsg = "调用Stand_ReadPrintPortData错误！错误原因："+e.message;
	}
	return strData;
}
//---------------------------------------------------------------------
/*
名称：获得配置文件名
格式：Stand_GetPrintCfgFileName(strPrintTask)
功能：获得某打印任务的本地配置文件全路径名。
参数：
	strPrintTask：
	打印任务名，字符型，即初始化时所设的任务名。
结果：返回字符
	返回字符结果表示本地配置文件全路径名（并非文件内容），空表示失败。
建议或要求：
	在初始化之后调用。
*/
function Stand_GetPrintCfgFileName(strPrintTask)
{
	var cfgFileName = "";
	try{
		cfgFileName = StandPrintControl.GET_PRINT_INIFFNAME (strPrintTask);
	}catch(e){
		StandPrintErrorMsg = "调用Stand_GetPrintCfgFileName错误！错误原因："+e.message;
	}
	return cfgFileName;
}
//--------------------------------------------------------------------
/*
名称：写本地文件内容
格式：Stand_LocalFileWrite(intWriteMode,strFileName, strText)
功能：向本地文件写入文本内容。
参数：
	intWriteMode：
	写入模式，数字型，0(或其它)-文件覆盖模式 1-文件尾追加模式 2-文件首插入模式。
	strFileName：
	本地文件名，字符型，文件名包含全路径。
	strText：
	写入的文本内容，字符型。
	结果：调用函数后控件启动安全提示，等待操作许可。
	返回字符值表示写入情况：
		true - 
		false -
建议或要求：
	任何时间调用。
*/
function Stand_LocalFileWrite(intWriteMode,strFileName, strText)
{
	var bRet = true;
	var strRet = "";
	try{
		strRet = StandPrintControl.WRITE_FILE_TEXT(intWriteMode,strFileName, strText);
	}catch(e){
		StandPrintErrorMsg = "调用Stand_FileWrite错误！错误原因："+e.message;
		return false;
	}
	
	if(strRet == "ok")
	{
		bRet = true;
	}
	else if(strRet == "file not exist")
	{
		StandPrintErrorMsg = "写文件错误:文件不存在!";
		bRet = false;
	}
	else if(strRet == "do nothing")
	{
		StandPrintErrorMsg = "写文件错误.未写入，一般原因有：操作者禁止读写、文件只读属性等";
		bRet = false;
	}
	return bRet;
}
//--------------------------------------------------------------------------------
/*
名称：读本地文件内容
格式：Stand_LocalFileRead(strFileName)
功能：读本地文件文本内容。
参数：
	strFileName：
	本地文件名，字符型，含全路径。
结果：调用函数后控件启动安全提示，等待操作许可。
	返回字符值，文本内容。
返回空原因：文件不存在；内容真实空；操作者禁止读写；
建议或要求：
	任何时间调用。

*/
function Stand_LocalFileRead(strFileName)
{
	var strText = "";
	try{
		strText = StandPrintControl.GET_FILE_TEXT(strFileName);
	}catch(e){
		StandPrintErrorMsg = "读写文件错误.错误原因："+e.message;
	}
	return strText;
}
//--------------------------------------------------------------------------------
/*
名称：读本地文件时间
格式：Stand_LocalFileLatestModifTime(strFileName)
功能：读本地文件最后修改时间。
参数：
	strFileName：
		本地文件名，字符型，含全路径。
结果：返回字符值，最后修改时间，时间样式：yyyy-mm-dd hh:mm:ss。
	返回空原因：文件不存在；
建议或要求：
	任何时间调用。
*/
function Stand_LocalFileLatestModifTime(strFileName)
{
	var strTime = "";
	try{
		strTime = StandPrintControl.GET_FILE_TEXT(strFileName);
	}catch(e){
		StandPrintErrorMsg = "调用Stand_LocalFileLatestModifTime错误.错误原因："+e.message;
	}
	if(strTime == ""||strTime == null)
	{
		StandPrintErrorMsg = "读本地文件最后修改时间错误.错误原因：文件不存在；"	
	}
	return strTime;
}
//--------------------------------------------------------------------------------
/*
名称：判断本地文件是否存在
格式：IS_FILE_EXIST (strFileName)
功能：判断本地文件是否存在。
参数：
	strFileName：
	本地文件名，字符型，含全路径。
结果：返回逻辑值，逻辑真表示文件存在，逻辑假表示文件不存在。 
建议或要求：
	任何时间调用。
*/
function Stand_LocalFileExist(strFileName)
{
	var bRet = true;
	try{
		bRet = StandPrintControl.IS_FILE_EXIST(strFileName);
	}catch(e){
		StandPrintErrorMsg = "调用Stand_LocalFileExist错误！错误原因："+e.message;
		bRet = false;
	}
	return bRet;
}



