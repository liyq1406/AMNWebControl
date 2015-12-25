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
		var servicetypeChangeData=JSON.parse(parent.parent.loadCommonControlDate_select("FWLB",0));
   		var commoninfodivthirth=$("#commoninfodivthirth").ligerGrid({
	    columns: [
	            { display: '项目/产品' ,	name: 'csitemname', width:270,align: 'center'},
                { display: '支付方式', 	name: 'cspaymode', 			width:95,
	             	editor: { type: 'select', data: null, valueField: 'choose',selectBoxWidth:105 },
		            	render: function (item)
		              	{
		              		var lsZw=parent.parent.gainCommonInfoByCode("ZFFS",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.cspaymode)
								{	
									return "<span style='color:blue'>"+lsZw[i].parentcodevalue+"</span>";								
							    }
							}
		                    return '';
		                } 
		        },
		        { display: '单价', 		name: 'csdisprice', 		width:50,align: 'right'},
		       
	            { display: '数量', 		name: 'csitemcount', 		width:50,align: 'right'  },
	            { display: '金额', 		name: 'csitemamt', 			width:60,align: 'right'  },
	         
	            { display: '大工', 		name: 'csfirstsaler', 		width:100 },
	            { display: '类型', 		name: 'csfirsttype', 		width:60 ,
	             	editor: { type: 'select', data: servicetypeChangeData,autocomplete: true, valueField: 'choose',selectBoxHeight:'120',onChanged:clientChange,alwayShowInDown:true},
		            	render: function (item)
		              	{
		              		var lsZw=parent.parent.gainCommonInfoByCode("FWLB",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.csfirsttype)
								{	
									return "<span style='color:blue'>"+lsZw[i].parentcodevalue+"</span>";								
							    }
							}
		                    return '';
		                } 
	            },
	            { display: '分享', 		name: 'csfirstshare', 	width:50,align: 'left' },
	            { display: '中工', 		name: 'cssecondsaler', 		width:100 },
	            { display: '类型', 		name: 'cssecondtype', 		width:60 ,
	             	editor: { type: 'select', data: servicetypeChangeData, valueField: 'choose',selectBoxHeight:'120'},
		            	render: function (item)
		              	{
		              		var lsZw=parent.parent.gainCommonInfoByCode("FWLB",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.cssecondtype)
								{	
									return "<span style='color:blue'>"+lsZw[i].parentcodevalue+"</span>";								
							    }
							}
		                    return '';
		                } 
	            },
	            { display: '分享', 		name: 'cssecondshare', 	width:50,align: 'left' },
	            { display: '小工', 		name: 'csthirdsaler', 		width:100  },
	            { display: '类型', 		name: 'csthirdtype', 		width:60 ,
	             	editor: { type: 'select', data: servicetypeChangeData, valueField: 'choose',selectBoxHeight:'120'},
		            	render: function (item)
		              	{
		              		var lsZw=parent.parent.gainCommonInfoByCode("FWLB",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.csthirdtype)
								{	
									return "<span style='color:blue'>"+lsZw[i].parentcodevalue+"</span>";								
							    }
							}
		                    return '';
		                } 
	            },
	           	{ display: '分享', 	name: 'csthirdshare', 		width:50,align: 'left' }
	            
	           ],  pageSize:10, 
	                data: {Rows: parent.lsCostHistoryInfo,Total: parent.lsCostHistoryInfo.length},      
	                width: '100%',
	                height:'100%',
	                enabledEdit: false,  
	                rownumbers:false,usePager:false
           			});
            		$("#pageloading").hide();  
            		}catch(e){alert(e.message);} 	
    	});
    	
    	function clientChange(obj)
    	{
    		alert(obj.value);
    	}
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
