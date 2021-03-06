import java.util.Scanner;
import java.io.File; 
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*; 
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.sql.*;

public class InvertedIndex {

    static class DocumentClass
    {
        //String title; // title of the Document //No need of it 
        Vector<String> headings; // all headings of the Document
        Vector<String> paragrahs;// all normal text of the Document
        int docIndex; // the index of this doc, ex doc 1, doc 2
        public DocumentClass(int idx)
        {
            docIndex=idx;
            headings = new Vector<String>();
            paragrahs = new Vector<String>();
        }
    }
    static class WordInfo{
        int docIndex;
        // to do make it array of string carry the types of the singe word was mentioned ???? ???? football mentioned as a h1 and paragrah and in title
        String type;//heading or normal or both
        //ex word football was a heading and paragrah
        int TF;
        public WordInfo()
        {
            TF=1;
        }
        public WordInfo(int d,String t)
        {
            docIndex =d;
            type=t;
            TF=1;
        }
        public WordInfo(int d,String t, int tf)
        {
            docIndex =d;
            type=t;
            TF=tf;
        }
    }
    static class indexer
    {
        HashMap<String, Vector<WordInfo>> wordToDocumentMap;
        HashMap<String, Integer> stopWords;
        indexer()
        { 
           wordToDocumentMap = new HashMap<String, Vector<WordInfo>>(); 

           // add all stop words
            stopWords = new HashMap<String, Integer>(); 
            try {
                Scanner myReader = new Scanner(new File("C:\\Users\\Lenovo\\Documents\\NetBeansProjects\\mavenproject1\\src\\main\\java\\stopWords.txt"));
                while (myReader.hasNextLine()) {
                  String word = myReader.nextLine();
                  stopWords.put(word,1);
                }
                myReader.close();
              } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
              }
        }
        void indexDoc(DocumentClass Doc) // o(n^2)
        {
            //fill all headings
            String type = "HEADING"; // To Do get the type 
            
            for(int i =0;i<Doc.headings.size();i++)
            {
                String temp = Doc.headings.get(i);
                if(stopWords.get(temp)!=null) continue; // the word is stop word , is about....
                
                /*Stemmer S1 = new Stemmer (temp); //stem words
                S1.stem();
                temp =S1.toString();*/
                if(wordToDocumentMap.get(temp)==null) // first time this word
                {
                    WordInfo wordInfo =new WordInfo(Doc.docIndex,type);
                    Vector<WordInfo>vec = new Vector<WordInfo>(1);
                    vec.add(wordInfo);
                    wordToDocumentMap.put(temp, vec);
                }
                else
                {
                    boolean found = false;
                    Vector<WordInfo>vec = wordToDocumentMap.get(temp);
                    for(int j = 0;j<vec.size();j++)
                    {
                        if(vec.get(j).docIndex==Doc.docIndex)
                        {   
                            found=true;
                            vec.get(j).TF = vec.get(j).TF + 1;
                            wordToDocumentMap.replace(temp, vec);
                            break;
                        }
                    }
		    if(found==false)
		    {
			WordInfo wordInfo =new WordInfo(Doc.docIndex,type);
			vec.add(wordInfo);
			wordToDocumentMap.replace(temp, vec);
		    }
                }
            }
            // fill all normals
            String type1 = "NORMAL"; // To Do get the type 
            
            for(int i =0;i<Doc.paragrahs.size();i++)
            {
                String temp = Doc.paragrahs.get(i);
                if(stopWords.get(temp)!=null) continue; // the word is stop word , is about....
                
                /*Stemmer S1 = new Stemmer (temp); //stem words
                S1.stem();
                temp =S1.toString();*/
                if(wordToDocumentMap.get(temp)==null) // first time this word
                {
                    WordInfo wordInfo1 =new WordInfo(Doc.docIndex,type1);
                    Vector<WordInfo>vec = new Vector<WordInfo>(1);
                    vec.add(wordInfo1);
                    wordToDocumentMap.put(temp, vec);
                }
                else
                {
                    boolean found = false;
                    Vector<WordInfo>vec = wordToDocumentMap.get(temp);
                    for(int j = 0;j<vec.size();j++)
                    {
                        if(vec.get(j).docIndex==Doc.docIndex)
                        {   
                            found=true;
                            vec.get(j).TF = vec.get(j).TF + 1;
                            if(vec.get(j).type.equals("HEADING"))
                                vec.get(j).type="BOTH";
                            wordToDocumentMap.replace(temp, vec);
                            break;
                        }
                    }
	  	    if(found==false)
		    {
			WordInfo wordInfo =new WordInfo(Doc.docIndex,type1);
			vec.add(wordInfo);
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
               Vector<WordInfo>vec=(Vector)mentry.getValue();
               System.out.print("DF = "+ vec.size()+ " ");
               for(int i = 0;i < vec.size();i++)
               {
                   WordInfo wordInfo =vec.get(i);
                   System.out.print("Doc index = "+wordInfo.docIndex+" type is " + wordInfo.type + " Tf = " + wordInfo.TF + " ");
               }
               System.out.println();
            }
        }
        //this function to store the search index to the database
        void storeCurrIndex(){
            try{
                String url="jdbc:mysql://localhost:3306/searchindex";
                String user="root";
                String password="12345678";
                // 1. Get a connection to the database 
                Connection myCon = DriverManager.getConnection(url,user,password );
                // 2. Create a statement
                //Statement myStat = myCon.createStatement();
                Set set = wordToDocumentMap.entrySet();
                Iterator iterator = set.iterator();
                while(iterator.hasNext()) {
                   Map.Entry mentry = (Map.Entry)iterator.next();
                   String word = (String)mentry.getKey();
                   Vector<WordInfo>vec=(Vector)mentry.getValue();
                   int df =  vec.size();
                   ////String sql="INSERT INTO words (word , df) VALUES('"+word+"',"+df+")";
                   String sql="INSERT INTO words (word , df) VALUES(?,?)";
                   PreparedStatement myStat = myCon.prepareStatement(sql);
                   myStat.setString(1, word);
                   myStat.setInt(2, df);
                   myStat.executeUpdate();
                   for(int i = 0;i < vec.size();i++)
                   {
                       WordInfo wordInfo =vec.get(i);
                       ////sql="INSERT INTO wordDocs (word , docIndex, tf, type ) VALUES('"+word+"',"+wordInfo.docIndex+ ","+wordInfo.TF+",'"+wordInfo.type+"')";
                       sql="INSERT INTO wordDocs (word , docIndex, tf, type ) VALUES(?,?,?,?)";
                       myStat = myCon.prepareStatement(sql);
                       myStat.setString(1, word);
                       myStat.setInt(2, wordInfo.docIndex);
                       myStat.setInt(3, wordInfo.TF);
                       myStat.setString(4, wordInfo.type);
                       myStat.executeUpdate();
                   }
                   myStat.close();
                }
                myCon.close();
                System.out.println("Insertion completed");          
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        //this function to restore the search index to the database
        void  restoreCurrIndex(){
            try{
                String url="jdbc:mysql://localhost:3306/searchindex";
                String user="root";
                String password="12345678";
                Connection myCon = DriverManager.getConnection(url,user,password );
                String sql="SELECT * FROM searchindex.words;";
                PreparedStatement myStat = myCon.prepareStatement(sql);
                ResultSet rs = myStat.executeQuery();
                HashMap <String,Integer> words = new HashMap<String,Integer>();
                while(rs.next()){
                    String word = rs.getString("word");
                    int df = rs.getInt("df");
                    words.put(word, df);
                }
                sql="SELECT * FROM searchindex.worddocs;";
                myStat = myCon.prepareStatement(sql);
                rs = myStat.executeQuery();
                while(rs.next()){
                    String word = rs.getString("word");
                    // 1. Get the df of the word from the words Hashmap
                    int df = words.get(word);	
                    // 2. Loop df times to fill the vector corresponding to each word 
                    Vector<WordInfo>vec = new Vector<WordInfo>(df);
                    for(int i =0 ; i < df ; i++){
                        int docIndex = rs.getInt("docIndex");
                        int tf = rs.getInt("tf");
                        String type = rs.getString("type");
                        WordInfo wordInfo =new WordInfo(docIndex,type,tf);
                        vec.add(wordInfo);
                        if(i==df-1) break; 
                        rs.next();
                    }
                    // 3. Insert the pair to the wordToDocumentMap
                    wordToDocumentMap.put(word, vec);
                }
                myStat.close();
                myCon.close();
                System.out.println("Restoring completed");          
            }catch(Exception ex){
                ex.printStackTrace();
            } 
        }
        void initializeCurrIndex(){
            restoreCurrIndex();
            clearDatabase();
        }
        void clearSearchIndex(){
            try{
                String url="jdbc:mysql://localhost:3306/searchindex";
                String user="root";
                String password="12345678";
                // 1. Get a connection to the database 
                Connection myCon = DriverManager.getConnection(url,user,password );
                // 2. Create a statement
                Statement myStat = myCon.createStatement();
                // 3. execute query
                String sql="DELETE FROM worddocs";
                myStat.execute(sql);
                sql="DELETE FROM words";
                myStat.execute(sql);
                myStat.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        void storeDocInfo( String link , int docIndex ){
            try {
            String url="jdbc:mysql://localhost:3306/searchindex";
            String user="root";
            String password="12345678";
            Document document = Jsoup.connect(link).get();
            String title=document.title();
            String description = document.select("meta[name=description]").get(0).attr("content");
            Connection myCon = DriverManager.getConnection(url,user,password );
            String sql="INSERT INTO docLink (link , docIndex, title, description) VALUES(?,?,?,?)";
            PreparedStatement myStat = myCon.prepareStatement(sql);
            myStat.setString(1, link);
            myStat.setInt(2, docIndex);
            myStat.setString(3, title);
            myStat.setString(4, description);
            myStat.executeUpdate();
            myStat.close();
            myCon.close();
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        void clearDocsInfo(){
            try{
                String url="jdbc:mysql://localhost:3306/searchindex";
                String user="root";
                String password="12345678";
                // 1. Get a connection to the database 
                Connection myCon = DriverManager.getConnection(url,user,password );
                // 2. Create a statement
                Statement myStat = myCon.createStatement();
                // 3. execute query
                String sql="DELETE FROM docLink";
                myStat.execute(sql);
                myStat.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        void clearWordsSearch(){
            try{
                String url="jdbc:mysql://localhost:3306/searchindex";
                String user="root";
                String password="12345678";
                // 1. Get a connection to the database 
                Connection myCon = DriverManager.getConnection(url,user,password );
                // 2. Create a statement
                Statement myStat = myCon.createStatement();
                // 3. execute query
                String sql="DELETE FROM searchwords";
                myStat.execute(sql);
                myStat.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        void clearDatabase(){
            clearSearchIndex();
            clearDocsInfo();
        }
    }
    static DocumentClass htmlTODoc(String html,int idx)
   {
       // this function take an html document like this "<html><head><title>Sample Title</title></head>"
       // and convert it to an document class which impemeted ???
      DocumentClass D = new DocumentClass(idx);
      Document document = Jsoup.parse(html);
      //D.title=document.title(); // get title
      String [] HTMLElements = new String[] {"h1","h2","h3","h4","h5","h6","p","em","b","i","u","a"};
      for(int i = 0; i<HTMLElements.length;i++){
        Elements elements = document.getElementsByTag(HTMLElements[i]);
        for (Element element : elements) {
            String text = element.text();
            text = text.replaceAll("[^a-zA-Z]", " ");  //remove all the special characters and numbers
            String[] words = text.split("\\s");//splits the string based on whitespace 
            for(String word:words){  
                word=word.toLowerCase();
                word=word.replaceAll(" ", "");
                if(word=="") continue;
                if(element.equals("h1")||element.equals("h2")||element.equals("h3")||element.equals("h4")||element.equals("h5")||element.equals("h6")){
                   //*Stem the word before storing it 
                    D.headings.add(word); 
                }else {
                    D.paragrahs.add(word);
                }
            }
          }
      }
      return D;
   }
    static DocumentClass htmlLinkTODoc(String Link,int idx) throws IOException
   {
      DocumentClass D = new DocumentClass(idx);
      Document document = Jsoup.connect(Link).get();
      // 1.Get the title and the description and store it
      //D.title=document.title(); //no need to store it here
      //D.description=document.description();
      String [] HTMLElements = new String[] {"h1","h2","h3","h4","h5","h6","p","em","b","i","u","a"};
      for(int i = 0; i<HTMLElements.length;i++){
        Elements elements = document.getElementsByTag(HTMLElements[i]);
        for (Element element : elements) {
            String text = element.text();
            text = text.replaceAll("[^a-zA-Z]", " ");  //remove all the special characters and numbers
            String[] words = text.split("\\s");//splits the string based on whitespace 
            for(String word:words){  
                word=word.toLowerCase();
                word=word.replaceAll(" ", "");
                if(word=="") continue;
                if(element.equals("h1")||element.equals("h2")||element.equals("h3")||element.equals("h4")||element.equals("h5")||element.equals("h6")){
                   //*Stem the word brfore storing it 
                    D.headings.add(word); 
                }else {
                    D.paragrahs.add(word);
                }
            }
        }
      }
      return D;
   }
   static void indexCurrLinks() throws IOException
   {
       try {
           int docIndex = 0;
           indexer idx = new indexer();
           Scanner myReader = new Scanner(new File("C:\\Users\\Lenovo\\Documents\\NetBeansProjects\\mavenproject1\\src\\main\\java\\links.txt"));
           while (myReader.hasNextLine()) {
             String link = myReader.nextLine();
             // 1. store the document info and index to the database
             idx.storeDocInfo(link,docIndex);
             // 2. Convert the html page to our document data structure then index it 
             idx.indexDoc( htmlLinkTODoc(link,docIndex));
             docIndex++;
           }
           myReader.close();
           idx.storeCurrIndex(); // Store the current searchIndex  
         } catch (FileNotFoundException e) {
           System.out.println("An error occurred.");
           e.printStackTrace();
         }
   }

    static void test() throws IOException
    {
        
        indexer idx = new indexer();
        //DocumentClass d1 = htmlLinkTODoc("https://stackoverflow.com/questions/12526979/jsoup-get-all-links-from-a-page#");
        //DocumentClass d1 = htmlLinkTODoc("https://mobirise.com/website-templates/sample-website-templates/",0);
        //idx.storeDocInfo("https://codeforces.com/",4);
        //idx.restoreCurrIndex();
        //idx.indexDoc(d1);
        //idx.indexDoc(htmlLinkTODoc("https://codeforces.com/",4));
        //idx.print();
        //idx.storeCurrIndex();
        idx.clearSearchIndex();
        idx.clearDocsInfo();
        idx.clearWordsSearch();
    }
    public static void main(String args[]) throws IOException {
        //test();
        indexCurrLinks();
    }
}
