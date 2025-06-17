package com.mycompany.lucas.model;

public class Empresa {
    private String nome;
    private int id;
    
    public Empresa (int id, String nome){
        this.id = id;
        this.nome = nome; 
        
    }
    
    @Override
    public String toString() {
        return this.getNome();
}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}