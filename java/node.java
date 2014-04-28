
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

import net.didion.jwnl.JWNL;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Pointer;
import net.didion.jwnl.dictionary.Dictionary;
import sun.security.provider.certpath.Vertex;

/*класс узла*/
class Node {
    private Object node;
    /*множество смежных узлов*/
    private Vector adjacentNodes = new Vector();

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

    public Iterator<Node> CreateIterator() {
        return adjacentNodes.iterator();
    }

    /*добавление смежного узла = добавление ребра*/
    public void addArc(Object elem) {
        adjacentNodes.add(elem);
    }

    /*удаление смежного узла = удаление ребра*/
    public void delArc(Object elem) {
        adjacentNodes.removeElement(elem);
    }
}

