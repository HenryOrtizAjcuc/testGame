//Stage
import java.awt.image.ImageObserver;

public interface Inicio extends ImageObserver{
	public static final int WIDTH=640;
	public static final int HEIGHT=480;
	public static final int PLAY_HEIGHT=400;
	public static final int SPEED=60;
	public Memoria getMemoria();
	public Sonido getSonido();
	public void addAccion(Accion a);
	public Jugador getJugador();
	public void gameOver();
	
}