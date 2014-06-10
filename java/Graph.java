
import java.lang.NoClassDefFoundError;
import java.lang.String;
import java.lang.System;
import java.io.*;
import java.util.*;
import java.util.Enumeration;

import net.didion.jwnl.JWNLException;
import net.didion.jwnl.JWNL;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Pointer;
import net.didion.jwnl.dictionary.Dictionary;
import sun.security.provider.certpath.Vertex;

import javax.management.monitor.StringMonitor;

/*класс графа*/
class Graph
{
    /*множество узлов*/
    private Hashtable<String, Node> nodes = new Hashtable<String, Node>();

    /*получение узла по индексу*/
    public Object GetElem(int index)
    {
        return nodes.values().toArray()[index];
    }


    /*получение размера графа*/
    public int GetSize()
    {
        return nodes.size();
    }

    /*добавление узла*/
    public void AddNode(String v)
    {
        Node newNode = new Node(v);
        if (!nodes.containsKey(v))
        {
            nodes.put(v, newNode);
        }
    }

    /*добавление или получение узла*/
    public Node AddOrGetNode(String v)
    {
        Node newNode = new Node(v);
        if (!nodes.containsKey(v))
        {
            nodes.put(v, newNode);
            return newNode;
        }
        else
        {
            return nodes.get(v);
        }
    }

    /*добавление дуги между двумя узлами - исходным и целевым*/
    public void AddArc(String source, String target)
    {
        Node sourceNode = AddOrGetNode(source);
        Node targetNode = AddOrGetNode(target);
        sourceNode.addArc(targetNode);
    };

    /*удаление узла по индексу*/
    public void DeleteNode(int index)
    {
        if (index < 0 || index >= nodes.size())
            return;
        Node del = new Node (((Node)this.GetElem(index)).getNode());
        nodes.remove(del.getNode());

        Enumeration<Node> NodeIter = nodes.elements();
        while(NodeIter.hasMoreElements())
        {
            Node p = new Node (NodeIter.nextElement().getNode());
            Vector <String> toRemove = new <String> Vector();
            for(String o: p.getAdjacentNodes())
                if (o == del.getNode())
                    toRemove.add(o);
            for(String o: toRemove)
                p.delArc(o);
        }
    }

    /*удаление узла по значению*/
    public void DeleteNode(String s)
    {
        if (!nodes.containsKey(s))
            return;

            Node del = nodes.remove(s);
            Enumeration<Node> NodeIter = nodes.elements();
            while(NodeIter.hasMoreElements())
                {
                    Node p = new Node (NodeIter.nextElement().getNode());
                    Vector <String> toRemove = new <String> Vector();
                    for(String o: p.getAdjacentNodes())
                        if (o == del.getNode())
                        toRemove.add(o);
                    for(String o: toRemove)
                        p.delArc(o);
                }

    }


    /*удаление дуги между двумя узлами - исходным и целевым*/
    public void DeleteArc(String source, String target)
    {
        if (!nodes.containsKey(source) || !nodes.containsKey(target))
           return;
        Node sourceNode = new Node(source);
        sourceNode.delArc(target);
    }


}