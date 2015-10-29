import java.util.Random;

/**
 * The Test class is the entry point of the application. 
 * 
 * @author thanskourtan
 */
public class Test {
	
	/**
	 * Instantiates and returns an object of type Graph based on the provided arguments. 
	 * Also provides validation, because if the arguments are not the proper ones it returns
	 * a null object which causes the application to exit.
	 * 
	 * @param choice the algorithm the user wants to run.
	 * @param type either the default case or the number of side vertices of the graph.
	 * @return an object of type Graph which references a Dfs, or Bfs, of Astar object according 
	 *         to the user's input. The object is null in case the input is not the proper one.
	 */
	private static Graph defineGraph(String choice, String type) {
		int noOfSideVertices = 0;
		if(type.equals("default")){
			noOfSideVertices = 5;
		}else if(type.matches("\\d+")){
			noOfSideVertices = Integer.parseInt(type);
		}else{
			return null;
		}
		
		switch(choice){
			case "dfs":
			return new Dfs(noOfSideVertices);
			case "bfs":
			return new Bfs(noOfSideVertices);
			case "Astar":
			return new Astar(noOfSideVertices);
			default: return null;
		}
	}

	/**
	 * Tests the program.
	 * 
	 * @param args The arguments provided by the user.
	 */
	public static void main(String[] args){
		Graph graph = defineGraph(args[0],args[1]);
		if(graph == null) {
			System.out.println("Wrong input. Program Exits.");
			return;
		}
		if(args[1].equals("default")){
			//default scenario
			graph.addObstacle(0,3);
			graph.addObstacle(2,1);
			graph.addObstacle(2,2);
			graph.addObstacle(3,2);
			graph.addObstacle(4,1);
			graph.addObstacle(4,2);
			graph.setInitialPosition(3,0);
			graph.setFinalPosition(3,3);
			graph.printInitialGraph();
			
			if(args.length==2){
				graph.agentMoving("");
			}else if(args[2].equals("super")){
				graph.agentMoving(args[2]);
			}
		}else{
			//random case scenario
			Random rand = new Random();
			int noOfSquares = graph.adjMatrix.length;
			//set obstacles
			
			for(int i = 0; i< (noOfSquares*noOfSquares)/4;i++){
				graph.addObstacle(rand.nextInt(noOfSquares),rand.nextInt(noOfSquares));
			}
			int num1=0;
			int num2=1;
			int num3=2;
			int num4=3;
			do{
				num1=rand.nextInt(noOfSquares);
				num2=rand.nextInt(noOfSquares);
				num3=rand.nextInt(noOfSquares);
				num4=rand.nextInt(noOfSquares);
			}while(num1==num3 && num2 ==num4); //initial position should be different than the final one
			graph.setInitialPosition(num1,num2);
			graph.setFinalPosition(num3,num4);
			graph.printInitialGraph();
			if(args.length==2){
				graph.agentMoving("");
			}else if(args[2].equals("super")){
				graph.agentMoving(args[2]);
			}
		}
	}
}
