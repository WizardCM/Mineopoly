package taco.mineopoly;

import java.util.ArrayList;

public class MineopolyChatChannel {

	private ArrayList<MineopolyChannelListener> listeners = new ArrayList<MineopolyChannelListener>();
	
	public void sendMessage(String message){
		for(MineopolyChannelListener p : listeners){
			p.sendMessage(Mineopoly.getChatUtils().formatMessage(message));
		}
	}
	
	public void sendMessage(String message, MineopolyPlayer exempt){
		for(MineopolyChannelListener p : listeners){
			if(!p.getName().equalsIgnoreCase(exempt.getName()))
				p.sendMessage(Mineopoly.getChatUtils().formatMessage(message));
		}
	}
	
	public void addPlayer(MineopolyChannelListener player){
		listeners.add(player);
	}
	
	public void removePlayer(String name){
		for(MineopolyChannelListener p : listeners){
			if(p.getName().equalsIgnoreCase(name)){
				listeners.remove(p);
			}
		}
	}
	
	public boolean isListeningToChannel(String name){
		for(MineopolyChannelListener p : listeners){
			if(p.getName().equalsIgnoreCase(name)){
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<MineopolyChannelListener> getListeners(){
		return listeners;
	}
	
}
