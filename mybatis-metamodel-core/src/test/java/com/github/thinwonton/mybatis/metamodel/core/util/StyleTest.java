package com.github.thinwonton.mybatis.metamodel.core.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * StyleTest
 */
public class StyleTest {
    private String[] fields = new String[]{
            "hello",
            "hello_world",
            "helloWorld",
            "hello1",
            "hello_1",
            "Hello",
            "HelloWorld"
    };

    @Test
    public void testNormal() {
        for (String field : fields) {
            Assert.assertEquals(field, StringUtils.transform(field, Style.NORMAL));
        }
    }

    @Test
    public void testUppercase() {
        for (String field : fields) {
            Assert.assertEquals(field.toUpperCase(), StringUtils.transform(field, Style.UPPERCASE));
        }
    }

    @Test
    public void testLowercase() {
        for (String field : fields) {
            Assert.assertEquals(field.toLowerCase(), StringUtils.transform(field, Style.LOWERCASE));
        }
    }

    @Test
    public void testCamelcase_to_underscore() {
        for (int i = 0; i < fields.length; i++) {
            if (i == 2) {
                Assert.assertEquals("hello_world", StringUtils.transform(fields[i], Style.CAMELCASE_TO_UNDERSCORE));
            } else if (i == 5) {
                Assert.assertEquals("hello", StringUtils.transform(fields[i], Style.CAMELCASE_TO_UNDERSCORE));
            } else if (i == 6) {
                Assert.assertEquals("hello_world", StringUtils.transform(fields[i], Style.CAMELCASE_TO_UNDERSCORE));
            } else {
                Assert.assertEquals(fields[i], StringUtils.transform(fields[i], Style.CAMELCASE_TO_UNDERSCORE));
            }
        }
    }

    @Test
    public void testCamelcase_to_underscore_and_uppercase() {
        for (int i = 0; i < fields.length; i++) {
            if (i == 2) {
                Assert.assertEquals("HELLO_WORLD", StringUtils.transform(fields[i], Style.CAMELCASE_TO_UNDERSCORE_AND_UPPERCASE));
            } else if (i == 5) {
                Assert.assertEquals("HELLO", StringUtils.transform(fields[i], Style.CAMELCASE_TO_UNDERSCORE_AND_UPPERCASE));
            } else if (i == 6) {
                Assert.assertEquals("HELLO_WORLD", StringUtils.transform(fields[i], Style.CAMELCASE_TO_UNDERSCORE_AND_UPPERCASE));
            } else {
                Assert.assertEquals(fields[i].toUpperCase(), StringUtils.transform(fields[i], Style.CAMELCASE_TO_UNDERSCORE_AND_UPPERCASE));
            }
        }
    }

    @Test
    public void testCamelcase_to_underscore_and_lowercase() {
        for (int i = 0; i < fields.length; i++) {
            if (i == 2) {
                Assert.assertEquals("hello_world", StringUtils.transform(fields[i], Style.CAMELCASE_TO_UNDERSCORE_AND_LOWERCASE));
            } else if (i == 5) {
                Assert.assertEquals("hello", StringUtils.transform(fields[i], Style.CAMELCASE_TO_UNDERSCORE_AND_LOWERCASE));
            } else if (i == 6) {
                Assert.assertEquals("hello_world", StringUtils.transform(fields[i], Style.CAMELCASE_TO_UNDERSCORE_AND_LOWERCASE));
            } else {
                Assert.assertEquals(fields[i].toLowerCase(), StringUtils.transform(fields[i], Style.CAMELCASE_TO_UNDERSCORE_AND_LOWERCASE));
            }
        }
    }

    @Test
    public void testFirst_char_lowercase() {
        for (int i = 0; i < fields.length; i++) {
            if (i == 5) {
                Assert.assertEquals("hello", StringUtils.transform(fields[i], Style.FIRST_CHAR_LOWERCASE));
            } else if (i == 6) {
                Assert.assertEquals("helloWorld", StringUtils.transform(fields[i], Style.FIRST_CHAR_LOWERCASE));
            } else {
                Assert.assertEquals(fields[i], StringUtils.transform(fields[i], Style.FIRST_CHAR_LOWERCASE));
            }
        }
    }

}