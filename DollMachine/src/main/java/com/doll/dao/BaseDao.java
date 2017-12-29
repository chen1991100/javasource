package com.doll.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.doll.utils.Page;


public interface BaseDao<T, PK extends Serializable> {

	/**
	 * 根据id 获取实体对象
	 * 
	 * @param id
	 *            记录ID
	 * @return 实体对象
	 */

	public T get(PK id);

	/**
	 * 根据id 获取实体对象
	 * 
	 * @param id
	 *            记录ID
	 * @return 实体对象
	 */
	public T load(PK id);

	/**
	 * 根据 ID数组 获取 实体对象集合
	 * 
	 * @param ids
	 *            id对象数组
	 * @return 实体对象集合
	 */
	public List<T> get(PK[] ids);

	/**
	 * 获取所有实体对象集合
	 * 
	 * @return
	 */
	public List<T> getAll();

	/**
	 * 获取实体对象总数
	 * 
	 * @return
	 */

	public Long getTotalCount();

	/**
	 * 保存实体对象
	 * 
	 * @param entity
	 * @return
	 */
	public PK save(T entity);

	/**
	 * 更新实体对象
	 * 
	 * @param entity
	 */
	public void update(T entity);

	/**
	 * 删除实体对象
	 * 
	 * @param entity
	 */
	public void delete(T entity);

	/**
	 * 根据id删除实体对象
	 * 
	 * @param id
	 */
	public void delete(PK id);

	/**
	 * 根据id数据删除实体对象
	 * 
	 * @param ids
	 */
	public void delete(PK[] ids);

	/**
	 * 刷新session
	 */
	public void flush();

	/**
	 * 清除session
	 */
	public void clear();

	/**
	 * 清除session中某一对象
	 * 
	 * @param object
	 */
	public void evict(Object object);

	/**
	 * 根据指定hql返回list
	 * 
	 * @return
	 */
	List<T> getHqList(String hql, String... param);

	/**
	 * 根据条件得到对象集合
	 * 
	 * @param hql
	 * @param parameters
	 * @return
	 */
	List<T> getData(String hql, String... parameters);

	/**
	 * 查询最大条数
	 * 
	 * @param hql
	 * @param max
	 * @param parameters
	 * @return
	 */
	List<T> getMaxData(String hql, int max, String... parameters);
	
	/**
	 * 得到一条唯一数据
	 * @param hql
	 * @param parameters
	 * @return
	 */
	T getHqlObj(String hql, String... parameters);
	
	/**
	 * 得到一条唯一数据(加锁)
	 * @param hql
	 * @param parameters
	 * @return
	 */
	T getHqlObjForUpdate(String hql, String... parameters);

	/**
	 * 根据传来的分页参数查询实体集合
	 * 
	 * @param hql
	 *            自己拼凑的Hql语句
	 * @param page
	 *            分装的分页参数
	 * @param parameters
	 * @return 返回当前分页的实体集合
	 */
	List<T> getPageAll(String hql, Page page, String... parameters);

	/**
	 * 分页查询
	 * 
	 * @param hql
	 * @param pageNew
	 * @param pageSize
	 * @param parameters
	 * @return
	 */
	List<Object[]> paging(String hql, int pageNew, int pageSize, String... parameters);

	/**
	 * 统计条数
	 * @param sql
	 * @return
	 */
	Integer getCount(String sql, String... parameters);
	
	/**
	 * 执行带输出参数存储过程(in and out)
	 * @param callSql
	 * @param inParameters
	 * @return
	 */
	public int prepareCallAndReturn(final String callSql,final Object... inParameters);
	
	/**
	 * 执行无参数返回存储过程(in)
	 * @param callSql
	 * @param inParameters
	 */
	public void prepareCallNoReturn(final String callSql,final Object... inParameters);
	/**
	 * 执行sql语句无结果集
	 * @param sql
	 * @param params
	 * @return
	 */
	public int executeSql(String sql, Object... params);
	
	/**
	 * 执行hql语句无结果集
	 * @param sql
	 * @param params
	 * @return
	 */
	public int updateHql(String hql, String... params);
	
	public Object getObject(String sql, Object... params);
	
	public Object getObject2List(String sql, Object... params);

	public Map<String, Object> getObjectToMap(String sql, Object[] parameters);
	
}
