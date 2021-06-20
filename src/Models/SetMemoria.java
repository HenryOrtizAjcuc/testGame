package src.Models;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public abstract class SetMemoria {
    protected Map<String, Object> recurso = new HashMap<>();

    public SetMemoria() {
    }

    protected Object cargarRecursos(String nombre) {
        URL url;
        url = getClass().getClassLoader().getResource(nombre);
        return cargarRecursos(url);
    }

    protected Object getResource(String nombre) {
        Object set = recurso.get(nombre);
        if (set == null) {
            set = cargarRecursos("" + nombre);
            recurso.put(nombre, set);
        }
        return set;
    }

    protected abstract Object cargarRecursos(URL url);
}