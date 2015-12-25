
select compno,person_inid,staffno,staffname,totaltc=convert(numeric(20,1),SUM(ISNULL(staffyeji,0))) from staff_work_salary,staffinfo 
where person_inid=manageno and  position in ('004','00401','00402') and staffno not like '___400' and salary_date between '20140201' and '20140228'
group by staffno,person_inid,staffname,compno order by totaltc desc



select compno,staffno,staffname,totaltc=convert(numeric(20,1),SUM(ISNULL(staffyeji,0))) from staff_work_salary,staffinfo 
where person_inid=manageno and  position in ('003','006','007','00701','00702') and staffno not like '___300' and salary_date between '20140201' and '20140228'
group by staffno,staffname,compno order by totaltc desc


select compno,staffno,staffname,totaltc=convert(numeric(20,1),SUM(ISNULL(staffyeji,0))) from staff_work_salary,staffinfo 
where person_inid=manageno and  position in ('00901','00902','00903','00904') and staffno not like '___600' and salary_date between '20140201' and '20140228'
group by staffno,staffname,compno order by totaltc desc


select compno,staffno,staffname,totaltc=convert(numeric(20,1),SUM(ISNULL(staffyeji,0))) from staff_work_salary,staffinfo 
where person_inid=manageno and  position in ('003') and staffno not like '___300' and salary_date between '20140201' and '20140228'
group by staffno,staffname,compno order by totaltc desc

select compno,staffno,staffname,totaltc=convert(numeric(20,1),SUM(ISNULL(staffyeji,0))) from staff_work_salary,staffinfo 
where person_inid=manageno and  position in ('006') and salary_date between '20140201' and '20140228'
group by staffno,staffname,compno order by totaltc desc

select compno,staffno,staffname,totaltc=convert(numeric(20,1),SUM(ISNULL(staffyeji,0))) from staff_work_salary,staffinfo 
where person_inid=manageno and  position in ('007','00701','00702') and salary_date between '20140201' and '20140228'
group by staffno,staffname,compno order by totaltc desc


