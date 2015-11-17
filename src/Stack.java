/**
 * A small stack class with basic stack methods for a pre order walk of the
 * Approximate MST tree
 * 
 * @author Nikhil
 * 
 */

public class Stack {

	int limit; // Full capacity of the stack (array used to implement stack)
	int[] stack; // Array implementing stack
	int top = -1; // top counter in stack

	public Stack(int limit) {
		this.limit = limit;
		stack = new int[limit];
	}

	/**
	 * Method to check if the stack is empty
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return top == -1;
	}

	/**
	 * Method to push value into the stack
	 * 
	 * @param i
	 */
	public void push(int i) {
		top++;
		stack[top] = i;
	}

	/**
	 * Pop value out of the stack
	 * 
	 * @return
	 */
	public int pop() {
		int popped = 0;
		if (!isEmpty()) {
			popped = stack[top--];

		}
		return popped;
	}

	/**
	 * Peek is useful for our pre-order walk of the tree. It gives the value of
	 * the top of the stack without removing it
	 * 
	 * @return
	 */
	public int peek() {
		return !isEmpty() ? stack[top] : -1;
	}

	//Test class for stack
	public static void main(String[] args) {
		Stack stack = new Stack(5);
		for (int i = 0; i < 5; i++) {
			stack.push(i);
		}
		for (int i = 0; i < 5; i++) {
			System.out.println(stack.pop());
		}
	}

}
