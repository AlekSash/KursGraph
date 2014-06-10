
import java.lang.Object;
import java.lang.String;
import java.lang.System;
import java.util.*;
import java.io.*;
import java.util.Objects;

/*класс узла*/
class Node {
    private String node;
    /*множество смежных узлов*/
    private Vector <String> adjacentNodes = new Vector<String>();

    public Node(String node)
    {
        this.node = node;
    }

    public boolean equals(Object o)
    {
        if (node == null || o == null || !(o instanceof Node))
            return false;
        Node n = (Node)o;
        return node == n.getNode() || node.equals(n.getNode());
    }

    public String getNode()
    {
        return node;
    }

    public String[] getAdjacentNodes()
    {
        return adjacentNodes.toArray(new String[adjacentNodes.size()]);
    }

    public Iterator<String> CreateIterator()
    {
        return adjacentNodes.iterator();
    }

    /*добавление смежного узла = добавление ребра*/
    public void addArc(Node elem)
    {
        String s = elem.getNode();
        int nodeIndex = adjacentNodes.indexOf(s);
        if(nodeIndex==-1)
        adjacentNodes.add(s);
    }

    /*удаление смежного узла = удаление ребра*/
    public void delArc(String elem)
    {
        adjacentNodes.removeElement(elem);
    }
}

