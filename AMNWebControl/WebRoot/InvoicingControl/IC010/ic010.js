	var commoninfodivmaster=null;	//产品入库主档列表
   	var commoninfodivdetial=null;	//产品入库明细列表
   	var commoninfodivdetial_barcode=null; //条码卡列表
   	var commoninfodivdetial_pcinser=null;  //入库批次号
   	var commoninfodivdetial_pcouter=null;  //出库批次号
   	var strCurCompId="";
   	var strCurBillId="";
   	var curMasterInfoDate=null;
   	var fromValidate=null;
   	var cinsertwaremanager = null;
   	var cinsertcardtypemanager = null;
   	var cinsertpermanager = null;
   	var ic010layout=null;
   	var curpagestate=3; // 1 add 2 modify 3 browse
   	var insertypemanager=null;
   	var curDMasterlRecord = null;
   	var curMasterRecord = null;
	var curDetialRecord = null;
   	var insertypeData=JSON.parse(parent.loadCommonControlDate_select("RKFS",0));
   	var showDialogmanager=null;
    var rightmenu;
    var iGoodsBarCode="";
    var istartBarCode="";
    var iendBarCode="";
    var showDialog=null;
    var confirmInfo=null;
    var cancelInfo=null;
    var IC010Tab=null;
    var lsDgoodssendinfos=null;
    var statechooseData = [{ choose: 0, text: '登记' }, { choose: 1, text: '已复合'}, { choose: 2, text: '已作废'}];
    
    var lsDetialBarInfo=null;
    function LocalDetialBarInfo(sendgoodsno,frombarcode,tobarcode,sendbarcount)
	{
		this.sendgoodsno = sendgoodsno;
		this.frombarcode = frombarcode;
		this.tobarcode = tobarcode;
		this.sendbarcount = sendbarcount;
	}
   	//初始化属性
    $(function ()
   	{
	   try
	   {  
	   		  //布局
            ic010layout= $("#ic010layout").ligerLayout({ rightWidth: 320,  allowBottomResize: false, isLeftCollapse:true,isRightCollapse:true});
	   		var height = $(".l-layout-center").height();
           	
           	$("#IC010Tab").ligerTab();
            IC010Tab = $("#IC010Tab").ligerGetTabManager();
           	commoninfodivmaster=$("#commoninfodivmaster").ligerGrid({
                columns: [
                { display: '发货单号', 	name: 'bsendbillid', 	width:140},
                { display: '门店', 		name: 'ordercompname', 	width:65},
	            { display: '发货日期', 	name: 'senddate', 		width:80},
	            { hide:true,	name: 'bsendcompid',width:1	}
                ],  pageSize:20, 
                data:{Rows: null,Total:0},
                height:'722',
                clickToEdit: false,   enabledEdit: false,  
                rownumbers: false,usePager: false,
                groupColumnName:'headwarename',groupColumnDisplay:'仓库',
                onSelectRow : function (data, rowindex, rowobj)
                {
                	curMasterRecord=data;
                    loadSelecDetialData(data, rowindex, rowobj);
                }   
            });
           
     
        	commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
                columns: [
                { display: '产品编号', 	name: 'sendgoodsno',  				width:70,align: 'left' },
                { display: '产品名称', 	name: 'sendgoodsname',  			width:280,align: 'left' },
                { display: '单位', 		name: 'sendgoodsunit', 				width:50,align: 'left'},
	            { display: '申请量', 	name: 'ordergoodscount', 			width:40,align: 'right'},
	            { display: '下单量', 	name: 'downordercount', 			width:40,align: 'center',
	            	render: function (row) {  
     					var html ="";
     					if(checkNull(row.downordercount)!="")
     					  html="<b><font color=\"blue\"> "+row.downordercount+"</font></b>";  
     					return html;  
 					}   },
 				 { display: '实发数', 	name: 'sendgoodscount', 			width:40,align: 'center' ,
	            	render: function (row) {  
     					var html ="";
     					if(checkNull(row.sendgoodscount)!="" && checkNull(row.sendgoodscount)*1!=checkNull(row.downordercount)*1)
     					  html="<b><font color=\"red\"> "+row.sendgoodscount+"</font></b>";  
     					else if(checkNull(row.sendgoodscount)!="" && checkNull(row.sendgoodscount)*1==checkNull(row.downordercount)*1)
     					  html="<b><font color=\"blue\"> "+row.sendgoodscount+"</font></b>";  
     					return html;  
 					}  },
	            { display: '单价', 	name: 'sendgoodprice', 				width:40,align: 'right' }  ,
	            { display: '折扣', 		name: 'sendgoodrate', 				width:40,align: 'right' , editor: { type: 'float' }}  ,
	           
	            { display: '金额',  	name: 'sendgoodsamt', 				width:60,align: 'right'}
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '700',
                height:'100%',
                clickToEdit: true,   enabledEdit: true,  checkbox: false,
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    curDetialRecord=data;
                    loadSelecBarDetialData(data, rowindex, rowobj);
                },
                onDblClickRow : function (data, rowindex, rowobj)
                {
                	curDetialRecord=data;
                    loadGoodsPcinfo(data);
                } 
                 /* toolbar: { items: [

    				 	     { text: '打印对货单', click: printReport, img: contextURL+'/common/ligerui/ligerUI/skins/icons/print.gif' },
	                		 { text: '总部发货', click: handconfirmInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/save.gif' },
	                		 { text: '打印发货单', click: printSendReport, img: contextURL+'/common/ligerui/ligerUI/skins/icons/print.gif' },
	                		 { text: '停止发货', click: handcancelInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/candle.gif' }
    				 	     
    				 	 ]}	*/
            });
            
          /* commoninfodivdetial_pcinser=$("#commoninfodivdetial_pcinser").ligerGrid({
                columns: [
                { display: '入库批次', 	name: 'inserbillid',  			width:120,align: 'left' },
                { display: '入库日期', 	name: 'inserdate',  			width:90,align: 'left' },
                { display: '存量', 		name: 'curlavecount',  			width:50,align: 'left' },
                { display: '出库', 		name: 'outercount', 			width:50,align: 'left',align: 'center',editor: { type: 'int' }},
              	{  hide:true ,	name: 'inserseqno' },
              	{ hide:true ,	name: 'outerprice' },
              	{  hide:true ,	name: 'insergoodsno' }
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '330',
                height:'280',
                clickToEdit: true,   enabledEdit: true,  checkbox: false,
                rownumbers: false, usePager: false,
               
                toolbar: { items: [
	                		{ text: '选择入库批次', click: itemclick_GoodsPcfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/right.gif' }
    				 	 ]}	
	           
            });
       
       		commoninfodivdetial_pcouter=$("#commoninfodivdetial_pcouter").ligerGrid({
                columns: [
                { display: '入库批次', 	name: 'inserbillid',  			width:120,align: 'left' },
                { display: '产品编号', 	name: 'insergoodsno',  			width:80,align: 'left' },
                { display: '出库数量', 		name: 'outercount', 		width:80,align: 'left'},
                { hide:true ,	name: 'inserseqno' },
                { hide:true ,	name: 'outerprice' }
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '320',
                height:'390',
                clickToEdit: true,   enabledEdit: true,  checkbox: false,
                rownumbers: false, usePager: false
            });*/
            
            $("#onebartoolbar").ligerToolBar({ items: [
      		   
            ]
            });	
           commoninfodivdetial_barcode=$("#commoninfodivdetial_barcode").ligerGrid({
                columns: [
                { display: '产品编号', 	name: 'sendgoodsno',  			width:80,align: 'left' },
                { display: '起始条码', 	name: 'frombarcode',  			width:100,align: 'left' },
                { display: '结束条码', 	name: 'tobarcode', 				width:100,align: 'left'},
                { display: '数量', 		name: 'sendbarcount', 			width:40,align: 'left'}
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '380',
                height:'100%',
                clickToEdit: true,   enabledEdit: true,  checkbox: false,
                rownumbers: false, usePager: false
            });
			 
           	 $("#searchButton").ligerButton(
	         {
	             text: '查询单据', width: 80,
		         click: function ()
		         {
		             searchOrderBill();
		         }
	         });
	         
           	 confirmInfo=$("#confirmInfo").ligerButton(
	         {
	             text: '总部发货', width: 80,
		         click: function ()
		         {
		             handconfirmInfo();
		         }
	         });
	          cancelInfo=$("#cancelInfo").ligerButton(
	         {
	             text: '停止发货', width: 80,
		         click: function ()
		         {
		             handcancelInfo();
		         }
	         });
	         
	       	$("#prePrint").ligerButton(
	         {
	             text: '打印对货单', width: 80,
		         click: function ()
		         {
		             printReport();
		         }
	         });
	         $("#curPrint").ligerButton(
	         {
	             text: '打印发货单', width: 80,
		         click: function ()
		         {
		             printSendReport();
		         }
	         });
	         
	        $("#toptoolbarCard").ligerToolBar({ items: [
	        	{text: '采购单号:&nbsp;<input id="strSearchOrderBillno" type="text" style="width:115"/> <input id="strSearchSendBillno" type="hidden" />'},
      		    { text: '查询', click: searchOrderBill, img: contextURL+'/common/ligerui/ligerUI/skins/icons/search.gif' }
            ]
            });
            
          	$("#pageloading").hide(); 
          	addDetialRecord();
    		loadAppOrder();
          	//addDetialinsertpcRecord();
          	//addDetialoutertpcRecord();
          	addDetialBarnoRecord();
   		}catch(e){alert(e.message);}
    }); 
   
	function loadAppOrders()
    {
    	lsDetialBarInfo=null;
        try{
        	var params ="strCurCompId="+ document.getElementById("sendcompid").value;
        	    params=params+"&strCurBillId="+document.getElementById("sendbillid").value;
     		var requestUrl ="ic010/loadDorderInfos.action"; 
			var responseMethod="loadGoodsOrderInfoMessages";				
			sendRequestForParams_p(requestUrl,responseMethod,params );
          }catch(e){alert(e.message);}
    }
   
   function loadGoodsOrderInfoMessages(request)
   {
       		try
        	{
        	var responsetext = eval("(" + request.responseText + ")");
	   		
	   		if(responsetext.lsDgoodssendinfo!=null && responsetext.lsDgoodssendinfo.length>0)
			{
						   			lsDgoodssendinfos=responsetext.lsDgoodssendinfo;
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: responsetext.lsDgoodssendinfo,Total: responsetext.lsDgoodssendinfo.length});
					            	commoninfodivdetial.loadData(true);    
					            	commoninfodivdetial.select(0);
					            	document.getElementById("needPost").value="0";        	
			}
			else
			{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
					            	commoninfodivdetial.loadData(true);  
					            	addDetialRecord();
					            	document.getElementById("needPost").value="1"; 
			}
			//commoninfodivdetial_barcode.options.data=$.extend(true, {},{Rows: null,Total:0});
            //commoninfodivdetial_barcode.loadData(true); 
            //addDetialBarnoRecord();
            /*if(responsetext.lsDgoodssendbarinfo!=null && responsetext.lsDgoodssendbarinfo.length>0)
			{
						   			commoninfodivdetial_barcode.options.data=$.extend(true, {},{Rows: responsetext.lsDgoodssendbarinfo,Total: responsetext.lsDgoodssendbarinfo.length});
					            	commoninfodivdetial_barcode.loadData(true);          	
			}
			else
			{
						   			commoninfodivdetial_barcode.options.data=$.extend(true, {},{Rows: null,Total:0});
					            	commoninfodivdetial_barcode.loadData(true); 
					            	addDetialBarnoRecord();
			}*/
            //commoninfodivdetial_pcouter.options.data=$.extend(true, {},{Rows: null,Total:0});
            //commoninfodivdetial_pcouter.loadData(true); 
            	
           // commoninfodivdetial_pcinser.options.data=$.extend(true, {},{Rows: null,Total:0});
            //commoninfodivdetial_pcinser.loadData(true); 
          
            //addDetialinsertpcRecord();
          	//addDetialoutertpcRecord();
						   		
	   		}catch(e){alert(e.message);}
    }
   
   function loadSelecBarDetialData(data, rowindex, rowobj)
   {
   			commoninfodivdetial_barcode.options.data=$.extend(true, {},{Rows: null,Total:0});
			commoninfodivdetial_barcode.loadData(true); 
			addDetialBarnoRecord();
   			if(document.getElementById("sendstate").value==0)
   			{
   				var maxlength=0;
				if(lsDetialBarInfo!=null)
				{
				
					commoninfodivdetial_barcode.options.data=loadBarGridByLocakInfoByGoodsNo(data.sendgoodsno);
					commoninfodivdetial_barcode.loadData(true); 
					/*maxlength=lsDetialBarInfo.length;
					for (var i=0;i<maxlength*1;i++)
					{
						if(data.sendgoodsno!=checkNull(lsDetialBarInfo[i].sendgoodsno))
						{
						 		continue;
						}
						var gridlen=commoninfodivdetial_barcode.rows.length*1;
		        		if(gridlen==0)
						{
							addDetialBarnoRecord();
							gridlen=gridlen+1;
						} 
						if(checkNull(commoninfodivdetial_barcode.getRow(0).sendgoodsno)!="")
						{
							addDetialBarnoRecord();
							gridlen=gridlen+1;
						}
						var curDetialbarRecord=commoninfodivdetial_barcode.getRow(gridlen-1);
			
						commoninfodivdetial_barcode.updateRow(curDetialbarRecord,{sendgoodsno: lsDetialBarInfo[i].sendgoodsno
															  				,frombarcode : lsDetialBarInfo[i].frombarcode
															  				,tobarcode : lsDetialBarInfo[i].tobarcode
															  				,sendbarcount : lsDetialBarInfo[i].sendbarcount});  
				 
				 	}*/
				}
   			}
   			else
   			{
   				var params ="strCurCompId="+ document.getElementById("sendcompid").value;
        	   	params=params+"&strCurBillId="+document.getElementById("sendbillid").value;
        	   	params=params+"&strCurGoodsId="+data.sendgoodsno;
     			var requestUrl ="ic010/loadSelecBarDetialData.action"; 
				var responseMethod="loadSelecBarDetialDataMessage";			
				sendRequestForParams_p(requestUrl,responseMethod,params );
   			}
   			
   }
   
   function loadSelecBarDetialDataMessage(request)
   {
       		try
        	{
        		var responsetext = eval("(" + request.responseText + ")");
	   		
	
            	if(responsetext.lsDgoodssendbarinfo!=null && responsetext.lsDgoodssendbarinfo.length>0)
				{
						   			commoninfodivdetial_barcode.options.data=$.extend(true, {},{Rows: responsetext.lsDgoodssendbarinfo,Total: responsetext.lsDgoodssendbarinfo.length});
					            	commoninfodivdetial_barcode.loadData(true);          	
				}
				else
				{
						   			commoninfodivdetial_barcode.options.data=$.extend(true, {},{Rows: null,Total:0});
					            	commoninfodivdetial_barcode.loadData(true); 
					            	addDetialBarnoRecord();
				}
            
						   		
	   		}catch(e){alert(e.message);}
    }
		//加载门店信息
	function loadAppOrder()
    {
        try{
        	var params = "";			
     		var requestUrl ="ic010/loadGoodsOrderInfo.action"; 
			var responseMethod="loadGoodsOrderInfoMessage";				
			sendRequestForParams_p(requestUrl,responseMethod,params );
          }catch(e){alert(e.message);}
    }
   
   
   function loadGoodsOrderInfoMessage(request)
   {
       		try
        	{
 
        	var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.lsMgoodssendinfo!=null && responsetext.lsMgoodssendinfo.length>0)
	   		{
	   			curMasterInfoDate={Rows: responsetext.lsMgoodssendinfo,Total: responsetext.lsMgoodssendinfo.length};
	   			commoninfodivmaster.options.data=$.extend(true, {},curMasterInfoDate);
            	commoninfodivmaster.loadData(true);   
            	commoninfodivmaster.select(0);
            	        	
	   		}
	   		else
	   		{
	   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivmaster.loadData(true); 
            	addMasterRecord(); 
	   		}
			//commoninfodivdetial_barcode.options.data=$.extend(true, {},{Rows: null,Total:0});
            //commoninfodivdetial_barcode.loadData(true); 
            
            //commoninfodivdetial_pcouter.options.data=$.extend(true, {},{Rows: null,Total:0});
            //commoninfodivdetial_pcouter.loadData(true); 
            	
            //commoninfodivdetial_pcinser.options.data=$.extend(true, {},{Rows: null,Total:0});
            //commoninfodivdetial_pcinser.loadData(true); 
            addDetialBarnoRecord();
            //addDetialinsertpcRecord();
          	//addDetialoutertpcRecord();
			
	 
	   		}catch(e){alert(e.message);}
    }
    
    function loadCurMaster(curMaster)
    {
		try
		{
		
			document.getElementById("sendcompid").value=checkNull(curMaster.bsendcompid);
			document.getElementById("bbsendcompname").value=checkNull(curMaster.bbsendcompname);
	    	document.getElementById("sendbillid").value=checkNull(curMaster.bsendbillid);
	    	document.getElementById("senddate").value=checkNull(curMaster.senddate);
	    	document.getElementById("sendtime").value=checkNull(curMaster.sendtime);
	    	document.getElementById("sendstaffid").value=checkNull(curMaster.sendstaffid);
	    	document.getElementById("sendstaffname").value=checkNull(curMaster.sendstaffname);
	    	document.getElementById("sendstate").value=checkNull(curMaster.sendstate);
	    	document.getElementById("storewareid").value=checkNull(curMaster.storewareid);
	    	document.getElementById("storewarename").value=checkNull(curMaster.storewarename);
	    	document.getElementById("headwareid").value=checkNull(curMaster.headwareid);
	    	document.getElementById("headwarename").value=checkNull(curMaster.headwarename);
	    	document.getElementById("headwarename").value=checkNull(curMaster.headwarename);
	    	document.getElementById("storestaffid").value=checkNull(curMaster.storestaffid);
	    	document.getElementById("storestaffname").value=checkNull(curMaster.storestaffname);
	    	document.getElementById("storeaddress").value=checkNull(curMaster.storeaddress);
	    	document.getElementById("orderdate").value=checkNull(curMaster.orderdate);
	    	document.getElementById("ordertime").value=checkNull(curMaster.ordertime);
	    	document.getElementById("ordercompid").value=checkNull(curMaster.ordercompid);
	    	document.getElementById("ordercompname").value=checkNull(curMaster.ordercompname);
	    	document.getElementById("orderbill").value=checkNull(curMaster.orderbill);
	    	document.getElementById("orderbilltype_h").value=checkNull(curMaster.orderbilltype);
	    	document.getElementById("orderbilltype").value=checkNull(curMaster.orderbilltype);
	    	document.getElementById("sendopationerid").value=checkNull(curMaster.sendopationerid);
	    	document.getElementById("sendopationdate").value=checkNull(curMaster.sendopationdate);
	    	/**************************/
	    	loadAppOrders();
			if(document.getElementById("sendstate").value==0)
			{
				confirmInfo.setEnabled();
				cancelInfo.setEnabled();
				pageWriteState();
			}
			else 
			{
				confirmInfo.setDisabled();
				cancelInfo.setDisabled();
				pageReadState();
			}
			
		}catch(e){alert(e.message);}
    }
    

    function pageWriteState()
    {
    	try
	    {
	    	document.getElementById("sendstaffid").readOnly="";
	    	document.getElementById("storestaffid").readOnly="";
	    	document.getElementById("storeaddress").readOnly="";
	    	document.getElementById("wfrombarcode").readOnly="";
	    	document.getElementById("wtobarcode").readOnly="";	    	
	    	commoninfodivdetial.options.enabledEdit=true;
	    	//commoninfodivdetial_pcinser.options.enabledEdit=true;
    	}catch(e){alert(e.message);}
    }
    function pageReadState()
    {
    	try
	    {
	    	document.getElementById("sendstaffid").readOnly="readOnly";
	    	document.getElementById("storestaffid").readOnly="readOnly";
	    	document.getElementById("storeaddress").readOnly="readOnly";
	    	document.getElementById("wfrombarcode").readOnly="readOnly";
	    	document.getElementById("wtobarcode").readOnly="readOnly";
	   		commoninfodivdetial.options.enabledEdit=false;
	   		//commoninfodivdetial_pcinser.options.enabledEdit=false;
    	}catch(e){alert(e.message);}
    }
    
    //根据主档选择加载明细
    function loadSelecDetialData(data, rowindex, rowobj)
    {
        try{
        		 //开启屏蔽窗口
        	 	 //addcloud();
        		showDialog=$.ligerDialog.waitting('正在查询中,请稍候...');
        		 if(curpagestate!=3)
        		 {
        		 	$.ligerDialog.warn("请保持当前信息或刷新本界面后在进行切换操作!");
        		 	return ;
        		 }
        		 strCurCompId=data.bsendcompid;
        		 strCurBillId=data.bsendbillid;
       			 var params = "strCurCompId="+strCurCompId;
       			 params =params+ "&strCurBillId="+strCurBillId;
				 var myAjax = new parent.Ajax.Request(
						"ic010/loadDorderInfo.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {
								var responsetext = eval( "("+request.responseText+")");	
								
	   							loadCurMaster(responsetext.curMgoodssendinfo);
	   							//关闭屏蔽窗口
	   							showDialog.close();
	   							//removecloud();
							},
							asynchronous:true
						});
						
				
          }catch(e){alert(e.message);}
    }
    
  
 
   //--------------------------验证验货人员-----------------
   function validateInserper(obj)
   {
   		if(obj.value=="")
   		{
   			document.getElementById("sendstaffname").value="";
   			return ;
   		}
   		var requestUrl ="ic010/validateInserper.action";
        var params="strCurCompId="+document.getElementById("sendcompid").value;
        params=params+"&strCurEmpId="+obj.value;
		var responseMethod="validateInserperMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	  
   }
   
   function validateInserperMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		document.getElementById("sendstaffname").value=responsetext.strCurEmpName;
	       	}
	       	else
	       	{
	       		document.getElementById("sendstaffid").value="";
	       		document.getElementById("sendstaffname").value="";
	       		$.ligerDialog.warn(strMessage);
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	
   	//--------------------------验证验货人员-----------------
   function validatestorestaffid(obj)
   {
   		if(obj.value=="")
   		{
   			document.getElementById("storestaffname").value="";
   			return ;
   		}
   		var requestUrl ="ic010/validateInserper.action";
         var params="strCurCompId="+document.getElementById("ordercompid").value;
        params=params+"&strCurEmpId="+obj.value;
		var responseMethod="validatedownerMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	  
   }
   
   function validatedownerMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		document.getElementById("storestaffname").value=responsetext.strCurEmpName;
	       	}
	       	else
	       	{
	       		document.getElementById("storestaffid").value="";
	       		document.getElementById("storestaffname").value="";
	       		$.ligerDialog.warn(strMessage);
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	



   	function addMasterRecord()
    {
    		var row = commoninfodivmaster.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivmaster.addRow({ 
				  headwarename:'无'         
			}, row, false);
    }
    
    function addDetialRecord()
    {
    		var row = commoninfodivdetial.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivdetial.addRow({ 
				      ordergoodsno: "",
				      ordergoodsname: "",
				      ordergoodsunit: "",
				      ordergoodscount: "" ,
				      ordergoodsprice: "",
				      ordergoodsamt: "",
				      downordercount: "",
				      nodowncount: "",
				      norevicecount: "",
				      supplierno:"",
				      headwareno:"",
				      goodssource:""
			}, row, false);
    }

	function addDetialinsertpcRecord()
    {
    		var row = commoninfodivdetial_pcinser.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivdetial_pcinser.addRow({ 
				    
			}, row, false);
    }
    
  	function addDetialoutertpcRecord()
    {
    		var row = commoninfodivdetial_pcouter.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivdetial_pcouter.addRow({ 
				inserbillid:'',
				outercount:'',
				inserseqno:0,
				insergoodsno:'',
				outerprice:0
			}, row, false);
			
                
    }         
    function deletedetialRecord()
    {
    	commoninfodivdetial.deleteSelectedRow();
    	
    }
    
    function addDetialBarnoRecord()
    {
    		var row = commoninfodivdetial_barcode.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivdetial_barcode.addRow({ 
				sendgoodsno:'',
				frombarcode:'',
				tobarcode:'',
				sendbarcount:''	    
			}, row, false);
    }
    

    
     //---------------------------------------------回车事件
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
				window.event.keyCode=9; //tab
				window.event.returnValue=true;
			}
			catch(e){alert(e.message);}
				
		}   
		
		
		
		
    
    //复合入库单
    function handconfirmInfo()
    {
    	showDialogmanager = $.ligerDialog.waitting('正在发货中,请稍候...');
        if(parent.hasFunctionRights( "IC010",  "UR_POST")!=true)
        {
       		$.ligerDialog.warn("该用户没有保存权限,请确认!");
       		showDialogmanager.close();
       		curpagestate=2;
       	 	return;
        }
	     if(document.getElementById("sendstaffid").value=="")
		{
			$.ligerDialog.error('请输入发货员工!');
			showDialogmanager.close();
			curpagestate=2;
			return;
		}
		 if(document.getElementById("storestaffid").value=="")
		{
			$.ligerDialog.error('请输入门店联系人!');
			showDialogmanager.close();
			curpagestate=2;
			return;
		}
    	var queryStringTmp=$('#goodsInsertForm').serialize();
		var requestUrl ="ic010/handconfirmInfo.action";
		var params=queryStringTmp;
		var curjosnparam="";
        var needReplaceStr="";
        var strJsonParam_detial="";
   		var needPost=1;
   		var notComplePost=1;
   		var notCompleGoodName="";
   		var totalSendCount=0;
   		var lsObj=new Array();
   		var lsobjlen=0;
        for (var rowid in commoninfodivdetial.records)
		{
				var row =commoninfodivdetial.records[rowid];
				if(checkNull(row.sendgoodsno)=="")
					continue;
				if(checkNull(row.downordercount)*1<checkNull(row.sendgoodscount)*1)
				{
						$.ligerDialog.error(checkNull(row.sendgoodsname)+'发货数量不能超过下单数量!');
						showDialogmanager.close();
						curpagestate=2;
						return;
				}
				if(checkNull(row.downordercount)*1>checkNull(row.sendgoodscount)*1)
				{
						lsObj[lsobjlen]='<br/>'+checkNull(row.sendgoodsname)+'下单数  ['+checkNull(row.downordercount)+']  发货数  ['+checkNull(row.sendgoodscount)+']!';
						lsobjlen=lsobjlen*1+1;
				}
				totalSendCount=totalSendCount*1+checkNull(row.sendgoodscount)*1;
				
				curjosnparam=JSON.stringify(row);
				curjosnparam=curjosnparam.replace("%","");
				curjosnparam=curjosnparam.replace("#","");
				/*if(curjosnparam.indexOf("_id")>-1)
				{
					needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
					curjosnparam=curjosnparam.replace(needReplaceStr,"");
				}*/	            		   
				if(strJsonParam_detial!="")
					strJsonParam_detial=strJsonParam_detial+",";
				strJsonParam_detial= strJsonParam_detial+curjosnparam;
		}
		if(strJsonParam_detial!="")
		{
			params=params+"&strJsonParam=["+strJsonParam_detial+"]";
		}
		
		
		if(lsDetialBarInfo!=null)
		{
			commoninfodivdetial_barcode.options.data=loadBarGridByLocakInfo();
			commoninfodivdetial_barcode.loadData(true); 
			/*var maxlength=lsDetialBarInfo.length;
			for (var i=0;i<maxlength*1;i++)
			{
				var gridlen=commoninfodivdetial_barcode.rows.length*1;
		        if(gridlen==0)
				{
					addDetialBarnoRecord();
					gridlen=gridlen+1;
				} 
				if(checkNull(commoninfodivdetial_barcode.getRow(0).sendgoodsno)!="")
				{
					addDetialBarnoRecord();
					gridlen=gridlen+1;
				}
				var curDetialbarRecord=commoninfodivdetial_barcode.getRow(gridlen-1);
			
				commoninfodivdetial_barcode.updateRow(curDetialbarRecord,{sendgoodsno: lsDetialBarInfo[i].sendgoodsno
															  				,frombarcode : lsDetialBarInfo[i].frombarcode
															  				,tobarcode : lsDetialBarInfo[i].tobarcode
															  				,sendbarcount : lsDetialBarInfo[i].sendbarcount});  
				
			} */
			
			strJsonParam_detial="";
			for (var rowid in commoninfodivdetial_barcode.records)
			{
					var row =commoninfodivdetial_barcode.records[rowid];
					if(checkNull(row.sendgoodsno)=="")
						continue;
					curjosnparam=JSON.stringify(row);
					            		   
					if(strJsonParam_detial!="")
						strJsonParam_detial=strJsonParam_detial+",";
					strJsonParam_detial= strJsonParam_detial+curjosnparam;
			}
			if(strJsonParam_detial!="")
			{
				params=params+"&strJsonBarParam=["+strJsonParam_detial+"]";
			}
		
		}
   	
		/*strJsonParam_detial="";
		for (var rowid in commoninfodivdetial_pcouter.records)
		{
				var row =commoninfodivdetial_pcouter.records[rowid];
				if(checkNull(row.inserbillid)=="")
					continue;
				curjosnparam=JSON.stringify(row);
				            		   
				if(strJsonParam_detial!="")
					strJsonParam_detial=strJsonParam_detial+",";
				strJsonParam_detial= strJsonParam_detial+curjosnparam;
		}
		if(strJsonParam_detial!="")
		{
			params=params+"&strJsonPcParam=["+strJsonParam_detial+"]";
		}*/
		var responseMethod="handconfirmInfoMessage";
		var altermessage="";
		for(var i=0;i<lsobjlen*1;i++)
		{
			altermessage=altermessage+checkNull(lsObj[i])+"   ";
			needPost=0;
		}	
		if(needPost==0)
		{
			$.ligerDialog.confirm('发货总数为 '+totalSendCount*1+','+altermessage+', <br/>发货数量不足,是否继续发货? ', function (result)
				{
					if( result==true)
				    {
				        sendRequestForParams_p(requestUrl,responseMethod,params );   
				    }
				    else
				    {
				       showDialogmanager.close();
				       
				    }
			});
		}
		else
		{
			$.ligerDialog.confirm('发货总数为 '+totalSendCount*1+',请核对是否正?', function (result)
				{
					if( result==true)
				    {
				        sendRequestForParams_p(requestUrl,responseMethod,params );   
				    }
				    else
				    {
				       showDialogmanager.close();
				       
				    }
			});
		}
    }
    
     function loadBarGridByLocakInfoByGoodsNo(goodsno)
	{
			try
			{
			
				if(lsDetialBarInfo==null)
					return null;
				var strJson = "";//'{ "name": "cxh", "sex": "man" }';
				var ccount=0;
				for(var i=0;i<lsDetialBarInfo.length;i++)
				{
					if(goodsno!=checkNull(lsDetialBarInfo[i].sendgoodsno))
					{
						 		continue;
					}
					ccount=ccount*1+1;
					if(ccount==1)
					{
						strJson=strJson+'[';
					}
					else
					{
						strJson=strJson+',';
					}
					strJson=strJson+'{"sendgoodsno": "'+lsDetialBarInfo[i].sendgoodsno+'", "frombarcode": "'+lsDetialBarInfo[i].frombarcode+'", "tobarcode":"'+lsDetialBarInfo[i].tobarcode+'", "sendbarcount": "'+lsDetialBarInfo[i].sendbarcount+'"   }';		
				}
				if(strJson!="")
				{
					strJson=strJson+']';
					return {Rows:JSON.parse(strJson),Total:lsDetialBarInfo.length};
				}
				return null;
			
			}catch(e){alert(e.message);}
	}
	
    
    function loadBarGridByLocakInfo()
	{
			try
			{
			
				if(lsDetialBarInfo==null)
					return null;
				var strJson = "";//'{ "name": "cxh", "sex": "man" }';
				var ccount=0;
				for(var i=0;i<lsDetialBarInfo.length;i++)
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
					strJson=strJson+'{"sendgoodsno": "'+lsDetialBarInfo[i].sendgoodsno+'", "frombarcode": "'+lsDetialBarInfo[i].frombarcode+'", "tobarcode":"'+lsDetialBarInfo[i].tobarcode+'", "sendbarcount": "'+lsDetialBarInfo[i].sendbarcount+'"   }';		
				}
				if(strJson!="")
				{
					strJson=strJson+']';
					return {Rows:JSON.parse(strJson),Total:lsDetialBarInfo.length};
				}
				return null;
			
			}catch(e){alert(e.message);}
	}
    
    
    function handconfirmInfoMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	$.ligerDialog.success("操作成功!");
	        	pageReadState();
	        	//confirmInfo.setDisabled();
				//cancelInfo.setDisabled(); 
				//commoninfodivdetial_barcode.options.data=$.extend(true, {},{Rows: null,Total:0});
            	//commoninfodivdetial_barcode.loadData(true); 
            
            	//commoninfodivdetial_pcouter.options.data=$.extend(true, {},{Rows: null,Total:0});
            	//commoninfodivdetial_pcouter.loadData(true); 
            	
            	//commoninfodivdetial_pcinser.options.data=$.extend(true, {},{Rows: null,Total:0});
            	//commoninfodivdetial_pcinser.loadData(true); 
            	addDetialBarnoRecord();
            	//addDetialinsertpcRecord();
          		//addDetialoutertpcRecord();
          		commoninfodivmaster.deleteSelectedRow();
          		
          		if(commoninfodivmaster.rows.length>0)
          		{
          			 curpagestate=3;
          			 commoninfodivmaster.select(0);
          		}
          		else
          			 curpagestate=3;
          			addMasterRecord();
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
      //申请单作废
    function handcancelInfo()
    {
    	$.ligerDialog.confirm('是否停止发货操作', function (result)
		{
		    if( result==true)
            {
    			var requestUrl ="ic010/handcancelInfo.action";
        		var params="strCurCompId="+document.getElementById("sendcompid").value;
        		params=params+"&strCurBillId="+document.getElementById("sendbillid").value;
       		 	params=params+"&strOrderCompId="+document.getElementById("ordercompid").value;
        		params=params+"&strOrderBillId="+document.getElementById("orderbill").value;
				var responseMethod="handcancelInfoMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params );
			}
		});
    }
    
     function handcancelInfoMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	$.ligerDialog.success("操作成功!");
	        	pageReadState();
	        	confirmInfo.setDisabled();
				cancelInfo.setDisabled(); 
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
    
    function searchOrderBill()
    {
    	if(document.getElementById("strSearchSendBillno").value=="" && document.getElementById("strSearchOrderBillno").value=="")
		{
			$.ligerDialog.error('请填写查询信息!');
			return;
		}
    	var requestUrl ="ic010/searchOrderBill.action";
        var params="strSearchSendBillno="+document.getElementById("strSearchSendBillno").value;
        params=params+"&strSearchOrderBillno="+document.getElementById("strSearchOrderBillno").value;
        
		var responseMethod="searchOrderBillMessage";
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
    function searchOrderBillMessage(request)
   	{
        	var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.lsMgoodssendinfo!=null && responsetext.lsMgoodssendinfo.length>0)
	   		{
	   			curpagestate=3;
	   			curMasterInfoDate={Rows: responsetext.lsMgoodssendinfo,Total: responsetext.lsMgoodssendinfo.length};
	   			commoninfodivmaster.options.data=$.extend(true, {},curMasterInfoDate);
            	commoninfodivmaster.loadData(true);   
            	commoninfodivmaster.select(0);
	   		}
	   		else
	   		{
	   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivmaster.loadData(true); 
            	addMasterRecord(); 
	   		}
			
	}

 	function loadGoodsPcinfo(data)
	{
	
		var requestUrl ="ic010/loadGoodsPc.action";
		var params="strCurCompId="+document.getElementById("sendcompid").value;
        params=params+"&strCurGoodsId="+data.sendgoodsno;
        
        params=params+"&strWareId="+document.getElementById("headwareid").value;
    
        var responseMethod="loadGoodsPcMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	
	}
	 function loadGoodsPcMessage(request)
	 {
		var responsetext = eval("(" + request.responseText + ")");
		if(responsetext.lsDgoodsinsertpc!=null && responsetext.lsDgoodsinsertpc.length>0)
		{
			commoninfodivdetial_pcinser.options.data=$.extend(true, {},{Rows: responsetext.lsDgoodsinsertpc,Total: responsetext.lsDgoodsinsertpc.length});
			commoninfodivdetial_pcinser.loadData(true);            	
		}
		else
		{
			commoninfodivdetial_pcinser.options.data=$.extend(true, {},{Rows: null,Total:0});
			commoninfodivdetial_pcinser.loadData(true);  
			addDetialinsertpcRecord();
		}
	 }	      
	function itemclick_GoodsPcfo(item)
    {
    	try
    	{
    		var detiallength=commoninfodivdetial_pcouter.rows.length*1;
    		
	    	for (var rowid in commoninfodivdetial_pcinser.records)
			{
				if(commoninfodivdetial_pcinser.records[rowid]['outercount']>0)
				{	
					var row =commoninfodivdetial_pcinser.records[rowid]; 
					if(checkNull(row.inserbillid)=="" || row.insergoodsno=="")
					{
						continue;
					}
					if(detiallength==0)
					{
						addDetialoutertpcRecord();
						detiallength=detiallength+1;
					}
					else if( commoninfodivdetial_pcouter.getRow(0)['inserbillid']!="")
					{
						addDetialoutertpcRecord();
						detiallength=detiallength+1;
					}
					var curPcRecord=commoninfodivdetial_pcouter.getRow(detiallength-1);
					commoninfodivdetial_pcouter.updateRow(curPcRecord,
					{inserbillid: row.inserbillid,insergoodsno :row.insergoodsno,outercount :ForDight(row.outercount,1),inserseqno : row.inserseqno,outerprice:curDetialRecord.outerprice});  
   	 			}
			}
			commoninfodivdetial_pcinser.options.data=$.extend(true, {},{Rows: null,Total:0});
			commoninfodivdetial_pcinser.loadData(true);  
			addDetialinsertpcRecord();
			
		}catch(e){alert(e.message);}
    }   
    
    	 //检查开始条码
	    function validateFromBarNo(obj)
	    {
	    	if(obj.value=="")
	    	{
	    		document.getElementById("winsergoodscount").value=0;
	    		document.getElementById("wtobarcode").value=0;
	    		return;
	    	}
	    	
	    	var requestUrl ="ic010/validateFromBarNo.action";
        	var params="strCurCompId="+document.getElementById("sendcompid").value;
        	params=params+"&strStartBarNo="+obj.value;
			var responseMethod="validateFromBarNoMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params ); 	
	    }
    	//验证起始条码
    	function validateFromBarNoMessage(request)
	    {
		 	try
			{
		       	var responsetext = eval("(" + request.responseText + ")");
		       	var strMessage=responsetext.strMessage;
		       	if(checkNull(strMessage)=="")
		       	{	       		 
		       		document.getElementById("winsergoodsno").value=checkNull(responsetext.curGoodsinfo.id.goodsno);
					document.getElementById("winsergoodsname").value=checkNull(responsetext.curGoodsinfo.goodsname);
					document.getElementById("winsergoodsunit").value=checkNull(responsetext.curGoodsinfo.saleunit);
					document.getElementById("winsergoodsprice").value=ForDight(checkNull(responsetext.curGoodsinfo.costamtbysale),2);
					document.getElementById("wgoodsuniquebar").value=checkNull(responsetext.curGoodsinfo.goodsuniquebar);
					document.getElementById("winsergoodscount").value=0;
					
		       	}
		       	else
		       	{
		       		$.ligerDialog.warn(strMessage);
		       		document.getElementById("wfrombarcode").value="";
		       		document.getElementById("sendbillid").focus();
					document.getElementById("sendbillid").select();
					
		       	}
	        }
			catch(e)
			{
				alert(e.message);
			}
	   	}
	   	
	   	//检查结束条码
	    function validateToBarNo(obj)
	    {
	    	if(obj.value=="")
	    	{
	    		document.getElementById("winsergoodscount").value=0;
	    	}
	    	if(document.getElementById("wfrombarcode").value=="" || document.getElementById("winsergoodsno").value=="" )
	    	{
	    			
	    			obj.value="";
	    			document.getElementById("winsergoodscount").value=0;
	    			document.getElementById("wfrombarcode").focus();
					document.getElementById("wfrombarcode").select();
					$.ligerDialog.warn("请先确当次起始条码!");
					return;
	    	}
	    	var requestUrl ="ic010/validateToBarNo.action";
        	var params="strCurCompId="+document.getElementById("sendcompid").value;
        	params=params+"&strCurGoodsId="+document.getElementById("winsergoodsno").value;
        	params=params+"&strStartBarNo="+document.getElementById("wfrombarcode").value;
        	params=params+"&strEndBarNo="+obj.value;
        	params=params+"&isOneBarFlag=0";
			var responseMethod="validateToBarNoMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params ); 	
	    }
	    
	    function validateToBarNoMessage(request)
	    {
		 	try
			{
		       	var responsetext = eval("(" + request.responseText + ")");
		       	var strMessage=responsetext.strMessage;
		       	if(checkNull(strMessage)=="")
		       	{	       		 
		       		document.getElementById("winsergoodscount").value=responsetext.goodsbarcount;
		       		if(responsetext.goodsbarcount*1>0)
		       		{
		       			var wfrombarcode=document.getElementById("wfrombarcode").value;
		       			var wtobarcode=document.getElementById("wtobarcode").value;
		       			var winsergoodsno=document.getElementById("winsergoodsno").value;
		       			/*var maxlength=commoninfodivdetial_barcode.rows.length;
		       			for (var i=0;i<maxlength*1;i++)
						{
							var row =commoninfodivdetial_barcode.getRow(i);
						 	if(winsergoodsno!=checkNull(row.sendgoodsno))
						 	{
						 		continue;
						 	}
						 	if(checkNull(row.frombarcode)!="")
						 	{
						 		if((wfrombarcode<=checkNull(row.frombarcode) && wtobarcode>=checkNull(row.tobarcode))
						 		|| (wfrombarcode>=checkNull(row.frombarcode) && wfrombarcode<=checkNull(row.tobarcode))
						 		|| (wtobarcode>=checkNull(row.frombarcode) && wtobarcode<=checkNull(row.tobarcode)))
						 		{
						 			
					    			document.getElementById("winsergoodscount").value=0;
					    			document.getElementById("wtobarcode").value="";
					    			document.getElementById("sendbillid").focus();
									document.getElementById("sendbillid").select();
									$.ligerDialog.error("该条码号段和发货条码明细中的重复,请确认!");
									return;
						 		}
						 	}
						 
						}*/
						
						var maxlength=0;
						if(lsDetialBarInfo!=null)
						{
							 maxlength=lsDetialBarInfo.length;
						}
					    for (var i=0;i<maxlength*1;i++)
					    {
							if(winsergoodsno!=checkNull(lsDetialBarInfo[i].sendgoodsno))
						 	{
						 		continue;
						 	}
						 	if(checkNull(lsDetialBarInfo[i].frombarcode)!="")
						 	{
						 		if((wfrombarcode<=checkNull(lsDetialBarInfo[i].frombarcode) && wtobarcode>=checkNull(lsDetialBarInfo[i].tobarcode))
						 		|| (wfrombarcode>=checkNull(lsDetialBarInfo[i].frombarcode) && wfrombarcode<=checkNull(lsDetialBarInfo[i].tobarcode))
						 		|| (wtobarcode>=checkNull(lsDetialBarInfo[i].frombarcode) && wtobarcode<=checkNull(lsDetialBarInfo[i].tobarcode)))
						 		{
						 			
					    			document.getElementById("winsergoodscount").value=0;
					    			document.getElementById("wtobarcode").value="";
					    			document.getElementById("sendbillid").focus();
									document.getElementById("sendbillid").select();
									$.ligerDialog.error("该条码号段和发货条码明细中的重复,请确认!");
									return;
						 		}
						 	}
						}
						
						var checkFlag=0;
						maxlength=commoninfodivdetial.rows.length;
		       			for (var i=0;i<maxlength*1;i++)
						{
							var row =commoninfodivdetial.getRow(i);
						 	if(checkNull(row.sendgoodsno)==winsergoodsno)
						 	{
						 		checkFlag=1;
						 		var downcount=checkNull(row.downordercount)*1;
						 		var dsendgoodscount=checkNull(row.sendgoodscount)*1;
						 		if(downcount<dsendgoodscount*1+responsetext.goodsbarcount*1)
						 		{
						 			
					    			document.getElementById("winsergoodscount").value=0;
					    			document.getElementById("wtobarcode").value="";
					    			document.getElementById("sendbillid").focus();
									document.getElementById("sendbillid").select();
									$.ligerDialog.error(document.getElementById("winsergoodsname").value+"条码数量超过了该产品的下单数量!");
									return;
						 		}
						 		else
						 		{
						 			commoninfodivdetial.updateRow(row,{sendgoodscount : ForDight(dsendgoodscount*1+responsetext.goodsbarcount*1,1),
						 											   sendgoodsamt: ForDight((dsendgoodscount*1+responsetext.goodsbarcount*1)*document.getElementById("winsergoodsprice").value*1,1) });  
								}
								break;
						 	}
						}
						if(checkFlag==0)
						{
							$.ligerDialog.error("该条码号段对应的产品在发货条码明细中不存在,请确认!");
							return;
						}
						loadGoodsBarCordMessage();
						if(responsetext.lsDgoodsinsertpc.length>0)
						{
							//loadOutPc(responsetext.lsDgoodsinsertpc);
						}
		       		}
		       	}
		       	else
		       	{
		       		$.ligerDialog.warn(strMessage);
		       		document.getElementById("wtobarcode").value="";
		       		document.getElementById("winsergoodscount").value=0;
		       		document.getElementById("sendbillid").focus();
					document.getElementById("sendbillid").select();
		       	}
	        }
			catch(e)
			{
				alert(e.message);
			}
	   	}
	   	
	   	function loadOutPc(lsDgoodsinsertpc)
	   	{
	   			var gridlen=commoninfodivdetial_pcouter.rows.length*1;
		        var curDetialbarRecord=null;
		        if(gridlen==0)
				{
					addDetialoutertpcRecord();
					gridlen=gridlen+1;
				} 
				var existsflag=0;
				var boutercount=0;
				for(var i=0;i<lsDgoodsinsertpc.length;i++)
				{
					existsflag=0;
					for (var rowid in commoninfodivdetial_pcouter.records)
					{
						var row =commoninfodivdetial_pcouter.records[rowid];
						if(row.inserbillid==lsDgoodsinsertpc[i].inserbillid
						&& row.inserseqno==lsDgoodsinsertpc[i].inserseqno)	
						{
							boutercount=row.outercount;
							commoninfodivdetial_pcouter.updateRow(row,{outercount : lsDgoodsinsertpc[i].outercount*1+boutercount*1});  
							existsflag=1;
							break;
						}	
					}            
						
					if(existsflag==0)
					{
						if(checkNull(commoninfodivdetial_pcouter.getRow(0).inserbillid)!="")
						{
							addDetialoutertpcRecord();
							gridlen=gridlen+1;
						}
						curDetialbarRecord=commoninfodivdetial_pcouter.getRow(gridlen-1);
						commoninfodivdetial_pcouter.updateRow(curDetialbarRecord,{inserbillid: lsDgoodsinsertpc[i].inserbillid
															  				,outercount : lsDgoodsinsertpc[i].outercount
															  				,inserseqno : lsDgoodsinsertpc[i].inserseqno
															  				,insergoodsno : lsDgoodsinsertpc[i].insergoodsno
															  				,outerprice : ForDight(document.getElementById("winsergoodsprice").value*1,1)});  
					}
					
				}
				
				
			
	   	}

	   	
	   	function loadGoodsBarCordMessage()
	    {
	    		
	       	try
			{
		       /* var gridlen=commoninfodivdetial_barcode.rows.length*1;
		        if(gridlen==0)
				{
					addDetialBarnoRecord();
					gridlen=gridlen+1;
				} 
				if(checkNull(commoninfodivdetial_barcode.getRow(0).sendgoodsno)!="")
				{
					addDetialBarnoRecord();
					gridlen=gridlen+1;
				}
				var curDetialbarRecord=commoninfodivdetial_barcode.getRow(gridlen-1);
			
				commoninfodivdetial_barcode.updateRow(curDetialbarRecord,{sendgoodsno: document.getElementById("winsergoodsno").value
															  				,frombarcode : document.getElementById("wfrombarcode").value
															  				,tobarcode : document.getElementById("wtobarcode").value
															  				,sendbarcount : ForDight(document.getElementById("winsergoodscount").value*1,1)});  
				 */
				var barInfo = new LocalDetialBarInfo(document.getElementById("winsergoodsno").value,document.getElementById("wfrombarcode").value,document.getElementById("wtobarcode").value,ForDight(document.getElementById("winsergoodscount").value*1,1));
				if(lsDetialBarInfo==null)
				{
					lsDetialBarInfo=new Array();
				}
				lsDetialBarInfo.length++;
     			lsDetialBarInfo[lsDetialBarInfo.length-1]=barInfo;
     			     
				document.getElementById("winsergoodsno").value="";
				document.getElementById("winsergoodsname").value="";
				document.getElementById("winsergoodsunit").value="";
				document.getElementById("winsergoodsprice").value="";
				document.getElementById("wfrombarcode").value="";
				document.getElementById("wtobarcode").value="";
				document.getElementById("wgoodsuniquebar").value="";
				document.getElementById("winsergoodscount").value=0;
				document.getElementById("winsergoodsno").focus();
				document.getElementById("winsergoodsno").select();
	        }
			catch(e)
			{
				alert(e.message);
			}
	    }
  
    	function printReport()
		{
			var specailurl=contextURL+'/InvoicingControl/IC010/ic010_print.jsp';
		 	window.open(specailurl,"","top=" +2+ ",left=" + 100+",width=800,height=600,toolbar =no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
		}
		
		function printSendReport()
		{
			var specailurl=contextURL+'/InvoicingControl/IC010/ic010_Sendprint.jsp';
		 	window.open(specailurl,"","top=" +2+ ",left=" + 100+",width=800,height=600,toolbar =no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
		}
		
		function validateOneBar(obj)
		{
			if(obj.value!="")
			{
				document.getElementById("onebarNo").readOnly="readOnly";
				document.getElementById("onebarNo").style.background="#EDF1F8";
				var requestUrl ="ic010/validateToBarNo.action";
        		var params="strCurCompId="+document.getElementById("sendcompid").value;
        		params=params+"&strCurGoodsId="+document.getElementById("winsergoodsno").value;
        		params=params+"&strStartBarNo="+obj.value;
        		params=params+"&strEndBarNo="+obj.value;
        		params=params+"&isOneBarFlag=1";
				var responseMethod="validateOneBarMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params ); 	
			}
		}
    
    	function validateOneBarMessage(request)
	    {
		 	try
			{
		       	var responsetext = eval("(" + request.responseText + ")");
		    
		       	var strMessage=responsetext.strMessage;
		       	if(checkNull(strMessage)=="")
		       	{	       		 
					
		       		if(responsetext.goodsbarcount*1>0)
		       		{
		       			var wfrombarcode=document.getElementById("onebarNo").value;
		       			var wtobarcode=document.getElementById("onebarNo").value;
		       			var winsergoodsno=responsetext.strCurGoodsId;
		       			
		       			//验证列表
		       			/*var maxlength=commoninfodivdetial_barcode.rows.length;
		       			for (var i=0;i<maxlength*1;i++)
						{
							
						 	var row =commoninfodivdetial_barcode.getRow(i);
						 	if(winsergoodsno!=checkNull(row.sendgoodsno))
						 	{
						 		continue;
						 	}
						 	if(checkNull(row.frombarcode)!="")
						 	{
						 		if(wtobarcode>=checkNull(row.frombarcode) && wtobarcode<=checkNull(row.tobarcode))
						 		{
						 			
					    			document.getElementById("onebarNo").value="";
					    			document.getElementById("onebarNo").focus();
									document.getElementById("onebarNo").select();
									$.ligerDialog.error("该条码和发货条码明细中的重复,请确认!");
									return;
						 		}
						 	}
						 
						}*/
						var maxlength=0;
						if(lsDetialBarInfo!=null)
						{
							 maxlength=lsDetialBarInfo.length;
						}
					    for (var i=0;i<maxlength*1;i++)
					    {
							if(winsergoodsno!=checkNull(lsDetialBarInfo[i].sendgoodsno))
						 	{
						 		continue;
						 	}
						 	if(checkNull(lsDetialBarInfo[i].frombarcode)!="")
						 	{
						 		if(wtobarcode>=checkNull(lsDetialBarInfo[i].frombarcode) && wtobarcode<=checkNull(lsDetialBarInfo[i].tobarcode))
						 		{
						 			
					    			document.getElementById("onebarNo").value="";
									$.ligerDialog.error("该条码和发货条码明细中的重复,请确认!");
									document.getElementById("onebarNo").readOnly="";
									document.getElementById("onebarNo").style.background="#FFFFFF";
									
									return;
						 		}
						 	}
						}
						var checkFlag=0;
						maxlength=commoninfodivdetial.rows.length;
		       			for (var i=0;i<maxlength*1;i++)
						{
							var row =commoninfodivdetial.getRow(i);
						 	if(checkNull(row.sendgoodsno)==winsergoodsno)
						 	{
						 		checkFlag=1;
						 		var downcount=checkNull(row.downordercount)*1;
						 		var dsendgoodscount=checkNull(row.sendgoodscount)*1;
						 		if(downcount<dsendgoodscount*1+responsetext.goodsbarcount*1)
						 		{
						 			
					    			document.getElementById("onebarNo").value="";
					    			//document.getElementById("onebarNo").focus();
									//document.getElementById("onebarNo").select();
									$.ligerDialog.error(row.sendgoodsname+"条码数量超过了该产品的下单数量!");
									document.getElementById("onebarNo").readOnly="";
									document.getElementById("onebarNo").style.background="#FFFFFF";
									
									return;
						 		}
						 		else
						 		{
						 			commoninfodivdetial.updateRow(row,{sendgoodscount : ForDight(dsendgoodscount*1+responsetext.goodsbarcount*1,1),
						 											   sendgoodsamt: ForDight((dsendgoodscount*1+responsetext.goodsbarcount*1)*row.sendgoodprice*1,1) });  
								}
								break;
						 	}
						}
					
						if(checkFlag==0)
						{
							document.getElementById("onebarNo").value="";
							$.ligerDialog.error("该条码号段对应的产品在发货条码明细中不存在,请确认!");
							document.getElementById("onebarNo").readOnly="";
							document.getElementById("onebarNo").style.background="#FFFFFF";
							return;
						}
						loadGoodsOneBarCordMessage(winsergoodsno,document.getElementById("onebarNo").value ,1);
					
						if(responsetext.lsDgoodsinsertpc.length>0)
						{
							//loadOutPc(responsetext.lsDgoodsinsertpc);
							
						}
					
		       		}
		       	}
		       	else
		       	{
		       		$.ligerDialog.warn(strMessage);
		       		document.getElementById("onebarNo").value="";
		       		
		       	}
		       	document.getElementById("onebarNo").readOnly="";
		       	document.getElementById("onebarNo").style.background="#FFFFFF";
		       	document.getElementById("onebarNo").value="";
		       	document.getElementById("onebarNo").focus();
				document.getElementById("onebarNo").select();
	        }
			catch(e)
			{
				alert(e.message);
			}
	   	}
	   	
	   	function loadGoodsOneBarCordMessage(goodsno,goodsbarno,goodscount)
	    {
	    		
	       	try
			{
				var barInfo = new LocalDetialBarInfo(goodsno,goodsbarno,goodsbarno,1);
				if(lsDetialBarInfo==null)
				{
					lsDetialBarInfo=new Array();
				}
				lsDetialBarInfo.length++;
     			lsDetialBarInfo[lsDetialBarInfo.length-1]=barInfo;
		        /*var gridlen=commoninfodivdetial_barcode.rows.length*1;
		        if(gridlen==0)
				{
					addDetialBarnoRecord();
					gridlen=gridlen+1;
				} 
			
				if(checkNull(commoninfodivdetial_barcode.getRow(0).sendgoodsno)!="")
				{
					addDetialBarnoRecord();
					gridlen=gridlen+1;
				}
			
				var curDetialbarRecord=commoninfodivdetial_barcode.getRow(gridlen-1);
			
				commoninfodivdetial_barcode.updateRow(curDetialbarRecord,{sendgoodsno: goodsno
															  				,frombarcode : goodsbarno
															  				,tobarcode : goodsbarno
															  				,sendbarcount :1 });  
				 */     
	        }
			catch(e)
			{
				alert(e.message);
			}
	    }
	    
	    

function addcloud() { 
//var bodyWidth = document.documentElement.clientWidth; 
var bodyWidth=100;
//var bodyHeight = Math.max(document.documentElement.clientHeight, document.body.scrollHeight); 
var bodyHeight=100;
var bgObj = document.createElement("div" ); 
bgObj.setAttribute( 'id', 'bgDiv' ); 
bgObj.style.position = "absolute"; 
bgObj.style.top = "0"; 
bgObj.style.background = "#000000"; 
bgObj.style.filter = "progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0,finishOpacity=0" ; 
//bgObj.style.opacity = "0.5"; 
//bgObj.style.left = "1"; 
bgObj.style.width = bodyWidth + "%"; 
bgObj.style.height = bodyHeight + "%"; 
//bgObj.style.zIndex = "100000"; //设置它的zindex属性，让这个div在z轴最大，用户点击页面任何东西都不会有反应| 
document.body.appendChild(bgObj); //添加遮罩 
var loadingObj = document.createElement("div"); 
loadingObj.setAttribute( 'id', 'loadingDiv' ); 
loadingObj.style.position = "absolute"; 
loadingObj.style.top = 30+"%"; 
loadingObj.style.left = 40+"%"; 
loadingObj.style.background = "url(images/load.gif)" ; 
loadingObj.style.width = "250"; 
loadingObj.style.height = "250"; 
//loadingObj.style.zIndex = "100000"; 
document.body.appendChild(loadingObj); //添加loading动画- 
} 
function removecloud() { 
$( "#loadingDiv").remove(); 
$( "#bgDiv").remove(); 
} 
