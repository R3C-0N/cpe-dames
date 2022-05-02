package model;


import controller.OutputModelData;
import nutsAndBolts.PieceSquareColor;

import java.util.List;

/**
 * @author francoise.perrin
 *
 * Cette classe gère les aspects métiers du jeu de dame
 * indépendamment de toute vue
 * 
 * Elle délégue à son objet ModelImplementor
 * le stockage des PieceModel dans une collection
 * 
 * Les pièces sont capables de se déplacer d'une case en diagonale
 * si la case de destination est vide
 * 
 * Ne sont pas gérés les prises, les rafles, les dames,
 * 
 * N'est pas géré le fait que lorsqu'une prise est possible
 * une autre pièce ne doit pas être jouée
 * 
 */
public class Model implements BoardGame<Coord> {

	private PieceSquareColor currentGamerColor;	// couleur du joueur courant

	private ModelImplementor implementor;		// Cet objet sait communiquer avec les PieceModel

	public Model() {
		super();
		this.implementor = new ModelImplementor();
		this.currentGamerColor = ModelConfig.BEGIN_COLOR;

		// System.out.println(this);
	}

	@Override
	public String toString() {
		return implementor.toString();
	}



	/**
	 * Actions potentielles sur le model : move, capture, promotion pion, rafles
	 */
	@Override
	public OutputModelData<Coord> moveCapturePromote(Coord toMovePieceCoord, Coord targetSquareCoord) {

		OutputModelData<Coord> outputModelData = null;

		boolean isMoveDone = false;
		Coord toCapturePieceCoord = null;
		Coord toPromotePieceCoord = null;
		PieceSquareColor toPromotePieceColor = null;

		// Si la pièce est déplaçable (couleur du joueur courant et case arrivée disponible)
		if (this.isPieceMoveable(toMovePieceCoord, targetSquareCoord)) {

			// S'il n'existe pas plusieurs pièces sur le chemin
			if (this.isThereMaxOnePieceOnItinerary(toMovePieceCoord, targetSquareCoord)) {

				//Recherche coord de l'éventuelle pièce à prendre
				toCapturePieceCoord = this.getToCapturePieceCoord(toMovePieceCoord, targetSquareCoord);

				// si le déplacement est légal (en diagonale selon algo pion ou dame)
				boolean isPieceToCapture = toCapturePieceCoord != null;
				if (this.isMovePiecePossible(toMovePieceCoord, targetSquareCoord, isPieceToCapture)) {

					// déplacement effectif de la pièce
					this.movePiece(toMovePieceCoord, targetSquareCoord);
					isMoveDone = true;

					// suppression effective de la pièce prise
					this.remove(toCapturePieceCoord);

					// promotion éventuelle de la pièce après déplacement
					if (this.isPiecePromotable(targetSquareCoord)) {
						this.promotePiece(targetSquareCoord);
						toPromotePieceCoord = targetSquareCoord;
						toPromotePieceColor = this.currentGamerColor;
					}

					// S'il n'y a pas eu de prise
					// ou si une rafle n'est pas possible alors changement de joueur 
					if (!isPieceToCapture || !this.isRaflePossible(targetSquareCoord)) {	// TODO : Test à changer atelier 4
						this.switchGamer();
					}
				}
			}
		}
		// System.out.println(this);

		// Constitution objet de données avec toutes les infos nécessaires à la view
		outputModelData = new OutputModelData<Coord>(
				isMoveDone, 
				toCapturePieceCoord, 
				toPromotePieceCoord, 
				toPromotePieceColor);

		return outputModelData;

	}

	/**
	 * @param toMovePieceCoord
	 * @param targetSquareCoord
	 * @return true si la PieceModel à déplacer est de la couleur du joueur courant
	 * et que les coordonnées d'arrivées soient dans les limites du tableau
	 * et qu'il n'y ait pas de pièce sur la case d'arrivée
	 */
	private boolean isPieceMoveable(Coord toMovePieceCoord, Coord targetSquareCoord) { // TODO : remettre en "private" après test unitaires
		boolean bool = false;

		// TODO : à compléter atelier 4 pour gèrer les rafles

		bool = 	this.implementor.isPiecehere(toMovePieceCoord) 
				&& this.implementor.getPieceColor(toMovePieceCoord) == this.currentGamerColor 
				&& Coord.coordonneesValides(targetSquareCoord)
				&& !this.implementor.isPiecehere(targetSquareCoord)
				&& (Coord.lastMove == null || Coord.lastMove.equals(toMovePieceCoord));

		return bool ;
	}

	/**
	 * @param toMovePieceCoord
	 * @param targetSquareCoord
	 * @return true s'il n'existe qu'1 seule pièCe à prendre d'une autre couleur sur la trajectoire
	 * ou pas de pièce à prendre
	 */
	private boolean isThereMaxOnePieceOnItinerary(Coord toMovePieceCoord, Coord targetSquareCoord) {
		List<Coord> itinerary = this.implementor.getCoordsOnItinerary(toMovePieceCoord, targetSquareCoord);
		int pieceAPrendre = 0;
		for (Coord coord : itinerary) {
			if (this.implementor.isPiecehere(coord) && this.implementor.getPieceColor(coord) != this.currentGamerColor) {
				pieceAPrendre += 1;
			} else if (this.implementor.getPieceColor(coord) == this.currentGamerColor) {
				return false;
			}
		}
		return pieceAPrendre <= 1;
	}

	/**
	 * @param toMovePieceCoord
	 * @param targetSquareCoord
	 * @return les coord de la pièce à prendre, null sinon
	 */
	private Coord getToCapturePieceCoord(Coord toMovePieceCoord, Coord targetSquareCoord) {
		Coord toCapturePieceCoord = null;
		List<Coord> itinerary = this.implementor.getCoordsOnItinerary(toMovePieceCoord, targetSquareCoord);
		for (Coord coord : itinerary) {
			if (this.implementor.isPiecehere(coord)) {
				toCapturePieceCoord = coord;
			}
		}
		return toCapturePieceCoord;
	}

	/**
	 * @param toMovePieceCoord
	 * @param targetSquareCoord
	 * @param isPieceToCapture
	 * @return true si le déplacement est légal
	 * (s'effectue en diagonale, avec ou sans prise)
	 * La PieceModel qui se trouve aux coordonnées passées en paramêtre
	 * est capable de répondre à cette question (par l'intermédiare du ModelImplementor)
	 */
	boolean isMovePiecePossible(Coord toMovePieceCoord, Coord targetSquareCoord, boolean isPieceToCapture) { // TODO : remettre en "private" après test unitaires
		return this.implementor.isMovePieceOk(toMovePieceCoord, targetSquareCoord, isPieceToCapture ) ;
	}

	public boolean isRaflePossible(Coord initCoord) {
		PieceModel initPiece = this.implementor.findPiece(initCoord);
		if (initPiece instanceof AbstractPieceModel) {
			AbstractPieceModel abstractPiece = (AbstractPieceModel) initPiece;
			List<Coord> rafleCoords = abstractPiece.movePossible();
			boolean isCapturable;
			for (Coord coord : rafleCoords) {
				isCapturable = this.getToCapturePieceCoord(initCoord, coord) != null;
				if (this.isThereMaxOnePieceOnItinerary(initCoord, coord)
					&& this.isPieceMoveable(initCoord, coord)
					&& this.isMovePiecePossible(initCoord, coord, isCapturable)
				) {
					return true;
				}
			}
		}
		return false;
	}

	boolean isPiecePromotable(Coord coord) {
		System.out.println("isPiecePromotable " + this.implementor.isPiecePromotable(coord) + " " + coord);
		return this.implementor.isPiecePromotable(coord);
	}

	boolean isPiecePromotable(char x, int y) {
		return this.isPiecePromotable(new Coord(x, y));
	}


	/**
	 * @param toMovePieceCoord
	 * @param targetSquareCoord
	 * Déplacement effectif de la PieceModel
	 */
	void movePiece(Coord toMovePieceCoord, Coord targetSquareCoord) { // TODO : remettre en "private" après test unitaires
		this.implementor.movePiece(toMovePieceCoord, targetSquareCoord);
		Coord.lastMove = targetSquareCoord;
	}

	/**
	 * @param toCapturePieceCoord
	 * Suppression effective de la pièe capturée
	 */
	private void remove(Coord toCapturePieceCoord) { 
		this.implementor.removePiece(toCapturePieceCoord);
	}

	void promotePiece(Coord pieceCoord) {
		this.implementor.promotePiece(pieceCoord);
	}

	void switchGamer() {
		this.currentGamerColor = (PieceSquareColor.WHITE).equals(this.currentGamerColor) ?
				PieceSquareColor.BLACK : PieceSquareColor.WHITE;
		Coord.lastMove = null;
	}


}