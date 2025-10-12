import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class WhacAMole {
    int boardWidth = 550;
    int boardHeight = 600;
    
    // --- New Variables for Restart Button ---
    JButton restartButton = new JButton("Restart Game");
    JPanel controlPanel = new JPanel(); // Panel for the button at the bottom

    JFrame frame = new JFrame("Mario: Whac A Mole");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    
    JButton[] board = new JButton[9];
    ImageIcon moleIcon;
    ImageIcon plantIcon;

    JButton currMoleTile;
    // --- CHANGE: Use ArrayList to store multiple plant tiles ---
    ArrayList<JButton> piranhaPlants = new ArrayList<>();

    Random random = new Random();
    Timer setMoleTimer;
    Timer setPlantTimer;
    int score = 0;
    
    // --- New variable to control game state ---
    boolean gameOver = false; 

    WhacAMole() {
        frame.setSize(boardWidth, boardHeight + 50); // Increased height for button
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // --- Text Panel Setup ---
        textLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Score: " + Integer.toString(score));
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        // --- Board Panel Setup ---
        boardPanel.setLayout(new GridLayout(3, 3));
        frame.add(boardPanel, BorderLayout.CENTER);

        // --- Control Panel (for Restart Button) Setup ---
        controlPanel.setLayout(new FlowLayout());
        restartButton.setFont(new Font("Arial", Font.BOLD, 20));
        controlPanel.add(restartButton);
        frame.add(controlPanel, BorderLayout.SOUTH);
        
        // --- Restart Button Listener ---
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });

        // --- Load and Scale Icons ---
        Image plantImg = new ImageIcon(getClass().getResource("./piranha.png")).getImage();
        plantIcon = new ImageIcon(plantImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

        Image moleImg = new ImageIcon(getClass().getResource("./monty.png")).getImage();
        moleIcon = new ImageIcon(moleImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));
        
        // --- Board Tile Setup ---
        for (int i = 0; i < 9; i++) {
            JButton tile = new JButton();
            board[i] = tile;
            boardPanel.add(tile);
            tile.setFocusable(false);
            
            // --- UPDATED Action Listener Logic ---
            tile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (gameOver) return; // Ignore clicks if game is over
                    
                    JButton tile = (JButton) e.getSource();
                    
                    if (tile == currMoleTile) {
                        score += 10;
                        textLabel.setText("Score: " + Integer.toString(score));
                    }
                    // --- CHANGE: Check if the tile is ANY piranha plant ---
                    else if (piranhaPlants.contains(tile)) {
                        textLabel.setText("Game Over! Score: " + Integer.toString(score));
                        gameOver = true;
                        setMoleTimer.stop();
                        setPlantTimer.stop();
                        for (int i = 0; i < 9; i++) {
                            board[i].setEnabled(false);
                        }
                    }
                }
            });
        }

        // --- Mole Timer ---
        setMoleTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (gameOver) return;
                
                if (currMoleTile != null) {
                    currMoleTile.setIcon(null);
                    currMoleTile = null;
                }

                int num = random.nextInt(9);
                JButton tile = board[num];

                // Skip if tile is occupied by ANY plant
                if (piranhaPlants.contains(tile)) return; 

                currMoleTile = tile;
                currMoleTile.setIcon(moleIcon);
            }
        });

        // --- UPDATED Plant Timer ---
        setPlantTimer = new Timer(1500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (gameOver) return;
                
                // 1. Remove one random existing plant (optional: makes the game dynamic)
                if (!piranhaPlants.isEmpty()) {
                    int removeIndex = random.nextInt(piranhaPlants.size());
                    JButton removedTile = piranhaPlants.remove(removeIndex);
                    removedTile.setIcon(null);
                }

                // 2. Add a new plant
                int num = random.nextInt(9);
                JButton tile = board[num];

                // Skip if tile is already a Mole OR an existing Piranha Plant
                if (currMoleTile == tile || piranhaPlants.contains(tile)) {
                    return;
                }

                // **Add the new plant tile to the ArrayList**
                piranhaPlants.add(tile);
                tile.setIcon(plantIcon);
            }
        });

        setMoleTimer.start();
        setPlantTimer.start();
        frame.setVisible(true);
    }
    
    private void restartGame() {
        // 1. Stop Timers
        setMoleTimer.stop();
        setPlantTimer.stop();

        // 2. Reset Game State
        gameOver = false;
        score = 0;
        textLabel.setText("Score: 0");

        // 3. Clear and Reset Board Tiles
        for (int i = 0; i < 9; i++) {
            board[i].setIcon(null);
            board[i].setEnabled(true);
        }

        // 4. Clear Mole and Plant Variables
        currMoleTile = null;
        piranhaPlants.clear(); // Important: Clear the list of plants

        // 5. Restart Timers
        setMoleTimer.start();
        setPlantTimer.start();
        System.out.println("Game Restarted!");
    }

    public static void main(String[] args) throws Exception {
       new WhacAMole();
    }
}