package Blackjack;

public class Card {
	static enum Number { 
		ACE(1,'1'),   TWO(2,'2'),    THREE(3,'3'), FOUR(4,'4'), FIVE(5,'5'), 
		SIX(6,'6'),   SEVEN(7,'7'),  EIGHT(8,'8'), NINE(9,'9'), TEN(10, 'T'), 
		JACK(10,'J'), QUEEN(10,'Q'), KING(10,'K');
		
		private final int value; 
		private final char name;
	    Number (int v, char n) { this.value = v; this.name = n; }
	    public int getValue() { return value; }
	    public char getName() { return name;}
	};
		
	static enum Face { 
		SPADE ("<&="), HEART ("<$"), DIAMOND ("<>"), CLUB ("oOo");
		
		private final String display;
		Face (String d) { this.display = d; }
		public String getDisplay() { return display; }
	};

		Number number;
		Face face;

		Card (Number n, Face f)
		{
			number = n;
			face = f;
		}

		public int getPoint()
		{
			return number.getValue();
		}
}