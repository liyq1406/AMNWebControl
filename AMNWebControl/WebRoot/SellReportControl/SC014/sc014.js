
   	var sc014layout=null;
   	var commoninfodivDate=null;
	var showDialogmanager=null;
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            sc014layout= $("#sc014layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = sc014layout.centerWidth
       
           	$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
            $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
           //--------销售条码卡
            commoninfodivDate=$("#commoninfodivDate").ligerGrid({
                columns: [
                { display: '门店', 			name: 'strCompId', 			width:60	,align: 'left' ,frozen:true}
	            ],  pageSize:20, 
                data: null,      
                width: centerWidth,
                height:height-100,
                enabledEdit: true,  checkbox:false,rownumbers: false ,usePager: true
               
            });
            document.getElementById("showTable").width=centerWidth;
            $("#searchButton").ligerButton(
	         {
	             text: '卖品统计', width: 120,
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
            f_setColumns(); 
            addtrade();
           
   		}catch(e){alert(e.message);}
    });
     function f_setColumns()
     { 
     		var showColumns='['; 
     		showColumns+= '{ "display": "门店", "name": "strCompId", "width":"60"	,"align":"left","frozen":"true" },';
     		showColumns+='{ "display": "名称",  "name": "strCompName","width":"110","align":"left","frozen":"true" }';
     		var WPTJ=parent.gainCommonInfoByCode("WPTJ",0);
		    for(var i=0;i<WPTJ.length;i++)
			{	
				showColumns+=',{ "display": "'+WPTJ[i].parentcodevalue+'",  "name": "'+WPTJ[i].bparentcodekey+'Amt","width":"110","align":"right","type":"float" }';
			}			
            showColumns+=']';
            commoninfodivDate.set('columns', JSON.parse(showColumns)); 
            commoninfodivDate.reRender();
     }
    function  addtrade()
       {
       	var row = commoninfodivDate.getSelectedRow();
			     //参数1:rowdata(非必填)
			    //参数2:插入的位置 Row Data 
			    //参数3:之前或者之后(非必填)
		commoninfodivDate.addRow({ 
			                shopId: "",
			                shopName: "",
			                dateReport: ""
			            }, row, false);
       }

    function loadDataSet()
    {	
    	var strCurCompId=getCurOrgFromSearchBar();
    	var strFromDate=document.getElementById("strFromDate").value;
    	var strToDate=document.getElementById("strToDate").value;
    	
    	var params = "strCurCompId="+strCurCompId;				
    		params=params+"&strFromDate="+strFromDate;	
    		params=params+"&strToDate="+strToDate;	
     	var requestUrl ="sc014/loadDataSet.action"; 
		var responseMethod="loadDataSetMessage";		
		showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
    function loadDataSetMessage(request)
	{
		var responsetext = eval("(" + request.responseText + ")");
		if(responsetext.lsDataSet!=null && responsetext.lsDataSet.length>0)
          	{
          			var curdate=loadPData(responsetext.lsDataSet);
           		commoninfodivDate.options.data=$.extend(true, {},curdate);
           		commoninfodivDate.loadData(true);
           }
		else
		{
			 	$.ligerDialog.warn("没有查到信息!");
	   			commoninfodivDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
            	commoninfodivDate.loadData(true);
            	addtrade(); 
		}
		commoninfodivDate.sortedData=false;
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
