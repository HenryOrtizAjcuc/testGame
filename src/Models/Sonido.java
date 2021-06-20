package src.Models;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class Sonido extends SetMemoria {
    protected Object cargarRecursos(URL url) {
        return Applet.newAudioClip(url);
    }

    public AudioClip getAudioClip(String nombre) {
        return (AudioClip) getResource(nombre);
    }

    public void reproducirSonido(final String nombre) {
        new Thread(() -> getAudioClip(nombre).play()).start();
    }

    public void loopSound(final String nombre) {
        new Thread(() -> getAudioClip(nombre).loop()).start();
    }
}