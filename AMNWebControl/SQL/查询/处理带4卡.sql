/*4	阿玛尼5折卡			张	3649	01503 10001871	01503 10007755	0150310001871 015031 09 001

5	阿玛尼4折卡			张	104		01403 10000370	01403 10000595	0140310000370 014031 09	001

7	阿玛尼4折卡			张	4051	01403 10001277	01403 10007755	0140310007755 014031 09	001

8	阿玛尼3.5折卡		张	106		01353 10000368	01353 10000595	0135310000368 013531 09	001

9	阿玛尼3.5折卡		张	100		01353 10000596	01353 10000672	0135310000596 013531 09	001

10	阿玛尼3.5折卡		张	1506	01353 10000969	01353 10002772	0135310000969 013531 09	001

12	阿玛尼6.5折卡		张	1599	01653 10000596	01653 10002772	0165310000596 016531 09	001

17	江西 5.5折卡		张	1999	01553 60000001	01553 60002772	0155360000001 015536 09	001

21	江西 4.5折卡		张	2001	01453 60000002	01453 60002772	0145360000002 014536 09	001

23	江西 4折卡			张	1999	01403 60000001	01403 60002772	0140360000001 014536 09	001
*/
delete cardsub
--exec upg_getcardsub '起始数字段','开始编号' ,'结束编号','卡类型','卡仓库','卡门店'
delete fab02_cache where cardfrom='0140360000001'
exec upg_getcardsub '01403','60000001' ,'60002772','014036','09','001'

insert fab02_cache(cardclass,cardfrom,cardto,count,storage,compid)
select 	'015031', '0150310000667','0150310000716',50,'09','001' 

select * from  fab02_cache where cardclass ='015031'


select * from fad03 where fdc04c='015031' 
select * from gam10 where gak00c='301'


/*create table cardsub
(
	cardfrom varchar(20) null,
	cardto varchar(20) null,
	ccount	int			null
)
go*/
alter  procedure upg_getcardsub
(
	@param1  varchar(5) ,
	@param2	 int        ,
	@param3  int		,
	@param4  varchar(10) ,
	@param5  varchar(10) ,
	@param6  varchar(10)
)
as
begin
declare @startcard varchar(5)
set @startcard=@param1
declare @cardendnoQ int 
declare @stargetcardnoQA int 
declare @stargetcardnoQB int
set @cardendnoQ		=@param3
set @stargetcardnoQA=@param2
set @stargetcardnoQB=@param2

declare @cardendnoB int 
declare @stargetcardnoBA int 
declare @stargetcardnoBB int
set @stargetcardnoBA=0
set @stargetcardnoBB=0
set @cardendnoB=0

declare @cardendnoS int 
declare @stargetcardnoSA int 
declare @stargetcardnoSB int
set @stargetcardnoSA=0
set @stargetcardnoSB=0
set @cardendnoS=0

declare @ccount int
set @ccount=1

while(@stargetcardnoQB<=@cardendnoQ)
begin
	if(SUBSTRING(CAST(@stargetcardnoQB as varchar(8)),5,1)='4')
	begin
		set @cardendnoB=@stargetcardnoQB			--结束百位数
		set @stargetcardnoBA=@stargetcardnoqA		--目标百位数
		set @stargetcardnoBB=@stargetcardnoqA		--目标百位数
		while(@stargetcardnoBB<=@cardendnoB)
		begin
			if(SUBSTRING(CAST(@stargetcardnoBB as varchar(8)),6,1)='4')
			begin
				set @ccount=1
				--select @stargetcardnoBA,@stargetcardnoBB-1   ----------------------------百位1
				--select '结算10位起始百位1'
				set @cardendnoS=@stargetcardnoBB			--结束十位数
				set @stargetcardnoSA=@stargetcardnoBA		--目标十位数
				set @stargetcardnoSB=@stargetcardnoBA		--目标十位数
				while(@stargetcardnoSB<@cardendnoS)
				begin
					if(SUBSTRING(CAST(@stargetcardnoSB as varchar(8)),7,1)='4')
					begin
						insert cardsub(cardfrom,cardto,ccount)
						select @startcard+CAST(@stargetcardnoSA as varchar(8)),@startcard+CAST(@stargetcardnoSB-1 as varchar(8)),@ccount   ----------------------------十位1
						set @stargetcardnoSA=@stargetcardnoSB+10
						set @stargetcardnoSB=@stargetcardnoSB+10
						set @ccount=1
					end
					set @stargetcardnoSB=@stargetcardnoSB+1
					if(CAST(@stargetcardnoSB as varchar(8)) like '%4%')
						continue
					set @ccount=@ccount+1
					
				end
				insert cardsub(cardfrom,cardto,ccount)
				select @startcard+CAST(@stargetcardnoSA as varchar(8)),@startcard+CAST(@stargetcardnoSB-1 as varchar(8)),@ccount   ----------------------------十位2
						
				set @stargetcardnoBA=@stargetcardnoBB+100
				set @stargetcardnoBB=@stargetcardnoBB+100
			end
			set @stargetcardnoBB=@stargetcardnoBB+1
		end
		--select '结算10位起始百位2'
		set @ccount=1
		--select @stargetcardnoBA,@stargetcardnoBB-1           ----------------------------百位2
		set @cardendnoS=@stargetcardnoBB			--结束十位数
		set @stargetcardnoSA=@stargetcardnoBA		--目标十位数
		set @stargetcardnoSB=@stargetcardnoBA		--目标十位数
		while(@stargetcardnoSB<@cardendnoS)
		begin
			if(SUBSTRING(CAST(@stargetcardnoSB as varchar(8)),7,1)='4')
			begin
				insert cardsub(cardfrom,cardto,ccount)
				select @startcard+CAST(@stargetcardnoSA as varchar(8)),@startcard+CAST(@stargetcardnoSB-1 as varchar(8)),@ccount   ----------------------------十位3
				set @stargetcardnoSA=@stargetcardnoSB+10
				set @stargetcardnoSB=@stargetcardnoSB+10
				set @ccount=1
			end
			set @stargetcardnoSB=@stargetcardnoSB+1
			if(CAST(@stargetcardnoSB as varchar(8)) like '%4%')
				continue
			set @ccount=@ccount+1
					
		end
		insert cardsub(cardfrom,cardto,ccount)
		select @startcard+CAST(@stargetcardnoSA as varchar(8)),@startcard+CAST(@stargetcardnoSB-1 as varchar(8)),@ccount   ----------------------------十位4
				
		set @stargetcardnoQA=@stargetcardnoQB+1000
		set @stargetcardnoQB=@stargetcardnoQB+1000
		
	end
	set @stargetcardnoQB=@stargetcardnoQB+1

end
set @cardendnoB=@stargetcardnoQB			--结束百位数
set @stargetcardnoBA=@stargetcardnoqA		--目标百位数
set @stargetcardnoBB=@stargetcardnoqA		--目标百位数
while(@stargetcardnoBB<=@cardendnoB)
begin
	if(SUBSTRING(CAST(@stargetcardnoBB as varchar(8)),6,1)='4')
	begin
		set @ccount=1
		--select '结算10位起始百位3'
		--select @stargetcardnoBA,@stargetcardnoBB-1		   ----------------------------百位3
		set @cardendnoS=@stargetcardnoBB			--结束十位数
		set @stargetcardnoSA=@stargetcardnoBA		--目标十位数
		set @stargetcardnoSB=@stargetcardnoBA		--目标十位数
		while(@stargetcardnoSB<@cardendnoS)
		begin
			if(SUBSTRING(CAST(@stargetcardnoSB as varchar(8)),7,1)='4')
			begin
				insert cardsub(cardfrom,cardto,ccount)
				select @startcard+CAST(@stargetcardnoSA as varchar(8)),@startcard+CAST(@stargetcardnoSB-1 as varchar(8)),@ccount   ----------------------------十位5
		
				set @stargetcardnoSA=@stargetcardnoSB+10
				set @stargetcardnoSB=@stargetcardnoSB+10
				set @ccount=1
			end
			set @stargetcardnoSB=@stargetcardnoSB+1
			if(CAST(@stargetcardnoSB as varchar(8)) like '%4%')
				continue
			set @ccount=@ccount+1
					
		end
		insert cardsub(cardfrom,cardto,ccount)
		select @startcard+CAST(@stargetcardnoSA as varchar(8)),@startcard+CAST(@stargetcardnoSB-1 as varchar(8)),@ccount   ----------------------------十位6
		set @stargetcardnoBA=@stargetcardnoBB+100
		set @stargetcardnoBB=@stargetcardnoBB+100
	end
	set @stargetcardnoBB=@stargetcardnoBB+1
end
set @ccount=1
--select '结算10位起始百位4'
--select @stargetcardnoBA,@stargetcardnoBB-1					----------------------------百位4
set @cardendnoS=@stargetcardnoBB			--结束十位数
set @stargetcardnoSA=@stargetcardnoBA		--目标十位数
set @stargetcardnoSB=@stargetcardnoBA		--目标十位数
while(@stargetcardnoSB<@cardendnoS)
begin
	if(SUBSTRING(CAST(@stargetcardnoSB as varchar(8)),7,1)='4')
	begin
		insert cardsub(cardfrom,cardto,ccount)
		select @startcard+CAST(@stargetcardnoSA as varchar(8)),@startcard+CAST(@stargetcardnoSB-1 as varchar(8)),@ccount   ----------------------------十位7
		
		set @stargetcardnoSA=@stargetcardnoSB+10
		set @stargetcardnoSB=@stargetcardnoSB+10
		set @ccount=1
	end
	set @stargetcardnoSB=@stargetcardnoSB+1
	if(CAST(@stargetcardnoSB as varchar(8)) like '%4%')
			continue
	set @ccount=@ccount+1
					
end
insert cardsub(cardfrom,cardto,ccount)
select @startcard+CAST(@stargetcardnoSA as varchar(8)),@startcard+CAST(@stargetcardnoSB-2 as varchar(8)),@ccount-1   ----------------------------十位8

insert fab02_cache(cardclass,cardfrom,cardto,count,storage,compid)
select 	@param4, cardfrom,cardto,ccount,@param5,@param6 from cardsub

end