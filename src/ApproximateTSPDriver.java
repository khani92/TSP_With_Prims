import java.util.List;
import java.util.Scanner;

public class ApproximateTSPDriver {

	public static void main(String[] args) {

		int startPos = 0, endPos = 0;
		PrimMST primObj = new PrimMST();
		List<Integer> cyclePath = null;

		try (Scanner scanObj = new Scanner(System.in);) {

			System.out.print("Enter start index: ");
			startPos = scanObj.nextInt();

			System.out.print("Enter end index: ");
			endPos = scanObj.nextInt();

			// Load graph
			primObj.loadGraph(startPos, endPos);

			// Print the records asked by the user
			System.out.println("Crime records Processed: \n");

			for (int i = 0; i < primObj.getFileData().length; i++) {
				System.out.println(primObj.getFileData()[i]);
			}

			primObj.getMST(0);
			System.out.print("\nHamiltonian Cycle (not necessarily optimum): ");

			cyclePath = primObj.getHamiltonianCycle(0);

			System.out.println(cyclePath.toString());

			System.out.println("Length of the Cycle: "
					+ primObj.getLengthFromPath(cyclePath) + " miles");

			scanObj.close();
		}

	}
}
