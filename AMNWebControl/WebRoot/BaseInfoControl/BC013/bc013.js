
   	var compTree = null;//主明细	
   	var compTreeManager = null;//主明细	
   	var commondetial=null; //模板信息
   	var curCompid="";
   	var curModeType="";
   	var curModeid="";
   	var curProjectid="";
   	var curGoodsid ="";
   	var curCardtypeid ="";
   	var curCompname="";
   	var bc013layout=null;
   	var BC013Tab = null;
   	var BC013DetialTab=null;
   	var commoninfodivsecond=null;
   	var commoninfodivthirth=null;
   	var commoninfodivfourth=null;
   	var commoninfodivfive=null;
   	var curLsinfomodes = null;
  
   	var modetypeManager=null;
   	var prjtypemanager=null;
   	var editprjtypemanager=null;
   	var prjreporttypemanager=null;
   	var editprjreporttypemanager=null;
   	
   	var goodstypemanager=null;
   	var editgoodstypemanager=null;
   	var goodsreporttypemanager=null;
   	var editgoodsreporttypemanager=null;
   	var goodscostunitmanager=null;
   	var goodssaleunitmanager=null;
   	var goodspurchaseunitmanager=null;
   	var fromValidate=null;
   	var curLsProjectinfo=null;
   	var curLsGoodsinfo = null;
   	var curLsCardTypeinfo = null;
   	var selfflag=1;
   	var chooseData = [{ choose: 1, text: '启' }, { choose: 2, text: '禁'}];
   	var chooseSourceData = [{ choose: 1, text: '私有' }, { choose: 0, text: '继承模板'}];
   	var chooseCardTypeData=null;
   	var lsCardType=null;
   	var commoninfodivdetial_goods=null;
   	var showDialogCompany=null;
    $(function ()
   	{
	   try
	   {
	   		  //布局
            bc013layout= $("#bc013layout").ligerLayout({ leftWidth: 270,rightWidth:235,  allowBottomResize: false, allowLeftResize: false ,isLeftCollapse:true });
             var height = $(".l-layout-center").height();
             $("#projectInfoDiv,#goodsInfoDiv,#cardTypeInfoDiv").height(height);
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
   			//$("#proddate").ligerDateEditor({labelWidth: 100, labelAlign: 'right'});
           	$("#BC013Tab").ligerTab();
            BC013Tab = $("#BC013Tab").ligerGetTabManager();
            $("#BC013DetialTab").ligerTab();
            BC013DetialTab = $("#BC013DetialTab").ligerGetTabManager();
           
          	compTreeManager = $("#companyTree").ligerGetTreeManager();
          	commondetial=$("#commondetial").ligerGrid({
                columns: [
                { display: '代码', name: 'bmodeid',  width: 100 , align: 'left'},
                { display: '名称', name: 'modename',  width: 140, align: 'left' },
                { display: '归属', name: 'modesource',  width: 60, align: 'left' },
                { display: '父模板', name: 'parentmodeid',   width: 75 }
                ],  pageSize:30, 
                data:null,        
                width: '100%',
                height:'70%',
                enabledEdit: false,  checkbox:false,rownumbers:false,usePager:false,
                groupColumnName:'strModeTypeText',groupColumnDisplay:'',
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                }	 ,
                toolbar: { items: [
                { text: '增加', click: itemclick_mode, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
                { line: true },
                { text: '保存', click: itemclick_mode, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' },
                { line: true }
                //{ text: '删除', click: itemclick_mode, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' } 
                ]}        
            });
            commoninfodivsecond=$("#commoninfodivsecond").ligerGrid({
                columns: [
                { display: '模板', name: 'bprjmodeId',  width: 90 , align: 'left'},
                { display: '项目编号', name: 'bprjno',  width: 85, align: 'left' },
                { display: '项目名称', name: 'prjname',  width: 145, align: 'left' } ,
	          
	            { display: '标准价', name: 'saleprice', width:50,align: 'left' },
	            { display: '疗程次数', name: 'ysalecount', width:60,align: 'left' },
	            { display: '疗程价', name: 'ysaleprice', width:50,align: 'left' },
	            { display: '项目归属', name: 'bprisource', width:70,align: 'left' },
	            { display: '项目来源', 	name: 'selfFlag',  width:70,align: 'left' , 
	                editor: { type: 'select', data: chooseSourceData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.selfFlag == 1) return '私有';
	                      return '继承';
	                }
	            }
	            
                ],  pageSize:20, 
                data: $.extend(true, {},{Rows: null,Total:0}),        
                width: '665',
                height:'95%',
                enabledEdit: false,  checkbox:false,rownumbers:false,usePager:true, where : f_getProjects(),
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecSecondDetialData(data, rowindex, rowobj);
                } ,
                toolbar: { items: [
                { text: '查询', click: itemclick_project, img: contextURL+'/common/ligerui/ligerUI/skins/icons/search.gif' },
                { line: true },
                { text: '增加', click: itemclick_project, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
                { line: true },
                { text: '保存', click: itemclick_project, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' },
                { line: true }
                //{ text: '删除', click: itemclick_project, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' } 
                ]} 	          
            });
            commoninfodivthirth=$("#commoninfodivthirth").ligerGrid({
                columns: [
                { display: '模板', name: 'bgoodsmodeid',  width: 70 , align: 'left'},
                { display: '产品代码', name: 'bgoodsno',  width: 80 , align: 'left'},
                { display: '产品名称', name: 'goodsname',  width: 180, align: 'left' },
                { display: '标准单位', name: 'saleunit',  width: 60, align: 'left' },
                { display: '规格', name: 'goodsformat',  width: 40, align: 'left' },
                { display: '标准价格', name: 'standprice',  width: 60, align: 'left' },
                { display: '产品归属', name: 'bgoodssource', width:50,align: 'left' },
	            { display: '产品来源', 	name: 'selfFlag',  width:70,align: 'left' , 
	                editor: { type: 'select', data: chooseSourceData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.selfFlag == 1) return '私有';
	                      return '继承';
	                }
	            }
                ],  pageSize:20, 
                data:null,        
                width: '665',
                height:'95%',
                enabledEdit: false,  checkbox:false,rownumbers:false,usePager:true, where : f_getGoods(),
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecThirthDetialData(data, rowindex, rowobj);
                },
                toolbar: { items: [
                { text: '查询', click: itemclick_goods, img: contextURL+'/common/ligerui/ligerUI/skins/icons/search.gif' },
                { line: true },
                { text: '增加', click: itemclick_goods, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
                { line: true },
                { text: '保存', click: itemclick_goods, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' },
                { line: true }
                //{ text: '删除', click: itemclick_goods, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' } 
                ]} 	  	          
            });
            commoninfodivfourth=$("#commoninfodivfourth").ligerGrid({
                columns: [
                { display: '模板', name: 'bcardtypemodeid',  width: 80 , align: 'left'},
                { display: '卡种代码', name: 'bcardtypeno',  width: 80 , align: 'left'},
                { display: '卡种名称', name: 'cardtypename',  width: 140, align: 'left' },
                { display: '开卡额度', name: 'lowopenamt',  width: 60, align: 'left' },
                { display: '充卡额度', name: 'lowfillamt',  width: 60, align: 'left' },
                { display: '卡种归属', name: 'bcardtypesource', width:80,align: 'left' },
	            { display: '卡种来源', 	name: 'selfFlag',  width:70,align: 'left' , 
	                editor: { type: 'select', data: chooseSourceData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.selfFlag == 1) return '私有';
	                      return '继承';
	                }
	            }
                ],  pageSize:20, 
                data:null,        
                width: '665',
                height:'95%',
                enabledEdit: false,  checkbox:false,rownumbers:false,usePager:true,where :f_getCardType,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecForthDetialData(data, rowindex, rowobj);
                },
                toolbar: { items: [
                { text: '查询', click: itemclick_cardtype, img: contextURL+'/common/ligerui/ligerUI/skins/icons/search.gif' },
                { line: true },
                { text: '增加', click: itemclick_cardtype, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
                { line: true },
                { text: '保存', click: itemclick_cardtype, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' },
                { line: true }
                //{ text: '删除', click: itemclick_cardtype, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' } 
                ]} 	          
            });
            commoninfodivfive  = $("#commoninfodivfive").ligerGrid({
                columns: [ 
                {display: '转卡类型', name: 'tocardtypeno', align: 'left', width: 150  ,
	            	editor: { type: 'select', data: null, valueField: 'choose',selectBoxHeight:'220'},
	            	render: function (item)
	              	{
	              		for( var i=0;i<lsCardType.length;i++)
						{
							if(lsCardType[i].id.cardtypeno==item.tocardtypeno)
							{
								return lsCardType[i].cardtypename;
							}
						}
	                    return '';
	                } 
	            },
                { display: '转卡金额', name: 'changeamt', align: 'left'  ,width: 60, editor: { type: 'text' } }
                ],     data: null, pageSize: 50, sortName: 'tocardtypeno',
                enabledEdit: true, width: '225', height: '150',rownumbers:false,
                fixedCellHeight:false,usePager:false,
                toolbar: { items: [
                { text: '添加转卡规则', click: itemclick_cardtyperule, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' }
                ]} 	  
            });
             $.validator.addMethod(
                    "notnull",
                    function (value, element, regexp)
                    {
                        if (!value) return true;
                        return !$(element).hasClass("l-text-field-null");
                    },
                    "不能为空"
            );

            $.metadata.setType("attr", "validate");
            fromValidate = $("form").validate({
                //调试状态，不会提交数据的
                debug: true,
                errorPlacement: function (lable, element)
                {

                    if (element.hasClass("l-textarea"))
                    {
                        element.addClass("l-textarea-invalid");
                    }
                    else if (element.hasClass("l-text-field"))
                    {
                        element.parent().addClass("l-text-invalid");
                    }
                    $(element).removeAttr("title").ligerHideTip();
                    $(element).attr("title", lable.html()).ligerTip();
                },
                success: function (lable)
                {
                    var element = $("#" + lable.attr("for"));
                    if (element.hasClass("l-textarea"))
                    {
                        element.removeClass("l-textarea-invalid");
                    }
                    else if (element.hasClass("l-text-field"))
                    {
                        element.parent().removeClass("l-text-invalid");
                    }
                    $(element).removeAttr("title").ligerHideTip();
                },
                submitHandler: function ()
                {
                    alert("Submitted!");
                }
            });
            var prjtypeData=JSON.parse(parent.loadCommonControlDate("XMLB",0));
           	prjtypemanager = $("#searchPrjType").ligerComboBox({ data: prjtypeData, isMultiSelect: false,valueFieldID: 'factsearchPrjType',width:'120',selectBoxHeight:'220',alwayShowInDown:true});	
           	editprjtypemanager = $("#prjtype").ligerComboBox({ data: prjtypeData, isMultiSelect: false,valueFieldID: 'factprjtype',width:'120',selectBoxHeight:'220',alwayShowInDown:true});	
           	var  prjreporttypeData=JSON.parse(parent.loadCommonControlDate("XMTJ",0));
           	prjreporttypemanager = $("#searchPrjReportType").ligerComboBox({ data: prjreporttypeData, isMultiSelect: false,valueFieldID: 'factsearchPrjReportType',width:'100',selectBoxHeight:'400',alwayShowInDown:true });           	 
          	//editprjreporttypemanager = $("#prjreporttype").ligerComboBox({ data: prjreporttypeData, isMultiSelect: false,valueFieldID: 'factprjreporttype',width:'100',selectBoxHeight:'400' ,alwayShowInDown:true});           
          	
          	addOption("","",document.getElementById("prjreporttype_s"));
           	var projectstypes=parent.gainCommonInfoByCode("XMTJ",0);
		    for(var i=0;i<projectstypes.length;i++)
			{
				addOption(projectstypes[i].bparentcodekey,projectstypes[i].parentcodevalue,document.getElementById("prjreporttype_s"));
			}	
		    
		    addOption("","",document.getElementById("ipaddl_s"));
		    var ipaddl=parent.gainCommonInfoByCode("IPDDL",0);
		    for(var i=0;i<ipaddl.length;i++)
			{
				addOption(ipaddl[i].bparentcodekey,ipaddl[i].parentcodevalue,document.getElementById("ipaddl_s"));
			}
			
          	var goodstypeData=JSON.parse(parent.loadCommonControlDate("WPDL",0));
           	goodstypemanager = $("#searchGoodsType").ligerComboBox({ data: goodstypeData, isMultiSelect: false,valueFieldID: 'factsearchGoodsType',width:'120',selectBoxHeight:'220',alwayShowInDown:true});	
           	editgoodstypemanager = $("#goodstype").ligerComboBox({ data: goodstypeData, isMultiSelect: false,valueFieldID: 'factgoodstype',width:'120',selectBoxHeight:'220',alwayShowInDown:true});	
           	/*var  goodsreporttypeData=JSON.parse(parent.loadCommonControlDate("WPTJ",0));
           	goodsreporttypemanager = $("#searchGoodsReportType").ligerComboBox({ data: goodsreporttypeData, isMultiSelect: false,valueFieldID: 'factsearchGoodsReportType',width:'120',selectBoxHeight:'400' ,alwayShowInDown:true});           	 
        	*/
        	addOption("","",document.getElementById("searchGoodsReportType"));
           	var goodstypes=parent.gainCommonInfoByCode("WPTJ",0);
		    for(var i=0;i<goodstypes.length;i++)
			{
				addOption(goodstypes[i].bparentcodekey,goodstypes[i].parentcodevalue,document.getElementById("searchGoodsReportType"));
			}
           
        	//editgoodsreporttypemanager = $("#goodspricetype").ligerComboBox({ data: goodsreporttypeData, isMultiSelect: false,valueFieldID: 'factgoodspricetype',width:'120',selectBoxHeight:'400' ,alwayShowInDown:true});           
          	
          	clearOption("h_goodspricetype");
          	var goodstj=parent.gainCommonInfoByCode("WPTJ",0);
		    for(var i=0;i<goodstj.length;i++)
			{
				addOption(goodstj[i].bparentcodekey,goodstj[i].parentcodevalue,document.getElementById("h_goodspricetype"));
			}	
          	//var goodsunitData=JSON.parse(parent.loadCommonControlDate("WPDW",0));
          	// goodscostunitmanager=$("#costunit").ligerComboBox({ data: goodsunitData, isMultiSelect: false,valueFieldID: 'factcostunit',width:'80',selectBoxHeight:'220',alwayShowInDown:true});	;
   			 //goodssaleunitmanager=$("#saleunit").ligerComboBox({ data: goodsunitData, isMultiSelect: false,valueFieldID: 'factsaleunit',width:'80',selectBoxHeight:'220',alwayShowInDown:true});	;
   			 //goodspurchaseunitmanager=$("#purchaseunit").ligerComboBox({ data: goodsunitData, isMultiSelect: false,valueFieldID: 'factpurchaseunit',width:'80',selectBoxHeight:'220',alwayShowInDown:true});	;
          	clearOption("h_costunit");
          	clearOption("h_saleunit");
          	clearOption("h_purchaseunit");
          	var goodsunit=parent.gainCommonInfoByCode("WPDW",0);
		    for(var i=0;i<goodsunit.length;i++)
			{
				addOption(goodsunit[i].bparentcodekey,goodsunit[i].parentcodevalue,document.getElementById("h_costunit"));
				addOption(goodsunit[i].bparentcodekey,goodsunit[i].parentcodevalue,document.getElementById("h_saleunit"));
				addOption(goodsunit[i].bparentcodekey,goodsunit[i].parentcodevalue,document.getElementById("h_purchaseunit"));
			}	
          	$("#stopdate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
          	
          	 $("#downPrjInfo").ligerToolBar({ items: [
           	 	{ text:'<select id="targerModeId" name="targerModeId" style="width:120"></select>'},
           	 	{ text:'下发',click: downProjectMode,img: contextURL+'/common/ligerui/ligerUI/skins/icons/down.gif'}
               ]
            });
            
          	 $("#bindgoodsno").focus(function(){//绑定供应商产品
          		 $.ligerDialog.open({ height: 560, url: contextURL+'/BaseInfoControl/BC013/showGoods.jsp', width: 880, showMax: false, showToggle: false,allowClose:true, showMin: false, isResize: false, title: '绑定供应商产品',
          			 buttons: [ { text: '确定', onclick: function (item, dialog) {
          				 var row = commoninfodivdetial_goods.getSelectedRow();
          				 if(row == null){
          					 $.ligerDialog.warn("请选择绑定的供应商产品，请确认！");
          					 return;
          				 }
          				 $("#bindgoodsno").val(row.materialID);
          				 dialog.close();
          			 } }, { text: '取消', onclick: function (item, dialog) {dialog.close(); } } ] });
          	 });
          	 $("#goodscompany").focus(function(){//允许采购门店
          		showDialogCompany=$.ligerDialog.open({ height: 560, url: contextURL+'/BaseInfoControl/BC013/showCompany.jsp', width: 700, showMax: false, showToggle: false,allowClose:true, showMin: false, isResize: false, title: '允许采购门店',_company:$("#goodscompany").val(),
          			 buttons: [ { text: '确定', onclick: function (item, dialog) {
          				 var rows = commoninfodivdetial_company.getCheckedRows();
          				 if(rows == null || rows.length==0){
          					 $.ligerDialog.warn("请选择允许采购的门店，请确认！");
          					 return;
          				 }
          				 var _value="";
          				 $(rows).each(function(){
          					_value += (this.attr1+";");
          				 });
          				 $("#goodscompany").val(_value);
          				 dialog.close();
          			 } }, { text: '取消', onclick: function (item, dialog) {dialog.close(); } } ] });
          	 });
          	$("#pageloading").hide(); 
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
        	curCompname=note.data.text;
       		var params = "strCurCompId="+note.data.id;				
     		var requestUrl ="bc013/loadInfoModes.action"; 
			var responseMethod="loadInfoModesMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params );
          }catch(e){alert(e.message);}
    }
    function loadInfoModesMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
    		curLsinfomodes=responsetext.lsinfomodes;
	   		if(responsetext.lsinfomodes!=null && responsetext.lsinfomodes.length>0)
	   		{
	   			
	   			commondetial.options.data=$.extend(true, {},{Rows: responsetext.lsinfomodes,Total: responsetext.lsinfomodes.length});
            	commondetial.loadData(true); 
            	commondetial.select(0); 
            	for(var i=0;i<responsetext.lsinfomodes.length;i++)
            	{
            		if(responsetext.lsinfomodes[i].bmodetype=="1")
            		{
            			addOption(responsetext.lsinfomodes[i].bmodeid,responsetext.lsinfomodes[i].modename,document.getElementById("targerModeId"));
            		}   
            	}
            	        	
	   		}
	   		else
	   		{
	   			commondetial.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commondetial.loadData(true);      
	   		}
	   		
	   	}catch(e){alert(e.message);}
    }
    
    function loadSelecDetialData(data, rowindex, rowobj)
    {
    	curModeid=data.bmodeid;
    	curModeType=data.bmodetype;
    	BC013DetialTab.selectTabItem("tabitem"+curModeType);
    	if(curModeType==1)//项目
    	{
    		document.getElementById("cardTypeInfoDiv").style.display="none";
    		document.getElementById("goodsInfoDiv").style.display="none";
    		document.getElementById("projectInfoDiv").style.display="block";
    	}
    	else if(curModeType==2)//产品
    	{
    		document.getElementById("cardTypeInfoDiv").style.display="none";
    		document.getElementById("goodsInfoDiv").style.display="block";
    		document.getElementById("projectInfoDiv").style.display="none";
    	}
    	else if(curModeType==3)//卡类型
    	{
    		document.getElementById("cardTypeInfoDiv").style.display="block";
    		document.getElementById("goodsInfoDiv").style.display="none";
    		document.getElementById("projectInfoDiv").style.display="none";
    	}
    	if(curModeid!="")
    	{
    		if(curLsinfomodes!=null && curLsinfomodes.length>0)
    		{
    			for(var i=0;i<curLsinfomodes.length;i++)
    			{
    				if(curLsinfomodes[i].bmodeid==curModeid)
    				{
    					document.getElementById("modetype").value=curLsinfomodes[i].id.modetype;
    					document.getElementById("modeid").value=curLsinfomodes[i].id.modeid;
    					document.getElementById("modename").value=curLsinfomodes[i].modename;
    					document.getElementById("modesource").value=curLsinfomodes[i].modesource;
    					document.getElementById("modesourceName").value=curLsinfomodes[i].modesourceName;
    					document.getElementById("parentmodeid").value=curLsinfomodes[i].parentmodeid;
    					document.getElementById("parentmodeName").value=curLsinfomodes[i].parentmodeName;
    					document.getElementById("createdate").value=curLsinfomodes[i].createdate;
    					document.getElementById("createemp").value=curLsinfomodes[i].createemp;
    					
    					var params = "strCurCompId="+document.getElementById("modesource").value;	
						params = params+"&strCurModeId="+document.getElementById("modeid").value;		
    	    			var requestUrl ="bc013/loadSysModesinfo.action"; 
						var responseMethod="loadSysModesinfoMessage";	
						sendRequestForParams_p(requestUrl,responseMethod,params );
    				}
    			}
    		}
    	}
    }
    
    function loadSysModesinfoMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
    		curLsProjectinfo=$.extend(true, {},{Rows: responsetext.lsProjectinfo,Total: responsetext.lsProjectinfo.length});
 
	   		searchProjectInfos();
	   		curLsGoodsinfo=$.extend(true, {},{Rows: responsetext.lsGoodsinfo,Total: responsetext.lsGoodsinfo.length});

	   		searchGoodsInfos();
	   		
	   		if(responsetext.lsCompwarehouses!=null && responsetext.lsCompwarehouses.length>0 )
	   		{
		   		clearOption("goodswarehouse");
		   		addOption("","",document.getElementById("goodswarehouse"));
		        for(var i=0;i<responsetext.lsCompwarehouses.length;i++)
				{
						addOption(responsetext.lsCompwarehouses[i].id.warehouseno,responsetext.lsCompwarehouses[i].warehousename,document.getElementById("goodswarehouse"));
				}
			}
	   		lsCardType=responsetext.lsCardtypeinfo;
	   		commoninfodivfive.columns[0].editor.data=showcardruleDetial();
	   		curLsCardTypeinfo=$.extend(true, {},{Rows: responsetext.lsCardtypeinfo,Total: responsetext.lsCardtypeinfo.length});
			
	   		searchCardTypeInfos();
	   	}catch(e){alert(e.message);}
    }
    
   function itemclick_mode(item)
   {
    	try
    	{
	    	 $.ligerDialog.confirm('确认 '+curCompid+' 系统模板资料 '+item.text+' 操作', function (result)
		     {
		     		if( result==true)
            		{
            			if(item.text=="增加")
			        	{
			        	 	addRecord();
			        	}
			        	else if(item.text=="保存")
			        	{
			        	 	editCurRecord();
			        	} 
			        	else if(item.text=="删除")
			        	{
			        	 	deleteRecord();
			        	} 		
			        }
		     });  
    	}
    	catch(e){alert(e.message);}
    }
    
    function addRecord()
    {
    	if(parent.hasFunctionRights( "BC013",  "UR_MODIFY")!=true)
	    {
	       	$.ligerDialog.warn("该用户没有编辑权限,请确认!");
	    }
	    else
	    {
	       	var row = commondetial.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commondetial.addRow({ 
				                bmodeid: "",
				                modename: "",
				                modesource: "",
				                parentmodeid: "",       
				                strModeTypeText: "项目模板"
				            }, row, false);
			addoption();
			var params = "strCurCompId="+curCompid;	
			params = params+"&strCurModeType="+document.getElementById("modetype").value;		
    	    var requestUrl ="bc013/loadParentMode.action"; 
			var responseMethod="loadParentModeMessage";	
	
			sendRequestForParams_p(requestUrl,responseMethod,params );
			
		}
    }
    function loadParentModeMessage(request)
    {
	   	try
	    {
	        var responsetext = eval("(" + request.responseText + ")");
	    	if(responsetext.lsparentModes!=null && responsetext.lsparentModes.length>0)
	    	{
	    		document.getElementById("parentmodeName").style.display="none";
	    		var modetypeDate=JSON.parse(loadCommoninforDate(responsetext.lsparentModes));
	    		modetypeManager = $("#parentmodeid").ligerComboBox({ data: modetypeDate, isMultiSelect: false,valueFieldID: 'factparentmodeid',width:'160',selectBoxHeight:'220'});	
				
	    	}
		   		
		}catch(e){alert(e.message);}
    }
    
    function loadCommoninforDate(lsparentModes)
	{
		var returnValue='';
		var key="";
		var value="";
		for(var i=0;i<lsparentModes.length;i++)
		{
			if(returnValue!='')
			{
				returnValue=returnValue+',';
			}
			else
			{
				returnValue=returnValue+'[';
			}
			key = lsparentModes[i].bmodeid;
			value = lsparentModes[i].modename;
			returnValue=returnValue+'{"id": "'+key+'","text": "'+value+'"}';
			
		}
		if(returnValue!='')
			returnValue=returnValue+']';	
		return 	returnValue;
	}
    //---------验证模板类型,自动生成父类模板-----Start
    function validateModetype(obj)
    {
    	if(obj.value!="")
    	{
    		var params = "strCurCompId="+curCompid;	
			params = params+"&strCurModeType="+obj.value;		
    	    var requestUrl ="bc013/loadParentMode.action"; 
			var responseMethod="loadParentModeMessage";	
			sendRequestForParams_p(requestUrl,responseMethod,params );
    	}
    }
    //---------验证模板类型,自动生成父类模板-----End
    
    //---------验证模板编号-------------------Start
    function validateModeId(obj)
    {
    	if(obj.value!="")
    	{
    		var params = "strCurModeId="+obj.value;		
    	    var requestUrl ="bc013/validateModeId.action"; 
			var responseMethod="validateModeIdMessage";	
			sendRequestForParams_p(requestUrl,responseMethod,params );
    	}
    }
    function validateModeIdMessage(request)
    {
	   	try
	    {
	        var responsetext = eval("(" + request.responseText + ")");
	    	if(checkNull(responsetext.strMessage !=""))
	    	{
	    		$.ligerDialog.warn(responsetext.strMessage);
	    		document.getElementById("modeid").value="";
	    	}
		   		
		}catch(e){alert(e.message);}
    }
    //---------验证模板编号-------------------End
   
    //-------------保存当前模板----------------Start
    function editCurRecord()
    {
    	try
    	{

	    	if(parent.hasFunctionRights( "BC013",  "UR_POST")!=true)
	        {
	        	$.ligerDialog.warn("该用户没有保存权限,请确认!");
	        	return;
	        }
	        if(fromValidate.element($("#modeid"))==false ||	fromValidate.element($("#modename"))==false )
			{
				$.ligerDialog.error('填入信息有误,请确认!');
				return;
			}
    		var queryStringTmp=$('#modeForm').serialize();
			var requestUrl ="bc013/post.action";
			var params=queryStringTmp;
			var responseMethod="editMessage";			
			sendRequestForParams_p(requestUrl,responseMethod,params ); 
    	}catch(e){alert(e.message);}
    }
    function editMessage(request)
    {
    	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	$.ligerDialog.success("更新成功!");
	        	readoption();
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
    //-------------保存当前模板----------------End 
    
    
    //-------------删除当前模板----------------Start 
    function deleteRecord()
    {
       	if(parent.hasFunctionRights( "BC013",  "UR_DELETE")!=true)
        {
        		$.ligerDialog.warn("该用户没有删除权限,请确认!");
        		return;
        }
        var requestUrl ="bc013/delete.action";
        var params="strCurModeId="+document.getElementById("modeid").value;
		var responseMethod="deleteMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	
            
   	}
   	function deleteMessage(request)
    {
    	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		  commondetial.deleteSelectedRow();
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
    //-------------删除当前模板----------------End     
    function addoption()
    {
    		document.getElementById("modetype").disabled=false;
			document.getElementById("modeid").readOnly="";
			document.getElementById("modename").readOnly="";			
			document.getElementById("modetype").value=1;
			document.getElementById("modeid").value="";
			document.getElementById("modename").value="";
			document.getElementById("modesource").value=curCompid;
			document.getElementById("modesourceName").value=curCompname;
			document.getElementById("parentmodeid").value="";
			document.getElementById("parentmodeName").value="";
			document.getElementById("createdate").value="";
			document.getElementById("createemp").value="";
    }
    
    function readoption()
    {
    		document.getElementById("modetype").disabled=true;
			document.getElementById("modeid").readOnly="readOnly";
			document.getElementById("modename").readOnly="readOnly";			
			
    }
    //----------------------------项目模板-------------------------------Start
    
    function itemclick_project(item)
    {
    	try
    	{
	    	 $.ligerDialog.confirm('确认 '+curCompid+' 项目模板 '+item.text+' 操作', function (result)
		     {
		     		if( result==true)
            		{
            			if(item.text=="增加")
			        	{
			        	 	addProjectRecord();
			        	}
			        	else if(item.text=="保存")
			        	{
			        	 	editProjectCurRecord();
			        	} 
			        	else if(item.text=="删除")
			        	{
			        	 	deleteProjectRecord();
			        	}
			        	else if(item.text=="查询")
			        	{
			        	 	searchProjectInfos();
			        	} 		
			        }
		     });  
    	}
    	catch(e){alert(e.message);}
    }
    
    function loadSelecSecondDetialData(data, rowindex, rowobj)
    {
    	curProjectid=data.bprjno;
    	selfflag=data.selfFlag;
    	var params=" strProjectId="+curProjectid;
    	params = params+"&strCurCompId="+document.getElementById("modesource").value;	
    	params = params+"&strCurModeId="+document.getElementById("modeid").value;		
    	var requestUrl ="bc013/loadProjectinfo.action"; 
		var responseMethod="loadProjectinfoMessage";	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    function loadProjectinfoMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
    		var curProjectinfo=responsetext.curProjectinfo;
    		if(curProjectinfo!=null)
	   		{
	   			if(curProjectinfo.selfFlag==1)
	   			{
	   				document.getElementById("prjShowText").innerHTML="私有项目-可编辑";
	   				enableProjectInfo();
	   			}
	   			else
	   			{
	   				document.getElementById("prjShowText").innerHTML="继承项目-不可编辑";
	   				disableProjectInfo();
	   			}
	   			document.getElementById("prjno").value=curProjectinfo.id.prjno;
	   			document.getElementById("prjname").value=curProjectinfo.prjname;
	   			document.getElementById("prisource").value=curProjectinfo.id.prisource;
	   			editprjtypemanager.selectValue(curProjectinfo.prjtype);
    			//editprjreporttypemanager.selectValue(curProjectinfo.prjreporttype);	
    			document.getElementById("h_prjtype").value=curProjectinfo.prjtype;
    			
    			document.getElementById("prjreporttype_s").value=curProjectinfo.prjreporttype;
    			document.getElementById("ipaddl_s").value=curProjectinfo.ipddl;
    			document.getElementById("h_prjreporttype").value=curProjectinfo.prjreporttype;
    			document.getElementById("prjpricetype").value=checkNull(curProjectinfo.prjpricetype);
    			document.getElementById("saleprice").value=checkNull(curProjectinfo.saleprice);
    			document.getElementById("ysalecount").value=checkNull(curProjectinfo.ysalecount);
    			document.getElementById("ysaleprice").value=checkNull(curProjectinfo.ysaleprice);
    			document.getElementById("onecountprice").value=checkNull(curProjectinfo.onecountprice);
    			document.getElementById("onepageprice").value=checkNull(curProjectinfo.onepageprice);
    			document.getElementById("salelowprice").value=checkNull(curProjectinfo.salelowprice);
    			document.getElementById("memberprice").value=checkNull(curProjectinfo.memberprice);
    			document.getElementById("needhairflag").value=checkNull(curProjectinfo.needhairflag);
    			document.getElementById("useflag").value=checkNull(curProjectinfo.useflag);
    			document.getElementById("markflag").value=checkNull(curProjectinfo.markflag);
    			document.getElementById("saleflag").value=checkNull(curProjectinfo.saleflag);
    			document.getElementById("rateflag").value=checkNull(curProjectinfo.rateflag);
    			document.getElementById("prjsaletype").value=checkNull(curProjectinfo.prjsaletype);
    			document.getElementById("editflag").value=checkNull(curProjectinfo.editflag);
    			document.getElementById("morelongflag").value=checkNull(curProjectinfo.morelongflag);
    			document.getElementById("costprice").value=checkNull(curProjectinfo.costprice);
    			document.getElementById("kyjrate").value=checkNull(curProjectinfo.kyjrate);
    			document.getElementById("ktcrate").value=checkNull(curProjectinfo.ktcrate);
    			document.getElementById("lyjrate").value=checkNull(curProjectinfo.lyjrate);
    			document.getElementById("ltcrate").value=checkNull(curProjectinfo.ltcrate);
    			document.getElementById("prjabridge").value=checkNull(curProjectinfo.prjabridge);
    			document.getElementById("newcosttc").value=checkNull(curProjectinfo.newcosttc);
    			document.getElementById("oldcosttc").value=checkNull(curProjectinfo.oldcosttc);
    			document.getElementById("ipadname").value=checkNull(curProjectinfo.ipadname);
	   		}
	   		
	   		
	   	}catch(e){alert(e.message);}
    }
    
    function disableProjectInfo()
    {
    	//document.getElementById("prjno").readOnly="readOnly";
	   	document.getElementById("prjname").readOnly="readOnly";
	   //	document.getElementById("prisource").readOnly="readOnly";
	   	editprjtypemanager.setDisabled();
	   	//editprjreporttypemanager.setDisabled();
	   	
	   	document.getElementById("prjreporttype_s").disabled=true;
	   	document.getElementById("ipaddl_s").disabled=true;
    	document.getElementById("prjpricetype").disabled=true;
    	document.getElementById("saleprice").readOnly="readOnly";
    	document.getElementById("ysalecount").readOnly="readOnly";
    	document.getElementById("ysaleprice").readOnly="readOnly";
    	document.getElementById("onecountprice").readOnly="readOnly";
    	document.getElementById("onepageprice").readOnly="readOnly";
    	document.getElementById("memberprice").readOnly="readOnly";
    	document.getElementById("needhairflag").disabled=true;
    	document.getElementById("useflag").disabled=true;
    	document.getElementById("markflag").disabled=true; 
    	document.getElementById("saleflag").disabled=true;
    	document.getElementById("rateflag").disabled=true;
    	document.getElementById("prjsaletype").disabled=true;
    	document.getElementById("editflag").disabled=true;
    	document.getElementById("morelongflag").disabled=true;
    	document.getElementById("costprice").readOnly="readOnly";
    	document.getElementById("kyjrate").readOnly="readOnly";
    	document.getElementById("ktcrate").readOnly="readOnly";
    	document.getElementById("lyjrate").readOnly="readOnly";
    	document.getElementById("ltcrate").readOnly="readOnly";
    	document.getElementById("newcosttc").readOnly="readOnly";
    	document.getElementById("oldcosttc").readOnly="readOnly";
    	document.getElementById("ipadname").readOnly="readOnly";
    	document.getElementById("prjabridge").readOnly="readOnly";
    }
    function enableProjectInfo()
    {
    	//document.getElementById("prjno").readOnly="";
	   	document.getElementById("prjname").readOnly="";
	   	//document.getElementById("prisource").readOnly="";
	    editprjtypemanager.setEnabled();
	   	//editprjreporttypemanager.setEnabled();
	   	
	   	document.getElementById("prjreporttype_s").disabled=false;
	   	document.getElementById("ipaddl_s").disabled=false;
    	document.getElementById("prjpricetype").disabled=false;
    	document.getElementById("saleprice").readOnly="";
    	document.getElementById("ysalecount").readOnly="";
    	document.getElementById("ysaleprice").readOnly="";
    	document.getElementById("onecountprice").readOnly="";
    	document.getElementById("onepageprice").readOnly="";
    	document.getElementById("memberprice").readOnly="";
    	document.getElementById("needhairflag").disabled=false;
    	document.getElementById("useflag").disabled=false;
    	document.getElementById("markflag").disabled=false; 
    	document.getElementById("saleflag").disabled=false;
    	document.getElementById("rateflag").disabled=false;
    	document.getElementById("prjsaletype").disabled=false;
    	document.getElementById("editflag").disabled=false;
    	document.getElementById("morelongflag").disabled=false;
    	document.getElementById("costprice").readOnly="";
    	document.getElementById("kyjrate").readOnly="";
    	document.getElementById("ktcrate").readOnly="";
    	document.getElementById("lyjrate").readOnly="";
    	document.getElementById("ltcrate").readOnly="";
    	document.getElementById("newcosttc").readOnly="";
    	document.getElementById("oldcosttc").readOnly="";
    	document.getElementById("ipadname").readOnly="";
    	document.getElementById("prjabridge").readOnly="";
    }
    function clearProjectInfo()
    {
    	document.getElementById("prjno").value="";
	   	document.getElementById("prjname").value="";
	   	document.getElementById("prisource").value="";
	   	editprjtypemanager.selectValue(0);
    	//editprjreporttypemanager.selectValue(0);	
    	
    	document.getElementById("prjreporttype_s").value="";
    	document.getElementById("ipaddl_s").value="";
    	document.getElementById("h_prjtype").value="";
    	document.getElementById("h_prjreporttype").value="";
    	document.getElementById("prjpricetype").value="";
    	document.getElementById("saleprice").value="";
    	document.getElementById("ysalecount").value="";
    	document.getElementById("ysaleprice").value="";
    	document.getElementById("onecountprice").value="";
    	document.getElementById("onepageprice").value="";
    	document.getElementById("salelowprice").value="";
    	document.getElementById("memberprice").value="";
    	document.getElementById("needhairflag").value=0;
    	document.getElementById("useflag").value=0;
    	document.getElementById("markflag").value=0;
    	document.getElementById("saleflag").value=0;
    	document.getElementById("rateflag").value=0;
    	document.getElementById("prjsaletype").value=0;
    	document.getElementById("editflag").value=0;
    	document.getElementById("morelongflag").value=0;
    	document.getElementById("costprice").value=0;
    	document.getElementById("kyjrate").value=0;
    	document.getElementById("ktcrate").value=0;
    	document.getElementById("lyjrate").value=0;
    	document.getElementById("ltcrate").value=0;
    	document.getElementById("prjabridge").value=0;
    }
    //------------------ 增加项目资料
    function addProjectRecord()
    {
    	if(parent.hasFunctionRights( "BC013",  "UR_MODIFY")!=true)
	    {
	       	$.ligerDialog.warn("该用户没有编辑权限,请确认!");
	    }
	    else
	    {
	    	 
    	     if(curModeType==1 && curModeid!="SPM" )
    	     {
    	     	$.ligerDialog.error("项目资料只能在SPM模板中增加,请确认!");
    	     	return ;
    	     }
	       	var row = commoninfodivsecond.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivsecond.addRow({ 
				                bprjmodeId: "",
				                bprjno: "",
				                prjname: "",
				                useflag: "2",       
				                saleflag: "2",   
				                saleprice: "0",
				                ysalecount: "0",
				                ysaleprice: "0",
				                markflag:"0"
				            }, row, false);
			clearProjectInfo()
			enableProjectInfo();
			document.getElementById("prjShowText").innerHTML="私有项目-可编辑";
			document.getElementById("prjno").readOnly="";
			document.getElementById("prisource").value=document.getElementById("modesource").value;	
			document.getElementById("saleflag").value=1;
			document.getElementById("useflag").value=1;
		}
    }
    //------------------ 保存项目资料
    function editProjectCurRecord()
    {
    	try
        {
  
	    	if(parent.hasFunctionRights( "BC013",  "UR_POST")!=true)
		    {
		        	$.ligerDialog.warn("该用户没有保存权限,请确认!");
		        	return;
		    }
			if(document.getElementById("prjno").value=="")
			{
		        	$.ligerDialog.warn("项目编号不能为空,请确认!");
		        	return;
		    }
		     if(document.getElementById("prjname").value=="")
			 {
		        	$.ligerDialog.warn("项目名称不能为空,请确认!");
		        	return;
		     }
		    
		     document.getElementById("h_prjtype").value=$("#factprjtype").val()
    		 //document.getElementById("h_prjreporttype").value=$("#factprjreporttype").val()
    		 document.getElementById("h_prjreporttype").value=document.getElementById("prjreporttype_s").value;
		     document.getElementById("ipddl").value=document.getElementById("ipaddl_s").value;
	         var queryStringTmp=$('#projectForm').serialize();
	         queryStringTmp=queryStringTmp.replace(/\+/g," ");
			 var requestUrl ="bc013/postProjectInfo.action";
			 var params=queryStringTmp;
			 params = params+"&strCurModeId="+document.getElementById("modeid").value;		
			 var responseMethod="postProjectInfoMessage";	
			 sendRequestForParams_p(requestUrl,responseMethod,params ); 
		}
		catch(e){alert(e.message);}
    }
    function postProjectInfoMessage(request)
    {
    	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	$.ligerDialog.success("更新成功!");
	        	disableProjectInfo();
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
    //------------------ 删除项目资料
    function deleteProjectRecord()
    {
       	if(parent.hasFunctionRights( "BC013",  "UR_DELETE")!=true)
        {
        		$.ligerDialog.warn("该用户没有删除权限,请确认!");
        		return;
        }
        if(checkNull(selfflag)!=1)
        {
        		$.ligerDialog.warn("没有权限删除继承项目,请确认!");
        		return;
        }
        
        var requestUrl ="bc013/deleteProjectInfo.action";
        var params="strProjectId="+curProjectid;
        params = params+"&strCurModeId="+document.getElementById("modeid").value;
        params = params+"&strProjectSource="+document.getElementById("modesource").value;
		var responseMethod="deleteProjectInfoMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	
            
   	}
   	function deleteProjectInfoMessage(request)
    {
    	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		  commoninfodivsecond.deleteSelectedRow();
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
    function validateProjectId(obj)
    {
    	if(obj.value!="")
    	{
    		var requestUrl ="bc013/validateProjectinfo.action";
    		var params="strProjectId="+obj.value;
        	params = params+"&strCurModeId="+document.getElementById("modeid").value;
        	params = params+"&strCurCompId="+document.getElementById("modesource").value;
        	var responseMethod="validateProjectinfoMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params ); 
    	}
    }
    function validateProjectinfoMessage(request)
    {
    	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)!="")
	       	{	       		 
	       		  $.ligerDialog.warn(strMessage);
	       		  document.getElementById("prjno").value="";
	       	}
	       	
        }
		catch(e)
		{
				alert(e.message);
		}
    }
     //------------------ 查询项目资料
    function searchProjectInfos()
    {
    	 commoninfodivsecond.options.data = $.extend(true, {}, curLsProjectinfo);
    	 commoninfodivsecond.loadData(f_getProjects());
         if(commoninfodivsecond.rows.length>0)
         {
         	commoninfodivsecond.select(0);
         }
    }
    function f_getProjects()
    {
    	try
    	{
            if (!commoninfodivsecond) return null;
            var clause = function (rowdata, rowindex)
            {
                var keyprjtype =$("#factsearchPrjType").val();
             	var keyprjreporttype =$("#factsearchPrjReportType").val();
             	var keyPrjNo =$("#searchPrjNo").val();
             	var keyPrjName =$("#searchPrjName").val();
             	var keyState =$("#searchPrjState").val();
             	if(keyPrjNo!="")
             	{
             		return (rowdata.bprjno==keyPrjNo)
             	}
             	else if(keyPrjName!="")
             	{
             		return (rowdata.prjname.indexOf(keyPrjName)>-1)
             	}
             	else
             	{
	             	if(keyprjtype=="" && keyprjreporttype=="")
	             		return (rowdata.useflag==keyState);
	             	else if(keyprjtype=="")
	             		return (rowdata.prjreporttype==keyprjreporttype && rowdata.useflag==keyState )
	             	else if(keyprjreporttype=="")
	               		return (rowdata.prjtype==keyprjtype && rowdata.useflag==keyState )
	               	else 
	              		return (rowdata.prjtype==keyprjtype  && rowdata.prjreporttype==keyprjreporttype && rowdata.useflag==keyState)
              	}
                
            };
            return clause; 
        }
    	catch(e){alert(e.message);}
    }
    
    
  //----------------------------项目模板-------------------------------End
  
  //----------------------------产品模板-------------------------------Start
    
    function itemclick_goods(item)
    {
    	try
    	{
	    	 $.ligerDialog.confirm('确认 '+curCompid+' 产品模板 '+item.text+' 操作', function (result)
		     {
		     		if( result==true)
            		{
            			if(item.text=="增加")
			        	{
			        	 	addGoodsRecord();
			        	}
			        	else if(item.text=="保存")
			        	{
			        	 	editGoodsCurRecord();
			        	} 
			        	else if(item.text=="删除")
			        	{
			        	 	deleteGoodsRecord();
			        	}
			        	else if(item.text=="查询")
			        	{
			        	 	searchGoodsInfos();
			        	} 		
			        }
		     });  
    	}
    	catch(e){alert(e.message);}
    }
    
    function loadSelecThirthDetialData(data, rowindex, rowobj)
    {
    	curGoodsid=data.bgoodsno;
    	selfflag=data.selfFlag;
    	var params=" strGoodstId="+curGoodsid;
    	params = params+"&strCurCompId="+document.getElementById("modesource").value;	
    	params = params+"&strCurModeId="+document.getElementById("modeid").value;		
    	var requestUrl ="bc013/loadGoodsinfo.action"; 
		var responseMethod="loadGoodsinfoMessage";	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    function loadGoodsinfoMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
    		var curGoodsinfo=responsetext.curGoodsinfo;
    		if(curGoodsinfo!=null)
	   		{
	   			if(curGoodsinfo.selfFlag==1)
	   			{
	   				document.getElementById("goodsShowText").innerHTML="私有项目-可编辑";
	   				enableGoodsInfo();
	   			}
	   			else
	   			{
	   				document.getElementById("goodsShowText").innerHTML="继承项目-不可编辑";
	   				disableGoodsInfo();
	   			}
	   			document.getElementById("goodsno").value=curGoodsinfo.id.goodsno;
	   			document.getElementById("goodsbarno").value=checkNull(curGoodsinfo.goodsbarno);
	   			document.getElementById("goodsname").value=curGoodsinfo.goodsname;
	   			document.getElementById("goodssource").value=curGoodsinfo.id.goodssource;
	   			editgoodstypemanager.selectValue(curGoodsinfo.goodstype);
    			//editgoodsreporttypemanager.selectValue(curGoodsinfo.goodspricetype);	
    			document.getElementById("h_goodstype").value=curGoodsinfo.goodstype;
    			document.getElementById("h_goodspricetype").value=curGoodsinfo.goodspricetype;
    			document.getElementById("goodsappsource").value=checkNull(curGoodsinfo.goodsappsource);
    			
			
    			document.getElementById("goodswarehouse").value=checkNull(curGoodsinfo.goodswarehouse);
    			document.getElementById("goodssupplier").value=checkNull(curGoodsinfo.goodssupplier);
    			//goodscostunitmanager.selectValue(curGoodsinfo.costunit);
   				//goodssaleunitmanager.selectValue(curGoodsinfo.saleunit);
   				//goodspurchaseunitmanager.selectValue(curGoodsinfo.purchaseunit);
   				
   				document.getElementById("h_costunit").value=checkNull(curGoodsinfo.costunit);
   				document.getElementById("h_saleunit").value=checkNull(curGoodsinfo.saleunit);
   				document.getElementById("h_purchaseunit").value=checkNull(curGoodsinfo.purchaseunit);
   				
   				document.getElementById("goodsuniquebar").value=checkNull(curGoodsinfo.goodsuniquebar);
    			document.getElementById("goodsformat").value=checkNull(curGoodsinfo.goodsformat);
    			document.getElementById("purchaseprice").value=checkNull(curGoodsinfo.purchaseprice);
    			document.getElementById("minordercount").value=checkNull(curGoodsinfo.minordercount);
    			document.getElementById("costamtbysale").value=checkNull(curGoodsinfo.costamtbysale);
    			document.getElementById("standprice").value=checkNull(curGoodsinfo.standprice);
    			document.getElementById("storesalseprice").value=checkNull(curGoodsinfo.storesalseprice);
    			document.getElementById("lowstock").value=checkNull(curGoodsinfo.lowstock);
    			document.getElementById("heightstock").value=checkNull(curGoodsinfo.heightstock);
    			document.getElementById("appflag").value=checkNull(curGoodsinfo.appflag);
    			document.getElementById("goodsuseflag").value=checkNull(curGoodsinfo.useflag);
    			document.getElementById("goodsusetype").value=checkNull(curGoodsinfo.goodsusetype);
    			document.getElementById("stopdate").value=checkNull(curGoodsinfo.stopdate);
    			document.getElementById("stopmark").value=checkNull(curGoodsinfo.stopmark);
    			document.getElementById("proddate").value=checkNull(curGoodsinfo.proddate);
    			document.getElementById("goodsabridge").value=checkNull(curGoodsinfo.goodsabridge);
    			document.getElementById("bindgoodsno").value=checkNull(curGoodsinfo.bindgoodsno);
    			document.getElementById("enablecompany").value=(checkNull(curGoodsinfo.enablecompany)==""?"0":checkNull(curGoodsinfo.enablecompany));
    			document.getElementById("goodscompany").value=checkNull(curGoodsinfo.goodscompany);
    			document.getElementById("enablebarcode").value=(checkNull(curGoodsinfo.enablebarcode)==""?"1":checkNull(curGoodsinfo.enablebarcode));
    			
	   		}
	   		
	   		
	   	}catch(e){alert(e.message);}
    }
    
    function disableGoodsInfo()
    {
    	document.getElementById("goodsname").readOnly="readOnly";
    		document.getElementById("goodsname").readOnly="readOnly";
	   	editgoodstypemanager.setDisabled();
	   	document.getElementById("h_goodspricetype").disabled=true;
    	document.getElementById("goodsappsource").disabled=true;
    	document.getElementById("goodswarehouse").readOnly="readOnly";
    	document.getElementById("goodssupplier").readOnly="readOnly";
  		document.getElementById("goodsbarno").readOnly="readOnly";
    	document.getElementById("h_costunit").disabled=true;
    	document.getElementById("h_saleunit").disabled=true;
    	document.getElementById("h_purchaseunit").disabled=true;
    	document.getElementById("goodsformat").readOnly="readOnly";
    	document.getElementById("purchaseprice").readOnly="readOnly";
    	document.getElementById("minordercount").readOnly="readOnly";
    	document.getElementById("costamtbysale").readOnly="readOnly";
    	document.getElementById("standprice").readOnly="readOnly";
    	document.getElementById("storesalseprice").readOnly="readOnly";
    	document.getElementById("lowstock").readOnly="readOnly";
    	document.getElementById("heightstock").readOnly="readOnly";
    	document.getElementById("appflag").disabled=true;
    	document.getElementById("goodsuseflag").disabled=true;
    	document.getElementById("goodsusetype").disabled=true;
    	document.getElementById("stopdate").readOnly="readOnly";
    	document.getElementById("stopmark").readOnly="readOnly";
    	document.getElementById("goodsabridge").readOnly="readOnly";
    	document.getElementById("bindgoodsno").readOnly="readOnly";
    	document.getElementById("enablecompany").disabled=true;
    	document.getElementById("goodscompany").readOnly="readOnly";
    	document.getElementById("enablebarcode").disabled=true;
    }
    function enableGoodsInfo()
    {
    	document.getElementById("goodsname").readOnly="";
    	document.getElementById("goodsbarno").readOnly="";
	   	editgoodstypemanager.setEnabled();
	   	document.getElementById("h_goodspricetype").disabled=false;
    	document.getElementById("goodsappsource").disabled=false;
    	document.getElementById("goodswarehouse").readOnly="";
    	document.getElementById("goodssupplier").readOnly="";
    	document.getElementById("h_costunit").disabled=false;
    	document.getElementById("h_saleunit").disabled=false;
    	document.getElementById("h_purchaseunit").disabled=false;
    	document.getElementById("proddate").readOnly="";
    	document.getElementById("goodsformat").readOnly="";
    	document.getElementById("purchaseprice").readOnly="";
    	document.getElementById("minordercount").readOnly="";
    	document.getElementById("costamtbysale").readOnly="";
    	document.getElementById("standprice").readOnly="";
    	document.getElementById("storesalseprice").readOnly="";
    	document.getElementById("lowstock").readOnly="";
    	document.getElementById("heightstock").readOnly="";
    	document.getElementById("appflag").disabled=false;
    	document.getElementById("goodsuseflag").disabled=false;
    	document.getElementById("goodsusetype").disabled=false;
    	document.getElementById("stopdate").readOnly="";
    	document.getElementById("stopmark").readOnly="";
    	document.getElementById("goodsabridge").readOnly="";
    	document.getElementById("bindgoodsno").readOnly="";
    	document.getElementById("enablecompany").disabled=false;
    	document.getElementById("goodscompany").readOnly="";
    	document.getElementById("enablebarcode").disabled=false;
    }
    function clearGoodsInfo()
    {
    			document.getElementById("goodsno").value="";
    			document.getElementById("goodsbarno").value="";
	   			document.getElementById("goodsname").value="";
	   			document.getElementById("goodssource").value="";
	   			editgoodstypemanager.selectValue(0);
	   		
	   			document.getElementById("h_goodspricetype").value="";
    			//editgoodsreporttypemanager.selectValue(0);	
    			document.getElementById("h_goodstype").value="";
    			document.getElementById("h_goodspricetype").value="";
    			document.getElementById("goodsappsource").value="";
    			document.getElementById("goodswarehouse").value="";
    			document.getElementById("goodssupplier").value="";
    			document.getElementById("h_costunit").value="";
    			document.getElementById("h_saleunit").value="";
    			document.getElementById("h_purchaseunit").value="";
    			//goodscostunitmanager.selectValue("");
   				//goodssaleunitmanager.selectValue("");
   				//goodspurchaseunitmanager.selectValue("");
   				document.getElementById("goodssource").value="";
    			document.getElementById("goodsuniquebar").value="";
    			document.getElementById("goodsformat").value="";
    			document.getElementById("purchaseprice").value="";
    			document.getElementById("minordercount").value="";
    			document.getElementById("costamtbysale").value="";
    			document.getElementById("standprice").value="";
    			document.getElementById("storesalseprice").value="";
    			document.getElementById("lowstock").value="";
    			document.getElementById("heightstock").value="";
    			document.getElementById("appflag").value="";
    			document.getElementById("goodsuseflag").value="";
    			document.getElementById("stopdate").value="";
    			document.getElementById("stopmark").value="";
    			document.getElementById("bindgoodsno").value="";
    			document.getElementById("goodscompany").value="";
    }

    function addGoodsRecord()
    {
    	if(parent.hasFunctionRights( "BC013",  "UR_MODIFY")!=true)
	    {
	       	$.ligerDialog.warn("该用户没有编辑权限,请确认!");
	    }
	    else
	    {
	       	var row = commoninfodivthirth.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivthirth.addRow({ 
				                bgoodsmodeid: "",
				                bgoodsno: "",
				                goodsname: "",
				                saleunit: "",       
				                goodsformat: "",   
				                standprice: "0",
				                bgoodssource: "0",
				                selfFlag: "0"
				            }, row, false);
			clearGoodsInfo()
			enableGoodsInfo();
			document.getElementById("goodsShowText").innerHTML="私有项目-可编辑";
			document.getElementById("goodsno").readOnly="";
			document.getElementById("goodssource").value=document.getElementById("modesource").value;	
	
		}
    }

    function editGoodsCurRecord()
    {
    	try
        {
  
	    	if(parent.hasFunctionRights( "BC013",  "UR_POST")!=true)
		    {
		        	$.ligerDialog.warn("该用户没有保存权限,请确认!");
		        	return;
		    }
			if(document.getElementById("goodsno").value=="")
			{
		        	$.ligerDialog.warn("产品编号不能为空,请确认!");
		        	return;
		    }
		     if(document.getElementById("goodsname").value=="")
			 {
		        	$.ligerDialog.warn("产品名称不能为空,请确认!");
		        	return;
		     }
		     if(document.getElementById("goodssupplier").value!="")
			 {
		    	 if($("#bindgoodsno").val()==""){
		    		 $.ligerDialog.warn("供应商产品必须绑定编码,请确认!");
		    		 return;
		    	 }
		     }
		     document.getElementById("h_goodstype").value=$("#factgoodstype").val()
    		// document.getElementById("h_goodspricetype").value=$("#factgoodspricetype").val()
    		// document.getElementById("h_costunit").value=$("#factcostunit").val()
    		// document.getElementById("h_saleunit").value=$("#factsaleunit").val()
    		// document.getElementById("h_purchaseunit").value=$("#factpurchaseunit").val()
	         var queryStringTmp=$('#GoodsForm').serialize();
	         queryStringTmp=queryStringTmp.replace(/\+/g," ");
			 var requestUrl ="bc013/postGoodsInfo.action";
			 var params=queryStringTmp;
			 params = params+"&strCurModeId="+document.getElementById("modeid").value;		
			 var responseMethod="postGoodsInfoMessage";	
			 sendRequestForParams_p(requestUrl,responseMethod,params ); 
		}
		catch(e){alert(e.message);}
    }
    function postGoodsInfoMessage(request)
    {
    	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	$.ligerDialog.success("更新成功!");
	        	disableGoodsInfo();
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

    function deleteGoodsRecord()
    {
       	if(parent.hasFunctionRights( "BC013",  "UR_DELETE")!=true)
        {
        		$.ligerDialog.warn("该用户没有删除权限,请确认!");
        		return;
        }
        if(checkNull(selfflag)!=1)
        {
        		$.ligerDialog.warn("没有权限删除继承项目,请确认!");
        		return;
        }
        
        var requestUrl ="bc013/deleteGoodsInfo.action";
        var params="strGoodstId="+curGoodsid;
        params = params+"&strCurModeId="+document.getElementById("modeid").value;
        params = params+"&strGoodsSource="+document.getElementById("modesource").value;
		var responseMethod="deleteGoodsInfoMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	
            
   	}
   	function deleteGoodsInfoMessage(request)
    {
    	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		  commoninfodivthirth.deleteSelectedRow();
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
    function validateGoodsId(obj)
    {
    	if(obj.value!="")
    	{
    		var requestUrl ="bc013/validateGoodsinfo.action";
    		var params="strGoodstId="+obj.value;
        	params = params+"&strCurModeId="+document.getElementById("modeid").value;
        	params = params+"&strCurCompId="+document.getElementById("modesource").value;
        	var responseMethod="validateGoodsinfoMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params ); 
    	}
    }
    function validateGoodsinfoMessage(request)
    {
    	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)!="")
	       	{	       		 
	       		  $.ligerDialog.warn(strMessage);
	       		  document.getElementById("goodsno").value="";
	       	}
	       	
        }
		catch(e)
		{
				alert(e.message);
		}
    }
     //------------------ 查询项目资料
    function searchGoodsInfos()
    {
    	 commoninfodivthirth.options.data = $.extend(true, {}, curLsGoodsinfo);
     	 commoninfodivthirth.loadData(f_getGoods());
     	 if(commoninfodivthirth.rows.length>0)
         {
         	commoninfodivthirth.select(0);
         }
    }
    function f_getGoods()
    {
    	try
    	{
            if (!commoninfodivthirth) return null;
            var clause = function (rowdata, rowindex)
            {
                var keygoodstype =$("#factsearchGoodsType").val()
             	//var keygoodsreporttype =$("#factsearchGoodsReportType").val()
             	var keygoodsreporttype=document.getElementById("searchGoodsReportType").value;
             	var keygoodsNo =$("#searchGoodsNo").val()
             	if(keygoodsNo!="")
             	{
             		return (rowdata.bgoodsno==keygoodsNo)
             	}
             	else
             	{
	             	if(keygoodstype=="" && keygoodsreporttype=="")
	             		return true;
	             	else if(keygoodstype=="")
	             		return (rowdata.goodspricetype==keygoodsreporttype)
	             	else if(keygoodsreporttype=="")
	               		return (rowdata.goodstype==keygoodstype  )
	               	else 
	              		return (rowdata.goodstype==keygoodstype  && rowdata.goodspricetype==keygoodsreporttype)
              	}
                
            };
            return clause; 
        }
    	catch(e){alert(e.message);}
    }
    
    
  //----------------------------产品模板-------------------------------End
  
  
   //----------------------------卡类型模板-------------------------------Start
    
    function itemclick_cardtype(item)
    {
    	try
    	{
	    	 $.ligerDialog.confirm('确认 '+curCompid+' 卡类型模板 '+item.text+' 操作', function (result)
		     {
		     		if( result==true)
            		{
            			if(item.text=="增加")
			        	{
			        	 	addCardTypeRecord();
			        	}
			        	else if(item.text=="保存")
			        	{
			        	 	editCardTypeCurRecord();
			        	} 
			        	else if(item.text=="删除")
			        	{
			        	 	deleteCardTypeRecord();
			        	}
			        	else if(item.text=="查询")
			        	{
			        	 	searchCardTypeInfos();
			        	} 		
			        }
		     });  
    	}
    	catch(e){alert(e.message);}
    }
    
    function loadSelecForthDetialData(data, rowindex, rowobj)
    {
    	curCardtypeid=data.bcardtypeno;
    	selfflag=data.selfFlag;
    	var params=" strCardTypetId="+curCardtypeid;
    	params = params+"&strCurCompId="+document.getElementById("modesource").value;	
    	params = params+"&strCurModeId="+document.getElementById("modeid").value;		
    	var requestUrl ="bc013/loadCardTypeinfo.action"; 
		var responseMethod="loadCardTypeinfoMessage";	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    function loadCardTypeinfoMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
    		var curCardtypeinfo=responsetext.curCardtypeinfo;
    		if(curCardtypeinfo!=null)
	   		{
	   			if(curCardtypeinfo.selfFlag==1)
	   			{
	   				document.getElementById("cardtypeShowText").innerHTML="私有卡种-可编辑";
	   				enableCardTypeInfo();
	   			}
	   			else
	   			{
	   				document.getElementById("cardtypeShowText").innerHTML="继承卡种-不可编辑";
	   				disableCardTypeInfo();
	   			}
	   			document.getElementById("cardtypeno").value=checkNull(curCardtypeinfo.id.cardtypeno);
    			document.getElementById("cardtypesource").value=checkNull(curCardtypeinfo.id.cardtypesource);
    			document.getElementById("cardtypename").value=checkNull(curCardtypeinfo.cardtypename);
    			document.getElementById("cardchiptype").value=checkNull(curCardtypeinfo.cardchiptype);
    			document.getElementById("carduselife").value=checkNull(curCardtypeinfo.carduselife);
    			document.getElementById("saletcvalue").value=checkNull(curCardtypeinfo.saletcvalue);
    			document.getElementById("saleyjvalue").value=checkNull(curCardtypeinfo.saleyjvalue);
    			document.getElementById("fillyjvalue").value=checkNull(curCardtypeinfo.fillyjvalue);
    			document.getElementById("filltcvalue").value=checkNull(curCardtypeinfo.filltcvalue);
    			document.getElementById("lowfillamt").value=checkNull(curCardtypeinfo.lowfillamt);
    			document.getElementById("lowopenamt").value=checkNull(curCardtypeinfo.lowopenamt);
    			document.getElementById("slaeproerate").value=checkNull(curCardtypeinfo.slaeproerate);
    			document.getElementById("slaegoodsrate").value=checkNull(curCardtypeinfo.slaegoodsrate);
    			document.getElementById("salegoodsflag").value=checkNull(curCardtypeinfo.salegoodsflag);
    			document.getElementById("changerule").value=checkNull(curCardtypeinfo.changerule);
    			document.getElementById("openflag").value=checkNull(curCardtypeinfo.openflag);
    			document.getElementById("fillflag").value=checkNull(curCardtypeinfo.fillflag);	
    			document.getElementById("changeflag").value=checkNull(curCardtypeinfo.changeflag);
    			document.getElementById("sendamtflag").value=checkNull(curCardtypeinfo.sendamtflag);
    			if(responsetext.lsCardchangerules!=null && responsetext.lsCardchangerules.length>0)
		   		{
		   			commoninfodivfive.options.data=$.extend(true, {},{Rows: responsetext.lsCardchangerules,Total: responsetext.lsCardchangerules.length});
	            	commoninfodivfive.loadData(true);            	
		   		}
		   		else
		   		{
		   			commoninfodivfive.options.data=$.extend(true, {},{Rows: null,Total:0});
	            	commoninfodivfive.loadData(true);      
		   		}
    
	   		}
	   		
	   		
	   	}catch(e){alert(e.message);}
    }
    
    function disableCardTypeInfo()
    {
    	document.getElementById("cardtypename").readOnly="readOnly";
    	document.getElementById("cardchiptype").disabled=true;
    	document.getElementById("carduselife").readOnly="readOnly";
    	document.getElementById("saletcvalue").readOnly="readOnly";

    	document.getElementById("saleyjvalue").readOnly="readOnly";
    	document.getElementById("fillyjvalue").readOnly="readOnly";
    	document.getElementById("filltcvalue").readOnly="readOnly";
    	document.getElementById("lowfillamt").readOnly="readOnly";
    	document.getElementById("lowopenamt").readOnly="readOnly";
    	document.getElementById("slaeproerate").readOnly="readOnly";
    	document.getElementById("slaegoodsrate").readOnly="readOnly";
    	document.getElementById("salegoodsflag").disabled=true;
    	document.getElementById("changerule").disabled=true;
    	document.getElementById("openflag").disabled=true;
    	document.getElementById("fillflag").disabled=true;
    	document.getElementById("changeflag").disabled=true;
    	document.getElementById("sendamtflag").disabled=true;
    }
    function enableCardTypeInfo()
    {
    	document.getElementById("cardtypename").readOnly="";
    	document.getElementById("cardchiptype").disabled=false;
    	document.getElementById("carduselife").readOnly="";
    	document.getElementById("saletcvalue").readOnly="";

    	document.getElementById("saleyjvalue").readOnly="";
    	document.getElementById("fillyjvalue").readOnly="";
    	document.getElementById("filltcvalue").readOnly="";
    	document.getElementById("lowfillamt").readOnly="";
    	document.getElementById("lowopenamt").readOnly="";
    	document.getElementById("slaeproerate").readOnly="";
    	document.getElementById("slaegoodsrate").readOnly="";
    	document.getElementById("salegoodsflag").disabled=false;
    	document.getElementById("changerule").disabled=false;
    	document.getElementById("openflag").disabled=false;
    	document.getElementById("fillflag").disabled=false;
    	document.getElementById("changeflag").disabled=false;
    	document.getElementById("sendamtflag").disabled=false;
    }
    function clearCardTypeInfo()
    {
    	document.getElementById("cardtypeno").value="";
    	document.getElementById("cardtypesource").value="";
    	document.getElementById("cardtypename").value="";
    	document.getElementById("cardchiptype").value="";
    	document.getElementById("carduselife").value="";
    	document.getElementById("saletcvalue").value="";
    	document.getElementById("saleyjvalue").value="";
    	document.getElementById("fillyjvalue").value="";
    	document.getElementById("filltcvalue").value="";
    	document.getElementById("lowfillamt").value="";
    	document.getElementById("lowopenamt").value="";
    	document.getElementById("slaeproerate").value="";
    	document.getElementById("salegoodsflag").value="";
    	document.getElementById("changerule").value="";
    	document.getElementById("openflag").value="";
    	document.getElementById("fillflag").value="";
    	document.getElementById("changeflag").value="";
    	document.getElementById("sendamtflag").value="";
    }

    function addCardTypeRecord()
    {
    	if(parent.hasFunctionRights( "BC013",  "UR_MODIFY")!=true)
	    {
	       	$.ligerDialog.warn("该用户没有编辑权限,请确认!");
	    }
	    else
	    {
	       	var row = commoninfodivfourth.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivfourth.addRow({ 
				                bcardtypemodeid: "",
				                bcardtypeno: "",
				                cardtypename: "",
				                lowopenamt: "",       
				                lowfillamt: "", 
				                cardtypesource: "0",
				                selfFlag: "0"
				            }, row, false);
			clearCardTypeInfo()
			enableCardTypeInfo();
			document.getElementById("cardtypeShowText").innerHTML="私有卡种-可编辑";
			document.getElementById("cardtypeno").readOnly="";
			document.getElementById("cardtypesource").value=document.getElementById("modesource").value;	
	
		}
    }

    function editCardTypeCurRecord()
    {
    	try
        {
  
	    	if(parent.hasFunctionRights( "BC013",  "UR_POST")!=true)
		    {
		        	$.ligerDialog.warn("该用户没有保存权限,请确认!");
		        	return;
		    }
			if(document.getElementById("cardtypeno").value=="")
			{
		        	$.ligerDialog.warn("卡种编号不能为空,请确认!");
		        	return;
		    }
		     if(checkNull(selfflag)!=1)
	        {
	        		$.ligerDialog.warn("没有权限保存继承项目,请确认!");
	        		return;
	        }
		     if(document.getElementById("cardtypename").value=="")
			 {
		        	$.ligerDialog.warn("卡种名称不能为空,请确认!");
		        	return;
		     }
		    
	         var queryStringTmp=$('#CardTypeForm').serialize();
	         queryStringTmp=queryStringTmp.replace(/\+/g," ");
			 var requestUrl ="bc013/postCardTypeInfo.action";
			 var params=queryStringTmp;
			 params = params+"&strCurModeId="+document.getElementById("modeid").value;	
			 var strJsonParam_five="";
			 var curjosnparam ="";
			 var needReplaceStr="";
			 for (var rowid in commoninfodivfive.records)
			 {
				 	var row =commoninfodivfive.records[rowid];
				 	curjosnparam=JSON.stringify(row);
					/*if(curjosnparam.indexOf("_id")>-1)
				  	{
				     	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				      	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				    }*/	            		   
				    if(strJsonParam_five!="")
				      	strJsonParam_five=strJsonParam_five+",";
				    strJsonParam_five= strJsonParam_five+curjosnparam;        		 
			 }	
			 if(strJsonParam_five!="")
			 {
			 	 params = params+"&strJsonParam=["+strJsonParam_five+"]";
			 }
			 var responseMethod="postCardTypeInfoMessage";	
			 	
			 sendRequestForParams_p(requestUrl,responseMethod,params ); 
		}
		catch(e){alert(e.message);}
    }
    function postCardTypeInfoMessage(request)
    {
    	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	$.ligerDialog.success("更新成功!");
	        	disableCardTypeInfo();
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

    function deleteCardTypeRecord()
    {
       	if(parent.hasFunctionRights( "BC013",  "UR_DELETE")!=true)
        {
        		$.ligerDialog.warn("该用户没有删除权限,请确认!");
        		return;
        }

        if(checkNull(selfflag)!=1)
        {
        		$.ligerDialog.warn("没有权限删除继承项目,请确认!");
        		return;
        }
        
        var requestUrl ="bc013/deleteCardTypeInfo.action";
        var params="strCardTypetId="+curCardtypeid;
        params = params+"&strCurModeId="+document.getElementById("modeid").value;
        params = params+"&strCardTypeSource="+document.getElementById("modesource").value;
		var responseMethod="deleteCardTypeInfoMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	
            
   	}
   	function deleteCardTypeInfoMessage(request)
    {
    	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		  commoninfodivfourth.deleteSelectedRow();
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
    function validateCareTypeId(obj)
    {
    	
    	try
		{
			if(obj.value!="")
	    	{
	    		var requestUrl ="bc013/validateCardTypeinfo.action";
	    		var params="strCardTypetId="+obj.value;
	        	params = params+"&strCurModeId="+document.getElementById("modeid").value;
	        	params = params+"&strCurCompId="+document.getElementById("modesource").value;
	        	var responseMethod="validateCardTypeinfoMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params ); 
	    	}
	     }
		catch(e)
		{
				alert(e.message);
		}
    }
    function validateCardTypeinfoMessage(request)
    {
    	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)!="")
	       	{	       		 
	       		  $.ligerDialog.warn(strMessage);
	       		  document.getElementById("cardtypeno").value="";
	       	}
	       	
        }
		catch(e)
		{
				alert(e.message);
		}
    }
     //------------------ 查询项目资料
    function searchCardTypeInfos()
    {
    	 commoninfodivfourth.options.data = $.extend(true, {}, curLsCardTypeinfo);
     	 commoninfodivfourth.loadData(f_getCardType());
     	 if(commoninfodivfourth.rows.length>0)
         {
         	commoninfodivfourth.select(0);
         }
    }
    function f_getCardType()
    {
    	try
    	{
            if (!commoninfodivthirth) return null;
            var clause = function (rowdata, rowindex)
            {
                var keycardtype =$("#factsearchCardTypeNo").val()
             	
             	if(keycardtype!="")
             	{
             		return (rowdata.bcardtypeno==keycardtype)
             	}
             	else
             	{
	             	return true;
              	}
                
            };
            return clause; 
        }
    	catch(e){alert(e.message);}
    }
    
    function itemclick_cardtyperule()
    {
    	var row = commoninfodivfive.getSelectedRow();
		commoninfodivfive.addRow({ 
				                tocardtypeno: "",
				                changeamt: 0
				            }, row, false);
    }
    
    //初始化转卡规则
	function showcardruleDetial()
	{
		try
		{
			var strJson = "";//'{ "name": "cxh", "sex": "man" }';
			var ccount=0;
			for( var i=0;i<lsCardType.length;i++)
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
					strJson=strJson+'{ "choose":"'+lsCardType[i].id.cardtypeno+'", "text": "'+lsCardType[i].cardtypename+'"}';
			}
			if(strJson!="")
			{
				strJson=strJson+']';
				return JSON.parse(strJson);
			}
			return null;
		}catch(e){alert(e.message)}
	}
	
	
	function hotKeyOfSelf(key)
		{
			if(key==13)//回车
			{
				hotKeyEnter();
			}
			else if(key==114)//F3
			{
				addRecord();
			}
			else if(key==115)//F4
			{
				 deleteCurRecord();
			}
			else if( key == 83 &&  event.altKey)
			{
				editCurRecord();
			}
	
		}
		
		function  hotKeyEnter()
		{
		
			try
			{
				window.event.keyCode=9; //tab
				window.event.returnValue=true;
				
			}
			catch(e){alert(e.message);}
				
		}   
		
		//下发项目资料
		function downProjectMode()
		{
			if(document.getElementById("targerModeId").value=="SPM")
			{
				$.ligerDialog.error("不能将产品下发到标准模板,请确认!");
        		return;
			}
			var requestUrl ="bc013/downProjectMode.action";
	    	var params="targerModeId="+document.getElementById("targerModeId").value;
	        params = params+"&strProjectId="+document.getElementById("prjno").value;
	        var responseMethod="downProjectModeMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params ); 
		}
		
	 	function downProjectModeMessage(request)
	    {
	    		var responsetext = eval("(" + request.responseText + ")");
		       	var strMessage=responsetext.strMessage;
		       	if(checkNull(strMessage)!="")
		       	{	       		 
		       		  $.ligerDialog.warn(strMessage);
		       	}
		        else
		        {
		        	$.ligerDialog.success("下发成功");
		        }	
	       
	    }
  //----------------------------卡类型模板-------------------------------End
    