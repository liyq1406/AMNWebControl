    var commoninfodiv;     	 		//第2级明细
    var commoninfodivthirth; 		//第3级明细
    var commondetail;				//主明细	
    var curCommonKey="";			//当前主明细选中资料
    var curCommonValue="";			//当前主明细选中名称
    var curSecondCommonValue="";	//当前二级明细选中名称
    var curSecondCommonKey="";		//当前二级明细选中资料
    var curDivId="";
	var useSourceDate = [{ choose: 0, text: '所有' }, { choose: 1, text: '门店'}, { choose: 2, text: '总部'}];
    var pageState="B"; // B:Browse A:Add M:Modify
       $(function () {   
       	try{
			 commondetail = $("#commondetial").ligerGrid({
                columns: [ 
                {display: '类型代码', name: 'binfotype', align: 'left', width: 60 } ,
                { display: '类型名称', name: 'infoname', align: 'left'  ,width: 120 }
                ],     data:parent.CommonDataGrid, pageSize: 50, sortName: 'binfotype',
                width: '100%', height: '100%',rownumbers:true,
                fixedCellHeight:false,usePager:false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelectData(data, rowindex, rowobj);
                } 
            });
            commoninfodiv=$("#commoninfodivsecond").ligerGrid({
                columns: [
                { display: '类型代码', name: 'binfotype',  width: 80 },
                { display: '类型名称', name: 'infoname',  width: 100, align: 'left' },
                { display: '二级代码', name: 'bparentcodekey',   width: 80 , editor: { type: 'text' } },
                { display: '二级名称', name: 'parentcodevalue', width: 120, align: 'left', editor: { type: 'text' }   },
	            { display: '使用范围', 		name: 'useflag',  width:60,align: 'left' , 
	                editor: { type: 'select', data: useSourceDate, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.useflag == 0) return '所有';
	                    else if (item.useflag == 1) return '门店';
	                    else if (item.useflag == 2) return '总部';
	                }
	            }
                ],  pageSize:10, 
                data: loadCommonDataGridByInfotype(parent.CommonData[0].id.infotype),         
                width: '100%',
                height:'100%',
                enabledEdit: true,  checkbox:true,rownumbers:true,usePager:false,
                onCheckRow: f_onCheckRow,
                onCheckAllRow: f_onCheckAllRow,
                toolbar: { items: [
                { text: '增加', click: itemclick_second, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
                { line: true },
                { text: '保存', click: itemclick_second, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' },
                { line: true },
                { text: '删除', click: itemclick_second, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' },
                 { line: true },
                { text: '刷新', click: itemclick_second, img: contextURL+'/common/ligerui/ligerUI/skins/icons/refresh.gif' }
                ]
                }	          
            });
            commoninfodivthirth=$("#commoninfodivthirth").ligerGrid({
                columns: [
                { display: '类型代码', name: 'binfotype',  width: 80 },
                { display: '类型名称', name: 'infoname',  width: 100, align: 'left' },
                { display: '三级代码', name: 'bcodekey',   width: 80 , editor: { type: 'text' } },
                { display: '三级名称', name: 'codevalue', width: 120, align: 'left', editor: { type: 'text' }   }
                ],  pageSize:10, 
                data: loadCommonDataGridByParentcodekey(parent.CommonData[0].id.infotype,parent.CommonData[0].id.parentcodekey),                  
                width: '100%',
                height:'100%',
                enabledEdit: true,  checkbox:true,rownumbers:true,usePager:false,
                onCheckRow: f_onCheckRow_thirth,
                toolbar: { items: [
                { text: '增加', click: itemclick_thirth, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
                { line: true },
                { text: '保存', click: itemclick_thirth, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' },
                { line: true },
                { text: '删除', click: itemclick_thirth, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' },
                { line: true },
                { text: '刷新', click: itemclick_thirth, img: contextURL+'/common/ligerui/ligerUI/skins/icons/refresh.gif' }
                ]
                }
            });
            curCommonKey=parent.CommonData[0].id.infotype;
            curCommonValue=parent.CommonData[0].infoname;
            curSecondCommonKey=parent.CommonData[0].id.parentcodekey;
   			curSecondCommonValue=parent.CommonData[0].parentcodevalue;		
    
    
            $("#pageloading").hide(); 
            }catch(e){alert(e.message);}
         
        });
        
        function loadSelectData(data, rowindex, rowobj)
        {
           /*commoninfodiv=$("#commoninfodivsecond").ligerGrid({
                columns: [
                { display: '二级代码', name: 'binfotype',  width: 80 },
                { display: '类型名称', name: 'infoname',  width: 100, align: 'left' },
                { display: '二级资料代码', name: 'bparentcodekey',   width: 80 , editor: { type: 'text' } },
                { display: '二级资料名称xx', name: 'parentcodevalue', width: 120, align: 'left', editor: { type: 'text' }   }
                ],  pageSize:10, 
                data:loadCommonDataGridByInfotype(data.binfotype),
                width: '100%',
                height:'600px',
                enabledEdit: true,  checkbox:true,rownumbers:true,usePager:false,
                onCheckRow: f_onCheckRow      
            });*/
            try{
            		checkedBcodekey= [];
            		checkedBcodekey_thirth= [];
            		commoninfodiv.options.data=$.extend(true, {},loadCommonDataGridByInfotype(data.binfotype));
            		commoninfodiv.loadData(true);
            		curCommonKey=data.binfotype;
            		curCommonValue=data.infoname;
            }catch(e){alert(e.message);}
            
        }
        
        function loadCommonDataGridByInfotype(Infotype)
		{
			try
			{
				if(checkNull(Infotype)=="" )
					return null;
				var strJson = "";//'{ "name": "cxh", "sex": "man" }';
				var ccount=0;
				if(parent.CommonparentDataByGroup!=null && parent.CommonparentDataByGroup.length>0)
				{	
					for(var i=0;i<parent.CommonparentDataByGroup.length;i++)
					{
						if(parent.CommonparentDataByGroup[i].binfotype==Infotype)
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
							strJson=strJson+'{"binfotype": "'+parent.CommonparentDataByGroup[i].binfotype+'", "infoname": "'+parent.CommonparentDataByGroup[i].infoname+'", "bparentcodekey":"'+parent.CommonparentDataByGroup[i].bparentcodekey+'", "parentcodevalue": "'+parent.CommonparentDataByGroup[i].parentcodevalue+'" , "useflag": "'+parent.CommonparentDataByGroup[i].useflag+'"  }';
						
							
						}
					}
						if(strJson!="")
					{
						strJson=strJson+']';
						return {Rows:JSON.parse(strJson),Total:2};
					}
					return null;
					 
				}
			
			}catch(e){alert(e.message);}
		}
		
		function loadSelectDataByParentCodeKey(data)
		{
			/*try
			{
		
			 commoninfodivthirth=$("#commoninfodivthirth").ligerGrid({
                columns: [
                { display: '类型代码', name: 'binfotype',  width: 80 },
                { display: '类型名称', name: 'infoname',  width: 100, align: 'left' },
                { display: '三级资料代码', name: 'bcodekey',   width: 80 , editor: { type: 'text' } },
                { display: '三级资料名称', name: 'codevalue', width: 120, align: 'left', editor: { type: 'text' }   }
                ],  pageSize:10, 
                data: loadCommonDataGridByParentcodekey(data.binfotype,data.bparentcodekey),               
                width: '100%',
                height:'600px',
                enabledEdit: true,  checkbox:true,rownumbers:true,usePager:false,
                onCheckRow: f_onCheckRow_thirth		          
            });
            }catch(e){alert(e.message);}*/
            try{
            		commoninfodivthirth.options.data=$.extend(true, {},loadCommonDataGridByParentcodekey(data.binfotype,data.bparentcodekey));
            		commoninfodivthirth.loadData(true);
            		curSecondCommonKey=data.bparentcodekey
   					curSecondCommonValue=data.parentcodevalue	
            }catch(e){alert(e.message);}
		}
		function loadCommonDataGridByParentcodekey(Infotype,Bparentcodekey)
		{
			try
			{
			
				if(checkNull(Infotype)=="" || checkNull(Bparentcodekey)=="")
					return null;
				var strJson = "";//'{ "name": "cxh", "sex": "man" }';
				var ccount=0;
				if(parent.CommonData!=null && parent.CommonData.length>0)
				{	
					
					for(var i=0;i<parent.CommonData.length;i++)
					{
						if(parent.CommonData[i].binfotype==Infotype && parent.CommonData[i].bparentcodekey==Bparentcodekey && checkNull(parent.CommonData[i].bcodekey)!="")
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
							strJson=strJson+'{"binfotype": "'+parent.CommonData[i].binfotype+'", "infoname": "'+parent.CommonData[i].infoname+'", "bcodekey":"'+parent.CommonData[i].bcodekey+'", "codevalue": "'+parent.CommonData[i].codevalue+'"   }';		
						}
					}
					if(strJson!="")
					{
						strJson=strJson+']';
						return {Rows:JSON.parse(strJson),Total:2};
					}
					return null;
					 
				}
			
			}catch(e){alert(e.message);}
		}
        
        function f_onCheckAllRow(checked)
        {
            for (var rowid in commoninfodiv.records)
            {
            	if(checked)
                    addCheckedBcodekey(commoninfodiv.records[rowid]['bparentcodekey']);
                else
                    removeCheckedBcodekey(commoninfodiv.records[rowid]['bparentcodekey']);
            }
        }
        function f_onCheckRow(checked, data)
        {
        	if (checked) 
            {
            	addCheckedBcodekey(data.bparentcodekey);
            	loadSelectDataByParentCodeKey(data);
            }
            else 
            {
            	removeCheckedBcodekey(data.bparentcodekey);
            	commoninfodivthirth.options.data=$.extend(true, {},null);
            	commoninfodivthirth.loadData(false);
            }
        }
        
        
        
        var checkedBcodekey= [];
        
        function findCheckedBcodekey(bcodekey)
        {
            for(var i =0;i<checkedBcodekey.length;i++)
            {
                if(checkedBcodekey[i] == bcodekey) return i;
            }
            return -1;
        }
        
        function addCheckedBcodekey(bcodekey)
        {
            if(findCheckedBcodekey(bcodekey) == -1)
                checkedBcodekey.push(bcodekey);
        }
        function removeCheckedBcodekey(bcodekey)
        {
            var i = findCheckedBcodekey(bcodekey);
            if(i==-1) return;
            checkedBcodekey.splice(i,1);
        }
        
        function f_onCheckRow_thirth(checked, data)
        {
        	if (checked) 
            	addCheckedBcodekey_thirth(data.bcodekey);
            else 
            	removeCheckedBcodekey_thirth(data.bcodekey);
        }
        var checkedBcodekey_thirth= [];
        
        function findCheckedBcodekey_thirth(bcodekey)
        {
            for(var i =0;i<checkedBcodekey_thirth.length;i++)
            {
                if(checkedBcodekey_thirth[i] == bcodekey) return i;
            }
            return -1;
        }
        
        function addCheckedBcodekey_thirth(bcodekey)
        {
            if(findCheckedBcodekey_thirth(bcodekey) == -1)
                checkedBcodekey_thirth.push(bcodekey);
        }
        function removeCheckedBcodekey_thirth(bcodekey)
        {
            var i = findCheckedBcodekey_thirth(bcodekey);
            if(i==-1) return;
            checkedBcodekey_thirth.splice(i,1);
        }
        
        
        
     
        function itemclick_second(item)
        {
        	 // pageState="B"; // B:Browse A:Add M:Modify
        	 if(item.text=="增加")
        	 {
        	 	addRecord("commoninfodivsecond");
        	 }
        	 else if(item.text=="保存")
        	 {
        	 	curDivId="commoninfodivsecond";
        	 	editCurRecord("commoninfodivsecond")
        	 }
        	 else if(item.text=="刷新")
        	 {
        	 	UnifiedCurRecord("commoninfodivsecond")
        	 }  
        	 else if(item.text=="删除")
        	 {
        	 	curDivId="commoninfodivsecond";
        	 	deleteRecord("commoninfodivsecond")
        	 }        	 
        }
        function itemclick_thirth(item)
        {
        	 if(item.text=="增加")
        	 {
        	 	if(curSecondCommonKey=="")
        	 	{
        	 		 $.ligerDialog.warn("请选择需要新增的二级明细!");
        	 		 return ;
        	 	}
        	 	addRecord("commoninfodivthirth");
        	 }
        	 else if(item.text=="保存")
        	 {
        	 	curDivId="commoninfodivthirth";
        	 	editCurRecord("commoninfodivthirth")
        	 }
        	  else if(item.text=="刷新")
        	 {
        	 	UnifiedCurRecord("commoninfodivthirth")
        	 }
        	 else if(item.text=="删除")
        	 {
        	 	curDivId="commoninfodivthirth";
        	 	deleteRecord("commoninfodivthirth")
        	 }
        }
        
        //新增
        function addRecord(objDivId)
        {
        	try
			{
	        	if(parent.hasFunctionRights( "BC002",  "UR_MODIFY")!=true)
	        	{
	        			$.ligerDialog.warn("该用户没有编辑权限,请确认!");
	        	}
	        	else
	        	{
	        			if(objDivId=="commoninfodivsecond")
	        			{
	        				var row = commoninfodiv.getSelectedRow();
				            //参数1:rowdata(非必填)
				            //参数2:插入的位置 Row Data 
				            //参数3:之前或者之后(非必填)
				            commoninfodiv.addRow({ 
				                binfotype: curCommonKey,
				                infoname: curCommonValue,
				                bparentcodekey: "",
				                parentcodevalue: ""               
				            }, row, false);
	        			}
	        			else
	        			{
	        				var row = commoninfodivthirth.getSelectedRow();
				            //参数1:rowdata(非必填)
				            //参数2:插入的位置 Row Data 
				            //参数3:之前或者之后(非必填)
				            commoninfodivthirth.addRow({ 
				                binfotype: curCommonKey,
				                infoname: curCommonValue,
				                bcodekey: "",
				                codevalue: ""		          
				            }, row, false);
				          
	        			}
	        		 	
	        	}
          	}
			catch(e)
			{
				alert(e.message);
			}
        } 
        //保存
        function editCurRecord(objDivId)
        { 
        	if(parent.hasFunctionRights( "BC002",  "UR_POST")!=true)
        	{
        		 $.ligerDialog.warn("该用户没有保存权限,请确认!");
        		 return;
        	}
        	$.ligerDialog.confirm('确认提交保存', function (result)
            {
            	if( result==true)
            	{
            		try
					{
						var jsonParam="";
				        var curjosnparam="";
				        var needReplaceStr="";
					   if(objDivId=="commoninfodivsecond")
					   {
				        	
				        	for (var rowid in commoninfodiv.records)
				            {
				            	if(findCheckedBcodekey(commoninfodiv.records[rowid]['bparentcodekey'])!=-1)
				            	{	
				            		    var row =commoninfodiv.records[rowid];
				            		    curjosnparam=JSON.stringify(row);
				            		   /* if(curjosnparam.indexOf("_id")>-1)
				            		    {
				            		    	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				            		    	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				            		    }	*/            		   
				            		    if(jsonParam!="")
				            		    	jsonParam=jsonParam+",";
				            			jsonParam= jsonParam+curjosnparam;
				            	}            		 
				            }
				        	if(jsonParam=="")
				        	{
				        		 $.ligerDialog.warn("请选择需要更新的项目!");
				        		 return;
				        	}
						}
						else
						{
							for (var rowid in commoninfodivthirth.records)
				            {
				            	if(findCheckedBcodekey_thirth(commoninfodivthirth.records[rowid]['bcodekey'])!=-1)
				            	{	
				            		    var row =commoninfodivthirth.records[rowid];
				            		    curjosnparam=JSON.stringify(row);
				            		    /*if(curjosnparam.indexOf("_id")>-1)
				            		    {
				            		    	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				            		    	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				            		    }*/	            		   
				            		    if(jsonParam!="")
				            		    	jsonParam=jsonParam+",";
				            			jsonParam= jsonParam+curjosnparam;
				            	}            		 
				            }
				        	if(jsonParam=="")
				        	{
				        		 $.ligerDialog.warn("请选择需要更新的项目!");
				        		 return;
				        	}
						}
						var requestUrl ="bc002/post.action";
						var params="strJsonParam=["+jsonParam+"]";
						params=params+"&curDivid="+objDivId;
						params=params+"&strInfotype="+curCommonKey;
						params=params+"&strInfoname="+curCommonValue;
						params=params+"&strParentcodekey="+curSecondCommonKey;
						params=params+"&strParentcodevalue="+curSecondCommonValue;
						var responseMethod="postMessage";				
						sendRequestForParams_p(requestUrl,responseMethod,params ); 
					}
					catch(e)
					{
						alert(e.message);
					}
            	}
            });     	
            
        }
        
        function postMessage(request)
        {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		 curSecondCommonKey="";
	        		 curSecondCommonValue="";
	        		 if(curDivId=="commoninfodivthirth")
	        		 {
	        			 parent.CommonData=responsetext.lsCommonData;   
	        			 
	        		 }   	
	        		 else
	        		 {
	        	
	        		 	parent.CommonparentDataByGroup=responsetext.lsCommonDataByGroup;  
	        		 }	
	        		  $.ligerDialog.success("更新成功!");
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
        
        //根据数据库同步
        function UnifiedCurRecord(objDivId)
        {
        	try{
		        	if(objDivId=="commoninfodivsecond")
			        {	        		 
	            		commoninfodiv.options.data=$.extend(true, {},loadCommonDataGridByInfotype(curCommonKey));
	            		commoninfodiv.loadData(true);       
        			}
        			else
        			{
        				commoninfodivthirth.options.data=$.extend(true, {},loadCommonDataGridByParentcodekey(curCommonKey,curSecondCommonKey));
            			commoninfodivthirth.loadData(true);
        			}
        	  }catch(e){alert(e.message);}
        }
        //删除行
        function deleteRecord(objDivId)
        {
        	if(parent.hasFunctionRights( "BC002",  "UR_DELETE")!=true)
        	{
        		 $.ligerDialog.warn("该用户没有删除权限,请确认!");
        		 return;
        	}
        	$.ligerDialog.confirm('确认提交删除', function (result)
            {
            	if( result==true)
            	{
            		try
					{
						var jsonParam="";
				        var curjosnparam="";
				        var needReplaceStr="";
					   if(objDivId=="commoninfodivsecond")
					   {
				        	
				        	for (var rowid in commoninfodiv.records)
				            {
				            	if(findCheckedBcodekey(commoninfodiv.records[rowid]['bparentcodekey'])!=-1)
				            	{	
				            		    var row =commoninfodiv.records[rowid];
				            		    curjosnparam=JSON.stringify(row);
				            		   /* if(curjosnparam.indexOf("_id")>-1)
				            		    {
				            		    	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				            		    	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				            		    }	       */     		   
				            		    if(jsonParam!="")
				            		    	jsonParam=jsonParam+",";
				            			jsonParam= jsonParam+curjosnparam;
				            	}            		 
				            }
				        	if(jsonParam=="")
				        	{
				        		 $.ligerDialog.warn("请选择需要更新的项目!");
				        		 return;
				        	}
						}
						else
						{
							for (var rowid in commoninfodivthirth.records)
				            {
				            	if(findCheckedBcodekey_thirth(commoninfodivthirth.records[rowid]['bcodekey'])!=-1)
				            	{	
				            		    var row =commoninfodivthirth.records[rowid];
				            		    curjosnparam=JSON.stringify(row);
				            		   /* if(curjosnparam.indexOf("_id")>-1)
				            		    {
				            		    	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				            		    	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				            		    }	*/            		   
				            		    if(jsonParam!="")
				            		    	jsonParam=jsonParam+",";
				            			jsonParam= jsonParam+curjosnparam;
				            	}            		 
				            }
				        	if(jsonParam=="")
				        	{
				        		 $.ligerDialog.warn("请选择需要更新的项目!");
				        		 return;
				        	}
						}
						var requestUrl ="bc002/delete.action";
						var params="strJsonParam=["+jsonParam+"]";
						params=params+"&curDivid="+objDivId;
						params=params+"&strInfotype="+curCommonKey;
						params=params+"&strInfoname="+curCommonValue;
						params=params+"&strParentcodekey="+curSecondCommonKey;
						params=params+"&strParentcodevalue="+curSecondCommonValue;
						var responseMethod="deleteMessage";				
						sendRequestForParams_p(requestUrl,responseMethod,params ); 
					}
					catch(e)
					{
						alert(e.message);
					}
            	}
            });     	
        }
        
         function deleteMessage(request)
        {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	       		 
	        		 if(curDivId=="commoninfodivthirth")
	        		 {
	        			 parent.CommonData=responsetext.lsCommonData;   
	        			 commoninfodivthirth.options.data=$.extend(true, {},loadCommonDataGridByParentcodekey(curCommonKey,curSecondCommonKey));
            			 commoninfodivthirth.loadData(true);
	        		 }   	
	        		 else
	        		 {
	        		 	parent.CommonparentDataByGroup=responsetext.lsCommonDataByGroup;  
	        		 	commoninfodiv.options.data=$.extend(true, {},loadCommonDataGridByInfotype(curCommonKey));
	            		commoninfodiv.loadData(true);  
	            		curSecondCommonKey="";
	        		 	curSecondCommonValue=""; 
	        		 }	
	        		  $.ligerDialog.success("删除成功!");
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
        