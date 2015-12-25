    var selectdetail;     	 	//显示查询结果
    var missionditail;			//显示任务详情
    var  defaultphones;			//显示默认发送号码
       $(function () {   
       	try{
       		  $("#selecttools").ligerGrid({
       			  width:'100%',
       			  usePager:false,
                 toolbar:{items:[
                	 {text:'查询条件',},
                	 ]}
            });
       		  
       		  $("#messagetext").ligerGrid({
       			 width:'100%',
       			   usePager:false,
       			 toolbar:{
       			  items:[
       				   {text: '短信内容：', }
       			  ]
       			 }
       			  
       		  });
       		  $("#btn1").ligerButton(
	         {
	             text: '立即发送', width:80,
		         click: function ()
		         {
		           sedBtn();
		         }
	         
	         });
       		   $("#btn2").ligerButton(
	         {
	             text: '即时发送', width:80,
		         click: function ()
		         {
	        	$.ligerDialog.open({ title: '添加', url: contextURL+'/AdvancedOperations/AC001/addmission.jsp',
	        		height: 520, width: 700, isResize: true } 
	        	);
	        	// addsend();
		           		         }
	         
	         });
       		 $("#sdetail").ligerButton(
	         {
	             text: '查询', width: 120,
		         click: function ()
		         {
		           gbmSelct();
		         }
	         
	         });
       		  $("#add").ligerButton(
	         {
	             text: '添加任务', width: 120,
		         click: function ()
		         {
	        	 alert(1111)
		          // addsend();
	        	 // alert(1111)
	        	addmission();
 		         }
	         
	         });
       		  selectdetail=$("#selectdetails").ligerGrid({
                columns: [
               { display: '姓名', name: 'menberName',  width: 100 },
                { display: '手机号', name: 'phone',  width: 205, },
                { display: '卡号', name: 'memberNo',   width: 251 },
                { display: '卡类型', name: 'cardClass', width: 100, }
                ],  pageSize:10, 
                width: '100%',
                height:'50%', enabledEdit: true, 
                 rownumbers:true,usePager:true,
                 toolbar:{items:[
                	 {text:'批量导入',},
                	   { line: true },
                	 {text:'手机号:',},
                 	 {text:'<input type="text" name="phones" id="phones"/>',ids:"phones",},
                	 {text:'添加',click:addphone},
                	   { line: true },
                	   {text:'过滤重复号码',}
                	 ]}
            });

       		  
                 $("#editDetailInfo").ligerButton(
	         {
	             text: '模板管理', width: 120,
		         click: function ()
		         {
		             //editCurDetailInfo();
		         }
	         });
                 
    
//       function addmission(){
//				var data = selectdetail.getData();
//            	var queryStringTmp=$('#addmissionfrom').serialize();
//				var requestUrl ="ac001/post.action";
//				var params=queryStringTmp;
//			//	var requestUrl ="ac001/post.action";
//			//	var params="strJsonParam=["+jsonParam+"]";
//				params=params+"&missionnames="+document.getElementById("missionnames").value;
//				params=params+"& missionkey="+document.getElementById(" missionkey").value;
//				params=params+"&templatestate="+document.getElementById("templatestate").value;
//				var responseMethod="messageMsg";				
//				sendRequestForParams_p(requestUrl,responseMethod,params ); 
//				alert( params);
//			     var row = missionditail.getSelectedRow();
//				  missionditail.addRow({ 
//					  missionnames:"",
//					  missionkey:"",
//				  		templatestate:""
////				                missionnames:document.getElementById("missionnames").value,
////				                missionkey:document.getElementById("missionkey").value,
////				                templatestate:document.getElementById("templatestate").value,
//				            }, row, false);
//		
//       }
           function gbmSelct(){
    	 try{
    		 var jsonParam="";
    		   var params=" ";
				var requestUrl ="ac001/btuselectBean.action";
				var params="strJsonParam="+jsonParam+"";
				var	curComp = getCurOrgFromSearchBar();
				//params=params+"&strCompId()="+document.getElementById("strCompId").value;
				params=params+"&gbaBean.getCardClass()="+document.getElementById("cardClass").value;
				params=params+"&gbaBean.getCardmonney()="+document.getElementById("cardmonney").value;
				params=params+"&gbaBean.getCardtreatment()="+document.getElementById("cardtreatment").value;
				params=params+"&gbaBean.getBirthday()="+document.getElementById("birthday").value;
				params=params+"&gbaBean.getDataactivity()="+document.getElementById("dataactivity").value;
				sendRequestForParams_p(requestUrl,null,params ); 
				alert( params);
			   var myAjax = new parent.Ajax.Request(
				"ac001/btuselectBean.action",{
							method:'post',
							parameters:params,
							onComplete:function (request) {
								var action = eval( "("+request.responseText+")");
							
								if(action.lsCardInfos!=null && action.lsCardInfos.length>0)
								{
									var btndate={Rows:action.lsCardInfos,Total: action.lsCardInfos.length};	
									selectdetail.options.data=$.extend(true, {},btndate);
            						selectdetail.loadData(true);	
								}
								else
								{
									document.getElementById("strCompId").value=checkNull(action.strCompId);
									selectdetail.options.data=$.extend(true, {},{Rows:null,Total:0});
            						selectdetail.loadData(true);	
								}
							},
							asynchronous:true
						});			  
    	   }catch(e){
    		 alert(e.message);
    	   }
       }
          //添加任务信息
                 function addmission(){
                	var queryStringTmp=$('#addmissionfrom').serialize();
					var requestUrl ="ac001/post.action";
					var params=queryStringTmp;
					var curjosnparam="";
			        var needReplaceStr="";
			        var strJsonParam_detial="";
			        //任务列表
			        for(var rowid in  missionditail.getSelectedRow()){
			        	//var row=missionditail.records[rowid];
			        	var row = missionditail.getSelectedRow();
							  missionditail.addRow({ 
				                missionnames:"",
				                missionkey:"",
				                templatestate: ""
				            }, row, false);
			        	curjosnparam=JSON.stringify(row);
			        	if(curjosnparam.indexOf("_id")>-1)
				{
				   	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				   	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				}	            		   
						if(strJsonParam_detial!="")
						  	strJsonParam_detial=strJsonParam_detial+",";
						strJsonParam_detial= strJsonParam_detial+curjosnparam;
			        }
			      if(strJsonParam_detial!="")
		{
				 params=params+"&strJsonParam=["+strJsonParam_detial+"]";
			      params=params+"&missionBean.getMissionnames()="+document.getElementById("missionnames").value;
				params=params+"&missionBean.getMissionkey()="+document.getElementById("missionkey").value;
				params=params+"&missionBean.getTemplatestate()="+document.getElementById("templatestate").value;
				  params=params+"&missionBean.getMissionphone()="+document.getElementById("missionphone").value;
				params=params+"&missionBean.getMissionname()="+document.getElementById("missionname").value;
				params=params+"&missionBean.getMissiontype()="+document.getElementById("missiontype").value;
		        params=params+"&missionBean.getMissiondetails()="+document.getElementById("missiondetails").value;
			}
//					var responseMethod="postMessage";
					sendRequestForParams_p(requestUrl,"",params );
                 }
            missionditail=$("#missionditail").ligerGrid({
                columns: [
                { display: '时间', name: 'missionnames',  width: 180,},
                { display: '任务名称', name: 'missionkey',  width: 320,},
                { display: '状态', name: 'templatestate',  width: 130, }
                ],  pageSize:10, checkbox:true,
                width: '100%',
                height:'40%', enabledEdit: true, 
                 rownumbers:true,usePager:false,
               // usePager:false,
                toolbar: { items: [
                { text: '任务清单:',click:addmission},
                { text: '停用'},
                { text: '删除'},
                ]
                }
            });
    
              defaultphones=$("#defaultphones").ligerGrid({
                columns: [
                { display: '姓名', name: '1',  width:92 },
                { display: '号码', name: '1',  width: 181, align: 'left' }
                ],  pageSize:10, 
                width: '95%',
                height:300,
                 enabledEdit: true,
                 rownumbers:true,usePager:false,
                toolbar: { items: [
                { text: '默认发送号码:'},
                ]
                }
            });
               $("#birthday").ligerDateEditor({ 
            	   labelWidth: 100,width:110, labelAlign: 'left' });
               $("#birthdays").ligerDateEditor({ 
            	   labelWidth: 100,width:110, labelAlign: 'left' });
                $("#showtime").ligerDateEditor({ 
                	showTime:true,width:160,
                	format: "yyyy-MM-dd hh:mm",
            	   labelWidth: 200, labelAlign: 'right' });
                 $("#showday").ligerDateEditor({ 
                	showTime:true,width:160,
                	format: "MM-dd hh:mm",
            	   labelWidth: 200, labelAlign: 'right' });
  
                 
            $("#pageloading").hide(); 
            }catch(e){alert(e.message);}
         
        });
function postMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("任务添加成功!");
	        	 curpagestate=3;
	        }
	        else
	        {
	        	alert(strMessage);
	        }
        }
		catch(e)
		{
			alert(e.message);
		}
    }
    
        //新增行
     function addphone()
     {
	        	var row = selectdetail.getSelectedRow();
				   selectdetail.addRow({ 
				                menberName: "",
				                phone:document.getElementById("phones").value,
				                memberNo: "",
				                cardClass: ""
				            }, row, false);
				
	        }

         
          function sedBtn(){
    	   try{
				var jsonParam="";
				var curjosnparam="";
				var needReplaceStr="";
				var data = selectdetail.getData();
            	jsonParam=JSON.stringify(data);
            	if(jsonParam.indexOf("_id")>-1)
				{
				    needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				    jsonParam=jsonParam.replace(needReplaceStr," ");
				}	
				var requestUrl ="ac001/sendMessage.action";
				var params="strJsonParam=["+jsonParam+"]";
				params=params+"&memeberPhone="+jsonParam;
				params=params+"&msgText="+document.getElementById("smgText").value;
				var responseMethod="messageMsg";				
				sendRequestForParams_p(requestUrl,responseMethod,params ); 
				alert( params);
			}
			catch(e)
			{
				alert(e.message);
			}
       }
      function messageMsg(request){
    	  try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	
	        		  $.ligerDialog.success("发送成功!");
	        	}
	        	else
	        	{
	        		alert(strMessage);
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
        }
   
//    	   try{
//    		   var jsonParam="";
//    		   var params=" ";
//				var requestUrl ="ac001/btuselectBean.action";
//				var params="strJsonParam="+jsonParam+"";
//				params=params+"&selectMsgBean.getStrCompId()="+document.getElementById("strCompId").value;
//				params=params+"&selectMsgBean.getCardtype()="+document.getElementById("cardtype").value;
//				params=params+"&selectMsgBean.getCardmonney()="+document.getElementById("cardmonney").value;
//				params=params+"&selectMsgBean.getCardtreatment()="+document.getElementById("cardtreatment").value;
//				params=params+"&selectMsgBean.getBirthday()="+document.getElementById("birthday").value;
//				params=params+"&selectMsgBean.getDataactivity()="+document.getElementById("dataactivity").value;
//				sendRequestForParams_p(requestUrl,null,params ); 
//				alert( params);
    		  
//			   var myAjax = new parent.Ajax.Request(
//				   "ac001/btuselectBean.action",{
//							method:'post',
//							parameters:params,
//							onComplete:function (request) {	
//					     alert(4444444)
//							 alert(action.lsSelectMsgBean.length);
//						 	alert(111111111)
//							var action = eval( "("+request.responseText+")");
//							if(action.lsSelectMsgBean!=null && action.lsSelectMsgBean.length>0){
//									var selects={Rows:action.lsSelectMsgBean,Total: action.lsSelectMsgBean.length};	
//									selectdetail.options.data=$.extend(true, {},selects);
//            						selectdetail.loadData(true);	
//								}
//								else
//								{
//									selectdetail.options.data=$.extend(true, {},{Rows:null,Total:0});
//            						selectdetail.loadData(true);	
//								}
//								  alert(2222222222222222)
//							},
//							
//							asynchronous:true
//						});		
//    	   }catch(e){
//    		 alert(e.message);
//    	   }
//       }
     var str = "";
document.writeln("<div id=\"_contents\" style=\"padding:6px; background-color:#daebff;" +
" font-size: 12px; border: 1px solid #c6dffe; " +
" position:absolute; left:?px; top:?px; width:?px; height:?px; z-index:1; visibility:hidden\">");
str += "\u65f6<select name=\"_hour\">";
for (h = 0; h <= 9; h++) {
    str += "<option value=\"0" + h + "\">0" + h + "</option>";
}
for (h = 10; h <= 23; h++) {
    str += "<option value=\"" + h + "\">" + h + "</option>";
}
str += "</select> \u5206<select name=\"_minute\">";
for (m = 0; m <= 9; m++) {
    str += "<option value=\"0" + m + "\">0" + m + "</option>";
}
for (m = 10; m <= 59; m++) {
    str += "<option value=\"" + m + "\">" + m + "</option>";
}
str += "</select> \u79d2<select name=\"_second\">";
for (s = 0; s <= 9; s++) {
    str += "<option value=\"0" + s + "\">0" + s + "</option>";
}
for (s = 10; s <= 59; s++) {
    str += "<option value=\"" + s + "\">" + s + "</option>";
}
str += "</select> <input name=\"queding\" type=\"button\" onclick=\"_select()\" value=\"\u786e\u5b9a\" style=\"font-size:12px; background-color:#daebff;\" /></div>";
document.writeln(str);
var _fieldname;
function _SetTime(tt) {
    _fieldname = tt;
    var ttop = tt.offsetTop;    //TT控件的定位点高
    var thei = tt.clientHeight;    //TT控件本身的高
    var tleft = tt.offsetLeft;    //TT控件的定位点宽
    while (tt = tt.offsetParent) {
        ttop += tt.offsetTop;
        tleft += tt.offsetLeft;
    }
    document.all._contents.style.top = ttop + thei + 4;
    document.all._contents.style.left = tleft;
    document.all._contents.style.visibility = "visible";
}
function _select() {
    _fieldname.value = document.all._hour.value + ":" + document.all._minute.value + ":" + document.all._second.value;
    document.all._contents.style.visibility = "hidden";
}

    function showadv() {
 if(document.text.advshow.checked == true) {
  adv.style.display = "";
 } else {
  adv.style.display = "none";
 }
}
  
    