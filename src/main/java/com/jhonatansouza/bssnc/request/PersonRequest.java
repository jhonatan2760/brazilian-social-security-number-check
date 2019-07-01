package com.jhonatansouza.bssnc.request;

import com.jhonatansouza.bssnc.model.Person;

public class PersonRequest {

    private String cpf;
    private String birthDate;
    private String captcha;


    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public static Person fromRequest(PersonRequest personRequest){
        Person p = new Person();
        p.setCpf(personRequest.getCpf());
        p.setBirthDate(personRequest.getBirthDate());
        return p;
    }

}
