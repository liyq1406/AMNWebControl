
   	var sc007layout=null;
   	var commoninfodivTradeDate=null;
	var showDialogmanager=null;
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            sc007layout= $("#sc007layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = sc007layout.centerWidth
       		$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'100' });
            $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'100' });
            var goodsWPTJData=JSON.parse(parent.loadCommonControlDate_select("WPTJ",0));
            var projectXMTJData=JSON.parse(parent.loadCommonControlDate_select("XMTJ",0));
           //--------销售条码卡
            commoninfodivTradeDate=$("#commoninfodivTradeDate").ligerGrid({
                columns: [
                { display: '门店', 			name: 'shopId', 			width:60	,align: 'left' ,frozen:true},
                { display: '名称', 			name: 'shopName', 			width:110	,align: 'left' ,frozen:true},
                { display: '项目编号', 		name: 'projectno', 			width:100	,align: 'left' },
                { display: '项目名称', 		name: 'projectname',  		width:200	,align: 'left' },
	            { display: '项目类别', 		name: 'projecttype', 		data: projectXMTJData, 	 	width:100	,align: 'left' ,
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("XMTJ",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.projecttype)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
	            { display: '消耗数量',   		name: 'projectcostcount',  	width:80	,align: 'right' },
	            { display: '消耗金额',   		name: 'projectcostamt',  	width:80	,align: 'right'},
	            { display: '产品编号',   		name: 'goodsno',  			width:100	,align: 'left'},
	            { display: '产品名称',   		name: 'goodsname',  		width:200	,align: 'left'},
	            { display: '产品类别',   		name: 'goodstype',  	 	data: goodsWPTJData, 	 	width:100	,align: 'left' ,
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
	            { display: '销售数量',   		name: 'goodssalecount',  	width:80	,align: 'right'},
	            { display: '销售金额',   		name: 'goodssaleamt',  		width:80	,align: 'right'}
	            ],  pageSize:20, 
                data: null,      
                width: centerWidth,
                height:height-100,
                enabledEdit: true,  checkbox:false,rownumbers: true,usePager:false
               
            });
            document.getElementById("showTable").width=centerWidth;
            $("#searchButton").ligerButton(
	         {
	             text: '查询日报表', width: 120,
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
            addtrade();
   		}catch(e){alert(e.message);}
    });
    
    function  addtrade()
        {
        	var row = commoninfodivTradeDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivTradeDate.addRow({ 
				                shopId: "",
				                shopName: "",
				                dateReport: ""
				             
				            }, row, false);
        }
    //登记储值卡
    function loadDataSet()
    {	
   
    	var strCurCompId=getCurOrgFromSearchBar();
		var strFromDate=document.getElementById("strFromDate").value;
    	var strToDate=document.getElementById("strToDate").value;
    	var iBeforCount=document.getElementById("iBeforCount").value;
    	var dPrjFromCostAmt=document.getElementById("dPrjFromCostAmt").value;
    	var dPrjToCostAmt=document.getElementById("dPrjToCostAmt").value;
    	var dGoodsFromCostAmt=document.getElementById("dGoodsFromCostAmt").value;
    	var dGoodsToCostAmt=document.getElementById("dGoodsToCostAmt").value;
    	var params = "strCurCompId="+strCurCompId;		
    	params=params+"&strFromDate="+strFromDate;	
    	params=params+"&strToDate="+strToDate;	
    	params=params+"&iBeforCount="+iBeforCount;	
    	params=params+"&dPrjFromCostAmt="+dPrjFromCostAmt;	
    	params=params+"&dPrjToCostAmt="+dPrjToCostAmt;	
    	params=params+"&dGoodsFromCostAmt="+dGoodsFromCostAmt;	
    	params=params+"&dGoodsToCostAmt="+dGoodsToCostAmt;	
     	var requestUrl ="sc007/loadDataSet.action"; 
		var responseMethod="loadDataSetMessage";		
		 showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    
    }
    
    function loadDataSetMessage(request)
		{
			var responsetext = eval("(" + request.responseText + ")");
			if(responsetext.lsDataSet!=null && responsetext.lsDataSet.length>0)
           	{
            		commoninfodivTradeDate.options.data=$.extend(true, {},{Rows: responsetext.lsDataSet,Total: responsetext.lsDataSet.length});
            		commoninfodivTradeDate.loadData(true);
            }
			else
			{
				 	$.ligerDialog.warn("没有查到营业信息!");
		   			commoninfodivTradeDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivTradeDate.loadData(true);
	            	addtrade(); 
			}
			showDialogmanager.close();
		}
