package model;

import java.util.LinkedList;
import java.util.List;

import nutsAndBolts.PieceSquareColor;

public class PawnModel implements PieceModel {

	private Coord coord;
	private PieceSquareColor pieceColor;

	public PawnModel(Coord coord, PieceSquareColor pieceColor) {
		super();
		this.coord = coord;
		this.pieceColor = pieceColor;
	}

	@Override
	public char getColonne() {
		return this.coord.getColonne();
	}

	@Override
	public int getLigne() {
		return this.coord.getLigne();
	}

	@Override
	public boolean hasThisCoord(Coord coord) {
		return this.coord.equals(coord);
	}

	@Override
	public PieceSquareColor getPieceColor() {
		return this.pieceColor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("[%s[%s , %s]]", this.pieceColor == PieceSquareColor.BLACK ? 'B' : 'W',
				this.coord.getLigne(), this.coord.getColonne());
	}

	@Override
	public void move(Coord coord) {
		// If isMoveOk()
		// if((Math.abs(this.getColonne() - coord.getColonne()) > 1) ? isMoveOk(coord,
		// true) : isMoveOk(coord, false))
		this.coord = coord;
	}

	@Override
	public boolean isMoveOk(Coord targetCoord, boolean isPieceToCapture) {
		if(this.getColonne() == targetCoord.getColonne())
			return false;
		
		if(this.getLigne() == targetCoord.getLigne())
			return false;

		if(isPieceToCapture != ((Math.abs(this.getColonne() - coord.getColonne()) > 1) ? true : false))
			return false;

		return true;
	}

	@Override
	public List<Coord> getCoordsOnItinerary(Coord targetCoord) {

		List<Coord> coordsOnItinery = new LinkedList<Coord>();

		// TODO Atelier 2

		return coordsOnItinery;
	}

}
