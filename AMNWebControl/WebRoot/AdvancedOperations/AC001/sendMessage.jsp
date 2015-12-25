<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>添加默认信息</title>
    <link href="<%=ContextPath%>/AdvancedOperations/AC002/demo.css" rel="stylesheet" type="text/css" />
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script> 
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
 	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>

</head>
<body style="padding:6px; overflow:hidden;" style="font-size:12px;line-height:35px;"><center>
	     <table border="0" valign="top" width="90%" height="100%" style="font-size:12px">
							  <tr>
							  		 <td colspan="3">请输入大写'OK'确认发送&nbsp;&nbsp;<input type="text" id="okname" onkeyup="validateWord(this)"  width="160" /></td>
							  </tr>
							  <tr>
	                           		 <td  align="center" id="sedmsgs"></td>
									 <td  align="center" id="sedmsg" ><div id="sedmessage" ></div></td>
	                          		 <td valign="middle"  ><div id="resert"></div></td>
	                          </tr>
							  <tr>
	                          		<td valign="top" colspan="3">
	                          		<textarea name="show"  style="width:100%;height:80px" id="show"  readonly="true"></textarea> </td>	
	                          </tr>
	                         
									</table>
		</center> 
</body>
</html>
<script>
            document.getElementById("show").value=parent.document.getElementById("smgText").value;
	  	 	var contextURL="<%=request.getContextPath()%>";
	  	    var  selectdetail;     	 	//显示查询结果
            var  defaultphones;			//显示默认发送号码
    	 	try{
            var sedmessagebt=  $("#sedmessage").ligerButton({text: '立即发送', width: 120, click: function () {sedBtn(); }});
            var   resertbt=$("#resert").ligerButton({text: '取消发送', width: 120, click: function () {  parent.$(".l-dialog,.l-window-mask").remove();}});
	 		sedmessagebt.setDisabled();
	 		//resertbt.setDisabled();
	 		 }catch(e){alert(e.message);}
	 	//验证键盘事件
	 	function validateWord(obj)
	 	{
	 		if(obj.value=="OK")
	 		{
	 			sedmessagebt.setEnabled();
	 		}
	 		else
	 		{
	 			sedmessagebt.setDisabled();
	 		}
	 	}
        //发送短信息
	    function sedBtn(){
	    	   try{
					var jsonParam=""
					var jsonParams=""
					var curjosnparam=""
					var needReplaceStr=""
					var data=null;
					if(parent.selectdetail.options.pageCount==1)
					{
						data =parent.selectdetail.getData();
						jsonParam=JSON.stringify(data);
					}
					else
					{
						data =parent.searchDate;
						jsonParam=JSON.stringify(data);
					}
					var datas=parent.defaultphones.getData();
					jsonParams=JSON.stringify(datas);
					if(jsonParams.indexOf("_id")>-1)
					{
					    needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
					    jsonParams=jsonParams.replace(needReplaceStr," ");
					}	
				    var requestUrl ="ac001/sendMessage.action";
					var params="strJsonParam=["+jsonParams+"]";
					params="strJsonParam=["+jsonParam+"]";
					params=params+"&memeberPhone="+jsonParam;
					params=params+"&phone="+ jsonParams;
					params=params+"&msgText="+parent.document.getElementById("smgText").value;
					var responseMethod="sendMessageMessage";	
					if(parent.document.getElementById("smgText").value==""){
					 var s=$.ligerDialog.warn("发送失败， 手机号码或者内容为空！");
					     //	parent.$.ligerDialog.close(); 
					     return;
					}else{
						parent.showDialogmanager = $.ligerDialog.waitting('正在发送中,请稍候...');
				    	parent.sendRequestForParams_p(requestUrl,responseMethod,params );
					}
				}
				catch(e)
				{
					alert(e.message);
				}
	       }
	       function onnames(){
		       if(checkNull(document.getElementById("okname").value)=='OK'){
		        var ui = document.getElementById("show");
		        displayshowUI();
		        displayHideUI();
		        }else{
		        displayHideUIs();
		        displayshowUIs();
				okname.picname.value.focus();
				parent.$.ligerDialog.close(); 
				return false;
		       }
	       }
	       //显示
	       function displayshowUI()
			{
			     var ui = document.getElementById("sedmsg");
			     ui.style.display="block";
			}
			 function displayshowUIs()
			{
			     var ui = document.getElementById("sedmsg");
			     ui.style.display="none";
			}
			 function displayHideUI()
			{
			     var ui = document.getElementById("sedmsgs");
			     ui.style.display="none";
			}
			 function displayHideUIs()
			{
			     var ui = document.getElementById("sedmsgs");
			     ui.style.display="block";
			}
	     
    	function sendMessageMessage(request){
	    	  try
				{
		        	var responsetext = eval("(" + request.responseText + ")");
		        	var strMessage=responsetext.strMessage;
		        	if(checkNull(strMessage)=="" )
		        	{	
		        	   $.ligerDialog.success("发送成功!");
		        	}
		        	else
		        	{
		        		 $.ligerDialog.warn("发送失败，请检查手机号码是否正确！");
		        	}  
	        	}
				catch(e)
				{
					alert(e.message);
				}
         }
	    	 //查询默认号码
	    function selectDefaults(){
	    	try{
			   var params = "";
			   var myAjax = new parent.parent.Ajax.Request(
				"ac001/selectDefault.action",{
							method:'post',
							parameters:params,
							onComplete:function (request) {
								var action = eval( "("+request.responseText+")");
								if(action.lsDefaultBean!=null && action.lsDefaultBean.length>0)
								{
									var btndate={Rows:action.lsDefaultBean,Total: action.lsDefaultBean.length};	
									defaultphones.options.data=$.extend(true, {},btndate);
            						defaultphones.loadData(true);	
								}
								else
								{
									defaultphones.options.data=$.extend(true, {},{Rows:null,Total:0});
            						defaultphones.loadData(true);	
								}
							},
							asynchronous:true
						});			  
    	   }catch(e){
    		 alert(e.message);
    	   }
	    } 
   
        //---------------------------------------------回车事件
		function hotKeyOfSelf(key)
		{
			if(key==13)//回车
			{
				hotKeyEnter();
			}
			
		}
		
		function  hotKeyEnter()
		{
		
			try
			{
				window.event.keyCode=9; //tab
				window.event.returnValue=true;
				
			}
			catch(e){alert(e.message);}
				
		}   
		
	</script>
	
