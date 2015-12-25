
   	var compTree = null;//主明细	
   	var compTreeManager = null;//主明细	
   	var commoninfodivsecond=null;//员工明细
   	var curCompid="";
   	var curStaffid="";
   	var goodsWPDLData=JSON.parse(parent.loadCommonControlDate_select("WPDL",0));
   	var goodsWPDWData=JSON.parse(parent.loadCommonControlDate_select("WPDW",0));
   	var goodsWPTJData=JSON.parse(parent.loadCommonControlDate_select("WPTJ",0));
   	var chooseData = [{ choose: 1, text: '是' }, { choose: 2, text: '否'}];
   	var appsourceData = [{ choose: 0, text: '总仓库' }, { choose: 1, text: '供应商'}];
   	var curGoodsInfoDate=null;
   	var bc005layout=null;
    $(function ()
   	{
	   try
	   {
	   		  //布局
            bc005layout= $("#bc005layout").ligerLayout({ leftWidth: 270, allowBottomResize: false, allowLeftResize: false });
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
          	
        	commoninfodivsecond=$("#commoninfodivsecond").ligerGrid({
                columns: [
                { display: '产品编号', name: 'bgoodsno',  width: 80,align: 'left' , frozen: true},
                { display: '产品名称', name: 'goodsname', width:200,align: 'left' , frozen: true},
                { display: '产品大类', name: 'goodstype',  width: 60,align: 'left' ,
                	editor: { type: 'select', data: goodsWPDLData, valueField: 'choose',selectBoxHeight:'220'},
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("WPDL",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.goodstype)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
                { display: '统计分类', name: 'goodspricetype',  width: 70,align: 'left' ,
                editor: { type: 'select', data: goodsWPTJData, valueField: 'choose',selectBoxHeight:'220'},
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("WPTJ",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.goodspricetype)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
	            { display: '产品来源', 		name: 'goodsappsource',  width:60,align: 'left' , 
	                editor: { type: 'select', data: appsourceData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.goodsappsource == 0) return '总仓库';
	                      return '供应商';
	                }
	            },
	            { display: '总仓库', name: 'goodswarehouse', width:50,align: 'left'},
	            { display: '供应商', name: 'goodssupplier', width:50,align: 'left' },
	            { display: '消耗单位',name: 'costunit',  width:50,align: 'left' , 
	               editor: { type: 'select', data: goodsWPDWData, valueField: 'choose',selectBoxHeight:'220'},
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("WPDW",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.costunit)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
	            { display: '销售单位', 	name: 'saleunit',  width:50,align: 'left' , 
	               editor: { type: 'select', data: goodsWPDWData, valueField: 'choose',selectBoxHeight:'220'},
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("WPDW",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.saleunit)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
	            { display: '进货单位', 	name: 'purchaseunit',  width:50,align: 'left' , 
	                 editor: { type: 'select', data: goodsWPDWData, valueField: 'choose',selectBoxHeight:'220'},
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("WPDW",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.purchaseunit)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
	            { display: '规格', name: 'goodsformat', width:30,align: 'left' },
	            
	            { display: '标准价', name: 'standprice', width:40,align: 'left' },
	            { display: '门店价', name: 'storesalseprice', width:40,align: 'left' },
	            { display: '采购', 	name: 'appflag',  width:30,align: 'left' , 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.appflag == 1) return '启';
	                      return '禁';
	                }
	            },
	            { display: '停用', 	name: 'useflag',  width:30,align: 'left' , 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.useflag == 1) return '禁';
	                      return '启';
	                }
	            },
	            { display: '积分方式', name: 'pointtype', width:50,align: 'left' },
	            { display: '积分比率', name: 'pointvalue', width:70,align: 'left' }	            
                ],  pageSize:20, 
                data:null,      
                width: bc005layout.centerWidth*1+bc005layout.leftWidth*1-30,
                height:'95%',
                clickToEdit: false,   enabledEdit: false,  
                rownumbers:true,usePager:true, where : f_getWhere(),     
                onDblClickRow : function (data, rowindex, rowobj)
                {
                	$.ligerDialog.prompt('输入需要打印的条码','', function (yes,value) { if(yes) bc005_printbar(value,value); });
                },  
                toolbar: { items: [
                { text: 'Excel导出', click: itemclick_goodsInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/attibutes.gif' },
                { text: '<input type="radio" id="printtype1" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="printtype" value="1" />大机器<input type="radio" id="printtype2" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="printtype" value="2"/>小机器<input type="radio" id="printtype3" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="printtype" value="3" checked="true"/>单列纸大机器'} 
               ]
                }   
            });
            addOption("","",document.getElementById("searchGoodsType"));
           	var goodstypes=parent.gainCommonInfoByCode("WPTJ",0);
		    for(var i=0;i<goodstypes.length;i++)
			{
				addOption(goodstypes[i].bparentcodekey,goodstypes[i].parentcodevalue,document.getElementById("searchGoodsType"));
			}		
           
          	$("#pageloading").hide(); 
          	f_selectNode();
   		}catch(e){alert(e.message);}
    });
    
    
    function bc005_printbar(barcode1,barcode2)
	{
			var strValue="2";
			if(document.getElementById("printtype1").checked==true)
			{
				strValue="1";
			}
			Stand_CheckPrintControl();
			if(strValue=="2")
			{
				StandPrintControl.PRINT_INITA(0,0,"62mm","15mm","");
			
				StandPrintControl.ADD_PRINT_BARCODE("0.5mm","9.5mm","25.9mm","9.5mm","128Auto",barcode1);
				StandPrintControl.SET_PRINT_STYLEA(0,"FontSize",6);
				
				StandPrintControl.ADD_PRINT_BARCODE("0.5mm","40.5mm","25.9mm","9.5mm","128Auto",barcode2);
				StandPrintControl.SET_PRINT_STYLEA(0,"FontSize",6);	
			}	
			else if(strValue=="3"){
				StandPrintControl.PRINT_INITA(0,0,"62mm","15mm","");
				StandPrintControl.ADD_PRINT_BARCODE("1.3mm","0.1mm","25.9mm","9.5mm","128Auto",barcode1);
				StandPrintControl.SET_PRINT_STYLEA(0,"FontSize",9);
			}
			else
			{
					StandPrintControl.PRINT_INITA(0,0,"62mm","15mm","");
					StandPrintControl.ADD_PRINT_BARCODE("5mm","2.2mm","25.9mm","9.5mm","128Auto",barcode1);
					StandPrintControl.SET_PRINT_STYLEA(0,"FontSize",9);
					StandPrintControl.SET_PRINT_STYLEA(0,"Vorient",1);
					StandPrintControl.ADD_PRINT_BARCODE("5mm","33.2mm","25.9mm","9.5mm","128Auto",barcode2);
					StandPrintControl.SET_PRINT_STYLEA(0,"FontSize",9);
					StandPrintControl.SET_PRINT_STYLEA(0,"Vorient",1);
				
			}
			
			StandPrintControl.PRINT();
			
	}
   
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
       		var params = "strCurCompId="+note.data.id;				
     		var requestUrl ="bc005/loadGoodsInfos.action"; 
			var responseMethod="loadGoodsInfosMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params );
          }catch(e){alert(e.message);}
    }
    function loadGoodsInfosMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.lsGoodsinfoInfo!=null && responsetext.lsGoodsinfoInfo.length>0)
	   		{
	   			curGoodsInfoDate={Rows: responsetext.lsGoodsinfoInfo,Total: responsetext.lsGoodsinfoInfo.length};
	   			commoninfodivsecond.options.data=$.extend(true, {},curGoodsInfoDate);
            	commoninfodivsecond.loadData(true);            	
	   		}
	   		else
	   		{
	   			commoninfodivsecond.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivsecond.loadData(true);      
	   		}
	   		
	   	}catch(e){alert(e.message);}
    }
    
   
     // ------------------------操作用户 ----------------End
     // ------------------------查询信息 ----------------Start
     function f_searchStaffInfo()
     {
      commoninfodivsecond.options.data = $.extend(true, {}, curGoodsInfoDate);
     	
           commoninfodivsecond.loadData(f_getWhere());
     }
     function f_getWhere()
     {
     	
     	try
    	{
            if (!commoninfodivsecond) return null;
            var clause = function (rowdata, rowindex)
            {
            	  var key = $("#txtSearchKey").val();
                		
                if(document.getElementById("goodsState").value=="2")
                	return ((key=="" || rowdata.bgoodsno.indexOf(key) > -1 ||  rowdata.goodsname.indexOf(key) > -1 )
                	 && ( rowdata.goodspricetype==document.getElementById("searchGoodsType").value || document.getElementById("searchGoodsType").value=="") );
                else
                	return ((key=="" || rowdata.bgoodsno.indexOf(key) > -1 ||  rowdata.goodsname.indexOf(key) > -1)
                			 && checkNull(rowdata.useflag)*1==document.getElementById("goodsState").value*1
                			 && ( rowdata.goodspricetype==document.getElementById("searchGoodsType").value || document.getElementById("searchGoodsType").value=="") );
              
                
            };
            return clause; 
        }
    	catch(e){alert(e.message);}
     }
     
     function itemclick_goodsInfo(item)
     {
     	try
    	{
	    	 $.ligerDialog.confirm('确认 '+curCompid+' 产品资料 '+item.text+' 操作', function (result)
		     {
		     		if( result==true)
            		{
			          	if(parent.hasFunctionRights( "BC005",  "UR_PRINT")!=true)
			        	{
			        		 $.ligerDialog.warn("该用户没有到导出权限,请确认!");
			        		 return;
			        	}
			        }
		     });  
    	}
    	catch(e){alert(e.message);}
     }
     // ------------------------查询信息 ----------------End