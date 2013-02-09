package taco.mineopoly.sections;

import org.bukkit.entity.Player;

import taco.mineopoly.Mineopoly;
import taco.mineopoly.MineopolyPlayer;
import taco.tacoapi.api.TacoChatUtils;

/**
 * Represents a railroad space on the board.  Implements {@link taco.mineopoly.sections.Ownable Ownable}
 * @author Taco
 *
 */
public class Railroad extends MineopolySection implements Ownable, CardinalSection{

	protected int side, price = 200;
	protected boolean owned = false;
	protected MineopolyPlayer owner;
	private boolean mortgaged;

	
	public Railroad(String name, int side) {
		super((side * 10) + 5, Mineopoly.config.getRailroadName(name), '8');
		this.side = side;
	}

	@Override
	public boolean isOwned() {
		return owned;
	}

	@Override
	public MineopolyPlayer getOwner() {
		return owner;
	}
	
	public String getName(){
		return super.getName() + " Railroad";
	}
	
	public String getColorfulName(){
		return super.getColorfulName() + " Railroad";
	}

	@Override
	public void setOwner(MineopolyPlayer player) {
		this.owner = player;
		this.owned = true;
		player.addSection(this);
	}
	
	public void reset(){
		this.owned = false;
	}

	public void getInfo(Player player){
		TacoChatUtils cu = Mineopoly.getChatUtils();
		player.sendMessage(cu.formatMessage("&6---[" + getColorfulName() +"&6]---"));
		player.sendMessage(cu.formatMessage(color + "Owned&7: &b" + (isOwned() ? owner.getName() : "none")));
		player.sendMessage(cu.formatMessage(color + (isOwned() ? "Rent&7: " + getRent() : "Price&7: ")));
	}
	
	public int getRent(){
		if(isOwned()){
			switch(owner.ownedRailRoads()){
			case 2:
				return 50;
			case 3:
				return 75;
			case 4:
				return 100;
			default:
				return 25;
			}
		}else return 0;
	}

	@Override
	public int getPrice() {
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
