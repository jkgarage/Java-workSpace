import java.util.*;

class TestRadixSort
{
	public static void main (String [] args)
	{
		int [] numberList = new int[15];
		for (int i = 0; i < 15; i++)
			numberList[i] = (int) (Math.random() * 999);
		
		String str = "Orig:";
		for (int num : numberList)
			str += num + ", ";
		System.out.println(str);
		
		radixSort(numberList);
		str = "Sorted:";
		for (int num : numberList)
			str += num + ", ";
		System.out.println(str);
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
				System.out.print("Bucket #" + bucketNo + ":: ");
				bucketNo++;
				for (Integer num : item)
				{
					numberList[index++] = num.intValue();
					System.out.print(num.intValue() + "->");	
				}
				item.clear();
				System.out.println();
			}
		}
	}
}
