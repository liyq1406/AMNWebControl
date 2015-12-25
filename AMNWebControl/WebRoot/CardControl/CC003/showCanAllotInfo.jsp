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
	var commoninfodivthirth=null;
	var Lastlength=parent.document.getElementById("cardIdLength").value;
	var numberOfCardFilter=parent.document.getElementById("numberOfCardFilter").value;

	$(function ()
   	{
   	 try
   	 {
		document.getElementById("allotTitleCardTypeName").innerHTML=parent.curSelectCardTypename;
		document.getElementById("allotTitleCount").innerHTML=parent.curSelectCardTypeCount;
   		commoninfodivthirth=$("#commoninfodivthirth").ligerGrid({
	                columns: [
	                { display: '卡类型', 			name: 'cardclass', 		width: 50 },
	                { display: '起始编号', 		name: 'cardfrom',  		width: 90 },
	                { display: '结束编号',		name: 'cardto',  		width: 90 },
	                { display: '库存量', 			name: 'ccount',  		width: 50 },
	                { display: '配发起始段', 		name: 'allotcardfrom',  	width: 90,editor: { type: 'text' }  },	   
	                { display: '配发结束段', 		name: 'allotcardto',  	width: 90, editor: { type: 'text' } }	,
	                { display: '配发数量', 		name: 'allotcardnum',  	width: 50 }	                 
	                ],  pageSize:10, 
	                data: {Rows: parent.lsStoreCanAllotCards,Total: parent.lsStoreCanAllotCards.length},      
	                width: '100%',
	                height:'100%',
	                enabledEdit: true,  checkbox:true,
	                rownumbers:false,usePager:false,
	                onCheckRow: f_onCheckRow_thirth,
                	toolbar: { items: [
                 		{ text: '计算当前配发卡数', click: itemclick_companyCount, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' },
                 		{ text: '配发卡', click: itemclick_companyCount, img: contextURL+'/common/ligerui/ligerUI/skins/icons/down.gif' }
                 	]}
           			});
            		$("#pageloading").hide();  
            		}catch(e){alert(e.message);} 	
    	});
    	
    	function itemclick_companyCount(item)
    	{
    		if(item.text=="计算当前配发卡数")
    		{
    			companyCount();
    		}
    		else
    		{
    			allotCardInfo();
    		}
    	}
    	
    	function companyCount()
    	{
    		try
    		{
	    		var cardfrom="";
	    		var cardend="";
	    		var factcardfrom="";
	    		var factcardend="";
	    		for (var rowid in commoninfodivthirth.records)
				{
					if(findCheckedBfunctionno_thirth(commoninfodivthirth.records[rowid]['rid'])!=-1)
					{	
					 	
						cardfrom=checkNull(commoninfodivthirth.records[rowid]['allotcardfrom']);
						cardend=checkNull(commoninfodivthirth.records[rowid]['allotcardto']);
					    factcardfrom=checkNull(commoninfodivthirth.records[rowid]['cardfrom']);
					    factcardend=checkNull(commoninfodivthirth.records[rowid]['cardto']);
					  
					    if(factcardfrom!=""&&factcardend!="")
				        {
					              /*****输入的起始卡头或尾*******/
					              var cardId_f=cardfrom.substring(cardfrom.length-Lastlength,cardfrom.length)*1;
					              var cardId_f_head=cardfrom.substring(0,cardfrom.length-Lastlength*1);
					              /*****输入的结束卡头或尾*******/
					              var cardId_e=checkNull(cardend.substring(cardend.length-Lastlength,cardend.length)*1);
					              var cardId_e_head=checkNull(cardend.substring(0,cardend.length-Lastlength*1));
					              /*****实际的卡头或卡尾************/
					              var cardIdFact_f=checkNull(factcardfrom.substring(factcardfrom.length-Lastlength,factcardfrom.length)*1);
					              var cardIdFact_e=checkNull(factcardend.substring(factcardend.length-Lastlength,factcardend.length)*1);
					              var cardId_head=checkNull(factcardfrom.substring(0,factcardfrom.length-Lastlength*1));
					              /******判断输入的卡是否符合条件********Start************/
					            	
					              if(factcardfrom.length*1!=cardfrom.length*1)
					              {
					                	alert("起始编号"+factcardfrom+"输入有误!!");
					                	commoninfodivthirth.updateRow(rowid,{allotcardfrom:'',allotcardnum: 0});
					                	return ;
					              }
					              
					              if(factcardend.length*1!=cardfrom.length*1)
					              {
					                	alert("结束编号"+factcardend+"输入有误!!");
					                 	commoninfodivthirth.updateRow(rowid,{allotcardto:'',allotcardnum: 0});
					                	return ;
					              }
					             
					              if(cardId_f_head!=cardId_head||cardId_f<cardIdFact_f||cardId_f>cardIdFact_e)
					              {
					                	alert("起始编号"+factcardfrom+"输入有误!!");
					                	commoninfodivthirth.updateRow(rowid,{allotcardfrom:'',allotcardnum: 0});
					                	return ;
					              }
					         
					              if(cardId_e_head!=cardId_head||cardId_e<cardIdFact_f||cardId_e>cardIdFact_e)
					              {
					                	alert("结束编号"+factcardend+"输入有误!!");
					                 	commoninfodivthirth.updateRow(rowid,{allotcardto:'',allotcardnum: 0});
					                	return ;
					              }
					              /******判断输入的卡是否符合条件********End************/
					              /*********计算输入卡号范围的值************/
					              var totalcount=cardId_e-cardId_f+1;
					              if(numberOfCardFilter=="")
					               {
						                 if(totalcount<0)
						                 {
						                  	 commoninfodivthirth.updateRow(rowid,{allotcardnum: 0});
						                 }
						                 else
						                 {
						                 	 commoninfodivthirth.updateRow(rowid,{allotcardnum: totalcount*1});
						                 }
					              }
					              else
					              {
					                	var count = 0;
					       	        	var fromTrgNum = (cardId_f+"").substring((cardId_f+"").length-1,(cardId_f+"").length)*1;
					       	       	 	var endTrgNum = (cardId_e+"").substring((cardId_e+"").length-1,(cardId_e+"").length)*1;
					       	       	 	if(fromTrgNum<=Number(numberOfCardFilter))
					       	 	      		count++;
					       	        	if(endTrgNum>=Number(numberOfCardFilter))
					       	 	      		count++;
					                  	count = count + ((cardId_e*1-endTrgNum)-(cardId_f*1-fromTrgNum))/10 -1; 
					                	totalout4Count=totalcount-count;
					               	 	if(totalout4Count<0)
					                 	{
					                   		 commoninfodivthirth.updateRow(rowid,{allotcardnum:0});
					                 	}
					                	else
					                 	{
					                  		 commoninfodivthirth.updateRow(rowid,{allotcardnum:totalout4Count});
					                 	}
					              }
          
				       	}
				        else
				        {
				        	  commoninfodivthirth.updateRow(rowid,{allotcardnum:0});
				        }
					}            		 
				}
    		}catch(e){alert(e.message);} 
    	}
    	
    	function allotCardInfo()
    	{
    		companyCount();
    		var cardfrom="";
    		var cardend="";
    		var allotcount=0;
    		var rowDetial= parent.commoninfodivdetial.getSelectedRow();
    		for (var rowid in commoninfodivthirth.records)
			{
				if(findCheckedBfunctionno_thirth(commoninfodivthirth.records[rowid]['rid'])!=-1)
				{	
					cardfrom=checkNull(commoninfodivthirth.records[rowid]['allotcardfrom']);
					cardend=checkNull(commoninfodivthirth.records[rowid]['allotcardto']);
					allotcount=checkNull(commoninfodivthirth.records[rowid]['allotcardnum'])*1;
					if(allotcount*1>0)
					{
						
				   		parent.commoninfodivdetial.addRow({ 
				                cardtypeid: parent.curSelectCardType,
				                cardtypeName: parent.curSelectCardTypename,
				                cardnofrom: cardfrom,
				                cardnoto: cardend,
				                ccount:parent.curSelectCardTypeCount,
				                allotcount:allotcount*1
				            }, rowDetial, false);
					}
				}
			}
			parent.commoninfodivdetial.deleteRow(parent.curSelectRowIndex);
			parent.showAllot.hide();
    	}
    	//获取配发多选的模块
	   	function f_onCheckRow_thirth(checked, data)
        {
        	if (checked) 
            	addCheckedBfunctionno_thirth(data.rid);
            else 
            	removeCheckedBfunctionno_thirth(data.rid);
        }
	   	var checkedBfunctionno_thirth= [];
	   	function findCheckedBfunctionno_thirth(rid)
        {
            for(var i =0;i<checkedBfunctionno_thirth.length;i++)
            {
                if(checkedBfunctionno_thirth[i] == rid) return i;
            }
            return -1;
        }
        
        function addCheckedBfunctionno_thirth(rid)
        {
            if(findCheckedBfunctionno_thirth(rid) == -1)
                checkedBfunctionno_thirth.push(rid);
        }
        function removeCheckedBfunctionno_thirth(rid)
        {
            var i = findCheckedBfunctionno_thirth(rid);
            if(i==-1) return;
            checkedBfunctionno_thirth.splice(i,1);
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
	<div  style="width:100%; height:20; float:left; clear:both; border:1px solid #ccc; overflow:auto;font-size:12px;  ">
	<font color="blue"><label id="allotTitleCardTypeName"></label>&nbsp;&nbsp;申请数:&nbsp;&nbsp;<label id="allotTitleCount"></label></font>
	</div> 
   	<div id="commoninfodivthirth" style="margin:0; padding:0"></div>
</body>
	<script language="JavaScript">
  	 	var contextURL="<%=request.getContextPath()%>";
	</script>
</html>
