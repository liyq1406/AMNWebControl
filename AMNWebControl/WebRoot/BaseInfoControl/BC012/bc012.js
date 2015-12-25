
   	var compTree = null;//主明细	
   	var compTreeManager = null;//主明细	
   	var commoninfodivsecond=null;//员工明细
   	var commoninfodivchilden=null;
   	var curCompid="";
   	var curStaffid="";
   	var staffPostionData=JSON.parse(parent.loadCommonControlDate_select("GZGW",0));
   	var staffPostionTitleData=JSON.parse(parent.loadCommonControlDate_select("GZZC",0));
 
   	var staffDepData=JSON.parse(parent.loadCommonControlDate_select("BMZL",0));
   	var staffYejiData=JSON.parse(parent.loadCommonControlDate_select("YJFS",0));
   	var staffChangeData=JSON.parse(parent.loadCommonControlDate_select("YDLX",0));
  	var chooseData = [{ choose: 1, text: '是' }, { choose: 0, text: '否'}];
   	var commoninfodivthirth=null;
   	var curStaffInfoDate=null;
   	var lsStaffhistory=null;
   	var lsStaffabsenceinfo=null;
   	var lsManagerShare=null;
   	var bc012layout=null;
   	var curRecord=null;
   	var comfirmButton = null;
   	var showDialogmanager=null;
   	var upDialogmanager=null;
   	var commoninfodivthirth=null;
   	var curdialog=null;
   	var lsBdDataSet=null;
   	var addBandDialog=null;
    $(function ()
   	{
	   try
	   {
	   	  //布局
            bc012layout= $("#bc012layout").ligerLayout({ leftWidth: 270,rightWidth: 140, bottomHeight:250, allowBottomResize: false, allowLeftResize: false, isRightCollapse:true ,isLeftCollapse:true});
            //门店树形结构
   			var height = $(".l-layout-center").height();
           
	   		//门店树形结构
   			compTree=$("#companyTree").ligerTree(
        	{
           		data: parent.complinkInfo,
           		idFieldName :'id',           		
           		onSelect: compSelect,
           		nodeWidth: 140,
           		checkbox: false,
           		height:'100%'
          	});
          	$("#dingweiButton").ligerButton(
		     {
		             text: '定位员工资料', width: 120,
			         click: function ()
			         {
			             f_searchStaffInfo();
			         }
		     });
		     
		     $("#searchFingerButton").ligerButton(
		     {
		             text: '获取员工考勤', width: 120,
			         click: function ()
			         {
			             //searchFingerButton();
			         }
		     });
		   
          	compTreeManager = $("#companyTree").ligerGetTreeManager();
        	commoninfodivsecond=$("#commoninfodivsecond").ligerGrid({
                columns: [
                { display: '工号', name: 'bstaffno',  width: 60, align: 'left' },
                { display: '姓名', name: 'staffname',  width: 60,align: 'left' },
                { display: '指纹号', name: 'fingerno',  width: 50,align: 'left' },
                { display: '部门', name: 'department',  width: 60,align: 'left' ,
                	editor: { type: 'select', data: staffDepData, valueField: 'choose',selectBoxHeight:'220'},
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
                { display: '职位', name: 'position',  width: 60,align: 'left' ,
                editor: { type: 'select', data: staffPostionData, valueField: 'choose',selectBoxHeight:'220'},
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
	            { display: '状态', name: 'curstate',  width:60,align: 'left'  , 
	                editor: { type: 'select', data: null, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.curstate == 1) return '未到职';
	                    if (item.curstate == 2) return '已到职';
	                      return '离职';
	                }
	            }
                ],  pageSize:25, 
                data:null,      
                width: 400,
                height:height-10,
                enabledEdit: false, 
                clickToEdit: false,  
                rownumbers:false,usePager: false, where : f_getWhere(), 	 
                onSelectRow : function (data, rowindex, rowobj)
                {
                	curRecord=data;
                	loadCurStaffinfo(data, rowindex, rowobj);
                } //,         
                //onDblClickRow : function (data, rowindex, rowobj)
                //{
                	//curRecord=data;
                    //loadSelecDetialData(data, rowindex, rowobj);
                //} 
            });
            
            
            commoninfodivchilden=$("#commoninfodivchilden").ligerGrid({
                columns: [
                { display: '工号', name: 'cstaffno',  width: 60, align: 'left' },
                { display: '姓名', name: 'staffname',  width: 60,align: 'left' },
                { display: '部门', name: 'department',  width: 80,align: 'left' ,
                	editor: { type: 'select', data: staffDepData, valueField: 'choose',selectBoxHeight:'220'},
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
	            }
                ],  pageSize:25, 
                data:null,      
                width: 400,
                height:height-10,
                enabledEdit: false, 
                clickToEdit: false,
                rownumbers:false,usePager: false
            });
            
            $("#onebartoolbar").ligerToolBar({items: [
      		    { text: '工号:&nbsp;<input type="text" id="strChildrenStaffNo" name="strChildrenStaffNo" style="width:80"/> '},
          		{ text: '添加&nbsp;<label id="lbdyAmt"></label>', click: addChildrenStaffInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/customers.gif'  }
	             
            ]
            });	
            
            if(parent.hasFunctionRights( "BC012",  "UR_SPECIAL_CHECK")!=true)
        	{
      			commoninfodivthirth=$("#commoninfodivthirth").ligerGrid({
	                columns: [
	                { display: '异动类型', 	name: 'changetype', width: 60 ,
	                	editor: { type: 'select', data: parent.staffChangeData, valueField: 'choose',selectBoxHeight:'220'},
		            	render: function (item)
		              	{
		              		var lsZw=parent.parent.gainCommonInfoByCode("YDLX",0);
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
	                { display: '单据编号', 	name: 'optionbill', width: 120 },
	                { display: '生效日期', 	name: 'effectivedate',  width: 70 },
	                { display: '原店号', 	name: 'oldcompid', 	width: 50 },
	                { display: '原工号', 	name: 'oldempid',  	width: 80 },
	                { display: '原部门', 		name: 'olddepid',  	width: 80 ,
	                	editor: { type: 'select', data: parent.staffDepData, valueField: 'choose',selectBoxHeight:'220'},
		            	render: function (item)
		              	{
		              		var lsZw=parent.parent.gainCommonInfoByCode("BMZL",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.olddepid)
								{	
									return lsZw[i].parentcodevalue;								
							    }
							}
		                    return '';
		                } 
	                },
	                { display: '原职位', 		name: 'oldpostion', width:80  ,
		                editor: { type: 'select', data: parent.staffPostionData, valueField: 'choose',selectBoxHeight:'220'},
			            	render: function (item)
			              	{
			              		var lsZw=parent.parent.gainCommonInfoByCode("GZGW",0);
			              		for(var i=0;i<lsZw.length;i++)
								{
									if(lsZw[i].bparentcodekey==item.oldpostion)
									{	
										return lsZw[i].parentcodevalue;								
								    }
								}
			                    return '';
			                } 
		            },
	                { display: '新店号', 	name: 'newcompid',  width: 50 },
	                { display: '新工号', 	name: 'newempid',  	width: 80 },
	                { display: '新部门',		name: 'newdepid',  	width: 80 ,
	                	editor: { type: 'select', data: parent.staffDepData, valueField: 'choose',selectBoxHeight:'220'},
		            	render: function (item)
		              	{
		              		var lsZw=parent.parent.gainCommonInfoByCode("BMZL",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.newdepid)
								{	
									return lsZw[i].parentcodevalue;								
							    }
							}
		                    return '';
		                } 
	                },
	                { display: '新职位', 		name: 'newpostion', width: 80  ,
		                editor: { type: 'select', data: parent.staffPostionData, valueField: 'choose',selectBoxHeight:'220'},
			            	render: function (item)
			              	{
			              		var lsZw=parent.parent.gainCommonInfoByCode("GZGW",0);
			              		for(var i=0;i<lsZw.length;i++)
								{
									if(lsZw[i].bparentcodekey==item.newpostion)
									{	
										return lsZw[i].parentcodevalue;								
								    }
								}
			                    return '';
			                } 
			         },
	                
	               
	                { display: '备注', 		name: 'changemark', width: 200 }
	                ],  pageSize:10, 
	                data: null,      
	                width: '100%',
	                height:'100%',
	                enabledEdit: false,  
	                rownumbers:false,usePager:false
           		 });
           	 }
           	 else
           	 {
           	 	commoninfodivthirth=$("#commoninfodivthirth").ligerGrid({
	                columns: [
	                { display: '异动类型', 	name: 'changetype', width: 60 ,
	                	editor: { type: 'select', data: parent.staffChangeData, valueField: 'choose',selectBoxHeight:'220'},
		            	render: function (item)
		              	{
		              		var lsZw=parent.parent.gainCommonInfoByCode("YDLX",0);
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
	                { display: '单据编号', 	name: 'optionbill', width: 100 },
	                { display: '生效日期', 	name: 'effectivedate',  width: 70 },
	                { display: '原店号', 	name: 'oldcompid', 	width: 50 },
	                { display: '原工号', 	name: 'oldempid',  	width: 50 },
	                { display: '原部门', 		name: 'olddepid',  	width: 60 ,
	                	editor: { type: 'select', data: parent.staffDepData, valueField: 'choose',selectBoxHeight:'220'},
		            	render: function (item)
		              	{
		              		var lsZw=parent.parent.gainCommonInfoByCode("BMZL",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.olddepid)
								{	
									return lsZw[i].parentcodevalue;								
							    }
							}
		                    return '';
		                } 
	                },
	                { display: '原职位', 		name: 'oldpostion', width: 70  ,
		                editor: { type: 'select', data: parent.staffPostionData, valueField: 'choose',selectBoxHeight:'220'},
			            	render: function (item)
			              	{
			              		var lsZw=parent.parent.gainCommonInfoByCode("GZGW",0);
			              		for(var i=0;i<lsZw.length;i++)
								{
									if(lsZw[i].bparentcodekey==item.oldpostion)
									{	
										return lsZw[i].parentcodevalue;								
								    }
								}
			                    return '';
			                } 
		            },
	                { display: '原工资', 		name: 'oldsalary',  width: 50 },
	                { display: '原方式', 		name: 'oldyjtype',  width: 80 ,
	                	editor: { type: 'select', data: parent.staffYejiData, valueField: 'choose',selectBoxHeight:'220'},
		            	render: function (item)
		              	{
		              		var lsZw=parent.parent.gainCommonInfoByCode("YJFS",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.oldyjtype)
								{	
									return lsZw[i].parentcodevalue;								
							    }
							}
		                    return '';
		             		} 
		             },
	                { display: '原系数',		name: 'oldyjrate',  width: 50 },
	                { display: '原基数',		name: 'oldyjamt',  width: 50 },
	                { display: '新店号', 	name: 'newcompid',  width: 50 },
	                { display: '新工号', 	name: 'newempid',  	width: 50 },
	                { display: '新部门',		name: 'newdepid',  	width: 60 ,
	                	editor: { type: 'select', data: parent.staffDepData, valueField: 'choose',selectBoxHeight:'220'},
		            	render: function (item)
		              	{
		              		var lsZw=parent.parent.gainCommonInfoByCode("BMZL",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.newdepid)
								{	
									return lsZw[i].parentcodevalue;								
							    }
							}
		                    return '';
		                } 
	                },
	                { display: '新职位', 		name: 'newpostion', width: 70  ,
		                editor: { type: 'select', data: parent.staffPostionData, valueField: 'choose',selectBoxHeight:'220'},
			            	render: function (item)
			              	{
			              		var lsZw=parent.parent.gainCommonInfoByCode("GZGW",0);
			              		for(var i=0;i<lsZw.length;i++)
								{
									if(lsZw[i].bparentcodekey==item.newpostion)
									{	
										return lsZw[i].parentcodevalue;								
								    }
								}
			                    return '';
			                } 
			         },
	                { display: '新工资',		name: 'newsalary',  width: 60 },
	                { display: '新方式',		name: 'newyjtype',  width: 80 ,
	                	editor: { type: 'select', data: parent.staffYejiData, valueField: 'choose',selectBoxHeight:'220'},
		            	render: function (item)
		              	{
		              		var lsZw=parent.parent.gainCommonInfoByCode("YJFS",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.newyjtype)
								{	
									return lsZw[i].parentcodevalue;								
							    }
							}
		                    return '';
		             		} 
		             },
	                { display: '新系数', 		name: 'newyjrate',  width: 50 },
	                { display: '新基数', 		name: 'newyjramt',  width: 50 },
	               
	                { display: '备注', 		name: 'changemark', width: 200 }
	                ],  pageSize:10, 
	                data: null,      
	                width: '100%',
	                height:'100%',
	                enabledEdit: false,  
	                rownumbers:false,usePager:false
           		 })
           	 }
           			
			 $("#optionBut1").ligerButton(
		     {
		             text: '派遣', width: 130,
			         click: function ()
			         {
			             itemclick_empInfoPQ();
			         }
		     });
		     
		      $("#optionBut2").ligerButton(
		     {
		             text: '入职', width: 130,
			         click: function ()
			         {
			             itemclick_empInfoRZ();
			         }
		     });
		     
		      $("#optionBut3").ligerButton(
		     {
		             text: '离职', width: 130,
			         click: function ()
			         {
			             itemclick_empInfoLZ();
			         }
		     });
		     
		      $("#optionBut4").ligerButton(
		     {
		             text: '本店调动', width: 130,
			         click: function ()
			         {
			             itemclick_empInfoADD();
			         }
		     });
		     
		      $("#optionBut5").ligerButton(
		     {
		             text: '薪资调整', width: 130,
			         click: function ()
			         {
			             itemclick_empInfoSalaryUp();
			         }
		     });
		     
		      $("#optionBut6").ligerButton(
		     {
		             text: '跨店调动', width: 130,
			         click: function ()
			         {
			             itemclick_empInfoBDD();
			         }
		     });
		     
		      $("#optionBut7").ligerButton(
		     {
		             text: '平级调动', width: 130,
			         click: function ()
			         {
			             itemclick_empInfoSamelevelChange();
			         }
		     });
		     
		      $("#optionBut8").ligerButton(
		     {
		             text: '重回公司', width: 130,
			         click: function ()
			         {
			             itemclick_empInfoCH();
			         }
		     });
		     
		      $("#optionBut9").ligerButton(
		     {
		             text: '指纹下发', width: 130,
			         click: function ()
			         {
			             itemclick_downFingerForPerson();
			         }
		     });
		     
		      $("#optionBut10").ligerButton(
		     {
		             text: '人脸下发', width: 130,
			         click: function ()
			         {
			             itemclick_downFactForPerson();
			         }
		     });
		     
		      $("#optionBut11").ligerButton(
		     {
		             text: '人脸数据拷贝', width: 130,
			         click: function ()
			         {
			             itemclick_backupFactForPerson();
			         }
		     });
		     
		      $("#optionBut12").ligerButton(
		     {
		             text: '设置区域经理底薪', width: 130,
			         click: function ()
			         {
			             itemclick_setManageShare();
			         }
		     });
		     
		 
		     
		   $("#toptoolbardetial").ligerToolBar({ items: [
      		     { text: '工号:&nbsp;<input type="text"  name="strStaffNo" id="strStaffNo" maxlength="20"  style="width:50;"  />&nbsp;姓名:&nbsp;<input type="text"  name="strStaffName" id="strStaffName" maxlength="20"  style="width:60;"  />&nbsp;身份证:&nbsp;<input type="text"  name="strPCID" id="strPCID" maxlength="20" style="width:110;" />&nbsp;&nbsp;内部工号:&nbsp;<input type="text"  name="strStaffInNo" id="strStaffInNo" maxlength="20" style="width:80;" />&nbsp;&nbsp;' },
	             { text: '查询&nbsp;<label id="lbdyAmt"></label>', click: searchStaffInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/search.gif'  },
	             { text: '保存&nbsp;<label id="lbdyAmt"></label>', click: editCurRecord, img: contextURL+'/common/ligerui/ligerUI/skins/icons/save.gif'  }
	               	 	
            ]
            });
	
		 
		     
           	clearOption("department");
          	var postiontitle=parent.gainCommonInfoByCode("BMZL",0);
          	for(var i=0;i<postiontitle.length;i++)
			{
				addOption(postiontitle[i].bparentcodekey,postiontitle[i].parentcodevalue,document.getElementById("department"));
			}	
			
			clearOption("position");
          	var postiontitle=parent.gainCommonInfoByCode("GZGW",0);
          	for(var i=0;i<postiontitle.length;i++)
			{
				addOption(postiontitle[i].bparentcodekey,postiontitle[i].parentcodevalue,document.getElementById("position"));
			}	
			
			clearOption("positiontitle");
          	var postiontitle=parent.gainCommonInfoByCode("GZZC",0);
          	for(var i=0;i<postiontitle.length;i++)
			{
				addOption(postiontitle[i].bparentcodekey,postiontitle[i].parentcodevalue,document.getElementById("positiontitle"));
			}	
			
			clearOption("resulttye");
          	var postiontitle=parent.gainCommonInfoByCode("YJFS",0);
          	for(var i=0;i<postiontitle.length;i++)
			{
				addOption(postiontitle[i].bparentcodekey,postiontitle[i].parentcodevalue,document.getElementById("resulttye"));
			}
			
			clearOption("socialsource");
			addOption("","",document.getElementById("socialsource"));
          	var sbgsdate=parent.gainCommonInfoByCode("SBGS",0);
          	for(var i=0;i<sbgsdate.length;i++)
			{
				addOption(sbgsdate[i].bparentcodekey,sbgsdate[i].parentcodevalue,document.getElementById("socialsource"));
			}	
			
			clearOption("absencesalary");
			addOption("","",document.getElementById("absencesalary"));
          	var qqdxdate=parent.gainCommonInfoByCode("QQDX",0);
          	for(var i=0;i<qqdxdate.length;i++)
			{
				addOption(qqdxdate[i].bparentcodekey,qqdxdate[i].parentcodevalue,document.getElementById("absencesalary"));
			}	
			clearOption("tichengmode");
			addOption("","",document.getElementById("tichengmode"));
          	var qqdxdate=parent.gainCommonInfoByCode("XZMS",0);
          	for(var i=0;i<qqdxdate.length;i++)
			{
				addOption(qqdxdate[i].bparentcodekey,qqdxdate[i].parentcodevalue,document.getElementById("tichengmode"));
			}
			$("#healthdate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'140' });
            $("#contractdate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'140' });
          	$("#pageloading").hide(); 
          	f_selectNode();
          	addStaffhistory();
          	if(parent.hasFunctionRights( "BC012",  "UR_SPECIAL_CHECK")==false)
        	{
        		initShowInfo(2);
        	}
        	else
        	{
        		initShowInfo(1);
        	}
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
        try{
        	curCompid=note.data.id;
       		var params = "strCurCompId="+note.data.id;				
     		var requestUrl ="bc012/loadCurStaffInfo.action"; 
			var responseMethod="loadSysuserinfoMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params );
          }catch(e){alert(e.message);}
    }
    function loadSysuserinfoMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.lsStaffinfo!=null && responsetext.lsStaffinfo.length>0)
	   		{
	   			curStaffInfoDate={Rows: responsetext.lsStaffinfo,Total: responsetext.lsStaffinfo.length};
	   			commoninfodivsecond.options.data=$.extend(true, {},curStaffInfoDate);
            	commoninfodivsecond.loadData(true);          
            	commoninfodivsecond.select(0);  	
	   		}
	   		else
	   		{
	   			commoninfodivsecond.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivsecond.loadData(true);      
	   		}
	   		
	   		
	   		//document.getElementById("sysParamSp103").value=responsetext.sysParamSp103;
	   	
	   		
	   	}catch(e){alert(e.message);}
    }
    
    function initShowInfo(ttype)
    {
    	if(ttype==2)//加密
    	{
    		document.getElementById("boxA").innerHTML="<input type=\"password\" name=\"curStaffinfo.basesalary\" id=\"basesalary\"  readonly=\"true\" style=\"width:140;background:#EDF1F8;\" />";
    		document.getElementById("boxB").innerHTML="<input type=\"password\" name=\"curStaffinfo.resultrate\" id=\"resultrate\"  readonly=\"true\" style=\"width:140;background:#EDF1F8;\" />";
    		document.getElementById("boxC").innerHTML="<input type=\"password\" name=\"curStaffinfo.baseresult\" id=\"baseresult\"  readonly=\"true\" style=\"width:140;background:#EDF1F8;\" />";
    		document.getElementById("boxD").innerHTML="<input type=\"password\" name=\"curStaffinfo.reservephone\" id=\"reservephone\"  readonly=\"true\" style=\"width:140;\" maxlength=\"12\" />";
    		document.getElementById("boxE").innerHTML="<input type=\"password\" name=\"curStaffinfo.pccid\" id=\"pccid\" readonly=\"true\" style=\"width:140;\"  maxlength=\"20\" />";
    		document.getElementById("boxF").innerHTML="<input type=\"password\" name=\"curStaffinfo.mobilephone\" id=\"mobilephone\"  readonly=\"true\" style=\"width:140;\" maxlength=\"12\"  />";
    		document.getElementById("boxG").innerHTML="<input type=\"password\" name=\"curStaffinfo.bankno\" id=\"bankno\"  readonly=\"true\" style=\"width:140;\"  maxlength=\"20\"  onchange=\"validateStaffBank(this)\"/>";
    		document.getElementById("boxH").innerHTML="<input type=\"password\" name=\"curStaffinfo.aaddress\" id=\"aaddress\" readonly=\"true\" style=\"width:550;\" maxlength=\"40\"   />";
    	}
    	else
    	{
    		document.getElementById("boxA").innerHTML="<input type=\"text\" name=\"curStaffinfo.basesalary\" id=\"basesalary\"  readonly=\"true\" style=\"width:140;background:#EDF1F8;\" />";
    		document.getElementById("boxB").innerHTML="<input type=\"text\" name=\"curStaffinfo.resultrate\" id=\"resultrate\"  readonly=\"true\" style=\"width:140;background:#EDF1F8;\" />";
    		document.getElementById("boxC").innerHTML="<input type=\"text\" name=\"curStaffinfo.baseresult\" id=\"baseresult\"  readonly=\"true\" style=\"width:140;background:#EDF1F8;\" />";
    		document.getElementById("boxD").innerHTML="<input type=\"text\" name=\"curStaffinfo.reservephone\" id=\"reservephone\"   style=\"width:140;\" maxlength=\"12\" />";
    		document.getElementById("boxE").innerHTML="<input type=\"text\" name=\"curStaffinfo.pccid\" id=\"pccid\"  style=\"width:140;\"  maxlength=\"20\" />";
    		document.getElementById("boxF").innerHTML="<input type=\"text\" name=\"curStaffinfo.mobilephone\" id=\"mobilephone\"   style=\"width:140;\" maxlength=\"12\"  />";
    		document.getElementById("boxG").innerHTML="<input type=\"text\" name=\"curStaffinfo.bankno\" id=\"bankno\"  style=\"width:140;\"  maxlength=\"20\"  onchange=\"validateStaffBank(this)\"/>";
    		document.getElementById("boxH").innerHTML="<input type=\"text\" name=\"curStaffinfo.aaddress\" id=\"aaddress\" style=\"width:550;\" maxlength=\"40\"   />";
    	}
    }
    
    function loadCurStaffinfo(data, rowindex, rowobj)
    {
    	curStaffid=data.bstaffno;
    	var params = "strCurCompId="+data.id.compno;			
    	params =params+ "&strCurStaffId="+curStaffid;		
     	var requestUrl ="bc012/loadCurStaff.action"; 
		var responseMethod="loadCurStaffinfoMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    function loadCurStaffinfoMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
    		if(responsetext.curStaffinfo!=null)
    		{
	   			document.getElementById("compno").value=checkNull(responsetext.curStaffinfo.id.compno);
	   			document.getElementById("bcompname").value=checkNull(responsetext.curStaffinfo.bcompname);
	   			document.getElementById("staffno").value=checkNull(responsetext.curStaffinfo.id.staffno);
	   			document.getElementById("manageno").value=checkNull(responsetext.curStaffinfo.manageno);
	   			document.getElementById("department").value=checkNull(responsetext.curStaffinfo.department);
	   			document.getElementById("position").value=checkNull(responsetext.curStaffinfo.position);
	   			document.getElementById("resulttye").value=checkNull(responsetext.curStaffinfo.resulttye);
	   			document.getElementById("resultrate").value=checkNull(responsetext.curStaffinfo.resultrate);
	   			document.getElementById("baseresult").value=checkNull(responsetext.curStaffinfo.baseresult);
	   			document.getElementById("staffname").value=checkNull(responsetext.curStaffinfo.staffname);
	   			document.getElementById("aaddress").value=checkNull(responsetext.curStaffinfo.aaddress);
	   			document.getElementById("mobilephone").value=checkNull(responsetext.curStaffinfo.mobilephone);
	   			document.getElementById("reservecontect").value=checkNull(responsetext.curStaffinfo.reservecontect);
	   			document.getElementById("reservephone").value=checkNull(responsetext.curStaffinfo.reservephone);
	   			document.getElementById("introductioner").value=checkNull(responsetext.curStaffinfo.introductioner);
	   			document.getElementById("healthno").value=checkNull(responsetext.curStaffinfo.healthno);
	   			document.getElementById("healthdate").value=checkNull(responsetext.curStaffinfo.healthdate);
	   			document.getElementById("banktype").value=checkNull(responsetext.curStaffinfo.banktype);
	   			document.getElementById("bankno").value=checkNull(responsetext.curStaffinfo.bankno);
	   			document.getElementById("fillno").value=checkNull(responsetext.curStaffinfo.fillno);
	   			document.getElementById("basesalary").value=checkNull(responsetext.curStaffinfo.basesalary);
	   			document.getElementById("remark").value=checkNull(responsetext.curStaffinfo.remark);
	   			document.getElementById("staffmark").value=checkNull(responsetext.curStaffinfo.staffmark);
	   			document.getElementById("pccid").value=checkNull(responsetext.curStaffinfo.pccid);
	   			document.getElementById("businessflag").value=checkNull(responsetext.curStaffinfo.businessflag);
	   			document.getElementById("socialsecurity").value=checkNull(responsetext.curStaffinfo.socialsecurity); 
	   			document.getElementById("contractdate").value=checkNull(responsetext.curStaffinfo.contractdate);
	   			document.getElementById("arrivaldate").value=checkNull(responsetext.curStaffinfo.arrivaldate); 
	   			document.getElementById("positiontitle").value=checkNull(responsetext.curStaffinfo.positiontitle);
	   			document.getElementById("socialsource").value=checkNull(responsetext.curStaffinfo.socialsource);
	   			document.getElementById("absencesalary").value=checkNull(responsetext.curStaffinfo.absencesalary); 
	   			document.getElementById("tichengmode").value=checkNull(responsetext.curStaffinfo.tichengmode); 
	   			document.getElementById("staffsex").value=checkNull(responsetext.curStaffinfo.staffsex);
	   			document.getElementById("mangerflag").value=checkNull(responsetext.curStaffinfo.mangerflag);
	   			document.getElementById("hairqualified").value=checkNull(responsetext.curStaffinfo.hairqualified);
	   			document.getElementById("trkcqualified").value=checkNull(responsetext.curStaffinfo.trkcqualified);
	   			document.getElementById("iscurr").value=checkNull(responsetext.curStaffinfo.iscurr);
	   			document.getElementById("ismoney").value=checkNull(responsetext.curStaffinfo.ismoney);
	   			
	   			if(checkNull(responsetext.curStaffinfo.iscurr)+""=="" || checkNull(responsetext.curStaffinfo.iscurr)+""=="0")
	   			{
	   				document.getElementById("iscurr").disabled=false;
	   			}
	   			else
	   			{
	   				document.getElementById("iscurr").disabled=true;
	   			}
	   			if(checkNull(responsetext.curStaffinfo.ismoney)+""=="")
	   			{
	   				document.getElementById("ismoney").disabled=false;
	   			}
	   			else
	   			{
	   				document.getElementById("ismoney").disabled=true;
	   			}
	   			//$("#ismoney").val(responsetext.curStaffinfo.ismoney);
	   			document.getElementById("displayname").value=checkNull(responsetext.curStaffinfo.displayname);
	   			document.getElementById("staffintroduction").value=checkNull(responsetext.curStaffinfo.staffintroduction);
	   		}
	   		if(responsetext.lsStaffhistory!=null && responsetext.lsStaffhistory.length>0)
	   		{
	 			commoninfodivthirth.options.data=$.extend(true, {},{Rows: responsetext.lsStaffhistory,Total: responsetext.lsStaffhistory.length});
            	commoninfodivthirth.loadData(true);          
            	commoninfodivthirth.select(0);  	
	   		}
	   		else
	   		{
	   			commoninfodivthirth.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivthirth.loadData(true);
            	addStaffhistory();      
	   		}
	   		if(responsetext.lsStaffinfomanger!=null && responsetext.lsStaffinfomanger.length>0)
	   		{
	   			commoninfodivchilden.options.data=$.extend(true, {},{Rows: responsetext.lsStaffinfomanger,Total: responsetext.lsStaffinfomanger.length});
            	commoninfodivchilden.loadData(true);          
            
	   		}
	   		else
	   		{
	   			commoninfodivchilden.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivchilden.loadData(true);   
            	addStaffChildren();   
	   		}
	   		document.getElementById("sysParamSp103").value=responsetext.sysParamSp103;
	   		lsBdDataSet=responsetext.lsBdDataSet;
	   	}catch(e){alert(e.message);}
    }
    function loadSelecDetialData(data, rowindex, rowobj)
    {
    	curStaffid=data.bstaffno;
    	var params = "strCurCompId="+data.id.compno;			
    	params =params+ "&strCurStaffId="+curStaffid;		
     	var requestUrl ="bc012/loadCurStaffHistoryInfo.action"; 
		var responseMethod="loadCurStaffHistoryInfoMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
    	
    }
    function loadCurStaffHistoryInfoMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
    		
	   		//lsStaffhistory=responsetext.lsStaffhistory;
	   		//f_openStaffhistory();
	   	}catch(e){alert(e.message);}
    }
     // ------------------------薪资调整 ----------------Start
     function itemclick_empInfoSalaryUp(item)
     {
     		 upDialogmanager=$.ligerDialog.open({ height: null, url: contextURL+'/BaseInfoControl/BC012/staffSalaryUp.jsp', width: 400, showMax: false, showToggle: false, showMin: false, isResize: false, title: '薪资调整' });
     }
     // ------------------------平级调整 ----------------Start
     function itemclick_empInfoSamelevelChange(item)
     {
     		 upDialogmanager=$.ligerDialog.open({ height: null, url: contextURL+'/BaseInfoControl/BC012/staffSameLevelChange.jsp', width: 400, showMax: false, showToggle: false, showMin: false, isResize: false, title: '平级跨店调动  当前门店  '+document.getElementById("compno").value+'  当前员工'+document.getElementById("staffno").value+' ['+document.getElementById("staffname").value+']' });
     }
     
     
    function itemclick_empInfoPQ(item)
    {
    	 shownewPayDialogmanager=$.ligerDialog.open({ height: null, url: contextURL+'/BaseInfoControl/BC012/staffDispatch.jsp', width: 300, showMax: false, showToggle: false, showMin: false, isResize: false, title: '员工派遣' });
    }
    
    function itemclick_setManageShare(item)
    {
    
    		var params = "strStaffInNo="+curRecord.manageno;		
     		var requestUrl ="bc012/loadManageShare.action"; 
			var responseMethod="loadManageShareMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
     function loadManageShareMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
    		lsManagerShare=responsetext.lsManagerShare;
	   		shownewPayDialogmanager=$.ligerDialog.open({ height: null, url: contextURL+'/BaseInfoControl/BC012/managerShare.jsp', width: 450, showMax: false, showToggle: false, showMin: false, isResize: false, title: '['+curRecord.staffname+']门店底薪设置' });
	   	}catch(e){alert(e.message);}
    }
    
    function itemclick_empInfoRZ(item)
    {
     	curdialog=$.ligerDialog.open({ height: '700', url: contextURL+'/BaseInfoControl/BC012/staff_rz.jsp', width: '550', showMax: true, showToggle: true, showMin: false,  title: '员工入职单' });
    	curdialog.collapse();
    }
    function itemclick_empInfoLZ(item)
    {
    	
    	if(curRecord.curstate!=2)
    	{
    		$.ligerDialog.error("当前员工 "+curRecord.staffname+" 员工状态不正确,请核实!");
    	}
    	else
    	{
    		curdialog=$.ligerDialog.open({ height: 700, url: contextURL+'/BaseInfoControl/BC012/staff_lz.jsp', width: 550, showMax: true, showToggle: true, showMin: false, isResize: true, title: '员工离职单' });
   			curdialog.collapse();
   		}
    }
    function itemclick_empInfoADD(item)
    {
    	if(curRecord.curstate!=2)
    	{
    		$.ligerDialog.error("当前员工 "+curRecord.staffname+" 员工状态不正确,请核实!");
    	}
    	else
    	{
    		curdialog=$.ligerDialog.open({ height: '700', url: contextURL+'/BaseInfoControl/BC012/staff_dd.jsp', width: 550, showMax: true, showToggle: true, showMin: false, title: '员工本店调动单' });
   			curdialog.collapse();
   		
   		}
    }
    function itemclick_empInfoBDD(item)
    {
    	if(curRecord.curstate!=2)
    	{
    		$.ligerDialog.error("当前员工 "+curRecord.staffname+" 员工状态不正确,请核实!");
    	}
    	else
    	{
    		curdialog=$.ligerDialog.open({ height: 700, url: contextURL+'/BaseInfoControl/BC012/staff_kdd.jsp', width: 550, showMax: true, showToggle: true, showMin: false, isResize: true, title: '员工跨店调动单' });
   			curdialog.collapse();
   		}
    }
    function itemclick_empInfoCH(item)
    {
     	if(curRecord.curstate!=3)
    	{
    		$.ligerDialog.error("当前员工 "+curRecord.staffname+" 员工状态不正确,请核实!");
    	}
    	else
    	{
    		curdialog=$.ligerDialog.open({ height: 700, url: contextURL+'/BaseInfoControl/BC012/staff_ch.jsp', width: 550, showMax: true, showToggle: true, showMin: false, isResize: true, title: '员工重回公司' });
    		curdialog.collapse();
    	}
    }
     // ------------------------操作用户 ----------------End
     // ------------------------查询信息 ----------------Start
     function f_searchStaffInfo()
     {
    
     	   commoninfodivsecond.options.data = $.extend(true, {}, curStaffInfoDate);
     	
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
             
               return (rowdata.bstaffno.indexOf(key) > -1 ||  rowdata.manageno.indexOf(key) > -1 );
              
                
            };
            return clause; 
        }
    	catch(e){alert(e.message);}
     }
     var wincount = 0;
     function f_openStaffhistory() {
        $.ligerDialog.open({ height: null, url: contextURL+'/BaseInfoControl/BC012/staffhistory.jsp', width: 1024, showMax: true, showToggle: true, showMin: true, isResize: true, modal: false, title: title() });
     }
     function title() {
            return '员工'+curStaffid+"异动历史" + (++wincount);
     }
     // ------------------------查询信息 ----------------End
     
     //入职申请回调
   	function postStaffRZMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("操作成功!");
	        	 comfirmButton.setDisabled();
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
    
     //离职申请回调
   	function postStaffLZMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("操作成功!");
	        	 comfirmButton.setDisabled();
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
    
    //本店调动回调
   	function postStaffDDMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("操作成功!");
	        	 comfirmButton.setDisabled();
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
    
    function postStaffCHMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("操作成功!");
	        	 comfirmButton.setDisabled();
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
    
    function postStaffCHessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("操作成功!");
	        	 comfirmButton.setDisabled();
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
    
     function dispatchStaffMessage(request)
	 {
	 			var responsetext = eval("(" + request.responseText + ")");
		        var strMessage=responsetext.strMessage;
		         if(checkNull(strMessage)=="")
		        {	        		 
		        	 $.ligerDialog.success("派遣成功!");
		        }
		        else
		        {
		        	$.ligerDialog.error(strMessage);
		        }
	}
	
	
     function postManagerShareInfoMessage(request)
	 {
	 			var responsetext = eval("(" + request.responseText + ")");
		        var strMessage=responsetext.strMessage;
		         if(checkNull(strMessage)=="")
		        {	        		 
		        	 $.ligerDialog.success("录入成功!");
		        }
		        else
		        {
		        	$.ligerDialog.error(strMessage);
		        }
	}
    
    
    	
     function deleteManageShareMessage(request)
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
	
     function dispatchSalaryUpMessage(request)
	 {
	 			var responsetext = eval("(" + request.responseText + ")");
		        var strMessage=responsetext.strMessage;
		         if(checkNull(strMessage)=="")
		        {	        		 
		        	 $.ligerDialog.success("设置成功!");
		        }
		        else
		        {
		        	$.ligerDialog.error(strMessage);
		        }
		        upDialogmanager.close();
	}
	
	 function changeBySameLevelMessage(request)
	 {
	 			var responsetext = eval("(" + request.responseText + ")");
		        var strMessage=responsetext.strMessage;
		         if(checkNull(strMessage)=="")
		        {	        		 
		        	 $.ligerDialog.success("调动成功!");
		        }
		        else
		        {
		        	$.ligerDialog.error(strMessage);
		        }
		        upDialogmanager.close();
	}
	
    function searchStaffInfo()
    {
    	var params ="strStaffNo="+document.getElementById("strStaffNo").value;
    	params=params+"&strStaffName="+document.getElementById("strStaffName").value;
    	params=params+"&strPCID="+document.getElementById("strPCID").value;
		params=params+"&strStaffInNo="+document.getElementById("strStaffInNo").value;
		params=params+"&strFingerId=0"; 
        var requestUrl ="bc012/loadDataSet.action"; 
		var responseMethod="loadDataSetMessage";		
		showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
    function loadDataSetMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
			showDialogmanager.close();
			if(responsetext.lsStaffinfo!=null && responsetext.lsStaffinfo.length>0)
           	{
            		commoninfodivsecond.options.data=$.extend(true, {},{Rows: responsetext.lsStaffinfo,Total: responsetext.lsStaffinfo.length});
            		commoninfodivsecond.loadData(true);
            		commoninfodivsecond.select(0);  
            }
			else
			{
				 	$.ligerDialog.warn("没有查到员工信息!");
		   			commoninfodivsecond.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivsecond.loadData(true);
	            	addStaff(); 
			}
	
	}
	
	function searchFingerButton()
	{
		var params = "strCurCompId="+curCompid;
        var requestUrl ="bc012/loadFingerDataSet.action"; 
		var responseMethod="loadFingerDataSetMessage";		
		showDialogmanager = $.ligerDialog.waitting('正在获取'+curCompid+'店的考勤信息中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
	}
	function loadFingerDataSetMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
			showDialogmanager.close();
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
	function addStaff()
	{
		var row = commoninfodivsecond.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivsecond.addRow({ 
				                bchangetype: 5,
				                billflag: 0
				             
				            }, row, false);
	}
	
	function addStaffhistory()
	{
		var row = commoninfodivthirth.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivthirth.addRow({
				            }, row, false);
	}
	
	
	function itemclick_empInfoFinger()
	{
	
    	var params = "strCurCompId="+curRecord.id.compno;			
    	params =params+ "&strCurFingerId="+curRecord.fingerno;	
		params =params+ "&strCurStaffName="+curRecord.staffname;	
     	var requestUrl ="bc012/handEmpInfoFinger.action"; 
		var responseMethod="handEmpInfoFingerMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
	}
	
	function handEmpInfoFingerMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("操作成功!");
	        	 comfirmButton.setDisabled();
	        }
	        else
	        {
	        	$.ligerDialog.error(strMessage);
	        }
	}
	
	
	function itemclick_downFingerForComp()
	{
		if(curRecord.id.compno=="001")
		{
			$.ligerDialog.warn("总部员工不需此操作!");
			return;
		}
		var params = "strCurCompId="+curRecord.id.compno;	
		params =params+ "&strCurStaffName="+curRecord.staffname;	
     	var requestUrl ="bc012/downFingerForComp.action"; 
		var responseMethod="downFingerForCompMessage";		
		showDialogmanager = $.ligerDialog.waitting('正在传送门店指纹,请勿此时操作系统...');
		sendRequestForParams_p(requestUrl,responseMethod,params );
	}
		
	function downFingerForCompMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("操作成功!");
	        	 comfirmButton.setDisabled();
	        }
	        else
	        {
	        	$.ligerDialog.error(strMessage);
	        }
	        showDialogmanager.close();
	}
	
	
	function itemclick_downFingerForPerson()
	{
		var params = "strCurCompId="+curRecord.id.compno;			
    	params =params+ "&strCurFingerId="+curRecord.fingerno;	
		params =params+ "&strCurStaffName="+curRecord.staffname;	
     	var requestUrl ="bc012/downFingerForPerson.action"; 
		var responseMethod="downFingerForPersonMessage";	
		showDialogmanager = $.ligerDialog.waitting('正在传送指纹,请勿此时操作系统...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
	}
	
	function downFingerForPersonMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("操作成功.");
	        }
	        else
	        {
	        	$.ligerDialog.error(strMessage);
	        }
	        showDialogmanager.close();
	}
	
	function itemclick_downFactForPerson()
	{
		var params = "strCurCompId="+curRecord.id.compno;			
    	params =params+ "&strCurFingerId="+curRecord.fingerno;	
		params =params+ "&strCurStaffName="+curRecord.staffname;	
     	var requestUrl ="bc012/downFaceForPerson.action"; 
		var responseMethod="downFaceForPersonMessage";	
		showDialogmanager = $.ligerDialog.waitting('正在传送人脸数据,请勿此时操作系统...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
	}
	
	function downFaceForPersonMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("操作成功.");
	        }
	        else
	        {
	        	$.ligerDialog.error(strMessage);
	        }
	        showDialogmanager.close();
	}
	
	function itemclick_backupFactForPerson()
	{
		addBandDialog=$.ligerDialog.open({ height: 600, url: contextURL+'/BaseInfoControl/BC012/handBandCompNo.jsp', width: 300, showMax: false, showToggle: false, showMin: false, isResize: false, title: '选择拷贝门店' });
		//$.ligerDialog.prompt('请输入需要拷贝的门店店号','', function (yes,value) { if(yes) copyFaceToOtherCompId(value) });
	}
	
	function copyFaceToOtherCompId(toCompId)
	{
		if(toCompId.length=="")
		{
			$.ligerDialog.error("请确认拷贝门店是否存在");
			return ;
		}
		addBandDialog.close();
		var params = "strCurCompId="+curRecord.id.compno;			
    	params =params+ "&strCurFingerId="+curRecord.fingerno;	
		params =params+ "&strStaffNo="+curRecord.bstaffno;	
		params =params+ "&strToCurCompId="+toCompId;	
		var requestUrl ="bc012/backupFactForPerson.action"; 
		var responseMethod="backupFactForPersonMessage";
		showDialogmanager = $.ligerDialog.waitting('正在传送人脸数据,请勿此时操作系统...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
	}
	
	function backupFactForPersonMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("拷贝成功.");
	        }
	        else
	        {
	        	$.ligerDialog.error(strMessage);
	        }
	        showDialogmanager.close();
	}
	
	itemclick_backupFactForPerson
	
	function editCurRecord()
	{
		var params = "curStaffinfo.id.compno="+document.getElementById("compno").value;			
    	params =params+ "&curStaffinfo.id.staffno="+document.getElementById("staffno").value;	
		params =params+ "&curStaffinfo.aaddress="+document.getElementById("aaddress").value;
		params =params+ "&curStaffinfo.mobilephone="+document.getElementById("mobilephone").value;
		params =params+ "&curStaffinfo.reservecontect="+document.getElementById("reservecontect").value;
		params =params+ "&curStaffinfo.reservephone="+document.getElementById("reservephone").value;
		params =params+ "&curStaffinfo.introductioner="+document.getElementById("introductioner").value;
		params =params+ "&curStaffinfo.healthno="+document.getElementById("healthno").value;
		params =params+ "&curStaffinfo.healthdate="+document.getElementById("healthdate").value;
		params =params+ "&curStaffinfo.banktype="+document.getElementById("banktype").value;
		params =params+ "&curStaffinfo.bankno="+document.getElementById("bankno").value;
		params =params+ "&curStaffinfo.fillno="+document.getElementById("fillno").value;
		params =params+ "&curStaffinfo.remark="+document.getElementById("remark").value;	
		params =params+ "&curStaffinfo.staffmark="+document.getElementById("staffmark").value;	
		params =params+ "&curStaffinfo.pccid="+document.getElementById("pccid").value;
		params =params+ "&curStaffinfo.businessflag="+document.getElementById("businessflag").value;
		params =params+ "&curStaffinfo.socialsecurity="+document.getElementById("socialsecurity").value;
		params =params+ "&curStaffinfo.contractdate="+document.getElementById("contractdate").value;
		params =params+ "&curStaffinfo.positiontitle="+document.getElementById("positiontitle").value;
		params =params+ "&curStaffinfo.socialsource="+document.getElementById("socialsource").value;
		params =params+ "&curStaffinfo.absencesalary="+document.getElementById("absencesalary").value; 
		params =params+ "&curStaffinfo.tichengmode="+document.getElementById("tichengmode").value; 
		params =params+ "&curStaffinfo.staffsex="+document.getElementById("staffsex").value;
		params =params+ "&curStaffinfo.mangerflag="+document.getElementById("mangerflag").value;
		//params =params+ "&curStaffinfo.hairqualified="+document.getElementById("hairqualified").value;
		params =params+ "&curStaffinfo.displayname="+document.getElementById("displayname").value;
		params =params+ "&curStaffinfo.staffintroduction="+document.getElementById("staffintroduction").value;
		params= params+"&curStaffinfo.iscurr="+document.getElementById("iscurr").value;
		params= params+"&curStaffinfo.ismoney="+document.getElementById("ismoney").value;
     	var requestUrl ="bc012/editCurRecord.action"; 
		var responseMethod="editCurRecordMessage";	
		sendRequestForParams_p(requestUrl,responseMethod,params );
	}
	
	function editCurRecordMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("更新成功!");
	        	 comfirmButton.setDisabled();
	        }
	        else
	        {
	        	$.ligerDialog.error(strMessage);
	        }
	        showDialogmanager.close();
	}
	
	

	
	function itemclick_shopInfoFinger()
	{
	}
	
	
	function resetCasherRoleAccount()
	{
		$.ligerDialog.confirm('确认设置当前员工为收银?', function (result)
		{
		    if( result==true)
           	{
				var params = "strCurCompId="+curCompid;			
    			params =params+ "&strCurStaffId="+curRecord.bstaffno;	
    			params =params+ "&strStaffInNo="+curRecord.manageno;	
     			var requestUrl ="bc012/resetCasherRoleAccount.action"; 
				var responseMethod="resetCasherRoleAccountMessage";	
				sendRequestForParams_p(requestUrl,responseMethod,params );
			}
		});  
	}
	
	function resetCasherRoleAccountMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("设置成功!");
	        	 comfirmButton.setDisabled();
	        }
	        else
	        {
	        	$.ligerDialog.error(strMessage);
	        }
	}
	
	function validateStaffBank(obj)
	{
		if(obj.value=="")
		{
			return;
		}
		if(obj.value.length!=16 && obj.value.length!=19)
		{
			$.ligerDialog.error("员工银行卡格式有误,请确认后重新输入!");
			obj.value="";
			return;
		}
		
		if(luhmCheck(obj.value)==false)
		{
			$.ligerDialog.error("员工银行卡格式有误,请确认后重新输入!");
			obj.value="";
		}
	}
	
	
	function bdcomfrimBillMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
		
			if(checkNull(responsetext.strMessage)!="")
           	{
            		$.ligerDialog.warn(responsetext.strMessage);
            }
			else
			{	
		   			$.ligerDialog.success("操作成功,请至薪资保底统计确认!");
		   			
			}
		
	}

	function bdcomfrimstopBillMessage(request)
	{
			var responsetext = eval("(" + request.responseText + ")");
			if(checkNull(responsetext.strMessage)!="")
           	{
            		$.ligerDialog.warn(responsetext.strMessage);
            }
			else
			{	
		   			$.ligerDialog.success("操作成功,请至薪资保底统计确认!");
		   		   
			}
			
	}
	
		function hotKeyOfSelf(key)
		{
			if(key==13)//回车
			{
				window.event.keyCode=9; //tab
				window.event.returnValue=true;
			}
			else if( key == 83 &&  event.altKey)
			{
				editCurRecord();
			}
	
		}
		
		//设置员工洗头是否合格
		function setStaffHairState(hariState)
		{
			var hariStateText="";
			if(hariState==0)
				hariStateText=" 合格";
			else 
				hariStateText=" 不合格";
			$.ligerDialog.confirm('确认设置当前员工洗头成绩 '+hariStateText, function (result)
			{
			    if( result==true)
	           	{
					var params = "strCurCompId="+document.getElementById("compno").value;	
	    			params =params+ "&strCurStaffId="+document.getElementById("staffno").value;
	    			params =params+ "&strHairState="+hariState;	
	     			var requestUrl ="bc012/resetStaffHiarState.action"; 
					var responseMethod="resetStaffHiarStateMessage";	
					sendRequestForParams_p(requestUrl,responseMethod,params );
				}
			});  
		}
		
		function resetStaffHiarStateMessage(request)
		{
				var responsetext = eval("(" + request.responseText + ")");
				if(checkNull(responsetext.strMessage)!="")
	           	{
	            		$.ligerDialog.error(responsetext.strMessage);
	            }
				else
				{	
			   			$.ligerDialog.success("设置成功!");
			   		    document.getElementById("hairqualified").value=responsetext.strHairState;
				}
				
		}
		
		
		//设置员工烫染课程是否合格
		function setStaffTrkcState(hariState)
		{
			var hariStateText="";
			if(hariState==0)
				hariStateText=" 合格";
			else 
				hariStateText=" 不合格";
			$.ligerDialog.confirm('确认设置当前员工烫染课程 '+hariStateText, function (result)
			{
			    if( result==true)
	           	{
					var params = "strCurCompId="+document.getElementById("compno").value;	
	    			params =params+ "&strCurStaffId="+document.getElementById("staffno").value;
	    			params =params+ "&strHairState="+hariState;	
	     			var requestUrl ="bc012/resetStaffTrkcState.action"; 
					var responseMethod="resetStaffTrkcStateMessage";	
					sendRequestForParams_p(requestUrl,responseMethod,params );
				}
			});  
		}
		
		function resetStaffTrkcStateMessage(request)
		{
				var responsetext = eval("(" + request.responseText + ")");
				if(checkNull(responsetext.strMessage)!="")
	           	{
	            		$.ligerDialog.error(responsetext.strMessage);
	            }
				else
				{	
			   			$.ligerDialog.success("设置成功!");
			   		    document.getElementById("trkcqualified").value=responsetext.strHairState;
				}
				
		}
		
		function addChildrenStaffInfo()
		{
			if(document.getElementById("strChildrenStaffNo").value=="")
			{
				$.ligerDialog.success("请输入要添加的员工编号!");
				return;
			}
			var params = "strCurCompId="+document.getElementById("compno").value;	
	    	params =params+ "&strCurStaffId="+document.getElementById("staffno").value;
	    	params =params+ "&strChildrenStaffNo="+document.getElementById("strChildrenStaffNo").value;	
	     	var requestUrl ="bc012/addChildrenStaffInfo.action"; 
			var responseMethod="addChildrenStaffInfoMessage";	
			sendRequestForParams_p(requestUrl,responseMethod,params );
		}
		
		
		function addStaffChildren()
		{
			var row = commoninfodivchilden.getSelectedRow();
					     //参数1:rowdata(非必填)
					    //参数2:插入的位置 Row Data 
					    //参数3:之前或者之后(非必填)
				commoninfodivchilden.addRow({ 
					                cstaffno: '',
					                staffname: '',
					             	department:''
					            }, row, false);
		}
	
	
		function addChildrenStaffInfoMessage(request)
		{
				var responsetext = eval("(" + request.responseText + ")");
				if(checkNull(responsetext.strMessage)!="")
	           	{
	            		$.ligerDialog.error(responsetext.strMessage);
	            }
				else
				{	
					var gridlen=commoninfodivchilden.rows.length*1;
					if(gridlen==0)
					{
						addStaffChildren();
						gridlen=gridlen+1;
					} 
					if(checkNull(commoninfodivchilden.getRow(0).cstaffno)!="")
					{
						addStaffChildren();
						gridlen=gridlen+1;
					}
					var curCDetialRecord=commoninfodivchilden.getRow(gridlen-1);
					commoninfodivchilden.updateRow(curCDetialRecord,{   cstaffno: responsetext.curStaffinfo.id.staffno
															  			,staffname : responsetext.curStaffinfo.staffname
															  			,department  : responsetext.curStaffinfo.department
															  
															   }); 
															   
			   		$.ligerDialog.success("添加成功!");
				}
				
		}