   	var commoninfodivmaster=null;	//卡入库主档列表
   	var commoninfodivdetial=null;	//卡入库明细列表
   	var CC003Tab=null;
   	var commoninfodivcardstock=null; //卡库存查询
   	var commoninfodivcardallto=null; //卡领用查询
   	var strCurCompId="";
   	var strCurBillId="";
   	var curMasterInfoDate=null;
   	var fromValidate=null;
   	var cinsertwaremanager = null;
   	var cinsertcardtypemanager = null;
   	var cinsertpermanager = null;
   	var cc001layout=null;
   	var curpagestate=3; // 1 add 2 modify 3 browse
   	var lsStoreAppBills =new Array();
   	var lsStoreCanAllotCards=new Array();
   	var showD=null;
   	var showAllot=null;
   	var curSelectRowIndex=0;
   	var curSelectCardType="";
   	var curSelectCardTypename=0;
   	var curSelectCardTypeCount=0;
   	//初始化属性
    $(function ()
   	{
	   try
	   {
	   	 	  //布局
            cc001layout= $("#cc001layout").ligerLayout({ rightWidth: 350,  allowBottomResize: false, allowLeftResize: false });
	   		commoninfodivmaster=$("#commoninfodivmaster").ligerGrid({
                columns: [
                { display: '配发门店', 	name: 'bcallotcompid',  	width:50,align: 'left' },
                { display: '配发单号', 	name: 'bcallotbillid', 	width:120,align: 'left'},
	            { display: '配发日期', 	name: 'callotdate', 		width:80,align: 'left'},
	            { display: '配发时间', 	name: 'callottime', 		width:70,align: 'left' },
	            { display: '申请门店', 	name: 'cappcompid', 		width:70,align: 'left' },
	            { display: '申请单号', 	name: 'cappbillid', 		width:120,align: 'left' }
                ],  pageSize:25, 
                data:{Rows: null,Total:0},      
                width: '552',
                height:'690',
                clickToEdit: false,   enabledEdit: false,  
                rownumbers: true,usePager: true,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                }, 
                toolbar: { items: [
	                { text: '查询', click: itemclick_cardalloInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/search.gif' },
	                { text: '增加', click: itemclick_cardalloInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
	           		{ text: '保存', click: itemclick_cardalloInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' },
					{ text: '删除', click: itemclick_cardalloInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' },
	                { text: '刷新', click: itemclick_cardalloInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/refresh.gif' }
	                ]
                },where : f_getCardAlloInfo()   	   
            });
            
        
        	commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
                columns: [
                { display: '卡类', 		name: 'cardtypeid',  	width:40,align: 'left' },
                { display: '类型名称', 	name: 'cardtypeName',  	width:90,align: 'left' },
                { display: '起始卡编号', 	name: 'cardnofrom', 	width:90,align: 'left'},
	            { display: '结束卡编号', 	name: 'cardnoto', 		width:90,align: 'left'},
	            { display: '申请数', 		name: 'ccount', 	width:45,align: 'right' },
	            { display: '配发数', 		name: 'allotcount', 			width:45,align: 'right' },
	            { display: '操作', 		name: 'option', 		width:70,align: 'right', render: function (rowdata, rowindex, value)
	                {
	                    var h = "";
	                    if (!rowdata._editing)
	                    {
	                      return "<a href='javascript:showCanAllotInfo(" + rowindex + ")'>查看可领卡</a> ";
	                    }  
	                   
	                }
	            }  
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '480',
                height:'410',
                clickToEdit: false,   enabledEdit: false,  
                usePager: false
            });
            
            commoninfodivcardstock=$("#commoninfodivcardstock").ligerGrid({
                columns: [
                { display: '分区号', 		name: 'compid',  	width:50,align: 'left' },
                { display: '仓库', 	name: 'storagename',  	width:70,align: 'left' },
                { display: '卡类型', 	name: 'cardclass', 	width:45,align: 'left'},
                { display: '卡名称', 	name: 'cardclassname', 	width:100,align: 'left'},
	            { display: '库存量', 	name: 'ccount', 		width:50,align: 'right'}
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '98%',
                height:'98%',
                clickToEdit: false,   enabledEdit: false,  
                usePager: false, 
                toolbar: { items: [
	                { text: '查询卡库存', click: itemclick_cardstock, img: contextURL+'/common/ligerui/ligerUI/skins/icons/search.gif' }
	                ]
                }
            });
            
             commoninfodivcardallto=$("#commoninfodivcardallto").ligerGrid({
                columns: [
                { display: '门店号', 	name: 'appCompId',  	width:40,align: 'left' },
                { display: '卡类型', 	name: 'cardtypeName', 	width:85,align: 'left'},
                { display: '起始卡', 	name: 'cardnofrom', 	width:75,align: 'left'},
                { display: '结束卡', 	name: 'cardnoto', 	width:75,align: 'left'},
	            { display: '数量', 	name: 'allotcount', 		width:40,align: 'right'}
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '98%',
                height:'98%',
                clickToEdit: false,   enabledEdit: false,  
                usePager: false, 
                toolbar: { items: [
	                { text: '查询卡配发', click: itemclick_cardallot, img: contextURL+'/common/ligerui/ligerUI/skins/icons/search.gif' }
	                ]
                }
            });
            //表单验证信息
            $.validator.addMethod(
                    "notnull",
                    function (value, element, regexp)
                    {
                        if (!value) return true;
                        return !$(element).hasClass("l-text-field-null");
                    },
                    "不能为空"
            );

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
             $("#editCurDetailInfo").ligerButton(
	         {
	             text: '载入列表', width: 120,
		         click: function ()
		         {
		             editCurDetailInfo();
		         }
	         });
          	$("#pageloading").hide();
          	$("#txtSearchDateKey").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' }); 
          	$("#checkoutdate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'140' }); 
          	$("#CC003Tab").ligerTab();
            CC003Tab = $("#CC003Tab").ligerGetTabManager();
            
          	loadCardAllotInfo();
          	addtradeSearch();
   		}catch(e){alert(e.message);}
    });
    
    //加载卡入库主明细
    function loadCardAllotInfo()
    {
     	var requestUrl ="cc003/loadCardAllotInfo.action"; 
		var responseMethod="loadCardAllotInfoMessage";				
		sendRequestForParams_p(requestUrl,responseMethod,"" );
    }
   
   function loadCardAllotInfoMessage(request)
   {
       		try
        	{
 
        	var responsetext = eval("(" + request.responseText + ")");
    		if(responsetext.lsMcardallotments!=null && responsetext.lsMcardallotments.length>0)
	   		{
	   			curMasterInfoDate={Rows: responsetext.lsMcardallotments,Total: responsetext.lsMcardallotments.length};
	   			commoninfodivmaster.options.data=$.extend(true, {},curMasterInfoDate);
            	commoninfodivmaster.loadData(true);            	
	   		}
	   		else
	   		{
	   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivmaster.loadData(true);  
	   		}
			
			if(responsetext.lsDcardallotments!=null && responsetext.lsDcardallotments.length>0)
	   		{
	   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: responsetext.lsDcardallotments,Total: responsetext.lsDcardallotments.length});
            	commoninfodivdetial.loadData(true);            	
	   		}
	   		else
	   		{
	   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivdetial.loadData(true);  
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
				
		   		cinsertwaremanager= $("#callotwareid").ligerComboBox({ isShowCheckBox: true, 
	                data: JSON.parse(returnValue)
	                , valueFieldID: 'factCallotwareid',label:'请选仓库',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220'
	            }); 
	        }
	        else
	        {
	        	cinsertwaremanager= $("#callotwareid").ligerComboBox({ isShowCheckBox: true, 
	                data: null , valueFieldID: 'factCallotwareid',label:'选择仓库',labelWidth:200,labelAlign:'center',width:'120',selectBoxHeight:'220'
	            }); 
	        }
	        cinsertwaremanager.setDisabled();
	      	returnValue='';
	   		if(responsetext.lsCardtypeinfos!=null && responsetext.lsCardtypeinfos.length>0)
	   		{
	   			for(var i=0;i<responsetext.lsCardtypeinfos.length;i++)
				{
				    
					if(returnValue!='')
					{
						returnValue=returnValue+',';
					}
					else
					{
						returnValue=returnValue+'[';
					}
					returnValue=returnValue+'{"id": "'+responsetext.lsCardtypeinfos[i].id.cardtypeno+'","text": "'+responsetext.lsCardtypeinfos[i].cardtypename+'"}';
				}
	   		}
	   		if(returnValue!='')
	   		{
	   			
				returnValue=returnValue+']';	
				
		   		cinsertcardtypemanager= $("#txtAllotCardTypeKey").ligerComboBox({ isShowCheckBox: true, 
	                data: JSON.parse(returnValue)
	                , valueFieldID: 'factctxtAllotCardTypeKey',label:'请选卡类型',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'320'
	            }); 
	        }
	        else
	        {
	        	cinsertwaremanager= $("#txtAllotCardTypeKey").ligerComboBox({ isShowCheckBox: true, 
	                data: null , valueFieldID: 'factctxtAllotCardTypeKey',label:'选择卡类型',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'320'
	            }); 
	        }
	        
	   		document.getElementById("cardIdLength").value=checkNull(responsetext.cardIdLength);
    		document.getElementById("numberOfCardFilter").value=checkNull(responsetext.numberOfCardFilter);
	        loadCurMaster(responsetext.curMcardallotment);
	   		}catch(e){alert(e.message);}
    }
    
    function loadCurMaster(curMaster)
    {
    	document.getElementById("callotcompid").value=checkNull(curMaster.bcallotcompid);
    	document.getElementById("callotbillid").value=checkNull(curMaster.bcallotbillid);
   		cinsertwaremanager.selectValue(checkNull(curMaster.callotwareid));
    	document.getElementById("callotdate").value=checkNull(curMaster.callotdate);
    	document.getElementById("callottime").value=checkNull(curMaster.callottime);
    	document.getElementById("callotempid").value=checkNull(curMaster.callotempid);
    	document.getElementById("callotempText").value=checkNull(curMaster.callotempText);
    	document.getElementById("recevieempid").value=checkNull(curMaster.recevieempid);
    	document.getElementById("recevieempText").value=checkNull(curMaster.recevieempText);
    	document.getElementById("cappcompid").value=checkNull(curMaster.cappcompid);
    	document.getElementById("cappcompidText").value=checkNull(curMaster.cappcompidText);
    	document.getElementById("cappbillid").value=checkNull(curMaster.cappbillid);
   		handleRadio("curMcardnoinsert.checkoutflag",checkNull(curMaster.checkoutflag));
    	document.getElementById("checkoutdate").value=checkNull(curMaster.checkoutdate);
    	document.getElementById("callotopationempid").value=checkNull(curMaster.callotopationempid);
    	document.getElementById("callotopationdate").value=checkNull(curMaster.callotopationdate);
    	
		if(document.getElementById("callotempid").value=="")
		{
			pageWriteState();
		}
    }
    
    function pageWriteState()
    {
	    try
	    {
	    	document.getElementById("recevieempid").readOnly="";
	    	document.getElementById("callotempid").readOnly="";
	    	document.getElementById("cappcompid").readOnly="";
	   		cinsertwaremanager.setEnabled();
	   		
    	}catch(e){alert(e.message);}
	}
    
    function pageReadState()
    {
    	try
	    {
	    	document.getElementById("recevieempid").readOnly="readOnly";
	    	document.getElementById("callotempid").readOnly="readOnly";
	    	document.getElementById("cappcompid").readOnly="readOnly";
	   		cinsertwaremanager.setDisabled();
	   		
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
        		 strCurCompId=data.bcallotcompid;
        		 strCurBillId=data.bcallotbillid;
       			 var params = "strCurCompId="+strCurCompId;
       			 params =params+ "&strCurBillId="+strCurBillId;
				 var myAjax = new parent.Ajax.Request(
						"cc003/loadDcardallotment.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {							
								var responsetext = eval( "("+request.responseText+")");	
								if(responsetext.lsDcardallotments!=null && responsetext.lsDcardallotments.length>0)
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: responsetext.lsDcardallotments,Total: responsetext.lsDcardallotments.length});
					            	commoninfodivdetial.loadData(true);            	
						   		}
						   		else
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
					            	commoninfodivdetial.loadData(true);  
						   		}
	   							 loadCurMaster(responsetext.curMcardallotment);
							},
							asynchronous:true
						});			  
          }catch(e){alert(e.message);}
    }
    //------------------------主档按钮操作-------------------Start
    function itemclick_cardalloInfo(item)
    {
    	try
    	{
	    	 $.ligerDialog.confirm('确认提交 '+strCurCompId+' 卡号段入库 '+item.text+' 操作', function (result)
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
		        		loadCardAllotInfo();
		        	} 		
		        	else if(item.text=="查询")
		        	{
		        		loadCardAllotInfoBySearch();
		        	} 	
		        }
		      });  
    	}
    	catch(e){alert(e.message);}
    }
    //------------------------查询配发信息---------------------
     function loadCardAllotInfoBySearch()
     {
    	  try
    	  {
     	   commoninfodivmaster.options.data = $.extend(true, {}, curMasterInfoDate);
           commoninfodivmaster.loadData(f_getCardAlloInfo());
           
           }catch(e){alert(e.message);}
     }
     function f_getCardAlloInfo()
     {
     	try
    	{
            if (!commoninfodivmaster) return null;
            var clause = function (rowdata, rowindex)
            {
                var txtSearchCompIdKey = $("#txtSearchCompIdKey").val();
             	var txtSearchBillIdKey = $("#txtSearchBillIdKey").val();
             	var txtSearchDateKey = $("#txtSearchDateKey").val();
                return ((rowdata.cappcompid==txtSearchCompIdKey || txtSearchCompIdKey=="")
                  && (rowdata.cappbillid==txtSearchBillIdKey || txtSearchBillIdKey=="")
                  && (rowdata.callotdate==txtSearchDateKey || txtSearchDateKey=="") );
          
                
            };
            return clause; 
        }
    	catch(e){alert(e.message);}
     }
    //------------------------保存配发信息---------------------
    function editCurRecord()
    {
    	if(parent.hasFunctionRights( "CC003",  "UR_POST")!=true)
        {
       		$.ligerDialog.warn("该用户没有保存权限,请确认!");
       	 	return;
        }
	   if(fromValidate.element($("#cappcompid"))==false ||
			fromValidate.element($("#cappbillid"))==false ||
			fromValidate.element($("#callotdate"))==false ||
			fromValidate.element($("#callotempid"))==false )
		{
			$.ligerDialog.error('填入信息有误,请确认!');
			return;
		}
    	var queryStringTmp=$('#cardCallotForm').serialize();
		var requestUrl ="cc003/post.action";
		var params=queryStringTmp;
		
		var curjosnparam="";
        var needReplaceStr="";
        var strJsonParam_detial="";
        //------卡号段列表
        for (var rowid in commoninfodivdetial.records)
		{
				var row =commoninfodivdetial.records[rowid];
				curjosnparam=JSON.stringify(row);
				/*if(curjosnparam.indexOf("_id")>-1)
				{
				   	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				   	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				}	*/            		   
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
	        	 $.ligerDialog.success("更新成功!");
	        	 curpagestate=3;
	        	 pageReadState();
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
     
	//------------------------新增配发信息--------------------- 
     function addRecord()
     {
     		if(parent.hasFunctionRights( "CC003",  "UR_MODIFY")!=true)
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
				                bcallotcompid: "",
				                bcallotbillid: "",
				                callotdate: "",
				                callottime: ""
				             
				            }, row, false);
				var params = "";			
    	     	var requestUrl ="cc003/add.action"; 
				var responseMethod="addMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params );
	        }
     }
     
     function  addtradeSearch()
     {
        	var row = commoninfodivcardstock.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivcardstock.addRow({ 
				            }, row, false);
     }
    
     function addMessage(request)
     {
	    	try
	        {
	        	var responsetext = eval("(" + request.responseText + ")");
	    		loadCurMaster(responsetext.curMcardallotment);
		   		commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivdetial.loadData(true);  
		   	}catch(e){alert(e.message);}
     }
     
  	//------------------------删除配发信息--------------------- 
    function deleteCurRecord()
    {
       	if(parent.hasFunctionRights( "CC003",  "UR_DELETE")!=true)
        {
        	 $.ligerDialog.warn("该用户没有删除权限,请确认!");
        	 return;
        }
        var requestUrl ="cc003/delete.action";
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
 
   //--------------------------验证配发人员-----------------
   function validateCallotempid(obj)
   {
   		if(obj.value=="")
   		{
   			document.getElementById("callotempText").value="";
   			return ;
   		}
   		var requestUrl ="cc003/validateCallotempid.action";
        var params="strCurCompId="+document.getElementById("callotcompid").value;
        params=params+"&strCurEmpId="+obj.value;
		var responseMethod="validateCallotempidMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	  
   }
   
   function validateCallotempidMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		document.getElementById("callotempText").value=responsetext.strCurEmpName;
	       	}
	       	else
	       	{
	       		document.getElementById("callotempid").value="";
	       		document.getElementById("callotempText").value="";
	       		$.ligerDialog.warn(strMessage);
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	
   	 //--------------------------验证领取人员-----------------
   function validateRecevieempid(obj)
   {
   		if(obj.value=="")
   		{
   			document.getElementById("recevieempText").value="";
   			return ;
   		}
   		var requestUrl ="cc003/validateRecevieempid.action";
        var params="strCurCompId="+document.getElementById("cappcompid").value;
        params=params+"&strCurEmpId="+obj.value;
		var responseMethod="validateRecevieempidMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	  
   }
   
   function validateRecevieempidMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		document.getElementById("recevieempText").value=responsetext.strCurEmpName;
	       	}
	       	else
	       	{
	       		document.getElementById("recevieempid").value="";
	       		document.getElementById("recevieempText").value="";
	       		$.ligerDialog.warn(strMessage);
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	
   	//-------------------------验证申请门店编号-----------------------
   	function validateCappcompid(obj)
   	{
   		if(obj.value=="")
   		{
   			document.getElementById("cappcompidText").value="";
   			return ;
   		}
   		var requestUrl ="cc003/validateCappcompid.action";
        var params="strCurCompId="+obj.value;
		var responseMethod="validateCappcompidMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	  
   }
   
   function validateCappcompidMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		document.getElementById("cappcompidText").value=responsetext.strCurCompName;
	       		lsStoreAppBills=responsetext.lsStoreAppBills;

	       	}
	       	else
	       	{
	       		document.getElementById("cappcompidText").value="";
	       		$.ligerDialog.warn(strMessage);
	       	}
	       	document.getElementById("cappbillid").value="";
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	//--------------------显示已经同意但未配发的单据----------------------
   	function showConfirmBill()
    {
    	 if(curpagestate==3)
		 {
		       $.ligerDialog.warn("配发单不在新增状态,不能选择门店卡申请单!");
		      return ;
		 }
		 if(document.getElementById("cappcompid").value=="")
		 {
		       $.ligerDialog.warn("请确认申请门店!");
		      return ;
		 }
         showD=$.ligerDialog.open({url: contextURL+'/CardControl/CC003/storeAppBills.jsp',width:410,  height: 600, isResize: true,title: '门店卡申请表' });
    }
    
    //----------------------验证申请单号---------------------
    function validateCappbillid(obj)
    {
    	if(obj.value=="")
   		{
   			return ;
   		}
   		var requestUrl ="cc003/validateCappbillid.action";
        var params="strCurCompId="+document.getElementById("cappcompid").value;
        params=params+"&strCurBillId="+obj.value;
		var responseMethod="validateCappbillidMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	  
   }
   
    function validateCappbillidMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	if(responsetext.lsDcardallotments!=null && responsetext.lsDcardallotments.length>0)
			{
				commoninfodivdetial.options.data=$.extend(true, {},{Rows: responsetext.lsDcardallotments,Total: responsetext.lsDcardallotments.length});
				commoninfodivdetial.loadData(true);            	
			}
			else
			{
				commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
				commoninfodivdetial.loadData(true);  
			}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   
   //-------------------配发查看可领卡----------------------
   function showCanAllotInfo(index)
   {
   		try
		{
			curSelectRowIndex=index;
			
	   		if(curpagestate==3)
			{
			    $.ligerDialog.warn("配发单不在新增状态,不能选择门店卡申请单!");
			    return ;
			}
			var rowdata = commoninfodivdetial.getRow(index);
			curSelectCardType=rowdata.cardtypeid;
			curSelectCardTypename=rowdata.cardtypeName;
   			curSelectCardTypeCount=rowdata.ccount;
			var requestUrl ="cc003/showCanAllotInfo.action";
        	var params="strCurCompId="+document.getElementById("callotcompid").value;
        	params=params+"&strWareId="+$("#factCallotwareid").val();
       	 	params=params+"&strCardType="+rowdata.cardtypeid;
			var responseMethod="showCanAllotInfoMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params ); 	 
   		}
		catch(e)
		{
			alert(e.message);
		}
   }
   
   function showCanAllotInfoMessage(request)
   {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	lsStoreCanAllotCards=responsetext.lsStoreCanAllotCards;
	       	if(lsStoreCanAllotCards!=null && lsStoreCanAllotCards.length>0)
	       	{
	       		showAllot=$.ligerDialog.open({url: contextURL+'/CardControl/CC003/showCanAllotInfo.jsp',width:600,  height: 600, isResize: true,title: '门店卡申请表' });
  			}
  			else
  			{
  				 $.ligerDialog.warn("此类型的在本仓库已不存在库存!");
  			}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	//----------------------门店卡库查询-------------------------
   	function itemclick_cardstock(item)
   	{
   		var requestUrl ="cc003/loadCardstockInfo.action"; 
		var responseMethod="loadCardstockInfoMessage";				
		sendRequestForParams_p(requestUrl,responseMethod,"" );
    }
   
   function loadCardstockInfoMessage(request)
   {
       		var responsetext = eval("(" + request.responseText + ")");
    		if(responsetext.lsStoreCanAllotCards!=null && responsetext.lsStoreCanAllotCards.length>0)
	   		{
	   			commoninfodivcardstock.options.data=$.extend(true, {},{Rows: responsetext.lsStoreCanAllotCards,Total: responsetext.lsStoreCanAllotCards.length});
            	commoninfodivcardstock.loadData(true);            	
	   		}
	   		else
	   		{
	   			commoninfodivcardstock.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivcardstock.loadData(true);  
	   		}
   	}
   	
   	//---------------------会员卡配发查询----------------------------
   	function itemclick_cardallot(item)
   	{
   		var strAllotCompId=document.getElementById("txtAllotCompIdKey").value;
   		var strAllotCardType=$("#factctxtAllotCardTypeKey").val();
   		if(strAllotCompId=="" && strAllotCardType=="")
   		{
   			 $.ligerDialog.warn("请输入配发的门店或配发的卡类型!");
   			 return ;
   		}
   		var requestUrl ="cc003/searchCardAllotInfo.action"; 
		var responseMethod="searchCardAllotInfoMessage";			
		var param="strAllotCompId="+strAllotCompId;
		param=param+"&strAllotCardType="+strAllotCardType;
		sendRequestForParams_p(requestUrl,responseMethod,param );
   		
   	}
	function searchCardAllotInfoMessage(request)
   	{
       		var responsetext = eval("(" + request.responseText + ")");
    		if(responsetext.lsDcardallotments!=null && responsetext.lsDcardallotments.length>0)
	   		{
	   			commoninfodivcardallto.options.data=$.extend(true, {},{Rows: responsetext.lsDcardallotments,Total: responsetext.lsDcardallotments.length});
            	commoninfodivcardallto.loadData(true);            	
	   		}
	   		else
	   		{
	   			commoninfodivcardallto.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivcardallto.loadData(true);  
	   		}
   	}
   	
   	function hotKeyOfSelf(key)
	{
		if(key==13)//回车
		{
				window.event.keyCode=9; //tab
				window.event.returnValue=true;
		}
	}
   	