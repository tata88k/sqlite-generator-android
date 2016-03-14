package com.pacific.SQLite;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by UsherBaby on 2016/1/6.
 */
public class StringHelper {

    public static String camel2Pascal(String source) {
        return source.replaceFirst(source.substring(0, 1), source.substring(0, 1).toUpperCase());
    }

    public static String score2Pascal(String source) {
        Pattern pattern = Pattern.compile("_[a-z]");
        Matcher matcher = pattern.matcher(source);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            String firstChar = matcher.group().substring(1, 2);
            matcher.appendReplacement(stringBuffer, firstChar.toUpperCase());
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.replace(0, 1, stringBuffer.substring(0, 1).toUpperCase()).toString();
    }


    public static String pascal2Camel(String source) {
        return source.replaceFirst(source.substring(0, 1), source.substring(0, 1).toLowerCase());
    }

    public static String score2Camel(String source) {
        Pattern pattern = Pattern.compile("_[a-z]");
        Matcher matcher = pattern.matcher(source);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            String firstChar = matcher.group().substring(1, 2);
            matcher.appendReplacement(stringBuffer, firstChar.toUpperCase());
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    public static String camel2Score(String source) {
        return pascal2Score(source);
    }

    public static String pascal2Score(String source) {
        String regexStr = "[A-Z]";
        Matcher matcher = Pattern.compile(regexStr).matcher(source);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            String g = matcher.group();
            matcher.appendReplacement(stringBuffer, "_" + g.toLowerCase());
        }
        matcher.appendTail(stringBuffer);
        if (stringBuffer.charAt(0) == '_') {
            stringBuffer.delete(0, 1);
        }
        return stringBuffer.toString();
    }

    //called in ftl template
    public static String toIFExists(String source) {
        String upperCaseSource = source.toUpperCase();
        if (source.contains("CREATE VIEW") && !upperCaseSource.contains("CREATE VIEW IF NOT EXISTS")) {
            return source.replaceFirst("CREATE VIEW", "CREATE VIEW IF NOT EXISTS");
        }
        if (!upperCaseSource.contains("CREATE TABLE IF NOT EXISTS")) {
            return source.replaceFirst("CREATE TABLE", "CREATE TABLE IF NOT EXISTS");
        }
        return source;
    }
}
