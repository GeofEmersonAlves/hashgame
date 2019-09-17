package br.com.geofemersonalves.hashgame.controller;

import br.com.geofemersonalves.hashgame.model.HashGame;
/**
 * A hash game Controller, it makes the game run 
 *  
 * @author Emerson Alves da Silva
 * @version 1.0 (26/08/2019)
 */
public class HashGameController {
	public static final int WINLINE0=0;
	public static final int WINLINE1=1;
	public static final int WINLINE2=2;
	public static final int WINCOLUMN0=3;
	public static final int WINCOLUMN1=4;
	public static final int WINCOLUMN2=5;
	public static final int WINDIAGONAL0=6;
	public static final int WINDIAGONAL1=7;
	
	private int whereHasWin;
	private boolean gameEnded;
	
	private HashGame hashGame;
	
	//Total value of the lines, columns and diagonals of the HashGame
	private int lines[]= new int[3];
	private int columns[]= new int[3];
	private int diagonals[]= new int[2];
	
	//Getters
	public int getWhereHasWin() {
		return this.whereHasWin;
	}
	public void setGameEnded() {
		gameEnded=true;
	}
	public boolean isGameEnded() {
		return gameEnded;
	}
	public int getNumMoves() {
		return hashGame.getNumMoves();
	}
	
	//Constructor
	public HashGameController() {
		hashGame = new HashGame();
		gameEnded=false;
		iniSumValues();		
	}
	
/**
 *  Mark one position for a new movement in the game.
 *   
 * @param i number of the line in the #, starts with line 0.
 * @param c number of the column in the #
 * @param valPos Value to put in that position, one different number for each gamer
 * 
 * @throws InvalidHashMoveException when try to make a move if either the game is ended 
 *         or the position is filled already! 
 */
	public void makeMovePlayed(int i, int c, int valPos) throws InvalidHashMoveException {

		if(hashGame.validHashPlay(i, c) && !this.isGameEnded()) {
			hashGame.markPositionPlayed(i, c, valPos);
			hashGame.incrNumMoves();
			
		} else {			
			throw new InvalidHashMoveException(String
					  .format("The game is ended or the position (%d,%d) is filled already!", i,c)
					  .toString());
		}
	}
	/**
	 * 
	 * @param i number of the line in the #, starts with line 0.
	 * @param c number of the column in the #	 
	 * 
	 * @return Returns true if the position is not filled.
	 */
	public boolean validHashMove(int i, int c) {
		return hashGame.validHashPlay(i,c);
	}
	/**
	 * 
	 * @return The matrix 3x3 of the hash game
	 */
	public int[][] getHashGameMatrix(){
		return hashGame.getHashValues();
	}
	/**
	 * 
	 * @return true if the game is won
	 */
	public boolean hasWon() {
		boolean itHasWon=false;
	
		//Sum lines, columns and diagonals of the #
		sumHash();
		
		//Check if won in one line or column
		for(int i=0;i<3;i++) {
			if(Math.abs(lines[i])==3) {
				itHasWon=true;
				this.whereHasWin=i;
			} else if(Math.abs(columns[i])==3) {
				itHasWon=true;
				this.whereHasWin=i+3;
			}
		}
		//Check if won in one diagonal
		if(Math.abs(diagonals[0])==3) {
			itHasWon=true;
			this.whereHasWin=HashGameController.WINDIAGONAL0;
		} else if(Math.abs(diagonals[1])==3) {
			itHasWon=true;
			this.whereHasWin=HashGameController.WINDIAGONAL1;
		}
		return itHasWon;
	}
/**
 * Make a new hash game reseting all values
 */
	public void makeNewHashGame() {
		hashGame.newHashGame();
		iniSumValues();
		gameEnded=false;
	}
/**
 * Makes a computer movement in the hash game;
 * 
 * @param linOpponent number of the line in the # of the last movement of the opponent, starts with line 0.
 * @param colOpponent number of the column in the # of the last movement of the opponent #	 
 * 
 * @return a int[2] with the line and column of the computer movement
 */
	public int[] doTheComputerMove(int linOpponent, int colOpponent) {
		boolean doUserWinInTheNext=false;
		boolean doIwin=false;
		int 	auxCounter=0;
	    int 	lineMyMovement=0;
		int 	columnMyMovement=0;
		int 	hash[][] = new int[3][3];
		
		hash = hashGame.getHashValues();
		int[] computerMove= new int[2];
		
		//Check if is possible I win in this movement and returns it
		do {
			//Checks if I win on any line
			if(lines[auxCounter]==-2) {
				doIwin=true;
				lineMyMovement=auxCounter;
				columnMyMovement=0;
				while(hash[lineMyMovement][columnMyMovement]!=0) {
					columnMyMovement++;
				}
				//Checks if I win on any column	
			} else if(columns[auxCounter]==-2) {
				doIwin=true;
				columnMyMovement=auxCounter;
				lineMyMovement=0;
				while(hash[lineMyMovement][columnMyMovement]!=0) {
					lineMyMovement++;
				}
			//Checks if I win on any diagonal	
			} else if(auxCounter<2) {
				if(diagonals[0]==-2) {
					doIwin=true;
					columnMyMovement=0;
					lineMyMovement=0;
					while(hash[lineMyMovement][columnMyMovement]!=0) {
						columnMyMovement++;
						lineMyMovement++;
					}
				} else if(diagonals[1]==-2){
					doIwin=true;
					lineMyMovement=0;
					columnMyMovement=2;
					while(hash[lineMyMovement][columnMyMovement]!=0) {
						lineMyMovement++;
						columnMyMovement--;
					}		
				}		
			}
			auxCounter++;
		} while(!doIwin && auxCounter<3);
		computerMove[0]=lineMyMovement;
		computerMove[1]=columnMyMovement;
		
		if(doIwin) {
			return computerMove;
		} else { //If I didn't win in this movement, check if the opponent wins in the next move
			auxCounter=0;
			do {
				if(lines[auxCounter]==2) {
					doUserWinInTheNext=true;
					lineMyMovement=auxCounter;
					columnMyMovement=0;
					while(hash[lineMyMovement][columnMyMovement]!=0) {
						columnMyMovement++;
					}
					//Checks if win on any column	
				} else if(columns[auxCounter]==2) {
					doUserWinInTheNext=true;
					columnMyMovement=auxCounter;
					lineMyMovement=0;
					while(hash[lineMyMovement][columnMyMovement]!=0) {
						lineMyMovement++;
					}
				//Checks if  win on any diagonal	
				} else if(auxCounter<2) {
					if(diagonals[0]==2) {
						doUserWinInTheNext=true;
						columnMyMovement=0;
						lineMyMovement=0;
						while(hash[lineMyMovement][columnMyMovement]!=0) {
							columnMyMovement++;
							lineMyMovement++;
						}
					} else if(diagonals[1]==2){
						doUserWinInTheNext=true;
						lineMyMovement=0;
						columnMyMovement=2;
						while(hash[lineMyMovement][columnMyMovement]!=0) {
							lineMyMovement++;
							columnMyMovement--;
						}		
					}		
				}
				auxCounter++;				
			}while(!doUserWinInTheNext && auxCounter<3);

			computerMove[0]=lineMyMovement;
			computerMove[1]=columnMyMovement;
			
			if(doUserWinInTheNext) {
				return computerMove;
			//If the opponent will not win in the next movement, checks the better movement to do
			} else {
				if(hashGame.getNumMoves()==1) {
					if(linOpponent==1 && colOpponent==1) { //if the first opponent movement was in the center
						lineMyMovement=0;
						columnMyMovement=1;
					} else if(linOpponent+colOpponent%2==0) { //If the opponent movement was in the corner
						lineMyMovement = linOpponent==0 ? 2 : 0; //Put them in the opposite corner  
						columnMyMovement = colOpponent==0 ? 2 : 0;
					} else { //else put it far from the opponent movement
						if(linOpponent==0){
							lineMyMovement = 2;  
							columnMyMovement=2;
						} else if (linOpponent==2) {
							lineMyMovement   = 0;  
							columnMyMovement = 0;
						} else if(colOpponent==0) {
							lineMyMovement   = 0;  
							columnMyMovement = 2;							
						} else {
							lineMyMovement   = 0;  
							columnMyMovement = 0;							
						}
					}
				//if is not the first movement
				} else { //Try to bite the opponent
					if(lines[0]==-1 && columns[0]==-1 && hash[0][0]==0) {
						lineMyMovement   = 0;  
						columnMyMovement = 0;
					} else if(diagonals[0]==-1 && hash[0][0]==0 && hash[2][2]==0 || (lines[1]==-1 && hash[0][0]==0 && hash[0][1]==0) ) {
						lineMyMovement   = 0;  
						columnMyMovement = 0;
					} else if(diagonals[0]==-1 && hash[0][0]==0 && hash[2][0]==0) {
						lineMyMovement = 1;  
						columnMyMovement=1;						
					} else if(columns[0]==-1 && hash[0][0]==-1 && hash[1][0]+hash[2][0]==0) {
						lineMyMovement = 1;  
						columnMyMovement=0;						
					} else if(columns[1]==-1 && hash[0][1]==-1 && hash[1][1]+hash[2][1]==0) {
						for(int i = 0;i<3;i++) {
							for(int c=0;c<3;c++) {
								if(hash[i][c]==0) {
									lineMyMovement   = i;  
									columnMyMovement = c;																					
								}
							}
						}
					} else if(columns[2]==-1 && hash[0][2]==-1 && hash[1][2]+hash[2][2]==0) {
						lineMyMovement = 1;  
						columnMyMovement=2;												
					} else if(hash[1][1]==0) {
						lineMyMovement = 1;  
						columnMyMovement=1;																		
					} else { //Play in the first empty position
						lineMyMovement   = 0;  
						columnMyMovement = 0;
						do {
							if(lineMyMovement==2) {
								lineMyMovement=1;
								columnMyMovement++;
							} else {
								lineMyMovement++;
							}
						}while(hash[lineMyMovement][columnMyMovement]!=0 && lineMyMovement < 2 &&  columnMyMovement<2);
					}
				}
			}
		}
		
		computerMove[0]=lineMyMovement;
		computerMove[1]=columnMyMovement;

		return computerMove;	
	}

	
	//Private methods
	private void sumHash() {
		int myHash[][] = new int[3][3];
	
		myHash = hashGame.getHashValues();
		
		//Sum lines, columns and diagonals of the #
		for(int i=0;i<3;i++) {
			lines[i]	= myHash[i][0] + myHash[i][1] + myHash[i][2];
			columns[i]	= myHash[0][i] + myHash[1][i] + myHash[2][i];
		}		
		diagonals[0]=myHash[0][0] + myHash[1][1] + myHash[2][2];
		diagonals[1]=myHash[0][2] + myHash[1][1] + myHash[2][0];		
	}
	
	private void iniSumValues() {
		//Initialize the sum values of the hash
		for(int i=0; i<3;i++) {
			lines[i]=0;
			columns[i]= 0;
			if(i<2) {
				diagonals[i]=0;
			}
		}
	}
}
