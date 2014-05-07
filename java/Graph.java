
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
        System.out.println("Node added: ");
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
        int sourceIndex=nodes.indexOf(new Node(source));

        if (sourceIndex==-1)
        {
            AddNode(source);
            sourceIndex = nodes.size() - 1;
        }
        Node sourceNode = nodes.get(sourceIndex);
        sourceNode.addArc(target);


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

    public void graphFill(String lemma, Synset [] a)throws JWNLException
    {
            /*добавление леммы в граф в виде узла*/
        this.AddNode(lemma);

        for (int i = 0; i != a.length; i++)
        {  //для каждого синсета получение множества указателей на другие синсеты
            Pointer[] b = a[i].getPointers();

            for (int j = 0; j != b.length; j++)
            {   //для каждого указателя получение его целевого синсета
                Synset c = b[j].getTargetSynset();
                //получение множества слов целевого синсета
                Word[] d = c.getWords();

                for (int k = 0; k != d.length; k++)
                {   //для каждой целевой леммы
                    String tar = d[k].getLemma();
                    this.AddArc(lemma, tar);

                }

            }
        }

    }
}