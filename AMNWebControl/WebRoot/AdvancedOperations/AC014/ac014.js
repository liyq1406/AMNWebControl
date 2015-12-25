
   	var ac014layout=null;
   	var AC014Tab=null;
   	var commoninfodivTradeDate=null;
   	var commoninfodivFactOrderDate=null;
	var showDialogmanager=null;
	var departmentmanager=null;
	var yejichooseData = [{ choose: '1', text: '业务' }, { choose: '0', text: '非业务'}];
	var sexchooseData = [{ choose: '1', text: '男' }, { choose: '0', text: '女'}];
	var departBMZLData=JSON.parse(parent.loadCommonControlDate_select("BMZL",0));
	var postionGZGWData=JSON.parse(parent.loadCommonControlDate_select("GZGW",0));
	var curRecord=null;
	var lsProjectinfo=null;
	var StaffInfo=null;
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            ac014layout= $("#ac014layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
            $("#AC014Tab").ligerTab();
            AC014Tab = $("#AC014Tab").ligerGetTabManager();
           	var height = $(".l-layout-center").height();
           	var centerWidth = ac014layout.centerWidth
       		$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
            $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });	
            $("#orderfactdate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
           //--------销售条码卡
            commoninfodivTradeDate=$("#commoninfodivTradeDate").ligerGrid({
                columns: [
                { display: '门店编号', 		name: 'strCompId', 			width:60	,align: 'left' },
	            { display: '门店名称',   		name: 'strCompName',  		width:100	,align: 'left'},
	            { display: '手机号码',   		name: 'strMemberPhone',  			width:100	,align: 'left'},
	            { display: '预约时间',   		name: 'strOrderDate',  			width:140	,align: 'left'},
	            { display: '预约项目',   		name: 'strOrderProject',  		width:240	,align: 'left'},
	            { display: '预约美发人员',   	name: 'strMfEmpNo',  		width:130	,align: 'left'},
	            { display: '预约美容人员',   	name: 'strMrEmpNo',  		width:130	,align: 'left'},
	            { display: '预约烫染人员',   	name: 'strTrhEmpNo',  		width:130	,align: 'left'}
	            ],  pageSize:25, 
                data: null,      
                width: centerWidth,
                height:height-80,
                enabledEdit: false,  checkbox: false,rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                	curRecord=data;
                    loadSelecDetialData(data, rowindex, rowobj);
                } 
            });
            
             commoninfodivFactOrderDate=$("#commoninfodivFactOrderDate").ligerGrid({
                columns: [
                { display: '门店编号', 		name: 'strCompId', 				width:60	,align: 'left' },
	            { display: '门店名称',   		name: 'strCompName',  			width:100	,align: 'left'},
	            { display: '手机号码',   		name: 'strMemberPhone',  		width:100	,align: 'left'},
	            { display: '预约日期',   		name: 'orderfactdate',  		width:100	,align: 'left'},
	            { display: '预约时间',   		name: 'orderfacttime',  		width:80	,align: 'left'},
	            { display: '预约项目',   		name: 'orderfactproject',  		width:240	,align: 'left'},
	            { display: '预约工号',   		name: 'orderfactempid',  		width:80	,align: 'left'},
	            { display: '预约备注',   		name: 'orderdetail',  			width:240	,align: 'left'}
	            ],  pageSize:25, 
                data: null,      
                width: centerWidth,
                height:height-80,
                enabledEdit: false,  checkbox: false,rownumbers: false,usePager: false
            });
            
            document.getElementById("showTable").width=centerWidth;
            $("#searchButton").ligerButton(
	         {
	             text: '查询门店预约', width: 120,
		         click: function ()
		         {
		             loadDataSet();
		         }
	         });
	         $("#confirmOrder").ligerButton(
	         {
	             text: '核实预约信息', width: 120,
		         click: function ()
		         {
		             confirmOrder();
		         }
	     	});
	     	$("#confirmMsg").ligerButton(
	         {
	             text: '确认预约', width: 120,
		         click: function ()
		         {
		             confirmMsg();
		         }
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
            
            addOption("","",document.getElementById("orderfacttime"));
            for(var i=9;i<21;i++)
			{
				if(i<10)
				{
					addOption("0"+i+":00","0"+i+":00",document.getElementById("orderfacttime"));
				}
				else
				{
					addOption(i+":00",i+":00",document.getElementById("orderfacttime"));
				}
			}
			addOption("","",document.getElementById("projecttype"));
			addOption("300","美发项目",document.getElementById("projecttype"));
			addOption("400","美容项目",document.getElementById("projecttype"));
			addOption("600","烫染项目",document.getElementById("projecttype"));
			
   		}catch(e){alert(e.message);}
    });
    
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
     function  addFacttrade()
    {
        	var row = commoninfodivFactOrderDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivFactOrderDate.addRow({ 
				                shopId: "",
				                shopName: ""
				             
				            }, row, false);
     }
    //登记储值卡
    function loadDataSet()
    {	
   
    	var strCurCompId=getCurOrgFromSearchBar();

    	
    	var params = "strCurCompId="+strCurCompId;	
    	params=params+"&strFromDate="+document.getElementById("strFromDate").value;
    	params=params+"&strToDate="+document.getElementById("strToDate").value;
    	var requestUrl ="ac014/loadDataSet.action"; 
		var responseMethod="loadDataSetMessage";		
		 showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    
    }
    
    function loadDataSetMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
			if(responsetext.lsDataSet!=null && responsetext.lsDataSet.length>0)
           	{
            		commoninfodivTradeDate.options.data=$.extend(true, {},{Rows: responsetext.lsDataSet,Total: responsetext.lsDataSet.length});
            		commoninfodivTradeDate.loadData(true);
            		commoninfodivFactOrderDate.options.data=$.extend(true, {},{Rows: responsetext.lsFactDataSet,Total: responsetext.lsFactDataSet.length});
            		commoninfodivFactOrderDate.loadData(true);
            }
			else
			{
				
		   			commoninfodivTradeDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivTradeDate.loadData(true);
	            	addtrade(); 
	            	commoninfodivFactOrderDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivFactOrderDate.loadData(true);
	            	addFacttrade();
	            
			}
			document.getElementById("strMemberPhone").value="";
			document.getElementById("orderconfirmmsg").value="";
			document.getElementById("orderfactdate").value="";
			document.getElementById("orderfacttime").value="";
			document.getElementById("orderfactproject").value="";
			document.getElementById("orderfactempid").value="";
			document.getElementById("projecttype").value="";
			document.getElementById("orderdetail").value="";
			document.getElementById("strCompAddress").value="";
			showDialogmanager.close();
	}

	function loadSelecDetialData(data, rowindex, rowobj)
	{
		document.getElementById("ordersid").value=data.ordersid;
		document.getElementById("strMemberPhone").value=data.strMemberPhone;
		document.getElementById("strCompAddress").value=data.strCompAddress;
		document.getElementById("orderconfirmmsg").value="";
		document.getElementById("orderfactdate").value="";
		document.getElementById("orderfacttime").value="";
		document.getElementById("orderfactproject").value="";
		document.getElementById("orderfactempid").value="";
		document.getElementById("projecttype").value="";
		document.getElementById("orderdetail").value="";
		
		var params = "strCurCompId="+data.strCompId;	
    	var requestUrl ="ac014/loadCurCompInfo.action"; 
		var responseMethod="loadCurCompInfoMessage";	
		
		sendRequestForParams_p(requestUrl,responseMethod,params );
	}
	
	function loadCurCompInfoMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
			if(responsetext.lsProjectinfoDown!=null && responsetext.lsProjectinfoDown.length>0)
           	{
           		lsProjectinfo=responsetext.lsProjectinfoDown;
           		StaffInfo=responsetext.lsStaffinfoDown;
            }
            else
            {
            	lsProjectinfo=null;
            	StaffInfo=null;
            }
    }
	
	function validateProjectType(obj)
	{
		clearOption("orderfactproject");
		clearOption("orderfactempid");
		addOption("","",document.getElementById("orderfactproject"));
		addOption("","",document.getElementById("orderfactempid"));
       
		if(obj.value=="300" || obj.value=="600" && lsProjectinfo!=null)
		{
			for(var i=0;i<lsProjectinfo.length;i++)
			{
				if(lsProjectinfo[i].prjpricetype==1 && (lsProjectinfo[i].prjtype=="3" || lsProjectinfo[i].prjtype=="6" ))
				{
					addOption(lsProjectinfo[i].prjname,lsProjectinfo[i].id.prjno+"-"+lsProjectinfo[i].prjname,document.getElementById("orderfactproject"));
				}
			}
			if(obj.value=="300")
			{
				for(var i=0;i<StaffInfo.length;i++)
				{
					if(StaffInfo[i].department=="004" )
					{
						addOption(StaffInfo[i].bstaffno,StaffInfo[i].bstaffno+"-"+StaffInfo[i].staffname,document.getElementById("orderfactempid"));
					}
				}
			}
			if(obj.value=="600")
			{
				for(var i=0;i<StaffInfo.length;i++)
				{
					if(StaffInfo[i].department=="006" )
					{
						addOption(StaffInfo[i].bstaffno,StaffInfo[i].bstaffno+"-"+StaffInfo[i].staffname,document.getElementById("orderfactempid"));
					}
				}
			}
		}
		else if(obj.value=="400" && lsProjectinfo!=null)
		{
			 for(var i=0;i<lsProjectinfo.length;i++)
			{
				if(lsProjectinfo[i].prjpricetype==1 && lsProjectinfo[i].prjtype=="4")
				{
					addOption(lsProjectinfo[i].prjname,lsProjectinfo[i].id.prjno+"-"+lsProjectinfo[i].prjname,document.getElementById("orderfactproject"));
				}
			}
			for(var i=0;i<StaffInfo.length;i++)
			{
				if(StaffInfo[i].department=="003" )
				{
					addOption(StaffInfo[i].bstaffno,StaffInfo[i].bstaffno+"-"+StaffInfo[i].staffname,document.getElementById("orderfactempid"));
				}
			}
		}
	}
	
	function confirmOrder()
	{
		document.getElementById("orderconfirmmsg").value="";
		if(document.getElementById("strMemberPhone").value=="")
		{
			$.ligerDialog.error("请确认预约手机号!");
			return ;
		}
		if(document.getElementById("orderfactdate").value=="")
		{
			$.ligerDialog.error("请确认预约日期!");
			return ;
		}
		if(document.getElementById("orderfacttime").value=="")
		{
			$.ligerDialog.error("请确认预约时间!");
			return ;
		}
		if(document.getElementById("projecttype").value=="")
		{
			$.ligerDialog.error("请确认预约项目类型!");
			return ;
		}
		var toSendMsg="尊敬的会员:您预约已经生效," +document.getElementById("orderfactdate").value+" 日 "+document.getElementById("orderfacttime").value+" 时 "+curRecord.strCompName+"," ;
		/*if(document.getElementById("projecttype").value=="300")
		{
			toSendMsg=toSendMsg+"美发项目。";
		} 
		else if(document.getElementById("projecttype").value=="400")
		{
			toSendMsg=toSendMsg+"美容项目。";
		} 
		else if(document.getElementById("projecttype").value=="600")
		{
			toSendMsg=toSendMsg+"烫染项目。";
		} 
		if(document.getElementById("orderfactproject").value!="")
		{
			toSendMsg=toSendMsg+" 服务项目:"+document.getElementById("orderfactproject").value+"。";
		}*/
		if(document.getElementById("orderfactempid").value!="")
		{
			toSendMsg=toSendMsg+" 服务人员工号:"+document.getElementById("orderfactempid").value+",";
		}
		toSendMsg=toSendMsg+"门店地址:"+curRecord.strCompAddress+"。预约将保留15分钟,请您合理安排时间前往门店,谢谢您的支持!";
		document.getElementById("orderconfirmmsg").value=toSendMsg;
		
	}
	
	function confirmMsg()
	{
		if(document.getElementById("strMemberPhone").value=="")
		{
			$.ligerDialog.error("请确认预约手机号!");
			return ;
		}
		if(document.getElementById("orderfactdate").value=="")
		{
			$.ligerDialog.error("请确认预约日期!");
			return ;
		}
		if(document.getElementById("orderfacttime").value=="")
		{
			$.ligerDialog.error("请确认预约时间!");
			return ;
		}
		if(document.getElementById("projecttype").value=="")
		{
			$.ligerDialog.error("请确认预约项目类型!");
			return ;
		}
		var params = "strMemberPhone="+document.getElementById("strMemberPhone").value;	
    	params=params+"&orderfactdate="+document.getElementById("orderfactdate").value;
    	params=params+"&orderfacttime="+document.getElementById("orderfacttime").value;
    	params=params+"&orderfactproject="+document.getElementById("orderfactproject").value;
    	params=params+"&orderfactempid="+document.getElementById("orderfactempid").value;
    	params=params+"&orderconfirmmsg="+document.getElementById("orderconfirmmsg").value;
    	params=params+"&orderdetail="+document.getElementById("orderdetail").value;
    	params=params+"&ordersid="+document.getElementById("ordersid").value;
    	var requestUrl ="ac014/confirmMsg.action"; 
		var responseMethod="confirmMsgMessage";		
		 showDialogmanager = $.ligerDialog.waitting('正在确认中,请稍候...');		
		sendRequestForParams_p(requestUrl,responseMethod,params );
	}
	function confirmMsgMessage(request)
	{
		var responsetext = eval("(" + request.responseText + ")");
		var strMessage=responsetext.strMessage;
		if(strMessage=="")
		{
			$.ligerDialog.success("预约成功,请重新查询到已处理预约中核实!");
			commoninfodivTradeDate.deleteSelectedRow();
			document.getElementById("strMemberPhone").value="";
			document.getElementById("orderconfirmmsg").value="";
			document.getElementById("orderfactdate").value="";
			document.getElementById("orderfacttime").value="";
			document.getElementById("orderfactproject").value="";
			document.getElementById("orderfactempid").value="";
			document.getElementById("projecttype").value="";
			document.getElementById("orderdetail").value="";
			document.getElementById("strCompAddress").value="";
		}
		else
		{
			$.ligerDialog.error(strMessage);
		}
		showDialogmanager.close();
	}