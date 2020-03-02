package org.atgpcm.oneStopApplet.common;

/**
 * @Auther: luorui
 * @Date: 2018/9/7 15:30
 * @Description: 返回数据结果级
 * @Emmail: luorui@atgco.cn
 */
@SuppressWarnings("unchecked")
public class Result<T> {
    private String errMsg;
    private int code;
    private T data;

    private Result(T data) {
        this.code = Constant.SUCCESS;
        this.errMsg = Constant.SUCCESS_MSG;
        this.data = data;
    }

    private Result(int code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }

    private Result(int code, String errMsg, T data) {
        this.code = code;
        this.errMsg = errMsg;
        this.data = data;
    }

    public Result() {
    }

    public T getData() {
        return data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public int getCode() {
        return code;
    }

    /**
     * 成功时候的调用
     *
     * @return
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }
    public static <T> Result<T> success(int code,T data) {
        return new Result<T>(code,null,data);
    }
    public static <T> Result<T> success(int code, String errMsg) {
        return new Result<T>(code, errMsg);
    }

    /**
     * 成功，不需要传入参数
     *
     * @return
     */
    public static <T> Result<T> success() {
        return (Result<T>) success("");
    }

    /**
     * 失败时候的调用
     *
     * @return
     */
    public static <T> Result<T> error(int code, String errMsg) {
        return new Result<T>(code, errMsg);
    }

    /**
     * 失败时候的调用
     *
     * @return
     */
    public static <T> Result<T> error() {
        return new Result<T>(Constant.ERROR, Constant.ERROR_MSG);
    }

    /**
     * 失败时调用，并返回
     * @param code
     * @param errMsg
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(int code, String errMsg, T data) {
        return new Result<T>(code, errMsg, data);
    }
}
