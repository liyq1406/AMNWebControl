
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
           	var centerWidth = sc014layout.centerWidth
       
           	$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
            $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlignk: 'right',width:'120' });
           //--------销售条码卡
            commoninfodivDate=$("#commoninfodivDate").ligerGrid({
                columns: [
                { display: '会员卡号', 			name: 'strCardNo', 			width:180	,align: 'left' ,frozen:true},
                { display: '会员名称', 			name: 'strCardName', 			width:180	,align: 'left' ,frozen:true},
                { display: '卡类别', 			name: 'strCardClass', 			width:180	,align: 'left' ,frozen:true},
                { display: '手机号码', 			name: 'strCardPhone', 			width:180	,align: 'left' ,frozen:true},
                { display: '消费金额', 			name: 'amt', 			width:180	,align: 'left' ,frozen:true}
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
	         /*$("#excelButton").ligerButton(
	         {
	             text: 'Excel导出', width: 120,
		         click: function ()
		         {
		              loadDataSetExcel();
		         }
	         });*/
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
    		params=params+"&strFromDate="+strFromDate;	
    		params=params+"&strToDate="+strToDate;	
    		params=params+"&strCardClass="+$("#strCardClass").val();
    		params=params+"&fromamt="+$("#fromamt").val();
    		params=params+"&toamt="+$("#toamt").val();
     	var requestUrl ="sc016/query.action"; 
		var responseMethod="loadDataSetMessage";		
		showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
    function loadDataSetMessage(request)
	{
		var responsetext = eval("(" + request.responseText + ")");
		if(responsetext.lsCardSaleInfos!=null && responsetext.lsCardSaleInfos.length>0)
          	{
           		commoninfodivDate.options.data=$.extend(true, {},{Rows: responsetext.lsCardSaleInfos,Total: responsetext.lsCardSaleInfos.length});
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
   		var params = "strCurCompId="+strCurCompId;				
   		params=params+"&strFromDate="+strFromDate;	
   		params=params+"&strToDate="+strToDate;		
		var excelDialog=$.ligerDialog.open({url: contextURL+'/sc014/loadsc014Excel.action?'+params });
   		setTimeout(function () { excelDialog.close(); }, 15000);
	}
