import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class first {

    // Board dimensions
    int boardwidth = 3;
    int boardHeight = 3;

    // Turn tracker (0 = X's turn, 1 = O's turn)
    int turn = 0;

    // UI Components
    JLabel textLabel;
    JFrame frame;
    JPanel boardPanel;
    JButton[][] board;

    // Player names
    String PlayerX = "X";
    String PlayerO = "O";
    String currentplayer;

    // Game state
    boolean gameover = false;

    // New game button
    JButton newGameButton;

    public first() {
        currentplayer = PlayerX;

        // --- Text Label (top) ---
        textLabel = new JLabel();
        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.cyan);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setText(" let's play");
        textLabel.setOpaque(true);

        // --- New Game Button (bottom) ---
        newGameButton = new JButton("wanna play again");
        newGameButton.setVisible(true);
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        // --- Board Panel (center) ---
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(boardHeight, boardwidth));
        boardPanel.setBackground(Color.blue);

        board = new JButton[boardHeight][boardwidth];
        for (int r = 0; r < boardHeight; r++) {
            for (int c = 0; c < boardwidth; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                tile.setBackground(Color.GRAY);
                tile.setForeground(Color.darkGray);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);
                tile.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton tile = (JButton) e.getSource();
                        if (gameover) return;
                        if (!tile.getText().equals("")) return;

                        tile.setText(currentplayer);

                        checkwinner();

                        if (!gameover) {
                            if (currentplayer.equals(PlayerX)) {
                                currentplayer = PlayerO;
                            } else {
                                currentplayer = PlayerX;
                            }
                            textLabel.setText(currentplayer + "'s turn.");
                        }
                    }
                });
                boardPanel.add(tile);
            }
        }

        // --- Frame (main window) ---
        frame = new JFrame("here we go");
        frame.setVisible(true);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);

        frame.add(textPanel, BorderLayout.NORTH);
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(newGameButton, BorderLayout.SOUTH);
    }

    void checkwinner() {
        // Check rows
        for (int r = 0; r < boardHeight; r++) {
            if (!board[r][0].getText().equals("") &&
                board[r][0].getText().equals(board[r][1].getText()) &&
                board[r][1].getText().equals(board[r][2].getText())) {
                setwinner(board[r][0]);
                setwinner(board[r][1]);
                setwinner(board[r][2]);
                return;
            }
        }

        // Check columns
        for (int c = 0; c < boardwidth; c++) {
            if (!board[0][c].getText().equals("") &&
                board[0][c].getText().equals(board[1][c].getText()) &&
                board[1][c].getText().equals(board[2][c].getText())) {
                setwinner(board[0][c]);
                setwinner(board[1][c]);
                setwinner(board[2][c]);
                return;
            }
        }

        // Check diagonal top-left to bottom-right
        if (!board[0][0].getText().equals("") &&
            board[0][0].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][2].getText())) {
            setwinner(board[0][0]);
            setwinner(board[1][1]);
            setwinner(board[2][2]);
            return;
        }

        // Check diagonal top-right to bottom-left
        if (!board[0][2].getText().equals("") &&
            board[0][2].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][0].getText())) {
            setwinner(board[0][2]);
            setwinner(board[1][1]);
            setwinner(board[2][0]);
            return;
        }

        // Check for tie
        boolean allFilled = true;
        for (int r = 0; r < boardHeight; r++) {
            for (int c = 0; c < boardwidth; c++) {
                if (board[r][c].getText().equals("")) {
                    allFilled = false;
                    break;
                }
            }
        }
        if (allFilled) {
            tied(board[0][0]);
        }
    }

    void setwinner(JButton tile) {
        String winner = tile.getText();
        textLabel.setText(winner + " is the Winner");

        if (winner.equals(PlayerX)) {
            textLabel.setForeground(Color.RED);
            for (int r = 0; r < boardHeight; r++) {
                for (int c = 0; c < boardwidth; c++) {
                    board[r][c].setForeground(Color.RED);
                    board[r][c].setBackground(Color.MAGENTA);
                }
            }
        } else {
            textLabel.setForeground(Color.YELLOW);
            for (int r = 0; r < boardHeight; r++) {
                for (int c = 0; c < boardwidth; c++) {
                    board[r][c].setForeground(Color.YELLOW);
                    board[r][c].setBackground(Color.BLUE);
                }
            }
        }
        gameover = true;
    }

    void tied(JButton tile) {
        textLabel.setText("shit  it's a tie");
        textLabel.setForeground(Color.pink);
        for (int r = 0; r < boardHeight; r++) {
            for (int c = 0; c < boardwidth; c++) {
                board[r][c].setForeground(Color.pink);
                board[r][c].setBackground(Color.MAGENTA);
            }
        }
        gameover = true;
    }

    void resetGame() {
        currentplayer = PlayerX;
        gameover = false;
        textLabel.setText(" let's play");
        textLabel.setForeground(Color.cyan);
        textLabel.setBackground(Color.darkGray);

        for (int r = 0; r < boardHeight; r++) {
            for (int c = 0; c < boardwidth; c++) {
                board[r][c].setText("");
                board[r][c].setBackground(Color.GRAY);
                board[r][c].setForeground(Color.darkGray);
            }
        }
    }
}
