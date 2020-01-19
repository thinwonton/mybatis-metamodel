package com.github.thinwonton.mybatis.metamodel.tkmapper.util;

import com.github.thinwonton.mybatis.metamodel.core.util.Style;

/**
 * Utils
 */
public class Utils {
    public static Style transform(tk.mybatis.mapper.code.Style style) {
        switch (style) {
            case normal:
                return Style.NORMAL;
            case lowercase:
                return Style.LOWERCASE;
            case uppercase:
                return Style.UPPERCASE;
            case camelhumpAndLowercase:
                return Style.CAMELCASE_TO_UNDERSCORE_AND_LOWERCASE;
            case camelhumpAndUppercase:
                return Style.CAMELCASE_TO_UNDERSCORE_AND_UPPERCASE;
            case camelhump:
            default:
                return Style.CAMELCASE_TO_UNDERSCORE;
        }
    }
}
