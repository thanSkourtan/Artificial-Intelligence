import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * Class Graph represents the 2 dimensional map in which the agent moves.It uses an adjacency
 * matrix to represent a graph. The difference here compared to the usual Graph problems is
 * that instead of providing the edges to the class' constructor, we initially consider all 
 * the vertices connected and then we break this connections by providing the positions of the 
 * obstacles.
 * Consequently, every vertex is represented by an int array of length 2 which includes its two
 * coordinates, x and y.
 * The code can be applied ONLY to square, 2-dimensional maps
 * @author thanskourtan
 */
public abstract class Graph{
	protected int adjMatrix[][];
	protected int noOfSideVertices; //for example if the map is 5x5 then the noOfSideVertices is 5
	protected int initialPosition[] = new int[2];
	protected int finalPosition[] = new int[2];
	protected List<int[]> visitedList = new ArrayList<>();
	
	/**
	 * Every subclass must provide an implementation of the method below, so that
	 * the Final Graph which will include the agent's path will be visually presented to the user.
	 * 
	 */
	public abstract void printFinalGraph();
	
	/**
	 * Every subclass must provide an implementation of the algorithm the agent will use to move across 
	 * the graph. We will implement three algorithms, depth first search(dfs), breadth first search(bfs)(uninformed 
	 * search strategies) and A*(informed search strategy).
	 * 
	 */
	public abstract boolean agentMoving();
	
	/**
	 * Initializes the class' protected fields.
	 * 
	 * @param noOfSideVertices
	 */
	public Graph(int noOfSideVertices){
		this.noOfSideVertices = noOfSideVertices;
		adjMatrix = new int[noOfSideVertices][noOfSideVertices];
		for(int i = 0;i< adjMatrix.length;i++){
			Arrays.fill(adjMatrix[i],1);          
		}
	}
	
	/**
	 * Adds an obstacle to the Labyrinth by setting the corresponding position to 0.
	 *
	 * @param x the vertical vertex's coordinate
	 * @param y the horizontal vertex's coordinate
	 */
	public void addObstacle(int x, int y){
		adjMatrix[x][y] = 0;
	}

	/**
	 * Sets the value of the initial vertex of the agent to 2 and the current position's coordinates.
	 * 
	 * @param x the vertical vertex's coordinate
	 * @param y the horizontal vertex's coordinate
	 */
	public void setInitialPosition(int x, int y){
		adjMatrix[x][y] = 2;
		initialPosition[0] = x;
		initialPosition[1] = y;
	}

	/**
	 * Sets the value of the final vertex of the agent to 3.
	 * 
	 * @param x the vertical vertex's coordinate
	 * @param y the horizontal vertex's coordinate
	 */
	public void setFinalPosition(int x, int y){
		adjMatrix[x][y] = 3;
		finalPosition[0] = x;
		finalPosition[1] = y;
	}
	
	/**
	 * Checks whether the coordinates of the position passed as parameters are within the map
	 * and do not contain an obstacle.
	 * 
	 * @param x the vertical vertex's coordinate
	 * @param y the horizontal vertex's coordinate
	 * @return true if the position defined by the coordinates passed as arguments  is valid, otherwise false
	 */
	public boolean constraints(int x, int y){
		//check if the agent goes outside the map
		if(x<0 || x>=noOfSideVertices || y<0 || y>=noOfSideVertices){
			return false;
		}
		//check whether there is an obstacle
		if(adjMatrix[x][y] == 0){
			return false;
		}
		//check if visited
		for(int[] temp : visitedList){
			if(temp[0] == x && temp[1] == y){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Prints the initial labyrinth before the agent starts traversing.
	 */
	public void printInitialGraph(){
		System.out.println("The initial labyrinth is the following:");
		int l = adjMatrix.length;
		for(int i = 0; i< l*l;i++){
			if(i%l == 0) 
				System.out.println();
			switch(adjMatrix[i/l][i%l]){
				case 0:
				System.out.print("#  ");
				break;
				case 1:
				System.out.print("_  ");
				break;
				case 2: 
				System.out.print("S  ");
				break;
				case 3:
				System.out.print("F  ");
				break;
			}
		}
		System.out.println();
		System.out.println();
		System.out.println();
	}
}

	