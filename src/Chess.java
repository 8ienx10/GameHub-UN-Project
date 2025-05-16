import javax.swing.*; //importing some libraries
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Chess { //creating the class Chess

    private static boolean party; //boolean to check if the game is a party or not
    private static int cpuDifficulty; //int to check the difficulty of the cpu
    private static boolean darkTheme; //boolean to check if the theme is dark or not

    private static boolean playerTurn = true; //boolean to check if the player turn is true or not
    public static int activatedPieceMoves = -1; //int to check the activated piece moves
    public static boolean isCheck = false; //boolean to check if the game is in check or not


    private static int[] blackPiecesIndexes = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}; // indexes of the black pieces
    private static int[] whitePiecesIndexes = {56,57,58,59,60,61,62,63,48,49,50,51,52,53,54,55}; // indexes of the white pieces

    //blackPiecesIndexes = {BlackRooKIndex 0, BlackKnightIndex 1, BlackBishopIndex 2, BlackQueenIndex 3, BlackKingIndex 4, BlackBishop2Index 5, BlackKnight2Index 6, BlackRook2Index 7, BlackPawn1Index 8, BlackPawn2Index 9, BlackPawn3Index 10, BlackPawn4Index 11, BlackPawn5Index 12, BlackPawn6Index 13, BlackPawn7Index 14, BlackPawn8Index 15};
    //whitePiecesIndexes = {WhiteRookIndex 0, WhiteKnightIndex 1, WhiteBishopIndex 2, WhiteQueenIndex 3, WhiteKingIndex 4, WhiteBishop2Index 5, WhiteKnight2Index 6, WhiteRook2Index 7, WhitePawn1Index 8, WhitePawn2Index 9, WhitePawn3Index 10, WhitePawn4Index 11, WhitePawn5Index 12, WhitePawn6Index 13, WhitePawn7Index 14, WhitePawn8Index 15};


    // the powns can move 2 spaces only in the first move
    private static boolean blackPawn1FirstMove = true;
    private static boolean blackPawn2FirstMove = true;
    private static boolean blackPawn3FirstMove = true;
    private static boolean blackPawn4FirstMove = true;
    private static boolean blackPawn5FirstMove = true;
    private static boolean blackPawn6FirstMove = true;
    private static boolean blackPawn7FirstMove = true;
    private static boolean blackPawn8FirstMove = true;

    private static boolean whitePawn1FirstMove = true;
    private static boolean whitePawn2FirstMove = true;
    private static boolean whitePawn3FirstMove = true;
    private static boolean whitePawn4FirstMove = true;
    private static boolean whitePawn5FirstMove = true;
    private static boolean whitePawn6FirstMove = true;
    private static boolean whitePawn7FirstMove = true;
    private static boolean whitePawn8FirstMove = true;

    Chess(boolean party, int cpuDifficulty, boolean darkThem) { //constructor of the class Chess

        this.party = party; // initializing party
        this.cpuDifficulty = cpuDifficulty; // initializing cpuDifficulty
        this.darkTheme = darkThem; // initializing darkTheme

        new ChessUI(); //creating ChessUI

    }

    public static void boardClick(ActionEvent e){ //method to check if the board is clicked

        for (int i = 0; i < 64; i++) { //cycle through the board buttons
            if (e.getSource() == ChessUI.buttons[i]) { //if the source of the event is the button
                if (ChessUI.buttons[i].getBackground() == Color.DARK_GRAY || ChessUI.buttons[i].getBackground() == Color.RED) //if the button is dark gray or red
                    move(i, activatedPieceMoves,false); // calling move method
                else if (ChessUI.buttons[i].getIcon() != null) { //if the button has an icon
                    clearBackGround(); //clearing the background
                    printMoves(getMoves(i,false)); //calling printMoves method
                }
                else // if its not a button click
                    clearBackGround(); //clearing the background
            }
        }

    }

    public static void restartGame(ActionEvent e) { //method to restart the game

        //restarting some variables to their default values
        playerTurn = true;

        blackPawn1FirstMove = true;
        blackPawn2FirstMove = true;
        blackPawn3FirstMove = true;
        blackPawn4FirstMove = true;
        blackPawn5FirstMove = true;
        blackPawn6FirstMove = true;
        blackPawn7FirstMove = true;
        blackPawn8FirstMove = true;

        whitePawn1FirstMove = true;
        whitePawn2FirstMove = true;
        whitePawn3FirstMove = true;
        whitePawn4FirstMove = true;
        whitePawn5FirstMove = true;
        whitePawn6FirstMove = true;
        whitePawn7FirstMove = true;
        whitePawn8FirstMove = true;

        ChessUI.frame.dispose(); //disposing the frame
        new Chess(party, cpuDifficulty,darkTheme); //creating a new Chess object (restart the game)

    }

    public static void backToMenu(ActionEvent e) { //method to go back to the menu

        //restarting some variables to their default values
        playerTurn = true;

        blackPawn1FirstMove = true;
        blackPawn2FirstMove = true;
        blackPawn3FirstMove = true;
        blackPawn4FirstMove = true;
        blackPawn5FirstMove = true;
        blackPawn6FirstMove = true;
        blackPawn7FirstMove = true;
        blackPawn8FirstMove = true;

        whitePawn1FirstMove = true;
        whitePawn2FirstMove = true;
        whitePawn3FirstMove = true;
        whitePawn4FirstMove = true;
        whitePawn5FirstMove = true;
        whitePawn6FirstMove = true;
        whitePawn7FirstMove = true;
        whitePawn8FirstMove = true;

        ChessUI.frame.dispose(); //disposing the frame (closing the window)
        Main.main(null); //calling the main method of the Main class

    }

    private static void printMoves(ArrayList<ArrayList<Integer>> moves) { //method to print the moves of the pieces

        for (int i : moves.get(0)) //for the normal moves
            ChessUI.buttons[i].setBackground(Color.DARK_GRAY); //setting the background of the button to dark gray

        L: for (int i : moves.get(1)) { //for the kill moves
            if (playerTurn) { //if its the player turn
                for (int j = 0; j < whitePiecesIndexes.length; j++) //for the white pieces
                    if (whitePiecesIndexes[j] == i) //if the piece is white
                        continue L; //continue to the next iteration (skiping i)
            }
            else { //if its not the player turn
                for (int k = 0; k < blackPiecesIndexes.length; k++) //for the black pieces
                    if (blackPiecesIndexes[k] == i) //if the piece is black
                        continue L; //continue to the next iteration (skiping i)
            }

            ChessUI.buttons[i].setBackground(Color.RED); //setting the background of the button to red

        }
    }

    private static void move(int moveTo, int pieceIndex,boolean isCpu) { //method to move the pieces

        clearBackGround(); //clearing the background

        ChessUI.buttons[moveTo].setIcon(ChessUI.buttons[pieceIndex].getIcon()); //setting the icon of the button to the icon of the piece
        ChessUI.buttons[pieceIndex].setIcon(null); //setting the icon of the button to null (removing the piece)

        activatedPieceMoves = -1;

        if (playerTurn) { //if its the player turn
            for (int i = 0; i < blackPiecesIndexes.length; i++) { //for the black pieces
                if (blackPiecesIndexes[i] == moveTo) //if the piece is black
                    blackPiecesIndexes[i] = -1; //setting the index to -1 (removing the piece)
                if (!isCheck || ChessUI.buttons[i].getBackground() != Color.YELLOW) { //if the game is not in check or the button background is not yellow
                    try { //try to set the background of the button to green
                        if (party) //if its a party
                            ChessUI.buttons[blackPiecesIndexes[i]].setBackground(Color.GREEN); //setting the background of the button to green
                    } catch (ArrayIndexOutOfBoundsException ex) { //catching the exception if the index is out of bounds
                        //do nothing
                    }
                }
            }
            for (int i = 0; i < whitePiecesIndexes.length; i++) //for the white pieces
                if (whitePiecesIndexes[i] == pieceIndex) { //if the piece is white
                    whitePiecesIndexes[i] = moveTo; //setting the index to the moveTo index
                    switch (i) { //switching the index to check which pawn is moved (so we can set the first move to false)
                        case 8:
                            whitePawn1FirstMove = false;
                            break;
                        case 9:
                            whitePawn2FirstMove = false;
                            break;
                        case 10:
                            whitePawn3FirstMove = false;
                            break;
                        case 11:
                            whitePawn4FirstMove = false;
                            break;
                        case 12:
                            whitePawn5FirstMove = false;
                            break;
                        case 13:
                            whitePawn6FirstMove = false;
                            break;
                        case 14:
                            whitePawn7FirstMove = false;
                            break;
                        case 15:
                            whitePawn8FirstMove = false;
                            break;
                    }
                }

            checkWin(); //checking if the game is won
            if (party) //if its a party
                playerTurn = !playerTurn; //changing the player turn
        }
        else {
            for (int i = 0; i < whitePiecesIndexes.length; i++) { //for the white pieces
                if (whitePiecesIndexes[i] == moveTo) //if the piece is white
                    whitePiecesIndexes[i] = -1; //setting the index to -1 (removing the piece)
                if (!isCheck || ChessUI.buttons[i].getBackground() != Color.YELLOW) { //if the game is not in check or the button background is not yellow
                    try { //try to set the background of the button to green
                        if (party) //if its a party
                            ChessUI.buttons[whitePiecesIndexes[i]].setBackground(Color.GREEN); //setting the background of the button to green
                    } catch (ArrayIndexOutOfBoundsException ex) { //catching the exception if the index is out of bounds
                        //do nothing
                    }
                }

            }
            for (int i = 0; i < blackPiecesIndexes.length; i++) //for the black pieces
                if (blackPiecesIndexes[i] == pieceIndex) { //if the piece is black
                    blackPiecesIndexes[i] = moveTo; //setting the index to the moveTo index
                    switch (i) { //switching the index to check which pawn is moved (so we can set the first move to false)
                        case 8:
                            blackPawn1FirstMove = false;
                            break;
                        case 9:
                            blackPawn2FirstMove = false;
                            break;
                        case 10:
                            blackPawn3FirstMove = false;
                            break;
                        case 11:
                            blackPawn4FirstMove = false;
                            break;
                        case 12:
                            blackPawn5FirstMove = false;
                            break;
                        case 13:
                            blackPawn6FirstMove = false;
                            break;
                        case 14:
                            blackPawn7FirstMove = false;
                            break;
                        case 15:
                            blackPawn8FirstMove = false;
                            break;
                    }
                }

            checkWin(); //checking if the game is won
            if (party) //if its a party
                playerTurn = !playerTurn; //changing the player turn
        }

        if (!party && !isCpu) //if its not a party and its not a cpu call
            switch (cpuDifficulty) { //switching the cpu difficulty
                case 1:
                    cpuDifficiltyEasy(); //calling the cpu difficulty easy method
                    break;
                case 2:
                    cpuDifficiltyMedium(); //calling the cpu difficulty medium method
                    break;
                case 3:
                    cpuDifficiltyHard(); //calling the cpu difficulty hard method
                    break;
            }

    }

    private static void cpuDifficiltyEasy() {
        playerTurn = !playerTurn; //changing the player turn
        Random random = new Random(); //creating a random object
        ArrayList<Integer> validPieces = new ArrayList<>(); //creating an array list to store the valid pieces

        for (int i = 0; i < blackPiecesIndexes.length; i++) { //for the black pieces
            if (blackPiecesIndexes[i] != -1) { //if the piece is not -1
                validPieces.add(blackPiecesIndexes[i]); //adding the piece to the array list
            }
        }

        while (true) { //infinite loop to check if the piece is valid
            boolean p = true; //boolean to check if the piece is valid
            int pieceIndex = validPieces.get(random.nextInt(validPieces.size())); //getting a random piece from the array list
            ArrayList<ArrayList<Integer>> moves = getMoves(pieceIndex,false); //getting the moves of the piece
            if (!moves.get(1).isEmpty()) { //if the piece has kill moves
                int moveTo = moves.get(1).get(random.nextInt(moves.get(1).size())); //getting a random kill move from the array list
                l: for (int i : moves.get(1)) { //for the kill moves
                    for (int k = 0; k < blackPiecesIndexes.length; k++) //for the black pieces
                        if (blackPiecesIndexes[k] == i) { //if the piece is black
                            p = false; //setting the boolean to false
                            break l; //breaking the loop
                        }
                }
                if (p) { //if the piece is valid
                    move(moveTo, pieceIndex, true); //calling the move method
                    break; //breaking the loop
                }
            }
            else if (!moves.get(0).isEmpty()) { //if the piece has normal moves
                int moveTo = moves.get(0).get(random.nextInt(moves.get(0).size())); //getting a random normal move from the array list
                move(moveTo, pieceIndex,true); //calling the move method
                break; //breaking the loop
            }
        }
        checkWin(); //checking if the game is won
        playerTurn = !playerTurn; //changing the player turn

    }

    private static void cpuDifficiltyMedium() {
        cpuDifficiltyEasy(); //calling the cpu difficulty easy method (for now)
        // Will be added in the future 'Maybe'
    }

    private static void cpuDifficiltyHard() {
        cpuDifficiltyEasy(); //calling the cpu difficulty easy method (for now)
        // Will be added in the future 'Maybe'
    }


    private static void checkWin() {

        isCheck = false; //setting the check to false

        if (whitePiecesIndexes[4] == -1) { //if the white king is dead
            ChessUI.winnerLabel.setText(" Black wins!"); // setting the winner label to black wins
            ChessUI.winnerLabel.setVisible(true); // making the winner label visible
            ChessUI.winnerPanel.setVisible(true); // making the winner panel visible

            for (int i = 0; i < 64; i++) { //cycle through the board buttons (reseting the board colors)
                if ((i / 8 + i % 8) % 2 == 0)
                    ChessUI.buttons[i].setBackground(Color.WHITE); //setting the background of the button to white
                else //if the button is odd
                    ChessUI.buttons[i].setBackground(Color.GRAY); //setting the background of the button to gray

                ChessUI.buttons[i].setEnabled(false); //disabling the button
            }

        }
        else if (blackPiecesIndexes[4] == -1) { //if the black king is dead
            ChessUI.winnerLabel.setText(" White wins!"); // setting the winner label to white wins
            ChessUI.winnerLabel.setVisible(true); // making the winner label visible
            ChessUI.winnerPanel.setVisible(true); // making the winner panel visible


            for (int i = 0; i < 64; i++) { //cycle through the board buttons (reseting the board colors)
                if ((i / 8 + i % 8) % 2 == 0)
                    ChessUI.buttons[i].setBackground(Color.WHITE); //setting the background of the button to white
                else
                    ChessUI.buttons[i].setBackground(Color.GRAY); //setting the background of the button to gray

                ChessUI.buttons[i].setEnabled(false); //disabling the button
            }
        }

        for (int i : blackPiecesIndexes) { //for the black pieces
            ArrayList<ArrayList<Integer>> moves = getMoves(i,true);
            for (int j : moves.get(1)) { //for the kill moves
                if (j == whitePiecesIndexes[4]) { //if the move is the white king
                    ChessUI.buttons[i].setBackground(Color.YELLOW);  //setting the background of the button to yellow
                    isCheck = true; //setting the check to true
                }
            }
        }

        for (int i : whitePiecesIndexes) { //for the white pieces
            ArrayList<ArrayList<Integer>> moves = getMoves(i,true);
            for (int j : moves.get(1)) { //for the kill moves
                if (j == blackPiecesIndexes[4]) { //if the move is the black king
                    ChessUI.buttons[i].setBackground(Color.YELLOW); //setting the background of the button to yellow
                    isCheck = true; //setting the check to true
                }
            }
        }

    }

    private static void clearBackGround() {
        for (int i = 0; i < 64; i++) { //cycle through the board buttons
            if ((i / 8 + i % 8) % 2 == 0) {
                if (!isCheck || ChessUI.buttons[i].getBackground() != Color.YELLOW)
                    ChessUI.buttons[i].setBackground(Color.WHITE); //setting the background of the button to white
            }
            else {
                if (!isCheck || ChessUI.buttons[i].getBackground() != Color.YELLOW)
                    ChessUI.buttons[i].setBackground(Color.GRAY); //setting the background of the button to gray
            }
        }
    }

    private static ArrayList<ArrayList<Integer>> getMoves(int pieceIndex, boolean isCheckWin) { //method to get the moves of the pieces

        ArrayList<ArrayList<Integer>> moveSet = new ArrayList<>(); //creating an array list to store the moves
        ArrayList<Integer> normalMove = new ArrayList<>(); //creating an array list to store the normal moves
        ArrayList<Integer> killMove = new ArrayList<>(); //creating an array list to store the kill moves
        moveSet.add(normalMove); //adding the normal moves to the array list
        moveSet.add(killMove); //adding the kill moves to the array list


        if (playerTurn || isCheckWin) { //if its the player turn or the game is in check

            if (pieceIndex == whitePiecesIndexes[0] || pieceIndex == whitePiecesIndexes[7]) { // white rooks

                activatedPieceMoves = pieceIndex;

                for (int i = pieceIndex+8; i < 64; i += 8) {  // cant use switch because index is not final
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }
                for (int i = pieceIndex-8; i >= 0; i-= 8) {
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);

                }
                for (int i = pieceIndex+1; i < 64; i++) {
                    if (i % 8 == 0)
                        break;
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }
                for (int i = pieceIndex-1; i >= 0; i--) {
                    if ((i+1) % 8 == 0)
                        break;
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }
            }
            else if (pieceIndex == whitePiecesIndexes[1] || pieceIndex == whitePiecesIndexes[6]) { // white knights

                activatedPieceMoves = pieceIndex;

                if (pieceIndex + 6 < 64 && Math.abs((pieceIndex + 6) % 8 - (pieceIndex % 8)) <= 2 && Math.abs((pieceIndex + 6) / 8 - (pieceIndex / 8)) <= 2) {
                    if (ChessUI.buttons[pieceIndex + 6].getIcon() != null)
                        killMove.add(pieceIndex + 6);
                    else
                        normalMove.add(pieceIndex + 6);
                }
                if (pieceIndex - 6 >= 0 && Math.abs((pieceIndex - 6) % 8 - (pieceIndex % 8)) <= 2 && Math.abs((pieceIndex - 6) / 8 - (pieceIndex / 8)) <= 2) {
                    if (ChessUI.buttons[pieceIndex - 6].getIcon() != null)
                        killMove.add(pieceIndex - 6);
                    else
                        normalMove.add(pieceIndex - 6);
                }
                if (pieceIndex + 10 < 64 && Math.abs((pieceIndex + 10) % 8 - (pieceIndex % 8)) <= 2 && Math.abs((pieceIndex + 10) / 8 - (pieceIndex / 8)) <= 2) {
                    if (ChessUI.buttons[pieceIndex + 10].getIcon() != null)
                        killMove.add(pieceIndex + 10);
                    else
                        normalMove.add(pieceIndex + 10);
                }
                if (pieceIndex - 10 >= 0 && Math.abs((pieceIndex - 10) % 8 - (pieceIndex % 8)) <= 2 && Math.abs((pieceIndex - 10) / 8 - (pieceIndex / 8)) <= 2) {
                    if (ChessUI.buttons[pieceIndex - 10].getIcon() != null)
                        killMove.add(pieceIndex - 10);
                    else
                        normalMove.add(pieceIndex - 10);
                }
                if (pieceIndex + 15 < 64 && Math.abs((pieceIndex + 15) % 8 - (pieceIndex % 8)) <= 2 && Math.abs((pieceIndex + 15) / 8 - (pieceIndex / 8)) <= 2) {
                    if (ChessUI.buttons[pieceIndex + 15].getIcon() != null)
                        killMove.add(pieceIndex + 15);
                    else
                        normalMove.add(pieceIndex + 15);
                }
                if (pieceIndex - 15 >= 0 && Math.abs((pieceIndex - 15) % 8 - (pieceIndex % 8)) <= 2 && Math.abs((pieceIndex - 15) / 8 - (pieceIndex / 8)) <= 2) {
                    if (ChessUI.buttons[pieceIndex - 15].getIcon() != null)
                        killMove.add(pieceIndex - 15);
                    else
                        normalMove.add(pieceIndex - 15);
                }
                if (pieceIndex + 17 < 64 && Math.abs((pieceIndex + 17) % 8 - (pieceIndex % 8)) <= 2 && Math.abs((pieceIndex + 17) / 8 - (pieceIndex / 8)) <= 2) {
                    if (ChessUI.buttons[pieceIndex + 17].getIcon() != null)
                        killMove.add(pieceIndex + 17);
                    else
                        normalMove.add(pieceIndex + 17);
                }
                if (pieceIndex -17 >= 0 && Math.abs((pieceIndex - 17) % 8 - (pieceIndex % 8)) <= 2 && Math.abs((pieceIndex - 17) / 8 - (pieceIndex / 8)) <= 2) {
                    if (ChessUI.buttons[pieceIndex -17].getIcon() != null)
                        killMove.add(pieceIndex -17);
                    else
                        normalMove.add(pieceIndex -17);
                }
            }
            else if (pieceIndex == whitePiecesIndexes[2] || pieceIndex == whitePiecesIndexes[5]) { // white bishops

                activatedPieceMoves = pieceIndex;

                for (int i = pieceIndex+9; i < 64 && (i % 8) != 0; i += 9) {
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }
                for (int i = pieceIndex-9; i >= 0 && (i % 8) != 7; i-= 9) {
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }
                for (int i = pieceIndex+7; i < 64 && (i % 8) != 7; i+=7) {
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }
                for (int i = pieceIndex-7; i >= 0 && (i % 8) != 0; i-=7) {
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }
            }
            else if (pieceIndex == whitePiecesIndexes[3]) { // white queen

                activatedPieceMoves = pieceIndex;

                for (int i = pieceIndex+8; i < 64; i += 8) {
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }
                for (int i = pieceIndex-8; i >= 0; i -= 8) {
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);

                }
                for (int i = pieceIndex+1; i < 64; i++) {
                    if (i % 8 == 0)
                        break;
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }
                for (int i = pieceIndex-1; i >= 0; i--) {
                    if ((i+1) % 8 == 0)
                        break;
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }
                for (int i = pieceIndex+9; i < 64 && (i % 8) != 0; i += 9) {
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }
                for (int i = pieceIndex-9; i >= 0 && (i % 8) != 7; i -= 9) {
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }
                for (int i = pieceIndex+7; i < 64 && (i % 8) != 7; i += 7) {
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }
                for (int i = pieceIndex-7; i >= 0 && (i % 8) != 0; i-=7) {
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }
            }
            else if (pieceIndex == whitePiecesIndexes[4]) { // white king

                activatedPieceMoves = pieceIndex;

                if (pieceIndex + 1 < 64 && (pieceIndex % 8) != 7) {
                    if (ChessUI.buttons[pieceIndex + 1].getIcon() != null)
                        killMove.add(pieceIndex + 1);
                    else
                        normalMove.add(pieceIndex + 1);
                }

                if (pieceIndex - 1 >= 0 && (pieceIndex % 8) != 0) {
                    if (ChessUI.buttons[pieceIndex - 1].getIcon() != null)
                        killMove.add(pieceIndex - 1);
                    else
                        normalMove.add(pieceIndex - 1);
                }

                if (pieceIndex + 8 < 64) {
                    if (ChessUI.buttons[pieceIndex + 8].getIcon() != null)
                        killMove.add(pieceIndex + 8);
                    else
                        normalMove.add(pieceIndex + 8);
                }

                if (pieceIndex - 8 >= 0) {
                    if (ChessUI.buttons[pieceIndex - 8].getIcon() != null)
                        killMove.add(pieceIndex - 8);
                    else
                        normalMove.add(pieceIndex - 8);
                }

                if (pieceIndex + 9 < 64 && (pieceIndex % 8) != 7) {
                    if (ChessUI.buttons[pieceIndex + 9].getIcon() != null)
                        killMove.add(pieceIndex + 9);
                    else
                        normalMove.add(pieceIndex + 9);
                }

                if (pieceIndex - 9 >= 0 && (pieceIndex % 8) != 0) {
                    if (ChessUI.buttons[pieceIndex - 9].getIcon() != null)
                        killMove.add(pieceIndex - 9);
                    else
                        normalMove.add(pieceIndex - 9);
                }

                if (pieceIndex + 7 < 64 && (pieceIndex % 8) != 0) {
                    if (ChessUI.buttons[pieceIndex + 7].getIcon() != null)
                        killMove.add(pieceIndex + 7);
                    else
                        normalMove.add(pieceIndex + 7);
                }

                if (pieceIndex - 7 >= 0 && (pieceIndex % 8) != 7) {
                    if (ChessUI.buttons[pieceIndex - 7].getIcon() != null)
                        killMove.add(pieceIndex - 7);
                    else
                        normalMove.add(pieceIndex - 7);
                }
            }
            else if (pieceIndex == whitePiecesIndexes[8]) { // white pawn1

                activatedPieceMoves = pieceIndex;

                int x = 0;
                for (int i = pieceIndex-8; i >= 0; i -= 8) {
                    if (ChessUI.buttons[i].getIcon() != null)
                        break;
                    normalMove.add(i);
                    x++;
                    if (x == 2 && whitePawn1FirstMove)
                        break;
                    else if ( x == 1 && !whitePawn1FirstMove)
                        break;
                }

                if (pieceIndex - 7 >= 0 && (pieceIndex % 8) != 0 && (pieceIndex % 8) != 7)
                    if (ChessUI.buttons[pieceIndex - 7].getIcon() != null)
                        killMove.add(pieceIndex - 7);
                if (pieceIndex - 9 >= 0 && (pieceIndex % 8) != 7 && (pieceIndex % 8) != 0)
                    if (ChessUI.buttons[pieceIndex - 9].getIcon() != null)
                        killMove.add(pieceIndex - 9);
            }
            else if (pieceIndex == whitePiecesIndexes[9]) { // white pawn2

                activatedPieceMoves = pieceIndex;

                int x = 0;
                for (int i = pieceIndex-8; i >= 0; i -= 8) {
                    if (ChessUI.buttons[i].getIcon() != null)
                        break;
                    normalMove.add(i);
                    x++;
                    if (x == 2 && whitePawn2FirstMove)
                        break;
                    else if ( x == 1 && !whitePawn2FirstMove)
                        break;
                }

                if (pieceIndex - 7 >= 0 && (pieceIndex % 8) != 0 && (pieceIndex % 8) != 7)
                    if (ChessUI.buttons[pieceIndex - 7].getIcon() != null)
                        killMove.add(pieceIndex - 7);
                if (pieceIndex - 9 >= 0 && (pieceIndex % 8) != 7 && (pieceIndex % 8) != 0)
                    if (ChessUI.buttons[pieceIndex - 9].getIcon() != null)
                        killMove.add(pieceIndex - 9);
            }
            else if (pieceIndex == whitePiecesIndexes[10]) { // white pawn3

                activatedPieceMoves = pieceIndex;

                int x = 0;
                for (int i = pieceIndex-8; i >= 0; i -= 8) {
                    if (ChessUI.buttons[i].getIcon() != null)
                        break;
                    normalMove.add(i);
                    x++;
                    if (x == 2 && whitePawn3FirstMove)
                        break;
                    else if ( x == 1 && !whitePawn3FirstMove)
                        break;
                }

                if (pieceIndex - 7 >= 0 && (pieceIndex % 8) != 0 && (pieceIndex % 8) != 7)
                    if (ChessUI.buttons[pieceIndex - 7].getIcon() != null)
                        killMove.add(pieceIndex - 7);
                if (pieceIndex - 9 >= 0 && (pieceIndex % 8) != 7 && (pieceIndex % 8) != 0)
                    if (ChessUI.buttons[pieceIndex - 9].getIcon() != null)
                        killMove.add(pieceIndex - 9);
            }
            else if (pieceIndex == whitePiecesIndexes[11]) { // white pawn4

                activatedPieceMoves = pieceIndex;

                int x = 0;
                for (int i = pieceIndex-8; i >= 0; i -= 8) {
                    if (ChessUI.buttons[i].getIcon() != null)
                        break;
                    normalMove.add(i);
                    x++;
                    if (x == 2 && whitePawn4FirstMove)
                        break;
                    else if ( x == 1 && !whitePawn4FirstMove)
                        break;
                }

                if (pieceIndex - 7 >= 0 && (pieceIndex % 8) != 0 && (pieceIndex % 8) != 7)
                    if (ChessUI.buttons[pieceIndex - 7].getIcon() != null)
                        killMove.add(pieceIndex - 7);
                if (pieceIndex - 9 >= 0 && (pieceIndex % 8) != 7 && (pieceIndex % 8) != 0)
                    if (ChessUI.buttons[pieceIndex - 9].getIcon() != null)
                        killMove.add(pieceIndex - 9);
            }
            else if (pieceIndex == whitePiecesIndexes[12]) {// white pawn5

                activatedPieceMoves = pieceIndex;

                int x = 0;
                for (int i = pieceIndex-8; i >= 0; i -= 8) {
                    if (ChessUI.buttons[i].getIcon() != null)
                        break;
                    normalMove.add(i);
                    x++;
                    if (x == 2 && whitePawn5FirstMove)
                        break;
                    else if ( x == 1 && !whitePawn5FirstMove)
                        break;
                }

                if (pieceIndex - 7 >= 0 && (pieceIndex % 8) != 0 && (pieceIndex % 8) != 7)
                    if (ChessUI.buttons[pieceIndex - 7].getIcon() != null)
                        killMove.add(pieceIndex - 7);
                if (pieceIndex - 9 >= 0 && (pieceIndex % 8) != 7 && (pieceIndex % 8) != 0)
                    if (ChessUI.buttons[pieceIndex - 9].getIcon() != null)
                        killMove.add(pieceIndex - 9);
            }
            else if (pieceIndex == whitePiecesIndexes[13]) { // white pawn6

                activatedPieceMoves = pieceIndex;

                int x = 0;
                for (int i = pieceIndex-8; i >= 0; i -= 8) {
                    if (ChessUI.buttons[i].getIcon() != null)
                        break;
                    normalMove.add(i);
                    x++;
                    if (x == 2 && whitePawn6FirstMove)
                        break;
                    else if ( x == 1 && !whitePawn6FirstMove)
                        break;
                }

                if (pieceIndex - 7 >= 0 && (pieceIndex % 8) != 0 && (pieceIndex % 8) != 7)
                    if (ChessUI.buttons[pieceIndex - 7].getIcon() != null)
                        killMove.add(pieceIndex - 7);
                if (pieceIndex - 9 >= 0 && (pieceIndex % 8) != 7 && (pieceIndex % 8) != 0)
                    if (ChessUI.buttons[pieceIndex - 9].getIcon() != null)
                        killMove.add(pieceIndex - 9);
            }
            else if (pieceIndex == whitePiecesIndexes[14]) { // white pawn7

                activatedPieceMoves = pieceIndex;

                int x = 0;
                for (int i = pieceIndex-8; i >= 0; i -= 8) {
                    if (ChessUI.buttons[i].getIcon() != null)
                        break;
                    normalMove.add(i);
                    x++;
                    if (x == 2 && whitePawn7FirstMove)
                        break;
                    else if ( x == 1 && !whitePawn7FirstMove)
                        break;
                }

                if (pieceIndex - 7 >= 0 && (pieceIndex % 8) != 0 && (pieceIndex % 8) != 7)
                    if (ChessUI.buttons[pieceIndex - 7].getIcon() != null)
                        killMove.add(pieceIndex - 7);
                if (pieceIndex - 9 >= 0 && (pieceIndex % 8) != 7 && (pieceIndex % 8) != 0)
                    if (ChessUI.buttons[pieceIndex - 9].getIcon() != null)
                        killMove.add(pieceIndex - 9);
            }
            else if (pieceIndex == whitePiecesIndexes[15]) { // white pawn8

                activatedPieceMoves = pieceIndex;

                int x = 0;
                for (int i = pieceIndex-8; i >= 0; i -= 8) {
                    if (ChessUI.buttons[i].getIcon() != null)
                        break;
                    normalMove.add(i);
                    x++;
                    if (x == 2 && whitePawn8FirstMove)
                        break;
                    else if ( x == 1 && !whitePawn8FirstMove)
                        break;
                }

                if (pieceIndex - 7 >= 0 && (pieceIndex % 8) != 7 && (pieceIndex % 8) != 0)
                    if (ChessUI.buttons[pieceIndex - 7].getIcon() != null)
                        killMove.add(pieceIndex - 7);
                if (pieceIndex - 9 >= 0 && (pieceIndex % 8) != 0 && (pieceIndex % 8) != 7)
                    if (ChessUI.buttons[pieceIndex - 9].getIcon() != null)
                        killMove.add(pieceIndex - 9);
            }
        }
        else if (!playerTurn || isCheckWin) { //if its not the player turn or the game is in check

            if (pieceIndex == blackPiecesIndexes[0] || pieceIndex == blackPiecesIndexes[7]) { // black rooks

                activatedPieceMoves = pieceIndex;

                for (int i = pieceIndex+8; i < 64; i += 8) {
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }
                for (int i = pieceIndex-8; i >= 0; i-= 8) {
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);

                }
                for (int i = pieceIndex+1; i < 64; i++) {
                    if (i % 8 == 0)
                        break;
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }
                for (int i = pieceIndex-1; i >= 0; i--) {
                    if ((i+1) % 8 == 0)
                        break;
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }

            }
            else if (pieceIndex == blackPiecesIndexes[1] || pieceIndex == blackPiecesIndexes[6]) { // black knights

                activatedPieceMoves = pieceIndex;

                if (pieceIndex + 6 < 64 && Math.abs((pieceIndex + 6) % 8 - (pieceIndex % 8)) <= 2 && Math.abs((pieceIndex + 6) / 8 - (pieceIndex / 8)) <= 2) {
                    if (ChessUI.buttons[pieceIndex + 6].getIcon() != null )
                        killMove.add(pieceIndex + 6);
                    else
                        normalMove.add(pieceIndex + 6);
                }
                if (pieceIndex - 6 >= 0 && Math.abs((pieceIndex - 6) % 8 - (pieceIndex % 8)) <= 2 && Math.abs((pieceIndex - 6) / 8 - (pieceIndex / 8)) <= 2) {
                    if (ChessUI.buttons[pieceIndex - 6].getIcon() != null)
                        killMove.add(pieceIndex - 6);
                    else
                        normalMove.add(pieceIndex - 6);
                }
                if (pieceIndex + 10 < 64 && Math.abs((pieceIndex + 10) % 8 - (pieceIndex % 8)) <= 2 && Math.abs((pieceIndex + 10) / 8 - (pieceIndex / 8)) <= 2) {
                    if (ChessUI.buttons[pieceIndex + 10].getIcon() != null)
                        killMove.add(pieceIndex + 10);
                    else
                        normalMove.add(pieceIndex + 10);
                }
                if (pieceIndex - 10 >= 0 && Math.abs((pieceIndex - 10) % 8 - (pieceIndex % 8)) <= 2 && Math.abs((pieceIndex - 10) / 8 - (pieceIndex / 8)) <= 2) {
                    if (ChessUI.buttons[pieceIndex - 10].getIcon() != null)
                        killMove.add(pieceIndex - 10);
                    else
                        normalMove.add(pieceIndex - 10);
                }
                if (pieceIndex + 15 < 64 && Math.abs((pieceIndex + 15) % 8 - (pieceIndex % 8)) <= 2 && Math.abs((pieceIndex + 15) / 8 - (pieceIndex / 8)) <= 2) {
                    if (ChessUI.buttons[pieceIndex + 15].getIcon() != null)
                        killMove.add(pieceIndex + 15);
                    else
                        normalMove.add(pieceIndex + 15);
                }
                if (pieceIndex - 15 >= 0 && Math.abs((pieceIndex - 15) % 8 - (pieceIndex % 8)) <= 2 && Math.abs((pieceIndex - 15) / 8 - (pieceIndex / 8)) <= 2) {
                    if (ChessUI.buttons[pieceIndex - 15].getIcon() != null)
                        killMove.add(pieceIndex - 15);
                    else
                        normalMove.add(pieceIndex - 15);
                }
                if (pieceIndex + 17 < 64 && Math.abs((pieceIndex + 17) % 8 - (pieceIndex % 8)) <= 2 && Math.abs((pieceIndex + 17) / 8 - (pieceIndex / 8)) <= 2) {
                    if (ChessUI.buttons[pieceIndex + 17].getIcon() != null)
                        killMove.add(pieceIndex + 17);
                    else
                        normalMove.add(pieceIndex + 17);
                }
                if (pieceIndex -17 >= 0 && Math.abs((pieceIndex - 17) % 8 - (pieceIndex % 8)) <= 2 && Math.abs((pieceIndex - 17) / 8 - (pieceIndex / 8)) <= 2) {
                    if (ChessUI.buttons[pieceIndex -17].getIcon() != null)
                        killMove.add(pieceIndex -17);
                    else
                        normalMove.add(pieceIndex -17);
                }

            }
            else if (pieceIndex == blackPiecesIndexes[2] || pieceIndex == blackPiecesIndexes[5]) { // black bishops

                activatedPieceMoves = pieceIndex;

                for (int i = pieceIndex+9; i < 64 && (i % 8) != 0; i += 9) {
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }
                for (int i = pieceIndex-9; i >= 0 && (i % 8) != 7; i-= 9) {
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }
                for (int i = pieceIndex+7; i < 64 && (i % 8) != 7; i+=7) {
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }
                for (int i = pieceIndex-7; i >= 0 && (i % 8) != 0; i-=7) {
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }

            }
            else if (pieceIndex == blackPiecesIndexes[3]) { // black queen

                activatedPieceMoves = pieceIndex;

                for (int i = pieceIndex+8; i < 64; i += 8) {
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }
                for (int i = pieceIndex-8; i >= 0; i -= 8) {
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);

                }
                for (int i = pieceIndex+1; i < 64; i++) {
                    if (i % 8 == 0)
                        break;
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }
                for (int i = pieceIndex-1; i >= 0; i--) {
                    if ((i+1) % 8 == 0)
                        break;
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }
                for (int i = pieceIndex+9; i < 64 && (i % 8) != 0; i += 9) {
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }
                for (int i = pieceIndex-9; i >= 0 && (i % 8) != 7; i -= 9) {
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }
                for (int i = pieceIndex+7; i < 64 && (i % 8) != 7; i += 7) {
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }
                for (int i = pieceIndex-7; i >= 0  && (i % 8) != 0; i-=7) {
                    if (ChessUI.buttons[i].getIcon() != null) {
                        killMove.add(i);
                        break;
                    }
                    normalMove.add(i);
                }
            }
            else if (pieceIndex == blackPiecesIndexes[4]) { // black king

                activatedPieceMoves = pieceIndex;

                if (pieceIndex + 1 < 64 && (pieceIndex % 8) != 7) {
                    if (ChessUI.buttons[pieceIndex + 1].getIcon() != null)
                        killMove.add(pieceIndex + 1);
                    else
                        normalMove.add(pieceIndex + 1);
                }

                if (pieceIndex - 1 >= 0 && (pieceIndex % 8) != 0) {
                    if (ChessUI.buttons[pieceIndex - 1].getIcon() != null)
                        killMove.add(pieceIndex - 1);
                    else
                        normalMove.add(pieceIndex - 1);
                }

                if (pieceIndex + 8 < 64) {
                    if (ChessUI.buttons[pieceIndex + 8].getIcon() != null)
                        killMove.add(pieceIndex + 8);
                    else
                        normalMove.add(pieceIndex + 8);
                }

                if (pieceIndex - 8 >= 0) {
                    if (ChessUI.buttons[pieceIndex - 8].getIcon() != null)
                        killMove.add(pieceIndex - 8);
                    else
                        normalMove.add(pieceIndex - 8);
                }

                if (pieceIndex + 9 < 64 && (pieceIndex % 8) != 7) {
                    if (ChessUI.buttons[pieceIndex + 9].getIcon() != null)
                        killMove.add(pieceIndex + 9);
                    else
                        normalMove.add(pieceIndex + 9);
                }

                if (pieceIndex - 9 >= 0 && (pieceIndex % 8) != 0) {
                    if (ChessUI.buttons[pieceIndex - 9].getIcon() != null)
                        killMove.add(pieceIndex - 9);
                    else
                        normalMove.add(pieceIndex - 9);
                }

                if (pieceIndex + 7 < 64 && (pieceIndex % 8) != 0) {
                    if (ChessUI.buttons[pieceIndex + 7].getIcon() != null)
                        killMove.add(pieceIndex + 7);
                    else
                        normalMove.add(pieceIndex + 7);
                }

                if (pieceIndex - 7 >= 0 && (pieceIndex % 8) != 7) {
                    if (ChessUI.buttons[pieceIndex - 7].getIcon() != null)
                        killMove.add(pieceIndex - 7);
                    else
                        normalMove.add(pieceIndex - 7);
                }
            }
            else if (pieceIndex == blackPiecesIndexes[8]) { // black pawn1

                activatedPieceMoves = pieceIndex;

                int x = 0;
                for (int i = pieceIndex+8; i < 64; i += 8) {
                    if (ChessUI.buttons[i].getIcon() != null)
                        break;
                    normalMove.add(i);
                    x++;
                    if (x == 2 && blackPawn1FirstMove)
                        break;
                    else if ( x == 1 && !blackPawn1FirstMove)
                        break;
                }

                if (pieceIndex + 7 < 64 && (pieceIndex % 8) != 0 && (pieceIndex % 8) != 7)
                    if (ChessUI.buttons[pieceIndex + 7].getIcon() != null)
                        killMove.add(pieceIndex + 7);
                if (pieceIndex + 9 < 64 && (pieceIndex % 8) != 7 && (pieceIndex % 8) != 0)
                    if (ChessUI.buttons[pieceIndex + 9].getIcon() != null)
                        killMove.add(pieceIndex + 9);
            }
            else if (pieceIndex == blackPiecesIndexes[9]) { // black pawn2

                activatedPieceMoves = pieceIndex;

                int x = 0;
                for (int i = pieceIndex+8; i < 64; i += 8) {
                    if (ChessUI.buttons[i].getIcon() != null)
                        break;
                    normalMove.add(i);
                    x++;
                    if (x == 2 && blackPawn2FirstMove)
                        break;
                    else if ( x == 1 && !blackPawn2FirstMove)
                        break;
                }

                if (pieceIndex + 7 < 64 && (pieceIndex % 8) != 0 && (pieceIndex % 8) != 7)
                    if (ChessUI.buttons[pieceIndex + 7].getIcon() != null)
                        killMove.add(pieceIndex + 7);
                if (pieceIndex + 9 < 64 && (pieceIndex % 8) != 7 && (pieceIndex % 8) != 0)
                    if (ChessUI.buttons[pieceIndex + 9].getIcon() != null)
                        killMove.add(pieceIndex + 9);
            }
            else if (pieceIndex == blackPiecesIndexes[10]) { // black pawn3

                activatedPieceMoves = pieceIndex;

                int x = 0;
                for (int i = pieceIndex+8; i < 64; i += 8) {
                    if (ChessUI.buttons[i].getIcon() != null)
                        break;
                    normalMove.add(i);
                    x++;
                    if (x == 2 && blackPawn3FirstMove)
                        break;
                    else if ( x == 1 && !blackPawn3FirstMove)
                        break;
                }

                if (pieceIndex + 7 < 64 && (pieceIndex % 8) != 0 && (pieceIndex % 8) != 7)
                    if (ChessUI.buttons[pieceIndex + 7].getIcon() != null)
                        killMove.add(pieceIndex + 7);
                if (pieceIndex + 9 < 64 && (pieceIndex % 8) != 7 && (pieceIndex % 8) != 0)
                    if (ChessUI.buttons[pieceIndex + 9].getIcon() != null)
                        killMove.add(pieceIndex + 9);
            }
            else if (pieceIndex == blackPiecesIndexes[11]) { // black pawn4

                activatedPieceMoves = pieceIndex;

                int x = 0;
                for (int i = pieceIndex+8; i < 64; i += 8) {
                    if (ChessUI.buttons[i].getIcon() != null)
                        break;
                    normalMove.add(i);
                    x++;
                    if (x == 2 && blackPawn4FirstMove)
                        break;
                    else if ( x == 1 && !blackPawn4FirstMove)
                        break;
                }

                if (pieceIndex + 7 < 64 && (pieceIndex % 8) != 0 && (pieceIndex % 8) != 7)
                    if (ChessUI.buttons[pieceIndex + 7].getIcon() != null)
                        killMove.add(pieceIndex + 7);
                if (pieceIndex + 9 < 64 && (pieceIndex % 8) != 7 && (pieceIndex % 8) != 0)
                    if (ChessUI.buttons[pieceIndex + 9].getIcon() != null)
                        killMove.add(pieceIndex + 9);
            }
            else if (pieceIndex == blackPiecesIndexes[12]) { // black pawn5

                activatedPieceMoves = pieceIndex;

                int x = 0;
                for (int i = pieceIndex+8; i < 64; i += 8) {
                    if (ChessUI.buttons[i].getIcon() != null)
                        break;
                    normalMove.add(i);
                    x++;
                    if (x == 2 && blackPawn5FirstMove)
                        break;
                    else if ( x == 1 && !blackPawn5FirstMove)
                        break;
                }

                if (pieceIndex + 7 < 64 && (pieceIndex % 8) != 0 && (pieceIndex % 8) != 7)
                    if (ChessUI.buttons[pieceIndex + 7].getIcon() != null)
                        killMove.add(pieceIndex + 7);
                if (pieceIndex + 9 < 64 && (pieceIndex % 8) != 7 && (pieceIndex % 8) != 0)
                    if (ChessUI.buttons[pieceIndex + 9].getIcon() != null)
                        killMove.add(pieceIndex + 9);
            }
            else if (pieceIndex == blackPiecesIndexes[13]) { // black pawn6

                activatedPieceMoves = pieceIndex;

                int x = 0;
                for (int i = pieceIndex+8; i < 64; i += 8) {
                    if (ChessUI.buttons[i].getIcon() != null)
                        break;
                    normalMove.add(i);
                    x++;
                    if (x == 2 && blackPawn6FirstMove)
                        break;
                    else if ( x == 1 && !blackPawn6FirstMove)
                        break;
                }

                if (pieceIndex + 7 < 64 && (pieceIndex % 8) != 0 && (pieceIndex % 8) != 7)
                    if (ChessUI.buttons[pieceIndex + 7].getIcon() != null)
                        killMove.add(pieceIndex + 7);
                if (pieceIndex + 9 < 64 && (pieceIndex % 8) != 7 && (pieceIndex % 8) != 0)
                    if (ChessUI.buttons[pieceIndex + 9].getIcon() != null)
                        killMove.add(pieceIndex + 9);
            }
            else if (pieceIndex == blackPiecesIndexes[14]) { // black pawn7

                activatedPieceMoves = pieceIndex;

                int x = 0;
                for (int i = pieceIndex+8; i < 64; i += 8) {
                    if (ChessUI.buttons[i].getIcon() != null)
                        break;
                    normalMove.add(i);
                    x++;
                    if (x == 2 && blackPawn7FirstMove)
                        break;
                    else if ( x == 1 && !blackPawn7FirstMove)
                        break;
                }

                if (pieceIndex + 7 < 64 && (pieceIndex % 8) != 0 && (pieceIndex % 8) != 7)
                    if (ChessUI.buttons[pieceIndex + 7].getIcon() != null)
                        killMove.add(pieceIndex + 7);
                if (pieceIndex + 9 < 64 && (pieceIndex % 8) != 7 && (pieceIndex % 8) != 0)
                    if (ChessUI.buttons[pieceIndex + 9].getIcon() != null)
                        killMove.add(pieceIndex + 9);
            }
            else if (pieceIndex == blackPiecesIndexes[15]) { // black pawn8

                activatedPieceMoves = pieceIndex;

                int x = 0;
                for (int i = pieceIndex+8; i < 64; i += 8) {
                    if (ChessUI.buttons[i].getIcon() != null)
                        break;
                    normalMove.add(i);
                    x++;
                    if (x == 2 && blackPawn8FirstMove)
                        break;
                    else if ( x == 1 && !blackPawn8FirstMove)
                        break;
                }

                if (pieceIndex + 7 < 64 && (pieceIndex % 8) != 0 && (pieceIndex % 8) != 7)
                    if (ChessUI.buttons[pieceIndex + 7].getIcon() != null)
                        killMove.add(pieceIndex + 7);
                if (pieceIndex + 9 < 64 && (pieceIndex % 8) != 7 && (pieceIndex % 8) != 0)
                    if (ChessUI.buttons[pieceIndex + 9].getIcon() != null)
                        killMove.add(pieceIndex + 9);
            }
        }
        return moveSet; // return the move set

    }

class ChessUI { //class for the UI

    private static JFrame frame; // main frame
    private JPanel board; // chess board
    public static JButton[] buttons; // buttons for each square
    private static JPanel winnerPanel; // panel for winner message
    private static JLabel winnerLabel; // label for winner message
    private JPanel buttonPanel; // panel for buttons
    private JButton restartButton; // restart button
    private JButton backButton; // back button

    ChessUI() { // constructor for the UI

        frame = new JFrame(); // main frame
        board = new JPanel(); // chess board
        buttons = new JButton[64]; // buttons for each square
        winnerPanel = new JPanel(); // panel for winner message
        winnerLabel = new JLabel(); // label for winner message
        buttonPanel = new JPanel(new FlowLayout()); // panel for buttons
        restartButton = new JButton(); // restart button
        backButton = new JButton(); // back button

        window(); // create the window

    }

    private void window() { // create the window

        frame.setSize(1000, 1000); // set the size of the window
        frame.setLocationRelativeTo(null); // center the window
        frame.setTitle("Chess"); // set the title of the window
        frame.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessIcon.png"))).getImage()); // set the icon of the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close the window when the user clicks the X button


        board.setLayout(new GridLayout(8, 8)); // set the layout of the chess board
        frame.add(board); // add the chess board to the window

        for (int i = 0; i < 64; i++) { // create buttons for each square
            buttons[i] = new JButton(); // create a button for each square
            if ((i / 8 + i % 8) % 2 == 0) // set the color of the button
                buttons[i].setBackground(Color.WHITE);
            else
                buttons[i].setBackground(Color.GRAY);

            setBoardImage(i); // set the image of the button (a method)

            buttons[i].addActionListener(Chess::boardClick); // add action listener to the button (a board click method)
            buttons[i].setFocusable(false); // disable focus on the button
            buttons[i].setBorderPainted(false); // disable this for easier looking of buttons
            board.add(buttons[i]); // add the button to the chess board
        }

        winnerPanel.setLayout(new BorderLayout()); // set the layout of the winner panel
        if (darkTheme) // set the background color of the winner panel
            winnerPanel.setBackground(Color.BLACK);
        else
            winnerPanel.setBackground(Color.WHITE);
        winnerPanel.setVisible(false); // hide the winner panel by default

        winnerLabel.setFont(new Font("Arial", Font.BOLD, 25)); // set the font of the winner label
        if (darkTheme) // set the color of the winner label
            winnerLabel.setForeground(Color.WHITE);
        else
            winnerLabel.setForeground(Color.BLACK);

        backButton.setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/BackButton.png"))).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH))); // set the icon of the back button(scaled)
        if (darkTheme)
            backButton.setBackground(Color.BLACK);
        else
            backButton.setBackground(Color.gray);
        backButton.setFocusable(false); // disable focus on the button
        backButton.setBorderPainted(false); // disable this for easier looking of buttons
        backButton.setContentAreaFilled(false); // disable this for easier looking of buttons
        backButton.addActionListener(Chess::backToMenu); // add action listener to the button (a back to menu method)

        restartButton.setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/RestartButton.png"))).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH))); // set the icon of the restart button(scaled)
        if (darkTheme)
            restartButton.setBackground(Color.BLACK);
        else
            restartButton.setBackground(Color.gray);
        restartButton.setFocusable(false); // disable focus on the button
        restartButton.setBorderPainted(false); // disable this for easier looking of buttons
        restartButton.setContentAreaFilled(false); // disable this for easier looking of buttons
        restartButton.addActionListener(Chess::restartGame); // add action listener to the button (the restart button)

        if (darkTheme)
            buttonPanel.setBackground(Color.BLACK); // Match your theme
        else
            buttonPanel.setBackground(Color.GRAY); // Match your theme
        buttonPanel.add(restartButton); // add the restart button to the panel
        buttonPanel.add(backButton); // add the back button to the panel

        winnerPanel.add(winnerLabel, BorderLayout.CENTER); // add the winner label to the panel
        winnerPanel.add(buttonPanel, BorderLayout.EAST); // add the button panel to the winner panel

        frame.add(winnerPanel, BorderLayout.SOUTH); // add the winner panel to the window
        frame.setVisible(true); // show the window

    }

    private void setBoardImage(int i) { // set the image of the button and the piece index

        switch (i) {
            case 0:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessBlackRook.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.blackPiecesIndexes[0] = i;
                break;
            case 1:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessBlackKnight.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.blackPiecesIndexes[1] = i;
                break;
            case 2:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessBlackBishop.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.blackPiecesIndexes[2] = i;
                break;
            case 3:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessBlackQueen.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.blackPiecesIndexes[3] = i;
                break;
            case 4:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessBlackKing.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.blackPiecesIndexes[4] = i;
                break;
            case 5:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessBlackBishop.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.blackPiecesIndexes[5] = i;
                break;
            case 6:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessBlackKnight.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.blackPiecesIndexes[6] = i;
                break;
            case 7:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessBlackRook.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.blackPiecesIndexes[7] = i;
                break;
            case 8:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessBlackPawn.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.blackPiecesIndexes[8] = i;
                break;
            case 9:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessBlackPawn.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.blackPiecesIndexes[9] = i;
                break;
            case 10:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessBlackPawn.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.blackPiecesIndexes[10] = i;
                break;
            case 11:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessBlackPawn.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.blackPiecesIndexes[11] = i;
                break;
            case 12:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessBlackPawn.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.blackPiecesIndexes[12] = i;
                break;
            case 13:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessBlackPawn.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.blackPiecesIndexes[13] = i;
                break;
            case 14:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessBlackPawn.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.blackPiecesIndexes[14] = i;
                break;
            case 15:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessBlackPawn.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.blackPiecesIndexes[15] = i;
                break;
            case 48:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessWhitePawn.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.whitePiecesIndexes[8] = i;
                break;
            case 49:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessWhitePawn.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.whitePiecesIndexes[9] = i;
                break;
            case 50:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessWhitePawn.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.whitePiecesIndexes[10] = i;
                break;
            case 51:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessWhitePawn.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.whitePiecesIndexes[11] = i;
                break;
            case 52:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessWhitePawn.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.whitePiecesIndexes[12] = i;
                break;
            case 53:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessWhitePawn.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.whitePiecesIndexes[13] = i;
                break;
            case 54:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessWhitePawn.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.whitePiecesIndexes[14] = i;
                break;
            case 55:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessWhitePawn.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.whitePiecesIndexes[15] = i;
                break;
            case 56:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessWhiteRook.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.whitePiecesIndexes[0] = i;
                break;
            case 57:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessWhiteKnight.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.whitePiecesIndexes[1] = i;
                break;
            case 58:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessWhiteBishop.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.whitePiecesIndexes[2] = i;
                break;
            case 59:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessWhiteQueen.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.whitePiecesIndexes[3] = i;
                break;
            case 60:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessWhiteKing.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.whitePiecesIndexes[4] = i;
                break;
            case 61:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessWhiteBishop.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.whitePiecesIndexes[5] = i;
                break;
            case 62:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessWhiteKnight.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.whitePiecesIndexes[6] = i;
                break;
            case 63:
                buttons[i].setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/ChessWhiteRook.png"))).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                Chess.whitePiecesIndexes[7] = i;
                break;
            }
        }

    }

}