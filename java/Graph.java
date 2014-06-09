
import java.lang.NoClassDefFoundError;
import java.lang.String;
import java.util.*;

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
    };

//    public Iterator<Node> CreateIterator()
//    {
//        return nodes.iterator();
//    };


    public int GetSize()
    {
        return nodes.size();
    };

    /*добавление узла*/
    public void AddNode(String v)
    {
        Node newNode = new Node(v);
        if (!nodes.containsKey(v)) {
            nodes.put(v, newNode);
        }
    }

    public Node AddOrGetNode(String v)
    {
        Node newNode = new Node(v);
        if (!nodes.containsKey(v)) {
            nodes.put(v, newNode);
            return newNode;
        } else {
            return nodes.get(v);
        }
    }

//    /*удаление узла по индексу*/
//    public void DeleteNode(int index)
//    {
//        if (index < 0 || index >= nodes.size())
//            return;
//        Node v = nodes.get(index);
//        nodes.removeElementAt(index);
//        for(Node p: nodes)
//        {
//            Vector toRemove = new Vector();
//            for(Object o: p.getAdjacentNodes())
//                if (o == v.getNode())
//                    toRemove.add(o);
//            for(Object o: toRemove)
//                p.delArc(o);
//        }
//    }

    /*добавление дуги между двумя узлами - исходным и целевым*/
    public void AddArc(String source, String target)
    {
        Node sourceNode = AddOrGetNode(source);
        Node targetNode = AddOrGetNode(target);
        sourceNode.addArc(targetNode);
    };

    /*удаление дуги между двумя узлами - исходным и целевым*/
//    public void DeleteArc(Object source, Object target)
//    {
//        int sourceIndex = nodes.indexOf(source);
//        int targetIndex = nodes.indexOf(target);
//        if (sourceIndex < 0 || sourceIndex >= nodes.size() ||
//                targetIndex < 0 || targetIndex>= nodes.size())
//            return;
//        Node sourceNode = nodes.get(sourceIndex);
//        sourceNode.delArc(target);
//    };


}