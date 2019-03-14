package com.pan.common.enums;

/**
 * @author panzhigao
 */

public enum MethodEnum {
    /**
     *POST
     */
    POST("POST"),
    /**
     *GET
     */
    GET("GET");

    private String methodType;

    public String getMethodType() {
        return methodType;
    }

    MethodEnum(String methodType) {
        this.methodType = methodType;
    }
}
