<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title></title>
  
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js" type="text/javascript"></script> 
 	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery/scrollbar.js" type="text/javascript"></script>
	<script language="JavaScript">
		 
		$(function ()
   		{
   			$("#bhandCardState").ligerButton(
	         {
	             text: '确认挂失(读经理卡)', width: 160,
		         click: function ()
		         {
		             bhandCardState();
		         }
	     	});
	     	document.getElementById("costCardNo").value=parent.document.getElementById("changebeforcardno").value;
	     });
	     
	     function bhandCardState()
	     {
	     	if(document.getElementById("handMemberName").value=="")
	     	{
	     		$.ligerDialog.error("请输入会员姓名!");
				return;
	     	}
	     	if(document.getElementById("handPhone").value=="" || document.getElementById("handPcid").value=="")
	     	{
	     		$.ligerDialog.error("请输入手机号码或身份证号!");
				return;
	     	}
	     	var CardControl=parent.parent.document.getElementById("CardCtrl");
			CardControl.Init(parent.parent.commtype,parent.parent.prot,parent.parent.password1,parent.parent.password2,parent.parent.password3);
			var cardNo=CardControl.ReadCard();
			if(cardNo=="")
			{
				$.ligerDialog.error("请初始化卡号");
				return;
	    	}
	     	var params ="mangerCardNo="+cardNo;	
	     	params=params+"&strCardNo="+document.getElementById("costCardNo").value;
	     	params=params+"&handMemberName="+document.getElementById("handMemberName").value;
	     	params=params+"&handPcid="+document.getElementById("handPcid").value;
	     	params=params+"&handPhone="+document.getElementById("handPhone").value;
	     	var requestUrl ="cc010/handCardState.action";
   			var responseMethod="handCardStateMessage";;
   			parent.sendRequestForParams_p(requestUrl,responseMethod,params)
	     }
	     
	     
	     function checknumber(number){
			var str=number;
			//在JavaScript中，正则表达式只能使用"/"开头和结束，不能使用双引号
			var Expression=/(P\d{7})|(G\d{8})/;
			var objExp=new RegExp(Expression);
			if(objExp.test(str)==true){
			   return true;
			}else{
			   return false;
			} 
		}
	     
	     
	    function validatePcid(obj)
	    {
	    	if(obj.value=="" || (obj.value.length!=15 && obj.value.length!=18 ) )
	    	{
	    		return;
	    	}
	    	
	    	 var card = obj.value;
			
			 //校验长度，类型
			 if(isCardNo(card) === false)
			 {
			 	$.ligerDialog.error('您输入的身份证号码不正确，请重新输入');
			 	obj.value="";
			 	return ;
			 }
			 //检查省份
			 if(checkProvince(card) === false)
			 {
			 	$.ligerDialog.error('您输入的身份证号码不正确,请重新输入');
			 	obj.value="";
			 	return ;
			 }
			 //校验生日
			 if(checkBirthday(card) === false)
			 {
			 	$.ligerDialog.error('您输入的身份证号码生日不正确,请重新输入');
			 	obj.value="";
			 	return ;
			 }
	    }
	    
		var vcity={ 11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古", 21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",
			33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",
		  	42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",
		  	51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",
		 	63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外" };
 
 	//检查号码是否符合规范，包括长度，类型
	 function isCardNo(card)
	 {
	 	//身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
	 	var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/;
	 	if(reg.test(card) === false)
	 	{
	 		return false;
	 	}
		return true;
	 }
	    
	 //15位转18位身份证号
	 function changeFivteenToEighteen(card)
	 {
		 if(card.length == '15')
		 {
		 var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
		 var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
		 var cardTemp = 0, i;
		 card = card.substr(0, 6) + '19' + card.substr(6, card.length - 6);
		 for(i = 0; i < 17; i ++)
		 {
		 cardTemp += card.substr(i, 1) * arrInt[i];
		 }
		 card += arrCh[cardTemp % 11];
		 return card;
		 }
		 return card;
	 }
	 
	 //检查生日是否正确
	 function checkBirthday(card)
	 {
			 var len = card.length;
			 //身份证15位时，次序为省（3位）市（3位）年（2位）月（2位）日（2位）校验位（3位），皆为数字
			 if(len == '15')
			 {
			 var re_fifteen = /^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/;
			 var arr_data = card.match(re_fifteen);
			 var year = arr_data[2];
			 var month = arr_data[3];
			 var day = arr_data[4];
			 var birthday = new Date('19'+year+'/'+month+'/'+day);
			 return verifyBirthday('19'+year,month,day,birthday);
			 }
			 //身份证18位时，次序为省（3位）市（3位）年（4位）月（2位）日（2位）校验位（4位），校验位末尾可能为X
			 if(len == '18')
			 {
			 var re_eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;
			 var arr_data = card.match(re_eighteen);
			 var year = arr_data[2];
			 var month = arr_data[3];
			 var day = arr_data[4];
			 var birthday = new Date(year+'/'+month+'/'+day);
			 return verifyBirthday(year,month,day,birthday);
			 }
			 return false;
	 }
	//校验日期
	 function verifyBirthday(year,month,day,birthday)
	 {
		 var now = new Date();
		 var now_year = now.getFullYear();
		 //年月日是否合理
		 if(birthday.getFullYear() == year && (birthday.getMonth() + 1) == month && birthday.getDate() == day)
		 {
		 //判断年份的范围（3岁到100岁之间)
		 var time = now_year - year;
		 if(time >= 3 && time <= 100)
		 {
		 return true;
		 }
		 return false;
		 }
		 return false;
	 }
	 
	 //取身份证前两位,校验省份
	function checkProvince(card)
	{
		 var province = card.substr(0,2);
		 if(vcity[province] == undefined)
		 {
		 return false;
		 }
		 return true;
	}
	</script>
	
</head>
<body style="padding:6px; overflow:hidden;">
	<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
	<table style="font: 12px;line-height: 16px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
		<tr><td width="80">会员卡号</td><td><input type="text" name="costCardNo" id="costCardNo"  readonly="true"  style="width:120;background:#EDF1F8;"/></td></tr>
		<tr><td><font color="red">*</font> 会员姓名 </td><td><input type="text" name="handMemberName" id="handMemberName"  style="width:120;" /></td></tr>
		<tr><td><font color="blue">*</font> 手机号码 </td><td><input type="text" name="handPhone" id="handPhone"   style="width:120;" /></td></tr>
		<tr><td><font color="blue">*</font> 身份证号 </td><td><input type="text" name="handPcid" id="handPcid"   style="width:160;" onchange="validatePcid(this)" /></td></tr>
		<tr><td colspan="2" align=center>&nbsp;</td></tr>
		<tr><td colspan="2" align=center><div id="bhandCardState"></div></td></tr>
	</table>	
	</div>

</body>
</html>
