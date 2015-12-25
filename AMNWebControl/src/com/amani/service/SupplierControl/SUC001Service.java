package com.amani.service.SupplierControl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.model.Goodsinfo;
import com.amani.model.Supplierlink;
import com.amani.model.Suppliermode;
import com.amani.model.Supplierprice;
import com.amani.model.Sysmodeinfo;
import com.amani.service.AMN_ModuleService;

/**
 * 供应商设定
 */
@Service
public class SUC001Service extends AMN_ModuleService{
	
	@SuppressWarnings("unchecked")
	public List<Suppliermode> validate(Suppliermode mode) throws Exception {
		this.amn_Dao.setModel(Suppliermode.class);
		String hql = "from Suppliermode where login=:login and password=:pwd";
		String[] params = new String[]{"login", "pwd"};
		Object[] values = new Object[]{mode.getLogin(), mode.getPassword()};
		return this.amn_Dao.findByParams(hql, params, values);
	}
	
	@SuppressWarnings("unchecked")
	public List<Sysmodeinfo> getModule() throws Exception {
		String hql = "from Sysmodeinfo where upmoduleno='SupplierControl'";
		return this.amn_Dao.findByHql(hql);
	}
	
	public int save(Suppliermode suppliermode){
		if(suppliermode != null){
			this.amn_Dao.update(suppliermode);
			return 1;
		}
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	public List<Supplierlink> loadLinkSet(String no) throws Exception {
		this.amn_Dao.setModel(Supplierlink.class);
		String queryStr = "from Supplierlink where state=1 and supplierno=:no order by ismain desc";
		String[] params = new String[]{"no"};
		Object[] values = new Object[]{no};
		return this.amn_Dao.findByParams(queryStr, params, values);
	}
	
	public int saveLink(List<Supplierlink> supplierlink){
		if(supplierlink != null && supplierlink.size()>0){
			this.amn_Dao.saveOrUpdateAll(supplierlink);
			return 1;
		}
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	public List<Supplierprice> loadPriceSet(String no) throws Exception {
		this.amn_Dao.setModel(Supplierprice.class);
		String queryStr = "from Supplierprice where supplierno=:no";
		String[] params = new String[]{"no"};
		Object[] values = new Object[]{no};
		return this.amn_Dao.findByParams(queryStr, params, values);
	}
	
	@SuppressWarnings("unchecked")
	public List<Goodsinfo> searchGoodsInfo(String no) throws Exception {
		this.amn_Dao.setModel(Goodsinfo.class);
		String queryStr = "from Goodsinfo a where a.id.goodsno=:no";
		String[] params = new String[]{"no"};
		Object[] values = new Object[]{no};
		return this.amn_Dao.findByParams(queryStr, params, values);
	}
	
	public int savePrice(Supplierprice supplierprice){
		if(supplierprice != null){
			this.amn_Dao.save(supplierprice);
			return 1;
		}
		return 0;
	}

	@Override
	protected boolean deleteDetail(Object curMaster) {
		return false;
	}

	@Override
	protected boolean deleteMaster(Object curMaster) {
		return false;
	}

	@Override
	protected boolean postMaster(Object curMaster) {
		return false;
	}

	@Override
	protected boolean postDetail(Object details) {
		return false;
	}

	@Override
	public List<?> loadMasterDataSet(int pageSize, int startRow) {
		return null;
	}
}