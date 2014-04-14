package JavaChess;
import JavaChess.pieces.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;
public class GUI {
    static ImageIcon wPawn, bPawn, wRook, bRook, wKnight, bKnight, wBishop, bBishop, wQueen, bQueen, wKing, bKing;
    static JFrame frame;
    static JPanel panel;
    static JButton[][] buttons = new JButton[8][8];
    static Semaphore semaphore = new Semaphore(0);
    static Chess chess = new Chess();
    static boolean firstChoice = false, secondChoice = false;
    static int myXCoor = 0, myYCoor = 0, targXCoor = 0, targYCoor = 0;
    public static void init() {
        frame = new JFrame("Chessweasel");
        frame.setSize(640, 640);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel(new GridLayout(8,8));
        frame.add(panel);
        wPawn = new ImageIcon((new ImageIcon("images/Wpawn.jpeg")).getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH));
        bPawn = new ImageIcon((new ImageIcon("images/Bpawn.jpeg")).getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH));
        wRook = new ImageIcon((new ImageIcon("images/Wrook.jpeg")).getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH));
        bRook = new ImageIcon((new ImageIcon("images/Brook.jpeg")).getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH));
        wKnight = new ImageIcon((new ImageIcon("images/Wknight.jpeg")).getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH));
        bKnight = new ImageIcon((new ImageIcon("images/Bknight.jpeg")).getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH));
        wBishop = new ImageIcon((new ImageIcon("images/Wbishop.jpeg")).getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH));
        bBishop = new ImageIcon((new ImageIcon("images/Bbishop.jpeg")).getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH));
        wQueen = new ImageIcon((new ImageIcon("images/Wqueen.jpeg")).getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH));
        bQueen = new ImageIcon((new ImageIcon("images/Bqueen.jpeg")).getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH));
        wKing = new ImageIcon((new ImageIcon("images/Wking.jpeg")).getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH));
        bKing = new ImageIcon((new ImageIcon("images/Bking.jpeg")).getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH));
    }
    public static void update(String userColor) {
        JButton button = null;
        ChessPiece[][] board = chess.getBoard().getBoard();

        int iStart = 0;
        int iEnd = 8;
        int iStep = 1;
        int uStart = 0;
        int uEnd = 8;
        int uStep = 1;
        if (userColor.equals("B")) {
            iStart = 7;
            iEnd = -1;
            iStep = -1;
            uStart = 7;
            uEnd = -1;
            uStep = -1;
        }

        for (int i = iStart;i != iEnd;i += iStep) {
            for (int u = uStart;u != uEnd;u += uStep) {
                ChessPiece c = board[i][u];
                if (c != null) {
                    if (c.getType().equals("PAWN")) {
                        if (c.getColor().equals("W")) {
                            button = new JButton(wPawn);
                            button.setBackground(Color.gray);
                        }
                        else {
                            button = new JButton(bPawn);
                            button.setBackground(Color.gray);
                        }
                    }
                    else if (c.getType().equals("ROOK")) {
                        if (c.getColor().equals("W")) {
                            button = new JButton(wRook);
                            button.setBackground(Color.gray);
                        }
                        else {
                            button = new JButton(bRook);
                            button.setBackground(Color.gray);
                        }
                    }
                    else if (c.getType().equals("KNIGHT")) {
                        if (c.getColor().equals("W")) {
                            button = new JButton(wKnight);
                            button.setBackground(Color.gray);
                        }
                        else {
                            button = new JButton(bKnight);
                            button.setBackground(Color.gray);
                        }
                    }
                    else if (c.getType().equals("BISHOP")) {
                        if (c.getColor().equals("W")) {
                            button = new JButton(wBishop);
                            button.setBackground(Color.gray);
                        }
                        else {
                            button = new JButton(bBishop);
                            button.setBackground(Color.gray);
                        }
                    }
                    else if (c.getType().equals("QUEEN")) {
                        if (c.getColor().equals("W")) {
                            button = new JButton(wQueen);
                            button.setBackground(Color.gray);
                        }
                        else {
                            button = new JButton(bQueen);
                            button.setBackground(Color.gray);
                        }
                    }
                    else if (c.getType().equals("KING")) {
                        if (c.getColor().equals("W")) {
                            button = new JButton(wKing);
                            button.setBackground(Color.gray);
                        }
                        else {
                            button = new JButton(bKing);
                            button.setBackground(Color.gray);
                        }
                    }
                    else {
                        button = new JButton();
                        button.setBackground(Color.gray);
                    }
                    button.addActionListener(new ClickAction());
                    panel.add(button);
                }
                else {
                    button = new JButton();
                    button.setBackground(Color.gray);
                    button.addActionListener(new ClickAction());
                    panel.add(button);
                }
                buttons[i][u] = button;
            }
        }
        frame.pack();
    }
    public static void main(String[] args) {
        init();
        update(chess.getUserColor());
        while (!chess.isCheckmate()) {
            try {
            semaphore.acquire();
            semaphore.acquire();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
            firstChoice = false;
            secondChoice = false;
            chess.gfxPlayRound(myXCoor, myYCoor, targXCoor, targYCoor);
            panel.removeAll();
            update(chess.getUserColor());
            panel.updateUI();
        }
    }
    static class ClickAction implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            JButton bx = (JButton) ae.getSource();
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    if (buttons[y][x] == ae.getSource()) {
                        if (!firstChoice) {
                            if (chess.getBoard().get(x, y) == null || !chess.getUserColor().equals(chess.getBoard().get(x, y).getColor())) {
                                return;
                            }
                            myXCoor = x;
                            myYCoor = y;
                            firstChoice = true;
                        }
                        else if (!secondChoice) {
                            targXCoor = x;
                            targYCoor = y;
                            secondChoice = true;
                        }
                        System.out.println(x);
                        System.out.println(y);
                    }
                }
            }
            semaphore.release();
        }
    }
}
