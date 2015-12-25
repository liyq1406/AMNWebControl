delete tabConsomeAnlysis

insert tabConsomeAnlysis(billno,innerno,itemno)
select ggb01c,ggb12cinid,ggb03c
from ggm01,ggm02
where gga00c=ggb00c and ggb01c=gga01c
 and gga80d between '20130101' and '20131231'
 and ISNULL(gga99i,0)=0 and ISNULL(gga30c,'')=''
 and isnull(ggb15c,'')='1'


insert tabConsomeAnlysis(billno,innerno,itemno)
select ggb01c,ggb13cinid,ggb03c
from ggm01,ggm02
where gga00c=ggb00c and ggb01c=gga01c
 and gga80d between '20130101' and '20131231'
 and ISNULL(gga99i,0)=0 and ISNULL(gga30c,'')=''
 and ISNULL(ggb13cinid,'')<>''
  and isnull(ggb16c,'')='1'


insert tabConsomeAnlysis(billno,innerno,itemno)
select ggb01c,ggb14cinid,ggb03c
from ggm01,ggm02
where gga00c=ggb00c and ggb01c=gga01c
 and gga80d between '20130101' and '20131231'
 and ISNULL(gga99i,0)=0 and ISNULL(gga30c,'')=''
 and ISNULL(ggb13cinid,'')<>''
  and isnull(ggb19c,'')='1'
  
  
  --√¿∑¢
select top 10 haa00c,gae03c,haa01c,innerno,haa02c,pcount=count(billno)/(12-CAST(MIN(SUBSTRING(billno,8,2)) as int ))
from ( select haa00c,haa01c, billno,innerno,haa02c
from tabConsomeAnlysis,ham01 where haa34c=innerno and haa06c='004' and haa00c<>'99999'
group by haa00c,haa01c,billno,innerno,haa02c) as A,gam05
where gae01c=haa00c 
group by haa00c,haa01c,gae03c,innerno,haa02c
having CAST(MIN(SUBSTRING(billno,8,2)) as int )<12 
order by pcount desc

--ÃÃ»æ
select top 10 haa00c,gae03c,haa01c,innerno,haa02c,pcount=count(billno)/(12-CAST(MIN(SUBSTRING(billno,8,2)) as int ))
from ( select haa00c,haa01c, billno,innerno,haa02c
from tabConsomeAnlysis,ham01 where haa34c=innerno and haa06c='006' and haa00c<>'99999'
group by haa00c,haa01c,billno,innerno,haa02c) as A,gam05
where gae01c=haa00c
group by haa00c,haa01c,gae03c,innerno,haa02c
having CAST(MIN(SUBSTRING(billno,8,2)) as int )<12 
order by pcount desc

--√¿»›
select top 10 haa00c,gae03c,haa01c,innerno,haa02c,pcount=count(billno)/(12-CAST(MIN(SUBSTRING(billno,8,2)) as int ))
from ( select haa00c,haa01c, billno,innerno,haa02c
from tabConsomeAnlysis,ham01
 where haa34c=innerno and haa06c='003' and haa00c<>'99999'
group by haa00c,haa01c,billno,innerno,haa02c) as A,gam05
where gae01c=haa00c
group by haa00c,haa01c,gae03c,innerno,haa02c
having CAST(MIN(SUBSTRING(billno,8,2)) as int )<12 
order by pcount desc
  
  
create table cls_yeji_resultx_jiesuan(                                  
  compid varchar(10) not null,  
  ddate  varchar(10) not null,                             
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
 
 create table cls_yeji_resultx_jiesuan_season(                                  
  compid varchar(10) not null,  
  season	int		null,                              
  real_beaut_yeji float null,                                  
  real_hair_yeji float null,                                  
  real_foot_yeji float null                                
 ) 
 
 delete cls_yeji_resultx_jiesuan_season
 insert cls_yeji_resultx_jiesuan_season(compid,season,real_beaut_yeji,real_hair_yeji,real_foot_yeji)
 select compid,1,sum(isnull(real_beaut_yeji,0)),sum(isnull(real_hair_yeji,0)),sum(isnull(real_foot_yeji,0)) 
	from  cls_yeji_resultx_jiesuan where ddate between '201301' and '201303'
	group by compid
	
	 insert cls_yeji_resultx_jiesuan_season(compid,season,real_beaut_yeji,real_hair_yeji,real_foot_yeji)
 select compid,2,sum(isnull(real_beaut_yeji,0)),sum(isnull(real_hair_yeji,0)),sum(isnull(real_foot_yeji,0)) 
	from  cls_yeji_resultx_jiesuan where ddate between '201304' and '201306'
	group by compid
	
	 insert cls_yeji_resultx_jiesuan_season(compid,season,real_beaut_yeji,real_hair_yeji,real_foot_yeji)
 select compid,3,sum(isnull(real_beaut_yeji,0)),sum(isnull(real_hair_yeji,0)),sum(isnull(real_foot_yeji,0)) 
	from  cls_yeji_resultx_jiesuan where ddate between '201307' and '201309'
	group by compid
	
	
 	 insert cls_yeji_resultx_jiesuan_season(compid,season,real_beaut_yeji,real_hair_yeji,real_foot_yeji)
 select compid,4,sum(isnull(real_beaut_yeji,0)),sum(isnull(real_hair_yeji,0)),sum(isnull(real_foot_yeji,0)) 
	from  cls_yeji_resultx_jiesuan where ddate between '201310' and '201312'
	group by compid
	
	
select  * into cls_yeji_resultx_jiesuan_bak from cls_yeji_resultx_jiesuan
select real_beaut_yeji,real_hair_yeji,real_foot_yeji from  cls_yeji_resultx_jiesuan order by compid,ddate 


 --delete cls_yeji_resultx_jiesuan
 --exec upg_yq_compute_comp_classed_yeji_ex_jiesuan '001','20130101','20130131',0
 --exec upg_yq_compute_comp_classed_yeji_ex_jiesuan '001','20130201','20130228',0
 --exec upg_yq_compute_comp_classed_yeji_ex_jiesuan '001','20130301','20130331',0
 --exec upg_yq_compute_comp_classed_yeji_ex_jiesuan '001','20130401','20130430',0
 --exec upg_yq_compute_comp_classed_yeji_ex_jiesuan '001','20130501','20130531',0
 --exec upg_yq_compute_comp_classed_yeji_ex_jiesuan '001','20130601','20130630',0
 --exec upg_yq_compute_comp_classed_yeji_ex_jiesuan '001','20130701','20130731',0
 --exec upg_yq_compute_comp_classed_yeji_ex_jiesuan '001','20130801','20130831',0
 --exec upg_yq_compute_comp_classed_yeji_ex_jiesuan '001','20130901','20130930',0
 --exec upg_yq_compute_comp_classed_yeji_ex_jiesuan '001','20131001','20131031',0
 --exec upg_yq_compute_comp_classed_yeji_ex_jiesuan '001','20131101','20131130',0
 --exec upg_yq_compute_comp_classed_yeji_ex_jiesuan '001','20131201','20131231',0
 
select * from cls_yeji_resultx_jiesuan

	declare @ccount int 
	set @ccount=0
	declare @compid varchar(20)
	declare @yeji	float
	declare @ddate	int
	declare @tcompid varchar(20)
	declare @tyeji	float
	declare @tddate	int
	declare @strSql varchar(200)
	declare cur_each_comp cursor for
	select compid,real_beaut_yeji,season from cls_yeji_resultx_jiesuan_season order by compid,season 
	open cur_each_comp
	fetch cur_each_comp into @compid,@yeji,@ddate
	while @@fetch_status=0
	begin
		set @strSql=''
		if(ISNULL(@tddate,0)<>0 )
		begin
			if(@tcompid=@compid)
			begin
				if(isnull(@tyeji,0)<isnull(@yeji,0))
					set @ccount=@ccount+1
				else
				begin
					if(@ccount=2)
					begin
						set @strSql='select top '+str(@ccount+1)+' compid,gae03c,real_beaut_yeji,season from cls_yeji_resultx_jiesuan_season,gam05 ' 
						set @strSql=@strSql+' where compid='+str(@tcompid)+' and season<='+str(@tddate)+'   and gae01c=compid order by season desc '
						execute(@strSql)
					end
					set @ccount=0
				end
			end
			else
			begin
				set @ccount=0
			end 
		end
		set @tcompid=@compid
		set @tyeji=@yeji
		set @tddate=@ddate
		fetch cur_each_comp into @compid,@yeji,@ddate
	end
	close cur_each_comp
	deallocate cur_each_comp
	
	
	--declare @ccount int 
	--set @ccount=0
	--declare @compid varchar(20)
	--declare @yeji	float
	--declare @ddate	varchar(10)
	
	--declare @tcompid varchar(20)
	--declare @tyeji	float
	--declare @tddate	varchar(10)
	--declare @strSql varchar(200)
	--declare cur_each_comp cursor for
	--select compid,real_beaut_yeji,ddate from cls_yeji_resultx_jiesuan order by compid,ddate 
	--open cur_each_comp
	--fetch cur_each_comp into @compid,@yeji,@ddate
	--while @@fetch_status=0
	--begin
	--	set @strSql=''
	--	if(ISNULL(@tddate,'')<>'' )
	--	begin
	--		if(@tcompid=@compid)
	--		begin
	--			if(isnull(@tyeji,0)<isnull(@yeji,0))
	--				set @ccount=@ccount+1
	--			else
	--			begin
	--				if(@ccount>=4)
	--				begin
	--					set @strSql='select top '+str(@ccount)+' compid,gae03c,real_beaut_yeji,ddate from cls_yeji_resultx_jiesuan,gam05 ' 
	--					set @strSql=@strSql+' where compid='+str(@tcompid)+' and ddate<='+str(@tddate)+'  and gae01c=compid order by ddate desc '
	--					execute(@strSql)
	--				end
	--				set @ccount=0
	--			end
	--		end
	--		else
	--		begin
	--			set @ccount=0
	--		end 
	--	end
	--	set @tcompid=@compid
	--	set @tyeji=@yeji
	--	set @tddate=@ddate
	--	fetch cur_each_comp into @compid,@yeji,@ddate
	--end
	--close cur_each_comp
	--deallocate cur_each_comp