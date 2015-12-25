
   	var compTree = null;//主明细	
   	var compTreeManager = null;//主明细	
   	var commoninfodivsecond=null;//卡类型明细
   	var commoninfodivthirth=null;//转卡规则明细
   	var curCompid="";
   	var curbillid="";
   	var curBillCompid="";
	var curDetialRecord=null;
   	var appflowTypeData = [{ choose: 1, text: '服务项目' }, { choose: 2, text: '开卡金额'}, { choose: 3, text: '充值金额'}, { choose: 4, text: '单卡开卡金额'}, { choose: 5, text: '单卡充值金额'}];
   	var chooseData = [{ choose: 1, text: '已审核' }, { choose: 0, text: '未审核'}];
   	var curappflowDate=null;
   	var ac012layout=null;
   	var newStoreflowinfo=null;
    $(function ()
   	{
	   try
	   {
	   		  //布局
            ac012layout= $("#ac012layout").ligerLayout({ leftWidth: 270,rightWidth: 285,  allowBottomResize: false, allowLeftResize: false });
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
                { display: '申请类型', 	name: 'appflowtype',  width:90,align: 'left' ,minWidth: 60 , 
	                editor: { type: 'select', data: appflowTypeData, valueField: 'choose' },
	                render: function (item)
	                {
	                      if (item.appflowtype == 1) return '换卡';
	                      else if (item.appflowtype == 2) return '补卡';
	                      else if (item.appflowtype == 3) return '退卡';
	                      else if (item.appflowtype == 4) return '单卡开卡金额';
	                      else if (item.appflowtype == 5) return '单卡充值金额';
	                      else if (item.appflowtype == 6) return '修改会员资料';
	                      else if (item.appflowtype == 7) return '退疗程';
	                      else if (item.appflowtype == 8) return '项目变价';
	                      else if (item.appflowtype == 9) return '产品变价';
	                      return '请选择..';
	                }},
	            { display: '项目/卖品/卡号', 	name: 'appflowcode', 	width:100,align: 'left' },
	            { display: '名称', 			name: 'appflowname', 	width:120,align: 'left' },
	            { display: '申请门店', 		name: 'appflowstore', 	width:100,align: 'left' },
	            { display: '生效值', 			name: 'appflowvalue', 	width:120,align: 'left' },
	            { display: '起始日期', 		name: 'startdate', 	width:80,align: 'left' },
	            { display: '结束日期', 		name: 'enddate', 	width:80,align: 'left' },
	            { display: '是否审核', 		name: 'appflowstate',  width:60,align: 'left' , 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.appflowstate == 1) return '已审核';
	                      return '未审核';
	                }
	            },
	            {name: 'bbillid', 	width:1,hide:true}
                ],  pageSize:20, 
                data:null,      
                width: ac012layout.centerWidth*1+ac012layout.leftWidth*1+ac012layout.rightWidth*1-30,
                height:'95%',
                clickToEdit: false,   enabledEdit: false,  
                rownumbers:true,usePager:true, where : f_getWhere(),  
  
                onSelectRow : function (data, rowindex, rowobj)
                {
                	curDetialRecord=data;
                    loadSelecDetialData(data, rowindex, rowobj);
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
	             text: '保存流程单', width: 160,
		         click: function ()
		         {
		             editCurRecord();
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
     			var requestUrl ="ac012/loadappflow.action"; 
				var responseMethod="loadappflowMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params );
          }catch(e){alert(e.message);}
    }
    function loadappflowMessage(request)
    {
    	try
        {  		
    		var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.lsStoreflowinfos!=null && responsetext.lsStoreflowinfos.length>0)
	   		{
	   	
	   			curappflowDate={Rows: responsetext.lsStoreflowinfos,Total: responsetext.lsStoreflowinfos.length};
	   			commoninfodivsecond.options.data=$.extend(true, {},curappflowDate);
            	commoninfodivsecond.loadData(true);  
           		addRecord(); 	
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
    	var params = "strCurCompId="+data.bcompid;			
    	params =params+ "&strCurBillId="+curbillid;		
     	var requestUrl ="ac012/loadStoreflowinfo.action"; 
		var responseMethod="loadStoreflowinfoMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
    	
    }
    function loadStoreflowinfoMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.curStoreflowinfo!=null )
	   		{
	   			loadPageValue(responsetext.curStoreflowinfo);   	
	   		}
	   	}catch(e){alert(e.message);}
    }
  
     // ------------------------操作用户 ----------------End
     // ------------------------查询信息 ----------------Start
     function f_searchStaffInfo()
     {
    
     	   commoninfodivsecond.options.data = $.extend(true, {}, curappflowDate);
     	
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
             
               return (rowdata.appflowcode.indexOf(key) > -1 ||  rowdata.appflowstore.indexOf(key) > -1 );
              
                
            };
            return clause; 
        }
    	catch(e){alert(e.message);}
     }
    
     function itemclick_Storeflowinfo(item)
     {
     	try
    	{
	    	 $.ligerDialog.confirm('确认对 '+curCompid+' 申请信息 '+item.text+' 操作', function (result)
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
     		if(parent.hasFunctionRights( "AC012",  "UR_MODIFY")!=true)
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
				                appflowtype: "",
				                appflowcode: "",
				                appflowstore: "",       
				                appflowvalue: "",       
				                startdate: "", 
				                enddate: "", 
				                appflowstate: ""
				             
				            }, row, false);
				var params = "strCurCompId="+curCompid;			
    	     	var requestUrl ="ac012/add.action"; 
				var responseMethod="addMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params );
	        }
     }
     function addMessage(request)
     {
	    	try
	        {
	        	var responsetext = eval("(" + request.responseText + ")");
	    		loadPageValue(responsetext.newStoreflowinfo);
	    		if(commoninfodivsecond.rows.length>0)
	    		{
	    			curDetialRecord=commoninfodivsecond.getRow(commoninfodivsecond.rows.length-1);
	    		}
		   		commoninfodivsecond.updateRow(curDetialRecord,{bcompid :responsetext.newStoreflowinfo.id.compid,bbillid: responsetext.newStoreflowinfo.id.billid});  
		   	}catch(e){alert(e.message);}
     }
     
     //保存
	 function editCurRecord()
        { 
        	if(parent.hasFunctionRights( "AC012",  "UR_POST")!=true)
        	{
        		 $.ligerDialog.warn("该用户没有保存权限,请确认!");
        		 return;
        	}

        	try
			{
				 if(document.getElementById("appflowstate").value==1)
				 {
				 	$.ligerDialog.error('该申请单已审核,请确认!');
				 	return;
				 }
				 if(fromValidate.element($("#appflowstore"))==false ||
				 	fromValidate.element($("#appflowcode"))==false ||
				 	fromValidate.element($("#appflowvalue"))==false
				 )
				 {
				 	$.ligerDialog.error('填入信息有误,请确认!');
				 	return;
				 }
			
				var queryStringTmp=$('#detailForm').serialize();//serialize('#detailForm');

				var requestUrl ="ac012/post.action";
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
        	if(parent.hasFunctionRights( "AC012",  "UR_DELETE")!=true)
        	{
        		 $.ligerDialog.warn("该用户没有删除权限,请确认!");
        		 return;
        	}
        	var requestUrl ="ac012/delete.action";
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
	        		  loadPageValue(responsetext.newStoreflowinfo);
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
     	document.getElementById("appflowtype").value=checkNull(curObj.appflowtype);  
     	document.getElementById("appflowcode").value=checkNull(curObj.appflowcode);  
     	document.getElementById("appflowname").value=checkNull(curObj.appflowname);  
     	document.getElementById("appflowstore").value=checkNull(curObj.appflowstore);  
     	document.getElementById("appflowstorename").value=checkNull(curObj.appflowstorename);  
     	document.getElementById("appflowvalue").value=checkNull(curObj.appflowvalue);  
     	document.getElementById("startdate").value=checkNull(curObj.startdate);  
     	document.getElementById("enddate").value=checkNull(curObj.enddate);  
     	document.getElementById("appflowreason").value=checkNull(curObj.appflowreason);  
		document.getElementById("appflowstate").value=checkNull(curObj.appflowstate); 
     	document.getElementById("invalid").value=checkNull(curObj.invalid);
     }
     
	 function validateItem(obj)
	 {
	 	if(obj.value=="")
	 	{
	 		document.getElementById("appflowname").value="";
	 	}
	 	else
	 	{
	 		var requestUrl ="ac012/validateItemId.action";
        	var params = "strCurCompId="+document.getElementById("compid").value;	
        	params=params+"&strCurItemId="+obj.value;	
        	params=params+"&iCurItemType="+document.getElementById("appflowtype").value;	
			var responseMethod="validateItemIdMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params ); 
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
	        		  document.getElementById("appflowname").value=responsetext.strCurItemName;	        		  
	        	}
	        	else
	        	{
	        		 $.ligerDialog.warn(strMessage);
	        		 document.getElementById("appflowcode").value="";
	        		 document.getElementById("appflowname").value="";
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
		 		document.getElementById("appflowstorename").value="";
		 	}
		 	else
		 	{
		 		var requestUrl ="ac012/validateCompId.action";
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
	        		  document.getElementById("appflowstorename").value=responsetext.strCurCompName;	        		  
	        	}
	        	else
	        	{
	        		 $.ligerDialog.warn(strMessage);
	        		 document.getElementById("appflowstore").value="";
	        		 document.getElementById("appflowstorename").value="";
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
        	if(parent.hasFunctionRights( "AC012",  "UR_DELETE")!=true)
        	{
        		 $.ligerDialog.warn("该用户没有删除权限,请确认!");
        		 return;
        	}
        	var requestUrl ="ac012/confirmCurRootInfo.action";
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
        
        
        	
	function hotKeyOfSelf(key)
		{
			if(key==13)//回车
			{
				hotKeyEnter();
			}
			else if(key==114)//F3
			{
				addRecord();
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
		
		function  hotKeyEnter()
		{
		
			try
			{
				window.event.keyCode=9; //tab
				window.event.returnValue=true;
				
			}
			catch(e){alert(e.message);}
				
		}  