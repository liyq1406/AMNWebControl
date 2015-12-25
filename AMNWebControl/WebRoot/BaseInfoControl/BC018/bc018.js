	var compTreeManager;
   	var compTree;
	var commoninfodivmaster = null;
	var commoninfodivproject = null;
	var commoninfodivdetial = null;
	var strCurCompId = "";
	var strCurProjecttypeid = "";
	var strCurPackageNo="";
	var bc018layout=null;
	var curMasterInfoDate = null;
	var curpagestate =3;
	var curDetialRecord = null;
	var showDialogmanager=null;
	var f_detailPanel	=null;
	var curStrProjectNo="";
	var curStrGoodsNo="";
	var f_callback=null;
	var goodsWPTJData=JSON.parse(parent.loadCommonControlDate_select("WPTJ",0));
    $(function ()
   	{
	   try
	   {
	   		bc018layout= $("#bc018layout").ligerLayout({  allowBottomResize: false, allowLeftResize: false });
            var height = $(".l-layout-center").height();
          
            commoninfodivmaster=$("#commoninfodivmaster").ligerGrid({
                columns: [
                { display: '项目类别', 		name: 'bprojecttypeid', 				width:80	,align: 'left' },
                { display: '类别名称', 		name: 'bprojecttypename', 			width:200	,align: 'left' }
	            ],  pageSize:20, 
                data: loadCommonControlDate("XMTJ",0),      
                width: '285',
                height:height-20,
                enabledEdit: false,  checkbox:false,rownumbers:false,usePager:false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                }
            });
           commoninfodivproject=$("#commoninfodivproject").ligerGrid({
                columns: [
                { display: '项目编号', 		name: 'bprjno', 			width:90	,align: 'left' },
                { display: '项目名称', 		name: 'prjname', 			width:170	,align: 'left' }
	            ],  pageSize:20, 
                data: null,      
                width: '300',
                height:height-20,
                enabledEdit: false,  checkbox:false,rownumbers:false,usePager:false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecProjectData(data, rowindex, rowobj);
                }
            });
            
             commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
                columns: [
                { display: '产品编号', 		name: 'goodsno', 			width:90	,align: 'left' },
                { display: '产品名称', 		name: 'goodsname', 			width:200	,align: 'left' },
                { display: '产品类型', 		name: 'goodstype', 			width:80	,align: 'left',
                	editor: { type: 'select', data: goodsWPTJData, valueField: 'choose',selectBoxHeight:'220'},
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("WPTJ",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.goodstype)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                }  },
                { display: '产品单位', 		name: 'goodsunit', 			width:60	,align: 'left' },
                { display: '消耗数量', 		name: 'costunitcount', 		width:60	,align: 'right' }
	            ],  pageSize:20, 
                data: null,      
                width: '500',
                height:height-65,
                enabledEdit: false,  checkbox:false,rownumbers:false,usePager:false,
                onContextmenu : function (parm,e)
                {
                	 curStrGoodsNo=parm.data.goodsno;
                     menu.show({ top: e.pageY, left: e.pageX });
                     return false;
                } 
            });
            
            
            menu = $.ligerMenu({ width: 180, items:
	            [
	            	{ text: '删除当前消耗信息', click: deleteProjectCostInfo, icon: 'delete' , img: contextURL+'/common/ligerui/ligerUI/skins/icons/candle.gif'}
	            ]
	            }); 
	            
             $("#postCosInfo").ligerButton(
	         {
	             text: '添加设置', width: 100,
		         click: function ()
		         {
		             postCosInfo();
		         }
	         });
	         
            addProjectgrid();
            addGoodsgrid();
          	$("#pageloading").hide(); 
   		}
   		catch(e){alert(e.message);}
    });
    
    function addProjectgrid()
    {
    	var row = commoninfodivproject.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
		commoninfodivproject.addRow({ 
				            }, row, false);
    }
    
    function addGoodsgrid()
    {
    	var row = commoninfodivdetial.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
		commoninfodivdetial.addRow({ 
				            goodsno:'',
				            goodsname:'',
				            goodstype:'',
				            goodsunit:'',
				            costunitcount:0
				            }, row, false);
    }
    
    function loadCommonControlDate(code,lvl)
	{
		var returnValue='';
		var items = parent.gainCommonInfoByCode(code,lvl);
		var ccount=0;
		for(var i=0;i<items.length;i++)
		{
		    if(lvl*1==0)
			{
				key = items[i].bparentcodekey;
				value = items[i].parentcodevalue;
			}
			else
			{
				key = items[i].bcodekey;
				value = items[i].codevalue;
			}
			if(returnValue!='')
			{
				returnValue=returnValue+',';
			}
			else
			{
				returnValue=returnValue+'[';
			}
			ccount=ccount*1+1;
			returnValue=returnValue+'{"bprojecttypeid": "'+key+'","bprojecttypename": "'+value+'"}';
		}
		if(returnValue!='')
		{
			returnValue=returnValue+']';
			return {Rows:JSON.parse(returnValue),Total:ccount*1};
		}
		else
		{
			return null;
		}
	}
	
   
  
   
	 
    function loadSelecDetialData(data, rowindex, rowobj)
    {
    	var params = "strCurProjecttypeid="+data.bprojecttypeid;
		var myAjax = new parent.Ajax.Request(
			"bc018/loadCurProjectinfo.action",
			{
				method:'post',
				parameters:params,
				onComplete:function (request) {							
					var action = eval( "("+request.responseText+")");	
					if(action.lsPrjInfos!=null && action.lsPrjInfos.length>0)
					{
						   commoninfodivproject.options.data=$.extend(true, {},{Rows: action.lsPrjInfos,Total: action.lsPrjInfos.length});
					       commoninfodivproject.loadData(true);            	
					}
					else
					{
						   commoninfodivproject.options.data=$.extend(true, {},{Rows: null,Total: 0});
					       commoninfodivproject.loadData(true);
					       addProjectgrid();   
					}
				},
				asynchronous:true
			});
   	}
   	
   	function loadSelecProjectData(data, rowindex, rowobj)
   	{
   		curStrProjectNo=data.bprjno;
   		var params = "curStrProjectNo="+curStrProjectNo;
		var myAjax = new parent.Ajax.Request(
			"bc018/loadCurGoodsinfo.action",
			{
				method:'post',
				parameters:params,
				onComplete:function (request) {							
					var action = eval( "("+request.responseText+")");	
					if(action.lsProjectcostinfos!=null && action.lsProjectcostinfos.length>0)
					{
						   commoninfodivdetial.options.data=$.extend(true, {},{Rows: action.lsProjectcostinfos,Total: action.lsProjectcostinfos.length});
					       commoninfodivdetial.loadData(true);            	
					}
					else
					{
						   commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total: 0});
					       commoninfodivdetial.loadData(true);
					       addGoodsgrid();   
					}
				},
				asynchronous:true
			});
   	}
   	
   function selectCall()
   {
		validateinserno(document.getElementById("winsergoodsno"));
   }
   
   
    function hotKeyOfSelf(key)
	{
		if(key==13)//回车
		{
			hotKeyEnter();
		}
	}
	
   	function  hotKeyEnter()
	{
		try
		{
			var fieldName = document.activeElement.name;
			var fieldId = document.activeElement.id ;
			if(fieldId=="winsergoodsno")
			{
				document.activeElement.onchange();
			}
			else
			{
				window.event.keyCode=9; //tab
				window.event.returnValue=true;
			}
				
		}
		catch(e){alert(e.message);}
				
	}   
		
		
   	function validateinserno(obj)
	{
				var checkflag=0;
				var goodsbarno="";
				var strgoodsbarno=null;
				var goodsBarFlag=0;
				if(parent.lsGoodsinfo!=null && parent.lsGoodsinfo.length>0)
				{
					for(var i=0;i<parent.lsGoodsinfo.length;i++)
					{
						if( parent.lsGoodsinfo[i].id.goodsno==obj.value
						|| checkNull(parent.lsGoodsinfo[i].goodsabridge).toLowerCase()==obj.value.toLowerCase())
						{
							if(checkNull(parent.lsGoodsinfo[i].useflag)==1)
							{
	        	 				$.ligerDialog.warn("产品!"+parent.lsGoodsinfo[i].goodsname+"已停用");
	        	 				obj.focus();
								obj.select();
	        	  				obj.value="";
	        	 				return ;
	        				}
							document.getElementById("winsergoodsname").value=checkNull(parent.lsGoodsinfo[i].goodsname);
							document.getElementById("winsergoodsunit").value=checkNull(parent.lsGoodsinfo[i].purchaseunit);
							document.getElementById("winsergoodstype").value=checkNull(parent.lsGoodsinfo[i].goodspricetype);
							document.getElementById("winsergoodscount").value=0;
							document.getElementById("winsergoodscount").focus();
							document.getElementById("winsergoodscount").select();
							checkflag=1;
							break;
						}
						goodsbarno=checkNull(parent.lsGoodsinfo[i].goodsbarno);
						if(goodsbarno!="")
						{
							
							strgoodsbarno= new Array(); 
						
							strgoodsbarno=goodsbarno.split(";");  
							goodsBarFlag=0;
								
							for (j=0;j<strgoodsbarno.length ;j++ )   
							{
								if(strgoodsbarno[j]==obj.value)
								{
									goodsBarFlag=1;
									document.getElementById("winsergoodsno").value=checkNull(parent.lsGoodsinfo[i].id.goodsno);
									document.getElementById("winsergoodsname").value=checkNull(parent.lsGoodsinfo[i].goodsname);
									document.getElementById("winsergoodsunit").value=checkNull(parent.lsGoodsinfo[i].purchaseunit);
									document.getElementById("winsergoodstype").value=checkNull(parent.lsGoodsinfo[i].goodspricetype);
									document.getElementById("winsergoodscount").value=0;
									document.getElementById("winsergoodscount").focus();
									document.getElementById("winsergoodscount").select();
									break;
								}
							}
							if(goodsBarFlag==1)
							{
								checkflag=1;
								break;
							}
						}		 
					}
				}
				if(checkflag==0)
				{
					$.ligerDialog.warn("产品编号不存在或已停用");
	        	 	document.getElementById("winsergoodsno").focus();
					document.getElementById("winsergoodsno").select();
	        	 	obj.value="";
	        	  
				}
	}
   	
   	
   	function postCosInfo()
   	{
   		
   		if(curStrProjectNo=="")
   		{
   			$.ligerDialog.error("请确认消耗的项目信息");
   			return;
   		}
   		if(document.getElementById("winsergoodsno").value=="")
   		{
   			$.ligerDialog.error("请确认消耗的产品信息");
   			return;
   		}
   		if(document.getElementById("winsergoodscount").value*1==0)
   		{
   			$.ligerDialog.error("请确认消耗的产品数量");
   			return;
   		}
   		for (var rowid in commoninfodivdetial.records)
		{
				var row =commoninfodivdetial.records[rowid];
				if(checkNull(row.goodsno)=="")
					continue;
				if(checkNull(row.goodsno)==document.getElementById("winsergoodsno").value)
				{
					$.ligerDialog.error("该产品已在消耗列表存在,请确认!");
   					return;
				}
		}       
		showDialogmanager = $.ligerDialog.waitting('正在保存中,请稍候...');	
   		var params = "curStrProjectNo="+curStrProjectNo;
   		params=params+"&curStrGoodsNo="+document.getElementById("winsergoodsno").value;
   		params=params+"&curGoodsCount="+document.getElementById("winsergoodscount").value;
		var myAjax = new parent.Ajax.Request(
			"bc018/postCurProjectinfo.action",
			{
				method:'post',
				parameters:params,
				onComplete:function (request) {							
					var action = eval( "("+request.responseText+")");
				
					if(checkNull(action.strMessage)=="")
					{
						  	var gridlen=commoninfodivdetial.rows.length*1;
							if(gridlen==0)
							{
									addGoodsgrid();
									gridlen=gridlen+1;
							} 
							if(checkNull(commoninfodivdetial.getRow(0).goodsno)!="")
							{
									addGoodsgrid();
									gridlen=gridlen+1;
							}
							curDetialRecord=commoninfodivdetial.getRow(gridlen-1);
						
							commoninfodivdetial.updateRow(curDetialRecord,{goodsno : document.getElementById("winsergoodsno").value
																		  ,goodsname : document.getElementById("winsergoodsname").value
																		  ,goodstype  : document.getElementById("winsergoodstype").value
																		  ,goodsunit : document.getElementById("winsergoodsunit").value
																		  ,costunitcount : ForDight(document.getElementById("winsergoodscount").value*1,2)
																		 });         	
					}
					else
					{
						   $.ligerDialog.error(checkNull(action.strMessage));
					}
					showDialogmanager.close();
					document.getElementById("winsergoodsno").value="";
					document.getElementById("winsergoodsname").value="";
					document.getElementById("winsergoodsunit").value="";
					document.getElementById("winsergoodstype").value="";
					document.getElementById("winsergoodscount").value=0;
				},
				asynchronous:true
			});
			
   	}
   	
   	function deleteProjectCostInfo()
   	{
   		var params = "curStrProjectNo="+curStrProjectNo;
   		params=params+"&curStrGoodsNo="+curStrGoodsNo;
		var myAjax = new parent.Ajax.Request(
			"bc018/deleteCurProjectinfo.action",
			{
				method:'post',
				parameters:params,
				onComplete:function (request) {							
					var action = eval( "("+request.responseText+")");	
					if(checkNull(action.strMessage)=="")
					{
						  	commoninfodivdetial.deleteSelectedRow();      	
					}
					else
					{
						   $.ligerDialog.error(checkNull(action.strMessage));
					}
				},
				asynchronous:true
			});
			
   	}