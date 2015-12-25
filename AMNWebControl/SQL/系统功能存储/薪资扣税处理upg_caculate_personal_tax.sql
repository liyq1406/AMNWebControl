if exists(select 1 from sysobjects where type='P' and name='upg_caculate_personal_tax')
	drop procedure upg_caculate_personal_tax
go
CREATE procedure upg_caculate_personal_tax(      
 @basicm float ,--个人所得税起征金额      
 @totalmoney float ,--个人月收入      
 @tax float  output , --应扣税      
 @income float output --实际税后收入      
)      
as      
begin      
 declare @cha float      
 set @cha = @totalmoney - @basicm      
      
      
--if (@cha<=0)         set @tax=0      
-- if (@cha>0 and @cha<=500)      set @tax=@cha*0.05      
-- if (@cha>500 and @cha<=2000)  set @tax=@cha*0.1-25      
-- if (@cha>2000 and @cha<=5000)  set @tax=@cha*0.15-125      
-- if (@cha>5000 and @cha<=20000)  set @tax=@cha*0.2-375      
-- if (@cha>20000 and @cha<=40000)  set @tax=@cha*0.25-1375      
-- if (@cha>40000 and @cha<=60000)  set @tax=@cha*0.30-3375      
-- if (@cha>60000 and @cha<=80000)  set @tax=@cha*0.35-6375      
-- if (@cha>80000 and @cha<=100000) set @tax=@cha*0.4-10375      
-- if (@cha>100000  )     set @tax=@cha*0.45-15375      
     
--     1     不超过1500元的                3    
--　 　2     超过1500元至4500元的部分      10    
--　　 3     超过4500元至9000元的部分      20    
--　　 4     超过9000元至35000元的部分     25    
--　　 5     超过35000元至55000元的部分    30    
--　　 6     超过55000元至80000元的部分    35    
--　　 7     超过80000元的部分             45    
 if (@cha<=0)         set @tax=0      
 if (@cha>0 and @cha<=1500)      set @tax=@cha*0.03      
 if (@cha>1500 and @cha<=4500)  set @tax=@cha*0.1-105      
 if (@cha>4500 and @cha<=9000)  set @tax=@cha*0.2-555     
 if (@cha>9000 and @cha<=35000)  set @tax=@cha*0.25-1005    
 if (@cha>35000 and @cha<=55000)  set @tax=@cha*0.30-2755      
 if (@cha>55000 and @cha<=80000)  set @tax=@cha*0.35-5505      
 if (@cha>80000 ) set @tax=@cha*0.45-13505      
    
     
    
       
 set @income = @totalmoney - @tax      
end 