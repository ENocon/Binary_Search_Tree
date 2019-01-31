//Programmer: Elizabeth Nocon
//Class: CS 1450.001 MW
//Assignment Number: 10
//Due Date: 11/28/2018
//Description: This program reads a text file that contain information on parrots. 
//The program then creates a binary search tree. For each parrot read from the file,
//the program creates a parrot and adds it to the binary search tree. After the entire
//file has been read, a levelOrder traversal will be performed on the binary tree
//to print the parrots information in the correct order. 

import java.util.Scanner;
import java.io.IOException;
import java.lang.Comparable;
import java.util.Queue;
import java.util.LinkedList;

public class EHortonAssignment10 
{
	public static void main(String[] args) throws IOException
	{
		//Create an instance of binary tree. 
		BinaryTree theTree = new BinaryTree();
		
		//Create an instance of the parrots text file. 
		java.io.File parrotsFile = new java.io.File("parrots.txt");
		
		//Create a scanner to read the file. 
		Scanner input = new Scanner(parrotsFile);
		
		//Read the text file, create parrot objects, and add parrots to the tree. 
		while(input.hasNext())
		{
			//Read the id and lyrics of each parrot. 
			int id = input.nextInt();
			String songWords = input.next();
			
			//Create parrot. 
			Parrot newParrot = new Parrot(id, songWords);
			
			//Add parrot to the binary tree. 
			theTree.insert(newParrot);
		}//while loop
		
		//Close scanner.
		input.close();
		
		//Call the levelOrder method to traverse the tree and print the lyrics. 
		theTree.levelOrder();
		
	}//Main Method
}//Assignment10

class Parrot implements Comparable<Parrot>
{
	//Private data fields.
	int id;
	String songWord;
	
	//Constructor
	Parrot(int idNumber, String songWord)
	{
		this.id = idNumber;
		this.songWord = songWord;
	}
	
	//Create a getter for the parrot's ID. 
	public int getIDNumber()
	{
		return id;
	}
	
	//Getter to return the lyrics the parrot has.
	public String sing()
	{
		return songWord;
	}
	
	//compareTo method for comparing parrots. 
	public int compareTo(Parrot otherParrot)
	{
		if(this.getIDNumber() < otherParrot.getIDNumber())
		{
			return -1;
		}
		if(this.getIDNumber() > otherParrot.getIDNumber())
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
} //Parrot Class

class BinaryTree 
{
	//Private data field. 
	Node root;
	
	//Constructor.
	BinaryTree()
	{
		root = null;
	}
	
	public boolean insert(Parrot parrotToAdd)
	{
		if(root == null)
		{
			root = new Node(parrotToAdd);
		}
		else
		{
			Node parent = null;
			Node current = root; 
			
			//While the bottom of the tree has not been reached, enter the loop. 
			//While loop purpose is to find where the new Parrot needs to be inserted.
			while(current != null)
			{
				//If newParrot's id is less than the current, move left on the tree. 
				if (parrotToAdd.compareTo(current.parrot) < 0)
				{
					parent = current;
					current = current.left;
				}
				//Else if newParrot's id is greater than current, then move right on the tree. 
				else if (parrotToAdd.compareTo(current.parrot) > 0)
				{
					parent = current;
					current = current.right;
				}
				else
				{
					//If you reach else, then the parrot is a duplicate.
					//So, return false, and do not insert the parrot duplicate.
					return false;
				}
			}//while loop
			//If the new parrot is less than the current, then add to the left.
			if (parrotToAdd.compareTo(parent.parrot) < 0)
			{
				parent.left = new Node(parrotToAdd);
			}
			else
			{
				parent.right = new Node(parrotToAdd);
			}
		}//else statement
		return true;
	}//insert method
	
	public void levelOrder()
	{
		//If you have a tree, then create a queue and put the root in the queue. 
		if(root != null)
		{
			Queue<Node> theQueue = new LinkedList<>();
			theQueue.offer(root);
			
			//continue to traverse the tree, while the queue is not empty
			while(!theQueue.isEmpty())
			{
				//remove a node
				Node tempNode = theQueue.remove();
				//print the parrot's words. 
				String lyrics = tempNode.parrot.sing();
				System.out.print(lyrics + " ");
				
				//if the temporary node has children, then add them to the queue. 
				if (tempNode.left != null)
				{
					theQueue.offer(tempNode.left);
				}
				if(tempNode.right != null)
				{
					theQueue.offer(tempNode.right);
				}
			}//While queue is not empty
		}// if binary tree is not empty
	} //levelOrder method
	
	//Inner Class Node
	private static class Node
	{
		//Private data fields. 
		Parrot parrot;
		Node left;
		Node right;
		
		//Constructor. 
		Node(Parrot newParrot)
		{
			this.parrot = newParrot;
			this.left = null;
			this.right = null;
		}
	}//Inner Node Class
}//BinaryTree class