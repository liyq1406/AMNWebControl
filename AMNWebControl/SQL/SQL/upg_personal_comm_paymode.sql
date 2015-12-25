

alter procedure upg_personal_comm_paymode(                                        
 @compid    varchar(10), -- 公司别                                        
 @fromdate   varchar(10), -- 开始日期                                        
 @todate    varchar(10), -- 截至日期        
 @fromempinno  varchar(20), -- 查询开始人员内部编号                 
 @toempinno   varchar(20)  -- 查询截至人员内部编号         
)                                                                           
as                                        
begin                                      
 create table #work_detail        
 (        
    seqno     int identity  NOT NULL,               
    person_inid   varchar(20)   NULL, --员工内部编号        
    action_id    int     NULL, --单据类型        
    srvdate    varchar(10)   NULL, --日期        
    code     varchar(20)   NULL, --项目代码,或是卡号,产品码        
    name     varchar(40)   NULL, --名称        
    payway    varchar(20)   NULL, --支付方式        
    billamt    float    NULL, --营业金额        
    ccount    float    NULL, --数量        
    cost     float    NULL, --成本        
    staffticheng   float    NULL, --提成        
    staffyeji    float    NULL, --虚业绩       
    staffshareyeji  float    NULL, --分享卡金  
    prj_type    varchar(20)   NULL, --项目类别        
    cls_flag    int     NULL, --1:项目 2:产品 3:卡        
    billid    varchar(20)   NULL, --单号        
    paycode    varchar(20)   NULL, --支付代码        
    compid    varchar(10)   NULL, --公司别        
    cardid    varchar(20)   NULL, --会员卡号        
    cardtype    varchar(20)   NULL, --会员卡类型        
    postation    varchar(10)   NULL, --职位   
    arrivaldmonth   int     NULL, --到职月份  
    csitemstate   int     NULL,  --是否达标 0 无 1 达标 2 未达标   
    billinsertype   int     NULL, --充值主办方 1 美容 2 美发 3合作   
    costpricetype   int     NULL,  --是否为体验价消费 0 不是 1 是   
 )             
 exec upg_prepare_yeji_detail @compid,@fromdate, @todate,@fromempinno, @toempinno          
    declare @isNewComdMode int --是否是新模式  
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
   update #work_detail set staffyeji=ISNULL(staffshareyeji,0) where action_id  in ('1','2','3')  
  end  
    end  
    declare @SP105 varchar(2)  
    select @SP105=paramvalue from sysparaminfo where compid=@compid and paramid='SP105'  
 --默认入职日期  
    update   #work_detail set arrivaldmonth=datediff(month,arrivaldate,@todate)  
    from  #work_detail,staffinfo   
    where person_inid=manageno  
   --调动升级   
   UPDATE #work_detail set arrivaldmonth=(select MAX(isnull(effectivedate,'')) from staffhistory c where person_inid=c.manageno and c.changetype in (0,1) and c.oldpostion not in  ('003','006','007','00701','00702')  and isnull(newpostion,'')='003')  
   from #work_detail a,staffhistory b  
   where a.person_inid=b.manageno and  b.changetype in (0,1) and b.oldpostion not in  ('003','006','007','00701','00702')  and isnull(newpostion,'')='003'  
   --重回公司的不算入职日期  
   UPDATE a   
   set arrivaldmonth='20130101'  
   from #work_detail a,staffhistory b   
   where person_inid=manageno and changetype =5 and isnull(newpostion,'') in ('003','006','007','00701','00702')  and b.oldpostion  in  ('003','006','007','00701','00702')   
 update #work_detail set payway=parentcodevalue         
 from #work_detail,commoninfo        
 where infotype='ZFFS' and parentcodekey=paycode        
 update #work_detail set cardtype='' where cardtype='ZK' and paycode not in ('4','17')        
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
 declare @arrivaldmonth int  --在职月份  
 declare @newcosttc  float --新客提成数  
 declare @oldcosttc  float --老客提成数   
 declare @saleprice  float --项目标准价  
 declare @csitemstate int --是否达标 0 无 1 达标 2 未达标   
 declare @billinsertype int  --充值主办方 1 美容 2 美发    0 不区分   
 declare @costpricetype  int   --是否为体验价消费 0 不是 1 是  
 declare @prjsaletype int  --是否是疗程   1是  2否          
 -----------------------------提成化参数------------------------------------------------  
 --产品提成  
 declare @cp_costrate_mr    float       -- 美容产品成本系数  
 declare @cp_salaryrate_mr   float       -- 美容产品提成系数  
 declare @cp_costrate_mf    float       -- 美发产品成本系数  
 declare @cp_salaryrate_mf   float       -- 美发产品提成系数  
 declare @cp_costrate_mj    float       -- 美甲产品成本系数  
 declare @cp_salaryrate_mj   float       -- 美甲产品提成系数  
 declare @cp_costrate_ks    float       -- 卡诗产品成本系数  
 declare @cp_salaryrate_ks   float       -- 卡诗产品提成系数  
 --卡金提成  
 declare @kj_salaryrate_trsa   float     --一,二,三级烫染师卡金提成系数  
 declare @kj_salaryrate_trsb   float     --四级烫染师,烫染督导卡金提成系数  
 declare @kj_salaryrate_mrc   float     --美容师C卡金提成系数  
 declare @kj_salaryrate_mr   float     --其他美容师提成系数  
 declare @kj_salaryrate_mf   float     --美发人员提成系数  
 --疗程兑换提成  
 declare @dh_costrate_trsa   float     --一,二,三级烫染师疗程兑换成本系数  
 declare @dh_costrate_trsb   float     --四级烫染师,烫染督导疗程兑换成本系数  
 declare @dh_costrate_mf    float     --美发人员疗程兑换成本系数  
 declare @dh_salaryrate_trsa   float     --一,二,三级烫染师疗程兑换提成系数  
 declare @dh_salaryrate_trsb   float     --四级烫染师,烫染督导疗程兑换提成系数  
 declare @dh_salaryrate_mf   float     --美发人员疗程兑换提成系数  
 --合作项目提成  
 declare @hz_salaryrate_qh   float     --全韩私密合作提成比率  
 declare @hz_salaryrate_jd   float     --暨大合作提成比率  
 declare @jfdh_salary_reward   float     --建发疗程兑换提成奖励  
 declare @jjdh_yeji_reward   float     --肩颈疗程兑换卡金奖励  
 declare @jjfw_salary_reward   float     --肩颈服务提成奖励  
 declare @jsr_salary_reward   float     --介绍人提成奖励  
 declare @tjsr_salary_cost   float     --被介绍人扣除提成  
 --项目消费特殊提成方式  
 declare @mf_salaryrate_fiveup  float     --美发人员5个月上的提成比率(限美发类项目)  
 declare @mf_salaryrate_fivedown  float     --美发人员5个月下的提成比率(限美发类项目)  
 declare @olc_cost_yeji_fixed  float     --老疗程卡固定业绩  
 declare @olc_cost_salary_fixed  float     --老疗程卡固定提成  
 declare @nlc_costrate_tr   float     --烫染师疗程成本  
 declare @yjk_costrate_mrmf   float     --原价卡美容美发成本  
 declare @yjk_salaryrate_mrmf  float     --原价卡美容美发提成比率  
 declare @yjk_costrate_tr   float     --原价卡烫染师成本  
 declare @salaryrate_tra    float     --一,二,三级烫染师提成比率  
 declare @salaryrate_trb    float     --四级烫染师,烫染督导提成比率  
 declare @xjc_costrate_sjs   float     --设计师洗剪吹成本  
 declare @xjc_costrate_sx   float     --首席洗剪吹成本  
 declare @xjc_costrate_zj   float     --总监洗剪吹成本  
 declare @xjc_salary_fixed_db  float     --洗剪吹中工达标 10  
 declare @xjc_salary_fixed_ndb  float     --洗剪吹中工不达标 5  
 declare @xjc_salary_fixed_nhg  float     --洗剪吹中工不合格 3  
 declare @mr_salary_fixed_tmk  float     --美容新项目条码卡固定提成  
 declare @mr_salary_fixed_ty   float     --美容新项目体验固定提成  
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
 select seqno,person_inid,action_id,case when isnull(action_id,'') in ('1','2','3') then staffshareyeji else staffyeji end,code,srvdate ,paycode,isnull(cardtype,''),isnull(ccount,0),isnull(arrivaldmonth,0),ISNULL(csitemstate,0),ISNULL(billinsertype,0) ,isnull(costpricetype,0)                                              
 from #work_detail                                                                
 declare @empTicheng float             
    declare @trkcqualified int --烫染课程是否合格                                                             
 open cur_yeji_ticheng                                                                
 fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate,@paycode ,@cardtype,@quan,@arrivaldmonth,@csitemstate,@billinsertype ,@costpricetype                                                                
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
   update #work_detail set staffticheng=0 where seqno=@tmpSeqId                                           
   fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan ,@arrivaldmonth,@csitemstate,@billinsertype,@costpricetype                                       
   continue                                           
  end        
  --一二级烫染师  烫染项目没有业绩
  if exists(select 1 from projectinfo where prjreporttype in('02','03'))
  begin
	if((@empPostion='00901' or @empPostion='00902') and @tmpDate>='20141001')
	begin
		update #work_detail set staffticheng=0 where seqno=@tmpSeqId
		fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan ,@arrivaldmonth,@csitemstate,@billinsertype,@costpricetype                                       
		continue
	end
  end
  
  -- 16，17，18，19，20，21，22，23，24-售产品          
  if(isnull(@tmpItem,'') in ('16','17','18','19','20','21','22','23','24'))                                                        
  begin              
   select   @GOODS_TYPE=isnull(goodstype,1)  from  goodsnameinfo with(nolock) where goodsno=@tmpPrjId                                              
   if(isnull(@GOODS_TYPE,'300'))='400'----美容产品(扣除成本20%提成10%)              
   begin                                              
    update #work_detail set staffyeji=isnull(staffyeji,0)*isnull(@cp_costrate_mr,0) where seqno=@tmpSeqId                                               
    set @empTicheng = isnull(@tmpYeji,0)*isnull(@cp_costrate_mr,0)*ISNULL(@cp_salaryrate_mr,0)                                         
   end                                              
   else if(isnull(@GOODS_TYPE,'300'))='300'----美发产品（提成5%）                                               
   begin                                              
    update #work_detail set staffyeji=isnull(staffyeji,0)*isnull(@cp_costrate_mf,0) where seqno=@tmpSeqId                                               
    set @empTicheng = isnull(@tmpYeji,0)*isnull(@cp_costrate_mf,0)*ISNULL(@cp_salaryrate_mf,0)                                      
   end                      
   else if(isnull(@GOODS_TYPE,'300'))='500'----美甲产品（产品不扣成本 4，6分）                                             
   begin                   
    update #work_detail set staffyeji=isnull(staffyeji,0)*isnull(@cp_costrate_mj,0) where seqno=@tmpSeqId                                 
    set @empTicheng = isnull(@tmpYeji,0)*isnull(@cp_costrate_mj,0)*ISNULL(@cp_salaryrate_mj,0)                
   end                                          
   else if(isnull(@GOODS_TYPE,'300'))='700'----卡诗产品（无业绩无提成）                                                   
   begin                                 
    update #work_detail set staffyeji=isnull(staffyeji,0)*isnull(@cp_costrate_ks,0) where seqno=@tmpSeqId                                               
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
    else if (isnull(@empPostion,'') in ('004' ,'00401','00103') ) -- 其他类美容师拿1%的提成  
     set @empTicheng = @tmpYeji*ISNULL(@kj_salaryrate_mr,0)       
    else if (isnull(@empPostion,'') in ('003','006','007','00701','00702') and ISNULL(@tmpDate,'')>='20140517') -- 美发部拿4%  
     set @empTicheng = @tmpYeji*ISNULL(@kj_salaryrate_mf,0)  
    if(ISNULL(@billinsertype,0)=0)  
    begin  
     update #work_detail set staffyeji=@tmpYeji where seqno=@tmpSeqId   
    end  
    if(ISNULL(@billinsertype,0)=1 and isnull(@empPostion,'') in ('003','006','007','00701','00702')) --美容卡金  
    begin  
     update #work_detail set staffyeji=0 where seqno=@tmpSeqId                  
    end   
    if(ISNULL(@billinsertype,0)=2 and isnull(@empPostion,'') in ('003','006','007','00701','00702')) --美发卡金  
    begin  
     update #work_detail set staffyeji=@tmpYeji where seqno=@tmpSeqId                  
    end   
    if(ISNULL(@billinsertype,0)=2 and isnull(@empPostion,'') in ('004','00401','00402','00103')) --美发卡金  
    begin  
     update #work_detail set staffyeji=0 where seqno=@tmpSeqId                  
    end   
    if(ISNULL(@billinsertype,0)=1 and isnull(@empPostion,'') in ('004','00401','00402','00103')) --美容卡金  
    begin  
     update #work_detail set staffyeji=@tmpYeji where seqno=@tmpSeqId                  
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
    update #work_detail set staffyeji=isnull(@tmpYeji,0)*ISNULL(@dh_costrate_trsa,0) where seqno=@tmpSeqId                              
    set @empTicheng = @tmpYeji*ISNULL(@dh_costrate_trsa,0)*ISNULL(@dh_salaryrate_trsa,0)      
   end                                         
   else if ( isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008' )  --三级和四级烫染师 2.5%    
   begin      
    update #work_detail set staffyeji=isnull(@tmpYeji,0)*ISNULL(@dh_costrate_trsb,0) where seqno=@tmpSeqId                                      
    set @empTicheng = @tmpYeji*ISNULL(@dh_costrate_trsb,0)*ISNULL(@dh_salaryrate_trsb,0)   
   end                                            
   --首席，总监，设计师记业绩20%,提成20%                                         
   else if ( isnull(@empPostion,'')='003' or isnull(@empPostion,'')='006'    
    or isnull(@empPostion,'')='007' or isnull(@empPostion,'')='00701' or isnull(@empPostion,'')='00702'    
    or isnull(@empPostion,'')='00102')                                          
   begin                                        
    update #work_detail set staffyeji=isnull(@tmpYeji,0)*ISNULL(@dh_costrate_mf,0) where seqno=@tmpSeqId                                          
    set @empTicheng = isnull(@tmpYeji,0)*ISNULL(@dh_costrate_mf,0)*ISNULL(@dh_salaryrate_mf,0)                       
   end                                        
  end       
  --疗程兑换 (建发奖励)     
  else if(isnull(@tmpItem,'')='25')                                       
  begin                                 
   if(isnull(@empPostion,'') in ('00901','00902','00903','00904','008') )  --一级和二级烫染师2%                                           
   begin  
    update #work_detail set staffyeji=0 where seqno=@tmpSeqId   
    set @empTicheng = @tmpYeji*ISNULL(@jfdh_salary_reward,0)    
   end    
   else if(isnull(@empPostion,'') in ('003','006','007','00701','00702') )  --美发师                                           
   begin  
    update #work_detail set staffyeji=0 where seqno=@tmpSeqId   
    set @empTicheng = @tmpYeji*ISNULL(@jfdh_salary_reward,0)     
   end                                             
   else     
   begin  
    set @empTicheng =0      
    update #work_detail set staffyeji=0 where seqno=@tmpSeqId   
   end          
  end  
  --疗程兑换 (肩颈奖励)     
  else if(isnull(@tmpItem,'')='35')                                       
  begin                                 
   if(isnull(@empPostion,'') in ('00103','004','00401') )  --美容师                                           
   begin  
    update #work_detail set staffyeji=0 where seqno=@tmpSeqId   
    set @empTicheng = @tmpYeji*ISNULL(@jjfw_salary_reward,0)    
   end                                             
   else     
   begin  
    set @empTicheng =0      
    update #work_detail set staffyeji=0 where seqno=@tmpSeqId   
   end          
  end         
  --32 项目介绍人  
  else if(isnull(@tmpItem,'')='32')                                       
  begin        
   update #work_detail set staffyeji=0 where seqno=@tmpSeqId   
   if(ISNULL(@SP105,'0')='1')  
    set @empTicheng = ISNULL(@jsr_salary_reward,0)         
   else   
    set @empTicheng = 0           
  end    
  --36 项目介绍人  
  else if(isnull(@tmpItem,'')='36')                                       
  begin        
   update #work_detail set paycode='1',staffyeji=ISNULL(@jjdh_yeji_reward,0)   where seqno=@tmpSeqId   
   set @empTicheng = 0      
  end   
  --37发型师手工费  
  else if(isnull(@tmpItem,'')='37')                                       
  begin        
   update #work_detail set staffyeji=25*ISNULL(@quan,1)   where seqno=@tmpSeqId   
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
     update #work_detail set staffyeji=0,staffticheng=0 where seqno=@tmpSeqId                                          
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
     @Wage_Rates=isnull(ltcrate,1),@PROJECT_TYPE=prjtype,@newcosttc=newcosttc,@oldcosttc=oldcosttc,@prjsaletype=isnull(prjsaletype,0)       
     from  projectinfo a,sysparaminfo b       
     where b.paramid='SP059' and b.compid=@compid and b.paramvalue=a.prjmodeId and prjno=@tmpPrjId                                       
    --美容师C大项无提成  
    if(isnull(@empPostion,'') ='00402'  and ISNULL(@proflag,2)=1)                                        
    begin                                        
     update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                          
     set @empTicheng = 0       
     fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan ,@arrivaldmonth,@csitemstate,@billinsertype,@costpricetype                                       
     continue                                      
    end     
    if(ISNULL(@newcosttc,0)>0 or ISNULL(@oldcosttc,0)>0)  
    begin  
      if(  isnull(@tmpItem,'')='7')  
       update #work_detail set staffticheng=@oldcosttc*@fuflag  where seqno=@tmpSeqId   
      else   if(  isnull(@tmpItem,'')='8')  
       update #work_detail set staffticheng=@newcosttc*@fuflag  where seqno=@tmpSeqId   
      else  
       update #work_detail set staffticheng=0 where seqno=@tmpSeqId   
      --非疗程的疗程项目   
      if(ISNULL(@prjsaletype,0)<>1)  
        update #work_detail set staffticheng=60*@fuflag  where seqno=@tmpSeqId                                      
      fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan ,@arrivaldmonth,@csitemstate,@billinsertype,@costpricetype                                       
      continue    
    end  
    update #work_detail set staffyeji=(isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*isnull(@Performance_Ratio,0) where seqno=@tmpSeqId                                               
    set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*@Performance_Ratio*@Wage_Rates                                              
    --烫染师 非疗程卡 记80%业绩 记5%提成 纯疗程卡记120块的业绩，6块钱的提成                                        
    if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903'  or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008' )                                            
    begin              
     if( isnull(@cardtype,'') ='MR' or isnull(@cardtype,'')='MF')                                            
     begin                                        
      update #work_detail set staffyeji=ISNULL(@olc_cost_yeji_fixed,0)*@fuflag where seqno=@tmpSeqId                                               
      set @empTicheng=ISNULL(@olc_cost_salary_fixed,0)*@fuflag                                        
     end                                         
     else                                        
     begin                                             
      update #work_detail set staffyeji=isnull(@tmpYeji,0)*ISNULL(@nlc_costrate_tr,0) where seqno=@tmpSeqId                                 
      set @empTicheng = isnull(@tmpYeji,0)*ISNULL(@nlc_costrate_tr,0)*ISNULL(@salaryrate_tra,0)                                        
      if(isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')--四级烫染师                                        
       set @empTicheng = isnull(@tmpYeji,0)*ISNULL(@nlc_costrate_tr,0)*ISNULL(@salaryrate_trb,0)                                        
     end                                           
    end      
    if(isnull(@empPostion,'')in('004','00401','00402','00103') and ISNULL(@PROJECT_TYPE,'') in ('3','6') and ISNULL(@proflag,2)=1)                                        
    begin                                        
     update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                          
     set @empTicheng = 0                                         
    end                                        
    if(isnull(@empPostion,'') in ('003','006','007','00701','00702','00901','00902','00903','00904','008')  and ISNULL(@PROJECT_TYPE,'') ='4' and ISNULL(@proflag,2)=1)                                        
    begin                                        
     update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                          
     set @empTicheng = 0                                         
    end                                         
    --美发的输入在中工和小工位置的(美发师,首席,总监)无提成                                        
    if((@PROJECT_TYPE='3' or @PROJECT_TYPE='6') and isnull(@empPostion,'') in ('003','006','007','00701','00702') and isnull(@tmpItem,'') not in ('7','8','9'))                                        
    begin                                        
     set @empTicheng = 0                                         
    end                                        
    if(@cardtype ='MFOLD' )                                         
    begin                                        
     update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                          
     set @empTicheng =0                                        
    end                                                           
   end         
   else if( (@cardtype ='ZK' and isnull(@paycode,'') in ('4','17')) or isnull(@paycode,'')='$'  or isnull(@paycode,'')='A' or isnull(@paycode,'')='7' or isnull(@paycode,'')='11'  or isnull(@paycode,'')='12'  )                       
   begin                                     
    select  @proflag=isnull(prjpricetype,2),@newcosttc=newcosttc,@oldcosttc=oldcosttc        
    from  projectinfo a,sysparaminfo b       
    where b.paramid='SP059' and b.compid=@compid and b.paramvalue=a.prjmodeId and prjno=@tmpPrjId                               
    --美容师C大项无提成  
    if(isnull(@empPostion,'') ='00402'  and ISNULL(@proflag,2)=1)                                        
    begin                                        
     update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                          
     set @empTicheng = 0       
     fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan ,@arrivaldmonth,@csitemstate,@billinsertype,@costpricetype                                       
     continue                                      
    end    
    --if(isnull(@tmpYeji,0)>0)                                      
    -- set @fuflag=1                                      
    --else                                      
    -- set @fuflag=-1   
    if((ISNULL(@newcosttc,0)>0 or ISNULL(@oldcosttc,0)>0) and isnull(@paycode,'')<>'11')  
    begin  
     if(  isnull(@tmpItem,'')='7' and ISNULL(@costpricetype,0)=0 )  --指定客非体验项目  
     begin  
       update #work_detail set staffticheng=@oldcosttc*@fuflag  where seqno=@tmpSeqId   
       set @empTicheng=@oldcosttc*@fuflag  
     end  
     else   if(  isnull(@tmpItem,'')='7' and ISNULL(@costpricetype,0)=1  ) --指定客体验项目  
     begin  
       if(ISNULL(@oldcosttc,0)>ISNULL(@mr_salary_fixed_ty,0))  
       begin  
        update #work_detail set staffticheng=ISNULL(@mr_salary_fixed_ty,0)*@fuflag  where seqno=@tmpSeqId   
        set @empTicheng=ISNULL(@mr_salary_fixed_ty,0)*@fuflag  
       end  
       else  
       begin  
        update #work_detail set staffticheng=@oldcosttc*@fuflag  where seqno=@tmpSeqId   
        set @empTicheng=@oldcosttc*@fuflag  
       end  
     end  
     else   if(  isnull(@tmpItem,'')='8' and ISNULL(@costpricetype,0)=0) --新客非体验项目  
     begin  
       update #work_detail set staffticheng=@newcosttc*@fuflag  where seqno=@tmpSeqId  
       set @empTicheng=@newcosttc*@fuflag   
     end  
     else   if(  isnull(@tmpItem,'')='8' and ISNULL(@costpricetype,0)=1) --新客体验项目  
     begin  
       if(ISNULL(@newcosttc,0)>ISNULL(@mr_salary_fixed_ty,0))  
       begin  
        update #work_detail set staffticheng=ISNULL(@mr_salary_fixed_ty,0)*@fuflag  where seqno=@tmpSeqId   
        set @empTicheng=ISNULL(@mr_salary_fixed_ty,0)*@fuflag   
       end  
       else  
       begin  
        update #work_detail set staffticheng=@newcosttc*@fuflag  where seqno=@tmpSeqId  
        set @empTicheng=@newcosttc*@fuflag   
       end  
     end  
     else   if(  isnull(@tmpItem,'')='9' and ISNULL(@costpricetype,0)=1) --新客推荐体验  
     begin  
       if(ISNULL(@newcosttc,0)>ISNULL(@mr_salary_fixed_ty,0))  
       begin  
        update #work_detail set staffticheng=(@mr_salary_fixed_ty-ISNULL(@tjsr_salary_cost,0))*@fuflag  where seqno=@tmpSeqId   
        set @empTicheng=(@mr_salary_fixed_ty-ISNULL(@tjsr_salary_cost,0))*@fuflag  
       end  
       else  
       begin  
        update #work_detail set staffticheng=(@newcosttc-ISNULL(@tjsr_salary_cost,0))*@fuflag  where seqno=@tmpSeqId   
        set @empTicheng=(@newcosttc-ISNULL(@tjsr_salary_cost,0))*@fuflag  
       end  
     end  
     else   if(  isnull(@tmpItem,'')='9' and ISNULL(@costpricetype,0)=0) --新客推荐非体验  
     begin  
       update #work_detail set staffticheng=(@newcosttc-ISNULL(@tjsr_salary_cost,0))*@fuflag  where seqno=@tmpSeqId   
       set @empTicheng=(@newcosttc-ISNULL(@tjsr_salary_cost,0))*@fuflag  
     end  
     else  
       update #work_detail set staffticheng=0 where seqno=@tmpSeqId   
     fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan,@arrivaldmonth ,@csitemstate,@billinsertype,@costpricetype                                       
     continue    
    end  
    if(@tmpPrjId in ('321','322','323','324','325','326','327','328','329','330','331','332') and isnull(@tmpItem,'')  in ('10','11','12') and  isnull(@empPostion,'') not in ('003','006','007','00701','00702')  )  
    begin  
      if(  ISNULL(@csitemstate,1)=1)  
       update #work_detail set staffticheng=ISNULL(@xjc_salary_fixed_db,0)*@fuflag  where seqno=@tmpSeqId   
      else   if( ISNULL(@csitemstate,1)=2)  
       update #work_detail set staffticheng=ISNULL(@xjc_salary_fixed_ndb,0)*@fuflag  where seqno=@tmpSeqId   
      else   if( ISNULL(@csitemstate,1)=3)  
       update #work_detail set staffticheng=ISNULL(@xjc_salary_fixed_nhg,0)*@fuflag  where seqno=@tmpSeqId   
      else  
       update #work_detail set staffticheng=0 where seqno=@tmpSeqId                                        
      fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan,@arrivaldmonth ,@csitemstate,@billinsertype,@costpricetype                                       
      continue   
    end    
    if(@tmpPrjId in ('321','322','323','324','325','326','327','328','329','330','331','332') and isnull(@tmpItem,'')  in ('13','14','15') )  
    begin  
      update #work_detail set staffticheng=0 where seqno=@tmpSeqId                                        
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
      update #work_detail set staffticheng=25*@fuflag where seqno=@tmpSeqId                                        
      fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan,@arrivaldmonth ,@csitemstate,@billinsertype,@costpricetype                                       
      continue  
     end    
     --新登记抵用券  
     if(ISNULL(@dycardnocreatetype,0)<>0)  
     begin  
      update #work_detail set staffticheng=ISNULL(@mr_salary_fixed_ty,0)*@fuflag where seqno=@tmpSeqId                                        
      fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan,@arrivaldmonth ,@csitemstate,@billinsertype,@costpricetype                                       
      continue  
     end                                
     if(ISNULL(@fuflag,0)<0)                              
      set  @tmpYeji=ISNULL(@tmpYeji,0)*(-1)                                     
     update #work_detail set staffyeji=@tmpYeji,billamt=@tmpYeji where seqno=@tmpSeqId                                          
    end                                         
    --烫染师 记业绩24% 提成5%                                        
    if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903' or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                                            
    begin                                        
     update #work_detail set staffyeji=isnull(@tmpYeji,0)*ISNULL(@yjk_costrate_tr,0) where seqno=@tmpSeqId                                          
     set @empTicheng = isnull(@tmpYeji,0)*ISNULL(@yjk_costrate_tr,0)*ISNULL(@salaryrate_tra,0)                                         
     if( isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                  
      set @empTicheng = isnull(@tmpYeji,0)*ISNULL(@yjk_costrate_tr,0)*ISNULL(@salaryrate_trb,0)                                          
    end                                          
    --首席，总监，设计师记业绩24%,提成22% 美容师记业绩24%,提成22%                                         
    else                                         
    begin                                        
     update #work_detail set staffyeji=isnull(@tmpYeji,0)*ISNULL(@yjk_costrate_mrmf,0) where seqno=@tmpSeqId                                          
     set @empTicheng = isnull(@tmpYeji,0)*ISNULL(@yjk_costrate_mrmf,0)*ISNULL(@yjk_salaryrate_mrmf,0)      
     if(  isnull(@tmpItem,'')='9' and ISNULL(@empTicheng,0)>ISNULL(@tjsr_salary_cost,0)) --新客推荐   
      set @empTicheng =ISNULL(@empTicheng,0) -ISNULL(@tjsr_salary_cost,0)                                       
    end                                          
    ---洗剪吹项目 美发师扣除0.25的成本 总监扣除0.11的成本 首席扣除0.15的成本                                        
    ---美发项目美容师参与的干洗20的业绩，水系5 提成系数0.3           
    if(@tmpPrjId in ('300','3002','301','302','303','305','306','309','311','321','322','323','324','325','326','327','328','329','330','331','332'))                                      
    begin                                        
     if(isnull(@empPostion,'')in('004','00401','00402','00103') or isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903' or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                     
     begin                                        
      if(@tmpPrjId='300' or @tmpPrjId='302' or @tmpPrjId='303'  or @tmpPrjId='309' )                                        
      begin                                        
       update #work_detail set staffyeji=20*@fuflag where seqno=@tmpSeqId                                          
       set @empTicheng =6*@fuflag                                        
      end                                         
      else                                   
      begin                                        
       update #work_detail set staffyeji=5*@fuflag where seqno=@tmpSeqId                                          
        set @empTicheng =1.5*@fuflag                          
      end                                        
     end                                               
     if((@tmpPrjId='300' or @tmpPrjId='3002') and isnull(@tmpItem,'') not in ('7','8','9'))                                        
     begin                                        
      if(isnull(@empPostion,'')='006' or isnull(@empPostion,'') ='007' or isnull(@empPostion,'') ='00701' or isnull(@empPostion,'') ='00702')--首席总监        
      begin                                        
       update #work_detail set staffyeji=10*@fuflag where seqno=@tmpSeqId                                        
       set @empTicheng =3*@fuflag                                        
      end                                               
      else if(isnull(@empPostion,'')='003')                                        
      begin                                        
       update #work_detail set staffyeji=10*@fuflag where seqno=@tmpSeqId                                        
       set @empTicheng =2.8*@fuflag                                        
      end                                               
      else                                         
      begin               
       update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                          
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
     update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                          
     set @empTicheng = 0       
     fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan ,@arrivaldmonth,@csitemstate,@billinsertype,@costpricetype                                       
     continue                                      
    end    
    if((ISNULL(@newcosttc,0)>0 or ISNULL(@oldcosttc,0)>0) )  
    begin  
     if(  isnull(@tmpItem,'')='7' and ISNULL(@costpricetype,0)=0 )  --指定客非体验项目  
     begin  
       update #work_detail set staffticheng=@oldcosttc*@fuflag  where seqno=@tmpSeqId   
       set @empTicheng=@oldcosttc*@fuflag  
     end  
     else   if(  isnull(@tmpItem,'')='7' and ISNULL(@costpricetype,0)=1  ) --指定客体验项目  
     begin  
       if(ISNULL(@oldcosttc,0)>ISNULL(@mr_salary_fixed_ty,0))  
       begin  
        update #work_detail set staffticheng=ISNULL(@mr_salary_fixed_ty,0)*@fuflag  where seqno=@tmpSeqId   
        set @empTicheng=ISNULL(@mr_salary_fixed_ty,0)*@fuflag  
       end  
       else  
       begin  
        update #work_detail set staffticheng=@oldcosttc*@fuflag  where seqno=@tmpSeqId   
        set @empTicheng=@oldcosttc*@fuflag  
       end  
     end  
     else   if(  isnull(@tmpItem,'')='8' and ISNULL(@costpricetype,0)=0) --新客非体验项目  
     begin  
       update #work_detail set staffticheng=@newcosttc*@fuflag  where seqno=@tmpSeqId  
       set @empTicheng=@newcosttc*@fuflag   
     end  
     else   if(  isnull(@tmpItem,'')='8' and ISNULL(@costpricetype,0)=1) --新客体验项目  
     begin  
       if(ISNULL(@newcosttc,0)>ISNULL(@mr_salary_fixed_ty,0))  
       begin  
        update #work_detail set staffticheng=ISNULL(@mr_salary_fixed_ty,0)*@fuflag  where seqno=@tmpSeqId   
        set @empTicheng=ISNULL(@mr_salary_fixed_ty,0)*@fuflag   
       end  
       else  
       begin  
        update #work_detail set staffticheng=@newcosttc*@fuflag  where seqno=@tmpSeqId  
        set @empTicheng=@newcosttc*@fuflag   
       end  
     end  
     else   if(  isnull(@tmpItem,'')='9' and ISNULL(@costpricetype,0)=1) --新客推荐体验  
     begin  
       if(ISNULL(@newcosttc,0)>ISNULL(@mr_salary_fixed_ty,0))  
       begin  
        update #work_detail set staffticheng=(@mr_salary_fixed_ty-ISNULL(@tjsr_salary_cost,0))*@fuflag  where seqno=@tmpSeqId   
        set @empTicheng=(@mr_salary_fixed_ty-ISNULL(@tjsr_salary_cost,0))*@fuflag  
       end  
       else  
       begin  
        update #work_detail set staffticheng=(@newcosttc-ISNULL(@tjsr_salary_cost,0))*@fuflag  where seqno=@tmpSeqId   
        set @empTicheng=(@newcosttc-ISNULL(@tjsr_salary_cost,0))*@fuflag  
       end  
     end  
     else   if(  isnull(@tmpItem,'')='9' and ISNULL(@costpricetype,0)=0) --新客推荐非体验  
     begin  
       update #work_detail set staffticheng=(@newcosttc-ISNULL(@tjsr_salary_cost,0))*@fuflag  where seqno=@tmpSeqId   
       set @empTicheng=(@newcosttc-ISNULL(@tjsr_salary_cost,0))*@fuflag  
     end  
     else  
     begin  
       update #work_detail set staffticheng=0 where seqno=@tmpSeqId  
       set @empTicheng=0  
     end   
     if( isnull(@paycode,'')='13')  
     begin  
       update #work_detail set staffticheng=ISNULL(@mr_salary_fixed_tmk,0)*@fuflag  where seqno=@tmpSeqId   
       set @empTicheng=ISNULL(@mr_salary_fixed_tmk,0)*@fuflag   
     end   
     fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan,@arrivaldmonth ,@csitemstate,@billinsertype,@costpricetype                                       
     continue  
    end  
    if(@tmpPrjId in ('321','322','323','324','325','326','327','328','329','330','331','332') and isnull(@tmpItem,'')  in ('10','11','12') and  isnull(@empPostion,'') not in ('003','006','007','00701','00702')   )  
    begin  
      if(  ISNULL(@csitemstate,1)=1)  
       update #work_detail set staffticheng=ISNULL(@xjc_salary_fixed_db,0)*@fuflag  where seqno=@tmpSeqId   
      else   if( ISNULL(@csitemstate,1)=2)  
       update #work_detail set staffticheng=ISNULL(@xjc_salary_fixed_ndb,0)*@fuflag  where seqno=@tmpSeqId  
      else   if( ISNULL(@csitemstate,1)=3)  
       update #work_detail set staffticheng=ISNULL(@xjc_salary_fixed_nhg,0)*@fuflag  where seqno=@tmpSeqId   
      else  
       update #work_detail set staffticheng=0 where seqno=@tmpSeqId                                        
      fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan,@arrivaldmonth ,@csitemstate ,@billinsertype,@costpricetype                                      
      continue   
    end    
    if(@tmpPrjId in ('321','322','323','324','325','326','327','328','329','330','331','332') and isnull(@tmpItem,'')  in ('13','14','15') )  
    begin  
      update #work_detail set staffticheng=0 where seqno=@tmpSeqId                                        
      fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan,@arrivaldmonth ,@csitemstate,@billinsertype,@costpricetype                               
      continue   
    end       
    --if(@compid in ('006','047','033','014','041','046') and isnull(@empPostion,'')  in ('003','006','007','00701','00702')   )  
    if(isnull(@isNewComdMode,0)=1 and isnull(@empPostion,'')  in ('003','006','007','00701','00702')  and ISNULL(@PROJECT_TYPE,'')<>'6' )  
    begin  
     if(ISNULL(@arrivaldmonth,0)<=5)  
     begin  
      set @Wage_Rates=ISNULL(@mf_salaryrate_fivedown,0) --********新6家店美发师5月内的提成系数为0.3  
     end  
     else  
     begin  
      set @Wage_Rates=ISNULL(@mf_salaryrate_fiveup,0)  --********新6家店的提成系数为0.25  
     end  
    end        
    if(@PROJECT_TYPE<>'5')--美甲成本单独算                          
    begin                          
     update #work_detail set staffyeji=(isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*isnull(@Performance_Ratio,0) where seqno=@tmpSeqId                                               
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
       update #work_detail set staffyeji=isnull(@tmpYeji,0)*ISNULL(@xjc_costrate_sjs,0) where seqno=@tmpSeqId                                          
       set @empTicheng = isnull(@tmpYeji,0)*ISNULL(@xjc_costrate_sjs,0)*@Wage_Rates          
      end   
      else  
      begin                                       
       update #work_detail set staffyeji=isnull(@tmpYeji,0)*ISNULL(@xjc_costrate_sjs,0) where seqno=@tmpSeqId                                          
       set @empTicheng = isnull(@tmpYeji,0)*ISNULL(@xjc_costrate_sjs,0)*0.28   
      end                                          
     end                                         
     else if(isnull(@empPostion,'')='006') --首席                                        
     begin    
      --if(@compid in ('006','047','033','014','041','046')   )  
      if(isnull(@isNewComdMode,0)=1)  
      begin  
       update #work_detail set staffyeji=isnull(@tmpYeji,0)*ISNULL(@xjc_costrate_sx,0) where seqno=@tmpSeqId                                          
       set @empTicheng = isnull(@tmpYeji,0)*ISNULL(@xjc_costrate_sx,0)*@Wage_Rates          
      end   
      else  
      begin                                        
       update #work_detail set staffyeji=isnull(@tmpYeji,0)*ISNULL(@xjc_costrate_sx,0) where seqno=@tmpSeqId                                          
       set @empTicheng = isnull(@tmpYeji,0)*ISNULL(@xjc_costrate_sx,0)*0.3     
      end                        
     end                        
     else if(isnull(@empPostion,'')='007' or isnull(@empPostion,'')='00701' or isnull(@empPostion,'')='00702') --总监                                       
     begin                                        
      --if(@compid in ('006','047','033','014','041','046')   )  
      if(isnull(@isNewComdMode,0)=1)  
      begin  
       update #work_detail set staffyeji=isnull(@tmpYeji,0)*ISNULL(@xjc_costrate_zj,0) where seqno=@tmpSeqId                                          
       set @empTicheng = isnull(@tmpYeji,0)*ISNULL(@xjc_costrate_zj,0)*@Wage_Rates          
      end   
      else  
      begin     
       update #work_detail set staffyeji=isnull(@tmpYeji,0)*ISNULL(@xjc_costrate_zj,0) where seqno=@tmpSeqId                                          
       set @empTicheng = isnull(@tmpYeji,0)*ISNULL(@xjc_costrate_zj,0)*0.3   
      end                                         
     end                                         
     else if(isnull(@empPostion,'')in('004','00401','00402','00103') or isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903' or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                
     begin                                        
      if(@tmpPrjId='300' or @tmpPrjId='302' or @tmpPrjId='303'  or @tmpPrjId='309' )                                  
      begin                                        
       update #work_detail set staffyeji=20*@fuflag where seqno=@tmpSeqId                                          
       set @empTicheng =6*@fuflag                                        
      end                                         
      else                                        
      begin                                        
       update #work_detail set staffyeji=5*@fuflag where seqno=@tmpSeqId                                          
       set @empTicheng =1.5*@fuflag                                        
      end                                        
     end    
     --新6店的提成比率  
     if((@tmpPrjId='300' or @tmpPrjId='3002') and isnull(@tmpItem,'') not in ('7','8','9'))                         
     begin                       
      if(isnull(@empPostion,'')='006' or isnull(@empPostion,'') ='007' or isnull(@empPostion,'') ='00701' or isnull(@empPostion,'') ='00702')--首席总监                
      begin                                        
       update #work_detail set staffyeji=10*@fuflag where seqno=@tmpSeqId                                        
       set @empTicheng =3*@fuflag                                        
      end                                               
      else if(isnull(@empPostion,'')='003')                                        
      begin                                        
       update #work_detail set staffyeji=10*@fuflag where seqno=@tmpSeqId   
       set @empTicheng =2.8*@fuflag                                        
      end                                               
      else                                         
      begin                                        
       update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                          
       set @empTicheng =0                                        
      end                  
     end                                        
    end                                        
    if(isnull(@paycode,'')='13' )                                        
    begin                    
     declare @tmcardfrom int --0 正常开卡,1 赠送开卡  
     declare @createcardtype int --0 正常开卡,1 赠送开卡                                           
     select @tmcardfrom=ISNULL(entrytype,0),@createcardtype=createcardtype from nointernalcardinfo where cardno=@cardtype                                        
     if(ISNULL(@createcardtype ,0)=8009)  
     begin  
      set @empTicheng =60  
     end  
     else if(ISNULL(@tmcardfrom,0)=1)                  
     begin                                        
      --烫染师 记业绩24% 提成5%                                        
      if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903' or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                                            
      begin                                        
       update #work_detail set staffyeji=isnull(@tmpYeji,0)*ISNULL(@yjk_costrate_tr,0) where seqno=@tmpSeqId                                          
       set @empTicheng = isnull(@tmpYeji,0)*ISNULL(@yjk_costrate_tr,0)*ISNULL(@salaryrate_tra,0)                    
       if( isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                                        
        set @empTicheng = isnull(@tmpYeji,0)*ISNULL(@yjk_costrate_tr,0)*ISNULL(@salaryrate_trb,0)                                          
      end                                          
      --首席，总监，设计师记业绩24%,提成22% 美容师记业绩24%,提成22%                                         
      else                                         
      begin                                        
       update #work_detail set staffyeji=isnull(@tmpYeji,0)*ISNULL(@yjk_costrate_mrmf,0) where seqno=@tmpSeqId                                          
       set @empTicheng = isnull(@tmpYeji,0)*ISNULL(@yjk_costrate_mrmf,0)*ISNULL(@yjk_salaryrate_mrmf,0)                                        
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
       update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.9 where seqno=@tmpSeqId                                          
       set @empTicheng = isnull(@tmpYeji,0)*0.9*0.6                              
      end                                      
     end                                      
     else                                        
     begin                               
      update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                          
      set @empTicheng =0                                        
     end                                        
    end          
    if(isnull(@empPostion,'') in('004','00401','00402','00103')  and @tmpPrjId in ('48811') and isnull(@paycode,'')<>'13')                                        
    begin                                        
     if(isnull(@tmpItem,'') in ('7','8','9'))                        
     begin               
      if(isnull(@tmpYeji,0)<100)             
       set @tmpYeji=100                              
      update #work_detail set staffyeji= isnull(@tmpYeji,0)*0.8 where seqno=@tmpSeqId                   
      set @empTicheng = isnull(@tmpYeji,0)*0.8*0.3*@fuflag                        
     end                                   
     else                                        
     begin                                        
      update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                          
      set @empTicheng = 0                       
     end                                        
    end   
    if(isnull(@empPostion,'')in('004','00401','00402','00103') and ISNULL(@PROJECT_TYPE,'') in ('3','6') and ISNULL(@proflag,2)=1)                                        
    begin                               
     update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                          
     set @empTicheng = 0                                         
    end    
    if(isnull(@empPostion,'') in ('003','006','007','00701','00702','00901','00902','00903','00904','008')  and ISNULL(@PROJECT_TYPE,'') ='4' and ISNULL(@proflag,2)=1)                                        
    begin                                        
     update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                          
     set @empTicheng = 0                                         
    end    
   end         
  end     
  ---更新提成                                     
  update #work_detail set staffticheng=@empTicheng,person_inid=@empinid,postation=@empPostion where seqno=@tmpSeqId                                                                          
  fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan ,@arrivaldmonth ,@csitemstate,@billinsertype,@costpricetype                                 
 end                                                                
 close cur_yeji_ticheng                                                                
 deallocate cur_yeji_ticheng                               
 --美发师做美发项目 超过指定业绩增高普通的提成比率                                        
 --美发师业绩超2.5W,首席超5W,总监 7W 美发项目提成比率0.35                                        
 create  table #emp_yeji_total_resultx                                         
 (                                        
  inid  varchar(20) null,  --员工内部编号                                           
  yeji  float  null, -- 业绩                         
  lv   float  null, --提成比率                                  
 )                                           
 insert #emp_yeji_total_resultx(inid,yeji)                                               
 select  person_inid,sum(isnull(staffyeji,0)) from #work_detail   
 where ISNULL(action_id,-1)<>4 and paycode in ('1','2','6','0','14','15') group by person_inid                                      
    --if(@compid in ('006','047','033','014','041','046'))  
    if(isnull(@isNewComdMode,0)=1)  
    begin  
  if exists (select 1 from compchaininfo where curcomp='00105' and relationcomp=@compid )  
  begin  
   --新6店 5个月以上 发型师  5W下 0.35 5W 上0.40  
   update a set staffticheng=isnull(staffyeji,0)*0.35                                        
   from #work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                        
   where a.person_inid=b.inid and isnull(a.postation,'') in ('003','006','007','00701','00702')                                          
   and action_id in (7,10,13)  and ISNULL(paycode,'') not in ('9','11','12')                                         
   and b.yeji<50000 and  prjno=code and prjtype='3'                                        
   and isnull(cardtype,'')   not in ('MFOLD','ZK')   
   --and ISNULL(arrivaldmonth,0)>5    
   update a set staffticheng=isnull(staffyeji,0)*0.4                                        
   from #work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                        
   where a.person_inid=b.inid and isnull(a.postation,'') in ('003','006','007','00701','00702')                                         
   and action_id in (7,10,13)  and ISNULL(paycode,'') not in ('9','11','12')                                         
   and b.yeji>=50000 and  prjno=code and prjtype='3'                                        
   and isnull(cardtype,'')   not in ('MFOLD','ZK')   
   --and ISNULL(arrivaldmonth,0)>5    
  end  
  else  
  begin  
   update a set staffticheng=isnull(staffyeji,0)*0.30                                        
   from #work_detail a ,projectnameinfo with(nolock)                                        
   where isnull(a.postation,'') in ('003','006','007','00701','00702')                                      
   and action_id in (7,10,13)  and ISNULL(paycode,'') not in ('9','11','12')                                   
   and  prjno=code and prjtype='3'                                        
   and isnull(cardtype,'')   not in ('MFOLD','ZK')   
   and ISNULL(srvdate,'')<='20140831'  
   --新6店 5个月以上 发型师 1W-2W(不包含2W) 老客提成比率为30%  
   update a set staffticheng=isnull(staffyeji,0)*0.30                                        
   from #work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                        
   where a.person_inid=b.inid and isnull(a.postation,'')='003'                                         
   and action_id in (7,10,13)  and ISNULL(paycode,'') not in ('9','11','12')                                         
   and b.yeji>=10000 and b.yeji<20000 and  prjno=code and prjtype='3'                                        
   and isnull(cardtype,'')   not in ('MFOLD','ZK')   
   --and ISNULL(arrivaldmonth,0)>5    
   --新6店 5个月以上 发型师 2W以上 老客提成比率为35%  
   update a set staffticheng=isnull(staffyeji,0)*0.35                                       
   from #work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                        
   where a.person_inid=b.inid and isnull(a.postation,'')='003'                                         
   and action_id in (7,10,13)  and ISNULL(paycode,'') not in ('9','11','12')                                         
   and b.yeji>=20000 and  prjno=code and prjtype='3'                                        
   and isnull(cardtype,'')   not in ('MFOLD','ZK')   
   --and ISNULL(arrivaldmonth,0)>5                                      
   ----新6店 5个月以上 首席 2W-3W(不包含3W) 老客提成比率为30%     
   update a set staffticheng=isnull(staffyeji,0)*0.30                                        
   from #work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                        
   where a.person_inid=b.inid and isnull(a.postation,'')='006'                                         
   and action_id in (7,10,13) and ISNULL(paycode,'') not in ('9','11','12')                                         
   and b.yeji>=20000 and b.yeji<30000 and  prjno=code and prjtype='3'                                        
   and isnull(cardtype,'')   not in ('MFOLD','ZK')      
   --and ISNULL(arrivaldmonth,0)>5      
   ----新6店 5个月以上 首席 3W-5W(不包含5W) 老客提成比率为35%      
   update a set staffticheng=isnull(staffyeji,0)*0.35                                       
   from #work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                        
   where a.person_inid=b.inid and isnull(a.postation,'')='006'                                         
   and action_id in (7,10,13) and ISNULL(paycode,'') not in ('9','11','12')                                        
   and b.yeji>=30000 and b.yeji<50000 and  prjno=code and prjtype='3'                                        
   and isnull(cardtype,'')   not in ('MFOLD','ZK')      
   --and ISNULL(arrivaldmonth,0)>5      
   ----新6店 5个月以上 首席 5W以上 老客提成比率为40%      
   update a set staffticheng=isnull(staffyeji,0)*0.40                                       
   from #work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                        
   where a.person_inid=b.inid and isnull(a.postation,'')='006'                                         
   and action_id in (7,10,13) and ISNULL(paycode,'') not in ('9','11','12')                                       
   and b.yeji>=50000 and  prjno=code and prjtype='3'                                        
   and isnull(cardtype,'')   not in ('MFOLD','ZK')      
   --and ISNULL(arrivaldmonth,0)>5   
   ----新6店 5个月以上 总监,创意总监,设计总监 3W-5W(不包含5W) 老客提成比率为30%                                
   update a set staffticheng=isnull(staffyeji,0)*0.30                                        
   from #work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                        
   where a.person_inid=b.inid and isnull(a.postation,'') in ('007' ,'00701','00702')                                       
   and action_id in (7,10,13) and ISNULL(paycode,'') not in ('9','11','12')                                       
   and b.yeji>=30000 and b.yeji<50000   and prjno=code and prjtype='3'                                        
   and isnull(cardtype,'')   not in ('MFOLD','ZK')                                        
   --and ISNULL(arrivaldmonth,0)>5   
   ----新6店 5个月以上 总监,创意总监,设计总监 5W-8W(不包含5W) 老客提成比率为35%                                
   update a set staffticheng=isnull(staffyeji,0)*0.35                                        
   from #work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                        
   where a.person_inid=b.inid and isnull(a.postation,'') in ('007' ,'00701','00702')                                       
   and action_id in (7,10,13) and ISNULL(paycode,'') not in ('9','11','12')                                      
   and b.yeji>=50000 and b.yeji<70000   and prjno=code and prjtype='3'                                        
   and isnull(cardtype,'')   not in ('MFOLD','ZK')                                        
   --and ISNULL(arrivaldmonth,0)>5   
   ----新6店 5个月以上 总监,创意总监,设计总监 8W以上 老客提成比率为40%                                
   update a set staffticheng=isnull(staffyeji,0)*0.40                    
   from #work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                        
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
  from #work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                        
  where a.person_inid=b.inid and isnull(a.postation,'')='003'                                         
  and (action_id>=7 and action_id<=15) and ISNULL(paycode,'')<>'9'                                        
  and b.yeji>=30000 and  prjno=code and prjtype='3'                                        
  and isnull(cardtype,'')   not in ('MFOLD','ZK')                                        
  update a set staffticheng=isnull(staffyeji,0)*0.35                                        
  from #work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                        
  where a.person_inid=b.inid and isnull(a.postation,'')='006'                                         
  and (action_id>=7 and action_id<=15) and ISNULL(paycode,'')<>'9'                                        
  and b.yeji>=50000 and  prjno=code and prjtype='3'                                        
  and isnull(cardtype,'')   not in ('MFOLD','ZK')                                        
  update a set staffticheng=isnull(staffyeji,0)*0.35                                        
  from #work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                        
  where a.person_inid=b.inid and isnull(a.postation,'')='007'                                         
  and (action_id>=7 and action_id<=15) and ISNULL(paycode,'')<>'9'                                        
  and b.yeji>=70000  and prjno=code and prjtype='3'                                        
  and isnull(cardtype,'')   not in ('MFOLD','ZK')                                        
  update a set staffticheng=isnull(staffyeji,0)*0.35                                        
  from #work_detail a ,#emp_yeji_total_resultx b,projectnameinfo with(nolock)                                        
  where a.person_inid=b.inid and isnull(a.postation,'') in ('00701','00702')                                      
  and (action_id>=7 and action_id<=15) and ISNULL(paycode,'')<>'9'                                        
  and b.yeji>=90000  and prjno=code and prjtype='3'                                        
  and isnull(cardtype,'')   not in ('MFOLD','ZK')     
 end                            
 ---美容师A类                      
 /*if(@compid in ('008','017','019','026','032'))                      
 begin                      
  update a set staffticheng=ISNULL(staffticheng,0)+ISNULL(staffyeji,0)*0.05          
  from #work_detail a,#emp_yeji_total_resultx b                      
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
  from #work_detail a,nointernalcardinfo,#emp_yeji_total_resultx b          
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
  from #work_detail a,#emp_yeji_total_resultx b                      
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
  from #work_detail a,nointernalcardinfo,#emp_yeji_total_resultx b                      
  where a.person_inid=b.inid                       
  and a.cardtype=cardno                      
  and ISNULL(a.postation,'')='004'                      
  and (action_id>=7 and action_id<=15)                     
  and paycode='13'                      
  and yeji<70000                       
  and yeji>=50000                      
  and isnull(entrytype,0)=0                      
  and action_id not in (26,27,28,29,30,31)                      
 end   */                   
 drop table #emp_yeji_total_resultx    
  --现有提成方式                          
  --承包方式:五五分成,扣5%成本                          
  --公司直营:个人业绩1-5000 50% 扣10%成本,5000以上35%扣10%成本                                       
  if(ISNULL(@comptypebyfinger,'1')='3') --承包方式                          
  begin                          
   update #work_detail set staffyeji=ISNULL(staffyeji,0)*0.95,staffticheng=isnull(staffticheng,0)*0.95*0.5                          
   from #work_detail,staffinfo where Manageno=person_inid and position = '005'                          
  end                     
  else  if(ISNULL(@comptypebyfinger,'1')='2') --公司直营                           
  begin                          
      update #work_detail set staffyeji=ISNULL(staffyeji,0)*0.9,staffticheng=isnull(staffticheng,0)*0.5*0.9                          
   where  person_inid in (select person_inid from #work_detail ,staffinfo where Manageno=person_inid and position = '005'                          
   group by person_inid having SUM(ISNULL(staffyeji,0))<=5000 )                           
      update #work_detail set staffyeji=ISNULL(staffyeji,0)*0.9,staffticheng=isnull(staffticheng,0)*0.35*0.9                  
   where  person_inid in (select person_inid from #work_detail ,staffinfo where Manageno=person_inid and position = '005'                          
   group by person_inid having SUM(ISNULL(staffyeji,0))>5000 )                           
  end            
    --88  美容经理/顾问 提成0.02  
 update #work_detail set staffticheng=isnull(staffyeji,0)*0.02 where action_id=88  
 select seqno,person_inid,action_id,srvdate,code,name,payway,billamt,ccount,cost,staffticheng,staffyeji,staffshareyeji,prj_type,cls_flag,billid,paycode,compid,cardid,cardtype         
 from #work_detail order by action_id,srvdate        
 drop table #empinfobydate        
    drop table #work_detail         
end 
