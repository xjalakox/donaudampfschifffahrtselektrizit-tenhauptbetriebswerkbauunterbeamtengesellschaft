package Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import Terracraft.Game;
import Terracraft.Handler;
import Terracraft.Id;
import Tile.source.Tile;
import gfx.Sprite;

public class Dragon extends Entity {
	
	private Sprite [] sprite=new Sprite[4];
	private int tick;
	private int delay = 0;
	private boolean fight,retreat;
	private Entity enemy;
	private int direction,toReachX,toReachY,xCal,yCal;
	private boolean distanceCheck,goBack;
	
	public Dragon(int x, int y, int breite, int höhe, Handler handler, Id id) {
		super(x, y, breite, höhe, handler, id);
		for(int i=0;i<sprite.length;i++){
			sprite[i]=new Sprite(Game.sheet,9+i , 1, 1, 1);
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.drawRect(x, y, breite, höhe);
		g.drawImage(sprite[frame].getBufferedImage(), x, y,breite,höhe, null);
	}
	
	public void tick() {
		framedelay++;
		if (framedelay >= 13) {
			frame++;
			if (frame >= 4) {
				frame = 0;
			}
			framedelay = 0;
		}
		lookIfFight();
		if(!fight){
		if(tick==delay){
			delay = Terracraft.Utils.RandomInt(400,800);
			tick=0;
			direction = Terracraft.Utils.RandomInt(2);
				if(direction==0){
					setVelX(1);
				}else{
					setVelX(-1);
				}
		}
		tick++;
		
		}else{
			calculateAttack();
		}
			x+=velX;
			y+=velY;
	}

	private void calculateAttack() {
		
		if(x==enemy.getX()){
			goBack=true;
		}
		
		if(!goBack){
			xCal = (x-enemy.getX()+(enemy.getBreite()/2))/20;
			yCal = (y-enemy.getY())/20;
			setVelX(-xCal);
			setVelY(-yCal);
		}else{
			xCal = (x-toReachX)/20;
			yCal = (y-toReachY)/20;
			setVelX(-xCal);
			setVelY(-yCal);
			if(x>toReachX&&y<toReachY){
				setVelX(0);
				setVelY(0);
			}
		}
		System.out.println(velY+" "+velX);
	}
	
	public Rectangle getArea(){
		return new Rectangle(x - 224, y - 224, 96 * 5 + breite, 96 * 5 + höhe);
	}
	
	public void lookIfFight(){
		for(Entity en:Game.handler.entity2){
			if(getArea().intersects(en.getBounds())&&!en.id.equals(Id.Dragon)){
				fight=true;
				enemy = en;
				break;
			}else{
				fight=false;
			}
		}
		if(!distanceCheck&&enemy!=null){
			if(enemy.getX()<x){
				toReachX =enemy.getX()- (x-enemy.getX());
			}else if(enemy.getX()>x){
				toReachX =(enemy.getX()-x)+enemy.getX();
			}
			toReachY = y;
			distanceCheck=true;
			System.out.println("toReachX: "+toReachX+" toReachY: "+toReachY);
		}
	}

}
