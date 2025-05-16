import java.awt.*; //importing some libararies
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.*;

public class TicTacToe {

    private static boolean party; // true for 2 players, false for player vs cpu
    private static int CpuDifficulty; // 1 for easy, 2 for normal, 3 for hard
    static boolean darkTheme; // true for dark theme, false for light theme
    private static boolean PlayerTurn = true; // true for player 1, false for player 2
    private static boolean GameOver = false; // true if game is over

    private static Random random; // random number generator


    TicTacToe(boolean party, int CpuDifficulty, boolean darkTheme) { // constructor

        this.party = party;
        this.CpuDifficulty = CpuDifficulty;
        this.darkTheme = darkTheme;

        new TicTacToeUI(); // creating the UI

    }

    public static void PrintPlayer(ActionEvent e) { // method to print the player who clicked the button

        for (int r = 0; r < 3; r++) { // cycle through rows
            for (int c = 0; c < 3; c++) { // cycle through columns
                if (e.getSource() == TicTacToeUI.buttons[r][c] && TicTacToeUI.buttons[r][c].getText().isEmpty()) { // if the button is empty
                    if (PlayerTurn) { // if it is player 1's turn
                        TicTacToeUI.buttons[r][c].setForeground(Color.RED); // make the text red
                        TicTacToeUI.buttons[r][c].setText("X"); // set the text to X
                    }
                    else {
                        TicTacToeUI.buttons[r][c].setForeground(Color.BLUE); // make the text blue
                        TicTacToeUI.buttons[r][c].setText("O"); // set the text to O
                    }
                    Changeturn(); // calling Changeturn
                }
            }
        }
    }

    public static void restartGame(ActionEvent e) { // method to restart the game when the restart button is clicked
        PlayerTurn = true; //returning some values to default
        GameOver = false;

        TicTacToeUI.instance.dispose(); // disposing the current window
        new TicTacToe(party, CpuDifficulty, darkTheme); // creating a new window (restarting the game)
    }

    public static void backToMain(ActionEvent e) {
        PlayerTurn = true; //returning some values to default
        GameOver = false;

        TicTacToeUI.instance.dispose(); // disposing the current window
        Main.main(null); // going back to the main menu
    }

    private static void Changeturn() {

        CheckWin(); // checking if someone has won

        if (GameOver) // if the game is over
            return; // exiting the method

        if (party){ // if it is a party game
            if (PlayerTurn) // if it is player 1's turn
                for (int r = 0; r < 3; r++) { // cycle through rows
                    for (int c = 0; c < 3; c++) { // cycle through columns
                        if (TicTacToeUI.buttons[r][c].getText().equals("X")) // if the button is X
                            TicTacToeUI.buttons[r][c].setForeground(Color.RED); // make the text red
                        else if (TicTacToeUI.buttons[r][c].getText().equals("O")) // if the button is O
                            TicTacToeUI.buttons[r][c].setForeground(Color.GREEN); // make the text green
                    }
                }
            else
                for (int r = 0; r < 3; r++) { // cycle through rows
                    for (int c = 0; c < 3; c++) { // cycle through columns
                        if (TicTacToeUI.buttons[r][c].getText().equals("O")) // if the button is O
                            TicTacToeUI.buttons[r][c].setForeground(Color.BLUE); // make the text blue
                        else if (TicTacToeUI.buttons[r][c].getText().equals("X")) // if the button is X
                            TicTacToeUI.buttons[r][c].setForeground(Color.GREEN); // make the text green
                    }

                }
            PlayerTurn = !PlayerTurn; // changing the turn
        }
        else {
            switch (CpuDifficulty) { // checking the difficulty level
                case 1:
                    CpuDifficultyEasy(); // easy difficulty
                    break;
                case 2:
                    CpuDifficultyNormal(); // normal difficulty
                    break;
                case 3:
                    CpuDifficultyHard(); // hard difficulty
                    break;
            }
            CheckWin(); // checking if someone has won
        }

    }

    private static void CpuDifficultyEasy() {

        random = new Random(); // creating a random number generator
        while (true){ // loop until a valid move is found
            int r = random.nextInt(3); // random row
            int c = random.nextInt(3); // random column
            if (TicTacToeUI.buttons[r][c].getText().isEmpty()) { // if the button is empty
                TicTacToeUI.buttons[r][c].setForeground(Color.BLUE); // make the text blue
                TicTacToeUI.buttons[r][c].setText("O"); // set the text to O
                break; // exit the loop
            }
        }

    }

    private static int[] WinOrBlock(String p) {

        int[][][] WinPatterns = { // the wining patterns
                {{0, 0}, {0, 1}, {0, 2}},
                {{1, 0}, {1, 1}, {1, 2}},
                {{2, 0}, {2, 1}, {2, 2}},

                {{0, 0}, {1, 0}, {2, 0}},
                {{0, 1}, {1, 1}, {2, 1}},
                {{0, 2}, {1, 2}, {2, 2}},

                {{0, 0}, {1, 1}, {2, 2}},
                {{0, 2}, {1, 1}, {2, 0}}
        };

        for (int[][] line : WinPatterns) { // cycle through the wining patterns
            String first = TicTacToeUI.buttons[line[0][0]][line[0][1]].getText(); // get the text of the first button
            String second = TicTacToeUI.buttons[line[1][0]][line[1][1]].getText(); // get the text of the second button
            String third = TicTacToeUI.buttons[line[2][0]][line[2][1]].getText(); // get the text of the third button
            if (first.equals(p) && second.equals(p) && third.isEmpty())
                return new int[]{line[2][0], line[2][1]}; // if the first two buttons are equal to p and the third is empty
            if (first.equals(p) && second.isEmpty() && third.equals(p))
                return new int[]{line[1][0], line[1][1]}; // if the first and third buttons are equal to p and the second is empty
            if (first.isEmpty() && second.equals(p) && third.equals(p))
                return new int[]{line[0][0], line[0][1]}; // if the second and third buttons are equal to p and the first is empty
        }
        return null; // if no valid move is found
    }

    private static void CpuDifficultyNormal() {

        int[] move = WinOrBlock("O"); // check if cpu can win

        if (move == null) // if cpu can't win
            move = WinOrBlock("X"); // check if player can win
        if (move != null) { // if player can win
            TicTacToeUI.buttons[move[0]][move[1]].setForeground(Color.BLUE); // make the text blue
            TicTacToeUI.buttons[move[0]][move[1]].setText("O"); // set the text to O
        }

        else
            CpuDifficultyEasy(); // if cpu can't win and player can't win, make a random move

    }

    private static int minimax(boolean IsMaximizing) { // minimax algorithm
        String winner = GetMove(); // check if someone has won
        if (winner != null) { // if someone has won
            if (winner.equals("O")) return 10; // if cpu won
            if (winner.equals("X")) return -10; // if player won
            if (winner.equals("draw")) return 0; // if draw
        }

        int best = IsMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE; // set best to min or max value

        for (int r = 0; r < 3; r++) { // cycle through rows
            for (int c = 0; c < 3; c++) { // cycle through columns
                if (TicTacToeUI.buttons[r][c].getText().isEmpty()) { // if the button is empty
                    TicTacToeUI.buttons[r][c].setText(IsMaximizing ? "O" : "X"); // set the text to O or X
                    int score = minimax(!IsMaximizing); // call minimax recursively
                    TicTacToeUI.buttons[r][c].setText(""); // reset the button

                    if (IsMaximizing) // if it is maximizing
                        best = Math.max(best, score); // set best to max value
                    else
                        best = Math.min(best, score); // set best to min value
                }
            }
        }

        return best; // return the best value
    }

    private static String GetMove() {
        int[][][] lines = { // the wining patterns
                {{0, 0}, {0, 1}, {0, 2}},
                {{1, 0}, {1, 1}, {1, 2}},
                {{2, 0}, {2, 1}, {2, 2}},

                {{0, 0}, {1, 0}, {2, 0}},
                {{0, 1}, {1, 1}, {2, 1}},
                {{0, 2}, {1, 2}, {2, 2}},

                {{0, 0}, {1, 1}, {2, 2}},
                {{0, 2}, {1, 1}, {2, 0}}
        };

        for (int[][] line : lines) { // cycle through the wining patterns
            String a = TicTacToeUI.buttons[line[0][0]][line[0][1]].getText(); // get the text of the first button
            String b = TicTacToeUI.buttons[line[1][0]][line[1][1]].getText(); // get the text of the second button
            String c = TicTacToeUI.buttons[line[2][0]][line[2][1]].getText(); // get the text of the third button
            if (!a.isEmpty() && a.equals(b) && a.equals(c)) // if all three buttons are equal
                return a; // return the text of the button
        }

        for (int r = 0; r < 3; r++) // cycle through rows
            for (int c = 0; c < 3; c++) // cycle through columns
                if (TicTacToeUI.buttons[r][c].getText().isEmpty()) // if the button is empty
                    return null; // return null

        return "draw"; // if no empty buttons are found, return draw
    }

    private static void CpuDifficultyHard() { // hard difficulty
        int bestScore = Integer.MIN_VALUE; // set best score to min value
        int bestRow = -1; // set best row to -1
        int bestCol = -1; // set best column to -1

        for (int r = 0; r < 3; r++) { // cycle through rows
            for (int c = 0; c < 3; c++) { // cycle through columns
                if (TicTacToeUI.buttons[r][c].getText().isEmpty()) { // if the button is empty
                    TicTacToeUI.buttons[r][c].setText("O"); // set the text to O
                    int score = minimax(false); // call minimax recursively
                    TicTacToeUI.buttons[r][c].setText(""); // reset the button
                    if (score > bestScore) { // if score is greater than best score
                        bestScore = score; // set best score to score
                        bestRow = r; // set best row to r
                        bestCol = c; // set best column to c
                    }
                }
            }
        }

        if (bestRow != -1 && bestCol != -1) { // if a valid move is found
            TicTacToeUI.buttons[bestRow][bestCol].setForeground(Color.BLUE); // make the text blue
            TicTacToeUI.buttons[bestRow][bestCol].setText("O"); // set the text to O
        }

    }


    private static void CheckWin() {

        int[][][] WinPatterns = { // the wining patterns
                {{0, 0}, {0, 1}, {0, 2}},
                {{1, 0}, {1, 1}, {1, 2}},
                {{2, 0}, {2, 1}, {2, 2}},

                {{0, 0}, {1, 0}, {2, 0}},
                {{0, 1}, {1, 1}, {2, 1}},
                {{0, 2}, {1, 2}, {2, 2}},

                {{0, 0}, {1, 1}, {2, 2}},
                {{0, 2}, {1, 1}, {2, 0}}
        };

        for (int[][] line : WinPatterns) { // cycle through the wining patterns
            String first = TicTacToeUI.buttons[line[0][0]][line[0][1]].getText(); // get the text of the first button
            String second = TicTacToeUI.buttons[line[1][0]][line[1][1]].getText(); // get the text of the second button
            String third = TicTacToeUI.buttons[line[2][0]][line[2][1]].getText(); // get the text of the third button

            if (!first.isEmpty() && first.equals(second) && first.equals(third)){ // if all three buttons are equal
                for (int[] l : line) // cycle through the line
                    TicTacToeUI.buttons[l[0]][l[1]].setBackground(Color.WHITE); // set the background to white
                for (int r = 0; r < 3; r++) // cycle through rows
                    for (int c = 0; c < 3; c++) // cycle through columns
                        TicTacToeUI.buttons[r][c].setEnabled(false); // disable all buttons
                PrintWinner(first,false); // print the winner
                return; // exit the method
            }
        }

        for (int r = 0; r < 3; r++) // cycle through rows
            for (int c = 0; c < 3; c++) // cycle through columns
                if (TicTacToeUI.buttons[r][c].getText().isEmpty()) // if the button is empty
                    return; // exit the method
        for (int r = 0; r < 3; r++) // cycle through rows
            for (int c = 0; c < 3; c++) // cycle through columns
                TicTacToeUI.buttons[r][c].setEnabled(false); // disable all buttons
        PrintWinner("",true); // print draw
    }

    private static void PrintWinner(String winner, boolean draw) { // method to print the winner
        GameOver = true; // set game over to true

        if (draw) { // if draw
            TicTacToeUI.winnerLabel.setText(" Thats a draw ! !"); // set the text to draw
            TicTacToeUI.winnerLabel.setVisible(true); // make the text visible
            TicTacToeUI.winnerPanel.setVisible(true); // make the panel visible
            return; // exit the method
        }
        if (party) // if it is a party game
            TicTacToeUI.winnerLabel.setText(winner.equals("X") ? " First Player Won !!" : " Second Player Won !!"); // set the text to player 1 or player 2
        else
            TicTacToeUI.winnerLabel.setText(winner.equals("X") ? " You won !!" : " Better luck next time !!"); // set the text to you or better luck next time

        TicTacToeUI.winnerPanel.setVisible(true); // make the panel visible

    }

}

class TicTacToeUI extends JFrame { // class for the UI (uses inheritance from JFrame)

    static JFrame instance; // instance of the class for the frame so i can call it from other classes
    private JPanel board; // panel for the board
    static JButton[][] buttons; // buttons for the board
    static JPanel winnerPanel; // panel for the winner
    static JLabel winnerLabel; // label for the winner
    private JButton restartButton; // button to restart the game
    private JButton backButton; // button to go back to main menu
    private JPanel buttonPanel; // panel for the buttons

    TicTacToeUI() { // constructor for the UI

        board = new JPanel(); // creating the panel
        buttons = new JButton[3][3]; // creating the buttons
        winnerPanel = new JPanel(); // creating the panel for the winner
        winnerLabel = new JLabel(); // creating the label for the winner
        restartButton = new JButton(); // creating the button to restart the game
        backButton = new JButton(); // creating the button to go back to main menu
        buttonPanel = new JPanel(new FlowLayout()); // creating the panel for the buttons
        instance = this; // setting the instance to this class

        window(); // calling the window method to create the window

    }

    private void window() { // method to create the window

        this.setSize(1000, 1000); // setting the size of the window
        this.setLocationRelativeTo(null); // setting the location of the window to center
        this.setTitle("Tic Tac Toe"); // setting the title of the window

        this.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/TicTacToeIcon.png"))).getImage()); // setting the icon of the window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // setting the default close operation


        board.setLayout(new GridLayout(3, 3)); // setting the layout of the panel to grid layout

        this.add(board); // adding the panel to the window (frame)

        for (int r = 0; r < 3; r++) // cycle through rows
            for (int c = 0; c < 3; c++) { // cycle through columns
                buttons[r][c] = new JButton(); // creating the button
                board.add(buttons[r][c]); // adding the button to the panel
                if (TicTacToe.darkTheme) // if dark theme is selected
                    buttons[r][c].setBackground(Color.BLACK); // set the background to black
                else // if light theme is selected
                    buttons[r][c].setBackground(Color.WHITE); // set the background to white
                buttons[r][c].setFont(new Font("Arial", Font.BOLD, 200)); // setting the font of the button
                buttons[r][c].setFocusable(false); // making the button not focusable
                buttons[r][c].addActionListener(TicTacToe::PrintPlayer); // adding action listener to the button
            }

        winnerPanel.setLayout(new BorderLayout()); // setting the layout of the panel to border layout
        if (TicTacToe.darkTheme) // if dark theme is selected
            winnerPanel.setBackground(Color.BLACK); // set the background to black
        else
            winnerPanel.setBackground(Color.WHITE); // set the background to white
        winnerPanel.setVisible(false); // making the panel invisible

        winnerLabel.setFont(new Font("Arial", Font.BOLD, 25)); // setting the font of the label
        if (TicTacToe.darkTheme) // if dark theme is selected
            winnerLabel.setForeground(Color.WHITE); // set the text color to white
        else
            winnerLabel.setForeground(Color.BLACK); // set the text color to black

        backButton.setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/BackButton.png"))).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH))); // setting the icon of the button(scaled)
        if (TicTacToe.darkTheme)
            backButton.setBackground(Color.BLACK); // Match your theme
        else
            backButton.setBackground(Color.GRAY); // Match your theme
        backButton.setFocusable(false); // making the button not focusable
        backButton.setBorderPainted(false); // removing the border
        backButton.setContentAreaFilled(false); // removing the background
        backButton.addActionListener(TicTacToe::backToMain); // adding action listener to the button (back to main menu)

        restartButton.setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/media/RestartButton.png"))).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH))); // setting the icon of the button(scaled)
        if (TicTacToe.darkTheme) // if dark theme is selected
            restartButton.setBackground(Color.BLACK); // Match your theme
        else
            restartButton.setBackground(Color.GRAY); // Match your theme
        restartButton.setFocusable(false); // making the button not focusable
        restartButton.setBorderPainted(false); // removing the border
        restartButton.setContentAreaFilled(false); // removing the background
        restartButton.addActionListener(TicTacToe::restartGame); // adding action listener to the button (restart game)

        if (TicTacToe.darkTheme)
            buttonPanel.setBackground(Color.BLACK); // Match your theme
        else
            buttonPanel.setBackground(Color.GRAY); // Match your theme
        buttonPanel.add(restartButton); // adding the button to the panel
        buttonPanel.add(backButton); // adding the button to the panel

        winnerPanel.add(winnerLabel, BorderLayout.CENTER); // adding the label to the panel
        winnerPanel.add(buttonPanel, BorderLayout.EAST); // adding the panel to the panel

        this.add(winnerPanel, BorderLayout.SOUTH); // adding the panel to the window (frame)
        this.setVisible(true); // making the window visible

    }
}

class justForPolymorphism extends JFrame { // class to demonstrate polymorphism
    // This class is just to demonstrate polymorphism
    // It doesn't do anything
}