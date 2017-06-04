import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

/**
 * @author Jeremiah Pinto(21545883) and Neosh Sheikh(21959462)
 * CITS2200 Project
 */

//Class that contains the contents of the graph
class Graph
{
	public ArrayList<LinkedList<Integer>> p; //Adjacency list of the edges in the graph
	public LinkedList<String> s;			  //List of all the vertices
	public static final int INFINITY = Integer.MAX_VALUE/2;

	Graph()
	{
		p = new ArrayList<LinkedList<Integer>>();
		s = new LinkedList<String>();
	}

	//A method that adds and edge between the "from" and "to" vertices,
	//if either doesn't exist, it creates them
	void addEdge(String from, String to)
	{
		int locF = -1; int locT = -1; //Variables to get the indices of the "from" and "to" vertices if present

		for(int i = 0; i < s.size(); i++ )
		{
			if( s.get(i).equals(to) )
				locT = i;

			if( s.get(i).equals(from))
				locF = i;

			//Break the loop if both, "from" and "to" are found
			if(locT != -1 && locF != -1) 
				break;
		}

		// If the "from" vertex is not present in the graph, creates it
		if( locF == -1 )
		{
			LinkedList<Integer> fromList = new LinkedList<Integer>();

			locF = p.size();
			fromList.add(locF);
			p.add(fromList);
			s.add(from);
		}

		//If the "to" vertex is present in the graph, adds an edge from the "from" vertex to it
		//else it creates the "to" vertex and then adds an edge from the "from" vertex to it
		if( locT != -1 )
			p.get(locF).add(locT);		
		else
		{
			LinkedList<Integer> toList = new LinkedList<Integer>();

			locT = p.size();
			toList.add(locT);	
			p.get(locF).add(locT);
			p.add(toList);
			s.add(to);
		}		
	}

	//A method to return the index when the vertex is passed as a parameter,
	//if not present, returns -1
	int indexOf(String str)
	{
		for(int i = 0; i< s.size(); i++)
			if(s.get(i).equals(str)) 
				return i;
		return -1;
	}

	//A method that returns 1 if the parameter "b" is present in the row of the adjacency list of the parent "a",
	//else it returns INFINITY
	int dist(int a, int b)
	{
		if(p.get(a).contains(b)) 
			return 1;
		return INFINITY;
	}

	String getVertex(int i) 
	{	return s.get(i);	}

	//	ArrayList<LinkedList<Integer>> getEdge() //
	//	{	return p;	}

	LinkedList<Integer> getRow(int i) 
	{	return p.get(i);	}

	int size()
	{	return s.size();	}

	static Graph transpose(Graph G)
	{
		Graph RG = new Graph();

		for( int i = 0; i < G.size();i++ )
		{
			LinkedList<Integer> temp = new LinkedList<Integer>();

			temp.add(i);
			RG.p.add(temp);
			RG.s.add(G.s.get(i));
		}

		for( LinkedList<Integer> x : G.p )
			for( int child : x )
				if( child != x.get(0) ) 
					RG.addEdge(G.getVertex(child), G.getVertex(x.get(0)) );

		return RG;
	}
}
public class MyCITS2200Project implements CITS2200Project
{

	public Graph page;
	public static final int INFINITY = Integer.MAX_VALUE/2;
	public MyCITS2200Project()
	{	page = new Graph();	}

	//A method that adds an edge from the "from" vertex to the "to" vertex
	@Override
	public void addEdge(String from, String to) 
	{	page.addEdge(from, to);	}
	
	public int getNum(String s)
	{
		int i = -1;
		if(page.s.contains(s))page.s.indexOf(s);
		return i;
	}

	//A method that gets the shortest path from the "from" vertex to the "to" vertex
	//using a breadth first search
	@Override
	public int getShortestPath(String urlFrom, String urlTo) 
	{
		boolean contFrom = false, contTo = false; 
		int from = 0;
		if(urlFrom.equals(urlTo)) return 0;
		for( int i = 0; i<page.size(); i++ )
		{
			if( page.getVertex(i).equals(urlTo) )	
				contTo = true;	

			if( page.getVertex(i).equals(urlFrom))
			{
				contFrom = true;
				from = i;
			}

			if( contTo && contFrom ) 
				break;
		}

		if( !contTo || !contFrom ) 
			return -1;

		int dist[] = new int[page.size()];
		int visited[] = new int[page.size()];
		LinkedList<Integer> queue = new LinkedList<Integer>();
		int disA_B = -1;
		for(int x = 0; x < dist.length; x++)
			dist[x] = visited[x] = -1;
		
		visited[from] = -2; 
		dist[from] = 0; 
		queue.add(from);

		while( !queue.isEmpty() )
		{
			int x = queue.remove();

			// checks the size of the linked-list at x and it's children 
			for( int index : page.getRow(x) )
			{
				if( index != x && visited[index] == -1 )
				{
					visited[index] = x;

					queue.add(index);
					dist[index] = dist[x]+1;
				}
				//Finds the first instance of urlTo and if found, assigns distance to distA_B
				//Breaks out of the loop if it's found
				if( index != x && page.getVertex(index).equals(urlTo) )
				{
					queue.clear();		//used to exit out of the while loop
					disA_B = dist[index];
					break;
				}
			}
		}
		return disA_B;
	}

	/*
	 * A method that finds the eccentricity of the graph using a BFS from every vertex
	 * starting from the "from" vertex until the end of the graph
	 * 
	 * @param from, the vertex from which the BFS must start
	 */
	private int findE(int from)
	{
		
		int dist[] = new int[page.size()];
		int visited[] = new int[page.size()];
		
		LinkedList<Integer> q = new LinkedList<Integer>(); 	//q is a queue
		int maxD = 0;
		for(int x = 0; x < dist.length; x++)
			dist[x] = visited[x] = -1;

		int count = 0;
		visited[from]= -2; 
		dist[from]=0; 
		
		q.add(from);
		
		while(!q.isEmpty())
		{
			int x = q.remove();
			
			// checks the size of the linked-list at x and it's children 
			for(int index : page.getRow(x))
			{
				if(index == x) continue;
				if(visited[index] == -1)
				{
					count++;
					visited[index]=x;
					q.add(index);
					dist[index]=dist[x]+1;
					if(dist[index]>maxD)
						maxD=dist[index];
				}
			}
		}

//		for(int i = 0; i < visited.length; i++)
//			if(dist[i]==-1) return Integer.MAX_VALUE;
		if(count< page.size() -1) return INFINITY;
		return maxD;
	}

	/**
	 * Finds all the centers of the page graph. The order of pages
	 * in the output does not matter. Any order is correct as long as
	 * all the centers are in the array, and no pages that aren't centers
	 * are in the array.
	 * 
	 * @return an array containing all the URLs that correspond to pages that are centers.
	 */
	
	@Override
	public String[] getCenters() 
	{
		
		String[] result;
		int[] ecc = new int[page.size()];
		int minMax = INFINITY;
		
		ArrayList<Integer> storage = new ArrayList<Integer>();
		for( int i = 0 ; i<page.size();i++)
		{
			ecc[i]=findE(i);
			if(ecc[i]<=minMax && ecc[i] != INFINITY)
			{
				if(ecc[i]<minMax)
				{
					storage.clear();
					minMax = ecc[i];
				}
				storage.add(i);
			}
		}
		
		result = new String[storage.size()];
		for(int j = 0; j<storage.size();j++)
			result[j] = page.getVertex(storage.get(j));
		return result;
	}

	//Finds SCC's using Kosaraju's algorithm
	@Override
	public String[][] getStronglyConnectedComponents() 
	{
		int size = page.size();
		boolean[] used = new boolean[size];
		
		ArrayList<Integer> order = new ArrayList<Integer>();
		for(int i = 0; i < size; i++)
			if(!used[page.getRow(i).get(0)]){
				dfs(page, used, order, page.getRow(i).get(0));
			}
		
		Graph Tpage = Graph.transpose(page);
		ArrayList<ArrayList<Integer>> comp = new ArrayList<ArrayList<Integer>>();


		Arrays.fill(used, false);
	    Collections.reverse(order);
	    int sizeA=0;
	    
	    for (int i = 0; i < size; i++)
	        if (!used[order.get(i)]) {
	          ArrayList<Integer> component = new ArrayList<>();
	          dfs(Tpage, used, component, order.get(i));
	          if(component.size()>sizeA) sizeA = component.size();
	          comp.add(component);
	        }
	    String[][] re = new String[comp.size()][sizeA];
	    int countB;
		int countA = countB = 0;
	    for(ArrayList<Integer> ape : comp)
	    {
	    	countB = 0;
	    	for(int val : ape){
	    		re[countA][countB] = page.getVertex(comp.get(countA).get(countB));
	    	countB++;	
	    	}
	    	countA++;
	    }
		return re;
	}

	/*
	 * A method that implements a Depth First Search using recursion
	 * 
	 * @param G holds the values of the graph
	 * @param used, an array that contains all indices of the vertices that have been visited
	 * @param ord is an array-list that acts like the stack in the DFS
	 * @param v is the index of the vertex to be checked
	 */
	private void dfs(Graph G, boolean[] used, ArrayList<Integer> ord, int v)
	{
		used[v] = true;

		for( int x = 1; x < G.getRow(v).size(); x++ )
			if( !used[G.getRow(v).get(x)] )
				dfs(G, used, ord, G.getRow(v).get(x));

		if( !ord.contains(v) )
			ord.add(v);
	}

	boolean check;
	// Finds the Hamiltonian path using the Held-Karp algorithm
	@Override
	public String[] getHamiltonianPath() 
	{
		int n = page.size();		
		int[][]  dp = new int [1 << n][n];

		//		//debug****
		//int[][] adj = new int [n][n];
		//		for(LinkedList<Integer> i : page.getEdge())
		//			for(int j = 1; j < i.size(); j++)
		//				adj[i.get(0)][i.get(j)] = 1;
		//
		//		
		//		System.out.print(" \t");
		//		for(int i = 0; i < n; i++)
		//			if(page.getVertex(i).toCharArray().length == 1)
		//				System.out.print(page.getVertex(i) + "  ");
		//			else
		//				System.out.print(page.getVertex(i) + " ");
		//		System.out.println();
		//
		//		for (int i = 0; i < adj.length; i++) {
		//			System.out.print(page.getVertex(i) + "\t");
		//			for (int j = 0; j < adj[i].length; j++) 
		//				System.out.print(adj[i][j] + "  ");
		//
		//			System.out.println();
		//
		//		}
		//		System.out.println();
		//		//********

		for ( int[] d : dp )
			Arrays.fill(d, Graph.INFINITY);

		for ( int i = 0; i < n; i++ )
			dp[1 << i][i] = 0;

		for ( int mask = 0; mask < (1 << n); mask++ ) 
			for ( int i = 0; i < n; i++ ) 
				if ( (mask & 1 << i) != 0 ) 
					for ( int j = 0; j < n; j++ ) 
						if ( (mask & 1 << j) != 0 ) 
							dp[mask][i] = Math.min(dp[mask][i], dp[mask^(1 << i)][j] + page.dist(j,i));	

		// reconstruct path

		int[] order = new int[n];
		int last = -1, cur = (1 << n) - 1, bj;

		for ( int i = n - 1; i >= 0; i-- ) 
		{
			bj = -1;

			for ( int j = 0; j < n; j++ ) 
				if ( (cur & 1 << j) != 0  &&  (bj == -1 || dp[cur][bj] + (last == -1 ? 0 : page.dist(bj,last)) > dp[cur][j] + (last == -1 ? 0 : page.dist(j,last))) )
					bj = j;				

			order[i] = bj;
			cur ^= 1 << bj;
			last = bj;
		}

		String[] ham = new String [n];

		// Loop to check if the path exists using the indices in the array, order
		for ( int i = 0; i < order.length-1; i++ ) 
			if( !page.getRow(order[i]).contains(order[i+1]) )
				return ham;	

		for( int i = 0; i < n; i++ )
			ham[i] = page.getVertex(order[i]);		

		return ham;
	}
	int max =0;
	private void ham(Graph G, LinkedList<Integer> path, int vertex)
	{
		path.add(vertex);
		if(path.size() == G.size()){
			check = false;
		}

		for(int x = 1; x < G.getRow(vertex).size(); x++) //Children of vertex
		{

			if(!path.contains(G.getRow(vertex).get(x)))
			{
				ham(G, path, G.getRow(vertex).get(x));
				if(!check) break;
			}
		}
		if(path.size()>=max){
		System.out.println(path + " " + path.size());
		max = path.size();
		}
		if(check) path.removeLast();
	}
	
	public static void printGraph(Graph G)
	{
		for(LinkedList<Integer> s: G.p)
		{
		for(Integer p: s)
			System.out.print(G.s.get(p) +"\t");
		System.out.println();
		}
	}

}