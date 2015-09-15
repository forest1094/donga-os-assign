import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class sjf {
	static ArrayList<Process> alProcess = new ArrayList<Process>();		
	
	public static void main(String[] args) throws IOException {
		
		/*** File read ***/
		Scanner scanner = new Scanner(new FileReader("sjf.inp"));
		
		scanner.nextInt();
		
		while(scanner.hasNext()) {
			int arrInt[] = new int[3];
			
			for(int i=0; i<3; i++){
				arrInt[i] = scanner.nextInt();
			}
			
			Process p = new Process(arrInt);
			alProcess.add(p);
		}
		
		scanner.close();
		
		/*** test code ***/
//		Collections.sort(alProcess, new ArrivalCompare());
//		
//		for(int i=0; i<alProcess.size(); i++) {
//			Process p0 = alProcess.get(i);
//			System.out.print(p0.getProcess() + " " + p0.getArrival() + " " + p0.getBurst());
//			System.out.println();
//			
//		}
		
		/*** calculate waiting time ***/		
		int waiting = 0, now = 0;  
		Collections.sort(alProcess, new ArrivalCompare());
		
		while(!alProcess.isEmpty()) {
			ArrayList<Process> tempAlProcess = new ArrayList<Process>();
		
			for(int i=0; i<alProcess.size(); i++)
			{
				if (now >= alProcess.get(i).getArrival())
					tempAlProcess.add(alProcess.get(i));
			}
			
			if(tempAlProcess.isEmpty()) {
				Collections.sort(alProcess, new ArrivalCompare());
				tempAlProcess.add(alProcess.get(0));
			}
			
			Collections.sort(tempAlProcess, new BurstCompare());
			
			//System.out.println(tempAlProcess.get(0).getArrival());
			if (now - tempAlProcess.get(0).getArrival() > 0) {
				waiting += (now - tempAlProcess.get(0).getArrival());
				now = now + tempAlProcess.get(0).getBurst();
			}
			
			else
				now = tempAlProcess.get(0).getArrival() + tempAlProcess.get(0).getBurst();
			
			alProcess.remove(tempAlProcess.get(0));
		}
		
		System.out.println(waiting);
		
		/*** write result ***/
		FileWriter fw = new FileWriter("sjf.out");
		fw.write(String.valueOf(waiting));
		fw.close();
	}

}

/*** Process class define ***/
class Process{
	
	private int process;
	private int arrival;
	private int burst;
	
	public Process(int arrayInt[]) {
		this.setProcess(arrayInt[0]);
		this.setArrival(arrayInt[1]);
		this.setBurst(arrayInt[2]);
	}
	
//	public Process(ArrayList<Integer> al){
//		this.setProcess(al.get(0));
//		this.setArrival(al.get(1));
//		this.setBurst(al.get(2));
//	}
	
	public int getProcess() {
		return process;
	}

	public void setProcess(int process) {
		this.process = process;
	}

	public int getArrival() {
		return arrival;
	}

	public void setArrival(int arrival) {
		this.arrival = arrival;
	}

	public int getBurst() {
		return burst;
	}

	public void setBurst(int burst) {
		this.burst = burst;
	}
}

/*** Process class sorting ***/
class ArrivalCompare implements Comparator<Process>{
	@Override
	public int compare(Process p1, Process p2) {
		// TODO Auto-generated method stub
		if(p1.getArrival() < p2.getArrival()){
			return -1;
		}else if(p1.getArrival() == p2.getArrival()){
			if(p1.getBurst() < p2.getBurst()){
				return -1;
			}else{
				return 1;
			}
		}else{
			return 1;
		}
		//return p1.getArrival() < p2.getArrival() ? -1 : p1.getArrival() > p2.getArrival() ? 1:0;
	}		
	
}

class BurstCompare implements Comparator<Process>{
	@Override
	public int compare(Process p1, Process p2) {
		// TODO Auto-generated method stub
		if(p1.getBurst() < p2.getBurst()){
			return -1;
		}else if(p1.getBurst() == p2.getBurst()){
			if(p1.getArrival() < p2.getArrival()){
				return -1;
			}else{
				return 1;
			}
		}else{
			return 1;
		}
		//return p1.getArrival() < p2.getArrival() ? -1 : p1.getArrival() > p2.getArrival() ? 1:0;
	}		
	
}
