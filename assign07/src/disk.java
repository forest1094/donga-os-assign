import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class disk {

	public static void main(String[] args) throws IOException {

		/***  File read Part  ***/
		Reader reader = new Reader();
		reader.init(new FileInputStream("disk.inp"));
		
		String select = reader.next();
		int s = reader.nextInt();
		
		System.out.println(select + " " + s);
		
		while(true) {
			int time = reader.nextInt();
			int track = reader.nextInt();
			
			if( time == -1 )
				break;
			
			System.out.println("time: " + time + "  track: " + track);
		}
		
		/***  Run algorithm  ***/
		
	}
}

//--------------------------------------------------------------------------

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
        while ( !tokenizer.hasMoreTokens() ) {
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

//--------------------------------------------------------------------------

class DiskRequest {
	int request_time;
	int request_track;
	
	DiskRequest(int t, int r) {
		this.setTime(t);
		this.setRequest(r);
	}
	
	void setTime(int t) {
		this.request_time = t;
	}
	void setRequest(int r) {
		this.request_track = r;
	}
}

//--------------------------------------------------------------------------

abstract class DiskScheduling {
	int time, head;
	
	abstract int runAlgorithm();
}

class FCFS {
	
}

class SSTF {
	
}

class LOOK {
	
}