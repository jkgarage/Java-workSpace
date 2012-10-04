import Blackjack.*;
import Blackjack.BlackjackGame.GameResult;

import java.io.*;

public class PlayGame {
	static final boolean _DEBUG = false;
	
	public static void main (String [] args)
	{	
		BlackjackGame bjgame = new BlackjackGame(1, null);
		
		PlayerDealer dealer = new PlayerDealer();
		Player p1 = new Player(100,"p1");
		Player p2 = new Player(100,"p2");
		Player p3 = new Player(100,"p3");
		
		p1.subcribeToGame(bjgame);
		p2.subcribeToGame(bjgame);
		p3.subcribeToGame(bjgame);
		dealer.subcribeToGame(bjgame);
		
		if (_DEBUG) System.out.println( bjgame );
		
		while (true)
		{
			//clear all players' cards
			for ( Player p:bjgame.getPlayers() )
			{
				p.clearCards();
			}

			//place bet
			for ( Player p:bjgame.getPlayers() )
			{
				p.placeBet(10);
			}

			//deal a hand
			bjgame.handPlay();

			System.out.println( "\n\t\t\t--oOo--");
			System.out.println( "\t\t****************************");
			System.out.println( bjgame.displayPlayerCards() );

			if (_DEBUG) System.out.println( "--- Init ---\n" + bjgame );

			GameResult outcome;
			
			//go round the tables for each player's action
			for ( Player p:bjgame.getPlayers() )
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				for ( int i = 0; i < p.getDecks().size(); i++ )
				{	
					String action = "";
					boolean isValidAction = false;
					outcome = null;

					//check if blackjack
					if ( bjgame.checkIfBlackJack(p, i) )
					{
						outcome = bjgame.checkGameResult(p, i);
						if (outcome != null)
							System.out.println( "\t<<<<<< " + p.getName() + "::Deck" + i+1 + ":" + outcome.name() + ">>>>>>>");
						break;
					}
					
					//1. continue the action for player until he calls Stand
					//2. if player is dealer, make sure Stand is a valid move since he need at least 17
					while ( !(action.equals("T") || action.equals("D") ) 
							|| !isValidAction)  //not stand, and not double down
					{
						//await for action from each player
						System.out.print ("Player(" + p.getName() + ")-action:");

						//  read the player input from command-line;
						try {
							action = br.readLine();
							if ( action.equals("H") )
								isValidAction = p.action(BlackjackGame.GameAction.HIT, i);
							else if ( action.equals("S") )
								isValidAction = p.action(BlackjackGame.GameAction.SPLIT, i);
							else if ( action.equals("D") )
								isValidAction = p.action(BlackjackGame.GameAction.DOUBLE, i);
							else if ( action.equals("T") )
								isValidAction = p.action(BlackjackGame.GameAction.STAND, i);
							else if ( action.equals("DB") ) //debug
								printPlayersDetails (bjgame);
							else if ( action.equals("Q") ) //Quit
								return;
						}
						catch (Exception e)
						{
							System.out.println(e);
						}

						/***************************/
						System.out.println( "\t\t\t--oOo--\n" + bjgame.displayPlayerCards() );
						if ( bjgame.checkIfBursted(p, i) )
						{
							System.out.println( "\t<<<<<< " + p.getName() + "::Deck" + i + ": Bursted - LOSE" + ">>>>>>>");
							break;
						}

					}//while loop to read input for each deck

					if (_DEBUG) System.out.println( bjgame );
				}
			}//foreach player
			
			/*********************************/
			//check outcome after the action
			System.out.println( "--- Final cards ---\n" + bjgame.displayPlayerCards() );
			
			for ( Player p:bjgame.getPlayers() )
			{
				for ( int i = 0; i < p.getDecks().size(); i++ )
				{
					outcome = bjgame.checkGameResult(p, i);
					if (outcome != null)
						System.out.println( "\t**\t" + p.getName() + "::Deck" + i+1 + ":" + outcome.name() + "\t**");
				}
			}
		}//while loops for continuous hands
	}//main method
	
	
	static void printPlayersDetails (BlackjackGame bjgame)
	{
		for ( Player p:bjgame.getPlayers() )
		{
			System.out.println( "\n<<<<<< " + p.getName() + "::Holding " + p.getDecks().size() + " decks >>>>>>>");
			System.out.println( "Balance: " + p.getBalance() + "\tCurrentBet: " + p.getBet() );
			
			for ( int i = 0; i < p.getDecks().size(); i++ )
				System.out.println( "Deck" + i+1 + " - TotalPts: " + bjgame.getTotalCardPoints( p.getDeckAt(i)) );
		}
	}
}//class