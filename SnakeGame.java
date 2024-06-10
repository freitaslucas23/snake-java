import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JPanel implements KeyListener, ActionListener {
    private final int SCALE = 20;
    private final int WIDTH = 50;
    private final int HEIGHT = 30;
    private ArrayList<Point> snake;
    private Point fruit;
    private int direction;
    private Timer timer;

    public SnakeGame() {
        snake = new ArrayList<>();
        direction = KeyEvent.VK_RIGHT;
        snake.add(new Point(10, 10));
        fruit = new Point(5, 5);
        timer = new Timer(100, this);
        timer.start();
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
        g.setColor(Color.GREEN);
        for (Point p : snake) {
            g.fillRect(p.x * SCALE, p.y * SCALE, SCALE, SCALE);
        }
        g.setColor(Color.RED);
        g.fillRect(fruit.x * SCALE, fruit.y * SCALE, SCALE, SCALE);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        move();
        if (checkCollision()){
            timer.stop();
            JOptionPane.showMessageDialog(this, "Game Over");

        }
        if (snake.get(0).equals(fruit)){
            eatFruit();
        }
        repaint();
    }

    private void move() {
        Point head = new Point(snake.get(0));
        switch (direction) {
            case KeyEvent.VK_UP:
                head.y--;
                break;
            case KeyEvent.VK_DOWN:
                head.y++;
                break;
            case KeyEvent.VK_LEFT:
                head.x--;
                break;
            case KeyEvent.VK_RIGHT:
                head.x++;
                break;
        }
        snake.add(0, head);
        snake.remove(snake.size() - 1);

    }
    private boolean checkCollision() {
        Point head = snake.get(0);
        if (head.x < 0 || head.x >= WIDTH || head.y < 0 || head.y >= HEIGHT) {
            return true;
        }
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                return true;
            }
        }
        return false;
    }

    private  void eatFruit() {
        snake.add(new Point(fruit));
        generateFruit();
    }

    private void generateFruit() {
        Random rand = new Random();
        int x, y;
        do {
            x = rand.nextInt(WIDTH);
            y = rand.nextInt(HEIGHT);
        } while (snake.contains(new Point(x, y)));
        fruit.setLocation(x, y);
    }

    @Override
    public void keyTyped(KeyEvent e){
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN)
                && Math.abs(direction - key) != 2) {
            direction = key;
        }
    }


    @Override
    public void keyReleased(KeyEvent e){

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        SnakeGame game = new SnakeGame();
        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
