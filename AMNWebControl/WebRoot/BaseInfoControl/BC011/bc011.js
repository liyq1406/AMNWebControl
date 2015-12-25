
   	var compTree = null;//主明细	
   	var compTreeManager = null;//主明细	
   	var commoninfodivsecond=null;//卡类型明细
   	var commoninfodivthirth=null;//转卡规则明细
   	var curCompid="";
   	var curbillid="";
   	var curBillCompid="";

   	var promotionsTypeData = [{ choose: 1, text: '服务项目' }, { choose: 2, text: '开卡金额'}, { choose: 3, text: '充值金额'}, { choose: 4, text: '单卡开卡金额'}, { choose: 5, text: '单卡充值金额'}];
   	var chooseData = [{ choose: 1, text: '已审核' }, { choose: 0, text: '未审核'}];
   	var curPromotionsDate=null;
   	var bc011layout=null;
   	var newPromotionsInfo=null;
   	var addBandDialog=null;
    $(function ()
   	{
	   try
	   {
	   		  //布局
            bc011layout= $("#bc011layout").ligerLayout({ leftWidth: 270,rightWidth: 285,  allowBottomResize: false, allowLeftResize: false });
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
          	commoninfodivsecond=$("#commoninfodivsecond").ligerGrid({
                columns: [
                { display: '录入单号', 	name: 'bbillid', 	width:120,align: 'left' , frozen: true},
	            { display: '促销类型', 	name: 'promotionstype',  width:80,align: 'left' ,minWidth: 60 , 
	                editor: { type: 'select', data: promotionsTypeData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.promotionstype == 1) return '服务项目';
	                     else if (item.promotionstype == 2) return '开卡金额';
	                     else if (item.promotionstype == 3) return '充值金额';
	                     else if (item.promotionstype == 4) return '单卡开卡金额';
	                     else if (item.promotionstype == 5) return '单卡充值金额';
	                      return '请选择..';
	                }},
	            { display: '编号/卡类型', 		name: 'promotionscode', 	width:100,align: 'left' },
	            { display: '名称', 				name: 'promotionsname', 	width:120,align: 'left' },
	            { display: '促销门店', 		name: 'promotionsstore', 	width:100,align: 'left' },
	            
	            { display: '促销价', 		name: 'promotionsvalue', 	width:50,align: 'right' },
	            { display: '起始日期', 	name: 'startdate', 	width:80,align: 'left' },
	            { display: '结束日期', 	name: 'enddate', 	width:80,align: 'left' },
	            { display: '是否审核', 	name: 'promotionsstate',  width:60,align: 'left' , 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.promotionsstate == 1) return '已审核';
	                      return '未审核';
	                }
	            }
                ],  pageSize:20, 
                data:null,      
                width: bc011layout.centerWidth*1+bc011layout.leftWidth*1+bc011layout.rightWidth*1-30,
                height:'95%',
                clickToEdit: false,   enabledEdit: false,  
                rownumbers:true,usePager:true, where : f_getWhere(),  
  
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                }, 
                toolbar: { items: [
                { text: '增加', click: itemclick_promotionsInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
                { line: true },
                //{ text: '保存', click: itemclick_promotionsInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' },
                //{ line: true },
                { text: '删除', click: itemclick_promotionsInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' },
                { line: true },
                { text: '刷新', click: itemclick_promotionsInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/refresh.gif' }
                ]
                }	   
            });
        	 $.validator.addMethod(
                    "notnull",
                    function (value, element, regexp)
                    {
                        if (!value) return true;
                        return !$(element).hasClass("l-text-field-null");
                    },
                    "不能为空"
            );
            
             $("#confirmCurRootInfo").ligerButton(
	         {
	             text: '保存促销单', width: 160,
		         click: function ()
		         {
		             //confirmCurRootInfo();
		             editCurRecord();
		         }
	         });
				
			 $("#copyCurRootInfo").ligerButton(
	         {
	             text: '拷贝促销单', width: 160,
		         click: function ()
		         {
		             copyCurRootInfo();
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
            $("#startdate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });   
            $("#enddate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
          	$("#pageloading").hide(); 
          	f_selectNode();
   		}catch(e){alert(e.message);}
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
        		curCompid=note.data.id;
        
       			var params = "strCurCompId="+note.data.id;				
     			var requestUrl ="bc011/loadPromotions.action"; 
				var responseMethod="loadPromotionsMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params );
          }catch(e){alert(e.message);}
    }
    function loadPromotionsMessage(request)
    {
    	try
        {  		
    		var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.lsPromotionsinfos!=null && responsetext.lsPromotionsinfos.length>0)
	   		{
	   	
	   			curPromotionsDate={Rows: responsetext.lsPromotionsinfos,Total: responsetext.lsPromotionsinfos.length};
	   			commoninfodivsecond.options.data=$.extend(true, {},curPromotionsDate);
            	commoninfodivsecond.loadData(true);  
            	commoninfodivsecond.select(0);
            	newPromotionsInfo=responsetext.newPromotionsInfo;  
            	loadPageValue(newPromotionsInfo);        	
	   		}
	   		else
	   		{	   	
	   			commoninfodivsecond.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivsecond.loadData(true); 
            	addRecord();     
	   		}
	   		

	   		
	   	}catch(e){alert(e.message);}
    }
    
    function loadSelecDetialData(data, rowindex, rowobj)
    {
    	curbillid=data.bbillid;
    	curBillCompid=data.bcompid;
    	var params = "strCurCompId="+curBillCompid;	
    	params =params+ "&strCurBillId="+curbillid;	
    	params =params+ "&strPromotionCompId="+data.bpromotionsstore;		
     	var requestUrl ="bc011/loadPromotionsInfo.action"; 
		var responseMethod="loadPromotionsInfoMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
    	
    }
    function loadPromotionsInfoMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.curPromotionsinfo!=null )
	   		{
	   			loadPageValue(responsetext.curPromotionsinfo);   	
	   		}
	   	}catch(e){alert(e.message);}
    }
  
     // ------------------------操作用户 ----------------End
     // ------------------------查询信息 ----------------Start
     function f_searchStaffInfo()
     {
    
     	   commoninfodivsecond.options.data = $.extend(true, {}, curPromotionsDate);
     	
           commoninfodivsecond.loadData(f_getWhere());
     }
     function f_getWhere()
     {
     	try
    	{
            if (!commoninfodivsecond) return null;
            var clause = function (rowdata, rowindex)
            {
                var key = $("#txtSearchKey").val();
             
               return (rowdata.promotionscode.indexOf(key) > -1 ||  rowdata.promotionsstore.indexOf(key) > -1 );
              
                
            };
            return clause; 
        }
    	catch(e){alert(e.message);}
     }
    
     function itemclick_promotionsInfo(item)
     {
     	try
    	{
	    	 $.ligerDialog.confirm('确认对 '+curCompid+' 促销信息 '+item.text+' 操作', function (result)
		     {
		     		if( result==true)
            		{
			          	if(item.text=="增加")
			        	{
			        	 	addRecord();
			        	}
			        	else if(item.text=="保存")
			        	{
			        	 	editCurRecord();
			        	} 	
			        	else if(item.text=="删除")
			        	{
			        	 	deleteRecord();
			        	} 	
			        	else if(item.text=="刷新")
        	 			{
			        		loadSupperList();
			        	}
			        }
		     });  
    	}
    	catch(e){alert(e.message);}
     }
      
     function addRecord()
     {
     		if(parent.hasFunctionRights( "BC011",  "UR_MODIFY")!=true)
	        {
	        	$.ligerDialog.warn("该用户没有编辑权限,请确认!");
	        }
	        else
	        {
	        	var row = commoninfodivsecond.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
				   commoninfodivsecond.addRow({ 
				                bbillid: "",
				                promotionstype: "",
				                promotionscode: "",
				                promotionsstore: "",       
				                promotionsvalue: "",       
				                startdate: "", 
				                enddate: "", 
				                promotionsstate: ""
				             
				            }, row, false);
				var params = "strCurCompId="+curCompid;			
    	     	var requestUrl ="bc011/add.action"; 
				var responseMethod="addMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params );
	        }
     }
     function addMessage(request)
     {
	    	try
	        {
	        	var responsetext = eval("(" + request.responseText + ")");
	    
	    		loadPageValue(responsetext.newPromotionsInfo);
		   		
		   	}catch(e){alert(e.message);}
     }
     
     //保存
	 function editCurRecord()
        { 
        	if(parent.hasFunctionRights( "BC011",  "UR_POST")!=true)
        	{
        		 $.ligerDialog.warn("该用户没有保存权限,请确认!");
        		 return;
        	}

        	try
			{
				 if(document.getElementById("promotionsstate").value==1)
				 {
				 	$.ligerDialog.error('该促销单已审核,请确认!');
				 	return;
				 }
				 if(fromValidate.element($("#promotionsstore"))==false ||
				 	fromValidate.element($("#promotionscode"))==false ||
				 	fromValidate.element($("#promotionsvalue"))==false
				 )
				 {
				 	$.ligerDialog.error('填入信息有误,请确认!');
				 	return;
				 }
			
				var queryStringTmp=$('#detailForm').serialize();//serialize('#detailForm');

				var requestUrl ="bc011/post.action";
				var params=queryStringTmp;
	
				var responseMethod="editMessage";				
				sendRequestForParams_p(requestUrl,responseMethod,params ); 
			}
			catch(e)
			{
				alert(e.message);
			}
            
        }
        
        function editMessage(request)
        {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		 $.ligerDialog.success("更新成功!");
	        		
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
        
        //删除行
        function deleteRecord()
        {
        	if(parent.hasFunctionRights( "BC011",  "UR_DELETE")!=true)
        	{
        		 $.ligerDialog.warn("该用户没有删除权限,请确认!");
        		 return;
        	}
        	var requestUrl ="bc011/delete.action";
        	var params = "strCurCompId="+curBillCompid;			
    		params =params+ "&strCurBillId="+curbillid;	
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
	        		  commoninfodivsecond.deleteSelectedRow();
	        		  $.ligerDialog.success("删除成功!");
	        		  loadPageValue(responsetext.newPromotionsInfo);
	        	}
	        	else
	        	{
	        		 $.ligerDialog.warn(strMessage);
	        	}
        	}
			catch(e)
			{
				 $.ligerDialog.warn(e.message);
			}
        }
     function loadPageValue(curObj)
     {
     	document.getElementById("compid").value=checkNull(curObj.id.compid);
     	document.getElementById("billid").value=checkNull(curObj.id.billid);
     	document.getElementById("promotionstype").value=checkNull(curObj.promotionstype);  
     	document.getElementById("promotionscode").value=checkNull(curObj.promotionscode);  
     	document.getElementById("promotionsname").value=checkNull(curObj.promotionsname);  
     	document.getElementById("promotionsstore").value=checkNull(curObj.promotionsstore);  
     	document.getElementById("promotionsstorename").value=checkNull(curObj.promotionsstorename);  
     	document.getElementById("promotionsvalue").value=checkNull(curObj.promotionsvalue);  
     	document.getElementById("startdate").value=checkNull(curObj.startdate);  
     	document.getElementById("enddate").value=checkNull(curObj.enddate);  
     	document.getElementById("promotionsreason").value=checkNull(curObj.promotionsreason);  
		document.getElementById("promotionsstate").value=checkNull(curObj.promotionsstate); 
     	document.getElementById("invalid").value=checkNull(curObj.invalid);
     }
     
	 function validateItem(obj)
	 {
	 	if(obj.value=="")
	 	{
	 		document.getElementById("promotionsname").value="";
	 	}
	 	else
	 	{
	 		if(document.getElementById("promotionstype").value==6 || document.getElementById("promotionstype").value==7)
	 		{
	 			var changevalue=parent.loadCommonControlValue("XMLB",0,obj.value);
	 			if(checkNull(changevalue)=="")
	 			{
	 				$.ligerDialog.warn("项目大类不存在,请确认!");
        		 	return;
	 			}
	 			else
	 			{
	 				document.getElementById("promotionsname").value=changevalue;
	 				document.getElementById("promotionsstore").focus();
	 				document.getElementById("promotionsstore").select();
	 			}
	 		}
	 		else
	 		{
	 			var requestUrl ="bc011/validateItemId.action";
        		var params = "strCurCompId="+document.getElementById("compid").value;	
        		params=params+"&strCurItemId="+obj.value;	
        		params=params+"&iCurItemType="+document.getElementById("promotionstype").value;	
				var responseMethod="validateItemIdMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params ); 
			}
	 	}
	 }
	  function validateItemIdMessage(request)
        {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	       		 
	        		  document.getElementById("promotionsname").value=responsetext.strCurItemName;	        		  
	        	}
	        	else
	        	{
	        		 $.ligerDialog.warn(strMessage);
	        		 document.getElementById("promotionscode").value="";
	        		 document.getElementById("promotionsname").value="";
	        	}
        	}
			catch(e)
			{
				 $.ligerDialog.warn(e.message);
			}
        }
        
         function validateCompId(obj)
		 {
		 	if(obj.value=="")
		 	{
		 		document.getElementById("promotionsstorename").value="";
		 	}
		 	else
		 	{
		 		var requestUrl ="bc011/validateCompId.action";
	        	var params = "strCurCompId="+obj.value;	
	        	 params =params+ "&strCurBillCompId="+document.getElementById("compid").value;
				var responseMethod="validateCompIdMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params ); 
		 	}
		 }
	  function validateCompIdMessage(request)
        {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	       		 
	        		  document.getElementById("promotionsstorename").value=responsetext.strCurCompName;	        		  
	        	}
	        	else
	        	{
	        		 $.ligerDialog.warn(strMessage);
	        		 document.getElementById("promotionsstore").value="";
	        		 document.getElementById("promotionsstorename").value="";
	        	}
        	}
			catch(e)
			{
				 $.ligerDialog.warn(e.message);
			}
        }
        
         //删除行
        function confirmCurRootInfo()
        {
        	if(parent.hasFunctionRights( "BC011",  "UR_DELETE")!=true)
        	{
        		 $.ligerDialog.warn("该用户没有删除权限,请确认!");
        		 return;
        	}
        	var requestUrl ="bc011/confirmCurRootInfo.action";
        	var params = "strCurCompId="+curBillCompid;			
    		params =params+ "&strCurBillId="+curbillid;	
			var responseMethod="confirmCurRootInfoMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params ); 	
            
           
        }
        function confirmCurRootInfoMessage(request)
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
	        		 $.ligerDialog.warn(strMessage);
	        	}
        	}
			catch(e)
			{
				 $.ligerDialog.warn(e.message);
			}
        }
        
        function copyCurRootInfo()
        {
        	addBandDialog=$.ligerDialog.open({ height: 600, url: contextURL+'/BaseInfoControl/BC011/handBandCompNo.jsp', width: 300, showMax: false, showToggle: false, showMin: false, isResize: false, title: '选择拷贝门店' });
		
        }
        function copyPromotionsToOtherCompId(bandComps)
        {
        	var requestUrl ="bc011/copyPromotionsInfos.action";
        	var params = "strCurCompId="+curBillCompid;			
    		params =params+ "&strCurBillId="+curbillid;	
        	params =params+ "&strBandComps="+bandComps;	
			var responseMethod="copyPromotionsInfosMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params ); 
        }
        
        function copyPromotionsInfosMessage(request)
        {
    		var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	       		 
	        	$.ligerDialog.success("拷贝成功!");
	        	addBandDialog.close();
	        }
	        else
	        {
	        	$.ligerDialog.warn(strMessage);
	        }
	        
        }