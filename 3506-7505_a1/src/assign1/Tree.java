package assign1;

import java.util.List;
import java.util.Stack;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.LinkedList;

public class Tree {

	private LinkedList<Node> nodeList;
	private List<Character> operatorList;
	private List<Character> uselessCharList;
	private String infix;

	/*
	 * This is a private inner class which modeling the node of the binary tree
	 */
	private class Node {
		private Node leftChild;
		private Node rightChild;
		private String data;

		public Node(String newData) {
			leftChild = null;
			rightChild = null;
			data = newData;
		}
	}

	/*
	 * Construct an ArrayList of operators
	 */
	private void addOperators() {
		operatorList = new ArrayList<Character>();
		operatorList.add('%');
		operatorList.add('?');
		operatorList.add('&');
	}

	/*
	 * Construct an ArrayList of characters unrelated to the postfix expressions
	 */
	private void addUselessChar() {
		uselessCharList = new ArrayList<Character>();
		uselessCharList.add(' ');
		uselessCharList.add('(');
		uselessCharList.add(')');
	}

	/**
	 * Constructor should take a string representing the tree to be created.
	 * 
	 * @param s
	 *            - String representing the tree, e.g. (4 & 6) % (2 ? 3)
	 */
	public Tree(String s) {
		this.infix = s;
		this.nodeList = new LinkedList<Node>();
		List<String> outputList = new LinkedList<String>();
		Stack<String> operatorStack = new Stack<String>();
		int leftParenthesis = 0;
		int rightParenthesis = 0;
		int startIndex = -1;
		char currentChar;
		String currentOperator;
		int numbers = 0;
		int operators = 0;

		addOperators();
		addUselessChar();
		// Converting the expression to a postfix expression and dealing with
		// errors
		for (int stringIndex = 0; stringIndex < s.length(); stringIndex++) {
			currentChar = s.charAt(stringIndex);
			// Dealing with the error "Wrong number of argument for an operator"
			if (!Character.isDigit(s.charAt(0)) && s.charAt(0) != '(') {
				System.err.println("Wrong number of argument for an operator");
				return;
			}
			if (!Character.isDigit(s.charAt(s.length() - 1)) && s.charAt(s.length() - 1) != ')') {
				System.err.println("Wrong number of argument for an operator");
				return;
			}
			// Consider that the number could be greater than 10, substring is
			// used to take the number from the string s
			if (startIndex >= 0) {
				if (uselessCharList.contains(currentChar)) {
					// Output the number
					outputList.add(s.substring(startIndex, stringIndex));
					startIndex = -1;
				}
				if ((stringIndex == s.length() - 1) && (currentChar != ')')) {
					outputList.add(s.substring(startIndex));
				}
				if (Character.isDigit(s.charAt(stringIndex))) {
					continue;
				}
			}
			// When the character is a digit
			if (Character.isDigit(s.charAt(stringIndex)) && startIndex == -1) {
				if (stringIndex == s.length() - 1) {
					outputList.add(s.substring(stringIndex));
				} else {
					startIndex = stringIndex;
				}
			}
			// When the character is a valid operator
			if (currentChar == '?') {
				operatorStack.push("?");
			}
			if (currentChar == '%') {
				operatorStack.push("%");
			}
			if (currentChar == '&') {
				operatorStack.push("&");
			}

			// When the character is '('
			if (currentChar == '(') {
				leftParenthesis++;
				// Dealing with the error "Non-positive number"
				if (s.charAt(stringIndex + 1) == '-') {
					System.err.println("Non-positive number");
					return;
				}
				operatorStack.push("(");
			}
			// When the character is ')'
			if (currentChar == ')') {
				rightParenthesis++;
				// pop the operator and add to the postfix expression
				currentOperator = operatorStack.pop();
				outputList.add(currentOperator);
				// pop "("
				operatorStack.pop();
			}
		}
		// The last operator (The root of the tree)
		if (!operatorStack.isEmpty()) {
			String lastOperator = operatorStack.pop();
			outputList.add(lastOperator);
		}
		// Dealing with the error "Non-matching parenthesis"
		if (leftParenthesis != rightParenthesis) {
			System.err.println("Non-matching parenthesis");
			return;
		}
		// Constructing the tree
		Stack<Node> treeStack = new Stack<Node>();
		for (int i = 0; i < outputList.size(); i++) {
			Node currentNode = new Node(outputList.get(i));
			if (outputList.get(i).matches("^[0-9]*$")) {
				treeStack.push(currentNode);
				numbers++;
			} else {
				// Dealing with the error "Wrong number of argument for an
				// operator"
				try {
					currentNode.rightChild = treeStack.pop();
					currentNode.leftChild = treeStack.pop();
					treeStack.push(currentNode);
					operators++;
				} catch (EmptyStackException e) {
					System.err.println("Wrong number of argument for an operator");
					nodeList = new LinkedList<Node>();
					return;
				}
			}
			this.nodeList.add(currentNode);
			System.out.println("current node: " + currentNode.data);
		}
		System.out.println("End of Constructor");
		// Dealing with the error "Missing operator"
		if (numbers != operators + 1) {
			System.err.println("Missing operator");
			return;
		}
	}

	/**
	 * Return the height of the tree
	 * 
	 * Note: Height is defined as the number of nodes in the longest path from
	 * the root node to a leaf node. (1 node = height 0, 2 layers = height 1)
	 */
	public int getHeight() {
		// Since nodeList is a list that store the elements by the post-order
		// traversal of the tree, so the last element is the root of the tree.
		Node node = nodeList.getLast();
		return getHeight(node) - 1;
	}

	private int getHeight(Node node) {
		if (node == null) {
			return 0;
		} else {
			return 1 + Math.max(getHeight(node.leftChild), getHeight(node.rightChild));
		}
	}

	/**
	 * Return the number of leaves in the tree
	 */
	public int nbLeaves() {
		int leaves = 0;
		for (Node node : nodeList) {
			if (node.leftChild == null && node.rightChild == null) {
				leaves++;
			}
		}
		return leaves;
	}

	/**
	 * Return the result of evaluating each operation in the tree using
	 * recursion.
	 */
	public int getResultRec() {
		// Since nodeList is a list that store the elements by the post-order
		// traversal of the tree, so the last element is the root of the tree.
		Node node = nodeList.getLast();
		return getResultRec(node);
	}

	private int getResultRec(Node node) {
		try {
			if (node.leftChild == null && node.rightChild == null) {
				return Integer.parseInt(node.data);
			} else {
				if (node.data == "&") {
					return getResultRec(node.leftChild) + getResultRec(node.rightChild);
				} else if (node.data == "%") {
					return getResultRec(node.leftChild) % getResultRec(node.rightChild);
				} else if (node.data == "?") {
					return Math.max(getResultRec(node.leftChild), getResultRec(node.rightChild));
				} else {
					return -1;
				}
			}
		} catch (ArithmeticException e) {
			return -1;
		}
	}

	/**
	 * Return a list in which each item is a step in evaluating the tree.
	 */
	public List<String> getResultBySteps() {
		Stack<String> stepStack = new Stack<String>();
		List<String> stepList = new ArrayList<String>();

		stepList.add(infix);
		stepList.add(everyStepOperation(infix, stepStack, stepList));
		return stepList;
	}

	/*
	 * This method is to represent the string in every step and add them to the
	 * list by recursion.
	 * 
	 * @param s - The String to evaluate
	 * 
	 * @param stepStack - The stack that help evaluation
	 * 
	 * @param stepList - The list to be return
	 * 
	 */
	private String everyStepOperation(String s, Stack<String> stepStack, List<String> stepList) {
		String currentString;
		String stepString;
		char currentChar;
		int startIndex = -1;
		int leftParenthesisIndex = -1;
		for (int stringIndex = 0; stringIndex < s.length(); stringIndex++) {
			currentChar = s.charAt(stringIndex);
			// The number
			if (startIndex >= 0) {
				if (uselessCharList.contains(currentChar)) {
					stepStack.push(s.substring(startIndex, stringIndex));
					startIndex = -1;
				}
				if ((stringIndex == s.length() - 1) && (currentChar != ')')) {
					stepStack.push(s.substring(startIndex));
				}
				if (Character.isDigit(s.charAt(stringIndex))) {
					continue;
				}
			}
			if (Character.isDigit(s.charAt(stringIndex)) && startIndex == -1) {
				if (stringIndex == s.length() - 1) {
					stepStack.push(s.substring(stringIndex));
				} else {
					startIndex = stringIndex;
				}
			}
			// The operators
			if (currentChar == '?') {
				stepStack.push("?");
			}
			if (currentChar == '%') {
				stepStack.push("%");
			}
			if (currentChar == '&') {
				stepStack.push("&");
			}
			// when the character is "("
			if (currentChar == '(') {
				stepStack.push("(");
				leftParenthesisIndex = stringIndex;
			}
			// when the character is ")"
			if (currentChar == ')') {
				currentString = stackOperation(stepStack) + "";
				if (leftParenthesisIndex == 0) {
					stepString = currentString + s.substring(stringIndex + 1);
					stepList.add(stepString);
					return everyStepOperation(stepString, stepStack, stepList);
				} else {
					if (stringIndex == s.length() - 1) {
						stepString = s.substring(0, leftParenthesisIndex) + currentString;
						stepList.add(stepString);
						return everyStepOperation(stepString, stepStack, stepList);
					} else {
						stepString = s.substring(0, leftParenthesisIndex) + currentString + s.substring(stringIndex);
						stepList.add(stepString);
						return everyStepOperation(stepString, stepStack, stepList);
					}
				}
			}
		}
		if (!stepStack.isEmpty()) {
			currentString = stackOperation(stepStack) + "";
			return currentString;
		}
		return "-1";
	}

	/*
	 * This method aims to evaluate when there are two numbers and a operator in
	 * the stack
	 * 
	 * @param stepStack - The stack that help evaluation
	 * 
	 */
	private int stackOperation(Stack<String> stepStack) {
		int result = -1;
		// Distinguish the numbers and the operator
		int rightNumber = Integer.parseInt(stepStack.pop());
		String operator = stepStack.pop();
		int leftNumber = Integer.parseInt(stepStack.pop());
		// pop "(" if there exist
		if (stepStack.size() != 0 && stepStack.peek() == "(") {
			stepStack.pop();
		}
		// Wrong number
		if (leftNumber < 0 || rightNumber < 0) {
			return -1;
		}
		if (operator == "?") {
			result = Math.max(leftNumber, rightNumber);
		}
		if (operator == "%") {
			// Cannot be divide by 0
			if (rightNumber == 0) {
				return -1;
			}
			result = leftNumber % rightNumber;
		}
		if (operator == "&") {
			result = leftNumber + rightNumber;
		}
		// make sure the stack is empty for the next evaluation
		while (!stepStack.isEmpty()) {
			stepStack.pop();
		}
		return result;
	}

	/**
	 * Return the result of evaluating each operation in the tree using an
	 * iterative method.
	 */
	public int getResultIt() {
		Stack<Node> nodeStack = new Stack<Node>();
		Stack<Integer> leftStack = new Stack<Integer>();
		Node currentNode = nodeList.getLast();
		int leftNum = -1;
		int rightNum = -1;
		int result = 0;
		int breakTime = 0;
		String operator;

		do {
			// add all the leftchilds
			while (currentNode != null) {
				nodeStack.push(currentNode);
				currentNode = currentNode.leftChild;
			}
			do {
				// when the element is a number
				if (nodeStack.peek().data.matches("^[0-9]*$")) {
					leftNum = Integer.parseInt(nodeStack.peek().data);
					if (leftNum < 0) {
						return -1;
					}
					nodeStack.pop();
				}
				if (operatorList.contains(nodeStack.peek().data.charAt(0))) {
					if (rightNum >= 0) {
						leftNum = leftStack.peek();
						operator = nodeStack.peek().data;
						if (operator == "%") {
							if (rightNum == 0) {
								return -1;
							}
							result = leftNum % rightNum;
						}
						if (operator == "?") {
							result = Math.max(leftNum, rightNum);
						}
						if (operator == "&") {
							result = leftNum + rightNum;
						}
						rightNum = result;
						nodeStack.pop();
						leftStack.pop();
						continue;
					}
					// when the element is an operator, search its right child
					if (operatorList.contains(nodeStack.peek().rightChild.data.charAt(0))) {
						currentNode = nodeStack.peek().rightChild;
						leftStack.push(leftNum);
						breakTime++;
						break;
					}
					if (nodeStack.peek().rightChild.data.matches("^[0-9]*$")) {
						operator = nodeStack.peek().data;
						rightNum = Integer.parseInt(nodeStack.peek().rightChild.data);
						if (rightNum < 0) {
							return -1;
						}
						if (operator == "%") {
							if (rightNum == 0) {
								return -1;
							}
							result = leftNum % rightNum;
						}
						if (operator == "?") {
							result = Math.max(leftNum, rightNum);
						}
						if (operator == "&") {
							result = leftNum + rightNum;
						}
						nodeStack.pop();
						rightNum = -1;
					}
					// To decide whether current operation is in the left or
					// right hand side of the one of the subtree
					if (breakTime > 0) {
						rightNum = result;
						leftNum = -1;
						breakTime--;
					} else {
						leftNum = result;
						leftStack.push(leftNum);
					}
				}
			} while (!nodeStack.isEmpty());
		} while (!nodeStack.isEmpty());
		return result;
	}
}
