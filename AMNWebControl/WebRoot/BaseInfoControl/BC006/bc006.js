
   	var compTree = null;//主明细	
   	var compTreeManager = null;//主明细	
   	var commoninfodivsecond=null;//员工明细
   	var curCompid="";
   	var curStaffid="";
   	var projectXMLBData=JSON.parse(parent.loadCommonControlDate_select("XMLB",0));
   	var projectJGCWData=JSON.parse(parent.loadCommonControlDate_select("JGCW",0));
   	var projectXMTJData=JSON.parse(parent.loadCommonControlDate_select("XMTJ",0));
   	var chooseData = [{ choose: 1, text: '启' }, { choose: 2, text: '禁'}];
   	var curProjectInfoDate=null;
   	var bc006layout=null;
    $(function ()
   	{
	   try
	   {
	   		  //布局
            bc006layout= $("#bc006layout").ligerLayout({ leftWidth: 270, allowBottomResize: false, allowLeftResize: false, isLeftCollapse:true });
             var height = $(".l-layout-center").height();
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
          	compTreeManager = $("#companyTree").ligerGetTreeManager();
          	
        	commoninfodivsecond=$("#commoninfodivsecond").ligerGrid({
                columns: [
                { display: '项目编号', name: 'bprjno',  width: 80,align: 'left' , frozen: true},
                { display: '项目名称', name: 'prjname', width:150,align: 'left' , frozen: true},
                { display: '项目大类', name: 'prjtype',  width: 60,align: 'left' ,
                	editor: { type: 'select', data: projectXMLBData, valueField: 'choose',selectBoxHeight:'220'},
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("XMLB",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.prjtype)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
                { display: '统计分类', name: 'prjreporttype',  width: 70,align: 'left' ,
                editor: { type: 'select', data: projectXMTJData, valueField: 'choose',selectBoxHeight:'220'},
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("XMTJ",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.prjreporttype)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
	            { display: '单位', 		name: 'saleunit',  width:30,align: 'left' },
	            { display: '洗头费', 		name: 'needhairflag',  width:40,align: 'left' , 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.needhairflag == 1) return '启';
	                      return '禁';
	                }
	            },
	            { display: '启用', 	name: 'useflag',  width:30,align: 'left' , 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.useflag == 1) return '启';
	                      return '禁';
	                }
	            },
	            { display: '启售', 	name: 'saleflag',  width:30,align: 'left' , 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.saleflag == 1) return '启';
	                      return '禁';
	                }
	            },
	            { display: '打折', 	name: 'rateflag',  width:30,align: 'left' , 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.rateflag == 1) return '启';
	                      return '禁';
	                }
	            },
	            { display: '价格编辑', 	name: 'editflag',  width:50,align: 'left' , 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.editflag == 1) return '启';
	                      return '禁';
	                }
	            },
	            { display: '标准价', name: 'saleprice', width:50,align: 'left' },
	            { display: '单次体验价', name: 'onecountprice', width:70,align: 'left' },
	            { display: '疗程次数', name: 'ysalecount', width:60,align: 'left' },
	            { display: '疗程价', name: 'ysaleprice', width:50,align: 'left' },
	            { display: '疗程体验价', name: 'onepageprice', width:70,align: 'left' },
	            { display: '成本比率', name: 'costprice', width:60,align: 'left' },
	            { display: '普通业绩', name: 'kyjrate', width:60,align: 'left' },
	            { display: '普通提成', name: 'ktcrate', width:60,align: 'left' },
	            { display: '疗程业绩', name: 'lyjrate', width:60,align: 'left' },
	            { display: '疗程提成', name: 'ltcrate', width:60,align: 'left' },
	            { display: '新客提点', name: 'newcosttc', width:60,align: 'left' },
	            { display: '老客提点', name: 'oldcosttc', width:60,align: 'left' },
	            { display: 'IPAD显示名称', name: 'ipadname', width:80,align: 'left' }
                ],  pageSize:20, 
                data:null,      
                width: bc006layout.centerWidth*1+bc006layout.leftWidth*1-30,
                height:'95%',
                clickToEdit: false,   enabledEdit: false,  
                rownumbers:true,usePager:true, where : f_getWhere(),     
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                }, 
                toolbar: { items: [
                { text: 'Excel导出', click: itemclick_projectInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/attibutes.gif' }]
                }   
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
        try{
        	curCompid=note.data.id;
       		var params = "strCurCompId="+note.data.id;				
     		var requestUrl ="bc006/loadPrjInfos.action"; 
			var responseMethod="loadPrjInfosMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params );
          }catch(e){alert(e.message);}
    }
    function loadPrjInfosMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.lsProjectinfoInfo!=null && responsetext.lsProjectinfoInfo.length>0)
	   		{
	   			curProjectInfoDate={Rows: responsetext.lsProjectinfoInfo,Total: responsetext.lsProjectinfoInfo.length};
	   			commoninfodivsecond.options.data=$.extend(true, {},curProjectInfoDate);
            	commoninfodivsecond.loadData(true);            	
	   		}
	   		else
	   		{
	   			commoninfodivsecond.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivsecond.loadData(true);      
	   		}
	   		
	   	}catch(e){alert(e.message);}
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
     // ------------------------查询信息 ----------------End