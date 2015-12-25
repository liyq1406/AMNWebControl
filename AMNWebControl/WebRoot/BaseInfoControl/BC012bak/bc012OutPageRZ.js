	var bmzlmanager=null;
	var gwgzmanager=null;
	var yhklmanager=null;
	var yjfsmanager=null;

	$(function ()
   	{
   		try
   		{
	   	
		     $("#healthdate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'140' });
		     $("#birthdate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
		     $("#apparrivaldate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
		     parent.comfirmButton=$("#comfirmButton").ligerButton(
		     {
		             text: '提交入职申请', width: 160,
			         click: function ()
			         {
			             comfirmbill();
			         }
		     });
		    // var  bmzlData=JSON.parse(parent.parent.loadCommonControlDate("BMZL",0));
           	// bmzlmanager = $("#departmentshow").ligerComboBox({ data: bmzlData, isMultiSelect: false,valueFieldID: 'factDepartmentshow',width:'120' ,alwayShowInDown:true });           	 
           	// var  gwgzData=JSON.parse(parent.parent.loadCommonControlDate("GZGW",0));
           	// gwgzmanager = $("#positionshow").ligerComboBox({ data: gwgzData, isMultiSelect: false,valueFieldID: 'factPositionshow',width:'140'  ,alwayShowInDown:true});           	 
           	 
            addOption("","",document.getElementById("department"));
            addOption("","",document.getElementById("position"));
           	var bmzlData=parent.parent.gainCommonInfoByCode("BMZL",0);
		    for(var i=0;i<bmzlData.length;i++)
			{
				addOption(bmzlData[i].bparentcodekey,bmzlData[i].parentcodevalue,document.getElementById("department"));
			}		
            var gwgzData=parent.parent.gainCommonInfoByCode("GZGW",0);
		    for(var i=0;i<gwgzData.length;i++)
			{
				addOption(gwgzData[i].bparentcodekey,gwgzData[i].parentcodevalue,document.getElementById("position"));
			}	
           
           	 var  yhklData=JSON.parse(parent.parent.loadCommonControlDate("YHKL",0));
           	 yhklmanager = $("#banktypeshow").ligerComboBox({ data: yhklData, isMultiSelect: false,valueFieldID: 'factYanktypeshow',width:'120'  ,alwayShowInDown:true});           	 
           	 var  yjfsData=JSON.parse(parent.parent.loadCommonControlDate("YJFS",0));
             yjfsmanager = $("#resulttyeshow").ligerComboBox({ data: yjfsData, isMultiSelect: false,valueFieldID: 'factResulttyeshow',width:'120'  ,alwayShowInDown:true});           	 
    
           	 //document.getElementById("compno").value=checkNull(parent.curCompid);
           	  document.getElementById("compno").value=checkNull(parent.curRecord.id.compno);
	     }catch(e){alert(e.message);}
   	});
   	
   	function comfirmbill()
   	{
   		if(document.getElementById("staffno").value=="")
   		{
   			$.ligerDialog.warn("员工工号不能为空!");
   			document.getElementById("staffno").select();
   			document.getElementById("staffno").fouse();
   			return;
   		}
   		if(document.getElementById("staffname").value=="")
   		{
   			$.ligerDialog.warn("员工姓名不能为空!");
   			document.getElementById("staffname").select();
   			document.getElementById("staffno").fouse();
   			return;
   		}
   		if(document.getElementById("reservecontect").value=="")
   		{
   			$.ligerDialog.warn("紧急联系人不能为空!");
   			document.getElementById("reservecontect").select();
   			document.getElementById("staffno").fouse();
   			return;
   		}
   		if(document.getElementById("reservephone").value=="")
   		{
   			$.ligerDialog.warn("紧急联系电话不能为空!");
   			document.getElementById("reservephone").select();
   			document.getElementById("staffno").fouse();
   			return;
   		}
   		if(document.getElementById("pccid").value=="")
   		{
   			$.ligerDialog.warn("身份证号不能为空!");
   			document.getElementById("pccid").select();
   			document.getElementById("staffno").fouse();
   			return;
   		}
   		if(document.getElementById("reserveaddress").value=="")
   		{
   			$.ligerDialog.warn("紧急联系地址不能为空!");
   			document.getElementById("reserveaddress").select();
   			document.getElementById("staffno").fouse();
   			return;
   		}
   		if(document.getElementById("department").value=="")
   		{
   			$.ligerDialog.warn("员工部门不能为空!");
   			return;
   		}
   		if(document.getElementById("position").value=="")
   		{
   			$.ligerDialog.warn("员工职位不能为空!");
   				return;
   		}
   		if(document.getElementById("apparrivaldate").value=="")
   		{
   			$.ligerDialog.warn("生效日期不能为空!");
   			document.getElementById("apparrivaldate").select();
   			document.getElementById("apparrivaldate").fouse();
   			return;
   		}
   		//document.getElementById("department").value=$("#factDepartmentshow").val();
   		//document.getElementById("position").value=$("#factPositionshow").val();
   		document.getElementById("banktype").value=$("#factYanktypeshow").val();
   		document.getElementById("resulttye").value=$("#factResulttyeshow").val();
   		try
   		{
   			var queryStringTmp=$('#curRZFrom').serialize();
   			var requestUrl ="bc012/postStaffRZ.action";
   			var responseMethod="postStaffRZMessage";
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