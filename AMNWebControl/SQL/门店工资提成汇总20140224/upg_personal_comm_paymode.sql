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
  seqno					int identity			not null,           
  person_inid			varchar(20)			NULL, --员工内部编号    
  action_id				int					NULL, --单据类型    
  srvdate				varchar(10)		NULL, --日期    
  code					varchar(20)		NULL, --项目代码,或是卡号,产品码    
  name					varchar(40)		NULL, --名称    
  payway				varchar(20)		NULL, --支付方式    
  billamt				float			NULL, --营业金额    
  ccount				float			NULL, --数量    
  cost					float			NULL, --成本    
  staffticheng			float			NULL, --提成    
  staffyeji				float			NULL, --虚业绩    
  prj_type				varchar(20)		NULL, --项目类别    
  cls_flag				int				NULL, -- 1:项目 2:产品 3:卡    
  billid				varchar(20)		NULL, --单号    
  paycode				varchar(20)		NULL, --支付代码    
  compid				varchar(10)		NULL, --公司别    
  cardid				varchar(20)		NULL, --会员卡号    
  cardtype				varchar(20)		NULL, --会员卡类型    
  postation				varchar(10)		NULL, --职位 
  csitemstate			int				NULL,		--是否达标	0 无 1 达标 2 未达标   
 )   
    
 exec upg_prepare_yeji_detail @compid,@fromdate, @todate,@fromempinno, @toempinno      
      
 update #work_detail set payway=parentcodevalue     
 from #work_detail,commoninfo    
 where infotype='ZFFS' and parentcodekey=paycode    
     
     
 update #work_detail set cardtype='' where cardtype='ZK' and paycode<>'4'    
     
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
  set @CARD_SALE_RATE=0.06                                        
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
  declare @newcosttc  float	--新客提成数
  declare @oldcosttc  float		--老客提成数 
  declare @csitemstate int	--是否达标	0 无 1 达标 2 未达标                                                 
  declare cur_yeji_ticheng cursor for                                                             
  select seqno,person_inid,action_id,staffyeji,code,srvdate ,paycode,isnull(cardtype,''),isnull(ccount,0),ISNULL(csitemstate,0)                                            
  from #work_detail                                                            
  declare @empTicheng float         
                                                             
  open cur_yeji_ticheng                                                            
  fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate,@paycode ,@cardtype,@quan ,@csitemstate                                                             
  while(@@fetch_status=0)                        
  begin                                         
     set @empTicheng = 0     
     set @empPostion=''    
			--更新员工的最新职位          
			select @empPostion=position ,@empinid=inid from #empinfobydate where inid=@tmpEmpId and @tmpDate>=datefrom and @tmpDate<dateto     
            
			-- 查看员工是否是业务人员       
			select @businessflag=ISNULL(businessflag,0)  from staffinfo with(nolock)  where manageno=@tmpEmpId                                 
			-- 如果是非业务人员提成为0    
			if(@businessflag=0 or isnull(@empPostion,'') not in ('003','004','00401','00402','005','006','007','00701','00702','008','00901','00902','00903','00904'))
			begin                                    
				update #work_detail set staffticheng=0 where seqno=@tmpSeqId                                       
				fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan ,@csitemstate                                     
				continue                                       
			end    
			-- 16，17，18，19，20，21，22，23，24-售产品      
			if(isnull(@tmpItem,'') in ('16','17','18','19','20','21','22','23','24'))                                                    
			begin          
				select   @GOODS_TYPE=isnull(goodstype,1)  from  goodsnameinfo with(nolock) where goodsno=@tmpPrjId                                          
				if(isnull(@GOODS_TYPE,'300'))='400'----美容产品(扣除成本20%提成10%)                                          
				begin                                          
					update #work_detail set staffyeji=isnull(staffyeji,0) where seqno=@tmpSeqId                                           
					set @empTicheng = isnull(@tmpYeji,0)*0.06                                        
				end                                          
				else if(isnull(@GOODS_TYPE,'300'))='300'----美发产品（提成5%）                                           
				begin                                          
					update #work_detail set staffyeji=isnull(staffyeji,0) where seqno=@tmpSeqId                                           
					set @empTicheng = isnull(@tmpYeji,0)*@GOODS_SALE_RATE_hair                                             
				end                  
				else if(isnull(@GOODS_TYPE,'300'))='500'----美甲产品（产品不扣成本 4，6分）                                         
				begin                                             
					set @empTicheng = isnull(@tmpYeji,0)*@GOODS_SALE_RATE_finger--0.4               
				end                                      
				else if(isnull(@GOODS_TYPE,'300'))='700'----卡诗产品（无业绩无提成）                                               
				begin                                             
					update #work_detail set staffyeji=0 where seqno=@tmpSeqId                                           
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
					set @empTicheng = @tmpYeji*@CARD_SALE_RATE                                                  
			end       
			-- 合作项目销售  26，27，28，29，30，31                                         
			else if(isnull(@tmpItem,'')='26' or isnull(@tmpItem,'')='27'                            
			or  isnull(@tmpItem,'')='28' or isnull(@tmpItem,'')='29'                            
			or  isnull(@tmpItem,'')='30' or isnull(@tmpItem,'')='31' )                                       
			begin                                           
				if(isnull(@tmpItem,'')='26' or isnull(@tmpItem,'')='27' or isnull(@tmpItem,'')='30' )                            
					set @empTicheng = @tmpYeji*0.06                               
				else if (isnull(@tmpItem,'')='28')                            
					set @empTicheng = @tmpYeji*0.22                            
			end      
			--疗程兑换  4    
			else if(isnull(@tmpItem,'')='4')                                   
			begin                                    
				if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902' or isnull(@empPostion,'')='00903'  )  --一级和二级烫染师2%                                       
					set @empTicheng = @tmpYeji*0.02                                         
				else if ( isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008' )  --三级和四级烫染师 2.5%                                       
					set @empTicheng = @tmpYeji*0.025                                        
				--首席，总监，设计师记业绩20%,提成20%                                     
				else if ( isnull(@empPostion,'')='003' or isnull(@empPostion,'')='006'
				 or isnull(@empPostion,'')='007' or isnull(@empPostion,'')='00701' or isnull(@empPostion,'')='00702'
				 or isnull(@empPostion,'')='00102')                                      
				begin                                    
					update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.2 where seqno=@tmpSeqId                                      
					set @empTicheng = isnull(@tmpYeji,0)*0.2*0.2                    
				end                                    
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
					from  projectinfo ,compchaininfo     
					where prisource=curcomp and relationcomp=@compid and prjno=@tmpPrjId                                     
        
					if(ISNULL(@newcosttc,0)>0 or ISNULL(@oldcosttc,0)>0)
					begin
						if(  isnull(@tmpItem,'')='7')
							update #work_detail set staffticheng=@oldcosttc*@fuflag  where seqno=@tmpSeqId 
						else   if(  isnull(@tmpItem,'')='8')
							update #work_detail set staffticheng=@newcosttc*@fuflag  where seqno=@tmpSeqId 
						else
							update #work_detail set staffticheng=0 where seqno=@tmpSeqId                                      
						fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan ,@csitemstate                                     
						continue  
					end
					if(@tmpPrjId in ('321','322','323','324','325','326','327','328','329','330','331','332'))
					begin
						if(  ISNULL(@csitemstate,1)=1)
							update #work_detail set staffticheng=10*@fuflag  where seqno=@tmpSeqId 
						else   if( ISNULL(@csitemstate,1)=2)
							update #work_detail set staffticheng=5*@fuflag  where seqno=@tmpSeqId 
						else
							update #work_detail set staffticheng=0 where seqno=@tmpSeqId                                      
						fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan ,@csitemstate                                     
						continue 
					end
					
					update #work_detail set staffyeji=(isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*isnull(@Performance_Ratio,0) where seqno=@tmpSeqId                                           
					set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*@Performance_Ratio*@Wage_Rates                                          
                                            
					--烫染师 非疗程卡 记80%业绩 记5%提成 纯疗程卡记120块的业绩，6块钱的提成                                    
					if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903'  or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008' )                                        
					begin          
						if( isnull(@cardtype,'') ='MR' or isnull(@cardtype,'')='MF')                                        
						begin                                    
							update #work_detail set staffyeji=120*@fuflag where seqno=@tmpSeqId                                           
							set @empTicheng=6*@fuflag                                    
						end                                     
						else                                    
						begin                                         
							update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.8 where seqno=@tmpSeqId                             
							set @empTicheng = isnull(@tmpYeji,0)*0.8*0.05                                    
							if(isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')--四级烫染师                                    
								set @empTicheng = isnull(@tmpYeji,0)*0.8*0.06                                    
						end                                       
					end                                                          
					if(isnull(@empPostion,'')in('004','00401','00402') and ISNULL(@PROJECT_TYPE,'') in ('3','6') and ISNULL(@proflag,2)=1)                                    
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
				else if( (@cardtype ='ZK' and isnull(@paycode,'')='4') or isnull(@paycode,'')='$'  or isnull(@paycode,'')='A' or isnull(@paycode,'')='7' or isnull(@paycode,'')='11'  or isnull(@paycode,'')='12'  )                   
				begin       
					select @newcosttc=newcosttc,@oldcosttc=oldcosttc      
					from  projectinfo ,compchaininfo     
					where prisource=curcomp and relationcomp=@compid and prjno=@tmpPrjId                            
					if(isnull(@tmpYeji,0)>0)                                    
						set @fuflag=1                                    
					else                                    
						set @fuflag=-1 
					if(ISNULL(@newcosttc,0)>0 or ISNULL(@oldcosttc,0)>0)
					begin
						if(  isnull(@tmpItem,'')='7')
							update #work_detail set staffticheng=@oldcosttc*@fuflag  where seqno=@tmpSeqId 
						else   if(  isnull(@tmpItem,'')='8')
							update #work_detail set staffticheng=@newcosttc*@fuflag  where seqno=@tmpSeqId 
						else
							update #work_detail set staffticheng=0 where seqno=@tmpSeqId                                      
						fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan ,@csitemstate                                     
						continue  
					end
					if(@tmpPrjId in ('321','322','323','324','325','326','327','328','329','330','331','332'))
					begin
						if(  ISNULL(@csitemstate,1)=1)
							update #work_detail set staffticheng=10*@fuflag  where seqno=@tmpSeqId 
						else   if( ISNULL(@csitemstate,1)=2)
							update #work_detail set staffticheng=5*@fuflag  where seqno=@tmpSeqId 
						else
							update #work_detail set staffticheng=0 where seqno=@tmpSeqId                                      
						fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan ,@csitemstate                                     
						continue 
					end                                   
					--项目抵用券使用面值做业绩                                    
					if(isnull(@paycode,'')='11' )                                    
					begin          
						select @tmpYeji=ISNULL(cardfaceamt,0) from nointernalcardinfo where cardno=@cardtype                            
						if(ISNULL(@fuflag,0)<0)                          
							set  @tmpYeji=ISNULL(@tmpYeji,0)*(-1)                                 
						update #work_detail set staffyeji=@tmpYeji,billamt=@tmpYeji where seqno=@tmpSeqId                                      
					end                                     
					--烫染师 记业绩24% 提成5%                                    
					if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903' or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                                        
					begin                                    
						update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.24 where seqno=@tmpSeqId                                      
						set @empTicheng = isnull(@tmpYeji,0)*0.24*0.05                                      
						if( isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')              
							set @empTicheng = isnull(@tmpYeji,0)*0.24*0.06                                      
					end                                      
					--首席，总监，设计师记业绩24%,提成22% 美容师记业绩24%,提成22%                                     
					else                                     
					begin                                    
						update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.24 where seqno=@tmpSeqId                                      
						set @empTicheng = isnull(@tmpYeji,0)*0.24*0.22                                    
					end                                      
					---洗剪吹项目 美发师扣除0.25的成本 总监扣除0.11的成本 首席扣除0.15的成本                                    
					---美发项目美容师参与的干洗20的业绩，水系5 提成系数0.3       
                           
					if(@tmpPrjId in ('300','3002','301','302','303','305','306','309','311','321','322','323','324','325','326','327','328','329','330','331','332'))                                    
					begin                                    
						if(isnull(@empPostion,'')in('004','00401','00402') or isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903' or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                            
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
					select @proflag=isnull(prjpricetype,2),@PROJECT_COST=isnull(costprice,0),@Performance_Ratio=isnull(kyjrate,1),@Wage_Rates=isnull(ktcrate,1),@PROJECT_TYPE=prjtype,@newcosttc=newcosttc,@oldcosttc=oldcosttc       
					from  projectinfo ,compchaininfo     
					where prisource=curcomp and relationcomp=@compid  and prjno=@tmpPrjId 
					if(ISNULL(@newcosttc,0)>0 or ISNULL(@oldcosttc,0)>0)
					begin
						if(  isnull(@tmpItem,'')='7')
							update #work_detail set staffticheng=@oldcosttc*@fuflag  where seqno=@tmpSeqId 
						else   if(  isnull(@tmpItem,'')='8')
							update #work_detail set staffticheng=@newcosttc*@fuflag  where seqno=@tmpSeqId 
						else
							update #work_detail set staffticheng=0 where seqno=@tmpSeqId                                      
						fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan ,@csitemstate                                     
						continue  
					end
					if(@tmpPrjId in ('321','322','323','324','325','326','327','328','329','330','331','332') and isnull(@tmpItem,'') not in ('7','8','9') )
					begin
						if(  ISNULL(@csitemstate,1)=1)
							update #work_detail set staffticheng=10*@fuflag  where seqno=@tmpSeqId 
						else   if( ISNULL(@csitemstate,1)=2)
							update #work_detail set staffticheng=5*@fuflag  where seqno=@tmpSeqId 
						else
							update #work_detail set staffticheng=0 where seqno=@tmpSeqId                                      
						fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan ,@csitemstate                                     
						continue 
					end     
					if(@PROJECT_TYPE<>'5')--美甲成本单独算                      
					begin                      
						update #work_detail set staffyeji=(isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*isnull(@Performance_Ratio,0) where seqno=@tmpSeqId                                           
					end                      
					set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*@Performance_Ratio*@Wage_Rates                                          
					--首席总监 扣除成本后×业绩比率，提成 30%                                    
					if((isnull(@empPostion,'')='006' or isnull(@empPostion,'') ='007' or isnull(@empPostion,'') ='00701' or isnull(@empPostion,'') ='00702') and ISNULL(@PROJECT_TYPE,'0')<>'6' )                                        
						set @empTicheng = (isnull(@tmpYeji,0)*isnull(@PROJECT_COST,0))*@Performance_Ratio*0.3                                         
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
								update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.75 where seqno=@tmpSeqId                                      
								set @empTicheng = isnull(@tmpYeji,0)*0.75*0.28                                      
							end                                     
							else if(isnull(@empPostion,'')='006') --美发师                                    
							begin                                    
								update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.85 where seqno=@tmpSeqId                                      
								set @empTicheng = isnull(@tmpYeji,0)*0.85*0.3                                      
							end                                 
							else if(isnull(@empPostion,'')='007' or isnull(@empPostion,'')='00701' or isnull(@empPostion,'')='00702') --美发师                                    
							begin                                    
								update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.89 where seqno=@tmpSeqId                                      
								set @empTicheng = isnull(@tmpYeji,0)*0.89*0.3                                      
							end                                     
							else if(isnull(@empPostion,'')in('004','00401','00402') or isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903' or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                       
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
					if(isnull(@paycode,'')='13' )                                    
					begin                                    
						declare @tmcardfrom int --0 正常开卡,1 赠送开卡                                    
						select @tmcardfrom=ISNULL(entrytype,0) from nointernalcardinfo where cardno=@cardtype                                    
						if(ISNULL(@tmcardfrom,0)=1)                                    
						begin                                    
							--烫染师 记业绩24% 提成5%                                    
							if(isnull(@empPostion,'')='00901' or isnull(@empPostion,'')='00902'  or isnull(@empPostion,'')='00903' or isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                                        
							begin                                    
								update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.24 where seqno=@tmpSeqId                                      
								set @empTicheng = isnull(@tmpYeji,0)*0.24*0.05                                      
								if( isnull(@empPostion,'')='00904' or isnull(@empPostion,'')='008')                                    
									set @empTicheng = isnull(@tmpYeji,0)*0.24*0.06                                      
							end                                      
							--首席，总监，设计师记业绩24%,提成22% 美容师记业绩24%,提成22%                                     
							else                                     
							begin                                    
								update #work_detail set staffyeji=isnull(@tmpYeji,0)*0.24 where seqno=@tmpSeqId                                      
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
                                          
					if(isnull(@empPostion,'') in('004','00401','00402')  and @tmpPrjId in ('48811') and isnull(@paycode,'')<>'13')                                    
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
					if(isnull(@empPostion,'')in('004','00401','00402') and ISNULL(@PROJECT_TYPE,'') in ('3','6') and ISNULL(@proflag,2)=1)                                    
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
	fetch cur_yeji_ticheng into @tmpSeqId,@tmpEmpId,@tmpItem,@tmpYeji,@tmpPrjId,@tmpDate ,@paycode ,@cardtype,@quan  ,@csitemstate                              
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
  select  person_inid,sum(isnull(staffyeji,0)) from #work_detail where ISNULL(action_id,-1)<>4 and paycode in ('1','2','6','0','14','15') group by person_inid                                  
                                     
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
                           
                     
                   
  ---美容师A类                  
  if(@compid in ('008','017','019','026','032'))                  
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
 end                  
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
        
 select seqno,person_inid,action_id,srvdate,code,name,payway,billamt,ccount,cost,staffticheng,staffyeji,prj_type,cls_flag,billid,paycode,compid,cardid,cardtype     
 from #work_detail order by action_id,srvdate    
     
    
 drop table #empinfobydate    
    drop table #work_detail     
       
end 