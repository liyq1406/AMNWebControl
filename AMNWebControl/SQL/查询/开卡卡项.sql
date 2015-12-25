--013031	ÉÏº£°¢ÂêÄá×ê¿¨
update a  set a.t013031changeamt=agsc05f,a.t013031changecount=b.cgca02c
from zongnedd a,(select gea00c,cgca02c=COUNT(gca02c),agsc05f=SUM(isnull(gsc05f,0)) from gem01,gcm01 ,gsm03
where gca01c=gea07c and gea80d between '20131118' and '20131218' and gca00c=gca13d 
and gca02c='013031'
and isnull(gea99i,0)=0 and gsc04c<>'5' and gsc00c=gea00c and gsc01c=gea01c and gsc02c='ge' and gea00c<>'001' and gea02i=4
group by gea00c) b
where a.compid=b.gea00c   and a.ddate='201311'


--013531	ÉÏº£°¢ÂêÄá½ğ¿¨
update a  set a.t013531changeamt=agsc05f,a.t013531changecount=b.cgca02c
from zongnedd a,(select gea00c,cgca02c=COUNT(gca02c),agsc05f=SUM(isnull(gsc05f,0)) from gem01,gcm01 ,gsm03
where gca01c=gea07c and gea80d between '20131118' and '20131218' and gca00c=gca13d 
and gca02c='013531'
and isnull(gea99i,0)=0 and gsc04c<>'5' and gsc00c=gea00c and gsc01c=gea01c and gsc02c='ge' and gea00c<>'001' and gea02i=4
group by gea00c) b
where a.compid=b.gea00c   and a.ddate='201311'

--014031	ÉÏº£°¢ÂêÄá4ÕÛ
update a  set a.t014031changeamt=agsc05f,a.t014031changecount=b.cgca02c
from zongnedd a,(select gea00c,cgca02c=COUNT(gca02c),agsc05f=SUM(isnull(gsc05f,0)) from gem01,gcm01 ,gsm03
where gca01c=gea07c and gea80d between '20131118' and '20131218' and gca00c=gca13d 
and gca02c='014031'
and isnull(gea99i,0)=0 and gsc04c<>'5' and gsc00c=gea00c and gsc01c=gea01c and gsc02c='ge' and gea00c<>'001' and gea02i=4
group by gea00c) b
where a.compid=b.gea00c   and a.ddate='201311'


--015031	ÉÏº£°¢ÂêÄá5ÕÛ
update a  set a.t015031changeamt=agsc05f,a.t015031changecount=b.cgca02c
from zongnedd a,(select gea00c,cgca02c=COUNT(gca02c),agsc05f=SUM(isnull(gsc05f,0)) from gem01,gcm01 ,gsm03
where gca01c=gea07c and gea80d between '20131118' and '20131218' and gca00c=gca13d 
and gca02c='015031'
and isnull(gea99i,0)=0 and gsc04c<>'5' and gsc00c=gea00c and gsc01c=gea01c and gsc02c='ge' and gea00c<>'001' and gea02i=4
group by gea00c) b
where a.compid=b.gea00c   and a.ddate='201311'


--016531	ÉÏº£°¢ÂêÄá6.5ÕÛ
update a  set a.t016531changeamt=agsc05f,a.t016531changecount=b.cgca02c
from zongnedd a,(select gea00c,cgca02c=COUNT(gca02c),agsc05f=SUM(isnull(gsc05f,0)) from gem01,gcm01 ,gsm03
where gca01c=gea07c and gea80d between '20131118' and '20131218' and gca00c=gca13d 
and gca02c='016531'
and isnull(gea99i,0)=0 and gsc04c<>'5' and gsc00c=gea00c and gsc01c=gea01c and gsc02c='ge' and gea00c<>'001' and gea02i=4
group by gea00c) b
where a.compid=b.gea00c   and a.ddate='201311'
