package com.pacific.SQLite;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by UsherBaby on 2016/1/6.
 */
public class Api {
    private String javaPackage;
    private Map<String, String> tables;
    private Map<String, String> views;
    private String dbOpenHelperClassName;
    private String dbContextClassName;
    private String dataAccessClassName;
    private String modelJavaPackage;
    private String superModelClassName;

    public Api() {
        this.tables = new HashMap<>();
        this.views = new HashMap<>();
    }

    public String getJavaPackage() {
        return javaPackage;
    }

    public void setJavaPackage(String javaPackage) {
        this.javaPackage = javaPackage;
    }

    public Map<String, String> getTables() {
        return tables;
    }

    public void setTables(Map<String, String> tables) {
        this.tables = tables;
    }

    public void addTable(String name, String sql) {
        if (!tables.containsKey(name)) {
            tables.put(name, sql);
        }
    }

    public void addView(String name, String sql) {
        if (!views.containsKey(name)) {
            views.put(name, sql);
        }
    }

    public Map<String, String> getViews() {
        return views;
    }

    public void setViews(Map<String, String> views) {
        this.views = views;
    }

    public String getDbOpenHelperClassName() {
        return dbOpenHelperClassName;
    }

    public void setDbOpenHelperClassName(String dbOpenHelperClassName) {
        this.dbOpenHelperClassName = dbOpenHelperClassName;
    }

    public String getDbContextClassName() {
        return dbContextClassName;
    }

    public void setDbContextClassName(String dbContextClassName) {
        this.dbContextClassName = dbContextClassName;
    }

    public String getDataAccessClassName() {
        return dataAccessClassName;
    }

    public void setDataAccessClassName(String dataAccessClassName) {
        this.dataAccessClassName = dataAccessClassName;
    }

    public String getModelJavaPackage() {
        return modelJavaPackage;
    }

    public void setModelJavaPackage(String modelJavaPackage) {
        this.modelJavaPackage = modelJavaPackage;
    }

    public String getSuperModelClassName() {
        return superModelClassName;
    }

    public void setSuperModelClassName(String superModelClassName) {
        this.superModelClassName = superModelClassName;
    }
}
