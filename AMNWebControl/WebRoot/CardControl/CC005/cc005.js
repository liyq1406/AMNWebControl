	var compTreeManager;
   	var compTree;
   	var commoninfodivsecond=null;//供应商列表
   	var strCurCompId="";
   	var strCurCardNo="";
   	var strAccountType="";
   	var strAccountTypeName="";
   	var cardBalance=0;
   	var cardDebts=0;
 	var cc005layout=null;
   	var CC005DetialTab = null;
   	var commoninfodivAccount = null;
   	var commoninfodivProAccount = null;
   	var commoninfodivCardchange = null;
   	var commoninfodivAccountchange = null;
   	var commoninfodivSendpointcard = null;
   	var commoninfodivCardson = null;
   	var f_detailPanel	=null;
	var f_callback=null;
    var cardChangeData=JSON.parse(parent.loadCommonControlDate_select("YDZT",0));
    var cardStateData=JSON.parse(parent.loadCommonControlDate_select("HYZT",0));
    var accountChangeData=JSON.parse(parent.loadCommonControlDate_select("ZHYD",0));
    var accountData=JSON.parse(parent.loadCommonControlDate_select("ZHLX",0));
    var paymodeData=JSON.parse(parent.loadCommonControlDate_select("ZFFS",0));
    var modifyDialogmanager=null;
    $(function ()
   	{
	   try
	   {
	   		  //布局
            cc005layout= $("#cc005layout").ligerLayout({ leftWidth: 270,  allowBottomResize: false, allowLeftResize: false });
         	var height = $(".l-layout-center").height();
         	//-----会员卡集合
          	 commoninfodivsecond=$("#commoninfodivsecond").ligerGrid({
                columns: [
                { display: '归属门店', 		name: 'bcardvesting', 		width:60	,align: 'left' },
                { display: '会员卡号', 		name: 'bcardno', 		width:100	,align: 'left' },
                { display: '姓名', 			name: 'membername', 	width:60	,align: 'left'},
	            { display: '手机号码', 		name: 'memberphone',  	width:100	,align: 'left'}
	            ],  pageSize:20, 
                data: null,      
                width: '326',
                height:'98%',
                enabledEdit: false,  checkbox:false,rownumbers:false,usePager:false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                }   
            });
            //------账户列表
            commoninfodivAccount=$("#commoninfodivAccount").ligerGrid({
                columns: [
                { display: '类型', 		name: 'accounttype', 		width:50	,align: 'left' },
                { display: '账户名称', 	name: 'accounttypeText', 			width:100	,align: 'left' },
                { display: '开账日期', 	name: 'accountdatefrom', 		width:80	,align: 'left'},
	            { display: '截止日期', 	name: 'accountdateend',  		width:80	,align: 'left'},
	            { display: '余额', 		name: 'accountbalance',  		width:60	,align: 'left'},
	            { display: '欠款', 		name: 'accountdebts',  		width:60	,align: 'left'},
                { display: '', isSort: false, width: 40, render: function (rowdata, rowindex, value)
		            {
		                		var h =  "<a href=\"javascript: modifyCardBalance('"+checkNull(rowdata.accounttype)+"','"+checkNull(rowdata.accountbalance)+"','"+checkNull(rowdata.accountdebts)+"','"+checkNull(rowdata.accounttypeText)+"' )\"><img src='../../common/ligerui/ligerUI/skins/icons/memeber.gif'/></a> ";
		                  		return h;
		            }
	            }
	            ],  pageSize:20, 
                data: null,      
                width: '500',
                height:'200',
                enabledEdit: false,  checkbox:false,rownumbers:false,usePager:false
            });
            //------疗程账户明细
            commoninfodivProAccount=$("#commoninfodivProAccount").ligerGrid({
                columns: [
                { display: '序号', 		name: 'bproseqno', 			width:50	,align: 'left' },
                { display: '门店', 		name: 'bcardvesting', 		width:40	,align: 'left' },
                { display: '疗程代码', 	name: 'bprojectno', 		width:100	,align: 'left' },
                { display: '疗程名称', 	name: 'bprojectname', 		width:150	,align: 'left'},
	            { display: '开始日期', 	name: 'saledate',  		width:80	,align: 'left'},
	            { display: '结束日期',   name: 'cutoffdate',  	width:80	,align: 'left'},
	            { display: '购买次数',   name: 'salecount',  	width:60	,align: 'center'},
	            { display: '消耗次数',   name: 'costcount',  	width:60	,align: 'center'},
	            { display: '剩余次数',   name: 'lastcount',  	width:60	,align: 'center'},
	            { display: '购买金额',   name: 'saleamt',  	width:60	,align: 'right'},
	            { display: '消耗金额',   name: 'costamt',  	width:60	,align: 'right'},
	            { display: '剩余金额',   name: 'lastamt',  	width:60	,align: 'right'},
	            { display: '来源类型',   name: 'yearsz',  	width:60	,align: 'right',render:function(rowdata)
	            	{
	            		if(rowdata.yearsz==1)
	            		{
	            			return "赠送";
	            		}
	            		else
	            		{
	            			return "购买";
	            		}
	            	}},
	            
	            
	            { display: '备注', 		name: 'proremark',  		width:180	,align: 'left'},
	            { display: '', 		name: 'createbilltype',  	hide:true,	width:1}//剩余次数分组用
	            ],  pageSize:20, 
                data: null,      
                width: '100%',
                height:height-120,
                enabledEdit: false,  checkbox:false,rownumbers:false,usePager:false,
                groupColumnName:'createbilltype',groupColumnDisplay:'状态',
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecProDetialData(data, rowindex, rowobj);
                }   
            });
            
             $("#dhpro").ligerToolBar({ items: [
      		     { text: '疗程:&nbsp;<select id="oldProItemNo" name="oldProItemNo" onchange="validateProItem(this)"><option value="">请选择</option><option value="399001">老系统美发疗程</option><option value="499001">老系统美容疗程A(80%)</option><option value="499002">老系统美容疗程B(50%)</option><option value="W">手动填写编号</option></select>'},
	             { text: '次数:&nbsp;<input type="text"  name="oldprocount" id="oldprocount" maxlength="20"  style="width:40;"  onchange="validatePrice(this)"/>' },
	             { text: '金额:&nbsp;<input type="text"  name="oldproamt" id="oldproamt" maxlength="20" style="width:60;"  onchange="validatePrice(this)"/>' }
	          ]
            });
            $("#dhpromark").ligerToolBar({ items: [
      		     { text: '老疗程备注:&nbsp;<input type="text"  name="oldpromark" id="oldpromark" maxlength="20"  style="width:160;"  maxlength="80"/>' },
				 { text: '兑换老疗程&nbsp;', click: changeOldProInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/down.gif'  }
	          ]
            });
            
             $("#changeAccountBypro").ligerToolBar({ items: [
      		     { text: '编号:&nbsp;<input type="text"  name="curChangeProNo" id="curChangeProNo"  readonly="true" style="width:90;background:#EDF1F8" /><input type="hidden"  name="curChangeSeqnoNo" id="curChangeSeqnoNo" />&nbsp;<input type="text"  name="curChangeProName" id="curChangeProName" maxlength="20" readonly="true" style="width:140;background:#EDF1F8"  />&nbsp;<input type="hidden"  name="curLastCount" id="curLastCount" maxlength="20"  style="width:40;" /><input type="hidden"  name="curLastAmt" id="curLastAmt" maxlength="20" style="width:60;"  />' },
	             { text: '抵扣:&nbsp;<input type="text"  name="curChangeLastCount" id="curChangeLastCount" maxlength="20"  style="width:20;" onchange="validatePrice(this);changeCurCount(this)"/>&nbsp;&nbsp;<input type="text"  name="curChangeLastAmt" id="curChangeLastAmt" maxlength="20" style="width:50;background:#EDF1F8" readonly="true" />' },
	             { text: '销售:&nbsp;<input type="text"  name="curChangeSaler1" id="curChangeSaler1" maxlength="20"  style="width:50;" onchange="validateFirstEmp(this)" />&nbsp<input type="text"  name="curChangeSaler1name" id="curChangeSaler1name" maxlength="20"  style="width:60;"  readonly="true" style="width:80;background:#EDF1F8" />&nbsp;<input type="text"  name="curChangeSaler1Amt" id="curChangeSaler1Amt" maxlength="20" style="width:50;" onchange="validatePrice(this);validateSaler1Amt(this)"/>' },
				 { text: '烫染师:&nbsp;<input type="text"  name="curChangeSaler2" id="curChangeSaler2" maxlength="20"  style="width:50;" onchange="validateSecondEmp(this)" />&nbsp<input type="text"  name="curChangeSaler2name" id="curChangeSaler2name" maxlength="20"  style="width:60;"  readonly="true" style="width:80;background:#EDF1F8" />&nbsp;<input type="text"  name="curChangeSaler2Amt" id="curChangeSaler2Amt" maxlength="20" style="width:50;background:#EDF1F8" readonly="true" />' },
				 { text: '兑换&nbsp;', click: changeProAccountinfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/logout.gif'  }
	          ]
            });
       
            //----异动历史
            commoninfodivCardchange=$("#commoninfodivCardchange").ligerGrid({
                columns: [
                { display: '异动性质', 		name: 'changetype', 			width:70	,align: 'left' ,
                	editor: { type: 'select', data: cardChangeData, valueField: 'choose',selectBoxHeight:'220'},
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("YDZT",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.changetype)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
                { display: '异动日期', 		name: 'chagedate', 		width:80	,align: 'left' },
                { display: '异动单号', 		name: 'changebillid', 		width:110	,align: 'left' },
	            { display: '异动前状态', 		name: 'beforestate',  		width:80	,align: 'left' ,
                	editor: { type: 'select', data: cardStateData, valueField: 'choose'},
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("HYZT",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.beforestate)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
	            { display: '异动后状态',   	name: 'afterstate',  	width:80	,align: 'left' ,
                	editor: { type: 'select', data: cardStateData, valueField: 'choose'},
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("HYZT",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.afterstate)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
	            { display: '对应卡号',   		name: 'targetcardno',  	width:80	,align: 'center'}
	            ],  pageSize:20, 
                data: null,      
                width: '550',
                height:height-120,   
                enabledEdit: false,  checkbox:false,rownumbers:false,usePager:false
            });
            
            commoninfodivAccountchange=$("#commoninfodivAccountchange").ligerGrid({
                columns: [
                { display: '序号', 			name: 'changeseqno', 			width:40	,align: 'left' },
                { display: '门店编号', 		name: 'changecompid', 			width:50	,align: 'left' },
                { display: '门店名称', 		name: 'changecompname', 		width:60	,align: 'left' },
                { display: '单据类型', 		name: 'changebilltype', 		width:60	,align: 'left' },
                { display: '类型名称', 		name: 'changetype', 		width:100	,align: 'left',
                	editor: { type: 'select', data: accountChangeData, valueField: 'choose'},
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("ZHYD",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.changetype)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
                { display: '单据编号', 		name: 'changebillno', 		width:150	,align: 'left'},
	            { display: '单据日期', 		name: 'chagedate',  		width:80	,align: 'left'},
	            { display: '异动账户',   		name: 'changeaccounttype',  	width:80	,align: 'left',
                	editor: { type: 'select', data: accountData, valueField: 'choose'},
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("ZHLX",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.changeaccounttype)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
	            { display: '前次余额',   		name: 'changebeforeamt',  	width:60	,align: 'center'},
	            { display: '异动金额',   		name: 'changeamt',  	width:60	,align: 'center'},
	            { display: '余额',   		name: 'lastamt',  	width:60	,align: 'center'},
	            { display: '备注',   		name: 'changemark',  	width:160	,align: 'left'} 
	            ],  pageSize:20, 
                data: null,      
                width: '100%',
                height:height-120,
                enabledEdit: false,  checkbox:false,rownumbers:false,usePager:true,
                detail: { onShowDetail: showTranscationinfo}
            });
            
            //--赠送积分卡历史
            commoninfodivSendpointcard=$("#commoninfodivSendpointcard").ligerGrid({
                columns: [
                { display: '赠送门店', 		name: 'bsendcompid', 			width:60	,align: 'left' },
                { display: '赠送单号', 		name: 'bsendbillid', 			width:110	,align: 'left' },
                { display: '赠送日期', 		name: 'senddate', 				width:80	,align: 'left' },
                { display: '原始单号', 		name: 'sourcebillid', 			width:110	,align: 'left'},
	            { display: '原单类型', 		name: 'sendtypeText',  			width:80	,align: 'left'},
	            { display: '原单金额',   		name: 'sourceamt',  			width:60	,align: 'left'},
	           
	            { display: '赠送金额',   		name: 'sendamt',  				width:60	,align: 'center'},
	            { display: '条码卡号',   		name: 'picno',  				width:80	,align: 'center'},
	            { display: '日历券号',   		name: 'firstdateno',  			width:80	,align: 'center'},
	            { display: '备注',   		name: 'sendmark',  				width:120	,align: 'right'}
	            ],  pageSize:20, 
                data: null,      
                width: '100%',
                  height:height-120,
                enabledEdit: false,  checkbox:false,rownumbers:false,usePager:false
            });
            
            commoninfodivCardson=$("#commoninfodivCardson").ligerGrid({
                columns: [
                { display: '卡号', 		name: 'cardno', 			width:120	,align: 'left' },
                { display: '销售日期', 		name: 'salecarddate', 				width:80	,align: 'left' },
                { display: '销售单号', 		name: 'salebillno', 			width:110	,align: 'left'},
	            { display: '销售金额', 		name: 'saleamt',  			width:60	,align: 'left'}
	            ],  pageSize:20, 
                data: null,      
                width: '420',
               height:height-120,
                enabledEdit: false,  checkbox:false,rownumbers:false,usePager:false
            });
            
            $("#CC005DetialTab").ligerTab();
            CC005DetialTab = $("#CC005DetialTab").ligerGetTabManager();
            var  cardStateData=JSON.parse(parent.loadCommonControlDate("HYZT",0));
            cardStatemanager = $("#cardstate").ligerComboBox({ data: cardStateData, isMultiSelect: false,valueFieldID: 'factCardstate',width:'140' });           	 
           	cardStatemanager.setDisabled(); 
           	$("#searchCurCardInfo").ligerButton(
	         {
	             text: '查询会员卡信息', width: 120,
		         click: function ()
		         {
		             searchRecord();
		         }
	        });
	      
	         
	       $("#initCurCardInfo").ligerToolBar({ items: [
      		    { text: '卡初始化', click: initCurCardInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/memeber.gif' },
      		    { line: true },
      		    { text: '新归属:&nbsp;<input type="text"  name="newHomeCompId" id="newHomeCompId" maxlength="20" style="width:60;background:#EDF1F8" onchange="validateCompId(this)"  />-<input type="text"  name="newHomeCompName" id="newHomeCompName" maxlength="20" readonly="true" style="width:80;background:#EDF1F8"  />' },
	            { text: '修改归属', click: editCardHomeComp, img: contextURL+'/common/ligerui/ligerUI/skins/icons/memeber.gif' },
      		    { line: true },
      		    { text: '新类型:&nbsp;<input type="text"  name="newCardTypeId" id="newCardTypeId" maxlength="20" style="width:60;background:#EDF1F8" onchange="validateCardTypeId(this)"  />-<input type="text"  name="newCardTypeName" id="newCardTypeName" maxlength="20" readonly="true" style="width:100;background:#EDF1F8"  />' },
	            { text: '修改类型', click: editCardType, img: contextURL+'/common/ligerui/ligerUI/skins/icons/memeber.gif' },
      		    { line: true },
      		    { text: '状态:&nbsp;<select name="newCardState" id="newCardState" style="width:120"></select>'},
      		    { text: '修改状态', click: editCardState, img: contextURL+'/common/ligerui/ligerUI/skins/icons/memeber.gif' }
            ]
            });
	         $("#readCurCardInfo").ligerButton(
	         {
	             text: '读取卡号', width: 80,
		         click: function ()
		         {
		             readCurCardInfo();
		         }
	         });
	         $("#initProjectInfo").ligerButton(
	         {
	             text: '初始洗剪吹价格', width:160,
		         click: function ()
		         {
		             initProjectInfo();
		         }
	         });
	         
           	$("#pageloading").hide();
           	var cardstates=parent.gainCommonInfoByCode("HYZT",0);
		    for(var i=0;i<cardstates.length;i++)
			{
				addOption(cardstates[i].bparentcodekey,cardstates[i].parentcodevalue,document.getElementById("newCardState"));
			}			 
           	loadCardinfo();
           	//加载十周年验证码
           	loadCode();
   		}catch(e){alert(e.message);}
    });
    
   
	function loadCode()
    {
        try{
        	//strCurCompId=note.data.id;
       		var params = "";				
       		var requestUrl ="cc005/loadCode.action"; 
			var responseMethod="loadCodeMessage";				
			sendRequestForParams_p(requestUrl,responseMethod,params );
          }catch(e){alert(e.message);}
    }
	
    function loadCodeMessage(request)
    {
    	try{
    		var responsetext = eval("(" + request.responseText + ")");
    		$("#searchMemberCode").val(responsetext.code);
   		}catch(e){alert(e.message);}
     }
	
	function loadCardinfo()
	{
		try{
			//strCurCompId=note.data.id;
			var params = "";				
			var requestUrl ="cc005/loadCardinfo.action"; 
			var responseMethod="loadCardinfoMessage";				
			sendRequestForParams_p(requestUrl,responseMethod,params );
		}catch(e){alert(e.message);}
	}
 
   function loadCardinfoMessage(request)
   {
       		try
        	{
 
        	var responsetext = eval("(" + request.responseText + ")");
    		if(responsetext.lsCardinfos!=null && responsetext.lsCardinfos.length>0)
	   		{
	   			commoninfodivsecond.options.data=$.extend(true, {},{Rows: responsetext.lsCardinfos,Total: responsetext.lsCardinfos.length});
            	commoninfodivsecond.loadData(true);            	
	   		}
	   		else
	   		{
	   			addcommoninfodivsecond();
	   		}
			if(commoninfodivsecond.rows.length>0)
			{
				commoninfodivsecond.select(0);
			}
	   		
	   		}catch(e){alert(e.message);}
    }
   function loadSelecProDetialData(data, rowindex, rowobj)
   {
   		document.getElementById("curChangeProNo").value=data.bprojectno;
   		document.getElementById("curChangeSeqnoNo").value=data.bproseqno;
   		document.getElementById("curChangeProName").value=data.bprojectname;
   		document.getElementById("curLastCount").value=data.lastcount;
   		document.getElementById("curLastAmt").value=data.lastamt;
   		document.getElementById("curChangeLastCount").value="";
   		document.getElementById("curChangeLastAmt").value="";
   		document.getElementById("curChangeSaler1").value="";
   		document.getElementById("curChangeSaler1name").value="";
   		document.getElementById("curChangeSaler2").value="";
   		document.getElementById("curChangeSaler2name").value=""; 
   			document.getElementById("curChangeSaler1Amt").value=0;
   			document.getElementById("curChangeSaler2Amt").value=0;   
       
   }
   
   function changeCurCount(obj)
   {
   		if(obj.value=="" || obj.value*1==0 )
   		{
   			document.getElementById("curChangeLastCount").value=0;
   			document.getElementById("curChangeLastAmt").value=0;
   		}
   		else if(obj.value*1>document.getElementById("curLastCount").value*1)
   		{
   			$.ligerDialog.error("兑换次数不能超过现有剩余疗程次数!");
   			document.getElementById("curChangeLastCount").value=0;
   			document.getElementById("curChangeLastAmt").value=0;
   			document.getElementById("curChangeLastCount").focus();
   			document.getElementById("curChangeLastCount").select();
		    return;
   		}
   		else
   		{
   			document.getElementById("curChangeLastAmt").value=ForDight(document.getElementById("curLastAmt").value*1/document.getElementById("curLastCount").value*1*obj.value*1,1);
   		}
   }
   
   function validateSaler1Amt(obj)
   {
   		if(obj.value*1>document.getElementById("curChangeLastAmt").value*1)
   		{
   			obj.value=0;
   			$.ligerDialog.error("分享金额不能超过兑换金额!");
   			 return;
   		}
   		if(document.getElementById("curChangeSaler2").value!="")
   		{
   			document.getElementById("curChangeSaler2Amt").value=obj.value*1;
   		}
   }
   
    function validateFirstEmp(obj)
    {
    	if(obj.value=="")
    	{
    		document.getElementById("curChangeSaler1name").value="";
    		return;
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
			 					document.getElementById("curChangeSaler1name").value=parent.StaffInfo[i].staffname;
			 					vflag=1;
			 					break;
			 				}
						}
					}
					if(vflag==0)
					{
						obj.value="";
						document.getElementById("curChangeSaler1name").value="";
						$.ligerDialog.error("该员工编号不存在,请确认！");
					}
    	}
    }
    function validateSecondEmp(obj) 
    {
    	if(obj.value=="")
    	{
    		document.getElementById("curChangeSaler2name").value="";
    		document.getElementById("curChangeSaler2Amt").value=0;
    		return;
    	}
    	else
    	{
    				var vflag=0;
					if(parent.StaffInfo!=null && parent.StaffInfo.length>0)
					{
						for(var i=0;i<parent.StaffInfo.length;i++)
						{
							if(parent.StaffInfo[i].bstaffno==obj.value && parent.StaffInfo[i].department=="006")
			 				{
			 					document.getElementById("curChangeSaler2name").value=parent.StaffInfo[i].staffname;
			 					document.getElementById("curChangeSaler2Amt").value=document.getElementById("curChangeSaler1Amt").value;
			 					vflag=1;
			 					break;
			 				}
						}
					}
					if(vflag==0)
					{
						obj.value="";
						document.getElementById("curChangeSaler2name").value="";
						document.getElementById("curChangeSaler2Amt").value=0;
						$.ligerDialog.error("该员工编号不存在或不是烫染师,请确认！");
					}
    	}
    }
    
   function loadSelecDetialData(data, rowindex, rowobj)
   {
        try{
        		 strCurCompId=data.bcardvesting;
        		 strCurCardNo=data.bcardno;
       			 var params = "strCurCompId="+strCurCompId;
       			 params =params+ "&strCurCardNo="+strCurCardNo;
       		     params=params+"&searcharDataType="+document.getElementById("searcharDataType").value;
       		 
        		 var myAjax = new parent.Ajax.Request(
						"cc005/loadCurCardInfo.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {							
								var action = eval( "("+request.responseText+")");
								document.getElementById("cardvesting").value=checkNull(action.curCardinfo.bcardvesting);
							
								document.getElementById("cardno").value=checkNull(action.curCardinfo.bcardno);
								document.getElementById("cardtype").value=checkNull(action.curCardinfo.cardtype);
								document.getElementById("cardtypeName").value=checkNull(action.curCardinfo.cardtypeName);
								//document.getElementById("cardsource").value=checkNull(action.curCardinfo.cardsource);
								handleRadio("curCardinfo.cardsource",checkNull(action.curCardinfo.cardsource));
								document.getElementById("memberphone").value=checkNull(action.curCardinfo.memberphone);
								document.getElementById("membername").value=checkNull(action.curCardinfo.membername);
								document.getElementById("memberpcid").value=checkNull(action.curCardinfo.memberpcid);
								document.getElementById("costamtbydebts").value=checkNull(action.curCardinfo.costamtbydebts);
								document.getElementById("costcountbydebts").value=checkNull(action.curCardinfo.costcountbydebts);
								document.getElementById("salecarddate").value=checkNull(action.curCardinfo.salecarddate);
								document.getElementById("cutoffdate").value=checkNull(action.curCardinfo.cutoffdate);
								cardStatemanager.selectValue(checkNull(action.curCardinfo.cardstate));
								if(action.lsCardaccount!=null && action.lsCardaccount.length>0)
						   		{
						   			commoninfodivAccount.options.data=$.extend(true, {},{Rows: action.lsCardaccount,Total: action.lsCardaccount.length});
					            	commoninfodivAccount.loadData(true);            	
						   		}
						   		else
						   		{
						   			commoninfodivAccount.options.data=$.extend(true, {},{Rows: null,Total: 0});
					            	commoninfodivAccount.loadData(true);   
						   		}
						   		if(action.lsCardproaccount!=null && action.lsCardproaccount.length>0)
						   		{
						   			commoninfodivProAccount.options.data=$.extend(true, {},{Rows: action.lsCardproaccount,Total: action.lsCardproaccount.length});
					            	commoninfodivProAccount.loadData(true);
					            	$("span.l-grid-group-togglebtn:eq(1)").click();
						   		}
						   		else
						   		{
						   			commoninfodivProAccount.options.data=$.extend(true, {},{Rows: null,Total: 0});
					            	commoninfodivProAccount.loadData(true);   
						   		}
						   		if(action.lsCardchangehistory!=null && action.lsCardchangehistory.length>0)
						   		{
						   			commoninfodivCardchange.options.data=$.extend(true, {},{Rows: action.lsCardchangehistory,Total: action.lsCardchangehistory.length});
					            	commoninfodivCardchange.loadData(true);            	
						   		}
						   		else
						   		{
						   			commoninfodivCardchange.options.data=$.extend(true, {},{Rows: null,Total: 0});
					            	commoninfodivCardchange.loadData(true);   
						   		}
						   		if(action.lsCardaccountchangehistory!=null && action.lsCardaccountchangehistory.length>0)
						   		{
						   			commoninfodivAccountchange.options.data=$.extend(true, {},{Rows: action.lsCardaccountchangehistory,Total: action.lsCardaccountchangehistory.length});
					            	commoninfodivAccountchange.loadData(true);            	
						   		}
						   		else
						   		{
						   			commoninfodivAccountchange.options.data=$.extend(true, {},{Rows: null,Total: 0});
					            	commoninfodivAccountchange.loadData(true);   
						   		}
						   		if(action.lsSendpointcard!=null && action.lsSendpointcard.length>0)
						   		{
						   			commoninfodivSendpointcard.options.data=$.extend(true, {},{Rows: action.lsSendpointcard,Total: action.lsSendpointcard.length});
					            	commoninfodivSendpointcard.loadData(true);            	
						   		}
						   		else
						   		{
						   			commoninfodivSendpointcard.options.data=$.extend(true, {},{Rows: null,Total: 0});
					            	commoninfodivSendpointcard.loadData(true);   
						   		}
						   		if(action.lsCardsoninfo!=null && action.lsCardsoninfo.length>0)
						   		{
						   			commoninfodivCardson.options.data=$.extend(true, {},{Rows: action.lsCardsoninfo,Total: action.lsCardsoninfo.length});
					            	commoninfodivCardson.loadData(true);            	
						   		}
						   		else
						   		{
						   			commoninfodivCardson.options.data=$.extend(true, {},{Rows: null,Total: 0});
					            	commoninfodivCardson.loadData(true);   
						   		}
						   		if(action.curCardspecialcost!=null)
						   		{
						   			document.getElementById("costxc1").value=checkNull(action.curCardspecialcost.costxc1);
						   			document.getElementById("costxc2").value=checkNull(action.curCardspecialcost.costxc2);
						   			document.getElementById("costxc3").value=checkNull(action.curCardspecialcost.costxc3);
						   			document.getElementById("costxc4").value=checkNull(action.curCardspecialcost.costxc4);
						   			document.getElementById("costxc5").value=checkNull(action.curCardspecialcost.costxc5);
						   			document.getElementById("costxc6").value=checkNull(action.curCardspecialcost.costxc6);
						   			document.getElementById("costxc7").value=checkNull(action.curCardspecialcost.costxc7);
						   			document.getElementById("costxc8").value=checkNull(action.curCardspecialcost.costxc8);
						   			document.getElementById("costxc9").value=checkNull(action.curCardspecialcost.costxc9);
						   		}
						   		else
						   		{
						   			document.getElementById("costxc1").value=0;
						   			document.getElementById("costxc2").value=0;
						   			document.getElementById("costxc3").value=0;
						   			document.getElementById("costxc4").value=0;
						   			document.getElementById("costxc5").value=0;
						   			document.getElementById("costxc6").value=0;
						   			document.getElementById("costxc7").value=0;
						   			document.getElementById("costxc8").value=0;
						   			document.getElementById("costxc9").value=0;
						   		}
							},
							asynchronous:true
						});			  
          }catch(e){alert(e.message);}
   		}
   	
   		//--根据账户历史显示交易历史
   		function showTranscationinfo(row, detailPanel,callback)
		{
		
			f_detailPanel=detailPanel;
			f_callback=callback;
			
			loadDetialByAccountHistory(row.changebilltype,row.changebillno,row.changeaccounttype);
		}
		
		function loadDetialByAccountHistory(changebilltype,changebillno,changeaccounttype)
		{
			var requestUrl ="cc005/loadTransactionhistory.action"; 
			var responseMethod="loadTransactionhistoryMessage";		
			var param="strCurCardNo="+document.getElementById("cardno").value;		
			param=param+"&strBillType="+changebilltype;
			param=param+"&strBillNo="+changebillno;
			param=param+"&strAccountType="+changeaccounttype;
			param=param+"&searcharDataType="+document.getElementById("searcharDataType").value;
		
			sendRequestForParams_p(requestUrl,responseMethod,param );
		}
		
		function loadTransactionhistoryMessage(request)
	    {
	    	try
	    	{
	    	
	   		var responsetext = eval("(" + request.responseText + ")");
	   	
	   		if(responsetext.lsCardtransactionhistory!=null && responsetext.lsCardtransactionhistory.length>0)
	   		{
	   			var detailGrid = document.createElement('div'); 
	            $(f_detailPanel).append(detailGrid);
	            $(detailGrid).css('margin','10').ligerGrid({
	                columns:
	                            [
	                            { display: '类别', name: 'transactiontypeText', width: 70,type:'text' },
	                            { display: '代码', name: 'codeno' ,width: 100 },
	                            { display: '名称', name: 'codename' ,width: 130 },
	                            { display: '数量', name: 'ccount' ,width: 40 },
	                            { display: '金额', name: 'price' ,width: 60 },
	                            { display: '支付方式', name: 'paymode' ,width: 80 ,
				                	editor: { type: 'select', data: paymodeData, valueField: 'choose'},
					            	render: function (item)
					              	{
					              		var lsZw=parent.gainCommonInfoByCode("ZFFS",0);
					              		for(var i=0;i<lsZw.length;i++)
										{
											if(lsZw[i].bparentcodekey==item.paymode)
											{	
												return lsZw[i].parentcodevalue;								
										    }
										}
					                    return '';
					                } 
					            },
	                            { display: '第一销售', name: 'firstempid' ,width: 80 },
	                            { display: '第二销售', name: 'secondempid' ,width: 80 },
	                            { display: '第三销售', name: 'thirthempid' ,width: 80 }
	                            ], isScroll: false, showToggleColBtn: false, width: '90%', 
	                 data:{Rows: responsetext.lsCardtransactionhistory,Total: responsetext.lsCardtransactionhistory.length}
	                 , showTitle: false, rownumbers:true,columnWidth: 100
	                 , onAfterShowData: f_callback,frozen:false,usePager:false
	            });
	   		}
	   		}catch(e){alert(e.message)}
	   	}
	   	
     	function searchRecord()
        {
        	
        	var requestUrl ="cc005/searchCardinfos.action";
        	var searchMemberCompIdKey="";
		    var searchMemberNoKey=document.getElementById("searchMemberNoKey").value;
		   	var searchMemberNameKey=document.getElementById("searchMemberNameKey").value;
		    var searchMemberPhoneKey=document.getElementById("searchMemberPhoneKey").value;
		    var searchMemberPCIDKey=document.getElementById("searchMemberPCIDKey").value;
		    if( searchMemberNoKey=="" && searchMemberNameKey=="" && searchMemberPhoneKey=="" && searchMemberPCIDKey=="")
		    {
		    	 $.ligerDialog.warn("请输入查询信息!");
		    	 return;
		    }
        	var params="searchMemberCompIdKey="+searchMemberCompIdKey;
        	 params=params+"&searchMemberNoKey="+searchMemberNoKey;
        	 params=params+"&searchMemberNameKey="+searchMemberNameKey;
        	 params=params+"&searchMemberPhoneKey="+searchMemberPhoneKey;
        	 params=params+"&searchMemberPCIDKey="+searchMemberPCIDKey;
        	 params=params+"&searcharDataType="+document.getElementById("searcharDataType").value;
        	var responseMethod="searchMemberInfosMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params ); 	
            
           
        }
        function searchMemberInfosMessage(request)
        {
    		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	if(responsetext.lsCardinfos!=null && responsetext.lsCardinfos.length>0)
		   		{
		   			commoninfodivsecond.options.data=$.extend(true, {},{Rows: responsetext.lsCardinfos,Total: responsetext.lsCardinfos.length});
	            	commoninfodivsecond.loadData(true);   
	            	commoninfodivsecond.select(0);      	
		   		}
		   		else
		   		{
		   			 $.ligerDialog.warn("没有查询到相应会员信息!");
		   			commoninfodivsecond.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivsecond.loadData(true);   
		   		}
        	}
			catch(e)
			{
				alert(e.message);
			}
        }
        
        function initCurCardInfo()
        {
        	if(parent.hasFunctionRights( "CC005",  "UR_SPECIAL_CHECK")!=true)
	        {
	        	$.ligerDialog.warn("该用户没有初始化权限,请确认!");
	        	return;
	        }
        	$.ligerDialog.confirm('确认初始化 '+document.getElementById("cardno").value+' IC卡 ?', function (result)
			{
			    if( result==true)
	           	{
	           		var CardControl=parent.document.getElementById("CardCtrl");
					CardControl.Init(parent.commtype,parent.prot,parent.password1,parent.password2,parent.password3);
					CardControl.WriteCard(document.getElementById("cardno").value);
					$.ligerDialog.success("请在本界面读卡测试!");
	           	}
	        });
        }
        
        function readCurCardInfo()
        {
        	var CardControl=parent.document.getElementById("CardCtrl");
			CardControl.Init(parent.commtype,parent.prot,parent.password1,parent.password2,parent.password3);
			var cardNo=CardControl.ReadCard();
			if(cardNo!="")
			{
				document.getElementById("searchMemberNoKey").value=cardNo;
				searchRecord();
	    	}
        }
        
        function hotKeyOfSelf(key)
		{
			if(key==13)//回车
			{
				window.event.keyCode=9; //tab
				window.event.returnValue=true;
			}
		
		}
		
		function validateCompId(obj)
		{
			if(obj.value=="")
			{
				document.getElementById("newHomeCompName").value="";
				return ;
			}
			var requestUrl ="cc005/validateCompId.action";
			var params="newHomeCompId="+obj.value;
			var responseMethod="validateCompIdMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params ); 	
		}
		
		function validateCompIdMessage(request)
        {
    		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)!="")
	        	{
	        		$.ligerDialog.error(strMessage);
	        		document.getElementById("newHomeCompId").value="";
	        		document.getElementById("newHomeCompName").value="";
	        	}
	        	else
	        	{
	        		document.getElementById("newHomeCompName").value=responsetext.newHomeCompName;
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
        }
        
        function editCardHomeComp()
        {
        	if(document.getElementById("newHomeCompId").value=="")
        	{
        		$.ligerDialog.error("请先确认新归属门店");
        		return ;
        	}
        	if(document.getElementById("newHomeCompId").value==document.getElementById("cardvesting").value)
        	{
        		$.ligerDialog.error("新归属门店与原归属门店一致,不能修改!");
        		return ;
        	}
        	
        	var requestUrl ="cc005/editCardHomeComp.action";
			var params="newHomeCompId="+document.getElementById("newHomeCompId").value;
			params=params+"&strCurCardNo="+document.getElementById("cardno").value;
			params=params+"&oldHomeCompId="+document.getElementById("cardvesting").value;
			var responseMethod="editCardHomeCompMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params ); 	
        }
        
        function editCardHomeCompMessage(request)
        {
    		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)!="")
	        	{
	        		$.ligerDialog.error(strMessage);
	        		
	        	}
	        	else
	        	{
	        		$.ligerDialog.success("操作成功!");
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
        }
        
        function validateCardTypeId(obj)
        {
        	if(obj.value=="")
        	{
        		document.getElementById("newCardTypeName").value="";
        		return ;
        	}
        	var requestUrl ="cc005/validateCardTypeId.action";
			var params="newCardTypeId="+obj.value;
			var responseMethod="validateCardTypeIdMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params );
        }
        
        function validateCardTypeIdMessage(request)
        {
    		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)!="")
	        	{
	        		$.ligerDialog.error(strMessage);
	        		document.getElementById("newCardTypeId").value="";
	        		document.getElementById("newCardTypeName").value="";
	        	}
	        	else
	        	{
	        		document.getElementById("newCardTypeName").value=responsetext.newCardTypeName;
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
        }
        
        function editCardState()
        {
        	var requestUrl ="cc005/editCardState.action";
			var params="newCardState="+document.getElementById("newCardState").value;
			params=params+"&strCurCardNo="+document.getElementById("cardno").value;
			var responseMethod="editCardStateMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params ); 
        }
        
        function editCardStateMessage(request)
        {
    		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)!="")
	        	{
	        		$.ligerDialog.error(strMessage);
	        		
	        	}
	        	else
	        	{
	        		$.ligerDialog.success("操作成功!");
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
        }
        function editCardType()
        {
       		if(document.getElementById("newCardTypeId").value=="")
        	{
        		$.ligerDialog.error("请先确认新的卡类型编号");
        		return ;
        	}
        	if(document.getElementById("newCardTypeId").value==document.getElementById("cardtype").value)
        	{
        		$.ligerDialog.error("新卡类型与卡类型一致,不能修改!");
        		return ;
        	}
        	
        	var requestUrl ="cc005/editCardType.action";
			var params="newCardTypeId="+document.getElementById("newCardTypeId").value;
			params=params+"&strCurCardNo="+document.getElementById("cardno").value;
			var responseMethod="editCardTypeMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params ); 	
        }
        
        function editCardTypeMessage(request)
        {
    		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)!="")
	        	{
	        		$.ligerDialog.error(strMessage);
	        		
	        	}
	        	else
	        	{
	        		$.ligerDialog.success("操作成功!");
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
        }
        
        function initProjectInfo()
        {
        	$.ligerDialog.confirm('确认初始化 ['+document.getElementById("cardno").value+'] 这张卡的洗吹|洗剪吹特价设置', function (result)
			{
			    if( result==true)
	           	{
	           		var requestUrl ="cc005/initProjectInfo.action";
	           		if(document.getElementById("costxc1").value*1==0
	           		&& document.getElementById("costxc2").value*1==0
	           		&& document.getElementById("costxc3").value*1==0
	           		&& document.getElementById("costxc4").value*1==0
	           		&& document.getElementById("costxc5").value*1==0
	           		&& document.getElementById("costxc6").value*1==0
	           		&& document.getElementById("costxc7").value*1==0
	           		&& document.getElementById("costxc8").value*1==0
	           		&& document.getElementById("costxc9").value*1==0)
	           		{
	           			$.ligerDialog.error("无特殊价格,不用设置!");
        				return ;
	           		}
	           		if(document.getElementById("cardtype").value!="C"
	           		&& document.getElementById("cardtype").value!="E"
	           		&& document.getElementById("cardtype").value!="ZK"
	           		&& document.getElementById("cardtype").value!="A2"
	           		&& document.getElementById("cardtype").value!="A4"
	           		&& document.getElementById("cardtype").value!="J"
	           		&& document.getElementById("cardtype").value!="B45" )
	           		{
	           			$.ligerDialog.error("非老卡会员,不能设置特殊价格!");
        				return ;
	           		}
					var params="costxc1="+document.getElementById("costxc1").value;
					params=params+"&costxc2="+document.getElementById("costxc2").value;
					params=params+"&costxc3="+document.getElementById("costxc3").value;
					params=params+"&costxc4="+document.getElementById("costxc4").value;
					params=params+"&costxc5="+document.getElementById("costxc5").value;
					params=params+"&costxc6="+document.getElementById("costxc6").value;
					params=params+"&costxc7="+document.getElementById("costxc7").value;
					params=params+"&costxc8="+document.getElementById("costxc8").value;
					params=params+"&costxc9="+document.getElementById("costxc9").value;
					params=params+"&strCurCardNo="+document.getElementById("cardno").value;
					params=params+"&cardvesting="+document.getElementById("cardvesting").value;
					var responseMethod="initProjectInfoMessage";	
					sendRequestForParams_p(requestUrl,responseMethod,params ); 
	           	}
	        });
        }
        
        function initProjectInfoMessage(request)
        {
    		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)!="")
	        	{
	        		$.ligerDialog.error(strMessage);
	        		
	        	}
	        	else
	        	{
	        		$.ligerDialog.success("初始操作成功!");
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
        }
        
        function addcommoninfodivsecond()
        {
        	var row = commoninfodivsecond.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivsecond.addRow({ 
				            }, row, false);
        }
        function changeOldProInfo()
        {
        	var requestUrl ="cc005/changeOldProInfo.action";
        	if(document.getElementById("cardno").value=="")
        	{
        		$.ligerDialog.error("请选择要兑换的卡号!");
        		return ;
        	}
        	if(document.getElementById("oldProItemNo").value=="")
        	{
        		$.ligerDialog.error("请选择要兑换的老疗程项目!");
        		return ;
        	}
        	if(document.getElementById("oldprocount").value*1==0)
        	{
        		$.ligerDialog.error("请选择要兑换的老疗程项目次数!");
        		return ;
        	}
        	if(document.getElementById("oldproamt").value*1==0)
        	{
        		$.ligerDialog.error("请选择要兑换的老疗程项目金额!");
        		return ;
        	}
        	var params="oldProItemNo="+document.getElementById("oldProItemNo").value;
			params=params+"&oldprocount="+document.getElementById("oldprocount").value;
			params=params+"&oldproamt="+document.getElementById("oldproamt").value;
			params=params+"&oldpromark="+document.getElementById("oldpromark").value;
			params=params+"&strCurCardNo="+document.getElementById("cardno").value;
			params=params+"&cardvesting="+document.getElementById("cardvesting").value;
			var responseMethod="changeOldProInfoMessage";	
			sendRequestForParams_p(requestUrl,responseMethod,params ); 
        }
        
        function changeOldProInfoMessage(request)
        {
    		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)!="")
	        	{
	        		$.ligerDialog.error(strMessage);
	        		
	        	}
	        	else
	        	{
	        		$.ligerDialog.success("操作成功,请重新读卡!");
	        		document.getElementById("oldProItemNo").value="";
	        		document.getElementById("oldprocount").value="";
	        		document.getElementById("oldproamt").value="";
	        		document.getElementById("oldpromark").value="";
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
        }
        
        function changeProAccountinfo()
        {
        	  
   			   
        	if(document.getElementById("curChangeProNo").value=="")
        	{
        		$.ligerDialog.error("请确认兑换的疗程!");
        		return ;
        	}
        	if(document.getElementById("curChangeLastCount").value*1==0)
        	{
        		$.ligerDialog.error("请确认兑换的疗程次数!");
        		return ;
        	}
        	if(document.getElementById("curChangeLastCount").value*1==0)
        	{
        		$.ligerDialog.error("请确认兑换的疗程次数!");
        		return ;
        	}
        	if(document.getElementById("curChangeSaler1Amt").value*1>document.getElementById("curChangeLastAmt").value*1)
        	{
        		$.ligerDialog.error("分享业绩不能大于兑换金额!");
        		return ;
        	}
        	var requestUrl ="cc005/changeProAccountinfo.action";
        	var params="curChangeProNo="+document.getElementById("curChangeProNo").value;
			params=params+"&curChangeSeqnoNo="+document.getElementById("curChangeSeqnoNo").value;
			params=params+"&curChangeLastCount="+document.getElementById("curChangeLastCount").value;
			params=params+"&curChangeLastAmt="+document.getElementById("curChangeLastAmt").value;
			params=params+"&curChangeSaler1="+document.getElementById("curChangeSaler1").value;
			params=params+"&curChangeSaler1Amt="+document.getElementById("curChangeSaler1Amt").value;
			params=params+"&curChangeSaler2="+document.getElementById("curChangeSaler2").value;
			params=params+"&curChangeSaler2Amt="+document.getElementById("curChangeSaler2Amt").value;
			params=params+"&strCurCardNo="+document.getElementById("cardno").value;
			params=params+"&cardvesting="+document.getElementById("cardvesting").value;
			var responseMethod="changeProAccountinfoMessage";	
			sendRequestForParams_p(requestUrl,responseMethod,params ); 
        }
        
        function changeProAccountinfoMessage(request)
        {
    		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)!="")
	        	{
	        		$.ligerDialog.error(strMessage);
	        		
	        	}
	        	else
	        	{
	        		$.ligerDialog.success("操作成功,请重新读卡!");
	        			document.getElementById("curChangeProNo").value="";
				   		document.getElementById("curChangeSeqnoNo").value="";
				   		document.getElementById("curChangeProName").value="";
				   		document.getElementById("curLastCount").value="";
				   		document.getElementById("curLastAmt").value="";
				   		document.getElementById("curChangeLastCount").value="";
				   		document.getElementById("curChangeLastAmt").value="";
				   		document.getElementById("curChangeSaler1").value="";
				   		document.getElementById("curChangeSaler1name").value="";
				   		document.getElementById("curChangeSaler2").value="";
				   		document.getElementById("curChangeSaler2name").value=""; 
				   		document.getElementById("curChangeSaler1Amt").value=0;
				   		document.getElementById("curChangeSaler2Amt").value=0; 
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
        }
        
        function validateProItem(obj)
        {
        	if(document.getElementById("cardno").value=="")
        	{
        		$.ligerDialog.error("请选择要兑换的卡号!");
        		document.getElementById("oldProItemNo").value="";
        		return ;
        	}
        	if(obj.value=="W")
        	{
        		$.ligerDialog.prompt('请输入疗程编号','', function (yes,value) { if(yes) checkWritePro(value); });
		   	}
        }
        function checkWritePro(projectNo)
        {
        	var requestUrl ="cc005/validateProInfo.action";
        	var params="curChangeProNo="+projectNo;
        	params=params+"&cardvesting="+document.getElementById("cardvesting").value;
			var responseMethod="validateProInfoMessage";	
			sendRequestForParams_p(requestUrl,responseMethod,params ); 
        }
        
        function validateProInfoMessage(request)
        {
    		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)!="")
	        	{
	        		$.ligerDialog.error(strMessage);
	        		document.getElementById("oldProItemNo").value="";
	        	}
	        	else
	        	{
	        		  addOption(responsetext.curChangeProNo,responsetext.curChangeProName,document.getElementById("oldProItemNo"));
	        		  document.getElementById("oldProItemNo").value=responsetext.curChangeProNo;
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
        }
        
        function modifyCardBalance(accountType,acbalance,acdebts,accountTypeName)
        {
        	if(document.getElementById("cardno").value=="")
        	{
        		$.ligerDialog.error("请选择要更新的卡号!");
        		document.getElementById("oldProItemNo").value="";
        		return ;
        	}
        	if(parent.hasFunctionRights( "CC005",  "UR_SPECIAL_CHECK")!=true)
	        {
	        	$.ligerDialog.warn("该用户没有初更新权限,请确认!");
	        	return;
	        }
	        strCurCardNo=document.getElementById("cardno").value;
   			strAccountType=accountType;
   			strAccountTypeName=accountTypeName;
   			cardBalance=acbalance;
   			cardDebts=acdebts;
	        modifyDialogmanager=$.ligerDialog.open({ height:300,  url: contextURL+'/CardControl/CC005/modityCardInfo.jsp', width: 300, showMax: false, showToggle: false, showMin: false, isResize: false, title: '修改卡账户' });
        }
        
        function confirmModifyAccount(cardno,accounttype,oldBalance,newBalance,oldDebts,newDebts,changeMark)
        {
        	var requestUrl ="cc005/confirmModifyAccount.action";
        	var params="strCurCardNo="+cardno;
        	params=params+"&strAccountType="+accounttype;
        	params=params+"&oldAccountBalance="+oldBalance;
        	params=params+"&newAccountBalance="+newBalance;
        	params=params+"&oldAccountDebts="+oldDebts;
        	params=params+"&newAccountDebts="+newDebts;
        	params=params+"&strChangeMark="+changeMark; 
			var responseMethod="confirmModifyAccountMessage";	
			sendRequestForParams_p(requestUrl,responseMethod,params );
        }
        
         function confirmModifyAccountMessage(request)
        {
    		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)!="")
	        	{
	        		$.ligerDialog.error(strMessage);
	        	}
	        	else
	        	{
	        		$.ligerDialog.success("修改成功,请重查询!");
	        		modifyDialogmanager.close();
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
        }