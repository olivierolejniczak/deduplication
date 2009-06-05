/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Gère le cache des données
 * @author Mathieu PASSENAUD
 * @version 1.0
 */
public class Donnees {

    private static ArrayList<Donnees> cache = new ArrayList<Donnees>();
    public static ArrayList<Index> index = new ArrayList<Index>();
    public int id;
    public byte[] bytes;
    private static int id_courant = -1;
    private static int donneesInscrites = 0;
    private static ThreadVideCacheDonnees t = null;
    public String MD5;

    /**
     * Ajoute l'entrée au cache de données
     * @param données
     */
    public Donnees(byte[] b, String MD5) {
        if (id_courant == -1) {
            id_courant = BaseDeDonnees.retournerProchainIdCode();
        }
        id_courant++;
        this.id = id_courant;
        this.bytes = b;
        this.MD5 = MD5;
        index.add(new Index(id_courant, MD5));
        cache.add(this);
        donneesInscrites++;
    }

    /**
     * recherche les données dans le cache puis dans la base
     * Si les données n'existent pas, on le créé puis on les ajoute au cache.
     * Le cache est vidé automatiquement
     * @param données
     * @return numéro attribué à la donnée
     */
    public static int rechercherDonnee(byte[] b, String MD5) {
        if (donneesInscrites > Parametres.tailleCache) {
            System.out.println("vide le cache");
            if (t != null) {
                while (t.isAlive()) {
                }
            }
            t = new ThreadVideCacheDonnees(cache);
            t.run();
            cache.clear();
            donneesInscrites = 0;
        }

        for (int i = 0; i < index.size(); i++) {
            if (MD5.compareTo(index.get(i).md5) == 0) {
                return index.get(i).index_code;
            }
        }



        return new Donnees(b, MD5).id;
    }

    /**
     * Vide le cache en intégralité dans la base de données.
     */
    public static void viderCache() {
        if (t != null) {
            while (t.isAlive()) {
            }
        }
        t = new ThreadVideCacheDonnees(cache);
        t.run();
        while (t.isAlive()) {
        }
        cache.clear();
    }
}
