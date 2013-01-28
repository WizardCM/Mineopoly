package taco.mineopoly;

import java.io.File;
import java.io.IOException;

import org.bukkit.Server;

import taco.tacoapi.api.TacoPlugin;
import taco.mineopoly.cmds.JailCommandHandler;
import taco.mineopoly.cmds.MineopolyCommandHandler;
import taco.mineopoly.cmds.PropertyCommandHandler;
import taco.mineopoly.listener.MineopolyListener;

public class Mineopoly extends TacoPlugin{

	public static MineopolyConfig config;
	private MineopolyGame game;
	public static Mineopoly plugin;
	public static Server server;
	private MineopolyQueue queue;
	
	public void onDisable(){
		print("Disabled");
	}
	
	public void onEnable(){
		File file = new File(getDataFolder() + "/config.yml");
		config = new MineopolyConfig(file);
		plugin = this;
		server = getServer();
		queue = new MineopolyQueue();
		restartGame();
		registerCommand(new MineopolyCommandHandler());
		registerCommand(new PropertyCommandHandler());
		registerCommand(new JailCommandHandler());
		getServer().getPluginManager().registerEvents(new MineopolyListener(), this);
		if(config.useMetrics()){
			try {
				print("Starting Metrics (c) Hidendra...");
				Metrics metrics = new Metrics(this);
				metrics.start();
			} catch (IOException e) {
				print("Failed to start Metrics");
			}
		}
		print("Enabled");
	}
	
	public MineopolyQueue getQueue(){
		return queue;
	}
	
	public MineopolyGame getGame(){
		return game;
	}
	
	public void restartGame(){
		game = new MineopolyGame();
	}
	
}
