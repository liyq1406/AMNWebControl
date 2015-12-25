
            var tab = null;
            var accordion = null;
            var tree = null;
            
            $(function ()
            {
				
                //布局
                $("#layout1").ligerLayout({  height: '100%',heightDiff:-10,space:4, onHeightChanged: f_heightChanged });

                var height = $(".l-layout-center").height();

                //Tab
                $("#framecenter").ligerTab({ height: height ,dblClickToClose :true});

                //面板
                $("#accordion1").ligerAccordion({ height: height - 24, speed: null });

                $(".l-link").hover(function ()
                {
                    $(this).addClass("l-link-over");
                }, function ()
                {
                    $(this).removeClass("l-link-over");
                });
               
                tab = $("#framecenter").ligerGetTabManager();
          
                //accordion = $("#accordion1").ligerGetAccordionManager();               
         
                //---------------------------------------------右侧面板营业分析 start
				$("#dataAnalysis").ligerTab({ onBeforeSelectTabItem: function (tabid)
	            {

	                dataAnalysis_before( tabid);
	            }, onAfterSelectTabItem: function (tabid)
	            {
	                dataAnalysis_after( tabid);
	            } 
	            });
	            //---------------------------------------------右侧面板公告start
				$("#dataNotice").ligerTab({ onBeforeSelectTabItem: function (tabid)
	            {	               
	            }, onAfterSelectTabItem: function (tabid)
	            {	               
	            } 
	            });
	            //----------------------日历start
	           /* $("input.mh_date").manhuaDate({					       
					Event : "click",//可选				       
					Left : 0,//弹出时间停靠的左边位置
					Top : -16,//弹出时间停靠的顶部边位置
					fuhao : "-",//日期连接符默认为-
					isTime : false,//是否开启时间值默认为false
					beginY : 2010,//年份的开始默认为1949
					endY :2015//年份的结束默认为2049
				});
				$("input.mh_date").click();
				document.getElementById(id="topcurdate" ).value="";*/
				//----------------------日历end
		
				$("#pageloading").hide();
	       		
            });
           
		
            function f_heightChanged(options)
            {
                if (tab)
                    tab.addHeight(options.diff);
                if (accordion && options.middleHeight - 24 > 0)
                    accordion.setHeight(options.middleHeight - 24);
            }
            function f_addTab(tabid,text, url)
            { 
                tab.addTabItem({ tabid : tabid,text: text, url: url });
            } 
            
            $(document).ready(function(){	
				//-------------------------日历
				$(".datepicker").datePicker({
					inline:true,
					selectMultiple:false	
					
				}); 
				//-------------------------公告
				jQuery(document).ready(function($){   
				$("#twitter li:not(:first)").css("display","none");   
				var B=$("#twitter li:last");   
				var C=$("#twitter li:first");   
				setInterval(function(){   
				if(B.is(":visible")){   
				C.fadeIn(1000).addClass("in");B.hide()   
				}else{   
				$("#twitter li:visible").addClass("in");   
				$("#twitter li.in").next().fadeIn(1000);   
				$("li.in").hide().removeClass("in")}   
				},5000) //每5秒钟切换一条，你可以根据需要更改   
				}) ;  
				//--------------------功能菜单				
				$("#mozu a").hover(function(){
					$(this).animate({
						marginTop: "-10px"
					}, 250);
				}, function(){
					$(this).animate({
						marginTop: "0px"
					}, 250);
				});
			});
			
			function dataAnalysis_before(tabid)
			{
				
				
			}
			function dataAnalysis_after(tabid)
			{
				if(tabid=="tabitem1")//点击营业分析显示事件
				{
					
				}
				else//点击单据分析显示事件
				{
					
				}
			}
			//---------------------------------------------右侧面板分析 end
			
			//---------------------------------------------右侧图形报表 start
				
				function drawBarChart(cTitle, cXTitle, cYTitle, cXValue, cYValue,topdiv) {
				
				// cTitle 标题；cXTitle x轴标题, cYTitle y轴标题, cXValue x轴值, cYValue y轴值；
				var yValues = cYValue.split(',');
				var yArray = new Array();
				for (i = 0; i < yValues.length; i++) {
					yArray[i] = formatFloat(Number(yValues[i]), 2);
				};
				
				var chart = new top.Highcharts.Chart({
							chart : {
								renderTo : 'pic_div',
								defaultSeriesType : 'bar'
							},
							title : {
								text : cTitle
							},
							xAxis : {
								categories : cXValue.split(','),
								title : {
									text : cXTitle
								}
							},
							yAxis : {
								min : 0,
								title : {
									text : cYTitle,
									align : 'high'
								}
							},
							tooltip : {
								formatter : function() {
									return '' + this.series.name + ': ' + this.y;
								}
							},
							plotOptions : {
								bar : {
									dataLabels : {
										enabled : true
									}
								}
							},
							legend : {
								enabled : false
							},
							credits : {
								enabled : false
							},
							series : [{
										name : cYTitle,
										data : yArray
									}]
						});
				
				showPic(topdiv);
			}
			function drawMultiBarPic(cTitle, cXTitle, cYTitle, cXValue, cSeriesName,cSeriesValue,curdiv,topdiv) {
				// cTitle 标题；cXTitle x轴标题, cYTitle y轴标题, cXValue x轴值, cSeriesName 序列名,
				// cSeriesValue 序列值
				try
				{
		
				var seriesNames = cSeriesName.split(',');
				var seriesValues = cSeriesValue.split(';');
				var seriesData = new Array();
				for (i = 0; i < seriesNames.length; i++) {
					var valueArray = new Array();
					var seriesValue = seriesValues[i].split(',');
					for (j = 0; j < seriesValue.length; j++) {
						valueArray[j] = formatFloat(Number(seriesValue[j]), 2);
					};
					seriesData.push({
								name : seriesNames[i],
								data : valueArray
							});
				}
			
				var chart = new top.Highcharts.Chart({
							chart : {
								renderTo : curdiv,
								defaultSeriesType : 'bar'
							},
							title : {
								text : cTitle
							},
							xAxis : {
								categories : cXValue.split(','),
								title : {
									text : cXTitle
								}
							},
							yAxis : {
								min : 0,
								title : {
									text : cYTitle
								}
							},
							legend : {
								backgroundColor : '#FFFFFF',
								reversed : true
							},
							tooltip : {
								formatter : function() {
									return '' + this.series.name + ': ' + this.y + '';
								}
							},
							plotOptions : {
								series : {
									stacking : 'normal'
								}
							},
							series : seriesData
						});
					
		
				}catch(e){alert(e.message);}
			}
			
			
			function formatFloat(src, pos) {
				return Math.round(src * Math.pow(10, pos)) / Math.pow(10, pos);
			}
			
			drawBarChart('', '', '金额', '现金,指付通,Ok卡,团购卡,储值消费,疗程消费,项目抵用券,现金抵用券,条码卡,美容虚业绩,美发虚业绩,烫染虚业绩,美甲虚业绩,总业绩', 
					'500,100,200,150,1600,300,400,500,600,1000,2000,1200,800,3800','#pic_show_div');
			
			function changeBarChartByDate(date)
			{	
				var strYear=date.getFullYear();
				var strMonth=date.getMonth();
				if(strMonth*1<10)
					strMonth="0"+strMonth;
				var strDay=date.getDate();
				if(strDay*1<10)
					strDay="0"+strDay;
				alert(strYear+strMonth+strDay);	
				drawBarChart('', '', '金额', '现金,指付通,Ok卡,团购卡,储值消费,疗程消费,项目抵用券,现金抵用券,条码卡,美容虚业绩,美发虚业绩,烫染虚业绩,美甲虚业绩,总业绩', 
					'1500,100,200,150,1200,300,400,500,600,300,2000,500,1200,1400','#pic_show_div');
			}	
			
			
//---------------------------------------------右侧图形报表 end