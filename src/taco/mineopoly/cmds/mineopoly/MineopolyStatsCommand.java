package taco.mineopoly.cmds.mineopoly;

import org.bukkit.entity.Player;


import taco.tacoapi.api.command.TacoCommand;
import taco.tacoapi.api.messages.PlayerNotOnlineMessage;
import taco.tacoapi.api.messages.TooManyArgumentsMessage;
import taco.mineopoly.Mineopoly;
import taco.mineopoly.Permissions;
import taco.mineopoly.messages.GameNotInProgressMessage;
import taco.mineopoly.messages.NotPlayingGameMessage;

public class MineopolyStatsCommand extends TacoCommand{

	/*
	 * This command only requires the sender to be playing mineopoly
	 * if they're trying to view their own stats
	 */
	
	@Override
	public boolean onPlayerCommand(Player player, String[] args) {
		if(args.length == 0){
			if(Mineopoly.plugin.getGame().isRunning()){
				if(Mineopoly.plugin.getGame().hasPlayer(player)){
					Mineopoly.plugin.getGame().getBoard().getPlayer(player).getInfo(player);
					return true;
				}else{
					player.sendMessage(new NotPlayingGameMessage() + "");
					return true;
				}
			}else{
				player.sendMessage(new GameNotInProgressMessage() + "");
				return true;
			}
		}else if(args.length == 1){
			if(Mineopoly.plugin.getGame().isRunning()){
				Player p = Mineopoly.plugin.getServer().getPlayer(args[0]);
				if(p != null){
					if(Mineopoly.plugin.getGame().hasPlayer(player)){
						Mineopoly.plugin.getGame().getBoard().getPlayer(p).getInfo(player);
						return true;
					}else{
						if(player.hasPermission(Permissions.VIEW_PLAYER_STATS.toString())){
							Mineopoly.plugin.getGame().getBoard().getPlayer(p).getInfo(player);
							return true;
						}else{
							return false;
						}
					}
				}else{
					player.sendMessage(new PlayerNotOnlineMessage(args[0]) + "");
					return true;
				}
			}else{
				player.sendMessage(new GameNotInProgressMessage() + "");
				return true;
			}
		}else{
			player.sendMessage(new TooManyArgumentsMessage("/mineopoly stats [player]") + "");
			return true;
		}
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		if(args.length == 0){
			Mineopoly.plugin.print("You aren't playing Mineopoly");
		}else{
			//if player exists, if playing, get stats
		}
		return true;
	}

	@Override
	protected String[] getAliases() {
		return new String[]{"stats", "s"};
	}

}
