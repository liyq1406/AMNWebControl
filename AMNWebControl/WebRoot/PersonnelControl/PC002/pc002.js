
   	var pc002layout=null;
   	var commoninfodivTradeDate=null;
	var showDialogmanager=null;
	var departmentmanager=null;
	var typechooseData = [{ choose: '5', text: '本店' }, { choose: '6', text: '跨店'}];
	var statechooseData = [{ choose: '0', text: '登记' }, { choose: '1', text: '门店审核'}, { choose: '2', text: '总部审核'}, { choose: '3', text: '人事部审核'}, { choose: '5', text: '已驳回'}, { choose: '8', text: '已生效'}];
	var curRecord=null;		
	var departBMZLData=JSON.parse(parent.loadCommonControlDate_select("BMZL",0));
	var postionGZGWData=JSON.parse(parent.loadCommonControlDate_select("GZGW",0));
	var yetypeYJFSData=JSON.parse(parent.loadCommonControlDate_select("YJFS",0));
	var checkButton=null;
	var checkinheadButton=null;
	var comfirmButton =null;
	var comfirmbackButton=null;
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            pc002layout= $("#pc002layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = pc002layout.centerWidth
       		$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'90' });
            $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'90' });
           //--------销售条码卡
            commoninfodivTradeDate=$("#commoninfodivTradeDate").ligerGrid({
                columns: [
                { display: '调动类型',   		name: 'bchangetype',  			width:60	,align: 'left', 
	                editor: { type: 'select', data: typechooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.bchangetype == 5) return '本店';
	                      return '跨店';
	                }
	            },
                { display: '单据状态',   		name: 'billflag',  				width:80	,align: 'left', 
	                editor: { type: 'select', data: typechooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.billflag == 0) return '登记中';
	                    else  if (item.billflag == 1) return '门店审核';
	                    else  if (item.billflag == 2) return '人事专员审核';
	                    else  if (item.billflag == 3) return '人事经理审核';
	                    else  if (item.billflag == 8) return '已生效';
	                    else  if (item.billflag == 5) return '已驳回';
	                    else return '';
	                }
	            },
	            { display: '生效日期',   		name: 'validatestartdate',  				width:100	,align: 'left'},
	            { display: '原工号',   		name: 'changestaffno',  				width:100	,align: 'left'},
	            { display: '姓名',   		name: 'changestaffname',  				width:100	,align: 'left'},
	            { display: '手机号码',   		name: 'staffphone',  				width:100	,align: 'left'},
	            { display: '身份证号',   		name: 'staffpcid',  				width:140	,align: 'left'},
	            { display: '原门店',   		name: 'appchangecompname',  			width:80	,align: 'left'},
	            { display: '原部门',   		name: 'beforedepartment',  				data: departBMZLData,	width:100 	,align: 'left',
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("BMZL",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.beforedepartment)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
	            { display: '原职位',   		name: 'beforepostation',  		data: postionGZGWData, 	 	width:100	,align: 'left' ,
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("GZGW",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.beforepostation)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
	            { display: '原工资',   		name: 'beforesalary',  	width:60	,align: 'left'},
	            { display: '原业绩方式',   	name: 'beforeyejitype',  	data: yetypeYJFSData, 	 	width:80	,align: 'left' ,
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("YJFS",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.beforeyejitype)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
	            { display: '原业绩系数',   	name: 'beforeyejirate',  		width:80	,align: 'left'},
	            { display: '原业绩基数',   	name: 'beforeyejiamt',  		width:80	,align: 'left'},
	            { display: '新工号',   		name: 'afterstaffno',  			width:100	,align: 'left'},
	            { display: '新门店',   		name: 'aftercompname',  		width:80	,align: 'left'},
	            { display: '新部门',   		name: 'afterdepartment',  		data: departBMZLData,	width:100 	,align: 'left',
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("BMZL",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.afterdepartment)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
	            { display: '新职位',   		name: 'afterpostation',  		data: postionGZGWData, 	 	width:100	,align: 'left' ,
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("GZGW",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.afterpostation)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
	            { display: '新工资',   		name: 'aftersalary',  			width:60	,align: 'left'},
	            { display: '新业绩方式',   	name: 'afteryejitype',  		data: yetypeYJFSData, 	 	width:80	,align: 'left' ,
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("YJFS",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.afteryejitype)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
	            { display: '新业绩系数',   	name: 'afteryejirate',  		width:80	,align: 'left'},
	            { display: '新业绩基数',   	name: 'afteryejiamt',  			width:80	,align: 'left'},
	            { display: '单据编号',   		name: 'bchangebillid',  		width:120	,align: 'left'},
	            { display: '申请日期',   		name: 'changedate',  			width:80	,align: 'left'},
	            { display: '备注',   		name: 'remark',  				width:200	,align: 'left'},
	            { name: 'bchangecompid',    hide:true ,align: 'left',width:5}
	            ],  pageSize:25, 
                data: null,      
                width: centerWidth,
                height:height-130,
                groupColumnName:'bchangetype',
             
                groupRender: function (bchangetype,groupdata)
                {
                   		if (bchangetype == 5) return '本店调整';
	                    else if (bchangetype == 6) return '跨店申请';
	                    return '';
                },
                onSelectRow : function (data, rowindex, rowobj)
                {
                    curRecord = data;
                    initCheckInfo(data);
                }, 
                onDblClickRow : function (data, rowindex, rowobj)
                {
                	$.ligerDialog.open({ height: 700, url: contextURL+'/PersonnelControl/PC002/staffchange.jsp', width: 550, showMax: false, showToggle: false, showMin: false, isResize: true, title: '人事打印单' });
     			},
                enabledEdit: false,  checkbox: false,rownumbers: false,usePager: true
               
            });
            document.getElementById("showTable").width=centerWidth;
            document.getElementById("checkTable").width=centerWidth;
            $("#searchButton").ligerButton(
	         {
	             text: '查询调动单据', width: 120,
		         click: function ()
		         {
		             loadDataSet();
		         }
	         });
	         
	         checkButton=$("#checkButton").ligerButton(
	         {
	             text: '门店审核', width: 100,
		         click: function ()
		         {
		             checkbill();
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
	         
	        /*$("#comfirmValodateButton").ligerButton(
	         {
	             text: '立即生效', width: 160,
		         click: function ()
		         {
		             comfirmValodateButton();
		         }
	         });*/
	         
	         
	        var today = new Date();
			var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
			var intDay=today.getDate();
			var today = intYear.toString()+"-"+fullStr(intMonth.toString())+"-"+fullStr(intDay.toString());
			document.getElementById("strFromDate").value=today;
			document.getElementById("strToDate").value=today;
            $("#pageloading").hide(); 
            loadCardAllotInfo();
   		}catch(e){alert(e.message);}
    });
       //加载卡入库主明细
    function loadCardAllotInfo()
    {
     	var requestUrl ="pc002/loadMaster.action"; 
		var responseMethod="loadMasterMessage";				
		sendRequestForParams_p(requestUrl,responseMethod,"" );
    }
   
   function loadMasterMessage(request)
   {
       		var responsetext = eval("(" + request.responseText + ")");
    		if(responsetext.lsDataSet!=null && responsetext.lsDataSet.length>0)
	   		{
	   			curMasterInfoDate={Rows: responsetext.lsDataSet,Total: responsetext.lsDataSet.length};
	   			commoninfodivTradeDate.options.data=$.extend(true, {},curMasterInfoDate);
            	commoninfodivTradeDate.loadData(true);   
            	commoninfodivTradeDate.select(0);          	
	   		}
	   		else
	   		{
	   			commoninfodivTradeDate.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivTradeDate.loadData(true);
            	addtrade()  
	   		}
	}
    
    function  addtrade()
        {
        	var row = commoninfodivTradeDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivTradeDate.addRow({ 
				                bchangetype: 5,
				                billflag: 0
				             
				            }, row, false);
        }
    //登记储值卡
    function loadDataSet()
    {	
   
    	var strCurCompId=getCurOrgFromSearchBar();
    	var params = "strCurCompId="+strCurCompId;	
    	params=params+"&strFromDate="+document.getElementById("strFromDate").value;
    	params=params+"&strToDate="+document.getElementById("strToDate").value;
    	params=params+"&iSearchType="+document.getElementById("iSearchType").value;
		params=params+"&iSearchState="+document.getElementById("iSearchState").value;
		params=params+"&strCurStaffNo="+document.getElementById("strCurStaffNo").value;
        var requestUrl ="pc002/loadDataSet.action"; 
		var responseMethod="loadDataSetMessage";		
		 showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    
    }
    
    function loadDataSetMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
			showDialogmanager.close();
			if(responsetext.lsDataSet!=null && responsetext.lsDataSet.length>0)
           	{
            		commoninfodivTradeDate.options.data=$.extend(true, {},{Rows: responsetext.lsDataSet,Total: responsetext.lsDataSet.length});
            		commoninfodivTradeDate.loadData(true);
            		commoninfodivTradeDate.select(0);  
            }
			else
			{
				 	$.ligerDialog.warn("没有查到调动信息!");
		   			commoninfodivTradeDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivTradeDate.loadData(true);
	            	addtrade(); 
			}
	
	}

	function    initCheckInfo(data)
	{
		document.getElementById("checkstaffid").value=data.checkstaffid;
		document.getElementById("checkdate").value=data.checkdate;
		document.getElementById("checkinheadstaffid").value=data.checkinheadstaffid;
		document.getElementById("checkinheaddate").value=data.checkinheaddate;
		if(data.comfirmflag==1)
		{
			document.getElementById("comfirmstaffid").value=data.comfirmstaffid;
			document.getElementById("comfirmdate").value=data.comfirmdate;
		}
		else if(data.comfirmflag==2)
		{
			document.getElementById("comfirmbackstaffid").value=data.comfirmstaffid;
			document.getElementById("comfirmbackButton").value=data.comfirmdate;
		}
		else
		{
			document.getElementById("comfirmstaffid").value="";
			document.getElementById("comfirmdate").value="";
			document.getElementById("comfirmbackstaffid").value="";
			document.getElementById("comfirmbackButton").value="";
		}
		if(data.billflag==0)
		{
			checkButton.setEnabled();
			checkinheadButton.setDisabled();
			comfirmButton.setDisabled();
			comfirmbackButton.setEnabled();
		}
		else if(data.billflag==1)
		{
			checkButton.setDisabled();
			checkinheadButton.setEnabled();
			comfirmButton.setDisabled();
			comfirmbackButton.setEnabled();
		}
		else if(data.billflag==2)
		{
			checkButton.setDisabled();
			checkinheadButton.setDisabled();
			comfirmButton.setEnabled();
			comfirmbackButton.setEnabled();
		}
		else 
		{
			checkButton.setDisabled();
			checkinheadButton.setDisabled();
			comfirmButton.setDisabled();
			comfirmbackButton.setDisabled();
		}
	
	}
	
	function checkbill()
	{
		var params = "strCompId="+curRecord.bchangecompid;	
    	params=params+"&strBillId="+curRecord.bchangebillid;	
    	params=params+"&iChangeType="+curRecord.bchangetype;	
	    var requestUrl ="pc002/checkBill.action"; 
		var responseMethod="checkBillMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
	}
	
	function checkBillMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
			if(checkNull(responsetext.strMessage)!="")
           	{
            		$.ligerDialog.warn(responsetext.strMessage);
            }
			else
			{	
		   			$.ligerDialog.success("操作成功");
		   			document.getElementById("checkstaffid").value=responsetext.checkEmpId;
		   			document.getElementById("checkdate").value=responsetext.checkDate;
					checkButton.setDisabled();
					comfirmButton.setDisabled();
					checkinheadButton.setEnabled();
			}
	
	}
	
	
	function checkinheadbill()
	{
		var params = "strCompId="+curRecord.bchangecompid;	
    	params=params+"&strBillId="+curRecord.bchangebillid;	
    	params=params+"&iChangeType="+curRecord.bchangetype;	
	    var requestUrl ="pc002/checkinheadBill.action"; 
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
					checkButton.setDisabled();
			}
			
	}
	
	function comfirmbill()
	{
		var params = "strCompId="+curRecord.bchangecompid;	
    	params=params+"&strBillId="+curRecord.bchangebillid;	
    	params=params+"&iChangeType="+curRecord.bchangetype;	
	    var requestUrl ="pc002/comfrimBill.action"; 
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
					checkButton.setDisabled();
			}
	
	}
	
	
	function comfirmValodateButton()
	{
		var params = "strCompId="+curRecord.bchangecompid;	
    	params=params+"&strBillId="+curRecord.bchangebillid;	
	    var requestUrl ="pc002/comfirmValodateButton.action"; 
		var responseMethod="comfirmValodateButtonMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
	}
	
	function comfirmValodateButtonMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
			if(checkNull(responsetext.strMessage)!="")
           	{
            		$.ligerDialog.warn(responsetext.strMessage);
            }
			else
			{	
		   			$.ligerDialog.success("操作成功");
					comfirmButton.setDisabled();
			}
	
	}
	
	function comfirmbackbill()
	{
		var params = "strCompId="+curRecord.bchangecompid;	
    	params=params+"&strBillId="+curRecord.bchangebillid;	
    	params=params+"&iChangeType="+curRecord.bchangetype;	
	    var requestUrl ="pc002/comfrimbackBill.action"; 
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
			}
	
	}