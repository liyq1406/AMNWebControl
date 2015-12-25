	var commoninfodivmaster=null;	//产品入库主档列表
   	var commoninfodivdetial=null;	//产品入库明细列表
   	var commoninfodivdetial_barcode=null; //条码卡列表
   	var strCurCompId="";
   	var strCurBillId="";
   	var curMasterInfoDate=null;
   	var fromValidate=null;
   	var cinsertwaremanager = null;
   	var cinsertcardtypemanager = null;
   	var cinsertpermanager = null;
   	var ic001layout=null;
   	var curpagestate=3; // 1 add 2 modify 3 browse
   	var insertypemanager=null;
	var curDetialRecord = null;
   	var insertypeData_s=JSON.parse(parent.loadCommonControlDate_select("RKFS",0));
   	var showDialogmanager=null;
    var rightmenu;
    var iGoodsBarCode="";
    var istartBarCode="";
    var iendBarCode="";
    var printgoodsname="";
    var printgoodscount="";
    var confirmInfo=null;
    var cancelInfo=null;
    var goodsdilog=null;
    var goodsdilogText="";
    var lsDgoodsinserts=null;
    var shownewSendDialogmanager=null;
    var postState=0;
   	//初始化属性
    $(function ()
   	{
	   try
	   {  
	   		//布局
            ic001layout= $("#ic001layout").ligerLayout({ rightWidth: 400,  allowBottomResize: false, allowLeftResize: false,isRightCollapse:true });
	   		var height = $(".l-layout-center").height();
           	commoninfodivmaster=$("#commoninfodivmaster").ligerGrid({
                columns: [
                { 	name: 'binsercompid', hide:true	},
                { display: '入库单号', 	name: 'binserbillid', 	width:120,align: 'left'},
                { display: '入库方式', 	name: 'insertype', 		width:120,align: 'left',
	                	editor: { type: 'select', data: insertypeData_s, valueField: 'choose',selectBoxHeight:'220'},
		            	render: function (item)
		              	{
		              		var lsZw=parent.gainCommonInfoByCode("RKFS",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.insertype)
								{	
									return lsZw[i].parentcodevalue;								
							    }
							}
		                    return '';
		                } 
		        },
	            { display: '入库日期', 	name: 'inserdate', 		width:80,align: 'left'}   
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '340',
                height:'675',
                clickToEdit: false,   enabledEdit: false,  
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                }, 
                toolbar: { items: [
	                { text: '增加', click: itemclick_cardInsertInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
	           		{ text: '保存', click: itemclick_cardInsertInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' },
					{ text: '删除', click: itemclick_cardInsertInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' },
	                { text: '刷新', click: itemclick_cardInsertInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/refresh.gif' } 
					]
                }	   
            });
           
        	menu = $.ligerMenu({ width: 120, items:
	            [
	            	{ text: '新增赠品', click: addSendGoodsInfo, icon: 'add' },
	              	{ line: true },
	            	{ text: '打印条形码', click: printBarcode, icon: 'add' }
	            ]
	            }); 
        
        	commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
                columns: [
                { display: '产品编号', 	name: 'insergoodsno',  			width:110,align: 'left' },
                { display: '产品名称', 	name: 'insergoodsname',  		width:150,align: 'left' },
                { display: '单位', 		name: 'inserunit', 				width:50,align: 'left'},
	            { display: '数量', 		name: 'insercount', 			width:50,align: 'right'},
	            { display: '单价', 		name: 'goodsprice', 			width:50,align: 'right' },
	            { display: '进货金额', 	name: 'goodsamt', 				width:60,align: 'right' }  ,
	            { display: '规格', 		name: 'producenorm', 			width:80,align: 'left' }  ,
	            { display: '生产日期', 	name: 'producedate', 			width:100,align: 'left', type: 'date', editor: { type: 'date'} }  ,
	            { display: '过期日期', 	name: 'produceenddate', 		width:100,align: 'left', type: 'date', editor: { type: 'date'} }  ,
	            { display: '起始编码', 	name: 'frombarcode', 			width:100,align: 'left' }  ,   
	            { display: '结束编码', 	name: 'tobarcode', 				width:100,align: 'left' } ,
	            {  hide:true ,	name: 'standunit', width:1},
	            {  hide:true ,	name: 'standprice', width:1} ,
	            {  hide:true ,	name: 'strBarSeqno', width:1}       
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '900',
                height:'500',
                clickToEdit: true,   enabledEdit: true,  checkbox:true,
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    curDetialRecord=data;
                } , onRClickToSelect:true,
                onContextmenu : function (parm,e)
                {
                	 istartBarCode=parm.data.frombarcode;
    				 iendBarCode=parm.data.tobarcode;
    				 iGoodsBarCode=parm.data.insergoodsno;
    				 printgoodsname=parm.data.insergoodsname;
    				 printgoodscount=parm.data.insercount;
                     menu.show({ top: e.pageY, left: e.pageX });
                     return false;
                } ,
                 toolbar: { items: [
                 { text: '打印', click: printReport, img: contextURL+'/common/ligerui/ligerUI/skins/icons/print.gif' },
                 { text: '<input type="radio" id="printtype1" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="printtype" value="1" />大机器<input type="radio" id="printtype2" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="printtype" value="2"/>小机器<input type="radio" id="printtype3" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="printtype" value="3" checked="true"/>单列纸大机器'} 
                ]
                }
            });
            
          	$('#thetable').tableScroll({
				width:280,
				height:620
			});
			
			 var  insertypeData=JSON.parse(parent.loadCommonControlDate("RKFS",0));
           	 insertypemanager = $("#insertype").ligerComboBox({ data: insertypeData, isMultiSelect: false,valueFieldID: 'factinsertype',width:'140' });           	 
           	
           	 $("#searchButton").ligerButton(
	         {
	             text: '查询单据', width: 100,
		         click: function ()
		         {
		             searchOrderBill();
		         }
	         });
           	 confirmInfo=$("#confirmInfo").ligerButton(
	         {
	             text: '复合入库', width: 160,
		         click: function ()
		         {
		             handconfirmInfo();
		         }
	         });
	         cancelInfo=$("#cancelInfo").ligerButton(
	         {
	             text: '取消复合', width: 160,
		         click: function ()
		         {
		             handcancelInfo();
		         }
	         });
          	$("#pageloading").hide(); 
          	addMasterRecord();
          	addDetialRecord();
          	loadCardInsertList();
          	
   		}catch(e){alert(e.message);}
    });
    //加载打印
    function loadpringt()
    { 
    	 var params ="strCurCompId="+ document.getElementById("insercompid").value;
        	 params=params+"&strCurBillId="+document.getElementById("inserbillid").value;
     	var requestUrl ="ic001/loadDgoodsinsertss.action"; 
		var responseMethod="loadCardprint";				
		sendRequestForParams_p(requestUrl,responseMethod,params);
    }
   
   function loadCardprint(request)
   {
        	var responsetext = eval("(" + request.responseText + ")");
	   			lsDgoodsinserts=responsetext.lsDgoodsinsert;
	}
    function loadCardInsertList()
    {
     	var requestUrl ="ic001/loadCardInsertInfo.action"; 
		var responseMethod="loadCardInsertInfoMessage";				
		sendRequestForParams_p(requestUrl,responseMethod,"" );
    }
   
   function loadCardInsertInfoMessage(request)
   {
       		try
        	{
 
        	var responsetext = eval("(" + request.responseText + ")");
    
	   		if(responsetext.lsMgoodsinsert!=null && responsetext.lsMgoodsinsert.length>0)
	   		{
	   			curMasterInfoDate={Rows: responsetext.lsMgoodsinsert,Total: responsetext.lsMgoodsinsert.length};
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
			
			
	 
	   		//仓库选择框
	   		var returnValue='';
	   		if(responsetext.lsCompwarehouses!=null && responsetext.lsCompwarehouses.length>0)
	   		{
	   			for(var i=0;i<responsetext.lsCompwarehouses.length;i++)
				{
				    
					if(returnValue!='')
					{
						returnValue=returnValue+',';
					}
					else
					{
						returnValue=returnValue+'[';
					}
					returnValue=returnValue+'{"id": "'+responsetext.lsCompwarehouses[i].id.warehouseno+'","text": "'+responsetext.lsCompwarehouses[i].warehousename+'"}';
				}
	   		}
	   		if(returnValue!='')
	   		{
	   			
				returnValue=returnValue+']';	
				
		   		cinsertwaremanager= $("#inserwareid").ligerComboBox({  data: JSON.parse(returnValue)
	                , valueFieldID: 'factcinserwareid',label:'请选仓库',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220'
	            }); 
	        }
	        else
	        {
	        	cinsertwaremanager= $("#inserwareid").ligerComboBox({  data: null , valueFieldID: 'factcinserwareid',label:'选择仓库',labelWidth:200,labelAlign:'center',width:'120',selectBoxHeight:'220'
	            }); 
	        }
	        
	   		}catch(e){alert(e.message);}
    }
    
    function loadCurMaster(curMaster)
    {
		try
		{ 
	    	document.getElementById("insercompid").value=checkNull(curMaster.binsercompid);
	    	document.getElementById("inserbillid").value=checkNull(curMaster.binserbillid);
	    	document.getElementById("inserdate").value=checkNull(curMaster.inserdate);
	    	document.getElementById("insertime").value=checkNull(curMaster.insertime);
	    	document.getElementById("inserstaffid").value=checkNull(curMaster.inserstaffid);
	    	document.getElementById("binsercompname").value=checkNull(curMaster.binsercompname);
	    	document.getElementById("inserstaffname").value=checkNull(curMaster.inserstaffname);
	    	insertypemanager.selectValue(checkNull(curMaster.insertype));
	    	cinsertwaremanager.selectValue(checkNull(curMaster.inserwareid));
	    	handleRadio("curMcardnoinsert.checkbillflag",checkNull(curMaster.checkbillflag));
	    	document.getElementById("checkbillno").value=checkNull(curMaster.checkbillno);
	    	document.getElementById("inseropationerid").value=checkNull(curMaster.inseropationerid);
	    	document.getElementById("inseropationdate").value=checkNull(curMaster.inseropationdate);
			document.getElementById("winsergoodsname").value="";
			document.getElementById("winsergoodsunit").value="";
			document.getElementById("winsergoodsprice").value="";
			document.getElementById("winsergoodscount").value="";
			document.getElementById("winsergoodsno").value="";
			document.getElementById("wfrombarcode").value="";
			document.getElementById("wtobarcode").value="";
			document.getElementById("wstandunit").value="";
			document.getElementById("wstandprice").value="";
			document.getElementById("wgoodsuniquebar").value="";
			loadpringt();
			if(document.getElementById("inseropationerid").value=="")
			{
				pageWriteState();
				curpagestate=1;
			}
			else
			{
				pageReadState();
			}
			if(document.getElementById("inseropationerid").value=="")
			{
				confirmInfo.setEnabled();
				cancelInfo.setDisabled();
			}
			else
			{
				confirmInfo.setDisabled();
				cancelInfo.setEnabled();
			}
			
		}catch(e){alert(e.message);}
    }
    
    function pageWriteState()
    {
	    try
	    {
	    	document.getElementById("inserstaffid").readOnly="";
	   		insertypemanager.setEnabled();
	   		cinsertwaremanager.setEnabled();
	    	document.getElementById("checkbillno").readOnly="";
	    	document.getElementById("winsergoodsno").readOnly="";
	    	document.getElementById("winsergoodscount").readOnly="";
	    	commoninfodivdetial.options.enabledEdit=true;
    	}catch(e){alert(e.message);}
	}
    
    function pageReadState()
    {
    	try
	    {
	    	document.getElementById("inserstaffid").readOnly="readOnly";
	   		insertypemanager.setDisabled();
	   		cinsertwaremanager.setDisabled();
	    	document.getElementById("checkbillno").readOnly="readOnly";
	    	document.getElementById("winsergoodsno").readOnly="readOnly";
	    	document.getElementById("winsergoodscount").readOnly="readOnly";
	    	commoninfodivdetial.options.enabledEdit=false;
    	}catch(e){alert(e.message);}
    }
    
    //根据主档选择加载明细
    function loadSelecDetialData(data, rowindex, rowobj)
    {
        try{
        	
        		 if(curpagestate!=3)
        		 {
        		 	$.ligerDialog.warn("请保持当前信息或刷新本界面后在进行切换操作!");
        		 	return ;
        		 }
        		 strCurCompId=data.binsercompid;
        		 strCurBillId=data.binserbillid;
       			 var params = "strCurCompId="+strCurCompId;
       			 params =params+ "&strCurBillId="+strCurBillId;
				 var myAjax = new parent.Ajax.Request(
						"ic001/loadDcardnoinsert.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {							
								var responsetext = eval( "("+request.responseText+")");	
								if(responsetext.lsDgoodsinsert!=null && responsetext.lsDgoodsinsert.length>0)
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: responsetext.lsDgoodsinsert,Total: responsetext.lsDgoodsinsert.length});
					            	commoninfodivdetial.loadData(true);     
					            	document.getElementById("needPost").value="0";        	
						   		}
						   		else
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
					            	commoninfodivdetial.loadData(true);  
					            	addDetialRecord();
					            	document.getElementById("needPost").value="1";  
						   		}
	   							loadCurMaster(responsetext.curMgoodsinsert);
							},
							asynchronous:true
						});			  
          }catch(e){alert(e.message);}
    }
    //------------------------主档按钮操作-------------------Start
    function itemclick_cardInsertInfo(item)
    {
    	try
    	{
	    	 $.ligerDialog.confirm('确认提交 '+strCurCompId+' 产品入库 '+item.text+' 操作', function (result)
		     {
		     	if( result==true)
            	{
		          	if(item.text=="增加")
		        	{
		        		 if(curpagestate!=3)
		        		 {
		        		 	$.ligerDialog.warn("请保存当前信息或刷新本界面后在进行新增操作!");
		        		 	return ;
		        		 }
		        		curpagestate=1;
		        	 	addRecord();
		        	 	pageWriteState();
		        	}
		        	else if(item.text=="保存")
		        	{
		        		 if(curpagestate==3)
		        		 {
		        		 	$.ligerDialog.warn("未保存数据,无需保存!");
		        		 	return ;
		        		 }
		        	 	editCurRecord();
		        	} 	
		        	else if(item.text=="删除")
		        	{
		        		 if(curpagestate!=3)
		        		 {
		        		 	$.ligerDialog.warn("请保存当前信息或刷新本界面后在进行刷新操作!");
		        		 	return ;
		        		 }
		        	 	deleteCurRecord();
		        	} 
		        	else if(item.text=="刷新")
		        	{
		        		loadCardInsertList();
		        	} 		
		        	
		        }
		      });  
    	}
    	catch(e){alert(e.message);}
    }
    
    function editCurRecord()
    {
    	 showDialogmanager = $.ligerDialog.waitting('正在保存中,请稍候...');	
    	 curpagestate=3;
    	if(postState==1)
		{
			$.ligerDialog.error("正在保存中,请不要连续保存!");
			postState=0;
			return ;
		}
   		postState=1;
    	if(parent.hasFunctionRights( "IC001",  "UR_POST")!=true)
        {
       		$.ligerDialog.warn("该用户没有保存权限,请确认!");
       		postState=0;
       		showDialogmanager.close();
       		curpagestate=2;
       	 	return;
        }
        if(document.getElementById("needPost").value!="1")
        {
       		$.ligerDialog.error("该单据只能复合，不能保存,请确认!");
       		postState=0;
       		showDialogmanager.close();
       		curpagestate=2;
       	 	return;
        }
	    if(document.getElementById("inserstaffid").value=="")
		{
			$.ligerDialog.error('请输入入库员工!');
			postState=0;
			showDialogmanager.close();
			curpagestate=2;
			return;
		}
		if($("#factinsertype").val()=="")
		{
			$.ligerDialog.error('请选择入库方式!');
			postState=0;
			showDialogmanager.close();
			curpagestate=2;
			return;
		}
		if($("#factcinserwareid").val()=="")
		{
			$.ligerDialog.error('请选择入库仓库!');
			postState=0;
			showDialogmanager.close();
			curpagestate=2;
			return;
		}
		document.getElementById("inserwareid_h").value=$("#factcinserwareid").val();
		document.getElementById("insertype_h").value=$("#factinsertype").val();
    	var queryStringTmp=$('#goodsInsertForm').serialize();
		var requestUrl ="ic001/post.action";
		var params=queryStringTmp;
		
		var curjosnparam="";
        var needReplaceStr="";
        var strJsonParam_detial="";
        //------卡号段列表
        for (var rowid in commoninfodivdetial.records)
		{
				var row =commoninfodivdetial.records[rowid];
				if(checkNull(row.insergoodsno)=="")
					continue;
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
			
		var responseMethod="postMessage";
	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
    function postMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("保存成功!");
	        	 curpagestate=3;
	        	 pageReadState();
	        	 confirmInfo.setDisabled();
				 cancelInfo.setEnabled();
	        }
	        else
	        {
	        	alert(strMessage);
	        }
	        postState=0;
	        showDialogmanager.close();
        }
		catch(e)
		{
			alert(e.message);
		}
    }
     
			 
     //新增行
     function addRecord()
     {
     		if(parent.hasFunctionRights( "IC001",  "UR_MODIFY")!=true)
	        {
	        	$.ligerDialog.warn("该用户没有编辑权限,请确认!");
	        }
	        else
	        {
	        	var row = commoninfodivmaster.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
				   commoninfodivmaster.addRow({ 
				                binserbillid: "",
				                insertype: "",
				                inserdate: "",
				                binsercompid: ""
				             
				            }, row, false);
				var params = "";			
    	     	var requestUrl ="ic001/add.action"; 
				var responseMethod="addMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params );
	        }
     }
     function addMessage(request)
     {
	    	try
	        {
	        	var responsetext = eval("(" + request.responseText + ")");
	    		loadCurMaster(responsetext.curMgoodsinsert);
		   		commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivdetial.loadData(true);  
            	addDetialRecord();
            	document.getElementById("needPost").value="1";  
		   	}catch(e){alert(e.message);}
     }
     
    //删除行
    function deleteCurRecord()
    {
       	if(parent.hasFunctionRights( "IC001",  "UR_DELETE")!=true)
        {
        	 $.ligerDialog.warn("该用户没有删除权限,请确认!");
        	 return;
        }
        var requestUrl ="ic001/delete.action";
        var params="strCurCompId="+strCurCompId;
        params=params+"&strCurBillId="+strCurBillId;
		var responseMethod="deleteMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	   
    }
   	function deleteMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		  commoninfodivmaster.deleteSelectedRow();
	       		  $.ligerDialog.success("删除成功!");
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
   //------------------------主档按钮操作-------------------End
    
  //-------------------------查询信息 ---------------------Start
     function f_searchCardInsertInfo()
     {
    
     	  
     }
   //--------------------------验证验货人员-----------------
   function validateInserper(obj)
   {
   		if(obj.value=="")
   		{
   			document.getElementById("inserstaffname").value="";
   			return ;
   		}
   		var requestUrl ="ic001/validateInserper.action";
        var params="strCurCompId="+document.getElementById("insercompid").value;
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
	       		document.getElementById("inserstaffname").value=responsetext.strCurEmpName;
	       	}
	       	else
	       	{
	       		document.getElementById("inserstaffname").value="";
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
				          
			}, row, false);
    }
    
    function addDetialRecord()
    {
    		var row = commoninfodivdetial.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivdetial.addRow({ 
				      insergoodsno: "",
				      insergoodsname: "",
				      inserunit: "",
				      insercount: "" ,
				      goodsprice: "",
				      goodsamt: "",
				      producenorm: "",
				      producedate: "",
				      frombarcode: "",
				      tobarcode:  "" ,
				      standunit:  "", 
				      standprice: "", 
				      strBarSeqno: ""
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
				    
			}, row, false);
    }
    
  
    function validateFromBarno(obj)
    {
    	alert(obj.value);
    }
	function validateToBarno(obj)
    {
    	alert(obj.value);
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
			else if(key==115)//F4
			{
				if(curpagestate==3)
		        {
		 			$.ligerDialog.warn("非新增操作,不可编辑入库明细!");
		    		return ;
		    	}
				 deletedetialRecord();
			}
			else if( key == 83 &&  event.altKey)
			{
				if(curpagestate==3)
		        {
		        	$.ligerDialog.warn("未保存数据,无需保存!");
		        	return ;
		        }
				editCurRecord();
			}
	
		}
		
		function  hotKeyEnter()
		{
		
			try
			{
				var fieldName = document.activeElement.name;
				var fieldId = document.activeElement.id ;
				if(fieldId=="winsergoodsno")
				{
					document.activeElement.onchange();
				}
				else
				{
					window.event.keyCode=9; //tab
					window.event.returnValue=true;
				}
				
			}
			catch(e){alert(e.message);}
				
		}   
		
		function validateinserno(obj)
		{
			if(document.getElementById("inserstaffid").value=="")
	        {
	        
	        	 obj.value="";
	        	 document.getElementById("storesendbill").focus();
				 document.getElementById("storesendbill").select();
				 $.ligerDialog.error("请确认入库员工!");
	        	 return;
	        }
	        if($("#factcinserwareid").val()=="")
	        {
	        	
	        	 obj.value="";
	        	 document.getElementById("storesendbill").focus();
				 document.getElementById("storesendbill").select();
				  $.ligerDialog.error("请确认入库仓库!");
	        	 return;
	        }
	        if($("#factinsertype").val()=="")
	        {
	        	
	        	  obj.value="";
	        	 document.getElementById("storesendbill").focus();
				 document.getElementById("storesendbill").select();
				  $.ligerDialog.error("请确认入库方式!");
	        	 return;
	        }
			if(obj.value=="")
			{
				document.getElementById("winsergoodsname").value="";
				document.getElementById("winsergoodsunit").value="";
				document.getElementById("winsergoodsprice").value="";
				document.getElementById("wgoodsformat").value="";
				document.getElementById("winsergoodscount").value="0";
				document.getElementById("wfrombarcode").value="";
				document.getElementById("wtobarcode").value="";
				document.getElementById("wgoodsuniquebar").value="";
				document.getElementById("wstandunit").value="";
				document.getElementById("wstandprice").value="";
				obj.focus();
				obj.select();
			}
			else
			{
				var checkflag=0;
				var goodsbarno="";
				var strgoodsbarno=null;
				var goodsBarFlag=0;
				if(parent.lsGoodsinfo!=null && parent.lsGoodsinfo.length>0)
				{
					for(var i=0;i<parent.lsGoodsinfo.length;i++)
					{
						if( parent.lsGoodsinfo[i].id.goodsno==obj.value
						|| checkNull(parent.lsGoodsinfo[i].goodsabridge).toLowerCase()==obj.value.toLowerCase())
						{
							if(checkNull(parent.lsGoodsinfo[i].useflag)==1)
							{
	        	 				$.ligerDialog.warn("产品!"+parent.lsGoodsinfo[i].goodsname+"已停用");
	        	 				obj.focus();
								obj.select();
	        	  				obj.value="";
	        	 				return ;
	        				}
							document.getElementById("winsergoodsname").value=checkNull(parent.lsGoodsinfo[i].goodsname);
							document.getElementById("winsergoodsunit").value=checkNull(parent.lsGoodsinfo[i].purchaseunit);
							document.getElementById("winsergoodsprice").value=ForDight(parent.lsGoodsinfo[i].purchaseprice,2);
							document.getElementById("wgoodsformat").value=checkNull(parent.lsGoodsinfo[i].goodsformat);
							document.getElementById("wgoodsuniquebar").value=checkNull(parent.lsGoodsinfo[i].goodsuniquebar);
							document.getElementById("wstandunit").value=checkNull(parent.lsGoodsinfo[i].saleunit);
							document.getElementById("wstandprice").value=ForDight(parent.lsGoodsinfo[i].standprice,2);
							document.getElementById("winsergoodscount").value=0;
							document.getElementById("winsergoodscount").focus();
							document.getElementById("winsergoodscount").select();
							checkflag=1;
							break;
						}
						goodsbarno=checkNull(parent.lsGoodsinfo[i].goodsbarno);
						if(goodsbarno!="")
						{
							
							strgoodsbarno= new Array(); 
						
							strgoodsbarno=goodsbarno.split(";");  
							goodsBarFlag=0;
								
							for (j=0;j<strgoodsbarno.length ;j++ )   
							{
								if(strgoodsbarno[j]==obj.value)
								{
									goodsBarFlag=1;
									document.getElementById("winsergoodsno").value=checkNull(parent.lsGoodsinfo[i].id.goodsno);
									document.getElementById("winsergoodsname").value=checkNull(parent.lsGoodsinfo[i].goodsname);
									document.getElementById("winsergoodsunit").value=checkNull(parent.lsGoodsinfo[i].purchaseunit);
									document.getElementById("wgoodsformat").value=checkNull(parent.lsGoodsinfo[i].goodsformat);
									document.getElementById("winsergoodsprice").value=ForDight(checkNull(parent.lsGoodsinfo[i].purchaseprice),2);
									document.getElementById("wstandunit").value=checkNull(parent.lsGoodsinfo[i].saleunit);
									document.getElementById("wstandprice").value=ForDight(parent.lsGoodsinfo[i].standprice,2);
									document.getElementById("wgoodsuniquebar").value=checkNull(parent.lsGoodsinfo[i].goodsuniquebar);
									document.getElementById("winsergoodscount").value=0;
									document.getElementById("winsergoodscount").focus();
									document.getElementById("winsergoodscount").select();
									break;
								}
							}
							if(goodsBarFlag==1)
							{
								checkflag=1;
								break;
							}
						}		 
					}
				}
				if(checkflag==0)
				{
					$.ligerDialog.warn("产品编号不存在或已停用");
	        	 	document.getElementById("winsergoodsno").focus();
					document.getElementById("winsergoodsno").select();
	        	 	obj.value="";
	        	  
				}
			}
		}
		
		function loadGoodsGrid(obj)
		{
			if(document.getElementById("winsergoodsno").value=="")
			{
				$.ligerDialog.warn("请确认入库产品编号!");
	        	document.getElementById("winsergoodsno").focus();
				document.getElementById("winsergoodsno").select();
				obj.value=0;
	        	 return;
			}
			if( ! isNumber(obj.value) )
		    {
				alert( " 输入有误!" );
				obj.value=0;
				obj.focus();
				obj.select();
				return;
			}
			try
			{
				if(document.getElementById("wgoodsuniquebar").value=="")
				{
					$.ligerDialog.warn("该产品没有设置条码前缀,不能生成!");
					return;
				}
				if(obj.value*1==0)
				{
					$.ligerDialog.warn("请确认入库数量!");
					return;
				}
				var requestUrl ="ic001/loadGoodsBarCord.action";
        		var params="strCurGoodsId="+document.getElementById("winsergoodsno").value;
        		params=params+"&barcodeCount="+document.getElementById("winsergoodscount").value;
        		params=params+"&strIndexBar="+document.getElementById("wgoodsuniquebar").value;
				var responseMethod="loadGoodsBarCordMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params ); 
				
				
			}catch(e){alert(e.message);}
		}
		
		function loadGoodsBarCordMessage(request)
	    {
	    		
	       	try
			{
		        var responsetext = eval("(" + request.responseText + ")");
		        document.getElementById("wfrombarcode").value= checkNull(responsetext.strStartBarNo);
		        document.getElementById("wtobarcode").value=checkNull(responsetext.strEndBarNo);
		        var gridlen=commoninfodivdetial.rows.length*1;
				if(gridlen==0)
				{
					addDetialRecord();
					gridlen=gridlen+1;
				} 
				if(checkNull(commoninfodivdetial.getRow(0).insergoodsname)!="")
				{
					addDetialRecord();
					gridlen=gridlen+1;
				}
				curDetialRecord=commoninfodivdetial.getRow(gridlen-1);
				commoninfodivdetial.updateRow(curDetialRecord,{insergoodsno: document.getElementById("winsergoodsno").value
															  ,insergoodsname:document.getElementById("winsergoodsname").value
															  ,inserunit  : document.getElementById("winsergoodsunit").value
															  ,insercount : ForDight(document.getElementById("winsergoodscount").value*1,2)
															  ,goodsprice : ForDight(document.getElementById("winsergoodsprice").value*1,2)
															  ,goodsamt   : ForDight(document.getElementById("winsergoodsprice").value*1*document.getElementById("winsergoodscount").value*1,2)
															  ,producenorm : document.getElementById("wgoodsformat").value
															  ,frombarcode : document.getElementById("wfrombarcode").value
															  ,tobarcode : document.getElementById("wtobarcode").value
															  ,standunit : document.getElementById("wstandunit").value 
															  ,standprice : document.getElementById("wstandprice").value 
															  ,strBarSeqno: document.getElementById("wgoodsuniquebar").value });  
				      
				document.getElementById("winsergoodsno").value="";
				document.getElementById("winsergoodsname").value="";
				document.getElementById("winsergoodsunit").value="";
				document.getElementById("winsergoodsprice").value="";
				document.getElementById("wgoodsformat").value="";
				document.getElementById("wfrombarcode").value="";
				document.getElementById("wtobarcode").value="";
				document.getElementById("wgoodsuniquebar").value="";
				document.getElementById("wstandunit").value="";
				document.getElementById("wstandprice").value="";	
				document.getElementById("winsergoodscount").value=0;
				document.getElementById("winsergoodsno").focus();
				document.getElementById("winsergoodsno").select();
	        }
			catch(e)
			{
				alert(e.message);
			}
	    }
    
    function printBarcode(item, i)
    {
    	$.ligerDialog.confirm('确认打印 '+iGoodsBarCode+' 产品的条码 '+istartBarCode+' 至'+iendBarCode, function (result)
		{
			if(result==true)
            {
            	showDialogmanager = $.ligerDialog.waitting('正在打印中,请稍候...');
            	
            	var pcount=printgoodsname+"("+ForDight(printgoodscount,0);
            	ic001_printCont(1,pcount+")",pcount+")");
            	var targerStart=(istartBarCode+"").substring(0,4);	
            	var startgetbar=(istartBarCode+"").substring(4,12);
            	var endtbar=(iendBarCode+"").substring(4,12);
            	var parentbar="";		
            	while(startgetbar*1<=endtbar*1)
            	{
            		if(startgetbar*1<10)
            			parentbar=targerStart+"0000000"+startgetbar*1;
            		else if(startgetbar*1<100)
            			parentbar=targerStart+"000000"+startgetbar*1;
            		else if(startgetbar*1<1000)
            			parentbar=targerStart+"00000"+startgetbar*1;
            		else if(startgetbar*1<10000)
            			parentbar=targerStart+"0000"+startgetbar*1;
            		else if(startgetbar*1<100000)
            			parentbar=targerStart+"000"+startgetbar*1;
            		else if(startgetbar*1<1000000)
            			parentbar=targerStart+"00"+startgetbar*1;
            		else if(startgetbar*1<10000000)
            			parentbar=targerStart+"0"+startgetbar*1;
            		else 
            			parentbar=targerStart+startgetbar;
            		ic001_printbar(1,parentbar,parentbar);
            		startgetbar=startgetbar*1+1;
            	}
            	ic001_printCont(1,pcount+")",pcount+")");
            	showDialogmanager.close();
            	
            }
		});
    	
    }
    
    function ic001_printbar(count,barcode1,barcode2)
	{
			var strValue="2";
			if(document.getElementById("printtype1").checked==true)
			{
				strValue="1";
			}else if(document.getElementById("printtype3").checked==true){
				strValue="3";
			}
			Stand_CheckPrintControl();
			if(strValue=="2")
			{
				StandPrintControl.PRINT_INITA(0,0,"62mm","15mm","");
			
				StandPrintControl.ADD_PRINT_BARCODE("0.5mm","9.5mm","25.9mm","9.5mm","128Auto",barcode1);
				StandPrintControl.SET_PRINT_STYLEA(0,"FontSize",6);
				
				StandPrintControl.ADD_PRINT_BARCODE("0.5mm","40.5mm","25.9mm","9.5mm","128Auto",barcode2);
				StandPrintControl.SET_PRINT_STYLEA(0,"FontSize",6);	
			}else if(strValue=="3"){
				StandPrintControl.PRINT_INITA(0,0,"62mm","15mm","");
				
				StandPrintControl.ADD_PRINT_BARCODE("1.3mm","0.1mm","25.9mm","9.5mm","128Auto",barcode1);
				StandPrintControl.SET_PRINT_STYLEA(0,"FontSize",9);
			}
			else
			{
				StandPrintControl.PRINT_INITA(0,0,"62mm","15mm","");
				
				StandPrintControl.ADD_PRINT_BARCODE("5mm","2.2mm","25.9mm","9.5mm","128Auto",barcode1);
				StandPrintControl.SET_PRINT_STYLEA(0,"FontSize",9);
				StandPrintControl.SET_PRINT_STYLEA(0,"Vorient",1);
				
				StandPrintControl.ADD_PRINT_BARCODE("5mm","33.2mm","25.9mm","9.5mm","128Auto",barcode2);
				StandPrintControl.SET_PRINT_STYLEA(0,"FontSize",9);
				StandPrintControl.SET_PRINT_STYLEA(0,"Vorient",1);
				
			}
			
			StandPrintControl.PRINT();
			
	}

    function ic001_printCont(count,barcode1,barcode2)
	{
	try
	{
		var strValue="2";
		if(document.getElementById("printtype1").checked==true)
		{
			strValue="1";
		}else if(document.getElementById("printtype3").checked==true){
			strValue="3";
		}
		Stand_CheckPrintControl();
		if(strValue=="2")
		{
			//ADD_PRINT_BARCODE(Top, Left,Width, Height, CodeType, CodeValue)
			StandPrintControl.PRINT_INIT("");
			
			StandPrintControl.ADD_PRINT_TEXT("0.1mm","9.1mm","25.9mm","5.5mm",barcode1);
			StandPrintControl.SET_PRINT_STYLEA(0,"FontSize",8);
			
			StandPrintControl.ADD_PRINT_TEXT("0.1mm","40mm","25.9mm","5.5mm",barcode2);
			StandPrintControl.SET_PRINT_STYLEA(0,"FontSize",8);
			StandPrintControl.PRINT();
		}else if(strValue=="3"){
			StandPrintControl.PRINT_INIT("");
			
			StandPrintControl.ADD_PRINT_TEXT("3mm","2mm","25.9mm","5.5mm",barcode1);
			StandPrintControl.SET_PRINT_STYLEA(0,"FontSize",8);
			StandPrintControl.PRINT();
		}
		else
		{
			
			StandPrintControl.PRINT_INIT("");
			StandPrintControl.SET_PRINT_STYLE("FontName","新宋体");
				
			StandPrintControl.ADD_PRINT_TEXT("1mm","2mm","25.9mm","5.5mm",barcode1);
			StandPrintControl.SET_PRINT_STYLEA(0,"FontSize",8);
			
			StandPrintControl.ADD_PRINT_TEXT("1mm","33mm","25.9mm","5.5mm",barcode2);
			StandPrintControl.SET_PRINT_STYLEA(0,"FontSize",8);
			StandPrintControl.PRINT();
			
		}
		}catch(e){alert(e.message);}
	}
	
	
	
    //复合入库单
    function handconfirmInfo()
    {
    
        if(parent.hasFunctionRights( "IC001",  "UR_POST")!=true)
        {
       		$.ligerDialog.warn("该用户没有保存权限,请确认!");
       	
       		curpagestate=2;
       	 	return;
        }
	    if(document.getElementById("inserstaffid").value=="")
		{
			$.ligerDialog.error('请输入入库员工!');
		
			curpagestate=2;
			return;
		}
		if($("#factinsertype").val()=="")
		{
			$.ligerDialog.error('请选择入库方式!');
	
			curpagestate=2;
			return;
		}
		if($("#factcinserwareid").val()=="")
		{
			$.ligerDialog.error('请选择入库仓库!');

			curpagestate=2;
			return;
		}
		document.getElementById("inserwareid_h").value=$("#factcinserwareid").val();
		document.getElementById("insertype_h").value=$("#factinsertype").val();
    	var queryStringTmp=$('#goodsInsertForm').serialize();
		var requestUrl ="ic001/handconfirmInfo.action";
		var params=queryStringTmp;
		
		var curjosnparam="";
        var needReplaceStr="";
        var strJsonParam_detial="";
        //------卡号段列表
        for (var rowid in commoninfodivdetial.records)
		{
				var row =commoninfodivdetial.records[rowid];
				if(checkNull(row.insergoodsno)=="")
					continue;
				curjosnparam=JSON.stringify(row);
				curjosnparam=curjosnparam.replace("%","");
				curjosnparam=curjosnparam.replace("#","");
				/*if(curjosnparam.indexOf("_id")>-1)
				{
				   	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				   	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				}	     */       		   
				if(strJsonParam_detial!="")
				  	strJsonParam_detial=strJsonParam_detial+",";
				strJsonParam_detial= strJsonParam_detial+curjosnparam;
		}            		 
		if(strJsonParam_detial!="")
		{
			 params=params+"&strJsonParam=["+strJsonParam_detial+"]";
		}
			
		var responseMethod="handconfirmInfoMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
     //取肖复合入库单
    function handcancelInfo()
    {
    	var requestUrl ="ic001/handcancelInfo.action";
        var params="strCurCompId="+document.getElementById("insercompid").value;
        params=params+"&strCurBillId="+document.getElementById("inserbillid").value;
        params=params+"&handtype=2";
		var responseMethod="handcancelInfoMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
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
	        	 curpagestate=3;
	        	confirmInfo.setDisabled();
				cancelInfo.setEnabled();
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
    
    function handcancelInfoMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("操作成功!");
	        	pageWriteState();
				curpagestate=1;
				confirmInfo.setEnabled();
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
    	if(document.getElementById("strSearchBillno").value=="" )
		{
			$.ligerDialog.error('请填写查询信息!');
			return;
		}
    	var requestUrl ="ic001/searchOrderBill.action";
        var params="strCurCompId="+document.getElementById("insercompid").value;;
        params=params+"&strCurBillId="+document.getElementById("strSearchBillno").value;
		var responseMethod="searchOrderBillMessage";
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
    function searchOrderBillMessage(request)
   	{
        	var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.lsMgoodsinsert!=null && responsetext.lsMgoodsinsert.length>0)
	   		{
	   			curpagestate=3;
	   			curMasterInfoDate={Rows: responsetext.lsMgoodsinsert,Total: responsetext.lsMgoodsinsert.length};
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
	
	function confirmSendMessage(request)
	{
		try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	var gridlen=commoninfodivdetial.rows.length*1;
				if(gridlen==0)
				{
					addDetialRecord();
					gridlen=gridlen+1;
				} 
				if(checkNull(commoninfodivdetial.getRow(0).insergoodsname)!="")
				{
					addDetialRecord();
					gridlen=gridlen+1;
				}
				curDetialRecord=commoninfodivdetial.getRow(gridlen-1);
				commoninfodivdetial.updateRow(curDetialRecord,{ insergoodsno: checkNull(responsetext.curGoodsinfo.id.goodsno)
															  ,insergoodsname:checkNull(responsetext.curGoodsinfo.goodsname)
															  ,inserunit  : checkNull(responsetext.curGoodsinfo.purchaseunit)
															  ,insercount : ForDight(checkNull(responsetext.sendgoodsCount),1)
															  ,goodsprice : 0
															  ,goodsamt   : 0
															  ,producenorm : checkNull(responsetext.curGoodsinfo.goodsformat)
															  ,frombarcode : responsetext.strStartBarNo
															  ,tobarcode : responsetext.strEndBarNo
															  ,standunit : checkNull(responsetext.curGoodsinfo.purchaseunit)
															  ,standprice : 0
															  ,strBarSeqno: checkNull(responsetext.strIndexBar) });  
				shownewSendDialogmanager.close();
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
	

	
	function loadGoodsInfo(obj)
	{
		goodsdilogText=obj;
		goodsdilog=$.ligerDialog.open({ height: 600, url: contextURL+'/common/commonDilog/GoodsInfo.html', width: 750, isResize:true, title: '产品列表' });
   }
	
	
	function printReport()
	{
		var specailurl=contextURL+'/InvoicingControl/IC001/ic001_print.jsp';
	 	window.open(specailurl,"","top=" +2+ ",left=" + 100+",width=800,height=600,toolbar =no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
	}
	 
   
   function selectCall()
   {
		validateinserno(document.getElementById("winsergoodsno"));
   }
   
   function addSendGoodsInfo()
   {
   	 if(curpagestate!=1)
   	 {
   	 		$.ligerDialog.error('单据不在新增状态,请确认!');
			return;
   	 }
   	 shownewSendDialogmanager=$.ligerDialog.open({ height: null, url: contextURL+'/InvoicingControl/IC001/goodssend.jsp', width: 300, showMax: false, showToggle: false, showMin: false, isResize: false, title: '赠送产品' });
   }
   