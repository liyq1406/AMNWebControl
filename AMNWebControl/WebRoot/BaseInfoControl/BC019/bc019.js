    var bc019layout=null;
   	var commoninfodivTradeDate=null;
   	var commoninfodivRecordDate=null;
   	var commoninfodivPrjDate=null;
   	var commoninfodivGoodsDate=null;
   	var commoninfodivGoodsDate=null;
   	var commoninfodivEntryDate=null;
   	var commoninfodivrandcardDate=null;
	var showDialogmanager=null;
	var strEntryCompId="";
	var strEntryBillId="";
	var ibillstate=0;
	var BC019Tab=null;
	var curRecord=null;
	var showDialogmanager=null;
	var curDzqRecode=null;
	var curPrjtyRecode=null;
	var curGoodtyRecode=null;
	var sendchooseData = [{ choose: 1, text: '是' }, { choose: 0, text: '否'}];
	$(function ()
   	{
	   try
	   {
	   		  //布局
            bc019layout= $("#bc019layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
            $("#BC019Tab").ligerTab();
            BC019Tab = $("#BC019Tab").ligerGetTabManager();
           	var height = $(".l-layout-center").height();
           	var centerWidth = bc019layout.centerWidth
       	   	$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'100' });
       	   //--------销售条码卡
        
            
             commoninfodivRecordDate=$("#commoninfodivRecordDate").ligerGrid({
                columns: [
                { display: '门店', 			name: 'cardvesting', 				width:60,align: 	'left'} ,
	            { display: '名称', 			name: 'cardvestingname', 			width:90,align: 	'left'}, 
	            { display: '活动券号', 		name: 'cardno', 					width:140,align: 	'left'} ,
	            { display: '登记日期', 		name: 'createdate', 				width:80,align: 	'left'},
	            { display: '有效日期', 		name: 'validatedate', 				width:90,align: 	'left'},
	            { display: '体验项目', 		name: 'expericeitemname', 			width:140,align: 	'left'} ,
	            { display: '体验门店', 		name: 'expericecompno', 			width:60,align: 	'left'},
	            { display: '体验单据', 		name: 'expericebillno', 			width:120,align: 	'left'},
	            { display: '抵用卡类', 		name: 'salecardtypename', 			width:100,align: 	'left'},
	            { display: '抵用卡金额', 		name: 'salecarddeductamt', 			width:80,align: 	'left'},
	            { display: '抵用门店', 		name: 'salecardcompno', 			width:80,align: 	'left'},
	            { display: '抵用单号', 		name: 'salecardbillno', 			width:120,align: 	'left'},
	            { display: '电子券', 		name: 'sendquanflag', 					width:80,align: 	'left', 
	                editor: { type: 'select', data: sendchooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.sendquanflag == 1) return '赠送';
	                      return '不赠送';
	                }
	            },
	            { display: '电子券号', 		name: 'sendquanno', 				width:100,align: 	'left'}
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '100%',
                height:'92%',
                enabledEdit: false,  checkbox: false ,rownumbers: false,usePager: false
            });
            
            commoninfodivEntryDate=$("#commoninfodivEntryDate").ligerGrid({
                columns: [
                { display: '门店', 			name: 'cardvesting', 				width:60,align: 	'left'} ,
	            { display: '门店名称', 		name: 'cardvestingname', 			width:120,align: 	'left'}, 
	            { display: '活动券号', 		name: 'cardno', 					width:120,align: 	'left'},
	            { display: '有效日期', 		name: 'validatedate', 				width:90,align: 	'left'},
	            { display: '抵充卡类', 		name: 'salecardtypename', 				width:100,align: 	'left'},
	            { display: '抵充卡金', 		name: 'salecarddeductamt', 			width:100,align: 	'left'},
	            { display: '电子券', 		name: 'sendquanflag', 					width:80,align: 	'left', 
	                editor: { type: 'select', data: sendchooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.sendquanflag == 1) return '赠送';
	                      return '不赠送';
	                }
	            },
	            { display: '电子券号', 		name: 'sendquanno', 				width:100,align: 	'left'}
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '100%',
                height:'80%',
                enabledEdit: false,  checkbox: false ,rownumbers: false,usePager: false
            });
            
            var  menuprjty = $.ligerMenu({ width: 120, items:
	            [
	            	{ text: '删除当前记录', click: deletePrjtyRecord, icon: 'delete' , img: contextURL+'/common/ligerui/ligerUI/skins/icons/candle.gif'}
	            ]
	        });
            
            commoninfodivPrjDate=$("#commoninfodivPrjDate").ligerGrid({
                columns: [
                { display: '体验项目', 		name: 'expericeitemno', 			width:70,align: 	'left'} ,
	            { display: '项目名称', 		name: 'expericeitemname', 			width:120,align: 	'left'}, 
	            { display: '体验价', 			name: 'expericeitemprice', 			width:50,align: 	'left'}
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '280',
                height:'135',
                enabledEdit: false,  checkbox: false ,rownumbers: false,usePager: false,
			    onContextmenu : function (parm,e)
			    {
			          curPrjtyRecode=parm.data;
			          menuprjty.show({ top: e.pageY, left: e.pageX });
			          return false;
			    } 
            });
            
            var  menudzq = $.ligerMenu({ width: 120, items:
	            [
	            	{ text: '删除当前记录', click: deletePXRecord, icon: 'delete' , img: contextURL+'/common/ligerui/ligerUI/skins/icons/candle.gif'}
	            ]
	        }); 
            commoninfodivrandcardDate=$("#commoninfodivrandcardDate").ligerGrid({
                columns: [
                { display: '体验项目', 		name: 'expericeitemno', 			width:100,align: 	'left'} ,
	            { display: '项目名称', 		name: 'expericeitemname', 			width:160,align: 	'left'} ,
	            { display: '次数', 			name: 'expericeitemcount', 			width:70,align: 	'right'},
	            { display: '金额', 			name: 'expericeitemprice', 			width:70,align: 	'right'} 
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '98%',
                height:'100',
                enabledEdit: false,  checkbox: false ,rownumbers: false,usePager: false ,
			    onContextmenu : function (parm,e)
			    {
			          curDzqRecode=parm.data;
			          menudzq.show({ top: e.pageY, left: e.pageX });
			          return false;
			    } 
            });
            $("#onebartoolbar").ligerToolBar({items: [
      		    { text: '项目:&nbsp;<input type="text" id="strPrjNo" name="strPrjNo" style="width:80"  onfocus="itemsearchbegin(this,4);"/>&nbsp;体验价:&nbsp;<input type="text" id="dSharePrice" name="dSharePrice" style="width:40" onchange="validatePrice(this);addExpericePrjInfo()"/> '},
          		{ text: '',click: addExpericePrjInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/down.gif'  }
            ]
            });	
            
             $("#twobartoolbar").ligerToolBar({items: [
      		    { text: '类型:&nbsp;<select type="text" id="strGoodsType" name="strGoodsType" style="width:80" ></select>&nbsp;折扣:&nbsp;<input type="text" id="dShareRate" name="dShareRate" style="width:40" onchange="validatePrice(this);addExpericeGoodsInfo();"/> '},
          		{ text: '', click: addExpericeGoodsInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/down.gif'  }
            ]
            });	
            
            $("#threebartoolbar").ligerToolBar({items: [
      		    { text: '项目:&nbsp;<input type="text" id="strRandPrjNo" name="strRandPrjNo"  style="width:80;background:#EDF1F8;"  readonly="true" onfocus="itemsearchbegin(this,4);"/>&nbsp;<input type="text" id="strRandPrjName" name="strRandPrjName" style="width:140;background:#EDF1F8;"  readonly="true" /> &nbsp;次数:&nbsp;<input type="text" id="dRandShareCount" name="dRandShareCount" style="width:40" onchange="validatePrice(this);"/>&nbsp;金额:&nbsp;<input type="text" id="dRandSharePrice" name="dRandSharePrice" style="width:40" onchange="validatePrice(this);addRandPrjInfo();"/> '},
          		{ text: '',click: addRandPrjInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif'  }
            ]
            });	
            
          	var  menugoodty = $.ligerMenu({ width: 120, items:
	            [
	            	{ text: '删除当前记录', click: deleteGoodstyRecord, icon: 'delete' , img: contextURL+'/common/ligerui/ligerUI/skins/icons/candle.gif'}
	            ]
	        });
            commoninfodivGoodsDate=$("#commoninfodivGoodsDate").ligerGrid({
                columns: [
                { display: '产品类型', 		name: 'salegoodstype', 			width:70,align: 	'left'} ,
	            { display: '类型名称', 		name: 'salegoodstypename', 		width:120,align: 	'left'}, 
	            { display: '折扣', 			name: 'salegoodsrate', 			width:50,align: 	'left'}
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '280',
                height:'80',
                enabledEdit: false,  checkbox: false ,rownumbers: false,usePager: false,
			    onContextmenu : function (parm,e)
			    {
			          curGoodtyRecode=parm.data;
			          menugoodty.show({ top: e.pageY, left: e.pageX });
			          return false;
			    } 
            });
            
            document.getElementById("showTable").width=centerWidth;
            $("#searchButton").ligerButton(
	         {
	             text: '查询报表', width: 120,
		         click: function ()
		         {
		             searchDataSet();
		         }
	         });
	         
	           $("#postTargetButton").ligerButton(
	         {
	             text: '设置门店活动券', width: 120,
		         click: function ()
		         {
		             postTargetInfo();
		         }
	         }); 
	        
			addrtrade();
			addentrytrade();
			addrandprjtrade();
			addprjtrade();
			addgoodstrade();
			
			  $("#strSearchDate").ligerDateEditor({labelAlign: 'right',width:'100' });
	         $("#strValidate").ligerDateEditor({labelAlign: 'right',width:'100' });
	        addOption("","",document.getElementById("strGoodsType"));
	        var goodstypes=parent.gainCommonInfoByCode("WPTJ",0);
		    for(var i=0;i<goodstypes.length;i++)
			{
				addOption(goodstypes[i].bparentcodekey,goodstypes[i].parentcodevalue,document.getElementById("strGoodsType"));
			}	
			if(parent.document.getElementById("strCompId").value=="001")
			{
				document.getElementById("fd").style.display="block";
				initPageDate();
			}
            $("#pageloading").hide(); 
   		}catch(e){alert(e.message);}
    });
    
    
    function initPageDate()
    {
    	var requestUrl ="bc019/loadInitDate.action";
	    var params="";
		var responseMethod="loadInitDateMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	
    }
   
   	function loadInitDateMessage(request)
	{
		 	try
			{
		       	var responsetext = eval("(" + request.responseText + ")");
		       if(responsetext.lsCardtypeinfos!=null && responsetext.lsCardtypeinfos.length>0)
		   		{
		   			clearOption("strSaleCardType");
		   			addOption("","请选择",document.getElementById("strSaleCardType"))
					for(var i=0;i<responsetext.lsCardtypeinfos.length;i++)
					{
						addOption(responsetext.lsCardtypeinfos[i].id.cardtypeno,responsetext.lsCardtypeinfos[i].id.cardtypeno+"-"+responsetext.lsCardtypeinfos[i].cardtypename,document.getElementById("strSaleCardType"))
					}
		   		}
	        }
			catch(e)
			{
				alert(e.message);
			}
	  }
	  
    function  addrtrade()
    {
        	var row = commoninfodivRecordDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivRecordDate.addRow({
				            }, row, false);
     }
     
    function  addentrytrade()
     {
        	var row = commoninfodivEntryDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivEntryDate.addRow({
				            }, row, false);
     }
	function  addprjtrade()
    {
        	var row = commoninfodivPrjDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivPrjDate.addRow({ 
				                expericeitemno: "",
				                expericeitemname: "",
				                expericeitemprice: 0
				            }, row, false);
     }
     
     function  addrandprjtrade()
     {
        	var row = commoninfodivrandcardDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivrandcardDate.addRow({ 
				                expericeitemno: "",
				                expericeitemname: "",
				                expericeitemcount:0,
				                expericeitemprice: 0
				            }, row, false);
     }
     
     
     function  addgoodstrade()
     {
        	var row = commoninfodivGoodsDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivGoodsDate.addRow({ 
				                salegoodstype: "",
				                salegoodstypename: "",
				                salegoodsrate: 0
				            }, row, false);
      }
     
	  function validateShandcompid(obj)
	  {
	   		if(obj.value=="")
	   		{
	   			document.getElementById("strEntryCompName").value="";
	   		}
	   		else
	   		{
	   			var requestUrl ="bc019/validateShandcompid.action";
	       	 	var params="strCurCompId="+obj.value;
				var responseMethod="validateShandcompidMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params ); 	 
	   		}
	  }
	   	
	  function validateShandcompidMessage(request)
	  {
		 	try
			{
		       	var responsetext = eval("(" + request.responseText + ")");
		       	var strMessage=responsetext.strMessage;
		       	if(checkNull(strMessage)=="")
		       	{	       		 
		       		document.getElementById("strEntryCompName").value=responsetext.strCurCompName;
		       	}
		       	else
		       	{
		       		
		       		document.getElementById("strEntryCompId").value="";
		       		document.getElementById("strEntryCompName").value="";
		       		$.ligerDialog.error(strMessage);
		       	}
	        }
			catch(e)
			{
				alert(e.message);
			}
	  }
	  
	  function validateProject(obj)
	  {
	  		if(obj.value=="")
	  		{
	  			document.getElementById("strProjectName").value="";
	  		}
	  		else
	  		{
	  			var checkflag=0;
	  			if(parent.lsProjectinfo!=null && parent.lsProjectinfo.length>0)
				{
						for(var i=0;i<parent.lsProjectinfo.length;i++)
						{
							if(parent.lsProjectinfo[i].id.prjno==obj.value )
							{
								document.getElementById("strProjectName").value=parent.lsProjectinfo[i].prjname;
								checkflag=1;
							}
						}
				}
				
				if(checkflag==0)
				{
					$.ligerDialog.error("该项目编号不存在");
					document.getElementById("strProjectNo").value="";
					document.getElementById("strProjectName").value="";
				}	
	  		}
	  }
	   	
	   	function hotKeyOfSelf(key)
		{
		   if(key==13)//回车
			{
				var fieldName = document.activeElement.name;
				var fieldId = document.activeElement.id ;
				if(fieldId=="strPrjNo")
				{
					validateProject(document.activeElement);
				}
				window.event.keyCode=9; //tab
				window.event.returnValue=true;
			}
			else if( key == 83 &&  event.altKey)
			{
				editCurRecord();
			}
		}
		
		function selectCall()
		{
			validateProNo(document.getElementById("strPrjNo"));
		}
		
		
		function validatePrjQuan(obj)
		{
			if(obj.value=="")
			{
				document.getElementById("strQuanValidate").value="";
				return;
			}
			var requestUrl ="bc019/validateDiyongcardno.action"; 
			var responseMethod="validateDiyongcardnoMessage";	
			var params="strDiYongCardNo="+obj.value;			
			sendRequestForParams_p(requestUrl,responseMethod,params );
		}
		
		function validateDiyongcardnoMessage(request)
      	{
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		document.getElementById("strQuanValidate").value=checkNull(responsetext.strDiYongValidate);
	        	}
	        	else
	        	{
	        		document.getElementById("strQuanValidate").value="";
	        		document.getElementById("strProQuanNo").value="";
	        		$.ligerDialog.error(strMessage);
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
      	}
      	
      	function addExpericePrjInfo()
      	{
   
      		if(document.getElementById("strPrjNo").value=="")
      		{
      			$.ligerDialog.error("请确认体验项目");
				return;
      		}
      		if(document.getElementById("dSharePrice").value*1==0)
      		{
      			$.ligerDialog.error("请确认项目体验价格");
				return;
      		}
      		
      		for (var rowid in commoninfodivPrjDate.records)
			{
				var row =commoninfodivPrjDate.records[rowid];
				if(checkNull(row.expericeitemno)==document.getElementById("strPrjNo").value)
				{
					$.ligerDialog.error("该项目已经在体验项目列表中存在,请重新输入");
					return;
				}		
			}        
					
      		var checkPrjFlag=0;
      		if(parent.lsProjectinfo!=null && parent.lsProjectinfo.length>0)
			{
				for(var i=0;i<parent.lsProjectinfo.length;i++)
				{
					if(parent.lsProjectinfo[i].id.prjno==document.getElementById("strPrjNo").value )
					{
				    	checkPrjFlag=1;	   
				    	break;
					}
				}
			 }
			 if(checkPrjFlag==0)
			 {
			 	$.ligerDialog.error("输入的项目编号不存在,请确认");
				return;
			 }	
			 else
			 {
			 	var gridlen=commoninfodivPrjDate.rows.length*1;
				if(gridlen==0)
				{
					addprjtrade();
					gridlen=gridlen+1;
				} 
				if(checkNull(commoninfodivPrjDate.getRow(0).expericeitemno)!="")
				{
					addprjtrade();
					gridlen=gridlen+1;
				}
				var curCDetialRecord=commoninfodivPrjDate.getRow(gridlen-1);
				commoninfodivPrjDate.updateRow(curCDetialRecord,{    expericeitemno: document.getElementById("strPrjNo").value
															  		,expericeitemname : checkNull(parent.lsProjectinfo[i].prjname)
															  		,expericeitemprice  : document.getElementById("dSharePrice").value*1
															   }); 
										
			 	document.getElementById("strPrjNo").value="";
			 	document.getElementById("dSharePrice").value="";
			 	document.getElementById("strPrjNo").select();
			 	document.getElementById("strPrjNo").focus();
			 }
      	}
      	
      	
      	function addRandPrjInfo()
      	{
      		   //strRandPrjName     dRandShareCount
      		if(document.getElementById("strRandPrjNo").value=="")
      		{
      			$.ligerDialog.error("请确认体验项目");
				return;
      		}
      		if(document.getElementById("dRandSharePrice").value*1==0)
      		{
      			$.ligerDialog.error("请确认项目体验价格");
				return;
      		}
      		if(document.getElementById("dRandShareCount").value*1==0)
      		{
      			$.ligerDialog.error("请确认项目体验次数");
				return;
      		}
      		for (var rowid in commoninfodivrandcardDate.records)
			{
				var row =commoninfodivrandcardDate.records[rowid];
				if(checkNull(row.expericeitemno)==document.getElementById("strRandPrjNo").value)
				{
					$.ligerDialog.error("该项目已经在体验项目列表中存在,请重新输入");
					return;
				}		
			}        
					
			var gridlen=commoninfodivrandcardDate.rows.length*1;
			if(gridlen==0)
			{
				addrandprjtrade();
				gridlen=gridlen+1;
			} 
			if(checkNull(commoninfodivrandcardDate.getRow(0).expericeitemno)!="")
			{
				addrandprjtrade();
				gridlen=gridlen+1;
			}
			var curCDetialRecord=commoninfodivrandcardDate.getRow(gridlen-1);
			commoninfodivrandcardDate.updateRow(curCDetialRecord,{    expericeitemno: document.getElementById("strRandPrjNo").value
															  		,expericeitemname : document.getElementById("strRandPrjName").value 
															  		,expericeitemprice  : document.getElementById("dRandSharePrice").value*1
															  		,expericeitemcount  : document.getElementById("dRandShareCount").value*1
															   }); 
										
			 document.getElementById("strRandPrjNo").value="";
			 document.getElementById("strRandPrjName").value="";
			 document.getElementById("dRandSharePrice").value=0;
			 document.getElementById("dRandShareCount").value=0;
			 document.getElementById("strRandPrjNo").select();
			 document.getElementById("strRandPrjNo").focus();
      	}
      	function addExpericeGoodsInfo()
      	{
      		if(document.getElementById("strGoodsType").value=="")
      		{
      			$.ligerDialog.error("请确认产品类型");
				return;
      		}
      		if(document.getElementById("dShareRate").value*1==0 || document.getElementById("dShareRate").value*1>1 )
      		{
      			$.ligerDialog.error("请确认产品体验折扣(不能超过1)");
				return;
      		}
      		for (var rowid in commoninfodivGoodsDate.records)
			{
				var row =commoninfodivGoodsDate.records[rowid];
				if(checkNull(row.salegoodstype)==document.getElementById("strGoodsType").value)
				{
					$.ligerDialog.error("该产品类别已经在体验产品类型列表中存在,请重新输入");
					return;
				}		
			}  
      		var salegoodstypename=parent.loadCommonControlValue("WPTJ",0,document.getElementById("strGoodsType").value);
      		var gridlen=commoninfodivGoodsDate.rows.length*1;
			if(gridlen==0)
			{
				addgoodstrade();
				gridlen=gridlen+1;
			}
			if(checkNull(commoninfodivGoodsDate.getRow(0).salegoodstype)!="")
			{
				addgoodstrade();
				gridlen=gridlen+1;
			}
			var curCDetialRecord=commoninfodivGoodsDate.getRow(gridlen-1);
			commoninfodivGoodsDate.updateRow(curCDetialRecord,{  salegoodstype: document.getElementById("strGoodsType").value
															  	,salegoodstypename : salegoodstypename
															  	,salegoodsrate  : document.getElementById("dShareRate").value*1
															}); 
										
			 document.getElementById("strGoodsType").value="";
			 document.getElementById("dShareRate").value="";
			 document.getElementById("strGoodsType").select();
      	}
		
		function validateFromCardNo(obj)
		{
			if(obj.value=="" || obj.value*1==0)
			{
				document.getElementById("strToCardNo").value="";
				return;
			}
			if(document.getElementById("dtotalCount").value*1==0)
			{
				$.ligerDialog.error("请确认活动券数量");
				obj.value=0;
				document.getElementById("strToCardNo").value="";
				return;
			}
			var strFromCardNo="1"+document.getElementById("strFromCardNo").value;
			var strToCardNo=(strFromCardNo*1+document.getElementById("dtotalCount").value*1-1)+"";
			if(strToCardNo.substring(0,1)!="1")
			{
				$.ligerDialog.error("起始券号在提供的范围内不能正确的生成结束券号!");
				obj.value="";
				document.getElementById("strToCardNo").value="";
				return;
			}
			document.getElementById("strToCardNo").value=strToCardNo.substring(1,strToCardNo.length);
		}
		
		function postTargetInfo()
		{
			
			if(document.getElementById("strEntryCompId").value=="")
			{
				$.ligerDialog.error("请确认活动券登记门店");
				return;
			}
			if(document.getElementById("createflag").value==0)
			{
				if(document.getElementById("strFromCardNo").value=="")
				{
					$.ligerDialog.error("请确认活动券起始券号");
					return;
				}
				if(document.getElementById("strToCardNo").value=="")
				{
					$.ligerDialog.error("请确认活动券起始券号");
					return;
				}
			}
			else
			{
				if(document.getElementById("strCardPrefix").value=="")
				{
					$.ligerDialog.error("请确认活动券前缀");
					return;
				}
				if(document.getElementById("dtotalCount").value=="")
				{
					$.ligerDialog.error("请确认活动券登记张数");
					return;
				}
			}
			if(document.getElementById("strValidate").value=="")
			{
				$.ligerDialog.error("请确认该批次活动券有效日期");
				return;
			}
			$.ligerDialog.confirm('登记该批次活动券?', function (result)
			{
			    if( result==true)
	           	{
					var params="strEntryCompId="+document.getElementById("strEntryCompId").value;	
					params=params+"&strCardPrefix="+document.getElementById("strCardPrefix").value;
					params=params+"&strFromCardNo="+document.getElementById("strFromCardNo").value;
					params=params+"&strToCardNo="+document.getElementById("strToCardNo").value;
					params=params+"&strValidate="+document.getElementById("strValidate").value;
					params=params+"&strSaleCardType="+document.getElementById("strSaleCardType").value;
					params=params+"&dCardDeductAmt="+document.getElementById("dCardDeductAmt").value;
					params=params+"&strProQuanNo="+document.getElementById("strProQuanNo").value;
					params=params+"&sendquanflag="+document.getElementById("sendquanflag").value;
					params=params+"&createflag="+document.getElementById("createflag").value;
					params=params+"&totalCount="+document.getElementById("dtotalCount").value; 
					var curjosnparam="";
			        var needReplaceStr="";
			        var strJsonParam_detial="";
			        for (var rowid in commoninfodivPrjDate.records)
					{
							var row =commoninfodivPrjDate.records[rowid];
							if(checkNull(row.expericeitemno)=="")
								continue;
							curjosnparam=JSON.stringify(row);
							curjosnparam=curjosnparam.replace("%","");
							curjosnparam=curjosnparam.replace("#","");      		   
							if(strJsonParam_detial!="")
							  	strJsonParam_detial=strJsonParam_detial+",";
							strJsonParam_detial= strJsonParam_detial+curjosnparam;
					}            		 
					if(strJsonParam_detial!="")
					{
						 params=params+"&strPrjJsonParam=["+strJsonParam_detial+"]";
					}
					
					curjosnparam="";
			        needReplaceStr="";
			        strJsonParam_detial="";
			        for (var rowid in commoninfodivGoodsDate.records)
					{
							var row =commoninfodivGoodsDate.records[rowid];
							if(checkNull(row.salegoodstype)=="")
								continue;
							curjosnparam=JSON.stringify(row);
							curjosnparam=curjosnparam.replace("%","");
							curjosnparam=curjosnparam.replace("#","");      		   
							if(strJsonParam_detial!="")
							  	strJsonParam_detial=strJsonParam_detial+",";
							strJsonParam_detial= strJsonParam_detial+curjosnparam;
					}            		 
					if(strJsonParam_detial!="")
					{
						 params=params+"&strGoodsJsonParam=["+strJsonParam_detial+"]";
					}
					
					
					curjosnparam="";
			        needReplaceStr="";
			        strJsonParam_detial="";
			        for (var rowid in commoninfodivrandcardDate.records)
					{
							var row =commoninfodivrandcardDate.records[rowid];
							if(checkNull(row.expericeitemno)=="")
								continue;
							curjosnparam=JSON.stringify(row);
							curjosnparam=curjosnparam.replace("%","");
							curjosnparam=curjosnparam.replace("#","");      		   
							if(strJsonParam_detial!="")
							  	strJsonParam_detial=strJsonParam_detial+",";
							strJsonParam_detial= strJsonParam_detial+curjosnparam;
					}            		 
					if(strJsonParam_detial!="")
					{
						 params=params+"&strRandPrjJsonParam=["+strJsonParam_detial+"]";
					}
					showDialogmanager = $.ligerDialog.waitting('正在生成活动券中,请稍候...');
					var requestUrl ="bc019/postActivitycardinfo.action"; 
					var responseMethod="postActivitycardinfoMessage";	
					sendRequestForParams_p(requestUrl,responseMethod,params );
				}
			});  
		}
		
		function postActivitycardinfoMessage(request)
      	{
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		$.ligerDialog.success("登记成功");
	        		document.getElementById("strEntryCompId").value="";	
	        		document.getElementById("strEntryCompName").value="";
	        		document.getElementById("dtotalCount").value="";
	        		document.getElementById("strCardPrefix").value="";	
	        		document.getElementById("strFromCardNo").value="";	
	        		document.getElementById("strToCardNo").value="";	
	        		document.getElementById("strSaleCardType").value="";	
	        		document.getElementById("dCardDeductAmt").value="";	
	        		document.getElementById("strProQuanNo").value="";	
	        		document.getElementById("strQuanValidate").value="";
	        		document.getElementById("strPrjNo").value="";
	        		document.getElementById("dSharePrice").value="";
	        		document.getElementById("strGoodsType").value="";
	        		document.getElementById("dShareRate").value="";
	        		document.getElementById("strValidate").value="";
	        		document.getElementById("sendquanflag").value=0;
	        		document.getElementById("createflag").value=0;
	        		commoninfodivPrjDate.options.data=$.extend(true, {},{Rows: null,Total:0});
            		commoninfodivPrjDate.loadData(true);   
            		addprjtrade();  
            		
            		commoninfodivGoodsDate.options.data=$.extend(true, {},{Rows: null,Total:0});
            		commoninfodivGoodsDate.loadData(true);   
            		addgoodstrade();  
            		
            		commoninfodivrandcardDate.options.data=$.extend(true, {},{Rows: null,Total:0});
            		commoninfodivrandcardDate.loadData(true);   
            		addrandprjtrade(); 
            		
            		if(responsetext.lsMaster!=null && responsetext.lsMaster.length>0)
			   		{
			   			commoninfodivEntryDate.options.data=$.extend(true, {},{Rows: responsetext.lsMaster,Total: responsetext.lsMaster.length});
		            	commoninfodivEntryDate.loadData(true); 
			   		}
			   		else
			   		{
			   			commoninfodivEntryDate.options.data=$.extend(true, {},{Rows: null,Total:0});
		            	commoninfodivEntryDate.loadData(true);   
		            	addentrytrade()   
			   		}
	        	}
	        	else
	        	{
	        		$.ligerDialog.error(strMessage);
	        	}
	        	showDialogmanager.close();
        	}
			catch(e)
			{
				alert(e.message);
			}
      	}
      	
      	
      	function searchDataSet()
      	{
      		if(document.getElementById("strSearchDate").value=="" 
      		&& document.getElementById("strSearchCardNo").value==""
      		&& document.getElementById("strSearchVoucherno").value=="")
      		{
      			$.ligerDialog.error("请填写登记日期或登记券号或券前缀");
				return;
      		}
      		
      		var requestUrl ="bc019/searchData.action"; 
			var responseMethod="searchDataMessage";	
			var strCurCompId=getCurOrgFromSearchBar();
			var params="strCurCompId="+strCurCompId;	
			params=params+"&strSearchDate="+document.getElementById("strSearchDate").value ;
			params=params+"&strSearchCardNo="+document.getElementById("strSearchCardNo").value ;
			params=params+"&strSearchVoucherno="+document.getElementById("strSearchVoucherno").value ;
			sendRequestForParams_p(requestUrl,responseMethod,params );
      	}
      	
      	
      	function searchDataMessage(request)
	    {
	    	try
	        {
	    		var responsetext = eval("(" + request.responseText + ")");
		   		if(responsetext.lsMaster!=null && responsetext.lsMaster.length>0)
		   		{
		   			commoninfodivRecordDate.options.data=$.extend(true, {},{Rows: responsetext.lsMaster,Total: responsetext.lsMaster.length});
	            	commoninfodivRecordDate.loadData(true); 
		   		}
		   		else
		   		{
		   			commoninfodivRecordDate.options.data=$.extend(true, {},{Rows: null,Total:0});
	            	commoninfodivRecordDate.loadData(true);   
	            	addrtrade()   
		   		}
		   	}catch(e){alert(e.message);}
	    }
	    
	    function hotKeyOfSelf(key)
		{
			if(key==13)//回车
			{
				hotKeyEnter();
				
			}
	
		}
		
		function  hotKeyEnter()
		{
		
			try
			{
				var fieldName = document.activeElement.name;
				var fieldId = document.activeElement.id ;
				if(fieldId=="strRandPrjNo")
				{
						validateProNo(document.activeElement);
				}
				window.event.keyCode=9; //tab
				window.event.returnValue=true;
			}
			catch(e){alert(e.message);}
				
		}
		
		
		function selectCall()
		{
			validateProNo(document.getElementById("strPrjNo"));
			validateProNo(document.getElementById("strRandPrjNo"));
		}
		
		function validateProNo(obj)
		{
			
			if(obj.value=="")
			{
				return ;
			}
			if(document.getElementById("strEntryCompId").value=="")
			{
				$.ligerDialog.warn("请确认登记的门店!");
				document.getElementById("strRandPrjName").value="";
				return ;
			}
			{
				var requestUrl ="bc019/validateItem.action"; 
				var responseMethod="validateItemMessage";
				var params="strCurItemId="+obj.value;;	
				params =params+ "&strEntryCompId="+document.getElementById("strEntryCompId").value;
				sendRequestForParams_p(requestUrl,responseMethod,params );
			}
		}
		
		function validateItemMessage(request)
		{
			try
			{
		     	var responsetext = eval("(" + request.responseText + ")");
		     	var curProjectinfo=responsetext.curProjectinfo;
		     	if(curProjectinfo==null)
		   	 	{
		     		$.ligerDialog.warn("输入的项目编码不存在!");
		     		document.getElementById("strRandPrjName").value="";
		     		return;
		     	}
				else if(checkNull(curProjectinfo.useflag)==2)
				{
				  	$.ligerDialog.warn("该项目已经停止使用");
				  	document.getElementById("strRandPrjName").value="";
				  	return;
				}
				else if(checkNull(curProjectinfo.saleflag)==2)
				{
				  	$.ligerDialog.warn("该项目已经停止销售");
				  	document.getElementById("strRandPrjName").value="";
				  	return;
				}
				else
		  		{
		  			document.getElementById("strRandPrjName").value=curProjectinfo.prjname;
				}
		     }catch(e){alert(e.message);}
		
		}
		
		function validateSendFlag(obj)
		{
			if(obj.value==0)
			{
				document.getElementById("strRandPrjNo").readOnly="readOnly";
		    	document.getElementById("strRandPrjNo").style.background="#EDF1F8"; 
			}
			else
			{
				document.getElementById("strRandPrjNo").readOnly="";
		    	document.getElementById("strRandPrjNo").style.background="#FFFFFF";
			}
		}
		
		function validateCreateFlag(obj)
		{
			if(obj.value==1)
			{
				document.getElementById("strFromCardNo").value="";
				document.getElementById("strToCardNo").value="";
				document.getElementById("strFromCardNo").readOnly="readOnly";
		    	document.getElementById("strFromCardNo").style.background="#EDF1F8"; 
			}
			else
			{
				document.getElementById("strFromCardNo").readOnly="";
		    	document.getElementById("strFromCardNo").style.background="#FFFFFF";
			}
		}
		
		function deletePXRecord()
		{
			commoninfodivrandcardDate.deleteSelectedRow();
		}
		
		function deletePrjtyRecord()
		{
			commoninfodivPrjDate.deleteSelectedRow();
		}
		
		function deleteGoodstyRecord()
		{
			commoninfodivGoodsDate.deleteSelectedRow();
		}