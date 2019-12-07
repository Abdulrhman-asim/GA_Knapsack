package Ass1;

import java.util.Vector;

public class Main {

	public static void main(String[] args) {
		
		Vector<TestCase> testCases = RFF.readFromFile();
		for(int i = 0; i < testCases.size(); i++)
		{
			KnapsackGA solver = new KnapsackGA(testCases.get(i));
			Vector<Solution> finalGen = new Vector<Solution>(); 
			solver.generatePopulation();
			
			finalGen = solver.crosstate();
			int bestInd = solver.maxValueIndex(finalGen);
			System.out.println("Case " + (i+1) + ": " + finalGen.get(bestInd).getValue());
			System.out.println(finalGen.get(bestInd).itemsUsed());
			
			for(int j=0;j<finalGen.get(bestInd).getCode().length();j++)
			{
				if(finalGen.get(bestInd).getCode().charAt(j) == '1')
				{
					System.out.println(testCases.get(i).getItems().get(j).getWeight() + " " + testCases.get(i).getItems().get(j).getValue());
				}
			}
		}
	}

}
