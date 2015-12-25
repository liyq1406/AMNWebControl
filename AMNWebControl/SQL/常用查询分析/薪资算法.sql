006 008
--烫染部 后勤部 大厅接待
update staffinfo set absencesalary=1 where department in ('006','008')
update staffinfo set absencesalary=1 where position='010'
--收银员 调配师
update staffinfo set absencesalary=2 where position in ('002','01201','01202','01203')
--美容经理 美容顾问 
update staffinfo set absencesalary=3 where position in ('008','00103','00101')
--大堂经理
update staffinfo set absencesalary=4 where position in ('00105','0010502','0010503','00102')


--只有美容经理 00101    0.02 美容部实业绩	6  ,门店月度奖金
--美容顾问     00103    0.02 美容部实业绩	6  ,门店月度奖金
--大堂经理(C) 00105		0.001总虚业绩		4
--大堂经理(B) 0010502   0.001总虚业绩		4
--大堂经理(A) 0010503   0.004总虚业绩		4
--烫染督导 008

select * into staffinfobak20140305 from staffinfo 
select position from staffinfo where ISNULL(resultrate,0)>0 or ISNULL(baseresult,0)>0 group by position

update  staffinfo set resulttye='',resultrate=0,baseresult=0 where position not in ('00101','00103','00105','0010502','0010503','008')

update staffinfo set resulttye=6 ,resultrate=0.02 where position ='00101'
update staffinfo set resulttye=6 ,resultrate=0.02 where position ='00103'
update staffinfo set resulttye=4 ,resultrate=0.001 where position ='00105'
update staffinfo set resulttye=4 ,resultrate=0.001 where position ='0010502'
update staffinfo set resulttye=4 ,resultrate=0.004 where position ='0010503'

--美容经理 美容顾问  80% 以上的指标  完成额度/指标额度 *2000 *到勤率
--田林店	48W  
update staffinfo set baseresult=480000 where compno='002' and position in ('00101','00103')
--武宁店	45W
update staffinfo set baseresult=450000 where compno='005' and position in ('00101','00103')
--虹梅店	60W
update staffinfo set baseresult=600000 where compno='006' and position in ('00101','00103')
--武定点	30W
update staffinfo set baseresult=300000 where compno='007' and position in ('00101','00103')
--真光店	50W
update staffinfo set baseresult=500000 where compno='008' and position in ('00101','00103')
--中谭店	50W
update staffinfo set baseresult=500000 where compno='009' and position in ('00101','00103')
--牡丹江	38W
update staffinfo set baseresult=380000 where compno='013' and position in ('00101','00103')
--联洋店	60W
update staffinfo set baseresult=600000 where compno='014' and position in ('00101','00103')
--昌里店	55W
update staffinfo set baseresult=550000 where compno='015' and position in ('00101','00103')
--延长店	50W
update staffinfo set baseresult=500000 where compno='016' and position in ('00101','00103')
--徐汇店	52W
update staffinfo set baseresult=520000 where compno='017' and position in ('00101','00103')
--九亭店	35W
update staffinfo set baseresult=350000 where compno='018' and position in ('00101','00103')
--七宝店	50W
update staffinfo set baseresult=500000 where compno='019' and position in ('00101','00103')
--金汇店	50W
update staffinfo set baseresult=500000 where compno='020' and position in ('00101','00103')
--花木店	40W
update staffinfo set baseresult=400000 where compno='021' and position in ('00101','00103')
--鞍山店	35W
update staffinfo set baseresult=350000 where compno='022' and position in ('00101','00103')
--莘庄店	45W
update staffinfo set baseresult=450000 where compno='024' and position in ('00101','00103')
--喜马拉雅	55W
update staffinfo set baseresult=550000 where compno='025' and position in ('00101','00103')
--古北店	45W
update staffinfo set baseresult=450000 where compno='026' and position in ('00101','00103')
--宝龙店	35W
update staffinfo set baseresult=350000 where compno='027' and position in ('00101','00103')
--周浦店	35W
update staffinfo set baseresult=350000 where compno='028' and position in ('00101','00103')
--井亭店	30W
update staffinfo set baseresult=300000 where compno='029' and position in ('00101','00103')
--伟业店	35W
update staffinfo set baseresult=350000 where compno='030' and position in ('00101','00103')
--威宁店	35W
update staffinfo set baseresult=350000 where compno='031' and position in ('00101','00103')
--常德店	36W 
update staffinfo set baseresult=360000 where compno='032' and position in ('00101','00103')
--大拇指	65W
update staffinfo set baseresult=650000 where compno='033' and position in ('00101','00103')
--城中店	35W
update staffinfo set baseresult=350000 where compno='035' and position in ('00101','00103')
--安信店	55W
update staffinfo set baseresult=550000 where compno='036' and position in ('00101','00103')
--真华店	38W
update staffinfo set baseresult=380000 where compno='038' and position in ('00101','00103')
--迎春店	30W
update staffinfo set baseresult=300000 where compno='039' and position in ('00101','00103')
--金桥店	35W
update staffinfo set baseresult=350000 where compno='041' and position in ('00101','00103')
--高桥店	25W
update staffinfo set baseresult=250000 where compno='042' and position in ('00101','00103')
--剑河店	25W
update staffinfo set baseresult=250000 where compno='043' and position in ('00101','00103')
--锦绣店	40W
update staffinfo set baseresult=400000 where compno='045' and position in ('00101','00103')
--碧云店	35W
update staffinfo set baseresult=350000 where compno='046' and position in ('00101','00103')
--潍坊店	35W
update staffinfo set baseresult=350000 where compno='047' and position in ('00101','00103')
--商城店	22W
update staffinfo set baseresult=220000 where compno='048' and position in ('00101','00103')
--渊明店	35W
update staffinfo set baseresult=350000 where compno='301' and position in ('00101','00103')
--象山店	28W
update staffinfo set baseresult=280000 where compno='302' and position in ('00101','00103')
--第一街区	22W
update staffinfo set baseresult=220000 where compno='303' and position in ('00101','00103')
--武林路	35W	
update staffinfo set baseresult=350000 where compno='501' and position in ('00101','00103')

--美容经理底薪 3000  00101
update staffinfo set basesalary=3000 where position='00101'
--美容顾问底薪 2000  00103
update staffinfo set basesalary=2000 where position='00103'

update staffhistory set newsalary=3000 where newpostion='00101'
update staffhistory set newsalary=2000 where newpostion='00103'

update staffhistory set oldsalary=3000 where oldpostion='00101' and newpostion<>'00101'
update staffhistory set oldsalary=2000 where oldpostion='00103' and newpostion<>'00103'



--update a set a.basesalary=b.basesalary
-- from staffinfo a,[A320140305].dbo.staffinfo b where a.position='00102' and b.position='00102' and a.manageno=b.manageno
 
-- update a set a.newsalary=b.newsalary
-- from staffhistory a,[A320140305].dbo.staffhistory b where a.newpostion='00102' and b.newpostion='00102' and a.manageno=b.manageno and a.seqno=b.seqno
 
--  update a set a.oldsalary=b.oldsalary
-- from staffhistory a,[A320140305].dbo.staffhistory b where b.oldpostion='00102' and b.newpostion<>'00102' and a.manageno=b.manageno and a.seqno=b.seqno

--美发经理
刘际根 020107   ID00004187
嵇助阳 026101   ID00007233
张士艮 027105   ID00000416
马玉银 048101   014014606

update a set a.position='01203' from staffinfo a,staffhistory b
 where position in ('01201','01202')  and changetype=4 and a.manageno=b.manageno and effectivedate>'20131201'

update staffhistory  set newpostion='01203',oldpostion='01203' where changetype=4 and effectivedate>'20131201' and newpostion in ('01201','01202')


update a set a.position='00201' from staffinfo a,staffhistory b
 where position ='002'  and changetype=4 and a.manageno=b.manageno and effectivedate>'20131201'

update staffhistory  set newpostion='00201',oldpostion='01203' where changetype=4 and effectivedate>'20131201' and newpostion  ='002' 
