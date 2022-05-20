package com.company;

import java.util.List;

public class Bayes {
    private final List<String> attributesColumn;
    private  String decision;

    public Bayes(List<String> attributesColumn, String decision) {
        this.attributesColumn = attributesColumn;
        this.decision = decision;
    }

    public Bayes(List<String> attributesColumn) {
        this.attributesColumn = attributesColumn;
    }

    public List<String> getAttributesColumn() {
        return attributesColumn;
    }

    public String getDecision() {
        return decision;
    }

    @Override
    public String toString() {
        if(decision==null){
            return "Bayes: "+
                    "attributes=" + attributesColumn ;
        }else{
            return "Bayes: "+
                    "attributes=" + attributesColumn +
                    ", decision=" + decision + '\'';
        }
    }
}
