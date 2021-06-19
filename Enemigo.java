//Monster
public class Enemigo extends Accion{
	protected int vx;
	protected static  final double FIRING_FREQUENCY=0.01;
	
	public Enemigo(Inicio inicio){
	super(inicio);
	setSpriteNames(new String[]{"enemigo0.png","enemigo1.png"});
	setFrameSpeed(50);
	}
//Este metodo sirve para indicar la velocidad y el desplazamiento de los enemgios y asignarle quien dispara.
	public void act(){
	super.act();
	x+=vx;
		if(x<0||x>(Inicio.WIDTH-35))
			vx=-vx;
		
		if(Math.random()<FIRING_FREQUENCY)
			fire();
		
	}
//Este metodo sirve para llamara al metodo que valida colisiones
	public void collision(Accion a){
		if(a instanceof Disparos){//Si existe una colision entre los enemigos y el disparo.
			remove();//LLama al metodo remove
			generar();//LLama al metodo generar
			inicio.getJugador().addScore(1);//Agrega al punteo un punto.
		}
	}

	public void fire(){
		Fuego m=new Fuego(inicio);
		m.setX(x+getWidth()/2);
		m.setY(y+getHeight());
		inicio.addAccion(m);
		inicio.getSonido().reproducirSonido("bullet.wav");
	}
	public int getVx(){return vx;}
	public void setVx(int i){vx=i;}
	
	public void generar(){
	Enemigo m=new Enemigo(inicio);
	m.setX((int)(Math.random()*Inicio.WIDTH-35));
	m.setY((int)(Math.random()*Inicio.PLAY_HEIGHT/2));
	m.setVx((int)(Math.random()*20-10));
	inicio.addAccion(m);
	}
}
	