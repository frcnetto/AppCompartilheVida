package com.example.frcnetto.compartilhevida.model;

public class Usuario {
    private String uid = "";
    private String nome = "";
    private String email = "";
    private int idade;
    private String sexo = "";
    private boolean naoEstouGravida = false;
    private boolean esteveGravida = false;
    private String tpParto = "";
    private String dtParto = "";
    private String tpSanguineo = "";
    private boolean jaDoador = false;
    private boolean primeiraDoacao = false;
    private String dtUltmDoacao = "";
    private float peso = (float) 0.0;
    private boolean tatuagem = false;
    private String dtTatuagem = "";
    private boolean diabetes = false;
    private boolean hepatite = false;
    private boolean aids = false;
    private boolean sifilis = false;
    private boolean htlv = false;
    private boolean chagas = false;
    private boolean malaria = false;
    private boolean drogas = false;

    public Usuario() {
        nome = "";
    }

    public boolean isAids() {
        return aids;
    }

    public void setAids(boolean aids) {
        this.aids = aids;
    }

    public boolean isChagas() {
        return chagas;
    }

    public void setChagas(boolean chagas) {
        this.chagas = chagas;
    }

    public boolean isDiabetes() {
        return diabetes;
    }

    public void setDiabetes(boolean diabetes) {
        this.diabetes = diabetes;
    }

    public boolean isDrogas() {
        return drogas;
    }

    public void setDrogas(boolean drogas) {
        this.drogas = drogas;
    }

    public String getDtParto() {
        return dtParto;
    }

    public void setDtParto(String dtParto) {
        this.dtParto = dtParto;
    }

    public String getDtTatuagem() {
        return dtTatuagem;
    }

    public void setDtTatuagem(String dtTatuagem) {
        this.dtTatuagem = dtTatuagem;
    }

    public String getDtUltmDoacao() {
        return dtUltmDoacao;
    }

    public void setDtUltmDoacao(String dtUltmDoacao) {
        this.dtUltmDoacao = dtUltmDoacao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEsteveGravida() {
        return esteveGravida;
    }

    public void setEsteveGravida(boolean esteveGravida) {
        this.esteveGravida = esteveGravida;
    }

    public boolean isHepatite() {
        return hepatite;
    }

    public void setHepatite(boolean hepatite) {
        this.hepatite = hepatite;
    }

    public boolean isHtlv() {
        return htlv;
    }

    public void setHtlv(boolean htlv) {
        this.htlv = htlv;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public boolean isJaDoador() {
        return jaDoador;
    }

    public void setJaDoador(boolean jaDoador) {
        this.jaDoador = jaDoador;
    }

    public boolean isMalaria() {
        return malaria;
    }

    public void setMalaria(boolean malaria) {
        this.malaria = malaria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public boolean isPrimeiraDoacao() {
        return primeiraDoacao;
    }

    public void setPrimeiraDoacao(boolean primeiraDoacao) {
        this.primeiraDoacao = primeiraDoacao;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public boolean isSifilis() {
        return sifilis;
    }

    public void setSifilis(boolean sifilis) {
        this.sifilis = sifilis;
    }

    public boolean isTatuagem() {
        return tatuagem;
    }

    public void setTatuagem(boolean tatuagem) {
        this.tatuagem = tatuagem;
    }

    public String getTpParto() {
        return tpParto;
    }

    public void setTpParto(String tpParto) {
        this.tpParto = tpParto;
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

    public boolean isNaoEstouGravida() {
        return naoEstouGravida;
    }

    public void setNaoEstouGravida(boolean naoEstouGravida) {
        this.naoEstouGravida = naoEstouGravida;
    }
}
