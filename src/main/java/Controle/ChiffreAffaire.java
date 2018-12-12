/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

/**
 * retourne un ChiffreAffaire composé d'un nom avec sa valeur
 * @author ludovic
 */
public class ChiffreAffaire {

    private String nom; //Nom de la variable du chiffre d'affaire (client, produit ou état)
    private double total; //Valeur du chiffre d'affaire
    //private String date;
    
    
    public ChiffreAffaire (String n, double tot){
        this.nom = n;
        this.total = tot;
    }
    
        public String getNom() {
        return nom;
    }

    public double getTotal() {
        return total;
    }
}
