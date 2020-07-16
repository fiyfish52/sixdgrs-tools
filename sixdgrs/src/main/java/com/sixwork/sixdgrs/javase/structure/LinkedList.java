package com.sixwork.sixdgrs.javase.structure;

/**
 * 单链表
 * @author zlp
 * @time 2020年7月16日下午5:41:47
 */
public class LinkedList {
	
	
	/**
	 * 使用内部类创建链表节点；
	 * @author zlp
	 * @time 2020年7月16日下午5:33:24
	 */
	class Node{
		Integer data;
		Node next = null;
		Node(Integer data){
			this.data = data;
		}
	}
	
	
	Node head = null;
	
	/**
	 * 链表添加节点
	 * 如果头结点指向空，则直接连在头结点后面；
	 * 如果头结点不为空，那么需要找到链表的最后一个非空节点，并连在后面
	 * @author zlp
	 * @time 2020年7月16日下午5:38:44
	 * @param data
	 */
	public void addNode(Integer data) {
		Node nextNode = new Node(data);
		if(null==head) {
			head = nextNode;
		}else {
			Node temp = head;
			while(null != temp.next) {
				temp = temp.next;
			}
			temp.next = nextNode;
		}
	}
	
	public void printAllNodes() {
		Node currentNode = head;
		while(null!=currentNode) {
			System.out.print(currentNode.data+" ");
			currentNode = currentNode.next;
		}
		
	}
	
	public static void main(String[] adsfdf) {
		LinkedList l = new LinkedList();
		for(int i=0;i<10;i++) {
			l.addNode(i);
		}
		l.printAllNodes();
	}
	
	
}


