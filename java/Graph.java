
import java.util.*;

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
        //в этом месте проблемы!
        int sourceIndex=nodes.indexOf(source);
//        int c = 0;
//        for(Node p: nodes)
//        {
//            if  (!(p.equals(source)))
//                c++;
//            if (p.equals(source)) {
//                sourceIndex = c;
//                break;
//            }
//        }
        if (sourceIndex==-1)
        {
            AddNode(source);
            sourceIndex = nodes.size() - 1;
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
