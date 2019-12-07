package Ass1;

import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

public class KnapsackGA {
	Vector<Solution> generation;
	TestCase casee;

	public KnapsackGA(TestCase casee) {
		this.casee = casee;
	}

	public void generatePopulation() {
		generation = new Vector<Solution>();
		int numOfItems = casee.getNoOfItems();

		int tmpWeight = 0, tmpValue = 0;
		for (int i = 0; i < 100; i++) {
			int[] codearr = new int[numOfItems];
			String code = "";
			for (int j = 0; j < numOfItems; j++) {
				double x = Math.random();

				if (x >= 0.5 && (tmpWeight + casee.getVec().get(j).getWeight() < casee.getSize())) {
					code += "1";
					codearr[j] = 1;
					tmpWeight += casee.getVec().get(j).getWeight();
					tmpValue += casee.getVec().get(j).getValue();

				} else {
					code += "0";
					codearr[j] = 0;
				}
			}

			generation.add(new Solution(code, tmpValue, tmpWeight, codearr));

			tmpValue = 0;
			tmpWeight = 0;
		}
	}

	public int maxValueIndex(Vector<Solution> pinkman) {
		// pinkman holds the population for which you wish to get the index of the
		// highest value
		Solution max = pinkman.get(0);
		int maxInd = 0;
		for (int i = 1; i < pinkman.size(); i++) {
			if (max.getValue() < pinkman.get(i).getValue()) {
				max = pinkman.get(i);
				maxInd = i;
			}
		}

		return maxInd;

	}

	public Vector<Solution> rouletteSelection() {
		int totalFitness = 0;
		Vector<Solution> salamanca = new Vector<Solution>();
		for (int i = 0; i < generation.size(); i++) {
			totalFitness += generation.get(i).getValue();
		}

		int ind;
		for (int i = 0; i < generation.size(); i++) {
			double randomNum = Math.random() * (double) totalFitness;
			for (ind = 0; ind < generation.size() && randomNum > 0; ind++) {
				randomNum -= generation.get(ind).getValue();
			}
			if (ind >= 100)
				ind--;
			salamanca.add(generation.get(ind));
		}
		return salamanca;
	}

	public Vector<Solution> crosstate() {

		// Gustavo is the Generation after the Selection
		Vector<Solution> gustavo = rouletteSelection();
		// Walter will hold new Offsprings
		
		int chromosomeLength = gustavo.get(0).getCode().length();

		for (int i = 0; i < 1000; i++) {
			Vector<Solution> walter = new Vector<Solution>();
			for (int j = 0; j < gustavo.size()/2; j++) {
				Solution parent1 = gustavo.get(j);
				Solution parent2 = gustavo.get(j + 50);
				Solution offspring1 = new Solution();
				Solution offspring2 = new Solution();

				Random rand = new Random();

				int r1 = rand.nextInt(chromosomeLength-1);

				while (r1 == 0)
					r1 = rand.nextInt(chromosomeLength-1);

				double r2 = Math.random();

				if (r2 < 0.71) {

					offspring1.setCode(parent1.getCode().substring(0, r1) + parent2.getCode().substring(r1));
					offspring1.setWeight(calcWeight(offspring1.getCode()));
					offspring1.setValue(calcValue(offspring1.getCode()));
					
					offspring2.setCode(parent2.getCode().substring(0, r1) + parent1.getCode().substring(r1));
					offspring2.setWeight(calcWeight(offspring2.getCode()));
					offspring2.setValue(calcValue(offspring2.getCode()));

					

				} 
				else if(r2 >= 0.71){
					offspring1.setCode(parent1.getCode());
					offspring1.setValue(parent1.getValue());
					offspring1.setWeight(parent1.getWeight());

					offspring2.setCode(parent2.getCode());
					offspring2.setValue(parent2.getValue());
					offspring2.setWeight(parent2.getWeight());

				}

				// For Offspring1 mutation;
				r1 = rand.nextInt(chromosomeLength);

				r2 = Math.random();
				String flip = "";
				if (r2 <= 0.1) {
					if (offspring1.getCode().charAt(r1) == '1') {
						flip = "0";
					} else {
						flip = "1";
					}
					offspring1.setCode(
							offspring1.getCode().substring(0, r1) + flip + offspring1.getCode().substring(r1 + 1));

					offspring1.setWeight(calcWeight(offspring1.getCode()));
					offspring1.setValue(calcValue(offspring1.getCode()));
				}

				// For Offspring2 mutation;
				
				r1 = rand.nextInt(chromosomeLength);
				
				r2 = Math.random();
				String flip2 = "";
				if (r2 <= 0.1) {
					if (offspring2.getCode().charAt(r1) == '1') {
						flip2 = "0";
					} else {
						flip2 = "1";
					}
//
//					if(r1 != 4)
//					{
//					offspring2.setCode(
//							offspring2.getCode().substring(0, r1) + flip2 + offspring2.getCode().substring(r1 + 1));
//					}
//					else
//					{
						offspring2.setCode(
								offspring2.getCode().substring(0, r1) + flip2 + offspring2.getCode().substring(r1+1));
//					}
					offspring2.setWeight(calcWeight(offspring2.getCode()));

					offspring2.setValue(calcValue(offspring2.getCode()));

				}
				
				if (offspring1.getWeight() > casee.getSize()) {
					String tmpcode = adjust(offspring1.getCode());
					int tmpWeight = calcWeight(tmpcode);
					while (tmpWeight > casee.getSize()) {
						tmpcode = adjust(tmpcode);
						tmpWeight = calcWeight(tmpcode);
						
					}
					offspring1.setCode(tmpcode);
					offspring1.setWeight(tmpWeight);
					offspring1.setValue(calcValue(offspring1.getCode()));
					
				}

				if (offspring2.getWeight() > casee.getSize()) {
					String tmpcode = adjust(offspring2.getCode());
					int tmpWeight = calcWeight(tmpcode);
					while (tmpWeight > casee.getSize()) {
						tmpcode = adjust(tmpcode);
						tmpWeight = calcWeight(tmpcode);
					}
					offspring2.setCode(tmpcode);
					offspring2.setWeight(tmpWeight);
					offspring2.setValue(calcValue(offspring2.getCode()));
					
				}
				
				walter.add(offspring1);
				walter.add(offspring2);
				flip = "";
				flip2 = "";

			}

			int maxInd = maxValueIndex(gustavo);
			Solution bestFit = new Solution(gustavo.get(maxInd));
			walter.remove(maxInd);
			walter.add(bestFit);
			gustavo = new Vector<Solution>(walter);


		}
		return gustavo;

	}

	public String adjust(String codeToFix) {

		double minRatio = 1000000000;
		int ind = 0;

		for (int i = 0; i < codeToFix.length(); i++) {
			double tmpmin = (double) casee.getItems().get(i).getValue() / casee.getItems().get(i).getWeight();
			if (tmpmin < minRatio && codeToFix.charAt(i) != '0') {
				ind = i;
				minRatio = tmpmin;
			}
		}
		
		String tmp = codeToFix.substring(0, ind) + "0" + codeToFix.substring(ind + 1);
		
		return tmp;
	}

	public int calcWeight(String flynn) {
		int weight = 0;
		for (int i = 0; i < flynn.length(); i++) {
			weight += Integer.parseInt(flynn.charAt(i) + "") * casee.getItems().get(i).getWeight();
		}
		return weight;
	}
	public int calcValue(String lewis) {
		int value = 0;
		for (int i = 0; i < lewis.length(); i++) {
			value += Integer.parseInt(lewis.charAt(i) + "") * casee.getItems().get(i).getValue();
		}
		return value;
	}
}
