create table #randNum
(
	randNo  varchar(12) null
)
declare @i int
declare @textid varchar(20)
declare @pass varchar(12)
set @i=1
while(@i<=150)
begin
select @pass=char( cONVERT(NVARCHAR,CONVERT(INT,26*rand())+97))
		+CHAR( CONVERT(NVARCHAR,CONVERT(INT,rand()*26)+97))
		+ CHAR( CONVERT(NVARCHAR,CONVERT(INT,rand()*26)+97))
		+ CHAR( CONVERT(NVARCHAR,CONVERT(INT,rand()*26)+97))
		+ CHAR( CONVERT(NVARCHAR,CONVERT(INT,rand()*26)+97))
		+ CHAR( CONVERT(NVARCHAR,CONVERT(INT,rand()*26)+97))
		+ CHAR( CONVERT(NVARCHAR,CONVERT(INT,rand()*26)+97))
		+ CHAR( CONVERT(NVARCHAR,CONVERT(INT,rand()*26)+97))
	
	insert #randNum
	select substring(sys.fn_sqlvarbasetostr(hashbytes('MD5',@pass)),3,6)
	
	set @i=@i+1
end
update #randNum set randNo=replace(randNo,'a','1')
update #randNum set randNo=replace(randNo,'b','2')
update #randNum set randNo=replace(randNo,'c','3')
update #randNum set randNo=replace(randNo,'d','4')
update #randNum set randNo=replace(randNo,'e','5')
update #randNum set randNo=replace(randNo,'f','6')
update #randNum set randNo=replace(randNo,'g','7')
update #randNum set randNo=replace(randNo,'h','8')
update #randNum set randNo=replace(randNo,'i','9')
select * from #randNum
drop table #randNum