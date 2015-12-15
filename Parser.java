/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author akhfa
 */
public class Parser {

    public ArrayList getAllAuthor (String filename)
    {
        ArrayList<String> allAuthor = new ArrayList<>();
        // The name of the file to open.
        String fileName = filename;

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                if(line.contains("author"))
                {
                    line = line.substring(8, line.indexOf("</a", 7));
                    //System.out.println(line);
                    allAuthor.add(line);
                }   
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
        return allAuthor;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Parser parser = new Parser();
        ArrayList Authors = parser.getAllAuthor("dblp-short.xml");
        for(Object Author : Authors)
        {
            System.out.println(Author.toString());
        }
    }
    
}
