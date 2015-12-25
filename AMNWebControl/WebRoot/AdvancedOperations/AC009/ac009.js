    var selectcustom;
    var customdetails;
    var showDialogmanager;
    $(function () { 
    		customdetails=$("#customdetails").ligerGrid({
                columns: [
                { display: '卡号', name: 'memberno',width:120},
                { display: '卡类型', name:'cardtypename',width:110},
                { display: '卡余额', name:'accountbalance',width:80}
                ],
                pageSize:10,
                width:350, 
                height:563,
                enabledEdit: true,
                rownumbers:true,usePager:false
           	   }); 
    		  selectcustom=$("#selectcustom").ligerGrid({
                columns: [
                { display: '类别', name:'billtype',width:40},
                { display: '姓名', name: 'membername',width:70},
                { display: '来电号码', name: 'call_no',width:100},
                { display: '接听坐席', name: 'agent_num',width:80},
                { display: '内容', name: 'orderproject',width:240 },
               	{ display: '备注',  name:'orderdetail',width:120},
                { display: '更新时间', name: 'ordertime',width:160} 
//               	{ display: '操作', isSort: false, width: 80, render: function (data, rowindex, value)
//                {
//                    var h = "";
//                    if (!data._editing)
//                    {  
//                    	h += "<a href='http://10.1.1.4:8180/ucallcenter/record/monitor/127.0.0.1/"+data.offer_time.replace("-","").replace("-","").substring(0,8) +"/" + data.callall + "'>播放</a> ";
//                    }
//                    return h;
//                }
//                }
                ],
                frozen:false,
                pageSize:10,
                pageSize:10,
                width:850, 
                height:739,
                pageSize: 30,
                enabledEdit: true,
                rownumbers:true,
                 onSelectRow : function (data, rowindex, rowobj)
                {
                	loadbycuphone(data.call_no);
                },
                 toolbar:{items:[
                	 	{ text: '客户来电信息',img: contextURL+'/common/ligerui/ligerUI/skins/icons/msn.gif'}
                	 ]}
                });
            $("#ordertime").ligerDateEditor({width:160,format: "yyyy-MM-dd",labelWidth: 80, labelAlign: 'right' });
    	 	$("#customselects").ligerToolBar({ items: [
            	{ text:'查询' ,click:loadbycustoms,img: contextURL+'/common/ligerui/ligerUI/skins/icons/search.gif'}]});
  		  initpage();
  		  addcustomdetails();
    });
	    	 function initpage()
       		{
       			var requestUrl = "ac009/initpage.action?";
				var responseMethod="initpageMessage";	
				sendRequestForParams_p(requestUrl,responseMethod,"" ); 
       		}
       		function initpageMessage(request){
				var action = eval( "("+request.responseText+")");
				lsCallingAdminBean=action.lsCallingAdminBean;
 				 changeMFPrjBook(document.getElementById("calluserids"));
 				 loadbycustoms();
			}
       		
       		function changeMFPrjBook(obj)
			{
			try{
				clearOption("calluserids");
				addOption("","请选择",document.getElementById("calluserids"));
				if(lsCallingAdminBean!=null && lsCallingAdminBean.length>0)
				{
					for(var i=0;i<lsCallingAdminBean.length;i++)
					{
							addOption(lsCallingAdminBean[i].calluserid,lsCallingAdminBean[i].staffname+"-"+lsCallingAdminBean[i].calluserid,document.getElementById("calluserids"));
					}
				}	
			}
			catch(e)
			{
				alert(e.message);
				
			}
		}
        function loadbycustoms(){
    	try{
    		 var params ="callon="+document.getElementById("callon").value;
	    	 	 params=params+"&membername="+document.getElementById("membername").value;
	    	 	 params=params+"&ordertime="+document.getElementById("ordertime").value;
	    	 	 params=params+"&calluserid="+document.getElementById("calluserids").value;
				 var myAjax = new parent.Ajax.Request(
					"ac009/loadbycustoms.action",{
								method:'post',
								parameters:params,
								onComplete:function (request) {
									var action = eval( "("+request.responseText+")");
									if(action.lsCallsWatingBean!=null && action.lsCallsWatingBean.length>0)
									{
										var btndate={Rows:action.lsCallsWatingBean,Total: action.lsCallsWatingBean.length};	
										selectcustom.options.data=$.extend(true, {},btndate);
	            						selectcustom.loadData(true);
									}
									else
									{
										$.ligerDialog.warn("您查询的没有记录！");
										selectcustom.options.data=$.extend(true, {},{Rows:null,Total:0});
	            						selectcustom.loadData(true);	
									}
								},
								asynchronous:true
							});		
	    	   }catch(e){
	    		 alert(e.message);
    	   }
	    }
        
      
        function loadbybillid(callbillid){
    	try{
    	 var params ="callbillid="+callbillid;
			 var myAjax = new parent.Ajax.Request(
				"ac009/loadbybillid.action",{
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
     
        function loadbycuphone(call_no){
	    	try{
	    		 var params ="callon="+call_no;
				 var myAjax = new parent.Ajax.Request(
					"ac009/loadbymemever.action",{
								method:'post',
								parameters:params,
								onComplete:function (request) {
									var action = eval( "("+request.responseText+")");
									if(action.lsCallingAdminBean!=null && action.lsCallingAdminBean.length>0)
									{
										var btndate={Rows:action.lsCallingAdminBean,Total: action.lsCallingAdminBean.length};	
										customdetails.options.data=$.extend(true, {},btndate);
	            						customdetails.loadData(true);	
									}
									else
									{
										customdetails.options.data=$.extend(true, {},{Rows:null,Total:0});
	            						customdetails.loadData(true);	
											}
								},
								asynchronous:true
							});			  
	    	   }catch(e){
	    		 alert(e.message);
	    	   }
		    }
    
	     function addcustomdetails(){
		  		   var row = customdetails.getSelectedRow();
		  		   			 customdetails.addRow({
		  		   				memberno:"",
		  		   				cardtypename:"",
		  		   				accountbalance:""
		  		   			 },row,false);
		  	   }
	     
