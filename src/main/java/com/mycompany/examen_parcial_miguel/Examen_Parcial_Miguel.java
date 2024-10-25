/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.examen_parcial_miguel;

import Vista.Biblioteca;
import modelo.Conexion;

/**
 * Clase principal para la ejecución del sistema de inventario
 * 
 * @author miguel
 */
public class Examen_Parcial_Miguel {

   public static void main(String args[]) {

       Conexion conexion = new Conexion();
       conexion.getConnection();
       
       try {
           // Configurar el Look and Feel HiFi de JTattoo
           javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
       } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
           java.util.logging.Logger.getLogger(Examen_Parcial_Miguel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
       }

       // Lanzar la ventana principal de Inventario
       java.awt.EventQueue.invokeLater(new Runnable() {
           public void run() {
               new Biblioteca().setVisible(true);
           }
       });
   }
}