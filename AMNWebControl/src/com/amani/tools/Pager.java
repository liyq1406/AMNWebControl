package com.amani.tools;

import java.util.*;

public class Pager {
	private int totalRows; 
	private int pageSize = 7; 
	private int totalPages = 0;
	private int curPage=1; 
	private int pageStartRow = 0; 
	private boolean hasNextPage = false; 
	private boolean hasPreviousPage = false;
	
	public Pager()
	{
		
	}

	public Pager(int _totalRows)
	{
		this.totalRows = _totalRows;
		this.totalPages = (_totalRows+pageSize-1)/pageSize;
		this.curPage = 1;
		this.pageStartRow = 0;
		
		if(this.curPage == this.totalPages)
			this.hasNextPage = false;
		else
			this.hasNextPage = true;
		
		if(this.curPage == 1)
			this.hasPreviousPage = false;
		else
			this.hasPreviousPage = true;
	}
	
	public Pager(int totalRows,int pageSize)
	{
		this.totalRows = totalRows;
		this.pageSize=pageSize;
		this.totalPages = (totalRows+pageSize-1)/pageSize;
		this.curPage = 1;
		this.pageStartRow = 0;
		
		if(this.curPage == this.totalPages)
			this.hasNextPage = false;
		else
			this.hasNextPage = true;
		
		if(this.curPage == 1)
			this.hasPreviousPage = false;
		else
			this.hasPreviousPage = true;
	} 
	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageStartRow() {
		try{
		    pageStartRow = pageSize*( curPage - 1 ); 
		}catch(Exception ex ){
			pageStartRow = 0;
		}
		return pageStartRow;
	}

	public void setPageStartRow(int pageStartRow) {
//		this.pageStartRow = this.pageSize*(this.curPage - 1); 
		this.pageStartRow = pageStartRow;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	
	public void resetPreviousAndNext()
	{
		if(this.curPage >= this.totalPages)
			this.hasNextPage = false;
		else
			this.hasNextPage = true;
		
		if(this.curPage <= 1)
			this.hasPreviousPage = false;
		else
			this.hasPreviousPage = true;
	}
	
	public void previous()
	{
		if(this.curPage == 1)
			return;
		
		this.curPage--;
		this.pageStartRow = this.pageSize*this.curPage - this.pageSize; 
		
		resetPreviousAndNext();
	}
	
	public void next()
	{
		if(this.curPage == this.totalPages)
			return;
		
		this.curPage++;
		this.pageStartRow = this.pageSize*this.curPage - this.pageSize;
		
		resetPreviousAndNext();
	}
	
	public void first()
	{
		this.curPage = 1;
		this.pageStartRow = 0;
	}
	
	public void last()
	{
		this.curPage = this.totalPages;
		this.pageStartRow = this.pageSize*this.curPage - this.pageSize;
	}
	
	public void refresh(int _curPage)
	{
		if(this.totalPages<_curPage)
			last();	
	}
}
