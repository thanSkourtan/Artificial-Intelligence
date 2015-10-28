import java.util.Queue;
import java.util.Comparator;
import java.util.PriorityQueue;


public class Astar extends Bfs{
	Sorting sorting =new Sorting();
	Queue<int[]> aStarQueue = new PriorityQueue<>(sorting);
	int[][] distances = new int[noOfSideVertices][noOfSideVertices];
	
	/**
	 * Calls the constructor of the superclass passing the number of Side vertices as parameter.
	 * @param noOfSideVertices
	 */
	public Astar(int noOfSideVertices) {
		super(noOfSideVertices);
	}

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
	 * Returns the Manhattan distance of two vertices.
	 * @param a The first vertex represented by an array of integers.
	 * @param b The second vertex represented by an array of integers.
	 * @return The sum of the absolute values of the vertical and horizontal distances of the two vertices.
	 */
	public int heuristic(int[] a, int[] b){
	    return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);
	}
	
	/**
	 * An inner class to implement the Comparator Interface and override its unique compare method.
	 * An instance of this class will be passed as an argument to the constructor of the priority 
	 * queue, so as to sort its contents from smaller to larger.
	 * 
	 *
	 */
	class Sorting implements Comparator<int[]>{
		
		/**
		 * (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(int[] one, int[] two){
			return heuristic(one,finalPosition) - heuristic(two,finalPosition);
		}
	}
}
