	var bmzlmanager=null;
	var gwgzmanager=null;
	var yjfsmanager=null;

	$(function ()
   	{
   		try
   		{
	   	
		     $("#validatestartdate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
		     parent.comfirmButton=$("#comfirmButton").ligerButton(
		     {
		             text: '提交离职申请', width: 160,
			         click: function ()
			         {
			             comfirmbill();
			         }
		     });
		     var  bmzlData=JSON.parse(parent.parent.loadCommonControlDate("BMZL",0));
           	 bmzlmanager = $("#departmentshow").ligerComboBox({ data: bmzlData, isMultiSelect: false,valueFieldID: 'factDepartmentshow',width:'120'  ,alwayShowInDown:true});           	 
           	 var  gwgzData=JSON.parse(parent.parent.loadCommonControlDate("GZGW",0));
           	 gwgzmanager = $("#positionshow").ligerComboBox({ data: gwgzData, isMultiSelect: false,valueFieldID: 'factPositionshow',width:'140'  ,alwayShowInDown:true});           	 
           	 var  yjfsData=JSON.parse(parent.parent.loadCommonControlDate("YJFS",0));
             yjfsmanager = $("#resulttyeshow").ligerComboBox({ data: yjfsData, isMultiSelect: false,valueFieldID: 'factResulttyeshow',width:'140'  ,alwayShowInDown:true});           	 
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
   		document.getElementById("beforedepartment").value=$("#factDepartmentshow").val();
   		document.getElementById("beforepostation").value=$("#factPositionshow").val();
   		document.getElementById("beforeyejitype").value=$("#factResulttyeshow").val();
   		try
   		{
   			var queryStringTmp=$('#curRZFrom').serialize();
   			var requestUrl ="bc012/postStaffLZ.action";
   			var responseMethod="postStaffLZMessage";
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