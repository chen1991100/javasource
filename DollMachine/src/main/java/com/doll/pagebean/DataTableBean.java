package com.doll.pagebean;

import java.util.List;

public class DataTableBean {
	  private int iTotalRecords;
	  private int iTotalDisplayRecords;
	  private String sEcho;
	  private List<?> aaData;

	  public int getiTotalRecords()
	  {
	    return this.iTotalRecords;
	  }
	  public void setiTotalRecords(int iTotalRecords) {
	    this.iTotalRecords = iTotalRecords;
	  }
	  public int getiTotalDisplayRecords() {
	    return this.iTotalDisplayRecords;
	  }
	  public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
	    this.iTotalDisplayRecords = iTotalDisplayRecords;
	  }
	  public String getsEcho() {
	    return this.sEcho;
	  }
	  public void setsEcho(String sEcho) {
	    this.sEcho = sEcho;
	  }
	  public List<?> getAaData() {
	    return this.aaData;
	  }
	  public void setAaData(List<?> aaData) {
	    this.aaData = aaData;
	  }
}
