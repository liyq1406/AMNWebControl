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
 	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
 	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery/scrollbar.js" type="text/javascript"></script>
	<script language="JavaScript">
		 
		$(function ()
   		{
   			$("#changeBySameLevel").ligerButton(
	         {
	             text: '确认调动', width: 120,
		         click: function ()
		         {
		             changeBySameLevel();
		         }
	     	});
	     	var today = new Date();
			var intYear=today.getYear();
	
			var intMonth=today.getMonth()+1;
		
			var intDay=today.getDate();
			var today = intYear.toString()+"-"+fullStr(intMonth.toString())+"-"+fullStr(intDay.toString());
			document.getElementById("dispatchdate").value=today;
			document.getElementById("oldcompno").value=parent.document.getElementById("compno").value;
			document.getElementById("oldstaffno").value=parent.document.getElementById("staffno").value;
			document.getElementById("oldstaffinid").value=parent.document.getElementById("manageno").value;
			//document.getElementById("oldstaffname").value=parent.document.getElementById("staffname").value;
			
			
			addOption("","",document.getElementById("olddepid"));
			addOption("","",document.getElementById("oldpostion"));
			addOption("","",document.getElementById("oldyjtype"));
			
			//----------部门资料
			var bmzl=parent.parent.gainCommonInfoByCode("BMZL",0);
		    for(var i=0;i<bmzl.length;i++)
			{
				addOption(bmzl[i].bparentcodekey,bmzl[i].parentcodevalue,document.getElementById("olddepid"));
			}
			document.getElementById("olddepid").value=parent.document.getElementById("department").value;
			
			//----------工作岗位
			var gzgw=parent.parent.gainCommonInfoByCode("GZGW",0);
		    for(var i=0;i<gzgw.length;i++)
			{
				addOption(gzgw[i].bparentcodekey,gzgw[i].parentcodevalue,document.getElementById("oldpostion"));
			}
			document.getElementById("oldpostion").value=parent.document.getElementById("position").value;
			
			
			//----------业绩方式
			var yitype=parent.parent.gainCommonInfoByCode("YJFS",0);
			for(var i=0;i<yitype.length;i++)
			{
				addOption(yitype[i].bparentcodekey,yitype[i].parentcodevalue,document.getElementById("oldyjtype"));
			}
			document.getElementById("oldyjtype").value=parent.document.getElementById("resulttye").value;
			
			//---------基本工资,业绩比率,业绩系数,业务方式
			document.getElementById("oldSalary").value=parent.document.getElementById("basesalary").value;
			document.getElementById("oldyjrate").value=parent.document.getElementById("resultrate").value;
			document.getElementById("oldyjamt").value=parent.document.getElementById("baseresult").value;
			document.getElementById("oldbusinessflag").value=parent.document.getElementById("businessflag").value;
			//----------工作职称
			var gzzc=parent.parent.gainCommonInfoByCode("GZZC",0);
		    for(var i=0;i<gzzc.length;i++)
			{
				addOption(gzzc[i].bparentcodekey,gzzc[i].parentcodevalue,document.getElementById("oldpositiontitle"));
			}
			document.getElementById("oldpositiontitle").value=parent.document.getElementById("positiontitle").value;
		
			
			//$("#dispatchdate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'100' });
	     });
	     
	     function changeBySameLevel()
	     {
	     	
	     	if(document.getElementById("newcompno").value=="")
	     	{
	     		   	 $.ligerDialog.error("请确认调动新门店!");
	     		   	 return;
	     	}
	     	if(document.getElementById("newstaffno").value=="")
	     	{
	     		   	 $.ligerDialog.error("请确定调动新工号!");
	     		   	 return;
	     	}
	     	if(document.getElementById("dispatchdate").value=="" || document.getElementById("dispatchdate").value.length!=10)
	     	{
	     		   	 $.ligerDialog.error("请注意生效日期!");
	     		   	 return;
	     	}
	     	var params = "oldcompno="+document.getElementById("oldcompno").value;			
    		params =params+ "&oldstaffno="+document.getElementById("oldstaffno").value;	
    		params =params+ "&oldstaffinid="+document.getElementById("oldstaffinid").value;	
    		params =params+ "&olddepid="+document.getElementById("olddepid").value;	
    		params =params+ "&oldpostion="+document.getElementById("oldpostion").value;	
    		params =params+ "&oldyjtype="+document.getElementById("oldyjtype").value;	
    		params =params+ "&oldyjrate="+document.getElementById("oldyjrate").value;	
    		params =params+ "&oldyjamt="+document.getElementById("oldyjamt").value;
    		params =params+ "&oldSalary="+document.getElementById("oldSalary").value;
    		params =params+ "&newcompno="+document.getElementById("newcompno").value;
    		params =params+ "&newstaffno="+document.getElementById("newstaffno").value;	
    		params =params+ "&dispatchdate="+document.getElementById("dispatchdate").value;	
	     	var requestUrl ="bc012/changeBySameLevel.action";
   			var responseMethod="changeBySameLevelMessage";
   			parent.sendRequestForParams_p(requestUrl,responseMethod,params)
	     }
	   
	</script>
	
</head>
<body style="padding:6px; overflow:hidden;">
	<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
	<table style="font: 12px;line-height: 16px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
		<tr>
		<td>新门店</td>
		<td><input type="text" name="newcompno" id="newcompno"   style="width:100;" />
		 </td>
		<td>新工号: </td>
		<td ><input type="text" name="newstaffno" id="newstaffno" style="width:100;" />
		</td>
		</tr>
		<tr>
			<td>部门: </td>
			<td>
					<select id="olddepid" name="olddepid" style="width:100"   disabled="true">
					</select>
			</td>
			<td>基本工资:</td>
			<td><input type="text" name="oldSalary" id="oldSalary"  readonly="true" style="width:100;background:#EDF1F8;" /></td>
			
		</tr>
		<tr>
			<td>职位:</td>
			<td>
				<select id="oldpostion" name="oldpostion" style="width:100"  disabled="true" >
				</select>
			</td>
			<td>业绩方式:</td>
			<td><select name="oldyjtype" id="oldyjtype" style="width:100"  disabled="true"></select></td>

		</tr>
		<tr>
			<td>职称:</td>
			<td>
				<select id="oldpositiontitle" name="oldpositiontitle" style="width:100"  disabled="true" >
				</select>
			</td>
			<td>业绩系数:</td>
			<td><input type="text" name="oldyjrate" id="oldyjrate"  readonly="true" style="width:100;background:#EDF1F8;" /></td>

		</tr>
		<tr>
			<td>业务</td>
			<td>
				<select id="oldbusinessflag" name="oldbusinessflag" style="width:100;" disabled="true">
					<option value="0">非业务</option>
					<option value="1">业务</option>
				</select>
			</td>
			<td>业绩基数:</td>
			<td><input type="text" name="oldyjamt" id="oldyjamt"  readonly="true" style="width:100;background:#EDF1F8;" /></td>
		
		</tr>
		<tr>
		<td><font color="blue">生效日期:</font> </td>
		<td ><input type="text" name="dispatchdate" id="dispatchdate" style="width:100;" />
		<input type="hidden" name="oldcompno" id="oldcompno"/>
		<input type="hidden" name="oldstaffno" id="oldstaffno"/>
		<input type="hidden" name="oldstaffinid" id="oldstaffinid"/>
		</td><td colspan="2" align="center"><div id="changeBySameLevel"></div></td></tr>
	</table>	
	</div>

</body>
</html>
