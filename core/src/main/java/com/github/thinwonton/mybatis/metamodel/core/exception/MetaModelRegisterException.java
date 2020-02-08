package com.github.thinwonton.mybatis.metamodel.core.exception;

/**
 * MetaModelGenException
 */
public class MetaModelRegisterException extends MetaModelException {

    private static final long serialVersionUID = 6507468266756257343L;

    public MetaModelRegisterException(String message) {
        super(message);
    }

    public MetaModelRegisterException(String message, Exception e) {
        super(message, e);
    }
}
