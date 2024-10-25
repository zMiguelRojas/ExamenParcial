/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.Date;
import javax.swing.JOptionPane;
import Vista.Biblioteca;
import javax.swing.table.DefaultTableModel;
import modelo.Conexion;

public class Controlador {
    private Biblioteca vista;
    private Conexion conexion;

    public Controlador(Biblioteca vista) {
        this.vista = vista;
        this.conexion = new Conexion();
    }
    
        public void guardar() {
        // Obtener los datos del autor
        String nombre = vista.getNombre();
        String nacionalidad = vista.getNacionalidad();
        Date fechaNacimiento = vista.getFechaNacimiento(); // Método que obtendrá la fecha del JCalendar

        // Validar que no sea null
        if (fechaNacimiento == null) {
            JOptionPane.showMessageDialog(vista, "Por favor, seleccione una fecha de nacimiento.");
            return;
        }

        // Crear conexión a la base de datos
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConnection();

        if (conn != null) {
            try {
                // Insertar autor y obtener el ID generado
                String sqlAutor = "INSERT INTO autores (nombre, nacionalidad, fecha_nacimiento) VALUES (?, ?, ?)";
                PreparedStatement pstmtAutor = conn.prepareStatement(sqlAutor, Statement.RETURN_GENERATED_KEYS);
                pstmtAutor.setString(1, nombre);
                pstmtAutor.setString(2, nacionalidad);
                pstmtAutor.setDate(3, new java.sql.Date(fechaNacimiento.getTime()));
                pstmtAutor.executeUpdate();

                // Obtener el ID del autor insertado
                ResultSet generatedKeys = pstmtAutor.getGeneratedKeys();
                int idAutor = -1;
                if (generatedKeys.next()) {
                    idAutor = generatedKeys.getInt(1); // Obtener el ID generado
                }

                // Obtener los datos del libro
                String titulo = vista.getTitulo();
                String genero = ""; // Asegúrate de tener un campo para el género si es necesario
                Date fechaPublicacion = vista.getFechaPublicacion();
                String isbn = vista.getIsbn();

                // Validar fecha de publicación
                if (fechaPublicacion == null) {
                    JOptionPane.showMessageDialog(vista, "Por favor, seleccione una fecha de publicación.");
                    return;
                }

                // Insertar libro
                String sqlLibro = "INSERT INTO libros (titulo, id_autor, genero, fecha_publicacion, isbn) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmtLibro = conn.prepareStatement(sqlLibro, Statement.RETURN_GENERATED_KEYS);
                pstmtLibro.setString(1, titulo);
                pstmtLibro.setInt(2, idAutor); // Usar el ID del autor
                pstmtLibro.setString(3, genero);
                pstmtLibro.setDate(4, new java.sql.Date(fechaPublicacion.getTime()));
                pstmtLibro.setString(5, isbn);
                pstmtLibro.executeUpdate();

                // Obtener el ID del libro insertado
                ResultSet generatedKeysLibro = pstmtLibro.getGeneratedKeys();
                int idLibro = -1;
                if (generatedKeysLibro.next()) {
                    idLibro = generatedKeysLibro.getInt(1); // Obtener el ID generado
                }

                // Obtener los datos del préstamo
                String idPrestamo = vista.getIdPrestamo();
                Date fechaPrestamo = vista.getFechaPrestamo();
                Date fechaDevolucion = vista.getFechaDevolucion();

                // Validar fechas de préstamo y devolución
                if (fechaPrestamo == null || fechaDevolucion == null) {
                    JOptionPane.showMessageDialog(vista, "Por favor, seleccione las fechas de préstamo y devolución.");
                    return;
                }

                // Insertar préstamo
                String sqlPrestamo = "INSERT INTO prestamos (id_libro, fecha_prestamo, fecha_devolucion) VALUES (?, ?, ?)";
                PreparedStatement pstmtPrestamo = conn.prepareStatement(sqlPrestamo);
                pstmtPrestamo.setInt(1, idLibro); // Usar el ID del libro
                pstmtPrestamo.setDate(2, new java.sql.Date(fechaPrestamo.getTime()));
                pstmtPrestamo.setDate(3, new java.sql.Date(fechaDevolucion.getTime()));
                pstmtPrestamo.executeUpdate();

                // Limpiar los campos después de guardar
                vista.limpiarCampos();
                JOptionPane.showMessageDialog(vista, "Datos guardados exitosamente.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(vista, "Error al guardar los datos: " + e.getMessage());
            } finally {
                conexion.cerrarConexion(); // Cerrar la conexión
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Error en la conexión a la base de datos.");
        }
    }
        public void editar() {
        int filaSeleccionada = vista.getFilaSeleccionada();

        // Validar si hay una fila seleccionada
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor, seleccione una fila para editar.");
            return;
        }

        // Obtener los datos actuales de la fila seleccionada
        String idAutor = vista.getValorTabla(filaSeleccionada, 0);
        String nombre = vista.getValorTabla(filaSeleccionada, 1);
        String nacionalidad = vista.getValorTabla(filaSeleccionada, 2);
        String fechaNacimiento = vista.getValorTabla(filaSeleccionada, 3);
        String idLibro = vista.getValorTabla(filaSeleccionada, 4);
        String titulo = vista.getValorTabla(filaSeleccionada, 5);
        String fechaPublicacion = vista.getValorTabla(filaSeleccionada, 6);
        String isbn = vista.getValorTabla(filaSeleccionada, 7);
        String idPrestamo = vista.getValorTabla(filaSeleccionada, 8);
        String fechaPrestamo = vista.getValorTabla(filaSeleccionada, 9);
        String fechaDevolucion = vista.getValorTabla(filaSeleccionada, 10);

        // Solicitar nuevos valores para cada campo
        String nuevoNombre = vista.solicitarNuevoDato("Editar Nombre:", nombre);
        String nuevaNacionalidad = vista.solicitarNuevoDato("Editar Nacionalidad:", nacionalidad);
        String nuevaFechaNacimiento = vista.solicitarNuevoDato("Editar Fecha de Nacimiento (yyyy-MM-dd):", fechaNacimiento);
        String nuevoTitulo = vista.solicitarNuevoDato("Editar Título del Libro:", titulo);
        String nuevaFechaPublicacion = vista.solicitarNuevoDato("Editar Fecha de Publicación (yyyy-MM-dd):", fechaPublicacion);
        String nuevoIsbn = vista.solicitarNuevoDato("Editar ISBN:", isbn);
        String nuevaFechaPrestamo = vista.solicitarNuevoDato("Editar Fecha de Préstamo (yyyy-MM-dd):", fechaPrestamo);
        String nuevaFechaDevolucion = vista.solicitarNuevoDato("Editar Fecha de Devolución (yyyy-MM-dd):", fechaDevolucion);

        Connection conn = conexion.getConnection();

        if (conn != null) {
            try {
                // Actualizar autor
                String sqlAutor = "UPDATE autores SET nombre = ?, nacionalidad = ?, fecha_nacimiento = ? WHERE id_autor = ?";
                PreparedStatement pstmtAutor = conn.prepareStatement(sqlAutor);
                pstmtAutor.setString(1, nuevoNombre);
                pstmtAutor.setString(2, nuevaNacionalidad);
                pstmtAutor.setString(3, nuevaFechaNacimiento);
                pstmtAutor.setString(4, idAutor);
                pstmtAutor.executeUpdate();

                // Actualizar libro
                String sqlLibro = "UPDATE libros SET titulo = ?, fecha_publicacion = ?, isbn = ? WHERE id_libro = ?";
                PreparedStatement pstmtLibro = conn.prepareStatement(sqlLibro);
                pstmtLibro.setString(1, nuevoTitulo);
                pstmtLibro.setString(2, nuevaFechaPublicacion);
                pstmtLibro.setString(3, nuevoIsbn);
                pstmtLibro.setString(4, idLibro);
                pstmtLibro.executeUpdate();

                // Actualizar préstamo
                String sqlPrestamo = "UPDATE prestamos SET fecha_prestamo = ?, fecha_devolucion = ? WHERE id_prestamo = ?";
                PreparedStatement pstmtPrestamo = conn.prepareStatement(sqlPrestamo);
                pstmtPrestamo.setString(1, nuevaFechaPrestamo);
                pstmtPrestamo.setString(2, nuevaFechaDevolucion);
                pstmtPrestamo.setString(3, idPrestamo);
                pstmtPrestamo.executeUpdate();

                // Actualizar la vista
vista.getTablaBiblioteca().setValueAt(nuevoNombre, filaSeleccionada, 1);
vista.getTablaBiblioteca().setValueAt(nuevaNacionalidad, filaSeleccionada, 2);
vista.getTablaBiblioteca().setValueAt(nuevaFechaNacimiento, filaSeleccionada, 3);
vista.getTablaBiblioteca().setValueAt(nuevoTitulo, filaSeleccionada, 5);
vista.getTablaBiblioteca().setValueAt(nuevaFechaPublicacion, filaSeleccionada, 6);
vista.getTablaBiblioteca().setValueAt(nuevoIsbn, filaSeleccionada, 7);
vista.getTablaBiblioteca().setValueAt(nuevaFechaPrestamo, filaSeleccionada, 9);
vista.getTablaBiblioteca().setValueAt(nuevaFechaDevolucion, filaSeleccionada, 10);
JOptionPane.showMessageDialog(vista, "Datos actualizados exitosamente.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(vista, "Error al actualizar los datos: " + e.getMessage());
            } finally {
                conexion.cerrarConexion();
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Error en la conexión a la base de datos.");
        }
    } 
        public void eliminarRegistro() {
    int filaSeleccionada = vista.getTablaBiblioteca().getSelectedRow();

    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(vista, "Por favor, seleccione una fila para eliminar.");
        return;
    }

    String idAutor = vista.getTablaBiblioteca().getValueAt(filaSeleccionada, 0).toString();
    String idLibro = vista.getTablaBiblioteca().getValueAt(filaSeleccionada, 4).toString();
    String idPrestamo = vista.getTablaBiblioteca().getValueAt(filaSeleccionada, 8).toString();

    Conexion conexion = new Conexion();
    Connection conn = conexion.getConnection();

    if (conn != null) {
        try {
            // Eliminar el préstamo, libro y autor en ese orden
            if (!idPrestamo.isEmpty()) {
                String sql = "DELETE FROM prestamos WHERE id_prestamo = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, idPrestamo);
                pstmt.executeUpdate();
            }

            if (!idLibro.isEmpty()) {
                String sql = "DELETE FROM libros WHERE id_libro = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, idLibro);
                pstmt.executeUpdate();
            }

            if (!idAutor.isEmpty()) {
                String sql = "DELETE FROM autores WHERE id_autor = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, idAutor);
                pstmt.executeUpdate();
            }

            ((DefaultTableModel) vista.getTablaBiblioteca().getModel()).removeRow(filaSeleccionada);
            JOptionPane.showMessageDialog(vista, "Datos eliminados exitosamente.");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vista, "Error al eliminar los datos: " + e.getMessage());
        } finally {
            conexion.cerrarConexion();
        }
    } else {
        JOptionPane.showMessageDialog(vista, "Error en la conexión a la base de datos.");
   
    }
    
}
public void listarDatos() {
    DefaultTableModel modeloTabla = (DefaultTableModel) vista.getTablaBiblioteca().getModel();
    Conexion conexion = new Conexion();
    Connection conn = conexion.getConnection();

    if (conn != null) {
        try {
            // Limpiar la tabla antes de llenarla
            modeloTabla.setRowCount(0);

            // Consulta SQL
            String sql = "SELECT a.id_autor, a.nombre, a.nacionalidad, a.fecha_nacimiento, "
                       + "l.id_libro, l.titulo, l.fecha_publicacion, l.isbn, "
                       + "p.id_prestamo, p.fecha_prestamo, p.fecha_devolucion "
                       + "FROM autores a "
                       + "JOIN libros l ON a.id_autor = l.id_autor "
                       + "JOIN prestamos p ON l.id_libro = p.id_libro";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            // Llenar la tabla con los datos obtenidos
            while (rs.next()) {
                Object[] fila = new Object[11];
                fila[0] = rs.getInt("id_autor");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("nacionalidad");
                fila[3] = rs.getDate("fecha_nacimiento");
                fila[4] = rs.getInt("id_libro");
                fila[5] = rs.getString("titulo");
                fila[6] = rs.getDate("fecha_publicacion");
                fila[7] = rs.getString("isbn");
                fila[8] = rs.getInt("id_prestamo");
                fila[9] = rs.getDate("fecha_prestamo");
                fila[10] = rs.getDate("fecha_devolucion");

                modeloTabla.addRow(fila);
            }
            
            JOptionPane.showMessageDialog(vista, "Datos listados exitosamente.");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vista, "Error al listar los datos: " + e.getMessage());
        } finally {
            conexion.cerrarConexion();
        }
    } else {
        JOptionPane.showMessageDialog(vista, "Error en la conexión a la base de datos.");
    }
}
public void crearPDF() {
    Document documento = new Document();

    try {
        String ruta = System.getProperty("user.home") + "/OneDrive/Escritorio/Reporte_Biblioteca.pdf"; // Ruta del escritorio
        PdfWriter.getInstance(documento, new FileOutputStream(ruta));
        documento.open();

        PdfPTable tabla = new PdfPTable(4); // Cambia el número de columnas según tu necesidad
        tabla.addCell("Id Autor");
        tabla.addCell("Nombre");
        tabla.addCell("Nacionalidad");
        tabla.addCell("Fecha de Nacimiento");

        try {
            // Usar la clase Conexion para obtener la conexión
            Conexion conexion = new Conexion();
            Connection cn = conexion.getConnection();

            PreparedStatement pst = cn.prepareStatement("SELECT * FROM autores");
            ResultSet rs = pst.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(vista, "No hay datos en la tabla de autores.");
            } else {
                do {
                    tabla.addCell(String.valueOf(rs.getInt("id_autor")));
                    tabla.addCell(rs.getString("nombre"));
                    tabla.addCell(rs.getString("nacionalidad"));
                    tabla.addCell(rs.getString("fecha_nacimiento"));
                } while (rs.next());

                documento.add(tabla);
                JOptionPane.showMessageDialog(vista, "Reporte creado en: " + ruta);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vista, "Error al obtener datos: " + e.getMessage());
        }
    } catch (DocumentException | FileNotFoundException e) {
        JOptionPane.showMessageDialog(vista, "Error al crear el PDF: " + e.getMessage());
    } finally {
        documento.close();
    }
}
}