package src.Models;

import src.Interfaces.Inicio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Vive extends Canvas implements Inicio, KeyListener {

    private final BufferStrategy strategy;
    private final Memoria memoria;
    private final Sonido sonido;
    private ArrayList actors;
    private Jugador jugador;
    private boolean gameEnded = false;

    //Metodo Constructor
    public Vive() {
        memoria = new Memoria();
        sonido = new Sonido();
        JFrame ventana = new JFrame("Sobrevive");
        JPanel panel = (JPanel) ventana.getContentPane();
        setBounds(0, 0, Inicio.WIDTH, Inicio.HEIGHT);
        panel.setPreferredSize(new Dimension(Inicio.WIDTH, Inicio.HEIGHT));
        panel.setLayout(null);
        panel.add(this);
        ventana.setBounds(0, 0, Inicio.WIDTH, Inicio.HEIGHT);
        ventana.setVisible(true);
        ventana.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        ventana.setResizable(false);
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        requestFocus();
        addKeyListener(this);
    }

    //Metodos para obtener datos del jugador(usuario)
    public Jugador getJugador() {
        return jugador;
    }

    //Metodo para obtener el sonido
    public Sonido getSonido() {
        return sonido;
    }

    //Metodo para llamar a la clase src.Models.Accion y agregar personajes en el juego
    public void addAccion(Accion a) {
        actors.add(a);
    }

    //Metodo para llamar a los metodos de la  clase src.Models.Memoria
    public Memoria getMemoria() {
        return memoria;
    }

    //Metodo para finalizar el juego
    public void gameOver() {
        gameEnded = true;
    }

    //Metodo para actualizar lo que sucede en el juego
    public void updateWorld() {
        int i = 0;
        while (i < actors.size()) {
            Accion m = (Accion) actors.get(i);
            if (m.isMarkedForRemoval()) {
                actors.remove(i);
            } else {
                m.act();
                i++;
            }
        }
        jugador.act();
    }

    //Metodos para obterner la tecla presionada por el usuario
    public void keyPressed(KeyEvent e) {
        jugador.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        jugador.keyReleased(e);
    }

    public void keyTyped(KeyEvent e) {
    }

    //Metodo para inicializar enemgios y al personaje en el juego.
    public void initWorld() {
        actors = new ArrayList();
        for (int i = 0; i < 4; i++) {
            Enemigo enemigo = new Enemigo(this);
            enemigo.setX((int) (Math.random() + 1 * (Inicio.WIDTH - 35)));
            enemigo.setY(i * 40);
            enemigo.setVx((int) (Math.random() * 10 - 5));
            actors.add(enemigo);
        }
        jugador = new Jugador(this);
        jugador.setX(Inicio.WIDTH / 2);
        jugador.setY(Inicio.PLAY_HEIGHT - 1 * jugador.getHeight());
        sonido.loopSound("src/audio/audio.wav");
    }

    //Metodo para validar colisiones
    public void validarColision() {
        Rectangle punteo = jugador.getBounds();
        for (int i = 0; i < actors.size(); i++) {//Valida las colisiones sobre el personaje.
            Accion a1 = (Accion) actors.get(i);
            Rectangle r1 = a1.getBounds();
            if (r1.intersects(punteo)) {
                jugador.collision(a1);
                a1.collision(jugador);
            }
            for (int j = i + 1; j < actors.size(); j++) {//valida colisiones sobre los enemigos
                Accion a2 = (Accion) actors.get(j);
                Rectangle r2 = a2.getBounds();
                if (r1.intersects(r2)) {
                    a1.collision(a2);
                    a2.collision(a1);
                }
            }
        }
    }

    //Metodo para generar el juego
    public void paintWorld() {
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        for (int i = 0; i < actors.size(); i++) {
            Accion accion = (Accion) actors.get(i);
            accion.paint(g);
        }
        jugador.paint(g);
        paintEstado(g);
        strategy.show();
    }

    //Metodo para mostar fin del juego
    public void paintGameOver() {
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("GAME OVER", Inicio.WIDTH / 2 - 50, Inicio.HEIGHT / 2);
        strategy.show();
    }

    //Metodo para inicial el juego
    public void game() {
        initWorld();
        while (isVisible() && !gameEnded) {
            updateWorld();
            validarColision();
            paintWorld();
            try {
                Thread.sleep(Inicio.SPEED);
            } catch (InterruptedException e) {
            }
        }
        paintGameOver();
    }

    //Metodo para obtener el punteo
    public void paintPunteo(Graphics2D g) {
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setPaint(Color.white);
        g.drawString("Punteo", 20, Inicio.PLAY_HEIGHT + 20);
        g.setPaint(Color.red);
        g.drawString(jugador.getScore() + "", 100, Inicio.PLAY_HEIGHT + 20);
    }

    //Metodo para mostrar el nivel
    public void paintnivel(Graphics2D g) {
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setPaint(Color.white);
        g.drawString("Nivel ", 450, Inicio.PLAY_HEIGHT + 20);
        g.setPaint(Color.red);
        g.drawString(jugador.getNivel() + "", 520, Inicio.PLAY_HEIGHT + 20);
    }

    //Metodo para obtener el barra de vida
    public void paintShields(Graphics2D g) {
        g.setPaint(Color.red);
        g.fillRect(230, Inicio.PLAY_HEIGHT, Jugador.MAX_SHIELDS, 30);
        g.setPaint(Color.green);
        g.fillRect(230 + Jugador.MAX_SHIELDS - jugador.getShields(), Inicio.PLAY_HEIGHT, jugador.getShields(), 30);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setPaint(Color.white);
        g.drawString("Vida", 170, Inicio.PLAY_HEIGHT + 20);
    }

    //Metodo para mostrar en pantalla el punteo y barra de vida
    public void paintEstado(Graphics2D g) {
        paintPunteo(g);
        paintShields(g);
        paintnivel(g);
    }

    //Metodo principal
    public static void main(String[] args) {
        Vive inicio = new Vive();
        inicio.game();
    }
}