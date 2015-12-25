   var compTree = null;//主明细	
   var compTreeManager = null;//主明细	
   var curCompid="";		
   var wareHouseDetail = null;				//仓库明细
   var commoninfodivScheduling=null;		//班次明细
   var roomDetail=null;		//房间明细
   var city=null;
   var bc001layout=null;
   var fromValidate=null;
   var compstatemanager="";
   var compmodemanager="";
   var BC001Tab=null;
   var MoveNodeItems = new Array();
   var IsMoveNodeFlag=false;
   var chooseData = [{ choose: '0800', text: '8:00' },{ choose: '0900', text: '9:00' },{ choose: '1000', text: '10:00' },{ choose: '1100', text: '11:00' },{ choose: '1200', text: '12:00' },{ choose: '1300', text: '13:00' },{ choose: '1400', text: '14:00' },{ choose: '1500', text: '15:00' },{ choose: '1600', text: '16:00' },{ choose: '1700', text: '17:00' },{ choose: '1800', text: '18:00' },{ choose: '1900', text: '19:00' },{ choose: '2000', text: '20:00' },{ choose: '2100', text: '21:00' },{ choose: '2200', text: '22:00' },{ choose: '2300', text: '23:00' },{ choose: '0000', text: '00:00' },{ choose: '0100', text: '01:00' },{ choose: '0200', text: '02:00' },{ choose: '0300', text: '03:00' },{ choose: '0400', text: '04:00' },{ choose: '0500', text: '05:00' }];
   	//移动过得节点
	function moveNodeInfo(parentNodeId, curNodeId) 
	{
		this.parentNodeId = parentNodeId;
		this.curNodeId = curNodeId;
	}
   $(function ()
   {
	   try
	   { 
	   		bc001layout= $("#bc001layout").ligerLayout({ leftWidth: 270,rightWidth: 140, bottomHeight:250, allowBottomResize: false, allowLeftResize: false, isRightCollapse:true });
           
	   		menu = $.ligerMenu({ top: 100, left: 100, width: 120, items:
            [
            	{ text: '移除', click: itemclick_chain },
            	{ line: true },
            	{ text: '重置父节点', click: itemclick_chain }
            ]
            });
	   		//门店树形结构
   			compTree=$("#companyTree").ligerTree(
        	{
           		data: parent.complinkInfo,
           		idFieldName :'id',           		
           		onSelect: compSelect,
           		nodeWidth: 140,
           		checkbox: false,
           		//nodeDraggable: true,
           		onContextmenu: function (node, e)
	            { 
	                actionNodeID = node.data.text;
	                menu.show({ top: e.pageY, left: e.pageX });
	                return false;
	            }
          	});
          	compTreeManager = $("#companyTree").ligerGetTreeManager();
          
          	
          	$("#BC001Tab").ligerTab();
            BC001Tab = $("#BC001Tab").ligerGetTabManager();
            
          	//仓库
        	wareHouseDetail = $("#commoninfodivWareHouse").ligerGrid({
                columns:
                            [
                            { display: '仓库编号', name: 'bwarehouseno', width: 80,type:'text',editor: { type: 'text'}  },
                            { display: '仓库名称', name: 'warehousename', width: 100,type:'text',editor: { type: 'text' }  },
                            { display: '仓管联络人', name: 'warehousecontact', width: 80,editor: { type: 'text' }  },
                            { display: '联络电话', name: 'warehousephone', width: 80,editor: { type: 'text' }  },
                            { display: '仓库传真', name: 'warehousefax', width: 70,editor: { type: 'text' }  },
                            { display: '仓库地址', name: 'warehouseaddress',  width: 160,editor: { type: 'text' } }
                            ], isScroll: false, showToggleColBtn: false, width: '100%', enabledEdit: true, 
                data: null , showTitle: false,usePager:false,frozen:false,
                toolbar: { items: [
                { text: '增加', click: itemclick_wareHouse, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
                { line: true },
                //{ text: '修改', click: itemclick_wareHouse, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' },
                //{ line: true },
                { text: '删除', click: itemclick_wareHouse, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' }
                ]
                }	          
            });  
            
           commoninfodivScheduling = $("#commoninfodivScheduling").ligerGrid({
                columns:
                            [
                            { display: '班次编号', name: 'schedulno', 			width: 80,type:'text',editor: { type: 'text'}  },
                            { display: '班次名称', name: 'schedulname',			width: 140,type:'text',editor: { type: 'text' }  },
                            { display: '起始时间', name: 'fromtime', 				width: 80, 
			                	editor: { type: 'select', data: chooseData, valueField: 'choose' },
			                    render: function (item)
			                    {
			                        if (item.fromtime == '0800') return '8:00';
			                        if (item.fromtime == '0900') return '9:00';
			                        if (item.fromtime == '1000') return '10:00';
			                        if (item.fromtime == '1100') return '11:00';
			                        if (item.fromtime == '1200') return '12:00';
			                        if (item.fromtime == '1300') return '13:00';
			                        if (item.fromtime == '1400') return '14:00';
			                        if (item.fromtime == '1500') return '15:00';
			                        if (item.fromtime == '1600') return '16:00';
			                        if (item.fromtime == '1700') return '17:00';
			                        if (item.fromtime == '1800') return '18:00';
			                        if (item.fromtime == '1900') return '19:00';
			                        if (item.fromtime == '2000') return '20:00';
			                        if (item.fromtime == '2100') return '21:00';
			                        if (item.fromtime == '2200') return '22:00';
			                        if (item.fromtime == '2300') return '23:00';
			                        if (item.fromtime == '0000') return '00:00';
			                        if (item.fromtime == '0100') return '01:00';
			                        if (item.fromtime == '0200') return '02:00';
			                        if (item.fromtime == '0300') return '03:00';
			                        if (item.fromtime == '0400') return '04:00';
			                        if (item.fromtime == '0500') return '05:00';
			                        return '';
			                    } 
			                 },
                            { display: '结束时间', name: 'totime', 				width: 80 , 
			                	editor: { type: 'select', data: chooseData, valueField: 'choose' },
			                    render: function (item)
			                    {
			                        if (item.totime == '0800') return '8:00';
			                        if (item.totime == '0900') return '9:00';
			                        if (item.totime == '1000') return '10:00';
			                        if (item.totime == '1100') return '11:00';
			                        if (item.totime == '1200') return '12:00';
			                        if (item.totime == '1300') return '13:00';
			                        if (item.totime == '1400') return '14:00';
			                        if (item.totime == '1500') return '15:00';
			                        if (item.totime == '1600') return '16:00';
			                        if (item.totime == '1700') return '17:00';
			                        if (item.totime == '1800') return '18:00';
			                        if (item.totime == '1900') return '19:00';
			                        if (item.totime == '2000') return '20:00';
			                        if (item.totime == '2100') return '21:00';
			                        if (item.totime == '2200') return '22:00';
			                        if (item.totime == '2300') return '23:00';
			                        if (item.totime == '0000') return '00:00';
			                        if (item.totime == '0100') return '01:00';
			                        if (item.totime == '0200') return '02:00';
			                        if (item.totime == '0300') return '03:00';
			                        if (item.totime == '0400') return '04:00';
			                        if (item.totime == '0500') return '05:00';
			                        return '';
			                    }
			                 }
                            ], isScroll: false, showToggleColBtn: false, width: '390',enabledEdit: true, data: null ,usePager:false,
                toolbar: { items: [
                { text: '增加', click: itemclick_Scheduling, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
                { line: true },
                { text: '移除', click: itemclick_Scheduling, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' }
                ]
                }	          
            }); 
           	//房间信息
			roomDetail = $("#roomDetail").ligerGrid({
                columns:
                            [
                            { display: '房间编号', name: 'roomno', width: 80,type:'text',editor: { type: 'text' }  },
                            { display: '房间名称', name: 'roomname', width: 100,editor: { type: 'text' }  }
                            ], isScroll: false, showToggleColBtn: false, width: '185',enabledEdit: true, data: null ,usePager:false,
                toolbar: { items: [
                { text: '增加', click: itemclick_Room, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
                { line: true },
                { text: '移除', click: itemclick_Room, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' }
                ]
                }	          
            }); 

       		$("#pageloading").hide(); 
       
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
       		 var compstateData=JSON.parse(parent.loadCommonControlDate("MDZT",0));
           	 compstatemanager = $("#compstate").ligerComboBox({ data: compstateData, isMultiSelect: false,valueFieldID: 'factCompstate',width:'120'});	
           	 var  compmodeData=JSON.parse(parent.loadCommonControlDate("MDMS",0));
           	 compmodemanager = $("#compmode").ligerComboBox({ data: compmodeData, isMultiSelect: false,valueFieldID: 'factCompmode',width:'120' });           	 
           	 // $("form").ligerForm();
          	 $("#editCurRecord").ligerButton(
	         {
	             text: '保存设置', width: 60,
		         click: function ()
		         {
		             editCurRecord();
		         }
	         });
          	 $("#getAddress").ligerButton(
          			 {
          				 text: '获取经纬度', width: 60,
          				 click: function ()
          				 {
          					codeAddress();
          				 }
          			 });
	         $("#editCurRootInfo").ligerButton(
	         {
	             text: '保存连锁设置', width: 100,
		         click: function ()
		         {
		             editCurRootInfo();
		         }
	         });
	          $("#createMangerPass").ligerButton(
	         {
	             text: '初始化经理卡密码', width: 140,
		         click: function ()
		         {
		             createMangerPass();
		         }
	         });
	          $("#uploadStaffImg").ligerButton(
	     	         {
	     	             text: '上传门店照', width: 100,
	     		         click: function ()
	     		         {
	     		             uploadStaffImg();
	     		         }
	     	         });
	         $("#createdate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
	       	 f_selectNode();
	       	 addSchedulingRecord();
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
   function compSelect(note)
   {
        try{
        		 curCompid=note.data.id;
       			 var params = "strCurCompId="+note.data.id;
				 var myAjax = new parent.Ajax.Request(
						"bc001/loadCurWareHouse.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {						
								var action = eval( "("+request.responseText+")");
								document.getElementById("compno").value=checkNull(action.curCompanyinfo.compno);
								document.getElementById("compname").value=checkNull(action.curCompanyinfo.compname);
								//document.getElementById("compstate").value=checkNull(action.curCompanyinfo.compstate);  
								 compstatemanager.selectValue(checkNull(action.curCompanyinfo.compstate));
								document.getElementById("compphone").value=checkNull(action.curCompanyinfo.compphone);
								document.getElementById("compaddress").value=checkNull(action.curCompanyinfo.compaddress);
								document.getElementById("comptradelicense").value=checkNull(action.curCompanyinfo.comptradelicense);
								document.getElementById("compfex").value=checkNull(action.curCompanyinfo.compfex);
								document.getElementById("compzipcode").value=checkNull(action.curCompanyinfo.compzipcode);
								document.getElementById("compadslno").value=checkNull(action.curCompanyinfo.compadslno);
								document.getElementById("compadslpassword").value=checkNull(action.curCompanyinfo.compadslpassword);
								document.getElementById("comparea").value=checkNull(action.curCompanyinfo.comparea);
								document.getElementById("comprent").value=checkNull(action.curCompanyinfo.comprent);
								document.getElementById("compipaddress").value=checkNull(action.curCompanyinfo.compipaddress);
								document.getElementById("compipaddressex").value=checkNull(action.curCompanyinfo.compipaddressex);
								document.getElementById("compresponsible").value=checkNull(action.curCompanyinfo.compresponsible);
								//document.getElementById("compmode").value=checkNull(action.curCompanyinfo.compmode);
								document.getElementById("createdate").value=checkNull(action.curCompanyinfo.createdate);
								document.getElementById("region").value=checkNull(action.curCompanyinfo.region);
								loadAllSortByAreaId(document.getElementById("region").value);
								document.getElementById("mangerPassword").value=checkNull(action.curCompanyinfo.mangerPassword);
								document.getElementById("mirrornumber").value=checkNull(action.curCompanyinfo.mirrornumber);
								document.getElementById("shopwf1").value=checkNull(action.curCompanyinfo.shopwf1);
								document.getElementById("shopwf2").value=checkNull(action.curCompanyinfo.shopwf2);
								document.getElementById("shopwf3").value=checkNull(action.curCompanyinfo.shopwf3);
								document.getElementById("shopwf4").value=checkNull(action.curCompanyinfo.shopwf4);
								document.getElementById("shopwf5").value=checkNull(action.curCompanyinfo.shopwf5);
								document.getElementById("shopwf6").value=checkNull(action.curCompanyinfo.shopwf6);
								document.getElementById("shopwf7").value=checkNull(action.curCompanyinfo.shopwf7);
								document.getElementById("shopwf8").value=checkNull(action.curCompanyinfo.shopwf8);
								document.getElementById("shopwf9").value=checkNull(action.curCompanyinfo.shopwf9);
								document.getElementById("ipadpwd").value=checkNull(action.curCompanyinfo.ipadpwd);
								document.getElementById("shopwf10").value=checkNull(action.curCompanyinfo.shopwf10);
								document.getElementById("imgUrl").value=checkNull(action.curCompanyinfo.imgUrl);
								document.getElementById("xcoordinate").value=checkNull(action.curCompanyinfo.xcoordinate);
								document.getElementById("ycoordinate").value=checkNull(action.curCompanyinfo.ycoordinate);
								document.getElementById("model").value=checkNull(action.curCompanyinfo.model);
								compmodemanager.selectValue(checkNull(action.curCompanyinfo.compmode));
								
								if(action.lsCompwarehouses!=null && action.lsCompwarehouses.length>0)
								{
									var curHousedate={Rows:action.lsCompwarehouses,Total: action.lsCompwarehouses.length};	
									wareHouseDetail.options.data=$.extend(true, {},curHousedate);
            						wareHouseDetail.loadData(true);	
            					}
            					else
            					{
            						wareHouseDetail.options.data=$.extend(true, {},{Rows:null,Total: 0});
            						wareHouseDetail.loadData(true);	
            					}
            					
								if(action.lsCompscheduling!=null && action.lsCompscheduling.length>0)
								{
									var curSchedulingdate={Rows:action.lsCompscheduling,Total: action.lsCompscheduling.length};	
									commoninfodivScheduling.options.data=$.extend(true, {},curSchedulingdate);
            						commoninfodivScheduling.loadData(true);	
            					}	
            					else
            					{
            						commoninfodivScheduling.options.data=$.extend(true, {},{Rows:null,Total: 0});
            						commoninfodivScheduling.loadData(true);	
            						addSchedulingRecord();
            					}	
            					//房间信息
            					if(action.lsRoominfo!=null && action.lsRoominfo.length>0)
								{
									var curRoominfodate={Rows:action.lsRoominfo,Total: action.lsRoominfo.length};	
									roomDetail.options.data=$.extend(true, {},curRoominfodate);
            						roomDetail.loadData(true);	
            					}	
            					else
            					{
            						roomDetail.options.data=$.extend(true, {},{Rows:null,Total: 0});
            						roomDetail.loadData(true);	
            						addRoomRecord();
            					}	
            					if(checkNull(action.curCompanyinfo.compaddress)!="")
								{
									 //city.search(checkNull(action.curCompanyinfo.compaddress)); //查找城市
								}					
							},
							asynchronous:true
						});			  
          }catch(e){alert(e.message);}
   }
   
   function itemclick_wareHouse(item)
   {
      if(checkNull(curCompid)=="")
      {
      	 	$.ligerDialog.warn("还未选中需修改的门店信息,请确认!");
      		return;
      }
      $.ligerDialog.confirm('确认提交 '+curCompid+' 门店资料 '+item.text+' 操作', function (result)
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
        	 	deleteCurRecord();
        	} 	
      });  
      		 
   	}
   	
   	
   function itemclick_Scheduling(item)
   {
      if(checkNull(curCompid)=="")
      {
      	 	$.ligerDialog.warn("还未选中需修改的门店信息,请确认!");
      		return;
      }
      if(item.text=="增加")
       {
        	 	addSchedulingRecord();
       }
       else if(item.text=="移除")
       {
        	 	commoninfodivScheduling.deleteSelectedRow();
       } 
      		 
   	}
   	
   function itemclick_Room(item)
   {
      if(checkNull(curCompid)=="")
      {
      	 	$.ligerDialog.warn("还未选中需修改的门店信息,请确认!");
      		return;
      }
      if(item.text=="增加")
       {
        	 	addRoomRecord();
       }
       else if(item.text=="移除")
       {
        	 	roomDetail.deleteSelectedRow();
       } 
      		 
   	}
   	
      	
    //新增
    function addRecord()
    {
       	try
		{
	        if(parent.hasFunctionRights( "BC001",  "UR_MODIFY")!=true)
	        {
	        	$.ligerDialog.warn("该用户没有编辑权限,请确认!");
	        }
	        else
	        {
	        	var row = wareHouseDetail.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
				   wareHouseDetail.addRow({ 
				                bwarehouseno: "",
				                warehousename: "",
				                warehousecontact: "",
				                warehousephone: "",
				                warehousefax: "",       
				                warehouseaddress: ""        
				            }, row, false);
	        }
        }
		catch(e)
		{
			alert(e.message);
		}
	} 
	
	//新增
    function addSchedulingRecord()
    {
       	try
		{
	        
	        	var row = commoninfodivScheduling.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
				   commoninfodivScheduling.addRow({ 
				                schedulno: "",
				                schedulname: "",
				                fromtime: "",
				                totime: ""        
				            }, row, false);
        }
		catch(e)
		{
			alert(e.message);
		}
	}

	function addRoomRecord()
    {
       	try
		{
	        
	        	var row = roomDetail.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
				   roomDetail.addRow({ 
				                roomno: "",
				                roomname: ""     
				            }, row, false);
        }
		catch(e)
		{
			alert(e.message);
		}
	}
	 
	//删除
	function deleteCurRecord()
	{
		if(parent.hasFunctionRights( "BC001",  "UR_DELETE")!=true)
        {
        	 $.ligerDialog.warn("该用户没有删除权限,请确认!");
        	 return;
        }
        var row = wareHouseDetail.getSelectedRow();
        if (!row)
        { 
         	$.ligerDialog.warn('请选择要删除的仓库行');
            return;
        }
        else if(checkNull(row.bwarehouseno)=="")
        {
        	$.ligerDialog.warn('请选择要删除的仓库行');
            return;
        }
        else
        {
        	var requestUrl ="bc001/delete.action";
        	var params="strCurWareHouseId="+row.bwarehouseno;
			params=params+"&strCurCompId="+curCompid;
			var responseMethod="deleteMessage";				
			sendRequestForParams_p(requestUrl,responseMethod,params ); 
        }
            
	}
	
	function deleteMessage(request)
    {
   		var responsetext = eval("(" + request.responseText + ")");
	    var strMessage=responsetext.strMessage;
	    if(checkNull(strMessage)=="")
	    {       		 
	    	$.ligerDialog.success("删除成功!");
	    	wareHouseDetail.deleteSelectedRow();
	    }
	    else
	    {
	    	alert(strMessage);
	    }
        	
   	}
	
	//保存
	 function editCurRecord()
        { 
        	if(parent.hasFunctionRights( "BC001",  "UR_POST")!=true)
        	{
        		 $.ligerDialog.warn("该用户没有修改权限,请确认!");
        		 return;
        	}

        	try
			{
				 if(fromValidate.element($("#compname"))==false ||
				 	fromValidate.element($("#compphone"))==false ||
				 	fromValidate.element($("#compaddress"))==false ||
				 	fromValidate.element($("#comptradelicense"))==false ||
				 	fromValidate.element($("#compfex"))==false ||
				 	fromValidate.element($("#compzipcode"))==false ||
				 	fromValidate.element($("#compadslno"))==false ||
				 	fromValidate.element($("#compadslpassword"))==false ||
				 	fromValidate.element($("#comparea"))==false ||
				 	fromValidate.element($("#comprent"))==false||
				 	fromValidate.element($("#mirrornumber"))==false
				 )
				 {
				 	$.ligerDialog.error('填入信息有误,请确认!');
				 	return;
				 }
				document.getElementById("region").value=document.getElementById("area").value;
				var jsonParam="";
				var strJsonSchedulParam="";
				var strJsonRoomParam="";
				var curjosnparam="";
				var needReplaceStr="";
				var data = wareHouseDetail.getData();
            	jsonParam=JSON.stringify(data);
            	data = commoninfodivScheduling.getData();
            	strJsonSchedulParam=JSON.stringify(data);
            	data = roomDetail.getData();
            	strJsonRoomParam=JSON.stringify(data);
            	/*if(jsonParam.indexOf("_id")>-1)
				{
				    needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				    jsonParam=jsonParam.replace(needReplaceStr,"");
				}	*/
				var requestUrl ="bc001/post.action";
				var params="strJsonParam="+jsonParam+"";
				params=params+"&strJsonSchedulParam="+strJsonSchedulParam;
				params=params+"&strJsonRoomParam="+strJsonRoomParam;
				params=params+"&strCurCompId="+curCompid;
				params=params+"&curCompanyinfo.compno="+document.getElementById("compno").value;
				params=params+"&curCompanyinfo.compname="+document.getElementById("compname").value;
				params=params+"&curCompanyinfo.compstate="+$("#factCompstate").val();
				params=params+"&curCompanyinfo.compphone="+document.getElementById("compphone").value;
				params=params+"&curCompanyinfo.compaddress="+document.getElementById("compaddress").value;
				params=params+"&curCompanyinfo.comptradelicense="+document.getElementById("comptradelicense").value;
				params=params+"&curCompanyinfo.compfex="+document.getElementById("compfex").value;
				params=params+"&curCompanyinfo.compzipcode="+document.getElementById("compzipcode").value;
				params=params+"&curCompanyinfo.compadslno="+document.getElementById("compadslno").value;
				params=params+"&curCompanyinfo.compadslpassword="+document.getElementById("compadslpassword").value;
				params=params+"&curCompanyinfo.comparea="+document.getElementById("comparea").value;
				params=params+"&curCompanyinfo.comprent="+document.getElementById("comprent").value;
				params=params+"&curCompanyinfo.compresponsible="+document.getElementById("compresponsible").value;
				params=params+"&curCompanyinfo.compmode="+$("#factCompmode").val();
				params=params+"&curCompanyinfo.createdate="+document.getElementById("createdate").value;
				params=params+"&curCompanyinfo.region="+document.getElementById("region").value;
				params=params+"&curCompanyinfo.mangerPassword="+document.getElementById("mangerPassword").value;
				params=params+"&curCompanyinfo.compipaddress="+document.getElementById("compipaddress").value;
				params=params+"&curCompanyinfo.compipaddressex="+document.getElementById("compipaddressex").value;
				params=params+"&curCompanyinfo.mirrornumber="+document.getElementById("mirrornumber").value;
				params=params+"&curCompanyinfo.shopwf1="+document.getElementById("shopwf1").value;
				params=params+"&curCompanyinfo.shopwf2="+document.getElementById("shopwf2").value;
				params=params+"&curCompanyinfo.shopwf3="+document.getElementById("shopwf3").value;
				params=params+"&curCompanyinfo.shopwf4="+document.getElementById("shopwf4").value;
				params=params+"&curCompanyinfo.shopwf5="+document.getElementById("shopwf5").value;
				params=params+"&curCompanyinfo.shopwf6="+document.getElementById("shopwf6").value;
				params=params+"&curCompanyinfo.shopwf7="+document.getElementById("shopwf7").value;
				params=params+"&curCompanyinfo.shopwf8="+document.getElementById("shopwf8").value;
				params=params+"&curCompanyinfo.shopwf9="+document.getElementById("shopwf9").value;
				params=params+"&curCompanyinfo.ipadpwd="+document.getElementById("ipadpwd").value;
				params=params+"&curCompanyinfo.shopwf10="+document.getElementById("shopwf10").value;
				params=params+"&curCompanyinfo.imgUrl="+document.getElementById("imgUrl").value;
				params=params+"&curCompanyinfo.xcoordinate="+document.getElementById("xcoordinate").value;
				params=params+"&curCompanyinfo.ycoordinate="+document.getElementById("ycoordinate").value;
				//params=params+"&curCompanyinfo.model="+document.getElementById("model").value;
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
        
        //初始化腾讯地图
        var geocoder = null;
        var initMap = function() {
            geocoder = new qq.maps.Geocoder();
        }
        
        function codeAddress() {
            var address = document.getElementById("compaddress").value;
            //对指定地址进行解析
            geocoder.getLocation(address);
            //设置服务请求成功的回调函数
            geocoder.setComplete(function(result) {
                var res = result.detail.location;
                document.getElementById("res").value=res;
                var rest = obj2str(document.getElementById("res").value);
                document.getElementById("xcoordinate").value=rest.split("\"")[1].split(",")[0];
                document.getElementById("ycoordinate").value=rest.split("\"")[1].split(",")[1]
            });
            //若服务请求失败，则运行以下函数
            geocoder.setError(function() {
                alert("出错了，请输入正确的地址！！！");
            });
        }
        
		//修改连锁结构树
		function editCurRootInfo()
		{
		
			try
		   {
			if(parent.hasFunctionRights( "BC001",  "UR_SPECIAL_CHECK")!=true)
        	{
        		 $.ligerDialog.warn("该用户没有复合权限,不能重建连锁结构,请确认!");
        		 return;
        	}
        	if(parent.localCompid!="001")
        	{
        		 $.ligerDialog.warn("重建连锁结构只能在总部进行,请确认!");
        		 return;
        	}
        	var p= compTree.options;         
        	var ddate=compTree.getDataByID(parent.localCompid);        	
        	var jsonParam='{"curcompno":"'+ddate[p.idFieldName]+'","parentcompno":"0","complevel":"1"}';
        	var childrenFirstlv=ddate.children;
        	var childrenSecondlv=null;
        	var childrenthirthlv=null;
        	var firstNodeExistsFlag=0;
        	var secondNodeExistsFlag=0;
        	var thirthNodeExistsFlag=0;
        	for(var i=0;i<childrenFirstlv.length;i++)//2级门店
        	{
        		firstNodeExistsFlag=0;
        		for(var z=0;z<MoveNodeItems.length;z++)
            	{
            		if(MoveNodeItems[z].curNodeId==childrenFirstlv[i][p.idFieldName])
            		{
            			if(MoveNodeItems[z].parentNodeId==ddate[p.idFieldName])
            			{
        					jsonParam=jsonParam+',';
               				jsonParam=jsonParam+'{"curcompno":"'+childrenFirstlv[i][p.idFieldName]+'","parentcompno":"'+parent.localCompid+'","complevel":"2"}';
        					firstNodeExistsFlag=1;
        					break;
        				}
        				firstNodeExistsFlag=2;
               		}               		
             	}
             	if(firstNodeExistsFlag==0)
             	{
             		jsonParam=jsonParam+',';
               		jsonParam=jsonParam+'{"curcompno":"'+childrenFirstlv[i][p.idFieldName]+'","parentcompno":"'+parent.localCompid+'","complevel":"2"}';
        		}
        		if(compTreeManager.hasChildren(childrenFirstlv[i])==true && (firstNodeExistsFlag==0 || firstNodeExistsFlag==1 ))
        		{
        			childrenSecondlv=childrenFirstlv[i].children;        			
	        		for(var j=0;j<childrenSecondlv.length;j++)//3级门店
	        		{ 
	        			secondNodeExistsFlag=0;
	        			for(var z=0;z<MoveNodeItems.length;z++)
		            	{
		            		if(MoveNodeItems[z].curNodeId==childrenSecondlv[j][p.idFieldName])
		            		{
		            			if(MoveNodeItems[z].parentNodeId==childrenFirstlv[i][p.idFieldName])
		            			{
		        					jsonParam=jsonParam+',';
	        						jsonParam=jsonParam+'{"curcompno":"'+childrenSecondlv[j][p.idFieldName]+'","parentcompno":"'+childrenFirstlv[i][p.idFieldName]+'","complevel":"3"}';
	        						secondNodeExistsFlag=1;
		        					break;
		        				}
		        				secondNodeExistsFlag=2;
		               		}               		
		             	}
		             	if(secondNodeExistsFlag==0)
		             	{
		             		jsonParam=jsonParam+',';
	        				jsonParam=jsonParam+'{"curcompno":"'+childrenSecondlv[j][p.idFieldName]+'","parentcompno":"'+childrenFirstlv[i][p.idFieldName]+'","complevel":"3"}';
	        			}
        		
	        			if(compTreeManager.hasChildren(childrenSecondlv[j])==true  && (secondNodeExistsFlag==0 || secondNodeExistsFlag==1 ))
	        			{
	        				childrenthirthlv=childrenSecondlv[j].children;
		        			for(var k=0;k<childrenthirthlv.length;k++)//3级门店
				        	{
				        		thirthNodeExistsFlag=0;
				        		for(var z=0;z<MoveNodeItems.length;z++)
				            	{
				            		if(MoveNodeItems[z].curNodeId==childrenthirthlv[k][p.idFieldName])
				            		{
				            			if(MoveNodeItems[z].parentNodeId==childrenSecondlv[j][p.idFieldName])
				            			{
				        					jsonParam=jsonParam+',';
				        					jsonParam=jsonParam+'{"curcompno":"'+childrenthirthlv[k][p.idFieldName]+'","parentcompno":"'+childrenSecondlv[j][p.idFieldName]+'","complevel":"4"}';
			        						thirthNodeExistsFlag=1;
				        					break;
				        				}
				        				thirthNodeExistsFlag=2;
				               		}               		
				             	}
				             	if(thirthNodeExistsFlag==0)
				             	{
				             		jsonParam=jsonParam+',';
				        			jsonParam=jsonParam+'{"curcompno":"'+childrenthirthlv[k][p.idFieldName]+'","parentcompno":"'+childrenSecondlv[j][p.idFieldName]+'","complevel":"4"}';
				        		}
				        	}
			        	}	        		
	        		}
        		}
        	}
        	
        	var requestUrl ="bc001/postChainInfo.action";
			var params="strJsonParam=["+jsonParam+"]";
			var responseMethod="postChainInfoMessage";				
			sendRequestForParams_p(requestUrl,responseMethod,params ); 
        	}catch(e){alert(e.message);}
		}
		function postChainInfoMessage(request)
        {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		 curSecondCommonKey="";
	        		 curSecondCommonValue="";
	        		 $.ligerDialog.success("更新成功，请重新登录！");
	        	
	        		 IsMoveNodeFlag==false;
	        	}
	        	else
	        	{
	        		$.ligerDialog.warn(strMessage);
	        	
	        		 IsMoveNodeFlag==false;
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
        }
        
        function itemclick_chain(item, i)
        {
        	try
			{
				if(item.text=="移除")
				{
					var node = compTreeManager.getSelected();
					if(node.data[compTree.options.idFieldName]=="001")
					{
						return ;
					}
					$.ligerDialog.confirm('确认移除'+node.data[compTree.options.textFieldName], function (result)
            		{   
            			if( result==true)
            			{
            				if (node)
	                			compTreeManager.remove(node.target);   
	                	}
                	}); 
                } 
                else
                {
       				//if(IsMoveNodeFlag==true)
       				//{
       					//$.ligerDialog.warn("每次只能更新一次节点");
       				//}
       				if(parent.hasFunctionRights( "BC001",  "UR_SPECIAL_CHECK")!=true)
		        	{
		        		 $.ligerDialog.warn("该用户没有复合权限,不能重建连锁结构,请确认!");
		        		 return;
		        	}
                	var node = compTree.getSelected();	
                	var nodeIdValue=node.data[compTree.options.idFieldName];	                	
		            if (node.target && node.data[compTree.options.idFieldName]!="001")
		            {
		               	$.ligerDialog.prompt('上级门店号','', function (yes, value)
	                    {
	                         if (yes)
							 {
							 	 compTree.selectNode(value);
							 	 var parentnode = compTree.getSelected();
							 	 var nodes = [];
            					 nodes.push({id: node.data[compTree.options.idFieldName],text: node.data[compTree.options.textFieldName]});
            					 
            					 if (parentnode)
            					 {
                					compTree.append(parentnode.target, nodes); 
                				 }
            					 else
            					 {
               		 				compTree.append(null, nodes);
               		 			 }
               		 			 
               		 			 compTree.remove(node.target);
               		 			 compTree.selectNode(nodeIdValue);
               		 			 var isExistsNode=false;
               		 			 for(var i=0;i<MoveNodeItems.length;i++)
               		 			 {
               		 			 	if(nodeIdValue==MoveNodeItems[i].curNodeId)
               		 			 	{
       									MoveNodeItems[i].parentNodeId=value;
               		 			 		isExistsNode=true;
               		 			 	}
               		 			 }
               		 			 if(isExistsNode==false)
               		 			 {
								 	var item = new moveNodeInfo(value,nodeIdValue);
									MoveNodeItems.push(item);
								 }
								 IsMoveNodeFlag=true;
							 }
	                    });
		            }
                
                }       
            }
			catch(e)
			{
				alert(e.message);
			}
        }
        
        function createMangerPass()
        {
        	try
        	{
	        	var rnd="M";
				for(var i=0;i<15;i++)
				 	rnd+=Math.floor(Math.random()*10);
	        	document.getElementById("mangerPassword").value=rnd;
	        	var requestUrl ="bc001/validateInitRight.action";
	        	var responseMethod="validateInitRightMessage";	
	        	var params="mangerPassword="+rnd;	
	        	params=params+"&strCurCompId="+curCompid;		
				sendRequestForParams_p(requestUrl,responseMethod,params ); 
	        	
			}
			catch(e)
			{
				alert(e.message)
			}
        }
        
        function validateInitRightMessage(request)
        {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		var CardControl=parent.document.getElementById("CardCtrl");
					CardControl.Init(parent.commtype,parent.prot,parent.password1,parent.password2,parent.password3);
					var initflag=CardControl.WriteCard(document.getElementById("mangerPassword").value);
					if(initflag==0)
					{
						$.ligerDialog.error('经理卡初始化失败,请重新初始化!');
					}
					else
					{
						$.ligerDialog.success('经理卡初始化成功!');
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
        
        function obj2str(o){
        	var r = [];
        	if(typeof o =="string") return "\""+o.replace(/([\'\"\\])/g,"\\$1").replace(/(\n)/g,"\\n").replace(/(\r)/g,"\\r").replace(/(\t)/g,"\\t")+"\"";
        	    if(typeof o == "object"){
        	        if(!o.sort){
        	            for(var i in o)
        	                r.push(i+":"+obj2str(o[i]));
        	            if(!!document.all && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)){
        	                r.push("toString:"+o.toString.toString());
        	            }
        	            r="{"+r.join()+"}"
        	        }else{
        	            for(var i =0;i<o.length;i++)
        	                r.push(obj2str(o[i]))
        	            r="["+r.join()+"]"
        	        }
        	        return r;
        	    }
        	    return o.toString();
        	} 