package com.github.cyk.exception;


/**
 * @description: 业务异常
 * @create: 2018-07-15 19:18
 */
/*public enum SystemErrorEnum implements StandardError {

    AUTHORIZATION_ERROR("权限问题"),
    ENUM_ERROR("枚举异常"),
    SYSTEM_ERROR("系统异常"),
    ADD_VCABIN_ERROR("添加虚仓失败"),
    UPDATE_VCABIN_ERROR("修改虚仓状态失败"),
    PAGE_VCABIN_ERROR("分页查询虚仓失败");

    private String description;

    SystemErrorEnum(String description) {
        this.description = description;
    }

    @Override
    public String getType() {
        return "SYSTEM_ERROR";
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}*/

public enum SystemErrorEnum {

    AUTHORIZATION_ERROR("权限问题"),
    ENUM_ERROR("枚举异常"),
    SYSTEM_ERROR("系统异常"),
    ADD_VCABIN_ERROR("添加虚仓失败"),
    UPDATE_VCABIN_ERROR("修改虚仓状态失败"),
    PAGE_VCABIN_ERROR("分页查询虚仓失败");

    private String description;

    SystemErrorEnum(String description) {
        this.description = description;
    }

}

