package model;


import nutsAndBolts.PieceSquareColor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author francoise.perrin
 * 
 * Cete classe fabrique et stocke toutes les PieceModel du Model dans une collection 
 * elle est donc responsable de rechercher et mettre à jour les PieceModel (leur position)
 * En réalité, elle délégue à une fabrique le soin de fabriquer les bonnes PieceModel
 * avec les bonnes coordonnées
 * 
 * En revanche, elle n'est pas responsable des algorithme métiers liés au déplacement des pièes
 * (responsabilité de la classe Model)
 */
public class ModelImplementor {

	// la collection de pièces en jeu - mélange noires et blanches
	private Collection<PieceModel> pieces ;	

	public ModelImplementor() {
		super();

		pieces = ModelFactory.createPieceModelCollection();
	}

	public PieceSquareColor getPieceColor(Coord coord) {
		PieceSquareColor color = null;
		PieceModel piece = this.findPiece(coord);

		if (piece != null) {
			color = piece.getPieceColor();
		}
		return color;
	}

	public boolean isPiecehere(Coord coord) {
		return this.findPiece(coord) != null;
	}

	public boolean isMovePieceOk(Coord initCoord, Coord targetCoord, boolean isPieceToTake) {
		boolean isMovePieceOk = false;
		PieceModel initPiece = this.findPiece(initCoord);
		if (initPiece != null) {
			isMovePieceOk = initPiece.isMoveOk(targetCoord, isPieceToTake) ;
		}
		return isMovePieceOk;
	}

	public boolean isRaflePossible(Coord initCoord) {
		PieceModel initPiece = this.findPiece(initCoord);
		if (initPiece instanceof AbstractPieceModel) {
			AbstractPieceModel abstractPiece = (AbstractPieceModel) initPiece;
			List<Coord> rafleCoords = abstractPiece.movePossible();
			for (Coord coord : rafleCoords) {
				if (this.isMovePieceOk(initCoord, coord, true)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isPiecePromotable(Coord coord) {
		PieceModel piece = this.findPiece(coord);
		if (piece instanceof AbstractPieceModel) {
			System.out.println("isPiecePromotable : " + ((AbstractPieceModel) piece).isPromotable());
			return ((AbstractPieceModel) piece).isPromotable();
		}
		return false;
	}

	public boolean movePiece(Coord initCoord, Coord targetCoord) {

		boolean isMovePieceDone = false;
		PieceModel initPiece = this.findPiece(initCoord);
		if (initPiece != null) {

			// déplacement pièce
			initPiece.move(targetCoord) ;
			isMovePieceDone = true;
		}
		return isMovePieceDone;
	}

	public void removePiece(Coord pieceToTakeCoord) {
		this.pieces.remove(this.findPiece(pieceToTakeCoord));
	}

	public void promotePiece(Coord pieceToPromoteCoord) {
		PieceModel pieceToTake = this.findPiece(pieceToPromoteCoord);
		this.pieces.remove(pieceToTake);
		this.pieces.add(new QueenModel(pieceToPromoteCoord, pieceToTake.getPieceColor()));
		System.out.println("pieces : " + this.pieces);
	}

	
	public List<Coord> getCoordsOnItinerary(Coord initCoord, Coord targetCoord) {
		List<Coord> coordsOnItinerary = new ArrayList<>();

		int directionX = initCoord.getColonne() > targetCoord.getColonne() ? -1 : 1;
		int directionY = initCoord.getLigne() > targetCoord.getLigne() ? -1 : 1;

		for	(int i = 1; i < Math.abs(initCoord.getLigne() - targetCoord.getLigne()); i++) {
			coordsOnItinerary.add(new Coord((char) (initCoord.getColonne() + i * directionX), initCoord.getLigne() + i * directionY));
		}
		
		return coordsOnItinerary;
	}

	
	/**
	 * @param coord
	 * @return la pièce qui se trouve aux coordonnées indiquées
	 */
	 public PieceModel findPiece(Coord coord) {
		PieceModel findPiece = null;

		for(PieceModel piece : this.pieces) {
			if(coord != null && piece.hasThisCoord(coord)) {
				findPiece = piece;
				break;
			}
		}
		return findPiece;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * 
	 * La méthode toStrong() retourne une représentation
	 * de la liste de pièes sous forme d'un tableau 2D
	 * 
	 */
	public String toString() {


		String st = "";
		String[][] damier = new String[ModelConfig.LENGTH][ModelConfig.LENGTH];

		// création d'un tableau 2D avec les noms des pièes à partir de la liste de pièes
		for(PieceModel piece : this.pieces) {

			PieceSquareColor color = piece.getPieceColor();
			String stColor = (PieceSquareColor.WHITE.equals(color) ? "--B--" : "--N--" );

			int col = piece.getColonne() -'a';
			int lig = piece.getLigne() -1;
			damier[lig][col ] = stColor ;
		}

		// Affichage du tableau formatté
		st = "     a      b      c      d      e      f      g      h      i      j\n";
		for ( int lig = 9; lig >=0 ; lig--) {
			st += (lig+1) + "  ";
			for ( int col = 0; col <= 9; col++) {					 
				String stColor = damier[lig][col];				
				if (stColor != null) {						
					st += stColor + "  ";	
				} 
				else {
					st += "-----  ";
				}
			}
			st +="\n";
		}
		
		return "\nDamier du model \n" + st;	
	}

}
