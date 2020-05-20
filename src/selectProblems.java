
public class selectProblems {
	
	private static int numComparisons; // Saves the number of comparisons made in one of the methods
								// Initialized with 0 with every call to one of the select methods.
	
	/*
	 * The method returns if num1 is greater than num2 and updates the field numComparisons.
	 */
	private static boolean greaterThan(int num1, int num2) {
		numComparisons++;
		return num1 > num2; 
	}
	
	
	/*
	 * The method returns an array whose length is 2, and contains
	 * the k-th statistic order in position 0 and the number of comparisons done in position 1,
	 * using quick sort.
	 * Complexity: O(n^2) W.C, O(nlogn) expected
	 */
	public static int[] selectRandQuickSort(int[] arr, int k) {
		numComparisons = 0;
		
		randQuickSort(arr, 0, arr.length-1);
		
		return new int[]{arr[k], numComparisons};
	}
	
	
	/*
	 * The method sorts the elements in arr in ascending order in place using rand-partition.
	 * Complexity: O(n^2) W.C, O(nlogn) expected
	 */
	private static void randQuickSort(int[] arr, int l, int r) {
		if (l < r) {
			int pivot = randPartition(arr, l, r);
			randQuickSort(arr, l, pivot-1);
			randQuickSort(arr, pivot+1, r);
		}
	}

	/*
	 * the method swaps arr[i]<->arr[j]
	 */
	private static void swap(int[] arr, int i, int j) {
		int temp = arr[j];
		arr[j] = arr[i];
		arr[i] = temp;
	}
	
	/*
	 * The method raffles a pivot and uses it to perform Lomuto's partition.
	 * It returns the location of the pivot in arr after the partition.
	 * Complexity: O(r-l+1)
	 */
	private static int randPartition(int[] arr, int l, int r) {
		int i = l + (int)(Math.random()*(r-l+1));
		swap(arr, r, i); // now the pivot is in arr[r]
		return partition(arr, l, r); 
	}
	
	/*
	 * The method performs Lomuto's partition
	 * It returns the location of the pivot in arr after the partition.
	 * Complexity: O(r-l+1) 
	 */
	private static int partition(int[] arr, int l, int r) {
		int i = l-1 ; 
		for (int j = l; j < r ; j++) {
			if (greaterThan(arr[r], arr[j])) {
				i ++ ;
				swap(arr, j, i);
			}
		}
		swap(arr, i+1, r);
		return i+1; //  returns the pivot's location
	}
	
	/***************************
	 * 
	 */
	
	
	/*
	 * The method returns an array whose length is 2, and contains
	 * the k-th statistic order in position 0 and the number of comparisons done in position 1,
	 * using insertion sort.
	 */
	public static int[] selectInsertionSort(int[] arr, int k) {
		numComparisons = 0;
		
		insertionSort(arr);
		
		return new int[]{arr[k], numComparisons};
	}
	

	/*
	 * The method sorts the elements in arr in ascending order in place.
	 * Complexity: O(n^2) W.C
	 */
	private static void insertionSort(int[] arr) {
		for (int i =1 ; i<arr.length ; i++) {
			int t = arr[i];
			int j = i ;
			while(j>0 && greaterThan(arr[j-1], t)) {
				arr[j] = arr[j-1];
				j-- ;
			}
			arr[j] = t;
		}
	}
	
	/*
	 *  The method returns an array whose length is 2, and contains
	 * the k-th statistic order in position 0 and the number of comparisons done in position 1,
	 * using min heap and k-1 times deleteMin.
	 * Time Complexity: O(n + klogn) 
	 */
	public static int[] selectHeap(int[] arr, int k) {
		numComparisons = 0;
		
		MinHeap mHeap = new MinHeap(arr); // O(n)
		
		for (int i = 0; i < k-1; i++) { //O(klogn)
			mHeap.deleteMin();
		}
		
		return new int[] {mHeap.heap[0].key, numComparisons};
	}
	
	/*
	 *  The method returns an array whose length is 2, and contains
	 * the k-th statistic order in position 0 and the number of comparisons done in position 1,
	 * using min heap and k-1 times deleteMin. 
	 * Time Complexity: O(n + klogk)
	 */
	public static int[] selectDoubleHeap(int[] arr, int k) {
		if (arr == null)
			return null;
		
		numComparisons = 0;
		
		MinHeap mHeap = new MinHeap(arr);
		MinHeap helpHeap = new MinHeap(arr.length);
		Node node;
		
		helpHeap.insert(mHeap.heap[0]);
		node = mHeap.heap[0];
		
		for (int i = 0; i < k; i++) {
			node = helpHeap.deleteMin();
			if (mHeap.getLeft(node.index) < mHeap.length) {
				helpHeap.insert(mHeap.heap[mHeap.getLeft(node.index)]); // adding the left son of node in mHeap to helpHeap
			}
			if (mHeap.getRight(node.index) < mHeap.length) {
				helpHeap.insert(mHeap.heap[mHeap.getRight(node.index)]); // adding the right son of node in mHeap to helpHeap
			}
		}
		
		
		return new int[] {node.key, numComparisons};
	}
	
	
	/*************************** */
	
	private static class Node{
		private int index; // the index of the node in the current heap
		private int key; // the key of the node
		
		/*
		 * The method creates and returns an object of type Node
		 * Complexity: O(1)
		 */
		private Node(int index, int key){
			this.index = index;
			this.key = key;
		}
	}
	
	
	/*
	 * This class implements MinHeap methods
	 */
	private static class MinHeap{
		
		private int length; // the actual length of the heap
		private Node[] heap; // array that represents a heap
		
		
		/*
		 * the method swaps arr[i]<->arr[j]
		 */
		private static void swap(Node[] arr, int i, int j) {
			Node temp = arr[j];
			arr[j] = arr[i];
			arr[i] = temp;
		}
		
		/*
		 * The method creates and returns an object of type MinHeap from arr.
		 * Complexity: O(n)
		 */
		private MinHeap(int[] arr){
			int n = arr.length;
			Node[] nodeArr = new Node[n];
			for (int i=0; i<n; i++) {
				nodeArr[i] = new Node(i, arr[i]);
			}
			
			this.heap = nodeArr;
			this.length = arr.length;
			
			for (int i = n/2; i > 0; i--) {
				heapifyDown(i-1);
			}
			
		}
		
		/*
		 * The method creates and returns an empty object of type MinHeap of the size entered. 
		 */
		private MinHeap(int n) {
			this.length = 0;
			this.heap = new Node[n];
		}
		
		
		/*
		 * The method returns the index of the left son of i 
		 */
		private int getLeft(int i) {
			return 2*i + 1;
		}
		
		/*
		 * The method returns the index of the right son of i
		 */
		private int getRight(int i) {
			return 2*(i+1);
		}
		
		/*
		 * The method returns the index of the parent of i
		 */
		private int getParent(int i) {
			return (i+1)/2 - 1;
		}
		
		/*
		 * The method repairs the route from i down so that this route is legal afterwards.
		 * Complexity: O(logn)
		 */
		private void heapifyDown(int i) {
			int l = getLeft(i);
			int r = getRight(i);
			int n = this.heap.length;
			int smallest = i;
			if (l < n && greaterThan(this.heap[smallest].key, this.heap[l].key)) {
				smallest = l;
			}
			
			if (r < n && greaterThan(this.heap[smallest].key, this.heap[r].key)) {
				smallest = r;
			}
			
			if (smallest != i){
				swap(this.heap, i, smallest);
				heapifyDown(smallest);
			}
		}
		
		/*
		 * The method repairs the route from i up to the root so that this route is legal afterwards.
		 * Complexity: O(logn)
		 */
		private void heapifyUp(int i) {
			int parent = getParent(i);
			int n = this.heap.length;
			if (parent < n && greaterThan(this.heap[parent].key, this.heap[i].key)) {
				swap(this.heap, i, parent);
				heapifyUp(parent);
			}
		}
		
		/*
		 * The method deletes the minimal element from this and returns this element.
		 * Complexity: O(logn)
		 */
		private Node deleteMin() {
			Node min = this.heap[0];
			this.heap[0] = this.heap[this.length - 1];
			this.length--;
			heapifyDown(0); // O(logn)
			return min;
		}
		
		/*
		 * The method inserts key into the heap.
		 * Complexity: O(logn)
		 */
		private void insert(Node node) {
			this.heap[this.length] = node;
			this.length++;
			this.heapifyUp(this.length-1);
		}
		
		
		
	}
	

	/********************************************/
	
	

	/*
	 * The method returns an array whose length is 2, and contains
	 * the k-th statistic order in position 0 and the number of comparisons done in position 1,
	 * using "lazy" quick sort.
	 * Complexity: O(n^2) W.C expected O(n).
	 */
	public static int[] randQuickSelect(int[] arr, int k) {
		numComparisons = 0;
		
		recRandQuickSelect(arr, k , 0, arr.length-1);
		
		return new int[]{arr[k], numComparisons};
	}
	
	/*
	 * The method performs quick select using lazy quick sort.
	 *  Complexity: O(n^2) W.C expected O(n).
	 */
	private static void recRandQuickSelect(int[] arr, int l, int r, int k) {
		if (l < r) {
			int pivot = randPartition(arr, l, r);
			if (greaterThan(pivot, k)) // there are more than k elements which are smaller than the pivot
				recRandQuickSelect(arr, l, pivot-1, k);
			if (greaterThan(k, pivot)) // there are more than k elements which are greater than the pivot
				recRandQuickSelect(arr, pivot+1, r, k);
		}
	}
	
}
