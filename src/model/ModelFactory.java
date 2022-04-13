package model;

import nutsAndBolts.PieceSquareColor;

import java.util.Collection;
import java.util.LinkedList;

public class ModelFactory {

	public static Collection<PieceModel> createPieceModelCollection() {
		
		Collection<PieceModel> pieces = new LinkedList<PieceModel>();
		// Collection<PieceModel> pieces = new ArrayList<PieceModel>();
		// Collection<PieceModel> pieces = new HashSet<PieceModel>();
		// Collection<PieceModel> pieces = new TreeSet<PieceModel>();
				
		// Création des pion blancs et ajout dans la collection de pièes
		for ( Coord coord : ModelConfig.WHITE_PIECE_COORDS){
			pieces.add(new PawnModel(coord, PieceSquareColor.WHITE));
		}

		// Création des pions noirs et ajout dans la collection de pièes
		for ( Coord coord : ModelConfig.BLACK_PIECE_COORDS){
			pieces.add(new PawnModel(coord, PieceSquareColor.BLACK));
		}
		return pieces;
	}
}
