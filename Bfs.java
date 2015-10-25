import java.util.Queue;
import java.util.LinkedList;


public class Bfs extends Graph{
	
	private static Queue<int[]> bfsQueue = new LinkedList<>();
	int[][] parent = new int[labyrinth.noOfSideVertices][labyrinth.noOfSideVertices];
	
	public void bfs(Labyrinth labyrinth){
		bfsQueue.add(labyrinth.initialPosition);
		while(!bfsQueue.isEmpty()){
			int [] currentPosition= bfsQueue.remove();
			for(int counter = 0, xJump = 1,yJump = 0;counter < 4 ;counter++,
			    xJump += (counter <= 2) ? -1 : 1, 
			    yJump += (counter >= 2) ? -1 : 1 ){
				if(constraints(currentPosition[0] + xJump,currentPosition[1] + yJump,labyrinth)){
					int[] newPosition = new int[]{currentPosition[0] + xJump,currentPosition[1] + yJump};	
					bfsQueue.add(newPosition);
				}
			}
			
		}	
	}




	public static void main(String[] args){
		//declare and initialize a labyrinth
		Labyrinth labyrinth = new Labyrinth(5);
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
		
		
		Graph graph =new Graph();
		graph.printInitialLabyrinth(labyrinth);
	}

}
