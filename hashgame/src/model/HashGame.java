package model;

/**
 *  A hash game model
 *  
 * @author Emerson Alves da Silva
 * @version 1.0 (25/08/2019)
 */
public class HashGame {
	public static int USERPLAYERVALUE=1;
	public static int COMPUTERPLAYERVALUE=-1;
	
	private int numMoves;
	private int hash[][]= new int[3][3];
	
	//Getters
	public int[][] getHashValues() {
		return hash;
	}

	public int getNumMoves() {
		return numMoves;
	}
	/**
	 * Increases the number of movements  
	 */
	public void incrNumMoves() {
		numMoves++;
	}
	//Contructor
	public HashGame() {
		super();
		newHashGame();
	}

	/**
	 * See if the new play position is valid to play.
	 * @param i number of the line in the #
	 * @param c number of the column in the #	 
	 * 
	 * @return true if the position is valid
	 * 
	 */
	public boolean validHashPlay(int i, int c) {
		if(this.hash[i][c]==0) {
			return true;
		}
		return false;
	}
    /**
     * 
	 *  Mark one position for a new movement in the game.
	 *   
	 * @param i number of the line in the #, starts with line 0.
	 * @param c number of the column in the #
	 * @param valPos Value to put in that position, one different number for each gamer
	 * 
     * @return the updated hash with this position marked
     */
	public int[][] markPositionPlayed(int i, int c, int valPos) {
		hash[i][c]=valPos;
		
		return hash;
	}
	/**
	 * Make a new empty HashGame 
	 * 
	 * @return Returns a int[3][3] matrix with the new hash game
	 * 
	 */
	public int[][] newHashGame(){
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				hash[i][j]=0;
			}
		}
		numMoves=0;
		return hash;
	}
}
