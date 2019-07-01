package com.jhonatansouza.bssnc.service;

import com.jhonatansouza.bssnc.model.AccessResponse;
import com.jhonatansouza.bssnc.model.Person;
import com.jhonatansouza.bssnc.model.SsnPerson;
import java.util.Map;
import java.util.Optional;

public interface SocialSecurityNumberService {

    AccessResponse createRequest();
    Optional<AccessResponse> findRequestById(Long id);
    SsnPerson getPerson(Person pessoaFisica, String captcha, Map<String, String> cookies);

}
