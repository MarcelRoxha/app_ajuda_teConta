package com.marcel.a.n.roxha.ajuda_teconta.model;

import java.io.Serializable;

public class UsuarioModel implements Serializable {

    private String id;
    private String nome;
    private String eMailUser;
    private String senhaUser;

    public UsuarioModel() {
    }

    public UsuarioModel(String id, String nome, String eMailUser, String senhaUser) {
        this.id = id;
        this.nome = nome;
        this.eMailUser = eMailUser;
        this.senhaUser = senhaUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String geteMailUser() {
        return eMailUser;
    }

    public void seteMailUser(String eMailUser) {
        this.eMailUser = eMailUser;
    }

    public String getSenhaUser() {
        return senhaUser;
    }

    public void setSenhaUser(String senhaUser) {
        this.senhaUser = senhaUser;
    }
}
