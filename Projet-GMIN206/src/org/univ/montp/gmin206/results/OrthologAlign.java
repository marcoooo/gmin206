/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univ.montp.gmin206.results;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.biojava.nbio.alignment.Alignments;
import org.biojava.nbio.alignment.template.Profile;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.compound.NucleotideCompound;
import org.biojava.nbio.core.util.ConcurrencyTools;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.univ.montp.gmin206.sequence.Exon;
import org.univ.montp.gmin206.sequence.Sequence;

/**
 *
 * @author MChakiachvili
 * @author CAgret
 */
public class OrthologAlign {

    String sourceFile;
    ArrayList<Sequence> sequences;

    protected Profile<DNASequence, NucleotideCompound> profile;

    public OrthologAlign(String source) {
        this.sourceFile = source;
    }

    /**
     * Perform a basic clustalw alignement based on given sequences
     *
     * @throws Exception
     */
    public void multipleAlign() throws Exception {
        List<DNASequence> lst = new ArrayList<>();
        for (Sequence seq : this.sequences) {
            lst.add(new DNASequence(seq.getSeqRaw()));
        }
        this.profile = Alignments.getMultipleSequenceAlignment(lst);
        System.out.printf("Clustalw:%n%s%n", profile);
        ConcurrencyTools.shutdown();
    }

    public Profile<DNASequence, NucleotideCompound> getProfile() {
        return profile;
    }

    @Override
    public String toString() {
        return profile.toString();
    }

    public void setSequences(ArrayList<Sequence> resList) {
        this.sequences = resList;
    }

    /**
     *
     * @return Element
     */
    public Element createResultXML() {
        Element root = new Element("output");
        Element current = new Element("FileName");
        current.setText(this.sourceFile);
        root.addContent(current);
        current = new Element("Alignement");
        current.setAttribute("count", String.valueOf(this.sequences.size()));
        current.setAttribute("method", "clustalw");
        if (this.profile != null) {
            current.setText(this.profile.toString());
        } else {
            current.setText("Alignement not done");
        }
        root.addContent(current);
        Element rtSeq = new Element("Sequences");
        for (Sequence seq : this.sequences) {
            Element sequence = new Element("Sequence");
            sequence.setAttribute("accNr", seq.getAccessionNumber());
            sequence.setAttribute("organism", seq.getOrganism().getName());
            List<Exon> exonsList = seq.getExons();
            Element exons = new Element("Exons");
            for (Exon ex : exonsList) {
                Element exon = new Element("Exon");
                exon.setAttribute("position", ex.start + " - " + ex.stop);
                exon.setAttribute("length", String.valueOf(ex.length));
                exon.setText(ex.getExonSequence());
                exons.addContent(exon);
            }
            sequence.addContent(exons);
            rtSeq.addContent(sequence);
        }
        root.addContent(rtSeq);
        return root;
    }

    public void writeXml() {
        Element root = this.createResultXML();
        try {
            //On utilise ici un affichage classique avec getPrettyFormat()
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            File out = new File("outResult.xml");
            sortie.output(new Document(root), new FileOutputStream(out.getAbsolutePath()));
            System.out.println("Output in " + out.getAbsolutePath());
        } catch (java.io.IOException e) {
            System.out.println("Exception " + e.getMessage());
        }
    }

}
