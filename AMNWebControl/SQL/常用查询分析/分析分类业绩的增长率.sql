--create table classyjanlysis
--(
--	compid					varchar(10)		null,
--	compname				varchar(30)		null,
--	curbeatyeji				float			null,
--	curhairyeji				float			null,                              
--	curfootyeji				float			null,                              
--	curtotalyeji			float			null,                              
--	currealbeautyeji		float			null,                              
--	currealhairyeji			float			null,                              
--	currealfootyeji			float			null,                              
--	currealtotalyeji		float			null,
--	b1beatyeji				float			null,
--	b1hairyeji				float			null,                              
--	b1footyeji				float			null,                              
--	b1totalyeji				float			null,                              
--	b1realbeautyeji			float			null,                              
--	b1realhairyeji			float			null,                              
--	b1realfootyeji			float			null,                              
--	b1realtotalyeji			float			null,
	
--	cur2beatyeji				float			null,
--	cur2hairyeji				float			null,                              
--	cur2footyeji				float			null,                              
--	cur2totalyeji			float			null,                              
--	cur2realbeautyeji		float			null,                              
--	cur2realhairyeji			float			null,                              
--	cur2realfootyeji			float			null,                              
--	cur2realtotalyeji		float			null,
	
--	b2beatyeji				float			null,
--	b2hairyeji				float			null,                              
--	b2footyeji				float			null,                              
--	b2totalyeji				float			null,                              
--	b2realbeautyeji			float			null,                              
--	b2realhairyeji			float			null,                              
--	b2realfootyeji			float			null,                              
--	b2realtotalyeji			float			null,
	
--	cur3beatyeji				float			null,
--	cur3hairyeji				float			null,                              
--	cur3footyeji				float			null,                              
--	cur3totalyeji			float			null,                              
--	cur3realbeautyeji		float			null,                              
--	cur3realhairyeji			float			null,                              
--	cur3realfootyeji			float			null,                              
--	cur3realtotalyeji		float			null,
	
--	b3beatyeji				float			null,
--	b3hairyeji				float			null,                              
--	b3footyeji				float			null,                              
--	b3totalyeji				float			null,                              
--	b3realbeautyeji			float			null,                              
--	b3realhairyeji			float			null,                              
--	b3realfootyeji			float			null,                              
--	b3realtotalyeji			float			null,
--)
delete classyjanlysis
insert classyjanlysis(compid,compname,curbeatyeji,curhairyeji,curfootyeji,curtotalyeji,currealbeautyeji,currealhairyeji,currealfootyeji,currealtotalyeji)
select compid,compname,convert(numeric(20,0),sum(isnull(beautyeji,0))),convert(numeric(20,0),sum(isnull(hairyeji,0))),
convert(numeric(20,0),sum(isnull(footyeji,0))),convert(numeric(20,0),sum(isnull(totalyeji,0))),
convert(numeric(20,0),sum(isnull(realbeautyeji,0))),convert(numeric(20,0),sum(isnull(realhairyeji,0))),
convert(numeric(20,0),sum(isnull(realfootyeji,0))),convert(numeric(20,0),sum(isnull(realtotalyeji,0)))
 from compclasstraderesult,companyinfo 
 where ddate between '20140101' and '20140131' and compid=compno and ISNULL(totalyeji,0)>0 and ISNULL(realtotalyeji,0)>0  group by compid,compname order by compid
 
 
  update c
 set cur2beatyeji=beautyeji,cur2hairyeji=hairyeji,cur2footyeji=footyeji,cur2totalyeji=totalyeji,
     cur2realbeautyeji=realbeautyeji,cur2realhairyeji=realhairyeji,cur2realfootyeji=realfootyeji,cur2realtotalyeji=realtotalyeji
 from classyjanlysis c,(select compid,
beautyeji=convert(numeric(20,0),sum(isnull(beautyeji,0))),hairyeji=convert(numeric(20,0),sum(isnull(hairyeji,0))),
footyeji=convert(numeric(20,0),sum(isnull(footyeji,0))),totalyeji=convert(numeric(20,0),sum(isnull(totalyeji,0))),
realbeautyeji=convert(numeric(20,0),sum(isnull(realbeautyeji,0))),realhairyeji=convert(numeric(20,0),sum(isnull(realhairyeji,0))),
realfootyeji=convert(numeric(20,0),sum(isnull(realfootyeji,0))),realtotalyeji=convert(numeric(20,0),sum(isnull(realtotalyeji,0)))
 from compclasstraderesult 
 where ddate between '20131201' and '20131231' and  ISNULL(totalyeji,0)>0 and ISNULL(realtotalyeji,0)>0 
 group by compid )  as b
 where c.compid=b.compid
 
 update c
 set cur3beatyeji=beautyeji,cur3hairyeji=hairyeji,cur3footyeji=footyeji,cur3totalyeji=totalyeji,
     cur3realbeautyeji=realbeautyeji,cur3realhairyeji=realhairyeji,cur3realfootyeji=realfootyeji,cur3realtotalyeji=realtotalyeji
 from classyjanlysis c,(select compid,
beautyeji=convert(numeric(20,0),sum(isnull(beautyeji,0))),hairyeji=convert(numeric(20,0),sum(isnull(hairyeji,0))),
footyeji=convert(numeric(20,0),sum(isnull(footyeji,0))),totalyeji=convert(numeric(20,0),sum(isnull(totalyeji,0))),
realbeautyeji=convert(numeric(20,0),sum(isnull(realbeautyeji,0))),realhairyeji=convert(numeric(20,0),sum(isnull(realhairyeji,0))),
realfootyeji=convert(numeric(20,0),sum(isnull(realfootyeji,0))),realtotalyeji=convert(numeric(20,0),sum(isnull(realtotalyeji,0)))
 from compclasstraderesult 
 where ddate between '20131101' and '20131130' and  ISNULL(totalyeji,0)>0 and ISNULL(realtotalyeji,0)>0 
 group by compid )  as b
 where c.compid=b.compid
 
 
 update c
 set b1beatyeji=beautyeji,b1hairyeji=hairyeji,b1footyeji=footyeji,b1totalyeji=totalyeji,
     b1realbeautyeji=realbeautyeji,b1realhairyeji=realhairyeji,b1realfootyeji=realfootyeji,b1realtotalyeji=realtotalyeji
 from classyjanlysis c,(
 select compid
,beautyeji=convert(numeric(20,0),SUM(ISNULL(beautyeji,0))/COUNT(compid))
,hairyeji=convert(numeric(20,0),SUM(ISNULL(hairyeji,0))/COUNT(compid))  
,footyeji=convert(numeric(20,0),SUM(ISNULL(footyeji,0))/COUNT(compid)) 
,totalyeji=convert(numeric(20,0),SUM(ISNULL(totalyeji,0))/COUNT(compid)) 
,realbeautyeji=convert(numeric(20,0),SUM(ISNULL(realbeautyeji,0))/COUNT(compid)) 
,realhairyeji=convert(numeric(20,0),SUM(ISNULL(realhairyeji,0))/COUNT(compid)) 
,realfootyeji=convert(numeric(20,0),SUM(ISNULL(realfootyeji,0))/COUNT(compid)) 
,realtotalyeji=convert(numeric(20,0),SUM(ISNULL(realtotalyeji,0))/COUNT(compid)) 
 from (select compid,ddate=SUBSTRING(ddate,1,6),
beautyeji=convert(numeric(20,0),sum(isnull(beautyeji,0))),hairyeji=convert(numeric(20,0),sum(isnull(hairyeji,0))),
footyeji=convert(numeric(20,0),sum(isnull(footyeji,0))),totalyeji=convert(numeric(20,0),sum(isnull(totalyeji,0))),
realbeautyeji=convert(numeric(20,0),sum(isnull(realbeautyeji,0))),realhairyeji=convert(numeric(20,0),sum(isnull(realhairyeji,0))),
realfootyeji=convert(numeric(20,0),sum(isnull(realfootyeji,0))),realtotalyeji=convert(numeric(20,0),sum(isnull(realtotalyeji,0)))
 from compclasstraderesult 
 where ddate between '20131001' and '20131231' and  ISNULL(totalyeji,0)>0 and ISNULL(realtotalyeji,0)>0 
 group by compid,SUBSTRING(ddate,1,6) ) as a group by compid ) as b
 where c.compid=b.compid
 
 
  update c
 set b2beatyeji=beautyeji,b2hairyeji=hairyeji,b2footyeji=footyeji,b2totalyeji=totalyeji,
     b2realbeautyeji=realbeautyeji,b2realhairyeji=realhairyeji,b2realfootyeji=realfootyeji,b2realtotalyeji=realtotalyeji
 from classyjanlysis c,(
 select compid
,beautyeji=convert(numeric(20,0),SUM(ISNULL(beautyeji,0))/COUNT(compid))
,hairyeji=convert(numeric(20,0),SUM(ISNULL(hairyeji,0))/COUNT(compid))  
,footyeji=convert(numeric(20,0),SUM(ISNULL(footyeji,0))/COUNT(compid)) 
,totalyeji=convert(numeric(20,0),SUM(ISNULL(totalyeji,0))/COUNT(compid)) 
,realbeautyeji=convert(numeric(20,0),SUM(ISNULL(realbeautyeji,0))/COUNT(compid)) 
,realhairyeji=convert(numeric(20,0),SUM(ISNULL(realhairyeji,0))/COUNT(compid)) 
,realfootyeji=convert(numeric(20,0),SUM(ISNULL(realfootyeji,0))/COUNT(compid)) 
,realtotalyeji=convert(numeric(20,0),SUM(ISNULL(realtotalyeji,0))/COUNT(compid)) 
 from (select compid,ddate=SUBSTRING(ddate,1,6),
beautyeji=convert(numeric(20,0),sum(isnull(beautyeji,0))),hairyeji=convert(numeric(20,0),sum(isnull(hairyeji,0))),
footyeji=convert(numeric(20,0),sum(isnull(footyeji,0))),totalyeji=convert(numeric(20,0),sum(isnull(totalyeji,0))),
realbeautyeji=convert(numeric(20,0),sum(isnull(realbeautyeji,0))),realhairyeji=convert(numeric(20,0),sum(isnull(realhairyeji,0))),
realfootyeji=convert(numeric(20,0),sum(isnull(realfootyeji,0))),realtotalyeji=convert(numeric(20,0),sum(isnull(realtotalyeji,0)))
 from compclasstraderesult 
 where ddate between '20130901' and '20131130' and  ISNULL(totalyeji,0)>0 and ISNULL(realtotalyeji,0)>0 
 group by compid,SUBSTRING(ddate,1,6) ) as a group by compid ) as b
 where c.compid=b.compid
 
 
   update c
 set b3beatyeji=beautyeji,b3hairyeji=hairyeji,b3footyeji=footyeji,b3totalyeji=totalyeji,
     b3realbeautyeji=realbeautyeji,b3realhairyeji=realhairyeji,b3realfootyeji=realfootyeji,b3realtotalyeji=realtotalyeji
 from classyjanlysis c,(
 select compid
,beautyeji=convert(numeric(20,0),SUM(ISNULL(beautyeji,0))/COUNT(compid))
,hairyeji=convert(numeric(20,0),SUM(ISNULL(hairyeji,0))/COUNT(compid))  
,footyeji=convert(numeric(20,0),SUM(ISNULL(footyeji,0))/COUNT(compid)) 
,totalyeji=convert(numeric(20,0),SUM(ISNULL(totalyeji,0))/COUNT(compid)) 
,realbeautyeji=convert(numeric(20,0),SUM(ISNULL(realbeautyeji,0))/COUNT(compid)) 
,realhairyeji=convert(numeric(20,0),SUM(ISNULL(realhairyeji,0))/COUNT(compid)) 
,realfootyeji=convert(numeric(20,0),SUM(ISNULL(realfootyeji,0))/COUNT(compid)) 
,realtotalyeji=convert(numeric(20,0),SUM(ISNULL(realtotalyeji,0))/COUNT(compid)) 
 from (select compid,ddate=SUBSTRING(ddate,1,6),
beautyeji=convert(numeric(20,0),sum(isnull(beautyeji,0))),hairyeji=convert(numeric(20,0),sum(isnull(hairyeji,0))),
footyeji=convert(numeric(20,0),sum(isnull(footyeji,0))),totalyeji=convert(numeric(20,0),sum(isnull(totalyeji,0))),
realbeautyeji=convert(numeric(20,0),sum(isnull(realbeautyeji,0))),realhairyeji=convert(numeric(20,0),sum(isnull(realhairyeji,0))),
realfootyeji=convert(numeric(20,0),sum(isnull(realfootyeji,0))),realtotalyeji=convert(numeric(20,0),sum(isnull(realtotalyeji,0)))
 from compclasstraderesult 
 where ddate between '20130801' and '20131031' and  ISNULL(totalyeji,0)>0 and ISNULL(realtotalyeji,0)>0 
 group by compid,SUBSTRING(ddate,1,6) ) as a group by compid ) as b
 where c.compid=b.compid
 
 select compid,compname
 ,curbeatyeji,cur2beatyeji,cur3beatyeji,b1beatyeji,b2beatyeji,b3beatyeji
 ,curhairyeji,cur2hairyeji,cur3hairyeji,b1hairyeji,b2hairyeji,b3hairyeji
 ,curfootyeji,cur2footyeji,cur3footyeji,b1footyeji,b2footyeji,b3footyeji
 ,curtotalyeji,cur2totalyeji,cur3totalyeji,b1totalyeji,b2totalyeji,b3totalyeji
 ,currealbeautyeji,cur2realbeautyeji,cur3realbeautyeji,b1realbeautyeji,b2realbeautyeji,b3realbeautyeji
 ,currealhairyeji,cur2realhairyeji,cur3realhairyeji,b1realhairyeji,b2realhairyeji,b3realhairyeji
 ,currealfootyeji,cur2realfootyeji,cur3realfootyeji,b1realfootyeji,b2realfootyeji,b3realfootyeji
 ,currealtotalyeji,cur2realtotalyeji,cur3realtotalyeji,b1realtotalyeji,b2realtotalyeji,b3realtotalyeji
 from classyjanlysis order by compid
 
 
  select compid,compname
 ,curbeatyeji,cast (convert(numeric(20,2),(isnull(curbeatyeji,0)-ISNULL(b1beatyeji,0))/isnull(b1beatyeji,0)*100) as varchar(8))+'%',isnull(curbeatyeji,0)-isnull(b1beatyeji,0)
 ,curhairyeji,cast (convert(numeric(20,2),(isnull(curhairyeji,0)-ISNULL(b1hairyeji,0))/isnull(b1hairyeji,0)*100) as varchar(8))+'%',isnull(curhairyeji,0)-isnull(b1hairyeji,0)
 ,curfootyeji,cast (convert(numeric(20,2),(isnull(curfootyeji,0)-ISNULL(b1footyeji,0))/isnull(b1footyeji,0)*100) as varchar(8))+'%',isnull(curfootyeji,0)-isnull(b1footyeji,0)
 ,curtotalyeji,cast (convert(numeric(20,2),(isnull(curtotalyeji,0)-ISNULL(b1totalyeji,0))/isnull(b1totalyeji,0)*100) as varchar(8))+'%',isnull(curtotalyeji,0)-isnull(b1totalyeji,0)
 ,currealbeautyeji,cast (convert(numeric(20,2),(isnull(currealbeautyeji,0)-ISNULL(b1realbeautyeji,0))/isnull(b1realbeautyeji,0)*100) as varchar(8))+'%',isnull(currealbeautyeji,0)-isnull(b1realbeautyeji,0)
 ,currealhairyeji,cast (convert(numeric(20,2),(isnull(currealhairyeji,0)-ISNULL(b1realhairyeji,0))/isnull(b1realhairyeji,0)*100) as varchar(8))+'%',isnull(currealhairyeji,0)-isnull(b1realhairyeji,0)
 ,currealfootyeji,cast (convert(numeric(20,2),(isnull(currealfootyeji,0)-ISNULL(b1realfootyeji,0))/isnull(b1realfootyeji,0)*100) as varchar(8))+'%',isnull(currealfootyeji,0)-isnull(b1realfootyeji,0)
 ,currealtotalyeji,cast (convert(numeric(20,2),(isnull(currealtotalyeji,0)-ISNULL(b1realtotalyeji,0))/isnull(b1realtotalyeji,0)*100) as varchar(8))+'%',isnull(currealtotalyeji,0)-isnull(b1realtotalyeji,0)
 from classyjanlysis order by compid
 


 select compid,compname,ISNULL(curbeatyeji,0)-ISNULL(b1beatyeji,0),ISNULL(cur2beatyeji,0)-ISNULL(b2beatyeji,0), ISNULL(cur3beatyeji,0)-ISNULL(b3beatyeji,0)
 from classyjanlysis where ISNULL(curbeatyeji,0)>ISNULL(b1beatyeji,0) and ISNULL(cur2beatyeji,0)>ISNULL(b2beatyeji,0) and ISNULL(cur3beatyeji,0)>ISNULL(b3beatyeji,0)
 
 
  select compid,compname,ISNULL(curhairyeji,0)-ISNULL(b1hairyeji,0),ISNULL(cur2hairyeji,0)-ISNULL(b2hairyeji,0), ISNULL(cur3hairyeji,0)-ISNULL(b3hairyeji,0)
 from classyjanlysis where ISNULL(curhairyeji,0)>ISNULL(b1hairyeji,0) and ISNULL(cur2hairyeji,0)>ISNULL(b2hairyeji,0) and ISNULL(cur3hairyeji,0)>ISNULL(b3hairyeji,0)
 
   select compid,compname,ISNULL(curtotalyeji,0)-ISNULL(b1totalyeji,0),ISNULL(cur2totalyeji,0)-ISNULL(b2totalyeji,0), ISNULL(cur3totalyeji,0)-ISNULL(b3totalyeji,0)
 from classyjanlysis where ISNULL(curtotalyeji,0)>ISNULL(b1totalyeji,0) and ISNULL(cur2totalyeji,0)>ISNULL(b2totalyeji,0) and ISNULL(cur3totalyeji,0)>ISNULL(b3totalyeji,0)
 
 
  
  select compid,compname,  ISNULL(currealbeautyeji,0)-ISNULL(b1realbeautyeji,0),ISNULL(cur2realbeautyeji,0)-ISNULL(b2realbeautyeji,0), ISNULL(cur3realbeautyeji,0)-ISNULL(b3realbeautyeji,0)
 from classyjanlysis where ISNULL(currealbeautyeji,0)>ISNULL(b1realbeautyeji,0) and ISNULL(cur2realbeautyeji,0)>ISNULL(b2realbeautyeji,0) and ISNULL(cur3realbeautyeji,0)>ISNULL(b3realbeautyeji,0)
 
 
  select compid,compname,ISNULL(currealhairyeji,0)-ISNULL(b1realhairyeji,0),ISNULL(cur2realhairyeji,0)-ISNULL(b2realhairyeji,0), ISNULL(cur3realhairyeji,0)-ISNULL(b3realhairyeji,0)
 from classyjanlysis where ISNULL(currealhairyeji,0)>ISNULL(b1realhairyeji,0) and ISNULL(cur2realhairyeji,0)>ISNULL(b2realhairyeji,0) and ISNULL(cur3realhairyeji,0)>ISNULL(b3realhairyeji,0)
 
   select compid,compname,ISNULL(currealtotalyeji,0)-ISNULL(b1realtotalyeji,0),ISNULL(cur2realtotalyeji,0)-ISNULL(b2realtotalyeji,0), ISNULL(cur3realtotalyeji,0)-ISNULL(b3realtotalyeji,0)
 from classyjanlysis where ISNULL(currealtotalyeji,0)>ISNULL(b1realtotalyeji,0) and ISNULL(cur2realtotalyeji,0)>ISNULL(b2realtotalyeji,0) and ISNULL(cur3realtotalyeji,0)>ISNULL(b3realtotalyeji,0)
 
 
 
 
 
 select compid,compname,ISNULL(curbeatyeji,0)-ISNULL(b1beatyeji,0),ISNULL(cur2beatyeji,0)-ISNULL(b2beatyeji,0), ISNULL(cur3beatyeji,0)-ISNULL(b3beatyeji,0)
 from classyjanlysis where ISNULL(curbeatyeji,0)<ISNULL(b1beatyeji,0) and ISNULL(cur2beatyeji,0)<ISNULL(b2beatyeji,0) and ISNULL(cur3beatyeji,0)<ISNULL(b3beatyeji,0)
 
 
  select compid,compname,ISNULL(curhairyeji,0)-ISNULL(b1hairyeji,0),ISNULL(cur2hairyeji,0)-ISNULL(b2hairyeji,0), ISNULL(cur3hairyeji,0)-ISNULL(b3hairyeji,0)
 from classyjanlysis where ISNULL(curhairyeji,0)<ISNULL(b1hairyeji,0) and ISNULL(cur2hairyeji,0)<ISNULL(b2hairyeji,0) and ISNULL(cur3hairyeji,0)<ISNULL(b3hairyeji,0)
 
   select compid,compname,ISNULL(curtotalyeji,0)-ISNULL(b1totalyeji,0),ISNULL(cur2totalyeji,0)-ISNULL(b2totalyeji,0), ISNULL(cur3totalyeji,0)-ISNULL(b3totalyeji,0)
 from classyjanlysis where ISNULL(curtotalyeji,0)<ISNULL(b1totalyeji,0) and ISNULL(cur2totalyeji,0)<ISNULL(b2totalyeji,0) and ISNULL(cur3totalyeji,0)<ISNULL(b3totalyeji,0)
 
 
  
  select compid,compname,  ISNULL(currealbeautyeji,0)-ISNULL(b1realbeautyeji,0),ISNULL(cur2realbeautyeji,0)-ISNULL(b2realbeautyeji,0), ISNULL(cur3realbeautyeji,0)-ISNULL(b3realbeautyeji,0)
 from classyjanlysis where ISNULL(currealbeautyeji,0)<ISNULL(b1realbeautyeji,0) and ISNULL(cur2realbeautyeji,0)<ISNULL(b2realbeautyeji,0) and ISNULL(cur3realbeautyeji,0)<ISNULL(b3realbeautyeji,0)
 
 
  select compid,compname,ISNULL(currealhairyeji,0)-ISNULL(b1realhairyeji,0),ISNULL(cur2realhairyeji,0)-ISNULL(b2realhairyeji,0), ISNULL(cur3realhairyeji,0)-ISNULL(b3realhairyeji,0)
 from classyjanlysis where ISNULL(currealhairyeji,0)<ISNULL(b1realhairyeji,0) and ISNULL(cur2realhairyeji,0)<ISNULL(b2realhairyeji,0) and ISNULL(cur3realhairyeji,0)<ISNULL(b3realhairyeji,0)
 
   select compid,compname,ISNULL(currealtotalyeji,0)-ISNULL(b1realtotalyeji,0),ISNULL(cur2realtotalyeji,0)-ISNULL(b2realtotalyeji,0), ISNULL(cur3realtotalyeji,0)-ISNULL(b3realtotalyeji,0)
 from classyjanlysis where ISNULL(currealtotalyeji,0)<ISNULL(b1realtotalyeji,0) and ISNULL(cur2realtotalyeji,0)<ISNULL(b2realtotalyeji,0) and ISNULL(cur3realtotalyeji,0)<ISNULL(b3realtotalyeji,0)
 