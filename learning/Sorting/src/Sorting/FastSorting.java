package Sorting;

public class FastSorting {

	private final static int N = 5;

	public static void main(String[] args) {
		
		int[] arr = { 5, 3, 2, 1, 4 };

		
		fastSort(arr, 0, N-1); //��������
		
		for (int data:arr) {
			System.out.println(data);
		}

	}


	//��������O(N*logN)
	private static void fastSort(int[] arr, int low,int high) {
		
		//�ݹ��˳�����
		if (low > high) {
			return;
		}
		
		//�Ե�һ��ֵ��Ϊ��׼ֵ
		int key = arr[low];
		
		int i = low, j = high;
		
		while (i < j) {
			while (i < j && arr[j] > key) {
				j--;
			}
			
			while (i < j && arr[i] <= key) {
				i++;
			}
			
			if (i < j) {
				Util.swap(arr,i,j);
			}
		}
		
		Util.swap(arr,low,i);
		
		fastSort(arr, low, i-1);
		fastSort(arr, i+1, high);
	}


}
