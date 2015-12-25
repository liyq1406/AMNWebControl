<%@page import="java.util.*,com.amani.model.A3area,com.amani.model.A3city,com.amani.model.A3province"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
	<body>
	<script type="text/javascript">
  <!-- 市 -->
  var lsCity=parent.lsCity; 
   <!--省 -->
  var lsProvine=parent.lsProvine; 

   <!-- 县 -->
  var lsArea=parent.lsArea;
  </script>
  <body>
  省:<select id="provi" name="provi" onchange="chooseProvince()" style="width:80px;">
  		<option value=""></option>
  		<script type="text/javascript">
  			for(var i=0;i<lsProvine.length;i++)
  			{
  				document.writeln("<option value='"+lsProvine[i].id.code+"'>"+lsProvine[i].id.name+"</option>");
  			}
  		</script>
  	</select>
    　　市:<select name="city" id="city" onchange="chooseCity()" style="width:200px;">
    	 		<option value=""></option>
  		<script type="text/javascript">
  			for(var i=0;i<lsCity.length;i++)
  			{
  				if(lsCity[i].id.provinceId=="110000")
  				{
  					document.writeln("<option value='"+lsCity[i].id.code+"'>"+lsCity[i].id.name+"</option>");
  				}
  			}
  		</script>
  	</select>
  	　　县<select name="area" id="area" style="width:200px;">
  	 	 		<option value=""></option>
    	<script type="text/javascript">
  			for(var i=0;i<lsArea.length;i++)
  			{
  				if(lsArea[i].id.cityId=="110100")
  				{
  					document.writeln("<option value='"+lsArea[i].id.code+"'>"+lsArea[i].id.name+"</option>");
  				}
  			}
  		</script>
  	</select>
  </body>
  <script type="text/javascript">
	function chooseProvince(){
		var provival=document.getElementById("provi").value;
		var city=document.getElementById("city");
		city.options.length=0;
		city.options.add(new Option("",""));
		for(var i=0;i<lsCity.size();i++){
			if(lsCity[i].id.provinceId==provival)
			{
				city.options.add(new Option(lsCity[i].id.name,lsCity[i].id.code));
			}
		}
		var cityval=document.getElementById("city").value;
		var areaVal=document.getElementById("area");
		areaVal.options.length=0;
		areaVal.options.add(new Option("",""));
		for(var i=0;i<lsArea.size();i++){
			if(lsArea[i].id.cityId==cityval){
				areaVal.options.add(new Option(lsArea[i].id.name,lsArea[i].id.code));
			}
		}
	}
	function chooseCity()
	{
		var cityval=document.getElementById("city").value;
		var area=document.getElementById("area");
		area.options.length=0;
		area.options.add(new Option("",""));
		for(var i=0;i<lsArea.size();i++){
			if(lsArea[i].id.cityId==cityval)
			{
				area.options.add(new Option(lsArea[i].id.name,lsArea[i].id.code));
			}
		}
	}
	
	
	function loadAllSortByAreaId(areaId)
	{
		var provi=document.getElementById("provi");
		var city=document.getElementById("city");
		city.options.length=0;
		var area=document.getElementById("area");
		area.options.length=0;
		if(areaId=="")
		{
			provi[0].selected=true;
			city.options.add(new Option("", ""));
			area.options.add(new Option("", ""));
			return;		
		}
		
		var provicode="";
		var citycode="";
		var areacode="";
		
		var provival="";
		var cityval="";
		var areaval="";
		
		for(var i=0;i<lsArea.size();i++)
		{
			if(lsArea[i].id.code==areaId)
			{
				areacode=areaId;
				areaval=lsArea[i].id.name;
				citycode=lsArea[i].id.cityId;
				break;
			}
		}
		if(citycode!="")
		{
			for(var i=0;i<lsCity.size();i++)
			{
				if(lsCity[i].id.code==citycode)
				{
					cityval=lsCity[i].id.name;
					provicode=lsCity[i].id.provinceId;
					break;
				}
			}
		}
		if(provicode!="")
		{
			for(var i=0;i<provi.length;i++)
			{
				if(provi[i].value==provicode)
				{
					city.options.add(new Option(cityval, citycode));
					area.options.add(new Option(areaval, areacode));
					provi[i].selected=true;
				}
			}
			
		}
	}
</script>
</html>
