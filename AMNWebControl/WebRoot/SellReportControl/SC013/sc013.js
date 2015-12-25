	var SC013Tab=null;
   	var sc013layout=null;
   	var commoninfodivPjDate=null;
	var showDialogmanager=null;
	var curRecord=null;
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            sc013layout= $("#sc013layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
           	var height = $(".l-layout-center").height();
           	var centerWidth = sc013layout.centerWidth
       		$("#SC013Tab").ligerTab();
            SC013Tab = $("#SC013Tab").ligerGetTabManager();
          // 	$("#strFromDate").ligerDateEditor({ labelWidth: 100,format: "yyyy", labelAlign: 'right',width:'80' });
           //--------销售条码卡
            commoninfodivPjDate=$("#commoninfodivPjDate").ligerGrid({
                columns: [
                 { display: '门店', 				name: 'strCompName', 				width:70	,align: 'left' },
                 { display: '项目名称', 			name: 'strPrjName', 				width:120	,align: 'left' },
                 { display: '1月', 				name: 'month1num', 					width:70	,align: 'left' },
                 { display: '2月', 				name: 'month2num', 					width:70	,align: 'left' },
                 { display: '3月', 				name: 'month3num', 					width:70	,align: 'left' },
                 { display: '4月', 				name: 'month4num', 					width:70	,align: 'left' },
                 { display: '5月', 				name: 'month5num', 					width:70	,align: 'left' },
                 { display: '6月', 				name: 'month6num', 					width:70	,align: 'left' },
                 { display: '7月', 				name: 'month7num', 					width:70	,align: 'left' },
                 { display: '8月', 				name: 'month8num', 					width:70	,align: 'left' },
                 { display: '9月', 				name: 'month9num', 					width:70	,align: 'left' },
                 { display: '10月', 				name: 'month10num', 				width:70	,align: 'left' },
                 { display: '11月', 				name: 'month11num', 				width:70	,align: 'right' },
                 { display: '12月', 				name: 'month12num', 				width:70	,align: 'right' },
                 { display: '年总计', 			name: 'summonthnum', 				width:70	,align: 'right' },
                 { display: '公司前五平均', 		name: 'totalavgbefor', 				width:90	,align: 'right' },
                 { display: '单店月平均', 		name: 'compavgbymonth', 			width:90	,align: 'right' },
                 { display: '公司后五平均', 		name: 'totalavgafter', 				width:90	,align: 'right' }
	            ],  pageSize:20, 
                data: null,      
                width: centerWidth,
                height:height-100,
                enabledEdit: true,  checkbox:false,rownumbers: false ,usePager: false,
                groupColumnName:'showtype',
                groupRender: function (showtype,groupdata)
                {
                   		if (showtype == 1) return '总店业绩分析';
	                    else if (showtype == 2) return '美发业绩分析';
	                    else if (showtype == 3) return '美容业绩分析';
	                    return '';
                },
                onDblClickRow : function (data, rowindex, rowobj)
                {
                	curRecord=data;
                	loadOtherImage();
                } 
            });
            document.getElementById("showTable").width=centerWidth;
            $("#searchButton").ligerButton(
	         {
	             text: '查询明细', width: 120,
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
	        var today = new Date();
			var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
			var intDay=today.getDate();
			var today = intYear.toString()
			//document.getElementById("strFromDate").value=today;
            $("#pageloading").hide(); 
           addtrade();
           
   		}catch(e){alert(e.message);}
    });
   
    function  addtrade()
        {
        	var row = commoninfodivPjDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivPjDate.addRow({ 
				                shopId: "",
				                shopName: "",
				                changeTypename: ""
				             
				            }, row, false);
        }
    //登记储值卡
    function loadDataSet()
    {	
   
    	var strCurCompId=getCurOrgFromSearchBar();

    	var strFromDate=document.getElementById("strFromDate").value;
    	if(strFromDate=="")
    	{
    		$.ligerDialog.error("确认查询年份!");
    	}
    	var params = "strCurCompId="+strCurCompId;				
    	params=params+"&strFromDate="+strFromDate;
     	var requestUrl ="sc013/loadDataSet.action"; 
		var responseMethod="loadDataSetMessage";		
		showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
   	 	
    }
    
    function loadDataSetMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
			if(responsetext.lsDataSet!=null && responsetext.lsDataSet.length>0)
           	{
            		commoninfodivPjDate.options.data=$.extend(true, {},{Rows: responsetext.lsDataSet,Total: responsetext.lsDataSet.length});
            		commoninfodivPjDate.loadData(true);
            		var lsData=responsetext.lsDataSet;
            		var lstotal=new Array();;
            		var lsbeaty=new Array();;
            		var lshair=new Array();;
            		var sumTotal=0;
            		var sumBeaty=0;
            		var sumHair=0;
            		for(var i=0;i<lsData.length*1;i++)
            		{
            			if(lsData[i].resusttyep==1) //总业绩
            			{
            				lstotal[0]=ForDight(checkNull(lsData[i].month1num)*1/10000,2);
            				lstotal[1]=ForDight(checkNull(lsData[i].month2num)*1/10000,2);
            				lstotal[2]=ForDight(checkNull(lsData[i].month3num)*1/10000,2);
            				lstotal[3]=ForDight(checkNull(lsData[i].month4num)*1/10000,2);
            				lstotal[4]=ForDight(checkNull(lsData[i].month5num)*1/10000,2);
            				lstotal[5]=ForDight(checkNull(lsData[i].month6num)*1/10000,2);
            				lstotal[6]=ForDight(checkNull(lsData[i].month7num)*1/10000,2);
            				lstotal[7]=ForDight(checkNull(lsData[i].month8num)*1/10000,2);
            				lstotal[8]=ForDight(checkNull(lsData[i].month9num)*1/10000,2);
            				lstotal[9]=ForDight(checkNull(lsData[i].month10num)*1/10000,2);
            				lstotal[10]=ForDight(checkNull(lsData[i].month11num)*1/10000,2);
            				lstotal[11]=ForDight(checkNull(lsData[i].month12num)*1/10000,2);
            				lstotal[12]=ForDight(checkNull(lsData[i].totalavgbefor)*1/10000,2);
            				lstotal[13]=ForDight(checkNull(lsData[i].compavgbymonth)*1/10000,2);
            				lstotal[14]=ForDight(checkNull(lsData[i].totalavgafter)*1/10000,2);
            				sumTotal=ForDight(checkNull(lsData[i].summonthnum)*1/10000,2);
            			}
            			else if(lsData[i].resusttyep==2) //美发业绩
            			{
            				lshair[0]=ForDight(checkNull(lsData[i].month1num)*1/10000,2);
            				lshair[1]=ForDight(checkNull(lsData[i].month2num)*1/10000,2);
            				lshair[2]=ForDight(checkNull(lsData[i].month3num)*1/10000,2);
            				lshair[3]=ForDight(checkNull(lsData[i].month4num)*1/10000,2);
            				lshair[4]=ForDight(checkNull(lsData[i].month5num)*1/10000,2);
            				lshair[5]=ForDight(checkNull(lsData[i].month6num)*1/10000,2);
            				lshair[6]=ForDight(checkNull(lsData[i].month7num)*1/10000,2);
            				lshair[7]=ForDight(checkNull(lsData[i].month8num)*1/10000,2);
            				lshair[8]=ForDight(checkNull(lsData[i].month9num)*1/10000,2);
            				lshair[9]=ForDight(checkNull(lsData[i].month10num)*1/10000,2);
            				lshair[10]=ForDight(checkNull(lsData[i].month11num)*1/10000,2);
            				lshair[11]=ForDight(checkNull(lsData[i].month12num)*1/10000,2);
            				lshair[12]=ForDight(checkNull(lsData[i].totalavgbefor)*1/10000,2);
            				lshair[13]=ForDight(checkNull(lsData[i].compavgbymonth)*1/10000,2);
            				lshair[14]=ForDight(checkNull(lsData[i].totalavgafter)*1/10000,2);
            				sumHair=ForDight(checkNull(lsData[i].summonthnum)*1/10000,2);
            			}
            			else if(lsData[i].resusttyep==3) //美容业绩
            			{
            				lsbeaty[0]=ForDight(checkNull(lsData[i].month1num)*1/10000,2);
            				lsbeaty[1]=ForDight(checkNull(lsData[i].month2num)*1/10000,2);
            				lsbeaty[2]=ForDight(checkNull(lsData[i].month3num)*1/10000,2);
            				lsbeaty[3]=ForDight(checkNull(lsData[i].month4num)*1/10000,2);
            				lsbeaty[4]=ForDight(checkNull(lsData[i].month5num)*1/10000,2);
            				lsbeaty[5]=ForDight(checkNull(lsData[i].month6num)*1/10000,2);
            				lsbeaty[6]=ForDight(checkNull(lsData[i].month7num)*1/10000,2);
            				lsbeaty[7]=ForDight(checkNull(lsData[i].month8num)*1/10000,2);
            				lsbeaty[8]=ForDight(checkNull(lsData[i].month9num)*1/10000,2);
            				lsbeaty[9]=ForDight(checkNull(lsData[i].month10num)*1/10000,2);
            				lsbeaty[10]=ForDight(checkNull(lsData[i].month11num)*1/10000,2);
            				lsbeaty[11]=ForDight(checkNull(lsData[i].month12num)*1/10000,2);
            				lsbeaty[12]=ForDight(checkNull(lsData[i].totalavgbefor)*1/10000,2);
            				lsbeaty[13]=ForDight(checkNull(lsData[i].compavgbymonth)*1/10000,2);
            				lsbeaty[14]=ForDight(checkNull(lsData[i].totalavgafter)*1/10000,2);
            				sumBeaty=ForDight(checkNull(lsData[i].summonthnum)*1/10000,2);
            			}
            		}
            		loadClassYeJiImage(lstotal,lsbeaty,lshair,sumTotal,sumBeaty,sumHair);
            		lsData=null;
            		lstotal=null;
            		lsbeaty=null;
            		lshair=null;
            }
			else
			{
				 	$.ligerDialog.warn("没有查到异动信息!");
		   			commoninfodivPjDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivPjDate.loadData(true);
	            	addtrade(); 
			}
			showDialogmanager.close();
	}
	
	function loadClassYeJiImage(lstotal,lsbeaty,lshair,sumTotal,sumBeaty,sumHair)
	{
	try
	{
		var chart = new Highcharts.Chart({
					chart: {
						renderTo: 'containerCalssYeji'
					},
					title: {
						text: '门店业绩分析[总虚业绩,美发业绩,美容业绩]'
					},
					xAxis: {
						categories: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月','十二月','前5名','本店平均','后5名']
					},
					yAxis: {
            			title: { text: '业绩单位(W)' }
        			},
					tooltip: {
						formatter: function() {
							var s;
							if (this.point.name) { // the pie chart
								s = ''+
									this.point.name +': '+ this.y +'(万)元';
							} else {
								s = ''+
									this.x  +': '+ this.y;
							}
							return s;
						}
					},
					labels: {
						items: [{
							html: '门店业绩分布图',
							style: {
								left: '40px',
								top: '8px',
								color: 'black'				
							}
						}]
					},
					series: [{
						type: 'column',
						name: '总虚业绩',
						data: [lstotal[0]*1, lstotal[1]*1, lstotal[2]*1,lstotal[3]*1, lstotal[4]*1,lstotal[5]*1, lstotal[6]*1, lstotal[7]*1, lstotal[8]*1, lstotal[9]*1,lstotal[10]*1, lstotal[11]*1, lstotal[12]*1,lstotal[13]*1, lstotal[14]*1]
					}, {
						type: 'column',
						name: '美发业绩',
						data: [lshair[0]*1, lshair[1]*1, lshair[2]*1,lshair[3]*1, lshair[4]*1,lshair[5]*1, lshair[6]*1, lshair[7]*1, lshair[8]*1, lshair[9]*1,lshair[10]*1, lshair[11]*1, lshair[12]*1,lshair[13]*1, lshair[14]*1]
					}, {
						type: 'column',
						name: '美容业绩',
						data: [lsbeaty[0]*1, lsbeaty[1]*1, lsbeaty[2]*1,lsbeaty[3]*1, lsbeaty[4]*1,lsbeaty[5]*1, lsbeaty[6]*1, lsbeaty[7]*1, lsbeaty[8]*1, lsbeaty[9]*1,lsbeaty[10]*1, lsbeaty[11]*1, lsbeaty[12]*1,lsbeaty[13]*1, lsbeaty[14]*1]
					}, {
						type: 'pie',
						name: 'Total consumption',
						data: [ {
							name: '美发业绩',
							y: sumHair,
							color: '#AA4643' // John's color
						}, {
							name: '美容业绩',
							y: sumBeaty,
							color: '#89A54E' // Joe's color
						}],
						center: [100, 80],
						size: 100,
						showInLegend: false,
						dataLabels: {
							enabled: false
						}
					}]
				});
				}catch(e){alert(e.message);}
	}
	function loadDataSetExcel()
	{
			var strCurCompId=getCurOrgFromSearchBar();

    		var strFromDate=document.getElementById("strFromDate").value;
    		var params = "strCurCompId="+strCurCompId;		
    		params=params+"&strFromDate="+strFromDate;	
			var excelDialog=$.ligerDialog.open({url: contextURL+'/sc013/loadSC013Excel.action?'+params });
    		setTimeout(function () { excelDialog.close(); }, 10000);
	}
	
	function loadOtherImage()
	{
		SC013Tab.selectTabItem("tabitem3");
		var lsDataInfo=new Array();
		var bAvgNum=0;
		var aAvgNum=0;
		var sAvgNum=0;
		var charTitle=checkNull(curRecord.strPrjName);
		if(checkNull(curRecord.strPrjName)=="")
		{
			for(var i=0;i<12;i++)
			{
				lsDataInfo[i]=1;
			}
			bAvgNum=1;
			aAvgNum=1;
			sAvgNum=1;
			loadTab3Image(lsDataInfo,bAvgNum,aAvgNum,sAvgNum,charTitle,'');
		}
		else
		{
			if(checkNull(curRecord.resusttyep)==1 
			|| checkNull(curRecord.resusttyep)==2 
			|| checkNull(curRecord.resusttyep)==3
			|| checkNull(curRecord.resusttyep)==5
			|| checkNull(curRecord.resusttyep)==9
			|| checkNull(curRecord.resusttyep)==42)
			{
				lsDataInfo[0]=ForDight(checkNull(curRecord.month1num)*1/10000,2);
            	lsDataInfo[1]=ForDight(checkNull(curRecord.month2num)*1/10000,2);
            	lsDataInfo[2]=ForDight(checkNull(curRecord.month3num)*1/10000,2);
            	lsDataInfo[3]=ForDight(checkNull(curRecord.month4num)*1/10000,2);
            	lsDataInfo[4]=ForDight(checkNull(curRecord.month5num)*1/10000,2);
            	lsDataInfo[5]=ForDight(checkNull(curRecord.month6num)*1/10000,2);
            	lsDataInfo[6]=ForDight(checkNull(curRecord.month7num)*1/10000,2);
            	lsDataInfo[7]=ForDight(checkNull(curRecord.month8num)*1/10000,2);
            	lsDataInfo[8]=ForDight(checkNull(curRecord.month9num)*1/10000,2);
            	lsDataInfo[9]=ForDight(checkNull(curRecord.month10num)*1/10000,2);
            	lsDataInfo[10]=ForDight(checkNull(curRecord.month11num)*1/10000,2);
            	lsDataInfo[11]=ForDight(checkNull(curRecord.month12num)*1/10000,2);
            	
            	bAvgNum=ForDight(checkNull(curRecord.totalavgbefor)*1/10000,2);
            	aAvgNum=ForDight(checkNull(curRecord.totalavgafter)*1/10000,2);
            	sAvgNum=ForDight(checkNull(curRecord.compavgbymonth )*1/10000,2);
            	loadTab3Image(lsDataInfo,bAvgNum,aAvgNum,sAvgNum,charTitle,'W');
			}
			else if(checkNull(curRecord.month1num).indexOf("%")==-1) 
            {
            	lsDataInfo[0]=ForDight(checkNull(curRecord.month1num)*1,0);
            	lsDataInfo[1]=ForDight(checkNull(curRecord.month2num)*1,0);
            	lsDataInfo[2]=ForDight(checkNull(curRecord.month3num)*1,0);
            	lsDataInfo[3]=ForDight(checkNull(curRecord.month4num)*1,0);
            	lsDataInfo[4]=ForDight(checkNull(curRecord.month5num)*1,0);
            	lsDataInfo[5]=ForDight(checkNull(curRecord.month6num)*1,0);
            	lsDataInfo[6]=ForDight(checkNull(curRecord.month7num)*1,0);
            	lsDataInfo[7]=ForDight(checkNull(curRecord.month8num)*1,0);
            	lsDataInfo[8]=ForDight(checkNull(curRecord.month9num)*1,0);
            	lsDataInfo[9]=ForDight(checkNull(curRecord.month10num)*1,0);
            	lsDataInfo[10]=ForDight(checkNull(curRecord.month11num)*1,0);
            	lsDataInfo[11]=ForDight(checkNull(curRecord.month12num)*1,0);
            	
            	bAvgNum=ForDight(checkNull(curRecord.totalavgbefor)*1,0);
            	aAvgNum=ForDight(checkNull(curRecord.totalavgafter)*1,0);
            	sAvgNum=ForDight(checkNull(curRecord.compavgbymonth)*1,0);
            	loadTab3Image(lsDataInfo,bAvgNum,aAvgNum,sAvgNum,charTitle,'');
            	
            	
            }
            else
            {
            	lsDataInfo[0]=ForDight(checkNull(curRecord.month1num).replace("%","")*1,2);
            	lsDataInfo[1]=ForDight(checkNull(curRecord.month2num).replace("%","")*1,2);
            	lsDataInfo[2]=ForDight(checkNull(curRecord.month3num).replace("%","")*1,2);
            	lsDataInfo[3]=ForDight(checkNull(curRecord.month4num).replace("%","")*1,2);
            	lsDataInfo[4]=ForDight(checkNull(curRecord.month5num).replace("%","")*1,2);
            	lsDataInfo[5]=ForDight(checkNull(curRecord.month6num).replace("%","")*1,2);
            	lsDataInfo[6]=ForDight(checkNull(curRecord.month7num).replace("%","")*1,2);
            	lsDataInfo[7]=ForDight(checkNull(curRecord.month8num).replace("%","")*1,2);
            	lsDataInfo[8]=ForDight(checkNull(curRecord.month9num).replace("%","")*1,2);
            	lsDataInfo[9]=ForDight(checkNull(curRecord.month10num).replace("%","")*1,2);
            	lsDataInfo[10]=ForDight(checkNull(curRecord.month11num).replace("%","")*1,2);
            	lsDataInfo[11]=ForDight(checkNull(curRecord.month12num).replace("%","")*1,2);
            	
            	bAvgNum=ForDight(checkNull(curRecord.totalavgbefor).replace("%","")*1,2);
            	aAvgNum=ForDight(checkNull(curRecord.totalavgafter).replace("%","")*1,2);
            	sAvgNum=ForDight(checkNull(curRecord.compavgbymonth ).replace("%","")*1,2);
            	loadTab3Image(lsDataInfo,bAvgNum,aAvgNum,sAvgNum,charTitle,'%');
            }
		}
	}
	
            				
	
	function loadTab3Image(lsDataInfo,bAvgNum,aAvgNum,sAvgNum,charTitle,unit)
	{
		var colors = Highcharts.getOptions().colors;
		var chart = new Highcharts.Chart({
					chart: {
						renderTo: 'containerNum'
					},
					title: {
						text: charTitle
					},
					xAxis: {
						categories: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月','十二月']
					},
					yAxis: {
            			title: { text: '业绩单位(W/%)' }
        			},
					labels: {
						items: [{
							html: '门店业绩分布图',
							style: {
								left: '40px',
								top: '8px',
								color: 'black'				
							}
						}]
					},
					plotOptions: {
						column: {
							dataLabels: {
								enabled: true,
								color: colors[0],
								style: {fontWeight: 'bold'},
								formatter: function() {return this.y+unit;}
								}
								}
					},
					series: [{
						type: 'column',
						name: '总虚业绩',
						data: [lsDataInfo[0]*1, lsDataInfo[1]*1, lsDataInfo[2]*1,lsDataInfo[3]*1, lsDataInfo[4]*1,lsDataInfo[5]*1, lsDataInfo[6]*1, lsDataInfo[7]*1, lsDataInfo[8]*1, lsDataInfo[9]*1,lsDataInfo[10]*1, lsDataInfo[11]*1]
					}, 
					{
						type: 'spline',
						name: '总店前5名平均',
						data: [bAvgNum, bAvgNum,bAvgNum,bAvgNum,bAvgNum,bAvgNum, bAvgNum,bAvgNum,bAvgNum,bAvgNum,bAvgNum,bAvgNum]
					}, 
					{
						type: 'spline',
						name: '本店月平均',
						data: [sAvgNum, sAvgNum,sAvgNum,sAvgNum,sAvgNum,sAvgNum, sAvgNum,sAvgNum,sAvgNum,sAvgNum,sAvgNum,sAvgNum]
					}, 
					{
						type: 'spline',
						name: '总店后5名平均',
						data: [aAvgNum, aAvgNum,aAvgNum,aAvgNum,aAvgNum,aAvgNum, aAvgNum,aAvgNum,aAvgNum,aAvgNum,aAvgNum,aAvgNum]
					}
					]
				});
				
	}
	
	
