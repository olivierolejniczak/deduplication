/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Mathieu PASSENAUD
 * @version 1
 * charge le driver mysql
 */
public class BaseDeDonneesMysql {

    public BaseDeDonneesMysql() {
        try {

            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            return;
        }
        String url = Parametres.url, mdp = Parametres.motdepasse, user = Parametres.nom;
        try {
            BaseDeDonnees.con = (Connection) DriverManager.getConnection("jdbc:mysql://" + url, user, mdp);
        } catch (SQLException ex) {
            return;
        }
    }
}
