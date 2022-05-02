package model;


import nutsAndBolts.PieceSquareColor;

import java.util.List;

/**
 * @author francoiseperrin
 *
 *le mode de déplacement et de prise de la reine est différent de celui du pion
 */
public class QueenModel extends AbstractPieceModel {

	public QueenModel(Coord coord, PieceSquareColor pieceColor) {
		super(coord, pieceColor);
		System.out.println("QueenModel");
	}
	
	public boolean isMoveOk(Coord targetCoord, boolean isPieceToCapture) {
		int colDistance = Math.abs(targetCoord.getColonne() - this.getColonne());
		int ligDistance = Math.abs(targetCoord.getLigne() - this.getLigne());
		System.out.println("QueenModel isMoveOk " + colDistance + " " + ligDistance);
		return colDistance == ligDistance;
	}

	@Override
	public List<Coord> movePossible() {
		char x = this.coord.getColonne();
		int y = this.coord.getLigne();
		Coord coord;
		List<Coord> listCoord = new java.util.ArrayList<Coord>();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 20; j++) {
				coord = new Coord(x, y);
				switch (i) {
					case 0:
						coord = new Coord((char) (x+j), y+j);
						break;
					case 1:
						coord = new Coord((char) (x+j), y-j);
						break;
					case 2:
						coord = new Coord((char) (x-j), y+j);
						break;
					case 3:
						coord = new Coord((char) (x-j), y-j);
						break;
				}
				if (Coord.coordonneesValides(coord)) {
					listCoord.add(coord);
				}
			}
		}
		return listCoord;
	}

	@Override
	public boolean isPromotable() {
		return false;
	}
}