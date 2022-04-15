package com.itsol.recruit_managerment.utils;

public class DataUtil {
    public static boolean isNotNullAndEmptyString(String srcString) {
        return !(srcString == null || "".equals(srcString.trim()));
    }
}
