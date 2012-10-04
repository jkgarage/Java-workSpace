package Blackjack;

import java.util.ArrayList;

public class BlackjackGame {
	public static int BLACK_JACK = 999;
	public enum GameAction { HIT, STAND, DOUBLE, SPLIT, INSURANCE };
	public enum GameResult { WIN, LOSE, PUSH };
	DeckOfCards houseDeckOfCards;
	
	private PlayerDealer dealer;
	ArrayList<Player> players;
	
	public BlackjackGame (int nod, ArrayList<Player> playrs)
	{
		houseDeckOfCards = new DeckOfCards(nod);
		houseDeckOfCards.shuffleCards();
		
		this.players = playrs;
	}
	
	public void handPlay()
	{
		//deal-out 2 rounds of cards
		for (int i = 0; i < 2; i++ )
			for (Player p : players)
			{
				p.receiveCard( houseDeckOfCards.dealOut(), 0 );
			}
	}
	
	public boolean addPlayer(Player p)
	{
		boolean result = true;
		
		if (players == null)
			players = new ArrayList<Player>();
		if ( p instanceof PlayerDealer )
			if ( this.dealer != null )
				return false;
			else
				this.dealer = (PlayerDealer) p;
		players.add(p);
		
		return result;
	}
	
	public void removePlayer(Player p)
	{
		players.remove(p);
	}
	
	public ArrayList<Player> getPlayers()
	{
		return players;
	}
	
	//return BlackjackGame.BLACK_JACK if the cards are blackjack
	public int getTotalCardPoints(DeckOfCards deck)
	{
		int result = 0;
		int cardCount = 0;
		boolean hasAce = false;
		
		for ( Card c:deck.getCards() )
		{
			cardCount ++;
			result += c.getPoint();
			if (c.getPoint() == 1) hasAce = true;
			//todo - ACE can be 1 or 11
		}
		
		if (cardCount == 2 && hasAce && result == 11)
			result = BLACK_JACK;
		else
			if ( hasAce && result <= 11)
				result += 10;
		return result;
	}
	
	/************************************/
	//display whatever on the table
	public String toString()
	{
		String result = new String();
		result += "HouseCards:" + houseDeckOfCards + "\n";
		
		for (Player p:players)
		{
			for (DeckOfCards d:p.decksOfcards)
			result += "Playr(" + p.name + "):" + d + "\n";
		}
		return result;
	}
	
	//display whatever on the table
	public String displayPlayerCards()
	{
		String result = new String();
		
		for (Player p:players)
		{
			for (DeckOfCards d:p.decksOfcards)
			result += "Playr(" + p.name + "):" + d + "\n";
		}
		return result;
	}
	
	public GameResult checkGameResult(Player p, int deckNumber)
	{
		GameResult result = GameResult.PUSH;
		int dealerPoints = getTotalCardPoints( this.dealer.getDecks().get(0) );
		int playerDeckPts = -1;
		DeckOfCards d = p.getDeckAt(deckNumber);
		
		if ( !(p instanceof PlayerDealer) )
		{
			playerDeckPts = getTotalCardPoints( d );
			if (playerDeckPts == BLACK_JACK)
			{
				if (dealerPoints == BLACK_JACK)
				{
					result = GameResult.PUSH;
					p.clearBet();
				}
				else
				{
					result = GameResult.WIN;
					dealer.loseBet( p.collectWinningBlackjack() );
				}
			}
			else if (playerDeckPts > 21)
			{
				result = GameResult.LOSE;
				dealer.collectWinning(p.loseBet());
			}
			else if (dealerPoints > 21)
			{
				result = GameResult.WIN;
				dealer.loseBet( p.collectWinning() );
			}
			else if (dealerPoints < playerDeckPts)
			{
				result = GameResult.WIN;
				dealer.loseBet( p.collectWinning() );
			}
			else if (dealerPoints == playerDeckPts)
			{
				result = GameResult.PUSH;
				p.clearBet();
			}
			else 
			{
				result = GameResult.LOSE;
				dealer.collectWinning(p.loseBet());
			}
		}
		else
			return null;
		return result;
	}
	
	public boolean checkIfBursted(Player p, int deckNumber)
	{
		boolean result = false;
		DeckOfCards d = p.getDeckAt(deckNumber);
		int playerDeckPts = getTotalCardPoints( d );

		if (playerDeckPts > 21)
			return true;

		return result;
	}
	
	public boolean checkIfBlackJack(Player p, int deckNumber)
	{
		boolean result = false;
		DeckOfCards d = p.getDeckAt(deckNumber);
		int playerDeckPts = getTotalCardPoints( d );

		if (playerDeckPts == BLACK_JACK)
			return true;

		return result;
	}
}