
   	var pc006layout=null;
   	var commoninfodivPersonDate1=null;
   	var commoninfodivPersonDate2=null;
   	var commoninfodivPersonDate3=null;
   	var commoninfodivPersonDate4=null;
   	var commoninfodivPersonDate5=null;
   	var commoninfodivPersonDate6=null;
	var showDialogmanager=null;
	var departmentmanager=null;
	var PC006Tab=null;
	var departBMZLData=JSON.parse(parent.loadCommonControlDate_select("BMZL",0));
	var postionGZGWData=JSON.parse(parent.loadCommonControlDate_select("GZGW",0));
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            pc006layout= $("#pc006layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = pc006layout.centerWidth
       		$("#strFromDate").ligerDateEditor({ showTime:true,width:160,format: "yyyy-MM", labelWidth:80, labelAlign: 'right' })
			$("#PC006Tab").ligerTab();
            PC006Tab = $("#PC006Tab").ligerGetTabManager();
           //--------销售条码卡
            commoninfodivPersonDate1=$("#commoninfodivPersonDate1").ligerGrid({
                columns: [
                { display: '门店编号', 		name: 'appchangecompid', 			width:100	,align: 'left' , totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return '总记录数: '+suminf.count;
                        },
						align: 'right'
                    }
                },
                { display: '门店名称', 		name: 'appchangecompname', 			width:100 	,align: 'left'},
                { display: '部门',   		name: 'afterdepartment', 		data: departBMZLData,	width:100 	,align: 'left',
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
	            { display: '职位',   		name: 'afterpostation', 			data: postionGZGWData, 	 	width:100	,align: 'left' ,
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
	            { display: '工号', 			name: 'changestaffno', 				width:80	,align: 'left' },
                { display: '姓名', 			name: 'changestaffname',  			width:120	,align: 'left' },
	            { display: '手机号码',   		name: 'staffphone',  				width:100	,align: 'left'},
	            { display: '身份证号码',   	name: 'staffpcid',  				width:160	,align: 'left'},
	            { display: '入职日期',   		name: 'validatestartdate',  		width:80	,align: 'left'},
	            { display: '备注',   		name: 'remark',  					width:180	,align: 'left'}
	            ],  pageSize:25, 
                data: null,      
                width: centerWidth-100,
                height:600,
                enabledEdit: false,  checkbox: false,rownumbers: false,usePager: false
               
            });
            commoninfodivPersonDate2=$("#commoninfodivPersonDate2").ligerGrid({
                columns: [
                { display: '门店编号', 		name: 'appchangecompid', 		width:100	,align: 'left' , totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return '总记录数: '+suminf.count;
                        },
						align: 'right'
                    }
                },
                { display: '门店名称', 		name: 'appchangecompname', 		width:100 	,align: 'left'},
	            { display: '部门',   		name: 'afterdepartment', 		data: departBMZLData,	width:100 	,align: 'left',
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
	            { display: '职位',   		name: 'afterpostation', 			data: postionGZGWData, 	 	width:100	,align: 'left' ,
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
	            { display: '工号', 			name: 'changestaffno', 				width:80	,align: 'left' },
                { display: '姓名', 			name: 'changestaffname',  			width:120	,align: 'left' },
	            { display: '手机号码',   		name: 'staffphone',  				width:100	,align: 'left'},
	            { display: '身份证号码',   		name: 'staffpcid',  				width:160	,align: 'left'},
	            { display: '档案号',   		name: 'fillno',  				width:80	,align: 'left'},
	          	{ display: '入职日期',   		name: 'arrivaldate',  		width:80	,align: 'left'},
	            { display: '离职日期',   		name: 'validatestartdate',  		width:80	,align: 'left'},
	            { display: '社保',   		name: 'socialsecurity',  		width:80	,align: 'left'},
	            { display: '离职类型',   		name: 'leaveltypeText',  		width:80	,align: 'left'},
	            { display: '备注',   		name: 'remark',  			width:120	,align: 'left'}
	            ],  pageSize:25, 
                data: null,      
                width: centerWidth-100,
                height:600,
                enabledEdit: false,  checkbox: false,rownumbers: false,usePager: false
               
            });
            
           commoninfodivPersonDate3=$("#commoninfodivPersonDate3").ligerGrid({
                columns: [
                { display: '门店编号', 		name: 'appchangecompid', 				width:90	,align: 'left' , totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return '总记录数: '+suminf.count;
                        },
						align: 'right'
                    }
                },
                { display: '门店名称', 		name: 'appchangecompname', 				width:80 	,align: 'left'},
                { display: '原部门',   		name: 'beforedepartment', 		data: departBMZLData,	width:100 	,align: 'left',
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
	            { display: '新部门',   		name: 'afterdepartment', 		data: departBMZLData,	width:100 	,align: 'left',
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
	            { display: '原职位',   		name: 'beforepostation', 		data: postionGZGWData, 	 	width:100	,align: 'left' ,
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
	            { display: '新职位',   		name: 'afterpostation', 		data: postionGZGWData, 	 	width:100	,align: 'left' ,
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
	            { display: '原工号', 			name: 'changestaffno', 			width:80	,align: 'left' },
                { display: '新工号', 			name: 'afterstaffno', 				width:80	,align: 'left' },
                { display: '姓名', 			name: 'changestaffname',  			width:80	,align: 'left' },
                { display: '身份证号',   		name: 'staffpcid',  			width:160	,align: 'left'},
	            { display: '手机号码',   		name: 'staffphone',  			width:100	,align: 'left'},
	            { display: '生效日期',   		name: 'validatestartdate',  	width:80	,align: 'left'},
	            { display: '备注',   		name: 'remark',  				width:120	,align: 'left'}
	            ],  pageSize:25, 
                data: null,      
                width: centerWidth-100,
                height:600,
                enabledEdit: false,  checkbox: false,rownumbers: false,usePager: false
               
            });
            
            commoninfodivPersonDate4=$("#commoninfodivPersonDate4").ligerGrid({
                columns: [
                { display: '原门店',   		name: 'appchangecompid', 			width:90 	,align: 'left', totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return '总记录数: '+suminf.count;
                        },
						align: 'right'
                    }
                },
                { display: '门店名称', 		name: 'appchangecompname', 			width:70 	,align: 'left'},
	            { display: '新门店',   		name: 'aftercompid', 				width:60 	,align: 'left'},
	            { display: '门店名称', 		name: 'aftercompname', 				width:70 	,align: 'left'},
	            { display: '原部门',   		name: 'beforedepartment', 			data: departBMZLData,	width:100 	,align: 'left',
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
	            { display: '新部门',   		name: 'afterdepartment', 		data: departBMZLData,	width:100 	,align: 'left',
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
	            { display: '原职位',   		name: 'beforepostation', 		data: postionGZGWData, 	 	width:100	,align: 'left' ,
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
	            { display: '新职位',   		name: 'afterpostation', 		data: postionGZGWData, 	 	width:100	,align: 'left' ,
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
	            { display: '原工号', 			name: 'changestaffno', 			width:80	,align: 'left' },
                { display: '新工号', 			name: 'afterstaffno', 				width:80	,align: 'left' },
                { display: '姓名', 			name: 'changestaffname',  			width:80	,align: 'left' },
                { display: '手机号码',   		name: 'staffphone',  			width:100	,align: 'left'},
	            { display: '生效日期',   		name: 'validatestartdate',  	width:80	,align: 'left'},
	            { display: '备注',   		name: 'remark',  				width:120	,align: 'left'}
	            ],  pageSize:25, 
                data: null,      
                width: centerWidth-100,
                height:600,
                enabledEdit: false,  checkbox: false,rownumbers: false,usePager: false
               
            });
            commoninfodivPersonDate5=$("#commoninfodivPersonDate5").ligerGrid({
                columns: [
               	{ display: '新门店',   		name: 'aftercompid', 				width:90 	,align: 'left', totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return '总记录数: '+suminf.count;
                        },
						align: 'right'
                    }
                 },
	            { display: '门店名称', 		name: 'aftercompname', 				width:70 	,align: 'left'},
	           
	            { display: '新部门',   		name: 'afterdepartment', 		data: departBMZLData,	width:100 	,align: 'left',
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
	           
	            { display: '新职位',   		name: 'afterpostation', 		data: postionGZGWData, 	 	width:100	,align: 'left' ,
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
	            { display: '新工号', 			name: 'afterstaffno', 				width:80	,align: 'left' },
                { display: '姓名', 			name: 'changestaffname',  			width:80	,align: 'left' },
                { display: '身份证号',   		name: 'staffpcid',  			width:160	,align: 'left'},
	          	{ display: '手机号码',   		name: 'staffphone',  			width:100	,align: 'left'},
	            { display: '生效日期',   		name: 'validatestartdate',  	width:80	,align: 'left'},
	            { display: '备注',   		name: 'remark',  				width:120	,align: 'left'}
	            ],  pageSize:25, 
                data: null,      
                width: centerWidth-100,
                height:600,
                enabledEdit: false,  checkbox: false,rownumbers: false,usePager: false
               
            });
            commoninfodivPersonDate6=$("#commoninfodivPersonDate6").ligerGrid({
                columns: [
                { display: '门店编号', 		name: 'appchangecompid', 			width:100	,align: 'left' , totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return '总记录数: '+suminf.count;
                        },
						align: 'right'
                    }
                },
                { display: '门店名称', 		name: 'appchangecompname', 		width:100 	,align: 'left'},
	            { display: '原部门',   		name: 'beforedepartment', 		data: departBMZLData,	width:100 	,align: 'left',
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
	            { display: '原职位',   		name: 'beforepostation', 		data: postionGZGWData, 	 	width:100	,align: 'left' ,
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
	            { display: '工号', 			name: 'changestaffno', 			width:80	,align: 'left' },
                { display: '姓名', 			name: 'changestaffname',  		width:80	,align: 'left' },
	            { display: '手机号码',   		name: 'staffphone',  		width:100	,align: 'left'},
	            { display: '请假开始日期',   	name: 'validatestartdate',  			width:140	,align: 'left'},
	            { display: '请假结束日期',   	name: 'validateenddate',  		width:80	,align: 'left'},
	            { display: '备注',   		name: 'remark',  			width:120	,align: 'left'}
	            ],  pageSize:25, 
                data: null,      
                width: centerWidth-100,
                height:600,
                enabledEdit: false,  checkbox: false,rownumbers: false,usePager: false
               
            });
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
	        var today = new Date();
			var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
	
			var today = intYear.toString()+"-"+fullStr(intMonth.toString());
			document.getElementById("strFromDate").value=today;
            $("#pageloading").hide(); 
            addtrade1();
            addtrade2();
            addtrade3();
            addtrade4();
            addtrade5();
            addtrade6();
   		}catch(e){alert(e.message);}
    });
    
    function  addtrade1()
    {
        	var row = commoninfodivPersonDate1.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivPersonDate1.addRow({ 
				            }, row, false);
    }
    function  addtrade2()
    {
        	var row = commoninfodivPersonDate2.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivPersonDate2.addRow({ 
				            }, row, false);
    }
    function  addtrade3()
    {
        	var row = commoninfodivPersonDate3.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivPersonDate3.addRow({ 
				            }, row, false);
    }
    function  addtrade4()
    {
        	var row = commoninfodivPersonDate4.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivPersonDate4.addRow({ 
				            }, row, false);
    }
    function  addtrade5()
    {
        	var row = commoninfodivPersonDate5.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivPersonDate5.addRow({ 
				            }, row, false);
    }
    function  addtrade6()
    {
        	var row = commoninfodivPersonDate6.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivPersonDate6.addRow({ 
				            }, row, false);
    }
    //登记储值卡
    function loadDataSet()
    {	
   
    	var strCurCompId=getCurOrgFromSearchBar();

    	
    	var params = "strCurCompId="+strCurCompId;	
    	params=params+"&strMonth="+document.getElementById("strFromDate").value;
    	var requestUrl ="pc006/loadDataSet.action"; 
		var responseMethod="loadDataSetMessage";		
		 showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    
    }
    
    function loadDataSetMessage(request)
		{
			var responsetext = eval("(" + request.responseText + ")");
			if(responsetext.lsDataSetA!=null && responsetext.lsDataSetA.length>0)
           	{
            		commoninfodivPersonDate1.options.data=$.extend(true, {},{Rows: responsetext.lsDataSetA,Total: responsetext.lsDataSetA.length});
            		commoninfodivPersonDate1.loadData(true);
            }
			else
			{
				 	commoninfodivPersonDate1.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivPersonDate1.loadData(true);
	            	addtrade1(); 
			}
			if(responsetext.lsDataSetB!=null && responsetext.lsDataSetB.length>0)
           	{
            		commoninfodivPersonDate2.options.data=$.extend(true, {},{Rows: responsetext.lsDataSetB,Total: responsetext.lsDataSetB.length});
            		commoninfodivPersonDate2.loadData(true);
            }
			else
			{
				 	commoninfodivPersonDate2.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivPersonDate2.loadData(true);
	            	addtrade2(); 
			}
			if(responsetext.lsDataSetC!=null && responsetext.lsDataSetC.length>0)
           	{
            		commoninfodivPersonDate3.options.data=$.extend(true, {},{Rows: responsetext.lsDataSetC,Total: responsetext.lsDataSetC.length});
            		commoninfodivPersonDate3.loadData(true);
            }
			else
			{
				 	commoninfodivPersonDate3.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivPersonDate3.loadData(true);
	            	addtrade3(); 
			}
			if(responsetext.lsDataSetD!=null && responsetext.lsDataSetD.length>0)
           	{
            		commoninfodivPersonDate4.options.data=$.extend(true, {},{Rows: responsetext.lsDataSetD,Total: responsetext.lsDataSetD.length});
            		commoninfodivPersonDate4.loadData(true);
            }
			else
			{
				 	commoninfodivPersonDate4.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivPersonDate4.loadData(true);
	            	addtrade4(); 
			}
			if(responsetext.lsDataSetE!=null && responsetext.lsDataSetE.length>0)
           	{
            		commoninfodivPersonDate5.options.data=$.extend(true, {},{Rows: responsetext.lsDataSetE,Total: responsetext.lsDataSetE.length});
            		commoninfodivPersonDate5.loadData(true);
            }
			else
			{
				 	commoninfodivPersonDate5.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivPersonDate5.loadData(true);
	            	addtrade4(); 
			}
			if(responsetext.lsDataSetF!=null && responsetext.lsDataSetF.length>0)
           	{
            		commoninfodivPersonDate6.options.data=$.extend(true, {},{Rows: responsetext.lsDataSetF,Total: responsetext.lsDataSetF.length});
            		commoninfodivPersonDate6.loadData(true);
            }
			else
			{
				 	commoninfodivPersonDate6.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivPersonDate6.loadData(true);
	            	addtrade4(); 
			}
			showDialogmanager.close();
		}

		
		function loadDataSetExcel()
		{
			var strCurCompId=getCurOrgFromSearchBar();
    		var params = "strCurCompId="+strCurCompId;	
    		params=params+"&strMonth="+document.getElementById("strFromDate").value;
			var excelDialog=$.ligerDialog.open({url: contextURL+'/pc006/loadpc006Excel.action?'+params });
    		setTimeout(function () { excelDialog.close(); }, 10000);
		}
		