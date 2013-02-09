package taco.mineopoly.sections;

import java.util.Random;

import org.bukkit.entity.Player;

import taco.mineopoly.Mineopoly;
import taco.mineopoly.MineopolyPlayer;
import taco.tacoapi.api.TacoChatUtils;

/**
 * Represents a utility space on the board. Implements {@link taco.mineopoly.sections.Ownable Ownable}
 * @author Taco
 *
 */
public class Utility extends MineopolySection implements Ownable, CardinalSection{

	private MineopolyPlayer owner;
	private boolean owned = false;
	private int side, price = 150;
	private boolean mortgaged;
	
	public Utility(int id, String name, char color, int side) {
		super(id, Mineopoly.config.getCompanyName(name), color);
		this.side = side;
	}

	@Override
	public void getInfo(Player player){
		TacoChatUtils cu = Mineopoly.getChatUtils();
		player.sendMessage(cu.formatMessage("&6---[" + getColorfulName() +"&6]---"));
		player.sendMessage(cu.formatMessage(color + "Owned&7: &b" + (isOwned() ? owner.getName() : "none")));
		player.sendMessage(cu.formatMessage(color + (isOwned() ? "Rent&7: " + getRent() : "Price&7: ")));
	}
	
	public void reset(){
		this.owned = false;
	}
	
	public int getRent(){
		if(this.isOwned()){
			Random random = new Random();
			switch(owner.ownedUtilities()){
				case 2:
					return 10 * (random.nextInt(6) + 1);
				default:
					return 4 * (random.nextInt(6) + 1);
			}
		}else{
			return 0;
		}
	}
	
	public boolean isOwned(){
		return owned;
	}
	
	public String getName(){
		return super.getName() + " Company";
	}
	
	public String getColorfulName(){
		return super.getColorfulName() + " Company";
	}
	
	public void setOwner(MineopolyPlayer player){
		this.owner = player;
		this.owned = true;
		player.addSection(this);		
	}
	
	public MineopolyPlayer getOwner(){
		return this.owner;
	}
	
	public int getPrice(){
		return price;
	}

	@Override
	public int getSide() {
		return side;
	}


	@Override
	public boolean isMortgaged() {
		return mortgaged;
	}

	@Override
	public void setMortgaged(boolean mortgage) {
		mortgaged = mortgage;
	}
}
