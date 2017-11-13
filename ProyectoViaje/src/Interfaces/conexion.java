/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Joc
 */
public class conexion {
    Connection connect=null;
    public Connection conectar(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect=DriverManager.getConnection("jdbc:mysql://localhost/viajes","root",""); // el primero es la base de datos y de donde esta la base ,el segundo el usuario ,y el tercero la contraseña 
           // JOptionPane.showMessageDialog(null, "Conecxion Correcta");
        } catch (Exception ex) {
             JOptionPane.showMessageDialog(null, ex);
             JOptionPane.showMessageDialog(null, "Fallo la Conecxion ");
        }
        return connect;
    }
    
}
