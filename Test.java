import java.util.Random;

public class Test {
	
	/**
	 * Tests the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		Random rand = new Random();
		//declare and initialize a labyrinth
		int noOfSquares = 25;
		Bfs graph= new Bfs(noOfSquares);
		//Dfs graph = new Dfs(noOfSquares);
		//set obstacles
		for(int i = 0; i< 200;i++){
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
		}while(num1!=num3 && num2 !=num4);
		graph.setInitialPosition(num1,num2);
		graph.setFinalPosition(num3,num4);
		
		
		graph.printInitialGraph();

		graph.agentMoving();
	}

}
