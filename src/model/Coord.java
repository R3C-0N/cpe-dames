package model;



/**
 * @author francoiseperrin
 *
 * Coordonnées des PieceModel
 */
public class Coord implements Comparable<Coord> {

	public static Coord lastMove;
	private char colonne; // ['a'..'j']
	private int ligne; // [10..1]
	static final int MAX = ModelConfig.LENGTH; // 10

	public Coord(char colonne, int ligne) {
		super();
		this.colonne = colonne;
		this.ligne = ligne;
	}

	public char getColonne() {
		return colonne;
	}

	public int getLigne() {
		return ligne;
	}

	@Override
	public String toString() {
		return "[" + ligne + "," + colonne + "]";
	}

	/**
	 * @param coord
	 * @return true si 'a' <= col < 'a'+MAX et 1 < lig <= MAX
	 */
	public static boolean coordonneesValides(Coord coord) {
		return ('a' <= coord.getColonne() && coord.getColonne() < 'a' + MAX && 1 <= coord.getLigne()
				&& coord.getLigne() <= MAX);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.oect)
	 *
	 * La méthode compareTo() indique comment comparer un objet à l'objet courant
	 * selon l'ordre dit naturel
	 * Dans cet application, nous décidons que l'ordre naturel est celui
	 * correspondant au N° de la case d'un tableau 2D représenté par la Coord
	 * ainsi le N° 1 correspond à la Coord ['a', 10], le N° 100 correspond à la Coord ['j', 1]
	 */
	@Override
	public int compareTo(Coord o) {

		if (this.ligne == o.getLigne() && this.colonne == o.getColonne())
			return 0;
		else if (this.colonne <= o.getColonne() && this.ligne >= o.getLigne())
			return -1;
		else
			return 0;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;

		if (o.getClass() != this.getClass())
			return false;

		final Coord other = (Coord) o;
		if (this.colonne != other.getColonne())
			return false;

		if (this.ligne != other.getLigne())
			return false;

		return true;
	}

	//Correctionf
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + colonne;
		result = prime * result + ligne;
		return result;
	}
}
