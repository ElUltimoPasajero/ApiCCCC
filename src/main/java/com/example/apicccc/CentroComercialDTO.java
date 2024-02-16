package com.example.apicccc;


import lombok.Data;
@Data
public class CentroComercialDTO {

    private String nombre;
    private String fechainauguracion;


    // Constructor para nombre y fecha de inauguración
    public CentroComercialDTO(String nombre, String fechainauguracion) {
        this.nombre = nombre;
        this.fechainauguracion = fechainauguracion;
    }



}


