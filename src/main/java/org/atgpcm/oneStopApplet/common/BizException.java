package org.atgpcm.oneStopApplet.common;

import org.atgpcm.oneStopApplet.bizEnum.BizStatusEnum;

/**
 * @author chaihaoping
 * @title 自定义异常
 * @date 2020/1/6 16:30
 * @description 自定义异常
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int code;
    private String message;

    public BizException(int code,String message) {
        this.code = code;
        this.message = message;
    }

    public BizException(BizStatusEnum bizStatusEnum) {
        this.code = bizStatusEnum.getCode();
        this.message = bizStatusEnum.getMsg();
    }
}
