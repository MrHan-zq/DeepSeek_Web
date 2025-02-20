package com.example.deepseek.util;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class StringUtil {

    private static final String DEFAULT_STR = "abcbacbbc";

    public static int getMaxStrLength(String str) {
        String listStr = StringUtils.isBlank(str) ? DEFAULT_STR : str;
        char[] bytes = listStr.toCharArray();
        List<String> stringList = new ArrayList<>();
        for (char aByte : bytes) {
            stringList.add(String.valueOf(aByte));
        }
        List<String> list = new ArrayList<>(stringList);
        List<String> newList = new ArrayList<>();
        for (String s : stringList) {
            Map<String, String> map = new HashMap<>();
            map.put(s, s);
            for (String m : list) {
                if (map.containsKey(m)) {
                    StringBuilder builder = new StringBuilder();
                    for (String v : map.values()) {
                        builder.append(v);
                    }
                    newList.add(builder.toString());
                    map.clear();
                    continue;
                }
                map.put(m, m);
            }
        }
        return newList.stream().sorted(Comparator.comparing(String::length))
                .max(Comparator.comparing(String::length))
                .map(String::length)
                .orElse(0);
    }

    public static void main(String[] args) {
        System.out.println(StringUtil.getMaxStrLength(null));
    }
}
