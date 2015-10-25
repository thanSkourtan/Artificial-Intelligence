import java.util.Queue;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * A class implementing the breadth first search algorithm and also providing a method
 * to visualize the selected path into the labyrinth.
 * 
 * @author thanskourtan
 *
 */
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
		List<int[]> path = new ArrayList<>();
		int[] currentPosition = finalPosition;
		path.add(currentPosition);
		while(currentPosition!=null){ //since the parent of the starting position is null
			path.add(parent[currentPosition[0]][currentPosition[1]]);
			currentPosition=parent[currentPosition[0]][currentPosition[1]];
		}
		System.out.println("The final labyrinth after the agent's walk with the entry/exit times is: ");
		int l = adjMatrix.length;
		boolean printed =false;
		for(int i = 0; i < l*l;i++){
			int x = i/l;
			int y = i%l;
			if (y == 0) System.out.println();   //change line
			int[] temp = new int[]{x,y};   // a temporary array with the dimensions of the current position 
			int counter = path.size();
			printed =false;
			for(int[] anArray: path){
				counter--;
				if(anArray!=null  && anArray[0]== temp[0] && anArray[1]== temp[1]){
					if(counter<10) {
						System.out.print(counter + "  ");
					}else if(counter>9){
						System.out.print(counter + " ");
					}
					printed = true;
					break;
				}
			}
			if(!printed){
				switch(adjMatrix[x][y]){
					case 0:
					System.out.print("#  ");
					break;
					case 1:
					System.out.print("_  ");
					break;
				}
			}
		}
	}

	/**
	 * Implements the breadth first search algorithm to find the path from start to finish. Contrary to 
	 * the dfs algorithm, the path it finds is the shortest. It includes a variation of the classical bfs
	 * algorithm as we add a condition to stop traversing the graph when the finishing vertex is discovered.
	 */
	@Override
	public boolean agentMoving() {
		bfsQueue.add(initialPosition);
		visitedList.add(initialPosition);
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
					visitedList.add(newPosition);
					parent[newPosition[0]][newPosition[1]] = currentPosition;  //we go down up to the 2nd level only as newPosition is an array
				}
			}
		}	
		return false;
	}

	/**
	 * Tests the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		//declare and initialize a labyrinth
		Bfs graph= new Bfs(25);
				//set obstacles
		graph.addObstacle(0,3);
		graph.addObstacle(2,1);
		graph.addObstacle(2,2);
		graph.addObstacle(3,2);
		graph.addObstacle(4,1);
		graph.addObstacle(4,2);
		graph.addObstacle(8,3);
		graph.addObstacle(8,13);
		graph.addObstacle(9,1);
		graph.addObstacle(12,0);
		graph.addObstacle(11,8);
		graph.addObstacle(14,3);
		
		graph.setInitialPosition(3,0);
		graph.setFinalPosition(15,20);
		
		graph.printInitialGraph();
		
		graph.agentMoving();
	}

	






}
