package com.github.thinwonton.mybatis.metamodel.mybatisplus.processor.util;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.github.thinwonton.mybatis.metamodel.core.util.Style;

/**
 * Utils
 */
public class Utils {
    public static Style getStyle(GlobalConfig.DbConfig dbConfig) {
        if (dbConfig.isTableUnderline()) {
            return Style.CAMELCASE_TO_UNDERSCORE;
        }
        if (dbConfig.isCapitalMode()) {
            return Style.UPPERCASE;
        } else {
            return Style.FIRST_CHAR_LOWERCASE;
        }
    }
}
