package taco.mineopoly.cmds.property;

import org.bukkit.entity.Player;

import taco.mineopoly.Mineopoly;
import taco.mineopoly.MineopolyPlayer;
import taco.mineopoly.messages.CannotPerformActionMessage;
import taco.mineopoly.messages.GameNotInProgressMessage;
import taco.mineopoly.messages.InvalidTurnMessage;
import taco.mineopoly.messages.NotPlayingGameMessage;
import taco.mineopoly.messages.SectionNotFoundMessage;
import taco.mineopoly.sections.MineopolySection;
import taco.mineopoly.sections.Property;
import taco.tacoapi.api.command.TacoCommand;

public class PropertyAddHotelCommand extends TacoCommand {

	@Override
	protected String[] getAliases() {
		return new String[]{"add-hotel"};
	}

	@Override
	public boolean onConsoleCommand(String[] arg0) {
		return false;
	}

	@Override
	public boolean onPlayerCommand(Player player, String[] args) {
		if(Mineopoly.plugin.getGame().isRunning()){
			if(Mineopoly.plugin.getGame().hasPlayer(player)){
				MineopolyPlayer mp = Mineopoly.plugin.getGame().getBoard().getPlayer(player);
				if(mp.hasTurn()){
					if(args.length == 0){
						if(mp.getCurrentSection() instanceof Property){
							if(mp.canAddHotel((Property) mp.getCurrentSection())){
								Property prop = (Property) mp.getCurrentSection();
								prop.addHouse();
								Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + mp.getName() + " &3added a house to " + prop.getColorfulName(), mp);
								mp.sendMessage("&3You added a &ahouse &3to " + prop.getColorfulName());
							}else{
								mp.sendMessage(new CannotPerformActionMessage("add a house to that right now"));
							}
						}else{
							mp.sendMessage(new CannotPerformActionMessage("add a house to that"));
						}
					}else{ 
						MineopolySection section;
						if(Mineopoly.getChatUtils().isNum(args[0]))
							section = Mineopoly.plugin.getGame().getBoard().getSection(Integer.parseInt(args[0]));
						else
							section = Mineopoly.plugin.getGame().getBoard().getSection(args[0]);
						if(section == null){
							mp.sendMessage(new SectionNotFoundMessage());
						}else{
							if(section instanceof Property){
								if(mp.canAddHouse((Property) section)){
									Property prop = (Property) section;
									prop.addHouse();
									Mineopoly.plugin.getGame().getChannel().sendMessage("&b" + mp.getName() + " &3added a house to " + prop.getColorfulName(), mp);
									mp.sendMessage("&3You added a &ahouse &3to " + prop.getColorfulName());
								}else{
									mp.sendMessage(new CannotPerformActionMessage("add a house to that right now"));
								}
							}else{
								mp.sendMessage(new CannotPerformActionMessage("add a house to that"));
							}
						}
					}
				}else{
					player.sendMessage(new InvalidTurnMessage() + "");
				}
			}else{
				player.sendMessage(new NotPlayingGameMessage() + "");
			}
		}else{
			player.sendMessage(new GameNotInProgressMessage().getMessage());
		}
		return true;
	}

}
