package com.doll.pagebean;

import java.util.HashMap;

public class ParamListBean {
	  private int pageNo;
	  private int pageLength;
	  private String sEcho;
	  private String fieldName;
	  private String compositorType;
	  private HashMap<String, String> param;

	  public int getPageNo()
	  {
	    return this.pageNo;
	  }
	  public void setPageNo(int pageNo) {
	    this.pageNo = pageNo;
	  }
	  public int getPageLength() {
	    return this.pageLength;
	  }
	  public void setPageLength(int pageLength) {
	    this.pageLength = pageLength;
	  }
	  public String getsEcho() {
	    return this.sEcho;
	  }
	  public void setsEcho(String sEcho) {
	    this.sEcho = sEcho;
	  }
	  public String getFieldName() {
	    return this.fieldName;
	  }
	  public void setFieldName(String fieldName) {
	    this.fieldName = fieldName;
	  }
	  public String getCompositorType() {
	    return this.compositorType;
	  }
	  public void setCompositorType(String compositorType) {
	    this.compositorType = compositorType;
	  }
	  public HashMap<String, String> getParam() {
	    return this.param;
	  }
	  public void setParam(HashMap<String, String> param) {
	    this.param = param;
	  }
}
