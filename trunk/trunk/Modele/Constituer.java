/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.ArrayList;

/**
 * Gère un cache pour la table CONSTITUER
 * @author Mathieu PASSENAUD
 * @version 1.0
 *
 */
public class Constituer {

    public int id_fichier;
    public int id_code;
    public static int nb = 0;
    private static ArrayList<Constituer> cache = new ArrayList<Constituer>();
    public int sit;
    private static ThreadVideCacheConstituer t = null;

    /**
     * ajoute au cache l'entrée
     * si le cache atteind 200 entrées, il est vidé
     * @param numéro defichier
     * @param numéro du code
     * @param emplacement dans le fichier
     */
    public Constituer(int fichier, int code, int sit) {
        this.id_code = code;
        this.id_fichier = fichier;
        this.sit = sit;
        cache.add(this);
        nb++;
        if (nb > Parametres.tailleCache) {
            if (t != null) {
                while (t.isAlive()) {
                }
            }
            ThreadVideCacheConstituer t = new ThreadVideCacheConstituer(cache);
            cache = new ArrayList<Constituer>();
            t.start();
            nb = 0;
        }
    }
    
    /**
     * Vide le cache dans la base de données
     */
    public static void viderCache() {
        if (t != null) {
            while (t.isAlive()) {
            }
        }
        t = new ThreadVideCacheConstituer(cache);
        t.run();
        while (t.isAlive()) {
        }
        cache.clear();
    }
}
