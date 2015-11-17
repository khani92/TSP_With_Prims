/**
 * This class implements the Graph ADT. The graph implemented below would be an
 * undirected and with unweighed edges
 * 
 * @author Nikhil
 * 
 */
public class Graph {

	private int vertices;
	double[][] adjMatrix;
	int[] labels;

	/**
	 * The constructor asks for the number of vertices in the graph and creates
	 * an adjacency matrix and labels array
	 * 
	 * @param v
	 */
	Graph(int v) {
		vertices = v;
		adjMatrix = new double[v][v];
		labels = new int[v];

		for (int i = 0; i < v; i++) {
			for (int j = 0; j < v; j++) {
				addEdge(i, j, Double.POSITIVE_INFINITY);
				addEdge(j, i, Double.POSITIVE_INFINITY);
			}
		}
	}

	/**
	 * Adds an edge to an undirected graph PreCondition: i,j are less than the
	 * number of vertices in the graph and are non negative
	 * 
	 * @param i
	 * @param j
	 */
	public void addEdge(int i, int j, double weight) {
		adjMatrix[i][j] = weight;
		adjMatrix[j][i] = weight;
	}

	/**
	 * Checks if an edge exists between a given set of vertices
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean isEdge(int i, int j) {
		return (adjMatrix[i][j] != Double.POSITIVE_INFINITY) ? true : false;
	}

	/**
	 * Removes all the edges between two vertices in an undirected graph
	 * 
	 * @param i
	 * @param j
	 */
	public void removeEdge(int i, int j) {
		adjMatrix[i][j] = Double.POSITIVE_INFINITY;
		adjMatrix[j][i] = Double.POSITIVE_INFINITY;
	}

	public double[][] getAdjMatrix() {
		return adjMatrix;
	}

	/**
	 * Get all the neighbors for a given vertex
	 * 
	 * @param i
	 * @return
	 */
	public int[] getNeighbours(int vertex) {

		int count = 0;

		// Count number of neighbors first
		for (int i = 0; i < vertices; i++) {
			if (adjMatrix[vertex][i] != Double.POSITIVE_INFINITY) {
				count++;
			}
		}

		int[] neighborArray = new int[count];
		count = 0;

		for (int i = 0; i < vertices; i++) {
			if (adjMatrix[vertex][i] != Double.POSITIVE_INFINITY) {
				neighborArray[count] = i;
				count++;
			}
		}
		return neighborArray;
	}

	public int getSize() {
		return vertices;
	}

	/**
	 * This method is used to print the adjacency matrix. This method does not
	 * print the unused part of the matrix
	 */
	public void printAdjMatrix() {
		int countX = 0;
		int countY = 0;
		System.out.println("The adjacency matrix for the graph is: ");

		for (int i = 0; i < vertices; i++) {

			for (int j = 0; j < vertices; j++) {
				if (adjMatrix[i][j] != Double.POSITIVE_INFINITY) {

					countY = i;

					if (countX < j) {
						countX = j;
					}
				}
			}
		}

		// Time to print the adjacency matrix. Effective vertices start counting
		// from 0
		int effectiveVertices = Math.max(countX, countY);

		for (int i = 0; i <= effectiveVertices; i++) {
			for (int j = 0; j <= effectiveVertices; j++) {
				if (adjMatrix[i][j] != Double.POSITIVE_INFINITY) {
					System.out.print(adjMatrix[i][j] + " ");
				} else {
					System.out.print(-1 + " ");
				}
			}
			System.out.println();
		}
	}

	// Add traversal BFS-DFS methods if time permits

	public void breadthFirstSearch(int vertex) {
		boolean[] markerArray = new boolean[vertices];
		Queue queue = new Queue();

		queue.enQueue(vertex);

		while (!queue.isEmpty()) {

			int vertexNum;
			vertexNum = (int) queue.deQueue();
			if (markerArray[vertexNum] == false) {
				System.out.println(vertexNum);
				markerArray[vertex] = true;
			}

			int[] neighbours = getNeighbours(vertexNum);

			for (int i = 0; i < neighbours.length; i++) {
				queue.enQueue(neighbours[i]);
			}
		}

	}
}

/**
 * The Queue is a first in first out data structure. This Queue holds Java
 * Object references. It grows dynamically as long as memory is available.
 * 
 * @author Nikhil
 */
class Queue {

	// array to implement the queue
	private Object[] array;

	// items variable to keep track of number of items in the array/queue i.e.
	// size of the data structure
	private int items = 0;
	private int front, rear = 0;

	/**
	 * Create a default empty queue that lives in a small array. PreCondition:
	 * Memory is available. PostCondition: Array created and indexes
	 * established.
	 */
	public Queue() {
		array = new Object[10];
	}

	/**
	 * Boolean method returns true on empty queue, false otherwise. Pre: None
	 * BigTheta No case: Big-Theta (1)
	 * 
	 * @return Returns true if queue is empty.
	 */
	public boolean isEmpty() {
		if (items == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Boolean method returns true if queue is currently at capacity, false
	 * otherwise. If isfull() returns true and additional enqueue calls are
	 * made, the queue will expand in size. Pre: None BigTheta No case:
	 * Big-Theta (1)
	 * 
	 * @return Returns true if queue is at current capacity.
	 */
	public boolean isFull() {
		if (items == array.length) {
			return true;
		}
		return false;
	}

	/**
	 * Object method removes and returns reference in front of queue.
	 * PreCondition: Queue should not be empty. PostCondition: The top/front
	 * item is removed from the queue. BigTheta No case: Big-Theta (1)
	 * 
	 * @return Object from the front of the queue.
	 */
	public Object deQueue() {
		Object data = null;
		if (!isEmpty()) {
			// decrease the items variable to keep the correct count of items
			items--;
			if (front == rear) {
				data = array[front];
				array[front] = null;
			}

			else if (front != rear) {
				data = array[front];
				array[front] = null;
				front = getNext(front);
			}
		}
		return data;
	}

	/**
	 * This method returns the next inderx based on the current index which is
	 * passed as a paramter. It uses modulo operator to implement circular queue
	 * 
	 * @param i
	 *            : current index
	 * @return
	 */
	private int getNext(int i) {
		return (i + 1) % array.length;
	}

	/**
	 * Add an object reference to the rear of the queue. Pre-condition Memory is
	 * available for doubling queue capacity when full. Post-condition: queue
	 * now contains x in the rear. BigTheta Best case: Queue is not full
	 * Big-Theta (1) BigTheta Worst case: Queue is full Big-Theta (N)
	 * 
	 * @param x
	 *            : Is an object to be added to the rear of the queue.
	 */
	public void enQueue(Object x) {
		if (isEmpty()) {
			array[front] = x;
		}

		else if (!isFull()) {
			rear = getNext(rear);
			array[rear] = x;
		} else if (isFull()) {
			// Now copy the array to a bigger sized array
			Object[] temp = array;
			// Create a new and bigger array of double size
			array = new Object[temp.length * 2];
			// Copies the small array into the big one
			int count = front;
			for (int i = 0; i < temp.length; i++) {
				array[i] = temp[count];
				count = (count + 1) % temp.length;
			}
			front = 0;
			rear = temp.length;
			array[rear] = x;
			temp = null;
		}
		// increases the count of items
		items++;
	}

	/**
	 * Method getFront returns the front of the queue without removing it.
	 * Pre-condition: queue not empty BigTheta No case: Big-Theta (1)
	 * 
	 * @return: The queue front without removal.
	 */
	public Object getFront() {
		Object data = null;
		if (!isEmpty()) {
			data = array[front];
		}
		return data;
	}

	/**
	 * The toString method returns a String representation of the current queue
	 * contents. BigTheta Best case: Big-Theta (1)
	 * 
	 * @return: a string representation of the queue. It shows the front of the
	 *          queue first. It then shows the second and third and so on.
	 */
	public String toString() {
		StringBuffer s = new StringBuffer();
		for (int i = front; i != getNext(rear); getNext(i)) {
			s.append(array[i] + "  ");
		}
		return s.toString();
	}

	/**
	 * main is for testing the queue routines.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Queue queue = new Queue();

		for (int i = 0; i < 10; i++) {
			queue.enQueue(i);
		}
		System.out.println(queue.isEmpty());
		System.out.println(queue.isFull());

		System.out.println(queue.deQueue());
		System.out.println(queue.deQueue());

		for (int i = 0; i < 80; i++) {
			queue.enQueue(i);
		}
		queue.enQueue(111);
		queue.enQueue(12232);

		queue.enQueue(1212);
		System.out.println(queue.getFront());
	}
}
