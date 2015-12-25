package com.amani.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AMN_ModuleAction;
import com.amani.dao.AMN_DaoImp;
import com.amani.tools.DataTool;
import com.amani.tools.CommonTool;

@Service
public class KeepCurrSessionService extends AMN_ModuleAction
{

	@Override
	protected boolean addActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean afterAdd() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean afterDelete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean afterLoad() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean afterPost() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean beforeAdd() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean beforeDelete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean beforeLoad() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean beforePost() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean deleteActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean loadActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean postActive() {
		// TODO Auto-generated method stub
		return false;
	}


}
