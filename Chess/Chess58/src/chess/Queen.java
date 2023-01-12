/**
 * Queen Class is responsible for implementing the canMove method based on the chess rules for a legal move with a queen.
 * @author Waleed Rizwan
 * @author Mohammad (Massab) Sohail
 */

package chess;

import java.util.ArrayList;

public class Queen extends Piece {

    /**
     * constructor calls the superclass Piece constructor to assign color and name to each piece
     * @param color The piece can have color "w" for white, or "b" for black
     * @param name The abbreviated name of the piece: "Q" for Queen
     */
    public Queen(String color, String name){
        super(color,name);
    }


    /**
     *Checks to see if the piece can move to a given tile with the passed in coordinates.
     * @param sRow Row of starting Tile in the board array
     * @param sCol Column of starting Tile in the board array
     * @param dRow Row of destination Tile in the board array
     * @param dCol Column of destination Tile in the board array
     * @param board instance of the game board that the game is being played on
     * @param prevMove The piece that was moved last turn
     * @param startingCoordOfPrevMove starting row and column of the previous move
     * @return true if move is possible with given coordinates for a specific piece, false otherwise
     */
    @Override
    public boolean canMove(int sRow, int sCol, int dRow, int dCol,  Board board, Piece prevMove, int [] startingCoordOfPrevMove) {
        Piece[][] gameBoard = board.getBoard();
        Piece sPiece = gameBoard[sRow][sCol];
        String color = sPiece.getColor();
        Rook rookPiece = new Rook(color, "R");
        Bishop bishopPiece = new Bishop(color, "B");
        //queen can go horizontal, vertical, diagonal
        //queen is basically combination of rook + bishop

        int deltaX =Math.abs(sRow-dRow);
        int deltaY =Math.abs(sCol-dCol);
        if(deltaX == deltaY && bishopPiece.isPathClear(sRow, sCol, dRow, dCol, board)){ //diagonal
            return true;
        }
        else if(sRow == dRow && sCol != dCol && rookPiece.isPathClear(sRow, sCol, dRow, dCol, board)){ //horizontal
            return true;
        }
        else if(sCol == dCol && sRow != dRow && rookPiece.isPathClear(sRow, sCol, dRow, dCol, board)){ //vertical
            return true;
        }

        return false;
    }


    /**
     *Checks to see if there are any blocking pieces in the path of the piece that is trying to be moved.
     * @param sRow Row of starting Tile in the board array
     * @param sCol Column of starting Tile in the board array
     * @param dRow Row of destination Tile in the board array
     * @param dCol Column of destination Tile in the board array
     * @param board instance of the game board that the game is being played on
     * @return true if the path is clear, false otherwise
     */
    @Override
    public boolean isPathClear(int sRow, int sCol, int dRow, int dCol, Board board) {
        Piece[][] gameBoard = board.getBoard();
        Piece sPiece = gameBoard[sRow][sCol];
        Piece dPiece = gameBoard[dRow][dCol];

        //if trying to attk same color piece
        if(sPiece != null && dPiece != null){
            if(sPiece.getColor().equals(dPiece.getColor())){
                return false;
            }
        }
        return true;
    }//end isPathClear

    /**
     * Gives a list of viable moves that a piece can make
     * @param sRow starting row of piece
     * @param sCol starting col of piece
     * @param board instance of chessboard
     * @return Arraylist of all viable moves
     */


    @Override
    public ArrayList<ArrayList<Integer>> listMoves(int sRow, int sCol, Board board) {
        Bishop bishop = new Bishop(this.getColor(), this.getName());
        Rook rook = new Rook(this.getColor(), this.getName());
        ArrayList<ArrayList<Integer>> bishopMoves = bishop.listMoves(sRow, sCol, board);
        ArrayList<ArrayList<Integer>> rookMoves = rook.listMoves(sRow, sCol, board);
        ArrayList<ArrayList<Integer>> queenMoves = new ArrayList<>();
        queenMoves.addAll(bishopMoves);
        queenMoves.addAll(rookMoves);
        return queenMoves;
    }



}
