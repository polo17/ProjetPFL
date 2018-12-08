/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

/**
 *
 * @author ludovic
 */
public class ChiffreAffaire {

    private String nom;
    private double total;
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
