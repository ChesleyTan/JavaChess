package JavaChess;

import javax.swing.*;
import java.io.*;
import java.awt.*;

public class Console extends JTextArea {

    static JTextArea console = new JTextArea(500, 300);
    static JFrame frame = new JFrame("Console");
    static Font consoleDefaultFont = new Font(Font.MONOSPACED, Font.BOLD, 14);
    static Font consoleCustomFont;

    public static void main(String[] args) {
        start();
    }

    public static Font loadFont() {
        try {
            InputStream inStream = new BufferedInputStream(new FileInputStream("JavaChess/resources/UbuntuMono-B.ttf"));
            consoleCustomFont = Font.createFont(Font.TRUETYPE_FONT, inStream).deriveFont(Font.BOLD, 18);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(consoleCustomFont);
            return consoleCustomFont;
        } catch (Exception e) {
            System.err.print("Font file not found: " + e.getMessage());
            return consoleDefaultFont;
        }
    }

    public static void start() {
        frame.setSize(500, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        console.setFont(loadFont());
        frame.add(console);
        redirectSystemStreams();
    }

    private static void updateConsole(final String text) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                console.append(text);
            }
        });
    }

    private static void flushConsole() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                console.setText("");
            }
        });
    }

    private static void redirectSystemStreams() {

        OutputStream out = new OutputStream() {

            @Override
            public void write(int b) throws IOException {
                updateConsole(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                updateConsole(new String(b, off, len));
            }

            @Override
            public void write(byte[] b) throws IOException {
                write(b, 0, b.length);
            }
        };

        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(out));
    }
}
