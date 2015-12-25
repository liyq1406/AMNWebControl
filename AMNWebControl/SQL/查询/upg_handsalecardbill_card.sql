
-------会员卡疗程兑换----
if exists(select 1 from sysobjects where type='P' and name='upg_handaccountChangebill_card')
	drop procedure upg_handaccountChangebill_card
go
create procedure upg_handaccountChangebill_card
@compid			varchar(10),	--兑换公司      
@billid			varchar(20)		--兑换单号 
as      
begin 
	--更新内部工号
	update dproexchangeinfo set firstsalerinid=manageno		from dproexchangeinfo,staffinfo	where changecompid=@compid and changebillid=@billid and firstsalerid=staffno and compno=@compid
	update dproexchangeinfo set secondsalerinid=manageno	from dproexchangeinfo,staffinfo	where changecompid=@compid and changebillid=@billid and secondsalerid=staffno and compno=@compid
	update dproexchangeinfo set thirdsalerinid=manageno		from dproexchangeinfo,staffinfo	where changecompid=@compid and changebillid=@billid and thirdsalerid=staffno and compno=@compid
	update dproexchangeinfo set fourthsalerinid=manageno	from dproexchangeinfo,staffinfo	where changecompid=@compid and changebillid=@billid and fourthsalerid=staffno and compno=@compid
	
	declare @changecardno				varchar(20)		--兑换卡号
	declare @changedate					varchar(8)		--兑换日期
	declare @changetime					varchar(9)		--兑换时间
	
	declare @changeaccount				varchar(5)		--抵用账户
	declare @changeaccountamt			float			--抵用账户金额
	declare @changeaccount4amt			float			--抵用疗程账户金额
	declare @inseraccount4amt			float			--兑换疗程账户金额
	
	select @changecardno=changecardno,@changedate=changedate,@changetime=changetime,@changeaccount=ISNULL(changeaccounttype,0)
	from mproexchangeinfo with(nolock) where changecompid=@compid and changebillid=@billid
	
	select @changeaccountamt=SUM(ISNULL(changebyaccountamt,0)),@changeaccount4amt=SUM(ISNULL(changebyproaccountamt,0)),@inseraccount4amt=SUM(isnull(changeproamt,0))
	from  dproexchangeinfo with(nolock) where changecompid=@compid and changebillid=@billid
	
	--更新账户金额
		if(ISNULL(@changeaccountamt,0)>0)--抵用账户
		begin
			update cardaccount set accountbalance=ISNULL(accountbalance,0)-@changeaccountamt where accounttype=@changeaccount and cardno=@changecardno
		end
		if(ISNULL(@changeaccount4amt,0)>0)--抵用疗程账户
		begin
			update cardaccount set accountbalance=ISNULL(accountbalance,0)-@changeaccount4amt where accounttype='4' and cardno=@changecardno
		end
		if(ISNULL(@inseraccount4amt,0)>0)--兑换疗程账户
		begin
			update cardaccount set accountbalance=ISNULL(accountbalance,0)+@inseraccount4amt where accounttype='4' and cardno=@changecardno
		end
	--新增账户历史
		declare @costaccountseqno		float
		declare @curcardamt				float
		select @costaccountseqno=MAX(changeseqno)+1 from cardaccountchangehistory where changecardno=@changecardno 
		if(ISNULL(@changeaccountamt,0)>0)--抵用账户
		begin
			select top 1 @curcardamt=(case when changetype in (0,6,7,8,9,10,11) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@changecardno  and changeaccounttype=CAST(@changeaccount as int) order by changeseqno desc 
			insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)
			values(@compid,@changecardno,CAST(@changeaccount as int),isnull(@costaccountseqno,0),12,@changeaccountamt,'DH',@billid,@changedate,@curcardamt)
			set @costaccountseqno=@costaccountseqno+1
		end
		if(ISNULL(@changeaccount4amt,0)>0)--抵用疗程账户
		begin
			select top 1 @curcardamt=(case when changetype in (0,6,7,8,9,10,11) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@changecardno  and changeaccounttype=4 order by changeseqno desc 
			insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)
			values(@compid,@changecardno,4,isnull(@costaccountseqno,0),13,@changeaccount4amt,'DH',@billid,@changedate,@curcardamt)
			set @costaccountseqno=@costaccountseqno+1
		end
		if(ISNULL(@inseraccount4amt,0)>0)--新增疗程账户
		begin
			select top 1 @curcardamt=(case when changetype in (0,6,7,8,9,10,11) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@changecardno  and changeaccounttype=4 order by changeseqno desc 
			insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)
			values(@compid,@changecardno,4,isnull(@costaccountseqno,0),11,@inseraccount4amt,'DH',@billid,@changedate,@curcardamt)
			set @costaccountseqno=@costaccountseqno+1
		end
	--生成疗程信息
		declare @proseqno float
		select @proseqno=MAX(proseqno)+1 from cardproaccount with(nolock) where cardno=@changecardno 
		insert cardproaccount(cardvesting,cardno,projectno,proseqno,propricetype,salecount,costcount,lastcount,saleamt,costamt,lastamt,saledate,cutoffdate,proremark)
		select changecompid,@changecardno,changeproid,ISNULL(@proseqno,0)+ISNULL(changeseqno,0),4,ISNULL(changeprocount,0),0,ISNULL(changeprocount,0),
		ISNULL(changeproamt,0),0,ISNULL(changeproamt,0),@changedate,'','' 
		from dproexchangeinfo where changecompid=@compid and changebillid=@billid 
	--生成抵扣信息	
		if(ISNULL(@changeaccount4amt,0)>0)--抵用疗程账户
		begin
			update a set costcount=ISNULL(a.costcount,0)+ISNULL(b.changeprocount,0),
									  lastcount=ISNULL(a.lastcount,0)-ISNULL(b.changeprocount,0),
									  costamt=ISNULL(a.costamt,0)-ISNULL(b.changeproamt,0),
									  lastamt=ISNULL(a.lastamt,0)-ISNULL(b.changeproamt,0),
									  exchangeseqno=ISNULL(b.changeseqno,0),
									  changecompid=ISNULL(b.changecompid,''),
									  changebillid=ISNULL(b.changebillid,'')
			from cardproaccount a,dproexchangeinfobypro b
			where b.changecompid=@compid and b.changebillid=@billid and a.cardno=@changecardno and a.projectno=b.changeproid and a.proseqno=b.bproseqno
		end
	--处理抵用券状态
	 update nointernalcardinfo set cardstate=2,usedate=@changedate
	 from nointernalcardinfo a,dproexchangeinfo b
	 where b.changecompid=@compid and b.changebillid=@billid and b.nointernalcardno=a.cardno
end 
go  

