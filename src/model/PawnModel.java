package model;

import nutsAndBolts.PieceSquareColor;

import java.util.ArrayList;
import java.util.List;

public class PawnModel extends AbstractPieceModel implements Promotable {

	public PawnModel(Coord coord, PieceSquareColor pieceColor) {
		super(coord, pieceColor);
	}

	public boolean isMoveOk(Coord targetCoord, boolean isPieceToCapture) {
		boolean ret = false;

		int colDistance = targetCoord.getColonne() - this.getColonne();
		int ligDistance = targetCoord.getLigne() - this.getLigne();
		int deltaLig = (int) Math.signum(ligDistance);

		// Cas d'un déplacement en diagonale
		if (Math.abs(colDistance) == Math.abs(ligDistance)){
			// sans prise
			if (!isPieceToCapture) {
				if (deltaLig == this.direction && Math.abs(colDistance) == 1) {
					ret = true;
				}
			}
			// avec prise
			else {
				if (Math.abs(colDistance) == 2) {
					ret = true;
				}
			}
		}
		return ret;
	}

	@Override
	public List<Coord> movePossible() {
		char x = this.getColonne();
		int y = this.getLigne();
		List<Coord> rafleCoords = new ArrayList<Coord>();
		rafleCoords.add(new Coord((char) (x+2), y + 2));
		rafleCoords.add( new Coord((char) (x+2), y - 2));
		rafleCoords.add(new Coord((char) (x-2), y + 2));
		rafleCoords.add(new Coord((char) (x-2), y - 2));
		return rafleCoords;
	}

	// si le pion est sur la dernière ligne, il peut promouvoir en dame
	public boolean isPromotable() {
		return (this.getLigne() == Coord.MAX && this.getPieceColor() == PieceSquareColor.WHITE) ||
				(this.getLigne() == 1 && this.getPieceColor() == PieceSquareColor.BLACK);
	}

	@Override
	public void promote() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
