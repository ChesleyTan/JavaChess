import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChessGUI {
    public static JButton[][] buttons = new JButton[8][8];
    public static void main(String[] args) {
        JFrame frame = new JFrame("Testicular");
        frame.setSize(640,640);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(8,8));
        frame.add(panel);
        int i = 0;
        int t = 0;
        Image img;
        Image newimg;
        ImageIcon Wpawn = new ImageIcon("images/Wpawn.jpeg");
        img = Wpawn.getImage();
        newimg = img.getScaledInstance(60,60, java.awt.Image.SCALE_SMOOTH );
        Wpawn = new ImageIcon(newimg);
        ImageIcon Bpawn = new ImageIcon("images/Bpawn.jpeg");
        img = Bpawn.getImage();
        newimg = img.getScaledInstance(60,60, java.awt.Image.SCALE_SMOOTH );
        Bpawn = new ImageIcon(newimg);
        Bpawn = new ImageIcon(newimg);
        ImageIcon Wrook = new ImageIcon("images/Wrook.jpeg");
        img = Wrook.getImage();
        newimg = img.getScaledInstance(60,60, java.awt.Image.SCALE_SMOOTH );
        Wrook = new ImageIcon(newimg);
        ImageIcon Brook = new ImageIcon("images/Brook.jpeg");
        img = Brook.getImage();
        newimg = img.getScaledInstance(60,60, java.awt.Image.SCALE_SMOOTH );
        Brook = new ImageIcon(newimg);
        ImageIcon Wknight = new ImageIcon("images/Wknight.jpeg");
        img = Wknight.getImage();
        newimg = img.getScaledInstance(60,60, java.awt.Image.SCALE_SMOOTH );
        Wknight = new ImageIcon(newimg);
        ImageIcon Bknight = new ImageIcon("images/Bknight.jpeg");
        img = Bknight.getImage();
        newimg = img.getScaledInstance(60,60, java.awt.Image.SCALE_SMOOTH );
        Bknight = new ImageIcon(newimg);
        ImageIcon Wbishop = new ImageIcon("images/Wbishop.jpeg");
        img = Wbishop.getImage();
        newimg = img.getScaledInstance(60,60, java.awt.Image.SCALE_SMOOTH );
        Wbishop = new ImageIcon(newimg);
        ImageIcon Bbishop = new ImageIcon("images/Bbishop.jpeg");
        img = Bbishop.getImage();
        newimg = img.getScaledInstance(60,60, java.awt.Image.SCALE_SMOOTH );
        Bbishop = new ImageIcon(newimg);
        ImageIcon Wqueen = new ImageIcon("images/Wqueen.jpeg");
        img = Wqueen.getImage();
        newimg = img.getScaledInstance(60,60, java.awt.Image.SCALE_SMOOTH );
        Wqueen = new ImageIcon(newimg);
        ImageIcon Bqueen = new ImageIcon("images/Bqueen.jpeg");
        img = Bqueen.getImage();
        newimg = img.getScaledInstance(60,60, java.awt.Image.SCALE_SMOOTH );
        Bqueen = new ImageIcon(newimg);
        ImageIcon Wking = new ImageIcon("images/Wking.jpeg");
        img = Wking.getImage();
        newimg = img.getScaledInstance(60,60, java.awt.Image.SCALE_SMOOTH );
        Wking = new ImageIcon(newimg);
        ImageIcon Bking = new ImageIcon("images/Bking.jpeg");
        img = Bking.getImage();
        newimg = img.getScaledInstance(60,60, java.awt.Image.SCALE_SMOOTH );
        Bking = new ImageIcon(newimg);
        
        
        for (int x = 0; x < 8; x++) {
            if (x == 0) {
                JButton button;
                for (int y = 0; y < 8; y++) {
                    if (y == 0 || y == 7) {
                        button = new JButton (Brook);
                    }
                    else if (y == 1 || y == 6) {
                        button = new JButton (Bknight);
                    }
                    else if (y == 2 || y == 5) {
                        button = new JButton (Bbishop);
                    }   
                    else if (y == 3) {
                        button = new JButton (Bqueen);
                    }   
                    else  {
                        button = new JButton (Bking);
                    }
		    
		    
                                                                                                    
                    if (i == 0) {
                        button.setBackground(Color.gray);
                        i = 1;
                    }
                    else {
                        button.setBackground(Color.black);
                        i = 0;
                    }
                    
                    button.addActionListener(new Action());
		    buttons[x][y] = button;
		    panel.add(button);
                }
                if (t == 0) {
                    i = 1;
                    t = 1;
                }
                else {
                    t = 0;
                    i = 0;
                }
            }
            else if (x == 1 || x == 6) {
                ImageIcon pawn;
                JButton button;
                if (x == 1) {
                    pawn = Bpawn;
                }
                else {
                    pawn = Wpawn;
                }
                button = new JButton (pawn);
                for (int y = 0; y < 8; y++) {
                    if (i == 0) {
                        button.setBackground(Color.gray);
                        i = 1;
                    }
                    else {
                        button.setBackground(Color.black);
                        i = 0;
                    }
		    
		    button.addActionListener(new Action());
                    buttons[x][y] = button;
		    panel.add(button);
                    button = new JButton (pawn);
                }
                if (t == 0) {
                    i = 1;
                    t = 1;
                }
                else {
                    t = 0;
                    i = 0;
                }
            }
            else if (x == 7) {
                JButton button;
                for (int y = 0; y < 8; y++) {
                    if (y == 0 || y == 7) {
                        button = new JButton (Wrook);
                    }
                    else if (y == 1 || y == 6) {
                        button = new JButton (Wknight);
                    }
                    else if (y == 2 || y == 5) {
                        button = new JButton (Wbishop);
                    }   
                    else if (y == 3) {
                        button = new JButton (Wqueen);
                    }   
                    else  {
                        button = new JButton (Wking);
                    }
		    
                                                                                                    
                    if (i == 0) {
                        button.setBackground(Color.gray);
                        i = 1;
                    }
                    else {
                        button.setBackground(Color.black);
                        i = 0;
                    }
		    
		    button.addActionListener(new Action());
		    buttons[x][y] = button;
                    panel.add(button);
                }
                if (t == 0) {
                    i = 1;
                    t = 1;
                }
                else {
                    t = 0;
                    i = 0;
                }
            }
            else {
                JButton button = new JButton ();
                for (int y = 0; y < 8; y++) {
                    if (i == 0) {
                        button.setBackground(Color.gray);
                        i = 1;
                    }
                    else {
                        button.setBackground(Color.black);
                        i = 0;
                    }
		    button.addActionListener(new Action());
		    buttons[x][y] = button;
                    panel.add(button);
                    button = new JButton ();
                }
                if (t == 0) {
                    i = 1;
                    t = 1;
                }
                else {
                    t = 0;
                    i = 0;
                }
            }
        }        
        frame.pack();
        
    }
	static class Action implements ActionListener {
	public void actionPerformed(ActionEvent ae) {
	    JButton bx = (JButton) ae.getSource();
	    for (int i = 0; i < 8; i++) {
		for (int j = 0; j < 8; j++) {
		    if (buttons[i][j] == ae.getSource()) {
			System.out.println(i);
			System.out.println(j);
		    }
		}
	    }
	}
    }
}
