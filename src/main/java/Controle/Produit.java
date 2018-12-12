/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

/**
 * retourne un Produit composé de ses caractéristiques
 * @author pedago
 */
public class Produit {

    private String description;
    private int quantite;
    private double prix;
    private String date;
    private String companie;
    private double total;
    private int order;
    
    public Produit (String des, int q, double p, String dat, String c, double t, int o){
        this.description = des;
        this.quantite = q;
        this.prix = p;
        this.date = dat;
        this.companie = c;
        this.total = t;
        this.order = o;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCompanie() {
        return companie;
    }

    public void setCompanie(String companie) {
        this.companie = companie;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
    public int getOrder() {
        return order;
    }
    
}
