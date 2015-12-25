    var  selectdetail;     	 	//显示查询结果
    var  defaultphones;			//显示默认发送号码
    var  revokedetails;			//显示回复内容
    var  cinsertcardtypemanager = null; //卡类型列表
    var  cinsertprojectmanager = null; //疗程列表
    var showDialogmanager=null;
    var chooseData = [{ choose: '0', text: '未发送' }, { choose: '1', text: '已发送'}];
    var searchDate=null;
    $(function () {  
    	 	try{
        selectdetail=$("#selectdetails").ligerGrid({
                columns: [
                { display: '姓名', name: 'showmenberName',  	width: 110 },
                { display: '手机号', name: 'showphone',  		width: 200},
                { display: '卡号', name: 'showmemberNo',   	width: 250 },
                { display: '卡类型', name: 'cardClass', 		width: 124 },
                {  name:'menberName',hide:true},
                {  name: 'phone', hide:true},
                {  name: 'memberNo', hide:true }
                ],
                pageSize: 50,
                width: '100%', 
                height:'50%', 
                enabledEdit: true,
                rownumbers: true,
                usePager: true,
                toolbar:{items:[
                	  //---------------------------------读取Excel--------------------------------------------------
  						{text:'导入<input name="myFile" type="file">',click: fileUp,img: contextURL+'/common/ligerui/ligerUI/skins/icons/communication.gif' },
    					{text:'手机号&nbsp;&nbsp;&nbsp;<input type="text" name="phones" style="width:130" id="phones"/>',ids: "phones",img: contextURL+'/common/ligerui/ligerUI/skins/icons/edit.gif'},
                	 	{text:'添加',click: addphone,img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
                	 	{ text: '删除',click: deletedefaultphone,img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif'},
                	 	{ text: '删除所有',click: deleteAllphone,img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif'}
                	 ]}
            })
       defaultphones=$("#defaultphones").ligerGrid({
                columns: [
                { display: '姓名', name: 'name',  width:70 },
                { display: '号码', name: 'phone',  width: 150}
                ],
                data:selectDefaults(),
                checkbox:true,
                pageSize:10,
                width:'100%', 
                height:'100%',
                enabledEdit: true,
                rownumbers:true,usePager:false,
                toolbar: { items: [
                { text: '默认发送号码',click:selectDefaults,img: contextURL+'/common/ligerui/ligerUI/skins/icons/search.gif' },
                { text:'添加',click: function () {
	            	$.ligerDialog.open({ title: '添加', url: contextURL+'/AdvancedOperations/AC001/adddefault.jsp',
	        	    height: 200, width:300} ) },img: contextURL+'/common/ligerui/ligerUI/skins/icons/save-disabled.gif' },
                { text:'删除',click:deletedefault,img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif'}
                ]
                }
           })
         revokedetails=$("#revokedetails").ligerGrid({
                columns: [
                { display: '电话号码', name: 'revokphone',  width: 120},
                { display: '短信内容', name: 'revokdetails',  width: 300},
                { display: '回复时间', name: 'revokdate',  width: 147}
                ],
                pageSize:10, 
                width: '100%',
                height:'100%', 
                enabledEdit: true,
                rownumbers:true,
                usePager:false,
                toolbar: { items: [
                { text: '查询',click:addrevokes,width:'200',img: contextURL+'/common/ligerui/ligerUI/skins/icons/search.gif' },
                { text:'日期：<input type="text" name="time" id="time"/>---<input type="text" name="times" id="times"/>',ids: "time",ids:"times",img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif'},
                { text:'查询',click: addrevokeses,img: contextURL+'/common/ligerui/ligerUI/skins/icons/search2.gif' }
                ,
                { text:'导出',click:preview}
                ]
                }
            })
            $("#topmenu").ligerMenuBar({ items: [
            	{ text:'短信内容' ,img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif'},
                { text: '立即发送',click: function () {
	            	$.ligerDialog.open({ title: '请确认发送信息', url: contextURL+'/AdvancedOperations/AC001/sendMessage.jsp',
	        	    height: 280, width:500, isResize: true } )} ,img: contextURL+'/common/ligerui/ligerUI/skins/icons/mailbox.gif'},
                { text: '定时发送',click: function () {
	            	$.ligerDialog.open({ title: '定时发送', url: contextURL+'/AdvancedOperations/AC001/addmission.jsp',
	        	    height: 600, width: 900, isResize: true } ) } ,img: contextURL+'/common/ligerui/ligerUI/skins/icons/mailbox.gif'}
            ]
            });
        
		        $("#birthday").ligerDateEditor({format: "MM-dd hh:mm",labelWidth: 80,width:150, labelAlign: 'right' });
			    $("#birthdays").ligerDateEditor({format: "MM-dd hh:mm",labelWidth: 80,width:150, labelAlign: 'right' });
			    $("#sdetail").ligerButton({text: '查询', width: 120, click: function () {gbmSelct()}});
			    $("#pageloading").hide(); 
	 		    initpage();
	 		 }catch(e){alert(e.message);}
        });
    
	       //添加手机号
	  	   function addphone()
	    		 {
		          var row = selectdetail.getSelectedRow();
				   		    selectdetail.addRow({ 
					                menberName: "",
					                phone:document.getElementById("phones").value,
					                showphone:document.getElementById("phones").value,
					                memberNo: "",
					                cardClass: ""
					                }, row, false);
		        }

	  	   function adddefault(){
	  		   var row = defaultphones.getSelectedRow();
	  		   			 defaultphones.addRow({
	  		   				name:"",
	  		   				phone:""
	  		   			 },row,false);
	  	   }
	  	   
     		//初始化界面
       		function initpage()
       		{
       			var requestUrl ="ac001/initpage.action";
				var responseMethod="initpageMessage";	
				sendRequestForParams_p(requestUrl,responseMethod,"" ); 
       		}
       		
      		function initpageMessage(request){
        	 	try 
        	  	{
					var action = eval( "("+request.responseText+")");
					var returnValue='';
					
					cinsertcardtypemanager=$("#cardClass").ligerComboBox({  valueField: 'bcardtypeno' , textField: 'bcardtypeno',
						valueFieldID: 'factcCardClass', width: 450,
                		slide: false,
                		selectBoxWidth: 500,
                		selectBoxHeight: 240, grid: getGridCardtypeOptions(true,action.lsCardtypeInfo) });
					
					cinsertprojectmanager=$("#cardtreatment").ligerComboBox({  valueField: 'bprjno' , textField: 'bprjno',
						valueFieldID: 'factcCardtreatment', width: 450,
                		slide: false,
                		selectBoxWidth: 500,
                		selectBoxHeight: 240, grid: getGridOptions(true,action.lsProjectinfo)
		           		 });
				addrevok();
				addphone();
				adddefault();
				}catch(e)
				{
					alert(e.message)
				}			  
           }
           
        function getGridOptions(checkbox,lsProjectinfo)
        {
            var options = {
                columns: [
                { display: '疗程编号', name: 'bprjno',align: 'left', width: 80, minWidth: 60 },
                { display: '疗程名称', name: 'prjname', minWidth: 160, width: 100 },
                { display: '疗程次数', name: 'ysalecount', minWidth: 60, width: 100 },
                { display: '疗程价格', name: 'ysaleprice', width: 60 }
                ], switchPageSizeApplyComboBox: false,
                data: $.extend({}, {Rows: lsProjectinfo,Total: lsProjectinfo.length} ),
                pageSize: 30, 
                checkbox: checkbox
            };
            return options;
        }
        
        function getGridCardtypeOptions(checkbox,lsCardtypeInfo)
        {
            var options = {
                columns: [
                { display: '类型编号', name: 'bcardtypeno',align: 'left', width: 80, minWidth: 60 },
                { display: '类型名称', name: 'cardtypename', minWidth: 160, width: 100 },
                { display: '开卡金额', name: 'lowopenamt', minWidth: 60, width: 100 },
                { display: '充值金额', name: 'lowfillamt', width: 60 }
                ], switchPageSizeApplyComboBox: false,
                data: $.extend({}, {Rows: lsCardtypeInfo,Total: lsCardtypeInfo.length} ),
                pageSize: 30, 
                checkbox: checkbox
            };
            return options;
        }
           
          //添加回复信息   revokphone,revokdetails,revokdate
	    function addrevokes(){
	   			try{
			   var params = "";
			   var myAjax = new parent.Ajax.Request(
				"ac001/selecrevoke.action",{
							method:'post',
							parameters:params,
							onComplete:function (request) {
								var action = eval( "("+request.responseText+")");
								if(action.lsRevokBean!=null && action.lsRevokBean.length>0)
								{
									var btndate={Rows:action.lsRevokBean,Total: action.lsRevokBean.length};	
									revokedetails.options.data=$.extend(true, {},btndate);
            						revokedetails.loadData(true);	
								}
								else
								{
									revokedetails.options.data=$.extend(true, {},{Rows:null,Total:0});
            						revokedetails.loadData(true);	
								}
							},
							asynchronous:true
						});			  
    	   }catch(e){
    		 alert(e.message);
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
					if(selectdetail.options.pageCount==1)
					{
						data =selectdetail.getData();
						jsonParam=JSON.stringify(data);
					}
					else
					{
						data =searchDate;
						jsonParam=JSON.stringify(data);
					}
					var datas=defaultphones.getData();
					jsonParams=JSON.stringify(datas);
					/*if(jsonParams.indexOf("_id")>-1)
					{
					    needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
					    jsonParams=jsonParams.replace(needReplaceStr," ");
					}*/	
				    var requestUrl ="ac001/sendMessage.action";
					var params="strJsonParam=["+jsonParams+"]";
					params="strJsonParam=["+jsonParam+"]";
					params=params+"&memeberPhone="+jsonParam;
					params=params+"&phone="+ jsonParams;
					params=params+"&msgText="+document.getElementById("smgText").value;
					var responseMethod="sendMessageMessage";	
					if(parent.document.getElementById("smgText").value==""){
					     var s=$.ligerDialog.warn("发送失败， 手机号码或者内容为空！");
					     return;
					}else{
				    	sendRequestForParams_p(requestUrl,responseMethod,params );
					}
				}
				catch(e)
				{
					alert(e.message);
				}
	       }
	    
	    function sendMessageMessage(request){
	    	  try
				{
		        	var responsetext = eval("(" + request.responseText + ")");
		        	var strMessage=responsetext.strMessage;
		        	showDialogmanager.close();
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
	    
		 //根据字段查询信息
	     function addrevokeses(){
	    	  try{
	    		 var jsonParam="";
	    		 var params=" ";
				 var requestUrl ="ac001/selecrevokes.action";
				 var params="strJsonParam="+jsonParam+"";
				  if(checkNull(document.getElementById("time").value)==""&& checkNull(document.getElementById("times").value)=="")
				 {
				 	$.ligerDialog.warn("请确认查询信息!");
				 	return ;
				 }
					 params=params+"&time="+document.getElementById("time").value;
					 params=params+"&times="+document.getElementById("times").value;
					 var responseMethod="search";
					 showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');
					 sendRequestForParams_p(requestUrl,responseMethod,params );
				     	   }catch(e){
	    		     alert(e.message)
	    	   }
	       } 
	     
	    function search(request){
	 
	   				try {
	        	
					var action = eval( "("+request.responseText+")")
									if(action.lsRevokBean!=null && action.lsRevokBean.length>0)
									{
									   var btndate={Rows:action.lsRevokBean,Total: action.lsRevokBean.length};	
										revokedetails.options.data=$.extend(true, {},btndate);
            							revokedetails.loadData(true);
	            						revokedetails.select(0);
									}
									else
									{
										revokedetails.options.data=$.extend(false, {},'');
										revokedetails.loadData(true);
									$.ligerDialog.warn("没有查询到相应信息!");
									addrevok();
									}
									showDialogmanager.close();
								}catch(e){
									alert(e.message)
							}			  
    	   }
	    
	     //根据字段查询信息
	     function gbmSelct(){
	    	  try{
	    		 var jsonParam=""
	    		 var params=" "
				 var requestUrl ="ac001/btuselectBean.action"
				 var params="strJsonParam="+jsonParam+""
				 var curComp = getCurOrgFromSearchBar();
				 if(curComp=="001" 
				 && checkNull($("#factcCardClass").val())==""
				 && checkNull($("#factcCardtreatment").val())==""
				 && checkNull(document.getElementById("cardmonney").value)==""
				 && checkNull(document.getElementById("cardmonneys").value)==""
				 && checkNull(document.getElementById("birthday").value)==""
				 && checkNull(document.getElementById("birthdays").value)=="" 
				 && document.getElementById("dataactivity").value==0
				 )
				 {
				 	$.ligerDialog.warn("请确认查询信息!");
				 	return ;
				 }
					 params=params+"&compId="+curComp;
					 params=params+"&cardtype="+$("#factcCardClass").val();
					 params=params+"&cardmonney="+document.getElementById("cardmonney").value;
					 params=params+"&cardmonneys="+document.getElementById("cardmonneys").value;
				     params=params+"&cardtreatment="+$("#factcCardtreatment").val();
					 params=params+"&birthday="+document.getElementById("birthday").value;
					 params=params+"&birthdays="+document.getElementById("birthdays").value;
					 params=params+"&dataactivity="+document.getElementById("dataactivity").value;
					 var responseMethod="searchMessage"	;
					 showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');
					 sendRequestForParams_p(requestUrl,responseMethod,params );
				     	   }catch(e){
	    		     alert(e.message)
	    	   }
	       }
	     
	      function searchMessage(request){
	        	  try {
	        	
					var action = eval( "("+request.responseText+")")
									if(action.lsCardInfos!=null && action.lsCardInfos.length>0)
									{
									    var btndate={Rows:action.lsCardInfos,Total:action.lsCardInfos.length}	
										searchDate=action.lsCardInfos;
										selectdetail.options.data=$.extend(true, {},btndate);
	            						selectdetail.loadData(true);
	            						selectdetail.select(0);
									}
									else
									{
										selectdetail.options.data=$.extend(false, {},'')
										selectdetail.loadData(true)
										addphone();
									$.ligerDialog.warn("没有查询到相应信息!")
									}
									showDialogmanager.close();
								}catch(e){
									alert(e.message)
							}			  
	           }
	      
            function adddefaltphones(){
	            	 var row = defaultphones.getSelectedRow();
						     defaultphones.addRow({ 
								    name:"",
					                phone:""
					            }, row, false);
				   
	      		}
            function 	addrevok(){
        	   var row = revokedetails.getSelectedRow();
						      revokedetails.addRow({ 
								    revokphone:"",
					                revokdetails:"",
					                revokdate: ""
					            }, row, false);
				   
          }
	      function deletedefaultphone()
	      {
	      	selectdetail.deleteSelectedRow();
	      }
	       function deletedefault()
	      {
	      	defaultphones.deleteSelectedRow();
	      }
	      function deleteAllphone()
	      {
	      	var detiallength=selectdetail.rows.length*1;
	      	 for (var i=0;i<detiallength;i++)
			 {
			 		var row=selectdetail.getRow(0);
					selectdetail.deleteRow(row);
			} 
	      }
	      
         //查询默认号码
	    function selectDefaults(){
	    	try{
			   var params = "";
			   var myAjax = new parent.Ajax.Request(
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
	    
	      function addmissionMessage(request)
	   {
	    	    try
				{
	   	 			var responsetext = eval("(" + request.responseText + ")");
		        	var strMessage=responsetext.strMessage;
		        	
		        	if(checkNull(strMessage)=="" )
		        	{
		        		$.ligerDialog.success("添加成功!");
		        	}
		        	else
		        	{
		        		 $.ligerDialog.warn("添加失败！");
		        	}
	        	}
					catch(e)
				{
					alert(e.message);
				}
	   }
	      
	   function missionstopMessage(request){
	    	  try
				{
		        	var responsetext = eval("(" + request.responseText + ")");
		        	var strMessage=responsetext.strMessage;
		        	if(checkNull(strMessage)=="" )
		        	{
		        		$.ligerDialog.success("该任务已停用!");
		        	}
		        	else
		        	{
		        		 $.ligerDialog.warn("该任务停用失败！");
		        	}
	        	}
				catch(e)
				{
					alert(e.message);
				}
         }
	   
	    function addMessage(request){
	    	  try
				{
		        	var responsetext = eval("(" + request.responseText + ")");
		        	var strMessage=responsetext.strMessage;
		        	if(checkNull(strMessage)=="" )
		        	{	
		        		  $.ligerDialog.success("添加成功!");
		        	}
		        	else
		        	{
		        		 $.ligerDialog.warn("添加失败！");
		        	}
	        	}
				catch(e)
				{
					alert(e.message);
				}
         }
	    
	    //发送短信数字
	    function checkLength(which) {
		    var maxChars =300; //
		    if (which.value.length > maxChars)
		    {
		        alert("您出入的字数超多限制!");
		        // 超过限制的字数了就将 文本框中的内容按规定的字数 截取
		        which.value = which.value.substring(0,maxChars);
		        return false;
		    }
		    else
		    {
		         var curr = maxChars - which.value.length; //减去 当前输入的
		         document.getElementById("sy").innerHTML = curr.toString();
		         document.getElementById("writecount").innerHTML = which.value.length;
		         var totalCount=which.value.length*1.0/70;
		         if(totalCount*1.0%1>0)
		         {
		         	totalCount=totalCount+1;
		         }
		      
		         document.getElementById("infocount").innerHTML=maskAmt(maskAmt(totalCount,0)*1*maskAmt(selectdetail.options.total*1-1,0)*1,0)
		         return true;
		    }
		}
	   
	   
	//**************************************导出*****************************************
    function preview()
	{
	
		var printExcel=parent.document.getElementById("PrintControl");
		printExcel.PRINT_INIT(""); 
		loadExcelTable();
		printExcel.ADD_PRINT_TABLE(100,20,500,60,document.getElementById("excelscroll").innerHTML); 
		printExcel.SET_SAVE_MODE("PAGE_TYPE",10000); 
		printExcel.SET_SAVE_MODE("CenterHeader","页眉"); //Excel文件的页面设置
		printExcel.SET_SAVE_MODE("CenterFooter","第&P页"); //Excel文件的页面设置
		printExcel.SET_SAVE_MODE("Caption","我的标题栏");//Excel文件的页面设置					 
		printExcel.SET_SAVE_MODE("RETURN_FILE_NAME",1); 
		printExcel.SET_SAVE_MODE("QUICK_SAVE",true);
		printExcel.SAVE_TO_FILE("短信回复.xls");
		
	}
    
    
    function loadExcelTable()
    {
    	removeExcelTable();	
    	var result = new Array(3);
    	for (var rowid in revokedetails.records)
		{
    		var row =revokedetails.records[rowid];
    		result[0] = checkNull(row.revokphone);
	   	 	result[1] = checkNull(row.revokdetails);
	   	 	result[2] = checkNull(row.revokdate);
	   	 	addRow(result,"exceldate");
		}
    }

	 function removeExcelTable()
	 {
		 var tblPrjs = document.getElementById("exceldate");
		 while(tblPrjs.childNodes.length>1)
	     { 
		    tblPrjs.removeChild(tblPrjs.childNodes[1]);
	     }
	 }
	   //***************************************导出*****************************************
	    	 //---------------------------------读取Excel--------------------------------------------------
	   function fileUp()
		{
			if(document.getElementById("myFile").value=="")
			{
				return ;
			}
			try{
				excelDataJson="";
				var ccount=0;
	 			var filePath= document.getElementById("myFile").value;
	 			var oXL = new ActiveXObject("Excel.application"); 
	 			var oWB = oXL.Workbooks.open(filePath);
	 			oWB.worksheets(1).select(); 
	 			var oSheet = oWB.ActiveSheet;
	 			for(var i=2;i<1000;i++)
	  			{
	  				var  phone=checkNull(oSheet.Cells(i,2).value)==""?"":oSheet.Cells(i,2).value;
   					 if(phone=="null" || phone =="" )
	    				break;
	    			loadExcelData(checkNull(oSheet.Cells(i,1).value),checkNull(oSheet.Cells(i,2).value),checkNull(oSheet.Cells(i,3).value),checkNull(oSheet.Cells(i,4).value))
	   				ccount=ccount*1+1;
	 			 }
	 			
	 			 if(excelDataJson!="")
	 			 {
	 			 	excelDataJson=excelDataJson+"]";
	 			 	searchDate=JSON.parse(excelDataJson);
	 			 	selectdetail.options.data=$.extend(true, {},{Rows:JSON.parse(excelDataJson),Total:ccount });
	            	selectdetail.loadData(true);
	            	
	 			 }
	 			 else
	 			 {
	 				searchDate=null;
	 			 	selectdetail.options.data=$.extend(false, {},'')
					selectdetail.loadData(true)
					addphone();
	 			 }
	 			 oWB=null;
	 			 oXL.Quit();
	 			 CollectGarbage();
			 }catch(e){  alert(e.message);} 
		}	
		function loadExcelData(menberName,phone,memberNo,cardClass)
		{
			if(excelDataJson=="")
			{
				excelDataJson=excelDataJson+'[';
			}
			else
			{
				excelDataJson=excelDataJson+',';
			}
			excelDataJson=excelDataJson+'{"showmenberName": "'+menberName+'", "phone": "'+phone+'", "showphone": "'+phone+'", "showmemberNo":"'+memberNo+'", "cardClass": "'+cardClass+'"}';
		}
		
		 //---------------------------------读取Excel--------------------------------------------------    