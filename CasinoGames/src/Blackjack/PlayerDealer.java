package Blackjack;

import Blackjack.BlackjackGame.GameAction;

public class PlayerDealer extends Player {
	public PlayerDealer()
	{
		super();
		this.balance = 1000000; //todo-remove hardcode. This is house' money
		this.name = "Dealer";
	}
	
	/// return true if the action is a valid move
		///        false if the action is an invalid move
		public boolean action(GameAction act, int deckNumber)
		{
			deckNumber = 0; //just default it to 0 since dealer has only 1 deck
			boolean result = true;
			
			switch (act) {
				case HIT:
					if ( bjGame.getTotalCardPoints(getDeckAt(deckNumber)) >= 17 )
						return false;
					receiveCard(this.bjGame.houseDeckOfCards.dealOut(), deckNumber);
					break;
				case STAND:
					if ( bjGame.getTotalCardPoints(getDeckAt(deckNumber)) < 17 )
						return false;
					break;
				default :
					return false;
			}
			return result;
		}
		
		public void collectWinning(int amt)
		{
			this.balance += amt;
		}
		
		public void loseBet(int amt)
		{
			this.balance -= amt;
		}
}