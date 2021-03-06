package taco.mineopoly.sections;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import taco.mineopoly.Mineopoly;
import taco.mineopoly.sections.squares.*;

/**
 * Represents any section on the board
 * @author Taco
 *
 */
public abstract class MineopolySection implements Comparable<MineopolySection> {

	private int id;
	protected String name;
	protected char color;
	private Location location = getLocation();
	
	public MineopolySection(int id, String name, char color){
		this.name = name;
		this.color = color;
		this.id = id;
	}
	
	public abstract void getInfo(Player player);
	
	public String getColorfulName(){
		return "&" + color + name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String toString(){
		return getName();
	}
	
	public int getId(){
		return id;
	}
	
	public Location getLocation(){
		Location origin = Mineopoly.config.getBoardOrigin();
		location = origin;
		setPitchYaw();
		if(this instanceof CardinalSection){
			if(id > 0 && id < 10){
				location.setX(location.getX() - (13.5 + (10 * id)));
				location.setZ(location.getZ() - 8.5);
			}else if(id > 10 && id < 20){
				location.setX(location.getX() - 118.5);
				location.setZ(location.getZ() - (13.5 + (10 * (id - 10))));
			}else if(id > 20 && id < 30){
				location.setX(location.getX() - (13.5 + (10 * (30 - id))));
				location.setZ(location.getZ() - 118.5);
			}else if(id > 30){
				location.setX(location.getX() - 8.5);
				location.setZ(location.getZ() - (13.5 + (10 * (40 - id))));
			}
			
		}else if(this instanceof SpecialSquare){
			if(this instanceof GoSquare){
				location.setX(location.getX() - 9.5);
				location.setZ(location.getZ() - 8.5);
			}else if(this instanceof FreeParkingSquare){
				location.setX(location.getX() - 119.5);
				location.setZ(location.getZ() - 118.5);
			}else if(this instanceof JailSquare){
				location.setX(location.getX() - 9.5);
				location.setZ(location.getZ() - 118.5);
			}
		}
		return location;
	}
	
	private void setPitchYaw(){
		location.setPitch(0);
		if(this instanceof CardinalSection){
			CardinalSection section = (CardinalSection) this;
			location.setYaw((section.getSide() + 1) * 90);
		}else if(this instanceof SpecialSquare){
			SpecialSquare square = (SpecialSquare) this;
			if(square instanceof GoSquare){
				location.setYaw(90);
			}else if(square instanceof JailSquare){
				location.setYaw(180);
			}else if(square instanceof FreeParkingSquare){
				location.setYaw(270);
			}else if(square instanceof GoToJailSquare){
				location.setYaw(0);
			}
		}
	}
	
	@Override
	public int compareTo(MineopolySection section){
		return id - section.getId();
	}
}
