import java.util.LinkedList;

public class test1 {

	public static void main(String[] args)
	{
		WikiO x = new WikiO();
//		x.addEdge("1", "2");
//		x.addEdge("2", "3");
//		x.addEdge("3", "4");
//		x.addEdge("3", "5");
//		x.addEdge("3", "6");
//		x.addEdge("4", "7");
//		x.addEdge("4", "8");
//		x.addEdge("8", "9");
//		x.addEdge("7", "9");
//		x.addEdge("5", "9");
//		WikiO.printGraph(x.page);
//		System.out.println(x.getShortestPath("2", "9"));
		
//		x.addEdge("1", "2");
//		x.addEdge("3", "2");
//		x.addEdge("3", "5");
		
//		x.addEdge("1","2");
//		x.addEdge("2","3");
//		x.addEdge("3","4");
//		x.addEdge("4","5");
//		x.addEdge("5","2");
//		x.addEdge("5","3");
//		x.addEdge("5","6");
//		x.addEdge("4","6");
//		x.addEdge("7","3");
//		x.addEdge("7","8");
		
		// Hamiltonian test
		x.addEdge("1", "2");
		x.addEdge("1", "3");
		x.addEdge("2", "3");
		x.addEdge("2", "4");
		x.addEdge("3", "6");
		x.addEdge("3", "5");
		x.addEdge("4", "3");
		x.addEdge("5", "6");
		x.addEdge("6", "1");
		
		
		WikiO.printGraph(x.page);
		
		System.out.println();
		String[] hamS = x.getHamiltonianPath();
		for(String s: hamS)
			System.out.print(s + " ");
		
		
//		WikiO.printGraph(Graph.transpose(x.page));
//		
//		System.out.println();
		
//		String[][] a = x.getStronglyConnectedComponents();
//		for(String[] b: a)
//		{
//			for(String c: b)
//				System.out.print(c + " ");
//			System.out.println();
//		}
		
		
		
//		WikiO.printGraph(Graph.transpose(x.page));
		
//		x.printGraph();
	}

}
