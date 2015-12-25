var commoninfodivsecond=null;//图片明细
var bc012layout=null;
var showDialogmanager=null;
var images = null;
var reg = new RegExp('"',"g");  

$(function ()
{
   try
   {
   	  //布局
	   bc012layout= $("#bc012layout").ligerLayout({ leftWidth: 270,rightWidth: 140, bottomHeight:250, allowBottomResize: false, allowLeftResize: false, isRightCollapse:true ,isLeftCollapse:true});
        var height = $(window).height();
    	commoninfodivsecond=$("#commoninfodivsecond").ligerGrid({
            columns: [
            { display: '作品序号', name: 'id',  width: 200, align: 'left' ,
            	 render: function (item)
                {
                    return JSON.stringify(item.id);
                }
            },
            { display: '姓名', name: 'staffname',  width: 150, align: 'left' 
            	,render: function (item)
            	{
            		return  (JSON.stringify(item.staffname)).replace(reg, "");;
            	}
            },
            {
                display: '作品', name: 'imageurl', align: 'center', width: 250, minWidth: 240, validate: { required: true }
             , render: function (item) {
                 return "<div style='width:100%;'><img width='100%' src="+JSON.stringify(item.imageurl)+" /></div>";
             } //render
            },
            { display: '作品描述', name: 'staffname',  width: 208, align: 'left' 
            	,render: function (item)
            	{
            		return  (JSON.stringify(item.content)).replace(reg, "");;
            	}
            },
            { display: '状态', name: 'auditstate',  width:150,align: 'left'  , 
            	render: function (item,rowindex)
            	{
            		if (JSON.stringify(item.auditstate) == "\"0\"") return '未审核';
            		if (JSON.stringify(item.auditstate) == "\"1\"") return '已审核';
            		return rowindex;
            	}
            }
            ]
    	,
            pageSize:100, 
            width: 1000,
            checkbox: true,
            height:height-30,
            rowHeight:286,
            isScroll:true
        });
    	$(".list-style-image").attr("url","http://7xkadu.com2.z0.glb.qiniucdn.com/1446108096627_Jellyfish.jpg");
	   $("#toptoolbardetial").ligerToolBar({ items: [
  		     { text: '工号:&nbsp;<input type="text"  name="strStaffNo" id="strStaffNo" maxlength="20"  style="width:50;" />审核状态 ：<select name="auditState" id="auditState" ligeruiid="auditState"><option selected="selected" value="1">已审核</option><option value="0">未审核</option></select>' },
             { text: '查询&nbsp;', click: searchStaffInfo },
             { text: '审核&nbsp;', click: shenHe },
             { text: '作废&nbsp;', click: boHui }
        ]
        });
	}catch(e){alert(e.message);}
});

function shenHe(){
	 var rows = commoninfodivsecond.getCheckedRows();  
     var str = "";  
     $(rows).each(function ()  
     {  
         str += this.id + ",";  
     }); 
     updateSelect(str,'1');
}

function boHui(){
	var rows = commoninfodivsecond.getCheckedRows();  
		 var str = "";  
		 $(rows).each(function ()  
				 {  
			 str += this.id + ","; 
				 }); 
		 updateSelect(str,'2');
}

function updateSelect(strId,auditstate)
{
	try{
		var params = "strId="+strId;				
		params = params + "&auditState="+auditstate;				
		var requestUrl ="ac021/updateSelect.action"; 
		var responseMethod="loadUpdateSelectMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
	}catch(e){alert(e.message);}
}

function loadUpdateSelectMessage(request){
	(function (){
		searchStaffInfo();
	}());
}

//搜索框
 function searchStaffInfo()
{
	var params ="strStaffNo="+document.getElementById("strStaffNo").value;
	params=params+"&auditState="+document.getElementById("auditState").value;
    var requestUrl ="ac021/loadDataSet.action"; 
	var responseMethod="loadDataSetMessage";	
	showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
	sendRequestForParams_p(requestUrl,responseMethod,params );
}

function loadDataSetMessage(request)
{		
		var responsetext = eval("(" + request.responseText + ")");
		showDialogmanager.close();
		if(responsetext.images!=null && responsetext.images.length>0)
       	{
				images = responsetext.images;
        		commoninfodivsecond.options.data=$.extend(true, {},{Rows: responsetext.images,Total: responsetext.images.length});
        		commoninfodivsecond.loadData(true);
        		commoninfodivsecond.select(0);  
        }
		else
		{
			 	$.ligerDialog.warn("没有查到作品信息!");
	   			commoninfodivsecond.options.data=$.extend(true, {},{Rows: null,Total: 0});
            	commoninfodivsecond.loadData(true);
		}

}