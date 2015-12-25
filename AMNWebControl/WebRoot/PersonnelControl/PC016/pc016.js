
   	var pc016layout=null;
   	var commoninfodivTradeDate=null;
	var showDialogmanager=null;
	var departmentmanager=null;
	var yejichooseData = [{ choose: '1', text: '业务' }, { choose: '0', text: '非业务'}];
	var sexchooseData = [{ choose: '1', text: '男' }, { choose: '0', text: '女'}];
	var statechooseData = [{ choose: '0', text: '登记' }, { choose: '1', text: '专员审核'}, { choose: '2', text: '人事经理审核'}, { choose: '3', text: '人事经理驳回'}];
	var checkinheadButton=null;
	var comfirmButton =null;
	var comfirmbackButton=null;
	var departBMZLData=JSON.parse(parent.loadCommonControlDate_select("BMZL",0));
	var postionGZGWData=JSON.parse(parent.loadCommonControlDate_select("GZGW",0));
	var curRecord=null;
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            pc016layout= $("#pc016layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = pc016layout.centerWidth
       		var departmentData=JSON.parse(parent.loadCommonControlDate("BMZL",0));
           	departmentmanager = $("#department").ligerComboBox({ data: departmentData, isMultiSelect: false,valueFieldID: 'factdepartment',width:'140',selectBoxHeight:'220'});	
         
           //--------销售条码卡
            commoninfodivTradeDate=$("#commoninfodivTradeDate").ligerGrid({
                columns: [
                { display: '原门店', 			name: 'oldcompname', 			width:100	,align: 'left' },
                { display: '新门店', 			name: 'newcompname', 			width:100	,align: 'left' },
                { display: '原部门', 			name: 'olddepid', 	 data: departBMZLData, 	 	width:100	,align: 'left' ,
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("BMZL",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.olddepid)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
	            { display: '原职位',   		name: 'oldpostion',  			data: postionGZGWData, 	 	width:100	,align: 'left' ,
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("GZGW",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.oldpostion)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
	            { display: '工号', 			name: 'oldempid', 			width:80	,align: 'left' },
                { display: '姓名', 			name: 'oldempname',  		width:80	,align: 'left' },
	            { display: '性别', 			name: 'staffsex',  			width:40	,align: 'left' , 
	            	editor: { type: 'select', data: sexchooseData, valueField: 'choose' },
	            	render: function (item)
	              	{
	                  if (item.staffsex == '1') return '男';
	                    return '女';
	                }
	            },
	            { display: '身份证号码',   	name: 'strpcid',  			width:140	,align: 'left'},
	            { display: '业绩方式',   		name: 'oldyjtype',  		width:80	,align: 'left',
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("YJFS",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.oldyjtype)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
	            { display: '业绩系数',   		name: 'oldyjrate',  		width:60	,align: 'left'},
	            { display: '业绩基数',   		name: 'oldyjamt',  			width:60	,align: 'left'},
	            { display: '派遣开始日期',   	name: 'effectivedate',  	width:120	,align: 'left'},
	            { display: '派遣结束日期',   	name: 'teffectivedate',  	width:120	,align: 'left'},
	            { display: '派遣状态',   		name: 'dispatchstate',  	width:100	,align: 'left',
	                editor: { type: 'select', data: statechooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.dispatchstate == 0) return '登记中';
	                    else  if (item.dispatchstate == 1) return '专员审核';
	                    else  if (item.dispatchstate == 2) return '人事经理审核';
	                    else  if (item.dispatchstate == 3) return '人事经理驳回';
	                    else return '';
	                }
	            }
	            ],  pageSize:25, 
                data: null,      
                width: centerWidth,
                height:height-140,
                enabledEdit: false,  checkbox: false,rownumbers: true,usePager: true, 
                onSelectRow : function (data, rowindex, rowobj)
                {
                    curRecord = data;
                  	initCheckInfo(data);
                }
            });
            document.getElementById("showTable").width=centerWidth;
            $("#searchButton").ligerButton(
	         {
	             text: '查询门店人事', width: 120,
		         click: function ()
		         {
		             loadDataSet();
		         }
	         });
	         
	          checkinheadButton=$("#checkinheadButton").ligerButton(
	         {
	             text: '人事专员审核', width: 100,
		         click: function ()
		         {
		             checkinheadbill();
		         }
	         });
	         
	         comfirmButton=$("#comfirmButton").ligerButton(
	         {
	             text: '人事经理审核', width: 100,
		         click: function ()
		         {
		             comfirmbill();
		         }
	         });
	         
	          comfirmbackButton=$("#comfirmbackButton").ligerButton(
	         {
	             text: '人事经理驳回', width: 100,
		         click: function ()
		         {
		             comfirmbackbill();
		         }
	         });
	         
	         $("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
             $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
	         var today = new Date();
			var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
			var intDay=today.getDate();
			var today = intYear.toString()+"-"+fullStr(intMonth.toString())+"-"+fullStr(intDay.toString());
			document.getElementById("strFromDate").value=today;
			document.getElementById("strToDate").value=today;
            $("#pageloading").hide(); 
            addtrade();
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
    //登记储值卡
    function loadDataSet()
    {	
   		var strCurCompId=getCurOrgFromSearchBar();
    	var strFromDate=document.getElementById("strFromDate").value;
    	var strToDate=document.getElementById("strToDate").value;
    	var params = "strCurCompId="+strCurCompId;		
    	params=params+"&strFromDate="+strFromDate;	
    	params=params+"&strToDate="+strToDate;	
    	params=params+"&department="+$("#factdepartment").val();
    	var requestUrl ="pc016/loadDataSet.action"; 
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
    		var params = "strCurCompId="+strCurCompId;	
    		params=params+"&department="+$("#factdepartment").val();
			var excelDialog=$.ligerDialog.open({url: contextURL+'/pc016/loadpc016Excel.action?'+params });
    		setTimeout(function () { excelDialog.close(); }, 10000);
		}
		
	function    initCheckInfo(data)
	{
	
		if(data.dispatchstate==0)
		{
			checkinheadButton.setEnabled();
			comfirmButton.setDisabled();
			comfirmbackButton.setEnabled();
		}
		else if(data.dispatchstate==1)
		{
			checkinheadButton.setDisabled();
			comfirmButton.setEnabled();
			comfirmbackButton.setEnabled();
		}
		else if(data.dispatchstate==2)
		{
			checkinheadButton.setDisabled();
			comfirmButton.setDisabled();
			comfirmbackButton.setEnabled();
		}
		else 
		{
			checkinheadButton.setDisabled();
			comfirmButton.setDisabled();
			comfirmbackButton.setDisabled();
		}
	
	}		
	function checkinheadbill()
	{
		var params = "oldcompid="+curRecord.oldcompid;	
    	params=params+"&oldempid="+curRecord.oldempid;	
    	params=params+"&newcompid="+curRecord.newcompid;	
    	params=params+"&effectivedate="+curRecord.effectivedate;	
    	params=params+"&teffectivedate="+curRecord.teffectivedate;
	    var requestUrl ="pc016/checkinheadBill.action"; 
		var responseMethod="checkinheadBillMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
	}
	
	function checkinheadBillMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
			if(checkNull(responsetext.strMessage)!="")
           	{
            		$.ligerDialog.warn(responsetext.strMessage);
            }
			else
			{	
		   			$.ligerDialog.success("操作成功");
		   			document.getElementById("checkinheadstaffid").value=responsetext.checkEmpId;
		   			document.getElementById("checkinheaddate").value=responsetext.checkDate;
					checkinheadButton.setDisabled();
					comfirmButton.setEnabled();
					commoninfodivTradeDate.updateRow(curRecord,{dispatchstate: 1});
			}
			
	}
	
	function comfirmbill()
	{
		var params = "oldcompid="+curRecord.oldcompid;	
    	params=params+"&oldempid="+curRecord.oldempid;	
    	params=params+"&newcompid="+curRecord.newcompid;	
    	params=params+"&effectivedate="+curRecord.effectivedate;	
    	params=params+"&teffectivedate="+curRecord.teffectivedate;
	    var requestUrl ="pc016/comfrimBill.action"; 
		var responseMethod="comfrimBillMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
	}
	
	function comfrimBillMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
			if(checkNull(responsetext.strMessage)!="")
           	{
            		$.ligerDialog.warn(responsetext.strMessage);
            }
			else
			{	
		   			$.ligerDialog.success("操作成功");
		   			document.getElementById("comfirmstaffid").value=responsetext.checkEmpId;
		   			document.getElementById("comfirmdate").value=responsetext.checkDate;
					comfirmButton.setDisabled();
					checkinheadButton.setDisabled();
					commoninfodivTradeDate.updateRow(curRecord,{dispatchstate: 2});
			}
	
	}
	
	function comfirmbackbill()
	{
		var params = "oldcompid="+curRecord.oldcompid;	
    	params=params+"&oldempid="+curRecord.oldempid;	
    	params=params+"&newcompid="+curRecord.newcompid;	
    	params=params+"&effectivedate="+curRecord.effectivedate;	
    	params=params+"&teffectivedate="+curRecord.teffectivedate;	
	    var requestUrl ="pc016/comfrimbackBill.action"; 
		var responseMethod="comfrimbackBillMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
	}
	
	function comfrimbackBillMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
			if(checkNull(responsetext.strMessage)!="")
           	{
            		$.ligerDialog.warn(responsetext.strMessage);
            }
			else
			{	
		   			$.ligerDialog.success("操作成功");
		   			document.getElementById("comfirmbackstaffid").value=responsetext.checkEmpId;
		   			document.getElementById("comfirmbackdate").value=responsetext.checkDate;
					comfirmbackButton.setDisabled();
					commoninfodivTradeDate.updateRow(curRecord,{dispatchstate: 3});
			}
	
	}
