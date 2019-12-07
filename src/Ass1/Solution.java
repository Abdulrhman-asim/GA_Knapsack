package Ass1;

public class Solution {
	String code;
	int value;
	int weight;
	int[] code1;
	
	public Solution(String code2, int tmpValue, int tmpWeight, int[] codearr) {
		code = code2;
		value = tmpValue;
		weight = tmpWeight;
		code1 = codearr;
	}
	public Solution() {
		// TODO Auto-generated constructor stub
	}
	public Solution(Solution x) {
		code = x.getCode();
		value = x.getValue();
		weight = x.getWeight();
		code1 = x.getCode1();
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public int itemsUsed()
	{
		int counter = 0;
		
		for(int i=0; i < code.length(); i++)
		{
			if(code.charAt(i) == '1')
			{
				counter++;
			}
			
		}
		return counter;
	}
	public int[] getCode1() {
		return code1;
	}
	public void setCode1(int[] code1) {
		this.code1 = code1;
	}
	
}
