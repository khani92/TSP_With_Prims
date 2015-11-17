import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class holds all the important method to get a minimum spanning tree. It
 * also creates the Hamiltonian cycle from the MST
 * 
 * @author Nikhil
 * 
 */
public class PrimMST {

	/**
	 * Contains the rows from file extracted as per the user preference
	 */
	private String[] fileData = null;
	/**
	 * Graph object
	 */
	private Graph tspGraph = null;

	/**
	 * An array of the point class. This will be handy to do distance
	 * calculation
	 */
	private Point[] points = null;
	/**
	 * This is the queue that will help us get the light edge from the cut
	 */
	private PriorityQueue pq = null;

	/**
	 * This array will hold the vertex num of the parent (the vertex from where
	 * we reached the index). Eg. parent[0] =2 will mean that we reached the
	 * vertex 0 from the vertex 2
	 */
	int[] parent = null;

	/**
	 * Marker array to see which vertices are alredy on the tree
	 */
	boolean[] marked = null;

	/**
	 * Distance array to hold the distance of the edge
	 */
	double[] distance = null;

	/**
	 * This method loads the graph based on the inputs by the user
	 * 
	 * @param start
	 * @param end
	 */
	public void loadGraph(int start, int end) {

		fileData = getDataFromFile(start, end);
		points = getPointsFromFile();

		int size = end - start + 1;
		tspGraph = new Graph(size);

		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				tspGraph.addEdge(i, j, Point.distance(points[i], points[j]));
				tspGraph.addEdge(j, i, Point.distance(points[i], points[j]));
			}
		}
	}

	/**
	 * Gets the required rows from the csv file based on the user input
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public String[] getDataFromFile(int start, int end) {
		Scanner scanObj;
		int pos = 0, counter = 0;
		String[] data = new String[end - start + 1];

		try {
			scanObj = new Scanner(new File("CrimeLatLonXY1990.csv"));
			scanObj.nextLine();

			while (pos < start && scanObj.hasNext()) {
				scanObj.nextLine();
				pos++;
			}
			while (pos <= end && scanObj.hasNext()) {
				data[counter] = scanObj.nextLine();
				counter++;
				pos++;

			}

			scanObj.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * Populates the point array
	 * 
	 * @return
	 */
	public Point[] getPointsFromFile() {
		String[] row = null;
		Point[] pointArray = new Point[fileData.length];
		for (int i = 0; i < fileData.length; i++) {
			row = fileData[i].split("[,]");
			pointArray[i] = new Point(Double.parseDouble(row[0].trim()),
					Double.parseDouble(row[1].trim()));
		}

		return pointArray;
	}

	/**
	 * Generates the MST from the graph and populates the parent, weight array
	 * 
	 * @param vertex
	 */
	public void getMST(int vertex) {
		initializeArrays();

		pq.insert(0, 0.0);
		distance[0] = 0.0;

		while (!pq.isEmpty()) {
			visit(pq.deleteMin());
		}

	}

	public void visit(int vertex) {
		// true means visted
		marked[vertex] = true;
		int[] neighbours = tspGraph.getNeighbours(vertex);
		double[][] adjMatrix = tspGraph.getAdjMatrix();

		for (int i = 0; i < neighbours.length; i++) {

			if (marked[neighbours[i]] == true) {
				continue;
			}
			if (adjMatrix[vertex][neighbours[i]] < distance[neighbours[i]]) {
				// Current edge has a smaller distance than the edge distance in
				// the array
				distance[neighbours[i]] = adjMatrix[vertex][neighbours[i]];
				parent[neighbours[i]] = vertex;

				if (pq.contains(neighbours[i])) {
					pq.reduceKey(neighbours[i], distance[neighbours[i]]);
				} else {
					pq.insert(neighbours[i], distance[neighbours[i]]);
				}
			}
		}
	}

	/**
	 * Populates all the required arrays
	 */
	public void initializeArrays() {

		distance = new double[tspGraph.getSize()];
		parent = new int[tspGraph.getSize()];
		marked = new boolean[tspGraph.getSize()];
		pq = new PriorityQueue(tspGraph.getSize());

		for (int i = 0; i < tspGraph.getSize(); i++) {
			distance[i] = Double.POSITIVE_INFINITY;
		}
	}

	/**
	 * Generates the Hamiltonian cycle from parent array
	 * 
	 * @param root
	 * @return
	 */
	public List<Integer> getHamiltonianCycle(int root) {
		List<Integer> pathList = new ArrayList<>();
		Stack stack = new Stack(tspGraph.getSize());

		pathList.add(root);
		stack.push(root);

		while (!stack.isEmpty()) {
			boolean pushedSomething = false;

			for (int i = 0; i < parent.length; i++) {

				if (parent[i] == stack.peek() && !pathList.contains(i)) {
					stack.push(i);
					pathList.add(i);
					pushedSomething = true;
					break;
				}
			}

			if (pushedSomething == false) {
				stack.pop();
			}
		}

		pathList.add(root);
		return pathList;

	}

	public String getLengthFromPath(List<Integer> list) {
		double sum = 0.0;

		for (int i = 0; i < list.size() - 1; i++) {
			sum = sum
					+ Point.distance(points[list.get(i)],
							points[list.get(i + 1)]);
		}

		return String.format("%.2f", sum * (0.00018939));
	}

	public String[] getFileData() {
		return fileData;
	}

}
