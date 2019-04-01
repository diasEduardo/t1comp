/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import java.io.*;
/**
 *
 * @author nathan
 */
public class FileHanddler {
    public static String readFile(String path) {
        // This will reference one line at a time
        String line = null;
        String readedFile = "";
        try {
            // FileHanddler reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(path);

            // Always wrap FileHanddler in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                readedFile += line + "\n";
            }   

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                path + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + path + "'");                  
        }
        
        return readedFile;
    }
}
