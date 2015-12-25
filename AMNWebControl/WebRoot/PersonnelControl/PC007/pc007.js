
   	var pc007layout=null;
   	var commoninfodivTradeDate=null;
   	var commoninfodivStaffDate=null;
	var showDialogmanager=null;
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            pc007layout= $("#pc007layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = pc007layout.centerWidth
       
           	$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
            $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
           //--------门店员工信息
           commoninfodivStaffDate=$("#commoninfodivStaffDate").ligerGrid({
                columns: [
                { display: '员工工号', 			name: 'bstaffno', 			width:60	,align: 'left' },
                { display: '员工名称', 			name: 'staffname', 			width:110	,align: 'left' },
                { display: '内部工号', 			name: 'manageno', 		width:120	,align: 'left' }
	             
	            ],  pageSize:20, 
                data: null,      
                width: 315,
                height:height-100,
                enabledEdit: false,  checkbox: false,rownumbers: false,usePager: false,	          
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                } 
            });
           var list=parent.gainCommonInfoByCode("DJLB",0);
            commoninfodivTradeDate=$("#commoninfodivTradeDate").ligerGrid({
                columns: [
                	{ display: '类别', 			name: 'actionid', 			width:100	,align: 'left' , 
	                editor: { type: 'select',  valueField: 'choose' },
	                render: function (item)
	                {
	                	var title="";
	          			$.each(list, function(i, code){
	          				if(item.actionid == code.bparentcodekey){
	          					title = code.parentcodevalue;
	          					return false;
	          				}
	          			});
	                    /*if (item.actionid == 1) return '会员卡开卡';
	                    else if (item.actionid == 2) return '会员卡充值';
	                    else if (item.actionid == 3) return '会员卡异动';
	                    else if (item.actionid == 4) return '疗程兑换';
	                    else if (item.actionid == 5) return '条码卡销售';
	                    else if (item.actionid == 27 || item.actionid == 28 || item.actionid == 29 || item.actionid == 30 || item.actionid == 31 ) return '合作项目销售';
	                    else if (item.actionid == 7) return '指定大工项目';
	                    else if (item.actionid == 8) return '轮班大工项目';
	                    else if (item.actionid == 9) return '补工大工项目';
	                    else if (item.actionid == 10) return '指定中工项目';
	                    else if (item.actionid == 11) return '轮班中工项目';
	                    else if (item.actionid == 12) return '补工中工项目';
	                    else if (item.actionid == 13) return '指定小工项目';
	                    else if (item.actionid == 14) return '轮班小工项目';
	                    else if (item.actionid == 15) return '补工小工项目';
	                    else if (item.actionid == 16) return '指定大工产品';
	                    else if (item.actionid == 17) return '轮班大工产品';
	                    else if (item.actionid == 18) return '补工大工产品';
	                    else if (item.actionid == 19) return '指定中工产品';
	                    else if (item.actionid == 20) return '轮班中工产品';
	                    else if (item.actionid == 21) return '补工中工产品';
	                    else if (item.actionid == 22) return '指定小工产品';
	                    else if (item.actionid == 23) return '轮班小工产品';
	                    else if (item.actionid == 24) return '补工小工产品';
	                    else if (item.actionid == 25) return '健发疗程奖励';
	                    else if (item.actionid == 32) return '介绍人提成';
	                    else if (item.actionid == 35) return '肩颈疗程奖励';
	                    else if (item.actionid == 36) return '肩颈推荐卡金奖励';
	                    else if (item.actionid == 37) return '发型师手工费';
	                    else if (item.actionid == 88) return '经理/顾问奖励';
	                    else if (item.actionid == 89) return '美容师绩效罚款';
	                    else if (item.actionid == 91) return '推首席奖励';
	                    else if (item.actionid == 92) return '推总监奖励';
	                    else if (item.actionid == 93) return '年卡销售';
	                    else if (item.actionid == 94) return '合作项目';
	                    else if (item.actionid == 95) return '合作项目';
	                    else if (item.actionid == 96) return '合作项目';
	                    else if (item.actionid == 98) return '年卡指标奖罚';
	                    else if (item.actionid == 99) return '合作项目';
	                    else if (item.actionid == 100) return '合作项目';
	                    //else if (item.actionid == 93) return '年卡销售';
	                    //else if (item.actionid == 93) return '年卡销售';*/
	                      return title;
	                }
	            },
                { display: '日期', 			name: 'srvdate', 			width:90	,align: 'left' },
                { display: '单号', 			name: 'billid', 		width:120	,align: 'left' },
                { display: '代码', 			name: 'code',  			width:100	,align: 'left'},
	            { display: '名称', 			name: 'name', 	 		width:160	,align: 'left',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return "现金业绩:"+loadCashTotal();
                        },
						align: 'right'
                    }},
	            { display: '支付方式',   		name: 'payway',  		width:80	,align: 'left'},
	            { display: '营业额',   		name: 'billamt',  			width:80	,align: 'right',
	            	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }
	            },
	            { display: '卡金',   		name: 'staffshareyeji',  			width:80	,align: 'right',
	            	totalSummary:
                    {
                         render: function (suminf, column, cell)
                        {
                           return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }
                },
                { display: '业绩',   		name: 'staffyeji',  			width:80	,align: 'right',
	            	totalSummary:
                    {
                         render: function (suminf, column, cell)
                        {
                           return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }
                },
	            { display: '提成',   		name: 'staffticheng',  			width:80	,align: 'right',
	            	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }
                }
	            ],  pageSize:20, 
                data: null,      
                width: centerWidth-350,
                height:height-100,
                enabledEdit: false,  checkbox:false,rownumbers: true,usePager: false
               
            });
            document.getElementById("showTable").width=centerWidth;
            $("#searchButton").ligerButton(
	         {
	             text: '查询日报表', width: 120,
		         click: function ()
		         {
		             loadDataSet();
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
            addtradestaff();
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
     	var requestUrl ="pc007/loadStaffDataSet.action"; 
		var responseMethod="loadStaffDataSetMessage";		
		 showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    
    }
    
    function loadStaffDataSetMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
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
	
	function loadSelecDetialData(data, rowindex, rowobj)
    {
    	var strCurCompId=getCurOrgFromSearchBar();
    	var strFromDate=document.getElementById("strFromDate").value;
    	var strToDate=document.getElementById("strToDate").value;
    	var params = "strCurCompId="+strCurCompId;			
    	params=params+"&strFromDate="+strFromDate;	
    	params=params+"&strToDate="+strToDate;		
    	params=params+"&strFromInid="+data.manageno;	
    	params=params+"&strToInid="+data.manageno;	
    	showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
     	var requestUrl ="pc007/loadDataSet.action"; 
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
            }
			else
			{
				 	$.ligerDialog.warn("没有查到业绩信息!");
		   			commoninfodivTradeDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivTradeDate.loadData(true);
	            	addtrade(); 
			}
			showDialogmanager.close();
	}
