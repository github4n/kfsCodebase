
public class Sorting {

	private final static int N = 5;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int[] arr = { 5, 3, 2, 1, 4 };

		
//		bibleSort(arr,N); //ð������
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
				swap(arr,i,j);
			}
		}
		
		swap(arr,low,i);
		
		fastSort(arr, low, i-1);
		fastSort(arr, i+1, high);
	}

	
	//ð������(O(n*n)
	private static void bibleSort(int[] arr, int n) {
		for (int j = n; j > 1; j--) {
			for (int i = 0; i < j - 1; i++) {
				if (arr[i] > arr[i + 1]) {					
					swap(arr, i, i+1);
				}
			}
		}
	}

	//�������������±��ֵ
	private static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	

}
