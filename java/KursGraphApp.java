
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


public class KursGraphApp {
    private static Graph tes;
    private static PrintWriter writer;
    private static PrintWriter tester;

    public static void main(String[] args) throws Exception {
        JWNL.initialize(new FileInputStream("file_properties.xml"));
        Dictionary dict = Dictionary.getInstance();
        Iterator it = dict.getIndexWordIterator(POS.NOUN);

        writer = new PrintWriter("writer.txt", "UTF-8");
        tester = new PrintWriter("tester.txt", "UTF-8");

        /*создание графа*/
        tes = new Graph();
        /*создание итератора по узлам для графа*/
        Iterator<Node> it1 = tes.CreateIterator();
        int wer = 0;
        while ((it.hasNext()) && (wer < 10)) {   //текущее слово из словаря
            IndexWord current = (IndexWord) it.next();

            System.out.println();
            System.out.println();
            System.out.println(wer);
            graphFill(current);

            tester.println();
            wer++;
        }
        writer.println("Graph size:");
        writer.print(tes.GetSize());
        tester.close();
        writer.close();
        System.in.read();


    }


    public static void graphFill(IndexWord current) throws JWNLException {//получение леммы
        String lemma = current.getLemma();
            /*добавление леммы в граф в виде узла*/
        tes.AddNode(lemma);

        Node f = (Node) tes.GetElem(tes.GetSize()-1);
        String f1 = (String) f.getNode();
        writer.print(f1);
        writer.println();
        tester.print(lemma);
        tester.println();
        //получение множества синсетов, в котором она содержится

        Synset[] a = current.getSenses();

        for (int i = 0; i != a.length; i++) {  //для каждого синсета получение множества указателей на другие синсеты
            Pointer[] b = a[i].getPointers();
            for (int j = 0; j != b.length; j++) {   //для каждого указателя получение его целевого синсета
                Synset c = b[j].getTargetSynset();
                //получение множества слов целевого синсета
                Word[] d = c.getWords();
                for (int k = 0; k != d.length; k++) {   //для каждой целевой леммы
                    String tar = d[k].getLemma();
                    tes.AddArc(f, tar);
                    tester.print(tar);
                    tester.print(" ");


                }
                tester.println();

            }
        }


        tester.println();
    }

}


