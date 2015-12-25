alter procedure upg_compute_comp_classed_trade        
(    
 @compid   varchar(10),  --门店号     
 @datefrom  varchar(10),  --起始日期    
 @dateto   varchar(10),  --结束日期    
 @usetype  int     --查询类型 1 显示 2被调用    
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
 
  declare @sp102 varchar(2)
  select @sp102=paramvalue from sysparaminfo where   compid=@compid and paramid='SP102'                                   
                                      
 --11.项目消费美容类业绩; 12.项目消费美发类业绩；13.项目消费总业绩;14.项目消费足疗类业绩；15.项目消费美甲类业绩                                      
 --21.售美容类产品业绩;   22.售美发类产品业绩；  23.售产品总业绩；                                      
 --31.开卡美容类业绩；  32.开卡美发类业绩； 33.开卡总业绩;    34.开卡足疗类业绩；    35.开卡美甲类业绩                                      
 --41.帐户异动美容类业绩；42.帐户异动美发类业绩；43.帐户异动总业绩 44.帐户异动足疗类业绩；45.帐户异动美甲类业绩                                      
 --51.卡异动美容类业绩；  52.卡异动美发类业绩；  53.卡异动总业绩   54.卡异动足疗类业绩；  55.卡异动美甲类业绩                                      
                                      
 --81.项目消费美容类实业绩；82.项目消费美发类实业绩；83.项目消费总实业绩；84.项目消费足疗类实业绩；85.项目消费美甲类实业绩                                      
 --91.售美容类产品实业绩；  92.售美发类产品实业绩；  93.售产品总实业绩；       
     
 --系统业绩表    
 create table #yeji_result(                                      
  compid   varchar(10)   not null,  --门店编号                                     
  id    int     identity,  -- 流水号                                      
  item   varchar(5)    null,  -- 业绩项目类别                                      
  yeji   float     null,  -- 业绩                                      
  datefrom  varchar(8)    null,  -- 开始日期                                      
  dateto   varchar(8)    null,  -- 截止日期                                      
 )     
 --项目表                                     
 create table #prj_cls(                                      
  compid   varchar(10)  null,                                      
  prjid   varchar(20)  null,--项目编号                                      
  prjcls   varchar(10)  null--项目类别                                      
  )                                      
 create clustered index idx_clust_prj_cls on #prj_cls(compid,prjcls,prjid)          
                                 
 insert into #prj_cls(compid,prjid,prjcls)                                      
 select relationcomp,prjno,prjtype from projectnameinfo,compchaininfo where curcomp=@compid                             
         
    --产品表                                 
 create table #goods_cls(                     
  compid   varchar(10)  null,                        
  goodsid   varchar(20)  null,--物品编号                
  goodscls  varchar(10)  null--物品类别                                  
  )                                      
 create clustered index idx_clust_goods_cls on #goods_cls(compid,goodsid,goodscls)     
                                      
 insert into #goods_cls(compid,goodsid,goodscls)                                      
 select relationcomp,goodsno,goodstype from goodsnameinfo with(NOLOCK),compchaininfo where curcomp=@compid                                       
                       
 create table #emp_dep(                                    
  compid  varchar(10)   null,                                    
  inid  varchar(20)   null,--内部编号                                   
  empid  varchar(20)   null,--员工编号                                    
  empdep  varchar(10)   null,--员工部门                                    
  datefrom varchar(8)   null,                                    
  dateto  varchar(8)   null,                                    
 )                                    
 create clustered index idx_tmp_emp_dep on #emp_dep(compid,empid,datefrom,dateto)         
                                
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
 exec upg_get_empinfo_by_date_comps @compid,@datefrom,@dateto                        
                    
 insert into #emp_dep(compid,inid,empid,empdep,datefrom,dateto)                                    
 select distinct compid,inid,empid,department,datefrom,dateto                                    
 from #empinfobydate,staffinfo with (nolock)                                    
 where inid=manageno and isnull(inid,'')<>'' and ISNULL(stafftype,0)=0                
                
 update #emp_dep set empdep=olddepid from #emp_dep,staffhistory where effectivedate > datefrom and effectivedate<= dateto and manageno=inid       
     
     
  --计算项目消费虚业绩                                      
 create table #m_d_consumeinfo(                                      
   cscompid				varchar(10)     NULL,   --公司编号                                      
   csbillid				varchar(20)		NULL,   --消费单号                                   
   financedate			varchar(8)		NULL,   --帐务日期     
   csinfotype			int             NULL, --消费类型  1 项目  2 产品     
   csitemno				varchar(20)     NULL,   --项目代码 
   cscardtype			varchar(10)		null,	--消费卡类型 
   tiaomacardno			varchar(20)		NUlL	,	--条码卡号                                    
   csitemamt			float           NULL,   --金额                                      
   csfirstsaler			varchar(20)     NULL,   --大工代码                                      
   csfirstinid			varchar(20)		NULL,   --大工内部编号      
   csfirstshare			float           NULL, --大工分享                                  
   cssecondsaler		varchar(20)     NULL,   --中工代码                                      
   cssecondinid			varchar(20)		NULL,   --中工内部编号     
   cssecondshare		float           NULL, --中工分享                                   
   csthirdsaler			varchar(20)     NULL,   --小工代码            
   csthirdinid			varchar(20)		NULL,   --小工内部编号     
   csthirdshare			float           NULL, --小工分享                          
   cspaymode			varchar(5)      NULL   --项目支付方式                                         
 )      
 create nonclustered index index_m_d_consumeinfo_csitemno  on #m_d_consumeinfo(cscompid,csinfotype,csitemno)      
 create nonclustered index index_m_d_consumeinfo_financedate  on #m_d_consumeinfo(cscompid,csinfotype,financedate)      
      
  insert into #m_d_consumeinfo(cscompid,csbillid,csinfotype,financedate,cscardtype,tiaomacardno,csitemno,csitemamt,csfirstsaler,csfirstinid,cssecondsaler,cssecondinid,csthirdsaler,csthirdinid,cspaymode,csfirstshare,cssecondshare,csthirdshare)                                    
  
  select a.cscompid,a.csbillid,csinfotype,a.financedate,a.cscardtype,a.tiaomacardno,csitemno,csitemamt,csfirstsaler,csfirstinid,cssecondsaler,cssecondinid,csthirdsaler,csthirdinid,cspaymode,csfirstshare,cssecondshare,csthirdshare                                      
    from mconsumeinfo a WITH (NOLOCK),dconsumeinfo b with (nolock)                                      
   where a.cscompid=b.cscompid                                      
  and a.csbillid=b.csbillid                                      
  and a.cscompid = @compid                                      
  and financedate>=@datefrom                                       
  and financedate<=@dateto                                        
 --11.项目消费美容类业绩; 12.项目消费美发类业绩；13.项目消费总业绩                                      
 --81.项目消费美容类实业绩；82.项目消费美发类实业绩；83.项目消费总实业绩；84.项目消费足疗类实业绩；85.项目消费美甲类实业绩                                      
 --------------------------------------------计算项目虚实业绩----------Start-----------------------------------------------    
   insert into #yeji_result(compid,item,yeji)                                      
   select cscompid,'11',isnull(sum(csitemamt),0)                                      
   from #m_d_consumeinfo,#prj_cls                                      
   where cscompid=compid                                       
     and csitemno=prjid                                       
     and prjcls=@PRJ_BEAUT_CLASS_CODE                                      
     and isnull(cspaymode,'') in ('1','2','6','14','15','16')     
     and isnull(csinfotype,1)=1                                      
   group by cscompid                                    
                                       
   insert into #yeji_result(compid,item,yeji)                                      
   select cscompid,'12',isnull(sum(csitemamt),0)                                      
   from #m_d_consumeinfo,#prj_cls                                      
   where cscompid=compid                                       
     and csitemno=prjid                                       
     and (prjcls=@PRJ_HAIR_CLASS_CODE   or    prjcls= @PRJ_FOOT_CLASS_CODE)                                
     and isnull(cspaymode,'') in ('1','2','6','14','15','16')    
     and isnull(csinfotype,1)=1                                            
   group by cscompid                           
                           
   insert into #yeji_result(compid,item,yeji)                                      
   select cscompid,'13',isnull(sum(isnull(csitemamt,0) ),0)                                      
   from #m_d_consumeinfo                                      
   where isnull(cspaymode,'') in ('1','2','6','14','15','16')      
   and isnull(csinfotype,1)=1               
   group by cscompid                                    
       
   insert into #yeji_result(compid,item,yeji)                                      
   select cscompid,'15',isnull(sum(csitemamt),0)                                      
   from #m_d_consumeinfo,#prj_cls                                      
   where cscompid=compid                                       
     and csitemno=prjid                                       
     and prjcls=@PRJ_FINGER_CLASS_CODE                                      
     and isnull(cspaymode,'') in ('1','2','6','16')      
     and isnull(csinfotype,1)=1                                    
   group by cscompid           
                            
   insert into #yeji_result(compid,item,yeji)         
   select cscompid,'81',isnull(sum(csitemamt),0)                                      
   from #m_d_consumeinfo,#prj_cls                                  
   where cscompid=compid                                       
     and csitemno=prjid                                       
     and prjcls=@PRJ_BEAUT_CLASS_CODE             
     and isnull(csinfotype,1)=1 
     and isnull(cspaymode,'') in ('1','2','6','14','15','16')                                         
   group by cscompid                                      
                                         
   insert into #yeji_result(compid,item,yeji)                                      
   select cscompid,'82',isnull(sum(csitemamt),0)                              
   from #m_d_consumeinfo,#prj_cls                                      
   where cscompid=compid                                       
     and csitemno=prjid                                       
     and (prjcls=@PRJ_HAIR_CLASS_CODE   or    prjcls= @PRJ_FOOT_CLASS_CODE)       
     and isnull(csinfotype,1)=1   
     and isnull(cspaymode,'') in ('1','2','6','14','15','16')                                               
   group by cscompid                                      
                                                   
                                        
   insert into #yeji_result(compid,item,yeji)                                      
   select cscompid,'83',isnull(sum(isnull(csitemamt,0) ),0)     
   from #m_d_consumeinfo       
   where isnull(csinfotype,1)=1   
   and isnull(cspaymode,'') in ('1','2','6','14','15','16')                                       
   group by cscompid             
           
   insert into #yeji_result(compid,item,yeji)                                      
   select cscompid,'84',isnull(sum(csitemamt),0)                              
   from #m_d_consumeinfo,#prj_cls                                      
   where cscompid=compid                                       
     and csitemno=prjid                                       
     and (prjcls=@PRJ_HAIR_CLASS_CODE   or    prjcls= @PRJ_FOOT_CLASS_CODE)         
     and cspaymode='9'                          
     and isnull(csinfotype,1)=1  
     and isnull(cspaymode,'') in ('1','2','6','14','15','16')                   
   group by cscompid         
                            
   insert into #yeji_result(compid,item,yeji)                                      
   select cscompid,'85',isnull(sum(csitemamt),0)                                  
   from #m_d_consumeinfo,#prj_cls                                      
   where cscompid=compid                                       
     and csitemno=prjid                                       
     and prjcls=@PRJ_FINGER_CLASS_CODE        
     and isnull(csinfotype,1)=1  
     and isnull(cspaymode,'') in ('1','2','6','14','15','16')                                    
   group by cscompid   
   
   
   insert into #yeji_result(compid,item,yeji)         
   select cscompid,'81',isnull(sum(csitemamt),0)                                      
   from #m_d_consumeinfo,#prj_cls                                  
   where cscompid=compid                                       
     and csitemno=prjid                                       
     and prjcls=@PRJ_BEAUT_CLASS_CODE             
     and isnull(csinfotype,1)=1 
     and isnull(cspaymode,'') in ('4','9','17') 
     and ISNULL(cscardtype,'')<>'ZK'                                    
   group by cscompid                                      
                                         
   insert into #yeji_result(compid,item,yeji)                                      
   select cscompid,'82',isnull(sum(csitemamt),0)                              
   from #m_d_consumeinfo,#prj_cls                                      
   where cscompid=compid                                       
     and csitemno=prjid                                       
     and (prjcls=@PRJ_HAIR_CLASS_CODE   or    prjcls= @PRJ_FOOT_CLASS_CODE)       
     and isnull(csinfotype,1)=1   
     and isnull(cspaymode,'') in ('4','9','17') 
     and ISNULL(cscardtype,'')<>'ZK'                                         
   group by cscompid                                      
                                                   
                                        
   insert into #yeji_result(compid,item,yeji)                                      
   select cscompid,'83',isnull(sum(isnull(csitemamt,0) ),0)     
   from #m_d_consumeinfo       
   where isnull(csinfotype,1)=1   
   and isnull(cspaymode,'') in ('4','9','17')
   and ISNULL(cscardtype,'')<>'ZK'                                         
   group by cscompid             
           
   insert into #yeji_result(compid,item,yeji)                                      
   select cscompid,'84',isnull(sum(csitemamt),0)                              
   from #m_d_consumeinfo,#prj_cls                                      
   where cscompid=compid                                       
     and csitemno=prjid                                       
     and (prjcls=@PRJ_HAIR_CLASS_CODE   or    prjcls= @PRJ_FOOT_CLASS_CODE)         
     and cspaymode='9'                          
     and isnull(csinfotype,1)=1  
     and isnull(cspaymode,'') in ('4','9','17')
     and ISNULL(cscardtype,'')<>'ZK'                     
   group by cscompid         
                            
   insert into #yeji_result(compid,item,yeji)                                      
   select cscompid,'85',isnull(sum(csitemamt),0)                                  
   from #m_d_consumeinfo,#prj_cls                                      
   where cscompid=compid                                       
     and csitemno=prjid                                       
     and prjcls=@PRJ_FINGER_CLASS_CODE        
     and isnull(csinfotype,1)=1  
     and isnull(cspaymode,'') in ('4','9','17') 
     and ISNULL(cscardtype,'')<>'ZK'                                     
   group by cscompid    
   
   
    insert into #yeji_result(compid,item,yeji)         
   select cscompid,'81',isnull(sum(csitemamt),0)                                      
   from #m_d_consumeinfo,#prj_cls ,nointernalcardinfo d                                 
   where cscompid=compid                                       
     and csitemno=prjid                                       
     and prjcls=@PRJ_BEAUT_CLASS_CODE             
     and isnull(csinfotype,1)=1 
     and isnull(cspaymode,'') ='13'
     and ISNULL(tiaomacardno,'')=d.cardno and ISNULL(cardtype,0)=2 and ISNULL(entrytype,0)=0                                    
   group by cscompid                                      
                                         
   insert into #yeji_result(compid,item,yeji)                                      
   select cscompid,'82',isnull(sum(csitemamt),0)                              
   from #m_d_consumeinfo,#prj_cls  ,nointernalcardinfo d                                          
   where cscompid=compid                                       
     and csitemno=prjid                                       
     and (prjcls=@PRJ_HAIR_CLASS_CODE   or    prjcls= @PRJ_FOOT_CLASS_CODE)       
     and isnull(csinfotype,1)=1   
     and isnull(cspaymode,'') ='13'
     and ISNULL(tiaomacardno,'')=d.cardno and ISNULL(cardtype,0)=2 and ISNULL(entrytype,0)=0                                         
   group by cscompid                                      
                                                   
                                        
   insert into #yeji_result(compid,item,yeji)                                      
   select cscompid,'83',isnull(sum(isnull(csitemamt,0) ),0)     
   from #m_d_consumeinfo   ,nointernalcardinfo d          
   where isnull(csinfotype,1)=1   
   and isnull(cspaymode,'') ='13'
   and ISNULL(tiaomacardno,'')=d.cardno and ISNULL(cardtype,0)=2 and ISNULL(entrytype,0)=0                                          
   group by cscompid             
           
   insert into #yeji_result(compid,item,yeji)                                      
   select cscompid,'84',isnull(sum(csitemamt),0)                              
   from #m_d_consumeinfo,#prj_cls ,nointernalcardinfo d                                           
   where cscompid=compid                                       
     and csitemno=prjid                                       
     and (prjcls=@PRJ_HAIR_CLASS_CODE   or    prjcls= @PRJ_FOOT_CLASS_CODE)         
     and cspaymode='9'                          
     and isnull(csinfotype,1)=1  
     and isnull(cspaymode,'') ='13'
     and ISNULL(tiaomacardno,'')=d.cardno and ISNULL(cardtype,0)=2 and ISNULL(entrytype,0)=0                       
   group by cscompid         
                            
   insert into #yeji_result(compid,item,yeji)                                      
   select cscompid,'85',isnull(sum(csitemamt),0)                                  
   from #m_d_consumeinfo,#prj_cls  ,nointernalcardinfo d                                          
   where cscompid=compid                                       
     and csitemno=prjid                                       
     and prjcls=@PRJ_FINGER_CLASS_CODE        
     and isnull(csinfotype,1)=1  
     and isnull(cspaymode,'')='13'
     and ISNULL(tiaomacardno,'')=d.cardno and ISNULL(cardtype,0)=2 and ISNULL(entrytype,0)=0                                       
   group by cscompid          
       
    --------------------------------------------计算项目虚实业绩-------END--------------------------------------------------    
        
    --21.售美容类产品业绩;   22.售美发类产品业绩；  23.售产品总业绩                                      
 --91.售美容类产品实业绩；  92.售美发类产品实业绩；  93.售产品总实业绩；                                      
                           
 --------------------------------------------计算产品虚实业绩-------Start------------------------------------------------    
    insert into #yeji_result(compid,item,yeji)                                    
    select cscompid,case when empdep=@DEP_BEAUT_CODE  then '21'    
        when empdep=@DEP_HAIR_CODE  then '22'    
        when empdep=@DEP_FOOT_CODE  then '24x'    
        when empdep=@DEP_FINGER_CODE    then '25' end ,isnull(sum(csfirstshare),0)                                      
    from #m_d_consumeinfo,#emp_dep                                      
    where inid=csfirstinid                                      
   and isnull(cspaymode,'') in ('1','2','6','14','15','16')                                    
   and financedate>=datefrom and financedate<dateto    and cscompid=compid      
   and isnull(csinfotype,1)=2                              
    group by cscompid,empdep                                      
                                        
     insert into #yeji_result(compid,item,yeji)                                    
    select cscompid,case when empdep=@DEP_BEAUT_CODE  then '21'    
        when empdep=@DEP_HAIR_CODE  then '22'    
        when empdep=@DEP_FOOT_CODE then '24x'    
        when empdep=@DEP_FINGER_CODE    then '25' end,isnull(sum(cssecondshare),0)                                      
    from #m_d_consumeinfo,#emp_dep                                      
    where inid=cssecondinid                                       
   and isnull(cspaymode,'') in ('1','2','6','14','15','16')                                  
   and financedate>=datefrom and financedate<dateto   and cscompid=compid     
   and isnull(csinfotype,1)=2                                    
    group by cscompid,empdep                                     
                                        
     insert into #yeji_result(compid,item,yeji)                                    
    select cscompid,case when empdep=@DEP_BEAUT_CODE  then '21'    
        when empdep=@DEP_HAIR_CODE  then '22'    
        when empdep=@DEP_FOOT_CODE  then '24x'    
        when empdep=@DEP_FINGER_CODE    then '25' end,isnull(sum(csthirdshare),0)                                      
    from #m_d_consumeinfo,#emp_dep                                      
    where inid=csthirdinid                                      
     and isnull(cspaymode,'') in ('1','2','6','14','15','16')                                    
     and financedate>=datefrom and financedate<dateto  and cscompid=compid     
     and isnull(csinfotype,1)=2                                   
    group by cscompid,empdep                                     
                                        
                                  
                        
    insert into #yeji_result(compid,item,yeji)                                      
   select cscompid,'23',sum(isnull(csitemamt,0))                                     
   from #m_d_consumeinfo                                      
   where isnull(cspaymode,'') in ('1','2','6','14','15','16')    
   and isnull(csinfotype,1)=2              
   group by cscompid                           
                           
                           
   insert into #yeji_result(compid,item,yeji)                                    
    select cscompid,'24',isnull(sum(csfirstshare),0)                                      
    from #m_d_consumeinfo,#emp_dep                                      
    where inid=csfirstinid and empdep=@DEP_FOOT_CODE                                      
   --and isnull(cspaymode,'') in ('1','2','6','14','15')                                    
   and financedate>=datefrom and financedate<dateto    and cscompid=compid     
   and isnull(csinfotype,1)=2                                   
    group by cscompid                         
                                  
                                        
     insert into #yeji_result(compid,item,yeji)                                    
    select cscompid,'24',isnull(sum(cssecondshare),0)                                      
    from #m_d_consumeinfo,#emp_dep                                      
    where inid=cssecondinid and empdep=@DEP_FOOT_CODE                                      
   --and isnull(cspaymode,'') in ('1','2','6','14','15')                                  
   and financedate>=datefrom and financedate<dateto and cscompid=compid      
    and isnull(csinfotype,1)=2                                     
    group by cscompid                         
                 
     insert into #yeji_result(compid,item,yeji)                                    
    select cscompid,'24',isnull(sum(csthirdshare),0)                                      
    from #m_d_consumeinfo,#emp_dep                                      
    where inid=csthirdinid and empdep=@DEP_FOOT_CODE                                      
    -- and isnull(cspaymode,'') in ('1','2','6','14','15')                         
     and financedate>=datefrom and financedate<dateto     and cscompid=compid       
      and isnull(csinfotype,1)=2                                
    group by cscompid                        
                
                           
                                       
                                       
                                       
     insert into #yeji_result(compid,item,yeji)                                   
    select cscompid,case when empdep=@DEP_BEAUT_CODE  then '91'    
        when empdep=@DEP_HAIR_CODE  then '92'    
        when empdep=@DEP_FOOT_CODE then '94'    
        when empdep=@DEP_FINGER_CODE    then '95' end,isnull(sum(csfirstshare),0)                                      
    from #m_d_consumeinfo,#emp_dep                                      
    where inid=csfirstinid                               
    and financedate>=datefrom and financedate<dateto    and cscompid=compid        
     and isnull(csinfotype,1)=2   
     and isnull(cspaymode,'') in ('1','2','6','14','15','16')                                     
    group by cscompid,empdep                                   
                                            
     insert into #yeji_result(compid,item,yeji)                                    
    select cscompid,case when empdep=@DEP_BEAUT_CODE  then '91'    
        when empdep=@DEP_HAIR_CODE  then '92'    
        when empdep=@DEP_FOOT_CODE then '94'    
        when empdep=@DEP_FINGER_CODE    then '95' end,isnull(sum(cssecondshare),0)                                      
    from #m_d_consumeinfo,#emp_dep                                      
    where inid=cssecondinid                                 
    and financedate>=datefrom and financedate<dateto     and cscompid=compid    
     and isnull(csinfotype,1)=2    
     and isnull(cspaymode,'') in ('1','2','6','14','15','16')                                      
    group by cscompid,empdep                                     
                             
     insert into #yeji_result(compid,item,yeji)                                    
    select cscompid,case when empdep=@DEP_BEAUT_CODE  then '91'    
        when empdep=@DEP_HAIR_CODE  then '92'    
        when empdep=@DEP_FOOT_CODE  then '94'    
        when empdep=@DEP_FINGER_CODE    then '95' end,isnull(sum(csthirdshare),0)                                      
    from #m_d_consumeinfo,#emp_dep                                      
    where inid=csthirdinid                  
    and financedate>=datefrom and financedate<dateto    and cscompid=compid       
     and isnull(csinfotype,1)=2    
     and isnull(cspaymode,'') in ('1','2','6','14','15','16')                                      
    group by cscompid ,empdep                                  
                                        
          
                                       
    insert into #yeji_result(compid,item,yeji)                                      
   select cscompid,'93',ISNULL(SUM(csitemamt),0)--isnull(sum(isnull(csfirstshare,0)+isnull(cssecondshare,0)+isnull(csthirdshare,0)),0)                                      
   from #m_d_consumeinfo         
   where isnull(csinfotype,1)=2 
   and isnull(cspaymode,'') in ('1','2','6','14','15','16')                                          
   group by cscompid                
   
   
   
   
    insert into #yeji_result(compid,item,yeji)                                   
    select cscompid,case when empdep=@DEP_BEAUT_CODE  then '91'    
        when empdep=@DEP_HAIR_CODE  then '92'    
        when empdep=@DEP_FOOT_CODE then '94'    
        when empdep=@DEP_FINGER_CODE    then '95' end,isnull(sum(csfirstshare),0)                                      
    from #m_d_consumeinfo,#emp_dep                                      
    where inid=csfirstinid                               
    and financedate>=datefrom and financedate<dateto    and cscompid=compid        
     and isnull(csinfotype,1)=2   
    and isnull(cspaymode,'') in ('4','9','17') 
     and ISNULL(cscardtype,'')<>'ZK'                                   
    group by cscompid,empdep                                   
                                            
     insert into #yeji_result(compid,item,yeji)                                    
    select cscompid,case when empdep=@DEP_BEAUT_CODE  then '91'    
        when empdep=@DEP_HAIR_CODE  then '92'    
        when empdep=@DEP_FOOT_CODE then '94'    
        when empdep=@DEP_FINGER_CODE    then '95' end,isnull(sum(cssecondshare),0)                                      
    from #m_d_consumeinfo,#emp_dep                                      
    where inid=cssecondinid                                 
    and financedate>=datefrom and financedate<dateto     and cscompid=compid    
     and isnull(csinfotype,1)=2    
      and isnull(cspaymode,'') in ('4','9','17') 
     and ISNULL(cscardtype,'')<>'ZK'                                     
    group by cscompid,empdep                                     
                             
     insert into #yeji_result(compid,item,yeji)                                    
    select cscompid,case when empdep=@DEP_BEAUT_CODE  then '91'    
        when empdep=@DEP_HAIR_CODE  then '92'    
        when empdep=@DEP_FOOT_CODE  then '94'    
        when empdep=@DEP_FINGER_CODE    then '95' end,isnull(sum(csthirdshare),0)                                      
    from #m_d_consumeinfo,#emp_dep                                      
    where inid=csthirdinid                  
    and financedate>=datefrom and financedate<dateto    and cscompid=compid       
     and isnull(csinfotype,1)=2    
     and isnull(cspaymode,'') in ('4','9','17') 
     and ISNULL(cscardtype,'')<>'ZK'                                       
    group by cscompid ,empdep                                  
                                        
          
                                       
    insert into #yeji_result(compid,item,yeji)                                      
   select cscompid,'93',ISNULL(SUM(csitemamt),0)--isnull(sum(isnull(csfirstshare,0)+isnull(cssecondshare,0)+isnull(csthirdshare,0)),0)                                      
   from #m_d_consumeinfo         
   where isnull(csinfotype,1)=2 
   and isnull(cspaymode,'') in ('4','9','17') 
   and ISNULL(cscardtype,'')<>'ZK'                                          
   group by cscompid                                        
                              
              
 --------------------------------------------计算产品虚实业绩-------END--------------------------------------------------    
    drop table #m_d_consumeinfo    
      
  CREATE TABLE #msalecardinfo  -- 会员卡销售单                                      
  (                                      
    salecompid   varchar(10)   NULL,  --公司编号                                      
    salebillid   varchar(20)   NULL,  --销售单号                                      
    firstsalerinid  varchar(20)   NULL,  --负责业务                                      
    firstsaleamt   float    NULL,  --第一销售虚业绩                                      
    secondsalerinid  varchar(20)   NULL    ,   --第二卖卡人                                      
    secondsaleamt   float    NULL    , --第二销售虚业绩                                       
    thirdsalerinid  varchar(20)   NULL    ,   --第三卖卡人                                      
    thirdsaleamt   float    NULL    , --第三销售虚业绩                                       
    fourthsalerinid  varchar(20)   NULL    ,   --第4卖卡人                                      
    fourthsaleamt   float    NULL    , --第四人员业绩                          
    fifthsalerinid  varchar(20)   NULL    ,   --第5卖卡人                                      
    fifthsaleamt   float    NULL    , --第五人员业绩                                      
    sixthsalerinid  varchar(20)   NULL    ,   --第6卖卡人                                      
    sixthsaleamt   float    NULL    , --第六人员业绩                                      
    seventhsalerinid  varchar(20)   NULL    ,   --第7卖卡人                                      
    seventhsaleamt  float    NULL    , --第七人员业绩                                      
    eighthsalerinid  varchar(20)   NULL    ,   --第8卖卡人                                      
    eighthsaleamt   float    NULL    , --第八人员业绩     
    ninthsalerinid  varchar(20)   NULL    ,   --第9卖卡人                                      
    ninthsaleamt   float    NULL    , --第9人员业绩      
    tenthsalerinid  varchar(20)   NULL    ,   --第10卖卡人                                      
    tenthsaleamt   float    NULL    , --第10人员业绩                                 
    financedate   varchar(8)   NULL ,  --帐务日期                                      
    salebakflag   int     NULL ,              
    billinsertype		int						NULL,	--充值主办方 1 美容 2 美发
    firstsalecashamt		float					NULL,   --第一销售分享金额(实际现金业绩)
    secondsalecashamt		float					NULL,   --第二销售分享金额(实际现金业绩)
    thirdsalecashamt		float					NULL,   --第三销售分享金额(实际现金业绩)
    fourthsalecashamt		float					NULL,   --第四销售分享金额(实际现金业绩)
    fifthsalecashamt		float					NULL,   --第五销售分享金额(实际现金业绩)
    sixthsalecashamt		float					NULL,   --第六销售分享金额(实际现金业绩)
    seventhsalecashamt		float					NULL,   --第七销售分享金额(实际现金业绩)
    eighthsalecashamt		float					NULL,   --第八销售分享金额(实际现金业绩)
    ninthsalecashamt		float					NULL,   --第九销售分享金额(实际现金业绩)
    tenthsalecashamt		float					NULL,   --第十销售分享金额(实际现金业绩)                        
  )    
  create clustered index index_msalecardinfo on #msalecardinfo(salecompid,financedate)                                      
    
 insert into #msalecardinfo(salecompid,salebillid,firstsalerinid,firstsaleamt,secondsalerinid,secondsaleamt,thirdsalerinid,thirdsaleamt,    
   fourthsalerinid,fourthsaleamt,fifthsalerinid,fifthsaleamt, sixthsalerinid,sixthsaleamt,seventhsalerinid,seventhsaleamt,    
   eighthsalerinid,eighthsaleamt,ninthsalerinid,ninthsaleamt,tenthsalerinid,tenthsaleamt,
   firstsalecashamt,secondsalecashamt,thirdsalecashamt,fourthsalecashamt,fifthsalecashamt,sixthsalecashamt,seventhsalecashamt,eighthsalecashamt,ninthsalecashamt,tenthsalecashamt,
   financedate,salebakflag,billinsertype)                                         
 select salecompid,salebillid,firstsalerinid,firstsaleamt,secondsalerinid,secondsaleamt,thirdsalerinid,thirdsaleamt,    
   fourthsalerinid,fourthsaleamt,fifthsalerinid,fifthsaleamt, sixthsalerinid,sixthsaleamt,seventhsalerinid,seventhsaleamt,    
   eighthsalerinid,eighthsaleamt,ninthsalerinid,ninthsaleamt,tenthsalerinid,tenthsaleamt,
   firstsalecashamt,secondsalecashamt,thirdsalecashamt,fourthsalecashamt,fifthsalecashamt,sixthsalecashamt,seventhsalecashamt,eighthsalecashamt,ninthsalecashamt,tenthsalecashamt,
   financedate,salebakflag ,case when isnull(@sp102,'0')='1' then ISNULL(billinsertype,0) else 0 end                                      
 from msalecardinfo with (nolock)                                   
 where salecompid=@compid and financedate>=@datefrom and financedate<=@dateto and isnull(salebakflag,0)<>1   and ISNULL(salebakflag,0)=0    
     
 --------------------------------------------计算售卡业绩-------Start------------------------------------------------    
     
   --第1卖卡人业绩                                      
   insert into #yeji_result(compid,item,yeji)                                      
   select salecompid,case when empdep=@DEP_BEAUT_CODE  then '31'    
        when empdep=@DEP_HAIR_CODE   then '32'    
        when empdep=@DEP_FINGER_CODE then '35'  end ,
        isnull(sum(
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3 then isnull(firstsalecashamt,0)
		else isnull(firstsaleamt,0) end ),0)                                             
   from #emp_dep ,#msalecardinfo                                      
   where inid=firstsalerinid  and financedate>=datefrom and financedate<dateto    and salecompid=compid                                      
   group by salecompid ,empdep                                   
                       
                               
                                        
   --第2卖卡人业绩          
   insert into #yeji_result(compid,item,yeji)                                      
   select salecompid,case when empdep=@DEP_BEAUT_CODE  then '31'    
        when empdep=@DEP_HAIR_CODE  then '32'    
        when empdep=@DEP_FINGER_CODE then '35'  end ,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3 then isnull(secondsalecashamt,0)
		else isnull(secondsaleamt,0) end )),0)                                                                          
   from #emp_dep ,#msalecardinfo                                      
   where inid=secondsalerinid   and financedate>=datefrom and financedate<dateto    and salecompid=compid                                      
   group by salecompid ,empdep         
                                   
                    
   --第3卖卡人业绩        
    insert into #yeji_result(compid,item,yeji)                                      
   select salecompid,case when empdep=@DEP_BEAUT_CODE  then '31'    
        when empdep=@DEP_HAIR_CODE  then '32'    
        when empdep=@DEP_FINGER_CODE then '35'  end ,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3 then isnull(thirdsalecashamt,0)
		else 1*isnull(thirdsaleamt,0) end )),0)                                                                          
   from #emp_dep ,#msalecardinfo                                      
   where inid=thirdsalerinid    and financedate>=datefrom and financedate<dateto    and salecompid=compid                                      
   group by salecompid ,empdep      
                                     
                        
   --第4卖卡人业绩       
    insert into #yeji_result(compid,item,yeji)                                      
   select salecompid,case when empdep=@DEP_BEAUT_CODE  then '31'    
        when empdep=@DEP_HAIR_CODE  then '32'    
        when empdep=@DEP_FINGER_CODE then '35'  end ,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3 then isnull(fourthsalecashamt,0) 
		else 1*isnull(fourthsaleamt,0) end )),0)                                                                          
   from #emp_dep ,#msalecardinfo                                      
   where inid=fourthsalerinid    and financedate>=datefrom and financedate<dateto    and salecompid=compid                                      
   group by salecompid ,empdep      
                                      
                                   
   --第5卖卡人业绩         
     insert into #yeji_result(compid,item,yeji)                                      
   select salecompid,case when empdep=@DEP_BEAUT_CODE  then '31'    
        when empdep=@DEP_HAIR_CODE  then '32'    
        when empdep=@DEP_FINGER_CODE then '35'  end ,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3  then isnull(fifthsalecashamt,0) 
		else 1*isnull(fifthsaleamt,0) end )),0)                                                                         
   from #emp_dep ,#msalecardinfo                                      
   where inid=fifthsalerinid    and financedate>=datefrom and financedate<dateto    and salecompid=compid                                      
   group by salecompid ,empdep      
                                    
                      
   --第6卖卡人业绩            
   insert into #yeji_result(compid,item,yeji)                                      
   select salecompid,case when empdep=@DEP_BEAUT_CODE  then '31'    
        when empdep=@DEP_HAIR_CODE  then '32'    
        when empdep=@DEP_FINGER_CODE then '35'  end ,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3  then isnull(sixthsalecashamt,0)
		else 1*isnull(sixthsaleamt,0) end )),0)                                                                          
   from #emp_dep ,#msalecardinfo                                      
   where inid=sixthsalerinid    and financedate>=datefrom and financedate<dateto    and salecompid=compid                                      
   group by salecompid ,empdep      
                               
   --第7卖卡人业绩      
    insert into #yeji_result(compid,item,yeji)                                      
   select salecompid,case when empdep=@DEP_BEAUT_CODE  then '31'    
        when empdep=@DEP_HAIR_CODE  then '32'    
        when empdep=@DEP_FINGER_CODE then '35'  end ,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3 then isnull(seventhsalecashamt,0)
		else 1*isnull(seventhsaleamt,0) end )),0)                                                                          
   from #emp_dep ,#msalecardinfo                                      
   where inid=seventhsalerinid    and financedate>=datefrom and financedate<dateto    and salecompid=compid                                      
   group by salecompid ,empdep     
                             
   --第8卖卡人业绩         
    insert into #yeji_result(compid,item,yeji)                                      
   select salecompid,case when empdep=@DEP_BEAUT_CODE  then '31'    
        when empdep=@DEP_HAIR_CODE  then '32'    
        when empdep=@DEP_FINGER_CODE then '35'  end ,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3 then isnull(eighthsalecashamt,0)
		else 1*isnull(eighthsaleamt,0) end )),0)                                                                          
   from #emp_dep ,#msalecardinfo                                      
   where inid=eighthsalerinid    and financedate>=datefrom and financedate<dateto    and salecompid=compid                                      
   group by salecompid ,empdep     
       
       
    --第9卖卡人业绩         
    insert into #yeji_result(compid,item,yeji)                                      
   select salecompid,case when empdep=@DEP_BEAUT_CODE  then '31'    
        when empdep=@DEP_HAIR_CODE  then '32'    
        when empdep=@DEP_FINGER_CODE then '35'  end ,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3  then isnull(ninthsalecashamt,0)
		else 1*isnull(ninthsaleamt,0) end )),0)                                                                          
   from #emp_dep ,#msalecardinfo                                      
   where inid=ninthsalerinid    and financedate>=datefrom and financedate<dateto    and salecompid=compid                                      
   group by salecompid ,empdep     
       
    --第10卖卡人业绩         
    insert into #yeji_result(compid,item,yeji)                                      
   select salecompid,case when empdep=@DEP_BEAUT_CODE  then '31'    
        when empdep=@DEP_HAIR_CODE  then '32'    
        when empdep=@DEP_FINGER_CODE then '35'  end ,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3  then isnull(tenthsalecashamt,0)
		else 1*isnull(tenthsaleamt,0) end )),0)                                                                          
   from #emp_dep ,#msalecardinfo                                      
   where inid=tenthsalerinid    and financedate>=datefrom and financedate<dateto    and salecompid=compid                                      
   group by salecompid ,empdep     
      
      
   insert into #yeji_result(compid,item,yeji)                                        
   select salecompid,'33',isnull(sum(payamt),0)                                         
   from #msalecardinfo,dpayinfo with(nolock)                                      
   where isnull(salebakflag,0)<>1 and salecompid=paycompid and salebillid=paybillid and paybilltype='SK' and paymode in ('1','2','6','14','15','16')                                       
   group by salecompid                                       
                                        
   drop table #msalecardinfo                 
        
 --------------------------------------------计算售卡业绩-------END--------------------------------------------------                                    
 --41.帐户异动美容类业绩；42.帐户异动美发类业绩；43.帐户异动总业绩                                      
  CREATE TABLE  #mcardrechargeinfo               -- 帐户异动单                                      
  (                                      
    rechargecompid  varchar(10)   NULL,   --公司编号                                       
    rechargebillid  varchar(20)   NULL,   --异动单号                    
    rechargetype   int     NULL,   --异动类别( 0充值 ,1取款 5欠款 6还款)     
    firstsalerinid  varchar(20)   NULL,   --销售人员                                      
    firstsaleamt   float    NULL,   --第一销售虚业绩                                       
    secondsalerinid  varchar(20)   NULL,   --第二销售                                      
    secondsaleamt   float    NULL,   --第二销售虚业绩                                        
    thirdsalerinid  varchar(20)   NULL,   --第三负责人                                  
    thirdsaleamt   float    NULL,   --第三销售虚业绩                                       
    fourthsalerinid  varchar(20)   NULL,   --第4负责人                                      
    fourthsaleamt   float    NULL, --第四人员业绩                                      
    fifthsalerinid  varchar(20)   NULL,   --第5负责人                                      
    fifthsaleamt   float    NULL, --第五人员业绩                                      
    sixthsalerinid  varchar(20)   NULL,   --第6负责人                                      
    sixthsaleamt   float    NULL, --第六人员业绩                                      
    seventhsalerinid  varchar(20)   NULL,   --第7负责人                                      
    seventhsaleamt  float    null, --第七人员业绩                                      
    eighthsalerinid  varchar(20)   NULL,   --第8负责人                                      
    eighthsaleamt   float    NULL, --第八人员业绩      
    ninthsalerinid  varchar(20)   NULL    ,   --第9卖卡人                                      
    ninthsaleamt   float    NULL    , --第9人员业绩      
    tenthsalerinid  varchar(20)   NULL    ,   --第10卖卡人                                      
    tenthsaleamt   float    NULL    , --第10人员业绩      
    backbillid   varchar(20)   NULL,   --反充的时候对应原始单号                            
    financedate   varchar(8)   NULL,   --帐务日期                        
    salebakflag   int     NULL     ,
    billinsertype		int						NULL,	--充值主办方 1 美容 2 美发
    firstsalecashamt		float					NULL,   --第一销售分享金额(实际现金业绩)
    secondsalecashamt		float					NULL,   --第二销售分享金额(实际现金业绩)
    thirdsalecashamt		float					NULL,   --第三销售分享金额(实际现金业绩)
    fourthsalecashamt		float					NULL,   --第四销售分享金额(实际现金业绩)
    fifthsalecashamt		float					NULL,   --第五销售分享金额(实际现金业绩)
    sixthsalecashamt		float					NULL,   --第六销售分享金额(实际现金业绩)
    seventhsalecashamt		float					NULL,   --第七销售分享金额(实际现金业绩)
    eighthsalecashamt		float					NULL,   --第八销售分享金额(实际现金业绩)
    ninthsalecashamt		float					NULL,   --第九销售分享金额(实际现金业绩)
    tenthsalecashamt		float					NULL,   --第十销售分享金额(实际现金业绩)                                  
  )    
  create clustered index index_mcardrechargeinfo on #mcardrechargeinfo(rechargecompid,financedate)                                         
  insert into #mcardrechargeinfo(rechargecompid,rechargebillid,rechargetype,firstsalerinid,firstsaleamt,secondsalerinid,secondsaleamt,thirdsalerinid,thirdsaleamt,    
   fourthsalerinid,fourthsaleamt,fifthsalerinid,fifthsaleamt, sixthsalerinid,sixthsaleamt,seventhsalerinid,seventhsaleamt,    
   eighthsalerinid,eighthsaleamt,ninthsalerinid,ninthsaleamt,tenthsalerinid,tenthsaleamt,
   firstsalecashamt,secondsalecashamt,thirdsalecashamt,fourthsalecashamt,fifthsalecashamt,sixthsalecashamt,seventhsalecashamt,eighthsalecashamt,ninthsalecashamt,tenthsalecashamt,
   financedate,salebakflag,backbillid,billinsertype)                                      
 select rechargecompid,rechargebillid,rechargetype,firstsalerinid,firstsaleamt,secondsalerinid,secondsaleamt,thirdsalerinid,thirdsaleamt,    
   fourthsalerinid,fourthsaleamt,fifthsalerinid,fifthsaleamt, sixthsalerinid,sixthsaleamt,seventhsalerinid,seventhsaleamt,    
   eighthsalerinid,eighthsaleamt,ninthsalerinid,ninthsaleamt,tenthsalerinid,tenthsaleamt,
   firstsalecashamt,secondsalecashamt,thirdsalecashamt,fourthsalecashamt,fifthsalecashamt,sixthsalecashamt,seventhsalecashamt,eighthsalecashamt,ninthsalecashamt,tenthsalecashamt,
   financedate,salebakflag,backbillid ,case when isnull(@sp102,'0')='1' then ISNULL(billinsertype,0) else 0 end                             
 from mcardrechargeinfo with (nolock)                                    
 where rechargecompid=@compid and financedate>=@datefrom and financedate<=@dateto and rechargetype in (0,1,6) and ISNULL(salebakflag,0)=0  and isnull(backbillid,'')=''                                     
  --------------------------------------------计算充值业绩-------Start--------------------------------------------------      
 --第1负责人充值/还款业绩                                       
   insert into #yeji_result(compid,item,yeji)                                      
   select rechargecompid,
		case when empdep=@DEP_BEAUT_CODE then '41'    
        when empdep=@DEP_HAIR_CODE  then '42'    
        when empdep=@DEP_FINGER_CODE then '45' end ,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3  then isnull(firstsalecashamt,0) 
		else 1*isnull(firstsaleamt,0) end )),0)                                      
   from #emp_dep ,#mcardrechargeinfo                                      
   where inid=firstsalerinid   and financedate>=datefrom and financedate<dateto   and rechargecompid=compid                                    
   group by rechargecompid,empdep                                      
                        
                    
   --第2负责人充值/还款业绩                                       
   insert into #yeji_result(compid,item,yeji)                                      
   select rechargecompid,case when empdep=@DEP_BEAUT_CODE then '41'    
        when empdep=@DEP_HAIR_CODE  then '42'    
        when empdep=@DEP_FINGER_CODE then '45' end,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3 then isnull(secondsalecashamt,0)
		else 1*isnull(secondsaleamt,0) end )),0)                                       
   from #emp_dep ,#mcardrechargeinfo                                      
   where inid=secondsalerinid   and financedate>=datefrom and financedate<dateto     and rechargecompid=compid                                   
   group by rechargecompid,empdep                                          
                      
   --第3负责人充值/还款业绩                                    
   insert into #yeji_result(compid,item,yeji)                                      
   select rechargecompid,case when empdep=@DEP_BEAUT_CODE then '41'    
        when empdep=@DEP_HAIR_CODE  then '42'    
        when empdep=@DEP_FINGER_CODE then '45' end,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3 then isnull(thirdsalecashamt,0)
		else 1*isnull(thirdsaleamt,0) end )),0)                                        
   from #emp_dep ,#mcardrechargeinfo                                      
   where inid=thirdsalerinid     and financedate>=datefrom and financedate<dateto     and rechargecompid=compid                                
   group by rechargecompid  ,empdep                                         
                                        
                    
   --第4负责人充值/还款业绩                                       
   insert into #yeji_result(compid,item,yeji)                                      
   select rechargecompid,case when empdep=@DEP_BEAUT_CODE then '41'    
        when empdep=@DEP_HAIR_CODE  then '42'    
        when empdep=@DEP_FINGER_CODE then '45' end,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3 then isnull(fourthsalecashamt,0)
		else 1*isnull(fourthsaleamt,0) end )),0)                                        
   from #emp_dep ,#mcardrechargeinfo                                      
   where inid=fourthsalerinid       and financedate>=datefrom and financedate<dateto   and rechargecompid=compid                                 
   group by rechargecompid,empdep                                           
                                        
       
   --第5负责人充值/还款业绩      
       
   insert into #yeji_result(compid,item,yeji)                                      
   select rechargecompid,case when empdep=@DEP_BEAUT_CODE then '41'    
        when empdep=@DEP_HAIR_CODE  then '42'    
        when empdep=@DEP_FINGER_CODE then '45' end,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3 then isnull(fifthsalecashamt,0)
		else 1*isnull(fifthsaleamt,0) end )),0)                                         
   from #emp_dep ,#mcardrechargeinfo                                      
   where inid=fifthsalerinid      and financedate>=datefrom and financedate<dateto    and rechargecompid=compid                                
   group by rechargecompid,empdep                                           
                                 
   --第6负责人充值/还款业绩                                        
   insert into #yeji_result(compid,item,yeji)                                      
   select rechargecompid,case when empdep=@DEP_BEAUT_CODE then '41'    
        when empdep=@DEP_HAIR_CODE  then '42'    
        when empdep=@DEP_FINGER_CODE then '45' end,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3 then isnull(sixthsalecashamt,0)
		else 1*isnull(sixthsaleamt,0) end )),0)       
   from #emp_dep ,#mcardrechargeinfo                                      
   where inid=sixthsalerinid    and financedate>=datefrom and financedate<dateto   and rechargecompid=compid                                  
   group by rechargecompid,empdep                                           
                                        
   --第7负责人充值/还款业绩                                       
   insert into #yeji_result(compid,item,yeji)                                      
   select rechargecompid,case when empdep=@DEP_BEAUT_CODE then '41'    
        when empdep=@DEP_HAIR_CODE  then '42'    
        when empdep=@DEP_FINGER_CODE then '45' end,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3 then isnull(seventhsalecashamt,0)
		else 1*isnull(seventhsaleamt,0) end )),0)                                         
   from #emp_dep ,#mcardrechargeinfo                                      
   where inid=seventhsalerinid     and financedate>=datefrom and financedate<dateto   and rechargecompid=compid                                 
   group by rechargecompid ,empdep                                          
                                 
   --第8负责人充值/还款业绩                                       
   insert into #yeji_result(compid,item,yeji)                                      
   select rechargecompid,case when empdep=@DEP_BEAUT_CODE then '41'    
        when empdep=@DEP_HAIR_CODE  then '42'    
        when empdep=@DEP_FINGER_CODE then '45' end,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3 then isnull(eighthsalecashamt,0)
		else 1*isnull(eighthsaleamt,0) end )),0)                                         
   from #emp_dep ,#mcardrechargeinfo                                      
   where inid=eighthsalerinid      and financedate>=datefrom and financedate<dateto  and rechargecompid=compid                                 
   group by rechargecompid ,empdep        
       
    --第9负责人充值/还款业绩                                       
   insert into #yeji_result(compid,item,yeji)                                      
   select rechargecompid,case when empdep=@DEP_BEAUT_CODE then '41'    
        when empdep=@DEP_HAIR_CODE  then '42'    
        when empdep=@DEP_FINGER_CODE then '45' end,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3 then isnull(ninthsalecashamt,0)
		else 1*isnull(ninthsaleamt,0) end )),0)                                         
   from #emp_dep ,#mcardrechargeinfo                                      
   where inid=ninthsalerinid      and financedate>=datefrom and financedate<dateto  and rechargecompid=compid                                 
   group by rechargecompid ,empdep       
       
    --第10负责人充值/还款业绩                                       
   insert into #yeji_result(compid,item,yeji)                                      
   select rechargecompid,case when empdep=@DEP_BEAUT_CODE then '41'    
        when empdep=@DEP_HAIR_CODE  then '42'    
        when empdep=@DEP_FINGER_CODE then '45' end,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3 then isnull(tenthsalecashamt,0)
		else 1*isnull(tenthsaleamt,0) end )),0)                                        
   from #emp_dep ,#mcardrechargeinfo                                      
   where inid=tenthsalerinid      and financedate>=datefrom and financedate<dateto  and rechargecompid=compid                                 
   group by rechargecompid ,empdep                                         
          
   insert into #yeji_result(compid,item,yeji)                                        
   select rechargecompid,'43',isnull(sum(payamt),0)                                         
   from #mcardrechargeinfo ,dpayinfo with(nolock)                        
   where rechargetype in (0,1,6) and rechargecompid=paycompid and rechargebillid=paybillid and paybilltype='CZ' and paymode in ('1','2','6','14','15','16') and isnull(salebakflag,0)=0 and isnull(backbillid,'')=''                                       
   group by rechargecompid      
                
                
          
                               
   drop table #mcardrechargeinfo     
   --------------------------------------------计算充值业绩-------End--------------------------------------------------      
       
    --51.卡异动美容类业绩；  52.卡异动美发类业绩；  53.卡异动总业绩                                      
 CREATE TABLE #mcardchangeinfo               -- 会员卡--异动单                                      
 (                                      
    changecompid   varchar(10)   NULL,   --公司编号                                      
    changebillid   varchar(20)   NULL, --异动单号                                      
    changetype   int     NULL,   --异动类别(0 折扣转卡 1 收购转卡 2竞争转卡 3换卡 4挂失卡 5补卡 6老卡并老卡 7老卡并新卡 8退卡    
    firstsalerinid  varchar(20)   NULL,   --销售人员                                      
    firstsaleamt   float    NULL,   --第一销售虚业绩                                       
    secondsalerinid  varchar(20)   NULL,   --第二销售                                      
    secondsaleamt   float    NULL,   --第二销售虚业绩                                        
    thirdsalerinid  varchar(20)   NULL,   --第三负责人                                  
    thirdsaleamt   float    NULL,   --第三销售虚业绩                                       
    fourthsalerinid  varchar(20)   NULL,   --第4负责人                                      
    fourthsaleamt   float    NULL, --第四人员业绩                                      
    fifthsalerinid  varchar(20)   NULL,   --第5负责人                                      
    fifthsaleamt   float    NULL, --第五人员业绩                                      
    sixthsalerinid  varchar(20)   NULL,   --第6负责人                                      
    sixthsaleamt   float    NULL, --第六人员业绩                                      
    seventhsalerinid  varchar(20)   NULL,   --第7负责人                                      
    seventhsaleamt  float    null, --第七人员业绩                                      
    eighthsalerinid  varchar(20)   NULL,   --第8负责人                                      
    eighthsaleamt   float    NULL, --第八人员业绩      
    ninthsalerinid  varchar(20)   NULL    ,   --第9卖卡人                                      
    ninthsaleamt   float    NULL    , --第9人员业绩      
    tenthsalerinid  varchar(20)   NULL    ,   --第10卖卡人                                      
    tenthsaleamt   float    NULL    , --第10人员业绩                                    
    financedate   varchar(8)   NULL,   --帐务日期                                        
    salebakflag   int     NULL   ,
     billinsertype		int						NULL,	--充值主办方 1 美容 2 美发
     firstsalecashamt		float					NULL,   --第一销售分享金额(实际现金业绩)
    secondsalecashamt		float					NULL,   --第二销售分享金额(实际现金业绩)
    thirdsalecashamt		float					NULL,   --第三销售分享金额(实际现金业绩)
    fourthsalecashamt		float					NULL,   --第四销售分享金额(实际现金业绩)
    fifthsalecashamt		float					NULL,   --第五销售分享金额(实际现金业绩)
    sixthsalecashamt		float					NULL,   --第六销售分享金额(实际现金业绩)
    seventhsalecashamt		float					NULL,   --第七销售分享金额(实际现金业绩)
    eighthsalecashamt		float					NULL,   --第八销售分享金额(实际现金业绩)
    ninthsalecashamt		float					NULL,   --第九销售分享金额(实际现金业绩)
    tenthsalecashamt		float					NULL,   --第十销售分享金额(实际现金业绩) 
                                           
  )                                      
  create clustered index index_mcardchangeinfo on #mcardchangeinfo(changecompid,financedate)       
      
 insert into #mcardchangeinfo(changecompid,changebillid,changetype,firstsalerinid,firstsaleamt,secondsalerinid,secondsaleamt,thirdsalerinid,thirdsaleamt,    
   fourthsalerinid,fourthsaleamt,fifthsalerinid,fifthsaleamt, sixthsalerinid,sixthsaleamt,seventhsalerinid,seventhsaleamt,    
   eighthsalerinid,eighthsaleamt,ninthsalerinid,ninthsaleamt,tenthsalerinid,tenthsaleamt,
   firstsalecashamt,secondsalecashamt,thirdsalecashamt,fourthsalecashamt,fifthsalecashamt,sixthsalecashamt,seventhsalecashamt,eighthsalecashamt,ninthsalecashamt,tenthsalecashamt,
   financedate,salebakflag,billinsertype)           
 select changecompid,changebillid,changetype,firstsalerinid,firstsaleamt,secondsalerinid,secondsaleamt,thirdsalerinid,thirdsaleamt,    
   fourthsalerinid,fourthsaleamt,fifthsalerinid,fifthsaleamt, sixthsalerinid,sixthsaleamt,seventhsalerinid,seventhsaleamt,    
   eighthsalerinid,eighthsaleamt,ninthsalerinid,ninthsaleamt,tenthsalerinid,tenthsaleamt,
   firstsalecashamt,secondsalecashamt,thirdsalecashamt,fourthsalecashamt,fifthsalecashamt,sixthsalecashamt,seventhsalecashamt,eighthsalecashamt,ninthsalecashamt,tenthsalecashamt,
   financedate,salebakflag,case when isnull(@sp102,'0')='1' then ISNULL(billinsertype,0) else 0 end                           
 from mcardchangeinfo with (nolock)                                     
 where changecompid=@compid and financedate>=@datefrom and financedate<=@dateto and ISNULL(salebakflag,0)=0                                     
      and changetype in (0,1,2,3,5,6,7,8)                           
                                      
 update #mcardchangeinfo set firstsaleamt = 0-ISNULL(firstsaleamt,0),secondsaleamt = 0-ISNULL(secondsaleamt,0),    
        thirdsaleamt = 0-ISNULL(thirdsaleamt,0),fourthsaleamt = 0-ISNULL(fourthsaleamt,0),    
        fifthsaleamt = 0-ISNULL(fifthsaleamt,0),sixthsaleamt = 0-ISNULL(sixthsaleamt,0),    
        seventhsaleamt = 0-ISNULL(seventhsaleamt,0),eighthsaleamt = 0-ISNULL(eighthsaleamt,0)                                     
 where changetype = 8   
 
 update #mcardchangeinfo set firstsalecashamt = 0-ISNULL(firstsalecashamt,0),secondsalecashamt = 0-ISNULL(secondsalecashamt,0),    
        thirdsalecashamt = 0-ISNULL(thirdsalecashamt,0),fourthsalecashamt = 0-ISNULL(fourthsalecashamt,0),    
        fifthsalecashamt = 0-ISNULL(fifthsalecashamt,0),sixthsalecashamt = 0-ISNULL(sixthsalecashamt,0),    
        seventhsalecashamt = 0-ISNULL(seventhsalecashamt,0),eighthsalecashamt = 0-ISNULL(eighthsalecashamt,0)                                     
 where changetype = 8        
 --------------------------------------------计算卡异动业绩-------Start------------------------------------------------    
                                  
   --第1销售                                      
   insert into #yeji_result(compid,item,yeji)                                      
   select changecompid,case  when empdep=@DEP_BEAUT_CODE  then '51'    
        when empdep=@DEP_HAIR_CODE  then '52'    
        when empdep=@DEP_FINGER_CODE then '55' end ,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3 then isnull(firstsalecashamt,0)
		else 1*isnull(firstsaleamt,0) end )),0)                                           
   from #emp_dep ,#mcardchangeinfo                                      
   where inid=firstsalerinid   and financedate>=datefrom and financedate<dateto    and changecompid=compid                                  
   group by changecompid,empdep                                      
                                        
                 
                                        
   --第2销售                                      
   insert into #yeji_result(compid,item,yeji)                         
   select changecompid,case  when empdep=@DEP_BEAUT_CODE  then '51'    
        when empdep=@DEP_HAIR_CODE  then '52'    
        when empdep=@DEP_FINGER_CODE then '55' end,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3  then isnull(secondsalecashamt,0)
		else 1*isnull(secondsaleamt,0) end )),0)                                           
   from #emp_dep ,#mcardchangeinfo                                      
   where inid=secondsalerinid  and financedate>=datefrom and financedate<dateto      and changecompid=compid                               
   group by changecompid,empdep                                      
                                        
                              
   --第3销售                                      
   insert into #yeji_result(compid,item,yeji)                                      
   select changecompid,case  when empdep=@DEP_BEAUT_CODE  then '51'    
        when empdep=@DEP_HAIR_CODE  then '52'    
        when empdep=@DEP_FINGER_CODE then '55' end,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3 then isnull(thirdsalecashamt,0)
		else 1*isnull(thirdsaleamt,0) end )),0)                                           
   from #emp_dep ,#mcardchangeinfo                                      
   where inid=thirdsalerinid    and financedate>=datefrom and financedate<dateto  and changecompid=compid                                 
   group by changecompid,empdep                                      
                         
   --第4销售                                     
   insert into #yeji_result(compid,item,yeji)                                      
   select changecompid,case  when empdep=@DEP_BEAUT_CODE  then '51'    
        when empdep=@DEP_HAIR_CODE  then '52'    
        when empdep=@DEP_FINGER_CODE then '55' end,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3 then isnull(fourthsalecashamt,0)
		else 1*isnull(fourthsaleamt,0) end )),0)                                           
   from #emp_dep ,#mcardchangeinfo                                      
   where inid=fourthsalerinid   and financedate>=datefrom and financedate<dateto    and changecompid=compid                                
   group by changecompid,empdep                                      
                                  
   --第5销售                                      
   insert into #yeji_result(compid,item,yeji)    
   select changecompid,case  when empdep=@DEP_BEAUT_CODE  then '51'    
        when empdep=@DEP_HAIR_CODE  then '52'    
        when empdep=@DEP_FINGER_CODE then '55' end,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3  then  isnull(fifthsalecashamt,0)
		else 1*isnull(fifthsaleamt,0) end )),0)                                            
   from #emp_dep ,#mcardchangeinfo                                      
   where inid=fifthsalerinid   and financedate>=datefrom and financedate<dateto     and changecompid=compid                                
   group by changecompid,empdep                                      
                              
   --第6销售                                      
   insert into #yeji_result(compid,item,yeji)                                      
   select changecompid,case   when empdep=@DEP_BEAUT_CODE  then '51'    
         when empdep=@DEP_HAIR_CODE  then '52'    
         when empdep=@DEP_FINGER_CODE then '55' end,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3 then isnull(sixthsalecashamt,0)
		else 1*isnull(sixthsaleamt,0) end )),0)                                            
   from #emp_dep ,#mcardchangeinfo                                      
   where inid=sixthsalerinid   and financedate>=datefrom and financedate<dateto      and changecompid=compid                              
   group by changecompid ,empdep                                     
              
   --第7销售                                      
   insert into #yeji_result(compid,item,yeji)                                      
   select changecompid,case  when empdep=@DEP_BEAUT_CODE  then '51'    
        when empdep=@DEP_HAIR_CODE  then '52'    
        when empdep=@DEP_FINGER_CODE then '55' end,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3 then isnull(seventhsalecashamt,0)
		else 1*isnull(seventhsaleamt,0) end )),0)                                           
   from #emp_dep ,#mcardchangeinfo                                      
   where inid=seventhsalerinid    and financedate>=datefrom and financedate<dateto      and changecompid=compid                              
   group by changecompid ,empdep                                     
                                      
   --第8销售                                      
   insert into #yeji_result(compid,item,yeji)                                      
   select changecompid,case  when empdep=@DEP_BEAUT_CODE  then '51'    
        when empdep=@DEP_HAIR_CODE  then '52'    
        when empdep=@DEP_FINGER_CODE then '55' end,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3 then isnull(eighthsalecashamt,0)
		else 1*isnull(eighthsaleamt,0) end )),0)                                           
   from #emp_dep ,#mcardchangeinfo                                      
   where inid=eighthsalerinid   and financedate>=datefrom and financedate<dateto  and changecompid=compid                                
   group by changecompid,empdep         
       
    --第9销售                                      
   insert into #yeji_result(compid,item,yeji)                                      
   select changecompid,case  when empdep=@DEP_BEAUT_CODE  then '51'    
        when empdep=@DEP_HAIR_CODE  then '52'    
        when empdep=@DEP_FINGER_CODE then '55' end,
        isnull(sum((
			case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			     when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			     when isnull(billinsertype,0)=3 then isnull(ninthsalecashamt,0)
		else 1*isnull(ninthsaleamt,0) end )),0)                                           
   from #emp_dep ,#mcardchangeinfo                                      
   where inid=ninthsalerinid   and financedate>=datefrom and financedate<dateto  and changecompid=compid                                
   group by changecompid,empdep      
       
    --第10销售                                      
   insert into #yeji_result(compid,item,yeji)                                      
   select changecompid,case  when empdep=@DEP_BEAUT_CODE  then '51'    
        when empdep=@DEP_HAIR_CODE  then '52'    
        when empdep=@DEP_FINGER_CODE then '55' end,
        isnull(sum((
        case when isnull(billinsertype,0)=1  and empdep=@DEP_HAIR_CODE then 0  --美容卡金 美发人员分享为0
			 when isnull(billinsertype,0)=2  and empdep=@DEP_BEAUT_CODE then 0  --美发卡金 美容人员分享为0
			 when isnull(billinsertype,0)=3  then isnull(tenthsalecashamt,0)
		else 1*isnull(tenthsaleamt,0) end )),0)                                            
   from #emp_dep ,#mcardchangeinfo                                      
   where inid=tenthsalerinid   and financedate>=datefrom and financedate<dateto  and changecompid=compid                                
   group by changecompid,empdep                                   
                            
   ----------                                   
   insert into #yeji_result(compid,item,yeji)                                        
   select changecompid,'53',isnull(sum(case when changetype=8 then 0-payamt else payamt end),0)                                         
   from #mcardchangeinfo,dpayinfo with(nolock)                                       
   where isnull(salebakflag,0)<>1 and changecompid=paycompid and changebillid=paybillid and paybilltype in ('ZK','TK') and paymode in ('1','2','6','14','15','16')                                       
   group by changecompid                                     
                                        
   drop table #mcardchangeinfo                                       
                         
 --------------------------------------------计算卡异动业绩-------End--------------------------------------------------     
 --疗程兑换    
 create table #mproexchangeinfo(                        
  changecompid   varchar(10)    NULL,   --公司别                        
  changebillid   varchar(20)    NULL,   --单据编号                      
  changeproid    varchar(20)    NULL,   --项目编号                        
  changedate    varchar(10)    NULL,           --财务日期       
  changebycashamt   float     NULL,   --充值金额（现金金额）        
  firstsalerinid   varchar(20)    NULL,   --第一销售内部编号    
  firstsaleamt   float     NULL,   --第一销售分享金额    
  secondsalerinid   varchar(20)    NULL,   --第二销售内部编号    
  secondsaleamt   float     NULL,   --第二销售分享金额    
  thirdsalerinid   varchar(20)    NULL,   --第三销售内部编号    
  thirdsaleamt   float     NULL,   --第三销售分享金额    
  fourthsalerinid   varchar(20)    NULL,   --第四销售内部编号    
  fourthsaleamt   float     NULL,   --第四销售分享金额    
 )          
 insert #mproexchangeinfo( changecompid,changebillid,changeproid,changedate,firstsalerinid,firstsaleamt,secondsalerinid,secondsaleamt,thirdsalerinid,thirdsaleamt,    
   fourthsalerinid,fourthsaleamt,changebycashamt)                        
 select a.changecompid,a.changebillid,changeproid,changedate,firstsalerinid,firstsaleamt,secondsalerinid,secondsaleamt,thirdsalerinid,thirdsaleamt,    
   fourthsalerinid,fourthsaleamt,changebycashamt                        
 from mproexchangeinfo a WITH (NOLOCK) , dproexchangeinfo b WITH (NOLOCK)                         
 where a.changecompid=b.changecompid and a.changebillid=b.changebillid and     
  a.changecompid=@compid and changedate>=@datefrom and changedate<=@dateto     
  and ISNULL(salebakflag,0)=0        
    --------------------------------------------计算疗程兑换业绩-------Start------------------------------------------------    
   insert into #yeji_result(compid,item,yeji)                                      
   select changecompid,'61',isnull(sum(changebycashamt),0)                                      
   from #mproexchangeinfo,#prj_cls                                      
   where changecompid=compid                                       
     and changeproid=prjid                                       
     and prjcls=@PRJ_BEAUT_CLASS_CODE                                                    
   group by changecompid                                    
                                       
   insert into #yeji_result(compid,item,yeji)                                      
   select changecompid,'62',isnull(sum(changebycashamt),0)                                      
   from #mproexchangeinfo,#prj_cls                                      
   where changecompid=compid                                       
     and changeproid=prjid                                       
     and (prjcls=@PRJ_HAIR_CLASS_CODE   or    prjcls= @DEP_FOOT_CODE)                     
   group by changecompid                           
                                
      
   insert into #yeji_result(compid,item,yeji)                                        
   select changecompid,'63',isnull(sum(changebycashamt),0)                                         
   from #mproexchangeinfo                                            
   group by changecompid                             
                           
   insert into #yeji_result(compid,item,yeji)                                      
   select changecompid,'64',isnull(sum(firstsaleamt),0)                               
   from #emp_dep ,#mproexchangeinfo                                      
   where inid=firstsalerinid and empdep=@DEP_FOOT_CODE  and changedate>=datefrom and changedate<dateto  and changecompid=compid                                   
   group by changecompid                                      
                  
      
                            
    insert into #yeji_result(compid,item,yeji)                                      
   select changecompid,'64',isnull(sum(secondsaleamt),0)                                       
   from #emp_dep ,#mproexchangeinfo                                      
   where inid=secondsalerinid and empdep=@DEP_FOOT_CODE  and changedate>=datefrom and changedate<dateto    and changecompid=compid                                       
   group by changecompid                            
                          
   insert into #yeji_result(compid,item,yeji)                                      
   select changecompid,'64',isnull(sum(thirdsaleamt),0)                                       
   from #emp_dep ,#mproexchangeinfo                                      
   where inid=thirdsalerinid and empdep=@DEP_FOOT_CODE  and changedate>=datefrom and changedate<dateto    and changecompid=compid                                       
   group by changecompid                         
                                      
   insert into #yeji_result(compid,item,yeji)                                      
   select changecompid,'64',isnull(sum(fourthsaleamt),0)                                       
   from #emp_dep ,#mproexchangeinfo                                      
   where inid=fourthsalerinid and empdep=@DEP_FOOT_CODE  and changedate>=datefrom and changedate<dateto     and changecompid=compid                                      
   group by changecompid                          
                           
                                  
   insert into #yeji_result(compid,item,yeji)                                      
   select changecompid,'65',isnull(sum(changebycashamt),0)                                      
   from #mproexchangeinfo,#prj_cls                                      
   where changecompid=compid                                       
     and changeproid=prjid                                       
     and prjcls=@PRJ_FINGER_CLASS_CODE                                      
   group by changecompid                            
                           
                           
  drop table #mproexchangeinfo      
    --------------------------------------------计算疗程兑换业绩-------End--------------------------------------------------     
    --销售条码卡    
     create table #msalebarcodecardinfo                        
 (                        
   salecompid    varchar(10)    NULL,--公司编号       
   salebillid    varchar(20)    NULL,--转卡单                        
   barcodecardno   varchar(20)    NULL,--转卡单                        
   saledate    varchar(10)    NULL,--操作     
   saleamt    float    null ,--销售总额                  
   firstsalerinid   varchar(20)    NULL,   --第一销售内部编号    
   firstsaleamt   float     NULL,   --第一销售分享金额    
   secondsalerinid  varchar(20)    NULL,   --第二销售内部编号    
   secondsaleamt   float     NULL,   --第二销售分享金额    
   thirdsalerinid   varchar(20)    NULL,   --第三销售内部编号    
   thirdsaleamt   float     NULL,   --第三销售分享金额   
   firstpayamt		float				null	,--第一支付金额
   secondpayamt		float				null	,--第二支付金额
   usecardpayamt	float				null	--储值支付                    
 )     
 insert #msalebarcodecardinfo(salecompid,salebillid,saledate,saleamt,barcodecardno,firstsalerinid,firstsaleamt,secondsalerinid,secondsaleamt,thirdsalerinid,thirdsaleamt,firstpayamt,secondpayamt)                        
    select salecompid,salebillid,saledate,saleamt,barcodecardno,firstsaleempinid,firstsaleamt,secondsaleempinid,secondsaleamt,thirdsaleempinid,thirdsaleamt ,firstpayamt,secondpayamt   
   from msalebarcodecardinfo WITH (NOLOCK)                                            
  where saledate  between @datefrom and @dateto and salecompid= @compid        
              
               
    --------------------------------------------计算条码卡销售业绩-------Start--------------------------------------------------            
    --第1销售                                      
   insert into #yeji_result(compid,item,yeji)                                      
   select salecompid,case     when empdep=@DEP_BEAUT_CODE  then '71'    
         when empdep=@DEP_HAIR_CODE  then '72'    
         when empdep=@DEP_FINGER_CODE then '75' end,isnull(sum(isnull(firstsaleamt,0)*(ISNULL(firstpayamt,0)+ISNULL(secondpayamt,0))/isnull(saleamt,0)),0)                                       
   from #emp_dep ,#msalebarcodecardinfo                                      
   where inid=firstsalerinid   and saledate>=datefrom and saledate<dateto     and salecompid=compid   and isnull(saleamt,0)<>0                                      
   group by salecompid ,empdep                                     
                              
                          
    --第2销售                  
    insert into #yeji_result(compid,item,yeji)                                      
   select salecompid,case     when empdep=@DEP_BEAUT_CODE  then '71'    
         when empdep=@DEP_HAIR_CODE  then '72'    
         when empdep=@DEP_FINGER_CODE then '75' end,isnull(sum(isnull(secondsaleamt,0)*(ISNULL(firstpayamt,0)+ISNULL(secondpayamt,0))/isnull(saleamt,0)),0)                                       
   from #emp_dep ,#msalebarcodecardinfo                                      
   where inid=secondsalerinid  and saledate>=datefrom and saledate<dateto     and salecompid=compid    and isnull(saleamt,0)<>0                                 
   group by salecompid ,empdep     
       
     
     --第3销售                  
    insert into #yeji_result(compid,item,yeji)                                      
   select salecompid,case     when empdep=@DEP_BEAUT_CODE  then '71'    
         when empdep=@DEP_HAIR_CODE  then '72'    
         when empdep=@DEP_FINGER_CODE then '75' end,isnull(sum(isnull(thirdsaleamt,0)*(ISNULL(firstpayamt,0)+ISNULL(secondpayamt,0))/isnull(saleamt,0)),0)                                       
   from #emp_dep ,#msalebarcodecardinfo                                      
   where inid=thirdsalerinid    and saledate>=datefrom and saledate<dateto     and salecompid=compid     and isnull(saleamt,0)<>0                                    
   group by salecompid ,empdep    
        
      
   ----------                                      
   insert into #yeji_result(compid,item,yeji)                                        
   select salecompid,'73',isnull(sum(ISNULL(firstpayamt,0)+ISNULL(secondpayamt,0)),0)                                         
   from #msalebarcodecardinfo                                            
   group by salecompid               
                          
  drop table #msalebarcodecardinfo       
    --------------------------------------------计算条码卡销售业绩-------End--------------------------------------------------      
    --合作项目    
        CREATE TABLE #mcooperatesaleinfo               -- 会员卡--异动单                                      
  (                                      
    salecompid   varchar(10)   NULL ,   --公司编号             
    salebillid   varchar(20)   NULL    ,   --单据编号            
    saledate    varchar(10)   NULL    ,   --销售日期    
    salecooperid   varchar(30)    NULL    ,   --合作单位    
    slaepaymode   varchar(5)    NULL    ,   --支付方向 1 店内支付，2 合作单位支付    
    firstsalerinid  varchar(20)   NULL ,   --第一销售内部编号          
    firstsaleamt   float    NULL , --第一销售虚业绩                                      
    secondsalerinid  varchar(20)   NULL    ,   --第二卖卡人                                      
    secondsaleamt   float    NULL    , --第二销售虚业绩                                       
    thirdsalerinid  varchar(20)   NULL    ,   --第三卖卡人                                      
    thirdsaleamt   float    NULL    , --第三销售虚业绩                                       
    fourthsalerinid  varchar(20)   NULL    ,   --第4卖卡人                                      
    fourthsaleamt   float    NULL    , --第四人员业绩                                      
    fifthsalerinid  varchar(20)   NULL    ,   --第5卖卡人                                      
    fifthsaleamt   float    NULL    , --第五人员业绩                                      
    sixthsalerinid  varchar(20)   NULL    ,   --第6卖卡人                                      
    sixthsaleamt   float    NULL    , --第六人员业绩                                          
    seventhsalerinid  varchar(20)   NULL    ,   --第7卖卡人                                      
    seventhsaleamt  float    NULL    , --第七人员业绩                                      
    eighthsalerinid  varchar(20)   NULL    ,   --第8卖卡人                                      
    eighthsaleamt   float    NULL    , --第八人员业绩     
    ntotalamt    float    NULL , --总支付    
    totalamt    float    NULL , --总支付           
   )                                      
   create clustered index index_mcooperatesaleinfo on #mcooperatesaleinfo(salecompid,saledate)     
     
      
   insert #mcooperatesaleinfo(salecompid,salebillid,saledate,salecooperid,slaepaymode,firstsalerinid,firstsaleamt,secondsalerinid,secondsaleamt,thirdsalerinid,thirdsaleamt,    
   fourthsalerinid,fourthsaleamt,fifthsalerinid,fifthsaleamt, sixthsalerinid,sixthsaleamt,seventhsalerinid,seventhsaleamt,    
   eighthsalerinid,eighthsaleamt)    
   select salecompid,salebillid,saledate,salecooperid,slaepaymode,firstsalerinid,firstsaleamt,secondsalerinid,secondsaleamt,thirdsalerinid,thirdsaleamt,    
   fourthsalerinid,fourthsaleamt,fifthsalerinid,fifthsaleamt, sixthsalerinid,sixthsaleamt,seventhsalerinid,seventhsaleamt,    
   eighthsalerinid,eighthsaleamt    
   from   mcooperatesaleinfo  where salecompid=@compid and financedate  between @datefrom and @dateto    
   and ISNULL(salebillflag,0)=2     
       
   update #mcooperatesaleinfo set ntotalamt=isnull((select SUM(isnull(payamt,0)) from dpayinfo where paycompid=salecompid and paybillid=salebillid and paybilltype='HZW'),0)    
   update #mcooperatesaleinfo set totalamt=isnull((select SUM(isnull(payamt,0)) from dpayinfo where paycompid=salecompid and paybillid=salebillid and paybilltype='HZ'),0)    
       
   update #mcooperatesaleinfo set totalamt= case  when  salecooperid='001' and ISNULL(slaepaymode,'1')='1' then isnull(totalamt,0)*0.5                    
          when  salecooperid='001' and ISNULL(slaepaymode,'1')='2' then isnull(totalamt,0)                    
          when  salecooperid='002' and ISNULL(slaepaymode,'1')='1' then isnull(totalamt,0)                    
          when  salecooperid='002' and ISNULL(slaepaymode,'1')='2' then 0                    
          when  salecooperid='003' and ISNULL(slaepaymode,'1')='1' then isnull(totalamt,0)*0.6                    
          when  salecooperid='003' and ISNULL(slaepaymode,'1')='2' then 0 end    
   where ISNULL(totalamt,0)>0    
       
   delete #mcooperatesaleinfo where ISNULL(ntotalamt,0)=0     
        
        
        
     --------------------------------------------计算合作项目销售业绩-------Start--------------------------------------------------     
      --第1销售                                      
   insert into #yeji_result(compid,item,yeji)                                      
   select salecompid,case     when empdep=@DEP_BEAUT_CODE  then 'A1'    
         when empdep=@DEP_HAIR_CODE  then 'A2'    
         when empdep=@DEP_FINGER_CODE then 'A5' end,convert(numeric(20,1),isnull(sum(isnull(firstsaleamt,0)*ISNULL(totalamt,0)/ISNULL(ntotalamt,0)),0))                                       
   from #emp_dep ,#mcooperatesaleinfo                                      
   where inid=firstsalerinid   and saledate>=datefrom and saledate<dateto    and salecompid=compid                                  
   group by salecompid   ,empdep                                   
                                        
       
              
   --第2销售                                      
   insert into #yeji_result(compid,item,yeji)                                      
   select salecompid,case     when empdep=@DEP_BEAUT_CODE  then 'A1'    
         when empdep=@DEP_HAIR_CODE  then 'A2'    
         when empdep=@DEP_FINGER_CODE then 'A5' end,convert(numeric(20,1),isnull(sum(isnull(secondsaleamt,0)*ISNULL(totalamt,0)/ISNULL(ntotalamt,0)),0))                              
   from #emp_dep ,#mcooperatesaleinfo                                      
   where inid=secondsalerinid    and saledate>=datefrom and saledate<dateto    and salecompid=compid                                  
   group by salecompid  ,empdep                                    
       
    --第3销售                                      
   insert into #yeji_result(compid,item,yeji)                                      
   select salecompid,case     when empdep=@DEP_BEAUT_CODE  then 'A1'    
         when empdep=@DEP_HAIR_CODE  then 'A2'    
         when empdep=@DEP_FINGER_CODE then 'A5' end,convert(numeric(20,1),isnull(sum(isnull(thirdsaleamt,0)*ISNULL(totalamt,0)/ISNULL(ntotalamt,0)),0))                 
   from #emp_dep ,#mcooperatesaleinfo                                      
   where inid=thirdsalerinid    and saledate>=datefrom and saledate<dateto    and salecompid=compid                                  
   group by salecompid,empdep                                      
           
               
    --第4销售                                      
   insert into #yeji_result(compid,item,yeji)                                      
   select salecompid,case     when empdep=@DEP_BEAUT_CODE  then 'A1'    
         when empdep=@DEP_HAIR_CODE  then 'A2'    
         when empdep=@DEP_FINGER_CODE then 'A5' end,convert(numeric(20,1),isnull(sum(isnull(fourthsaleamt,0)*ISNULL(totalamt,0)/ISNULL(ntotalamt,0)),0))                                           
   from #emp_dep ,#mcooperatesaleinfo                                      
   where inid=fourthsalerinid  and saledate>=datefrom and saledate<dateto    and salecompid=compid                                  
   group by salecompid ,empdep                                     
             
               
    --第5销售                                      
   insert into #yeji_result(compid,item,yeji)              
   select salecompid,case     when empdep=@DEP_BEAUT_CODE  then 'A1'    
         when empdep=@DEP_HAIR_CODE  then 'A2'    
         when empdep=@DEP_FINGER_CODE then 'A5' end,convert(numeric(20,1),isnull(sum(isnull(fifthsaleamt,0)*ISNULL(totalamt,0)/ISNULL(ntotalamt,0)),0))                                           
   from #emp_dep ,#mcooperatesaleinfo                                      
   where inid=fifthsalerinid   and saledate>=datefrom and saledate<dateto    and salecompid=compid                                  
   group by salecompid  ,empdep                                   
                   
                                 
   --第6销售                                      
   insert into #yeji_result(compid,item,yeji)                                      
   select salecompid,case     when empdep=@DEP_BEAUT_CODE  then 'A1'    
         when empdep=@DEP_HAIR_CODE  then 'A2'    
         when empdep=@DEP_FINGER_CODE then 'A5' end,convert(numeric(20,1),isnull(sum(isnull(sixthsaleamt,0)*ISNULL(totalamt,0)/ISNULL(ntotalamt,0)),0))                                            
   from #emp_dep ,#mcooperatesaleinfo                                      
   where inid=sixthsalerinid   and saledate>=datefrom and saledate<dateto    and salecompid=compid                                  
   group by salecompid ,empdep                                     
                       
                                         
   insert into #yeji_result(compid,item,yeji)        
   select salecompid,'A3',isnull(sum(isnull(totalamt,0)),0)                                         
   from #mcooperatesaleinfo           
   group by salecompid         
                    
   drop table #mcooperatesaleinfo            
      
  --------------------------------------------计算合作项目销售业绩-------End--------------------------------------------------    
 --------------------------------------------计算分类业绩-------Start------------------------------------------------    
  create table #cls_yeji_resultx(                                  
    compid varchar(10) not null,                                  
    beaut_yeji float null,                                  
    hair_yeji float null,                                  
    foot_yeji float null,                                  
    finger_yeji float null,                                  
    total_yeji float null,                                  
    real_beaut_yeji float null,                                  
    real_hair_yeji float null,                                  
    real_foot_yeji float null,                                  
    real_finger_yeji float null,                                  
    real_total_yeji  float null                                  
   )                                  
                                    
   create table #sum_yeji(compid varchar(10) not null,flag int null, yeji float null)                                  
   insert into #sum_yeji(compid,flag,yeji)                                  
   select compid,1,isnull(sum(yeji),0)                                   
   from #yeji_result                                   
   where item in ('11','21','31','41','51','61','71','A1')                      
   group by compid                                  
                                     
   insert into #sum_yeji(compid,flag,yeji)                                  
   select compid,2,isnull(sum(yeji),0)                                  
   from #yeji_result                                   
   where item in ('12','22','32','42','52','62','72','A2','24x')                                   
   group by compid                                  
                                    
   insert into #sum_yeji(compid,flag,yeji)                                  
   select compid,3,isnull(sum(yeji),0)                                 
   from #yeji_result                                   
   where item in ('13','23','33','43','53','63','73','A3')                                   
   group by compid                                  
                                    
   insert into #sum_yeji(compid,flag,yeji)                                  
   select compid,4,isnull(sum(yeji),0)                                   
   from #yeji_result                                   
   where item in ('14','24','34','44','54','64','74','A4')                       
   group by compid                                  
                                    
   insert into #sum_yeji(compid,flag,yeji)                                  
   select compid,5,isnull(sum(yeji),0)                                   
   from #yeji_result                                   
   where item in ('15','25','35','45','55','65','75','A5')                                   
   group by compid                                  
                                    
   insert into #sum_yeji(compid,flag,yeji)                                  
   select compid,6,isnull(sum(yeji),0)                                   
   from #yeji_result                                   
   where item in ('81','91')                                   
   group by compid                                  
                                    
   insert into #sum_yeji(compid,flag,yeji)                   
   select compid,7,isnull(sum(yeji),0)                                   
   from #yeji_result                                   
   where item in ('82','92','94')                                   
   group by compid                                  
                                     
   insert into #sum_yeji(compid,flag,yeji)                                  
   select compid,8,isnull(sum(yeji),0)                                   
   from #yeji_result                                   
   where item   in ('84','94')                               
   group by compid                                  
                                   
   insert into #sum_yeji(compid,flag,yeji)                                  
   select compid,9,isnull(sum(yeji),0)                                   
   from #yeji_result                                   
   where item  in ('85','95')                                  
   group by compid                                  
                               
   insert into #sum_yeji(compid,flag,yeji)    
   select compid,10,isnull(sum(yeji),0)                                   
   from #yeji_result                                   
   where item in ('83','93')                                   
   group by compid                                  
                                     
   insert into #cls_yeji_resultx(compid,beaut_yeji,hair_yeji,total_yeji)                                  
   select @compid,0,0,0                                 
                                    
   update a                                   
   set beaut_yeji = isnull(yeji,0)                                   
   from #cls_yeji_resultx a,#sum_yeji b                                  
   where b.flag=1                                  
     and a.compid=b.compid                                  
                                     
   update a                                   
   set hair_yeji = isnull(yeji,0)                                   
   from #cls_yeji_resultx a,#sum_yeji b                                  
   where b.flag=2                                  
     and a.compid=b.compid                                  
                                    
   update a                                   
   set total_yeji = isnull(yeji,0)                                   
   from #cls_yeji_resultx a,#sum_yeji b                                  
   where b.flag=3                                  
     and a.compid=b.compid                     
                                    
   update a                                   
   set foot_yeji = isnull(yeji,0)                                   
   from #cls_yeji_resultx a,#sum_yeji b                            
   where b.flag=4                                  
     and a.compid=b.compid                                  
                                     
   update a         
  set finger_yeji = isnull(yeji,0)                                   
   from #cls_yeji_resultx a,#sum_yeji b                                  
   where b.flag=5                                  
     and a.compid=b.compid                                  
  ----------                                  
   update a                    
   set real_beaut_yeji = isnull(yeji,0)                                   
   from #cls_yeji_resultx a,#sum_yeji b                                  
   where b.flag=6                                  
     and a.compid=b.compid                                  
                                     
   update a                                   
   set real_hair_yeji = isnull(yeji,0)                                   
   from #cls_yeji_resultx a,#sum_yeji b                                  
   where b.flag=7                                  
     and a.compid=b.compid                                  
                                    
   update a                                   
   set real_total_yeji = isnull(yeji,0)                                   
   from #cls_yeji_resultx a,#sum_yeji b                                  
   where b.flag=10                                  
     and a.compid=b.compid                                  
                                    
   update a                                   
   set real_foot_yeji = isnull(yeji,0)                                   
   from #cls_yeji_resultx a,#sum_yeji b                                  
   where b.flag=8                                  
     and a.compid=b.compid                                  
                                     
   update a                                   
   set real_finger_yeji = isnull(yeji,0)                                   
   from #cls_yeji_resultx a,#sum_yeji b                                  
   where b.flag=9                                  
     and a.compid=b.compid                                  
                                     
 delete #cls_yeji_resultx where ISNULL(total_yeji,0)=0  and isnull(foot_yeji,0)=0    
     
 if(@usetype=1)    
 begin    
  select compid,@datefrom,beaut_yeji,hair_yeji,foot_yeji,finger_yeji,total_yeji,                                  
    real_beaut_yeji,real_hair_yeji,real_foot_yeji,real_finger_yeji,real_total_yeji                                  
  from #cls_yeji_resultx                                   
   order by compid asc     
 end        
 else    
 begin    
  insert #cls_yeji_result_search(compid,ddate,beaut_yeji,hair_yeji,foot_yeji,finger_yeji,total_yeji,                                  
    real_beaut_yeji,real_hair_yeji,real_foot_yeji,real_finger_yeji,real_total_yeji)    
    select compid,@datefrom,beaut_yeji,hair_yeji,foot_yeji,finger_yeji,total_yeji,                                  
    real_beaut_yeji,real_hair_yeji,real_foot_yeji,real_finger_yeji,real_total_yeji                                  
  from #cls_yeji_resultx      
 end          
 --------------------------------------------计算分类业绩-------End--------------------------------------------------      
    
    drop table #cls_yeji_resultx    
 drop table #prj_cls    
 drop table #yeji_result    
 drop table #goods_cls    
 drop table #empinfobydate    
 drop table #emp_dep    
end    
    
    