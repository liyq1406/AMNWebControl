insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)        
values('047','0260310000451',2,8,1,165,'DH','0010405','20140516',1836)  

update cardaccount set accountbalance=1836-165,accountremark='´¢Öµ¶Ò»»¿Û³ý165' where cardno='0260310000451' and accounttype=2

select * from cardaccount where cardno='0260310000451'
select * from cardaccountchangehistory where changecardno='0260310000451' order by changeseqno

select 1836-1671 165