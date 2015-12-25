update  staffinfo set tichengmode=2 where compno in ('301','302','303') and position in ('00101','00103')
update  staffinfo set tichengmode=2 where staffno='9027106'
update  staffinfo set tichengmode=2 where staffno='027106' --SHYQ00000648
update  staffinfo set basesalary=5000  where  staffno='9027106'
update staffinfo set basesalary=5000 where manageno in ('ID00000156','ID00000744','SHYQ00001825','SHYQ00002167','SHYQ00001623','AMN00000440')

update  staffhistory set newsalary=5000 where manageno in ('ID00000156','ID00000744','SHYQ00001825','SHYQ00002167','SHYQ00001623','AMN00000440')
and seqno in (24828,20167,25451,19635,19921)

update  staffhistory set oldsalary=5000 where manageno in ('ID00000156','ID00000744','SHYQ00001825','SHYQ00002167','SHYQ00001623','AMN00000440')
and seqno in (24828,20167,25451,19635,19921) and changetype=4

update staffinfo set basesalary=5000 where manageno ='017017406'
update  staffhistory set newsalary=5000 where manageno ='017017406' and seqno = 20684
update  staffhistory set newsalary=5000,  oldsalary=5000 where manageno ='017017406' and seqno =25069

select * from staffinfo where staffno='017017406LZ'
select * from staffhistory where manageno='017017406'




