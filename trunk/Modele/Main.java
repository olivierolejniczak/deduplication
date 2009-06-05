/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import vue.Principale;
import vue.VueParametres;

/**
 *
 * @author Mathieu PASSENAUD
 * @version 0.6
 */
public class Main {

    private static Fichier[] f = new Fichier[Parametres.nbThreads];
    private static int id = 2;

    /**
     * liste le contenu d'un répertoire, enregistre les fichiers et appel récursif sur sous-répertoire
     * @param directory
     */
    public static void lister(File directory, Principale vue) {
        File[] fichiers = directory.listFiles();
        for (int i = 0; i < fichiers.length; i++) {
            if (!fichiers[i].isDirectory()) {
                enregistrerFichier(fichiers[i], vue);
            } else {
                lister(fichiers[i], vue);
            }
        }
    }

    /**
     * Lance l'enregistrement d'un fichier
     * @param fichier
     */
    public static void enregistrerFichier(File fichier, Principale vue) {
        id = BaseDeDonnees.retournerProchainIdFichier();
        BaseDeDonnees.emplacement(fichier.getParent(), id);
        BaseDeDonnees.enregistrerNouveauFichier(fichier.getName(), id);
        try {
            new Fichier("Mathieu", fichier, id, vue).enregistrer();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (vue != null) {
            vue.actualiserAffichage();
        }
        Donnees.viderCache();
        Constituer.viderCache();
    }
    /**
     *
     * @param args
     * -f fichier : déduplique le fichier
     * -r fichier : restaure le fichier
     * -r numéro : restaure le fichier avec l'identifiant
     */
    public static void main(String args[]) {

        if (args.length > 0) {
            if (Parametres.initialiserParametres()) {
                System.out.println("Veuillez au préalable initialiser les paramètres de l'application");
            }
            if ("-f".compareTo(args[0]) == 0) {
                enregistrerFichier(new File(args[1]), null);
            }
            if ("-r".compareTo(args[0]) == 0) {
                BaseDeDonnees.restaurer(new File(args[1]));
            }
        } else {
            if (Parametres.initialiserParametres()) {
                new VueParametres().setVisible(true);
            } else {
                new Principale().setVisible(true);
            }
        }
    }
}
