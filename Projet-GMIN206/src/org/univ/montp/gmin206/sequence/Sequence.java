/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univ.montp.gmin206.sequence;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic Sequence description
 * @author mchakiachvil
 * @author cagret
 */
abstract public class Sequence {
    
    
    protected String accessionNumber;
    
    protected long gi;

    protected int seqLength;
    
    protected String seqRaw;
    
    protected Organism organism;
    private ArrayList<Exon> exons = new ArrayList<>();

    public String getAccessionNumber() {
        return accessionNumber;
    }

    public void setOrganism(Organism organism) {
        this.organism = organism;
    }

    public Organism getOrganism() {
        return organism;
    }

    public int getSeqLength() {
        return seqLength;
    }

    public long getGi() {
        return gi;
    }

    public String getSeqRaw() {
        return seqRaw;
    }

    public void setAccessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }

    public void setGi(long gi) {
        this.gi = gi;
    }

    public void setSeqLength(int seqLength) {
        this.seqLength = seqLength;
    }

    public void setSeqRaw(String seqRaw) {
        this.seqRaw = seqRaw;
    }

    public ArrayList<Exon> getExons() {
        return this.exons;
    }

    public void setExons(ArrayList<Exon> exons) {
        this.exons = exons;
    }

    

}
