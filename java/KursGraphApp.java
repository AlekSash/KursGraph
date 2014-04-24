
import java.io.PrintWriter;
import java.lang.*;
import java.lang.Integer;
import java.lang.Iterable;
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

import net.didion.jwnl.JWNL;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Pointer;
import net.didion.jwnl.dictionary.Dictionary;
import sun.security.provider.certpath.Vertex;

/*класс узла*/
class Node
{
    private Object node;
    /*множество смежных узлов*/
    private Vector adjacentNodes = new Vector();

    public Node(Object node)
    {
	this.node = node;
    };

    public boolean equals(Object o)
    {
	if (node == null || o == null || !(o instanceof Node))
	    return false;
	Node n = (Node)o;
	return node == n.node || node.equals(n.node);
    }

    public Object getNode()
    {
	return node;
    }

    public Object[] getAdjacentNodes()
    {
	return adjacentNodes.toArray(new Object[adjacentNodes.size()]);
    }

    public Iterator<Node> CreateIterator()
    {
	return adjacentNodes.iterator();
    };

    /*добавление смежного узла = добавление ребра*/
    public void addArc(Object elem)
    {
        adjacentNodes.add(elem);
    };

    /*удаление смежного узла = удаление ребра*/
    public void delArc(Object elem)
    {
        adjacentNodes.removeElement(elem);
    };
}

/*класс графа*/
class Graph
{
    /*множество узлов*/
    private Vector<Node> nodes = new Vector<Node>();

    /*получение узла по индексу*/
    public Object GetElem(int index)
    {
        return nodes.get(index);
    };

    public Iterator<Node> CreateIterator()
    {
        return nodes.iterator();
    };


    public int GetSize()
    {
      return nodes.size();
    };

    /*добавление узла*/
    public void AddNode(Object v)
    {
	    nodes.add(new Node(v));
        System.out.println();
        System.out.println(v);
    };

    /*удаление узла по индексу*/
    public void DeleteNode(int index)
    {
	if (index < 0 || index >= nodes.size())
	    return;
	Node v = nodes.get(index);
	nodes.removeElementAt(index);
	for(Node p: nodes)
	{
	    Vector toRemove = new Vector();
	    for(Object o: p.getAdjacentNodes())
		if (o == v.getNode())
		    toRemove.add(o);
	    for(Object o: toRemove)
		p.delArc(o);
	}
    }

    /*добавление дуги между двумя узлами - исходным и целевым*/
    public void AddArc(Object source, Object target)
    {
    //в этом месте проблемы!
	int sourceIndex = nodes.indexOf(source);
	if (sourceIndex == -1)
	{
        System.out.println(nodes.size());
	    nodes.add(new Node(source));
	    sourceIndex = nodes.size() - 1;
        System.out.print(source);
        System.out.print(" ");
	}
	Node sourceNode = nodes.get(sourceIndex);
	sourceNode.addArc(target);
	/*if (nodes.indexOf(target) == -1)
	    nodes.add(new Node(target));*/

    };

    /*удаление дуги между двумя узлами - исходным и целевым*/
    public void DeleteArc(Object source, Object target)
    {
        int sourceIndex = nodes.indexOf(source);
        int targetIndex = nodes.indexOf(target);
        if (sourceIndex < 0 || sourceIndex >= nodes.size() ||
            targetIndex < 0 || targetIndex>= nodes.size())
            return;
        Node sourceNode = nodes.get(sourceIndex);
        sourceNode.delArc(target);
    };

}


public class KursGraphApp
{
    public static void main(String[] args) throws Exception
    {
        JWNL.initialize(new FileInputStream("file_properties.xml"));
        Dictionary dict = Dictionary.getInstance();
        Iterator it = dict.getIndexWordIterator(POS.NOUN);

        PrintWriter writer = new PrintWriter("writer.txt", "UTF-8");
        PrintWriter tester = new PrintWriter("tester.txt",  "UTF-8");
        /*создание графа*/
        Graph tes = new Graph();
        /*создание итератора по узлам для графа*/
        Iterator<Node> it1 = tes.CreateIterator();
        int wer = -1;
        while((it.hasNext())&&(wer<10))
        {   //текущее слово из словаря
            IndexWord current = (IndexWord)it.next();
            wer++;
            System.out.println();
            System.out.println();
            System.out.print(wer);
            //получение леммы
            String lemma = current.getLemma();
            /*добавление леммы в граф в виде узла*/
            tes.AddNode(lemma);
            Node f = (Node)tes.GetElem(wer);
            String f1 = (String)f.getNode();
            writer.print(f1);
            writer.println();
            tester.print(lemma);
            tester.println();
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
                        tester.print(tar);
                        tester.print(" ");


                    }
                    tester.println();

                }
            }

            tester.println();
        }
        writer.print(tes.GetSize());
        tester.close();
        writer.close();
        System.in.read();
    }
}
