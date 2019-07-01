package com.jhonatansouza.bssnc.service.impl;

import com.jhonatansouza.bssnc.model.AccessResponse;
import com.jhonatansouza.bssnc.model.Person;
import com.jhonatansouza.bssnc.model.SsnPerson;
import com.jhonatansouza.bssnc.repository.AccessRepository;
import com.jhonatansouza.bssnc.service.SocialSecurityNumberService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class SocialSecurityNumberServiceImpl implements SocialSecurityNumberService {

    private final static int NAME_INDEX = 1;
    private final static int BIRTH_DATE_INDEX = 2;
    private final static int STATUS_INDEX = 3;

    @Autowired
    private AccessRepository accessRepository;

    @Override
    public AccessResponse createRequest() {
        /* Iremos criar uma conexão com a receita federal */
        Connection conn = Jsoup.connect("https://servicos.receita.fazenda.gov.br/Servicos/CPF/ConsultaSituacao/ConsultaPublicaSonoro.asp")
                /* Precisamos invalidar o certificado TLS para conseguir o acesso sem autenticar o protocolo*/
                .validateTLSCertificates(false)
                /* Devemos seguir os redirects da página */
                .followRedirects(true)
                /* Inserimos um timeout variável*/
                .timeout(1000)
                /* Vamos simular um UserAgent */
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36")
                .method(Connection.Method.GET);

        Connection.Response r = null;
        try {
            r = conn.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Document doc = Jsoup.parse(r.body());
        /*
        * Iremos capturar a imagem do captcha e retornar o base64 para que o
        * cliente possa responder o captcha, um passo muito importante é armazenar
        * os cookies em algum local, nesse caso estamos utilizando um banco de dados
        * H2 para que possamos buscar os cookies da sessão
        * */
        Element el = doc.getElementById("imgCaptcha");
        String base = el.attr("src");
        String bas = base.subSequence(base.indexOf(",") + 1, base.length()).toString();
        AccessResponse acrs = new AccessResponse();
        String uuid = UUID.randomUUID().toString();
        acrs.setCookie(r.cookies().toString());
        acrs.setCaptchaBase64(bas);
        acrs.setUuid(uuid);
        /* Vamos salvar a sessão dessa requisição para mais tarde utilizarmos os cookies*/
        this.accessRepository.save(acrs);
        return acrs;

    }

    @Override
    public Optional<AccessResponse> findRequestById(Long id) {
        return this.accessRepository.findById(id);
    }

    @Override
    public SsnPerson getPerson(Person person, String captcha, Map<String, String> cookies) {

        Document d = null;
        try {
            /* Efetuando um post com os dados criados e respondidos no request anterior */
            d = Jsoup.connect("https://www.receita.fazenda.gov.br/Aplicacoes/SSL/ATCTA/CPF/ConsultaSituacao/ConsultaPublicaExibir.asp")
                    .data("idCheckedReCaptcha", "false")
                    .data("txtCPF", person.getCpf())
                    .data("txtDataNascimento", person.getBirthDate())
                    /* O captcha deve ser respondido */
                    .data("txtTexto_captcha_serpro_gov_br", captcha)
                    .data("enviar", "Consultar")
                    .validateTLSCertificates(false)
                    .cookies(cookies)
                    .post();
        } catch (IOException e) {
            e.printStackTrace();
            return new SsnPerson("-", "-", "RECEITA OFFLINE", "-", "-");
        }

        Document resposta = Jsoup.parse(d.body().toString());
        Elements els = resposta.getElementsByClass("clConteudoDados");
        SsnPerson pf = new SsnPerson();

        pf.setNome(els.get(NAME_INDEX).text());
        pf.setDataNascimento(els.get(BIRTH_DATE_INDEX).text());
        pf.setSituacao(els.get(STATUS_INDEX).text());

        return pf;
    }

}
