package com.jhonatansouza.bssnc.response;

import com.jhonatansouza.bssnc.model.AccessResponse;

public class AccessHttpResponse {

    private Long id;
    private String uuid;
    private String captchaBase64;
    private String cookie;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCaptchaBase64() {
        return captchaBase64;
    }

    public void setCaptchaBase64(String captchaBase64) {
        this.captchaBase64 = captchaBase64;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public static AccessHttpResponse fromEntity(AccessResponse accessResponse){
        AccessHttpResponse ht = new AccessHttpResponse();
        ht.setCaptchaBase64(accessResponse.getCaptchaBase64());
        ht.setId(accessResponse.getId());
        ht.setUuid(accessResponse.getUuid());
        ht.setCookie(accessResponse.getCookie());
        return ht;
    }


}
