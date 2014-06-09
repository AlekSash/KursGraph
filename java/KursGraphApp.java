
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.lang.*;
import java.lang.Integer;
import java.lang.Iterable;
import java.lang.NullPointerException;
import java.lang.Object;
import java.lang.String;
import java.lang.System;
import java.util.*;
import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.Vector;

import net.didion.jwnl.JWNLException;
import net.didion.jwnl.JWNL;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Pointer;
import net.didion.jwnl.dictionary.Dictionary;
import sun.security.provider.certpath.Vertex;


public class KursGraphApp
{
    private static int maxWords = 20000;

    public static void main(String[] args) throws Exception
    {
        PrintWriter writer = new PrintWriter("writer.txt", "UTF-8");



        /*создание графа*/
        Graph wordsGraph = new Graph();

//        String content = new Scanner(new File("Text.txt")).useDelimiter("\\Z").next();
//        String[] statements = content.replaceAll("[\n\r]", "").split("[.?!]+");
//        List<String> allWords = new ArrayList<String>();
//        for (String statement: statements)
//        {
//            String[] words = statement.split("[^a-zA-Zа-яА-Я]+");
//
//            for (String word: words)
//            {
//                if (word.matches("[a-zA-Zа-яА-Я]+"))
//                {
//                    allWords.add(word);
//                }
//            }
//        }
//        System.out.print("Check");
//        for (String word: allWords)
//        {
//            wordsGraph.AddNode(word.toLowerCase());
//        }



        // Обработчик WordNet
        WordnetHandler wordnetHandler = new WordnetHandler();
        wordnetHandler.SetMaxWords(maxWords);

        List<String> list = wordnetHandler.GetNextWordWithSynsets();
        int c = 0;

        while (list != null)
        {
            c++;
            if (c % 1000 == 0) {
                System.out.println(String.format("%d words done. %d words in graph", c, wordsGraph.GetSize()));
            }
            for (int i = 1; i < list.size(); i++)
            {
                wordsGraph.AddArc(list.get(0),list.get(i));
            }

            list = wordnetHandler.GetNextWordWithSynsets();
        }

        writer.print("Graph size :");
        writer.println(wordsGraph.GetSize());
        System.out.print("Size : ");
        System.out.println(wordsGraph.GetSize());

        for (int i = 0; i < wordsGraph.GetSize(); i++ )
        {
            writer.println((String)(((Node)wordsGraph.GetElem(i)).getNode()));
        }

        writer.close();

        System.in.read();
    }
}


