/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

/**
 * Chaque entrée du cache id_code/MD5
 * @author Mathieu PASSENAUD
 * @version 1.0
 */
class Index {

    public int index_code;
    public String md5;

    public Index(int aInt, String string) {
        this.index_code = aInt;
        this.md5 = string;
    }
}
