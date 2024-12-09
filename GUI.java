import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {
    private Game game;
    private JPanel mazePanel;
    private JLabel hpLabel;
    private JLabel staminaLabel;
    private JLabel healthPotionLabel;
    private JLabel staminaPotionLabel;
    private boolean[][] visited;

    public GUI(Game game) {
        this.game = game;
        int rows = game.getLabirin().getMaze().length;
        int cols = game.getLabirin().getMaze()[0].length;
        visited = new boolean[rows][cols];
        initComponents();
    }

    private void initComponents() {
        setTitle("Maze Game");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        showLevelSelectionScreen(); // Show level selection first
    }

    private void showLevelSelectionScreen() {
        JPanel levelPanel = new JPanel();
        levelPanel.setLayout(new BoxLayout(levelPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Select Level", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        levelPanel.add(title);

        for (int i = 1; i <= 5; i++) {
            int level = i;
            JButton levelButton = new JButton("Level " + i);
            levelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            levelButton.addActionListener(e -> startGame(level));
            levelPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            levelPanel.add(levelButton);
        }

        getContentPane().removeAll();
        getContentPane().add(levelPanel);
        revalidate();
        repaint();
    }

    private void startGame(int level) {
        this.game = new Game(level);
        int rows = game.getLabirin().getMaze().length;
        int cols = game.getLabirin().getMaze()[0].length;
        visited = new boolean[rows][cols];

        mazePanel = new JPanel(new GridLayout(10, 10));
        hpLabel = new JLabel("HP: " + game.getCharacter().getHP() + " / 50");
        staminaLabel = new JLabel("Stamina: " + game.getCharacter().getStamina() + " / 50");
        healthPotionLabel = new JLabel("Health Potions: " + game.getCharacter().getInventory().getHealthPotionCount());
        staminaPotionLabel = new JLabel("Stamina Potions: " + game.getCharacter().getInventory().getStaminaPotionCount());

        updateMaze();

        JPanel infoPanel = new JPanel(new GridLayout(2, 2));
        infoPanel.add(hpLabel);
        infoPanel.add(staminaLabel);

        JPanel potionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton useHealthPotionButton = new JButton("Use Health Potion");
        JButton useStaminaPotionButton = new JButton("Use Stamina Potion");

        useHealthPotionButton.setPreferredSize(new Dimension(150, 20)); // Change button size
        useStaminaPotionButton.setPreferredSize(new Dimension(150, 20)); // Change button size

        useHealthPotionButton.addActionListener(e -> useHealthPotion());
        useStaminaPotionButton.addActionListener(e -> useStaminaPotion());

        potionPanel.add(healthPotionLabel);
        potionPanel.add(useHealthPotionButton);
        potionPanel.add(staminaPotionLabel);
        potionPanel.add(useStaminaPotionButton);

        // Add Help button
        JPanel helpPanel = new JPanel();
        helpPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton helpButton = new JButton("Help");
        helpButton.setPreferredSize(new Dimension(100, 20));
        helpButton.addActionListener(e -> showHelpDialog());
        helpPanel.add(helpButton);

        JPanel combinedPanel = new JPanel();
        combinedPanel.setLayout(new BoxLayout(combinedPanel, BoxLayout.Y_AXIS));
        combinedPanel.add(helpPanel);
        combinedPanel.add(infoPanel);
        combinedPanel.add(potionPanel);

        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mazePanel, BorderLayout.CENTER);
        getContentPane().add(combinedPanel, BorderLayout.NORTH);

        setupKeyBindings();

        revalidate();
        repaint();
    }

    // Method to show Help dialog
    private void showHelpDialog() {
        String helpMessage = "Game Controls:\n\n" +
            "1. Use arrow keys or W, A, S, D to move.\n" +
            "   - W / ↑: Move up\n" +
            "   - S / ↓: Move down\n" +
            "   - A / ←: Move left\n" +
            "   - D / →: Move right\n" +
            "2. Press '1' to use Health Potion.\n" +
            "3. Press '2' to use Stamina Potion.";

        JOptionPane.showMessageDialog(this, helpMessage, "Control Instructions", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateMaze() {
        mazePanel.removeAll();
        int[][] maze = game.getLabirin().getMaze();
        Character character = game.getCharacter();

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                JLabel cell = new JLabel();
                cell.setHorizontalAlignment(SwingConstants.CENTER);
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cell.setOpaque(true);

                if (i == character.getX() && j == character.getY()) {
                    cell.setText("C");
                    cell.setBackground(Color.BLUE);

                    if (!visited[i][j]) {
                        showTrapOrChestDialog(maze[i][j], i, j);
                    }

                    visited[i][j] = true;
                } else if (maze[i][j] == 1) {
                    // Wall
                    cell.setBackground(Color.BLACK);
                } else if (maze[i][j] == 2) {
                    // Goal
                    cell.setBackground(Color.GREEN);
                } else if (maze[i][j] == 3) {
                    // TrapBomb
                    cell.setBackground(visited[i][j] ? Color.RED : Color.WHITE);
                } else if (maze[i][j] == 4) {
                    // TrapMouse
                    cell.setBackground(visited[i][j] ? Color.RED : Color.WHITE);
                } else if (maze[i][j] == 5) {
                    // TrapArrow
                    cell.setBackground(visited[i][j] ? Color.RED : Color.WHITE);
                } else if (maze[i][j] == 6) {
                    // Chest
                    cell.setBackground(visited[i][j] ? Color.YELLOW : Color.WHITE);
                } else {
                    // Path
                    cell.setBackground(visited[i][j] ? Color.LIGHT_GRAY : Color.WHITE);
                }

                mazePanel.add(cell);
            }
        }

        updateStatus();
        mazePanel.revalidate();
        mazePanel.repaint();
    }

    private void showTrapOrChestDialog(int cellType, int x, int y) {
        String message = null;
        if (cellType == 3) {
            new TrapBomb().activate(game.getCharacter());
            message = "You hit a TrapBomb! HP reduced by 20.";
        } else if (cellType == 4) {
            new TrapMouse().activate(game.getCharacter());
            message = "You hit a TrapMouse! HP reduced by 10.";
        } else if (cellType == 5) {
            new TrapArrow().activate(game.getCharacter());
            message = "You hit a TrapArrow! HP reduced by 15.";
        } else if (cellType == 6) {
            Chest chest = new Chest();
            chest.open(game.getCharacter().getInventory());
            message = chest.containsHealthPotion
                    ? "You opened a chest! Got 1 Health Potion."
                    : "You opened a chest! Got 1 Stamina Potion.";
        }

        if (message != null) {
            JOptionPane optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
            JDialog dialog = optionPane.createDialog(this, "Info");
            dialog.setModal(true);

            InputMap dialogInputMap = dialog.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap dialogActionMap = dialog.getRootPane().getActionMap();
            dialogInputMap.put(KeyStroke.getKeyStroke("ENTER"), "closeDialog");
            dialogActionMap.put("closeDialog", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });

            dialog.setVisible(true);
        }
    }

    private void updateStatus() {
        hpLabel.setText("HP: " + game.getCharacter().getHP() + " / 50");
        staminaLabel.setText("Stamina: " + game.getCharacter().getStamina() + " / 50");
        healthPotionLabel.setText("Health Potions: " + game.getCharacter().getInventory().getHealthPotionCount());
        staminaPotionLabel.setText("Stamina Potions: " + game.getCharacter().getInventory().getStaminaPotionCount());

        if (!game.getCharacter().isAlive()) {
            showGameOver("Game Over! You ran out of HP.");
        } else if (game.getCharacter().isExhausted()) {
            showGameOver("Game Over! You ran out of Stamina.");
        } else if (game.isFinished()) {
            showCongratulations();
        }
    }

    private void useHealthPotion() {
        if (game.getCharacter().getInventory().getHealthPotionCount() > 0) {
            game.getCharacter().getInventory().useHealthPotion(game.getCharacter());
            updateStatus();
            JOptionPane.showMessageDialog(this, "Health Potion used! HP restored.");
        } else {
            JOptionPane.showMessageDialog(this, "You don't have any Health Potions.");
        }
    }

    private void useStaminaPotion() {
        if (game.getCharacter().getInventory().getStaminaPotionCount() > 0) {
            game.getCharacter().getInventory().useStaminaPotion(game.getCharacter());
            updateStatus();
            JOptionPane.showMessageDialog(this, "Stamina Potion used! Stamina restored.");
        } else {
            JOptionPane.showMessageDialog(this, "You don't have any Stamina Potions.");
        }
    }

    private void showGameOver(String message) {
        int option = JOptionPane.showOptionDialog(this, message,
                "Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, new Object[]{"Play Again", "Exit"}, "Play Again");

        if (option == 0) {
            startGame(game.getLevel());
        } else {
            showLevelSelectionScreen();
        }
    }

    private void showCongratulations() {
        int nextLevel = game.getLevel() + 1;
        int option = JOptionPane.showOptionDialog(this, "Congratulations! You've completed level " + game.getLevel(),
                "Level Complete", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, new Object[]{"Play Again", nextLevel <= 5 ? "Next Level" : "Exit"}, "Play Again");

        if (option == 0) {
            startGame(game.getLevel());
        } else if (option == 1 && nextLevel <= 5) {
            startGame(nextLevel);
        } else {
            showLevelSelectionScreen();
        }
    }

    private void setupKeyBindings() {
        JPanel inputPanel = new JPanel();
        InputMap inputMap = inputPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = inputPanel.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("UP"), "moveUp");
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");

        inputMap.put(KeyStroke.getKeyStroke("W"), "moveUp");
        inputMap.put(KeyStroke.getKeyStroke("S"), "moveDown");
        inputMap.put(KeyStroke.getKeyStroke("A"), "moveLeft");
        inputMap.put(KeyStroke.getKeyStroke("D"), "moveRight");

        actionMap.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.moveCharacter(-1, 0)) updateMaze();
            }
        });
        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.moveCharacter(1, 0)) updateMaze();
            }
        });
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.moveCharacter(0, -1)) updateMaze();
            }
        });
        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.moveCharacter(0, 1)) updateMaze();
            }
        });

        // Key bindings for using potions
        inputMap.put(KeyStroke.getKeyStroke("1"), "useHealthPotion");
        inputMap.put(KeyStroke.getKeyStroke("2"), "useStaminaPotion");

        actionMap.put("useHealthPotion", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                useHealthPotion();
            }
        });
        actionMap.put("useStaminaPotion", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                useStaminaPotion();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("H"), "showHelp");
        actionMap.put("showHelp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHelpDialog();
            }
        });

        getContentPane().add(inputPanel, BorderLayout.SOUTH);
    }
}