package com.sunwave.app.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sunwave.app.dao.BaseDAO;

public class BaseDAOImpl<T> implements BaseDAO<T>{
	
	@PersistenceContext(unitName="mysql")
	private EntityManager entityManager;
	

	@Override
	public boolean save(Object entity) {
		try {
			entityManager.persist(entity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Object entity) {
		try {
			entityManager.remove(entity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Object entity) {
		try {
			entityManager.merge(entity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Object findById(Class<T> c, Object id) {
		try {
			return entityManager.find(c, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public EntityManager getEntityManager() {
		return this.entityManager;
	}

}
