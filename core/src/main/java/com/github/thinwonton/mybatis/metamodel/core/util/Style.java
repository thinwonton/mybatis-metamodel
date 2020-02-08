package com.github.thinwonton.mybatis.metamodel.core.util;

/**
 * 字段转换方式
 */
public enum Style {
    NORMAL,                     //原值
    CAMELCASE_TO_UNDERSCORE,   //驼峰转下划线
    UPPERCASE,                  //转换为大写
    LOWERCASE,                  //转换为小写
    CAMELCASE_TO_UNDERSCORE_AND_UPPERCASE,      //驼峰转下划线大写形式
    CAMELCASE_TO_UNDERSCORE_AND_LOWERCASE,      //驼峰转下划线小写形式
    FIRST_CHAR_LOWERCASE //第一个字符大写
}
