/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univ.montp.gmin206;

import org.univ.montp.gmin206.sequence.Sequence;
import java.io.File;
import org.univ.montp.gmin206.sequence.TinySeq;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jdom2.JDOMException;
import org.univ.montp.gmin206.sequence.EnumSequenceType;

/**
 * GMIN 206 XML Treatment Main Class
 * @author mchakiachvil
 * @author cagret
 */
public class TPXML {
    
    protected static final String XML_FILE_PATTERN = "(\\w+)_(bioseq|gbc|tiny)\\.xml";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        
        try {
            Pattern p = Pattern.compile(XML_FILE_PATTERN);
            
            System.out.println("**************************** GMIN206 ***************************");
            System.out.println("* Please enter Xml file path (type space enter for default value) : ");
            String fileName = in.next();
            Matcher mat = p.matcher(fileName);
            if (! mat.matches()) {
                System.out.println("Wrong format :-( \n Expected format is XXXXX_bioseq|gbc|tiny.xml");
                System.exit(0);
            }
            System.out.println(" => You entered " + fileName + " as file name ");
            File theFile = new File("files/" + fileName);
            EnumSequenceType type = EnumSequenceType.valueOf(mat.group(2).toUpperCase());
            System.out.println("Open file " + theFile.getAbsolutePath() + " fileType : " + mat.group(2).toUpperCase()   ); // renvoie SeqSet
            ArrayList<Sequence> resList = XMLSeqParser.parseFile(theFile, type);
            String[] rawSeq = new String[resList.size()];
            int i=0;
            for (Sequence seq : resList) {
                System.out.println("current Sequence added to alignment " + seq.getSeqRaw());
                rawSeq[i] = seq.getSeqRaw(); 
                i++;
            }
            TinyAlignment.multipleSequenceAlignment(rawSeq);
            
        } catch (IOException ex) {
            Logger.getLogger(TPXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JDOMException ex) {
            Logger.getLogger(TPXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}