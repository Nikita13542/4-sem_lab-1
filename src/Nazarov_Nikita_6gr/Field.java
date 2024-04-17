package Nazarov_Nikita_6gr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Field extends JPanel {
    private boolean paused;
    private ArrayList<BouncingBall> balls = new ArrayList<>();
    private Timer repaintTimer = new Timer(10, new ActionListener() {
        public void actionPerformed(ActionEvent ev) {
            repaint();
        }
    });

    public Field() {
        setBackground(Color.WHITE);
        repaintTimer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        for (BouncingBall ball : balls) {
            ball.paint(canvas);
        }
    }

    public void addBall() {
        balls.add(new BouncingBall(this));
    }

    public synchronized void pause() {
        paused = true;
    }

    public synchronized void resume() {
        paused = false;
        notifyAll();
    }

    public synchronized void canMove(BouncingBall ball) throws InterruptedException {
        if (paused) {
            wait();
        }
    }

    public synchronized void applyTeamEffect(String teamId) {
        for (BouncingBall ball : balls) {
            if (!ball.getTeamId().equals(teamId)) {
                ball.setActive(false); // Останавливаем мячи с неправильным идентификатором
            } else {
                ball.setActive(true); // Делаем мячи с правильным идентификатором активными
            }
        }
    }
}
