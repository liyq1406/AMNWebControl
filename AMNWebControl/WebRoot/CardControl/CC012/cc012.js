   	var compTreeManager;
   	var compTree;
   	var commoninfodivmaster=null;	//卡入库主档列表
   	var commoninfodivdetial=null;	//卡入库明细列表
   	var strCurCompId="";
   	var strCurBillId="";
   	var iState=3;
   	var curMasterInfoDate=null;
   	var commoninfodivdetialpro=null;
   	var fromValidate=null;
	var lsCardTypeInfo=new Array();
	var cc012layout=null;
   	var pageState=3; // 1 add 2 modify 3 browse
   	var curRecord = null;
   	var curProRecord = null;
   	var dSaleProRate=1;
   	var lsStaffinfo= null;
   	var showDialogmanager=null;
   	var curitemManger=null;
   	var curEmpManger=null;
   	var postState=0;
   	var useManagerRate=0;
   	var markcustomerDialog=null;
   	var changeproid1="4610067";
   	var changeproid2="4610068";
   	var btnDis2015=null;
    function loadPayModeDate()
    {
   			var strpaymode= new Array();
   			strpaymode[0]="1";
   			strpaymode[1]="6";
   			strpaymode[2]="14";
   			strpaymode[3]="15";
   			var returnValue="";
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
				returnValue=returnValue+'{"choose": "'+paymode+'","text": "'+paymodename+'"}';
	   		}
	   		if(returnValue!='')
	   		{
				returnValue=returnValue+']';
				return JSON.parse(returnValue);
			}
    		
    }
   
   	var paymodeChangeData=loadPayModeDate();
   	
 
   
    $(function ()
   	{
	   try
	   {
	   		  //布局
            cc012layout= $("#cc012layout").ligerLayout({ leftWidth: 270,   allowBottomResize: false, allowLeftResize: false,isLeftCollapse:true });
            var height = $(".l-layout-center").height();
	   		//门店树形结构
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
          	commoninfodivmaster=$("#commoninfodivmaster").ligerGrid({
                columns: [
                { display: '兑换单号', 	name: 'bchangebillid', 		width:110,align: 'left'},
                { display: '兑换日期', 	name: 'changedate', 		width:80,align: 'left'},
	            { display: '兑换卡号', 	name: 'changecardno', 		width:100,align: 'left'}
	            ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '360',
                height: '375',
                clickToEdit: false,   enabledEdit: false,  
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                }
            });
          
        
           commoninfodivdetialpro=$("#commoninfodivdetialpro").ligerGrid({
                columns: [
                { display: '疗程编号', 		name: 'changeproid',  		width:100,align: 'left' },
                { display: '疗程名称', 		name: 'changeproname', 		width:180,align: 'left'},
	            { display: '剩余次数', 		name: 'lastcount', 			width:60,align: 'left'},
	            { display: '剩余金额', 		name: 'lastamt', 			width:60,align: 'left'},
	            { display: '退换次数', 		name: 'changeprocount', 		width:60,align: 'center',editor: { type: 'int' ,onChanged: validateCurCostCount}},
	            { display: '退换金额', 		name: 'changeproamt', 		width:60,align: 'left'},
	            {	name: 'bproseqno', 	hide:true,width:1}
                ],  pageSize:25, 
                data:{Rows: null,Total:0},      
                width: '560',
                height:'375',
                enabledEdit: true, checkbox:false,
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    curProRecord = data;
                }  
            });
            
            commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
                columns: [
                { display: '疗程编号', 		name: 'changeproid',  			width:80,align: 'left', 
			 	 	editor: { type: 'select', data: null, url:'loadAutoProject',autocomplete: true, valueField: 'choose',onChanged : validateItem,selectBoxWidth:300,selectBoxHeight:300}
                },
                { display: '疗程名称', 		name: 'changeproname', 			width:140,align: 'left'},
                { display: '疗程数', 			name: 'procount', 				width:60,align: 'left',editor: { type: 'int' ,onChanged: validateChangeCount}},
	            { display: '标准额',			name: 'standchangeproamt',		width:50,align: 'right'},
	            { display: '项目数', 			name: 'changeprocount', 		width:50,align: 'left'},
	            { display: '折扣', 			name: 'changeprorate', 			width:40,align: 'center'},
	            { display: '兑换金额', 		name: 'changeproamt', 			width:60,align: 'right'},
	            { display: '疗程抵扣', 		name: 'changebyproaccountamt', 	width:60,align: 'right',editor: { type: 'float',onChanged: validateChangeProAccount}},
	            { display: '赠送抵扣', 		name: 'changebysendaccountamt', 	width:60,align: 'right',editor: { type: 'float',onChanged: validateSendProAccount}},
	            { display: '储值抵扣', 		name: 'changebyaccountamt', 	width:60,align: 'right',editor: { type: 'float'}},
	            { display: '积分抵扣', 		name: 'changebyfaceamt', 	width:60,align: 'right',editor: { type: 'float'}},
	            { display: '支付方式', 		name: 'changepaymode', 			width:80,align: 'left',
	             	editor: { type: 'select', data: paymodeChangeData, valueField: 'choose',selectBoxWidth:105},
		            	render: function (item)
		              	{
		              		var lsZw=parent.gainCommonInfoByCode("ZFFS",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.changepaymode)
								{	
									return lsZw[i].parentcodevalue;								
							    }
							}
		                    return '';
		                } 
		        },
	            { display: '现金抵扣', 		name: 'changebycashamt', 		width:60,align: 'right',editor: { type: 'float' ,onChanged: validateChangeKeepAccount }},
	            { display: '抵用券', 			name: 'nointernalcardno', 		width:100,align: 'left', editor: { type: 'text',onChanged : validateNointernalcardno}},
	            { display: '抵用', 			name: 'changebydyqamt', 		width:50,align: 'right'},
	            { display: '第一销售', 		name: 'firstsalerid', 			width:70,align: 'center',
	            	editor: { type: 'select', data: null,  url:'loadAutoStaff',autocomplete: true,valueField: 'choose',selectBoxWidth:150,onChanged:validateFristSale,alwayShowInDown:true},
	            	render: function (item)
	              	{
	              		for(var i=0;i<lsStaffinfo.length;i++)
						{	
								if(lsStaffinfo[i].bstaffno==item.firstsalerid)
								{
									return lsStaffinfo[i].staffname;
								}
						}
	                    return '';
	                } 
	            },
	            { display: '分享', 		name: 'firstsaleamt', 			width:50,align: 'right',editor: { type: 'float' }},
	            { display: '第一烫染', 		name: 'thirdsalerid', 			width:70,align: 'center',
	            	editor: { type: 'select', data: null, valueField: 'choose',selectBoxWidth:150,onChanged : validateTrShareAmt},
	            	render: function (item)
	              	{
	              		for(var i=0;i<lsStaffinfo.length;i++)
						{	
								if(lsStaffinfo[i].bstaffno==item.thirdsalerid)
								{
									return lsStaffinfo[i].staffname;
								}
						}
	                    return '';
	                } 
	            },
	            { display: '分享', 		name: 'thirdsaleamt', 			width:50,align: 'right'},
	            { display: '备注', 		name: 'changemark', 			width:130,align: 'left'},
	       
	            { name: 'standchangeprocount', 	hide:true ,width:1},
	            { name: 'ctypestate', hide:true ,width:1}
                ],  pageSize:25, 
                data:{Rows: null,Total:0},      
                width: cc012layout.centerWidth*1-10,
                height:height-345,
                enabledEdit: false, checkbox: false,
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    curRecord = data;
                    document.getElementById("fastProId").value="";
					document.getElementById("fastProCount").value="";
					document.getElementById("fastProDkAmt").value="";
					document.getElementById("fastSendDkAmt").value=""; 
					document.getElementById("fastKeepDkAmt").value="";
					document.getElementById("fastPayCode").value="";
					document.getElementById("fastCashDkAmt").readOnly="readOnly";
		 			document.getElementById("fastCashDkAmt").style.background="#EDF1F8"
					document.getElementById("fastCashDkAmt").value="";
					document.getElementById("fastDyNo").value="";
					document.getElementById("fastFirstEmp").value="";
					document.getElementById("fastSecondEmp").value="";
					document.getElementById("fastFristShare").value=0;
					document.getElementById("fastProId").select();
					document.getElementById("fastProId").focus();
                },
                onContextmenu : function (parm,e)
                {
                	 curRecord = parm.data;
                     menu.show({ top: e.pageY, left: e.pageX });
                     return false;
                } 
                	   
            });
            
             menu = $.ligerMenu({ width: 120, items:
	            [
	            	{ text: '使用超长疗程', click: handMoreLongHair, icon: 'delete' , img: contextURL+'/common/ligerui/ligerUI/skins/icons/config.gif'}
	            ]
	            });
             
             
             //dis2015
             
             
             btnDis2015=$("#dis2015").ligerButton(
        	         {
        	             text: '2015大礼包', width: 90,
        		         click: function ()
        		         {
        		        	 dis2015();
        		             //editCurRecord();
        		         }
        	 });
	            
             $("#editCurRootInfo").ligerButton(
	         {
	             text: '兑换疗程', width: 90,
		         click: function ()
		         {
		             editCurRecord();
		         }
	         });
             $("#managerRate").ligerButton(
	         {
	             text: '经理打折', width: 90,
		         click: function ()
		         {
		             managerRate();
		             //viewTicketReport();
		            
		         }
	         });
	          $("#manager38Rate").ligerButton(
	         {
	             text: '3.8节活动', width: 80,
		         click: function ()
		         {
		             manager38Rate();
		             //viewTicketReport();
		            
		         }
	         });
	          $("#manager85Rate").ligerButton({
    			  text: '980疗程体验', width: 90,
    			  click: function (){
    				  if(checkNull($("#SP117Flag").val())*1==0){
    					  $.ligerDialog.warn("门店未开启980疗程体验活动！");
    					  return;
    				  }
    				  var _endDate = checkNull($("#SP118Flag").val());
    				  if(checkNull(_endDate)==""){
    					  $.ligerDialog.warn("此活动未设置截止日期，请确认！");
    					  return;
    				  }
    				  ;
    				  var end = new Date(_endDate.substring(0, 4)+"/"+_endDate.substring(4, 6)+"/"+_endDate.substring(6, 8));
    				  //if(strCurCompId=="048" || strCurCompId=="050"){
					  if(currentdate>=end){
						  $.ligerDialog.warn("此活动截止"+_endDate.substring(4, 6)+"月"+_endDate.substring(6, 8)+"号结束！");
						  return;
					  }
    				  /*}else{
    					  $.ligerDialog.warn("此活动从8月5号到8月31号！");
						  return;
    				  }*/
    				  manager85Rate();
    			  }
    		  });
	           $("#printCurRootInfo").ligerButton(
	         {
	             text: '重新打印', width: 90,
		         click: function ()
		         {
		            viewTicketReport();
		            
		         }
	         });
	         
             $("#toptoolbardetial").ligerToolBar({ items: [
      		     { text: '编号:&nbsp;<input type="text"  name="fastProId" id="fastProId" maxlength="20"  style="width:100;"   onfocus="itemsearchbegin(this,6);"/> &nbsp;&nbsp;套数:&nbsp;<select  name="fastProCount" id="fastProCount"  onchange="validatefastProCount(this);"></select>' },
	             { text: '扣疗程:&nbsp;<input type="text"  name="fastProDkAmt" id="fastProDkAmt"  style="width:40;"  onchange="validatePrice(this);validateFastProDkAmt(this)"/>&nbsp;&nbsp;<input type="hidden"  name="fastKeepDkAmt" id="fastKeepDkAmt" />'+
	             		 '扣赠送:&nbsp;<input type="text"  name="fastSendDkAmt" id="fastSendDkAmt"  style="width:40;"  onchange="validatePrice(this);validateFastSendDkAmt(this)"/>'+
			             '扣积分:&nbsp;<input type="text"  name="fastFaceDkAmt" id="fastFaceDkAmt"  style="width:40;"  onchange="validatePrice(this);validateFastFaceDkAmt(this)"/>' },
	             { text: '抵用券:&nbsp;<input type="text"  name="fastDyNo" id="fastDyNo" style="width:100;" onchange="validateFastDyNo(this)" />' },
	             { text: '支付:&nbsp;<select  name="fastPayCode" id="fastPayCode" onchange="validatePayCode(this)" ><option value="1">现金</option><option value="6">银行卡</option></select>&nbsp;&nbsp;金额:&nbsp;<input type="text"  name="fastCashDkAmt" id="fastCashDkAmt"  style="width:40;background:#EDF1F8" readonly="true" onchange="validateFastCashDkAmt(this)" />' },
	             { text: '销售:&nbsp;<input type="text"  name="fastFirstEmp" id="fastFirstEmp" style="width:40;" onchange="validateFastFirstEmp(this)" />&nbsp;分享:&nbsp;<input type="text"  name="fastFristShare" id="fastFristShare"  style="width:40;" onchange="validatePrice(this);validateFastFristShare(this)" />&nbsp;&nbsp;烫染:&nbsp;<input type="text"  name="fastSecondEmp" id="fastSecondEmp"  style="width:40;" onchange="validateFastSecondEmp(this)" />' },
	             { text: '备注:&nbsp;<input type="text"  name="fastMask" id="fastDyNo" style="width:120;" maxlength="200" onchange="validatefastMask(this)"  onblur="item_adddetial()"/>' }
	             	
	          ]
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
            $("#readCurCardInfo").ligerButton(
	         {
	             text: '读取卡号', width: 80,
		         click: function ()
		         {
		             readCurCardInfo();
		         }
	         });
            $("#pageloading").hide(); 
          	f_selectNode();
   		}
   		catch(e){alert(e.message);}
    });
    
    function f_selectNode()
	{
		var parm = function (data)
		{
		   	return data.id== parent.localCompid;
		};		
		compTree.selectNode(parm);
	}
    //加载门店信息
	function compSelect(note)
    {
        try{
        	
        	strCurCompId=note.data.id;
       		var params = "strCurCompId="+note.data.id;	
     		var requestUrl ="cc012/loadMasterinfo.action"; 
			var responseMethod="loadMasterinfoMessage";		

			sendRequestForParams_p(requestUrl,responseMethod,params );
          }catch(e){alert(e.message);}
    }
    function loadMasterinfoMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
    		
	       	if(responsetext.lsMproexchangeinfo!=null && responsetext.lsMproexchangeinfo.length>0)
	   		{
	   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: responsetext.lsMproexchangeinfo,Total: responsetext.lsMproexchangeinfo.length});
            	commoninfodivmaster.loadData(true);      
            	commoninfodivmaster.select(0);      	
	   		}
	   		else
	   		{
	   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivmaster.loadData(true);  
	   		}
			lsStaffinfo=responsetext.lsStaffinfo;	
			commoninfodivdetial.columns[15].editor.data=parent.loadGridByStaffInfo_grid(lsStaffinfo,1);
			commoninfodivdetial.columns[17].editor.data=parent.loadGridByStaffInfo_grid(lsStaffinfo,2);
			clearOption("changePackageNo");
			if(responsetext.lsMpackageinfo!=null && responsetext.lsMpackageinfo.length>0)
			{
				for(var i=0;i<responsetext.lsMpackageinfo.length;i++)
				{
					addOption(responsetext.lsMpackageinfo[i].id.packageno,responsetext.lsMpackageinfo[i].packagename,document.getElementById("changePackageNo"));
				}
			}
			document.getElementById("SP095Flag").value=responsetext.SP095Flag;
			document.getElementById("SP099Flag").value=responsetext.SP099Flag;
			document.getElementById("SP117Flag").value=responsetext.SP117Flag;
			document.getElementById("SP118Flag").value=responsetext.SP118Flag;
			clearOption("fastProCount");
			if(checkNull(responsetext.SP095Flag)==1)
			{
				addOption("0.5","0.5",document.getElementById("fastProCount"));
				addOption("1","1",document.getElementById("fastProCount"));
				addOption("1.5","1.5",document.getElementById("fastProCount"));
				addOption("2","2",document.getElementById("fastProCount"));
				addOption("2.5","2.5",document.getElementById("fastProCount"));
				addOption("3","3",document.getElementById("fastProCount"));
				addOption("3.5","3.5",document.getElementById("fastProCount"));
				addOption("4","4",document.getElementById("fastProCount"));
				addOption("5","5",document.getElementById("fastProCount"));
				addOption("6","6",document.getElementById("fastProCount"));
				addOption("7","7",document.getElementById("fastProCount"));
				addOption("8","8",document.getElementById("fastProCount"));
				addOption("9","9",document.getElementById("fastProCount"));
				addOption("10","10",document.getElementById("fastProCount"));
				addOption("11","11",document.getElementById("fastProCount"));
				addOption("12","12",document.getElementById("fastProCount"));
				addOption("13","13",document.getElementById("fastProCount"));
				addOption("14","14",document.getElementById("fastProCount"));
				addOption("15","15",document.getElementById("fastProCount"));
				addOption("16","16",document.getElementById("fastProCount"));
			}
			else
			{
				addOption("1","1",document.getElementById("fastProCount"));
				addOption("2","2",document.getElementById("fastProCount"));
				addOption("3","3",document.getElementById("fastProCount"));
				addOption("4","4",document.getElementById("fastProCount"));
				addOption("5","5",document.getElementById("fastProCount"));
				addOption("6","6",document.getElementById("fastProCount"));
				addOption("7","7",document.getElementById("fastProCount"));
				addOption("8","8",document.getElementById("fastProCount"));
				addOption("9","9",document.getElementById("fastProCount"));
				addOption("10","10",document.getElementById("fastProCount"));
				addOption("11","11",document.getElementById("fastProCount"));
				addOption("12","12",document.getElementById("fastProCount"));
				addOption("13","13",document.getElementById("fastProCount"));
				addOption("14","14",document.getElementById("fastProCount"));
				addOption("15","15",document.getElementById("fastProCount"));
				addOption("16","16",document.getElementById("fastProCount"));
			}
	   	}
	   	catch(e){alert(e.message);}
    }
    
   
	//----------------------------加载明细-------------------Start
    function loadSelecDetialData(data, rowindex, rowobj)
    {
        try{
        		 
				 strCurBillId=data.bchangebillid;
       			 var params = "strCurCompId="+strCurCompId;
       			 params =params+ "&strCurBillId="+strCurBillId;
				 var myAjax = new parent.Ajax.Request(
						"cc012/loadCurMaster.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {							
								var responsetext = eval( "("+request.responseText+")");	
	   							loadcurmaster(responsetext.curMproexchangeinfo);
	   							if(responsetext.lsDproexchangeinfo!=null && responsetext.lsDproexchangeinfo.length>0)
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: responsetext.lsDproexchangeinfo,Total: responsetext.lsDproexchangeinfo.length});
					            	commoninfodivdetial.loadData(true);         	
						   		}
						   		else
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
					            	commoninfodivdetial.loadData(true);  
					            	item_adddetial();
						   		}
						   		if(responsetext.lsDproexchangeinfobypro!=null && responsetext.lsDproexchangeinfobypro.length>0)
						   		{
						   			commoninfodivdetialpro.options.data=$.extend(true, {},{Rows: responsetext.lsDproexchangeinfobypro,Total: responsetext.lsDproexchangeinfobypro.length});
					            	commoninfodivdetialpro.loadData(true);         	
						   		}
						   		else
						   		{
						   			commoninfodivdetialpro.options.data=$.extend(true, {},{Rows: null,Total:0});
					            	commoninfodivdetialpro.loadData(true); 
					            	item_addpro();
						   		}
							},
							asynchronous:true
						});			  
          }catch(e){alert(e.message);}
    }
	    
   	function  item_addpro()
   	{
        	var row = commoninfodivdetialpro.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivdetialpro.addRow({ 
				                changeproid: "",
				                changeproname: "",
				                lastcount: "",
				                lastamt: "",
				             	changeprocount:0,
				             	changeproamt:0
					            }, row, false);
		
	}
	function  item_adddetial()
	{
	   if(commoninfodivdetial.rows.length*1>0 && checkNull(curRecord.changeproid)=="")
    	{
    			return ;
    	}
    	var detiallength=0;
    	var row = commoninfodivdetial.getSelectedRow();
    	if(commoninfodivdetial.rows.length*1==0)
    	{
    		detiallength=-1;
    	}
    	else
    	{
    		detiallength=commoninfodivdetial.rows.length*1-1;
    	}				    
		commoninfodivdetial.addRow({ 
		changeproid: "",
		changeproname: "",
		procount: "",
		changeprocount:0,
		changeprorate: "",
		changeproamt: "",
		changebyproaccountamt: "",
		changebyaccountamt: "",
		changepaymode: "",
		changebycashamt: "",
		nointernalcardno: "",
		changebydyqamt: "",
		firstsalerid: "",
		firstsaleamt: "",
		thirdsalerid: "",
		thirdsaleamt: ""
		}, row, false);
		commoninfodivdetial.select(detiallength*1+1);
		curitemManger=null;
		curEmpManger=null;
		document.getElementById("fastProId").value="";
		document.getElementById("fastProCount").value="";
		document.getElementById("fastProDkAmt").value="";
		document.getElementById("fastSendDkAmt").value=""; 
		document.getElementById("fastKeepDkAmt").value="";
		document.getElementById("fastPayCode").value="";
		document.getElementById("fastCashDkAmt").readOnly="readOnly";
		document.getElementById("fastCashDkAmt").style.background="#EDF1F8"
		document.getElementById("fastCashDkAmt").value="";
		document.getElementById("fastDyNo").value="";
		document.getElementById("fastFirstEmp").value="";
		document.getElementById("fastSecondEmp").value="";
		document.getElementById("fastFristShare").value=0;
		document.getElementById("fastProId").select();
		document.getElementById("fastProId").focus();
	}
	
   	function loadcurmaster(curmaster)
   	{
   		try
		{
			document.getElementById("changecompid").value=checkNull(curmaster.id.changecompid);
	   		document.getElementById("changebillid").value=checkNull(curmaster.id.changebillid);
	   		document.getElementById("changecardno").value=checkNull(curmaster.changecardno);
	   		//document.getElementById("changeaccounttype").value=checkNull(curmaster.changeaccounttype);
	   		document.getElementById("changecardtype").value=checkNull(curmaster.changecardtype);
	   		document.getElementById("changecardtypename").value="";
	   		document.getElementById("membername").value=checkNull(curmaster.membername);
	   		document.getElementById("memberphone").value=checkNull(curmaster.memberphone);
	   		if(checkNull(curmaster.membername)!="")
	   			document.getElementById("lbmembername").value=checkNull(curmaster.membername).substring(0,1)+"**";
	   		else	
	   			document.getElementById("lbmembername").value="";
	   		if(checkNull(curmaster.memberphone)!="" && checkNull(curmaster.memberphone).length==11)
	   			document.getElementById("lbmemberphone").value=checkNull(curmaster.memberphone).substring(0,3)+"****"+checkNull(curmaster.memberphone).substring(7,11);
	   		else
	   			document.getElementById("lbmemberphone").value="";
	   		document.getElementById("curaccountkeepamt").value=checkNull(curmaster.curaccountkeepamt);
	   		document.getElementById("cursendaccountkeepamt").value=checkNull(curmaster.cursendaccountkeepamt);
	   		document.getElementById("curfaceaccountamt").value=checkNull(curmaster.curfaceaccountamt);
	   		document.getElementById("curaccountdebtamt").value=checkNull(curmaster.curaccountdebtamt);
	   		document.getElementById("curproaccountamt").value=checkNull(curmaster.curproaccountamt);
	   		document.getElementById("curproaccountdebtamt").value=checkNull(curmaster.curproaccountdebtamt);
	   		document.getElementById("changedate").value=checkNull(curmaster.changedate);
	   		document.getElementById("changetime").value=checkNull(curmaster.changetime);
	   		document.getElementById("changeopationerid").value=checkNull(curmaster.changeopationerid);
	   		document.getElementById("changeopationdate").value=checkNull(curmaster.changeopationdate);
	   		document.getElementById("backcsflag").value=checkNull(curmaster.backcsflag);
			document.getElementById("SP028Rate").value="";
			document.getElementById("changePackageNo").value="";
			//document.getElementById("changePackageName").value="";
	   		//handleRadio("backcsflag",checkNull(curmaster.backcsflag));
	   		if(checkNull(curmaster.changecardno)=="")			
	   		{
	   			writePage();
	   		}
	   		else
	   		{
	   			readPage();
	   		}
   		}catch(e){alert(e.message);}
   	}
   	
	function writePage()
    {
    	
    	pageState=1;
     	//document.getElementById("changecardno").value="";
     	//document.getElementById("changecardno").readOnly="";
     	document.getElementById("changePackageNo").value="";
     	document.getElementById("changePackageNo").readOnly="";
     	document.getElementById("changecardtype").value="";
     	document.getElementById("changecardtypename").value="";
     	document.getElementById("membername").value="";
     	document.getElementById("memberphone").value="";
     	document.getElementById("lbmembername").value="";
     	document.getElementById("lbmemberphone").value="";
     	document.getElementById("curaccountkeepamt").value="";
     	document.getElementById("cursendaccountkeepamt").value="";
     	document.getElementById("curfaceaccountamt").value="";
     	document.getElementById("curaccountdebtamt").value="";
     	document.getElementById("curproaccountamt").value="";
     	document.getElementById("curproaccountdebtamt").value="0";
     	document.getElementById("changePackageNo").value="";
     	//document.getElementById("changePackageName").value="0";
     	//document.getElementById("fifthsaleamt").readOnly="";
     	//document.getElementById("sixthsaleamt").readOnly="";
     	//document.getElementById("seventhsaleamt").readOnly="";
     	commoninfodivdetialpro.options.data=$.extend(true, {},{Rows: null,Total: 0});
		commoninfodivdetialpro.loadData(true);   
		commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total: 0});
		commoninfodivdetial.loadData(true); 
     	commoninfodivdetialpro.options.clickToEdit=true;
     	commoninfodivdetialpro.options.enabledEdit=true;
     	commoninfodivdetial.options.clickToEdit=false;
     	commoninfodivdetial.options.enabledEdit=false;
     	item_addpro();
		item_adddetial();
    }
    function readPage()
    {

     	document.getElementById("changecardno").readOnly="readOnly";
     	document.getElementById("changePackageNo").readOnly="readOnly";
     	pageState=3;
     	commoninfodivdetial.options.clickToEdit=false;
     	commoninfodivdetial.options.enabledEdit=false;
     	commoninfodivdetialpro.options.clickToEdit=false;
     	commoninfodivdetialpro.options.enabledEdit=false;
    }
    
    
    function validateExchangecardno(obj)
    {
    	if(obj.value=="")
    	{
     		document.getElementById("changecardtype").value="";
     		document.getElementById("changecardtypename").value="";
     		document.getElementById("membername").value="";
     		document.getElementById("memberphone").value="";
     		document.getElementById("lbmembername").value="";
     		document.getElementById("lbmemberphone").value="";
     		document.getElementById("curaccountkeepamt").value="";
     		document.getElementById("cursendaccountkeepamt").value="";
     		document.getElementById("curfaceaccountamt").value="";
     		document.getElementById("curaccountdebtamt").value="";
     		document.getElementById("curproaccountamt").value="";
     		document.getElementById("curproaccountdebtamt").value="0";
    	}
    	else
    	{
    		var requestUrl ="cc012/validateExchangecardno.action"; 
			var responseMethod="validateExchangecardnoMessage";	
			var params="strCardNo="+obj.value;	
			params =params+ "&strCurCompId="+strCurCompId;		
			sendRequestForParams_p(requestUrl,responseMethod,params );
    	}
    }
    
    function validateExchangecardnoMessage(request)
   	{
       	try
        {
        	var responsetext = eval("(" + request.responseText + ")");
    		var strmessage=	checkNull(responsetext.strMessage);
	   		if(strmessage!="")
	   		{
	   			$.ligerDialog.warn(strmessage);
	   			document.getElementById("changecardno").value="";
	   			document.getElementById("changecardtype").value="";
     			document.getElementById("changecardtypename").value="";
     			document.getElementById("membername").value="";
     			document.getElementById("memberphone").value="";
     			document.getElementById("lbmembername").value="";
     			document.getElementById("lbmemberphone").value="";
     			document.getElementById("curaccountkeepamt").value="";
     			document.getElementById("cursendaccountkeepamt").value="";
     			document.getElementById("curfaceaccountamt").value="";
     			document.getElementById("curaccountdebtamt").value="";
     			document.getElementById("curproaccountamt").value="";
     			document.getElementById("curproaccountdebtamt").value="0";
     			$("#randomno").val("");
	   		}
	   		else
	   		{
	   			document.getElementById("changecardtype").value=checkNull(responsetext.curCardinfo.cardtype);;
     			document.getElementById("changecardtypename").value=checkNull(responsetext.curCardinfo.cardtypeName);
     			document.getElementById("membername").value=checkNull(responsetext.curCardinfo.membername);
     			document.getElementById("memberphone").value=checkNull(responsetext.curCardinfo.memberphone);
     			if(checkNull(responsetext.curCardinfo.membername)!="")
	   				document.getElementById("lbmembername").value=checkNull(responsetext.curCardinfo.membername).substring(0,1)+"**";
	   			else	
	   				document.getElementById("lbmembername").value="";
	   			if(checkNull(responsetext.curCardinfo.memberphone)!="" && checkNull(responsetext.curCardinfo.memberphone).length==11)
	   				document.getElementById("lbmemberphone").value=checkNull(responsetext.curCardinfo.memberphone).substring(0,3)+"****"+checkNull(responsetext.curCardinfo.memberphone).substring(7,11);
	   			else
	   				document.getElementById("lbmemberphone").value="";
     			document.getElementById("curaccountkeepamt").value=checkNull(responsetext.curCardinfo.account2Amt);
     			document.getElementById("cursendaccountkeepamt").value=checkNull(responsetext.curCardinfo.account6Amt);
     			document.getElementById("curaccountdebtamt").value=checkNull(responsetext.curCardinfo.account2debtAmt);
     			document.getElementById("curproaccountamt").value=checkNull(responsetext.curCardinfo.account4Amt);
     			document.getElementById("curproaccountdebtamt").value=checkNull(responsetext.curCardinfo.account4debtAmt);
     			document.getElementById("curfaceaccountamt").value=checkNull(responsetext.curCardinfo.account7Amt);//美容积分
   				dSaleProRate=checkNull(responsetext.curCardinfo.slaeproerate);
   				document.getElementById("dSaleProRate").value=checkNull(responsetext.curCardinfo.slaeproerate);
   				if(responsetext.lsCardproaccount!=null && responsetext.lsCardproaccount.length>0)
		   		{
		   			commoninfodivdetialpro.options.data=$.extend(true, {}, loadCurProInfo(responsetext.lsCardproaccount));
	            	commoninfodivdetialpro.loadData(true);            	
		   		}
		   		else
		   		{
		   			commoninfodivdetialpro.options.data=$.extend(true, {},{Rows: null,Total:0});
	            	commoninfodivdetialpro.loadData(true);  
	            	item_addpro()
		   		}
	    
				commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total: 0});
				commoninfodivdetial.loadData(true); 
				item_adddetial();
				document.getElementById("fastProId").select();
				document.getElementById("fastProId").focus();
	   		}
	   	
	   	}catch(e){alert(e.message);}
    }
    
    //加载疗程信息
  	function loadCurProInfo(lsCardproaccount)
		{
			try
			{
				var strJson = "";//'{ "name": "cxh", "sex": "man" }';
				var ccount=0;
				if(lsCardproaccount!=null && lsCardproaccount.length>0)
				{	
					for(var i=0;i<lsCardproaccount.length*1;i++)
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
						strJson=strJson+'{"changeproid":"'+lsCardproaccount[i].bprojectno+'", "changeproname": "'+lsCardproaccount[i].bprojectname+'","bproseqno":"'+lsCardproaccount[i].bproseqno+'","lastcount": "'+lsCardproaccount[i].lastcount+'","lastamt": "'+lsCardproaccount[i].lastamt+'","changeprocount":"0","changeproamt":"0"  }';
					}
					if(strJson!="")
					{
						strJson=strJson+']';
						return {Rows:JSON.parse(strJson),Total:lsCardproaccount.length};
					}
					return null;
				}
			}catch(e){alert(e.message);}
		}
    
   	//验证抵用疗程次数
   	function validateCurCostCount(obj)
   	{
   		try
   		{
   			if(obj.value=="" || obj.value==0)
	   		{
	   			commoninfodivdetialpro.updateRow(curProRecord,{changeprocount:'0',changeproamt: '0'});
	   			return;
	   		}
	   		var lastcount=ForDight(curProRecord.lastcount*1,1);
	   		var lastamt=ForDight(curProRecord.lastamt,1);
	   		if(ForDight(curProRecord.lastcount,1)<ForDight(obj.value*1,1))
	   		{
	   			$.ligerDialog.warn("退换次数不能超过本疗程的剩余次数!");
	   			commoninfodivdetialpro.updateRow(curProRecord,{changeprocount:'0',changeproamt: '0'});
	   			return;
	   		}
	   		var oneprice=ForDight(lastamt/lastcount,2);

	   		commoninfodivdetialpro.updateRow(curProRecord,{changeproamt: ForDight(oneprice*1*obj.value*1,0)});
   		}
   		catch(e){alert(e.message);}
   		
   	}
   	
   	function validateChangeCount(obj)
   	{
   		if(obj.value=="" || obj.value==0)
	   	{
	   		obj.value=1;
	   	}
	   	dSaleProRate=document.getElementById("dSaleProRate").value;
	   	var yearcount=ForDight(curRecord.standchangeprocount*1*obj.value*1,1);
	    var yearamt=ForDight(curRecord.standchangeproamt*obj.value*1*dSaleProRate*1*curRecord.changeprorate*1,1);
	    commoninfodivdetial.updateRow(curRecord,{changeprocount: yearcount,changeproamt: yearamt});
		
   	}
   	function loadAutoStaff(curmanager,curWriteStaff)
	{	
		curmanager.setData(loadGridChooseByStaffInfo(lsStaffinfo,curWriteStaff));
		curmanager.selectBox.show();
		curEmpManger=curmanager;
	 }
   function loadAutoProject(curmanager,curWriteitemname)
	{	
		curmanager.setData(loadProjectControlDate_selectL(curWriteitemname));
		curmanager.selectBox.show();
		curitemManger=curmanager;
	}
	
	function loadProjectControlDate_selectL(curWriteItemNo)
	{
		
		try{
		var returnValue='';
		var key='';
		var value='';
		var projectcount=0;
		for(var i=0;i<parent.lsProjectinfo.length;i++)
		{
			if(projectcount*1==10)
			{
				break;
			}
			if(curWriteItemNo!="" 
			 && parent.lsProjectinfo[i].id.prjno.indexOf(curWriteItemNo)==-1
			 && checkNull(parent.lsProjectinfo[i].prjabridge).toLowerCase().indexOf(curWriteItemNo.toLowerCase())==-1
			 && checkNull(toPinyin(parent.lsProjectinfo[i].prjname)).indexOf(curWriteItemNo.toUpperCase())==-1)
			{
				continue;
			}
		   	key = parent.lsProjectinfo[i].id.prjno;
			value = parent.lsProjectinfo[i].prjname;
			if(returnValue!='')
			{
				returnValue=returnValue+',';
			}
			else
			{
				returnValue=returnValue+'[';
			}
			
			projectcount=projectcount*1+1;
			returnValue=returnValue+'{"choose": "'+key+'","text": "'+key+'_'+value+'"}';
		}
		if(returnValue!='')
		{
			returnValue=returnValue+']';	
			return 	JSON.parse(returnValue);
		}
		else
		{	
			return null;
		}
		}catch(e){alert(e.message);}
	}
	
   	//---------------------------------------------验证项目编号
	function validateItem(obj)
	{
		if(document.getElementById("changecardno").value=="")
		{
			$.ligerDialog.warn("请确认兑换的卡号!");
			commoninfodivdetial.updateRow(curRecord,{changeproid:'',changeproname:'',procount: '',changeprocount: '',changeprorate: '1',changeproamt: '',changepaymode:'1',changebycashamt:0});
			return ;
		}
		var curItemValue="";
		if(curitemManger!=null && curitemManger.inputText.val()!="")
			curItemValue=curitemManger.inputText.val();
		else
			curItemValue=curRecord.csitemname;
		curitemManger=null;	
		var requestUrl ="cc012/validateItem.action"; 
		var responseMethod="validateItemMessage";
		if(curItemValue=="")
		{
			commoninfodivdetial.updateRow(curRecord,{changeproname:'',procount: '',changeprocount: '',changeprorate: '1',changeproamt: '',changepaymode:'1',changebycashamt:0});
			return ;
		}
		if(curItemValue=="" )
		{
			commoninfodivdetial.updateRow(curRecord,{csitemname:'',csitemno: '',changepaymode:'1',changebycashamt:0});
			return ;
		}
		if( curItemValue.indexOf('_')!=-1)
		{
			curItemValue=curItemValue.substring(0,curItemValue.indexOf('_'));
		}
		var params="strCurItemId="+curItemValue;	
		params =params+ "&strCurCompId="+strCurCompId;
		sendRequestForParams_p(requestUrl,responseMethod,params );
	
	}
	
	function validateProNo(obj)
	{
		var flag = false;
		var dataSet = commoninfodivdetial.getData();
		var fastProId = $("#fastProId").val();
		$(dataSet).each(function(){
			if(this.changeproid==changeproid1){
				if(fastProId==this.changeproid){
					flag = true;
					$.ligerDialog.warn("此疗程已经添加至明细列表中，无法重复添加！");
					return false;
				}
			}
		});
		if(flag){
			return;
		}
		if(document.getElementById("changecardno").value=="")
		{
			$.ligerDialog.warn("请确认兑换的卡号!");
			commoninfodivdetial.updateRow(curRecord,{changeproid:'',changeproname:'',procount: '',changeprocount: '',changeprorate: '1',changeproamt: '',changepaymode:'1',changebycashamt:0});
			return ;
		}
		if(obj.value=="")
		{
			return ;
		}
		else
		{
			var requestUrl ="cc012/validateItem.action"; 
			var responseMethod="validateItemMessage";
			var params="strCurItemId="+obj.value;;	
			params =params+ "&strCurCompId="+strCurCompId;
			sendRequestForParams_p(requestUrl,responseMethod,params );
		}
	}

	function validateItemMessage(request)
	{
		try
		{
	     	var responsetext = eval("(" + request.responseText + ")");
			$("#rateToRate").val("1");
	     	var curProjectinfo=responsetext.curProjectinfo;
	     	if(curProjectinfo==null)
	   	 	{
	     		$.ligerDialog.warn("输入的项目编码不存在!");
	     		return;
	     	}
			else if(checkNull(curProjectinfo.useflag)==2)
			{
			  	$.ligerDialog.warn("该项目已经停止使用");
			  	return;
			}
			else if(checkNull(curProjectinfo.saleflag)==2)
			{
			  	$.ligerDialog.warn("该项目已经停止销售");
			  	return;
			}
			else
	  		{
	  			dSaleProRate=document.getElementById("dSaleProRate").value;
	  			if(checkNull(curProjectinfo.rateflag)*1==2)
	  			{
	  				dSaleProRate=1;
	  			}
	  			else
	  			{
	  				dSaleProRate=dSaleProRate*1*(checkNull(responsetext.rateToRate))*1
					$("#rateToRate").val(responsetext.rateToRate);
					//document.getElementById("rateToRate").value=responsetext.rateToRate;
	  			}
	  			document.getElementById("fastProCount").value=1;
	  			document.getElementById("fastProCount").focus();
	  			//document.getElementById("fastProCount").select();
	  			item_adddetial();
	  			commoninfodivdetial.updateRow(curRecord,{changeproid:curProjectinfo.id.prjno,changeproname:curProjectinfo.prjname,procount: 1,changeprocount:curProjectinfo.ysalecount,standchangeprocount:curProjectinfo.ysalecount,changeprorate: dSaleProRate,changeproamt:ForDight(curProjectinfo.ysaleprice*1*dSaleProRate*1,1),standchangeproamt:  curProjectinfo.ysaleprice ,changebyaccountamt :ForDight(curProjectinfo.ysaleprice*1*dSaleProRate*1,1)});
				if(document.getElementById("SP095Flag").value=="1")
				{
					$.ligerDialog.prompt('请输入该疗程的'+1+'次折后最终售价,折后价['+ForDight(curProjectinfo.ysaleprice*1*dSaleProRate*1,1)+']','', function (yes,value) { if(yes) factSaleAmt(value); });
				}
			}
			if(checkNull(curProjectinfo.markflag)==1)
			{
				markcustomerDialog=$.ligerDialog.open({ height: null, url: contextURL+'/CardControl/CC012/handProjectMark.jsp', width: 400, showMax: false, showToggle: false, showMin: false, isResize: false, title: '疗程备注' });
			}
	     }catch(e){alert(e.message);}
	
	}
	
	function validatefastProCount(obj)
	{
		if(obj.value=="" || obj.value==0)
	   	{
	   		obj.value=1;
	   	}
	   	//dSaleProRate=document.getElementById("dSaleProRate").value;
	   	dSaleProRate=curRecord.changeprorate*1;
	   	var yearcount=ForDight(curRecord.standchangeprocount*1*obj.value*1,0);
	   	var difcount=ForDight(curRecord.standchangeprocount*1-yearcount*1,0);
	   	if(obj.value*1==0.5)
	   	{
	   		if(yearcount*1!=difcount*1)
	   		{
	   			$.ligerDialog.confirmLCDH('确认 兑换疗程的次数', function (result)
				{
			   	 	if( result==true)
	           		{
						var yearamt=ForDight(curRecord.standchangeproamt*1/curRecord.standchangeprocount*1*yearcount*1*dSaleProRate*1,1);
					    commoninfodivdetial.updateRow(curRecord,{procount: obj.value*1,changeprocount: yearcount,changeproamt: yearamt,
					    										changebyaccountamt :ForDight(yearamt*1-curRecord.changebyproaccountamt*1-curRecord.changebydyqamt*1-curRecord.changebycashamt*1,1)});
						
						if(document.getElementById("SP095Flag").value=="1")
						{
							$.ligerDialog.prompt('请输入该疗程的'+yearcount+'次折后最终售价,折后价['+yearamt+']','', function (yes,value) { if(yes) factSaleAmt(value); });
						}
					}
					else
	           		{
						yearcount=difcount;
						var yearamt=ForDight(curRecord.standchangeproamt*1/curRecord.standchangeprocount*1*yearcount*1*dSaleProRate*1,1);
					    commoninfodivdetial.updateRow(curRecord,{procount: obj.value*1,changeprocount: yearcount,changeproamt: yearamt,
					    										changebyaccountamt :ForDight(yearamt*1-curRecord.changebyproaccountamt*1-curRecord.changebydyqamt*1-curRecord.changebycashamt*1,1)});
						
						if(document.getElementById("SP095Flag").value=="1")
						{
							$.ligerDialog.prompt('请输入该疗程的'+yearcount+'次折后最终售价,折后价['+yearamt+']','', function (yes,value) { if(yes) factSaleAmt(value); });
						}
					}
				}); 
	   		}
	   		else
	   		{
	   			var yearamt=ForDight(curRecord.standchangeproamt*1/curRecord.standchangeprocount*1*yearcount*1*dSaleProRate*1,1);
			    commoninfodivdetial.updateRow(curRecord,{procount: obj.value*1,changeprocount: yearcount,changeproamt: yearamt,
			    										changebyaccountamt :ForDight(yearamt*1-curRecord.changebyproaccountamt*1-curRecord.changebydyqamt*1-curRecord.changebycashamt*1,1)});
				
				if(document.getElementById("SP095Flag").value=="1")
				{
					$.ligerDialog.prompt('请输入该疗程的'+yearcount+'次折后最终售价,折后价['+yearamt+']','', function (yes,value) { if(yes) factSaleAmt(value); });
				}
	   		}
	   	}
	   	else
	   	{
		    var yearamt=ForDight(curRecord.standchangeproamt*1/curRecord.standchangeprocount*1*yearcount*1*dSaleProRate*1,1);
		    commoninfodivdetial.updateRow(curRecord,{procount: obj.value*1,changeprocount: yearcount,changeproamt: yearamt,
		    										changebyaccountamt :ForDight(yearamt*1-curRecord.changebyproaccountamt*1-curRecord.changebydyqamt*1-curRecord.changebycashamt*1,1)});
			
			if(document.getElementById("SP095Flag").value=="1")
			{
				$.ligerDialog.prompt('请输入该疗程的'+yearcount+'次折后最终售价,折后价['+yearamt+']','', function (yes,value) { if(yes) factSaleAmt(value); });
			}
		}
		//美容的疗程超过24(4套)后每次送50块
		if(document.getElementById("SP099Flag").value==1 && obj.value*1>=4)
		{
			for(var i=0;i<parent.lsProjectinfo.length;i++)
			{
				if(checkNull(curRecord.changeproid)==parent.lsProjectinfo[i].id.prjno)
				{
					if(checkNull(parent.lsProjectinfo[i].rateflag)*1==1 && parent.lsProjectinfo[i].prjtype==4)
					{
							commoninfodivdetial.updateRow(curRecord,{changeproamt: checkNull(curRecord.changeproamt)*1-yearcount*1*50,
		    										changebyaccountamt :ForDight(yearamt*1-yearcount*1*50-curRecord.changebyproaccountamt*1-curRecord.changebydyqamt*1-curRecord.changebycashamt*1,1)});
					}
					break;
				}
			}
		}
	}
	
	function factSaleAmt(factvalue)
	{
			var discount=document.getElementById("dSaleProRate").value;
			var afterdiscount=ForDight(curRecord.standchangeproamt*1/curRecord.standchangeprocount*1*curRecord.changeprocount*1*discount*1,1);
			if(ForDight(factvalue,1)<afterdiscount)
		 	{
		 		$.ligerDialog.error("兑换的疗程价不能低于标准折后价"+afterdiscount);
		 		commoninfodivdetial.updateRow(curRecord,{changeproamt: afterdiscount,
	    										  changebyaccountamt :ForDight(afterdiscount*1-curRecord.changebyproaccountamt*1-curRecord.changebydyqamt*1-curRecord.changebycashamt*1,1)});
				return ;
		 	}
		 	commoninfodivdetial.updateRow(curRecord,{changeproamt: factvalue,
	    										  changebyaccountamt :ForDight(factvalue*1-curRecord.changebyproaccountamt*1-curRecord.changebydyqamt*1-curRecord.changebycashamt*1,1)});
		
		document.getElementById("fastDyNo").focus();
	  	document.getElementById("fastDyNo").select();
	}
	function validateFastProDkAmt(obj)
	{
			var factKeepAmt=0;
			if(curRecord.changeprorate*1==1){
				factKeepAmt=ForDight(checkNull(curRecord.changeproamt)*1-obj.value*1-checkNull(curRecord.changebysendaccountamt)*1-checkNull(curRecord.changebydyqamt)*1-checkNull(curRecord.changebycashamt)*1,1);
				factKeepAmt=ForDight(factKeepAmt-checkNull(curRecord.changebyfaceamt)*1, 1);
			}else{
				var changeprorate=null;
				if(curRecord.changeproid==changeproid1){
					changeprorate=0.9;
					factKeepAmt=ForDight(checkNull(curRecord.changeproamt)*1-obj.value*1-checkNull(curRecord.changebysendaccountamt)*1-checkNull(curRecord.changebydyqamt)*1*changeprorate-checkNull(curRecord.changebycashamt)*1,1);
					factKeepAmt=ForDight(factKeepAmt-checkNull(curRecord.changebyfaceamt)*1, 1);
				}else{
					changeprorate=document.getElementById("dSaleProRate").value*1;
					factKeepAmt=ForDight(checkNull(curRecord.changeproamt)*1-obj.value*1-checkNull(curRecord.changebysendaccountamt)*1-checkNull(curRecord.changebydyqamt)*1*changeprorate-checkNull(curRecord.changebycashamt)*1*changeprorate,1);
					factKeepAmt=ForDight(factKeepAmt-checkNull(curRecord.changebyfaceamt)*1*changeprorate, 1);
				}
			}
			commoninfodivdetial.updateRow(curRecord,{changebyproaccountamt:ForDight(obj.value*1,1),changebyaccountamt :ForDight(factKeepAmt,1)});
	}
	function validateFastSendDkAmt(obj)
	{		
			var factKeepAmt=0;
			if(curRecord.changeprorate*1==1){
				factKeepAmt=ForDight(checkNull(curRecord.changeproamt)*1-obj.value*1-checkNull(curRecord.changebyproaccountamt)*1-checkNull(curRecord.changebydyqamt)*1-checkNull(curRecord.changebycashamt)*1,1);
				factKeepAmt=ForDight(factKeepAmt-checkNull(curRecord.changebyfaceamt)*1, 1);
			}else{
				var changeprorate=null;
				if(curRecord.changeproid==changeproid1){
					changeprorate=0.9;
					factKeepAmt=ForDight(checkNull(curRecord.changeproamt)*1-obj.value*1-checkNull(curRecord.changebyproaccountamt)*1-checkNull(curRecord.changebydyqamt)*1*changeprorate-checkNull(curRecord.changebycashamt)*1,1);
					factKeepAmt=ForDight(factKeepAmt-checkNull(curRecord.changebyfaceamt)*1, 1);
				}else{
					changeprorate=document.getElementById("dSaleProRate").value*1;
					factKeepAmt=ForDight(checkNull(curRecord.changeproamt)*1-obj.value*1-checkNull(curRecord.changebyproaccountamt)*1-checkNull(curRecord.changebydyqamt)*1*changeprorate-checkNull(curRecord.changebycashamt)*1*changeprorate,1);
					factKeepAmt=ForDight(factKeepAmt-checkNull(curRecord.changebyfaceamt)*1*changeprorate, 1);
				}
			}
			commoninfodivdetial.updateRow(curRecord,{changebysendaccountamt :ForDight(obj.value*1,1),changebyaccountamt :ForDight(factKeepAmt,1)});
	}
	
	
	function validateFastKeepDkAmt(obj)
	{
			commoninfodivdetial.updateRow(curRecord,{changebyaccountamt :ForDight(obj.value*1,1)});
	}
	
	function validatePayCode(obj)
	{
		 if(obj.value=="")
		 {
		 		document.getElementById("fastCashDkAmt").readOnly="readOnly";
		 		document.getElementById("fastCashDkAmt").style.background="#EDF1F8"
		 }
		 else
		 {
		 		document.getElementById("fastCashDkAmt").readOnly="";
		 		document.getElementById("fastCashDkAmt").style.background="#FFFFFF"
		 }
		
		    			
		 commoninfodivdetial.updateRow(curRecord,{changepaymode:obj.value});
	}
	function validateFastCashDkAmt(obj)
	{
		  if(document.getElementById("fastPayCode").value=="")
		  {
		  		$.ligerDialog.error("请输入支付方式");
		  		return;
		  }
		  var factKeepAmt=0;
		  if(curRecord.changeprorate*1==1){
			  factKeepAmt=ForDight((checkNull(curRecord.changeproamt)*1-checkNull(curRecord.changebyproaccountamt)*1-checkNull(curRecord.changebysendaccountamt)*1)-checkNull(curRecord.changebydyqamt)*1,1)-checkNull(curRecord.changebyfaceamt)*1;
			  factKeepAmt=ForDight((factKeepAmt*1-obj.value*1),1);
		  }else{
			  var changeprorate=null;
			  if(curRecord.changeproid==changeproid1){
					changeprorate=0.9;
					factKeepAmt=(checkNull(curRecord.changeproamt)*1-checkNull(curRecord.changebyproaccountamt)*1-checkNull(curRecord.changebysendaccountamt)*1)/changeprorate-checkNull(curRecord.changebydyqamt)*1-checkNull(curRecord.changebyfaceamt)*1;
					factKeepAmt=ForDight((factKeepAmt*1*changeprorate*1-obj.value*1),1);//8.5活动疗程：标准额*折扣-金额
				}else{
					changeprorate=document.getElementById("dSaleProRate").value*1;//（标准额-金额）*折扣
					factKeepAmt=(checkNull(curRecord.changeproamt)*1-checkNull(curRecord.changebyproaccountamt)*1-checkNull(curRecord.changebysendaccountamt)*1)/changeprorate-checkNull(curRecord.changebydyqamt)*1-checkNull(curRecord.changebyfaceamt)*1;
					factKeepAmt=ForDight((factKeepAmt*1-obj.value*1)*changeprorate*1,1);
				}
		  }
		  commoninfodivdetial.updateRow(curRecord,{changepaymode:document.getElementById("fastPayCode").value,changebycashamt:ForDight(obj.value*1,1),changebyaccountamt :ForDight(factKeepAmt,1)});
	}
	function validateFastFirstEmp(obj)
	{
		if(obj.value=="")
		{
			commoninfodivdetial.updateRow(curRecord,{firstsalerid: "",firstsaleamt:0});
		}
		else
		{
				var exists=0;
		    	for(var i=0;i<lsStaffinfo.length;i++)
		    	{
		    		if(obj.value==lsStaffinfo[i].bstaffno)
		    		{
		    			commoninfodivdetial.updateRow(curRecord,{firstsalerid: obj.value});
		    			exists=1;
		    			break;
		    		}
		    			
		    	}
		    	if(exists==0)
		    	{
		    		$.ligerDialog.warn("第一销售不存在!");
		    		document.getElementById("fastDyNo").focus();
	  				document.getElementById("fastDyNo").select();
		    		commoninfodivdetial.updateRow(curRecord,{firstsalerid: "",firstsaleamt:0});
		    	}
		}
	}
	
	function validateFastFristShare(obj)
	{
		if(checkNull(curRecord.thirdsalerid)!="")
		{
			commoninfodivdetial.updateRow(curRecord,{firstsaleamt:obj.value*1,thirdsaleamt :obj.value*1});
		}
		else
		{
			commoninfodivdetial.updateRow(curRecord,{firstsaleamt:obj.value*1});
		}
	}
	function validateFastSecondEmp(obj)
	{
		if(obj.value=="")
		{
			commoninfodivdetial.updateRow(curRecord,{thirdsalerid: "",thirdsaleamt :0});
			
		}
		var firstsalerid=curRecord.firstsalerid;
		var firstsaleamt=curRecord.firstsaleamt;
		if(firstsalerid!="")
		{
				var exists=0;
		    	for(var i=0;i<lsStaffinfo.length;i++)
		    	{
		    		if(obj.value==lsStaffinfo[i].bstaffno 
		    		&& ( lsStaffinfo[i].position=="008" || lsStaffinfo[i].position=="00901" || lsStaffinfo[i].position=="00902" ||lsStaffinfo[i].position=="00903" || lsStaffinfo[i].position=="00904"))
		    		{
		    			commoninfodivdetial.updateRow(curRecord,{thirdsalerid:obj.value,thirdsaleamt :firstsaleamt});
		    			exists=1;
		    			
		    			break;
		    		}
		    			
		    	}
		    	if(exists==0)
		    	{
		    		$.ligerDialog.warn("第一烫染不存在!");
		    		document.getElementById("fastFirstEmp").focus();
	  				document.getElementById("fastFirstEmp").select();
		    		commoninfodivdetial.updateRow(curRecord,{thirdsalerid: "",thirdsaleamt :0});
		    	}
			
		}
		else
		{
			commoninfodivdetial.updateRow(curRecord,{thirdsalerid: "",thirdsaleamt :0});
		}
	}
	
	function validatefastMask(obj)
	{
		commoninfodivdetial.updateRow(curRecord,{changemark:obj.value});
	}
	function validateChangeProAccount(obj)
	{
		commoninfodivdetial.updateRow(curRecord,{changebyaccountamt :ForDight(curRecord.changeproamt*1-curRecord.changebycashamt*1-curRecord.changebydyqamt*1-curRecord.changebysendaccountamt*1-obj.value*1,1)});
	}
	
	function validateSendProAccount()
	{
		commoninfodivdetial.updateRow(curRecord,{changebyaccountamt :ForDight(curRecord.changeproamt*1-curRecord.changebycashamt*1-curRecord.changebydyqamt*1-curRecord.changebyproaccountamt*1-obj.value*1,1)});
	}
	function validateChangeKeepAccount(obj)
	{	
		commoninfodivdetial.updateRow(curRecord,{changebyaccountamt :ForDight(curRecord.changeproamt*1-curRecord.changebyproaccountamt*1-curRecord.changebydyqamt*1-curRecord.changebysendaccountamt*1-obj.value*1,1)});
	
	}
	
	function validateFastDyNo(obj)
	{
		if(obj.value.substring(0,2)=="NS" || obj.value.substring(0,3)=="LOR")
		{
			$.ligerDialog.warn("NS,LOR开头的抵用券不能使用!");
			commoninfodivdetial.updateRow(curRecord,{nointernalcardno:'',changebydyqamt:'0'});
			return;
		}
		if(obj.value=="")
		{
			commoninfodivdetial.updateRow(curRecord,{nointernalcardno:'',changebydyqamt:'0'});
			return ;
		}
		else
		{
			var iexiste=0;
			//验证抵用券是否在列表中已经存在
			for (var rowid in commoninfodivdetial.records)
			{
				var usercardno=checkNull(commoninfodivdetial.records[rowid]['nointernalcardno']);
				if(obj.value==usercardno)
				{
					iexiste=iexiste*1+1;
				}
			}
			if(iexiste>1)
			{
					$.ligerDialog.warn("输入的券编号已经存在于抵用的列表中,请确认!");
					document.getElementById("fastCashDkAmt").select();
					document.getElementById("fastCashDkAmt").focus();
					commoninfodivdetial.updateRow(curRecord,{nointernalcardno:'',changebydyqamt:'0'});
					return;
			}
			var requestUrl ="cc012/validateNointernalcardno.action"; 
			var responseMethod="validateNointernalcardnoMessage";
			var params="strDiyqNo="+obj.value;	
			sendRequestForParams_p(requestUrl,responseMethod,params );
		}
	}
	
	function validateTrShareAmt(obj)
	{
		if(obj.value=="")
		{
			commoninfodivdetial.updateRow(curRecord,{thirdsaleamt:'0'});
			return ;
		}
		var firstsalerid=curRecord.firstsalerid;
		var firstsaleamt=curRecord.firstsaleamt;
		if(firstsalerid!="")
		{
			commoninfodivdetial.updateRow(curRecord,{thirdsaleamt:firstsaleamt});
		}
	}
	
	function validateNointernalcardno(obj)
	{
		if(obj.value.substring(0,2)=="NS" || obj.value.substring(0,3)=="LOR")
		{
			$.ligerDialog.warn("NS,LOR开头的抵用券不能使用!");
			commoninfodivdetial.updateRow(curRecord,{nointernalcardno:'',changebydyqamt:'0'});
			return;
		}
		if(obj.value=="")
		{
			commoninfodivdetial.updateRow(curRecord,{nointernalcardno:'',changebydyqamt:'0'});
			return ;
		}
		else
		{
			var iexiste=0;
			//验证抵用券是否在列表中已经存在
			for (var rowid in commoninfodivdetial.records)
			{
				var usercardno=checkNull(commoninfodivdetial.records[rowid]['nointernalcardno']);
				if(obj.value==usercardno)
				{
					iexiste=iexiste*1+1;
				}
			}
			if(iexiste>1)
			{
					$.ligerDialog.warn("输入的券编号已经存在于抵用的列表中,请确认!");
					commoninfodivdetial.updateRow(curRecord,{nointernalcardno:'',changebydyqamt:'0'});
					return;
			}
			var requestUrl ="cc012/validateNointernalcardno.action"; 
			var responseMethod="validateNointernalcardnoMessage";
			var params="strDiyqNo="+obj.value;	
			sendRequestForParams_p(requestUrl,responseMethod,params );
		}
	}
	function validateFristSale(obj)
	{
		var curempValue="";
		if(curEmpManger!=null && curEmpManger.inputText.val()!="")
			curempValue=curEmpManger.inputText.val();
		else
			curempValue=obj.value;
		curEmpManger=null;
		if(curempValue!="")
		{
			if(curempValue!="" && curempValue.indexOf('_')==-1)
	    	{
	
	    		var exists=0;
		    	for(var i=0;i<lsStaffinfo.length;i++)
		    	{
		    		if(curempValue==lsStaffinfo[i].bstaffno)
		    		{
		    			commoninfodivdetial.updateRow(curRecord,{firstsalerid: lsStaffinfo[i].bstaffno});
		    			exists=1;
		    			break;
		    		}
		    			
		    	}
		    	if(exists==0)
		    	{
		    		commoninfodivdetial.updateRow(curRecord,{firstsalerid:''});
		    	}
		    }
		}
		//initDetialGrid();
	}
	function validateNointernalcardnoMessage(request)
   	{
       	try
        {
        	var responsetext = eval("(" + request.responseText + ")");
    		var strmessage=	checkNull(responsetext.strMessage);
	   		if(strmessage!="")
	   		{
	   			$.ligerDialog.warn(strmessage);
	   			document.getElementById("fastCashDkAmt").select();
				document.getElementById("fastCashDkAmt").focus();
	   			commoninfodivdetial.updateRow(curRecord,{nointernalcardno:'',changebydyqamt:'0'});
	   		}
	   		else
	   		{
	   			var factKeepAmt=0;
	   			if(curRecord.changeprorate*1==1){
	   				factKeepAmt=ForDight((checkNull(curRecord.changeproamt)*1-checkNull(curRecord.changebyproaccountamt)*1-checkNull(curRecord.changebysendaccountamt)*1)-checkNull(curRecord.changebycashamt)*1,1);
	   				factKeepAmt=ForDight((factKeepAmt*1-responsetext.diyqAmt*1-checkNull(curRecord.changebyfaceamt)*1),1);
	   			}else{
	   				var changeprorate=null;
	   				if(curRecord.changeproid==changeproid1){
	  					changeprorate=0.9;
	  				}else{
	  					changeprorate=document.getElementById("dSaleProRate").value*1;
	  				}
	   				factKeepAmt=ForDight((checkNull(curRecord.changeproamt)*1-checkNull(curRecord.changebyproaccountamt)*1-checkNull(curRecord.changebysendaccountamt)*1)/changeprorate-checkNull(curRecord.changebycashamt)*1,1);
	   				factKeepAmt=ForDight((factKeepAmt*1-responsetext.diyqAmt*1-checkNull(curRecord.changebyfaceamt)*1)*changeprorate,1);
	   			}
	   			commoninfodivdetial.updateRow(curRecord,{nointernalcardno:document.getElementById("fastDyNo").value,changebydyqamt:ForDight(responsetext.diyqAmt,1), changebyaccountamt:ForDight(factKeepAmt,1)});
	   		}

	
	   	}catch(e){alert(e.message);}
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
				item_adddetial();
				window.event.keyCode = 505;
				window.event.returnValue=false;
			}
			else if(key==115)//F4
			{
				deleteCurRecord();
			}
			else if( key == 83 &&  event.altKey)
			{
				editCurRecord();
			}
	
		}
		
		 
	    function deleteCurRecord()
	    {
	    	$.ligerDialog.confirm('确认删除当前选中行?', function (result)
			{
			    if( result==true)
	           	{
					commoninfodivdetial.deleteSelectedRow();
				
				}
			});  
	    	
	    }
		
		function  hotKeyEnter()
		{
		
			try
			{
				var fieldName = document.activeElement.name;
				var fieldId = document.activeElement.id ;
				if(fieldId=="fastProId")
				{
						validateProNo(document.activeElement);
				}
				else
				{
					window.event.keyCode=9; //tab
					window.event.returnValue=true;
					if(curitemManger!=null)
					{
							curitemManger.selectBox.hide();
					}
					if(curEmpManger!=null)
					{
							curEmpManger.selectBox.hide();
					}
				}
			}
			catch(e){alert(e.message);}
		}
		
		function selectCall()
		{
			validateProNo(document.getElementById("fastProId"));
		}
		
		
		function editCurRecord()
		{
			if(parent.hasFunctionRights( "CC012",  "UR_POST")!=true)
	        {
	        	$.ligerDialog.warn("该用户没有兑换权限,请确认!");
	        	return;
	        }
			if(pageState==3)
	   		{
	   			$.ligerDialog.warn("非新增单据,不可兑换!");
	   			return;
	   		}
			if(document.getElementById("changecardno").value=="")
			{
				$.ligerDialog.warn("请确认兑换会员卡号");
				return ;
			}
			if(postState==1)
			{
				$.ligerDialog.error("正在保存中,请不要连续保存!");
				return ;
			}
			btnDis2015.setEnabled();
			//$("#dis2015").display=false;
			/*var ybsToSendCount=0;
			var ziwuToSendCount=0;
			for (var rowid in commoninfodivdetial.records)
			{
				var row =commoninfodivdetial.records[rowid];	
				if(checkNull(row.changeproid)=="4610016")   //子午
				{
					ziwuToSendCount=ziwuToSendCount*1+checkNull(row.changeprocount);
				}	 
				else if( checkNull(row.changeproid)=="4610009" 
					  || checkNull(row.changeproid)=="4610010"
					  || checkNull(row.changeproid)=="4610011"
					  || checkNull(row.changeproid)=="4610012" )   //悦碧施
				{
					ybsToSendCount=ybsToSendCount*1+checkNull(row.changeprocount);
				}     		 
			}
			//悦碧施赠送 4610024  ,3610025
			if(ybsToSendCount*1>=12 && ybsToSendCount<24)
			{
				var row = commoninfodivdetial.getSelectedRow();
				commoninfodivdetial.addRow({ 
					changeproid: "4610024",
					changeproname: "健康美胸疏通(疗程)",
					procount: 0,
					changeprocount:1,
					changeprorate: 1,
					changeproamt: 0,
					changebyproaccountamt: "",
					changebyaccountamt: "",
					changepaymode: "",
					changebycashamt: "",
					nointernalcardno: "",
					changebydyqamt: "",
					firstsalerid: "",
					firstsaleamt: "",
					thirdsalerid: "",
					thirdsaleamt: "",
					changemark:"赠送不退"
					
					}, row, false);
			}	
			else if(ybsToSendCount*1>=24)
			{
				var row = commoninfodivdetial.getSelectedRow();
				commoninfodivdetial.addRow({ 
					changeproid: "3610025",
					changeproname: "头皮SPA(疗程)",
					procount: 0,
					changeprocount:1,
					changeprorate: 1,
					changeproamt: 0,
					changebyproaccountamt: "",
					changebyaccountamt: "",
					changepaymode: "",
					changebycashamt: "",
					nointernalcardno: "",
					changebydyqamt: "",
					firstsalerid: "",
					firstsaleamt: "",
					thirdsalerid: "",
					thirdsaleamt: "",
					changemark:"赠送不退"
					}, row, false);
				row = commoninfodivdetial.getSelectedRow();
				commoninfodivdetial.addRow({ 
					changeproid: "4610024",
					changeproname: "健康美胸疏通(疗程)",
					procount: 0,
					changeprocount:1,
					changeprorate: 1,
					changeproamt: 0,
					changebyproaccountamt: "",
					changebyaccountamt: "",
					changepaymode: "",
					changebycashamt: "",
					nointernalcardno: "",
					changebydyqamt: "",
					firstsalerid: "",
					firstsaleamt: "",
					thirdsalerid: "",
					thirdsaleamt: "",					
					changemark:"赠送不退"
					}, row, false);
			}
			//子午 4610024  ,3610025
			if(ziwuToSendCount*1>=12 && ziwuToSendCount<24)
			{
				var row = commoninfodivdetial.getSelectedRow();
				commoninfodivdetial.addRow({ 
					changeproid: "4610024",
					changeproname: "健康美胸疏通(疗程)",
					procount: 0,
					changeprocount:1,
					changeprorate: 1,
					changeproamt: 0,
					changebyproaccountamt: "",
					changebyaccountamt: "",
					changepaymode: "",
					changebycashamt: "",
					nointernalcardno: "",
					changebydyqamt: "",
					firstsalerid: "",
					firstsaleamt: "",
					thirdsalerid: "",
					thirdsaleamt: "",					
					changemark:"赠送不退"
					}, row, false);
			}	
			else if(ziwuToSendCount*1>=24)
			{
				var row = commoninfodivdetial.getSelectedRow();
				commoninfodivdetial.addRow({ 
					changeproid: "3610025",
					changeproname: "头皮SPA(疗程)",
					procount: 0,
					changeprocount:1,
					changeprorate: 1,
					changeproamt: 0,
					changebyproaccountamt: "",
					changebyaccountamt: "",
					changepaymode: "",
					changebycashamt: "",
					nointernalcardno: "",
					changebydyqamt: "",
					firstsalerid: "",
					firstsaleamt: "",
					thirdsalerid: "",
					thirdsaleamt: "",					
					changemark:"赠送不退"
					}, row, false);
				row = commoninfodivdetial.getSelectedRow();
				commoninfodivdetial.addRow({ 
					changeproid: "4610024",
					changeproname: "健康美胸疏通(疗程)",
					procount: 0,
					changeprocount:1,
					changeprorate: 1,
					changeproamt: 0,
					changebyproaccountamt: "",
					changebyaccountamt: "",
					changepaymode: "",
					changebycashamt: "",
					nointernalcardno: "",
					changebydyqamt: "",
					firstsalerid: "",
					firstsaleamt: "",
					thirdsalerid: "",
					thirdsaleamt: "",					
					changemark:"赠送不退"
					}, row, false);
			}
			*/
   			postState=1;
			var curjosnparam="";
			var needReplaceStr="";
			var strProJsonParam="";			
			var totalProamt=0;
			for (var rowid in commoninfodivdetialpro.records)
			{
				totalProamt=totalProamt*1+checkNull(commoninfodivdetialpro.records[rowid]['changeproamt'])*1    
				var row =commoninfodivdetialpro.records[rowid];
				if(checkNull(commoninfodivdetialpro.records[rowid]['changeproamt'])==""
				&& checkNull(commoninfodivdetialpro.records[rowid]['changeproamt'])*1==0)
					continue;
				curjosnparam=JSON.stringify(row);
				/*if(curjosnparam.indexOf("_id")>-1)
				{
					needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
					curjosnparam=curjosnparam.replace(needReplaceStr,"");
				}	*/    
				
				if(strProJsonParam!="")
					strProJsonParam=strProJsonParam+",";
				strProJsonParam= strProJsonParam+curjosnparam;    		 
			}	
			curjosnparam="";
			needReplaceStr="";
			var strJsonParam="";
			var totaldetialProamt=0;
			var totaldetialAccountamt=0;
			var totaldetialSendAccount=0;
			var detialneedpayamt=0;
			var detialProamt=0;
			var detialAccountamt=0;
			var detialSendAccountamt=0;
			var detialCashamt=0;
			var detialDiyongamt=0,detailFaceamt=0;
			var firstsaleamt=0;
			var thirdsaleamt=0;
			var thirdsalerid="";
			for (var rowid in commoninfodivdetial.records)
			{
				var row =commoninfodivdetial.records[rowid];
				if(checkNull(commoninfodivdetial.records[rowid]['changeproid'])=="")
					continue;
				curjosnparam=JSON.stringify(row);
				/*if(curjosnparam.indexOf("_id")>-1)
				{
						     	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
						      	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				}	*/            		   
				if(strJsonParam!="")
					strJsonParam=strJsonParam+",";
				strJsonParam= strJsonParam+curjosnparam;    
				detialneedpayamt=checkNull(commoninfodivdetial.records[rowid]['changeproamt'])*1 ;
				detialProamt=checkNull(commoninfodivdetial.records[rowid]['changebyproaccountamt'])*1 ;
				detialAccountamt=checkNull(commoninfodivdetial.records[rowid]['changebyaccountamt'])*1 ;
				detialSendAccountamt=checkNull(commoninfodivdetial.records[rowid]['changebysendaccountamt'])*1 ;
				detialCashamt=checkNull(commoninfodivdetial.records[rowid]['changebycashamt'])*1 ;
				detialDiyongamt=checkNull(commoninfodivdetial.records[rowid]['changebydyqamt'])*1 ;
				firstsaleamt=checkNull(commoninfodivdetial.records[rowid]['firstsaleamt'])*1 ;
				thirdsalerid=checkNull(commoninfodivdetial.records[rowid]['thirdsalerid']);
				thirdsaleamt=checkNull(commoninfodivdetial.records[rowid]['thirdsaleamt'])*1 ;
				thirdsaleamt=checkNull(commoninfodivdetial.records[rowid]['thirdsaleamt'])*1 ;
				detailFaceamt=checkNull(commoninfodivdetial.records[rowid]['changebyfaceamt'])*1;
				if(detialProamt*1<0 || detialCashamt*1<0 || detialAccountamt*1<0)
				{
					$.ligerDialog.error("抵扣金额不能小于0!");
					postState=0;
					return ;
				}
				var factKeepAmt=null;
				if(commoninfodivdetial.records[rowid]['changeprorate']*1==1){
					if(curRecord.changeproid==changeproid1){
						factKeepAmt=ForDight(detialSendAccountamt*1+detialProamt*1+detialAccountamt*1+detialCashamt*1+detialDiyongamt*1 ,1);
					}else{
						factKeepAmt=ForDight(detialSendAccountamt*1+detialProamt*1+detialAccountamt*1+detialCashamt*1+detialDiyongamt*1*document.getElementById("dSaleProRate").value*1 ,1);
					}
				}else{
					var changeprorate=null;
					if(curRecord.changeproid==changeproid1){
						changeprorate=0.9;
						factKeepAmt=ForDight(detialSendAccountamt*1+detialProamt*1+detialAccountamt*1+detialCashamt*1+detialDiyongamt*1*changeprorate ,1);
					}else{
						changeprorate=document.getElementById("dSaleProRate").value*1;
						factKeepAmt=ForDight(detialSendAccountamt*1+detialProamt*1+detialAccountamt*1+detialCashamt*1*changeprorate+detialDiyongamt*1*changeprorate ,1);
						factKeepAmt=ForDight(factKeepAmt+detailFaceamt*1*changeprorate, 1);
					}
				}
				/*if(ForDight(detialneedpayamt*1,1)!=factKeepAmt)
				{
					$.ligerDialog.error("疗程"+checkNull(commoninfodivdetial.records[rowid]['changeproid'])+"兑换的额度与支付额度不一致!");
					postState=0;
					return ;
				}*/
				totaldetialProamt=totaldetialProamt*1+checkNull(commoninfodivdetial.records[rowid]['changebyproaccountamt'])*1  ;
				totaldetialAccountamt=totaldetialAccountamt*1+checkNull(commoninfodivdetial.records[rowid]['changebyaccountamt'])*1 ;   		 
				totaldetialSendAccount=totaldetialSendAccount*1+checkNull(commoninfodivdetial.records[rowid]['changebysendaccountamt'])*1 ;   		 
				if(ForDight(detialAccountamt*1+detialCashamt*1+detialSendAccountamt*1,1)<ForDight(firstsaleamt*1,1 ))
				{
					$.ligerDialog.error("销售分享金额不能超过储值和现金兑换之和!");
					postState=0;
					return ;
				}
				
				if(ForDight(firstsaleamt*1,1)!=ForDight(thirdsaleamt*1,1 ) && checkNull(thirdsalerid,'')!="")
				{
					$.ligerDialog.error("烫染师分享与第一销售分享比率不一致,请确认!");
					postState=0;
					return ;
				}	
			}
			if(ForDight(totalProamt*1,1)!=ForDight(totaldetialProamt*1,1 ))
			{
					$.ligerDialog.error("会员卡抵用的疗程额度与实际支付额度不一致!");
					postState=0;
					return ;
			}
			if(ForDight(document.getElementById("curaccountkeepamt").value*1,1)<ForDight(totaldetialAccountamt*1,1 ))
			{
					$.ligerDialog.error("会员卡抵用的储值账户额度不能超过卡实际余额!");
					postState=0;
					return ;
			}	
			if(ForDight(document.getElementById("cursendaccountkeepamt").value*1,1)<ForDight(totaldetialSendAccount*1,1 ))
			{
					$.ligerDialog.error("会员卡抵用的赠送账户额度不能超过卡实际余额!");
					postState=0;
					return ;
			}	
			if($("#fastDyNo").val().indexOf("JF")==0)
			{
				 var sumamt=0;
	    		  var rows=commoninfodivdetial.getCheckedRows();
	    		  for (var rowid in commoninfodivdetial.records)
	  			  {
	    			  var row=commoninfodivdetial.records[rowid];
	    			  for(var i=0;i<parent.lsProjectinfo.length;i++)
	    			  {
	    				  if(parent.lsProjectinfo[i].id.prjno==row['changeproid'] && parent.lsProjectinfo[i].prjreporttype=='24')
	    				  {
	    					  sumamt+=row['changeproamt']*1;
	    				  }
	    			  }
	  			  }
	    		  if(sumamt*1<7000)
	    		  {
	    			  $.ligerDialog.error("兑换悦碧施项目金额要达到7000以上才能使用该抵用券！");
	    			  postState=0;
	    			  return;
	    		  }
			}
		
			
	            
			$.ligerDialog.confirm('确认保存当前兑换单?', function (result)
			{
			    if( result==true)
	           	{
			    	if(curRecord.changeproid==changeproid1){
				    	var params = {"strCurBillId":billIdCtype};
				    	var url = contextURL+"/cc012/upCtypeState.action"; 
				    	$.post(url, params, function(data){
				    		if(data.strMessage!=""){
				    			$.ligerDialog.warn(data.strMessage);
				    		}else{
				    			postData(strJsonParam, strProJsonParam);
				    		}
				    	}).error(function(e){$.ligerDialog.error("读取信息失败，请重试！");});
			    	}else{
			    		postData(strJsonParam, strProJsonParam);
			    	}
				}
				else
				{
					postState=0;
				}
			}); 
		}
		
		function postData(strJsonParam, strProJsonParam){
			var queryStringTmp=$('#cardChangeForm').serialize();//serialize('#detailForm');
			queryStringTmp=queryStringTmp.replace(/\+/g," ");
			var requestUrl ="cc012/post.action";
			var params=queryStringTmp;
			 if(strJsonParam!="")
			 {
			 	 params = params+"&strJsonParam=["+strJsonParam+"]";
			 }
			 if(strProJsonParam!="")
			 {
			 	 params = params+"&strProJsonParam=["+strProJsonParam+"]";
			 }
			 var responseMethod="editMessage";
			 var strCardNo = $("#randomno").val();
	 		 if(strCardNo!=""){//微信扫码弹出输入验证码
				 var url = contextURL+"/cc012/sendWechatPwd.action"; 
				 var _params = {"strCardNo":strCardNo,"_random": Math.random()};
				 $.ajax({
		             type:"POST", url:url, data:_params, dataType:"json",
		             success: function(data){
		            	 if(checkNull(data.strMessage)=="true"){
							var showWechatPwd = $.ligerDialog.open({ height:170, url: contextURL+'/CardControl/CC011/checkWechatPwd.jsp', 
				    			width: 360,showMax: false, showToggle: false, allowClose:false, showMin: false, isResize: false, title:"输入微信验证码",
			    				buttons:[{text:'确定', onclick:function(item, dialog){
			    					var _frame = $(dialog.frame.document);
			    			    	var pwd_code = $("#pwd_code", _frame).val();
			    			    	if(checkNull(pwd_code)==""){
			    						$.ligerDialog.warn("请输入验证码！");
			    						postState=0;
			    					}else{
			    						$("#pwd_code", _frame).attr("readonly",true);
			    						var url = contextURL+"/cc012/validateWechatPwd.action";
			    						var _params = {"strCardNo": pwd_code,"_random": Math.random()};
			    						$.ajax({
			   				             type:"POST", url:url, data:_params, dataType:"json",
			   				             	success: function(data){
			   				             		if(checkNull(data.strMessage)!=""){
			   				             			$.ligerDialog.warn(data.strMessage);
			   				             			$("#pwd_code", _frame).attr("readonly",false);
			   				             			postState=0;
			   				             		}else{
			   				             			showWechatPwd.close();
			   				             			showDialogmanager = $.ligerDialog.waitting('正在保存中,请稍候...');
													params =params+ "&strCardNo="+strCardNo;
													sendRequestForParams_p(requestUrl,responseMethod,params );
			   				             		} 
			   				             	},
			   				             	error: function(XMLHttpRequest, textStatus, errorThrown){
			   				             		$.ligerDialog.error("系统异常，请刷新或关闭页面重试！");
			   				             	}
			    						});
			    					}
			    				}}, {text:'取消', onclick:function(item, dialog){postState=0;dialog.close();}}]
							});
						}else{
							$.ligerDialog.error(data.strMessage);
							postState=0;
						}         
		             },
		             error: function(XMLHttpRequest, textStatus, errorThrown){
		            	$.ligerDialog.error("系统异常，请刷新或关闭页面重试！");
		             }
		         });
			 }else{
				showDialogmanager = $.ligerDialog.waitting('正在保存中,请稍候...');
				sendRequestForParams_p(requestUrl,responseMethod,params );
			 }
		}
		
		 function editMessage(request)
	     {
	    		
	        	try
				{
					postState=0;
					showDialogmanager.close();
		        	var responsetext = eval("(" + request.responseText + ")");
		        	var strMessage=responsetext.strMessage;
		        	if(checkNull(strMessage)=="")
		        	{	     
		        		 pageState=3;   		 
		        		 $.ligerDialog.success("保存成功!");
		        		 viewTicketReport();
		        		 readPage();
		        	}
		        	else
		        	{
		        		$.ligerDialog.warn(strMessage);
		        	}
	        	}
				catch(e)
				{
					alert(e.message);
				}
	      }
	      
	      function validatePackageNo(obj)
	      {
	      		if(obj.value=="")
	      		{
	      			return;
	      		}
	      		if(document.getElementById("changecardno").value=="")
				{
					$.ligerDialog.warn("请确认兑换的卡号!");
					obj.value="";
					return ;
				}
	      		var requestUrl ="cc012/validatePackageNo.action";
				var responseMethod="validatePackageNoMessage";	
				var params="strCurPackageId="+obj.value;	
				params =params+ "&strCurCompId="+strCurCompId;
				showDialogmanager = $.ligerDialog.waitting('正在加载套餐中,请稍候...');
				sendRequestForParams_p(requestUrl,responseMethod,params ); 
	      }
	      
	      function validatePackageNoMessage(request)
	     {
	    		
	        	try
				{
					showDialogmanager.close();
		        	var responsetext = eval("(" + request.responseText + ")");
		        	var strMessage=responsetext.strMessage;
		        	if(checkNull(strMessage)=="")
		        	{	   
		        		//commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total: 0});
						//commoninfodivdetial.loadData(true); 
						var changeRate=1;
						if(checkNull(responsetext.packageRateFlag)==0)
							changeRate=1*document.getElementById("dSaleProRate").value;
						if(responsetext.lsDmpackageinfo!=null && responsetext.lsDmpackageinfo.length>0)
						{
							for(var i=0;i<responsetext.lsDmpackageinfo.length;i++)
							{
								item_adddetial();
								commoninfodivdetial.updateRow(curRecord,{changeproid:responsetext.lsDmpackageinfo[i].bpackageprono,
																		changeproname: responsetext.lsDmpackageinfo[i].bpackageproname,
																		procount: 1,
																		changeprocount: ForDight(responsetext.lsDmpackageinfo[i].packageprocount,1),
																		standchangeprocount: ForDight(responsetext.lsDmpackageinfo[i].packageprocount,1),
																		changeprorate:changeRate*1,
																		changeproamt:ForDight(responsetext.lsDmpackageinfo[i].packageproamt*1*changeRate*1,1),
																		standchangeproamt:  responsetext.lsDmpackageinfo[i].packageproamt ,
																		changebyaccountamt :ForDight(responsetext.lsDmpackageinfo[i].packageproamt*1*changeRate*1,1)
																		});
							}
						}
						 
		        		
		        	}
		        	else
		        	{
		        		$.ligerDialog.warn(strMessage);
		        	}
	        	}
				catch(e)
				{
					alert(e.message);
				}
	      }
		
		function readCurCardInfo()
        {
        	var CardControl=parent.document.getElementById("CardCtrl");
			CardControl.Init(parent.commtype,parent.prot,parent.password1,parent.password2,parent.password3);
			var cardNo=CardControl.ReadCard();
			if(cardNo!="")
			{
				document.getElementById("changecardno").value=cardNo;
				validateExchangecardno(document.getElementById("changecardno"));
	    	}
        } 
        
         function loadProSeqno(strInput)
		 {
		 	
		 }
		 
		 function managerRate()
		 {
		 	//$.ligerDialog.prompt('输入经理工号','', function (yes,value) { if(yes) checkmanagerStaffNo(value); });
		 	$.ligerDialog.confirm("请将店长卡插入读卡器中!", function (result)
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
					var requestUrl ="cc012/checkmanagerPass.action";
					var responseMethod="checkmanagerPassMessage";	
					var params="mangerCardNo="+cardNo;	
					sendRequestForParams_p(requestUrl,responseMethod,params ); 
				}
			});
		 }
		
		  function checkmanagerStaffNo(value)
		  {
		  	var staffNo=value;
		  	$.ligerDialog.prompt('输入经理签单密码','', function (yes,value) { if(yes) checkmanagerPass(staffNo,value); });
		
		  }
		  
		  function checkmanagerPass(staffNo,value)
		  {
		  		var requestUrl ="cc012/checkmanagerPass.action";
				var responseMethod="checkmanagerPassMessage";	
				var params="strCurManagerNo="+staffNo;	
				params =params+ "&strCurManagerPass="+value;
				params =params+ "&strCurCompId="+strCurCompId;	
				sendRequestForParams_p(requestUrl,responseMethod,params ); 
				
		  }
		  
		 function checkmanagerPassMessage(request)
	     {
	    		
	        	try
				{
					var responsetext = eval("(" + request.responseText + ")");
		        	var strMessage=responsetext.strMessage;
		        	if(checkNull(strMessage)=="")
		        	{	     
		        		document.getElementById("SP028Rate").value=ForDight(checkNull(responsetext.SP028Rate)*1/100,2);
		        		$.ligerDialog.prompt('输入经理折扣(折扣上限)'+document.getElementById("SP028Rate").value,'', function (yes,value) { if(yes) checkmanagerRate(value); });
		        	}
		        	else
		        	{
		        		$.ligerDialog.warn(strMessage);
		        	}
	        	}
				catch(e)
				{
					alert(e.message);
				}
	      }
	      
	      function checkmanagerRate(value)
	      {
	      	 if(value*1<document.getElementById("SP028Rate").value*1)
	      	 {
	      	 	$.ligerDialog.prompt('输入折扣不能低于上限(折扣上限'+document.getElementById("SP028Rate").value+')','', function (yes,value) { if(yes) checkmanagerRate(value); });
	      	 }
	      	 else
	      	 {
	      	 	document.getElementById("dSaleProRate").value=value*1;
	      	 }
	      }
	      
	      
	      
	      function manager38Rate()
		 {
		 		if(useManagerRate==1)
		 		{
		 			$.ligerDialog.error("该单据已享受过38节折扣活动,请确认！");
				  	return ;
		 		}
		 		var intMonth=document.getElementById("changedate").value.substring(5,7)*1
				var intDay=document.getElementById("changedate").value.substring(8,10)*1
				if(intMonth*1!=3 ||  18<intDay*1)
				{
					$.ligerDialog.error("本次活动日期在 3.01-3.18之间,请确认！");
				  	return ;
				}
		 	$.ligerDialog.prompt('输入经理工号','', function (yes,value) { if(yes) checkmanager38StaffNo(value); });
		 }
		
		  function checkmanager38StaffNo(value)
		  {
		  	var staffNo=value;
		  	$.ligerDialog.prompt('输入经理签单密码','', function (yes,value) { if(yes) checkmanager38Pass(staffNo,value); });
		
		  }
		  
		  function checkmanager38Pass(staffNo,value)
		  {
		  		var requestUrl ="cc012/checkmanagerPass.action";
				var responseMethod="checkmanager38PassMessage";	
				var params="strCurManagerNo="+staffNo;	
				params =params+ "&strCurManagerPass="+value;
				params =params+ "&strCurCompId="+strCurCompId;	
				sendRequestForParams_p(requestUrl,responseMethod,params ); 
				
		  }
		  
		 function checkmanager38PassMessage(request)
	     {
	    		
	        	try
				{
					var responsetext = eval("(" + request.responseText + ")");
		        	var strMessage=responsetext.strMessage;
		        	if(checkNull(strMessage)=="")
		        	{	     
		        		document.getElementById("dSaleProRate").value=document.getElementById("dSaleProRate").value*1*0.95;
		        		useManagerRate=1;
		        	}
		        	else
		        	{
		        		$.ligerDialog.warn(strMessage);
		        	}
	        	}
				catch(e)
				{
					alert(e.message);
				}
	      }
	      
	      
	    function viewTicketReport()
		{
		  	var requestUrl ="cc012/viewTicketReport.action"; 
			var responseMethod="viewTicketReportMessage";	
			var params =  "strCurCompId=" + document.getElementById("changecompid").value + "&strCurBillId=" + document.getElementById("changebillid").value;		
			params =params+ "&strCardNo="+document.getElementById("changecardno").value;	
			sendRequestForParams_p(requestUrl,responseMethod,params );
		}
		
		function viewTicketReportMessage(request)
		{
		  try
		  {
		    var responsetext = eval("(" + request.responseText + ")");
		  	Stand_CheckPrintControl();//检查是否有打印控件
			Stand_InitPrint("疗程兑换模块_小票打印作业");
			Stand_SetPrintStyle("FontSize",11);
			Stand_SetPrintStyle("Alignment",2);
			Stand_SetPrintStyle("HOrient",2);
			Stand_SetPrintStyle("Bold",1);
			Stand_AddPrintContent(StandPRINT_CONTENT_TYPE_TEXT,31,15,351,25,"阿玛尼护肤造型"+"("+checkNull(parent.document.getElementById("strCompName").value)+")");
			Stand_SetPrintStyle("FontSize",9);
			Stand_AddPrintContent(StandPRINT_CONTENT_TYPE_TEXT,55,15,351,25,"一切为你 想你所想");
			Stand_SetPrintStyle("FontSize",11);
			Stand_SetPrintStyle("Bold",0);
		   document.getElementById("currdate_print").innerHTML=responsetext.printDate;
		   document.getElementById("memberCardId_print").innerHTML=responsetext.strCardNo;
		   if(checkNull(responsetext.curCardinfo)!=null)
		   {
		   		document.getElementById("keepAmount_print").innerHTML=maskAmt(checkNull(responsetext.curCardinfo.account2Amt),2);
		   }
		   document.getElementById("tradebillId_print").innerHTML=responsetext.strCurBillId;
		   document.getElementById("clerkName_print").innerHTML=responsetext.cashMemberName ;
		   document.getElementById("telephone_print").innerHTML=responsetext.companTel;
		   document.getElementById("printTime_print").innerHTML=responsetext.printTime;   
		   document.getElementById("address_print").innerHTML =responsetext.companAddr;
	  	   clearPreviousResult_report();
	  	   if(responsetext.lsDproexchangeinfo != null)
		   {
					for(var i=0;i<responsetext.lsDproexchangeinfo.length;i++)
					{
						addRowReport(checkNull(responsetext.lsDproexchangeinfo[i].changeproname),
						maskAmt(responsetext.lsDproexchangeinfo[i].changeprocount,1),
						maskAmt(checkNull(responsetext.lsDproexchangeinfo[i].changebyproaccountamt)*1
						+checkNull(responsetext.lsDproexchangeinfo[i].changebyaccountamt)*1
						+checkNull(responsetext.lsDproexchangeinfo[i].changebycashamt)*1,1));
					}
		   }
			
		    var printContent = document.getElementById("printContent").innerHTML;
		    Stand_AddPrintContent(StandPRINT_CONTENT_TYPE_HTML,70,0,260,800,printContent);
		    Stand_Print();
		  }
		  catch(e){alert(e.message);}
		}
		
		
		function addRowReport(name,count,amt)
	     {
		    var row = document.createElement("tr");
		    var cell = createCellWithText(checkNull(name)+":");
		    cell.style.fontSize="12px";
		    row.appendChild(cell);
		   
		    cell = createCellWithText(checkNull(amt));
		    cell.style.fontSize="12px";
		    row.appendChild(cell);
		    
		     cell = createCellWithText(checkNull(count));
		    cell.style.fontSize="12px";
		    row.appendChild(cell);
		    
		    document.getElementById("changeDetail").appendChild(row);
	     }
	     
	    function clearPreviousResult_report()
	    {
		    var tblPrjs = document.getElementById("changeDetail");
		    while(tblPrjs.childNodes.length>0)
		     {
			    tblPrjs.removeChild(tblPrjs.childNodes[0]);
		     }
	    }
	    
	    function handMoreLongHair()
	    {
	    	alert(curRecord.changeproid);
	    	var hasMoreLongHairPrj=0;
	    	for(var i=0;i<parent.lsProjectinfo.length;i++)
			{
				if(parent.lsProjectinfo[i].id.prjno==checkNull(curRecord.csitemno) )
				{
					if(checkNull(parent.lsProjectinfo[i].morelongflag)==1 ) //美发
					{
								hasMoreLongHairPrj=1;
								break;
					}
				}
			}
			if(hasMoreLongHairPrj==0)
			{
	    		$.ligerDialog.error("选择的项目没有超长项提供！");
				return ;
	    	}
	    	else
	    	{
		    }
		}
	    //检查微信扫码是否存在
		function checkRandomno(){
	    	var randomno=$("#randomno").val();
	    	$("#changecardno").val(randomno);
	    	validateExchangecardno(document.getElementById("changecardno"));
	    	/*if(checkNull(randomno)==""){
	    		return;
	    	}
	    	var params = {"strRandomno":randomno}
	    	var url = contextURL+"/cc011/checkWXRandomno.action"; 
	    	$.post(url, params, function(data){
	    		if(data.strMessage!=""){
	    			$.ligerDialog.warn(data.strMessage);
	    			$("#randomno").val("");
	    			$("#changecardno").val("");
	    		}else{
	    			$("#changecardno").val(data.wxbandcard.cardno);
	    		}
	    		validateExchangecardno(document.getElementById("changecardno"));
	    	}).error(function(e){$.ligerDialog.error("读取信息失败，请重试！");});*/
	    }
		//8月5号活动（当天消费C类美容师体验，则打九折）
		var ctypelist=null,billIdCtype=null;
		var showDialogCtype = null;
		function manager85Rate(){
			if(curRecord.changeproid==changeproid1){
				showDialogCtype = $.ligerDialog.open({ height:30, url: contextURL+'/CardControl/CC012/openCtype.jsp', 
			    	width: 460,showMax: false, showToggle: false, showMin: false, isResize: false, title: 'C类美容师体验活动', buttons: [ { text: '确定', onclick: function (item, dialog) { updateCtype(dialog); } }, { text: '取消', onclick: function (item, dialog) {dialog.close(); }}]});
			}else{
				$.ligerDialog.warn("此疗程无法使用该折扣！");
			}
		}
		//更新消费主档中的ctypestate标识
		function updateCtype(){
			if(checkNull(curRecord.ctypestate)=="1"){
				$.ligerDialog.warn("此疗程已经9折优惠了，不能重复操作！");
				return;
			}
			billIdCtype=ctypelist.val();
			if(checkNull(billIdCtype)=="" || checkNull(billIdCtype)=="-1"){
				$.ligerDialog.warn("必须选择当前会员的今天消费记录，才能操作疗程折扣！");
				return;
			}
			showDialogCtype.close();
			var changeprorate=0.9;
			var changeproamt=ForDight(curRecord.standchangeproamt*1*changeprorate,1);
			var factKeepAmt=ForDight((checkNull(changeproamt)*1-checkNull(curRecord.changebyproaccountamt)*1-checkNull(curRecord.changebysendaccountamt)*1)/changeprorate*1-checkNull(curRecord.changebydyqamt)*1,1);
			factKeepAmt=ForDight((factKeepAmt*1*changeprorate*1-curRecord.changebycashamt*1),1);//标准额*折扣-金额
			commoninfodivdetial.updateRow(curRecord,{changeprorate:changeprorate,changeproamt:changeproamt,changebyaccountamt:factKeepAmt,ctypestate:1});
		}
		//美容积分抵扣
		function validateFastFaceDkAmt(obj){
			if(checkNull(curRecord.changeproid).substring(0,1)!="4"){
				$("#fastFaceDkAmt").val("");
				$.ligerDialog.warn("美容积分只能抵扣美容疗程！");
				return;
			}
			if(obj.value*1<0){
				$("#fastFaceDkAmt").val("");
				return;
			}
			if(obj.value*1>$("#curfaceaccountamt").val()*1){
				$("#fastFaceDkAmt").val("");
				$.ligerDialog.error("抵扣美容积分不能超过现有美容积分余额!");
				return;
			}
			var factKeepAmt=0;
			var rateToRate=$("#rateToRate").val()*1;
			if(curRecord.changeprorate*1==1){
				factKeepAmt=ForDight(checkNull(curRecord.changeproamt)*1-checkNull(curRecord.changebysendaccountamt)*1-checkNull(curRecord.changebyproaccountamt)*1-checkNull(curRecord.changebydyqamt)*1-checkNull(curRecord.changebycashamt)*1,1);
				factKeepAmt=ForDight(factKeepAmt-obj.value*1, 1);
			}else{
				var changeprorate=null;
				if(curRecord.changeproid==changeproid1){
					changeprorate=0.9;
					factKeepAmt=ForDight(((checkNull(curRecord.standchangeproamt)*1*checkNull(curRecord.procount)*1)-checkNull(curRecord.changebydyqamt)*1-obj.value*1-checkNull(curRecord.changebycashamt)*1)*changeprorate*rateToRate-checkNull(curRecord.changebysendaccountamt)*1-checkNull(curRecord.changebyproaccountamt)*1,1);
					//factKeepAmt=ForDight(factKeepAmt-obj.value*1, 1);
				}else{
					changeprorate=document.getElementById("dSaleProRate").value*1;
					//factKeepAmt=ForDight(checkNull(curRecord.standchangeproamt)*1-checkNull(curRecord.changebydyqamt)*1-obj.value*1-checkNull(curRecord.changebysendaccountamt)*1-checkNull(curRecord.changebyproaccountamt)*1)*changeprorate-;
					factKeepAmt=ForDight(((checkNull(curRecord.standchangeproamt)*1*checkNull(curRecord.procount)*1)-checkNull(curRecord.changebydyqamt)*1-obj.value*1-checkNull(curRecord.changebycashamt)*1)*changeprorate*rateToRate-checkNull(curRecord.changebysendaccountamt)*1-checkNull(curRecord.changebyproaccountamt)*1,1);
					//factKeepAmt=ForDight(factKeepAmt-obj.value*1*changeprorate, 1);
				}
			}
			commoninfodivdetial.updateRow(curRecord,{changebyfaceamt :ForDight(obj.value*1,1),changebyaccountamt :ForDight(factKeepAmt,1)});
		}
		
		
		function dis2015()
		{
			var date=$("#changedate").val().replace("-","").replace("-","");
			if(date*1<20151204 || date*1>=20160115)
			{
				return;
			}
			btnDis2015.setDisabled();
			var sumNum=0;
			var lvl=1;
			for (var rowid in commoninfodivdetial.records)
			{
				var row =commoninfodivdetial.records[rowid];
				sumNum=row["changeprocount"]*1/12;
			}
			if(sumNum>=4)
			{
				lvl=0.85;
			}
			else if(sumNum>=3)
			{
				lvl=0.9;
			}
			else if(sumNum>=2)
			{
				lvl=0.95;
			}
			for (var rowid in commoninfodivdetial.records)
			{
				var row =commoninfodivdetial.records[rowid];
				commoninfodivdetial.updateRow(rowid,{changeproamt:ForDight(row["changeproamt"]*lvl,1)});
			}
		}
