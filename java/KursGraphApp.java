
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.StringBufferInputStream;
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
import java.util.Hashtable;
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

import java.util.LinkedList;
import java.util.Queue;

public class KursGraphApp {   /*количество обрабатываемых слов*/
    private static int maxWords = 300000;

    public static void main(String[] args) throws Exception {
        SingleWriter asd = SingleWriter.getInstance("tester.txt");
         /*создание графа*/
        Graph wordsGraph = new Graph();
        // Обработчик WordNet
        WordnetHandler wordnetHandler = new WordnetHandler();
        /*установка количества считываемых из WordNet слов*/
        wordnetHandler.SetMaxWords(maxWords);




        List<String> list;
        int c = 0;

        for (int r = 0; r < 4; r++) {

            list = wordnetHandler.GetNextWordWithSynsets(r);
            c = 0;

            while (list != null) {
                c++;
                String word = list.get(0);
                asd.Show(word);
                asd.NewLine();

                for (int i = 1; i < list.size(); i++) {
                    String synWord = list.get(i);
//                    asd.Show(synWord);

                    wordsGraph.AddArc(word, synWord);
                }
                if (c % 1000 == 0) {
                    System.out.println(String.format("%d words done. %d nodes in graph", c, wordsGraph.GetSize()));
                }

                list = wordnetHandler.GetNextWordWithSynsets(r);


            }
        }


        asd.Show("Graph size :");
        asd.Show(wordsGraph.GetSize());
        asd.NewLine();

        System.out.print("Graph size : ");
        System.out.println(wordsGraph.GetSize());
        System.out.println("Number of components : " + (wordsGraph.Colorize()));
        for (int i = 0; i < wordsGraph.GetSize(); i++) {
            asd.Show(((Node) wordsGraph.GetElem(i)).getNode());
            asd.Show(((Node) wordsGraph.GetElem(i)).GetComponentNumber());
            String [] dfg =((Node) wordsGraph.GetElem(i)).getAdjacentNodes();
            asd.Show("(");
            for(int j = 0; j< dfg.length; j++){
                asd.Show(dfg[j]);
            }
            asd.Show(")");
            asd.NewLine();
        }
        asd.Close();
        System.in.read();
    }
}







