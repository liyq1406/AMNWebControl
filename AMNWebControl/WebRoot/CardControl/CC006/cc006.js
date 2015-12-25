
   	var compTreeManager;
   	var compTree;
   	var commoninfodivsecond=null;//供应商列表
   	var strCurCompId="";
   	var strCurMemberId="";
   	var chooseSexData = [{ choose: 1, text: '男' }, { choose: 0, text: '女'}];
   	var cc006layout=null;
   	var fromValidate=null;
   	var membermodemanager = null;
    $(function ()
   	{
	   try
	   {
	   		  //布局
            cc006layout= $("#cc006layout").ligerLayout({ leftWidth: 270,rightWidth: 250,  allowBottomResize: false, allowLeftResize: false });
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
                { display: '门店编号', 		name: 'bmembervesting',  		width:60,align: 'center' , frozen: true},
                { display: '会员编号', 		name: 'bmemberno', 		width:100,align: 'left' , frozen: true},
	            { display: '会员姓名', 		name: 'membername',  	width:80,align: 'left' ,minWidth: 60},
	            { display: '性别', 			name: 'membersex', 		width:40,align: 'left'  , 
	                editor: { type: 'select', data: chooseSexData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.membersex == 1) return '男';
	                      return '女';
	                }
	            },
	            { display: '手机号码', 		name: 'membermphone', 		width:100,align: 'left' },
	            { display: '家庭地址', 		name: 'memberaddress', 		width:180,align: 'left' },
	            { display: '证件号码', 		name: 'memberpaperworkno', 		width:150,align: 'left' },
	            { display: '会员生日', 		name: 'memberbirthday',	width:80,align: 'left' }
                ],  pageSize:20, 
                data:null,      
                width: cc006layout.centerWidth*1+cc006layout.leftWidth*1+cc006layout.rightWidth*1-30,
                height:'98%',
                clickToEdit: false,   enabledEdit: false,  
                rownumbers:true,usePager:true, where : f_getWhere(),  
  
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                }	   
            });
            $.validator.addMethod(
                    "notnull",
                    function (value, element, regexp)
                    {
                        if (!value) return true;
                        return !$(element).hasClass("l-text-field-null");
                    },
                    "不能为空"
            );

            $.metadata.setType("attr", "validate");
            fromValidate = $("form").validate({
                //调试状态，不会提交数据的
                debug: true,
                errorPlacement: function (lable, element)
                {

                    if (element.hasClass("l-textarea"))
                    {
                        element.addClass("l-textarea-invalid");
                    }
                    else if (element.hasClass("l-text-field"))
                    {
                        element.parent().addClass("l-text-invalid");
                    }
                    $(element).removeAttr("title").ligerHideTip();
                    $(element).attr("title", lable.html()).ligerTip();
                },
                success: function (lable)
                {
                    var element = $("#" + lable.attr("for"));
                    if (element.hasClass("l-textarea"))
                    {
                        element.removeClass("l-textarea-invalid");
                    }
                    else if (element.hasClass("l-text-field"))
                    {
                        element.parent().removeClass("l-text-invalid");
                    }
                    $(element).removeAttr("title").ligerHideTip();
                },
                submitHandler: function ()
                {
                    alert("Submitted!");
                }
            });
          	$("#pageloading").hide(); 
          	$("#searchMember").ligerButton(
	         {
	             text: '查询会员', width: 80,
		         click: function ()
		         {
		             searchRecord();
		         }
	         });
	         
	         $("#editMember").ligerButton(
	         {
	             text: '更新会员资料', width: 180,
		         click: function ()
		         {
		             editCurRecord();
		         }
	         });
          	var  membermodeData=JSON.parse(parent.loadCommonControlDate("HYLB",0));
           	membermodemanager = $("#membertype").ligerComboBox({ data: membermodeData, isMultiSelect: false,valueFieldID: 'factMembertype',width:'140' });    
          	 
           	if(parent.hasFunctionRights( "CC006",  "UR_SPECIAL_CHECK")==true)
        	{
        		$("#memberbirthday").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'140' });  
           		document.getElementById("membername").style.display="block";
        		document.getElementById("membermphone").style.display="block";
        		document.getElementById("memberpaperworkno").style.display="block"; 
        		document.getElementById("lbmembername").style.display="none";
        		document.getElementById("lbmembermphone").style.display="none";
        		document.getElementById("lbmemberpaperworkno").style.display="none";
        		document.getElementById("lbmemberbirthday").style.display="none"; 
        		
        		document.getElementById("membername").readOnly="";
        		document.getElementById("membermphone").readOnly="";
        	}
        	else
        	{
        		document.getElementById("membername").style.display="none";
        		document.getElementById("membermphone").style.display="none";
        		document.getElementById("memberpaperworkno").style.display="none";
        		document.getElementById("memberbirthday").style.display="none";
        		document.getElementById("lbmembername").style.display="block";
        		document.getElementById("lbmembermphone").style.display="block";
        		document.getElementById("lbmemberpaperworkno").style.display="block";
        		document.getElementById("lbmemberbirthday").style.display="block"; 
        	}
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
        	strCurCompId=note.data.id;
       		var params = "strCurCompId="+note.data.id;				
       		var requestUrl ="cc006/loadMemberinfo.action"; 
			var responseMethod="loadMemberinfoMessage";				
			sendRequestForParams_p(requestUrl,responseMethod,params );
          }catch(e){alert(e.message);}
    }
 
   function loadMemberinfoMessage(request)
   {
       		try
        	{
 
        	var responsetext = eval("(" + request.responseText + ")");
    
	   		if(responsetext.lsMemberinfos!=null && responsetext.lsMemberinfos.length>0)
	   		{
	   			commoninfodivsecond.options.data=$.extend(true, {},{Rows: responsetext.lsMemberinfos,Total: responsetext.lsMemberinfos.length});
            	commoninfodivsecond.loadData(true);            	
	   		}
			if(commoninfodivsecond.rows.length>0)
			{
				commoninfodivsecond.select(0);
			}
	   		
	   		}catch(e){alert(e.message);}
    }
   
   function loadSelecDetialData(data, rowindex, rowobj)
   {
        try{
        		 strCurCompId=data.bmembervesting;
        		 strCurMemberId=data.bmemberno;
       			 var params = "strCurCompId="+strCurCompId;
       			 params =params+ "&strCurMemberId="+strCurMemberId;
				 var myAjax = new parent.Ajax.Request(
						"cc006/loadCurMember.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {							
								var action = eval( "("+request.responseText+")");	
								document.getElementById("membervesting").value=checkNull(action.curMemberinfo.id.membervesting);
								document.getElementById("memberno").value=checkNull(action.curMemberinfo.id.memberno);
								document.getElementById("membername").value=checkNull(action.curMemberinfo.membername);
								if(checkNull(action.curMemberinfo.membername)!="")
									document.getElementById("lbmembername").value=checkNull(action.curMemberinfo.membername).substring(0,1)+"**";
								else
									document.getElementById("lbmembername").value="";	
								document.getElementById("memberaddress").value=checkNull(action.curMemberinfo.memberaddress);
								document.getElementById("membertphone").value=checkNull(action.curMemberinfo.membertphone);
								document.getElementById("membermphone").value=checkNull(action.curMemberinfo.membermphone);
								if(checkNull(action.curMemberinfo.membermphone)!="")
									document.getElementById("lbmembermphone").value=checkNull(action.curMemberinfo.membermphone).substring(0,3)+"****"+checkNull(action.curMemberinfo.membermphone).substring(7,11);
								else
									document.getElementById("lbmembermphone").value="";
								document.getElementById("memberemail").value=checkNull(action.curMemberinfo.memberemail);
								document.getElementById("memberpaperworkno").value=checkNull(action.curMemberinfo.memberpaperworkno);
								if(checkNull(action.curMemberinfo.memberpaperworkno)!="" && checkNull(action.curMemberinfo.memberpaperworkno).length==15 )
									document.getElementById("lbmemberpaperworkno").value=checkNull(action.curMemberinfo.memberpaperworkno).substring(0,4)+"********"+checkNull(action.curMemberinfo.memberpaperworkno).substring(13,15);
								else if(checkNull(action.curMemberinfo.memberpaperworkno)!="" && checkNull(action.curMemberinfo.memberpaperworkno).length==18 )
									document.getElementById("lbmemberpaperworkno").value=checkNull(action.curMemberinfo.memberpaperworkno).substring(0,4)+"********"+checkNull(action.curMemberinfo.memberpaperworkno).substring(16,18);
								else
									document.getElementById("lbmemberpaperworkno").value="";
								
								document.getElementById("memberbirthday").value=checkNull(action.curMemberinfo.memberbirthday);
								
								if(checkNull(action.curMemberinfo.memberbirthday)!="")
									document.getElementById("lbmemberbirthday").value="****"+checkNull(action.curMemberinfo.memberbirthday).substring(4,10);
								else
									document.getElementById("lbmemberbirthday").value="";
									
								document.getElementById("memberjob").value=checkNull(action.curMemberinfo.memberjob);
								document.getElementById("memberqqno").value=checkNull(action.curMemberinfo.memberqqno);
								handleRadio("curMemberinfo.membersex",checkNull(action.curMemberinfo.membersex));
								if(checkNull(action.curMemberinfo.isSendMsg)=="")
								{
									handleRadio("curMemberinfo.isSendMsg",0);
								}
								else
								{
									handleRadio("curMemberinfo.isSendMsg",checkNull(action.curMemberinfo.isSendMsg));
								}
								membermodemanager.selectValue(checkNull(action.curMemberinfo.membertype));
								
								
								document.getElementById("curmembertype").value=checkNull(action.curMemberinfo.membertype);
							},
							asynchronous:true
						});			  
          }catch(e){alert(e.message);}
   }
   
   
     // ------------------------操作用户 ----------------End
     // ------------------------查询信息 ----------------Start
     function f_searchSupperInfo()
     {
    
     	   commoninfodivsecond.options.data = $.extend(true, {}, curSupperInfoDate);
     	
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
             
               	return (rowdata.supplierid.indexOf(key) > -1 ||  rowdata.suppliername.indexOf(key) > -1 );
              
                
            };
            return clause; 
        }
    	catch(e){alert(e.message);}
     }
    
     function itemclick_memberInfo(item)
     {
     	try
    	{
	    	 $.ligerDialog.confirm('确认  会员资料 '+item.text+' 操作', function (result)
		     {
		     		if( result==true)
            		{
			          	if(item.text=="查询")
			        	{
			        	 	searchRecord();
			        	}
			        	else if(item.text=="修改")
			        	{
			        	 	editCurRecord();
			        	} 	
			        	
			        }
		     });  
    	}
    	catch(e){alert(e.message);}
     }
  
     
     //保存
	 function editCurRecord()
        { 
        	if(parent.hasFunctionRights( "CC006",  "UR_POST")!=true)
        	{
        		 $.ligerDialog.warn("该用户没有修改权限,请确认!");
        		 return;
        	}

        	try
			{
				 if(fromValidate.element($("#membertphone"))==false ||
				 	fromValidate.element($("#membermphone"))==false ||
				 	fromValidate.element($("#memberaddress"))==false
				 )
				 {
				 	$.ligerDialog.error('填入信息有误,请确认!');
				 	return;
				 }
				document.getElementById("curmembertype").value=$("#factMembertype").val();		
				var queryStringTmp=$('#detailForm').serialize();//serialize('#detailForm');
				var requestUrl ="cc006/post.action";
				var params=queryStringTmp;
				var responseMethod="editMessage";
				sendRequestForParams_p(requestUrl,responseMethod,params ); 
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
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		 curSecondCommonKey="";
	        		 curSecondCommonValue="";
	        		  $.ligerDialog.success("更新成功!");
	        	
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
        
        function searchRecord()
        {
        	
        	var requestUrl ="cc006/searchMemberInfos.action";
        	var searchMemberCompIdKey=strCurCompId;
		    var searchMemberNoKey=document.getElementById("searchMemberNoKey").value;
		   	var searchMemberNameKey=document.getElementById("searchMemberNameKey").value;
		    var searchMemberPhoneKey=document.getElementById("searchMemberPhoneKey").value;
		    var searchMemberPCIDKey=document.getElementById("searchMemberPCIDKey").value;
		    if( searchMemberNoKey=="" && searchMemberNameKey=="" && searchMemberPhoneKey=="" && searchMemberPCIDKey=="")
		    {
		    	 $.ligerDialog.success("请输入查询信息!");
		    	 return;
		    }
        	var params="searchMemberCompIdKey="+searchMemberCompIdKey;
        	 params=params+"&searchMemberNoKey="+searchMemberNoKey;
        	 params=params+"&searchMemberNameKey="+searchMemberNameKey;
        	 params=params+"&searchMemberPhoneKey="+searchMemberPhoneKey;
        	 params=params+"&searchMemberPCIDKey="+searchMemberPCIDKey;
			var responseMethod="searchMemberInfosMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params ); 	
            
           
        }
        function searchMemberInfosMessage(request)
        {
    		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	if(responsetext.lsMemberinfos!=null && responsetext.lsMemberinfos.length>0)
		   		{
		   			commoninfodivsecond.options.data=$.extend(true, {},{Rows: responsetext.lsMemberinfos,Total: responsetext.lsMemberinfos.length});
	            	commoninfodivsecond.loadData(true);   
	            	commoninfodivsecond.select(0);      	
		   		}
		   		else
		   		{
		   			 $.ligerDialog.success("没有查询到相应会员信息!");
		   			commoninfodivsecond.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivsecond.loadData(true);   
		   		}
        	}
			catch(e)
			{
				alert(e.message);
			}
        }