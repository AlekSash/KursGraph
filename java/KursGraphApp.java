
import java.io.PrintWriter;
import java.lang.Integer;
import java.lang.Iterable;
import java.lang.Object;
import java.lang.String;
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

class Node
{
    private Object node;
    private Vector adjacentNodes = new Vector();

    public Node(Object node)
    {
	this.node = node;
    };

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

    public void addArc(Object elem)
    {
        adjacentNodes.add(elem);
    };

    public void delArc(Object elem)
    {
        adjacentNodes.removeElement(elem);
    };
}

class Graph
{
    private Vector<Node> nodes = new Vector<Node>();

    // This method provides access to the Node object which is supposed to be a part of internal structure, it is not a good idea!
    private Object GetElem(int index)
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

    public void AddNode(Object v)
    {
	    nodes.add(new Node(v));
    };

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

    public void AddArc(Object source, Object target)
    {
	int sourceIndex = nodes.indexOf(source);
	if (sourceIndex == -1)
	{
	    nodes.add(new Node(source));
	    sourceIndex = nodes.size() - 1;
	}
	Node sourceNode = nodes.get(sourceIndex);
	sourceNode.addArc(target);
	if (nodes.indexOf(target) == -1)
	    nodes.add(new Node(target));
    };

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
        PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
        PrintWriter tester = new PrintWriter("test.txt",  "UTF-8");
        Graph tes = new Graph();
        Iterator it1 = tes.CreateIterator();
        while(it.hasNext())
        {   //текущее слово
            IndexWord current = (IndexWord)it.next();
            //получение леммы
            String lemma = current.getLemma();
            tes.AddNode(lemma);
            tester.println(it1.next());
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
