	var AC015tab=null;
   	var ac015layout=null;
   	var commoninfodivTradeDate=null;
   	var commoninfodivStaffDate=null;
   	var commoninfodivMStaffDate=null;
	var showDialogmanager=null;
	var commoninfodivScheduling=null;		//班次明细
	var curRecord=null;
	var strCurStaffNo="";
	var strCurStaffName="";
	var strCurDate="";
	var upDialogmanager=null;
	var strOldFaceNo="";
	var chooseData = [{ choose: '0800', text: '8:00' },{ choose: '0900', text: '9:00' },{ choose: '1000', text: '10:00' },{ choose: '1100', text: '11:00' },{ choose: '1200', text: '12:00' },{ choose: '1300', text: '13:00' },{ choose: '1400', text: '14:00' },{ choose: '1500', text: '15:00' },{ choose: '1600', text: '16:00' },{ choose: '1700', text: '17:00' },{ choose: '1800', text: '18:00' },{ choose: '1900', text: '19:00' },{ choose: '2000', text: '20:00' },{ choose: '2100', text: '21:00' },{ choose: '2200', text: '22:00' },{ choose: '2300', text: '23:00' }];
   	$(function ()
   	{
	   try
	   {
	   	  	
	   		  //布局
            ac015layout= $("#ac015layout").ligerLayout({rightWidth: 400,   allowBottomResize: false, allowLeftResize: false,isRightCollapse:true });
           	var height = $(".l-layout-center").height();
           	var centerWidth = ac015layout.centerWidth
       		$("#AC015tab").ligerTab();
            AC015tab = $("#AC015tab").ligerGetTabManager();
          
           	$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
            $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
           //--------门店员工信息
           commoninfodivStaffDate=$("#commoninfodivStaffDate").ligerGrid({
                columns: [
                { display: '员工工号', 			name: 'bstaffno', 			width:80	,align: 'left' },
                { display: '员工名称', 			name: 'staffname', 			width:110	,align: 'left' },
                { display: '考勤ID号', 			name: 'fingerno', 		width:100	,align: 'left' }
	             
	            ],  pageSize:20, 
                data: null,      
                width: 335,
                height:height-100,
                enabledEdit: false,  checkbox: false,rownumbers: false,usePager: false,	          
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                } 
            });
            
            commoninfodivMStaffDate=$("#commoninfodivMStaffDate").ligerGrid({
                columns: [
               	    { display: '考勤ID号', 			name: 'name', 		width:120	,align: 'left' },
	            	{ display: '员工名称', 			name: 'id', 			width:110	,align: 'left' }
                
	            ],  pageSize:20, 
                data: null,      
                width: 335,
                height:height-100,
                enabledEdit: false,  checkbox: false,rownumbers: false,usePager: false,
                onDblClickRow : function (data, rowindex, rowobj)
                {
                	strOldFaceNo=data.id;
                	$.ligerDialog.prompt('请输入系统指纹号','', function (yes,value) { if(yes) modifyFaceInfo(value) });
                } 
            });
            
            commoninfodivTradeDate=$("#commoninfodivTradeDate").ligerGrid({
                columns: [
                	
                { display: '日期', 				name: 'workdate', 			width:80	,align: 'left' },
                { display: '星期', 				name: 'weekdate', 			width:60	,align: 'left' },
                { display: '门店编号', 			name: 'compno',  			width:60	,align: 'left'},
	            { display: '门店名称', 			name: 'compname', 	 		width:100	,align: 'left'},
	            { display: '员工编号',   			name: 'staffno',  			width:80	,align: 'left'},
	            { display: '员工名称',   			name: 'staffname',  		width:80	,align: 'left'},
	            { display: '上班时间',   			name: 'attime',  			width:80	,align: 'left'},
	            { display: '下班时间',   			name: 'pttime',  			width:80	,align: 'left'},
	            { display: '请假备注',   			name: 'leavemark',  		width:300	,align: 'left'}
	            ],  pageSize:20, 
                data: null,      
                width: centerWidth-400,
                height:height-70,
                enabledEdit: false,  checkbox:false,rownumbers: true,usePager: false,
                onDblClickRow : function (data, rowindex, rowobj)
                {
                	curRecord=data;
                    loadHandData(data, rowindex, rowobj);
                } 
               
            });
            
              commoninfodivScheduling = $("#commoninfodivScheduling").ligerGrid({
                columns:
                            [
                            { display: '班次编号', name: 'schedulno', 			width: 80,type:'text',editor: { type: 'text'}  },
                            { display: '班次名称', name: 'schedulname',			width: 140,type:'text',editor: { type: 'text' }  },
                            { display: '起始时间', name: 'fromtime', 				width: 80, 
			                	editor: { type: 'select', data: chooseData, valueField: 'choose' },
			                    render: function (item)
			                    {
			                        if (item.fromtime == '0800') return '8:00';
			                        if (item.fromtime == '0900') return '9:00';
			                        if (item.fromtime == '1000') return '10:00';
			                        if (item.fromtime == '1100') return '11:00';
			                        if (item.fromtime == '1200') return '12:00';
			                        if (item.fromtime == '1300') return '13:00';
			                        if (item.fromtime == '1400') return '14:00';
			                        if (item.fromtime == '1500') return '15:00';
			                        if (item.fromtime == '1600') return '16:00';
			                        if (item.fromtime == '1700') return '17:00';
			                        if (item.fromtime == '1800') return '18:00';
			                        if (item.fromtime == '1900') return '19:00';
			                        if (item.fromtime == '2000') return '20:00';
			                        if (item.fromtime == '2100') return '21:00';
			                        if (item.fromtime == '2200') return '22:00';
			                        if (item.fromtime == '2300') return '23:00';
			                        return '';
			                    } 
			                 },
                            { display: '结束时间', name: 'totime', 				width: 80 , 
			                	editor: { type: 'select', data: chooseData, valueField: 'choose' },
			                    render: function (item)
			                    {
			                        if (item.totime == '0800') return '8:00';
			                        if (item.totime == '0900') return '9:00';
			                        if (item.totime == '1000') return '10:00';
			                        if (item.totime == '1100') return '11:00';
			                        if (item.totime == '1200') return '12:00';
			                        if (item.totime == '1300') return '13:00';
			                        if (item.totime == '1400') return '14:00';
			                        if (item.totime == '1500') return '15:00';
			                        if (item.totime == '1600') return '16:00';
			                        if (item.totime == '1700') return '17:00';
			                        if (item.totime == '1800') return '18:00';
			                        if (item.totime == '1900') return '19:00';
			                        if (item.totime == '2000') return '20:00';
			                        if (item.totime == '2100') return '21:00';
			                        if (item.totime == '2200') return '22:00';
			                        if (item.totime == '2300') return '23:00';
			                        return '';
			                    }
			                 }
                            ], isScroll: false, showToggleColBtn: false, width: '390',enabledEdit: true, data: null ,usePager:false,
                toolbar: { items: [
                { text: '增加', click: itemclick_Scheduling, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
                { line: true },
                { text: '移除', click: itemclick_Scheduling, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' },
                { line: true },
                { text: '保存', click: itemclick_Scheduling, img: contextURL+'/common/ligerui/ligerUI/skins/icons/save.gif' }
                ]
                }	          
            }); 
            
            document.getElementById("showTable").width=centerWidth;
            $("#searchButton").ligerButton(
	         {
	             text: '查询考勤', width: 100,
		         click: function ()
		         {
		             loadDataSet();
		         }
	         });
	         $("#handButton2").ligerButton(
	         {
	             text: '下载考勤', width: 100,
		         click:  loadFingerDataSet
	         });
	         $("#handButton4").ligerButton(
	         {
	             text: '转Excel', width: 100,
		         click:  loadFingerExcel
	         });
	         $("#handButton3").ligerButton(
	         {
	             text: '获取考勤机信息', width: 100,
		         click:  loadFaceDataSet
	         });
	        var today = new Date();
			var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
			var intDay=today.getDate();
			var today = intYear.toString()+"-"+fullStr(intMonth.toString())+"-"+fullStr(intDay.toString());
			document.getElementById("strFromDate").value=today;
			document.getElementById("strToDate").value=today;
            $("#pageloading").hide(); 
            addtrade();
            addtradestaff();
            addtradeMstaff();
            addSchedulingRecord()
            //loadCompSchedulInfo();
   		}catch(e){alert(e.message);}
    });

	
    function loadCashTotal()
    {
    	  var total=0;
    	  for (var rowid in commoninfodivTradeDate.records)
		  {
				var row =commoninfodivTradeDate.records[rowid];
				if(checkNull(row.paycode)=="1" || checkNull(row.paycode)=="2"  || checkNull(row.paycode)=="6" || checkNull(row.paycode)=="14"  || checkNull(row.paycode)=="15" )		
			    {
			    	if(checkNull(row.actionid)==4 )
			    		continue;
			    	total=total*1+checkNull(row.staffyeji)*1;
			    }		
		  } 
		 return ForDight(total,1);  
    }
   	function  addtradestaff()
    {
        	var row = commoninfodivStaffDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivStaffDate.addRow({ 
				                bstaffno: "",
				                staffname: "",
				                manageno: ""
				             
				            }, row, false);
    }
    
    function  addtradeMstaff()
    {
        	var row = commoninfodivMStaffDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivMStaffDate.addRow({ 
				                id: "",
				                name: ""
				             
				            }, row, false);
    }
    
    
    function  addtrade()
    {
        	var row = commoninfodivTradeDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivTradeDate.addRow({ 
				                shopId: "",
				                shopName: "",
				                dateReport: ""
				             
				            }, row, false);
    }
    //登记储值卡
    function loadDataSet()
    {	
   
    	var strCurCompId=getCurOrgFromSearchBar();

    	var strFromDate=document.getElementById("strFromDate").value;
    	var strToDate=document.getElementById("strToDate").value;
    	
    	var params = "strCurCompId="+strCurCompId;			
    	params=params+"&strFromDate="+strFromDate;	
    	params=params+"&strToDate="+strToDate;	
     	var requestUrl ="ac015/loadStaffDataSet.action"; 
		var responseMethod="loadStaffDataSetMessage";		
		 showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    
    }
    
    function loadStaffDataSetMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
			if(responsetext.lsCompscheduling!=null && responsetext.lsCompscheduling.length>0)
			{
				var curSchedulingdate={Rows: responsetext.lsCompscheduling,Total: responsetext.lsCompscheduling.length};	
				commoninfodivScheduling.options.data=$.extend(true, {},curSchedulingdate);
            	commoninfodivScheduling.loadData(true);	
            }	
            else
            {
            	commoninfodivScheduling.options.data=$.extend(true, {},{Rows:null,Total: 0});
            	commoninfodivScheduling.loadData(true);	
            	addSchedulingRecord();
            }	
			if(responsetext.lsStaffDataSet!=null && responsetext.lsStaffDataSet.length>0)
           	{
            		commoninfodivStaffDate.options.data=$.extend(true, {},{Rows: responsetext.lsStaffDataSet,Total: responsetext.lsStaffDataSet.length});
            		commoninfodivStaffDate.loadData(true);
            }
			else
			{
				 	$.ligerDialog.warn("没有查到员工信息!");
		   			commoninfodivStaffDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivStaffDate.loadData(true);
	            	addtradestaff(); 
			}
			showDialogmanager.close();
	}
	
	
	 //登记储值卡
    function loadFaceDataSet()
    {	
   
    	var strCurCompId=getCurOrgFromSearchBar();
    	var params = "strCurCompId="+strCurCompId;			
     	var requestUrl ="ac015/loadFaceDataSet.action"; 
		var responseMethod="loadFaceDataSetMessage";		
		showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    
    }
    
    function loadFaceDataSetMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
			if(responsetext.lsFacesInfo!=null && responsetext.lsFacesInfo.length>0)
           	{
            		commoninfodivMStaffDate.options.data=$.extend(true, {},{Rows: responsetext.lsFacesInfo,Total: responsetext.lsFacesInfo.length});
            		commoninfodivMStaffDate.loadData(true);
            }
			else
			{
				 	commoninfodivMStaffDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivMStaffDate.loadData(true);
	            	addtradeMstaff(); 
			}
			showDialogmanager.close();
	}
	
    
	function loadHandData(data, rowindex, rowobj)
	{
		strCurDate=data.workdate;
		if(document.getElementById("shoplevel").value=="4" && checkNull(data.leavemark)!="" )
		{
			$.ligerDialog.error("当天请假已通过经理审核,不能再次修改!");
			return;
		}
		upDialogmanager=$.ligerDialog.open({ height: null, url: contextURL+'/AdvancedOperations/AC015/handleave.jsp', width: 400, showMax: false, showToggle: false, showMin: false, isResize: false, title: '员工请假审核' });
	}
	function loadSelecDetialData(data, rowindex, rowobj)
    {
    	strCurStaffNo=data.bstaffno;
    	strCurStaffName=data.staffname;
    	var strCurCompId=getCurOrgFromSearchBar();
    	var strFromDate=document.getElementById("strFromDate").value;
    	var strToDate=document.getElementById("strToDate").value;
    	var params = "strCurCompId="+strCurCompId;			
    	params=params+"&strFromDate="+strFromDate;	
    	params=params+"&strToDate="+strToDate;		
    	params=params+"&fingerid="+data.fingerno;	
    	showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
     	var requestUrl ="ac015/loadDataSet.action"; 
		var responseMethod="loadDataSetMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
    	
    }
    
    
    function loadDataSetMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
			if(responsetext.lsDateSet!=null && responsetext.lsDateSet.length>0)
           	{
            		commoninfodivTradeDate.options.data=$.extend(true, {},{Rows: responsetext.lsDateSet,Total: responsetext.lsDateSet.length});
            		commoninfodivTradeDate.loadData(true);
            		document.getElementById("shoplevel").value=checkNull(responsetext.shoplevel);
            		
            }
			else
			{
				 	$.ligerDialog.warn("没有查到考勤信息!");
		   			commoninfodivTradeDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivTradeDate.loadData(true);
	            	addtrade(); 
			}
			showDialogmanager.close();
	}
	
	 function loadFingerDataSet()
	 {
	 	var strCurCompId=getCurOrgFromSearchBar();
    	var strFromDate=document.getElementById("strFromDate").value;
    	var strToDate=document.getElementById("strToDate").value;
	 	$.ligerDialog.confirm('确认下载 '+strCurCompId+' 店 '+strFromDate+'至'+strToDate+'之间的考勤记录', function (result)
		{
		    if( result==true)
           	{
				var params = "strCurCompId="+strCurCompId;			
    			params=params+"&strFromDate="+strFromDate;	
    			params=params+"&strToDate="+strToDate;	
     			var requestUrl ="ac015/loadFingerDataSet.action"; 
				var responseMethod="loadFingerDataSetMessage";	
				showDialogmanager = $.ligerDialog.waitting('正在下载中,请稍候...');		
				sendRequestForParams_p(requestUrl,responseMethod,params);
			}
		});  
    }
    function loadFingerDataSetMessage(request)
    {
    		
        	var responsetext = eval("(" + request.responseText + ")");
    		if(checkNull(responsetext.strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("下载成功!");
	        }
	        else
	        {
	        	$.ligerDialog.error(responsetext.strMessage);
	        }
        	showDialogmanager.close();
    }
    
    function backupFingerDataSet()
    {
    	var strCurCompId=getCurOrgFromSearchBar();
    	$.ligerDialog.confirm('确认备份 '+strCurCompId+' 店人脸数据 ', function (result)
		{
		    if( result==true)
           	{
				var params = "strCurCompId="+strCurCompId;	
     			var requestUrl ="ac015/backupFingerDataSet.action"; 
				var responseMethod="backupFingerDataSetMessage";	
				showDialogmanager = $.ligerDialog.waitting('正在下载中,请稍候...');		
				sendRequestForParams_p(requestUrl,responseMethod,params);
			}
		});  
    }
    	//新增
    function addSchedulingRecord()
    {
       	try
		{
	        
	        	var row = commoninfodivScheduling.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
				   commoninfodivScheduling.addRow({ 
				                schedulno: "",
				                schedulname: "",
				                fromtime: "",
				                totime: ""        
				            }, row, false);
        }
		catch(e)
		{
			alert(e.message);
		}
	} 
    function backupFingerDataSetMessage(request)
    {
    		
        	var responsetext = eval("(" + request.responseText + ")");
    		if(checkNull(responsetext.strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("备份成功!");
	        }
	        else
	        {
	        	$.ligerDialog.error(responsetext.strMessage);
	        }
        	showDialogmanager.close();
    }

   function itemclick_Scheduling(item)
   {
       if(item.text=="增加")
       {
        	 	addSchedulingRecord();
       }
       else if(item.text=="保存")
       {
       			var strCurCompId=getCurOrgFromSearchBar();
        	 	var data = commoninfodivScheduling.getData();
            	var strJsonSchedulParam=JSON.stringify(data);
            	var requestUrl ="ac015/postCompSchedulInfo.action";
				var params="strJsonParam="+strJsonSchedulParam;
				params=params+"&strCurCompId="+strCurCompId;
				var responseMethod="postCompSchedulInfoMessage";				
				sendRequestForParams_p(requestUrl,responseMethod,params ); 
       } 
       else if(item.text=="移除")
       {
        	 	commoninfodivScheduling.deleteSelectedRow();
       } 
      		 
   	}
   	
   	  function postCompSchedulInfoMessage(request)
      {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		  $.ligerDialog.success("保存成功!");
	        	}
	        	else
	        	{
	        		$.ligerDialog.error(strMessage);
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
       }
       
          
     function handStaffLeaveMessage(request)
	 {
	 			var responsetext = eval("(" + request.responseText + ")");
		        var strMessage=responsetext.strMessage;
		         if(checkNull(strMessage)=="")
		        {	        		 
		        	 $.ligerDialog.success("处理请假成功!");
		        	 commoninfodivTradeDate.updateRow(curRecord,{leavemark:checkNull(responsetext.leavemark)}); 
		        	 upDialogmanager.close();
		        }
		        else
		        {
		        	$.ligerDialog.error(strMessage);
		        }
	}
	
	
	
	function loadFingerExcel()
	{
		var strCurCompId=getCurOrgFromSearchBar();
		var strFromDate=document.getElementById("strFromDate").value;
    	var strToDate=document.getElementById("strToDate").value;
    	var params = "strCurCompId="+strCurCompId;			
    	params=params+"&strFromDate="+strFromDate;	
    	params=params+"&strToDate="+strToDate;	
		var excelDialog=$.ligerDialog.open({url: contextURL+'/ac015/loadac015Excel.action?'+params });
    	setTimeout(function () { excelDialog.close(); }, 10000);
	}
	
	function modifyFaceInfo(strNewFaceNo)
	{
		var strCurCompId=getCurOrgFromSearchBar();
		if(strNewFaceNo=="")
		{
			$.ligerDialog.error("请输入新的人脸ID号");
			return ;
		}
		if(strNewFaceNo==strOldFaceNo)
		{
			$.ligerDialog.error("新的人脸ID号与原人脸ID号一致,无需更新");
			return ;
		}
		$.ligerDialog.confirm('是否确认将'+strCurCompId+"门店的人脸号由"+strOldFaceNo+'改为'+strNewFaceNo, function (result)
		{
			if( result==true)
			{
				var params = "strCurCompId="+strCurCompId;	
				params=params+"&strOldFaceNo="+strOldFaceNo;
				params=params+"&strNewFaceNo="+strNewFaceNo;
     			var requestUrl ="ac015/modifyFaceInfo.action"; 
				var responseMethod="modifyFaceInfoMessage";	
				showDialogmanager = $.ligerDialog.waitting('正在更新中,请稍候...');		
				sendRequestForParams_p(requestUrl,responseMethod,params);
			}
		}); 
	}
	
	 function modifyFaceInfoMessage(request)
	 {
	 	var responsetext = eval("(" + request.responseText + ")");
		var strMessage=responsetext.strMessage;
		if(checkNull(strMessage)=="")
		{	        		 
		  	$.ligerDialog.success("修改成功,请查询此人历史考勤信息!");
		}
		else
		{
		 	$.ligerDialog.error(strMessage);
		}
		showDialogmanager.close();
	}