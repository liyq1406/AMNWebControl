alter procedure upg_prepare_yeji_detail(            
 @compid    varchar(10), -- 公司别            
 @fromdate   varchar(10), -- 开始日期            
 @todate    varchar(10), -- 截至日期            
 @fromempinno  varchar(20), -- 查询开始人员内部编号           
 @toempinno   varchar(20)  -- 查询截至人员内部编号   
)       
as            
begin 
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
 )       
    insert #msalecardinfo(salecompid,salebillid,saledate,salecardno,salecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,  
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,  
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,  
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,salekeepamt,paymode,payamt)  
 select salecompid,salebillid,saledate,salecardno,salecardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,  
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,  
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,  
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,salekeepamt,paymode,payamt  
    from msalecardinfo, dpayinfo  
    where salebillid=paybillid and salecompid=paycompid and paybilltype='SK'  
      and salecompid=@compid and financedate between @fromdate and @todate and ISNULL(salekeepamt,0)<>0  and ISNULL(salebakflag,0)=0  
          
          
                    
           
 --卖卡第一销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,firstsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,firstsaleamt*payamt/isnull(salekeepamt,1)            
 from #msalecardinfo a ,cardtypenameinfo c       
 where a.firstsalerinid >= @fromempinno            
 and a.firstsalerinid <= @toempinno            
    and isnull(firstsaleamt,0)<>0     
    and salecardtype=cardtypeno  
      
    --卖卡第二销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,secondsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,secondsaleamt*payamt/isnull(salekeepamt,1)            
 from #msalecardinfo a,cardtypenameinfo c           
 where a.secondsalerinid >= @fromempinno            
 and a.secondsalerinid <= @toempinno            
    and isnull(secondsaleamt,0)<>0              
    and salecardtype=cardtypeno       
    --卖卡第三销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,thirdsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,thirdsaleamt*payamt/isnull(salekeepamt,1)            
 from #msalecardinfo a,cardtypenameinfo c            
 where a.thirdsalerinid >= @fromempinno            
 and a.thirdsalerinid <= @toempinno            
    and isnull(thirdsaleamt,0)<>0  
    and salecardtype=cardtypeno   
    --卖卡第四销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,fourthsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,fourthsaleamt*payamt/isnull(salekeepamt,1)            
 from #msalecardinfo a ,cardtypenameinfo c          
 where a.fourthsalerinid >= @fromempinno            
 and a.fourthsalerinid <= @toempinno            
    and isnull(fourthsaleamt,0)<>0  
    and salecardtype=cardtypeno   
    --卖卡第五销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,fifthsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,fifthsaleamt*payamt/isnull(salekeepamt,1)            
 from #msalecardinfo a,cardtypenameinfo c           
 where a.fifthsalerinid >= @fromempinno            
 and a.fifthsalerinid <= @toempinno            
    and isnull(fifthsaleamt,0)<>0  
    and salecardtype=cardtypeno   
    --卖卡第六销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,sixthsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,sixthsaleamt*payamt/isnull(salekeepamt,1)            
 from #msalecardinfo a,cardtypenameinfo c           
 where a.sixthsalerinid >= @fromempinno            
 and a.sixthsalerinid <= @toempinno            
    and isnull(sixthsaleamt,0)<>0  
    and salecardtype=cardtypeno   
    --卖卡第七销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,seventhsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,seventhsaleamt*payamt/isnull(salekeepamt,1)            
 from #msalecardinfo a ,cardtypenameinfo c          
 where a.seventhsalerinid >= @fromempinno            
 and a.seventhsalerinid <= @toempinno            
    and isnull(seventhsaleamt,0)<>0  
    and salecardtype=cardtypeno    
    --卖卡第八销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,eighthsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,eighthsaleamt*payamt/isnull(salekeepamt,1)            
 from #msalecardinfo a ,cardtypenameinfo c          
 where a.eighthsalerinid >= @fromempinno            
 and a.eighthsalerinid <= @toempinno            
    and isnull(eighthsaleamt,0)<>0  
    and salecardtype=cardtypeno   
    --卖卡第九销售人员  ,cardtypenameinfo c             
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,ninthsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,ninthsaleamt*payamt/isnull(salekeepamt,1)            
 from #msalecardinfo a ,cardtypenameinfo c          
 where a.ninthsalerinid >= @fromempinno            
 and a.ninthsalerinid <= @toempinno            
    and isnull(ninthsaleamt,0)<>0  
    and salecardtype=cardtypeno   
    --卖卡第十销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,tenthsalerinid,1,financedate,salecardno,cardtypename,payamt,1,salebillid,paymode,tenthsaleamt*payamt/isnull(salekeepamt,1)            
 from #msalecardinfo a  ,cardtypenameinfo c         
 where a.tenthsalerinid >= @fromempinno            
 and a.tenthsalerinid <= @toempinno            
    and isnull(tenthsaleamt,0)<>0  
    and salecardtype=cardtypeno  
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
 )       
   
 insert #mcardrechargeinfo(rechargecompid,rechargebillid,rechargedate,rechargecardno,rechargecardtype,rechargetype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,  
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,  
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,  
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,rechargekeepamt,paymode,payamt)  
 select rechargecompid,rechargebillid,rechargedate,rechargecardno,rechargecardtype,rechargetype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,  
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,  
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,  
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,rechargekeepamt,paymode,payamt  
    from mcardrechargeinfo, dpayinfo  
    where rechargebillid=paybillid and rechargecompid=paycompid and paybilltype='CZ'  
      and rechargecompid=@compid and financedate between @fromdate and @todate and ISNULL(rechargekeepamt,0)<>0  and ISNULL(salebakflag,0)=0 and isnull(backbillid,'')=''  
   
   
 update #mcardrechargeinfo set rechargecardtype=cardtype  
 from #mcardrechargeinfo,cardinfo with(nolock)  
 where rechargecardno=cardno and ISNULL(rechargetype,0)=1  
   
  --卖卡第一销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,firstsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,firstsaleamt*payamt/isnull(rechargekeepamt,1)            
 from #mcardrechargeinfo a  ,cardtypenameinfo c           
 where a.firstsalerinid >= @fromempinno            
 and a.firstsalerinid <= @toempinno            
    and isnull(firstsaleamt,0)<>0     
    and rechargecardtype=cardtypeno  
    --卖卡第二销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,secondsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,secondsaleamt*payamt/isnull(rechargekeepamt,1)            
 from #mcardrechargeinfo a  ,cardtypenameinfo c           
 where a.secondsalerinid >= @fromempinno            
 and a.secondsalerinid <= @toempinno            
    and isnull(secondsaleamt,0)<>0              
     and rechargecardtype=cardtypeno      
    --卖卡第三销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,thirdsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,thirdsaleamt*payamt/isnull(rechargekeepamt,1)            
 from #mcardrechargeinfo a   ,cardtypenameinfo c          
 where a.thirdsalerinid >= @fromempinno            
 and a.thirdsalerinid <= @toempinno            
    and isnull(thirdsaleamt,0)<>0  
     and rechargecardtype=cardtypeno  
    --卖卡第四销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,fourthsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,fourthsaleamt*payamt/isnull(rechargekeepamt,1)            
 from #mcardrechargeinfo a    ,cardtypenameinfo c         
 where a.fourthsalerinid >= @fromempinno            
 and a.fourthsalerinid <= @toempinno            
    and isnull(fourthsaleamt,0)<>0  
     and rechargecardtype=cardtypeno  
    --卖卡第五销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,fifthsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,fifthsaleamt*payamt/isnull(rechargekeepamt,1)            
 from #mcardrechargeinfo a  ,cardtypenameinfo c           
 where a.fifthsalerinid >= @fromempinno            
 and a.fifthsalerinid <= @toempinno            
    and isnull(fifthsaleamt,0)<>0  
     and rechargecardtype=cardtypeno  
    --卖卡第六销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,sixthsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,sixthsaleamt*payamt/isnull(rechargekeepamt,1)            
 from #mcardrechargeinfo a ,cardtypenameinfo c         
 where a.sixthsalerinid >= @fromempinno            
 and a.sixthsalerinid <= @toempinno            
    and isnull(sixthsaleamt,0)<>0  
    and rechargecardtype=cardtypeno   
    --卖卡第七销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,seventhsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,seventhsaleamt*payamt/isnull(rechargekeepamt,1)            
 from #mcardrechargeinfo a ,cardtypenameinfo c            
 where a.seventhsalerinid >= @fromempinno            
 and a.seventhsalerinid <= @toempinno            
    and isnull(seventhsaleamt,0)<>0  
     and rechargecardtype=cardtypeno  
    --卖卡第八销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,eighthsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,eighthsaleamt*payamt/isnull(rechargekeepamt,1)            
 from #mcardrechargeinfo a  ,cardtypenameinfo c           
 where a.eighthsalerinid >= @fromempinno            
 and a.eighthsalerinid <= @toempinno            
    and isnull(eighthsaleamt,0)<>0  
     and rechargecardtype=cardtypeno  
    --卖卡第九销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,ninthsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,ninthsaleamt*payamt/isnull(rechargekeepamt,1)            
 from #mcardrechargeinfo a  ,cardtypenameinfo c           
 where a.ninthsalerinid >= @fromempinno            
 and a.ninthsalerinid <= @toempinno            
    and isnull(ninthsaleamt,0)<>0  
     and rechargecardtype=cardtypeno  
    --卖卡第十销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,tenthsalerinid,2,financedate,rechargecardno,cardtypename,payamt,1,rechargebillid,paymode,tenthsaleamt*payamt/isnull(rechargekeepamt,1)            
 from #mcardrechargeinfo a  ,cardtypenameinfo c           
 where a.tenthsalerinid >= @fromempinno            
 and a.tenthsalerinid <= @toempinno            
    and isnull(tenthsaleamt,0)<>0  
    and rechargecardtype=cardtypeno  
       
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
  changefillamt  float     NULL,   --充值金额  
  paymode    varchar(10)    NULL, --支付方式  
  payamt    float     NULL, --支付金额  
 )     
   
 insert #mcardchangeinfo(changecompid,changebillid,changetype,changedate,changeaftercardno,changeaftercardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,  
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,  
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,  
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,changefillamt,paymode,payamt)  
 select changecompid,changebillid,changetype,changedate,changeaftercardno,changeaftercardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,  
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,  
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,  
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,financedate,changefillamt,paymode,payamt  
    from mcardchangeinfo, dpayinfo  
    where changebillid=paybillid and changecompid=paycompid and paybilltype in ('ZK','TK')  
      and changecompid=@compid and financedate between @fromdate and @todate and ISNULL(changefillamt,0)<>0   and ISNULL(salebakflag,0)=0  
        
        
      --卖卡第一销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,firstsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*firstsaleamt*payamt/isnull(changefillamt,1) else   firstsaleamt*payamt/isnull(changefillamt,1) end           
 from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno   
 where a.firstsalerinid >= @fromempinno            
 and a.firstsalerinid <= @toempinno            
    and isnull(firstsaleamt,0)<>0     
  
    --卖卡第二销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,secondsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*secondsaleamt*payamt/isnull(changefillamt,1) else   secondsaleamt*payamt/isnull(changefillamt,1) end        
 from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno   
 where a.secondsalerinid >= @fromempinno            
 and a.secondsalerinid <= @toempinno            
    and isnull(secondsaleamt,0)<>0              
  
    --卖卡第三销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,thirdsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*thirdsaleamt*payamt/isnull(changefillamt,1) else   thirdsaleamt*payamt/isnull(changefillamt,1) end     
 from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno   
 where a.thirdsalerinid >= @fromempinno            
 and a.thirdsalerinid <= @toempinno            
    and isnull(thirdsaleamt,0)<>0  
  
    --卖卡第四销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,fourthsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*fourthsaleamt*payamt/isnull(changefillamt,1) else   fourthsaleamt*payamt/isnull(changefillamt,1) end         
 from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno   
 where a.fourthsalerinid >= @fromempinno            
 and a.fourthsalerinid <= @toempinno            
    and isnull(fourthsaleamt,0)<>0  
  
    --卖卡第五销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,fifthsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*fifthsaleamt*payamt/isnull(changefillamt,1) else   fifthsaleamt*payamt/isnull(changefillamt,1) end     
 from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno   
 where a.fifthsalerinid >= @fromempinno            
 and a.fifthsalerinid <= @toempinno            
    and isnull(fifthsaleamt,0)<>0  
  
    --卖卡第六销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,sixthsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*sixthsaleamt*payamt/isnull(changefillamt,1) else   sixthsaleamt*payamt/isnull(changefillamt,1) end          
 from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno   
 where a.sixthsalerinid >= @fromempinno            
 and a.sixthsalerinid <= @toempinno            
    and isnull(sixthsaleamt,0)<>0  
     
    --卖卡第七销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,seventhsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*seventhsaleamt*payamt/isnull(changefillamt,1) else   seventhsaleamt*payamt/isnull(changefillamt,1) end            
 from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno   
 where a.seventhsalerinid >= @fromempinno            
 and a.seventhsalerinid <= @toempinno            
    and isnull(seventhsaleamt,0)<>0  
     
    --卖卡第八销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,eighthsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*eighthsaleamt*payamt/isnull(changefillamt,1)
 else   eighthsaleamt*payamt/isnull(changefillamt,1) end     
 from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno   
 where a.eighthsalerinid >= @fromempinno            
 and a.eighthsalerinid <= @toempinno            
    and isnull(eighthsaleamt,0)<>0  
     
    --卖卡第九销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,ninthsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*ninthsaleamt*payamt/isnull(changefillamt,1) else  ninthsaleamt*payamt/isnull(changefillamt,1) end     
 from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno   
 where a.ninthsalerinid >= @fromempinno            
 and a.ninthsalerinid <= @toempinno            
    and isnull(ninthsaleamt,0)<>0  
     
    --卖卡第十销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,tenthsalerinid,3,financedate,changeaftercardno,cardtypename,case when isnull(changetype,0)=8  then (-1)*payamt else payamt end,1,changebillid,paymode,case when isnull(changetype,0)=8  then (-1)*tenthsaleamt*payamt/isnull(changefillamt,1) else   tenthsaleamt*payamt/isnull(changefillamt,1) end     
 from     #mcardchangeinfo a  left join cardtypenameinfo c  on changeaftercardtype=cardtypeno   
 where a.tenthsalerinid >= @fromempinno            
 and a.tenthsalerinid <= @toempinno            
    and isnull(tenthsaleamt,0)<>0  
     
      
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
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,financedate,changeproamt,changepaymode,changebycashamt   
        from mproexchangeinfo a,dproexchangeinfo b  
    where a.changecompid=b.changecompid and a.changebillid=b.changebillid  
    and  financedate between @fromdate and @todate and ISNULL(changeproamt,0)<>0 and a.changecompid=@compid   
    and ISNULL(backcsflag,0)=0  and ISNULL(salebakflag,0)=0  
       --卖卡第一销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,firstsalerinid,4,financedate,changecardno,changecardtype,payamt,1,changebillid,paymode,firstsaleamt          
 from #mproexchangeinfo a        
 where a.firstsalerinid >= @fromempinno            
 and a.firstsalerinid <= @toempinno            
    and isnull(firstsaleamt,0)<>0     
      
    --卖卡第二销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,secondsalerinid,4,financedate,changecardno,changecardtype,payamt,1,changebillid,paymode,secondsaleamt  
 from #mproexchangeinfo a        
 where a.secondsalerinid >= @fromempinno            
 and a.secondsalerinid <= @toempinno            
    and isnull(secondsaleamt,0)<>0              
          
    --卖卡第三销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,thirdsalerinid,4,financedate,changecardno,changecardtype,payamt,1,changebillid,paymode,thirdsaleamt  
 from #mproexchangeinfo a        
 where a.thirdsalerinid >= @fromempinno            
 and a.thirdsalerinid <= @toempinno            
    and isnull(thirdsaleamt,0)<>0  
      
    --卖卡第四销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,fourthsalerinid,4,financedate,changecardno,changecardtype,payamt,1,changebillid,paymode,fourthsaleamt  
 from #mproexchangeinfo a        
 where a.fourthsalerinid >= @fromempinno            
 and a.fourthsalerinid <= @toempinno            
    and isnull(fourthsaleamt,0)<>0  
      
    CREATE tAbLE #msalebarcodecardinfo             -- 系统条码卡销售主档  
 (  
  salecompid   varchar(10)  not null ,--销售门店  
  salebillid   varchar(20)  not null ,--销售单号  
  saledate   varchar(8)   null ,--销售日期  
  barcodecardno  varchar(20)   null ,--销售条码卡卡号  
  firstpaymode  varchar(20)   null ,--第一支付方式  
  firstpayamt   float    null ,--第一支付金额  
  secondpaymode  varchar(20)   null ,--第二支付方式  
  secondpayamt  varchar(20)   null ,--第二支付金额  
  saleamt    float    null ,--销售总额  
  firstsaleempid  varchar(20)   null ,--第一销售工号  
  firstsaleempinid varchar(20)   null ,--第一销售内部工号  
  firstsaleamt  float    null ,--第一销售分享金额  
  secondsaleempid  varchar(20)   null ,--第二销售工号  
  secondsaleempinid varchar(20)   null ,--第二销售内部工号  
  secondsaleamt  float    null ,--第二销售分享金额  
  thirdsaleempid  varchar(20)   null ,--第三销售工号  
  thirdsaleempinid varchar(20)   null ,--第三销售内部工号  
  thirdsaleamt  float    null ,--第三销售分享金额  
 )    
   
 insert #msalebarcodecardinfo(salecompid,salebillid,saledate,barcodecardno,firstpaymode,firstpayamt,secondpaymode,secondpayamt,saleamt,  
 firstsaleempid,firstsaleempinid,firstsaleamt,secondsaleempid,secondsaleempinid,secondsaleamt,thirdsaleempid,thirdsaleempinid,thirdsaleamt)  
    select salecompid,salebillid,saledate,barcodecardno,firstpaymode,firstpayamt,secondpaymode,secondpayamt,saleamt,  
 firstsaleempid,firstsaleempinid,firstsaleamt,secondsaleempid,secondsaleempinid,secondsaleamt,thirdsaleempid,thirdsaleempinid,thirdsaleamt  
 from msalebarcodecardinfo  
    where salecompid=@compid and saledate between @fromdate and @todate and ISNULL(saleamt,0)<>0    
      
       --卖卡第一销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,firstsaleempinid,5,saledate,barcodecardno,'条码卡',saleamt,1,salebillid,firstpaymode,firstsaleamt          
 from #msalebarcodecardinfo a        
 where a.firstsaleempinid >= @fromempinno            
 and a.firstsaleempinid <= @toempinno            
    and isnull(firstsaleamt,0)<>0     
      
    --卖卡第二销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,secondsaleempinid,5,saledate,barcodecardno,'条码卡',saleamt,1,salebillid,firstpaymode,secondsaleamt  
 from #msalebarcodecardinfo a        
 where a.secondsaleempinid >= @fromempinno            
 and a.secondsaleempinid <= @toempinno            
    and isnull(secondsaleamt,0)<>0              
          
    --卖卡第三销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,thirdsaleempinid,5,saledate,barcodecardno,'条码卡',saleamt,1,salebillid,firstpaymode,thirdsaleamt  
 from #msalebarcodecardinfo a        
 where a.thirdsaleempinid >= @fromempinno            
 and a.thirdsaleempinid <= @toempinno            
    and isnull(thirdsaleamt,0)<>0  
      
    --合作项目  
 CREATE tAbLE    #mcooperatesaleinfo               
 (  
  salecompid    char(10)    Not NULL,   --公司编号  
  salebillid    varchar(20)    Not NULL,   --异动单号  
  saledate    varchar(8)    NULL    ,   --异动日期  
  salecooperid   varchar(30)    NULL    ,   --合作单位  
  slaepaymode    varchar(5)    NULL    ,   --支付方向 1 店内支付，2 合作单位支付  
  salecostcardno   varchar(20)    NULL    ,   --会员卡号  
  salecostcardtype  varchar(20)    NULL    ,   --会员卡类型  
  salecostproamt   float     NULL    ,   --项目金额  
  firstsalerid   varchar(20)    NULL,   --第一销售工号  
  firstsalerinid   varchar(20)    NULL,   --第一销售内部编号  
  firstsaleamt   float     NULL,   --第一销售分享金额  
  secondsalerid   varchar(20)    NULL,   --第二销售工号  
  secondsalerinid   varchar(20)    NULL,   --第二销售内部编号  
  secondsaleamt   float     NULL,   --第二销售分享金额  
  thirdsalerid   varchar(20)    NULL,   --第三销售工号  
  thirdsalerinid   varchar(20)    NULL,   --第三销售内部编号  
  thirdsaleamt   float     NULL,   --第三销售分享金额  
  fourthsalerid   varchar(20)    NULL,   --第四销售工号  
  fourthsalerinid   varchar(20)    NULL,   --第四销售内部编号  
  fourthsaleamt   float     NULL,   --第四销售分享金额  
  fifthsalerid   varchar(20)    NULL,   --第五销售工号 -----烫染师  
  fifthsalerinid   varchar(20)    NULL,   --第五销售内部编号  
  fifthsaleamt   float     NULL,   --第五销售分享金额  
  sixthsalerid   varchar(20)    NULL,   --第六销售工号----- 烫染师  
  sixthsalerinid   varchar(20)    NULL,   --第六销售内部编号  
  sixthsaleamt   float     NULL,   --第六销售分享金额  
  seventhsalerid   varchar(20)    NULL,   --第七销售工号 -----烫染师  
  seventhsalerinid  varchar(20)    NULL,   --第七销售内部编号  
  seventhsaleamt   float     NULL,   --第七销售分享金额  
  eighthsalerid   varchar(20)    NULL,   --第八销售工号----- 烫染师  
  eighthsalerinid   varchar(20)    NULL,   --第八销售内部编号  
  eighthsaleamt   float     NULL,   --第八销售分享金额  
  financedate    varchar(8)    NULL,   --帐务日期   
  paymode     varchar(10)    NULL, --支付方式  
  payamt     float     NULL, --支付金额  
 )  
   
 insert #mcooperatesaleinfo(salecompid,salebillid,saledate,salecooperid,slaepaymode,salecostcardno,salecostcardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,  
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,  
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,  
                     financedate,salecostproamt,paymode,payamt)  
 select salecompid,salebillid,saledate,salecooperid,slaepaymode,salecostcardno,salecostcardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,  
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,  
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,  
                     financedate,salecostproamt,paymode,payamt  
    from mcooperatesaleinfo, dpayinfo  
    where salebillid=paybillid and salecompid=paycompid and paybilltype='HZ'  
      and salecompid=@compid and financedate between @fromdate and @todate and ISNULL(salecostproamt,0)<>0  
     -- and ISNULL(slaepaymode,0)=1   
      and ISNULL(salebillflag,0)=2  
        
       --卖卡第一销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,firstsalerinid,case when  salecooperid='001' and ISNULL(slaepaymode,'1')='2' then 27      
          when  salecooperid='002' and ISNULL(slaepaymode,'1')='1' then 28      
          when  salecooperid='002' and ISNULL(slaepaymode,'1')='2' then 29      
          when  salecooperid='003' and ISNULL(slaepaymode,'1')='1' then 30      
          when  salecooperid='003' and ISNULL(slaepaymode,'1')='2' then 31 end ,financedate,salecostcardno,salecostcardtype,payamt,1,salebillid,paymode,firstsaleamt*payamt/isnull(salecostproamt,1)            
 from #mcooperatesaleinfo a        
 where a.firstsalerinid >= @fromempinno            
 and a.firstsalerinid <= @toempinno       
    and isnull(firstsaleamt,0)<>0     
      
    --卖卡第二销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,secondsalerinid,case when  salecooperid='001' and ISNULL(slaepaymode,'1')='2' then 27      
          when  salecooperid='002' and ISNULL(slaepaymode,'1')='1' then 28      
          when  salecooperid='002' and ISNULL(slaepaymode,'1')='2' then 29      
          when  salecooperid='003' and ISNULL(slaepaymode,'1')='1' then 30      
          when  salecooperid='003' and ISNULL(slaepaymode,'1')='2' then 31 end ,financedate,salecostcardno,salecostcardtype,payamt,1,salebillid,paymode,secondsaleamt*payamt/isnull(salecostproamt,1)            
 from #mcooperatesaleinfo a        
 where a.secondsalerinid >= @fromempinno            
 and a.secondsalerinid <= @toempinno            
    and isnull(secondsaleamt,0)<>0              
          
    --卖卡第三销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,thirdsalerinid,case when  salecooperid='001' and ISNULL(slaepaymode,'1')='2' then 27      
          when  salecooperid='002' and ISNULL(slaepaymode,'1')='1' then 28      
          when  salecooperid='002' and ISNULL(slaepaymode,'1')='2' then 29      
          when  salecooperid='003' and ISNULL(slaepaymode,'1')='1' then 30      
          when  salecooperid='003' and ISNULL(slaepaymode,'1')='2' then 31 end ,financedate,salecostcardno,salecostcardtype,payamt,1,salebillid,paymode,thirdsaleamt*payamt/isnull(salecostproamt,1)            
 from #mcooperatesaleinfo a        
 where a.thirdsalerinid >= @fromempinno            
 and a.thirdsalerinid <= @toempinno            
    and isnull(thirdsaleamt,0)<>0  
      
    --卖卡第四销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,fourthsalerinid,case when  salecooperid='001' and ISNULL(slaepaymode,'1')='2' then 27      
          when  salecooperid='002' and ISNULL(slaepaymode,'1')='1' then 28      
          when  salecooperid='002' and ISNULL(slaepaymode,'1')='2' then 29      
          when  salecooperid='003' and ISNULL(slaepaymode,'1')='1' then 30      
          when  salecooperid='003' and ISNULL(slaepaymode,'1')='2' then 31 end ,financedate,salecostcardno,salecostcardtype,payamt,1,salebillid,paymode,fourthsaleamt*payamt/isnull(salecostproamt,1)            
 from #mcooperatesaleinfo a        
 where a.fourthsalerinid >= @fromempinno            
 and a.fourthsalerinid <= @toempinno            
    and isnull(fourthsaleamt,0)<>0  
      
    --卖卡第五销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,fifthsalerinid,case when  salecooperid='001' and ISNULL(slaepaymode,'1')='2' then 27      
          when  salecooperid='002' and ISNULL(slaepaymode,'1')='1' then 28      
          when  salecooperid='002' and ISNULL(slaepaymode,'1')='2' then 29      
          when  salecooperid='003' and ISNULL(slaepaymode,'1')='1' then 30      
          when  salecooperid='003' and ISNULL(slaepaymode,'1')='2' then 31 end,financedate,salecostcardno,salecostcardtype,payamt,1,salebillid,paymode,fifthsaleamt*payamt/isnull(salecostproamt,1)            
 from #mcooperatesaleinfo a        
 where a.fifthsalerinid >= @fromempinno            
 and a.fifthsalerinid <= @toempinno            
    and isnull(fifthsaleamt,0)<>0  
      
    --卖卡第六销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,sixthsalerinid,case when  salecooperid='001' and ISNULL(slaepaymode,'1')='2' then 27      
          when  salecooperid='002' and ISNULL(slaepaymode,'1')='1' then 28      
          when  salecooperid='002' and ISNULL(slaepaymode,'1')='2' then 29      
          when  salecooperid='003' and ISNULL(slaepaymode,'1')='1' then 30      
          when  salecooperid='003' and ISNULL(slaepaymode,'1')='2' then 31 end,financedate,salecostcardno,salecostcardtype,payamt,1,salebillid,paymode,sixthsaleamt*payamt/isnull(salecostproamt,1)            
 from #mcooperatesaleinfo a        
 where a.sixthsalerinid >= @fromempinno            
 and a.sixthsalerinid <= @toempinno            
    and isnull(sixthsaleamt,0)<>0  
      
    --卖卡第七销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,seventhsalerinid,case when  salecooperid='001' and ISNULL(slaepaymode,'1')='2' then 27      
          when  salecooperid='002' and ISNULL(slaepaymode,'1')='1' then 28      
          when  salecooperid='002' and ISNULL(slaepaymode,'1')='2' then 29      
          when  salecooperid='003' and ISNULL(slaepaymode,'1')='1' then 30      
          when  salecooperid='003' and ISNULL(slaepaymode,'1')='2' then 31 end,financedate,salecostcardno,salecostcardtype,payamt,1,salebillid,paymode,seventhsaleamt*payamt/isnull(salecostproamt,1)            
 from #mcooperatesaleinfo a        
 where a.seventhsalerinid >= @fromempinno            
 and a.seventhsalerinid <= @toempinno            
    and isnull(seventhsaleamt,0)<>0  
      
    --卖卡第八销售人员            
 insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji)            
 select @compid,eighthsalerinid,case when  salecooperid='001' and ISNULL(slaepaymode,'1')='2' then 27      
          when  salecooperid='002' and ISNULL(slaepaymode,'1')='1' then 28      
          when  salecooperid='002' and ISNULL(slaepaymode,'1')='2' then 29      
          when  salecooperid='003' and ISNULL(slaepaymode,'1')='1' then 30      
          when  salecooperid='003' and ISNULL(slaepaymode,'1')='2' then 31 end,financedate,salecostcardno,salecostcardtype,payamt,1,salebillid,paymode,eighthsaleamt*payamt/isnull(salecostproamt,1)            
 from #mcooperatesaleinfo a        
 where a.eighthsalerinid >= @fromempinno            
 and a.eighthsalerinid <= @toempinno            
    and isnull(eighthsaleamt,0)<>0  
      
    create table #mconsumeinfo  
    (  
  cscompid  varchar(10)     Not NULL,   --公司编号  
  csbillid  varchar(20)  Not NULL,   --消费单号  
  cscardno  varchar(20)  NULL    ,   --会员卡号  
  cscardtype  varchar(10)  NULL    ,   --卡类型  
  diyongcardno varchar(20)  NULL    ,   --抵用券号  
  tiaomacardno varchar(20)  NULL    ,   --条码卡号  
  financedate  varchar(8)  NULL    ,   --帐务日期   
  csinfotype  int    Not NULL, --消费类型  1 项目  2 产品  
  csitemno  varchar(20)     NULL,  --项目/产品代码  
  csitemcount  float           NULL,  --数量  
  csitemamt  float           NULL,  --金额  
  cspaymode  varchar(5)  NULL,  --支付方式  
  csfirstsaler varchar(20)     NULL,  --大工工号  
  csfirsttype  varchar(5)     NULL,  --大工类型  
  csfirstinid  varchar(20)  NULL,  --大工内部编号  
  csfirstshare float           NULL,  --大工分享  
  cssecondsaler varchar(20)     NULL,  --中工工号  
  cssecondtype varchar(5)     NULL,  --中工类型  
  cssecondinid varchar(20)  NULL,  --中工内部编号  
  cssecondshare float           NULL,  --中工分享  
  csthirdsaler varchar(20)     NULL,  --小工工号  
  csthirdtype  varchar(5)  NULL,  --小工类型  
  csthirdinid  varchar(20)  NULL,  --小工内部编号  
  csthirdshare float           NULL,  --小工分享  
    )  
      
  
    insert #mconsumeinfo(cscompid,csbillid,cscardno,cscardtype,financedate,csinfotype,csitemno,csitemcount,csitemamt,cspaymode,  
    csfirstsaler,csfirsttype,csfirstinid,csfirstshare,cssecondsaler,cssecondtype,cssecondinid,cssecondshare,csthirdsaler,csthirdtype,csthirdinid,csthirdshare,diyongcardno,tiaomacardno)  
    select a.cscompid,a.csbillid,cscardno,cscardtype,financedate,csinfotype,csitemno,csitemcount,csitemamt,cspaymode,  
    csfirstsaler,csfirsttype,csfirstinid,csfirstshare,cssecondsaler,cssecondtype,cssecondinid,cssecondshare,csthirdsaler,csthirdtype,csthirdinid,csthirdshare,diyongcardno,tiaomacardno  
     from  mconsumeinfo a,dconsumeinfo b  
    where a.cscompid=b.cscompid and a.csbillid=b.csbillid and a.cscompid=@compid and financedate between @fromdate and @todate and ISNULL(csitemamt,0)<>0   
   
 -------------------------这句话非常重要, 美容大项如果多人分享要除相应比重---------------------------  
 update #mconsumeinfo set csitemamt=convert(numeric(20,2),ISNULL(csitemamt,0)/(case when isnull(csfirstsaler,'')<>''   then 1 else 0 end +          
        case when isnull(cssecondsaler,'')<>'' then 1 else 0 end +          
        case when isnull(csthirdsaler,'')<>'' then 1 else 0 end ))          
    from #mconsumeinfo,projectnameinfo with(nolock)          
    where  csitemno=prjno          
 and prjtype='4' and prjpricetype=1          
 and (isnull(cssecondsaler,'')<>''  or  isnull(csthirdsaler,'')<>'' or isnull(csfirstsaler,'')<>'' )     
    
    
     -- 项目大工 7，8，9 (要记录项目抵用券号)  
  insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji,cardid,cardtype)            
  select cscompid,csfirstinid,isnull(convert(int,isnull(csfirsttype,'2')),1)+6,financedate,csitemno,prjname,csitemamt,csitemcount,csbillid,  
      cspaymode,csitemamt,cscardno,   case when cspaymode  in ('12','11') then diyongcardno when cspaymode='13' then tiaomacardno  else cscardtype end    
   from #mconsumeinfo b, projectnameinfo c           
      where  b.csitemno = c.prjno and csinfotype=1  
      and b.csfirstinid >= @fromempinno            
   and b.csfirstinid <= @toempinno            
      and isnull(csitemamt,0)<>0     and ISNULL(csfirstsaler,'')<>''  
        
     -- 项目中工 10，11，12  
  insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji,cardid,cardtype)            
  select cscompid,cssecondinid,isnull(convert(int,isnull(cssecondtype,'2')),1)+9,financedate,csitemno,prjname,csitemamt,csitemcount,csbillid,  
      cspaymode,csitemamt,cscardno,   case when cspaymode  in ('12','11') then diyongcardno when cspaymode='13' then tiaomacardno  else cscardtype end    
   from #mconsumeinfo b, projectnameinfo c           
      where  b.csitemno = c.prjno and csinfotype=1  
      and b.cssecondinid >= @fromempinno            
   and b.cssecondinid <= @toempinno            
      and isnull(csitemamt,0)<>0 and ISNULL(cssecondsaler,'')<>''  
        
        
      -- 项目小工 13，14，15  
  insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji,cardid,cardtype)            
  select cscompid,csthirdinid,isnull(convert(int,isnull(csthirdtype,'2')),1)+12,financedate,csitemno,prjname,csitemamt,csitemcount,csbillid,  
      cspaymode,csitemamt,cscardno,   case when cspaymode  in ('12','11') then diyongcardno when cspaymode='13' then tiaomacardno  else cscardtype end    
   from #mconsumeinfo b, projectnameinfo c           
      where  b.csitemno = c.prjno and csinfotype=1  
      and b.csthirdinid >= @fromempinno            
   and b.csthirdinid <= @toempinno            
      and isnull(csitemamt,0)<>0  and ISNULL(csthirdsaler,'')<>''  
        
       -- 产品大工 16，17，18  
  insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji,cardid,cardtype)            
  select cscompid,csfirstinid,17,financedate,csitemno,goodsname,csitemamt,csitemcount,csbillid,cspaymode,csfirstshare,cscardno,cscardtype  
   from #mconsumeinfo b, goodsnameinfo c           
      where  b.csitemno = c.goodsno and csinfotype=2  
      and b.csfirstinid >= @fromempinno            
   and b.csfirstinid <= @toempinno            
      and isnull(csitemamt,0)<>0      and ISNULL(csfirstsaler,'')<>''  
        
     -- 产品中工 19，20，21  
  insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji,cardid,cardtype)            
  select cscompid,cssecondinid,20,financedate,csitemno,goodsname,csitemamt,csitemcount,csbillid,cspaymode,cssecondshare,cscardno,cscardtype  
   from #mconsumeinfo b, goodsnameinfo c           
      where  b.csitemno = c.goodsno and csinfotype=2  
      and b.cssecondinid >= @fromempinno            
   and b.cssecondinid <= @toempinno            
      and isnull(csitemamt,0)<>0 and ISNULL(cssecondsaler,'')<>''  
        
        
      -- 产品小工 22，23，24  
  insert #work_detail(compid,person_inid,action_id,srvdate,code,name,billamt,ccount,billid,paycode,staffyeji,cardid,cardtype)            
  select cscompid,csthirdinid,23,financedate,csitemno,goodsname,csitemamt,csitemcount,csbillid,cspaymode,csthirdshare,cscardno,cscardtype  
   from #mconsumeinfo b, goodsnameinfo c           
      where  b.csitemno = c.goodsno and csinfotype=2  
      and b.csthirdinid >= @fromempinno            
   and b.csthirdinid <= @toempinno            
      and isnull(csitemamt,0)<>0  and ISNULL(csthirdsaler,'')<>''  
        
                  
    drop table #msalecardinfo      
    drop table #msalebarcodecardinfo  
    drop table #mcardrechargeinfo   
    drop table #mcardchangeinfo     
    drop table #mproexchangeinfo   
    drop table #mcooperatesaleinfo   
    drop table #mconsumeinfo  
    
              
end   
  