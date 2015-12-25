
   	var sc009layout=null;
   	var commoninfodivPjDate=null;
	var showDialogmanager=null;
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            sc009layout= $("#sc009layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = sc009layout.centerWidth
       
           	$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
            $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
           //--------销售条码卡
            commoninfodivPjDate=$("#commoninfodivPjDate").ligerGrid({
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
           f_setColumns(); 
             addtrade();
           
   		}catch(e){alert(e.message);}
    });
     function f_setColumns()
     { 
     		var showColumns='['; 
     		showColumns+= '{ "display": "门店", "name": "strCompId", "width":"60"	,"align":"left","frozen":"true" },';
     		showColumns+='{ "display": "名称",  "name": "strCompName","width":"110","align":"left","frozen":"true" }';
     		var XMTJ=parent.gainCommonInfoByCode("XMTJ",0);
		    for(var i=0;i<XMTJ.length;i++)
			{	
				showColumns+=',{ "display": "'+XMTJ[i].parentcodevalue+'",  "name": "'+XMTJ[i].bparentcodekey+'Amt","width":"110","align":"right","type":"float" }';
					
			}			
            showColumns+=']';
            commoninfodivPjDate.set('columns', JSON.parse(showColumns)); 
            commoninfodivPjDate.reRender();
     }
    function  addtrade()
        {
        	var row = commoninfodivPjDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivPjDate.addRow({ 
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
     	var requestUrl ="sc009/loadDataSet.action"; 
		var responseMethod="loadDataSetMessage";		
		 showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    
    }
    
    function loadDataSetMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
			if(responsetext.lsDataSet!=null && responsetext.lsDataSet.length>0)
           	{
           			var curdate=loadPrjData(responsetext.lsDataSet);
            		commoninfodivPjDate.options.data=$.extend(true, {},curdate);
            		commoninfodivPjDate.loadData(true);
            }
			else
			{
				 	$.ligerDialog.warn("没有查到兑换信息!");
		   			commoninfodivPjDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivPjDate.loadData(true);
	            	addtrade(); 
			}
			commoninfodivPjDate.sortedData=false;
			showDialogmanager.close();
	}
	
	function loadPrjData(lsDataSet)
	{
			var pjDataJson="";
			for(var i=0;i<lsDataSet.length;i++)
			{
				if(pjDataJson=="")
				{
					pjDataJson=pjDataJson+'[';
				}
				else
				{
					pjDataJson=pjDataJson+',';
				}
				pjDataJson=pjDataJson+'{"strCompId": "'+lsDataSet[i].strCompId+'", "strCompName": "'+lsDataSet[i].strCompName+'"';
				var XMTJ=parent.gainCommonInfoByCode("XMTJ",0);
			    for(var j=0;j<XMTJ.length;j++)
				{	
					pjDataJson+=',  "'+XMTJ[j].bparentcodekey+'Amt":"'+ForDight(lsDataSet[i].prjTypesAmt[j][0],2)+'" ';
				}
				pjDataJson=pjDataJson+'}';
				
			}
			if(pjDataJson!="")
			{
					pjDataJson=pjDataJson+']';
					return {Rows:JSON.parse(pjDataJson),Total: lsDataSet.length };
			}
			
	}
