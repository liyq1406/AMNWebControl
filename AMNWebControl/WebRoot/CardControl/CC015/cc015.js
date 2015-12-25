	var compTreeManager;
   	var compTree;
	var commoninfodivmaster = null;
	var commoninfodivdetial = null;
	var commoninfodivdetialsearch=null;
	var commoninfodivdetialconfirm=null;
	var strCurCompId = "";
	var strCurPackageNo="";
	var cc015layout=null;
	var curMasterInfoDate = null;
	var curpagestate =3;
	var CC015Tab=null;
	var curEditRecord = null;
	var curDetialRecord = null;
	var curDetialRowIndex = null;
	var fromItemmanager=null;
	var toitemmanager=null;
	var showDialogmanager=null;
	var postState=0;
	var payoutData=JSON.parse(parent.loadCommonControlDate_select("ZCXM",0));
   	var checkchooseData = [{ choose: 0, text: '无发票' },{ choose: 1, text: '有发票' }, { choose: 2, text: '冲账'}];
   	var statechooseData = [{ choose: 0, text: '登记中' },{ choose: 1, text: '门店审核' }, { choose: 2, text: '财务专员审核'}, { choose: 3, text: '财务经理审核'},
   	 													{ choose: 11, text: '门店驳回'},{ choose: 22, text: '财务专员驳回'},{ choose: 33, text: '财务经理驳回'}];
    var mstatechooseData = [{ choose: 0, text: '登记中' },{ choose: 1, text: '已保存' }, { choose: 2, text: '已受理'}];
    $(function ()
   	{
	   try
	   {
	   		cc015layout= $("#cc015layout").ligerLayout({ leftWidth: 270,  allowBottomResize: false, allowLeftResize: false,isLeftCollapse:true});
            var height = $(".l-layout-center").height();
            var centerWidth = cc015layout.centerWidth;
             $("#CC015Tab").ligerTab();
            CC015Tab = $("#CC015Tab").ligerGetTabManager();
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
                { hide:true,				name: 'bpayoutcompid' },
                
                { display: '支出单号', 		name: 'bpayoutbillid', 			width:120	,align: 'left' },
                { display: '支出日期', 		name: 'payoutdate', 			width:90	,align: 'left' },
                { display: '支出时间', 		name: 'payouttime',  			width:90	,align: 'left'},
                { display: '单据状态', 		name: 'billstate',  			width:60	,align: 'left'	, 
	                editor: { type: 'select', data: mstatechooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.billstate == 0) return '登记';
	                    else if (item.billstate == 1) return '已保存';
	                    else if (item.billstate == 2) return '已受理';
	                }
	            }
	            ],  pageSize:20, 
                data: null,      
                width: '380',
                height:height-50,
                enabledEdit: false,  checkbox:false,rownumbers:false,usePager:false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                	curEditRecord=data;
                    loadSelecDetialData(data, rowindex, rowobj);
                },
                toolbar: { items: [
                	{ text: '<input type="text"  name="fastDate" id="fastDate"  style="width:100;background:#EDF1F8"/>' , img: contextURL+'/common/ligerui/ligerUI/skins/icons/edit.gif'},
               	 	{ text: '增加', click: itemclick_mpackageInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
	           		{ text: '保存', click: itemclick_mpackageInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/save-disabled.gif'}
					]
                }	   
            });
            
             commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
                columns: [
                { display: '支出项目', 		name: 'payoutitemid', 			width:140	,align: 'left'  ,
                	editor: { type: 'select', data: payoutData, url:'loadAutoCommon',autocomplete: true, valueField: 'choose',selectBoxWidth:'220',alwayShowInDown:true},
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("ZCXM",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.payoutitemid)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
                { display: '支出金额', 		name: 'payoutitemamt', 			width:80	,align: 'right' ,editor: { type: 'float'} },
                { display: '有无发票', 		name: 'checkbookflag',  		width:100	,align: 'left', 
	                editor: { type: 'select', data: checkchooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.checkbookflag == 0) return '无发票';
	                    else if (item.checkbookflag == 1) return '有发票';
	                    else if (item.checkbookflag == 2) return '冲账';
	                     
	                }
	            },
                { display: '发票号码', 		name: 'checkbookno',  			width:140	,align: 'left' ,editor: { type: 'text'} },
                { display: '备注', 			name: 'payoutmark',  			width:160	,align: 'left' ,editor: { type: 'text'} },
	            { display: '专员审核日期', 	name: 'checkedopationdate',  			width:100	,align: 'left'},
	            { display: '经理审核日期', 	name: 'confirmopationdate',  			width:100	,align: 'left'},
	            { display: '状态', 			name: 'payoutbillstateText',  			width:100	,align: 'left'}
	            ],  pageSize:20, 
                data: null,      
                width: '940',
                height:height-50,
                enabledEdit: true,  checkbox:false,clickToEdit: false,rownumbers:false,usePager:false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                	  var beforeRoeDex=curDetialRowIndex;
			          curDetialRecord = data;
                	  curDetialRowIndex=rowindex;
           			  
                },
                 toolbar: { items: [
               	 	{ text: '增加', click: itemclick_bmpackageInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' }
	                ]
                }
                
				
            });
            commoninfodivdetialsearch=$("#commoninfodivdetialsearch").ligerGrid({
                columns: [
                { display: '支出日期', 		name: 'payoutdate', 			width:90	,align: 'right'  },
                { display: '支出单号', 		name: 'bpayoutbillid', 			width:120	,align: 'right' },
                { display: '经办人', 			name: 'payoutopationerid', 			width:80	,align: 'right' },
                { display: '支出项目', 		name: 'payoutitemid', 			width:140	,align: 'left'  ,
                	editor: { type: 'select', data: payoutData, valueField: 'choose',selectBoxHeight:'220'},
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("ZCXM",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.payoutitemid)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
                { display: '支出金额', 		name: 'payoutitemamt', 			width:80	,align: 'right' ,
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }
                 },
                { display: '发票号码', 		name: 'checkbookno',  			width:140	,align: 'left'  },
                { display: '备注', 			name: 'payoutmark',  			width:160	,align: 'left' },
	            { display: '专员审核日期', 	name: 'checkedopationdate',  			width:100	,align: 'left'},
	            { display: '经理审核日期', 	name: 'confirmopationdate',  			width:100	,align: 'left'},
	            { display: '状态', 			name: 'payoutbillstateText',  			width:100	,align: 'left'}
	            ],  pageSize:31, 
	            groupColumnName:'payoutdate',
	            groupColumnDisplay:'日期',
                data: null,      
                width: centerWidth,
                height:height-80,
                enabledEdit: false,  checkbox:false,rownumbers:false,usePager: true
            });
            commoninfodivdetialconfirm=$("#commoninfodivdetialconfirm").ligerGrid({
                columns: [
                { name: 'bpayoutseqno', 	hide:true },
                { display: '支出门店', 		name: 'bpayoutcompid', 			width:60	,align: 'left'  },
                { display: '门店名称', 		name: 'bpayoutcompname', 		width:90	,align: 'left' },
                { display: '支出单号', 		name: 'bpayoutbillid', 			width:120	,align: 'left' },
                { display: '支出日期', 		name: 'payoutdate', 			width:90	,align: 'left'  },
                { display: '支出项目', 		name: 'payoutitemid', 			width:140	,align: 'left'  ,
                	editor: { type: 'select', data: payoutData, valueField: 'choose',selectBoxHeight:'220'},
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("ZCXM",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.payoutitemid)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
                { display: '支出金额', 		name: 'payoutitemamt', 			width:80	,align: 'right' },
                { display: '发票号码', 		name: 'checkbookno',  			width:140	,align: 'left' },
                { display: '备注', 			name: 'payoutmark',  			width:160	,align: 'left'  },
	            { display: '状态', 			name: 'payoutbillstateText',  			width:100	,align: 'left'}
	            ],  pageSize:31, 
                data: null,      
                width: centerWidth,
                height:height-80,
                onCheckRow: f_onCheckRow,
                onCheckAllRow: f_onCheckAllRow,
                enabledEdit: false,  checkbox: true ,rownumbers: false,usePager: true,
                toolbar: { items: [
               	 	{ text: '支出审核', click: comfirmbill, img: contextURL+'/common/ligerui/ligerUI/skins/icons/true.gif' },
	           		{ text: '支出驳回', click: callbackbill, img: contextURL+'/common/ligerui/ligerUI/skins/icons/candle.gif' }
					]
                }	
            });
            $("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'100' });
            $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'100' });
            $("#strCFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'100' });
            $("#strCToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'100' });
            $("#searchButton").ligerButton(
	        {
	             text: '查询支出明细', width: 160,
		         click: function ()
		         {
		             loadDataSet();
		         }
	        });
	         $("#searchCButton").ligerButton(
	        {
	             text: '查询', width: 60,
		         click: function ()
		         {
		             loadConfirmDataSet();
		         }
	        });
	         var payoutmanagerData=JSON.parse(parent.loadCommonControlDate("ZCXM",0));
           	 fromItemmanager = $("#strFromItemNo").ligerComboBox({ data: payoutmanagerData, isMultiSelect: false,valueFieldID: 'factstrFromItemNo',width:'120'});	
           	 toitemmanager = $("#strToItemNo").ligerComboBox({ data: payoutmanagerData, isMultiSelect: false,valueFieldID: 'factstrToItemNo',width:'120' });           	 
           
           
            var today = new Date();
			var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
			var intDay=today.getDate();
			var today = intYear.toString()+"-"+fullStr(intMonth.toString())+"-"+fullStr(intDay.toString());
			document.getElementById("strFromDate").value=today;
			document.getElementById("strToDate").value=today;
			document.getElementById("strCFromDate").value=today;
			document.getElementById("strCToDate").value=today;
          	$("#pageloading").hide(); 
          	addDetialRecord();
          	addDetialSearchRecord();
          	addMasterRecord();
          	addDetialConfirmRecord();
          	
          	
            $("#fastDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120',onChangeDate:loadFastDate });
         	document.getElementById("fastDate").value=today;
					
            f_selectNode();
   		}
   		catch(e){alert(e.message);}
    });
    
    function loadFastDate(obj)
    {
    	commoninfodivmaster.updateRow(curEditRecord,{payoutdate: obj});
    	document.getElementById("payoutdate").value=obj;
    }
    
    
    	
    function loadAutoCommon(curmanager,curWriteStaff)
    {
    	var items=parent.gainCommonInfoByCode("ZCXM",0);
    	curmanager.setData(loadOtherGridByCommonInfo(items,0,curWriteStaff));
    	curmanager.selectBox.show();
    }     
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
    	strCurCompId=note.data.id;
    	var param="strCurCompId="+strCurCompId;
     	var requestUrl ="cc015/loadMaster.action"; 
		var responseMethod="loadMasterMessage";				
		sendRequestForParams_p(requestUrl,responseMethod,param );
    }
   
   function loadMasterMessage(request)
   {
       	try
        {
 
        	var responsetext = eval("(" + request.responseText + ")");
    
	   		if(responsetext.lsMpayoutinfo!=null && responsetext.lsMpayoutinfo.length>0)
	   		{
	   			curMasterInfoDate={Rows: responsetext.lsMpayoutinfo,Total: responsetext.lsMpayoutinfo.length};
	   			commoninfodivmaster.options.data=$.extend(true, {},curMasterInfoDate);
            	commoninfodivmaster.loadData(true);  
            	commoninfodivmaster.select(0);          	
	   		}
	   		else
	   		{
	   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivmaster.loadData(true);  
            	
	   		}
	 	}
	 	catch(e){alert(e.message);}
    }
    
    function loadSelecDetialData(data, rowindex, rowobj)
    {
        try{
        		 document.getElementById("payoutcompid").value=data.bpayoutcompid;
        		 document.getElementById("payoutbillid").value=data.bpayoutbillid;
        		 document.getElementById("billstate").value=data.billstate;
        		 document.getElementById("payoutdate").value=data.payoutdate;
        		 document.getElementById("payouttime").value=data.payouttime;
        		 document.getElementById("payoutopationerid").value=data.payoutopationerid;
        		 document.getElementById("payoutopationdate").value=data.payoutopationdate;
        		if(document.getElementById("billstate").value==1)
				{
					commoninfodivdetial.options.clickToEdit=false;
     				commoninfodivdetial.options.enabledEdit=false;
				}	
				else
				{
					commoninfodivdetial.options.clickToEdit=true;
     				commoninfodivdetial.options.enabledEdit=true;
				}	
			 	commoninfodivdetial.endEdit();
       			 var params = "strCurCompId="+data.bpayoutcompid;
       			 params =params+ "&strCurBillId="+data.bpayoutbillid;
				 var myAjax = new parent.Ajax.Request(
						"cc015/loadCurDetialinfo.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {							
								var action = eval( "("+request.responseText+")");	
								if(action.lsDpayoutinfo!=null && action.lsDpayoutinfo.length>0)
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: action.lsDpayoutinfo,Total: action.lsDpayoutinfo.length});
					            	commoninfodivdetial.loadData(true);            	
						   		}
						   		else
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total: 0});
					            	commoninfodivdetial.loadData(true); 
					            	addDetialRecord();  
						   		}
							},
							asynchronous:true
						});
				
          }catch(e){alert(e.message);}
   	}
   	
   	function itemclick_mpackageInfo(item)
    {
    	try
    	{
	    	 $.ligerDialog.confirm('确认提交 '+strCurCompId+' 支出登记 '+item.text+' 操作', function (result)
		     {
		     	if( result==true)
            	{
		          	if(item.text=="增加")
		        	{
		        		 
		        	 	addRecord();
		        	 	pageWriteState();
		        	}
		        	else if(item.text=="保存")
		        	{
		        		 
		        	 	editCurRecord();
		        	} 	
		        	else if(item.text=="删除")
		        	{
		        		
		        	 	deleteDetialRecord();
		        	} 
		        	else if(item.text=="查询")
		        	{
		        		loadCardInsertList();
		        	} 		
		        	
		        }
		      });  
    	}
    	catch(e){alert(e.message);}
    }
   
        function hotKeyOfSelf(key)
		{
			if(key==13)//回车
			{
				window.event.keyCode=9; //tab
				window.event.returnValue=true;
			}
			else if(key==114)//F3
			{
				 addDetialRecord();
				 window.event.keyCode = 505;
				 window.event.returnValue=false;
			}
			else if(key==115)//F4
			{
				 deleteDetialRecord();
			}
			else if( key == 83 &&  event.altKey)
			{
				editCurRecord();
			}
	
		}
     //删除行
    function deleteDetialRecord()
    {
         commoninfodivdetial.deleteSelectedRow();
    }
   
   	//新增行
     function addRecord()
     {
     		if(parent.hasFunctionRights( "CC015",  "UR_MODIFY")!=true)
	        {
	        	$.ligerDialog.warn("该用户没有编辑权限,请确认!");
	        }
	        else
	        {
	        		var params = "strCurCompId="+strCurCompId;				
       				var requestUrl ="cc015/add.action"; 
					var responseMethod="addMessage";				
					sendRequestForParams_p(requestUrl,responseMethod,params );
				          
	        }
     }
     function addMessage(request)
   	{
   		var responsetext = eval("(" + request.responseText + ")");
   		var row = commoninfodivmaster.getSelectedRow();
   		var data=responsetext.curMpayoutinfo;
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
		commoninfodivmaster.addRow({ 
				                 bpayoutcompid: data.bpayoutcompid,
				                 bpayoutbillid: data.bpayoutbillid,
				                 payoutdate: data.payoutdate,
				                 payouttime: data.payouttime,
				                 billstate : data.billstate
				            }, row, false);
		document.getElementById("payoutcompid").value=data.bpayoutcompid;
        document.getElementById("payoutbillid").value=data.bpayoutbillid;
        document.getElementById("billstate").value=data.billstate;
        document.getElementById("payoutdate").value=data.payoutdate;
        document.getElementById("payouttime").value=data.payouttime;
       	commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total: 0});
		commoninfodivdetial.loadData(true); 
		commoninfodivdetial.options.clickToEdit=true;
     	commoninfodivdetial.options.enabledEdit=true;
		addDetialRecord(); 
	
   	}
   	
   	function loadDataSet()
   	{
   		var params = "strCurCompId="+strCurCompId;
   		params = params+"&strFromDate="+document.getElementById("strFromDate").value;
   		params = params+"&strToDate="+document.getElementById("strToDate").value;		
   		params = params+"&iItemState="+document.getElementById("iItemState").value;		
   		params = params+"&strFromItemNo="+$("#factstrFromItemNo").val();
   		params = params+"&strToItemNo="+$("#factstrToItemNo").val();
   		showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
        var requestUrl ="cc015/loadDataSet.action"; 
		var responseMethod="loadDataSetMessage";				
		sendRequestForParams_p(requestUrl,responseMethod,params );
   	}
   	
   function loadDataSetMessage(request)
   {
       	try
        {
 			var responsetext = eval("(" + request.responseText + ")");
 			if(responsetext.lsDataSet!=null && responsetext.lsDataSet.length>0)
	   		{
	   			curMasterInfoDate={Rows: responsetext.lsDataSet,Total: responsetext.lsDataSet.length};
	   			commoninfodivdetialsearch.options.data=$.extend(true, {},curMasterInfoDate);
            	commoninfodivdetialsearch.loadData(true);  
            	commoninfodivdetialsearch.select(0);          	
	   		}
	   		else
	   		{
	   			commoninfodivdetialsearch.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivdetialsearch.loadData(true);  
            	addDetialSearchRecord();
	   		}
	   		showDialogmanager.close();
	 	}
	 	catch(e){alert(e.message);}
    }
    
   function loadConfirmDataSet()
   	{
   		var params = "strCurCompId="+getCurOrgFromSearchBar();
   		params = params+"&strFromDate="+document.getElementById("strCFromDate").value;
   		params = params+"&strToDate="+document.getElementById("strCToDate").value;		
   		params = params+"&iItemState="+document.getElementById("iCItemState").value;	
   		showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
        var requestUrl ="cc015/loadCDataSet.action"; 
		var responseMethod="loadCDataSetMessage";
		sendRequestForParams_p(requestUrl,responseMethod,params );
   	}
   	
   function loadCDataSetMessage(request)
   {
       	try
        {
 			var responsetext = eval("(" + request.responseText + ")");
 			if(responsetext.lsCDataSet!=null && responsetext.lsCDataSet.length>0)
	   		{
	   			curMasterInfoDate={Rows: responsetext.lsCDataSet,Total: responsetext.lsCDataSet.length};
	   			commoninfodivdetialconfirm.options.data=$.extend(true, {},curMasterInfoDate);
            	commoninfodivdetialconfirm.loadData(true);     	
	   		}
	   		else
	   		{
	   			commoninfodivdetialconfirm.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivdetialconfirm.loadData(true);  
            	addDetialConfirmRecord();
	   		}
	   		showDialogmanager.close();
	 	}
	 	catch(e){alert(e.message);}
    }
    
    
     function editCurRecord()
    {
    	if(postState==1)
		{
			$.ligerDialog.error("正在保存中,请不要连续保存!");
			return ;
		}
   		postState=1;
    	if(parent.hasFunctionRights( "CC015",  "UR_POST")!=true)
        {
       		$.ligerDialog.warn("该用户没有保存权限,请确认!");
       		postState=0;
       	 	return;
        }
        if(commoninfodivdetial.rows.length*1>0 && curDetialRowIndex!=null)
		{
			   commoninfodivdetial.endEdit();
		}	
		
		
        
    	var queryStringTmp=$('#packageForm').serialize();
		var requestUrl ="cc015/post.action";
		var params=queryStringTmp;
		var curjosnparam="";
        var needReplaceStr="";
        var strJsonParam_detial="";
        var  checkbookflag=0; 
  		var checkbookno="";
        for (var rowid in commoninfodivdetial.records)
		{
				var row =commoninfodivdetial.records[rowid];
				curjosnparam=JSON.stringify(row);
				/*if(curjosnparam.indexOf("_id")>-1)
				{
				   	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				   	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				}*/
				checkbookflag=row.checkbookflag;
				if((checkbookflag==1 || checkbookflag==2) && checkNull(row.checkbookno)=="")	      
				{
						$.ligerDialog.error("请将有发票和冲账的支出发票填写完整!");
						postState=0;
       	 				return;
				} 
				if( row.payoutbillstate>=1)
		        {
		        	$.ligerDialog.error("该支出登记单已被受理,不能再次保存,请确认!");
		        	postState=0;
					return;
		        }		   
				if(strJsonParam_detial!="")
				  	strJsonParam_detial=strJsonParam_detial+",";
				strJsonParam_detial= strJsonParam_detial+curjosnparam;
		}            		 
		if(strJsonParam_detial!="")
		{
			 params=params+"&strJsonParam=["+strJsonParam_detial+"]";
		}
		params=params+"&strCurCompId="+strCurCompId;
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
	        	 document.getElementById("payoutdate").value=1;
								
	        }
	        else
	        {
	        	alert(strMessage);
	        }
	        postState=0;
        }
		catch(e)
		{
			alert(e.message);
		}
    }
    
    
     
  
   	
   	function itemclick_bmpackageInfo(item)
   	{
   		addDetialRecord();
   	}
   	
   	function addDetialRecord()
   	{

   			if(commoninfodivdetial.rows.length*1>0)
   			{
   			 	 commoninfodivdetial.endEdit();
            }	
   		    if(commoninfodivdetial.rows.length*1>0 && checkNull(curDetialRecord.payoutitemid)=="")
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
    			detiallength=row['__index']*1;
    		}
    	     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
		commoninfodivdetial.addRow({ 
				                payoutitemid: "",
				                payoutitemamt: 0,
				                checkbookflag: 0,
				                checkbookno: "",
				                payoutmark: "",
				                payoutbillstateText: ""
				            }, row, false);
		commoninfodivdetial.select(detiallength*1+1);
		if(commoninfodivdetial.rows.length*1>0)
   		{
			commoninfodivdetial.beginEdit(commoninfodivdetial.getRow(curDetialRowIndex) );
		}
   	}
   	function addDetialSearchRecord()
   	{
   		var row = commoninfodivdetialsearch.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
		commoninfodivdetialsearch.addRow({ 
				                payoutitemid: "",
				                payoutitemamt: "",
				                checkbookflag: "",
				                checkbookno: "",
				                payoutmark: "",
				                payoutdate:"",
				                payoutbillstateText: ""
				            }, row, false);
   	}
   	function addDetialConfirmRecord()
   	{
   		var row = commoninfodivdetialconfirm.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
		commoninfodivdetialconfirm.addRow({ 
				                payoutitemid: "",
				                payoutitemamt: "",
				                checkbookflag: "",
				                checkbookno: "",
				                payoutmark: "",
				                payoutdate:"",
				                payoutbillstateText: ""
				            }, row, false);
   	}
   	function addMasterRecord()
   	{
   		var row = commoninfodivmaster.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
		commoninfodivmaster.addRow({ 
				                bpayoutcompid: "",
				                bpayoutbillid: "",
				                payoutdate: "",
				                payouttime: ""
				            }, row, false);
   	}
   
   		//获取审核的单据
    	var checkedComps= [];
    	var checkedBills= [];
    	var checkedItems= [];
    	function f_onCheckAllRow(checked)
        {
            for (var rowid in commoninfodivdetialconfirm.records)
            {
                if(checked)
                {
                    addCheckedComps_bills(commoninfodivdetialconfirm.records[rowid]['bpayoutcompid'],
                    commoninfodivdetialconfirm.records[rowid]['bpayoutbillid'],
                    commoninfodivdetialconfirm.records[rowid]['bpayoutseqno']);
                }
                else
                {
                    removeCheckedComps_bills(commoninfodivdetialconfirm.records[rowid]['bpayoutcompid'],
                    commoninfodivdetialconfirm.records[rowid]['bpayoutbillid'],
                    commoninfodivdetialconfirm.records[rowid]['bpayoutseqno']);
                }
            }
        }
	   	function f_onCheckRow(checked, data)
        {
        	if (checked) 
            	addCheckedComps_bills(data.bpayoutcompid,data.bpayoutbillid,data.bpayoutseqno);
            else 
            	removeCheckedComps_bills(data.bpayoutcompid,data.bpayoutbillid,data.bpayoutseqno);
        }
	   
	   	function findCheckedComps(compid)
        {
            for(var i =0;i<checkedComps.length;i++)
            {
                if(checkedComps[i] == compid) return i;
            }
            return -1;
        }
        function findCheckedBills(billid)
        {
            for(var i =0;i<checkedBills.length;i++)
            {
                if(checkedBills[i] == billid) return i;
            }
            return -1;
        }
        function findCheckedItems(item)
        {
            for(var i =0;i<checkedItems.length;i++)
            {
                if(checkedItems[i] == item) return i;
            }
            return -1;
        }
        function addCheckedComps_bills(compid,billid,item)
        {
       
            if(findCheckedComps(compid) == -1)
                checkedComps.push(compid);
            if(findCheckedBills(billid) == -1)
                checkedBills.push(billid);
            if(findCheckedItems(item) == -1)
                checkedItems.push(item);
        }
        function removeCheckedComps_bills(compid,billid,item)
        {
            /*var i = findCheckedComps(compid);
            if(i==-1) 
            	return;
            checkedComps.splice(i,1);
            
            i = findCheckedBills(billid);
            if(i==-1) 
            	return;
            checkedBills.splice(i,1);*/
            
            var i = findCheckedItems(item);
            if(i==-1) 
            	return;
            checkedItems.splice(i,1);
        }
        
    function comfirmbill()
    {
    		var needReplaceStr="";
    		var curjosnparam="";
    		var jsonParam="";
    		for (var rowid in commoninfodivdetialconfirm.records)
			{
				if(findCheckedComps(commoninfodivdetialconfirm.records[rowid]['bpayoutcompid'])!=-1
				&& findCheckedBills(commoninfodivdetialconfirm.records[rowid]['bpayoutbillid'])!=-1
				&& findCheckedItems(commoninfodivdetialconfirm.records[rowid]['bpayoutseqno'])!=-1)
				{	
				    var row =commoninfodivdetialconfirm.records[rowid];
				    curjosnparam=JSON.stringify(row);
					if(curjosnparam.indexOf("_id")>-1)
				  	{
				     	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				      	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				    }	            		   
				    if(jsonParam!="")
				      	jsonParam=jsonParam+",";
				    jsonParam= jsonParam+curjosnparam;
				 }            		 
			}
			if(jsonParam!="")
			{
				var params = "strJsonParam=["+jsonParam+"]"
   				params = params+"&iItemState="+document.getElementById("iCItemState").value;	
   				showDialogmanager = $.ligerDialog.waitting('正在审核中,请稍候...');	
       	 		var requestUrl ="cc015/ConfirmDataSet.action"; 
				var responseMethod="ConfirmDataSetMessage";
				sendRequestForParams_p(requestUrl,responseMethod,params );
			}
			else
			{
					$.ligerDialog.warn("请选择需要审核的明细!");
       	 			return;
			}
    }
    
    function ConfirmDataSetMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("审核成功!");
	        }
	        else
	        {
	        	$.ligerDialog.ware(strMessage);
	        }
	        showDialogmanager.close();
        }
		catch(e)
		{
			alert(e.message);
		}
    }
    function callbackbill()
    {
    	var needReplaceStr="";
    		var curjosnparam="";
    		var jsonParam="";
    		for (var rowid in commoninfodivdetialconfirm.records)
			{
				if(findCheckedComps(commoninfodivdetialconfirm.records[rowid]['bpayoutcompid'])!=-1
				&& findCheckedBills(commoninfodivdetialconfirm.records[rowid]['bpayoutbillid'])!=-1
				&& findCheckedItems(commoninfodivdetialconfirm.records[rowid]['bpayoutseqno'])!=-1)
				{	
				    var row =commoninfodivdetialconfirm.records[rowid];
				    curjosnparam=JSON.stringify(row);
					if(curjosnparam.indexOf("_id")>-1)
				  	{
				     	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				      	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				    }	            		   
				    if(jsonParam!="")
				      	jsonParam=jsonParam+",";
				    jsonParam= jsonParam+curjosnparam;
				 }            		 
			}
	
			if(jsonParam!="")
			{
				var params = "strJsonParam=["+jsonParam+"]";
   				params = params+"&iItemState="+document.getElementById("iCItemState").value;	
   				showDialogmanager = $.ligerDialog.waitting('正在驳回中,请稍候...');	
       	 		var requestUrl ="cc015/CallbackDataSet.action"; 
				var responseMethod="CallbackDataSetMessage";
				sendRequestForParams_p(requestUrl,responseMethod,params );
			}
			else
			{
					$.ligerDialog.warn("请选择需要驳回的明细!");
       	 			return;
			}
    }
    function CallbackDataSetMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("驳回成功!");
	        }
	        else
	        {
	        	$.ligerDialog.ware(strMessage);
	        }
	        showDialogmanager.close();
        }
		catch(e)
		{
			alert(e.message);
		}
    }