
--总部入库
select '产品编码'=ghb03c,'产品类别'=gsb03c,'产品名称'=gfa03c,'单位'=gfa20c,'出库日期'=gha03d,'入库单号'=gha01c,'出库数量'=ghb07f,'出库单价'=ghb10f,'出库金额'=ghb11f 
from ghm02,gfm01,gsm02,ghm01
 where gha00c='001'
and gha00c=ghb00c and gha01c=ghb01c
and gha03d between '20130801' and '20130831'
and gfa00c=ghb00c and gfa01c=ghb03c
and gsb00c='001' and gsb01c='AL' and gsb02c=gfa24c
order by ghb03c 

--总部出库
select '产品编码'=gib03c,'产品类别'=gsb03c,'产品名称'=gfa03c,'单位'=gfa20c,'出库日期'=gia03d,'申请单号'=gia10c,gia05c,'申请门店'=case when ISNULL(gta21c,'')<>'' then gia05c + '现金' else gia05c end ,'申请门店名称'=gae03c,'出库数量'=gib07f,'出库单价'=gib09f,'出库金额'=gib10f 
from gim02,gfm01,gam05,gsm02,gim01
left join gtm01 on gia05c=isnull(gta21c,0) and gia10c=gta01c
 where gia00c='001' and gia09i='2'
and gia00c=gib00c and gia01c=gib01c
and gia03d between '20130801' and '20130831'
and gfa00c=gib00c and gfa01c=gib03c
and gae01c=gfa00c 
and gsb00c='001' and gsb01c='AL' and gsb02c=gfa24c
order by gia05c,gib03c 

--总部入库

select gha03d, gsb03c,
'欧莱雅产品'=SUM(case when gfa24c='01' then  ISNULL(ghb11f,0) else 0 end ) ,
'歌薇产品'=SUM(case when gfa24c='02' then  ISNULL(ghb11f,0) else 0 end ) ,
'菲灵产品'=SUM(case when gfa24c='03' then  ISNULL(ghb11f,0) else 0 end ) ,
'玫丽盼产品'=SUM(case when gfa24c='04' then  ISNULL(ghb11f,0) else 0 end ) ,
'卡诗产品'=SUM(case when gfa24c='05' then  ISNULL(ghb11f,0) else 0 end ) ,
'威娜产品'=SUM(case when gfa24c='06' then  ISNULL(ghb11f,0) else 0 end ) ,
'博柔产品'=SUM(case when gfa24c='07' then  ISNULL(ghb11f,0) else 0 end ) ,
'Capilo产品'=SUM(case when gfa24c='08' then  ISNULL(ghb11f,0) else 0 end ) ,
'尼保迈产品'=SUM(case when gfa24c='09' then  ISNULL(ghb11f,0) else 0 end ) ,
'乔薇尔产品'=SUM(case when gfa24c='10' then  ISNULL(ghb11f,0) else 0 end ) ,
'露新兰产品'=SUM(case when gfa24c='11' then  ISNULL(ghb11f,0) else 0 end ) ,
'华新产品'=SUM(case when gfa24c='12' then  ISNULL(ghb11f,0) else 0 end ) ,
'豆蔻产品'=SUM(case when gfa24c='13' then  ISNULL(ghb11f,0) else 0 end ) ,
'雅兰产品'=SUM(case when gfa24c='14' then  ISNULL(ghb11f,0) else 0 end ) ,
'秋鹏产品'=SUM(case when gfa24c='15' then  ISNULL(ghb11f,0) else 0 end ) ,
'巨邦产品'=SUM(case when gfa24c='16' then  ISNULL(ghb11f,0) else 0 end ) ,
'吉爱丝迪'=SUM(case when gfa24c='17' then  ISNULL(ghb11f,0) else 0 end ) ,
'尚源堂'=SUM(case when gfa24c='18' then  ISNULL(ghb11f,0) else 0 end ) ,
'爱德嘉兰'=SUM(case when gfa24c='19' then  ISNULL(ghb11f,0) else 0 end ) ,
'博医生'=SUM(case when gfa24c='20' then  ISNULL(ghb11f,0) else 0 end ) ,
'利鑫产品'=SUM(case when gfa24c='21' then  ISNULL(ghb11f,0) else 0 end ) ,
'美芬妮'=SUM(case when gfa24c='22' then  ISNULL(ghb11f,0) else 0 end ) ,
'瑜伽经络'=SUM(case when gfa24c='23' then  ISNULL(ghb11f,0) else 0 end ) ,
'阿玛尼'=SUM(case when gfa24c='24' then  ISNULL(ghb11f,0) else 0 end ) ,
'集美伊立信'=SUM(case when gfa24c='25' then  ISNULL(ghb11f,0) else 0 end ) ,
'玛索产品'=SUM(case when gfa24c='26' then  ISNULL(ghb11f,0) else 0 end ) ,
'蒂凡妮'=SUM(case when gfa24c='27' then  ISNULL(ghb11f,0) else 0 end ) ,
'韵瑶瑶浴'=SUM(case when gfa24c='28' then  ISNULL(ghb11f,0) else 0 end ) ,
'莫拉诺'=SUM(case when gfa24c='29' then  ISNULL(ghb11f,0) else 0 end ) ,
'斯诺菲产品'=SUM(case when gfa24c='30' then  ISNULL(ghb11f,0) else 0 end ) ,
'纤柔产品'=SUM(case when gfa24c='31' then  ISNULL(ghb11f,0) else 0 end ) ,
'LP'=SUM(case when gfa24c='88' then  ISNULL(ghb11f,0) else 0 end ) ,
'总计'=SUM( ISNULL(ghb11f,0) )
from ghm01,ghm02,gsm02,gfm01  
where gha00c='001' and gha03d between '20130801' and '20130831'
and gha00c=ghb00c and gha01c=ghb01c 
and gsb00c='001' and gsb01c='AL' and gsb02c=gfa24c
and gfa00c='001' and gfa01c=ghb03c
group by gha03d,gsb03c,gfa24c
order by gha03d


--总部出库

select gia03d, gsb03c,
'欧莱雅产品'=SUM(case when gfa24c='01' then  ISNULL(gib10f,0) else 0 end ) ,
'歌薇产品'=SUM(case when gfa24c='02' then  ISNULL(gib10f,0) else 0 end ) ,
'菲灵产品'=SUM(case when gfa24c='03' then  ISNULL(gib10f,0) else 0 end ) ,
'玫丽盼产品'=SUM(case when gfa24c='04' then  ISNULL(gib10f,0) else 0 end ) ,
'卡诗产品'=SUM(case when gfa24c='05' then  ISNULL(gib10f,0) else 0 end ) ,
'威娜产品'=SUM(case when gfa24c='06' then  ISNULL(gib10f,0) else 0 end ) ,
'博柔产品'=SUM(case when gfa24c='07' then  ISNULL(gib10f,0) else 0 end ) ,
'Capilo产品'=SUM(case when gfa24c='08' then  ISNULL(gib10f,0) else 0 end ) ,
'尼保迈产品'=SUM(case when gfa24c='09' then  ISNULL(gib10f,0) else 0 end ) ,
'乔薇尔产品'=SUM(case when gfa24c='10' then  ISNULL(gib10f,0) else 0 end ) ,
'露新兰产品'=SUM(case when gfa24c='11' then  ISNULL(gib10f,0) else 0 end ) ,
'华新产品'=SUM(case when gfa24c='12' then  ISNULL(gib10f,0) else 0 end ) ,
'豆蔻产品'=SUM(case when gfa24c='13' then  ISNULL(gib10f,0) else 0 end ) ,
'雅兰产品'=SUM(case when gfa24c='14' then  ISNULL(gib10f,0) else 0 end ) ,
'秋鹏产品'=SUM(case when gfa24c='15' then  ISNULL(gib10f,0) else 0 end ) ,
'巨邦产品'=SUM(case when gfa24c='16' then  ISNULL(gib10f,0) else 0 end ) ,
'吉爱丝迪'=SUM(case when gfa24c='17' then  ISNULL(gib10f,0) else 0 end ) ,
'尚源堂'=SUM(case when gfa24c='18' then  ISNULL(gib10f,0) else 0 end ) ,
'爱德嘉兰'=SUM(case when gfa24c='19' then  ISNULL(gib10f,0) else 0 end ) ,
'博医生'=SUM(case when gfa24c='20' then  ISNULL(gib10f,0) else 0 end ) ,
'利鑫产品'=SUM(case when gfa24c='21' then  ISNULL(gib10f,0) else 0 end ) ,
'美芬妮'=SUM(case when gfa24c='22' then  ISNULL(gib10f,0) else 0 end ) ,
'瑜伽经络'=SUM(case when gfa24c='23' then  ISNULL(gib10f,0) else 0 end ) ,
'阿玛尼'=SUM(case when gfa24c='24' then  ISNULL(gib10f,0) else 0 end ) ,
'集美伊立信'=SUM(case when gfa24c='25' then  ISNULL(gib10f,0) else 0 end ) ,
'玛索产品'=SUM(case when gfa24c='26' then  ISNULL(gib10f,0) else 0 end ) ,
'蒂凡妮'=SUM(case when gfa24c='27' then  ISNULL(gib10f,0) else 0 end ) ,
'韵瑶瑶浴'=SUM(case when gfa24c='28' then  ISNULL(gib10f,0) else 0 end ) ,
'莫拉诺'=SUM(case when gfa24c='29' then  ISNULL(gib10f,0) else 0 end ) ,
'斯诺菲产品'=SUM(case when gfa24c='30' then  ISNULL(gib10f,0) else 0 end ) ,
'纤柔产品'=SUM(case when gfa24c='31' then  ISNULL(gib10f,0) else 0 end ) ,
'LP'=SUM(case when gfa24c='88' then  ISNULL(gib10f,0) else 0 end ) ,
'总计'=SUM( ISNULL(gib10f,0) )
from gim01,gim02,gsm02,gfm01  
where gia00c='001' and gia03d between '20130801' and '20130831'
and gia00c=gib00c and gia01c=gib01c 
and gsb00c='001' and gsb01c='AL' and gsb02c=gfa24c
and gfa00c='001' and gfa01c=gib03c
group by gia03d,gsb03c,gfa24c
order by gia03d
