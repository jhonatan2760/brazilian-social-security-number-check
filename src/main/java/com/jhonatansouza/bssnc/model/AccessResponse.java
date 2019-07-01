package com.jhonatansouza.bssnc.model;

import javax.persistence.*;
import java.util.Map;

@Entity
@SequenceGenerator(sequenceName = "access_seq", initialValue = 1, allocationSize = 1, name = "access_seq")
public class AccessResponse {

    private Long id;
    private String uuid;
    private String captchaBase64;
    private String cookie;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "access_seq")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Transient
    public String getCaptchaBase64() {
        return captchaBase64;
    }

    public void setCaptchaBase64(String captchaBase64) {
        this.captchaBase64 = captchaBase64;
    }


}
