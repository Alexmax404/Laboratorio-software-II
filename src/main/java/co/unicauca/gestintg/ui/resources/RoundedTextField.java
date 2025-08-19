package co.unicauca.gestintg.ui.resources;

import javax.swing.*;
import java.awt.*;

/**
 * TextField con bordes redondeados y borde personalizable.
 */
public class RoundedTextField extends JTextField {

    private int cornerRadius;       // radio de las esquinas
    private Color borderColor;      // color del borde
    private int borderThickness;    // grosor del borde

    /**
     * Constructor completo: define radio, color y grosor.
     */
    public RoundedTextField(int cornerRadius, Color borderColor, int borderThickness) {
        super();
        this.cornerRadius = cornerRadius;
        this.borderColor = borderColor;
        this.borderThickness = borderThickness;
        setOpaque(false); // importante para que se vea el redondeado
        setBorder(BorderFactory.createEmptyBorder(5, 200, 5, 20)); // padding interno
    }

    /**
     * Constructor con valores por defecto: borde negro y grosor 2.
     */
    public RoundedTextField(int cornerRadius) {
        this(cornerRadius, Color.BLACK, 2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Fondo redondeado
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Fondo del campo
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);

        // Dibujar texto y caret
        super.paintComponent(g);

        // Borde
        if (borderThickness > 0) {
            g2.setStroke(new BasicStroke(borderThickness));
            g2.setColor(borderColor);
            g2.drawRoundRect(borderThickness / 2, borderThickness / 2,
                    width - borderThickness, height - borderThickness,
                    cornerRadius, cornerRadius);
        }

        g2.dispose();
    }

    // Getters y setters
    public int getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        repaint();
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        repaint();
    }

    public int getBorderThickness() {
        return borderThickness;
    }

    public void setBorderThickness(int borderThickness) {
        this.borderThickness = borderThickness;
        repaint();
    }
}
