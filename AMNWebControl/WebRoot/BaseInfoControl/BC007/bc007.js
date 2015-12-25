
   	var compTree = null;//主明细	
   	var compTreeManager = null;//主明细	
   	var commoninfodivsecond=null;//卡类型明细
   	var commoninfodivthirth=null;//转卡规则明细
   	var curCompid="";
   	var curStaffid="";
   	var cardYJIFData=JSON.parse(parent.loadCommonControlDate_select("YJIF",0));
   	var cardTCIFData=JSON.parse(parent.loadCommonControlDate_select("TCIF",0));
	var changeruleData = [{ choose: 1, text: '标准转卡' }, { choose: 0, text: '余额转卡'}];
   	var chooseData = [{ choose: 1, text: '是' }, { choose: 0, text: '否'}];
   	var curCardtypeInfoDate=null;
   	var bc007layout=null;

    $(function ()
   	{
	   try
	   {
	   		  //布局
            bc007layout= $("#bc007layout").ligerLayout({ leftWidth: 270,rightWidth: 245,  allowBottomResize: false, allowLeftResize: false });
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
                { display: '类别编号', name: 'bcardtypeno',  width: 60,align: 'left' , frozen: true},
                { display: '类别名称', name: 'cardtypename', width:100,align: 'left' , frozen: true},
               
	            { display: '标准售价', name: 'cardsaleprice',  width:50,align: 'left' ,minWidth: 60},
	            { display: '充值额度', name: 'lowfillamt', width:70,align: 'right' },
	            { display: '开卡额度', name: 'lowopenamt', width:70,align: 'right' },
	            { display: '销售疗程折扣', name: 'slaeproerate', width:80,align: 'right' },
	            { display: '购买物品', 	name: 'salegoodsflag',  width:50,align: 'left' , 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.salegoodsflag == 1) return '是';
	                      return '否';
	                }
	            },
	            { display: '允许开卡', 	name: 'openflag',  width:50,align: 'left' , 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.openflag == 1) return '是';
	                      return '否';
	                }
	            },
	            { display: '允许充值', 	name: 'fillflag',  width:50,align: 'left' , 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.fillflag == 1) return '是';
	                      return '否';
	                }
	            },
	            { display: '允许转卡', 	name: 'changeflag',  width:50,align: 'left' , 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.changeflag == 1) return '是';
	                      return '否';
	                }
	            },
	            { display: '转卡规则', 	name: 'changerule',  width:50,align: 'left' , 
	                editor: { type: 'select', data: changeruleData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.fillflag == 1) return '标准转卡';
	                      return '余额转卡';
	                }
	            },
	            { display: '售卡业绩', columns:
                [
		            { display: '方式', name: 'saleyjtype', width:60,align: 'left'  ,
	                	editor: { type: 'select', data: cardYJIFData, valueField: 'choose',selectBoxHeight:'220'},
		            	render: function (item)
		              	{
		              		var lsZw=parent.gainCommonInfoByCode("YJIF",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.saleyjtype)
								{	
									return lsZw[i].parentcodevalue;								
							    }
							}
		                    return '';
		                } 
		            },
		            { display: '比率', name: 'saleyjvalue', width:30,align: 'right' }
	            ]},
	            { display: '售卡提成', columns:
                [
		            { display: '方式', name: 'saletctype', width:60,align: 'left',
		             	editor: { type: 'select', data: cardTCIFData, valueField: 'choose',selectBoxHeight:'220'},
		            	render: function (item)
		              	{
		              		var lsZw=parent.gainCommonInfoByCode("TCIF",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.saletctype)
								{	
									return lsZw[i].parentcodevalue;								
							    }
							}
		                    return '';
		                } 
		            },
		         	{ display: '比率', name: 'saletcvalue', width:30,align: 'right' }
				]},
				{ display: '充值业绩', columns:
                [
		            { display: '方式', name: 'fillyjtype', width:60,align: 'left',
		             	editor: { type: 'select', data: cardYJIFData, valueField: 'choose',selectBoxHeight:'220'},
		            	render: function (item)
		              	{
		              		var lsZw=parent.gainCommonInfoByCode("YJIF",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.fillyjtype)
								{	
									return lsZw[i].parentcodevalue;								
							    }
							}
		                    return '';
		                } 
		            },
		            { display: '比率', name: 'fillyjvalue', width:30,align: 'right' }
	            ]},
	            { display: '充值提成', columns:
                [
		            { display: '方式', name: 'filltctype', width:60,align: 'left' ,
		             	editor: { type: 'select', data: cardTCIFData, valueField: 'choose',selectBoxHeight:'220'},
		            	render: function (item)
		              	{
		              		var lsZw=parent.gainCommonInfoByCode("TCIF",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.filltctype)
								{	
									return lsZw[i].parentcodevalue;								
							    }
							}
		                    return '';
		                } 
		            },
		            { display: '比率', name: 'filltcvalue', width:30,align: 'right' }
	           	]}
	           	
	            
                ],  pageSize:20, 
                data:null,      
                width: bc007layout.centerWidth*1+bc007layout.leftWidth*1+bc007layout.rightWidth*1-30,
                height:'95%',
                clickToEdit: false,   enabledEdit: false,  
                rownumbers:true,usePager:true, where : f_getWhere(),     
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                }, 
                toolbar: { items: [
                { text: 'Excel导出', click: itemclick_projectInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/attibutes.gif' }]
                }   
            });
           
           commoninfodivthirth  = $("#commoninfodivthirth").ligerGrid({
                columns: [ 
                {display: '转卡类型', name: 'tocardtypeno', align: 'left', width: 60 } ,
                { display: '类型名称', name: 'tocardtypename', align: 'left'  ,width: 115 },
                { display: '转卡金额', name: 'changeamt', align: 'left'  ,width: 60 }
                ],     data: null, pageSize: 50, sortName: 'tocardtypeno',
                width: '100%', height: '95%',rownumbers:false,
                fixedCellHeight:false,usePager:false
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
       		var params = "strCurCompId="+note.data.id;				
     		var requestUrl ="bc007/loadCardTyeInfo.action"; 
			var responseMethod="loadPrjInfosMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params );
          }catch(e){alert(e.message);}
    }
    function loadPrjInfosMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.lsCardtypeInfo!=null && responsetext.lsCardtypeInfo.length>0)
	   		{
	   			curCardtypeInfoDate={Rows: responsetext.lsCardtypeInfo,Total: responsetext.lsCardtypeInfo.length};
	   			commoninfodivsecond.options.data=$.extend(true, {},curCardtypeInfoDate);
            	commoninfodivsecond.loadData(true);
            	commoninfodivsecond.select(0);    	            	
	   		}
	   		else
	   		{
	   			commoninfodivsecond.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivsecond.loadData(true);      
	   		}
	   		
	   	}catch(e){alert(e.message);}
    }
    
    function loadSelecDetialData(data, rowindex, rowobj)
    {
    	var strCurCardTypeNo=data.bcardtypeno;
    	var params = "strCurCompId="+curCompid;			
    	params =params+ "&strCurCardTypeNo="+strCurCardTypeNo;		
     	var requestUrl ="bc007/loadCardchangerule.action"; 
		var responseMethod="loadCardchangeruleMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
    	
    }
    function loadCardchangeruleMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.lsCardchangeruleInfo!=null && responsetext.lsCardchangeruleInfo.length>0)
	   		{
	   			commoninfodivthirth.options.data=$.extend(true, {},{Rows: responsetext.lsCardchangeruleInfo,Total: responsetext.lsCardchangeruleInfo.length});
            	commoninfodivthirth.loadData(true);            	
	   		}
	   		else
	   		{
	   			commoninfodivthirth.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivthirth.loadData(true);      
	   		}
	   		
	   	}catch(e){alert(e.message);}
    }
  
     // ------------------------操作用户 ----------------End
     // ------------------------查询信息 ----------------Start
     function f_searchStaffInfo()
     {
    
     	   commoninfodivsecond.options.data = $.extend(true, {}, curCardtypeInfoDate);
     	
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
             
               return (rowdata.bcardtypeno.indexOf(key) > -1 ||  rowdata.cardtypename.indexOf(key) > -1 );
              
                
            };
            return clause; 
        }
    	catch(e){alert(e.message);}
     }
    
     function itemclick_projectInfo(item)
     {
     	try
    	{
	    	 $.ligerDialog.confirm('确认 '+curCompid+' 卡类型资料 '+item.text+' 操作', function (result)
		     {
		     		if( result==true)
            		{
			          	if(parent.hasFunctionRights( "BC007",  "UR_PRINT")!=true)
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