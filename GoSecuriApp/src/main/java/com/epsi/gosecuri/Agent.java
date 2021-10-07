/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epsi.gosecuri;

import java.util.ArrayList;

/**
 *
 * @author loicb
 */
public class Agent {
    
    private String nom;
    private String prenom;
    private String photoPath;
    private String password;
    private String mission;
    private ArrayList<String> stuffList;

    public Agent(String nom, String prenom, String password, String mission, ArrayList<String> stuffList) {
        this.nom = nom;
        this.prenom = prenom;
        this.photoPath = prenom.toCharArray()[0]+nom+".jpg";
        this.photoPath = this.photoPath.toLowerCase();
        this.password = password;
        this.mission = mission;
        this.stuffList = stuffList;
    }
    
    public void generateAgentFile(){
        
    }
    
    public String getIdentity(){
        return this.nom+" "+this.prenom;
    }
    
    public String toString(){
        String res;
        res = "Nom: "+this.nom+" Prenom: "+this.prenom;
        res += "\n mdp: "+this.password;
        res += "\n mission: "+this.mission;
        res += "\n Liste de matériel: "+this.stuffList.toString();
        res += "\n Chemin de la photo: "+this.photoPath;
        return res;
    }
    
}
