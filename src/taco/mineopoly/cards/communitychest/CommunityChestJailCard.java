package taco.mineopoly.cards.communitychest;

import taco.mineopoly.MineopolyPlayer;

public class CommunityChestJailCard extends CommunityChestCard {

	public CommunityChestJailCard() {
		super("Get out of Jail Free card. You may use this card to get out of jail", "");
	}

	@Override
	public void action(MineopolyPlayer player) {
		player.giveCommunityChestJailCard();
	}

}
