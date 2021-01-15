package com.smt.data.tree;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/****************************************************************************
 * <b>Title</b>: TreeTest.java
 * <b>Project</b>: SpaceLibs-Java
 * <b>Description: </b> CHANGE ME!!
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author James Camire
 * @version 3.0
 * @since Jan 14, 2021
 * @updates:
 ****************************************************************************/
class TreeTest {
	
	private Node root;
	private List<Node> nodes = new ArrayList<>();

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUpBeforeEach() throws Exception {
		root = new Node("root_node_id", null);
		Node firstChild = new Node("child_node_1", "root_node_id");
		Node secondChild = new Node("child_node_2", "root_node_id");
		Node firstFirstChild = new Node("child_node_3", "child_node_1");

		nodes.add(root);
		nodes.add(firstChild);
		nodes.add(secondChild);
		nodes.add(firstFirstChild);
	}

	/**
	 * Test method for {@link com.smt.data.tree.Tree#hashCode()}.
	 */
	@Test
	void testHashCode() {
		Tree t = new Tree(nodes, root);
		assertEquals(90184, t.hashCode());	
		
		t = new Tree(nodes, null);
		assertEquals(21254, t.hashCode());
		
		t = new Tree(nodes);
		assertEquals(21254, t.hashCode());	
	}

	/**
	 * Test method for {@link com.smt.data.tree.Tree#findNode(java.lang.String)}.
	 */
	@Test
	void testFindNode() {
		Tree t = new Tree(nodes, root);
		assertEquals("child_node_1", t.findNode("child_node_1").getNodeId());
		assertEquals(null, t.findNode("no_node_id"));
	}

	/**
	 * Test method for {@link com.smt.data.tree.Tree#preorderList()}.
	 */
	@Test
	void testPreorderList() {
		Tree t = new Tree(nodes, root);
		assertEquals(3, t.getPreorderList().size());
	}

	/**
	 * Test method for {@link com.smt.data.tree.Tree#getPreorderList()}.
	 */
	@Test
	void testGetPreorderList() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.smt.data.tree.Tree#preorderList(boolean)}.
	 */
	@Test
	void testPreorderListBoolean() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.smt.data.tree.Tree#preorderList(com.smt.data.tree.Node)}.
	 */
	@Test
	void testPreorderListNode() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.smt.data.tree.Tree#createPreorder(java.util.List, java.util.List)}.
	 */
	@Test
	void testCreatePreorder() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.smt.data.tree.Tree#calculateTotalChildren(com.smt.data.tree.Node)}.
	 */
	@Test
	void testCalculateTotalChildren() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.smt.data.tree.Tree#getDepth()}.
	 */
	@Test
	void testGetDepth() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.smt.data.tree.Tree#setRootNode(com.smt.data.tree.Node)}.
	 */
	@Test
	void testSetRootNode() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.smt.data.tree.Tree#getRootNode()}.
	 */
	@Test
	void testGetRootNode() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.smt.data.tree.Tree#buildNodePaths()}.
	 */
	@Test
	void testBuildNodePaths() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.smt.data.tree.Tree#buildNodePaths(java.lang.String)}.
	 */
	@Test
	void testBuildNodePathsString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.smt.data.tree.Tree#buildNodePaths(java.lang.String, boolean)}.
	 */
	@Test
	void testBuildNodePathsStringBoolean() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.smt.data.tree.Tree#buildNodePaths(com.smt.data.tree.Node, java.lang.String, boolean)}.
	 */
	@Test
	void testBuildNodePathsNodeStringBoolean() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.smt.data.tree.Tree#equals(java.lang.Object)}.
	 */
	@Test
	void testEqualsObject() {
		fail("Not yet implemented");
	}

}
