/**
 * Pawn Class is responsible for implementing the canMove method based on the chess rules for a legal move with a pawn.
 * @author Waleed Rizwan
 * @author Mohammad (Massab) Sohail
 */

package chess;

import java.util.ArrayList;
import java.util.Arrays;

public class Pawn extends Piece {
    public boolean HasMadeFirstMove() {
        return hasMadeFirstMove;
    }

    public void setHasMadeFirstMove(boolean hasMadeFirstMove) {
        this.hasMadeFirstMove = hasMadeFirstMove;
    }

    private boolean hasMadeFirstMove;
    /**
     * constructor calls the superclass Piece constructor to assign color and name to each piece
     * @param color The piece can have color "w" for white, or "b" for black
     * @param name The abbreviated name of the piece: "P" for Pawn
     */
    public Pawn(String color, String name){
        super(color,name);
        this.hasMadeFirstMove = false;
        this.enpassant = false;
    }


    /**
     *Checks to see if the piece can move to a given tile with the passed in coordinates.
     * @param sRow Row of starting Tile in the board array
     * @param sCol Column of starting Tile in the board array
     * @param dRow Row of destination Tile in the board array
     * @param dCol Column of destination Tile in the board array
     * @param board instance of the game board that the game is being played on
     * @param prevMove The piece that was moved last turn
     * @param CoordOfPrevMove starting row and column of the previous move
     * @return true if move is possible with given coordinates for a Pawn, false otherwise
     */
    @Override
    public boolean canMove(int sRow, int sCol, int dRow, int dCol,  Board board, Piece prevMove, int [] CoordOfPrevMove) {
        Piece[][] gameBoard = board.getBoard();
        Piece sPiece = gameBoard[sRow][sCol];
        Piece dPiece = gameBoard[dRow][dCol];
        String color = sPiece.getColor();
        if(sPiece != null && dPiece != null){
            if(sPiece.getColor().equals(dPiece.getColor())){
                return false;
            }
        }

        //white pawn
        if(color.equals("w")){ //ex: c2 c3
            //white enpassant
            if(Pawn.class.isInstance(prevMove) && prevMove.enpassant) {
                int prevMoveRow = CoordOfPrevMove[2];
                int prevMoveCol = CoordOfPrevMove[3];
                if ((prevMoveRow == 3 && sRow == 3) && (Math.abs(sCol - prevMoveCol) == 1)) {
                    if (dRow == 2 && dCol == prevMoveCol) {
                        //update board pawn indirectly captured
                        gameBoard[prevMoveRow][prevMoveCol] = null;
                        return true;
                    }
                }
            }//end enpassant

            if((dCol == sCol && dRow == sRow- 1) && isPathClear(sRow, sCol, dRow, dCol, board)){
                hasMadeFirstMove = true;
                return true;
            }
            else if((dCol == sCol && dRow == sRow- 2) && !hasMadeFirstMove && isPathClear(sRow, sCol, dRow, dCol, board)){
                enpassant = true;
                hasMadeFirstMove = true;
                return true;
            }
            //pawn is capturing an enemy piece
            else if(((dCol == sCol + 1 && dRow == sRow- 1) ||(dCol == sCol - 1 && dRow == sRow- 1) ) && dPiece != null && isPathClear(sRow, sCol, dRow, dCol, board)){
                hasMadeFirstMove = true;
                return true;
            }
            //
        }

        //black pawn
        else{
            //black enpassant
            if(Pawn.class.isInstance(prevMove) && prevMove.enpassant) {
                int prevMoveRow = CoordOfPrevMove[2];
                int prevMoveCol = CoordOfPrevMove[3];
                if ((prevMoveRow == 4 && sRow == 4) && (Math.abs(sCol - prevMoveCol) == 1)) {
                    if (dRow == 5 && dCol == prevMoveCol) {
                        //update board pawn indirectly captured
                        gameBoard[prevMoveRow][prevMoveCol] = null;
                        return true;
                    }
                }
            } // end enpassant

            if((dCol == sCol && dRow == sRow + 1) && isPathClear(sRow, sCol, dRow, dCol, board)){
                hasMadeFirstMove = true;
                return true;
            }
            else if((dCol == sCol && dRow == sRow + 2) && !hasMadeFirstMove && isPathClear(sRow, sCol, dRow, dCol, board)){
                enpassant = true;
                hasMadeFirstMove = true;
                return true;
            }
            else if(((dCol == sCol + 1 && dRow == sRow + 1) ||(dCol == sCol - 1 && dRow == sRow + 1) ) && dPiece != null && isPathClear(sRow, sCol, dRow, dCol, board)){
                hasMadeFirstMove = true;
                return true;
            }
            //
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
    public boolean isPathClear(int sRow, int sCol, int dRow, int dCol, Board board ) {

        //might cause trouble with enpassant
        Piece[][] gameBoard = board.getBoard();
        Piece sPiece = gameBoard[sRow][sCol];
        Piece dPiece = gameBoard[dRow][dCol];
        String color = sPiece.getColor();
        //is moving vertically 1 square
        if(sCol == dCol && (dRow == sRow - 1 || dRow == sRow + 1)){
            if(dPiece != null){
                return false;
            }
        }
        //white is moving vertically 2 squares

        else if(sCol == dCol && (dRow == sRow - 2 || dRow == sRow + 2) && color.equals("w")){
           Piece tempPiece = gameBoard[dRow + 1][sCol];
            return dPiece == null && tempPiece == null;
        }
        //black is moving vertically 2 squares
        else if(sCol == dCol && (dRow == sRow - 2 || dRow == sRow + 2) && color.equals("b")){
            Piece tempPiece = gameBoard[dRow - 1][sCol];
            return dPiece == null && tempPiece == null;
        }


        return true;
    }

    /**
     * Gives a list of viable moves that a piece can make
     * @param sRow starting row of piece
     * @param sCol starting col of piece
     * @param board instance of chessboard
     * @return Arraylist of all viable moves
     */
    @Override
    public ArrayList<ArrayList<Integer>> listMoves(int sRow, int sCol, Board board) {
        ArrayList<ArrayList<Integer>> moves = new ArrayList<>();
        Piece[][] pieces = board.getBoard();
        String color = this.getColor();
        int modifier = "b".equals(color) ? 1 : -1;
        // Forward check
        int forwardRow = sRow + modifier;
        if (forwardRow >= 0
                && forwardRow <= 7
                && (pieces[forwardRow][sCol] == null)) {
            moves.add(new ArrayList<>(Arrays.asList(forwardRow, sCol)));
            // Double forward check
            int doubleForwardRow = sRow + modifier + modifier;
            if (!this.hasMadeFirstMove && pieces[doubleForwardRow][sCol] == null) {
                moves.add(new ArrayList<>(Arrays.asList(doubleForwardRow, sCol)));
            }
        }
        // Left check
        // Right check

        // En passant check
        return moves;
    }



}
