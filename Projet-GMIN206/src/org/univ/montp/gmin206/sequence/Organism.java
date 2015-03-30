/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univ.montp.gmin206.sequence;

/**
 *
 * @author mchakiachvil
 */
public class Organism {
    
    protected String name;
    
    protected int taxonId;

    public Organism(String name, int taxonId) {
        this.name = name;
        this.taxonId = taxonId;
    }

    public String getName() {
        return name;
    }
    
    
}