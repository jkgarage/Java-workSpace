import java.io.*;
import java.util.*;
class Solution {
	public static void main(String args[] ) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = br.readLine();
		
		Scanner s = new Scanner(line).useDelimiter(" ");
		int N = s.nextInt();
		int K = s.nextInt();
		
		line = br.readLine();
		s = new Scanner(line).useDelimiter(" ");
		
		int [] inputArr = new int[N*2];

		for (int i = 0; i < N; i++) {
			inputArr[i] = s.nextInt();
			inputArr[i+N] = inputArr[i] + K;
		}
		
		radixSort(inputArr);
		
		int valueOfPrevIndex = 0;
		int result = 0;
		for (int i = 1; i < inputArr.length; i++)
		{
			if (inputArr[i] == inputArr[i-1])
			{
				result++;
			}
		}
		
		System.out.println(result);
	}
	
	public static void radixSort(int[] numberList)
	{
		int maxValue = Integer.MIN_VALUE;
		
		for (int i = 0; i < numberList.length; i++)
			if (numberList[i] > maxValue)
			maxValue = numberList[i];
		int k = (int) Math.floor(Math.log10(maxValue)) + 1;
		
		int order = 0, nextOrder = 0;
		
		ArrayList<ArrayList<Integer>> buckets = new ArrayList<ArrayList<Integer>> ();
		int digit = 0;
		for (int i = 0; i < 10; i++)
		{
			ArrayList<Integer> item = new ArrayList<Integer> ();
			buckets.add(item);
		}
		
		for (int i = 0; i < k; i++)
		{	
			for (int j = 0; j < numberList.length; j++)
			{
				digit = (numberList[j] / (int)Math.pow(10,i) ) % 10;
				buckets.get(digit).add(new Integer(numberList[j]) );
			}
			
			int index = 0; int bucketNo = 0;
			for (ArrayList<Integer> item : buckets)
			{
				bucketNo++;
				for (Integer num : item)
				{
					numberList[index++] = num.intValue();	
				}
				item.clear();
			}
		}
	}
}
