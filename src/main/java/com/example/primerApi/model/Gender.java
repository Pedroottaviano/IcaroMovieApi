package com.example.primerApi.model;

public enum Gender {
    ACCION("Acción"),
    POLICIAL("Policial"),
    ROMANTICA("Romantica"),
    DOCUMENTAL("Documental");
    private final String gender;

    Gender(String gender){
        this.gender = gender;
    }

    public String getGender(){
        return this.gender;
    }

}
