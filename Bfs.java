import java.util.Queue;
import java.util.LinkedList;
import java.util.Arrays;

/**
 * A class implementing the breadth first search algorithm and also providing a method
 * to visualize the selected path into the graph.
 * For an overview of the algorithm, see Introduction to Algorithms, CLRS, The MIT Press.
 * 
 * @author thanSkourtan
 */
public class Bfs extends Graph{
	protected static final int DIAGONALCOST = 2;
	protected static final int NORMALCOST = 1;
	protected Queue<int[]> bfsQueue = new LinkedList<>();
	
	/**
	 * Calls the constructor of the superclass passing the number of Side vertices as parameter.
	 * @param noOfSideVertices
	 */
	public Bfs(int noOfSideVertices) {
		super(noOfSideVertices);
		for(int i = 0; i < noOfSideVertices;i++){        
			for(int j = 0;j<noOfSideVertices;j++){
				Arrays.fill(parent[i][j],-1);
			}
		}
		distances = new int[noOfSideVertices][noOfSideVertices];
	}
	
	/**
	 * Overrides the setInitialPositionMethod in order to initialize the distances array
	 * at the initial Position to 0.
	 */
	@Override
	public void setInitialPosition(int x, int y) {
		super.setInitialPosition(x, y);
		distances[initialPosition[0]][initialPosition[1]]=0;
	}

	/**
	 * Implements the breadth first search algorithm to find the path from start to finish. Contrary to 
	 * the dfs algorithm, the path it finds is the shortest. It includes a variation of the classical bfs
	 * algorithm as we add a condition to stop traversing the graph when the finishing vertex is discovered.
	 * 
	 * @return true if the final position is found, false otherwise.
	 */
	@Override
	public boolean agentMoving(String agentMode) {
		bfsQueue.add(initialPosition);
		visitedList.add(initialPosition);
		while(!bfsQueue.isEmpty()){
			int [] currentPosition= bfsQueue.remove();
			//condition that ends the searching
			if(currentPosition[0] == finalPosition[0] && currentPosition[1] == finalPosition[1]){
				printDiscoveryTimes();
				printFinalGraph();
				return true;
			}
			for(int[] temp : agentPotentialMoves(agentMode)){
				if(constraints(currentPosition[0] + temp[0],currentPosition[1] + temp[1])){
					int[] newPosition = new int[]{currentPosition[0] + temp[0],currentPosition[1] + temp[1]};	
					bfsQueue.add(newPosition);
					if(heuristic(currentPosition,newPosition)==2){
						distances[newPosition[0]][newPosition[1]]= distances[currentPosition[0]][currentPosition[1]] + DIAGONALCOST;
					}else{
						distances[newPosition[0]][newPosition[1]]= distances[currentPosition[0]][currentPosition[1]] + NORMALCOST ;
					}
					visitedList.add(newPosition);
					parent[newPosition[0]][newPosition[1]] = currentPosition;  //we go down up to the 2nd level only, as newPosition is an array
				}
			}
		}	
		return false;
	}
	
	/**
	 * Prints the graph along with the discovery times of all vertices.
	 */
	@Override
	public void printDiscoveryTimes() {
		System.out.println("The graph along with the discovery times is:");
		int l = distances.length;
		for(int i = 0;i<l*l;i++){
			int x = i/l;
			int y = i%l;
			if(y%l==0) System.out.println();
			if(distances[x][y]!=0 && distances[x][y]!=Integer.MAX_VALUE){
				if(distances[x][y]<10){
					System.out.print(distances[x][y] + "   ");
				}else if(distances[x][y]<100){
					System.out.print(distances[x][y]+"  ");
				}
			}else{
				if(adjMatrix[x][y]==0){
					System.out.print("#   ");
				}else if(adjMatrix[x][y]==2){
					System.out.print("0   ");
				}else{
					System.out.print("_   ");
				}
			}
		}
		System.out.println();
		System.out.println();
	}
	
	/**
	 * Heuristic function which calculates the Manhattan distance of two vertices.
	 * @param a The first vertex represented by an array of integers.
	 * @param b The second vertex represented by an array of integers.
	 * @return The sum of the absolute values of the vertical and horizontal distances of the two vertices.
	 */
	public int heuristic(int[] a, int[] b){
	    return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);
	}
}
