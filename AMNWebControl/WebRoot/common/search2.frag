<html>
	<body>
	<%
		try{
				HashMap chainLevels = (HashMap)application.getAttribute("chainLevels");
			  	int count = chainLevels.size();
				String sessionKey = (String)session.getAttribute("strCurCompId")+"_levelOrgs";
				HashMap levelOrgs = (HashMap)application.getAttribute(sessionKey);
				HashMap orgList = (HashMap)application.getAttribute("orgList");
		  		int i=2;
				while(i<=count)
				{
					String istr = (new Integer(i)).toString();
					out.println("<label id=lblev"+istr+">"+chainLevels.get(istr)+"</label>");
					String selname = "s2level_"+istr;
					String onchange = "selectSomeOrg("+istr+","+(new Integer(count)).toString()+");loadCurCompId("+istr+")";
					if(i==count)
						onchange = "loadCurCompId("+istr+")";
				
					List<String> orgs = (List<String>)levelOrgs.get(istr);
		
		%>
		<select style="width:120px;" id="<%=selname%>" onchange="<%=onchange%>">
		<%
			for(int j=0;j<orgs.size();j++)
			{
				String key = orgs.get(j);
				String value = (String)orgList.get(key);
				if(value == null) value="";
				if(key.equals("*"))
					value = "*";
				else if(key.equals("no"))
					value = "无";
				else
					value = key+"-"+value;
		%>
			<option value="<%=key %>"><%=value %></option>
		<%
			}
		%>
		</select>&nbsp;&nbsp;
		<%
				i++;
			}
		}catch(Exception e){e.printStackTrace();}
		%>
        
            
 
	</body>
</html>