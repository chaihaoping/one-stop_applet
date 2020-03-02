package org.atgpcm.oneStopApplet.bizEnum;

/**
 * 业务异常枚举
 */
public enum BizStatusEnum {

    USER_IS_NOT_EXIST(10002,"用户信息不存在"),
    CAR_SERIES_FAIL(10003,"获取车系信息失败"),
    CAR_SERIES_AND_MODEL_FAIL(10004,"获取车系及车型信息失败"),
    CAR_MODEL_FAIL(10005,"获取车型信息失败"),
    REGION_INFO_FAIL(10006,"获取大区及省市信息失败"),
    NATIONAL_SALES_PRICE_FAIL(10007,"获取全国销量/均价信息失败"),
    REGION_FAIL(10008,"获取大区信息失败"),
    PROVINCE_FAIL(10009,"获取省份信息失败"),
    CITY_FAIL(10010,"获取地级市信息失败"),
    AREA_PERCENT_FAIL(10011,"获取优劣势区域占比信息失败"),
    PROVINCE_SERIES_FAIL(10012,"获取省内车系详情信息失败"),
    QUANTITY_PRICE_INFO_FAIL(10013,"获取量价概况信息失败"),
    BRAND_AND_COMPETE_FAIL(10014,"获取本品牌及竞品信息失败"),
    SAVE_FEEDBACK_FAIL(10015,"保存反馈建议失败"),
    SERIES_AND_COMPETE_FAIL(10016,"获取车系及竞品车系信息失败"),
    CAR_SALES_NUM_FAIL(10017,"获取车系/车型销量信息失败"),
    QUANTITY_PRICE_TREND_FAIL(10018,"获取量价走势信息失败"),
    QUANTITY_PRICE_REGION_DISTRIBUTION_FAIL(10019,"获取区域分布信息失败"),
    QUANTITY_PRICE_REGION_DISTRIBUTION_INFO_FAIL(10020,"获取区域分布详情信息失败"),
    PRICE_PERCENT_FAIL(10021,"获取价格占比信息失败"),
    ACCOUNT_PASSWORD_FAIL(10022,"账号或密码不正确");

    private int code;
    private String msg;

    BizStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
