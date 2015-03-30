/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univ.montp.gmin206.sequence;

/**
 *
 * @author mchakiachvil
 * 
 */
public class TinySeq extends Sequence {

    protected String seqDefinition;

    protected String seqType;

    public String getSeqDefinition() {
        return seqDefinition;
    }
    public String getSeqType() {
        return seqType;
    }
    public void setSeqType(String seqType) {
        this.seqType = seqType;
    }
    public void setSeqDefinition(String seqDefinition) {
        this.seqDefinition = seqDefinition;
    }
    
    
}
