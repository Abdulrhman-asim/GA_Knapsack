package Ass1;
import java.io.File;
import java.util.Scanner;
import java.util.Vector;

public class RFF {
	static public Vector<TestCase> readFromFile() {

		try {
			Scanner in = new Scanner(new File("input_example.txt"));
			int numOfCases;
			numOfCases = in.nextInt();
			Vector<TestCase> testCases = new Vector<TestCase>();
			for(int i = 0; i < numOfCases; i++)
			{
				TestCase T = new TestCase();
				T.setNoOfItems(in.nextInt());
				T.setSize(in.nextInt());

				for(int j = 0; j < T.getNoOfItems(); j++)
				{
					int tmpW = in.nextInt();
					int tmpV = in.nextInt();
					T.addItem(tmpW,tmpV);
				}
				testCases.add(T);
			}
			in.close();
			return testCases;
		} catch (Exception E) {
			System.out.println("error404 not found!!");
		}
		return null;
	}
}