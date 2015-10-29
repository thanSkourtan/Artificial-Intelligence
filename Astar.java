import java.util.Queue;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * A class implementing the A* algorithm and also providing a method to visualize the selected
 * path into the labyrinth. 
 * For an overview of the algorithm, see http://www.redblobgames.com/pathfinding/a-star/introduction.html
 * 
 * @author thanskourtan
 */
public class Astar extends Bfs{
	Sorting sorting =new Sorting();
	Queue<int[]> aStarQueue = new PriorityQueue<>(sorting);
	/**Caching the distances between the current position and the final position.*/
	int[][] distances = new int[noOfSideVertices][noOfSideVertices];
	
	/**
	 * Calls the constructor of the superclass passing the number of Side vertices as parameter.
	 * @param noOfSideVertices
	 */
	public Astar(int noOfSideVertices) {
		super(noOfSideVertices);
	}
	
	/**
	 * Implements the A* algorithm to find the path from start to finish. It is a variation
	 * of the Dijkstras' algorithm, with the difference among the two being that in the case 
	 * of A*, it is sum of the heuristic function and the distance up to the current point 
	 * which defines the priority of the vertex to be investigated, contrary to the Dijkstras'
	 * algorithm which only examines the distance up to the current point. 
	 * 
	 * @return true if the final position is found, false otherwise.
	 */
	@Override
	public boolean agentMoving() {
		for(int i = 0;i<noOfSideVertices*noOfSideVertices;i++){
			distances[i/noOfSideVertices][i%noOfSideVertices]=Integer.MAX_VALUE;
		}
		
		aStarQueue.add(initialPosition);
		visitedList.add(initialPosition);
		while(!aStarQueue.isEmpty()){
			int[] currentPosition = aStarQueue.remove();
			if(currentPosition[0]==finalPosition[0] && currentPosition[1]==finalPosition[1]){
				printFinalGraph();
				return true;
			}
			for(int counter = 0, xJump = 1,yJump = 0;counter < 4 ;counter++,
				    xJump += (counter <= 2) ? -1 : 1, 
				    yJump += (counter >= 2) ? -1 : 1 ){
				if(constraints(currentPosition[0] + xJump, currentPosition[1] + yJump)){
					int[] newPosition = new int[]{currentPosition[0] + xJump,currentPosition[1] + yJump};
					if(distances[currentPosition[0]][currentPosition[1]] + adjMatrix[newPosition[0]][newPosition[1]] <  distances[newPosition[0]][newPosition[1]]){
						distances[newPosition[0]][newPosition[1]] =distances[currentPosition[0]][currentPosition[1]] + adjMatrix[newPosition[0]][newPosition[1]];
					}
					aStarQueue.offer(newPosition);
					visitedList.add(newPosition);
					parent[newPosition[0]][newPosition[1]] = currentPosition; 
				}	
			}
		}
		return false;
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
	
	/**
	 * An inner class which implements the Comparator Interface and overrides its compare method.
	 * An instance of this class will be passed as an argument to the constructor of the priority 
	 * queue, so as to sort its contents from smaller to larger, according to their distance from
	 * the final position.
	 */
	class Sorting implements Comparator<int[]>{
		
		/**
		 * (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(int[] one, int[] two){
			return (distances[one[0]][one[1]] + heuristic(one,finalPosition)) - (distances[two[0]][two[1]] + heuristic(two,finalPosition));
		}
	}
}
