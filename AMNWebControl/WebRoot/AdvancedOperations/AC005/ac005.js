//Report's query condition
var curComp="";
var dateFrom="";
var bills_kk ;
var bills_cz ;
var bills_hk ;
var bills_zkzk ;
var bills_sgzk ;
var bills_jzzk ;
var bills_bk ;
var bills_tk ;
var bills_sy ;
var bills_lcdh;
var ac005layout=null;
var showDialogmanager=null;
 	$(function ()
   	{
	   try
	   {
	   		$("#pageloading").hide(); 
	   		$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
            ac005layout= $("#ac005layout").ligerLayout({ leftWidth: 270,  allowBottomResize: false, allowLeftResize: false });
       		 $("#compAnlysis").ligerButton(
	         {
	             text: '营业汇总', width: 100,
		         click: function ()
		         {
		             handCompAnlysis();
		         }
	         });
	         $("#billAnlysis").ligerButton(
	         {
	             text: '单据分析', width: 100,
		         click: function ()
		         {
		             handBillAnlysis();
		         }
	         });
	         $("#overDayAnlysis").ligerButton(
	         {
	             text: '结算封帐', width: 100,
		         click: function ()
		         {
		             handOverDayAnlysis();
		         }
	         });
	         $("#closeForcibly").ligerButton(
	         {
	             text: '强行封帐', width: 100,
		         click: function ()
		         {
		             closeAccountForcibly();
		         }
	         });
	          $("#uncloseForcibly").ligerButton(
	         {
	             text: '强行解除封帐', width: 100,
		         click: function ()
		         {
		             uncloseAccountForcibly();
		         }
	         });
	        var today = new Date();
			var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
			var intDay=today.getDate();
			var today = intYear.toString()+"-"+fullStr(intMonth.toString())+"-"+fullStr(intDay.toString());
			document.getElementById("strFromDate").value=today;
			
       }catch(e){alert(e.message);}
    });
    
var canCloseAccount = null;
callbackForSelectSomeOrg = function(){
	canCloseAccount = null;
}
function setQueryParam()
{
	curComp = getCurOrgFromSearchBar();
	dateFrom = document.getElementById("strFromDate").value;
}
function genQueryCondition()
{
	var condition = "?";
		
	condition = condition + "strCurCompId="+curComp+"&";
	condition = condition + "strFromDate="+dateFrom+"&";
	condition = condition + "isSkipChain=false";
	
	return condition;
		
}

function queryCloseAccountLog()
{
	var url = "ac005/queryCloseAccountLog.action";

	setQueryParam();
	url = url + genQueryCondition();
	sendRequest(url,"processQueryCloseAccountLogResponse");
}



function sendRequest(url,responseMethod)
{
	try{
		var params = null;
		var myAjax = new parent.Ajax.Request(
					url,
					{
						method:'post',
						parameters:params,
						onComplete:eval(responseMethod),
						asynchronous:true
					});
	}
	catch(Exception)
	{
		return;
	}
}
function initErrorBills()
{
	bills_kk = null;
	bills_cz = null;
	bills_hk = null;
	bills_zkzk = null;
	bills_sgzk = null;
	bills_jzzk = null;
	bills_bk = null;
	bills_tk = null;
	bills_sy = null;
	bills_lcdh = null;
}
function existErrorBills(billtype,dataSet)
{
	var bills = new Array();
	for(var i=0;i<dataSet.length;i++)
	{
		if(dataSet[i].billType == billtype)
		{
			bills[bills.length] = dataSet[i];
		}
	}
	if(bills.length == 0)
	{
		return false;
	}
	else
	{
		if(billtype == "1") bills_kk = bills;
		else if(billtype == "2") bills_cz = bills;
		else if(billtype == "3") bills_hk = bills;
		else if(billtype == "4") bills_zkzk = bills;
		else if(billtype == "5") bills_sgzk = bills;
		else if(billtype == "6") bills_jzzk = bills;
		else if(billtype == "7") bills_bk = bills;
		else if(billtype == "8") bills_tk = bills;
		else if(billtype == "9") bills_sy = bills;
		else if(billtype == "10") bills_lcdh = bills;  
	
	}
	return true;
}

function uncloseAccountForcibly()
{
	var url = "ac005/uncloseAccountForcibly.action";
	setQueryParam();
	url = url + genQueryCondition();
	sendRequest(url,"uncloseAccountForciblymessage");
}
function uncloseAccountForciblymessage(request)
{
	var action = eval( "("+request.responseText+")");
	var strMessage = action.strMessage;
	var textareaAnalysisInfo = document.getElementById("analysisInfo");
	if(strMessage!="")
	{
		$.ligerDialog.error(strMessage);
		textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n"+strMessage;
		return;
	}
	else 
	{
		$.ligerDialog.success("强行解除封帐成功!");
		textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n强行解除封帐成功!";
	}
	setCaretAtEnd(textareaAnalysisInfo);
}

function closeAccountForcibly()
{
	var url = "ac005/closeAccountForcibly.action";
		
	setQueryParam();
	url = url + genQueryCondition();
	sendRequest(url,"processCloseAccountForciblyResponse");
}
function processCloseAccountForciblyResponse(request)
{
	var action = eval( "("+request.responseText+")");
	var closeAccountFlag = action.closeAccountFlag;
	var strMessage = action.strMessage;
	var textareaAnalysisInfo = document.getElementById("analysisInfo");
	if(strMessage!=""&&strMessage!="OP_SUCCESS")
	{
		$.ligerDialog.error(strMessage);
		textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n"+strMessage;
		return;
	}
	if(closeAccountFlag == "SUCCESS")
	{
		$.ligerDialog.success("强行结算封帐成功!");
		textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n强行结算封帐成功!";
	}
	else if(closeAccountFlag == "FAILURE")
	{
			$.ligerDialog.error("该日已经结算封帐，不能重新封帐!");
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n该日已经结算封帐，不能重新封帐！";
	}
	else
	{
		$.ligerDialog.error("系统在结算封帐过程中出现运行时异常,强行封帐失败!");
		textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n系统在结算封帐过程中出现运行时异常,强行封帐失败!";
	}
	setCaretAtEnd(textareaAnalysisInfo);
	
}

function handBillAnlysis()
{
	showDialogmanager = $.ligerDialog.waitting("正在分析当日单据.......");
    document.getElementById("analysisInfo").value="";
	var url = "ac005/analysisExceptionBills.action";
	setQueryParam();
	url = url + genQueryCondition();
	sendRequest(url,"handBillAnlysisMessage");
}
function handBillAnlysisMessage(request)
{
	try{
		var action = eval( "("+request.responseText+")");
		var dataSet = action.lsLogDate;
		var analysisFlag = action.analysisFlag;
		var closeAccountFlag = action.closeAccountFlag;
		var textareaAnalysisInfo = document.getElementById("analysisInfo");
		canCloseAccount = true;
		if(analysisFlag == "EXCEPTION")
		{
			document.getElementById("analysisInfo").value = document.getElementById("analysisInfo").value + "\n系统在分析当日单据过程中出现异常,已返回!";
			document.getElementById("analysisInfo").value = document.getElementById("analysisInfo").value + "\n结算封帐失败!";
			return;
		}
		var index = 0;
		initErrorBills();
		if(!existErrorBills("1",dataSet)||bills_kk == null)
		{
			//textareaAnalysisInfo.value = textareaAnalysisInfo.value+",未发现异常单据.";
		}
		else
		{	
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n分析<会员开卡>单据......";
			canCloseAccount = false;
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n发现异常单据:";
			for(index=0;index<bills_kk.length;index++)
			{
				textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n***"+bills_kk[index].errorId+"-"+bills_kk[index].errorDescription;
				textareaAnalysisInfo.value = textareaAnalysisInfo.value+",涉及单据(卡号)包括:"+bills_kk[index].remark;
			}
		}
		if(!existErrorBills("2",dataSet)||bills_cz == null)
		{
			//textareaAnalysisInfo.value = textareaAnalysisInfo.value+",未发现异常单据.";
		}
		else
		{
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n分析<会员卡充值>单据......";
			canCloseAccount = false;
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n发现异常单据:";
			for(index=0;index<bills_cz.length;index++)
			{
				textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n***"+bills_cz[index].errorId+"-"+bills_cz[index].errorDescription;
				textareaAnalysisInfo.value = textareaAnalysisInfo.value+",涉及单据(卡号)包括:"+bills_cz[index].remark;
			}
		}
		if(!existErrorBills("3",dataSet)||bills_hk == null)
		{
			//textareaAnalysisInfo.value = textareaAnalysisInfo.value+",未发现异常单据.";
		}
		else
		{
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n分析<还款>单据......";
			canCloseAccount = false;
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n发现异常单据:";
			for(index=0;index<bills_hk.length;index++)
			{
				textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n***"+bills_hk[index].errorId+"-"+bills_hk[index].errorDescription;
				textareaAnalysisInfo.value = textareaAnalysisInfo.value+",涉及单据(卡号)包括:"+bills_hk[index].remark;
			}
		}
		if(!existErrorBills("4",dataSet)||bills_zkzk == null)
		{
			//textareaAnalysisInfo.value = textareaAnalysisInfo.value+",未发现异常单据.";
		}
		else
		{
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n分析<折扣转卡>单据......";
			canCloseAccount = false;
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n发现异常单据:";
			for(index=0;index<bills_zkzk.length;index++)
			{
				textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n***"+bills_zkzk[index].errorId+"-"+bills_zkzk[index].errorDescription;
				textareaAnalysisInfo.value = textareaAnalysisInfo.value+",涉及单据(卡号)包括:"+bills_zkzk[index].remark;
			}
		}
		if(!existErrorBills("5",dataSet)||bills_sgzk == null)
		{
			//textareaAnalysisInfo.value = textareaAnalysisInfo.value+",未发现异常单据.";
		}
		else
		{
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n分析<收购转卡>单据......";
			canCloseAccount = false;
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n发现异常单据:";
			for(index=0;index<bills_sgzk.length;index++)
			{
				textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n***"+bills_sgzk[index].errorId+"-"+bills_sgzk[index].errorDescription;
				textareaAnalysisInfo.value = textareaAnalysisInfo.value+",涉及单据(卡号)包括:"+bills_sgzk[index].remark;
			}
		}
		if(!existErrorBills("6",dataSet)||bills_jzzk == null)
		{
			//textareaAnalysisInfo.value = textareaAnalysisInfo.value+",未发现异常单据.";
		}
		else
		{
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n分析<竞争转卡>单据......";
			canCloseAccount = false;
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n发现异常单据:";
			for(index=0;index<bills_jzzk.length;index++)
			{
				textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n***"+bills_jzzk[index].errorId+"-"+bills_jzzk[index].errorDescription;
				textareaAnalysisInfo.value = textareaAnalysisInfo.value+",涉及单据(卡号)包括:"+bills_jzzk[index].remark;
			}
		}
		if(!existErrorBills("7",dataSet)||bills_bk == null)
		{
			//textareaAnalysisInfo.value = textareaAnalysisInfo.value+",未发现异常单据.";
		}
		else
		{
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n分析<会员并卡>单据......";
			canCloseAccount = false;
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n发现异常单据:";
			for(index=0;index<bills_bk.length;index++)
			{
				textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n***"+bills_bk[index].errorId+"-"+bills_bk[index].errorDescription;
				textareaAnalysisInfo.value = textareaAnalysisInfo.value+",涉及单据(卡号)包括:"+bills_bk[index].remark;
			}
		}
		if(!existErrorBills("8",dataSet)||bills_tk == null)
		{
			//textareaAnalysisInfo.value = textareaAnalysisInfo.value+",未发现异常单据.";
		}
		else
		{
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n分析<会员退卡>单据......";
			canCloseAccount = false;
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n发现异常单据:";
			for(index=0;index<bills_tk.length;index++)
			{
				textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n***"+bills_tk[index].errorId+"-"+bills_tk[index].errorDescription;
				textareaAnalysisInfo.value = textareaAnalysisInfo.value+",涉及单据(卡号)包括:"+bills_tk[index].remark;
			}
		}
		if(!existErrorBills("9",dataSet)||bills_sy == null)
		{
			//textareaAnalysisInfo.value = textareaAnalysisInfo.value+",未发现异常单据.";
		}
		else
		{
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n分析<收银>单据......";
			canCloseAccount = false;
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n发现异常单据:";
			for(index=0;index<bills_sy.length;index++)
			{
				textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n***"+bills_sy[index].errorId+"-"+bills_sy[index].errorDescription;
				textareaAnalysisInfo.value = textareaAnalysisInfo.value+",涉及单据(卡号)包括:"+bills_sy[index].remark;
			}
		}
		if(!existErrorBills("10",dataSet)||bills_lcdh == null)
		{
			//textareaAnalysisInfo.value = textareaAnalysisInfo.value+",未发现异常单据.";
		}
		else
		{
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n分析<疗程兑换>单据......";
			canCloseAccount = false;
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n发现异常单据:";
			for(index=0;index<bills_lcdh.length;index++)
			{
				textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n***"+bills_lcdh[index].errorId+"-"+bills_lcdh[index].errorDescription;
				textareaAnalysisInfo.value = textareaAnalysisInfo.value+",涉及单据(卡号)包括:"+bills_lcdh[index].remark;
			}
		}
		if(!existErrorBills("11",dataSet))
		{
			//textareaAnalysisInfo.value = textareaAnalysisInfo.value+",未发现异常单据.";
		}
		else
		{
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n分析门店分类统计......";
			canCloseAccount = false;
			//textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n发现异常单据:";
			
				textareaAnalysisInfo.value=textareaAnalysisInfo.value+"\n***"+"分类业绩总业绩不等于美容业绩和美发业绩总和,请参考门店分类业绩!";
				//textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n***"+bills_lcdh[index].errorId+"-"+bills_lcdh[index].errorDescription;
				//textareaAnalysisInfo.value = textareaAnalysisInfo.value+",涉及单据(卡号)包括:"+bills_lcdh[index].remark;
			
		}
		textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n========================================================";
		if(!canCloseAccount)
		{
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n系统存在异常单据,无法结算封帐!请先处理异常单据!";		
		}
		else
		{
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n分析程序未发现异常单据,系统可以进行结算封帐!";
		}
		setCaretAtEnd(textareaAnalysisInfo);
		showDialogmanager.close();
	}catch(Exception)
	{
		alert(Exception.message);
		return ;
	}
}

function handOverDayAnlysis()
{
	if(canCloseAccount == null)
	{
		$.ligerDialog.error("结算封帐前，请先点击<当日单据分析>按钮执行分析程序，以确保当日无异常单据！");
		return;
	}
	var textareaAnalysisInfo = document.getElementById("analysisInfo");
	if(!canCloseAccount)
	{
		//textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n系统存在异常单据,无法结算封帐!请先处理异常单据!";
		setCaretAtEnd(textareaAnalysisInfo);
		if(confirm("系统存在异常单据,无法结算封帐!是否强行结算封帐？"))
		{
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n您选择了强行结算封帐；但仍然要提醒您，一般不建议强行结算封帐！";
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n系统正在强行结算封帐......";
			closeAccountForcibly();
		}
		return;
	}
	//document.getElementById("analysisInfo").value = "正在分析当日单据.......";
	var url ="ac005/handOverDayAnlysis.action";
		
	setQueryParam();
	url = url + genQueryCondition();
	sendRequest(url,"handOverDayAnlysismessage");
}

function handOverDayAnlysismessage(request)
{
	try{
		var action = eval( "("+request.responseText+")");
		if(checkNull(action.strMessage)!="")
		{
			$.ligerDialog.error(action.strMessage);
			return;
		}
		var analysisFlag = action.analysisFlag;
		var closeAccountFlag = action.closeAccountFlag;
		var textareaAnalysisInfo = document.getElementById("analysisInfo");
		if(closeAccountFlag == "SUCCESS")
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n系统结算封帐成功!";
		else if(closeAccountFlag == "FAILURE")
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n该日已经结算封帐，不能重新封帐！";
		else if(closeAccountFlag == "EXCEPTION")
			textareaAnalysisInfo.value = textareaAnalysisInfo.value+"\n系统在结算封帐过程中出现运行时异常,封帐失败,请重新结算封帐!";
		setCaretAtEnd(textareaAnalysisInfo);
	}catch(Exception)
	{
		alert(Exception.message);
		return ;
	}			
}

function handCompAnlysis()
{

	var url ="ac005/handCompAnlysis.action";
	setQueryParam();
	url = url + genQueryCondition();
	sendRequest(url,"handCompAnlysisMessage");
	
}


function handCompAnlysisMessage(request)
{

	try{
		var action = eval( "("+request.responseText+")");
		var dataSet = action.compAmtAnlysisBean;
		if(dataSet==null)
		{
				$.ligerDialog.warn("没有查到营业信息!");
				return;
		}
		document.getElementById("prj_cash").innerText = maskAmt(dataSet.prj_cash,2);
		document.getElementById("prj_bank").innerText = maskAmt(dataSet.prj_bank,2);
		document.getElementById("prj_check").innerText = maskAmt(dataSet.prj_check,2);
		document.getElementById("prj_card").innerText = maskAmt(dataSet.prj_card,2);
		document.getElementById("prj_sgcard").innerText = maskAmt(dataSet.prj_sgcard,2);
		document.getElementById("prj_jlqd").innerText = maskAmt(dataSet.prj_jlqd,2);
		document.getElementById("prj_point").innerText = maskAmt(dataSet.prj_point,2);
		document.getElementById("prj_qdgz").innerText = maskAmt(dataSet.prj_qdgz,2);

		document.getElementById("prj_prjdy").innerText = maskAmt(dataSet.prj_prjdy,2);
		document.getElementById("prj_cashdy").innerText = maskAmt(dataSet.prj_cashdy,2);
		document.getElementById("prj_carddy").innerText = maskAmt(dataSet.prj_carddy,2);
		document.getElementById("prj_finger").innerText = maskAmt(dataSet.prj_finger,2);
	    document.getElementById("prj_okcard").innerText = maskAmt(dataSet.prj_okcard,2);
		document.getElementById("prj_total").innerText = maskAmt(dataSet.prj_total,2);
		
		document.getElementById("goods_cash").innerText = maskAmt(dataSet.goods_cash,2);
		document.getElementById("goods_bank").innerText = maskAmt(dataSet.goods_bank,2);
		document.getElementById("goods_check").innerText = maskAmt(dataSet.goods_check,2);
		document.getElementById("goods_card").innerText = maskAmt(dataSet.goods_card,2);
		document.getElementById("goods_sgcard").innerText = maskAmt(dataSet.goods_sgcard,2);
		document.getElementById("goods_jlqd").innerText = maskAmt(dataSet.goods_jlqd,2);
		document.getElementById("goods_point").innerText = maskAmt(dataSet.goods_point,2);
		document.getElementById("goods_qdgz").innerText = maskAmt(dataSet.goods_qdgz,2);
		document.getElementById("goods_prjdy").innerText = maskAmt(dataSet.goods_prjdy,2);
		document.getElementById("goods_cashdy").innerText = maskAmt(dataSet.goods_cashdy,2);
		document.getElementById("goods_carddy").innerText = maskAmt(dataSet.goods_carddy,2);
		document.getElementById("goods_finger").innerText = maskAmt(dataSet.goods_finger,2);
	    document.getElementById("goods_okcard").innerText = maskAmt(dataSet.goods_okcard,2);
		document.getElementById("goods_total").innerText = maskAmt(dataSet.goods_total,2);
		
		document.getElementById("prj_goods_cash").innerText = maskAmt(dataSet.prj_goods_cash,2);
		document.getElementById("prj_goods_bank").innerText = maskAmt(dataSet.prj_goods_bank,2);
		document.getElementById("prj_goods_check").innerText = maskAmt(dataSet.prj_goods_check,2);
		document.getElementById("prj_goods_card").innerText = maskAmt(dataSet.prj_goods_card,2);
		document.getElementById("prj_goods_sgcard").innerText = maskAmt(dataSet.prj_goods_sgcard,2);
		document.getElementById("prj_goods_jlqd").innerText = maskAmt(dataSet.prj_goods_jlqd,2);
		document.getElementById("prj_goods_point").innerText = maskAmt(dataSet.prj_goods_point,2);
		document.getElementById("prj_goods_qdgz").innerText = maskAmt(dataSet.prj_goods_qdgz,2);
		document.getElementById("prj_goods_prjdy").innerText = maskAmt(dataSet.prj_goods_prjdy,2);
		document.getElementById("prj_goods_cashdy").innerText = maskAmt(dataSet.prj_goods_cashdy,2);
		document.getElementById("prj_goods_carddy").innerText = maskAmt(dataSet.prj_goods_carddy,2);
		document.getElementById("prj_goods_finger").innerText = maskAmt(dataSet.prj_goods_finger,2);
	    document.getElementById("prj_goods_okcard").innerText = maskAmt(dataSet.prj_goods_okcard,2);
		document.getElementById("prj_goods_total").innerText = maskAmt(dataSet.prj_goods_total,2);
		
		document.getElementById("kk_cash").innerText = maskAmt(dataSet.kk_cash,2);
		document.getElementById("kk_bank").innerText = maskAmt(dataSet.kk_bank,2);
		document.getElementById("kk_check").innerText = maskAmt(dataSet.kk_check,2);
		document.getElementById("kk_card").innerText = maskAmt(dataSet.kk_card,2);
		document.getElementById("kk_sgcard").innerText = maskAmt(dataSet.kk_sgcard,2);
		document.getElementById("kk_jlqd").innerText = maskAmt(dataSet.kk_jlqd,2);
		document.getElementById("kk_point").innerText = maskAmt(dataSet.kk_point,2);
		document.getElementById("kk_qdgz").innerText = maskAmt(dataSet.kk_qdgz,2);
		document.getElementById("kk_prjdy").innerText = maskAmt(dataSet.kk_prjdy,2);
		document.getElementById("kk_cashdy").innerText = maskAmt(dataSet.kk_cashdy,2);
		document.getElementById("kk_carddy").innerText = maskAmt(dataSet.kk_carddy,2);
		document.getElementById("kk_finger").innerText = maskAmt(dataSet.kk_finger,2);
	    document.getElementById("kk_okcard").innerText = maskAmt(dataSet.kk_okcard,2);
		document.getElementById("kk_total").innerText = maskAmt(dataSet.kk_total,2);
		
		document.getElementById("kcz_cash").innerText = maskAmt(dataSet.kcz_cash,2);
		document.getElementById("kcz_bank").innerText = maskAmt(dataSet.kcz_bank,2);
		document.getElementById("kcz_check").innerText = maskAmt(dataSet.kcz_check,2);
		document.getElementById("kcz_card").innerText = maskAmt(dataSet.kcz_card,2);
		document.getElementById("kcz_sgcard").innerText = maskAmt(dataSet.kcz_sgcard,2);
		document.getElementById("kcz_jlqd").innerText = maskAmt(dataSet.kcz_jlqd,2);
		document.getElementById("kcz_point").innerText = maskAmt(dataSet.kcz_point,2);
		document.getElementById("kcz_qdgz").innerText = maskAmt(dataSet.kcz_qdgz,2);
		document.getElementById("kcz_prjdy").innerText = maskAmt(dataSet.kcz_prjdy,2);
		document.getElementById("kcz_cashdy").innerText = maskAmt(dataSet.kcz_cashdy,2);
		document.getElementById("kcz_carddy").innerText = maskAmt(dataSet.kcz_carddy,2);
		document.getElementById("kcz_finger").innerText = maskAmt(dataSet.kcz_finger,2);
	    document.getElementById("kcz_okcard").innerText = maskAmt(dataSet.kcz_okcard,2);
		document.getElementById("kcz_total").innerText = maskAmt(dataSet.kcz_total,2);
		
		document.getElementById("zkzk_cash").innerText = maskAmt(dataSet.zkzk_cash,2);
		document.getElementById("zkzk_bank").innerText = maskAmt(dataSet.zkzk_bank,2);
		document.getElementById("zkzk_check").innerText = maskAmt(dataSet.zkzk_check,2);
		document.getElementById("zkzk_card").innerText = maskAmt(dataSet.zkzk_card,2);
		document.getElementById("zkzk_sgcard").innerText = maskAmt(dataSet.zkzk_sgcard,2);
		document.getElementById("zkzk_jlqd").innerText = maskAmt(dataSet.zkzk_jlqd,2);
		document.getElementById("zkzk_point").innerText = maskAmt(dataSet.zkzk_point,2);
		document.getElementById("zkzk_qdgz").innerText = maskAmt(dataSet.zkzk_qdgz,2);
		document.getElementById("zkzk_prjdy").innerText = maskAmt(dataSet.zkzk_prjdy,2);
		document.getElementById("zkzk_cashdy").innerText = maskAmt(dataSet.zkzk_cashdy,2);
		document.getElementById("zkzk_carddy").innerText = maskAmt(dataSet.zkzk_carddy,2);
		document.getElementById("zkzk_finger").innerText = maskAmt(dataSet.zkzk_finger,2);
	    document.getElementById("zkzk_okcard").innerText = maskAmt(dataSet.zkzk_okcard,2);
		document.getElementById("zkzk_total").innerText = maskAmt(dataSet.zkzk_total,2);
		
		
		document.getElementById("card_change_cash").innerText = maskAmt(dataSet.card_change_cash,2);
		document.getElementById("card_change_bank").innerText = maskAmt(dataSet.card_change_bank,2);
		document.getElementById("card_change_check").innerText = maskAmt(dataSet.card_change_check,2);
		document.getElementById("card_change_card").innerText = maskAmt(dataSet.card_change_card,2);
		document.getElementById("card_change_sgcard").innerText = maskAmt(dataSet.card_change_sgcard,2);
		document.getElementById("card_change_jlqd").innerText = maskAmt(dataSet.card_change_jlqd,2);
		document.getElementById("card_change_point").innerText = maskAmt(dataSet.card_change_point,2);
		document.getElementById("card_change_qdgz").innerText = maskAmt(dataSet.card_change_qdgz,2);
		document.getElementById("card_change_prjdy").innerText = maskAmt(dataSet.card_change_prjdy,2);
		document.getElementById("card_change_cashdy").innerText = maskAmt(dataSet.card_change_cashdy,2);
		document.getElementById("card_change_carddy").innerText = maskAmt(dataSet.card_change_carddy,2);
		document.getElementById("card_change_finger").innerText = maskAmt(dataSet.card_change_finger,2);
	    document.getElementById("card_change_okcard").innerText = maskAmt(dataSet.card_change_okcard,2);
		document.getElementById("card_change_total").innerText = maskAmt(dataSet.card_change_total,2);
		
		document.getElementById("tk_cash").innerText = maskAmt(dataSet.tk_cash,2);
		document.getElementById("tk_bank").innerText = maskAmt(dataSet.tk_bank,2);
		document.getElementById("tk_check").innerText = maskAmt(dataSet.tk_check,2);
		document.getElementById("tk_card").innerText = maskAmt(dataSet.tk_card,2);
		document.getElementById("tk_sgcard").innerText = maskAmt(dataSet.tk_sgcard,2);
		document.getElementById("tk_jlqd").innerText = maskAmt(dataSet.tk_jlqd,2);
		document.getElementById("tk_point").innerText = maskAmt(dataSet.tk_point,2);
		document.getElementById("tk_qdgz").innerText = maskAmt(dataSet.tk_qdgz,2);
		document.getElementById("tk_prjdy").innerText = maskAmt(dataSet.tk_prjdy,2);
		document.getElementById("tk_cashdy").innerText = maskAmt(dataSet.tk_cashdy,2);
		document.getElementById("tk_carddy").innerText = maskAmt(dataSet.tk_carddy,2);
		document.getElementById("tk_finger").innerText = maskAmt(dataSet.tk_finger,2);
	    document.getElementById("tk_okcard").innerText = maskAmt(dataSet.tk_okcard,2);
		document.getElementById("tk_total").innerText = maskAmt(dataSet.tk_total,2);
		
		
		document.getElementById("lcdh_cash").innerText = maskAmt(dataSet.lcdh_cash,2);
		document.getElementById("lcdh_bank").innerText = maskAmt(dataSet.lcdh_bank,2);
		document.getElementById("lcdh_total").innerText = maskAmt(dataSet.lcdh_total,2);
		
		
		document.getElementById("tmk_cash").innerText = maskAmt(dataSet.tmk_cash,2);
		document.getElementById("tmk_bank").innerText = maskAmt(dataSet.tmk_bank,2);
		document.getElementById("tmk_check").innerText = maskAmt(dataSet.tmk_check,2);
		document.getElementById("tmk_prjdy").innerText = maskAmt(dataSet.tmk_prjdy,2);
		document.getElementById("tmk_cashdy").innerText = maskAmt(dataSet.tmk_cashdy,2);
		document.getElementById("tmk_carddy").innerText = maskAmt(dataSet.tmk_carddy,2);
		document.getElementById("tmk_finger").innerText = maskAmt(dataSet.tmk_finger,2);
	    document.getElementById("tmk_okcard").innerText = maskAmt(dataSet.tmk_okcard,2);
		document.getElementById("tmk_total").innerText = maskAmt(dataSet.tmk_total,2);
		
		
		document.getElementById("totalIncome").innerText = maskAmt(dataSet.totalIncome,2);
		document.getElementById("totalDebt").innerText = maskAmt(dataSet.totalDebt,2);
		document.getElementById("realPerformance").innerText = maskAmt(dataSet.realPerformance,2);
		document.getElementById("virtualPerformance").innerText = maskAmt(dataSet.virtualPerformance,2);
		
	}catch(Exception)
	{
		alert(Exception.message);
		return ;
	}			
	
}
function processQueryCloseAccountLogResponse(request)
{
	var action = eval( "("+request.responseText+")");
	var dataSet = action.logDataSet;
	
	showData(dataSet);
}
function showData(dataSet)
{
	var result = new Array(7)
	if(dataSet.length == 0) {$.ligerDialog.error("没有查询出数据！");return;}
	
	var gridId = "logDataSet";
	document.getElementById("logShow").style.left = (screen.width-1000)/2;
	document.getElementById("logShow").style.display = "block";
	clearPreviousResultEX(gridId,0);
	//set detail
	for(var i=0;i<dataSet.length;i++)
	{
		
		result[0] = isnull(dataSet[i].id.gcp00c);
		result[1] = isnull(dataSet[i].id.gcp01d);
		result[2] = isnull(dataSet[i].gcp02c);
		result[3] = isnull(dataSet[i].gcp03d);
		result[4] = isnull(dataSet[i].gcp04t);
		result[5] = isnull(dataSet[i].gcp04c);
		result[6] = "";
		
		addRow(result,gridId);
		document.getElementById(gridId).rows[i].onclick=function(){OnSelectRow(gridId,this)};
	}

}
function OnSelectRow(tableId,selrow)
{  
	var grid = document.getElementById(tableId);
	for(var k=0;k<selrow.cells.length;k++)
	{
		selrow.cells[k].style.backgroundColor="#d3d3d3";
	}
	
	curRowIndex = rowIndex-1;
}
function closeLogShow()
{
	document.getElementById("logShow").style.display = "none";
}
function printCloseAccountLog()
{
	//document.all.WebBrowser.ExecWB(6,6);
	window.print();
}
function  setCaretAtEnd(field)     
{       
    if(field.createTextRange)     
    {       
    	var r = field.createTextRange();       
        r.moveStart('character',field.value.length);       
        r.collapse();       
        r.select();       
    }       
 } 