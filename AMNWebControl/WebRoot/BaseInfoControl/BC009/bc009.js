
   
   	var commoninfodivsecond=null;//供应商列表
   	var strCurSupperId="";
   	var chooseData = [{ choose: 1, text: '无合作' }, { choose: 0, text: '已合作'}];
   	var curSupperInfoDate=null;
   	var bc009layout=null;
   	var fromValidate=null;
    $(function ()
   	{
	   try
	   {
	   		  //布局
            bc009layout= $("#bc009layout").ligerLayout({ leftWidth: 270,rightWidth: 250,  allowBottomResize: false, allowLeftResize: false });
            commoninfodivsecond=$("#commoninfodivsecond").ligerGrid({
                columns: [
                { display: '供应商ID', 	name: 'supplierid',  	width:50,align: 'left' , frozen: true},
                { display: '供应商名称', 	name: 'suppliername', 	width:100,align: 'left' , frozen: true},
	            { display: '供应商简称', 	name: 'suppliersname',  width:70,align: 'left' ,minWidth: 60},
	            { display: '电话', 		name: 'supplierphone', 	width:70,align: 'right' },
	            { display: '传真', 		name: 'supplierfex', 	width:60,align: 'right' },
	            { display: '电子邮件', 	name: 'supplieremail', 	width:80,align: 'right' },
	            { display: '电子主页', 	name: 'supplierurl', 	width:80,align: 'right' },
	            { display: '发票地址', 	name: 'supplieraddress',width:120,align: 'right' },
	            { display: '邮编', 		name: 'supplierpos', 	width:50,align: 'right' },
	            { display: '备注', 		name: 'supplierremark', width:180,align: 'right' },
	            { display: '联系人', 		name: 'miantoucher', 	width:60,align: 'right' },
	            { display: '手机号码', 	name: 'suppliermobilephone', width:70,align: 'right' },
	            { display: '是否合作', 	name: 'supplierstate',  width:50,align: 'left' , 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.supplierstate == 1) return '已合作';
	                      return '无合作';
	                }
	            }
                ],  pageSize:20, 
                data:null,      
                width: bc009layout.centerWidth*1+bc009layout.leftWidth*1+bc009layout.rightWidth*1-30,
                height:'95%',
                clickToEdit: false,   enabledEdit: false,  
                rownumbers:true,usePager:true, where : f_getWhere(),  
  
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                }, 
                toolbar: { items: [
                { text: '增加', click: itemclick_supperInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
                { line: true },
                { text: '修改', click: itemclick_supperInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' },
                { line: true },
                { text: '删除', click: itemclick_supperInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' },
                { line: true },
                { text: '刷新', click: itemclick_supperInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/refresh.gif' }
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
          	$("#pageloading").hide(); 
      		loadSupperList();
   		}catch(e){alert(e.message);}
    });
  
    
    function loadSupperList()
    {
    	var requestUrl ="bc009/loadSupplierinfo.action"; 
		var responseMethod="loadSupplierinfoMessage";				
		sendRequestForParams_p(requestUrl,responseMethod,"" );
    }
   
   function loadSupplierinfoMessage(request)
   {
       		try
        	{
 
        	var responsetext = eval("(" + request.responseText + ")");
    
	   		if(responsetext.lsSuppliers!=null && responsetext.lsSuppliers.length>0)
	   		{
	   			curSupperInfoDate={Rows: responsetext.lsSuppliers,Total: responsetext.lsSuppliers.length};
	   			commoninfodivsecond.options.data=$.extend(true, {},curSupperInfoDate);
            	commoninfodivsecond.loadData(true);  
            	commoninfodivsecond.select(0);           	
	   		}

	   		
	   		}catch(e){alert(e.message);}
    }
   
   function loadSelecDetialData(data, rowindex, rowobj)
   {
        try{
        		 strCurSupperId=data.supplierid;
       			 var params = "strCurSupperId="+data.supplierid;
				 var myAjax = new parent.Ajax.Request(
						"bc009/loadCurSupplier.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {							
								var action = eval( "("+request.responseText+")");	
								document.getElementById("supplierid").value=checkNull(action.curSupplier.supplierid);
								document.getElementById("suppliername").value=checkNull(action.curSupplier.suppliername);
								document.getElementById("suppliersname").value=checkNull(action.curSupplier.suppliersname);
								document.getElementById("supplierphone").value=checkNull(action.curSupplier.supplierphone);
								document.getElementById("supplierfex").value=checkNull(action.curSupplier.supplierfex);
								document.getElementById("supplieremail").value=checkNull(action.curSupplier.supplieremail);
								document.getElementById("supplierurl").value=checkNull(action.curSupplier.supplierurl);
								document.getElementById("supplieraddress").value=checkNull(action.curSupplier.supplieraddress);
								document.getElementById("supplierpos").value=checkNull(action.curSupplier.supplierpos);
								document.getElementById("supplierremark").value=checkNull(action.curSupplier.supplierremark);
								document.getElementById("miantoucher").value=checkNull(action.curSupplier.miantoucher);
								document.getElementById("supplierpassword").value=checkNull(action.curSupplier.supplierpassword);
								document.getElementById("suppliermobilephone").value=checkNull(action.curSupplier.suppliermobilephone);
								handleRadio("curSupplier.supplierstate",checkNull(action.curSupplier.supplierstate));
							},
							asynchronous:true
						});			  
          }catch(e){alert(e.message);}
   }
   
   
     // ------------------------操作用户 ----------------End
     // ------------------------查询信息 ----------------Start
     function f_searchSupperInfo()
     {
    
     	   commoninfodivsecond.options.data = $.extend(true, {}, curSupperInfoDate);
     	
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
             
               	return (rowdata.supplierid.indexOf(key) > -1 ||  rowdata.suppliername.indexOf(key) > -1 );
              
                
            };
            return clause; 
        }
    	catch(e){alert(e.message);}
     }
    
     function itemclick_supperInfo(item)
     {
     	try
    	{
	    	 $.ligerDialog.confirm('确认  供应商资料 '+item.text+' 操作', function (result)
		     {
		     		if( result==true)
            		{
			          	if(item.text=="增加")
			        	{
			        	 	addRecord();
			        	}
			        	else if(item.text=="修改")
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
     		if(parent.hasFunctionRights( "BC009",  "UR_MODIFY")!=true)
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
				                supplierid: "",
				                suppliername: "",
				                suppliersname: "",
				                supplierphone: "",
				                supplierfex: "",       
				                supplieremail: "",       
				                supplierurl: "", 
				                supplieraddress: "", 
				                supplierpos: "", 
				                supplierremark: "", 
				                miantoucher: "", 
				                suppliermobilephone: "", 
				                supplierstate: "" 
				            }, row, false);
				 document.getElementById("supplierid").readOnly="";
	        }
     }
     
     //保存
	 function editCurRecord()
        { 
        	if(parent.hasFunctionRights( "BC009",  "UR_POST")!=true)
        	{
        		 $.ligerDialog.warn("该用户没有修改权限,请确认!");
        		 return;
        	}

        	try
			{
				 if(fromValidate.element($("#supplierid"))==false ||
				 	fromValidate.element($("#suppliername"))==false ||
				 	fromValidate.element($("#suppliersname"))==false ||
				 	fromValidate.element($("#supplierphone"))==false ||
				 	fromValidate.element($("#supplierfex"))==false ||
				 	fromValidate.element($("#supplierpassword"))==false ||
				 	fromValidate.element($("#suppliermobilephone"))==false
				 )
				 {
				 	$.ligerDialog.error('填入信息有误,请确认!');
				 	return;
				 }
			
				var queryStringTmp=$('#detailForm').serialize();//serialize('#detailForm');

				var requestUrl ="bc009/post.action";
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
	        		 curSecondCommonKey="";
	        		 curSecondCommonValue="";
	        		  $.ligerDialog.success("更新成功!");
	        		   document.getElementById("supplierid").readOnly="readOnly";
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
        	if(parent.hasFunctionRights( "BC009",  "UR_DELETE")!=true)
        	{
        		 $.ligerDialog.warn("该用户没有删除权限,请确认!");
        		 return;
        	}
        	var requestUrl ="bc009/delete.action";
        	var params="strCurSupperId="+strCurSupperId;
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