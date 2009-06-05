/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Vide le cache dans la base de données en parallèle du traitement
 * @author Mathieu PASSENAUD
 * @version 0.8
 */
public class ThreadVideCacheDonnees extends Thread {
    ArrayList<Donnees> cache;
    int index = 0;
    int nbdonnees = 0;
    String requete;
    /**
     * 
     * @param cache
     */
    public ThreadVideCacheDonnees(ArrayList<Donnees> cache) {
        this.cache = cache;

    }

    /**
     * Lance les enregistrements dans la base sous forme d'insertions à 50 données par requête.
     * Vide le cache de Donnees d'un coup une fois toutes les insertions effectuées
     */
    @Override
    public void run() {
        index = 0;
        nbdonnees = 0;
        requete = "";
        System.out.println("vide cache donnees");
        PreparedStatement ps = null;
        for (int i = 0; i < cache.size(); i++) {
            try {
                ps = (PreparedStatement) BaseDeDonnees.con.prepareStatement("insert into code (id_code, code, md5) values (?, ?, ?)");
            } catch (SQLException ex) {
                Logger.getLogger(ThreadVideCacheDonnees.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                ps.setInt(1, cache.get(i).id);
            } catch (SQLException ex) {
                Logger.getLogger(ThreadVideCacheDonnees.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                ps.setBytes(2, cache.get(i).bytes);

            } catch (SQLException ex) {
                Logger.getLogger(ThreadVideCacheDonnees.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                ps.setString(3, cache.get(i).MD5);
            } catch (SQLException ex) {
                Logger.getLogger(ThreadVideCacheDonnees.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(ThreadVideCacheDonnees.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }
}
