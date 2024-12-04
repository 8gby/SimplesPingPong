import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class SimplePingPong extends JPanel implements KeyListener {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ping Pong Simples");
        SimplePingPong game = new SimplePingPong();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private int ballX = 250, ballY = 150;
    private int ballDX = 2, ballDY = 2;
    private int paddle1Y = 100, paddle2Y = 100;
    private final int paddleHeight = 60, paddleWidth = 10;
    private final int ballSize = 30;
    private boolean wPressed = false, sPressed = false, upPressed = false, downPressed = false;

    private Image ballImage; // Adiciona a bola

    public SimplePingPong() {
        setPreferredSize(new Dimension(500, 300));
        setBackground(new Color(216, 167, 216)); // Fundo

        ballImage = new ImageIcon("kuromi.png").getImage();

        addKeyListener(this);
        setFocusable(true);
        Timer timer = new Timer(10, e -> {
            moveBall();
            movePaddles();
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Desenho da bola
        g.drawImage(ballImage, ballX, ballY, ballSize, ballSize, this);

        // Rebatedor 1
        g.setColor(Color.BLACK);
        g.fillRect(20, paddle1Y, paddleWidth, paddleHeight);

        // Rebatedor 2
        g.setColor(new Color(255, 105, 180));
        g.fillRect(470, paddle2Y, paddleWidth, paddleHeight);
    }

    private void moveBall() {
        ballX += ballDX;
        ballY += ballDY;

        // Rebote cima e baixo
        if (ballY <= 0 || ballY >= getHeight() - ballSize) ballDY = -ballDY;

        // Rebote
        if (ballX <= 30 && ballY >= paddle1Y && ballY <= paddle1Y + paddleHeight) ballDX = -ballDX;
        if (ballX >= 455 && ballY >= paddle2Y && ballY <= paddle2Y + paddleHeight) ballDX = -ballDX;

        // Reinicia a bola
        if (ballX < 0 || ballX > getWidth()) {
            ballX = 250;
            ballY = 150;
        }
    }

    private void movePaddles() {
        if (wPressed && paddle1Y > 0) paddle1Y -= 5;
        if (sPressed && paddle1Y < getHeight() - paddleHeight) paddle1Y += 5;
        if (upPressed && paddle2Y > 0) paddle2Y -= 5;
        if (downPressed && paddle2Y < getHeight() - paddleHeight) paddle2Y += 5;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                wPressed = true;
                break;
            case KeyEvent.VK_S:
                sPressed = true;
                break;
            case KeyEvent.VK_UP:
                upPressed = true;
                break;
            case KeyEvent.VK_DOWN:
                downPressed = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                wPressed = false;
                break;
            case KeyEvent.VK_S:
                sPressed = false;
                break;
            case KeyEvent.VK_UP:
                upPressed = false;
                break;
            case KeyEvent.VK_DOWN:
                downPressed = false;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}