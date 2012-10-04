class TestInsertionSort
{
	public static void main (String [] args)
	{
		int [] numberList = new int[15];
		for (int i = 0; i < 10; i++)
			numberList[i] = (int) (Math.random() * 999);
		for (int i = 11; i < 15; i++) numberList[i] = numberList[i-6];
		
		String str = "Orig:";
		for (int num : numberList)
			str += num + ", ";
		System.out.println(str);
		
		InsertionSort(numberList);
		str = "Sorted:";
		for (int num : numberList)
			str += num + ", ";
		System.out.println(str);
	}
	
	//sort Asc order
	public static void InsertionSort(int [] numberList)
	{
		for (int i = 0; i < numberList.length; i++)
		{
			//insert numberList[i] into the sorted area
			int j = 0;
			while ( numberList[j] <= numberList[i] && j < i)
			{
				j++;
			}
			
			int tempValue = numberList[i];
			
			for (int k = i; k > j; k--)
				numberList[k]=numberList[k-1];
			numberList[j] = tempValue;
		}
	}
}
