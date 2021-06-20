package src.Models;

import src.Interfaces.Inicio;

//bullet
public class Disparo extends Accion {
    protected static final int DISPARO_SPEED = 10;

    public Disparo(Inicio inicio) {
        super(inicio);
        setSpriteNames(new String[]{"src/images/flama.png"});
    }

    //Este metodo sirve para borrar de pantalla los disparos que hayan salido del marco.
    public void act() {
        super.act();
        y -= DISPARO_SPEED;
        if (y < 0)
            remove();
    }

}