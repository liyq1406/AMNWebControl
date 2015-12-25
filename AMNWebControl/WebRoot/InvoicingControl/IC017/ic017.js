
   	var ic017layout=null;
   	var commoninfodivSearch=null;
	var showDialogmanager=null;
	var f_detailPanel	=null;
	var f_callback=null;
	var goodsWPTJData=JSON.parse(parent.loadCommonControlDate_select("WPTJ",0));
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            ic017layout= $("#ic017layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = ic017layout.centerWidth;
           	$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'90' });
            $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'90' });
          	//--------销售条码卡
            commoninfodivSearch=$("#commoninfodivSearch").ligerGrid({
                columns: [
                { display: '门店编号', 			name: 'strCompId', 			width:60	,align: 'left' },
                { display: '门店名称', 			name: 'strCompName', 		width:100	,align: 'left' },
                { display: '产品类型', 			name: 'goodstype', 			width:100	,align: 'left' ,
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
	                } 
	             },
                { display: '产品编号', 			name: 'goodsno', 			width:100	,align: 'left' },
                { display: '产品名称', 			name: 'goodsname', 			width:240	,align: 'left' },
                { display: '产品单位', 			name: 'goodsunit', 			width:60	,align: 'left' },
                { display: '总消耗量', 			name: 'costunitcount', 	 	width:90	,align: 'right'}
	            ],  pageSize:25, 
                data: null,      
                width: centerWidth,
                height:height-100,
                enabledEdit: true,  checkbox:false,rownumbers: false,usePager: true,
                detail: { onShowDetail: showDetialinfo},detailHeight:350
            });
            
    
            
        
            document.getElementById("showTable").width=centerWidth;
            $("#searchButton").ligerButton(
	         {
	             text: '查询', width: 160,
		         click: function ()
		         {
		             loadDataSet();
		         }
	         });
	        var today = new Date();
			var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
			var intDay=today.getDate();
			var today = intYear.toString()+"-"+fullStr(intMonth.toString())+"-"+fullStr(intDay.toString());
			document.getElementById("strFromDate").value=today;
			document.getElementById("strToDate").value=today;
            $("#pageloading").hide(); 
            
            addOption("","",document.getElementById("searchGoodsType"));
           	var goodstypes=parent.gainCommonInfoByCode("WPTJ",0);
		    for(var i=0;i<goodstypes.length;i++)
			{
				addOption(goodstypes[i].bparentcodekey,goodstypes[i].parentcodevalue,document.getElementById("searchGoodsType"));
			}	
			
            addtrade();
   		}catch(e){alert(e.message);}
    });
    
    function  addtrade()
        {
        	var row = commoninfodivSearch.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivSearch.addRow({ 
				                strCompId: "",
				                strCompName: "",
				                goodstype: ""
				            }, row, false);
        }

    //登记储值卡
    function loadDataSet()
    {	
   
    	var strCurCompId=getCurOrgFromSearchBar();
    	
    	var params = "strCurCompId="+strCurCompId;	
    	params=params+"&strFromDate="+document.getElementById("strFromDate").value;
    	params=params+"&strToDate="+document.getElementById("strToDate").value;
    	params=params+"&strFromGoodsNo="+document.getElementById("strFromGoodsNo").value;
    	params=params+"&strToGoodsNo="+document.getElementById("strToGoodsNo").value;
    	params=params+"&strGoodsType="+document.getElementById("searchGoodsType").value;
    	var requestUrl ="ic017/loadDataSet.action"; 
		var responseMethod="loadDataSetMessage";		
		 showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    
    }
    
    function loadDataSetMessage(request)
		{
			var responsetext = eval("(" + request.responseText + ")");
			if(responsetext.lsDataSet!=null && responsetext.lsDataSet.length>0)
           	{
            		commoninfodivSearch.options.data=$.extend(true, {},{Rows: responsetext.lsDataSet,Total: responsetext.lsDataSet.length});
            		commoninfodivSearch.loadData(true);
            }
			else
			{
				 	$.ligerDialog.warn("没有查到库存异动信息!");
		   			commoninfodivSearch.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivSearch.loadData(true);
	            	addtrade(); 
			}
			showDialogmanager.close();
		}
		
		
		function showDetialinfo(row, detailPanel,callback)
		{
			f_detailPanel=detailPanel;
			f_callback=callback;
			
			loadDetialByGoodsNo(row.strCompId,row.goodsno);
		}
		
		function loadDetialByGoodsNo(compid,goodsno)
		{
			if(document.getElementById("strFromDate").value==""
			|| document.getElementById("strToDate").value=="")
			{
				$.ligerDialog.error("请确认查询日期!");
				return ;
			}
			var requestUrl ="ic017/loadProjectDetialInfo.action"; 
			var responseMethod="loadProjectDetialInfoMessage";		
			var param="strCurCompId="+compid;
			param=param+"&strGoodsNo="+goodsno;		
			param=param+"&strFromDate="+document.getElementById("strFromDate").value;
    		param=param+"&strToDate="+document.getElementById("strToDate").value;
			sendRequestForParams_p(requestUrl,responseMethod,param );
		}
		
		function loadProjectDetialInfoMessage(request)
	    {
	    	try
	    	{
	    	
	   		var responsetext = eval("(" + request.responseText + ")");
	   	
	   		if(responsetext.lsDconsumeinfo!=null && responsetext.lsDconsumeinfo.length>0)
	   		{
	   			var detailGrid = document.createElement('div'); 
	            $(f_detailPanel).append(detailGrid);
	            $(detailGrid).css('margin',10).ligerGrid({
	                columns:
	                            [
	                            { display: '消费单号', name: 'bcsbillid',width: 110,align: 'left' },
	                            { display: '消费日期', name: 'financedate',width: 80,align: 'left' },
	                            { display: '项目编号', name: 'csitemno', width: 90,align: 'left' },
	                            { display: '项目名称', name: 'csitemname' ,width: 180,align: 'left' },
	                            { display: '次数', 	 name: 'csitemcount' ,width: 40,align: 'right' },
	                            { display: '消耗量', 	 name: 'csdiscount' ,width: 40,align: 'right' },
	                            { display: '工号', 	 name: 'csfirstsaler' ,width: 70,align: 'left' },
	                            { display: '姓名', 	 name: 'csfirstinid' ,width: 90,align: 'left' }
	                            ], isScroll: false, showToggleColBtn: false, width: '98%',
	                 data:{Rows: responsetext.lsDconsumeinfo,Total: responsetext.lsDconsumeinfo.length}
	                 , showTitle: false, rownumbers:true,columnWidth: 100,usePager: true
	                 , onAfterShowData: f_callback,frozen:false
	            });
	   		}
	   		}catch(e){alert(e.message)}
	   	}
	
