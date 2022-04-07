package com.example.pfr.controleur;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ControlRechercheFichier {
    //Le code utilisé ci-dessous provient d'un site
    //https://mkyong.com/java/search-directories-recursively-for-file-in-java/
    //Il a cependant été modifé pour coller aux besoins du projet

  private String fileNameToSearch;
  private List<String> result = new ArrayList<String>();
	
  public String getFileNameToSearch() {
	return fileNameToSearch;
  }

  public List<String> getResult() {
	return result;
  }

  public void searchDirectory(File directory, String fileNameToSearch) {
    this.fileNameToSearch = fileNameToSearch;
	if (directory.isDirectory()) {
	    search(directory);
	} 
    else {
	    //erreur on ne nous a pas passer un fichier
	}
  }

  private void search(File file){
    if (file.isDirectory()) {		
        if(file.canRead()) {
            for (File temp : file.listFiles()) {
                if (temp.isDirectory()) {
                    search(temp);
                } 
                else {
                    if (getFileNameToSearch().equals(temp.getName().toLowerCase())){			
                        result.add(temp.getAbsoluteFile().toString());
                    }
                }
            }
        }
        else {
            //nous n'avons pas le droit d'acceder à ce dossier
        }
    }
  }

  public void searchDirectory(File directory) {
	if (directory.isDirectory()) {
	    search(directory);
	} 
    else {
	    //erreur on ne nous a pas passer un fichier
	}
  }

  private void searchAll(File file){
    if (file.isDirectory()) {		
        if(file.canRead()) {
            for (File temp : file.listFiles()) {
                if (temp.isDirectory()) {
                    searchAll(temp);
                } 
                else {
                    result.add(temp.getAbsoluteFile().toString());
                }
            }
        }
        else {
            //nous n'avons pas le droit d'acceder à ce dossier
        }
    }
  }
  
  public void clearResult(){
      this.result.clear();
  }
    
}
