package com.github.thinwonton.mybatis.metamodel.core.util;

public class StringUtils {
    public static final String DOT = ".";

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 根据指定的样式进行转换
     *
     * @param str
     * @param style
     * @return
     */
    public static String transform(String str, Style style) {
        switch (style) {
            case CAMELCASE_TO_UNDERSCORE:
                return camelcaseToUnderscore(str);
            case CAMELCASE_TO_UNDERSCORE_AND_LOWERCASE:
                return camelcaseToUnderscore(str).toLowerCase();
            case CAMELCASE_TO_UNDERSCORE_AND_UPPERCASE:
                return camelcaseToUnderscore(str).toUpperCase();
            case UPPERCASE:
                return str.toUpperCase();
            case LOWERCASE:
                return str.toLowerCase();
            case FIRST_CHAR_LOWERCASE:
                return firstCharToLowerCase(str);
            case NORMAL:
            default:
                return str;
        }
    }

    /**
     * 驼峰写法转换成下划线
     *
     * @param str
     */
    private static String camelcaseToUnderscore(String str) {
        final int size;
        final char[] chars;
        final StringBuilder sb = new StringBuilder(
                (size = (chars = str.toCharArray()).length) * 3 / 2 + 1);
        char c;
        for (int i = 0; i < size; i++) {
            c = chars[i];
            if (isUppercaseAlpha(c)) {
                sb.append('_').append(toLowerAscii(c));
            } else {
                sb.append(c);
            }
        }
        return sb.charAt(0) == '_' ? sb.substring(1) : sb.toString();
    }

    private static boolean isUppercaseAlpha(char c) {
        return (c >= 'A') && (c <= 'Z');
    }

    private static char toLowerAscii(char c) {
        if (isUppercaseAlpha(c)) {
            c += (char) 0x20;
        }
        return c;
    }

    public static String firstCharToLowerCase(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

}
