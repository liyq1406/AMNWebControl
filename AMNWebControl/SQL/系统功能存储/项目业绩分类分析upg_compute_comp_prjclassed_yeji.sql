if exists(select 1 from sysobjects where type='P' and name='upg_compute_comp_prjclassed_yeji')
	drop procedure upg_compute_comp_prjclassed_yeji
go
CREATE procedure upg_compute_comp_prjclassed_yeji                        
(                                  
 @compid varchar(10) ,                                  
 @datefrom varchar(8),                                  
 @dateto varchar(8)                                
)                                  
as                                  
begin                                  
--计算业绩相关常量                                      
 declare @PRJ_BEAUT_CLASS_CODE varchar(10) --项目类别之美容类代码                                      
 declare @PRJ_HAIR_CLASS_CODE varchar(10)  --项目类别之美发类代码                                      
 declare @PRJ_FOOT_CLASS_CODE varchar(10)  --项目类别之足疗类代码                                      
 declare @PRJ_FINGER_CLASS_CODE varchar(10) --项目类别之美甲类代码                                      
 declare @GOODS_BEAUT_CLASS_CODE varchar(10) --物品类别之美容类代码                                      
 declare @GOODS_HAIR_CLASS_CODE varchar(10)  --物品类别之美发类代码                                      
 declare @DEP_BEAUT_CODE varchar(10)  --美容部门代码                                      
 declare @DEP_HAIR_CODE varchar(10)   --美发部门代码                                      
 declare @DEP_FOOT_CODE varchar(10)   --美发部门代码                                      
 declare @DEP_FINGER_CODE varchar(10)   --美发部门代码                                      
                                       
 set @PRJ_BEAUT_CLASS_CODE = '4'                                      
 set @PRJ_HAIR_CLASS_CODE = '3'                                      
 set @PRJ_FOOT_CLASS_CODE = '6'                                      
 set @PRJ_FINGER_CLASS_CODE = '5'                         
                                      
 set @GOODS_BEAUT_CLASS_CODE = '400'                                      
 set @GOODS_HAIR_CLASS_CODE = '300'                          
                                     
 set @DEP_BEAUT_CODE = '003'  --美容部                                     
 set @DEP_HAIR_CODE = '004'   --美发部                                  
 set @DEP_FINGER_CODE = '005' --美甲部                         
 set @DEP_FOOT_CODE = '006'  --烫染部                                  
                                      
 --11.项目消费美容类业绩; 12.项目消费美发类业绩；13.项目消费总业绩;14.项目消费足疗类业绩；15.项目消费美甲类业绩                                      
 --21.售美容类产品业绩;   22.售美发类产品业绩；  23.售产品总业绩；                                      
 --31.开卡美容类业绩；  32.开卡美发类业绩； 33.开卡总业绩;    34.开卡足疗类业绩；    35.开卡美甲类业绩                                      
 --41.帐户异动美容类业绩；42.帐户异动美发类业绩；43.帐户异动总业绩 44.帐户异动足疗类业绩；45.帐户异动美甲类业绩                                      
 --51.卡异动美容类业绩；  52.卡异动美发类业绩；  53.卡异动总业绩   54.卡异动足疗类业绩；  55.卡异动美甲类业绩                                      
                                      
 --81.项目消费美容类实业绩；82.项目消费美发类实业绩；83.项目消费总实业绩；84.项目消费足疗类实业绩；85.项目消费美甲类实业绩                                      
 --91.售美容类产品实业绩；  92.售美发类产品实业绩；  93.售产品总实业绩；                                      
 create table #yeji_result(                                      
  compid  varchar(10) not null,                                      
  id         int  identity       , -- 流水号                                      
  item  varchar(5)  null, -- 业绩项目类别                                      
  yeji  float  null, -- 业绩                                      
  datefrom varchar(8) null, -- 开始日期                                      
  dateto  varchar(8)  null, -- 截止日期                                      
 )                                      
 create table #prj_cls(                                      
                                    
  prjid varchar(20) null,--项目编号                                      
  prjcls varchar(10) null--项目类别                                      
 )                                      
 create clustered index idx_clust_prj_cls on #prj_cls(prjcls,prjid)                                      
 insert into #prj_cls(prjid,prjcls)                                      
 select prjno,prjtype from projectnameinfo                               
                                      
              
 create table #emp_dep(                                    
  compid varchar(10) null,                                    
  inid varchar(20) null,--内部编号                                   
  empid varchar(20) null,--员工编号                                    
  empdep varchar(10) null,--员工部门                                    
  datefrom varchar(8) null,                                    
  dateto varchar(8) null,                                    
 )                                    
 create clustered index idx_tmp_emp_dep on #emp_dep(compid,empid,datefrom,dateto)                                    
 create table #empinfobydate(                                    
  seqno int identity not null,                        
  compid varchar(10) null,                            
  inid varchar(20) null,                                    
  empid varchar(20) null,                                    
  datefrom varchar(8) null,                                    
  dateto  varchar(8) null,                                    
  position varchar(10) null,                                    
  salary  float null,                                    
  sharetype varchar(5) null,                                    
  sharerate float null,                                    
  deducttax int null,                                     
 )                           
                       
    declare @compidid varchar(10)                      
    declare cur_each_comp cursor for                      
	 select relationcomp from compchaininfo  where curcomp=@compid                      
	 open cur_each_comp                      
	 fetch cur_each_comp into @compidid                      
	 while @@fetch_status=0                      
	 begin                      
	   insert into #empinfobydate(compid,inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)                                    
	   exec upg_get_empinfo_by_date_comps @compidid,@datefrom,@dateto                        
	                      
	   fetch cur_each_comp into @compidid                      
	 end                      
	 close cur_each_comp                      
	 deallocate cur_each_comp                      
                      
                                             
	insert into #emp_dep(compid,inid,empid,empdep,datefrom,dateto)                                
	select distinct compid,inid,empid,department,datefrom,dateto                                
	from #empinfobydate,staffinfo with (nolock)                                
	where inid=manageno and isnull(inid,'')<>''           
                                   
                       
                                       
	--计算项目消费虚业绩                                  
	create table #m_d_consumeinfo(                                  
	  cscompid			varchar(10)     NULL,   --公司编号                                  
	  csbillid			varchar(20)		NULL,   --消费单号                               
	  financedate		varchar(8)		NULL,   --帐务日期 
	  csinfotype		int             NULL,	--消费类型  1 项目  2 产品 
	  csitemno			varchar(20)     NULL,   --项目代码                                  
	  csitemamt			float           NULL,   --金额                                  
	  csfirstsaler		varchar(20)     NULL,   --大工代码                                  
	  csfirstinid		varchar(20)		NULL,   --大工内部编号  
	  csfirstshare		float           NULL,	--大工分享                              
	  cssecondsaler		varchar(20)     NULL,   --中工代码                                  
	  cssecondinid		varchar(20)		NULL,   --中工内部编号 
	  cssecondshare		float           NULL,	--中工分享                               
	  csthirdsaler		varchar(20)     NULL,   --小工代码        
	  csthirdinid		varchar(20)		NULL,   --小工内部编号 
	  csthirdshare		float           NULL,	--小工分享                      
	  cspaymode			varchar(5)      NULL   --项目支付方式                                     
	)  
	create nonclustered index index_m_d_consumeinfo_csitemno  on #m_d_consumeinfo(cscompid,csinfotype,csitemno)  
	create nonclustered index index_m_d_consumeinfo_financedate  on #m_d_consumeinfo(cscompid,csinfotype,financedate)  
	                                  
	insert into #m_d_consumeinfo(cscompid,csbillid,csinfotype,financedate,csitemno,csitemamt,csfirstsaler,csfirstinid,cssecondsaler,cssecondinid,csthirdsaler,csthirdinid,cspaymode,csfirstshare,cssecondshare,csthirdshare)                                  
	select a.cscompid,a.csbillid,csinfotype,a.financedate,csitemno,csitemamt,csfirstsaler,csfirstinid,cssecondsaler,cssecondinid,csthirdsaler,csthirdinid,cspaymode,csfirstshare,cssecondshare,csthirdshare                                  
	   from mconsumeinfo a WITH (NOLOCK),dconsumeinfo b with (nolock),compchaininfo                                   
	  where a.cscompid=b.cscompid                                  
		and a.csbillid=b.csbillid                                  
		and a.cscompid =relationcomp 
		and curcomp= @compid                                  
		and financedate>=@datefrom                                   
		and financedate<=@dateto                
                              
		insert into #yeji_result(compid,item,yeji)                                  
		 select cscompid,'11',isnull(sum(csitemamt),0)                                  
		 from #m_d_consumeinfo,#prj_cls                                  
		 where csitemno=prjid                                   
		   and prjcls=@PRJ_BEAUT_CLASS_CODE                                  
		   --and isnull(cspaymode,'') in ('1','2','6','14','15','16') 
		   and isnull(csinfotype,1)=1                                  
		 group by cscompid                                
		                                 
		 insert into #yeji_result(compid,item,yeji)                                  
		 select cscompid,'12',isnull(sum(csitemamt),0)                                  
		 from #m_d_consumeinfo,#prj_cls                                  
		 where csitemno=prjid                                   
		   and (prjcls=@PRJ_HAIR_CLASS_CODE   or    prjcls= @PRJ_FOOT_CLASS_CODE)                            
		   --and isnull(cspaymode,'') in ('1','2','6','14','15','16')
		   and isnull(csinfotype,1)=1                                        
		 group by cscompid                       
		                     
		                             
		 
		 insert into #yeji_result(compid,item,yeji)                                  
		 select cscompid,'15',isnull(sum(csitemamt),0)                                  
		 from #m_d_consumeinfo,#prj_cls                                  
		 where csitemno=prjid                                   
		   and prjcls=@PRJ_FINGER_CLASS_CODE                                  
		   --and isnull(cspaymode,'') in ('1','2','6','16')  
		   and isnull(csinfotype,1)=1                                
		 group by cscompid       
		                      
		  
    --------------------------------------------计算项目虚实业绩-------END--------------------------------------------------
    
    --21.售美容类产品业绩;   22.售美发类产品业绩；  23.售产品总业绩                                  
	--91.售美容类产品实业绩；  92.售美发类产品实业绩；  93.售产品总实业绩；                                  
	                      
	--------------------------------------------计算产品虚实业绩-------Start------------------------------------------------
		  insert into #yeji_result(compid,item,yeji)                                
		  select cscompid,case when empdep=@DEP_BEAUT_CODE		then '21'
								when empdep=@DEP_HAIR_CODE		then '22'
								when empdep=@DEP_FOOT_CODE		then '24x'
								when empdep=@DEP_FINGER_CODE    then '25' end ,isnull(sum(csfirstshare),0)                                  
		  from #m_d_consumeinfo,#emp_dep                                  
		  where inid=csfirstinid                                  
			--and isnull(cspaymode,'') in ('1','2','6','14','15','16')                                
			and financedate>=datefrom and financedate<dateto    and cscompid=compid  
			and isnull(csinfotype,1)=2                          
		  group by cscompid,empdep                                  
		                                  
		   insert into #yeji_result(compid,item,yeji)                                
		  select cscompid,case when empdep=@DEP_BEAUT_CODE		then '21'
								when empdep=@DEP_HAIR_CODE		then '22'
								when empdep=@DEP_FOOT_CODE	then '24x'
								when empdep=@DEP_FINGER_CODE    then '25' end,isnull(sum(cssecondshare),0)                                  
		  from #m_d_consumeinfo,#emp_dep                                  
		  where inid=cssecondinid                                   
			--and isnull(cspaymode,'') in ('1','2','6','14','15','16')                              
			and financedate>=datefrom and financedate<dateto   and cscompid=compid 
			and isnull(csinfotype,1)=2                                
		  group by cscompid,empdep                                 
		                                  
		   insert into #yeji_result(compid,item,yeji)                                
		  select cscompid,case when empdep=@DEP_BEAUT_CODE		then '21'
								when empdep=@DEP_HAIR_CODE		then '22'
								when empdep=@DEP_FOOT_CODE		then '24x'
								when empdep=@DEP_FINGER_CODE    then '25' end,isnull(sum(csthirdshare),0)                                  
		  from #m_d_consumeinfo,#emp_dep                                  
		  where inid=csthirdinid                                  
		   --and isnull(cspaymode,'') in ('1','2','6','14','15','16')                                
		   and financedate>=datefrom and financedate<dateto  and cscompid=compid 
		   and isnull(csinfotype,1)=2                               
		  group by cscompid,empdep                                 
		                                  
		
		                  
	
		        
	--------------------------------------------计算产品虚实业绩-------END--------------------------------------------------
	
                           
         
	create table #prjchange_yeji_resultx(                                
		compid					varchar(10)		null,	--门店编号         
		compname				varchar(50)		null,	--门店名称 
        beaut_prj_yeji			float			null,  --美容项目                          
		hair_prj_yeji			float			null,  --美发项目                           
		finger_prj_yeji			float			null,  --美甲项目                             
		beaut_goods_yeji		float			null,  --美容产品                              
		hair_goods_yeji			float			null,  --美发产品                             
		finger_goods_yeji		float			null,  --美甲产品 
		projecttype				varchar(10)		null,	--类型编号    
		projectamt				float			null,	--类型金额              
	 )     
     
    insert #prjchange_yeji_resultx(compid,projecttype,projectamt )      
	select a.cscompid,prjreporttype+'Amt',sum(isnull(csitemamt,0))
	from #m_d_consumeinfo a ,commoninfo,compchaininfo,projectnameinfo
	where infotype='XMTJ' and a.csinfotype='1' and prjno=a.csitemno and prjreporttype=parentcodekey
	and a.cscompid =relationcomp and curcomp=@compid and financedate between @datefrom and @dateto    
	group by a.cscompid,prjreporttype  order by cscompid,prjreporttype                                
	
	update a set compname= b.compname  from  #prjchange_yeji_resultx a,companyinfo b  where a.compid=b.compno
	
    update a set beaut_prj_yeji=b.beaut_prj_yeji,hair_prj_yeji=b.hair_prj_yeji,finger_prj_yeji=b.finger_prj_yeji,
				 beaut_goods_yeji=b.beaut_goods_yeji,hair_goods_yeji=b.hair_goods_yeji,finger_goods_yeji=b.finger_goods_yeji
      from  #prjchange_yeji_resultx a,(
				select compid,beaut_prj_yeji=isnull(sum( case when item in ('11','61') then yeji else 0 end ),0),              
					hair_prj_yeji=isnull(sum( case when item in ('12','62') then yeji else 0 end ),0),        
					finger_prj_yeji=isnull(sum( case when item='15' then yeji else 0 end ),0),                
					beaut_goods_yeji=isnull(sum( case when item='21' then yeji else 0 end ),0),              
					hair_goods_yeji=isnull(sum( case when item='22' then yeji else 0 end ),0),        
					finger_goods_yeji=isnull(sum( case when item='25' then yeji else 0 end ),0)              
			   from #yeji_result     group by compid ) as b
	where a.compid=b.compid
	  
	
	
	declare @sqltitle varchar(600)
	select @sqltitle = isnull(@sqltitle + '],[' , '') + parentcodekey+'Amt' from commoninfo where infotype='XMTJ' 
	set @sqltitle = '[' + @sqltitle + ']'
	
	exec ('select * from (select * from #prjchange_yeji_resultx ) a pivot (max(projectamt) for projecttype in (' + @sqltitle + ')) b order by compid')
	    

	drop table #m_d_consumeinfo                                      
	drop table #prj_cls                                                                 
	drop table #emp_dep                                  
	drop table #yeji_result                                  
	drop table #prjchange_yeji_resultx                             
end 

go

exec upg_compute_comp_prjclassed_yeji '0010101','20130801','20130831'