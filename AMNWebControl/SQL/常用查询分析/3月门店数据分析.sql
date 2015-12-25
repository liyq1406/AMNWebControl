    --一、3月各门店分类业绩（总虚、美容虚、美发虚、烫染虚；总实、美容实、美发实、烫染实）
  select compid,compname,beautyeji=SUM(ISNULL(beautyeji,0)),hairyeji=SUM(ISNULL(hairyeji,0)),
  footyeji=SUM(ISNULL(footyeji,0)),fingeryeji=SUM(ISNULL(fingeryeji,0)),
  totalyeji=SUM(ISNULL(totalyeji,0)),realbeautyeji=SUM(ISNULL(realbeautyeji,0)),
  realhairyeji=SUM(ISNULL(realhairyeji,0)),realfootyeji=SUM(ISNULL(realfootyeji,0)),
  realfingeryeji=SUM(ISNULL(realfingeryeji,0)),realtotalyeji=SUM(ISNULL(realtotalyeji,0) )
  from compclasstraderesult,companyinfo   
  where ddate between '20140301' and '20140331' and compid=compno
  group by compid,compname
  order by compid
  

--二、3月对比1月门店分类增长情况，增长绝对数及百分比（总虚、美容虚、美发虚、烫染虚；总实、美容实、美发实、烫染实）

 select  a.compid,compname,
  '总虚绩增长额'=convert(numeric(20,1),ISNULL(a.totalyeji,0)-ISNULL(b.totalyeji,0)),
  '总虚绩增比率'=convert(numeric(20,4),(ISNULL(a.totalyeji,0)-ISNULL(b.totalyeji,0))/(ISNULL(b.totalyeji,0)+0.1)),
  '美容虚业绩增长额'=convert(numeric(20,1),ISNULL(a.beautyeji,0)-ISNULL(b.beautyeji,0)),
  '美容虚业绩增比率'=convert(numeric(20,3),(ISNULL(a.beautyeji,0)-ISNULL(b.beautyeji,0))/(ISNULL(b.beautyeji,0)+0.1)),
  '美发虚业绩增长额'=convert(numeric(20,1),ISNULL(a.hairyeji,0)-ISNULL(b.hairyeji,0)),
  '美发虚业绩增比率'=convert(numeric(20,3),(ISNULL(a.hairyeji,0)-ISNULL(b.hairyeji,0))/(ISNULL(b.hairyeji,0)+0.1)),
  '烫染虚业绩增长额'=convert(numeric(20,1),ISNULL(a.footyeji,0)-ISNULL(b.footyeji,0)),
  '烫染虚业绩增比率'=convert(numeric(20,3),(ISNULL(a.footyeji,0)-ISNULL(b.footyeji,0))/(ISNULL(b.footyeji,0)+0.1)),
  
  '总实绩增长额'=convert(numeric(20,1),ISNULL(a.realtotalyeji,0)-ISNULL(b.realtotalyeji,0)),
  '总实绩增比率'=convert(numeric(20,3),(ISNULL(a.realtotalyeji,0)-ISNULL(b.realtotalyeji,0))/(ISNULL(b.realtotalyeji,0)+0.1)),
  '美容实业绩增长额'=convert(numeric(20,1),ISNULL(a.realbeautyeji,0)-ISNULL(b.realbeautyeji,0)),
  '美容实业绩增比率'=convert(numeric(20,3),(ISNULL(a.realbeautyeji,0)-ISNULL(b.realbeautyeji,0))/(ISNULL(b.realbeautyeji,0)+0.1)),
  '美发实业绩增长额'=convert(numeric(20,1),ISNULL(a.realhairyeji,0)-ISNULL(b.realhairyeji,0)),
  '美发实业绩增比率'=convert(numeric(20,3),(ISNULL(a.realhairyeji,0)-ISNULL(b.realhairyeji,0))/(ISNULL(b.realhairyeji,0)+0.1)),
  '烫染实业绩增长额'=convert(numeric(20,1),ISNULL(a.realfootyeji,0)-ISNULL(b.realfootyeji,0)),
  '烫染实业绩增比率'=convert(numeric(20,3),(ISNULL(a.realfootyeji,0)-ISNULL(b.realfootyeji,0))/(ISNULL(b.realfootyeji,0)+0.1))
  
  from (select compid,beautyeji=SUM(ISNULL(beautyeji,0)),hairyeji=SUM(ISNULL(hairyeji,0)),
  footyeji=SUM(ISNULL(footyeji,0)),fingeryeji=SUM(ISNULL(fingeryeji,0)),
  totalyeji=SUM(ISNULL(totalyeji,0)),realbeautyeji=SUM(ISNULL(realbeautyeji,0)),
  realhairyeji=SUM(ISNULL(realhairyeji,0)),realfootyeji=SUM(ISNULL(realfootyeji,0)),
  realfingeryeji=SUM(ISNULL(realfingeryeji,0)),realtotalyeji=SUM(ISNULL(realtotalyeji,0) )
  from compclasstraderesult   
  where ddate between '20140301' and '20140331' 
  group by compid) a,
  (select compid,beautyeji=SUM(ISNULL(beautyeji,0)),hairyeji=SUM(ISNULL(hairyeji,0)),
  footyeji=SUM(ISNULL(footyeji,0)),fingeryeji=SUM(ISNULL(fingeryeji,0)),
  totalyeji=SUM(ISNULL(totalyeji,0)),realbeautyeji=SUM(ISNULL(realbeautyeji,0)),
  realhairyeji=SUM(ISNULL(realhairyeji,0)),realfootyeji=SUM(ISNULL(realfootyeji,0)),
  realfingeryeji=SUM(ISNULL(realfingeryeji,0)),realtotalyeji=SUM(ISNULL(realtotalyeji,0) )
  from compclasstraderesult   
  where ddate between '20140101' and '20140131' 
  group by compid) b,companyinfo
  where a.compid=b.compid and a.compid=compno and isnull(a.totalyeji,0)>0
  order by compid asc
  
  --店长3项指标完成情况，指标及实际完成数据（总实、总虚、离职人数/客单）
  
  --create table shoptargetinfo
  --(
		--strcompid		varchar(10)		null,
		--strcompname		varchar(30)		null,
		--totalyeji		float			null,
		--realtotalyeji	float			null,
		--leavecount		float			null,
		--consumcount		float			null,
  --)
  delete shoptargetinfo
  insert shoptargetinfo(strcompid,strcompname,totalyeji,realtotalyeji)
  select compid,compname,SUM(ISNULL(totalyeji,0)),SUM(ISNULL(realtotalyeji,0) )
  from compclasstraderesult,companyinfo   
  where ddate between '20140301' and '20140331' and compid=compno
  group by compid,compname
  order by compid
  
   update b set leavecount=ISNULL(ccount,0)
   from (select appchangecompid,ccount=COUNT(changestaffno)
   from staffchangeinfo where changetype=1 and validatestartdate between '20140301' and '20140331'
   group by appchangecompid) a,shoptargetinfo b
   where a.appchangecompid=b.strcompid
   
   
   update b set consumcount=ISNULL(ccount,0)
   from (select cscompid,ccount=COUNT(distinct csbillid) from mconsumeinfo
   where financedate between '20140301' and '20140331' 
   and ISNULL(backcsbillid,'')='' 
   and ISNULL(backcsflag,0)=0
   group by cscompid) a,shoptargetinfo b
   where a.cscompid=b.strcompid
   
   
  select strcompid,strcompname,totalyeji,realtotalyeji,leavecount,consumcount from shoptargetinfo order by strcompid
  
  --3月对比1月、12月、11月的三连涨和三连跌情况，3月对比1月增长额、1月对比12月增长额、12月对比11月增长额
   delete shopyejitargetinfo
  insert shopyejitargetinfo(strcompid,totalyeji_403,beautyeji_403,hairyeji_403,footyeji_403,realtotalyeji_403,realtbeautyeji_403,realthairyeji_403,realtfootyeji_403)
  select compid,totalyeji=SUM(ISNULL(totalyeji,0)),beautyeji=SUM(ISNULL(beautyeji,0)),  hairyeji=SUM(ISNULL(hairyeji,0)), 
   footyeji=SUM(ISNULL(footyeji,0)),realtotalyeji=SUM(ISNULL(realtotalyeji,0) ),  
  realbeautyeji=SUM(ISNULL(realbeautyeji,0)),  realhairyeji=SUM(ISNULL(realhairyeji,0)),
  realfootyeji=SUM(ISNULL(realfootyeji,0))
  from compclasstraderesult,companyinfo   
  where ddate between '20140301' and '20140331' and compid=compno and ISNULL(totalyeji,0)>0
  group by compid


  update a set totalyeji_401=totalyeji,beautyeji_401=beautyeji,
			   hairyeji_401=hairyeji,footyeji_401=footyeji,
			   realtotalyeji_401=realtotalyeji,realtbeautyeji_401=realbeautyeji
			   ,realthairyeji_401=realhairyeji,realtfootyeji_401=realfootyeji
  from shopyejitargetinfo a,( select compid,totalyeji=SUM(ISNULL(totalyeji,0)),beautyeji=SUM(ISNULL(beautyeji,0)),  hairyeji=SUM(ISNULL(hairyeji,0)), 
   footyeji=SUM(ISNULL(footyeji,0)),realtotalyeji=SUM(ISNULL(realtotalyeji,0) ),  
  realbeautyeji=SUM(ISNULL(realbeautyeji,0)),  realhairyeji=SUM(ISNULL(realhairyeji,0)),
  realfootyeji=SUM(ISNULL(realfootyeji,0))
  from compclasstraderesult,companyinfo   
  where ddate between '20140101' and '20140131' and compid=compno
  group by compid) b
  where a.strcompid=b.compid
  
 
  update a set totalyeji_312=totalyeji,beautyeji_312=beautyeji,
			   hairyeji_312=hairyeji,footyeji_312=footyeji,
			   realtotalyeji_312=realtotalyeji,realtbeautyeji_312=realbeautyeji
			   ,realthairyeji_312=realhairyeji,realtfootyeji_312=realfootyeji
  from shopyejitargetinfo a,( select compid,totalyeji=SUM(ISNULL(totalyeji,0)),beautyeji=SUM(ISNULL(beautyeji,0)),  hairyeji=SUM(ISNULL(hairyeji,0)), 
   footyeji=SUM(ISNULL(footyeji,0)),realtotalyeji=SUM(ISNULL(realtotalyeji,0) ),  
  realbeautyeji=SUM(ISNULL(realbeautyeji,0)),  realhairyeji=SUM(ISNULL(realhairyeji,0)),
  realfootyeji=SUM(ISNULL(realfootyeji,0))
  from compclasstraderesult,companyinfo   
  where ddate between '20131201' and '20131231' and compid=compno
  group by compid) b
  where a.strcompid=b.compid


 
  update a set totalyeji_311_1=totalyeji/3,beautyeji_311_1=beautyeji/3,
			   hairyeji_311_1=hairyeji/3,footyeji_311_1=footyeji/3,
			   realtotalyeji_311_1=realtotalyeji/3,realtbeautyeji_311_1=realbeautyeji/3
			   ,realthairyeji_311_1=realhairyeji/3,realtfootyeji_311_1=realfootyeji/3
  from shopyejitargetinfo a,( select compid,totalyeji=SUM(ISNULL(totalyeji,0)),beautyeji=SUM(ISNULL(beautyeji,0)),  hairyeji=SUM(ISNULL(hairyeji,0)), 
   footyeji=SUM(ISNULL(footyeji,0)),realtotalyeji=SUM(ISNULL(realtotalyeji,0) ),  
  realbeautyeji=SUM(ISNULL(realbeautyeji,0)),  realhairyeji=SUM(ISNULL(realhairyeji,0)),
  realfootyeji=SUM(ISNULL(realfootyeji,0))
  from compclasstraderesult,companyinfo   
  where ddate between '20131101' and '20140131' and compid=compno
  group by compid) b
  where a.strcompid=b.compid
  
  
  update a set totalyeji_310_12=totalyeji/3,beautyeji_310_12=beautyeji/3,
			   hairyeji_310_12=hairyeji/3,footyeji_310_12=footyeji/3,
			   realtotalyeji_310_12=realtotalyeji/3,realtbeautyeji_310_12=realbeautyeji/3
			   ,realthairyeji_310_12=realhairyeji/3,realtfootyeji_310_12=realfootyeji/3
  from shopyejitargetinfo a,( select compid,totalyeji=SUM(ISNULL(totalyeji,0)),beautyeji=SUM(ISNULL(beautyeji,0)),  hairyeji=SUM(ISNULL(hairyeji,0)), 
   footyeji=SUM(ISNULL(footyeji,0)),realtotalyeji=SUM(ISNULL(realtotalyeji,0) ),  
  realbeautyeji=SUM(ISNULL(realbeautyeji,0)),  realhairyeji=SUM(ISNULL(realhairyeji,0)),
  realfootyeji=SUM(ISNULL(realfootyeji,0))
  from compclasstraderesult,companyinfo   
  where ddate between '20131001' and '20131231' and compid=compno
  group by compid) b
  where a.strcompid=b.compid
  
  
    
  update a set totalyeji_309_11=totalyeji/3,beautyeji_309_11=beautyeji/3,
			   hairyeji_309_11=hairyeji/3,footyeji_309_11=footyeji/3,
			   realtotalyeji_309_11=realtotalyeji/3,realtbeautyeji_309_11=realbeautyeji/3
			   ,realthairyeji_309_11=realhairyeji/3,realtfootyeji_309_11=realfootyeji/3
  from shopyejitargetinfo a,( select compid,totalyeji=SUM(ISNULL(totalyeji,0)),beautyeji=SUM(ISNULL(beautyeji,0)),  hairyeji=SUM(ISNULL(hairyeji,0)), 
   footyeji=SUM(ISNULL(footyeji,0)),realtotalyeji=SUM(ISNULL(realtotalyeji,0) ),  
  realbeautyeji=SUM(ISNULL(realbeautyeji,0)),  realhairyeji=SUM(ISNULL(realhairyeji,0)),
  realfootyeji=SUM(ISNULL(realfootyeji,0))
  from compclasstraderesult,companyinfo   
  where ddate between '20130901' and '20131130' and compid=compno
  group by compid) b
  where a.strcompid=b.compid
  
update shopyejitargetinfo set strcompname=compname
from shopyejitargetinfo,companyinfo
where strcompid=compno

select * from shopyejitargetinfo


	--总虚业绩3连长
    select strcompid,strcompname,
    convert(numeric(20,1),isnull(totalyeji_403,0)-isnull(totalyeji_311_1,0)) ,
    convert(numeric(20,1),isnull(totalyeji_401,0)-isnull(totalyeji_310_12,0)),
    convert(numeric(20,1),isnull(totalyeji_312,0)-isnull(totalyeji_309_11,0))
    from shopyejitargetinfo
    where  isnull(totalyeji_403,0)>isnull(totalyeji_311_1,0)
     and isnull(totalyeji_401,0)>isnull(totalyeji_310_12,0) 
     and isnull(totalyeji_312,0)>isnull(totalyeji_309_11,0)
    order by strcompid
    
   
    
    --美容虚业绩3连长
    select strcompid,strcompname,
    convert(numeric(20,1),isnull(beautyeji_403,0)-isnull(beautyeji_311_1,0)) ,
    convert(numeric(20,1),isnull(beautyeji_401,0)-isnull(beautyeji_310_12,0)),
    convert(numeric(20,1), isnull(beautyeji_312,0)-isnull(beautyeji_309_11,0))
    from shopyejitargetinfo
    where  isnull(beautyeji_403,0)>isnull(beautyeji_311_1,0) 
    and isnull(beautyeji_401,0)>isnull(beautyeji_310_12,0) 
    and isnull(beautyeji_312,0)>isnull(beautyeji_309_11,0)
    order by strcompid
    
      --美发虚业绩3连长
    select strcompid,strcompname,
    convert(numeric(20,1),isnull(hairyeji_403,0)-isnull(hairyeji_311_1,0)) ,
    convert(numeric(20,1),isnull(hairyeji_401,0)-isnull(hairyeji_310_12,0)),
    convert(numeric(20,1),isnull(hairyeji_312,0)-isnull(hairyeji_309_11,0))
    from shopyejitargetinfo
    where  isnull(hairyeji_403,0)>isnull(hairyeji_311_1,0)
      and isnull(hairyeji_401,0)>isnull(hairyeji_310_12,0)
      and isnull(hairyeji_312,0)>isnull(hairyeji_309_11,0)
    order by strcompid
    
     --烫染虚业绩3连长
    select strcompid,strcompname,
    convert(numeric(20,1),isnull(footyeji_403,0)-isnull(footyeji_311_1,0)) ,
    convert(numeric(20,1),isnull(footyeji_401,0)-isnull(footyeji_310_12,0)),
    convert(numeric(20,1),isnull(footyeji_312,0)-isnull(footyeji_309_11,0))
    from shopyejitargetinfo
    where  isnull(footyeji_403,0)>isnull(footyeji_311_1,0)
      and isnull(footyeji_401,0)>isnull(footyeji_310_12,0)
      and isnull(footyeji_312,0)>isnull(footyeji_309_11,0)
    order by strcompid
    
    
     --总实业绩3连长
    select strcompid,strcompname,
    convert(numeric(20,1),isnull(realtotalyeji_403,0)-isnull(realtotalyeji_311_1,0)) ,
    convert(numeric(20,1),isnull(realtotalyeji_401,0)-isnull(realtotalyeji_310_12,0)),
    convert(numeric(20,1),isnull(realtotalyeji_312,0)-isnull(realtotalyeji_309_11,0))
    from shopyejitargetinfo
    where  isnull(realtotalyeji_403,0)>isnull(realtotalyeji_311_1,0)
      and isnull(realtotalyeji_401,0)>isnull(realtotalyeji_310_12,0)
      and isnull(realtotalyeji_312,0)>isnull(realtotalyeji_309_11,0)
    order by strcompid
    
    
    --美容实业绩3连长
    select strcompid,strcompname,
    convert(numeric(20,1),isnull(realtbeautyeji_403,0)-isnull(realtbeautyeji_311_1,0)) ,
    convert(numeric(20,1),isnull(realtbeautyeji_401,0)-isnull(realtbeautyeji_310_12,0)),
    convert(numeric(20,1),isnull(realtbeautyeji_312,0)-isnull(realtbeautyeji_309_11,0))
    from shopyejitargetinfo
    where  isnull(realtbeautyeji_403,0)>isnull(realtbeautyeji_311_1,0)
      and isnull(realtbeautyeji_401,0)>isnull(realtbeautyeji_310_12,0)
      and isnull(realtbeautyeji_312,0)>isnull(realtbeautyeji_309_11,0)
    order by strcompid
    
       --美发实业绩3连长
    select strcompid,strcompname,
    convert(numeric(20,1),isnull(realthairyeji_403,0)-isnull(realthairyeji_311_1,0)) ,
    convert(numeric(20,1),isnull(realthairyeji_401,0)-isnull(realthairyeji_310_12,0)),
    convert(numeric(20,1),isnull(realthairyeji_312,0)-isnull(realthairyeji_309_11,0))
    from shopyejitargetinfo
    where  isnull(realthairyeji_403,0)>isnull(realthairyeji_311_1,0)
      and isnull(realthairyeji_401,0)>isnull(realthairyeji_310_12,0)
      and isnull(realthairyeji_312,0)>isnull(realthairyeji_309_11,0)
    order by strcompid
    
    
       --烫染实业绩3连长
    select strcompid,strcompname,
    convert(numeric(20,1),isnull(realtfootyeji_403,0)-isnull(realtfootyeji_311_1,0))  ,
    convert(numeric(20,1),isnull(realtfootyeji_401,0)-isnull(realtfootyeji_310_12,0)) ,
    convert(numeric(20,1),isnull(realtfootyeji_312,0)-isnull(realtfootyeji_309_11,0)) 
    from shopyejitargetinfo
    where  isnull(realtfootyeji_403,0)>isnull(realtfootyeji_311_1,0)
      and isnull(realtfootyeji_401,0)>isnull(realtfootyeji_310_12,0)
      and isnull(realtfootyeji_312,0)>isnull(realtfootyeji_309_11,0)
    order by strcompid
    
    
    
    --总虚业绩3连跌
    select strcompid,strcompname,
    convert(numeric(20,1),isnull(totalyeji_403,0)-isnull(totalyeji_311_1,0)) ,
    convert(numeric(20,1),isnull(totalyeji_401,0)-isnull(totalyeji_310_12,0)),
    convert(numeric(20,1),isnull(totalyeji_312,0)-isnull(totalyeji_309_11,0))
    from shopyejitargetinfo
    where  isnull(totalyeji_403,0)<isnull(totalyeji_311_1,0) 
    and isnull(totalyeji_401,0)<isnull(totalyeji_310_12,0)
     and isnull(totalyeji_312,0)<isnull(totalyeji_309_11,0)
    order by strcompid
    --美容虚业绩3连跌
    select strcompid,strcompname,
    convert(numeric(20,1),isnull(beautyeji_403,0)-isnull(beautyeji_311_1,0)) ,
    convert(numeric(20,1),isnull(beautyeji_401,0)-isnull(beautyeji_310_12,0)),
    convert(numeric(20,1),isnull(beautyeji_312,0)-isnull(beautyeji_309_11,0))
    from shopyejitargetinfo
    where  isnull(beautyeji_403,0)<isnull(beautyeji_311_1,0) 
    and isnull(beautyeji_401,0)<isnull(beautyeji_310_12,0) 
    and isnull(beautyeji_312,0)<isnull(beautyeji_309_11,0)
    order by strcompid
    
      --美发虚业绩3连跌
    select strcompid,strcompname,
    isnull(hairyeji_403,0)-isnull(hairyeji_311_1,0) ,
    isnull(hairyeji_401,0)-isnull(hairyeji_310_12,0),
    isnull(hairyeji_312,0)-isnull(hairyeji_309_11,0)
    from shopyejitargetinfo
    where  isnull(hairyeji_403,0)<isnull(hairyeji_311_1,0)
      and isnull(hairyeji_401,0)<isnull(hairyeji_310_12,0)
      and isnull(hairyeji_312,0)<isnull(hairyeji_309_11,0)
    order by strcompid
    
     --烫染虚业绩3连跌
    select strcompid,strcompname,
    convert(numeric(20,1),isnull(footyeji_403,0)-isnull(footyeji_311_1,0)) ,
    convert(numeric(20,1),isnull(footyeji_401,0)-isnull(footyeji_310_12,0)),
    convert(numeric(20,1),isnull(footyeji_312,0)-isnull(footyeji_309_11,0))
    from shopyejitargetinfo
    where  isnull(footyeji_403,0)<isnull(footyeji_311_1,0)
      and isnull(footyeji_401,0)<isnull(footyeji_310_12,0)
      and isnull(footyeji_312,0)<isnull(footyeji_309_11,0)
    order by strcompid
    
    
     --总实业绩3连跌
    select strcompid,strcompname,
     convert(numeric(20,1),isnull(realtotalyeji_403,0)-isnull(realtotalyeji_311_1,0)) ,
     convert(numeric(20,1),isnull(realtotalyeji_401,0)-isnull(realtotalyeji_310_12,0)),
     convert(numeric(20,1),isnull(realtotalyeji_312,0)-isnull(realtotalyeji_309_11,0))
    from shopyejitargetinfo
    where  isnull(realtotalyeji_403,0)<isnull(realtotalyeji_311_1,0)
      and isnull(realtotalyeji_401,0)<isnull(realtotalyeji_310_12,0)
      and isnull(realtotalyeji_312,0)<isnull(realtotalyeji_309_11,0)
    order by strcompid
    
    
    --美容实业绩3连跌
    select strcompid,strcompname,
    convert(numeric(20,1),isnull(realtbeautyeji_403,0)-isnull(realtbeautyeji_311_1,0)) ,
    convert(numeric(20,1),isnull(realtbeautyeji_401,0)-isnull(realtbeautyeji_310_12,0)),
    convert(numeric(20,1),isnull(realtbeautyeji_312,0)-isnull(realtbeautyeji_309_11,0))
    from shopyejitargetinfo
    where  isnull(realtbeautyeji_403,0)<isnull(realtbeautyeji_311_1,0)
      and isnull(realtbeautyeji_401,0)<isnull(realtbeautyeji_310_12,0)
      and isnull(realtbeautyeji_312,0)<isnull(realtbeautyeji_309_11,0)
    order by strcompid
    
       --美发实业绩3连跌
    select strcompid,strcompname,
    isnull(realthairyeji_403,0)-isnull(realthairyeji_311_1,0) ,
    isnull(realthairyeji_401,0)-isnull(realthairyeji_310_12,0),
    isnull(realthairyeji_312,0)-isnull(realthairyeji_309_11,0)
    from shopyejitargetinfo
    where  isnull(realthairyeji_403,0)<isnull(realthairyeji_311_1,0)
      and isnull(realthairyeji_401,0)<isnull(realthairyeji_310_12,0)
      and isnull(realthairyeji_312,0)<isnull(realthairyeji_309_11,0)
    order by strcompid
    
    
       --烫染实业绩3连跌
    select strcompid,strcompname,
    isnull(realtfootyeji_403,0)-isnull(realtfootyeji_311_1,0) ,
    isnull(realtfootyeji_401,0)-isnull(realtfootyeji_310_12,0),
    isnull(realtfootyeji_312,0)-isnull(realtfootyeji_309_11,0)
    from shopyejitargetinfo
    where  isnull(realtfootyeji_403,0)<isnull(realtfootyeji_311_1,0)
      and isnull(realtfootyeji_401,0)<isnull(realtfootyeji_310_12,0)
      and isnull(realtfootyeji_312,0)<isnull(realtfootyeji_309_11,0)
    order by strcompid

  --create table shopyejitargetinfo
  --(
		--strcompid				varchar(10)		null,
		--strcompname				varchar(30)		null,
		--totalyeji_403			float			null,
		--beautyeji_403			float			null,
		--hairyeji_403			float			null,
		--footyeji_403			float			null,
		--realtotalyeji_403		float			null,
		--realtbeautyeji_403		float			null,
		--realthairyeji_403		float			null,
		--realtfootyeji_403		float			null,
		
		
		--totalyeji_401			float			null,
		--beautyeji_401			float			null,
		--hairyeji_401			float			null,
		--footyeji_401			float			null,
		--realtotalyeji_401		float			null,
		--realtbeautyeji_401		float			null,
		--realthairyeji_401		float			null,
		--realtfootyeji_401		float			null,
		
		--totalyeji_312			float			null,
		--beautyeji_312			float			null,
		--hairyeji_312			float			null,
		--footyeji_312			float			null,
		--realtotalyeji_312		float			null,
		--realtbeautyeji_312		float			null,
		--realthairyeji_312		float			null,
		--realtfootyeji_312		float			null,
		
		--totalyeji_311_1			float			null,
		--beautyeji_311_1			float			null,
		--hairyeji_311_1			float			null,
		--footyeji_311_1			float			null,
		--realtotalyeji_311_1		float			null,
		--realtbeautyeji_311_1	float			null,
		--realthairyeji_311_1		float			null,
		--realtfootyeji_311_1		float			null,
		
		--totalyeji_310_12		float			null,
		--beautyeji_310_12		float			null,
		--hairyeji_310_12			float			null,
		--footyeji_310_12			float			null,
		--realtotalyeji_310_12	float			null,
		--realtbeautyeji_310_12	float			null,
		--realthairyeji_310_12	float			null,
		--realtfootyeji_310_12	float			null,
		
		--totalyeji_309_11		float			null,
		--beautyeji_309_11		float			null,
		--hairyeji_309_11			float			null,
		--footyeji_309_11			float			null,
		--realtotalyeji_309_11	float			null,
		--realtbeautyeji_309_11	float			null,
		--realthairyeji_309_11	float			null,
		--realtfootyeji_309_11	float			null
  --)
  
  
  
  
   select  a.compid,compname,
  '总虚绩增长额'=convert(numeric(20,1),ISNULL(a.totalyeji,0)-ISNULL(b.totalyeji,0)),
  '总虚绩增比率'=substring(cast(convert(numeric(20,4),(ISNULL(a.totalyeji,0)-ISNULL(b.totalyeji,0))/(ISNULL(b.totalyeji,0)+0.1))*100 as varchar(20)),1,6)+'%',
  '美容虚业绩增长额'=convert(numeric(20,1),ISNULL(a.beautyeji,0)-ISNULL(b.beautyeji,0)),
  '美容虚业绩增比率'=substring(cast(convert(numeric(20,4),(ISNULL(a.beautyeji,0)-ISNULL(b.beautyeji,0))/(ISNULL(b.beautyeji,0)+0.1))*100 as varchar(20)),1,6)+'%',
  '美发虚业绩增长额'=convert(numeric(20,1),ISNULL(a.hairyeji,0)-ISNULL(b.hairyeji,0)),
  '美发虚业绩增比率'=substring(cast(convert(numeric(20,4),(ISNULL(a.hairyeji,0)-ISNULL(b.hairyeji,0))/(ISNULL(b.hairyeji,0)+0.1))*100 as varchar(20)),1,6)+'%',
  '烫染虚业绩增长额'=convert(numeric(20,1),ISNULL(a.footyeji,0)-ISNULL(b.footyeji,0)),
  '烫染虚业绩增比率'=substring(cast(convert(numeric(20,4),(ISNULL(a.footyeji,0)-ISNULL(b.footyeji,0))/(ISNULL(b.footyeji,0)+0.1))*100 as varchar(20)),1,6)+'%',
  
  '总实绩增长额'=convert(numeric(20,1),ISNULL(a.realtotalyeji,0)-ISNULL(b.realtotalyeji,0)),
  '总实绩增比率'=substring(cast(convert(numeric(20,4),(ISNULL(a.realtotalyeji,0)-ISNULL(b.realtotalyeji,0))/(ISNULL(b.realtotalyeji,0)+0.1))*100 as varchar(20)),1,6)+'%',
  '美容实业绩增长额'=convert(numeric(20,1),ISNULL(a.realbeautyeji,0)-ISNULL(b.realbeautyeji,0)),
  '美容实业绩增比率'=substring(cast(convert(numeric(20,4),(ISNULL(a.realbeautyeji,0)-ISNULL(b.realbeautyeji,0))/(ISNULL(b.realbeautyeji,0)+0.1))*100 as varchar(20)),1,6)+'%',
  '美发实业绩增长额'=convert(numeric(20,1),ISNULL(a.realhairyeji,0)-ISNULL(b.realhairyeji,0)),
  '美发实业绩增比率'=substring(cast(convert(numeric(20,4),(ISNULL(a.realhairyeji,0)-ISNULL(b.realhairyeji,0))/(ISNULL(b.realhairyeji,0)+0.1))*100 as varchar(20)),1,6)+'%',
  '烫染实业绩增长额'=convert(numeric(20,1),ISNULL(a.realfootyeji,0)-ISNULL(b.realfootyeji,0)),
  '烫染实业绩增比率'=substring(cast(convert(numeric(20,4),(ISNULL(a.realfootyeji,0)-ISNULL(b.realfootyeji,0))/(ISNULL(b.realfootyeji,0)+0.1))*100 as varchar(20)),1,6)+'%'
  
  from (select compid,beautyeji=SUM(ISNULL(beautyeji,0)),hairyeji=SUM(ISNULL(hairyeji,0)),
  footyeji=SUM(ISNULL(footyeji,0)),fingeryeji=SUM(ISNULL(fingeryeji,0)),
  totalyeji=SUM(ISNULL(totalyeji,0)),realbeautyeji=SUM(ISNULL(realbeautyeji,0)),
  realhairyeji=SUM(ISNULL(realhairyeji,0)),realfootyeji=SUM(ISNULL(realfootyeji,0)),
  realfingeryeji=SUM(ISNULL(realfingeryeji,0)),realtotalyeji=SUM(ISNULL(realtotalyeji,0) )
  from compclasstraderesult   
  where ddate between '20140301' and '20140331' 
  group by compid) a,
  (select compid,beautyeji=SUM(ISNULL(beautyeji,0))/3,hairyeji=SUM(ISNULL(hairyeji,0))/3,
  footyeji=SUM(ISNULL(footyeji,0))/3,fingeryeji=SUM(ISNULL(fingeryeji,0))/3,
  totalyeji=SUM(ISNULL(totalyeji,0))/3,realbeautyeji=SUM(ISNULL(realbeautyeji,0))/3,
  realhairyeji=SUM(ISNULL(realhairyeji,0))/3,realfootyeji=SUM(ISNULL(realfootyeji,0))/3,
  realfingeryeji=SUM(ISNULL(realfingeryeji,0))/3,realtotalyeji=SUM(ISNULL(realtotalyeji,0) )/3
  from compclasstraderesult   
  where ddate between '20131101' and '20140131' 
  group by compid) b,companyinfo
  where a.compid=b.compid and a.compid=compno and isnull(a.totalyeji,0)>0
  order by compid asc
  
  
    select  '2014(01-03)','总虚业绩'=convert(numeric(20,1),SUM(ISNULL(totalyeji,0))),
    '美容虚业绩'=convert(numeric(20,1),SUM(ISNULL(beautyeji,0))),
    '美发虚业绩'=convert(numeric(20,1),SUM(ISNULL(hairyeji,0))),
    '烫染虚业绩'=convert(numeric(20,1),SUM(ISNULL(footyeji,0))),
    '总实业绩'=convert(numeric(20,1),SUM(ISNULL(realtotalyeji,0))),
	'美容实业绩'=convert(numeric(20,1),SUM(ISNULL(realbeautyeji,0))),
	'美发实业绩'=convert(numeric(20,1),SUM(ISNULL(realhairyeji,0))),
	'烫染实业绩'=convert(numeric(20,1),SUM(ISNULL(realfootyeji,0)))
  from compclasstraderesult,companyinfo  ,compchainstruct 
  where ddate between '20140101' and '20140331' and compid=compno 
  and complevel=4
  
  
      select  '2014(01-03)','总虚业绩'=convert(numeric(20,1),SUM(ISNULL(totalyeji,0))),
    '美容虚业绩'=convert(numeric(20,1),SUM(ISNULL(beautyeji,0))),
    '美发虚业绩'=convert(numeric(20,1),SUM(ISNULL(hairyeji,0))),
    '烫染虚业绩'=convert(numeric(20,1),SUM(ISNULL(footyeji,0))),
    '总实业绩'=convert(numeric(20,1),SUM(ISNULL(realtotalyeji,0))),
	'美容实业绩'=convert(numeric(20,1),SUM(ISNULL(realbeautyeji,0))),
	'美发实业绩'=convert(numeric(20,1),SUM(ISNULL(realhairyeji,0))),
	'烫染实业绩'=convert(numeric(20,1),SUM(ISNULL(realfootyeji,0)))
  from compclasstraderesult,companyinfo  ,compchainstruct 
  where ddate between '20130101' and '20130331' and compid=compno 
  and complevel=4
  
  
  
  
  
  
 select  a.compid,compname,
  '总虚绩增长额'=convert(numeric(20,1),ISNULL(a.totalyeji,0)-ISNULL(b.totalyeji,0)),
  '总虚绩增比率'=substring(cast(convert(numeric(20,4),(ISNULL(a.totalyeji,0)-ISNULL(b.totalyeji,0))/(ISNULL(b.totalyeji,0)+0.1))*100 as varchar(20)),1,6)+'%',
  '美容虚业绩增长额'=convert(numeric(20,1),ISNULL(a.beautyeji,0)-ISNULL(b.beautyeji,0)),
  '美容虚业绩增比率'=substring(cast(convert(numeric(20,4),(ISNULL(a.beautyeji,0)-ISNULL(b.beautyeji,0))/(ISNULL(b.beautyeji,0)+0.1))*100 as varchar(20)),1,6)+'%',
  '美发虚业绩增长额'=convert(numeric(20,1),ISNULL(a.hairyeji,0)-ISNULL(b.hairyeji,0)),
  '美发虚业绩增比率'=substring(cast(convert(numeric(20,4),(ISNULL(a.hairyeji,0)-ISNULL(b.hairyeji,0))/(ISNULL(b.hairyeji,0)+0.1))*100 as varchar(20)),1,6)+'%',
  '烫染虚业绩增长额'=convert(numeric(20,1),ISNULL(a.footyeji,0)-ISNULL(b.footyeji,0)),
  '烫染虚业绩增比率'=substring(cast(convert(numeric(20,4),(ISNULL(a.footyeji,0)-ISNULL(b.footyeji,0))/(ISNULL(b.footyeji,0)+0.1))*100 as varchar(20)),1,6)+'%',
  
  '总实绩增长额'=convert(numeric(20,1),ISNULL(a.realtotalyeji,0)-ISNULL(b.realtotalyeji,0)),
  '总实绩增比率'=substring(cast(convert(numeric(20,4),(ISNULL(a.realtotalyeji,0)-ISNULL(b.realtotalyeji,0))/(ISNULL(b.realtotalyeji,0)+0.1))*100 as varchar(20)),1,6)+'%',
  '美容实业绩增长额'=convert(numeric(20,1),ISNULL(a.realbeautyeji,0)-ISNULL(b.realbeautyeji,0)),
  '美容实业绩增比率'=substring(cast(convert(numeric(20,4),(ISNULL(a.realbeautyeji,0)-ISNULL(b.realbeautyeji,0))/(ISNULL(b.realbeautyeji,0)+0.1))*100 as varchar(20)),1,6)+'%',
  '美发实业绩增长额'=convert(numeric(20,1),ISNULL(a.realhairyeji,0)-ISNULL(b.realhairyeji,0)),
  '美发实业绩增比率'=substring(cast(convert(numeric(20,4),(ISNULL(a.realhairyeji,0)-ISNULL(b.realhairyeji,0))/(ISNULL(b.realhairyeji,0)+0.1))*100 as varchar(20)),1,6)+'%',
  '烫染实业绩增长额'=convert(numeric(20,1),ISNULL(a.realfootyeji,0)-ISNULL(b.realfootyeji,0)),
  '烫染实业绩增比率'=substring(cast(convert(numeric(20,4),(ISNULL(a.realfootyeji,0)-ISNULL(b.realfootyeji,0))/(ISNULL(b.realfootyeji,0)+0.1))*100 as varchar(20)),1,6)+'%'
  
  from (select compid,beautyeji=SUM(ISNULL(beautyeji,0)),hairyeji=SUM(ISNULL(hairyeji,0)),
  footyeji=SUM(ISNULL(footyeji,0)),fingeryeji=SUM(ISNULL(fingeryeji,0)),
  totalyeji=SUM(ISNULL(totalyeji,0)),realbeautyeji=SUM(ISNULL(realbeautyeji,0)),
  realhairyeji=SUM(ISNULL(realhairyeji,0)),realfootyeji=SUM(ISNULL(realfootyeji,0)),
  realfingeryeji=SUM(ISNULL(realfingeryeji,0)),realtotalyeji=SUM(ISNULL(realtotalyeji,0) )
  from compclasstraderesult   
  where ddate between '20140101' and '20140331' 
  group by compid) a,
  (select compid,beautyeji=SUM(ISNULL(beautyeji,0)),hairyeji=SUM(ISNULL(hairyeji,0)),
  footyeji=SUM(ISNULL(footyeji,0)),fingeryeji=SUM(ISNULL(fingeryeji,0)),
  totalyeji=SUM(ISNULL(totalyeji,0)),realbeautyeji=SUM(ISNULL(realbeautyeji,0)),
  realhairyeji=SUM(ISNULL(realhairyeji,0)),realfootyeji=SUM(ISNULL(realfootyeji,0)),
  realfingeryeji=SUM(ISNULL(realfingeryeji,0)),realtotalyeji=SUM(ISNULL(realtotalyeji,0) )
  from compclasstraderesult   
  where ddate between '20130101' and '20130331' 
  group by compid) b,companyinfo
  where a.compid=b.compid and a.compid=compno and isnull(a.totalyeji,0)>0
  order by compid asc
  