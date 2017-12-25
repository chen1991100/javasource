<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String today = DateTimeUtil.getDate2Str();		
	String timeFrame = today.substring(0,8)+"01至"+today;	
	AdminUser admin = (AdminUser)request.getAttribute("adminInfo");
%>

<div class="data-div">
		<div class="widget-header bordered-bottom bordered-themeprimary">
                <i class="widget-icon fa fa-tasks themeprimary"></i>
                <span class="widget-caption themeprimary">用户注册列表</span> 
                <form  role="form" onsubmit="return false;" id="form_data_table_list"> 
                	 <div class="widget-buttons">
                		  用户名称：<input type="text" name="barName" style="width:120px;height:25px;margin-right:10px;padding: 3px;">
			         </div>     
			         <div class="widget-buttons">	  			
						<button href="javascript:;" class="btn btn-blue btn-xs search"  data-title="查询" data-form="true" onclick="">查询</button>								
					 </div>
					 <div class="widget-buttons buttons-bordered">
						<button href="bar/add_page" class="btn btn-palegreen btn-xs" data-handler="MODAL" modal-size="800px" data-title="开通网吧账号">开通网吧账号</button>
					</div>
           </form>
		</div>
         
		<div class="widget no-header ">
			<div class="widget-body">
				<table class="table table-condensed table-bordered table-hover " id="data_table_list">
				</table>
				
			</div>
		</div>
</div>

			
    

<script>
$(document).ready(function() {

 Select2Ex.handleForm4select2({target:$("#form_data_table_list")});
   
  TableAjax.init('data_table_list',{
	 sAjaxSource: "user/list",
	 aaSorting: [[1, "desc" ]], // 需要自定义
	 uxDataTitle:"null",
	 uxCheckable:false,
	 uxCloseScroll:true,
	 uxDrapable:false,
	 iDisplayLength:20,
	 aoColumns: [		
	 		{sTitle:'ID',  mData: "id"},
			{sTitle:'名称',  mData: "barName"},	
			{sTitle:'网吧类型',  mData: "uniAgentId",mRender:function(data,d,all){
				if(all.uniAgentId==all.uniParentAgentId){
					return '<font color="red">直管网吧</font>';
				}else{
					return '<font color="green">市级代理管理</font>';
				}
			}},
			{sTitle:'地区',  mData: "regionCode",mRender:function(data,d,all){
				return all.regionProvince+'-'+all.regionCity;
			}},
			{sTitle:'联系人',  mData: "contacts"},
			{sTitle:'联系人电话',  mData: "contactsPhone"},
			{sTitle:'添加时间',  mData: "insertTime",mRender:function(data,d,all){
				return all.insertTime+'';
			}},
			{sTitle:'开通业务情况',  mData: "id",mRender:function(data,d,all){
				var qk = '';
				if(all.isSpeed){
					qk += ' 开通迅游加速';
				}else{
					qk += '-';
				}
				if(all.isLol){
					qk += ' 开通LOL特权';
				}else{
					qk += ' -';
				}
				if(all.isShareSteam){
					qk += ' 开通steam共享账号';
				}else{
					qk += ' -';
				}
				return qk;
			}},
			{sTitle:'操作',  mData: "id", mRender:function(data,d,all){ 
				var html1='';
				if(aid==all.uniAgentId){
					html1= '<a class="blue-stripes" style="margin:20px" data-title="开通业务" data-handler="MODAL" href="bar/active_service_page?bid='+all.id+'" modal-size="820px">开通业务</a> | ';
				}
				var htm = '<a class="blue-stripes" style="margin:20px" data-title="详情" data-handler="MODAL" href="bar/detail?id='+all.id+'" modal-size="820px">详情</a> | '+
					   '<a class="blue-stripes" style="margin:20px" data-title="修改" data-handler="MODAL" href="bar/mod_page?id='+all.id+'" modal-size="820px">修改</a> | '+
					   '<a style="margin:20px" data-handler="CONFIRM"  href="bar/del?id='+all.id+'">删除</a>';
				return htm;
			}	 		  		    
			}
        ]
 	});
 	
  });
  
  
</script>