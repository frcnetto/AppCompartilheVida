package com.example.frcnetto.compartilhevida.model;

public class Campanha {

    private String cid;
    private String uid;
    private String nomeBeneficiario;
    private String cidade;
    private String estado;
    private String tpSanguineo;
    private String contato;

    public Campanha() {}

    public Campanha(String cid, String uid, String cidade, String estado, String nomeBeneficiario, String tpSanguineo, String contato) {
        this.cid = cid;
        this.cidade = cidade;
        this.estado = estado;
        this.nomeBeneficiario = nomeBeneficiario;
        this.tpSanguineo = tpSanguineo;
        this.uid = uid;
        this.contato = contato;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNomeBeneficiario() {
        return nomeBeneficiario;
    }

    public void setNomeBeneficiario(String nomeBeneficiario) {
        this.nomeBeneficiario = nomeBeneficiario;
    }

    public String getTpSanguineo() {
        return tpSanguineo;
    }

    public void setTpSanguineo(String tpSanguineo) {
        this.tpSanguineo = tpSanguineo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }
}
