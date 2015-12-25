	var AC019tab=null;
   	var ac019layout=null;
   	var compTree=null;
   	var commoninfodivCompMachine=null;
   	var commoninfodivMStaffDate=null;
	var showDialogmanager=null;
	var addBandDialog=null;		//班次明细
	var curRecord=null;
	var strCurCompId="";
	var strCurStaffNo="";
	var strCurStaffName="";
	var strCurDate="";
	var upDialogmanager=null;
	var strOldFaceNo="";
	var machineVersionType=JSON.parse(parent.loadCommonControlDate_select("KQBB",0));
	var strEntryCompId="";
	var strEntryMachineId="";
	var strEntrySeqno="";
	$(function ()
   	{
	   try
	   {
	   	  	
	   		  //布局
            ac019layout= $("#ac019layout").ligerLayout({leftWidth: 270,rightWidth: 400,   allowBottomResize: false, allowLeftResize: false,isRightCollapse:true });
           	var height = $(".l-layout-center").height();
           	var centerWidth = ac019layout.centerWidth
       		$("#AC019tab").ligerTab();
            AC019tab = $("#AC019tab").ligerGetTabManager();
          
           	compTree=$("#companyTree").ligerTree(
        	{
           		data: parent.complinkInfo,
           		idFieldName :'id',           		
           		onSelect: compSelect,
           		nodeWidth: 140,
           		slide: false,
           		checkbox: false,
           		height:'100%'
          	});
          	compTreeManager = $("#companyTree").ligerGetTreeManager();
          	
          	commoninfodivCompMachine=$("#commoninfodivCompMachine").ligerGrid({
                columns: [
               	    { display: '门店编号', 			name: 'compno', 				width:60	,align: 'left' },
	            	{ display: '门店名称', 			name: 'compname', 				width:100	,align: 'left' },
	            	{ display: '门店IP', 			name: 'machineip', 				width:100	,align: 'left' },
	            	{ display: '考勤机序号', 			name: 'machineno', 				width:150	,align: 'left' },
	            	{ display: '版本', 				name: 'machineversion', 		width:120	,align: 'left',
                	editor: { type: 'select', data: machineVersionType, valueField: 'choose',selectBoxHeight:'220'},
	            	render: function (item)
	              	{
	              		var lsKQBB=parent.gainCommonInfoByCode("KQBB",0);
	              		for(var i=0;i<lsKQBB.length;i++)
						{
							if(lsKQBB[i].bparentcodekey==item.machineversion)
							{	
								return lsKQBB[i].parentcodevalue;								
						    }
						}
	                    return '';
	                }  },
                	{ display: '开始日期', 			name: 'createfromdate', 		width:90	,align: 'left' },
                	{ display: '结束日期', 			name: 'createtodate', 			width:90	,align: 'left' },
                	{ name: 'createseqno', 			width:1	,hide: true }
	            ],  pageSize:20, 
                data: null,      
                width: 750,
                height:height-60,
                enabledEdit: false,  checkbox: false,rownumbers: false,usePager: false ,
                toolbar: { items: [
                { text: '添加考勤机', click: addMachineInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/communication.gif' }
                ]
                },
                onContextmenu : function (parm,e)
                {
                	menu.show({ top: e.pageY, left: e.pageX });
                    return false;
                },
                onSelectRow : function (data, rowindex, rowobj)
                {
                  	strEntryCompId=data.compno;
					strEntryMachineId=data.machineno;
					strEntrySeqno=data.createseqno;
                } 	
            });
            commoninfodivMStaffDate=$("#commoninfodivMStaffDate").ligerGrid({
                columns: [
               	    { display: '考勤ID号', 			name: 'name', 		width:120	,align: 'left' },
	            	{ display: '员工名称', 			name: 'id', 			width:110	,align: 'left' }
                
	            ],  pageSize:20, 
                data: null,      
                width: 260,
                height:height-60,
                enabledEdit: false,  checkbox: false,rownumbers: false,usePager: false,
                onDblClickRow : function (data, rowindex, rowobj)
                {
                	strOldFaceNo=data.id;
                	$.ligerDialog.prompt('请输入系统指纹号','', function (yes,value) { if(yes) modifyFaceInfo(value) });
                },
                toolbar: { items: [
                { text: '获取考勤机信息', click: loadFaceDataSet, img: contextURL+'/common/ligerui/ligerUI/skins/icons/down.gif' }
                ]
                }	 
            });
	        menu = $.ligerMenu({ width: 120, items:
	            [
	            	{ text: '删除当前信息', click: deleteMachineInfo, icon: 'delete' , img: contextURL+'/common/ligerui/ligerUI/skins/icons/candle.gif'}
	            ]
	            }); 
            $("#pageloading").hide(); 
            addtradeMstaff();
            addtradeCompMachine();
   		}catch(e){alert(e.message);}
    });

	//加载门店信息
	function compSelect(note)
    {
        try{
        	strCurCompId=note.data.id;
        	var params = "strCurCompId="+strCurCompId;				
       		var requestUrl ="ac019/loadMachineinfo.action"; 
			var responseMethod="loadMachineinfoMessage";				
			sendRequestForParams_p(requestUrl,responseMethod,params );
          }catch(e){alert(e.message);}
    }
    function loadMachineinfoMessage(request)
    {
       	var responsetext = eval("(" + request.responseText + ")");
	    if(responsetext.lsMachineDataSet!=null && responsetext.lsMachineDataSet.length>0)
		{
		   	commoninfodivCompMachine.options.data=$.extend(true, {},{Rows: responsetext.lsMachineDataSet,Total: responsetext.lsMachineDataSet.length});
	        commoninfodivCompMachine.loadData(true);            	
		}
		else
		{
			commoninfodivCompMachine.options.data=$.extend(true, {},{Rows: null,Total: 0});
	        commoninfodivCompMachine.loadData(true);
	        addtradeCompMachine();
		}
	}
				
    function  addtradeCompMachine()
    {
        	var row = commoninfodivCompMachine.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivCompMachine.addRow({ 
				                compno: "",
				                compname: "",
				             	machineno: "",
				             	machineversion: "",
				             	createfromdate: "",
				             	createtodate:""
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
     			var requestUrl ="ac019/modifyFaceInfo.action"; 
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
	
	
	function handBandCompMachineMessage(request)
	{
	 	var responsetext = eval("(" + request.responseText + ")");
		var strMessage=responsetext.strMessage;
		if(checkNull(strMessage)=="")
		{	        		 
		  	$.ligerDialog.success("新增成功!!");
		}
		else
		{
		 	$.ligerDialog.error(strMessage);
		}
	}
	
		
	 //登记储值卡
    function loadFaceDataSet()
    {	
   		var params = "strCurCompId="+strCurCompId;			
     	var requestUrl ="ac019/loadFaceDataSet.action"; 
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
	
	function addMachineInfo()
	{
		addBandDialog=$.ligerDialog.open({ height: 400, url: contextURL+'/AdvancedOperations/AC019/handBandCompMachine.jsp', width: 300, showMax: false, showToggle: false, showMin: false, isResize: false, title: '新增考勤社保' });
	}
	
	
	function deleteMachineInfo()
	{
		if(strEntryMachineId=="")
		{
			$.ligerDialog.error("请选中需要删除的信息!");
			return ;
		}
		var params = "strEntryCompId="+strEntryCompId;	
		params=params+"&strEntryMachineId="+strEntryMachineId;	
		params=params+"&strEntrySeqno="+strEntrySeqno;	
     	var requestUrl ="ac019/deleteMachineInfo.action"; 
		var responseMethod="deleteMachineInfoMessage";		
		showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');
		$.ligerDialog.confirm('确认删除选中信息?', function (result)
		{
		    if( result==true)
           	{	
				sendRequestForParams_p(requestUrl,responseMethod,params );
			}
		});
	}
	function deleteMachineInfoMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		commoninfodivCompMachine.deleteSelectedRow();
	       	}
	       	else
	       	{
	       		$.ligerDialog.error(strMessage);
	       	}
	       	showDialogmanager.close();
        }
		catch(e)
		{
			alert(e.message);
		}
   	}