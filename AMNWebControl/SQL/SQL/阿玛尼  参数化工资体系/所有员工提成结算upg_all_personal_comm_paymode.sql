alter procedure upg_all_personal_comm_paymode(                                      
	@compid    varchar(10), -- 公司别                                      
	@fromdate   varchar(10), -- 开始日期                                      
	@todate    varchar(10), -- 截至日期      
    @handtype   int    --1 查询 2 日结 3 员工业绩统计      
   )                                                          
as                                      
begin                                    
	create table #allstaff_work_detail      
	(      
	   seqno   int identity  not null,             
	   person_inid  varchar(20)   NULL, --员工内部编号      
	   action_id  int    NULL, --单据类型      
	   srvdate   varchar(10)   NULL, --日期      
	   code   varchar(20)   NULL, --项目代码,或是卡号,产品码      
	   name   varchar(40)   NULL, --名称      
	   payway   varchar(20)   NULL, --支付方式      
	   billamt   float    NULL, --营业金额      
	   ccount   float    NULL, --数量      
	   cost   float    NULL, --成本      
	   staffticheng float    NULL, --提成      
	   staffyeji  float    NULL, --虚业绩    
	   staffshareyeji		float				NULL, --分享卡金  
	   prj_type  varchar(20)   NULL, --项目类别      
	   cls_flag        int     NULL, -- 1:项目 2:产品 3:卡      
	   billid   varchar(20)   NULL, --单号      
	   paycode   varchar(20)   NULL, --支付代码      
	   compid   varchar(10)   NULL, --公司别      
	   cardid   varchar(20)   NULL, --会员卡号      
	   cardtype  varchar(20)   NULL, --会员卡类型      
	   postation  varchar(10)   NULL, --员工部门 
	   arrivaldmonth			int					NULL, --到职月份  
	   csitemstate			int					NULL,		--是否达标	0 无 1 达标 2 未达标 
	   billinsertype			int						NULL,	--充值主办方 1 美容 2 美发 
	   costpricetype			int					NULL,		--是否为体验价消费	0 不是 1 是     
	)           
	create clustered index idx_work_detail_action_id on #allstaff_work_detail(action_id,code)        
       
     exec upg_prepare_yeji_analysis @compid,@fromdate, @todate 
          
    declare @isNewComdMode	int	--是否是新模式
    set @isNewComdMode=0
    if exists(select 1 from compchaininfo where curcomp in ('0010104','0010102') and relationcomp=@compid and @compid not in ('028','035') )
	begin
		set @isNewComdMode=1
    end
    else
    begin
		if(@todate>'20140531')
		begin
			set @isNewComdMode=1
		end
		else
		begin
			update #allstaff_work_detail set staffyeji=ISNULL(staffshareyeji,0) where action_id  in ('1','2','3')
		end
    end
    
    declare @SP105 varchar(2)
    select @SP105=paramvalue from sysparaminfo where compid=@compid and paramid='SP105'
       
	  
 
    update   #allstaff_work_detail set arrivaldmonth=datediff(month,arrivaldate,@todate)
    from  #allstaff_work_detail,staffinfo 
    where person_inid=manageno
    
    --调动升级
    UPDATE #allstaff_work_detail set arrivaldmonth=(select MAX(isnull(effectivedate,'')) from staffhistory c where person_inid=c.manageno and c.changetype in (0,1) and c.oldpostion not in  ('003','006','007','00701','00702')  and isnull(newpostion,'')='003')
   from #allstaff_work_detail a,staffhistory b
   where a.person_inid=b.manageno and  b.changetype in (0,1) and b.oldpostion not in  ('003','006','007','00701','00702') and isnull(newpostion,'')='003' 
   
   
   --重回公司的不算入职日期
   UPDATE a 
   set arrivaldmonth='20130101'
   from #allstaff_work_detail a,staffhistory b 
   where person_inid=manageno and changetype =5 and isnull(newpostion,'') in ('003','006','007','00701','00702')  and b.oldpostion  in  ('003','006','007','00701','00702') 
        
	update #allstaff_work_detail set payway=parentcodevalue       
	from #allstaff_work_detail,commoninfo      
	where infotype='ZFFS' and parentcodekey=paycode      
       
	update #allstaff_work_detail set cardtype='' where cardtype='ZK' and paycode not in ('4','17')     
      
	create table #empinfobydate(                                      
	  seqno  int identity  not null,                          
	  compid  varchar(10)   null,                              
	  inid  varchar(20)   null,                                      
	  empid  varchar(20)   null,                                      
	  datefrom varchar(8)   null,                                      
	  dateto  varchar(8)   null,                                      
	  position varchar(10)   null,                                      
	  salary  float    null,                                      
	  sharetype varchar(5)   null,                                      
	  sharerate float    null,                                      
	  deducttax int     null,                                       
	)                             
                         
    insert into #empinfobydate(compid,inid,empid,datefrom,dateto,position,salary,sharetype,sharerate,deducttax)                                 
	exec upg_get_empinfo_by_date_comps @compid,@fromdate,@todate        
    --门店模式      
	declare @comptypebyfinger varchar(5)                        
	select  @comptypebyfinger=ISNULL(compmode,'1') from companyinfo where compno=@compid         
                                         
	declare @empPostion varchar(10)                                                    
	declare @tmpSeqId int                                                              
	declare @tmpEmpId varchar(20)                                                              
	declare @tmpItem varchar(10)                                                              
	declare @tmpYeji float                                                              
	declare @tmpPrjId varchar(20)                                                              
	declare @tmpDate varchar(8)                                                              
	declare @paycode varchar(10)                            
	declare @emp_total_yeji float                                             
                                              
	declare @GOODS_TYPE varchar(5)                                            
	declare @PROJECT_COST float                                         
	declare @Performance_Ratio float                                          
	declare @Wage_Rates float                  
	declare @CARD_SALE_RATE float                                           
	set @CARD_SALE_RATE=0.04                                          
	declare @PROJECT_TYPE varchar(5)                                          
	declare @GOODS_SALE_RATE_buty float                                          
	declare @GOODS_SALE_RATE_hair float                                        
	declare @GOODS_SALE_RATE_finger float --美发售产品提成比率                                             
                                            
	set  @GOODS_SALE_RATE_buty=0.1                                                 
	set  @GOODS_SALE_RATE_hair=0.05                                
	set  @GOODS_SALE_RATE_finger=0.6                                        
	declare @cardtype  varchar(20)  --会员卡类别 
	declare @dycardnocreatetype  int  -- 抵用券类型                                           
	declare @quan float                                      
	declare @fuflag float --正负单据                                      
	declare @businessflag int --是否为业务人员 0--不是 1--是                                       
	declare @empinid varchar(20)                                  
	declare @proflag int --项目类别   
	declare @arrivaldmonth	int  --在职月份
	declare @newcosttc  float	--新客提成数
	declare @oldcosttc  float	--老客提成数 
	declare @saleprice  float	--项目标准价
	declare @prjsaletype   int		--是否是疗程		 1是  2否  
	declare @csitemstate int	--是否达标	0 无 1 达标 2 未达标 
	declare @billinsertype int		--充值主办方 1 美容 2 美发    0 不区分
	declare @costpricetype  int   --是否为体验价消费	0 不是 1 是 
	
	-----------------------------提成化参数------------------------------------------------
	--产品提成
	declare @cp_costrate_mr				float				   -- 美容产品成本系数
	declare @cp_salaryrate_mr			float				   -- 美容产品提成系数
	declare @cp_costrate_mf				float				   -- 美发产品成本系数
	declare @cp_salaryrate_mf			float				   -- 美发产品提成系数
	declare @cp_costrate_mj				float				   -- 美甲产品成本系数
	declare @cp_salaryrate_mj			float				   -- 美甲产品提成系数
	declare @cp_costrate_ks				float				   -- 卡诗产品成本系数
	declare @cp_salaryrate_ks			float				   -- 卡诗产品提成系数
	
	--卡金提成
	declare @kj_salaryrate_trsa			float					--一,二,三级烫染师卡金提成系数
	declare @kj_salaryrate_trsb			float					--四级烫染师,烫染督导卡金提成系数
	declare @kj_salaryrate_mrc			float					--美容师C卡金提成系数
	declare @kj_salaryrate_mr			float					--其他美容师提成系数
	declare @kj_salaryrate_mf			float					--美发人员提成系数
	
	--疗程兑换提成
	declare @dh_costrate_trsa			float					--一,二,三级烫染师疗程兑换成本系数
	declare @dh_costrate_trsb			float					--四级烫染师,烫染督导疗程兑换成本系数
	declare @dh_costrate_mf				float					--美发人员疗程兑换成本系数
	declare @dh_salaryrate_trsa			float					--一,二,三级烫染师疗程兑换提成系数
	declare @dh_salaryrate_trsb			float					--四级烫染师,烫染督导疗程兑换提成系数
	declare @dh_salaryrate_mf			float					--美发人员疗程兑换提成系数
	
	--合作项目提成
	declare @hz_salaryrate_qh			float					--全韩私密合作提成比率
	declare @hz_salaryrate_jd			float					--暨大合作提成比率
	
	declare @jfdh_salary_reward			float					--建发疗程兑换提成奖励
	declare @jjdh_yeji_reward			float					--肩颈疗程兑换卡金奖励
	declare @jjfw_salary_reward			float					--肩颈服务提成奖励
	declare @jsr_salary_reward			float					--介绍人提成奖励
	declare @tjsr_salary_cost			float					--被介绍人扣除提成
	
	--项目消费特殊提成方式
	declare @mf_salaryrate_fiveup		float					--美发人员5个月上的提成比率(限美发类项目)
	declare @mf_salaryrate_fivedown		float					--美发人员5个月下的提成比率(限美发类项目)
	
	declare @olc_cost_yeji_fixed		float					--老疗程卡固定业绩
	declare @olc_cost_salary_fixed		float					--老疗程卡固定提成
	declare @nlc_costrate_tr			float					--烫染师疗程成本
	
	declare @yjk_costrate_mrmf			float					--原价卡美容美发成本
	declare @yjk_salaryrate_mrmf		float					--原价卡美容美发提成比率
	declare @yjk_costrate_tr			float					--原价卡烫染师成本
	
	declare @salaryrate_tra				float					--一,二,三级烫染师提成比率
	declare @salaryrate_trb				float					--四级烫染师,烫染督导提成比率
	
	declare @xjc_costrate_sjs			float					--设计师洗剪吹成本
	declare @xjc_costrate_sx			float					--首席洗剪吹成本
	declare @xjc_costrate_zj			float					--总监洗剪吹成本
	
	declare @xjc_salary_fixed_db		float					--洗剪吹中工达标 10
	declare @xjc_salary_fixed_ndb		float					--洗剪吹中工不达标 5
	declare @xjc_salary_fixed_nhg		float					--洗剪吹中工不合格 3
	
	declare @mr_salary_fixed_tmk		float					--美容新项目条码卡固定提成
	declare @mr_salary_fixed_ty			float					--美容新项目体验固定提成
	
	select  @cp_costrate_mr=cp_costrate_mr,@cp_salaryrate_mr=cp_salaryrate_mr,@cp_costrate_mf=cp_costrate_mf,@cp_salaryrate_mf=cp_salaryrate_mf,
			@cp_costrate_mj=cp_costrate_mj,@cp_salaryrate_mj=cp_salaryrate_mj,@cp_costrate_ks=cp_costrate_ks,@cp_salaryrate_ks=cp_salaryrate_ks, 
			@kj_salaryrate_trsa=kj_salaryrate_trsa,@kj_salaryrate_trsb=kj_salaryrate_trsb,@kj_salaryrate_mrc=kj_salaryrate_mrc,@kj_salaryrate_mr=kj_salaryrate_mr,@kj_salaryrate_mf=kj_salaryrate_mf,
			@dh_costrate_trsa=dh_costrate_trsa,@dh_costrate_trsb=dh_costrate_trsb,@dh_costrate_mf=dh_costrate_mf,@dh_salaryrate_trsa=dh_salaryrate_trsa,@dh_salaryrate_trsb=dh_salaryrate_trsb,@dh_salaryrate_mf=dh_salaryrate_mf, 
			@hz_salaryrate_qh=hz_salaryrate_qh,@hz_salaryrate_jd=hz_salaryrate_jd,@jfdh_salary_reward=jfdh_salary_reward,@jjdh_yeji_reward=jjdh_yeji_reward,@jjfw_salary_reward=jjfw_salary_reward,@jsr_salary_reward=jsr_salary_reward,@tjsr_salary_cost=tjsr_salary_cost, 
			@mf_salaryrate_fiveup=mf_salaryrate_fiveup,@mf_salaryrate_fivedown=mf_salaryrate_fivedown,@olc_cost_yeji_fixed=olc_cost_yeji_fixed,@olc_cost_salary_fixed=olc_cost_salary_fixed,@nlc_costrate_tr=nlc_costrate_tr,
			@yjk_costrate_mrmf=yjk_costrate_mrmf,@yjk_salaryrate_mrmf=yjk_salaryrate_mrmf,@yjk_costrate_tr=yjk_costrate_tr, 
			@salaryrate_tra=salaryrate_tra,@salaryrate_trb=salaryrate_trb,@xjc_costrate_sjs=xjc_costrate_sjs,@xjc_costrate_sx=xjc_costrate_sx,@xjc_costrate_zj=xjc_costrate_zj,@xjc_salary_fixed_db=xjc_salary_fixed_db,@xjc_salary_fixed_ndb=xjc_salary_fixed_ndb,@xjc_salary_fixed_nhg=xjc_salary_fixed_nhg,
			@mr_salary_fixed_tmk=mr_salary_fixed_tmk,@mr_salary_fixed_ty=mr_salary_fixed_ty 
			from salaryrateinfo where compno=@compid
	                                                   
	declare cur_yeji_ticheng cursor for                                                               
	select seqno,person_inid,action_id,case when isnull(action_id,'') in ('1','2','3') then staffshareyeji else staffyeji end,code,srvdate ,paycode,isnull(cardtype,''),isnull(ccount,0),isnull(arrivaldmonth,0),ISNULL(csitemstate,0),ISNULL(billinsertype,0),isnull(costpricetype,0)                                                 
	from #allstaff_work_detail                                                              
	declare @empTicheng float           
    declare @trkcqualified int --烫染课程是否合格                                                            
	open cur_yeji_ticheng                                                              
	fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate,@paycode ,@cardtype,@quan,@arrivaldmonth,@csitemstate,@billinsertype,@costpricetype                                                                 
	while(@@fetch_status=0)                          
	begin                                           
		set @empTicheng = 0         
		set @empPostion=''    
		--更新员工的最新职位            
		select @empPostion=position ,@empinid=inid from #empinfobydate where inid=@tmpEmpId and @tmpDate>=datefrom and @tmpDate<dateto                           
		 -- 查看员工是否是业务人员         
		select @businessflag=ISNULL(businessflag,0),@trkcqualified=ISNULL(trkcqualified,0)  from staffinfo with(nolock)  where manageno=@tmpEmpId and ISNULL(stafftype,0)=0                                   
		-- 如果是非业务人员提成为0      
		if(@businessflag=0 or isnull(@empPostion,'') not in ('003','004','00103','00401','00402','005','006','007','00701','00702','008','00901','00902','00903','00904'))                                            
		begin                                      
			update #allstaff_work_detail set staffticheng=0 where seqno=@tmpSeqId                                         
			fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan ,@arrivaldmonth,@csitemstate,@billinsertype,@costpricetype                                      
			continue                                         
		end      
		-- 16，17，18，19，20，21，22，23，24-售产品        
		if(isnull(@tmpItem,'') in ('16','17','18','19','20','21','22','23','24'))                                                      
		begin            
			select   @GOODS_TYPE=isnull(goodstype,1)  from  goodsnameinfo with(nolock) where goodsno=@tmpPrjId                                            
			if(isnull(@GOODS_TYPE,'300'))='400'----美容产品(扣除成本20%提成10%)                                            
			begin                                            
				update #allstaff_work_detail set staffyeji=isnull(staffyeji,0)*isnull(@cp_costrate_mr,0) where seqno=@tmpSeqId                                             
				set @empTicheng = isnull(@tmpYeji,0)*isnull(@cp_costrate_mr,0)*ISNULL(@cp_salaryrate_mr,0)                                            
			end                                            
			else if(isnull(@GOODS_TYPE,'300'))='300'----美发产品（提成5%）                                       
			begin                                            
				update #allstaff_work_detail set staffyeji=isnull(staffyeji,0)*isnull(@cp_costrate_mf,0) where seqno=@tmpSeqId                                             
				set @empTicheng = isnull(@tmpYeji,0)*isnull(@cp_costrate_mf,0)*ISNULL(@cp_salaryrate_mf,0)                                                
			end                                           
			else if(isnull(@GOODS_TYPE,'300'))='500'----美甲产品（产品不扣成本 4，6分）                                           
			begin                                               
				update #allstaff_work_detail set staffyeji=isnull(staffyeji,0)*isnull(@cp_costrate_mj,0) where seqno=@tmpSeqId 
				set @empTicheng = isnull(@tmpYeji,0)*isnull(@cp_costrate_mj,0)*ISNULL(@cp_salaryrate_mj,0)                                       
			end                                        
			else if(isnull(@GOODS_TYPE,'300'))='700'----卡诗产品（无业绩无提成）                                                 
			begin                                               
				update #allstaff_work_detail set staffyeji=isnull(staffyeji,0)*isnull(@cp_costrate_ks,0) where seqno=@tmpSeqId                                             
				set @empTicheng = isnull(@tmpYeji,0)*isnull(@cp_costrate_ks,0)*ISNULL(@cp_salaryrate_ks,0)                                          
			end                                                      
		end        
		-- 开卡+充值+转卡+条码卡开卡 1，2，3，5      
		else if(isnull(@tmpItem,'')='1' or isnull(@tmpItem,'')='2'  or isnull(@tmpItem,'')='3' or isnull(@tmpItem,'')='5')                                           
		begin                                             
			if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902' or isnull(@empPostion,'')='00903'  )  --一级和二级烫染师1.5%                                         
				set @empTicheng = @tmpYeji*ISNULL(@kj_salaryrate_trsa,0)                                          
			else if (isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008' )  --三级和四级烫染师 2%                                         
				set @empTicheng = @tmpYeji*ISNULL(@kj_salaryrate_trsb,0) 
			else    -- 其他职位都是6%                     
				set @empTicheng = @tmpYeji*0.06  
			--if(@compid in ('006','047','033','014','041','046'))
			if(ISNULL(@isNewComdMode,0)=1)
			begin
				if (isnull(@empPostion,'')='00402') -- C类美容师不拿提成
					set @empTicheng = @tmpYeji*ISNULL(@kj_salaryrate_mrc,0)      
				else if (isnull(@empPostion,'')='004' or isnull(@empPostion,'')='00401'  or isnull(@empPostion,'')='00103') -- 其他类美容师拿1%的提成
					set @empTicheng = @tmpYeji*ISNULL(@kj_salaryrate_mr,0) 
				else if (isnull(@empPostion,'') in ('003','006','007','00701','00702') and ISNULL(@tmpDate,'')>='20140517' ) -- 美发部拿4%
					set @empTicheng = @tmpYeji*ISNULL(@kj_salaryrate_mf,0)
			
				if(ISNULL(@billinsertype,0)=0)
				begin
					update #allstaff_work_detail set staffyeji=@tmpYeji where seqno=@tmpSeqId 
				end
				if(ISNULL(@billinsertype,0)=1 and isnull(@empPostion,'') in ('003','006','007','00701','00702')) --美发卡金
				begin
					update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId                
				end 
				if(ISNULL(@billinsertype,0)=2 and isnull(@empPostion,'') in ('003','006','007','00701','00702')) --美发卡金
				begin
					update #allstaff_work_detail set staffyeji=@tmpYeji where seqno=@tmpSeqId                
				end 
				if(ISNULL(@billinsertype,0)=2 and isnull(@empPostion,'') in ('004','00401','00402','00103')) --美容卡金
				begin
					update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId                
				end 
				if(ISNULL(@billinsertype,0)=1 and isnull(@empPostion,'') in ('004','00401','00402','00103')) --美容卡金
				begin
					update #allstaff_work_detail set staffyeji=@tmpYeji where seqno=@tmpSeqId                
				end 
			end                                                     
		end         
		-- 合作项目销售  26，27，28，29，30，31                                           
		else if(isnull(@tmpItem,'')='26' or isnull(@tmpItem,'')='27'                              
			or  isnull(@tmpItem,'')='28' or isnull(@tmpItem,'')='29'                              
			or  isnull(@tmpItem,'')='30' or isnull(@tmpItem,'')='31' )                                         
		begin                                             
			if(isnull(@tmpItem,'')='26' or isnull(@tmpItem,'')='27' or isnull(@tmpItem,'')='30' )                              
				set @empTicheng = @tmpYeji*ISNULL(@hz_salaryrate_qh,0)                                 
			else if (isnull(@tmpItem,'')='28')                              
				set @empTicheng = @tmpYeji*ISNULL(@hz_salaryrate_jd,0)                              
		end        
		--疗程兑换  4      
		else if(isnull(@tmpItem,'')='4')                                     
		begin                                      
			if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902' or isnull(@empPostion,'')='00903'  )  --一级和二级烫染师2% 
			begin                                        
				update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*ISNULL(@dh_costrate_trsa,0) where seqno=@tmpSeqId                                      
				set @empTicheng = @tmpYeji*ISNULL(@dh_costrate_trsa,0)*ISNULL(@dh_salaryrate_trsa,0)        
			end                                    
			else if ( isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008' )  --三级和四级烫染师 2.5%  
			begin                                       
				update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*ISNULL(@dh_costrate_trsb,0) where seqno=@tmpSeqId                                    
				set @empTicheng = @tmpYeji*ISNULL(@dh_costrate_trsb,0)*ISNULL(@dh_salaryrate_trsb,0) 
			end                                         
			--首席，总监，设计师记业绩20%,提成20%                                       
			else if ( isnull(@empPostion,'')='003' or isnull(@empPostion,'')='006' or isnull(@empPostion,'')='007' or isnull(@empPostion,'')='00701' or isnull(@empPostion,'')='00702'  or isnull(@empPostion,'')='00102')                                        
			begin                                      
				update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*ISNULL(@dh_costrate_mf,0) where seqno=@tmpSeqId                                        
				set @empTicheng = isnull(@tmpYeji,0)*ISNULL(@dh_costrate_mf,0)*ISNULL(@dh_salaryrate_mf,0)                      
			end                                      
		end  
		--疗程兑换 (建发奖励)   
		else if(isnull(@tmpItem,'')='25')                                     
		begin      
			                                
			if(isnull(@empPostion,'') in ('00901','00902','00903','00904','008') )  --一级和二级烫染师2%                                         
			begin
				update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId 
				set @empTicheng = @tmpYeji*ISNULL(@jfdh_salary_reward,0)  
			end 
			else  if(isnull(@empPostion,'') in ('003','006','007','00701','00702') )  --一级和二级烫染师2%                                         
			begin
				update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId 
				set @empTicheng = @tmpYeji*ISNULL(@jfdh_salary_reward,0)  
			end                                        
			else   
			begin
				set @empTicheng =0    
				update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId 
			end        
		end
		--疗程兑换 (肩颈奖励)   
		else if(isnull(@tmpItem,'')='35')                                     
		begin                               
			if(isnull(@empPostion,'') in ('00103','004','00401') )  --美容师                                         
			begin
				update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId 
				set @empTicheng = @tmpYeji*ISNULL(@jjfw_salary_reward,0)   
			end                                           
			else   
			begin
				set @empTicheng =0    
				update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId 
			end        
		end     
		--32 项目介绍人
		else if(isnull(@tmpItem,'')='32')                                     
		begin      
			update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId 
			if(ISNULL(@SP105,'0')='1')
				set @empTicheng =  ISNULL(@jsr_salary_reward,0)      
			else 
				set @empTicheng = 0     
		end 
		--36 项目介绍人
		else if(isnull(@tmpItem,'')='36')                                     
		begin      
			update #allstaff_work_detail set paycode='1',staffyeji=ISNULL(@jjdh_yeji_reward,0) where seqno=@tmpSeqId 
			set @empTicheng = 0    
		end  
		--37发型师手工费
		else if(isnull(@tmpItem,'')='37')                                     
		begin      
			update #allstaff_work_detail set staffyeji=25*ISNULL(@quan,1)   where seqno=@tmpSeqId 
			set @empTicheng =  25*ISNULL(@quan,1)
		end    
		--项目消费 7,8,9,10,11,12,13,14,15      
		else      
		begin      
			--设置美发师的烫染项目项目
			if(isnull(@empPostion,'') in ('003','006','007','00701','00702') and ISNULL(@trkcqualified,0)=1)
			begin
				if exists(select 1 from projectnameinfo where prjno=@tmpPrjId and ISNULL(prjreporttype,'') in ('02','03'))
				begin
					update #allstaff_work_detail set staffyeji=0,staffticheng=0 where seqno=@tmpSeqId                                        
					set @empTicheng = 0     
					fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan ,@arrivaldmonth,@csitemstate,@billinsertype,@costpricetype                                     
					continue 
				end
			end  
			set @fuflag=@quan         
			if(isnull(@paycode,'')='9') --疗程                                                        
			begin                                                          
				--疗程消费美容师和美发师按照设定成本和业绩比率走                                    
				select @proflag=isnull(prjpricetype,2),@PROJECT_COST=isnull(costprice,0),@Performance_Ratio=isnull(lyjrate,1),
					@Wage_Rates=isnull(ltcrate,1),@PROJECT_TYPE=prjtype,@newcosttc=newcosttc,@oldcosttc=oldcosttc ,@prjsaletype=isnull(prjsaletype,0)      
					from  projectinfo a,sysparaminfo b     
					where b.paramid='SP059' and b.compid=@compid and b.paramvalue=a.prjmodeId and prjno=@tmpPrjId                                     
				
				 --美容师C大项无提成
				if(isnull(@empPostion,'') ='00402'  and ISNULL(@proflag,2)=1)                                      
				begin                                      
					update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId                                        
					set @empTicheng = 0     
					fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan ,@arrivaldmonth,@csitemstate,@billinsertype,@costpricetype                                     
					continue                                    
				end  
				 
				if(ISNULL(@newcosttc,0)>0 or ISNULL(@oldcosttc,0)>0)
				begin
						if(  isnull(@tmpItem,'')='7')
							update #allstaff_work_detail set staffticheng=@oldcosttc*@fuflag  where seqno=@tmpSeqId 
						else   if(  isnull(@tmpItem,'')='8')
							update #allstaff_work_detail set staffticheng=@newcosttc*@fuflag  where seqno=@tmpSeqId 
						else
							update #allstaff_work_detail set staffticheng=0 where seqno=@tmpSeqId 
						--非疗程的疗程项目 
						if(ISNULL(@prjsaletype,0)<>1)
							 update #allstaff_work_detail set staffticheng=60*@fuflag  where seqno=@tmpSeqId                                     
						fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan ,@arrivaldmonth,@csitemstate,@billinsertype,@costpricetype                                     
						continue  
				end
				update #allstaff_work_detail set staffyeji=(isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*isnull(@Performance_Ratio,0) where seqno=@tmpSeqId                                             
				set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*@Performance_Ratio*@Wage_Rates                                            
                                              
				--烫染师 非疗程卡 记80%业绩 记5%提成 纯疗程卡记120块的业绩，6块钱的提成                                      
				if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903'  or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008' )                                          
				begin            
					if( isnull(@cardtype,'') ='MR' or isnull(@cardtype,'')='MF')                                          
					begin                                      
						update #allstaff_work_detail set staffyeji=ISNULL(@olc_cost_yeji_fixed,0)*@fuflag where seqno=@tmpSeqId                                             
						set @empTicheng=ISNULL(@olc_cost_salary_fixed,0)*@fuflag                                      
					end                                       
					else                                      
					begin                                           
						update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*ISNULL(@nlc_costrate_tr,0) where seqno=@tmpSeqId                               
						set @empTicheng = isnull(@tmpYeji,0)*ISNULL(@nlc_costrate_tr,0)*ISNULL(@salaryrate_tra,0)                                      
						if(isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')--四级烫染师                                      
							set @empTicheng = isnull(@tmpYeji,0)*ISNULL(@nlc_costrate_tr,0)*ISNULL(@salaryrate_trb,0)                                      
					end                                         
				end                                                            
				if(isnull(@empPostion,'')in('004','00103','00401','00402') and ISNULL(@PROJECT_TYPE,'') in ('3','6') and ISNULL(@proflag,2)=1)                                      
				begin                                      
					update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId                                        
					set @empTicheng = 0                                       
				end                                      
				if(isnull(@empPostion,'') in ('003','006','007','00701','00702','00901','00902','00903','00904','008')  and ISNULL(@PROJECT_TYPE,'') ='4' and ISNULL(@proflag,2)=1)                                      
				begin                                      
					update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId                                        
					set @empTicheng = 0                                       
				end                                       
				--美发的输入在中工和小工位置的(美发师,首席,总监)无提成                                      
				if((@PROJECT_TYPE='3' or @PROJECT_TYPE='6') and isnull(@empPostion,'') in ('003','006','007','00701','00702') and isnull(@tmpItem,'') not in ('7','8','9'))                                      
				begin                                      
					set @empTicheng = 0                                       
				end                                      
				if(@cardtype ='MFOLD' )                                       
				begin                                      
					update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId                                        
					set @empTicheng =0                                      
				end                                                         
			end   
			else if( (@cardtype ='ZK' and isnull(@paycode,'')='4') or (@cardtype ='ZK' and isnull(@paycode,'')='17')  or isnull(@paycode,'')='$'  or isnull(@paycode,'')='A' or isnull(@paycode,'')='7' or isnull(@paycode,'')='11'  or isnull(@paycode,'')='12'  )                     
			begin                                   
				select @proflag=isnull(prjpricetype,2),@newcosttc=newcosttc,@oldcosttc=oldcosttc      
				from  projectinfo a,sysparaminfo b     
				where b.paramid='SP059' and b.compid=@compid and b.paramvalue=a.prjmodeId and prjno=@tmpPrjId  
				
				--美容师C大项无提成
				if(isnull(@empPostion,'') ='00402'  and ISNULL(@proflag,2)=1)                                      
				begin                                      
					update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId                                        
					set @empTicheng = 0     
					fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan ,@arrivaldmonth,@csitemstate,@billinsertype,@costpricetype                                     
					continue                                    
				end 	                          
				--if(isnull(@tmpYeji,0)>0)                                    
				--	set @fuflag=1                                    
				--else                                    
				--	set @fuflag=-1 
				if((ISNULL(@newcosttc,0)>0 or ISNULL(@oldcosttc,0)>0) and isnull(@paycode,'')<>'11')
				begin
					if(  isnull(@tmpItem,'')='7' and ISNULL(@costpricetype,0)=0 )  --指定客非体验项目
							update #allstaff_work_detail set staffticheng=@oldcosttc*@fuflag  where seqno=@tmpSeqId 
					else   if(  isnull(@tmpItem,'')='7' and ISNULL(@costpricetype,0)=1  ) --指定客体验项目
					begin
							if(ISNULL(@oldcosttc,0)>ISNULL(@mr_salary_fixed_ty,0))
							begin
								update #allstaff_work_detail set staffticheng=ISNULL(@mr_salary_fixed_ty,0)*@fuflag  where seqno=@tmpSeqId 
								set @empTicheng=ISNULL(@mr_salary_fixed_ty,0)*@fuflag
							end
							else
							begin
								update #allstaff_work_detail set staffticheng=@oldcosttc*@fuflag  where seqno=@tmpSeqId 
								set @empTicheng=@oldcosttc*@fuflag
							end
					end
					else   if(  isnull(@tmpItem,'')='8'  and ISNULL(@costpricetype,0)=0) --新客非体验项目
							update #allstaff_work_detail set staffticheng=@newcosttc*@fuflag  where seqno=@tmpSeqId 
					else   if(  isnull(@tmpItem,'')='8'  and ISNULL(@costpricetype,0)=1) --新客体验项目
					begin
							if(ISNULL(@newcosttc,0)>ISNULL(@mr_salary_fixed_ty,0))
							begin
								update #allstaff_work_detail set staffticheng=ISNULL(@mr_salary_fixed_ty,0)*@fuflag  where seqno=@tmpSeqId 
								set @empTicheng=ISNULL(@mr_salary_fixed_ty,0)*@fuflag 
							end
							else
							begin
								update #allstaff_work_detail set staffticheng=@newcosttc*@fuflag  where seqno=@tmpSeqId
								set @empTicheng=@newcosttc*@fuflag 
							end
					end
					else   if(  isnull(@tmpItem,'')='9' and ISNULL(@costpricetype,0)=1) --新客推荐体验
					begin
							if(ISNULL(@newcosttc,0)>ISNULL(@mr_salary_fixed_ty,0))
							begin
								update #allstaff_work_detail set staffticheng=ISNULL(@mr_salary_fixed_ty,0)*@fuflag  where seqno=@tmpSeqId 
								set @empTicheng=ISNULL(@mr_salary_fixed_ty,0)*@fuflag 
							end
							else
							begin
								update #allstaff_work_detail set staffticheng=@newcosttc*@fuflag  where seqno=@tmpSeqId
								set @empTicheng=@newcosttc*@fuflag 
							end
							--set @empTicheng=(@mr_salary_fixed_ty-ISNULL(@tjsr_salary_cost,0))*@fuflag
					end
					else   if(  isnull(@tmpItem,'')='9' and ISNULL(@costpricetype,0)=0) --新客推荐非体验
					begin
							update #allstaff_work_detail set staffticheng=(@newcosttc-ISNULL(@tjsr_salary_cost,0))*@fuflag  where seqno=@tmpSeqId 
							--set @empTicheng=(@newcosttc-ISNULL(@tjsr_salary_cost,0))*@fuflag
					end
					else
							update #allstaff_work_detail set staffticheng=0 where seqno=@tmpSeqId                                       
					fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan,@arrivaldmonth ,@csitemstate,@billinsertype,@costpricetype                                     
					continue  
				end
				if(@tmpPrjId in ('321','322','323','324','325','326','327','328','329','330','331','332') and isnull(@tmpItem,'')  in ('10','11','12') and  isnull(@empPostion,'') not in ('003','006','007','00701','00702') )
				begin
						if(  ISNULL(@csitemstate,1)=1)
							update #allstaff_work_detail set staffticheng=ISNULL(@xjc_salary_fixed_db,0)*@fuflag  where seqno=@tmpSeqId 
						else   if( ISNULL(@csitemstate,1)=2)
							update #allstaff_work_detail set staffticheng=ISNULL(@xjc_salary_fixed_ndb,0)*@fuflag  where seqno=@tmpSeqId 
						else   if( ISNULL(@csitemstate,1)=3)
							update #allstaff_work_detail set staffticheng=ISNULL(@xjc_salary_fixed_nhg,0)*@fuflag  where seqno=@tmpSeqId 
						else
							update #allstaff_work_detail set staffticheng=0 where seqno=@tmpSeqId                                      
						fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan,@arrivaldmonth ,@csitemstate,@billinsertype,@costpricetype                                     
						continue 
				end  
				if(@tmpPrjId in ('321','322','323','324','325','326','327','328','329','330','331','332') and isnull(@tmpItem,'')  in ('13','14','15') )
				begin
						update #allstaff_work_detail set staffticheng=0 where seqno=@tmpSeqId                                      
						fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan,@arrivaldmonth ,@csitemstate,@billinsertype,@costpricetype                                     
						continue 
				end                                        
				--项目抵用券使用面值做业绩                                      
				if(isnull(@paycode,'')='11' )                                      
				begin            
					select @tmpYeji=ISNULL(cardfaceamt,0),@dycardnocreatetype=createtype from nointernalcardinfo where cardno=@cardtype
					--活动券
					if(ISNULL(@dycardnocreatetype,0)=20140811)
					begin
						update #allstaff_work_detail set staffticheng=25*@fuflag where seqno=@tmpSeqId                                      
						fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan,@arrivaldmonth ,@csitemstate,@billinsertype,@costpricetype                                     
						continue
					end   
					--新登记抵用券
					if(ISNULL(@dycardnocreatetype,0)<>0)
					begin
						update #allstaff_work_detail set staffticheng=ISNULL(@mr_salary_fixed_ty,0)*@fuflag where seqno=@tmpSeqId                                      
						fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan,@arrivaldmonth ,@csitemstate,@billinsertype,@costpricetype                                     
						continue
					end   
					if(ISNULL(@fuflag,0)<0)                            
						set  @tmpYeji=ISNULL(@tmpYeji,0)*(-1)                                   
					update #allstaff_work_detail set staffyeji=@tmpYeji,billamt=@tmpYeji where seqno=@tmpSeqId                                        
				end                                       
				--烫染师 记业绩24% 提成5%                                      
				if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903' or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                                          
				begin                                      
					update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*ISNULL(@yjk_costrate_tr,0) where seqno=@tmpSeqId                                        
					set @empTicheng = isnull(@tmpYeji,0)*ISNULL(@yjk_costrate_tr,0)*ISNULL(@salaryrate_tra,0)                                       
					if( isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                
						set @empTicheng = isnull(@tmpYeji,0)*ISNULL(@yjk_costrate_tr,0)*ISNULL(@salaryrate_trb,0)                                        
				end                                        
				--首席，总监，设计师记业绩24%,提成22% 美容师记业绩24%,提成22%                                       
				else                                       
				begin                                      
					update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*ISNULL(@yjk_costrate_mrmf,0) where seqno=@tmpSeqId                                        
					set @empTicheng = isnull(@tmpYeji,0)*ISNULL(@yjk_costrate_mrmf,0)*ISNULL(@yjk_salaryrate_mrmf,0)  
					if(  isnull(@tmpItem,'')='9' and ISNULL(@empTicheng,0)>ISNULL(@tjsr_salary_cost,0)) --新客推荐     
						set  @empTicheng=ISNULL(@empTicheng,0)-ISNULL(@tjsr_salary_cost,0)                                  
				end                                        
				---洗剪吹项目 美发师扣除0.25的成本 总监扣除0.11的成本 首席扣除0.15的成本                                      
				---美发项目美容师参与的干洗20的业绩，水系5 提成系数0.3         
                             
				if(@tmpPrjId in ('300','3002','301','302','303','305','306','309','311','321','322','323','324','325','326','327','328','329','330','331','332'))                                    
				begin                                      
					if(isnull(@empPostion,'')in('004','00103','00401','00402') or isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903' or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                            
					begin                                      
						if(@tmpPrjId='300' or @tmpPrjId='302' or @tmpPrjId='303'  or @tmpPrjId='309' )                                      
						begin                                      
							update #allstaff_work_detail set staffyeji=20*@fuflag where seqno=@tmpSeqId                                        
							set @empTicheng =6*@fuflag                                      
						end                                       
						else                                      
						begin                                      
							update #allstaff_work_detail set staffyeji=5*@fuflag where seqno=@tmpSeqId                                        
							set @empTicheng =1.5*@fuflag                                      
						end                                      
					end                                             
					if((@tmpPrjId='300' or @tmpPrjId='3002') and isnull(@tmpItem,'') not in ('7','8','9'))         
					begin                                      
						if(isnull(@empPostion,'')='006' or isnull(@empPostion,'') ='007' or isnull(@empPostion,'') ='00701' or isnull(@empPostion,'') ='00702')--首席总监                                      
						begin                                      
							update #allstaff_work_detail set staffyeji=10*@fuflag where seqno=@tmpSeqId                                      
							set @empTicheng =3*@fuflag                                      
						end                                             
						else if(isnull(@empPostion,'')='003')                                      
						begin                                      
							update #allstaff_work_detail set staffyeji=10*@fuflag where seqno=@tmpSeqId                                      
							set @empTicheng =2.8*@fuflag                                      
						end                                             
						else                                       
						begin                                      
							update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId                                        
							set @empTicheng =0                                      
						end                                      
					end                                      
				end                               
			end       
			else      
			begin      
				select @proflag=isnull(prjpricetype,2),@PROJECT_COST=isnull(costprice,0),@Performance_Ratio=isnull(kyjrate,1),
				@Wage_Rates=isnull(ktcrate,1),@PROJECT_TYPE=prjtype,@newcosttc=newcosttc,@oldcosttc=oldcosttc ,@saleprice=saleprice      
				from  projectinfo a,sysparaminfo b     
				where b.paramid='SP059' and b.compid=@compid and b.paramvalue=a.prjmodeId and prjno=@tmpPrjId  
					
				--美容师C大项无提成
				if(isnull(@empPostion,'') ='00402'  and ISNULL(@proflag,2)=1)                                      
				begin                                      
					update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId                                        
					set @empTicheng = 0     
					fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan ,@arrivaldmonth,@csitemstate,@billinsertype,@costpricetype                                     
					continue                                    
				end 
				
				if(ISNULL(@newcosttc,0)>0 or ISNULL(@oldcosttc,0)>0)
				begin
					if(  isnull(@tmpItem,'')='7' and ISNULL(@costpricetype,0)=0 )  --指定客非体验项目
					begin
							update #allstaff_work_detail set staffticheng=@oldcosttc*@fuflag  where seqno=@tmpSeqId 
							set @empTicheng=@oldcosttc*@fuflag
					end
					else   if(  isnull(@tmpItem,'')='7' and ISNULL(@costpricetype,0)=1  ) --指定客体验项目
					begin
							if(ISNULL(@oldcosttc,0)>ISNULL(@mr_salary_fixed_ty,0))
							begin
								update #allstaff_work_detail set staffticheng=ISNULL(@mr_salary_fixed_ty,0)*@fuflag  where seqno=@tmpSeqId 
								set @empTicheng=ISNULL(@mr_salary_fixed_ty,0)*@fuflag
							end
							else
							begin
								update #allstaff_work_detail set staffticheng=@oldcosttc*@fuflag  where seqno=@tmpSeqId 
								set @empTicheng=@oldcosttc*@fuflag
							end
					end
					else   if(  isnull(@tmpItem,'')='8' and ISNULL(@costpricetype,0)=0) --新客非体验项目
					begin
							update #allstaff_work_detail set staffticheng=@newcosttc*@fuflag  where seqno=@tmpSeqId
							set @empTicheng=@newcosttc*@fuflag 
					end
					else   if(  isnull(@tmpItem,'')='8' and ISNULL(@costpricetype,0)=1) --新客体验项目
					begin
							if(ISNULL(@newcosttc,0)>ISNULL(@mr_salary_fixed_ty,0))
							begin
								update #allstaff_work_detail set staffticheng=ISNULL(@mr_salary_fixed_ty,0)*@fuflag  where seqno=@tmpSeqId 
								set @empTicheng=ISNULL(@mr_salary_fixed_ty,0)*@fuflag 
							end
							else
							begin
								update #allstaff_work_detail set staffticheng=@newcosttc*@fuflag  where seqno=@tmpSeqId
								set @empTicheng=@newcosttc*@fuflag 
							end
					end
					else   if(  isnull(@tmpItem,'')='9' and ISNULL(@costpricetype,0)=1) --新客推荐体验
					begin
							if(ISNULL(@newcosttc,0)>ISNULL(@mr_salary_fixed_ty,0))
							begin
								update #allstaff_work_detail set staffticheng=(@mr_salary_fixed_ty-ISNULL(@tjsr_salary_cost,0))*@fuflag  where seqno=@tmpSeqId 
								set @empTicheng=(@mr_salary_fixed_ty-ISNULL(@tjsr_salary_cost,0))*@fuflag
							end
							else
							begin
								update #allstaff_work_detail set staffticheng=(@newcosttc-ISNULL(@tjsr_salary_cost,0))*@fuflag  where seqno=@tmpSeqId 
								set @empTicheng=(@newcosttc-ISNULL(@tjsr_salary_cost,0))*@fuflag
							end
					end
					else   if(  isnull(@tmpItem,'')='9' and ISNULL(@costpricetype,0)=0) --新客推荐非体验
					begin
							update #allstaff_work_detail set staffticheng=(@newcosttc-ISNULL(@tjsr_salary_cost,0))*@fuflag  where seqno=@tmpSeqId 
							set @empTicheng=(@newcosttc-ISNULL(@tjsr_salary_cost,0))*@fuflag
					end
					else
					begin
							update #allstaff_work_detail set staffticheng=0 where seqno=@tmpSeqId
							set @empTicheng=0
					end  
					if( isnull(@paycode,'')='13')
					begin
							update #allstaff_work_detail set staffticheng=ISNULL(@mr_salary_fixed_tmk,0)*@fuflag  where seqno=@tmpSeqId 
							set @empTicheng=ISNULL(@mr_salary_fixed_tmk,0)*@fuflag 
					end	                                   
					fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan,@arrivaldmonth ,@csitemstate,@billinsertype,@costpricetype                                     
					continue  
				end
				
				if(@tmpPrjId in ('321','322','323','324','325','326','327','328','329','330','331','332') and isnull(@tmpItem,'')  in ('10','11','12') and  isnull(@empPostion,'') not in ('003','006','007','00701','00702') )
				begin
						if(  ISNULL(@csitemstate,1)=1)
							update #allstaff_work_detail set staffticheng=ISNULL(@xjc_salary_fixed_db,0)*@fuflag  where seqno=@tmpSeqId 
						else   if( ISNULL(@csitemstate,1)=2)
							update #allstaff_work_detail set staffticheng=ISNULL(@xjc_salary_fixed_ndb,0)*@fuflag  where seqno=@tmpSeqId 
						else   if( ISNULL(@csitemstate,1)=3)
							update #allstaff_work_detail set staffticheng=ISNULL(@xjc_salary_fixed_nhg,0)*@fuflag  where seqno=@tmpSeqId 
						else
							update #allstaff_work_detail set staffticheng=0 where seqno=@tmpSeqId                                      
						fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan,@arrivaldmonth ,@csitemstate,@billinsertype,@costpricetype                                     
						continue 
				end  
				if(@tmpPrjId in ('321','322','323','324','325','326','327','328','329','330','331','332') and isnull(@tmpItem,'')  in ('13','14','15') )
				begin
						update #allstaff_work_detail set staffticheng=0 where seqno=@tmpSeqId                                      
						fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan,@arrivaldmonth ,@csitemstate,@billinsertype,@costpricetype                                     
						continue 
				end     
				--if(@compid in ('006','047','033','014','041','046') and isnull(@empPostion,'')  in ('003','006','007','00701','00702')   )
				if(isnull(@isNewComdMode,0)=1 and isnull(@empPostion,'')  in ('003','006','007','00701','00702') and ISNULL(@PROJECT_TYPE,'')<>'6'  )
				begin
					if(ISNULL(@arrivaldmonth,0)<=5)
					begin
						set @Wage_Rates=ISNULL(@mf_salaryrate_fivedown,0)  --********新6家店美发师5月内的提成系数为0.3
					end
					else
					begin
						set @Wage_Rates=ISNULL(@mf_salaryrate_fiveup,0)  --********新6家店的提成系数为0.25
					end
				end          
				if(@PROJECT_TYPE<>'5')--美甲成本单独算                        
				begin                        
					update #allstaff_work_detail set staffyeji=(isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*isnull(@Performance_Ratio,0) where seqno=@tmpSeqId                                             
				end 
				
				    
				set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*@Performance_Ratio*@Wage_Rates
				if(  isnull(@tmpItem,'')='9' and ISNULL(@empTicheng,0)>ISNULL(@tjsr_salary_cost,0)) --新客推荐 
					set @empTicheng =ISNULL(@empTicheng,0) -ISNULL(@tjsr_salary_cost,0)  
					                                         
				--首席总监 扣除成本后×业绩比率，提成 30%                                      
				--if(@compid not in ('006','047','033','014','041','046') )  --非6家店的为标准体系走
				if(isnull(@isNewComdMode,0)=0)
				begin 
					if((isnull(@empPostion,'')='006' or isnull(@empPostion,'') ='007' or isnull(@empPostion,'') ='00701' or isnull(@empPostion,'') ='00702') and ISNULL(@PROJECT_TYPE,'0')<>'6' )                                          
						set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*@Performance_Ratio*0.3                                           
				end
				--烫染师 扣除成本后记业绩5%                                      
				if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903'  )                                          
					set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*isnull(@Performance_Ratio,0)*ISNULL(@salaryrate_tra,0)                                           
				if( isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                                      
					set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*isnull(@Performance_Ratio,0)*ISNULL(@salaryrate_trb,0)                                           
                         
				---洗剪吹项目 美发师扣除0.25的成本 总监扣除0.11的成本 首席扣除0.15的成本                                      
				---美发项目美容师参与的干洗20的业绩，水系5 提成系数0.3                                          
				if(@tmpPrjId in ('300','3002','301','302','303','305','306','309','311','321','322','323','324','325','326','327','328','329','330','331','332'))                                    
				begin                                      
					if(isnull(@empPostion,'')='003') --美发师                                      
					begin 
					    --if(@compid in ('006','047','033','014','041','046')  )
					    if(isnull(@isNewComdMode,0)=1)
						begin
							update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*ISNULL(@xjc_costrate_sjs,0) where seqno=@tmpSeqId                                        
							set @empTicheng = isnull(@tmpYeji,0)*ISNULL(@xjc_costrate_sjs,0)*@Wage_Rates        
						end 
						else
						begin                                     
							update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*ISNULL(@xjc_costrate_sjs,0) where seqno=@tmpSeqId                                        
							set @empTicheng = isnull(@tmpYeji,0)*ISNULL(@xjc_costrate_sjs,0)*0.28 
						end                                        
					end                                       
					else if(isnull(@empPostion,'')='006') --首席                                      
					begin  
						--if(@compid in ('006','047','033','014','041','046')   )
						if(isnull(@isNewComdMode,0)=1)
						begin
							update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*ISNULL(@xjc_costrate_sx,0) where seqno=@tmpSeqId                                        
							set @empTicheng = isnull(@tmpYeji,0)*ISNULL(@xjc_costrate_sx,0)*@Wage_Rates        
						end 
						else
						begin                                      
							update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*ISNULL(@xjc_costrate_sx,0) where seqno=@tmpSeqId                                        
							set @empTicheng = isnull(@tmpYeji,0)*ISNULL(@xjc_costrate_sx,0)*0.3   
						end                      
					end                                   
					else if(isnull(@empPostion,'')='007' or isnull(@empPostion,'')='00701' or isnull(@empPostion,'')='00702') --总监                                     
					begin                                      
						--if(@compid in ('006','047','033','014','041','046')   )
						if(isnull(@isNewComdMode,0)=1)
						begin
							update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*ISNULL(@xjc_costrate_zj,0) where seqno=@tmpSeqId                                        
							set @empTicheng = isnull(@tmpYeji,0)*ISNULL(@xjc_costrate_zj,0)*@Wage_Rates        
						end 
						else
						begin   
							update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*ISNULL(@xjc_costrate_zj,0) where seqno=@tmpSeqId                                        
							set @empTicheng = isnull(@tmpYeji,0)*ISNULL(@xjc_costrate_zj,0)*0.3 
						end                                       
					end                                                      
					else if(isnull(@empPostion,'')in('004','00103','00401','00402') or isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903' or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                       
					begin                                      
						if(@tmpPrjId='300' or @tmpPrjId='302' or @tmpPrjId='303'  or @tmpPrjId='309' ) 
						begin                                      
							update #allstaff_work_detail set staffyeji=20*@fuflag where seqno=@tmpSeqId                                        
							set @empTicheng =6*@fuflag                                      
						end                                       
						else                                      
						begin                                     
							update #allstaff_work_detail set staffyeji=5*@fuflag where seqno=@tmpSeqId                                        
							set @empTicheng =1.5*@fuflag                                      
						end                                      
					end     
					   
					if((@tmpPrjId='300' or @tmpPrjId='3002') and isnull(@tmpItem,'') not in ('7','8','9'))                       
					begin                                            
						if(isnull(@empPostion,'')='006' or isnull(@empPostion,'') ='007' or isnull(@empPostion,'') ='00701' or isnull(@empPostion,'') ='00702')--首席总监              
						begin                                      
							update #allstaff_work_detail set staffyeji=10*@fuflag where seqno=@tmpSeqId                                      
							set @empTicheng =3*@fuflag                                      
						end                                             
						else if(isnull(@empPostion,'')='003')                                      
						begin                                      
							update #allstaff_work_detail set staffyeji=10*@fuflag where seqno=@tmpSeqId                                      
							set @empTicheng =2.8*@fuflag                                      
						end                                             
						else                                       
						begin                                      
							update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId                                        
							set @empTicheng =0                                      
						end                    
					end                                      
				end                                      
				if(isnull(@paycode,'')='13' )                                      
				begin                                      
					declare @tmcardfrom int --0 正常开卡,1 赠送开卡                                      
					select @tmcardfrom=ISNULL(entrytype,0) from nointernalcardinfo where cardno=@cardtype                                      
					if(ISNULL(@tmcardfrom,0)=1)                                      
					begin                                      
						--烫染师 记业绩24% 提成5%                                      
						if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903' or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                                          
						begin                                      
							update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*0.24 where seqno=@tmpSeqId                                        
							set @empTicheng = isnull(@tmpYeji,0)*0.24*0.05                                        
							if( isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008' )                                      
								set @empTicheng = isnull(@tmpYeji,0)*0.24*0.06                                        
						end                                        
						--首席，总监，设计师记业绩24%,提成22% 美容师记业绩24%,提成22%                                       
						else                                       
						begin                                      
							update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*0.24 where seqno=@tmpSeqId                                        
							set @empTicheng = isnull(@tmpYeji,0)*0.24*0.22                                      
						end     
					end                                       
				end              
				--美发的输入在中工和小工位置的(美发师,首席,总监)无提成                                      
				if((@PROJECT_TYPE='3' or @PROJECT_TYPE='6') and isnull(@empPostion,'') in ('003','006','007','00701','00702') and isnull(@tmpItem,'') not in ('7','8','9'))                                      
				begin                                      
						set @empTicheng = 0                                       
				end                                      
				else if(@PROJECT_TYPE='5')-- 美甲业绩扣10%的成本，提成4，6分                                      
				begin                                      
					if(isnull(@empPostion,'')='005' )                                      
					begin                          
							if( ISNULL(@comptypebyfinger,'1')='1')                          
							begin                             
								update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*0.9 where seqno=@tmpSeqId                                        
								set @empTicheng = isnull(@tmpYeji,0)*0.9*0.6                            
							end                                    
					end                                      
					else                                      
					begin                                      
							update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId                                        
							set @empTicheng =0                                      
					end                                      
				end           
				--一加一等于一(活动)              
				if(isnull(@empPostion,'') in('004','00103','00401','00402')  and @tmpPrjId in ('48811') and isnull(@paycode,'')<>'13')                                      
				begin                                      
					if(isnull(@tmpItem,'') in ('7','8','9'))                      
					begin             
						if(isnull(@tmpYeji,0)<100)           
							set @tmpYeji=100                            
						update #allstaff_work_detail set staffyeji= isnull(@tmpYeji,0)*0.8 where seqno=@tmpSeqId                 
						set @empTicheng = isnull(@tmpYeji,0)*0.8*0.3*@fuflag                      
					end                                 
					else                                      
					begin                                      
						update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId                                        
						set @empTicheng = 0                     
					end                                      
				end                                     
				if(isnull(@empPostion,'')in('004','00103','00401','00402') and ISNULL(@PROJECT_TYPE,'') in ('3','6') and ISNULL(@proflag,2)=1)                                      
				begin                             
					update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId                                        
					set @empTicheng = 0                                       
				end                    
				if(isnull(@empPostion,'') in ('003','006','007','00701','00702','00901','00902','00903','00904','008')  and ISNULL(@PROJECT_TYPE,'') ='4' and ISNULL(@proflag,2)=1)                                      
				begin                                      
					update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId                                        
					set @empTicheng = 0                                       
				end                                       
			end       
		end                    
		---更新提成                                   
		update #allstaff_work_detail set staffticheng=@empTicheng,person_inid=@empinid,postation=@empPostion where seqno=@tmpSeqId                                                                        
		fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan,@arrivaldmonth,@csitemstate,@billinsertype,@costpricetype                                 
	end                                                              
	close cur_yeji_ticheng                                                              
	deallocate cur_yeji_ticheng                  
         
      
  if(@handtype=2)      
  begin      
		delete allstaff_work_detail_daybyday where compid=@compid and srvdate=@fromdate      
		insert allstaff_work_detail_daybyday(compid,person_inid,action_id,srvdate,code,name,payway,billamt,ccount,cost,staffticheng,staffyeji,prj_type,cls_flag,billid,paycode,cardid,cardtype,postation)      
		select compid,person_inid,action_id,srvdate,code,name,payway,billamt,ccount,cost,staffticheng,staffyeji,prj_type,cls_flag,billid,paycode,cardid,cardtype,postation      
		from #allstaff_work_detail      
	end      
	--美发师做美发项目 超过指定业绩增高普通的提成比率                                      
	--美发师业绩超2.5W,首席超5W,总监 7W 美发项目提成比率0.35                                      
	create  table #emp_yeji_total_resultx                                       
	(                                      
		inid  varchar(20) null,  --员工内部编号                                         
		yeji  float  null, -- 业绩                       
		lv   float  null, --提成比率                                           
	)                                         
                  
    insert #emp_yeji_total_resultx(inid,yeji)                                             
	select  person_inid,sum(isnull(staffyeji,0)) from #allstaff_work_detail where ISNULL(action_id,-1)<>4 and paycode in ('1','2','6','0','14','15') group by person_inid                                    
    
    --if(@compid in ('006','047','033','014','041','046'))
    if(isnull(@isNewComdMode,0)=1)
    begin
		if exists (select 1 from compchaininfo where curcomp='00105' and relationcomp=@compid )
		begin
			--新6店 5个月以上 发型师  5W下 0.35 5W 上0.40
			update a set staffticheng=isnull(staffyeji,0)*0.35                                      
			from #allstaff_work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                      
			where a.person_inid=b.inid and isnull(a.postation,'') in ('003','006','007','00701','00702')                                        
			and action_id in (7,10,13)  and ISNULL(paycode,'') not in ('9','11','12')                                       
			and b.yeji<50000 and  prjno=code and prjtype='3'                                      
			and isnull(cardtype,'')   not in ('MFOLD','ZK') 
			--and ISNULL(arrivaldmonth,0)>5  
			
			update a set staffticheng=isnull(staffyeji,0)*0.4                                      
			from #allstaff_work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                      
			where a.person_inid=b.inid and isnull(a.postation,'') in ('003','006','007','00701','00702')                                       
			and action_id in (7,10,13)  and ISNULL(paycode,'') not in ('9','11','12')                                       
			and b.yeji>=50000 and  prjno=code and prjtype='3'                                      
			and isnull(cardtype,'')   not in ('MFOLD','ZK') 
			--and ISNULL(arrivaldmonth,0)>5  
		end
		else
		begin		
			update a set staffticheng=isnull(staffyeji,0)*0.30                                      
			from #allstaff_work_detail a ,projectnameinfo with(nolock)                                      
			where isnull(a.postation,'')  in ('003','006','007','00701','00702')                                           
			and action_id in (7,10,13)  and ISNULL(paycode,'') not in ('9','11','12')                                      
			and  prjno=code and prjtype='3'                                      
			and isnull(cardtype,'')   not in ('MFOLD','ZK') 
			and ISNULL(srvdate,'')<='20140831'
			
			--新6店 5个月以上 发型师 1W-2W(不包含2W) 老客提成比率为30%
			update a set staffticheng=isnull(staffyeji,0)*0.30                                      
			from #allstaff_work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                      
			where a.person_inid=b.inid and isnull(a.postation,'')='003'                                       
			and action_id in (7,10,13)  and ISNULL(paycode,'') not in ('9','11','12')                                      
			and b.yeji>=10000 and b.yeji<20000 and  prjno=code and prjtype='3'                                      
			and isnull(cardtype,'')   not in ('MFOLD','ZK') 
			--and ISNULL(arrivaldmonth,0)>5  
			
			
			--新6店 5个月以上 发型师 2W以上 老客提成比率为35%
			update a set staffticheng=isnull(staffyeji,0)*0.35                                     
			from #allstaff_work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                      
			where a.person_inid=b.inid and isnull(a.postation,'')='003'                                       
			and action_id in (7,10,13)  and ISNULL(paycode,'') not in ('9','11','12')                                      
			and b.yeji>=20000 and  prjno=code and prjtype='3'                                      
			and isnull(cardtype,'')   not in ('MFOLD','ZK') 
			--and ISNULL(arrivaldmonth,0)>5                                    
	         
			----新6店 5个月以上 首席 2W-3W(不包含3W) 老客提成比率为30%    
			update a set staffticheng=isnull(staffyeji,0)*0.30                                      
			from #allstaff_work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                      
			where a.person_inid=b.inid and isnull(a.postation,'')='006'                                       
			and action_id in (7,10,13) and ISNULL(paycode,'') not in ('9','11','12')                                      
			and b.yeji>=20000 and b.yeji<30000 and  prjno=code and prjtype='3'                                      
			and isnull(cardtype,'')   not in ('MFOLD','ZK')    
			--and ISNULL(arrivaldmonth,0)>5    
			
			----新6店 5个月以上 首席 3W-5W(不包含5W) 老客提成比率为35%    
			update a set staffticheng=isnull(staffyeji,0)*0.35                                     
			from #allstaff_work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                      
			where a.person_inid=b.inid and isnull(a.postation,'')='006'                                       
			and action_id in (7,10,13) and ISNULL(paycode,'') not in ('9','11','12')                                      
			and b.yeji>=30000 and b.yeji<50000 and  prjno=code and prjtype='3'                                      
			and isnull(cardtype,'')   not in ('MFOLD','ZK')    
			--and ISNULL(arrivaldmonth,0)>5    
			
			----新6店 5个月以上 首席 5W以上 老客提成比率为40%    
			update a set staffticheng=isnull(staffyeji,0)*0.40                                     
			from #allstaff_work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                      
			where a.person_inid=b.inid and isnull(a.postation,'')='006'                                       
			and action_id in (7,10,13) and ISNULL(paycode,'') not in ('9','11','12')                                      
			and b.yeji>=50000 and  prjno=code and prjtype='3'                                      
			and isnull(cardtype,'')   not in ('MFOLD','ZK')    
			--and ISNULL(arrivaldmonth,0)>5 
			
			                              
			----新6店 5个月以上 总监,创意总监,设计总监 3W-5W(不包含5W) 老客提成比率为30%                              
			update a set staffticheng=isnull(staffyeji,0)*0.30                                      
			from #allstaff_work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                      
			where a.person_inid=b.inid and isnull(a.postation,'') in ('007' ,'00701','00702')                                     
			and action_id in (7,10,13) and ISNULL(paycode,'') not in ('9','11','12')                                      
			and b.yeji>=30000 and b.yeji<50000   and prjno=code and prjtype='3'                                      
			and isnull(cardtype,'')   not in ('MFOLD','ZK')                                      
			--and ISNULL(arrivaldmonth,0)>5 
			
			----新6店 5个月以上 总监,创意总监,设计总监 5W-8W(不包含5W) 老客提成比率为35%                              
			update a set staffticheng=isnull(staffyeji,0)*0.35                                      
			from #allstaff_work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                      
			where a.person_inid=b.inid and isnull(a.postation,'') in ('007' ,'00701','00702')                                     
			and action_id in (7,10,13) and ISNULL(paycode,'') not in ('9','11','12')                                      
			and b.yeji>=50000 and b.yeji<70000   and prjno=code and prjtype='3'                                      
			and isnull(cardtype,'')   not in ('MFOLD','ZK')                                      
			--and ISNULL(arrivaldmonth,0)>5 
			
			----新6店 5个月以上 总监,创意总监,设计总监 8W以上 老客提成比率为40%                              
			update a set staffticheng=isnull(staffyeji,0)*0.40                                      
			from #allstaff_work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                      
			where a.person_inid=b.inid and isnull(a.postation,'') in ('007' ,'00701','00702')                                     
			and action_id in (7,10,13) and ISNULL(paycode,'') not in ('9','11','12')                                      
			and b.yeji>=70000   and prjno=code and prjtype='3'                                      
			and isnull(cardtype,'')   not in ('MFOLD','ZK')                                      
			--and ISNULL(arrivaldmonth,0)>5 
		end 
    end   
    else                                  
	begin
	                  
		update a set staffticheng=isnull(staffyeji,0)*0.35                                      
		from #allstaff_work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                      
		where a.person_inid=b.inid and isnull(a.postation,'')='003'                                       
		and (action_id>=7 and action_id<=15) and ISNULL(paycode,'')<>'9'                                      
		and b.yeji>=30000 and  prjno=code and prjtype='3'                                      
		and isnull(cardtype,'')   not in ('MFOLD','ZK')                                      
	                                       
		update a set staffticheng=isnull(staffyeji,0)*0.35                                      
		from #allstaff_work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                      
		 where a.person_inid=b.inid and isnull(a.postation,'')='006'                                       
		and (action_id>=7 and action_id<=15) and ISNULL(paycode,'')<>'9'                                      
		and b.yeji>=50000 and  prjno=code and prjtype='3'                                      
		and isnull(cardtype,'')   not in ('MFOLD','ZK')                                      
	                                       
		update a set staffticheng=isnull(staffyeji,0)*0.35                                      
		from #allstaff_work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)             
		where a.person_inid=b.inid and isnull(a.postation,'')='007'                                       
		and (action_id>=7 and action_id<=15) and ISNULL(paycode,'')<>'9'                                      
		and b.yeji>=70000  and prjno=code and prjtype='3'                                      
		and isnull(cardtype,'')   not in ('MFOLD','ZK')                                      
	                     
		update a set staffticheng=isnull(staffyeji,0)*0.35                                      
		from #allstaff_work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                      
		where a.person_inid=b.inid and isnull(a.postation,'') in ('00701' ,'00702' )                                     
		and (action_id>=7 and action_id<=15) and ISNULL(paycode,'')<>'9'                                      
		and b.yeji>=90000  and prjno=code and prjtype='3'                                      
		and isnull(cardtype,'')   not in ('MFOLD','ZK')    
    end            
	---美容师A类                    
	/*if(@compid in ('008','017','019','026','032'))                    
	begin                    
                     
	update a set staffticheng=ISNULL(staffticheng,0)+ISNULL(staffyeji,0)*0.05        
	 from #allstaff_work_detail a,#emp_yeji_total_resultx b                    
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
    from #allstaff_work_detail a,nointernalcardinfo,#emp_yeji_total_resultx b        
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
   from #allstaff_work_detail a,#emp_yeji_total_resultx b                    
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
    from #allstaff_work_detail a,nointernalcardinfo,#emp_yeji_total_resultx b                    
    where a.person_inid=b.inid                     
   and a.cardtype=cardno                    
   and ISNULL(a.postation,'')='004'                    
   and (action_id>=7 and action_id<=15)             
   and paycode='13'                    
   and yeji<70000                     
   and yeji>=50000                    
   and isnull(entrytype,0)=0                    
   and action_id not in (26,27,28,29,30,31)                    
 end  */                  
 drop table #emp_yeji_total_resultx                                      
                                       
  
                                                  
                          
 --现有提成方式                        
 --承包方式:五五分成,扣5%成本                        
 --公司直营:个人业绩1-5000 50% 扣10%成本,5000以上35%扣10%成本                                     
                          
    if(ISNULL(@comptypebyfinger,'1')='3') --承包方式                        
    begin                        
  update #allstaff_work_detail set staffyeji=ISNULL(staffyeji,0)*0.95,staffticheng=isnull(staffticheng,0)*0.95*0.5                        
  from #allstaff_work_detail,staffinfo where Manageno=person_inid and position = '005'                        
    end                   
    else  if(ISNULL(@comptypebyfinger,'1')='2') --公司直营                         
    begin                        
                        
   update #allstaff_work_detail set staffyeji=ISNULL(staffyeji,0)*0.9,staffticheng=isnull(staffticheng,0)*0.5*0.9                        
   where  person_inid in (select person_inid from #allstaff_work_detail ,staffinfo where Manageno=person_inid and position = '005'                        
   group by person_inid having SUM(ISNULL(staffyeji,0))<=5000 )                         
                          
                              
   update #allstaff_work_detail set staffyeji=ISNULL(staffyeji,0)*0.9,staffticheng=isnull(staffticheng,0)*0.35*0.9                        
   where  person_inid in (select person_inid from #allstaff_work_detail ,staffinfo where Manageno=person_inid and position = '005'                        
   group by person_inid having SUM(ISNULL(staffyeji,0))>5000 )                         
    end         
                     
      
   --88 只要是88的都是用业绩*0.02
   update #allstaff_work_detail set staffticheng=staffyeji*0.02 where action_id=88

        
 create table #allstaff_work_analysis      
 (      
  person_inid   varchar(20)   NULL, --员工内部编号      
  staffno    varchar(30)   NULL, --员工工号      
  staffname   varchar(30)   NULL, --员工姓名      
  staffposition  varchar(30)   NULL, --员工职位      
  oldcostcount  float    NULL, --老客项目数      
  newcostcount  float    NULL, --新客项目数      
  trcostcount   float    NULL, --烫染项目数      
  cashbigcost   float    NULL, --现金大项      
  cashsmallcost  float    NULL, --现金小项      
  cashhulicost  float    NULL, --现金护理      
  cardbigcost   float    NULL, --销卡大项      
  cardsmallcost  float    NULL, --销卡小项      
  cardhulicost  float    NULL, --销卡护理      
  cardprocost   float    NULL, --疗程消费      
  cardsgcost   float    NULL, --收购卡消费      
  cardpointcost  float    NULL, --积分消费      
  projectdycost  float    NULL, --项目抵用券      
  cashdycost   float    NULL, --现金抵用券      
  tmcardcost   float    NULL, --条卡卡消费      
  salegoodsamt  float    NULL, --产品销售      
  salecardsamt  float    NULL, --卡销售      
  prochangeamt  float    NULL, --疗程兑换      
  saletmkamt   float    NULL, --条码卡销售      
  qhpayinner   float    NULL, --全韩店内支付      
  qhpayouter   float    NULL, --全韩对方支付      
  jdpayinner   float    NULL, --暨大店内支付      
  smpayinner   float    NULL, --私密店内支付      
  staffyeji   float    NULL, --员工提成合计      
  staffcashyeji  float    NULL, --员工现金业绩合计      
 )       
 create clustered index idx_work_analysis_person_inid on #allstaff_work_analysis(person_inid)        
       
 if(@handtype=3)--员工业绩统计表      
 begin      

  insert #allstaff_work_analysis(person_inid,staffyeji)      
  select person_inid,SUM(isnull(staffyeji,0)) from #allstaff_work_detail where isnull(person_inid,'')<>'' and action_id<>'36' group by person_inid       
        
  update a set a.staffno=b.staffno,a.staffname=b.staffname,a.staffposition=b.position      
  from #allstaff_work_analysis a,staffinfo b      
  where a.person_inid=b.Manageno      
        
  --现金大项      
  update a set cashbigcost=ISNULL((      
   select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b,projectnameinfo c      
    where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype<>6 and c.prjpricetype=1 and b.paycode in ('1','2','6','0','14','15') ),0)      
  from #allstaff_work_analysis a      
        
  --现金小项      
  update a set cashsmallcost=ISNULL((      
   select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b,projectnameinfo c      
    where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype<>6 and c.prjpricetype=2 and b.paycode in ('1','2','6','0','14','15') ),0)      
  from #allstaff_work_analysis a      
        
  --现金护理      
  update a set cashhulicost=ISNULL((      
   select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b,projectnameinfo c      
    where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype=6  and b.paycode in ('1','2','6','0','14','15') ),0)      
  from #allstaff_work_analysis a      
        
  --销卡大项      
  update a set cardbigcost=ISNULL((      
   select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b,projectnameinfo c      
    where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype<>6 and c.prjpricetype=1 and b.paycode in ('4','17') ),0)      
  from #allstaff_work_analysis a      
        
  --销卡小项      
  update a set cardsmallcost=ISNULL((      
   select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b,projectnameinfo c      
    where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype<>6 and c.prjpricetype=2 and b.paycode  in ('4','17')  ),0)      
  from #allstaff_work_analysis a      
        
  --销卡护理      
  update a set cardhulicost=ISNULL((      
   select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b,projectnameinfo c      
    where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype=6  and b.paycode in ('4','17')  ),0)      
  from #allstaff_work_analysis a      
        
  --疗程消费      
  update a set cardprocost=ISNULL((      
   select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b      
    where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.paycode  ='9' ),0)      
  from #allstaff_work_analysis a      
        
  --收购卡消费      
  update a set cardsgcost=ISNULL((      
   select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b      
    where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.paycode  ='A' ),0)      
  from #allstaff_work_analysis a      
        
  --积分消费      
  update a set cardpointcost=ISNULL((      
   select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b      
    where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.paycode  ='7' ),0)      
  from #allstaff_work_analysis a      
        
  --项目抵用券消费      
  update a set projectdycost=ISNULL((      
   select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b      
    where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.paycode  ='11' ),0)      
  from #allstaff_work_analysis a      
        
  --现金抵用券消费      
  update a set cashdycost=ISNULL((      
   select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b      
    where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.paycode  ='12' ),0)      
  from #allstaff_work_analysis a      
        
  --条码卡消费      
  update a set tmcardcost=ISNULL((      
   select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b      
    where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.paycode  ='13' ),0)      
  from #allstaff_work_analysis a      
        
  --产品销售      
  update a set salegoodsamt=ISNULL((      
   select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b      
    where a.person_inid=b.person_inid and b.action_id >=16 and b.action_id<=24  ),0)      
  from #allstaff_work_analysis a      
        
  --会员卡销售      
  update a set salecardsamt=ISNULL((      
   select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b      
    where a.person_inid=b.person_inid and b.action_id >=1 and b.action_id<=3  ),0)      
  from #allstaff_work_analysis a      
        
  --疗程兑换      
  update a set prochangeamt=ISNULL((      
   select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b      
    where a.person_inid=b.person_inid and b.action_id =4  ),0)      
  from #allstaff_work_analysis a      
        
  --条码卡销售      
  update a set saletmkamt=ISNULL((      
   select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b      
    where a.person_inid=b.person_inid and b.action_id =5  ),0)      
  from #allstaff_work_analysis a      
        
      
  --全韩店内支付      
  update a set qhpayinner=ISNULL((      
   select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b      
    where a.person_inid=b.person_inid and b.action_id =28  ),0)      
  from #allstaff_work_analysis a      
        
  --全韩对方支付      
  update a set qhpayouter=ISNULL((      
   select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b      
    where a.person_inid=b.person_inid and b.action_id =27 ),0)      
  from #allstaff_work_analysis a      
        
  --暨大店内支付      
  update a set jdpayinner=ISNULL((      
   select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b      
    where a.person_inid=b.person_inid and b.action_id =28  ),0)      
  from #allstaff_work_analysis a      
        
  --私密店内支付      
  update a set smpayinner=ISNULL((      
   select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b      
    where a.person_inid=b.person_inid and b.action_id =29  ),0)      
  from #allstaff_work_analysis a      
        
  --现金业绩    

  update a set staffcashyeji=ISNULL((      
   select sum(ISNULL(staffyeji,0)) from #allstaff_work_detail b      
    where a.person_inid=b.person_inid  and b.paycode in ('1','2','6','0','14','15') and ISNULL(b.action_id,-1)<>4 ),0)      
  from #allstaff_work_analysis a      
        
  --新客项目数      
  update a set  newcostcount=ISNULL((      
   select count(distinct billid) from #allstaff_work_detail b      
    where a.person_inid=b.person_inid and b.action_id in (8,11,14) and isnull(ccount,0)>0  ),0)      
  from #allstaff_work_analysis a      
        
  --老客项目数      
  update a set oldcostcount=ISNULL((      
   select count(distinct billid) from #allstaff_work_detail b      
    where a.person_inid=b.person_inid and b.action_id in (7,10,13) and isnull(ccount,0)>0  ),0)      
  from #allstaff_work_analysis a      
        
  --烫染项目数      
  update a set trcostcount=ISNULL((      
   select count(distinct billid) from #allstaff_work_detail b,projectnameinfo c      
    where a.person_inid=b.person_inid  and isnull(ccount,0)>0 and b.code=c.prjno and c.prjreporttype in ('02','03') ),0)      
  from #allstaff_work_analysis a      
       
        
        
       
        
  select  person_inid,staffno,staffname,staffposition,oldcostcount,newcostcount,trcostcount,cashbigcost,cashsmallcost,cashhulicost,      
    cardbigcost,cardsmallcost,cardhulicost,cardprocost,cardsgcost,cardpointcost,projectdycost,cashdycost,tmcardcost,      
    salegoodsamt,salecardsamt,prochangeamt,saletmkamt,qhpayinner,qhpayouter,jdpayinner,smpayinner,staffyeji,staffcashyeji      
  from #allstaff_work_analysis order by staffno      
        
  drop table #empinfobydate      
  drop table #allstaff_work_detail       
  drop table #allstaff_work_analysis       
        
  return       
 end      
       
 insert #allstaff_work_analysis(person_inid,staffyeji)      
 select person_inid,SUM(isnull(staffticheng,0)) from #allstaff_work_detail where isnull(person_inid,'')<>'' group by person_inid       
       
 update a set a.staffno=b.staffno,a.staffname=b.staffname,a.staffposition=b.position      
 from #allstaff_work_analysis a,staffinfo b      
 where a.person_inid=b.Manageno      
       
 --现金大项      
 update a set cashbigcost=ISNULL((      
  select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b,projectnameinfo c      
   where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype<>6 and c.prjpricetype=1 and b.paycode in ('1','2','6','0','14','15') ),0)      
 from #allstaff_work_analysis a      
       
 --现金小项      
 update a set cashsmallcost=ISNULL((      
  select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b,projectnameinfo c      
   where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype<>6 and c.prjpricetype=2 and b.paycode in ('1','2','6','0','14','15') ),0)      
 from #allstaff_work_analysis a      
       
 --现金护理      
 update a set cashhulicost=ISNULL((      
  select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b,projectnameinfo c      
   where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype=6  and b.paycode in ('1','2','6','0','14','15') ),0)      
 from #allstaff_work_analysis a      
       
 --销卡大项      
 update a set cardbigcost=ISNULL((      
  select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b,projectnameinfo c      
   where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype<>6 and c.prjpricetype=1 and b.paycode in ('4','17')  ),0)      
 from #allstaff_work_analysis a      
       
 --销卡小项      
 update a set cardsmallcost=ISNULL((      
  select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b,projectnameinfo c      
   where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype<>6 and c.prjpricetype=2 and b.paycode  in ('4','17') ),0)      
 from #allstaff_work_analysis a      
       
 --销卡护理      
 update a set cardhulicost=ISNULL((      
  select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b,projectnameinfo c      
   where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.code=c.prjno and  c.prjtype=6  and b.paycode  in ('4','17')  ),0)      
 from #allstaff_work_analysis a      
       
 --疗程消费      
 update a set cardprocost=ISNULL((      
  select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b      
   where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.paycode  ='9' ),0)      
 from #allstaff_work_analysis a      
       
 --收购卡消费      
 update a set cardsgcost=ISNULL((      
  select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b      
   where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.paycode  ='A' ),0)      
 from #allstaff_work_analysis a      
       
 --积分消费      
 update a set cardpointcost=ISNULL((      
  select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b      
   where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.paycode  ='7' ),0)      
 from #allstaff_work_analysis a      
       
 --项目抵用券消费      
 update a set projectdycost=ISNULL((      
  select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b      
   where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.paycode  ='11' ),0)      
 from #allstaff_work_analysis a      
       
 --现金抵用券消费      
 update a set cashdycost=ISNULL((      
  select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b      
   where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.paycode  ='12' ),0)      
 from #allstaff_work_analysis a      
       
 --条码卡消费      
 update a set tmcardcost=ISNULL((      
  select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b      
   where a.person_inid=b.person_inid and b.action_id >=7 and b.action_id<=15 and b.paycode  ='13' ),0)      
 from #allstaff_work_analysis a      
       
 --产品销售      
 update a set salegoodsamt=ISNULL((      
  select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b      
   where a.person_inid=b.person_inid and b.action_id >=16 and b.action_id<=24  ),0)      
 from #allstaff_work_analysis a      
       
 --会员卡销售      
 update a set salecardsamt=ISNULL((      
  select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b      
   where a.person_inid=b.person_inid and b.action_id >=1 and b.action_id<=3  ),0)      
 from #allstaff_work_analysis a      
       
 --疗程兑换      
 update a set prochangeamt=ISNULL((      
  select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b      
   where a.person_inid=b.person_inid and b.action_id =4  ),0)      
 from #allstaff_work_analysis a      
       
 --条码卡销售      
 update a set saletmkamt=ISNULL((      
  select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b      
   where a.person_inid=b.person_inid and b.action_id =5  ),0)      
 from #allstaff_work_analysis a      
       
      
 --全韩店内支付      
 update a set qhpayinner=ISNULL((      
  select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b      
   where a.person_inid=b.person_inid and b.action_id =28  ),0)      
 from #allstaff_work_analysis a      
       
 --全韩对方支付      
 update a set qhpayouter=ISNULL((      
  select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b      
   where a.person_inid=b.person_inid and b.action_id =27 ),0)      
 from #allstaff_work_analysis a      
       
 --暨大店内支付      
 update a set jdpayinner=ISNULL((      
  select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b      
   where a.person_inid=b.person_inid and b.action_id =28  ),0)      
 from #allstaff_work_analysis a      
       
 --私密店内支付      
 update a set smpayinner=ISNULL((      
  select sum(ISNULL(staffticheng,0)) from #allstaff_work_detail b      
   where a.person_inid=b.person_inid and b.action_id =29  ),0)      
 from #allstaff_work_analysis a      
       
 --新客项目数      
 update a set newcostcount =ISNULL((      
  select count(distinct billid) from #allstaff_work_detail b      
   where a.person_inid=b.person_inid and b.action_id in (8,11,14) and isnull(ccount,0)>0  ),0)      
 from #allstaff_work_analysis a      
       
 --老客项目数      
 update a set oldcostcount=ISNULL((      
  select count(distinct billid) from #allstaff_work_detail b      
   where a.person_inid=b.person_inid and b.action_id in (7,10,13) and isnull(ccount,0)>0  ),0)      
 from #allstaff_work_analysis a      
       
 --烫染项目数      
 update a set trcostcount=ISNULL((      
  select count(distinct billid) from #allstaff_work_detail b,projectnameinfo c      
   where a.person_inid=b.person_inid  and isnull(ccount,0)>0 and b.code=c.prjno and c.prjreporttype in ('02','03') ),0)      
 from #allstaff_work_analysis a      
       
 --select seqno,person_inid,action_id,srvdate,code,name,payway,billamt,ccount,cost,staffticheng,staffyeji,prj_type,cls_flag,billid,paycode,compid,cardid,cardtype       
 --from #allstaff_work_detail order by action_id,srvdate      
 if(@handtype=1)      
 begin      
  select  person_inid,staffno,staffname,staffposition,oldcostcount,newcostcount,trcostcount,cashbigcost,cashsmallcost,cashhulicost,      
    cardbigcost,cardsmallcost,cardhulicost,cardprocost,cardsgcost,cardpointcost,projectdycost,cashdycost,tmcardcost,      
    salegoodsamt,salecardsamt,prochangeamt,saletmkamt,qhpayinner,qhpayouter,jdpayinner,smpayinner,staffyeji       
  from #allstaff_work_analysis order by staffno      
 end      
 else      
 begin      
  --   delete allstaff_work_yeji_daybyday  where compid=@compid and yeji_date =@fromdate      
  --insert allstaff_work_yeji_daybyday(yeji_date,compid,person_inid,staffno,staffname,staffposition,      
  --oldcostcount,newcostcount,trcostcount,cashbigcost,cashsmallcost,cashhulicost,cardbigcost,cardsmallcost,cardhulicost,cardprocost,      
  --cardsgcost,cardpointcost,projectdycost,cashdycost,tmcardcost,salegoodsamt,salecardsamt,prochangeamt,saletmkamt,      
  --qhpayinner,qhpayouter,jdpayinner,smpayinner,staffyeji)      
  --select @fromdate,@compid,person_inid,staffno,staffname,staffposition,      
  --oldcostcount,newcostcount,trcostcount,cashbigcost,cashsmallcost,cashhulicost,cardbigcost,cardsmallcost,cardhulicost,cardprocost,      
  --cardsgcost,cardpointcost,projectdycost,cashdycost,tmcardcost,salegoodsamt,salecardsamt,prochangeamt,saletmkamt,      
  --qhpayinner,qhpayouter,jdpayinner,smpayinner,staffyeji from #allstaff_work_analysis      
        
      
  delete staff_work_salary where compid=@compid and salary_date =@fromdate      
  insert staff_work_salary(compid,person_inid,salary_date,oldcostcount,newcostcount,trcostcount,cashbigcost,cashsmallcost,cashhulicost,      
    cardbigcost,cardsmallcost,cardhulicost,cardprocost,cardsgcost,cardpointcost,projectdycost,cashdycost,tmcardcost,      
    salegoodsamt,salecardsamt,prochangeamt,saletmkamt,qhpayinner,qhpayouter,jdpayinner,smpayinner,staffyeji)      
  select @compid,person_inid,@fromdate,sum(isnull(oldcostcount,0)),sum(isnull(newcostcount,0)),sum(isnull(trcostcount,0)),      
    sum(isnull(cashbigcost,0)),sum(isnull(cashsmallcost,0)),sum(isnull(cashhulicost,0)),      
    sum(isnull(cardbigcost,0)),sum(isnull(cardsmallcost,0)),sum(isnull(cardhulicost,0)),      
    sum(isnull(cardprocost,0)),sum(isnull(cardsgcost,0)),sum(isnull(cardpointcost,0)),      
    sum(isnull(projectdycost,0)),sum(isnull(cashdycost,0)),sum(isnull(tmcardcost,0)),      
    sum(isnull(salegoodsamt,0)),sum(isnull(salecardsamt,0)),sum(isnull(prochangeamt,0)),      
    sum(isnull(saletmkamt,0)),sum(isnull(qhpayinner,0)),sum(isnull(qhpayouter,0)),      
    sum(isnull(jdpayinner,0)),sum(isnull(smpayinner,0)),sum(isnull(staffyeji,0))      
  from #allstaff_work_analysis      
  group by person_inid      
        
  update a      
  set staffcashyeji=isnull((select  sum(isnull(staffyeji,0)) from #allstaff_work_detail b where ISNULL(action_id,-1)<>4 and paycode in ('1','2','6','0','14','15') and a.person_inid=b.person_inid  ),0)                                  
  from staff_work_salary a where salary_date=@fromdate      
        
 end      
       
    drop table #empinfobydate      
    drop table #allstaff_work_detail       
    drop table #allstaff_work_analysis       
end       