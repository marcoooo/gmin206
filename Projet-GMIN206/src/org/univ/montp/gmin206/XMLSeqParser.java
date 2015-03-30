/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univ.montp.gmin206;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.univ.montp.gmin206.sequence.EnumSequenceType;
import org.univ.montp.gmin206.sequence.GBCSeq;
import org.univ.montp.gmin206.sequence.Organism;
import org.univ.montp.gmin206.sequence.Sequence;
import org.univ.montp.gmin206.sequence.TinySeq;

/**
 * Basic TinySeq xml format parser
 *
 * @author mchakiachvil
 * @author cagret
 */
public class XMLSeqParser {
    
   
    private static TinySeq parseTinyElement(Element x) throws JDOMException {
        Organism org = new Organism(x.getChildTextNormalize("TSeq_orgname"), Integer.parseInt(x.getChildText("TSeq_taxid")));
        TinySeq tSeq = new TinySeq();
        tSeq.setSeqLength(Integer.parseInt(x.getChildText("TSeq_length")));
        tSeq.setSeqRaw(x.getChildText("TSeq_sequence"));
        tSeq.setSeqType(x.getChild("TSeq_seqtype").getAttributeValue("value"));
        tSeq.setAccessionNumber(x.getChildText("TSeq_accver"));
        tSeq.setOrganism(org);
        tSeq.setSeqDefinition(x.getChildTextNormalize("TSeq_defline"));
        tSeq.setGi(Long.parseLong(x.getChildText("TSeq_gi")));
        return tSeq;
    }
    
    public static ArrayList<Sequence> parseFile(File theFile, EnumSequenceType seqType) throws IOException, JDOMException {
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(new FileInputStream(theFile.getAbsolutePath()));
        Element root = doc.getRootElement();
        System.out.println(root.getName()); // renvoie SeqSet
        ArrayList<Sequence> listSeq = new ArrayList<Sequence>();
        Sequence seq = null;
        List<Element> list = root.getChildren();
        if  (seqType == EnumSequenceType.BIOSEQ) { 
            list = root.getChild("Bioseq-set_seq-set").getChildren();
        } 
        for (Element x : list) {
            switch (seqType) {
                case TINY:
                    seq = parseTinyElement(x);
                    break;
                case BIOSEQ:
                    throw new UnsupportedOperationException("Not yet implemented");
                   // seq = parseBioSeqElement(x);
                   // break;
                case GBC:
                    seq = parseGbcElement(x);
                    break;
            }
            listSeq.add(seq);
        }
        System.out.println("----- Sequences input length: " + listSeq.size());
        return listSeq;
    } 
    

    private static Sequence parseBioSeqElement(Element x) {
        /*Element y = x.getChild("Seq-entry_set").getChild("Bioseq-set");
        List<Element> listDsr = y.getChild("Bioseq-set_descr").getChild("Seq-descr").getChildren();
        for (Element z : listDsr) {
            if (z.getChild("Seqdesc_source") != null) {
                if (z.getChild("Seqdesc_source").getChild("BioSource") != null) {
                    z.
                }
            }        
        }
        Organism org = new Organism(x.getChildTextNormalize("INSDSeq_organism"), -1);
        BioSeq tSeq = new BioSeq();
        tSeq.setSeqLength(Integer.parseInt(x.getChild("Bioseq_inst").getChild("Seq-inst").getChildText("Seq-inst_length"))); 
        //XPathBuilder xpathBuilder = new XPathBuilder("//");
        tSeq.setSeqRaw(x.getChild("Seq-inst"));
        tSeq.setAccessionNumber(x.getChildText("INSDSeq_primary-accession"));
        tSeq.setOrganism(org);
        return tSeq;*/
    throw new UnsupportedOperationException("Not yet implemented");
    }

    private static Sequence parseGbcElement(Element x) {
        //Element y = x.getChild("INSDSeq").getChild("INSDSeq_feature-table");
      	Element featureTab = x.getChild("INSDSeq_feature-table");
            List<Element> listDsr = featureTab.getChildren();
		for (Element z : listDsr) { 
			if (z.getChildText("INSDFeature_key").equals("exon")) {
				Element interval = z.getChild("INSDFeature_intervals").getChild("INSDInterval");
				Element from = interval.getChild("INSDInterval_from");
				Element to = interval.getChild("INSDInterval_to");
			}	
		}
    		
        Organism org = new Organism(x.getChildTextNormalize("INSDSeq_organism"), -1);
        GBCSeq tSeq = new GBCSeq();
        tSeq.setSeqLength(Integer.parseInt(x.getChildText("INSDSeq_length")));
        tSeq.setSeqRaw(x.getChildText("INSDSeq_sequence"));
        tSeq.setAccessionNumber(x.getChildText("INSDSeq_primary-accession"));
        tSeq.setOrganism(org);
        return tSeq;
    }


}
