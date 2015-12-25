
   	var compTree = null;//主明细	
   	var compTreeManager = null;//主明细	
   	var commoninfodivsecond=null;//员工明细
   	var curCompid="";
   	var curStaffid="";
   	var staffPostionData=JSON.parse(parent.loadCommonControlDate_select("GZGW",0));
   	var staffPostionTitleData=JSON.parse(parent.loadCommonControlDate_select("GZZC",0));
 
   	var staffDepData=JSON.parse(parent.loadCommonControlDate_select("BMZL",0));
   	var staffYejiData=JSON.parse(parent.loadCommonControlDate_select("YJFS",0));
   	var staffChangeData=JSON.parse(parent.loadCommonControlDate_select("YDLX",0));
  	var chooseData = [{ choose: 1, text: '是' }, { choose: 0, text: '否'}];
   	var commoninfodivthirth=null;
   	var curStaffInfoDate=null;
   	var lsStaffhistory=null;
   	var lsStaffabsenceinfo=null;
   	var lsManagerShare=null;
   	var bc012layout=null;
   	var curRecord=null;
   	var comfirmButton = null;
   	var showDialogmanager=null;
   	var upDialogmanager=null;
    $(function ()
   	{
	   try
	   {
	   	  //布局
            bc012layout= $("#bc012layout").ligerLayout({ leftWidth: 270,rightWidth: 280,  allowBottomResize: false, allowLeftResize: false ,isLeftCollapse:true,isRightCollapse:true});
            //门店树形结构
   			var height = $(".l-layout-center").height();
           
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
          	$("#dingweiButton").ligerButton(
		     {
		             text: '定位员工资料', width: 120,
			         click: function ()
			         {
			             f_searchStaffInfo();
			         }
		     });
		     $("#searchButton").ligerButton(
		     {
		             text: '查询员工资料', width: 120,
			         click: function ()
			         {
			             searchStaffInfo();
			         }
		     });
		     $("#searchFingerButton").ligerButton(
		     {
		             text: '获取员工考勤', width: 120,
			         click: function ()
			         {
			             //searchFingerButton();
			         }
		     });
		      $("#editCurRecord").ligerButton(
		     {
		             text: '更新员工资料', width: 160,
			         click: function ()
			         {
			             editCurRecord();
			         }
		     });
          	compTreeManager = $("#companyTree").ligerGetTreeManager();

        	commoninfodivsecond=$("#commoninfodivsecond").ligerGrid({
                columns: [
                { display: '员工编号', name: 'bstaffno',  width: 70 },
                { display: '员工名称', name: 'staffname',  width: 100,align: 'left' },
                { display: '指纹号', 	 name: 'fingerno',  width:50,align: 'left' },
                { display: '员工部门', name: 'department',  width: 60,align: 'left' ,
                	editor: { type: 'select', data: staffDepData, valueField: 'choose',selectBoxHeight:'220'},
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("BMZL",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.department)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
                { display: '员工职位', name: 'position',  width: 60,align: 'left' ,
                editor: { type: 'select', data: staffPostionData, valueField: 'choose',selectBoxHeight:'220'},
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("GZGW",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.position)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
	            { display: '员工职称', name: 'positiontitle',  width: 60,align: 'left' ,
                editor: { type: 'select', data: staffPostionTitleData, valueField: 'choose',selectBoxHeight:'220'},
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("GZZC",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.positiontitle)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
	            { display: '身份证号', name: 'pccid',  width:150,align: 'left' },
	            { display: '行动电话', name: 'mobilephone',  width:90,align: 'left' },
	            { display: '入职日期',  name: 'arrivaldate',  width:80,align: 'left' },
	            { display: '内部管理号',name: 'manageno',  width:95,align: 'left' },
	            { display: '银行卡号',  name: 'bankno',  width:145,align: 'left' },
	           
	            { display: '业绩方式 ', name: 'resulttye',  width:80,align: 'left' ,
	             editor: { type: 'select', data: staffYejiData, valueField: 'choose',selectBoxHeight:'220'},
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("YJFS",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.resulttye)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } },
	            { display: '业绩系数', name: 'resultrate',  width:60,align: 'left' },	           
	            { display: '业绩基数', name: 'baseresult',  width:60,align: 'left' },
	            { display: '业务员', name: 'businessflag',  width:50,align: 'left'  , 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.businessflag == 1) return '是';
	                      return '否';
	                }
	            },
	            { display: '员工状态', name: 'curstate',  width:60,align: 'left'  , 
	                editor: { type: 'select', data: null, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.curstate == 1) return '未到职';
	                    if (item.curstate == 2) return '已到职';
	                      return '离职';
	                }
	            }
                ],  pageSize:25, 
                data:null,      
                width: '100%',
                height:'98%',
                enabledEdit: false, 
                clickToEdit: false,  
                rownumbers:false,usePager:true, where : f_getWhere(),            
                toolbar: { items: [
                { text: '派遣', click: itemclick_empInfoPQ, img: contextURL+'/common/ligerui/ligerUI/skins/icons/attibutes.gif' },
                { line: true },
                { text: '入职', click: itemclick_empInfoRZ, img: contextURL+'/common/ligerui/ligerUI/skins/icons/attibutes.gif' },
                { line: true },
                { text: '离职', click: itemclick_empInfoLZ, img: contextURL+'/common/ligerui/ligerUI/skins/icons/attibutes.gif' },
                { line: true },
                { text: '本店调动', click: itemclick_empInfoADD, img: contextURL+'/common/ligerui/ligerUI/skins/icons/attibutes.gif' },
                { line: true },
                { text: '薪资调整', click: itemclick_empInfoSalaryUp, img: contextURL+'/common/ligerui/ligerUI/skins/icons/attibutes.gif' },
                { line: true },
                { text: '跨店调动', click: itemclick_empInfoBDD, img: contextURL+'/common/ligerui/ligerUI/skins/icons/attibutes.gif' },
                { line: true },
                { text: '平级调动', click: itemclick_empInfoSamelevelChange, img: contextURL+'/common/ligerui/ligerUI/skins/icons/attibutes.gif' },
                { line: true },
                { text: '重回公司', click: itemclick_empInfoCH, img: contextURL+'/common/ligerui/ligerUI/skins/icons/attibutes.gif' },
               	{ line: true },
               	{ text: '指纹下发', click: itemclick_downFingerForPerson, img: contextURL+'/common/ligerui/ligerUI/skins/icons/back.gif' },
              	{ line: true },
              	{ text: '人脸下发', click: itemclick_downFactForPerson, img: contextURL+'/common/ligerui/ligerUI/skins/icons/back.gif' },
              	{ line: true },
              	{ text: '人脸还原', click: itemclick_backupFactForPerson, img: contextURL+'/common/ligerui/ligerUI/skins/icons/back.gif' },
              	{ line: true },
               	{ text: '设置区域经理底薪', click: itemclick_setManageShare, img: contextURL+'/common/ligerui/ligerUI/skins/icons/process.gif' },
              	//{ line: true },
               	//{ text: '门店获取总部录入考勤', click: itemclick_downFingerForComp, img: contextURL+'/common/ligerui/ligerUI/skins/icons/back.gif' },
              	//{ line: true },
                //{ text: '更新备份个人指纹', click: itemclick_empInfoFinger, img: contextURL+'/common/ligerui/ligerUI/skins/icons/memeber.gif' },
              	{ line: true },
                { text: '设置收银员角色', click: resetCasherRoleAccount, img: contextURL+'/common/ligerui/ligerUI/skins/icons/outbox.gif' }
               ]
                }   ,	 
                onSelectRow : function (data, rowindex, rowobj)
                {
                	curRecord=data;
                	loadCurStaffinfo(data, rowindex, rowobj);
                } ,         
                onDblClickRow : function (data, rowindex, rowobj)
                {
                	curRecord=data;
                    loadSelecDetialData(data, rowindex, rowobj);
                } 
            });

           	clearOption("positiontitle");
          	var postiontitle=parent.gainCommonInfoByCode("GZZC",0);
          	for(var i=0;i<postiontitle.length;i++)
			{
				addOption(postiontitle[i].bparentcodekey,postiontitle[i].parentcodevalue,document.getElementById("positiontitle"));
			}	
			clearOption("socialsource");
			addOption("","",document.getElementById("socialsource"));
          	var sbgsdate=parent.gainCommonInfoByCode("SBGS",0);
          	for(var i=0;i<sbgsdate.length;i++)
			{
				addOption(sbgsdate[i].bparentcodekey,sbgsdate[i].parentcodevalue,document.getElementById("socialsource"));
			}	
			
			clearOption("absencesalary");
			addOption("","",document.getElementById("absencesalary"));
          	var qqdxdate=parent.gainCommonInfoByCode("QQDX",0);
          	for(var i=0;i<qqdxdate.length;i++)
			{
				addOption(qqdxdate[i].bparentcodekey,qqdxdate[i].parentcodevalue,document.getElementById("absencesalary"));
			}	
			clearOption("tichengmode");
			addOption("","",document.getElementById("tichengmode"));
          	var qqdxdate=parent.gainCommonInfoByCode("XZMS",0);
          	for(var i=0;i<qqdxdate.length;i++)
			{
				addOption(qqdxdate[i].bparentcodekey,qqdxdate[i].parentcodevalue,document.getElementById("tichengmode"));
			}
			
            $("#contractdate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'140' });
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
     		var requestUrl ="bc012/loadCurStaffInfo.action"; 
			var responseMethod="loadSysuserinfoMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params );
          }catch(e){alert(e.message);}
    }
    function loadSysuserinfoMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.lsStaffinfo!=null && responsetext.lsStaffinfo.length>0)
	   		{
	   			curStaffInfoDate={Rows: responsetext.lsStaffinfo,Total: responsetext.lsStaffinfo.length};
	   			commoninfodivsecond.options.data=$.extend(true, {},curStaffInfoDate);
            	commoninfodivsecond.loadData(true);          
            	commoninfodivsecond.select(0);  	
	   		}
	   		else
	   		{
	   			commoninfodivsecond.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivsecond.loadData(true);      
	   		}
	   		
	   	}catch(e){alert(e.message);}
    }
    
    function loadCurStaffinfo(data, rowindex, rowobj)
    {
    	curStaffid=data.bstaffno;
    	var params = "strCurCompId="+data.id.compno;			
    	params =params+ "&strCurStaffId="+curStaffid;		
     	var requestUrl ="bc012/loadCurStaff.action"; 
		var responseMethod="loadCurStaffinfoMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    function loadCurStaffinfoMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
    		if(responsetext.curStaffinfo!=null)
    		{
	   			document.getElementById("compno").value=checkNull(responsetext.curStaffinfo.id.compno);
	   			document.getElementById("staffno").value=checkNull(responsetext.curStaffinfo.id.staffno);
	   			document.getElementById("manageno").value=checkNull(responsetext.curStaffinfo.manageno);
	   			document.getElementById("department").value=checkNull(responsetext.curStaffinfo.department);
	   			document.getElementById("position").value=checkNull(responsetext.curStaffinfo.position);
	   			document.getElementById("resulttye").value=checkNull(responsetext.curStaffinfo.resulttye);
	   			document.getElementById("resultrate").value=checkNull(responsetext.curStaffinfo.resultrate);
	   			document.getElementById("baseresult").value=checkNull(responsetext.curStaffinfo.baseresult);
	   			document.getElementById("staffname").value=checkNull(responsetext.curStaffinfo.staffname);
	   			document.getElementById("aaddress").value=checkNull(responsetext.curStaffinfo.aaddress);
	   			document.getElementById("mobilephone").value=checkNull(responsetext.curStaffinfo.mobilephone);
	   			document.getElementById("reservecontect").value=checkNull(responsetext.curStaffinfo.reservecontect);
	   			document.getElementById("reservephone").value=checkNull(responsetext.curStaffinfo.reservephone);
	   			document.getElementById("introductioner").value=checkNull(responsetext.curStaffinfo.introductioner);
	   			document.getElementById("banktype").value=checkNull(responsetext.curStaffinfo.banktype);
	   			document.getElementById("bankno").value=checkNull(responsetext.curStaffinfo.bankno);
	   			document.getElementById("basesalary").value=checkNull(responsetext.curStaffinfo.basesalary);
	   			document.getElementById("remark").value=checkNull(responsetext.curStaffinfo.remark);
	   			document.getElementById("staffmark").value=checkNull(responsetext.curStaffinfo.staffmark);
	   			document.getElementById("pccid").value=checkNull(responsetext.curStaffinfo.pccid);
	   			document.getElementById("businessflag").value=checkNull(responsetext.curStaffinfo.businessflag);
	   			document.getElementById("socialsecurity").value=checkNull(responsetext.curStaffinfo.socialsecurity); 
	   			document.getElementById("contractdate").value=checkNull(responsetext.curStaffinfo.contractdate);
	   			document.getElementById("positiontitle").value=checkNull(responsetext.curStaffinfo.positiontitle);
	   			document.getElementById("socialsource").value=checkNull(responsetext.curStaffinfo.socialsource);
	   			document.getElementById("absencesalary").value=checkNull(responsetext.curStaffinfo.absencesalary); 
	   			document.getElementById("tichengmode").value=checkNull(responsetext.curStaffinfo.tichengmode); 
	   			document.getElementById("staffsex").value=checkNull(responsetext.curStaffinfo.staffsex);
	   		}
	   	}catch(e){alert(e.message);}
    }
    function loadSelecDetialData(data, rowindex, rowobj)
    {
    	curStaffid=data.bstaffno;
    	var params = "strCurCompId="+data.id.compno;			
    	params =params+ "&strCurStaffId="+curStaffid;		
     	var requestUrl ="bc012/loadCurStaffHistoryInfo.action"; 
		var responseMethod="loadCurStaffHistoryInfoMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
    	
    }
    function loadCurStaffHistoryInfoMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
	   		
	   		lsStaffhistory=responsetext.lsStaffhistory;
	   		f_openStaffhistory();
	   	}catch(e){alert(e.message);}
    }
     // ------------------------薪资调整 ----------------Start
     function itemclick_empInfoSalaryUp(item)
     {
     		 upDialogmanager=$.ligerDialog.open({ height: null, url: contextURL+'/BaseInfoControl/BC012/staffSalaryUp.jsp', width: 400, showMax: false, showToggle: false, showMin: false, isResize: false, title: '薪资调整' });
     }
     // ------------------------平级调整 ----------------Start
     function itemclick_empInfoSamelevelChange(item)
     {
     		 upDialogmanager=$.ligerDialog.open({ height: null, url: contextURL+'/BaseInfoControl/BC012/staffSameLevelChange.jsp', width: 400, showMax: false, showToggle: false, showMin: false, isResize: false, title: '平级跨店调动  当前门店  '+document.getElementById("compno").value+'  当前员工'+document.getElementById("staffno").value+' ['+document.getElementById("staffname").value+']' });
     }
     
     
    function itemclick_empInfoPQ(item)
    {
    	 shownewPayDialogmanager=$.ligerDialog.open({ height: null, url: contextURL+'/BaseInfoControl/BC012/staffDispatch.jsp', width: 300, showMax: false, showToggle: false, showMin: false, isResize: false, title: '员工派遣' });
    }
    
    function itemclick_setManageShare(item)
    {
    
    		var params = "strStaffInNo="+curRecord.manageno;		
     		var requestUrl ="bc012/loadManageShare.action"; 
			var responseMethod="loadManageShareMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
     function loadManageShareMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
    		lsManagerShare=responsetext.lsManagerShare;
	   		shownewPayDialogmanager=$.ligerDialog.open({ height: null, url: contextURL+'/BaseInfoControl/BC012/managerShare.jsp', width: 450, showMax: false, showToggle: false, showMin: false, isResize: false, title: '['+curRecord.staffname+']门店底薪设置' });
	   	}catch(e){alert(e.message);}
    }
    
    function itemclick_empInfoRZ(item)
    {
     	var dialog=$.ligerDialog.open({ height: '700', url: contextURL+'/BaseInfoControl/BC012/staff_rz.jsp', width: '550', showMax: true, showToggle:true, showMin: false,  title: '员工入职单' });
    	
    }
    function itemclick_empInfoLZ(item)
    {
    	
    	if(curRecord.curstate!=2)
    	{
    		$.ligerDialog.error("当前员工 "+curRecord.staffname+" 员工状态不正确,请核实!");
    	}
    	else
    	{
    		$.ligerDialog.open({ height: 700, url: contextURL+'/BaseInfoControl/BC012/staff_lz.jsp', width: 550, showMax: false, showToggle: false, showMin: false, isResize: true, title: '员工离职单' });
   		}
    }
    function itemclick_empInfoADD(item)
    {
    	if(curRecord.curstate!=2)
    	{
    		$.ligerDialog.error("当前员工 "+curRecord.staffname+" 员工状态不正确,请核实!");
    	}
    	else
    	{
    		$.ligerDialog.open({ height: 700, url: contextURL+'/BaseInfoControl/BC012/staff_dd.jsp', width: 550, showMax: false, showToggle: false, showMin: false, isResize: true, title: '员工本店调动单' });
   		}
    }
    function itemclick_empInfoBDD(item)
    {
    	if(curRecord.curstate!=2)
    	{
    		$.ligerDialog.error("当前员工 "+curRecord.staffname+" 员工状态不正确,请核实!");
    	}
    	else
    	{
    		$.ligerDialog.open({ height: 700, url: contextURL+'/BaseInfoControl/BC012/staff_kdd.jsp', width: 550, showMax: false, showToggle: false, showMin: false, isResize: true, title: '员工跨店调动单' });
   		}
    }
    function itemclick_empInfoCH(item)
    {
     	if(curRecord.curstate!=3)
    	{
    		$.ligerDialog.error("当前员工 "+curRecord.staffname+" 员工状态不正确,请核实!");
    	}
    	else
    	{
    		$.ligerDialog.open({ height: 700, url: contextURL+'/BaseInfoControl/BC012/staff_ch.jsp', width: 550, showMax: false, showToggle: false, showMin: false, isResize: true, title: '员工重回公司' });
    	}
    }
     // ------------------------操作用户 ----------------End
     // ------------------------查询信息 ----------------Start
     function f_searchStaffInfo()
     {
    
     	   commoninfodivsecond.options.data = $.extend(true, {}, curStaffInfoDate);
     	
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
             
               return (rowdata.bstaffno.indexOf(key) > -1 ||  rowdata.manageno.indexOf(key) > -1 );
              
                
            };
            return clause; 
        }
    	catch(e){alert(e.message);}
     }
     var wincount = 0;
     function f_openStaffhistory() {
        $.ligerDialog.open({ height: null, url: contextURL+'/BaseInfoControl/BC012/staffhistory.jsp', width: 1024, showMax: true, showToggle: true, showMin: true, isResize: true, modal: false, title: title() });
     }
     function title() {
            return '员工'+curStaffid+"异动历史" + (++wincount);
     }
     // ------------------------查询信息 ----------------End
     
     //入职申请回调
   	function postStaffRZMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("操作成功!");
	        	 comfirmButton.setDisabled();
	        }
	        else
	        {
	        	$.ligerDialog.error(strMessage);
	        }
        }
		catch(e)
		{
			alert(e.message);
		}
    }
    
     //离职申请回调
   	function postStaffLZMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("操作成功!");
	        	 comfirmButton.setDisabled();
	        }
	        else
	        {
	        	$.ligerDialog.error(strMessage);
	        }
        }
		catch(e)
		{
			alert(e.message);
		}
    }
    
    //本店调动回调
   	function postStaffDDMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("操作成功!");
	        	 comfirmButton.setDisabled();
	        }
	        else
	        {
	        	$.ligerDialog.error(strMessage);
	        }
        }
		catch(e)
		{
			alert(e.message);
		}
    }
    
    function postStaffCHMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("操作成功!");
	        	 comfirmButton.setDisabled();
	        }
	        else
	        {
	        	$.ligerDialog.error(strMessage);
	        }
        }
		catch(e)
		{
			alert(e.message);
		}
    }
    
    function postStaffCHessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("操作成功!");
	        	 comfirmButton.setDisabled();
	        }
	        else
	        {
	        	$.ligerDialog.error(strMessage);
	        }
        }
		catch(e)
		{
			alert(e.message);
		}
    }
    
     function dispatchStaffMessage(request)
	 {
	 			var responsetext = eval("(" + request.responseText + ")");
		        var strMessage=responsetext.strMessage;
		         if(checkNull(strMessage)=="")
		        {	        		 
		        	 $.ligerDialog.success("派遣成功!");
		        }
		        else
		        {
		        	$.ligerDialog.error(strMessage);
		        }
	}
	
	
     function postManagerShareInfoMessage(request)
	 {
	 			var responsetext = eval("(" + request.responseText + ")");
		        var strMessage=responsetext.strMessage;
		         if(checkNull(strMessage)=="")
		        {	        		 
		        	 $.ligerDialog.success("录入成功!");
		        }
		        else
		        {
		        	$.ligerDialog.error(strMessage);
		        }
	}
    
    
    	
     function deleteManageShareMessage(request)
	 {
	 			var responsetext = eval("(" + request.responseText + ")");
		        var strMessage=responsetext.strMessage;
		         if(checkNull(strMessage)=="")
		        {	        		 
		        	 $.ligerDialog.success("操作成功!");
		        }
		        else
		        {
		        	$.ligerDialog.error(strMessage);
		        }
	}
	
     function dispatchSalaryUpMessage(request)
	 {
	 			var responsetext = eval("(" + request.responseText + ")");
		        var strMessage=responsetext.strMessage;
		         if(checkNull(strMessage)=="")
		        {	        		 
		        	 $.ligerDialog.success("设置成功!");
		        }
		        else
		        {
		        	$.ligerDialog.error(strMessage);
		        }
		        upDialogmanager.close();
	}
	
	 function changeBySameLevelMessage(request)
	 {
	 			var responsetext = eval("(" + request.responseText + ")");
		        var strMessage=responsetext.strMessage;
		         if(checkNull(strMessage)=="")
		        {	        		 
		        	 $.ligerDialog.success("调动成功!");
		        }
		        else
		        {
		        	$.ligerDialog.error(strMessage);
		        }
		        upDialogmanager.close();
	}
	
    function searchStaffInfo()
    {
    	var params ="strStaffNo="+document.getElementById("strStaffNo").value;
    	params=params+"&strStaffName="+document.getElementById("strStaffName").value;
    	params=params+"&strPCID="+document.getElementById("strPCID").value;
		params=params+"&strStaffInNo="+document.getElementById("strStaffInNo").value;
        var requestUrl ="bc012/loadDataSet.action"; 
		var responseMethod="loadDataSetMessage";		
		showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
    function loadDataSetMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
			showDialogmanager.close();
			if(responsetext.lsStaffinfo!=null && responsetext.lsStaffinfo.length>0)
           	{
            		commoninfodivsecond.options.data=$.extend(true, {},{Rows: responsetext.lsStaffinfo,Total: responsetext.lsStaffinfo.length});
            		commoninfodivsecond.loadData(true);
            		commoninfodivsecond.select(0);  
            }
			else
			{
				 	$.ligerDialog.warn("没有查到员工信息!");
		   			commoninfodivsecond.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivsecond.loadData(true);
	            	addStaff(); 
			}
	
	}
	
	function searchFingerButton()
	{
		var params = "strCurCompId="+curCompid;
        var requestUrl ="bc012/loadFingerDataSet.action"; 
		var responseMethod="loadFingerDataSetMessage";		
		showDialogmanager = $.ligerDialog.waitting('正在获取'+curCompid+'店的考勤信息中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
	}
	function loadFingerDataSetMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
			showDialogmanager.close();
			var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("操作成功!");
	        	
	        }
	        else
	        {
	        	$.ligerDialog.error(strMessage);
	        }
	}
	function addStaff()
	{
		var row = commoninfodivsecond.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivsecond.addRow({ 
				                bchangetype: 5,
				                billflag: 0
				             
				            }, row, false);
	}
	
	function itemclick_empInfoFinger()
	{
	
    	var params = "strCurCompId="+curRecord.id.compno;			
    	params =params+ "&strCurFingerId="+curRecord.fingerno;	
		params =params+ "&strCurStaffName="+curRecord.staffname;	
     	var requestUrl ="bc012/handEmpInfoFinger.action"; 
		var responseMethod="handEmpInfoFingerMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
	}
	
	function handEmpInfoFingerMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("操作成功!");
	        	 comfirmButton.setDisabled();
	        }
	        else
	        {
	        	$.ligerDialog.error(strMessage);
	        }
	}
	
	
	function itemclick_downFingerForComp()
	{
		if(curRecord.id.compno=="001")
		{
			$.ligerDialog.warn("总部员工不需此操作!");
			return;
		}
		var params = "strCurCompId="+curRecord.id.compno;	
		params =params+ "&strCurStaffName="+curRecord.staffname;	
     	var requestUrl ="bc012/downFingerForComp.action"; 
		var responseMethod="downFingerForCompMessage";		
		showDialogmanager = $.ligerDialog.waitting('正在传送门店指纹,请勿此时操作系统...');
		sendRequestForParams_p(requestUrl,responseMethod,params );
	}
		
	function downFingerForCompMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("操作成功!");
	        	 comfirmButton.setDisabled();
	        }
	        else
	        {
	        	$.ligerDialog.error(strMessage);
	        }
	        showDialogmanager.close();
	}
	
	
	function itemclick_downFingerForPerson()
	{
		var params = "strCurCompId="+curRecord.id.compno;			
    	params =params+ "&strCurFingerId="+curRecord.fingerno;	
		params =params+ "&strCurStaffName="+curRecord.staffname;	
     	var requestUrl ="bc012/downFingerForPerson.action"; 
		var responseMethod="downFingerForPersonMessage";	
		showDialogmanager = $.ligerDialog.waitting('正在传送指纹,请勿此时操作系统...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
	}
	
	function downFingerForPersonMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("操作成功.");
	        }
	        else
	        {
	        	$.ligerDialog.error(strMessage);
	        }
	        showDialogmanager.close();
	}
	
	function itemclick_downFactForPerson()
	{
		var params = "strCurCompId="+curRecord.id.compno;			
    	params =params+ "&strCurFingerId="+curRecord.fingerno;	
		params =params+ "&strCurStaffName="+curRecord.staffname;	
     	var requestUrl ="bc012/downFaceForPerson.action"; 
		var responseMethod="downFaceForPersonMessage";	
		showDialogmanager = $.ligerDialog.waitting('正在传送人脸数据,请勿此时操作系统...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
	}
	
	function downFaceForPersonMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("操作成功.");
	        }
	        else
	        {
	        	$.ligerDialog.error(strMessage);
	        }
	        showDialogmanager.close();
	}
	
	function itemclick_backupFactForPerson()
	{
		var params = "strCurCompId="+curRecord.id.compno;			
    	params =params+ "&strCurFingerId="+curRecord.fingerno;	
		params =params+ "&strStaffNo="+curRecord.bstaffno;	
     	var requestUrl ="bc012/backupFactForPerson.action"; 
		var responseMethod="backupFactForPersonMessage";	
		showDialogmanager = $.ligerDialog.waitting('正在传送人脸数据,请勿此时操作系统...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
	}
	
	function backupFactForPersonMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("还原成功.");
	        }
	        else
	        {
	        	$.ligerDialog.error(strMessage);
	        }
	        showDialogmanager.close();
	}
	
	itemclick_backupFactForPerson
	
	function editCurRecord()
	{
		var params = "curStaffinfo.id.compno="+document.getElementById("compno").value;			
    	params =params+ "&curStaffinfo.id.staffno="+document.getElementById("staffno").value;	
		params =params+ "&curStaffinfo.aaddress="+document.getElementById("aaddress").value;
		params =params+ "&curStaffinfo.mobilephone="+document.getElementById("mobilephone").value;
		params =params+ "&curStaffinfo.reservecontect="+document.getElementById("reservecontect").value;
		params =params+ "&curStaffinfo.reservephone="+document.getElementById("reservephone").value;
		params =params+ "&curStaffinfo.introductioner="+document.getElementById("introductioner").value;
		params =params+ "&curStaffinfo.banktype="+document.getElementById("banktype").value;
		params =params+ "&curStaffinfo.bankno="+document.getElementById("bankno").value;
		params =params+ "&curStaffinfo.remark="+document.getElementById("remark").value;	
		params =params+ "&curStaffinfo.staffmark="+document.getElementById("staffmark").value;	
		params =params+ "&curStaffinfo.pccid="+document.getElementById("pccid").value;
		params =params+ "&curStaffinfo.businessflag="+document.getElementById("businessflag").value;
		params =params+ "&curStaffinfo.socialsecurity="+document.getElementById("socialsecurity").value;
		params =params+ "&curStaffinfo.contractdate="+document.getElementById("contractdate").value;
		params =params+ "&curStaffinfo.positiontitle="+document.getElementById("positiontitle").value;
		params =params+ "&curStaffinfo.socialsource="+document.getElementById("socialsource").value;
		params =params+ "&curStaffinfo.absencesalary="+document.getElementById("absencesalary").value; 
		params =params+ "&curStaffinfo.tichengmode="+document.getElementById("tichengmode").value; 
		params =params+ "&curStaffinfo.staffsex="+document.getElementById("staffsex").value;
     	var requestUrl ="bc012/editCurRecord.action"; 
		var responseMethod="editCurRecordMessage";	
		sendRequestForParams_p(requestUrl,responseMethod,params );
	}
	
	function editCurRecordMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("更新成功!");
	        	 comfirmButton.setDisabled();
	        }
	        else
	        {
	        	$.ligerDialog.error(strMessage);
	        }
	        showDialogmanager.close();
	}
	
	

	
	function itemclick_shopInfoFinger()
	{
	}
	
	
	function resetCasherRoleAccount()
	{
		$.ligerDialog.confirm('确认设置当前员工为收银?', function (result)
		{
		    if( result==true)
           	{
				var params = "strCurCompId="+curCompid;			
    			params =params+ "&strCurStaffId="+curRecord.bstaffno;	
    			params =params+ "&strStaffInNo="+curRecord.manageno;	
     			var requestUrl ="bc012/resetCasherRoleAccount.action"; 
				var responseMethod="resetCasherRoleAccountMessage";	
				sendRequestForParams_p(requestUrl,responseMethod,params );
			}
		});  
	}
	
	function resetCasherRoleAccountMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("设置成功!");
	        	 comfirmButton.setDisabled();
	        }
	        else
	        {
	        	$.ligerDialog.error(strMessage);
	        }
	}
	
	function validateStaffBank(obj)
	{
		if(obj.value=="")
		{
			return;
		}
		if(obj.value.length!=16 && obj.value.length!=19)
		{
			$.ligerDialog.error("员工银行卡格式有误,请确认后重新输入!");
			obj.value="";
			return;
		}
		
		if(luhmCheck(obj.value)==false)
		{
			$.ligerDialog.error("员工银行卡格式有误,请确认后重新输入!");
			obj.value="";
		}
	}
		function hotKeyOfSelf(key)
		{
			if(key==13)//回车
			{
				window.event.keyCode=9; //tab
				window.event.returnValue=true;
			}
			else if( key == 83 &&  event.altKey)
			{
				editCurRecord();
			}
	
		}