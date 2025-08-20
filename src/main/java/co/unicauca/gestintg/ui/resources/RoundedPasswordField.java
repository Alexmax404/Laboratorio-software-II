package co.unicauca.gestintg.ui.resources;

import javax.swing.*;
import java.awt.*;

/**
 * PasswordField con bordes redondeados, borde personalizable y placeholder.
 */
public class RoundedPasswordField extends JPasswordField {

    private int cornerRadius;
    private Color borderColor;
    private int borderThickness;
    private String placeholder;

    public RoundedPasswordField(int cornerRadius, Color borderColor, int borderThickness, String placeholder) {
        super();
        this.cornerRadius = cornerRadius;
        this.borderColor = borderColor;
        this.borderThickness = borderThickness;
        this.placeholder = placeholder;
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    public RoundedPasswordField(int cornerRadius, Color borderColor, int borderThickness) {
        this(cornerRadius, borderColor, borderThickness, null);
    }

    public RoundedPasswordField(int cornerRadius) {
        this(cornerRadius, Color.BLACK, 2, null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Fondo redondeado
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);

        super.paintComponent(g);

        // Dibujar placeholder si está vacío
        if ((getPassword() == null || getPassword().length == 0) && placeholder != null) {
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
    public int getCornerRadius() { return cornerRadius; }
    public void setCornerRadius(int cornerRadius) { this.cornerRadius = cornerRadius; repaint(); }

    public Color getBorderColor() { return borderColor; }
    public void setBorderColor(Color borderColor) { this.borderColor = borderColor; repaint(); }

    public int getBorderThickness() { return borderThickness; }
    public void setBorderThickness(int borderThickness) { this.borderThickness = borderThickness; repaint(); }

    public String getPlaceholder() { return placeholder; }
    public void setPlaceholder(String placeholder) { this.placeholder = placeholder; repaint(); }
}
