import java.util.*;
import java.io.*;
public class MazeCreator{

    private char[][]maze;
    private boolean animate;//false by default

    /*Constructor loads a maze text file, and sets animate to false by default.

      1. The file contains a rectangular ascii maze, made with the following 4 characters:
      '#' - Walls - locations that cannot be moved onto
      ' ' - Empty Space - locations that can be moved onto
      'E' - the location of the goal (exactly 1 per file)
      'S' - the location of the start(exactly 1 per file)

      2. The maze has a border of '#' around the edges. So you don't have to check for out of bounds!

      3. When the file is not found OR the file is invalid (not exactly 1 E and 1 S) then:
         throw a FileNotFoundException or IllegalStateException
    */
    public MazeCreator(String filename) throws FileNotFoundException {
        //instead of a try/catch, you can throw the FileNotFoundException.
        //This is generally bad behavior
        File f = new File();

        System.out.println(f.createNewFile("hello.dat"));
    }
    public String toString(){
      String to_return = "";
      for(int i = 0; i < maze.length; i ++){
        for(int j = 0; j < maze[0].length; j ++){
            to_return += maze[i][j];
        }
        to_return = to_return+"\n";
      }
      return to_return;
    }

    private void wait(int millis){
         try {
             Thread.sleep(millis);
         }
         catch (InterruptedException e) {
         }
     }

    public void setAnimate(boolean b){
        animate = b;
    }

    public void clearTerminal(){
        //erase terminal, go to top left of screen.
        System.out.println("\033[2J\033[1;1H");
    }


    /*Wrapper Solve Function returns the helper function
      Note the helper function has the same name, but different parameters.
      Since the constructor exits when the file is not found or is missing an E or S, we can assume it exists.
    */
    public int solve(){
      int s_row = 0;
      int s_col = 0;
      for(int i = 0; i < maze.length;i++){
        for(int j = 0; j < maze.length; j++){
          if(maze[i][j]=='S'){
            s_row = i;
            s_col = j;
          }
        }
      }
      maze[s_row][s_col]='@';

            //find the location of the S.

            //erase the S

            //and start solving at the location of the s.
            //return solve(???,???);
      return solve(s_row,s_col);
    }

    /*
      Recursive Solve function:

      A solved maze has a path marked with '@' from S to E.

      Returns the number of @ symbols from S to E when the maze is solved,
      Returns -1 when the maze has no solution.

      Postcondition:
        The S is replaced with '@' but the 'E' is not.
        All visited spots that were not part of the solution are changed to '.'
        All visited spots that are part of the solution are changed to '@'
    */
    private int solve(int i, int j){ //you can add more parameters since this is private

        //automatic animation! You are welcome.
        if(animate){
            clearTerminal();
            System.out.println(toString());
            wait(20);
        }
        boolean foundSol = false;
        if(maze[i-1][j]=='E' || maze[i+1][j]=='E' || maze[i][j+1]=='E' || maze[i][j-1]=='E'){
          return 1;
        }
        if(maze[i-1][j]==' '){
          maze[i-1][j]='@';
          int x = solve(i-1,j);
          if(x>0){
            return 1+x;
          }
          else{
            maze[i-1][j]='.';
          }
        }
        if(maze[i+1][j]==' '){
          maze[i+1][j]='@';
          int x = solve(i+1,j);
          if(x>0){
            return 1+x;
          }
          else{
            maze[i+1][j]='.';
          }
        }
        if(maze[i][j-1]==' '){
          maze[i][j-1]='@';
          int x = solve(i,j-1);
          if(x>0){
            return 1+x;
          }
          else{
            maze[i][j-1]='.';
          }
        }
        if(maze[i][j+1]==' '){
          maze[i][j+1]='@';
          int x = solve(i,j+1);
          if(x>0){
            return 1+x;
          }
          else{
            maze[i][j+1]='.';
          }
        }
        //COMPLETE SOLVE
        return -1; //so it compiles
    }
    public static void main(String[] args){
      try{
        MazeCreator nm = new MazeCreator("data.dat");
        System.out.println(nm.toString());
        Random r = new Random();

        nm.solve();
        System.out.println(r.nextInt()%100);
      }catch(FileNotFoundException e){
        System.out.println("file not found");
      }

    }

}
