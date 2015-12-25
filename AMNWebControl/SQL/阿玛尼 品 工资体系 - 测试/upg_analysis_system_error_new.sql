alter procedure upg_analysis_system_error_new                                    
(                                    
 @compid varchar(10),                                    
 @fromdate varchar(8),                                    
 @todate varchar(8)                                    
)                                    
as                                    
begin                                    
 declare @TIME_VALVE int                                    
 set @TIME_VALVE = 5--单位：秒                                    
 create table #error                                    
 (                                    
  errid  varchar(5)  not null,                                    
  description varchar(500) null                                    
 )                                    
 insert into #error(errid,description) values('E0001','单据重复')                                                      
 insert into #error(errid,description) values('E0003','业绩未分享或未完全分享')                                    
 --1-开卡单 2-充值单 3-还款单 4-折扣转卡单 5-收购转卡单 6-竞争转卡单 7-会员并卡单 8-退卡单                                    
 --9-收银单                                     
                             
  create table #result                                    
 (                                    
  compid  varchar(10)  not null,                                    
  billtype varchar(5)  null,                                    
  errid  varchar(5)  null,                                    
  errdescript varchar(500) null,                                    
  remark  varchar(2048) null,                                    
 )                                 
  create table #emp_deperror(                                      
  compid varchar(10) null,                                      
  inid varchar(20) null,--内部编号                                      
  empid varchar(20) null,--员工编号                     
  emppos varchar(10) null,                                   
  empdep varchar(10) null,--员工部门                           
  datefrom varchar(8) null,                                      
  dateto varchar(8) null,                                      
 )                      
 insert into #emp_deperror(compid,empid,emppos)                                              
 select distinct compno,staffno,position                                      
 from staffinfo with (nolock)                                              
 where @compid=compno                    
                            
  declare @mfstaffshareamt  float         
  declare @mrstaffshareamt  float                
  declare @mfstaffsharecount int                 
  declare @trstaffshareamt  float              
  declare @trstaffsharecount int                  
  declare @totalshareamt  float     
  declare @billinsertype int     
  declare @sp102 varchar(2)      
  select  @sp102=paramvalue from sysparaminfo where paramid='SP102' and compid=@compid         
 --判断单据重复的规则：                                    
 --1、在时间阀值区间内，有两张以及两张以上的单据内容相同；                                    
                                     
 CREATE tAbLE #msalecardinfo               -- 会员卡销售单              
 (              
  salecompid   varchar(10)   Not NULL,   --公司编号              
  salebillid   varchar(20)   Not NULL,   --销售单号              
  saledate   varchar(8)    NULL,   --销售日期              
  salecardno   varchar(20)    NULL,   --销售卡号              
  salecardtype  varchar(20)    NULL,   --销售卡类型              
  firstsalerid  varchar(20)    NULL,   --第一销售工号              
  firstsalerinid  varchar(20)    NULL,   --第一销售内部编号              
  firstsaleamt  float     NULL,   --第一销售分享金额              
  secondsalerid  varchar(20)    NULL,   --第二销售工号              
  secondsalerinid  varchar(20)    NULL,   --第二销售内部编号              
  secondsaleamt  float     NULL,   --第二销售分享金额              
  thirdsalerid  varchar(20)    NULL,   --第三销售工号              
  thirdsalerinid  varchar(20)    NULL,   --第三销售内部编号              
  thirdsaleamt  float     NULL,   --第三销售分享金额              
  fourthsalerid  varchar(20)    NULL,   --第四销售工号              
  fourthsalerinid  varchar(20)    NULL,   --第四销售内部编号              
  fourthsaleamt  float     NULL,   --第四销售分享金额              
  fifthsalerid  varchar(20)    NULL,   --第五销售工号 -----烫染师              
  fifthsalerinid  varchar(20)    NULL,   --第五销售内部编号              
  fifthsaleamt  float     NULL,   --第五销售分享金额              
  sixthsalerid  varchar(20)    NULL,   --第六销售工号----- 烫染师              
  sixthsalerinid  varchar(20)    NULL,   --第六销售内部编号              
  sixthsaleamt  float     NULL,   --第六销售分享金额              
  seventhsalerid  varchar(20)    NULL,   --第七销售工号 -----烫染师              
  seventhsalerinid varchar(20)    NULL,   --第七销售内部编号              
  seventhsaleamt  float     NULL,   --第七销售分享金额              
  eighthsalerid  varchar(20)    NULL,   --第八销售工号----- 烫染师              
  eighthsalerinid  varchar(20)    NULL,   --第八销售内部编号              
  eighthsaleamt  float     NULL,   --第八销售分享金额              
  ninthsalerid  varchar(20)    NULL,   --第九销售工号-----              
  ninthsalerinid  varchar(20)    NULL,   --第九销售内部编号              
  ninthsaleamt  float     NULL,   --第九销售分享金额              
  tenthsalerid  varchar(20)    NULL,   --第十销售工号----- 烫染师              
  tenthsalerinid  varchar(20)    NULL,   --第十销售内部编号              
  tenthsaleamt  float     NULL,   --第十销售分享金额              
  financedate   varchar(8)    NULL,   --帐务日期               
  salekeepamt   float     NULL, --储值金额              
  paymode    varchar(10)    NULL, --支付方式              
  payamt    float     NULL, --支付金额     
  billinsertype   int      NULL, --充值主办方 1 美容 2 美发              
 )                   
    insert #msalecardinfo(salecompid,salebillid,saledate,salecardno,salecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,              
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,              
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,              
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,salekeepamt,paymode,payamt,billinsertype)              
 select salecompid,salebillid,saledate,salecardno,salecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,              
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,              
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,              
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,salekeepamt,'',0,ISNULL(billinsertype,0)              
    from msalecardinfo              
    where salecompid=@compid and financedate between @fromdate and @todate and ISNULL(salekeepamt,0)<>0 and ISNULL(salebakflag,0)=0   and ISNULL(backflag,0)=0             
                                            
                       
              
                            
   if exists(select 1 from #msalecardinfo)                                      
   begin                            
  declare @e0003_salebillid varchar(20)               
  declare @e0003_salecardno varchar(20)                         
  declare @e0003_bill_msalecardinfo varchar(1024)                                      
  set @e0003_bill_msalecardinfo = ''                                      
                              
  declare @e0003_firstsalerid varchar(20), @e0003_secondsalerid varchar(20), @e0003_thirdsalerid varchar(20),              
    @e0003_fourthsalerid varchar(20),@e0003_fifthsalerid varchar(20) , @e0003_sixthsalerid varchar(20) ,              
    @e0003_seventhsalerid varchar(20) ,@e0003_eighthsalerid varchar(20) , @e0003_ninthsalerid varchar(20) , @e0003_tenthsalerid varchar(20)                 
  declare @e0003_firstsaleamt float, @e0003_secondsaleamt float, @e0003_thirdsaleamt float,              
    @e0003_fourthsaleamt float, @e0003_fifthsaleamt float, @e0003_sixthsaleamt float,               
    @e0003_seventhsaleamt float, @e0003_eighthsaleamt float, @e0003_ninthsaleamt float,@e0003_tenthsaleamt float,@e0003_salekeepamt float                              
                              
  declare cur_#msalecardinfo03err3 cursor for                                      
  select salebillid,salecardno,              
    isnull(firstsalerid,''),isnull(secondsalerid,''),isnull(thirdsalerid,''),isnull(fourthsalerid,''),isnull(fifthsalerid,''),isnull(sixthsalerid,''),isnull(seventhsalerid,''),isnull(eighthsalerid,''),isnull(ninthsalerid,''),isnull(tenthsalerid,''),      
  
     firstsaleamt,secondsaleamt,thirdsaleamt,fourthsaleamt,fifthsaleamt,sixthsaleamt,seventhsaleamt,eighthsaleamt,ninthsaleamt,tenthsaleamt,salekeepamt,ISNULL(billinsertype,0)              
     from #msalecardinfo                                       
  open cur_#msalecardinfo03err3                                      
  fetch cur_#msalecardinfo03err3 into  @e0003_salebillid,@e0003_salecardno,              
  @e0003_firstsalerid,@e0003_secondsalerid,@e0003_thirdsalerid,@e0003_fourthsalerid,              
  @e0003_fifthsalerid,@e0003_sixthsalerid,@e0003_seventhsalerid,@e0003_eighthsalerid,              
  @e0003_ninthsalerid,@e0003_tenthsalerid,                            
        @e0003_firstsaleamt,@e0003_secondsaleamt,@e0003_thirdsaleamt,@e0003_fourthsaleamt,              
        @e0003_fifthsaleamt,@e0003_sixthsaleamt,@e0003_seventhsaleamt,@e0003_eighthsaleamt,               
        @e0003_ninthsaleamt,@e0003_tenthsaleamt,@e0003_salekeepamt,@billinsertype                          
                                                  
  while(@@fetch_status=0)                                      
  begin                
                
   set @mfstaffshareamt=0           
   set @mrstaffshareamt=0           
   set @mfstaffsharecount=0              
   set @trstaffshareamt=0              
   set @trstaffsharecount=0              
   set @totalshareamt=0              
   set @e0003_bill_msalecardinfo=''              
   --第一销售              
   if(isnull(@e0003_firstsalerid,'')<>'' and ISNULL(@e0003_firstsaleamt,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_firstsalerid=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_firstsaleamt,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end         
    if  exists( select 1 from #emp_deperror where @e0003_firstsalerid=empid and emppos  in ('004','00401','00402'))              
    begin              
     set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_firstsaleamt,0)        
    end                 
    if  exists( select 1 from #emp_deperror where @e0003_firstsalerid=empid and emppos  in ('008','00901','00902','00903','00904') )                
    begin              
     if(ISNULL(@e0003_firstsaleamt,0)>10000)              
        set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'第一销售烫发师分享业绩不能超过10000'              
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_firstsaleamt,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
    end               
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_firstsaleamt,0)              
   end              
   --第二销售              
   if(isnull(@e0003_secondsalerid,'')<>'' and ISNULL(@e0003_secondsaleamt,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_secondsalerid=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_secondsaleamt,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end         
    if  exists( select 1 from #emp_deperror where @e0003_secondsalerid=empid and emppos  in ('004','00401','00402'))              
    begin              
     set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_secondsaleamt,0)        
    end                  
    if  exists( select 1 from #emp_deperror where @e0003_secondsalerid=empid and emppos  in ('008','00901','00902','00903','00904') )                
    begin              
     if(ISNULL(@e0003_secondsaleamt,0)>10000)              
        set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'第二销售烫发师分享业绩不能超过10000'              
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_secondsaleamt,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
    end               
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_secondsaleamt,0)              
   end              
   --第三销售              
   if(isnull(@e0003_thirdsalerid,'')<>'' and ISNULL(@e0003_thirdsaleamt,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_thirdsalerid=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_thirdsaleamt,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end        
    if  exists( select 1 from #emp_deperror where @e0003_thirdsalerid=empid and emppos  in ('004','00401','00402'))              
    begin              
     set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_thirdsaleamt,0)        
    end                  
    if  exists( select 1 from #emp_deperror where @e0003_thirdsalerid=empid and emppos  in ('008','00901','00902','00903','00904') )                
    begin              
     if(ISNULL(@e0003_thirdsaleamt,0)>10000)              
        set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'第三销售烫发师分享业绩不能超过10000'              
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_thirdsaleamt,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
    end               
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_thirdsaleamt,0)              
   end              
   --第四销售              
   if(isnull(@e0003_fourthsalerid,'')<>'' and ISNULL(@e0003_fourthsaleamt,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_fourthsalerid=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_fourthsaleamt,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end        
    if  exists( select 1 from #emp_deperror where @e0003_fourthsalerid=empid and emppos  in ('004','00401','00402'))              
    begin              
     set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_fourthsaleamt,0)        
    end                
    if  exists( select 1 from #emp_deperror where @e0003_fourthsalerid=empid and emppos  in ('008','00901','00902','00903','00904') )                
    begin              
     if(ISNULL(@e0003_fourthsaleamt,0)>10000)              
        set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'第四销售烫发师分享业绩不能超过10000'              
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_fourthsaleamt,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
    end               
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_fourthsaleamt,0)              
   end              
   --第五销售              
   if(isnull(@e0003_fifthsalerid,'')<>'' and ISNULL(@e0003_fifthsaleamt,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_fifthsalerid=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_fifthsaleamt,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end        
    if  exists( select 1 from #emp_deperror where @e0003_fifthsalerid=empid and emppos  in ('004','00401','00402'))              
    begin              
     set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_fifthsaleamt,0)        
    end                
    if  exists( select 1 from #emp_deperror where @e0003_fifthsalerid=empid and emppos  in ('008','00901','00902','00903','00904') )             
    begin              
     if(ISNULL(@e0003_fifthsaleamt,0)>10000)              
        set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'第五销售烫发师分享业绩不能超过10000'              
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_fifthsaleamt,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
                   
    end               
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_fifthsaleamt,0)              
   end              
   --第六销售              
   if(isnull(@e0003_sixthsalerid,'')<>'' and ISNULL(@e0003_sixthsaleamt,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_sixthsalerid=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_sixthsaleamt,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end        
    if  exists( select 1 from #emp_deperror where @e0003_sixthsalerid=empid and emppos  in ('004','00401','00402'))              
    begin              
     set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_sixthsaleamt,0)        
    end                 
    if  exists( select 1 from #emp_deperror where @e0003_sixthsalerid=empid and emppos  in ('008','00901','00902','00903','00904') )                
    begin              
     if(ISNULL(@e0003_sixthsaleamt,0)>10000)              
        set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'第六销售烫发师分享业绩不能超过10000'              
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_sixthsaleamt,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
    end               
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_sixthsaleamt,0)              
   end              
   --第七销售              
   if(isnull(@e0003_seventhsalerid,'')<>'' and ISNULL(@e0003_seventhsaleamt,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_seventhsalerid=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_seventhsaleamt,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end        
    if  exists( select 1 from #emp_deperror where @e0003_seventhsalerid=empid and emppos  in ('004','00401','00402'))              
    begin              
     set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_seventhsaleamt,0)        
    end                
    if  exists( select 1 from #emp_deperror where @e0003_seventhsalerid=empid and emppos  in ('008','00901','00902','00903','00904') )                
    begin              
     if(ISNULL(@e0003_seventhsaleamt,0)>10000)              
        set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'第七销售烫发师分享业绩不能超过10000'               
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_seventhsaleamt,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
    end              
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_seventhsaleamt,0)               
   end              
                               
   --第八销售              
   if(isnull(@e0003_eighthsalerid,'')<>'' and ISNULL(@e0003_eighthsaleamt,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_eighthsalerid=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_eighthsaleamt,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end        
    if  exists( select 1 from #emp_deperror where @e0003_eighthsalerid=empid and emppos  in ('004','00401','00402'))              
    begin              
     set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_eighthsaleamt,0)        
    end                
    if  exists( select 1 from #emp_deperror where @e0003_eighthsalerid=empid and emppos  in ('008','00901','00902','00903','00904') )      
    begin              
     if(ISNULL(@e0003_eighthsaleamt,0)>10000)              
        set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'第八销售烫发师分享业绩不能超过10000'               
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_eighthsaleamt,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
    end               
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_eighthsaleamt,0)              
   end              
   --第九销售              
   if(isnull(@e0003_ninthsalerid,'')<>'' and ISNULL(@e0003_ninthsaleamt,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_ninthsalerid=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_ninthsaleamt,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end        
     if  exists( select 1 from #emp_deperror where @e0003_ninthsalerid=empid and emppos  in ('004','00401','00402'))              
    begin              
     set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_ninthsaleamt,0)        
    end                
    if  exists( select 1 from #emp_deperror where @e0003_ninthsalerid=empid and emppos  in ('008','00901','00902','00903','00904') )                
    begin              
     if(ISNULL(@e0003_ninthsaleamt,0)>10000)              
        set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'第九销售烫发师分享业绩不能超过10000'               
 set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_ninthsaleamt,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
    end               
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_ninthsaleamt,0)              
   end              
                 
   --第十销售              
   if(isnull(@e0003_tenthsalerid,'')<>'' and ISNULL(@e0003_tenthsaleamt,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_tenthsalerid=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_tenthsaleamt,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end         
     if  exists( select 1 from #emp_deperror where @e0003_tenthsalerid=empid and emppos  in ('004','00401','00402'))              
    begin              
     set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_tenthsaleamt,0)        
    end                
    if  exists( select 1 from #emp_deperror where @e0003_tenthsalerid=empid and emppos  in ('008','00901','00902','00903','00904') )                
    begin              
     if(ISNULL(@e0003_tenthsaleamt,0)>10000)              
        set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'第十销售烫发师分享业绩不能超过10000'                
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_tenthsaleamt,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
    end               
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_tenthsaleamt,0)              
   end                  
   if(ISNULL(@trstaffshareamt,0)>ISNULL(@mfstaffshareamt,0))              
 set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'美发人员与对应的烫发师分享业绩不匹配'                             
    if(ISNULL(@trstaffsharecount,0)>ISNULL(@mfstaffsharecount,0))              
        set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'分享美发人员少于烫发师人数'       
         
    if(ISNULL(@billinsertype,0)=0 or ISNULL(@sp102,'0')='0') --不区分卡金    
    begin                
  if(convert(numeric(20,1),ISNULL(@totalshareamt,0)-ISNULL(@trstaffshareamt,0))<>convert(numeric(20,1),ISNULL(@e0003_salekeepamt,0)))              
  set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'没有完全分享'     
 end      
 else  if(ISNULL(@billinsertype,0)=1 and ISNULL(@sp102,'0')='1') --美容卡金     
 begin    
  if(convert(numeric(20,1),ISNULL(@mrstaffshareamt,0))<>convert(numeric(20,1),ISNULL(@e0003_salekeepamt,0)))              
   set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'没有完全分享(美容卡金)'     
 end       
    else  if(ISNULL(@billinsertype,0)=2 and ISNULL(@sp102,'0')='1') --美发卡金     
 begin    
  if(convert(numeric(20,1),ISNULL(@mfstaffshareamt,0))<>convert(numeric(20,1),ISNULL(@e0003_salekeepamt,0)))              
   set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'没有完全分享(美发卡金)'     
 end     
         
    if(@compid in ('006','047','033','014','041','046') and @todate between '20140501' and '20140515' )        
    begin        
  if(convert(numeric(20,2),ISNULL(@mfstaffshareamt,0)/ISNULL(@totalshareamt,0))>0.5)        
   set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'美发业绩分享超出了总业绩的50%'         
  if(convert(numeric(20,2),ISNULL(@mrstaffshareamt,0)/ISNULL(@totalshareamt,0))>0.5)        
   set @e0003_bill_msalecardinfo=isnull(@e0003_bill_msalecardinfo,'')+'美容业绩分享超出了总业绩的50%'         
 end           
   if(@e0003_bill_msalecardinfo<>'')                            
   begin                              
    insert into #result(compid,billtype,errid,remark)                                      
    values(@compid,'1','E0003',@e0003_bill_msalecardinfo+'单号'+@e0003_salebillid)                              
   end                              
                                    
   fetch cur_#msalecardinfo03err3 into  @e0003_salebillid,@e0003_salecardno,              
  @e0003_firstsalerid,@e0003_secondsalerid,@e0003_thirdsalerid,@e0003_fourthsalerid,              
  @e0003_fifthsalerid,@e0003_sixthsalerid,@e0003_seventhsalerid,@e0003_eighthsalerid,              
  @e0003_ninthsalerid,@e0003_tenthsalerid,                            
        @e0003_firstsaleamt,@e0003_secondsaleamt,@e0003_thirdsaleamt,@e0003_fourthsaleamt,              
        @e0003_fifthsaleamt,@e0003_sixthsaleamt,@e0003_seventhsaleamt,@e0003_eighthsaleamt,               
        @e0003_ninthsaleamt,@e0003_tenthsaleamt  ,@e0003_salekeepamt,@billinsertype                            
  end                                      
  close cur_#msalecardinfo03err3                                      
  deallocate cur_#msalecardinfo03err3                                      
                                      
 end                                      
                                                     
 drop table #msalecardinfo                                    
                                  
    CREATE tAbLE #mcardrechargeinfo              -- 会员卡充值单              
 (              
  rechargecompid   varchar(10)   Not NULL,   --充值门店              
  rechargebillid   varchar(20)   Not NULL,   --充值单号               
  rechargedate   varchar(8)    NULL,   --充值日期               
  rechargetime   varchar(6)    NULL,   --充值时间               
rechargecardno   varchar(20)    NULL,   --会员卡号              
  rechargecardtype  varchar(10)    NULL,   --卡类型              
  rechargetype   int      NULL,   --续费方式( 0充值 ,6还款 ,)              
  firstsalerid  varchar(20)    NULL,   --第一销售工号              
  firstsalerinid  varchar(20)    NULL,   --第一销售内部编号              
  firstsaleamt  float     NULL,   --第一销售分享金额              
  secondsalerid  varchar(20)    NULL,   --第二销售工号              
  secondsalerinid  varchar(20)    NULL,   --第二销售内部编号              
  secondsaleamt  float     NULL,   --第二销售分享金额              
  thirdsalerid  varchar(20)    NULL,   --第三销售工号              
  thirdsalerinid  varchar(20)    NULL,   --第三销售内部编号              
  thirdsaleamt  float     NULL,   --第三销售分享金额              
  fourthsalerid  varchar(20)    NULL,   --第四销售工号              
  fourthsalerinid  varchar(20)    NULL,   --第四销售内部编号              
  fourthsaleamt  float     NULL,   --第四销售分享金额              
  fifthsalerid  varchar(20)    NULL,   --第五销售工号 -----烫染师              
  fifthsalerinid  varchar(20)    NULL,   --第五销售内部编号              
  fifthsaleamt  float     NULL,   --第五销售分享金额              
  sixthsalerid  varchar(20)    NULL,   --第六销售工号----- 烫染师              
  sixthsalerinid  varchar(20)    NULL,   --第六销售内部编号              
  sixthsaleamt  float     NULL,   --第六销售分享金额              
  seventhsalerid  varchar(20)    NULL,   --第七销售工号 -----烫染师              
  seventhsalerinid varchar(20)    NULL,   --第七销售内部编号              
  seventhsaleamt  float     NULL,   --第七销售分享金额              
  eighthsalerid  varchar(20)    NULL,   --第八销售工号----- 烫染师              
  eighthsalerinid  varchar(20)    NULL,   --第八销售内部编号              
  eighthsaleamt  float     NULL,   --第八销售分享金额              
  ninthsalerid  varchar(20)    NULL,   --第九销售工号-----              
  ninthsalerinid  varchar(20)    NULL,   --第九销售内部编号              
  ninthsaleamt  float     NULL,   --第九销售分享金额              
  tenthsalerid  varchar(20)    NULL,   --第十销售工号----- 烫染师              
  tenthsalerinid  varchar(20)    NULL,   --第十销售内部编号              
  tenthsaleamt  float     NULL,   --第十销售分享金额              
  financedate   varchar(8)    NULL,   --帐务日期               
  rechargekeepamt  float     NULL,   --充值金额              
  paymode    varchar(10)    NULL, --支付方式              
  payamt    float     NULL, --支付金额       
  billinsertype   int      NULL, --充值主办方 1 美容 2 美发             
 )                   
               
 insert #mcardrechargeinfo(rechargecompid,rechargebillid,rechargedate,rechargetype,rechargecardno,rechargecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,              
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,              
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,              
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,rechargekeepamt,paymode,payamt,billinsertype)              
 select rechargecompid,rechargebillid,rechargedate,rechargetype,rechargecardno,rechargecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,              
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,              
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,              
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,rechargekeepamt,'',0,isnull(billinsertype,0)              
    from mcardrechargeinfo              
    where  rechargecompid=@compid and financedate between @fromdate and @todate and ISNULL(rechargekeepamt,0)<>0 and ISNULL(salebakflag,0)=0    and ISNULL(backflag,0)=0                 
                   
   if exists(select 1 from #mcardrechargeinfo)                                      
   begin                                      
  declare @e0003_bill_mcardrechargeinfo varchar(1024)                                      
  set @e0003_bill_mcardrechargeinfo = ''                                      
        declare @e0003_rechargebillid varchar(20)                       
  declare @e0003_rechargecardno varchar(20)                              
  declare @e0003_firstsalerid_cz varchar(20), @e0003_secondsalerid_cz varchar(20), @e0003_thirdsalerid_cz varchar(20),              
    @e0003_fourthsalerid_cz varchar(20),@e0003_fifthsalerid_cz varchar(20) , @e0003_sixthsalerid_cz varchar(20) ,              
    @e0003_seventhsalerid_cz varchar(20) ,@e0003_eighthsalerid_cz varchar(20) , @e0003_ninthsalerid_cz varchar(20) , @e0003_tenthsalerid_cz varchar(20)                 
  declare @e0003_firstsaleamt_cz float, @e0003_secondsaleamt_cz float, @e0003_thirdsaleamt_cz float,              
    @e0003_fourthsaleamt_cz float, @e0003_fifthsaleamt_cz float, @e0003_sixthsaleamt_cz float,               
    @e0003_seventhsaleamt_cz float, @e0003_eighthsaleamt_cz float, @e0003_ninthsaleamt_cz float,@e0003_tenthsaleamt_cz float,@e0003_rechargekeepamt_cz float                 
                           
  declare cur_#mcardrechargeinfo03err3 cursor for                                      
  select rechargebillid,rechargecardno,               
    isnull(firstsalerid,''),isnull(secondsalerid,''),isnull(thirdsalerid,''),isnull(fourthsalerid,''),isnull(fifthsalerid,''),isnull(sixthsalerid,''),isnull(seventhsalerid,''),isnull(eighthsalerid,''),isnull(ninthsalerid,''),isnull(tenthsalerid,''),      
 
     
   firstsaleamt,secondsaleamt,thirdsaleamt,fourthsaleamt,fifthsaleamt,sixthsaleamt,seventhsaleamt,eighthsaleamt,ninthsaleamt,tenthsaleamt,rechargekeepamt,isnull(billinsertype,0)              
  from #mcardrechargeinfo                            
  open cur_#mcardrechargeinfo03err3                                      
  fetch cur_#mcardrechargeinfo03err3 into  @e0003_rechargebillid,@e0003_rechargecardno,              
  @e0003_firstsalerid_cz,@e0003_secondsalerid_cz,@e0003_thirdsalerid_cz,@e0003_fourthsalerid_cz,              
  @e0003_fifthsalerid_cz,@e0003_sixthsalerid_cz,@e0003_seventhsalerid_cz,@e0003_eighthsalerid_cz,              
  @e0003_ninthsalerid_cz,@e0003_tenthsalerid_cz,                            
        @e0003_firstsaleamt_cz,@e0003_secondsaleamt_cz,@e0003_thirdsaleamt_cz,@e0003_fourthsaleamt_cz,              
        @e0003_fifthsaleamt_cz,@e0003_sixthsaleamt_cz,@e0003_seventhsaleamt_cz,@e0003_eighthsaleamt_cz,               
        @e0003_ninthsaleamt_cz,@e0003_tenthsaleamt_cz,@e0003_rechargekeepamt_cz,@billinsertype                             
                                                   
  while(@@fetch_status=0)                                      
  begin                       
                               
   set @e0003_bill_mcardrechargeinfo=''              
   set @mfstaffshareamt=0              
   set @mfstaffsharecount=0              
   set @trstaffshareamt=0              
   set @trstaffsharecount=0              
   set @totalshareamt=0           
   set @mrstaffshareamt=0           
   --第一销售              
   if(isnull(@e0003_firstsalerid_cz,'')<>'' and ISNULL(@e0003_firstsaleamt_cz,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_firstsalerid_cz=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_firstsaleamt_cz,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end        
    if  exists( select 1 from #emp_deperror where @e0003_firstsalerid_cz=empid and emppos  in ('004','00401','00402'))              
    begin              
 set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_firstsaleamt_cz,0)          
    end                   
    if  exists( select 1 from #emp_deperror where @e0003_firstsalerid_cz=empid and emppos  in ('008','00901','00902','00903','00904') )                
    begin              
     if(ISNULL(@e0003_firstsaleamt_cz,0)>10000)              
        set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_mcardrechargeinfo,'')+'第一销售烫发师分享业绩不能超过10000'              
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_firstsaleamt_cz,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
    end               
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_firstsaleamt_cz,0)              
   end              
   --第二销售              
   if(isnull(@e0003_secondsalerid_cz,'')<>'' and ISNULL(@e0003_secondsaleamt_cz,0)>0)               
   begin              
                 
    if  exists( select 1 from #emp_deperror where @e0003_secondsalerid_cz=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_secondsaleamt_cz,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end        
    if  exists( select 1 from #emp_deperror where @e0003_secondsalerid_cz=empid and emppos  in ('004','00401','00402'))              
    begin              
  set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_secondsaleamt_cz,0)          
    end                
    if  exists( select 1 from #emp_deperror where @e0003_secondsalerid_cz=empid and emppos  in ('008','00901','00902','00903','00904') )                
    begin              
     if(ISNULL(@e0003_secondsaleamt_cz,0)>10000)          
        set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_mcardrechargeinfo,'')+'第二销售烫发师分享业绩不能超过10000'              
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_secondsaleamt_cz,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
    end               
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_secondsaleamt_cz,0)              
   end              
                 
   --第三销售              
   if(isnull(@e0003_thirdsalerid_cz,'')<>'' and ISNULL(@e0003_thirdsaleamt_cz,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_thirdsalerid_cz=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_thirdsaleamt_cz,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end        
    if  exists( select 1 from #emp_deperror where @e0003_thirdsalerid_cz=empid and emppos  in ('004','00401','00402'))              
    begin              
  set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_thirdsaleamt_cz,0)          
    end                 
    if  exists( select 1 from #emp_deperror where @e0003_thirdsalerid_cz=empid and emppos  in ('008','00901','00902','00903','00904') )                
    begin              
     if(ISNULL(@e0003_thirdsaleamt_cz,0)>10000)              
        set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_mcardrechargeinfo,'')+'第三销售烫发师分享业绩不能超过10000'              
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_thirdsaleamt_cz,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
    end               
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_thirdsaleamt_cz,0)              
   end              
   --第四销售              
   if(isnull(@e0003_fourthsalerid_cz,'')<>'' and ISNULL(@e0003_fourthsaleamt_cz,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_fourthsalerid_cz=empid and emppos  in ('00102','003','006','007','00701','00702'))              begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_fourthsaleamt_cz,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end        
    if  exists( select 1 from #emp_deperror where @e0003_fourthsalerid_cz=empid and emppos  in ('004','00401','00402'))              
    begin              
  set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_fourthsaleamt_cz,0)          
    end                
    if  exists( select 1 from #emp_deperror where @e0003_fourthsalerid_cz=empid and emppos  in ('008','00901','00902','00903','00904') )                
    begin              
   if(ISNULL(@e0003_fourthsaleamt_cz,0)>10000)              
        set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_mcardrechargeinfo,'')+'第四销售烫发师分享业绩不能超过10000'              
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_fourthsaleamt_cz,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
    end               
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_fourthsaleamt_cz,0)              
   end              
                 
   --第五销售              
               
   if(isnull(@e0003_fifthsalerid_cz,'')<>'' and ISNULL(@e0003_fifthsaleamt_cz,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_fifthsalerid_cz=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_fifthsaleamt_cz,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end        
    if  exists( select 1 from #emp_deperror where @e0003_fifthsalerid_cz=empid and emppos  in ('004','00401','00402'))              
    begin              
  set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_fifthsaleamt_cz,0)          
    end                
    if  exists( select 1 from #emp_deperror where @e0003_fifthsalerid_cz=empid and emppos  in ('008','00901','00902','00903','00904') )             
    begin              
     if(ISNULL(@e0003_fifthsaleamt_cz,0)>10000)              
        set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_mcardrechargeinfo,'')+'第五销售烫发师分享业绩不能超过10000'              
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_fifthsaleamt_cz,0)              
     set @trstaffsharecount=@trstaffsharecount+1           
    end               
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_fifthsaleamt_cz,0)              
   end              
   --第六销售              
   if(isnull(@e0003_sixthsalerid_cz,'')<>'' and ISNULL(@e0003_sixthsaleamt_cz,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_sixthsalerid_cz=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_sixthsaleamt_cz,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end        
    if  exists( select 1 from #emp_deperror where @e0003_sixthsalerid_cz=empid and emppos  in ('004','00401','00402'))              
    begin              
  set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_sixthsaleamt_cz,0)          
    end                
    if  exists( select 1 from #emp_deperror where @e0003_sixthsalerid_cz=empid and emppos  in ('008','00901','00902','00903','00904') )                
    begin              
     if(ISNULL(@e0003_sixthsaleamt_cz,0)>10000)              
        set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_mcardrechargeinfo,'')+'第六销售烫发师分享业绩不能超过10000'              
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_sixthsaleamt_cz,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
    end               
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_sixthsaleamt_cz,0)              
   end              
   --第七销售              
   if(isnull(@e0003_seventhsalerid_cz,'')<>'' and ISNULL(@e0003_seventhsaleamt_cz,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_seventhsalerid_cz=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_seventhsaleamt_cz,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end        
    if  exists( select 1 from #emp_deperror where @e0003_seventhsalerid_cz=empid and emppos  in ('004','00401','00402'))              
    begin              
  set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_seventhsaleamt_cz,0)          
 end                
    if  exists( select 1 from #emp_deperror where @e0003_seventhsalerid_cz=empid and emppos  in ('008','00901','00902','00903','00904') )                
    begin              
     if(ISNULL(@e0003_seventhsaleamt_cz,0)>10000)              
        set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_mcardrechargeinfo,'')+'第七销售烫发师分享业绩不能超过10000'               
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_seventhsaleamt_cz,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
    end               
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_seventhsaleamt_cz,0)              
   end              
                               
   --第八销售              
   if(isnull(@e0003_eighthsalerid_cz,'')<>'' and ISNULL(@e0003_eighthsaleamt_cz,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_eighthsalerid_cz=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_eighthsaleamt_cz,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end        
    if  exists( select 1 from #emp_deperror where @e0003_eighthsalerid_cz=empid and emppos  in ('004','00401','00402'))              
    begin              
  set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_eighthsaleamt_cz,0)          
    end                 
    if  exists( select 1 from #emp_deperror where @e0003_eighthsalerid_cz=empid and emppos  in ('008','00901','00902','00903','00904') )                
    begin              
     if(ISNULL(@e0003_eighthsaleamt_cz,0)>10000)              
        set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_mcardrechargeinfo,'')+'第八销售烫发师分享业绩不能超过10000'               
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_eighthsaleamt_cz,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
    end              
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_eighthsaleamt_cz,0)               
   end              
   --第九销售              
   if(isnull(@e0003_ninthsalerid_cz,'')<>'' and ISNULL(@e0003_ninthsaleamt_cz,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_ninthsalerid_cz=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_ninthsaleamt_cz,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end        
    if  exists( select 1 from #emp_deperror where @e0003_ninthsalerid_cz=empid and emppos  in ('004','00401','00402'))              
    begin              
  set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_ninthsaleamt_cz,0)          
    end                
    if  exists( select 1 from #emp_deperror where @e0003_ninthsalerid_cz=empid and emppos  in ('008','00901','00902','00903','00904') )                
    begin              
     if(ISNULL(@e0003_ninthsaleamt_cz,0)>10000)              
        set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_mcardrechargeinfo,'')+'第九销售烫发师分享业绩不能超过10000'               
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_ninthsaleamt_cz,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
    end              
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_ninthsaleamt_cz,0)               
   end              
                 
   --第十销售              
   if(isnull(@e0003_tenthsalerid_cz,'')<>'' and ISNULL(@e0003_tenthsaleamt_cz,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_tenthsalerid_cz=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_tenthsaleamt_cz,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end        
    if  exists( select 1 from #emp_deperror where @e0003_tenthsalerid_cz=empid and emppos  in ('004','00401','00402'))              
    begin              
  set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_tenthsaleamt_cz,0)          
    end                
    if  exists( select 1 from #emp_deperror where @e0003_tenthsalerid_cz=empid and emppos  in ('008','00901','00902','00903','00904') )                
    begin              
     if(ISNULL(@e0003_tenthsaleamt_cz,0)>10000)              
        set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_mcardrechargeinfo,'')+'第十销售烫发师分享业绩不能超过10000'                
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_tenthsaleamt_cz,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
    end              
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_tenthsaleamt_cz,0)                
   end                  
                 
 if(ISNULL(@trstaffshareamt,0)>ISNULL(@mfstaffshareamt,0))              
   set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_mcardrechargeinfo,'')+'美发人员与对应的烫发师分享业绩不匹配'                             
    if(ISNULL(@trstaffsharecount,0)>ISNULL(@mfstaffsharecount,0))              
        set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_mcardrechargeinfo,'')+'分享美发人员少于烫发师人数'     
                      
 if(ISNULL(@billinsertype,0)=0 or ISNULL(@sp102,'0')='0') --不区分卡金    
    begin                
   if(convert(numeric(20,1),ISNULL(@totalshareamt,0)-ISNULL(@trstaffshareamt,0))<>convert(numeric(20,1),ISNULL(@e0003_rechargekeepamt_cz,0)))    
   set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_mcardrechargeinfo,'')+'没有完全分享'      
 end      
 else  if(ISNULL(@billinsertype,0)=1 and ISNULL(@sp102,'0')='1') --美容卡金     
 begin    
  if(convert(numeric(20,1),ISNULL(@mrstaffshareamt,0))<>convert(numeric(20,1),ISNULL(@e0003_rechargekeepamt_cz,0)))              
   set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_mcardrechargeinfo,'')+'没有完全分享(美容卡金)'     
 end       
    else  if(ISNULL(@billinsertype,0)=2 and ISNULL(@sp102,'0')='1') --美发卡金     
 begin    
  if(convert(numeric(20,1),ISNULL(@mfstaffshareamt,0))<>convert(numeric(20,1),ISNULL(@e0003_rechargekeepamt_cz,0)))              
   set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_mcardrechargeinfo,'')+'没有完全分享(美发卡金)'     
 end     
             
 if(@compid in ('006','047','033','014','041','046') and @todate between '20140501' and '20140515')        
 begin        
 if(convert(numeric(20,2),ISNULL(@mfstaffshareamt,0)/ISNULL(@totalshareamt,0))>0.5)        
  set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_mcardrechargeinfo,'')+'美发业绩分享超出了总业绩的50%'         
 if(convert(numeric(20,2),ISNULL(@mrstaffshareamt,0)/ISNULL(@totalshareamt,0))>0.5)        
  set @e0003_bill_mcardrechargeinfo=isnull(@e0003_bill_mcardrechargeinfo,'')+'美容业绩分享超出了总业绩的50%'         
 end                   
   if(@e0003_bill_mcardrechargeinfo<>'')                            
   begin                              
    insert into #result(compid,billtype,errid,remark)                                      
    values(@compid,'2','E0003',@e0003_bill_mcardrechargeinfo+'单号'+@e0003_rechargebillid)                              
   end                              
                                     
   fetch cur_#mcardrechargeinfo03err3 into  @e0003_rechargebillid,@e0003_rechargecardno,              
    @e0003_firstsalerid_cz,@e0003_secondsalerid_cz,@e0003_thirdsalerid_cz,@e0003_fourthsalerid_cz,              
    @e0003_fifthsalerid_cz,@e0003_sixthsalerid_cz,@e0003_seventhsalerid_cz,@e0003_eighthsalerid_cz,              
    @e0003_ninthsalerid_cz,@e0003_tenthsalerid_cz,                            
    @e0003_firstsaleamt_cz,@e0003_secondsaleamt_cz,@e0003_thirdsaleamt_cz,@e0003_fourthsaleamt_cz,              
    @e0003_fifthsaleamt_cz,@e0003_sixthsaleamt_cz,@e0003_seventhsaleamt_cz,@e0003_eighthsaleamt_cz,               
    @e0003_ninthsaleamt_cz,@e0003_tenthsaleamt_cz,@e0003_rechargekeepamt_cz,@billinsertype                 
      end                      
   close cur_#mcardrechargeinfo03err3                                      
   deallocate cur_#mcardrechargeinfo03err3                                    
   end              
                    
     drop table #mcardrechargeinfo                  
                   
                   
        CREATE tAbLE #mcardchangeinfo             -- 会员卡异动              
 (              
  changecompid  varchar(10)   Not NULL,   --充值门店              
  changebillid  varchar(20)   Not NULL,   --充值单号               
  changetype   int     Not NULL,              
  changedate   varchar(8)    NULL,   --充值日期               
  changetime   varchar(6)    NULL,   --充值时间               
  changeaftercardno varchar(20)    NULL,   --会员卡号              
  changeaftercardtype varchar(10)    NULL,   --卡类型              
  firstsalerid  varchar(20)    NULL,   --第一销售工号              
  firstsalerinid  varchar(20)    NULL,   --第一销售内部编号              
  firstsaleamt float     NULL,   --第一销售分享金额              
  secondsalerid  varchar(20)    NULL,   --第二销售工号              
  secondsalerinid  varchar(20)    NULL,   --第二销售内部编号              
  secondsaleamt  float     NULL,   --第二销售分享金额              
  thirdsalerid  varchar(20)    NULL,   --第三销售工号              
  thirdsalerinid  varchar(20)    NULL,   --第三销售内部编号              
  thirdsaleamt  float     NULL,   --第三销售分享金额              
  fourthsalerid  varchar(20)    NULL,   --第四销售工号              
  fourthsalerinid  varchar(20)    NULL,   --第四销售内部编号              
  fourthsaleamt  float     NULL,   --第四销售分享金额              
  fifthsalerid  varchar(20)    NULL,   --第五销售工号 -----烫染师              
  fifthsalerinid  varchar(20)    NULL,   --第五销售内部编号              
  fifthsaleamt  float     NULL,   --第五销售分享金额              
  sixthsalerid  varchar(20)    NULL,   --第六销售工号----- 烫染师              
  sixthsalerinid  varchar(20)  NULL,   --第六销售内部编号              
  sixthsaleamt  float     NULL,   --第六销售分享金额              
  seventhsalerid  varchar(20)    NULL,   --第七销售工号 -----烫染师              
  seventhsalerinid varchar(20)    NULL,   --第七销售内部编号              
  seventhsaleamt  float     NULL,   --第七销售分享金额              
  eighthsalerid  varchar(20)    NULL,   --第八销售工号----- 烫染师              
  eighthsalerinid  varchar(20)    NULL,   --第八销售内部编号              
  eighthsaleamt  float     NULL,   --第八销售分享金额              
  ninthsalerid  varchar(20)    NULL,   --第九销售工号-----              
  ninthsalerinid  varchar(20)    NULL,   --第九销售内部编号              
  ninthsaleamt  float     NULL,   --第九销售分享金额              
  tenthsalerid  varchar(20)    NULL,   --第十销售工号----- 烫染师              
  tenthsalerinid  varchar(20)    NULL,   --第十销售内部编号              
  tenthsaleamt  float     NULL,   --第十销售分享金额              
  financedate   varchar(8)    NULL,   --帐务日期               
  changefillamt  float     NULL,   --充值金额              
  paymode    varchar(10)    NULL, --支付方式              
  payamt    float     NULL, --支付金额    
  billinsertype   int      NULL, --充值主办方 1 美容 2 美发              
 )                 
               
 insert #mcardchangeinfo(changecompid,changebillid,changetype,changedate,changeaftercardno,changeaftercardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,              
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,              
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,              
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,changefillamt,paymode,payamt,billinsertype)              
 select changecompid,changebillid,changetype,changedate,changeaftercardno,changeaftercardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,              
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,              
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,              
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,changefillamt,'',0,ISNULL(billinsertype,0)              
    from mcardchangeinfo              
    where  changecompid=@compid and financedate between @fromdate and @todate and ISNULL(changefillamt,0)<>0 and ISNULL(salebakflag,0)=0       and ISNULL(backflag,0)=0             
                    
                    
    if exists(select 1 from #mcardchangeinfo)                                      
 begin                                      
  declare @e0003_changebillid varchar(20)               
  declare @e0003_bill_mcardchangeinfo varchar(1024)                                      
  set @e0003_bill_mcardchangeinfo = ''                                      
  declare @e0003_firstsalerid_zk varchar(20), @e0003_secondsalerid_zk varchar(20), @e0003_thirdsalerid_zk varchar(20),              
    @e0003_fourthsalerid_zk varchar(20),@e0003_fifthsalerid_zk varchar(20) , @e0003_sixthsalerid_zk varchar(20) ,              
    @e0003_seventhsalerid_zk varchar(20) ,@e0003_eighthsalerid_zk varchar(20) , @e0003_ninthsalerid_zk varchar(20) , @e0003_tenthsalerid_zk varchar(20)                 
  declare @e0003_firstsaleamt_zk float, @e0003_secondsaleamt_zk float, @e0003_thirdsaleamt_zk float,              
    @e0003_fourthsaleamt_zk float, @e0003_fifthsaleamt_zk float, @e0003_sixthsaleamt_zk float,               
    @e0003_seventhsaleamt_zk float, @e0003_eighthsaleamt_zk float, @e0003_ninthsaleamt_zk float,@e0003_tenthsaleamt_zk float,@e0003_changefillamt_zk float                 
                         
  declare cur_#mcardchangeinfoerr3 cursor for                   
  select changebillid,               
    isnull(firstsalerid,''),isnull(secondsalerid,''),isnull(thirdsalerid,''),isnull(fourthsalerid,''),isnull(fifthsalerid,''),isnull(sixthsalerid,''),isnull(seventhsalerid,''),isnull(eighthsalerid,''),isnull(ninthsalerid,''),isnull(tenthsalerid,''),     
  
    
    firstsaleamt,secondsaleamt,thirdsaleamt,fourthsaleamt,fifthsaleamt,sixthsaleamt,seventhsaleamt,eighthsaleamt,ninthsaleamt,tenthsaleamt,changefillamt,ISNULL(billinsertype,0)              
  from #mcardchangeinfo                            
  open cur_#mcardchangeinfoerr3                                      
  fetch cur_#mcardchangeinfoerr3 into  @e0003_changebillid,              
    @e0003_firstsalerid_zk,@e0003_secondsalerid_zk,@e0003_thirdsalerid_zk,@e0003_fourthsalerid_zk,              
    @e0003_fifthsalerid_zk,@e0003_sixthsalerid_zk,@e0003_seventhsalerid_zk,@e0003_eighthsalerid_zk,              
    @e0003_ninthsalerid_zk,@e0003_tenthsalerid_zk,                            
    @e0003_firstsaleamt_zk,@e0003_secondsaleamt_zk,@e0003_thirdsaleamt_zk,@e0003_fourthsaleamt_zk,              
    @e0003_fifthsaleamt_zk,@e0003_sixthsaleamt_zk,@e0003_seventhsaleamt_zk,@e0003_eighthsaleamt_zk,               
    @e0003_ninthsaleamt_zk,@e0003_tenthsaleamt_zk,@e0003_changefillamt_zk,@billinsertype                                   
  while(@@fetch_status=0)                                      
  begin                
   set @e0003_bill_mcardchangeinfo=''              
   set @mfstaffshareamt=0              
   set @mfstaffsharecount=0              
   set @trstaffshareamt=0              
   set @trstaffsharecount=0              
   set @totalshareamt=0         
   set @mrstaffshareamt=0             
   --第一销售              
   if(isnull(@e0003_firstsalerid_zk,'')<>'' and ISNULL(@e0003_firstsaleamt_zk,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_firstsalerid_zk=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_firstsaleamt_zk,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end          
     if  exists( select 1 from #emp_deperror where @e0003_firstsalerid_zk=empid and emppos  in ('004','00401','00402'))              
     begin              
     set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_firstsaleamt_zk,0)              
     end               
    if  exists( select 1 from #emp_deperror where @e0003_firstsalerid_zk=empid and emppos  in ('008','00901','00902','00903','00904') )                
    begin              
     if(ISNULL(@e0003_firstsaleamt_zk,0)>10000)              
        set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_mcardchangeinfo,'')+'第一销售烫发师分享业绩不能超过10000'              
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_firstsaleamt_zk,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
    end              
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_firstsaleamt_zk,0)               
   end              
   --第二销售              
   if(isnull(@e0003_secondsalerid_zk,'')<>'' and ISNULL(@e0003_secondsaleamt_zk,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_secondsalerid_zk=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_secondsaleamt_zk,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end        
    if  exists( select 1 from #emp_deperror where @e0003_secondsalerid_zk=empid and emppos  in ('004','00401','00402'))              
    begin              
     set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_secondsaleamt_zk,0)              
    end                
    if  exists( select 1 from #emp_deperror where @e0003_secondsalerid_zk=empid and emppos  in ('008','00901','00902','00903','00904') )                
    begin              
     if(ISNULL(@e0003_secondsaleamt_zk,0)>10000)              
        set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_mcardchangeinfo,'')+'第二销售烫发师分享业绩不能超过10000'              
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_secondsaleamt_zk,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
    end              
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_secondsaleamt_zk,0)                
   end              
   --第三销售              
   if(isnull(@e0003_thirdsalerid_zk,'')<>'' and ISNULL(@e0003_thirdsaleamt_zk,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_thirdsalerid_zk=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_thirdsaleamt_zk,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end        
    if  exists( select 1 from #emp_deperror where @e0003_thirdsalerid_zk=empid and emppos  in ('004','00401','00402'))              
    begin              
     set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_thirdsaleamt_zk,0)              
    end                
    if  exists( select 1 from #emp_deperror where @e0003_thirdsalerid_zk=empid and emppos  in ('008','00901','00902','00903','00904') )                
    begin              
     if(ISNULL(@e0003_thirdsaleamt_zk,0)>10000)              
        set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_mcardchangeinfo,'')+'第三销售烫发师分享业绩不能超过10000'              
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_thirdsaleamt_zk,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
    end              
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_thirdsaleamt_zk,0)                 
   end              
   --第四销售              
   if(isnull(@e0003_fourthsalerid_zk,'')<>'' and ISNULL(@e0003_fourthsaleamt_zk,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_fourthsalerid_zk=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_fourthsaleamt_zk,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end        
    if  exists( select 1 from #emp_deperror where @e0003_fourthsalerid_zk=empid and emppos  in ('004','00401','00402'))              
    begin              
     set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_fourthsaleamt_zk,0)              
    end                
    if  exists( select 1 from #emp_deperror where @e0003_fourthsalerid_zk=empid and emppos  in ('008','00901','00902','00903','00904') )                
    begin              
     if(ISNULL(@e0003_fourthsaleamt_zk,0)>10000)              
        set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_mcardchangeinfo,'')+'第四销售烫发师分享业绩不能超过10000'              
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_fourthsaleamt_zk,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
    end              
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_fourthsaleamt_zk,0)                  
   end              
   --第五销售              
   if(isnull(@e0003_fifthsalerid_zk,'')<>'' and ISNULL(@e0003_fifthsaleamt_zk,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_fifthsalerid_zk=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_fifthsaleamt_zk,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end        
    if  exists( select 1 from #emp_deperror where @e0003_fifthsalerid_zk=empid and emppos  in ('004','00401','00402'))              
    begin              
     set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_fifthsaleamt_zk,0)              
    end                
    if  exists( select 1 from #emp_deperror where @e0003_fifthsalerid_zk=empid and emppos  in ('008','00901','00902','00903','00904') )                
    begin              
     if(ISNULL(@e0003_fifthsaleamt_zk,0)>10000)              
        set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_mcardchangeinfo,'')+'第五销售烫发师分享业绩不能超过10000'              
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_fifthsaleamt_zk,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
    end               
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_fifthsaleamt_zk,0)              
   end              
   --第六销售              
   if(isnull(@e0003_sixthsalerid_zk,'')<>'' and ISNULL(@e0003_sixthsaleamt_zk,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_sixthsalerid_zk=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_sixthsaleamt_zk,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end        
    if  exists( select 1 from #emp_deperror where @e0003_sixthsalerid_zk=empid and emppos  in ('004','00401','00402'))              
    begin              
     set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_sixthsaleamt_zk,0)              
    end                
    if  exists( select 1 from #emp_deperror where @e0003_sixthsalerid_zk=empid and emppos  in ('008','00901','00902','00903','00904') )                
    begin              
     if(ISNULL(@e0003_sixthsaleamt_zk,0)>10000)              
        set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_mcardchangeinfo,'')+'第六销售烫发师分享业绩不能超过10000'              
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_sixthsaleamt_zk,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
   end              
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_sixthsaleamt_zk,0)               
   end              
   --第七销售         
   if(isnull(@e0003_seventhsalerid_zk,'')<>'' and ISNULL(@e0003_seventhsaleamt_zk,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_seventhsalerid_zk=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_seventhsaleamt_zk,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end        
    if  exists( select 1 from #emp_deperror where @e0003_seventhsalerid_zk=empid and emppos  in ('004','00401','00402'))              
    begin              
     set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_seventhsaleamt_zk,0)              
    end                
    if  exists( select 1 from #emp_deperror where @e0003_seventhsalerid_zk=empid and emppos  in ('008','00901','00902','00903','00904') )                
    begin              
     if(ISNULL(@e0003_seventhsaleamt_zk,0)>10000)              
        set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_mcardchangeinfo,'')+'第七销售烫发师分享业绩不能超过10000'               
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_seventhsaleamt_zk,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
    end              
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_seventhsaleamt_zk,0)                
   end              
                               
   --第八销售              
   if(isnull(@e0003_eighthsalerid_zk,'')<>'' and ISNULL(@e0003_eighthsaleamt_zk,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_eighthsalerid_zk=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_eighthsaleamt_zk,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end        
    if  exists( select 1 from #emp_deperror where @e0003_eighthsalerid_zk=empid and emppos  in ('004','00401','00402'))              
    begin              
     set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_eighthsaleamt_zk,0)              
    end                
    if  exists( select 1 from #emp_deperror where @e0003_eighthsalerid_zk=empid and emppos  in ('008','00901','00902','00903','00904') )                
    begin              
     if(ISNULL(@e0003_eighthsaleamt_zk,0)>10000)              
        set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_mcardchangeinfo,'')+'第八销售烫发师分享业绩不能超过10000'               
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_eighthsaleamt_zk,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
    end        
                  
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_eighthsaleamt_zk,0)                 
   end              
   --第九销售              
   if(isnull(@e0003_ninthsalerid_zk,'')<>'' and ISNULL(@e0003_ninthsaleamt_zk,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_ninthsalerid_zk=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_ninthsaleamt_zk,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end        
    if  exists( select 1 from #emp_deperror where @e0003_ninthsalerid_zk=empid and emppos  in ('004','00401','00402'))              
    begin              
     set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_ninthsaleamt_zk,0)              
    end                
    if  exists( select 1 from #emp_deperror where @e0003_ninthsalerid_zk=empid and emppos  in ('008','00901','00902','00903','00904') )                
    begin              
     if(ISNULL(@e0003_ninthsaleamt_zk,0)>10000)              
        set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_mcardchangeinfo,'')+'第九销售烫发师分享业绩不能超过10000'               
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_ninthsaleamt_zk,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
    end              
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_ninthsaleamt_zk,0)                  
   end              
                 
   --第十销售           if(isnull(@e0003_tenthsalerid_zk,'')<>'' and ISNULL(@e0003_tenthsaleamt_zk,0)>0)               
   begin              
    if  exists( select 1 from #emp_deperror where @e0003_tenthsalerid_zk=empid and emppos  in ('00102','003','006','007','00701','00702'))              
    begin              
     set @mfstaffshareamt=@mfstaffshareamt+ISNULL(@e0003_tenthsaleamt_zk,0)              
     set @mfstaffsharecount=@mfstaffsharecount+1              
    end        
    if  exists( select 1 from #emp_deperror where @e0003_tenthsalerid_zk=empid and emppos  in ('004','00401','00402'))              
    begin              
     set @mrstaffshareamt=@mrstaffshareamt+ISNULL(@e0003_tenthsaleamt_zk,0)              
    end                
    if  exists( select 1 from #emp_deperror where @e0003_tenthsalerid_zk=empid and emppos  in ('008','00901','00902','00903','00904') )                
    begin              
     if(ISNULL(@e0003_tenthsaleamt_zk,0)>10000)              
        set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_mcardchangeinfo,'')+'第十销售烫发师分享业绩不能超过10000'                
     set @trstaffshareamt=@trstaffshareamt+ISNULL(@e0003_tenthsaleamt_zk,0)              
     set @trstaffsharecount=@trstaffsharecount+1              
    end              
    set @totalshareamt=ISNULL(@totalshareamt,0)+ISNULL(@e0003_tenthsaleamt_zk,0)                
   end                  
 if(ISNULL(@trstaffshareamt,0)>ISNULL(@mfstaffshareamt,0))              
        set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_mcardchangeinfo,'')+'美发人员与对应的烫发师分享业绩不匹配'                             
    if(ISNULL(@trstaffsharecount,0)>ISNULL(@mfstaffsharecount,0))              
        set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_mcardchangeinfo,'')+'分享美发人员少于烫发师人数'       
            
    if(ISNULL(@billinsertype,0)=0 or ISNULL(@sp102,'0')='0') --不区分卡金    
    begin                
    if(convert(numeric(20,1),ISNULL(@totalshareamt,0)-ISNULL(@trstaffshareamt,0))<>convert(numeric(20,1),ISNULL(@e0003_changefillamt_zk,0)))              
        set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_mcardchangeinfo,'')+'没有完全分享'          
            
 end      
 else  if(ISNULL(@billinsertype,0)=1 and ISNULL(@sp102,'0')='1') --美容卡金     
 begin    
  if(convert(numeric(20,1),ISNULL(@mrstaffshareamt,0))<>convert(numeric(20,1),ISNULL(@e0003_changefillamt_zk,0)))              
   set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_mcardchangeinfo,'')+'没有完全分享(美容卡金)'     
 end       
    else  if(ISNULL(@billinsertype,0)=2 and ISNULL(@sp102,'0')='1') --美发卡金     
 begin    
  if(convert(numeric(20,1),ISNULL(@mfstaffshareamt,0))<>convert(numeric(20,1),ISNULL(@e0003_changefillamt_zk,0)))              
   set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_mcardchangeinfo,'')+'没有完全分享(美发卡金)'     
 end            
        
            
    if(@compid in ('006','047','033','014','041','046') and @todate between '20140501' and '20140515')        
    begin        
 if(convert(numeric(20,2),ISNULL(@mfstaffshareamt,0)/ISNULL(@totalshareamt,0))>0.5)        
  set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_mcardchangeinfo,'')+'美发业绩分享超出了总业绩的50%'         
 if(convert(numeric(20,2),ISNULL(@mrstaffshareamt,0)/ISNULL(@totalshareamt,0))>0.5)        
  set @e0003_bill_mcardchangeinfo=isnull(@e0003_bill_mcardchangeinfo,'')+'美容业绩分享超出了总业绩的50%'         
 end                
   if(@e0003_bill_mcardchangeinfo<>'')                            
   begin                              
    insert into #result(compid,billtype,errid,remark)                                      
    values(@compid,'4','E0003',@e0003_bill_mcardchangeinfo+'单号'+@e0003_changebillid)                              
   end                              
                                    
   fetch cur_#mcardchangeinfoerr3 into  @e0003_changebillid,              
    @e0003_firstsalerid_zk,@e0003_secondsalerid_zk,@e0003_thirdsalerid_zk,@e0003_fourthsalerid_zk,              
    @e0003_fifthsalerid_zk,@e0003_sixthsalerid_zk,@e0003_seventhsalerid_zk,@e0003_eighthsalerid_zk,              
    @e0003_ninthsalerid_zk,@e0003_tenthsalerid_zk,                            
    @e0003_firstsaleamt_zk,@e0003_secondsaleamt_zk,@e0003_thirdsaleamt_zk,@e0003_fourthsaleamt_zk,              
    @e0003_fifthsaleamt_zk,@e0003_sixthsaleamt_zk,@e0003_seventhsaleamt_zk,@e0003_eighthsaleamt_zk,               
    @e0003_ninthsaleamt_zk,@e0003_tenthsaleamt_zk,@e0003_changefillamt_zk ,@billinsertype                    
  end                                      
  close cur_#mcardchangeinfoerr3                                      
  deallocate cur_#mcardchangeinfoerr3                
 end                            
               
 drop table #mcardchangeinfo                          
                             
     CREATE tAbLE #mproexchangeinfo               -- 会员卡疗程兑换              
 (              
  changecompid   varchar(10)   Not NULL,   --公司编号              
  changebillid   varchar(20)   Not NULL,   --销售单号              
  changedate    varchar(8)    NULL,   --销售日期              
  changecardno   varchar(20)    NULL,   --销售卡号              
  changecardtype   varchar(20)    NULL,   --销售卡类型              
  firstsalerid   varchar(20)    NULL,   --第一销售工号              
  firstsalerinid   varchar(20)    NULL,   --第一销售内部编号              
  firstsaleamt   float     NULL,   --第一销售分享金额              
  secondsalerid   varchar(20)    NULL,   --第二销售工号              
  secondsalerinid   varchar(20)    NULL,   --第二销售内部编号              
  secondsaleamt   float     NULL,   --第二销售分享金额              
  thirdsalerid   varchar(20)    NULL,   --第三销售工号----- 烫染师              
  thirdsalerinid   varchar(20)    NULL,   --第三销售内部编号              
  thirdsaleamt   float     NULL,   --第三销售分享金额              
  fourthsalerid   varchar(20)    NULL,   --第四销售工号----- 烫染师              
  fourthsalerinid   varchar(20)    NULL,   --第四销售内部编号              
  fourthsaleamt   float     NULL,   --第四销售分享金额              
  financedate    varchar(8)    NULL,   --帐务日期               
  changeproamt   float     NULL, --储值金额              
  paymode     varchar(10)    NULL, --支付方式              
  payamt     float     NULL, --支付金额              
 )                
 insert #mproexchangeinfo(changecompid,changebillid,changedate,changecardno,changecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,              
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,financedate,changeproamt,paymode,payamt)                 
    select a.changecompid,a.changebillid,changedate,changecardno,changecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,              
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,financedate,changebyaccountamt,changepaymode,changebycashamt               
        from mproexchangeinfo a,dproexchangeinfo b              
    where a.changecompid=b.changecompid and a.changebillid=b.changebillid              
    and  financedate between @fromdate and @todate and ISNULL(changeproamt,0)<>0 and a.changecompid=@compid and ISNULL(salebakflag,0)=0              
                  
    if exists(select 1 from #mproexchangeinfo)                                      
 begin                                      
  declare @e0010_bill_mproexchangeinfo varchar(1024)                                      
  set @e0010_bill_mproexchangeinfo = ''                         
  declare @e0010_changecompid varchar(10),  @e0010_changecardno varchar(20) ,  @e0010_changebillid varchar(20)                        
  declare @e0010_changeproamt float,   @e0010_payamt float                                   
  declare @e0010_firstsalerid varchar(20), @e0010_secondsalerid varchar(20), @e0010_gcq14c varchar(20), @e0010_gcq15c varchar(20)                                
  declare @e0010_firstsaleamt float, @e0010_secondsaleamt float, @e0010_gcq16f float, @e0010_gcq17f float                        
                              
  declare cur_#mproexchangeinfo1err10 cursor for                                      
  select changecompid,changecardno,changebillid,changeproamt,payamt,firstsalerid,firstsaleamt,secondsalerid,secondsaleamt from #mproexchangeinfo                         
  open cur_#mproexchangeinfo1err10                                      
  fetch cur_#mproexchangeinfo1err10 into  @e0010_changecompid,@e0010_changecardno,@e0010_changebillid,@e0010_changeproamt,@e0010_payamt,                        
  @e0010_firstsalerid,@e0010_firstsaleamt,@e0010_secondsalerid,@e0010_secondsaleamt                      
                                            
  while(@@fetch_status=0)                                      
  begin                 
   set @e0010_bill_mproexchangeinfo=''               
   if(ISNULL(@e0010_changeproamt,0)+ISNULL(@e0010_payamt,0)>0 and ISNULL(@e0010_firstsaleamt,0)+ISNULL(@e0010_secondsaleamt,0)=0)                        
   begin                        
    insert into #result(compid,billtype,errid,remark)                                      
    values(@compid,'10','E0010','本次疗程兑换未做分享'+'卡号'+@e0010_changecardno+'单号'+@e0010_changebillid)                           
   end                         
                                    
   fetch cur_#mproexchangeinfo1err10 into  @e0010_changecompid,@e0010_changecardno,@e0010_changebillid,@e0010_changeproamt,@e0010_payamt,                        
   @e0010_firstsalerid,@e0010_firstsaleamt,@e0010_secondsalerid,@e0010_secondsaleamt                         
  end                         
  close cur_#mproexchangeinfo1err10                                      
  deallocate cur_#mproexchangeinfo1err10                                      
                                      
 end               
               
    drop table #mproexchangeinfo                 
                   
                   
   ----------------------add by liujie (ggm03 业绩分享)--------------------------------------------                
 create table #mconsumeinfo                                    
 (                                    
  cscompid  varchar(10)     NOT NULL,   --公司编号                                    
  csbillid  varchar(20)     NOT NULL,   --销售单号                                    
  cscardno  varchar(20)     NULL    ,   --                                    
 )                                
 insert into #mconsumeinfo(cscompid,csbillid,cscardno)                                    
 select a.cscompid,a.csbillid,cscardno                                  
 from mconsumeinfo   a with (nolock),dconsumeinfo  b                              
 where a.cscompid=@compid                                     
      and a.cscompid=b.cscompid and a.csbillid=b.csbillid                                    
      and a.financedate between @fromdate and @todate                
      and ISNULL(csinfotype,0)=2 and ISNULL(backcsflag,0)=0              
  and convert(numeric(20,2),isnull(csitemamt,0))<>convert(numeric(20,2),isnull(csfirstshare,0)+isnull(cssecondshare,0)+isnull(csthirdshare,0))                                    
                      
    insert into #mconsumeinfo(cscompid,csbillid,cscardno)                                    
 select a.cscompid,a.csbillid,cscardno                                  
 from mconsumeinfo   a with (nolock),dconsumeinfo  b                              
 where a.cscompid=@compid                                     
      and a.cscompid=b.cscompid and a.csbillid=b.csbillid                                    
      and a.financedate between @fromdate and @todate                
      and ISNULL(csinfotype,0)=1 and ISNULL(backcsflag,0)=0              
   and (ISNULL(csfirstinid,'')='' or ISNULL(csfirstsaler,'')='')              
                      
 --e0003            
 if exists(select 1 from #mconsumeinfo)                                    
 begin                                    
  declare @e0003_bill_9 varchar(1024)                                    
  set @e0003_bill_9 = ''                                    
  declare @e0003_csbillid varchar(20),@e0003_cscardno varchar(20)                                    
  declare cur_mconsumeinfo cursor for                                    
  select csbillid,cscardno from #mconsumeinfo                                 
  open cur_mconsumeinfo                                    
  fetch cur_mconsumeinfo into  @e0003_csbillid,@e0003_cscardno                                    
  while(@@fetch_status=0)                                    
  begin                                    
   if(@e0003_bill_9='')                                    
    set @e0003_bill_9 = @e0003_bill_9+@e0003_csbillid+'('+@e0003_cscardno+')'                                    
   else                                    
    set @e0003_bill_9 = @e0003_bill_9+','+@e0003_csbillid+'('+@e0003_cscardno+')'                                    
   fetch cur_mconsumeinfo into @e0003_csbillid,@e0003_cscardno                                    
  end                                    
  close cur_mconsumeinfo                                    
  deallocate cur_mconsumeinfo                                    
                                    
  insert into #result(compid,billtype,errid,remark)                                    
  values(@compid,'9','E0003',@e0003_bill_9)                                    
 end                                    
 drop table #mconsumeinfo                             
                 
   --分类业绩结算                               
  create table #cls_yeji_result_search(                                        
    compid varchar(10) not null,              
    ddate varchar(10) null,                                     
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
  exec upg_compute_comp_classed_trade @compid,@fromdate,@todate,2          
            
  if exists(select 1 from #cls_yeji_result_search           
  where convert(numeric(20,0),ISNULL(beaut_yeji,0)+ISNULL(hair_yeji,0)+ISNULL(finger_yeji,0))<>convert(numeric(20,0),ISNULL(total_yeji,0)) )          
  begin          
 insert into #result(compid,billtype,errid,remark)                                      
    values(@compid,'11','E0011','当天分类业绩不平,请至分类统计查询')                  
  end          
  drop table #cls_yeji_result_search                                                          
 -------------------------------------------------------------------------------------                                    
 update a set errdescript = description                                     
 from #result a,#error b                                    
 where a.errid = b.errid                                    
                                    
 select compid,billtype,errid,errdescript,remark                                     
 from #result                                  
                                    
                                    
 drop table #result                             
         
 drop table #emp_deperror                               
 drop table #error                                    
end 