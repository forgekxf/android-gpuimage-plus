package com.bhtc.huajuan.push.bean;

/**
 * 发送验证码
 */
public class ValidateBean extends BaseBean {
    private String validate_url;    //人机验证地址

    public String getValidate_url() {
        return validate_url;
    }

    public void setValidate_url(String validate_url) {
        this.validate_url = validate_url;
    }
}
