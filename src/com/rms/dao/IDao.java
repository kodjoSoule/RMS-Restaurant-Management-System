package com.rms.dao;

import java.util.List;

import com.rms.exceptions.DAOException;

public interface IDao<T> {
	public void create (T entity) throws DAOException;
	public T read (int id) throws DAOException;
	public List<T> list () throws DAOException;
	public void update (T entity) throws DAOException;
	public void delete (int id) throws DAOException;
	
}
