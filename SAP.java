import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
	private final Digraph G;
	private BreadthFirstDirectedPaths BFSOfv;
	private BreadthFirstDirectedPaths BFSOfw;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
    		Digraph newG = new Digraph(G);   // call the copy constructor
    		this.G = newG;
    }

   // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
    		int commonAnces = ancestor(v,w);
    		if(commonAnces==-1) return -1;   // no common ancestor, return -1;
    		return BFSOfv.distTo(commonAnces)+BFSOfw.distTo(commonAnces);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
    		BFSOfv = new BreadthFirstDirectedPaths(G,v);
		BFSOfw = new BreadthFirstDirectedPaths(G,w);
		int minLen = Integer.MAX_VALUE;
		int res = -1;
    		for(int u=0;u<G.V();u++) {
    			if(BFSOfv.hasPathTo(u) && BFSOfw.hasPathTo(u)) {
    				int tempLen = BFSOfv.distTo(u)+BFSOfw.distTo(u);
    				if( tempLen<minLen ) {
    					minLen = tempLen;
    					res = u;
    				}
    			}
    		}
	    return res;
    } 
   

   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
    		int commonAnces = ancestor(v,w);
		if(commonAnces==-1) return -1;   // no common ancestor, return -1;
		return BFSOfv.distTo(commonAnces)+BFSOfw.distTo(commonAnces);
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
    		BFSOfv = new BreadthFirstDirectedPaths(G,v);
		BFSOfw = new BreadthFirstDirectedPaths(G,w);
		int minLen = Integer.MAX_VALUE;
		int res = -1;
    		for(int u=0;u<G.V();u++) {
    			if(BFSOfv.hasPathTo(u) && BFSOfw.hasPathTo(u)) {
    				int tempLen = BFSOfv.distTo(u)+BFSOfw.distTo(u);
    				if( tempLen<minLen ) {
    					minLen = tempLen;
    					res = u;
    				}
    			}
    		}
	    return res;
    }
    
	// do unit testing of this class
	public static void main(String[] args) {
		In in = new In(args[0]);
	    Digraph G = new Digraph(in);
	    SAP sap = new SAP(G);
	    while (!StdIn.isEmpty()) {
	        int v = StdIn.readInt();
	        int w = StdIn.readInt();
	        int length   = sap.length(v, w);
	        int ancestor = sap.ancestor(v, w);
	        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
	    }
    } 	

}
