/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author miguel
 */

public class Libro {
    private int idLibro;
    private String titulo;
    private int anioPublicacion;
    private String isbn; // Cambié el tipo de int a String para el ISBN

    // Constructor
    public Libro(int idLibro, String titulo, int anioPublicacion, String isbn) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.anioPublicacion = anioPublicacion;
        this.isbn = isbn; // Inicializa el ISBN
    }

    // Getters
    public int getIdLibro() {
        return idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public String getIsbn() { // Agrega el getter para ISBN
        return isbn;
    }

    // Método que devuelve la fecha de publicación como Date
    public Date getFechaPublicacion() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(anioPublicacion, Calendar.JANUARY, 1); // Ajusta el mes y el día según sea necesario
        return calendar.getTime();
    }
}