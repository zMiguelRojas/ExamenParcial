/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.Date;

/**
 *
 * @author miguel
 */
public class Autor {
    private int idAutor;
    private String nombre;
    private String nacionalidad;
    private Date fechaNacimiento;

    // Constructor
    public Autor(int idAutor, String nombre, String nacionalidad, Date fechaNacimiento) {
        this.idAutor = idAutor;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Getters
    public int getIdAutor() {
        return idAutor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }
}