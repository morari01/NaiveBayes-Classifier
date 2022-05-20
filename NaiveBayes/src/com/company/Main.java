package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static boolean exit = false;
    static List<Bayes> learningData = new ArrayList<>();
    static List<Bayes> testingData = new ArrayList<>();
    static List<String> decisionsList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        learningData = readFile("trainingset.csv", "training");
        testingData = readFile("testset.csv", "test");
        decisionsList = countPossibleAtr(0, "dec");

        for(Bayes b : testingData){
            System.out.println("Test data: " + b.getAttributesColumn() );
            Bayes(b.getAttributesColumn());
        }

        while (!exit){
            System.out.println("1 - enter the data for Bayes algorithm");
            System.out.println("2 - end");
            int menu;
            Scanner scanner = new Scanner(System.in);
            menu = scanner.nextInt();
            switch (menu){
                case 1: {
                    System.out.println("Enter data");
                    String data = scanner.next();
                    String [] split = data.split(",");
                    List <String> tmp = new ArrayList<>();
                    for (String s : split) tmp.add(String.valueOf(s));
                    System.out.println("Test data: " + tmp);
                    Bayes(tmp);
                }break;
                case 2: {
                    exit=true;
                    System.out.println("The end");
                }break;
            }
        }
    }


    public static void Bayes(List<String> test){
        String result = "";
        double productOfPossibilities;
        double res = 0.0;
        double possX;
        double allX = 1.0;

        for(int s =0; s<test.size(); s++){
            possX = countAttributes(test.get(s),s)/(double)learningData.size();
            allX *= possX;
        }

        for(String decision : decisionsList){
            productOfPossibilities = (countDecisionAtr(decision) / learningData.size()) / allX ;
            for(int i =0 ; i< test.size(); i++){
                productOfPossibilities *= countPossibility(test.get(i), i , decision);
            }
            System.out.println(productOfPossibilities + " " + decision);
            if(res<productOfPossibilities){
                res = productOfPossibilities;
                result = decision;
            }
        }
        System.out.println("Result:" + result );
        System.out.println();
    }


    public static double countPossibility(String attr, int i, String decision){
        double numerator = 0;
        double decisions = countDecisionAtr(decision);
        double res;

        for (Bayes learningDatum : learningData) {
            if (decision.equals(learningDatum.getDecision())
                    && attr.equals(learningDatum.getAttributesColumn().get(i))) {
                numerator++;
            }
        }
        res = numerator/decisions;
        if(res==0) return laplace(decisions, i) ;
        else
            return res;
    }

    public static double laplace(double denominator, int i){
        double newDenominator = denominator + countPossibleAtr(i, "attr").size();
        //System.out.println("Laplace " + 1/newDenominator);
        return 1/newDenominator;
    }


    public static List<String> countPossibleAtr(int i, String decORattr) {
        List<String> result = new ArrayList<>();
        if(decORattr.equals("dec")){
            for (Bayes b : learningData) {
                String atr = String.valueOf(b.getDecision());
                if (!result.contains(atr)) result.add(atr);
            }
        }
        else{
            for (Bayes b : learningData) {
                String atr = String.valueOf(b.getAttributesColumn().get(i));
                if (!result.contains(atr)) result.add(atr);
            }
        }
        return result;
    }

    public static double countAttributes(String attribute, int i) {
        double count = 0.0;
        for (Bayes b : learningData) {
            if (attribute.equals(String.valueOf(b.getAttributesColumn().get(i))))
                count++;
        }
        return count;
    }

    public static double countDecisionAtr(String desision) {
        double count = 0.0;
        for (Bayes b : learningData) {
            if (desision.equals(b.getDecision())) count++;
        }
        return count;
    }


    public static List<Bayes> readFile(String fileAddress, String testORtraining) throws IOException {
        List<Bayes> bayes = new ArrayList<>();
        FileReader fileReader = new FileReader(fileAddress);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine())!=null && (!line.equals(""))){
            String [] split = line.split(",");
            List<String> attributes = new ArrayList<>();
            if(testORtraining.equals("training")){
                for (int i = 0; i < split.length-1 ; i++)
                    attributes.add(String.valueOf(split[i]));
                bayes.add(new Bayes(attributes,split[split.length-1]));
            }
            else {
                for (String s : split) attributes.add(String.valueOf(s));
                bayes.add(new Bayes(attributes));
            }
        }
        return bayes;
    }

}
