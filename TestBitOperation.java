import java.util.*;
class TestBitOperation
{
	public static void main(String[] args) {
		int bitmask = 0x000F;
		int val = 0x2222;
		
		int x = Integer.MIN_VALUE+1;
		int xNeg = negateNumber(x);
		// prints "2"
		//System.out.println(val & bitmask);
		//System.out.println(addLeadingZeroes(Integer.toBinaryString(val & bitmask)));
		System.out.println( String.format("%-13s", "x0=" + x)   + "  Binary:" + Integer.toBinaryString(x));
		System.out.println( String.format("%-13s", "x1=" + xNeg)+ "  Binary:" + Integer.toBinaryString(xNeg));
		System.out.println( String.format("%-13s", "x2=" + (int) ((x^0xffffffff)+1) )+ "Binary:" + Integer.toBinaryString((x^0xffffffff)+1));
		
		System.out.println( String.format("%-13s", "x3=" + (int) (x >>> 31) )+ "Binary:" + Integer.toBinaryString(x >>> 31));
		
		//int [] sqrArr = {1,4,8, 16, 32, 64, 128, 256, 512, 1024};
		//for (int i : sqrArr)
		//System.out.println("i=" + i + "  Binary:" + Integer.toBinaryString(i) + "  Minus1: " + Integer.toBinaryString(i-1) );
		
		System.out.println( "isNegative10:" + isNegative(10) );
		
		System.out.println( "******************" );
		
		int y0 = 10, y1 = -10;
		System.out.println( String.format("%-13s", "y00=" + y0 )+ "Binary:" + Integer.toBinaryString(y0));
		System.out.println( String.format("%-13s", "y01=" + (int) (y0 >>> 10) )+ "Binary:" + Integer.toBinaryString(y0 >>> 10));
		System.out.println( String.format("%-13s", "y02=" + (int) (y0 >> 10) )+ "Binary:" + Integer.toBinaryString(y0 >> 10));
		//System.out.println( String.format("%-13s", "y03=" + (int) (y0 <<< 10) )+ "Binary:" + Integer.toBinaryString(y0 <<< 10));
		System.out.println( String.format("%-13s", "y04=" + (int) (y0 << 10) )+ "Binary:" + Integer.toBinaryString(y0 << 10));
		
		System.out.println( "----" );
		System.out.println( String.format("%-13s", "y10=" + y1 )+ "Binary:" + Integer.toBinaryString(y1));
		System.out.println( String.format("%-13s", "y11=" + (int) (y1 >>> 10) )+ "Binary:" + Integer.toBinaryString(y1 >>> 10));
		System.out.println( String.format("%-13s", "y12=" + (int) (y1 >> 10) )+ "Binary:" + Integer.toBinaryString(y1 >> 10));
		//System.out.println( String.format("%-13s", "y13=" + (int) (y1 <<< 10) )+ "Binary:" + Integer.toBinaryString(y1 <<< 10));
		System.out.println( String.format("%-13s", "y14=" + (int) (y1 << 10) )+ "Binary:" + Integer.toBinaryString(y1 << 10));
		
		
		/* Output */
		/*
		******************
		y00=10       Binary:00000000000000000000000000001010
		y01=0        Binary:00000000000000000000000000000000
		y02=0        Binary:00000000000000000000000000000000
		y04=10240    Binary:00000000000000000010100000000000
		----
		y10=-10      Binary:11111111111111111111111111110110
		y11=4194303  Binary:00000000001111111111111111111111  //>>> operator, without sign bit
		y12=-1       Binary:11111111111111111111111111111111  //>>  operator, with sign bit
		y14=-10240   Binary:11111111111111111101100000000000
		*/
	}
	
	static int negateNumber(int num)
	{
		int result = num^0xffffffff;
		result ++;
		return result;
	}
	
	static boolean isNegative(int num)
	{
		return (num >>> 31) == 1;
	}
	
	static String addLeadingZeroes(String s) {
		int zeroes = s.length() % 8;
		byte[] bzero = new byte[8 - zeroes];
		Arrays.fill(bzero, (byte)0x30);
		return new StringBuilder(new String(bzero)).append(s).toString();
	}
}


/* ***************************
'number >>> offset' operator : to shift the bits of "number" with a count of "offset" places to the right. 
The original right-most bits will be dropped, and the left-most bits will be filled with 0's.

'number >> offset' operator : to shift the bits of "number" with a count of "offset" places to the right. 
The original right-most bits will be dropped, and the left-most bits will be filled with the original sign bit.
So, if original number is positive, sign bit is 0, the shift will fill up left bits with 0's
    if original number is negative, sign bit is 1, the shift will fill up left bits with 1's

'number << offset' operator : to shift the bits of "number" with a count of "offset" places to the left. 
The original left-most bits will be dropped, and the right-most bits will be filled with 0's.

There is no operator for '<<<' since '<<' would have achieved the same result.
****************************** */


