package com.tiaoling.cloud.core.constant;

/**
 * Created by yhl on 2016/9/30.
 */
public class ComErrorCodeConstants {
    public enum ErrorCode {
        /**
         * 错误编码规范： 长度：8位 00 0 00 000 含义： 12位：公共系统错误编码，00 3位：消息等级，默认为1 45位：模块，01
         * 67位：具体错误 系统异常：00100001-00100099
         */
		/* 21100** */
        SYSTEM_SUCCESS("00100000", "success"),
        SYSTEM_ERROR("00100001", "系统异常!"), PARA_NORULE_ERROR(
                "00100002", "请求参数格式不符合规则"), VALIDATE_ERROR("00100003", "校验有误"), DATA_OPER_ERROR(
                "00100004", "数据操作异常"), APPLICATION_OPER_ERROR("00100006",
                "系统业务异常"), ACCOUNT_EXIST_ERROR("00100007", "账号信息存在异常"), DATA_EMPTY_ERROR(
                "00100005", "查询结果为空"), UPDATE_STATUS_ERROR("00100008",
                "修改优惠券状态失败"), SIGN_VALID_ERROR("00100009", "签名验证失败"), SIGN_EMPTY_ERROR(
                "00100010", "签名参数不能为空"), JSON_ERROR("21100021", "JSON格式不正确！"), MERCHANT_ERROR(
                "00100012", "商家验证失败"), Params_EMPTY_ERROR("00100013",
                "请求报文不能为空"), User_Code_Exist("00100014", "手机号已经注册过"), Id_Number_Exist(
                "00100015", "身份证号已经注册过"),Waybill_Not_Exist("00100016","运单号不存在");

        private String errorCode;
        private String memo;

        private ErrorCode() {
        };

        private ErrorCode(String errorCode, String memo) {
            this.errorCode = errorCode;
            this.memo = memo;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }
    }
}
