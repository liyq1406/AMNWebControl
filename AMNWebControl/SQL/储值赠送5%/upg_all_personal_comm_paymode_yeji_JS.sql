  
 --declare @tocompid varchar(10)    
 --declare cur_each_comp cursor for    
 --select curcompno from compchainstruct where complevel=4  
 --open cur_each_comp    
 --fetch cur_each_comp into @tocompid    
 --while @@fetch_status = 0    
 --begin    
 -- select @tocompid  
 -- exec upg_all_personal_comm_paymode_yeji_JS @tocompid,'20140301','20140331'  
 -- fetch cur_each_comp into @tocompid    
 --end    
 --close cur_each_comp    
 --deallocate cur_each_comp                                 
    
    
  
alter procedure upg_all_personal_comm_paymode_yeji_JS(                                        
	@compid    varchar(10), -- 公司别                                        
	@fromdate   varchar(10), -- 开始日期                                        
	@todate    varchar(10) -- 截至日期           
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
    
    declare @isNewComdMode	int	--是否是新模式
    set @isNewComdMode=0
    if exists(select 1 from compchaininfo where curcomp in ('0010104','0010102') and relationcomp=@compid  and @compid not in ('028','035') )
	begin
		set @isNewComdMode=1
    end 
    else
    begin
		if(@todate>'20140531')
		begin
			set @isNewComdMode=1
		end
    end
    
    declare @SP105 varchar(2)
    select @SP105=paramvalue from sysparaminfo where compid=@compid and paramid='SP105'
    
    exec upg_prepare_yeji_analysis @compid,@fromdate, @todate  
  
    update   #allstaff_work_detail set arrivaldmonth=datediff(month,arrivaldate,@todate)
    from  #allstaff_work_detail,staffinfo 
    where person_inid=manageno
            
	      
          
	update #allstaff_work_detail set payway=parentcodevalue         
	from #allstaff_work_detail,commoninfo        
	where infotype='ZFFS' and parentcodekey=paycode        
         
	update #allstaff_work_detail set cardtype='' where cardtype='ZK' and paycode not in ('4','17')       
        
        
     
	create table #empinfobydate
	(                                        
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
	declare @quan float                                        
	declare @fuflag float --正负单据                                        
	declare @businessflag int --是否为业务人员 0--不是 1--是                                         
	declare @empinid varchar(20)                                    
	declare @proflag int --项目类别    
	declare @arrivaldmonth	int  --在职月份 
	declare @newcosttc  float	--新客提成数
	declare @oldcosttc  float	--老客提成数 
	declare @saleprice  float	--项目标准价
	declare @csitemstate int	--是否达标	0 无 1 达标 2 未达标
	declare @billinsertype int		--充值主办方 1 美容 2 美发    0 不区分    
	declare @costpricetype  int   --是否为体验价消费	0 不是 1 是                                                       
	declare cur_yeji_ticheng cursor for                                                                 
	select seqno,person_inid,action_id,staffyeji,code,srvdate ,paycode,isnull(cardtype,''),isnull(ccount,0),isnull(arrivaldmonth,0),ISNULL(csitemstate,0),ISNULL(billinsertype,0),isnull(costpricetype,0)                                                 
	from #allstaff_work_detail                                                                
	declare @empTicheng float             
                                                                 
	open cur_yeji_ticheng                                                                
	fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate,@paycode ,@cardtype,@quan,@arrivaldmonth,@csitemstate,@billinsertype ,@costpricetype                                                                  
	while(@@fetch_status=0)                            
	begin                                             
		set @empTicheng = 0           
		set @empPostion=''      
		--更新员工的最新职位              
		select @empPostion=position ,@empinid=inid from #empinfobydate where inid=@tmpEmpId and @tmpDate>=datefrom and @tmpDate<dateto                             
		-- 查看员工是否是业务人员           
		select @businessflag=ISNULL(businessflag,0)  from staffinfo with(nolock)  where manageno=@tmpEmpId                                     
		-- 如果是非业务人员提成为0        
		if(@businessflag=0 or isnull(@empPostion,'') not in ('003','004','00103','00401','00402','005','006','007','00701','00702','008','00901','00902','00903','00904'))                                              
		begin                                        
			update #allstaff_work_detail set staffticheng=0 where seqno=@tmpSeqId                                           
			fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan ,@arrivaldmonth,@csitemstate,@billinsertype  ,@costpricetype                                         
			continue                                           
		end        
		-- 16，17，18，19，20，21，22，23，24-售产品          
		if(isnull(@tmpItem,'') in ('16','17','18','19','20','21','22','23','24'))                                                        
		begin              
			select   @GOODS_TYPE=isnull(goodstype,1)  from  goodsnameinfo with(nolock) where goodsno=@tmpPrjId                                              
			if(isnull(@GOODS_TYPE,'300'))='400'----美容产品(扣除成本20%提成10%)                                              
			begin                                              
				update #allstaff_work_detail set staffyeji=isnull(staffyeji,0) where seqno=@tmpSeqId                                               
				set @empTicheng = isnull(@tmpYeji,0)*0.06                                             
			end                                              
			else if(isnull(@GOODS_TYPE,'300'))='300'----美发产品（提成5%）                                         
			begin                                              
				update #allstaff_work_detail set staffyeji=isnull(staffyeji,0) where seqno=@tmpSeqId                                               
				set @empTicheng = isnull(@tmpYeji,0)*@GOODS_SALE_RATE_hair                                                 
			end                                             
			else if(isnull(@GOODS_TYPE,'300'))='500'----美甲产品（产品不扣成本 4，6分）                                             
			begin                                                 
				set @empTicheng = isnull(@tmpYeji,0)*@GOODS_SALE_RATE_finger--0.4                                              
			end                                          
			else if(isnull(@GOODS_TYPE,'300'))='700'----卡诗产品（无业绩无提成）                                                   
			begin                                                 
				update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId                                               
				set @empTicheng = 0                                        
			end                                                        
		end          
		-- 开卡+充值+转卡+条码卡开卡 1，2，3，5        
		else if(isnull(@tmpItem,'')='1' or isnull(@tmpItem,'')='2'  or isnull(@tmpItem,'')='3' or isnull(@tmpItem,'')='5')                                             
		begin                                               
			if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902' or isnull(@empPostion,'')='00903'  )  --一级和二级烫染师1.5%                                         
				set @empTicheng = @tmpYeji*0.015                                           
			else if (isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008' )  --三级和四级烫染师 2%                                         
				set @empTicheng = @tmpYeji*0.02                                       
			else    -- 其他职位都是6%                     
				set @empTicheng = @tmpYeji*0.06 
			--if(@compid in ('006','047','033','014','041','046'))
			if(isnull(@isNewComdMode,0)=1)
			begin
				if (isnull(@empPostion,'')='00402') -- C类美容师不拿提成
					set @empTicheng = 0      
				else if (isnull(@empPostion,'')='004' or isnull(@empPostion,'')='00103'  or isnull(@empPostion,'')='00401' ) -- 其他类美容师拿1%的提成
					set @empTicheng = @tmpYeji*0.01  
				else if (isnull(@empPostion,'') in ('003','006','007','00701','00702') and ISNULL(@tmpDate,'')>='20140517' ) -- 美发部拿4%
					set @empTicheng = @tmpYeji*0.04
			
				if(ISNULL(@billinsertype,0)=1 and isnull(@empPostion,'') in ('003','006','007','00701','00702')) --美容卡金
				begin
					update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId                
				end 
				if(ISNULL(@billinsertype,0)=2 and isnull(@empPostion,'') in ('004','00103','00401','00402')) --美发卡金
				begin
					update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId                
				end 
			end                                                         
		end           
		-- 合作项目销售  26，27，28，29，30，31                                             
		else if(isnull(@tmpItem,'')='26' or isnull(@tmpItem,'')='27'                                
			or  isnull(@tmpItem,'')='28' or isnull(@tmpItem,'')='29'                                
			or  isnull(@tmpItem,'')='30' or isnull(@tmpItem,'')='31' )                                           
		begin                                               
			if(isnull(@tmpItem,'')='26' or isnull(@tmpItem,'')='27' or isnull(@tmpItem,'')='30' )                                
				set @empTicheng = @tmpYeji*0.06                                   
			else if (isnull(@tmpItem,'')='28')                                
				set @empTicheng = @tmpYeji*0.3                                
		end          
		--疗程兑换  4        
		else if(isnull(@tmpItem,'')='4')                                       
		begin                                        
			if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902' or isnull(@empPostion,'')='00903'  )  --一级和二级烫染师2%                                           
				set @empTicheng = @tmpYeji*0.02                                             
			else if ( isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008' )  --三级和四级烫染师 2.5%              
				set @empTicheng = @tmpYeji*0.025                                            
			--首席，总监，设计师记业绩20%,提成20%                                         
			else if ( isnull(@empPostion,'')='003' or isnull(@empPostion,'')='006' or isnull(@empPostion,'')='007' or isnull(@empPostion,'')='00701' or isnull(@empPostion,'')='00702'  or isnull(@empPostion,'')='00102')                                          
			begin                                        
				update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*0.2 where seqno=@tmpSeqId                                          
				set @empTicheng = isnull(@tmpYeji,0)*0.2*0.2                        
			end                                        
		end    
		--疗程兑换 (建发奖励)   
		else if(isnull(@tmpItem,'')='25')                                     
		begin      
			                                
			if(isnull(@empPostion,'') in ('00901','00902','00903','00904','008'))  --一级和二级烫染师2%                                         
			begin
				update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId 
				set @empTicheng = @tmpYeji*15  
			end  
			else if(isnull(@empPostion,'') in ('003','006','007','00701','00702'))  --一级和二级烫染师2%                                         
			begin
				update #allstaff_work_detail set staffyeji=0 where seqno=@tmpSeqId 
				set @empTicheng = @tmpYeji*15  
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
				set @empTicheng = 10      
			else 
				set @empTicheng = 0        
		end            
		--项目消费 7,8,9,10,11,12,13,14,15        
		else        
		begin        
			set @fuflag=@quan           
			if(isnull(@paycode,'')='9') --疗程                                                          
			begin                                                            
				--疗程消费美容师和美发师按照设定成本和业绩比率走                                    
				select @proflag=isnull(prjpricetype,2),@PROJECT_COST=isnull(costprice,0),@Performance_Ratio=isnull(lyjrate,1),
					@Wage_Rates=isnull(ltcrate,1),@PROJECT_TYPE=prjtype,@newcosttc=newcosttc,@oldcosttc=oldcosttc      
					from  projectinfo a,sysparaminfo b     
					where b.paramid='SP059' and b.compid=@compid and b.paramvalue=a.prjmodeId and prjno=@tmpPrjId                                      
        
				if(ISNULL(@newcosttc,0)>0 or ISNULL(@oldcosttc,0)>0)
				begin
						if(  isnull(@tmpItem,'')='7')
							update #allstaff_work_detail set staffticheng=@oldcosttc*@fuflag  where seqno=@tmpSeqId 
						else   if(  isnull(@tmpItem,'')='8')
							update #allstaff_work_detail set staffticheng=@newcosttc*@fuflag  where seqno=@tmpSeqId 
						else
							update #allstaff_work_detail set staffticheng=0 where seqno=@tmpSeqId                                      
						fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan ,@arrivaldmonth,@csitemstate,@billinsertype ,@costpricetype                                       
						continue  
				end
				update #allstaff_work_detail set staffyeji=(isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*isnull(@Performance_Ratio,0) where seqno=@tmpSeqId                                               
				set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*@Performance_Ratio*@Wage_Rates                                              
                                                
				--烫染师 非疗程卡 记80%业绩 记5%提成 纯疗程卡记120块的业绩，6块钱的提成                                        
				if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903'  or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008' )                                            
				begin              
					if( isnull(@cardtype,'') ='MR' or isnull(@cardtype,'')='MF')                                            
					begin                                        
						update #allstaff_work_detail set staffyeji=120*@fuflag where seqno=@tmpSeqId                                               
						set @empTicheng=6*@fuflag                                        
					end                                         
					else                                        
					begin                                             
						update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*0.8 where seqno=@tmpSeqId                                 
						set @empTicheng = isnull(@tmpYeji,0)*0.8*0.05                                        
						if(isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')--四级烫染师                                        
							set @empTicheng = isnull(@tmpYeji,0)*0.8*0.06                                        
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
			else if( (@cardtype ='ZK' and isnull(@paycode,'')='4') or (@cardtype ='ZK' and isnull(@paycode,'')='17') or isnull(@paycode,'')='$'  or isnull(@paycode,'')='A' or isnull(@paycode,'')='7' or isnull(@paycode,'')='11'  or isnull(@paycode,'')='12'  )                       
			begin                                     
				select @newcosttc=newcosttc,@oldcosttc=oldcosttc      
				from  projectinfo a,sysparaminfo b     
				where b.paramid='SP059' and b.compid=@compid and b.paramvalue=a.prjmodeId and prjno=@tmpPrjId  
					                          
				if(isnull(@tmpYeji,0)>0)                                    
					set @fuflag=1                                    
				else                                    
					set @fuflag=-1 
				if(ISNULL(@newcosttc,0)>0 or ISNULL(@oldcosttc,0)>0)
				begin
					if(  isnull(@tmpItem,'')='7' and ISNULL(@costpricetype,0)=0 )  --指定客非体验项目
							update #allstaff_work_detail set staffticheng=@oldcosttc*@fuflag  where seqno=@tmpSeqId 
					else   if(  isnull(@tmpItem,'')='7' and ISNULL(@costpricetype,0)=1  ) --指定客体验项目
							update #allstaff_work_detail set staffticheng=@newcosttc*@fuflag  where seqno=@tmpSeqId 
					else   if(  isnull(@tmpItem,'')='8') --新客
							update #allstaff_work_detail set staffticheng=@newcosttc*@fuflag  where seqno=@tmpSeqId 
					else   if(  isnull(@tmpItem,'')='9') --新客推荐
							update #allstaff_work_detail set staffticheng=(@newcosttc-10)*@fuflag  where seqno=@tmpSeqId 
					else
							update #allstaff_work_detail set staffticheng=0 where seqno=@tmpSeqId 
							                                 
					fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan,@arrivaldmonth ,@csitemstate,@billinsertype  ,@costpricetype                                      
					continue  
				end
				if(@tmpPrjId in ('321','322','323','324','325','326','327','328','329','330','331','332') and isnull(@tmpItem,'')  in ('10','11','12') and  isnull(@empPostion,'') not in ('003','006','007','00701','00702') )
				begin
						if(  ISNULL(@csitemstate,1)=1)
							update #allstaff_work_detail set staffticheng=10*@fuflag  where seqno=@tmpSeqId 
						else   if( ISNULL(@csitemstate,1)=2)
							update #allstaff_work_detail set staffticheng=5*@fuflag  where seqno=@tmpSeqId 
						else
							update #allstaff_work_detail set staffticheng=0 where seqno=@tmpSeqId                                      
						fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan,@arrivaldmonth ,@csitemstate ,@billinsertype  ,@costpricetype                                     
						continue 
				end  
				if(@tmpPrjId in ('321','322','323','324','325','326','327','328','329','330','331','332') and isnull(@tmpItem,'')  in ('13','14','15') )
				begin
						update #allstaff_work_detail set staffticheng=0 where seqno=@tmpSeqId                                      
						fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan,@arrivaldmonth ,@csitemstate,@billinsertype   ,@costpricetype                                     
						continue 
				end                                         
				--项目抵用券使用面值做业绩                                        
				if(isnull(@paycode,'')='11' )                                        
				begin              
					select @tmpYeji=ISNULL(cardfaceamt,0) from nointernalcardinfo where cardno=@cardtype                                
					if(ISNULL(@fuflag,0)<0)                              
						set  @tmpYeji=ISNULL(@tmpYeji,0)*(-1)                                     
					update #allstaff_work_detail set staffyeji=@tmpYeji,billamt=@tmpYeji where seqno=@tmpSeqId                                          
				end                                         
				--烫染师 记业绩24% 提成5%                                        
				if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903' or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                                            
				begin                                        
					update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*0.24 where seqno=@tmpSeqId                                          
					set @empTicheng = isnull(@tmpYeji,0)*0.24*0.05                                          
					if( isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                  
						set @empTicheng = isnull(@tmpYeji,0)*0.24*0.06                                          
				end                                          
				--首席，总监，设计师记业绩24%,提成22% 美容师记业绩24%,提成22%                                         
				else                                         
				begin                                        
					update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*0.24 where seqno=@tmpSeqId                                          
					set @empTicheng = isnull(@tmpYeji,0)*0.24*0.22  
					if(  isnull(@tmpItem,'')='9' and ISNULL(@empTicheng,0)>10) --新客推荐 
						set @empTicheng =ISNULL(@empTicheng,0) -10                                            
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
					 
				if(ISNULL(@newcosttc,0)>0 or ISNULL(@oldcosttc,0)>0)
				begin
					if(  isnull(@tmpItem,'')='7' and ISNULL(@costpricetype,0)=0 )  --指定客非体验项目
							update #allstaff_work_detail set staffticheng=@oldcosttc*@fuflag  where seqno=@tmpSeqId 
					else   if(  isnull(@tmpItem,'')='7' and ISNULL(@costpricetype,0)=1  ) --指定客体验项目
							update #allstaff_work_detail set staffticheng=@newcosttc*@fuflag  where seqno=@tmpSeqId 
					else   if(  isnull(@tmpItem,'')='8') --新客
							update #allstaff_work_detail set staffticheng=@newcosttc*@fuflag  where seqno=@tmpSeqId 
					else   if(  isnull(@tmpItem,'')='9') --新客推荐
							update #allstaff_work_detail set staffticheng=(@newcosttc-10)*@fuflag  where seqno=@tmpSeqId 
					else
							update #allstaff_work_detail set staffticheng=0 where seqno=@tmpSeqId                                 
					fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan,@arrivaldmonth ,@csitemstate,@billinsertype ,@costpricetype                                       
					continue  
				end
				
				if(@tmpPrjId in ('321','322','323','324','325','326','327','328','329','330','331','332') and isnull(@tmpItem,'')  in ('10','11','12') and  isnull(@empPostion,'') not in ('003','006','007','00701','00702') )
				begin
						if(  ISNULL(@csitemstate,1)=1)
							update #allstaff_work_detail set staffticheng=10*@fuflag  where seqno=@tmpSeqId 
						else   if( ISNULL(@csitemstate,1)=2)
							update #allstaff_work_detail set staffticheng=5*@fuflag  where seqno=@tmpSeqId 
						else
							update #allstaff_work_detail set staffticheng=0 where seqno=@tmpSeqId                                      
						fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan,@arrivaldmonth ,@csitemstate,@billinsertype  ,@costpricetype                                      
						continue 
				end  
				if(@tmpPrjId in ('321','322','323','324','325','326','327','328','329','330','331','332') and isnull(@tmpItem,'')  in ('13','14','15') )
				begin
						update #allstaff_work_detail set staffticheng=0 where seqno=@tmpSeqId                                      
						fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan,@arrivaldmonth ,@csitemstate,@billinsertype  ,@costpricetype                                      
						continue 
				end     
				--if(@compid in ('006','047','033','014','041','046') and isnull(@empPostion,'')  in ('003','006','007','00701','00702')   )
				if(isnull(@isNewComdMode,0)=1 and isnull(@empPostion,'')  in ('003','006','007','00701','00702')   )
				begin
					if(ISNULL(@arrivaldmonth,0)<=5)
					begin
						set @Wage_Rates=0.3  --********新6家店美发师5月内的提成系数为0.3
					end
					else
					begin
						set @Wage_Rates=0.25  --********新6家店的提成系数为0.25
					end
				end              
				if(@PROJECT_TYPE<>'5')--美甲成本单独算                          
				begin                          
					update #allstaff_work_detail set staffyeji=(isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*isnull(@Performance_Ratio,0) where seqno=@tmpSeqId                                               
				end      
				                    
				set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*@Performance_Ratio*@Wage_Rates     
				if(  isnull(@tmpItem,'')='9' and ISNULL(@empTicheng,0)>10) --新客推荐 
					set @empTicheng =ISNULL(@empTicheng,0) -10                                           
				--首席总监 扣除成本后×业绩比率，提成 30%                                        
				--if(@compid not in ('006','047','033','014','041','046') )  --非6家店的为标准体系走
				if(isnull(@isNewComdMode,0)=0)
				begin   
					if((isnull(@empPostion,'')='006' or isnull(@empPostion,'') ='007' or isnull(@empPostion,'') ='00701' or isnull(@empPostion,'') ='00702') and ISNULL(@PROJECT_TYPE,'0')<>'6' )                                            
						set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*@Performance_Ratio*0.3                                             
				end
				--烫染师 扣除成本后记业绩5%                                        
				if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903'  )                                            
					set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*isnull(@Performance_Ratio,0)*0.05                                             
				if( isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                                        
					set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*isnull(@Performance_Ratio,0)*0.06                                             
                           
				---洗剪吹项目 美发师扣除0.25的成本 总监扣除0.11的成本 首席扣除0.15的成本                                        
				---美发项目美容师参与的干洗20的业绩，水系5 提成系数0.3                                            
				if(@tmpPrjId in ('300','3002','301','302','303','305','306','309','311','321','322','323','324','325','326','327','328','329','330','331','332'))                                    
				begin                                        
					if(isnull(@empPostion,'')='003') --美发师                                      
					begin 
					    --if(@compid in ('006','047','033','014','041','046')  )
					    if(isnull(@isNewComdMode,0)=1)
						begin
							update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*0.75 where seqno=@tmpSeqId                                        
							set @empTicheng = isnull(@tmpYeji,0)*0.75*@Wage_Rates        
						end 
						else
						begin                                     
							update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*0.75 where seqno=@tmpSeqId                                        
							set @empTicheng = isnull(@tmpYeji,0)*0.75*0.28 
						end                                        
					end                                       
					else if(isnull(@empPostion,'')='006') --首席                                      
					begin  
						--if(@compid in ('006','047','033','014','041','046')   )
						if(isnull(@isNewComdMode,0)=1)
						begin
							update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*0.85 where seqno=@tmpSeqId                                        
							set @empTicheng = isnull(@tmpYeji,0)*0.85*@Wage_Rates        
						end 
						else
						begin                                      
							update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*0.85 where seqno=@tmpSeqId                                        
							set @empTicheng = isnull(@tmpYeji,0)*0.85*0.3   
						end                      
					end                                   
					else if(isnull(@empPostion,'')='007' or isnull(@empPostion,'')='00701' or isnull(@empPostion,'')='00702') --总监                                     
					begin                                      
						--if(@compid in ('006','047','033','014','041','046')   )
						if(isnull(@isNewComdMode,0)=1)
						begin
							update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*0.89 where seqno=@tmpSeqId                                        
							set @empTicheng = isnull(@tmpYeji,0)*0.89*@Wage_Rates        
						end 
						else
						begin   
							update #allstaff_work_detail set staffyeji=isnull(@tmpYeji,0)*0.89 where seqno=@tmpSeqId                                        
							set @empTicheng = isnull(@tmpYeji,0)*0.89*0.3 
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
		fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan,@arrivaldmonth,@csitemstate,@billinsertype   ,@costpricetype                                   
	end                                                                
	close cur_yeji_ticheng                                                                
	deallocate cur_yeji_ticheng                    
           
  
		delete staff_work_yeji where compid=@compid and  srvdate between @fromdate  and @todate        
		insert staff_work_yeji(compid,person_inid,action_id,srvdate,code,name,payway,billamt,ccount,cost,staffticheng,staffyeji,prj_type,cls_flag,billid,paycode,cardid,cardtype,postation)        
		select compid,person_inid,action_id,srvdate,code,name,payway,billamt,ccount,cost,staffticheng,staffyeji,prj_type,cls_flag,billid,paycode,cardid,cardtype,postation        
		from #allstaff_work_detail   
         
		drop table #empinfobydate        
		drop table #allstaff_work_detail            
end 