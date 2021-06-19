import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import java.net.URL;
import java.util.HashMap;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Sobrevive extends Canvas{
	public static final int WIDTH=640;
	public static final int HEIGHT=480;
	public static final int SPEED=10;
	
	public BufferStrategy strategy;
	
	public HashMap sprites;
	public int posX,posY,vX;
	
	public Sobrevive(){
		sprites=new HashMap();
		posX=WIDTH/2;
		posY=HEIGHT/3;
		vX=2;
		
		JFrame ventana=new JFrame("Sobrevive");
		JPanel panel=(JPanel)ventana.getContentPane();
		setBounds(0,0,WIDTH,HEIGHT);
		panel.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		panel.setLayout(null);
		panel.add(this);		
		ventana.setBounds(0,0,WIDTH,HEIGHT);
		ventana.setVisible(true);
		ventana.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		ventana.setResizable(false);
		createBufferStrategy(2);
		strategy=getBufferStrategy();
		requestFocus();
	}
	public BufferedImage cargarImage(String nombre){
		URL url=null;
			try{
				url=getClass().getClassLoader().getResource(nombre);
				return ImageIO.read(url);
			}
			catch(Exception e){
			System.out.println("No se puede cargar la imagen"+nombre+"de"+url);
			System.out.println("El error es: "+e.getClass().getName()+" "+e.getMessage());
			System.exit(0);
			return null;
			}
	}
	
	public BufferedImage getSprite(String nombre){
		BufferedImage img=(BufferedImage)sprites.get(nombre);
		if(img==null){
			img=cargarImage(""+nombre);
			sprites.put(nombre,img);
		}
		return img;
	}
	public void paintWorld(){
	Graphics g=strategy.getDrawGraphics();
	
		g.setColor(getBackground());
		g.fillRect(0,0,getWidth(),getHeight());
		g.drawImage(getSprite("enemigo.png"),posX,posY,this);
		
		strategy.show();
	}
	
	public void updateWorld(){
		posX+=vX;
		if(posX<0||posX>WIDTH){
			vX=-vX;
		}
	}
	public void game(){
		while(isVisible()){
			updateWorld();
			paintWorld();
			try{
				Thread.sleep(SPEED);
			}
			catch(InterruptedException e){}
		}
	}
	public static void main(String[]args){
		Sobrevive inicio=new Sobrevive();
		inicio.game();
	}
}