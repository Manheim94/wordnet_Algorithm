import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
	private final WordNet wordnet;
	
	public Outcast(WordNet wordnet) {         // constructor takes a WordNet object
		this.wordnet = wordnet;
	}
	
	public String outcast(String[] nouns) {  // given an array of WordNet nouns, return an outcast
		int[][] distance = new int[nouns.length][nouns.length];
		int minID = 0;
		int max = 0;
		
		for(int i=0;i<nouns.length;i++) {
			for(int j=i+1;j<nouns.length;j++) {
				distance[i][j] = wordnet.distance(nouns[i],nouns[j]);
			}
		}
		
		for(int i=0;i<nouns.length;i++) {
			int sum = 0;
			for(int j=0;j<nouns.length;j++) {
				if(i<j) sum+= distance[i][j];
				else sum+= distance[j][i];
			}
			if(i==0 || sum>max) {
				max = sum;
				minID = i;
			}
		}
		return nouns[minID];
	}
	
	public static void main(String[] args) {  // see test client below
		WordNet wordnet = new WordNet(args[0], args[1]);
	    Outcast outcast = new Outcast(wordnet);
	    for (int t = 2; t < args.length; t++) {
	        In in = new In(args[t]);
	        String[] nouns = in.readAllStrings();
	        outcast.outcast(nouns);
	        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
	    }
	}
}
