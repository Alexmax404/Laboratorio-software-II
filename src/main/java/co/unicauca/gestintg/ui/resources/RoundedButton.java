package co.unicauca.gestintg.ui;

import java.awt.*;
import javax.swing.*;

public class RoundedButton extends JButton {
    private int cornerRadius = 15;

    public RoundedButton(String text) {
        super(text);
        setOpaque(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setBackground(new Color(240, 240, 240)); // color base
        setForeground(Color.BLACK);
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // cambia a manito al pasar
    }

    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        ButtonModel model = getModel();

        // Cambia el color según el estado del botón
        if (model.isPressed()) {
            g2.setColor(new Color(200, 200, 200)); // color al hacer click
        } else if (model.isRollover()) {
            g2.setColor(new Color(220, 220, 220)); // color al pasar el mouse
        } else {
            g2.setColor(getBackground()); // color normal
        }

        // Fondo redondeado
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        // Borde
        g2.setColor(Color.GRAY);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);

        // Texto centrado
        FontMetrics fm = g2.getFontMetrics();
        Rectangle rect = new Rectangle(0, 0, getWidth(), getHeight());
        int textHeight = fm.getAscent();
        int textY = rect.y + (rect.height - textHeight) / 2 + fm.getAscent();
        int textX = (getWidth() - fm.stringWidth(getText())) / 2;

        g2.setColor(getForeground());
        g2.drawString(getText(), textX, textY);

        g2.dispose();
    }
}
