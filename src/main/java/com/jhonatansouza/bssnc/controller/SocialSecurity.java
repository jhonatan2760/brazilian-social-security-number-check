package com.jhonatansouza.bssnc.controller;

import com.jhonatansouza.bssnc.model.AccessResponse;
import com.jhonatansouza.bssnc.model.SsnPerson;
import com.jhonatansouza.bssnc.request.PersonRequest;
import com.jhonatansouza.bssnc.response.AccessHttpResponse;
import com.jhonatansouza.bssnc.service.SocialSecurityNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/ssn/")
public class SocialSecurity {

    @Autowired
    private SocialSecurityNumberService socialSecurityNumberService;

    @GetMapping
    public ResponseEntity<AccessHttpResponse> createAccess(UriComponentsBuilder uriComponentsBuilder) {
        AccessHttpResponse hta = AccessHttpResponse.fromEntity(this.socialSecurityNumberService.createRequest());
        return ResponseEntity.created(uriComponentsBuilder.path("/ssn/{id}").buildAndExpand(hta.getId()).toUri()).body(hta);
    }

    @PostMapping("/{id}")
    public ResponseEntity<SsnPerson> sendPerson(@PathVariable Long id, @RequestBody PersonRequest personRequest) {

        Optional<AccessResponse> accessResponseOptional = this.socialSecurityNumberService.findRequestById(id);

        if (accessResponseOptional.isPresent()) {

            AccessHttpResponse ac = AccessHttpResponse.fromEntity(accessResponseOptional.get());

            String[] it = ac.getCookie().substring(1, (ac.getCookie().length() - 1)).split(",");
            Map<String, String> cookies = new HashMap<>();

            for (int i = 0; i < it.length; i++) {
                String[] res = it[i].split("=");
                cookies.put(res[0], res[1]);
            }

            SsnPerson pf = this.socialSecurityNumberService.getPerson(PersonRequest.fromRequest(personRequest), personRequest.getCaptcha(), cookies);
            return ResponseEntity.ok(pf);
        }

        return ResponseEntity.notFound().build();
    }

}
