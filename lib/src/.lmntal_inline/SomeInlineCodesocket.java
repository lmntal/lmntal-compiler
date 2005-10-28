import runtime.*;
import java.util.*;
/*inline_define*/
import java.net.*;
import java.io.*;

class AcceptThread extends Thread {
	ServerSocket ss;
	Atom ssAtom;
	AcceptThread(ServerSocket ss, Atom ssAtom) {
		this.ss = ss;
		this.ssAtom = ssAtom;
	}
	public void run() {
		try {
			Socket soc = ss.accept();
			AbstractMembrane mem = ssAtom.getMem();
			mem.asyncLock();
			Atom dot = ssAtom.nthAtom(1);
			Atom acceptingAtom = dot.nthAtom(0);
			ReadThread sr = new ReadThread(soc);
			//make client socket
			Atom s = mem.newAtom(new Functor("socket", 4, "socket"));
			Atom o = mem.newAtom(new ObjectFunctor(sr));
			sr.me = o;
			Atom nil1 = mem.newAtom(new Functor("nil", 1));
			Atom nil2 = mem.newAtom(new Functor("[]", 1));
			mem.newLink(s, 0, o, 0);
			mem.newLink(s, 1, nil1, 0); 
			mem.newLink(s, 2, nil2, 0); 
			mem.relink(s, 3, acceptingAtom, 0);
			
			//relink command list
			mem.unifyAtomArgs(dot, 1, dot, 2);
			dot.remove();
			acceptingAtom.remove();

			mem.asyncUnlock();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class ReadThread extends Thread {
	Socket socket;
	Atom me;
	boolean flgClosing = false;
	ReadThread(Socket socket) {
		this.socket = socket;
		this.me = me;
	}
	ReadThread(String host, int port) throws IOException {
		this.socket = new Socket(host, port);
		this.me = me;
	}
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
L:
			while (true) {
				String data;
				while (true) {
					if (flgClosing) {
						break L;
					}
					if (reader.ready()) {
						data = reader.readLine();
						break;
					}
					Thread.sleep(50);
				}
				AbstractMembrane mem = me.getMem();
				mem.asyncLock();
				Atom socAtom = me.nthAtom(0);
				Atom dataAtom = mem.newAtom(new StringFunctor(data));
				Atom dot = mem.newAtom(new Functor(".", 3));
				mem.newLink(dot, 0, dataAtom, 0);
				mem.relink(dot, 2, socAtom, 1);
				mem.newLink(dot, 1, socAtom, 1);
				mem.asyncUnlock();
			}
			AbstractMembrane mem = me.getMem();
			mem.asyncLock();
			Atom socAtom = me.nthAtom(0);
			Atom nil = mem.newAtom(new Functor("[]", 1));
			mem.relink(nil, 0, socAtom, 1);
			Atom closed = mem.newAtom(new Functor("nil", 1));
			mem.newLink(closed, 0, socAtom, 1);
			mem.asyncUnlock();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class SomeInlineCodesocket implements InlineCode {
	public void run(Atom me, int codeID) {
		AbstractMembrane mem = me.getMem();
		switch(codeID) {
		case 7: {
			/*inline*/
		ReadThread sr = (ReadThread)((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();
		Socket soc = sr.socket;
		try {
			soc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		me.nthAtom(0).remove();
		me.remove();
	
			break; }
		case 0: {
			/*inline*/
		try {
			ServerSocket ss = new ServerSocket(Integer.parseInt(me.nth(0)));
			Atom o = mem.newAtom(new ObjectFunctor(ss));
			mem.relink(o, 0, me, 1);
			
			me.nthAtom(0).remove();
			me.remove();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
			break; }
		case 4: {
			/*inline*/
		ReadThread sr = (ReadThread)((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();
		sr.start();
		mem.unifyAtomArgs(me, 0, me, 1);
		me.remove();
	
			break; }
		case 3: {
			/*inline*/
		try {
			String addr = me.nth(0);
			int port = Integer.parseInt(me.nth(1));
			ReadThread sr = new ReadThread(addr, port);

			Functor func = new ObjectFunctor(sr);
			Atom so = mem.newAtom(func);
			sr.me = so;
			mem.relink(so, 0, me, 2);

			me.nthAtom(0).remove();
			me.nthAtom(1).remove();
			me.remove();
		} catch(Exception e) {
			e.printStackTrace();
		}
	
			break; }
		case 6: {
			/*inline*/
		ReadThread sr = (ReadThread)((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();
		sr.flgClosing = true;
		mem.unifyAtomArgs(me, 0, me, 1);
		me.remove();
	
			break; }
		case 5: {
			/*inline*/
		try {
			String data = (String)((StringFunctor)me.nthAtom(0).getFunctor()).getObject();
			Socket soc = ((ReadThread)((ObjectFunctor)me.nthAtom(1).getFunctor()).getObject()).socket;
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(soc.getOutputStream()));
			writer.write(data);
			writer.write("\n");
			writer.flush();
			mem.unifyAtomArgs(me, 1, me, 2);
			me.nthAtom(0).remove();
			me.remove();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
			break; }
		case 1: {
			/*inline*/
		ServerSocket ss = (ServerSocket)((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();
		AcceptThread t = new AcceptThread(ss,me.nthAtom(1));
		mem.makePerpetual();
		t.start();
		mem.unifyAtomArgs(me, 0, me, 1);
		me.remove();
	
			break; }
		case 2: {
			/*inline*/
		ServerSocket ss = (ServerSocket)((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();
		try {
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		me.nthAtom(0).remove();
		me.remove();
	
			break; }
		}
	}
}
