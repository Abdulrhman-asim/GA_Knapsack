package Ass1;
import java.util.Vector;


public class TestCase {
	int noOfItems;
	int size;
	Vector<Pairr> items;
	
	public TestCase()
	{
		noOfItems = 0;
		size = 0;
		items = new Vector<Pairr>();
	}
	
	public int getNoOfItems() {
		return noOfItems;
	}
	public void setNoOfItems(int noOfItems) {
		this.noOfItems = noOfItems;
	}
	public int getSize() {
		return size;
	}
	public Vector<Pairr> getItems() {
		return items;
	}
	public void setItems(Vector<Pairr> items) {
		this.items = items;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public Vector<Pairr> getVec() {
		return items;
	}
	
	public void addItem(int weight, int value)
	{
		items.add(new Pairr(weight, value));
	}
	
}