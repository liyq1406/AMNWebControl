
   	var sc005layout=null;
   	var commoninfodivTradeDate=null;
	var showDialogmanager=null;
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            sc005layout= $("#sc005layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = sc005layout.centerWidth
       
           	$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
            $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
           //--------销售条码卡
            commoninfodivTradeDate=$("#commoninfodivTradeDate").ligerGrid({
                columns: [
                { display: '门店', 				name: 'compid', 			width:80	,align: 'left' },
                { display: '名称', 				name: 'compname', 			width:110	,align: 'left' },
                { display: '美容虚业绩', 			name: 'beautyeji',  		type:'float',width:100	,align: 'right' ,
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '美发虚业绩', 			name: 'hairyeji', 	 		type:'float',width:100	,align: 'right' ,
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '烫染护虚业绩',   		name: 'footyeji',  			type:'float',width:100	,align: 'right' ,
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '美甲虚业绩',   		name: 'fingeryeji',  		type:'float',width:100	,align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '总虚业绩',   			name: 'totalyejitext',  	type:'float',	width:100	,align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '美容实业绩', 			name: 'realbeautyeji',  	type:'float',width:100	,align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '美发实业绩', 			name: 'realhairyeji', 	 	type:'float',width:100	,align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '烫染护实业绩',   		name: 'realfootyeji',  		type:'float',width:100	,align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '美甲实业绩',   		name: 'realfingeryeji',  	type:'float',width:100	,align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }},
	            { display: '总实业绩',   			name: 'realtotalyejitext',  type:'float',	width:100	,align: 'right',
                	totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return ForDight(suminf.sum,2);
                        },
						align: 'right'
                    }}
	            
	             
	            ],  pageSize:20, 
                data: null,      
                width: centerWidth,
                height:height-140,
                enabledEdit: true,  checkbox:false,rownumbers: false,usePager: false
               
            });
            document.getElementById("showTable").width=centerWidth;
            $("#searchButton").ligerButton(
	         {
	             text: '查询分类业绩', width: 180,
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
    	var params = "strCurCompId="+strCurCompId;				
    	params=params+"&strFromDate="+strFromDate;	
    	params=params+"&strToDate="+strToDate;	
     	var requestUrl ="sc005/loadDataSet.action"; 
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
         
            		document.getElementById("lbcurcompid").innerHTML=getCurOrgFromSearchBar();
            		document.getElementById("lbmryejisort").innerHTML=checkNull(responsetext.butySeqno);
            		document.getElementById("lbmfyejisort").innerHTML=checkNull(responsetext.hairSeqno);
            		document.getElementById("lbtryejisort").innerHTML=checkNull(responsetext.trhSeqno);
            		document.getElementById("lbmjyejisort").innerHTML=checkNull(responsetext.fingerSeqno);
            		document.getElementById("lbtotalyejisort").innerHTML=checkNull(responsetext.totalSeqno);
            		document.getElementById("lbhairyeji").innerHTML=ForDight(checkNull(responsetext.curDateSet.beautyeji)*1,1);
            		document.getElementById("lbbeatyyeji").innerHTML=ForDight(checkNull(responsetext.curDateSet.hairyeji)*1,1);
            		document.getElementById("lbtrhyeji").innerHTML=ForDight(checkNull(responsetext.curDateSet.footyeji)*1,1);
            		document.getElementById("lbfingeryeji").innerHTML=ForDight(checkNull(responsetext.curDateSet.fingeryeji)*1,1);
            		document.getElementById("lbtotalyeji").innerHTML=ForDight(checkNull(responsetext.curDateSet.totalyeji)*1,1);
            		document.getElementById("lbrealhairyeji").innerHTML=ForDight(checkNull(responsetext.curDateSet.realbeautyeji)*1,1);
            		document.getElementById("lbrealbeatyyeji").innerHTML=ForDight(checkNull(responsetext.curDateSet.realhairyeji)*1,1);
            		document.getElementById("lbrealtrhyeji").innerHTML=ForDight(checkNull(responsetext.curDateSet.realfootyeji)*1,1);
            		document.getElementById("lbrealfingeryeji").innerHTML=ForDight(checkNull(responsetext.curDateSet.realfingeryeji)*1,1);
            		document.getElementById("lbrealtotalyeji").innerHTML=ForDight(checkNull(responsetext.curDateSet.realtotalyeji)*1,1);
            		
            }
			else
			{
				 	$.ligerDialog.warn("没有查到营业信息!");
		   			commoninfodivTradeDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivTradeDate.loadData(true);
	            	addtrade(); 
			}
			commoninfodivTradeDate.sortedData=false;
			showDialogmanager.close();
		}
