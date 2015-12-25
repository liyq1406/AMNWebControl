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
		var showCompInfodiv=null;
		$(function ()
   		{
   			 showCompInfodiv=$("#showCompInfodiv").ligerGrid({
                columns: [
                { display: '门店编号', name: 'compno',   width: 70 , align: 'left'  },
                { display: '门店名称', name: 'compname', width: 120, align: 'left'  }
                ],  pageSize:10, 
                data: loadCompnayDataGridByInfotype(),         
                width: 250,
                height:400,
                enabledEdit: false,  checkbox: true,rownumbers: false,usePager: false,
                onCheckRow: f_onCheckRow,
                onCheckAllRow: f_onCheckAllRow
            });
            
   			$("#handBandComp").ligerButton(
	         {
	             text: '确认添加', width: 160,
		         click: function ()
		         {
		             handBandComp();
		         }
	     	});
	     	
	     });
	     
	    function loadCompnayDataGridByInfotype()
		{
			try
			{
				var strJson = "";//'{ "name": "cxh", "sex": "man" }';
				var ccount=0;
				if(parent.parent.lsCompanyinfos!=null && parent.parent.lsCompanyinfos.length>0)
				{	
					for(var i=0;i<parent.parent.lsCompanyinfos.length;i++)
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
						strJson=strJson+'{ "compno":"'+parent.parent.lsCompanyinfos[i].compno+'", "compname": "'+parent.parent.lsCompanyinfos[i].compname+'"   }';
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
            for (var rowid in showCompInfodiv.records)
            {
            	if(checked)
                    addCheckedBcodekey(showCompInfodiv.records[rowid]['compno']);
                else
                    removeCheckedBcodekey(showCompInfodiv.records[rowid]['compno']);
            }
        }
        
		function f_onCheckRow(checked, data)
        {
        	if (checked) 
            {
            	addCheckedBcodekey(data.compno);
            }
            else 
            {
            	removeCheckedBcodekey(data.compno);
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
	     
	     
	     
	     function handBandComp()
	     {
	     try
	     {
		     	
		     	var bandCompNo="";
		     	for(var i =0;i<checkedBcodekey.length;i++)
            	{
                	if(checkedBcodekey[i]!="")
					{
						bandCompNo=bandCompNo+checkedBcodekey[i]+";";
					}
            	}
		     	parent.copyShopRateSetToOtherCompId(bandCompNo);
	   		}catch(e){alert(e.message);};
	     }
	     
	    
	   
	</script>
	
</head>
<body style="padding:6px; overflow:hidden;">
	<div  style="width:100%; float:left; clear:both; border:0px solid #ccc; overflow:auto;font-size:12px;  ">
	<table style="font: 12px;line-height: 16px; text-align: inherit" cellspacing="0" cellpadding="0" width="100%">
		<tr><td colspan="2" ><div id="showCompInfodiv"></div></td></tr>
		<tr><td colspan="2" align=center>&nbsp;</td></tr>
		<tr><td colspan="2" align=center><div id="handBandComp"></div></td></tr>
	</table>	
	</div>

</body>
</html>
