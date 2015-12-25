//检查日期格式是否非法
//参数：要检查的对象
//返回值：如果检查合法就返回true 否则返回 false
function   checkDate(checktext)
{   
	var   datetime;   
  	var   year,month,day;   
  	var   gone,gtwo;   
  	var   strMessage = "日期格式非法";
  	if(trim(checktext.value)!="")
  	{   
    	datetime=trim(checktext.value);   
    	if(datetime.length==10)
    	{   
      		year=datetime.substring(0,4);   
      		if(isNaN(year)==true)
      		{   
        		alert(strMessage);   
        		checktext.focus(); 
        		return   false;   
      		}   
      		gone=datetime.substring(4,5);   
      		month=datetime.substring(5,7);   
      		if(isNaN(month)==true)
      		{   
        		alert(strMessage);   
        		checktext.focus(); 
        		return   false;   
      		}   
      		gtwo=datetime.substring(7,8);   
      		day=datetime.substring(8,10);   
      		if(isNaN(day)==true)
      		{   
        		alert(strMessage);   
        		checktext.focus(); 
        		return   false;   
      		}   
      		if((gone=="-")&&(gtwo=="-"))
      		{   
        		if(month<1||month>12)   
        		{     
          			alert(strMessage);     
          			checktext.focus(); 
          			return   false;     
          		}     
        		if(day<1||day>31)
        		{     
          			alert(strMessage);   
          			checktext.focus();   
          			return   false;     
        		}
        		else
        		{   
          			if(month==2)
          			{       
            			if(isLeapYear(year)&&day>29)
            			{     
                			alert(strMessage);     
                			checktext.focus();
                			return   false;     
            			}                 
            			if(!isLeapYear(year)&&day>28)
            			{	     
                			alert(strMessage);   
                			checktext.focus();   
                			return   false;     
            			}     
          			}     
          			if((month==4||month==6||month==9||month==11)&&(day>30))
          			{     
            			alert(strMessage);   
            			checktext.focus();     
            			return   false;     
          			}     
        		}   
      		}
      		else
      		{   
        		alert(strMessage);   
        		checktext.focus();  
        		return   false;   
      		}   
    	}
    	else
    	{   
      		alert(strMessage);   
      		checktext.focus();  
      		return   false;   
    	}   
	}
	else
  	{   
    	return   true;   
	}   
}   
 

