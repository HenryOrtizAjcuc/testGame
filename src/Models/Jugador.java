package src.Models;

import src.Interfaces.Inicio;

import java.awt.event.KeyEvent;

public class Jugador extends Accion {

    protected static final int Jugador_SPEED = 5;
    protected int vx;
    protected int vy;
    private boolean up, down, left, right;
    private int score;
    public static final int MAX_SHIELDS = 200;
    private int shields;
    private int nivel;

    public Jugador(Inicio inicio) {
        super(inicio);
        setSpriteNames(new String[]{"src/images/1.png"});
        score = 0;
        shields = MAX_SHIELDS;
        nivel = 1;
    }

    //Este metodo nos sirve para que nuestro personaje se quede en los bordes de la pantalla
    public void act() {
        super.act();
        x += vx;
        y += vy;
        if (x < 0)
            x = 0;
        if (x > Inicio.WIDTH - getWidth())
            x = Inicio.WIDTH - getWidth();
        if (y < 0)
            y = 0;
        if (y > Inicio.PLAY_HEIGHT - getHeight())
            y = Inicio.PLAY_HEIGHT - getHeight();
    }

    //Metodos para configuar movimientos
    public int getVx() {
        return vx;
    }

    public void setVx(int i) {
        vx = i;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int i) {
        vy = i;
    }

    //Metodos para configuar la velocidad del personaje que utiliza el usuario
    protected void updateSpeed() {
        vx = 0;
        vy = 0;
        if (down) vy = Jugador_SPEED;
        if (up) vy = -Jugador_SPEED;
        if (left) vx = -Jugador_SPEED;
        if (right) vx = Jugador_SPEED;
    }

    //Metodo para obtener la tecla presionada
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                up = true;
                break;
            case KeyEvent.VK_LEFT:
                left = true;
                setSpriteNames(new String[]{"src/images/2.png", "src/images/3.png", "src/images/4.png", "src/images/5.png"});
                break;
            case KeyEvent.VK_RIGHT:
                right = true;
                setSpriteNames(new String[]{"src/images/8.png", "src/images/9.png", "src/images/10.png", "src/images/11.png"});
                break;
            case KeyEvent.VK_DOWN:
                down = true;
                break;
            case KeyEvent.VK_SPACE:
                fire();
                break;
        }
        updateSpeed();
    }

    //Metodos para detectar la tecla que no esta siendo presionada.
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                down = false;
                break;
            case KeyEvent.VK_UP:
                up = false;
                break;
            case KeyEvent.VK_LEFT:
                left = false;
                setSpriteNames(new String[]{"src/images/1.png"});
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                setSpriteNames(new String[]{"src/images/12.png"});
                break;
        }
        updateSpeed();
    }

    //Estos metodos sirve para obtener y asignar la barra de vida
    public int getShields() {
        return shields;
    }

    public void setShields(int i) {
        shields = i;
    }

    public void addShields(int i) {
        shields += i;
        if (shields > MAX_SHIELDS)
            shields = MAX_SHIELDS;
    }

    //Metodos para obtener y asignar el resultado
    public int getScore() {
        return score;
    }

    public void setScore(int i) {
        score = i;
    }

    public void addScore(int i) {
        score += i;
        if (score / nivel == 10) {
            nivel += i;
        }
    }


    //Metodo para aumetar el nivel
    public int getNivel() {
        return nivel;
    }

    public void setNivel(int a) {
        nivel = a;
    }

    public void addNivel(int a) {
        nivel += a;
    }

    //Metodos para detectar colisiones
    public void collision(Accion a) {
        if (a instanceof Enemigo) {//funcion para detectar colisiones entre el personaje y los enemigos.
            a.remove();//LLama al metodo remove para borrar al enemigo
            addScore(1);//Si colisiona el personaje con los enemigos obtenemos 10 pts.
            addShields(-40);//Si colisiona el personaje con los enemigos perdemos un 20% de vida.
        }
        if (a instanceof Fuego) {//funcion para detectar colision del disparo del enemigo con el personaje.
            a.remove();//LLama al metodo remove para borrar el disparo del enemigo.
            addShields(-10);//Nos disminulle la vida en un 5%.
        }
        if (getShields() < 0) {//Esta funcion verifica si la barra de vida a llegado a cero.
            inicio.gameOver();//Si la barra de vida a llegado a cero llama al metodo fin del juego(gameOver).
        }

    }

    //Metodos para disparar
    public void fire() {
        Disparo b = new Disparo(inicio);
        b.setX(x);
        b.setY(y - b.getHeight());
        inicio.addAccion(b);
        inicio.getSonido().reproducirSonido("src/audio/fire.wav");
    }
}