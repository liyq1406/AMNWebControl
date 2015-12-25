	 --备份 
	select * into cardaccountchangehistorybak20140118 from cardaccountchangehistory

	--删除20140101以前的记录
	delete cardaccountchangehistory where chagedate<'20140101'
	--递归新历史的序号
	update a set changeseqno=ISNULL(changeseqno,0)+ccount+1 from cardaccountchangehistory a,( select gcd01c,ccount=COUNT(gcd01c) from [10.0.0.9].S3GOS2016.dbo.gcm04 group by gcd01c ) b
	where a.changecardno=b.gcd01c

	--从老系统加入记录
	insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)
	select gcd00c,gcd01c,gcd02i,row_number() over( PARTITION BY gcd01c order by max(ISNULL(gcd13i,0)) asc) ,
	gcd05i,gcd06f,gcd07c,gcd08c,gcd09d,gcd10f  
	from [10.0.0.9].S3GOS2016.dbo.gcm04 where gcd01c not in (
	select gcd01c from [10.0.0.9].S3GOS2016.dbo.gcm04
	  group by gcd00c,gcd01c,gcd02i,gcd04f having coUNt(gcd01c)>1) 
	  group by  gcd00c,gcd01c,gcd02i,gcd05i,gcd13i,gcd06f,gcd07c,gcd08c,gcd09d,gcd10f 
	  order by gcd01c,gcd02i,gcd13i asc
  
	--delete cardaccountchangehistory where changecardno='AZ00002302' 
	--insert cardaccountchangehistory
	--select * from [MasterDatabase2014].dbo.cardaccountchangehistory where chagedate>='20140101' and changecardno='AZ00002302' 
	--update cardaccountchangehistory set changeseqno=ISNULL(changeseqno,0)+56 where changecardno='AZ00002302'  
    declare @cardcardno  varchar(20)
	declare @changeaccounttype varchar(10)
    declare @changeseqno float
    declare @costaccountlastamt float
	declare cur_each_comp cursor for
	select changecardno,changeaccounttype,changeseqno from cardaccountchangehistory where  chagedate>='20140101' order by changeseqno asc
	open cur_each_comp

	fetch cur_each_comp into @cardcardno,@changeaccounttype,@changeseqno
	while @@fetch_status=0
	begin
		
		if exists (select 1 from cardaccountchangehistory where changecardno=@cardcardno and @changeaccounttype=changeaccounttype and changeseqno<@changeseqno )
		begin
			select top 1 @costaccountlastamt=(case when changetype in (0,6,7,8,9,10,11) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@cardcardno  and @changeaccounttype=changeaccounttype   and changeseqno<@changeseqno order by changeseqno desc 
			update cardaccountchangehistory set changebeforeamt=@costaccountlastamt where changecardno=@cardcardno and @changeaccounttype=changeaccounttype and changeseqno=@changeseqno
		end	 
		fetch cur_each_comp into @cardcardno,@changeaccounttype,@changeseqno
	end
	close cur_each_comp
	deallocate cur_each_comp
	



     


