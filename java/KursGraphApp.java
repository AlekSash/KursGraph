
import java.util.*;
import java.io.*;
import net.didion.jwnl.JWNL;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.dictionary.Dictionary;

public class KursGraphApp
{
    public static void main(String[] args) throws Exception
    {
	JWNL.initialize(new FileInputStream("file_properties.xml"));
	Dictionary dict = Dictionary.getInstance();
	Iterator it = dict.getIndexWordIterator(POS.NOUN);
	while(it.hasNext())
	    System.out.println(it.next());
    }
}
