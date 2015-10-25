import java.util.Queue;
import java.util.LinkedList;
import java.util.Arrays;

public class Bfs extends Graph{
	protected static Queue<int[]> bfsQueue = new LinkedList<>();
	int[][][] parent = new int[noOfSideVertices][noOfSideVertices][2];
	
	/**
	 * Calls the constructor of the superclass passing the number of Side vertices as parameter.
	 * @param noOfSideVertices
	 */
	public Bfs(int noOfSideVertices) {
		super(noOfSideVertices);
		for(int i = 0; i < noOfSideVertices;i++){        //initialize all members of the parent array to -1
			for(int j = 0;j<noOfSideVertices;j++){
				Arrays.fill(parent[i][j],-1);
			}
		}
	}
	
	/**
	 * Overrides the setInitialPositionMethod in order to initialize the parent of the 
	 * starting vertext to null.
	 */
	@Override
	public void setInitialPosition(int x, int y) {
		super.setInitialPosition(x, y);
		parent[initialPosition[0]][initialPosition[1]] = null; //initialize the parent of starting vertex to null
	}



	/**
	 * Prints the labyrinth along with the agent's path. The agent's path is represented
	 * by a number indicating the order each vertex was visited.
	 */
	@Override
	public void printFinalGraph() {
		// TODO Auto-generated method stub
		
	}
		
	/**
	 * Implements the breadth first search algorithm to find the path from start to finish. Contrary to 
	 * the dfs algorithm, the path it finds is the shortest. It includes a variation of the classical bfs
	 * algorithm as we add a condition to stop traversing the graph when the finishing vertex is discovered.
	 */
	@Override
	public boolean agentMoving() {
		bfsQueue.add(initialPosition);
		while(!bfsQueue.isEmpty()){
			int [] currentPosition= bfsQueue.remove();
			//condition that ends the searching
			if(currentPosition[0] == finalPosition[0] && currentPosition[1] == finalPosition[1]){
				printFinalGraph();
				return true;
			}
			for(int counter = 0, xJump = 1,yJump = 0;counter < 4 ;counter++,
			    xJump += (counter <= 2) ? -1 : 1, 
			    yJump += (counter >= 2) ? -1 : 1 ){
				if(constraints(currentPosition[0] + xJump,currentPosition[1] + yJump)){
					int[] newPosition = new int[]{currentPosition[0] + xJump,currentPosition[1] + yJump};	
					bfsQueue.add(newPosition);
					visitedList.add(currentPosition);
					parent[newPosition[0]][newPosition[1]] = currentPosition;  //we go down up to the 2nd level only as newPosition is an array
				}
			}
			
		}	
		return false;
	}



	public static void main(String[] args){
		//declare and initialize a labyrinth
		Bfs graph= new Bfs(5);
				//set obstacles
		graph.addObstacle(0,3);
		graph.addObstacle(2,1);
		graph.addObstacle(2,2);
		graph.addObstacle(3,2);
		graph.addObstacle(4,1);
		graph.addObstacle(4,2);
		
		
		graph.setInitialPosition(3,0);
		graph.setFinalPosition(3,3);
		
		graph.printInitialGraph();
		
		graph.agentMoving();
	}

	






}
