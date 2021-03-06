package taco.mineopoly;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import taco.mineopoly.cards.chance.ChanceCardSet;
import taco.mineopoly.cards.communitychest.CommunityChestCardSet;
import taco.mineopoly.sections.ChanceSection;
import taco.mineopoly.sections.CommunityChestSection;
import taco.mineopoly.sections.MineopolySection;
import taco.mineopoly.sections.Ownable;
import taco.mineopoly.sections.Property;
import taco.mineopoly.sections.Railroad;
import taco.mineopoly.sections.Utility;
import taco.mineopoly.sections.squares.*;
import taco.mineopoly.sections.tax.*;
import taco.tacoapi.TacoAPI;
import taco.tacoapi.api.object.WorldEditObject;

public class MineopolyBoard implements Iterable<MineopolySection>{

	private ArrayList<MineopolyPlayer> players;
	private ArrayList<MineopolySection> sections;
	private ChanceCardSet chanceCards;
	private CommunityChestCardSet communityChestCards;
	private MineopolyPot pot = new MineopolyPot();
	
	public MineopolyBoard(){
		Location origin = Mineopoly.config.getBoardOrigin();
		if(Mineopoly.config.getBoolean("mineopoly.schematic.needs_paste")){
			WorldEditObject we = new WorldEditObject(TacoAPI.getWorldEditPlugin());
			Mineopoly.plugin.broadcast("&ePasting MineopolyBoard. There may be some lag...");
			we.pasteSchematic(origin.getWorld().getName(), Mineopoly.plugin.getDataFolder() + "/mineopoly.schematic", origin);
			Mineopoly.plugin.broadcast("&ePaste Complete");
			Mineopoly.config.setBoolean("mineopoly.schematic.needs_paste", false);
		}
		players = new ArrayList<MineopolyPlayer>();
		sections = new ArrayList<MineopolySection>();
		initSections();
		initCardSets();
	}
	
	private void initSections() {
		// property(name, mColor, side, buyPrice, rent[]{site, 1h, 2h, 3h, 4h, hotel})
		sections.add(new GoSquare());
		sections.add(new Property(1, "mediterranean_ave", MineopolyColor.PURPLE, 0, 60, new int[]{2, 10, 30, 90, 160, 250}));
		sections.add(new CommunityChestSection(2, 0));
		sections.add(new Property(3, "baltic_ave", MineopolyColor.PURPLE, 0, 60, new  int[]{4, 20, 60, 180, 320, 450}));
		sections.add(new IncomeTax());
		sections.add(new Railroad("reading", 0));
		sections.add(new Property(6, "oriental_ave", MineopolyColor.LIGHT_BLUE, 0, 100, new int[]{6, 30, 90, 270, 400, 550}));
		sections.add(new ChanceSection(7, 0));
		sections.add(new Property(8, "vermont_ave", MineopolyColor.LIGHT_BLUE, 0, 100, new int[]{6, 30, 90, 270, 400, 550}));
		sections.add(new Property(9, "connecticut_ave", MineopolyColor.LIGHT_BLUE, 0, 120, new int[]{8, 40, 100 ,300, 450, 600}));
		sections.add(new JailSquare());
		sections.add(new Property(11, "st_charles_place", MineopolyColor.MAGENTA, 1, 140, new int[]{10, 50, 150, 450, 625, 750}));
		sections.add(new Utility(12, "electric", '1', 1));
		sections.add(new Property(13, "states_ave", MineopolyColor.MAGENTA, 1, 140, new int[]{10, 50, 150, 450, 625, 750}));
		sections.add(new Property(14, "virginia_ave", MineopolyColor.MAGENTA, 1, 160, new int[]{12, 60, 180, 500, 700, 900}));
		sections.add(new Railroad("pennsylvania", 1));
		sections.add(new Property(16, "st_james_place", MineopolyColor.ORANGE, 1, 180, new int[]{14, 70, 200, 550, 750, 950}));
		sections.add(new CommunityChestSection(17, 1));
		sections.add(new Property(18, "tennessee_ave", MineopolyColor.ORANGE, 1, 180, new int[]{14, 70, 200, 550, 750, 950}));
		sections.add(new Property(19, "new_york_ave", MineopolyColor.ORANGE, 1, 200, new int[]{16, 80, 220, 600, 800, 1000}));
		sections.add(new FreeParkingSquare());
		sections.add(new Property(21, "kentucky_ave", MineopolyColor.RED, 2, 220, new int[]{18, 90, 250, 700, 875, 1050}));
		sections.add(new ChanceSection(22, 2));
		sections.add(new Property(23, "indiana_ave", MineopolyColor.RED, 2, 220, new int[]{18, 90, 250, 700, 875, 1050}));
		sections.add(new Property(24, "illinois_ave", MineopolyColor.RED, 2, 240, new int[]{20, 100, 300, 750, 925, 1100}));
		sections.add(new Railroad("b_o", 2));
		sections.add(new Property(26, "atlantic_ave", MineopolyColor.YELLOW, 2, 260, new int[]{22, 110, 330, 800, 975, 1150}));
		sections.add(new Property(27, "ventor_ave", MineopolyColor.YELLOW, 2, 260, new int[]{22, 110, 330, 800, 975, 1150}));
		sections.add(new Utility(28, "water", '1', 2));
		sections.add(new Property(29, "marvin_gardens", MineopolyColor.YELLOW, 2, 280, new int[]{22, 120, 360, 850, 1025, 1200}));
		sections.add(new GoToJailSquare());
		sections.add(new Property(31, "pacific_ave", MineopolyColor.GREEN, 3, 300, new int[]{26, 130, 390, 900, 110, 1275}));
		sections.add(new Property(32, "north_carolina_ave", MineopolyColor.GREEN, 3, 300, new int[]{26, 130, 390, 900, 110, 1275}));
		sections.add(new CommunityChestSection(33, 3));
		sections.add(new Property(34, "pennsylvania_ave", MineopolyColor.GREEN, 3, 320, new int[]{28, 150, 450, 1000, 1200, 1400}));
		sections.add(new Railroad("short_line", 3));
		sections.add(new ChanceSection(36, 3));
		sections.add(new Property(37, "park_place", MineopolyColor.BLUE, 3, 350, new int[]{35, 175, 500, 1100, 1300, 1500}));
		sections.add(new LuxuryTax());
		sections.add(new Property(39, "boardwalk", MineopolyColor.BLUE, 3, 400, new int[]{50, 200, 600, 1400, 1700, 2000}));
	}
	
	public void initCardSets(){
		chanceCards = new ChanceCardSet();
		communityChestCards = new CommunityChestCardSet();
	}
	
	public ChanceCardSet getChanceCards(){
		return chanceCards;
	}
	
	public CommunityChestCardSet getCommunityChestCards(){
		return communityChestCards;
	}
	
	public MineopolySection getSection(String name){
		for(MineopolySection ms : this){
			if(ms.getName().replace(" ", "_").equalsIgnoreCase(name)){
				return ms;
			}
		}
		return null;
	}
	
	public MineopolySection getSection(int id){
		if(id >= 40)
			id = id - (40 * (id % 40));
		else if(id < 0)
			return null;
		return sections.get(id);
	}
	
	public ArrayList<MineopolySection> getAllSections(){
		return sections;
	}
	
	public ArrayList<Ownable> getOwnableSections(){
		ArrayList<Ownable> properties = new ArrayList<Ownable>();
		for(MineopolySection section : sections){
			if(section instanceof Ownable){
				properties.add((Ownable) section);
			}
		}
		return properties;
	}
	
	public ArrayList<Railroad> getRailroads(){
		ArrayList<Railroad> railroads = new ArrayList<Railroad>();
		for(MineopolySection section : sections){
			if(section instanceof Railroad){
				railroads.add((Railroad) section);
			}
		}
		return railroads;
	}
	
	public ArrayList<Utility> getUtilities(){
		ArrayList<Utility> utilities = new ArrayList<Utility>();
		utilities.add((Utility) this.sections.get(12));
		utilities.add((Utility) this.sections.get(28));
		return utilities;
	}

	public ArrayList<MineopolyPlayer> getPlayers(){
		return this.players;
	}
	
	public MineopolyPlayer getPlayer(Player p){
		for(MineopolyPlayer mp : players){
			if(mp.getName().equalsIgnoreCase(p.getName())){
				return mp;
			}
		}
		return null;
	}
	
	public void addPlayer(MineopolyPlayer player){
		this.players.add(player);
	}
	
	public void removePlayer(MineopolyPlayer player){
		for(MineopolyPlayer p : players){
			if(p.getName().equalsIgnoreCase(player.getName())){
				players.remove(p);
				break;
			}
		}
		if(Mineopoly.plugin.getGame().getBoard().getPlayers().size() == 0){
			Mineopoly.plugin.getGame().end();
			Mineopoly.plugin.broadcast("&eThe Mineopoly game has ended");
			if(Mineopoly.plugin.getGame().canStart()){
				Mineopoly.plugin.broadcast("&eThe next game will start soon");
			}else{
				Mineopoly.plugin.broadcast("&eThe next game will start when there are enough players in the queue");
			}
		}
	}
	
	public MineopolyPot getPot(){
		return this.pot;
	}
	
	public Location getOrigin(){
		return Mineopoly.config.getBoardOrigin();
	}

	@Override
	public Iterator<MineopolySection> iterator() {
		return sections.iterator();
	}
}
