alter procedure upg_handaccountinser_card  
@compid			varchar(10),	--兑换公司        
@cardno			varchar(20),	--兑换卡号
@changedate		varchar(10),	--兑换时间
@changeemp		varchar(20),	--兑换人员
@changeamt		float		--兑换金额
as        
begin   

 --新增账户历史  
  declare @costaccountseqno  float  
  declare @curcardamt    float  
  select @costaccountseqno=MAX(changeseqno)+1 from cardaccountchangehistory where changecardno=@cardno   
  if(ISNULL(@changeamt,0)>0)--抵用账户  
  begin  
   set @curcardamt=0  
   select top 1 @curcardamt=(case when changetype in (0,6,7,8,9,10,11) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@cardno  and changeaccounttype=2 order by chagedate desc,changeseqno desc   
    
   --新增账户历史   
   insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)  
   values(@compid,@cardno,2,isnull(@costaccountseqno,0),12,@changeamt*(-1),'DH',@changeemp,@changedate,@curcardamt)  
   set @costaccountseqno=@costaccountseqno+1  
  
   set @curcardamt=0  
   select top 1 @curcardamt=(case when changetype in (0,6,7,8,9,10,11) then ISNULL(changebeforeamt,0)+isnull(changeamt,0) else ISNULL(changebeforeamt,0)-isnull(changeamt,0) end ) from cardaccountchangehistory where changecardno=@cardno  and changeaccounttype=4 order by chagedate desc,changeseqno desc   
    
   insert cardaccountchangehistory(changecompid,changecardno,changeaccounttype,changeseqno,changetype,changeamt,changebilltype,changebillno,chagedate,changebeforeamt)  
   values(@compid,@cardno,4,isnull(@costaccountseqno,0),13,@changeamt,'DH',@changeemp,@changedate,@curcardamt)  
  end  

end   