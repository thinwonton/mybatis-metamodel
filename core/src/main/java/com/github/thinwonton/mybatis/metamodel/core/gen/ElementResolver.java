package com.github.thinwonton.mybatis.metamodel.core.gen;

import javax.lang.model.element.TypeElement;

/**
 * ElementResolver
 */
public interface ElementResolver {
    /**
     * 解析 element 为 MetaEntity
     * @param element
     * @return
     */
    MetaEntity resolveElement(TypeElement element);
}
