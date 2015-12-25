insert commoninfo(infotype,infoname,parentcodekey,parentcodevalue,codesource,codekey,codevalue)
values('ZHLX','账户类型','6','赠送账户','D','','')
go
insert commoninfo(infotype,infoname,parentcodekey,parentcodevalue,codesource,codekey,codevalue)
values('ZFFS','支付方式','17','赠送支付','D','','')
go
insert sysaccountforpaymode(paymode,accounttype)
values('17','6')
go
update sysparaminfo set paramvalue='1;11;12;13;14;15;16;4;6;7;8;9;A;17' where  paramid='SP067'
go
insert sysparaminfo(compid,paramid,paramname,paramvalue,parammark)
values('001','SP106','是否启用充值赠送金额','0','0 不启用 1 启用')
go
insert sysparaminfo(compid,paramid,paramname,paramvalue,parammark)
select relationcomp,paramid,paramname,paramvalue,parammark 
from sysparaminfo,compchaininfo where curcomp='001' and relationcomp<>'001' and paramid='SP106'
go
