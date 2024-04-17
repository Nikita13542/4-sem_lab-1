package Nazarov_Nikita_6gr;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class BouncingBall implements Runnable {
    private static final int MAX_RADIUS = 80;
    private static final int MIN_RADIUS = 0;
    private static final int MAX_SPEED = 15;
    private Field field;
    private int radius;
    private Color color;
    private double x;
    private double y;
    private int speed;
    private double speedX;
    private double speedY;
    private boolean active = true; // Флаг для определения активности мяча
    private String teamId; // Поле для хранения идентификатора команды

    public BouncingBall(Field field) {
        this.field = field;
        radius = (int) (Math.random() * (MAX_RADIUS - MIN_RADIUS)) + MIN_RADIUS;
        speed = (int) (5 * MAX_SPEED / radius);
        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }
        double angle = Math.random() * 2 * Math.PI;
        speedX = 3 * Math.cos(angle);
        speedY = 3 * Math.sin(angle);
        color = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
        x = Math.random() * (field.getSize().getWidth() - 2 * radius) + radius;
        y = Math.random() * (field.getSize().getHeight() - 2 * radius) + radius;
        teamId = getId(); // Сохраняем сгенерированный идентификатор команды
        Thread thisThread = new Thread(this);
        thisThread.start();
    }

    public void run() {
        try {
            while (true) {
                if (active) { // Проверяем активность мяча перед выполнением движения
                    field.canMove(this);
                    if (x + speedX <= radius) {
                        speedX = -speedX;
                        x = radius;
                    } else if (x + speedX >= field.getWidth() - radius) {
                        speedX = -speedX;
                        x = field.getWidth() - radius;
                    } else if (y + speedY <= radius) {
                        speedY = -speedY;
                        y = radius;
                    } else if (y + speedY >= field.getHeight() - radius) {
                        speedY = -speedY;
                        y = field.getHeight() - radius;
                    } else {
                        x += speedX;
                        y += speedY;
                    }
                }
                Thread.sleep(16 - speed);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void paint(Graphics2D canvas) {
        canvas.setColor(color);
        canvas.setPaint(color);
        Ellipse2D.Double ball = new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius);
        canvas.draw(ball);
        canvas.fill(ball);
        // Отображаем идентификатор команды рядом с мячом
        canvas.setColor(Color.BLACK);
        canvas.drawString(teamId, (int) x + radius + 5, (int) y);
    }

    public String getId() {
        return "Team" + ((int) (Math.random() * 3)); // Генерация случайного идентификатора команды
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTeamId() {
        return teamId;
    }
}
