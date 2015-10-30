/**
 * A class to implement the depth first search algorithm in order to represent the agent's
 * move through the map from the initial to the final vertex. 
 * For an overview of the algorithm, see Introduction to Algorithms, CLRS, The MIT Press.
 * 
 * @author thanos dimitris
 */
public class Dfs extends Graph{
	/**Cache the entry and exit time for each vertex.*/
	protected int[][][] time;
	protected static int timePassed;
	
	/**
	 * Calls the constructor of the superclass passing the number of Side vertices as parameter.
	 * Also, initializes the time array.
	 * @param noOfSideVertices
	 */
	public Dfs(int noOfSideVertices) {
		super(noOfSideVertices);
		time = new int[noOfSideVertices][noOfSideVertices][2]; //for caching the visiting and leaving time of each vertex
		parent = new int[noOfSideVertices][noOfSideVertices][2];
		
	}
	
	/**
	 * Overloaded method that allows the testing class not to pass any parameter as argument.
	 * Also, as the agentMoving(int[] currentPosition) method is going to be called recursively,
	 * we place here the statements that only need to run once.  
	 * @return the return value of the overloaded method agentMovint(int[] currentPosition);
	 */
	@Override
	public boolean agentMoving(String agentMode){
		return agentMoving(initialPosition);
	}

	/**
	 * Implements the depth first search algorithm recursively. The base condition is "when the 
	 * agent reaches the final position stop".
	 * 
	 * @param currentPosition the int array containing the coordinates of each currentPosition
	 * @return true if the final position is found, false otherwise.
	 */      
	public boolean agentMoving(int[] currentPosition){
		visitedList.add(currentPosition);
		
		time[currentPosition[0]][currentPosition[1]][0] =++timePassed;
		//Base condition
		if(currentPosition[0] == finalPosition[0] && currentPosition[1] == finalPosition[1]){
			printDiscoveryTimes();
			printFinalGraph();
			return true;
		}
		for(int counter = 0, xJump = 1,yJump = 0;counter < 4 ;counter++,
				xJump += (counter <= 2) ? -1 : 1, 
				yJump += (counter >= 2) ? -1 : 1 ){
			if(constraints(currentPosition[0]+xJump,currentPosition[1] + yJump)){
				int[] newPosition = new int[]{currentPosition[0]+xJump,currentPosition[1]+yJump};
				parent[newPosition[0]][newPosition[1]]=currentPosition;
				if(agentMoving(newPosition)){
					return true;
				}
			}	
		}
		time[currentPosition[0]][currentPosition[1]][1]=++timePassed;
		return false;
	}
	
	/**
	 * Prints the map along with the agent's path. The agent's path is represented
	 * by a figure of the form x/y where x represents the entry time and y the exit time
	 * for each vertex.
	 */
	@Override
	public void printDiscoveryTimes(){
		System.out.println("The graph with the dfs algorithm's entry/exit times is: ");
		for(int i = 0; i< time.length;i++){
			for(int j = 0; j<time[i].length;j++){
				if(adjMatrix[i][j]==0){           //labyrinth.time[i][j] and labyrinth.adjMatrix[i][j] are the same vertex
					System.out.print("###     ");
					continue;
				}else{
					//prints the entry time
					if(time[i][j][0] == 0) System.out.print("_/");
					else System.out.print(time[i][j][0] +"/");
					//prints the exit time + the space between vertices so that the map is aligned for values up to 99
					if(time[i][j][1]==0) {              // case where there is no exit time
						if(time[i][j][0]<10){
							System.out.print("_     ");  // 5 blank spaces
						}else if(time[i][j][0]<100){
							System.out.print("_    ");   //4 blank spaces
						}else if(time[i][j][0]<1000){
							System.out.print("_   ");    //3 blank spaces 
						}
					}
					else {                               //case where there is exit time
						if(time[i][j][0]<10 && time[i][j][1]<10){ //if all times are one digit
							System.out.print(time[i][j][1] +"     ");
						}else if(time[i][j][0]<100 && time[i][j][1]<100){ //if all time values are two digit
							System.out.print(time[i][j][1] +"   ");
						}else if(time[i][j][0]<1000 && time[i][j][1]<1000){ //if all time values are three digit
							System.out.print(time[i][j][1] +" ");
						}else if((time[i][j][0]<100 && time[i][j][1]<10) || (time[i][j][0]<10 && time[i][j][1]<100) ){ //if one time value is one digit and one two digit
							System.out.print(time[i][j][1] +"    ");
						}else if((time[i][j][0]<10 && time[i][j][1]<1000) || (time[i][j][0]<1000 && time[i][j][1]<10)){//if one time value is 3 digit and one 2 digit
							System.out.print(time[i][j][1] +"  ");
						}else if((time[i][j][0]<100 && time[i][j][1]<1000) || (time[i][j][0]<1000 && time[i][j][1]<100)){ //if one time value is 3 digit and one 1 digit
							System.out.print(time[i][j][1] +"   ");
						}
					}
				}
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}
}
