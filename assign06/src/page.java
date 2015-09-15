import java.io.BufferedReader;
import java.io.FileInputStream;
//import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
//import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

public class page {
	static ArrayList<Integer> refString = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		
		/***  File read Part  ***/
		Reader reader = new Reader();
		reader.init(new FileInputStream("page.inp"));
		
		String str = reader.next();
		int n = reader.nextInt();
		
		//Scanner scanner = new Scanner(new FileReader("page.inp"));
		//int n = scanner.nextInt();		// n is number of frame (memory size)
		
		//get referenceString
		while(true) {
			int ref = reader.nextInt();
			
			if (ref == -1)
				break;
			else
				refString.add(ref);
		}
		//scanner.close();
		
		/***  Run algorithm ***/
		FileWriter fw = new FileWriter("page.out");
		
		FIFO f = new FIFO(n, refString);
		fw.write("FIFO: ");
		fw.write(String.valueOf(f.runAlgorithm()) + "\n");
		
		LRU l = new LRU(n, refString);
		fw.write("LRU: ");
		fw.write(String.valueOf(l.runAlgorithm()) + "\n");
		
		OPT o = new OPT(n, refString);
		fw.write("OPT: ");
		fw.write(String.valueOf(o.runAlgorithm()) + "\n");
		
		fw.close();
	}
}

/////////////////////////////////////////////////////////////////////////////////////////////

class Reader {
    static BufferedReader reader;
    static StringTokenizer tokenizer;

    /** call this method to initialize reader for InputStream */
    void init(InputStream input) {
        reader = new BufferedReader(
                     new InputStreamReader(input) );
        tokenizer = new StringTokenizer("");
    }

    /** get next word */
    String next() throws IOException {
        while ( ! tokenizer.hasMoreTokens() ) {
            //TODO add check for eof if necessary
            tokenizer = new StringTokenizer(
                   reader.readLine() );
        }
        return tokenizer.nextToken();
    }

    int nextInt() throws IOException {
        return Integer.parseInt( next() );
    }
	
    double nextDouble() throws IOException {
        return Double.parseDouble( next() );
    }
}

/////////////////////////////////////////////////////////////////////////////////////////////

abstract class PageReplace {
	ArrayList<Integer> refString;
	int pageFault;
	int frameSize;
	
	abstract int runAlgorithm();
}

class FIFO extends PageReplace{
	private Queue<Integer> memory = new LinkedList<Integer>();
	
	public FIFO(int n, ArrayList<Integer> ref) {
		refString = ref;
		pageFault = 0;
		frameSize = n;
	}
	
	@Override
	int runAlgorithm() {
		for(int i=0; i<refString.size(); i++) {
			if(memory.size() < frameSize) {					//queue(memory) is not full
				if(!memory.contains(refString.get(i))) {		//page fault!!!
					pageFault++;
					memory.offer(refString.get(i));
				}
			} 
			else {											//queue(memory) is full
				if(!memory.contains(refString.get(i))) {		//page fault!!!
					pageFault++;
					memory.poll();
					memory.offer(refString.get(i));
				}
			}
		}
		System.out.println("FIFO's total pagefault: " + pageFault);
		
		return pageFault;
	}
}

class LRU extends PageReplace{
	private Stack<Integer> memory = new Stack<>();

	public LRU(int n, ArrayList<Integer> ref) {
		refString = ref;
		pageFault = 0;
		frameSize = n;
	}
	
	@Override
	int runAlgorithm() {
		for(int i=0; i<refString.size(); i++) {
			if(memory.size() < frameSize) {					//stack(memory) is not full
				if(!memory.contains(refString.get(i))) {		//page fault
					pageFault++;
					memory.push(refString.get(i));
				}
				else {											//not page fault
					memory.remove(refString.get(i));				//move data to stack's head
					memory.push(refString.get(i));				
				}
			}
			else {											//stack(memory) is full
				if(!memory.contains(refString.get(i))) {		//page fault
					pageFault++;
					memory.remove(0);
					memory.push(refString.get(i));
				}
				else {											//not page fault
					memory.remove(refString.get(i));				//move used data to stack's head
					memory.push(refString.get(i));		
				}
			}
		}
		System.out.println("LRU's total pagefault: " + pageFault);
		return pageFault;
	}
}

class OPT extends PageReplace{
	private ArrayList<Integer> memory = new ArrayList<>();

	public OPT(int n, ArrayList<Integer> ref) {
		refString = ref;
		pageFault = 0;
		frameSize = n;
	}
	
	int findVictim(int index) {		//return will not be used page for longest time
		ArrayList<Integer> nextIndex = new ArrayList<>();
		
		for(int i=0; i<memory.size(); i++) {
			nextIndex.add(refString.size()+1);
		}
		
		//get every pages's next use time
		for(int j=0; j<memory.size(); j++) {			
			for(int i=index; i<refString.size(); i++) {
				if(memory.get(j) == refString.get(i)) {
					nextIndex.set(j, i);
					break;
				}
			}
		}
		
		int temp = nextIndex.get(0);
		for(int i=1; i<memory.size(); i++) {
			if(temp < nextIndex.get(i)) {
				temp = nextIndex.get(i);
			}
		}
		return memory.get(nextIndex.indexOf(temp));
	}
	
	@Override
	int runAlgorithm() {
		for(int i=0; i<refString.size(); i++) {
			if(memory.size() < frameSize) {					//arraylist(memory) is not full
				if(!memory.contains(refString.get(i))) {		//page fault!!!
					pageFault++;
					memory.add(refString.get(i));
				}
			} 
			else {											//arraylist(memory) is full
				if(!memory.contains(refString.get(i))) {		//page fault!!!
					Integer rm = findVictim(i);
					pageFault++;
					memory.remove(rm);
					memory.add(refString.get(i));
				}
			}
		}
		System.out.println("OPT's total pagefault: " + pageFault);
		return pageFault;
	}
}