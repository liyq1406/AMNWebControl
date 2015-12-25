
   	var pc010layout=null;
   	var commoninfodivTradeDate=null;
	var showDialogmanager=null;
	var departmentmanager=null;
	var yejichooseData = [{ choose: '1', text: '业务' }, { choose: '0', text: '非业务'}];
	var sexchooseData = [{ choose: '1', text: '男' }, { choose: '0', text: '女'}];
	var departBMZLData=JSON.parse(parent.loadCommonControlDate_select("BMZL",0));
	var postionGZGWData=JSON.parse(parent.loadCommonControlDate_select("GZGW",0));
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            pc010layout= $("#pc010layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = pc010layout.centerWidth
       		//var departmentData=JSON.parse(parent.loadCommonControlDate("BMZL",0));
           	//departmentmanager = $("#department").ligerComboBox({ data: departmentData, isMultiSelect: false,valueFieldID: 'factdepartment',width:'140',selectBoxHeight:'220'});	
           //var postionData=JSON.parse(parent.loadCommonControlDate("GZGW",0));
           //	departmentmanager = $("#postion").ligerComboBox({ data: postionData, isMultiSelect: false,valueFieldID: 'factpostion',width:'140',selectBoxHeight:'220'});	
         	addOption("","",document.getElementById("department"));
           	var lsBMZL=parent.gainCommonInfoByCode("BMZL",0);
		    for(var i=0;i<lsBMZL.length;i++)
			{
				addOption(lsBMZL[i].bparentcodekey,lsBMZL[i].parentcodevalue,document.getElementById("department"));
			}		
            addOption("","",document.getElementById("postion"));
           	var lsGZGW=parent.gainCommonInfoByCode("GZGW",0);
		    for(var i=0;i<lsGZGW.length;i++)
			{
				addOption(lsGZGW[i].bparentcodekey,lsGZGW[i].parentcodevalue,document.getElementById("postion"));
			}	
           //--------销售条码卡
            commoninfodivTradeDate=$("#commoninfodivTradeDate").ligerGrid({
                columns: [
                { display: '门店', 			name: 'bcompname', 			width:100	,align: 'left' },
                { display: '部门', 			name: 'department', 	 data: departBMZLData, 	 	width:60	,align: 'left' ,
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("BMZL",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.department)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
	            { display: '职位',   		name: 'position',  			data: postionGZGWData, 	 	width:80	,align: 'left' ,
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("GZGW",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.position)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
	            { display: '职称',   		name: 'positiontitle',  			data: postionGZGWData, 	 	width:80	,align: 'left' ,
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("GZZC",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.positiontitle)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
	            { display: '工号', 			name: 'bstaffno', 			width:60	,align: 'left' },
                { display: '姓名', 			name: 'staffname',  		width:80	,align: 'left' },
	            { display: '性别', 			name: 'staffsex',  			width:40	,align: 'left' , 
	            	editor: { type: 'select', data: sexchooseData, valueField: 'choose' },
	            	render: function (item)
	              	{
	                  if (item.staffsex == '1') return '男';
	                    return '女';
	                }
	            },
	            { display: '手机号码',   		name: 'mobilephone',  		width:100	,align: 'left'},
	            { display: '身份证号码',   	name: 'pccid',  			width:140	,align: 'left'},
	            { display: '身份证地址',   	name: 'aaddress',  			width:220	,align: 'left'},
	            { display: '档案号',   		name: 'fillno',  		width:80	,align: 'left'},
	            { display: '入职日期',   		name: 'arrivaldate',  		width:80	,align: 'left'},
	            { display: '合约终止日期',   	name: 'contractdate',  		width:80	,align: 'left'},
	            { display: '基本工资',   		name: 'basesalary',  		width:60	,align: 'left'},
	            { display: '社保类型',   		name: 'socialsource',  	width:80	,align: 'left'},
	            { display: '社保',   		name: 'socialsecurity',  	width:40	,align: 'left'},
	            { display: '业务',   		name: 'businessflag',  		width:40	,align: 'left' , 
	            	editor: { type: 'select', data: yejichooseData, valueField: 'choose' },
	            	render: function (item)
	              	{
	                  if (item.businessflag == '1') return '业务';
	                    return '非业务';
	                }
	            },
	            { display: '业绩方式',   		name: 'resulttye',  		width:60	,align: 'left'},
	            { display: '备注',   		name: 'remark',  			width:120	,align: 'left'}
	            ],  pageSize:25, 
                data: null,      
                width: centerWidth,
                height:height-60,
                enabledEdit: false,  checkbox: false,rownumbers: true,usePager: true
               
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
	         
	         $("#excelButton").ligerButton(
	         {
	             text: '转Excel', width: 120,
		         click: function ()
		         {
		             loadDataSetExcel();
		         }
	         });
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

    	
    	var params = "strCurCompId="+strCurCompId;	
    	params=params+"&department="+document.getElementById("department").value;
    	params=params+"&postion="+document.getElementById("postion").value;
    	params=params+"&socialsourceflag="+document.getElementById("socialsourceflag").value;
    	var requestUrl ="pc010/loadDataSet.action"; 
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
    		params=params+"&department="+document.getElementById("department").value;
    		params=params+"&postion="+document.getElementById("postion").value;
    		params=params+"&socialsourceflag="+document.getElementById("socialsourceflag").value;
			var excelDialog=$.ligerDialog.open({url: contextURL+'/pc010/loadpc010Excel.action?'+params });
    		setTimeout(function () { excelDialog.close(); }, 10000);
		}
