import java.util.*;  
public class InvertedIndex {

    static class Document
    {
        String[] wordInDoc; //array contains the all strings in this doc
        int docIndex; // the index of this doc, ex doc 1, doc 2
        public Document(String[] arr, int idx)
        {
            int n = arr.length;
            wordInDoc=new String[n];
            for(int i = 0;i<n;i++)
                wordInDoc[i]=arr[i];
            docIndex=idx;
        }
    }
    static class gaser{
        int docIndex;
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
    static class indexer{
        HashMap<String, Vector<gaser>> wordToDocumentMap;
        indexer()
        { 
           wordToDocumentMap = new HashMap<String, Vector<gaser>>(); 
        }
        void fillDoc(Document Doc) // o(n^2)
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
            Document d1 = new Document(arr.get(i),i+1);
            idx.fillDoc(d1);
        }
        idx.print();
    }
    public static void main(String args[]) {
        test();
    }
}
