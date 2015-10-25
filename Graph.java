import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * The .... class 
 * 
 * In addition, it contains the inner static class used to represent the map in which
 * the agent moves.
 * The code can be applied ONLY to square 2 dimensional maps
 * @author thanskourtan
 *
 */
public class Graph{
	
	protected static int time;
	/**
	 * A static inner class to represent the 2 dimensional map in which the agent moves.
	 * Although it is called "Labyrinth" it is really a Graph, represented by an
	 * adjacency matrix. The difference here compared with the usual Graph problems 
	 * is that, instead of providing the edges to the class' constructor, we initially
	 * consider all the vertices connected and break this connections by providing the
	 * positions of the obstacles.
	 * 
	 * @author thanos - dimitris
	 *
	 */
	static class Labyrinth{
		protected int adjMatrix[][];
		protected int noOfSideVertices;
		protected int initialPosition[] = new int[2];
		protected int finalPosition[] = new int[2];
		protected List<int[]> visitedList = new ArrayList<>();
		protected int[][][] time;

		/**
		 * Initializes the class' private fields.
		 * 
		 * @param noOfSideVertices
		 */
		public Labyrinth(int noOfSideVertices){
			this.noOfSideVertices = noOfSideVertices;
			adjMatrix = new int[noOfSideVertices][noOfSideVertices];
			for(int i = 0;i< adjMatrix.length;i++){
				Arrays.fill(adjMatrix[i],1);            //initialize all fields of adjacency matrix to 1
			}
			time = new int[noOfSideVertices][noOfSideVertices][2]; //for caching the visiting and leaving time of each vertex
		}

		/**
		 * Adds an obstacle to the Labyrinth by setting the corresponding position to 0.
		 *
		 * @param x
		 * @param y
		 */
		public void addObstacle(int x, int y){
			adjMatrix[x][y] = 0;
		}

		/**
		 * Sets the value of the initial vertex of the agent to 2 and the current position's coordinates.
		 * 
		 * @param x
		 * @param y
		 */
		public void setInitialPosition(int x, int y){
			adjMatrix[x][y] = 2;
			initialPosition[0] = x;
			initialPosition[1] = y;
		}

		/**
		 * Sets the value of the final vertex of the agent to 3.
		 * 
		 * @param x
		 * @param y
		 */
		public void setFinalPosition(int x, int y){
			adjMatrix[x][y] = 3;
			finalPosition[0] = x;
			finalPosition[1] = y;
		}
	}

	/**
	 * Overloaded method that allows the user to pass only the labyrinth object as argument 
	 * and not its initial position. 
	 * 
	 * @param labyrinth
	 * @return
	 */
	public boolean dfs(Labyrinth labyrinth){
		dfs(labyrinth,labyrinth.initialPosition);
		return false;
	}

	/**
	 * Implements the depth first search algorithm recursively. The base condition is "when the 
	 * agent reaches the final position stop".
	 * 
	 * @param labyrinth
	 *        the labyrinth in which the agent is moving.
	 * @return true if the final position is found, false otherwise.
	 */      
	public boolean dfs(Labyrinth labyrinth,int[] currentPosition){
		labyrinth.visitedList.add(currentPosition);
		labyrinth.time[currentPosition[0]][currentPosition[1]][0] =++time;
		//Base condition
		if(currentPosition[0] == labyrinth.finalPosition[0] && currentPosition[1] == labyrinth.finalPosition[1]){
			printFinalLabyrinth(labyrinth);
			return true;
		}
		for(int counter = 0, xJump = 1,yJump = 0;counter < 4 ;counter++,
				xJump += (counter <= 2) ? -1 : 1, 
				yJump += (counter >= 2) ? -1 : 1 ){
			if(constraints(currentPosition[0]+xJump,currentPosition[1] + yJump,labyrinth)){
				int[] newPosition = new int[]{currentPosition[0]+xJump,currentPosition[1]+yJump};
				if(dfs(labyrinth,newPosition)){
					return true;
				}
			}	
		}
		labyrinth.time[currentPosition[0]][currentPosition[1]][1]=++time;
		return false;
	}

	/**
	 * Checks whether the coordinates of the position passed as parameters are within the map
	 * and do not contain an obstacle.
	 * 
	 * @param x
	 * @param y
	 * @param labyrinth
	 * @return true if the position defined by the coordinates passed as arguments  is valid, otherwise false
	 */
	public boolean constraints(int x, int y, Labyrinth labyrinth){
		//check if the agent goes outside the map
		if(x<0 || x>=labyrinth.noOfSideVertices || y<0 || y>=labyrinth.noOfSideVertices){
			return false;
		}
		//check whether there is an obstacle
		if(labyrinth.adjMatrix[x][y] == 0){
			return false;
		}
		//check if visited
		for(int[] temp : labyrinth.visitedList){
			if(temp[0] == x && temp[1] == y){
				return false;
			}
		}

		return true;
	}

	/**
	 * Prints the initial labyrinth before the agent starts wandering.
	 * 
	 * @param labyrinth
	 *        the labyrinth to be printed
	 */
	public void printInitialLabyrinth(Labyrinth labyrinth){
		System.out.println("The initial labyrinth is the following:");
		int l = labyrinth.adjMatrix.length;
		for(int i = 0; i< l*l;i++){
			if(i%l == 0) 
				System.out.println();
			switch(labyrinth.adjMatrix[i/l][i%l]){
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
	 * Prints the labyrinth along with the agent's path. The agent's path is represented
	 * by a figure of the form x/y where x represents the entry time and y the exit time
	 * for each vertex.
	 * 
	 * @param labyrinth
	 */
	public void printFinalLabyrinth(Labyrinth labyrinth){
		System.out.println("The final labyrinth after the agent's walk with the entry/exit times is: ");
		for(int i = 0; i< labyrinth.time.length;i++){
			for(int j = 0; j<labyrinth.time[i].length;j++){
				if(labyrinth.adjMatrix[i][j]==0){           //labyrinth.time[i][j] and labyrinth.adjMatrix[i][j] are the same vertex
					System.out.print("###   ");
					continue;
				}else{
					//prints the entry time
					if(labyrinth.time[i][j][0] == 0) System.out.print("_/");
					else System.out.print(labyrinth.time[i][j][0] +"/");
					//prints the exit time + the space between vertices so that the map is aligned for values up to 99
					if(labyrinth.time[i][j][1]==0) {
						if(labyrinth.time[i][j][0]<10){
							System.out.print("_   ");
						}else{
							System.out.print("_  ");
						}
					}
					else { 
						if(labyrinth.time[i][j][0]<9 && labyrinth.time[i][j][1]<9){
							System.out.print(labyrinth.time[i][j][1] +"   ");
						}else if(labyrinth.time[i][j][0]>9 && labyrinth.time[i][j][1]>9){
							System.out.print(labyrinth.time[i][j][1] +" ");
						}else if(labyrinth.time[i][j][0]>9 || labyrinth.time[i][j][1]>9){
							System.out.print(labyrinth.time[i][j][1] +"  ");
						}
					}
				}
			}
			System.out.println();
		}
	}

	/**
	 * Tests the program.
	 * 
	 * @param args
	 */
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
		graph.dfs(labyrinth);

	}
}