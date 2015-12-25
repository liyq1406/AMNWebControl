alter table memberinfo
add issendmsg int
go

create table blacklist
(
	id varchar(50) not null primary key,--主键
	mobilephone varchar(30) null,--手机号码
	acceptdate varchar(30) null,--回复日期和时间
	content varchar(1000)  null,--内容
	operdate varchar(20)  null,--系统接受日期
)
go

insert into sysmodeinfo (sysversion,upmoduleno,curmoduleno,modulename,modulevel,moduletype,remark,moduletitle,moduleurl,modulewidth,moduleheight) values ('2013001','PersonnelControl','PC021','员工年假统计',2,'R','','员工年假统计','PersonnelControl/PC021/index.jsp',900,600)
go

insert into sysmodeinfo (sysversion,upmoduleno,curmoduleno,modulename,modulevel,moduletype,remark,moduletitle,moduleurl,modulewidth,moduleheight)
values ('2013001','PersonnelControl','PC022','门店考勤统计',2,'R','','门店考勤统计','PersonnelControl/PC022/index.jsp',900,600)
go




--exec upg_monthkq_amn '002','201407'



create procedure upg_monthkq_amn

(

	@compid varchar(20),

	@fromdate varchar(10)

)

as

begin

	create table #result

	(

		empno varchar(20) null,

		empname varchar(100) null,

		empinid varchar(50) null,

		kqdate varchar(30) null,

		/*m1	varchar(20) null,

		m2	varchar(20) null,

		m3	varchar(20) null,

		m4	varchar(20) null,

		m5	varchar(20) null,

		m6	varchar(20) null,

		m7	varchar(20) null,

		m8	varchar(20) null,

		m9	varchar(20) null,

		m10	varchar(20) null,

		m11	varchar(20) null,

		m12	varchar(20) null,

		m13	varchar(20) null,

		m14	varchar(20) null,

		m15	varchar(20) null,

		m16	varchar(20) null,

		m17	varchar(20) null,

		m18	varchar(20) null,

		m19	varchar(20) null,

		m20	varchar(20) null,

		m21	varchar(20) null,

		m22	varchar(20) null,

		m23	varchar(20) null,

		m24	varchar(20) null,

		m25	varchar(20) null,

		m26	varchar(20) null,

		m27	varchar(20) null,

		m28	varchar(20) null,

		m29	varchar(20) null,

		m30	varchar(20) null,

		m31	varchar(20) null,

		f1 	varchar(20) null,

		f2 	varchar(20) null,

		f3 	varchar(20) null,

		f4 	varchar(20) null,

		f5 	varchar(20) null,

		f6 	varchar(20) null,

		f7 	varchar(20) null,

		f8 	varchar(20) null,

		f9 	varchar(20) null,

		f10 	varchar(20) null,

		f11 	varchar(20) null,

		f12 	varchar(20) null,

		f13 	varchar(20) null,

		f14 	varchar(20) null,

		f15 	varchar(20) null,

		f16 	varchar(20) null,

		f17 	varchar(20) null,

		f18 	varchar(20) null,

		f19 	varchar(20) null,

		f20 	varchar(20) null,

		f21 	varchar(20) null,

		f22 	varchar(20) null,

		f23 	varchar(20) null,

		f24 	varchar(20) null,

		f25 	varchar(20) null,

		f26 	varchar(20) null,

		f27 	varchar(20) null,

		f28 	varchar(20) null,

		f29 	varchar(20) null,

		f30 	varchar(20) null,

		f31 	varchar(20) null*/

	)



	create table #result_emp

	(

		empno varchar(20) null,

		empname varchar(100) null,

		empinid varchar(50) null,

		sdate varchar(20) null,

		strdate varchar(20) null,

		enddate varchar(20) null,

		ftype varchar(20) null,

		mtype varchar(20) null,

		strdate2 varchar(20) null,

		enddate2 varchar(20) null,

		ftype2 varchar(20) null,

		mtype2 varchar(20) null,



	)



	declare @startdate varchar(20)

	declare @enddate varchar(20)

	

	set @startdate=@fromdate+'01'

	set @enddate=convert(varchar(20),dateadd(mm,1,@startdate)-1,112)



	/*insert into #result(empno,empname,empinid,kqdate)

	select staffno,staffname,menegeno,max(isnull(ddate,0)+isnull(ttime,0))

	from staffinfo,staffkqrecordinfo

	where compid=@compid

	  and fingno=personid

	  and substring(ddate,1,6)=@fromdate

	group by staffno,staffname,menegeno

	union all 

	select staffno,staffname,menegeno,min(isnull(ddate,0)+isnull(ttime,0))

	from staffinfo,staffkqrecordinfo

	where compid=@compid

	  and fingno=personid

	  and substring(ddate,1,6)=@fromdate

	group by staffno,staffname,menegeno*/



	while(@startdate<=@enddate)

	begin

		insert into #result_emp(empno,empname,empinid,sdate)

		select staffno,staffname,manageno,@startdate

		from staffinfo

		where compno=@compid



		set @startdate=convert(varchar(20),dateadd(dd,1,@startdate),112)

	end





	update a set strdate=mindate

	from #result_emp a,(select staffno,staffname,manageno,ddate,min(isnull(ddate,0)+isnull(ttime,0)) mindate

	from staffinfo,staffkqrecordinfo

	where compno=@compid

	  and fingerno=personid

	  and substring(ddate,1,6)=@fromdate

	group by staffno,staffname,manageno,ddate

	) b

	where empno=staffno

	  and manageno=empinid

	  and substring(mindate,1,8)=sdate





    update a set enddate=maxdate

	from #result_emp a,(select staffno,staffname,manageno,ddate,max(isnull(ddate,0)+isnull(ttime,0)) maxdate

	from staffinfo a,staffkqrecordinfo

	where compno=@compid

	  and fingerno=personid

	  and substring(ddate,1,6)=@fromdate

	group by staffno,staffname,manageno,ddate

	) b

	where empno=staffno

	  and manageno=empinid

	  and substring(maxdate,1,8)=sdate





	  update #result_emp set enddate=''

	  where strdate=enddate





	  update a set ftype=leavetype,strdate=isnull(leavedate,'')+isnull(mindate,'')+'00'

	  from #result_emp a,(select staffinid,leavedate,leavetype,min(replace(totime,':','')) mindate from staffleaveinfo

	    where leavedate between @fromdate+'01' and @enddate

		group by staffinid,leavedate,leavetype

		) b

	  where empinid=staffinid

	    and sdate=leavedate

		and isnull(strdate,'')=''

	

	   update a set mtype=leavetype,enddate=isnull(leavedate,'')+isnull(maxdate,'')+'00'

	  from #result_emp a,(select staffinid,leavedate,leavetype,max(replace(totime,':','')) maxdate from staffleaveinfo

	    where leavedate between @fromdate+'01' and @enddate

		group by staffinid,leavedate,leavetype

		) b

	  where empinid=staffinid

	    and sdate=leavedate

		and isnull(enddate,'')=''


		


		update #result_emp set enddate=null 
		where isnull(enddate,'')=''


		update #result_emp set strdate2=enddate,mtype2=ftype,enddate2=strdate,ftype2=mtype
		where isnull(strdate,'1')>isnull(enddate,'1')
		  and isnull(enddate,'')<>'' 

		update #result_emp set strdate2=strdate,enddate2=enddate,mtype2=mtype,ftype2=ftype
		where isnull(strdate,0)<=isnull(enddate,0)
		  and isnull(strdate,'')<>''

		update #result_emp set strdate2=enddate,mtype2=ftype,enddate2=strdate,ftype2=mtype
		where isnull(strdate,0)<=isnull(enddate,0)
		  and isnull(strdate,'')=''

		update #result_emp set strdate2=strdate,enddate2=enddate,mtype2=mtype,ftype2=ftype
		where isnull(strdate,'1')>isnull(enddate,'1')
		  and isnull(enddate,'')='' 

		update #result_emp set mtype2=parentcodevalue
		from #result_emp,commoninfo
		where mtype2=parentcodekey and infotype='QJLX'


		update #result_emp set ftype2=parentcodevalue
		from #result_emp,commoninfo
		where ftype2=parentcodekey and infotype='QJLX'


		update #result_emp set strdate2=mtype2
		where isnull(mtype2,'')<>''

		update #result_emp set enddate2=ftype2
		where isnull(ftype2,'')<>''


		update #result_emp set strdate2='无'
		where isnull(strdate2,'')=''

		update #result_emp set enddate2='无'
		where isnull(enddate2,'')=''



		select empno,empname,empinid,sdate,strdate2,enddate2
		from #result_emp
		order by empno,sdate

	drop table #result_emp
	drop table #result

end
