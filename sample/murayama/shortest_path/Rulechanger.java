import java.io.*;

class Rulechanger {
	static String[] head, guard, body, submems;
	static int[] anyreduct;
	static int memuse = 0;
	
    public static void main(String[] args) {
        try {
            FileReader fr = new FileReader("rule.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            String rule="";
            while ((line = br.readLine()) != null) {
            	rule = rule + line;
            }

            submems = new String[128];
            head = new String[128];
            guard = new String[128];
            body = new String[128];
            anyreduct = new int[128];
            int start=0;
            int end=0;
            int submemnum=0;
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
		 	String rule1[] = rule.split(":-");
		 	String rule2[] = rule1[1].split("\\|");
		 	head[0] = rule1[0].trim().replace("\t","");
		 	guard[0] = rule2[0].trim().replace("\t","");
		 	body[0] = rule2[1].trim().substring(0, rule2[1].trim().length()-1).replace("\t","");
		 	
		 	int i;
		 	for(int mnum = 0; mnum<=memuse; mnum++){
			 	while((i=head[mnum].indexOf("thread.")) >-1){
			 		if((head[mnum].substring(i+7,i+10)).equals("any")){
			 			if(anyreduct[mnum]==0){
			 				ruleANY1(i, mnum);
				 			anyreduct[mnum]++;
			 			} else if(anyreduct[mnum] < 4) {
				 			anyreduct[mnum]++;
				 			ruleANY2(i, mnum);
			 			} else {
			 				
			 			}
			 		}else if((head[mnum].substring(i+7,i+10)).equals("all")){
			 			System.out.println("all");
			 		}else if((head[mnum].substring(i+7,i+9)).equals("at")){
			 			System.out.println("at");
			 		}else{
			 			System.out.println("error");
			 		}
			 	}
		 	}
		 	for(int mnum = 0; mnum<=memuse; mnum++){
			 	while((i=body[mnum].indexOf("thread.")) >-1){
			 		if((body[mnum].substring(i+7,i+10)).equals("any")){
			 			if(body[mnum].indexOf("thread(THD") > -1)
			 				reductANY(i, mnum);
			 		}else if((body[mnum].substring(i+7,i+10)).equals("all")){
			 			System.out.println("all");
			 		}else if((body[mnum].substring(i+7,i+9)).equals("at")){
			 			System.out.println("at");
			 		}else{
			 			System.out.println("error");
			 		}
			 		i++;
			 	}
		 	}
		 	for(int mnum = 0; mnum<=memuse; mnum++){
			 	if((i=head[mnum].indexOf("thread(THD")) >-1){
			 		if(head[mnum].indexOf("thread(THD",i+1) == -1){
			 			int j=0;
			 			if((j=body[mnum].indexOf("thread(THD")) >-1){
				 			if(body[mnum].indexOf("thread(THD",i+1) == -1)
				 				reductANY2(i, j, mnum);
				 		}else{
//				 			System.out.println("error");
				 		}
			 		}
			 		i++;
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
		 	for(int k=0; k<=memuse; k++){
		 		System.out.println("______________________________rule"+k+"___________________________");
			 	System.out.println(toRule(head[k],guard[k],body[k])+".");
		 	}
		 	
		 	br.close();
            fr.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    static void headAdd(String s, int mnum){
    	if(head[mnum].length()==0)
    		head[mnum] = s; 
    	else
    		head[mnum] = head[mnum] + " ,"+s; 
    }
    static void guardAdd(String s, int mnum){
    	if(guard[mnum].length()==0)
    		guard[mnum] = s;
    	else
    		guard[mnum] = guard[mnum] + " ,"+s; 
    }
    static void bodyAdd(String s, int mnum){
    	if(body[mnum].length()==0)
    		body[mnum] = s;
    	else
    		body[mnum] = body[mnum] + " ,"+s; 
    }

    static int getHeadMemNum(int start, int mnum){
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
    static int getBodyMemNum(int start, int mnum){
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

    static int threadNum(int mnum){
    	int num=0;
    	int st=0;
    	int i=0;
	 	while((i=head[mnum].indexOf("thread(", st)) >-1){
	 		st=i+5;
	 		num++;
	 	}
    	return num;
    }
    static void submemIns(){
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
    static String toRule(String head, String guard, String body){
    	if(head.trim().equals(""))
    		return body.trim();
    	else if(guard.trim().equals(""))
    		return head.trim() + " :- " + body.trim();
    	else
    		return head.trim() + " :- " + guard.trim() + " | " + body.trim();
    }
    
    static void ruleANY1(int i, int mnum){
    	int memnum = getHeadMemNum(i, mnum);
		headAdd("thread(THD"+memnum+",{?"+memnum+"?, $th" + memnum + ", @th" + memnum + "})", mnum);
		guardAdd("int(THD"+memnum+")", mnum);
		bodyAdd("thread(THD"+memnum+", $th" + memnum + ", @th" + memnum + "})", mnum);
    }
    static void ruleANY2(int i, int mnum){
    	int memnum = getHeadMemNum(i, mnum);
		int spacenum = memuse+1;
		
		int st = 0;
		for(int k=0; k<threadNum(mnum); k++){
			int s,e;
			s = head[mnum].indexOf("thread(THD", st);
			e = head[mnum].indexOf("{", s);
			st = e;
			StringBuffer buf = new StringBuffer(head[mnum]);
			buf.insert(e+1, "?" + memnum +"?, ");
			head[spacenum] = buf.toString();
			body[spacenum] = body[mnum];
			guard[spacenum] = guard[mnum];
			anyreduct[spacenum] = anyreduct[mnum];
			spacenum++;
		}
		memuse = spacenum-1;
		headAdd("thread(THD"+memnum+",{?"+memnum+"?, $th" + memnum + ", @th" + memnum + "})", mnum);
		guardAdd("int(THD"+memnum+")", mnum);
		bodyAdd("thread(THD"+memnum+", $th" + memnum + ", @th" + memnum + "})", mnum);	
    }
    
    static void reductANY(int i, int mnum){
    	int k, end, num;
    	int thdid[][] = new int[128][3]; 
    	int memnum = getBodyMemNum(i, mnum);
		for(k=0;(i=body[mnum].indexOf("thread(THD", i)) > -1; k++){
			while(body[mnum].charAt(i)!='D')i++;
			end=i+1;
			while(body[mnum].charAt(end)!=',')end++;
			num = Integer.parseInt(body[mnum].substring(i+1,end));
			thdid[k][0] = num;
			thdid[k][1] = end+2;
			i=end;
		}
		int minld = 65536;
		int ins = -1;
		Distance dis = new Distance();
		for(k--;k>=0;k--){
			int ld = dis.LD(submems[memnum], submems[thdid[k][0]]);
			if(minld > ld) {
				minld = ld;
				ins = thdid[k][1];
			}
		}
		StringBuffer buf = new StringBuffer(body[mnum]);
		buf.insert(ins, "?" + memnum +"?, ");
		body[mnum] = buf.toString();
    }
    
    static void reductANY2(int hst, int bst, int mnum){
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
    	hbuf = new StringBuffer(hbuf);
    	bbuf = new StringBuffer(bbuf);
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
    	String hs = hbuf2.substring(hst,hend-2);
    	String bs = bbuf2.substring(bst,bend-2);
    	bodyAdd("thread.all({"+toRule(hs,gbuf.toString(),bs)+"})", mnum);
    }
}			
					