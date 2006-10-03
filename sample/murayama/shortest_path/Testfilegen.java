import java.io.*;
import java.util.Random;

/* input [filename(before dot)] [parts] [nodenum] [edgenum]
 *
 *
 */
class Testfilegen {
    public static void main(String[] args) {
    	String filename = args[0];
    	int partnum = Integer.parseInt(args[1]);
    	int nodenum = Integer.parseInt(args[2]);
    	int edgenum = Integer.parseInt(args[3]);
    	int linenum = 0;
    	int[] n1= new int[nodenum+1];
    	if(partnum !=1 ){
	        try {
	            FileReader fr = new FileReader(filename + ".txt.part."+args[1]);
	            BufferedReader br = new BufferedReader(fr);
	            String line;
	            while ((line = br.readLine()) != null) {
	            	linenum++;
				 	int threadnum = Integer.parseInt(line);
				 	n1[linenum] = threadnum;
	            }
	            br.close();
	            fr.close();
	        } catch (IOException e) {
	            System.out.println(e);
	        }
	
	    	int[][] n2= new int[edgenum+1][2];
	    	linenum = 0;
	        try {
	            FileReader fr = new FileReader(filename + ".graphml");
	            BufferedReader br = new BufferedReader(fr);
	            String line;
	            while ((line = br.readLine()) != null) {
	            	linenum++;
				 	String r[] = line.split(" ");
				 	n2[linenum][0] = Integer.parseInt(r[0]);
				 	n2[linenum][1] = Integer.parseInt(r[1]);
	            }
	            br.close();
	            fr.close();
	        } catch (IOException e) {
	            System.out.println(e);
	        }        
	        
	        
	        try {
	            FileWriter fr = new FileWriter(filename + "nodes_"+partnum);
	            for(int i=1;i<nodenum+1;i++){
		            fr.write(i+" "+n1[i]+"\n");
	            }
	            fr.close();
	            
	            fr = new FileWriter(filename + "edges_"+partnum);
	            Random rand = new Random(0);            
	            for(int i=1;i<edgenum+1;i++){
		            fr.write(n2[i][0]+" "+n2[i][1]+" "+rand.nextInt(100)+" "+n1[n2[i][0]]+"\n");
	            }
	            fr.close();
	        }
	        catch (IOException e) {
	            System.out.println(e);
	        }
    	} else {
	    	int[][] n2= new int[edgenum+1][2];
	    	linenum = 0;
	        try {
	            FileReader fr = new FileReader(filename + ".graphml");
	            BufferedReader br = new BufferedReader(fr);
	            String line;
	            while ((line = br.readLine()) != null) {
	            	linenum++;
				 	String r[] = line.split(" ");
				 	n2[linenum][0] = Integer.parseInt(r[0]);
				 	n2[linenum][1] = Integer.parseInt(r[1]);
	            }
	            br.close();
	            fr.close();
	        } catch (IOException e) {
	            System.out.println(e);
	        }        
	        try {
	            FileWriter fr = new FileWriter(filename + "nodes_"+partnum);
	            for(int i=1;i<nodenum+1;i++){
		            fr.write(i+" "+0+"\n");
	            }
	            fr.close();
	            
	            fr = new FileWriter(filename + "edges_"+partnum);
	            Random rand = new Random(0);            
	            for(int i=1;i<edgenum+1;i++){
		            fr.write(n2[i][0]+" "+n2[i][1]+" "+rand.nextInt(100)+" "+0+"\n");
	            }
	            fr.close();
	        }
	        catch (IOException e) {
	            System.out.println(e);
	        }
    	}
    }
}
