import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class fcfs 
{
	static ArrayList<Process> processList = new ArrayList<Process>();
	
	public static void main(String[] args) throws IOException {
		int arrayInt[] = new int[3];
		
		int bursted=0, arrival=0, waiting=0;
		int temp;
		
		// file open and set data
		//BufferedReader br = new BufferedReader(new FileReader("fcfs.inp"));
		
		
		Scanner scanner = new Scanner(new FileReader("fcfs.inp"));
		
		int count = scanner.nextInt();
		
		//int count = Integer.parseInt(br.readLine().trim());
				
		
		while(scanner.hasNext()) {
			
			ArrayList<Integer> alInt = new ArrayList<Integer>();
			
//			String line = br.readLine();
//	        if (line.equals("") || line.equals(null)) break;
	        //System.out.println(line);
//	        String[] arrayStr = line.split(" ");
	        
//	        for(int i=0; i<arrayStr.length; i++) {
	        	//arrayInt[i] = Integer.parseInt(arrayStr[i]);
//	        	alInt.add(Integer.parseInt(arrayStr[i]));
//	        }
			for(int j=0; j<3; j++) {
				int test = scanner.nextInt();
				alInt.add(test);
				
			}
				       	        
	        //Process p = new Process(arrayInt);
	        Process alP = new Process(alInt);
	        
	       
	        processList.add(alP);
	        
	        
		}
		scanner.close();
		//br.close();
		
		// sort data list
		Collections.sort(processList, new ArrivalCompare());
		
		for(int i=0; i<processList.size(); i++) {
			Process p9 = processList.get(i);
			//System.out.print(p9.getProcess() + " " + p9.getArrival() + " " + p9.getBurst());
			//System.out.println();
		}
		
		// print data list
//		for(int i=0; i<processList.size(); i++)
//			System.out.println(processList.get(i).getProcess());
		
		// get waiting time
		bursted = processList.get(0).getBurst();
		arrival = processList.get(0).getArrival();
		
		for(int i=1; i<processList.size(); i++) {
			temp = (bursted + arrival) - processList.get(i).getArrival();
			
			if (temp > 0) {
				waiting += temp;
				bursted += processList.get(i).getBurst();
			}
			
			else {
				bursted = processList.get(i-1).getBurst();
				arrival = processList.get(i-1).getArrival();
			}
		}
		
		//System.out.println(waiting);
		
		// write result
		FileWriter fw = new FileWriter("fcfs.out");
		fw.write(String.valueOf(waiting));
        fw.close();
    }
	
}

class Process{
	
	private int process;
	private int arrival;
	private int burst;
	
	public Process(int arrayInt[]) {
		this.setProcess(arrayInt[0]);
		this.setArrival(arrayInt[1]);
		this.setBurst(arrayInt[2]);
	}
	
	public Process(ArrayList<Integer> al){
		this.setProcess(al.get(0));
		this.setArrival(al.get(1));
		this.setBurst(al.get(2));
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
	
	
}

class ArrivalCompare implements Comparator<Process>{
	@Override
	public int compare(Process p1, Process p2) {
		// TODO Auto-generated method stub
		if(p1.getArrival() < p2.getArrival()){
			return -1;
		}
		else if(p1.getArrival() == p2.getArrival()){
			
			if(p1.getBurst() < p2.getBurst()){
				return -1;
			}else{
				return 1;
			}
			
		}else{
			return 1;
		}
		
		
//		return p1.getArrival() < p2.getArrival() ? -1 : p1.getArrival() > p2.getArrival() ? 1:0;
	}		
	
}
