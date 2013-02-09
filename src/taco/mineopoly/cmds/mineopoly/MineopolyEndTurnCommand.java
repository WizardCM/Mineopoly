package taco.mineopoly.cmds.mineopoly;

import org.bukkit.entity.Player;

import taco.mineopoly.Mineopoly;
import taco.mineopoly.MineopolyPlayer;
import taco.mineopoly.messages.GameNotInProgressMessage;
import taco.mineopoly.messages.InvalidTurnMessage;
import taco.mineopoly.messages.MustRollFirstMessage;
import taco.mineopoly.messages.NotPlayingGameMessage;
import taco.tacoapi.api.command.TacoCommand;

public class MineopolyEndTurnCommand extends TacoCommand {

	@Override
	protected String[] getAliases() {
		return new String[]{"end-turn", "et"};
	}

	@Override
	public boolean onConsoleCommand(String[] args) {
		return false;
	}

	@Override
	public boolean onPlayerCommand(Player player, String[] args) {
		if(Mineopoly.plugin.getGame().isRunning()){
			if(Mineopoly.plugin.getGame().hasPlayer(player)){
				MineopolyPlayer p = Mineopoly.plugin.getGame().getBoard().getPlayer(player);
				if(p.hasTurn()){
					if(p.hasRolled()){
						if(p.getMoney() < 0){
							p.sendMessage("&cYou are in debt (negative money) you must gain money before ending your turn. Or you can quit with &3/mineopoly quit");
						}else{
							p.setTurn(false, false);
						}
					}else{
						p.sendMessage(new MustRollFirstMessage("ending you turn"));
					}
				}else{
					p.sendMessage(new InvalidTurnMessage());
				}
			}else{
				player.sendMessage(new NotPlayingGameMessage() + "");
			}
		}else{
			player.sendMessage(new GameNotInProgressMessage() + "");
		}
		return true;
	}

}
