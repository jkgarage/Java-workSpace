package Blackjack;

import java.util.*;

import Blackjack.BlackjackGame.*;

public class Player {
	int balance; //the $$ the player still has
	int bet; //the $$ the player bet
	
	ArrayList<DeckOfCards> decksOfcards; //normally 1 user will have only 1 deck of card, until he splits
	BlackjackGame bjGame;
	String name; //just to identify the player
	
	public Player ()
	{
		this(0,"NoName");
	}
	
	public Player (int money, String name)
	{
		this.balance = money;
		this.name = name;
		
		decksOfcards = new ArrayList<DeckOfCards>();
		decksOfcards.add(new DeckOfCards(0));
	}
	
	public boolean subcribeToGame(BlackjackGame bjg)
	{
		this.bjGame = bjg;
		return this.bjGame.addPlayer(this);
	}
	
	public void unsubcribeFromGame()
	{
		if (this.bjGame == null) return;
		this.bjGame.removePlayer(this);
	}
	
	//default value for deckNumber is 0, ie. player has only 1 deck
	void receiveCard(Card c, int deckNumber)
	{
		decksOfcards.get(deckNumber).receiveCard(c);
	}
	
	public void clearCards()
	{
		decksOfcards = new ArrayList<DeckOfCards>();
		decksOfcards.add(new DeckOfCards(0));
	}
	
	public boolean placeBet (int amt)
	{
		if (amt > balance)
			return false;
		this.bet = amt;
		return true;
	}
	
	public int collectWinning()
	{
		int result = this.bet;
		this.balance += bet;
		this.bet = 0;
		return result;
	}
	
	public int collectWinningBlackjack()
	{
		int result = (int) Math.floor(this.bet * 1.5);
		this.balance += bet;
		this.bet = 0;
		return result;
	}
	
	public int loseBet()
	{
		int result = this.bet;
		this.balance -= this.bet;
		this.bet = 0;
		return result;
	}
	
	public void clearBet()
	{
		this.bet = 0;
	}
	
	public String getName() { return name;}
	public int getBalance() { return balance; }
	public int getBet()     { return bet; }
	public ArrayList<DeckOfCards> getDecks()
	{
		return decksOfcards;
	}
	
	public DeckOfCards getDeckAt(int deckNumber)
	{
		return decksOfcards.get(deckNumber);
	}

	/// return true if the action is a valid move
	///        false if the action is an invalid move
	public boolean action(GameAction act, int deckNumber)
	{
		boolean result = true;
		DeckOfCards deck = getDeckAt(deckNumber);
		
		switch (act) {
			case HIT:
				if ( bjGame.getTotalCardPoints(deck) >= 21 )
					return false;
				receiveCard(this.bjGame.houseDeckOfCards.dealOut(), deckNumber);
				break;
			case DOUBLE:
				if ( bjGame.getTotalCardPoints(deck) >= 21 || this.bet*2 > this.balance)
					return false;
					receiveCard(this.bjGame.houseDeckOfCards.dealOut(), deckNumber);
				this.bet *= 2;
				break;
			case SPLIT:
				if ( !deck.isPairOfSameCard() )
					return false;
				DeckOfCards newDeck = new DeckOfCards(0);
				newDeck.receiveCard( deck.dealOut() );
				decksOfcards.add(newDeck);
				break;
			case INSURANCE:
				//todo - add action here
				break;
			default :
				break;
		}
		return result;
	}
}