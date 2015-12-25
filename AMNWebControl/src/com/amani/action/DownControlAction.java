package com.amani.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.apache.struts2.json.annotations.JSON;


import com.amani.model.A3area;
import com.amani.model.A3city;
import com.amani.model.A3province;
import com.amani.model.Commoninfo;
import com.amani.model.Companyinfo;
import com.amani.model.Compchainstruct;
import com.amani.model.Goodsinfo;
import com.amani.model.Projectinfo;
import com.amani.model.Staffinfo;
import com.amani.model.Sysmodeinfo;
import com.amani.model.Sysrolemode;
import com.amani.model.Useroverall;
import com.amani.service.DowmControlService;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/downControl")
public class DownControlAction  extends AMN_Action{
	@Autowired
	private DowmControlService dowmControlService;
	
	private List<Commoninfo> lsCommonData=new ArrayList();
	private List<Commoninfo> lsCommonDataByGroup=new ArrayList();
	private List<Commoninfo> lsCommonparentDataByGroup=new ArrayList();
	private List<Sysrolemode> lsSysrolemode=new ArrayList();
	private List<Sysmodeinfo> lsSysmodeinfo=new ArrayList();
	private List<Useroverall> lsUseroverall=new ArrayList();
	private String strCurCompId;
	private List<Companyinfo> 		lsCompanyinfos=new ArrayList();
	private List<Compchainstruct>	lsCompchainstructs=new ArrayList();
	private List<Staffinfo> lsStaffinfoDown;
	private List<Projectinfo> lsProjectinfoDown;
	private List<Goodsinfo> lsGoodsinfoDown;
	private List<A3area> a3areas;
	private List<A3city> a3citys;
	private List<A3province> a3provinces;
	private int   commtype; //Ic卡Com口设置
	private int   commprot; //Ic卡Com波特率
    
    /************门店洗剪吹价格*************/
    private BigDecimal dSp074Price;
    private BigDecimal dSp075Price;
    private BigDecimal dSp076Price;
    private BigDecimal dSp077Price;
    private BigDecimal dSp078Price;
    private BigDecimal dSp079Price;
    
    private BigDecimal dSp081Price;
    private BigDecimal dSp082Price;
    private BigDecimal dSp083Price;
    private BigDecimal dSp084Price;
    private BigDecimal dSp085Price;
    private BigDecimal dSp086Price;
    
    
    private BigDecimal dSp087Price;
    private BigDecimal dSp088Price;
    private BigDecimal dSp089Price;
    private BigDecimal dSp090Price;
    private BigDecimal dSp091Price;
    private BigDecimal dSp092Price;
	public String getStrCurCompId() {
		return strCurCompId;
	}


	public void setStrCurCompId(String strCurCompId) {
		this.strCurCompId = strCurCompId;
	}


	public List<Companyinfo> getLsCompanyinfos() {
		return lsCompanyinfos;
	}


	public void setLsCompanyinfos(List<Companyinfo> lsCompanyinfos) {
		this.lsCompanyinfos = lsCompanyinfos;
	}


	public List<Compchainstruct> getLsCompchainstructs() {
		return lsCompchainstructs;
	}


	public void setLsCompchainstructs(List<Compchainstruct> lsCompchainstructs) {
		this.lsCompchainstructs = lsCompchainstructs;
	}


	public List<Commoninfo> getLsCommonData() {
		return lsCommonData;
	}


	public void setLsCommonData(List<Commoninfo> lsCommonData) {
		this.lsCommonData = lsCommonData;
	}


	@Action(value = "loadCommonController",  results = { 
			 @Result(name = "load_success", type = "json"),
            @Result(name = "load_failure", type = "json")	
          }) 
    public  String loadCommonController()
	{
		try
		{
			lsCommonData=this.dowmControlService.loadCommoninfos();
			if(lsCommonData!=null && lsCommonData.size()>0)
			{
				for(int i=0;i<lsCommonData.size();i++)
				{
					lsCommonData.get(i).setBcodekey(lsCommonData.get(i).getId().getCodekey());
					lsCommonData.get(i).setBinfotype(lsCommonData.get(i).getId().getInfotype());
					lsCommonData.get(i).setBparentcodekey(lsCommonData.get(i).getId().getParentcodekey());
				}
				lsCommonparentDataByGroup=this.dowmControlService.loadCommoninfosParentByGroup();
				lsCommonDataByGroup=this.dowmControlService.loadCommoninfosByGroup();
			}
			this.strCurCompId=CommonTool.getLoginInfo("COMPID");
			this.lsCompanyinfos=this.dowmControlService.loadCompanyinfoByCurCompId();
			this.lsCompchainstructs=this.dowmControlService.loadCompchainstructByCurCompId();
			lsStaffinfoDown=this.dowmControlService.getDataTool().loadEmpsByCompId(this.strCurCompId,2);
			if(this.strCurCompId.equals("001"))
				this.lsProjectinfoDown=this.dowmControlService.getDataTool().loadProjectinfoByCompId("0010102",1);
			else
				this.lsProjectinfoDown=this.dowmControlService.getDataTool().loadProjectinfoByCompId(this.strCurCompId,1);
			this.lsGoodsinfoDown=this.dowmControlService.getDataTool().loadGoodsinfoByCompId(this.strCurCompId,0);
			this.a3citys=this.dowmControlService.getDataTool().genGlobalInfoForA3City();
			this.a3areas=this.dowmControlService.getDataTool().genGlobalInfoForA3Area();
			this.a3provinces=this.dowmControlService.getDataTool().genGlobalInfoForA3Province();
			this.dSp074Price=CommonTool.FormatBigDecimal(new BigDecimal(Double.parseDouble(this.dowmControlService.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP074"))));
			this.dSp075Price=CommonTool.FormatBigDecimal(new BigDecimal(Double.parseDouble(this.dowmControlService.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP075"))));
			this.dSp076Price=CommonTool.FormatBigDecimal(new BigDecimal(Double.parseDouble(this.dowmControlService.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP076"))));
			this.dSp077Price=CommonTool.FormatBigDecimal(new BigDecimal(Double.parseDouble(this.dowmControlService.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP077"))));
			this.dSp078Price=CommonTool.FormatBigDecimal(new BigDecimal(Double.parseDouble(this.dowmControlService.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP078"))));
			this.dSp079Price=CommonTool.FormatBigDecimal(new BigDecimal(Double.parseDouble(this.dowmControlService.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP079"))));
			
			this.dSp081Price=CommonTool.FormatBigDecimal(new BigDecimal(Double.parseDouble(this.dowmControlService.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP081"))));
			this.dSp082Price=CommonTool.FormatBigDecimal(new BigDecimal(Double.parseDouble(this.dowmControlService.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP082"))));
			this.dSp083Price=CommonTool.FormatBigDecimal(new BigDecimal(Double.parseDouble(this.dowmControlService.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP083"))));
			this.dSp084Price=CommonTool.FormatBigDecimal(new BigDecimal(Double.parseDouble(this.dowmControlService.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP084"))));
			this.dSp085Price=CommonTool.FormatBigDecimal(new BigDecimal(Double.parseDouble(this.dowmControlService.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP085"))));
			this.dSp086Price=CommonTool.FormatBigDecimal(new BigDecimal(Double.parseDouble(this.dowmControlService.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP086"))));
			
			
			this.dSp087Price=CommonTool.FormatBigDecimal(new BigDecimal(Double.parseDouble(this.dowmControlService.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP087"))));
			this.dSp088Price=CommonTool.FormatBigDecimal(new BigDecimal(Double.parseDouble(this.dowmControlService.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP088"))));
			this.dSp089Price=CommonTool.FormatBigDecimal(new BigDecimal(Double.parseDouble(this.dowmControlService.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP089"))));
			this.dSp090Price=CommonTool.FormatBigDecimal(new BigDecimal(Double.parseDouble(this.dowmControlService.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP090"))));
			this.dSp091Price=CommonTool.FormatBigDecimal(new BigDecimal(Double.parseDouble(this.dowmControlService.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP091"))));
			this.dSp092Price=CommonTool.FormatBigDecimal(new BigDecimal(Double.parseDouble(this.dowmControlService.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP092"))));
			this.commtype=Integer.parseInt(this.dowmControlService.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP080"));
			this.commprot=Integer.parseInt(this.dowmControlService.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP093"));
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}

	@Action(value = "loadSysrolemodeController",  results = { 
			 @Result(name = "load_success", type = "json"),
           @Result(name = "load_failure", type = "json")	
         }) 
   public  String loadSysrolemodeController()
	{
		try
		{
			lsSysrolemode=this.dowmControlService.loadSysrolemodes();
			lsUseroverall=this.dowmControlService.loadUseroveralls();
			lsSysmodeinfo=this.dowmControlService.loadSysmodeinfos();
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	@JSON(serialize=false)
	public DowmControlService getDowmControlService() {
		return dowmControlService;
	}

	@JSON(serialize=false)
	public void setDowmControlService(DowmControlService dowmControlService) {
		this.dowmControlService = dowmControlService;
	}


	public List<Sysrolemode> getLsSysrolemode() {
		return lsSysrolemode;
	}


	public void setLsSysrolemode(List<Sysrolemode> lsSysrolemode) {
		this.lsSysrolemode = lsSysrolemode;
	}


	public List<Commoninfo> getLsCommonDataByGroup() {
		return lsCommonDataByGroup;
	}


	public void setLsCommonDataByGroup(List<Commoninfo> lsCommonDataByGroup) {
		this.lsCommonDataByGroup = lsCommonDataByGroup;
	}


	public List<Commoninfo> getLsCommonparentDataByGroup() {
		return lsCommonparentDataByGroup;
	}


	public void setLsCommonparentDataByGroup(
			List<Commoninfo> lsCommonparentDataByGroup) {
		this.lsCommonparentDataByGroup = lsCommonparentDataByGroup;
	}


	public List<Useroverall> getLsUseroverall() {
		return lsUseroverall;
	}


	public void setLsUseroverall(List<Useroverall> lsUseroverall) {
		this.lsUseroverall = lsUseroverall;
	}


	public List<Sysmodeinfo> getLsSysmodeinfo() {
		return lsSysmodeinfo;
	}


	public void setLsSysmodeinfo(List<Sysmodeinfo> lsSysmodeinfo) {
		this.lsSysmodeinfo = lsSysmodeinfo;
	}


	public List<Staffinfo> getLsStaffinfoDown() {
		return lsStaffinfoDown;
	}


	public void setLsStaffinfoDown(List<Staffinfo> lsStaffinfoDown) {
		this.lsStaffinfoDown = lsStaffinfoDown;
	}


	public List<Projectinfo> getLsProjectinfoDown() {
		return lsProjectinfoDown;
	}


	public void setLsProjectinfoDown(List<Projectinfo> lsProjectinfoDown) {
		this.lsProjectinfoDown = lsProjectinfoDown;
	}


	public List<Goodsinfo> getLsGoodsinfoDown() {
		return lsGoodsinfoDown;
	}


	public void setLsGoodsinfoDown(List<Goodsinfo> lsGoodsinfoDown) {
		this.lsGoodsinfoDown = lsGoodsinfoDown;
	}


	public List<A3area> getA3areas() {
		return a3areas;
	}


	public void setA3areas(List<A3area> a3areas) {
		this.a3areas = a3areas;
	}


	public List<A3city> getA3citys() {
		return a3citys;
	}


	public void setA3citys(List<A3city> a3citys) {
		this.a3citys = a3citys;
	}


	public List<A3province> getA3provinces() {
		return a3provinces;
	}


	public void setA3provinces(List<A3province> a3provinces) {
		this.a3provinces = a3provinces;
	}


	public BigDecimal getDSp074Price() {
		return dSp074Price;
	}


	public void setDSp074Price(BigDecimal sp074Price) {
		dSp074Price = sp074Price;
	}


	public BigDecimal getDSp075Price() {
		return dSp075Price;
	}


	public void setDSp075Price(BigDecimal sp075Price) {
		dSp075Price = sp075Price;
	}


	public BigDecimal getDSp076Price() {
		return dSp076Price;
	}


	public void setDSp076Price(BigDecimal sp076Price) {
		dSp076Price = sp076Price;
	}


	public BigDecimal getDSp077Price() {
		return dSp077Price;
	}


	public void setDSp077Price(BigDecimal sp077Price) {
		dSp077Price = sp077Price;
	}


	public BigDecimal getDSp078Price() {
		return dSp078Price;
	}


	public void setDSp078Price(BigDecimal sp078Price) {
		dSp078Price = sp078Price;
	}


	public BigDecimal getDSp079Price() {
		return dSp079Price;
	}


	public void setDSp079Price(BigDecimal sp079Price) {
		dSp079Price = sp079Price;
	}


	public int getCommtype() {
		return commtype;
	}


	public void setCommtype(int commtype) {
		this.commtype = commtype;
	}


	public BigDecimal getDSp081Price() {
		return dSp081Price;
	}


	public void setDSp081Price(BigDecimal sp081Price) {
		dSp081Price = sp081Price;
	}


	public BigDecimal getDSp082Price() {
		return dSp082Price;
	}


	public void setDSp082Price(BigDecimal sp082Price) {
		dSp082Price = sp082Price;
	}


	public BigDecimal getDSp083Price() {
		return dSp083Price;
	}


	public void setDSp083Price(BigDecimal sp083Price) {
		dSp083Price = sp083Price;
	}


	public BigDecimal getDSp084Price() {
		return dSp084Price;
	}


	public void setDSp084Price(BigDecimal sp084Price) {
		dSp084Price = sp084Price;
	}


	public BigDecimal getDSp085Price() {
		return dSp085Price;
	}


	public void setDSp085Price(BigDecimal sp085Price) {
		dSp085Price = sp085Price;
	}


	public BigDecimal getDSp086Price() {
		return dSp086Price;
	}


	public void setDSp086Price(BigDecimal sp086Price) {
		dSp086Price = sp086Price;
	}


	public BigDecimal getDSp087Price() {
		return dSp087Price;
	}


	public void setDSp087Price(BigDecimal sp087Price) {
		dSp087Price = sp087Price;
	}


	public BigDecimal getDSp088Price() {
		return dSp088Price;
	}


	public void setDSp088Price(BigDecimal sp088Price) {
		dSp088Price = sp088Price;
	}


	public BigDecimal getDSp089Price() {
		return dSp089Price;
	}


	public void setDSp089Price(BigDecimal sp089Price) {
		dSp089Price = sp089Price;
	}


	public BigDecimal getDSp090Price() {
		return dSp090Price;
	}


	public void setDSp090Price(BigDecimal sp090Price) {
		dSp090Price = sp090Price;
	}


	public BigDecimal getDSp091Price() {
		return dSp091Price;
	}


	public void setDSp091Price(BigDecimal sp091Price) {
		dSp091Price = sp091Price;
	}


	public BigDecimal getDSp092Price() {
		return dSp092Price;
	}


	public void setDSp092Price(BigDecimal sp092Price) {
		dSp092Price = sp092Price;
	}


	public int getCommprot() {
		return commprot;
	}


	public void setCommprot(int commprot) {
		this.commprot = commprot;
	}


}
