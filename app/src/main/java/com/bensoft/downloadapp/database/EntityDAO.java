package com.bensoft.downloadapp.database;

import android.content.Context;

import com.bensoft.downloadapp.entities.Entity;

import java.util.List;

/**
 * Created by 炎宾 on 2016/3/13.
 */
public interface EntityDAO<T extends Entity> {
    public long insertEntity(T entity);
    public long deleteEntity(T entity);
    public long updateEntity(T entity);
    public List<T> getEntities(String url);
    public boolean isEntityExist(String url,int id);
}
