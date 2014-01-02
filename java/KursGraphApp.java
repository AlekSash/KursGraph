
import java.lang.Integer;
import java.lang.String;
import java.util.*;
import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Pointer;
import net.didion.jwnl.dictionary.Dictionary;


public class KursGraphApp
{
    public static void main(String[] args) throws Exception
    {
        JWNL.initialize(new FileInputStream("file_properties.xml"));
        Dictionary dict = Dictionary.getInstance();
        //итератор проходится по всем существительным
        Iterator it = dict.getIndexWordIterator(POS.NOUN);
        Iterator it1 = dict.getIndexWordIterator(POS.NOUN);
        //файл для записи слов
        PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
        //Мар для записи пары ключ, слово
        Map<Integer, String> words= new TreeMap<Integer, String>();
        Set kv = words.entrySet();
        //матрица смежностей для представления графа
        Integer SMatrix [][] = new Integer[120000][120000];
        for (Integer i1=0; i1!=SMatrix.length; i1++)
            for (Integer j1=0; j1!=SMatrix.length; j1++)
            {
                SMatrix[i1][j1]=0;
            }
        Integer count=0;
        ///заполнение Мар
        while(it1.hasNext())
        {   //текущее слово
            IndexWord cur = (IndexWord)it1.next();
            //получение леммы
            String lem = cur.getLemma();
            count++;
            //помещение леммы по ключу в Мар
            words.put(count, lem);
        }
        ///
        count = 0;
        while(it.hasNext())
        {   //текущее слово
            IndexWord current = (IndexWord)it.next();
            //получение леммы
            String lemma = current.getLemma();
            count++;
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
                        //запускаем итератор
                        Iterator it2 = kv.iterator();
                        while(it2.hasNext())
                        {
                            //доступ к данным Мар через интерфейс Entry
                            Map.Entry m = (Map.Entry)it2.next();
                            //ищем целевую лемму в Мар
                            if(tar.equals((String)m.getValue()))
                            {
                                //достаём её ключ
                                Integer num = (Integer)m.getKey();
                                //заполняем матрицу смежностей
                                SMatrix[count][num] = 1;
                            }
                        }
                        it2.remove();
                        writer.print(tar);
                        writer.print("  ");

                    }
                    writer.println();

                }
            }

            writer.println();
        }
    }
}
