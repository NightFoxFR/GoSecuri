/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epsi.gosecuri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author loicb
 */
public class Generator {
    
    private String mainDirPath;
    private final String  staffFile = "staff.txt";
    private final String  stuffFile = "liste.txt";
    
    private ArrayList<Agent> agentList = new ArrayList<>();
    private ArrayList<String> agentNameList = new ArrayList<>();
    private HashMap<String,String> stuffList = new HashMap<>();
    
    public Generator(/*String mainDirPath*/){
        //this.mainDirPath = mainDirPath;
    }
    
    public void createHomePage(){
        
    }
    
    public void createAgentFilePage(){
        
    }
    
    /**
     * Créer la liste d'agent
     */
    public void readAgentFile(){
        //Variable pour la création d'un agent
        String nom = "";
        String prenom = "";
        String password = "";
        String mission= "";
        ArrayList<String> stuffList;
        
        for(String name:this.agentNameList){
            try{
                stuffList = new ArrayList<>();
                
                // Le fichier d'entrée
                //File lcStaffFile = new File(this.mainDirPath+this.name); // Pour release
                File lcAgentFile = new File("C:\\Users\\loicb\\Documents\\NetBeansProjects\\gosecuri\\GoSecuriApp\\src\\main\\java\\filesForTest\\"+name+".txt"); //Pour debug
                
                // Créer l'objet File Reader
                FileReader fr = new FileReader(lcAgentFile);
                 // Créer l'objet BufferedReader        
                BufferedReader br = new BufferedReader(fr);

                String line;
                int index = 0;
                
                while((line = br.readLine()) != null)
                {
                    if(line != null || !line.isEmpty() || line.trim().isEmpty()){
                        switch(index){
                            case 0:
                                nom = line;
                                break;
                            case 1:
                                prenom = line;
                                break;
                            case 2:
                                mission = line;
                                break;
                            case 3:
                                password = line;
                                break;
                            default:
                                stuffList.add(this.stuffList.get(line.trim()));
                                break;
                        }
                        index++;
                    }
                }
                fr.close(); 
                Agent agent = new Agent(nom,prenom,password,mission,stuffList);
                this.agentList.add(agent);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        
        //Ligne ci dessous uniquement pour debug
        System.out.println(this.agentList.toString());
    }
    
    /**
     * Créer la liste des différents matériels
     */
    public void readStuffFile(){
        try{
            // Le fichier d'entrée
            //File lcStaffFile = new File(this.mainDirPath+this.stuffFile); // Pour release
            File lcStuffFile = new File("C:\\Users\\loicb\\Documents\\NetBeansProjects\\gosecuri\\GoSecuriApp\\src\\main\\java\\filesForTest\\liste.txt"); //Pour debug
            // Créer l'objet File Reader
            FileReader fr = new FileReader(lcStuffFile);
             // Créer l'objet BufferedReader        
            BufferedReader br = new BufferedReader(fr);

            String line;
            while((line = br.readLine()) != null)
            {
                String name = line.split("\\s+")[0].trim();
                String value = "";
                
                //Si le nom complet du matériel est séparé par des espaces
                //on boucle jusqu'au dernier mot de la ligne
                //sinon on prend le mot après l'identifiant 
                if(line.split("\\s+").length>2){
                    for(int i=1;i<line.split("\\s+").length;i++){
                        value += line.split("\\s+")[i]+" ";
                    }
                }
                else{
                    value = line.split("\\s+")[1];
                }
                this.stuffList.put(name, value);
            }
            fr.close(); 
            
            //Ligne ci dessous uniquement pour debug
            System.out.println(this.stuffList.toString());
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    /**
     * Créer la liste des différents agents
     */
    public void readStaffFile(){
        try{
            // Le fichier d'entrée
            //File lcStaffFile = new File(this.mainDirPath+this.staffFile); // Pour release
            File lcStaffFile = new File("C:\\Users\\loicb\\Documents\\NetBeansProjects\\gosecuri\\GoSecuriApp\\src\\main\\java\\filesForTest\\staff.txt"); //Pour debug
            // Créer l'objet File Reader
            FileReader fr = new FileReader(lcStaffFile);
             // Créer l'objet BufferedReader        
            BufferedReader br = new BufferedReader(fr);

            String line;
            while((line = br.readLine()) != null)
            {
                this.agentNameList.add(line);
            }
            fr.close(); 
            
            //Ligne ci dessous uniquement pour debug
            System.out.println(this.agentNameList.toString());
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
