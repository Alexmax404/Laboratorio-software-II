package co.unicauca.gestiontg.ui.resources;

import javax.swing.*;
import java.awt.*;

/**
 * TextField con bordes redondeados, borde personalizable y placeholder.
 */
public class RoundedTextField extends JTextField {

    private int cornerRadius;       // radio de las esquinas
    private Color borderColor;      // color del borde
    private int borderThickness;    // grosor del borde
    private String placeholder;     // texto guía

    /**
     * Constructor completo: define radio, color, grosor y placeholder.
     */
    public RoundedTextField(int cornerRadius, Color borderColor, int borderThickness, String placeholder) {
        super();
        this.cornerRadius = cornerRadius;
        this.borderColor = borderColor;
        this.borderThickness = borderThickness;
        this.placeholder = placeholder;
        setOpaque(false); // importante para que se vea el redondeado
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // padding interno
    }

    /**
     * Constructor sin placeholder.
     */
    public RoundedTextField(int cornerRadius, Color borderColor, int borderThickness) {
        this(cornerRadius, borderColor, borderThickness, null);
    }

    /**
     * Constructor simple con valores por defecto (borde negro y grosor 2).
     */
    public RoundedTextField(int cornerRadius) {
        this(cornerRadius, Color.BLACK, 2, null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Fondo redondeado
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Fondo
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);

        // Dibujar contenido normal (texto y caret)
        super.paintComponent(g);

        // Dibujar placeholder si no hay texto
        if ((getText() == null || getText().isEmpty()) && placeholder != null) {
            g2.setColor(Color.GRAY);
            g2.setFont(getFont().deriveFont(Font.ITALIC));
            Insets ins = getInsets();
            g2.drawString(placeholder, ins.left, height / 2 + g2.getFontMetrics().getAscent() / 2 - 2);
        }

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

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        repaint();
    }
}
