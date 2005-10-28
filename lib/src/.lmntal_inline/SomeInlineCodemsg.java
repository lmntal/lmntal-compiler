import runtime.*;
import java.util.*;
/*inline_define*/
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class NetHolder {
	static Net net;
	static AbstractMembrane mem;
	static void init(AbstractMembrane mem) {
		if(NetHolder.net==null) {
			NetHolder.net = new Net(50101, 100);
			NetHolder.mem = mem;
		}
	}
}
class GenThread extends Thread {
	public void run() {
		AbstractMembrane mem = NetHolder.mem;
		try {
			while(true) {
				Packet p = NetHolder.net.recv();
				mem.asyncLock();
				Atom a = mem.newAtom(new Functor("recv", 1));
				Atom b = mem.newAtom(new Functor("test", 1));
				mem.newLink(a, 0, b, 0);
				mem.asyncUnlock();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

/**
 * Ç¤°Õ¤ÎÄÌ¿®Àè¤È¤ÎÀÜÂ³¤ò´ÉÍ?¤¹¤?¡£
 */
class Net {
	ServerSocket ssock;
	
	public Map pool = Collections.synchronizedMap(new HashMap());
	
	AcceptThread th1;
	
	RecvThread th2;
	
	int _port;
	
	Net(int port, int backlog) {
		_port=port;
		th1 = new AcceptThread(port, backlog, this);
		th2 = new RecvThread(this);
	}
	
	synchronized void addSocket(Socket s, InetAddress _ia, int port) {
		synchronized(pool) {
			InetAddress ia = s!=null ? s.getInetAddress() : _ia;
			if(pool.containsKey(ia)) {
				if(s!=null) ((Connection)pool.get(ia)).addSocket(s);
			} else {
				try {
					Socket ns=null;
					if(s==null) ns = new Socket(ia, port);
					pool.put(ia, new Connection(s!=null ? s : ns));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
//		show();
	}
	
	public void send(String hostname, String data) {
		int pos = hostname.indexOf(':');
		if(pos>0) {
			send(hostname.substring(0, pos), Integer.parseInt(hostname.substring(pos+1)), data);
		} else {
			send(hostname, 50101, data);
		}
	}
	/**
	 * »ØÄê¤·¤¿¥Û¥¹¥È¤Ë¥Ç¡¼¥¿¤òÁ÷¤?¡£
	 * @param hostname ¥Û¥¹¥ÈÌ¾ or IP¥¢¥É¥?¥¹
	 * @param port ÀÜÂ³Àè¥Ý¡¼¥ÈÈÖ¹?
	 * @param data Á÷¿®¤¹¤?Ê¸»úÎ?
	 */
	public void send(String hostname, int port, String data) {
//		System.out.println("SEND");
		try {
			InetAddress ia = InetAddress.getByName(hostname);
			Connection t;
			addSocket(null, ia, port);
			t = (Connection)pool.get(ia);
			if(t==null) return;
//			System.out.println("get tuple = "+t);
			((SocketTuple)t.sockets.get(0)).writer.println(data);
			System.out.println("> Sent "+data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ¤É¤³¤«¤é¤«Ê¸»úÎó¤ò¼õ¿®¤¹¤?¡£¥Ö¥úÁÃ¥¯¤¹¤?¡£
	 * @return Packet ¥¯¥é¥¹¤Î¥¤¥ó¥¹¥¿¥ó¥¹
	 */
	public Packet recv() {
//		System.out.println("RECV");
		do {
			synchronized(pool) {
				Iterator it = pool.keySet().iterator();
				while(it.hasNext()) {
					InetAddress ia = (InetAddress)it.next();
					Connection p = (Connection)pool.get(ia);
					String s = p.removeTop();
					if(s!=null) return new Packet(((SocketTuple)p.sockets.get(0)).sock.getLocalSocketAddress(), s);
				}
			}
//			sleep(50);
		} while(true);
	}
	
	public void show() {
		synchronized(pool) {
			System.out.println("\t"+_port+" "+pool);
		}
	}
	
	/**
	 * ÄÌ¿®¤Î¤¿¤á¤Î¥¹¥?¥Ã¥É¤ò½ª¤?¤é¤»¤?¡£
	 *
	 */
	public void end() {
		th1.end();
		th2.end();
	}
	static void sleep(int milli) {
		try {
			Thread.sleep(milli);
		} catch (Exception e) {
		}
	}
	
	/**
	 * ¥Ç¥Ð¥Ã¥°ÍÑ
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		if(false) {
			new Thread() {
				public void run() {
					try {
						ServerSocket ss0 = new ServerSocket(50000);
	//					sleep(1000);
						Socket s = ss0.accept();
						PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
						BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
						pw.println("HelloA");
						System.out.println(br.readLine());
						sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
			sleep(1000);
			Socket s1 = new Socket(args[0], 50000);
			PrintWriter pw = new PrintWriter(s1.getOutputStream(), true);
			BufferedReader br = new BufferedReader(new InputStreamReader(s1.getInputStream()));
			pw.println("HelloB");
			System.out.println(br.readLine());
			sleep(1000);
			
			System.exit(0);;
		}
		if(args.length==1) {
			String host = args[0];
			Net a = new Net(50101, 100);
//			Net.sleep(1000);
			a.send(host, 50101, "Hello from a");
			a.recv();
//			System.out.println("A");
//			a.send(host, 50101, "Hello from a2");
//			a.recv();
//			Net.sleep(1000);
			a.end();
		} else {
			// ·×»»µ¡ A ¤Ç
			new Thread() {
				public void run() {
					Net a = new Net(50101, 100);
//					Net.sleep(1000);
					a.send("localhost", 50102, "Hello from a");
					a.recv();
					a.send("localhost", 50102, "Hello from a2");
//					Net.sleep(2000);
					a.end();
				}
			}.start();
			// ·×»»µ¡ B ¤Ç
			new Thread() {
				public void run() {
					Net b = new Net(50102, 100);
//					Net.sleep(1000);
					b.send("localhost", 50101, "Hello from b");
					b.recv();
					b.recv();
//					Net.sleep(2000);
					b.end();
				}
			}.start();
		}
	}
}

/**
 * ¼õ¿®À?ÍÑ¥¹¥?¥Ã¥É
 */
class RecvThread extends Thread {
	
	Net net;
	
	boolean goahead = true;
	
	RecvThread(Net n) {
		net = n;
		start();
	}
	public void run() {
		while(goahead) {
			try {
				synchronized(net.pool) {
					Iterator it = net.pool.values().iterator();
					while(it.hasNext()) {
						Connection p = (Connection)it.next();
						p.addBuf();
					}
				}
//				sleep(10);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void end() {
		goahead = false;
	}
}

/**
 * ¥Ý¡¼¥ÈÂÔ¤Á¤¦¤±¥¹¥?¥Ã¥É
 */
class AcceptThread extends Thread {
	
	int ap, bl;
	
	ServerSocket ssock;
	
	Net net;
	
	boolean goahead = true;
	
	AcceptThread(int acceptPort, int backlog, Net net) {
		ap = acceptPort;
		bl = backlog;
		this.net = net;
		try {
//			System.out.println(ap+" "+bl);
			ssock = new ServerSocket(ap, bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		start();
	}
	public void end() {
		goahead = false;
	}
	public void run() {
		while(goahead) {
			try {
				ssock.setSoTimeout(100);
				Socket sock = ssock.accept();
//				System.out.println("Accepted : "+sock+" "+sock.getLocalSocketAddress());
				net.addSocket(sock, null, 0);
			} catch (SocketTimeoutException e) {
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

/**
 * £±¤Ä¤ÎÄÌ¿®Àè¡ÊIP¤¬Æ±¤¸ÄÌ¿®Àè¡Ë¤Ë´Ø¤¹¤?ÀÜÂ³¾ðÊó¡£
 */
class Connection {
	
	public List sockets;
	
	public List buf;
	
	Connection(Socket s) {
		sockets = new ArrayList();
		buf = Collections.synchronizedList(new LinkedList());
		addSocket(s);
//		System.out.println("# NEW TUPLE. "+this);
	}
	synchronized void addSocket(Socket s) {
		sockets.add(new SocketTuple(s));
	}
	public String removeTop() {
		synchronized(buf) {
			if(!buf.isEmpty()) return (String)buf.remove(0);
		}
		return null;
	}
	public void addBuf() {
		try {
			synchronized(buf) {
				for(int i=0;i<sockets.size();i++) {
					SocketTuple st = (SocketTuple)sockets.get(i);
					if(st.reader.ready()) buf.add(st.reader.readLine());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String toString() {
		synchronized(buf) {
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<sockets.size();i++) sb.append(sockets.get(i)+" "+sockets.get(i).hashCode()+" ");
			return " "+buf+" "+sb;
		}
	}
}

/**
 * £±¤Ä¤Î¥½¥±¥Ã¥È¤Ë´Ø¤¹¤?¾ðÊ?
 */
class SocketTuple {
	
	public Socket sock;
	
	public BufferedReader reader;
	
	public PrintWriter writer;
	
	SocketTuple(Socket s) {
		sock = s;
		try {
			reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			writer = new PrintWriter(sock.getOutputStream(), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String toString() {
		return " "+sock;
	}
}

/**
 * ¼õ¿®¤·¤¿Ê¸»úÎó¤ÈÁ÷¿®¼Ô¾ðÊó¤ò´Þ¤à¥¯¥é¥¹¡£
 */
class Packet {
	
	public SocketAddress ia;
	
	public String value;
	
	Packet(SocketAddress i, String v) {
		System.out.println("< Packet "+i+"  "+v);
		ia=i;v=value;
	}
}

public class SomeInlineCodemsg implements InlineCode {
	public void run(Atom me, int codeID) {
		AbstractMembrane mem = me.getMem();
		switch(codeID) {
		case 1: {
			/*inline*/
	NetHolder.init(mem);
	NetHolder.net.send(me.nth(0), me.nth(1));
	
			break; }
		case 0: {
			/*inline*/
	NetHolder.init(mem, ((IntegerFunctor)me.nthAtom(0).getFunctor()).intValue());
	new GenThread().start();
	
			break; }
		}
	}
}
