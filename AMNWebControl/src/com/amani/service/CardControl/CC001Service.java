package com.amani.service.CardControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;

import com.amani.model.Dcardnoinsert;
import com.amani.model.DcardnoinsertId;
import com.amani.model.Mcardnoinsert;
import com.amani.model.McardnoinsertId;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
@Service
public class CC001Service extends AMN_ModuleService{

	@Override
	protected boolean deleteDetail(Object curMaster) {
		// TODO Auto-generated method stub

		return true;
	}

	@Override
	protected boolean deleteMaster(Object curMaster) {
		try
		{
			Mcardnoinsert record=(Mcardnoinsert)curMaster;
			if(record!=null && record.getId()!=null)
			{
				String strSql=" update mcardnoinsert set invalid=1 where cinsertcompid='"+record.getId().getCinsertcompid()+"' and cinsertbillid='"+record.getId().getCinsertbillid()+"' ";
				return this.amn_Dao.deleteBySql(strSql);
			}
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
		
	}

	@Override
	public List loadMasterDataSet(int pageSize, int startRow) {
		return loadMcardnoinserts("","",pageSize,startRow);
	}

	@Override
	protected boolean postDetail(Object details) {
		try
		{
			List<Dcardnoinsert> lsDcardnoinsert=(List<Dcardnoinsert>)details;
			if(lsDcardnoinsert!=null && lsDcardnoinsert.size()>0)
			{
				 this.amn_Dao.saveOrUpdateAll(lsDcardnoinsert);
			}
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	protected boolean postMaster(Object curMaster) {
		this.amn_Dao.saveOrUpdate(curMaster);
		return true;
	}
	
	//获取主档信息
	public List<Mcardnoinsert> loadMcardnoinserts(String strDate,String strBillId,int pageSize, int startRow)
	{
		try
		{
			String strSql="select  mcardnoinsert From Mcardnoinsert mcardnoinsert ,Compchaininfo compchaininfo " +
					" where curcomp='"+CommonTool.getLoginInfo("COMPID")+"' and relationcomp=cinsertcompid " +
					" and (isnull(cinsertdate,'')='"+strDate+"' or '"+strDate+"'='' ) " +
					" and (isnull(cinsertbillid,'')='"+strBillId+"' or '"+strBillId+"'='' ) and isnull(invalid,0)=0 order by cinsertdate desc ";
			return (List<Mcardnoinsert>)this.amn_Dao.findByPage(pageSize, startRow,strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	//获取主档信息
	public Mcardnoinsert loadMcardnoinsertById(String strCompId,String strBillId)
	{
		try
		{
			String strSql="select  cinsertcompid,cinsertbillid,cinsertdate,cinserttime,cinsertware,cinsertper" +
					" ,createcompname,checkoutflag,billflag,billno,checkoutmark,checkoutimgurl,optionconfrimdate,optionconfrimper From  mcardnoinsert " +
					" where cinsertcompid='"+strCompId+"' and cinsertbillid='"+strBillId+"'  ";
			 AnlyResultSet<Mcardnoinsert> analysis = new AnlyResultSet<Mcardnoinsert>()
				{
					public Mcardnoinsert anlyResultSet(ResultSet rs)
					{
						Mcardnoinsert returnValue = new Mcardnoinsert();
						try
						{
							if(rs != null && rs.next()==true)
							{
								returnValue.setId(new McardnoinsertId(CommonTool.FormatString(rs.getString("cinsertcompid")),CommonTool.FormatString(rs.getString("cinsertbillid"))));
								returnValue.setBcinsertcompid(CommonTool.FormatString(rs.getString("cinsertcompid")));
								returnValue.setBcinsertbillid(CommonTool.FormatString(rs.getString("cinsertbillid")));
								returnValue.setCinsertdate(CommonTool.getDateMask(rs.getString("cinsertdate")));
								returnValue.setCinserttime(CommonTool.getTimeMask(rs.getString("cinserttime"),1));
								returnValue.setCinsertware(CommonTool.FormatString(rs.getString("cinsertware")));
								returnValue.setCinsertper(CommonTool.FormatString(rs.getString("cinsertper")));
								returnValue.setCreatecompname(CommonTool.FormatString(rs.getString("createcompname")));
								returnValue.setBillno(CommonTool.FormatString(rs.getString("billno")));
								returnValue.setCheckoutmark(CommonTool.FormatString(rs.getString("checkoutmark")));
								returnValue.setCheckoutimgurl(CommonTool.FormatString(rs.getString("checkoutimgurl")));
								returnValue.setCheckoutflag(CommonTool.FormatInteger(rs.getInt("checkoutflag")));
								returnValue.setBillflag(CommonTool.FormatInteger(rs.getInt("billflag")));
								returnValue.setOptionconfrimdate(CommonTool.getDateMask(rs.getString("optionconfrimdate")));
								returnValue.setOptionconfrimper(CommonTool.FormatString(rs.getString("optionconfrimper")));
							}				
						}
						catch(Exception e)
						{
							e.printStackTrace();
							returnValue =  null;
						}
						return returnValue;
					}
				};
				Mcardnoinsert returnRecord= (Mcardnoinsert)this.amn_Dao.executeQuery_ex(strSql,analysis);
				returnRecord.setBcinsertcompName(this.dataTool.loadCompNameById(CommonTool.FormatString(returnRecord.getBcinsertcompid())));
				StringBuffer validatemsg=new StringBuffer();
				returnRecord.setCinsertperName(this.dataTool.loadEmpNameById(CommonTool.FormatString(returnRecord.getBcinsertcompid()), CommonTool.FormatString(returnRecord.getCinsertper()),validatemsg));
				validatemsg=null;
				return returnRecord;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	//获取明细信息
	 public List<Dcardnoinsert> loadDcardnoinserts(String strCompId,String strBillId)
	 {
		 try
		 {
			 
			 String strModeId=this.dataTool.loadSysParam(strCompId,"SP063");
		     String strFristModeId="";//第一级模板Id
			 String strSecondModeId="";//第2级模板ID
			 String strThirthModeId="";//第三级模板Id
			 //先定位模板门店的连锁级别(暂时支持4级连锁)
			 int compLvl=this.dataTool.loadCompLvl(strCompId);
		     if(compLvl==2)
			 {
				strFristModeId=this.dataTool.loadparentModeId(strModeId);
			 }
			 else if(compLvl==3)
			 {
				strSecondModeId=this.dataTool.loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
			 }
			 else if(compLvl==4)
			 {
				strThirthModeId=this.dataTool.loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=this.dataTool.loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
			 }
			 String strSql=" select cinsertcompid,cinsertbillid,cardtypeid,cardtypename,cardnofrom,cardnoto,cardnum,cardprice,cardamt " +
			 		" From  dcardnoinsert,cardtypeinfo where cinsertcompid='"+strCompId+"' and cinsertbillid='"+strBillId+"' " +
			 		" and cardtypemodeid  in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') and cardtypeno=cardtypeid  ";
			 AnlyResultSet<List<Dcardnoinsert>> analysis = new AnlyResultSet<List<Dcardnoinsert>>()
				{
					public List<Dcardnoinsert> anlyResultSet(ResultSet rs)
					{
						List<Dcardnoinsert> returnValue = new ArrayList();
						Dcardnoinsert record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Dcardnoinsert();
								record.setId(new DcardnoinsertId(CommonTool.FormatString(rs.getString("cinsertcompid")),CommonTool.FormatString(rs.getString("cinsertbillid")),0));
								record.setCardtypeid(CommonTool.FormatString(rs.getString("cardtypeid")));
								record.setCardtypeName(CommonTool.FormatString(rs.getString("cardtypename")));
								record.setCardnofrom(CommonTool.FormatString(rs.getString("cardnofrom")));
								record.setCardnoto(CommonTool.FormatString(rs.getString("cardnoto")));
								record.setCardnum(new BigDecimal(rs.getDouble("cardnum")));
								record.setCardprice(new BigDecimal(rs.getDouble("cardprice")));
								record.setCardamt(new BigDecimal(rs.getDouble("cardamt")));
								returnValue.add(record);
							}				
						}
						catch(Exception e)
						{
							e.printStackTrace();
							returnValue =  null;
						}
						return returnValue;
					}
				};
				return (List<Dcardnoinsert>)this.amn_Dao.executeQuery_ex(strSql,analysis);
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return null;
		 }
	 }
	 
	 //新增主档
	 public Mcardnoinsert addMastRecord()
	 {
		 Mcardnoinsert record=new Mcardnoinsert();
		 record.setId(new McardnoinsertId(CommonTool.getLoginInfo("COMPID"),this.dataTool.loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"mcardnoinsert", "cinsertbillid", "SP012")));
		 record.setCinsertdate(CommonTool.getDateMask(CommonTool.getCurrDate()));
		 record.setCinserttime(CommonTool.getTimeMask(CommonTool.getCurrTime(), 1));
		 record.setBcinsertcompid(CommonTool.getLoginInfo("COMPID"));
		 record.setBcinsertbillid(record.getId().getCinsertbillid());
		 record.setInvalid(0);
		 return record;
	 }
	 
	 /**********验证起始编号***********/
		public boolean validateCardNoFrom(String cardfrom,String cardType,String storeId)
		{

			  //获得系统设置的卡序号长度	
			  int cardIdLength=Integer.parseInt(this.dataTool.loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP019"));
			  //获得系统设置的要过滤的数字
			  String numberOfCardFilter=CommonTool.FormatString(this.dataTool.loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP020"));
			  String headCard=cardfrom.substring(0,cardfrom.length()-cardIdLength);//起始卡卡头
			  int cardHeadLength=0;//卡头长度
			  cardHeadLength=headCard.length();
			  String bodyCard=cardfrom.substring(cardfrom.length()-cardIdLength,cardfrom.length());//卡尾
			  int cardBodyLength=bodyCard.length();//卡尾长度
			  String strSql="select 1 from cardstockchange where changetype='1' and cardtype='"+cardType+"' ";
			  strSql+=" and substring(changecardfromno,1,"+cardHeadLength+")='"+headCard+"' and substring(changecardtono,1,"+cardHeadLength+")='"+headCard+"'";
			  strSql+=" and (len(changecardfromno)-"+cardHeadLength+"="+cardBodyLength+")  and (len(changecardtono)-"+cardHeadLength+"="+cardBodyLength+")";
			  strSql+=" and cast(substring(changecardfromno,len(changecardfromno)-"+(cardIdLength-1)+","+cardIdLength+") as int)<="+Integer.parseInt(bodyCard)+" and cast(substring(changecardtono,len(changecardtono)-"+(cardIdLength-1)+","+cardIdLength+") as int)>="+Integer.parseInt(bodyCard)+"";
			  strSql+=" and isnull(changecardfromno,'')<>'' and isnull(changecardtono,'')<>''"; 

			  AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
			  		{
			  			public Boolean anlyResultSet(ResultSet rs)
			  			{
			  				boolean returnValue = false;
			  				try
			  				{
			  				if(rs != null && rs.next()==true)
			  				{
			  					returnValue =  false;
			  				}
			  				else
			  				{
			  					returnValue =  true;
			  				}
			  				}
			  				catch(Exception e)
			  				{
			  					e.printStackTrace();
			  					returnValue =  false;
			  				}
			  				return returnValue;
			  			}
			  		};
			  		return (Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);
		}
		/**********验证结束编号***********/
		public boolean validateCardNoEnd(String cardend,String cardType,String storeId)
		{
			 //获得系统设置的卡序号长度	
			  int cardIdLength=Integer.parseInt(this.dataTool.loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP019"));
			  //获得系统设置的要过滤的数字
			  String numberOfCardFilter=CommonTool.FormatString(this.dataTool.loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP020"));
			  String headCard=cardend.substring(0,cardend.length()-cardIdLength);//起始卡卡头
			  int cardHeadLength=0;//卡头长度
			  cardHeadLength=headCard.length();
			  String bodyCard=cardend.substring(cardend.length()-cardIdLength,cardend.length());//卡尾
			  int cardBodyLength=bodyCard.length();//卡尾长度
			  String strSql="select 1 from cardstockchange where changetype='1' and cardtype='"+cardType+"' ";
			  strSql+=" and substring(changecardfromno,1,"+cardHeadLength+")='"+headCard+"' and substring(changecardtono,1,"+cardHeadLength+")='"+headCard+"'";
			  strSql+=" and (len(changecardfromno)-"+cardHeadLength+"="+cardBodyLength+")  and (len(changecardtono)-"+cardHeadLength+"="+cardBodyLength+")";
			  strSql+=" and cast(substring(changecardfromno,len(changecardfromno)-"+(cardIdLength-1)+","+cardIdLength+") as int)<="+Integer.parseInt(bodyCard)+" and cast(substring(changecardtono,len(changecardtono)-"+(cardIdLength-1)+","+cardIdLength+") as int)>="+Integer.parseInt(bodyCard)+"";
			  strSql+=" and isnull(changecardfromno,'')<>'' and isnull(changecardtono,'')<>''"; 

			  AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
			  		{
			  			public Boolean anlyResultSet(ResultSet rs)
			  			{
			  				boolean returnValue = false;
			  				try
			  				{
			  				if(rs != null && rs.next()==true)
			  				{
			  					returnValue =  false;
			  				}
			  				else
			  				{
			  					returnValue =  true;
			  				}
			  				}
			  				catch(Exception e)
			  				{
			  					e.printStackTrace();
			  					returnValue =  false;
			  				}
			  				return returnValue;
			  			}
			  		};
			  		return (Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);
		}
		
		/**********验证起始与结束范围是否存在**********/
		public boolean validatecardRange(String cardstart,String cardend,String cardType,String storeId)
		{
			try
			{
				 //获得系统设置的卡序号长度	
				int cardIdLength=Integer.parseInt(this.dataTool.loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP019"));
				String headCard=cardstart.substring(0,cardstart.length()-cardIdLength);//起始卡卡头
				int cardHeadLength=0;//卡头长度
			    cardHeadLength=headCard.length();
				String bodyCard_start=cardstart.substring(cardstart.length()-cardIdLength,cardstart.length());//起始卡卡尾
				String bodyCard_end=cardend.substring(cardend.length()-cardIdLength,cardend.length());//结束卡卡尾
				int cardBodyLength=bodyCard_start.length();//卡尾长度
				String strSql="select 1 from cardstockchange where changetype='1' and cardtype='"+cardType+"' ";
			    strSql+=" and substring(changecardfromno,1,"+cardHeadLength+")='"+headCard+"' and substring(changecardtono,1,"+cardHeadLength+")='"+headCard+"'";
				strSql+=" and (len(changecardfromno)-"+cardHeadLength+"="+cardBodyLength+")  and (len(changecardtono)-"+cardHeadLength+"="+cardBodyLength+")";
			    strSql+=" and cast(substring(changecardfromno,len(changecardfromno)-"+(cardIdLength-1)+","+cardIdLength+") as int)>="+Integer.parseInt(bodyCard_start)+" and cast(substring(changecardtono,len(changecardtono)-"+(cardIdLength-1)+","+cardIdLength+") as int)<="+Integer.parseInt(bodyCard_end)+"";
			    strSql+=" and isnull(changecardfromno,'')<>'' and isnull(changecardtono,'')<>''"; 

			    AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
			    		{
			    			public Boolean anlyResultSet(ResultSet rs)
			    			{
			    				boolean returnValue = false;
			    				try
			    				{
			    				if(rs != null && rs.next()==true)
			    				{
			    					returnValue =  false;
			    				}
			    				else
			    				{
			    					returnValue =  true;
			    				}
			    				}
			    				catch(Exception e)
			    				{
			    					e.printStackTrace();
			    					returnValue =  false;
			    				}
			    				return returnValue;
			    			}
			    		};
			    		return (Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
		}
		
		public boolean postCardIntoStore(String strCompId,String strBillId,int iConfirm)
		{
			String strSql="";
			if(iConfirm==1)
			{
				strSql="insert into cardstock(cardclass,cardfrom,cardto,ccount,storage,compid)  " +
						" select cardtypeid,cardnofrom,cardnoto,cardnum,cinsertware,a.cinsertcompid  " +
						" from mcardnoinsert a,dcardnoinsert b " +
						" where a.cinsertcompid = '"+strCompId+"' and a.cinsertbillid = '"+strBillId+"'  and a.cinsertcompid = b.cinsertcompid   and a.cinsertbillid = b.cinsertbillid ";
				strSql=strSql+"    insert into cardstockchange(changecompid,changetype,changebill,changeseqno,cardtype,changecardfromno,changecardtono,changecount,changeprice,changeamt,changedate,changeware) " +
						"  select b.cinsertcompid,1,b.cinsertbillid,seqno,cardtypeid,cardnofrom,cardnoto,cardnum, cardprice,cardamt,'"+CommonTool.getCurrDate()+"',cinsertware  " +
						"   from mcardnoinsert a,dcardnoinsert b" +
						"  where a.cinsertcompid = b.cinsertcompid   and a.cinsertbillid = b.cinsertbillid and a.cinsertcompid = '"+strCompId+"' and a.cinsertbillid = '"+strBillId+"'   ";
			}
			else
			{
				strSql="delete a from cardstock a,mcardnoinsert c,dcardnoinsert b  " +
						"  where b.cinsertcompid = '"+strCompId+"'  and b.cinsertbillid = '"+strBillId+"'  and a.cardclass = b.cardtypeid  " +
						"   and a.cardfrom = b.cardnofrom  and a.cardto = b.cardnoto  and a.compid = b.cinsertcompid   and a.storage = c.cinsertware " +
						"   and b.cinsertcompid = c.cinsertcompid  and b.cinsertbillid = c.cinsertbillid  ";
				strSql=strSql+"   delete cardstockchange where changecompid='"+strCompId+"' and fdc02c = '"+strBillId+"' and changetype='1'  ";
			}
			try 
			{
				this.amn_Dao.executeSql(strSql);
				return true;
			} 
			catch(Exception ex)
			{
				ex.printStackTrace();
		    	return false;
			}
		}
		
		public boolean handBillImage(Mcardnoinsert curMcardnoinsert)
		{
		    try
		    {
		      this.amn_Dao.update(curMcardnoinsert);
		      curMcardnoinsert = null;
		      return true;
		    }
		    catch (Exception ex)
		    {
		      ex.printStackTrace(); }
		    return false;
		  }
}

