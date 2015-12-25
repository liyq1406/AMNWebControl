
   	var compTree = null;//主明细	
   	var compTreeManager = null;//主明细	
   	var commoninfodivsecond=null;//员工明细
   	var curCompid="";
   	var curStaffid="";
   	var yearcard=null;
   	var lsStaffinfo=null;
   	//var projectXMLBData=JSON.parse(parent.loadCommonControlDate_select("XMLB",0));
   	//var projectJGCWData=JSON.parse(parent.loadCommonControlDate_select("JGCW",0));
   	//var projectXMTJData=JSON.parse(parent.loadCommonControlDate_select("XMTJ",0));
   	//var chooseData = [{ choose: 1, text: '启' }, { choose: 2, text: '禁'}];
   	var curProjectInfoDate=null;
   	var bc006layout=null;
    $(function ()
   	{
	   try
	   {
	   		  //布局
            bc006layout= $("#bc006layout").ligerLayout({ leftWidth: 270, allowBottomResize: false, allowLeftResize: false, isLeftCollapse:true });
             var height = $(".l-layout-center").height();
             
             $(".l-layout-center").css("width","950px");
             $(".l-layout-center").css("marginRight","0px");
             
             $(".l-layout-right").css("width","300px");
             $(".l-layout-right").css("marginLeft","-80px");
	   		//门店树形结构
   			compTree=$("#companyTree").ligerTree(
        	{
           		data: parent.complinkInfo,
           		idFieldName :'id',           		
           		onSelect: compSelect,
           		nodeWidth: 140,
           		slide: false,
           		checkbox: false,
           		height:'100%'
          	});
   			
   			yearcard=$("#yearcard").ligerGrid({
   				columns:[
   				         	{display : '卡号',name : 'phone',width : 120,align : 'left'},
   				         	{display : '金额',name : 'amt',width : 60,align : 'left',
   				         		editor: { type: 'int'}	
   				         	}
   				         ],
   				      data:null,      
   	                width: '300',
   	                height:'220',
   	                clickToEdit: true,   enabledEdit: true,  
   	                rownumbers:false,usePager:false, /*where : f_getWhere(),*/
   	                onSelectRow:null
   			});
   			
          	compTreeManager = $("#companyTree").ligerGetTreeManager();
          	
        	commoninfodivsecond=$("#commoninfodivsecond").ligerGrid({
                columns : [ {
							display : '名称',
							name : 'itemname',
							width : 150,
							align : 'left'
						}, {
							display : '有效时间',
							name : 'validate',
							width : 120,
							align : 'center'
						}, {
							display : '剩余次数',
							name : 'synum',
							width : 60,
							align : 'center'
						}, {
							display : '剩余金额',
							name : 'syamt',
							width : 60,
							align : 'center'
						},{
							name:'firstempno',
							display : '第一',
							width : 60,
							editor: { type: 'text', data: null}
						}
						,{
							hide : true,
							name : 'num',
							width : 1
						}, {
							hide : true,
							name : 'itemno',
							width : 1
						}, {
							hide : true,
							name : 'iteminid',
							width : 1
						}, {
							hide : true,
							name : 'phone',
							width : 1
						},
						{
							hide : true,
							name : 'packno',
							width : 1
						},
						{
							name:'firstperf',
							display : '金额',
							width : 60,
							editor: { type: 'int', data: null}
						},
						{
							name:'sendempno',
							display : '第二',
							width : 60,
							editor: { type: 'text', data: null}
						},
						{
							name:'sendperf',
							display : '金额',
							width : 60,
							editor: { type: 'int', data: null}
						},
						{
							name:'threeempno',
							display : '第三',
							width : 60,
							editor: { type: 'text', data: null}
						},
						{
							name:'threeperf',
							display : '金额',
							width : 60,
							editor: { type: 'int', data: null}
						},
						{
							name:'fourempno',
							display : '烫染',
							width : 60,
							editor: { type: 'text', data: null}
						},
						{
							name:'fourperf',
							display : '金额',
							width : 60,
							editor: { type: 'int', data: null}
						},
						{
							hide:true,
							display:'状态',
							name : 'itemstate',
							width : 50,
							render: function (item)
			              	{
			              		if(item.itemstate==1)
			              		{
			              			return '停用';
			              		}
			              		else
			              		{
			              			return '正常';
			              		}
			                } 
						},
						{
							hide : true,
							name : 'amt',
							width : 1
						}
                ],  pageSize:50, 
                data:null,      
                width: bc006layout.centerWidth*1+bc006layout.leftWidth*1-30,
                height:'100%',
                checkbox : true,
                onBeforeCheckRow : f_onCheckRow,
                onCheckAllRow : f_allCheckRow,
                clickToEdit: true,   enabledEdit: true,  
                rownumbers:false,usePager:false /*where : f_getWhere(),*/
                /*onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                }
                toolbar: { items: [
                { text: 'Excel导出', click: itemclick_projectInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/attibutes.gif' }]
                } */  
            });
          	$("#pageloading").hide(); 
          	f_selectNode();
   		}catch(e){alert(e.message);}
    });
   
    function f_selectNode()
	{
		var parm = function (data)
		{
		   	return data.id== parent.localCompid;
		};		
		compTree.selectNode(parm);
	}
	//加载门店信息
	function compSelect(note)
    {
		curCompid=note.data.id;
        try{
       		var params = "strCurCompId="+note.data.id;				
     		var requestUrl ="cc019/loadEmpNo.action"; 
			var responseMethod="loadEmpNoMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params );
          }catch(e){alert(e.message);}
    }
    function loadEmpNoMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
    		lsStaffinfo=responsetext.lsStaffinfo;
	   	}catch(e){alert(e.message);}
    }
    
    
    function loadCommonDataGridByStaffInfo(lsStaffinfos)
	{
			try
			{
				var strJson = "";//'{ "name": "cxh", "sex": "man" }';
				var ccount=0;
				if(lsStaffinfos!=null && lsStaffinfos.length>0)
				{	
					for(var i=0;i<lsStaffinfos.length;i++)
					{
							ccount=ccount*1+1;
							if(ccount==1)
							{
								strJson=strJson+'[';
							}
							else
							{
								strJson=strJson+',';
							}
							strJson=strJson+'{ "choose":"'+lsStaffinfos[i].bstaffno+'", "text": "'+lsStaffinfos[i].bstaffno+'_'+lsStaffinfos[i].staffname+'"}';
						
					}
					if(strJson!="")
					{
						strJson=strJson+']';
						return JSON.parse(strJson);
					}
					return null;
					 
				}
			
			}catch(e){alert(e.message);}
	}
    
    
    function loadPhone() {
		if ($("#phone").val() == "") {
			return;
		} else {
			$.getJSON(contextURL + "/cc011/loadPhone.action", {
				"strPhone" : $("#phone").val()
			}, function(data) {
				if (data.strMessage != "") {
					$.ligerDialog.error(data.strMessage);
					$("#phone").val("");
					$("#name").val("");
				} else {
					commoninfodivsecond.options.data = $
							.extend(true, {}, {
								Rows : data.lsYearcarddetals,
								Total : data.lsYearcarddetals.length
							});
					
					//commoninfodivsecond.loadData(true);
					
					//commoninfodivsecond.options.data=$.extend(true, {},{Rows: null,Total: 0});
					commoninfodivsecond.loadData(true);
					//commoninfodivsecond.columns[4].editor.data=loadCommonDataGridByStaffInfo(lsStaffinfo);
					
		    		//commoninfodivsecond.columns[10].editor.data=loadCommonDataGridByStaffInfo(lsStaffinfo);
					$("#name").val(data.yearcardinof.name);
				}
			});
		}
	}
    
  
     // ------------------------操作用户 ----------------End
     // ------------------------查询信息 ----------------Start
     function f_searchStaffInfo()
     {
    
     	   commoninfodivsecond.options.data = $.extend(true, {}, curProjectInfoDate);
     	
           commoninfodivsecond.loadData(f_getWhere());
     }
     function f_getWhere()
     {
     	try
    	{
            if (!commoninfodivsecond) return null;
            var clause = function (rowdata, rowindex)
            {
                var key = $("#txtSearchKey").val();
              	if(document.getElementById("projectState").value=="3")
               		return (key=="" || rowdata.bprjno.indexOf(key) > -1 ||  rowdata.prjname.indexOf(key) > -1 );
             	else
              		return ( (key==""  || rowdata.bprjno.indexOf(key) > -1 ||  rowdata.prjname.indexOf(key) > -1) 
								&& rowdata.useflag==document.getElementById("projectState").value  );
                
            };
            return clause; 
        }
    	catch(e){alert(e.message);}
     }
    
     function itemclick_projectInfo(item)
     {
     	try
    	{
	    	 $.ligerDialog.confirm('确认 '+curCompid+' 项目资料 '+item.text+' 操作', function (result)
		     {
		     		if( result==true)
            		{
			          	if(parent.hasFunctionRights( "BC006",  "UR_PRINT")!=true)
			        	{
			        		 $.ligerDialog.warn("该用户没有到导出权限,请确认!");
			        		 return;
			        	}
			        }
		     });  
    	}
    	catch(e){alert(e.message);}
     }
     
     function loadAutoStaff(curmanager,curWriteStaff)
	 {
	    	/*if(curmanager.options.valueFieldID == "factcfirstsalerid"
	    	|| curmanager.options.valueFieldID == "factcsecondsalerid"
	    	//|| curmanager.options.valueFieldID == "factcthirdsalerid"
	    	|| curmanager.options.valueFieldID == "factcfourthsalerid"
	    	//|| curmanager.options.valueFieldID == "factceighthsalerid"
	    	|| curmanager.options.valueFieldID == "factcninthsalerid"
	    	//|| curmanager.options.valueFieldID == "factctenthsalerid"
	    	)
	    	{
	    		curmanager.setData(loadOtherGridByStaffInfo(lsStaffinfo,curWriteStaff,1));
	    	}
	    	else if(curmanager.options.valueFieldID == "factcthirdsalerid"
	    	|| curmanager.options.valueFieldID == "csfirstsaler"
	    	|| curmanager.options.valueFieldID == "cssecondsaler"
	    	|| curmanager.options.valueFieldID == "csthirdsaler")
	    	{
	    		curmanager.setData(loadGridByStaffInfo(lsStaffinfo,curWriteStaff));
	    		curEmpManger=curmanager;
	    	}
	    	else
	    	{
	    		curmanager.setData(loadOtherGridByStaffInfo(lsStaffinfo,curWriteStaff,2));
	    	}*/
	    	curmanager.setData(loadGridByStaffInfo(lsStaffinfo,curWriteStaff));
	    	curEmpManger=curmanager;
	    	curEmpManger.selectBox.show();
	    }
     
     function f_onCheckRow(checked, data, rowid) {
    	 var sumAmt=0;
    	 var rows=commoninfodivsecond.getCheckedRows();
    	 $(rows).each(function()
    	 {
    		 sumAmt=sumAmt*1+this.syamt*1;
    	 });
    	 if(checked)
    	 {
    		 sumAmt=sumAmt*1+data.syamt*1;
    	 }
    	 else
    	 {
    		 sumAmt=sumAmt*1-data.syamt*1;
    	 }
    	 $("#cashamt").val("0");
    	 $("#cost").val("0");
    	 $("#storedamt").val(sumAmt);
    	 $("#sumAmt").val(sumAmt);
 	 }
     
     function f_allCheckRow(checked,data)
     {
    	 var sumAmt=0;
    	 for (var rowid in commoninfodivsecond.records)
         {
             if(checked)
             {
            	 sumAmt=sumAmt*1+commoninfodivsecond.records[rowid]['syamt']*1;
             }
             else
             {
            	 sumAmt=0;
             }
         }
    	 $("#cashamt").val("0");
    	 $("#cost").val("0");
    	 $("#storedamt").val(sumAmt);
    	 $("#sumAmt").val(sumAmt);
     }
     
     function cost(obj)
     {
    	 if(isNaN(obj.value))
    		 obj.value="0";
    	 var tatalamt=$("#sumAmt").val()*1-$("#cashamt").val()*1;
    	 if(obj.value*1>tatalamt*1)
    	 {
    		 $("#cost").val(tatalamt);
    		 $("#storedamt").val("0");
    	 }
    	 else
    	 {
    		 $("#storedamt").val($("#sumAmt").val()*1-$("#cashamt").val()*1-obj.value*1);
    	 }
     }
     
     function cash(obj)
     {
    	 if(isNaN(obj.value))
    		 obj.value="0";
    	 var tatalamt=$("#sumAmt").val()*1-$("#cost").val()*1;
    	 if(obj.value*1>tatalamt*1)
    	 {
    		 $("#cashamt").val(tatalamt);
    		 $("#storedamt").val("0");
    	 }
    	 else
    	 {
    		 $("#storedamt").val($("#sumAmt").val()*1-$("#cost").val()*1-obj.value*1);
    	 }
     }
     
     function post()
     {
    	 if($("#cardno").val()=="")
    	 {
    		 $.ligerDialog.warn("会员卡号不能为空!");
    		 return;
    	 }
    	 if($("#cardno").val()!=$("#cardnoagen").val())
    	 {
    		 $.ligerDialog.warn("2个卡号不相同!");
    		 return;
    	 }
    	 var curjosnparam=null;
    	 var curJSON="";
    	 var rows=commoninfodivsecond.getCheckedRows();
    	 $(rows).each(function()
    	 {
    		curjosnparam=JSON.stringify(this);
 		 	curjosnparam=curjosnparam.replace("%","");
 		 	curjosnparam=curjosnparam.replace("#","");
 		 	if(curJSON=="")
 		 		curJSON+=curjosnparam;
 		 	else
 		 		curJSON+=","+curjosnparam;
    	 });
    	 curJSON="["+curJSON+"]";
    	 var requestUrl ="cc019/post.action";
		 var responseMethod="postMessage";
		 var params="curjosnparam="+curJSON;
		     params+="&curMaster.cardno="+$("#cardno").val();
		     params+="&curMaster.phone="+$("#phone").val();
		     params+="&curMaster.name="+$("#name").val();
		     params+="&curMaster.cost="+$("#cost").val();
		     params+="&curMaster.cashpaycode="+$("#cashpaycode").val();
		     params+="&curMaster.cashamt="+$("#cashamt").val();
		     params+="&curMaster.storedamt="+$("#storedamt").val();
		     //params+="&curMaster.storedamt="+$("#storedamt").val();
		     params+="&strCurCompId="+curCompid;
		 var index=0;
		 var sumStoreAmt=0;
		 for (var rowid in yearcard.records)
	         {
			 	params+="&lsCardYears["+index+"].phone="+yearcard.records[rowid]['phone'];
			 	params+="&lsCardYears["+index+"].amt="+yearcard.records[rowid]['amt'];
			 	sumStoreAmt=sumStoreAmt*1+yearcard.records[rowid]['amt']*1;
	         }
		 if(sumStoreAmt>$("#storedamt").val()*1)
		 {
			 $.ligerDialog.warn("原卡的退款金额不能大于储值金额");
			 return;
		 }
		 sendRequestForParams_p(requestUrl,responseMethod,params); 	
     }
     
     function postMessage(request)
     {
    	 var responsetext = eval("(" + request.responseText + ")");
    	 if(checkNull(responsetext.strMessage)!="")
    	 {
    		 $.ligerDialog.warn(responsetext.strMessage);
    		 return;
    	 }
    	 window.location.reload();
     }
     
     function checkCard(obj)
     {
    	if(obj.checked)
    	{
    		var rows=commoninfodivsecond.getCheckedRows();
    		var params="?";
    		var index=0;
    		if(rows.length>0)
    		{
    			$(rows).each(function()
    			{
    				params+="lsPamars["+index+"].iteminid="+this.iteminid+"&";
    				index++;
    			});
    			var requestUrl ="cc019/loadYears.action";
    			var responseMethod="checkCardMessage";				
				sendRequestForParams_p(requestUrl,responseMethod,params); 	
    		}
    	}
     }
     
     function checkCardMessage(request)
     {
    	 var responsetext = eval("(" + request.responseText + ")");
    	 if(responsetext.lsYearcarddetals!=null && responsetext.lsYearcarddetals.length>0)
    	 {
    		 yearcard.options.data = $
				.extend(true, {}, {
					Rows : responsetext.lsYearcarddetals,
					Total : responsetext.lsYearcarddetals.length
				});
    		 yearcard.loadData(true);
    	 }
     }
     
     function validateCscardno() {
    	 if($("#cardno").val()=="")
    	 {
    		 return;
    	 }
    		var requestUrl = contextURL + "/cc011/validateCscardno.action";
    		var responseMethod = "validateCscardnoMessage";
    		var params = "strCardNo=" + $("#cardno").val();
    		params = params + "&cardUseType=1";
    		sendRequestForParams_p(requestUrl, responseMethod, params);
    }
     
     function validateCscardnoMessage(request) 
     {
    		var responsetext = eval("(" + request.responseText + ")");
    		var strmessage = checkNull(responsetext.strMessage);
    		if (strmessage != "") {
    			$.ligerDialog.error(strmessage);
    			$("#cardno").val("");
    		}
     }
     
