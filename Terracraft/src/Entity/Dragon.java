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
	private int aimX,amiY,direction;
	
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
		int xCal = x-enemy.getX();
		int yCal = y-enemy.getY();
		if(getFightBounds().intersects(enemy.getBounds())){
			retreat=true;
		}else if(!retreat){
			setVelX(-xCal/25);
			setVelY(-yCal/35);
		}
		if(!getFightBounds().intersects(enemy.getArea())){
			retreat=false;
		}
	}
	
	public Rectangle getFightBounds(){
		return new Rectangle(x+10,y-10,breite-10,höhe-10);
	}
	
	public Rectangle getArea(){
		return new Rectangle(x - 224, y - 224, 96 * 5 + breite, 96 * 5 + höhe);
	}
	
	public void lookIfFight(){
		for(Entity en:Game.handler.entity2){
			if(getArea().intersects(en.getBounds())&&!en.id.equals(Id.Drache)){
				fight=true;
				enemy = en;
				break;
			}else{
				fight=false;
			}
		}
	}

}
