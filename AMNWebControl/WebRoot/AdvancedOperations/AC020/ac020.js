
   	var compTree = null;//主明细	
   	var compTreeManager = null;//主明细	
   	var commoninfodivsecond=null;//排班类别
   	var commoninfodivscheduldetial=null;//排班类别明细
   	var commoninfodivstaffinfodetial=null;
   	var curCompid="";			// 当前门店
	var postationid="";			//职位编号
	var categoryno="";			//类别编号
   	var staffPostionData=JSON.parse(parent.loadCommonControlDate_select("GZGW",0));
   	var ac020layout=null;
   	var newStoreflowinfo=null;
	var curdialog=null;	

    $(function ()
   	{
	   try
	   {
	   		  //布局
            ac020layout= $("#ac020layout").ligerLayout({ leftWidth: 270,rightWidth: 285,  allowBottomResize: false, allowLeftResize: false ,isLeftCollapse:true});
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
         	confirmClosing = $.ligerMenu({ width: 120, items:
            [
            { text: '移除排班', click: deleteCategoryinfoid, icon: 'add' },
            ]
            });  
          	commoninfodivsecond=$("#commoninfodivsecond").ligerGrid({
                columns: [
                { display: '门店编号', 				name: 'compno', 		width:100,align: 'left' },
              	{ display: '类别编号', 				name: 'categoryno', 		width:100,align: 'left' },
	            { display: '类别名称', 				name: 'categoryname', 		width:100,align: 'left' },
	            { display: '类别备注', 				name: 'categorymark', 	width:100,align: 'left'}
                ], 
                pageSize:20, 
                data:null,      
                width: '400',
                height:'740',
                clickToEdit: false,   enabledEdit: false, 
                usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    curCompid = data.compno;
                	categoryno = data.categoryno;
                	postationid = "";
                	loadCategoryinfoid(data, rowindex, rowobj);
                }
            });
        	$("#schedulingToolBar").ligerToolBar({ items: [
                { text: '添加排班类别', click: addDetailInfo},
                { text: '删除排班类别', click: deleteDetailInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' },
                { line: true },
               	{ text: '移除排班', click: deleteCategoryinfoid, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' },
               
               	{ line: true },
               	{ text: '添加排班职位', click: addPostation, img: contextURL+'/common/ligerui/ligerUI/skins/icons/ok.gif' },
               	{ text: '下发', click: downDetailInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/logout.gif' }
            ]
            });
            commoninfodivscheduldetial=$("#commoninfodivscheduldetial").ligerGrid({
                columns: [
                { display: '门店编号', 				name: 'compno', 		width:100,align: 'left' },
              	{ display: '类别编号', 				name: 'categoryno', 		width:100,align: 'left' },
	            { display: '职位编号', 				name: 'postationid', 		width:100,align: 'left' },
	            { display: '职位名称', 				name: 'postationname', 		width:100,align: 'left' }
                ],  pageSize:20, 
                data:null,      
                width: '400',
                height:'740',
                clickToEdit: true,   enabledEdit: true,usePager: false,	 
                onSelectRow : function (data, rowindex, rowobj)
                {
                    curCompid = data.compno;
                	categoryno = data.categoryno;
                	postationid = data.postationid;
                },
             	onContextmenu : function (parm,e)
                {
                	 curCompid = parm.data.compno;
                	 categoryno = parm.data.categoryno;
                	 postationid = parm.data.postationid;
                     confirmClosing.show({ top: e.pageY, left: e.pageX });
                     return false;
                } 
            });
            
            
            commoninfodivstaffinfodetial=$("#commoninfodivstaffinfodetial").ligerGrid({
                columns: [
	            { display: '职位编号', 	name: 'postationid', 		width:100,align: 	'left'}  ,
	            { display: '职位名称', 	name: 'postationname', 		width:100,align: 	'left'}  
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '260',
                height:'740',checkbox:true,
                clickToEdit: false,   enabledEdit: false, usePager: false,
                onCheckRow:f_onCheckRow,
                onCheckAllRow: f_onCheckAllRow
            });
          	$("#pageloading").hide(); 
          	//加载工作职位
          	loadPostation();
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
        		loadCategoryinfo();
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
				             position:'',
				             staffno:'',
				             staffname:''
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
    
     //选择列表
     function f_onCheckAllRow(checked)
     {
       	for (var rowid in commoninfodivstaffinfodetial.records)
       	{
           	if(checked)
            	addCheckedBfunctionno(commoninfodivstaffinfodetial.records[rowid]['postationid']);
            else
                removeCheckedBfunctionno(commoninfodivstaffinfodetial.records[rowid]['postationid']);
       	}
     }
   function f_onCheckRow(checked, data)
   {
       	if (checked) 
           	addCheckedBfunctionno(data.postationid);
           else 
           	removeCheckedBfunctionno(data.postationid);
   }
   
   var checkedBfunctionno= [];
   function findCheckedBfunctionno(postationid)
    {
         for(var i =0;i<checkedBfunctionno.length;i++)
         {
         	if(checkedBfunctionno[i] == postationid) return i;
         }
         return -1;
     }
             
     function addCheckedBfunctionno(postationid)
     {
         if(findCheckedBfunctionno(postationid) == -1)
             checkedBfunctionno.push(postationid);
     }
     function removeCheckedBfunctionno(postationid)
     {
         var i = findCheckedBfunctionno(postationid);
         if(i==-1) return;
     	 checkedBfunctionno.splice(i,1);
     }
    
    //添加排班类别
    function addDetailInfo(item)
    {
     	curdialog=$.ligerDialog.open({ height: '350', url: contextURL+'/AdvancedOperations/AC020/categoryinfo.jsp', width: '300', showMax: true, showToggle: true, showMin: false,  title: '添加排班类别' });
    	curdialog.collapse();
    }
    //添加排班类别
  	function postCategoryinfoMessage(request)
	{
		var responsetext = eval("(" + request.responseText + ")");
        var strMessage=responsetext.strMessage;
        if(checkNull(strMessage)=="")
        {	        		 
        	 $.ligerDialog.success("添加成功!");
        	 curdialog.close();
        	 loadCategoryinfo(curCompid);
        }
        else
        {
        	$.ligerDialog.error(strMessage);
        }

	}
	//删除排班类别
	function deleteDetailInfo()
	{
   		if(categoryno=="")
   		{
   			$.ligerDialog.warn("请选择要删除的类别!");
   			return;
   		}
	   	try
   		{
      	var params="strCurCompId="+curCompid;
      		params =params+ "&categoryno="+categoryno;

      		var requestUrl ="ac020/deleteCategoryinfo.action"; 
			var responseMethod="deleteCategoryinfoMessage";	
			sendRequestForParams_p(requestUrl,responseMethod,params);
		}catch(e){alert(e.message)}  
	}

  	function deleteCategoryinfoMessage(request)
	{
		var responsetext = eval("(" + request.responseText + ")");
        var strMessage=responsetext.strMessage;
        if(checkNull(strMessage)=="")
        {	        		 
        	 $.ligerDialog.success("删除成功!");
        	 loadCategoryinfo(curCompid);
        }
        else
        {
        	$.ligerDialog.error(strMessage);
        }
	} 
    //加载门店类别信息
	function loadCategoryinfo()
	{
	    	try
	    		{
	    			var requestUrl =contextURL+"/ac020/loadCategoryinfo.action?";
        			var params="strCurCompId="+curCompid;
					 $.ajax({
						type: 'POST',
						url: requestUrl+params,
						data: null,
						dataType: 'json',			
						success: function (data) { 
	       				var datastr ='';
						if(data.lsCategoryinfo!=null && data.lsCategoryinfo.length>0)
						{
							for(var i=0;i<data.lsCategoryinfo.length;i++)
							{
								if(datastr!='')
								{
									datastr=datastr+','
								}
							 datastr=datastr+'{"compno":"'+data.lsCategoryinfo[i].compno+'","categoryno":"'+data.lsCategoryinfo[i].categoryno+'","categoryname":"'+data.lsCategoryinfo[i].categoryname+'",' +
								'"categorymark":"'+data.lsCategoryinfo[i].categorymark+'"}';
							}
							
							if(datastr!='')
							{
								datastr='['+datastr+']';
							}
						    commoninfodivsecond.options.data=$.extend(true, {},{Rows:JSON.parse(datastr),Total: data.lsCategoryinfo.length});
						    commoninfodivsecond.loadData(true);
						}
						else
						{ 
						    commoninfodivsecond.options.data=$.extend(true, {},{Rows:null,Total:0});
						    commoninfodivsecond.loadData(true);	
						}
				}
			});
		}catch(e){alert(e.message)}     	   
	}
	
	//加载门店类别与职位的绑定信息
	function loadCategoryinfoid()
	{
	    	try
	    		{
	    			var requestUrl =contextURL+"/ac020/loadCategoryinfoid.action?";
        			var params="strCurCompId="+curCompid;
        				params =params+ "&categoryno="+categoryno;
					 $.ajax({
						type: 'POST',
						url: requestUrl+params,
						data: null,
						dataType: 'json',			
						success: function (data) { 
	       				var datastr ='';
						if(data.lsCategoryinfoid!=null && data.lsCategoryinfoid.length>0)
						{
							for(var i=0;i<data.lsCategoryinfoid.length;i++)
							{
								if(datastr!='')
								{
									datastr=datastr+','
								}
							 datastr=datastr+'{"compno":"'+data.lsCategoryinfoid[i].compno+'","categoryno":"'+data.lsCategoryinfoid[i].categoryno+'","postationid":"'+data.lsCategoryinfoid[i].postationid+'",' +
								'"postationname":"'+data.lsCategoryinfoid[i].postationname+'"}';
							}
							
							if(datastr!='')
							{
								datastr='['+datastr+']';
							}
						    commoninfodivscheduldetial.options.data=$.extend(true, {},{Rows:JSON.parse(datastr),Total: data.lsCategoryinfoid.length});
						    commoninfodivscheduldetial.loadData(true);
						}
						else
						{ 
						    commoninfodivscheduldetial.options.data=$.extend(true, {},{Rows:null,Total:0});
						    commoninfodivscheduldetial.loadData(true);	
						}
				}
			});
		}catch(e){alert(e.message)}     	   
	}

	//移除排班类别与职位的关系
	function deleteCategoryinfoid()
	{
			if(curCompid=="" || postationid=="" || categoryno=="")
   			{
	   			$.ligerDialog.warn("请选择要移除的职位!");
	   			return;
   			}
			try
	    		{
   				var requestUrl =contextURL+"/ac020/deleteCategoryinfoid.action";
      			var params="strCurCompId="+curCompid;
      				params =params+ "&categoryno="+categoryno;
      				params =params+ "&postationid="+postationid;
      				
				var responseMethod="deleteCategoryinfoidMessage";	
				sendRequestForParams_p(requestUrl,responseMethod,params);
		}catch(e){alert(e.message)}    
	}
	function deleteCategoryinfoidMessage(request)
	{
		var responsetext = eval("(" + request.responseText + ")");
        var strMessage=responsetext.strMessage;
        if(checkNull(strMessage)=="")
        {	        		 
        	 $.ligerDialog.success("移除成功!");
        	 loadCategoryinfoid(curCompid,categoryno);
        }
        else
        {
        	$.ligerDialog.error(strMessage);
        }
	} 
	
	//加载工作职位
	function loadPostation()
	{
	    	try
	    		{
	    			var requestUrl =contextURL+"/ac020/loadPostation.action?";
					 $.ajax({
						type: 'POST',
						url: requestUrl,
						data: null,
						dataType: 'json',			
						success: function (data) { 
	       				var datastr ='';
						if(data.lsCategoryinfoid!=null && data.lsCategoryinfoid.length>0)
						{
							for(var i=0;i<data.lsCategoryinfoid.length;i++)
							{
								if(datastr!='')
								{
									datastr=datastr+','
								}
							 	datastr=datastr+'{"postationid":"'+data.lsCategoryinfoid[i].postationid+'",'+'"postationname":"'+data.lsCategoryinfoid[i].postationname+'"}';
							}
							
							if(datastr!='')
							{
								datastr='['+datastr+']';
							}
						    commoninfodivstaffinfodetial.options.data=$.extend(true, {},{Rows:JSON.parse(datastr),Total: data.lsCategoryinfoid.length});
						    commoninfodivstaffinfodetial.loadData(true);
						}
						else
						{ 
						    commoninfodivstaffinfodetial.options.data=$.extend(true, {},{Rows:null,Total:0});
						    commoninfodivstaffinfodetial.loadData(true);	
						}
				}
			});
		}catch(e){alert(e.message)}     	   
	}
	//选择工作职位
	function addPostation()
	{   	
	    if(categoryno=="" || curCompid =="")
        {
        	$.ligerDialog.error("请选择类别!");
        	return;
        }
     	if(checkedBfunctionno.length==0)
     	{
     		$.ligerDialog.error("请选择排班职位!");
        	return;
     	}
     	var postations="";
	    var params="strCurCompId="+curCompid;
	     	params =params+ "&categoryno="+categoryno;
	     	params =params+	"&postations="+checkedBfunctionno;
	
	     	var requestUrl ="ac020/addPostation.action"; 
			var responseMethod="addPostationMessage";	
			sendRequestForParams_p(requestUrl,responseMethod,params);

	}
	function addPostationMessage(request)
	{
		var responsetext = eval("(" + request.responseText + ")");
        var strMessage=responsetext.strMessage;
        if(checkNull(strMessage)=="")
        {	        		 
        	 $.ligerDialog.success("添加成功!");
        	 loadCategoryinfoid(curCompid,categoryno);
        }
        else
        {
        	$.ligerDialog.error(strMessage);
        }
	}
	
	//下发到门店
	function downDetailInfo()
	{

		if(categoryno=="" || curCompid =="")
        {
        	$.ligerDialog.error("请选择要下发的类别!");
        	return;
        }
        $.ligerDialog.confirm('确认将'+curCompid+'门店,'+categoryno+"类别信息下发到各门店?", function (result)
         {
	    	if(result==true)
	    	{
	    	
	    	var requestUrl ="ac020/downDetailInfo.action";
       		var params="strCurCompId="+curCompid;
       		params =params+ "&categoryno="+categoryno;
       		var responseMethod="downDetailInfoMessage";
       		sendRequestForParams_p(requestUrl,responseMethod,params ); 
	    	}
	    }); 

	}
	
	function downDetailInfoMessage(request)
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
	
	    
  
	
	
	
	