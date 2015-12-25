

insert mstaffsubsidyinfo(entrycompid,entrybillid,handcompid,handstaffid,handstaffinid,subsidyamt,subsidyflag,conditionnum,billflag,operationer,operationdate,startdate,enddate)
select '001','0012014022410'+cast( row_number() over(order by staffno desc) as varchar(2) ),'047',staffno,manageno,3000,1,0,0,'amani','20140227','201402','201402'
from staffinfo where compno='047' and position='003'
--6

insert mstaffsubsidyinfo(entrycompid,entrybillid,handcompid,handstaffid,handstaffinid,subsidyamt,subsidyflag,conditionnum,billflag,operationer,operationdate,startdate,enddate)
select '001','0012014022410'+cast( (row_number() over(order by staffno desc))+6 as varchar(2) ),'047',staffno,manageno,3000,1,0,0,'amani','20140227','201402','201402'
from staffinfo where compno='047' and position in ('004','00401','00402')