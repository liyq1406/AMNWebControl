

  declare @tocompid varchar(10)  
  declare cur_each_comp cursor for  
  select curcompno from compchainstruct where complevel=4
  open cur_each_comp  
  fetch cur_each_comp into @tocompid  
  while @@fetch_status = 0  
  begin  
   exec upg_analysis_system_shop_rijie @tocompid,'201301'
   exec upg_analysis_system_shop_rijie @tocompid,'201302'
   exec upg_analysis_system_shop_rijie @tocompid,'201303'
   exec upg_analysis_system_shop_rijie @tocompid,'201304'
   exec upg_analysis_system_shop_rijie @tocompid,'201305'
   exec upg_analysis_system_shop_rijie @tocompid,'201306'
   exec upg_analysis_system_shop_rijie @tocompid,'201307'
   exec upg_analysis_system_shop_rijie @tocompid,'201308'
   exec upg_analysis_system_shop_rijie @tocompid,'201309'
   exec upg_analysis_system_shop_rijie @tocompid,'201310'
   exec upg_analysis_system_shop_rijie @tocompid,'201311'
   exec upg_analysis_system_shop_rijie @tocompid,'201312'
   fetch cur_each_comp into @tocompid  
  end  
  close cur_each_comp  
  deallocate cur_each_comp             