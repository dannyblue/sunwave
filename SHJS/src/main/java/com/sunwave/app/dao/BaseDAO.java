package com.sunwave.app.dao;

import javax.persistence.EntityManager;

public interface BaseDAO<T> {
	public boolean save(Object entity);

	public boolean delete(Object entity);

	public boolean update(Object entity);

	public Object findById(Class<T> c, Object id);
	
	public EntityManager getEntityManager();
}
