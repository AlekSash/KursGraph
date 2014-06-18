
import java.lang.*;
import java.io.*;
import java.lang.Integer;
import java.lang.NoClassDefFoundError;
import java.lang.SafeVarargs;
import java.lang.String;
import java.lang.System;
import java.util.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

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
class Graph {
    /*множество узлов*/
    private Hashtable<String, Node> nodes = new Hashtable<String, Node>();
    private Hashtable<Integer,Integer> connectComponents  = new Hashtable<Integer, Integer>();
    private int numberOfComponents = 0;
    private  int maxNumberInComponent = 0;
    private int minNumberInComponent = 300;
    private int maxComp = 0;

    /*получение узла по индексу*/
    public Object GetElem(int index) {
        return nodes.values().toArray()[index];
    }

    /*получение узла по содержимому*/
    public Node GetElem(String node) {
        return nodes.get(node);
    }

    public int GetMaxNumberInComponent(){
        return maxNumberInComponent;
    }

    public int GetMaxComp(){
        return maxComp;
    }

    public int GetMinNumberInComponent() {
        return minNumberInComponent;
    }


    /*получение размера графа*/
    public int GetSize() {
        return nodes.size();
    }

    /*добавление узла*/
    public void AddNode(String v) {
        Node newNode = new Node(v);
        if (!nodes.containsKey(v)) {
            nodes.put(v, newNode);
        }
    }

    /*добавление или получение узла*/
    public Node AddOrGetNode(String v) {
        Node newNode = new Node(v);
        if (!nodes.containsKey(v)) {
            nodes.put(v, newNode);
            return newNode;
        } else {
            return nodes.get(v);
        }
    }

    /*добавление дуги между двумя узлами - исходным и целевым*/
    public void AddArc(String source, String target) {
        Node sourceNode = AddOrGetNode(source);
        Node targetNode = AddOrGetNode(target);
        sourceNode.addArc(targetNode);
        targetNode.addArc(sourceNode);
    }

    ;
    public Hashtable<Integer, Integer> GetConnectComponents(){
        return connectComponents;
    }


    /*удаление узла по индексу
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
    }  */

    /*удаление узла по значению
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

    }                          */


    /*удаление дуги между двумя узлами - исходным и целевым
    public void DeleteArc(String source, String target)
    {
        if (!nodes.containsKey(source) || !nodes.containsKey(target))
           return;
        Node sourceNode = new Node(source);
        sourceNode.delArc(target);
    }
    */
    /*раскраска графа*/
    public int Colorize() {
        /*для каждого узла устанавливаем */
        for (Node node : nodes.values()) {
            /*нулевой номер компоненты*/
            node.SetComponentNumber(0);
            /*узел не просмотрен*/
            node.SetVisited(false);
        }
        /*текущий номер компоненты*/
        int currentColor = 1;
        /*очередь узлов на обработку*/
        Queue<Node> queue = new LinkedList<Node>();
        int term;
        /*для каждого узла из графа*/
        for (Node node : nodes.values()) {
            /*если он ещё не обработан*/
            if (!node.IsVisited()) {
                /*красим*/
                node.SetComponentNumber(currentColor);

                if (!connectComponents.containsKey(currentColor))
                    connectComponents.put(currentColor, 1);
                else{
                term = connectComponents.get(currentColor);
                connectComponents.put(currentColor, term + 1);
                }

                currentColor++;
                /*помещаем в очередь*/
                queue.add(node);

            }
            /*пока очередь не пуста*/
            while (!queue.isEmpty()) {
                /*достаём из очереди узел*/
                Node current = queue.poll();
                /*помечаем как обработанный*/
                current.SetVisited(true);
                /*раскрашиваем смежные с ним узлы*/
                List<Node> linkedNodes = current.Colorize();
                term = connectComponents.get(currentColor-1);
                connectComponents.put(currentColor-1, term + linkedNodes.size());
                /*помещаем их в очередь*/
                queue.addAll(linkedNodes);
            }
            term =connectComponents.get(currentColor-1);
            if(term>maxNumberInComponent)
            {
                maxNumberInComponent = term;
                maxComp = currentColor - 1;
            }
                if(term<minNumberInComponent)
                minNumberInComponent = term;

        }

        numberOfComponents = currentColor - 1;
        return numberOfComponents;
    }
}