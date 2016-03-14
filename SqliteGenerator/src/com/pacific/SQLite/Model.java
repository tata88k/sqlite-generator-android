package com.pacific.SQLite;

import java.util.*;

/**
 * Created by UsherBaby on 2016/1/6.
 */
public class Model {

    private Map<String, String> members;
    private List<String> types;
    private String tableName;
    private String javaPackage;
    private List<String> autoIncrementMembers;
    private CodeStyle tableNameCodeStyle;
    private CodeStyle memberNameCodeStyle;
    private String className;
    private String superModelClassName;
    private int index = 1;
    private boolean hasDateType = false;

    public Model() {
        this.members = new HashMap<>();
        this.autoIncrementMembers = new ArrayList<>();
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

    public void addAutoIncrementMember(String name) {
        autoIncrementMembers.add(name);
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

    public String getUpdateSql() {
        StringBuffer buffer = new StringBuffer("update ");
        buffer.append(tableName);
        buffer.append(" set ");
        Iterator iterator = members.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry) iterator.next();
            String key = entry.getKey();
            if (key.equals("id")) continue;
            if (memberNameCodeStyle == CodeStyle.Score) {
                key = StringHelper.camel2Score(key);
            } else if (memberNameCodeStyle == CodeStyle.Pascal) {
                key = StringHelper.camel2Pascal(key);
            } else {
            }
            buffer.append(key);
            buffer.append("=?, ");
        }
        buffer.deleteCharAt(buffer.length() - 2);
        buffer.append("where id=?;");
        return buffer.toString();
    }

    public String getInsertSql() {
        StringBuffer buffer = new StringBuffer("insert into ");
        buffer.append(tableName);
        buffer.append(" (");
        Iterator iterator = members.entrySet().iterator();
        int count = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry) iterator.next();
            String key = entry.getKey();
            if (autoIncrementMembers.contains(key)) continue;
            if (memberNameCodeStyle == CodeStyle.Score) {
                key = StringHelper.camel2Score(key);
            } else if (memberNameCodeStyle == CodeStyle.Pascal) {
                key = StringHelper.camel2Pascal(key);
            } else {
            }
            buffer.append(key);
            buffer.append(", ");
            count++;
        }
        buffer.replace(buffer.length() - 2, buffer.length() - 1, ")");

        buffer.append("values (");
        for (int i = 0; i < count; i++) {
            if (i == count - 1) {
                buffer.append("?);");
            } else {
                buffer.append("? ,");
            }
        }
        return buffer.toString();
    }

    public boolean isAutoIncrementMember(String name) {
        if (autoIncrementMembers.contains(name)) {
            return true;
        }
        return false;
    }

    public int getAutoIncrementMemberSize() {
        return autoIncrementMembers.size();
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
            return StringHelper.camel2Score(name);
        } else {
            return name;
        }
    }

    public boolean isHasDateType() {
        return hasDateType;
    }

    public boolean hasIdPrimaryKey() {
        return members.containsKey("id") || members.containsKey("Id") || members.containsKey("ID") || members.containsKey("iD");
    }

    public String getSuperModelClassName() {
        return superModelClassName;
    }

    public void setSuperModelClassName(String superModelClassName) {
        this.superModelClassName = superModelClassName;
    }
}
