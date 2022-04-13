package gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import nutsAndBolts.PieceSquareColor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


/**
 * @author francoise.perrin
 * 
 * Cette classe est responsable de :
 * 		créer les cases noires et blanches et les positionner au bon endroit sur le damier
 * 		créer les pions noirs et blancs en leur affectant une image et les positionner sur leur case initiale
 *		promouvoir les pions en dame en changeant leur image
 */
public class GuiFactory {

	
	/**
	 * @param col
	 * @param ligne
	 * @return Une case noire ou blanche en alternance
	 * la case en bas à gauche est noire
	 */
	public static BorderPane createSquare(int col, int ligne) {
		PieceSquareColor squareColor;
		// sélection de la couleur de la case
		if ((col % 2 == 0 && ligne % 2 == 0) || (col % 2 != 0 && ligne % 2 != 0)) {
			squareColor = PieceSquareColor.WHITE;
		} else {
			squareColor = PieceSquareColor.BLACK;
		}

		// la couleur est définie par les valeurs par défaut de configuration
		Color color = PieceSquareColor.BLACK.equals(squareColor) ? GuiConfig.CASERED : GuiConfig.CASEGREEN;
		return new SquareGui(color);
	}

	/**
	 * @param col
	 * @param ligne
	 * @return une PieceGui si col/ligne correspond à cases noires
	 * des 4 lignes du haut (piece noire) et du bas du damier (piece blanche)
	 */
	public static ImageView createPiece(int col, int ligne) {

		PieceGui pieceGui = null;
		Image image = null;
		PieceSquareColor pieceColor = null;

		if  ( !((col % 2 == 0 && ligne % 2 == 0) || (col % 2 != 0 && ligne % 2 != 0)) ) {
			if (ligne < 4)
				pieceColor = PieceSquareColor.BLACK;
			if (ligne > 5)
				pieceColor = PieceSquareColor.WHITE;
		}
		if (pieceColor != null) {
			image = GuiFactory.createImage(pieceColor, true);
			pieceGui = new PieceGui(image);
		}

		return pieceGui;
	}

	/**
	 * @param piece
	 * @param promotedPieceColor
	 * la promotion consiste à changer l'image de la PieceGui
	 */
	public static void PromotePiece(ImageView piece, PieceSquareColor promotedPieceColor) {

		// ToDo Atelier 3
		
	}
	
	/**
	 * @param pieceColor
	 * @param ispawn
	 * @return une image créée à partir d'un fichier png
	 */
	private static Image createImage(PieceSquareColor pieceColor, boolean ispawn) {

		Image image = null;
		String pieceImageFile = null, nomImageFile = null;
		File g = new File("");

		if (ispawn) {
			nomImageFile = pieceColor == PieceSquareColor.BLACK ? "PionNoir.png" : "PionBlanc.png";
		}
		else {	
			nomImageFile = pieceColor == PieceSquareColor.BLACK ? "DameNoire.png" : "DameBlanche.png";
		}

		pieceImageFile = g.getAbsolutePath()+"/images/" + nomImageFile;	// TODO - attention au chemin
		try {
			image = new Image(new FileInputStream(pieceImageFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return image;
	}


}


