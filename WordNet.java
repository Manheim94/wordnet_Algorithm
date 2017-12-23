import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

public class WordNet {
	
   private final Map<Integer,String> id2synset = new HashMap<>();
   private final Map<String,Set<Integer>> non2id = new HashMap<>();
   private Digraph digraph;
   private final SAP sap;
	
	
   // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms) {
	   readSynsets(synsets);
	   readHypernyms(hypernyms);
	   sap = new SAP(digraph);
   }
   
   private void readSynsets(String synsets) {
	   In input = new In(synsets);
	   while(input.hasNextLine()) {
		   String[] strList = input.readLine().split(",");
		   int id = Integer.parseInt(strList[0]);
		   id2synset.put(id, strList[1]);
		   
		   String[] nounList = strList[1].split(" ");
		   for(String noun:nounList) {
			   if(non2id.containsKey(noun)) {
				   non2id.get(noun).add(id);
			   }
			   else {
				   Set<Integer> set = new HashSet<>();
				   set.add(id);
				   non2id.put(noun, set);
			   }
			   
		   }
	   }
	   
	   input.close();
   }
   
   private void readHypernyms(String hypernyms) {
	   In input = new In(hypernyms);
	   digraph = new Digraph(id2synset.size());
	   while(input.hasNextLine()) {
		   String[] list = input.readLine().split(",");
		   for(int i=1;i<list.length;i++) {
			   digraph.addEdge(Integer.parseInt(list[0]),Integer.parseInt(list[i]));
		   }
	   }
	   input.close();
	   
	   // detect cycle
	   DirectedCycle dc = new DirectedCycle(digraph);
	   if(dc.hasCycle()) {
		   throw new IllegalArgumentException();
	   }
	   
	   // detect root
	   int rootCount=0;
	   for(int i=0;i<id2synset.size();i++) {
		   if( digraph.outdegree(i)==0 ) {
			   rootCount++;
		   }
	   }
	   if(rootCount!=1) {
		   throw new IllegalArgumentException();
	   }
	   
   }

   // returns all WordNet nouns
   public Iterable<String> nouns(){
	   return non2id.keySet();
   }

   // is the word a WordNet noun?
   public boolean isNoun(String word) {
	   if(word==null) throw new IllegalArgumentException();
	   return non2id.containsKey(word);
   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB) {
	   
	   return sap.length( non2id.get(nounA),non2id.get(nounB) );

   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB) {
	   int id = sap.ancestor(non2id.get(nounA),non2id.get(nounB));
	   return id2synset.get(id);
   }
   
   
   // do unit testing of this class
   public static void main(String[] args){
	   
   }

}
