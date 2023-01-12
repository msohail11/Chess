/**
 * Board is the game board class. It contains the actual chessboard the game is being played on, as well as methods to draw the board to the screen, update the state of the board, and determine if a player is in Check or Checkmate.
 * @author Waleed Rizwan
 * @author Mohammad (Massab) Sohail
 */

package chess;

import java.util.ArrayList;

public class Board {

    /**
     * board is chessboard that is filled with Pieces. Null if the tile is empty.
     * letters is an array that makes finding the index of the column easier
     * prevMove is a Piece that stores the most previously moved piece
     * startingCoord stores the coordinates of prevMove
     */

    private static Piece[][] board = new Piece[8][8];
    private static final String[] letters = { "a", "b", "c", "d", "e", "f", "g", "h" };
    public Piece prevMove = null;
    public int [] startingCoord= {0,0,0,0};
    private Piece prevStart;
    private Piece prevDest;
    private int[] prevStartPos;
    private int[] prevDestPos;

    /**
     * Contructor that initializes board and makes game ready to play by calling initializeBoardPieces()
     */
    public Board()
    {
        initializeBoardPieces();
    }


    /**
     * Method to fill in the chessboard with white and black pieces and make game ready for play
     */
    public void initializeBoardPieces(){
        //First add white pawns to rank 2 (index of row is 6 in array though) and black pawns to rank 7 (row 1 in array though)
        for(int i = 0; i < 8; i++) {
            board[6][i] = new Pawn("w", "p");
            board[1][i] = new Pawn("b", "p");
        }
        board[7][0] = new Rook("w","R");
        board[7][1] = new Knight("w","N");
        board[7][2] = new Bishop("w","B");
        board[7][3] = new Queen("w","Q");
        board[7][4] = new King("w","K");
        board[7][5] = new Bishop("w","B");
        board[7][6] = new Knight("w","N");
        board[7][7] = new Rook("w","R");
        board[0][0] = new Rook("b","R");
        board[0][1] = new Knight("b","N");
        board[0][2] = new Bishop("b","B");
        board[0][3] = new Queen("b","Q");
        board[0][4] = new King("b","K");
        board[0][5] = new Bishop("b","B");
        board[0][6] = new Knight("b","N");
        board[0][7] = new Rook("b","R");
    }

    /**
     * Method to update the state of the board depending on the user's move. If the move is not legal, this method does not do anything and instead simply returns false.
     * @param sRow Row of starting Tile in the board array
     * @param sCol Column of starting Tile in the board array
     * @param dRow Row of destination Tile in the board array
     * @param dCol Column of destination Tile in the board array
     * @param board instance of the game board that the game is being played on
     * @param isWhiteTurn true if it is white player's turn, false otherwise
     * @return True if the move was legal and the board was successfully updated, otherwise method returns false without doing anything.
     */



    public boolean updateBoard(int sRow, int sCol, int dRow, int dCol, Board board, boolean isWhiteTurn){
        Piece[][] gameBoard = board.getBoard();
        Piece startPiece = gameBoard[sRow][sCol];
        Piece destinationTile = gameBoard[dRow][dCol];

        if(startPiece == null){
            return false;
        }
        //white's move
        if(isWhiteTurn && startPiece.getColor().equals("w") && startPiece.canMove(sRow, sCol, dRow, dCol, board, prevMove, startingCoord) && startPiece.isPathClear(sRow, sCol, dRow, dCol, board)) {
            gameBoard[dRow][dCol] = startPiece;
            gameBoard[sRow][sCol] = null;
            ArrayList<Integer> kcoord = findKing(true, board);
            Piece temp = board.getBoard()[kcoord.get(0)][kcoord.get(1)];
            if(inCheck(sRow,sCol,board, isWhiteTurn)){
                gameBoard[dRow][dCol] = destinationTile;
                gameBoard[sRow][sCol] = startPiece;
                return false;
            }
            else{

                startingCoord[0] = sRow;
                startingCoord[1] =sCol;
                startingCoord[2] =dRow;
                startingCoord[3] =dCol;
                prevMove = gameBoard[dRow][dCol];
                prevStart = startPiece;
                prevStartPos = new int[]{sRow, sCol};
                prevDest = destinationTile;
                prevDestPos = new int[]{dRow,dCol};
                return true;
            }
        }//end white's move

        //black's move
        else if((!isWhiteTurn) && startPiece.getColor() == "b" && startPiece.canMove(sRow, sCol, dRow, dCol, board, prevMove, startingCoord) && startPiece.isPathClear(sRow, sCol, dRow, dCol, board)){
            gameBoard[dRow][dCol] = startPiece;
            gameBoard[sRow][sCol] = null;
            ArrayList<Integer> kcoord = findKing(false, board);
            Piece temp = board.getBoard()[kcoord.get(0)][kcoord.get(1)];

            if(inCheck(sRow,sCol,board, isWhiteTurn)){
                gameBoard[dRow][dCol] = destinationTile;
                gameBoard[sRow][sCol] = startPiece;
                return false;
            }
            else{
                startingCoord[0] = sRow;
                startingCoord[1] =sCol;
                startingCoord[2] =dRow;
                startingCoord[3] =dCol;
                prevMove = gameBoard[dRow][dCol];
                prevStart = startPiece;
                prevStartPos = new int[]{sRow, sCol};
                prevDest = destinationTile;
                prevDestPos = new int[]{dRow,dCol};
                return true;
            }
        }//end black's move

        return false;
    }

    /**
     * Checks to see if the king of a player is in Check.
     * @return true King is in check by an enemy piece. False otherwise
     */
    public static boolean inCheck(int sRow, int sCol, Board board, boolean isWhite){

        Piece sPiece = board.getBoard()[sRow][sCol];
        ArrayList<Integer> kcoord = findKing(isWhite, board);
        String color = isWhite ? "w" : "b";
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Piece temp = board.getBoard()[i][j];
                if(temp != null && ! temp.getColor().equals(color)){
                    ArrayList<Integer> isCheckMove = temp.listMoves(i,j,board).stream().filter(move -> {
                        return move.get(0) == kcoord.get(0) && move.get(1) == kcoord.get(1);
                    }).findAny().orElse(null);
                    if(isCheckMove != null){
                        return true;
                    }
                }
            }
        }
        return false;

    }

    /**
     *
     * Checks if player is in CheckMate -- no viable options to get king out of check
     * @return true if player has no viable moves to get out of Check. False if player has viable moves remaining.
     */
    public static boolean isCheckMate(String color, Board board){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Piece temp = board.getBoard()[i][j];
                if(temp != null && temp.getColor().equals(color)){
                    for(ArrayList<Integer> move :  temp.listMoves(i,j,board)){
                        boolean success = board.updateBoard(i,j,move.get(0), move.get(1),board, color.equals("w"));
                        if(success){
                            board.undoMove();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Method that finds a king based on color provided
     * @param white color of king in question
     * @param board chessboard
     * @return arraylist of king coordinates
     */
    private static ArrayList<Integer> findKing(boolean white, Board board){
        ArrayList<Integer> kCoord = new ArrayList<>();
        Piece tempPiece;
            for(int i = 0; i< 8; i++){
                for(int j = 0; j < 8; j++){
                    tempPiece = board.getBoard()[i][j];
                    if(tempPiece != null){
                        if(white && tempPiece.getName().equals("K") && tempPiece.getColor().equals("w")){
                            kCoord.add(i);
                            kCoord.add(j);
                            return kCoord;
                        }
                        else if(!white && tempPiece.getName().equals("K") && tempPiece.getColor().equals("b")){
                            kCoord.add(i);
                            kCoord.add(j);
                            return kCoord;
                        }
                    }
                }
            }
        return kCoord;
    }

    /**
     * Method to undo a move.
     */

    private void undoMove(){
        Piece[][] gameBoard = this.getBoard();
        gameBoard[prevDestPos[0]][prevDestPos[1]] = prevDest;
        gameBoard[prevStartPos[0]][prevStartPos[1]] = prevStart;
        prevStart = null;
        prevDest = null;
        prevStartPos = null;
        prevDestPos = null;
    }


    /**
     * Draws the current state of the chessboard on terminal for user to see
     */
    public void drawBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boolean isLight = (i % 2) == (j % 2);
                //piece name to board
                if(board[i][j] != null){
                    System.out.print(board[i][j] + " ");
                }
                //pieceless tile -- either black or white
                else{
                    if(isLight){
                        System.out.print("   ");
                    }
                    else{
                        System.out.print("## ");
                    }
                }
            }
            System.out.print(8-i);
            System.out.println();

        }
        for(int k = 0; k < 8; k++){
            System.out.print(" " + letters[k] + " ");
        }
        System.out.println();
        System.out.println();
    }

    /**
     * Gives user access to Board object in order to access to the game board itself or methods like UpdateBoard and isInCheck.
     * @return Board object
     */
    public static Piece[][] getBoard() {
        return board;
    }

    /**
     * Method that sets a specific tile on the board equal to the piece passed in.
     * @param i row of board
     * @param j column of board
     * @param piece A chess piece that will now occupy a tile
     */
    public static void setBoard(int i, int j, Piece piece) {
        Board.board[i][j] = piece;
    }


}
