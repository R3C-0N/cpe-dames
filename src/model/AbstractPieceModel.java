package model;

import nutsAndBolts.PieceSquareColor;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractPieceModel implements PieceModel {

    protected Coord coord;
    protected PieceSquareColor pieceColor;
    protected int direction;

    public AbstractPieceModel(Coord coord, PieceSquareColor pieceColor) {
        super();
        this.coord = coord;
        this.pieceColor = pieceColor;
        this.direction = PieceSquareColor.BLACK.equals(this.getPieceColor()) ? -1 : 1;
    }

    public char getColonne() {
        return coord.getColonne();
    }

    public int getLigne() {
        return coord.getLigne();
    }

    public boolean hasThisCoord(Coord coord) {
        return this.coord.equals(coord);
    }

    public PieceSquareColor getPieceColor() {
        return pieceColor;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public Coord getCoord() {
        return coord;
    }

    public String toString() {
        return " ["+pieceColor.toString().charAt(0) + coord + ", " + (this.getClass().getSimpleName()) +"]";
    }

    public void move(Coord coord) {
        this.coord = coord;
    }

    public List<Coord> getCoordsOnItinerary(Coord targetCoord) {
        List<Coord> coordsOnItinery = new LinkedList<>();
        for	(int i = 1; i < Math.abs(targetCoord.getLigne() - this.getLigne()); i++) {
            coordsOnItinery.add(new Coord((char) (this.getColonne() + i * this.direction), this.getLigne() + i * this.direction));
        }
        return coordsOnItinery;
    }

    public abstract boolean isMoveOk(Coord targetCoord, boolean isPieceToCapture);

    public abstract List<Coord> movePossible();

    public abstract boolean isPromotable();
}
