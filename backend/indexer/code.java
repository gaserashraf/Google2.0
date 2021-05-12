import java.io.IOException;
import java.util.*; 
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class InvertedIndex {

    static class DocumentClass
    {
        String[] wordInDoc; //array contains the all strings in this doc
       
        String title; // title of the Document
        Vector<String> headings; // all headings of the Document
        Vector<String> paragrahs;// all normal text of the Document
        int docIndex; // the index of this doc, ex doc 1, doc 2
        public DocumentClass(String[] arr, int idx)
        {
            int n = arr.length;
            wordInDoc=new String[n];
            for(int i = 0;i<n;i++)
                wordInDoc[i]=arr[i];
            docIndex=idx;
        }
        public DocumentClass()
        {
            headings = new Vector<String>();
            paragrahs = new Vector<String>();
        }
    }
    static class gaser{
        int docIndex;
        // to do make it array of string carry the types of the singe word was mentioned ???? ???? football mentioned as a h1 and paragrah and in title
        String type;//heading normal ... should be array of Strings?
        //ex word football was a heading and paragrah
        int TF = 0;
        public gaser()
        {
           
        }
        public gaser(int d,String t)
        {
            docIndex =d;
            type=t;
            TF=1;
        }
    }
    static class indexer
    {
        HashMap<String, Vector<gaser>> wordToDocumentMap;
        indexer()
        { 
           wordToDocumentMap = new HashMap<String, Vector<gaser>>(); 
        }
        void fillDoc(DocumentClass Doc) // o(n^2)
        {
            String type = "NORMAL"; // To Do get the type 
            gaser g1 =new gaser(Doc.docIndex,type);
            for(int i =0;i<Doc.wordInDoc.length;i++)
            {
                String temp = Doc.wordInDoc[i];
                if(wordToDocumentMap.get(temp)==null) // first time this word
                {
                    Vector<gaser>vec = new Vector<gaser>(1);
                    vec.add(g1);
                    wordToDocumentMap.put(temp, vec);
                }
                else
                {
                    boolean found = false;
                    Vector<gaser>vec = wordToDocumentMap.get(temp);
                    for(int j = 0;j<vec.size();j++)
                    {
                        if(vec.get(j).docIndex==Doc.docIndex)
                        {   
                            found=true;
                            vec.get(j).TF++;
                            break;
                        }
                    }
                    if(found)
                        wordToDocumentMap.replace(temp, vec);
                    else
                    {
                        vec.add(g1);
                        wordToDocumentMap.replace(temp, vec);
                    }
                }
            }
        }
        void print()
        {
             
            /* Display content using Iterator*/
            Set set = wordToDocumentMap.entrySet();
            Iterator iterator = set.iterator();
            while(iterator.hasNext()) {
               Map.Entry mentry = (Map.Entry)iterator.next();
               System.out.print(mentry.getKey()+" : ");
               Vector<gaser>vec=(Vector)mentry.getValue();
               System.out.print("DF = "+ vec.size()+ " ");
               for(int i = 0;i < vec.size();i++)
               {
                   gaser g1 =vec.get(i);
                   System.out.print("Doc index = "+g1.docIndex+" type is " + g1.type + " Tf = " + g1.TF + " ");
               }
               System.out.println();
            }
        }
    }
   static DocumentClass htmlTODoc(String html)
   {
       // this function take an html document like this "<html><head><title>Sample Title</title></head>"
       // and convert it to an document class which impemeted ???
      DocumentClass D = new DocumentClass();
      Document document = Jsoup.parse(html);
      D.title=document.title(); // get title
      Elements paragraphs = document.getElementsByTag("p"); // first get all paragraphs
      for (Element paragraph : paragraphs) {
            D.paragrahs.add(paragraph.text());
      }
      Elements h1 = document.getElementsByTag("h1"); // get all h1
      for (Element h : h1) {
            D.headings.add(h.text());
      }
      Elements h2 = document.getElementsByTag("h2"); // get all h2
      for (Element h : h2) {
            D.headings.add(h.text());
      }
      Elements h3 = document.getElementsByTag("h3");  // get all h3
      for (Element h : h3) {
            D.headings.add(h.text());
      }
      Elements h4 = document.getElementsByTag("h4");  // get all h4
      for (Element h : h4) {
            D.headings.add(h.text());
      }
      return D;
   }
   static DocumentClass htmlLinkTODoc(String Link) throws IOException
   {
       //this function like the funntion ??? ?????  but take the link of the document
      DocumentClass D = new DocumentClass();
      Document document = Jsoup.connect(Link).get();
      D.title=document.title();
      Elements paragraphs = document.getElementsByTag("p");
      for (Element paragraph : paragraphs) {
            D.paragrahs.add(paragraph.text());
      }
      Elements h1 = document.getElementsByTag("h1");
      for (Element h : h1) {
            D.headings.add(h.text());
      }
      Elements h2 = document.getElementsByTag("h2");
      for (Element h : h2) {
            D.headings.add(h.text());
      }
      Elements h3 = document.getElementsByTag("h3");
      for (Element h : h3) {
            D.headings.add(h.text());
      }
      Elements h4 = document.getElementsByTag("h4");
      for (Element h : h4) {
            D.headings.add(h.text());
      }
      return D;
   }
   static void test()
    {
        indexer idx = new indexer();
        Vector<String[]> arr=new Vector<String[]>();
        String tmp[] = new String[]{"hello","everyone"};
        arr.add(tmp);
        tmp = new String[]{"this" ,"article","is" ,"is","based","on","inverted","index"};
        arr.add(tmp);
        tmp = new String[]{"which" ,"is","hashmap","like","data","structure"};
        arr.add(tmp);
        for(int i = 0;i < arr.size(); i++)
        {
            DocumentClass d1 = new DocumentClass(arr.get(i),i+1);
            idx.fillDoc(d1);
        }
        idx.print();
    }
    public static void main(String args[]) {
        test();
    }
}
