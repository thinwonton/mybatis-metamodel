package com.github.thinwonton.mybatis.metamodel.core.exception;

public class MetaModelException extends RuntimeException {
    private static final long serialVersionUID = 9020783152756171001L;

    public MetaModelException(String message) {
        super(message);
    }

    public MetaModelException(String message, Throwable cause) {
        super(message, cause);
    }
}
