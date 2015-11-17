/**
 * A Priority queue, implemented using reduce-key heaps.
 * 
 * @author Nikhil
 * 
 */

public class PriorityQueue {

	/**
	 * int limit: This is not an auto-restructuring queue. So the limit needs to
	 * be defined at initialization time.
	 */
	private int limit;

	/**
	 * int currSize: Keeps a count of elements in the heap
	 */
	private int currSize = -1;

	/**
	 * This is the actual heap but it only contains the indices, not the weight
	 */
	private int[] pq;

	/**
	 * This contains the weight of the index. eg keys[1]= 2.3 would mean that
	 * index 1 has a key of 2.3 That index 1 can be anywhere on the heap. To
	 * find where exactly it is on the heap we use indexLocOnHeap[1]
	 */
	private double[] keys;
	/**
	 * This is a useful array that holds the location of the index in the heap.
	 * eg. indexLocOnHeap[1]=4 means that index 1 is the 5th( pq[4] ) item in
	 * the array (since arrays start from 0) In other words:
	 * pq[indexLocOnHeap[0]] = indexLocOnHeap[pq[0]]
	 */
	private int[] indexLocOnHeap;

	public PriorityQueue(int limit) {
		this.limit = limit;
		pq = new int[limit];
		keys = new double[limit];
		indexLocOnHeap = new int[limit];

		// -1 denotes that that index is not on the heap yet.
		for (int i = 0; i < limit; i++) {
			indexLocOnHeap[i] = -1;
		}
	}

	/**
	 * Insert the value in the last index of the array and then heapify up (Min
	 * heap)
	 * 
	 * @param i
	 * @param value
	 */
	public void insert(int i, double value) {
		currSize++;
		if (!isFull()) {
			pq[currSize] = i;
			indexLocOnHeap[i] = currSize;
			keys[i] = value;
			heapifyUp(currSize);
		}
	}

	/**
	 * Method to sift up. This moves the smallest value in the pq array up to
	 * the top also recording the changes in pq array in the indexOnHeap array
	 * to maintain state
	 * 
	 * @param index
	 */
	private void heapifyUp(int index) {

		if (index >= 0) {

			while (index >= 0 && keys[pq[index]] < keys[pq[getParent(index)]]) {

				swap(index, getParent(index));
				index = getParent(index); // Move up
			}
		}

	}

	/**
	 * This is opposite to hepaifyUp and will siftDown. This is used when we
	 * changes the index of the pq array.
	 * 
	 * @param index
	 */
	private void heapifyDown(int index) {

		if (index >= 0) {

			while (2 * index + 1 <= currSize) {
				int left = 2 * index + 1;
				int right = 2 * index + 2;
				int switchWith = left;
				if (left < currSize && (keys[pq[left]] > keys[pq[right]])) {
					// Right child is smaller child. Swap with right;
					switchWith = right;
				}
				if (keys[pq[index]] > keys[pq[switchWith]]) {
					// Switch here
					swap(index, switchWith);
					index = switchWith;
				} else if (keys[pq[index]] < keys[pq[switchWith]]) {
					// Heap is settled
					break;
				}

			}
		}

	}

	/**
	 * Private helper method to get the parent from the child node index
	 * @param i
	 * @return
	 */
	private int getParent(int i) {
		return (i / 2);
	}
	
	/**
	 * Actually does the swapping of the two indices on pq array. Also records the changes in indexOnHeap array
	 * @param child
	 * @param parent
	 */
	public void swap(int child, int parent) {
		int temp = pq[child];
		pq[child] = pq[parent];
		pq[parent] = temp;

		// Update this switch in indexlocOnHeap
		indexLocOnHeap[pq[parent]] = parent;
		indexLocOnHeap[pq[child]] = child;
	}

	/**
	 * Checks if the node is on the heap or not
	 */
	public boolean contains(int i) {
		return indexLocOnHeap[i] != -1 ? true : false;
	}

	/**
	 * Delete the min value based on the key (weight of edge) from pq array
	 * @return
	 */
	public int deleteMin() {
		int minIndex = -1;
		if (!isEmpty()) {
			minIndex = pq[0];
			swap(0, currSize--);
			heapifyDown(0);
			indexLocOnHeap[minIndex] = -1;

			// A few more pre-cautionary steps can be added
		}

		return minIndex;
	}

	/**
	 * Method to change the index (parent) in pq array
	 * @param i
	 * @param value
	 */
	public void reduceKey(int i, double value) {

		if (i >= 0 && contains(i)) {
			keys[i] = value;
			heapifyUp(indexLocOnHeap[i]);
		}

	}

	public boolean isEmpty() {
		return currSize == -1 ? true : false;
	}

	public boolean isFull() {
		return currSize == limit ? true : false;
	}

	//Test class for reduce key min heap
	public static void main(String[] args) {

		int[] array = { 10, 3, 5, 7, 9, 2, 4, 6, 8 };

		PriorityQueue pq = new PriorityQueue(array.length);
		for (int i = 0; i < array.length; i++) {
			pq.insert(i, array[i]);
		}

		pq.reduceKey(2, 1);

		while (!pq.isEmpty()) {
			int i = pq.deleteMin();
			System.out.println(array[i]);
		}

	}

}