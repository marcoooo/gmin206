/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univ.montp.gmin206.results;

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
 * @author Marc
 */
public class OrthologAlign {

    String orthologGene;
    ArrayList<Sequence> sequences;

    protected Profile<DNASequence, NucleotideCompound> profile;

    public OrthologAlign(String orthologGene) {
        this.orthologGene = orthologGene;
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
     */
    public Element createResultXML() {
        Element root = new Element("output");
        Element current = new Element("Gene");
        current.setText(this.orthologGene);
        root.addContent(current);
        current = new Element("Alignement");
        current.setAttribute("count", String.valueOf(this.sequences.size()));
        current.setAttribute("method", "clustalw");
        current.setText(this.profile.toString());
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
            sortie.output(new Document(root), new FileOutputStream("outResult.xml"));
        } catch (java.io.IOException e) {
        }
    }

}
