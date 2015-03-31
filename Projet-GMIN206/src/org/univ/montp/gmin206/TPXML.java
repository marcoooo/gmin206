/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univ.montp.gmin206;

import org.univ.montp.gmin206.sequence.Sequence;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jdom2.JDOMException;
import org.univ.montp.gmin206.results.OrthologAlign;
import org.univ.montp.gmin206.sequence.EnumSequenceType;

/**
 * GMIN 206 XML Treatment Main Class
 *
 * @author mchakiachvil
 * @author cagret
 */
public class TPXML {

    protected static final String XML_FILE_PATTERN = "(\\w+)_(bioseq|gbc|tiny)\\.xml";
    protected static final String inDir = "files/";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in)); 
        String fileName;
        try {
            Pattern p = Pattern.compile(XML_FILE_PATTERN);
            System.out.println("**************************** GMIN206 TP XML ***************************");
            File dir = new File(inDir);
            System.out.println("* Choose one of these file :");
            for (File f : dir.listFiles()){
                if (f.getName().endsWith(".xml")) {
                    System.out.println(". " + f.getName());
                }
            }
            System.out.print("* Please enter fileName (type space > enter for default \"sequence_gbc.xml\") : ");
            fileName = inputReader.readLine();
            System.out.println("You entered '" + fileName + "' as file name ");
            if (fileName == null || fileName.trim().length() == 0) {
                fileName = "sequence_gbc.xml";
            }
            File theFile = new File(inDir + fileName);
            Matcher mat = p.matcher(fileName);
            if (!mat.matches()) {
                System.out.println("Wrong format :-( \n Expected format is XXXXX_bioseq|gbc|tiny.xml");
                System.exit(0);
            }
            System.out.println("* NB if your file contains less than 15 sequences, process will align sequences.");
            EnumSequenceType type = EnumSequenceType.valueOf(mat.group(2).toUpperCase());
            System.out.println("Opening file " + theFile.getAbsolutePath() + " - fileType : " + mat.group(2).toUpperCase()); // renvoie SeqSet
            ArrayList<Sequence> resList = XMLSeqParser.parseFile(theFile, type);
            OrthologAlign align = new OrthologAlign(theFile.getName());
            align.setSequences(resList);
            if (resList.size() < 15) {
                align.multipleAlign();
            }
            align.writeXml();
        } catch (IOException | JDOMException ex) {
            Logger.getLogger(TPXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TPXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
