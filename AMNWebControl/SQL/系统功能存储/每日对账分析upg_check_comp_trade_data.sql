if exists(select 1 from sysobjects where type='P' and name='upg_check_comp_trade_data')
	drop procedure upg_check_comp_trade_data
go
CREATE procedure upg_check_comp_trade_data                        
(                                  
 @compid varchar(10) ,                                  
 @datefrom varchar(8),                                  
 @dateto varchar(8)                                
)                                  
as                                  
begin  

end
