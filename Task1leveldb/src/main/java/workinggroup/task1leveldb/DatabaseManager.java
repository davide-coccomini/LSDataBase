package workinggroup.task1leveldb;

import java.io.File;
import java.io.IOException;
import static org.iq80.leveldb.impl.Iq80DBFactory.bytes;
import org.iq80.leveldb.DB;
import static org.iq80.leveldb.impl.Iq80DBFactory.factory;
import org.iq80.leveldb.Options;
import org.json.JSONObject;

public class DatabaseManager {

    private DB db;
    private int nextId;
    private AuthorManager amanager;
    private BookManager bmanager;
    private PublisherManager pmanager;
    
    
    public DatabaseManager()  {
        nextId = 0;
    }
    
    public AuthorManager getAmanager() {
        return amanager;
    }

    public BookManager getBmanager() {
        return bmanager;
    }

    public PublisherManager getPmanager() {
        return pmanager;
    }
    public DB getDB(){
        return db;
    }
    /* Opens the DB or creates the folder "db" if not present */
    public void open(){
        try {
          Options options = new Options();

          db = factory.open(new File("db"), options);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public int getNextId() {
        return nextId;
    }
    public void setNextId(int nextId) {
        System.out.println("set next id: " + nextId);
        this.nextId = nextId;
    }
    /* Tests the newId to decide which id will be the next */
    public void incrementNextId(int newId){
        System.out.println("set new id: " + newId);
        int oldId = nextId;
        this.nextId = (newId >= oldId) ? (newId+1) : (oldId);
    }
    /* Closes the DB */
    public void close(){
       try{
           db.close();
       }catch(IOException e){
           e.printStackTrace();
       }
    }

    /* Puts a JSONObject into the DB*/ 
    public void createCommit(String key, JSONObject item){
        db.put(bytes(key), bytes(item.toString()));  
    }
    /* initializes the managers and populates the DB if there is no "db" folder */
    void init(AuthorManager amanager, BookManager bmanager, PublisherManager pmanager) {
        this.amanager = amanager;
        this.bmanager = bmanager;
        this.pmanager = pmanager;
        
        File fileFolder = new File("db");
        if(!fileFolder.exists())
            populateDB();  
    }
    
   /* Populates the DB with given values. Only for testing */
    public void populateDB(){
        String [] author0 = {"0"};
        String [] author1 = {"1"};
        String [] author2 = {"2"};
        String [] author3 = {"3"};
        String [] author4 = {"4"};
        String [] author5 = {"5"};
        String [] author6 = {"6"};
        String [] author7 = {"7"};
        String [] author8 = {"8"};
        String [] author9 = {"9"};
        String [] author10 = {"10"};
        String [] author11 = {"11"};
        String [] author12 = {"12"};
        String [] author13 = {"13"};
        String [] author14 = {"14"};
        String [] author15 = {"15", "16" ,"17"};
        String [] author18 = {"18"};

   /*0*/amanager.create("Charles", "Dickens", "Charles John Huffam Dickens (7 February 1812 - 9 June 1870) was an English writer and social critic. He created some of the world's best-known fictional characters and is regarded by many as the greatest novelist of the Victorian era.");
        nextId++;
        amanager.create("Dante", "Alighieri", "Dante, (Florence 1265 - 1321 Ravenna), was an Italian poet. His Divine Comedy, originally called Commedia and later christened Divina by Giovanni Boccaccio, is widely considered the most important poem of the Middle Ages and the greatest literary work in the Italian language."); 
        nextId++;   
        amanager.create("Alessandro", "Manzoni", "Alessandro Francesco Tommaso Antonio Manzoni was an Italian poet and novelist. He is famous for the novel The Betrothed (orig. Italian: I promessi sposi) (1827), generally ranked among the masterpieces of world literature. The novel is also a symbol of the Italian Risorgimento, both for its patriotic message and because it was a fundamental milestone in the development of the modern, unified Italian language.");
        nextId++;
        amanager.create("Emilio", "Salgari", "Emilio Salgari (21 August 1862 - 25 April 1911) was an Italian writer of action adventure swashbucklers and a pioneer of science fiction.");
        nextId++;
        amanager.create("Niccolò", "Machiavelli", "Niccolò di Bernardo dei Machiavelli (3 May 1469 - 21 June 1527) was an Italian diplomat, politician, historian, philosopher, writer, playwright and poet of the Renaissance period.");
        nextId++;
        amanager.create("Ludovico", "Ariosto", "Ludovico Ariosto (8 September 1474 - 6 July 1533) was an Italian poet. He is best known as the author of the romance epic Orlando Furioso (1516).");
        nextId++;
        amanager.create("Gabriele", "D'Annunzio", "General Gabriele D'Annunzio, Prince of Montenevoso, Duke of Gallese (12 March 1863 - 1 March 1938), sometimes spelled d'Annunzio, was an Italian poet, journalist, playwright and soldier during World War I.");
        nextId++;
        amanager.create("Italo", "Calvino", "Italo Calvino (15 October 1923 - 19 September 1985) was an Italian journalist and writer of short stories and novels. His best known works include the Our Ancestors trilogy (1952 - 1959), the Cosmicomics collection of short stories (1965), and the novels Invisible Cities (1972) and If on a winter\'s night a traveler (1979).");
        nextId++;
        amanager.create("Johann Wolfgang", "von Goethe", "Johann Wolfgang von Goethe (28 August 1749 - 22 March 1832) was a German writer and statesman. His works include: four novels; epic and lyric poetry; prose and verse dramas; memoirs; an autobiography; literary and aesthetic criticism; and treatises on botany, anatomy, and colour. In addition, numerous literary and scientific fragments, more than 10,000 letters, and nearly 3,000 drawings by him have survived.");
        nextId++;
        amanager.create("Victor", "Hugo", "Victor Marie Hugo (26 February 1802 - 22 May 1885) was a French poet, novelist, and dramatist of the Romantic movement.");
        nextId++;
        amanager.create("Ismail", "Kadare", "Ismail Kadare (born 28 January 1936) is an Albanian novelist, poet, essayist and playwright. He has been a leading literary figure in Albania since the 1960s. He focused on poetry until the publication of his first novel 'The General of the Dead Army' which made him famous outside of Albania. In 1996, he became a lifetime member of the AcadÃƒÂ©mie des Sciences Morales et Politiques of France.");
        nextId++;
        amanager.create("Fyodor", "Dostoevskij", "Fyodor Mikhailovich Dostoevsky (11 November 1821 - 9 February 1881), sometimes transliterated Dostoyevsky, was a Russian novelist, short story writer, essayist, journalist and philosopher. Dostoevsky\'s literary works explore human psychology in the troubled political, social, and spiritual atmospheres of 19th-century Russia, and engage with a variety of philosophical and religious themes.");
        nextId++;
        amanager.create("Lev", "Tolstoj", "Count Lev Nikolayevich Tolstoy (9 September 1828 - 20 November 1910), usually referred to in English as Leo Tolstoy, was a Russian writer who is regarded as one of the greatest authors of all time. He received multiple nominations for Nobel Prize in Literature every year from 1902 to 1906, and nominations for Nobel Peace Prize in 1901, 1902 and 1910, and his miss of the prize is a major Nobel prize controversy.");
        nextId++;
        amanager.create("William", "Shakespeare", "William Shakespeare (26 April 1564 - 23 April 1616) was an English poet, playwright, and actor, widely regarded as the greatest writer in the English language and the world\'s greatest dramatist. His extant works, including collaborations, consist of some 39 plays, 154 sonnets, two long narrative poems, and a few other verses, some of uncertain authorship. His plays have been translated into every major living language and are performed more often than those of any other playwright.");
        nextId++;
        amanager.create("Jules", "Verne", "Jules Gabriel Verne (8 February 1828 - 24 March 1905) was a French novelist, poet, and playwright.Verne's collaboration with the publisher Pierre-Jules Hetzel led to the creation of the Voyages extraordinaires, a widely popular series of scrupulously researched adventure novels including Journey to the Center of the Earth (1864), Twenty Thousand Leagues Under the Sea (1870), and Around the World in Eighty Days (1873).");
        nextId++;
        amanager.create("David", "Halliday", "David Halliday (March 3, 1916 - April 2, 2010) was an American physicist known for his physics textbooks, Physics and Fundamentals of Physics, which he wrote with Robert Resnick. Both textbooks have been in continuous use since 1960 and are available in more than 47 languages.");
        nextId++;
        amanager.create("Robert", "Resnick", "Robert Resnick (January 11, 1923 - January 29, 2014) was a physics educator and author of physics textbooks. He was born in Baltimore, Maryland on January 11, 1923 and graduated from the Baltimore City College high school in 1939. He received his B.A. in 1943 and his Ph.D. in 1949, both in physics from Johns Hopkins University. From 1949 to 1956, he was a member of the faculty at the University of Pittsburgh. He later became a professor at Rensselaer Polytechnic Institute.");
        nextId++;
        amanager.create("Kenneth", "Krane", "Kenneth Samuel Krane (May 15, 1944 - Philadelphia, Pennsylvania) was an American physicist and professor at the Oregon State University. He later got an American Association of Physics Teachers Distinguished Service Certificate in 2017.");
        nextId++;
  /*18*/amanager.create("John", "Tolkien", "John Ronald Reuel Tolkien (3 January 1892 - 2 September 1973) was an English writer, poet, philologist, and academic, who is best known as the author of the classic high fantasy works The Hobbit, The Lord of the Rings, and The Silmarillion.");
        nextId++;
                
 /*19*/ pmanager.create("Hachette Livre", "Paris [FR]");
        nextId++;
        pmanager.create("HarperCollins", "New York [US]");
        nextId++;
        pmanager.create("Macmillan Publishers", "London [UK]");
        nextId++;
        pmanager.create("Simon & Schuster", "New York [US]");
        nextId++;
        pmanager.create("Springer", "Berlin [GER]");
        nextId++;
        pmanager.create("Bonnier Books", "Stockholm [SWE]");
        nextId++;
  /*25*/pmanager.create("John Wiley & Sons Inc.", "Hoboken [US]");
        nextId++;
        
  /*26*/bmanager.create("David Copperfield", "768", "0", "Biography", "3.95", "1948", author0, "20");
        nextId++;
        bmanager.create("The Count of Carmagnola and Adelchis'", "360", "0", "Tragedy", "11.55", "1948", author2, "22");
        nextId++;
        bmanager.create("Sandokan: The Tigers of Mompracem", "272", "1", "Adventure", "16.95", "1948", author3, "23");
        nextId++;
        bmanager.create("The Prince", "88", "3", "Political treatise", "5.99", "1948", author4, "24");
        nextId++;
        bmanager.create("Orlando Furioso", "656", "12", "Classics", "17.95", "1944", author5, "22");
        nextId++;
        bmanager.create("The Baron In The Trees", "288", "7", "Classics", "11.50", "1999", author7, "19");   
        nextId++;
        bmanager.create("Faust", "496", "3", "Tragedy", "20.00", "1901", author8, "21");
        nextId++;
        bmanager.create("Les Miserables","332", "4", "Historical", "17.99", "1945", author9, "22");
        nextId++;
        bmanager.create("Chronicle in Stone: A Novel", "322", "1", "Historical","12.00", "1945", author10, "19");
        nextId++;
        bmanager.create("Crime and Punishment", "565", "0", "Psychological", "14.99", "1948", author11, "24");
        nextId++;
        bmanager.create("War and Peace", "928", "5", "Historical", "15.70", "1948", author12, "23");
        nextId++;
        bmanager.create("Hamlet", "289", "4", "Tragedy", "5.99", "1609", author13, "20");
        nextId++;
        bmanager.create("Romeo and Juliet", "336", "6", "Tragedy", "5.50", "1948", author13, "19");
        nextId++;
        bmanager.create("From the Earth to the Moon", "338", "9", "Science Fiction", "12.50", "2000", author14, "21");
        nextId++;
        bmanager.create("Twenty Thousand Leagues Under the Seas", "518", "2", "Science Fiction", "13.50", "1999", author14, "24");
        nextId++;
        bmanager.create("Il Signore Degli Anelli", "400", "2", "Fantasy", "20.00", "1948", author18, "19");
        nextId++;
  /*44*/bmanager.create("Physics 1", "684", "3", "Physics", "67.00", "2003", author15, "25");
        nextId++;
    }
}
