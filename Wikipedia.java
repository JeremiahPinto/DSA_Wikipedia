import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Jeremiah Pinto(21545883) and Neosh Sheikh(21959462)
 *
 */
public class Wikipedia implements CITS2200Project
{
	// vertex
	ArrayList<LinkedList<String>> page;
	
	public Wikipedia()
	{
		page = new ArrayList<LinkedList<String>>();
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
		boolean contFrom = false, contTo = false;
		
		//Checks if the ArrayList of LinkedLists contains the URL's of the "to" and "from"
		for( LinkedList<String> s: page )
		{
			if( s.get(0).equals(to) );	
				contTo = true;
			
			//If the ArrayList does contain the "from" URL, adds the "to" URL to it
			if( s.get(0).equals(from))
			{
				contFrom = true;
				page.get(page.indexOf(s)).add(to);
			}
		}
		
		//If the "to" URL is not in the List but the "from" URL is, creates the "to"
		if( contFrom && !contTo )
		{
			LinkedList<String> toList = new LinkedList<String>();
			
			toList.add(to);
			page.add(toList);
			
		}
		
		//If the "from" URL is not present in the list, creates it, 
		//then checks and creates the"to" URL if not present 
		else
		{
			LinkedList<String> fromList = new LinkedList<String>();
			
			fromList.add(to);
			
			if( !contTo )
			{
				LinkedList<String> toList = new LinkedList<String>();
				
				toList.add(to);
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
		// TODO Auto-generated method stub
		return -1;
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
		// TODO Auto-generated method stub
		return null;
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

}
