/**
 * Chess class is the driver for the entire Chess application. 
 * It starts the game and calls on the Board class to make updates to the state of the board and verify if a player is in Check or CheckMate.
 * It handles all the input for moves and promotions and can accept resignation and draws as valid moves
 * @author Waleed Rizwan
 * @author Mohammad (Massab) Sohail
 */

package chess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;


public class Chess {

    public static void main(String[] args) throws IOException {
        Board gameBoard = new Board();
        gameBoard.drawBoard();
        int sRow,sCol,dRow,dCol = 0;
        boolean isWhiteTurn = true;
        boolean noCheckMate =true;
        boolean hasOfferedDraw = false;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(noCheckMate){
            //if white turn (first move of game is always made by white)
            if(isWhiteTurn){
                System.out.print("White's move: ");
                String wCoordinates = reader.readLine();
                
                if(hasOfferedDraw && wCoordinates.equals("draw")){
                    System.out.println("Draw");
                    return;
                }
                
                if(wCoordinates.contains("draw?")){
                    hasOfferedDraw = true;
                    isWhiteTurn = false;
                    continue;
                }
                else{
                    hasOfferedDraw = false;
                    while(!isValid(wCoordinates, hasOfferedDraw)){
                        System.out.println("Illegal move, try again");
                        System.out.print("White's move: ");
                        wCoordinates = reader.readLine();
                    }
                }
                if(wCoordinates.equals("resign")){
                    System.out.println("Black Wins");
                    return;
                }

                boolean wLegalMove = false;
                if(!wCoordinates.contains("draw?")){

	                sRow = getRowCoord(Character.getNumericValue(wCoordinates.charAt(1)));
	                sCol = getColCoord(wCoordinates.charAt(0));
	                dRow = getRowCoord(Character.getNumericValue(wCoordinates.charAt(4)));
	                dCol =  getColCoord(wCoordinates.charAt(3));
	                
	
	                wLegalMove = gameBoard.updateBoard(sRow,sCol,dRow,dCol, gameBoard, true);
                }
                sRow = 0;
                sCol = 0;
                dRow = 0;
                dCol = 0;
                while(!wLegalMove && !wCoordinates.contains("draw?")){
                    System.out.println("Illegal move, try again");
                    System.out.print("white's move: ");
                    wCoordinates = reader.readLine();
                    if(isValid(wCoordinates, hasOfferedDraw)){
                        if(wCoordinates.equals("resign")){
                            System.out.println("Black Wins");
                            return;
                        }

                        if(hasOfferedDraw && wCoordinates.equals("draw")){
                            System.out.println("Draw");
                            return;
                        }
                        sRow = getRowCoord(Character.getNumericValue(wCoordinates.charAt(1)));
                        sCol = getColCoord(wCoordinates.charAt(0));
                        dRow = getRowCoord(Character.getNumericValue(wCoordinates.charAt(4)));
                        dCol =  getColCoord(wCoordinates.charAt(3));
                        wLegalMove = gameBoard.updateBoard(sRow,sCol,dRow,dCol, gameBoard, true);
                    }
                    else{
                        continue;
                    }
                }
                //IMPLEMENT PROMOTION HERE
                Piece PieceInQuestion = gameBoard.getBoard()[dRow][dCol];
                if(isPromotionElgible(PieceInQuestion, true, dRow)){
                    if(wCoordinates.length() == 7){
                        PieceInQuestion = promotePiece(wCoordinates.charAt(6), "w");
                    }
                    else{
                        PieceInQuestion = promotePiece('q' , "w");
                    }
                    gameBoard.setBoard(dRow, dCol, PieceInQuestion);
                }
                Piece prev = gameBoard.prevMove;

                System.out.println();
                gameBoard.drawBoard();

                if(gameBoard.inCheck(sRow, sCol, gameBoard, false)){


                    if(gameBoard.isCheckMate( "w",gameBoard)){
                        System.out.println("Checkmate");
                        System.out.println("White wins");
                       return;
                    }
                    else{
                        System.out.println("Check");
                    }

                }


            } //end white turn


            //black turn
            else{
                System.out.print("Black's move: ");
                String bCoordinates = reader.readLine();
                if(hasOfferedDraw && bCoordinates.equals("draw")){
                    System.out.println("Draw");
                    return;
                }
                if(bCoordinates.contains("draw?")){
                    hasOfferedDraw = true;
                    isWhiteTurn = true;
                    continue;
                }
                else{
                    hasOfferedDraw = false;
                    while(!isValid(bCoordinates, hasOfferedDraw)){
                        System.out.println("Illegal move, try again");
                        System.out.print("White's move: ");
                        bCoordinates = reader.readLine();
                    }
                } 
                bCoordinates = reader.readLine();
                if(bCoordinates.equals("resign")){
                    System.out.println("White Wins");
                    return;
                }
                sRow = getRowCoord(Character.getNumericValue(bCoordinates.charAt(1)));
                sCol = getColCoord(bCoordinates.charAt(0));
                dRow = getRowCoord(Character.getNumericValue(bCoordinates.charAt(4)));
                dCol =  getColCoord(bCoordinates.charAt(3));
                boolean bLegalMove = gameBoard.updateBoard(sRow,sCol,dRow,dCol, gameBoard, false);
                while(!bLegalMove){
                    System.out.println("Illegal move, try again");
                    System.out.print("Black's move: ");
                    bCoordinates = reader.readLine();
                    if(isValid(bCoordinates, hasOfferedDraw)){
                        if(bCoordinates.equals("resign")){
                            System.out.println("White Wins");
                            return;
                        }
                        if(hasOfferedDraw && bCoordinates.equals("draw")){
                            System.out.println("Draw");
                            return;
                        }
                        sRow = getRowCoord(Character.getNumericValue(bCoordinates.charAt(1)));
                        sCol = getColCoord(bCoordinates.charAt(0));
                        dRow = getRowCoord(Character.getNumericValue(bCoordinates.charAt(4)));
                        dCol =  getColCoord(bCoordinates.charAt(3));
                        bLegalMove = gameBoard.updateBoard(sRow,sCol,dRow,dCol, gameBoard, false);
                    }
                    else{
                        continue;
                    }
                }
                if(bCoordinates.contains("draw?")){
                    hasOfferedDraw = true;
                }
                else{
                    hasOfferedDraw = false;
                }
                //IMPLEMENT PROMOTION HERE
                Piece PieceInQuestion = gameBoard.getBoard()[dRow][dCol];
                if(isPromotionElgible(PieceInQuestion, false, dRow)){
                    if(bCoordinates.length() == 7){
                        PieceInQuestion = promotePiece(bCoordinates.charAt(6), "b");
                    }
                    else{
                        PieceInQuestion = promotePiece('q', "b");
                    }
                    gameBoard.setBoard(dRow, dCol, PieceInQuestion);
                }
                Piece prev = gameBoard.prevMove;

                System.out.println();
                gameBoard.drawBoard();

                if(gameBoard.inCheck(sRow, sCol, gameBoard, true) ){

                    if(gameBoard.isCheckMate( "b",gameBoard)){
                        System.out.println("Checkmate");
                        System.out.println("Black wins");
                        return;
                    }
                    else{
                        System.out.println( "Check");
                    }

                }

            }//end black turn
            isWhiteTurn = !isWhiteTurn;
        }
    //checkmate-- game over
    return;
    }//end main method

    /**
     * Checks to see if the piece provided is eligible for Promotion
     * @param piece the Piece in question
     * @param isWhitePiece true if the piece is a white piece, false if it is a black piece
     * @param rank the row the piece is on in the chessboard
     * @return true if eligible, false otherwise
     */
    private static boolean isPromotionElgible(Piece piece, boolean isWhitePiece, int rank ){
        if(Pawn.class.isInstance(piece)){
            if(isWhitePiece && rank == 0){
                return true;
            }
            else if((!isWhitePiece)&& rank == 7){
                return true;
            }
        }
        return false;
    }

    /**
     * Promotes a piece based on input. If user
     * @param newPiece
     * @param color
     * @return
     */
    private static Piece promotePiece(char newPiece, String color){
        newPiece = Character.toUpperCase(newPiece);
        if(newPiece == 'R'){
            return new Rook(color, "R");
        }
        if(newPiece == 'N'){
            return new Knight(color, "N");
        }
        if(newPiece == 'B'){
            return new Bishop(color, "B");
        }
        if(newPiece == 'Q'){
            return new Queen(color, "Q");
        }
        return new Queen(color, "Q");
    }

    /**
     * User enters "FileRank FileRank" as input. This method returns the column in the board array that corresponds to the File the user entered
     * @param col File passed in by user
     * @return the array index that corresponds to the File
     */
    private static int getColCoord(char col){
        col = Character.toLowerCase(col);
        char[] letters = {'a','b','c','d','e','f','g','h'};
        for(int i = 0; i < 8; i++){
            if (letters[i] == col){
                return i;
            }
        }
        return 0;
    }

    /**
     * User enters "FileRank FileRank" as input. This method returns the row in the board array that corresponds to the Rank the user entered
     * @param row Rank passed in by user
     * @return the array index that corresponds to the Rank
     */
    private static int getRowCoord(int row){
        return 8 - row;
    }

    /**
     * Determines if input user passed into program is a valid chess move or not. The input must be in a specific format: FileRank FileRank with "draw?" optionally appended to the end
     * A user can input the letters n, r ,b, or q at the end of a move to indicate a promotion
     * @param input The string the user entered into terminal as input
     * @param hasOfferedDraw true if the previous player's move asked for a draw, else false
     * @return true if the user entered valid input, false otherwise
     */
    private static boolean isValid (String input, boolean hasOfferedDraw){
        input = input.toLowerCase();
        ArrayList<Character> letters = new ArrayList<Character>(Arrays.asList('a', 'b','c', 'd', 'e', 'f', 'g', 'h'));
        ArrayList<Character> numbers = new ArrayList<Character>(Arrays.asList('1', '2','3', '4', '5', '6', '7', '8'));
        if(input.equals("resign")){
            return true;
        }
        //player is agreeing to accept draw offer
        if(hasOfferedDraw && input.equals("draw")){
            return true;
        }
        //player is moving piece to promotion and indicating what piece it will be.
        if(input.length() == 7){
            String coordinates = input.substring(0,5);
            Character space = input.charAt(5);
            Character promotionType = input.charAt(6);
            if(coordinates.length() != 5 || !letters.contains(coordinates.charAt(0)) || !numbers.contains(coordinates.charAt(1)) || coordinates.charAt(2) != ' ' || !letters.contains(coordinates.charAt(3)) || !numbers.contains(coordinates.charAt(4)) || (coordinates.charAt(0) == coordinates.charAt(3) && coordinates.charAt(1) == coordinates.charAt(4))){
                return false;
            }
            if(space != ' '){
                return false;
            }
            if(promotionType != 'r' && promotionType != 'n' && promotionType != 'b' && promotionType != 'q'){
                return false;
            }
            return true;

        }
        //player is moving piece and offering draw e.g: "h3 h5 draw?"
        if(input.length() == 11){
            String coordinates = input.substring(0,5);
            Character space = input.charAt(5);
            String drawOffer = input.substring(6);
            if(coordinates.length() != 5 || !letters.contains(coordinates.charAt(0)) || !numbers.contains(coordinates.charAt(1)) || coordinates.charAt(2) != ' ' || !letters.contains(coordinates.charAt(3)) || !numbers.contains(coordinates.charAt(4)) || (coordinates.charAt(0) == coordinates.charAt(3) && coordinates.charAt(1) == coordinates.charAt(4))){
                return false;
            }
            if(space != ' '){
                return false;
            }
            if(!drawOffer.equals("draw?")){
                return false;
            }
            if(!hasOfferedDraw){
                return true;
            }
            else{
                return false;
            }
        }
        //else checks to make sure it is just a regular move e.g: "a1 a3"
        else if(input.length() != 5 || !letters.contains(input.charAt(0)) || !numbers.contains(input.charAt(1)) || input.charAt(2) != ' ' || !letters.contains(input.charAt(3)) || !numbers.contains(input.charAt(4)) || (input.charAt(0) == input.charAt(3) && input.charAt(1) == input.charAt(4))){
            return false;
        }
        return true;
    }

}//end Chess class