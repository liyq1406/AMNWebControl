var commondetail;				//主明细	
var commoninfodivsecond;     	//第2级明细
var commoninfodivthirth; 		//第3级明细
var commoninfodivforth; 		//第4级明细
var curRoleIdKey="";				//当前主明细
var curMozuIdKey;				//当前主明细
var commoninfodivpostion;
var loadMoZuState=0;
var f_detailPanel	=null;
var f_callback=null;
var chooseData = [{ choose: 'Y', text: '启' }, { choose: 'N', text: '禁'}];
var chooseMozuData = null;
var chooseMokuaiData =null;
var bc003layout=null;
var addposition="";
var addpositionname="";
var addBandDialog=null;
var strCurPostion="";
  $(function () {   
       	try{
			bc003layout= $("#bc003layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
            commoninfodivsecond=$("#commoninfodivsecond").ligerGrid({
                columns: [
                { display: '模组编号', name: 'strMoZuId',  width: 70 },
                { display: '模组名称', name: 'strMoZuName',  width: 90 }
                ],  pageSize:10, 
                data:loadCommonDataGridByInfotype(),      
                width: '100%',
                height:'30%',
                enabledEdit: false,  
                //checkbox:true,
                rownumbers:false,usePager:false,
                isSelected: mozu_isChecked ,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecMozutData(data, rowindex, rowobj);
                } 
            });
            
             commoninfodivpostion=$("#commoninfodivpostion").ligerGrid({
                columns: [
                { display: '职位编号', name: 'position',  width: 70 },
                { display: '职位名称', name: 'positionname',  width: 90 }
                ],  pageSize:10, 
                data:null,      
                width: '100%',
                height:'30%',
                enabledEdit: false,  
                //checkbox:true,
                rownumbers:false,usePager:false,
                toolbar: { items: [
                  { text: '绑定职位', click: addRolePosition, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' }
                 ]},
                onContextmenu : function (parm,e)
                {
                	 menu.show({ top: e.pageY, left: e.pageX });
                     return false;
                } ,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    strCurPostion=data.position;
                } 
            });
            
             menu = $.ligerMenu({ width: 160, items:
	            [
	            	{ text: '取消职位绑定', click: cancleBandInfo, icon: 'delete' , img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif'}
	            ]
	            });
	            
            commoninfodivthirth=$("#commoninfodivthirth").ligerGrid({
                columns: [
                { display: '模块编号', name: 'bfunctionno',  width: 57,align: 'left' },
                { display: '模块名称', name: 'bfunctioname',  width: 90 ,align: 'left' },
                { display: '查看', name: 'browsepurviewflag',  width: 50 , 
                	editor: { type: 'select', data: chooseData, valueField: 'choose' },render: checkboxRender
                   /* render: function (item)
                    {
                        if (item.browsepurview == 'Y') return '启';
                        return '禁';
                    }*/
                },
                { display: '编辑', name: 'editpurviewflag',  width: 50 , 
                	editor: { type: 'select', data: chooseData, valueField: 'choose' },render: checkboxRender
                    /*render: function (item)
                    {
                        if (item.editpurview == 'Y') return '启';
                        return '禁';
                    }*/
                },
                { display: '导出', name: 'exportpurviewflag',  width: 50 , 
                	editor: { type: 'select', data: chooseData, valueField: 'choose' },render: checkboxRender
                   /* render: function (item)
                    {
                        if (item.exportpurview == 'Y') return '启';
                        return '禁';
                    }*/
                },
                { display: '保存', name: 'postpurviewflag',  width: 50, 
                	editor: { type: 'select', data: chooseData, valueField: 'choose' },render: checkboxRender
                   /* render: function (item)
                    {
                        if (item.postpurview == 'Y') return '启';
                        return '禁';
                    }*/
                },
                { display: '审核', name: 'confirmpurviewflag',  width: 50 , 
                	editor: { type: 'select', data: chooseData, valueField: 'choose' },render: checkboxRender
                   /* render: function (item)
                    {
                        if (item.confirmpurview == 'Y') return '启';
                        return '禁';
                    }*/
                },
                { display: '作废', name: 'invalidpurviewflag',  width: 50 , 
                	editor: { type: 'select', data: chooseData, valueField: 'choose' },render: checkboxRender
                    /*render: function (item)
                    {
                        if (item.invalidpurview == 'Y') return '启';
                        return '禁';
                    }*/
                }
                ],  pageSize:10, 
                data:null,         
                width: '100%',
                height:'99%',
                isChecked: mokuai_isChecked ,
                onCheckRow: f_onCheckRow_thirth,
                onCheckAllRow: f_onCheckAllRow_thirth,
                enabledEdit: true,  checkbox:true,rownumbers:false,usePager:false
            });
            commoninfodivforth=$("#commoninfodivforth").ligerGrid({
                columns: [
                { display: '特殊权限', name: 'purviewname',  width: 130 }
                ],  pageSize:10, 
                data: loadCurpurviewno(),        
                width: '100%',
                height:'99%',
                onCheckRow: f_onCheckRow_forth,
                onCheckAllRow: f_onCheckAllRow_forth,
                isChecked: mokuai_isChecked_forth ,
                enabledEdit: true,  checkbox:true,rownumbers:false,usePager:false
            });
            //加载主明细
            loadCommondetial();
            addThirthDetialRecord();
            addRosPostionRecord();
            $("#pageloading").hide(); 
            }catch(e){alert(e.message);}
         
        });
  
  	      
        //是否类型的模拟复选框的渲染函数
	function checkboxRender(rowdata, rowindex, value, column)
	{
	    var iconHtml = '<div class="chk-icon';
	    if (value) iconHtml += " chk-icon-selected";
	    iconHtml += '"';
	    iconHtml += ' rowid = "' + rowdata['__id'] + '"';
	    iconHtml += ' gridid = "' + this.id + '"';
	    iconHtml += ' columnname = "' + column.name + '"';
	    iconHtml += '></div>';
	    return iconHtml;
	}
	//是否类型的模拟复选框的点击事件
	$("div.chk-icon").live('click', function ()
	{
	    var grid = $.ligerui.get($(this).attr("gridid"));
	    var rowdata = grid.getRow($(this).attr("rowid"));
	    var columnname = $(this).attr("columnname");
	    var checked = rowdata[columnname];
	     
	    grid.updateCell(columnname, !checked, rowdata);
	});
	        
        
        function f_onCheckAllRow_thirth(checked)
        {
            for (var rowid in commoninfodivthirth.records)
            {
                if(checked)
                    addCheckedBfunctionno_thirth(commoninfodivthirth.records[rowid]['bfunctionno']);
                else
                    removeCheckedBfunctionno_thirth(commoninfodivthirth.records[rowid]['bfunctionno']);
            }
        }
        
        function f_onCheckAllRow_forth(checked)
        {
        	for (var rowid in commoninfodivforth.records)
            {
            	if(checked)
                    addCheckedBfunctionno_forth(commoninfodivforth.records[rowid]['bcurpurviewno']);
                else
                    removeCheckedBfunctionno_forth(commoninfodivforth.records[rowid]['bcurpurviewno']);
            }
        }
        
        function mozu_isChecked(rowdata)
        {
            if (checkNull(rowdata.strMoZuId)=="BC") 
            {
           
                return true;
            }
            return false;
        }
        //初始加载角色信息
        function loadCommondetial()
        {
        	var requestUrl ="bc003/loadCurRolsInfo.action"; 
			var responseMethod="loadCurRolsInfoMessage";				
			sendRequestForParams_p(requestUrl,responseMethod,"" );
        }
        
        function loadCurRolsInfoMessage(request)
	    {
	   		var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.roleInfos!=null && responsetext.roleInfos.length>0)
		     commondetail = $("#commondetial").ligerGrid({
             columns: [ 
                {display: '角色编号', name: 'strRoleId', align: 'center', width: 85 } ,
                { display: '角色名称', name: 'strRoleName', align: 'center'  ,width: 145 },
                { display: '总用户数', name: 'roleUserNum', align: 'center' ,width: 60 }               
                ],     data:{Rows: responsetext.roleInfos,Total: responsetext.roleInfos.length}, pageSize: 50, sortName: 'binfotype',
                width: '360', height: '99%',rownumbers:false,
                fixedCellHeight:false,usePager:false,
                detail: { onShowDetail: showSysuserinfo},
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelectData(data, rowindex, rowobj);
                } ,
                toolbar: { items: [
                 { text: '保存当前角色权限', click: itemclick_function, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' }
                 ]}
            });
		
	        	
	   	}
        //根据角色信息加载模组信息
        function loadSelectData(data, rowindex, rowobj)
        {
          
            try{
            		checkedBfunctionno_thirth= [];
            		checkedBfunctionno_forth=[];
            		
            	    loadMoZuState=1;
            		curRoleIdKey=data.strRoleId;
            		var requestUrl ="bc003/loadRolsMoZuInfo.action"; 
					var responseMethod="loadRolsmozuInfoMessage";		
					var param="strCurRoleId="+curRoleIdKey;		
					sendRequestForParams_p(requestUrl,responseMethod,param );
		
            }catch(e){alert(e.message);}
            
        }
        
        function loadRolsmozuInfoMessage(request)
        {
       		try
        	{
 
        	var responsetext = eval("(" + request.responseText + ")");
    
	   		if(responsetext.roleMozuInfos!=null && responsetext.roleMozuInfos.length>0)
	   		{
	   			commoninfodivsecond.options.data=$.extend(true, {},loadMozuInfoByRole(responsetext.roleMozuInfos));
            	commoninfodivsecond.loadData(true);
            	commoninfodivsecond.select(0);   
            	loadMoZuState=2;
	   		}

	   		if(responsetext.roleSysmodepurviews!=null && responsetext.roleSysmodepurviews.length>0)
	   		{
	   			commoninfodivforth.options.data=$.extend(true, {},loadSysmodepurviewByRole(responsetext.roleSysmodepurviews));
            	commoninfodivforth.loadData(true);
	   		}
	   		
	   		if(responsetext.rolePosInfos!=null && responsetext.rolePosInfos.length>0)
	   		{
				commoninfodivpostion.options.data=$.extend(true, {},{Rows: responsetext.rolePosInfos,Total: responsetext.rolePosInfos.length});
            	commoninfodivpostion.loadData(true); 
	   		}
	   		else
	   		{
	   			commoninfodivpostion.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivpostion.loadData(true); 
            	addRosPostionRecord(); 
	   		}
	   		}catch(e){alert(e.message);}
        }
        //根据角色信息加载模组信息
        function loadMozuInfoByRole(lsroleMozuInfos)
        {
        	var ccount=0;
        	var strJson="";
        	for(var i=0;i<lsroleMozuInfos.length;i++)
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
					strJson=strJson+'{"strMoZuId": "'+lsroleMozuInfos[i].bsysmodeno+'", "strMoZuName": "'+lsroleMozuInfos[i].bsysmodename+'", "strShowFlag":"'+lsroleMozuInfos[i].strShowFlag+'"   }';		
			}
			if(strJson!="")
			{
				strJson=strJson+']';
				return {Rows:JSON.parse(strJson),Total:2};
			}
			return null;
        }
        
        //根据角色信息加载模组信息
        function loadSysmodepurviewByRole(roleSysmodepurviews)
        {
        	var ccount=0;
        	var strJson="";
        	for(var i=0;i<roleSysmodepurviews.length;i++)
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
					strJson=strJson+'{"bcurpurviewno": "'+roleSysmodepurviews[i].bcurpurviewno+'", "purviewname": "'+roleSysmodepurviews[i].purviewname+'", "showflag":"'+roleSysmodepurviews[i].showflag+'"   }';		
			}
			if(strJson!="")
			{
				strJson=strJson+']';

				return {Rows:JSON.parse(strJson),Total:2};
			}
			return null;
        }
  	   //初始根据角色信息加载模组信息
  		function loadCurpurviewno()
		{
			try
			{
				var strJson = "";//'{ "name": "cxh", "sex": "man" }';
				var ccount=0;
				if(parent.CommonparentDataByGroup!=null && parent.CommonparentDataByGroup.length>0)
				{	
					for(var i=0;i<parent.CommonparentDataByGroup.length;i++)
					{
						if(parent.CommonparentDataByGroup[i].binfotype=="XTQX")
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
							strJson=strJson+'{"bcurpurviewno":"'+parent.CommonparentDataByGroup[i].bparentcodekey+'", "purviewname": "'+parent.CommonparentDataByGroup[i].parentcodevalue+'","showflag":"N"   }';
							
							
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
		
		function loadCommonDataGridByInfotype()
		{
			try
			{
				var strJson = "";//'{ "name": "cxh", "sex": "man" }';
				var ccount=0;
				if(parent.Useroverall!=null && parent.Useroverall.length>0)
				{	
					for(var i=0;i<parent.Useroverall.length;i++)
					{
						if(parent.Useroverall[i].id.modetype=="2")
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
							strJson=strJson+'{ "strMoZuId":"'+parent.Useroverall[i].id.modevalue+'", "strMoZuName": "'+parent.Useroverall[i].descriptions+'"}';
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
		
		function showSysuserinfo(row, detailPanel,callback)
		{
			f_detailPanel=detailPanel;
			f_callback=callback;
			
			loadDetialByRoleId(row.strRoleId);
		}
		
		function loadDetialByRoleId(stRoleId)
		{
		
			var requestUrl ="bc003/loadRolsUserInfo.action"; 
			var responseMethod="loadRolsUserInfoMessage";		
			var param="strCurRoleId="+stRoleId;		
			sendRequestForParams_p(requestUrl,responseMethod,param );
		}
		
		function loadRolsUserInfoMessage(request)
	    {
	    	try
	    	{
	    	
	   		var responsetext = eval("(" + request.responseText + ")");
	   	
	   		if(responsetext.roleUserInfos!=null && responsetext.roleUserInfos.length>0)
	   		{
	   			var detailGrid = document.createElement('div'); 
	            $(f_detailPanel).append(detailGrid);
	            $(detailGrid).css('margin',10).ligerGrid({
	                columns:
	                            [
	                            { display: '门店', name: 'fromcompno',width: 70,type:'text' },
	                            { display: '员工编号', name: 'userno', width: 70,type:'text' },
	                            { display: '员工名称', name: 'username' ,width: 90 }
	                            ], isScroll: false, showToggleColBtn: false, width: '100%',
	                 data:{Rows: responsetext.roleUserInfos,Total: responsetext.roleUserInfos.length}
	                 , showTitle: false, rownumbers:true,columnWidth: 100
	                 , onAfterShowData: f_callback,frozen:false,usePager:false
	            });
	   		}
	   		}catch(e){alert(e.message)}
	   	}
	   	
	  
	   	
	   	function loadSelecMozutData(data, rowindex, rowobj)
	   	{
	   			checkedBfunctionno_thirth= [];
	   			curMozuIdKey=data.strMoZuId;
	   			var requestUrl ="bc003/loadRolsMokuaiInfo.action"; 
				var responseMethod="loadRolsmokuaiInfoMessage";		
				var param="strCurRoleId="+curRoleIdKey;		
				param=param+"&strCurMozuId="+data.strMoZuId;	
				sendRequestForParams_p(requestUrl,responseMethod,param );
		
	   	}
	   	
	   	function loadRolsmokuaiInfoMessage(request)
	   	{
	   		try
        	{
 
        	var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.roleMokuaiInfos!=null && responsetext.roleMokuaiInfos.length>0)
	   		{
	   			commoninfodivthirth.options.data=$.extend(true, {},{Rows: responsetext.roleMokuaiInfos,Total: responsetext.roleMokuaiInfos.length});
            	commoninfodivthirth.loadData(true);            	
	   		}
	   		}catch(e){alert(e.message);}
	   	}
	   
	   	function mokuai_isChecked(rowdata)
	   	{
	   		 if (checkNull(rowdata.strShowFlag)=="Y") 
	   		 {
	   		 	f_onCheckRow_thirth(true, rowdata);
                return true;
             }
            return false;
	   	}
	   	
	   	function mokuai_isChecked_forth(rowdata)
	   	{
	   		 if (checkNull(rowdata.showflag)=="Y") 
	   		 {
	   		 	f_onCheckRow_forth(true, rowdata);
                return true;
             }
            return false;
	   	}
	   	//获取模块多选的模块
	   	function f_onCheckRow_thirth(checked, data)
        {
        	if (checked) 
            	addCheckedBfunctionno_thirth(data.bfunctionno);
            else 
            	removeCheckedBfunctionno_thirth(data.bfunctionno);
        }
	   	var checkedBfunctionno_thirth= [];
	   	function findCheckedBfunctionno_thirth(bfunctionno)
        {
            for(var i =0;i<checkedBfunctionno_thirth.length;i++)
            {
                if(checkedBfunctionno_thirth[i] == bfunctionno) return i;
            }
            return -1;
        }
        
        function addCheckedBfunctionno_thirth(bfunctionno)
        {
            if(findCheckedBfunctionno_thirth(bfunctionno) == -1)
                checkedBfunctionno_thirth.push(bfunctionno);
        }
        function removeCheckedBfunctionno_thirth(bfunctionno)
        {
            var i = findCheckedBfunctionno_thirth(bfunctionno);
            if(i==-1) return;
            checkedBfunctionno_thirth.splice(i,1);
        }
        
        
        //获取特殊权限多选的模块
	   	function f_onCheckRow_forth(checked, data)
        {
        	if (checked) 
            	addCheckedBfunctionno_forth(data.bcurpurviewno);
            else 
            	removeCheckedBfunctionno_forth(data.bcurpurviewno);
        }
	   	var checkedBfunctionno_forth= [];
	   	function findCheckedBfunctionno_forth(curpurviewno)
        {
            for(var i =0;i<checkedBfunctionno_forth.length;i++)
            {
                if(checkedBfunctionno_forth[i] == curpurviewno) return i;
            }
            return -1;
        }
        
        function addCheckedBfunctionno_forth(curpurviewno)
        {
            if(findCheckedBfunctionno_forth(curpurviewno) == -1)
                checkedBfunctionno_forth.push(curpurviewno);
        }
        function removeCheckedBfunctionno_forth(curpurviewno)
        {
            var i = findCheckedBfunctionno_forth(curpurviewno);
            if(i==-1) return;
            checkedBfunctionno_forth.splice(i,1);
        }
        
	   	function itemclick_function(item)
	   	{
	   	try
	   	{
	   		var curjosnparam="";
	   		var needReplaceStr="";
	   		var jsonParam="";
	   		var jsonParam_specilright="";
	   		for (var rowid in commoninfodivthirth.records)
			{
				if(findCheckedBfunctionno_thirth(commoninfodivthirth.records[rowid]['bfunctionno'])!=-1)
				{	
				    var row =commoninfodivthirth.records[rowid];
				    curjosnparam=JSON.stringify(row);
					/*if(curjosnparam.indexOf("_id")>-1)
				  	{
				     	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				      	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				    }	    */        		   
				    if(jsonParam!="")
				      	jsonParam=jsonParam+",";
				    jsonParam= jsonParam+curjosnparam;
				 }            		 
			}
			for (var rowid in commoninfodivforth.records)
			{
				if(findCheckedBfunctionno_forth(commoninfodivforth.records[rowid]['bcurpurviewno'])!=-1)
				{	
				    var row =commoninfodivforth.records[rowid];
				    curjosnparam=JSON.stringify(row);
					/*if(curjosnparam.indexOf("_id")>-1)
				  	{
				     	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				      	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				    }	*/            		   
				    if(jsonParam_specilright!="")
				      	jsonParam_specilright=jsonParam_specilright+",";
				    jsonParam_specilright= jsonParam_specilright+curjosnparam;
				 }            		 
			}
			var requestUrl ="bc003/postMoKuai.action";
			var params="strCurRoleId="+curRoleIdKey;	
			params=params+"&strCurMozuId="+curMozuIdKey;		
			if(jsonParam!="")
				 params=params+"&strJsonParam=["+jsonParam+"]";
			if(jsonParam_specilright!="")
				 params=params+"&strJsonParam_specilright=["+jsonParam_specilright+"]";
			var responseMethod="postMoKuaiMessage";	
	
			sendRequestForParams_p(requestUrl,responseMethod,params ); 
			
			}catch(e){alert(e.message);}
	   	}
	   	
	   	function postMoKuaiMessage(request)
        {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
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
        
        
    function addThirthDetialRecord()
    {
    		var row = commoninfodivthirth.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivthirth.addRow({ 
				     
			}, row, false);
    }
    	function hotKeyOfSelf(key)
		{
			 if( key == 83 &&  event.altKey)
			{
				itemclick_function("");
			}
	
		}
		
	function addRosPostionRecord()
    {
    		
    		var row = commoninfodivpostion.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivpostion.addRow({ 
				          position:'',
				          positionname:''
			}, row, false);
    }
    
	function addRolePosition()
	{
		if(checkNull(curRoleIdKey)=="")
		{
			$.ligerDialog.error("请选择角色!");
			return ;
		}
		else
		{
			addposition="";
			addpositionname="";
		    addBandDialog=$.ligerDialog.open({ height: 600, url: contextURL+'/BaseInfoControl/BC003/handBandPosition.jsp', width: 300, showMax: false, showToggle: false, showMin: false, isResize: false, title: '添加职位' });
		    
		}
	}
	
	 function handBandPosMessage(request)
     {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        	
	        		var addpositions=addposition.split(";");
	        		for(var i=0;i<addpositions.length*1;i++)
	        		{
	        			if(addpositions[i]!="")
	        			{
	        				var gridlen=commoninfodivpostion.rows.length*1;
							if(gridlen==0)
							{
								addRosPostionRecord();
								gridlen=gridlen+1;
							} 
							if(checkNull(commoninfodivpostion.getRow(0).position)!="")
							{
								addRosPostionRecord();
								gridlen=gridlen+1;
							}   
							var curpositionRecord=commoninfodivpostion.getRow(gridlen-1);
							commoninfodivpostion.updateRow(curpositionRecord,{position: addpositions[i]
																	  ,positionname : parent.loadCommonControlValue("GZGW",0,addpositions[i])});  
	        			}
	        		}
	        	
	        		
	        		addBandDialog.close();
	        	}
	        	else
	        	{
	        		$.ligerDialog.error(strMessage);
	        	}
	        	addposition="";
				addpositionname="";
        	}
			catch(e)
			{
				alert(e.message);
			}
        }
        
        function cancleBandInfo()
        {
        	if(checkNull(strCurPostion)=="")
        	{
        		$.ligerDialog.error("请选择需要取消绑定的职位!");
				return ;
        	}
        	
        	var params =" strCurRoleId="+curRoleIdKey;
		    params=params+"&strCurPostion="+strCurPostion;
		    var requestUrl ="bc003/handdeleteBandPos.action";
	   		var responseMethod="handdeleteBandPosMessage";
	   		sendRequestForParams_p(requestUrl,responseMethod,params);
        }
        
        function handdeleteBandPosMessage(request)
     	{
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        	
	        		commoninfodivpostion.deleteSelectedRow();
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