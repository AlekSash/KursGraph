
import java.io.FileInputStream;
import net.didion.jwnl.JWNL;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.dictionary.Dictionary;

public class KursGraphApp
{
    private IndexWord DOG;

    public KursGraphApp() throws Exception 
    {
        DOG = Dictionary.getInstance().getIndexWord(POS.NOUN, "dog");
    }

    public void go() throws Exception
    {
        for (Synset o : DOG.getSenses())
            System.out.println(o);
    }

    public static void main(String[] args) 
    {
        String _PROPS_FILE = "file_properties.xml";
        try {
            JWNL.initialize(new FileInputStream(_PROPS_FILE));
            new KursGraphApp().go();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
    }
}
