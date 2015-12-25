--alter table nointernalcardinfo
--add uespassward		int					null	,--是否需要密码  -- 0 不需要 1 需要
--	cardpassward	varchar(20)			null	

--alter table mpackageinfo
--add usedate			varchar(10)		NULL		--套餐截止使用日期

	--alter table mpackageinfo
	--add usetype			int				NULL		--套餐使用范围  1 条码卡  2 疗程兑换

insert nointernalcardinfo(cardvesting,cardno,cardtype,carduseflag,entrytype,cardstate,oldvalidate,lastvalidate,enabledate,uespassward,cardpassward)
select '001',cardno,2,1,0,0,'20140601','20140601','20140304',1,cardpassword from k3TMCard

update  mpackageinfo  set usedate='20240101'
update  mpackageinfo  set usetype=1