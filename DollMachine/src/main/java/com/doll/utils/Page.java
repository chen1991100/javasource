package com.doll.utils;

/**
 * @author 陈强
 *	分页分装类
 */
public class Page {
	/**
	 * 默认每页显示多少条
	 */
	private static final int DEFAULT_PAGE_SIZE=5;
	
	/**
	 * 总记录条数
	 */
	private long totalCount =0;

	
	/**
	 * 默认当前页 
	 */
	private int currentPage=1;
	
	/**
	 *  每页显示多少条
	 */
	private int numPerPage=DEFAULT_PAGE_SIZE;
	
	/**
	 * 总页数
	 */
	private int totalPage=10;
	
	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		 if(currentPage>totalPage){
			this.currentPage =totalPage;
		 }
		 if(currentPage<1){
			 this.currentPage =1;
		 }
		 if(currentPage>1&&currentPage<=totalPage){
			 this.currentPage =currentPage;
		 }
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage =(int)(totalCount%numPerPage==0 ? totalCount/numPerPage  : (totalCount/numPerPage)+1);
	}
	
	
}
