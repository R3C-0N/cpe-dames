package model;


import nutsAndBolts.PieceSquareColor;

import java.util.List;

public interface PieceModel {
	
	
	/**
	 * @return the coord
	 */
	public char getColonne() ;
	public int getLigne() ;
	
	/**
	 * @param coord
	 * @return true si la pièe est aux coordonnées passées en paramêtre
	 */
	public boolean hasThisCoord(Coord coord);
	
	/**
	 * @param coord the coord to set
	 * le déplacement d'une pièe change ses coordonnées
	 */
	public void move(Coord coord);


	/**
	 * @return the pieceColor
	 */
	public PieceSquareColor getPieceColor() ;
	
	
	/**
	 * @param targetCoord
	 * @param isPieceToCapture
	 * @return true si le déplacement est légal
	 */
	public boolean isMoveOk(Coord targetCoord, boolean isPieceToCapture);

	/**
	 * @param targetCoord
	 * @return liste des coordonnées des cases traversées par itinéraire de déplacement
	 */
	public List<Coord> getCoordsOnItinerary(Coord targetCoord);

	
}

