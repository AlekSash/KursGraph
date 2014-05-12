
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


public class KursGraphApp
{
    private static int maxWords = 31;

    public static void main(String[] args) throws Exception
    {
        PrintWriter writer = new PrintWriter("writer.txt", "UTF-8");
        PrintWriter tester = new PrintWriter("tester.txt", "UTF-8");

        /*создание графа*/
        Graph wordsGraph = new Graph();

        // Обработчик WordNet
        WordnetHandler wordnetHandler = new WordnetHandler();
        wordnetHandler.SetMaxWords(maxWords);

        List<String> list = wordnetHandler.GetNextWordWithSynsets();

        while (list != null)
        {
            wordsGraph.AddNode(list.get(0));

            for (int i = 1; i < list.size(); i++)
            {
                wordsGraph.AddArc(list.get(0),list.get(i));
            }

            list = wordnetHandler.GetNextWordWithSynsets();
        }

        writer.println("Graph size:");
        writer.print(wordsGraph.GetSize());

        writer.close();

        System.in.read();
    }
}


