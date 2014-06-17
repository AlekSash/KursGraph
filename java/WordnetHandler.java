
import java.io.FileNotFoundException;
import java.lang.String;
import java.lang.System;
import java.util.*;
import java.io.*;

import net.didion.jwnl.JWNLException;
import net.didion.jwnl.JWNL;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Pointer;
import net.didion.jwnl.dictionary.Dictionary;
import sun.security.provider.certpath.Vertex;

class WordnetHandler {
    private Dictionary dictionary = null;
    private Iterator AdverbIterator = null;
    private Iterator AdjectiveIterator = null;
    private Iterator NounIterator = null;
    private Iterator VerbIterator = null;
    private IndexWord current;
    private int currentIndex = 0;//номер текущего узла
    private int maxWords = 20;

    public WordnetHandler() throws FileNotFoundException, JWNLException {
        JWNL.initialize(new FileInputStream("file_properties.xml"));
        dictionary = Dictionary.getInstance();
        NounIterator = dictionary.getIndexWordIterator(POS.NOUN);
        AdjectiveIterator = dictionary.getIndexWordIterator(POS.ADJECTIVE);
        AdverbIterator = dictionary.getIndexWordIterator(POS.ADVERB);
        VerbIterator = dictionary.getIndexWordIterator(POS.VERB);
    }

    public void SetMaxWords(int number) {
        this.maxWords = number;
    }

    /*получение очередного слова и связанных с ним слов*/
    public List<String> GetNextWordWithSynsets(int flag) throws JWNLException {

        switch (flag) {
            case 0:
                if ((NounIterator.hasNext()) && (currentIndex < maxWords))
                    /*текущее слово из словаря*/
                    current = (IndexWord) NounIterator.next();

                else current = null;
                break;
            case 1:
                if ((AdjectiveIterator.hasNext()) && (currentIndex < maxWords))
            /*текущее слово из словаря*/
                    current = (IndexWord) AdjectiveIterator.next();
                else
                    current = null;
                break;
            case 2:
                if ((AdverbIterator.hasNext()) && (currentIndex < maxWords))
            /*текущее слово из словаря*/
                    current = (IndexWord) AdverbIterator.next();
                else
                    current = null;
                break;
            case 3:
                if ((VerbIterator.hasNext()) && (currentIndex < maxWords))
            /*текущее слово из словаря*/
                    current = (IndexWord) VerbIterator.next();
                else
                    current = null;
                break;
            default:
                break;
        }

        if (current != null) {
            List<String> wordList = new ArrayList<String>();
            /*получение леммы - содержимое слова*/
            String lemma = current.getLemma();
            /*лемму в начало wordList*/
            wordList.add(0, lemma);
            /*получение множества синсетов, в котором она содержится*/
            Synset[] a = current.getSenses();
            /*получение множества слов из каждого такого синсета*/
            for (int i = 0; i < a.length; i++) {
                Word[] d = a[i].getWords();
                /*добавление этих слов в wordList*/
                for (int k = 0; k < d.length; k++) {
                    String tar = d[k].getLemma();
                    wordList.add(tar);
                }
            }

//            for (int i = 0; i < a.length; i++)
//            {  //для каждого синсета получение множества указателей на другие синсеты
//                Pointer[] b = a[i].getPointers();
//
//                for (int j = 0; j < b.length; j++)
//                {   //для каждого указателя получение его целевого синсета
//                    Synset c = b[j].getTargetSynset();
//
//                    //получение множества слов целевого синсета
//                    Word[] d = c.getWords();
//
//                    for (int k = 0; k < d.length; k++)
//                    {   //для каждой целевой леммы
//                        String tar = d[k].getLemma();
//                        wordList.add(tar);
//                    }
//                }
//            }
            currentIndex++;

            return wordList;
        } else return null;


    }


}