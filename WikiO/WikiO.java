import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Jeremiah Pinto(21545883) and Neosh Sheikh(21959462)
 *
 */
public class WikiO implements CITS2200Project
{
	// vertex
	ArrayList<LinkedList<Object[]>> page;
	
	public WikiO()
	{
		page = new ArrayList<LinkedList<Object[]>>();
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
		boolean contFrom = false, contTo = false; int locF = -1; int locT = -1;
		//1st entry is string
		//Checks if the ArrayList of LinkedLists contains the URL's of the "to" and "from"
		for(int i = 0; i<page.size(); i++ )
		{
			if( page.get(i).get(0)[0].equals(to) )	
			{
				contTo = true;
				locT = i;
			}
				
			
			//If the ArrayList does contain the "from" URL, adds the "to" URL to it
			if( page.get(i).get(0)[0].equals(from))
			{
				contFrom = true;
				locF = i;
			}
			
			//Break the loop if both URL's are found
			if(contTo && contFrom) break;
		}
		
		//If the "to" URL is not in the List but the "from" URL is, creates the "to"
		if( !contFrom )
		{
			LinkedList<Object[]> fromList = new LinkedList<Object[]>();
			Object[] t = {from,page.size()};
			fromList.add(t);
			page.add(fromList);
			if(contTo){
				
				Object[] t2 = {to,locT};
				fromList.add(t2);
				page.add(fromList);
			}
			else{
				LinkedList<Object[]> toList = new LinkedList<Object[]>();
				Object[] t2 = {to,page.size()};
				toList.add(t2);
				fromList.add(t2);
				
				page.add(toList);
			}
		}
		else{
			
			if(contTo){
				Object[] t2 = {to,locT};
				page.get(locF).add(t2);
			}
			else{
				LinkedList<Object[]> toList = new LinkedList<Object[]>();
				Object[] t2 = {to,page.size()};
				toList.add(t2);
				page.get(locF).add(t2);
				page.add(toList);
			}
			
		}
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

		
		boolean contFrom = false, contTo = false;	
		int from=0;
		
		for(int i = 0; i<page.size(); i++ )
		{
			if( page.get(i).get(0)[0].equals(urlTo) )	
				contTo = true;
			
			if( page.get(i).get(0)[0].equals(urlFrom))
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
			for(int y = 1; y< page.get(x).size(); y++)
			{
				int indexChild = (int)page.get(x).get(y)[1];
				if(visited[indexChild] == -1)
				{
					visited[indexChild]=x;
					
					q.add(indexChild);
					dist[indexChild]=dist[x]+1;
				}
				//Finds the first instance of urlTo and if found assigns distance
				//Breaks out of the loop if it's found
				if(page.get(x).get(y)[0].equals(urlTo))
				{
					q.clear();		//used to exit out of the while loop
					disA_B = dist[indexChild];
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
			for(int y = 1; y< page.get(x).size(); y++)
			{
				int indexChild = (int)page.get(x).get(y)[1];
				if(visited[indexChild] == -1)
				{
					visited[indexChild]=x;
					q.add(indexChild);
					dist[indexChild]=dist[x]+1;
					if(dist[indexChild]>maxD)
						maxD=dist[indexChild];
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
			result[j] = page.get(storage.get(j)).get(0)[0].toString();
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
		// TODO Auto-generated method stub
		return null;
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
	
	public void printGraph()
	{
		for(LinkedList<Object[]> s: page)
		{
		for(Object[] p: s)
			System.out.print(p[0] +", "+ p[1]+ "\t");
		System.out.println();
		}
	}

}
