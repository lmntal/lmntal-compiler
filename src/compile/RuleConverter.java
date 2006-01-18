/*
 * 作成日: 2005/1/14
 */
package compile;
import java.util.ArrayList;
import java.util.Iterator;
import runtime.Env;

class RuleConverter {
	private String[] name, head, guard, body, submems;
	private int[] anyreduct;
	private int memuse = 0;
	
    public Iterator convert(String rule){

        submems = new String[128];
        name = new String[128];
        head = new String[128];
        guard = new String[128];
        body = new String[128];
        anyreduct = new int[128];
        int start=0;
        int end=0;
        int submemnum=0;

        rule = rule.substring(1, rule.length());
        String[] rulex;
        int gap = 1;
        rulex = rule.split("\\\\");
        while(rulex.length>gap){
        	rulex[0] = rulex[0]+"\\"+rulex[gap+1];
        	gap += 2;
        }
        rule = rulex[0];
        

        for(int i=0;i<rule.length();i++){
        	if(rule.charAt(i)=='{'){
        		int p=1;
        		start=i+1;
        		while(p > 0){
        			i++;
        			if(rule.charAt(i)=='{')p++;
        			if(rule.charAt(i)=='}')p--;
        		}
        		end=i;
        		submems[submemnum] = rule.substring(start,end);
        		rule = rule.substring(0,start) + "?"+submemnum+"?" +rule.substring(end);
        		submemnum++;
        		i-= end-start;
        	}
        }
        String[] rule0, rule1, rule2;
        rule0 = rule.split("@@");
	 	if(rule0.length == 1)
		 	rule1 = rule0[0].split(":-");
	 	else
	 		rule1 = rule0[1].split(":-");
	 	if(rule1.length == 1)
	 		rule2 = rule1[0].split("\\|");
	 	else
	 		rule2 = rule1[1].split("\\|");

	 	if(rule0.length == 1)
	 		name[0] = "";
	 	else
	 		name[0] = rule0[0].trim().replace("\t","");
	 	
	 	head[0] = rule1[0].trim().replace("\t","");

	 	if(rule2.length == 1){
	 		guard[0] = "";
	 		body[0] = rule2[0].trim().substring(0, rule2[0].trim().length()-1).replace("\t","");
	 	} else {
		 	guard[0] = rule2[0].trim().replace("\t","");
		 	body[0] = rule2[1].trim().substring(0, rule2[1].trim().length()-1).replace("\t","");
	 	}
	 	
	 	int i=0;
	 	for(int mnum = 0; mnum<=memuse; mnum++){
		 	while((i=head[mnum].indexOf("thread.", i)) >-1){
		 		if((head[mnum].substring(i+7,i+10)).equals("any")){
		 			if(anyreduct[mnum]==0){
		 				ruleANY1(i, mnum);
			 			anyreduct[mnum]++;
		 			} else {
			 			anyreduct[mnum]++;
			 			ruleANY2(i, mnum);
		 			}
		 		}else if((head[mnum].substring(i+7,i+10)).equals("all")){
//		 			System.out.println("all");
		 			i+=5;
		 		}else if((head[mnum].substring(i+7,i+9)).equals("at")){
//		 			System.out.println("at");
		 			i+=5;
		 		}else{
//		 			System.out.println("error "+head[mnum].substring(i+7,i+15));
		 			i+=5;
		 		}
		 	}
	 	}
	 	for(int mnum = 0; mnum<=memuse; mnum++){
		 	while((i=body[mnum].indexOf("thread.",i)) >-1){
		 		if((body[mnum].substring(i+7,i+10)).equals("any")){
		 			if(body[mnum].indexOf("thread(THD") > -1)
		 				reductANY(i, mnum);
		 		}else if((body[mnum].substring(i+7,i+10)).equals("all")){
//		 			System.out.println("all");
			 		i+=5;
		 		}else if((body[mnum].substring(i+7,i+9)).equals("at")){
//		 			System.out.println("at");
			 		i+=5;
		 		}else{
//		 			System.out.println("error");
			 		i+=5;
		 		}
		 	}
	 	}
	 	for(int mnum = 0; mnum<=memuse; mnum++){
		 	if((i=head[mnum].indexOf("thread(THD",i)) >-1){
		 		if(head[mnum].indexOf("thread(THD",i+1) == -1){
		 			int j=0;
		 			if((j=body[mnum].indexOf("thread(THD")) >-1){
			 			if(body[mnum].indexOf("thread(THD",i+1) == -1)
			 				reductANY2(i, j, mnum);
			 		}else{
				 			System.out.println("error");
			 		}
		 		}
		 	}
	 	}
	 	submemIns();

	 	/*
		 	for(int k=0; k<=memuse; k++){
		 		System.out.println("______________________________rule"+k+"___________________________");
		 		System.out.println("______________________________HEAD____________________________");
			 	System.out.println(head[k]);
		 		System.out.println("______________________________GUARD___________________________");
	            System.out.println(guard[k]);
		 		System.out.println("______________________________BODY____________________________");
	            System.out.println(body[k]);
		 	}
*/
	 	ArrayList lst = new ArrayList();
	 	for(int k=0; k<=memuse; k++){
//	 		if(k!=0 || memuse==0){
	 			lst.add("("+toRule(name[k], head[k],guard[k],body[k])+")");
	 			System.out.println(toRule(name[k], head[k],guard[k],body[k]));
//	 		}
	 	}
	 	return lst.iterator();
    }
    private void headAdd(String s, int mnum){
    	if(head[mnum].length()==0)
    		head[mnum] = s; 
    	else
    		head[mnum] = head[mnum] + " ,"+s; 
    }
     private void guardAdd(String s, int mnum){
    	if(guard[mnum].length()==0)
    		guard[mnum] = s;	
    	else
    		guard[mnum] = guard[mnum] + " ,"+s; 
    }
     private void bodyAdd(String s, int mnum){
    	if(body[mnum].length()==0)
    		body[mnum] = s;
    	else
    		body[mnum] = body[mnum] + " ,"+s; 
    }

     private int getHeadMemNum(int start, int mnum){
    	int num=0;
    	int end=0;
    	for(int i=start;;i++){
    		if(head[mnum].charAt(i)=='('){
    			while(head[mnum].charAt(i)!='?')i++;
    			end=i+1;
    			while(head[mnum].charAt(end)!='?')end++;
    			num = Integer.parseInt(head[mnum].substring(i+1,end));
    		} else if(head[mnum].charAt(i)==','){
    			end = i+1;
    			break;
    		} else if(i>head[mnum].length()-1){
    			end = i;
    			break;
    		}
    	}
		head[mnum] = head[mnum].substring(0,start) + head[mnum].substring(end);    			
    	return num;
    }
     private int getBodyMemNum(int start, int mnum){
    	int num=0;
    	int end=0;
    	for(int i=start;;i++){
    		if(body[mnum].charAt(i)=='('){
    			while(body[mnum].charAt(i)!='?')i++;
    			end=i+1;
    			while(body[mnum].charAt(end)!='?')end++;
    			num = Integer.parseInt(body[mnum].substring(i+1,end));
    		} else if(body[mnum].charAt(i)==','){
    			end = i+1;
    			break;
    		} else if(i>body[mnum].length()-1){
    			end = i;
    			break;
    		}
    	}
		body[mnum] = body[mnum].substring(0,start) + body[mnum].substring(end);    			
    	return num;
    }

     /*
      * 左辺に出現するスレッド数を返す
      */
     private int threadNum(int mnum){
    	int num=0;
    	int st=0;
    	int i=0;
	 	while((i=head[mnum].indexOf("thread(", st)) >-1){
	 		st=i+5;
	 		num++;
	 	}
    	return num;
    }
     private void submemIns(){
    	for(int k=0; k<=memuse; k++){
        	int st=0;
        	int end=0;
        	int i=0;
    	 	while((i=head[k].indexOf("?", st)) >-1){
    	 		st=i;
    	 		end=head[k].indexOf("?", st+1);
    	 		int memnum = Integer.parseInt(head[k].substring(st+1,end));
    	 		head[k] = head[k].substring(0,st) +submems[memnum]+ head[k].substring(end+1); 
    	 	}
    	}
    	for(int k=0; k<=memuse; k++){
        	int st=0;
        	int end=0;
        	int i=0;
    	 	while((i=body[k].indexOf("?", st)) >-1){
    	 		st=i;
    	 		end=body[k].indexOf("?", st+1);
    	 		int memnum = Integer.parseInt(body[k].substring(st+1,end));
    	 		body[k] = body[k].substring(0,st) +submems[memnum]+ body[k].substring(end+1); 
    	 	}
    	}
    }
     private String toRule(String name, String head, String guard, String body){
    	if(name.trim().equals("")){
	    	if(head.trim().equals(""))
	    		return body.trim();
	    	else if(guard.trim().equals(""))
	    		return head.trim() + " :- " + body.trim();
	    	else
	    		return head.trim() + " :- " + guard.trim() + " | " + body.trim();
    	} else {
	    	if(head.trim().equals(""))
	    		return ":- uniq | " + body.trim();
	    	else if(guard.trim().equals(""))
	    		return name.trim() + " @@ " + head.trim() + " :- " + body.trim();
	    	else
	    		return name.trim() + " @@ " + head.trim() + " :- " + guard.trim() + " | " + body.trim();
    	}
    }
    
     private void ruleANY1(int i, int mnum){
    	int memnum = getHeadMemNum(i, mnum);
		headAdd("thread(THD"+memnum+",{?"+memnum+"?, $th" + memnum + ", @th" + memnum + "})", mnum);
		guardAdd("int(THD"+memnum+")", mnum);
		bodyAdd("thread(THD"+memnum+",{$th" + memnum + ", @th" + memnum + "})", mnum);
    }
     private void ruleANY2(int i, int mnum){
    	int memnum = getHeadMemNum(i, mnum);
		int spacenum = memuse+1;
		
		int st = 0;
		int k;
		for(k=0; k<threadNum(mnum); k++){
			int s,e;
			s = head[mnum].indexOf("thread(THD", st);
			e = head[mnum].indexOf("{", s);
			st = e;
			StringBuffer buf = new StringBuffer(head[mnum]);
			buf.insert(e+1, "?" + memnum +"?, ");
			name[spacenum] = name[mnum];
			head[spacenum] = buf.toString();
			body[spacenum] = body[mnum];
			guard[spacenum] = guard[mnum];
			anyreduct[spacenum] = anyreduct[mnum];
			spacenum++;
		}
		if(k < Env.threadMax){
			memuse = spacenum-1;
			headAdd("thread(THD"+memnum+",{?"+memnum+"?, $th" + memnum + ", @th" + memnum + "})", mnum);
			guardAdd("int(THD"+memnum+")", mnum);
			bodyAdd("thread(THD"+memnum+",{$th" + memnum + ", @th" + memnum + "})", mnum);	
		} else {
			memuse = spacenum-2;
			name[mnum] = name[spacenum-1];
			head[mnum] = head[spacenum-1];
			body[mnum] = body[spacenum-1];
			guard[mnum] = guard[spacenum-1];
			anyreduct[mnum] = anyreduct[spacenum-1];
		}
    }
    
     private void reductANY(int i, int mnum){
    	int k, j, h,  st, e, end, num, id;
    	int thdid[][] = new int[128][2]; 
    	int thdhead[][] = new int[128][2]; 
    	int memnum = getBodyMemNum(i, mnum);
		for(k=0;(i=head[mnum].indexOf("thread(THD", i)) > -1;){
			while(head[mnum].charAt(i)!='D')i++;
			e=i+1;
			while(head[mnum].charAt(e)!=',')e++;
			id = Integer.parseInt(head[mnum].substring(i+1,e));
			st = i+1;
			for(h=0; h<128; h++){
				while(head[mnum].charAt(st)!='?' && head[mnum].charAt(st)!='$')st++;
				if(head[mnum].charAt(st)=='$')break;
				end=st+1;
				while(head[mnum].charAt(end)!='?' && head[mnum].charAt(end)!='$')end++;
				if(head[mnum].charAt(end)=='$')break;
				num = Integer.parseInt(head[mnum].substring(st+1,end));
				thdhead[k][1] = id;
				thdhead[k][0] = num;
				k++;
				st=end+1;
			}
		}
		for(j=0;(i=body[mnum].indexOf("thread(THD", i)) > -1; j++){
			while(body[mnum].charAt(i)!='D')i++;
			end=i+1;
			while(body[mnum].charAt(end)!=',')end++;
			num = Integer.parseInt(body[mnum].substring(i+1,end));
			thdid[j][0] = num;
			thdid[j][1] = end+2;
			i=end;
		}
		int minld = 65536;
		int ins = -1;
		LD dis = new LD();
		for(k--;k>=0;k--){
			int ld = dis.getLD(submems[memnum], submems[thdhead[k][0]]);
			if(minld > ld) {
				minld = ld;
				ins = thdhead[k][1];
			}
		}
		j=0;
		while(thdid[j][0] != ins) j++;
		StringBuffer buf = new StringBuffer(body[mnum]);
		buf.insert(thdid[j][1], "?" + memnum +"?, ");
		body[mnum] = buf.toString();
    }
    
    private void reductANY2(int hst, int bst, int mnum){
    	StringBuffer hbuf = new StringBuffer(head[mnum]);
    	StringBuffer bbuf = new StringBuffer(body[mnum]);
    	StringBuffer gbuf = new StringBuffer(guard[mnum]);
    	int hend = head[mnum].indexOf(")", hst);
    	int bend = body[mnum].indexOf(")", bst);
    	while(head[mnum].length()>hend && head[mnum].charAt(hend)!=',')hend++;
    	while(body[mnum].length()>bend && body[mnum].charAt(bend)!=',')bend++;
    	if(head[mnum].length()==hend)
    		while(0<hst && head[mnum].charAt(hst)!=',')hst--;
    	if(body[mnum].length()==bend)
    		while(0<bst && body[mnum].charAt(bst)!=',')bst--;
    	int gend = gbuf.indexOf(" ,int(THD");
    	if(gend<0)
        	gbuf = new StringBuffer("");
    	else
    		gbuf = new StringBuffer(gbuf.substring(0,gend));
    	StringBuffer hbuf2 = new StringBuffer(hbuf.substring(hst,hend));
    	StringBuffer bbuf2 = new StringBuffer(bbuf.substring(bst,bend));
    	head[mnum] = hbuf.delete(hst,hend+1).toString();
    	body[mnum] = bbuf.delete(bst,bend+1).toString();
    	guard[mnum] = "";
    	hst = hbuf2.indexOf("?");
    	bst = bbuf2.indexOf("?");
    	hend = hbuf2.indexOf("$");
    	bend = bbuf2.indexOf("$");
    	String nm =name[mnum];
    	String hs = hbuf2.substring(hst,hend-2);
    	String bs = bbuf2.substring(bst,bend-2);
    	guardAdd("uniq", mnum);
    	bodyAdd("thread.all({"+toRule(nm, hs,gbuf.toString(),bs)+"})", mnum);
    }
}

class LD {
	  
  private int min(int x, int y, int z) {
    int min;
    min = x;
    if(y < min) min = y;
    if(z < min) min = z;
    return min;
  }

  public int getLD(String s1, String s2) {
    int i,j;
    int mat[][];
    int cost;
    char s1_c, s2_c;
        
    int s1_l = s1.length();
    int s2_l = s2.length();
    if (s1_l == 0) return s2_l;
    if (s2_l == 0) return s1_l;

    mat = new int[s1_l+1][s2_l+1];
    for (i=0; i <= s1_l; i++) mat[i][0] = i;
    for (j=0; j <= s2_l; j++) mat[0][j] = j;

    for (i=0; i < s1_l; i++) {
      s1_c = s1.charAt(i);
      for (j=0; j < s2_l; j++) {
        s2_c = s2.charAt(j);
        cost = s1_c==s2_c ? 0 : 1;
        mat[i+1][j+1] = min(mat[i][j+1]+1, mat[i+1][j]+1, mat[i][j] + cost);
      }
    }

    return mat[s1_l][s2_l];
  }
}
