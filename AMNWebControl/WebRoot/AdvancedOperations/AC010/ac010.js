    var selectludata;    	 
    var selectludatas;
    var selectcustom;
    var showDialogmanager;
    $(function () { 
    		 selectludata=$("#selectludata").ligerGrid({
                columns: [
//                { display: '单号', name: 'callbillid',width:170},
                { display: '日期', name:'calltimes',width:270}
                ],
                data:selectludatas(),
                pageSize:50,
                width:300, 
                height:'98%',
                enabledEdit: true,
                frozen:false,usePager:false,
                groupColumnName:'calltime', groupColumnDisplay:'日期',
                 onSelectRow : function (data, rowindex, rowobj)
                {
    				 loadbybillid(data.calltimes);
                },
                toolbar:{items:[
                	 	{ text: '加载录音',img: contextURL+'/common/ligerui/ligerUI/skins/icons/process.gif'}
                	 ]}
           	   }); 
    		  selectludatas=$("#selectludatas").ligerGrid({
                columns: [
                { display: '呼叫时间', name: 'calltime',width:200},
                { display: '主叫', name: 'callon',width:140},
                { display: '被叫', name: 'vallend',width:140},
                { display: '坐席', name: 'calledon',width:140},
                { display: '通话时长', name: 'callhoues',width:160},
                { display: '操作', isSort: false, width: 120, render: function (data, rowindex, value)
                {
                    var h = "";
                    if (!data._editing)
                    {
                        h += "<a href='http://10.1.1.4:8180/ucallcenter/record/monitor/127.0.0.1/"+data.calltime +"/" + data.callall + ".mp3'>播放</a> ";
                    }
                    return h;
                }
                }
                ],
                data:selectls(),
                pageSize:30,
                width:'100%', 
                height:640,
                enabledEdit: true,
                rownumbers:true, 
                 groupColumnName:'calltimes', groupColumnDisplay:'日期'
                });
            $("#calltime").ligerDateEditor({width:120,format: "yyyy-MM-dd",labelWidth: 80, labelAlign: 'right' });
            $("#calltimes").ligerDateEditor({width:120,format: "yyyy-MM-dd",labelWidth: 80, labelAlign: 'right' });
    	 	$("#callbaobar").ligerToolBar({ items: [
            	{ text:'电话报表' ,img: contextURL+'/common/ligerui/ligerUI/skins/icons/database.gif'}]});
            $("#sercher").ligerToolBar({ items: [
            	{ text:'查询' ,click:selectls,img: contextURL+'/common/ligerui/ligerUI/skins/icons/search.gif'}]});
    });
      
    function selectls(){
    	try{
    	 var params ="calltime="+document.getElementById("calltime").value.replace("-","");
    	 	 params =params+"&calltimes="+document.getElementById("calltimes").value.replace("-","");
    	 	 params =params+"&callon="+document.getElementById("callon").value;
    	 	 params =params+"&calledon="+document.getElementById("calloned").value;
    	 	 params =params+"&calltype="+document.getElementById("calltype").value;
			 var myAjax = new parent.Ajax.Request(
				"ac010/selectlu.action",{
							method:'post',
							parameters:params,
							onComplete:function (request) {
								var action = eval( "("+request.responseText+")");
								if(action.lsCallingAdminBean!=null && action.lsCallingAdminBean.length>0)
								{
									var btndate={Rows:action.lsCallingAdminBean,Total: action.lsCallingAdminBean.length};	
									selectludatas.options.data=$.extend(true, {},btndate);
            						selectludatas.loadData(true);	
								}
								else
								{
									 $.ligerDialog.warn("数据未上传！");
									selectludatas.options.data=$.extend(true, {},{Rows:null,Total:0});
            						selectludatas.loadData(true);	
										}
							},
							asynchronous:true
						});			  
    	   }catch(e){
    		 alert(e.message);
    	   }
	    }
    function selectludetatils(calltime,callbillid){
    	try{
    	 var params ="calltime="+calltime;
    	 	 params =params+"&callbillid="+callbillid;
    	 	  showDialogmanager =  $.ligerDialog.waitting('正在上传中,请稍候...');
			   var myAjax = new parent.Ajax.Request(
				"ac010/selectrecords.action",{
							method:'post',
							parameters:params,
							onComplete:function (request) {
							showDialogmanager.close();
							 selectludatas();
							 $.ligerDialog.success("上传数据成功！");
							}
						});			  
    	   }catch(e){
    		 alert(e.message);
    	   }
	    }
     function selectludatas(){
	    	try{
			   var params = "";
			   var myAjax = new parent.Ajax.Request(
				"ac010/selectrecord.action",{
							method:'post',
							parameters:params,
							onComplete:function (request) {
								var action = eval( "("+request.responseText+")");
								if(action.lsCallingAdminBean!=null && action.lsCallingAdminBean.length>0)
								{
									var btndate={Rows:action.lsCallingAdminBean,Total: action.lsCallingAdminBean.length};	
									selectludata.options.data=$.extend(true, {},btndate);
            						selectludata.loadData(true);	
								}
								else
								{
									selectludata.options.data=$.extend(true, {},{Rows:null,Total:0});
            						selectludata.loadData(true);	
								}
							},
							asynchronous:true
						});			  
    	   }catch(e){
    		 alert(e.message);
    	   }
	    }
    //加载日期文件
	     function selectdata(){
	    	 try{
	    	   showDialogmanager =  $.ligerDialog.waitting('正在加载中,请稍候...');
			   var requestUrl =contextURL+"/ac010/selectludate.action"; 
					 $.ajax({
						type: 'POST',
						url: requestUrl,
						data: null,
						dataType: 'json',
						success: function (data) { 
							 showDialogmanager.close();
							}
						});			  
			}catch(e){alert(e.message);}
	     }
	       function loadbybillid(calltime){
    	try{
    	 var params ="calltime="+calltime;
			 var myAjax = new parent.Ajax.Request(
				"ac010/loadbybillid.action",{
							method:'post',
							parameters:params,
							onComplete:function (request) {
								var action = eval( "("+request.responseText+")");
								if(action.lsCallingAdminBean!=null && action.lsCallingAdminBean.length>0)
								{
									var btndate={Rows:action.lsCallingAdminBean,Total: action.lsCallingAdminBean.length};	
									selectludatas.options.data=$.extend(true, {},btndate);
            						selectludatas.loadData(true);	
								}
								else
								{
									selectludatas.options.data=$.extend(true, {},{Rows:null,Total:0});
            						selectludatas.loadData(true);	
								}
							},
							asynchronous:true
						});			  
    	   }catch(e){
    		 alert(e.message);
    	   }
	    }
 