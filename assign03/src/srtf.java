import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class srtf {
	static ArrayList<Process> alProcess = new ArrayList<Process>();
	static ArrayList<Process> alFinish = new ArrayList<Process>();
	
	public static void main(String[] args) throws IOException {
		
		/*** File read ***/
		Scanner scanner = new Scanner(new FileReader("srtf.inp"));
		
		scanner.nextInt();				// ignore first line
		
		while(scanner.hasNext()) {
			int arrInt[] = new int[3];
			
			for(int i=0; i<3; i++){
				arrInt[i] = scanner.nextInt();
			}
			
			Process p = new Process(arrInt);
			alProcess.add(p);
		}
		
		scanner.close();
		Collections.sort(alProcess, new ArrivalCompare());
		
//		for(int i=0; i<alProcess.size(); i++) {
//			Process p0 = alProcess.get(i);
//			System.out.print(p0.getProcess() + " " + p0.getArrival() + " " + p0.getBurst());
//			System.out.println();
//			
//		}
		/*** calculate waiting time ***/
		int waiting = 0;
		
		while(!alProcess.isEmpty()) {
			ArrayList<Process> tempAl = new ArrayList<Process>();		// create temp arraylist
			
			Collections.sort(alProcess, new ArrivalCompare());			// sort arraylist by arrival time
		
			System.out.println();
			int temp = alProcess.get(0).getArrival();
			
			try {
				while(true) {
					if(temp == alProcess.get(0).getArrival()) {
						tempAl.add(alProcess.get(0));
						alProcess.remove(0);
					}
					else break;
				}
			} catch (Exception e) {
				break;
			}
			
			
			//////////////////////////////////////////////////////
			int now = tempAl.get(0).getArrival();
			int next = alProcess.get(0).getArrival();
			
			System.out.println("now: "+now);
			System.out.println("next: "+next);
			
			if (now + tempAl.get(0).getBurst() > next) {
				tempAl.get(0).decreaseBurst(next-now);
				tempAl.get(0).setArrival(next);
				
				for(int j=1; j<tempAl.size(); j++) {
					tempAl.get(j).setArrival(next);
					waiting=next-now;
					tempAl.get(j).encreaseWaiting(next-now);
					alProcess.add(tempAl.get(j));
				}
				
				if(tempAl.get(0).getBurst() > 0) 
					alProcess.add(tempAl.get(0));
				else
					alFinish.add(tempAl.get(0));
			}
			
			else {
				for(int j=1; j<tempAl.size(); j++) {
					tempAl.get(j).setArrival(now + tempAl.get(0).getBurst());
					tempAl.get(j).encreaseWaiting(tempAl.get(0).getBurst());
					alProcess.add(tempAl.get(j));
				}
				
				tempAl.get(0).setBurst(0);
				alFinish.add(tempAl.get(0));
			}
			for(int i=0; i<alProcess.size(); i++) {
				Process p0 = alProcess.get(i);
				System.out.print(p0.getProcess() + " " + p0.getArrival() + " " + p0.getBurst());
				System.out.println();
				
			}
		}
		
		for(int i=0; i<alProcess.size(); i++)
			waiting += alProcess.get(i).getWaiting();
		
		System.out.println("result : " + waiting);
		
		/*** write result ***/
		FileWriter fw = new FileWriter("srtf.out");
		fw.write(String.valueOf(waiting));
		fw.close();
	}

}

/*** Process class define ***/
class Process{
	
	private int process;
	private int arrival;
	private int burst;
	private int waiting;
	
	public Process(int arrayInt[]) {
		this.setProcess(arrayInt[0]);
		this.setArrival(arrayInt[1]);
		this.setBurst(arrayInt[2]);
		this.setWaiting(0);
	}
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
	public int getWaiting() {
		return waiting;
	}
	public void setWaiting(int waiting) {
		this.waiting = waiting;
	}
	public void decreaseBurst(int val) {
		this.burst -= val;
	}
	public void encreaseArrival(int val) {
		this.arrival += val;
	}
	public void encreaseWaiting(int val) {
		this.waiting += val;
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
	}		
	
}