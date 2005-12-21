import java.io.*;

class Fileconv {
    public static void main(String[] args) {
    	int nodenum = 202;
    	int[][] n= new int[nodenum+1][10];
    	int linenum = 0;
        try {
            FileReader fr = new FileReader("file.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
            	linenum++;
			 	String r[] = line.split(" ");
			 	int s=Integer.parseInt(r[0]);
			 	int e=Integer.parseInt(r[1]);
			 	for(int i=0;i<10;i++){
			 		if(n[s][i]==0){
			 			n[s][i]=e;
			 			break;
			 		}
			 	}
			 	for(int i=0;i<10;i++){
			 		if(n[e][i]==0){
			 			n[e][i]=s;
			 			break;
			 		}
			 	}
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        try {
            FileWriter fr = new FileWriter("file2.txt");
            fr.write(nodenum+" "+linenum+"\n");
            for(int i=1;i<nodenum+1;i++){
			 	for(int j=0;j<10;j++){
			 		if(n[i][j]==0){
			            fr.write("\n");
			 			break;
			 		}else{
			            fr.write(n[i][j]+" ");
			 		}
			 	}
            }
            fr.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }

    }
}
