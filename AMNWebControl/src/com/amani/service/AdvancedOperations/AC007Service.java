package com.amani.service.AdvancedOperations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.xwork.ObjectUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.amani.model.WxCard;
import com.amani.model.WxProduct;
import com.amani.model.WxProductDetail;
import com.amani.model.WxProductPackage;
import com.amani.service.AMN_ReportService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 微信
 */
@Service
public class AC007Service extends AMN_ReportService{
	
	@SuppressWarnings("unchecked")
	public List<Object> loadInitData() throws Exception {
		List<Object> result = new ArrayList<Object>();
		/*this.amn_Dao.setModel(WxProductGroup.class);
		List<WxProductGroup> group= this.amn_Dao.findAll();
		result.add(group);*/
		List<WxCard> card = this.amn_Dao.findByHql("from WxCard where state=1");
		result.add(card);
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<WxProduct> loadDataSet(WxProduct wxProduct) {
		StringBuffer hql = new StringBuffer();
		hql.append("from WxProduct where 1=1");
		int catgory = wxProduct.getCatgory();
		if(catgory!=-1){
			hql.append(" and catgory="+ catgory);
		}
		int type = wxProduct.getType();
		if(type!=-1){
			hql.append(" and type="+ type);
		}
		/*int group = wxProduct.getGroup();
		if(group!=-1){
			hql.append(" and group="+ group);
		}*/
		String card_id = wxProduct.getCard_id();
		if(!StringUtils.equals("-1", card_id)){
			hql.append(" and card_id='"+ card_id +"'");
		}
		String id = ObjectUtils.toString(wxProduct.getId());
		if(StringUtils.isNotBlank(id)){
			hql.append(" and id="+ id);
		}
		String name = wxProduct.getName();
		if(StringUtils.isNotBlank(name)){
			hql.append(" and name like '%"+ name +"%'");
		}
		List<WxProduct> list= this.amn_Dao.findByHql(hql.toString());
		return list;
	}
	
	public List<WxProductPackage> loadPackage(int id){
		List<WxProductPackage> wxPackages = new ArrayList<WxProductPackage>();
		String sql ="select a.*,b.name from wx_product_package a, wx_product b where a.productid=b.cid and a.parentId="+id;
		ResultSet rs = this.amn_Dao.executeQuery(sql);
		try {
			while (rs.next()) {
				WxProductPackage pack = new WxProductPackage();
				pack.setId(rs.getInt("id"));
				pack.setParentId(rs.getInt("parentId"));
				pack.setProductid(rs.getInt("productid"));
				pack.setCount(rs.getInt("count"));
				pack.setName(rs.getString("name"));
				wxPackages.add(pack);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return wxPackages;
	}
	
	@SuppressWarnings("unchecked")
	public WxProduct checkProject(int cid){
		this.amn_Dao.setModel(WxProduct.class);
		String hql="from WxProduct where isopen=1 and type in(1,2) and cid=:pid";
		String[] params = new String[]{"pid"};
		Object[] values = new Object[]{cid};
		List<WxProduct> list = this.amn_Dao.findByParams(hql, params, values);
		return list==null || list.size()==0 ? null:list.get(0);
	}
	
	public boolean checkPackageNo(int cid){
		String sql="select count(*) from wx_product where type=3 and cid="+ cid;
		return this.amn_Dao.getRowsCount_Ex(sql)>0;
	}

	/**
	 * 新增或编辑
	 * @param product
	 */
	public void post(WxProduct product) throws DataAccessException,RuntimeException {
		Integer id = product.getId();
		if(id==null){//新增时使用
			WxProduct temp = new WxProduct();
			temp.setCid(product.getCid());
			temp.setName(product.getName());
			id = (Integer) amn_Dao.saveByKey(temp);
			product.setId(id);
		}
		String name = product.getMainimagename();
		if(StringUtils.isNotBlank(name)){
			product.setMainimageurl(uploadImage(id+name, product.getMainimageurl()));
		}
		amn_Dao.saveOrUpdate(product);
		String json = product.getStrJson();
		if(StringUtils.isNotBlank(json)){
			Gson gson = new Gson();
			if(product.getType()==3){//套餐
				List<WxProductPackage> list = gson.fromJson(json, new TypeToken<List<WxProductPackage>>(){}.getType());
				for (WxProductPackage wxProductPackage : list) {
					wxProductPackage.setParentId(id);
				}
				amn_Dao.saveOrUpdateAll(list);
			}else{//步骤
				List<WxProductDetail> list = gson.fromJson(json, new TypeToken<List<WxProductDetail>>(){}.getType());
				for (WxProductDetail wxProductDetail : list) {
					wxProductDetail.setParentId(id);
					String imageName = wxProductDetail.getImagename();
					if(StringUtils.isNotBlank(imageName)){
						wxProductDetail.setImageurl(uploadImage(id+imageName, wxProductDetail.getImageurl()));
					}
				}
				amn_Dao.saveOrUpdateAll(list);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<WxProductDetail> loadStep(int parentId) throws Exception{
		String hql = "from WxProductDetail where parentId=:pId";
		this.amn_Dao.setModel(WxProductDetail.class);
		String[] params = new String[]{"pId"};
		Object[] values = new Object[]{parentId};
		return amn_Dao.findByParams(hql, params, values);
	}
	
	//上传图片
	public static String uploadImage(String imageName, String base64Img){
		String url = "";
		try {
			if(StringUtils.isBlank(base64Img)){
				return "";
			}
			String base64Str = StringUtils.substring(base64Img, base64Img.indexOf(",")+1);
			// webservice路径  
	    	// 这里后面加不加 "?wsdl" 效果都一样的  
	    	String endpoint = "http://222.73.31.3:7009/AMNCommonService/services/ImageCloudService";  
	    	// 上传图片接口方法名  
	    	String operationName = "uploadFile";  
	    	String targetNamespace = "http://services.amani.com/";  
	    	// 定义service对象  
	    	org.apache.axis.client.Service service = new org.apache.axis.client.Service();  
	    	// 创建一个call对象  
	    	Call call = (Call) service.createCall();  
	    	// 设置目标地址，即webservice路径  
	    	call.setTargetEndpointAddress(endpoint);  
	    	// 设置操作名称，即方法名称  
	    	call.setOperationName(new QName(targetNamespace, operationName));  
	    	// 设置方法参数  
	    	call.addParameter(new QName(targetNamespace, "file"), XMLType.SOAP_BASE64BINARY, ParameterMode.IN); 
	    	call.addParameter(new QName(targetNamespace, "bucket"), XMLType.XSD_STRING, ParameterMode.IN); 
	    	call.addParameter(new QName(targetNamespace, "key"), XMLType.XSD_STRING, ParameterMode.IN); 
	    	call.setReturnClass(String.class);  
	    	// 解决错误：服务器未能识别 HTTP 头 SOAPAction 的值  
	    	call.setUseSOAPAction(true);  
	    	call.setSOAPActionURI(targetNamespace + operationName);
	    	Object obj = call.invoke(new Object[]{Base64.decodeBase64(base64Str), "amani", imageName});
	    	String result = ObjectUtils.toString(obj);
	    	if(StringUtils.isNotBlank(result)){
	    		Gson gson = new Gson();
	    		Map<String, String> map = gson.fromJson(result, new TypeToken<Map<String, String>>(){}.getType());
	    		url = map.get("url");
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
}