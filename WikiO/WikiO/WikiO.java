import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

/**
 * @author Jeremiah Pinto(21545883) and Neosh Sheikh(21959462)
 *
 */
public class WikiO implements CITS2200Project
{
	// Make private later
	public Graph page;
	
	public WikiO()
	{
		page = new Graph();
	}
	

	/**
	 * Adds an edge to the Wikipedia page graph. If the pages do not
	 * already exist in the graph, they will be added to the graph.
	 * 
	 * @param urlFrom the URL which has a link to urlTo.
	 * @param urlTo the URL which urlFrom has a link to.
	 */
	@Override
	public void addEdge(String from, String to) 
	{
		page.addEdge(from, to);
	}

	/**
	 * Finds the shortest path in number of links between two pages.
	 * If there is no path, returns -1.
	 * 
	 * @param urlFrom the URL where the path should start.
	 * @param urlTo the URL where the path should end.
	 * @return the length of the shortest path in number of links followed.
	 */
	@Override
	public int getShortestPath(String urlFrom, String urlTo) 
	{

		
		boolean contFrom = false, contTo = false; int from=0;
		
		for(int i = 0; i<page.size(); i++ )
		{
			if( page.getVertex(i).equals(urlTo) )	
				contTo = true;	
			if( page.getVertex(i).equals(urlFrom))
			{
				contFrom = true;
				from = i;
			}
			if(contTo && contFrom) break;
		}
			
		if(!contTo || !contFrom) 
		{
//			System.out.println("Could not calculate distances!");
			return -1;
			
		}
		int dist[] = new int[page.size()];
		int visited[] = new int[page.size()];
		
		LinkedList<Integer> q = new LinkedList<Integer>(); 	//q is a queue
		int disA_B = -1;
		
		for(int x = 0; x < dist.length; x++)
			dist[x] = visited[x] = -1;

		
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
					visited[index]=x;
					
					q.add(index);
					dist[index]=dist[x]+1;
				}
				//Finds the first instance of urlTo and if found assigns distance
				//Breaks out of the loop if it's found
				if(page.getVertex(index).equals(urlTo))
				{
					q.clear();		//used to exit out of the while loop
					disA_B = dist[index];
					break;
				}
			}
		}
		return disA_B;
	}
	
	private int findE(int from)
	{
		
		int dist[] = new int[page.size()];
		int visited[] = new int[page.size()];
		
		LinkedList<Integer> q = new LinkedList<Integer>(); 	//q is a queue
		int maxD = 0;
		
		for(int x = 0; x < dist.length; x++)
			dist[x] = visited[x] = -1;

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
					visited[index]=x;
					q.add(index);
					dist[index]=dist[x]+1;
					if(dist[index]>maxD)
						maxD=dist[index];
				}
			}
		}

//		for(int i = 0; i < visited.length; i++)
//			System.out.print(dist[i]+" ");
//		System.out.println();
		for(int i = 0; i < visited.length; i++)
			if(dist[i]==-1) return Integer.MAX_VALUE;
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
		int minMax = Integer.MAX_VALUE;
		
		ArrayList<Integer> storage = new ArrayList<Integer>();
		for( int i = 0 ; i<page.size();i++)
		{
			ecc[i]=findE(i);
			if(ecc[i]<=minMax)
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

	/**
	 * Finds all the strongly connected components of the page graph.
	 * Every strongly connected component can be represented as an array 
	 * containing the page URLs in the component. The return value is thus an array
	 * of strongly connected components. The order of elements in these arrays
	 * does not matter. Any output that contains all the strongly connected
	 * components is considered correct.
	 * 
	 * @return an array containing every strongly connected component.
	 */
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
	
	private void dfs(Graph G, boolean[] used, ArrayList<Integer> ord, int v)
	{
		used[v] = true;
		for(int x = 1; x < G.getRow(v).size(); x++)
			if(!used[G.getRow(v).get(x)])
				dfs(G, used, ord, G.getRow(v).get(x));
		
		if(!ord.contains(v))
			ord.add(v);
	}
	
	

	/**
	 * Finds a Hamiltonian path in the page graph. There may be many
	 * possible Hamiltonian paths. Any of these paths is a correct output.
	 * This method should never be called on a graph with more than 20
	 * vertices. If there is no Hamiltonian path, this method will
	 * return an empty array. The output array should contain the URLs of pages
	 * in a Hamiltonian path. The order matters, as the elements of the
	 * array represent this path in sequence. So the element [0] is the start
	 * of the path, and [1] is the next page, and so on.
	 * 
	 * @return a Hamiltonian path of the page graph.
	 */
	@Override
	public String[] getHamiltonianPath() 
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void printGraph(Graph G)
	{
		for(LinkedList<Integer> s: G.page)
		{
		for(Integer p: s)
			System.out.print(G.s.get(p) +"\t");
		System.out.println();
		}
	}

}

class Graph
{
	public ArrayList<LinkedList<Integer>> page;
	public LinkedList<String> s;
	
	Graph()
	{
		page = new ArrayList<LinkedList<Integer>>();
		s = new LinkedList<String>();
	}
	
	void addEdge(String from, String to)
	{
		boolean contFrom = false, contTo = false; int locF = -1; int locT = -1;
		
		for(int i = 0; i<s.size(); i++ )
		{
			if( s.get(i).equals(to) )	
			{
				contTo = true;
				locT = i;
			}
				
			//If the Map does contain the "from" URL, adds the "to" URL to it
			if( s.get(i).equals(from))
			{
				contFrom = true;
				locF = i;
			}
			//Break the loop if both URL's are found
			if(contTo && contFrom) break;
		}
		
		if( !contFrom )
		{
			LinkedList<Integer> fromList = new LinkedList<Integer>();
			locF = page.size();
			fromList.add(locF);
			page.add(fromList);
			s.add(from);
		}
		if(contTo){
			page.get(locF).add(locT);
		}
		else{
			LinkedList<Integer> toList = new LinkedList<Integer>();
			locT = page.size();
			toList.add(locT);	
			page.get(locF).add(locT);
			page.add(toList);
			s.add(to);
		}		
	}
	
	int indexOf(String str)
	{
		for(int i = 0; i< s.size(); i++)
			if(s.get(i).equals(str)) return i;
		return -1;
	}
	
	String getVertex(int i) {return s.get(i);}
	
	LinkedList<Integer> getRow(int i) {return page.get(i);}
	
	int size(){return s.size();}
	
	static Graph transpose(Graph G)
	{
		Graph RG = new Graph();
		for(int i = 0; i < G.size();i++)
		{
			LinkedList<Integer> temp = new LinkedList<Integer>();
			temp.add(i);
			RG.page.add(temp);
			RG.s.add(G.s.get(i));
		}
		for(LinkedList<Integer> x : G.page)
		{
			for(int child : x)
			{
				if(child == x.get(0)) continue;
				RG.addEdge(G.getVertex(child), G.getVertex(x.get(0)) );
			}
		}
		
		return RG;
	}
	
}