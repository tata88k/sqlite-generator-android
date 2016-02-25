package com.pacific.SQLite;

import java.util.*;

/**
 * Created by lizhanyi on 2016/1/8.
 */
public class ViewModel {

    private Map<String, String> members;
    private List<String> types;
    private String tableName;
    private String javaPackage;
    private CodeStyle tableNameCodeStyle;
    private CodeStyle memberNameCodeStyle;
    private String className;
    private String superModelClassName;
    private int index = 1;
    private boolean hasDateType = false;

    public ViewModel() {
        this.members = new HashMap<>();
        this.types = new ArrayList<>();
    }

    public void addMember(String name, String type) {
        if (type.equals("java.util.Date")) {
            type = "Date";
        }
        members.put(name, type);
    }

    public void addType(String type) {
        if (!isIgnore(type)) {
            types.add(type);
            if (type.equals("java.util.Date") && !types.contains("java.text.SimpleDateFormat")) {
                types.add("java.text.SimpleDateFormat");
                hasDateType = true;
            }
        }
    }

    public Map<String, String> getMembers() {
        return members;
    }

    public void setMembers(Map<String, String> members) {
        this.members = members;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getJavaPackage() {
        return javaPackage;
    }

    public void setJavaPackage(String javaPackage) {
        this.javaPackage = javaPackage;
    }

    private boolean isIgnore(String type) {
        if (type.equals("java.util.Date") && !types.contains(type)) {
            return false;
        }
        return true;
    }

    public CodeStyle getTableNameCodeStyle() {
        return tableNameCodeStyle;
    }

    public void setTableNameCodeStyle(CodeStyle tableNameCodeStyle) {
        this.tableNameCodeStyle = tableNameCodeStyle;
    }

    public CodeStyle getMemberNameCodeStyle() {
        return memberNameCodeStyle;
    }

    public void setMemberNameCodeStyle(CodeStyle memberNameCodeStyle) {
        this.memberNameCodeStyle = memberNameCodeStyle;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public int getIndex() {
        return index;
    }

    public String initIndex() {
        this.index = 1;
        return "";
    }

    public String addIndex() {
        index++;
        return "";
    }

    public String getCursorMethod(String type) {
        if (type.equals("float") || type.equals("Float")) {
            return "getFloat";
        } else if (type.equals("double") || type.equals("Double")) {
            return "getDouble";
        } else if (type.equals("byte[]")) {
            return "getBlob";
        } else if (type.equals("int") || type.equals("Integer")) {
            return "getInt";
        } else if (type.equals("long") || type.equals("Long")) {
            return "getLong";
        } else if (type.equals("short") || type.equals("Short")) {
            return "getShort";
        } else {
            return "getString";
        }
    }

    public String getColumnName(String name) {
        if (memberNameCodeStyle == CodeStyle.Pascal) {
            return StringHelper.camel2Pascal(name);
        } else if (memberNameCodeStyle == CodeStyle.Score) {
            return StringHelper.camel2score(name);
        } else {
            return name;
        }
    }

    public boolean isHasDateType() {
        return hasDateType;
    }

    public String getSuperModelClassName() {
        return superModelClassName;
    }

    public void setSuperModelClassName(String superModelClassName) {
        this.superModelClassName = superModelClassName;
    }
}
