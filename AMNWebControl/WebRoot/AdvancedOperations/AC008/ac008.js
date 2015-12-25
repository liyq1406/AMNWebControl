
   	var compTreeManager;
   	var compTree;
   	var commoninfodivdetial_pay=null;  //支付方式
   	var commoninfodivdetial=null; //项目明细
   	var commoninfodivmaster= null;
   	var strCurCompId="";
   	var strCurBillId="";
   	var nBillType=0;
   	var chooseSexData = [{ choose: 1, text: '男' }, { choose: 0, text: '女'}];
   	var ac008layout=null;
   	var fromValidate=null;
   	var firstsaleridmanager = null;
   	var secondsaleridmanager = null;
   	var thirdsalerinidmanager = null;
    var fourthsaleridmanager = null;
   	var fifthsaleridmanager = null;
 
   	var sixthsaleridmanager = null;
   	var seventhsaleridmanager = null;
   	var eighthsaleridmanager = null;
   	var ninthsaleridmanager = null;
    var tenthsaleridmanager = null;
   	var lsProjectinfo = null;
   	var pageState=3;
   	var curEmpManger=null;
   	var lsStaffinfo=null;
   	var AC008Tab=null;
   	//var paymodeChangeData=JSON.parse(parent.loadCommonControlDate_selectdree("ZFFS",0));
   	var lcTypeChangeData=JSON.parse(parent.loadCommonControlDate_selectdree("JGCW",0));
   	var servicetypeChangeData=JSON.parse(parent.loadCommonControlDate_select("FWLB",0));
   	var curProRecord = null;
   	var curMasterRecord = null;
   	var showDialogmanager=null;
   	var commoninfodivdetial_service =null;
   	var commoninfodivdetial_goods=null;
   	var postState=0;
   	var lsBillHistoryInfo=null;
   	var lsCostHistoryInfo=null;
   	var lsProHistoryInfo=null;
   	var commoninfodivdetial_oldcustomer=null;
   	var showDialogRe=null;
    $(function ()
   	{
	   try
	   {
	   		  //布局
            ac008layout= $("#ac008layout").ligerLayout({ leftWidth: 270,rightWidth: 250,  allowBottomResize: false, allowLeftResize: false,isLeftCollapse:true });
            //门店树形结构
   			var height = $(".l-layout-center").height();
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
          	$("#pageloading").hide(); 
          	/* $("#editCurRootInfo").ligerButton(
	         {
	             text: '修改业绩分享', width: 180,
		         click: function ()
		         {
		             editCurRootInfo();
		         }
	         });*/
            
          	commoninfodivdetial_pay=$("#commoninfodivdetial_pay").ligerGrid({
                columns: [
                { display: '支付方式', 	name: 'paymode',  		width:140,align: 'center' ,
	             	editor: { type: 'select', data: null, valueField: 'choose',selectBoxWidth:105},
		            	render: function (item)
		              	{
		              		var lsZw=parent.gainCommonInfoByCode("ZFFS",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.paymode)
								{	
									return lsZw[i].parentcodevalue;								
							    }
							}
		                    return '';
		                } 
		        },
                { display: '支付金额', 	name: 'payamt', 		width:80,align: 'right'},
                { display: '支付备注', 	name: 'payremark', 		width:200,align: 'left',editor: { type: 'text'}}
	            ],  pageSize:25, 
                data:{Rows: null,Total:0},      
              	width: 500,
                height:'170',
                enabledEdit: true, checkbox: false,
                rownumbers: false,usePager: false
            });
     		commoninfodivmaster=$("#commoninfodivmaster").ligerGrid({
                columns: [
                { display: '业务类型', 	name: 'billTypeName', 		width:80,align: 'left'},
                { display: '开单数', 		name: 'billCount', 			width:55,align: 'right'},
                { name: 'billType', 	hide:true,	width:1}
               ],  pageSize:25, 
                data:{Rows: null,Total:0},      
              	width: 155,
                height:height-20,
                enabledEdit: true, checkbox: false,
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                  	loadSelecMasterData(data, rowindex, rowobj);
                }
            });
            commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
                columns: [
                	{ display: '单据编号', 	name: 'strBillId', 			width:110,align: 'left',
                		render: function (row) {  
     					var html =row.strBillId; 
     					if(checkNull(row.backflag)==1)
     						 html= "<span style='color:blue'>"+row.strBillId+"</span>";
     					return html;  
 					}  
 					},
                	{ display: '会员编号', 	name: 'strCardNo', 			width:100,align: 'left'},
                 	{ display: '会员姓名', 	name: 'strMemberName', 			width:80,align: 'left'},
                 	{ display: '历史', isSort: false, width: 40, render: function (rowdata, rowindex, value)
		                {
		                		var h =  "<a href=\"javascript:showBillHistory('"+checkNull(rowdata.strCompId)+"','"+checkNull(rowdata.strBillId)+"','"+checkNull(rowdata.billType)+"','"+checkNull(rowdata.changeseqno)+"' )\"><img src="+contextURL+"/common/ligerui/ligerUI/skins/icons/pager.gif /></a> ";
		                  		return h;
		                }
	                },
                 	{ hide:true, 	name: 'changeseqno',width:1},
                 	{ hide:true, 	name: 'billdate',width:1},
                 	{ hide:true, 	name: 'backflag',width:1},
                 	{ hidd:true,	name: 'iteminid'},
                 	{ hide:true, 	name: 'billinsertype',width:1},//3个字段微信或支付宝用
                 	{ hide:true, 	name: 'firstsaleamt',width:1},
                 	{ hide:true, 	name: 'strPrjNo',width:1},
                 	{ hide:true, 	name: 'strPrjName',width:1}
               ],  pageSize:25, 
                data:{Rows: null,Total:0},      
              	width: 350,
                height:height-20,
                enabledEdit: true, checkbox: false,
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                	curMasterRecord=data;
                    loadSelecDetialData(data, rowindex, rowobj);
                }
            });
            if(document.getElementById("paramSp112").value=="0")
          	{
            	commoninfodivdetial_service=$("#commoninfodivdetial_service").ligerGrid({
                    columns: [
                    { display: '项目名称' ,	name: 'csitemname', width:170,align: 'center'},
                    { display: '支付', 	name: 'cspaymode', 			width:70,
    	             	editor: { type: 'select', data: null, valueField: 'choose',selectBoxWidth:105,onChanged : validatePaycode},
    		            	render: function (item)
    		              	{
    		              		var lsZw=parent.gainCommonInfoByCode("ZFFS",0);
    		              		for(var i=0;i<lsZw.length;i++)
    							{
    								if(lsZw[i].bparentcodekey==item.cspaymode)
    								{	
    									if(checkNull(item.csitemno)!=""
    								 	&& document.getElementById("paramSp097").value=="1"
    								 	&& (checkNull(item.csitemno).indexOf("498")==0
    								 	|| checkNull(item.csitemno).indexOf("490")==0
    								 	|| checkNull(item.csitemno).indexOf("46")==0
    								 	))
    								    {
    									    column=commoninfodivdetial_service.columns[5];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    										column=commoninfodivdetial_service.columns[7];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    								    }	
    									return "<span style='color:blue'>"+lsZw[i].parentcodevalue+"</span>";								
    							    }
    							}
    		                    return '';
    		                } 
    		        },
    		        { display: '金额' ,	name: 'csitemamt', width:60,align: 'center'},
                    { display: '大工', 		name: 'csfirstsaler', 	 width:80,
    	            	editor: { type: 'select', data: null, 	 url:'loadAutoStaff',autocomplete: true, valueField: 'choose',selectBoxWidth:150,onChanged : validateFristSaleType,alwayShowInDown:true},
    	            	render: function (item)
    	              	{	
    	              		for(var i=0;i<lsStaffinfo.length;i++)
    						{	
    								if(lsStaffinfo[i].bstaffno==checkNull(item.csfirstsaler))
    								{
    									if(checkNull(item.csitemno)!="" 
    								 	&& document.getElementById("paramSp097").value=="1"
    								 	&& (checkNull(item.csitemno).indexOf("498")==0
    								 	|| checkNull(item.csitemno).indexOf("490")==0
    								 	|| checkNull(item.csitemno).indexOf("46")==0
    								 	))
    								    {
    									    column=commoninfodivdetial_service.columns[5];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    										column=commoninfodivdetial_service.columns[7];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    								    }	
    									return "<span style='color:blue'>"+lsStaffinfo[i].staffname+"</span>";
    								}
    						}
    	                    return '';
    	                } 
    	            },
    	            { display: '类型', 		name: 'csfirsttype', 		width:60,
    	             	/*editor: { type: 'select', data: servicetypeChangeData, valueField: 'choose',selectBoxHeight:'120'},*/
    		            	render: function (item)
    		              	{
    		              		var lsZw=parent.gainCommonInfoByCode("FWLB",0);
    		              		for(var i=0;i<lsZw.length;i++)
    							{
    								if(lsZw[i].bparentcodekey==item.csfirsttype)
    								{	
    									var ccolumn=null;
    									if(checkNull(item.cspaymode)=="4"
    									|| checkNull(item.cspaymode)=="9"
    									|| checkNull(item.cspaymode)=="7"
    									|| checkNull(item.cspaymode)=="A")
    									{
    										ccolumn=commoninfodivdetial_service.columns[1];
    										commoninfodivdetial_service.setCellEditing(item, ccolumn, true);
    									}
    									if(checkNull(item.csitemno)!="" 
    								 	&& document.getElementById("paramSp097").value=="1"
    								 	&& (checkNull(item.csitemno).indexOf("498")==0
    								 	|| checkNull(item.csitemno).indexOf("490")==0
    								 	|| checkNull(item.csitemno).indexOf("46")==0
    								 	))
    								    {
    									    column=commoninfodivdetial_service.columns[5];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    										column=commoninfodivdetial_service.columns[7];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    								    }	
    									
    									return "<span style='color:blue'>"+lsZw[i].parentcodevalue+"</span>";								
    							    }
    							}
    		                    return '';
    		                }
    	            },
    	            { display: '中工', 		name: 'cssecondsaler',			width:80,
    	            	editor: { type: 'select', data: null,  url:'loadAutoStaff',autocomplete: true,valueField: 'choose',selectBoxWidth:150,onChanged : validateSecondSaleType,alwayShowInDown:true},
    	            	render: function (item)
    	              	{
    	              		for(var i=0;i<lsStaffinfo.length;i++)
    						{	
    								if(lsStaffinfo[i].bstaffno==checkNull(item.cssecondsaler))
    								{
    									if(checkNull(item.csitemno)!="" 
    								 	&& document.getElementById("paramSp097").value=="1"
    								 	&& (checkNull(item.csitemno).indexOf("498")==0
    								 	|| checkNull(item.csitemno).indexOf("490")==0
    								 	|| checkNull(item.csitemno).indexOf("46")==0
    								 	))
    								    {
    									    column=commoninfodivdetial_service.columns[5];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    										column=commoninfodivdetial_service.columns[7];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    								    }	
    									return "<span style='color:blue'>"+lsStaffinfo[i].staffname+"</span>";
    								}
    						}
    	                    return '';
    	                } 
    	            },
    	            { display: '类型', 		name: 'cssecondtype',width:40,
    	             	/*editor: {type:'select', data: servicetypeChangeData, valueField: 'choose',selectBoxHeight:'120'},*/ 
    		            	render: function (item)
    		              	{
    		              		var lsZw=parent.gainCommonInfoByCode("FWLB",0);
    		              		for(var i=0;i<lsZw.length;i++)
    							{
    								if(lsZw[i].bparentcodekey==item.cssecondtype)
    								{	
    									var ccolumn=null;
    									if(checkNull(item.cspaymode)=="4"
    									|| checkNull(item.cspaymode)=="9"
    									|| checkNull(item.cspaymode)=="7"
    									|| checkNull(item.cspaymode)=="A")
    									{
    										ccolumn=commoninfodivdetial_service.columns[1];
    										commoninfodivdetial_service.setCellEditing(item, ccolumn, true);
    									}
    									if(checkNull(item.csitemno)!="" 
    								 	&& document.getElementById("paramSp097").value=="1"
    								 	&& (checkNull(item.csitemno).indexOf("498")==0
    								 	|| checkNull(item.csitemno).indexOf("490")==0
    								 	|| checkNull(item.csitemno).indexOf("46")==0
    								 	))
    								    {
    									    column=commoninfodivdetial_service.columns[5];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    										column=commoninfodivdetial_service.columns[7];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    								    }
    									return "<span style='color:blue'>"+lsZw[i].parentcodevalue+"</span>";								
    							    }
    							}
    		                    return '';
    		                }
    	            },
    	            { display: '小工', 		name: 'csthirdsaler',		width:80,
    	            	editor: { type: 'select', data: null,	url:'loadAutoStaff',autocomplete: true, valueField: 'choose',onChanged : validateThirdSaleType,selectBoxWidth:150,alwayShowInDown:true},
    	            	render: function (item)
    	              	{
    	              		for(var i=0;i<lsStaffinfo.length;i++)
    						{	
    								if(lsStaffinfo[i].bstaffno==checkNull(item.csthirdsaler))
    								{
    									if(checkNull(item.csitemno)!="" 
    								 	&& document.getElementById("paramSp097").value=="1"
    								 	&& (checkNull(item.csitemno).indexOf("498")==0
    								 	|| checkNull(item.csitemno).indexOf("490")==0
    								 	|| checkNull(item.csitemno).indexOf("46")==0
    								 	))
    								    {
    									    column=commoninfodivdetial_service.columns[5];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    										column=commoninfodivdetial_service.columns[7];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    								    }	
    									return "<span style='color:blue'>"+lsStaffinfo[i].staffname+"</span>";
    								}
    						}
    	                    return '';
    	                } 
    	            },
    	           { display: '类型', 		name: 'csthirdtype', 		width:40,
    	             	/*editor: { type: 'select', data: servicetypeChangeData, valueField: 'choose',selectBoxHeight:'120'},*/
    		            	render: function (item)
    		              	{
    		              		var lsZw=parent.gainCommonInfoByCode("FWLB",0);
    		              		for(var i=0;i<lsZw.length;i++)
    							{
    								if(lsZw[i].bparentcodekey==item.csthirdtype)
    								{	
    									var ccolumn=null;
    									if(checkNull(item.cspaymode)=="4"
    									|| checkNull(item.cspaymode)=="9"
    									|| checkNull(item.cspaymode)=="7"
    									|| checkNull(item.cspaymode)=="A")
    									{
    										ccolumn=commoninfodivdetial_service.columns[1];
    										commoninfodivdetial_service.setCellEditing(item, ccolumn, true);
    									}
    									if(checkNull(item.csitemno)!="" 
    								 	&& document.getElementById("paramSp097").value=="1"
    								 	&& (checkNull(item.csitemno).indexOf("498")==0
    								 	|| checkNull(item.csitemno).indexOf("490")==0
    								 	|| checkNull(item.csitemno).indexOf("46")==0
    								 	))
    								    {
    									    column=commoninfodivdetial_service.columns[5];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    										column=commoninfodivdetial_service.columns[7];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    								    }
    									return "<span style='color:blue'>"+lsZw[i].parentcodevalue+"</span>";								
    							    }
    							}
    		                    return '';
    		                }
    	            },
    	            { display: '推首席总监介绍人',   name: 'hairRecommendEmpId',width:60,
    	            	editor: { type: 'select', data: null, 	 url:'loadAutoStaff',autocomplete: true, valueField: 'choose',selectBoxWidth:150,onChanged : validateSX,alwayShowInDown:true},
	    	            render: function (item)
		              	{	
		              		for(var i=0;i<lsStaffinfo.length;i++)
							{	
									if(lsStaffinfo[i].bstaffno==checkNull(item.hairRecommendEmpId))
									{
										return "<span style='color:blue'>"+lsStaffinfo[i].staffname+"</span>";
									}
							}
		                    return '';
		                } 
    	            },
    	            { hide:true,		name: 'bcsinfotype',width:1},
    	            { hide:true,		name: 'bcsseqno',width:1},
                    { hide:true,		name: 'goodsbarno',width:1},
    	            { 		name: 'csitemno', 		hide:true,width:1}
                    ],  pageSize:25, 
                    data:{Rows: null,Total:0},      
                    width: 700,
                    height:'460',
                    enabledEdit: true, clickToEdit: true,usePager: false,
                    onSelectRow : function (data, rowindex, rowobj)
                    {
                    	curEmpManger=null;
                        curRecord = data;
                    }
                });
          	}
            else
            {
            	commoninfodivdetial_service=$("#commoninfodivdetial_service").ligerGrid({
                    columns: [
                    { display: '项目名称' ,	name: 'csitemname', width:170,align: 'center'},
                    { display: '支付', 	name: 'cspaymode', 			width:70,
    	             	editor: { type: 'select', data: null, valueField: 'choose',selectBoxWidth:105,onChanged : validatePaycode},
    		            	render: function (item)
    		              	{
    		              		var lsZw=parent.gainCommonInfoByCode("ZFFS",0);
    		              		for(var i=0;i<lsZw.length;i++)
    							{
    								if(lsZw[i].bparentcodekey==item.cspaymode)
    								{	
    									if(checkNull(item.csitemno)!=""
    								 	&& document.getElementById("paramSp097").value=="1"
    								 	&& (checkNull(item.csitemno).indexOf("498")==0
    								 	|| checkNull(item.csitemno).indexOf("490")==0
    								 	|| checkNull(item.csitemno).indexOf("46")==0
    								 	))
    								    {
    									    column=commoninfodivdetial_service.columns[5];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    										column=commoninfodivdetial_service.columns[7];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    								    }	
    									return "<span style='color:blue'>"+lsZw[i].parentcodevalue+"</span>";								
    							    }
    							}
    		                    return '';
    		                } 
    		        },
    		        { display: '金额' ,	name: 'csitemamt', width:60,align: 'center'},
                    { display: '大工', 		name: 'csfirstsaler', 	 width:80,
    	            	editor: { type: 'select', data: null, 	 url:'loadAutoStaff',autocomplete: true, valueField: 'choose',selectBoxWidth:150,onChanged : validateFristSaleType,alwayShowInDown:true},
    	            	render: function (item)
    	              	{	
    	              		for(var i=0;i<lsStaffinfo.length;i++)
    						{	
    								if(lsStaffinfo[i].bstaffno==checkNull(item.csfirstsaler))
    								{
    									if(checkNull(item.csitemno)!="" 
    								 	&& document.getElementById("paramSp097").value=="1"
    								 	&& (checkNull(item.csitemno).indexOf("498")==0
    								 	|| checkNull(item.csitemno).indexOf("490")==0
    								 	|| checkNull(item.csitemno).indexOf("46")==0
    								 	))
    								    {
    									    column=commoninfodivdetial_service.columns[5];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    										column=commoninfodivdetial_service.columns[7];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    								    }	
    									return "<span style='color:blue'>"+lsStaffinfo[i].staffname+"</span>";
    								}
    						}
    	                    return '';
    	                } 
    	            },
    	            { display: '类型', 		name: 'csfirsttype', 		width:60 ,
    	             	editor: { type: 'select', data: servicetypeChangeData, valueField: 'choose',selectBoxHeight:'120'},
    		            	render: function (item)
    		              	{
    		              		var lsZw=parent.gainCommonInfoByCode("FWLB",0);
    		              		for(var i=0;i<lsZw.length;i++)
    							{
    								if(lsZw[i].bparentcodekey==item.csfirsttype)
    								{	
    									var ccolumn=null;
    									if(checkNull(item.cspaymode)=="4"
    									|| checkNull(item.cspaymode)=="9"
    									|| checkNull(item.cspaymode)=="7"
    									|| checkNull(item.cspaymode)=="A")
    									{
    										ccolumn=commoninfodivdetial_service.columns[1];
    										commoninfodivdetial_service.setCellEditing(item, ccolumn, true);
    									}
    									if(checkNull(item.csitemno)!="" 
    								 	&& document.getElementById("paramSp097").value=="1"
    								 	&& (checkNull(item.csitemno).indexOf("498")==0
    								 	|| checkNull(item.csitemno).indexOf("490")==0
    								 	|| checkNull(item.csitemno).indexOf("46")==0
    								 	))
    								    {
    									    column=commoninfodivdetial_service.columns[5];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    										column=commoninfodivdetial_service.columns[7];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    								    }	
    									return "<span style='color:blue'>"+lsZw[i].parentcodevalue+"</span>";								
    							    }
    							}
    		                    return '';
    		                } 
    	            },
    	            { display: '中工', 		name: 'cssecondsaler',			width:80,
    	            	editor: { type: 'select', data: null,  url:'loadAutoStaff',autocomplete: true,valueField: 'choose',selectBoxWidth:150,onChanged : validateSecondSaleType,alwayShowInDown:true},
    	            	render: function (item)
    	              	{
    	              		for(var i=0;i<lsStaffinfo.length;i++)
    						{	
    								if(lsStaffinfo[i].bstaffno==checkNull(item.cssecondsaler))
    								{
    									if(checkNull(item.csitemno)!="" 
    								 	&& document.getElementById("paramSp097").value=="1"
    								 	&& (checkNull(item.csitemno).indexOf("498")==0
    								 	|| checkNull(item.csitemno).indexOf("490")==0
    								 	|| checkNull(item.csitemno).indexOf("46")==0
    								 	))
    								    {
    									    column=commoninfodivdetial_service.columns[5];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    										column=commoninfodivdetial_service.columns[7];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    								    }	
    									return "<span style='color:blue'>"+lsStaffinfo[i].staffname+"</span>";
    								}
    						}
    	                    return '';
    	                } 
    	            },
    	            { display: '类型', 		name: 'cssecondtype', 		width:40 ,
    	             	editor: { type: 'select', data: servicetypeChangeData, valueField: 'choose',selectBoxHeight:'120'},
    		            	render: function (item)
    		              	{
    		              		var lsZw=parent.gainCommonInfoByCode("FWLB",0);
    		              		for(var i=0;i<lsZw.length;i++)
    							{
    								if(lsZw[i].bparentcodekey==item.cssecondtype)
    								{	
    									var ccolumn=null;
    									if(checkNull(item.cspaymode)=="4"
    									|| checkNull(item.cspaymode)=="9"
    									|| checkNull(item.cspaymode)=="7"
    									|| checkNull(item.cspaymode)=="A")
    									{
    										ccolumn=commoninfodivdetial_service.columns[1];
    										commoninfodivdetial_service.setCellEditing(item, ccolumn, true);
    									}
    									if(checkNull(item.csitemno)!="" 
    								 	&& document.getElementById("paramSp097").value=="1"
    								 	&& (checkNull(item.csitemno).indexOf("498")==0
    								 	|| checkNull(item.csitemno).indexOf("490")==0
    								 	|| checkNull(item.csitemno).indexOf("46")==0
    								 	))
    								    {
    									    column=commoninfodivdetial_service.columns[5];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    										column=commoninfodivdetial_service.columns[7];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    								    }
    									return "<span style='color:blue'>"+lsZw[i].parentcodevalue+"</span>";								
    							    }
    							}
    		                    return '';
    		                } 
    	            },
    	            { display: '小工', 		name: 'csthirdsaler',		width:80,
    	            	editor: { type: 'select', data: null,	url:'loadAutoStaff',autocomplete: true, valueField: 'choose',onChanged : validateThirdSaleType,selectBoxWidth:150,alwayShowInDown:true},
    	            	render: function (item)
    	              	{
    	              		for(var i=0;i<lsStaffinfo.length;i++)
    						{	
    								if(lsStaffinfo[i].bstaffno==checkNull(item.csthirdsaler))
    								{
    									if(checkNull(item.csitemno)!="" 
    								 	&& document.getElementById("paramSp097").value=="1"
    								 	&& (checkNull(item.csitemno).indexOf("498")==0
    								 	|| checkNull(item.csitemno).indexOf("490")==0
    								 	|| checkNull(item.csitemno).indexOf("46")==0
    								 	))
    								    {
    									    column=commoninfodivdetial_service.columns[5];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    										column=commoninfodivdetial_service.columns[7];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    								    }	
    									return "<span style='color:blue'>"+lsStaffinfo[i].staffname+"</span>";
    								}
    						}
    	                    return '';
    	                } 
    	            },
    	           { display: '类型', 		name: 'csthirdtype', 		width:60 ,
    	             	editor: { type: 'select', data: servicetypeChangeData, valueField: 'choose',selectBoxHeight:'120'},
    		            	render: function (item)
    		              	{
    		              		var lsZw=parent.gainCommonInfoByCode("FWLB",0);
    		              		for(var i=0;i<lsZw.length;i++)
    							{
    								if(lsZw[i].bparentcodekey==item.csthirdtype)
    								{	
    									var ccolumn=null;
    									if(checkNull(item.cspaymode)=="4"
    									|| checkNull(item.cspaymode)=="9"
    									|| checkNull(item.cspaymode)=="7"
    									|| checkNull(item.cspaymode)=="A")
    									{
    										ccolumn=commoninfodivdetial_service.columns[1];
    										commoninfodivdetial_service.setCellEditing(item, ccolumn, true);
    									}
    									if(checkNull(item.csitemno)!="" 
    								 	&& document.getElementById("paramSp097").value=="1"
    								 	&& (checkNull(item.csitemno).indexOf("498")==0
    								 	|| checkNull(item.csitemno).indexOf("490")==0
    								 	|| checkNull(item.csitemno).indexOf("46")==0
    								 	))
    								    {
    									    column=commoninfodivdetial_service.columns[5];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    										column=commoninfodivdetial_service.columns[7];
    										commoninfodivdetial_service.setCellEditing(item, column, true);
    								    }
    									return "<span style='color:blue'>"+lsZw[i].parentcodevalue+"</span>";								
    							    }
    							}
    		                    return '';
    		                } 
    	            },
    	            {display: '推首席总监介绍人',   name: 'hairRecommendEmpId',width:60,
    	            	editor: { type: 'select', data: null, 	 url:'loadAutoStaff',autocomplete: true, valueField: 'choose',selectBoxWidth:150,onChanged : validateSX,alwayShowInDown:true},
	    	            render: function (item)
		              	{	
		              		for(var i=0;i<lsStaffinfo.length;i++)
							{	
									if(lsStaffinfo[i].bstaffno==checkNull(item.hairRecommendEmpId))
									{
										return "<span style='color:blue'>"+lsStaffinfo[i].staffname+"</span>";
									}
							}
		                    return '';
		                } 
    	            },
    	            { hide:true,		name: 'bcsinfotype',width:1},
    	            { hide:true,		name: 'bcsseqno',width:1},
                    { hide:true,		name: 'goodsbarno',width:1},
                    { hide:true,        name: 'costpricetype',width:1},
    	            { 		name: 'csitemno', 		hide:true,width:1}
                    ],  pageSize:25, 
                    data:{Rows: null,Total:0},      
                    width: 700,
                    height:'460',
                    enabledEdit: true, clickToEdit: true,usePager: false,
                    onSelectRow : function (data, rowindex, rowobj)
                    {
                    	curEmpManger=null;
                        curRecord = data;
                    }
                });
            }
            
            
            
            
            commoninfodivdetial_goods=$("#commoninfodivdetial_goods").ligerGrid({
                columns: [
                { display: '产品名称' ,	name: 'csitemname', width:170,align: 'center'},
                { display: '支付', 	name: 'cspaymode', 			width:70,
	             	editor: { type: 'select', data: null, valueField: 'choose',selectBoxWidth:105,onChanged : validatePaycode},
		            	render: function (item)
		              	{
		              		var lsZw=parent.gainCommonInfoByCode("ZFFS",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.cspaymode)
								{	
									return "<span style='color:blue'>"+lsZw[i].parentcodevalue+"</span>";								
							    }
							}
		                    return '';
		                } 
		        },
		        { display: '金额' ,	name: 'csitemamt', width:60,align: 'center'},
                { display: '大工', 		name: 'csfirstsaler', 	 width:80,
	            	editor: { type: 'select', data: null, 	 url:'loadAutoStaff',autocomplete: true, valueField: 'choose',selectBoxWidth:150,onChanged : validateFristSaleType_G,alwayShowInDown:true},
	            	render: function (item)
	              	{	
	              		for(var i=0;i<lsStaffinfo.length;i++)
						{	
								if(lsStaffinfo[i].bstaffno==checkNull(item.csfirstsaler))
								{
									return "<span style='color:blue'>"+lsStaffinfo[i].staffname+"</span>";
								}
						}
	                    return '';
	                } 
	            },
	            { display: '分享', 		name: 'csfirstshare', 	width:40,align: 'left', editor: { type: 'float', onChanged : validatePrice} },
	            { display: '中工', 		name: 'cssecondsaler',			width:80,
	            	editor: { type: 'select', data: null,   url:'loadAutoStaff',autocomplete: true,valueField: 'choose',selectBoxWidth:150,onChanged : validateSecondSaleType_G,alwayShowInDown:true},
	            	render: function (item)
	              	{
	              		for(var i=0;i<lsStaffinfo.length;i++)
						{	
								if(lsStaffinfo[i].bstaffno==checkNull(item.cssecondsaler))
								{
									return "<span style='color:blue'>"+lsStaffinfo[i].staffname+"</span>";
								}
						}
	                    return '';
	                } 
	            },
	            { display: '分享', 		name: 'cssecondshare', 	width:40,align: 'left', editor: { type: 'float', onChanged : validatePrice } },
	            { display: '小工', 		name: 'csthirdsaler',		width:80,
	            	editor: { type: 'select', data: null,	url:'loadAutoStaff',autocomplete: true, valueField: 'choose',onChanged : validateThirdSaleType_G,selectBoxWidth:150,alwayShowInDown:true},
	            	render: function (item)
	              	{
	              		for(var i=0;i<lsStaffinfo.length;i++)
						{	
								if(lsStaffinfo[i].bstaffno==checkNull(item.csthirdsaler))
								{
									return "<span style='color:blue'>"+lsStaffinfo[i].staffname+"</span>";
								}
						}
	                    return '';
	                } 
	            },
	            { display: '分享', 	name: 'csthirdshare', 		width:40,align: 'left', editor: { type: 'float' , onChanged : validatePrice} },
	            { hide:true,		name: 'bcsinfotype',width:1},
	            { hide:true,		name: 'bcsseqno',width:1},
                { hide:true,		name: 'goodsbarno',width:1},
	            { 		name: 'csitemno', 		hide:true,width:1}
                ],  pageSize:25, 
                data:{Rows: null,Total:0},      
                width: 700,
                height:'460',
                enabledEdit: true, clickToEdit: true,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                	curEmpManger=null;
                    curRecord = data;
                }
            });
            
            commoninfodivdetial_oldcustomer=$("#commoninfodivdetial_oldcustomer").ligerGrid({
                columns: [
                { display: '工号', 	name: 'staffno', 		width:80,align: 'left'},
                { display: '姓名', 	name: 'staffname', 		width:80,align: 'left'},
                { display: '单数', 	name: 'costCount', 		width:40,align: 'right'}
	            ],  pageSize:25, 
                data:{Rows: null,Total:0},      
              	width: 230,
                height:'270',
                enabledEdit: false, checkbox: false,
                rownumbers: false,usePager: false,
                toolbar: { items: [
	                { text: '增加分享老客', click: addcustomer, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' }
	                ]
                }	
            });
            $.metadata.setType("attr", "validate");
            fromValidate = $("form").validate({
                //调试状态，不会提交数据的
                debug: true,
                errorPlacement: function (lable, element)
                {

                    if (element.hasClass("l-textarea"))
                    {
                        element.addClass("l-textarea-invalid");
                    }
                    else if (element.hasClass("l-text-field"))
                    {
                        element.parent().addClass("l-text-invalid");
                    }
                    $(element).removeAttr("title").ligerHideTip();
                    $(element).attr("title", lable.html()).ligerTip();
                },
                success: function (lable)
                {
                    var element = $("#" + lable.attr("for"));
                    if (element.hasClass("l-textarea"))
                    {
                        element.removeClass("l-textarea-invalid");
                    }
                    else if (element.hasClass("l-text-field"))
                    {
                        element.parent().removeClass("l-text-invalid");
                    }
                    $(element).removeAttr("title").ligerHideTip();
                },
                submitHandler: function ()
                {
                    alert("Submitted!");
                }
            });
            
            $("#AC008Tab").ligerTab();
            AC008Tab = $("#AC008Tab").ligerGetTabManager();
      		$("#toptoolbarCard").ligerToolBar({ items: [
      		   	{ text: '介绍人:&nbsp;<input type="text"  name="recommendempid" id="recommendempid" maxlength="20" style="width:80;" onchange="validateRecommendempid(this)" /><input type="text"  name="recommendempname" id="recommendempname" maxlength="20" readonly="true" style="width:100;background:#EDF1F8;" /> <input type="hidden"  name="recommendempinid" id="recommendempinid" />' },
	            //{ line: true },
      		  //{ text: '经理(主):&nbsp;<input type="text"  name="managerzf" id="managerzf" maxlength="20" style="width:80;" onchange="checkStaffValuex(this)" /><input type="text"  name="managerzfname" id="managerzname" maxlength="20" readonly="true" style="width:100;background:#EDF1F8;" /> <input type="hidden"  name="managerzfinid" id="managerzinid" />经理(副):&nbsp;<input type="text"  name="managerff" id="managerff" maxlength="20" style="width:80;" onchange="checkStaffValuex(this)" /><input type="text"  name="managerffname" id="managerffname" maxlength="20" readonly="true" style="width:100;background:#EDF1F8;" /> <input type="hidden"  name="managerffinid" id="managerffinid" /> ' },
      		    { text: '修改收银单', click: editServiceDetailInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/memeber.gif' },
      		   //{ line: true },
      		    { text: '返销收银单', click: backServiceDetailInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' }
            ]
            });
      		
      		
      		$("#toptoolmanager").ligerToolBar({ items: [
      		                                		   	//{ text: '介绍人:&nbsp;<input type="text"  name="recommendempid" id="recommendempid" maxlength="20" style="width:80;" onchange="validateRecommendempid(this)" /><input type="text"  name="recommendempname" id="recommendempname" maxlength="20" readonly="true" style="width:100;background:#EDF1F8;" /> <input type="hidden"  name="recommendempinid" id="recommendempinid" />' },
      		                          	            //{ line: true },
      		                                		  { text: '经理(主):&nbsp;<input type="text"  name="managerzf" id="managerzf" maxlength="20" style="width:80;" onchange="checkStaffValuex(this)" /><input type="text"  name="managerzfname" id="managerzfname" maxlength="20" readonly="true" style="width:100;background:#EDF1F8;" /> <input type="hidden"  name="managerzfinid" id="managerzinid" />经理(副):&nbsp;<input type="text"  name="managerff" id="managerff" maxlength="20" style="width:80;" onchange="checkStaffValuex(this)" /><input type="text"  name="managerffname" id="managerffname" maxlength="20" readonly="true" style="width:100;background:#EDF1F8;" /> <input type="hidden"  name="managerffinid" id="managerffinid" /> ' }
      		                                		    //{ text: '修改收银单', click: editServiceDetailInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/memeber.gif' },
      		                                		   //{ line: true },
      		                                		    //{ text: '返销收银单', click: backServiceDetailInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' }
      		                                      ]
      		                                      });
      		
      		
            
            $("#toptoolbarCard_Goods").ligerToolBar({ items: [
                { text: '经理(主):&nbsp;<input type="text"  name="managerz" id="managerz" maxlength="20" style="width:80;" onchange="checkStaffValuex(this)" /><input type="text"  name="managerzname" id="managerzname" maxlength="20" readonly="true" style="width:100;background:#EDF1F8;" /> <input type="hidden"  name="managerzinid" id="managerzinid" />经理(副):&nbsp;<input type="text"  name="managerf" id="managerf" maxlength="20" style="width:80;" onchange="checkStaffValuex(this)" /><input type="text"  name="managerfname" id="managerfname" maxlength="20" readonly="true" style="width:100;background:#EDF1F8;" /> <input type="hidden"  name="managerfinid" id="managerfinid" /> ' },
      		    { text: '修改收银单', click: editGoodsDetailInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/memeber.gif' },
      		    { line: true },
      		    { text: '返销收银单', click: backGoodsDetailInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' }
            ]
            });
            
            
            $("#toptoolbarSaleCard").ligerToolBar({ items: [
      		    { text: '修改业绩分享', click: editCurRootInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/memeber.gif' },
      		    { line: true },
      		    { text: '返冲异动单', click: backCardSaleInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' }
            ]
            });
            $("#topdatabarCard").ligerToolBar({ items: [
      		    {text: '<input type="text" name="searchdate" id="searchdate"  readonly="true" style="width:120;" onchange="loadInfoByvalidate(this)"/>'}
            ]
            });
            $("#searchdate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120',onChangeDate: loadInfoByvalidate });
         	
      		var today = new Date();
			var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
			var intDay=today.getDate();
			var today = intYear.toString()+"-"+fullStr(intMonth.toString())+"-"+fullStr(intDay.toString());
			document.getElementById("searchdate").value=today;
			f_selectNode();
			loadPayModeDate();
   		}catch(e){alert(e.message);}
    });
    
    
   function loadPayModeDate()
   {
   		var strpaymode= new Array();
   		var strSalePayMode="1;6;14";
   		if(strSalePayMode!="")
   		{
   			var returnValue='';
   			var paymode="";
   			var paymodename="";
   			strpaymode=strSalePayMode.split(";");
   			for(var i=0;i<strpaymode.length;i++)
   			{
   				paymode=strpaymode[i];
   				paymodename=parent.loadCommonControlValue("ZFFS",0,paymode);
   				if(returnValue!='')
				{
					returnValue=returnValue+',';
				}
				else
				{
					returnValue=returnValue+'[';
				}
				returnValue=returnValue+'{"choose": "'+paymode+'","text": "'+paymode+'_'+paymodename+'"}';
	   		}
	   		if(returnValue!='')
	   		{
				returnValue=returnValue+']';
				commoninfodivdetial_pay.columns[0].editor.data=JSON.parse(returnValue);
				commoninfodivdetial_service.columns[1].editor.data=JSON.parse(returnValue);
				commoninfodivdetial_goods.columns[1].editor.data=JSON.parse(returnValue);
			}
   		}
   }
    
    function fullStr( str )
	{
			if( str.length == 1 )
			{
				str = "0" + str;
			}
			return str;
	}
 
    function f_selectNode()
	{
		var parm = function (data)
		{
		   	return data.id== parent.localCompid;
		};		
		compTree.selectNode(parm);
	}
  
  function loadInfoByvalidate(obj)
  {
  	if(obj!="")
  	{
  			var params = "strCurCompId="+strCurCompId;		
       		params = params+"&strSearchDate="+obj;	
       		var requestUrl ="ac008/loadMasterBillType.action"; 
			var responseMethod="loadMasterBillTypeMessage";				
			sendRequestForParams_p(requestUrl,responseMethod,params );
  	}
  }
    //加载门店信息
	function compSelect(note)
    {
        try{
        	
        	strCurCompId=note.data.id;
       		var params = "strCurCompId="+note.data.id;			
       		params = params+"&strSearchDate="+document.getElementById("searchdate").value;	
       		var requestUrl ="ac008/loadMasterBillType.action"; 
			var responseMethod="loadMasterBillTypeMessage";				
			sendRequestForParams_p(requestUrl,responseMethod,params );
          }catch(e){alert(e.message);}
    }
 
	   function loadMasterBillTypeMessage(request)
	   {
	       		try
	        	{
	 			
		        	var responsetext = eval("(" + request.responseText + ")");
		    		if(responsetext.lsBillEdits!=null && responsetext.lsBillEdits.length>0)
			   		{
			   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: responsetext.lsBillEdits,Total: responsetext.lsBillEdits.length});
		            	commoninfodivmaster.loadData(true);   
		            	commoninfodivmaster.select(0);         	
			   		}
					if(strCurCompId==parent.document.getElementById("strCompId").value)
			        {
			        	lsStaffinfo=parent.StaffInfo;
			       		
			        }
			        else
			        {
			        	lsStaffinfo=responsetext.lsStaffinfo;
			        }
					
					if(responsetext.paramSP114==0)
					{
						document.getElementById("isxnj").disabled=true;
					}
					else
					{
						document.getElementById("isxnj").disabled=false;
					}
			       
					var lsStaffSelectData_sj=loadOtherGridByStaffInfo(lsStaffinfo,'',1);
					var lsStaffSelectData_tr=loadOtherGridByStaffInfo(lsStaffinfo,'',2);
					var loadGridByStaffInfo_all=loadGridByStaffInfo(lsStaffinfo,'');
					/*firstsaleridmanager= $("#firstsalerid").ligerComboBox({ isShowCheckBox: false,   url:'loadAutoStaff', data: loadGridByStaffInfo_all
		               					 , valueFieldID: 'factcfirstsalerid',autocomplete: true,label:'请选员工',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220'  ,alwayShowInDown:true}); 
					secondsaleridmanager= $("#secondsalerid").ligerComboBox({ isShowCheckBox: false,   url:'loadAutoStaff', data: loadGridByStaffInfo_all
		               					 , valueFieldID: 'factcsecondsalerid',autocomplete: true,label:'请选员工',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220'  ,alwayShowInDown:true}); 
					thirdsalerinidmanager= $("#thirdsalerid").ligerComboBox({ isShowCheckBox: false,   url:'loadAutoStaff', data: loadGridByStaffInfo_all
		               					 , valueFieldID: 'factcthirdsalerid',autocomplete: true,label:'请选员工',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220'  ,alwayShowInDown:true}); 
					fourthsaleridmanager= $("#fourthsalerid").ligerComboBox({ isShowCheckBox: false,    url:'loadAutoStaff',data: loadGridByStaffInfo_all
		               					 , valueFieldID: 'factcfourthsalerid',autocomplete: true,label:'请选员工',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220' ,alwayShowInDown:true }); 
					fifthsaleridmanager= $("#fifthsalerid").ligerComboBox({ isShowCheckBox: false,    url:'loadAutoStaff',data: loadGridByStaffInfo_all
		               					 , valueFieldID: 'factcfifthsalerid',autocomplete: true,label:'请选员工',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220'  ,alwayShowInDown:true}); 
					sixthsaleridmanager= $("#sixthsalerid").ligerComboBox({ isShowCheckBox: false,    url:'loadAutoStaff',data: loadGridByStaffInfo_all
		               					 , valueFieldID: 'factcsixthsalerid',autocomplete: true,label:'请选员工',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220'  ,alwayShowInDown:true}); 
					seventhsaleridmanager= $("#seventhsalerid").ligerComboBox({ isShowCheckBox: false,   url:'loadAutoStaff', data: loadGridByStaffInfo_all
		               					 , valueFieldID: 'factcseventhsalerid',autocomplete: true,label:'请选员工',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220'  ,alwayShowInDown:true}); 
					eighthsaleridmanager= $("#eighthsalerid").ligerComboBox({ isShowCheckBox: false,   url:'loadAutoStaff', data: loadGridByStaffInfo_all
		               					 , valueFieldID: 'factceighthsalerid',autocomplete: true,label:'请选员工',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220'  ,alwayShowInDown:true}); 
					ninthsaleridmanager= $("#ninthsalerid").ligerComboBox({ isShowCheckBox: false,   url:'loadAutoStaff', data: loadGridByStaffInfo_all
		               					 , valueFieldID: 'factcninthsalerid',autocomplete: true,label:'请选员工',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220'  ,alwayShowInDown:true}); 
					tenthsaleridmanager= $("#tenthsalerid").ligerComboBox({ isShowCheckBox: false,   url:'loadAutoStaff', data: loadGridByStaffInfo_all
		               					 , valueFieldID: 'factctenthsalerid',autocomplete: true,label:'请选员工',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220' ,alwayShowInDown:true }); 
					
				*/
					commoninfodivdetial_service.columns[3].editor.data=loadCommonDataGridByStaffInfo(lsStaffinfo);
					commoninfodivdetial_service.columns[5].editor.data=loadCommonDataGridByStaffInfo(lsStaffinfo);
					commoninfodivdetial_service.columns[7].editor.data=loadCommonDataGridByStaffInfo(lsStaffinfo);
					commoninfodivdetial_service.columns[9].editor.data=loadCommonDataGridByStaffInfo(lsStaffinfo);
					
					commoninfodivdetial_goods.columns[3].editor.data=loadCommonDataGridByStaffInfo(lsStaffinfo);
					commoninfodivdetial_goods.columns[5].editor.data=loadCommonDataGridByStaffInfo(lsStaffinfo);
					commoninfodivdetial_goods.columns[7].editor.data=loadCommonDataGridByStaffInfo(lsStaffinfo);
					
				
					commoninfodivdetial_pay.options.data=$.extend(true, {},{Rows: null,Total: 0});
					commoninfodivdetial_pay.loadData(true);  
		   			itemclick_addpay();
					commoninfodivdetial_service.options.data=$.extend(true, {},{Rows: null,Total: 0});
					commoninfodivdetial_service.loadData(true); 
					commoninfodivdetial_goods.options.data=$.extend(true, {},{Rows: null,Total: 0});
					commoninfodivdetial_goods.loadData(true);
					document.getElementById("paramSp097").value=responsetext.paramSp097;
					document.getElementById("paramSp112").value=responsetext.paramSp112;
					document.getElementById("strShareCondition").value=responsetext.strShareCondition;
					
					addService();
		   			addGoods();
		   			itemclick_addcustomer();
		   		}catch(e){alert(e.message);}
	    }
	    
	    
	     function  itemclick_addcustomer()
        {
        	var row = commoninfodivdetial_oldcustomer.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivdetial_oldcustomer.addRow({ 
				                staffno: "",
				                staffname: "",
				                costCount:0
				            }, row, false);
        }
        
        
        function addcustomer()
        {
        	addStaffNo="";
   			addstaffName="";
        	addcustomerDialog=$.ligerDialog.open({ height: null, url: contextURL+'/AdvancedOperations/AC008/handShareStaffno.jsp', width: 300, showMax: false, showToggle: false, showMin: false, isResize: false, title: '新增美发部卡金分享人' });
        
        }
        
        function handInsertShareMessage(request)
        {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        	
	        		var gridlen=commoninfodivdetial_oldcustomer.rows.length*1;
					if(gridlen==0)
					{
						itemclick_addcustomer();
						gridlen=gridlen+1;
					} 
					if(checkNull(commoninfodivdetial_oldcustomer.getRow(0).staffno)!="")
					{
						itemclick_addcustomer();
						gridlen=gridlen+1;
					}   
					var curCustomerDetialRecord=commoninfodivdetial_oldcustomer.getRow(gridlen-1);
					commoninfodivdetial_oldcustomer.updateRow(curCustomerDetialRecord,{staffno: addStaffNo
															  ,staffname : addstaffName
															  ,costCount  : 0});  
	        		addcustomerDialog.close();
	        	}
	        	else
	        	{
	        		$.ligerDialog.error(strMessage);
	        	}
	        	addStaffNo="";
   				addstaffName="";
        	}
			catch(e)
			{
				alert(e.message);
			}
        }
        
        
	    function loadCommonDataGridByStaffInfo(lsStaffinfo)
		{
				try
				{
					var strJson = "";//'{ "name": "cxh", "sex": "man" }';
					var ccount=0;
					if(lsStaffinfo!=null && lsStaffinfo.length>0)
					{	
						for(var i=0;i<lsStaffinfo.length;i++)
						{
								ccount=ccount*1+1;
								if(ccount==1)
								{
									strJson=strJson+'[';
								}
								else
								{
									strJson=strJson+',';
								}
								strJson=strJson+'{ "choose":"'+lsStaffinfo[i].bstaffno+'", "text": "'+lsStaffinfo[i].bstaffno+'_'+lsStaffinfo[i].staffname+'"}';
							
						}
						if(strJson!="")
						{
							strJson=strJson+']';
							return JSON.parse(strJson);
						}
						return null;
						 
					}
				
				}catch(e){alert(e.message);}
		}
    
	    function loadAutoStaff(curmanager,curWriteStaff)
	    {
	    	/*if(curmanager.options.valueFieldID == "factcfirstsalerid"
	    	|| curmanager.options.valueFieldID == "factcsecondsalerid"
	    	//|| curmanager.options.valueFieldID == "factcthirdsalerid"
	    	|| curmanager.options.valueFieldID == "factcfourthsalerid"
	    	//|| curmanager.options.valueFieldID == "factceighthsalerid"
	    	|| curmanager.options.valueFieldID == "factcninthsalerid"
	    	//|| curmanager.options.valueFieldID == "factctenthsalerid"
	    	)
	    	{
	    		curmanager.setData(loadOtherGridByStaffInfo(lsStaffinfo,curWriteStaff,1));
	    	}
	    	else if(curmanager.options.valueFieldID == "factcthirdsalerid"
	    	|| curmanager.options.valueFieldID == "csfirstsaler"
	    	|| curmanager.options.valueFieldID == "cssecondsaler"
	    	|| curmanager.options.valueFieldID == "csthirdsaler")
	    	{
	    		curmanager.setData(loadGridByStaffInfo(lsStaffinfo,curWriteStaff));
	    		curEmpManger=curmanager;
	    	}
	    	else
	    	{
	    		curmanager.setData(loadOtherGridByStaffInfo(lsStaffinfo,curWriteStaff,2));
	    	}*/
	    	curmanager.setData(loadGridByStaffInfo(lsStaffinfo,curWriteStaff));
	    	curEmpManger=curmanager;
	    	curEmpManger.selectBox.show();
	    }
   
	   function loadSelecMasterData(data, rowindex, rowobj)
	   {
	   		 try{
	        	nBillType=data.billType;
				var params = "strCurCompId="+strCurCompId;	
				params=params+"&strSearchDate="+document.getElementById("searchdate").value;			
				params=params+"&nBillType="+nBillType;		
		
				var requestUrl ="ac008/loadMasterinfo.action"; 
				var responseMethod="loadMasterinfoMessage";				
				sendRequestForParams_p(requestUrl,responseMethod,params );
	          }catch(e){alert(e.message);}
	   }
	   
	   function loadMasterinfoMessage(request)
	   {
	       	var responsetext = eval("(" + request.responseText + ")");
		   	if(responsetext.lsReEditBillInfo!=null && responsetext.lsReEditBillInfo.length>0)
			{
			  	commoninfodivdetial.options.data=$.extend(true, {},{Rows: responsetext.lsReEditBillInfo,Total: responsetext.lsReEditBillInfo.length});
		       	commoninfodivdetial.loadData(true);      
		       	commoninfodivdetial.select(0);      	
			}
			else
			{
				commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total: 0 });
		       	commoninfodivdetial.loadData(true);
		       	itemclick_adddetial();
			}
					
		}
	   function loadSelecDetialData(data, rowindex, rowobj)
	   {
	   		if(checkNull(data.strBillId)=="")
	   		{
	   			return ;
	   		}
	   		try
	   		{
	   		    
	   			if(checkNull(data.billType)==12)
	   			{
	   				$("#dhprjname").html("年卡项目");
	   			}
	   			if(checkNull(data.billType)==10)//收银(项目)
	   			{
	   				AC008Tab.selectTabItem("tabitem2");
	   				var params = "strCurCompId="+checkNull(data.strCompId);	
					params=params+"&strCurBillId="+checkNull(data.strBillId);
					params=params+"&nBillType="+checkNull(data.billType);
					document.getElementById("strServiceCompId").value=checkNull(data.strCompId);
					document.getElementById("strServiceBillId").value=checkNull(data.strBillId);
					document.getElementById("strCurCardNo").value=checkNull(data.strCardNo);
					document.getElementById("strCurDate").value=checkNull(data.billdate);
					document.getElementById("backflag").value=checkNull(data.backflag);
					var requestUrl ="ac008/loadServiceDetial.action"; 
					var responseMethod="loadServiceDetialMessage";				
					sendRequestForParams_p(requestUrl,responseMethod,params );
	   				
	   				return;
	   			}
	   			else if(checkNull(data.billType)==11)//收银(产品)
	   			{
	   				AC008Tab.selectTabItem("tabitem3");
	   				var params = "strCurCompId="+checkNull(data.strCompId);	
					params=params+"&strCurBillId="+checkNull(data.strBillId);
					params=params+"&nBillType="+checkNull(data.billType);
					document.getElementById("strServiceCompId").value=checkNull(data.strCompId);
					document.getElementById("strServiceBillId").value=checkNull(data.strBillId);
					document.getElementById("strCurCardNo").value=checkNull(data.strCardNo);
					document.getElementById("strCurDate").value=checkNull(data.billdate);
					document.getElementById("backflag").value=checkNull(data.backflag);
					var requestUrl ="ac008/loadServiceDetial.action"; 
					var responseMethod="loadGoodSDetialMessage";				
					sendRequestForParams_p(requestUrl,responseMethod,params );
					
	   				return;
	   			}
	
	   			AC008Tab.selectTabItem("tabitem1");
	   			var params = "strCurCompId="+checkNull(data.strCompId);	
				params=params+"&strCurBillId="+checkNull(data.strBillId);
				params=params+"&changeseqno="+checkNull(data.changeseqno);	
				params=params+"&nBillType="+checkNull(data.billType);
				params+="&itemInid="+checkNull(data.itemInid);
				var requestUrl ="ac008/loadCardSaleDetial.action"; 
				var responseMethod="loadCardSaleDetialMessage";				
				sendRequestForParams_p(requestUrl,responseMethod,params );
			}
			catch(e){alert(e.message);}	   		
	   }
	   
	   function loadCardSaleDetialMessage(request)
	   {
	   		var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.curReEditBillInfo!=null)
	   		{
	   			loadCardSale(responsetext.curReEditBillInfo,responsetext.lsDpayinfo);
	   		}
	   		if(responsetext.lsStaffinfoSimple!=null && responsetext.lsStaffinfoSimple.length>0)
			{
				commoninfodivdetial_oldcustomer.options.data=$.extend(true, {},{Rows: responsetext.lsStaffinfoSimple,Total: responsetext.lsStaffinfoSimple.length});
				commoninfodivdetial_oldcustomer.loadData(true);            	
			}
			else
			{
				commoninfodivdetial_oldcustomer.options.data=$.extend(true, {},{Rows: null,Total: 0});
				commoninfodivdetial_oldcustomer.loadData(true);
				itemclick_addcustomer();
			}
	   }
	   
	   function loadCardSale(data,lsDpayinfo)
	   {
		   
	   			/*firstsaleridmanager.setDisabled();
   				secondsaleridmanager.setDisabled();
   				thirdsalerinidmanager.setDisabled();
	   			fourthsaleridmanager.setDisabled();
   				fifthsaleridmanager.setDisabled();
   				sixthsaleridmanager.setDisabled();
   				seventhsaleridmanager.setDisabled();
   				eighthsaleridmanager.setDisabled();
   				ninthsaleridmanager.setDisabled();
				tenthsaleridmanager.setDisabled();*/
				
				
				document.getElementById("firstsalerid").readOnly="readOnly";
				document.getElementById("secondsalerid").readOnly="readOnly";
				document.getElementById("thirdsalerid").readOnly="readOnly";
				document.getElementById("fourthsalerid").readOnly="readOnly";
				document.getElementById("fifthsalerid").readOnly="readOnly";
				document.getElementById("sixthsalerid").readOnly="readOnly";
				document.getElementById("seventhsalerid").readOnly="readOnly";
				document.getElementById("eighthsalerid").readOnly="readOnly";
				document.getElementById("ninthsalerid").readOnly="readOnly";
				document.getElementById("tenthsalerid").readOnly="readOnly";
				document.getElementById("beautyManager").readOnly="readOnly";
				document.getElementById("consultant").readOnly="readOnly";
				document.getElementById("consultant1").readOnly="readOnly";
				
				
				document.getElementById("firstsalerid").style.background="#EDF1F8";
				document.getElementById("secondsalerid").style.background="#EDF1F8";
				document.getElementById("thirdsalerid").style.background="#EDF1F8";
				document.getElementById("fourthsalerid").style.background="#EDF1F8";
				document.getElementById("fifthsalerid").style.background="#EDF1F8";
				document.getElementById("sixthsalerid").style.background="#EDF1F8";
				document.getElementById("seventhsalerid").style.background="#EDF1F8";
				document.getElementById("eighthsalerid").style.background="#EDF1F8";
				document.getElementById("ninthsalerid").style.background="#EDF1F8";
				document.getElementById("tenthsalerid").style.background="#EDF1F8";
				document.getElementById("beautyManager").style.background="#EDF1F8";
				document.getElementById("consultant").style.background="#EDF1F8";
				document.getElementById("consultant1").style.background="#EDF1F8";
				
				
				
				document.getElementById("editType1").checked=false;
				document.getElementById("editType2").checked=false;
				document.getElementById("editType3").checked=false;
				document.getElementById("editType4").checked=false;
				document.getElementById("editType5").checked=false;
				document.getElementById("editType6").checked=false;
				document.getElementById("editType7").checked=false;
				document.getElementById("editType8").checked=false;
				document.getElementById("editType9").checked=false;
				document.getElementById("editType10").checked=false;
				document.getElementById("editType11").checked=false;
				document.getElementById("editType12").checked=false;
				document.getElementById("editType13").checked=false;
				document.getElementById("editType14").checked=false;
				document.getElementById("editType15").checked=false;
				
				
				document.getElementById("editType1").disabled=false;
				document.getElementById("editType2").disabled=false;
				document.getElementById("editType3").disabled=false;
				document.getElementById("editType4").disabled=false;
				document.getElementById("editType5").disabled=false;
				document.getElementById("editType6").disabled=false;
				document.getElementById("editType7").disabled=false;
				document.getElementById("editType8").disabled=false;
				document.getElementById("editType9").disabled=false;
				document.getElementById("editType10").disabled=false;
				document.getElementById("editType11").disabled=false;
				document.getElementById("editType12").disabled=false;
				document.getElementById("editType13").disabled=false;
				document.getElementById("editType14").disabled=false;
				document.getElementById("editType15").disabled=false;
				
				if(data.billType==12)
				{
					document.getElementById("id12").style.display="block";
					document.getElementById("amt12").style.display="block";
					if(data.strPrjNo.substring(0,1)=="3")
					{
						document.getElementById("editType4").disabled=false;
						document.getElementById("editType2").disabled=true;
						document.getElementById("editType3").disabled=true;
						document.getElementById("editType1").disabled=false;
					}
					else
					{
						document.getElementById("editType4").disabled=true;
						document.getElementById("editType1").disabled=false;
						document.getElementById("editType2").disabled=false;
						document.getElementById("editType3").disabled=false;
					}
					
				}
				else
				{
					document.getElementById("id12").style.display="none";
					document.getElementById("amt12").style.display="none";
				}
				if(data.billType==9 || data.billType==12)
				{
					document.getElementById("editType5").disabled=true;
					document.getElementById("editType6").disabled=true;
					document.getElementById("editType7").disabled=true;
					document.getElementById("editType8").disabled=true;
					document.getElementById("editType9").disabled=true;
					document.getElementById("editType10").disabled=true;
				}
				else
				{
					document.getElementById("editType5").disabled=false;
					document.getElementById("editType6").disabled=false;
					document.getElementById("editType7").disabled=false;
					document.getElementById("editType8").disabled=false;
					document.getElementById("editType9").disabled=false;
					document.getElementById("editType10").disabled=false;
				}
				
				if(data.billType==6 || data.billType==7 || data.billType==8 || data.billType==9)
				{
					document.getElementById("editType11").disabled=true;
					document.getElementById("editType12").disabled=true;
					document.getElementById("editType13").disabled=true;
				}
				else
				{
					document.getElementById("editType11").disabled=false;
					document.getElementById("editType12").disabled=false;
					document.getElementById("editType13").disabled=false;
				}
				
				document.getElementById("firstsaleamt").readOnly="readOnly";
				document.getElementById("secondsaleamt").readOnly="readOnly";
				document.getElementById("thirdsaleamt").readOnly="readOnly";
				document.getElementById("fourthsaleamt").readOnly="readOnly";
				document.getElementById("fifthsaleamt").readOnly="readOnly";
				document.getElementById("sixthsaleamt").readOnly="readOnly";
				document.getElementById("seventhsaleamt").readOnly="readOnly";
				document.getElementById("eighthsaleamt").readOnly="readOnly";
				document.getElementById("ninthsaleamt").readOnly="readOnly";
				document.getElementById("tenthsaleamt").readOnly="readOnly";
				document.getElementById("firstsalecashamt").readOnly="readOnly";
				document.getElementById("secondsalecashamt").readOnly="readOnly";
				document.getElementById("thirdsalecashamt").readOnly="readOnly";
				document.getElementById("fourthsalecashamt").readOnly="readOnly";
				document.getElementById("fifthsalecashamt").readOnly="readOnly";
				document.getElementById("sixthsalecashamt").readOnly="readOnly";
				document.getElementById("seventhsalecashamt").readOnly="readOnly";
				document.getElementById("eighthsalecashamt").readOnly="readOnly";
				document.getElementById("ninthsalecashamt").readOnly="readOnly";
				document.getElementById("tenthsalecashamt").readOnly="readOnly";
				
				handleRadio("curReEditBillInfo.billType",checkNull(data.billType));
	   			document.getElementById("billType").value=checkNull(data.billType);
	   			document.getElementById("backflag").value=checkNull(data.backflag);
	   			document.getElementById("strCurCardNo").value=checkNull(data.strCardNo);
				document.getElementById("strCurDate").value=checkNull(data.billdate);
	   			document.getElementById("strCompId").value=checkNull(data.strCompId);
	   			document.getElementById("strBillId").value=checkNull(data.strBillId);
	   			document.getElementById("strCardNo").value=checkNull(data.strCardNo);
	   			document.getElementById("strMemberName").value=checkNull(data.strMemberName);
	   			document.getElementById("strCardType").value=checkNull(data.strCardType);
	   			document.getElementById("strCardTypeName").value=checkNull(data.strCardTypeName);
	   			document.getElementById("strPrjName").value=checkNull(data.strPrjName);
	   			document.getElementById("billinsertype").value=checkNull(data.billinsertype);
	   			document.getElementById("changeseqno").value=checkNull(data.changeseqno);
	   			document.getElementById("iteminid").value=checkNull(data.itemInid);
	   			document.getElementById("strPrjamt").value=checkNull(data.yearAmt);
	   			
	   			changebillinsertype(document.getElementById("billinsertype"));
	   			
		       	//document.getElementById("firstsalerid_h").value=checkNull(data.firstsalerid);
			   	//firstsaleridmanager.selectValue(checkNull(data.firstsalerid));
			    document.getElementById("firstsalerid").value=checkNull(data.firstsalerid);
			   	document.getElementById("firstsaleamt").value=checkNull(data.firstsaleamt);
			   	document.getElementById("firstsalecashamt").value=checkNull(data.firstsalecashamt);
				//document.getElementById("secondsalerid_h").value=checkNull(data.secondsalerid);
				//secondsaleridmanager.selectValue(checkNull(data.secondsalerid));
				 document.getElementById("secondsalerid").value=checkNull(data.secondsalerid);
				document.getElementById("secondsaleamt").value=checkNull(data.secondsaleamt);
				document.getElementById("secondsalecashamt").value=checkNull(data.secondsalecashamt);
				//document.getElementById("thirdsalerid_h").value=checkNull(data.thirdsalerid);
				//thirdsalerinidmanager.selectValue(checkNull(data.thirdsalerid));
				 document.getElementById("thirdsalerid").value=checkNull(data.thirdsalerid);
				document.getElementById("thirdsaleamt").value=checkNull(data.thirdsaleamt);
				document.getElementById("thirdsalecashamt").value=checkNull(data.thirdsalecashamt);			   		
				//document.getElementById("fourthsalerid_h").value=checkNull(data.fourthsalerid);
				//fourthsaleridmanager.selectValue(checkNull(data.fourthsalerid));
				document.getElementById("fourthsalerid").value=checkNull(data.fourthsalerid);
				document.getElementById("fourthsaleamt").value=checkNull(data.fourthsaleamt);
				document.getElementById("fourthsalecashamt").value=checkNull(data.fourthsalecashamt);			
				document.getElementById("isxnj").value=checkNull(data.isxnj);
				//document.getElementById("fifthsalerid_h").value=checkNull(data.fifthsalerid);
				//fifthsaleridmanager.selectValue(checkNull(data.fifthsalerid));
				document.getElementById("fifthsalerid").value=checkNull(data.fifthsalerid);
				document.getElementById("fifthsaleamt").value=checkNull(data.fifthsaleamt);
				document.getElementById("fifthsalecashamt").value=checkNull(data.fifthsalecashamt);
				//document.getElementById("sixthsalerid_h").value=checkNull(data.sixthsalerid);
				//sixthsaleridmanager.selectValue(checkNull(data.sixthsalerid));
				document.getElementById("sixthsalerid").value=checkNull(data.sixthsalerid);
				document.getElementById("sixthsaleamt").value=checkNull(data.sixthsaleamt);
				document.getElementById("sixthsalecashamt").value=checkNull(data.sixthsalecashamt);
				//document.getElementById("seventhsalerid_h").value=checkNull(data.seventhsalerid);
				//seventhsaleridmanager.selectValue(checkNull(data.seventhsalerid));
				document.getElementById("seventhsalerid").value=checkNull(data.seventhsalerid);
				document.getElementById("seventhsaleamt").value=checkNull(data.seventhsaleamt);
				document.getElementById("seventhsalecashamt").value=checkNull(data.seventhsalecashamt);
				//document.getElementById("eighthsalerid_h").value=checkNull(data.eighthsalerid);
				//eighthsaleridmanager.selectValue(checkNull(data.eighthsalerid));
				document.getElementById("eighthsalerid").value=checkNull(data.eighthsalerid);
				document.getElementById("eighthsaleamt").value=checkNull(data.eighthsaleamt);
				document.getElementById("eighthsalecashamt").value=checkNull(data.eighthsalecashamt);
				//document.getElementById("ninthsalerid_h").value=checkNull(data.ninthsalerid);
				//ninthsaleridmanager.selectValue(checkNull(data.ninthsalerid));
				document.getElementById("ninthsalerid").value=checkNull(data.ninthsalerid);
				document.getElementById("ninthsaleamt").value=checkNull(data.ninthsaleamt);
				document.getElementById("ninthsalecashamt").value=checkNull(data.ninthsalecashamt);
				
				
				//document.getElementById("tenthsalerid_h").value=checkNull(data.tenthsalerid);
				//tenthsaleridmanager.selectValue(checkNull(data.tenthsalerid));
				document.getElementById("tenthsalerid").value=checkNull(data.tenthsalerid);
				document.getElementById("tenthsaleamt").value=checkNull(data.tenthsaleamt);
				document.getElementById("tenthsalecashamt").value=checkNull(data.tenthsalecashamt);	
				
				document.getElementById("beautyManager").value=checkNull(data.beautyManager);
				document.getElementById("consultant").value=checkNull(data.consultant);
				document.getElementById("consultant1").value=checkNull(data.consultant1);
				
				
				document.getElementById("beautyMangerNo").value=checkNull(data.beautyMangerNo);
				document.getElementById("beautyGw").value=checkNull(data.beautyGw);
				
				if(lsDpayinfo!=null && lsDpayinfo.length>0)
				{
					commoninfodivdetial_pay.options.data=$.extend(true, {},{Rows: lsDpayinfo,Total:lsDpayinfo.length});
					commoninfodivdetial_pay.loadData(true);            	
				}
				else
				{
					commoninfodivdetial_pay.options.data=$.extend(true, {},{Rows: null,Total: 0});
					commoninfodivdetial_pay.loadData(true);   
					itemclick_addpay();
				}
				document.getElementById("firstsalername").value="";
				document.getElementById("secondsalername").value="";
				document.getElementById("thirdsalername").value="";
				document.getElementById("fourthsalername").value="";
				document.getElementById("fifthsalername").value="";
				document.getElementById("sixthsalername").value="";
				document.getElementById("seventhsalername").value="";
				document.getElementById("eighthsalername").value="";
				document.getElementById("ninthsalername").value="";
				document.getElementById("tenthsalername").value="";
				document.getElementById("beautyManagerName").value="";
				document.getElementById("consultantName").value="";
				
				document.getElementById("beautyMangerName").value="";
				document.getElementById("beautyGwName").value="";
				
				document.getElementById("consultant1Name").value="";
				for(var i=0;i<lsStaffinfo.length;i++)
				{
				  	if(document.getElementById("firstsalerid").value!="" 
				  	&& document.getElementById("firstsalerid").value==lsStaffinfo[i].bstaffno)
				    {
				    	document.getElementById("firstsalername").value=lsStaffinfo[i].staffname;
				    }
				   
				    if(document.getElementById("secondsalerid").value!="" 
				  	&& document.getElementById("secondsalerid").value==lsStaffinfo[i].bstaffno)
				    {
				    	document.getElementById("secondsalername").value=lsStaffinfo[i].staffname;
				    }
				    if(document.getElementById("thirdsalerid").value!="" 
				  	&& document.getElementById("thirdsalerid").value==lsStaffinfo[i].bstaffno)
				    {
				    	document.getElementById("thirdsalername").value=lsStaffinfo[i].staffname;
				    }
				    if(document.getElementById("fourthsalerid").value!="" 
				  	&& document.getElementById("fourthsalerid").value==lsStaffinfo[i].bstaffno)
				    {
				    	document.getElementById("fourthsalername").value=lsStaffinfo[i].staffname;
				    }
				    if(document.getElementById("fifthsalerid").value!="" 
				  	&& document.getElementById("fifthsalerid").value==lsStaffinfo[i].bstaffno)
				    {
				    	document.getElementById("fifthsalername").value=lsStaffinfo[i].staffname;
				    }
				    if(document.getElementById("sixthsalerid").value!="" 
				  	&& document.getElementById("sixthsalerid").value==lsStaffinfo[i].bstaffno)
				    {
				    	document.getElementById("sixthsalername").value=lsStaffinfo[i].staffname;
				    }
				    if(document.getElementById("seventhsalerid").value!="" 
				  	&& document.getElementById("seventhsalerid").value==lsStaffinfo[i].bstaffno)
				    {
				    	document.getElementById("seventhsalername").value=lsStaffinfo[i].staffname;
				    }
				    if(document.getElementById("eighthsalerid").value!="" 
				  	&& document.getElementById("eighthsalerid").value==lsStaffinfo[i].bstaffno)
				    {
				    	document.getElementById("eighthsalername").value=lsStaffinfo[i].staffname;
				    }
				    if(document.getElementById("ninthsalerid").value!="" 
				  	&& document.getElementById("ninthsalerid").value==lsStaffinfo[i].bstaffno)
				    {
				    	document.getElementById("ninthsalername").value=lsStaffinfo[i].staffname;
				    }
				    if(document.getElementById("tenthsalerid").value!="" 
				  	&& document.getElementById("tenthsalerid").value==lsStaffinfo[i].bstaffno)
				    {
				    	document.getElementById("tenthsalername").value=lsStaffinfo[i].staffname;
				    }
				    if(document.getElementById("beautyManager").value!="" 
					  	&& document.getElementById("beautyManager").value==lsStaffinfo[i].bstaffno)
					{
					    document.getElementById("beautyManagerName").value=lsStaffinfo[i].staffname;
					}
				    if(document.getElementById("consultant").value!="" 
					  	&& document.getElementById("consultant").value==lsStaffinfo[i].bstaffno)
					{
					    document.getElementById("consultantName").value=lsStaffinfo[i].staffname;
					}
				    if(document.getElementById("consultant1").value!="" 
					  	&& document.getElementById("consultant1").value==lsStaffinfo[i].bstaffno)
					{
					    document.getElementById("consultant1Name").value=lsStaffinfo[i].staffname;
					}
				    if(document.getElementById("beautyMangerNo").value!="" 
					  	&& document.getElementById("beautyMangerNo").value==lsStaffinfo[i].bstaffno)
					{
					    document.getElementById("beautyMangerName").value=lsStaffinfo[i].staffname;
					}
				    if(document.getElementById("beautyGw").value!="" 
					  	&& document.getElementById("beautyGw").value==lsStaffinfo[i].bstaffno)
					{
					    document.getElementById("beautyGwName").value=lsStaffinfo[i].staffname;
					}
				 }
			
	   }
	   
	   function loadpaymodeinfoMessage(request)
	   {
	       	var responsetext = eval("(" + request.responseText + ")");
  			if(responsetext.lsDpayinfo!=null && responsetext.lsDpayinfo.length>0)
			{
				commoninfodivdetial_pay.options.data=$.extend(true, {},{Rows: responsetext.lsDpayinfo,Total: responsetext.lsDpayinfo.length});
				commoninfodivdetial_pay.loadData(true);            	
			}
			else
			{
				commoninfodivdetial_pay.options.data=$.extend(true, {},{Rows: null,Total: 0});
				commoninfodivdetial_pay.loadData(true);   
				itemclick_addpay();
			}
		}
      
      	function loadServiceDetialMessage(request)
      	{
      		var responsetext = eval("(" + request.responseText + ")");
  			if(responsetext.lsDconsumeinfos!=null && responsetext.lsDconsumeinfos.length>0)
			{
				document.getElementById("recommendempid").value=checkNull(responsetext.curMconsumeinfo.recommendempid);
				document.getElementById("recommendempname").value=checkNull(responsetext.curMconsumeinfo.recommendempname);
				document.getElementById("recommendempinid").value=checkNull(responsetext.curMconsumeinfo.recommendempinid);
				
				$("#managerzf").val(responsetext.lsDconsumeinfos[0].beautyMangerNo);
				if($("#managerzf").val()=="")
				{
					$("#managerzfname").val("");
				}
				else
				{
					for(var i=0;i<lsStaffinfo.length;i++)
		    		{
		    			if($("#managerzf").val()==lsStaffinfo[i].bstaffno)
		    			{
		    				  document.getElementById("managerzfname").value=lsStaffinfo[i].staffname;
		    				  exists=1;
		    				  break;
		    			}
		    		}
				}
				
				
				$("#managerzfinid").val(responsetext.lsDconsumeinfos[0].beautyMangerNoinid);
				$("#managerff").val(responsetext.lsDconsumeinfos[0].beautyGw);
				if($("#managerff").val()=="")
				{
					$("#managerffname").val("");
				}
				else
				{
					for(var i=0;i<lsStaffinfo.length;i++)
		    		{
		    			if($("#managerff").val()==lsStaffinfo[i].bstaffno)
		    			{
		    				  document.getElementById("managerffname").value=lsStaffinfo[i].staffname;
		    				  exists=1;
		    				  break;
		    			}
		    		}
				}
				$("#managerffinid").val(responsetext.lsDconsumeinfos[0].beautyGwinid);
				
				commoninfodivdetial_service.options.data=$.extend(true, {},{Rows: responsetext.lsDconsumeinfos,Total: responsetext.lsDconsumeinfos.length});
				commoninfodivdetial_service.loadData(true);   
				initDetialGrid();         	
			}
			else
			{
				document.getElementById("recommendempid").value="";
				document.getElementById("recommendempname").value="";
				document.getElementById("recommendempinid").value="";
				commoninfodivdetial_service.options.data=$.extend(true, {},{Rows: null,Total: 0});
				commoninfodivdetial_service.loadData(true);   
				addService();
			}
			if(checkNull(responsetext.paramSp105)*1==1)
			{
				document.getElementById("recommendempid").readOnly="";
		    	document.getElementById("recommendempid").style.background="#FFFFFF";
			}
			else
			{
				document.getElementById("recommendempid").readOnly="readOnly";
		    	document.getElementById("recommendempid").style.background="#EDF1F8";
			}
      	}
      	
      	
      	function loadGoodSDetialMessage(request)
      	{
      		var responsetext = eval("(" + request.responseText + ")");
  			if(responsetext.lsDconsumeinfos!=null && responsetext.lsDconsumeinfos.length>0)
			{
				document.getElementById("recommendempid").value=checkNull(responsetext.curMconsumeinfo.recommendempid);
				document.getElementById("recommendempname").value=checkNull(responsetext.curMconsumeinfo.recommendempname);
				document.getElementById("recommendempinid").value=checkNull(responsetext.curMconsumeinfo.recommendempinid);
				$("#managerz").val(responsetext.lsDconsumeinfos[0].beautyMangerNo);
				if($("#managerz").val()=="")
				{
					$("#managerzname").val("");
				}
				else
				{
					for(var i=0;i<lsStaffinfo.length;i++)
		    		{
		    			if($("#managerz").val()==lsStaffinfo[i].bstaffno)
		    			{
		    				  document.getElementById("managerzname").value=lsStaffinfo[i].staffname;
		    				  exists=1;
		    				  break;
		    			}
		    		}
				}
				$("#managerzinid").val(responsetext.lsDconsumeinfos[0].beautyMangerNoinid);
				$("#managerf").val(responsetext.lsDconsumeinfos[0].beautyGw);
				if($("#managerf").val()=="")
				{
					$("#managerfname").val("");
				}
				else
				{
					for(var i=0;i<lsStaffinfo.length;i++)
		    		{
		    			if($("#managerf").val()==lsStaffinfo[i].bstaffno)
		    			{
		    				  document.getElementById("managerfname").value=lsStaffinfo[i].staffname;
		    				  exists=1;
		    				  break;
		    			}
		    		}
				}
				$("#managerfinid").val(responsetext.lsDconsumeinfos[0].beautyGwinid);
				commoninfodivdetial_goods.options.data=$.extend(true, {},{Rows: responsetext.lsDconsumeinfos,Total: responsetext.lsDconsumeinfos.length});
				commoninfodivdetial_goods.loadData(true);   
				initDetialGrid_G();         	
			}
			else
			{
				document.getElementById("recommendempid").value="";
				document.getElementById("recommendempname").value="";
				document.getElementById("recommendempinid").value="";
				commoninfodivdetial_goods.options.data=$.extend(true, {},{Rows: null,Total: 0});
				commoninfodivdetial_goods.loadData(true);   
				addGoods();
			}
      	}
      	
      	function editServiceDetailInfo()
      	{
      		if(document.getElementById("backflag").value==1)
        	{
        			$.ligerDialog.error("选中单据已经反消或被反消,不能修改业绩!");
					return ;
        	}
        	if(commoninfodivdetial_service.rows.length*1>0 )
			{
				   commoninfodivdetial_service.endEdit();
			}
      		 handPayList();
      		 var requestUrl ="ac008/postSerivceInfo.action";
			 var params = "strCurCompId="+document.getElementById("strServiceCompId").value;	
			 params=params+"&strCurBillId="+document.getElementById("strServiceBillId").value;
			 params=params+"&mangerflag=0";
			 params=params+"&strPayMode1="+document.getElementById("strPayMode1").value;
			 params=params+"&dPayAmt1="+document.getElementById("dPayAmt1").value;
			 params=params+"&strPayMode2="+document.getElementById("strPayMode2").value;
			 params=params+"&dPayAmt2="+document.getElementById("dPayAmt2").value;
			 params=params+"&strPayMode3="+document.getElementById("strPayMode3").value;
			 params=params+"&dPayAmt3="+document.getElementById("dPayAmt3").value;
			 params=params+"&strPayMode4="+document.getElementById("strPayMode4").value;
			 params=params+"&dPayAmt4="+document.getElementById("dPayAmt4").value;
			 params=params+"&strPayMode5="+document.getElementById("strPayMode5").value;
			 params=params+"&dPayAmt5="+document.getElementById("dPayAmt5").value;
			 params=params+"&strPayMode6="+document.getElementById("strPayMode6").value;
			 params=params+"&dPayAmt6="+document.getElementById("dPayAmt6").value;
			 params=params+"&strCurDate="+document.getElementById("strCurDate").value;
			 params=params+"&recommendempid="+document.getElementById("recommendempid").value;
			 params=params+"&recommendempinid="+document.getElementById("recommendempinid").value;
			 params=params+"&managerz="+document.getElementById("managerzf").value;
			 params=params+"&managerzinid="+document.getElementById("managerzfinid").value;
			 params=params+"&managerf="+document.getElementById("managerff").value;
			 params=params+"&managerfinid="+document.getElementById("managerffinid").value;
			 var curjosnparam="";
			 var strJsonParam_detial="";
			 var newCustomerByTjCount=0;//推荐新客数量
			 for (var rowid in commoninfodivdetial_service.records)
			 {
				var row =commoninfodivdetial_service.records[rowid];
				curjosnparam=JSON.stringify(row);
				curjosnparam=curjosnparam.replace("%","");
				curjosnparam=curjosnparam.replace("#","");
				if(strJsonParam_detial!="")
					strJsonParam_detial=strJsonParam_detial+",";
				strJsonParam_detial= strJsonParam_detial+curjosnparam;
				if(row['csfirsttype']=="3")
				{
					newCustomerByTjCount=newCustomerByTjCount*1+ForDight(checkNull(row['csitemcount'])*1,1);
				}
			} 
			if(newCustomerByTjCount*1>1)
			{
				$.ligerDialog.error("新客推荐项目只允许标记一个项目,请确认标记项目数量!");
				postState=0;
				return ;
			}
			if(document.getElementById("recommendempid").value!="" && newCustomerByTjCount*1==0)
			{
				$.ligerDialog.error("该消费单必须指定一个美容项目为新客推荐项目(大工类型)!");
				postState=0;
				return ;
			}
			if(document.getElementById("recommendempid").value=="" && newCustomerByTjCount*1!=0)
			{
				$.ligerDialog.error("该消费有美容项目为推荐项目,必须输入介绍人!");
				postState=0;
				return ;
			}
			params=params+"&strJsonParam_service=["+strJsonParam_detial+"]";
			var responseMethod="postSerivceInfoMessage";				
			sendRequestForParams_p(requestUrl,responseMethod,params); 	
      	}
      	
      	
      	function editGoodsDetailInfo()
      	{
      		if(document.getElementById("backflag").value==1)
        	{
        			$.ligerDialog.error("选中单据已经反消或被反消,不能修改业绩!");
					return ;
        	}
        	if(commoninfodivdetial_goods.rows.length*1>0 )
			{
				   commoninfodivdetial_goods.endEdit();
			}
      		 handPayList_G();
      		 var requestUrl ="ac008/postSerivceInfo.action";
			 var params = "strCurCompId="+document.getElementById("strServiceCompId").value;	
			 params=params+"&mangerflag=0";
			 params=params+"&strCurBillId="+document.getElementById("strServiceBillId").value;
			 params=params+"&strPayMode1="+document.getElementById("strPayMode1").value;
			 params=params+"&dPayAmt1="+document.getElementById("dPayAmt1").value;
			 params=params+"&strPayMode2="+document.getElementById("strPayMode2").value;
			 params=params+"&dPayAmt2="+document.getElementById("dPayAmt2").value;
			 params=params+"&strPayMode3="+document.getElementById("strPayMode3").value;
			 params=params+"&dPayAmt3="+document.getElementById("dPayAmt3").value;
			 params=params+"&strPayMode4="+document.getElementById("strPayMode4").value;
			 params=params+"&dPayAmt4="+document.getElementById("dPayAmt4").value;
			 params=params+"&strPayMode5="+document.getElementById("strPayMode5").value;
			 params=params+"&dPayAmt5="+document.getElementById("dPayAmt5").value;
			 params=params+"&strPayMode6="+document.getElementById("strPayMode6").value;
			 params=params+"&dPayAmt6="+document.getElementById("dPayAmt6").value;
			 params=params+"&strCurDate="+document.getElementById("strCurDate").value;
			 params=params+"&recommendempid="+document.getElementById("recommendempid").value;
			 params=params+"&recommendempinid="+document.getElementById("recommendempinid").value;
			 params=params+"&managerz="+document.getElementById("managerz").value;
			 params=params+"&managerzinid="+document.getElementById("managerzinid").value;
			 params=params+"&managerf="+document.getElementById("managerf").value;
			 params=params+"&managerfinid="+document.getElementById("managerfinid").value;
			 var curjosnparam="";
			 var strJsonParam_detial="";
			 var firstShareamt=0;
			 var secondShareamt=0;
			 var thirthShareamt=0;
			 for (var rowid in commoninfodivdetial_goods.records)
			 {
				var row =commoninfodivdetial_goods.records[rowid];
				firstShareamt=checkNull(row.csfirstshare)*1;
				secondShareamt=checkNull(row.cssecondshare)*1;
				thirthShareamt=checkNull(row.csthirdshare)*1;
				if(ForDight(firstShareamt*1+secondShareamt*1+thirthShareamt*1,1)>ForDight(checkNull(row.csitemamt)*1,1))
				{
					$.ligerDialog.error("销售产品业绩分享总部不能超过销售额!");
					return ;
				}
				curjosnparam=JSON.stringify(row);
				curjosnparam=curjosnparam.replace("%","");
				curjosnparam=curjosnparam.replace("#","");
				if(strJsonParam_detial!="")
					strJsonParam_detial=strJsonParam_detial+",";
				strJsonParam_detial= strJsonParam_detial+curjosnparam;
			} 
			params=params+"&strJsonParam_service=["+strJsonParam_detial+"]";
			var responseMethod="postServiceGoodInfoMessage";				
			sendRequestForParams_p(requestUrl,responseMethod,params); 	
      	}
      	
      	function postServiceGoodInfoMessage(request)
      	{
      		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		 curSecondCommonKey="";
	        		 curSecondCommonValue="";
	        		  $.ligerDialog.success("更新成功,请重载单据查看!");
	        	}
	        	else
	        	{
	        		if(checkNull(responsetext.errorflag)!=1)
	        		{
	        			$.ligerDialog.error(strMessage);
	        		}
	        		else
	        		{
	        			 $.ligerDialog.confirm('修改隔天单据需要经理卡验证,请插入经理卡?', function (result)
		        		 {
		        		 	 if( result==true)
		           			{
		           				var CardControl=parent.document.getElementById("CardCtrl");
								CardControl.Init(parent.commtype,parent.prot,parent.password1,parent.password2,parent.password3);
								var cardNo=CardControl.ReadCard();
								if(cardNo=="")
								{
									$.ligerDialog.error("请初始化卡号");
									return;
						    	}
						    	var params ="mangerCardNo="+cardNo;	
	     						var requestUrl ="ac008/handInsertShare.action";
   								var responseMethod="postServideGoodMessage_manager";	
   								sendRequestForParams_p(requestUrl,responseMethod,params);		
		           			}
		        		 });
	        		}
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
      	}
      	
      	function postServideGoodMessage_manager(request)
      	{
      		var responsetext = eval("(" + request.responseText + ")");
        	var strMessage=responsetext.strMessage;
        	if(checkNull(strMessage)=="")
        	{	 
        		 var requestUrl ="ac008/postSerivceInfo.action";
		 		 var params = "strCurCompId="+document.getElementById("strServiceCompId").value;	
		 		 params=params+"&strCurBillId="+document.getElementById("strServiceBillId").value;
				 params=params+"&mangerflag=1";
				 params=params+"&strPayMode1="+document.getElementById("strPayMode1").value;
				 params=params+"&dPayAmt1="+document.getElementById("dPayAmt1").value;
				 params=params+"&strPayMode2="+document.getElementById("strPayMode2").value;
				 params=params+"&dPayAmt2="+document.getElementById("dPayAmt2").value;
				 params=params+"&strPayMode3="+document.getElementById("strPayMode3").value;
				 params=params+"&dPayAmt3="+document.getElementById("dPayAmt3").value;
				 params=params+"&strPayMode4="+document.getElementById("strPayMode4").value;
				 params=params+"&dPayAmt4="+document.getElementById("dPayAmt4").value;
				 params=params+"&strPayMode5="+document.getElementById("strPayMode5").value;
				 params=params+"&dPayAmt5="+document.getElementById("dPayAmt5").value;
				 params=params+"&strPayMode6="+document.getElementById("strPayMode6").value;
				 params=params+"&dPayAmt6="+document.getElementById("dPayAmt6").value;
				 params=params+"&strCurDate="+document.getElementById("strCurDate").value;
				 params=params+"&recommendempid="+document.getElementById("recommendempid").value;
				 params=params+"&recommendempinid="+document.getElementById("recommendempinid").value;
				 params=params+"&managerz="+document.getElementById("managerz").value;
				 params=params+"&managerzinid="+document.getElementById("managerzinid").value;
				 params=params+"&managerf="+document.getElementById("managerf").value;
				 params=params+"&managerfinid="+document.getElementById("managerfinid").value;
				 var curjosnparam="";
				 var strJsonParam_detial="";
				 var newCustomerByTjCount=0;//推荐新客数量
				 for(var rowid in commoninfodivdetial_goods.records)
				 {
					var row =commoninfodivdetial_goods.records[rowid];
					curjosnparam=JSON.stringify(row);
					curjosnparam=curjosnparam.replace("%","");
					curjosnparam=curjosnparam.replace("#","");
					if(strJsonParam_detial!="")
						strJsonParam_detial=strJsonParam_detial+",";
					strJsonParam_detial= strJsonParam_detial+curjosnparam;
				} 
				params=params+"&strJsonParam_service=["+strJsonParam_detial+"]";
				var responseMethod="postSerivceInfoMessage";				
				sendRequestForParams_p(requestUrl,responseMethod,params); 	
        	}
        	else
        	{
        		$.ligerDialog.error(strMessage);
        	}
      	}
      	
      	function postSerivceInfoMessage(request)
        {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		 curSecondCommonKey="";
	        		 curSecondCommonValue="";
	        		  $.ligerDialog.success("更新成功,请重载单据查看!");
	        	}
	        	else
	        	{
	        		if(checkNull(responsetext.errorflag)!=1)
	        		{
	        			$.ligerDialog.error(strMessage);
	        		}
	        		else
	        		{
	        			 $.ligerDialog.confirm('修改隔天单据需要经理卡验证,请插入经理卡?', function (result)
		        		 {
		        		 	 if( result==true)
		           			{
		           				var CardControl=parent.document.getElementById("CardCtrl");
								CardControl.Init(parent.commtype,parent.prot,parent.password1,parent.password2,parent.password3);
								var cardNo=CardControl.ReadCard();
								if(cardNo=="")
								{
									$.ligerDialog.error("请初始化卡号");
									return;
						    	}
						    	var params ="mangerCardNo="+cardNo;	
	     						var requestUrl ="ac008/handInsertShare.action";
   								var responseMethod="handEditConsumShareMessage";	
   								sendRequestForParams_p(requestUrl,responseMethod,params);		
		           			}
		        		 });
	        		}
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
        }
        
        function handEditConsumShareMessage(request)
        {
        		var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	 
	        		 var requestUrl ="ac008/postSerivceInfo.action";
			 		 var params = "strCurCompId="+document.getElementById("strServiceCompId").value;	
			 		 params=params+"&strCurBillId="+document.getElementById("strServiceBillId").value;
					 params=params+"&mangerflag=1";
					 params=params+"&strPayMode1="+document.getElementById("strPayMode1").value;
					 params=params+"&dPayAmt1="+document.getElementById("dPayAmt1").value;
					 params=params+"&strPayMode2="+document.getElementById("strPayMode2").value;
					 params=params+"&dPayAmt2="+document.getElementById("dPayAmt2").value;
					 params=params+"&strPayMode3="+document.getElementById("strPayMode3").value;
					 params=params+"&dPayAmt3="+document.getElementById("dPayAmt3").value;
					 params=params+"&strPayMode4="+document.getElementById("strPayMode4").value;
					 params=params+"&dPayAmt4="+document.getElementById("dPayAmt4").value;
					 params=params+"&strPayMode5="+document.getElementById("strPayMode5").value;
					 params=params+"&dPayAmt5="+document.getElementById("dPayAmt5").value;
					 params=params+"&strPayMode6="+document.getElementById("strPayMode6").value;
					 params=params+"&dPayAmt6="+document.getElementById("dPayAmt6").value;
					 params=params+"&strCurDate="+document.getElementById("strCurDate").value;
					 params=params+"&recommendempid="+document.getElementById("recommendempid").value;
					 params=params+"&recommendempinid="+document.getElementById("recommendempinid").value;
					 params=params+"&managerz="+document.getElementById("managerzf").value;
					 params=params+"&managerzinid="+document.getElementById("managerzfinid").value;
					 params=params+"&managerf="+document.getElementById("managerff").value;
					 params=params+"&managerfinid="+document.getElementById("managerffinid").value;
					 var curjosnparam="";
					 var strJsonParam_detial="";
					 var newCustomerByTjCount=0;//推荐新客数量
					 for (var rowid in commoninfodivdetial_service.records)
					 {
						var row =commoninfodivdetial_service.records[rowid];
						curjosnparam=JSON.stringify(row);
						curjosnparam=curjosnparam.replace("%","");
						curjosnparam=curjosnparam.replace("#","");
						if(strJsonParam_detial!="")
							strJsonParam_detial=strJsonParam_detial+",";
						strJsonParam_detial= strJsonParam_detial+curjosnparam;
					} 
					params=params+"&strJsonParam_service=["+strJsonParam_detial+"]";
					var responseMethod="postSerivceInfoMessage";				
					sendRequestForParams_p(requestUrl,responseMethod,params); 	
	        	}
	        	else
	        	{
	        		$.ligerDialog.error(strMessage);
	        	}
        }
        
        //反冲
        function backCardSaleInfo()
        {
        	
        	if(document.getElementById("backflag").value==1)
        	{
        			$.ligerDialog.error("选中单据已经反冲或被反冲,不能再次反冲!");
					return ;
        	}
        	if(postState==1)
			{
				$.ligerDialog.error("正在反充中,请不要连续保存!");
				return ;
			}
        	var billtypeFlag=loadRadiovalue("curReEditBillInfo.billType");
        	/*if(billtypeFlag==12)
			{
				$.ligerDialog.error("暂无此功能，请等几天在操作!");
				return;
			}*/
   			postState=1;
        	$.ligerDialog.confirm('确认返充当前选销售单?', function (result)
			{
				    if( result==true)
		           	{
						var requestUrl ="ac008/postBackCardSaleInfo.action";
			 			var params = "strCurCompId="+document.getElementById("strCompId").value;	
						params=params+"&strCurBillId="+document.getElementById("strBillId").value;
						params=params+"&changeseqno="+document.getElementById("changeseqno").value;
						params=params+"&nBillType="+loadRadiovalue("curReEditBillInfo.billType");
						params=params+"&strCurDate="+document.getElementById("strCurDate").value;
						params=params+"&strCurCardNo="+document.getElementById("strCurCardNo").value;
						var responseMethod="postBackCardSaleInfoMessage";	
						if(loadRadiovalue("curReEditBillInfo.billType")!=8)
						{
							var CardControl=parent.document.getElementById("CardCtrl");
							CardControl.Init(parent.commtype,parent.prot,parent.password1,parent.password2,parent.password3);
							var cardNo=CardControl.ReadCard();
							if(checkNull(cardNo)=="")
							{
								$.ligerDialog.error("请插入卡号!");
								postState=0;
								return ;
							}
							if(document.getElementById("strCurCardNo").value!=checkNull(cardNo))
							{
								$.ligerDialog.error("插入卡号与返充卡号不一致,请确认!");
								postState=0;
								return ;
							}
						}
						
			 			sendRequestForParams_p(requestUrl,responseMethod,params);
					}
					else
					{
						postState=0;
					}
			}); 
        }
        
        function postBackCardSaleInfoMessage(request)
        {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		 curSecondCommonKey="";
	        		 curSecondCommonValue="";
	        		  $.ligerDialog.success("反充成功,请重载单据查看!");
	        	}
	        	else
	        	{
	        		 $.ligerDialog.error(strMessage);
	        	}
	        	postState=0;
        	}
			catch(e)
			{
				alert(e.message);
			}
        }
        
        var _outTradeNo=null;
        function backServiceDetailInfo()
        {
        		if(document.getElementById("backflag").value==1)
	        	{
	        			$.ligerDialog.error("选中单据已经返销或被返销,不能再次返销!");
						return ;
	        	}
        		var flag = false;
        		for (var rowid in commoninfodivdetial_service.records){
	   				var row =commoninfodivdetial_service.records[rowid];
	   				if(checkNull(row.costpricetype)=="104"){//接发-其他店介绍由于推荐提成，所以不能返销
	   					flag = true;
	   					break;
	        		}
	   			}
        		if(flag){
        			$.ligerDialog.error("选中的单据为接发活动中“其他店介绍”的单据，不能返销！");
        			return;
        		}
	        	if(postState==1)
				{
					$.ligerDialog.error("正在反充中,请不要连续保存!");
					return ;
				}
	   			postState=1;
        		$.ligerDialog.confirm('确认返销当前选收银单?', function (result)
				{
				    if( result==true)
		           	{
				    	//支付类型为第三方支付 1.支付宝 2.微信
				    	var scanpaytype = curMasterRecord.billinsertype;
				    	var _cspaymode= curMasterRecord.strPrjName;
				    	if(_cspaymode=="15" && (scanpaytype=="1" || scanpaytype=="2")){
				    		$.ligerDialog.confirm('此单据金额直接退款到客户的'+ (scanpaytype==1?'支付宝':'微信') +'账户上，是否继续?', function(_result){
				    			if(_result){
				    				var scantradeno = curMasterRecord.strPrjNo;
				    				_outTradeNo=scantradeno;//保存返销单时用
				    				var bcsbillid = curMasterRecord.strBillId;
				    				var scanpayamt = curMasterRecord.firstsaleamt;
				    				if(checkNull(scantradeno)=="" || scanpayamt*1==0){
				    					$.ligerDialog.warn("获取支付单据号或金额异常！请保留单据编号 "+ bcsbillid +" 联系总部财务。");
				    					return;
				    				}
				    				//主动撤销支付订单
				    				var paymethod = scanpaytype==1?"ali":"wechat";
			    			    	showDialogRe = $.ligerDialog.waitting('单据退款中,请稍候...');
			    			    	var params = {"strCurBillId":bcsbillid,"outTradeNo":scantradeno,"scanpayamt":scanpayamt*1,"_random": Math.random()};
			    					var url = contextURL+"/ac008/"+ paymethod +"Refund.action"; 
			    					$.post(url, params, function(data){
			    						showDialogRe.close();
			    						if(checkNull(data.strMessage)=="OK"){
			    							ajaxServiceDetailInfo();
			    						}else if(checkNull(data.strMessage)=="ERROR"){
			    							callPayRefund(paymethod,scantradeno);
			    						}else{
			    							$.ligerDialog.error(data.strMessage);
			    							postState=0;
			    						}
			    					}).error(function(e){$.ligerDialog.error("系统异常，请刷新或关闭页面重试！");});
				    			}else{
				    				postState=0;
				    			}
				    		});
				    	}else{
				    		_outTradeNo=null;
				    		ajaxServiceDetailInfo();
				    	}
					}
					else
					{
						postState=0;
					}
				}); 
        	 
        }
        function callPayRefund(paymethod,scantradeno){
        	showDialogRe = $.ligerDialog.waitting("单据撤销状态查询中，请稍候...");
	    	var params = {"outTradeNo":scantradeno,"_random": Math.random()};
			var url = contextURL+"/cc011/"+ paymethod +"State.action"; 
			$.post(url, params, function(data){
				showDialogRe.close();
				if(checkNull(data.strMessage)=="OK"){
					ajaxServiceDetailInfo();
				}else if(checkNull(data.strMessage)=="ERROR"){
					callPayRefund();
				}else{
					$.ligerDialog.error(data.strMessage);
					postState=0;
				}
			}).error(function(e){$.ligerDialog.error("系统异常，请刷新或关闭页面重试！");});
	    }
        function ajaxServiceDetailInfo(){
        	var requestUrl ="ac008/postBackSerivceInfo.action";
 			var params = "strCurCompId="+document.getElementById("strServiceCompId").value;	
			 params=params+"&strCurBillId="+document.getElementById("strServiceBillId").value;
			 params=params+"&strCurDate="+document.getElementById("strCurDate").value;
			 params=params+"&strCurCardNo="+document.getElementById("strCurCardNo").value;
			 if(checkNull(_outTradeNo)!=""){
				 params+="&outTradeNo="+_outTradeNo;
			 }
			 var responseMethod="postBackSerivceInfoMessage";	
			 var needcardflag=0;
			 for (var rowid in commoninfodivdetial_service.records)
			 {
				var row =commoninfodivdetial_service.records[rowid];
				if(checkNull(row.cspaymode)=="4" || checkNull(row.cspaymode)=="9")
				{
					needcardflag=1;
					break;
				}
			} 

			 if(needcardflag==1)
			 {
			 		var CardControl=parent.document.getElementById("CardCtrl");
					CardControl.Init(parent.commtype,parent.prot,parent.password1,parent.password2,parent.password3);
					var cardNo=CardControl.ReadCard();
					if(checkNull(cardNo)=="")
					{
						$.ligerDialog.error("请插入卡号!");
						postState=0;
						return ;
					}
					if(document.getElementById("strCurCardNo").value!=checkNull(cardNo))
					{
						$.ligerDialog.error("插入卡号与返销卡号不一致,请确认!");
						postState=0;
						return ;
					}
			 }
			 showDialogmanager = $.ligerDialog.waitting('正在返销中,请稍候...');
			 sendRequestForParams_p(requestUrl,responseMethod,params);
        }
        
        
         function backGoodsDetailInfo()
        {
        		if(document.getElementById("backflag").value==1)
	        	{
	        			$.ligerDialog.error("选中单据已经反消或被反消,不能再次反消!");
						return ;
	        	}
	        	if(postState==1)
				{
					$.ligerDialog.error("正在反充中,请不要连续保存!");
					return ;
				}
	   			postState=1;
        		$.ligerDialog.confirm('确认返销当前选收银单?', function (result)
				{
			
				    if( result==true)
		           	{
						var requestUrl ="ac008/postBackSerivceInfo.action";
			 			var params = "strCurCompId="+document.getElementById("strServiceCompId").value;	
						 params=params+"&strCurBillId="+document.getElementById("strServiceBillId").value;
						 params=params+"&strCurDate="+document.getElementById("strCurDate").value;
						 params=params+"&strCurCardNo="+document.getElementById("strCurCardNo").value;
						 var responseMethod="postBackSerivceInfoMessage";	
						 var needcardflag=0;
						 for (var rowid in commoninfodivdetial_service.records)
						 {
							var row =commoninfodivdetial_goods.records[rowid];
							if(checkNull(row.cspaymode)=="4" || checkNull(row.cspaymode)=="9")
							{
								needcardflag=1;
								break;
							}
						} 
			
						 if(needcardflag==1)
						 {
						 		var CardControl=parent.document.getElementById("CardCtrl");
								CardControl.Init(parent.commtype,parent.prot,parent.password1,parent.password2,parent.password3);
								var cardNo=CardControl.ReadCard();
								if(checkNull(cardNo)=="")
								{
									$.ligerDialog.error("请插入卡号!");
									postState=0;
									return ;
								}
								if(document.getElementById("strCurCardNo").value!=checkNull(cardNo))
								{
									$.ligerDialog.error("插入卡号与返销卡号不一致,请确认!");
									postState=0;
									return ;
								}
						 }
		
						 showDialogmanager = $.ligerDialog.waitting('正在返销中,请稍候...');
						 sendRequestForParams_p(requestUrl,responseMethod,params);
					}
					else
					{
						postState=0;
					}
				}); 
        	 
        }
        
        function postBackSerivceInfoMessage(request)
        {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		 curSecondCommonKey="";
	        		 curSecondCommonValue="";
	        		 document.getElementById("backflag").value=1;
	        		 $.ligerDialog.confirm('返销成功,是否需要打印', function (result)
					 {
						  if( result==true)
				          {
				          		viewTicketReport(responsetext.strCurCompId,responsetext.strNewBillId,document.getElementById("strCurCardNo").value,responsetext.strCurDate)
	        			  }
	        			  
	        		 }); 
	        	}
	        	else
	        	{
	        		 $.ligerDialog.error(strMessage);
	        	}
	        	postState=0;
	        	showDialogmanager.close();
        	}
			catch(e)
			{
				alert(e.message);
			}
        }
      	
      	function handPayList()
		{
			var paycode1="";
			var payamt1=0;
			var paycode2="";
			var payamt2=0;
			var paycode3="";
			var payamt3=0;
			var paycode4="";
			var payamt4=0;
			var paycode5="";
			var payamt5=0;
			var paycode6="";
			var payamt6=0;
			var curPaycode="";
			var curPayamt=0;
			var curProjectAmt=0;
			var curGoodsAmt=0;
			var totalCash=0;
			var totalBank=0;
			for (var rowid in commoninfodivdetial_service.records)
			{
				var row =commoninfodivdetial_service.records[rowid]; 
				curPaycode=checkNull(row.cspaymode);
				curPayamt=checkNull(row.csitemamt)*1;
				if(checkNull(row.csitemname)!="" && curPaycode!="" && curPayamt!=0)
				{
					if(paycode1=="" || paycode1==curPaycode)
					{
						paycode1=curPaycode;
						payamt1=payamt1*1+curPayamt;
					}
					else if( paycode2=="" || paycode2==curPaycode )
					{
						paycode2=curPaycode;
						payamt2=payamt2*1+curPayamt;
					}
					else if( paycode3=="" || paycode3==curPaycode )
					{
						paycode3=curPaycode;
						payamt3=payamt3*1+curPayamt;
					}
					else if( paycode4=="" || paycode4==curPaycode )
					{
						paycode4=curPaycode;
						payamt4=payamt4*1+curPayamt;
					}
					else if( paycode5=="" || paycode5==curPaycode )
					{
						paycode5=curPaycode;
						payamt5=payamt5*1+curPayamt;
					}
					else if( paycode6=="" || paycode6==curPaycode )
					{
						paycode6=curPaycode;
						payamt6=payamt6*1+curPayamt;
					}
				
				}
			}
			
			//第一支付
			if(paycode1!="")
			{
				document.getElementById("strPayMode1").value=paycode1;
				document.getElementById("dPayAmt1").value=payamt1;
			}
			else
			{
				document.getElementById("strPayMode1").value="";
				document.getElementById("dPayAmt1").value=0;
			}
			//第二支付
			if(paycode2!="")
			{
				document.getElementById("strPayMode2").value=paycode2;
				document.getElementById("dPayAmt2").value=payamt2;
			}
			else
			{
				document.getElementById("strPayMode2").value="";
				document.getElementById("dPayAmt2").value=0;
			}
			//第三支付
			if(paycode3!="")
			{
				document.getElementById("strPayMode3").value=paycode3;
				document.getElementById("dPayAmt3").value=payamt3;
			}
			else
			{
				document.getElementById("strPayMode3").value="";
				document.getElementById("dPayAmt3").value=0;
			}
			//第四支付
			if(paycode4!="")
			{
				document.getElementById("strPayMode4").value=paycode4;
				document.getElementById("dPayAmt4").value=payamt4;
			}
			else
			{
				document.getElementById("strPayMode4").value="";
				document.getElementById("dPayAmt4").value=0;
			}
			//第五支付
			if(paycode5!="")
			{
				document.getElementById("strPayMode5").value=paycode5;
				document.getElementById("dPayAmt5").value=payamt5;
			}
			else
			{
				document.getElementById("strPayMode5").value="";
				document.getElementById("dPayAmt5").value=0;
			}
			//第六支付
			if(paycode6!="")
			{
				document.getElementById("strPayMode6").value=paycode6;
				document.getElementById("dPayAmt6").value=payamt6;
			}
			else
			{
				document.getElementById("strPayMode6").value="";
				document.getElementById("dPayAmt6").value=0;
			}
		
		}
      	function initDetialGrid()
		{
			for (var rowid in commoninfodivdetial_service.records)
			{
				var row =commoninfodivdetial_service.records[rowid];
				showTextByinfoType(row);   		 
			}
		}
		
		function showTextByinfoType(rowdata)
		{
			var column  =null ;
			if(checkNull(rowdata.cspaymode)=="4"
			|| checkNull(rowdata.cspaymode)=="9"
			|| checkNull(rowdata.cspaymode)=="7"
			|| checkNull(rowdata.cspaymode)=="18"
			|| checkNull(rowdata.cspaymode)=="A")
			{
				column=commoninfodivdetial_service.columns[1];
				commoninfodivdetial_service.setCellEditing(rowdata, column, true);
			}
			else
			{
				column=commoninfodivdetial_service.columns[1];
				commoninfodivdetial_service.setCellEditing(rowdata, column, false);
			}
			column=commoninfodivdetial_service.columns[5];
			commoninfodivdetial_service.setCellEditing(rowdata, column, false);
			column=commoninfodivdetial_service.columns[7];
			commoninfodivdetial_service.setCellEditing(rowdata, column, false);
			if(checkNull(rowdata.csitemno)!="" 
			 	&& document.getElementById("paramSp097").value=="1"
			 	&& (checkNull(rowdata.csitemno).indexOf("498")==0
			 	|| checkNull(rowdata.csitemno).indexOf("490")==0
			 	|| checkNull(rowdata.csitemno).indexOf("46")==0
			 	))
		     {
			    column=commoninfodivdetial_service.columns[5];
				commoninfodivdetial_service.setCellEditing(rowdata, column, true);
				column=commoninfodivdetial_service.columns[7];
				commoninfodivdetial_service.setCellEditing(rowdata, column, true);
		     }	
		}
		
		
		function handPayList_G()
		{
			var paycode1="";
			var payamt1=0;
			var paycode2="";
			var payamt2=0;
			var paycode3="";
			var payamt3=0;
			var paycode4="";
			var payamt4=0;
			var paycode5="";
			var payamt5=0;
			var paycode6="";
			var payamt6=0;
			var curPaycode="";
			var curPayamt=0;
			var curProjectAmt=0;
			var curGoodsAmt=0;
			var totalCash=0;
			var totalBank=0;
			for (var rowid in commoninfodivdetial_goods.records)
			{
				var row =commoninfodivdetial_goods.records[rowid]; 
				curPaycode=checkNull(row.cspaymode);
				curPayamt=checkNull(row.csitemamt)*1;
				if(checkNull(row.csitemname)!="" && curPaycode!="" && curPayamt!=0)
				{
					if(paycode1=="" || paycode1==curPaycode)
					{
						paycode1=curPaycode;
						payamt1=payamt1*1+curPayamt;
					}
					else if( paycode2=="" || paycode2==curPaycode )
					{
						paycode2=curPaycode;
						payamt2=payamt2*1+curPayamt;
					}
					else if( paycode3=="" || paycode3==curPaycode )
					{
						paycode3=curPaycode;
						payamt3=payamt3*1+curPayamt;
					}
					else if( paycode4=="" || paycode4==curPaycode )
					{
						paycode4=curPaycode;
						payamt4=payamt4*1+curPayamt;
					}
					else if( paycode5=="" || paycode5==curPaycode )
					{
						paycode5=curPaycode;
						payamt5=payamt5*1+curPayamt;
					}
					else if( paycode6=="" || paycode6==curPaycode )
					{
						paycode6=curPaycode;
						payamt6=payamt6*1+curPayamt;
					}
				
				}
			}
			
			//第一支付
			if(paycode1!="")
			{
				document.getElementById("strPayMode1").value=paycode1;
				document.getElementById("dPayAmt1").value=payamt1;
			}
			else
			{
				document.getElementById("strPayMode1").value="";
				document.getElementById("dPayAmt1").value=0;
			}
			//第二支付
			if(paycode2!="")
			{
				document.getElementById("strPayMode2").value=paycode2;
				document.getElementById("dPayAmt2").value=payamt2;
			}
			else
			{
				document.getElementById("strPayMode2").value="";
				document.getElementById("dPayAmt2").value=0;
			}
			//第三支付
			if(paycode3!="")
			{
				document.getElementById("strPayMode3").value=paycode3;
				document.getElementById("dPayAmt3").value=payamt3;
			}
			else
			{
				document.getElementById("strPayMode3").value="";
				document.getElementById("dPayAmt3").value=0;
			}
			//第四支付
			if(paycode4!="")
			{
				document.getElementById("strPayMode4").value=paycode4;
				document.getElementById("dPayAmt4").value=payamt4;
			}
			else
			{
				document.getElementById("strPayMode4").value="";
				document.getElementById("dPayAmt4").value=0;
			}
			//第五支付
			if(paycode5!="")
			{
				document.getElementById("strPayMode5").value=paycode5;
				document.getElementById("dPayAmt5").value=payamt5;
			}
			else
			{
				document.getElementById("strPayMode5").value="";
				document.getElementById("dPayAmt5").value=0;
			}
			//第六支付
			if(paycode6!="")
			{
				document.getElementById("strPayMode6").value=paycode6;
				document.getElementById("dPayAmt6").value=payamt6;
			}
			else
			{
				document.getElementById("strPayMode6").value="";
				document.getElementById("dPayAmt6").value=0;
			}
		
		}
      	function initDetialGrid_G()
		{
			for (var rowid in commoninfodivdetial_goods.records)
			{
				var row =commoninfodivdetial_goods.records[rowid];
				showTextByinfoType(row);   		 
			}
		}
		
		function showTextByinfoType_G(rowdata)
		{
			var column  =null ;
			if(checkNull(rowdata.cspaymode)=="4"
			|| checkNull(rowdata.cspaymode)=="9"
			|| checkNull(rowdata.cspaymode)=="7"
			|| checkNull(rowdata.cspaymode)=="A")
			{
				column=commoninfodivdetial_goods.columns[1];
				commoninfodivdetial_goods.setCellEditing(rowdata, column, true);
			}
			else
			{
				column=commoninfodivdetial_goods.columns[1];
				commoninfodivdetial_goods.setCellEditing(rowdata, column, false);
			}
		}


        function  itemclick_adddetial (item)
        {
        	var row = commoninfodivdetial.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivdetial.addRow({
				            }, row, false);
        }
        function  itemclick_addpay (item)
        {
        	var row = commoninfodivdetial_pay.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivdetial_pay.addRow({ 
				                paymode: "",
				                payamt: "",
				                payremark: ""
				             
				            }, row, false);
        }
        
        
        function  addService()
        {
        	var row = commoninfodivdetial_service.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivdetial_service.addRow({ 
				            }, row, false);
        }
        
        function addGoods()
        {
        	var row = commoninfodivdetial_goods.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivdetial_goods.addRow({ 
				            }, row, false);
        }
        //---------------------------------------------回车事件
		function hotKeyOfSelf(key)
		{
			if(key==13)//回车
			{
				hotKeyEnter();
			}
			else if(key==114)//F3
			{
				//addRecord();
			}
			else if(key==115)//F4e
			{
				 //deleteCurRecord();
			}
			else if( key == 83 &&  event.altKey)
			{
				//editCurRootInfo();
			}
	
		}
		
		function  hotKeyEnter()
		{
		
			try
			{
				window.event.keyCode=9; //tab
				window.event.returnValue=true;
				//firstsaleridmanager.selectBox.hide();
   				//secondsaleridmanager.selectBox.hide();
   				//thirdsalerinidmanager.selectBox.hide();
   				//fifthsaleridmanager.selectBox.hide();
   				//sixthsaleridmanager.selectBox.hide();
   				//seventhsaleridmanager.selectBox.hide();
			}
			catch(e){alert(e.message);}
				
		}   
		
		function checkStaffValuex(obj)
		{
			try
			{
			
				if(obj.id=="firstsalerid")
				{
						if(obj.value!="" )
				    	{
				    		var exists=0;
				    		for(var i=0;i<lsStaffinfo.length;i++)
				    		{
				    			if(obj.value==lsStaffinfo[i].bstaffno)
				    			{
				    				  document.getElementById("firstsalername").value=lsStaffinfo[i].staffname;
				    				  exists=1;
				    				  break;
				    			}
				    		}
				    		if(exists==0)
				    		{
				    			$.ligerDialog.error("员工编号不存在");
				    			obj.value="";
				    			document.getElementById("firstsaleamt").value=0;
				    			document.getElementById("firstsalecashamt").value=0;
				    			document.getElementById("firstsalername").value="";
				    		}
				    	}
				    	else
				    	{
				    		document.getElementById("firstsalername").value="";
				    	}
				}
				else if(obj.id=="secondsalerid")
				{
						if(obj.value!="" && obj.value.indexOf('_')==-1)
				    	{
				    		var exists=0;
				    		for(var i=0;i<lsStaffinfo.length;i++)
				    		{
				    			if(obj.value==lsStaffinfo[i].bstaffno)
				    			{
				    				  document.getElementById("secondsalername").value=lsStaffinfo[i].staffname;
				    				  exists=1;
				    				  break;
				    			}
				    			
				    		}
				    		if(exists==0)
				    		{
				    			$.ligerDialog.error("员工编号不存在");
				    			obj.value="";
				    			document.getElementById("secondsaleamt").value=0;
				    			document.getElementById("secondsalecashamt").value=0;
				    			document.getElementById("secondsalername").value="";
				    		}
				    	}
				    	else
				    	{
				    		document.getElementById("secondsalername").value="";
				    	}
				}
				else if(obj.id=="thirdsalerid")
				{
						if(obj.value!="" && obj.value.indexOf('_')==-1)
				    	{
				    		var exists=0;
				    		for(var i=0;i<lsStaffinfo.length;i++)
				    		{
				    			if(obj.value==lsStaffinfo[i].bstaffno)
				    			{
        							  document.getElementById("thirdsalername").value=lsStaffinfo[i].staffname;
				    				  exists=1;
				    				  break;
				    			}
				    			
				    		}
				    		if(exists==0)
				    		{
				    			$.ligerDialog.error("员工编号不存在");
				    			obj.value="";
				    			document.getElementById("thirdsaleamt").value=0;
				    			document.getElementById("thirdsalecashamt").value=0;
				    			document.getElementById("thirdsalername").value="";
				    		}
				    	}
				    	else
				    	{
				    		document.getElementById("thirdsalername").value="";
				    	}
				}
				else if(obj.id=="fourthsalerid")
				{
						if(obj.value!="" && obj.value.indexOf('_')==-1)
				    	{
				    		var exists=0;
				    		for(var i=0;i<lsStaffinfo.length;i++)
				    		{
				    			if(obj.value==lsStaffinfo[i].bstaffno)
				    			{
				    				   document.getElementById("fourthsalername").value=lsStaffinfo[i].staffname;
				    				  exists=1;
				    				  break;
				    			}
				    			
				    		}
				    		if(exists==0)
				    		{
				    			$.ligerDialog.error("员工编号不存在");
				    			obj.value="";
				    			document.getElementById("fourthsaleamt").value=0;
				    			document.getElementById("fourthsalecashamt").value=0;
				    			document.getElementById("fourthsalername").value="";
				    		}
				    	}
				    	else
				    	{
				    		document.getElementById("fourthsalername").value="";
				    	}
				}
				else if(obj.id=="fifthsalerid")
				{
						if(obj.value!="" && obj.value.indexOf('_')==-1)
				    	{
				    		var exists=0;
				    		for(var i=0;i<lsStaffinfo.length;i++)
				    		{
				    			if(obj.value==lsStaffinfo[i].bstaffno)
				    			{
				    				  document.getElementById("fifthsalername").value=lsStaffinfo[i].staffname;
				    				  exists=1;
				    				  break;
				    			}
				    			
				    		}
				    		if(exists==0)
				    		{
				    			$.ligerDialog.error("员工编号不存在");
				    			obj.value="";
				    			document.getElementById("fifthsaleamt").value=0;
				    			document.getElementById("fifthsalecashamt").value=0;
				    			document.getElementById("fifthsalername").value="";
				    		}
				    	}
				    	else
				    	{
				    		document.getElementById("fifthsalername").value="";
				    	}
				}
				else if(obj.id=="sixthsalerid")
				{
						if(obj.value!="" && obj.value.indexOf('_')==-1)
				    	{
				    		var exists=0;
				    		for(var i=0;i<lsStaffinfo.length;i++)
				    		{
				    			if(obj.value==lsStaffinfo[i].bstaffno)
				    			{
				    				 document.getElementById("sixthsalername").value=lsStaffinfo[i].staffname;
				    				  exists=1;
				    				  break;
				    			}
				    			
				    		}
				    		if(exists==0)
				    		{
				    			$.ligerDialog.error("员工编号不存在");
				    			obj.value="";
				    			document.getElementById("sixthsaleamt").value=0;
				    			document.getElementById("sixthsalecashamt").value=0;
				    			document.getElementById("sixthsalername").value="";
				    		}
				    	}
				    	else
				    	{
				    		document.getElementById("sixthsalername").value="";
				    	}
				}
				else if(obj.id=="seventhsalerid")
				{
						if(obj.value!="" && obj.value.indexOf('_')==-1)
				    	{
				    		var exists=0;
				    		for(var i=0;i<lsStaffinfo.length;i++)
				    		{
				    			if(obj.value==lsStaffinfo[i].bstaffno)
				    			{
				    				  document.getElementById("seventhsalername").value=lsStaffinfo[i].staffname;
				    				  exists=1;
				    				  break;
				    			}
				    			
				    		}
				    		if(exists==0)
				    		{
				    			$.ligerDialog.error("员工编号不存在");
				    			obj.value="";
				    			document.getElementById("seventhsaleamt").value=0;
				    			document.getElementById("seventhsalecashamt").value=0;
				    			document.getElementById("seventhsalername").value="";
				    		}
				    	}
				    	else
				    	{
				    		document.getElementById("seventhsalername").value="";
				    	}
				}
				else if(obj.id=="eighthsalerid")
				{
						if(obj.value!="" && obj.value.indexOf('_')==-1)
				    	{
				    		var exists=0;
				    		for(var i=0;i<lsStaffinfo.length;i++)
				    		{
				    			if(obj.value==lsStaffinfo[i].bstaffno)
				    			{
				    				  document.getElementById("eighthsalername").value=lsStaffinfo[i].staffname;
				    				  exists=1;
				    				  break;
				    			}
				    			
				    		}
				    		if(exists==0)
				    		{
				    			$.ligerDialog.error("员工编号不存在");
				    			obj.value="";
				    			document.getElementById("eighthsaleamt").value=0;
				    			document.getElementById("eighthsalecashamt").value=0;
				    			document.getElementById("eighthsalername").value="";
				    		}
				    	}
				    	else
				    	{
				    		document.getElementById("eighthsalername").value="";
				    	}
				}
				else if(obj.id=="ninthsalerid")
				{
						if(obj.value!="" && obj.value.indexOf('_')==-1)
				    	{
				    		var exists=0;
				    		for(var i=0;i<lsStaffinfo.length;i++)
				    		{
				    			if(obj.value==lsStaffinfo[i].bstaffno)
				    			{
				    				  document.getElementById("ninthsalername").value=lsStaffinfo[i].staffname;
				    				  exists=1;
				    				  break;
				    			}
				    			
				    		}
				    		if(exists==0)
				    		{
				    			$.ligerDialog.error("员工编号不存在");
				    			obj.value="";
				    			document.getElementById("ninthsaleamt").value=0;
				    			document.getElementById("ninthsalecashamt").value=0;
				    			document.getElementById("ninthsalername").value="";
				    		}
				    	}
				    	else
				    	{
				    		document.getElementById("ninthsalername").value="";
				    	}
				}
				else if(obj.id=="tenthsalerid")
				{
						if(obj.value!="" && obj.value.indexOf('_')==-1)
				    	{
				    		var exists=0;
				    		for(var i=0;i<lsStaffinfo.length;i++)
				    		{
				    			if(obj.value==lsStaffinfo[i].bstaffno)
				    			{
				    				  document.getElementById("tenthsalername").value=lsStaffinfo[i].staffname;
				    				  exists=1;
				    				  break;
				    			}
				    			
				    		}
				    		if(exists==0)
				    		{
				    			$.ligerDialog.error("员工编号不存在");
				    			obj.value="";
				    			document.getElementById("tenthsaleamt").value=0;
				    			document.getElementById("tenthsalecashamt").value=0;
				    			document.getElementById("tenthsalername").value="";
				    		}
				    	}
				    	else
				    	{
				    		document.getElementById("tenthsalername").value="";
				    	}
				}
				else if(obj.id=="beautyManager")
				{
					if(obj.value!="" && obj.value.indexOf('_')==-1)
			    	{
			    		var exists=0;
			    		for(var i=0;i<lsStaffinfo.length;i++)
			    		{
			    			if(obj.value==lsStaffinfo[i].bstaffno)
			    			{
			    				  if(lsStaffinfo[i].position=="00101" || lsStaffinfo[i].position=="00103" ||  lsStaffinfo[i].position=="00104")
								  {
			    				 		document.getElementById("beautyManagerName").value=checkNull(lsStaffinfo[i].staffname);
			    				 		exists=1;
			    				  		break;
			    				  }
			    			}
			    			
			    		}
			    		if(exists==0)
			    		{
			    			obj.value="";
			    			document.getElementById("beautyManagerName").value="";
			    			//document.getElementById("seventhsaleamt").value=0;
			    			//document.getElementById("seventhsalecashamt").value=0;
			    			obj.select();
			    			obj.focus();
			    			$.ligerDialog.warn("输入的员工编号不存在或不是美容经理");
			    		}
			    	}
					else
			    	{
			    		document.getElementById("beautyManagerName").value="";
			    	}
				}
				else if(obj.id=="consultant")
				{
					if(obj.value!="" && obj.value.indexOf('_')==-1)
			    	{
			    		var exists=0;
			    		for(var i=0;i<lsStaffinfo.length;i++)
			    		{
			    			if(obj.value==lsStaffinfo[i].bstaffno)
			    			{
			    				if(lsStaffinfo[i].position=="00101" || lsStaffinfo[i].position=="00103" ||  lsStaffinfo[i].position=="00104")
								{
			    				 		document.getElementById("consultantName").value=checkNull(lsStaffinfo[i].staffname);
			    				 		exists=1;
			    				  		break;
			    				  }
			    			}
			    			
			    		}
			    		if(exists==0)
			    		{
			    			obj.value="";
			    			document.getElementById("consultantName").value="";
			    			//document.getElementById("seventhsaleamt").value=0;
			    			//document.getElementById("seventhsalecashamt").value=0;
			    			obj.select();
			    			obj.focus();
			    			$.ligerDialog.warn("输入的员工编号不存在或不是顾问");
			    		}
			    	}
					else
			    	{
			    		document.getElementById("consultantName").value="";
			    	}
				}
				else if(obj.id=="consultant1")
				{
					if(obj.value!="" && obj.value.indexOf('_')==-1)
			    	{
			    		var exists=0;
			    		for(var i=0;i<lsStaffinfo.length;i++)
			    		{
			    			if(obj.value==lsStaffinfo[i].bstaffno)
			    			{
			    				if(lsStaffinfo[i].position=="00101" || lsStaffinfo[i].position=="00103" ||  lsStaffinfo[i].position=="00104")
								{
			    				 		document.getElementById("consultant1Name").value=checkNull(lsStaffinfo[i].staffname);
			    				 		exists=1;
			    				  		break;
			    				  }
			    			}
			    			
			    		}
			    		if(exists==0)
			    		{
			    			obj.value="";
			    			document.getElementById("consultantName1").value="";
			    			obj.select();
			    			obj.focus();
			    			$.ligerDialog.warn("输入的员工编号不存在或不是顾问");
			    		}
			    	}
					else
			    	{
			    		document.getElementById("consultant1Name").value="";
			    	}
				}
				else if(obj.id=="beautyMangerNo")
				{
					if(obj.value!="" && obj.value.indexOf('_')==-1)
			    	{
			    		var exists=0;
			    		for(var i=0;i<lsStaffinfo.length;i++)
			    		{
			    			if(obj.value==lsStaffinfo[i].bstaffno)
			    			{
			    				if(lsStaffinfo[i].position=="00101" || lsStaffinfo[i].position=="00103" ||  lsStaffinfo[i].position=="00104")
								{
			    				 		document.getElementById("beautyMangerName").value=checkNull(lsStaffinfo[i].staffname);
			    				 		exists=1;
			    				  		break;
			    				  }
			    			}
			    			
			    		}
			    		if(exists==0)
			    		{
			    			obj.value="";
			    			document.getElementById("beautyMangerName").value="";
			    			obj.select();
			    			obj.focus();
			    			$.ligerDialog.warn("输入的员工编号不存在或不是顾问");
			    		}
			    	}
					else
			    	{
			    		document.getElementById("beautyMangerName").value="";
			    	}
				}
				else if(obj.id=="beautyGw")
				{
					if(obj.value!="" && obj.value.indexOf('_')==-1)
			    	{
			    		var exists=0;
			    		for(var i=0;i<lsStaffinfo.length;i++)
			    		{
			    			if(obj.value==lsStaffinfo[i].bstaffno)
			    			{
			    				if(lsStaffinfo[i].position=="00101" || lsStaffinfo[i].position=="00103" ||  lsStaffinfo[i].position=="00104")
								{
			    				 		document.getElementById("beautyGwName").value=checkNull(lsStaffinfo[i].staffname);
			    				 		exists=1;
			    				  		break;
			    				  }
			    			}
			    			
			    		}
			    		if(exists==0)
			    		{
			    			obj.value="";
			    			document.getElementById("beautyGwName").value="";
			    			obj.select();
			    			obj.focus();
			    			$.ligerDialog.warn("输入的员工编号不存在或不是顾问");
			    		}
			    	}
					else
			    	{
			    		document.getElementById("beautyGwName").value="";
			    	}
				}
				else if(obj.id=="managerz")
				{
					if(obj.value!="" && obj.value.indexOf('_')==-1)
			    	{
			    		var exists=0;
			    		for(var i=0;i<lsStaffinfo.length;i++)
			    		{
			    			if(obj.value==lsStaffinfo[i].bstaffno)
			    			{
			    				if(lsStaffinfo[i].position=="00101" || lsStaffinfo[i].position=="00103" ||  lsStaffinfo[i].position=="00104")
								{
			    				 		document.getElementById("managerzname").value=checkNull(lsStaffinfo[i].staffname);
			    				 		document.getElementById("managerzinid").value=checkNull(lsStaffinfo[i].manageno);
			    				 		exists=1;
			    				  		break;
			    				  }
			    			}
			    			
			    		}
			    		if(exists==0)
			    		{
			    			obj.value="";
			    			document.getElementById("managerzname").value="";
			    			document.getElementById("managerzinid").value="";
			    			obj.select();
			    			obj.focus();
			    			$.ligerDialog.warn("输入的员工编号不存在或不是顾问");
			    		}
			    	}
					else
			    	{
						document.getElementById("managerzname").value="";
		    			document.getElementById("managerzinid").value="";
			    	}
				}
				else if(obj.id=="managerf")
				{
					if(obj.value!="" && obj.value.indexOf('_')==-1)
			    	{
			    		var exists=0;
			    		for(var i=0;i<lsStaffinfo.length;i++)
			    		{
			    			if(obj.value==lsStaffinfo[i].bstaffno)
			    			{
			    				if(lsStaffinfo[i].position=="00101" || lsStaffinfo[i].position=="00103" ||  lsStaffinfo[i].position=="00104")
								{
			    				 		document.getElementById("managerfname").value=checkNull(lsStaffinfo[i].staffname);
			    				 		document.getElementById("managerfinid").value=checkNull(lsStaffinfo[i].manageno);
			    				 		exists=1;
			    				  		break;
			    				  }
			    			}
			    			
			    		}
			    		if(exists==0)
			    		{
			    			obj.value="";
			    			document.getElementById("managerfname").value="";
			    			document.getElementById("managerfinid").value="";
			    			obj.select();
			    			obj.focus();
			    			$.ligerDialog.warn("输入的员工编号不存在或不是顾问");
			    		}
			    	}
					else
			    	{
						document.getElementById("managerfname").value="";
		    			document.getElementById("managerfinid").value="";
			    	}
				}
				else if(obj.id=="managerzf")
				{
					if(obj.value!="" && obj.value.indexOf('_')==-1)
			    	{
			    		var exists=0;
			    		for(var i=0;i<lsStaffinfo.length;i++)
			    		{
			    			if(obj.value==lsStaffinfo[i].bstaffno)
			    			{
			    				if(lsStaffinfo[i].position=="00101" || lsStaffinfo[i].position=="00103" ||  lsStaffinfo[i].position=="00104")
								{
			    				 		document.getElementById("managerzfname").value=checkNull(lsStaffinfo[i].staffname);
			    				 		document.getElementById("managerzfinid").value=checkNull(lsStaffinfo[i].manageno);
			    				 		exists=1;
			    				  		break;
			    				  }
			    			}
			    			
			    		}
			    		if(exists==0)
			    		{
			    			obj.value="";
			    			document.getElementById("managerzfname").value="";
			    			document.getElementById("managerzfinid").value="";
			    			obj.select();
			    			obj.focus();
			    			$.ligerDialog.warn("输入的员工编号不存在或不是顾问");
			    		}
			    	}
					else
			    	{
						document.getElementById("managerzfname").value="";
		    			document.getElementById("managerzfinid").value="";
			    	}
				}
				else if(obj.id=="managerff")
				{
					if(obj.value!="" && obj.value.indexOf('_')==-1)
			    	{
			    		var exists=0;
			    		for(var i=0;i<lsStaffinfo.length;i++)
			    		{
			    			if(obj.value==lsStaffinfo[i].bstaffno)
			    			{
			    				if(lsStaffinfo[i].position=="00101" || lsStaffinfo[i].position=="00103" ||  lsStaffinfo[i].position=="00104")
								{
			    				 		document.getElementById("managerffname").value=checkNull(lsStaffinfo[i].staffname);
			    				 		document.getElementById("managerffinid").value=checkNull(lsStaffinfo[i].manageno);
			    				 		exists=1;
			    				  		break;
			    				  }
			    			}
			    			
			    		}
			    		if(exists==0)
			    		{
			    			obj.value="";
			    			document.getElementById("managerffname").value="";
			    			document.getElementById("managerffinid").value="";
			    			obj.select();
			    			obj.focus();
			    			$.ligerDialog.warn("输入的员工编号不存在或不是顾问");
			    		}
			    	}
				}
			}catch(e){alert(e.message);}
		}
		
		
		function editCurRootInfo()
		{
				var xbillType=loadRadiovalue("curReEditBillInfo.billType");
				if(document.getElementById("backflag").value==1)
	        	{
	        			$.ligerDialog.error("选中单据已经反充或被反充,不能修改业绩!");
						return ;
	        	}
				
	        	if(commoninfodivdetial_pay.rows.length*1>0 )
				{
					   commoninfodivdetial_pay.endEdit();
				}
				//document.getElementById("firstsalerid_h").value= $("#factcfirstsalerid").val();	
				//document.getElementById("secondsalerid_h").value= $("#factcsecondsalerid").val();	
				//document.getElementById("thirdsalerid_h").value=  $("#factcthirdsalerid").val();	
				//document.getElementById("fourthsalerid_h").value=  $("#factcfourthsalerid").val();	
				//document.getElementById("fifthsalerid_h").value=  $("#factcfifthsalerid").val();	
				//document.getElementById("sixthsalerid_h").value=  $("#factcsixthsalerid").val();	
				//document.getElementById("seventhsalerid_h").value=$("#factcseventhsalerid").val();	
				//document.getElementById("eighthsalerid_h").value=$("#factceighthsalerid").val();
				//document.getElementById("ninthsalerid_h").value=$("#factcninthsalerid").val();
				//document.getElementById("tenthsalerid_h").value=$("#factctenthsalerid").val();
				
				var totaltrsAmt=0;
				var otherotherAmt=0;
				var totaltrsCount=0;
				var ortherCount=0;
				var totalShareYeji=0;
				var totalmfShareYeji=0;
				var totaltrsShareYeji=0;
				var billtypeFlag=loadRadiovalue("curReEditBillInfo.billType");
				if (billtypeFlag=="0" || billtypeFlag=="1" || billtypeFlag=="2" || billtypeFlag=="3" || billtypeFlag=="4"|| billtypeFlag=="5" )
				{
					if(document.getElementById("billinsertype").value*1==0 && document.getElementById("strShareCondition").value=="1" )
					{
						$.ligerDialog.error("请确认卡来源");
			 			return;
					}
				}				
				var hairTotalAmt=0;
				var beatyTotalAmt=0;
				if(lsStaffinfo!=null && lsStaffinfo.length>0)
				{
						
						for(var i=0;i<lsStaffinfo.length;i++)
						{
							if(document.getElementById("firstsalerid").value!="" 
			 				&&  lsStaffinfo[i].bstaffno==document.getElementById("firstsalerid").value)
			 				{
			 					if(lsStaffinfo[i].department=="006" )
			 					{
			 						if(document.getElementById("firstsaleamt").value*1>10000 && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享金额不正确");
			 								return;
			 						}
			 						if(document.getElementById("firstsalecashamt").value*1>10000 && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享业绩不正确");
			 								return;
			 						}
			 						if(document.getElementById("billinsertype").value=="1" 
			 							&&  lsStaffinfo[i].bstaffno.indexOf("600")<0
			 							&& (billtypeFlag=="1" || billtypeFlag=="2" || billtypeFlag=="3" || billtypeFlag=="4"|| billtypeFlag=="5" ))
			 						{
			 							var exists=0;
		    							for (var rowid in commoninfodivdetial_oldcustomer.records)
										{
											var row =commoninfodivdetial_oldcustomer.records[rowid];
											if(checkNull(row.staffno)==lsStaffinfo[i].bstaffno)
											{
												exists=1;
											}
										} 
										if(exists==0)
										{
											$.ligerDialog.error("烫染师"+lsStaffinfo[i].bstaffno+"不在分享列表中,请核实!");
											return ;
										}
			 						}
			 						totaltrsAmt=totaltrsAmt*1+document.getElementById("firstsaleamt").value*1;
			 						totaltrsShareYeji=totaltrsShareYeji*1+document.getElementById("firstsalecashamt").value*1;
			 						totaltrsCount=totaltrsCount*1+1;
			 						
			 						
			 					}
			 					else if(lsStaffinfo[i].department=="004" )
			 					{
			 							if(document.getElementById("strShareCondition").value=="1" 
			 							&&  lsStaffinfo[i].bstaffno.indexOf("300")<0
			 							&& (billtypeFlag=="1" || billtypeFlag=="2" || billtypeFlag=="3" || billtypeFlag=="4"|| billtypeFlag=="5" ))
			    						{
			    							var exists=0;
			    							for (var rowid in commoninfodivdetial_oldcustomer.records)
											{
												var row =commoninfodivdetial_oldcustomer.records[rowid];
												if(checkNull(row.staffno)==lsStaffinfo[i].bstaffno)
												{
													exists=1;
												}
											} 
											if(exists==0)
											{
													$.ligerDialog.error("美发人员"+lsStaffinfo[i].bstaffno+"不在分享列表中,请核实!");
													return ;
											}
			    						}
			    						otherotherAmt=otherotherAmt*1+document.getElementById("firstsaleamt").value*1;
			 							ortherCount=ortherCount*1+1;
			    						hairTotalAmt=hairTotalAmt*1+document.getElementById("firstsaleamt").value*1;
			    						totalShareYeji=totalShareYeji*1+document.getElementById("firstsalecashamt").value*1;
			    						totalmfShareYeji=totalmfShareYeji*1+document.getElementById("firstsalecashamt").value*1;
			 					}
			 					else
			 					{
			 						otherotherAmt=otherotherAmt*1+document.getElementById("firstsaleamt").value*1;
			 						ortherCount=ortherCount*1+1;
			 						beatyTotalAmt=beatyTotalAmt*1+document.getElementById("firstsaleamt").value*1;
			 						totalShareYeji=totalShareYeji*1+document.getElementById("firstsalecashamt").value*1;
			 					}
			 					
			 				}
			 				if(document.getElementById("secondsalerid").value!="" 
			 				&&  lsStaffinfo[i].bstaffno==document.getElementById("secondsalerid").value)
			 				{
			 					if(lsStaffinfo[i].department=="006")
			 					{
			 						if(document.getElementById("secondsaleamt").value*1>10000  && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享金额不正确");
			 								return;
			 						}
			 						if(document.getElementById("secondsalecashamt").value*1>10000  && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享业绩不正确");
			 								return;
			 						}
			 						totaltrsAmt=totaltrsAmt*1+document.getElementById("secondsaleamt").value*1;
			 						totaltrsShareYeji=totaltrsShareYeji*1+document.getElementById("secondsalecashamt").value*1;
			 						totaltrsCount=totaltrsCount*1+1;
			 						if(document.getElementById("billinsertype").value=="1"
			 							&&  lsStaffinfo[i].bstaffno.indexOf("600")<0
			 							&& (billtypeFlag=="1" || billtypeFlag=="2" || billtypeFlag=="3" || billtypeFlag=="4"|| billtypeFlag=="5" ))
			 						{
			 							var exists=0;
		    							for (var rowid in commoninfodivdetial_oldcustomer.records)
										{
											var row =commoninfodivdetial_oldcustomer.records[rowid];
											if(checkNull(row.staffno)==lsStaffinfo[i].bstaffno)
											{
												exists=1;
											}
										} 
										if(exists==0)
										{
											$.ligerDialog.error("烫染师"+lsStaffinfo[i].bstaffno+"不在分享列表中,请核实!");
											return ;
										}
			 						}
			 					}
			 					else if(lsStaffinfo[i].department=="004" )
			 					{
			 							if(document.getElementById("strShareCondition").value=="1" 
			 							&&  lsStaffinfo[i].bstaffno.indexOf("300")<0
			 							&& (billtypeFlag=="1" || billtypeFlag=="2" || billtypeFlag=="3" || billtypeFlag=="4"|| billtypeFlag=="5" ))
			    						{
			    							var exists=0;
			    							for (var rowid in commoninfodivdetial_oldcustomer.records)
											{
												var row =commoninfodivdetial_oldcustomer.records[rowid];
												if(checkNull(row.staffno)==lsStaffinfo[i].bstaffno)
												{
													exists=1;
												}
											} 
											if(exists==0)
											{
													$.ligerDialog.error("美发人员"+lsStaffinfo[i].bstaffno+"不在分享列表中,请核实!");
													return ;
											}
			    						}
			    						otherotherAmt=otherotherAmt*1+document.getElementById("secondsaleamt").value*1;
			 							ortherCount=ortherCount*1+1;
			    						hairTotalAmt=hairTotalAmt*1+document.getElementById("secondsaleamt").value*1;
			    						totalShareYeji=totalShareYeji*1+document.getElementById("secondsalecashamt").value*1;
			    						totalmfShareYeji=totalmfShareYeji*1+document.getElementById("secondsalecashamt").value*1;
			 					}
			 					else
			 					{
			 						otherotherAmt=otherotherAmt*1+document.getElementById("secondsaleamt").value*1;
			 						ortherCount=ortherCount*1+1;
			 						beatyTotalAmt=beatyTotalAmt*1+document.getElementById("secondsaleamt").value*1;
			 						totalShareYeji=totalShareYeji*1+document.getElementById("secondsalecashamt").value*1;
			 					}
			 					
			 				}
			 				if(document.getElementById("thirdsalerid").value!="" 
			 				&&  lsStaffinfo[i].bstaffno==document.getElementById("thirdsalerid").value)
			 				{
			 					if(lsStaffinfo[i].department=="006" )
			 					{
			 						if(document.getElementById("thirdsaleamt").value*1>10000 && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享金额不正确");
			 								return;
			 						}
			 						if(document.getElementById("thirdsalecashamt").value*1>10000 && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享业绩不正确");
			 								return;
			 						}
			 						if(document.getElementById("billinsertype").value=="1" 
			 							&&  lsStaffinfo[i].bstaffno.indexOf("600")<0
			 							&& (billtypeFlag=="1" || billtypeFlag=="2" || billtypeFlag=="3" || billtypeFlag=="4"|| billtypeFlag=="5" ))
			 						{
			 							var exists=0;
		    							for (var rowid in commoninfodivdetial_oldcustomer.records)
										{
											var row =commoninfodivdetial_oldcustomer.records[rowid];
											if(checkNull(row.staffno)==lsStaffinfo[i].bstaffno)
											{
												exists=1;
											}
										} 
										if(exists==0)
										{
											$.ligerDialog.error("烫染师"+lsStaffinfo[i].bstaffno+"不在分享列表中,请核实!");
											return ;
										}
			 						}
			 						totaltrsAmt=totaltrsAmt*1+document.getElementById("thirdsaleamt").value*1;
			 						totaltrsShareYeji=totaltrsShareYeji*1+document.getElementById("thirdsalecashamt").value*1;
			 						totaltrsCount=totaltrsCount*1+1;
			 					}
			 					else if(lsStaffinfo[i].department=="004" )
			 					{
			 							if(document.getElementById("strShareCondition").value=="1" 
			 							&&  lsStaffinfo[i].bstaffno.indexOf("300")<0
			 							&& (billtypeFlag=="1" || billtypeFlag=="2" || billtypeFlag=="3" || billtypeFlag=="4"|| billtypeFlag=="5" ))
			    						{
			    							var exists=0;
			    							for (var rowid in commoninfodivdetial_oldcustomer.records)
											{
												var row =commoninfodivdetial_oldcustomer.records[rowid];
												if(checkNull(row.staffno)==lsStaffinfo[i].bstaffno)
												{
													exists=1;
												}
											} 
											if(exists==0)
											{
													$.ligerDialog.error("美发人员"+lsStaffinfo[i].bstaffno+"不在分享列表中,请核实!");
													return ;
											}
			    						}
			    						otherotherAmt=otherotherAmt*1+document.getElementById("thirdsaleamt").value*1;
			 							ortherCount=ortherCount*1+1;
			    						hairTotalAmt=hairTotalAmt*1+document.getElementById("thirdsaleamt").value*1;
			    						totalShareYeji=totalShareYeji*1+document.getElementById("thirdsalecashamt").value*1;
			    						totalmfShareYeji=totalmfShareYeji*1+document.getElementById("thirdsalecashamt").value*1;
			 					}
			 					else
			 					{
			 						otherotherAmt=otherotherAmt*1+document.getElementById("thirdsaleamt").value*1;
			 						ortherCount=ortherCount*1+1;
			 						beatyTotalAmt=beatyTotalAmt*1+document.getElementById("thirdsaleamt").value*1;
			 						totalShareYeji=totalShareYeji*1+document.getElementById("thirdsalecashamt").value*1;
			 					}
			 					
			 				}
			 				if(document.getElementById("fourthsalerid").value!="" 
			 				&&  lsStaffinfo[i].bstaffno==document.getElementById("fourthsalerid").value)
			 				{
			 					if(lsStaffinfo[i].department=="006" )
			 					{
			 						if(document.getElementById("fourthsaleamt").value*1>10000 && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享金额不正确");
			 								return;
			 						}
			 						if(document.getElementById("fourthsalecashamt").value*1>10000 && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享业绩不正确");
			 								return;
			 						}
			 						totaltrsAmt=totaltrsAmt*1+document.getElementById("fourthsaleamt").value*1;
			 						totaltrsShareYeji=totaltrsShareYeji*1+document.getElementById("fourthsalecashamt").value*1;
			 						totaltrsCount=totaltrsCount*1+1;
			 						if(document.getElementById("billinsertype").value=="1" 
			 							&&  lsStaffinfo[i].bstaffno.indexOf("600")<0
			 							&& (billtypeFlag=="1" || billtypeFlag=="2" || billtypeFlag=="3" || billtypeFlag=="4"|| billtypeFlag=="5" ))
			 						{
			 							var exists=0;
		    							for (var rowid in commoninfodivdetial_oldcustomer.records)
										{
											var row =commoninfodivdetial_oldcustomer.records[rowid];
											if(checkNull(row.staffno)==lsStaffinfo[i].bstaffno)
											{
												exists=1;
											}
										} 
										if(exists==0)
										{
											$.ligerDialog.error("烫染师"+lsStaffinfo[i].bstaffno+"不在分享列表中,请核实!");
											return ;
										}
			 						}
			 					}
			 					else if(lsStaffinfo[i].department=="004" )
			 					{
			 							if(document.getElementById("strShareCondition").value=="1" 
			 							&&  lsStaffinfo[i].bstaffno.indexOf("300")<0
			 							&& (billtypeFlag=="1" || billtypeFlag=="2" || billtypeFlag=="3" || billtypeFlag=="4"|| billtypeFlag=="5" ))
			    						{
			    							var exists=0;
			    							for (var rowid in commoninfodivdetial_oldcustomer.records)
											{
												var row =commoninfodivdetial_oldcustomer.records[rowid];
												if(checkNull(row.staffno)==lsStaffinfo[i].bstaffno)
												{
													exists=1;
												}
											} 
											if(exists==0)
											{
													$.ligerDialog.error("美发人员"+lsStaffinfo[i].bstaffno+"不在分享列表中,请核实!");
													return ;
											}
											otherotherAmt=otherotherAmt*1+document.getElementById("fourthsaleamt").value*1;
			 								ortherCount=ortherCount*1+1;
			    							hairTotalAmt=hairTotalAmt*1+document.getElementById("fourthsaleamt").value*1;
			    							totalShareYeji=totalShareYeji*1+document.getElementById("fourthsalecashamt").value*1;
			    							totalmfShareYeji=totalmfShareYeji*1+document.getElementById("fourthsalecashamt").value*1;
			    						}
			 					}
			 					else
			 					{
			 						otherotherAmt=otherotherAmt*1+document.getElementById("fourthsaleamt").value*1;
			 						ortherCount=ortherCount*1+1;
			 						beatyTotalAmt=beatyTotalAmt*1+document.getElementById("fourthsaleamt").value*1;
			 						totalShareYeji=totalShareYeji*1+document.getElementById("fourthsalecashamt").value*1;
			 					}
			 					
			 				}
			 				if(document.getElementById("fifthsalerid").value!="" 
			 				&&  lsStaffinfo[i].bstaffno==document.getElementById("fifthsalerid").value)
			 				{
			 					if(lsStaffinfo[i].department=="006" )
			 					{
			 						if(document.getElementById("fifthsaleamt").value*1>10000 && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享金额不正确");
			 								return;
			 						}
			 						if(document.getElementById("fifthsalecashamt").value*1>10000 && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享业绩不正确");
			 								return;
			 						}
			 						if(document.getElementById("billinsertype").value=="1" 
			 							&&  lsStaffinfo[i].bstaffno.indexOf("600")<0
			 							&& (billtypeFlag=="1" || billtypeFlag=="2" || billtypeFlag=="3" || billtypeFlag=="4"|| billtypeFlag=="5" ))
			 						{
			 							var exists=0;
		    							for (var rowid in commoninfodivdetial_oldcustomer.records)
										{
											var row =commoninfodivdetial_oldcustomer.records[rowid];
											if(checkNull(row.staffno)==lsStaffinfo[i].bstaffno)
											{
												exists=1;
											}
										} 
										if(exists==0)
										{
											$.ligerDialog.error("烫染师"+lsStaffinfo[i].bstaffno+"不在分享列表中,请核实!");
											return ;
										}
			 						}
			 						totaltrsAmt=totaltrsAmt*1+document.getElementById("fifthsaleamt").value*1;
			 						totaltrsShareYeji=totaltrsShareYeji*1+document.getElementById("fifthsalecashamt").value*1;
			 						totaltrsCount=totaltrsCount*1+1;
			 					}
			 					else if(lsStaffinfo[i].department=="004" )
			 					{
			 							if(document.getElementById("strShareCondition").value=="1" 
			 							&&  lsStaffinfo[i].bstaffno.indexOf("300")<0
			 							&& (billtypeFlag=="1" || billtypeFlag=="2" || billtypeFlag=="3" || billtypeFlag=="4"|| billtypeFlag=="5" ))
			    						{
			    							var exists=0;
			    							for (var rowid in commoninfodivdetial_oldcustomer.records)
											{
												var row =commoninfodivdetial_oldcustomer.records[rowid];
												if(checkNull(row.staffno)==lsStaffinfo[i].bstaffno)
												{
													exists=1;
												}
											} 
											if(exists==0)
											{
													$.ligerDialog.error("美发人员"+lsStaffinfo[i].bstaffno+"不在分享列表中,请核实!");
													return ;
											}
			    						}
			    						otherotherAmt=otherotherAmt*1+document.getElementById("fifthsaleamt").value*1;
			 							ortherCount=ortherCount*1+1;
			    						hairTotalAmt=hairTotalAmt*1+document.getElementById("fifthsaleamt").value*1;
			    						totalShareYeji=totalShareYeji*1+document.getElementById("fifthsalecashamt").value*1;
			    						totalmfShareYeji=totalmfShareYeji*1+document.getElementById("fifthsalecashamt").value*1;
			 					}
			 					else
			 					{
			 						otherotherAmt=otherotherAmt*1+document.getElementById("fifthsaleamt").value*1;
			 						ortherCount=ortherCount*1+1;
			 						beatyTotalAmt=beatyTotalAmt*1+document.getElementById("fifthsaleamt").value*1;
			 						totalShareYeji=totalShareYeji*1+document.getElementById("fifthsalecashamt").value*1;
			 					}
			 					
			 				}
			 				if(document.getElementById("sixthsalerid").value!="" 
			 				&&  lsStaffinfo[i].bstaffno==document.getElementById("sixthsalerid").value)
			 				{
			 					if(lsStaffinfo[i].department=="006" )
			 					{
			 						if(document.getElementById("sixthsaleamt").value*1>10000 && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享金额不正确");
			 								return;
			 						}
			 						if(document.getElementById("sixthsalecashamt").value*1>10000 && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享业绩不正确");
			 								return;
			 						}
			 						if(document.getElementById("billinsertype").value=="1" 
			 							&&  lsStaffinfo[i].bstaffno.indexOf("600")<0
			 							&& (billtypeFlag=="1" || billtypeFlag=="2" || billtypeFlag=="3" || billtypeFlag=="4"|| billtypeFlag=="5" ))
			 						{
			 							var exists=0;
		    							for (var rowid in commoninfodivdetial_oldcustomer.records)
										{
											var row =commoninfodivdetial_oldcustomer.records[rowid];
											if(checkNull(row.staffno)==lsStaffinfo[i].bstaffno)
											{
												exists=1;
											}
										} 
										if(exists==0)
										{
											$.ligerDialog.error("烫染师"+lsStaffinfo[i].bstaffno+"不在分享列表中,请核实!");
											return ;
										}
			 						}
			 						totaltrsAmt=totaltrsAmt*1+document.getElementById("sixthsaleamt").value*1;
			 						totaltrsShareYeji=totaltrsShareYeji*1+document.getElementById("sixthsalecashamt").value*1;
			 						totaltrsCount=totaltrsCount*1+1;
			 					}
			 					else if(lsStaffinfo[i].department=="004" )
			 					{
			 							if(document.getElementById("strShareCondition").value=="1" 
			 							&&  lsStaffinfo[i].bstaffno.indexOf("300")<0
			 							&& (billtypeFlag=="1" || billtypeFlag=="2" || billtypeFlag=="3" || billtypeFlag=="4"|| billtypeFlag=="5" ))
			    						{
			    							var exists=0;
			    							for (var rowid in commoninfodivdetial_oldcustomer.records)
											{
												var row =commoninfodivdetial_oldcustomer.records[rowid];
												if(checkNull(row.staffno)==lsStaffinfo[i].bstaffno)
												{
													exists=1;
												}
											} 
											if(exists==0)
											{
													$.ligerDialog.error("美发人员"+lsStaffinfo[i].bstaffno+"不在分享列表中,请核实!");
													return ;
											}
			    						}
			    						otherotherAmt=otherotherAmt*1+document.getElementById("sixthsaleamt").value*1;
			 							ortherCount=ortherCount*1+1;
			    						hairTotalAmt=hairTotalAmt*1+document.getElementById("sixthsaleamt").value*1;
			    						totalShareYeji=totalShareYeji*1+document.getElementById("sixthsalecashamt").value*1;
			    						totalmfShareYeji=totalmfShareYeji*1+document.getElementById("sixthsalecashamt").value*1;
			 					}
			 					else
			 					{
			 						otherotherAmt=otherotherAmt*1+document.getElementById("sixthsaleamt").value*1;
			 						ortherCount=ortherCount*1+1;
			 						beatyTotalAmt=beatyTotalAmt*1+document.getElementById("sixthsaleamt").value*1;
			 						totalShareYeji=totalShareYeji*1+document.getElementById("sixthsalecashamt").value*1;
			 					}
			 					
			 				}
			 				if(document.getElementById("seventhsalerid").value!="" 
			 				&&  lsStaffinfo[i].bstaffno==document.getElementById("seventhsalerid").value)
			 				{
			 					if(lsStaffinfo[i].department=="006" )
			 					{
			 						if(document.getElementById("seventhsaleamt").value*1>10000 && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享金额不正确");
			 								return;
			 						}
			 						if(document.getElementById("seventhsalecashamt").value*1>10000 && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享业绩不正确");
			 								return;
			 						}
			 						if(document.getElementById("billinsertype").value=="1" 
			 							&&  lsStaffinfo[i].bstaffno.indexOf("600")<0
			 							&& (billtypeFlag=="1" || billtypeFlag=="2" || billtypeFlag=="3" || billtypeFlag=="4"|| billtypeFlag=="5" ))
			 						{
			 							var exists=0;
		    							for (var rowid in commoninfodivdetial_oldcustomer.records)
										{
											var row =commoninfodivdetial_oldcustomer.records[rowid];
											if(checkNull(row.staffno)==lsStaffinfo[i].bstaffno)
											{
												exists=1;
											}
										} 
										if(exists==0)
										{
											$.ligerDialog.error("烫染师"+lsStaffinfo[i].bstaffno+"不在分享列表中,请核实!");
											return ;
										}
			 						}
			 						totaltrsAmt=totaltrsAmt*1+document.getElementById("seventhsaleamt").value*1;
			 						totaltrsShareYeji=totaltrsShareYeji*1+document.getElementById("seventhsalecashamt").value*1;
			 						totaltrsCount=totaltrsCount*1+1;
			 					}
			 					else if(lsStaffinfo[i].department=="004" )
			 					{
			 							if(document.getElementById("strShareCondition").value=="1" 
			 							&&  lsStaffinfo[i].bstaffno.indexOf("300")<0
			 							&& (billtypeFlag=="1" || billtypeFlag=="2" || billtypeFlag=="3" || billtypeFlag=="4"|| billtypeFlag=="5" ))
			    						{
			    							var exists=0;
			    							for (var rowid in commoninfodivdetial_oldcustomer.records)
											{
												var row =commoninfodivdetial_oldcustomer.records[rowid];
												if(checkNull(row.staffno)==lsStaffinfo[i].bstaffno)
												{
													exists=1;
												}
											} 
											if(exists==0)
											{
													$.ligerDialog.error("美发人员"+lsStaffinfo[i].bstaffno+"不在分享列表中,请核实!");
													return ;
											}
			    						}
			    						otherotherAmt=otherotherAmt*1+document.getElementById("seventhsaleamt").value*1;
			 							ortherCount=ortherCount*1+1;
			    						hairTotalAmt=hairTotalAmt*1+document.getElementById("seventhsaleamt").value*1;
			    						totalShareYeji=totalShareYeji*1+document.getElementById("seventhsalecashamt").value*1;
			    						totalmfShareYeji=totalmfShareYeji*1+document.getElementById("seventhsalecashamt").value*1;
			 					}
			 					else
			 					{
			 						otherotherAmt=otherotherAmt*1+document.getElementById("seventhsaleamt").value*1;
			 						ortherCount=ortherCount*1+1;
			 						beatyTotalAmt=beatyTotalAmt*1+document.getElementById("seventhsaleamt").value*1;
			 						totalShareYeji=totalShareYeji*1+document.getElementById("seventhsalecashamt").value*1;
			 					}
			 					
			 				}
			 				if(document.getElementById("eighthsalerid").value!="" 
			 				&&  lsStaffinfo[i].bstaffno==document.getElementById("eighthsalerid").value)
			 				{
			 					if(lsStaffinfo[i].department=="006" )
			 					{
			 						if(document.getElementById("eighthsaleamt").value*1>10000 && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享金额不正确");
			 								return;
			 						}
			 						if(document.getElementById("eighthsalecashamt").value*1>10000 && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享业绩不正确");
			 								return;
			 						}
			 						if(document.getElementById("billinsertype").value=="1" 
			 							&&  lsStaffinfo[i].bstaffno.indexOf("600")<0
			 							&& (billtypeFlag=="1" || billtypeFlag=="2" || billtypeFlag=="3" || billtypeFlag=="4"|| billtypeFlag=="5" ))
			 						{
			 							var exists=0;
		    							for (var rowid in commoninfodivdetial_oldcustomer.records)
										{
											var row =commoninfodivdetial_oldcustomer.records[rowid];
											if(checkNull(row.staffno)==lsStaffinfo[i].bstaffno)
											{
												exists=1;
											}
										} 
										if(exists==0)
										{
											$.ligerDialog.error("烫染师"+lsStaffinfo[i].bstaffno+"不在分享列表中,请核实!");
											return ;
										}
			 						}
			 						totaltrsAmt=totaltrsAmt*1+document.getElementById("eighthsaleamt").value*1;
			 						totaltrsShareYeji=totaltrsShareYeji*1+document.getElementById("eighthsalecashamt").value*1;
			 						totaltrsCount=totaltrsCount*1+1;
			 					}
			 					else if(lsStaffinfo[i].department=="004" )
			 					{
			 							if(document.getElementById("strShareCondition").value=="1" 
			 							&&  lsStaffinfo[i].bstaffno.indexOf("300")<0
			 							&& (billtypeFlag=="1" || billtypeFlag=="2" || billtypeFlag=="3" || billtypeFlag=="4"|| billtypeFlag=="5" ))
			    						{
			    							var exists=0;
			    							for (var rowid in commoninfodivdetial_oldcustomer.records)
											{
												var row =commoninfodivdetial_oldcustomer.records[rowid];
												if(checkNull(row.staffno)==lsStaffinfo[i].bstaffno)
												{
													exists=1;
												}
											} 
											if(exists==0)
											{
													$.ligerDialog.error("美发人员"+lsStaffinfo[i].bstaffno+"不在分享列表中,请核实!");
													return ;
											}
			    						}
			    						otherotherAmt=otherotherAmt*1+document.getElementById("eighthsaleamt").value*1;
			 							ortherCount=ortherCount*1+1;
			    						hairTotalAmt=hairTotalAmt*1+document.getElementById("eighthsaleamt").value*1;
			    						totalShareYeji=totalShareYeji*1+document.getElementById("eighthsalecashamt").value*1;
			    						totalmfShareYeji=totalmfShareYeji*1+document.getElementById("eighthsalecashamt").value*1;
			 					}
			 					else
			 					{
			 						otherotherAmt=otherotherAmt*1+document.getElementById("eighthsaleamt").value*1;
			 						ortherCount=ortherCount*1+1;
			 						beatyTotalAmt=beatyTotalAmt*1+document.getElementById("eighthsaleamt").value*1;
			 						totalShareYeji=totalShareYeji*1+document.getElementById("eighthsalecashamt").value*1;
			 					}
			 					
			 				}
			 				if(document.getElementById("ninthsalerid").value!="" 
			 				&&  lsStaffinfo[i].bstaffno==document.getElementById("ninthsalerid").value)
			 				{
			 					if(lsStaffinfo[i].department=="006" )
			 					{
			 						if(document.getElementById("ninthsaleamt").value*1>10000 && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享金额不正确");
			 								return;
			 						}
			 						if(document.getElementById("ninthsalecashamt").value*1>10000 && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享业绩不正确");
			 								return;
			 						}
			 						totaltrsAmt=totaltrsAmt*1+document.getElementById("ninthsaleamt").value*1;
			 						totaltrsShareYeji=totaltrsShareYeji*1+document.getElementById("ninthsalecashamt").value*1;
			 						totaltrsCount=totaltrsCount*1+1;
			 						if(document.getElementById("billinsertype").value=="1" 
			 							&&  lsStaffinfo[i].bstaffno.indexOf("600")<0
			 							&& (billtypeFlag=="1" || billtypeFlag=="2" || billtypeFlag=="3" || billtypeFlag=="4"|| billtypeFlag=="5" ))
			 						{
			 							var exists=0;
		    							for (var rowid in commoninfodivdetial_oldcustomer.records)
										{
											var row =commoninfodivdetial_oldcustomer.records[rowid];
											if(checkNull(row.staffno)==lsStaffinfo[i].bstaffno)
											{
												exists=1;
											}
										} 
										if(exists==0)
										{
											$.ligerDialog.error("烫染师"+lsStaffinfo[i].bstaffno+"不在分享列表中,请核实!");
											return ;
										}
			 						}
			 					}
			 					else if(lsStaffinfo[i].department=="004" )
			 					{
			 							if(document.getElementById("strShareCondition").value=="1" 
			 							&&  lsStaffinfo[i].bstaffno.indexOf("300")<0
			 							&& (billtypeFlag=="1" || billtypeFlag=="2" || billtypeFlag=="3" || billtypeFlag=="4"|| billtypeFlag=="5" ))
			    						{
			    							var exists=0;
			    							for (var rowid in commoninfodivdetial_oldcustomer.records)
											{
												var row =commoninfodivdetial_oldcustomer.records[rowid];
												if(checkNull(row.staffno)==lsStaffinfo[i].bstaffno)
												{
													exists=1;
												}
											} 
											if(exists==0)
											{
													$.ligerDialog.error("美发人员"+lsStaffinfo[i].bstaffno+"不在分享列表中,请核实!");
													return ;
											}
			    						}
			    						otherotherAmt=otherotherAmt*1+document.getElementById("ninthsaleamt").value*1;
			 							ortherCount=ortherCount*1+1;
			    						hairTotalAmt=hairTotalAmt*1+document.getElementById("ninthsaleamt").value*1;
			    						totalShareYeji=totalShareYeji*1+document.getElementById("ninthsalecashamt").value*1;
			    						totalmfShareYeji=totalmfShareYeji*1+document.getElementById("ninthsalecashamt").value*1;
			 					}
			 					else
			 					{
			 						otherotherAmt=otherotherAmt*1+document.getElementById("ninthsaleamt").value*1;
			 						ortherCount=ortherCount*1+1;
			 						beatyTotalAmt=beatyTotalAmt*1+document.getElementById("ninthsaleamt").value*1;
			 						totalShareYeji=totalShareYeji*1+document.getElementById("ninthsalecashamt").value*1;
			 					}
			 					
			 				}
			 				if(document.getElementById("tenthsalerid").value!="" 
			 				&&  lsStaffinfo[i].bstaffno==document.getElementById("tenthsalerid").value)
			 				{
			 					if(lsStaffinfo[i].department=="006")
			 					{
			 						if(document.getElementById("tenthsaleamt").value*1>10000  && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享金额不正确");
			 								return;
			 						}
			 						if(document.getElementById("tenthsalecashamt").value*1>10000  && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享业绩不正确");
			 								return;
			 						}
			 						totaltrsAmt=totaltrsAmt*1+document.getElementById("tenthsaleamt").value*1;
			 						totaltrsShareYeji=totaltrsShareYeji*1+document.getElementById("tenthsalecashamt").value*1;
			 						totaltrsCount=totaltrsCount*1+1;
			 						if(document.getElementById("billinsertype").value=="1" 
			 							&&  lsStaffinfo[i].bstaffno.indexOf("600")<0
			 							&& (billtypeFlag=="1" || billtypeFlag=="2" || billtypeFlag=="3" || billtypeFlag=="4"|| billtypeFlag=="5" ))
			 						{
			 							var exists=0;
		    							for (var rowid in commoninfodivdetial_oldcustomer.records)
										{
											var row =commoninfodivdetial_oldcustomer.records[rowid];
											if(checkNull(row.staffno)==lsStaffinfo[i].bstaffno)
											{
												exists=1;
											}
										} 
										if(exists==0)
										{
											$.ligerDialog.error("烫染师"+lsStaffinfo[i].bstaffno+"不在分享列表中,请核实!");
											return ;
										}
			 						}
			 					}
			 					else if(lsStaffinfo[i].department=="004" )
			 					{
			 							if(document.getElementById("strShareCondition").value=="1" 
			 							&&  lsStaffinfo[i].bstaffno.indexOf("300")<0
			 							&& (billtypeFlag=="1" || billtypeFlag=="2" || billtypeFlag=="3" || billtypeFlag=="4"|| billtypeFlag=="5" ))
			    						{
			    							var exists=0;
			    							for (var rowid in commoninfodivdetial_oldcustomer.records)
											{
												var row =commoninfodivdetial_oldcustomer.records[rowid];
												if(checkNull(row.staffno)==lsStaffinfo[i].bstaffno)
												{
													exists=1;
												}
											} 
											if(exists==0)
											{
													$.ligerDialog.error("美发人员"+lsStaffinfo[i].bstaffno+"不在分享列表中,请核实!");
													return ;
											}
			    						}
			    						otherotherAmt=otherotherAmt*1+document.getElementById("tenthsaleamt").value*1;
			 							ortherCount=ortherCount*1+1;
			    						hairTotalAmt=hairTotalAmt*1+document.getElementById("tenthsaleamt").value*1;
			    						totalShareYeji=totalShareYeji*1+document.getElementById("tenthsalecashamt").value*1;
			    						totalmfShareYeji=totalmfShareYeji*1+document.getElementById("tenthsalecashamt").value*1;
			 					}
			 					else
			 					{
			 						otherotherAmt=otherotherAmt*1+document.getElementById("tenthsaleamt").value*1;
			 						ortherCount=ortherCount*1+1;
			 						beatyTotalAmt=beatyTotalAmt*1+document.getElementById("tenthsaleamt").value*1;
			 						totalShareYeji=totalShareYeji*1+document.getElementById("tenthsalecashamt").value*1;
			 					}
			 					
			 				}
			 				
						}
					}
					
			
				if(ForDight(totaltrsCount*1,0) > ForDight(ortherCount*1,0))  
				{
					$.ligerDialog.error('烫染师分享人数不能超过正常销售人员分享人数,请确认!');
					return;
				} 
				var paymat=0;
				for (var rowid in commoninfodivdetial_pay.records)
				{
					paymat=paymat*1+commoninfodivdetial_pay.records[rowid]['payamt']*1;
				} 
				if(ForDight(totaltrsAmt*1,1) > ForDight(otherotherAmt*1,1))  
				{
					$.ligerDialog.error('烫染师分享金额不能超过正常销售人员分享总额,请确认!');
					return;
				} 		
				if (billtypeFlag=="0" || billtypeFlag=="1" || billtypeFlag=="2" || billtypeFlag=="3" || billtypeFlag=="4"|| billtypeFlag=="5" )
				{
					if(document.getElementById("billinsertype").value=="1" && ForDight(hairTotalAmt*1,1)>30000)
					{
							$.ligerDialog.error('美容部办卡美发部分享业绩不能超过30000,请确认!');
							return;
					}
				}	
				if(document.getElementById("strShareCondition").value=="1" )
				{
						if(ForDight(hairTotalAmt*1,1) > ForDight(paymat*1,1)  &&  ForDight(paymat*1,1)>0 )  
						{
							$.ligerDialog.error('美发正常销售分享总额不能超过总支付额度,请确认!');
							return;
						}
						if(ForDight(beatyTotalAmt*1,1) > ForDight(paymat*1,1)  &&  ForDight(paymat*1,1)>0)  
						{
							$.ligerDialog.error('美容正常销售分享总额不能超过总支付额度,请确认!');
							return;
						}  
				}
				else
				{
					if(ForDight(otherotherAmt*1,1) > ForDight(paymat*1,1) )  
					{
						$.ligerDialog.error('正常销售分享总额不能超过总支付额度,请确认!');
						return;
					} 
				}
				if(ForDight(totalShareYeji*1,1) > ForDight(paymat*1,1) &&  ForDight(paymat*1,1)>0 )  
				{
						$.ligerDialog.error('正常销售分享业绩总额不能超过总支付额度,请确认!');
						return;
				} 
				
				if(ForDight(totaltrsShareYeji*1,1) > ForDight(totalmfShareYeji*1,1)  &&  ForDight(paymat*1,1)>0 )  
				{
						$.ligerDialog.error('烫染师分享业绩不能超过正常销售人员分享总额,请确认!');
						return;
				} 
				
				
				
				var requestUrl ="ac008/postCurRootInfo.action";
				var params = "strCurCompId="+document.getElementById("strCompId").value;	
				params=params+"&strCurBillId="+document.getElementById("strBillId").value;
				params=params+"&changeseqno="+document.getElementById("changeseqno").value;
				params=params+"&nBillType="+loadRadiovalue("curReEditBillInfo.billType");		
				params=params+"&strCurDate="+document.getElementById("strCurDate").value;
				params=params+"&mangerflag=0";
				params=params+"&curReEditBillInfo.firstsalerid="+document.getElementById("firstsalerid").value;
				params=params+"&curReEditBillInfo.secondsalerid="+document.getElementById("secondsalerid").value;
				params=params+"&curReEditBillInfo.thirdsalerid="+document.getElementById("thirdsalerid").value;
				params=params+"&curReEditBillInfo.fourthsalerid="+document.getElementById("fourthsalerid").value;
				params=params+"&curReEditBillInfo.fifthsalerid="+document.getElementById("fifthsalerid").value;
				params=params+"&curReEditBillInfo.sixthsalerid="+document.getElementById("sixthsalerid").value;
				params=params+"&curReEditBillInfo.seventhsalerid="+document.getElementById("seventhsalerid").value;
				params=params+"&curReEditBillInfo.eighthsalerid="+document.getElementById("eighthsalerid").value;
				params=params+"&curReEditBillInfo.ninthsalerid="+document.getElementById("ninthsalerid").value;
				params=params+"&curReEditBillInfo.tenthsalerid="+document.getElementById("tenthsalerid").value;
				params=params+"&curReEditBillInfo.itemInid="+document.getElementById("iteminid").value;
				params=params+"&curReEditBillInfo.editType1="+document.getElementById("editType1").checked;
				params=params+"&curReEditBillInfo.editType2="+document.getElementById("editType2").checked;
				params=params+"&curReEditBillInfo.editType3="+document.getElementById("editType3").checked;
				params=params+"&curReEditBillInfo.editType4="+document.getElementById("editType4").checked;
				params=params+"&curReEditBillInfo.editType5="+document.getElementById("editType5").checked;
				params=params+"&curReEditBillInfo.editType6="+document.getElementById("editType6").checked;
				params=params+"&curReEditBillInfo.editType7="+document.getElementById("editType7").checked;
				params=params+"&curReEditBillInfo.editType8="+document.getElementById("editType8").checked;
				params=params+"&curReEditBillInfo.editType9="+document.getElementById("editType9").checked;
				params=params+"&curReEditBillInfo.editType10="+document.getElementById("editType10").checked;
				params=params+"&curReEditBillInfo.editType11="+document.getElementById("editType11").checked;
				params=params+"&curReEditBillInfo.editType12="+document.getElementById("editType12").checked;
				params=params+"&curReEditBillInfo.editType13="+document.getElementById("editType13").checked;
				params=params+"&curReEditBillInfo.editType14="+document.getElementById("editType14").checked;
				params=params+"&curReEditBillInfo.editType15="+document.getElementById("editType15").checked;
				
				
				params=params+"&curReEditBillInfo.firstsaleamt="+document.getElementById("firstsaleamt").value;
				params=params+"&curReEditBillInfo.secondsaleamt="+document.getElementById("secondsaleamt").value;
				params=params+"&curReEditBillInfo.thirdsaleamt="+document.getElementById("thirdsaleamt").value;
				params=params+"&curReEditBillInfo.fourthsaleamt="+document.getElementById("fourthsaleamt").value;
				params=params+"&curReEditBillInfo.fifthsaleamt="+document.getElementById("fifthsaleamt").value;
				params=params+"&curReEditBillInfo.sixthsaleamt="+document.getElementById("sixthsaleamt").value;
				params=params+"&curReEditBillInfo.seventhsaleamt="+document.getElementById("seventhsaleamt").value;
				params=params+"&curReEditBillInfo.eighthsaleamt="+document.getElementById("eighthsaleamt").value;
				params=params+"&curReEditBillInfo.ninthsaleamt="+document.getElementById("ninthsaleamt").value;
				params=params+"&curReEditBillInfo.tenthsaleamt="+document.getElementById("tenthsaleamt").value;
				
				params=params+"&curReEditBillInfo.firstsalecashamt="+document.getElementById("firstsalecashamt").value;
				params=params+"&curReEditBillInfo.secondsalecashamt="+document.getElementById("secondsalecashamt").value;
				params=params+"&curReEditBillInfo.thirdsalecashamt="+document.getElementById("thirdsalecashamt").value;
				params=params+"&curReEditBillInfo.fourthsalecashamt="+document.getElementById("fourthsalecashamt").value;
				params=params+"&curReEditBillInfo.fifthsalecashamt="+document.getElementById("fifthsalecashamt").value;
				params=params+"&curReEditBillInfo.sixthsalecashamt="+document.getElementById("sixthsalecashamt").value;
				params=params+"&curReEditBillInfo.seventhsalecashamt="+document.getElementById("seventhsalecashamt").value;
				params=params+"&curReEditBillInfo.eighthsalecashamt="+document.getElementById("eighthsalecashamt").value;
				params=params+"&curReEditBillInfo.ninthsalecashamt="+document.getElementById("ninthsalecashamt").value;
				params=params+"&curReEditBillInfo.tenthsalecashamt="+document.getElementById("tenthsalecashamt").value;
				
				params=params+"&curReEditBillInfo.beautyManager="+document.getElementById("beautyManager").value;
				params=params+"&curReEditBillInfo.consultant="+document.getElementById("consultant").value;
				params=params+"&curReEditBillInfo.consultant1="+document.getElementById("consultant1").value;
				
				params=params+"&curReEditBillInfo.beautyMangerNo="+document.getElementById("beautyMangerNo").value;
				params=params+"&curReEditBillInfo.beautyGw="+document.getElementById("beautyGw").value;
				
				
				params=params+"&curReEditBillInfo.billinsertype="+document.getElementById("billinsertype").value;
				var curjosnparam="";
				var strJsonParam_detial="";
				if(billtypeFlag!=12)
				{
					for (var rowid in commoninfodivdetial_pay.records)
					{
							var row =commoninfodivdetial_pay.records[rowid];
							curjosnparam=JSON.stringify(row);
							/*if(curjosnparam.indexOf("_id")>-1)
							{
								needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
								curjosnparam=curjosnparam.replace(needReplaceStr,"");
							}	*/
							
							if(checkNull(row.paymode)=="")
							{
								$.ligerDialog.error('支付方式不能为空,请确认!');
								return;
							}
								          		   
							if(strJsonParam_detial!="")
								strJsonParam_detial=strJsonParam_detial+",";
							strJsonParam_detial= strJsonParam_detial+curjosnparam;
					} 
				}
				params=params+"&strJsonParam_pay=["+strJsonParam_detial+"]";	  
				var responseMethod="postCurRootInfoMessage";				
				sendRequestForParams_p(requestUrl,responseMethod,params); 			
		}
		
		function postCurRootInfoMessage(request)
        {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		 curSecondCommonKey="";
	        		 curSecondCommonValue="";
	        		  $.ligerDialog.success("更新成功,请重载单据查看!");
	        	}
	        	else
	        	{
	        		if(checkNull(responsetext.errorflag)!=1)
	        		{
	        			$.ligerDialog.error(strMessage);
	        		}
	        		else
	        		{
	        			 $.ligerDialog.confirm('修改隔天单据需要经理卡验证,请插入经理卡?', function (result)
		        		 {
		        		 	 if( result==true)
		           			{
		           				var CardControl=parent.document.getElementById("CardCtrl");
								CardControl.Init(parent.commtype,parent.prot,parent.password1,parent.password2,parent.password3);
								var cardNo=CardControl.ReadCard();
								if(cardNo=="")
								{
									$.ligerDialog.error("请初始化卡号");
									return;
						    	}
						    	var params ="mangerCardNo="+cardNo;	
	     						var requestUrl ="ac008/handInsertShare.action";
   								var responseMethod="handEditShareMessage";	
   								sendRequestForParams_p(requestUrl,responseMethod,params);		
		           			}
		        		 });
	        		}
	        		
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
        }
        
        function handEditShareMessage(request)
        {
        		var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        	
	        		
		           				var requestUrl ="ac008/postCurRootInfo.action";
								var params = "strCurCompId="+document.getElementById("strCompId").value;	
								params=params+"&strCurBillId="+document.getElementById("strBillId").value;
								params=params+"&changeseqno="+document.getElementById("changeseqno").value;
								params=params+"&nBillType="+loadRadiovalue("curReEditBillInfo.billType");		
								params=params+"&strCurDate="+document.getElementById("strCurDate").value;
								params=params+"&mangerflag=1";
								params=params+"&curReEditBillInfo.itemInid="+document.getElementById("iteminid").value;
								params=params+"&curReEditBillInfo.firstsalerid="+document.getElementById("firstsalerid").value;
								params=params+"&curReEditBillInfo.secondsalerid="+document.getElementById("secondsalerid").value;
								params=params+"&curReEditBillInfo.thirdsalerid="+document.getElementById("thirdsalerid").value;
								params=params+"&curReEditBillInfo.fourthsalerid="+document.getElementById("fourthsalerid").value;
								params=params+"&curReEditBillInfo.fifthsalerid="+document.getElementById("fifthsalerid").value;
								params=params+"&curReEditBillInfo.sixthsalerid="+document.getElementById("sixthsalerid").value;
								params=params+"&curReEditBillInfo.seventhsalerid="+document.getElementById("seventhsalerid").value;
								params=params+"&curReEditBillInfo.eighthsalerid="+document.getElementById("eighthsalerid").value;
								params=params+"&curReEditBillInfo.ninthsalerid="+document.getElementById("ninthsalerid").value;
								params=params+"&curReEditBillInfo.tenthsalerid="+document.getElementById("tenthsalerid").value;
								params=params+"&curReEditBillInfo.editType1="+document.getElementById("editType1").checked;
								params=params+"&curReEditBillInfo.editType2="+document.getElementById("editType2").checked;
								params=params+"&curReEditBillInfo.editType3="+document.getElementById("editType3").checked;
								params=params+"&curReEditBillInfo.editType4="+document.getElementById("editType4").checked;
								params=params+"&curReEditBillInfo.editType5="+document.getElementById("editType5").checked;
								params=params+"&curReEditBillInfo.editType6="+document.getElementById("editType6").checked;
								params=params+"&curReEditBillInfo.editType7="+document.getElementById("editType7").checked;
								params=params+"&curReEditBillInfo.editType8="+document.getElementById("editType8").checked;
								params=params+"&curReEditBillInfo.editType9="+document.getElementById("editType9").checked;
								params=params+"&curReEditBillInfo.editType10="+document.getElementById("editType10").checked;
								params=params+"&curReEditBillInfo.editType11="+document.getElementById("editType11").checked;
								params=params+"&curReEditBillInfo.editType12="+document.getElementById("editType12").checked;
								params=params+"&curReEditBillInfo.editType13="+document.getElementById("editType13").checked;
								params=params+"&curReEditBillInfo.editType14="+document.getElementById("editType14").checked;
								params=params+"&curReEditBillInfo.editType15="+document.getElementById("editType15").checked;
								params=params+"&curReEditBillInfo.firstsaleamt="+document.getElementById("firstsaleamt").value;
								params=params+"&curReEditBillInfo.secondsaleamt="+document.getElementById("secondsaleamt").value;
								params=params+"&curReEditBillInfo.thirdsaleamt="+document.getElementById("thirdsaleamt").value;
								params=params+"&curReEditBillInfo.fourthsaleamt="+document.getElementById("fourthsaleamt").value;
								params=params+"&curReEditBillInfo.fifthsaleamt="+document.getElementById("fifthsaleamt").value;
								params=params+"&curReEditBillInfo.sixthsaleamt="+document.getElementById("sixthsaleamt").value;
								params=params+"&curReEditBillInfo.seventhsaleamt="+document.getElementById("seventhsaleamt").value;
								params=params+"&curReEditBillInfo.eighthsaleamt="+document.getElementById("eighthsaleamt").value;
								params=params+"&curReEditBillInfo.ninthsaleamt="+document.getElementById("ninthsaleamt").value;
								params=params+"&curReEditBillInfo.tenthsaleamt="+document.getElementById("tenthsaleamt").value;
								params=params+"&curReEditBillInfo.firstsalecashamt="+document.getElementById("firstsalecashamt").value;
								params=params+"&curReEditBillInfo.secondsalecashamt="+document.getElementById("secondsalecashamt").value;
								params=params+"&curReEditBillInfo.thirdsalecashamt="+document.getElementById("thirdsalecashamt").value;
								params=params+"&curReEditBillInfo.fourthsalecashamt="+document.getElementById("fourthsalecashamt").value;
								params=params+"&curReEditBillInfo.fifthsalecashamt="+document.getElementById("fifthsalecashamt").value;
								params=params+"&curReEditBillInfo.sixthsalecashamt="+document.getElementById("sixthsalecashamt").value;
								params=params+"&curReEditBillInfo.seventhsalecashamt="+document.getElementById("seventhsalecashamt").value;
								params=params+"&curReEditBillInfo.eighthsalecashamt="+document.getElementById("eighthsalecashamt").value;
								params=params+"&curReEditBillInfo.ninthsalecashamt="+document.getElementById("ninthsalecashamt").value;
								params=params+"&curReEditBillInfo.tenthsalecashamt="+document.getElementById("tenthsalecashamt").value;
								params=params+"&curReEditBillInfo.billinsertype="+document.getElementById("billinsertype").value;
								params=params+"&curReEditBillInfo.beautyManager="+document.getElementById("beautyManager").value;
								params=params+"&curReEditBillInfo.consultant="+document.getElementById("consultant").value;
								params=params+"&curReEditBillInfo.consultant1="+document.getElementById("consultant1").value;
								params=params+"&curReEditBillInfo.beautyMangerNo="+document.getElementById("beautyMangerNo").value;
								params=params+"&curReEditBillInfo.beautyGw="+document.getElementById("beautyGw").value;
								var curjosnparam="";
								var strJsonParam_detial="";
								for (var rowid in commoninfodivdetial_pay.records)
								{
										var row =commoninfodivdetial_pay.records[rowid];
										curjosnparam=JSON.stringify(row);
										if(strJsonParam_detial!="")
											strJsonParam_detial=strJsonParam_detial+",";
										strJsonParam_detial= strJsonParam_detial+curjosnparam;
								} 
								params=params+"&strJsonParam_pay=["+strJsonParam_detial+"]";	  
								var responseMethod="postCurRootInfoMessage";				
								sendRequestForParams_p(requestUrl,responseMethod,params); 
	        	}
	        	else
	        	{
	        		$.ligerDialog.error(strMessage);
	        	}
        }
        
        function editShare(obj)
        {	
        	if(obj.checked==true)
        	{
        		if(obj.value==1)
        		{
        			//firstsaleridmanager.setEnabled();
        			document.getElementById("firstsalerid").readOnly="";
        			document.getElementById("firstsaleamt").readOnly="";
        			document.getElementById("firstsalecashamt").readOnly="";
        			document.getElementById("firstsalerid").style.background="#FFFFFF";
        		}
        		else if(obj.value==2)
        		{
        			//secondsaleridmanager.setEnabled();
        			document.getElementById("secondsalerid").readOnly="";
        			document.getElementById("secondsaleamt").readOnly="";
        			document.getElementById("secondsalecashamt").readOnly="";
        			document.getElementById("secondsalerid").style.background="#FFFFFF";
        		}
        		else if(obj.value==3)
        		{
        			//thirdsalerinidmanager.setEnabled();
        			document.getElementById("thirdsalerid").readOnly="";
        			document.getElementById("thirdsaleamt").readOnly="";
        			document.getElementById("thirdsalecashamt").readOnly="";
        			document.getElementById("thirdsalerid").style.background="#FFFFFF";
        		}
        		else if(obj.value==4)
        		{
        			//fourthsaleridmanager.setEnabled();
        			document.getElementById("fourthsalerid").readOnly="";
        			document.getElementById("fourthsaleamt").readOnly="";
        			document.getElementById("fourthsalecashamt").readOnly="";
        			document.getElementById("fourthsalerid").style.background="#FFFFFF";
        		}
        		else if(obj.value==5)
        		{
        			//fifthsaleridmanager.setEnabled();
        			document.getElementById("fifthsalerid").readOnly="";
        			document.getElementById("fifthsaleamt").readOnly="";
        			document.getElementById("fifthsalecashamt").readOnly="";
        			document.getElementById("fifthsalerid").style.background="#FFFFFF";
        		}
        		else if(obj.value==6)
        		{
        			//sixthsaleridmanager.setEnabled();
        			document.getElementById("sixthsalerid").readOnly="";
        			document.getElementById("sixthsaleamt").readOnly="";
        			document.getElementById("sixthsalecashamt").readOnly="";
        			document.getElementById("sixthsalerid").style.background="#FFFFFF";
        		}
        		else if(obj.value==7)
        		{
        			//seventhsaleridmanager.setEnabled();
        			document.getElementById("seventhsalerid").readOnly="";
        			document.getElementById("seventhsaleamt").readOnly="";
        			document.getElementById("seventhsalecashamt").readOnly="";
        			document.getElementById("seventhsalerid").style.background="#FFFFFF";
        		}
        		else if(obj.value==8)
        		{
        			//eighthsaleridmanager.setEnabled();
        			document.getElementById("eighthsalerid").readOnly="";
        			document.getElementById("eighthsaleamt").readOnly="";
        			document.getElementById("eighthsalecashamt").readOnly="";
        			document.getElementById("eighthsalerid").style.background="#FFFFFF";
        		}
        		else if(obj.value==9)
        		{
        			//ninthsaleridmanager.setEnabled();
        			document.getElementById("ninthsalerid").readOnly="";
        			document.getElementById("ninthsaleamt").readOnly="";
        			document.getElementById("ninthsalecashamt").readOnly="";
        			document.getElementById("ninthsalerid").style.background="#FFFFFF";
        		}
        		else if(obj.value==10)
        		{
        			//tenthsaleridmanager.setEnabled();
        			document.getElementById("tenthsalerid").readOnly="";
        			document.getElementById("tenthsaleamt").readOnly="";
        			document.getElementById("tenthsalecashamt").readOnly="";
        			document.getElementById("tenthsalerid").style.background="#FFFFFF";
        		}
        		else if(obj.value==11)
        		{
        			//tenthsaleridmanager.setEnabled();
        			document.getElementById("beautyManager").readOnly="";
        			document.getElementById("beautyManager").style.background="#FFFFFF";
        		}
        		else if(obj.value==12)
        		{
        			//tenthsaleridmanager.setEnabled();
        			document.getElementById("consultant").readOnly="";
        			document.getElementById("consultant").style.background="#FFFFFF";
        		}
        		else if(obj.value==13)
        		{
        			//tenthsaleridmanager.setEnabled();
        			document.getElementById("consultant1").readOnly="";
        			document.getElementById("consultant1").style.background="#FFFFFF";
        		}
        		else if(obj.value==14)
        		{
        			//tenthsaleridmanager.setEnabled();
        			document.getElementById("beautyMangerNo").readOnly="";
        			document.getElementById("beautyMangerNo").style.background="#FFFFFF";
        		}
        		else if(obj.value==15)
        		{
        			//tenthsaleridmanager.setEnabled();
        			document.getElementById("beautyGw").readOnly="";
        			document.getElementById("beautyGw").style.background="#FFFFFF";
        		}
        	}
        	else
        	{
        	
        		if(obj.value==1)
        		{
        			//firstsaleridmanager.setDisabled();
        			document.getElementById("firstsalerid").readOnly="readOnly";
        			document.getElementById("firstsaleamt").readOnly="readOnly";
        			document.getElementById("firstsalecashamt").readOnly="readOnly";
        			document.getElementById("firstsalerid").style.background="#EDF1F8";
        		}
        		else if(obj.value==2)
        		{
        			//secondsaleridmanager.setDisabled();
        			document.getElementById("secondsalerid").readOnly="readOnly";
        			document.getElementById("secondsaleamt").readOnly="readOnly";
        			document.getElementById("secondsalecashamt").readOnly="readOnly";
        			document.getElementById("secondsalerid").style.background="#EDF1F8";
        		}
        		else if(obj.value==3)
        		{
        			//thirdsalerinidmanager.setDisabled();
        			document.getElementById("thirdsalerid").readOnly="readOnly";
        			document.getElementById("thirdsaleamt").readOnly="readOnly";
        			document.getElementById("thirdsalecashamt").readOnly="readOnly";
        			document.getElementById("fourthsalerid").style.background="#EDF1F8";
        		}
        		else if(obj.value==4)
        		{
        			//fourthsaleridmanager.setDisabled();
        			document.getElementById("fourthsalerid").readOnly="readOnly";
        			document.getElementById("fourthsaleamt").readOnly="readOnly";
        			document.getElementById("fourthsalecashamt").readOnly="readOnly";
        			document.getElementById("fourthsalerid").style.background="#EDF1F8";
        		}
        		else if(obj.value==5)
        		{
        			//fifthsaleridmanager.setDisabled();
        			document.getElementById("fifthsalerid").readOnly="readOnly";
        			document.getElementById("fifthsaleamt").readOnly="readOnly";
        			document.getElementById("fifthsalecashamt").readOnly="readOnly";
        			document.getElementById("fifthsalerid").style.background="#EDF1F8";
        		}
        		else if(obj.value==6)
        		{
        			//sixthsaleridmanager.setDisabled();
        			document.getElementById("sixthsalerid").readOnly="readOnly";
        			document.getElementById("sixthsaleamt").readOnly="readOnly";
        			document.getElementById("sixthsalecashamt").readOnly="readOnly";
        			document.getElementById("sixthsalerid").style.background="#EDF1F8";
        		}
        		else if(obj.value==7)
        		{
        			//seventhsaleridmanager.setDisabled();
        			document.getElementById("seventhsalerid").readOnly="readOnly";
        			document.getElementById("seventhsaleamt").readOnly="readOnly";
        			document.getElementById("seventhsalecashamt").readOnly="readOnly";
        			document.getElementById("seventhsalerid").style.background="#EDF1F8";
        		}
        		else if(obj.value==8)
        		{
        			//eighthsaleridmanager.setDisabled();
        			document.getElementById("eighthsalerid").readOnly="readOnly";
        			document.getElementById("eighthsaleamt").readOnly="readOnly";
        			document.getElementById("eighthsalecashamt").readOnly="readOnly";
        			document.getElementById("eighthsalerid").style.background="#EDF1F8";
        		}
        		else if(obj.value==9)
        		{
        			//ninthsaleridmanager.setDisabled();
        			document.getElementById("ninthsalerid").readOnly="readOnly";
        			document.getElementById("ninthsaleamt").readOnly="readOnly";
        			document.getElementById("ninthsalecashamt").readOnly="readOnly";
        			document.getElementById("ninthsalerid").style.background="#EDF1F8";
        		}
        		else if(obj.value==10)
        		{
        			//tenthsaleridmanager.setDisabled();
        			document.getElementById("tenthsalerid").readOnly="readOnly";
        			document.getElementById("tenthsaleamt").readOnly="readOnly";
        			document.getElementById("tenthsalecashamt").readOnly="readOnly";
        			document.getElementById("tenthsalerid").style.background="#EDF1F8";
        		}
        		else if(obj.value==11)
        		{
        			//tenthsaleridmanager.setEnabled();
        			document.getElementById("beautyManager").readOnly="readOnly";
        			document.getElementById("beautyManager").style.background="#EDF1F8";
        		}
        		else if(obj.value==12)
        		{
        			//tenthsaleridmanager.setEnabled();
        			document.getElementById("consultant").readOnly="readOnly";
        			document.getElementById("consultant").style.background="#EDF1F8";
        		}
        		else if(obj.value==13)
        		{
        			//tenthsaleridmanager.setEnabled();
        			document.getElementById("consultant1").readOnly="readOnly";
        			document.getElementById("consultant1").style.background="#EDF1F8";
        		}
        		else if(obj.value==14)
        		{
        			//tenthsaleridmanager.setEnabled();
        			document.getElementById("beautyMangerNo").readOnly="readOnly";
        			document.getElementById("beautyMangerNo").style.background="#EDF1F8";
        		}
        		else if(obj.value==15)
        		{
        			//tenthsaleridmanager.setEnabled();
        			document.getElementById("beautyGw").readOnly="readOnly";
        			document.getElementById("beautyGw").style.background="#EDF1F8";
        		}
        	}
        }
        
        function validatePaycode(obj)
        {
        	
        }
        
		function validateFristSaleType(obj)
		{
			var strEmpInid="";
			var curempValue="";
			var row=commoninfodivdetial.getRow(curRecord);
			if(curEmpManger!=null && curEmpManger.inputText.val()!="")
				curempValue=curEmpManger.inputText.val();
			else
				curempValue=obj.value;
			if(curEmpManger!=null)
			{
				curEmpManger=null;
			}
			if(curempValue!="")
			{
				if(curempValue.indexOf('_')>-1)
				{
					curempValue=curempValue.substring(0,curempValue.indexOf("_"));
				}
				if(curempValue!="" && curempValue.indexOf('_')==-1)
		    	{
					var exists=0;
			    	for(var i=0;i<lsStaffinfo.length;i++)
			    	{
			    		if(curempValue==lsStaffinfo[i].bstaffno)
			    		{
										//commoninfodivdetial_service.updateRow(curRecord,{csfirstsaler: parent.StaffInfo[i].bstaffno, csfirsttype:'2'});
					 					//document.getElementById("wCostFirstEmptype").focus();
					 					//vflag=1;
					 					//strEmpInid=parent.StaffInfo[i].manageno;
								if(lsStaffinfo[i].position=="006" && checkNull(row.csfirsttype)=="2" 
									&& (checkNull(row.csitemno)=="322" || checkNull(row.csitemno)=="323" ||  checkNull(row.csitemno)=="324" || checkNull(row.csitemno)=="325")
									&& (checkNull(row.cspaymode)=="1" || checkNull(row.cspaymode)=="6"))
								{
									commoninfodivdetial_service.updateRow(curRecord,{costpricetype:4});
									//document.getElementById("whairRecommendEmpId").readOnly=false;
								}
								else if((lsStaffinfo[i].position=="007" || lsStaffinfo[i].position=="00701" || lsStaffinfo[i].position=="00702") 
										&& checkNull(row.csfirsttype)=="2" 
										&&(checkNull(row.csitemno)=="322" || checkNull(row.csitemno)=="323" ||  checkNull(row.csitemno)=="324" || checkNull(row.csitemno)=="325")
										&& (checkNull(row.cspaymode)=="1" || checkNull(row.cspaymode)=="6"))
								{
									commoninfodivdetial_service.updateRow(curRecord,{costpricetype:5});
									//document.getElementById("whairRecommendEmpId").readOnly=false;
								}
								else
								{
									//document.getElementById("whairRecommendEmpId").value="";
									commoninfodivdetial_service.updateRow(curRecord,{hairRecommendEmpId:''});
									//document.getElementById("whairRecommendEmpId").readOnly=true;
								}
							
			    			strEmpInid=lsStaffinfo[i].manageno;
			    			commoninfodivdetial_service.updateRow(curRecord,{csfirstsaler:lsStaffinfo[i].bstaffno,csfirsttype:'2'});
			    			exists=1;
			    			break;
			    		}
			    		
			    			
			    	}
			    	if(exists==0)
			    	{
			    		commoninfodivdetial_service.updateRow(curRecord,{csfirstsaler:'',csfirsttype:''});
			    	}
			    	else
			    	{
			    		if(document.getElementById("paramSp112").value=="0")
						{
							checkClientType(strEmpInid, 1);
						}
			    	}
			    }
				
			}
		
			//initDetialGrid();
		}
		function validateSecondSaleType(obj)
		{
			var curempValue="";
			var strEmpInid="";
			if(curEmpManger!=null && curEmpManger.inputText.val()!="")
				curempValue=curEmpManger.inputText.val();
			else
				curempValue=obj.value;
			curEmpManger=null;
			if(curempValue!="")
			{
				if(curempValue.indexOf('_')>-1)
				{
					curempValue=curempValue.substring(0,curempValue.indexOf("_"));
				}
				if(curempValue!="" && curempValue.indexOf('_')==-1)
		    	{
				
		    		var exists=0;
			    	for(var i=0;i<lsStaffinfo.length;i++)
			    	{
			    		if(curempValue==lsStaffinfo[i].bstaffno)
			    		{
			    			commoninfodivdetial_service.updateRow(curRecord,{cssecondsaler: lsStaffinfo[i].bstaffno,cssecondtype:'2'});
			    			exists=1;
			    			strEmpInid=lsStaffinfo[i].manageno;
			    			break;
			    		}
			    			
			    	}
			    	if(exists==0)
			    	{
			    		commoninfodivdetial_service.updateRow(curRecord,{cssecondsaler:'',cssecondtype:''});
			    	}
			    	else
			    	{
			    		if(document.getElementById("paramSp112").value=="0")
						{
							checkClientType(strEmpInid, 2);
						}
			    	}
			    }
				
			}
			
			//initDetialGrid();
		}
		function validateThirdSaleType(obj)
		{
			var curempValue="";
			var strEmpInid="";
			if(curEmpManger!=null && curEmpManger.inputText.val()!="")
				curempValue=curEmpManger.inputText.val();
			else
				curempValue=obj.value;
			curEmpManger=null;
			if(curempValue!="")
			{
				if(curempValue.indexOf('_')>-1)
				{
					curempValue=curempValue.substring(0,curempValue.indexOf("_"));
				}
				if(curempValue!="" && curempValue.indexOf('_')==-1)
		    	{
		
		    		var exists=0;
			    	for(var i=0;i<lsStaffinfo.length;i++)
			    	{
			    		if(curempValue==lsStaffinfo[i].bstaffno)
			    		{
			    			commoninfodivdetial_service.updateRow(curRecord,{csthirdsaler: lsStaffinfo[i].bstaffno,cssecondtype:'2'});
			    			strEmpInid=lsStaffinfo[i].manageno;
			    			exists=1;
			    			break;
			    		}
			    			
			    	}
			    	if(exists==0)
			    	{
			    		commoninfodivdetial_service.updateRow(curRecord,{csthirdsaler:'',cssecondtype:''});
			    	}
			    	else
			    	{
			    		if(document.getElementById("paramSp112").value=="0")
						{
							checkClientType(strEmpInid, 3);
						}
			    	}
			    }
				
			}
		}
		
		function validateFristSaleType_G(obj)
		{
			var curempValue="";
			
			if(curEmpManger!=null && curEmpManger.inputText.val()!="")
				curempValue=curEmpManger.inputText.val();
			else
				curempValue=obj.value;
			curEmpManger=null;
			if(curempValue!="")
			{
				if(curempValue.indexOf('_')>-1)
				{
					curempValue=curempValue.substring(0,curempValue.indexOf("_"));
				}
				if(curempValue!="" && curempValue.indexOf('_')==-1)
		    	{
					var exists=0;
			    	for(var i=0;i<lsStaffinfo.length;i++)
			    	{
			    		if(curempValue==lsStaffinfo[i].bstaffno)
			    		{
			    			commoninfodivdetial_goods.updateRow(curRecord,{csfirstsaler:lsStaffinfo[i].bstaffno});
			    			exists=1;
			    			break;
			    		}
			    			
			    	}
			    	if(exists==0)
			    	{
			    		commoninfodivdetial_goods.updateRow(curRecord,{csfirstsaler:''});
			    	}
			    	else
			    	{
			    		
			    	}
			    }
				
			}
		
			//initDetialGrid();
		}
		function validateSecondSaleType_G(obj)
		{
			try
			{
			var curempValue="";
			if(curEmpManger!=null && curEmpManger.inputText.val()!="")
				curempValue=curEmpManger.inputText.val();
			else
				curempValue=obj.value;
			curEmpManger=null;
			if(curempValue!="")
			{
				if(curempValue.indexOf('_')>-1)
				{
					curempValue=curempValue.substring(0,curempValue.indexOf("_"));
				}
				if(curempValue!="" && curempValue.indexOf('_')==-1)
		    	{
				
		    		var exists=0;
			    	for(var i=0;i<lsStaffinfo.length;i++)
			    	{
			    		if(curempValue==lsStaffinfo[i].bstaffno)
			    		{
			    			commoninfodivdetial_goods.updateRow(curRecord,{cssecondsaler: lsStaffinfo[i].bstaffno});
			    			exists=1;
			    			break;
			    		}
			    			
			    	}
			    	if(exists==0)
			    	{
			    		commoninfodivdetial_goods.updateRow(curRecord,{cssecondsaler:''});
			    	}
			    }
				
			}
			}catch(e){alert(e.message);}
			//initDetialGrid();
		}
		function validateThirdSaleType_G(obj)
		{
			var curempValue="";
			if(curEmpManger!=null && curEmpManger.inputText.val()!="")
				curempValue=curEmpManger.inputText.val();
			else
				curempValue=obj.value;
			curEmpManger=null;
			if(curempValue!="")
			{
				if(curempValue.indexOf('_')>-1)
				{
					curempValue=curempValue.substring(0,curempValue.indexOf("_"));
				}
				if(curempValue!="" && curempValue.indexOf('_')==-1)
		    	{
		
		    		var exists=0;
			    	for(var i=0;i<lsStaffinfo.length;i++)
			    	{
			    		if(curempValue==lsStaffinfo[i].bstaffno)
			    		{
			    			commoninfodivdetial_goods.updateRow(curRecord,{csthirdsaler: lsStaffinfo[i].bstaffno});
			    			exists=1;
			    			break;
			    		}
			    			
			    	}
			    	if(exists==0)
			    	{
			    		commoninfodivdetial_goods.updateRow(curRecord,{csthirdsaler:''});
			    	}
			    }
				
			}
		}
		
		//打印小票
      	function viewTicketReport(shopId,ticketId,cscardno,csdate)
		{
			if(ticketId == "" || ticketId == null||shopId==""||shopId==null)
			{
			    return;
			}
			try
			{
				var params = "ticketId=" + ticketId +"&shopId="+shopId +"&memberCardId="+cscardno+" &billDate="+csdate;
				var requestUrl ="cc011/viewTicketReport.action";
		    	var responseMethod = "viewTicketReportMessage";
		    	sendRequestForParams_p(requestUrl,responseMethod,params ); 
			}
			catch(e)
			{
				alert(e.message)
			}
		}
		
		function viewTicketReportMessage( request )
		{
			try
			{
				var responsetext = eval("(" + request.responseText + ")");
				Stand_CheckPrintControl();//检查是否有打印控件
				Stand_InitPrint("收银模块_小票打印作业");
				Stand_SetPrintStyle("FontName","新宋体");
				Stand_SetPrintStyle("FontSize",11);
				Stand_SetPrintStyle("Alignment",2);
				Stand_SetPrintStyle("HOrient",2);
				Stand_SetPrintStyle("Bold",1);
				Stand_AddPrintContent(StandPRINT_CONTENT_TYPE_TEXT,31,15,351,25,"阿玛尼护肤造型"+"("+checkNull(responsetext.companName)+")");
				Stand_SetPrintStyle("FontSize",9);
				Stand_AddPrintContent(StandPRINT_CONTENT_TYPE_TEXT,55,15,351,25,"一切为你 想你所想");
				Stand_SetPrintStyle("FontSize",11);
				Stand_SetPrintStyle("Bold",0);
				var index=151;
				document.getElementById("billDat").innerHTML=checkNull(responsetext.billDate); 
				if(checkNull(responsetext.memberCardId).indexOf("散客")==-1)
					document.getElementById("memberCardId").innerHTML="***"+checkNull(responsetext.memberCardId).substring(checkNull(responsetext.memberCardId).length-3,checkNull(responsetext.memberCardId).length);
				else
					document.getElementById("memberCardId").innerHTML="现金";
				document.getElementById("billnop").innerHTML=checkNull(responsetext.ticketId).substring(checkNull(responsetext.ticketId).length-3,checkNull(responsetext.ticketId).length);
				clearPreviousResult();
				var table=document.getElementById("info");
				var texts=document.getElementById("texts");
				var text2=document.getElementById("text2");
				var text3=document.getElementById("text3");
				var text4=document.getElementById("text4");
				var text5=document.getElementById("text5");
				var bRet=false;
				var amt =0;
				var num=0;
				var sumNum=0;
				
				var tr=document.createElement("tr");
				var tr1=document.createElement("tr");
				var ttd1=document.createElement("td");
				var ttd2=document.createElement("td");
				var ttd3=document.createElement("td");
				var ttd4=document.createElement("td");
				ttd1.innerHTML="项目名称";
				ttd2.innerHTML="数量";
				ttd3.innerHTML="金额";
				ttd4.innerHTML="大工";
				ttd1.align="left";
				ttd2.align="left";
				ttd3.align="left";
				ttd4.align="left";
				tr.appendChild(ttd1);
				tr.appendChild(ttd2);
				tr.appendChild(ttd3);
				tr.appendChild(ttd4);
				table.appendChild(tr);
						
				if(responsetext.costListProj != null)
				{
					for(var i=0;i<responsetext.costListProj.length;i++)
					{
						var tr=document.createElement("tr");
						var tr1=document.createElement("tr");
						var td=document.createElement("td");
						td.setAttribute("colSpan",4);
						td.innerHTML=checkNull(responsetext.costListProj[i].projectName);
						tr.appendChild(td);
						table.appendChild(tr);
						var td1=document.createElement("td");
						var td3=document.createElement("td");
						var td4=document.createElement("td");
						var td2=document.createElement("td");
						td1.innerHTML="";
					
						td3.innerHTML=checkNull(responsetext.costListProj[i].costNumber);
						num=num*1+checkNull(responsetext.costListProj[i].costNumber)*1;
						if(checkNull(responsetext.costListProj[i].paymode)!="9")
						{
							td4.innerHTML=checkNull(responsetext.costListProj[i].costMoney);
							amt=amt*1+checkNull(responsetext.costListProj[i].costMoney)*1;
						}
						else
						{
							td4.innerHTML="次";
							if(responsetext.lsPrintCardproaccount != null && responsetext.lsPrintCardproaccount.length>0)
							{
								for(var j=0;j<responsetext.lsPrintCardproaccount.length;j++)
								{
									if((checkNull(responsetext.costListProj[i].projectNo)==checkNull(responsetext.lsPrintCardproaccount[j].bprojectno))
									&&(checkNull(responsetext.costListProj[i].csproseqno)==0 || checkNull(responsetext.costListProj[i].csproseqno)==checkNull(responsetext.lsPrintCardproaccount[j].bproseqno)))
						    		{
						    			td4.innerHTML=td4.innerHTML+"(余"+maskAmt(responsetext.lsPrintCardproaccount[j].lastcount,0)+"次)";
						    		}
								}
							}
							else
							{
								  td4.innerHTML=td4.innerHTML+"(余0次)";
							}
						}
						td2.innerHTML=checkNull(responsetext.costListProj[i].strFristStaffNo);
						tr1.appendChild(td1);
						tr1.appendChild(td3);
						tr1.appendChild(td4);
						tr1.appendChild(td2);
						table.appendChild(tr1);
						bRet=true;
						
					}
				}
				if(responsetext.CostListProd != null)
				{
					for(var i=0;i<responsetext.CostListProd.length;i++)
					{
						var tr=document.createElement("tr");
						var tr1=document.createElement("tr");
						var td=document.createElement("td");
						td.setAttribute("colSpan",4);
						td.innerHTML=checkNull(responsetext.CostListProd[i].projectName);
						tr.appendChild(td);
						table.appendChild(tr);
						var td1=document.createElement("td");
						var td3=document.createElement("td");
						var td4=document.createElement("td");
						var td2=document.createElement("td");
						td1.innerHTML="";
						td2.innerHTML="";
						td3.innerHTML=checkNull(responsetext.CostListProd[i].costNumber);
						num=num*1+checkNull(responsetext.CostListProd[i].costNumber)*1;
						amt=amt*1+checkNull(responsetext.CostListProd[i].costMoney)*1;
						td4.innerHTML=checkNull(responsetext.CostListProd[i].costMoney);
						td2.innerHTML=checkNull(responsetext.CostListProd[i].strFristStaffNo);
						tr1.appendChild(td1);
						tr1.appendChild(td2);
						tr1.appendChild(td3);
						tr1.appendChild(td4);
						table.appendChild(tr1);
						bRet=true;
					}
				}
				if(bRet)
				{
					var tr1=document.createElement("tr");
					var td1=document.createElement("td");
					var td2=document.createElement("td");
					var td3=document.createElement("td");
					td1.innerHTML="合计";
					td2.innerHTML=maskAmt(num,1);
					td3.innerHTML=maskAmt(amt,1);
					tr1.appendChild(td1);
					tr1.appendChild(td2);
					tr1.appendChild(td3);
					table.appendChild(tr1);
					
				}
				var strbank="";
				var pay4flag=0;
				var pay7flag=0;
				var payAflag=0;
				bRet=false;
				if(responsetext.payTypeList != null)
				{
					for(var i=0;i<responsetext.payTypeList.length;i++)
					{
						strbank="";
						var tr=document.createElement("tr");
						var td1=document.createElement("td");
						var td2=document.createElement("td");
						td1.setAttribute("colSpan",2);
						td2.setAttribute("colSpan",2);
						if(checkNull(responsetext.payTypeList[i].paymodename).length==2)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else if(checkNull(responsetext.payTypeList[i].paymodename).length==3)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else if(checkNull(responsetext.payTypeList[i].paymodename).length==4)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else if(checkNull(responsetext.payTypeList[i].paymodename).length==5)
						{
							strbank="&nbsp;&nbsp;";
						}
						
						if(checkNull(responsetext.payTypeList[i].paymode)=="4"
						|| checkNull(responsetext.payTypeList[i].paymode)=="7"
						|| checkNull(responsetext.payTypeList[i].paymode)=="A" )
						{
							if(responsetext.lsPrintCardaccount != null && responsetext.lsPrintCardaccount.length>0)
							{
								for(var j=0;j<responsetext.lsPrintCardaccount.length;j++)
								{
									if((checkNull(responsetext.payTypeList[i].paymode)=="4"
										&& checkNull(responsetext.lsPrintCardaccount[j].accounttype)==2)
									||
										(checkNull(responsetext.payTypeList[i].paymode)=="7"
										&& checkNull(responsetext.lsPrintCardaccount[j].accounttype)==3)
									||
										(checkNull(responsetext.payTypeList[i].paymode)=="A"
										&& checkNull(responsetext.lsPrintCardaccount[j].accounttype)==5)
									)
									{
											td1.innerHTML=checkNull(responsetext.lsPrintCardaccount[j].accounttypeText)+strbank+" &nbsp;&nbsp; "+maskAmt(responsetext.payTypeList[i].payamt,1);
											td2.innerHTML="&nbsp;"+" 余:&nbsp;&nbsp; "+maskAmt(responsetext.lsPrintCardaccount[j].accountbalance,1);
											if(checkNull(responsetext.payTypeList[i].paymode)=="4")
											{
												pay4flag=1;
											}
											if(checkNull(responsetext.payTypeList[i].paymode)=="7")
											{
												pay7flag=1;
											}
											if(checkNull(responsetext.payTypeList[i].paymode)=="A")
											{
												payAflag=1;
											}
									}
								}
							}
						}
						else
						{
							td1.innerHTML=checkNull(responsetext.payTypeList[i].paymodename)+strbank+"&nbsp;&nbsp; "+maskAmt(responsetext.payTypeList[i].payamt,1);
							td2.innerHTML="&nbsp; "+"余:&nbsp;&nbsp; 0";
						}
						tr.appendChild(td1);
						tr.appendChild(td2);
						text2.appendChild(tr);
						bRet=true;
					}
				
				}
				if(responsetext.lsPrintCardaccount != null && responsetext.lsPrintCardaccount.length>0)
				{
					for(var j=0;j<responsetext.lsPrintCardaccount.length;j++)
					{
						if(checkNull(responsetext.lsPrintCardaccount[j].accounttypeText).length==2)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else if(checkNull(responsetext.lsPrintCardaccount[j].accounttypeText).length==3)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else if(checkNull(responsetext.lsPrintCardaccount[j].accounttypeText).length==4)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else if(checkNull(responsetext.lsPrintCardaccount[j].accounttypeText).length==5)
						{
							strbank="&nbsp;&nbsp;";
						}
						if(
						   	(
								(pay4flag==0 && checkNull(responsetext.lsPrintCardaccount[j].accounttype)==2)
							||  (pay7flag==0 && checkNull(responsetext.lsPrintCardaccount[j].accounttype)==3)
							||  (payAflag==0 && checkNull(responsetext.lsPrintCardaccount[j].accounttype)==5)
							)
						    && checkNull(responsetext.lsPrintCardaccount[j].accountbalance)*1>0)
						{
							var tr=document.createElement("tr");
							var td1=document.createElement("td");
							var td2=document.createElement("td");
							td1.setAttribute("colSpan",2);
							td2.setAttribute("colSpan",2);
							td1.innerHTML=checkNull(responsetext.lsPrintCardaccount[j].accounttypeText)+strbank+"&nbsp;&nbsp; 0";
							td2.innerHTML="&nbsp;"+" 余:&nbsp;&nbsp; "+maskAmt(responsetext.lsPrintCardaccount[j].accountbalance,1);
							tr.appendChild(td1);
							tr.appendChild(td2);
							text2.appendChild(tr);
						}
					}
				}
				
				
				/*bRet=false;
				if(responsetext.lsPrintCardaccount != null && responsetext.lsPrintCardaccount.length>0)
				{
					for(var i=0;i<responsetext.lsPrintCardaccount.length;i++)
					{
						strbank="";
						var tr=document.createElement("tr");
						var td=document.createElement("td");
						td.setAttribute("colSpan",4);
						if(checkNull(responsetext.lsPrintCardaccount[i].accounttypeText).length==2)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else if(checkNull(responsetext.lsPrintCardaccount[i].accounttypeText).length==3)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else if(checkNull(responsetext.lsPrintCardaccount[i].accounttypeText).length==4)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else if(checkNull(responsetext.lsPrintCardaccount[i].accounttypeText).length==5)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						td.innerHTML=checkNull(responsetext.lsPrintCardaccount[i].accounttypeText)+strbank+"余额"+maskAmt(responsetext.lsPrintCardaccount[i].accountbalance,1);
						tr.appendChild(td);
						text3.appendChild(tr);
						bRet=true;
					}
				
				}*/
				var costprj="";
				var costsqno=0;
				bRet=false;
				/*if(responsetext.lsPrintCardproaccount != null 
				&& responsetext.lsPrintCardproaccount.length>0
				&& responsetext.costListProj != null 
				&& responsetext.costListProj.length>0)
				{
					for(var i=0;i<responsetext.lsPrintCardproaccount.length;i++)
					{
						
						for(var j=0;j<responsetext.costListProj.length;j++)
						{
							if(costprj==checkNull(responsetext.costListProj[j].projectName) 
							&& costsqno==checkNull(responsetext.costListProj[j].csproseqno))
							{
								continue;
							}
							if(checkNull(responsetext.costListProj[j].paymode)=="9"
								&&(checkNull(responsetext.costListProj[j].projectNo)==checkNull(responsetext.lsPrintCardproaccount[i].bprojectno))
								&&(checkNull(responsetext.costListProj[j].csproseqno)==0 || checkNull(responsetext.costListProj[j].csproseqno)==checkNull(responsetext.lsPrintCardproaccount[i].bproseqno)))
						    	{
									var tr=document.createElement("tr");
									var td=document.createElement("td");
									var td1=document.createElement("td");
									td.setAttribute("colSpan",2);
									td1.setAttribute("colSpan",2);
									td.innerHTML=checkNull(responsetext.lsPrintCardproaccount[i].bprojectname);
									td1.innerHTML="剩余"+maskAmt(responsetext.lsPrintCardproaccount[i].lastcount,0)+"次";
									tr.appendChild(td);
									tr.appendChild(td1);
									text4.appendChild(tr);
									bRet=true;
									costprj=checkNull(responsetext.costListProj[j].projectName);
									costsqno=checkNull(responsetext.costListProj[j].csproseqno);
								}
								
						}
						
					}
					if(bRet)
					{
						//addHr(text4,4);
					}
				}*/
				
				/*var tr4=document.createElement("tr");
				var td4=document.createElement("td");
				td4.innerHTML="流水号:&nbsp;&nbsp;"+checkNull(responsetext.ticketId);
				tr4.appendChild(td4);
				text5.appendChild(tr4);*/
				
				var tr3=document.createElement("tr");
				var td3=document.createElement("td");
				td3.innerHTML="操作人: &nbsp;&nbsp; "+checkNull(responsetext.cashMemberName)+" &nbsp;&nbsp; 打印日期: &nbsp;&nbsp; "+checkNull(responsetext.printDate);
				tr3.appendChild(td3);
				text5.appendChild(tr3);
				
				var tr5=document.createElement("tr");
				var td5=document.createElement("td");
				//td5.setAttribute("rowSpan",3);
				td5.innerHTML="贵宾签名:";
				tr5.appendChild(td5);
				text5.appendChild(tr5);
				
				
				var tr10=document.createElement("tr");
				var td10=document.createElement("td");
				//td10.setAttribute("",3);
				td10.innerHTML="&nbsp;";
				tr10.appendChild(td10);
				text5.appendChild(tr10);
				
				var tr8=document.createElement("tr");
				var td8=document.createElement("td");
				td8.setAttribute("align","center");
				td8.innerHTML=checkNull(responsetext.companAddr)+"["+checkNull(responsetext.companTel)+"]";
				tr8.appendChild(td8);
				text5.appendChild(tr8);
				
				var tr7=document.createElement("tr");
				var td7=document.createElement("td");
				td7.setAttribute("align","center");
				td7.innerHTML="www.chinamani.com";
				tr7.appendChild(td7);
				text5.appendChild(tr7);
				
				var tr9=document.createElement("tr");
				var td9=document.createElement("td");
				td9.setAttribute("align","center");
				td9.innerHTML="4006622818";
				tr9.appendChild(td9);
				text5.appendChild(tr9);
				var printContent = document.getElementById("printContent").innerHTML;
				Stand_AddPrintContent(StandPRINT_CONTENT_TYPE_HTML,68,0,230,800,printContent);
				
				Stand_Print();
			}
			catch(e)
			{
				alert(e.message);
			}
		}
		

		//清楚打印内容
		function clearPreviousResult()
		{
			var tblPrjs = document.getElementById("info");
			while(tblPrjs.childNodes.length>0)
			{
				tblPrjs.removeChild(tblPrjs.childNodes[0]);
			}
			var tblPrjs = document.getElementById("text2");
			while(tblPrjs.childNodes.length>0)
			{
				tblPrjs.removeChild(tblPrjs.childNodes[0]);
			}
			var tblPrjsb = document.getElementById("text3");
			while(tblPrjsb.childNodes.length>0)
			{
				tblPrjsb.removeChild(tblPrjsb.childNodes[0]);
			}
			var tblPrjsc = document.getElementById("text4");
			while(tblPrjsc.childNodes.length>0)
			{
				tblPrjsc.removeChild(tblPrjsc.childNodes[0]);
			}
			var tblPrjsc = document.getElementById("text5");
			while(tblPrjsc.childNodes.length>0)
			{
				tblPrjsc.removeChild(tblPrjsc.childNodes[0]);
			}
		}
		//增加行
	    function addHr(obj,num)
		{
			var tr=document.createElement("tr");
			var td=document.createElement("td");
			td.setAttribute("colSpan",num);
			td.innerHTML="<div style='border-bottom: 1px dashed #000000; width: 100%;'>"
			tr.appendChild(td);
			obj.appendChild(tr);
		}
	
	 	function validateRecommendempid(obj)
	    {
	    	if(obj.value=="")
	    	{
	    		document.getElementById("recommendempname").value="";
	    		document.getElementById("recommendempinid").value="";
	    	}
	    	else
	    	{	
	    		var vflag=0
	    		if(lsStaffinfo!=null && lsStaffinfo.length>0)
				{
					for(var i=0;i<lsStaffinfo.length;i++)
					{
						if(lsStaffinfo[i].bstaffno==obj.value)
				 		{
				 					document.getElementById("recommendempname").value=lsStaffinfo[i].staffname;
				 					document.getElementById("recommendempinid").value=lsStaffinfo[i].manageno;
				 					vflag=1;
				 					break;
				 		}
					}
				}
				if(vflag==0)
				{
					document.getElementById("recommendempid").value="";
					document.getElementById("recommendempname").value="";
					document.getElementById("recommendempinid").value="";
					$.ligerDialog.error("该员工编号不存在,请确认！");
				}
	    	}
	    }	
	   
	   function showBillHistory(strCompId,strBillId,billType,changeseqno)
	   {
	   		var params = "strCurCompId="+checkNull(strCompId+"");	
			params=params+"&strCurBillId="+checkNull(strBillId+"");
			params=params+"&nBillType="+checkNull(billType+"");
			params=params+"&changeseqno="+checkNull(changeseqno);
			var requestUrl ="";
			var responseMethod="";
			if(billType==10 || billType==11 )
			{
				requestUrl="ac008/loadBillCosHistory.action";
   				responseMethod="loadBillCosHistoryMessage";
			}
			else if(billType==9 )
			{
				requestUrl="ac008/loadBilProHistory.action";
   				responseMethod="loadBillProHistoryMessage";
			}
			else
			{
				requestUrl="ac008/loadBillHistory.action";
   				responseMethod="loadBillHistoryMessage";
   			}
   			sendRequestForParams_p(requestUrl,responseMethod,params);	
	   }
	   
	    function loadBillHistoryMessage(request)
        {
        		var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{
	        		lsBillHistoryInfo=responsetext.lsReEditBillInfo;
	        		$.ligerDialog.open({ height: 400, url: contextURL+'/AdvancedOperations/AC008/billhistory.jsp', width: 1024, showMax: true, showToggle: true, showMin: true, isResize: true, modal: false, title:'单据修改历史' });
  				}
	        	else
	        	{
	        		$.ligerDialog.success(strMessage);
	        	}
        }
        function loadBillCosHistoryMessage(request)
        {
        		var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{
	        		lsCostHistoryInfo=responsetext.lsDconsumeinfos;
	        		$.ligerDialog.open({ height: 400, url: contextURL+'/AdvancedOperations/AC008/billcoshistory.jsp', width: 1024, showMax: true, showToggle: true, showMin: true, isResize: true, modal: false, title:'单据修改历史' });
  				}
	        	else
	        	{
	        		$.ligerDialog.success(strMessage);
	        	}
        }
        
        function loadBillProHistoryMessage(request)
        {
        		var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{
	        		lsProHistoryInfo=responsetext.lsDproexchangeinfos;
	        		$.ligerDialog.open({ height: 400, url: contextURL+'/AdvancedOperations/AC008/billprohistory.jsp', width: 1024, showMax: true, showToggle: true, showMin: true, isResize: true, modal: false, title:'单据修改历史' });
  				}
	        	else
	        	{
	        		$.ligerDialog.success(strMessage);
	        	}
        }
        
        function checkClientType(strEmpInid,type)
        {
        	var strCardNo=document.getElementById("strCurCardNo").value;
        	$.get(contextURL+"/cc011/checkClientType.action",{strCardNo:strCardNo,strEmpInid:strEmpInid},function(data){
        		if(type==1)
        		{
        			if(data.bRet)
        			{
        				commoninfodivdetial_service.updateRow(curRecord,{csfirsttype:"1"});
        			}
        			else
        			{
        				commoninfodivdetial_service.updateRow(curRecord,{csfirsttype:"2"});
        			}
        		}
        		else if(type==2)
        		{
        			if(data.bRet)
        			{
        				commoninfodivdetial_service.updateRow(curRecord,{cssecondtype:"1"});
        			}
        			else
        			{
        				commoninfodivdetial_service.updateRow(curRecord,{cssecondtype:"2"});
        			}
        		}
        		else if(type==3)
        		{
        			if(data.bRet)
        			{
        				commoninfodivdetial_service.updateRow(curRecord,{csthirdtype:"1"});
        			}
        			else
        			{
        				commoninfodivdetial_service.updateRow(curRecord,{csthirdtype:"2"});
        			}
        		}
        	});
        }
        
        function validateSX(obj)
        {
        	var strEmpInid="";
			var curempValue="";
			var row=commoninfodivdetial.getRow(curRecord);
			if(curEmpManger!=null && curEmpManger.inputText.val()!="")
				curempValue=curEmpManger.inputText.val();
			else
				curempValue=obj.value;
			if(curEmpManger!=null)
			{
				curEmpManger=null;
			}
			if(curempValue!="")
			{
				if(curempValue.indexOf('_')>-1)
				{
					curempValue=curempValue.substring(0,curempValue.indexOf("_"));
				}
				if(curempValue!="" && curempValue.indexOf('_')==-1)
		    	{
					var exists=0;
			    	for(var i=0;i<lsStaffinfo.length;i++)
			    	{
			    		if(curempValue==lsStaffinfo[i].bstaffno)
			    		{
			    			commoninfodivdetial_service.updateRow(curRecord,{hairRecommendEmpId:lsStaffinfo[i].bstaffno});
			    		}
			    	}
		    	}
			}
        	/*var vflag=0;
        	var empFlag=0;
        	var row=commoninfodivdetial_service.getRow(curRecord);
        	if((checkNull(row['csitemno'])=="322" || checkNull(row['csitemno'])=="323" ||  checkNull(row['csitemno'])=="324" || checkNull(row['csitemno'])=="325") 
        		&& checkNull(row['csfirsttype'])=="2")
        	{
        		for(var i=0;i<lsStaffinfo.length;i++)
        		{
        			if(lsStaffinfo[i].bstaffno==checkNull(row['csfirstsaler']))
        		 	{
        	 			vflag=1;
        	 			if(lsStaffinfo[i].position=="006")
        	 			{
        	 				commoninfodivdetial.updateRow(curRecord,{costpricetype:4});
        	 			}
        	 			else if(lsStaffinfo[i].position=="007" || lsStaffinfo[i].position=="00701" || lsStaffinfo[i].position=="00702")
        	 			{
        	 				commoninfodivdetial.updateRow(curRecord,{costpricetype:5});
        	 			}
        	 			else
        	 			{
        	 				commoninfodivdetial.updateRow(curRecord,{costpricetype:0});
        	 			}
        	 			break;
        		 	}
        		}		
        	}
        	if(vflag==0)
        	{
        		commoninfodivdetial_service.updateRow(curRecord,{hairRecommendEmpId:''});
        		$.ligerDialog.error("员工职位不正确或者项目不正确或者大工类型不对。不能填写首席总监介绍人");
        		return;
        	}
        	for(var i=0;i<lsStaffinfo.length;i++)
    		{
     			if(lsStaffinfo[i].bstaffno==obj.value)
    		 	{
    				alert(lsStaffinfo[i].position);
    				if(lsStaffinfo[i].position=="00105" || lsStaffinfo[i].position=="0010502" || lsStaffinfo[i].position=="0010503" || lsStaffinfo[i].position=="0010504")
    				{
    					empFlag=1;
        				commoninfodivdetial_service.updateRow(curRecord,{hairRecommendEmpId:obj.value});
    				}
    				//$("#hairRecommendEmpName").val(parent.StaffInfo[i].staffname);
    				//$("#hairRecommendEmpInid").val(parent.StaffInfo[i].manageno);
     				break;
    		 	}
    		}
        	if(empFlag==0)
        	{
        		obj.value="";
        		commoninfodivdetial_service.updateRow(curRecord,{hairRecommendEmpId:''});
        		$.ligerDialog.error("介绍人职位不对或者员工编号不存在");
        		return;
        	}*/
        }
        
        function changebillinsertype(obj)
        {
        	var billtypeFlag=loadRadiovalue("curReEditBillInfo.billType");
        	if(billtypeFlag==0 || billtypeFlag==1|| billtypeFlag==2 || billtypeFlag==3 || billtypeFlag==4 || billtypeFlag==5 || billtypeFlag==9)
        	{
        		if(obj.value=="1" || obj.value=="3" || billtypeFlag==9)
        		{
        			document.getElementById("trBeauty").style.display="block";
        			document.getElementById("trGw").style.display="block";
        		}
        		else
        		{
        			document.getElementById("trBeauty").style.display="none";
        			document.getElementById("trGw").style.display="none";
        			document.getElementById("beautyMangerNo").value="";
        			document.getElementById("beautyGw").value="";
        		}
        	}
        	else
        	{
        		document.getElementById("trBeauty").style.display="none";
    			document.getElementById("trGw").style.display="none";
    			document.getElementById("beautyMangerNo").value="";
    			document.getElementById("beautyGw").value="";
        	}
        }
        