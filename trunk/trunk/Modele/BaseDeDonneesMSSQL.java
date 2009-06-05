/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import com.microsoft.sqlserver.jdbc.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mathieu PASSENAUD
 * @version 0.1
 * Charge le driver MS SQL (non testé)
 */
public class BaseDeDonneesMSSQL {

    public BaseDeDonneesMSSQL() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BaseDeDonnees.class.getName()).log(Level.SEVERE, null, ex);
        }
        String url = Parametres.url, mdp = Parametres.motdepasse, user = Parametres.nom;
        try {
            BaseDeDonnees.con = (Connection) DriverManager.getConnection("jdbc:sqlserver://" + url, user, mdp);
        } catch (SQLException ex) {
            Logger.getLogger(BaseDeDonnees.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
