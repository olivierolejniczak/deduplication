/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import vue.Principale;

/**
 * Découpe le fichier et transmet les blocs. Le temps de la découpe la fenêtre "vue" est masquée
 * @author Mathieu PASSENAUD
 * @version 1.0
 */
public class Fichier {

    private String proprietaire;
    private int id_fichier;
    private File fichier;
    FileInputStream fi;
    private Principale vue;
    byte[] b = new byte[Parametres.tailleBlocs * 1];
    int off;

    /**
     *
     * @param proprietaire du fichier
     * @param nom du fichier
     * @param numéro du fichier dans la base
     * @param fenêtre graphique
     */
    public Fichier(String proprietaire, File fichier, int id, Principale vue) {
        this.fichier = fichier;
        id_fichier = id;
        this.proprietaire = proprietaire;
        this.vue = vue;
        vue.setVisible(false);
    }

    /**
     * Découpe de fichier en bloc puis enregistre chaque bloc dans la BDD
     * @return
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public void enregistrer() throws FileNotFoundException, IOException {
        fi = new FileInputStream(this.fichier);
        off = 0;
        long taille = this.fichier.length();
        taille = taille / Parametres.tailleBlocs;
        int lus = 0;
        while (lus != -1) {
            lus = fi.read(b);
            if (lus == -1) {
                break;
            }
            byte[] bytes = new byte[lus];
            for (int j = 0; j < lus; j++) {
                bytes[j] = b[j];
            }
            new Enregistrement(bytes, this.id_fichier, off).run();
            off++;
        }
        fi.close();
        vue.setVisible(true);
    }
}
