
	  
	  update a set tiaomacardno=diyongcardno from mconsumeinfo a,dconsumeinfo b
	  where a.csbillid=b.csbillid and a.cscompid=b.cscompid
	  and b.cspaymode='13' and a.financedate <'20131201'
	  	insert dconsumeinfo(cscompid,csbillid,csinfotype,csseqno,csitemno,csitemunit,csitemcount,csunitprice,csdiscount,csdisprice,csitemamt,cspaymode,
					csfirstsaler,csfirsttype,csfirstinid,cssecondsaler,cssecondtype,cssecondinid,csthirdsaler,csthirdtype,csthirdinid,csortherpayid,csproseqno)
	select ggb00c,ggb01c,1,ggb02f,ggb03c,ggb04c,ggb05f,ggb08f,ggb09f,ggb10f,ggb11f,ggb27c,ggb12c,ggb15c,ggb12cinid,ggb13c,ggb16c,ggb13cinid,ggb14c,ggb19c,ggb14cinid,gga24c,ggb47i
	from [10.0.0.9].S3GOS2016.dbo.ggm01,[10.0.0.9].S3GOS2016.dbo.ggm02
	 where gga00c=ggb00c and gga01c=ggb01c and gga80d<'20131201'
	 
	 
	 insert dconsumeinfo(cscompid,csbillid,csinfotype,csseqno,csitemno,csitemunit,csitemcount,csunitprice,csdiscount,csdisprice,csitemamt,cspaymode,
					csfirstsaler,csfirstshare,csfirstinid,cssecondsaler,cssecondshare,cssecondinid,csthirdsaler,csthirdshare,csthirdinid,csortherpayid,csproseqno,saletype)
	select ggc00c,ggc01c,2,ggc02f,ggc03c,ggc04c,ggc05f,ggc08f,ggc09f,ggc10f,ggc11f,ggc27c,ggc12c,ggc21f,ggc12cinid,ggc15c,ggc23f,ggc15cinid,ggc17c,ggc25f,ggc17cinid,gga24c,0,ggc32i
	from [10.0.0.9].S3GOS2016.dbo.ggm01,[10.0.0.9].S3GOS2016.dbo.ggm03,dconsumeinfo
	 where gga00c=ggc00c and gga01c=ggc01c and gga80d <'20131201' and ISNULL(ggc32i,0)=2 and cscompid=ggc00c and csbillid= ggc01c and csinfotype=1
	
	

	 go
	 	insert dpayinfo(paycompid,paybillid,paybilltype,payseqno,paymode,payamt,payremark)
	select gsc00c,gsc01c,'SY',gsc03f,gsc04c,gsc05f,gsc06c 
	from [10.0.0.9].S3GOS2016.dbo.ggm01,[10.0.0.9].S3GOS2016.dbo.gsm03 
	 where gsc00c=gga00c and gsc01c=gga01c and gsc02c='gx' and gga80d<'20131201'
	 go
	 	insert msalecardinfo(salecompid,salebillid,saledate,saletime,salecardno,salecardtype,membername,memberphone,membersex,memberpcid,memberbirthday,
                     salekeepamt,saledebtamt,saletotalamt,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                     ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,
                     financedate,saleroperator,saleroperdate,cardappbillid,corpscardno,salebakflag)
	select gna00c,gna01c,gna02d,gna12t,gna13c,gna04c,gna06c,gna66c,gna85i,gna21c,gna10d,gna17f,gna18f,gna17f,gna20c,gna20cinid,gna67f,gna28c,gna28cinid,gna69f,
          gna30c,gna30cinid,gna71f,gna32c,gna32cinid,gna86f,gna34c,gna34cinid,gna87f,gna36c,gna36cinid,gna88f,gna38c,gna38cinid,gna89f,gna40c,gna40cinid,gna90f,gna42c,gna42cinid,gna91f,gna44c,gna44cinid,gna92f,gna80d,gna93c,gna94d,gna25c,gna81c,gna99i
    from [10.0.0.9].S3GOS2016.dbo.gnm01 where gna80d<'20131201'
    go
    insert dpayinfo(paycompid,paybillid,paybilltype,payseqno,paymode,payamt,payremark)
    select gsc00c,gsc01c,'SK',gsc03f,gsc04c,gsc05f,gsc06c 
	from [10.0.0.9].S3GOS2016.dbo.gnm01,[10.0.0.9].S3GOS2016.dbo.gsm03 
	 where gsc00c=gna00c and gsc01c=gna01c and gsc02c='gn' and gna80d <'20131201'
	 go
	 insert mcardrechargeinfo(rechargecompid,rechargebillid,rechargedate,rechargetime,rechargecardno,rechargecardtype,rechargeaccounttype,rechargetype,
						membername,rechargekeepamt,rechargedebtamt,curcardamt,curcarddebtamt,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
                     thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
                     sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
                      ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,
                     financedate,operationer,operationdate,backbillid,salebakflag)
	select gcl00c,gcl01c,gcl02d,gcl56t,gcl03c,gcl15c,gcl04c,gcl06i,gcl05c,gcl65f,gcl13f,0,0,gcl09c,gcl09cinid,gcl58f,gcl17c,gcl17cinid,gcl60f,gcl19c,gcl19cinid,gcl62f,gcl22c,gcl22cinid,gcl83f,
	 gcl24c,gcl24cinid,gcl84f,gcl26c,gcl26cinid,gcl85f,gcl28c,gcl28cinid,gcl86f, gcl30c,gcl30cinid,gcl87f,gcl32c,gcl32cinid,gcl88f,gcl34c,gcl34cinid,gcl89f,gcl80d,gcl93c,gcl94d,gcl54c,gcl99i
	from [10.0.0.9].S3GOS2016.dbo.gcm10  where gcl80d<'20131201'
	go
		insert dpayinfo(paycompid,paybillid,paybilltype,payseqno,paymode,payamt,payremark)
	select gsc00c,gsc01c,'CZ',gsc03f,gsc04c,gsc05f,gsc06c 
	from [10.0.0.9].S3GOS2016.dbo.gcm10,[10.0.0.9].S3GOS2016.dbo.gsm03  
	where gsc00c=gcl00c and gsc01c=gcl01c and gsc02c='gz' and gcl80d<'20131201'
	go
	insert mcardchangeinfo(changecompid,changebillid,changetype,changedate,changetime,changebeforcardno,changecardfrom,changebeforcardstate,changebeforcardtype,
    memberphone,membername,curaccountkeepamt,curaccountdebtamt,curproaccountkeepamt,curproaccountdebtamt,changelowamt,changefillamt,changdebtamt,
    changeaftercardno,changeaftercardstate,changeaftercardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
    thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
    sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
    ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,
    financedate,operationer,operationdate,salebakflag)
    select gea00c,gea01c,0,gea03d,gea22t,gea04c,'',4,'','','',0,0,0,0,0,gea25f,gea26f,gea07c,4,'',gea21c,gea21cinid,gea27f,
    gea28c,gea28cinid,gea29f,gea30c,gea30cinid,gea31f,gea32c,gea32cinid,gea33f,gea34c,gea34cinid,gea35f,gea36c,gea36cinid,gea37f,gea38c,gea38cinid,gea39f,gea40c,gea40cinid,gea41f,gea42c,gea42cinid,gea43f,gea44c,gea44cinid,gea45f,
    gea80d,gea93c,gea94d,gea99i from [10.0.0.9].S3GOS2016.dbo.gem01 
    where gea80d<'20131201'  and gea02i=4 and isnull(gea22i,0)=0
    go
    insert mcardchangeinfo(changecompid,changebillid,changetype,changedate,changetime,changebeforcardno,changecardfrom,changebeforcardstate,changebeforcardtype,
    memberphone,membername,curaccountkeepamt,curaccountdebtamt,curproaccountkeepamt,curproaccountdebtamt,changelowamt,changefillamt,changdebtamt,
    changeaftercardno,changeaftercardstate,changeaftercardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
    thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
    sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
    ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,
    financedate,operationer,operationdate,salebakflag)
    select gea00c,gea01c,1,gea03d,gea22t,gea04c,'',4,'','','',0,0,0,0,0,gea25f,gea26f,gea07c,4,'',gea21c,gea21cinid,gea27f,
    gea28c,gea28cinid,gea29f,gea30c,gea30cinid,gea31f,gea32c,gea32cinid,gea33f,gea34c,gea34cinid,gea35f,gea36c,gea36cinid,gea37f,gea38c,gea38cinid,gea39f,gea40c,gea40cinid,gea41f,gea42c,gea42cinid,gea43f,gea44c,gea44cinid,gea45f,
    gea80d,gea93c,gea94d,gea99i from [10.0.0.9].S3GOS2016.dbo.gem01 where gea80d <'20131201'  and gea02i=4 and isnull(gea22i,0)=1
    go
    
     insert mcardchangeinfo(changecompid,changebillid,changetype,changedate,changetime,changebeforcardno,changecardfrom,changebeforcardstate,changebeforcardtype,
    memberphone,membername,curaccountkeepamt,curaccountdebtamt,curproaccountkeepamt,curproaccountdebtamt,changelowamt,changefillamt,changdebtamt,
    changeaftercardno,changeaftercardstate,changeaftercardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
    thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
    sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
    ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,
    financedate,operationer,operationdate,salebakflag)
    select gea00c,gea01c,2,gea03d,gea22t,gea04c,'',4,'','','',0,0,0,0,0,gea25f,gea26f,gea07c,4,'',gea21c,gea21cinid,gea27f,
    gea28c,gea28cinid,gea29f,gea30c,gea30cinid,gea31f,gea32c,gea32cinid,gea33f,gea34c,gea34cinid,gea35f,gea36c,gea36cinid,gea37f,gea38c,gea38cinid,gea39f,gea40c,gea40cinid,gea41f,gea42c,gea42cinid,gea43f,gea44c,gea44cinid,gea45f,
    gea80d,gea93c,gea94d,gea99i from [10.0.0.9].S3GOS2016.dbo.gem01 where gea80d  <'20131201'  and gea02i=4 and isnull(gea22i,0)=2
   go 
    
    
    insert mcardchangeinfo(changecompid,changebillid,changetype,changedate,changetime,changebeforcardno,changecardfrom,changebeforcardstate,changebeforcardtype,
    memberphone,membername,curaccountkeepamt,curaccountdebtamt,curproaccountkeepamt,curproaccountdebtamt,changelowamt,changefillamt,changdebtamt,
    changeaftercardno,changeaftercardstate,changeaftercardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
    thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
    sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
    ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,
    financedate,operationer,operationdate,salebakflag)
    select gea00c,gea01c,6,gea03d,gea22t,gea04c,'',4,'','','',0,0,0,0,0,gea25f,gea26f,gea07c,4,'',gea21c,gea21cinid,gea27f,
    gea28c,gea28cinid,gea29f,gea30c,gea30cinid,gea31f,gea32c,gea32cinid,gea33f,gea34c,gea34cinid,gea35f,gea36c,gea36cinid,gea37f,gea38c,gea38cinid,gea39f,gea40c,gea40cinid,gea41f,gea42c,gea42cinid,gea43f,gea44c,gea44cinid,gea45f,
    gea80d,gea93c,gea94d,gea99i from [10.0.0.9].S3GOS2016.dbo.gem01 where gea80d <'20131201'  and gea02i=12 and isnull(gea22i,0)=1
    go
  
    insert mcardchangeinfo(changecompid,changebillid,changetype,changedate,changetime,changebeforcardno,changecardfrom,changebeforcardstate,changebeforcardtype,
    memberphone,membername,curaccountkeepamt,curaccountdebtamt,curproaccountkeepamt,curproaccountdebtamt,changelowamt,changefillamt,changdebtamt,
    changeaftercardno,changeaftercardstate,changeaftercardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
    thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
    sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
    ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,
    financedate,operationer,operationdate,salebakflag)
    select gea00c,gea01c,7,gea03d,gea22t,gea04c,'',4,'','','',0,0,0,0,0,gea25f,gea26f,gea07c,4,'',gea21c,gea21cinid,gea27f,
    gea28c,gea28cinid,gea29f,gea30c,gea30cinid,gea31f,gea32c,gea32cinid,gea33f,gea34c,gea34cinid,gea35f,gea36c,gea36cinid,gea37f,gea38c,gea38cinid,gea39f,gea40c,gea40cinid,gea41f,gea42c,gea42cinid,gea43f,gea44c,gea44cinid,gea45f,
    gea80d,gea93c,gea94d,gea99i from [10.0.0.9].S3GOS2016.dbo.gem01 where gea80d <'20131201'  and gea02i=12 and isnull(gea22i,0)=0
    go
 insert mcardchangeinfo(changecompid,changebillid,changetype,changedate,changetime,changebeforcardno,changecardfrom,changebeforcardstate,changebeforcardtype,
    memberphone,membername,curaccountkeepamt,curaccountdebtamt,curproaccountkeepamt,curproaccountdebtamt,changelowamt,changefillamt,changdebtamt,
    changeaftercardno,changeaftercardstate,changeaftercardtype,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
    thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
    sixthsalerid,sixthsalerinid,sixthsaleamt,seventhsalerid,seventhsalerinid,seventhsaleamt,eighthsalerid,eighthsalerinid,eighthsaleamt,
    ninthsalerid,ninthsalerinid,ninthsaleamt,tenthsalerid,tenthsalerinid,tenthsaleamt,
    financedate,operationer,operationdate,salebakflag)
    select gea00c,gea01c,8,gea03d,gea22t,gea04c,'',4,'','','',0,0,0,0,0,gea25f,gea26f,gea07c,4,'',gea21c,gea21cinid,gea27f,
    gea28c,gea28cinid,gea29f,gea30c,gea30cinid,gea31f,gea32c,gea32cinid,gea33f,gea34c,gea34cinid,gea35f,gea36c,gea36cinid,gea37f,gea38c,gea38cinid,gea39f,gea40c,gea40cinid,gea41f,gea42c,gea42cinid,gea43f,gea44c,gea44cinid,gea45f,
    gea80d,gea93c,gea94d,gea99i from [10.0.0.9].S3GOS2016.dbo.gem01 where gea80d <'20131201'  and gea02i=11
    go
     
	insert dcardchangeinfo(changecompid,changebillid,oldcardno,oldcardtype,oldcardname,curaccountkeepamt,curaccountdebtamt)
	select  ged00c,ged01c,ged02c,ged04c,'',ged06f,ged08f from [10.0.0.9].S3GOS2016.dbo.gem01,[10.0.0.9].S3GOS2016.dbo.gem04
	where gea80d <'20131201' and gea02i=12 and gea00c=ged00c and gea01c=ged01c
go
    insert dpayinfo(paycompid,paybillid,paybilltype,payseqno,paymode,payamt,payremark)
	select gsc00c,gsc01c,'ZK',gsc03f,gsc04c,gsc05f,gsc06c 
	from [10.0.0.9].S3GOS2016.dbo.gem01,[10.0.0.9].S3GOS2016.dbo.gsm03  
	where gsc00c=gea00c and gsc01c=gea01c and gsc02c='ge' and gea80d <'20131201' and gea02i<>11
go
	insert dpayinfo(paycompid,paybillid,paybilltype,payseqno,paymode,payamt,payremark)
	select gsc00c,gsc01c,'TK',gsc03f,gsc04c,gsc05f,gsc06c 
	from [10.0.0.9].S3GOS2016.dbo.gem01,[10.0.0.9].S3GOS2016.dbo.gsm03 
	 where gsc00c=gea00c and gsc01c=gea01c and gsc02c='ge' and gea80d <'20131201' and gea02i=11
	go 
	 insert mcooperatesaleinfo(salecompid,salebillid,saledate,saletime,salecooperid,slaepaymode,salecostproamt,salecostcardno,salecostcardtype,membername,memberphone,
		salebillflag,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
		thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt,fifthsalerid,fifthsalerinid,fifthsaleamt,
		sixthsalerid,sixthsalerinid,sixthsaleamt,financedate,operationer,operationdate)
    select geh00c,geh01c,geh02d,'',geh03c,geh04c,geh05f,geh06c,'',geh07c,geh08c,case when geh12i=0 then 1 else 2 end ,geh13c,geh13cinid,geh14f,geh15c,geh15cinid,geh16f,
    geh17c,geh17cinid,geh18f,geh19c,geh19cinid,geh20f,geh21c,geh21cinid,geh22f,geh23c,geh23cinid,geh24f,geh02d,geh91c,geh92d 
    from [10.0.0.9].S3GOS2016.dbo.gem06 where geh02d <'20131201' 
   go
 


   	insert dpayinfo(paycompid,paybillid,paybilltype,payseqno,paymode,payamt,payremark)
	select geh00c,geh01c,'HZ',0,geh10c,geh11f,geh09c 
	from [10.0.0.9].S3GOS2016.dbo.gem06 where geh02d <'20131201'
	go
	 insert msalebarcodecardinfo(salecompid,salebillid,saledate,saletime,operationer,barcodecardno,firstpaymode,firstpayamt,secondpaymode,secondpayamt,saleamt,
	firstsaleempid,firstsaleempinid,firstsaleamt,secondsaleempid,secondsaleempinid,secondsaleamt,thirdsaleempid,thirdsaleempinid,thirdsaleamt)
   	select fef00c,fef01c,fef06d,fef07t,fef05c,fef08c,fef02c,fef04f,fef03c,fef11f,ISNULL(fef04f,0)+ISNULL(fef11f,0),
   	fef12c,fef12cinid,fef13f,fef14c,fef14cinid,fef15f,fef16c,fef16cinid,fef17f 
   	from [10.0.0.9].S3GOS2016.dbo.fex05 where fef06d <'20131201'
   	go
   	insert dsalebarcodecardinfo(salecompid,salebillid,saleseqno,saleproid,saleprocount,saleproamt,saleremark)
   	select feg00c,feg01c,feg02f,feg03c,feg06f,feg07f,feg08c 
   	from [10.0.0.9].S3GOS2016.dbo.fex05,[10.0.0.9].S3GOS2016.dbo.fex06 where fef06d<'20131201' and fef00c=fef00c and fef01c=feg01c 
   	go
   	
   	insert mproexchangeinfo(changecompid,changebillid,changedate,changecardno,financedate)
	select gcq00c,gcq01c+cast(gcq02f as varchar(3)),gcq07d,gcq01c,gcq07d 
	from [10.0.0.9].S3GOS2016.dbo.gcm23 where gcq07d<'20131201' and gcq01c='AZ000001351'
	go
	
	select * from mproexchangeinfo where changebillid='039AZ0000013511'
update [10.0.0.9].S3GOS2016.dbo.gcm23 set gcq02f=21
 where gcq07d<'20131201' and  gcq01c='AZ000001351' and gcq03c='380052880'
 
 select * from [10.0.0.9].S3GOS2016.dbo.gcm23 where gcq07d<'20131201' and  gcq01c='AZ000001351'
	insert dproexchangeinfo(changecompid,changebillid,changeseqno,changeproid,procount,changeprocount,changeprorate,changeproamt,changebyproaccountamt,changebyaccountamt,
	changepaymode,changebycashamt,nointernalcardno,changebydyqamt,firstsalerid,firstsalerinid,firstsaleamt,secondsalerid,secondsalerinid,secondsaleamt,
	thirdsalerid,thirdsalerinid,thirdsaleamt,fourthsalerid,fourthsalerinid,fourthsaleamt)
	select gcq00c,gcq00c+gcq01c+cast(gcq02f as varchar(3)),gcq02f,gcq03c,1,gcq05f,1, ISNULL(gcq06f,0)+ISNULL(gcq12f,0)+ISNULL(gcq18f,0)+ISNULL(gcq20f,0),
	gcq18f,gcq06f, case when gcq13i=0 then 1 else 6 end,gcq12f,gcq19c,gcq20f,gcq08c,gcq08cinid,gcq09f,gcq10c,gcq10cinid,gcq11f,gcq14c,gcq14cinid,gcq16f,gcq15c,gcq15cinid,gcq17f
	from [10.0.0.9].S3GOS2016.dbo.gcm23 where gcq07d<'20131201'
	go
	
	
    