/*
Gaem Hub
This is a simple game hub application that allows users to select and play different games
including Tic Tac Toe and Chess. The application features a user-friendly interface with options
to change game difficulty and toggle between light and dark themes

** You can add a game just by adding its name to the ganeName array and adding the action listiner to the button, and do not forget its image in the media folder
Students:
Qusay Ikdam Alhami 2023901127
Jafar 2023901077
abdullah 2021901035
 */
import javax.swing.*; //importing some libraries
import java.awt.*;
import java.util.Objects;

public class Main { // the amin class

    private static final String[] gameName = {"Tic Tac Toe", "Chess"}; // names of the games
    private static final int numOfGames = gameName.length; // number of games

    private static final String[] difficulties = {"Easy", "Medium", "Hard", "Party"}; // difficulty levels
    private static int currentDifficultyIndex = 0; // current difficulty index
    private static boolean partyMode = false; // party mode flag
    private static boolean darkThemeOn = true; // dark theme flag
    private static boolean firstRun = true; // first run flag

    private static JFrame frame; // main frame
    private static JPanel mainPanel; // main panel
    private static JPanel optionsPanel; // options panel
    private static JButton[] games; // array of game buttons
    private static JButton difficultyButton; // difficulty button
    private static JButton themeButton; // theme button
    private static JButton button; // button for game selection

    public static void main(String[] args) { // main method (constructor)

        frame = new JFrame(); // creating a new frame
        mainPanel = new JPanel(); // creating a new panel
        games = new JButton[numOfGames]; // creating a new array of buttons
        optionsPanel = new JPanel(); // creating a new panel for options
        difficultyButton = new JButton(); // difficulty button
        themeButton = new JButton(); // theme button

        if (firstRun) { // if it's the first run, show the message
            JOptionPane.showMessageDialog(null, "Qusay Ikdam Alhami 2023901127\nJafar 2023901077\nabdullah 2021901035"); // show message
            firstRun = false; // set first run to false
        }


        MainUI(); // call the main UI method
        frame.setVisible(true); // set the frame visible
    }

    private static void MainUI() {

        frame.setSize(1000, 800); // set the size of the frame
        frame.setLocationRelativeTo(null); // center the frame on the screen
        frame.setTitle("Game Hub"); // set the title of the frame
        frame.setIconImage(new ImageIcon(Objects.requireNonNull(Main.class.getResource("/media/GameHub.png"))).getImage()); // set the icon of the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set the default close operation (you can close the frame from the panel)
        frame.setLayout(new BorderLayout()); // set the layout of the frame

        mainPanel.setLayout(new GridLayout(0, numOfGames, 20, 20)); // set the layout of the main panel


        for (int i = 0; i < numOfGames; i++) { // loop through the number of games
            games[i] = createGameButton(gameName[i]); // create a new button for each game
            mainPanel.add(games[i]); // add the button to the main panel
        }

        games[0].addActionListener(e -> launchTicTacToe()); // add action listener to the button (so u can run the game)
        games[1].addActionListener(e -> launchChess()); // add action listener to the button (so u can run the game)

        // === Options Panel ===

        optionsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // set the layout of the options panel

        difficultyButton.setContentAreaFilled(false); // set the content area filled to false
        themeButton.setContentAreaFilled(false); // set the content area filled to false

        difficultyButton.addActionListener(e -> cycleDifficulty()); // add action listener to the difficulty button (son u can change the difficulty)
        themeButton.addActionListener(e -> toggleTheme()); // add action listener to the theme button (so u can change the theme)

        optionsPanel.add(difficultyButton); // add the difficulty button to the options panel
        optionsPanel.add(themeButton); // add the theme button to the options panel

        frame.add(mainPanel, BorderLayout.CENTER); // add the main panel to the frame
        frame.add(optionsPanel, BorderLayout.SOUTH); // add the options panel to the frame

        updateTheme(); // update the theme
    }

    private static void launchTicTacToe() {
        frame.dispose(); // closing the main frame
        new TicTacToe(partyMode, currentDifficultyIndex + 1, darkThemeOn); // launch the Tic Tac Toe game
    }

    private static void launchChess() {
        frame.dispose(); // closing the main frame
        new Chess(partyMode, currentDifficultyIndex + 1,darkThemeOn); // launch the Chess game
    }

    private static void cycleDifficulty() {
        currentDifficultyIndex = (currentDifficultyIndex + 1) % difficulties.length; // cycle through the difficulties
        partyMode = difficulties[currentDifficultyIndex].equals("Party"); // if party is selected, set party mode to true
        updateDifficultyButton(); // update the difficulty button (so the pic and the text changes)
    }

    private static void toggleTheme() {
        darkThemeOn = !darkThemeOn; // toggle the theme
        updateTheme(); // update the theme
    }

    private static JButton createGameButton(String name) {
        button = new JButton(); // create a new button
        button.setLayout(new BorderLayout()); // set the layout of the button
        button.setPreferredSize(new Dimension(400, 400)); // set the size of the button
        button.setFocusable(false); // set the button to not be focusable
        button.setBorderPainted(false); // set the border painted to false
        button.setContentAreaFilled(false); // set the content area filled to false

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/media/" + name + "Wallpaper.png")));
        Image scaled = icon.getImage().getScaledInstance(450, 450, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaled)); // create a new label with the image
        iconLabel.setLayout(new BorderLayout()); // set the layout of the label

        JLabel title = new JLabel(name, SwingConstants.CENTER); // create a new label with the name of the game
        title.setFont(new Font("Arial", Font.BOLD, 24)); // set the font of the label
        title.setName(name); // set the name of the label
        iconLabel.add(title, BorderLayout.SOUTH); // add the title label to the icon label

        button.add(iconLabel); // add the icon label to the button

        return button; // return the button
    }

    private static void updateDifficultyButton() {
        String level = difficulties[currentDifficultyIndex]; // get the current difficulty level
        difficultyButton.setText(level); // set the text of the button to the current difficulty level
        difficultyButton.setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(Main.class.getResource("/media/" + level + ".png"))).getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH))); // set the icon of the button to the current difficulty level
        difficultyButton.setFont(new Font("Arial", Font.BOLD, 16)); // set the font of the button
        difficultyButton.setFocusable(false); // set the button to not be focusable
        difficultyButton.setBorderPainted(false); // set the border painted to false
        difficultyButton.setContentAreaFilled(false); // set the content area filled to false
        difficultyButton.setOpaque(true); // set the button to be opaque
    }

    private static void updateTheme() {

        if (darkThemeOn) { // if dark theme is on
            mainPanel.setBackground(Color.BLACK); // set the background color to black
            optionsPanel.setBackground(Color.BLACK); // set the background color to black
            frame.getContentPane().setBackground(Color.BLACK); // set the background color to black

            difficultyButton.setBackground(Color.DARK_GRAY); // set the background color to dark gray
            difficultyButton.setForeground(Color.WHITE); // set the text color to white
            themeButton.setBackground(Color.DARK_GRAY); // set the background color to dark gray
            themeButton.setForeground(Color.WHITE); // set the text color to white
        } else { // if dark theme is off
            mainPanel.setBackground(Color.WHITE); // set the background color to white
            optionsPanel.setBackground(Color.WHITE); // set the background color to white
            frame.getContentPane().setBackground(Color.WHITE); // set the background color to white

            difficultyButton.setBackground(Color.LIGHT_GRAY); // set the background color to light gray
            difficultyButton.setForeground(Color.BLACK); // set the text color to black
            themeButton.setBackground(Color.LIGHT_GRAY); // set the background color to light gray
            themeButton.setForeground(Color.BLACK); // set the text color to black
        }

        for (JButton gameButton : games) { // loop through the game buttons
            if (darkThemeOn) // if dark theme is on
                gameButton.setBackground(Color.BLACK); // Match theme
            else
                gameButton.setBackground(Color.WHITE); // Match theme
            // Update game title text color
            for (Component c : gameButton.getComponents()) { // loop through the components of the button
                if (c instanceof JLabel iconLabel) { // if the component is a label
                    for (Component label : iconLabel.getComponents()) { // loop through the components of the label
                        if (label instanceof JLabel titleLabel) { // if the component is a label
                            if (darkThemeOn) // if dark theme is on
                                titleLabel.setForeground(Color.WHITE); // set the text color to white
                            else
                                titleLabel.setForeground(Color.BLACK); // set the text color to black
                        }
                    }
                }
            }
        }

        //==== Update Theme Button ===
        themeButton.setText(darkThemeOn ? "Dark" : "Light"); // set the text of the button to dark or light
        String iconPath = darkThemeOn ? "/media/moon.png" : "/media/sun.png"; // set the icon path to moon or sun
        themeButton.setIcon(new ImageIcon(new ImageIcon(Objects.requireNonNull(Main.class.getResource(iconPath))).getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH))); // set the icon of the button to moon or sun
        themeButton.setFont(new Font("Arial", Font.BOLD, 16)); // set the font of the button
        themeButton.setFocusable(false); // set the button to not be focusable
        themeButton.setBorderPainted(false); // set the border painted to false
        themeButton.setContentAreaFilled(false); // set the content area filled to false
        themeButton.setOpaque(true); // set the button to be opaque

        //==== Update Game Buttons ===
        updateDifficultyButton(); // update the difficulty button
    }
}