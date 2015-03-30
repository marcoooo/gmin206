/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univ.montp.gmin206.sequence;

/**
 *
 * @author Marc
 */
public class Exon {
    
    public int start;
    public int stop;
    public int length;
    private Sequence sequence;
    
    public String getExonSequence(){
        return sequence.getSeqRaw().substring(start, stop);
    }

    public Exon(int start, int stop, int length, Sequence sequence) {
        this.start = start;
        this.stop = stop;
        this.length = length;
        this.sequence = sequence;
    }
    
    
    
}
