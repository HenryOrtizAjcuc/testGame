package src.Interfaces;//Stage

import src.Models.Accion;
import src.Models.Jugador;
import src.Models.Memoria;
import src.Models.Sonido;

import java.awt.image.ImageObserver;

public interface Inicio extends ImageObserver {
    int WIDTH = 640;
    int HEIGHT = 480;
    int PLAY_HEIGHT = 400;
    int SPEED = 60;

    Memoria getMemoria();

    Sonido getSonido();

    Jugador getJugador();

    void addAccion(Accion a);

    void gameOver();
}