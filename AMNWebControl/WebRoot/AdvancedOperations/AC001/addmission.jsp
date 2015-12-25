<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/sysfinal.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>任务</title>
    <link href="<%=ContextPath%>/common/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=ContextPath%>/common/ligerui/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
   	<script src="<%=ContextPath%>/common/ligerui/ligerUI/js/ligerui.all.js"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ContextPath%>/common/ligerui/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
    <script src="<%=ContextPath%>/common/ligerui/jquery-validation/messages_cn.js" type="text/javascript"></script> 
 	<script src="<%=ContextPath%>/common/ligerui/json2.js" type="text/javascript"></script>
 	<script type="text/javascript" src="<%=ContextPath%>/common/amnreport.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
</head>
<body style="padding:6px; overflow:hidden;" style="font-size:12px;line-height:35px;"><center>
	     <table border="0" width="90%" height="100%" style="font-size:12px;line-height:35px;">
	     	 <tr>
					<td valign="top" height="130px"><div  id="missionditail"  style="margin:0; padding:0" style="font-size:12px;line-height:35px;"> </div></td>
			</tr>
			<tr></tr>
			<tr>
					<td valign="top" height="30px"><div  id="missionditails"  style="margin:0; padding:0" style="font-size:12px;line-height:35px;"> </div></td>
			</tr>
			<tr></tr>
			<tr>
			
					<td valign="top" ><div width="100%" style="font-size:12px;border:solid 1px #aaa;"><table border="0" style="font-size:12px" >
					<tr>
							<td colspan="4"><input type="text" id="missionbillid" style="width:160px" style="display:none;"/></td>
					  </tr>
					  <tr>
							<td width="38px">姓    名</td>
							<td width="300px"><input type="text" id="missionnames" style="width:160px"/></td>
							<td width="38px">手机号</td>
							<td ><input type="text" id="missionphone" style="width:160px"/></td>
					  </tr>
                      <tr>
                     		 <td >名  称</td>
                             <td><input type="text" id="missionname" style="width:160px"/></td>
							 <td >时    间</td>
                             <td ><div id="message1" class="message" title="注意：任务时间只能是一个月之内的时间" >
                             <input type="text"   id="showtime" style="width:160px" onclick =" onnames();"/></div></td> 
								
					</tr>
					<tr>
						 <td valign="top" >状 态</td>
                         <td valign="top" colspan="3" >
								    <select id="templatestate" style="width:160 " >
								    <option value="1">已发送</option>
								    <option value="0" selected="true">未发送</option>
								    <option value="2" >已停用</option>
								    </select>
						 </td>
				   </tr>
				   <tr>
						 <td valign="top" >内  容 </td>
						 <td colspan="3">
						 <textarea name="missionBean.missiondetails" id="missiondetails" style="width:300px;height:83px"></textarea></td>
				    </tr>
           </table></div>
           </td>
           </tr>
           </table>
		</center> 
</body>
</html>
	
	
	
<script>
	  	 	var contextURL="<%=request.getContextPath()%>";
	  	 	var t =60;  
	  	 	var  missionditail;			//显示任务详情
	  	 	var missionditails;
	  	 	var missionid="";
            var chooseData = [{ choose: '0', text: '未发送' }, { choose: '1', text: '已发送'}, { choose: '2', text: '已停用'}];
    	 try{
        missionditail=$("#missionditail").ligerGrid({
                columns: [
                { display:'单号' , name:'missionbillid', width:140},
                { display: '时间', name: 'missionkey',  width: 160},
                { display: '任务名称', name: 'missionname',  width: 270},
                { display: '状态', name: 'missiondetails',  width: 141, 
	            	editor: { type: 'select', data: chooseData, valueField: 'choose' },
	            	render: function (item)
	              	{
	                  if (item.templatestate == 1) return '已发送';
	                  if (item.templatestate == 2) return '已停用';
	                  return '未发送';
	                }
	            }
                ],data:selectmissioin(),
                pageSize:10, 
                width: '100%',
                height:230, 
                enabledEdit: true,
                rownumbers:true,
                usePager:false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadselectData(data, rowindex, rowobj);
                }, 
                toolbar: { items: [
                { text: '任务清单',click:selectmissioin,img: contextURL+'/common/ligerui/ligerUI/skins/icons/search.gif' },
               	{ text:'添加',click:addmissions,img: contextURL+'/common/ligerui/ligerUI/skins/icons/save-disabled.gif' }
               	//,
                //{ text: '停用',click:stopmissioin,img: contextURL+'/common/ligerui/ligerUI/skins/icons/busy.gif'}
                ]
                }
            })
           missionditails=$("#missionditails").ligerGrid({
                columns: [
                { display:'单号' , name:'missionbillid', width:140},
                { display: '姓名', name: 'missionname',  width: 100},
                { display:'手机号' , name:'missionphone', width:140},
                { display: '内容', name: 'missiondetails',  width: 373}
                ],
                pageSize:10, 
                width: '100%',
                height:100, 
                enabledEdit: true,
                rownumbers:true,
                 usePager:false
            })
				$("#showtime").ligerDateEditor({ showTime:true,width:160,format: "yyyy-MM-dd hh:mm:ss",labelWidth: 80, labelAlign: 'right' });
				$("#pageloading").hide(); 
				addmissionses();
				
				 var i = 0;
            
            //这里是在鼠标经过时才显示，没有传入参数，或者传入的参数列表有 auto:true这个参数/值
            $("#message1").ligerTip();

            //这里调用一次就会显示一下
            $("#message2").ligerTip({ content: $("#message2").html() });
				
				
	 		 }catch(e){alert(e.message);}
	 
	   function stopmissioin()
	      {
		    		var requestUrl ="ac001/updatemissionstates.action?";
				    parent.sendRequestForParams_p(requestUrl,'','' );
	      }
	     //定时发送
	       function sendmission(){
	    	   try{
					var jsonParam="";
				    var curjosnparam="";
					var needReplaceStr="";
					var data=null;
					if(missionditail.options.pageCount==1)
					{
						data =missionditail.getData();
						jsonParam=JSON.stringify(data);
					}
					else
					{
						data =searchDate;
						jsonParam=JSON.stringify(data);
					}
					if(jsonParam.indexOf("_id")>-1)
					{
					    needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
					    jsonParam=jsonParams.replace(needReplaceStr," ");
					}	
					    var requestUrl ="ac001/sendtimelyMessages.action";
						/*var params="strJsonParam=["+jsonParam+"]";
						params=params+"&missionkey="+jsonParam;
						params=params+"&memeberPhone="+jsonParam;
						params=params+"&missiondetails="+jsonParam;*/
						var params="missionkey="+document.getElementById("showtime").value;
						    params=params+"&missionphone="+document.getElementById("missionphone").value;
				            params=params+"&missiondetails="+document.getElementById("missiondetails").value;
					    parent.sendRequestForParams_p(requestUrl,'',params );
				}
				catch(e)
				{
					alert(e.message);
				}
	       }
	    function addmissionses(){
	     var row = missionditails.getSelectedRow();
					 missionditails.addRow({ 
					 			    missionbillid:"",
								    missionkey:"",
					                missionname:"",
					                templatestate:""
					            }, row, false);
	    }
	   
  	 	 //添加任务
         function addmissions(){
         try
         {
	            	 var row = missionditail.getSelectedRow();
					 missionditail.addRow({ 
					 			    missionbillid:document.getElementById("missionbillid").value,
								    missionkey:document.getElementById("showtime").value,
					                missionname:document.getElementById("missionname").value,
					                templatestate: document.getElementById("templatestate").value
					            }, row, false);
				     var requestUrl ="ac001/addmission.action";
				     var responseMethod="addmissionMessage";	
				   	 var    params="missionkey="+document.getElementById("showtime").value;
						    params=params+"&missionname="+document.getElementById("missionname").value;
						    params=params+"&templatestate="+document.getElementById("templatestate").value;
						    params=params+"&missionnames="+document.getElementById("missionnames").value;
						    params=params+"&missionphone="+document.getElementById("missionphone").value;
				            params=params+"&missiondetails="+document.getElementById("missiondetails").value;
				 	   		parent.sendRequestForParams_p(requestUrl,responseMethod,params );
				 	   		sendmission();
				 	   		selectmissioin();
				 	   		//setTimeout("sendmission()",60*60*1000);//每一个小时刷新一次
	      		}catch(e){alert(e.message);}
	    }
	   
	     function loadselectData(data,rowindex,rowobj){
   			 try{  
        		 	 missionid=data.missionId;
	       			 var params = "id="+missionid;
					 var myAjax = new parent.parent.Ajax.Request(
									  "ac001/selectmissionid.action",{
									  method:'post',
									  parameters:params,
									  onComplete:function (request) {
									  var action = eval( "("+request.responseText+")");
									 if(action.lsMissionBean!=null && action.lsMissionBean.length>0)
									 {
										var btndate={Rows:action.lsMissionBean,Total: action.lsMissionBean.length};	
										missionditails.options.data=$.extend(true, {},btndate);
	            						missionditails.loadData(true);	
									 }
									 else
									 {
										missionditail.options.data=$.extend(true, {},{Rows:null,Total:0});
	            						missionditail.loadData(true);	
									 }
									 loadmission(responseText.missionBean);
								},
								asynchronous:true
						});	 	
    	 	     }catch(e){
    		         alert(e.message);
    	   	  }
  	 	 }
  
      function loadmission(mission)
    {
	    	document.getElementById("id").value=data.id;
	    	document.getElementById("missionbillid").value=checkNull(mission.strBillId);
			document.getElementById("showtime").value=checkNull(mission.showtime);
			document.getElementById("missionname").value=checkNull(mission.missionname);
			document.getElementById("templatestate").value=checkNull(mission.templatestate);
			document.getElementById("missionnames").value=checkNull(mission.missionnames);
			document.getElementById("missionphone").value=checkNull(mission.missionphone);
			document.getElementById("missiondetails").value=checkNull(mission.missiondetails);
    }
      	
      		//查询任务清单
	     function selectmissioin(){
	    	try{
			   var params = "";
			   var myAjax = new parent.parent.Ajax.Request(
				"ac001/selectmission.action",{
							method:'post',
							parameters:params,
							onComplete:function (request) {
							var action = eval( "("+request.responseText+")");
								if(action.lsMissionBean!=null && action.lsMissionBean.length>0)
								{
									var btndate={Rows:action.lsMissionBean,Total: action.lsMissionBean.length};	
									missionditail.options.data=$.extend(true, {},btndate);
            						missionditail.loadData(true);	
								}
								else
								{
									missionditail.options.data=$.extend(true, {},{Rows:null,Total:0});
            						missionditail.loadData(true);	
								}
							},
							asynchronous:true
						});			  
    	   }catch(e){
    		 alert(e.message);
    	   }
	    }
	</script>
	