if exists(select 1 from sysobjects where type='P' and name='upg_compute_comp_other_satff_salary_daybyday')
	drop procedure upg_compute_comp_other_satff_salary_daybyday
go
create procedure upg_compute_comp_other_satff_salary_daybyday
(
	@compid			varchar(10)	,
	@staffinid		varchar(20)	,
	@datefrom		varchar(8)	,
	@dateto			varchar(8)
)
as
begin
		create table #allstaff_work_detail_daybyday
		(
			seqno			int	identity		not null,       
			person_inid		varchar(20)			NULL, --员工内部编号
			action_id		int				NULL, --单据类型
			srvdate			varchar(10)			NULL, --日期
			code			varchar(20)			NULL, --项目代码,或是卡号,产品码
			name			varchar(40)			NULL, --名称
			payway			varchar(20)			NULL, --支付方式
			billamt			float				NULL, --营业金额
			ccount			float				NULL, --数量
			cost			float				NULL, --成本
			staffticheng	float				NULL, --提成
			staffyeji		float				NULL, --虚业绩
			prj_type		varchar(20)			NULL, --项目类别
			cls_flag        int					NULL, -- 1:项目 2:产品 3:卡
			billid			varchar(20)			NULL, --单号
			paycode			varchar(20)			NULL, --支付代码
			compid			varchar(10)			NULL, --公司别
			cardid			varchar(20)			NULL, --会员卡号
			cardtype		varchar(20)			NULL, --会员卡类型
			postation		varchar(10)			NULL, --员工部门
		)     
		
		insert #allstaff_work_detail_daybyday (compid,person_inid,action_id,srvdate,code,name,payway,billamt,ccount,cost,staffticheng,staffyeji,prj_type,cls_flag,billid,paycode,cardid,cardtype,postation)
		select compid,person_inid,action_id,srvdate,code,name,payway,billamt,ccount,cost,staffticheng,staffyeji,prj_type,cls_flag,billid,paycode,cardid,cardtype,postation
		from allstaff_work_detail_daybyday where compid=@compid and person_inid=@staffinid
		--美发师做美发项目 超过指定业绩增高普通的提成比率                                
		--美发师业绩超2.5W,首席超5W,总监 7W 美发项目提成比率0.35                                
		create  table #emp_yeji_total_resultx                                 
		(                                
			inid		varchar(20) null,  --员工内部编号                                   
			yeji		float  null, -- 业绩                 
			lv			float  null, --提成比率                                     
		)                                   
            
       	insert #emp_yeji_total_resultx(inid,yeji)                                       
		select  person_inid,sum(isnull(staffyeji,0)) from #allstaff_work_detail_daybyday where ISNULL(action_id,-1)<>4 and paycode in ('1','2','6','0','14','15') group by person_inid                              
                
		update a set staffticheng=isnull(staffyeji,0)*0.35                                
		from #allstaff_work_detail_daybyday a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                
		where a.person_inid=b.inid and isnull(a.postation,'')='003'                                 
		and (action_id>=7 and action_id<=15) and ISNULL(paycode,'')<>'9'                                
		and b.yeji>=25000 and  prjno=code and prjtype='3'                                
		and isnull(cardtype,'')   not in ('MFOLD','ZK')                                
                                 
		update a set staffticheng=isnull(staffyeji,0)*0.35                                
		from #allstaff_work_detail_daybyday a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                
		where a.person_inid=b.inid and isnull(a.postation,'')='006'                                 
		and (action_id>=7 and action_id<=15) and ISNULL(paycode,'')<>'9'                                
		and b.yeji>=50000 and  prjno=code and prjtype='3'                                
		and isnull(cardtype,'')   not in ('MFOLD','ZK')                                
                                 
		update a set staffticheng=isnull(staffyeji,0)*0.35                                
		from #allstaff_work_detail_daybyday a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                
		where a.person_inid=b.inid and isnull(a.postation,'')='007'                                 
		and (action_id>=7 and action_id<=15) and ISNULL(paycode,'')<>'9'                                
		 and b.yeji>=70000  and prjno=code and prjtype='3'                                
		and isnull(cardtype,'')   not in ('MFOLD','ZK')                                
               
               
		---美容师A类              
		if(@compid in ('008','017','019','026','032'))              
		begin              
               
		 update a set staffticheng=ISNULL(staffticheng,0)+ISNULL(staffyeji,0)*0.05  
		 from #allstaff_work_detail_daybyday a,#emp_yeji_total_resultx b              
		 where a.person_inid=b.inid              
		   and ISNULL(a.postation,'')='004'              
		   and (action_id>=7 and action_id<=15)  
		   and paycode not in('11','12','7','8','A','13')  
		   and cardtype not in('ZK')  
		   and yeji>=70000  
		   and code not in('300','3002','301','302','303','305','306','309','311')              
		   and action_id not in (26,27,28,29,30,31)  
  
		  --条码卡支付的时候需要判断是否为赠送  
		  update a set staffticheng=ISNULL(staffticheng,0)+ISNULL(staffyeji,0)*0.05  
		  from #allstaff_work_detail_daybyday a,nointernalcardinfo,#emp_yeji_total_resultx b  
		  where a.person_inid=b.inid               
			and a.cardtype=cardno              
			and ISNULL(a.postation,'')='004'              
			and (action_id>=7 and action_id<=15)
			and paycode='13'              
			and yeji>=70000                 
			and isnull(entrytype,0)=0              
			and code not in('300','3002','301','302','303','305','306','309','311')              
			and action_id not in (26,27,28,29,30,31)              
		                  
                 
		  update a set staffticheng=ISNULL(staffticheng,0)+ISNULL(staffyeji,0)*0.02              
		 from #allstaff_work_detail_daybyday a,#emp_yeji_total_resultx b              
		 where a.person_inid=b.inid and ISNULL(a.postation,'')='004'              
		   and paycode not in('11','12','7','8','A','13')              
		   and cardtype not in('ZK')              
		   and (action_id>=7 and action_id<=15)
		   and yeji<70000               
		   and yeji>=50000              
		   and code not in('300','3002','301','302','303','305','306','309','311')              
		   and action_id not in (26,27,28,29,30,31)              
                 
		   --条码卡支付的时候需要判断是否为赠送              
		  update a set staffticheng=ISNULL(staffticheng,0)+ISNULL(staffyeji,0)*0.02              
		  from #allstaff_work_detail_daybyday a,nointernalcardinfo,#emp_yeji_total_resultx b              
		  where a.person_inid=b.inid               
			and a.cardtype=cardno              
			and ISNULL(a.postation,'')='004'              
			and (action_id>=7 and action_id<=15)       
			and paycode='13'              
			and yeji<70000               
			and yeji>=50000              
			and isnull(entrytype,0)=0              
			and action_id not in (26,27,28,29,30,31)              
	end              
	drop table #emp_yeji_total_resultx                                
                                 
                                
                                            

               
	
  
	create table #allstaff_work_analysis_daybyday
	(
		ddate				varchar(10)			NULL,	--日期
		person_inid			varchar(20)			NULL,	--员工内部编号
		staffno				varchar(30)			NULL,	--员工工号
		staffname			varchar(30)			NULL,	--员工姓名
		staffposition		varchar(30)			NULL,	--员工职位
		oldcostcount		float				NULL,	--老客项目数
		newcostcount		float				NULL,	--新客项目数
		trcostcount			float				NULL,	--烫染项目数
		cashbigcost			float				NULL,	--现金大项
		cashsmallcost		float				NULL,	--现金小项
		cashhulicost		float				NULL,	--现金护理
		cardbigcost			float				NULL,	--销卡大项
		cardsmallcost		float				NULL,	--销卡小项
		cardhulicost		float				NULL,	--销卡护理
		cardprocost			float				NULL,	--疗程消费
		cardsgcost			float				NULL,	--收购卡消费
		cardpointcost		float				NULL,	--积分消费
		projectdycost		float				NULL,	--项目抵用券
		cashdycost			float				NULL,	--现金抵用券
		tmcardcost			float				NULL,	--条卡卡消费
		salegoodsamt		float				NULL,	--产品销售
		salecardsamt		float				NULL,	--卡销售
		prochangeamt		float				NULL,	--疗程兑换
		saletmkamt			float				NULL,	--条码卡销售
		qhpayinner			float				NULL,	--全韩店内支付
		qhpayouter			float				NULL,	--全韩对方支付
		jdpayinner			float				NULL,	--暨大店内支付
		smpayinner			float				NULL,	--私密店内支付
		staffyeji			float				NULL,	--员工提成合计
	) 
	create clustered index idx_work_analysis_person_inid on #allstaff_work_analysis_daybyday(person_inid)  
	insert #allstaff_work_analysis_daybyday(person_inid,ddate,staffyeji)
	select person_inid,srvdate,SUM(isnull(staffticheng,0)) from #allstaff_work_detail_daybyday where isnull(person_inid,'')<>'' group by person_inid,srvdate
	
	update a set a.staffno=b.staffno,a.staffname=b.staffname,a.staffposition=b.position
	from #allstaff_work_analysis_daybyday a,staffinfo b
	where a.person_inid=b.Manageno
	
	--现金大项
	update a set cashbigcost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b,projectnameinfo c
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype<>6 and c.prjpricetype=1 and b.paycode in ('1','2','6','0','14','15') ),0)
	from #allstaff_work_analysis_daybyday a
	
	--现金小项
	update a set cashsmallcost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b,projectnameinfo c
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype<>6 and c.prjpricetype=2 and b.paycode in ('1','2','6','0','14','15') ),0)
	from #allstaff_work_analysis_daybyday a
	
	--现金护理
	update a set cashhulicost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b,projectnameinfo c
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype=6  and b.paycode in ('1','2','6','0','14','15') ),0)
	from #allstaff_work_analysis_daybyday a
	
	--销卡大项
	update a set cardbigcost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b,projectnameinfo c
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype<>6 and c.prjpricetype=1 and b.paycode ='4' ),0)
	from #allstaff_work_analysis_daybyday a
	
	--销卡小项
	update a set cardsmallcost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b,projectnameinfo c
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype<>6 and c.prjpricetype=2 and b.paycode  ='4' ),0)
	from #allstaff_work_analysis_daybyday a
	
	--销卡护理
	update a set cardhulicost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b,projectnameinfo c
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype=6  and b.paycode  ='4' ),0)
	from #allstaff_work_analysis_daybyday a
	
	--疗程消费
	update a set cardprocost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=7 and b.action_id<=15 and b.paycode  ='9' ),0)
	from #allstaff_work_analysis_daybyday a
	
	--收购卡消费
	update a set cardsgcost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=7 and b.action_id<=15 and b.paycode  ='A' ),0)
	from #allstaff_work_analysis_daybyday a
	
	--积分消费
	update a set cardpointcost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=7 and b.action_id<=15 and b.paycode  ='7' ),0)
	from #allstaff_work_analysis_daybyday a
	
	--项目抵用券消费
	update a set projectdycost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=7 and b.action_id<=15 and b.paycode  ='11' ),0)
	from #allstaff_work_analysis_daybyday a
	
	--现金抵用券消费
	update a set cashdycost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=7 and b.action_id<=15 and b.paycode  ='12' ),0)
	from #allstaff_work_analysis_daybyday a
	
	--条码卡消费
	update a set tmcardcost=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=7 and b.action_id<=15 and b.paycode  ='13' ),0)
	from #allstaff_work_analysis_daybyday a
	
	--产品销售
	update a set salegoodsamt=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=16 and b.action_id<=24  ),0)
	from #allstaff_work_analysis_daybyday a
	
	--会员卡销售
	update a set salecardsamt=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id >=1 and b.action_id<=3  ),0)
	from #allstaff_work_analysis_daybyday a
	
	--疗程兑换
	update a set prochangeamt=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id =4  ),0)
	from #allstaff_work_analysis_daybyday a
	
	--条码卡销售
	update a set saletmkamt=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id =5  ),0)
	from #allstaff_work_analysis_daybyday a
	

	--全韩店内支付
	update a set qhpayinner=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id =28  ),0)
	from #allstaff_work_analysis_daybyday a
	
	--全韩对方支付
	update a set qhpayouter=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id =27 ),0)
	from #allstaff_work_analysis_daybyday a
	
	--暨大店内支付
	update a set jdpayinner=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id =28  ),0)
	from #allstaff_work_analysis_daybyday a
	
	--私密店内支付
	update a set smpayinner=ISNULL((
		select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id =29  ),0)
	from #allstaff_work_analysis_daybyday a
	
	--新客项目数
	update a set oldcostcount=ISNULL((
		select count(distinct billid) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id in (8,11,14) and isnull(ccount,0)>0  ),0)
	from #allstaff_work_analysis_daybyday a
	
	--老客项目数
	update a set newcostcount=ISNULL((
		select count(distinct billid) from #allstaff_work_detail_daybyday b
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and b.action_id in (7,10,13) and isnull(ccount,0)>0  ),0)
	from #allstaff_work_analysis_daybyday a
	
	--烫染项目数
	update a set trcostcount=ISNULL((
		select count(distinct billid) from #allstaff_work_detail_daybyday b,projectnameinfo c
		 where a.person_inid=b.person_inid and a.ddate=b.srvdate and isnull(ccount,0)>0 and b.code=c.prjno and c.prjreporttype in ('02','03') ),0)
	from #allstaff_work_analysis_daybyday a
	

	 

		delete staff_work_salary where compid=@compid and salary_date between @datefrom and @dateto and person_inid=@staffinid
		insert staff_work_salary(compid,person_inid,salary_date,oldcostcount,newcostcount,trcostcount,cashbigcost,cashsmallcost,cashhulicost,
				cardbigcost,cardsmallcost,cardhulicost,cardprocost,cardsgcost,cardpointcost,projectdycost,cashdycost,tmcardcost,
				salegoodsamt,salecardsamt,prochangeamt,saletmkamt,qhpayinner,qhpayouter,jdpayinner,smpayinner,staffyeji)
		select @compid,person_inid,ddate,sum(isnull(oldcostcount,0)),sum(isnull(newcostcount,0)),sum(isnull(trcostcount,0)),
				sum(isnull(cashbigcost,0)),sum(isnull(cashsmallcost,0)),sum(isnull(cashhulicost,0)),
				sum(isnull(cardbigcost,0)),sum(isnull(cardsmallcost,0)),sum(isnull(cardhulicost,0)),
				sum(isnull(cardprocost,0)),sum(isnull(cardsgcost,0)),sum(isnull(cardpointcost,0)),
				sum(isnull(projectdycost,0)),sum(isnull(cashdycost,0)),sum(isnull(tmcardcost,0)),
				sum(isnull(salegoodsamt,0)),sum(isnull(salecardsamt,0)),sum(isnull(prochangeamt,0)),
				sum(isnull(saletmkamt,0)),sum(isnull(qhpayinner,0)),sum(isnull(qhpayouter,0)),
				sum(isnull(jdpayinner,0)),sum(isnull(smpayinner,0)),sum(isnull(staffyeji,0))
		from #allstaff_work_analysis_daybyday
		group by person_inid,ddate
		drop table #allstaff_work_detail_daybyday
		drop table #allstaff_work_analysis_daybyday
	end