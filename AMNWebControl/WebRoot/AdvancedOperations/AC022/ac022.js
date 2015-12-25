var commoninfodivdetial=null;
var curDetialRecord=null;
lsProjectinfo=null;
$(function ()
{
   try
   {
	   $.extend($.ligerui.controls.ComboBox.prototype, {
			_setUrl: function ()
			{

			},
			_setAutocomplete: function (value)
			{
				var g = this, p = this.options;
				if (!value) return;
				g.inputText.removeAttr("readonly");
				g.lastInputText = g.inputText.val();
				g.inputText.keyup(function (event)
				{
					if (event.keyCode == 38 || event.keyCode == 40 || event.keyCode == 13) //up 、down、enter
					{
						return;
					}
					if (this._acto)
						clearTimeout(this._acto);
					this._acto = setTimeout(function ()
					{
						if (g.lastInputText == g.inputText.val()) return;
						p.initValue = "";
						g.valueField.val("");

						var currentKey = g.inputText.val();
						if (currentKey) currentKey = currentKey.replace(/(^\s*)|(\s*$)/g, "");
						if ($.isFunction(value))
						{
							value.call(g, {
								key: currentKey,
								show: function ()
								{
									g._selectBoxShow();
								}
							});
							return;
						}
						if (!p.autocompleteAllowEmpty && !currentKey)
						{
							g.clear();
							g.selectBox.hide();
							return;
						}
						if (p.url)
						{
							g.setParm('key', g.inputText.val());
							if (p.url == 'loadAutoProject')
								loadAutoProject(g, g.inputText.val());
						}
						g.lastInputText = g.inputText.val();
						this._acto = null;
					}, 300);
				});
			}
		});
        commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
            columns: [
            { display: '项目编号', 	name: 'ineritemno',  	width:150,align: 'left',
		 	 	editor: { type: 'select', data: null, url:'loadAutoProject', autocomplete: true, valueField: 'choose',onChanged : validateItem,selectBoxWidth:300,selectBoxHeight:300}
            },
            { display: '项目名称', 	name: 'ineritemname', 	width:280,align: 'left'},
            { display: '次数', 	name: 'entrycount', 		width:60,align: 'left' ,editor: { type: 'float' } }
            ],  pageSize:25, 
            data:{Rows: null,Total:0},      
            width: '540',
            height:'290',
            clickToEdit: true,   enabledEdit: true,  
            rownumbers: true,usePager: false  ,
            onSelectRow : function (data, rowindex, rowobj)
            {
                  curDetialRecord = data;
            }
        });
        
		function loadAutoProject(curmanager,curWriteitemname){
    		curmanager.setData(loadProjectControlDate_selectL(curWriteitemname));
    		curmanager.selectBox.show();
    		curitemManger=curmanager;
    	}
		 
	    function loadProjectControlDate_selectL(curWriteItemNo)
		{
			
		try{
			var returnValue='';
			var key='';
			var value='';
			var projectcount=0;
			for(var i=0;i<lsProjectinfo.length;i++)
			{
				if(projectcount*1==10)
				{
					break;
				}
				if(curWriteItemNo!="" 
				 && lsProjectinfo[i].id.prjno.indexOf(curWriteItemNo)==-1
				 && checkNull(lsProjectinfo[i].prjabridge).toLowerCase().indexOf(curWriteItemNo.toLowerCase())==-1
				 && checkNull(toPinyin(lsProjectinfo[i].prjname)).indexOf(curWriteItemNo.toUpperCase())==-1)
				{
					continue;
				}
			   	key = lsProjectinfo[i].id.prjno;
				value = lsProjectinfo[i].prjname;
				if(returnValue!='')
				{
					returnValue=returnValue+',';
				}
				else
				{
					returnValue=returnValue+'[';
				}
				
				projectcount=projectcount*1+1;
				returnValue=returnValue+'{"choose": "'+key+'","text": "'+key+'_'+value+'"}';
			}
			if(returnValue!='')
			{
				returnValue=returnValue+']';	
				return 	JSON.parse(returnValue);
			}
			else
			{	
				return null;
			}
			}catch(e){alert(e.message);}
		}
		
        function validateItem(obj){
       		if(obj.value=="")
       		{
       			commoninfodivdetial.updateRow(curDetialRecord,{ineritemno:'',ineritemname:'',entrycount:''}); 
       		}
       		else
       		{
       			var existscount=0;
       			for (var rowid in commoninfodivdetial.records)
    			{
    				var row =commoninfodivdetial.records[rowid];
    				if(obj.value==row.ineritemno)
    				{
    					existscount=existscount*1+1;
    				}
    			}  
    			
    			if(existscount>1)
    			{
    				commoninfodivdetial.updateRow(curDetialRecord,{ineritemno:'',ineritemname:'',entrycount:''}); 
    				$.ligerDialog.warn("该项目编号已经存在于此项目抵用券中!");
    			}else{
    				commoninfodivdetial.updateRow(curDetialRecord,{ineritemno:obj.value,ineritemname:obj.selected.text.split("_")[1],entrycount:1});
    			}
       		}
       	}
        
        compSelect();
        $("#handDyqOuterCardInfo").ligerButton({
     	   text: '登记微信券', width: 120,
     	   click: function ()
     	   {
     	      addWxCardInfo();
     	   }
     	});
	}catch(e){alert(e.message);}
});
  
function addDetialRecord(){
	var row = commoninfodivdetial.getSelectedRow();
     //参数1:rowdata(非必填)
    //参数2:插入的位置 Row Data 
    //参数3:之前或者之后(非必填)
	commoninfodivdetial.addRow({ 
        ineritemno: "",
        ineritemname: "",
        entrycount: "",
        entryamt: ""
    }, row, false);
}

//加载门店信息
function compSelect(){
    try{
   		var params = "strCurCompId=001";				
 		var requestUrl ="ac022/loadInfo.action"; 
		var responseMethod="loadInfoMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params);
      }catch(e){alert(e.message);}
}

function loadInfoMessage(request){
	try
    {
		var responsetext = eval("(" + request.responseText + ")");
		var returnValue='';
        commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total: 0});
        commoninfodivdetial.loadData(true);
        var i=0;
        while(i<8)
        {
        	addDetialRecord();
        	i++;
        }
        this.lsProjectinfo=responsetext.lsProjectinfo;
   	}
   	catch(e){alert(e.message);}
}

function addWxCardInfo(){
	if($("#cardId").val()=="")
	{
		 $.ligerDialog.warn("请输入cardId!");
		  return;
	}
	if($("#cardAmt").val()=="")
	{
		$.ligerDialog.warn("请输入面值!");
		return;
	}
	var curjosnparam="";
    var needReplaceStr="";
    var strJsonParam_detial="";
    //------卡号段列表
    for (var rowid in commoninfodivdetial.records)
	{
			var row =commoninfodivdetial.records[rowid];
			if(row.ineritemno==''){
				continue;
			}
			curjosnparam=JSON.stringify(row);
			if(strJsonParam_detial!=""){
			  	strJsonParam_detial=strJsonParam_detial+",";
			}
			strJsonParam_detial= strJsonParam_detial+curjosnparam;
	}   
    var params = "strCurCompId=001&cardId="+$("#cardId").val()+"&cardAmt="+$("#cardAmt").val()+"&type="+$("#type").val();
    if($("#type").val()=="0" && strJsonParam_detial!=""){
    	$.ligerDialog.warn("现金券不能配置项目！");
    	return;
    }
    if($("#type").val()=="1" && strJsonParam_detial==""){
    	$.ligerDialog.warn("项目券需配置项目！");
    	return;
    }
    params=params+"&strJsonParam=["+strJsonParam_detial+"]";
	try{
		var requestUrl ="ac022/addWxCard.action"; 
		var responseMethod="loadaddWxCardMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params);
	}catch(e){alert(e.message);}
}

function loadaddWxCardMessage(request){
	try
	{
    	var responsetext = eval('(' + request.responseText + ')');
    	var strMessage=responsetext.strMessage;
    	if(checkNull(strMessage)!="")
    	{
    		 $.ligerDialog.warn(strMessage);
    		 
    	}
    	else
    	{
    		 $.ligerDialog.warn("录入成功");
    	}
	}
	catch(e)
	{
		alert(e.message);
	}
}