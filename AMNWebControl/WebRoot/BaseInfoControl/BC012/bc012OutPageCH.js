	var bmzlmanager=null;
	var gwgzmanager=null;
	var yjfsmanager=null;
	var newbmzlmanager=null;
	var newgwgzmanager=null;
	var newyjfsmanager=null;
	
	$(function ()
   	{
   		try
   		{
	   	
		     $("#validatestartdate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
		     parent.comfirmButton=$("#comfirmButton").ligerButton(
		     {
		             text: '提交调动申请', width: 160,
			         click: function ()
			         {
			             comfirmbill();
			         }
		     });
		     var  bmzlData=JSON.parse(parent.parent.loadCommonControlDate("BMZL",0));
           	 bmzlmanager = $("#departmentshow").ligerComboBox({ data: bmzlData, isMultiSelect: false,valueFieldID: 'factDepartmentshow',width:'120',alwayShowInDown:true });           	 
           	 //newbmzlmanager = $("#newdepartmentshow").ligerComboBox({ data: bmzlData, isMultiSelect: false,valueFieldID: 'factnewDepartmentshow',width:'140',alwayShowInDown:true });           	 
           	 var  gwgzData=JSON.parse(parent.parent.loadCommonControlDate("GZGW",0));
           	 gwgzmanager = $("#positionshow").ligerComboBox({ data: gwgzData, isMultiSelect: false,valueFieldID: 'factPositionshow',width:'120',alwayShowInDown:true });           	 
           	 //newgwgzmanager = $("#newpositionshow").ligerComboBox({ data: gwgzData, isMultiSelect: false,valueFieldID: 'factnewPositionshow',width:'140' ,alwayShowInDown:true});           	 
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
             yjfsmanager = $("#resulttyeshow").ligerComboBox({ data: yjfsData, isMultiSelect: false,valueFieldID: 'factResulttyeshow',width:'120' ,alwayShowInDown:true});           	 
           	 newyjfsmanager = $("#newresulttyeshow").ligerComboBox({ data: yjfsData, isMultiSelect: false,valueFieldID: 'factnewResulttyeshow',width:'140',alwayShowInDown:true });           	 
           	 document.getElementById("appchangecompid").value=checkNull(parent.curCompid);
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
   			 parent.curdialog.extend();
	     }catch(e){alert(e.message);}
   	});
   	
   	function comfirmbill()
   	{
   		if(document.getElementById("validatestartdate").value=="")
   		{
   			$.ligerDialog.warn("生效日期不能为空!");
   			document.getElementById("validatestartdate").select();
   			document.getElementById("validatestartdate").fouse();
   			return;
   		}
   		if(document.getElementById("appchangecompid").value==document.getElementById("aftercompid").value)
   		{
   			$.ligerDialog.warn("跨店新门店不能为老门店,请确认!");
   			document.getElementById("aftercompid").value="";
   			document.getElementById("aftercompid").select();
   			document.getElementById("aftercompid").fouse();
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
   		document.getElementById("afteryejitype").value=$("#factnewResulttyeshow").val();
   		try
   		{
   			var queryStringTmp=$('#curRZFrom').serialize();
   			var requestUrl ="bc012/postStaffCH.action";
   			var responseMethod="postStaffCHessage";
   			parent.sendRequestForParams_p(requestUrl,responseMethod,queryStringTmp);
   		}catch(e){alert(e.message);}
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