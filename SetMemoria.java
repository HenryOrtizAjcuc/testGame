import java.net.URL;
import java.util.HashMap;

public abstract class SetMemoria{
	protected HashMap recurso;
	
	public SetMemoria(){
		recurso=new HashMap();
	}
	protected Object  cargarRecursos(String nombre){
		URL url=null;
		url=getClass().getClassLoader().getResource(nombre);
		return cargarRecursos(url);
	}
	protected Object getResource(String nombre){
	Object set=recurso.get(nombre);
		if(set==null){
		set=cargarRecursos(""+nombre);
		recurso.put(nombre,set);
		}
		return set;
	}
	protected abstract Object cargarRecursos(URL url);
}