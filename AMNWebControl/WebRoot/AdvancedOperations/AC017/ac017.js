
   	var ac017layout=null;
   	var commoninfodivTradeDate=null;
	var showDialogmanager=null;
	var postionGZGWData=JSON.parse(parent.loadCommonControlDate_select("GZGW",0));
	var lsStaffabsenceinfo=null;
	var curRecord=null;
	var disabsenceStaffdiv=null;
	var commoninfodivDetial=null;
	var strCurDate="";
	var curCompId="";
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            ac017layout= $("#ac017layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false,isRightCollapse:true });
           	var height = $(".l-layout-center").height();
           	var centerWidth = ac017layout.centerWidth
       		$("#strFromDate").ligerDateEditor({ labelWidth: 100,format: "yyyy-MM", labelAlign: 'right',width:'100' });
           //--------销售条码卡
            commoninfodivTradeDate=$("#commoninfodivTradeDate").ligerGrid({
                columns: [
                { display: '门店编号', 				name: 'strCompId', 				width:70	, 	align: 'left' },
                { display: '门店名称', 				name: 'strCompName', 			width:100	, 	align: 'left' },
	            { display: '在职有社保总额', 			name: 'sumHasSocialSaraly',  	width:100	,	align: 'right',
	            	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,0);
                        },
						align: 'right'
                    }},
	            { display: '在职无社保总额', 			name: 'sumNoSocialSaraly',  	width:100	,	align: 'right',
	            totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,0);
                        },
						align: 'right'
                    }},
	            { display: '待发工资总额', 			name: 'sumReadSalary',  		width:100	,	align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,0);
                        },
						align: 'right'
                    }},
	            { display: '离职工资总额', 			name: 'sumLeavelSalary',  		width:100	,	align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,0);
                        },
						align: 'right'
                    }
                }
	            ],  pageSize:20, 
                data: null,      
                width: 640,
                height:height-52,
                enabledEdit: true,  checkbox: false,rownumbers: true,usePager: false
            });
            
          	commoninfodivDetial=$("#commoninfodivDetial").ligerGrid({
                columns: [
                { display: '工号', 					name: 'staffno',  					width:80	,	align: 'left'},
	            { display: '姓名', 					name: 'staffname',  				width:90	,	align: 'left'},
	            { display: '职位', 					name: 'staffpositionname',  		width:100	,	align: 'left'},
	            { display: '社保类型', 				name: 'staffsocialsource',  		width:80	,	align: 'left'},
	            { display: '身份证号', 				name: 'staffpcid',  				width:140	,	align: 'left'},
	            { display: '银行卡号', 				name: 'staffbankaccountno',  		width:140	,	align: 'left'},
	            { display: '银行名称', 				name: 'strBackName',  				width:140	,	align: 'left'},
	            { display: '工资', 					name: 'factpaysalary',  			width:60	,	align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,0);
                        },
						align: 'right'
                    }}
	            ],  pageSize:20, 
                data: null,      
                width: centerWidth/2+191,
                height:height-100,
                enabledEdit: true,  checkbox: false,rownumbers: true,usePager: false
            });
            
   			 $("#toptoolbardetialA").ligerToolBar({ items: [
      		     { text: '在职有卡有社保<label id="lbdyAmt"></label>', 	click: loadDetialInfoA1, img: contextURL+'/common/ligerui/ligerUI/skins/icons/attibutes.gif'  },
	             { text: '在职有卡有社保(补)<label id="lbdyAmt"></label>', click: loadDetialInfoA2, img: contextURL+'/common/ligerui/ligerUI/skins/icons/attibutes.gif'  },
	             { text: '在职有卡无社保<label id="lbdyAmt"></label>', 	click: loadDetialInfoA3, img: contextURL+'/common/ligerui/ligerUI/skins/icons/attibutes.gif'  },
	             { text: '在职无卡<label id="lbdyAmt"></label>', 			click: loadDetialInfoA4, img: contextURL+'/common/ligerui/ligerUI/skins/icons/attibutes.gif'  }
	              	 	
            ]
            });
	         $("#toptoolbardetialB").ligerToolBar({ items: [
      		     { text: '离职有卡有社保<label id="lbdyAmt"></label>', click: loadDetialInfoA5, img: contextURL+'/common/ligerui/ligerUI/skins/icons/attibutes.gif'  },
	             { text: '离职有卡有社保(补)<label id="lbdyAmt"></label>', click: loadDetialInfoA6, img: contextURL+'/common/ligerui/ligerUI/skins/icons/attibutes.gif'  },
	             { text: '离职有卡无社保<label id="lbdyAmt"></label>', click: loadDetialInfoA7 ,img: contextURL+'/common/ligerui/ligerUI/skins/icons/attibutes.gif'  },
	             { text: '离职无卡<label id="lbdyAmt"></label>', click: loadDetialInfoA8, img: contextURL+'/common/ligerui/ligerUI/skins/icons/attibutes.gif'  },
	             { text: '待发<label id="lbdyAmt"></label>', click: loadDetialInfoA9, img: contextURL+'/common/ligerui/ligerUI/skins/icons/attibutes.gif'  },
	             { text: '转Excel<label id="lbdyAmt"></label>', click: loadDetialInfoExcel, img: contextURL+'/common/ligerui/ligerUI/skins/icons/attibutes.gif'  }
	                	 	
            ]
            });   
            document.getElementById("showTable").width=centerWidth;
            $("#searchButton").ligerButton(
	         {
	             text: '查询', width: 60,
		         click: function ()
		         {
		             loadDataSet();
		         }
	         });
	         $("#excelButtonA").ligerButton(
	         {
	             text: '工资汇总转Excel(核对)', width: 160,
		         click: function ()
		         {
		             loadExcelDataSet();
		         }
	         });
	         $("#excelButtonB").ligerButton(
	         {
	             text: '工资汇总转Excel(所有)', width: 160,
		         click: function ()
		         {
		             loadExcelDataSetT();
		         }
	         });
	        var today = new Date();
			var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
			var intDay=today.getDate();
			var today = intYear.toString()+"-"+fullStr(intMonth.toString());
			document.getElementById("strFromDate").value=today;
            $("#pageloading").hide(); 
            addtrade();
            adddetail();
   		}catch(e){alert(e.message);}
    });
    
    
    	function  addtrade()
        {
        	var row = commoninfodivDetial.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivDetial.addRow({ 
				                shopId: "",
				                shopName: "",
				                dateReport: ""
				             
				            }, row, false);
        }
        
         function  adddetail()
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
        
        
        function loadExcelDataSet()
        {
        	var strCurCompId=getCurOrgFromSearchBar();
    		var strFromDate=document.getElementById("strFromDate").value;
    		var params = "strCurCompId="+strCurCompId;		
    		params=params+"&strFromDate="+strFromDate;
			var excelDialog=$.ligerDialog.open({url: contextURL+'/ac017/loadTExcel.action?'+params });
    		setTimeout(function () { excelDialog.close(); }, 20000);
        }
        
        function loadExcelDataSetT()
        {
        	var strCurCompId=getCurOrgFromSearchBar();
    		var strFromDate=document.getElementById("strFromDate").value;
    		var params = "strCurCompId="+strCurCompId;		
    		params=params+"&strFromDate="+strFromDate;
			var excelDialog=$.ligerDialog.open({url: contextURL+'/ac017/loadTBExcel.action?'+params });
    		setTimeout(function () { excelDialog.close(); }, 20000);
        }
        
	    function loadDataSet()
	    {	
	    	var strCurCompId=getCurOrgFromSearchBar();
	    	var strFromDate=document.getElementById("strFromDate").value;
	    	var params = "strCurCompId="+strCurCompId;		
	    	params=params+"&strFromDate="+strFromDate;	
	     	var requestUrl ="ac017/loadDataSet.action"; 
			var responseMethod="loadDataSetMessage";		
			 showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
			sendRequestForParams_p(requestUrl,responseMethod,params );
	    
	    }
    
    	function loadDataSetMessage(request)
		{
			var responsetext = eval("(" + request.responseText + ")");
			var strMessage=responsetext.strMessage;
			if(strMessage!="")
			{
				$.ligerDialog.error(strMessage);
				showDialogmanager.close();
				return ;
			}
			if(responsetext.lsDataSet!=null && responsetext.lsDataSet.length>0)
           	{
            		commoninfodivTradeDate.options.data=$.extend(true, {},{Rows: responsetext.lsDataSet,Total: responsetext.lsDataSet.length});
            		commoninfodivTradeDate.loadData(true);
            }
			else
			{
				 	$.ligerDialog.warn("没有查到工资信息!");
		   			commoninfodivTradeDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivTradeDate.loadData(true);
	            	addtrade(); 
			}
			commoninfodivTradeDate.sortedData=false;
			showDialogmanager.close();
		}
		
		
		function loadDetialInfo()
		{
			alert(curCompId);
		}
		
		function loadDetialInfoA1()
		{
			var strCurCompId=getCurOrgFromSearchBar();
    		var strFromDate=document.getElementById("strFromDate").value;
    		var params = "strCurCompId="+strCurCompId;		
    		params=params+"&strFromDate="+strFromDate;	
    		params=params+"&detialtype=1";	
     		var requestUrl ="ac017/loadDetialDataSet.action"; 
			var responseMethod="loadDetialDataSetMessage";		
			showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
			sendRequestForParams_p(requestUrl,responseMethod,params );
		}
		
		function loadDetialInfoA2()
		{
			var strCurCompId=getCurOrgFromSearchBar();
    		var strFromDate=document.getElementById("strFromDate").value;
    		var params = "strCurCompId="+strCurCompId;		
    		params=params+"&strFromDate="+strFromDate;	
    		params=params+"&detialtype=2";	
     		var requestUrl ="ac017/loadDetialDataSet.action"; 
			var responseMethod="loadDetialDataSetMessage";		
			showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
			sendRequestForParams_p(requestUrl,responseMethod,params );
		}
		
		function loadDetialInfoA3()
		{
			var strCurCompId=getCurOrgFromSearchBar();
    		var strFromDate=document.getElementById("strFromDate").value;
    		var params = "strCurCompId="+strCurCompId;		
    		params=params+"&strFromDate="+strFromDate;	
    		params=params+"&detialtype=3";	
     		var requestUrl ="ac017/loadDetialDataSet.action"; 
			var responseMethod="loadDetialDataSetMessage";		
			showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
			sendRequestForParams_p(requestUrl,responseMethod,params );
		}
		
		function loadDetialInfoA4()
		{
			var strCurCompId=getCurOrgFromSearchBar();
    		var strFromDate=document.getElementById("strFromDate").value;
    		var params = "strCurCompId="+strCurCompId;		
    		params=params+"&strFromDate="+strFromDate;	
    		params=params+"&detialtype=4";	
     		var requestUrl ="ac017/loadDetialDataSet.action"; 
			var responseMethod="loadDetialDataSetMessage";		
			showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
			sendRequestForParams_p(requestUrl,responseMethod,params );
		}
		
		function loadDetialInfoA5()
		{
			var strCurCompId=getCurOrgFromSearchBar();
    		var strFromDate=document.getElementById("strFromDate").value;
    		var params = "strCurCompId="+strCurCompId;		
    		params=params+"&strFromDate="+strFromDate;	
    		params=params+"&detialtype=5";	
     		var requestUrl ="ac017/loadDetialDataSet.action"; 
			var responseMethod="loadDetialDataSetMessage";		
			showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
			sendRequestForParams_p(requestUrl,responseMethod,params );
		}
		
		function loadDetialInfoA6()
		{
			var strCurCompId=getCurOrgFromSearchBar();
    		var strFromDate=document.getElementById("strFromDate").value;
    		var params = "strCurCompId="+strCurCompId;		
    		params=params+"&strFromDate="+strFromDate;	
    		params=params+"&detialtype=6";	
     		var requestUrl ="ac017/loadDetialDataSet.action"; 
			var responseMethod="loadDetialDataSetMessage";		
			showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
			sendRequestForParams_p(requestUrl,responseMethod,params );
		}
		
		function loadDetialInfoA7()
		{
			var strCurCompId=getCurOrgFromSearchBar();
    		var strFromDate=document.getElementById("strFromDate").value;
    		var params = "strCurCompId="+strCurCompId;		
    		params=params+"&strFromDate="+strFromDate;	
    		params=params+"&detialtype=7";	
     		var requestUrl ="ac017/loadDetialDataSet.action"; 
			var responseMethod="loadDetialDataSetMessage";		
			showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
			sendRequestForParams_p(requestUrl,responseMethod,params );
		}
		
		function loadDetialInfoA8()
		{
			var strCurCompId=getCurOrgFromSearchBar();
    		var strFromDate=document.getElementById("strFromDate").value;
    		var params = "strCurCompId="+strCurCompId;		
    		params=params+"&strFromDate="+strFromDate;	
    		params=params+"&detialtype=8";	
     		var requestUrl ="ac017/loadDetialDataSet.action"; 
			var responseMethod="loadDetialDataSetMessage";		
			showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
			sendRequestForParams_p(requestUrl,responseMethod,params );
		}
		
		function loadDetialInfoA9()
		{
			var strCurCompId=getCurOrgFromSearchBar();
    		var strFromDate=document.getElementById("strFromDate").value;
    		var params = "strCurCompId="+strCurCompId;		
    		params=params+"&strFromDate="+strFromDate;	
    		params=params+"&detialtype=9";	
     		var requestUrl ="ac017/loadDetialDataSet.action"; 
			var responseMethod="loadDetialDataSetMessage";		
			showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
			sendRequestForParams_p(requestUrl,responseMethod,params );
		}
		
		function loadDetialDataSetMessage(request)
		{
			var responsetext = eval("(" + request.responseText + ")");
			var strMessage=responsetext.strMessage;
			if(strMessage!="")
			{
				$.ligerDialog.error(strMessage);
				showDialogmanager.close();
				return ;
			}
			if(responsetext.lsDataSet!=null && responsetext.lsDataSet.length>0)
           	{
            		commoninfodivDetial.options.data=$.extend(true, {},{Rows: responsetext.lsDataSet,Total: responsetext.lsDataSet.length});
            		commoninfodivDetial.loadData(true);
            }
			else
			{
				 	$.ligerDialog.warn("没有查到工资明细信息!");
		   			commoninfodivDetial.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivDetial.loadData(true);
	            	addtrade(); 
			}
			commoninfodivTradeDate.sortedData=false;
			showDialogmanager.close();
		}
		
		function loadDetialInfoExcel()
		{
			var strCurCompId=getCurOrgFromSearchBar();
    		var strFromDate=document.getElementById("strFromDate").value;
    		var params = "strCurCompId="+strCurCompId;		
    		params=params+"&strFromDate="+strFromDate;
			var excelDialog=$.ligerDialog.open({url: contextURL+'/ac017/loadac017Excel.action?'+params });
    		setTimeout(function () { excelDialog.close(); }, 20000);
		}
		