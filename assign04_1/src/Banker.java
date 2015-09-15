import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Banker {
	static ArrayList<Integer> maxResource = new ArrayList<Integer>();
	static ArrayList<Process> alProcess = new ArrayList<Process>();
	static ArrayList<Instruction> alInstruct = new ArrayList<Instruction>();
	static ArrayList<Instruction> readyQueue = new ArrayList<Instruction>();
	static ArrayList<Integer> availResource = new ArrayList<Integer>();
	static int n, m;
	
	public static void main(String[] args) throws IOException {
		
		/*** File read part ***/
		Scanner scanner = new Scanner(new FileReader("banker.inp"));
		
		n = scanner.nextInt();		// n is number of process
		m = scanner.nextInt();		// m is number of resource
		
		System.out.println("prcess number: " + n);
		System.out.println("resource number: " + m);
				
		// get max resource
		for(int i=0; i<m; i++) {
			int num = scanner.nextInt();
			maxResource.add(num);
			System.out.print(num + " ");
		}
		System.out.println("\n");
		
		// get process's max request
		for(int i=n; i>0; i--) {
			ArrayList<Integer> arrInt = new ArrayList<>();
			for(int j=m; j>0; j--) {
				arrInt.add(scanner.nextInt());
			}
			Process p = new Process(arrInt);
			alProcess.add(p);
		}
		
		// get process's allocation resource
		for(int i=0; i<n; i++) {
			ArrayList<Integer> arrInt = new ArrayList<>();
			for(int j=0; j<m; j++) {
				arrInt.add(scanner.nextInt());
			}
			alProcess.get(i).setpAlloc(arrInt);
		}
		
		// get process's need resource
		for(int i=0; i<alProcess.size(); i++)
			alProcess.get(i).setpNeed();
		
		// print process's all resource
		for(int i=0; i<alProcess.size(); i++) {
			System.out.println("process: " + i);
			alProcess.get(i).printAll();
			System.out.println("\n");
		}
		
		// get instruction
		while(true){
			String s = scanner.next();
			if(s.equals("quit"))	// exit program
				break;
			
			Instruction inst = new Instruction(s);
			inst.setpNum(scanner.nextInt());
			ArrayList<Integer> arrInt = new ArrayList<>();
			for(int i=0; i<m; i++)
				arrInt.add(scanner.nextInt());
			
			inst.setRe(arrInt);
			alInstruct.add(inst);
		}
		
		// print all instruction
		for(int i=0; i<alInstruct.size(); i++) {
			alInstruct.get(i).printAll();
		}
		scanner.close();
		
		/*** Banker's algorithm part ***/
		getAvailbleResource();
		
		System.out.println("\navail resource: ");
		for(int i=0; i<m; i++) {
			System.out.print(availResource.get(i) + " ");
		}
	}
	
	public static void getAvailbleResource() {
		for(int i=0; i<m; i++) {
			int temp = 0;
			for(int j=0; j<n; j++) {
				temp += alProcess.get(j).getpAlloc().get(i);
			}
			availResource.add(maxResource.get(i)-temp);
		}
	}
}

class Process {
	public ArrayList<Integer> pMax = new ArrayList<Integer>();
	public ArrayList<Integer> pAlloc = new ArrayList<Integer>();
	public ArrayList<Integer> pNeed = new ArrayList<Integer>();
	
	public Process(ArrayList<Integer> arr){
		setpMax(arr);
	}
	
	public void setpMax(ArrayList<Integer> pArr){
		for(int i=0; i<pArr.size(); i++) {
			pMax.add(pArr.get(i));
		}
	}
	public void setpAlloc(ArrayList<Integer> pArr){
		for(int i=0; i<pArr.size(); i++) {
			pAlloc.add(pArr.get(i));
		}
		
	}
	public void setpNeed(){
		for(int i=0; i<pMax.size(); i++) {
			pNeed.add(pMax.get(i)-pAlloc.get(i));
		}
	}
	public ArrayList<Integer> getpMax(){
		return pMax;
	}
	public ArrayList<Integer> getpAlloc(){
		return pAlloc;
	}
	public ArrayList<Integer> getpNeed(){
		return pNeed;
	}
	public void printAll(){
		System.out.print("pMax: ");
		for(int i=0; i<pMax.size(); i++)
			System.out.print(pMax.get(i) + " ");
		System.out.println();
		
		System.out.print("pAlloc: ");
		for(int i=0; i<pAlloc.size(); i++)
			System.out.print(pAlloc.get(i) + " ");
		System.out.println();
		
		System.out.print("pNeed: ");
		for(int i=0; i<pNeed.size(); i++)
			System.out.print(pNeed.get(i) + " ");
	}
}

class Instruction {
	public String instruct;
	public int pNum;
	public ArrayList<Integer> re = new ArrayList<Integer>();
	
	public Instruction(String s) {
		if(s.equals("request"))
			setInstruct("request");
		else
			setInstruct("release");
	}
	public String getInstruct() {
		return instruct;
	}
	public void setInstruct(String instruct) {
		this.instruct = instruct;
	}
	public int getpNum() {
		return pNum;
	}
	public void setpNum(int pNum) {
		this.pNum = pNum;
	}
	public ArrayList<Integer> getRe() {
		return re;
	}
	public void setRe(ArrayList<Integer> re) {
		this.re = re;
	}
	public void printAll() {
		System.out.println(this.instruct + " " + this.pNum);
		for(int i=0; i<re.size(); i++)
			System.out.print(re.get(i) + " ");
		System.out.println();
	}
}