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
 * @version 0.1
 * Charge le driver JDBC (ancien SEQUOIA)
 */
public class BaseDeDonneescjdbc {

    public BaseDeDonneescjdbc() {
        try {
            Class.forName("org.objectweb.cjdbc.driver.Driver").newInstance();
        } catch (Exception e) {
            System.out.println("ERROR: failed to load HSQLDB JDBC driver.");
            e.printStackTrace();
            return;
        }
        String url = Parametres.url, mdp = Parametres.motdepasse, user = Parametres.nom;
        try {
            BaseDeDonnees.con = (Connection) DriverManager.getConnection("jdbc:cjdbc://" + url, user, mdp);
        } catch (SQLException ex) {
            return;
        }
    }
}
