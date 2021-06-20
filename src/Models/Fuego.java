package src.Models;

import src.Interfaces.Inicio;

//Laser
public class Fuego extends Accion {
    protected static final int DISPARO_SPEED = 3;

    public Fuego(Inicio inicio) {
        super(inicio);
        setSpriteNames(new String[]{"src/images/fue0.png"});
        setFrameSpeed(10);
    }

    public void act() {
        super.act();
        y += DISPARO_SPEED;
        if (y > Inicio.PLAY_HEIGHT)
            remove();
    }
}