import java.util.Random;

public class Test {
	
	/**
	 * Tests the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		/*
		//declare and initialize a labyrinth
		Astar labyrinth = new Astar(5);
		//set obstacles
		labyrinth.addObstacle(0,3);
		labyrinth.addObstacle(2,1);
		labyrinth.addObstacle(2,2);
		labyrinth.addObstacle(3,2);
		labyrinth.addObstacle(4,1);
		labyrinth.addObstacle(4,2);
		//set initial and final position
		labyrinth.setInitialPosition(3,0);
		labyrinth.setFinalPosition(3,3);
		labyrinth.printInitialGraph();
		
		labyrinth.agentMoving();
		
		
	*/
		Random rand = new Random();
		//declare and initialize a labyrinth
		int noOfSquares = 25;
		//Bfs graph= new Bfs(noOfSquares);
		//Dfs graph = new Dfs(noOfSquares);
		Astar graph = new Astar(noOfSquares);
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
		}while(num1==num3 && num2 ==num4);
		graph.setInitialPosition(num1,num2);
		graph.setFinalPosition(num3,num4);
		
		
		graph.printInitialGraph();

		graph.agentMoving();
	}

}
