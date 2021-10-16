package cpen221.mp1.similarity;

import cpen221.mp1.Document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;
import java.net.URL;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import static java.lang.Character.isLetter;

public class DocumentSimilarity {

    /* DO NOT CHANGE THESE WEIGHTS */
    private final int WT_AVG_WORD_LENGTH      = 5;
    private final int WT_UNIQUE_WORD_RATIO    = 15;
    private final int WT_HAPAX_LEGOMANA_RATIO = 25;
    private final int WT_AVG_SENTENCE_LENGTH  = 1;
    private final int WT_AVG_SENTENCE_CPLXTY  = 4;
    private final int WT_JS_DIVERGENCE        = 50;
    /* ---- END OF WEIGHTS ------ */

    private final double[] w = new double[]{1.0, 4.0, 5.0, 15.0, 25.0};

    /* ------- Task 4 ------- */

    /**
     * Compute the Jensen-Shannon Divergence between the given documents
     * @param doc1 the first document, is not null
     * @param doc2 the second document, is not null
     * @return the Jensen-Shannon Divergence between the given documents
     */
    public double jsDivergence(Document doc1, Document doc2) {

        Set<String> words = this.wordAppearances(doc1, doc2);
        double sum = 0.0;

        for (String w: words) {
            double pi = this.Probability(doc1,w);

            double qi = this.Probability(doc2,w);
            double mi = (pi + qi) / 2.0;

            double exp1=0.0;
            double exp2=0.0;

            if(pi == 0){
                exp1 = 0.0;
            }
            else {
                exp1 = pi * (Math.log(pi / mi) / Math.log(2.0));
            }

            if(qi == 0){
                exp2 = 0;
            }
            else {
                exp1 = qi * (Math.log(qi / mi) / Math.log(2.0));
            }

            //System.out.println(pi + " " + qi + " " + mi);

            //sum = pi * (Math.log(pi / mi) / Math.log(2.0)) + qi * (Math.log(qi / mi) / Math.log(2.0)); //BUUUUUUUGGGGGGGGGGGGGG

            //0.5 * pi * qi * (Math.log(qi / mi) / Math.log(2.0));

            sum += exp1 + exp2;

            if(sum != 0){
                System.out.println("lmao");
            }

           // System.out.println(sum);
        }

       // System.out.println(sum + " bruh");

        return sum/2;
    }

    /*public double jsDivergence2(Document doc1, Document doc2) { //can't use rip

        Set<String> words = doc1.wordAppearances(doc2);
        double sum = 0.0;

        for (String c: words) {
            double pi = doc1.Probability(c);
            double qi = doc2.Probability(c);
            double mi = (pi + qi) / 2.0;
            sum = pi * (Math.log(pi / mi) / Math.log(2.0)) + qi * (Math.log(qi / mi) / Math.log(2.0));
        }

        System.out.println(sum / 2.0 + " bruh");
        return sum / 2.0;
    } */
    /**
     * Compute the probability of a word appearing in two documents
     * @param word a word appearing in both documents
     * @return The probability of the word appearing in both documents
     */
    public double Probability(Document document, String word) {

        ArrayList<String> wordArrayList = new ArrayList<String>();
        for(int i=1; i<=document.numSentences(); i++){
            for(String wor : document.getSentence(i).split(" ")){
                if(filter(wor) != ""){
                    wordArrayList.add(filter(wor));
                }
            }
        }

        double count = 0.0;

        for (String s: wordArrayList) {
            if (word.equals(s)) {
                count = count + 1.0;
            }
        }
        return count / wordArrayList.size();
    }

    /**
     * Stores all the words appearing in both documents in a set
     * @param **********************************************************************************
     * @return A set containing all the words that appear in both lists
     */
    public Set<String> wordAppearances(Document doc1, Document doc2) {
        Set<String> wordAppearances = new HashSet<>();

        ArrayList<String> wordArrayList1 = new ArrayList<String>();

        ArrayList<String> wordArrayList2 = new ArrayList<String>();

        for(int i=1; i<=doc1.numSentences(); i++){
            for(String wor : doc1.getSentence(i).split(" ")){
                if(filter(wor) != ""){
                    wordArrayList1.add(filter(wor));
                }
            }
        }

        for(int i=1; i<=doc2.numSentences(); i++){
            for(String wor : doc2.getSentence(i).split(" ")){
                if(wor.length() != 0){
                    if(filter(wor) != ""){
                        wordArrayList2.add(filter(wor));
                    }
                }
            }
        }


        //Add words that appear in both lists to a set
        for (String w: wordArrayList1) {
            if (wordArrayList2.contains(w)) {
                wordAppearances.add(w);
                //System.out.println(c + " from");
            }
        }
        return wordAppearances;
    }

    /**
     * Compute the Document Divergence between the given documents
     * @param doc1 the first document, is not null
     * @param doc2 the second document, is not null
     * @return the Document Divergence between the given documents
     */
    public double documentDivergence(Document doc1, Document doc2) {

        double firstTerm = 0.0;
        double secondTerm = WT_JS_DIVERGENCE * jsDivergence(doc1, doc2);
        double[] m1 = new double[5];
        double[] m2 = new double[5];
        m1[0] = doc1.averageSentenceLength();
        m1[1] = doc1.averageSentenceComplexity();
        m1[2] = doc1.averageWordLength();
        m1[3] = doc1.uniqueWordRatio();
        m1[4] = doc1.hapaxLegomanaRatio();
        m2[0] = doc2.averageSentenceLength();
        m2[1] = doc2.averageSentenceComplexity();
        m2[2] = doc2.averageWordLength();
        m2[3] = doc2.uniqueWordRatio();
        m2[4] = doc2.hapaxLegomanaRatio();

        for (int i = 0; i < 5; i++) {
            firstTerm += w[i] * Math.abs((m1[i] - m2[i]));
        }

        return firstTerm + secondTerm;
    }


    public String filter(String raw) {

        boolean poundFlag = true;
        boolean allBad = true;

        StringBuilder sb = new StringBuilder(raw);

        int frontInd = 0;
        int rearInd = 0;

        for(int i=raw.length(); i>0; i--){ //scan from rear
            char ch = raw.charAt(i-1);
            if((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9')) {
                rearInd = i-1;
                allBad = false;
                //System.out.println("end" + rearInd);
                break;
            }
        }

        if(allBad) { //if all punctuation then return empty
            return "";
        }

        for(int i=0; i<raw.length(); i++){ //scan from front
            char ch = raw.charAt(i);
            if((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9') || (ch == '#')) {
                frontInd = i;
                //System.out.println("frnt" + i);
                break;
            }
        }

        for(int i=frontInd; i<=rearInd; i++){


            char ch = raw.charAt(i);

            if((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9')) {
                poundFlag = true;
                break;
            }
            else {
                poundFlag = false;
            }

        }

        if(poundFlag == false){
            return "";
        }

        return sb.substring(frontInd, rearInd+1).toString().toLowerCase();

        //System.out.println(raw.length()-1 + " " + keyInd);

        /*for (int i = 0; i < raw.length(); i++) {

            char ch = raw.charAt(i);
            if(i >= keyInd){
                goodFlag = false;
            }
            if ((ch >= 'A' && ch <= 'Z') ||
                    (ch >= 'a' && ch <= 'z') ||
                    (ch >= '0' && ch <= '9') || goodFlag == true) {
                sb.append(ch);

                goodFlag = true;
            }

        }
        return sb.toString(); */

    }

}