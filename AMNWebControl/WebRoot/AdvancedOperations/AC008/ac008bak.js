
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
   	var commoninfodivdetial_oldcustomer=null;
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
                 	{ hide:true, 	name: 'changeseqno',width:1},
                 	{ hide:true, 	name: 'billdate',width:1},
                 	{ hide:true, 	name: 'backflag',width:1}
               ],  pageSize:25, 
                data:{Rows: null,Total:0},      
              	width: 330,
                height:height-20,
                enabledEdit: true, checkbox: false,
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                	curMasterRecord=data;
                    loadSelecDetialData(data, rowindex, rowobj);
                }
            });
            
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
									return "<span style='color:blue'>"+lsStaffinfo[i].staffname+"</span>";
								}
						}
	                    return '';
	                } 
	            },
	            { display: '类型', 		name: 'csfirsttype', 		width:40 ,
	             	editor: { type: 'select', data: servicetypeChangeData, valueField: 'choose',selectBoxHeight:'120'},
		            	render: function (item)
		              	{
		              		var lsZw=parent.gainCommonInfoByCode("FWLB",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.csfirsttype)
								{	
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
									return "<span style='color:blue'>"+lsStaffinfo[i].staffname+"</span>";
								}
						}
	                    return '';
	                } 
	            },
	           { display: '类型', 		name: 'csthirdtype', 		width:40 ,
	             	editor: { type: 'select', data: servicetypeChangeData, valueField: 'choose',selectBoxHeight:'120'},
		            	render: function (item)
		              	{
		              		var lsZw=parent.gainCommonInfoByCode("FWLB",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.csthirdtype)
								{	
									return "<span style='color:blue'>"+lsZw[i].parentcodevalue+"</span>";								
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
      		    { text: '修改收银单', click: editServiceDetailInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/memeber.gif' },
      		    { text: '返销收银单', click: backServiceDetailInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' }
            ]
            });
            
            $("#toptoolbarCard_Goods").ligerToolBar({ items: [
      		    { text: '修改收银单', click: editGoodsDetailInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/memeber.gif' },
      		    { text: '返销收银单', click: backGoodsDetailInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' }
            ]
            });
            
            
            $("#toptoolbarSaleCard").ligerToolBar({ items: [
      		    { text: '修改业绩分享', click: editCurRootInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/memeber.gif' },
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
   		var strSalePayMode="1;6;14;15";
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
			       
					var lsStaffSelectData_sj=loadOtherGridByStaffInfo(lsStaffinfo,'',1);
					var lsStaffSelectData_tr=loadOtherGridByStaffInfo(lsStaffinfo,'',2);
					var loadGridByStaffInfo_all=loadGridByStaffInfo(lsStaffinfo,'');
					firstsaleridmanager= $("#firstsalerid").ligerComboBox({ isShowCheckBox: false,   url:'loadAutoStaff', data: loadGridByStaffInfo_all
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
				
					commoninfodivdetial_service.columns[3].editor.data=loadCommonDataGridByStaffInfo(lsStaffinfo);
					commoninfodivdetial_service.columns[5].editor.data=loadCommonDataGridByStaffInfo(lsStaffinfo);
					commoninfodivdetial_service.columns[7].editor.data=loadCommonDataGridByStaffInfo(lsStaffinfo);
				
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
	   			firstsaleridmanager.setDisabled();
   				secondsaleridmanager.setDisabled();
   				thirdsalerinidmanager.setDisabled();
	   			fourthsaleridmanager.setDisabled();
   				fifthsaleridmanager.setDisabled();
   				sixthsaleridmanager.setDisabled();
   				seventhsaleridmanager.setDisabled();
   				eighthsaleridmanager.setDisabled();
   				ninthsaleridmanager.setDisabled();
				tenthsaleridmanager.setDisabled();
				
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
		       	document.getElementById("firstsalerid_h").value=checkNull(data.firstsalerid);
			   	firstsaleridmanager.selectValue(checkNull(data.firstsalerid));
			   	document.getElementById("firstsaleamt").value=checkNull(data.firstsaleamt);
				document.getElementById("secondsalerid_h").value=checkNull(data.secondsalerid);
				secondsaleridmanager.selectValue(checkNull(data.secondsalerid));
				document.getElementById("secondsaleamt").value=checkNull(data.secondsaleamt);
				document.getElementById("thirdsalerid_h").value=checkNull(data.thirdsalerid);
				thirdsalerinidmanager.selectValue(checkNull(data.thirdsalerid));
				document.getElementById("thirdsaleamt").value=checkNull(data.thirdsaleamt);
							   		
				document.getElementById("fourthsalerid_h").value=checkNull(data.fourthsalerid);
				fourthsaleridmanager.selectValue(checkNull(data.fourthsalerid));
				document.getElementById("fourthsaleamt").value=checkNull(data.fourthsaleamt);
							   
				document.getElementById("fifthsalerid_h").value=checkNull(data.fifthsalerid);
				fifthsaleridmanager.selectValue(checkNull(data.fifthsalerid));
				document.getElementById("fifthsaleamt").value=checkNull(data.fifthsaleamt);
				document.getElementById("sixthsalerid_h").value=checkNull(data.sixthsalerid);
				sixthsaleridmanager.selectValue(checkNull(data.sixthsalerid));
				document.getElementById("sixthsaleamt").value=checkNull(data.sixthsaleamt);
				document.getElementById("seventhsalerid_h").value=checkNull(data.seventhsalerid);
				seventhsaleridmanager.selectValue(checkNull(data.seventhsalerid));
				document.getElementById("seventhsaleamt").value=checkNull(data.seventhsaleamt);
				
				document.getElementById("eighthsalerid_h").value=checkNull(data.eighthsalerid);
				eighthsaleridmanager.selectValue(checkNull(data.eighthsalerid));
				document.getElementById("eighthsaleamt").value=checkNull(data.eighthsaleamt);
				
				document.getElementById("ninthsalerid_h").value=checkNull(data.ninthsalerid);
				ninthsaleridmanager.selectValue(checkNull(data.ninthsalerid));
				document.getElementById("ninthsaleamt").value=checkNull(data.ninthsaleamt);
				
				document.getElementById("tenthsalerid_h").value=checkNull(data.tenthsalerid);
				tenthsaleridmanager.selectValue(checkNull(data.tenthsalerid));
				document.getElementById("tenthsaleamt").value=checkNull(data.tenthsaleamt);		
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
				commoninfodivdetial_service.options.data=$.extend(true, {},{Rows: responsetext.lsDconsumeinfos,Total: responsetext.lsDconsumeinfos.length});
				commoninfodivdetial_service.loadData(true);   
				initDetialGrid();         	
			}
			else
			{
				commoninfodivdetial_service.options.data=$.extend(true, {},{Rows: null,Total: 0});
				commoninfodivdetial_service.loadData(true);   
				addService()
			}
      	}
      	
      	
      	function loadGoodSDetialMessage(request)
      	{
      		var responsetext = eval("(" + request.responseText + ")");
  			if(responsetext.lsDconsumeinfos!=null && responsetext.lsDconsumeinfos.length>0)
			{
				commoninfodivdetial_goods.options.data=$.extend(true, {},{Rows: responsetext.lsDconsumeinfos,Total: responsetext.lsDconsumeinfos.length});
				commoninfodivdetial_goods.loadData(true);   
				initDetialGrid_G();         	
			}
			else
			{
				commoninfodivdetial_goods.options.data=$.extend(true, {},{Rows: null,Total: 0});
				commoninfodivdetial_goods.loadData(true);   
				addGoods()
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
			 var curjosnparam="";
			 var strJsonParam_detial="";
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
			var responseMethod="postSerivceInfoMessage";				
			sendRequestForParams_p(requestUrl,responseMethod,params); 	
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
	        		alert(strMessage);
	        	}
        	}
			catch(e)
			{
				alert(e.message);
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
        
        function backServiceDetailInfo()
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
						 sendRequestForParams_p(requestUrl,responseMethod,params);
					}
					else
					{
						postState=0;
					}
				}); 
        	 
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
			 	&& checkNull(rowdata.csitemno).indexOf("498")==0
			 	&& checkNull(rowdata.csitemno).indexOf("490")==0)
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
				firstsaleridmanager.selectBox.hide();
   				secondsaleridmanager.selectBox.hide();
   				thirdsalerinidmanager.selectBox.hide();
   				fifthsaleridmanager.selectBox.hide();
   				sixthsaleridmanager.selectBox.hide();
   				seventhsaleridmanager.selectBox.hide();
			}
			catch(e){alert(e.message);}
				
		}   
		
		function checkStaffValuex(obj)
		{
			try
			{
			
				if(obj.id=="firstsalerid")
				{
						if(obj.value!="" && obj.value.indexOf('_')==-1)
				    	{
				    		var exists=0;
				    		for(var i=0;i<lsStaffinfo.length;i++)
				    		{
				    			if(obj.value==lsStaffinfo[i].bstaffno)
				    			{
				    				  firstsaleridmanager.selectValue(lsStaffinfo[i].bstaffno);
				    				  exists=1;
				    				  break;
				    			}
				    			
				    		}
				    		if(exists==0)
				    		{
				    			document.getElementById("firstsaleamt").value=0;
				    			firstsaleridmanager.selectValue('');
				    		}
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
				    				  secondsaleridmanager.selectValue(lsStaffinfo[i].bstaffno);
				    				  exists=1;
				    				  break;
				    			}
				    			
				    		}
				    		if(exists==0)
				    		{
				    			document.getElementById("secondsaleamt").value=0;
				    			secondsaleridmanager.selectValue('');
				    		}
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
        							  thirdsalerinidmanager.selectValue(lsStaffinfo[i].bstaffno);
				    				  exists=1;
				    				  break;
				    			}
				    			
				    		}
				    		if(exists==0)
				    		{
				    			document.getElementById("thirdsaleamt").value=0;
				    			thirdsaleridmanager.selectValue('');
				    		}
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
				    				  fourthsaleridmanager.selectValue(lsStaffinfo[i].bstaffno);
				    				  exists=1;
				    				  break;
				    			}
				    			
				    		}
				    		if(exists==0)
				    		{
				    			document.getElementById("fourthsaleamt").value=0;
				    			fourthsaleridmanager.selectValue('');
				    		}
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
				    				  fifthsaleridmanager.selectValue(lsStaffinfo[i].bstaffno);
				    				  exists=1;
				    				  break;
				    			}
				    			
				    		}
				    		if(exists==0)
				    		{
				    			document.getElementById("fifthsaleamt").value=0;
				    			fifthsaleridmanager.selectValue('');
				    		}
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
				    				  sixthsaleridmanager.selectValue(lsStaffinfo[i].bstaffno);
				    				  exists=1;
				    				  break;
				    			}
				    			
				    		}
				    		if(exists==0)
				    		{
				    			document.getElementById("sixthsaleamt").value=0;
				    			sixthsaleridmanager.selectValue('');
				    		}
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
				    				  seventhsaleridmanager.selectValue(lsStaffinfo[i].bstaffno);
				    				  exists=1;
				    				  break;
				    			}
				    			
				    		}
				    		if(exists==0)
				    		{
				    			document.getElementById("seventhsaleamt").value=0;
				    			seventhsaleridmanager.selectValue('');
				    		}
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
				    				  eighthsaleridmanager.selectValue(lsStaffinfo[i].bstaffno);
				    				  exists=1;
				    				  break;
				    			}
				    			
				    		}
				    		if(exists==0)
				    		{
				    			document.getElementById("eighthsaleamt").value=0;
				    			eighthsaleridmanager.selectValue('');
				    		}
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
				    				  ninthsaleridmanager.selectValue(lsStaffinfo[i].bstaffno);
				    				  exists=1;
				    				  break;
				    			}
				    			
				    		}
				    		if(exists==0)
				    		{
				    			document.getElementById("ninthsaleamt").value=0;
				    			ninthsaleridmanager.selectValue('');
				    		}
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
				    				  tenthsaleridmanager.selectValue(lsStaffinfo[i].bstaffno);
				    				  exists=1;
				    				  break;
				    			}
				    			
				    		}
				    		if(exists==0)
				    		{
				    			document.getElementById("tenthsaleamt").value=0;
				    			tenthsaleridmanager.selectValue('');
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
				document.getElementById("firstsalerid_h").value= $("#factcfirstsalerid").val();	
				document.getElementById("secondsalerid_h").value= $("#factcsecondsalerid").val();	
				document.getElementById("thirdsalerid_h").value=  $("#factcthirdsalerid").val();	
				document.getElementById("fourthsalerid_h").value=  $("#factcfourthsalerid").val();	
				document.getElementById("fifthsalerid_h").value=  $("#factcfifthsalerid").val();	
				document.getElementById("sixthsalerid_h").value=  $("#factcsixthsalerid").val();	
				document.getElementById("seventhsalerid_h").value=$("#factcseventhsalerid").val();	
				document.getElementById("eighthsalerid_h").value=$("#factceighthsalerid").val();
				document.getElementById("ninthsalerid_h").value=$("#factcninthsalerid").val();
				document.getElementById("tenthsalerid_h").value=$("#factctenthsalerid").val();
				
				var totaltrsAmt=0;
				var otherotherAmt=0;
				var totaltrsCount=0;
				var ortherCount=0;
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
							if(document.getElementById("firstsalerid_h").value!="" 
			 				&&  lsStaffinfo[i].bstaffno==document.getElementById("firstsalerid_h").value)
			 				{
			 					if(lsStaffinfo[i].department=="006" )
			 					{
			 						if(document.getElementById("firstsaleamt").value*1>10000 && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享业绩不正确");
			 								return;
			 						}
			 						totaltrsAmt=totaltrsAmt*1+document.getElementById("firstsaleamt").value*1;
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
			 					}
			 					else
			 					{
			 						otherotherAmt=otherotherAmt*1+document.getElementById("firstsaleamt").value*1;
			 						ortherCount=ortherCount*1+1;
			 						beatyTotalAmt=beatyTotalAmt*1+document.getElementById("firstsaleamt").value*1;
			 					}
			 				}
			 				if(document.getElementById("secondsalerid_h").value!="" 
			 				&&  lsStaffinfo[i].bstaffno==document.getElementById("secondsalerid_h").value)
			 				{
			 					if(lsStaffinfo[i].department=="006")
			 					{
			 						if(document.getElementById("secondsaleamt").value*1>10000  && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享业绩不正确");
			 								return;
			 						}
			 						totaltrsAmt=totaltrsAmt*1+document.getElementById("secondsaleamt").value*1;
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
			    						otherotherAmt=otherotherAmt*1+document.getElementById("secondsaleamt").value*1;
			 							ortherCount=ortherCount*1+1;
			    						hairTotalAmt=hairTotalAmt*1+document.getElementById("secondsaleamt").value*1;
			 					}
			 					else
			 					{
			 						otherotherAmt=otherotherAmt*1+document.getElementById("secondsaleamt").value*1;
			 						ortherCount=ortherCount*1+1;
			 						beatyTotalAmt=beatyTotalAmt*1+document.getElementById("secondsaleamt").value*1;
			 					}
			 				}
			 				if(document.getElementById("thirdsalerid_h").value!="" 
			 				&&  lsStaffinfo[i].bstaffno==document.getElementById("thirdsalerid_h").value)
			 				{
			 					if(lsStaffinfo[i].department=="006" )
			 					{
			 						if(document.getElementById("thirdsaleamt").value*1>10000 && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享业绩不正确");
			 								return;
			 						}
			 						totaltrsAmt=totaltrsAmt*1+document.getElementById("thirdsaleamt").value*1;
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
			 					}
			 					else
			 					{
			 						otherotherAmt=otherotherAmt*1+document.getElementById("thirdsaleamt").value*1;
			 						ortherCount=ortherCount*1+1;
			 						beatyTotalAmt=beatyTotalAmt*1+document.getElementById("thirdsaleamt").value*1;
			 					}
			 				}
			 				if(document.getElementById("fourthsalerid_h").value!="" 
			 				&&  lsStaffinfo[i].bstaffno==document.getElementById("fourthsalerid_h").value)
			 				{
			 					if(lsStaffinfo[i].department=="006" )
			 					{
			 						if(document.getElementById("fourthsaleamt").value*1>10000 && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享业绩不正确");
			 								return;
			 						}
			 						totaltrsAmt=totaltrsAmt*1+document.getElementById("fourthsaleamt").value*1;
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
											otherotherAmt=otherotherAmt*1+document.getElementById("fourthsaleamt").value*1;
			 								ortherCount=ortherCount*1+1;
			    							hairTotalAmt=hairTotalAmt*1+document.getElementById("fourthsaleamt").value*1;
			    						}
			 					}
			 					else
			 					{
			 						otherotherAmt=otherotherAmt*1+document.getElementById("fourthsaleamt").value*1;
			 						ortherCount=ortherCount*1+1;
			 						beatyTotalAmt=beatyTotalAmt*1+document.getElementById("fourthsaleamt").value*1;
			 					}
			 				}
			 				if(document.getElementById("fifthsalerid_h").value!="" 
			 				&&  lsStaffinfo[i].bstaffno==document.getElementById("fifthsalerid_h").value)
			 				{
			 					if(lsStaffinfo[i].department=="006" )
			 					{
			 						if(document.getElementById("fifthsaleamt").value*1>10000 && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享业绩不正确");
			 								return;
			 						}
			 						totaltrsAmt=totaltrsAmt*1+document.getElementById("fifthsaleamt").value*1;
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
			 					}
			 					else
			 					{
			 						otherotherAmt=otherotherAmt*1+document.getElementById("fifthsaleamt").value*1;
			 						ortherCount=ortherCount*1+1;
			 						beatyTotalAmt=beatyTotalAmt*1+document.getElementById("fifthsaleamt").value*1;
			 					}
			 				}
			 				if(document.getElementById("sixthsalerid_h").value!="" 
			 				&&  lsStaffinfo[i].bstaffno==document.getElementById("sixthsalerid_h").value)
			 				{
			 					if(lsStaffinfo[i].department=="006" )
			 					{
			 						if(document.getElementById("sixthsaleamt").value*1>10000 && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享业绩不正确");
			 								return;
			 						}
			 						totaltrsAmt=totaltrsAmt*1+document.getElementById("sixthsaleamt").value*1;
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
			 					}
			 					else
			 					{
			 						otherotherAmt=otherotherAmt*1+document.getElementById("sixthsaleamt").value*1;
			 						ortherCount=ortherCount*1+1;
			 						beatyTotalAmt=beatyTotalAmt*1+document.getElementById("sixthsaleamt").value*1;
			 					}
			 				}
			 				if(document.getElementById("seventhsalerid_h").value!="" 
			 				&&  lsStaffinfo[i].bstaffno==document.getElementById("seventhsalerid_h").value)
			 				{
			 					if(lsStaffinfo[i].department=="006" )
			 					{
			 						if(document.getElementById("seventhsaleamt").value*1>10000 && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享业绩不正确");
			 								return;
			 						}
			 						totaltrsAmt=totaltrsAmt*1+document.getElementById("seventhsaleamt").value*1;
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
			 					}
			 					else
			 					{
			 						otherotherAmt=otherotherAmt*1+document.getElementById("seventhsaleamt").value*1;
			 						ortherCount=ortherCount*1+1;
			 						beatyTotalAmt=beatyTotalAmt*1+document.getElementById("seventhsaleamt").value*1;
			 					}
			 				}
			 				if(document.getElementById("eighthsalerid_h").value!="" 
			 				&&  lsStaffinfo[i].bstaffno==document.getElementById("eighthsalerid_h").value)
			 				{
			 					if(lsStaffinfo[i].department=="006" )
			 					{
			 						if(document.getElementById("eighthsaleamt").value*1>10000 && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享业绩不正确");
			 								return;
			 						}
			 						totaltrsAmt=totaltrsAmt*1+document.getElementById("eighthsaleamt").value*1;
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
			 					}
			 					else
			 					{
			 						otherotherAmt=otherotherAmt*1+document.getElementById("eighthsaleamt").value*1;
			 						ortherCount=ortherCount*1+1;
			 						beatyTotalAmt=beatyTotalAmt*1+document.getElementById("eighthsaleamt").value*1;
			 					}
			 				}
			 				if(document.getElementById("ninthsalerid_h").value!="" 
			 				&&  lsStaffinfo[i].bstaffno==document.getElementById("ninthsalerid_h").value)
			 				{
			 					if(lsStaffinfo[i].department=="006" )
			 					{
			 						if(document.getElementById("ninthsaleamt").value*1>10000 && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享业绩不正确");
			 								return;
			 						}
			 						totaltrsAmt=totaltrsAmt*1+document.getElementById("ninthsaleamt").value*1;
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
			    						otherotherAmt=otherotherAmt*1+document.getElementById("ninthsaleamt").value*1;
			 							ortherCount=ortherCount*1+1;
			    						hairTotalAmt=hairTotalAmt*1+document.getElementById("ninthsaleamt").value*1;
			 					}
			 					else
			 					{
			 						otherotherAmt=otherotherAmt*1+document.getElementById("ninthsaleamt").value*1;
			 						ortherCount=ortherCount*1+1;
			 						beatyTotalAmt=beatyTotalAmt*1+document.getElementById("ninthsaleamt").value*1;
			 					}
			 				}
			 				if(document.getElementById("tenthsalerid_h").value!="" 
			 				&&  lsStaffinfo[i].bstaffno==document.getElementById("tenthsalerid_h").value)
			 				{
			 					if(lsStaffinfo[i].department=="006")
			 					{
			 						if(document.getElementById("tenthsaleamt").value*1>10000  && xbillType!=9)
			 						{
			 								$.ligerDialog.error("烫染师"+lsStaffinfo[i].staffname+"销售分享业绩不正确");
			 								return;
			 						}
			 						totaltrsAmt=totaltrsAmt*1+document.getElementById("tenthsaleamt").value*1;
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
			    						otherotherAmt=otherotherAmt*1+document.getElementById("tenthsaleamt").value*1;
			 							ortherCount=ortherCount*1+1;
			    						hairTotalAmt=hairTotalAmt*1+document.getElementById("tenthsaleamt").value*1;
			 					}
			 					else
			 					{
			 						otherotherAmt=otherotherAmt*1+document.getElementById("tenthsaleamt").value*1;
			 						ortherCount=ortherCount*1+1;
			 						beatyTotalAmt=beatyTotalAmt*1+document.getElementById("tenthsaleamt").value*1;
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
				if(document.getElementById("strShareCondition").value=="1" )
				{
						if(ForDight(hairTotalAmt*1,1) > ForDight(paymat*1,1))  
						{
							$.ligerDialog.error('美发正常销售分享总额不能超过总支付额度,请确认!');
							return;
						}
						if(ForDight(beatyTotalAmt*1,1) > ForDight(paymat*1,1))  
						{
							$.ligerDialog.error('美容正常销售分享总额不能超过总支付额度,请确认!');
							return;
						}  
				}
				else
				{
					if(ForDight(otherotherAmt*1,1) > ForDight(paymat*1,1))  
					{
						$.ligerDialog.error('正常销售分享总额不能超过总支付额度,请确认!');
						return;
					} 
				}
				var requestUrl ="ac008/postCurRootInfo.action";
				var params = "strCurCompId="+document.getElementById("strCompId").value;	
				params=params+"&strCurBillId="+document.getElementById("strBillId").value;
				params=params+"&changeseqno="+document.getElementById("changeseqno").value;
				params=params+"&nBillType="+loadRadiovalue("curReEditBillInfo.billType");		
				params=params+"&strCurDate="+document.getElementById("strCurDate").value;
				params=params+"&curReEditBillInfo.firstsalerid="+document.getElementById("firstsalerid_h").value;
				params=params+"&curReEditBillInfo.secondsalerid="+document.getElementById("secondsalerid_h").value;
				params=params+"&curReEditBillInfo.thirdsalerid="+document.getElementById("thirdsalerid_h").value;
				params=params+"&curReEditBillInfo.fourthsalerid="+document.getElementById("fourthsalerid_h").value;
				params=params+"&curReEditBillInfo.fifthsalerid="+document.getElementById("fifthsalerid_h").value;
				params=params+"&curReEditBillInfo.sixthsalerid="+document.getElementById("sixthsalerid_h").value;
				params=params+"&curReEditBillInfo.seventhsalerid="+document.getElementById("seventhsalerid_h").value;
				params=params+"&curReEditBillInfo.eighthsalerid="+document.getElementById("eighthsalerid_h").value;
				params=params+"&curReEditBillInfo.ninthsalerid="+document.getElementById("ninthsalerid_h").value;
				params=params+"&curReEditBillInfo.tenthsalerid="+document.getElementById("tenthsalerid_h").value;
				
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
				params=params+"&curReEditBillInfo.billinsertype="+document.getElementById("billinsertype").value;
				var curjosnparam="";
				var strJsonParam_detial="";
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
	        		alert(strMessage);
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
        }
        
        function editShare(obj)
        {	
        	if(obj.checked==true)
        	{
        		if(obj.value==1)
        		{
        			firstsaleridmanager.setEnabled();
        			document.getElementById("firstsaleamt").readOnly="";
        		}
        		else if(obj.value==2)
        		{
        			secondsaleridmanager.setEnabled();
        			document.getElementById("secondsaleamt").readOnly="";
        		}
        		else if(obj.value==3)
        		{
        			thirdsalerinidmanager.setEnabled();
        			document.getElementById("thirdsaleamt").readOnly="";
        		}
        		else if(obj.value==4)
        		{
        			fourthsaleridmanager.setEnabled();
        			document.getElementById("fourthsaleamt").readOnly="";
        		}
        		else if(obj.value==5)
        		{
        			fifthsaleridmanager.setEnabled();
        			document.getElementById("fifthsaleamt").readOnly="";
        		}
        		else if(obj.value==6)
        		{
        			sixthsaleridmanager.setEnabled();
        			document.getElementById("sixthsaleamt").readOnly="";
        		}
        		else if(obj.value==7)
        		{
        			seventhsaleridmanager.setEnabled();
        			document.getElementById("seventhsaleamt").readOnly="";
        		}
        		else if(obj.value==8)
        		{
        			eighthsaleridmanager.setEnabled();
        			document.getElementById("eighthsaleamt").readOnly="";
        		}
        		else if(obj.value==9)
        		{
        			ninthsaleridmanager.setEnabled();
        			document.getElementById("ninthsaleamt").readOnly="";
        		}
        		else if(obj.value==10)
        		{
        			tenthsaleridmanager.setEnabled();
        			document.getElementById("tenthsaleamt").readOnly="";
        		}
        	}
        	else
        	{
        		if(obj.value==1)
        		{
        			firstsaleridmanager.setDisabled();
        			document.getElementById("firstsaleamt").readOnly="readOnly";
        		}
        		else if(obj.value==2)
        		{
        			secondsaleridmanager.setDisabled();
        			document.getElementById("secondsaleamt").readOnly="readOnly";
        		}
        		else if(obj.value==3)
        		{
        			thirdsalerinidmanager.setDisabled();
        			document.getElementById("thirdsaleamt").readOnly="readOnly";
        		}
        		else if(obj.value==4)
        		{
        			fourthsaleridmanager.setDisabled();
        			document.getElementById("fourthsaleamt").readOnly="readOnly";
        		}
        		else if(obj.value==5)
        		{
        			fifthsaleridmanager.setDisabled();
        			document.getElementById("fifthsaleamt").readOnly="readOnly";
        		}
        		else if(obj.value==6)
        		{
        			sixthsaleridmanager.setDisabled();
        			document.getElementById("sixthsaleamt").readOnly="readOnly";
        		}
        		else if(obj.value==7)
        		{
        			seventhsaleridmanager.setDisabled();
        			document.getElementById("seventhsaleamt").readOnly="readOnly";
        		}
        		else if(obj.value==8)
        		{
        			eighthsaleridmanager.setDisabled();
        			document.getElementById("eighthsaleamt").readOnly="readOnly";
        		}
        		else if(obj.value==9)
        		{
        			ninthsaleridmanager.setDisabled();
        			document.getElementById("ninthsaleamt").readOnly="readOnly";
        		}
        		else if(obj.value==10)
        		{
        			tenthsaleridmanager.setDisabled();
        			document.getElementById("tenthsaleamt").readOnly="readOnly";
        		}
        	}
        }
        
        function validatePaycode(obj)
        {
        }
		
		
		function validateFristSaleType(obj)
		{
			var curempValue="";
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
			    			commoninfodivdetial_service.updateRow(curRecord,{csfirstsaler:lsStaffinfo[i].bstaffno,csfirsttype:'2'});
			    			exists=1;
			    			break;
			    		}
			    			
			    	}
			    	if(exists==0)
			    	{
			    		commoninfodivdetial_service.updateRow(curRecord,{csfirstsaler:'',csfirsttype:''});
			    	}
			    }
				
			}
		
			//initDetialGrid();
		}
		function validateSecondSaleType(obj)
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
			    			commoninfodivdetial_service.updateRow(curRecord,{cssecondsaler: lsStaffinfo[i].bstaffno,cssecondtype:'2'});
			    			exists=1;
			    			break;
			    		}
			    			
			    	}
			    	if(exists==0)
			    	{
			    		commoninfodivdetial_service.updateRow(curRecord,{cssecondsaler:'',cssecondtype:''});
			    	}
			    }
				
			}
			
			//initDetialGrid();
		}
		function validateThirdSaleType(obj)
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
			    			commoninfodivdetial_service.updateRow(curRecord,{csthirdsaler: lsStaffinfo[i].bstaffno,cssecondtype:'2'});
			    			exists=1;
			    			break;
			    		}
			    			
			    	}
			    	if(exists==0)
			    	{
			    		commoninfodivdetial_service.updateRow(curRecord,{csthirdsaler:'',cssecondtype:''});
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
				
   