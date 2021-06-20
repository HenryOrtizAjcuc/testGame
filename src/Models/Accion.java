package src.Models;//Actor Clase para agregar personajes al juego

import src.Interfaces.Inicio;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class Accion {
    protected int x, y, width, height;
    protected String[] spriteNames;
    protected int currentFrame;
    protected int frameSpeed;
    protected int t;
    protected Inicio inicio;
    protected Memoria memoria;
    protected boolean markedForRemoval;

    public Accion(Inicio inicio) {
        this.inicio = inicio;
        memoria = inicio.getMemoria();
        currentFrame = 0;
        frameSpeed = 1;
        t = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int i) {
        x = i;
    }

    public int getY() {
        return y;
    }

    public void setY(int i) {
        y = i;
    }

    public int getFrameSpeed() {
        return frameSpeed;
    }

    public void setFrameSpeed(int i) {
        frameSpeed = i;
    }

    public void setSpriteNames(String[] names) {
        spriteNames = names;
        height = 0;
        width = 0;
        for (int i = 0; i < names.length; i++) {
            BufferedImage image = memoria.getSprite(spriteNames[i]);
            height = Math.max(height, image.getHeight());
            width = Math.max(width, image.getWidth());
        }
    }

    //Este metodo dibuja la imagen Obtendia
    public void paint(Graphics2D g) {
        g.drawImage(memoria.getSprite(spriteNames[currentFrame]), x, y, inicio);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int i) {
        height = i;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int i) {
        width = i;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void collision(Accion a) {

    }

    public void act() {
        t++;
        if (t % frameSpeed == 0) {
            t = 0;
            currentFrame = (currentFrame + 1) % spriteNames.length;
        }
    }

    //Metodo que borrara los elementos ya marcados
    public void remove() {
        markedForRemoval = true;
    }

    //Metodo que marcara los elementos a borrar
    public boolean isMarkedForRemoval() {
        return markedForRemoval;
    }
}