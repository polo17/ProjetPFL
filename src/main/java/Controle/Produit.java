/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

/**
 *
 * @author pedago
 */
public class Produit {

    private String description;
    private int quantite;
    private int prix;
    private String date;
    private String companie;
    private int total;
    
    public Produit (String des, int q, int p, String dat, String c, int t){
        this.description = des;
        this.quantite = q;
        this.prix = p;
        this.date = dat;
        this.companie = c;
        this.total = t;
    }
    
}
