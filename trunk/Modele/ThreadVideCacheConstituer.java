/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Vide le cache dans la base de données en parallèle du traitement
 * @author Mathieu PASSENAUD
 * @version 0.9
 */
public class ThreadVideCacheConstituer extends Thread {

    private ArrayList<Constituer> cache = new ArrayList<Constituer>();

    public ThreadVideCacheConstituer(ArrayList<Constituer> cache) {
        this.cache = cache;
    }

    /**
     * Lance l'insertion dans la base sous forme de 50 données dans une requête
     * Vide le cache de Constituer au fur et à mesure.
     */
    @Override
    public void run() {
        String requete;
        System.out.println("Taille du cache en entrée : " + this.cache.size());
        for (int i = 0; i < this.cache.size(); i++) {
            requete = "insert into constituer (id_fichier, id_code, sit) values";
            requete = requete + " (" + this.cache.get(i).id_fichier + ", " + this.cache.get(i).id_code + ", " + this.cache.get(i).sit + ")";

            try {
                BaseDeDonnees.con.createStatement().execute(requete);
            } catch (SQLException ex) {
                Logger.getLogger(Donnees.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
