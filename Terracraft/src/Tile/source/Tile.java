package Tile.source;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import Terracraft.Game;
import Terracraft.Handler;
import Terracraft.Id;
import Terracraft.Utils;
import gfx.Sprite;

public class Tile {

	public int x, y, width, height, velX, velY,mapX,mapY;
	Handler handler;
	Id id;
	public int damage;
	public boolean shouldRemove,underground;

	public Tile(int x, int y, int Width, int height,Id id) {
		this.x = x;
		this.y = y;
		this.width = Width;
		this.height = height;
		this.id = id;
	}
	public void renderMap(Graphics g,Sprite sprite){
		double tmp;
		if((Game.player.getX()+Game.player.getBreite()/2)<x){
			tmp=(x-Game.player.getX())/3.55;
			mapX=(Game.player.getX() + 200+175)+Utils.toInt(tmp);
		}else{
			tmp=(Game.player.getX()-x)/3.55;
			mapX=(Game.player.getX() + 200+175)-Utils.toInt(tmp);;
		}
		if((Game.player.getY()+Game.player.getHeight()/2)<y){
			tmp=(y-Game.player.getY())/3.55;
			mapY=(Game.player.getY() - 430+103+30)+Utils.toInt(tmp);
		}else{
			tmp=(Game.player.getY()-y)/3.55;
			mapY=(Game.player.getY() - 430+103+30)-Utils.toInt(tmp);
		}
		if(Game.map.getBounds().intersects(new Rectangle(mapX,mapY,9,9))){
			g.drawImage(sprite.getBufferedImage(),mapX,mapY,9,9,null);
		}

	

		
	}
	public int getMapX() {
		return mapX;
	}

	public void setMapX(int mapX) {
		this.mapX = mapX;
	}

	public int getMapY() {
		return mapY;
	}

	public void setMapY(int mapY) {
		this.mapY = mapY;
	}

	public  void render(Graphics g){
		
	}

	public  void tick(){
	
	}
	
	public boolean shouldRemove() {
		return shouldRemove;
	}
	
	public void setAsRemoved(){
		shouldRemove=true;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int Width) {
		this.width = Width;
	}

	public int getHeight() {
		return height;
	}

	public void setheight(int height) {
		this.height = height;
	}

	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x,y,width,height);
	}
	public Rectangle getBoundsTop(){
		return new Rectangle(x + 200,y - 430,9,10);
	}
	public Rectangle getRight(){
		return new Rectangle(x+width-5,y+5,5,height-10);
	}
	public Rectangle getLeft(){
		return new Rectangle(x,y+5,5,height-10);
	}
	public Rectangle getBottom(){
		return new Rectangle(x,y+height-8,width,8);
	}
	public Rectangle getTop(){
		return new Rectangle(x+2,y,width-4,8);
	}
	
	public Rectangle getTileTop(){
		return new Rectangle(x,y-height,width,height);
	}
	
	public int getDamage(){
		return damage;
	}
	
	public void setHealth(int damage){
		this.damage = damage;
	}
	
	public void addDamage(int damage){
		this.damage -= damage;
	}
	
}
