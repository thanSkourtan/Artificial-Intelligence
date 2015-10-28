import java.util.Random;

public class Test {
	
	/**
	 * Instantiates and returns an object of type Graph based on the provided arguments. 
	 * 
	 * @param choice the algorithm the user wants to run.
	 * @param type either the default case or the number of side vertices of the graph.
	 * @return
	 */
	private static Graph defineGraph(String choice, String type) {
		if(choice.equals("dfs")){
			if(type.equals("default")){
				return new Dfs(5);	
			}else if(type.matches("\\d")){
				return new Dfs(Integer.parseInt(type));
			}
		}else if(choice.equals("bfs")){
			if(type.equals("default")){
				return new Bfs(5);	
			}else if(type.matches("\\d")){
				return new Bfs(Integer.parseInt(type));
			}
		}else if(choice.equals("Astar")){
			if(type.equals("default")){
				return new Astar(5);	
			}else if(type.matches("\\d+")){
				return new Astar(Integer.parseInt(type));
			}
		}
		return null;
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
			//set obstacles
			graph.addObstacle(0,3);
			graph.addObstacle(2,1);
			graph.addObstacle(2,2);
			graph.addObstacle(3,2);
			graph.addObstacle(4,1);
			graph.addObstacle(4,2);
				//set initial and final position
			graph.setInitialPosition(3,0);
			graph.setFinalPosition(3,3);
				//traverse and print
			graph.printInitialGraph();
			graph.agentMoving();	
		}else{
	//random case scenario
	
			
			Random rand = new Random();
			//declare and initialize a labyrinth
			int noOfSquares = Integer.parseInt(args[1]);
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
			}while(num1==num3 && num2 ==num4); //initial position should be different than final
			graph.setInitialPosition(num1,num2);
			graph.setFinalPosition(num3,num4);
			
			
			graph.printInitialGraph();

			graph.agentMoving();
		}
	}
}
