
   	var pc021layout=null;
   	var commoninfodivTradeDate=null;
	var showDialogmanager=null;
	var departmentmanager=null;
	var yejichooseData = [{ choose: '1', text: '业务' }, { choose: '0', text: '非业务'}];
	var sexchooseData = [{ choose: '1', text: '男' }, { choose: '0', text: '女'}];
	var departBMZLData=JSON.parse(parent.loadCommonControlDate_select("BMZL",0));
	var postionGZGWData=JSON.parse(parent.loadCommonControlDate_select("GZGW",0));
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            pc021layout= $("#pc021layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = pc021layout.centerWidth
	
           //--------销售条码卡
            commoninfodivTradeDate=$("#commoninfodivTradeDate").ligerGrid({
                columns: [
                { display: '门店', 			name: 'bcompname', 			width:100	,align: 'left' },
                { display: '部门', 			name: 'department', 	 data: departBMZLData, 	 	width:60	,align: 'left' ,
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("BMZL",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.department)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
	            { display: '职位',   		name: 'position',  			data: postionGZGWData, 	 	width:80	,align: 'left' ,
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("GZGW",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.position)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
	            { display: '职称',   		name: 'positiontitle',  			data: postionGZGWData, 	 	width:80	,align: 'left' ,
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("GZZC",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.positiontitle)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
	            { display: '工号', 			name: 'bstaffno', 			width:60	,align: 'left' },
                { display: '姓名', 			name: 'staffname',  		width:80	,align: 'left' },
	            { display: '性别', 			name: 'staffsex',  			width:40	,align: 'left' , 
	            	editor: { type: 'select', data: sexchooseData, valueField: 'choose' },
	            	render: function (item)
	              	{
	                  if (item.staffsex == '1') return '男';
	                    return '女';
	                }
	            },
	            { display: '手机号码',   		name: 'mobilephone',  		width:100	,align: 'left'},
	            { display: '身份证号码',   	name: 'pccid',  			width:140	,align: 'left'},
	            { display: '档案号',   		name: 'fillno',  		width:80	,align: 'left'},
	            { display: '入职日期',   		name: 'arrivaldate',  		width:80	,align: 'left'},
				{ display: '年假天数',   		name: 'years',  		width:80	,align: 'left'}
	            ],  pageSize:25, 
                data: null,      
                width: centerWidth,
                height:height-60,
                enabledEdit: false,  checkbox: false,rownumbers: true,usePager: true
               
            });
  		 	$("#strFromDate").ligerDateEditor({ showTime:true,width:160,format: "yyyy", labelWidth:80, labelAlign: 'right' })
            //$("#strDdatelast").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
            var today = new Date();
			var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
			var intDay=today.getDate();
			//var today = intYear.toString()+"-"+fullStr(intMonth.toString());
			var today = intYear.toString();
			document.getElementById("strFromDate").value=today;
			
            document.getElementById("showTable").width=centerWidth;
            $("#searchButton").ligerButton(
	         {
	             text: '查询门店员工年假', width: 120,
		         click: function ()
		         {
		             loadDataSet();
		         }
	         });

	         $("#excelButton").ligerButton(
	         {
	             text: '转Excel', width: 120,
		         click: function ()
		         {
		             loadDataSetExcel();
		         }
	         });
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

    function loadDataSet()
    {	
   
    	var strCurCompId=getCurOrgFromSearchBar();
    	var params = "strCurCompId="+strCurCompId;	
    	params=params+"&strFromDate="+document.getElementById("strFromDate").value;
    	var requestUrl ="pc021/loadDataSet.action"; 
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
				 	$.ligerDialog.warn("没有查到年假信息!");
		   			commoninfodivTradeDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivTradeDate.loadData(true);
	            	addtrade(); 
			}
			showDialogmanager.close();
		}
		
		function loadDataSetExcel()
		{
			var strCurCompId=getCurOrgFromSearchBar();
    		var params = "strCurCompId="+strCurCompId;	
    		params=params+"&strFromDate="+document.getElementById("strFromDate").value;
			var excelDialog=$.ligerDialog.open({url: contextURL+'/pc021/loadpc021Excel.action?'+params });
    		setTimeout(function () { excelDialog.close(); }, 10000);
		}
