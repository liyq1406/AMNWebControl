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
	<script src="<%=ContextPath%>/common/ligerui/jquery/scrollbar.js" type="text/javascript"></script>
	<script language="JavaScript">
		 var dispatchStaffdiv=null;
		 var monthhtml="<option value=\"01\">01</option><option value=\"02\">02</option><option value=\"03\">03</option><option value=\"04\">04</option><option value=\"05\">05</option>";
		 monthhtml=monthhtml+"<option value=\"06\">06</option><option value=\"07\">07</option><option value=\"08\">08</option><option value=\"09\">09</option><option value=\"10\">10</option><option value=\"11\">11</option><option value=\"12\">12</option>";
		$(function ()
   		{
   			$("#dispatchStaff").ligerButton(
	         {
	             text: '确认缺勤', width: 120,
		         click: function ()
		         {
		             absenceStaff();
		         }
	     	});
	     	
	     	dispatchStaffdiv=$("#dispatchStaffdiv").ligerGrid({
                columns: [
                { display: '缺勤日期', name: 'absencedate',  width: 175 }
                ],  pageSize:10, 
                data: null,        
                width: '100%',
                height:'95%',
                enabledEdit: false,checkbox: false,rownumbers:false,usePager:false
            });
			
			$("#batchhandtoptoolbar").ligerToolBar({ items: [
				{text: '年:<select id="kqyear" name="kqyear" style="width:60"><option value="2014">2014</option></select>'},
      		    {text: '月:<select id="kqmonth" name="kqmonth" style="width:40">'+monthhtml+'</select>'},
      		    { text: '<font color="blue">登记</font>', click: uploadStaffAbsence  }
	         ]
            });
            document.getElementById("oldcompno").value=parent.getCurOrgFromSearchBar();
			document.getElementById("oldstaffno").value=parent.curRecord.staffno;
			document.getElementById("oldstaffinid").value=parent.curRecord.staffinid;
			document.getElementById("oldstaffname").value=parent.curRecord.staffname;
			initDispatchStaffdiv();
	     });
	     
	     
	     function absenceStaff()
	     {
	     	if(document.getElementById("absencedate").value=="")
	     	{
	     		$.ligerDialog.error("请确认缺勤日期!");
	     		return ;
	     	}
	     	var params = "strCurCompId="+document.getElementById("oldcompno").value;			
    	    params =params+ "&strStaffNo="+document.getElementById("oldstaffno").value;	
    	    params =params+ "&strStaffInNo="+document.getElementById("oldstaffinid").value;	
    		params =params+ "&strAbsencedate="+document.getElementById("absencedate").value;
	     	var requestUrl ="bc012/postAbsenceStaff.action";
   			var responseMethod="absenceStaffMessage";
   			parent.sendRequestForParams_p(requestUrl,responseMethod,params)
	     }
	     
	     function initDispatchStaffdiv()
	     {
	     	if(parent.lsStaffabsenceinfo!=null && parent.lsStaffabsenceinfo.length>0)
	     	{
	     		dispatchStaffdiv.options.data=$.extend(true, {},{Rows: parent.lsStaffabsenceinfo,Total: parent.lsStaffabsenceinfo.length});
            	dispatchStaffdiv.loadData(true);
	     	}
	     	else
	     	{
	     		var row = dispatchStaffdiv.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
				dispatchStaffdiv.addRow({ 
				                absencedate: ""
				            }, row, false);
	     	}
	     }
	   
	   	function loadMonthCheck(obj)
	   	{
	   		$("input[name='absencemonth']").each(function()
	   		{
	   			if(obj.checked!="checked"){$(this).click();}
	   		});

	   	}
	   	
	   	function uploadStaffAbsence()
	   	{
	   		var toMonth="";
	   		var lsabsencemonth=document.getElementsByName("absencemonth");
	   		for( var i=0;i<lsabsencemonth.length;i++)
	   		{
	   			var checkboxid="month"+(i*1+1);
	   			if(document.getElementById(checkboxid).checked==true)
	   			{
	   				if(i*1+1<10)
	   				{
	   					toMonth=toMonth+document.getElementById("kqyear").value+document.getElementById("kqmonth").value+"0"+(i*1+1)+",";
	   				}
	   				else
	   				{
	   					toMonth=toMonth+document.getElementById("kqyear").value+document.getElementById("kqmonth").value+(i*1+1)+",";
	   				}
	   			}
	   		}
	   		if(toMonth=="")
	     	{
	     		$.ligerDialog.error("请确认缺勤日期!");
	     		return ;
	     	}
	     	var params = "strCurCompId="+document.getElementById("oldcompno").value;			
    	    params =params+ "&strStaffNo="+document.getElementById("oldstaffno").value;	
    	    params =params+ "&strStaffInNo="+document.getElementById("oldstaffinid").value;	
    		params =params+ "&strtoMonth="+toMonth;
	     	var requestUrl ="pc008/postAllAbsenceStaff.action";
   			var responseMethod="absenceStaffMessage";
   			parent.sendRequestForParams_p(requestUrl,responseMethod,params)
	   	}
	</script>
	
</head>
<body style="padding:6px; overflow:hidden;">
	<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
	<table style="font: 12px;line-height: 16px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
		<tr><td>门店号:</td><td><input type="text" name="oldcompno" id="oldcompno"  readonly="true" style="width:160;background:#EDF1F8;" /></td>
		<td rowspan="6" valign="top" width="180"><div id="dispatchStaffdiv"></div></td>
		<td rowspan="6" valign="top" width="280">
			<div id="batchhandtoptoolbar" style="height:25"></div>
			<div  style="width:98%; height:98%; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
			<table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:15px;" >
			<tr>
				<td colspan="2"><font color="red">全选</font></td>
				<td>01</td>
				<td>02</td>
				<td>03</td>
				<td>04</td>
				<td>05</td>
				<td>06</td>
				<td>07</td>
				<td>08</td>
				<td>09</td>
				
			</tr>
			<tr>
				<td  colspan="2"><input type="checkbox" id="allmonth" name="allmonth" onclick="loadMonthCheck(this)"/></td>
				<td><input type="checkbox" id="month1" name="absencemonth"/></td>
				<td><input type="checkbox" id="month2" name="absencemonth"/></td>
				<td><input type="checkbox" id="month3" name="absencemonth"/></td>
				<td><input type="checkbox" id="month4" name="absencemonth"/></td>
				<td><input type="checkbox" id="month5" name="absencemonth"/></td>
				<td><input type="checkbox" id="month6" name="absencemonth"/></td>
				<td><input type="checkbox" id="month7" name="absencemonth"/></td>
				<td><input type="checkbox" id="month8" name="absencemonth"/></td>
				<td><input type="checkbox" id="month9" name="absencemonth"/></td>
				
			</tr>
			<tr>
				<td>10</td>
				<td>11</td>
				<td>12</td>
				<td>13</td>
				<td>14</td>
				<td>15</td>
				<td>16</td>
				<td>17</td>
				<td>18</td>
				<td>19</td>
				<td>20</td>
			</tr>
			<tr>
				<td><input type="checkbox" id="month10" name="absencemonth"/></td>
				<td><input type="checkbox" id="month11" name="absencemonth"/></td>
				<td><input type="checkbox" id="month12" name="absencemonth"/></td>
				<td><input type="checkbox" id="month13" name="absencemonth"/></td>
				<td><input type="checkbox" id="month14" name="absencemonth"/></td>
				<td><input type="checkbox" id="month15" name="absencemonth"/></td>
				<td><input type="checkbox" id="month16" name="absencemonth"/></td>
				<td><input type="checkbox" id="month17" name="absencemonth"/></td>
				<td><input type="checkbox" id="month18" name="absencemonth"/></td>
				<td><input type="checkbox" id="month19" name="absencemonth"/></td>
				<td><input type="checkbox" id="month20" name="absencemonth"/></td>
			</tr>
			<tr>
				<td>21</td>
				<td>22</td>
				<td>23</td>
				<td>24</td>
				<td>25</td>
				<td>26</td>
				<td>27</td>
				<td>28</td>
				<td>29</td>
				<td>30</td>
				<td>31</td>
			</tr>
			<tr>
				<td><input type="checkbox" id="month21" name="absencemonth"/></td>
				<td><input type="checkbox" id="month22" name="absencemonth"/></td>
				<td><input type="checkbox" id="month23" name="absencemonth"/></td>
				<td><input type="checkbox" id="month24" name="absencemonth"/></td>
				<td><input type="checkbox" id="month25" name="absencemonth"/></td>
				<td><input type="checkbox" id="month26" name="absencemonth"/></td>
				<td><input type="checkbox" id="month27" name="absencemonth"/></td>
				<td><input type="checkbox" id="month28" name="absencemonth"/></td>
				<td><input type="checkbox" id="month29" name="absencemonth"/></td>
				<td><input type="checkbox" id="month30" name="absencemonth"/></td>
				<td><input type="checkbox" id="month31" name="absencemonth"/></td>
			</tr>
			
			</table>
			</div>
		</td>
		</tr>
		<tr><td>员工号: </td><td><input type="text" name="oldstaffno" id="oldstaffno"  readonly="true" style="width:80;background:#EDF1F8;" /><input type="text" name="oldstaffname" id="oldstaffname"  readonly="true" style="width:80;background:#EDF1F8;" /></td></tr>
		<tr><td>内部编号:</td><td><input type="text" name="oldstaffinid" id="oldstaffinid"   readonly="true" style="width:120;background:#EDF1F8;"/></td></tr>
		<tr><td>缺勤日期:</td><td><input type="text" name="absencedate" id="absencedate"  style="width:120;" /></td></tr>
		<tr><td colspan="2" align=center><font color="red">[格式:20140201 或 2014-02-01]</font>
	
		</td></tr>
		<tr><td colspan="2" align=center><div id="dispatchStaff"></div></td></tr>
	</table>	
	</div>

</body>
</html>
