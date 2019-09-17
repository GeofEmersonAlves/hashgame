package br.com.geofemersonalves.hashgame.controller;

/**
 * Hash game runtime Exception
 * 
 * @author Emerson Alves da Silva
 *
 */
public class InvalidHashMoveException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;
	
	public InvalidHashMoveException(String message) {
		super(message);
	}

}
