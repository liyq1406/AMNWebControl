<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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

	$(function ()
   	{
   	 try
   	 {
		
   		var commoninfodivthirth=$("#commoninfodivthirth").ligerGrid({
	                columns: [
	                { display: '异动类型', 	name: 'changetype', width: 60 ,
	                	editor: { type: 'select', data: parent.staffChangeData, valueField: 'choose',selectBoxHeight:'220'},
		            	render: function (item)
		              	{
		              		var lsZw=parent.parent.gainCommonInfoByCode("YDLX",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.changetype)
								{	
									return lsZw[i].parentcodevalue;								
							    }
							}
		                    return '';
		                } 
	                },
	                { display: '原店号', 	name: 'oldcompid', 	width: 50 },
	                { display: '原工号', 	name: 'oldempid',  	width: 50 },
	                { display: '原部门', 		name: 'olddepid',  	width: 60 ,
	                	editor: { type: 'select', data: parent.staffDepData, valueField: 'choose',selectBoxHeight:'220'},
		            	render: function (item)
		              	{
		              		var lsZw=parent.parent.gainCommonInfoByCode("BMZL",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.olddepid)
								{	
									return lsZw[i].parentcodevalue;								
							    }
							}
		                    return '';
		                } 
	                },
	                { display: '原职位', 		name: 'oldpostion', width: 70  ,
		                editor: { type: 'select', data: parent.staffPostionData, valueField: 'choose',selectBoxHeight:'220'},
			            	render: function (item)
			              	{
			              		var lsZw=parent.parent.gainCommonInfoByCode("GZGW",0);
			              		for(var i=0;i<lsZw.length;i++)
								{
									if(lsZw[i].bparentcodekey==item.oldpostion)
									{	
										return lsZw[i].parentcodevalue;								
								    }
								}
			                    return '';
			                } 
		            },
	                { display: '原工资', 		name: 'oldsalary',  width: 50 },
	                { display: '原方式', 		name: 'oldyjtype',  width: 80 ,
	                	editor: { type: 'select', data: parent.staffYejiData, valueField: 'choose',selectBoxHeight:'220'},
		            	render: function (item)
		              	{
		              		var lsZw=parent.parent.gainCommonInfoByCode("YJFS",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.oldyjtype)
								{	
									return lsZw[i].parentcodevalue;								
							    }
							}
		                    return '';
		             		} 
		             },
	                { display: '原系数',		name: 'oldyjrate',  width: 50 },
	                { display: '原基数',		name: 'oldyjamt',  width: 50 },
	                { display: '新店号', 	name: 'newcompid',  width: 50 },
	                { display: '新工号', 	name: 'newempid',  	width: 50 },
	                { display: '新部门',		name: 'newdepid',  	width: 60 ,
	                	editor: { type: 'select', data: parent.staffDepData, valueField: 'choose',selectBoxHeight:'220'},
		            	render: function (item)
		              	{
		              		var lsZw=parent.parent.gainCommonInfoByCode("BMZL",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.newdepid)
								{	
									return lsZw[i].parentcodevalue;								
							    }
							}
		                    return '';
		                } 
	                },
	                { display: '新职位', 		name: 'newpostion', width: 70  ,
		                editor: { type: 'select', data: parent.staffPostionData, valueField: 'choose',selectBoxHeight:'220'},
			            	render: function (item)
			              	{
			              		var lsZw=parent.parent.gainCommonInfoByCode("GZGW",0);
			              		for(var i=0;i<lsZw.length;i++)
								{
									if(lsZw[i].bparentcodekey==item.newpostion)
									{	
										return lsZw[i].parentcodevalue;								
								    }
								}
			                    return '';
			                } 
			         },
	                { display: '新工资',		name: 'newsalary',  width: 60 },
	                { display: '新方式',		name: 'newyjtype',  width: 80 ,
	                	editor: { type: 'select', data: parent.staffYejiData, valueField: 'choose',selectBoxHeight:'220'},
		            	render: function (item)
		              	{
		              		var lsZw=parent.parent.gainCommonInfoByCode("YJFS",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.newyjtype)
								{	
									return lsZw[i].parentcodevalue;								
							    }
							}
		                    return '';
		             		} 
		             },
	                { display: '新系数', 		name: 'newyjrate',  width: 50 },
	                { display: '新基数', 		name: 'newyjramt',  width: 50 },
	                { display: '生效日期', 	name: 'effectivedate',  width: 70 },
	                { display: '单据编号', 	name: 'optionbill', width: 100 },
	                { display: '备注', 		name: 'changemark', width: 200 }
	                ],  pageSize:10, 
	                data: {Rows: parent.lsStaffhistory,Total: parent.lsStaffhistory.length},      
	                width: '100%',
	                height:'100%',
	                enabledEdit: false,  
	                rownumbers:false,usePager:false
           			});
            		$("#pageloading").hide();  
            		}catch(e){alert(e.message);} 	
    	});
	</script>
		<style type="text/css">
           body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
        .l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}
        .l-verify-tip{ left:230px; top:120px;}
        .scr_con {position:relative;width:298px; height:98%;border:solid 1px #ddd;margin:0px auto;font-size:12px;}
        #dv_scroll{position:absolute;height:98%;overflow:hidden;width:298px;}
		#dv_scroll .Scroller-Container{width:100%;}
		#dv_scroll_bar {position:absolute;right:0;bottom:30px;width:14px;height:150px;border-left:1px solid #B5B5B5;}
		#dv_scroll_bar .Scrollbar-Track{position:absolute;left:0;top:5px;width:14px;height:150px;}
		#dv_scroll_bar .Scrollbar-Handle{position:absolute;left:-7px;bottom:10;width:13px;height:29px;overflow:hidden;background:url('<%=ContextPath%>/common/ligerui/images/srcoll.gif') no-repeat;cursor:pointer;}
		#dv_scroll_text {position:absolute;}
    </style>
</head>
<body style="padding:6px; overflow:hidden;">
	<div class="l-loading" style="display:block" id="pageloading"></div> 
   	<div id="commoninfodivthirth" style="margin:0; padding:0"></div>
</body>
</html>
