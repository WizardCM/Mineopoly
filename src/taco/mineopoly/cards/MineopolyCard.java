package taco.mineopoly.cards;

import taco.mineopoly.Mineopoly;
import taco.mineopoly.MineopolyPlayer;

public abstract class MineopolyCard {

	private String description, name, action;
	private boolean valid = false;
	
	public MineopolyCard(String name, String description, String action) {
		this.description = description;
		this.name = name;
		this.action = action;
		validate();
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public void readDescription(MineopolyPlayer player){
		String color = "&b";
		Mineopoly.plugin.getGame().getChannel().sendMessage(color  + player.getName() + " &3drew a " + name + " &3card, it says:");
		Mineopoly.plugin.getGame().getChannel().sendMessage(color + replaceNames(description, color));
	}
	
	private String replaceNames(String message, String color){
		for(String s : message.split(" ")){
			if(s.charAt(0) == '%'){
				if(Mineopoly.config.contains("mineopoly.names.properties." + s.substring(1)))
					message = message.replaceAll("%" + name, Mineopoly.config.getPropertyName(s.substring(1)) + color);
				if(Mineopoly.config.contains("mineopoly.names.railroads." + s.substring(1)))
					message = message.replaceAll("%" + name, Mineopoly.config.getRailroadName(s.substring(1)) + color);
				if(Mineopoly.config.contains("mineopoly.names.companies." + s.substring(1)))
					message = message.replaceAll("%" + name, Mineopoly.config.getCompanyName(s.substring(1)) + color);
			}
		}
		//message = message.replaceAll("%" + name, section.getColorfulName() + color);
		return message;
	}
	
	public void action(MineopolyPlayer player){
		String[] split = action.split(" ");
		if(split.length == 1){
			if(split[0].equalsIgnoreCase("jail")){
				player.setJailed(true);
			}
		}else if(split.length == 2){
			int param = 0;
			if(Mineopoly.getChatUtils().isNum(split[1]))
				param = Integer.parseInt(split[1]);
			if(split[0].equalsIgnoreCase("give")){
				player.addMoney(param);
			}else if(split[0].equalsIgnoreCase("move")){
				player.move(param);
			}else if(split[0].equalsIgnoreCase("payall")){
				for(MineopolyPlayer p : Mineopoly.plugin.getGame().getBoard().getPlayers())
					player.payPlayer(p, param);
			}else if(split[0].equalsIgnoreCase("paypot")){
				player.payPot(param);
			}else if(split[0].equalsIgnoreCase("payplayer")){
				for(MineopolyPlayer p : Mineopoly.plugin.getGame().getBoard().getPlayers()){
					p.payPlayer(player, param);
					p.sendMessage("&3You paid &b" + player.getName() + " &2" + param);
				}
			}else if(split[0].equalsIgnoreCase("take")){
				player.takeMoney(param);
			}else if(split[0].equalsIgnoreCase("moveto")){
				player.setCurrentSection(Mineopoly.plugin.getGame().getBoard().getSection(param));
			}else if(split[0].equalsIgnoreCase("movenearest")){
				if(split[1].equalsIgnoreCase("railroad"))
					player.moveToNearestRailroad();
				else if(split[0].equalsIgnoreCase("utility"))
					player.moveToNearestUtility();
			}
		}else{
			if(split[0].equalsIgnoreCase("repairs")){
				int houses = Integer.parseInt(split[1]);
				int hotels = Integer.parseInt(split[2]);
				player.payPot((houses * player.getHouses()) + (hotels * player.getHotels()));
			}
		}
	}
	
	private void validate(){
		String[] args = action.split(" ");
		if(args.length == 1){
			if(args[0].equalsIgnoreCase("jail")){
				valid = true;
			}
		}else{
			if(args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("move") || 	args[0].equalsIgnoreCase("payall") ||
					args[0].equalsIgnoreCase("payplayer") || args[0].equalsIgnoreCase("paypot") || args[0].equalsIgnoreCase("take") || args[0].equalsIgnoreCase("moveto")){
				if(Mineopoly.getChatUtils().isNum(args[1])){
					valid = true;
				}
			}else if(args[0].equalsIgnoreCase("movenearest")){
				if(args[1].equalsIgnoreCase("railroad") || args[1].equalsIgnoreCase("utility")){
					valid = true;
				}
			}else if(args[0].equalsIgnoreCase("repairs")){
				if(Mineopoly.getChatUtils().isNum(args[1]) && Mineopoly.getChatUtils().isNum(args[1])){
					valid = true;
				}
			}
		}
	}
	
	public boolean isValid(){
		return valid;
	}

}
