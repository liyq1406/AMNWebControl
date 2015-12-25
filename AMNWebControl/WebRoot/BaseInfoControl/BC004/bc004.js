   	//初始化模组
   	function loadCommonDataGridByInfotype()
	{
			try
			{
				var strJson = "";//'{ "name": "cxh", "sex": "man" }';
				var ccount=0;
				if(parent.Useroverall!=null && parent.Useroverall.length>0)
				{	
					for(var i=0;i<parent.Useroverall.length;i++)
					{
						if(parent.Useroverall[i].id.modetype=="2")
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
							strJson=strJson+'{ "choose":"'+parent.Useroverall[i].id.modevalue+'", "text": "'+parent.Useroverall[i].descriptions+'"}';
						}
					}
					if(strJson!="")
					{
						strJson=strJson+']';
						return JSON.parse(strJson);
					}
					return null;
					 
				}
			
			}catch(e){alert(e.message);}
	}
	//初始化模块
	function showFunctionDetial(curGroupType)
	{
		try
		{
			var strJson = "";//'{ "name": "cxh", "sex": "man" }';
			var ccount=0;
			for( var i=0;i<parent.Sysmodeinfo.length;i++)
			{
				if(parent.Sysmodeinfo[i].id.upmoduleno==curGroupType)
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
					strJson=strJson+'{ "choose":"'+parent.Sysmodeinfo[i].id.curmoduleno+'", "text": "'+parent.Sysmodeinfo[i].modulename+'"}';
				}
			}
			if(strJson!="")
			{
				strJson=strJson+']';
				return JSON.parse(strJson);
			}
			return null;
		}catch(e){alert(e.message)}
	}
   	var compTree = null;//主明细	
   	var compTreeManager = null;//主明细	
   	var curCompid="";
   	var BC004Tab="";
   	var commoninfodivsecond=null;
   	var commoninfodivthirth=null;
   	var commoninfodivforth=null;
   	var commoninfodivfive=null;
   	var commoninfodivsix=null;
   	var commoninfodivseven=null;
   	var commoninfodiveight=null;
   	var userrolemanager= null;
   	var userempmanager=null;
   	var strCurUserId="";
   	var postmanager=null;
   	var pageState=3; // 1 add 2 edit 3brow
   	var chooseData = [{ choose: 'Y', text: '启' }, { choose: 'N', text: '禁'}];
   	var chooseMozuData=loadCommonDataGridByInfotype();
   	var chooseMokuaiData=showFunctionDetial("BC");
   	var lsUseroverall_store=null;
   	var lsUseroverall_mozu=null;
    var lsUseroverall_mokuai=null;
    var lsUseroverall_right=null;
    var lsStaffinfo=null;
    $(function ()
   	{
	   try
	   {
	   		//门店树形结构
   			compTree=$("#companyTree").ligerTree(
        	{
           		data: parent.complinkInfo,
           		idFieldName :'id',           		
           		onSelect: compSelect,
           		nodeWidth: 140,
           		checkbox: false,
           		height:'100%'
          	});
          	compTreeManager = $("#companyTree").ligerGetTreeManager();
          	f_selectNode();
          	commoninfodivsecond=$("#commoninfodivsecond").ligerGrid({
                columns: [
                { display: '使用者编号', name: 'userno',  width: 70 },
                { display: '员工编号', name: 'strEmpId',  width: 60 },
                { display: '员工名称', name: 'username',  width: 100 }
                ],  pageSize:10, 
                data:null,      
                width: '100%',
                height:'98%',
                enabledEdit: false,  
                rownumbers:false,usePager:false,             
                toolbar: { items: [
                { text: '增加', click: itemclick_userInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
                { line: true },
                { text: '保存', click: itemclick_userInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' },
                { line: true },
                { text: '删除', click: itemclick_userInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' }
               // { line: true },
                //{ text: '同步S3', click: itemclick_userInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/logout.gif' }
                ]
                }   ,	          
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecUserData(data, rowindex, rowobj);
                } 
            });
          	$("#pageloading").hide(); 
          	$("#BC004Tab").ligerTab({
             	onAfterSelectTabItem: function (tabid)
            	{
            		if(tabid=="tabitem1")
            		{
            			if(pageState==3)
            			{
            				initEideFun();
            			}
            		}
               	 	else if(tabid=="tabitem2")
               	 	{
               	 		if(pageState==3)
            			{
               	 			initDisableFun();
               	 		}
               	 	}
            	} 
            });
            BC004Tab = $("#BC004Tab").ligerGetTabManager();
          	//-----------------------文本验证
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
           // var userroleData=JSON.parse(parent.loadCommonControlDate("XTJS",0));
           	//userrolemanager = $("#userrole").ligerComboBox({ data: userroleData, isMultiSelect: false,valueFieldID: 'factUserrole',width:'110',selectBoxHeight:'220'});	
         
           	//commoninfodivthirth 门店权限
           	/* commoninfodivthirth=$("#commoninfodivthirth").ligerGrid({
                columns: [
                { display: '登录门店', name: 'modevalue',  width: 100 }
                ],  pageSize:10, 
                data: null,        
                width: '100%',
                height:'100%',
                onCheckRow:f_onCheckRow_thirth,
                isChecked: right_isChecked_thirth ,
                enabledEdit: true,  checkbox:true,rownumbers:false,usePager:false
            });*/
           
           commoninfodiveight=$("#commoninfodiveight").ligerGrid({
	        	columns: [
	            {
	            	display: '系统模组', name: 'bsysmodeno',  width: 110,
	            	editor: { type: 'select', data: chooseMozuData, valueField: 'choose',selectBoxHeight:'220'},
	            	render: function (item)
	              	{
	              		for(var i=0;i<parent.Useroverall.length;i++)
						{
							if(parent.Useroverall[i].id.modetype=="2")
							{	
								if(parent.Useroverall[i].id.modevalue==item.bsysmodeno)
								{
									commoninfodiveight.columns[1].editor.data=showFunctionDetial(item.bsysmodeno);
									return parent.Useroverall[i].descriptions;
								}
						    }
						}
	                    return '';
	                } 
	            },
	            { display: '系统模块', name: 'bfunctionno',  width: 160 ,
	            	editor: { type: 'select', data: chooseMokuaiData, valueField: 'choose',selectBoxHeight:'220'},
	            	render: function (item)
	              	{
	              		for( var i=0;i<parent.Sysmodeinfo.length;i++)
						{
							if(parent.Sysmodeinfo[i].id.curmoduleno==item.bfunctionno)
							{
								return parent.Sysmodeinfo[i].modulename;
							}
						}
	                    return '';
	                } 
	            },
	            { display: '查看', name: 'browsepurview',  width: 55 , 
	            	editor: { type: 'select', data: chooseData, valueField: 'choose' },
	            	render: function (item)
	              	{
	                  if (item.browsepurview == 'Y') return '启';
	                    return '禁';
	                }
	            },
	            { display: '编辑', name: 'editpurview',  width: 55 , 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.editpurview == 'Y') return '启';
	                      return '禁';
	                }
	            },
	            { display: '导出', name: 'exportpurview',  width: 55 , 
	              	editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                   if (item.exportpurview == 'Y') return '启';
	            	        return '禁';
	                }
	         	},
	            { display: '保存', name: 'postpurview',  width: 55, 
	              	editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.postpurview == 'Y') return '启';
	                       return '禁';
	                }
	          	},
	            { display: '审核', name: 'confirmpurview',  width: 55 , 
	              	editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                   if (item.confirmpurview == 'Y') return '启';
	                        return '禁';
	                }
	        	},
	            { display: '作废', name: 'invalidpurview',  width: 55 , 
	               	editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.invalidpurview == 'Y') return '启';
	                      return '禁';
	            	}
	            }
	            ],  pageSize:10, 
	            data: null,         
	            width: '100%',
	            height:'420px',
	            enabledEdit: true,  checkbox:false,rownumbers:false,usePager:false,
	            toolbar: { items: [
                	{ text: '增加', click: itemclick_eight, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' }                
                ]}  
        	});	
        	 addOption("","",document.getElementById("userrole"));
           	var goodstypes=parent.gainCommonInfoByCode("XTJS",0);
		    for(var i=0;i<goodstypes.length;i++)
			{
				addOption(goodstypes[i].bparentcodekey,goodstypes[i].parentcodevalue,document.getElementById("userrole"));
			}	
        	initDisableFun();   		
   		}catch(e){alert(e.message);}
    });
    
    //初始化禁用功能模块
    function initDisableFun()
    {
    	try{
    	 	//commoninfodivforth 禁用门店
             commoninfodivforth=$("#commoninfodivforth").ligerGrid({
                columns: [
                { display: '禁用门店', name: 'descriptions',  width: 100 ,align: 'left'}
                ],  pageSize:100, 
                data: {Rows: lsUseroverall_store,Total: 0} ,        
                width: '100%',
                height:'420px',
                onCheckRow: f_onCheckRow_forth,
                isChecked: right_isChecked_forth ,
                enabledEdit: true,  checkbox:true,rownumbers:false,usePager:false
            });
             // commoninfodivfive  禁用模组
             commoninfodivfive=$("#commoninfodivfive").ligerGrid({
                columns: [
                { display: '禁用模组', name: 'descriptions',  width: 100,align: 'left' }
                ],  pageSize:10, 
                data: {Rows: lsUseroverall_mozu,Total: 0} ,       
                width: '100%',
                height:'420px',
                onCheckRow:f_onCheckRow_five,
                isChecked: right_isChecked_five ,
                enabledEdit: true,  checkbox:true,rownumbers:false,usePager:false
            });
             // commoninfodivsix  禁用功能
             commoninfodivsix=$("#commoninfodivsix").ligerGrid({
                columns: [
                { display: '禁用功能', name: 'descriptions',  width: 100 ,align: 'left'}
                ],  pageSize:50, 
                data: {Rows: lsUseroverall_mokuai,Total: 0} ,      
                width: '100%',
                height:'420px',
                onCheckRow:f_onCheckRow_six,
                isChecked: right_isChecked_six ,
                enabledEdit: true,  checkbox:true,rownumbers:false,usePager:false
            });
             // commoninfodivseven  禁用权限
             commoninfodivseven=$("#commoninfodivseven").ligerGrid({
                columns: [
                { display: '禁用权限', name: 'descriptions',  width: 100,align: 'left' }
                ],  pageSize:30, 
                data: {Rows: lsUseroverall_right,Total: 0} ,           
                width: '100%',
                height:'420px',
                onCheckRow:f_onCheckRow_seven,
                isChecked: right_isChecked_seven ,
                enabledEdit: true,  checkbox:true,rownumbers:false,usePager:false
            });
            	}catch(e){alert(e.message);}
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
        try{
        	if(curCompid!="")
        	{
        		clearUserInfo();
        	}
        	curCompid=note.data.id;
       		var params = "strCurCompId="+note.data.id;				
     		var requestUrl ="bc004/loadSysuserinfo.action"; 
			var responseMethod="loadSysuserinfoMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params );
          }catch(e){alert(e.message);}
    }
        
 
    function loadSysuserinfoMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.lsSysuserinfo!=null && responsetext.lsSysuserinfo.length>0)
	   		{
	   			commoninfodivsecond.options.data=$.extend(true, {},{Rows: responsetext.lsSysuserinfo,Total: responsetext.lsSysuserinfo.length});
            	commoninfodivsecond.loadData(true);       
            	commoninfodivsecond.select(0);      	
	   		}
	   		else
	   		{
	   			commoninfodivsecond.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivsecond.loadData(true);      
	   		}
	   		lsStaffinfo=responsetext.lsStaffinfo;
	        userempmanager= $("#strEmpId").ligerComboBox({ isShowCheckBox: false, 
	               url:'loadAutoStaff', data: loadGridByStaffInfo(lsStaffinfo,"")
	                , valueFieldID: 'factStrEmpId',autocomplete:true,labelWidth:200,labelAlign:'center',width:'110',selectBoxHeight:'220',selectBoxWidth:'160',comboxtype:'2'
	            }); 
   
	   	}catch(e){alert(e.message);}
    }

    function loadAutoStaff(curmanager,curWriteStaff)
    {
    	curmanager.setData(loadGridByStaffInfo(lsStaffinfo,curWriteStaff));
    	curmanager.selectBox.show();
    }

    //加载用户信息
    function loadSelecUserData(data, rowindex, rowobj)
    {
    	strCurUserId=data.userno;
    	var params = "strCurUserId="+strCurUserId;				
     	var requestUrl ="bc004/loadCurUserInfo.action"; 
		var responseMethod="loadCurUserInfoMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
    function loadCurUserInfoMessage(request)
    {
    	try
        {
    		var action = eval("(" + request.responseText + ")");
	   		if(action.curUserInfo!=null)
	   		{
	   			document.getElementById("userno").value=checkNull(action.curUserInfo.userno);
				document.getElementById("userpwd").value=checkNull(action.curUserInfo.userpwd); 
				document.getElementById("userrole").value=checkNull(action.curUserInfo.userrole);   
				//userrolemanager.selectValue(checkNull(action.curUserInfo.userrole));
				document.getElementById("fromcompno").value=checkNull(action.curUserInfo.fromcompno);
				userempmanager.selectValue(checkNull(action.curUserInfo.strEmpId));
				
				document.getElementById("frominnerno").value=checkNull(action.curUserInfo.frominnerno);   	
				document.getElementById("datefrom").value=checkNull(action.curUserInfo.datefrom);   
				document.getElementById("dateto").value=checkNull(action.curUserInfo.dateto);   
				document.getElementById("callcenterqueue").value=checkNull(action.curUserInfo.callcenterqueue);   
				document.getElementById("callcenterinterface").value=checkNull(action.curUserInfo.callcenterinterface);   
	   		}
	   		if(action.lsUsereditright!=null && action.lsUsereditright.length>0)
	   		{
	   			commoninfodiveight.options.data=$.extend(true, {},{Rows: action.lsUsereditright,Total: action.lsUsereditright.length});
            	commoninfodiveight.loadData(true);            	
	   		}
	   		else
	   		{
	   			commoninfodiveight.options.data=$.extend(true, {},{Rows: null,Total: 0});
            	commoninfodiveight.loadData(true); 
	   		}
	
	   		if(action.lsUseroverall_store!=null)
	   		{
	   			lsUseroverall_store={Rows: action.lsUseroverall_store,Total: action.lsUseroverall_store.length};
	   			commoninfodivforth.options.data=$.extend(true, {},lsUseroverall_store);
            	commoninfodivforth.loadData(true); 
	   		}
	   		if(action.lsUseroverall_mozu!=null)
	   		{
	   			lsUseroverall_mozu={Rows: action.lsUseroverall_mozu,Total: action.lsUseroverall_mozu.length};
	   			commoninfodivfive.options.data=$.extend(true, {},lsUseroverall_mozu);
            	commoninfodivfive.loadData(true); 
	   		}
	   		if(action.lsUseroverall_mokuai!=null)
	   		{
	   			lsUseroverall_mokuai={Rows: action.lsUseroverall_mokuai,Total: action.lsUseroverall_store.length};
	   			commoninfodivsix.options.data=$.extend(true, {},lsUseroverall_mokuai);
            	commoninfodivsix.loadData(true); 
	   		}
	   		if(action.lsUseroverall_store!=null)
	   		{
	   			lsUseroverall_right={Rows: action.lsUseroverall_right,Total: action.lsUseroverall_right.length};
	   			commoninfodivseven.options.data=$.extend(true, {},lsUseroverall_right);
            	commoninfodivseven.loadData(true); 
	   		}
	   	}catch(e){alert(e.message);}
    }
    // ------------------------登录门店 -----------------Start
    	function right_isChecked_thirth(rowdata)
	   	{
	   		 if (checkNull(rowdata.showflag)=="Y") 
	   		 {
	   		 	f_onCheckRow_thirth(true, rowdata);
                return true;
             }
            return false;
	   	}
	   	
	   	function f_onCheckRow_thirth(checked, data)
        {
        	if (checked) 
            	addCheckedModevalue_thirth(data.modevalue);
            else 
            	removeCheckedModevalue_thirth(data.modevalue);
        }
	   	var checkedModevalue_thirth= [];
	   	function findCheckedModevalue_thirth(modevalue)
        {
            for(var i =0;i<checkedModevalue_thirth.length;i++)
            {
                if(checkedModevalue_thirth[i] == modevalue) return i;
            }
            return -1;
        }
        
        function addCheckedModevalue_thirth(modevalue)
        {
            if(findCheckedModevalue_thirth(modevalue) == -1)
                checkedModevalue_thirth.push(modevalue);
        }
        function removeCheckedModevalue_thirth(modevalue)
        {
            var i = findCheckedModevalue_thirth(modevalue);
            if(i==-1) return;
            checkedBfunctionno_thirth.splice(i,1);
        }
        
    // ------------------------登录门店 -----------------END
    
     // ------------------------禁用门店 -----------------Start
     	function right_isChecked_forth(rowdata)
	   	{
	   		 if (checkNull(rowdata.showflag)=="Y") 
	   		 {
	   		 	f_onCheckRow_forth(true, rowdata);
                return true;
             }
            return false;
	   	}
	   	function f_onCheckRow_forth(checked, data)
        {
        	if (checked) 
            	addCheckedModevalue_forth(data.bmodevalue);
            else 
            	removeCheckedModevalue_forth(data.bmodevalue);
        }
	   	var checkedModevalue_forth= [];
	   	function findCheckedModevalue_forth(modevalue)
        {
            for(var i =0;i<checkedModevalue_forth.length;i++)
            {
                if(checkedModevalue_forth[i] == modevalue) return i;
            }
            return -1;
        }
        
        function addCheckedModevalue_forth(modevalue)
        {
        	pageState=2;
            if(findCheckedModevalue_forth(modevalue) == -1)
                checkedModevalue_forth.push(modevalue);
        }
        function removeCheckedModevalue_forth(modevalue)
        {
            var i = findCheckedModevalue_forth(modevalue);
            if(i==-1) return;
            checkedBfunctionno_forth.splice(i,1);
        }
        
    // ------------------------禁用门店 -----------------END
    
    // ------------------------禁用模组 -----------------Start
    	function right_isChecked_five(rowdata)
	   	{
	   		 if (checkNull(rowdata.showflag)=="Y") 
	   		 {
	   		 	f_onCheckRow_five(true, rowdata);
                return true;
             }
            return false;
	   	}
	   	function f_onCheckRow_five(checked, data)
        {
        	if (checked) 
            	addCheckedModevalue_five(data.bmodevalue);
            else 
            	removeCheckedModevalue_five(data.bmodevalue);
        }
	   	var checkedModevalue_five= [];
	   	function findCheckedModevalue_five(modevalue)
        {
            for(var i =0;i<checkedModevalue_five.length;i++)
            {
                if(checkedModevalue_five[i] == modevalue) return i;
            }
            return -1;
        }
        
        function addCheckedModevalue_five(modevalue)
        {
        	pageState=2;
            if(findCheckedModevalue_five(modevalue) == -1)
                checkedModevalue_five.push(modevalue);
        }
        function removeCheckedModevalue_five(modevalue)
        {
            var i = findCheckedModevalue_five(modevalue);
            if(i==-1) return;
            checkedBfunctionno_five.splice(i,1);
        }
        
    // ------------------------禁用模组 -----------------END
    
    // ------------------------禁用模块 -----------------Start
    	function right_isChecked_six(rowdata)
	   	{
	   		 if (checkNull(rowdata.showflag)=="Y") 
	   		 {
	   		 	f_onCheckRow_six(true, rowdata);
                return true;
             }
            return false;
	   	}
	   	function f_onCheckRow_six(checked, data)
        {
        	if (checked) 
            	addCheckedModevalue_six(data.bmodevalue);
            else 
            	removeCheckedModevalue_six(data.bmodevalue);
        }
	   	var checkedModevalue_six= [];
	   	function findCheckedModevalue_six(modevalue)
        {
            for(var i =0;i<checkedModevalue_six.length;i++)
            {
                if(checkedModevalue_six[i] == modevalue) return i;
            }
            return -1;
        }
        
        function addCheckedModevalue_six(modevalue)
        {
        	pageState=2;
            if(findCheckedModevalue_six(modevalue) == -1)
                checkedModevalue_six.push(modevalue);
        }
        function removeCheckedModevalue_six(modevalue)
        {
            var i = findCheckedModevalue_six(modevalue);
            if(i==-1) return;
            checkedBfunctionno_six.splice(i,1);
        }
        
    // ------------------------禁用模块 -----------------END
    
    // ------------------------禁用权限 -----------------Start
    	function right_isChecked_seven(rowdata)
	   	{
	   		 if (checkNull(rowdata.showflag)=="Y") 
	   		 {
	   		 	f_onCheckRow_seven(true, rowdata);
                return true;
             }
            return false;
	   	}
	   	function f_onCheckRow_seven(checked, data)
        {
        	if (checked) 
            	addCheckedModevalue_seven(data.bmodevalue);
            else 
            	removeCheckedModevalue_seven(data.bmodevalue);
        }
	   	var checkedModevalue_seven= [];
	   	function findCheckedModevalue_seven(modevalue)
        {
            for(var i =0;i<checkedModevalue_seven.length;i++)
            {
                if(checkedModevalue_seven[i] == modevalue) return i;
            }
            return -1;
        }
        
        function addCheckedModevalue_seven(modevalue)
        {
        	pageState=2;
            if(findCheckedModevalue_seven(modevalue) == -1)
                checkedModevalue_seven.push(modevalue);
        }
        function removeCheckedModevalue_seven(modevalue)
        {
            var i = findCheckedModevalue_seven(modevalue);
            if(i==-1) return;
            checkedBfunctionno_seven.splice(i,1);
        }
        
    // ------------------------禁用权限 -----------------END
    // ------------------------操作用户 ----------------Start
    function itemclick_userInfo(item)
    {
    	try
    	{
	    	 $.ligerDialog.confirm('确认提交 '+curCompid+' 使用者 '+item.text+' 操作', function (result)
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
		        	 	deleteCurRecord();
		        	} 	
		        	else if(item.text=="同步S3")
		        	{
		        	 	syncUserInfo();
		        	} 	
		        }
		      });  
    	}
    	catch(e){alert(e.message);}
    }
    
    function addRecord()
    {
    		pageState=1;
    		clearUserInfo();
    		document.getElementById("userno").readOnly="";
    		var row = commoninfodivsecond.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivsecond.addRow({ 
				userno: "",
				strEmpId:"",
				username: ""                
				}, row, false);
    }
    function deleteCurRecord()
    {
    		if(parent.hasFunctionRights( "BC004",  "UR_DELETE")!=true)
	        {
	        	 $.ligerDialog.warn("该用户没有删除权限,请确认!");
	        	 return;
	        }
	        var row = commoninfodivsecond.getSelectedRow();
	        if (!row)
	        { 
	         	$.ligerDialog.warn('请选择要删除的用户信息');
	            return;
	        }
	        var requestUrl ="bc004/delete.action";
        	var params="strCurUserId="+row.userno;
			var responseMethod="deleteMessage";				
			sendRequestForParams_p(requestUrl,responseMethod,params ); 	
    }
    
    function editCurRecord()
    {
    		if(parent.hasFunctionRights( "BC003",  "UR_POST")!=true)
        	{
        		 $.ligerDialog.warn("该用户没有保存权限,请确认!");
        		 return;
        	}
        	
        	var jsonParam=JSON.stringify(commoninfodiveight.getData());
        	var strJsonParam_forth="";
			var strJsonParam_five="";
			var strJsonParam_six="";
			var strJsonParam_seven=""; 
			var curjosnparam="";
			var needReplaceStr="";
			//------禁用门店
        	for (var rowid in commoninfodivforth.records)
			{
		
				if(findCheckedModevalue_forth(commoninfodivforth.records[rowid]['bmodevalue'])!=-1)
				{	
				    var row =commoninfodivforth.records[rowid];
				    curjosnparam=JSON.stringify(row);
					/*if(curjosnparam.indexOf("_id")>-1)
				  	{
				     	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				      	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				    }	     */       		   
				    if(strJsonParam_forth!="")
				      	strJsonParam_forth=strJsonParam_forth+",";
				    strJsonParam_forth= strJsonParam_forth+curjosnparam;
				 }            		 
			}
			curjosnparam="";
        	needReplaceStr="";
        	
        	//------禁用模组
        	for (var rowid in commoninfodivfive.records)
			{
				if(findCheckedModevalue_five(commoninfodivfive.records[rowid]['bmodevalue'])!=-1)
				{	
				    var row =commoninfodivfive.records[rowid];
				    curjosnparam=JSON.stringify(row);
					/*if(curjosnparam.indexOf("_id")>-1)
				  	{
				     	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				      	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				    }	*/            		   
				    if(strJsonParam_five!="")
				      	strJsonParam_five=strJsonParam_five+",";
				    strJsonParam_five= strJsonParam_five+curjosnparam;
				 }            		 
			}
			curjosnparam="";
        	needReplaceStr="";
        	
        	//------禁用功能
        	for (var rowid in commoninfodivsix.records)
			{
				if(findCheckedModevalue_six(commoninfodivsix.records[rowid]['bmodevalue'])!=-1)
				{	
				    var row =commoninfodivsix.records[rowid];
				    curjosnparam=JSON.stringify(row);
					/*if(curjosnparam.indexOf("_id")>-1)
				  	{
				     	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				      	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				    }*/	            		   
				    if(strJsonParam_six!="")
				      	strJsonParam_six=strJsonParam_six+",";
				    strJsonParam_six= strJsonParam_six+curjosnparam;
				 }            		 
			}
			curjosnparam="";
        	needReplaceStr="";
        	
        	//------禁用权限
        	for (var rowid in commoninfodivseven.records)
			{
				if(findCheckedModevalue_seven(commoninfodivseven.records[rowid]['bmodevalue'])!=-1)
				{	
				    var row =commoninfodivseven.records[rowid];
				    curjosnparam=JSON.stringify(row);
					/*if(curjosnparam.indexOf("_id")>-1)
				  	{
				     	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				      	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				    }	          */  		   
				    if(strJsonParam_seven!="")
				      	strJsonParam_seven=strJsonParam_seven+",";
				    strJsonParam_seven= strJsonParam_seven+curjosnparam;
				 }            		 
			}
			curjosnparam="";
        	needReplaceStr="";
        	var requestUrl ="bc004/post.action";
			var params="strJsonParam="+jsonParam;
			params=params+"&strJsonParam_forth=["+strJsonParam_forth+"]";
			params=params+"&strJsonParam_five=["+strJsonParam_five+"]";
			params=params+"&strJsonParam_six=["+strJsonParam_six+"]";
			params=params+"&strJsonParam_seven=["+strJsonParam_seven+"]";
			params=params+"&strCurUserId="+strCurUserId;
			params=params+"&curUserInfo.userno="+document.getElementById("userno").value;
			params=params+"&curUserInfo.userpwd="+document.getElementById("userpwd").value;
			//params=params+"&curUserInfo.userrole="+$("#factUserrole").val();
			params=params+"&curUserInfo.userrole="+document.getElementById("userrole").value;
			params=params+"&curUserInfo.fromcompno="+document.getElementById("fromcompno").value;
			params=params+"&curUserInfo.frominnerno="+document.getElementById("frominnerno").value;
			params=params+"&curUserInfo.datefrom="+document.getElementById("datefrom").value;
			params=params+"&curUserInfo.dateto="+document.getElementById("dateto").value;
			params=params+"&curUserInfo.callcenterqueue="+document.getElementById("callcenterqueue").value;
			params=params+"&curUserInfo.callcenterinterface="+document.getElementById("callcenterinterface").value;
			var responseMethod="postUserInfoMessage";	
		
			postmanager = $.ligerDialog.waitting('正在保存中,请稍候...');
			sendRequestForParams_p(requestUrl,responseMethod,params ); 
    }
    
    function postUserInfoMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        postmanager.close();
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("更新成功!");
	        	 pageState=3;
	        	 document.getElementById("userno").readOnly="readOnly";
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
    
    function deleteMessage(request)
    {
   		var responsetext = eval("(" + request.responseText + ")");
	    var strMessage=responsetext.strMessage;
	    if(checkNull(strMessage)=="")
	    {       		 
	    	$.ligerDialog.success("删除成功!");
	    	commoninfodivsecond.deleteSelectedRow();
	    }
	    else
	    {
	    	alert(strMessage);
	    }
        	
   	}
    
    function clearUserInfo()
    {
    		//--------基础资料
    		document.getElementById("userno").value="";
			document.getElementById("userpwd").value=""; 
			document.getElementById("userrole").value="";   
			//userrolemanager.selectValue("");
			document.getElementById("fromcompno").value="";
			document.getElementById("strEmpId").value="";
		
			document.getElementById("frominnerno").value="";   	
			document.getElementById("datefrom").value="";   
			document.getElementById("dateto").value="";   
    		//--------角色功能编辑
    		commoninfodiveight.options.data=$.extend(true, {},{Rows: null,Total: 0});
            commoninfodiveight.loadData(true); 
           
            
    }
        
    
    function itemclick_eight(item)
    {
    	
    	if(checkNull(strCurUserId)=="")
    	{
    		$.ligerDialog.warn("请选择需要操作的用户!");
    		return;
    	}
    	pageState=2;
    	var row = commoninfodiveight.getSelectedRow();
		//参数1:rowdata(非必填)
		//参数2:插入的位置 Row Data 
		//参数3:之前或者之后(非必填)
		commoninfodiveight.addRow({ 
			bfunctionno: "",
			bfunctioname: "",
			browsepurview: "N",
			editpurview: "N" , 
			exportpurview: "N" ,   
			postpurview: "N" ,   
			confirmpurview: "N" ,   
			invalidpurview: "N"                 
			}, row, false);
    }
    // ------------------------操作用户 ----------------END
    // ------------------------同步S3-------------Start
    function syncUserInfo()
    {
    	var params = "strCurCompId="+curCompid;				
     	var requestUrl ="bc004/syncUserInfo.action"; 
		var responseMethod="syncUserInfoMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    function syncUserInfoMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)!="")
	        {	        		 
	        	$.ligerDialog.success(strMessage);
	        }	       
        }
		catch(e)
		{
			alert(e.message);
		}
    }
    // ------------------------验证用户编号-------------Start
    function validateUserno(obj)
    {
    	var params = "strCurUserId="+obj.value;				
     	var requestUrl ="bc004/validateUserId.action"; 
		var responseMethod="validateUserIdMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    function validateUserIdMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)!="")
	        {	        		 
	        	$.ligerDialog.warn(strMessage);
	        	document.getElementById("userno").value="";
	        }	       
        }
		catch(e)
		{
			alert(e.message);
		}
    }
    // ------------------------验证用户编号-------------End
    // ------------------------验证员工编号-------------Start
    function validateEmpId(obj)
    {
    	var strCurEmpId="";
    	if($("#factStrEmpId").val()=="")
    		strCurEmpId=obj.value;
    	else 
    		strCurEmpId=$("#factStrEmpId").val();
    	
    	if(strCurEmpId!="" )
    	{
    			var params = "strCurEmpId="+strCurEmpId;	
    			params=params+"&strCurCompId="+curCompid;		
     			var requestUrl ="bc004/validateEmpId.action"; 
				var responseMethod="validateEmpIdMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params );
    	}
    	else
    	{
    		document.getElementById("fromcompno").value="";
    	}
  
    }
    function validateEmpIdMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)!="")
	        {	        		 
	        	$.ligerDialog.warn(strMessage);
	        	document.getElementById("fromcompno").value="";
	        	userempmanager.selectValue("");
	        	document.getElementById("strEmpId").value=""; 
    			userempmanager.setData(loadGridByStaffInfo(lsStaffinfo,""));
	        }	   
	        else
	        {
	        	document.getElementById("fromcompno").value=responsetext.curStaffinfo.id.compno;
	        	
	        	document.getElementById("frominnerno").value=responsetext.curStaffinfo.manageno;
	        }    
        }
		catch(e)
		{
			alert(e.message);
		}
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
				userempmanager.selectBox.hide();
			}
			catch(e){alert(e.message);}
				
		}   
    // ------------------------验证员工编号-------------end