package Sorting;

public class BibbleSorting {

	private final static int N = 5;

	public static void main(String[] args) {
		
		int[] arr = { 5, 3, 2, 1, 4 };

		
		bibleSort(arr,N); //Ã°ÅÝÅÅÐò
		
		for (int data:arr) {
			System.out.println(data);
		}

	}
	
	//Ã°ÅÝÅÅÐò(O(n*n)
	private static void bibleSort(int[] arr, int n) {
		for (int j = n; j > 1; j--) {
			for (int i = 0; i < j - 1; i++) {
				if (arr[i] > arr[i + 1]) {					
					Util.swap(arr, i, i+1);
				}
			}
		}
	}

	

}
