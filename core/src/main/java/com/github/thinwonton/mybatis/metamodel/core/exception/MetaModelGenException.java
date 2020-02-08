package com.github.thinwonton.mybatis.metamodel.core.exception;

/**
 * MetaModelGenException
 */
public class MetaModelGenException extends MetaModelException {

    private static final long serialVersionUID = -7743079043666143942L;

    public MetaModelGenException(String message) {
        super(message);
    }

    public MetaModelGenException(String message, Throwable cause) {
        super(message, cause);
    }
}
