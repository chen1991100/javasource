<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

			
              <li class="active open">
                        <a href="javascript:;" class="menu-dropdown "   data-container="body" data-placement="right" data-original-title=""  >
                            <i class="menu-icon glyphicon glyphicon-user"></i>
                            	<span class="menu-text">用户信息管理</span>
                            <i class="menu-expand"></i>
                        </a>

                        <ul class="submenu">
                         	<li class=" ">
                                <a class="ajaxify " href="user/list"  url="user/list"  external="false" data-container="body" data-placement="right" data-original-title="null">
                                    <span class="menu-text">用户列表</span>
                                </a>
                            </li>
                             <li class=" ">
                                <a class="ajaxify " href="user/zhua"  url="user/zhua"  external="false" data-container="body" data-placement="right" data-original-title="null">
                                    <span class="menu-text">用户抓取记录</span>
                                </a>
                            </li>
                        	
                        </ul>
              </li>
              
              <li class="active open">
                        <a href="javascript:;" class="menu-dropdown "   data-container="body" data-placement="right" data-original-title=""  >
                            <i class="menu-icon glyphicon glyphicon-phone"></i>
                            	<span class="menu-text">机器管理</span>
                            <i class="menu-expand"></i>
                        </a>

                        <ul class="submenu">
                           <li class=" ">
                                <a class="ajaxify " href="steam_account/bar_uni_steam_list_page"  url="steam_account/bar_uni_steam_list_page"  external="false" data-container="body" data-placement="right" data-original-title="null">
                                    <span class="menu-text">机器列表</span>
                                </a>
                            </li>
                        </ul>
              </li>