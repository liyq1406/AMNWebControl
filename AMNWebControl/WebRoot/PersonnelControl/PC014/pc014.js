var intention=null;//添加培训    
var complyid="";
var inteinobillid="";
var intetiondetails=null;
var commoninfodivdetialtj=null;
var PC014Tab=null;
var staffPostionData=JSON.parse(parent.loadCommonControlDate_select("GZGW",0));
var classpxData=JSON.parse(parent.loadCommonControlDate_select("PXKC",0));
var curRecode=null;
var pc014layout=null;
var curstate=3;
$(function () {  
    	 	try{
    	 	
    	 pc014layout= $("#pc014layout").ligerLayout({ rightWidth: 400,  allowBottomResize: false, allowLeftResize: false});
	   	 $("#PC014Tab").ligerTab();
         PC014Tab = $("#PC014Tab").ligerGetTabManager();
         intention=$("#intention").ligerGrid({
                columns: [
                { display: '门店编号', name: 'intcomplyno',  width: 70 },
                { display:'岗位课程', name:'intdproject', width: 100,
			                editor: { type: 'select', data: classpxData, valueField: 'choose',selectBoxHeight:'220'},
				            	render: function (item)
				              	{
				              		var lsZw=parent.gainCommonInfoByCode("PXKC",0);
				              		for(var i=0;i<lsZw.length;i++)
									{
										if(lsZw[i].bparentcodekey==item.intdproject)
										{	
											return lsZw[i].parentcodevalue;								
									    }
									}
				                    return '';
				                } 
				                },
                {display:'阶段',  name:'intdstage ',width: 100,
				                	render: function (item)
					              	{
					                  if (item.intdstage == 0) return '无';
					                  else if (item.intdstage ==1) return '第一阶段';
				 	                  else if (item.intdstage == 2) return '第二阶段';
					                  else if (item.intdstage == 3) return '第三阶段';
				 	                  else if (item.intdstage == 4) return '第四阶段';
				                      else if (item.intdstage == 5) return '其他';
					                }},
                { display: '登记人', name: 'intuser',  width: 80  } ,
                { display: '登记日期', name: 'intdata',  width: 100 }
                ], 
                pageSize:10, 
                width: 500,
                height:'98%', 
                enabledEdit: true,
                rownumbers:true,
                usePager:false,
                 onSelectRow : function (data, rowindex, rowobj)
                {
                	intentiondetails(data.intbillid)
                } 
            })
            intetiondetails=$("#intetiondetails").ligerGrid({
                columns: [
                		   { display:'员工编号', name:'instaffno', width: 80},
                		   { display:'员工姓名', name:'instaffname', width: 60},
                		   { display:'手册号码', name:'intstuno', width: 90},
					       { display: '身份证',  name:'incardno', width: 120},
					       { display: '出生日期',  name:'intbirthday', width: 90},
					       { display: '职位',  name:'intposition', width: 90 ,
			                editor: { type: 'select', data: staffPostionData, valueField: 'choose',selectBoxHeight:'220'},
				            	render: function (item)
				              	{
				              		var lsZw=parent.gainCommonInfoByCode("GZGW",0);
				              		for(var i=0;i<lsZw.length;i++)
									{
										if(lsZw[i].bparentcodekey==item.intposition)
										{	
											return lsZw[i].parentcodevalue;								
									    }
									}
				                    return '';
				                } 
	                        },
					       { display:'成绩', name:'intdscore', width: 50,
								          render: function (item)
				              	{
				                  if (item.intdscore == 0) return '不合格';
				                  else if (item.intdscore ==1) return '合格';
				                }  },
					       { display:'建议职位', name:'intpositions', width: 70,
								          render: function (item)
				              	{
				                  if (item.intpositions == 0) return '初级技师';
				                  else if (item.intpositions ==1) return '高级技师';
			 	                  else if (item.intpositions == 2) return '预备发型师';
				                  else if (item.intpositions == 3) return '发型师';
			 	                  else if (item.intpositions == 4) return '首席';
			                      else if (item.intpositions == 5) return '总监';
				                  else if (item.intpositions == 6) return '美发经理';
				                }  },
				           { display:'选修课名称', name:'intdproname', width:90},
					       { display:'奖罚', name:'intdpunish ', width: 100}
					       ],
					        showTableToggleBtn:true,
			                frozen:false,
			                pageSize:10, 
			                width: 860,
			                height:345, 
			                enabledEdit: true,
			                usePager:false  ,
			                onContextmenu : function (parm,e)
			                {
			                	 curRecode=parm.data;
			                	 menu.show({ top: e.pageY, left: e.pageX });
			                     return false;
			                } 
            });  
            
              menu = $.ligerMenu({ width: 120, items:
	            [
	            	{ text: '删除当前记录', click: deletePXRecord, icon: 'delete' , img: contextURL+'/common/ligerui/ligerUI/skins/icons/candle.gif'}
	            ]
	            }); 
            
            commoninfodivdetialtj=$("#commoninfodivdetialtj").ligerGrid({
                columns: [
                			{ display:'门店编号', name:'intdcomplyno', width: 80},
                			{ display:'门店名称', name:'intdcomplyname', width: 80},
                 		   { display:'员工编号', name:'instaffno', width: 80},
                		   { display:'员工姓名', name:'instaffname', width: 110},
                		   { display:'手册号码', name:'intstuno', width: 90},
					       { display: '身份证',  name:'incardno', width: 120},
					       { display: '出生日期',  name:'intbirthday', width: 90},
					       { display: '职位',  name:'intposition', width: 90,
				            	render: function (item)
				              	{
				              		var lsZw=parent.gainCommonInfoByCode("GZGW",0);
				              		for(var i=0;i<lsZw.length;i++)
									{
										if(lsZw[i].bparentcodekey==item.intposition)
										{	
											return lsZw[i].parentcodevalue;								
									    }
									}
				                    return '';
				                } 
				            },
				            { display:'岗位课程', name:'intdproject', width: 100,
			                		editor: { type: 'select', data: classpxData, valueField: 'choose',selectBoxHeight:'220'},
				            	render: function (item)
				              	{
				              		var lsZw=parent.gainCommonInfoByCode("PXKC",0);
				              		for(var i=0;i<lsZw.length;i++)
									{
										if(lsZw[i].bparentcodekey==item.intdproject)
										{	
											return lsZw[i].parentcodevalue;								
									    }
									}
				                    return '';
				                } 
				                },
                			{display:'阶段',  name:'intdstage ',width: 100,
				                	render: function (item)
					              	{
					                  if (item.intdstage == 0) return '无';
					                  else if (item.intdstage ==1) return '第一阶段';
				 	                  else if (item.intdstage == 2) return '第二阶段';
					                  else if (item.intdstage == 3) return '第三阶段';
				 	                  else if (item.intdstage == 4) return '第四阶段';
				                      else if (item.intdstage == 5) return '其他';
					                }
					        },
					        { display: '培训开始日期',  name:'intdstarttime', width: 90},
					        { display: '培训结束日期',  name:'intdendtime', width: 90},
					       { display:'成绩', name:'intdscore', width: 50,
								          render: function (item)
				              	{
				                  if (item.intdscore == 0) return '不合格';
				                  else if (item.intdscore ==1) return '合格';
				                }  },
					       { display:'建议职位', name:'intpositions', width: 70,
								          render: function (item)
				              	{
				                  if (item.intpositions == 0) return '初级技师';
				                  else if (item.intpositions ==1) return '高级技师';
			 	                  else if (item.intpositions == 2) return '预备发型师';
				                  else if (item.intpositions == 3) return '发型师';
			 	                  else if (item.intpositions == 4) return '首席';
			                      else if (item.intpositions == 5) return '总监';
				                  else if (item.intpositions == 6) return '美发经理';
				                }  },
				           { display:'选修课名称', name:'intdproname', width:90},
				         
					       { display:'奖罚', name:'intdpunish ', width: 120},
					       { display:'备注', name:'intdremark ', width: 120}
	            
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '100%',
                height:'600',
                clickToEdit: false,   enabledEdit: false, usePager: true
            });
            
           $("#intbirthday").ligerDateEditor({ showTime:true,width:120,format: "yyyy-MM-dd",labelWidth: 80, labelAlign: 'right' });
           $("#intdendtime").ligerDateEditor({ showTime:true,width:160,format: "yyyy-MM-dd",labelWidth: 80, labelAlign: 'right' });
           $("#intdstarttime").ligerDateEditor({ showTime:true,width:160,format: "yyyy-MM-dd",labelWidth: 80, labelAlign: 'right' });
           $("#intentions").ligerToolBar({ items: 
            [{ text:'培训经历' ,img: contextURL+'/common/ligerui/ligerUI/skins/icons/msn.gif'},
            { text: '新增',click:addIntention, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
            { text: '保存',click:addIntentions, img: contextURL+'/common/ligerui/ligerUI/skins/icons/save.gif' },
            { text: '刷新', click:refresh, img: contextURL+'/common/ligerui/ligerUI/skins/icons/refresh.gif' }] });
           $("#intentionss").ligerToolBar({ items: 
            [{ text:'培训课程信息' ,img: contextURL+'/common/ligerui/ligerUI/skins/icons/msn.gif'}] });
            
             $("#intentionsshops").ligerToolBar({ items: 
            [{ text:'培训门店编号' ,img: contextURL+'/common/ligerui/ligerUI/skins/icons/msn.gif'}] });
            
           $("#intentionsss").ligerToolBar({ items: 
            [{ text:'学员培训信息' ,img: contextURL+'/common/ligerui/ligerUI/skins/icons/msn.gif'}] });
            $("#editaddDetailInfo").ligerButton( { text: '载入列表', width: 120,click:addeditadd});
             var calssstypes=parent.gainCommonInfoByCode("PXKC",0);
		    for(var i=0;i<calssstypes.length;i++)
			{
				addOption(calssstypes[i].bparentcodekey,calssstypes[i].parentcodevalue,document.getElementById("iSearchIintdproject"));
				addOption(calssstypes[i].bparentcodekey,calssstypes[i].parentcodevalue,document.getElementById("intdproject"));
			}	
              
	         $("#searchTJButton").ligerButton(
	         {
	             text: '统计查询', width: 100,
		         click: function ()
		         {
		             searchTjInfo();
		         }
	         });
	         
            showintention();
    	 	}catch(e){alert(e.message);}
        });
	function selectbyp(){
	try{	 
				 var   params="intcomplyno="+document.getElementById("intcomplynos").value;
				 var   params=params+"&intstuno="+document.getElementById("intstunos").value;
				 var   params=params+"&incardno="+document.getElementById("incardnos").value;
				 var   params=params+"&instaffno="+document.getElementById("instaffnos").value;
			     var   requestUrl =contextURL+"/pc014/selecbyparmar.action?";
			    	   $.ajax({
				                type: 'POST',
				                url: requestUrl+params,
				                data: null,
				                dataType: 'json',
				                success: function (data) { 
								if(data.lsIntentionBean!=null && data.lsIntentionBean.length>0)
								{
									var btndate={Rows:data.lsIntentionBean,Total:data.lsIntentionBean.length};	
									intention.options.data=$.extend(true, {},btndate);
            						intention.loadData(true);	
            						intetiondetails.options.data=$.extend(true, {},btndate);
	            				    intetiondetails.loadData(true);	
								}
								else
								{
									intention.options.data=$.extend(true, {},{Rows:null,Total:0});
            						intention.loadData(true);	
            						intetiondetails.options.data=$.extend(true, {},{Rows:null,Total:0});
	            					intetiondetails.loadData(true);	
								}
								 	document.getElementById("intcomplynos").value="";	
			    		   			document.getElementById("intcomplynos").value="";	
			    		   			document.getElementById("intcomplynos").value="";	
			    		   			document.getElementById("intcomplynos").value="";	
							},
							asynchronous:true
						});			  
    	   		}catch(e){  alert(e.message);
    	   }
    	}

function addIntentions()
     {
     	if(curstate==3)
     	{
     		 $.ligerDialog.error("该单据不在保存状态,请确认");
     		 return;
     	}
		var     curjosnparam="";
        var     needReplaceStr="";
        var     strJsonParam_detial="";
        var     params="intcomplyno="+document.getElementById("intcomplyno").value;
				params=params+"&intbillid="+document.getElementById("intbillid").value;
			    params=params+"&intdproject="+ document.getElementById("intdproject").value;
				params=params+"&intdstage="+document.getElementById("intdstage").value;
				params=params+"&intdstarttime="+document.getElementById("intdstarttime").value;
				params=params+"&intdendtime="+document.getElementById("intdendtime").value;
				params=params+"&intdproname="+document.getElementById("intdproname").value;
				params=params+"&intdata="+document.getElementById("intdata").value;
	        for (var rowid in intetiondetails.records)
			{
					var row =intetiondetails.records[rowid];
					curjosnparam=JSON.stringify(row);
					/*if(curjosnparam.indexOf("_id")>-1)
					{
					   	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
					   	curjosnparam=curjosnparam.replace(needReplaceStr,"");
					}*/	            		   
					if(strJsonParam_detial!="")
					  	strJsonParam_detial=strJsonParam_detial+",";
					strJsonParam_detial= strJsonParam_detial+curjosnparam;
			}            		 
		if(strJsonParam_detial!="")
		{
			 params=params+"&strJsonParam=["+strJsonParam_detial+"]";
		}
		
		if(strJsonParam_detial==""){
			 $.ligerDialog.warn("请载入学员培训信息！");
			return;
		}
		if(document.getElementById("intbillid").value=="" && document.getElementById("intdata").value=="" ){
			$.ligerDialog.warn("请先点击“新增”按钮再添加数据！");
			return;
		}
	 	var     requestUrl ="pc014/addintentions.action";
		var 	responseMethod="postMessage";
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
    function postMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        	
	        	 pageReadState();
	        	 $.ligerDialog.success("保存成功!");
	        	 curstate=3;
	        }
	        else
	        {
	        	alert(strMessage);
	        }
        }
		catch(e)
		{
			alert(e.message);
		}
    }
    
  function pageReadState()
    {
    	try
	    {
    		document.getElementById("intbillid").disabled=true;
	   	    document.getElementById("intdata").disabled=true;
	   	    document.getElementById("intdstarttime").disabled=true;
	    	document.getElementById("intdendtime").disabled=true;
	    	document.getElementById("intdproject").disabled=true;
    		document.getElementById("intdstage").disabled=true;
//	    	document.getElementById("intdwaite").disabled=true;
//	    	document.getElementById("intstuno").disabled=true;
//	    	document.getElementById("incardno").disabled=true;
//	    	document.getElementById("instaffno").disabled=true;
//	    	document.getElementById("instaffname").disabled=true;
//	    	document.getElementById("intposition").disabled=true;
//			document.getElementById("intbirthday").disabled=true;
//			document.getElementById("intdscore").disabled=true;
//			document.getElementById("intpositions").disabled=true;
//    		document.getElementById("intdproname").disabled=true;
//	    	document.getElementById("intdpunish").disabled=true;
//	    	document.getElementById("intdremark").disabled=true;
    	}catch(e){alert(e.message);}
    }
    
//新增行
function addIntention()
     {
	        	var row = intention.getSelectedRow();
			   			  intention.addRow({ 
				                intcomplyno: "",
				                intdproject: "",
				                intuser: "",
				                intdata: "" 
				            }, row, false);
				var params = "";			
    	     	var requestUrl ="pc014/addintention.action"; 
				var responseMethod="addMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params );
     }
 function addMessage(request)
     {
	    	try
	        {
	        	var action = eval("(" + request.responseText + ")");
	    		othermessage(action.intention);
		   		intetiondetails.options.data=$.extend(true, {},{Rows: null,Total:0});
            	intetiondetails.loadData(true);  
            	curstate=1;
		   	}catch(e){alert(e.message);}
     }
function addeditadd(){
	if(document.getElementById("instaffname").value=="" && document.getElementById("incardno").value==""&& document.getElementById("intposition").value==""){
		$.ligerDialog.warn("请确认员工编号！");
		return;
	}
	 var row = intetiondetails.getSelectedRow();
			   intetiondetails.addRow({ 
									intdcomplyno: getCurOrgFromSearchBar(),
				 					intdbillid: document.getElementById("intbillid").value,
									
									intstuno: document.getElementById("intstuno").value,
									incardno: document.getElementById("incardno").value,
									instaffno: document.getElementById("instaffno").value,
									instaffname: document.getElementById("instaffname").value,
									intposition	: document.getElementById("intposition").value,
									intbirthday: document.getElementById("intbirthday").value,	
									intdscore: document.getElementById("intdscore").value,
									intpositions: document.getElementById("intpositions").value,
									intdproname: document.getElementById("intdproname").value,
									intdpunish: document.getElementById("intdpunish").value,
									intdremark: document.getElementById("intdremark").value
					                }, row, false);
								
									document.getElementById("intstuno").value="";
									document.getElementById("incardno").value="";
									document.getElementById("instaffno").value="";
									document.getElementById("instaffname").value="";
									document.getElementById("intposition").value="";
									document.getElementById("intbirthday").value="";	
									document.getElementById("intdproname").value="";
									document.getElementById("intdpunish").value="";
									document.getElementById("intdremark").value="";
}
function showintention(){
	try{
			     var   requestUrl =contextURL+"/pc014/selectintention.action";
			    	   $.ajax({
				                type: 'POST',
				                url: requestUrl,
				                data: null,
				                dataType: 'json',
				                success: function (data) { 
								if(data.lsIntentions!=null && data.lsIntentions.length>0)
								{
									var btndate={Rows:data.lsIntentions,Total:data.lsIntentions.length};	
									intention.options.data=$.extend(true, {},btndate);
            						intention.loadData(true);	
            						intention.select(0);
								}
								else
								{
									intention.options.data=$.extend(true, {},{Rows:null,Total:0});
            						intention.loadData(true);	
								}
								document.getElementById("intcomplyno").value=checkNull(data.intention.intcomplyno);
								document.getElementById("intbillid").value=checkNull(data.intention.intbillid);
			           			document.getElementById("intdproject").value=checkNull(data.intention.intdproject);
			           			document.getElementById("intdstage").value=checkNull(data.intention.intdstage);
			           			document.getElementById("intdstarttime").value=checkNull(data.intention.intdstarttime);
			          		 	document.getElementById("intdendtime").value=checkNull(data.intention.intdendtime);
			           			document.getElementById("intdata").value=checkNull(data.intention.intdata);
							},
							asynchronous:true
						});			  
    	   		}catch(e){  alert(e.message);
    	   }
    	}
function intentiondetails(intbillid){
	try{
			     var   requestUrl =contextURL+"/pc014/selectintentions.action?";
			     var   params="intbillid="+intbillid;
			    	   $.ajax({
				                type: 'POST',
				                url: requestUrl+params,
				                data: null,
				                dataType: 'json',
				                success: function (data) { 
									document.getElementById("intbillid").value=checkNull(data.intention.intbillid);
			           				document.getElementById("intdproject").value=checkNull(data.intention.intdproject);
			           				document.getElementById("intdstage").value=checkNull(data.intention.intdstage);
			           				document.getElementById("intdstarttime").value=checkNull(data.intention.intdstarttime);
			          		 		document.getElementById("intdendtime").value=checkNull(data.intention.intdendtime);
			           				document.getElementById("intdata").value=checkNull(data.intention.intdata);
									if(data.lsIntentiondetail!=null && data.lsIntentiondetail.length>0)
									{
										 var btndate={Rows:data.lsIntentiondetail,Total:data.lsIntentiondetail.length};
										intetiondetails.options.data=$.extend(true, {},btndate);
	            						intetiondetails.loadData(true);	
	            						curstate=3;
									}
									else
									{
										intetiondetails.options.data=$.extend(true, {},{Rows:null,Total:0});
	            						intetiondetails.loadData(true);
	            						curstate=1;	
									}
								},
							asynchronous:true
						});			  
    	   		}catch(e){  alert(e.message);
    	   }
    	}

   //--------------------------员工编号和姓名-----------------
function validateInserper(obj)
   {
   		var requestUrl ="pc014/validateInserper.action";
        var params="intcomplyno="+getCurOrgFromSearchBar();
        params=params+"&strCurEmpId="+obj.value;
		var responseMethod="validateInserperMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	  
   }
function validateInserperMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var str=responsetext.strMessage;
	       	if(checkNull(str)=="")
	       	{	       		 
	       		document.getElementById("instaffname").value=responsetext.instaffname;
	       		document.getElementById("incardno").value=responsetext.incardno;
	       		document.getElementById("intposition").value=responsetext.intposition;
	       		document.getElementById("intstuno").select();
	       		document.getElementById("intstuno").focus();
	       	}
	       	else
	       	{
	       		document.getElementById("instaffname").value="";
	       		document.getElementById("incardno").value="";
	       		document.getElementById("intposition").value="";
	       		$.ligerDialog.warn(str);
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
 function othermessage(intent){
	 	document.getElementById("intcomplyno").value=checkNull(intent.intcomplyno);
	 	document.getElementById("intbillid").value=checkNull(intent.intbillid);
    	document.getElementById("intdproject").value=checkNull(intent.intdproject);
    	document.getElementById("intdstarttime").value=checkNull(intent.intdstarttime);
    	document.getElementById("intdendtime").value=checkNull(intent.intdendtime);
    	document.getElementById("intdata").value=checkNull(intent.intdata);
 }
 function refresh(){
	 window.location.reload();
 }
 
 
       //---------------------------------------------回车事件
		function hotKeyOfSelf(key)
		{
			if(key==13)//回车
			{
				hotKeyEnter();
			}
		}
		
		function  hotKeyEnter()
		{
		
			try
			{
				window.event.keyCode=9; //tab
				window.event.returnValue=true;
			}
			catch(e){alert(e.message);}
				
		}  
		
	function getCurOrgFromSearchBarself1()
	{
			var elemId = null;
			var i=2;
			var element;
			var ret="";
		try{	
			elemId = "s1level_"+i;
			element = document.getElementById(elemId);             
			while(typeof(element)!="undefined"&&element != null)
			{
				var seltext = element.options[element.selectedIndex].text;      
				var selvalue = element.options[element.selectedIndex].value;   
			                         
				if(seltext == "*"&&i==2)
				{
					ret = "001";
					return ret;
				}
				else if(seltext == "*"&&i!=2)
				{
					i--;
					tmpElemId = "s1level_"+i;
					tmpElem = document.getElementById(tmpElemId);
					var elemValue = tmpElem.options[tmpElem.selectedIndex].value;
					while(elemValue == "no")
					{
						i--;
						if(i>1)
						{
							tmpElemId = "s1level_"+i;
							tmpElem = document.getElementById(tmpElemId);
							elemValue = tmpElem.options[tmpElem.selectedIndex].value;
						}
						else if(i == 1)
						{
							ret = "001";
							return ret;
						}
						
					}
					ret = elemValue;//tmpElem.options[tmpElem.selectedIndex].value;
				
					return ret;
				}
				i++;                                            
				elemId = "s1level_"+i;                            
				element = document.getElementById(elemId);      
			}
		
			i--;
			tmpElemId = "s1level_"+i;
			tmpElem = document.getElementById(tmpElemId);
			ret = tmpElem.options[tmpElem.selectedIndex].value;
		}catch(Exception)	{alert(Exception.message);}
			return ret;
	}
       //加载卡入库主明细
    function searchTjInfo()
    {
    	var strCurCompId=getCurOrgFromSearchBarself1();
	
 
    	var params = "strCompId="+strCurCompId;		
    	params=params+"&iProjectno="+document.getElementById("iSearchIintdproject").value;	
    	params=params+"&strEmpId="+document.getElementById("strSearchStaffno").value;
     	var requestUrl ="pc014/searchTntentionInfo.action"; 
		var responseMethod="searchTntentionInfoMessage";	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
   function searchTntentionInfoMessage(request)
   {
       		try
        	{
        
 			curpagestate=3;
        	var responsetext = eval("(" + request.responseText + ")");
        	if(responsetext.lsIntentiondetail!=null && responsetext.lsIntentiondetail.length>0)
			{
				commoninfodivdetialtj.options.data=$.extend(true, {},{Rows: responsetext.lsIntentiondetail,Total: responsetext.lsIntentiondetail.length});
				commoninfodivdetialtj.loadData(true);   
					           
			}
			else
			{
				commoninfodivdetialtj.options.data=$.extend(true, {},{Rows: null,Total:0});
				commoninfodivdetialtj.loadData(true);  
			
			}
			
	   		}catch(e){alert(e.message);}
    }
    
    function deletePXRecord()
    {
    	intetiondetails.deleteSelectedRow();
    }