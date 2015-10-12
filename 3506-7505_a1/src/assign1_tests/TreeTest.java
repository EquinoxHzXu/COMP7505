package assign1_tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import assign1.Tree;

/** This is a fairly basic set of tests, you will probably want to implement
 * a more diverse range to ensure your code is data structures and algorithms
 * are working correctly. 
 *
 */

public class TreeTest {
	
	@Test
	public void getHeightTest() {
		Tree tree = new Tree("5 ? 3");
		assertEquals("Tree height should be 1", 1, tree.getHeight());
	}
	
	@Test
	public void maxTest() {
		Tree tree = new Tree("1 ? 2");
		assertEquals("Tree height should be 1", 1, tree.getHeight());
		assertEquals("Result should be 2", 2, tree.getResultIt());
	}
	
	@Test
	public void modTest() {
		Tree tree = new Tree("1 % 2");
		assertEquals("Tree height should be 1", 1, tree.getHeight());
		assertEquals("Result should be 1", 1, tree.getResultIt());
	}
	
	@Test
	public void addTest() {
		Tree tree = new Tree("4 & 6");
		assertEquals("Tree height should be 1", 1, tree.getHeight());
		assertEquals("Result should be 10", 10, tree.getResultIt());
	}

	@Test
	public void complexTest() {
		Tree tree = new Tree("(4 & 6) % (2 ? 3)");
		assertEquals("Tree height should be 2", 2, tree.getHeight());
		assertEquals("Result should be 1", 1, tree.getResultIt());
	}
	
	@Test
	public void smallTreeTest() {
		Tree tree = new Tree("5");
		assertEquals("Tree height should be 0", 0, tree.getHeight());
	}

	@Test
	public void nbLeavesTest() {
		Tree tree = new Tree("5 ? 3");
		assertEquals("Tree has two leaf nodes", 2, tree.nbLeaves());
	}
	
	@Test
	public void getResultTest() {
		Tree tree = new Tree("5 ? 3");
		assertEquals("Result should be 5", 5, tree.getResultRec());
		assertEquals("Result should be 5", 5, tree.getResultIt());
	}
	
	@Test
	public void getResultStepsTest() {
		Tree tree = new Tree("5 ? 3");
		List<String> expected = Arrays.asList("5 ? 3", "5");
		assertEquals("Arrays are not equal", 
				expected,
				tree.getResultBySteps());
	}
	// custom tests from here
	@Test
	public void getResultStepsTestComplex() {
		Tree tree = new Tree("(4 & 6) % (2 ? 3)");
		List<String> expected = Arrays.asList("(4 & 6) % (2 ? 3)", "10 % (2 ? 3)", "10 % 3", "1");
		assertEquals("Arrays are not equal", 
				expected,
				tree.getResultBySteps());
	}
	
	@Test
	public void getResultRecGreaterThanNine() {
		Tree tree = new Tree("10 % 3");
		assertEquals(1, tree.getResultRec());
	}
	
	@Test
	public void complexItTest() {
		Tree tree = new Tree("((2 & 2) & 6) % (2 ? (1 ? 3))");
		assertEquals("Result should be 1", 1, tree.getResultIt());
		assertEquals("Result should be 1", 1, tree.getResultRec());
	}
	
	@Test 
	public void WNA() {
		Tree tree = new Tree("(4 & &) % 2");
		//assert.fail();
	}
}
