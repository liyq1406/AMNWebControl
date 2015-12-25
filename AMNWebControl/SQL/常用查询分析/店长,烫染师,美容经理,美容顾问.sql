select compno,staffname,staffno,resultrate,baseresult from staffinfo where position in ('00103','00101') 

--南昌的不在

--美容经理,美容顾问  美容部实业绩 系数 0.02
update staffinfo set resultrate=0.02 where position in ('00103','00101')  
--美容经理 014107 陈俊   014014420  0.015 
--美容经理 033106 黄丹   ID00003520 0.015
update staffinfo set resultrate=0.015 where position in ('00103','00101')   and manageno in ('014014420','ID00003520')
--美容经理 013013102 陈华 不需要门店业绩
update staffinfo set resultrate=0 where position in ('00103','00101')   and manageno ='013013102'

--南昌的不在
update staffinfo set resultrate=0 where position in('00105','0010502','0010503')  

--大堂经理(C) 大堂经理(B)  总虚业绩 0.001 
update staffinfo set resultrate=0.001 where position in('00105','0010502') and compno not in (select relationcomp from compchaininfo where curcomp='00102')  
--大堂经理(A)  总虚业绩 0.004 
update staffinfo set resultrate=0.004 where position in('0010503') and compno not in (select relationcomp from compchaininfo where curcomp='00102')  