package com.doll.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;

import com.doll.dao.BaseDao;
import com.doll.utils.Page;


public class BaseDaoImpl<T, PK extends Serializable> implements BaseDao<T, PK> {

	private Class<T> entityClass;

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		Type type = this.getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			Type[] parameterizedType = ((ParameterizedType) type)
					.getActualTypeArguments();
			this.entityClass = (Class<T>) parameterizedType[0];
		}
	}

	/**
	 * 得到session
	 * 
	 * @return
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void clear() {
		getSession().clear();
	}

	@Override
	public void delete(T entity) {
		getSession().delete(entity);
	}

	@Override
	public void delete(PK id) {
		getSession().delete(get(id));
	}

	@Override
	public void delete(PK[] ids) {
		for (PK id : ids) {
			delete(id);
		}
	}

	@Override
	public void evict(Object object) {
		getSession().evict(object);
	}

	@Override
	public void flush() {
		getSession().flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(PK id) {
		return (T) getSession().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> get(PK[] ids) {
		String hql = "from " + entityClass.getName()
				+ " as model where model.id in(:ids)";
		return getSession().createQuery(hql).setParameterList("ids", ids).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() {
		String hql = "from " + entityClass.getName();
		return getSession().createQuery(hql).list();
	}

	@Override
	public Long getTotalCount() {
		String hql = "select count(*) from " + entityClass.getName();
		return (Long) getSession().createSQLQuery(hql).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T load(PK id) {
		return (T) getSession().load(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PK save(T entity) {
		return (PK) getSession().save(entity);
	}

	@Override
	public void update(T entity) {
		getSession().update(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getHqList(String hql, String... param) {
		return getQuery(hql, param).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> paging(String hql, int pageNew, int pageSize, String... parameters) {
		return getQuery(hql, parameters).setFirstResult((pageNew - 1) * pageSize).setMaxResults(pageSize).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getData(String hql, String... parameters) {
		return getQuery(hql, parameters).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getMaxData(String hql, int max, String... parameters) {
		return getQuery(hql, parameters).setMaxResults(max).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T getHqlObj(String hql, String... parameters) {
		return (T) getQuery(hql, parameters).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getPageAll(String hql, Page page, String... parameters) {
		return getQuery(hql, parameters).setFirstResult((page.getCurrentPage() - 1) * page.getNumPerPage()).setMaxResults(page.getNumPerPage()).list();
	}

	@Override
	public Integer getCount(String sql, String... parameters) {
		return Integer.valueOf(getSqlQuery(sql, parameters).uniqueResult().toString());
	}

	protected Query getQuery(String hql, String... parameters) {
		return query(getSession().createQuery(hql), parameters);
	}

	protected Query getSqlQuery(String hql, String... parameters) {
		return query(getSession().createSQLQuery(hql), parameters);
	}
	
	private Query query(Query query, String... parameters) {
		if (parameters != null && parameters.length > 0) {
			for (int i = 0; i < parameters.length; i++) {
				query.setString(i, parameters[i]);
			}
		}
		return query;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getHqlObjForUpdate(String hql, String... parameters) {
		return (T) getQuery(hql, parameters).setLockOptions(LockOptions.UPGRADE).uniqueResult();
	}
	
	@Override
	public int prepareCallAndReturn(final String callSql, final Object... parameters) {
		return getSession().doReturningWork(new ReturningWork<Integer>() {
            @Override
            public Integer execute(Connection connection) throws SQLException {
                CallableStatement cs = connection.prepareCall(callSql);
                int parametersLength = parameters.length;
                for(int i=1;i<=parametersLength;i++){
                    cs.setObject(i, parameters[i-1]);
                }
                cs.registerOutParameter(parametersLength+1,Types.INTEGER);
                cs.executeUpdate();
                return cs.getInt(parametersLength+1);
            }
        });
	}

	@Override
	public void prepareCallNoReturn(final String callSql, final Object... parameters) {
		getSession().doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                CallableStatement cs = connection.prepareCall(callSql);
                int parametersLength = parameters.length;
                for(int i=1;i<=parametersLength;i++){
                    cs.setObject(i, parameters[i-1]);
                }
                cs.executeUpdate();
            }
        });
		
	}

	@Override
	public int executeSql(String sql, Object... parameters) {
		SQLQuery query = getSession().createSQLQuery(sql);
		if (parameters != null && parameters.length>0) {
			for (int i = 0; i < parameters.length; i++) {
				query.setParameter(i, parameters[i]);
			}
		}
		return query.executeUpdate();
	}

	@Override
	public Object getObject(String sql, Object... parameters) {
		SQLQuery query = getSession().createSQLQuery(sql);
		if (parameters != null && parameters.length>0) {
			for (int i = 0; i < parameters.length; i++) {
				query.setParameter(i, parameters[i]);
			}
		}
		//query.setLockOptions(LockOptions.UPGRADE);
		return query.uniqueResult();
	}
	
	@Override
	public List<T> getObject2List(String sql, Object... parameters) {
		SQLQuery query = getSession().createSQLQuery(sql);
		if (parameters != null && parameters.length>0) {
			for (int i = 0; i < parameters.length; i++) {
				query.setParameter(i, parameters[i]);
			}
		}
		/*query.addEntity(entityClass);
		return query.list();*/
		return query.setResultTransformer(Transformers.aliasToBean(entityClass)).list();
	}
	
	@Override
	public Map<String, Object> getObjectToMap(String sql, Object... parameters) {
		SQLQuery query = getSession().createSQLQuery(sql);
		if (parameters != null && parameters.length>0) {
			for (int i = 0; i < parameters.length; i++) {
				query.setParameter(i, parameters[i]);
			}
		}
		return (Map<String, Object>) query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
	}

	public boolean validateTableNameExist(String table_name) {
		String  getName = (String) getObject("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE  TABLE_NAME=?",table_name);
		if(getName!=null&&getName.equals(table_name)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public int updateHql(String hql, String... parameters) {
		return getQuery(hql, parameters).executeUpdate();
	}
	
}
