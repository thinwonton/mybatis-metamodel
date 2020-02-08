package com.github.thinwonton.mybatis.metamodel.core.gen;

public interface ImportContext {

	/**
	 * 获取导入的类型简单字符串
	 * @param qualifiedName 类型全名
	 * @return
	 */
	String importType(String qualifiedName);

	/**
	 * 生成导入包的字符串
	 * @return
	 */
	String generateImports();
}
