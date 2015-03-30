/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univ.montp.gmin206;

import java.util.ArrayList;
import java.util.List;
import org.biojava.nbio.alignment.Alignments;
import org.biojava.nbio.alignment.template.Profile;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.compound.NucleotideCompound;
import org.biojava.nbio.core.util.ConcurrencyTools;

/**
 *
 * @author mchakiachvil
 */
public class TinyAlignment {
 
    public static void multipleSequenceAlignment(String[] sequences) throws Exception {
        List<DNASequence> lst = new ArrayList<DNASequence>();
        for(String seq : sequences) {
            System.out.println(seq);
            lst.add(new DNASequence(seq));
        }
        Profile<DNASequence, NucleotideCompound> profile = Alignments.getMultipleSequenceAlignment(lst);
        System.out.printf("Clustalw:%n%s%n", profile);
        ConcurrencyTools.shutdown();
    }
}