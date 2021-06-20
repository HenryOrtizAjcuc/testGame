package src.Models;//SpriteCache

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;
import javax.imageio.ImageIO;

public class Memoria extends SetMemoria implements ImageObserver {
    //private HashMap sprites;

    protected Object cargarRecursos(URL url) {
        try {
            return ImageIO.read(url);
        } catch (Exception e) {
            System.out.println("No se puede cargar la imagen de: " + url);
            System.out.println("El error es: " + e.getClass().getName() + " " + e.getMessage());
            System.exit(0);
            return null;
        }
    }

    public BufferedImage Compatibilidad(int ancho, int alto, int transpariencia) {
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        return gc.createCompatibleImage(ancho, alto, transpariencia);
    }

    public BufferedImage getSprite(String nombre) {
        BufferedImage carga = (BufferedImage) getResource(nombre);
        BufferedImage compatible = Compatibilidad(carga.getWidth(), carga.getHeight(), Transparency.BITMASK);
        Graphics g = compatible.getGraphics();
        g.drawImage(carga, 0, 0, this);
        return compatible;
    }

    public boolean imageUpdate(Image img, int infoflags, int x, int y, int w, int h) {
        return (infoflags & (ALLBITS | ABORT)) == 0;
    }
}