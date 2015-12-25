
   	var pc008layout=null;
   	var commoninfodivTradeDate=null;
	var showDialogmanager=null;
	var postionGZGWData=JSON.parse(parent.loadCommonControlDate_select("GZGW",0));
	var lsStaffabsenceinfo=null;
	var curRecord=null;
	var disabsenceStaffdiv=null;
	var strCurDate="";
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            pc008layout= $("#pc008layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false,isRightCollapse:true });
           	var height = $(".l-layout-center").height();
           	var centerWidth = pc008layout.centerWidth
       		$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'100' });
           	$("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'100' });
           //--------销售条码卡
            commoninfodivTradeDate=$("#commoninfodivTradeDate").ligerGrid({
                columns: [
                { display: '门店', 				name: 'strCompName', 			width:70	, 	align: 'left' ,
                	render: function (item)
	                {
	                   	var html =item.strCompName;
     					if(checkNull(item.markflag)==1)
     					  html="<font color=\"red\">"+item.strCompName+"</font>";  
     					return html;  
	                }},
                { display: '银行账号', 			name: 'staffbankaccountno', 	width:160	, 	align: 'left' ,
	                render: function (item)
	                {
	                   	var html =item.staffbankaccountno;
     					if(checkNull(item.markflag)==1)
     					  html="<font color=\"red\">"+item.staffbankaccountno+"</font>";  
     					return html;  
	                }
	             },
	             { display: '银行名称', 			name: 'strBackName', 	width:160	, 	align: 'left'},
                { display: '员工姓名', 			name: 'staffname', 				width:100	, 	align: 'left' ,
	                render: function (item)
	                {
	                   	var html =item.staffname;
     					if(checkNull(item.markflag)==1)
     					  html="<font color=\"red\">"+item.staffname+"</font>";  
     					return html;  
	                }},
                { display: '员工编号', 			name: 'staffno', 				width:80	, 	align: 'left' ,
	                render: function (item)
	                {
	                   	var html =item.staffno;
     					if(checkNull(item.markflag)==1)
     					  html="<font color=\"red\">"+item.staffno+"</font>";  
     					return html;  
	                }
	             },
                /*{ display: '职位', 				name: 'staffposition',  		data: postionGZGWData, 	 	width:100	,align: 'left' ,
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("GZGW",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.staffposition)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },*/
	            { display: '职位', 				name: 'staffpositionname',  		width:100	,align: 'left'},
	            { display: '社保所属', 			name: 'staffsocialsource',  	width:70	,align: 'left'},
	            { display: '身份证', 				name: 'staffpcid',  			width:160	,align: 'left',
                	render: function (item)
	                {
	                   	var html =item.staffpcid;
     					if(checkNull(item.markflag)==1)
     					  html="<font color=\"red\">"+item.staffpcid+"</font>";  
     					return html;  
	                }},
	            { display: '缺勤天数', 			name: 'workdays',  				width:80	,align: 'right',
                	render: function (item)
	                {
	                   	var html =item.workdays;
     					if(checkNull(item.markflag)==1)
     					  html="<font color=\"red\">"+item.workdays+"</font>";  
     					return html;  
	                }},
	           	{ display: '应付工资', columns:
                [
                	{ display: '个人业绩', 			name: 'stafftotalyeji',  		width:70	,align: 'right',
                	render: function (item)
	                {
	                   	var html =item.stafftotalyeji;
     					if(checkNull(item.markflag)==1)
     					  html="<font color=\"red\">"+item.stafftotalyeji+"</font>";  
     					return html;  
	                }},
	            	{ display: '门店业绩', 			name: 'staffshopyeji',  		width:70	,align: 'right',
	            	render: function (item)
	                {
	                   	var html =item.staffshopyeji;
     					if(checkNull(item.markflag)==1)
     					  html="<font color=\"red\">"+item.staffshopyeji+"</font>";  
     					return html;  
	                }},
	            	{ display: '底薪', 				name: 'staffbasesalary',  		width:80	,align: 'right',
	            	render: function (item)
	                {
	                   	var html =item.staffbasesalary;
     					if(checkNull(item.markflag)==1)
     					  html="<font color=\"red\">"+item.staffbasesalary+"</font>";  
     					return html;  
	                }},
	            	{ display: '美容师补贴', 			name: 'beatysubsidy',  			width:70	,align: 'right',
	            	render: function (item)
	                {
	                   	var html =item.beatysubsidy;
     					if(checkNull(item.markflag)==1)
     					  html="<font color=\"red\">"+item.beatysubsidy+"</font>";  
     					return html;  
	                }},
	                { display: '老客补贴', 			name: 'oldcustomerreward',  	width:70	,align: 'right',
	            	render: function (item)
	                {
	                   	var html =item.oldcustomerreward;
     					if(checkNull(item.markflag)==1)
     					  html="<font color=\"red\">"+item.oldcustomerreward+"</font>";  
     					return html;  
	                }},	
	            	{ display: '指标奖罚', 			name: 'stafftargetreward',  	width:70	,align: 'right',
	            	render: function (item)
	                {
	                   	var html =item.stafftargetreward;
     					if(checkNull(item.markflag)==1)
     					  html="<font color=\"red\">"+item.stafftargetreward+"</font>";  
     					return html;  
	                }},	            	
	            	{ display: '奖励', 				name: 'staffreward',  			width:70	,align: 'right',
	            	render: function (item)
	                {
	                   	var html =item.staffreward;
     					if(checkNull(item.markflag)==1)
     					  html="<font color=\"red\">"+item.staffreward+"</font>";  
     					return html;  
	                }},
	            	{ display: '保底补贴', 			name: 'staffsubsidy',  			width:70	,align: 'right',
	            	render: function (item)
	                {
	                   	var html =item.staffsubsidy;
     					if(checkNull(item.markflag)==1)
     					  html="<font color=\"red\">"+item.staffsubsidy+"</font>";  
     					return html;  
	                }},
	            	{ display: '门店补贴', 			name: 'storesubsidy',  			width:70	,align: 'right',
	            	render: function (item)
	                {
	                   	var html =item.storesubsidy;
     					if(checkNull(item.markflag)==1)
     					  html="<font color=\"red\">"+item.storesubsidy+"</font>";  
     					return html;  
	                }},
	            	{ display: '差异调整', 			name: 'staffamtchange',  		width:70	,align: 'right',
	            	render: function (item)
	                {
	                   	var html =item.staffamtchange;
     					if(checkNull(item.markflag)==1)
     					  html="<font color=\"red\">"+item.staffamtchange+"</font>";  
     					return html;  
	                }}
                ]},
	           
	            { display: '应付工资', 			name: 'needpaybsalary',  				width:80	,align: 'right',
	            render: function (item)
	                {
	                   	var html =item.needpaybsalary;
     					if(checkNull(item.markflag)==1)
     					  html="<font color=\"red\">"+item.needpaybsalary+"</font>";  
     					return html;  
	                }},
	            { display: '小计', 				name: 'totalneedpay',  				width:80	,align: 'right'},
	           	{ display: '关爱基金扣款', columns:
                [
                	{ display: '迟到', 				name: 'latdebit',  				width:80	,align: 'right'},
                	{ display: '离职扣款', 			name: 'leaveldebit',  			width:80	,align: 'right'},
                	{ display: '罚款', 				name: 'staffdebit',  			width:80	,align: 'right'},
                	{ display: '小计', 				name: 'costtotal',  			width:80	,align: 'right'}
                ]},
	            { display: '应缴社保', 			name: 'staffsocials',  			width:70	,align: 'right'},
	           	{ display: '税前工资', 			name: 'needpaysalary',  		width:70	,align: 'right'},
	           	{ display: '个人所得税', 			name: 'salarydebit',  			width:80	,align: 'right'},
	           	{ display: '代扣', 				name: 'staffdaikou',  			width:70	,align: 'right'},
	           	{ display: '责任金抵押', 				name: 'zerenjincost',  			width:70	,align: 'right'},
	           	{ display: '责任金返还', 				name: 'zerenjinback',  			width:70	,align: 'right'},
               	{ display: '住宿', 				name: 'staydebit',  			width:70	,align: 'right'},
	           	{ display: '学习费', 				name: 'studydebit',  			width:70	,align: 'right'},
	           	{ display: '扣成本', 				name: 'staffcost',  			width:70	,align: 'right'},
	            { display: '税后扣款小计', 		name: 'sumcost',  				width:80	,align: 'right'},
	            { display: '应付税后工资', 		name: 'factpaysalary',  		width:80	,align: 'right',totalSummary:{
	            	 render: function (suminf, column, cell)
                     {
                         return ForDight(suminf.sum,2);
                     },
						align: 'right'
                }},
	            { display: '实发税后工资', 		name: 'comppaysalary',  		width:80	,align: 'right'}
	           
	             
	            ],  pageSize:20, 
                data: null,      
                width: centerWidth,
                height:height-100,
                enabledEdit: true,  checkbox: false,rownumbers: true,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                	curRecord=data;
                	loadCurStaffAbsenceinfo(data, rowindex, rowobj);
                } , 
                onDblClickRow : function (data, rowindex, rowobj)
                {
                	curRecord=data;
                    loadSelecDetialData(data, rowindex, rowobj);
                }
            });
            
            disabsenceStaffdiv=$("#disabsenceStaffdiv").ligerGrid({
                columns: [
                { display: '缺勤日期', name: 'absencedate',  width: 165 }
                ],  pageSize:10, 
                data: null,        
                width: '100%',
                height:'95%',
                enabledEdit: false,checkbox: false,rownumbers:false,usePager:false ,
                onSelectRow : function (data, rowindex, rowobj)
                {
                	strCurDate=data.absencedate;
                } ,
                toolbar: { items: [
	           		{ text: '人事经理作废', click: comfrimstopAbsence, img: contextURL+'/common/ligerui/ligerUI/skins/icons/candle.gif' }
					]
                }
            });
            
            document.getElementById("showTable").width=centerWidth;
            $("#searchButton").ligerButton(
	         {
	             text: '查询工资', width: 100,
		         click: function ()
		         {
		             loadDataSet();
		         }
	         });
	         
	         $("#markButton").ligerButton(
	         {
	             text: '标记工资行', width: 60,
		         click: function ()
		         {
		             markOneDataSet();
		         }
	         });
	         
	          $("#dmarkButton").ligerButton(
	         {
	             text: '删除标记', width: 60,
		         click: function ()
		         {
		             dmarkOneDataSet();
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
            addAbsence();
   		}catch(e){alert(e.message);}
    });
    
    function addAbsence()
    {
    	var row = disabsenceStaffdiv.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
		disabsenceStaffdiv.addRow({ 
				                absencedate: ""
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
    function loadDataSet()
    {	
   
    	var strCurCompId=getCurOrgFromSearchBar();

    	var strFromDate=document.getElementById("strFromDate").value;
    	var strToDate=document.getElementById("strToDate").value;
    	
    	var params = "strCurCompId="+strCurCompId;		
    	params=params+"&strFromDate="+strFromDate;	
    	params=params+"&strToDate="+strToDate;	
     	var requestUrl ="pc008/loadDataSet.action"; 
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
            }
			else
			{
				 	$.ligerDialog.warn("没有查到营业信息!");
		   			commoninfodivTradeDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivTradeDate.loadData(true);
	            	addtrade(); 
			}
			showDialogmanager.close();
		}
		
		function loadDataSetExcel()
		{
			var strCurCompId=getCurOrgFromSearchBar();

    		var strFromDate=document.getElementById("strFromDate").value;
    		var strToDate=document.getElementById("strToDate").value;
    		var iA3=0;
    	    if(document.getElementById("shortExcep").checked==true)
    	    {
    	    	 iA3=1;
    	    }
    		var params = "strCurCompId="+strCurCompId;		
    		params=params+"&strFromDate="+strFromDate;	
    		params=params+"&strToDate="+strToDate;	
    		params=params+"&iA3="+iA3;	
			var excelDialog=$.ligerDialog.open({url: contextURL+'/pc008/loadpc008Excel.action?'+params });
    		setTimeout(function () { excelDialog.close(); }, 10000);
		}
		

	function loadCurStaffAbsenceinfo(data, rowindex, rowobj)
	{
			var strFromDate=document.getElementById("strFromDate").value;
    		var strToDate=document.getElementById("strToDate").value;
			var params = "strCurCompId="+getCurOrgFromSearchBar();			
    		params =params+ "&strStaffInNo="+curRecord.staffinid;	
    		params=params+"&strFromDate="+strFromDate;	
    		params=params+"&strToDate="+strToDate;	
     		var requestUrl ="pc008/loadStaffAbsence.action"; 
			var responseMethod="loadCurStaffAbsenceinfoMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params );
	}
	 function loadCurStaffAbsenceinfoMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
    		if(responsetext.lsStaffabsenceinfo!=null && responsetext.lsStaffabsenceinfo.length>0)
    		{
    			disabsenceStaffdiv.options.data=$.extend(true, {},{Rows: responsetext.lsStaffabsenceinfo,Total: responsetext.lsStaffabsenceinfo.length});
            	disabsenceStaffdiv.loadData(true);
            	disabsenceStaffdiv.select(0);
    		}
    		else
    		{
    				disabsenceStaffdiv.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	disabsenceStaffdiv.loadData(true);
	            	strCurDate="";
	            	addAbsence(); 
    		}
	   	}catch(e){alert(e.message);}
    }
    
	function loadSelecDetialData(data, rowindex, rowobj)
    {
    		var strFromDate=document.getElementById("strFromDate").value;
    		var strToDate=document.getElementById("strToDate").value;
    		var params = "strCurCompId="+getCurOrgFromSearchBar();		
    		params =params+ "&strStaffInNo="+curRecord.staffinid;	
    		params=params+"&strFromDate="+strFromDate;	
    		params=params+"&strToDate="+strToDate;	
     		var requestUrl ="pc008/loadStaffAbsence.action"; 
			var responseMethod="loadStaffAbsenceMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
     function loadStaffAbsenceMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
    		lsStaffabsenceinfo=responsetext.lsStaffabsenceinfo;
	   		shownewPayDialogmanager=$.ligerDialog.open({ height: null, url: contextURL+'/PersonnelControl/PC008/staffAbsence.jsp', width: 750, showMax: false, showToggle: false, showMin: false, isResize: false, title: '员工缺勤登记' });
	   	}catch(e){alert(e.message);}
    }

     function absenceStaffMessage(request)
	 {
	 			var responsetext = eval("(" + request.responseText + ")");
		        var strMessage=responsetext.strMessage;
		         if(checkNull(strMessage)=="")
		        {	        		 
		        	 $.ligerDialog.success("录入成功!");
		        }
		        else
		        {
		        	$.ligerDialog.error(strMessage);
		        }
	}
	
	
	function markOneDataSet()
	{
		var strCurCompId=getCurOrgFromSearchBar();
		var params = "strCurCompId="+strCurCompId;	
		params =params+"&strStaffInNo="+curRecord.staffinid;
    	params =params+"&strCurMonth="+document.getElementById("strToDate").value.substring(0,4)+document.getElementById("strToDate").value.substring(5,7);	
    	var requestUrl ="pc008/markOneDataSet.action"; 
		var responseMethod="markOneDataSetMessage";
		sendRequestForParams_p(requestUrl,responseMethod,params );
	}
	function markOneDataSetMessage(request)
	 {
	 			var responsetext = eval("(" + request.responseText + ")");
		        var strMessage=responsetext.strMessage;
		         if(checkNull(strMessage)=="")
		        {	        		 
		        	 $.ligerDialog.success("标记成功!");
		        }
		        else
		        {
		        	$.ligerDialog.error(strMessage);
		        }
	}
	
	
	function dmarkOneDataSet()
	{
		var strCurCompId=getCurOrgFromSearchBar();
		var params = "strCurCompId="+strCurCompId;	
		params =params+"&strStaffInNo="+curRecord.staffinid;
    	params =params+"&strCurMonth="+document.getElementById("strToDate").value.substring(0,4)+document.getElementById("strToDate").value.substring(5,7);	
    	var requestUrl ="pc008/dmarkOneDataSet.action"; 
		var responseMethod="dmarkOneDataSetMessage";
		sendRequestForParams_p(requestUrl,responseMethod,params );
	}
	function dmarkOneDataSetMessage(request)
	 {
	 			var responsetext = eval("(" + request.responseText + ")");
		        var strMessage=responsetext.strMessage;
		         if(checkNull(strMessage)=="")
		        {	        		 
		        	 $.ligerDialog.success("删除标记成功!");
		        }
		        else
		        {
		        	$.ligerDialog.error(strMessage);
		        }
	}
	
	
	
	function comfrimstopAbsence()
	{
		var params = "strCurCompId="+curRecord.strCompId;			
    	params =params+ "&strStaffInNo="+curRecord.staffinid;
    	params =params+"&strCurDate="+strCurDate;	
    	var requestUrl ="pc008/comfrimstopAbsence.action"; 
		var responseMethod="comfrimstopAbsenceMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
	}
	 function comfrimstopAbsenceMessage(request)
	 {
	 			var responsetext = eval("(" + request.responseText + ")");
		        var strMessage=responsetext.strMessage;
		         if(checkNull(strMessage)=="")
		        {	        		 
		        	 $.ligerDialog.success("作废成功!");
		        	 disabsenceStaffdiv.deleteSelectedRow();
		        }
		        else
		        {
		        	$.ligerDialog.error(strMessage);
		        }
	}