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
		var showPostionInfodiv=null;
		$(function ()
   		{
   			 showPostionInfodiv=$("#showPostionInfodiv").ligerGrid({
                columns: [
                { display: '职位编号', name: 'bparentcodekey',   width: 70 , align: 'left'  },
                { display: '职位名称', name: 'parentcodevalue', width: 120, align: 'left'  }
                ],  pageSize:10, 
                data: loadCommonDataGridByInfotype("GZGW",0),         
                width: 250,
                height:400,
                enabledEdit: false,  checkbox: true,rownumbers: false,usePager: false,
                onCheckRow: f_onCheckRow,
                onCheckAllRow: f_onCheckAllRow
            });
            
   			$("#handBandPosition").ligerButton(
	         {
	             text: '确认添加', width: 160,
		         click: function ()
		         {
		             handBandPosition();
		         }
	     	});
	     	/*addOption("","",document.getElementById("bandPositionNo"));
           	var postions=parent.parent.gainCommonInfoByCode("GZGW",0);
            for(var i=0;i<postions.length;i++)
			{
				addOption(postions[i].bparentcodekey,postions[i].parentcodevalue,document.getElementById("bandPositionNo"));
			}	*/
	     });
	     
	    function loadCommonDataGridByInfotype(Infotype,showType)
		{
			try
			{
				if(checkNull(Infotype)=="" )
					return null;
				var strJson = "";//'{ "name": "cxh", "sex": "man" }';
				var ccount=0;
				if(parent.parent.CommonparentDataByGroup!=null && parent.parent.CommonparentDataByGroup.length>0)
				{	
					for(var i=0;i<parent.parent.CommonparentDataByGroup.length;i++)
					{
						if(parent.parent.CommonparentDataByGroup[i].binfotype==Infotype)
						{	
							if(showType!=0 )
							{
								if(parent.parent.CommonparentDataByGroup[i].useflag==showType )
								{
									ccount=ccount*1+1;
									if(ccount==1)
									{
										strJson=strJson+'[';
									}
									else
									{
										strJson=strJson+',';
									}
									strJson=strJson+'{ "bparentcodekey":"'+parent.parent.CommonparentDataByGroup[i].bparentcodekey+'", "parentcodevalue": "'+parent.parent.CommonparentDataByGroup[i].parentcodevalue+'"   }';
								}
							}
							else
							{
								ccount=ccount*1+1;
								if(ccount==1)
								{
									strJson=strJson+'[';
								}
								else
								{
									strJson=strJson+',';
								}
								strJson=strJson+'{ "bparentcodekey":"'+parent.parent.CommonparentDataByGroup[i].bparentcodekey+'", "parentcodevalue": "'+parent.parent.CommonparentDataByGroup[i].parentcodevalue+'"   }';
							
							}
						}
					}
					if(strJson!="")
					{
						strJson=strJson+']';
						return {Rows:JSON.parse(strJson),Total:2};
					}
					return null;
				}
			
			}catch(e){alert(e.message);}
		}
        var checkedBcodekey= [];
		function f_onCheckAllRow(checked)
        {
            for (var rowid in showPostionInfodiv.records)
            {
            	if(checked)
                    addCheckedBcodekey(showPostionInfodiv.records[rowid]['bparentcodekey']);
                else
                    removeCheckedBcodekey(showPostionInfodiv.records[rowid]['bparentcodekey']);
            }
        }
        
		function f_onCheckRow(checked, data)
        {
        	if (checked) 
            {
            	addCheckedBcodekey(data.bparentcodekey);
            }
            else 
            {
            	removeCheckedBcodekey(data.bparentcodekey);
            }
        }
        
        function findCheckedBcodekey(bcodekey)
        {
            for(var i =0;i<checkedBcodekey.length;i++)
            {
                if(checkedBcodekey[i] == bcodekey) return i;
            }
            return -1;
        }
        
        function addCheckedBcodekey(bcodekey)
        {
        	if(findCheckedBcodekey(bcodekey) == -1)
                checkedBcodekey.push(bcodekey);
        }
        function removeCheckedBcodekey(bcodekey)
        {
            var i = findCheckedBcodekey(bcodekey);
            if(i==-1) return;
            checkedBcodekey.splice(i,1);
        }
	     
	     function changeChooses(obj)
	     {
	     	/*clearOption("bandPositionNo");
	     	addOption("","",document.getElementById("bandPositionNo"));
           	var postions=parent.parent.gainCommonInfoByCodeByUse("GZGW",0,obj.value);
            for(var i=0;i<postions.length;i++)
			{
				addOption(postions[i].bparentcodekey,postions[i].parentcodevalue,document.getElementById("bandPositionNo"));
			}	*/
			showPostionInfodiv.options.data=$.extend(true, {},loadCommonDataGridByInfotype("GZGW",obj.value));
	        showPostionInfodiv.loadData(true);       
	     }
	     
	     function handBandPosition()
	     {
	     try
	     {
		     	
		     	var bandPositionNo="";
		     	for(var i =0;i<checkedBcodekey.length;i++)
            	{
                	if(checkedBcodekey[i]!="")
					{
						bandPositionNo=bandPositionNo+checkedBcodekey[i]+";";
					}
            	}
		     	var params =" strCurRoleId="+parent.curRoleIdKey;
		     	params=params+"&strCurPostion="+bandPositionNo;
		     	var requestUrl ="bc003/handBandPos.action";
	   			var responseMethod="handBandPosMessage";
		   		parent.addposition=bandPositionNo;
		   		parent.sendRequestForParams_p(requestUrl,responseMethod,params);
	   		}catch(e){alert(e.message);};
	     }
	     
	    
	   
	</script>
	
</head>
<body style="padding:6px; overflow:hidden;">
	<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
	<table style="font: 12px;line-height: 16px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
		<tr><td width="80">选择范围</td>
		<td>
			<select  name="bandPositionType" id="bandPositionType"   style="width:120;"  onchange="changeChooses(this)">
				<option value="0">所有</option>		
				<option value="1">门店</option>		
				<option value="2">总部</option>						
			</select>
		</td></tr>
		
		<tr><td colspan="2" ><div id="showPostionInfodiv"></div></td></tr>
		<tr><td colspan="2" align=center>&nbsp;</td></tr>
		<tr><td colspan="2" align=center><div id="handBandPosition"></div></td></tr>
	</table>	
	</div>

</body>
</html>
