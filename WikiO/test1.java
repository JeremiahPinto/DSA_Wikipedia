
public class test1 {

	public static void main(String[] args)
	{
		WikiO x = new WikiO();
		x.addEdge("Harambe", "Child");
		x.addEdge("Child", "Harambe");
		x.addEdge("Child", "Mother");
		x.addEdge("Mother", "Father");
		x.addEdge("Mother", "Mammal");
		x.addEdge("Mother", "Theresa");
		x.addEdge("Father", "Church");
		x.addEdge("Father", "John");
		x.addEdge("John", "Human");
		x.addEdge("Church", "Human");
		x.addEdge("Mammal", "Human");
		x.printGraph();
		System.out.println();
		System.out.println(x.getShortestPath("Harambe", "Human"));
		System.out.println();
//		
//		x.addEdge("1", "2");
//		x.addEdge("1", "3");
//		x.addEdge("3", "2");
//		x.printGraph();
		System.out.println();
		String[] pepe = x.getCenters();
		for(String eee : pepe)
		{
			System.out.print(eee + "\t");
		}
	}

}
