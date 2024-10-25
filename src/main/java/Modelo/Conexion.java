/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author miguel
 */
public class Conexion {
    private Connection conexion = null;

    // Parámetros de conexión
    private final String usuario = "miguel";
    private final String contraseña = "123";
    private final String db = "Biblioteca";
    private final String ip = "localhost";
    private final String puerto = "1433";

    // Método para obtener la conexión
    public Connection getConnection() {
        try {
            String cadena = "jdbc:sqlserver://" + ip + ":" + puerto + ";databaseName=" + db 
                    + ";user=" + usuario + ";password=" + contraseña 
                    + ";encrypt=false;trustServerCertificate=true;"; // Ajusta el cifrado
            conexion = DriverManager.getConnection(cadena);
            System.out.println("Conexión exitosa a la base de datos");
        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, 
                    "Error en la conexión: " + e.toString());
        }
        return conexion;
    }

    // Método para cerrar la conexión
    public void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada correctamente");
            } catch (SQLException e) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, 
                        "Error al cerrar la conexión: " + e.toString());
            }
        }
    }
}
