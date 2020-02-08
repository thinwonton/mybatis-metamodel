package com.github.thinwonton.mybatis.metamodel.core.gen;

import javax.lang.model.element.TypeElement;
import java.util.List;

public interface MetaEntity extends ImportContext {

    /**
     * 获取上下文
     * @return
     */
    MetaModelGenContext getMetaModelGenContext();

    /**
     * 获取对应的 Element
     * @return
     */
    TypeElement getElement();

    /**
     * 获取包名
     *
     * @return
     */
    String getPackageName();

    /**
     * 获取 ORM entity 类的全类名
     *
     * @return
     */
    String getQualifiedName();

    /**
     * 获取 ORM entity 类的简写名称
     *
     * @return
     */
    String getSimpleName();

    /**
     * 获取类的成员描述
     *
     * @return
     */
    List<MetaAttributeDescriptor> getMembers();

}