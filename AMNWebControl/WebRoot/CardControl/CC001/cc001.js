   	var commoninfodivmaster=null;	//卡入库主档列表
   	var commoninfodivdetial=null;	//卡入库明细列表
   	var strCurCompId="";
   	var strCurBillId="";
   	var curMasterInfoDate=null;
   	var fromValidate=null;
   	var cinsertwaremanager = null;
   	var cinsertcardtypemanager = null;
   	var cinsertpermanager = null;
   	var cc001layout=null;
   	var curpagestate=3; // 1 add 2 modify 3 browse
   	var contextURL="<%=request.getContextPath()%>";
   	//初始化属性
    $(function ()
   	{
	   try
	   {
	   	 	  //布局
            cc001layout= $("#cc001layout").ligerLayout({ rightWidth: 400,  allowBottomResize: false, allowLeftResize: false});
	   		var height = $(".l-layout-center").height();
           	commoninfodivmaster=$("#commoninfodivmaster").ligerGrid({
                columns: [
                { display: '入库门店', 	name: 'bcinsertcompid',  	width:50,align: 'left' },
                { display: '入库单号', 	name: 'bcinsertbillid', 	width:120,align: 'left'},
	            { display: '入库日期', 	name: 'cinsertdate', 		width:80,align: 'left'},
	            { display: '入库时间', 	name: 'cinserttime', 		width:70,align: 'right' }   
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '360',
                height:'715',
                clickToEdit: false,   enabledEdit: false,  
                rownumbers: true,usePager: false,
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
            
        
        	commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
                columns: [
                { display: '会员卡类型', 	name: 'cardtypeid',  	width:80,align: 'left' },
                { display: '会员卡名称', 	name: 'cardtypeName',  	width:80,align: 'left' },
                { display: '起始卡编号', 	name: 'cardnofrom', 	width:100,align: 'left'},
	            { display: '结束卡编号', 	name: 'cardnoto', 		width:100,align: 'left'},
	            { display: '入库卡张数', 	name: 'cardnum', 		width:70,align: 'right' },
	            { display: '单卡成本', 	name: 'cardprice', 		width:70,align: 'right' }  ,
	            { display: '入库金额', 	name: 'cardamt', 		width:70,align: 'right' }    
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '615',
                height:'350',
                clickToEdit: false,   enabledEdit: false,  
                rownumbers: true,usePager: false
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
		//捷易拍菜单按钮
            $("#toptoolbar").ligerToolBar({ items: [
                {text: '启 动', click:Start1_onclick},
                { line:true },
                { text: '参数设置', click: ParaSet_onclick },
                 { line:true },
                 { text: 'PIN设置', click: ParaSetPIN_onclick },
                { line:true },
                { text: '上 传', click: UpLoadJPG_onclick },
                { line:true },
                { text: '关 闭', click: Stop_onclick }
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
             $("#editCurDetailInfo").ligerButton(
	         {
	             text: '载入列表', width: 120,
		         click: function ()
		         {
		             editCurDetailInfo();
		         }
	         });
          	$("#pageloading").hide(); 
          	loadCardInsertList();
   		}catch(e){alert(e.message);}
    });
    
    //加载卡入库主明细
    function loadCardInsertList()
    {
     	var requestUrl ="cc001/loadCardInsertInfo.action"; 
		var responseMethod="loadCardInsertInfoMessage";				
		sendRequestForParams_p(requestUrl,responseMethod,"" );
    }
   
   function loadCardInsertInfoMessage(request)
   {
       		try
        	{
 
        	var responsetext = eval("(" + request.responseText + ")");
    
	   		if(responsetext.lsMcardnoinserts!=null && responsetext.lsMcardnoinserts.length>0)
	   		{
	   			curMasterInfoDate={Rows: responsetext.lsMcardnoinserts,Total: responsetext.lsMcardnoinserts.length};
	   			commoninfodivmaster.options.data=$.extend(true, {},curMasterInfoDate);
            	commoninfodivmaster.loadData(true);   
            	        	
	   		}
	   		else
	   		{
	   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivmaster.loadData(true);  
	   		}
			
			if(responsetext.lsDcardnoinsert!=null && responsetext.lsDcardnoinsert.length>0)
	   		{
	   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: responsetext.lsDcardnoinsert,Total: responsetext.lsDcardnoinsert.length});
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
				
		   		cinsertwaremanager= $("#cinsertware").ligerComboBox({ isShowCheckBox: true, 
	                data: JSON.parse(returnValue)
	                , valueFieldID: 'factcInsertware',label:'请选仓库',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220'
	            }); 
	        }
	        else
	        {
	        	cinsertwaremanager= $("#cinsertware").ligerComboBox({ isShowCheckBox: true, 
	                data: null , valueFieldID: 'factcInsertware',label:'选择仓库',labelWidth:200,labelAlign:'center',width:'120',selectBoxHeight:'220'
	            }); 
	        }
	        cinsertwaremanager.setDisabled();
	        //卡类型选择框
	       /* returnValue='';
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
				
		   		cinsertcardtypemanager= $("#dcardType").ligerComboBox({ isShowCheckBox: false, alwayShowInTop :true,
	                data: JSON.parse(returnValue)
	                , valueFieldID: 'factcInsertcardtype',label:'请选卡类型',labelWidth:200,labelAlign:'center',selectBoxHeight:'120',resize:false
	            }); 
	        }
	        else
	        {
	        	cinsertwaremanager= $("#dcardType").ligerComboBox({ isShowCheckBox: false, 
	                data: null , valueFieldID: 'factcInsertcardtype',label:'选择卡类型',labelWidth:200,labelAlign:'center',selectBoxHeight:'120',resize:false
	            }); 
	        }
	        cinsertcardtypemanager.setDisabled();*/
	        addOption("","请选择",document.getElementById("dcardType"))
			for(var i=0;i<responsetext.lsCardtypeinfos.length;i++)
			{
			    addOption(responsetext.lsCardtypeinfos[i].id.cardtypeno,responsetext.lsCardtypeinfos[i].id.cardtypeno+"-"+responsetext.lsCardtypeinfos[i].cardtypename,document.getElementById("dcardType"))
			}
			document.getElementById("dcardType").disabled=true;
	   		document.getElementById("cardIdLength").value=checkNull(responsetext.cardIdLength);
    		document.getElementById("numberOfCardFilter").value=checkNull(responsetext.numberOfCardFilter);
	        loadCurMaster(responsetext.curMcardnoinsert);
	   		}catch(e){alert(e.message);}
    }
    
    function loadCurMaster(curMaster)
    {
    	document.getElementById("cinsertcompid").value=checkNull(curMaster.bcinsertcompid);
    	document.getElementById("cinsertbillid").value=checkNull(curMaster.bcinsertbillid);
    	document.getElementById("cinsertdate").value=checkNull(curMaster.cinsertdate);
    	document.getElementById("cinserttime").value=checkNull(curMaster.cinserttime);
    	document.getElementById("cinsertper").value=checkNull(curMaster.cinsertper);
    	 
   		cinsertwaremanager.selectValue(checkNull(curMaster.cinsertware));
    	document.getElementById("bcinsertcompName").value=checkNull(curMaster.bcinsertcompName);
    	document.getElementById("cinsertperName").value=checkNull(curMaster.cinsertperName);
    	document.getElementById("createcompname").value=checkNull(curMaster.createcompname);
    	document.getElementById("billno").value=checkNull(curMaster.billno);
    	document.getElementById("checkoutmark").value=checkNull(curMaster.checkoutmark);
    	document.getElementById("optionconfrimper").value=checkNull(curMaster.optionconfrimper);
    	document.getElementById("optionconfrimdate").value=checkNull(curMaster.optionconfrimdate);
    	handleRadio("curMcardnoinsert.checkoutflag",checkNull(curMaster.checkoutflag));
    	handleRadio("curMcardnoinsert.billflag",checkNull(curMaster.billflag));
    	//cinsertcardtypemanager.selectValue("");
    	document.getElementById("dcardType").value="";
		document.getElementById("dcardFromNo").value="";
		document.getElementById("dcardToNo").value="";
		document.getElementById("dcardNum").value="";
		document.getElementById("dcardPrice").value="";
		document.getElementById("dcardAmt").value="";
		var strBillImageurl="D:\\amnBillImage\\cc001_"+checkNull(curMaster.bcinsertcompid)+"_"+checkNull(curMaster.bcinsertbillid)+".JPG";
		document.getElementById("billImage").src=contextURL + "/loadBillImage.action?strBillImageurl="+strBillImageurl;
   		if(document.getElementById("cinsertper").value=="")
		{
			pageWriteState();
		}
    }
    
    function pageWriteState()
    {
	    try
	    {
	    	document.getElementById("cinsertper").readOnly="";
	   		cinsertwaremanager.setEnabled();
	   		//cinsertcardtypemanager.setEnabled();
	   		document.getElementById("dcardType").disabled=false;
	    	document.getElementById("cinsertperName").readOnly="";
	    	document.getElementById("billno").readOnly="";
	    	document.getElementById("checkoutmark").readOnly="";
	    	document.getElementById("createcompname").readOnly="";
	    	document.getElementById("billno").readOnly="";
			document.getElementById("dcardFromNo").readOnly="";
			document.getElementById("dcardToNo").readOnly="";
			document.getElementById("dcardPrice").readOnly="";
    	}catch(e){alert(e.message);}
	}
    
    function pageReadState()
    {
    	try
	    {
	    	document.getElementById("cinsertper").readOnly="readOnly";
	   		cinsertwaremanager.setDisabled();
	   		//cinsertcardtypemanager.setDisabled();
	   		document.getElementById("dcardType").disabled=true;
	    	document.getElementById("cinsertperName").readOnly="readOnly";
	    	document.getElementById("billno").readOnly="readOnly";
	    	document.getElementById("checkoutmark").readOnly="readOnly";
	    	document.getElementById("createcompname").readOnly="readOnly";
	    	document.getElementById("billno").readOnly="readOnly";
			document.getElementById("dcardFromNo").readOnly="readOnly";
			document.getElementById("dcardToNo").readOnly="readOnly";
			document.getElementById("dcardPrice").readOnly="readOnly";
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
        		 strCurCompId=data.bcinsertcompid;
        		 strCurBillId=data.bcinsertbillid;
       			 var params = "strCurCompId="+strCurCompId;
       			 params =params+ "&strCurBillId="+strCurBillId;
				 var myAjax = new parent.Ajax.Request(
						"cc001/loadDcardnoinsert.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {							
								var responsetext = eval( "("+request.responseText+")");	
								if(responsetext.lsDcardnoinsert!=null && responsetext.lsDcardnoinsert.length>0)
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: responsetext.lsDcardnoinsert,Total: responsetext.lsDcardnoinsert.length});
					            	commoninfodivdetial.loadData(true);            	
						   		}
						   		else
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
					            	commoninfodivdetial.loadData(true);  
						   		}
	   							loadCurMaster(responsetext.curMcardnoinsert);
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
		        		loadCardInsertList();
		        	} 		
		        	
		        }
		      });  
    	}
    	catch(e){alert(e.message);}
    }
    
    function editCurRecord()
    {
    	if(parent.hasFunctionRights( "CC001",  "UR_POST")!=true)
        {
       		$.ligerDialog.warn("该用户没有保存权限,请确认!");
       	 	return;
        }
	   if(fromValidate.element($("#cinsertper"))==false ||
			fromValidate.element($("#cinsertperName"))==false ||
			fromValidate.element($("#checkoutmark"))==false ||
			fromValidate.element($("#billno"))==false )
		{
			$.ligerDialog.error('填入信息有误,请确认!');
			return;
		}
    	var queryStringTmp=$('#cardInsertForm').serialize();
		var requestUrl ="cc001/post.action";
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
     
			 
     //新增行
     function addRecord()
     {
     		if(parent.hasFunctionRights( "CC001",  "UR_MODIFY")!=true)
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
				                bcinsertcompid: "",
				                bcinsertbillid: "",
				                cinsertdate: "",
				                cinserttime: ""
				             
				            }, row, false);
				var params = "";			
    	     	var requestUrl ="cc001/add.action"; 
				var responseMethod="addMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params );
	        }
     }
     function addMessage(request)
     {
	    	try
	        {
	        	var responsetext = eval("(" + request.responseText + ")");
	    		loadCurMaster(responsetext.curMcardnoinsert);
		   		commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivdetial.loadData(true);  
		   	}catch(e){alert(e.message);}
     }
     
    //删除行
    function deleteCurRecord()
    {
       	if(parent.hasFunctionRights( "CC001",  "UR_DELETE")!=true)
        {
        	 $.ligerDialog.warn("该用户没有删除权限,请确认!");
        	 return;
        }
        var requestUrl ="cc001/delete.action";
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
   			document.getElementById("cinsertperName").value="";
   			return ;
   		}
   		var requestUrl ="cc001/validateInserper.action";
        var params="strCurCompId="+document.getElementById("cinsertcompid").value;
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
	       		document.getElementById("cinsertperName").value=responsetext.strCurEmpName;
	       	}
	       	else
	       	{
	       		document.getElementById("cinsertperName").value="";
	       		$.ligerDialog.warn(strMessage);
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	
   	function editCurDetailInfo()
   	{
   		if(document.getElementById("dcardType").value==""
   		|| document.getElementById("dcardFromNo").value==""
   		|| document.getElementById("dcardToNo").value=="")
   		{
   			$.ligerDialog.warn("入库号段信息不完整,请确认!");
   			 return;
   		}
   		var row = commoninfodivdetial.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
		commoninfodivdetial.addRow({ 
				                cardtypeid: document.getElementById("dcardType").value,
				               // cardtypeName: cinsertcardtypemanager.getText(),
				                cardnofrom: document.getElementById("dcardFromNo").value,
				                cardnoto: document.getElementById("dcardToNo").value,
				             	cardnum:document.getElementById("dcardNum").value,
				             	cardprice:document.getElementById("dcardPrice").value,
				             	cardamt:document.getElementById("dcardAmt").value
				            }, row, false);
		//cinsertcardtypemanager.selectValue("");
		document.getElementById("dcardFromNo").value="";
		document.getElementById("dcardToNo").value="";
		document.getElementById("dcardNum").value="";
		document.getElementById("dcardPrice").value="";
		document.getElementById("dcardAmt").value="";
   	}
   	
   	//-------------------验证卡号段---------------------Start
   	/**********检查起始编号是否存在**************/
function checkExitsFrom(obj)
{
     try
     {
         if(obj.value=="")
	     {
	     	 	document.getElementById("dcardFromNo").value="";//清空起始卡号
	  			document.getElementById("dcardNum").value=0;//清空卡数量
	        	return ;
	     }
	     if($("#factcInsertware").val()=="" || document.getElementById("dcardType").value=="")
	     {
	     		$.ligerDialog.warn("请输入入库仓库和入库卡类型");
	     		obj.value="";
	     		return;
	     }
	     var Lastlength=document.getElementById("cardIdLength").value;//获得系统参数中卡的序号长度
	     if(obj.value.length*1<Lastlength*1)
	     {
	     		$.ligerDialog.warn("输入卡号段的长度与系统设置不符!");
	     		obj.value="";
	     		return;
	     }
	     var params =  "strCardfrom=" +obj.value;//输入的起始卡号
	     params+="&strWareId="+$("#factcInsertware").val();//输入的仓库编号
	     params+="&strCardType="+document.getElementById("dcardType").value;//输入的卡类型
	     var requestUrl = "cc001/checkCardNoFrom.action";
	     var responseMethod = "checkCardNoFromMessage";
	     sendRequestForParams_p(requestUrl,responseMethod,params );
     }catch(e){alert(e.message);}
}

function checkCardNoFromMessage(request)//验证起始卡号的回调
{
    
    var responsetext = eval("(" + request.responseText + ")");
	var strMessage = responsetext.strMessage;
	if(strMessage!="")
	{
	  	$.ligerDialog.warn(strMessage);
	  	document.getElementById("dcardFromNo").value="";//清空起始卡号
	  	document.getElementById("dcardNum").value=0;//清空卡数量
	}
}
/***********检查结束编号是否存在************/
function checkExitsEnd(obj)//验证结束编号
{
     
     if(obj.value=="")
     {
	       	document.getElementById("dcardToNo").value="";//清空起始卡号
	  		document.getElementById("dcardNum").value=0;//清空卡数量
	        return ;
     }
     if($("#factcInsertware").val()=="" || document.getElementById("dcardType").value=="")
	 {
	     		$.ligerDialog.warn("请输入入库仓库和入库卡类型");
	     		obj.value="";
	     		return;
	 }
	 var Lastlength=document.getElementById("cardIdLength").value;//获得系统参数中卡的序号长度
	 if(obj.value.length*1<Lastlength*1)
	 {
	    	$.ligerDialog.warn("输入卡号段的长度与系统设置不符!");
	    	obj.value="";
	    	return;
	 }
     var params =  "strCardend=" +obj.value;//输入的结束卡号
     params+="&strWareId="+$("#factcInsertware").val();//输入的仓库编号
	 params+="&strCardType="+document.getElementById("dcardType").value;//输入的卡类型
     var requestUrl = "cc001/checkCardNoEnd.action";
     var responseMethod = "checkCardNoEndMessage";
     sendRequestForParams_p(requestUrl,responseMethod,params );
    
}

function checkCardNoEndMessage(request)//验证结束卡号的回调函数
{
     var responsetext = eval("(" + request.responseText + ")");
	 var strMessage = responsetext.strMessage;
	 if(strMessage!="")
	 {
	   	$.ligerDialog.warn(strMessage);
	 	document.getElementById("dcardToNo").value="";//清空起始卡号
	  	document.getElementById("dcardNum").value=0;//清空卡数量
	 }
}

function checkRangeExits()//验证数入的卡范围是否包含已存在的范围
{
   	try
   	{
	      //确定明细中输入卡范围的行数
	     
	      var startCardNo=document.getElementById("dcardFromNo").value;//输入的起始卡卡号
	      var endCardNo=document.getElementById("dcardToNo").value;//输入的结束卡卡号
	      if(startCardNo!=""&&endCardNo!="")
	      {
	      		var params =  "strCardfrom=" +startCardNo;//起始卡号
	      			params+=  "&strCardend=" +endCardNo;//结束卡号
	      			params+="&strWareId="+$("#factcInsertware").val();//输入的仓库编号
					params+="&strCardType="+document.getElementById("dcardType").value;//输入的卡类型
	      		var requestUrl = "cc001/checkCardRange.action";
	      		var responseMethod = "checkCardRangeMessage";
	      		sendRequestForParams_p(requestUrl,responseMethod,params );
	     }
   	}
  	catch(e){alert(e.message);}
}

function checkCardRangeMessage(request)//验证卡范围是否存在的回调
{
    var responsetext = eval("(" + request.responseText + ")");
	var strmessage = responsetext.strMessage;
	if(strmessage!="")
	{
	  	$.ligerDialog.warn(strmessage);
	  	document.getElementById("dcardToNo").value="";//清空起始卡号
	  	document.getElementById("dcardNum").value=0;//清空卡数量
	}
	else
	{
		companyCount();
	}
}

/** 是否都是数字 **/
function isNumber_E(sNum) {
	  var   re   =   /^\d+(?=\.\d+$|$)/   
      return   re.test(sNum);
}

function companyCount()//计算输入的卡范围之间卡的数量
{
	try
	{
	  		var cardId_f=document.getElementById("dcardFromNo").value;//获得起始卡号
	 	 	var cardId_e=document.getElementById("dcardToNo").value;//获得结束卡号
	  		var Lastlength=document.getElementById("cardIdLength").value;//获得系统参数中卡的序号长度
	  		var numberOfCardFilter=document.getElementById("numberOfCardFilter").value;//获得启动参数中卡好末尾要过滤的数字
	  		var cardofLeft=cardId_f;
	  		var cardofRight=cardId_e;
	  		if(Lastlength==0)//判断是否设置了系统中的卡序号长度
	  		{
	    		$.ligerDialog.warn("请设置系统参数中的卡序号长度!");
	    		return ;
	  		}
	  		//判断输入的卡号中序号长度内是否都是数字
	  		if(!isNumber_E(cardofLeft.substring(cardofLeft.length-Lastlength,cardofLeft.length)))
	  		{
	    		$.ligerDialog.warn("起始卡号输入不完整!");
	    		document.getElementById("dcardFromNo").value="";
	     		document.getElementById("dcardNum").value=0;
	   	 		return ;
	  		}
	  		//判断输入的卡号中序号长度内是否都是数字
	  		if(!isNumber_E(cardofRight.substring(cardofRight.length-Lastlength,cardofRight.length)))
	  		{
	    		$.ligerDialog.warn("结束卡号输入不完整!");
	    		document.getElementById("dcardToNo").value="";
	     		document.getElementById("dcardNum").value=0;
	   	 		return ;
	  		}
	  		//判断起始卡号与结束卡号的卡头数字是否相同--即卡类型是否相同
	  		if(cardId_f!=""&&cardId_e!=""&&(cardId_f.length!=cardId_e.length||cardId_f.substring(0,cardId_f.length-Lastlength*1)!=cardId_e.substring(0,cardId_e.length-Lastlength*1)))
			{
			       $.ligerDialog.warn("批次卡的型号(卡号长度)不同!");
			       obj.value="";
			       return ;
			} 
	   		cardId_f=cardId_f.substring(cardId_f.length-Lastlength,cardId_f.length)*1; //获得起始卡的卡序号
	   		cardId_e=cardId_e.substring(cardId_e.length-Lastlength,cardId_e.length)*1;//获得结束卡的卡序号
	   		var totalcount=cardId_e-cardId_f+1;//算出这段范围中不去过滤数字的总数量
		   	if(numberOfCardFilter=="")//判断过滤的参数是否存在,若不存在,则不需要过滤任何的数字
		   	{
		     	if((cardId_f+"")!=""&&(cardId_e+"")!="")
		     	{
		        	if(totalcount<0)//若起始卡号大于输入卡号,则总数量为0,反之为量卡号之差
		        	{
		          		document.getElementById("dcardNum").value=0;
		        	}
		        	else
		        	{
		          		document.getElementById("dcardNum").value=totalcount*1;
		        	}
		     	}
		  	}
		  	else
		  	{
		     	cardId_f=cardId_f+"";
		     	cardId_e=cardId_e+"";
		     	//若存在,先算出要过滤的个数--每相隔一个十,就有一个过滤的数字,先默认起始编号,结束编号是从小于它的最大的整十数开始
		     	if(cardId_f!=""&&cardId_e!="")
		     	{
		       	 	var count = 0;
		       	 	var fromTrgNum = cardId_f.substring(cardId_f.length-1,cardId_f.length)*1;
		       	 	var endTrgNum = cardId_e.substring(cardId_e.length-1,cardId_e.length)*1;
		       	 	//判断起始编号上的个位数是否小于过滤的数字,若小于,则要过滤的个数添加1
		       	 	if(fromTrgNum<=Number(numberOfCardFilter))
		       	 	count++;
		       	 	//判断结束编号上的各位数是否大于过滤的数字,若大于,则要过滤的个数添加1
		       	 	if(endTrgNum>=Number(numberOfCardFilter))
		       	 		count++;
		       		//判断起始编号与结束编号之间相隔几个整十,因为默认的起始编号已经包含了四,则必须减去一个要过滤的数
		       		count = count + ((cardId_e*1-endTrgNum)-(cardId_f*1-fromTrgNum))/10 -1; 
		       		//计算起始编号到结束编号之间去掉过滤之后的数后的总数量
		       		totalout4Count=totalcount-count;
		       		if(totalout4Count<0)
		        	{
		            	document.getElementById("dcardNum").value=0;
		        	}
		      	 	else
		        	{
		               document.getElementById("dcardNum").value=totalout4Count;
		        	}
		     	}
		   }
	  }catch(e){alert(e.message);}
 
}

function companyPrice(obj)//计算卡金额
{
  		var cardcount=document.getElementById("dcardNum").value;
	  	if(cardcount=="")
	  	{
	    	$.ligerDialog.warn("请先填入所需卡数量!");
	    	obj.value=0;
	    	return ;
	  	}
	 	var tP=(document.getElementById("dcardNum").value*1*obj.value)+"";
	   	if(tP.indexOf(".")==-1)//判断输入的金额是否带小数
	     {
	        document.getElementById("dcardAmt").value=tP*1;
	     }
	     else
	     {
	        document.getElementById("dcardAmt").value=tP.substring(0,tP.indexOf(".")+ 3);
	     }
	 
}


   function loadBillImage(strBillImageurl)
   {
   		document.getElementById("billImage").src=contextURL + "/loadBillImage.action?strBillImageurl="+strBillImageurl;
   		/*var requestUrl ="loadBillImage.action";
        var params="strBillImageurl="+strBillImageurl;
		var responseMethod="loadBillImageMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );  */
   }
   
   function loadBillImageMessage(request)
   {
	 	try
		{
	       var responsetext = eval("(" + request.responseText + ")");
	       document.getElementById("billImage").src=responsetext.inputStream;
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	
  	function UpLoadJPG_onclick() //上传
	{ 
			var filepath = "D:\\";
			var compid=document.getElementById("cinsertcompid").value;
    	    var billid=document.getElementById("cinsertbillid").value;
			var filename ="cc001_"+compid+"_"+billid;
			captrue.vSetImageQuality(50);			
			var str =captrue.bSaveJPG(filepath,filename);
			var imageurl=filepath+filename+".JPG";		
			var str=captrue.bUpLoadImageEx(imageurl, "10.0.0.10",80, "/AMNWebControl/UploadServlet",true,true);
			if(str==true)
				{
					$.ligerDialog.confirm('确认上传 该单据图片', function (result)
			     	{
			   		if( result==true)
	            		{
							loadBillImage(imageurl);
						}
					});
				}
		}
		function Start1_onclick() //启动
	    {
	  		var str=captrue.bStopPlay();
	  		var str = captrue.bStartPlay();
	    }
	    function Stop_onclick()   //关闭
	    {
	        var str=captrue.bStopPlay();
	    }
	    function ParaSet_onclick()//参数设置
	    {
	     	var str=captrue.displayVideoPara();
	    }
	    function ParaSetPIN_onclick()  //PIN设置
		{
			var str=captrue.vSetCapturePin();
  			captrue.bStartPlay();
		}
		   function itemclick(item)
        {
            alert(item.text);
        }