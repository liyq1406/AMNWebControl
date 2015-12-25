
if exists(select 1 from sysobjects where type='P' and name='upg_compute_costanlysis_by_date_comps')
	drop procedure upg_compute_costanlysis_by_date_comps
go
CREATE procedure upg_compute_costanlysis_by_date_comps                 
(          
 @compid varchar(10),  ---公司编号        
 @fromdate varchar(10),--开始日期        
 @todate varchar(10),  --结束日期     
 @viewtype int         --查看对象       
)          
as          
begin          
	create table #empinfobydatez_js          
	(                                    
		seqno int identity not null,                                                            
		inid varchar(20) null,                                                            
		empid varchar(20) null,                                                            
		datefrom varchar(8) null,                                                            
		dateto  varchar(8) null,                                                            
		position varchar(10) null,                                                            
		salary  float null,                                                            
		sharetype varchar(5) null,                                                            
		sharerate float null,                                                            
		deducttax int null                                                            
	)          
	create clustered index idx_empinfobydatez_js on #empinfobydatez_js(empid,datefrom,dateto)                
           
	create table #resulet          
	(          
		empno varchar(20) not null,          
		empname varchar(30) null,          
		inid varchar(20) null,          
		oldxf float   null, --老客洗头        
		xfcount float  null,--洗头个数        
		mrcount float  null,--美容项目个数          
		olditem float   null,--老客项目          
		itemcount float null,--项目数          
		oldcount float  null,--老客人数          
		hlcount float  null,--护理个数        
		rfcount float   null,--染发个数        
		recount float   null,--热烫        
		tfcount float   null,--烫发个数        
		gmitem   float  null,--购买疗程数        
	)                                
           
	 create table #tbl_temp          
	 (          
	  billno varchar(20) not null,          
	  inid varchar(20) null,          
	  seq float null,          
	  empno varchar(20) not null,          
	  itemid varchar(20) not null,          
	  clenttype float  null,          
	  ccount float  null,          
	 )          
           
	 create table #tbl_dis          
	 (          
	  billno varchar(20) not null,          
	  empno varchar(20) not null,          
	  inid varchar(20) not null,          
	  itemid varchar(20) not null,          
	  clenttype float null,          
	  ccount float null          
	 )          
	           
           
	    
		 if(@viewtype=1)        
		 begin          
		 insert into #resulet(empno,inid)        
		 select staffno,manageno    
		 from staffinfo        
		 where compno=@compid         
		   and position in('004','00401','00402','008','00901','00902','00903','00904')        
		 end          
		 else if(@viewtype=2)        
		 begin        
		 insert into #resulet(empno,inid)        
		 select staffno,manageno        
		 from staffinfo        
		 where compno=@compid         
		   and position in('004','00401','00402')    
		 end        
		 else if(@viewtype=3)        
		 begin        
		 insert into #resulet(empno,inid)        
		 select staffno,manageno  
		 from staffinfo        
		 where compno=@compid    
		   and position in('008','00901','00902','00903','00904')    
		 end        
         
		 insert into #tbl_temp(billno,inid,empno,itemid,clenttype,ccount,seq)          
		 select b.csbillid,csfirstinid,csfirstsaler,csitemno,csfirsttype,csitemcount,csitemamt          
		 from mconsumeinfo a with(nolock),dconsumeinfo b  with(nolock),#resulet          
		 where a.cscompid=b.cscompid          
		   and a.csbillid=b.csbillid          
		   and financedate between @fromdate and @todate          
		   and csfirstinid=inid    
		   and ISNULL(csfirstinid,'')<>''    
		   and ISNULL(a.backcsbillid,'')=''    
		   and ISNULL(a.backcsflag,0)=0    
           
	   insert into #tbl_temp(billno,inid,empno,itemid,clenttype,ccount,seq)          
		 select b.csbillid,cssecondinid,cssecondsaler,csitemno,cssecondtype,csitemcount,csitemamt          
		 from mconsumeinfo a with(nolock),dconsumeinfo b  with(nolock),#resulet          
		 where a.cscompid=b.cscompid          
		   and a.csbillid=b.csbillid          
		   and financedate between @fromdate and @todate          
		   and cssecondinid=inid    
		   and ISNULL(cssecondinid,'')<>''    
		   and ISNULL(a.backcsbillid,'')=''    
		   and ISNULL(a.backcsflag,0)=0         
             
		   insert into #tbl_temp(billno,inid,empno,itemid,clenttype,ccount,seq)          
		 select b.csbillid,csthirdinid,csthirdsaler,csitemno,csthirdtype,csitemcount,csitemamt          
		 from mconsumeinfo a with(nolock),dconsumeinfo b  with(nolock),#resulet          
		 where a.cscompid=b.cscompid          
		   and a.csbillid=b.csbillid          
		   and financedate between @fromdate and @todate          
		   and csthirdinid=inid    
		   and ISNULL(csthirdinid,'')<>''    
		   and ISNULL(a.backcsbillid,'')=''    
		   and ISNULL(a.backcsflag,0)=0           
           
		 create table #table_temp          
		 (          
		 empno varchar(20) not null,          
		 itemid varchar(20) null,    
		 ccount float  null,          
		 )          
		           
		           
		 insert into #tbl_dis(billno,empno,inid,itemid,clenttype,ccount)          
		 select billno,empno,inid,itemid,clenttype,ccount          
		 from #tbl_temp          
		 group by billno,empno,inid,itemid,clenttype,ccount,seq          
           
		 --计算洗发做的个数        
		 insert #table_temp(empno,ccount)        
		 select inid,SUM(ccount)        
		 from #tbl_dis,projectnameinfo        
		 where itemid=prjno    
		   and prjreporttype in('00','01')    
		 group by inid        
           
		 update a set xfcount=ccount        
		 from #resulet a,#table_temp b        
		 where a.inid=b.empno        
		           
		 delete #table_temp          
           
		--老客洗发        
		insert #table_temp(empno,ccount)        
		 select inid,SUM(ccount)        
		 from #tbl_dis,projectnameinfo        
		 where itemid=prjno         
		   and prjreporttype in('00','01')    
		   and clenttype=1    
		 group by inid    
           
            
		 update a set oldxf=ccount          
		 from #resulet a,#table_temp b          
		 where a.inid=b.empno          
		           
		 delete #table_temp          
           
		 --美容项目        
		 insert #table_temp(empno,ccount)          
		 select inid,SUM(ccount)          
		 from #tbl_dis,projectnameinfo          
		 where itemid=prjno            
		   and prjreporttype='4'          
		   and prjpricetype=1  
		 group by inid          
           
           
		 update a set mrcount=ccount          
		 from #resulet a,#table_temp b          
		 where a.inid=b.empno          
		          
		 delete #table_temp          
		           
		 --老客项目        
		 insert #table_temp(empno,ccount)          
		 select inid,SUM(ccount)          
		 from #tbl_dis,projectnameinfo          
		 where itemid=prjno    
		   and prjpricetype=1          
		   and clenttype=1          
		 group by inid          
           
           
		 update a set olditem=ccount          
		 from #resulet a,#table_temp b          
		 where a.inid=b.empno          
		          
		 delete #table_temp          
           
		 --老客        
		 insert #table_temp(empno,ccount)          
		 select inid,1          
		 from #tbl_dis  
		 where clenttype=1          
		 group by billno,inid          
		           
		 update a set oldcount=ccount        
		 from #resulet a,(select empno,SUM(ccount) ccount from #table_temp group by empno) b        
		 where a.inid=b.empno        
		           
		 delete #table_temp        
           
		 --项目个数        
		 insert #table_temp(empno,ccount)        
		 select inid,SUM(ccount)  
		 from #tbl_dis,projectnameinfo  
		 where itemid=prjno         
		   and prjpricetype=1  
		 group by inid        
		           
           
		 update a set itemcount=ccount          
		 from #resulet a,#table_temp b          
		 where a.inid=b.empno        
		          
		 delete #table_temp        
		         
         
         
		 if(@viewtype=1 or @viewtype=3)        
		 begin        
		 --计算护理的个数        
		  insert #table_temp(empno,ccount)        
		  select inid,SUM(ccount)        
		  from #tbl_dis,projectnameinfo        
		  where itemid=prjno      
			and prjreporttype in('04','05','06','07')         
		  group by inid        
		        
		  update a set hlcount=ccount  
		  from #resulet a,#table_temp b        
		  where a.inid=b.empno  
		          
		  delete #table_temp        
		          
		  --计算烫染的个数        
		  insert #table_temp(empno,ccount)  
		  select inid,SUM(ccount)  
		  from #tbl_dis,projectnameinfo  
		  where itemid=prjno  
			and prjreporttype='03'  
		  group by inid  
          
          
		  update a set tfcount=ccount        
		  from #resulet a,#table_temp b        
		  where a.inid=b.empno        
		        
		  delete #table_temp        
          
		  --计算染发的个数        
		  insert #table_temp(empno,ccount)        
		  select inid,SUM(ccount)        
		  from #tbl_dis,projectnameinfo        
		  where itemid=prjno         
			and prjreporttype='02'        
		  group by inid        
          
		  update a set rfcount=ccount        
		  from #resulet a,#table_temp b        
		  where a.inid=b.empno        
		        
		  delete #table_temp        
          
		  --计算热烫的个数        
		  insert #table_temp(empno,ccount)        
		  select inid,SUM(ccount)        
		  from #tbl_dis,projectnameinfo        
		  where itemid=prjno          
			and prjreporttype='03'    
			and prjname like '%(热烫)%'        
		  group by inid        
          
		  update a set recount=ccount        
		  from #resulet a,#table_temp b        
		  where a.inid=b.empno        
		        
		  delete #table_temp        
		         
		  delete #tbl_temp        
		  --购买疗程        
		  insert into #tbl_temp(billno,empno,inid,ccount,itemid)        
		  select b.changebillid,b.firstsalerid,b.firstsalerinid,procount,changeproid    
		  from mproexchangeinfo a with(nolock),dproexchangeinfo b with(nolock)   
		  where a.changecompid=@compid     and a.changecompid=b.changecompid and a.changebillid=b.changebillid
			and changedate between @fromdate and @todate    
			and ISNULL(firstsalerinid,'')<>'' 
        
 	     insert into #tbl_temp(billno,empno,inid,ccount,itemid)        
		  select b.changebillid,b.secondsalerid,b.secondsalerinid,procount,changeproid    
		  from mproexchangeinfo a with(nolock),dproexchangeinfo b with(nolock)   
		  where a.changecompid=@compid     and a.changecompid=b.changecompid and a.changebillid=b.changebillid
			and changedate between @fromdate and @todate    
			and ISNULL(secondsalerinid,'')<>''    
			
			insert into #tbl_temp(billno,empno,inid,ccount,itemid)        
		  select b.changebillid,b.thirdsalerid,b.thirdsalerinid,procount,changeproid    
		  from mproexchangeinfo a with(nolock),dproexchangeinfo b with(nolock)   
		  where a.changecompid=@compid     and a.changecompid=b.changecompid and a.changebillid=b.changebillid
			and changedate between @fromdate and @todate
			 and ISNULL(thirdsalerinid,'')<>''    
			 
			 
			insert into #tbl_temp(billno,empno,inid,ccount,itemid)        
		  select b.changebillid,b.fourthsalerid,b.fourthsalerinid,procount,changeproid    
		  from mproexchangeinfo a with(nolock),dproexchangeinfo b with(nolock)   
		  where a.changecompid=@compid     and a.changecompid=b.changecompid and a.changebillid=b.changebillid
			and changedate between @fromdate and @todate
			 and ISNULL(fourthsalerinid,'')<>''    
        
		  insert into #table_temp(empno,itemid,ccount)    
		  select inid,itemid,SUM(ccount)    
		  from #tbl_temp    
		  group by inid,itemid    
      
		 
     
     
		  update a set gmitem=b.ccount    
		  from #resulet a,(select empno,SUM(ccount) ccount from #table_temp group by empno) b    
		  where a.inid=b.empno    
		 end        
           
		 update #resulet set empname=staffname          
		 from #resulet,staffinfo        
		 where inid=manageno        
              
           
		 select * from #resulet         
		 where ISNULL(xfcount,0)+ISNULL(oldxf,0)+ISNULL(mrcount,0)+        
			   ISNULL(olditem,0)+ISNULL(itemcount,0)+ISNULL(oldcount,0)+        
			   ISNULL(hlcount,0)+ISNULL(rfcount,0)+ISNULL(tfcount,0)+        
			   isnull(recount,0)+ISNULL(gmitem,0)>0          
           
		 drop table #empinfobydatez_js          
		 drop table #resulet          
		 drop table #table_temp          
		 drop table #tbl_temp          
		 drop table #tbl_dis          
end 