/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univ.montp.gmin206.sequence;

import org.univ.montp.gmin206.sequence.Organism;

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


}
