
   	var sc014layout=null;
   	var commoninfodivDate=null;
	var showDialogmanager=null;
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            sc014layout= $("#sc016layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = sc014layout.centerWidth;
       
           	$("#strFromDate").ligerDateEditor({ labelWidth: 100,format:"yyyy-MM-dd", labelAlign: 'right',width:'120' });
            $("#strToDate").ligerDateEditor({ labelWidth: 100,format:"yyyy-MM-dd",labelAlignk: 'right',width:'120' });
           //--------销售条码卡
            commoninfodivDate=$("#commoninfodivDate").ligerGrid({
                columns: [
                { display: '会员层级', 			name: 'strType', 			width:180	,align: 'left' ,frozen:true},
                { display: '目标', 			name: 'strTarget', 			width:160	,align: 'left' ,frozen:true},
                { display: '完成数量', 			name: 'jan', 			width:80	,align: 'left' ,frozen:true},
                { display: '未完成数量', 			name: 'feb', 			width:80	,align: 'left' ,frozen:true}
                /*{ display: '三月', 			name: 'mar', 			width:80	,align: 'left' ,frozen:true},
                { display: '四月', 			name: 'apr', 			width:80	,align: 'left' ,frozen:true},
                { display: '五月', 			name: 'may', 			width:80	,align: 'left' ,frozen:true},
                { display: '六月', 			name: 'jun', 			width:80	,align: 'left' ,frozen:true},
                { display: '七月', 			name: 'jul', 			width:80	,align: 'left' ,frozen:true},
                { display: '八月', 			name: 'aug', 			width:80	,align: 'left' ,frozen:true},
                { display: '九月', 			name: 'sep', 			width:80	,align: 'left' ,frozen:true},
                { display: '十月', 			name: 'oct', 			width:80	,align: 'left' ,frozen:true},
                { display: '十一', 			name: 'nov', 			width:80	,align: 'left' ,frozen:true},
                { display: '十二', 			name: 'dec', 			width:80	,align: 'left' ,frozen:true}*/
	            ],  pageSize:50, 
                data: null,      
                width: centerWidth,
                height:height-100,
                enabledEdit: true,  checkbox:false,rownumbers: false ,usePager: true
            });
            document.getElementById("showTable").width=centerWidth;
            $("#searchButton").ligerButton(
	         {
	             text: '查询', width: 120,
		         click: function ()
		         {
		             loadDataSet();
		         }
	         });
	         $("#excelButton").ligerButton(
	         {
	             text: 'Excel导出', width: 120,
		         click: function ()
		         {
		              loadDataSetExcel();
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
            //f_setColumns(); 
            //addtrade();
           
   		}catch(e){alert(e.message);}
    });

    function loadDataSet()
    {	
    	var strCurCompId=getCurOrgFromSearchBar();
    	var strFromDate=document.getElementById("strFromDate").value;
    	var strToDate=document.getElementById("strToDate").value;
    	
    	var params = "strCompId="+strCurCompId;				
    		params=params+"&strDate="+strFromDate;
    		params=params+"&strToDate="+strToDate;	
     	var requestUrl ="sc017/query.action"; 
		var responseMethod="loadDataSetMessage";		
		showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
    function loadDataSetMessage(request)
	{
		var responsetext = eval("(" + request.responseText + ")");
		if(responsetext.lsBeans!=null && responsetext.lsBeans.length>0)
          	{
           		commoninfodivDate.options.data=$.extend(true, {},{Rows: responsetext.lsBeans,Total: responsetext.lsBeans.length});
           		commoninfodivDate.loadData(true);
           }
		else
		{
			 	$.ligerDialog.warn("没有查到信息!");
	   			commoninfodivDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
            	commoninfodivDate.loadData(true);
		}
		showDialogmanager.close();
	}
	
	function loadPData(lsDataSet)
	{
		var pDataJson="";
		for(var i=0;i<lsDataSet.length;i++)
		{
			if(pDataJson=="")
			{
				pDataJson=pDataJson+'[';
			}
			else
			{
				pDataJson=pDataJson+',';
			}
			pDataJson=pDataJson+'{"strCompId": "'+lsDataSet[i].strCompId+'", "strCompName": "'+lsDataSet[i].strCompName+'"';
			var WPTJ=parent.gainCommonInfoByCode("WPTJ",0);
		    for(var j=0;j<WPTJ.length;j++)
			{	
				pDataJson+=',  "'+WPTJ[j].bparentcodekey+'Amt":"'+ForDight(lsDataSet[i].goodsInserTypesAmt[j][0],2)+'" ';
			}
			pDataJson=pDataJson+'}';
			
		}
		if(pDataJson!="")
		{
				pDataJson=pDataJson+']';
				return {Rows:JSON.parse(pDataJson),Total: lsDataSet.length };
		}	
	}
	
		
	function loadDataSetExcel()
	{
		var strCurCompId=getCurOrgFromSearchBar();
   		var strFromDate=document.getElementById("strFromDate").value;
   		var strToDate=document.getElementById("strToDate").value;
   		var params = "strCompId="+strCurCompId;				
   		params=params+"&strFromDate="+strFromDate;	
   		params=params+"&strToDate="+strToDate;		
		var excelDialog=$.ligerDialog.open({url: contextURL+'/sc017/loadExcel.action?'+params });
   		setTimeout(function () { excelDialog.close(); }, 15000);
	}
