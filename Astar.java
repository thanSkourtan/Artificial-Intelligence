import java.util.Queue;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * A class implementing the A* algorithm and also providing a method to visualize the selected
 * path into the labyrinth. 
 * For an overview of the algorithm, see http://www.redblobgames.com/pathfinding/a-star/introduction.html
 * 
 * @author thanos dimitris
 */
public class Astar extends Bfs{
	Sorting sorting =new Sorting();
	Queue<int[]> aStarQueue = new PriorityQueue<>(sorting);
	
	/**
	 * Calls the constructor of the superclass passing the number of Side vertices as parameter.
	 * @param noOfSideVertices
	 */
	public Astar(int noOfSideVertices) {
		super(noOfSideVertices);
		distances = new int[noOfSideVertices][noOfSideVertices];
		for(int i = 0;i<noOfSideVertices*noOfSideVertices;i++){
			distances[i/noOfSideVertices][i%noOfSideVertices]=Integer.MAX_VALUE;
		}
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
	public boolean agentMoving(String agentMode) {	
		int currentCost = 0;
		aStarQueue.add(initialPosition);
		visitedList.add(initialPosition);
		while(!aStarQueue.isEmpty()){
			int[] currentPosition = aStarQueue.remove();
			if(currentPosition[0]==finalPosition[0] && currentPosition[1]==finalPosition[1]){
				printDiscoveryTimes();
				printFinalGraph();
				return true;
			}
			for(int[] temp : agentPotentialMoves(agentMode)){
				if(constraints(currentPosition[0] + temp[0], currentPosition[1] + temp[1])){
					int[] newPosition = new int[]{currentPosition[0] + temp[0],currentPosition[1] + temp[1]};
					//the heuristic is used here to find out if the movement is diagonal. It does not have to do with the algorithm.
					if(heuristic(currentPosition,newPosition)==2){
						currentCost = DIAGONALCOST;
					}else{
						currentCost = NORMALCOST;
					}
					if(distances[currentPosition[0]][currentPosition[1]] + currentCost <  distances[newPosition[0]][newPosition[1]]){
						distances[newPosition[0]][newPosition[1]] =distances[currentPosition[0]][currentPosition[1]] + currentCost;
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
	 * An inner class which implements the Comparator Interface and overrides its compare method.
	 * An instance of this class will be passed as an argument to the constructor of the priority 
	 * queue, so as to sort its contents from smaller to larger, according to their distance from
	 * the final position.
	 */
	class Sorting implements Comparator<int[]>{
		
		/**
		 * (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
		 */
		@Override
		public int compare(int[] one, int[] two){
			return (distances[one[0]][one[1]] + heuristic(one,finalPosition)) - (distances[two[0]][two[1]] + heuristic(two,finalPosition));
		}
	}
}
