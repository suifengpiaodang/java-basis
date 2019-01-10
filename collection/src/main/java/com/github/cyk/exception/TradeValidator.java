package com.github.cyk.exception;

import org.springframework.stereotype.Component;

/**
 * @packege com.mfw.traffic.flight.exception.validator
 * @Description 统一返回结果的封装
 * @Author hanxiaodong
 * @Date 2018/6/27 下午8:49
 * @Version 0.0.1
 */
@Component
public class TradeValidator {

    /**
     * 响应成功
     *
     * @param t
     * @param message
     * @param <T>
     * @return
     *//*
    public <T> ResultWrapper<T> getSuccessResponse(T t, String message) {
        ResultWrapper<T> successWrapper = new ResultWrapper<>();
        successWrapper.setSuccess(Boolean.TRUE);
        successWrapper.setCode("200");
        successWrapper.addMessage(message);
        successWrapper.setData(t);
        return successWrapper;
    }

    *//**
     * 响应错误
     *
     * @param code
     * @param message
     * @param <T>
     * @return
     *//*
    public <T> ResultWrapper<T> getErrorResponse(String code, String message) {
        ResultWrapper<T> errorWrapper = new ResultWrapper<>();
        errorWrapper.setSuccess(Boolean.FALSE);
        errorWrapper.setCode(code);
        errorWrapper.addMessage(message);
        return errorWrapper;
    }*/
}
