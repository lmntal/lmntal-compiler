/*
 * 作成日: 2004/11/11
 *
 * sample/sat3.lmnに読ませる為のcnfフォーマットのファイルを生成する。
 * 変数の数、節の数、節の（最大の）サイズを指定してください。後者二つは省略できます。
 */

/**
 * @author kudo
 */
public class SatGenerater {

	public static void main(String[] args) {
		int n=0,m=0,c=0;
		if(args.length>0){
			n = Integer.parseInt(args[0]);
		}else{System.out.println(
			"Usage : \n" +
			"java SatGenerator <variable number> <clause number> <clause size>");
			return;
		}
		if(args.length>1)m = Integer.parseInt(args[1]);
		else m = (int)(n * 1.5);
		if(args.length>2)m = Integer.parseInt(args[2]);
		else c = (int)(m /2);
		System.out.println("p cnf "+ n + " " + m);
		for(int i=0;i<m;i++){
			int l = (int)(Math.random()*c)+1;
			for(int j=0;j<l;j++){
				int x = (int)(Math.random()*n);
				int pm = (int)(Math.random() * 2);
				if(pm == 0) System.out.print((x+1) + " ");
				else System.out.print("-" + (x+1) + " ");
			}
			System.out.println("0");
		}
	}
}
