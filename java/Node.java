
import java.lang.Object;
import java.lang.String;
import java.lang.System;
import java.util.*;
import java.io.*;
import java.util.Objects;

/*класс узла*/
class Node {
    private Object node;
    /*множество смежных узлов*/
    private Vector <Object> adjacentNodes = new Vector<Object>();

    public Node(Object node) {
        this.node = node;
    }

    public boolean equals(Object o) {
        if (node == null || o == null || !(o instanceof Node))
            return false;
        Node n = (Node)o;
        return node == n.getNode() || node.equals(n.getNode());
    }

    public Object getNode() {
        return node;
    }

    public Object[] getAdjacentNodes() {
        return adjacentNodes.toArray(new Object[adjacentNodes.size()]);
    }

    public Iterator<Object> CreateIterator() {
        return adjacentNodes.iterator();
    }

    /*добавление смежного узла = добавление ребра*/
    public void addArc(Object elem) {
        int nodeIndex = adjacentNodes.indexOf(elem);
        if(nodeIndex==-1)
        adjacentNodes.add(elem);
    }

    /*удаление смежного узла = удаление ребра*/
    public void delArc(Object elem) {
        adjacentNodes.removeElement(elem);
    }
}

