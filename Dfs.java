/**
 * A class to implement the depth first search algorithm in order to represent the agent's
 * move through the map from the initial to the final vertex.
 * The code can be applied ONLY to square 2-dimensional maps
 * 
 * @author thanskourtan
 *
 */
public class Dfs extends Graph{
	protected static int timePassed;
	
	/**
	 * Calls the constructor of the superclass passing the number of Side vertices as parameter.
	 * @param noOfSideVertices
	 */
	public Dfs(int noOfSideVertices) {
		super(noOfSideVertices);
	}
	
	/**
	 * Overloaded method that allows the user to pass only the labyrinth object as argument 
	 * and not its initial position. 
	 * 
	 * @param labyrinth
	 * @return
	 */
	public boolean agentMoving(){
		agentMoving(initialPosition);
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
	public boolean agentMoving(int[] currentPosition){
		visitedList.add(currentPosition);
		time[currentPosition[0]][currentPosition[1]][0] =++timePassed;
		//Base condition
		if(currentPosition[0] == finalPosition[0] && currentPosition[1] == finalPosition[1]){
			printFinalGraph();
			return true;
		}
		for(int counter = 0, xJump = 1,yJump = 0;counter < 4 ;counter++,
				xJump += (counter <= 2) ? -1 : 1, 
				yJump += (counter >= 2) ? -1 : 1 ){
			if(constraints(currentPosition[0]+xJump,currentPosition[1] + yJump)){
				int[] newPosition = new int[]{currentPosition[0]+xJump,currentPosition[1]+yJump};
				if(agentMoving(newPosition)){
					return true;
				}
			}	
		}
		time[currentPosition[0]][currentPosition[1]][1]=++timePassed;
		return false;
	}

	/**
	 * Prints the labyrinth along with the agent's path. The agent's path is represented
	 * by a figure of the form x/y where x represents the entry time and y the exit time
	 * for each vertex.
	 * 
	 * @param labyrinth
	 */
	public void printFinalGraph(){
		System.out.println("The final labyrinth after the agent's walk with the entry/exit times is: ");
		for(int i = 0; i< time.length;i++){
			for(int j = 0; j<time[i].length;j++){
				if(adjMatrix[i][j]==0){           //labyrinth.time[i][j] and labyrinth.adjMatrix[i][j] are the same vertex
					System.out.print("###   ");
					continue;
				}else{
					//prints the entry time
					if(time[i][j][0] == 0) System.out.print("_/");
					else System.out.print(time[i][j][0] +"/");
					//prints the exit time + the space between vertices so that the map is aligned for values up to 99
					if(time[i][j][1]==0) {
						if(time[i][j][0]<10){
							System.out.print("_   ");
						}else{
							System.out.print("_  ");
						}
					}
					else { 
						if(time[i][j][0]<9 && time[i][j][1]<9){
							System.out.print(time[i][j][1] +"   ");
						}else if(time[i][j][0]>9 && time[i][j][1]>9){
							System.out.print(time[i][j][1] +" ");
						}else if(time[i][j][0]>9 || time[i][j][1]>9){
							System.out.print(time[i][j][1] +"  ");
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
		Dfs graph= new Dfs(5);
		//set obstacles
		graph.addObstacle(0,3);
		graph.addObstacle(2,1);
		graph.addObstacle(2,2);
		graph.addObstacle(3,2);
		graph.addObstacle(4,1);
		graph.addObstacle(4,2);
		//set initial and final position
		graph.setInitialPosition(3,0);
		graph.setFinalPosition(3,3);

		//Graph graph =new Graph();
		graph.printInitialGraph();

		graph.agentMoving();

	}

}
