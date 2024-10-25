/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.util.ArrayList;
/**
 *
 * @author miguel
 */

public class Biblioteca {
    // Lista de libros
    private ArrayList<Libro> libros;
    
    // Lista de autores
    private ArrayList<Autor> autores;
    
    // Lista de préstamos
    private ArrayList<Prestamo> prestamos;

    // Constructor para inicializar las listas
    public Biblioteca() {
        libros = new ArrayList<>();
        autores = new ArrayList<>();
        prestamos = new ArrayList<>();
    }

    // Método para generar un nuevo ID para autores
    public int generarIdAutor() {
        return autores.size() + 1;
    }

    // Método para generar un nuevo ID para libros
    public int generarIdLibro() {
        return libros.size() + 1;
    }

    // Método para generar un nuevo ID para préstamos
    public int generarIdPrestamo() {
        return prestamos.size() + 1;
    }

    // Métodos para la gestión de libros
    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    public ArrayList<Libro> obtenerLibros() {
        return libros;
    }

    public void eliminarLibro(int idLibro) {
        libros.removeIf(libro -> libro.getIdLibro() == idLibro);
    }

    // Métodos para la gestión de autores
    public void agregarAutor(Autor autor) {
        autores.add(autor);
    }

    public ArrayList<Autor> obtenerAutores() {
        return autores;
    }

    public void eliminarAutor(int idAutor) {
        autores.removeIf(autor -> autor.getIdAutor() == idAutor);
    }

    // Métodos para la gestión de préstamos
    public void agregarPrestamo(Prestamo prestamo) {
        prestamos.add(prestamo);
    }

    public ArrayList<Prestamo> obtenerPrestamos() {
        return prestamos;
    }

    public void eliminarPrestamo(int idPrestamo) {
        prestamos.removeIf(prestamo -> prestamo.getIdPrestamo() == idPrestamo);
    }
}
