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

public class Snowman extends Entity {
	
	private Sprite [] sprite=new Sprite[3];
	private int tick,pause,delay = 0,direction;
	private boolean standing;
	
	public Snowman(int x, int y, int breite, int höhe, Handler handler, Id id) {
		super(x, y, breite, höhe, handler, id);
		for(int i=0;i<sprite.length;i++){
			sprite[i]=new Sprite(Game.sheet,3+i , 2, 1, 2);
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.drawRect(x, y, breite, höhe);
		g.drawImage(sprite[frame].getBufferedImage(), x, y,breite,höhe, null);
	}
	
	public void tick() {
		if(!standing()){
			x+=velX;
			y+=velY;
		}
		frameticks();
		collision();
		move();
	}
	
	private void collision(){
		if(y<Game.getFrameHöhe()){
			setVelY(0);
		}
		for (Tile ti : handler.tile2) {

			if (getTop().intersects(ti.getBottom())) {
				setVelY(0);
				y = ti.getY() + 33;
				jumping = false;
				falling = true;
				gravity = 0;
			}
			if (getBottom().intersects(ti.getTop())) {
				setVelY(0);
				y = ti.getY() - 90;

			} else if (!jumping) {
				falling = true;
			}

			if (getLeft().intersects(ti.getRight())) {
				setVelX(0);
				x = ti.getX() + 53;
				standing=true;
				System.out.println("d");
			}
			if (getRight().intersects(ti.getLeft())) {
				setVelX(0);
				x = ti.getX() - 46;

			}

		}
		
		if(falling){
			falling();
			if(velY==1){
				tick=delay;
				move();
			}
		}
	}
	
	private void frameticks() {
		framedelay++;
		if (framedelay >= 13) {
			frame++;
			if (frame >= 3) {
				frame = 0;
			}
			framedelay = 0;
		}
	}
	
	private boolean standing(){
		if(standing){
			pause++;
			if(pause==10){
				standing=false;
			}
			return true;
		}else{
			pause=0;
		}
		return false;
	}

	private void move() {
		if(tick==delay){
			delay = Terracraft.Utils.RandomInt(200,600);
			direction = Terracraft.Utils.RandomInt(2);
				if(direction==0){
					setVelX(1);
				}else{
					setVelX(-1);
				}
				tick=0;
		}else{
			tick++;
		}
	}
	
	public Rectangle getRight() {
		return new Rectangle(getX() + breite - 5, getY() + 5, 5+20, höhe - 10);
	}

	public Rectangle getLeft() {
		return new Rectangle(getX()-20, getY() + 5, 5, höhe - 10);
	}

	

}
