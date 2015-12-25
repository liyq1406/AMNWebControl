
   	var compTree = null;//主明细	
   	var compTreeManager = null;//主明细	
   	var commoninfodivsecond=null;//卡类型明细
   	var commoninfodivscheduldetial=null;//排班明细
   	var commoninfodivstaffinfodetial=null;
   	var curCompid="";			// 当前门店
   	var curDate="";				// 当前日期
   	var curschedulno="";  		//当前班次
   	var curschedulname="";  	//当前班次
   	var curfromtime="";			//上班时间
   	var curtotime="";			//下班时间
	var curDetialRecord=null;
   	var appflowTypeData = [{ choose: 1, text: '服务项目' }, { choose: 2, text: '开卡金额'}, { choose: 3, text: '充值金额'}, { choose: 4, text: '单卡开卡金额'}, { choose: 5, text: '单卡充值金额'}];
   	var chooseData = [{ choose: 1, text: '已审核' }, { choose: 0, text: '未审核'}];
   	var staffPostionData=JSON.parse(parent.loadCommonControlDate_select("GZGW",0));
   	var curappflowDate=null;
   	var ac018layout=null;
   	var newStoreflowinfo=null;
   	var f_detailPanel=null;
    var f_callback=null;

    $(function ()
   	{
	   try
	   {
	   		  //布局
            ac018layout= $("#ac018layout").ligerLayout({ leftWidth: 270,rightWidth: 285,  allowBottomResize: false, allowLeftResize: false ,isLeftCollapse:true});
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
              	{ display: '日期', 				name: 'scheduldate', 		width:80,align: 'left' },
	            { display: '星期', 				name: 'schedulweek', 		width:100,align: 'left' },
	            { display: '总排班人数', 			name: 'totalCount', 	width:80,align: 'left',
	                render: function (item)
	                { 
	            		var html =item.totalCount;
     					if(checkNull(item.totalCount)*1>0)
     					  html="<font color=\"red\">"+item.totalCount+"</font>";  
     					return html;  
     				}
     			}
                ],  pageSize:20, 
                data:null,      
                width: '320',
                height:'740',
                clickToEdit: false,   enabledEdit: false, 
                detail: { onShowDetail: showSchedulDetialinfo},usePager: false
            });
        	$("#schedulingToolBar").ligerToolBar({ items: [
      		    {text: '年份:&nbsp;<select id="curYear" name="curYear" style="width:70"><option value="2014">2014年</option><option value="2015">2015年</option></select>&nbsp;&nbsp;月份:&nbsp;<select id="curMonth" name="curMonth" style="width:70"><option value="01">1月</option><option value="02">2月</option><option value="03">3月</option><option value="04">4月</option><option value="05">5月</option><option value="06">6月</option><option value="07">7月</option><option value="08">8月</option><option value="09">9月</option><option value="10">10月</option><option value="11">11月</option><option value="12">12月</option></select>&nbsp;&nbsp;工号:&nbsp;<input id="curEmpId" name="curEmpId" style="width:70"/>'},
               	{ text: '加载排班', click: loadCurDetailInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/myaccount.gif' },
                { text: '保存排班列表', click: postCurSchedul, img: contextURL+'/common/ligerui/ligerUI/skins/icons/save.gif' },
               	{ text: '删除排班', click: deleteCurSchedul, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' },
               	{ line: true },
               	{ text: '将: <font color="blue"><label id="lbcurdate">--</label></font>下发至'},
               	{ text: '<input id="curFromDate" name="curFromDate" style="width:80"/> '},
               	{ text: '<input id="curToDate" name="curToDate" style="width:80"/> '},
               	{ click: downStaffschedule, img: contextURL+'/common/ligerui/ligerUI/skins/icons/logout.gif' },
               	{ line: true },
               	{ text: '加载门店人员', click: loadStaffschedule, img: contextURL+'/common/ligerui/ligerUI/skins/icons/customers.gif' },
               	{ text: '选择排班人员', click: uploadStaffschedule, img: contextURL+'/common/ligerui/ligerUI/skins/icons/ok.gif' }
            ]
            });
            $("#curFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'90' });
            $("#curToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'90' });
            commoninfodivscheduldetial=$("#commoninfodivscheduldetial").ligerGrid({
                columns: [
              	{ display: '员工工号', 				name: 'schedulemp', 		width:60,align: 'left' },
	            { display: '员工名称', 				name: 'schedulempname', 		width:80,align: 'left' },
	            { display: '班次', 					name: 'schedulname', 		width:60,align: 'left' },
	            { display: '日期', 					name: 'scheduldate', 		width:80,align: 'left' },
	            { display: '上班时间', 				name: 'fromtime', 		width:80,align: 'left' },
	            { display: '下班时间', 				name: 'totime', 		width:80,align: 'left' },
	            { display: '备注', 					name: 'schedulmark', 		width:120,align: 'left',editor: { type: 'text' } },
	             { hide:true,	name: 'schedulempmanger', 		width:1},
	              { hide:true,	name: 'schedulno', 		width:1}
                ],  pageSize:20, 
                data:null,      
                width: '575',
                height:'740',
                clickToEdit: true,   enabledEdit: true,usePager: false	 
            });
            
            
            commoninfodivstaffinfodetial=$("#commoninfodivstaffinfodetial").ligerGrid({
                columns: [
                { display: '职位', 		name: 'position',  		width:100,align: 	'left' ,
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
	            { display: '员工编号', 	name: 'bstaffno', 		width:80,align: 	'left'}  ,
	            { display: '员工名称', 	name: 'staffname', 		width:80,align: 	'left'}  ,
	            { hide:true,	name: 'manageno', 		width:1}
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '320',
                height:'740',checkbox:true,
                clickToEdit: false,   enabledEdit: false, usePager: false,
                onCheckRow:f_onCheckRow,
                onCheckAllRow: f_onCheckAllRow
            });
            
            var today = new Date();
			var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
			if(intMonth<10)
			intMonth='0'+intMonth;
			document.getElementById("curYear").value=intYear;
			document.getElementById("curMonth").value=intMonth;
          	$("#pageloading").hide(); 
          	addRecord(); 
          	addDetialRecord();
          	addStaffDetialRecord();
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
          }catch(e){alert(e.message);}
    }
    
    function addRecord()
    {
    	var row = commoninfodivsecond.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
		commoninfodivsecond.addRow({ 
				              
				            }, row, false);
    }
    
    
    function addDetialRecord()
    {
    	var row = commoninfodivscheduldetial.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
		commoninfodivscheduldetial.addRow({ 
				             schedulno:'',
				             scheduldate:'',
				             schedulemp:'',
				             schedulempname:'',
				             schedulempmanger:'',
				             fromtime:'',
				             totime:'',
				             schedulmark:''
				            }, row, false);
    }
    
    function addStaffDetialRecord()
    {
    	var row = commoninfodivstaffinfodetial.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
		commoninfodivstaffinfodetial.addRow({ 
				              
				            }, row, false);
    }
    function loadCurDetailInfo()
    {
    
    	var curMonth=document.getElementById("curYear").value+document.getElementById("curMonth").value;
    	$.ligerDialog.confirm('确认加载 '+curCompid+' 门店 '+curMonth+'月份的排班信息', function (result)
	    {
	    	if(result==true)
	    	{
	    		var requestUrl ="ac018/loadStoreSchedulInfo.action";
        		var params="strCurCompId="+curCompid;
				params=params+"&strCurMonth="+curMonth;
				params=params+"&strEmpId="+document.getElementById("curEmpId").value ; 
				var responseMethod="loadStoreSchedulInfoMessage";				
				sendRequestForParams_p(requestUrl,responseMethod,params ); 
	    	}
	    });  
    }
    
    function loadStoreSchedulInfoMessage(request)
    {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	curschedulno="";
	        	curfromtime="";
	        	curtotime="";
	        	curschedulname="";  
	        	var lsCompschedulinfo=responsetext.lsCompschedulinfo;
	        	if(lsCompschedulinfo!=null && lsCompschedulinfo.length>0)
	        	{
	        		commoninfodivsecond.options.data=$.extend(true, {},{Rows: lsCompschedulinfo,Total: lsCompschedulinfo.length});
            		commoninfodivsecond.loadData(true);	
	        	}
	        	else
	        	{
	        		commoninfodivsecond.options.data=$.extend(true, {},{Rows:null,Total: 0});
            		commoninfodivsecond.loadData(true);	
            		addRecord();
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
    }
    
    function showSchedulDetialinfo(row, detailPanel,callback)
    {
    	f_detailPanel=detailPanel;
		f_callback=callback;
		loadSchedulDetialinfo(row.compno,row.scheduldate);
	}
		
	function loadSchedulDetialinfo(strCurCompId,strCurDate)
	{
			curDate=checkNull(strCurDate);
			document.getElementById("lbcurdate").innerHTML=checkNull(strCurDate);
			var requestUrl ="ac018/loadSchedulDetialinfo.action"; 
			var responseMethod="loadSchedulDetialinfoMessage";		
			var param="strCurCompId="+strCurCompId;	
			param=param+"&strCurDate="+strCurDate;
			param=param+"&strEmpId="+document.getElementById("curEmpId").value ; 
			sendRequestForParams_p(requestUrl,responseMethod,param );
	}
	
	function loadSchedulDetialinfoMessage(request)
	{
	    	try
	    	{
	    	var responsetext = eval("(" + request.responseText + ")");
	    	curschedulno="";
	       	curfromtime="";
	        curtotime="";
	        curschedulname="";  
	    	if(responsetext.lsDCompschedulinfo!=null && responsetext.lsDCompschedulinfo.length>0)
	   		{
	   			var detailGrid = document.createElement('div'); 
	            $(f_detailPanel).append(detailGrid);
	            $(detailGrid).css('margin',10).ligerGrid({
	                columns:
	                            [
	                            { display: '班次编号', 		name: 'schedulno' ,	width: 60 },
	                            { display: '班次名称', 		name: 'schedulname' ,width: 80 },
	                          	{ display: '排班人数', 		name: 'totalCount' ,width: 60 ,
					                render: function (item)
					                { 
					            		var html =item.totalCount;
				     					if(checkNull(item.totalCount)*1>0)
				     					  html="<font color=\"blue\">"+item.totalCount+"</font>";  
				     					return html;  
				     				}
				     			},
	                          	{ hide:true,	name: 'scheduldate', 		width:1},
	            				{ hide:true,	name: 'fromtime', 		width:1},
	            				{ hide:true,	name: 'totime', 		width:1}
	                            ], isScroll: false, showToggleColBtn: false, width: '100%', 
	                 			data:{Rows: responsetext.lsDCompschedulinfo,Total: responsetext.lsDCompschedulinfo.length}
	                 			, showTitle: false, rownumbers:false,columnWidth: 100
	                 			, onAfterShowData: f_callback,frozen:false,usePager:false,
				                onSelectRow : function (data, rowindex, rowobj)
				                {
				                    loadSelecSchedulData(data, rowindex, rowobj);
				                } 
	            });
	   		}
	   		}catch(e){alert(e.message)}
	}
	

	function loadStaffschedule()
	{
		var requestUrl ="ac018/loadSchedulStaff.action";
       	var params="strCurCompId="+curCompid;
		var responseMethod="loadSchedulStaffMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 
	}
	
	function loadSchedulStaffMessage(request)
    {
    	var responsetext = eval("(" + request.responseText + ")");
    	checkedBfunctionno= [];
    	if(responsetext.lsStaffinfo!=null && responsetext.lsStaffinfo.length>0)
		{
				commoninfodivstaffinfodetial.options.data=$.extend(true, {},{Rows: responsetext.lsStaffinfo,Total: responsetext.lsStaffinfo.length});
				commoninfodivstaffinfodetial.loadData(true);   
		}
		else
		{
				commoninfodivstaffinfodetial.options.data=$.extend(true, {},{Rows: null,Total:0});
				commoninfodivstaffinfodetial.loadData(true);  
				addStaffDetialRecord();
		}
		commoninfodivTradeDate.sortedData=false;
     }
     
     //选择列表
     
     function f_onCheckAllRow(checked)
     {
       	for (var rowid in commoninfodivstaffinfodetial.records)
       	{
           	if(checked)
            	addCheckedBfunctionno(commoninfodivstaffinfodetial.records[rowid]['manageno']);
            else
                removeCheckedBfunctionno(commoninfodivstaffinfodetial.records[rowid]['manageno']);
       	}
     }
        
	 function f_onCheckRow(checked, data)
     {
        	if (checked) 
            	addCheckedBfunctionno(data.manageno);
            else 
            	removeCheckedBfunctionno(data.manageno);
     }
	 var checkedBfunctionno= [];
	 function findCheckedBfunctionno(manageno)
     {
         for(var i =0;i<checkedBfunctionno.length;i++)
         {
         	if(checkedBfunctionno[i] == manageno) return i;
         }
         return -1;
     }
        
     function addCheckedBfunctionno(manageno)
     {
         if(findCheckedBfunctionno(manageno) == -1)
             checkedBfunctionno.push(manageno);
     }
     function removeCheckedBfunctionno(manageno)
     {
         var i = findCheckedBfunctionno(manageno);
         if(i==-1) return;
     	 checkedBfunctionno.splice(i,1);
     }
     
     function uploadStaffschedule()
     {
        if(curschedulno=="")
        {
        	$.ligerDialog.error("请选择班次!");
        	return;
        }
     	if(checkedBfunctionno.length==0)
     	{
     		$.ligerDialog.error("请选择排班人员!");
        	return;
     	}
     	for (var rowid in commoninfodivstaffinfodetial.records)
		{
				var row =commoninfodivstaffinfodetial.records[rowid];
				if(checkNull(row.manageno)!="")
				{
					for(var i=0;i<checkedBfunctionno.length;i++)
					{
						if(checkNull(row.manageno)==checkedBfunctionno[i])
						{
							var existsflag=0;
							for (var oldrowid in commoninfodivscheduldetial.records)
							{
									var oldrow =commoninfodivscheduldetial.records[oldrowid];
									if(oldrow.schedulempmanger==checkedBfunctionno[i])
									{
										existsflag=1;
										break;
									}
							}       
							if(existsflag==0)
							{
						    	addSchedulAuto(row.bstaffno,row.staffname,row.manageno,curDate,curfromtime,curtotime,curschedulno,curschedulname);
						    }
						}
					}
				}
		}  
		checkedBfunctionno= [];
     }
     
     function addSchedulAuto(bstaffno,staffname,manageno,curDate,curfromtime,curtotime,curschedulno,curschedulname)
     {
     		var gridlen=commoninfodivscheduldetial.rows.length*1;
     		if(gridlen==0)
			{
				addDetialRecord();
				gridlen=gridlen+1;
			} 
			if(checkNull(commoninfodivscheduldetial.getRow(0).schedulno)!="")
			{
				addDetialRecord();
				gridlen=gridlen+1;
			}
			var curDetialRecord=commoninfodivscheduldetial.getRow(gridlen-1);
			commoninfodivscheduldetial.updateRow(curDetialRecord,{   schedulno: curschedulno
															  ,scheduldate :curDate
															  ,schedulemp  : bstaffno
															  ,schedulempname  : staffname
															  ,schedulempmanger  :manageno
															  ,fromtime  : curfromtime
															  ,totime  : curtotime
															  ,schedulmark   : ''
															  ,schedulname: curschedulname
															   }); 
     }
     
     function loadSelecSchedulData(data, rowindex, rowobj)
     {
     		curschedulno=data.schedulno;  		//当前班次
   			curfromtime=data.fromtime;			//上班时间
   			curtotime=data.totime;			//下班时间
   			curschedulname=data.schedulname;  
   			curDate=data.scheduldate;
   			var requestUrl ="ac018/loadStaffSchedul.action";
       		var params="strCurCompId="+curCompid;
       		params=params+"&strCurDate="+curDate;
       		params=params+"&strCurSchedulno="+curschedulno;
       		params=params+"&strEmpId="+document.getElementById("curEmpId").value ; 
			var responseMethod="loadStaffSchedulMessage";
			sendRequestForParams_p(requestUrl,responseMethod,params ); 
     }
       
       
    function loadStaffSchedulMessage(request)
    {
    	var responsetext = eval("(" + request.responseText + ")");
    	if(responsetext.lsDCompschedulinfo!=null && responsetext.lsDCompschedulinfo.length>0)
		{
				commoninfodivscheduldetial.options.data=$.extend(true, {},{Rows: responsetext.lsDCompschedulinfo,Total: responsetext.lsDCompschedulinfo.length});
				commoninfodivscheduldetial.loadData(true);   
		}
		else
		{
				commoninfodivscheduldetial.options.data=$.extend(true, {},{Rows: null,Total:0});
				commoninfodivscheduldetial.loadData(true);  
				addDetialRecord();
		}
     }
       
    function postCurSchedul()
	{
		if(curschedulno=="")
		{
			$.ligerDialog.error("请选择班次!");
        	return;
		}
		var requestUrl ="ac018/postStaffSchedul.action";
       	var params="strCurCompId="+curCompid;
       	params=params+"&strCurDate="+curDate;
       	params=params+"&strCurSchedulno="+curschedulno;
		var responseMethod="postStaffSchedulMessage";		
		var curjosnparam="";
        var needReplaceStr="";
        var strJsonParam_detial="";
        //------卡号段列表
        for (var rowid in commoninfodivscheduldetial.records)
		{
				var row =commoninfodivscheduldetial.records[rowid];
				if(checkNull(row.schedulemp)=="")
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
		else
		{
			$.ligerDialog.error("请选择排班人员!");
        	return;
		}
		sendRequestForParams_p(requestUrl,responseMethod,params ); 
	}
	
	function postStaffSchedulMessage(request)
    {
    	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("保存成功!");
	        	
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
     
     
	function deleteCurSchedul()
	{
		if(curschedulno=="")
		{
			$.ligerDialog.error("请选择班次!");
        	return;
		}
		var selectedRow=commoninfodivscheduldetial.getSelectedRow();
		var requestUrl ="ac018/deleteStaffSchedul.action";
       	var params="strCurCompId="+curCompid;
       	params=params+"&strCurDate="+selectedRow.scheduldate;
       	params=params+"&strCurSchedulno="+selectedRow.schedulno;
       	params=params+"&strCurInid="+selectedRow.schedulempmanger;
       	var responseMethod="deleteStaffSchedulMessage";
       	sendRequestForParams_p(requestUrl,responseMethod,params ); 
	}
	
	function deleteStaffSchedulMessage(request)
    {
    	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("删除成功!");
	        	 commoninfodivscheduldetial.deleteSelectedRow();
	        	
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
     
    function hotKeyOfSelf(key)
	{
	   if(key==13)//回车
		{
			window.event.keyCode=9; //tab
			window.event.returnValue=true;
   			userempmanager1.selectBox.hide();
   			userempmanager2.selectBox.hide();
		}
		else if(key==115)//F4
		{
			commoninfodivscheduldetial.deleteSelectedRow();
		}
	}
	
	function downStaffschedule()
	{
		var strCurDate=document.getElementById("lbcurdate").innerHTML;
		var strFromDate=document.getElementById("curFromDate").value;
		var strToDate=document.getElementById("curToDate").value;
		if(strCurDate=="" || strCurDate=="--")
		{
			$.ligerDialog.error("请选择需要下发的班次日期!");
        	return;
		}
		if(strFromDate=="" || strToDate=="")
		{
			$.ligerDialog.error("请确认需要被下发的起始日期!");
        	return;
		}
		var requestUrl ="ac018/downStaffschedule.action";
       	var params="strCurCompId="+curCompid;
       	params=params+"&strCurDate="+strCurDate;
       	params=params+"&strFromDate="+strFromDate;
       	params=params+"&strToDate="+strToDate;
       	var responseMethod="downStaffscheduleMessage";
       	sendRequestForParams_p(requestUrl,responseMethod,params ); 
	}
	
	function downStaffscheduleMessage(request)
    {
    	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("下发成功!");
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
	
	