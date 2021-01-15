package com.smt.data.tree;

// JDK
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Space Libs 1.x
import com.smt.core.HashCodeUtil;
import com.smt.data.text.StringUtil;

/****************************************************************************
 * <b>Title</b>: Node.java<p/>
 * <b>Description: Manages the node object for a tree structure</b>  
 * <p/>
 * <b>Copyright:</b> Copyright (c) 2005<p/>
 * <b>Company:</b> Silicon Mountain Technologies<p/>
 * @author James Camire
 * @version 1.0
 * @since Aug 31, 2011<p/>
 * <b>Changes: </b>
 * JC 11-08-12 fixed hashCode, equals, and clone methods to be properly implemented
 ****************************************************************************/
public class Node implements Serializable, Comparable<Node> {
	private static final long serialVersionUID = 1l;
	
	// Member Variables
	private String nodeId;
	private String nodeName;
	private String parentId;
	private String parentName;
	private int depthLevel;
	private int orderNo;
	private boolean leaf = true;
	private boolean root;
	private List<Node> children;
	private int totalChildren;
	private Object userObject;
	private String fullPath;
    
	/**
	 * Default COnstructir
	 */
	public Node() {
		children = new ArrayList<>();
	}
	
	/**
	 * Copy constructor to clone a node
	 * @param n
	 */
	public Node(Node n) {
		this.nodeId = n.nodeId;
		this.nodeName = n.nodeName;
		this.parentId = n.parentId;
		this.parentName = n.parentName;
		this.depthLevel = n.depthLevel;
		this.orderNo = n.orderNo;
		this.leaf = n.leaf;
		this.root = n.root;

		this.setChildren(new ArrayList<Node>());
		this.setUserObject(n.getUserObject());
	}
	
	/**
	 * sets the root node or node head
	 * @param nodeId
	 * @param parentId
	 */
	public Node(String nodeId, String parentId) {
		this();
		this.nodeId = nodeId;
		this.parentId = parentId;
	}
	
	
	/**
	 * sets the root node or node head as well as the extended data
	 * @param nodeId
	 * @param parentId
	 * @param userObject
	 */
	public Node(String nodeId, String parentId, Object userObject) {
		this();
		this.nodeId = nodeId;
		this.parentId = parentId;
		this.userObject = userObject;
	}
	
	/**
	 * sets the root node or node head
	 * @param nodeId
	 * @param parentId
	 * @param parentName
	 */
	public Node(String nodeId, String parentId, String parentName) {
		this(nodeId, parentId);
		this.parentName = parentName;
	}
	
	/**
	 * Adds a child node to this element
	 * @param child
	 */
	public void addChild(Node child) {
		child.setDepthLevel(depthLevel + 1);
		child.setRoot(false);
		child.setFullPath(fullPath + child.getFullPath());
		child.setParentName(nodeName);
		children.add(child);
		
		setLeaf(false);
	}

	/**
	 * Returns if this is a child node
	 * @param child
	 * @return
	 */
	public boolean isChild(Node child) {
		return children.contains(child);
	}

	/**
	 * Gets the node id
	 * @return
	 */
	public String getNodeId() {
		return nodeId;
	}

	/**
	 * Sets the node id
	 * @param nodeId
	 */
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * Gets the parent id
	 * @return
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * Sets the parent id
	 * @return
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * Gets the node name
	 * @return
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * Sets the node name
	 * @return
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * gets the user object.  This allows the node to store any type
	 * of data elements as part of the tree/node
	 * @return
	 */
	public Object getUserObject() {
		return userObject;
	}

	/**
	 * Sets the user object.  This allows the node to store any type
	 * of data elements as part of the tree/node
	 * @return
	 */
	public void setUserObject(Object userObject) {
		this.userObject = userObject;
	}
	
	/**
	 * Sets the node depth level from root
	 * @return
	 */
	public void setDepthLevel(int treeLevel) { 
		this.depthLevel = treeLevel;
	}
	
	/**
	 * Gets the node depth level from root
	 * @return
	 */
	public int getDepthLevel() {
		return depthLevel;
	}

	/**
	 * Describe whether this node is a leaf (true) or branch (false)
	 * @return
	 */
	public boolean isLeaf() {
		return leaf;
	}

	/**
	 * Sets the leaf type
	 * @param leaf
	 */
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	/**
	 * describes whether or not the node is root
	 * @return
	 */
	public boolean isRoot() {
		return root;
	}
	
	/**
	 * Sets the root node
	 * @return
	 */
	public void setRoot(boolean root) {
		this.root = root;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// do null check
		if (obj == null) return false;

		// do this check
		if (this == obj) return true;

		// check both objects belongs to same class or not
		// check that they're both Node objects, exclusive of sub-classes.
		if (this.getClass() != obj.getClass()) return false;

		Node n = (Node) obj;
		return (this.hashCode() == n.hashCode());
	}
	
	
	/**
	 * Revised to use HashCodeUtil instead of member variables for ORM and lazy-loading 
	 * 'know issues'.  Refer to com.silicontn.gis.Location
	 */
	@Override
	public int hashCode() {
		int result = HashCodeUtil.hash(this.getNodeId());
		result += HashCodeUtil.hash(this.getParentId());
		result += HashCodeUtil.hash(this.getChildren());
		result += HashCodeUtil.hash(this.getUserObject());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
    public String toString() {
        return StringUtil.getToString(this);
    }
    
    /**
     * Returns the number of child nodes
     * @return
     */
	public int getNumberChildren() {
		if (children.isEmpty()) return 0;
		else
			return children.size();
	}

	/**
	 * @return the children
	 */
	public List<Node> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<Node> children) {
		this.children = children;
	}

	/**
	 * @return the fullPath
	 */
	public String getFullPath() {
		return fullPath;
	}

	/**
	 * @param fullPath the fullPath to set
	 */
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	/**
	 * @return the parentName
	 */
	public String getParentName() {
		return parentName;
	}

	/**
	 * @param parentName the parentName to set
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	/**
	 * Returns the number of child nodes for this node
	 * @return
	 */
	public int getTotalChildren() {
		return totalChildren;
	}

	/**
	 * Sets the total number of children fo rthis node
	 * @param totalChildren
	 */
	public void setTotalChildren(int totalChildren) {
		this.totalChildren = totalChildren;
	}
	
	/**
	 * Returns the order number (ordering) for this node
	 * @return
	 */
	public int getOrderNo() {
		return orderNo;
	}

	/**
	 * sets the order number (ordering) for this node
	 * @return
	 */
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Node n) {
		return Integer.compare(getOrderNo(),n.getOrderNo());
	}
}
