



--一月份的门店分类业绩
  select 门店编号=compid,门店名称=compname,
  美容虚业绩=convert(numeric(20,1),SUM(ISNULL(beautyeji,0))),美发虚业绩=convert(numeric(20,1),SUM(ISNULL(hairyeji,0))),
  烫染虚业绩=convert(numeric(20,1),SUM(ISNULL(footyeji,0))),总虚业绩=convert(numeric(20,1),SUM(ISNULL(totalyeji,0))),
  美容实业绩=convert(numeric(20,1),SUM(ISNULL(realbeautyeji,0))),
  美发实业绩=convert(numeric(20,1),SUM(ISNULL(realhairyeji,0))),烫染实业绩=convert(numeric(20,1),SUM(ISNULL(realfootyeji,0))),
  总实业绩=convert(numeric(20,1),SUM(ISNULL(realtotalyeji,0)) )
  from compclasstraderesult,companyinfo   
  where ddate between '20140401' and '20140430' and compid=compno
  group by compid,compname
  order by compid
--第二页：是4月对比3月1月12月的平均数

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
  where ddate between '20140401' and '20140430' 
  group by compid) a,
  (select compid,beautyeji=SUM(ISNULL(beautyeji,0))/3,hairyeji=SUM(ISNULL(hairyeji,0))/3,
  footyeji=SUM(ISNULL(footyeji,0))/3,fingeryeji=SUM(ISNULL(fingeryeji,0))/3,
  totalyeji=SUM(ISNULL(totalyeji,0))/3,realbeautyeji=SUM(ISNULL(realbeautyeji,0))/3,
  realhairyeji=SUM(ISNULL(realhairyeji,0))/3,realfootyeji=SUM(ISNULL(realfootyeji,0))/3,
  realfingeryeji=SUM(ISNULL(realfingeryeji,0))/3,realtotalyeji=SUM(ISNULL(realtotalyeji,0) )/3
  from compclasstraderesult   
  where (ddate between '20140301' and '20140331')  or (ddate between '20131201' and '20140131')
  group by compid) b,companyinfo
  where a.compid=b.compid and a.compid=compno and isnull(a.totalyeji,0)>0
  order by compid asc
  
  --第三页：3连长和3连跌是——
--     4月对比3月1月12月平均数
--     3月对比1月12月11月平均数
--     1月对比12月11月10月平均数

  --create table shopyejitargetinfo_04
  --(
		--strcompid				varchar(10)		null,
		--strcompname				varchar(30)		null,
		--totalyeji_404			float			null,
		--beautyeji_404			float			null,
		--hairyeji_404			float			null,
		--footyeji_404			float			null,
		--realtotalyeji_404		float			null,
		--realtbeautyeji_404		float			null,
		--realthairyeji_404		float			null,
		--realtfootyeji_404		float			null,
		
		
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
		
		--totalyeji_312_3			float			null,
		--beautyeji_312_3			float			null,
		--hairyeji_312_3			float			null,
		--footyeji_312_3			float			null,
		--realtotalyeji_312_3		float			null,
		--realtbeautyeji_312_3	float			null,
		--realthairyeji_312_3		float			null,
		--realtfootyeji_312_3		float			null,
		
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
		--realtfootyeji_310_12	float			null
  --)
  
   delete shopyejitargetinfo_04
  insert shopyejitargetinfo_04(strcompid,totalyeji_404,beautyeji_404,hairyeji_404,footyeji_404,realtotalyeji_404,realtbeautyeji_404,realthairyeji_404,realtfootyeji_404)
  select compid,totalyeji=SUM(ISNULL(totalyeji,0)),beautyeji=SUM(ISNULL(beautyeji,0)),  hairyeji=SUM(ISNULL(hairyeji,0)), 
   footyeji=SUM(ISNULL(footyeji,0)),realtotalyeji=SUM(ISNULL(realtotalyeji,0) ),  
  realbeautyeji=SUM(ISNULL(realbeautyeji,0)),  realhairyeji=SUM(ISNULL(realhairyeji,0)),
  realfootyeji=SUM(ISNULL(realfootyeji,0))
  from compclasstraderesult,companyinfo   
  where ddate between '20140401' and '20140430' and compid=compno and ISNULL(totalyeji,0)>0
  group by compid


  update a set totalyeji_403=totalyeji,beautyeji_403=beautyeji,
			   hairyeji_403=hairyeji,footyeji_403=footyeji,
			   realtotalyeji_403=realtotalyeji,realtbeautyeji_403=realbeautyeji
			   ,realthairyeji_403=realhairyeji,realtfootyeji_403=realfootyeji
  from shopyejitargetinfo_04 a,( select compid,totalyeji=SUM(ISNULL(totalyeji,0)),beautyeji=SUM(ISNULL(beautyeji,0)),  hairyeji=SUM(ISNULL(hairyeji,0)), 
   footyeji=SUM(ISNULL(footyeji,0)),realtotalyeji=SUM(ISNULL(realtotalyeji,0) ),  
  realbeautyeji=SUM(ISNULL(realbeautyeji,0)),  realhairyeji=SUM(ISNULL(realhairyeji,0)),
  realfootyeji=SUM(ISNULL(realfootyeji,0))
  from compclasstraderesult,companyinfo   
  where ddate between '20140301' and '20140331' and compid=compno
  group by compid) b
  where a.strcompid=b.compid
  
 
  update a set totalyeji_401=totalyeji,beautyeji_401=beautyeji,
			   hairyeji_401=hairyeji,footyeji_401=footyeji,
			   realtotalyeji_401=realtotalyeji,realtbeautyeji_401=realbeautyeji
			   ,realthairyeji_401=realhairyeji,realtfootyeji_401=realfootyeji
  from shopyejitargetinfo_04 a,( select compid,totalyeji=SUM(ISNULL(totalyeji,0)),beautyeji=SUM(ISNULL(beautyeji,0)),  hairyeji=SUM(ISNULL(hairyeji,0)), 
   footyeji=SUM(ISNULL(footyeji,0)),realtotalyeji=SUM(ISNULL(realtotalyeji,0) ),  
  realbeautyeji=SUM(ISNULL(realbeautyeji,0)),  realhairyeji=SUM(ISNULL(realhairyeji,0)),
  realfootyeji=SUM(ISNULL(realfootyeji,0))
  from compclasstraderesult,companyinfo   
  where ddate between '20140101' and '20140131' and compid=compno
  group by compid) b
  where a.strcompid=b.compid


update a set totalyeji_312_3=totalyeji/3,beautyeji_312_3=beautyeji/3,
			   hairyeji_312_3=hairyeji/3,footyeji_312_3=footyeji/3,
			   realtotalyeji_312_3=realtotalyeji/3,realtbeautyeji_312_3=realbeautyeji/3
			   ,realthairyeji_312_3=realhairyeji/3,realtfootyeji_312_3=realfootyeji/3
  from shopyejitargetinfo_04 a,( select compid,totalyeji=SUM(ISNULL(totalyeji,0)),beautyeji=SUM(ISNULL(beautyeji,0)),  hairyeji=SUM(ISNULL(hairyeji,0)), 
   footyeji=SUM(ISNULL(footyeji,0)),realtotalyeji=SUM(ISNULL(realtotalyeji,0) ),  
  realbeautyeji=SUM(ISNULL(realbeautyeji,0)),  realhairyeji=SUM(ISNULL(realhairyeji,0)),
  realfootyeji=SUM(ISNULL(realfootyeji,0))
  from compclasstraderesult,companyinfo   
  where ((ddate between '20140301' and '20140331')  or (ddate between '20131201' and '20140131')) and compid=compno
  group by compid) b
  where a.strcompid=b.compid
 

  update a set totalyeji_311_1=totalyeji/3,beautyeji_311_1=beautyeji/3,
			   hairyeji_311_1=hairyeji/3,footyeji_311_1=footyeji/3,
			   realtotalyeji_311_1=realtotalyeji/3,realtbeautyeji_311_1=realbeautyeji/3
			   ,realthairyeji_311_1=realhairyeji/3,realtfootyeji_311_1=realfootyeji/3
  from shopyejitargetinfo_04 a,( select compid,totalyeji=SUM(ISNULL(totalyeji,0)),beautyeji=SUM(ISNULL(beautyeji,0)),  hairyeji=SUM(ISNULL(hairyeji,0)), 
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
  from shopyejitargetinfo_04 a,( select compid,totalyeji=SUM(ISNULL(totalyeji,0)),beautyeji=SUM(ISNULL(beautyeji,0)),  hairyeji=SUM(ISNULL(hairyeji,0)), 
   footyeji=SUM(ISNULL(footyeji,0)),realtotalyeji=SUM(ISNULL(realtotalyeji,0) ),  
  realbeautyeji=SUM(ISNULL(realbeautyeji,0)),  realhairyeji=SUM(ISNULL(realhairyeji,0)),
  realfootyeji=SUM(ISNULL(realfootyeji,0))
  from compclasstraderesult,companyinfo   
  where ddate between '20131001' and '20131231' and compid=compno
  group by compid) b
  where a.strcompid=b.compid
  
update a set strcompname=compname
from shopyejitargetinfo_04 a,companyinfo
where strcompid=compno





	--总虚业绩3连长
    select strcompid,strcompname,
    convert(numeric(20,1),isnull(totalyeji_404,0)-isnull(totalyeji_312_3,0)) ,
    convert(numeric(20,1),isnull(totalyeji_403,0)-isnull(totalyeji_311_1,0)),
    convert(numeric(20,1),isnull(totalyeji_401,0)-isnull(totalyeji_310_12,0))
    from shopyejitargetinfo_04
    where  isnull(totalyeji_404,0)>isnull(totalyeji_312_3,0)
     and isnull(totalyeji_403,0)>isnull(totalyeji_311_1,0) 
     and isnull(totalyeji_401,0)>isnull(totalyeji_310_12,0)
    order by strcompid
    
   
    
    --美容虚业绩3连长
    select strcompid,strcompname,
    convert(numeric(20,1),isnull(beautyeji_404,0)-isnull(beautyeji_312_3,0)) ,
    substring(cast (convert(numeric(20,4),(isnull(beautyeji_404,0)-isnull(beautyeji_312_3,0))/isnull(beautyeji_312_3,0))*100 as varchar(20)),1,5)+'%',
    convert(numeric(20,1),isnull(beautyeji_403,0)-isnull(beautyeji_311_1,0)),
    substring(cast (convert(numeric(20,4),(isnull(beautyeji_403,0)-isnull(beautyeji_311_1,0))/isnull(beautyeji_311_1,0))*100 as varchar(20)),1,5)+'%',
    convert(numeric(20,1), isnull(beautyeji_401,0)-isnull(beautyeji_310_12,0)),
    substring(cast (convert(numeric(20,4),(isnull(beautyeji_401,0)-isnull(beautyeji_310_12,0))/isnull(beautyeji_310_12,0))*100 as varchar(20)),1,5)+'%'
    from shopyejitargetinfo_04
    where  isnull(beautyeji_404,0)>isnull(beautyeji_312_3,0) 
    and isnull(beautyeji_403,0)>isnull(beautyeji_311_1,0) 
    and isnull(beautyeji_401,0)>isnull(beautyeji_310_12,0)
    order by strcompid
    
      --美发虚业绩3连长
    select strcompid,strcompname,
    convert(numeric(20,1),isnull(hairyeji_404,0)-isnull(hairyeji_312_3,0)) ,
    substring(cast (convert(numeric(20,4),(isnull(hairyeji_404,0)-isnull(hairyeji_312_3,0))/isnull(hairyeji_312_3,0))*100 as varchar(20)),1,5)+'%',
    convert(numeric(20,1),isnull(hairyeji_403,0)-isnull(hairyeji_311_1,0)),
    substring(cast (convert(numeric(20,4),(isnull(hairyeji_403,0)-isnull(hairyeji_311_1,0))/isnull(hairyeji_311_1,0))*100 as varchar(20)),1,5)+'%',
    convert(numeric(20,1),isnull(hairyeji_401,0)-isnull(hairyeji_310_12,0)),
    substring(cast (convert(numeric(20,4),(isnull(hairyeji_401,0)-isnull(hairyeji_310_12,0))/isnull(hairyeji_310_12,0))*100 as varchar(20)),1,5)+'%'
    from shopyejitargetinfo_04
    where  isnull(hairyeji_404,0)>isnull(hairyeji_312_3,0)
      and isnull(hairyeji_403,0)>isnull(hairyeji_311_1,0)
      and isnull(hairyeji_401,0)>isnull(hairyeji_310_12,0)
    order by strcompid
    
     --烫染虚业绩3连长
    select strcompid,strcompname,
    convert(numeric(20,1),isnull(footyeji_404,0)-isnull(footyeji_312_3,0)) ,
    convert(numeric(20,1),isnull(footyeji_403,0)-isnull(footyeji_311_1,0)),
    convert(numeric(20,1),isnull(footyeji_401,0)-isnull(footyeji_310_12,0))
    from shopyejitargetinfo_04
    where  isnull(footyeji_404,0)>isnull(footyeji_312_3,0)
      and isnull(footyeji_403,0)>isnull(footyeji_311_1,0)
      and isnull(footyeji_401,0)>isnull(footyeji_310_12,0)
    order by strcompid
    
    
     --总实业绩3连长
    select strcompid,strcompname,
    convert(numeric(20,1),isnull(realtotalyeji_404,0)-isnull(realtotalyeji_312_3,0)) ,
      substring(cast (convert(numeric(20,4),(isnull(realtotalyeji_404,0)-isnull(realtotalyeji_312_3,0))/isnull(realtotalyeji_312_3,0))*100 as varchar(20)),1,5)+'%',
   convert(numeric(20,1),isnull(realtotalyeji_403,0)-isnull(realtotalyeji_311_1,0)),
      substring(cast (convert(numeric(20,4),(isnull(realtotalyeji_403,0)-isnull(realtotalyeji_311_1,0))/isnull(realtotalyeji_311_1,0))*100 as varchar(20)),1,5)+'%',
   convert(numeric(20,1),isnull(realtotalyeji_401,0)-isnull(realtotalyeji_310_12,0)),
      substring(cast (convert(numeric(20,4),(isnull(realtotalyeji_401,0)-isnull(realtotalyeji_310_12,0))/isnull(realtotalyeji_310_12,0))*100 as varchar(20)),1,5)+'%'
   from shopyejitargetinfo_04
    where  isnull(realtotalyeji_404,0)>isnull(realtotalyeji_312_3,0)
      and isnull(realtotalyeji_403,0)>isnull(realtotalyeji_311_1,0)
      and isnull(realtotalyeji_401,0)>isnull(realtotalyeji_310_12,0)
    order by strcompid
    
    
    --美容实业绩3连长
    select strcompid,strcompname,
    convert(numeric(20,1),isnull(realtbeautyeji_404,0)-isnull(realtbeautyeji_312_3,0)) ,
      substring(cast (convert(numeric(20,4),(isnull(realtbeautyeji_404,0)-isnull(realtbeautyeji_312_3,0))/isnull(realtbeautyeji_312_3,0))*100 as varchar(20)),1,5)+'%',
   convert(numeric(20,1),isnull(realtbeautyeji_403,0)-isnull(realtbeautyeji_311_1,0)),
      substring(cast (convert(numeric(20,4),(isnull(realtbeautyeji_403,0)-isnull(realtbeautyeji_311_1,0))/isnull(realtbeautyeji_311_1,0))*100 as varchar(20)),1,5)+'%',
   convert(numeric(20,1),isnull(realtbeautyeji_401,0)-isnull(realtbeautyeji_310_12,0)),
      substring(cast (convert(numeric(20,4),(isnull(realtbeautyeji_401,0)-isnull(realtbeautyeji_310_12,0))/isnull(realtbeautyeji_310_12,0))*100 as varchar(20)),1,5)+'%'
   from shopyejitargetinfo_04
    where  isnull(realtbeautyeji_404,0)>isnull(realtbeautyeji_312_3,0)
      and isnull(realtbeautyeji_403,0)>isnull(realtbeautyeji_311_1,0)
      and isnull(realtbeautyeji_401,0)>isnull(realtbeautyeji_310_12,0)
    order by strcompid
    
       --美发实业绩3连长
    select strcompid,strcompname,
    convert(numeric(20,1),isnull(realthairyeji_404,0)-isnull(realthairyeji_312_3,0)) ,
     substring(cast (convert(numeric(20,4),(isnull(realthairyeji_404,0)-isnull(realthairyeji_312_3,0))/isnull(realthairyeji_312_3,0))*100 as varchar(20)),1,5)+'%',
   convert(numeric(20,1),isnull(realthairyeji_403,0)-isnull(realthairyeji_311_1,0)),
    substring(cast (convert(numeric(20,4),(isnull(realthairyeji_403,0)-isnull(realthairyeji_311_1,0))/isnull(realthairyeji_311_1,0))*100 as varchar(20)),1,5)+'%',
    convert(numeric(20,1),isnull(realthairyeji_401,0)-isnull(realthairyeji_310_12,0)),
     substring(cast (convert(numeric(20,4),(isnull(realthairyeji_401,0)-isnull(realthairyeji_310_12,0))/isnull(realthairyeji_310_12,0))*100 as varchar(20)),1,5)+'%'
   from shopyejitargetinfo_04
    where  isnull(realthairyeji_404,0)>isnull(realthairyeji_312_3,0)
      and isnull(realthairyeji_403,0)>isnull(realthairyeji_311_1,0)
      and isnull(realthairyeji_401,0)>isnull(realthairyeji_310_12,0)
    order by strcompid
    
    
       --烫染实业绩3连长
    select strcompid,strcompname,
    convert(numeric(20,1),isnull(realtfootyeji_404,0)-isnull(realtfootyeji_312_3,0))  ,
    convert(numeric(20,1),isnull(realtfootyeji_403,0)-isnull(realtfootyeji_311_1,0)) ,
    convert(numeric(20,1),isnull(realtfootyeji_401,0)-isnull(realtfootyeji_310_12,0)) 
    from shopyejitargetinfo_04
    where  isnull(realtfootyeji_404,0)>isnull(realtfootyeji_312_3,0)
      and isnull(realtfootyeji_403,0)>isnull(realtfootyeji_311_1,0)
      and isnull(realtfootyeji_401,0)>isnull(realtfootyeji_310_12,0)
    order by strcompid
    
    
    --006	虹梅店	-420174.0	-316843.3	-488666.6
    --总虚业绩3连跌
    select strcompid,strcompname,
    convert(numeric(20,1),isnull(totalyeji_404,0)-isnull(totalyeji_312_3,0)) ,
       substring(cast (convert(numeric(20,4),(isnull(totalyeji_404,0)-isnull(totalyeji_312_3,0))/isnull(totalyeji_312_3,0))*100 as varchar(20)),1,5)+'%',
   convert(numeric(20,1),isnull(totalyeji_403,0)-isnull(totalyeji_311_1,0)),
       substring(cast (convert(numeric(20,4),(isnull(totalyeji_403,0)-isnull(totalyeji_311_1,0))/isnull(totalyeji_311_1,0))*100 as varchar(20)),1,5)+'%',
   convert(numeric(20,1),isnull(totalyeji_401,0)-isnull(totalyeji_310_12,0)),
       substring(cast (convert(numeric(20,4),(isnull(totalyeji_401,0)-isnull(totalyeji_310_12,0))/isnull(totalyeji_310_12,0))*100 as varchar(20)),1,5)+'%'
   from shopyejitargetinfo_04
    where  isnull(totalyeji_404,0)<isnull(totalyeji_312_3,0) 
    and isnull(totalyeji_403,0)<isnull(totalyeji_311_1,0)
     and isnull(totalyeji_401,0)<isnull(totalyeji_310_12,0)
    order by strcompid
    --美容虚业绩3连跌
    select strcompid,strcompname,
    convert(numeric(20,1),isnull(beautyeji_404,0)-isnull(beautyeji_312_3,0)) ,
      substring(cast (convert(numeric(20,4),(isnull(beautyeji_404,0)-isnull(beautyeji_312_3,0))/isnull(beautyeji_312_3,0))*100 as varchar(20)),1,5)+'%',
   convert(numeric(20,1),isnull(beautyeji_403,0)-isnull(beautyeji_311_1,0)),
      substring(cast (convert(numeric(20,4),(isnull(beautyeji_403,0)-isnull(beautyeji_311_1,0))/isnull(beautyeji_311_1,0))*100 as varchar(20)),1,5)+'%',
   convert(numeric(20,1),isnull(beautyeji_401,0)-isnull(beautyeji_310_12,0)),
      substring(cast (convert(numeric(20,4),(isnull(beautyeji_401,0)-isnull(beautyeji_310_12,0))/isnull(beautyeji_310_12,0))*100 as varchar(20)),1,5)+'%'
   from shopyejitargetinfo_04
    where  isnull(beautyeji_404,0)<isnull(beautyeji_312_3,0) 
    and isnull(beautyeji_403,0)<isnull(beautyeji_311_1,0) 
    and isnull(beautyeji_401,0)<isnull(beautyeji_310_12,0)
    order by strcompid
    
      --美发虚业绩3连跌
    select strcompid,strcompname,
    convert(numeric(20,1),isnull(hairyeji_404,0)-isnull(hairyeji_312_3,0)) ,
       substring(cast (convert(numeric(20,4),(isnull(hairyeji_404,0)-isnull(hairyeji_312_3,0))/isnull(hairyeji_312_3,0))*100 as varchar(20)),1,5)+'%',
   convert(numeric(20,1),isnull(hairyeji_403,0)-isnull(hairyeji_311_1,0)),
       substring(cast (convert(numeric(20,4),(isnull(hairyeji_403,0)-isnull(hairyeji_311_1,0))/isnull(hairyeji_311_1,0))*100 as varchar(20)),1,5)+'%',
   convert(numeric(20,1),isnull(hairyeji_401,0)-isnull(hairyeji_310_12,0)),
      substring(cast (convert(numeric(20,4),(isnull(hairyeji_401,0)-isnull(hairyeji_310_12,0))/isnull(hairyeji_310_12,0))*100 as varchar(20)),1,5)+'%'
    from shopyejitargetinfo_04
    where  isnull(hairyeji_404,0)<isnull(hairyeji_312_3,0)
      and isnull(hairyeji_403,0)<isnull(hairyeji_311_1,0)
      and isnull(hairyeji_401,0)<isnull(hairyeji_310_12,0)
    order by strcompid
    
     --烫染虚业绩3连跌
    select strcompid,strcompname,
    convert(numeric(20,1),isnull(footyeji_404,0)-isnull(footyeji_312_3,0)) ,
    convert(numeric(20,1),isnull(footyeji_403,0)-isnull(footyeji_311_1,0)),
    convert(numeric(20,1),isnull(footyeji_401,0)-isnull(footyeji_310_12,0))
    from shopyejitargetinfo_04
    where  isnull(footyeji_404,0)<isnull(footyeji_312_3,0)
      and isnull(footyeji_403,0)<isnull(footyeji_311_1,0)
      and isnull(footyeji_401,0)<isnull(footyeji_310_12,0)
    order by strcompid
    
    
     --总实业绩3连跌
    select strcompid,strcompname,
     convert(numeric(20,1),isnull(realtotalyeji_404,0)-isnull(realtotalyeji_312_3,0)) ,
     convert(numeric(20,1),isnull(realtotalyeji_403,0)-isnull(realtotalyeji_311_1,0)),
     convert(numeric(20,1),isnull(realtotalyeji_401,0)-isnull(realtotalyeji_310_12,0))
    from shopyejitargetinfo_04
    where  isnull(realtotalyeji_404,0)<isnull(realtotalyeji_312_3,0)
      and isnull(realtotalyeji_403,0)<isnull(realtotalyeji_311_1,0)
      and isnull(realtotalyeji_401,0)<isnull(realtotalyeji_310_12,0)
    order by strcompid
    
    
    --美容实业绩3连跌
    select strcompid,strcompname,
    convert(numeric(20,1),isnull(realtbeautyeji_404,0)-isnull(realtbeautyeji_312_3,0)) ,
      substring(cast (convert(numeric(20,4),(isnull(realtbeautyeji_404,0)-isnull(realtbeautyeji_312_3,0))/isnull(realtbeautyeji_312_3,0))*100 as varchar(20)),1,5)+'%' ,
   convert(numeric(20,1),isnull(realtbeautyeji_403,0)-isnull(realtbeautyeji_311_1,0)),
      substring(cast (convert(numeric(20,4),(isnull(realtbeautyeji_403,0)-isnull(realtbeautyeji_311_1,0))/isnull(realtbeautyeji_311_1,0))*100 as varchar(20)),1,5)+'%' ,
   convert(numeric(20,1),isnull(realtbeautyeji_401,0)-isnull(realtbeautyeji_310_12,0)),
     substring(cast (convert(numeric(20,4),(isnull(realtbeautyeji_401,0)-isnull(realtbeautyeji_310_12,0))/isnull(realtbeautyeji_310_12,0))*100 as varchar(20)),1,5)+'%' 
    from shopyejitargetinfo_04
    where  isnull(realtbeautyeji_404,0)<isnull(realtbeautyeji_312_3,0)
      and isnull(realtbeautyeji_403,0)<isnull(realtbeautyeji_311_1,0)
      and isnull(realtbeautyeji_401,0)<isnull(realtbeautyeji_310_12,0)
    order by strcompid
    
       --美发实业绩3连跌
    select strcompid,strcompname,
    convert(numeric(20,1),isnull(realthairyeji_404,0)-isnull(realthairyeji_312_3,0)) ,
      substring(cast (convert(numeric(20,4),(isnull(realthairyeji_404,0)-isnull(realthairyeji_312_3,0))/isnull(realthairyeji_312_3,0))*100 as varchar(20)),1,5)+'%' ,
   convert(numeric(20,1),isnull(realthairyeji_403,0)-isnull(realthairyeji_311_1,0)),
      substring(cast (convert(numeric(20,4),(isnull(realthairyeji_403,0)-isnull(realthairyeji_311_1,0))/isnull(realthairyeji_311_1,0))*100 as varchar(20)),1,5)+'%' ,
   convert(numeric(20,1),isnull(realthairyeji_401,0)-isnull(realthairyeji_310_12,0)),
     substring(cast (convert(numeric(20,4),(isnull(realthairyeji_401,0)-isnull(realthairyeji_310_12,0))/isnull(realthairyeji_310_12,0))*100 as varchar(20)),1,5)+'%' 
    from shopyejitargetinfo_04
    where  isnull(realthairyeji_404,0)<isnull(realthairyeji_312_3,0)
      and isnull(realthairyeji_403,0)<isnull(realthairyeji_311_1,0)
      and isnull(realthairyeji_401,0)<isnull(realthairyeji_310_12,0)
    order by strcompid
    
    
       --烫染实业绩3连跌
    select strcompid,strcompname,
    convert(numeric(20,1),isnull(realtfootyeji_404,0)-isnull(realtfootyeji_312_3,0)) ,
       substring(cast (convert(numeric(20,4),(isnull(realtfootyeji_404,0)-isnull(realtfootyeji_312_3,0))/isnull(realtfootyeji_312_3,0))*100 as varchar(20)),1,5)+'%' ,
   convert(numeric(20,1),isnull(realtfootyeji_403,0)-isnull(realtfootyeji_311_1,0)),
      substring(cast (convert(numeric(20,4),(isnull(realtfootyeji_403,0)-isnull(realtfootyeji_311_1,0))/isnull(realtfootyeji_311_1,0))*100 as varchar(20)),1,5)+'%' ,
    convert(numeric(20,1),isnull(realtfootyeji_401,0)-isnull(realtfootyeji_310_12,0)),
      substring(cast (convert(numeric(20,4),(isnull(realtfootyeji_401,0)-isnull(realtfootyeji_310_12,0))/isnull(realtfootyeji_310_12,0))*100 as varchar(20)),1,5)+'%' 
    from shopyejitargetinfo_04
    where  isnull(realtfootyeji_404,0)<isnull(realtfootyeji_312_3,0)
      and isnull(realtfootyeji_403,0)<isnull(realtfootyeji_311_1,0)
      and isnull(realtfootyeji_401,0)<isnull(realtfootyeji_310_12,0)
    order by strcompid
    
    
    
     select 门店编号,门店名称,
     总虚业绩指标=convert(numeric(20,1),ISNULL(ttotalyeji,0)),完成总虚业绩,
     总实业绩指标=convert(numeric(20,1),ISNULL(trealtotalyeji,0)),完成总实业绩 ,
       离职人数=convert(numeric(20,1),ISNULL(staffmangernocount,0) )
     from  (select 门店编号=compid,门店名称=compname,
  完成总虚业绩=convert(numeric(20,1),SUM(ISNULL(totalyeji,0))),
  完成总实业绩=convert(numeric(20,1),SUM(ISNULL(realtotalyeji,0)) )

  from compclasstraderesult,companyinfo   
  where ddate between '20140401' and '20140430' and compid=compno
  group by compid,compname) ff
  left join (   select compid,ttotalyeji,trealtotalyeji from storetargetinfo where targetmonth='201404' and targetflag=1) gg on ff.门店编号=gg.compid
  left join ( select appchangecompid,staffmangernocount=COUNT(distinct staffmangerno)
   from  staffchangeinfo a,staffinfo b 
   where changetype=1 and validatestartdate between '20140401' and '20140430'   and ISNULL(billflag,0)=8
   and a.staffmangerno=b.manageno and b.department in ('002','003','004','005','006','007') 
   and  datediff(DAY,arrivaldate,validateenddate)>60
   group by appchangecompid ) kk on ff.门店编号=kk.appchangecompid
  where ISNULL(完成总虚业绩,0)>0
  order by 门店编号
  
  