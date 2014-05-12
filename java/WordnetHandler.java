
import java.io.FileNotFoundException;
import java.lang.String;
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
    private Iterator iterator = null;
    private int currentIndex = 0;
    private int maxWords = 20;

    public WordnetHandler() throws FileNotFoundException, JWNLException{
        JWNL.initialize(new FileInputStream("file_properties.xml"));
        dictionary = Dictionary.getInstance();
        iterator = dictionary.getIndexWordIterator(POS.NOUN);
    }

    public void SetMaxWords(int number){
        this.maxWords = number;
    }

    public List<String> GetNextWordWithSynsets() throws JWNLException{

        if ((iterator.hasNext()) && (currentIndex < maxWords)) {

            List<String> wordList = new ArrayList<String>();

            //текущее слово из словаря
            IndexWord current = (IndexWord) iterator.next();

            //получение леммы
            String lemma = current.getLemma();
            wordList.add(0, lemma);

            //получение множества синсетов, в котором она содержится
            Synset[] a = current.getSenses();

            for (int i = 0; i < a.length; i++)
            {  //для каждого синсета получение множества указателей на другие синсеты
                Pointer[] b = a[i].getPointers();

                for (int j = 0; j < b.length; j++)
                {   //для каждого указателя получение его целевого синсета
                    Synset c = b[j].getTargetSynset();
                    //получение множества слов целевого синсета
                    Word[] d = c.getWords();

                    for (int k = 0; k < d.length; k++)
                    {   //для каждой целевой леммы
                        String tar = d[k].getLemma();
                        wordList.add(tar);
                    }

                }
            }

            currentIndex++;
            return wordList;
        }
        else
        {
            return null;
        }
    }
}