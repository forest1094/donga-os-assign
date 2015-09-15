/*import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Scanner;

public class banker {
	static ArrayList<Integer> maxResource = new ArrayList<Integer>();
	static ArrayList<Process> alProcess = new ArrayList<Process>();
	static ArrayList<Instruction> alInstruct = new ArrayList<Instruction>();
	static ArrayList<Instruction> readyQueue = new ArrayList<Instruction>();
//	static ArrayList<Integer> availResource = new ArrayList<Integer>();
	static int n, m;
	
	public static void main(String[] args) throws IOException {
		
		*//*** File read part ***//*
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
			System.out.println();System.out.println();
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
		
		// get available resource
		ArrayList<Integer> availResource = getAvailResource(alProcess);
		
		System.out.println("\navail resource: ");
		for(int i=0; i<m; i++) {
			System.out.print(availResource.get(i) + " ");
		}
		
		*//*** Banker's algorithm part ***//*
		FindSafe f = new FindSafe(alProcess, availResource, alInstruct.get(0));
		if(f.isSafeInstruction())
			System.out.println("can");
		else
			System.out.println("can not");
			
		
			
			
	}
	
	// get available resource method
	public static ArrayList<Integer> getAvailResource(ArrayList<Process> aP) {
		ArrayList<Integer> avail = new ArrayList<>();
		for(int i=0; i<m; i++) {
			int temp = 0;
			for(int j=0; j<n; j++) {
				temp += aP.get(j).getpAlloc().get(i);
			}
			avail.add(maxResource.get(i)-temp);
		}
		return avail;
	}
	
	public static ArrayList<Integer> decreaseAvailResource(ArrayList<Integer> av, ArrayList<Integer> request) {
		for(int i=0; i<request.size(); i++)
			av.set(i, av.get(i)-request.get(i));
		return av;
	}
}

class Process {
	private ArrayList<Integer> pMax = new ArrayList<Integer>();
	private ArrayList<Integer> pAlloc = new ArrayList<Integer>();
	private ArrayList<Integer> pNeed = new ArrayList<Integer>();
	private Boolean pCheck;
	
	public Process(ArrayList<Integer> arr){
		setpMax(arr);
		setpCheck(false);
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
	public void setpCheck(Boolean pCheck) {
		this.pCheck = pCheck;
	}
	public Boolean getpCheck() {
		return pCheck;
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
	public void decreaseNeed(ArrayList<Integer> request) {
		for(int i=0; i<request.size(); i++)
			pNeed.set(i, pNeed.get(i)-request.get(i));
	}
	public void increaseAlloc(ArrayList<Integer> request) {
		for(int i=0; i<request.size(); i++)
			pAlloc.set(i, pAlloc.get(i)+request.get(i));
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
	private Boolean instruct;
	private Boolean iCheck;
	private int pNum;
	private ArrayList<Integer> re = new ArrayList<Integer>();
	
	public Instruction(String s) {
		setiCheck(true);
		
		if(s.equals("request"))
			setInstruct(true);
		else
			setInstruct(false);
	}
	public Boolean getInstruct() {
		return instruct;
	}
	public void setInstruct(Boolean instruct) {
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
	public Boolean getiCheck() {
		return iCheck;
	}
	public void setiCheck(Boolean iCheck) {
		this.iCheck = iCheck;
	}
	public void printAll() {
		System.out.println(this.instruct + " " + this.pNum);
		for(int i=0; i<re.size(); i++)
			System.out.print(re.get(i) + " ");
		System.out.println();
	}
}

class FindSafe extends banker{
	private ArrayList<Process> tempProcess;
	private ArrayList<Integer> tempAvail;
	private Instruction tI;

	public FindSafe(ArrayList<Process> tP, ArrayList<Integer> tA, Instruction tI) {
		setTempProcess(tP);
		setTempAvail(tA);
		settI(tI);
	}

	public ArrayList<Process> getTempProcess() {
		return tempProcess;
	}
	public void setTempProcess(ArrayList<Process> tempProcess) {
		this.tempProcess = tempProcess;
	}
	public ArrayList<Integer> getTempAvail() {
		return tempAvail;
	}
	public void setTempAvail(ArrayList<Integer> tempAvail) {
		this.tempAvail = tempAvail;
	}
	public Instruction gettI() {
		return tI;
	}
	public void settI(Instruction tI) {
		this.tI = tI;
	}
	public Boolean checkProcess(){					// 프로세스중 하나라도 false가 있으면 false 리턴
		for(int i=0; i<tempProcess.size(); i++) {
			if(!tempProcess.get(i).getpCheck())
				return false;
		}
		return true;
	}
	public Boolean compareResource(int num){
		for(int i=0; i<tempAvail.size(); i++) {
			if(tempProcess.get(num).getpNeed().get(i) > tempAvail.get(i))
				return false;
		}
		return true;
	}
	public Boolean compareNeedAvail(Process p) {	// need와 avail 비교해 need가 더 작은게 있으면 false
		for(int i=0; i<tempAvail.size(); i++) {
			if(p.getpNeed().get(i) < tempAvail.get(i))
				return false;
		}
		return true;
	}
	public Boolean isDeadLock(){
		for(int i=0; i<tempProcess.size(); i++) {
			if(!tempProcess.get(i).getpCheck() && compareNeedAvail(tempProcess.get(i))) {
				return false;
			}
		}
		return true;
	}
	public void increaseAvail(ArrayList<Integer> pNeed) {
		for(int i=0; i<tempAvail.size(); i++) {
			tempAvail.set(i, tempAvail.get(i)+pNeed.get(i));
		}
	}
	
	public Boolean isSafeInstruction() {
		for(int i=0; i<tempAvail.size(); i++) {			// 인스트럭션이 avail보다 커서 수행 불가능
			if(tI.getRe().get(i) > tempAvail.get(i))
				return false;
		}
		for(int i=0; i<tempAvail.size(); i++)						// 요청을 수락해 availble 리소스 감소
			tempAvail.set(i, tempAvail.get(i)-tI.getRe().get(i));

		tempProcess.get(tI.getpNum()).decreaseNeed(tI.getRe());		// 요청을 수락해 해당 프로세스의 need 감소
				
		while(!checkProcess()) {
			if(isDeadLock())
				return false;
			
			for(int i=0; i<tempProcess.size(); i++) {
				if(compareNeedAvail(tempProcess.get(i))) {
					tempProcess.get(i).setpCheck(true);
					increaseAvail(tempProcess.get(i).getpNeed());
					break;
				}
			}
		}
		return true;
	}
 	
}*/