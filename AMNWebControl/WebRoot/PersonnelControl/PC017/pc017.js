   	var commoninfodivmaster=null;	//卡入库主档列表
   	var commoninfodivdetial=null;	//卡入库明细列表
   	var commoninfodivdetial_zrj=null;//责任金列表
   	var staffSubsidyDiv=null;
   	var strCurCompId="";
   	var strCurBillId="";
   	var rewardmodemanager=null;
   	var pc017layout=null;
   	var curpagestate=3; // 1 add 2 modify 3 browse
   	var checkButton=null;
   	var confirmButton=null;
   	var checkSubsidyButton=null;
   	var commoninfodivSubsidyStaffinfo=null;
   	var rewaedchooseData = [{ choose: '0', text: '奖励' }, { choose: '1', text: '处罚'}];
   	var staffRewardData=JSON.parse(parent.loadCommonControlDate_select("ZBJF",0));
   	var statechooseData = [{ choose: 1, text: '已审核' }, { choose: 0, text: '未审核'}];
   	var rewardchooseData = [{ choose: 1, text: '处罚' }, { choose: 0, text: '奖励'}];
   	var subsidychooseData = [{ choose: 1, text: '全部满足' }, { choose: 2, text: '部分满足'}];
   	var targemodechooseData = [{ choose: 1, text: '额度' }, { choose: 2, text: '比率'}];
   	var targeyejitypechooseData = [{ choose: 1, text: '个人现金业绩' }, { choose: 2, text: '个人实业绩'}, { choose: 3, text: '个人总业绩'}];
   	var staffPostionData=JSON.parse(parent.loadCommonControlDate_select("GZGW",0));
   	var PC017Tab=null;
   	var commoninfodivdetialtj=null;
   	var commoninfodivsubsidy=null;
   	var commoninfodivtarget=null;
   	var commoninfodivsubsidysearch=null;
   	var curStaffInfoDate=null;
   	var lsStaffinfo=null;
   	//初始化属性
    $(function ()
   	{
	   try
	   {
	   	 	  //布局
            pc017layout= $("#pc017layout").ligerLayout({ rightWidth: 400,  allowBottomResize: false, allowLeftResize: false ,isRightCollapse:true});
	   		$("#PC017Tab").ligerTab();
            PC017Tab = $("#PC017Tab").ligerGetTabManager();
	   		var height = $(".l-layout-center").height();
           	commoninfodivmaster=$("#commoninfodivmaster").ligerGrid({
                columns: [
                { display: '奖罚单号', 	name: 'bentrybillid',  			width:120,align: 	'left' },
	            { display: '奖罚门店', 	name: 'handcompid', 			width:60,align: 	'left'} ,
	            { display: '门店名称', 	name: 'handcompname', 		width:120,align: 	'left'}  
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '320',
                height:'645',
                clickToEdit: false,   enabledEdit: false, usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                }	, 
                toolbar: { items: [
	                { text: '增加', click: itemclick_cardInsertInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
	           		{ text: '保存', click: itemclick_cardInsertInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' }
					]
                }	   
            });
           
            commoninfodivsubsidy=$("#commoninfodivsubsidy").ligerGrid({
                columns: [
                { display: '录入单号', 	name: 'bentrybillid', 			width:120,align: 	'left'} ,
                { display: '保底门店', 	name: 'handcompid', 			width:80,align: 	'left'} ,
	            { display: '门店名称', 	name: 'handcompname', 			width:120,align: 	'left'}, 
	            { display: '保底工号', 	name: 'handstaffid', 			width:80,align: 	'left'} ,
	            { display: '员工名称', 	name: 'handstaffname', 			width:80,align: 	'left'},
	            { display: '保底额度', 	name: 'subsidyamt', 			width:60,align: 	'left'},
	            { display: '保底条件', 	name: 'subsidyconditiontext', 	width:400,align: 	'left' ,
	            	render: function (row) { 
     					return"<font color=\"blue\">"+row.subsidyconditiontext+"</font>";  
 					} 
 				},
	            { display: '保底方式', 	name: 'subsidyflag', 			width:80,align: 	'left', 
	                editor: { type: 'select', data: subsidychooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.subsidyflag == 1) return '全部满足';
	                      return '部分满足';
	                }},
	            { display: '满足条件数', 	name: 'conditionnum', 			width:70,align: 	'left' ,
	            	render: function (row) { 
     					return"<font color=\"red\">"+row.conditionnum+"</font>";  
 					} 
 				},
	            { display: '起始月份', 	name: 'startdate', 				width:90,align: 	'left'},
	            { display: '结束月份', 	name: 'enddate', 				width:90,align: 	'left'},
	            { name: 'subsidycondition', width:1,hide:true},
	            { name: 'handstaffinid', width:1,hide:true}      
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '100%',
                height:'660',
                clickToEdit: false,   enabledEdit: false, usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                   // loadSelecDetialData(data, rowindex, rowobj);
                }	, 
                toolbar: { items: [
                		{ text: '保存当前保底信息', click: postSubsidyInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' }
					]
                }	  
            });
            commoninfodivSubsidyStaffinfo=$("#commoninfodivsubsidy").ligerGrid({
                columns: [
                { display: '员工编号', 	name: 'bentrybillid', 			width:120,align: 	'left'} ,
                { display: '员工职位', 	name: 'handcompid', 			width:80,align: 	'left'} ,
	            { display: '门店名称', 	name: 'handcompname', 			width:120,align: 	'left'}, 
	            { display: '保底工号', 	name: 'handstaffid', 			width:80,align: 	'left'} ,
	            { display: '员工名称', 	name: 'handstaffname', 			width:80,align: 	'left'},
	            { display: '保底额度', 	name: 'subsidyamt', 			width:60,align: 	'left'},
	            { display: '保底条件', 	name: 'subsidyconditiontext', 	width:300,align: 	'left' ,
	            	render: function (row) { 
     					return"<font color=\"blue\">"+row.subsidyconditiontext+"</font>";  
 					} 
 				},
	            { display: '保底方式', 	name: 'subsidyflag', 			width:80,align: 	'left', 
	                editor: { type: 'select', data: subsidychooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.subsidyflag == 1) return '全部满足';
	                      return '部分满足';
	                }},
	            { display: '满足条件数', 	name: 'conditionnum', 			width:70,align: 	'left' ,
	            	render: function (row) { 
     					return"<font color=\"red\">"+row.conditionnum+"</font>";  
 					} 
 				},
	            { display: '起始月份', 	name: 'startdate', 				width:90,align: 	'left'},
	            { display: '结束月份', 	name: 'enddate', 				width:90,align: 	'left'},
	            { display: '申请人', 		name: 'appstaffname', 				width:90,align: 	'left'},
	            { display: '批准人', 		name: 'checkstaffname', 				width:90,align: 	'left'},
	            { name: 'subsidycondition', width:1,hide:true},
	            { name: 'handstaffinid', width:1,hide:true}      
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '100%',
                height:'660',
                clickToEdit: false,   enabledEdit: false, usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                   // loadSelecDetialData(data, rowindex, rowobj);
                }	, 
                toolbar: { items: [
                		{ text: '保存当前保底信息', click: postSubsidyInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' }
					]
                }	  
            });
            
            staffSubsidyDiv=$("#staffSubsidyDiv").ligerGrid({
                columns: [
                { display: '职位', name: 'position',  width: 100 ,align:'left',
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
                { display: '员工', name: 'staffname',  width: 80 ,align: 	'left'},
                { name: 'bstaffno',  width: 1,hide:true}
                ],  pageSize:10, 
                data: null,        
                width: '100%',
                height:'95%',
                enabledEdit: false,checkbox: true,rownumbers:false,usePager:false , where : f_getStaffWhere(),
                onCheckRow:f_onCheckRow,
                onCheckAllRow: f_onCheckAllRow
            });
            
            
            commoninfodivtarget=$("#commoninfodivtarget").ligerGrid({
                columns: [
                { display: '录入单号', 	name: 'bentrybillid', 			width:120,align: 	'left'} ,
                { display: '指标门店', 	name: 'handcompid', 			width:80,align: 	'left'} ,
	            { display: '门店名称', 	name: 'handcompname', 			width:120,align: 	'left'}, 
	            { display: '指标工号', 	name: 'handstaffid', 			width:80,align: 	'left'} ,
	            { display: '员工名称', 	name: 'handstaffname', 			width:80,align: 	'left'},
	            { display: '计算方式', 	name: 'targemode', 				width:60,align: 	'left', 
	                editor: { type: 'select', data: targemodechooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.targemode == 1) return '额度';
	                      return '比率';
	                }
	            },
	            { display: '计算类型', 	name: 'targeyejitype', 				width:60,align: 	'left', 
	                editor: { type: 'select', data: targeyejitypechooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.targeyejitype == 1) return '个人现金业绩';
	                    if (item.targeyejitype == 2) return '个人实业绩';
	                    if (item.targeyejitype == 3) return '个人总业绩';
	                      return '';
	                }
	             },
	            { display: '指标额度', 	name: 'targeamt', 				width:60,align: 	'left'},
	            { display: '指标条件', 	name: 'subsidyconditiontext', 	width:380,align: 	'left' ,
	            	render: function (row) { 
     					return"<font color=\"blue\">"+row.subsidyconditiontext+"</font>";  
 					} 
 				},
	            { display: '指标方式', 	name: 'targeflag', 			width:80,align: 	'left', 
	                editor: { type: 'select', data: subsidychooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.targeflag == 1) return '全部满足';
	                      return '部分满足';
	                }},
	            { display: '满足条件数', 	name: 'conditionnum', 			width:70,align: 	'left' ,
	            	render: function (row) { 
     					return"<font color=\"red\">"+row.conditionnum+"</font>";  
 					} 
 				},
	            { display: '起始月份', 	name: 'startdate', 				width:90,align: 	'left'},
	            { display: '结束月份', 	name: 'enddate', 				width:90,align: 	'left'},
	            { name: 'subsidycondition', width:1,hide:true},
	            { name: 'handstaffinid', width:1,hide:true}      
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '100%',
                height:'660',
                clickToEdit: false,   enabledEdit: false, usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                   // loadSelecDetialData(data, rowindex, rowobj);
                }	, 
                toolbar: { items: [
                		{ text: '保存当前指标信息', click: postTargetInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' }
					]
                }	  
            });
            
            
            commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
                columns: [
                { display: '奖罚员工', 	name: 'handstaffid',  		width:80,align: 	'left' ,
	            	render: function (row) {  
     					var html =row.handstaffid;
     					if(checkNull(row.rewardamt)*1>100)
     					  html="<font color=\"red\">"+row.handstaffid+"</font>";  
     					return html;  
 					}  },
                { display: '员工姓名', 	name: 'handstaffname',  	width:80,align: 	'left' ,
	            	render: function (row) {  
     					var html =row.handstaffname;
     					if(checkNull(row.rewardamt)*1>100)
     					  html="<font color=\"red\">"+row.handstaffname+"</font>";  
     					return html;  
 					}  },
	            { display: '奖罚日期', 	name: 'entrydate', 			width:90,align: 	'left'} ,
	            { display: '奖罚类型', 	name: 'entrytype', 			width:90,align: 	'left',
                    editor: { type: 'select', data: staffRewardData, valueField: 'choose',selectBoxHeight:'220'},
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("ZBJF",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.entrytype)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            } ,
	            { display: '奖罚金额', 	name: 'rewardamt', 			width:60,align: 	'left',
	            	render: function (row) {  
     					var html =row.rewardamt;
     					if(checkNull(row.rewardamt)*1>100)
     					  html="<font color=\"red\">"+row.rewardamt+"</font>";  
     					return html;  
 					}  
 				} , 
	            { display: '奖罚原因', 	name: 'entryreason', 		width:200,align: 	'left'} , 
	            { name: 'handstaffinid',  width:1,hide:true }
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '620',
                height:'540',
                clickToEdit: false,   enabledEdit: false, usePager: false
            });
            
               
             commoninfodivdetial_zrj=$("#commoninfodivdetial_zrj").ligerGrid({
                columns: [
                { display: '门店编号', 	name: 'bcompno',  		width:60,align: 	'left' },
	            { display: '员工编号', 	name: 'bstaffno', 		width:60,align: 	'left'}  ,
	            { display: '员工名称', 	name: 'staffname', 		width:90,align: 	'left'}  ,
	            { display: '责任金', 		name: 'zerenjin', 		width:40,align: 	'left'},
	            { hide:true,	name: 'manageno', 		width:1}
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '280',
                height:'540',
                clickToEdit: false,   enabledEdit: false, usePager: false, 
                toolbar: { items: [
                		{ text: '加载责任金员工', click: loadStaffDutyAmt, img: contextURL+'/common/ligerui/ligerUI/skins/icons/search2.gif' },
                		{ text: '保存', click: postStaffDutyAmt, img: contextURL+'/common/ligerui/ligerUI/skins/icons/save.gif' }
					]
                }	
            });
             var  rewardmodeData=JSON.parse(parent.loadCommonControlDate("ZBJF",0));
           	 rewardmodemanager = $("#entrytype").ligerComboBox({ data: rewardmodeData, isMultiSelect: false,valueFieldID: 'factentrytype',width:'140' });           	 
           	
          
	         checkSubsidyButton=$("#checkSubsidyButton").ligerButton(
	         {
	             text: '录入', width: 140,
		         click: function ()
		         {
		             checkSubsidyBill();
		         }
	         });
	         
	        checkSubsidyButton=$("#checkTargetButton").ligerButton(
	         {
	             text: '录入', width: 140,
		         click: function ()
		         {
		             checkTargetBill();
		         }
	         });
	         $("#searchButton").ligerButton(
	         {
	             text: '查询', width: 100,
		         click: function ()
		         {
		             searchInfo();
		         }
	         });
	         
	         $("#searchTJButton").ligerButton(
	         {
	             text: '统计查询', width: 100,
		         click: function ()
		         {
		             searchTjInfo();
		         }
	         });
	         $("#searchSubsidyButton").ligerButton(
	         {
	             text: '统计查询', width: 100,
		         click: function ()
		         {
		             searchSubsidyInfo();
		         }
	         });
	      	addOption("","",document.getElementById("ientrytype"));
           	var ZBJFtypes=parent.gainCommonInfoByCode("ZBJF",0);
		    for(var i=0;i<ZBJFtypes.length;i++)
			{
				addOption(ZBJFtypes[i].bparentcodekey,ZBJFtypes[i].parentcodevalue,document.getElementById("ientrytype"));
			}		
			
			addOption("","",document.getElementById("strSearchPostion"));
           	var goodstypes=parent.gainCommonInfoByCode("GZGW",0);
		    for(var i=0;i<goodstypes.length;i++)
			{
				addOption(goodstypes[i].bparentcodekey,goodstypes[i].parentcodevalue,document.getElementById("strSearchPostion"));
			}		
           
           
            $("#ientrydate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
            $("#strTJFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'100' });
            $("#strTJToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'100' });
            $("#sstartdate").ligerDateEditor({ labelWidth: 100,format: "yyyy-MM", labelAlign: 'right',width:'100' });
            $("#senddate").ligerDateEditor({ labelWidth: 100,format: "yyyy-MM", labelAlign: 'right',width:'100' });
            $("#tstartdate").ligerDateEditor({ labelWidth: 100,format: "yyyy-MM", labelAlign: 'right',width:'100' });
            $("#tenddate").ligerDateEditor({ labelWidth: 100,format: "yyyy-MM", labelAlign: 'right',width:'100' });
           
            $("#tdateFA").ligerDateEditor({ labelWidth: 100,labelAlign: 'right',width:'100' });
         	$("#tdateEA").ligerDateEditor({ labelWidth: 100,labelAlign: 'right',width:'100' });
			$("#tdateFB").ligerDateEditor({ labelWidth: 100,labelAlign: 'right',width:'100' });
         	$("#tdateEB").ligerDateEditor({ labelWidth: 100,labelAlign: 'right',width:'100' });
         	$("#tdateFC").ligerDateEditor({ labelWidth: 100,labelAlign: 'right',width:'100' });
         	$("#tdateEC").ligerDateEditor({ labelWidth: 100,labelAlign: 'right',width:'100' });
         	$("#tdateFD").ligerDateEditor({ labelWidth: 100,labelAlign: 'right',width:'100' });
         	$("#tdateED").ligerDateEditor({ labelWidth: 100,labelAlign: 'right',width:'100' });
         	$("#tdateFE").ligerDateEditor({ labelWidth: 100,labelAlign: 'right',width:'100' });
         	$("#tdateEE").ligerDateEditor({ labelWidth: 100,labelAlign: 'right',width:'100' });
          	$("#pageloading").hide(); 
          	loadStaffrewardinfo();
          	//addDTjRecord();
          	addSubsudyjRecord();
          	addTargetRecord();
          	addSubsidy();
          	addDRecord_zrj();
   		}catch(e){alert(e.message);}
    });
    
    //加载卡入库主明细
    function loadStaffrewardinfo()
    {
     	var requestUrl ="pc017/loadStaffrewardinfo.action"; 
		var responseMethod="loadStaffrewardinfoMessage";				
		sendRequestForParams_p(requestUrl,responseMethod,"" );
    }
   
   function loadStaffrewardinfoMessage(request)
   {
       		try
        	{
 
        	var responsetext = eval("(" + request.responseText + ")");
        	if(responsetext.lsStaffrewardinfo!=null && responsetext.lsStaffrewardinfo.length>0)
	   		{
	   			curStaffrewardDate={Rows: responsetext.lsStaffrewardinfo,Total: responsetext.lsStaffrewardinfo.length};
	   			commoninfodivmaster.options.data=$.extend(true, {},curStaffrewardDate);
            	commoninfodivmaster.loadData(true);   
            	commoninfodivmaster.select(0);        	
	   		}
	   		else
	   		{
	   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivmaster.loadData(true);  
	   		}
	   		
	   		document.getElementById("sentrycompid").value=responsetext.curMstaffsubsidyinfo.id.entrycompid;
	   		document.getElementById("sentrybillid").value=responsetext.curMstaffsubsidyinfo.id.entrybillid;
	   		document.getElementById("shandcompid").value="";
	   		document.getElementById("shandcompname").value="";
	   		document.getElementById("shandstaffid").value="";
	   		document.getElementById("shandstaffname").value="";
	   		document.getElementById("shandstaffinid").value="";
	   		document.getElementById("ssubsidyamt").value="";
	   		document.getElementById("sstartdate").value="";
	   		document.getElementById("senddate").value="";
	   		document.getElementById("subsidyamtA").value="";
	   		document.getElementById("subsidyamtB").value="";
	   		document.getElementById("subsidyamtC").value="";
	   		document.getElementById("subsidyamtD").value="";
			document.getElementById("subsidyamtE").value="";
			document.getElementById("subsidyamtF").value="";
			var today = new Date();
			var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
			var intDay=today.getDate();
			var today = intYear.toString()+"-"+fullStr(intMonth.toString());
			document.getElementById("sstartdate").value=today;
			document.getElementById("senddate").value=today;
			
			document.getElementById("tentrycompid").value=responsetext.curMstafftargetinfo.id.entrycompid;
	   		document.getElementById("tentrybillid").value=responsetext.curMstafftargetinfo.id.entrybillid;
	   		document.getElementById("thandcompid").value="";
	   		document.getElementById("thandcompname").value="";
	   		document.getElementById("thandstaffid").value="";
	   		document.getElementById("thandstaffname").value="";
	   		document.getElementById("thandstaffinid").value="";
	   		document.getElementById("ttargeamt").value="";
	   		document.getElementById("tstartdate").value="";
	   		document.getElementById("tenddate").value="";
	   		document.getElementById("targetamtA").value="";
	   		document.getElementById("targetamtB").value="";
	   		document.getElementById("targetamtC").value="";
	   		document.getElementById("targetamtD").value="";
			document.getElementById("targetamtE").value="";
			document.getElementById("targetamtF").value="";
			document.getElementById("targetamtG").value="";
			document.getElementById("targetamtH").value="";
			document.getElementById("targetamtI").value="";
			document.getElementById("targetamtJ").value="";
			document.getElementById("targetamtK").value="";
			document.getElementById("tstartdate").value=today;
			document.getElementById("tenddate").value=today;
			/*document.getElementById("tdateFA").value=today;
			document.getElementById("tdateEA").value=today;
			document.getElementById("tdateFB").value=today;
			document.getElementById("tdateEB").value=today;
			document.getElementById("tdateFC").value=today;
			document.getElementById("tdateEC").value=today;
			document.getElementById("tdateFD").value=today;
			document.getElementById("tdateED").value=today;
			document.getElementById("tdateFE").value=today;
			document.getElementById("tdateEE").value=today;*/
			}catch(e){alert(e.message);}
    }
    
    
     //加载卡入库主明细
    function searchInfo()
    {
    	if(document.getElementById("strSearchBillno").value=="" )
    	{
    		$.ligerDialog.error("请输入查询信息!");
		    return ;
    	}
    	var params="strSearchBillno="+document.getElementById("strSearchBillno").value;
     	var requestUrl ="pc017/searchInfo.action"; 
		var responseMethod="searchInfoMessage";				
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
   function searchInfoMessage(request)
   {
       		try
        	{
        
 			curpagestate=3;
        	var responsetext = eval("(" + request.responseText + ")");
        	if(responsetext.lsStaffrewardinfo!=null && responsetext.lsStaffrewardinfo.length>0)
	   		{
	   			curStaffrewardDate={Rows: responsetext.lsStaffrewardinfo,Total: responsetext.lsStaffrewardinfo.length};
	   			commoninfodivmaster.options.data=$.extend(true, {},curStaffrewardDate);
            	commoninfodivmaster.loadData(true);   
            	commoninfodivmaster.select(0); 
                	
	   		}
	   		else
	   		{
	   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivmaster.loadData(true);  
	   		}
			
	   		}catch(e){alert(e.message);}
    }
   
	function getCurOrgFromSearchBarself1()
	{
			var elemId = null;
			var i=2;
			var element;
			var ret="";
		try{	
			elemId = "s1level_"+i;
			element = document.getElementById(elemId);             
			while(typeof(element)!="undefined"&&element != null)
			{
				var seltext = element.options[element.selectedIndex].text;      
				var selvalue = element.options[element.selectedIndex].value;   
			                         
				if(seltext == "*"&&i==2)
				{
					ret = "001";
					return ret;
				}
				else if(seltext == "*"&&i!=2)
				{
					i--;
					tmpElemId = "s1level_"+i;
					tmpElem = document.getElementById(tmpElemId);
					var elemValue = tmpElem.options[tmpElem.selectedIndex].value;
					while(elemValue == "no")
					{
						i--;
						if(i>1)
						{
							tmpElemId = "s1level_"+i;
							tmpElem = document.getElementById(tmpElemId);
							elemValue = tmpElem.options[tmpElem.selectedIndex].value;
						}
						else if(i == 1)
						{
							ret = "001";
							return ret;
						}
						
					}
					ret = elemValue;//tmpElem.options[tmpElem.selectedIndex].value;
				
					return ret;
				}
				i++;                                            
				elemId = "s1level_"+i;                            
				element = document.getElementById(elemId);      
			}
		
			i--;
			tmpElemId = "s1level_"+i;
			tmpElem = document.getElementById(tmpElemId);
			ret = tmpElem.options[tmpElem.selectedIndex].value;
		}catch(Exception)	{alert(Exception.message);}
			return ret;
	}
	   
	       //加载卡入库主明细
	    function searchTjInfo()
	    {
    	var strCurCompId=getCurOrgFromSearchBarself1();

    	var strFromDate=document.getElementById("strTJFromDate").value;
    	var strToDate=document.getElementById("strTJToDate").value;
    	if(strFromDate=="" || strToDate=="")
    	{
    		$.ligerDialog.error("请确认查询日期范围!");
		    return ;
    	}
    	var params = "strCurCompId="+strCurCompId;		
    	params=params+"&strTJFromDate="+strFromDate;	
    	params=params+"&strTJToDate="+strToDate;	
     	var requestUrl ="pc017/searchTjInfo.action"; 
		var responseMethod="searchTjInfoMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
   function searchTjInfoMessage(request)
   {
       		try
        	{
        
 			curpagestate=3;
        	var responsetext = eval("(" + request.responseText + ")");
        	if(responsetext.lsDmstaffrewardinfo!=null && responsetext.lsDmstaffrewardinfo.length>0)
			{
				commoninfodivdetialtj.options.data=$.extend(true, {},{Rows: responsetext.lsDmstaffrewardinfo,Total: responsetext.lsDmstaffrewardinfo.length});
				commoninfodivdetialtj.loadData(true);   
					           
			}
			else
			{
				commoninfodivdetialtj.options.data=$.extend(true, {},{Rows: null,Total:0});
				commoninfodivdetialtj.loadData(true);  
				//addDTjRecord();
			}
			
	   		}catch(e){alert(e.message);}
    }
    
    function loadCurMaster(curMaster)
    {
    	try
    	{
    		
    		document.getElementById("entrycompid").value=checkNull(curMaster.id.entrycompid);
	    	document.getElementById("entrybillid").value=checkNull(curMaster.id.entrybillid);
	    	document.getElementById("entryflag").value=checkNull(curMaster.entryflag);
	    	document.getElementById("billflag").value=checkNull(curMaster.billflag);
	    	document.getElementById("handcompid").value=checkNull(curMaster.handcompid);
	        document.getElementById("ihandstaffid").value="";
	        document.getElementById("ihandstaffname").value="";
	        document.getElementById("ihandstaffinid").value="";
	        document.getElementById("ientrytype").value="";
	        document.getElementById("ientrydate").value="";
	        document.getElementById("irewardamt").value="";
	        document.getElementById("ientryreason").value="";
	        
			if(curpagestate==1 )
			{
				
				pageWriteState();
				
			}
			else
			{
			
				pageReadState();
			}
    	}
    	catch(e){alert(e.message);}
    	
    }
    
    function pageWriteState()
    {
	    try
	    {
	    	 document.getElementById("ihandstaffid").readOnly="";
	        document.getElementById("ihandstaffname").readOnly="";
	        document.getElementById("ientrydate").readOnly="";
	        document.getElementById("irewardamt").readOnly="";
	        document.getElementById("ientryreason").readOnly="";
    	}catch(e){alert(e.message);}
	}
    
    function pageReadState(type)
    {
    	try
	    {
	    	document.getElementById("ihandstaffid").readOnly="readOnly";
	        document.getElementById("ihandstaffname").readOnly="readOnly";
	        document.getElementById("ientrydate").readOnly="readOnly";
	        document.getElementById("irewardamt").readOnly="readOnly";
	        document.getElementById("ientryreason").readOnly="readOnly";
    	}catch(e){alert(e.message);}
    }
    
    //根据主档选择加载明细
    function loadSelecDetialData(data, rowindex, rowobj)
    {
        try{
        		
        		 strCurCompId=data.bentrycompid;
        		 strCurBillId=data.bentrybillid;
       			 var params = "strCurCompId="+strCurCompId;
       			 params =params+ "&strCurBillId="+strCurBillId;
				 var myAjax = new parent.Ajax.Request(
						"pc017/loadCurStaffrewardinfo.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {	
								var responsetext = eval( "("+request.responseText+")");		
	   							
	   							if(responsetext.lsDmstaffrewardinfo!=null && responsetext.lsDmstaffrewardinfo.length>0)
						   		{
									commoninfodivdetial.options.data=$.extend(true, {},{Rows: responsetext.lsDmstaffrewardinfo,Total: responsetext.lsDmstaffrewardinfo.length});
					            	commoninfodivdetial.loadData(true);   
					            	curpagestate=3;	
						   		}
						   		else
						   		{
						   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
					            	commoninfodivdetial.loadData(true);  
					            	addDRecord();
					            	curpagestate=1;	
						   		}
								
						   		loadCurMaster(responsetext.curStaffrewardinfo);
						   		clearOption("otherStaffNo");
								addOption("","",document.getElementById("otherStaffNo"));
								loadCurMaster(responsetext.curStaffrewardinfo);
								if(responsetext.lsOldStaffInfo!=null && responsetext.lsOldStaffInfo.length>0)
								{	
									for(var i=0;i<responsetext.lsOldStaffInfo.length;i++)
									{
										addOption(responsetext.lsOldStaffInfo[i].manageno+"_"+responsetext.lsOldStaffInfo[i].bstaffno+"_"+responsetext.lsOldStaffInfo[i].staffname,responsetext.lsOldStaffInfo[i].bstaffno+"_"+responsetext.lsOldStaffInfo[i].staffname,document.getElementById("otherStaffNo"));
									}	
								}   	
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
	    	 $.ligerDialog.confirm('确认提交 员工奖罚 '+item.text+' 操作', function (result)
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
		        		 	$.ligerDialog.warn("未修改数据,无需保存!");
		        		 	return ;
		        		 }
		        	 	editCurRecord();
		        	}
		        }
		      });  
    	}
    	catch(e){alert(e.message);}
    }
    
    function editCurRecord()
    {
    	if(parent.hasFunctionRights( "PC017",  "UR_POST")!=true)
        {
       		$.ligerDialog.warn("该用户没有修改权限,请确认!");
       	 	return;
        }
        if(curpagestate==3)
		{
		    $.ligerDialog.warn("该单据已经保存,不能再次保存!");
		     return ;
		}
		document.getElementById("handcompid").value=getCurOrgFromSearchBar();
    	var queryStringTmp=$('#curRewardFrom').serialize();
		var requestUrl ="pc017/post.action";
		var params=queryStringTmp;
		var curjosnparam="";
        var needReplaceStr="";
        var strJsonParam_detial="";
        //------卡号段列表
        for (var rowid in commoninfodivdetial.records)
		{
				var row =commoninfodivdetial.records[rowid];
				if(checkNull(row.handstaffid)=="")
					continue;
				curjosnparam=JSON.stringify(row);
				curjosnparam=curjosnparam.replace("%","");
				curjosnparam=curjosnparam.replace("#","");
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
     		if(parent.hasFunctionRights( "PC017",  "UR_MODIFY")!=true)
	        {
	        	$.ligerDialog.warn("该用户没有编辑权限,请确认!");
	        }
	        else
	        {
	        	
				var params = "";			
    	     	var requestUrl ="pc017/add.action"; 
				var responseMethod="addMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params );
	        }
     }
     function addDRecord()
     {
     		if(parent.hasFunctionRights( "PC017",  "UR_MODIFY")!=true)
	        {
	        	$.ligerDialog.warn("该用户没有编辑权限,请确认!");
	        }
	        else
	        {
	        	var row = commoninfodivdetial.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
				   commoninfodivdetial.addRow({ 
				                handstaffid: "",
				                handstaffinid: "",
				                entryreason:"",
				                entrydate:"",
				                entrytype:"",
				                rewardamt:""
				            }, row, false);
	        }
     }
     
     function addDRecord_zrj()
     {
     		var row = commoninfodivdetial_zrj.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivdetial_zrj.addRow({ 
				            }, row, false);
     }
     
     
     function addDTjRecord()
     {
     		
	        	var row = commoninfodivdetialtj.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
				   commoninfodivdetialtj.addRow({ 
				                handstaffid: "",
				                handstaffinid: "",
				                entryreason:"",
				                entrydate:"",
				                entrytype:"",
				                rewardamt:""
				            }, row, false);
     }
     function addSubsudyjRecord()
     {
     		
	        	var row = commoninfodivsubsidy.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
				   commoninfodivsubsidy.addRow({ 
				                handcompid: "",
				                handstaffid: "",
				                handstaffinid: "",
				                subsidyconditiontext:"",
				                conditionnum:""
				            }, row, false);
     }
     function addTargetRecord()
     {
     		
	        	var row = commoninfodivtarget.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
				   commoninfodivtarget.addRow({ 
				                handcompid: "",
				                handstaffid: "",
				                handstaffinid: "",
				                subsidyconditiontext:"",
				                conditionnum:""
				            }, row, false);
     }
     function addMessage(request)
     {
	    	try
	        {
	        	var responsetext = eval("(" + request.responseText + ")");
	    		loadCurMaster(responsetext.curStaffrewardinfo);
		   		commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
				commoninfodivdetial.loadData(true);  
				addDRecord();
				var row = commoninfodivmaster.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
				   commoninfodivmaster.addRow({ 
				                bcinsertcompid: "",
				                bcinsertbillid: ""
				            }, row, false);
				clearOption("otherStaffNo");
				addOption("","",document.getElementById("otherStaffNo"));
				loadCurMaster(responsetext.curStaffrewardinfo);
				if(responsetext.lsOldStaffInfo!=null && responsetext.lsOldStaffInfo.length>0)
				{	
					for(var i=0;i<responsetext.lsOldStaffInfo.length;i++)
					{
						addOption(responsetext.lsOldStaffInfo[i].manageno+"_"+responsetext.lsOldStaffInfo[i].bstaffno+"_"+responsetext.lsOldStaffInfo[i].staffname,responsetext.lsOldStaffInfo[i].bstaffno+"_"+responsetext.lsOldStaffInfo[i].staffname,document.getElementById("otherStaffNo"));
					}	
				}   
		   	}catch(e){alert(e.message);}
     }
     
    //删除行
    function deleteCurRecord()
    {
       	if(parent.hasFunctionRights( "PC017",  "UR_DELETE")!=true)
        {
        	 $.ligerDialog.warn("该用户没有删除权限,请确认!");
        	 return;
        }
        var requestUrl ="pc017/delete.action";
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
  
   //--------------------------验证验货人员-----------------
   function validateStaffNo(obj)
   {
   		if(obj.value=="")
   		{
   			document.getElementById("ihandstaffname").value="";
   			document.getElementById("ihandstaffinid").value="";
   			return ;
   		}
   		var requestUrl ="pc017/validateInserper.action";
        var params="strCurCompId="+getCurOrgFromSearchBar();
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
	       		document.getElementById("ihandstaffname").value=responsetext.strCurEmpName;
	       		document.getElementById("ihandstaffinid").value=responsetext.strCurEmpInid;
	       	}
	       	else
	       	{
	       		document.getElementById("ihandstaffid").value="";
	       		document.getElementById("ihandstaffinid").value="";
	       		document.getElementById("ihandstaffname").value="";
	       		$.ligerDialog.warn(strMessage);
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	

  
   
  
   	function checkbill()
   	{
   		if(parent.hasFunctionRights( "PC017",  "UR_SPECIAL_CHECK")!=true)
        {
       		$.ligerDialog.warn("该用户没有审核权限,请确认!");
       	 	return;
        }
       var requestUrl ="pc017/checkbill.action";
	   var params="strCurCompId="+strCurCompId;
	   params=params+"&strCurBillId="+strCurBillId;
	   var responseMethod="checkbillMessage";		
	   sendRequestForParams_p(requestUrl,responseMethod,params ); 	
   		
   	}
   	
   	function checkmanagerStaffNo(value)
	{
		  	var staffNo=value;
		  	$.ligerDialog.prompt('输入经理签单密码','', function (yes,value) { if(yes) checkmanagerPass(staffNo,value); });
		
	}
		  
	function checkmanagerPass(staffNo,value)
	{
		  		var requestUrl ="pc017/checkmanagerPass.action";
				var responseMethod="checkmanagerPassMessage";	
				var params="strCurManagerNo="+staffNo;	
				params =params+ "&strCurManagerPass="+value;	
				sendRequestForParams_p(requestUrl,responseMethod,params ); 
				
	}
	function checkmanagerPassMessage(request)
	{
	    		
	        	try
				{
					var responsetext = eval("(" + request.responseText + ")");
		        	var strMessage=responsetext.strMessage;
		        	if(checkNull(strMessage)=="")
		        	{	     
		        		var requestUrl ="pc017/checkbill.action";
				        var params="strCurCompId="+strCurCompId;
				        params=params+"&strCurBillId="+strCurBillId;
				        var responseMethod="checkbillMessage";		
						sendRequestForParams_p(requestUrl,responseMethod,params ); 	
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
   	
   	function checkbillMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		  $.ligerDialog.success("门店审核成功!");
	       		  checkButton.setDisabled();
				
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

   	
   	function loadGird(obj)
   	{
   		if(obj.value=="" || curpagestate==3)
   		{
   			return;
   		}
   		if(document.getElementById("ihandstaffid").value=="")
   		{
   			$.ligerDialog.error("请确认处罚员工");
   			document.getElementById("ihandstaffid").select();
   			obj.value="";
   			return;
   		}
   		if(document.getElementById("ientrytype").value=="")
   		{
   			$.ligerDialog.error("请确认处罚类型");
   			document.getElementById("ientrytype").select();
   			obj.value="";
   			return;
   		}
   		if(document.getElementById("ientrydate").value=="")
   		{
   			$.ligerDialog.error("请确认处罚日期");
   			document.getElementById("ientrydate").select();
   			obj.value="";
   			return;
   		}
   		if(document.getElementById("irewardamt").value*1==0)
   		{
   			$.ligerDialog.error("请确认处罚金额");
   			document.getElementById("irewardamt").select();
   			obj.value="";
   			return;
   		}
   		var gridlen=commoninfodivdetial.rows.length*1;
		if(gridlen==0)
		{
			addDRecord();
			gridlen=gridlen+1;
		} 
		if(checkNull(commoninfodivdetial.getRow(0).handstaffid)!="")
		{
			addDRecord();
			gridlen=gridlen+1;
		}
		try
		{
		var curDetialRecord=commoninfodivdetial.getRow(gridlen-1);
		commoninfodivdetial.updateRow(curDetialRecord,{       handstaffid: document.getElementById("ihandstaffid").value
															  ,handstaffname :document.getElementById("ihandstaffname").value
															  ,handstaffinid  : document.getElementById("ihandstaffinid").value
															  ,entryreason  : document.getElementById("ientryreason").value
															  ,entrydate  : document.getElementById("ientrydate").value
															  ,entrytype  : document.getElementById("ientrytype").value
															  ,rewardamt   : ForDight(document.getElementById("irewardamt").value*1,1)
															   });  
		document.getElementById("ihandstaffid").value="";
		document.getElementById("ihandstaffname").value="";
		document.getElementById("ihandstaffinid").value="";
		document.getElementById("ientryreason").value="";
		document.getElementById("ientrydate").value="";
		document.getElementById("ientrytype").value="";
		document.getElementById("irewardamt").value="";	
		document.getElementById("ihandstaffid").select();
		document.getElementById("ihandstaffid").focus();	 
		}catch(e){alert(e.message);}     
   	}
   	
   	function validateShandcompid(obj)
   	{
   		if(obj.value=="")
   		{
   			document.getElementById("shandcompname").value="";
   		}
   		else
   		{
   			var requestUrl ="pc017/validateShandcompid.action";
       	 	var params="strCurCompId="+obj.value;
       	 	params=params+"&strCurEmpId="+obj.value;
			var responseMethod="validateShandcompidMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params ); 	 
   		}
   	}
   	
   	function validateShandcompidMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		document.getElementById("shandcompname").value=responsetext.strCurCompName;
	       		curStaffInfoDate={Rows: responsetext.lsStaffinfo,Total: responsetext.lsStaffinfo.length};
	       		staffSubsidyDiv.options.data=$.extend(true, {},curStaffInfoDate);
            	staffSubsidyDiv.loadData(true);
            	checkedBstaffno= []
            	lsStaffinfo=responsetext.lsStaffinfo;
	       	}
	       	else
	       	{
	       		
	       		document.getElementById("shandcompid").value="";
	       		document.getElementById("shandcompname").value="";
	       		$.ligerDialog.warn(strMessage);
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	
   	 function searchStaffInfo()
     {
    
     	   staffSubsidyDiv.options.data = $.extend(true, {}, curStaffInfoDate);
           staffSubsidyDiv.loadData(f_getStaffWhere());
           checkedBstaffno= []
     }
     
   	
   	 function f_getStaffWhere()
     {
     
     	try
    	{
            if (!staffSubsidyDiv) return null;
            var clause = function (rowdata, rowindex)
            {
                var key = document.getElementById("strSearchPostion").value;
                return (key=="" || rowdata.position.indexOf(key) > -1);
            };
            return clause; 
        }
    	catch(e){alert(e.message);}
     }
   	
   	function validateShandstaffid(obj)
   	{
   		if(obj.value=="")
   		{
   			document.getElementById("shandstaffname").value="";
   			document.getElementById("handstaffinid").value="";
   			return;
   		}
   		if(document.getElementById("shandcompid").value=="")
   		{
   			$.ligerDialog.error("请确认登记门店");
   			obj.value="";
   			document.getElementById("shandstaffname").value="";
   			document.getElementById("handstaffinid").value="";
   			return;
   		}
   		var requestUrl ="pc017/validateInserper.action";
        var params="strCurCompId="+document.getElementById("shandcompid").value;
        params=params+"&strCurEmpId="+obj.value;
		var responseMethod="validateShandstaffidMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 
   	}
   	
   	function validateShandstaffidMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		document.getElementById("shandstaffname").value=responsetext.strCurEmpName;
	       		document.getElementById("shandstaffinid").value=responsetext.strCurEmpInid;
	       	}
	       	else
	       	{
	       		document.getElementById("shandstaffid").value="";
	       		document.getElementById("shandstaffinid").value="";
	       		document.getElementById("shandstaffname").value="";
	       		$.ligerDialog.warn(strMessage);
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	
   	
   	function checkSubsidyBill()
   	{
   		if(document.getElementById("shandcompid").value=="")
   		{
   			$.ligerDialog.error("请确认登记门店");
   			document.getElementById("sentrybillid").select();
   			return;
   		}
   	
   		else if(document.getElementById("shandstaffid").value=="" && checkedBstaffno.length==0)
   		{
   			$.ligerDialog.error("请确认保底员工");
   			document.getElementById("shandcompname").select();
   			return;
   		}
   		else if(document.getElementById("ssubsidyamt").value*1==0)
   		{
   			$.ligerDialog.error("请确认保底额度");
   			document.getElementById("shandstaffname").select();
   			return;
   		}
   		else if(document.getElementById("sstartdate").value=="" || document.getElementById("senddate").value=="")
   		{
   			$.ligerDialog.error("请确认保底日期");
   			document.getElementById("ssubsidyamt").select();
   			return;
   		}
   		/*else if(document.getElementById("conditionA").checked==true && document.getElementById("subsidyamtA").value*1==0)
   		{
   			$.ligerDialog.error("请确认个人虚业绩的设定值");
   			document.getElementById("subsidyamtA").select();
   			return;
   		}
   		else if(document.getElementById("conditionB").checked==true && document.getElementById("subsidyamtB").value*1==0)
   		{
   			$.ligerDialog.error("请确认个人实业绩的设定值");
   			document.getElementById("subsidyamtB").select();
   			return;
   		}
   		else if(document.getElementById("conditionC").checked==true && document.getElementById("subsidyamtC").value*1==0)
   		{
   			$.ligerDialog.error("请确认个人老客数的设定值");
   			document.getElementById("subsidyamtC").select();
   			return;
   		}
   		else if(document.getElementById("conditionD").checked==true && document.getElementById("subsidyamtD").value*1==0)
   		{
   			$.ligerDialog.error("请确认美容大项数的设定值");
   			document.getElementById("subsidyamtD").select();
   			return;
   		}
   		else if(document.getElementById("conditionE").checked==true && document.getElementById("subsidyamtE").value*1==0)
   		{
   			$.ligerDialog.error("请确认烫染疗程数的设定值");
   			document.getElementById("subsidyamtE").select();
   			return;
   		}*/
   		
	    var  subsidyconditiontext="";
	    var  subsidycondition="";
	    if(	document.getElementById("subsidyamtA").value*1>0)
	    {
	    	subsidyconditiontext=subsidyconditiontext+"个人虚业绩 :"+document.getElementById("subsidyamtA").value*1+" 上; ";
	    	subsidycondition=subsidycondition+"1,"+document.getElementById("subsidyamtA").value*1+";";
	    }
	    if(	document.getElementById("subsidyamtB").value*1>0)
	    {
	    	subsidyconditiontext=subsidyconditiontext+"个人实业绩 :"+document.getElementById("subsidyamtB").value*1+" 上; ";
	    	subsidycondition=subsidycondition+"2,"+document.getElementById("subsidyamtB").value*1+";"
	    }
	    if(	document.getElementById("subsidyamtC").value*1>0)
	    {
	    	subsidyconditiontext=subsidyconditiontext+"个人老客数 :"+document.getElementById("subsidyamtC").value*1+" ; ";
	    	subsidycondition=subsidycondition+"3,"+document.getElementById("subsidyamtC").value*1+";"
	    }
	    if(	document.getElementById("subsidyamtD").value*1>0)
	    {
	    	subsidyconditiontext=subsidyconditiontext+"美容大项数 :"+document.getElementById("subsidyamtD").value*1+" ; ";
	    	subsidycondition=subsidycondition+"4,"+document.getElementById("subsidyamtD").value*1+";"
	    }
	    if(	document.getElementById("subsidyamtE").value*1>0)
	    {
	    	subsidyconditiontext=subsidyconditiontext+"烫染疗程数 :"+document.getElementById("subsidyamtE").value*1+" ; ";
	    	subsidycondition=subsidycondition+"5,"+document.getElementById("subsidyamtE").value*1+";"
	    }
	    if(	document.getElementById("subsidyamtF").value*1>0)
	    {
	    	subsidyconditiontext=subsidyconditiontext+"美发大项数 :"+document.getElementById("subsidyamtF").value*1+" ; ";
	    	subsidycondition=subsidycondition+"6,"+document.getElementById("subsidyamtF").value*1+";"
	    }
	    var startBill=document.getElementById("sentrybillid").value.substring(0,document.getElementById("sentrybillid").value.length-4);
	    var endBill=document.getElementById("sentrybillid").value.substring(document.getElementById("sentrybillid").value.length-4,document.getElementById("sentrybillid").value.length);
	    var gridlen=commoninfodivsubsidy.rows.length*1;
				
	 	if(checkedBstaffno.length==0)
	 	{
	 		if(gridlen==0)
			{
				addSubsudyjRecord();
				gridlen=gridlen+1;
			} 
			if(checkNull(commoninfodivsubsidy.getRow(0).handstaffid)!="")
			{
				addSubsudyjRecord();
				gridlen=gridlen+1;
			}
			var curDetialRecord=commoninfodivsubsidy.getRow(gridlen-1);
			commoninfodivsubsidy.updateRow(curDetialRecord,{      bentrybillid: document.getElementById("sentrybillid").value
															  ,handcompid :document.getElementById("shandcompid").value
															  ,handcompname  : document.getElementById("shandcompname").value
															  ,handstaffid  : document.getElementById("shandstaffid").value
															  ,handstaffname  : document.getElementById("shandstaffname").value
															  ,handstaffinid  : document.getElementById("shandstaffinid").value
															  ,subsidyamt  : document.getElementById("ssubsidyamt").value
															  ,conditionnum   : checkNull(document.getElementById("sconditionnum").value)*1
															  ,subsidyflag   : document.getElementById("ssubsidyflag").value*1
															  ,startdate   : document.getElementById("sstartdate").value
															  ,enddate   : document.getElementById("senddate").value
															  ,appstaffname   : document.getElementById("appstaffname").value
															  ,checkstaffname   : document.getElementById("checkstaffname").value
															  ,subsidyconditiontext : subsidyconditiontext
															  ,subsidycondition:subsidycondition
															   }); 
			document.getElementById("sentrybillid").value=startBill+(endBill*1+1);
		}
		else
		{
			gridlen=commoninfodivsubsidy.rows.length*1;
			if(gridlen==0)
			{
				addSubsudyjRecord();
				gridlen=gridlen+1;
			} 
			var curDetialRecord=null;
			var curstaffname="";
			var curstaffinid="";
			for(var i=0;i<checkedBstaffno.length;i++)
			{
				
				if(checkNull(commoninfodivsubsidy.getRow(0).handstaffid)!="")
				{
					addSubsudyjRecord();
				}
				if(lsStaffinfo!=null && lsStaffinfo.length>0)
				{
					for(var j=0;j<lsStaffinfo.length;j++)
					{
						if(lsStaffinfo[j].bstaffno==checkedBstaffno[i])
						{
							curstaffname=lsStaffinfo[j].staffname;
							curstaffinid=lsStaffinfo[j].manageno;
						}
					}
				}
				curDetialRecord=commoninfodivsubsidy.getRow((gridlen*1+i)-1);
				commoninfodivsubsidy.updateRow(curDetialRecord,{      bentrybillid: document.getElementById("sentrybillid").value
															  ,handcompid :document.getElementById("shandcompid").value
															  ,handcompname  : document.getElementById("shandcompname").value
															  ,handstaffid  : checkedBstaffno[i]
															  ,handstaffname  : curstaffname
															  ,handstaffinid  : curstaffinid
															  ,subsidyamt  : document.getElementById("ssubsidyamt").value
															  ,conditionnum   : checkNull(document.getElementById("sconditionnum").value)*1
															  ,subsidyflag   : document.getElementById("ssubsidyflag").value*1
															  ,startdate   : document.getElementById("sstartdate").value
															  ,enddate   : document.getElementById("senddate").value
															  ,appstaffname   : document.getElementById("appstaffname").value
															  ,checkstaffname   : document.getElementById("checkstaffname").value
															  ,subsidyconditiontext : subsidyconditiontext
															  ,subsidycondition:subsidycondition
															   }); 
				document.getElementById("sentrybillid").value=startBill+(endBill*1+(i*1+1));
			}
		}
	    		
	    		document.getElementById("shandcompid").value="";
	    		document.getElementById("shandcompname").value="";
	    		document.getElementById("shandstaffid").value="";
	    		document.getElementById("shandstaffname").value="";
	    		document.getElementById("shandstaffinid").value="";
	    		document.getElementById("ssubsidyamt").value="";
	    		document.getElementById("sconditionnum").value="";
	    		document.getElementById("ssubsidyflag").value=1;
	    		document.getElementById("sstartdate").value="";
	    		document.getElementById("senddate").value="";
	    		document.getElementById("appstaffname").value="";
	    		document.getElementById("checkstaffname").value="";
	    		//document.getElementById("conditionA").checked=false;
	    		document.getElementById("subsidyamtA").value=0;
	    		//document.getElementById("conditionB").checked=false;
	    		document.getElementById("subsidyamtB").value=0;
	    		//document.getElementById("conditionC").checked=false;
	    		document.getElementById("subsidyamtC").value=0;
	    		//document.getElementById("conditionD").checked=false;
	    		document.getElementById("subsidyamtD").value=0;
	    		//document.getElementById("conditionE").checked=false;
	    		document.getElementById("subsidyamtE").value=0;
	    		document.getElementById("subsidyamtF").value=0;
	    		document.getElementById("shandcompid").select();
	    		document.getElementById("shandcompid").focus();
	    		document.getElementById("sconditionnum").disabled=true;
	    		var today = new Date();
				var intYear=today.getYear();
				var intMonth=today.getMonth()+1;
				var intDay=today.getDate();
				var today = intYear.toString()+"-"+fullStr(intMonth.toString());
				document.getElementById("sstartdate").value=today;
				document.getElementById("senddate").value=today;
				checkedBstaffno= [];
				staffSubsidyDiv.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            staffSubsidyDiv.loadData(true);
		/**/
															   
   	}
   	
   	function loadConditioninfo(obj)
   	{
   		if(obj.value==1)
   		{
   			document.getElementById("sconditionnum").disabled=true;
   			document.getElementById("sconditionnum").value="";
   		}
   		else
   		{
   			document.getElementById("sconditionnum").disabled=false;
   			document.getElementById("sconditionnum").value="1";
   		}
   	}
   	
   	function loadTargemodeChange(obj)
   	{
   		if(obj.value==1)
   		{
   			document.getElementById("targeyejitype").disabled=true;
   			document.getElementById("targeyejitype").value="";
   		}
   		else
   		{
   			document.getElementById("targeyejitype").disabled=false;
   			document.getElementById("targeyejitype").value="3";
   		}
   	}
   	
   	
     
     
     
    function postSubsidyInfo()
    {
    	if(parent.hasFunctionRights( "PC017",  "UR_POST")!=true)
        {
       		$.ligerDialog.warn("该用户没有修改权限,请确认!");
       	 	return;
        }
		var requestUrl ="pc017/postSubsidyInfo.action";
		var params="";
		var curjosnparam="";
        var needReplaceStr="";
        var strJsonParam_detial="";
        //------卡号段列表
        for (var rowid in commoninfodivsubsidy.records)
		{
				var row =commoninfodivsubsidy.records[rowid];
				if(checkNull(row.bentrybillid)=="")
					continue;
				curjosnparam=JSON.stringify(row);
				curjosnparam=curjosnparam.replace("%","");
				curjosnparam=curjosnparam.replace("#","");
				if(strJsonParam_detial!="")
				  	strJsonParam_detial=strJsonParam_detial+",";
				strJsonParam_detial= strJsonParam_detial+curjosnparam;
		}            		 
		if(strJsonParam_detial!="")
		{
			 params=params+"strSubsidyJsonParam=["+strJsonParam_detial+"]";
		}
		var responseMethod="postSubsidyInfoMessage";
	    sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
   	function postSubsidyInfoMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("更新成功,请到薪资录入统计表中查询!");
	        	var params = "";			
    			var requestUrl ="pc017/addSubsidy.action"; 
				var responseMethod="addSubsidyMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params );
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
    
    
    function validateThandcompid(obj)
   	{
   		if(obj.value=="")
   		{
   			document.getElementById("thandcompname").value="";
   		}
   		else
   		{
   			var requestUrl ="pc017/validateShandcompid.action";
       	 	var params="strCurCompId="+obj.value;
       	 	params=params+"&strCurEmpId="+obj.value;
			var responseMethod="validateThandcompidMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params ); 	 
   		}
   	}
   	
   	function validateThandcompidMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		document.getElementById("thandcompname").value=responsetext.strCurCompName;
	       	}
	       	else
	       	{
	       		document.getElementById("thandcompid").value="";
	       		document.getElementById("thandcompname").value="";
	       		$.ligerDialog.warn(strMessage);
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	
   	function validateThandstaffid(obj)
   	{
   		if(obj.value=="")
   		{
   			document.getElementById("thandstaffname").value="";
   			document.getElementById("tandstaffinid").value="";
   			return;
   		}
   		if(document.getElementById("thandcompid").value=="")
   		{
   			$.ligerDialog.error("请确认登记门店");
   			obj.value="";
   			document.getElementById("thandstaffname").value="";
   			document.getElementById("thandstaffinid").value="";
   			return;
   		}
   		var requestUrl ="pc017/validateInserper.action";
        var params="strCurCompId="+document.getElementById("thandcompid").value;
        params=params+"&strCurEmpId="+obj.value;
		var responseMethod="validateTShandstaffidMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 
   	}
   	
   	function validateTShandstaffidMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		document.getElementById("thandstaffname").value=responsetext.strCurEmpName;
	       		document.getElementById("thandstaffinid").value=responsetext.strCurEmpInid;
	       	}
	       	else
	       	{
	       		document.getElementById("thandstaffid").value="";
	       		document.getElementById("thandstaffinid").value="";
	       		document.getElementById("thandstaffname").value="";
	       		$.ligerDialog.warn(strMessage);
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	
   	
   	   	function checkTargetBill()
   	{
   	
   		if(document.getElementById("thandcompid").value=="")
   		{
   			$.ligerDialog.error("请确认登记门店");
   			document.getElementById("tentrybillid").select();
   			return;
   		}
   		else if(document.getElementById("thandstaffid").value=="")
   		{
   			$.ligerDialog.error("请确认指标员工");
   			document.getElementById("thandcompname").select();
   			return;
   		}
   		else if(document.getElementById("ttargeamt").value*1==0)
   		{
   			$.ligerDialog.error("请确认指标额度");
   			document.getElementById("thandstaffname").select();
   			return;
   		}
   		else if(document.getElementById("tstartdate").value=="" || document.getElementById("tenddate").value=="")
   		{
   			$.ligerDialog.error("请确认指标日期");
   			document.getElementById("targeamt").select();
   			return;
   		}
   		/*else if((document.getElementById("tdateFA").value=="" || document.getElementById("tdateEA").value=="" )  && document.getElementById("targetamtA").value*1!=0)
   		{
   			$.ligerDialog.error("请确认个人虚业绩的设定日期");
   			document.getElementById("targetamtA").select();
   			return;
   		}
   		else if((document.getElementById("tdateFB").value=="" || document.getElementById("tdateEB").value=="" )  && document.getElementById("targetamtB").value*1!=0)
   		{
   			$.ligerDialog.error("请确认个人实业绩的设定日期");
   			document.getElementById("targetamtB").select();
   			return;
   		}
   		else if((document.getElementById("tdateFC").value=="" || document.getElementById("tdateEC").value=="" )  && document.getElementById("targetamtC").value*1!=0)
   		{
   			$.ligerDialog.error("请确认个人老客数的设定日期");
   			document.getElementById("targetamtC").select();
   			return;
   		}
   		else if((document.getElementById("tdateFD").value=="" || document.getElementById("tdateED").value=="" )  && document.getElementById("targetamtD").value*1!=0)
   		{
   			$.ligerDialog.error("请确认美容大项数的设定日期");
   			document.getElementById("targetamtD").select();
   			return;
   		}
   		else if((document.getElementById("tdateFE").value=="" || document.getElementById("tdateEE").value=="" )  && document.getElementById("targetamtE").value*1!=0)
   		{
   			$.ligerDialog.error("请确认烫染疗程数的设定日期");
   			document.getElementById("targetamtE").select();
   			return;
   		}*/
   		var gridlen=commoninfodivtarget.rows.length*1;
		if(gridlen==0)
		{
			addTargetRecord();
			gridlen=gridlen+1;
		} 
		if(checkNull(commoninfodivtarget.getRow(0).handstaffid)!="")
		{
			addTargetRecord();
			gridlen=gridlen+1;
		}
	    var  subsidyconditiontext="";
	    var  subsidycondition="";
	    if(	document.getElementById("targetamtA").value*1>0)
	    {
	    	subsidyconditiontext=subsidyconditiontext+"个人虚业绩 :"+document.getElementById("targetamtA").value*1+" 上; ";
	    	subsidycondition=subsidycondition+"1,"+document.getElementById("targetamtA").value*1+";";//+","+document.getElementById("tdateFA").value+","+document.getElementById("tdateEA").value+";";
	    }
	    if(	document.getElementById("targetamtB").value*1>0)
	    {
	    	subsidyconditiontext=subsidyconditiontext+"个人实业绩 :"+document.getElementById("targetamtB").value*1+" 上; ";
	    	subsidycondition=subsidycondition+"2,"+document.getElementById("targetamtB").value*1+";";//+","+document.getElementById("tdateFB").value+","+document.getElementById("tdateEB").value+";";
	    }
	    if(	document.getElementById("targetamtC").value*1>0)
	    {
	    	subsidyconditiontext=subsidyconditiontext+"个人老客数 :"+document.getElementById("targetamtC").value*1+" ; ";
	    	subsidycondition=subsidycondition+"3,"+document.getElementById("targetamtC").value*1+";";//+","+document.getElementById("tdateFC").value+","+document.getElementById("tdateEC").value+";";
	    }
	    if(	document.getElementById("targetamtD").value*1>0)
	    {
	    	subsidyconditiontext=subsidyconditiontext+"美容大项数 :"+document.getElementById("targetamtD").value*1+" ; ";
	    	subsidycondition=subsidycondition+"4,"+document.getElementById("targetamtD").value*1+";";//+","+document.getElementById("tdateFD").value+","+document.getElementById("tdateED").value+";";
	    }
	    if(	document.getElementById("targetamtE").value*1>0)
	    {
	    	subsidyconditiontext=subsidyconditiontext+"烫染疗程数 :"+document.getElementById("targetamtE").value*1+" ; ";
	    	subsidycondition=subsidycondition+"5,"+document.getElementById("targetamtE").value*1+";";//+","+document.getElementById("tdateFE").value+","+document.getElementById("tdateEE").value+";";
	    }
		if(	document.getElementById("targetamtF").value*1>0)
	    {
	    	subsidyconditiontext=subsidyconditiontext+"门店美容虚业绩 :"+document.getElementById("targetamtF").value*1+" ; ";
	    	subsidycondition=subsidycondition+"6,"+document.getElementById("targetamtF").value*1+";";//+","+document.getElementById("tdateFE").value+","+document.getElementById("tdateEE").value+";";
	    }
	    if(	document.getElementById("targetamtG").value*1>0)
	    {
	    	subsidyconditiontext=subsidyconditiontext+"门店美发虚业绩 :"+document.getElementById("targetamtG").value*1+" ; ";
	    	subsidycondition=subsidycondition+"7,"+document.getElementById("targetamtG").value*1+";";//+","+document.getElementById("tdateFE").value+","+document.getElementById("tdateEE").value+";";
	    }
	    if(	document.getElementById("targetamtH").value*1>0)
	    {
	    	subsidyconditiontext=subsidyconditiontext+"门店总虚业绩 :"+document.getElementById("targetamtH").value*1+" ; ";
	    	subsidycondition=subsidycondition+"8,"+document.getElementById("targetamtH").value*1+";";//+","+document.getElementById("tdateFE").value+","+document.getElementById("tdateEE").value+";";
	    }
	    if(	document.getElementById("targetamtI").value*1>0)
	    {
	    	subsidyconditiontext=subsidyconditiontext+"门店美容实业绩 :"+document.getElementById("targetamtI").value*1+" ; ";
	    	subsidycondition=subsidycondition+"9,"+document.getElementById("targetamtI").value*1+";";//+","+document.getElementById("tdateFE").value+","+document.getElementById("tdateEE").value+";";
	    }
	    if(	document.getElementById("targetamtJ").value*1>0)
	    {
	    	subsidyconditiontext=subsidyconditiontext+"门店美发实业绩 :"+document.getElementById("targetamtJ").value*1+" ; ";
	    	subsidycondition=subsidycondition+"10,"+document.getElementById("targetamtJ").value*1+";";//+","+document.getElementById("tdateFE").value+","+document.getElementById("tdateEE").value+";";
	    }
	    if(	document.getElementById("targetamtK").value*1>0)
	    {
	    	subsidyconditiontext=subsidyconditiontext+"门店总实业绩 :"+document.getElementById("targetamtK").value*1+" ; ";
	    	subsidycondition=subsidycondition+"11,"+document.getElementById("targetamtK").value*1+";";//+","+document.getElementById("tdateFE").value+","+document.getElementById("tdateEE").value+";";
	    }
		var curDetialRecord=commoninfodivtarget.getRow(gridlen-1);
		commoninfodivtarget.updateRow(curDetialRecord,{      bentrybillid: document.getElementById("tentrybillid").value
															  ,handcompid :document.getElementById("thandcompid").value
															  ,handcompname  : document.getElementById("thandcompname").value
															  ,handstaffid  : document.getElementById("thandstaffid").value
															  ,handstaffname  : document.getElementById("thandstaffname").value
															  ,handstaffinid  : document.getElementById("thandstaffinid").value
															  ,targeamt  : document.getElementById("ttargeamt").value
															  ,conditionnum   : checkNull(document.getElementById("tconditionnum").value)*1
															  ,targeflag   : document.getElementById("ttargeflag").value*1
															  ,targemode   : document.getElementById("targemode").value*1
															  ,targeyejitype   : document.getElementById("targeyejitype").value*1
															  ,startdate   : document.getElementById("tstartdate").value
															  ,enddate   : document.getElementById("tenddate").value
															  ,subsidyconditiontext : subsidyconditiontext
															  ,subsidycondition:subsidycondition
															   }); 
	    		var startBill=document.getElementById("tentrybillid").value.substring(0,document.getElementById("tentrybillid").value.length-4);
	    		var endBill=document.getElementById("tentrybillid").value.substring(document.getElementById("tentrybillid").value.length-4,document.getElementById("tentrybillid").value.length);
	    		document.getElementById("tentrybillid").value=startBill+(endBill*1+1);
	    		document.getElementById("thandcompid").value="";
	    		document.getElementById("thandcompname").value="";
	    		document.getElementById("thandstaffid").value="";
	    		document.getElementById("thandstaffname").value="";
	    		document.getElementById("thandstaffinid").value="";
	    		document.getElementById("ttargeamt").value="";
	    		document.getElementById("tconditionnum").value="";
	    		document.getElementById("ttargeflag").value=1;
	    		document.getElementById("targemode").value=1;
	    		document.getElementById("targeyejitype").value=0;
	    		document.getElementById("tstartdate").value="";
	    		document.getElementById("tenddate").value="";
	    		//document.getElementById("tdateFA").value="";
	    		//document.getElementById("tdateEA").value="";
	    		document.getElementById("targetamtA").value=0;
	    		//document.getElementById("tdateFB").value="";
	    		//document.getElementById("tdateEB").value="";
	    		document.getElementById("targetamtB").value=0;
	    		//document.getElementById("tdateFC").value="";
	    		//document.getElementById("tdateEC").value="";
	    		document.getElementById("targetamtC").value=0;
	    		//document.getElementById("tdateFD").value="";
	    		//document.getElementById("tdateED").value="";
	    		document.getElementById("targetamtD").value=0;
	    		//document.getElementById("tdateFE").value="";
	    		//document.getElementById("tdateEE").value="";
	    		document.getElementById("targetamtE").value=0;
	    		document.getElementById("targetamtF").value=0;
	    		document.getElementById("targetamtG").value=0;
	    		document.getElementById("targetamtH").value=0;
	    		document.getElementById("targetamtI").value=0;
	    		document.getElementById("targetamtJ").value=0;
	    		document.getElementById("targetamtK").value=0;
	    		
	    		document.getElementById("thandcompid").select();
	    		document.getElementById("thandcompid").focus();
	    		document.getElementById("sconditionnum").disabled=true;
	    		var today = new Date();
				var intYear=today.getYear();
				var intMonth=today.getMonth()+1;
				var intDay=today.getDate();
				var today = intYear.toString()+"-"+fullStr(intMonth.toString());
				document.getElementById("tstartdate").value=today;
				document.getElementById("tenddate").value=today;
				
				/*document.getElementById("tdateFA").value=today;
				document.getElementById("tdateEA").value=today;
				document.getElementById("tdateFB").value=today;
				document.getElementById("tdateEB").value=today;
				document.getElementById("tdateFC").value=today;
				document.getElementById("tdateEC").value=today;
				document.getElementById("tdateFD").value=today;
				document.getElementById("tdateED").value=today;
				document.getElementById("tdateFE").value=today;
				document.getElementById("tdateEE").value=today;*/
			
		/**/
															   
   	}
   	
   	function loadConditioninfo(obj)
   	{
   		if(obj.value==1)
   		{
   			document.getElementById("sconditionnum").disabled=true;
   			document.getElementById("sconditionnum").value="";
   		}
   		else
   		{
   			document.getElementById("sconditionnum").disabled=false;
   			document.getElementById("sconditionnum").value="1";
   		}
   	}
   	
   
    function postTargetInfo()
    {
    	if(parent.hasFunctionRights( "PC017",  "UR_POST")!=true)
        {
       		$.ligerDialog.warn("该用户没有修改权限,请确认!");
       	 	return;
        }
		var requestUrl ="pc017/postTargetInfo.action";
		var params="";
		var curjosnparam="";
        var needReplaceStr="";
        var strJsonParam_detial="";
        //------卡号段列表
        for (var rowid in commoninfodivtarget.records)
		{
				var row =commoninfodivtarget.records[rowid];
				if(checkNull(row.bentrybillid)=="")
					continue;
				curjosnparam=JSON.stringify(row);
				curjosnparam=curjosnparam.replace("%","");
				curjosnparam=curjosnparam.replace("#","");
				if(strJsonParam_detial!="")
				  	strJsonParam_detial=strJsonParam_detial+",";
				strJsonParam_detial= strJsonParam_detial+curjosnparam;
		}            		 
		if(strJsonParam_detial!="")
		{
			 params=params+"strTargetJsonParam=["+strJsonParam_detial+"]";
		}
		var responseMethod="postTargetInfoMessage";
	    sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
   	function postTargetInfoMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("更新成功,请到薪资录入统计表中查询!");
	        	var params = "";			
    			var requestUrl ="pc017/addTarget.action"; 
				var responseMethod="addTargetMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params );
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
    
    function addTargetMessage(request)
     {
	    	try
	        {
	        	var responsetext = eval("(" + request.responseText + ")");
	        	commoninfodivtarget.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivtarget.loadData(true); 
            	addTargetRecord(); 
	    		document.getElementById("tentrybillid").value=responsetext.curMstafftargetinfo.id.entrybillid;
	    		document.getElementById("thandcompid").value="";
	    		document.getElementById("thandcompname").value="";
	    		document.getElementById("thandstaffid").value="";
	    		document.getElementById("thandstaffname").value="";
	    		document.getElementById("thandstaffinid").value="";
	    		document.getElementById("ttargeamt").value="";
	    		document.getElementById("tconditionnum").value="";
	    		document.getElementById("ttargeflag").value=1;
	    		document.getElementById("tstartdate").value="";
	    		document.getElementById("tenddate").value="";
	    		//document.getElementById("tdateFA").value="";
	    		//document.getElementById("tdateEA").value="";
	    		document.getElementById("targetamtA").value=0;
	    		//document.getElementById("tdateFB").value="";
	    		//document.getElementById("tdateEB").value="";
	    		document.getElementById("targetamtB").value=0;
	    		//document.getElementById("tdateFC").value="";
	    		//document.getElementById("tdateEC").value="";
	    		document.getElementById("targetamtC").value=0;
	    		//document.getElementById("tdateFD").value="";
	    		//document.getElementById("tdateED").value="";
	    		document.getElementById("targetamtD").value=0;
	    		//document.getElementById("tdateFE").value="";
	    		//document.getElementById("tdateEE").value="";
	    		document.getElementById("targetamtE").value=0;
	    		document.getElementById("thandcompid").select();
	    		document.getElementById("thandcompid").focus();
	    		document.getElementById("sconditionnum").disabled=true;
	    		var today = new Date();
				var intYear=today.getYear();
				var intMonth=today.getMonth()+1;
				var intDay=today.getDate();
				var today = intYear.toString()+"-"+fullStr(intMonth.toString());
				document.getElementById("tstartdate").value=today;
				document.getElementById("tenddate").value=today;
		   	}catch(e){alert(e.message);}
     }
    
     function addSubsidyMessage(request)
     {
	    	try
	        {
	        	var responsetext = eval("(" + request.responseText + ")");
	        	commoninfodivsubsidy.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivsubsidy.loadData(true); 
            	addSubsudyjRecord(); 
	    		document.getElementById("sentrybillid").value=responsetext.curMstaffsubsidyinfo.id.entrybillid;
	    		document.getElementById("shandcompid").value="";
	    		document.getElementById("shandcompname").value="";
	    		document.getElementById("shandstaffid").value="";
	    		document.getElementById("shandstaffname").value="";
	    		document.getElementById("shandstaffinid").value="";
	    		document.getElementById("ssubsidyamt").value="";
	    		document.getElementById("sconditionnum").value="";
	    		document.getElementById("ssubsidyflag").value="";
	    		document.getElementById("sstartdate").value="";
	    		document.getElementById("senddate").value="";
	    		//document.getElementById("conditionA").checked=false;
	    		document.getElementById("subsidyamtA").value=0
	    		//document.getElementById("conditionB").checked=false;
	    		document.getElementById("subsidyamtB").value=0
	    		//document.getElementById("conditionC").checked=false;
	    		document.getElementById("subsidyamtC").value=0
	    		//document.getElementById("conditionD").checked=false;
	    		document.getElementById("subsidyamtD").value=0
	    		//document.getElementById("conditionE").checked=false;
	    		document.getElementById("subsidyamtE").value=0
	    		document.getElementById("subsidyamtF").value=0;
	    		var today = new Date();
				var intYear=today.getYear();
				var intMonth=today.getMonth()+1;
				var intDay=today.getDate();
				var today = intYear.toString()+"-"+fullStr(intMonth.toString());
				document.getElementById("sstartdate").value=today;
				document.getElementById("senddate").value=today;	
		   	}catch(e){alert(e.message);}
     }
     
     
     function addSubsidy()
     {
     	var row = staffSubsidyDiv.getSelectedRow();
     	staffSubsidyDiv.addRow({ 
				                position: ""
				            }, row, false);
     }
     
     
        function f_onCheckAllRow(checked)
        {
        	for (var rowid in staffSubsidyDiv.records)
            {
            	if(checked)
                    addcheckedBstaffno(staffSubsidyDiv.records[rowid]['bstaffno']);
                else
                    removecheckedBstaffno(staffSubsidyDiv.records[rowid]['bstaffno']);
            }
        }
        
      //获取特殊权限多选的模块
	   	function f_onCheckRow(checked, data)
        {
        	if (checked) 
            	addcheckedBstaffno(data.bstaffno);
            else 
            	removecheckedBstaffno(data.bstaffno);
        }
	   	var checkedBstaffno= [];
	   	function findcheckedBstaffno(bstaffno)
        {
            for(var i =0;i<checkedBstaffno.length;i++)
            {
                if(checkedBstaffno[i] == bstaffno) return i;
            }
            return -1;
        }
        
        function addcheckedBstaffno(bstaffno)
        {
            if(findcheckedBstaffno(bstaffno) == -1)
                checkedBstaffno.push(bstaffno);
        }
        function removecheckedBstaffno(bstaffno)
        {
            var i = findcheckedBstaffno(bstaffno);
            if(i==-1) return;
            checkedBstaffno.splice(i,1);
        }
        
        
/** 是否都是数字 **/
function isNumber_E(sNum) {
	  var   re   =   /^\d+(?=\.\d+$|$)/   
      return   re.test(sNum);
}

	function hotKeyOfSelf(key)
	{
	   if(key==13)//回车
		{
			window.event.keyCode=9; //tab
			window.event.returnValue=true;
   			userempmanager1.selectBox.hide();
   			userempmanager2.selectBox.hide();
		}
		else if( key == 83 &&  event.altKey)
		{
			editCurRecord();
		}
		else if(key==115)//F4
		{
				
				 deletedetialRecord();
		}
	}
	
	function deletedetialRecord()
	{
		commoninfodivsubsidy.deleteSelectedRow();
		commoninfodivtarget.deleteSelectedRow();
		commoninfodivdetial_zrj.deleteSelectedRow();
	}
	
	function validateOtherStaffNo(obj)
	{
		if(obj.value=="")
		{
			document.getElementById("ihandstaffid").value="";
			document.getElementById("ihandstaffname").value="";
			document.getElementById("ihandstaffinid").value="";
		}
		else
		{
			var oldinfo=obj.value.split("_");
			document.getElementById("ihandstaffid").value=oldinfo[1];
			document.getElementById("ihandstaffname").value=oldinfo[2];
			document.getElementById("ihandstaffinid").value=oldinfo[0];
		}
	}
	
	function loadCurCompId(obj)
	{
		if(obj==4 || obj==3)
		{
			var curCompId=getCurOrgFromSearchBar();
			if(curpagestate!=3)
			{
				document.getElementById("ihandstaffid").value="";
				document.getElementById("ihandstaffname").value="";
				document.getElementById("ihandstaffinid").value="";
				var requestUrl ="pc017/loadOldStaffInfo.action";
       	 		var params="strCurCompId="+curCompId;
				var responseMethod="loadOldStaffInfoMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params ); 	 
			}
		}
	}
	
	function loadOldStaffInfoMessage(request)
    {
    	var responsetext = eval("(" + request.responseText + ")");
	    clearOption("otherStaffNo");
		addOption("","",document.getElementById("otherStaffNo"));
		if(responsetext.lsOldStaffInfo!=null && responsetext.lsOldStaffInfo.length>0)
		{	
			for(var i=0;i<responsetext.lsOldStaffInfo.length;i++)
			{
				addOption(responsetext.lsOldStaffInfo[i].manageno+"_"+responsetext.lsOldStaffInfo[i].bstaffno+"_"+responsetext.lsOldStaffInfo[i].staffname,responsetext.lsOldStaffInfo[i].bstaffno+"_"+responsetext.lsOldStaffInfo[i].staffname,document.getElementById("otherStaffNo"));
			}	
		}   
     }
     
     
    function loadStaffDutyAmt()
	{
		var curCompId=getCurOrgFromSearchBar();
		var requestUrl ="pc017/loadStaffDutyAmt.action";
       	var params="strCurCompId="+curCompId;
		var responseMethod="loadStaffDutyAmtMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	
	}
	
	function loadStaffDutyAmtMessage(request)
    {
    	var responsetext = eval("(" + request.responseText + ")");
    	if(responsetext.lsStaffinfoDutyAmt!=null && responsetext.lsStaffinfoDutyAmt.length>0)
		{
				commoninfodivdetial_zrj.options.data=$.extend(true, {},{Rows: responsetext.lsStaffinfoDutyAmt,Total: responsetext.lsStaffinfoDutyAmt.length});
				commoninfodivdetial_zrj.loadData(true);   
		}
		else
		{
				commoninfodivdetial_zrj.options.data=$.extend(true, {},{Rows: null,Total:0});
				commoninfodivdetial_zrj.loadData(true);  
				addDRecord_zrj();
		}
     }
    function postStaffDutyAmt()
	{
		var today = new Date();
		var intYear=today.getYear();
		var intMonth=today.getMonth()+1;
		var intDay=today.getDate();
		var today = intYear.toString()+"-"+fullStr(intMonth.toString())+"-"+fullStr(intDay.toString());
	   	$.ligerDialog.prompt('输入责任金输入日期',today, function (yes,value) { if(yes) postStaffDutyAmtInDate(value) });
	   
	}
	function postStaffDutyAmtInDate(dutyDate)
	{
		
		var curCompId=getCurOrgFromSearchBar();
		var requestUrl ="pc017/postStaffDutyAmt.action";
       	var params="strCurCompId="+curCompId;
       	params=params+"&strSearchDate="+dutyDate;
		var responseMethod="postStaffDutyAmtMessage";		
		var curjosnparam="";
        var needReplaceStr="";
        var strJsonParam_detial="";
        //------卡号段列表
        for (var rowid in commoninfodivdetial_zrj.records)
		{
				var row =commoninfodivdetial_zrj.records[rowid];
				if(checkNull(row.bstaffno)=="")
					continue;
				curjosnparam=JSON.stringify(row);
				curjosnparam=curjosnparam.replace("%","");
				curjosnparam=curjosnparam.replace("#","");
				if(strJsonParam_detial!="")
				  	strJsonParam_detial=strJsonParam_detial+",";
				strJsonParam_detial= strJsonParam_detial+curjosnparam;
		}            		 
		if(strJsonParam_detial!="")
		{
			 params=params+"&strJsonParam=["+strJsonParam_detial+"]";
		}
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	
	}
    
    function postStaffDutyAmtMessage(request)
    {
    	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("保存成功,请到薪资奖罚录入统计表中查询!");
	        	
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