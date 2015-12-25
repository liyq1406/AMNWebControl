var showDialogmanager = null;
	$(function ()
   	{
   		try
   		{
		     parent.comfirmButton=$("#comfirmButton").ligerButton(
		     {
		             text: '提交排班类别', width: 160,
			         click: function ()
			         {
			             comfirmbill();
			         }
		     });
		      document.getElementById("compno").value=checkNull(parent.curCompid);
           	  parent.curdialog.extend();
	     }catch(e){alert(e.message);}
   	});
   	//添加类别信息
   	function comfirmbill()
   	{
   		if(document.getElementById("compno").value=="")
   		{
   			$.ligerDialog.warn("门店编号不能为空!");
   			document.getElementById("compno").select();
   			document.getElementById("compno").fouse();
   			return;
   		}
   		if(document.getElementById("categoryno").value=="")
   		{
   			$.ligerDialog.warn("类别编号不能为空!");
   			document.getElementById("categoryno").select();
   			document.getElementById("categoryno").fouse();
   			return;
   		}
   		if(document.getElementById("categoryname").value=="")
   		{
   			$.ligerDialog.warn("类别名称不能为空!");
   			document.getElementById("categoryname").select();
   			document.getElementById("categoryname").fouse();
   			return;
   		}
   		try
   		{
      	var params="categoryinfo.compno="+document.getElementById("compno").value;
      		params =params+ "&categoryinfo.categoryno="+document.getElementById("categoryno").value;
      		params =params+ "&categoryinfo.categoryname="+document.getElementById("categoryname").value;
      		params =params+ "&categoryinfo.categorymark="+document.getElementById("categorymark").value;
      		var requestUrl ="ac020/postCategoryinfo.action"; 
			var responseMethod="postCategoryinfoMessage";	
			parent.sendRequestForParams_p(requestUrl,responseMethod,params);
		}catch(e){alert(e.message)}  
    }

    
 
