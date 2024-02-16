package com.example.apicccc;


import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;


@Entity
@Data
@Table(name="centroscomerciales")
public class CentroComercial implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String direccion;
    private Double tamaño;
    private Integer telefono;
    private String localesrestauracion;
    private String localesmoda;
    private Boolean recreativos;
    private Integer numerosalascine;
    private Integer aforo;
    private Integer capacidadaparcamiento;
    private String horario;
    private String fechainauguracion;
    private String descripcion;



}
