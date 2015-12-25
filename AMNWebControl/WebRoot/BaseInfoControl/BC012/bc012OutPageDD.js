	var bmzlmanager=null;
	var gwgzmanager=null;
	var yjfsmanager=null;
	var newbmzlmanager=null;
	var newgwgzmanager=null;
	var newyjfsmanager=null;
	var commoninfodivBDDate=null;
	var strEntryCompId="";
	var strEntryBillId="";
	var ibillstate="";
	var handBdInfoFlag=0;
	var statechooseData = [{ choose: 3, text: '已作废' },{ choose: 2, text: '已驳回' },{ choose: 1, text: '已审核' }, { choose: 0, text: '未审核'}];
    $(function ()
   	{
   		try
   		{
	   		if(parent.document.getElementById("sysParamSp103").value=="0")
	   		{
		   	 	$("#validatestartdate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
		   	}
		     parent.comfirmButton=$("#comfirmButton").ligerButton(
		     {
		             text: '提交调动申请', width: 160,
			         click: function ()
			         {
			             comfirmbill();
			         }
		     });
		     var  bmzlData=JSON.parse(parent.parent.loadCommonControlDate("BMZL",0));
           	 bmzlmanager = $("#departmentshow").ligerComboBox({ data: bmzlData, isMultiSelect: false,valueFieldID: 'factDepartmentshow',width:'120' ,alwayShowInDown:true });           	 
           	// newbmzlmanager = $("#newdepartmentshow").ligerComboBox({ data: bmzlData, isMultiSelect: false,valueFieldID: 'factnewDepartmentshow',width:'140'  ,alwayShowInDown:true});           	 
           	 var  gwgzData=JSON.parse(parent.parent.loadCommonControlDate("GZGW",0));
           	 gwgzmanager = $("#positionshow").ligerComboBox({ data: gwgzData, isMultiSelect: false,valueFieldID: 'factPositionshow',width:'120' ,alwayShowInDown:true });           	 
           	 //newgwgzmanager = $("#newpositionshow").ligerComboBox({ data: gwgzData, isMultiSelect: false,valueFieldID: 'factnewPositionshow',width:'140'  ,alwayShowInDown:true});           	 
           	 addOption("","",document.getElementById("afterdepartment"));
            addOption("","",document.getElementById("afterpostation"));
           	var bmzlData=parent.parent.gainCommonInfoByCode("BMZL",0);
		    for(var i=0;i<bmzlData.length;i++)
			{
				addOption(bmzlData[i].bparentcodekey,bmzlData[i].parentcodevalue,document.getElementById("afterdepartment"));
			}		
            var gwgzData=parent.parent.gainCommonInfoByCode("GZGW",0);
		    for(var i=0;i<gwgzData.length;i++)
			{
				addOption(gwgzData[i].bparentcodekey,gwgzData[i].parentcodevalue,document.getElementById("afterpostation"));
			}	
			
           	 var  yjfsData=JSON.parse(parent.parent.loadCommonControlDate("YJFS",0));
             yjfsmanager = $("#resulttyeshow").ligerComboBox({ data: yjfsData, isMultiSelect: false,valueFieldID: 'factResulttyeshow',width:'120' ,alwayShowInDown:true });           	 
           	 newyjfsmanager = $("#newresulttyeshow").ligerComboBox({ data: yjfsData, isMultiSelect: false,valueFieldID: 'factnewResulttyeshow',width:'140'  ,alwayShowInDown:true});           	 
           	 document.getElementById("appchangecompid").value=checkNull(parent.curRecord.id.compno);
   			 document.getElementById("changestaffno").value=checkNull(parent.curRecord.bstaffno);
   			 document.getElementById("staffname").value=checkNull(parent.curRecord.staffname);
   			 document.getElementById("staffmangerno").value=checkNull(parent.curRecord.manageno);
   			 bmzlmanager.selectValue(checkNull(parent.curRecord.department));
   			 gwgzmanager.selectValue(checkNull(parent.curRecord.position));
   			 yjfsmanager.selectValue(checkNull(parent.curRecord.resulttye));
   			 bmzlmanager.setDisabled();
   			 gwgzmanager.setDisabled();
   			 yjfsmanager.setDisabled();
   			 document.getElementById("staffphone").value=checkNull(parent.curRecord.mobilephone);
   			 document.getElementById("staffpcid").value=checkNull(parent.curRecord.pccid);
   			 document.getElementById("beforesalary").value=checkNull(parent.curRecord.basesalary);
   			 document.getElementById("beforeyejirate").value=checkNull(parent.curRecord.resultrate);
   			 document.getElementById("beforeyejiamt").value=checkNull(parent.curRecord.baseresult);
   			 document.getElementById("appchangecompid").value=checkNull(parent.curRecord.id.compno);
   			 document.getElementById("aftercompid").value=checkNull(parent.curRecord.id.compno);
   			 
   			var today = new Date();
			var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
			if(intMonth==12)
			{
				intMonth=1;
				intYear=intYear+1;
			}
			else
			{
				intMonth=intMonth+1;
			}
			document.getElementById("validatestartdate").value= intYear.toString()+"-"+fullStr(intMonth.toString())+"-01";
			/*if(intMonth==1 || intMonth==3 || intMonth==5 || intMonth==7 || intMonth==8 || intMonth==10 || intMonth==12 )
				document.getElementById("validatestartdate").value= intYear.toString()+"-"+fullStr(intMonth.toString())+"-31";
			else if(intMonth==4 || intMonth==6 || intMonth==9 || intMonth==11  )
				document.getElementById("validatestartdate").value= intYear.toString()+"-"+fullStr(intMonth.toString())+"-30";
			else
				document.getElementById("validatestartdate").value= intYear.toString()+"-"+fullStr(intMonth.toString())+"-28";*/
			commoninfodivBDDate=$("#commoninfodivBDDate").ligerGrid({
                columns: [
                { display: '保底工号', 	name: 'handstaffid', 			width:80,align: 	'left'} ,
	            { display: '起始月份', 	name: 'startdate', 				width:90,align: 	'left'},
	            { display: '结束月份', 	name: 'enddate', 				width:90,align: 	'left'},
				{ display: '单据状态', 	name: 'billflag', 		   width:70,align: 	'left', 
	                editor: { type: 'select', data: statechooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.billflag == 1) return '已审核';
	                    else if (item.billflag == 2) return '已驳回';
	                    else if (item.billflag == 3) return '已作废';
	                      return '未审核';
	                }
	            },
	            { display: '申请人', 		name: 'appstaffname', 				width:80,align: 	'left'},
	            { display: '批准人', 		name: 'checkstaffname', 				width:80,align: 	'left'},
	            { name: 'bentrycompid', width:1,hide:true},
	            { name: 'bentrybillid', width:1,hide:true}      
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '95%',
                height:'92%',
                onSelectRow : function (data, rowindex, rowobj)
                {
                    strEntryCompId=data.bentrycompid;
                    strEntryBillId=data.bentrybillid;
                    ibillstate=data.billflag;
                },
                enabledEdit: false,  checkbox: false ,rownumbers: false,usePager: false,
                toolbar: { items: [
                	{ text: '继续保底', click: comfrimBill_lock, img: contextURL+'/common/ligerui/ligerUI/skins/icons/lock.gif' },
	                { text: '人事经理审核', click: comfrimBill_bd, img: contextURL+'/common/ligerui/ligerUI/skins/icons/ok.gif' },
	           		{ text: '人事经理作废', click: comfrimstopBill_bd, img: contextURL+'/common/ligerui/ligerUI/skins/icons/candle.gif' }
					]
                }	  
            });
            if(parent.lsBdDataSet!=null && parent.lsBdDataSet.length>0)
            {
            	handBdInfoFlag=0;
            	commoninfodivBDDate.options.data=$.extend(true, {},{Rows: parent.lsBdDataSet,Total: parent.lsBdDataSet.length});
            	commoninfodivBDDate.loadData(true);
            }
            else
            {
            	addBD();
            	handBdInfoFlag=1;
            }
			parent.curdialog.extend();
	     }catch(e){alert(e.message);}
   	});
   	
   	 function  addBD()
     {
        	var row = commoninfodivBDDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivBDDate.addRow({ 
				                billflag: 0,
				             	bentrycompid:'',
				             	bentrybillid:''
				            }, row, false);
     }
     
     
        
   	function comfirmbill()
   	{
   		if(handBdInfoFlag==0)
   		{
   			$.ligerDialog.warn("调动前请先确认此人的保底信息!");
   			return;
   		}
   		if(document.getElementById("validatestartdate").value=="")
   		{
   			$.ligerDialog.warn("生效日期不能为空!");
   			document.getElementById("validatestartdate").select();
   			document.getElementById("validatestartdate").fouse();
   			return;
   		}
   		
   		if(document.getElementById("afterstaffno").value=="")
   		{
   			$.ligerDialog.warn("员工新工号不能为空!");
   			document.getElementById("afterstaffno").select();
   			document.getElementById("afterstaffno").fouse();
   			return;
   		}
   		if(document.getElementById("afterdepartment").value=="")
   		{
   			$.ligerDialog.warn("员工新部门不能为空!");
   			return;
   		}
   		if(document.getElementById("afterpostation").value=="")
   		{
   			$.ligerDialog.warn("员工新职位不能为空!");
   				return;
   		}
   		document.getElementById("beforedepartment").value=$("#factDepartmentshow").val();
   		document.getElementById("beforepostation").value=$("#factPositionshow").val();
   		document.getElementById("beforeyejitype").value=$("#factResulttyeshow").val();
   		//document.getElementById("afterdepartment").value=$("#factnewDepartmentshow").val();
   		//document.getElementById("afterpostation").value=$("#factnewPositionshow").val();
   		document.getElementById("afteryejitype").value=$("#factnewResulttyeshow").val();
   		try
   		{
   			var queryStringTmp=$('#curRZFrom').serialize();
   			var requestUrl ="bc012/postStaffDDA.action";
   			var responseMethod="postStaffDDMessage";
   			parent.sendRequestForParams_p(requestUrl,responseMethod,queryStringTmp);
   		}catch(e){alert(e.message);}
    }
    
   function comfrimBill_lock()
   {
   		handBdInfoFlag=1;
   }
    function comfrimBill_bd()
	{	
		handBdInfoFlag=1;
		if(strEntryBillId!="")
		{
				 if(checkNull(ibillstate)!=0)
				 {
				    	$.ligerDialog.warn(strEntryBillId+"已被处理,不能再次处理");
       	 				return;
				 }
				var params = "strEntryCompId="+strEntryCompId;
				params = params+"&strEntryBillId="+strEntryBillId;
   				var requestUrl ="bc012/comfrimBillBd.action"; 
				var responseMethod="bdcomfrimBillMessage";
				parent.sendRequestForParams_p(requestUrl,responseMethod,params );
		}
		else
		{
				$.ligerDialog.warn("请选择需要审核的明细!");
       	 		return;
		}
	}
	
	
	
	
	
	function comfrimstopBill_bd()
	{
		handBdInfoFlag=1;
		if(strEntryBillId!="")
		{
				var params = "strEntryCompId="+strEntryCompId;
				params =params+ "&strEntryBillId="+strEntryBillId;
				var requestUrl ="bc012/comfrimstopBillBd.action"; 
				var responseMethod="bdcomfrimstopBillMessage";
				parent.sendRequestForParams_p(requestUrl,responseMethod,params );
		}
		else
		{
				$.ligerDialog.warn("请选择需要作废的明细!");
       	 		return;
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
	}