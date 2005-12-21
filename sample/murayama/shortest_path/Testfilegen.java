import java.io.*;
import java.util.Random;

class Testfilegen {
    public static void main(String[] args) {
    	int nodenum = 202;
    	int edgenum = 303;
    	int linenum = 0;
    	int[] n1= new int[nodenum+1];
        try {
            FileReader fr = new FileReader("file2.txt.part."+args[0]);
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
            FileReader fr = new FileReader("file.txt");
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
            FileWriter fr = new FileWriter("testfile_nodes"+args[0]);
            for(int i=1;i<nodenum+1;i++){
	            fr.write(i+" "+n1[i]+"\n");
            }
            fr.close();
            
            fr = new FileWriter("testfile_edges"+args[0]);
            Random rand = new Random(0);            
            for(int i=1;i<edgenum+1;i++){
	            fr.write(n2[i][0]+" "+n2[i][1]+" "+rand.nextInt(100)+" "+n1[n2[i][0]]+"\n");
            }
            fr.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }

    }
}
