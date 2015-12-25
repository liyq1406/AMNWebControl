
   	var compTree = null;//主明细	
   	var compTreeManager = null;//主明细	
   	var curCompid="";
   	var cc011layout=null;
   	var commoninfodivdetial=null;
   	var commoninfodivdetial_Pro=null;
   	var commoninfodivdetial_Tm=null;
   	var commoninfodivdetial_Dy=null;
   	var commoninfodivdetial_cardInfo=null;
   	var commoninfodivdetial_payInfo =null;
   	var commoninfodivdetial_padInfo = null;
   	var commoninfodivthirth = null;
   	var commoninfodivfouth = null;
   	var pageState=3;
   	var chooseItemData = [{ choose: '1', text: '项目' }, { choose: '2', text: '产品'}];
    var chooseItemstateData = [{ choose: '0', text: '' },{ choose: '1', text: '达标' }, { choose: '2', text: '未达标'}];
   	var servicetypeChangeData=JSON.parse(parent.loadCommonControlDate_select("FWLB",0));
   	var paymodeChangeData=null;
   	var curRowIndex=0;
   	var lsStaffSelectData=loadCommonDataGridByStaffInfo();
   	var curRecord=null;
   	var curEmpManger=null;
   	var curitemManger=null;
   	var addRecordFlag=0;  //0 允许新增 1 不允许新增
   	var lsDnointernalcardinfo = null;
   	var paramtotiaomacardinfo='';
   	var showDialogmanager=null;
   	var shownewPayDialogmanager=null;
   	var fastTab=null;
    var projectdilog=null;
    var projectdilogText="";
    var staffdilog=null;
    var staffdilogText="";
    var rightmenu;
    var strSalePayMode="";
    var arrProList = new Array();
    var postState=0;
    var useManagerRate=0;
    var checkOtherPayFlag=0;  //储值分单标致
    var checkcustomerDialog=null;
    var vTCostAmt=0;
    var vTMrCostCount=0;
    var vTMfCostCount=0;
    var vparams="";
   	//初始化员工下拉表

   	function loadCommonDataGridByStaffInfo()
	{
			try
			{
				var strJson = "";//'{ "name": "cxh", "sex": "man" }';
				var ccount=0;
				if(parent.StaffInfo!=null && parent.StaffInfo.length>0)
				{	
					for(var i=0;i<parent.StaffInfo.length;i++)
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
							strJson=strJson+'{ "choose":"'+parent.StaffInfo[i].bstaffno+'", "text": "'+parent.StaffInfo[i].bstaffno+'_'+parent.StaffInfo[i].staffname+'"}';
						
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
	
    $(function ()
   	{
	   try
	   {
	   		  //布局
            cc011layout= $("#cc011layout").ligerLayout({ rightWidth: 280,  allowBottomResize: false, allowLeftResize: false,onEndResize:changeWidth });
             var lawidth = cc011layout.centerWidth;
     
             $("#readCurCardInfo").ligerButton(
	         {
	             text: '读取信息', width: 140,
		         click: function ()
		         {
		             editCurDetailInfo();
		         }
	         });
	       
	         $("#otherPay").ligerButton(
	         {
	             text: '分单支付', width: 120, height: 120,
		         click: function ()
		         {
		             //editCurDetailInfo();
		         }
	         });
            
           rightmenu = $.ligerMenu({ width: 120, items:
	            [
	            	{ text: '现金分单支付', click: needOtherPay, icon: 'add' },
	            	{ text: '原价分单支付', click: needOldOtherPay, icon: 'add' },
	            	{ text: '储值分单支付', click: needOtherCardPay, icon: 'add' }
	            ]
	            }); 
            
            commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
                columns: [
                { hide:true,		name: 'bcsinfotype'},
                { hide:true,		name: 'goodsbarno'},
                { display: '项目/产品' ,	name: 'csitemname', width:270,align: 'center',
                	editor: { type: 'select', data: null, url:'loadAutoProject',
                	autocomplete: true, 
                	valueField: 'choose',
                	onChanged : validateItem,selectBoxWidth:300,
                	selectBoxHeight:300}
                },
                { display: '支付方式', 	name: 'cspaymode', 			width:95,
	             	editor: { type: 'select', data: paymodeChangeData, valueField: 'choose',selectBoxWidth:105,onChanged : validatePaycode},
		            	render: function (item)
		              	{
		              		var lsZw=parent.gainCommonInfoByCode("ZFFS",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.cspaymode)
								{	
									return "<span style='color:blue'>"+lsZw[i].parentcodevalue+"</span>";								
							    }
							}
		                    return '';
		                } 
		        },
		        { display: '单价', 		name: 'csunitprice', 		width:50,align: 'right'},
		       
	            { display: '折扣', 		name: 'csdiscount', 		width:50,align: 'right' },
	            { display: '数量', 		name: 'csitemcount', 		width:50,align: 'right', editor: { type: 'int' ,onChanged : validateCostCount}  },
	            { display: '金额', 		name: 'csitemamt', 			width:60,align: 'right', editor: { type: 'float',onChanged : validateCostAmt }  },
	         
	            { display: '大工', 		name: 'csfirstsaler', 		width:100,
	            	editor: { type: 'select', data: lsStaffSelectData, url:'loadAutoStaff',autocomplete: true, valueField: 'choose',selectBoxWidth:150,onChanged : validateFristSaleType,alwayShowInDown:true},
	            	render: function (item)
	              	{	
	              		
	              		for(var i=0;i<parent.StaffInfo.length;i++)
						{	
								if(parent.StaffInfo[i].bstaffno==item.csfirstsaler)
								{
									return "<span style='color:blue'>"+parent.StaffInfo[i].staffname+"</span>";
								}
						}
	                    return '';
	                } 
	            },
	            { display: '类型', 		name: 'csfirsttype', 		width:60 ,
	             	editor: { type: 'select', data: servicetypeChangeData, valueField: 'choose',selectBoxHeight:'120'},
		            	render: function (item)
		              	{
		              		var lsZw=parent.gainCommonInfoByCode("FWLB",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.csfirsttype)
								{	
									return "<span style='color:blue'>"+lsZw[i].parentcodevalue+"</span>";								
							    }
							}
		                    return '';
		                } 
	            },
	            { display: '分享', 		name: 'csfirstshare', 	width:50,align: 'left', editor: { type: 'float', onChanged : validateFristSaleShare} },
	            { display: '中工', 		name: 'cssecondsaler', 		width:100,
	            	editor: { type: 'select', data: lsStaffSelectData,  url:'loadAutoStaff',autocomplete: true,valueField: 'choose',selectBoxWidth:150,onChanged : validateSecondSaleType,alwayShowInDown:true},
	            	render: function (item)
	              	{
	              		for(var i=0;i<parent.StaffInfo.length;i++)
						{	
								if(parent.StaffInfo[i].bstaffno==item.cssecondsaler)
								{
									return "<span style='color:blue'>"+parent.StaffInfo[i].staffname+"</span>";
								}
						}
	                    return '';
	                } 
	            },
	            { display: '类型', 		name: 'cssecondtype', 		width:60 ,
	             	editor: { type: 'select', data: servicetypeChangeData, valueField: 'choose',selectBoxHeight:'120'},
		            	render: function (item)
		              	{
		              		var lsZw=parent.gainCommonInfoByCode("FWLB",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.cssecondtype)
								{	
									return "<span style='color:blue'>"+lsZw[i].parentcodevalue+"</span>";								
							    }
							}
		                    return '';
		                } 
	            },
	            { display: '分享', 		name: 'cssecondshare', 	width:50,align: 'left', editor: { type: 'float', onChanged : validateSecondSaleShare } },
	            { display: '小工', 		name: 'csthirdsaler', 		width:100,
	            	editor: { type: 'select', data: lsStaffSelectData,url:'loadAutoStaff',autocomplete: true, valueField: 'choose',selectBoxWidth:150,onChanged : validateThirdSaleType,alwayShowInDown:true},
	            	render: function (item)
	              	{
	              		for(var i=0;i<parent.StaffInfo.length;i++)
						{	
								if(parent.StaffInfo[i].bstaffno==item.csthirdsaler)
								{
									return "<span style='color:blue'>"+parent.StaffInfo[i].staffname+"</span>";
								}
						}
	                    return '';
	                } 
	            },
	            { display: '类型', 		name: 'csthirdtype', 		width:60 ,
	             	editor: { type: 'select', data: servicetypeChangeData, valueField: 'choose',selectBoxHeight:'120'},
		            	render: function (item)
		              	{
		              		var lsZw=parent.gainCommonInfoByCode("FWLB",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.csthirdtype)
								{	
									return "<span style='color:blue'>"+lsZw[i].parentcodevalue+"</span>";								
							    }
							}
		                    return '';
		                } 
	            },
	           	{ display: '分享', 	name: 'csthirdshare', 		width:50,align: 'left', editor: { type: 'float' , onChanged : validateThirdSaleShare} },
	            { display: '序号', 	name: 'csproseqno', 	width:60, 	align: 'right'},
	            { display: '达标', 	name: 'csitemstate', 	width:60, 	align: 'right' , 
	                editor: { type: 'select', data: chooseItemstateData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.csitemstate == 0) return '';
	                    else if (item.csitemstate == 1) return "<span style='color:blue'>达标</span>";
	                    else if (item.csitemstate == 2) return "<span style='color:red'>未达标</span>";
	                    return '';
	                }
	            },
	            {	name: 'csdisprice', 	hide:true,	width:1},
	            {	name: 'shareflag', 	hide:true,	width:1},
	            {	name: 'costpricetype', 	hide:true,	width:1},
	            
	           	{ 	name: 'csitemno', 		hide:true,width:1}
	           	
                ],  pageSize:25, 
                data:{Rows: null,Total:0},      
                //width: lawidth-6,
                width:'100%',
                height:'460',
                enabledEdit: false,   clickToEdit: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    curRecord = data;
                    initFastGrid();
                    if(checkNull(data.cspaymode)=="9" || checkNull(data.cspaymode)=="11" || checkNull(data.cspaymode)=="13" || checkNull(data.cspaymode)=="16")
                    {
                    	document.getElementById("itempay").disabled=true;
                    }
                    else
                    {
                    	document.getElementById("itempay").disabled=false;
                    }
                    if(checkNull(data.csitemname)!="")
                    {
                    	document.getElementById("wCostItemNo").readOnly="readOnly";
		    			document.getElementById("wCostItemNo").style.background="#EDF1F8";
						document.getElementById("wCostItemBarNo").readOnly="readOnly";
		    			document.getElementById("wCostItemBarNo").style.background="#EDF1F8";
		    			document.getElementById("wgCostCount").readOnly="readOnly";
		    			document.getElementById("wgCostCount").style.background="#EDF1F8";
		    			document.getElementById("wCostFirstEmpNo").select();
		    			document.getElementById("wCostFirstEmpNo").focus();
                    }
                    else
                    {
                    	document.getElementById("wCostItemNo").readOnly="";
		    			document.getElementById("wCostItemNo").style.background="#FFFFFF";
						document.getElementById("wCostItemBarNo").readOnly="";
		    			document.getElementById("wCostItemBarNo").style.background="#FFFFFF";
		    			document.getElementById("wgCostCount").readOnly="";
		    			document.getElementById("wgCostCount").style.background="#FFFFFF";
                    	document.getElementById("wCostItemNo").focus();
                    	document.getElementById("wCostItemNo").select();
                    }
                    document.getElementById("wCostSecondEmpNo").readOnly="";
                    document.getElementById("wCostSecondEmpNo").style.background="#FFFFFF";
		     		document.getElementById("wCostthirthdEmpNo").readOnly="";
		     		document.getElementById("wCostthirthdEmpNo").style.background="#FFFFFF";
		     		if(checkNull(data.csitemno)!="" 
			 				&& document.getElementById("paramSp097").value=="1"
			 				&&(  checkNull(data.csitemno).indexOf("498")==0
			 				  || checkNull(data.csitemno).indexOf("490")==0))
		     		{
		     				document.getElementById("wCostSecondEmpNo").readOnly="readOnly";
			     			document.getElementById("wCostSecondEmpNo").style.background="#EDF1F8";
			     			document.getElementById("wCostthirthdEmpNo").readOnly="readOnly";
			     			document.getElementById("wCostthirthdEmpNo").style.background="#EDF1F8";
		     		}
		     		/*if(checkNull(data.csitemno)!="")
		     		{
			     		if(parent.lsProjectinfo!=null && parent.lsProjectinfo.length>0)
						{
								for(var i=0;i<parent.lsProjectinfo.length;i++)
								{
									if(parent.lsProjectinfo[i].id.prjno==data.csitemno )
									{
				     					if(document.getElementById("paramSp097").value=="1")
				     					{
				     						if(checkNull(parent.lsProjectinfo[i].prjtype)=="4")
				     						{
				     							document.getElementById("wCostSecondEmpNo").readOnly="readOnly";
				     							document.getElementById("wCostSecondEmpNo").style.background="#EDF1F8";
				     							document.getElementById("wCostthirthdEmpNo").readOnly="readOnly";
				     							document.getElementById("wCostthirthdEmpNo").style.background="#EDF1F8";
				     						}
				     					}
				     					break;
									}
								}
						}	
		     		}*/	
                    /*if(checkNull(data.csitemcount)*1<1)
                    {
                    	document.getElementById("wCostPCount").readOnly="readOnly";
		    			document.getElementById("wCostPCount").style.background="#EDF1F8";
                    }*/
                },onRClickToSelect:true,
                onContextmenu : function (parm,e)
                {
                	 istartBarCode=parm.data.frombarcode;
    				 iendBarCode=parm.data.tobarcode;
    				 iGoodsBarCode=parm.data.insergoodsno;
                     rightmenu.show({ top: e.pageY, left: e.pageX });
                     return false;
                }     
            });
  
            commoninfodivdetial_cardInfo=$("#commoninfodivdetial_cardInfo").ligerGrid({
                columns: [
                { 
                	display: '账户信息', 	name: 'strcardinfo',  	width:195,align: 'left' }
                ],  pageSize:25, 
                data:{Rows: null,Total:0},      
                width: '200',
                height:'240',
                enabledEdit: true, checkbox: false,
                rownumbers: false,usePager: false
            });
           commoninfodivdetial_payInfo=$("#commoninfodivdetial_payInfo").ligerGrid({
                columns: [
                { 
                	display: '支付信息', 	name: 'strpayinfo',  	width:195,align: 'left' }
                ],  pageSize:25, 
                data:{Rows: null,Total:0},      
                width: '200',
                height:'240',
                enabledEdit: true, checkbox: false,
                rownumbers: false,usePager: false,fixedCellHeight:false
            });
            
           commoninfodivdetial_padInfo=$("#commoninfodivdetial_padInfo").ligerGrid({
                columns: [
                { display: '手牌号', 	name: 'custom',  	width:70,align: 'left' },
                { display: '小单号', 	name: 'smallno',  	width:120,align: 'left' }
                ],  pageSize:25, 
                data:{Rows: null,Total:0},      
                width: '200',
                height:'240',
                enabledEdit: true, checkbox: false,
                rownumbers: false,usePager: false,fixedCellHeight:false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                	loadPadDetialInfo(data);
                }
            });
            
            commoninfodivdetial_Pro=$("#commoninfodivdetial_Pro").ligerGrid({
                columns: [
                { display: '疗程号', 	    name: 'bproseqno',  	width:80,align: 'center' ,
                	render: function (row) {  
     					var html ="<b>"+row.bproseqno+"</b>";  
     					return html;  
 					}  
                },
                { display: '疗程名称', 	name: 'bprojectname', 	width:200,align: 'left'},
                 { display: '剩余次数', 	name: 'lastcount', 		width:80,align: 'center',
	            	render: function (row) {  
     					var html ="";
     					if(checkNull(row.bprojectname)!="")
     						html ="0";
     					if(checkNull(row.lastcount)!="")
     					  html="<b>"+row.lastcount+"</b>";
     					return html;  
 					}  
 				},
	          	{ display: '备注', 		name: 'proremark', 		width:200,align: 'left'},
	          	{ name: 'bprojectno',hide:true, 		width:1},
	            { name: 'curcostcount', hide:true, 		width:1 },
	            { name: 'targetlastcount', hide:true, 		width:1 },
	            { 	name: 'lastamt', 	hide:true, 		width:1}
                ],  pageSize:25, 
                data:{Rows: null,Total:0},      
                width: '600',
                height:'240',
                enabledEdit: false, checkbox: false,
                rownumbers: false,usePager: false,
               // onCheckRow: f_onCheckRow,
                //onCheckAllRow: f_onCheckAllRow,
                onAfterEdit: comCostProAfterEdit
            });
        	$("#toptoolbarCard").ligerToolBar({ items: [
      		    { text: '<font color="blue">读卡(E)</font>', click: editCurDetailInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/myaccount.gif' }
            ]
            });
            
										
									
      		$("#toptoolbar").ligerToolBar({ items: [
      		    {text: '流水号:&nbsp;<label id="lbBillId"></label>'},
                { text: '日期:&nbsp;<label id="lbCostDate"></label>' },
                { text: '时间:&nbsp;<label id="lbCostTime"></label>' },
                { text: '卡号:&nbsp;<font color="red"><label id="lbCardNo">&nbsp;&nbsp;&nbsp;散客&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></font>'} ,
                { text: '<input type="radio" id="csersex0" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curMconsumeinfo.csersex" value="0"/>女<input type="radio" id="csersex1" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curMconsumeinfo.csersex" value="1"/>男'} ,
                { text: '<input type="radio" id="csertype0" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curMconsumeinfo.csertype" value="0"/>新客<input type="radio" id="csertype1" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curMconsumeinfo.csertype" value="1"/>老客'} 
                	 	
            ]
            });
            $("#toptoolbardetial").ligerToolBar({ items: [
      		     //{ text: '选定消费疗程', click: itemclick_proInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/right.gif' },
	             //{ line: true },
	             { text: '收购卡号:&nbsp;<input type="text"  name="shougoucardno" id="shougoucardno" maxlength="20" readonly="true" style="width:120;"  onchange="validateShouGou(this)"/>' },
	             { line: true },
	             { text: '团购卡号:&nbsp;<input type="text"  name="curMconsumeinfo.tuangoucardno" id="tuangoucardno" maxlength="20" readonly="true" style="width:100;"  onchange="validateTuangoucardno(this)"/>' },
	             { line: true },
	             { text: '条码卡号:&nbsp;<input type="text"  name="curMconsumeinfo.tiaomacardno" id="tiaomacardno" maxlength="20" readonly="true" style="width:100;"  onchange="validateTiaomacardno(this)"/>' },
	             { line: true },
	             { text: '抵用券:&nbsp;<input type="text"  name="curMconsumeinfo.diyongcardno" id="diyongcardno" maxlength="20" readonly="true" style="width:100;" onchange="validateDiyongcardno(this)" />' },
	             { text: '额度:&nbsp;<label id="lbdyAmt"></label>' },
	             { line: true },
	             { text: '介绍人:&nbsp;<input type="text"  name="curMconsumeinfo.recommendempid" id="recommendempid" maxlength="20" readonly="true" style="width:80;background:#EDF1F8;" onchange="validateRecommendempid(this)" /><input type="text"  name="curMconsumeinfo.recommendempname" id="recommendempname" maxlength="20" readonly="true" style="width:80;background:#EDF1F8;" /> <input type="hidden"  name="curMconsumeinfo.recommendempinid" id="recommendempinid" />' },
	             { text: '经理打折&nbsp;<label id="lbdyAmt"></label>', click: managerRate, img: contextURL+'/common/ligerui/ligerUI/skins/icons/lock.gif'  },
	             { text: '3.8节活动&nbsp;<label id="lbdyAmt"></label>', click: manager38Rate, img: contextURL+'/common/ligerui/ligerUI/skins/icons/memeber.gif'  }
	               	 	
            ]
            });
            
        
            
           $("#toptoolbarserch").ligerToolBar({ items: [
           		{ text:' <input type="text" name="strSearchBillId" style="width:120" id="strSearchBillId"/>'},
                { click: searchCurRecord, img: contextURL+'/common/ligerui/ligerUI/skins/icons/search.gif' },	 	
                { click: editCurRecord,img: contextURL+'/common/ligerui/ligerUI/skins/icons/save-disabled.gif' },	 	
                { click: viewTicketReport,img: contextURL+'/common/ligerui/ligerUI/skins/icons/print.gif' },
                { click: loadpadBill,img: contextURL+'/common/ligerui/ligerUI/skins/icons/logout.gif' }
            ]
            });
            $("#autobardetial").ligerToolBar({ items: [	
            	{text: '<font color="blue">体验:<blue>'},
           		{ text:'<label id="lbfastItemt1">儿童</label>(<label id="lbfastItem1"></label>)',click: validateFastItemCheckItem1  },
                { text:'<label id="lbfastItemt2">设计师</label>(<label id="lbfastItem2"></label>)',click: validateFastItemCheckItem2  },
               	{ text:'<label id="lbfastItemt3">首席</label>(<label id="lbfastItem3"></label>)',click: validateFastItemCheckItem3 },	 	
                { text:'<label id="lbfastItemt4">总监</label>(<label id="lbfastItem4"></label>)',click: validateFastItemCheckItem4  },	 	
                { text:'<label id="lbfastItemt5">创意</label>(<label id="lbfastItem5"></label>)',click: validateFastItemCheckItem5 },	 	
                { text:'<label id="lbfastItemt6">店长</label>(<label id="lbfastItem6"></label>)',click: validateFastItemCheckItem6  }
              	
            ]
            });
            $("#autobardetialyj").ligerToolBar({ items: [
           	 	{text: '<font color="blue">洗剪吹:<blue>'},
           		{ text:'<label id="lbfastItemt1">儿童</label>(<label id="lbfastItemy1"></label>)',click: validateFastItemCheckItemy1  },
                { text:'<label id="lbfastItemt2">设计师</label>(<label id="lbfastItemy2"></label>)',click: validateFastItemCheckItemy2  },
               	{ text:'<label id="lbfastItemt3">首席</label>(<label id="lbfastItemy3"></label>)',click: validateFastItemCheckItemy3 },	 	
                { text:'<label id="lbfastItemt4">总监</label>(<label id="lbfastItemy4"></label>)',click: validateFastItemCheckItemy4  },	 	
                { text:'<label id="lbfastItemt5">创意</label>(<label id="lbfastItemy5"></label>)',click: validateFastItemCheckItemy5 },	 	
                { text:'<label id="lbfastItemt6">店长</label>(<label id="lbfastItemy6"></label>)',click: validateFastItemCheckItemy6  }
            ]
            });
            
             $("#autobardetialyjxc").ligerToolBar({ items: [
           	 	{text: '<font color="blue">洗吹:&nbsp;&nbsp;&nbsp<blue>'},
           		{ text:'<label id="lbfastItemt2">设计师</label>(<label id="lbfastItemy2xc"></label>)',click: validateFastItemCheckItemy2_XC  },
               	{ text:'<label id="lbfastItemt3">首席</label>(<label id="lbfastItemy3xc"></label>)',click: validateFastItemCheckItemy3_XC },	 	
                { text:'<label id="lbfastItemt4">总监</label>(<label id="lbfastItemy4xc"></label>)',click: validateFastItemCheckItemy4_XC  },	 	
                { text:'<label id="lbfastItemt5">创意</label>(<label id="lbfastItemy5xc"></label>)',click: validateFastItemCheckItemy5_XC },
				{ text:'<label id="lbfastItemt6">特价</label>(<label id="lbfastItemy6xc"></label>)',click: validateFastItemCheckItemy6_XC },
				{ text:'<label id="lbfastItemt6">特价</label>(<label id="lbfastItemy7xc"></label>)',click: validateFastItemCheckItemy7_XC }
            ]
            });
            
            $("#autoxcCost1").ligerToolBar({ items: [
           	 	{ text:'设计师洗吹[<label id="lbSpecialItemyxc1"></label>]',click: validateSpecialItemCheckItem_XC1}
               ]
            });
            $("#autoxcCost2").ligerToolBar({ items: [
           	 	{ text:'首席洗吹[<label id="lbSpecialItemyxc2"></label>]',click: validateSpecialItemCheckItem_XC2}
               ]
            });
            $("#autoxcCost3").ligerToolBar({ items: [
           	 	{ text:'总监洗吹[<label id="lbSpecialItemyxc3"></label>]',click: validateSpecialItemCheckItem_XC3}
               ]
            });
            $("#autoxcCost4").ligerToolBar({ items: [
           	 	{ text:'创意总监洗吹[<label id="lbSpecialItemyxc4"></label>]',click: validateSpecialItemCheckItem_XC4}
               ]
            });
            $("#autoxcCost5").ligerToolBar({ items: [
           	 	{ text:'设计师洗剪吹[<label id="lbSpecialItemyxc5"></label>]',click: validateSpecialItemCheckItem_XC5}
               ]
            });
            $("#autoxcCost6").ligerToolBar({ items: [
           	 	{ text:'首席洗剪吹[<label id="lbSpecialItemyxc6"></label>]',click: validateSpecialItemCheckItem_XC6}
               ]
            });
            $("#autoxcCost7").ligerToolBar({ items: [
           	 	{ text:'总监洗剪吹[<label id="lbSpecialItemyxc7"></label>]',click: validateSpecialItemCheckItem_XC7}
               ]
            });
            $("#autoxcCost8").ligerToolBar({ items: [
           	 	{ text:'创意总监洗剪吹(<label id="lbSpecialItemyxc8"></label>)',click: validateSpecialItemCheckItem_XC8}
               ]
            });
            $("#autoxcCost9").ligerToolBar({ items: [
           	 	{ text:'店长洗剪吹(<label id="lbSpecialItemyxc9"></label>)',click: validateSpecialItemCheckItem_XC9}
               ]
            });
           //---------------------------------------------右侧面板营业分析 start
           /*	$("#fastTab").ligerTab();
            	fastTab = $("#fastTab").ligerGetTabManager();
            
				$("#dataAnalysis").ligerTab({ onBeforeSelectTabItem: function (tabid)
	            {
	                dataAnalysis_before( tabid);
	            }, onAfterSelectTabItem: function (tabid)
	            {
	                dataAnalysis_after( tabid);
	            } 
	            });
	      
				$(".datepicker").datePicker({
					inline:true,
					selectMultiple:false	
					
				}); */
				document.getElementById("lbfastItem1").innerHTML=eval("(parent.dSp074Price)");
				document.getElementById("lbfastItem2").innerHTML=eval("(parent.dSp075Price)");
				document.getElementById("lbfastItem3").innerHTML=eval("(parent.dSp076Price)");
				document.getElementById("lbfastItem4").innerHTML=eval("(parent.dSp077Price)");
				document.getElementById("lbfastItem5").innerHTML=eval("(parent.dSp078Price)");
				document.getElementById("lbfastItem6").innerHTML=eval("(parent.dSp079Price)");
				
				document.getElementById("lbfastItemy1").innerHTML=eval("(parent.dSp081Price)");
				document.getElementById("lbfastItemy2").innerHTML=eval("(parent.dSp082Price)");
				document.getElementById("lbfastItemy3").innerHTML=eval("(parent.dSp083Price)");
				document.getElementById("lbfastItemy4").innerHTML=eval("(parent.dSp084Price)");
				document.getElementById("lbfastItemy5").innerHTML=eval("(parent.dSp085Price)");
				document.getElementById("lbfastItemy6").innerHTML=eval("(parent.dSp086Price)");
				
				document.getElementById("lbfastItemy2xc").innerHTML=eval("(parent.dSp087Price)");
				document.getElementById("lbfastItemy3xc").innerHTML=eval("(parent.dSp088Price)");
				document.getElementById("lbfastItemy4xc").innerHTML=eval("(parent.dSp089Price)");
				document.getElementById("lbfastItemy5xc").innerHTML=eval("(parent.dSp090Price)");
				document.getElementById("lbfastItemy6xc").innerHTML=eval("(parent.dSp091Price)");
				document.getElementById("lbfastItemy7xc").innerHTML=eval("(parent.dSp092Price)");
				
          	$("#pageloading").hide(); 
          
         	addConsumeInfo();
         	addProRecord_cardInfo();
         	addProRecord();
         	addProRecord_payInfo();
         	addProRecord_padInfo();
   		}catch(e){alert(e.message);}
    });
    
    function changeWidth()
    {
    
    }
    
              
    function loadAutoStaff(curmanager,curWriteStaff)
	{	
		curmanager.setData(loadGridChooseByStaffInfo(parent.StaffInfo,curWriteStaff));
		curmanager.selectBox.show();
		curEmpManger=curmanager;
	 }
    
    function loadAutoProject(curmanager,curWriteitemname)
	{	
		if(curRecord.bcsinfotype==1)
			curmanager.setData(parent.loadProjectControlDate_select(curWriteitemname));
		else
			curmanager.setData(parent.loadGoodsControlDate_select(curWriteitemname));
		curmanager.selectBox.show();
		curitemManger=curmanager;
	}
    
    
   function loadPayModeDate()
   {
   		var strpaymode= new Array();
   		if(strSalePayMode!="")
   		{
   			clearOption("itempay");
   			var returnValue='';
   			var paymode="";
   			var paymodename="";
   			strpaymode=strSalePayMode.split(";");
   			for(var i=0;i<strpaymode.length;i++)
   			{
   				paymode=strpaymode[i];
   				paymodename=parent.loadCommonControlValue("ZFFS",0,paymode);
   				if(returnValue!='')
				{
					returnValue=returnValue+',';
				}
				else
				{
					returnValue=returnValue+'[';
				}
				returnValue=returnValue+'{"choose": "'+paymode+'","text": "'+paymode+'_'+paymodename+'"}';
	   			addOption(paymode,paymode+"."+paymodename,document.getElementById("itempay"));
	   		}
	   		if(returnValue!='')
	   		{
				returnValue=returnValue+']';
				commoninfodivdetial.columns[2].editor.data=JSON.parse(returnValue);
			}
   		}
		clearOption("wCostFirstEmptype");
		clearOption("wCostSecondEmptype");
		clearOption("wCostthirthEmptype");
		var items = parent.gainCommonInfoByCode("FWLB",0);
		for(var i=0;i<items.length;i++)
		{
			addOption(items[i].bparentcodekey,items[i].bparentcodekey+"."+items[i].parentcodevalue,document.getElementById("wCostFirstEmptype"));
	  		document.getElementById("wCostFirstEmptype").value=2;
	  		addOption(items[i].bparentcodekey,items[i].bparentcodekey+"."+items[i].parentcodevalue,document.getElementById("wCostSecondEmptype"));
	  		document.getElementById("wCostSecondEmptype").value=2;
	  		addOption(items[i].bparentcodekey,items[i].bparentcodekey+"."+items[i].parentcodevalue,document.getElementById("wCostthirthEmptype"));
	  		document.getElementById("wCostthirthEmptype").value=2;
	  	}
		
   }
   
    
     //默认新增消费单
    function addConsumeInfo()
    {
    	document.getElementById("specialCost").style.display="none";
    	document.getElementById("sumKeepBalance").value=0;
    	pageState=1;
    	useManagerRate=0;
    	checkOtherPayFlag=0;
     	var requestUrl ="cc011/add.action"; 
		var responseMethod="addMessage";				
		sendRequestForParams_p(requestUrl,responseMethod,"" );
    }
   
   function addMessage(request)
   {
       		try
        	{
        	var responsetext = eval("(" + request.responseText + ")");
    		if(responsetext.lsDconsumeinfos!=null && responsetext.lsDconsumeinfos.length>0)
	   		{
	   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: responsetext.lsDconsumeinfos,Total: responsetext.lsDconsumeinfos.length});
            	commoninfodivdetial.loadData(true);  
            	commoninfodivdetial.select(0);          	
	   		}
	   		else
	   		{
	   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivdetial.loadData(true);  
	   		}
	   		initDetialGrid();
	   		commoninfodivdetial_Pro.options.data=$.extend(true, {},{Rows: null,Total:0});
            commoninfodivdetial_Pro.loadData(true);  
            addProRecord();
	        loadCurMaster(responsetext.curMconsumeinfo);
	        //document.getElementById("dProjectAmt").innerHTML=0;
	        //document.getElementById("dGoodsAmt").innerHTML=0;
	       // document.getElementById("dTotalsAmt").innerHTML=0;
	       // document.getElementById("paycode1_img").src=contextURL+"/common/funtionimage/paycode1.jpg"
	       // document.getElementById("paycode2_div").style.display="none";
			//document.getElementById("paycode3_div").style.display="none";
			//document.getElementById("paycode4_div").style.display="none";
			//document.getElementById("paycode5_div").style.display="none";
			//document.getElementById("paycode6_div").style.display="none";
			//document.getElementById("payamt1_lb").innerHTML=0;
			document.getElementById("strPayMode1").value="";
			document.getElementById("dPayAmt1").value=0;
			document.getElementById("strPayMode2").value="";
			document.getElementById("dPayAmt2").value=0;
			document.getElementById("strPayMode3").value="";
			document.getElementById("dPayAmt3").value=0;
			document.getElementById("strPayMode4").value="";
			document.getElementById("dPayAmt4").value=0;
			document.getElementById("strPayMode5").value="";
			document.getElementById("dPayAmt5").value=0;
			document.getElementById("strPayMode6").value="";
			document.getElementById("dPayAmt6").value=0;
			document.getElementById("dXjcDiscount").value=1;
			document.getElementById("curCostAmt").value=0;
			document.getElementById("curMfCostCount").value=0;
			document.getElementById("curMrCostCount").value=0;
			document.getElementById("billflag").value="0";
			handPayList();
			strSalePayMode=checkNull(responsetext.strSalePayMode);
			loadPayModeDate(responsetext.strSalePayMode);
			addRecordFlag=0;
			document.getElementById("paramSp097").value=responsetext.paramSp097;
			document.getElementById("paramSp098").value=responsetext.paramSp098;
			document.getElementById("paramSp104").value=responsetext.paramSp104;
			document.getElementById("paramSp105").value=responsetext.paramSp105;
			if(checkNull(responsetext.paramSp105)*1==1)
			{
				document.getElementById("recommendempid").readOnly="";
		    	document.getElementById("recommendempid").style.background="#FFFFFF";
			}
			else
			{
				document.getElementById("recommendempid").readOnly="readOnly";
		    	document.getElementById("recommendempid").style.background="#EDF1F8";
			}
	   		}catch(e){alert(e.message);}
    }
    
    function loadCurMaster(curMaster)
    {
    
    	var rowslength=commoninfodivdetial_cardInfo.rows.length;
    	for (var rowid =0;rowid<rowslength*1;rowid++)
		{
			commoninfodivdetial_cardInfo.deleteRow(0); 
		}
		addProRecord_cardInfo("会员姓名:"+checkNull(curMaster.csname));
    	addProRecord_cardInfo("会员类型:"+checkNull(curMaster.cscardtypeName));
    			
    	document.getElementById("lbBillId").innerHTML=checkNull(curMaster.id.csbillid);
    	document.getElementById("lbCostDate").innerHTML=checkNull(curMaster.csdate);
    	document.getElementById("lbCostTime").innerHTML=checkNull(curMaster.csstarttime);
    	document.getElementById("lbCardNo").innerHTML=checkNull(curMaster.cscardno);
    	document.getElementById("lbCostTime").innerHTML=checkNull(curMaster.csstarttime);
    	document.getElementById("lbdyAmt").innerHTML=0;
    	document.getElementById("cscompid").value=checkNull(curMaster.id.cscompid);
    	document.getElementById("csbillid").value=checkNull(curMaster.id.csbillid);
    	document.getElementById("cscardno").value=checkNull(curMaster.cscardno);
    	document.getElementById("cscardtype").value=checkNull(curMaster.cscardtype);
    	document.getElementById("cscardtypeName").value=checkNull(curMaster.cscardtypeName);
    	document.getElementById("csname").value=checkNull(curMaster.csname);
    	document.getElementById("cscurkeepamt").value=checkNull(curMaster.cscurkeepamt);
    	document.getElementById("csdate").value=checkNull(curMaster.csdate);
    	document.getElementById("csstarttime").value=checkNull(curMaster.csstarttime); 
    	document.getElementById("cscurdepamt").value=checkNull(curMaster.cscurdepamt);
    	document.getElementById("csmanualno").value=checkNull(curMaster.csmanualno);
    	document.getElementById("tuangoucardno").value=checkNull(curMaster.tuangoucardno);
    	document.getElementById("tiaomacardno").value=checkNull(curMaster.tiaomacardno);
    	document.getElementById("diyongcardno").value=checkNull(curMaster.diyongcardno);
    	
    	document.getElementById("recommendempid").value=checkNull(curMaster.recommendempid);
    	document.getElementById("recommendempinid").value=checkNull(curMaster.recommendempinid);
    	document.getElementById("recommendempname").value=checkNull(curMaster.recommendempname);
    	document.getElementById("shougoucardno").value="";
    	document.getElementById("cardpointamt").value=0;
    	document.getElementById("cardHomeSource").value="";
    	document.getElementById("diyongcardnoamt").value="0";
    	handleRadio("curMconsumeinfo.csersex",checkNull(curMaster.csersex));
    	handleRadio("curMconsumeinfo.csertype",checkNull(curMaster.csertype));
    	if(pageState==1)
		{
			document.getElementById("wCostItemNo").readOnly="";
		    document.getElementById("wCostItemNo").style.background="#FFFFFF";
			document.getElementById("wCostItemBarNo").readOnly="";
		    document.getElementById("wCostItemBarNo").style.background="#FFFFFF";
			pageWriteState();
		}
		else
		{
			
		    document.getElementById("wCostItemNo").readOnly="readOnly";
		    document.getElementById("wCostItemNo").style.background="#EDF1F8";
			document.getElementById("wCostItemBarNo").readOnly="readOnly";
		    document.getElementById("wCostItemBarNo").style.background="#EDF1F8";
			pageReadState();
		}
    }
    
    function pageWriteState()
    {
	    try
	    {
	    	//document.getElementById("cscardno").readOnly="";
	    	document.getElementById("csmanualno").readOnly="";
			document.getElementById("tuangoucardno").readOnly="";
			document.getElementById("tiaomacardno").readOnly="";
			document.getElementById("diyongcardno").readOnly="";
			document.getElementById("shougoucardno").readOnly="";
	    	commoninfodivdetial.options.clickToEdit=true;
	    	commoninfodivdetial_Pro.options.clickToEdit=true;
    	}catch(e){alert(e.message);}
	}
    
    function pageReadState()
    {
    	try
	    {
	    	//document.getElementById("cscardno").readOnly="readOnly";
	    	document.getElementById("csmanualno").readOnly="readOnly";
	    	document.getElementById("tuangoucardno").readOnly="readOnly";
			document.getElementById("tiaomacardno").readOnly="readOnly";
			document.getElementById("diyongcardno").readOnly="readOnly";
			document.getElementById("shougoucardno").readOnly="readOnly";
			commoninfodivdetial.options.clickToEdit=false;
			commoninfodivdetial_Pro.options.clickToEdit=false;
    	}catch(e){alert(e.message);}
    }
    function editCurDetailInfo()
    {
    	document.getElementById("sumKeepBalance").value=0;
    	var CardControl=parent.document.getElementById("CardCtrl");
		CardControl.Init(parent.commtype,parent.prot,parent.password1,parent.password2,parent.password3);
		var cardNo=CardControl.ReadCard();
		if(cardNo!="")
		{
			document.getElementById("billflag").value="1";
			document.getElementById("billflag").value=1;
			document.getElementById("cscardno").value=cardNo;
			document.getElementById("lbCardNo").innerHTML=cardNo;
    		validateCscardno(document.getElementById("cscardno"));
    	}
  
    }
    //-----------------------------------验证卡号
    function validateCscardno(obj)
    {
    	document.getElementById("specialCost").style.display="none";
    	commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
        commoninfodivdetial.loadData(true);  
        addRecord();
    	var rowslength=commoninfodivdetial_cardInfo.rows.length;
    	for (var rowid =0;rowid<rowslength*1;rowid++)
		{
			commoninfodivdetial_cardInfo.deleteRow(0); 
		}
    	if(obj.value=="")
    	{
    		document.getElementById("cscardtype").value="";
    		document.getElementById("cscardtypeName").value="";
    		document.getElementById("csname").value="";
    		document.getElementById("cscurkeepamt").value=0;
    		document.getElementById("cscurdepamt").value=0;
    		document.getElementById("cardsource").value="";
    		document.getElementById("cardHomeSource").value="";
    		document.getElementById("dXjcDiscount").value=1;
    		document.getElementById("curCostAmt").value=0;
    		document.getElementById("curMfCostCount").value=0;
			document.getElementById("curMrCostCount").value=0;
    	}
    	else if(obj.value=="散客")
    	{
    		document.getElementById("cscardtype").value="";
    		document.getElementById("cscardtypeName").value="无";
    		document.getElementById("csname").value="散客";
    		document.getElementById("cscurkeepamt").value=0;
    		document.getElementById("cscurdepamt").value=0;
    		document.getElementById("cardsource").value="";
    		document.getElementById("cardHomeSource").value="";  
    		document.getElementById("dXjcDiscount").value=1;
    		document.getElementById("curCostAmt").value=0;
    		document.getElementById("curMfCostCount").value=0;
			document.getElementById("curMrCostCount").value=0;
    		handleRadio("curMconsumeinfo.csertype",0); 
    		//initDetialGrid();
	   		commoninfodivdetial_Pro.options.data=$.extend(true, {},{Rows: null,Total:0});
            commoninfodivdetial_Pro.loadData(true); 
            arrProList = new Array();
    	}
    	else
    	{
    		var requestUrl ="cc011/validateCscardno.action"; 
			var responseMethod="validateCscardnoMessage";	
			var params="strCardNo="+obj.value;
			params=params+"&cardUseType=1";					
			sendRequestForParams_p(requestUrl,responseMethod,params );
    	}
    }
    
    function validateCscardnoMessage(request)
   	{
       	try
        {
        	var responsetext = eval("(" + request.responseText + ")");
    		var strmessage=	checkNull(responsetext.strMessage);
	   		if(strmessage!="")
	   		{
	   			$.ligerDialog.warn(strmessage);
	   			document.getElementById("cscardno").value="";
	   			document.getElementById("cscardtype").value="";
    			document.getElementById("cscardtypeName").value="";
    			document.getElementById("csname").value="";
    			document.getElementById("cscurkeepamt").value=0;
    			document.getElementById("cscurdepamt").value=0;
    			document.getElementById("cardsource").value="";
    			document.getElementById("cardHomeSource").value="";
    			document.getElementById("dXjcDiscount").value=1;
    			document.getElementById("curCostAmt").value=0;
    			document.getElementById("curMfCostCount").value=0;
				document.getElementById("curMrCostCount").value=0;
	   		}
	   		else
	   		{
	   			document.getElementById("cscardtype").value=checkNull(responsetext.curCardinfo.cardtype);
    			document.getElementById("cscardtypeName").value=checkNull(responsetext.curCardinfo.cardtypeName);
    			addProRecord_cardInfo("会员姓名:"+checkNull(responsetext.curCardinfo.membername));
    			addProRecord_cardInfo("会员类型:"+checkNull(responsetext.curCardinfo.cardtypeName));
    			document.getElementById("csname").value=checkNull(responsetext.curCardinfo.membername);
    			if(checkNull(responsetext.curCardinfo.cardsource)==1)
    			{
    				document.getElementById("cardsource").value="收购卡";
    				document.getElementById("cscurkeepamt").value=checkNull(responsetext.curCardinfo.account5Amt);
    				document.getElementById("cscurdepamt").value=checkNull(responsetext.curCardinfo.account5debtAmt);
    				addProRecord_cardInfo("收购账户:"+checkNull(responsetext.curCardinfo.account5Amt));
    				document.getElementById("sumKeepBalance").value=checkNull(responsetext.curCardinfo.account5Amt)*1;
    			}
    			else
    			{
    				document.getElementById("cardsource").value="内部卡";
    				document.getElementById("cscurkeepamt").value=checkNull(responsetext.curCardinfo.account2Amt);
    				document.getElementById("cscurdepamt").value=checkNull(responsetext.curCardinfo.account2debtAmt);
    				addProRecord_cardInfo("储值账户:"+checkNull(responsetext.curCardinfo.account2Amt));
    				document.getElementById("sumKeepBalance").value=checkNull(responsetext.curCardinfo.account2Amt)*1;
    			}
    			document.getElementById("sumKeepBalance").value=document.getElementById("sumKeepBalance").value*1+checkNull(responsetext.curCardinfo.account4Amt)*1+checkNull(responsetext.curCardinfo.account6Amt)*1;
    			document.getElementById("cardsendamt").value=checkNull(responsetext.curCardinfo.account6Amt);
    			addProRecord_cardInfo("赠送账户:"+checkNull(responsetext.curCardinfo.account6Amt));
    			document.getElementById("cardpointamt").value=checkNull(responsetext.curCardinfo.account3Amt);
    			addProRecord_cardInfo("积分账户:"+checkNull(responsetext.curCardinfo.account3Amt));
    			document.getElementById("cardHomeSource").value=checkNull(responsetext.curCardinfo.id.cardvesting);
    			handleRadio("curMconsumeinfo.csersex",checkNull(responsetext.curCardinfo.membersex));
  
    			handleRadio("curMconsumeinfo.csertype",1);
    			if(responsetext.lsCardproaccount!=null && responsetext.lsCardproaccount.length>0)
		   		{
		   			commoninfodivdetial_Pro.options.data=$.extend(true, {},{Rows: responsetext.lsCardproaccount,Total: responsetext.lsCardproaccount.length});
	            	commoninfodivdetial_Pro.loadData(true);  
	            	arrProList = new Array();
	            	for(var i=0;i<responsetext.lsCardproaccount.length;i++)
				 	{ 
				 	   arrProList[i]="*"+responsetext.lsCardproaccount[i].bproseqno+"-"+responsetext.lsCardproaccount[i].bprojectname+"("+responsetext.lsCardproaccount[i].lastcount+"次)";
				 	   
				 	}          	
		   		}
		   		else
		   		{
		   			commoninfodivdetial_Pro.options.data=$.extend(true, {},{Rows: null,Total:0});
	            	commoninfodivdetial_Pro.loadData(true);  
	            	addProRecord();
	            	arrProList = new Array();
		   		}
		   		document.getElementById("dXjcDiscount").value=ForDight(responsetext.DCostProjectRate,2);
		   		document.getElementById("curCostAmt").value=ForDight(responsetext.curCostAmt,1);
				document.getElementById("curMfCostCount").value=ForDight(responsetext.curMfCostCount,1);
				document.getElementById("curMrCostCount").value=ForDight(responsetext.curMrCostCount,1);
	   			if(responsetext.curCardspecialcost!=null)
	   			{
	   				document.getElementById("specialCost").style.display="block";
	   				document.getElementById("lbSpecialItemyxc1").innerHTML=ForDight(checkNull(responsetext.curCardspecialcost.costxc1),2);
	   				document.getElementById("lbSpecialItemyxc2").innerHTML=ForDight(checkNull(responsetext.curCardspecialcost.costxc2),2);
	   				document.getElementById("lbSpecialItemyxc3").innerHTML=ForDight(checkNull(responsetext.curCardspecialcost.costxc3),2);
	   				document.getElementById("lbSpecialItemyxc4").innerHTML=ForDight(checkNull(responsetext.curCardspecialcost.costxc4),2);
	   				document.getElementById("lbSpecialItemyxc5").innerHTML=ForDight(checkNull(responsetext.curCardspecialcost.costxc5),2);
	   				document.getElementById("lbSpecialItemyxc6").innerHTML=ForDight(checkNull(responsetext.curCardspecialcost.costxc6),2);
	   				document.getElementById("lbSpecialItemyxc7").innerHTML=ForDight(checkNull(responsetext.curCardspecialcost.costxc7),2);
	   				document.getElementById("lbSpecialItemyxc8").innerHTML=ForDight(checkNull(responsetext.curCardspecialcost.costxc8),2);
	   				document.getElementById("lbSpecialItemyxc9").innerHTML=ForDight(checkNull(responsetext.curCardspecialcost.costxc9),2);
	   			}
	   			else
	   			{
	   				document.getElementById("specialCost").style.display="none";
	   			}
	   		}
	   	
	   	}catch(e){alert(e.message);}
    }
    
    
    function validateShouGou(obj)
    {
    	document.getElementById("specialCost").style.display="none";
    	if(obj.value=="")
    		return ;
    	commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
        commoninfodivdetial.loadData(true);  
        addRecord();
        commoninfodivdetial_Pro.options.data=$.extend(true, {},{Rows: null,Total:0});
        commoninfodivdetial_Pro.loadData(true); 
        addProRecord(); 
        var rowslength=commoninfodivdetial_cardInfo.rows.length;
    	for (var rowid =0;rowid<rowslength*1;rowid++)
		{
			commoninfodivdetial_cardInfo.deleteRow(0); 
		}
		document.getElementById("billflag").value=2;
    	var requestUrl ="cc011/validateCscardno.action"; 
		var responseMethod="validateCsSGcardnoMessage";	
		var params="strCardNo="+obj.value;	
		params=params+"&cardUseType="+document.getElementById("billflag").value;		
		sendRequestForParams_p(requestUrl,responseMethod,params );	
    }
    
    function validateCsSGcardnoMessage(request)
   	{
       	try
        {
        	var responsetext = eval("(" + request.responseText + ")");
    		var strmessage=	checkNull(responsetext.strMessage);
	   		if(strmessage!="")
	   		{
	   			$.ligerDialog.warn(strmessage);
	   			document.getElementById("cscardno").value="";
	   			document.getElementById("cscardtype").value="";
    			document.getElementById("cscardtypeName").value="";
    			document.getElementById("csname").value="";
    			document.getElementById("cscurkeepamt").value=0;
    			document.getElementById("cscurdepamt").value=0;
    			document.getElementById("cardsource").value="";
    			document.getElementById("cardHomeSource").value="";
    			document.getElementById("dXjcDiscount").value=1;
    			document.getElementById("shougoucardno").readOnly="";
    			document.getElementById("sumKeepBalance").value=0;
    			document.getElementById("curCostAmt").value=0;
    			document.getElementById("curMfCostCount").value=0;
				document.getElementById("curMrCostCount").value=0;
	   		}
	   		else
	   		{
	   			document.getElementById("cscardno").value=checkNull(responsetext.curCardinfo.id.cardno).replace(/(\s*$)/g, "");
	   			document.getElementById("lbCardNo").innerHTML=checkNull(responsetext.curCardinfo.id.cardno);
	   			document.getElementById("cscardtype").value=checkNull(responsetext.curCardinfo.cardtype);
    			document.getElementById("cscardtypeName").value=checkNull(responsetext.curCardinfo.cardtypeName);
    			addProRecord_cardInfo("会员姓名:"+checkNull(responsetext.curCardinfo.membername));
    			addProRecord_cardInfo("会员类型:"+checkNull(responsetext.curCardinfo.cardtypeName));
    			document.getElementById("csname").value=checkNull(responsetext.curCardinfo.membername);
    			document.getElementById("cardsource").value="收购卡";
    			document.getElementById("cscurkeepamt").value=checkNull(responsetext.curCardinfo.account5Amt);
    			document.getElementById("sumKeepBalance").value=checkNull(responsetext.curCardinfo.account5Amt);
    			document.getElementById("cscurdepamt").value=checkNull(responsetext.curCardinfo.account5debtAmt);
    			addProRecord_cardInfo("收购账户:"+checkNull(responsetext.curCardinfo.account5Amt));
    			document.getElementById("cardHomeSource").value=checkNull(responsetext.curCardinfo.id.cardvesting);
    			handleRadio("curMconsumeinfo.csersex",checkNull(responsetext.curCardinfo.membersex));
    			handleRadio("curMconsumeinfo.csertype",1);
		   		document.getElementById("dXjcDiscount").value=ForDight(responsetext.DCostProjectRate,2);
	   			document.getElementById("shougoucardno").readOnly="readOnly";
	   			document.getElementById("curCostAmt").value=0;
	   			document.getElementById("curMfCostCount").value=0;
				document.getElementById("curMrCostCount").value=0;
	   			
	   		}
	   	
	   	}catch(e){alert(e.message);}
    }

//---------------------------------------------获得服务类型 项目或产品
function validateItemType(record)
{
	var rowdata=commoninfodivdetial.updateRow(curRecord,{csitemname:'',csitemno:'',csitemcount:0,
     				csunitprice:0,csdisprice:0,csitemamt:0,csdiscount:0,cspaymode:'',csfirstsaler:'',csfirsttype:'',csfirstshare:'',
     				cssecondsaler:'',cssecondtype:'',cssecondshare:'',csthirdsaler:'',csthirdtype:'',csthirdshare:'',csproseqno:0});  
    showTextByinfoType(rowdata,1);	
}
//---------------------------------------------验证项目编号
function validateItem(record)
{
	var curItemValue="";
	if(curitemManger!=null && curitemManger.inputText.val()!="")
		curItemValue=curitemManger.inputText.val();
	else
		curItemValue=curRecord.csitemname;
	curitemManger=null;	
	var requestUrl ="cc011/validateItem.action"; 
	var responseMethod="validateItemMessage";
	if(curItemValue=="" )
	{
		commoninfodivdetial.updateRow(curRecord,{csitemname:'',csitemno: ''});
		return ;
	}
	if( curItemValue.indexOf('_')!=-1)
	{
		curItemValue=curItemValue.substring(0,curItemValue.indexOf('_'));
	}
	var params="itemType="+curRecord.bcsinfotype;
	var params=params+"&strCurItemId="+curItemValue;	
	var params=params+"&strCardType="+document.getElementById("cscardtype").value;	
	params=params+"&cardUseType="+document.getElementById("billflag").value;		

	sendRequestForParams_p(requestUrl,responseMethod,params );
	
}

function validateItemMessage(request)
{
	try
	{
     var responsetext = eval("(" + request.responseText + ")");
     var dCostProjectRate=ForDight(responsetext.DCostProjectRate,2);
     if(curRecord.bcsinfotype==1)
     {
     	var curProjectinfo=responsetext.curProjectinfo;
     	if(curProjectinfo==null)
     	{
     		$.ligerDialog.warn("输入的项目编码不存在!");
     		commoninfodivdetial.updateRow(curRecord,{csitemname:'',csitemno: ''});
     	}
     	else
     	{
     		if(document.getElementById("cscardno").value=="散客")
     		{
     				commoninfodivdetial.updateRow(curRecord,{csitemname:curProjectinfo.prjname,csitemno: curProjectinfo.id.prjno,csitemcount:1,
     				csunitprice:curProjectinfo.saleprice,csdisprice :curProjectinfo.saleprice,csitemamt:ForDight(curProjectinfo.saleprice*1,0),csdiscount:1,cspaymode:1,csproseqno:0});
     		}
     		else
     		{
     				commoninfodivdetial.updateRow(curRecord,{csitemname:curProjectinfo.prjname,csitemno: curProjectinfo.id.prjno,csitemcount:1,
     				csunitprice:curProjectinfo.saleprice,csdisprice :curProjectinfo.saleprice,csitemamt:ForDight(curProjectinfo.saleprice*1*dCostProjectRate*1,0),csdiscount:dCostProjectRate,cspaymode:4,csproseqno:0});
     		}
     		//commoninfodivdetial.updateCell(1,curProjectinfo.prjname,curRecord);
     	
     	}
     }
     else
     {
     	var curGoodsinfo=responsetext.curGoodsinfo;
     	if(curGoodsinfo==null)
     	{
     		$.ligerDialog.warn("输入的产品编码不存在!");
     		commoninfodivdetial.updateRow(curRecord,{csitemname:'',csitemno: ''});
     	}
     	else
     	{
     		if(document.getElementById("cscardno").value=="散客")
     		{
     				commoninfodivdetial.updateRow(curRecord,{csitemname: curGoodsinfo.goodsname,csitemno: curGoodsinfo.id.goodsno,csitemcount:1,
     				csunitprice: curGoodsinfo.standprice,csdisprice: curGoodsinfo.standprice, csitemamt: ForDight(curGoodsinfo.standprice*1,0),csdiscount:1,cspaymode:1,csproseqno:0});
     		}
     		else
     		{
     				
     				commoninfodivdetial.updateRow(curRecord,{csitemname: curGoodsinfo.goodsname,csitemno: curGoodsinfo.id.goodsno,csitemcount:1,
     				csunitprice: curGoodsinfo.standprice,csdisprice: curGoodsinfo.standprice,csitemamt: ForDight(curGoodsinfo.standprice*1,0),csdiscount:4,cspaymode:1,csproseqno:0});
     		}
     	
     	}
     }
     handPayList();
     }catch(e){alert(e.message);}
     showTextByinfoType(curRecord,1);
}
  //----------------------------------------------------验证分享员工Start 
function validateFristSaleType(obj)
{
	var curempValue="";
	if(curEmpManger!=null && curEmpManger.inputText.val()!="")
		curempValue=curEmpManger.inputText.val();
	else
		curempValue=obj.value;
	curEmpManger=null;
	if(curempValue!="")
	{
		if(curempValue!="" && curempValue.indexOf('_')==-1)
    	{

    		var exists=0;
	    	for(var i=0;i<parent.StaffInfo.length;i++)
	    	{
	    		if(curempValue==parent.StaffInfo[i].bstaffno)
	    		{
	    			commoninfodivdetial.updateRow(curRecord,{csfirstsaler: parent.StaffInfo[i].bstaffno});
	    			exists=1;
	    			break;
	    		}
	    			
	    	}
	    	if(exists==0)
	    	{
	    		commoninfodivdetial.updateRow(curRecord,{csfirstsaler:''});
	    	}
	    }
		if(curRecord.bcsinfotype==1)
		{
			commoninfodivdetial.updateRow(curRecord,{csfirsttype:2});
		}
		else
		{
			commoninfodivdetial.updateRow(curRecord,{csfirsttype:""});
		}
	}
	else
	{
		commoninfodivdetial.updateRow(curRecord,{csfirsttype:''});
	}
	//initDetialGrid();
}
function validateSecondSaleType(obj)
{
	var curempValue="";
	if(curEmpManger!=null && curEmpManger.inputText.val()!="")
		curempValue=curEmpManger.inputText.val();
	else
		curempValue=obj.value;
	curEmpManger=null;
	if(curempValue!="")
	{
		if(curempValue!="" && curempValue.indexOf('_')==-1)
    	{

    		var exists=0;
	    	for(var i=0;i<parent.StaffInfo.length;i++)
	    	{
	    		if(curempValue==parent.StaffInfo[i].bstaffno)
	    		{
	    			commoninfodivdetial.updateRow(curRecord,{cssecondsaler: parent.StaffInfo[i].bstaffno});
	    			exists=1;
	    			break;
	    		}
	    			
	    	}
	    	if(exists==0)
	    	{
	    		commoninfodivdetial.updateRow(curRecord,{cssecondsaler:''});
	    	}
	    }
		if(curRecord.bcsinfotype==1)
		{
			commoninfodivdetial.updateRow(curRecord,{cssecondtype:2});
		}
		else
		{
			commoninfodivdetial.updateRow(curRecord,{cssecondtype:""});
		}
	}
	else
	{
		commoninfodivdetial.updateRow(curRecord,{cssecondtype:''});
	}
	//initDetialGrid();
}
function validateThirdSaleType(obj)
{
	var curempValue="";
	if(curEmpManger!=null && curEmpManger.inputText.val()!="")
		curempValue=curEmpManger.inputText.val();
	else
		curempValue=obj.value;
	curEmpManger=null;
	if(curempValue!="")
	{
		if(curempValue!="" && curempValue.indexOf('_')==-1)
    	{

    		var exists=0;
	    	for(var i=0;i<parent.StaffInfo.length;i++)
	    	{
	    		if(curempValue==parent.StaffInfo[i].bstaffno)
	    		{
	    			commoninfodivdetial.updateRow(curRecord,{csthirdsaler: parent.StaffInfo[i].bstaffno});
	    			exists=1;
	    			break;
	    		}
	    			
	    	}
	    	if(exists==0)
	    	{
	    		commoninfodivdetial.updateRow(curRecord,{csthirdsaler:''});
	    	}
	    }
		if(curRecord.bcsinfotype==1)
		{
			commoninfodivdetial.updateRow(curRecord,{csthirdtype:2});
		}
		else
		{
			commoninfodivdetial.updateRow(curRecord,{csthirdtype:""});
		}
	}
	else
	{
		commoninfodivdetial.updateRow(curRecord,{csthirdtype:''});
	}
	//initDetialGrid();
}
  //----------------------------------------------------验证分享员工End 
//------------------------------------------------------验证分享金额Start
function validateFristSaleShare(obj)
{
	if(obj.value!="" && obj.value*1!=0 && obj.value>curRecord.csitemamt)
	{
		$.ligerDialog.warn("大工的分享金额超过了   "+curRecord.csitemname+"   的总金额!");
		commoninfodivdetial.updateRow(curRecord,{csfirstshare:""});
	}
}
function validateSecondSaleShare(obj)
{
	if(obj.value!="" && obj.value*1!=0 && obj.value>curRecord.csitemamt)
	{
		$.ligerDialog.warn("中工的分享金额超过了   "+curRecord.csitemname+"   的总金额!");
		commoninfodivdetial.updateRow(curRecord,{cssecondshare:""});
	}
}
function validateThirdSaleShare(obj)
{
	if(obj.value!="" && obj.value*1!=0 && obj.value>curRecord.csitemamt)
	{
		$.ligerDialog.warn("中工的分享金额超过了   "+curRecord.csitemname+"   的总金额!");
		commoninfodivdetial.updateRow(curRecord,{csthirdshare:""});
	}
}
//------------------------------------------------------验证分享金额End
//------------------------------------------------------验证数量Start
function validateCostCount(obj)
{
	var costPrice=checkNull(curRecord.csunitprice)*1;
	var costdiscount=checkNull(curRecord.csdiscount)*1;
	commoninfodivdetial.updateRow(curRecord,{csitemamt:ForDight(checkNull(obj.value)*1*costPrice*costdiscount,0)});
	handPayList();
}
//------------------------------------------------------验证数量End
//------------------------------------------------------验证金额Start
function validateCostAmt(obj)
{
	handPayList();
}
//------------------------------------------------------验证金额End
//------------------------------------------------------验证支付方式Start
function validatePaycode(obj)
{
	var curItem=checkNull(curRecord.csitemname);
	if(obj.value!="" && curItem=="")
	{
		commoninfodivdetial.updateRow(curRecord,{cspaymode: ""});
		$.ligerDialog.warn("选择支付方式前请先确定消费项目 !");
	}
	if(obj.value=="11" || obj.value=="13" || obj.value=="16")
	{
		commoninfodivdetial.updateRow(curRecord,{cspaymode: ""});
		$.ligerDialog.warn("该支付方式不能手动选择,请重新输入!");
	}
	handPayList();
}

function handPayList()
{
	var paycode1="";
	var payamt1=0;
	var paycode2="";
	var payamt2=0;
	var paycode3="";
	var payamt3=0;
	var paycode4="";
	var payamt4=0;
	var paycode5="";
	var payamt5=0;
	var paycode6="";
	var payamt6=0;
	var curPaycode="";
	var curPayamt=0;
	var curProjectAmt=0;
	var curGoodsAmt=0;
	var totalCash=0;
	var totalBank=0;
	for (var rowid in commoninfodivdetial.records)
	{
		var row =commoninfodivdetial.records[rowid]; 
		curPaycode=checkNull(row.cspaymode);
		curPayamt=checkNull(row.csitemamt)*1;
		if(checkNull(row.cspaymode)=="1")
		{
			totalCash=totalCash*1+curPayamt*1;
		}
		if(checkNull(row.cspaymode)=="6")
		{
			totalBank=totalBank*1+curPayamt*1;
		}
		if(checkNull(row.csitemname)!="" && curPaycode!="" && curPayamt!=0)
		{
			if(paycode1=="" || paycode1==curPaycode)
			{
				paycode1=curPaycode;
				payamt1=payamt1*1+curPayamt;
			}
			else if( paycode2=="" || paycode2==curPaycode )
			{
				paycode2=curPaycode;
				payamt2=payamt2*1+curPayamt;
			}
			else if( paycode3=="" || paycode3==curPaycode )
			{
				paycode3=curPaycode;
				payamt3=payamt3*1+curPayamt;
			}
			else if( paycode4=="" || paycode4==curPaycode )
			{
				paycode4=curPaycode;
				payamt4=payamt4*1+curPayamt;
			}
			else if( paycode5=="" || paycode5==curPaycode )
			{
				paycode5=curPaycode;
				payamt5=payamt5*1+curPayamt;
			}
			else if( paycode6=="" || paycode6==curPaycode )
			{
				paycode6=curPaycode;
				payamt6=payamt6*1+curPayamt;
			}
			if(checkNull(row.bcsinfotype)==1)
			 	curProjectAmt=curProjectAmt*1+curPayamt*1;
			 else
			 	curGoodsAmt=curGoodsAmt*1+curPayamt*1;
		}
	}
	var rowslength=commoninfodivdetial_payInfo.rows.length;
    for (var rowid =0;rowid<rowslength*1;rowid++)
	{
			commoninfodivdetial_payInfo.deleteRow(0); 
	}
	//第一支付
	if(paycode1!="")
	{
		//document.getElementById("paycode1_div").style.display="block";
		//document.getElementById("paycode1_img").src=contextURL+"/common/funtionimage/paycode"+paycode1+".jpg"
		//document.getElementById("payamt1_lb").innerHTML=ForDight(payamt1*1,1);
		document.getElementById("strPayMode1").value=paycode1;
		document.getElementById("dPayAmt1").value=ForDight(payamt1,0);
		addProRecord_payInfo(parent.loadCommonControlValue("ZFFS",0,paycode1)+":"+ForDight(payamt1*1,0));
	}
	else
	{
		//document.getElementById("paycode1_div").style.display="none";
		//document.getElementById("paycode1_img").src=contextURL+"/common/funtionimage/paycode"+paycode1+".jpg"
		//document.getElementById("payamt1_lb").innerHTML=0;
		document.getElementById("strPayMode1").value="";
		document.getElementById("dPayAmt1").value=0;
	}
	//第二支付
	if(paycode2!="")
	{
		//document.getElementById("paycode2_div").style.display="block";
		//document.getElementById("paycode2_img").src=contextURL+"/common/funtionimage/paycode"+paycode2+".jpg"
		//document.getElementById("payamt2_lb").innerHTML=ForDight(payamt2*1,1);
		document.getElementById("strPayMode2").value=paycode2;
		document.getElementById("dPayAmt2").value=ForDight(payamt2,0);
		addProRecord_payInfo(parent.loadCommonControlValue("ZFFS",0,paycode2)+":"+ForDight(payamt2*1,0));
	}
	else
	{
		//document.getElementById("paycode2_div").style.display="none";
		//document.getElementById("paycode2_img").src=contextURL+"/common/funtionimage/paycode"+paycode2+".jpg"
		//document.getElementById("payamt2_lb").innerHTML=0;
		document.getElementById("strPayMode2").value="";
		document.getElementById("dPayAmt2").value=0;
	}
	//第三支付
	if(paycode3!="")
	{
		//document.getElementById("paycode3_div").style.display="block";
		//document.getElementById("paycode3_img").src=contextURL+"/common/funtionimage/paycode"+paycode3+".jpg"
		//document.getElementById("payamt3_lb").innerHTML=ForDight(payamt3*1,1);
		document.getElementById("strPayMode3").value=paycode3;
		document.getElementById("dPayAmt3").value=ForDight(payamt3,0);
		addProRecord_payInfo(parent.loadCommonControlValue("ZFFS",0,paycode3)+":"+ForDight(payamt3*1,0));
	}
	else
	{
		//document.getElementById("paycode3_div").style.display="none";
		//document.getElementById("paycode3_img").src=contextURL+"/common/funtionimage/paycode"+paycode3+".jpg"
		//document.getElementById("payamt3_lb").innerHTML=0;
		document.getElementById("strPayMode3").value="";
		document.getElementById("dPayAmt3").value=0;
	}
	//第四支付
	if(paycode4!="")
	{
		//document.getElementById("paycode4_div").style.display="block";
		//document.getElementById("paycode4_img").src=contextURL+"/common/funtionimage/paycode"+paycode4+".jpg"
		//document.getElementById("payamt4_lb").innerHTML=ForDight(payamt4*1,1);
		document.getElementById("strPayMode4").value=paycode4;
		document.getElementById("dPayAmt4").value=ForDight(payamt4,0);
		addProRecord_payInfo(parent.loadCommonControlValue("ZFFS",0,paycode4)+":"+ForDight(payamt4*1,0));
	}
	else
	{
		//document.getElementById("paycode4_div").style.display="none";
		//document.getElementById("paycode4_img").src=contextURL+"/common/funtionimage/paycode"+paycode4+".jpg"
		//document.getElementById("payamt4_lb").innerHTML=0;
		document.getElementById("strPayMode4").value="";
		document.getElementById("dPayAmt4").value=0;
	}
	//第五支付
	if(paycode5!="")
	{
		//document.getElementById("paycode5_div").style.display="block";
		//document.getElementById("paycode5_img").src=contextURL+"/common/funtionimage/paycode"+paycode5+".jpg"
		//document.getElementById("payamt5_lb").innerHTML=ForDight(payamt5*1,1);
		document.getElementById("strPayMode5").value=paycode5;
		document.getElementById("dPayAmt5").value=ForDight(payamt5,0);
		addProRecord_payInfo(parent.loadCommonControlValue("ZFFS",0,paycode5)+":"+ForDight(payamt5*1,0));
	}
	else
	{
		//document.getElementById("paycode5_div").style.display="none";
		//document.getElementById("paycode5_img").src=contextURL+"/common/funtionimage/paycode"+paycode5+".jpg"
		//document.getElementById("payamt5_lb").innerHTML=0;
		document.getElementById("strPayMode5").value="";
		document.getElementById("dPayAmt5").value=0;
	}
	//第六支付
	if(paycode6!="")
	{
		//document.getElementById("paycode6_div").style.display="block";
		//document.getElementById("paycode6_img").src=contextURL+"/common/funtionimage/paycode"+paycode6+".jpg"
		//document.getElementById("payamt6_lb").innerHTML=ForDight(payamt6*1,1);
		document.getElementById("strPayMode6").value=paycode6;
		document.getElementById("dPayAmt6").value=ForDight(payamt6,0);
		addProRecord_payInfo(parent.loadCommonControlValue("ZFFS",0,paycode6)+":"+ForDight(payamt6*1,0));
	}
	else
	{
		//document.getElementById("paycode6_div").style.display="none";
		//document.getElementById("paycode6_img").src=contextURL+"/common/funtionimage/paycode"+paycode6+".jpg"
		//document.getElementById("payamt6_lb").innerHTML=0;
		document.getElementById("strPayMode6").value="";
		document.getElementById("dPayAmt6").value=0;
	}
	addProRecord_payInfo("");
	addProRecord_payInfo("<font color=\"red\">项目总额</font>:&nbsp;&nbsp;&nbsp;&nbsp;"+ForDight(curProjectAmt*1,0));
	addProRecord_payInfo("<font color=\"red\">产品总额:</font>&nbsp;&nbsp;&nbsp;&nbsp;"+ForDight(curGoodsAmt*1,0));
	addProRecord_payInfo("");
	addProRecord_payInfo("<font color=\"blue\" size=\"4\"><b>现金:</font>&nbsp;&nbsp;<font size=\"4\">"+ForDight(totalCash*1,0)+"</font></b><font color=\"blue\" size=\"4\"><b>&nbsp;&nbsp;刷卡:</font>&nbsp;&nbsp;<font size=\"4\">"+ForDight(totalBank*1,0)+"</font></b>");
	
	//document.getElementById("dProjectAmt").innerHTML=ForDight(curProjectAmt*1,1);
	//document.getElementById("dGoodsAmt").innerHTML=ForDight(curGoodsAmt*1,1);
	//document.getElementById("dTotalsAmt").innerHTML=ForDight(curGoodsAmt*1+curProjectAmt*1,1);
	
}
//------------------------------------------------------验证支付方式End
//------------------------------------------------------获取疗程明细
	function f_onCheckAllRow(checked)
    {
            for (var rowid in commoninfodivdetial_Pro.records)
            {
                if(checked)
                    addCheckedBcodekey(commoninfodivdetial_Pro.records[rowid]['bproseqno']);
                else
                    removeCheckedBcodekey(commoninfodivdetial_Pro.records[rowid]['bproseqno']);
            }
    }

  	function f_onCheckRow(checked, data)
  	{
       	if (checked) 
       	{
          	addCheckedBcodekey(data.bproseqno);
      	}
	 	else 
	 	{
         	removeCheckedBcodekey(data.bproseqno);
  		}
	}
   	var checkedBcodekey= [];
        
   	function findCheckedBcodekey(bproseqno)
   	{
    	for(var i =0;i<checkedBcodekey.length;i++)
       	{
       		if(checkedBcodekey[i] == bproseqno) return i;
       	}
     	return -1;
  	}
    function addCheckedBcodekey(bproseqno)
    {
     	if(findCheckedBcodekey(bproseqno) == -1)
         	checkedBcodekey.push(bproseqno);
    }
    function removeCheckedBcodekey(bproseqno)
    {
    	var i = findCheckedBcodekey(bproseqno);
  		if(i==-1) return;
    		checkedBcodekey.splice(i,1);
    }
   	function itemclick_proInfo(item)
    {
    	try
    	{
    		var detiallength=commoninfodivdetial.rows.length*1;
	    	for (var rowid in commoninfodivdetial_Pro.records)
			{
				//if(findCheckedBcodekey(commoninfodivdetial_Pro.records[rowid]['bproseqno'])!=-1)
				{	
					var row =commoninfodivdetial_Pro.records[rowid]; 
					if(checkNull(row.curcostcount)=="" || row.curcostcount==0)
					{
						continue;
					}
					if(detiallength==0)
					{
						addRecord();
						//detiallength=detiallength+1;
					}
					else if( commoninfodivdetial.getRow(0)['csitemname']!="")
					{
						addRecord();
						//detiallength=detiallength+1;
					}
					var curlastcount=ForDight(row.targetlastcount,1);
					var curlastamt=ForDight(row.lastamt,1);
					var curprice=ForDight(curlastamt/curlastcount,1)
					var curcostamt=ForDight(curprice*1*row.curcostcount*1,1);
					//curRecord=commoninfodivdetial.getRow(detiallength-1);
					var rowdata=commoninfodivdetial.updateRow(curRecord,{csitemname: row.bprojectname,csitemno:row.bprojectno,csitemcount:ForDight(row.curcostcount,1),
     				csunitprice:curprice,csdisprice :curprice,csitemamt:curcostamt,csdiscount:1,cspaymode:'9',csfirstsaler:'',csfirsttype:'',csfirstshare:'',
     				cssecondsaler:'',cssecondtype:'',cssecondshare:'',csthirdsaler:'',csthirdtype:'',csthirdshare:'',csproseqno:row.bproseqno});  
   	 				
				}
			}
			
			document.getElementById("wCostItemNo").readOnly="readOnly";
		    document.getElementById("wCostItemNo").style.background="#EDF1F8";
			document.getElementById("wCostItemBarNo").readOnly="readOnly";
		    document.getElementById("wCostItemBarNo").style.background="#EDF1F8";
		    document.getElementById("wgCostCount").readOnly="readOnly";
		    document.getElementById("wgCostCount").style.background="#EDF1F8";
		    			
			var detialprolen=commoninfodivdetial_Pro.rows.length*1;
			for (var i=0;i<detialprolen;i++)
			{
				//if(findCheckedBcodekey(commoninfodivdetial_Pro.getRow(i)['bproseqno'])!=-1)
				{
					if(checkNull(commoninfodivdetial_Pro.getRow(i)['curcostcount'])=="" || checkNull(commoninfodivdetial_Pro.getRow(i)['curcostcount'])==0)
					{
						continue;
					}
					commoninfodivdetial_Pro.deleteRow(i);
					detialprolen=detialprolen-1;
					i--;
				}
			}
			initDetialGrid();
			handPayList();
		}catch(e){alert(e.message);}
    }    
    
      
    function comCostProAfterEdit(e)
	{
		if(e.record.curcostcount!="" && e.record.lastcount *1 < e.record.curcostcount)
		{
			$.ligerDialog.warn("当次疗程消耗次数不能超过剩余次数!");
			commoninfodivdetial_Pro.updateRow(e.record,{curcostcount: 0}); 
		}
		
		

	}



function initDetialGrid()
{
	/*for (var rowid in commoninfodivdetial.records)
	{
		var row =commoninfodivdetial.records[rowid];
		if(checkNull(row.cspaymode)==9 || checkNull(row.cspaymode)==16 || checkNull(row.cspaymode)==13 || checkNull(row.cspaymode)==11)
			showTextByinfoType(row,2);     
		else
			showTextByinfoType(row,1);   		 
	}*/
}
function comsumAfterEdit(e)
{

	initDetialGrid();
	
}

function comsumbeforeEdit(e)
{
	
}
function showTextByinfoType(rowdata,readType)
{
	try
	{
	
	var column  =null ;

	if(rowdata.bcsinfotype==1)
	{
		column=commoninfodivdetial.columns[9];
		commoninfodivdetial.setCellEditing(rowdata, column, true);
		column=commoninfodivdetial.columns[12];
		commoninfodivdetial.setCellEditing(rowdata, column, true);
		column=commoninfodivdetial.columns[15];
		commoninfodivdetial.setCellEditing(rowdata, column, true);
		
		/*column=commoninfodivdetial.columns[9];
		commoninfodivdetial.toggleCol(column, true);
		column=commoninfodivdetial.columns[12];
		commoninfodivdetial.toggleCol(column, true);
		column=commoninfodivdetial.columns[15];
		commoninfodivdetial.toggleCol(column, true);
		column=commoninfodivdetial.columns[8];
		commoninfodivdetial.toggleCol(column, false);
		column=commoninfodivdetial.columns[11];
		commoninfodivdetial.toggleCol(column, false);
		column=commoninfodivdetial.columns[14];
		commoninfodivdetial.toggleCol(column, false);*/

	}
	else
	{
	
		/*column=commoninfodivdetial.columns[9];
		commoninfodivdetial.toggleCol(column, false);
		column=commoninfodivdetial.columns[12];
		commoninfodivdetial.toggleCol(column, false);
		column=commoninfodivdetial.columns[15];
		commoninfodivdetial.toggleCol(column, false);
		column=commoninfodivdetial.columns[8];
		commoninfodivdetial.toggleCol(column, true);
		column=commoninfodivdetial.columns[11];
		commoninfodivdetial.toggleCol(column, true);
		column=commoninfodivdetial.columns[14];
		commoninfodivdetial.toggleCol(column, true);*/
		column=commoninfodivdetial.columns[8];
		commoninfodivdetial.setCellEditing(rowdata, column, true);
		column=commoninfodivdetial.columns[11];
		commoninfodivdetial.setCellEditing(rowdata, column, true);
		column=commoninfodivdetial.columns[14];
		commoninfodivdetial.setCellEditing(rowdata, column, true);
		
	}
	
		if(readType==2)//项目,支付方式,数量金额屏蔽
		{
			column=commoninfodivdetial.columns[0];
			commoninfodivdetial.setCellEditing(rowdata, column, true);
			column=commoninfodivdetial.columns[1];
			commoninfodivdetial.setCellEditing(rowdata, column, true);
			column=commoninfodivdetial.columns[2];
			commoninfodivdetial.setCellEditing(rowdata, column, true);
			column=commoninfodivdetial.columns[5];
			commoninfodivdetial.setCellEditing(rowdata, column, true);
			column=commoninfodivdetial.columns[6];
			commoninfodivdetial.setCellEditing(rowdata, column, true);
		}
	}
	catch(e){alert(e.message);}
}

//-----------------------------------项目明细按钮
    function itemclick_serviceInfo(item)
    {
    	try
    	{
	    	if(item.text=="增加")
		    {
		       addRecord();
		    }
		    else if(item.text=="删除")
		    {
		       deleteCurRecord();
		    } 	
		       
    	}
    	catch(e){alert(e.message);}
    }
    
    function addRecord()
    {
    		if(addRecordFlag==1)
    		{
    			$.ligerDialog.warn("明细不可新增,请确认!");
    			return ;
    		}
    		if(commoninfodivdetial.rows.length*1>0 && checkNull(curRecord.csitemno)=="")
    		{
    			return ;
    		}
    		var detiallength=0;
    		var row = commoninfodivdetial.getSelectedRow();
    		if(commoninfodivdetial.rows.length*1==0)
    		{
    			detiallength=-1;
    		}
    		else
    		{
    			detiallength=row['__index']*1;
    		}
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivdetial.addRow({ 
				bcsinfotype: "1",
				csitemno:"",
				csitemunit:"",
				csitemcount:0,
				csunitprice:0,
				csdiscount:0,
				csdisprice:0,
				csitemamt:0,
				cspaymode:"",
				csfirstsaler:"",
				csfirsttype:"",
				csfirstinid:"",
				csfirstshare:"",
				cssecondsaler:"",
				cssecondtype:"",
				cssecondinid:"",
				cssecondshare:"",
				csthirdsaler:"",
				csthirdtype:"",
				csthirdinid:"",
				csthirdshare:"",
				csitemname: "" ,
				csproseqno:"",  
				goodsbarno:""  ,
				shareflag:0   ,
				costpricetype:0,
				csitemstate:0      
				}, row, false);
		initDetialGrid();
		curEmpManger=null;
		curitemManger=null;
		commoninfodivdetial.select(detiallength*1+1);
		initFastGrid();
    }
    
    function initFastGrid()
    {
    	document.getElementById("wCostItemNo").value="";
    	document.getElementById("wCostPCount").value="";
    	document.getElementById("wCostItemBarNo").value="";
    	document.getElementById("wCostCount").value="";
    	document.getElementById("itempay").value="1";
    	document.getElementById("wCostFirstEmpNo").value="";
    	document.getElementById("wCostFirstEmptype").value="2";
    	document.getElementById("wCostFirstEmpshare").value="";
    	document.getElementById("wCostSecondEmpNo").value="";
    	document.getElementById("wCostSecondEmptype").value="2";
    	document.getElementById("wCostSecondEmpshare").value="";
    	document.getElementById("wCostthirthdEmpNo").value="";
    	document.getElementById("wCostthirthEmptype").value="2";
    	document.getElementById("wCostthirthEmpshare").value="";
    	
    	document.getElementById("wCostItemNo").readOnly="";
    	document.getElementById("wCostItemNo").readOnly="";
    	document.getElementById("wCostCount").readOnly="";
    	document.getElementById("wCostItemBarNo").readOnly="";
    	document.getElementById("wCostFirstEmpshare").readOnly="";
    	document.getElementById("wCostSecondEmpshare").readOnly="";
    	document.getElementById("wCostthirthEmpshare").readOnly="";
    	
    	document.getElementById("wCostItemNo").style.background="#FFFFFF";
    	document.getElementById("wCostItemNo").style.background="#FFFFFF";
    	document.getElementById("wCostCount").style.background="#FFFFFF";
    	document.getElementById("wCostItemBarNo").style.background="#FFFFFF";
    	document.getElementById("wCostFirstEmpshare").style.background="#FFFFFF";
    	document.getElementById("wCostSecondEmpshare").style.background="#FFFFFF";
    	document.getElementById("wCostthirthEmpshare").style.background="#FFFFFF";
    	
    	document.getElementById("wCostFirstEmptype").disabled=false;
    	document.getElementById("wCostSecondEmptype").disabled=false;
    	document.getElementById("wCostthirthEmptype").disabled=false;
    	document.getElementById("wCostItemNo").select();
    	document.getElementById("wCostItemNo").focus();
    }
    
    function addProRecord()
    {
    		
    		var row = commoninfodivdetial_Pro.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivdetial_Pro.addRow({ 
				          bproseqno:''
			}, row, false);
    }
    function addProRecord_cardInfo(strInfo)
    {
    		var row = commoninfodivdetial_cardInfo.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivdetial_cardInfo.addRow({ 
				    strcardinfo: strInfo       
			}, row, false);
    }
    
    function addProRecord_payInfo(strInfo)
    {
    		var row = commoninfodivdetial_payInfo.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivdetial_payInfo.addRow({ 
				    strpayinfo: strInfo       
			}, row, false);
    }
   
    function addProRecord_padInfo()
    {
    		var row = commoninfodivdetial_padInfo.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivdetial_padInfo.addRow({ 
				    SMALL_NO: "",
				    CUSTOM: ""   
			}, row, false);
    }
    
    function deleteCurRecord()
    {
    	$.ligerDialog.confirm('确认删除当前选中行?', function (result)
		{
		    if( result==true)
           	{
				commoninfodivdetial.deleteSelectedRow();
			
			}
		});  
    	
    }
    
    //-----------------------------------保存消费信息
    function editCurRecord()
    {
    	if(parent.hasFunctionRights( "CC011",  "UR_POST")!=true)
        {
        	$.ligerDialog.warn("该用户没有保存权限,请确认!");
        	return;
        }
   		if(pageState==3)
   		{
   			$.ligerDialog.warn("非新增单据,不可保存!");
   			return;
   		}
   		else
   		{
   					if(postState==1)
					 {
					 	$.ligerDialog.error("正在保存中,请不要连续保存!");
					 	return ;
					 }
   					postState=1;
   					handPayList();
	   				var queryStringTmp=$('#consumCenterForm').serialize();//serialize('#detailForm');
					queryStringTmp=queryStringTmp.replace(/\+/g," ");
					
					var params=queryStringTmp;
					var strJsonParam_five="";
					var curjosnparam ="";
					var needReplaceStr="";
					var keepPayamt=0;
					var diyongPayamt=0;
					var jifenPayamt=0;
					var sendPayamt=0;
					var totalShareAmt=0;
					var iscardcostflag=0;
					var vmfcostcount=0
					var vmrcostcount=0
					var newCustomerByTjCount=0;//推荐新客数量
					for (var rowid in commoninfodivdetial.records)
					{
						 	var row =commoninfodivdetial.records[rowid];
						 	if(row['cspaymode']=="4" ||row['cspaymode']=="17" || row['cspaymode']=="9")
						 	{
						 		iscardcostflag=1;
						 	}
						 	if( checkNull(row['csitemno'])!="" && document.getElementById("paramSp104").value==1  && row['cspaymode']=="9")
						 	{
						 		for(var i=0;i<parent.lsProjectinfo.length;i++)
								{
									if(parent.lsProjectinfo[i].id.prjno== checkNull(row['csitemno']) )
									{
										if(parent.lsProjectinfo[i].prjtype=="3") //美发
										{
											vmfcostcount=vmfcostcount*1+checkNull(row['csitemcount'])*1;
											break;
										}
										else if(parent.lsProjectinfo[i].prjtype=="4" )
										{ 
											vmrcostcount=vmrcostcount*1+checkNull(row['csitemcount'])*1;
											break;
										}
									}
								}
						 	}
						 	if(checkNull(row['csitemno'])!="" && checkNull(row['csfirstsaler'])=="")
						 	{
						 	    $.ligerDialog.error("大工工号不能为空!");
						 		postState=0;
							 	return ;
						 	}
						 	if(document.getElementById("billflag").value==0 && (row['cspaymode']=="4" || row['cspaymode']=="7"|| row['cspaymode']=="A" || row['cspaymode']=="17"))
						 	{
						 		$.ligerDialog.error("散客不能用卡账户消费!");
						 		postState=0;
							 	return ;
						 	}
						 	if(checkNull(row['csitemno'])!="" && ForDight(row['csitemamt']*1,1)==0)
						 	{
						 		$.ligerDialog.error(checkNull(row['csitemno'])+" 支付金额不能为0,若需更改支付请直接修改支付方式!");
						 		postState=0;
							 	return ;
						 	}
						 	if(document.getElementById("billflag").value==2 && row['cspaymode']=="4")
						 	{
						 		$.ligerDialog.error("收购卡账户不能使用储值账户消费!");
						 		postState=0;
							 	return ;
						 	}
						 	if(row['cspaymode']=="4" || row['cspaymode']=='A')
						 	{
						 		keepPayamt=keepPayamt*1+row['csitemamt']*1;
						 	}
						 	else if(row['cspaymode']=="12" )
						 	{
						 		diyongPayamt=diyongPayamt*1+row['csitemamt']*1;
						 	}
						 	else if(row['cspaymode']=="7" )
						 	{
						 		jifenPayamt=jifenPayamt*1+row['csitemamt']*1;
						 	}
						 	else if(row['cspaymode']=="17" )
						 	{
						 		sendPayamt=sendPayamt*1+row['csitemamt']*1;
						 	}
						 	totalShareAmt=0;
						 	totalShareAmt=totalShareAmt*1+checkNull(row['csfirstshare'])*1;
						 	totalShareAmt=totalShareAmt*1+checkNull(row['cssecondshare'])*1;
						 	totalShareAmt=totalShareAmt*1+checkNull(row['csthirdshare'])*1;
						 	if(ForDight(totalShareAmt*1,1)>ForDight(row['csitemamt']*1,1))
							{
							 	$.ligerDialog.error("分享金额不能超过现有产品消费金额!");
							 	postState=0;
							 	return ;
							}
							if(row['csfirsttype']=="3")
							{
								newCustomerByTjCount=newCustomerByTjCount*1+ForDight(checkNull(row['csitemcount'])*1,1);
							}
						 	curjosnparam=JSON.stringify(row);
						 	curjosnparam=curjosnparam.replace("%","");
						 	curjosnparam=curjosnparam.replace("#","");
							/*if(curjosnparam.indexOf("_id")>-1)
						  	{
						     	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
						      	curjosnparam=curjosnparam.replace(needReplaceStr,"");
						    }	*/            		   
						    if(strJsonParam_five!="")
						      	strJsonParam_five=strJsonParam_five+",";
						    strJsonParam_five= strJsonParam_five+curjosnparam;        		 
					 }	
					 if(newCustomerByTjCount*1>1)
					 {
					 	$.ligerDialog.error("新客推荐项目只允许标记一个项目,请确认标记项目数量!");
					 	postState=0;
					 	return ;
					 }
					 if(document.getElementById("recommendempid").value!="" && newCustomerByTjCount*1==0)
					 {
					 	$.ligerDialog.error("该消费单必须指定一个美容项目为新客推荐项目(大工类型)!");
					 	postState=0;
					 	return ;
					 }
					  if(document.getElementById("recommendempid").value=="" && newCustomerByTjCount*1!=0)
					 {
					 	$.ligerDialog.error("该消费有美容项目为推荐项目,必须输入介绍人!");
					 	postState=0;
					 	return ;
					 }
					 
					 if(ForDight(keepPayamt*1,0)>ForDight(document.getElementById("cscurkeepamt").value*1,0))
					 {
					 	$.ligerDialog.error("消耗账户金额不能超过现有账户余额!");
					 	postState=0;
					 	return ;
					 }
					 
					 if(ForDight(jifenPayamt*1,0)>ForDight(document.getElementById("cardpointamt").value*1,0))
					 {
					 	$.ligerDialog.error("消耗积分金额不能超过现有积分余额!");
					 	postState=0;
					 	return ;
					 }
					 if(ForDight(sendPayamt*1,0)>ForDight(document.getElementById("cardsendamt").value*1,0))
					 {
					 	$.ligerDialog.error("消耗赠送储值金额不能超过现有赠送余额!");
					 	postState=0;
					 	return ;
					 }
					 
						 	
					 if(ForDight(diyongPayamt*1,0)>ForDight(document.getElementById("diyongcardnoamt").value*1,0))
					 {
					 	$.ligerDialog.error("消耗抵用券金额不能超过现有抵用券额度!");
					 	postState=0;
					 	return ;
					 }
					 if(ForDight(document.getElementById("diyongcardnoamt").value*1,0)>0 && ForDight(diyongPayamt*1,0)==0)
					 {
					 	$.ligerDialog.error("没有使用抵用券金额,请重新确认!");
					 	postState=0;
					 	return ;
					 }
					 if(iscardcostflag==1)//有卡消费
					 {
					     	var CardControl=parent.document.getElementById("CardCtrl");
							CardControl.Init(parent.commtype,parent.prot,parent.password1,parent.password2,parent.password3);
							var factcardNo=CardControl.ReadCard();
							if(factcardNo!=document.getElementById("cscardno").value)
							{
								$.ligerDialog.error("读卡器卡号与系统显示卡号不一致,或卡不在读卡器内!");
					 			postState=0;
					 			return ;
							}
					 }
					 if(checkOtherPayFlag==1) //有储值参与分单支付
					 {
					 	if(ForDight(document.getElementById("cscurkeepamt").value*1,0)==0)
					 	{
					 		$.ligerDialog.error("现有储值账户无余额,不能参与储值分单支付!");
					 		postState=0;
					 		return ;
					 	}
					 	if(ForDight(document.getElementById("cscurkeepamt").value*1-keepPayamt*1,0)>0)
						 {
						 	$.ligerDialog.error("有储值分单支付,现有储值账户余额必须完全消费!");
						 	postState=0;
						 	return ;
						 }
					 }
					 if(strJsonParam_five!="")
					 {
					 	 params = params+"&strJsonParam=["+strJsonParam_five+"]";
					 }
					 if(paramtotiaomacardinfo!="")
					 {
					 	 params = params+"&paramtotiaomacardinfo=["+paramtotiaomacardinfo+"]";
					 }
					 
					 	vTCostAmt=ForDight(keepPayamt*1,1)*1+ForDight(document.getElementById("curCostAmt").value*1,1)*1;
    					vTMrCostCount=vmrcostcount*1+ForDight(document.getElementById("curMrCostCount").value*1,1)*1;
    					vTMfCostCount=vmfcostcount*1+ForDight(document.getElementById("curMfCostCount").value*1,1)*1;
    					vparams=params;
					 if(document.getElementById("paramSp104").value==1  && vTCostAmt>2500)
					 {
					 	$.ligerDialog.confirm("会员卡"+document.getElementById("cscardno").value+"储值账户今天已累计消费"+ForDight(document.getElementById("curCostAmt").value*1,1)+"元,加上本次消费"+ForDight(keepPayamt*1,1)+",已超出2500额定范围,若需保存请读取店长卡!", function (result)
					 	{
						    if( result==true)
				           	{
				           		
					 			postState=0;
					 			checkcustomerDialog=$.ligerDialog.open({ height: null, url: contextURL+'/CardControl/CC011/handShareStaffno.jsp', width: 300, showMax: false, showToggle: false, showMin: false, isResize: false, title: '店长审核' });
        
							}
							else
							{
								postState=0;
						 		return ;
							}
						});
					 }
					 else  if(document.getElementById("paramSp104").value==1  && vTMrCostCount>4)
					 {
					 	$.ligerDialog.confirm("会员卡"+document.getElementById("cscardno").value+"美容疗程今天已累计消费"+ForDight(document.getElementById("curMrCostCount").value*1,1)+"次,加上本次消费"+ForDight(vTMrCostCount*1,1)+",已超出4次额定范围,若需保存请读取店长卡!", function (result)
					 	{
						    if( result==true)
				           	{
				           		
					 			postState=0;
					 			checkcustomerDialog=$.ligerDialog.open({ height: null, url: contextURL+'/CardControl/CC011/handShareStaffno.jsp', width: 300, showMax: false, showToggle: false, showMin: false, isResize: false, title: '店长审核' });
        
							}
							else
							{
								postState=0;
						 		return ;
							}
						});
					 }
					  else  if(document.getElementById("paramSp104").value==1  && vTMfCostCount>4)
					 {
					 	$.ligerDialog.confirm("会员卡"+document.getElementById("cscardno").value+"美发疗程今天已累计消费"+ForDight(document.getElementById("curMfCostCount").value*1,1)+"次,加上本次消费"+ForDight(vTMfCostCount*1,1)+",已超出4次额定范围,若需保存请读取店长卡!", function (result)
					 	{
						    if( result==true)
				           	{
				           		
					 			postState=0;
					 			checkcustomerDialog=$.ligerDialog.open({ height: null, url: contextURL+'/CardControl/CC011/handShareStaffno.jsp', width: 300, showMax: false, showToggle: false, showMin: false, isResize: false, title: '店长审核' });
        
							}
							else
							{
								postState=0;
						 		return ;
							}
						});
					 }
					 else
					 {
					 	var requestUrl ="cc011/post.action";
						var responseMethod="editMessage";		
					 	showDialogmanager = $.ligerDialog.waitting('正在保存中,请稍候...');	
					 	sendRequestForParams_p(requestUrl,responseMethod,params ); 
					 }
   		}
    }
    
     function handInsertShareMessage(request)
     {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        	
	        		var requestUrl ="cc011/post.action";
					var responseMethod="editMessage";		
					showDialogmanager = $.ligerDialog.waitting('正在保存中,请稍候...');	
					sendRequestForParams_p(requestUrl,responseMethod,vparams ); 
	        		checkcustomerDialog.close();
	        	}
	        	else
	        	{
	        		$.ligerDialog.error(strMessage);
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
        }
        
    
     function editMessage(request)
     {
    		
        	try
			{
				showDialogmanager.close();
				postState=0;	
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	document.getElementById("csbillid").value=responsetext.curMconsumeinfo.id.csbillid;
	        	var sumKeepBalance=document.getElementById("sumKeepBalance").value;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		 $.ligerDialog.confirm('保存成功!是否需要打印', function (result)
					 {
					 	 
						  if( result==true)
				          {
				          	viewTicketReport();
				          	addConsumeInfo();
	        			  }
	        			  else
	        			  {
	        			  	 addConsumeInfo();
	        			  }
	        			  if(checkNull(responsetext.sumCostAmt)*1>0)
					 	  {
					 	  		if(sumKeepBalance*1-checkNull(responsetext.sumCostAmt)*1<=20)
					 	  		{
					 	  			var needHandCardNo=responsetext.curMconsumeinfo.cscardno;
					 	  			var strCurBillId=responsetext.curMconsumeinfo.id.csbillid;
						 	  		$.ligerDialog.confirm('会员卡:'+needHandCardNo+' 剩余金额少于20,是否需要回收该卡?', function (result)
						 	  		{
						 	  			 if( result==true)
				          				 {
						 	  				var requestUrl ="cc011/handBackReviceCard.action"; 
											var responseMethod="handBackReviceCardMessage";	
											var params="strCardNo="+needHandCardNo;		
											params=params+"&strCurBillId="+strCurBillId;		
											sendRequestForParams_p(requestUrl,responseMethod,params );
										}
						 	  		});
					 	  		}
					 	  }
	        		 });
	        		
	        	}
	        	else
	        	{
	        		$.ligerDialog.warn(strMessage);
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
      }
   
   	  function handBackReviceCardMessage(request)
      {
    		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	      
	        		$.ligerDialog.success("操作成功!");
				}
	        	else
	        	{
	        		$.ligerDialog.error(strMessage);
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
      }
      
   	//刷新界面
   	function freshCurRecord()
   	{
   		addConsumeInfo();
   	}
	//查询单据
	function searchCurRecord()
	{
		if(document.getElementById("strSearchBillId").value!="")
		{
			var requestUrl ="cc011/searchCurRecord.action"; 
			var responseMethod="searchCurRecordMessage";	
			var params="strSearchBillId="+document.getElementById("strSearchBillId").value;			
			sendRequestForParams_p(requestUrl,responseMethod,params );
		}
	}
	function searchCurRecordMessage(request)
     {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	commoninfodivdetial_Pro.options.data=$.extend(true, {},{Rows: null,Total:0});
	            commoninfodivdetial_Pro.loadData(true);  
	            addProRecord();
	        	var curMconsumeinfo=responsetext.curMconsumeinfo;
	        	if(curMconsumeinfo==null)
	        	{	        		 
	        		 $.ligerDialog.success("无相关数据!");
	        		commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
            		commoninfodivdetial.loadData(true);  
	        	}
	        	else
	        	{
	        		pageState=3;
	        		loadCurMaster(responsetext.curMconsumeinfo);
	        		if(responsetext.lsDconsumeinfos!=null && responsetext.lsDconsumeinfos.length>0)
			   		{
			   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: responsetext.lsDconsumeinfos,Total: responsetext.lsDconsumeinfos.length});
		            	commoninfodivdetial.loadData(true);            	
			   		}
			   		commoninfodivdetial.options.clickToEdit=false;
			   		//document.getElementById("dProjectAmt").innerHTML=ForDight(responsetext.DProjectAmt,1);
	        		//document.getElementById("dGoodsAmt").innerHTML=ForDight(responsetext.DGoodsAmt,1);
	        		//document.getElementById("dTotalsAmt").innerHTML=ForDight(responsetext.DProjectAmt*1,1)+ForDight(responsetext.DGoodsAmt*1,1);
			        handPayList();
			        
			        addRecordFlag=1;
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
      }
      
      function validateDiyongcardno(obj)
      {
      		if(obj.value=="")
	      	{
	      		document.getElementById("lbdyAmt").innerHTML=0;
	      		return ;
	      	}
	      	var requestUrl ="cc011/validateDiyongcardno.action"; 
			var responseMethod="validateDiyongcardnoMessage";	
			var params="strDiYongCardNo="+obj.value;			
			sendRequestForParams_p(requestUrl,responseMethod,params );
      }
      function validateDiyongcardnoMessage(request)
      {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		lsDnointernalcardinfo=responsetext.lsDnointernalcardinfo;
	        		if(checkNull(responsetext.strTMCardpassword)=="")  
	        		{		 
	        			if(lsDnointernalcardinfo!=null && lsDnointernalcardinfo.length>0)
		        		{
		        			document.getElementById("diyongcardnoamt").value=0;
		        			document.getElementById("lbdyAmt").innerHTML=0;
		        			$.ligerDialog.open({ url: contextURL+'/CardControl/CC011/showDiyongCardItems.jsp', title:'项目抵用券疗程明细',height: 500,width: 650, buttons: [ { text: '确定', onclick: function (item, dialog) { loadDiyongCardInfo(dialog); } }, { text: '取消', onclick: function (item, dialog) { paramtotiaomacardinfo="";dialog.close(); } } ] });
						}
						else
						{
							document.getElementById("diyongcardnoamt").value=ForDight(responsetext.DDiyongAmt,1);
							document.getElementById("lbdyAmt").innerHTML=ForDight(responsetext.DDiyongAmt,1);
						}
					}
					else
					{
						$.ligerDialog.prompt('请输入条抵用券密码','', function (yes,value) { if(yes) loadDYQCardPassword(value) });
					}
	        	}
	        	else
	        	{
	        		document.getElementById("diyongcardno").value="";
	        		document.getElementById("lbdyAmt").innerHTML=0;
	        		$.ligerDialog.warn(strMessage);
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
      }
      
      function loadDYQCardPassword(value)
      {
      		var requestUrl ="cc011/validateDyqCardPassword.action"; 
			var responseMethod="validateDYCardCardPasswordMessage";
			var params="strTiaoMaCardPassword="+value;
			var params=params+"&strTiaoMaCardNo="+document.getElementById("diyongcardno").value;
		
			sendRequestForParams_p(requestUrl,responseMethod,params );
				
      }
      
       function validateDYCardCardPasswordMessage(request)
      {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	      
	        		if(lsDnointernalcardinfo!=null && lsDnointernalcardinfo.length>0)
		        	{
		        			document.getElementById("diyongcardnoamt").value=0;
		        			document.getElementById("lbdyAmt").innerHTML=0;
		        			$.ligerDialog.open({ url: contextURL+'/CardControl/CC011/showDiyongCardItems.jsp', title:'项目抵用券疗程明细',height: 500,width: 650, buttons: [ { text: '确定', onclick: function (item, dialog) { loadDiyongCardInfo(dialog); } }, { text: '取消', onclick: function (item, dialog) { paramtotiaomacardinfo="";dialog.close(); } } ] });
					}
					else
					{
							document.getElementById("diyongcardnoamt").value=ForDight(responsetext.DDiyongAmt,1);
							document.getElementById("lbdyAmt").innerHTML=ForDight(responsetext.DDiyongAmt,1);
					}
	        	}
	        	else
	        	{
	        		$.ligerDialog.prompt('输入密码不正确,再次输入条码卡密码','', function (yes,value) { if(yes) loadDYQCardPassword(value) });
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
      }
      
      function validateTiaomacardno(obj)
      {	
      		if(obj.value=="")
	      	{
	      		return ;
	      	}
	      	var requestUrl ="cc011/validateTiaoMaCardNo.action"; 
			var responseMethod="validateTiaoMaCardNoMessage";	
			var params="strTiaoMaCardNo="+obj.value;			
			sendRequestForParams_p(requestUrl,responseMethod,params );
      }
      function validateTiaoMaCardNoMessage(request)
      {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	      
	        		lsDnointernalcardinfo=responsetext.lsDnointernalcardinfo;
	        		if(checkNull(responsetext.strTMCardpassword)=="")  
	        		{		 
	        			if(lsDnointernalcardinfo!=null)
	        			{
	        				$.ligerDialog.open({ url: contextURL+'/CardControl/CC011/showTiaoMaCardItems.jsp', title:'条码卡疗程明细',height: 500,width: 750, buttons: [ { text: '确定', onclick: function (item, dialog) { loadTiaoMaCardInfo(dialog); } }, { text: '取消', onclick: function (item, dialog) { paramtotiaomacardinfo="";dialog.close(); } } ] });
						}
					}
					else
					{
						$.ligerDialog.prompt('请输入条码卡密码','', function (yes,value) { if(yes) loadTMCardPassword(value) });
					}
					
	        	}
	        	else
	        	{
	        		lsDnointernalcardinfo=null;
	        		document.getElementById("tiaomacardno").value="";
	        		$.ligerDialog.warn(strMessage);
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
      }
      
      function loadTMCardPassword(value)
      {
      		var requestUrl ="cc011/validateTMCardPassword.action"; 
			var responseMethod="validateTMCardPasswordMessage";
			var params="strTiaoMaCardPassword="+value;
			var params=params+"&strTiaoMaCardNo="+document.getElementById("tiaomacardno").value;
		
			sendRequestForParams_p(requestUrl,responseMethod,params );
				
      }
      
       function validateTMCardPasswordMessage(request)
      {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	      
	        		if(lsDnointernalcardinfo!=null)
	        		{
	        			$.ligerDialog.open({ url: contextURL+'/CardControl/CC011/showTiaoMaCardItems.jsp', title:'条码卡疗程明细',height: 500,width: 750, buttons: [ { text: '确定', onclick: function (item, dialog) { loadTiaoMaCardInfo(dialog); } }, { text: '取消', onclick: function (item, dialog) { paramtotiaomacardinfo="";dialog.close(); } } ] });
					}
	        	}
	        	else
	        	{
	        		$.ligerDialog.prompt('输入密码不正确,再次输入条码卡密码','', function (yes,value) { if(yes) loadTMCardPassword(value) });
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
      }
      
      
        function loadDiyongCardInfo(dialog)
        {
        	document.getElementById("diyongcardno").readOnly="readOnly";
        	var row=commoninfodivfouth.getSelected();
        	if(row!=null)
        	{	
        		var lastCount=row.lastcount;
            	var lastAmt=row.lastamt;
            	var curprice=ForDight(lastAmt/lastCount,1)
        		var detiallength=commoninfodivdetial.rows.length*1;
        		if(detiallength==0)
				{
					addRecord();
					//detiallength=detiallength+1;
				}
				else if( commoninfodivdetial.getRow(0)['csitemname']!="")
				{
					addRecord();
					//detiallength=detiallength+1;
				}
        		//curRecord=commoninfodivdetial.getRow(detiallength-1);
				commoninfodivdetial.updateRow(curRecord,{bcsinfotype:1,csitemname: row.ineritemname,csitemno: row.ineritemno,csitemcount:ForDight(lastCount,1),
     				csunitprice:curprice,csdisprice :curprice,csitemamt: ForDight(lastAmt,0),csdiscount:1,cspaymode:'11',csfirstsaler:'',csfirsttype:'',csfirstshare:'',
     				cssecondsaler:'',cssecondtype:'',cssecondshare:'',csthirdsaler:'',csthirdtype:'',csthirdshare:'',csproseqno:0});  
   	 			
   	 		}
   	 		handPayList();
   	 		 document.getElementById("wCostItemNo").readOnly="readOnly";
		     document.getElementById("wCostItemNo").style.background="#EDF1F8";
		     document.getElementById("wCostItemBarNo").readOnly="readOnly";
		     document.getElementById("wCostItemBarNo").style.background="#EDF1F8";
		     document.getElementById("wgCostCount").readOnly="readOnly";
		     document.getElementById("wgCostCount").style.background="#EDF1F8"; 
		     document.getElementById("wCostPCount").readOnly="readOnly";
		    document.getElementById("wCostPCount").style.background="#EDF1F8";
		    dialog.close();
            initDetialGrid();
        }
    	function loadTiaoMaCardInfo(dialog)
    	{
    		document.getElementById("tiaomacardno").readOnly="readOnly";
    		paramtotiaomacardinfo="";
    		var curjosnparam ="";
			var needReplaceStr="";
			var rows = commoninfodivthirth.getCheckedRows();
            var str = "";
            var lastCount=0;
			var lastAmt=0;
			var costCount=0;
			var curprice = 0;
			var curcostamt=0;
			var detiallength=commoninfodivdetial.rows.length*1
			if(commoninfodivthirth.rows.length*1>0 )
			{
				   commoninfodivthirth.endEdit();
			}
            $(rows).each(function ()
            {
            	lastCount=this.lastcount;
            	lastAmt=this.lastamt;
            	costcount=this.costcount;
            	if(costcount*1>lastCount*1)
            	{
            		$.ligerDialog.warn("取用疗程数量不能超过该疗程的剩余数量！");
            		paramtotiaomacardinfo="";
            		document.getElementById("tiaomacardno").value="";
            		document.getElementById("tiaomacardno").readOnly="";
            		return;
            	}
            	
            	if(detiallength==0)
				{
						addRecord();
						//detiallength=detiallength+1;
				}
				else if( commoninfodivdetial.getRow(0)['csitemname']!="")
				{
						addRecord();
						//detiallength=detiallength+1;
				}
				curprice=ForDight(lastAmt/lastCount,1)
				curcostamt=ForDight(curprice*1*costcount*1,1);
				commoninfodivthirth.updateRow(this,{costamt:ForDight(curcostamt*1,1)});
				//curRecord=commoninfodivdetial.getRow(detiallength-1);
				commoninfodivdetial.updateRow(curRecord,{bcsinfotype:1,csitemname: this.ineritemname,csitemno:this.ineritemno,csitemcount:ForDight(costcount,1),
     				csunitprice:curprice,csdisprice :curprice,csitemamt:ForDight(curcostamt,0),csdiscount:1,cspaymode:'13',csfirstsaler:'',csfirsttype:'',csfirstshare:'',
     				cssecondsaler:'',cssecondtype:'',cssecondshare:'',csthirdsaler:'',csthirdtype:'',csthirdshare:'',csproseqno:0});  
   	 				
   	 			curjosnparam=JSON.stringify(this);
               /* if(curjosnparam.indexOf("_id")>-1)
				{
					needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
					curjosnparam=curjosnparam.replace(needReplaceStr,"");
				}*/
				 if(paramtotiaomacardinfo!="")
					paramtotiaomacardinfo=paramtotiaomacardinfo+",";
				paramtotiaomacardinfo= paramtotiaomacardinfo+curjosnparam; 	 
            });
            	handPayList();
            	document.getElementById("wCostItemNo").readOnly="readOnly";
		     	document.getElementById("wCostItemNo").style.background="#EDF1F8";
	     	 	document.getElementById("wCostItemBarNo").readOnly="readOnly";
		      	document.getElementById("wCostItemBarNo").style.background="#EDF1F8";
		      	document.getElementById("wgCostCount").readOnly="readOnly";
		      	document.getElementById("wgCostCount").style.background="#EDF1F8"; 
		     	document.getElementById("wCostPCount").readOnly="readOnly";
		     	document.getElementById("wCostPCount").style.background="#EDF1F8";
            	dialog.close();
            	initDetialGrid();
    	}
      function validateTuangoucardno(obj)
      {
	      	if(obj.value=="")
	      	{
	      		return ;
	      	}
	      	var requestUrl ="cc011/validateTuanGouCardNo.action"; 
			var responseMethod="validateTuanGouCardNoMessage";	
			var params="strTuanGouCardNo="+obj.value;			
			sendRequestForParams_p(requestUrl,responseMethod,params );
      }
     function validateTuanGouCardNoMessage(request)
     {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		var curCorpsbuyinfo=responsetext.curCorpsbuyinfo;
	        		commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
            		commoninfodivdetial.loadData(true);  
            		addRecord();
            		curRecord=commoninfodivdetial.getRow(0);
            		commoninfodivdetial.updateRow(curRecord,{bcsinfotype:1,csitemname:curCorpsbuyinfo.corpspicname,csitemno: curCorpsbuyinfo.corpspicno,csitemcount:1,
     				csunitprice:curCorpsbuyinfo.corpsamt,csdisprice :curCorpsbuyinfo.corpsamt,csitemamt:ForDight(curCorpsbuyinfo.corpsamt,0),csdiscount:1,cspaymode:16,csproseqno:0});
	        		document.getElementById("tuangoucardno").readOnly="readOnly";
	        	
	        		var column=commoninfodivdetial.columns[0];
					commoninfodivdetial.setCellEditing(curRecord, column, true);
					column=commoninfodivdetial.columns[1];
					commoninfodivdetial.setCellEditing(curRecord, column, true);
					column=commoninfodivdetial.columns[2];
					commoninfodivdetial.setCellEditing(curRecord, column, true);
					column=commoninfodivdetial.columns[5];
					commoninfodivdetial.setCellEditing(curRecord, column, true);
					column=commoninfodivdetial.columns[6];
					commoninfodivdetial.setCellEditing(curRecord, column, true);
					commoninfodivdetial_Pro.options.clickToEdit=false;
					addRecordFlag=1;
					handPayList();
	        	}
	        	else
	        	{
	        		document.getElementById("tuangoucardno").value="";
	        		$.ligerDialog.warn(strMessage);
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
      }
      
      	//打印小票
      	function viewTicketReport()
		{
			var ticketId=document.getElementById("csbillid").value;
			var shopId=document.getElementById("cscompid").value;
			if(ticketId == "" || ticketId == null||shopId==""||shopId==null)
			{
			    return;
			}
			try
			{
				document.getElementById("strSearchBillId").value = ticketId;
				var params = "ticketId=" + ticketId +"& shopId="+shopId +"& memberCardId="+document.getElementById("cscardno").value+" & billDate="+document.getElementById("csdate").value;
				var requestUrl ="cc011/viewTicketReport.action";
		    	var responseMethod = "viewTicketReportMessage";
		    	sendRequestForParams_p(requestUrl,responseMethod,params ); 
			}
			catch(e)
			{
				alert(e.message)
			}
		}
		
		function viewTicketReportMessage( request )
		{
			try
			{
				var responsetext = eval("(" + request.responseText + ")");
				Stand_CheckPrintControl();//检查是否有打印控件
				Stand_InitPrint("收银模块_小票打印作业");
				Stand_SetPrintStyle("FontName","新宋体");
				Stand_SetPrintStyle("FontSize",11);
				Stand_SetPrintStyle("Alignment",2);
				Stand_SetPrintStyle("HOrient",2);
				Stand_SetPrintStyle("Bold",1);
				Stand_AddPrintContent(StandPRINT_CONTENT_TYPE_TEXT,31,15,351,25,"阿玛尼护肤造型"+"("+checkNull(responsetext.companName)+")");
				Stand_SetPrintStyle("FontSize",9);
				Stand_AddPrintContent(StandPRINT_CONTENT_TYPE_TEXT,55,15,351,25,"一切为你 想你所想");
				Stand_SetPrintStyle("FontSize",11);
				Stand_SetPrintStyle("Bold",0);
				var index=151;
				document.getElementById("billDat").innerHTML=checkNull(responsetext.billDate); 
				if(checkNull(responsetext.memberCardId).indexOf("散客")==-1)
					//document.getElementById("memberCardId").innerHTML="***"+checkNull(responsetext.memberCardId).substring(checkNull(responsetext.memberCardId).length-3,checkNull(responsetext.memberCardId).length);
					document.getElementById("memberCardId").innerHTML=checkNull(responsetext.memberCardId);
				else
					document.getElementById("memberCardId").innerHTML="现金";
				document.getElementById("billnop").innerHTML=checkNull(responsetext.ticketId).substring(checkNull(responsetext.ticketId).length-3,checkNull(responsetext.ticketId).length);
				clearPreviousResult();
				var table=document.getElementById("info");
				var texts=document.getElementById("texts");
				var text2=document.getElementById("text2");
				var text3=document.getElementById("text3");
				var text4=document.getElementById("text4");
				var text5=document.getElementById("text5");
				var bRet=false;
				var amt =0;
				var num=0;
				var sumNum=0;
				
				var tr=document.createElement("tr");
				var tr1=document.createElement("tr");
				var ttd1=document.createElement("td");
				var ttd2=document.createElement("td");
				var ttd3=document.createElement("td");
				var ttd4=document.createElement("td");
				ttd1.innerHTML="项目名称";
				ttd2.innerHTML="数量";
				ttd3.innerHTML="金额";
				ttd4.innerHTML="大工";
				ttd1.align="left";
				ttd2.align="left";
				ttd3.align="left";
				ttd4.align="left";
				tr.appendChild(ttd1);
				tr.appendChild(ttd2);
				tr.appendChild(ttd3);
				tr.appendChild(ttd4);
				table.appendChild(tr);
						
				if(responsetext.costListProj != null)
				{
					for(var i=0;i<responsetext.costListProj.length;i++)
					{
						var tr=document.createElement("tr");
						var tr1=document.createElement("tr");
						var td=document.createElement("td");
						td.setAttribute("colSpan",4);
						td.innerHTML=checkNull(responsetext.costListProj[i].projectName);
						tr.appendChild(td);
						table.appendChild(tr);
						var td1=document.createElement("td");
						var td3=document.createElement("td");
						var td4=document.createElement("td");
						var td2=document.createElement("td");
						td1.innerHTML="";
					
						td3.innerHTML=checkNull(responsetext.costListProj[i].costNumber);
						num=num*1+checkNull(responsetext.costListProj[i].costNumber)*1;
						if(checkNull(responsetext.costListProj[i].paymode)!="9")
						{
							td4.innerHTML=checkNull(responsetext.costListProj[i].costMoney);
							amt=amt*1+checkNull(responsetext.costListProj[i].costMoney)*1;
						}
						else
						{
							td4.innerHTML="次";
							if(responsetext.lsPrintCardproaccount != null && responsetext.lsPrintCardproaccount.length>0)
							{
								for(var j=0;j<responsetext.lsPrintCardproaccount.length;j++)
								{
									if((checkNull(responsetext.costListProj[i].projectNo)==checkNull(responsetext.lsPrintCardproaccount[j].bprojectno))
									&&(checkNull(responsetext.costListProj[i].csproseqno)==0 || checkNull(responsetext.costListProj[i].csproseqno)==checkNull(responsetext.lsPrintCardproaccount[j].bproseqno)))
						    		{
						    			td4.innerHTML=td4.innerHTML+"(余"+maskAmt(responsetext.lsPrintCardproaccount[j].lastcount,0)+"次)";
						    		}
								}
							}
							else
							{
								  td4.innerHTML=td4.innerHTML+"(余0次)";
							}
						}
						td2.innerHTML=checkNull(responsetext.costListProj[i].strFristStaffNo);
						tr1.appendChild(td1);
						tr1.appendChild(td3);
						tr1.appendChild(td4);
						tr1.appendChild(td2);
						table.appendChild(tr1);
						bRet=true;
						
					}
				}
				if(responsetext.costListProd != null)
				{
					for(var i=0;i<responsetext.costListProd.length;i++)
					{
						var tr=document.createElement("tr");
						var tr1=document.createElement("tr");
						var td=document.createElement("td");
						td.setAttribute("colSpan",4);
						td.innerHTML=checkNull(responsetext.costListProd[i].projectName);
						tr.appendChild(td);
						table.appendChild(tr);
						var td1=document.createElement("td");
						var td3=document.createElement("td");
						var td4=document.createElement("td");
						var td2=document.createElement("td");
						td1.innerHTML="";
						td2.innerHTML="";
						td3.innerHTML=checkNull(responsetext.costListProd[i].costNumber);
						num=num*1+checkNull(responsetext.costListProd[i].costNumber)*1;
						amt=amt*1+checkNull(responsetext.costListProd[i].costMoney)*1;
						td4.innerHTML=checkNull(responsetext.costListProd[i].costMoney);
						td2.innerHTML=checkNull(responsetext.costListProd[i].strFristStaffNo);
						tr1.appendChild(td1);
						tr1.appendChild(td2);
						tr1.appendChild(td3);
						tr1.appendChild(td4);
						table.appendChild(tr1);
						bRet=true;
					}
				}
				if(bRet)
				{
					var tr1=document.createElement("tr");
					var td1=document.createElement("td");
					var td2=document.createElement("td");
					var td3=document.createElement("td");
					td1.innerHTML="合计";
					td2.innerHTML=maskAmt(num,1);
					td3.innerHTML=maskAmt(amt,1);
					tr1.appendChild(td1);
					tr1.appendChild(td2);
					tr1.appendChild(td3);
					table.appendChild(tr1);
					
				}
				var strbank="";
				var pay4flag=0;
				var pay17flag=0;
				var pay7flag=0;
				var payAflag=0;
				bRet=false;
				if(responsetext.payTypeList != null)
				{
					for(var i=0;i<responsetext.payTypeList.length;i++)
					{
						strbank="";
						var tr=document.createElement("tr");
						var td1=document.createElement("td");
						var td2=document.createElement("td");
						td1.setAttribute("colSpan",2);
						td2.setAttribute("colSpan",2);
						if(checkNull(responsetext.payTypeList[i].paymodename).length==2)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else if(checkNull(responsetext.payTypeList[i].paymodename).length==3)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else if(checkNull(responsetext.payTypeList[i].paymodename).length==4)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else if(checkNull(responsetext.payTypeList[i].paymodename).length==5)
						{
							strbank="&nbsp;&nbsp;";
						}
						
						if(checkNull(responsetext.payTypeList[i].paymode)=="4"
						|| checkNull(responsetext.payTypeList[i].paymode)=="7"
						|| checkNull(responsetext.payTypeList[i].paymode)=="A"
						|| checkNull(responsetext.payTypeList[i].paymode)=="17" )
						{
							if(responsetext.lsPrintCardaccount != null && responsetext.lsPrintCardaccount.length>0)
							{
								for(var j=0;j<responsetext.lsPrintCardaccount.length;j++)
								{
									if((checkNull(responsetext.payTypeList[i].paymode)=="4"
										&& checkNull(responsetext.lsPrintCardaccount[j].accounttype)==2)
									||
										(checkNull(responsetext.payTypeList[i].paymode)=="7"
										&& checkNull(responsetext.lsPrintCardaccount[j].accounttype)==3)
									||
										(checkNull(responsetext.payTypeList[i].paymode)=="A"
										&& checkNull(responsetext.lsPrintCardaccount[j].accounttype)==5)
									||
										(checkNull(responsetext.payTypeList[i].paymode)=="17"
										&& checkNull(responsetext.lsPrintCardaccount[j].accounttype)==6)
									)
									{
											td1.innerHTML=checkNull(responsetext.lsPrintCardaccount[j].accounttypeText)+strbank+" &nbsp;&nbsp; "+maskAmt(responsetext.payTypeList[i].payamt,1);
											td2.innerHTML="&nbsp;"+" 余:&nbsp;&nbsp; "+maskAmt(responsetext.lsPrintCardaccount[j].accountbalance,1);
											if(checkNull(responsetext.payTypeList[i].paymode)=="4")
											{
												pay4flag=1;
											}
											if(checkNull(responsetext.payTypeList[i].paymode)=="7")
											{
												pay7flag=1;
											}
											if(checkNull(responsetext.payTypeList[i].paymode)=="A")
											{
												payAflag=1;
											}
											if(checkNull(responsetext.payTypeList[i].paymode)=="17")
											{
												pay17flag=1;
											}
									}
								}
							}
						}
						else
						{
							td1.innerHTML=checkNull(responsetext.payTypeList[i].paymodename)+strbank+"&nbsp;&nbsp; "+maskAmt(responsetext.payTypeList[i].payamt,1);
							td2.innerHTML="&nbsp; "+"余:&nbsp;&nbsp; 0";
						}
						tr.appendChild(td1);
						tr.appendChild(td2);
						text2.appendChild(tr);
						bRet=true;
					}
				
				}
				if(responsetext.lsPrintCardaccount != null && responsetext.lsPrintCardaccount.length>0)
				{
					for(var j=0;j<responsetext.lsPrintCardaccount.length;j++)
					{
						if(checkNull(responsetext.lsPrintCardaccount[j].accounttypeText).length==2)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else if(checkNull(responsetext.lsPrintCardaccount[j].accounttypeText).length==3)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else if(checkNull(responsetext.lsPrintCardaccount[j].accounttypeText).length==4)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else if(checkNull(responsetext.lsPrintCardaccount[j].accounttypeText).length==5)
						{
							strbank="&nbsp;&nbsp;";
						}
						if(
						   	(
								(pay4flag==0 && checkNull(responsetext.lsPrintCardaccount[j].accounttype)==2)
							||  (pay7flag==0 && checkNull(responsetext.lsPrintCardaccount[j].accounttype)==3)
							||  (payAflag==0 && checkNull(responsetext.lsPrintCardaccount[j].accounttype)==5)
							||  (pay17flag==0 && checkNull(responsetext.lsPrintCardaccount[j].accounttype)==6)
							)
						    && checkNull(responsetext.lsPrintCardaccount[j].accountbalance)*1>0)
						{
							var tr=document.createElement("tr");
							var td1=document.createElement("td");
							var td2=document.createElement("td");
							td1.setAttribute("colSpan",2);
							td2.setAttribute("colSpan",2);
							td1.innerHTML=checkNull(responsetext.lsPrintCardaccount[j].accounttypeText)+strbank+"&nbsp;&nbsp; 0";
							td2.innerHTML="&nbsp;"+" 余:&nbsp;&nbsp; "+maskAmt(responsetext.lsPrintCardaccount[j].accountbalance,1);
							tr.appendChild(td1);
							tr.appendChild(td2);
							text2.appendChild(tr);
						}
					}
				}
				
				
				/*bRet=false;
				if(responsetext.lsPrintCardaccount != null && responsetext.lsPrintCardaccount.length>0)
				{
					for(var i=0;i<responsetext.lsPrintCardaccount.length;i++)
					{
						strbank="";
						var tr=document.createElement("tr");
						var td=document.createElement("td");
						td.setAttribute("colSpan",4);
						if(checkNull(responsetext.lsPrintCardaccount[i].accounttypeText).length==2)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else if(checkNull(responsetext.lsPrintCardaccount[i].accounttypeText).length==3)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else if(checkNull(responsetext.lsPrintCardaccount[i].accounttypeText).length==4)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else if(checkNull(responsetext.lsPrintCardaccount[i].accounttypeText).length==5)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						td.innerHTML=checkNull(responsetext.lsPrintCardaccount[i].accounttypeText)+strbank+"余额"+maskAmt(responsetext.lsPrintCardaccount[i].accountbalance,1);
						tr.appendChild(td);
						text3.appendChild(tr);
						bRet=true;
					}
				
				}*/
				var costprj="";
				var costsqno=0;
				bRet=false;
				/*if(responsetext.lsPrintCardproaccount != null 
				&& responsetext.lsPrintCardproaccount.length>0
				&& responsetext.costListProj != null 
				&& responsetext.costListProj.length>0)
				{
					for(var i=0;i<responsetext.lsPrintCardproaccount.length;i++)
					{
						
						for(var j=0;j<responsetext.costListProj.length;j++)
						{
							if(costprj==checkNull(responsetext.costListProj[j].projectName) 
							&& costsqno==checkNull(responsetext.costListProj[j].csproseqno))
							{
								continue;
							}
							if(checkNull(responsetext.costListProj[j].paymode)=="9"
								&&(checkNull(responsetext.costListProj[j].projectNo)==checkNull(responsetext.lsPrintCardproaccount[i].bprojectno))
								&&(checkNull(responsetext.costListProj[j].csproseqno)==0 || checkNull(responsetext.costListProj[j].csproseqno)==checkNull(responsetext.lsPrintCardproaccount[i].bproseqno)))
						    	{
									var tr=document.createElement("tr");
									var td=document.createElement("td");
									var td1=document.createElement("td");
									td.setAttribute("colSpan",2);
									td1.setAttribute("colSpan",2);
									td.innerHTML=checkNull(responsetext.lsPrintCardproaccount[i].bprojectname);
									td1.innerHTML="剩余"+maskAmt(responsetext.lsPrintCardproaccount[i].lastcount,0)+"次";
									tr.appendChild(td);
									tr.appendChild(td1);
									text4.appendChild(tr);
									bRet=true;
									costprj=checkNull(responsetext.costListProj[j].projectName);
									costsqno=checkNull(responsetext.costListProj[j].csproseqno);
								}
								
						}
						
					}
					if(bRet)
					{
						//addHr(text4,4);
					}
				}*/
				
				
				
				var tr3=document.createElement("tr");
				var td3=document.createElement("td");
				td3.innerHTML="操作人: &nbsp;&nbsp; "+checkNull(responsetext.cashMemberName);
				tr3.appendChild(td3);
				text5.appendChild(tr3);
				
				var tr4=document.createElement("tr");
				var td4=document.createElement("td");
				td4.innerHTML="打印日期: &nbsp;&nbsp; "+checkNull(responsetext.printDate);
				tr4.appendChild(td3);
				text5.appendChild(tr4);
				
				
				var tr5=document.createElement("tr");
				var td5=document.createElement("td");
				//td5.setAttribute("rowSpan",3);
				td5.innerHTML="贵宾签名:";
				tr5.appendChild(td5);
				text5.appendChild(tr5);
				
				
				var tr10=document.createElement("tr");
				var td10=document.createElement("td");
				//td10.setAttribute("",3);
				td10.innerHTML="&nbsp;";
				tr10.appendChild(td10);
				text5.appendChild(tr10);
				
		
				
				
				
				
				var tr8=document.createElement("tr");
				var td8=document.createElement("td");
				td8.setAttribute("align","center");
				td8.innerHTML=checkNull(responsetext.companAddr)+"["+checkNull(responsetext.companTel)+"]";
				tr8.appendChild(td8);
				text5.appendChild(tr8);
				
				var tr7=document.createElement("tr");
				var td7=document.createElement("td");
				td7.setAttribute("align","center");
				td7.innerHTML="www.chinamani.com";
				tr7.appendChild(td7);
				text5.appendChild(tr7);
				
				var tr9=document.createElement("tr");
				var td9=document.createElement("td");
				td9.setAttribute("align","center");
				td9.innerHTML="4006622818";
				tr9.appendChild(td9);
				text5.appendChild(tr9);
				var printContent = document.getElementById("printContent").innerHTML;
				Stand_AddPrintContent(StandPRINT_CONTENT_TYPE_HTML,68,0,230,800,printContent);
				
				Stand_Print();
			}
			catch(e)
			{
				alert(e.message);
			}
		}
		

		//清楚打印内容
		function clearPreviousResult()
		{
			var tblPrjs = document.getElementById("info");
			while(tblPrjs.childNodes.length>0)
			{
				tblPrjs.removeChild(tblPrjs.childNodes[0]);
			}
			var tblPrjs = document.getElementById("text2");
			while(tblPrjs.childNodes.length>0)
			{
				tblPrjs.removeChild(tblPrjs.childNodes[0]);
			}
			var tblPrjsb = document.getElementById("text3");
			while(tblPrjsb.childNodes.length>0)
			{
				tblPrjsb.removeChild(tblPrjsb.childNodes[0]);
			}
			var tblPrjsc = document.getElementById("text4");
			while(tblPrjsc.childNodes.length>0)
			{
				tblPrjsc.removeChild(tblPrjsc.childNodes[0]);
			}
			var tblPrjsc = document.getElementById("text5");
			while(tblPrjsc.childNodes.length>0)
			{
				tblPrjsc.removeChild(tblPrjsc.childNodes[0]);
			}
		}
		//增加行
	    function addHr(obj,num)
		{
			var tr=document.createElement("tr");
			var td=document.createElement("td");
			td.setAttribute("colSpan",num);
			td.innerHTML="<div style='border-bottom: 1px dashed #000000; width: 100%;'>"
			tr.appendChild(td);
			obj.appendChild(tr);
		}
	      //---------------------------------------------回车事件
		function hotKeyOfSelf(key)
		{
			if(key==13)//回车
			{
				hotKeyEnter();
			}
			else if(key==114 )//F3
			{
			
				addRecord();
				window.event.keyCode = 505;
				window.event.returnValue=false;
			}
			else if(key==115)//F4
			{
				if(checkNull(curRecord.csitemcount)*1<1 && checkNull(curRecord.csitemcount)*1!=0)
				{
					$.ligerDialog.error("分单支付明细不允许删除!");
					return;
				}
				deleteCurRecord();
			}
			else if( key == 83 &&  event.altKey)
			{
				editCurRecord();
			}
			else if( key == 69 &&  event.altKey)
			{
				editCurDetailInfo();
			}
			else if(key==107 )//+号键
			{
				addRecord();
				event.returnValue=false;
			}
			else if(key==109 )//-号键
			{
				if(checkNull(curRecord.csitemcount)*1<1 && checkNull(curRecord.csitemcount)*1!=0)
				{
					$.ligerDialog.error("分单支付明细不允许删除!");
					return;
				}
				 deleteCurRecord();
				event.returnValue=false;
			}
		}
		
		function  hotKeyEnter()
		{
		
			try
			{
					var fieldName = document.activeElement.name;
					var fieldId = document.activeElement.id ;
					if(fieldId=="cscardno" && document.getElementById("cscardno").value=="散客")
					{
						document.forms["consumCenterForm"].elements["csname"].select();
					}
					else if(fieldId=="cscardno" && document.getElementById("cscardno").value!="散客")
					{
						document.forms["consumCenterForm"].elements["csmanualno"].select();
					}
					else if(fieldId=="csname")
					{
						document.forms["consumCenterForm"].elements["csmanualno"].select();
					}
					else if(fieldId=="csmanualno")
					{
						document.forms["consumCenterForm"].elements["tuangoucardno"].select();
					}
					else if(fieldId=="tuangoucardno")
					{
						document.forms["consumCenterForm"].elements["tiaomacardno"].select();
					}
					else if(fieldId=="wCostItemNo")
					{
						//onchange="validateProjectNo(this);"
						validateProjectNo(document.activeElement);
						//document.activeElement.onchange();
					}
					else if(fieldId=="wCostItemBarNo")
					{
						//onchange="validateProjectNo(this);"
						validateGoodsNo(document.activeElement);
						//document.activeElement.onchange();
					}
					else if(fieldId=="wCostPCount")
					{
					    if(document.getElementById("itempay").disabled==false)
							document.getElementById("itempay").focus();
						else
						{
							document.getElementById("wCostFirstEmpNo").focus();
							document.getElementById("wCostFirstEmpNo").select();
						}
					}
					else if(fieldId=="wCostFirstEmptype")
					{
						document.getElementById("wCostSecondEmpNo").focus();
						document.getElementById("wCostSecondEmpNo").select();
					}
					else if(fieldId=="wCostSecondEmptype")
					{
						document.getElementById("wCostthirthdEmpNo").focus();
						document.getElementById("wCostthirthdEmpNo").select();
					}
					else
					{
						window.event.keyCode=9; //tab
						window.event.returnValue=true;
					}
					if(curEmpManger!=null)
					{
						curEmpManger.selectBox.hide();
					}
					if(curitemManger!=null)
					{
						curitemManger.selectBox.hide();
					}
				
			}
			catch(e){alert(e.message);}
				
		}  
	
		function foucsItemNo()
		{
			//var column=commoninfodivdetial.columns[0];
			//var cell = commoninfodivdetial.getCellObj(curRecord, column);
		}
		
		function validateProjectNo(obj)
		{
				if(obj.value=="")
				{
					//document.getElementById("wCostPCount").readOnly="readOnly";
		    		//document.getElementById("wCostPCount").style.background="#EDF1F8";
		    		return;	
				}
				if(obj.value=="302" || obj.value=="303" || obj.value=="305" || obj.value=="306"
				|| obj.value=="321" || obj.value=="322" || obj.value=="323"  || obj.value=="324" 
				|| obj.value=="325" || obj.value=="326" || obj.value=="327" || obj.value=="328"
				|| obj.value=="329" || obj.value=="330" || obj.value=="331")
				{
					document.getElementById("diyongcardno").focus();
			 		document.getElementById("diyongcardno").select();
		     		$.ligerDialog.error("洗吹项目和洗剪吹项目不能在此输入,请确认!");
		     		obj.value="";
		    		return;	
				}
				if(obj.value.indexOf("*")>-1)
				{
					document.getElementById("itempay").disabled=true;
					document.getElementById("wCostPCount").readOnly="";
		    		document.getElementById("wCostPCount").style.background="#FFFFFF";
		    		/*for (var rowid in commoninfodivdetial.records)
					{
						var row =commoninfodivdetial.records[rowid]; 
						if(checkNull(row.csproseqno)==obj.value.replace("*",""))
						{
							$.ligerDialog.error("消费列表已存在当前消费的疗程序号,请确认!");	
	   	 					return ;	
						}
					}*/
		    		var curcostcount=1;
		    		var detiallength=commoninfodivdetial.rows.length*1;
		    		var seqnoflag=0
					for (var rowid in commoninfodivdetial_Pro.records)
					{
						var row =commoninfodivdetial_Pro.records[rowid]; 
						if(row.bproseqno!=obj.value.replace("*",""))
						{
							continue;
						}
						if(row.lastcount==0)
						{
							seqnoflag=2;
							break;
						}
						seqnoflag=1;
						if(detiallength==0)
						{
							addRecord();
							//detiallength=detiallength+1;
						}
						else if( commoninfodivdetial.getRow(0)['csitemname']!="")
						{
							addRecord();
							//detiallength=detiallength+1;
						}
						var curlastcount=ForDight(row.targetlastcount,0);
						var curlastamt=ForDight(row.lastamt,1);
						var curprice=ForDight(curlastamt/curlastcount,1)
						var curcostamt=ForDight(curprice*1*curcostcount*1,1);
						//curRecord=commoninfodivdetial.getRow(detiallength-1);
						var rowdata=commoninfodivdetial.updateRow(curRecord,{csitemname: row.bprojectname,csitemno:row.bprojectno,csitemcount:ForDight(curcostcount,1),
	     				csunitprice:curprice,csdisprice :curprice,csitemamt:ForDight(curcostamt,0),csdiscount:1,cspaymode:'9',csfirstsaler:'',csfirsttype:'',csfirstshare:'',
	     				cssecondsaler:'',cssecondtype:'',cssecondshare:'',csthirdsaler:'',csthirdtype:'',csthirdshare:'',csproseqno:row.bproseqno});  
	   	 				//疗程列表递减
	   	 				commoninfodivdetial_Pro.updateRow(row,{lastcount: ForDight(row.lastcount,0)*1-1});
	   	 				
	   	 				document.getElementById("wCostPCount").value=1;
	   	 				document.getElementById("wCostItemBarNo").readOnly="readOnly";
		     			document.getElementById("wCostItemBarNo").style.background="#EDF1F8";
		     			document.getElementById("wgCostCount").readOnly="readOnly";
		     			document.getElementById("wgCostCount").style.background="#EDF1F8";
		     			document.getElementById("wCostFirstEmpshare").readOnly="readOnly";
		     			document.getElementById("wCostFirstEmpshare").style.background="#EDF1F8";
		     			document.getElementById("wCostSecondEmpshare").readOnly="readOnly";
		     			document.getElementById("wCostSecondEmpshare").style.background="#EDF1F8";
		     			document.getElementById("wCostthirthEmpshare").readOnly="readOnly";
		     			document.getElementById("wCostthirthEmpshare").style.background="#EDF1F8";
		     			
		     			document.getElementById("wCostSecondEmpNo").readOnly="";
		     			document.getElementById("wCostSecondEmpNo").style.background="#FFFFFF";
		     			document.getElementById("wCostthirthdEmpNo").readOnly="";
		     			document.getElementById("wCostthirthdEmpNo").style.background="#FFFFFF";
		     			
		     			if(checkNull(row.bprojectno)!="" 
			 				&& document.getElementById("paramSp097").value=="1"
			 				&& (checkNull(row.bprojectno).indexOf("498")==0
			 				|| checkNull(row.bprojectno).indexOf("490")==0))
		     			{
		     				document.getElementById("wCostSecondEmpNo").readOnly="readOnly";
			     			document.getElementById("wCostSecondEmpNo").style.background="#EDF1F8";
			     			document.getElementById("wCostthirthdEmpNo").readOnly="readOnly";
			     			document.getElementById("wCostthirthdEmpNo").style.background="#EDF1F8";
		     			}
		     			break;
		     			/*if(parent.lsProjectinfo!=null && parent.lsProjectinfo.length>0)
						{
							for(var i=0;i<parent.lsProjectinfo.length;i++)
							{
								if(parent.lsProjectinfo[i].id.prjno==row.bprojectno )
								{
			     					if(document.getElementById("paramSp097").value=="1")
			     					{
			     						if(checkNull(parent.lsProjectinfo[i].prjtype)=="4")
			     						{
			     							document.getElementById("wCostSecondEmpNo").readOnly="readOnly";
			     							document.getElementById("wCostSecondEmpNo").style.background="#EDF1F8";
			     							document.getElementById("wCostthirthdEmpNo").readOnly="readOnly";
			     							document.getElementById("wCostthirthdEmpNo").style.background="#EDF1F8";
			     						}
			     					}
			     					break;
								}
							}
						}*/
	   	 			}
	   	 			if(seqnoflag==0)
	   	 			{
	   	 				$.ligerDialog.error("疗程序号在左上区域不存在,请确认!");	
	   	 				return ;		
	   	 			}
	   	 			if(seqnoflag==2)
	   	 			{
	   	 				$.ligerDialog.error("疗程序号对应的疗程已取完,请确认!");	
	   	 				return ;		
	   	 			}
	   	 			handPayList();
	   	 			document.getElementById("wCostPCount").focus();
			 		document.getElementById("wCostPCount").select();
				}
				else
				{
				
					document.getElementById("itempay").disabled=false;
					//document.getElementById("wCostPCount").readOnly="readOnly";
		    		//document.getElementById("wCostPCount").style.background="#EDF1F8";
		    		document.getElementById("wCostSecondEmpNo").readOnly="";
		     		document.getElementById("wCostSecondEmpNo").style.background="#FFFFFF";
		     		document.getElementById("wCostthirthdEmpNo").readOnly="";
		     		document.getElementById("wCostthirthdEmpNo").style.background="#FFFFFF";
		     		if(checkNull(obj.value)!="" 
			 		&& document.getElementById("paramSp097").value=="1"
			 		&& (checkNull(obj.value).indexOf("498")==0
			 		|| checkNull(obj.value).indexOf("490")==0))
				    {
				     	 document.getElementById("wCostSecondEmpNo").readOnly="readOnly";
					     document.getElementById("wCostSecondEmpNo").style.background="#EDF1F8";
					     document.getElementById("wCostthirthdEmpNo").readOnly="readOnly";
					     document.getElementById("wCostthirthdEmpNo").style.background="#EDF1F8";
				    }
					if(parent.lsProjectinfo!=null && parent.lsProjectinfo.length>0)
					{
						for(var i=0;i<parent.lsProjectinfo.length;i++)
						{
							if(parent.lsProjectinfo[i].id.prjno==obj.value )
							{
								if(checkNull(parent.lsProjectinfo[i].prjsaletype)==1)
								{
		     						$.ligerDialog.error("疗程项目只能在左上区域选择消费!");
		     						document.getElementById("diyongcardno").focus();
			 						document.getElementById("diyongcardno").select();
		     						document.getElementById("wCostItemNo").value="";
		     						document.getElementById("itemprice").value="";
		     						document.getElementById("itempay").value="";
		     						document.getElementById("wCostPCount").value="";
		     						return;
		     					}
		     					if(checkNull(parent.lsProjectinfo[i].useflag)==2)
								{
								  	$.ligerDialog.warn("该项目已经停止使用");
								  	document.getElementById("diyongcardno").focus();
			 						document.getElementById("diyongcardno").select();
		     						document.getElementById("wCostItemNo").value="";
		     						document.getElementById("itemprice").value="";
		     						document.getElementById("itempay").value="";
		     						document.getElementById("wCostPCount").value="";
		     						return;
								}
								
		     					/*if(document.getElementById("paramSp097").value=="1")
		     					{
		     						if(checkNull(parent.lsProjectinfo[i].prjtype)=="4")
		     						{
		     							document.getElementById("wCostSecondEmpNo").readOnly="readOnly";
		     							document.getElementById("wCostSecondEmpNo").style.background="#EDF1F8";
		     							document.getElementById("wCostthirthdEmpNo").readOnly="readOnly";
		     							document.getElementById("wCostthirthdEmpNo").style.background="#EDF1F8";
		     						}
		     					}*/
		     					break;
							}
						}
					}
					var requestUrl ="cc011/validateItem.action"; 
					var responseMethod="validateFastItemMessage";
					var params="itemType=1";
					var params=params+"&strCurItemId="+obj.value;	
					var params=params+"&strCardType="+document.getElementById("cscardtype").value;
					params=params+"&cardUseType="+document.getElementById("billflag").value;		
					sendRequestForParams_p(requestUrl,responseMethod,params );
				}
		}
		
		
		
		function validateFastItemMessage(request)
		{
			try
			{
		     	var responsetext = eval("(" + request.responseText + ")");
		     	var dCostProjectRate=ForDight(responsetext.DCostProjectRate,2);
		     	var curProjectinfo=responsetext.curProjectinfo;
		     	if(curProjectinfo==null)
		     	{
		     		document.getElementById("diyongcardno").focus();
			 		document.getElementById("diyongcardno").select();
		     		$.ligerDialog.error("输入的项目编码不存在!");
		     		document.getElementById("wCostItemNo").value="";
		     		document.getElementById("itemprice").value="";
		     		document.getElementById("itempay").value="";
		     		document.getElementById("wCostPCount").value="";
		     		
		     	}
		     	else if(checkNull(curProjectinfo.useflag)==2)
				{
					$.ligerDialog.warn("该项目已经停止使用");
					document.getElementById("diyongcardno").focus();
			 		document.getElementById("diyongcardno").select();
		     		document.getElementById("wCostItemNo").value="";
		     		document.getElementById("itemprice").value="";
		     		document.getElementById("itempay").value="";
		     		document.getElementById("wCostPCount").value="";
		     		return;
				}
		     	else
		     	{
		     		var dstandprice=ForDight(checkNull(responsetext.dstandprice),2)*1;   //标准价
		     		var donecountprice=ForDight(checkNull(responsetext.donecountprice),2)*1; //单次价
		     		var donepageprice=ForDight(checkNull(responsetext.donepageprice),2)*1; //散客价
		     		var dmemberprice=ForDight(checkNull(responsetext.dmemberprice),2)*1; //会员价
		     		var dcuxiaoprice=ForDight(checkNull(responsetext.dcuxiaoprice),2)*1; //促销价
		     	    var facttyprice=0;//实际促销价
		     		document.getElementById("wCostItemBarNo").readOnly="readOnly";
		     		document.getElementById("wCostItemBarNo").style.background="#EDF1F8";
		     		document.getElementById("wgCostCount").readOnly="readOnly";
		     		document.getElementById("wgCostCount").style.background="#EDF1F8";
		     		document.getElementById("wCostFirstEmpshare").readOnly="readOnly";
		     		document.getElementById("wCostFirstEmpshare").style.background="#EDF1F8";
		     		document.getElementById("wCostSecondEmpshare").readOnly="readOnly";
		     		document.getElementById("wCostSecondEmpshare").style.background="#EDF1F8";
		     		document.getElementById("wCostthirthEmpshare").readOnly="readOnly";
		     		document.getElementById("wCostthirthEmpshare").style.background="#EDF1F8";
		     		document.getElementById("itemprice").value=checkNull(curProjectinfo.saleprice);
		     		document.getElementById("wCostItemName").value=checkNull(curProjectinfo.prjname);
		     		document.getElementById("wCostPCount").value=1;
		     		if(document.getElementById("billflag").value=="0")
     				{
     					facttyprice=donepageprice;
     					document.getElementById("itemdiscount").value=1;
     					document.getElementById("itempay").value="1";
     				}
     				else if(document.getElementById("billflag").value=="1")
     				{
     					facttyprice=dmemberprice;
     					document.getElementById("itemdiscount").value=dCostProjectRate;
     					document.getElementById("itempay").value="4";
     				}
     				else
     				{
     					facttyprice=dmemberprice;
     					document.getElementById("itemdiscount").value=dCostProjectRate;
     					document.getElementById("itempay").value="A";
     				}
     				
     				
     				document.getElementById("wCostPCount").focus();
			 		document.getElementById("wCostPCount").select();
			 		
			 		var usePriceFlag=0;
			 		var csdisprice=dstandprice*1;
			 		var factcostprice=ForDight(dstandprice*1*dCostProjectRate*1,1);
		     		var vcostpricetype=0;
		     		if(dcuxiaoprice*1>0 )
		     		{
		     			factcostprice=dcuxiaoprice*1;
		     			if(dcuxiaoprice*1>0 && dstandprice*1>0 && dcuxiaoprice> ForDight(dstandprice*1*dCostProjectRate*1,1) )
		     			{
		     				factcostprice= ForDight(dstandprice*1*dCostProjectRate*1,1);
		     			}
		     			if(dcuxiaoprice*1>0 && dstandprice*1>0 && dcuxiaoprice<= ForDight(dstandprice*1*dCostProjectRate*1,1) )
		     			{	
		     				dCostProjectRate=1;
		     				factcostprice= ForDight(dcuxiaoprice*1,1);
		     				csdisprice=factcostprice;
		     			}
		     		}
		     		else
		     		{
		     			
		     			if(facttyprice*1>0 && donecountprice*1==0)
		     			{
		     				dCostProjectRate=1;
		     				factcostprice=facttyprice*1;
		     				vcostpricetype=1;
		     			}
		     			else if(facttyprice*1==0 && donecountprice*1>0)
		     			{
		     				dCostProjectRate=1;
		     				factcostprice=donecountprice*1;
		     			}
		     			else if(facttyprice*1>0 && donecountprice*1>0)
		     			{
		     				dCostProjectRate=1;
		     				usePriceFlag=1;
		     			}
		     		}
		     		if(usePriceFlag==0)
		     		{
				 		var curNewRecord=commoninfodivdetial.updateRow(curRecord,{bcsinfotype:1,
				 														 csitemno: curProjectinfo.id.prjno,
																		 csitemname: checkNull(curProjectinfo.prjname),
																		 csitemcount: 1,
		     															 csunitprice: checkNull(dstandprice)*1,
		     															 csdisprice: checkNull(csdisprice)*1,
		     															 csitemamt:  ForDight(factcostprice*1,0),
		     															 csdiscount: ForDight(dCostProjectRate,2),
		     															 cspaymode: document.getElementById("itempay").value,
		     															 csproseqno:0,shareflag:0,costpricetype:0,costpricetype: vcostpricetype*1});  
		  											 
			     		handPayList();
			     		if(checkNull(curProjectinfo.needhairflag)==1)
			     		{
			     				if(document.getElementById("paramSp098").value=="1" )
			     				{
			     					$.ligerDialog.confirmDBXJC('确认 [达标] 类型', function (result)
									{
										var factnedamt=ForDight(20*1*document.getElementById("dXjcDiscount").value*1,0);
								   	 	if( result==true)
						           		{
						           			commoninfodivdetial.updateRow(curNewRecord,{ csdisprice:  ForDight(checkNull(curNewRecord.csdisprice)*1-20,0),csitemamt:  ForDight(checkNull(curNewRecord.csitemamt)*1-factnedamt,0),shareflag:1 });  
											handFastLoad("321",2,factnedamt,2,1);
										}
										else
						           		{
											commoninfodivdetial.updateRow(curNewRecord,{csdisprice:  ForDight(checkNull(curNewRecord.csdisprice)*1-20,0), csitemamt:  ForDight(checkNull(curNewRecord.csitemamt)*1-factnedamt,0),shareflag:1 });  
											handFastLoad("321",2,factnedamt,2,2);
										}
									});
			     				}
			     				else
			     				{
			     					$.ligerDialog.confirmXJC('若需要洗头,请确认 [洗头] 类型,若不用直接关闭窗口', function (result)
									{
										var factnedamt=ForDight(20*1*document.getElementById("dXjcDiscount").value*1,0);
								   	 	if( result==true)
						           		{
						           			commoninfodivdetial.updateRow(curNewRecord,{ csdisprice:  ForDight(checkNull(curNewRecord.csdisprice)*1-20,0),csitemamt:  ForDight(checkNull(curNewRecord.csitemamt)*1-factnedamt,0),shareflag:1 });  
											handFastLoad("300",2,factnedamt,2,0);
										}
										else
						           		{
						           			commoninfodivdetial.updateRow(curNewRecord,{csdisprice:  ForDight(checkNull(curNewRecord.csdisprice)*1-20,0), csitemamt:  ForDight(checkNull(curNewRecord.csitemamt)*1-factnedamt,0),shareflag:1 });  
											handFastLoad("3002",2,factnedamt,2,0);
											
										}
									});
			     				}
								
			     		}
			     		
		     		}
		     		else
		     		{
		     			$.ligerDialog.confirmXPrice('请选择价格类型', function (result)
						{
								
						   	 	if( result==true)
				           		{	
				           			factcostprice=facttyprice*1;
				           			vcostpricetype=1;
								}
								else
				           		{
				           			factcostprice=donecountprice*1;
									
								}
								factcostprice=factcostprice
								var curNewRecord=commoninfodivdetial.updateRow(curRecord,{bcsinfotype:1,
				 														 csitemno: curProjectinfo.id.prjno,
																		 csitemname: checkNull(curProjectinfo.prjname),
																		 csitemcount: 1,
		     															 csunitprice: checkNull(dstandprice)*1,
		     															 csdisprice: checkNull(factcostprice)*1,
		     															 csitemamt:  ForDight(factcostprice*1,0),
		     															 csdiscount: ForDight(dCostProjectRate,1),
		     															 cspaymode: document.getElementById("itempay").value,
		     															 csproseqno:0,shareflag:0,costpricetype: vcostpricetype*1 });  
		  											 
			     				handPayList();
						}); 
		     		}
		     		if(checkNull(curProjectinfo.editflag)==1)
		     		{
		     			$.ligerDialog.prompt('输入实际消费金额','', function (yes,value) { if(yes) loadEditPrice(value) });
		     		}
		     		
		     	}
		     }catch(e){alert(e.message);}
		}
		
		function loadEditPrice(value)
		{
			 commoninfodivdetial.updateRow(curRecord,{csitemamt:ForDight(curRecord.csitemcount*1*value*1,0)});  
		  	 handPayList();
		}
		
		function validateGoodsNo(obj)
		{
				if(obj.value=="")
				{
				}
				else
				{
					
					var requestUrl ="cc011/validateItem.action"; 
					var responseMethod="validateFastGItemMessage";
					var params="itemType=2";
					params=params+"&strCurItemId="+obj.value;	
					params=params+"&strCardType="+document.getElementById("cscardtype").value;	
					params=params+"&cardUseType="+document.getElementById("billflag").value;
					sendRequestForParams_p(requestUrl,responseMethod,params );
				}
		}
		
		
		
		function validateFastGItemMessage(request)
		{
			try
			{
		     	var responsetext = eval("(" + request.responseText + ")");
		     	var dCostProjectRate=ForDight(responsetext.DCostProjectRate,2);
		     	var curGoodsinfo=responsetext.curGoodsinfo;
		     	if(curGoodsinfo==null)
		     	{
		     		document.getElementById("diyongcardno").focus();
			 		document.getElementById("diyongcardno").select();
		     		$.ligerDialog.error("输入的产品编码/条码不存在!");
		     		document.getElementById("wCostItemBarNo").value="";
		     		document.getElementById("itemprice").value="";
		     		document.getElementById("itempay").value="";
		     		document.getElementById("wgCostCount").value="";
		     	}
		     	else
		     	{
		     		document.getElementById("wCostItemNo").readOnly="readOnly";
		     		document.getElementById("wCostItemNo").style.background="#EDF1F8";
		     		document.getElementById("wCostPCount").readOnly="readOnly";
		     		document.getElementById("wCostPCount").style.background="#EDF1F8";
		     		document.getElementById("wCostFirstEmptype").disabled=true;
		     		document.getElementById("wCostSecondEmptype").disabled=true;
		     		document.getElementById("wCostthirthEmptype").disabled=true;
		     		document.getElementById("itemprice").value=checkNull(curGoodsinfo.storesalseprice);
		     		document.getElementById("wCostItemName").value=checkNull(curGoodsinfo.goodsname);
		     		document.getElementById("wCostCount").value=1;
		     		if(document.getElementById("billflag").value=="0")
     				{
     					document.getElementById("itemdiscount").value=1;
     					document.getElementById("itempay").value="1";
     				}
     				else if(document.getElementById("billflag").value=="1")
     				{
     					document.getElementById("itemdiscount").value=dCostProjectRate;
     					document.getElementById("itempay").value="4";
     				}
     				else
     				{
     					document.getElementById("itemdiscount").value=dCostProjectRate;
     					document.getElementById("itempay").value="A";
     				}
     				document.getElementById("wgCostCount").focus();
			 		document.getElementById("wgCostCount").select();
			 		commoninfodivdetial.updateRow(curRecord,{bcsinfotype:2,
			 														 csitemno: curGoodsinfo.id.goodsno,
			 														 goodsbarno:document.getElementById("wCostItemBarNo").value,
																	 csitemname: checkNull(curGoodsinfo.goodsname),
																	 csitemcount: 1,
	     															 csunitprice: checkNull(curGoodsinfo.storesalseprice),
	     															 csdisprice: checkNull(curGoodsinfo.storesalseprice),
	     															 csitemamt:  ForDight(checkNull(curGoodsinfo.storesalseprice)*1*dCostProjectRate*1,0),
	     															 csdiscount: ForDight(dCostProjectRate,2),
	     															 cspaymode: document.getElementById("itempay").value,
	     															 csproseqno:0,shareflag:0,costpricetype:0});  
	     															 
		     		handPayList();
		     	}
		     }catch(e){alert(e.message);}
		}
		
		function validateFastCostCount(obj)
		{
			if(checkNull(curRecord.csitemno)=="")
			{
				document.getElementById("diyongcardno").focus();
			 	document.getElementById("diyongcardno").select();
		     	$.ligerDialog.error("请先输入项目编码!");
		     	return ;
			}
			if(obj.value*1>=0)
			{
				if(checkNull(curRecord.cspaymode)=="9")
				{
					for (var rowid in commoninfodivdetial_Pro.records)
					{
						var row =commoninfodivdetial_Pro.records[rowid]; 
						if(row.bproseqno==curRecord.csproseqno )
						{
							var curCount=checkNull(curRecord.csitemcount*1);
							var disccount=obj.value*1-curCount*1;
							var curlastcount=checkNull(row.lastcount)*1;
							if(obj.value*1>curlastcount*1+curCount*1)
							{
								$.ligerDialog.error("消费的疗程次数不能超过剩余的疗程次数 !");
								obj.value=1;
								document.getElementById("wCostItemNo").focus();
								document.getElementById("wCostItemNo").select();
								return;
							}
							else
							{
								commoninfodivdetial_Pro.updateRow(row,{lastcount: curlastcount*1-disccount});
								break;
							}
						}
					}
				
				}
				var oldprice=ForDight(checkNull(curRecord.csitemamt)*1/checkNull(curRecord.csitemcount)*1,1);
				commoninfodivdetial.updateRow(curRecord, {csitemcount: obj.value,
				csitemamt:  ForDight(checkNull(oldprice)*1*obj.value*1,0)});
		  		handPayList();
		    }
		    document.getElementById("itempay").focus();
			document.getElementById("itempay").select();
		}
		
		function validateFastCostpay(obj)
		{
			var curItem=checkNull(curRecord.csitemname);
			if(obj.value!="" && curItem=="")
			{
				document.getElementById("diyongcardno").focus();
			 	document.getElementById("diyongcardno").select();
				$.ligerDialog.error("选择支付方式前请先确定消费项目 !");
				return ;
			}
			if( obj.value=="11" || obj.value=="13" || obj.value=="16")
			{
				//commoninfodivdetial.updateRow(curRecord,{cspaymode: ""});
				$.ligerDialog.error("该支付方式不能手动选择,请重新输入!");
				document.getElementById("wCostPCount").focus();
			 	document.getElementById("wCostPCount").select();
			 	handPayList();
			 	return ;
			}
			
			if( curRecord.cspaymode=="11" || curRecord.cspaymode=="13" || curRecord.cspaymode=="16" || curRecord.cspaymode=="9")
			{
				$.ligerDialog.error("该支付方式不能手动选择,请重新输入!");
				document.getElementById("wCostPCount").focus();
			 	document.getElementById("wCostPCount").select();
			 	handPayList();
			 	return ;
			}
			
			if(checkNull(curRecord.bcsinfotype)==2 && obj.value=="12" )
			{
				commoninfodivdetial.updateRow(curRecord,{cspaymode: "1"});
				obj.value=1;
				$.ligerDialog.error("产品不能实用现金抵用券,请重新输入!");
				handPayList();
			 	return ;
			}
			

			if(checkNull(curRecord.bcsinfotype)==2)
			{
				commoninfodivdetial.updateRow(curRecord,{cspaymode: document.getElementById("itempay").value});
				handPayList();
			}
			else
			{
				for(var i=0;i<parent.lsProjectinfo.length;i++)
				{
					if(checkNull(curRecord.csitemno)==checkNull(parent.lsProjectinfo[i].id.prjno)
					 && checkNull(parent.lsProjectinfo[i].editflag)==1)
					 {
					 	commoninfodivdetial.updateRow(curRecord,{cspaymode: document.getElementById("itempay").value});
						handPayList();
						return;
					 }
				}
				if(checkNull(curRecord.csitemno)=="302" 
				|| checkNull(curRecord.csitemno)=="303" 
				|| checkNull(curRecord.csitemno)=="305" 
				|| checkNull(curRecord.csitemno)=="306"
				|| checkNull(curRecord.csitemno)=="300" 
				|| checkNull(curRecord.csitemno)=="3002"
				|| checkNull(curRecord.csitemno)=="321"
				|| checkNull(curRecord.csitemno)=="322"
				|| checkNull(curRecord.csitemno)=="323"
				|| checkNull(curRecord.csitemno)=="324"
				|| checkNull(curRecord.csitemno)=="325"
				|| checkNull(curRecord.csitemno)=="326"
				|| checkNull(curRecord.csitemno)=="327"
				|| checkNull(curRecord.csitemno)=="328"
				|| checkNull(curRecord.csitemno)=="329"
				|| checkNull(curRecord.csitemno)=="330"
				|| checkNull(curRecord.csitemno)=="331" )
				{
						if(document.getElementById("itempay").value=="4" || document.getElementById("itempay").value=="17" || document.getElementById("itempay").value=="A"  )
						{
							commoninfodivdetial.updateRow(curRecord,{cspaymode: document.getElementById("itempay").value,
																	 csitemamt:  ForDight(curRecord.csdisprice*1*curRecord.csdiscount,1)});
						}
						else
						{
							commoninfodivdetial.updateRow(curRecord,{cspaymode: document.getElementById("itempay").value,
																	 csitemamt:  ForDight(curRecord.csdisprice*1,1)});
						}
						handPayList();
						return;
				}
				var requestUrl ="cc011/loadCostRate.action"; 
				var responseMethod="loadCostRateMessage";
				var params="strCurItemId="+curRecord.csitemno;
				params=params+"&strCardType="+document.getElementById("cscardtype").value;
				if(document.getElementById("itempay").value=="17")
					params=params+"&strCurPayMode=4";	
				else
					params=params+"&strCurPayMode="+document.getElementById("itempay").value;
				sendRequestForParams_p(requestUrl,responseMethod,params );
			}
			
					
			
		}
		
		function loadCostRateMessage(request)
		{
			var responsetext = eval("(" + request.responseText + ")");
	
			dCostProjectRate=responsetext.DCostProjectRate;
			var facttyprice=0 
			var dstandprice=ForDight(checkNull(responsetext.dstandprice),2)*1;   //标准价
		    var donecountprice=ForDight(checkNull(responsetext.donecountprice),2)*1; //单次价
		    var donepageprice=ForDight(checkNull(responsetext.donepageprice),2)*1; //散客价
		    var dmemberprice=ForDight(checkNull(responsetext.dmemberprice),2)*1; //会员价
		    var dcuxiaoprice=ForDight(checkNull(responsetext.dcuxiaoprice),2)*1; //促销价
		    
		    if(document.getElementById("billflag").value=="0")
     		{
     			facttyprice=donepageprice;
     		}
     		else if(document.getElementById("billflag").value=="1")
     		{
     			facttyprice=dmemberprice;
     		}
     		else
     		{
     			facttyprice=dmemberprice;
     		}
     				
     				
		    var usePriceFlag=0;
			var csdisprice=dstandprice*1;
			var factcostprice=ForDight(dstandprice*1*dCostProjectRate*1,1);

		    if(dcuxiaoprice*1>0 )
		     {
		     			factcostprice=dcuxiaoprice*1;
		     			if(dcuxiaoprice*1>0 && dstandprice*1>0 && dcuxiaoprice> ForDight(dstandprice*1*dCostProjectRate*1,1) )
		     			{
		     				factcostprice= ForDight(dstandprice*1*dCostProjectRate*1,1);
		     			}
		     			if(dcuxiaoprice*1>0 && dstandprice*1>0 && dcuxiaoprice<= ForDight(dstandprice*1*dCostProjectRate*1,1) )
		     			{	
		     				dCostProjectRate=1;
		     				factcostprice= ForDight(dcuxiaoprice*1,1);
		     				csdisprice=factcostprice;
		     			}
		     }
		     else
		     {
		     			if(facttyprice*1>0 && donecountprice*1==0)
		     			{
		     				dCostProjectRate=1;
		     				factcostprice=facttyprice*1;
		     				csdisprice=factcostprice;
		     			}
		     			else if(facttyprice*1==0 && donecountprice*1>0)
		     			{
		     				dCostProjectRate=1;
		     				factcostprice=donecountprice*1;
		     				csdisprice=factcostprice;
		     			}
		     			else if(facttyprice*1>0 && donecountprice*1>0)
		     			{
		     				dCostProjectRate=1;
		     				factcostprice=curRecord.csunitprice;
		     				factcostprice=checkNull(curRecord.csdisprice)*1;
		     				csdisprice=factcostprice;
		     			}
		     }
		    if(curRecord.csitemcount*1==0.5 && ForDight(dCostProjectRate,1)==1)
		    {
		    	if(curRecord.shareflag==0)
				{
					commoninfodivdetial.updateRow(curRecord,{cspaymode: document.getElementById("itempay").value,
														 csdiscount: ForDight(dCostProjectRate,1),
														 csdisprice: ForDight(csdisprice*1,0),
														 csitemamt: ForDight(factcostprice*1,0)});
				}
				else
				{
					commoninfodivdetial.updateRow(curRecord,{cspaymode: document.getElementById("itempay").value,
														 csdiscount: ForDight(dCostProjectRate,1),
														 csdisprice: ForDight(csdisprice*1,0)-20,
														 csitemamt: ForDight(factcostprice*1,0)-20});
				}
		    }
		    else
		    {
		    	if(curRecord.shareflag==0)
				{
					commoninfodivdetial.updateRow(curRecord,{cspaymode: document.getElementById("itempay").value,
														 csdiscount: ForDight(dCostProjectRate,1),
														 csdisprice: ForDight(csdisprice*1*curRecord.csitemcount*1,0),
														 csitemamt: ForDight(factcostprice*1*curRecord.csitemcount*1,0)});
				}
				else
				{
					commoninfodivdetial.updateRow(curRecord,{cspaymode: document.getElementById("itempay").value,
														 csdiscount: ForDight(dCostProjectRate,1),
														 csdisprice: ForDight(csdisprice*1*curRecord.csitemcount*1,0)-20,
														 csitemamt: ForDight(factcostprice*1*curRecord.csitemcount*1,0)-20});
				}
		    }
			
			handPayList();
			
		}
		
		
		function validateFristEmpNo(obj)
		{
			if(checkNull(curRecord.csitemno)=="")
			{
				document.getElementById("diyongcardno").focus();
			 	document.getElementById("diyongcardno").select();
			 	obj.value="";
		     	$.ligerDialog.error("请先输入项目编码!");
		     	return ;
			}
				if(obj.value=="")
				{
					commoninfodivdetial.updateRow(curRecord,{csfirstsaler: '',csfirsttype:''});
				}
				else
				{
					var vflag=0;
					if(parent.StaffInfo!=null && parent.StaffInfo.length>0)
					{
						for(var i=0;i<parent.StaffInfo.length;i++)
						{
							if(parent.StaffInfo[i].bstaffno==obj.value)
			 				{
			 					commoninfodivdetial.updateRow(curRecord,{csfirstsaler: parent.StaffInfo[i].bstaffno, csfirsttype:'2'});
			 					document.getElementById("wCostFirstEmptype").focus();
			 					vflag=1;
			 					break;
			 				}
						}
					}
					if(vflag==0)
					{
						document.getElementById("itempay").focus();
						obj.value="";
						commoninfodivdetial.updateRow(curRecord,{csfirstsaler: '',csfirsttype:''});
						$.ligerDialog.error("该员工编号不存在,请确认！");
						
					}
				}
		}
		
		function wCostFirstEmptype(obj)
		{
			if(obj.value=="3")
			{
			var checkPrjFlag=0
			if(parent.lsProjectinfo!=null && parent.lsProjectinfo.length>0)
			{
					for(var i=0;i<parent.lsProjectinfo.length;i++)
					{
						if(parent.lsProjectinfo[i].id.prjno==curRecord.csitemno )
						{
							//非疗程的美容大项
				     		if(checkNull(parent.lsProjectinfo[i].prjtype)=="4" && checkNull(parent.lsProjectinfo[i].prjpricetype)*1==1 && checkNull(parent.lsProjectinfo[i].prjsaletype)*1!=1 )
				     		{
				     			checkPrjFlag=1;
				     			break;			
				     		}
				     	}
					}
			}
			if(checkPrjFlag==0)
			{
				$.ligerDialog.error("该项目不是非疗程的美容大项,不能选择推荐新客,请确认！");
				obj.value="2";
			}
			}
			commoninfodivdetial.updateRow(curRecord,{csfirsttype:obj.value});
		}
		function validateCostFirstEmpshare(obj)
		{
			commoninfodivdetial.updateRow(curRecord,{csfirstshare :obj.value});
		}
		function validateSecondEmpNo(obj)
		{
			if(checkNull(curRecord.csitemno)=="")
			{
				document.getElementById("diyongcardno").focus();
			 	document.getElementById("diyongcardno").select();
			 	obj.value="";
		     	$.ligerDialog.error("请先输入项目编码!");
		     	return ;
			}
				if(obj.value=="")
				{
					commoninfodivdetial.updateRow(curRecord,{cssecondsaler: '',cssecondtype:''});
				}
				else
				{
					var vflag=0;
					if(parent.StaffInfo!=null && parent.StaffInfo.length>0)
					{
						for(var i=0;i<parent.StaffInfo.length;i++)
						{
							if(parent.StaffInfo[i].bstaffno==obj.value)
			 				{
			 					commoninfodivdetial.updateRow(curRecord,{cssecondsaler: parent.StaffInfo[i].bstaffno,cssecondtype:2});
			 					document.getElementById("wCostSecondEmptype").focus();
			 					vflag=1;
			 					break;
			 				}
			
						}
					}
					if(vflag==0)
					{
						if(document.getElementById("wCostFirstEmptype").disabled==true)
						{
							document.getElementById("wCostFirstEmpshare").focus();
			 				document.getElementById("wCostFirstEmpshare").select();
						}
						else
						{
							document.getElementById("wCostFirstEmptype").focus();
						}
						//obj.value="";
						commoninfodivdetial.updateRow(curRecord,{cssecondsaler: '',cssecondtype:''});
						$.ligerDialog.error("该员工编号不存在,请确认！");
						
					}
				}
		}
		
		
		function wCostSecondEmptype(obj)
		{
			commoninfodivdetial.updateRow(curRecord,{cssecondtype:obj.value});
		}
		function validateCostSecondEmpshare(obj)
		{
			commoninfodivdetial.updateRow(curRecord,{cssecondshare: obj.value});
		}
		function validateThirthdEmpNo(obj)
		{
			if(checkNull(curRecord.csitemno)=="")
			{
				document.getElementById("diyongcardno").focus();
			 	document.getElementById("diyongcardno").select();
			 	obj.value="";
		     	$.ligerDialog.error("请先输入项目编码!");
		     	return ;
			}
				if(obj.value=="")
				{
					commoninfodivdetial.updateRow(curRecord,{csthirdsaler: '',csthirdtype:''});
				}
				else
				{
					var vflag=0;
					if(parent.StaffInfo!=null && parent.StaffInfo.length>0)
					{
						for(var i=0;i<parent.StaffInfo.length;i++)
						{
							if(parent.StaffInfo[i].bstaffno==obj.value)
			 				{
			 					commoninfodivdetial.updateRow(curRecord,{csthirdsaler: parent.StaffInfo[i].bstaffno,csthirdtype:2});
			 					document.getElementById("wCostthirthEmptype").focus();
			 					vflag=1;
			 					break;
			 				}
			
						}
					}
					if(vflag==0)
					{
						if(document.getElementById("wCostSecondEmptype").disabled==true)
						{
							document.getElementById("wCostFirstEmpshare").focus();
			 				document.getElementById("wCostFirstEmpshare").select();
						}
						else
						{
							document.getElementById("wCostSecondEmptype").focus();
						}
						
						//obj.value="";
						commoninfodivdetial.updateRow(curRecord,{csthirdsaler: '',csthirdtype:''});
						$.ligerDialog.error("该员工编号不存在,请确认！");
					}
				}
		}
		
		function wCostThirthEmptype(obj)
		{
			commoninfodivdetial.updateRow(curRecord,{csthirdtype :obj.value});
		}
		function validateCostThirthEmpshare(obj)
		{
			commoninfodivdetial.updateRow(curRecord,{csthirdshare :obj.value});
		}
		function checkThirthEmptype()
		{
			if(checkNull(curRecord.csfirstsaler==""))
			{
				return;
			}
			addRecord();
		}
		function checkThirthEmpshare()
		{
			if(checkNull(curRecord.csfirstsaler==""))
			{
				return;
			}
			addRecord();
		}
		function checkSecondEmpNo(obj)
		{	
			if(checkNull(obj.value)=="" && checkNull(curRecord.csfirstsaler)!="")
			{
				//addRecord();
			}
		}
		function checkThirthEmpNo(obj)
		{
			if(checkNull(obj.value)=="" && checkNull(curRecord.csfirstsaler)!="")
			{
				addRecord();
			}
		}
		function validateFastItemCheckItem1()
		{
			 validateFastItemCheck("302",2,"dSp074Price",0);
		}
		function validateFastItemCheckItem2()
		{
			 validateFastItemCheck("302",2,"dSp075Price",1);
		}
		function validateFastItemCheckItem3()
		{
			 validateFastItemCheck("302",2,"dSp076Price",2);
		}
		function validateFastItemCheckItem4()
		{
			  validateFastItemCheck("302",2,"dSp077Price",3);
		}
		function validateFastItemCheckItem5()
		{
			  validateFastItemCheck("302",2,"dSp078Price",4);
		}
		function validateFastItemCheckItem6()
		{
			  validateFastItemCheck("302",2,"dSp079Price",5);
		}
		
		function validateFastItemCheckItemy1()
		{
			 validateFastItemCheck("302",2,"dSp081Price",0);
		}
		function validateFastItemCheckItemy2()
		{
			 validateFastItemCheck("302",1,"dSp082Price",1);
		}
		function validateFastItemCheckItemy3()
		{
			 validateFastItemCheck("302",1,"dSp083Price",2);
		}
		function validateFastItemCheckItemy4()
		{
			  validateFastItemCheck("302",1,"dSp084Price",3);
		}
		function validateFastItemCheckItemy5()
		{
			  validateFastItemCheck("302",1,"dSp085Price",4);
		}
		function validateFastItemCheckItemy6()
		{
			  validateFastItemCheck("302",1,"dSp086Price",5);
		}
		
		//快速选择洗剪吹项目 btntype 0 儿童 1设计师 2 首席 3总监 4创意总监 5店长
	    function validateFastItemCheck(obj,objtype,objPrice,btntype)
	    {
	    	if(document.getElementById("paramSp098").value=="1" )
			{
				if(btntype==0)
					obj="331";
				else  if(btntype==1)
					obj="321";
				else  if(btntype==2)
					obj="322";
				else  if(btntype==3)
					obj="323";
				else  if(btntype==4)
					obj="324";
				else  if(btntype==5)
					obj="325";
		    	$.ligerDialog.confirmDBXJC('确认 [达标] 类型', function (result)
				{
			   	 	if( result==true)
	           		{
						handFastLoad(obj,objtype,objPrice,1,1);
					}
					else
	           		{
						handFastLoad(obj,objtype,objPrice,1,2);
					}
				});
			} 
			else
			{
				$.ligerDialog.confirmXJC('确认 [洗头] 类型', function (result)
				{
			   	 	if( result==true)
	           		{
						obj="302"
						handFastLoad(obj,objtype,objPrice,1,0);
					}
					else
	           		{
						obj="305"
						handFastLoad(obj,objtype,objPrice,1,0);
					}
				});
			}	
	    }
	    
	    
	    function validateFastItemCheckItemy2_XC()
		{
			 validateFastItemCheck_XC("303",1,"dSp087Price",1);
		}
		function validateFastItemCheckItemy3_XC()
		{
			 validateFastItemCheck_XC("303",1,"dSp088Price",2);
		}
		function validateFastItemCheckItemy4_XC()
		{
			  validateFastItemCheck_XC("303",1,"dSp089Price",3);
		}
		function validateFastItemCheckItemy5_XC()
		{
			  validateFastItemCheck_XC("303",1,"dSp090Price",4);
		}
		function validateFastItemCheckItemy6_XC()
		{
			  validateFastItemCheck_XC("303",2,"dSp091Price",5);
		}
	    
	    function validateFastItemCheckItemy7_XC()
	    {
	    	validateFastItemCheck_XC("303",2,"dSp092Price",6);
	    }
		//快速选择洗吹项目
	    function validateFastItemCheck_XC(obj,objtype,objPrice,btntype)
	    {
	    	if(document.getElementById("paramSp098").value=="1")
			{
		    	$.ligerDialog.confirmDBXJC('确认 [达标] 类型', function (result)
				{
					if(btntype==1)
						obj="326";
					else  if(btntype==2)
						obj="327";
					else  if(btntype==3)
						obj="328";
					else  if(btntype==4)
						obj="329";
					else  if(btntype==5)
						obj="330";
			   	 	if( result==true)
	           		{
						handFastLoad(obj,objtype,objPrice,1,1);
					}
					else
	           		{
						handFastLoad(obj,objtype,objPrice,1,2);
					}
				}); 
			}
			else
			{
				$.ligerDialog.confirmXJC('确认 [洗头] 类型', function (result)
				{
			   	 	if( result==true)
	           		{
						obj="303"
						handFastLoad(obj,objtype,objPrice,1,0);
					}
					else
	           		{
						obj="306"
						handFastLoad(obj,objtype,objPrice,1,0);
					}
				}); 
			}	
	    }
	    
	    //csitemstate 0 无 1 达标 2 未达标
	    function handFastLoad(obj,objtype,objPrice,pricetype,csitemstate)
	    {
	    		var paymode="1";
		    	if(document.getElementById("billflag").value=="0")
	     		{
	     			paymode="1";
	     		}
	     		else if(document.getElementById("billflag").value=="1")
	     		{
	     			paymode="4";
	     		}
	     		else
	     		{
	     			paymode="A";
	     		}

		    	if(objtype==1)
		    	{
		    			if(parent.lsProjectinfo!=null && parent.lsProjectinfo.length>0)
						{
							for(var i=0;i<parent.lsProjectinfo.length;i++)
							{
								if(parent.lsProjectinfo[i].id.prjno==obj)
				 				{
				 					fastLoadPriject(obj,
				 									parent.lsProjectinfo[i].prjname,1,
				 									eval("(parent." + objPrice + ")")*1 ,
				 									eval("(parent." + objPrice + ")")*1*document.getElementById("dXjcDiscount").value*1 ,document.getElementById("dXjcDiscount").value*1,paymode,"","","",csitemstate);
				 				}
				
							}
						}
		    	}
		    	else
		    	{
		    			if(parent.lsProjectinfo!=null && parent.lsProjectinfo.length>0)
						{
							for(var i=0;i<parent.lsProjectinfo.length;i++)
							{
								if(parent.lsProjectinfo[i].id.prjno==obj)
				 				{
				 					if(pricetype==1)
				 					{
				 						fastLoadPriject(obj,
				 									parent.lsProjectinfo[i].prjname,1,
				 									eval("(parent." + objPrice + ")") ,
				 									eval("(parent." + objPrice + ")") ,1,paymode,"","","",csitemstate);
				 				    }
				 				    else
				 				    {
				 				    	if(objPrice*1==0)
				 				    	{
				 				    		$.ligerDialog.error("不能添加单价为0 的项目！");
				 				    	}
				 				    	else
				 				    	{
				 				    		fastLoadPriject(obj,
				 									parent.lsProjectinfo[i].prjname,1,
				 									objPrice ,
				 									objPrice ,1,paymode,"","","",csitemstate);
				 						}
				 				    }
				 				}
				
							}
						}
		    	}
	    	
	    	document.getElementById("wCostItemNo").readOnly="readOnly";
		    document.getElementById("wCostItemNo").style.background="#EDF1F8";
			document.getElementById("wCostItemBarNo").readOnly="readOnly";
		    document.getElementById("wCostItemBarNo").style.background="#EDF1F8";
		    document.getElementById("wgCostCount").readOnly="readOnly";
		    document.getElementById("wgCostCount").style.background="#EDF1F8";
		    document.getElementById("wCostFirstEmpNo").select();
		    document.getElementById("wCostFirstEmpNo").focus();
	    }
	
	
	
		function fastLoadPriject(projectno,projectname,fcount,curprice,curcostamt,discount,paymode,firstemp,secondemp,thirdemp,csitemstate)
	    {
	    	try
	    	{
	    		var detiallength=commoninfodivdetial.rows.length*1;
		    	if(detiallength==0)
				{
					addRecord();
					//detiallength=detiallength+1;
				}
				else if( commoninfodivdetial.getRow(0)['csitemname']!="")
				{
					addRecord();
					//detiallength=detiallength+1;
				}
				//curRecord=commoninfodivdetial.getRow(detiallength-1);
				var rowdata=commoninfodivdetial.updateRow(curRecord,{csitemno: projectno,
																	 csitemname: projectname,
																	 csitemcount: ForDight(fcount,1),
	     															 csunitprice: ForDight(curprice,1),
	     															 csdisprice: ForDight(curprice,1),
	     															 csitemamt:  ForDight(curcostamt,0),
	     															 csdiscount: ForDight(discount,2),
	     															 cspaymode: paymode,
	     															 csfirstsaler: firstemp,
	     															 csfirsttype:'2',
	     															 csfirstshare:'',
	     															 cssecondsaler: secondemp,
	     															 cssecondtype:'2',
	     															 cssecondshare:'',
	     															 csthirdsaler: thirdemp,
	     															 csthirdtype:'2',
	     															 csthirdshare:'',
	     															 csproseqno:0,
	     															 csitemstate:csitemstate});  
	   	 		initDetialGrid();
				handPayList();
			}catch(e){alert(e.message);}
	    }
	
	
		//现金分单支付
		function needOtherPay()
		{
				  if(checkNull(curRecord.csitemname)=="")
				  {
				  		$.ligerDialog.error("请先确认分单项目！");
				  		return ;
				  }
				  if(checkNull(curRecord.cspaymode)=="9")
				  {
				  		$.ligerDialog.error("疗程项目不能分单支付！");
				  		return ;
				  }
				 shownewPayDialogmanager=$.ligerDialog.open({ height: null, url: contextURL+'/CardControl/CC011/needNewOtherPay.jsp', width: 360, showMax: false, showToggle: false, showMin: false, isResize: false, title: '分单支付' });
    	}
    	//储值分单支付
    	function needOtherCardPay()
		{
				  if(checkNull(curRecord.csitemname)=="")
				  {
				  		$.ligerDialog.error("请先确认分单项目！");
				  		return ;
				  }
				  if(checkNull(curRecord.cspaymode)=="9")
				  {
				  		$.ligerDialog.error("疗程项目不能分单支付！");
				  		return ;
				  }
				 shownewPayDialogmanager=$.ligerDialog.open({ height: null, url: contextURL+'/CardControl/CC011/needCardNewOtherPay.jsp', width: 360, showMax: false, showToggle: false, showMin: false, isResize: false, title: '分单支付' });
    	}
    	
    	function needOldOtherPay()
		{
				  if(checkNull(curRecord.csitemname)=="")
				  {
				  		$.ligerDialog.error("请先确认分单项目！");
				  		return ;
				  }
				  if(checkNull(curRecord.cspaymode)=="9")
				  {
				  		$.ligerDialog.error("疗程项目不能分单支付！");
				  		return ;
				  }
				 shownewPayDialogmanager=$.ligerDialog.open({ height: null, url: contextURL+'/CardControl/CC011/needOtherPay.jsp', width: 360, showMax: false, showToggle: false, showMin: false, isResize: false, title: '分单支付' });
    	}
    	
    	 function selectCall()
		 {	
		    if(document.getElementById("wCostItemNo").value!="")
		 		validateProjectNo(document.getElementById("wCostItemNo"));
		 	else if(document.getElementById("wCostItemBarNo").value!="")
		 		validateGoodsNo(document.getElementById("wCostItemBarNo"));
		 		
		 }
		 
		 function loadProSeqno(strInput)
		 {
		 	return arrProList;
		 }
		 
	function addOptiondivex(value,keyw)
  	{
   		var showvalue = value.replace(keyw, "<b><font color=\"blue\">" + keyw + "</font></b>");
  		var realValue=value.substring(0,value.indexOf("-"));
  		document.getElementById("keysList").innerHTML +="<div onmouseover=this.className=\"sman_selectedStyle\";document.getElementById(\""+objInputId+"\").value=\"" + realValue + "\" onmouseout=this.className=\"\"; onmousedown=document.getElementById(\""+objInputId+"\").value=\"" + realValue + "\";selectCall();document.getElementById(\""+objInputId+"\").onchange();>" + showvalue + "</div>" ;
  	}
  	
  	
  		function managerRate()
		 {
		 	$.ligerDialog.prompt('输入经理工号','', function (yes,value) { if(yes) checkmanagerStaffNo(value); });
		 }
		
		  function checkmanagerStaffNo(value)
		  {
		  	var staffNo=value;
		  	$.ligerDialog.prompt('输入经理签单密码','', function (yes,value) { if(yes) checkmanagerPass(staffNo,value); });
		
		  }
		  
		  function checkmanagerPass(staffNo,value)
		  {
		  		var requestUrl ="cc011/checkmanagerPass.action";
				var responseMethod="checkmanagerPassMessage";	
				var params="strCurManagerNo="+staffNo;	
				params =params+ "&strCurManagerPass="+value;
				sendRequestForParams_p(requestUrl,responseMethod,params ); 
				
		  }
		  
		 function checkmanagerPassMessage(request)
	     {
	    		
	        	try
				{
					var responsetext = eval("(" + request.responseText + ")");
		        	var strMessage=responsetext.strMessage;
		        	if(checkNull(strMessage)=="")
		        	{	     
		        		document.getElementById("SP027Rate").value=ForDight(checkNull(responsetext.SP027Rate)*1/100,2);
		        		$.ligerDialog.prompt('输入经理折扣(折扣上限)'+document.getElementById("SP027Rate").value,'', function (yes,value) { if(yes) checkmanagerRate(value); });
		        	
		        		
		        	}
		        	else
		        	{
		        		$.ligerDialog.warn(strMessage);
		        	}
	        	}
				catch(e)
				{
					alert(e.message);
				}
	      }
	      
	      function checkmanagerRate(value)
	      {
	      	 if(value*1<document.getElementById("SP027Rate").value*1)
	      	 {
	      	 	$.ligerDialog.prompt('输入折扣不能低于上限(折扣上限'+document.getElementById("SP027Rate").value+')','', function (yes,value) { if(yes) checkmanagerRate(value); });
	      	 }
	      	 else
	      	 {
	      	 		for (var rowid in commoninfodivdetial.records)
					{
							 	var row =commoninfodivdetial.records[rowid];
							 	
							 	if((checkNull(row['cspaymode']=="1") || checkNull(row['cspaymode'])=="2" 
							 	|| checkNull(row['cspaymode']=="6") || checkNull(row['cspaymode'])=="14" 
							 	|| checkNull(row['cspaymode']=="15") ) && checkNull(row['csitemname'])!="")
							 	{
							 		commoninfodivdetial.updateRow(row,{
													 csdiscount: value,
													 csitemamt: ForDight(value*1*row.csitemamt*1,0)});
									handPayList();
			
							 	}
							 	      		 
					}	
	      	 }
	      }
	      


		 function manager38Rate()
		 {
		 		if(useManagerRate==1)
		 		{
		 			$.ligerDialog.error("该单据已享受过38节折扣活动,请确认！");
				  	return ;
		 		}
		 		var intMonth=document.getElementById("csdate").value.substring(5,7)*1
				var intDay=document.getElementById("csdate").value.substring(8,10)*1
				if(intMonth*1!=3 || 18<intDay*1)
				{
					$.ligerDialog.error("本次活动日期在 3.01-3.18之间,请确认！");
				  	return ;
				}
		 		$.ligerDialog.prompt('输入经理工号','', function (yes,value) { if(yes) checkmanager38StaffNo(value); });
		 }
		
		  function checkmanager38StaffNo(value)
		  {
		  		var staffNo=value;
		  		$.ligerDialog.prompt('输入经理签单密码','', function (yes,value) { if(yes) checkmanager38Pass(staffNo,value); });
		
		  }
		  
		  function checkmanager38Pass(staffNo,value)
		  {
		  		var requestUrl ="cc011/checkmanagerPass.action";
				var responseMethod="checkmanager38PassMessage";	
				var params="strCurManagerNo="+staffNo;	
				params =params+ "&strCurManagerPass="+value;
				sendRequestForParams_p(requestUrl,responseMethod,params ); 
				
		  }
		  
		  
		   function checkmanager38PassMessage(request)
	     {
	    		
	        	try
				{
					var responsetext = eval("(" + request.responseText + ")");
		        	var strMessage=responsetext.strMessage;
		        	if(checkNull(strMessage)=="")
		        	{	     
		        		var ratevalue=0.95;
		        		for (var rowid in commoninfodivdetial.records)
						{
								 	var row =commoninfodivdetial.records[rowid];
								 	if(checkNull(row['cspaymode'])!="9" && checkNull(row['cspaymode'])!="11" && checkNull(row['cspaymode'])!="16"
								 	 && checkNull(row['cspaymode'])!="12" && checkNull(row['cspaymode'])!="13" 
								 	 && checkNull(row['csitemname'])!=""  && checkNull(row['csitemno'])!="300" 
								 	 && checkNull(row['csitemno'])!="3002"   && checkNull(row['csitemno'])!="3008" 
								 	 && checkNull(row['csitemno'])!="301"   && checkNull(row['csitemno'])!="302" 
								 	 && checkNull(row['csitemno'])!="303"   && checkNull(row['csitemno'])!="305" 
								 	 && checkNull(row['csitemno'])!="306"   && checkNull(row['csitemno'])!="309" 
								 	 && checkNull(row['csitemno'])!="310"   && checkNull(row['csitemno'])!="311"
								 	 && checkNull(row['csitemno'])!="321"   && checkNull(row['csitemno'])!="322" 
								 	 && checkNull(row['csitemno'])!="323"   && checkNull(row['csitemno'])!="324" 
								 	 && checkNull(row['csitemno'])!="325"   && checkNull(row['csitemno'])!="326" 
								 	 && checkNull(row['csitemno'])!="327"   && checkNull(row['csitemno'])!="328" 
								 	 && checkNull(row['csitemno'])!="329"   && checkNull(row['csitemno'])!="330" && checkNull(row['csitemno'])!="331")
								 	{
								 		commoninfodivdetial.updateRow(row,{
														 csdiscount: ratevalue*checkNull(row.csdiscount),
														 csitemamt: ForDight(ratevalue*1*row.csitemamt*1,0)});
										handPayList();
				
								 	}
								 	      		 
						}
						useManagerRate=1;	
		        	}
		        	else
		        	{
		        		$.ligerDialog.warn(strMessage);
		        	}
	        	}
				catch(e)
				{
					alert(e.message);
				}
	      }
		  
	
	function loadProjectInfo(obj)
	{
		projectdilogText=obj;
		projectdilog=$.ligerDialog.open({ height: 600, url: contextURL+'/common/commonDilog/ProjectInfo.html', width: 750, isResize:true, title: '项目列表' });
    }
    
    function loadStaffInfo(obj)
	{
		staffdilogText=obj;
		staffdilog=$.ligerDialog.open({ height: 600, url: contextURL+'/common/commonDilog/StaffInfo.html', width: 750, isResize:true, title: '员工列表' });
    }
    
    //洗吹特价
    function validateSpecialItemCheckItem_XC1()
    {
    	if(document.getElementById("paramSp098").value=="1")
		{
	    	$.ligerDialog.confirmDBXJC('确认 [达标] 类型', function (result)
			{
				var prjCostPrice=document.getElementById("lbSpecialItemyxc1").innerHTML*1
	    		if( result==true)
				{
					  handFastLoad("326",2,prjCostPrice,2,1);
				}
				else
				{
					  handFastLoad("326",2,prjCostPrice,2,2);
										
				}
			});
		}
		else
		{
			$.ligerDialog.confirmXJC('确认 [洗头] 类型', function (result)
			{
				var prjCostPrice=document.getElementById("lbSpecialItemyxc1").innerHTML*1
	    		if( result==true)
				{
						handFastLoad("303",2,prjCostPrice,2,0);
				}
				else
				{
					  handFastLoad("306",2,prjCostPrice,2,0);
										
				}
			});
		}
    }
    function validateSpecialItemCheckItem_XC2()
    {
    	if(document.getElementById("paramSp098").value=="1")
		{
	    	$.ligerDialog.confirmDBXJC('确认 [达标] 类型', function (result)
			{
				var prjCostPrice=document.getElementById("lbSpecialItemyxc2").innerHTML*1
	    		if( result==true)
				{
						handFastLoad("327",2,prjCostPrice,2,1);
				}
				else
				{
					  handFastLoad("327",2,prjCostPrice,2,2);
										
				}
			});
		}
		else
		{
			$.ligerDialog.confirmXJC('确认 [洗头] 类型', function (result)
			{
				var prjCostPrice=document.getElementById("lbSpecialItemyxc2").innerHTML*1
	    		if( result==true)
				{
						handFastLoad("303",2,prjCostPrice,2,0);
				}
				else
				{
					  handFastLoad("306",2,prjCostPrice,2,0);
										
				}
			});
		}
    }
    function validateSpecialItemCheckItem_XC3()
    {
    	if(document.getElementById("paramSp098").value=="1")
		{
			$.ligerDialog.confirmDBXJC('确认 [达标] 类型', function (result)
			{
				var prjCostPrice=document.getElementById("lbSpecialItemyxc3").innerHTML*1
	    		if( result==true)
				{
						handFastLoad("328",2,prjCostPrice,2,1);
				}
				else
				{
					  handFastLoad("328",2,prjCostPrice,2,2);
										
				}
			});
		}
		else
		{
			$.ligerDialog.confirmXJC('确认 [洗头] 类型', function (result)
			{
				var prjCostPrice=document.getElementById("lbSpecialItemyxc3").innerHTML*1
	    		if( result==true)
				{
						handFastLoad("303",2,prjCostPrice,2,0);
				}
				else
				{
					  handFastLoad("306",2,prjCostPrice,2,0);
										
				}
			});
		}
    	
    }
    function validateSpecialItemCheckItem_XC4()
    {
    	if(document.getElementById("paramSp098").value=="1")
    	{
    		$.ligerDialog.confirmDBXJC('确认 [达标] 类型', function (result)
			{
				var prjCostPrice=document.getElementById("lbSpecialItemyxc4").innerHTML*1
	    		if( result==true)
				{
						handFastLoad("329",2,prjCostPrice,2,1);
				}
				else
				{
					  handFastLoad("329",2,prjCostPrice,2,2);
										
				}
			});
    	}
    	else
    	{
    		$.ligerDialog.confirmXJC('确认 [洗头] 类型', function (result)
			{
				var prjCostPrice=document.getElementById("lbSpecialItemyxc4").innerHTML*1
	    		if( result==true)
				{
						handFastLoad("303",2,prjCostPrice,2,0);
				}
				else
				{
					  handFastLoad("306",2,prjCostPrice,2,0);
										
				}
			});
    	}
    	
    }
    function validateSpecialItemCheckItem_XC5()
    {
    	if(document.getElementById("paramSp098").value=="1")
    	{
    		$.ligerDialog.confirmDBXJC('确认 [达标] 类型', function (result)
			{
				var prjCostPrice=document.getElementById("lbSpecialItemyxc5").innerHTML*1
	    		if( result==true)
				{
						handFastLoad("321",2,prjCostPrice,2,1);
				}
				else
				{
					  handFastLoad("321",2,prjCostPrice,2,2);
										
				}
			});
    	}
    	else
    	{
    		$.ligerDialog.confirmXJC('确认 [洗头] 类型', function (result)
			{
				var prjCostPrice=document.getElementById("lbSpecialItemyxc5").innerHTML*1
	    		if( result==true)
				{
						handFastLoad("302",2,prjCostPrice,2,0);
				}
				else
				{
					  handFastLoad("305",2,prjCostPrice,2,0);
										
				}
			});
    	}
    	
    }
    function validateSpecialItemCheckItem_XC6()
    {
    	if(document.getElementById("paramSp098").value=="1")
    	{
    		$.ligerDialog.confirmDBXJC('确认 [达标] 类型', function (result)
			{
				var prjCostPrice=document.getElementById("lbSpecialItemyxc6").innerHTML*1
	    		if( result==true)
				{
						handFastLoad("322",2,prjCostPrice,2,1);
				}
				else
				{
					  handFastLoad("322",2,prjCostPrice,2,2);
										
				}
			});
    	}
    	else
    	{
    		$.ligerDialog.confirmXJC('确认 [洗头] 类型', function (result)
			{
				var prjCostPrice=document.getElementById("lbSpecialItemyxc6").innerHTML*1
	    		if( result==true)
				{
						handFastLoad("302",2,prjCostPrice,2,0);
				}
				else
				{
					  handFastLoad("305",2,prjCostPrice,2,0);
										
				}
			});
    	}
    	
    }
    function validateSpecialItemCheckItem_XC7()
    {
    	if(document.getElementById("paramSp098").value=="1")
    	{
    		$.ligerDialog.confirmDBXJC('确认 [达标] 类型', function (result)
			{
				var prjCostPrice=document.getElementById("lbSpecialItemyxc7").innerHTML*1
	    		if( result==true)
				{
						handFastLoad("323",2,prjCostPrice,2,1);
				}
				else
				{
					  handFastLoad("323",2,prjCostPrice,2,2);
										
				}
			});
    	}
    	else
    	{
    		$.ligerDialog.confirmXJC('确认 [洗头] 类型', function (result)
			{
				var prjCostPrice=document.getElementById("lbSpecialItemyxc7").innerHTML*1
	    		if( result==true)
				{
						handFastLoad("302",2,prjCostPrice,2,0);
				}
				else
				{
					  handFastLoad("305",2,prjCostPrice,2,0);
										
				}
			});
    	}
    	
    }
    function validateSpecialItemCheckItem_XC8()
    {
    	if(document.getElementById("paramSp098").value=="1")
    	{
	    	$.ligerDialog.confirmDBXJC('确认 [达标] 类型', function (result)
			{
				var prjCostPrice=document.getElementById("lbSpecialItemyxc8").innerHTML*1
	    		if( result==true)
				{
						handFastLoad("324",2,prjCostPrice,2,1);
				}
				else
				{
					  handFastLoad("324",2,prjCostPrice,2,2);
										
				}
			});
		}
		else
		{
			$.ligerDialog.confirmXJC('确认 [洗头] 类型', function (result)
			{
				var prjCostPrice=document.getElementById("lbSpecialItemyxc8").innerHTML*1
	    		if( result==true)
				{
						handFastLoad("302",2,prjCostPrice,2,0);
				}
				else
				{
					  handFastLoad("305",2,prjCostPrice,2,0);
										
				}
			});
		}
    }
    function validateSpecialItemCheckItem_XC9()
    {
    	if(document.getElementById("paramSp098").value=="1")
    	{
    		$.ligerDialog.confirmDBXJC('确认 [达标] 类型', function (result)
			{
				var prjCostPrice=document.getElementById("lbSpecialItemyxc9").innerHTML*1
	    		if( result==true)
				{
						handFastLoad("325",2,prjCostPrice,2,1);
				}
				else
				{
					  handFastLoad("325",2,prjCostPrice,2,2);
										
				}
			});
    	}
    	else
    	{
	    	$.ligerDialog.confirmXJC('确认 [洗头] 类型', function (result)
			{
				var prjCostPrice=document.getElementById("lbSpecialItemyxc9").innerHTML*1
	    		if( result==true)
				{
						handFastLoad("302",2,prjCostPrice,2,0);
				}
				else
				{
					  handFastLoad("305",2,prjCostPrice,2,0);
										
				}
			});
		}
    }
    
    function validateRecommendempid(obj)
    {
    	if(obj.value=="")
    	{
    		document.getElementById("recommendempname").value="";
    		document.getElementById("recommendempinid").value="";
    	}
    	else
    	{	
    		var vflag=0
    		if(parent.StaffInfo!=null && parent.StaffInfo.length>0)
			{
				for(var i=0;i<parent.StaffInfo.length;i++)
				{
					if(parent.StaffInfo[i].bstaffno==obj.value)
			 		{
			 					document.getElementById("recommendempname").value=parent.StaffInfo[i].staffname;
			 					document.getElementById("recommendempinid").value=parent.StaffInfo[i].manageno;
			 					vflag=1;
			 					break;
			 		}
				}
			}
			if(vflag==0)
			{
				document.getElementById("recommendempid").value="";
				document.getElementById("recommendempname").value="";
				document.getElementById("recommendempinid").value="";
				$.ligerDialog.error("该员工编号不存在,请确认！");
			}
    	}
    }
    
    
    