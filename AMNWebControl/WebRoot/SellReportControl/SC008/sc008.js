
   	var sc008layout=null;
   	var commoninfodivTradeDate=null;
	var showDialogmanager=null;
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            sc008layout= $("#sc008layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = sc008layout.centerWidth
       
           	$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
            $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
           //--------销售条码卡
            commoninfodivTradeDate=$("#commoninfodivTradeDate").ligerGrid({
                columns: [
                { display: '门店', 			name: 'strCompId', 			width:60	,align: 'left' ,frozen:true},
                { display: '名称', 			name: 'strCompName', 			width:110	,align: 'left' ,frozen:true,isSort: true},
                { display: '日期', 			name: 'strDate', 		width:140	,align: 'left' ,frozen:true},
                { display: '总虚业绩', 		name: 'totalxuyejizbF',  			width:140	,align: 'left'},
	            { display: '总实业绩', 		name: 'totalshiyeji', 	 		width:60	,align: 'left'},
	            { display: '耗卡率',   		name: 'costcardratezbF',  		width:120	,align: 'left'},
	            { display: '新增会员',   		name: 'addmemcount',  			width:60	,align: 'right'},
	            { display: '有效会员',   		name: 'goodmemcountzbF',  			width:120	,align: 'left'},
	            { display: '会员数',   		name: 'memcount',  			width:60	,align: 'right'},
	            { display: '门店新增疗程', columns:
                [
		             { display: '美容',   		name: 'addbeatypromems',  			width:50	,align: 'right'},
		             { display: '美发',   		name: 'addhairpromems',  			width:50	,align: 'right'},
		             { display: '合计',   		name: 'addpromemszbF',  			width:80	,align: 'left'}
		        ]},    
		        
		        { display: '美容疗程会员', columns:
                [
		             { display: '会员数',   			name: 'beatycount',  				width:60	,align: 'right'},
		             { display: '有效会员数',   		name: 'beatygoodcount',  			width:70	,align: 'right'},
		             { display: '会员保有率',   		name: 'beatygoodratezbF',  			width:80	,align: 'left'}
		        ]}, 
		        { display: '美发疗程会员', columns:
                [
		             { display: '会员数',   			name: 'haircount',  				width:60	,align: 'right'},
		             { display: '有效会员数',   		name: 'hairgoodcount',  			width:70	,align: 'right'},
		             { display: '会员保有率',   		name: 'hairgoodratezbF',  			width:80	,align: 'left'}
		        ]}, 
	            { display: '离职人数', columns:
                [
		             { display: '核心员工',   		name: 'leavelcorecount',  			width:60	,align: 'right'},
		             { display: '总人数',   			name: 'leavelcountzbF',  			width:80	,align: 'left'}
		        ]
		        } 
	             
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

    	var strSearchType=document.getElementById("strSearchType").value;
    	var strFromDate=document.getElementById("strFromDate").value;
    	var strToDate=document.getElementById("strToDate").value;
    	
    	var params = "strCurCompId="+strCurCompId;	
    	params=params+"&strSearchType="+strSearchType;			
    	params=params+"&strFromDate="+strFromDate;	
    	params=params+"&strToDate="+strToDate;	
     	var requestUrl ="sc008/loadDataSet.action"; 
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
    
    function loadDataSetExcel()
	{
    	var strCurCompId=getCurOrgFromSearchBar();

    	var strSearchType=document.getElementById("strSearchType").value;
    	var strFromDate=document.getElementById("strFromDate").value;
    	var strToDate=document.getElementById("strToDate").value;
    	
    	var params = "strCurCompId="+strCurCompId;	
    	params=params+"&strSearchType="+strSearchType;			
    	params=params+"&strFromDate="+strFromDate;	
    	params=params+"&strToDate="+strToDate;	
    	window.open(contextURL+'/sc008/loadExcel.action?'+params,"","top=1,left=1,width=1,height=1,toolbar =no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
		//var excelDialog=$.ligerDialog.open({url:  });
   		//setTimeout(function () { excelDialog.close(); }, 15000);
	}
