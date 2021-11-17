/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epsi.gosecuri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author loicb
 */
public class Generator {
    
    //Chemin relatif pour le dossier ressource
    private final String ressourceDirPath = "src/main/java/com/epsi/gosecuri/ressourceFiles/";
    private final String htmlDirPath = "src/main/java/com/epsi/gosecuri/htmlFiles/";
    private final String generatedFilesDirPath = "src/main/java/com/epsi/gosecuri/generatedFiles/";
    private final String staffFile = "staff.txt";
    private final String stuffFile = "liste.txt";
    
    private ArrayList<Agent> agentList = new ArrayList<>();
    private ArrayList<String> agentNameList = new ArrayList<>();
    private HashMap<String,String> stuffList = new HashMap<>();
    
    public Generator() throws IOException{
        this.readStuffFile();
        this.readStaffFile();
        this.readAgentFile();
        this.createAgentFilePage();
        this.createHomePage();
    }
    
    /**
     * Créer la page d'accueil.
     */
    private void createHomePage(){
        try{
            //Récupération du template html
            File htmlTemplateFile = new File(this.htmlDirPath+"template.html");
            String htmlString = FileUtils.readFileToString(htmlTemplateFile);
            
            //Initialisation des variables avec le contenu à ajouter
            String title = "Accueil";
            
            String header = "<h1><a href=\"index.html\">" +
                                "<font size=\"6\"" +
                                " face=\"verdana\"" +
                                " color=\"white\">" +
                                    "Accueil" +
                                "</font>" +
                            "</a></h1>";
            
            String htmlAgentList = this.createHtmlAgentList();
            String body = "<div style=\"margin-left: 40%; margin-top: 10%; margin-right: 2%; width: 50%;\" class=\"container border border-success\">"+
                    htmlAgentList+"</div>";
            
            //Ajoute le contenu dans la page Html
            htmlString = htmlString.replace("$title", title);
            htmlString = htmlString.replace("$logo", "..//ressourceFiles//GoSecuri.png");
            htmlString = htmlString.replace("$header", header);
            htmlString = htmlString.replace("$body", body);
            
            //Création du fichier Html
            File newHtmlFile = new File(this.generatedFilesDirPath+"index.html");
            FileUtils.writeStringToFile(newHtmlFile, htmlString);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    /**
     * Créer une liste Html avec des liens pour chaque agent
     * @return 
     */
    private String createHtmlAgentList(){
        String res = "<h3>Liste des agents</h3>";
        res += "<div class=\"container\">"
                + "<div class=\"row\">"
                + "<ul class=\"col align-self-center\">";
        for(Agent agent:this.agentList){
            res += "<li><a href=\""+agent.getFileName()+".html\">"+agent.getIdentity()+"</a></li>";
        }
        return res+"<ul></div></div>";
    }
    
    /**
     * Créer les différentes fiches d'agents
     */
    private void createAgentFilePage(){
        for(Agent agent:this.agentList){
            try{
                //Récupération du template html
                File htmlTemplateFile = new File(this.htmlDirPath+"template.html");
                String htmlString = FileUtils.readFileToString(htmlTemplateFile);

                //Initialisation des variables avec le contenu à ajouter
                String title = agent.getIdentity()+" - Fiche agent";

                String header = "<h1><a href=\"index.html\">" +
                                    "<font size=\"6\"" +
                                    " face=\"verdana\"" +
                                    " color=\"white\">" +
                                        "Accueil" +
                                    "</font>" +
                                "</a></h1>";

                String body = this.createAndLoadStuffList(agent);

                //Ajoute le contenu dans la page Html
                htmlString = htmlString.replace("$title", title);
                htmlString = htmlString.replace("$logo", "GoSecuri.png");
                htmlString = htmlString.replace("$header", header);
                htmlString = htmlString.replace("$body", body);

                //Création du fichier Html
                File newHtmlFile = new File(this.generatedFilesDirPath+agent.getFileName()+".html");
                FileUtils.writeStringToFile(newHtmlFile, htmlString);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Créer une liste de checkBox initialiser 
     * avec les valeurs de l'agent passer en paramètre
     * @param agent
     * @return 
     */
    private String createAndLoadStuffList(Agent agent){
        String htmlString=agent.generateAgentFile();
        ArrayList<String> agentStuffList = agent.getStuffList();
        String stuffList="<ul>";
        int index=0;
        for(String stuff:this.stuffList.keySet()){
            if(agentStuffList.contains(this.stuffList.get(stuff))){
                stuffList += "<li>\n" +
"                        \n" +
"                        <input class=\"form-check-input bg-success\" type=\"checkbox\" value=\"\" id=\"defaultCheck"+index+"\" disabled checked>\n" +
"                        <label class=\"form-check-label\" for=\"defaultCheck"+index+"\">\n" +
"                            "+this.stuffList.get(stuff)+"\n" +
"                        </label>\n" +
"                          \n" +
"                    </li>";
            }else{
                stuffList += "<li>\n" +
"                        \n" +
"                        <input class=\"form-check-input bg-success\" type=\"checkbox\" value=\"\" id=\"defaultCheck"+index+"\" disabled>\n" +
"                        <label class=\"form-check-label\" for=\"defaultCheck"+index+"\">\n" +
"                            "+this.stuffList.get(stuff)+"\n" +
"                        </label>\n" +
"                          \n" +
"                    </li>";
            }
            index++;
        }
        htmlString = htmlString.replace("$stufflist", stuffList);
        return htmlString;
    }
    
    /**
     * Créer la liste d'agent
     */
    private void readAgentFile(){
        //Variable pour la création d'un agent
        String nom = "";
        String prenom = "";
        String password = "";
        String mission= "";
        ArrayList<String> stuffList;
        
        //On créer un agent pour chaque Nom dans la liste this.agentNameList
        for(String name:this.agentNameList){
            try{
                stuffList = new ArrayList<>();
                
                // Le fichier d'entrée
                File lcAgentFile = new File(this.ressourceDirPath+name+".txt"); // Pour release
                
                // Créer l'objet File Reader
                FileReader fr = new FileReader(lcAgentFile);
                // Créer l'objet BufferedReader        
                BufferedReader br = new BufferedReader(fr);

                String line;
                int index = 0;
                //On boucle jusqu'a la fin du fichier
                while((line = br.readLine()) != null)
                {
                    //On saute les lignes vides
                    if(line != null || !line.isEmpty() || line.trim().isEmpty()){
                        //On rempli les différentes variables avec les 4 premières lignes
                        // et créer une liste de matériel avec les lignes restantes
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
    private void readStuffFile(){
        try{
            // Le fichier d'entrée
            File lcStaffFile = new File(this.ressourceDirPath+this.stuffFile); // Pour releas
            // Créer l'objet File Reader
            FileReader fr = new FileReader(lcStaffFile);
            // Créer l'objet BufferedReader        
            BufferedReader br = new BufferedReader(fr);

            String line;
            //On boucle jusqu'a la fin du fichier
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
    private void readStaffFile(){
        try{
            // Le fichier d'entrée
            File lcStaffFile = new File(this.ressourceDirPath+this.staffFile); // Pour release
            // Créer l'objet File Reader
            FileReader fr = new FileReader(lcStaffFile);
            // Créer l'objet BufferedReader        
            BufferedReader br = new BufferedReader(fr);

            String line;
            //On boucle jusqu'a la fin du fichier
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
