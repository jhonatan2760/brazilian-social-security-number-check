package com.jhonatansouza.bssnc.model;

public class SsnPerson {

    private String nome;
    private String dataNascimento;
    private String situacao;
    private String dataInscricao;
    private String digitoVerificado;

    public SsnPerson() {
    }

    public SsnPerson(String nome, String dataNascimento, String situacao, String dataInscricao, String digitoVerificado) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.situacao = situacao;
        this.dataInscricao = dataInscricao;
        this.digitoVerificado = digitoVerificado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(String dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public String getDigitoVerificado() {
        return digitoVerificado;
    }

    public void setDigitoVerificado(String digitoVerificado) {
        this.digitoVerificado = digitoVerificado;
    }
}
