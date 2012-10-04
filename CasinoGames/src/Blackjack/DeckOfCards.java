package Blackjack;

import java.util.ArrayList;

public class DeckOfCards {
	private ArrayList<Card> cards;
	//private int numberOfDecks;
	static final int DECK_SIZE = 52;
	
	public DeckOfCards(int numberOfDecks) {
		//this.numberOfDecks = numberOfDecks;
		cards = initializeNewDecks(numberOfDecks);
	}
	
	public DeckOfCards(ArrayList<Card> deckOfCards) {
		cards = deckOfCards;
	}
	
	//initialize a new deck of cards, depends on how many decks to mix
	private ArrayList<Card> initializeNewDecks(int numberOfDecks)
	{
		ArrayList<Card> deckOfCards = new ArrayList<Card> ();
		Card c;
		
		for (int i = 0; i < numberOfDecks; i++)
		{
			for (Card.Number number : Card.Number.values()) 
				for (Card.Face face : Card.Face.values()) 
				{  
					c = new Card(number, face);
					deckOfCards.add(c);
				}
		}
		
		return deckOfCards;
	}
	
	
	//shuffle the current deck of cards
	//to remove
	void shuffleCards_old()
	{
		ArrayList<Card> tempDeck = new ArrayList<Card> (cards.size());
		
		int randIndex = 0;
		int direction = 0; //determine the direction to place new shuffled card
		
		for(int i = 0; i < cards.size(); i++)
		{
			randIndex = (int) Math.round( Math.random() * (cards.size()-1) );

			if (tempDeck.get(randIndex) != null) //throws error here since object is null
			{
				direction = (int) Math.round( Math.random() ); //0-go down, 1-go up
				if (direction == 0)
					while (tempDeck.get(randIndex) == null)
						randIndex = (randIndex++) % (cards.size()-1);
				else
					while (tempDeck.get(randIndex) == null)
					{
						if (--randIndex < 0)
							randIndex = cards.size() - 1;
					}
			}
			
			tempDeck.set (randIndex, cards.get(i) );
		}
		cards = tempDeck;
	}
	
	//shuffle the current deck of cards
	//to remove
	void shuffleCards()
	{
		int randIndex = 0;

		for(int i = 0; i < cards.size(); i++)
		{
			randIndex = (int) Math.round( Math.random() * (cards.size()-1-i) );
			randIndex = i + randIndex;
			
			//swap the current cards with the new randIndex
			Card temp = cards.get(i);
			cards.set(i, cards.get(randIndex));
			cards.set(randIndex, temp);
		}
	}
	
	/// return whether the deck has pair of same card value (eg. pair of 2, or paid of Kings)
	boolean isPairOfSameCard()
	{
		if (cards.size() == 2 && 
		   (cards.get(0).number.getName() == cards.get(1).number.getName()) )
			return true;
		else return false;
	}

	//deal out a single card off the deck
	Card dealOut()
	{
		if (cards.size() == 0) return null;
		return cards.remove(0);
	}
	
	void receiveCard (Card c)
	{
		cards.add(c);
	}
	
	public String toString()
	{
		String result = new String();
		for (Card c:cards)
		{
			result += " " + c.number.getName() + c.face.getDisplay();
		}
		return result;
	}
	
	public ArrayList<Card> getCards()
	{
		return cards;
	}
}