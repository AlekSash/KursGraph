
import java.io.PrintWriter;
import java.lang.Integer;
import java.lang.Object;
import java.lang.String;
import java.util.*;
import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Pointer;
import net.didion.jwnl.dictionary.Dictionary;

class Node
{
    public Object node;
    public Vector adjacentNodes = new Vector();
    private int count = 1;

    public Node(Object elem)
    {
      node = elem;
    };

    public void setArc(Object elem)
    {
        adjacentNodes.setElementAt(elem,count);
        count++;
    };

    public void delArc(Object elem)
    {
        if(count>0)
        adjacentNodes.removeElement(elem);
        count--;
    };
}

class Graph
{
    private Vector<Node> nodes = new Vector<Node>();
    public int size = 0;
    public int last = 0;
    public Object GetElem(int index)
    {
        return nodes.get(index);
    }
    public void AddNode(int index, Object v)
    {
       Node a = new Node(v);
       nodes.setElementAt(a, index);
        if (last<index) last=index;
        size = nodes.size();
    };

    public void DeleteNode(int index)
    {
      if(last>=index)
          nodes.removeElementAt(index);
        size = nodes.size();
    };

    public void AddArc(Object sourse, Object target)
    {
        int t = nodes.indexOf(sourse);
        Node a = new Node(nodes.get(t));
        a.setArc(target);
        nodes.setElementAt(a,t);
    };
    public void DeleteArc(int sourse, int target)
    {
        int t = nodes.indexOf(sourse);
        Node a = new Node(nodes.get(t));
        a.delArc(target);
        nodes.setElementAt(a,t);
    };
}

public class KursGraphApp
{
    public static void main(String[] args) throws Exception
    {
        JWNL.initialize(new FileInputStream("file_properties.xml"));
        Dictionary dict = Dictionary.getInstance();
        //итератор проходится по всем существительным
        Iterator it = dict.getIndexWordIterator(POS.NOUN);
        Iterator it1 = dict.getIndexWordIterator(POS.NOUN);
        //файл для записи слов
        PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
        PrintWriter tester = new PrintWriter("test.txt");
        //Мар для записи пары ключ, слово
        Map<Integer, String> words= new TreeMap<Integer, String>();
        Set kv = words.entrySet();





        Graph tes = new Graph();
        int count = 0;
        while(it.hasNext())
        {   //текущее слово
            IndexWord current = (IndexWord)it.next();
            //получение леммы
            String lemma = current.getLemma();
            count++;
            tes.AddNode(count,lemma);
            tester.println(tes.GetElem(count));
            //печать леммы
            writer.print(lemma);
            writer.println();
            //получение множества синсетов, в котором она содержится
            Synset [] a = current.getSenses();
            for (int i=0;i!=a.length;i++)
            {  //для каждого синсета получение множества указателей на другие синсеты
               Pointer [] b = a[i].getPointers();
                for (int j=0;j!=b.length;j++)
                {   //для каждого указателя получение его целевого синсета
                    Synset c = b[j].getTargetSynset();
                    //получение множества слов целевого синсета
                    Word [] d =c.getWords();
                    for (int k=0;k!=d.length;k++)
                    {   //для каждой целевой леммы
                        String tar = d[k].getLemma();
                        tes.AddArc(lemma,tar);
                        writer.print(tar);
                        writer.print("  ");

                    }
                    writer.println();

                }
            }

            writer.println();
        }
        tester.close();
    }
}
