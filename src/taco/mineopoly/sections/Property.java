package taco.mineopoly.sections;

import org.bukkit.entity.Player;

import taco.mineopoly.Mineopoly;
import taco.mineopoly.MineopolyColor;
import taco.mineopoly.MineopolyPlayer;
import taco.tacoapi.api.TacoChatUtils;

/**
 * Represents a property space on the board. Implements {@link taco.mineopoly.sections.Ownable Ownable}
 * @author Taco
 *
 */
public class Property extends MineopolySection implements Ownable, CardinalSection{

	private boolean mortgaged = false;
	protected int price, side;
	private int housePrice = (getSide() + 1) * 50;
	private int hotelPrice = housePrice;
	private int houses;
	private int r, h, h2, h3, h4, hotel;
	protected boolean hasHotel = false;
	private MineopolyPlayer owner;
	protected MineopolyColor mColor;
	private boolean owned = false;

	public Property(int id, String name, MineopolyColor color, int side, int price, int[] rent) {
		super(id, Mineopoly.config.getPropertyName(name), color.getChar());
		this.mColor = color;
		this.side = side;
		this.price = price;
		setRent(rent[0], rent[1], rent[2], rent[3], rent[4], rent[5]);
	}
	
	public int getRent(){
		if(isOwned()){
			if(!hasHotel){
				switch(houses){
					case 0:
						return r;
					case 1:
						return h;
					case 2:
						return h2;
					case 3:
						return h3;
					case 4:
						return h4;
					default:
						return r;
				}
			}else{
				return hotel;
			}
		}else return 0;
	}
	
	public void reset(){
		this.mortgaged = false;
		this.houses = 0;
		this.owned = false;
		this.hasHotel = false;
	}
	
	public MineopolyColor getColor(){
		return mColor;
	}
	
	protected void setRent(int r, int h, int h2, int h3, int h4, int hotel){
		this.h = h;
		this.h2 = h2;
		this.h3 = h3;
		this.h4 = h4;
		this.hotel = hotel;
	}
	
	public void getInfo(Player player){
		TacoChatUtils cu = Mineopoly.getChatUtils();
		player.sendMessage(cu.formatMessage("&6---[" + getColorfulName() +"&6]---"));
		player.sendMessage(cu.formatMessage(mColor + "Owner&7:&b " + (isOwned() ? owner.getName() : "none")));
		player.sendMessage(cu.formatMessage(mColor + (isOwned() ? "Rent&7: &2" + getRent() : "Price&7: &2" + getPrice())));
		if(getHouses() > 0 && !hasHotel()) player.sendMessage(cu.formatMessage(mColor + "Houses&7:&b " + getHouses()));
		if(hasHotel) player.sendMessage(cu.formatMessage(mColor + "Hotel&7:&b " + hasHotel()));
	}
	
	public boolean hasHotel(){
		return this.hasHotel;
	}
	
	public int getHouses(){
		return this.houses;
	}
	
	public void setMortgaged(boolean mortgaged){
		this.mortgaged = mortgaged;
	}
	
	public boolean isMortgaged(){
		return this.mortgaged;
	}
	
	public int getPrice(){
		return this.price;
	}
	
	public int getHousePrice(){
		return this.housePrice;
	}
	
	public int getHotelPrice(){
		return this.hotelPrice;
	}
	
	public boolean isOwned(){
		return this.owned;
	}
	
	public MineopolyPlayer getOwner(){
		return this.owner;
	}
	
	public void setOwner(MineopolyPlayer player){
		this.owner = player;
		this.owned = true;
		player.addSection(this);
	}
	
	public void addHotel(){
		this.hasHotel = true;
	}
	
	public void addHouse(){
		this.houses += 1;
	}

	@Override
	public int getSide() {
		return side;
	}
}
