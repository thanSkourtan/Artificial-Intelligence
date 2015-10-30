# Artificial-Intelligence
A command line application that creates a 2d square map and simulates an agent's movement from one point to another with three 
different algorithms, dfs, bfs and A*.

The repository contains:

1) A jar executable file.

   The jar executable can run from the command line with two obligatory parameters and two optionals. The first is the algorithm the user wants to use. The 
   choices are depth first search("dfs"), breadth first search("bfs") and A*("Astar"). The second is either the String "default,
   which runs a specific case of grid, or a number, which indicates the width of a random square map with random obstacles and 
   random beginning and ending positions as well.The third is the string "super" which gives the agent diagonal movement skills as well. Finally the fourth is the word "ObstaclesOff" which turns off the obstacles in random executions of the application.
   
   Examples: 
   
            java -jar ArtificIntelli.jar dfs default 
				 		
            (Uses the depth first search algorithm for the default case grid.)
             
            java -jar Artificintelli.jar bfs 25
             
            (Uses the breadth first search algorithm for a random grid of width 25*.)
			
			java -jar Artificintelli.jar Astar 25 super
             
            (Uses the A* algorithm for a random grid of width 25*. The agent can do diagonal movements.)
			
			java -jar Artificintelli.jar Astar 25 super ObstaclesOff.
             
            (Uses the breadth first search algorithm for a random grid of width 25*, with super agent and no obstacles.)
             
    *The user may need to adjust the width of the command prompt in order to present arrays with large width.
    
2) The source code.
