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
 * 
 * @author thanos dimitris
 */
public abstract class Graph{
	protected int adjMatrix[][];
	protected int noOfSideVertices; //for example if the map is 5x5 then the noOfSideVertices is 5
	protected int initialPosition[] = new int[2];
	protected int finalPosition[] = new int[2];
	protected List<int[]> visitedList = new ArrayList<>();	
	/**Caches the parent vertex of each visited vertex.*/
	protected int[][][] parent;
	protected int[][] distances;
	
	/**
	 * Every subclass must provide an implementation of the algorithm the agent will use to move across 
	 * the graph. We will implement three algorithms, depth first search(dfs), breadth first search(bfs)(uninformed 
	 * search strategies) and A*(informed search strategy).
	 */
	public abstract boolean agentMoving(String agentMode);
	
	/**
	 * Every subclass must provide an implementation of the below method which prints the graph along with the discovery 
	 * times with each vertex.
	 */
	public abstract void printDiscoveryTimes();
	
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
		parent = new int[noOfSideVertices][noOfSideVertices][2];
	}
	
	/**
	 * Adds an obstacle to the Graph by setting the corresponding position to 0.
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
		parent[initialPosition[0]][initialPosition[1]] = null;
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
	 * Calculates the moves the agent can execute and returns them in an ArrayList.
	 * 
	 * @return an ArrayList with the agent's potential moves
	 */
	public List<int[]> agentPotentialMoves(String agentMode){
		List<int[]> agentMoves = new ArrayList<>(8);;
		for(int counter = 0, xJump = 1,yJump = 0;counter < 4 ;counter++,
			    xJump += (counter <= 2) ? -1 : 1, 
			    yJump += (counter >= 2) ? -1 : 1 ){
			int[] agentSinglemove = new int[]{xJump,yJump};
			agentMoves.add(agentSinglemove);
		}
		
		if(agentMode.equals("super")){
			int[][] agentSinglemoves = new int[][]{{-1,-1},{-1,1},{1,-1},{1,1}};
			for(int i = 0;i<agentSinglemoves.length;i++){
				agentMoves.add(agentSinglemoves[i]);
			}
		}
		
		return agentMoves;
	}
	
	/**
	 * Prints the initial graph before the agent starts traversing.
	 */
	public void printInitialGraph(){
		System.out.println("The initial graph is the following:");
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
	
	/**
	 * Prints the graph along with the agent's path. The agent's path is represented
	 * by a number indicating the order each vertex was visited.
	 */
	public void printFinalGraph() {
		List<int[]> path = new ArrayList<>();
		int[] currentPosition = finalPosition;
		path.add(currentPosition);
		while(currentPosition!=null){ //since the parent of the starting position is null
			path.add(parent[currentPosition[0]][currentPosition[1]]);
			currentPosition=parent[currentPosition[0]][currentPosition[1]];
		}
		System.out.println("The final graph including the agent's shortest path is: ");
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
						System.out.print(counter + "   ");
					}else if(counter<100){
						System.out.print(counter + "  ");
					}else if(counter < 1000){
						System.out.print(counter + " ");
					}
					printed = true;
					break;
				}
			}
			if(!printed){
				switch(adjMatrix[x][y]){
					case 0:
					System.out.print("##  ");
					break;
					case 1:
					System.out.print("__  ");
					break;
				}
			}
		}
		System.out.println();
		System.out.println();
		if(this instanceof Bfs || this instanceof Astar){
			System.out.println("The total cost of the path is " + distances[finalPosition[0]][finalPosition[1]]);
		}else{
			System.out.println("The total cost of the path is " + path.size());
		}
		System.out.println();
	}
}

	