
if exists(select 1 from sysobjects where type='P' and name='upg_compute_costoldpersanlysis_by_date_comps')
	drop procedure upg_compute_costoldpersanlysis_by_date_comps
go
CREATE procedure upg_compute_costoldpersanlysis_by_date_comps   
(  
 @compid varchar(10),  
 @fromdate varchar(10),  
 @todate varchar(10),  
 @ccount int   
)  
as  
begin  
	 create table #tbl_emp  
	 (  
	  compid varchar(10) not null,  
	  empno varchar(20) not null  
	 )  
   
	 create table #tbl_ggm02_zj  
	 (  
	  compid varchar(10) not null,  
	  billno varchar(20) not null,  
	  empno varchar(20) null,  
	  srvtype varchar(10) null,  
	  seq  float null  
	 )  
  
	 insert into #tbl_emp(compid,empno)  
	 select compno,manageno  
	 from staffinfo,compchaininfo  
	 where compno=relationcomp and curcomp=@compid  
	   and position in('006','007')  
	   
	
   
	 insert into #tbl_ggm02_zj(compid,billno,empno,srvtype,seq)  
	 select a.cscompid,a.csbillid,csfirstinid,csfirsttype, csseqno 
	 from mconsumeinfo a with(nolock),dconsumeinfo b with(nolock),#tbl_emp  
	 where a.cscompid=b.cscompid  
	   and a.csbillid=b.csbillid  
	   and b.cscompid=compid  
	   and csfirstinid=empno  
	   and csfirsttype='1'  
	   and financedate between @fromdate and @todate  
	   and ISNULL(csfirstinid,'')<>'' 
     
  
		insert into #tbl_ggm02_zj(compid,billno,empno,srvtype,seq)  
	 select a.cscompid,a.csbillid,cssecondinid,cssecondtype, csseqno 
	 from mconsumeinfo a with(nolock),dconsumeinfo b with(nolock),#tbl_emp  
	 where a.cscompid=b.cscompid  
	   and a.csbillid=b.csbillid  
	   and b.cscompid=compid  
	   and cssecondinid=empno  
	   and cssecondtype='1'  
	   and financedate between @fromdate and @todate  
	   and ISNULL(cssecondinid,'')<>''  
	   
    insert into #tbl_ggm02_zj(compid,billno,empno,srvtype,seq)  
	 select a.cscompid,a.csbillid,csthirdinid,csthirdtype, csseqno 
	 from mconsumeinfo a with(nolock),dconsumeinfo b with(nolock),#tbl_emp  
	 where a.cscompid=b.cscompid  
	   and a.csbillid=b.csbillid  
	   and b.cscompid=compid  
	   and csthirdinid=empno  
	   and csthirdtype='1'  
	   and financedate between @fromdate and @todate
	   and ISNULL(csthirdinid,'')<>''  
  

		 select compid,c.compname,empno=staffno,position,positiontext=parentcodevalue ,staffname,sum(ccount) ccount,sum(ccount)-@ccount as difcount ,strEntrydata=ISNULL(arrivaldate,'')
		 from (select compid,billno,empno,seq,1 as ccount  
		 from #tbl_ggm02_zj  
		 group by compid,billno,empno,seq) b,companyinfo c,staffinfo  d,commoninfo
		 where b.compid=c.compno  
		   and c.compno=b.compid  
		   and manageno=b.empno  
		   and infotype='GZGW' and parentcodekey=position
		 group by compid,c.compname,staffno,position,parentcodevalue,staffname,arrivaldate  
		 order by compid,empno
   
	drop table #tbl_emp
	drop table #tbl_ggm02_zj
end  