package translated.module_dlmntal;
import runtime.*;
import java.util.*;
/*inline_define*/
import java.io.*;
import java.net.*;

/**
 *  class: AcceptThread
 */
class AcceptThread extends Thread {
  private ServerSocket servSock;
  private final Membrane mem;
  private Atom ssAtom = null;

  /**
   *  AcceptThread#AcceptThread()
   *  @param servSock
   *  @param mem
   *  @param ssAtom
   */
  AcceptThread(ServerSocket servSock, Membrane mem, Atom ssAtom) {
    this.servSock = servSock;
    this.mem = mem;
    this.ssAtom = ssAtom;
  }
  
  /**
   *  AcceptThread#run()
   */
  public void run() {
    try {
      Socket sock = servSock.accept();
      BufferedReader reader = 
        new BufferedReader(new InputStreamReader(sock.getInputStream()));
      PrintWriter writer = new PrintWriter(sock.getOutputStream());
      ReaderThread rt = new ReaderThread(sock,reader,mem);
      Vector obj = new Vector(3);
      obj.add(sock);
      obj.add(writer);
      obj.add(rt);
      Functor objFunc = new ObjectFunctor(obj);
      mem.asyncLock();
      Atom objAtom = mem.newAtom(objFunc);
      rt.setObjAtom(objAtom);
      Atom sockAtom = mem.newAtom(new Functor("socket",3,"dlmntal"));
      Atom consAtom = ssAtom.nthAtom(0).nthAtom(0);
      Atom cmdAtom = consAtom.nthAtom(0);
      mem.relinkAtomArgs(sockAtom,0,cmdAtom,0);
      mem.relinkAtomArgs(sockAtom,1,cmdAtom,1);
      mem.newLink(sockAtom,2,objAtom,0);
      mem.unifyAtomArgs(consAtom,1,consAtom,2);
      consAtom.remove();
      cmdAtom.remove();
      mem.asyncUnlock();
      Thread t = new Thread(rt,"rt");
      rt.start();
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
}

/** 
 *  class: ReaderThread
 */
class ReaderThread implements Runnable {
  volatile Thread th = null;
  private Socket sock;
  private BufferedReader reader;
  private final Membrane mem;
  private Atom objAtom = null;
  
  /**
   *  ReaderThread#ReaderThread()
   *  @param sock
   *  @param reader
   *  @param mem
   */
  ReaderThread(Socket sock, BufferedReader reader, Membrane mem) {
    this.sock = sock;
    this.reader = reader;
    this.mem = mem;
  }
  
  /**
   *  ReaderThread#setObjAtom()
   *  @param objAtom
   */
  public void setObjAtom(Atom objAtom) {
    this.objAtom = objAtom;
  }
  
  /**
   *  ReaderThread#start()
   */
  public void start() {
    if(th == null) {
      th = new Thread(this);
      th.start();
    }
  }
  
  /**
   *  ReaderThread#run()
   */
  public void run(){
    Thread thisThread = Thread.currentThread();
    String data;
    while(th == thisThread) {
      try {
        while(reader.ready()) {
          data = reader.readLine();
          Atom sockAtom = objAtom.nthAtom(0);
          mem.asyncLock();
          Atom consAtom = mem.newAtom(new Functor(".",3));
          if(data.equals("CLOSE")) {
            Atom closeAtom = mem.newAtom(new Functor("close",1));
            mem.newLink(closeAtom,0,consAtom,0);
            mem.relinkAtomArgs(consAtom,1,sockAtom,0);
            mem.newLink(consAtom,2,sockAtom,0);
          } else {
            Atom dataAtom = mem.newAtom(new StringFunctor(data));
            mem.newLink(dataAtom,0,consAtom,0);
            mem.relinkAtomArgs(consAtom,2,sockAtom,1);
            mem.newLink(consAtom,1,sockAtom,1);
          }
          mem.asyncUnlock();
        }
      } catch(IOException e) {
        e.printStackTrace();
      }
      try {
        th.sleep(50);
      } catch(InterruptedException e) {
        e.printStackTrace();
      }
    }
    th = null;
  }
  
  /**
   *  ReaderThread#stop()
   */
  public void stop() {
    th = null;
    try {
      reader.close();
      sock.close();
      System.out.println("ReaderThread: stopped");
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
}

public class SomeInlineCodedlmntal {
	public static void run(Atom me, int codeID) {
		AbstractMembrane mem = me.getMem();
		switch(codeID) {
		case 2: {
			/*inline*/
    ObjectFunctor objFunc = (ObjectFunctor)me.nthAtom(0).getFunctor();
    ServerSocket servSock = (ServerSocket)objFunc.getObject();
    try {
      servSock.close();
      System.out.println("ServerSocket: closed");
    } catch(IOException e) {
      e.printStackTrace();
    }
    me.nthAtom(0).remove();
    me.remove();
  
			break; }
		case 4: {
			/*inline*/
    String data = me.nth(0);
    ObjectFunctor objFunc = (ObjectFunctor)me.nthAtom(1).getFunctor();
    Vector obj = (Vector)objFunc.getObject();
    PrintWriter writer = (PrintWriter)obj.get(1);
    writer.println(data);
    writer.flush();
    mem.unifyAtomArgs(me,1,me,2);
    me.nthAtom(0).remove();
    me.remove();
  
			break; }
		case 5: {
			/*inline*/
    ObjectFunctor objFunc = (ObjectFunctor)me.nthAtom(0).getFunctor();
    Vector obj = (Vector)objFunc.getObject();
    PrintWriter writer = (PrintWriter)obj.get(1);
    writer.println("CLOSE");
    writer.close();
    ((ReaderThread)obj.get(2)).stop();
    me.nthAtom(0).remove();
    me.remove();
  
			break; }
		case 1: {
			/*inline*/
    mem.makePerpetual();
    ObjectFunctor ssFunc = (ObjectFunctor)me.nthAtom(0).getFunctor();
    ServerSocket servSock = (ServerSocket)ssFunc.getObject();
    AcceptThread at = 
      new AcceptThread(servSock,(Membrane)mem,me.nthAtom(0));
    at.setName("at");
    mem.unifyAtomArgs(me,0,me,1);
    me.remove();
    at.start();
  
			break; }
		case 6: {
			/*inline*/
    ((Membrane)mem).perpetual = false;
    me.remove();
  
			break; }
		case 3: {
			/*inline*/
    String host = me.nth(0);
    // throws NumberFormatException 
    int port = Integer.parseInt(me.nth(1));
    try {
      // throws UnknownHostException and SecurityException
      InetAddress ip = InetAddress.getByName(host);
      // throws IOException and SecurityException
      Socket sock = new Socket(ip,port);
      // throws IOException
      BufferedReader reader = 
        new BufferedReader(new InputStreamReader(sock.getInputStream()));
      // throws IOException
      PrintWriter writer = new PrintWriter(sock.getOutputStream());
      ReaderThread rt = new ReaderThread(sock,reader,(Membrane)mem);
      Vector obj = new Vector(3);
      obj.add(sock);
      obj.add(writer);
      obj.add(rt);
      Functor objFunc = new ObjectFunctor(obj);
      Atom objAtom = mem.newAtom(objFunc);
      rt.setObjAtom(objAtom);
      mem.relinkAtomArgs(objAtom,0,me,2);
      mem.makePerpetual();
      Thread t = new Thread(rt,"rt");
      rt.start();
      me.nthAtom(0).remove();
      me.nthAtom(1).remove();
      me.remove();
    } catch(IOException e) {
      e.printStackTrace();
      Atom failedAtom = mem.newAtom(new Functor("failed",4));
      mem.relinkAtomArgs(failedAtom,0,me.nthAtom(0),0);
      mem.relinkAtomArgs(failedAtom,1,me.nthAtom(1),0);
      mem.relinkAtomArgs(failedAtom,2,me.nthAtom(2),0);
      mem.relinkAtomArgs(failedAtom,3,me.nthAtom(2),1);
      me.nthAtom(2).remove();
      me.remove();
    }
  
			break; }
		case 0: {
			/*inline*/
    // throws NumberFormatException 
    int port = Integer.parseInt(me.nth(0));
    try {
      // throws IOException and SecurityException
      ServerSocket servSock = new ServerSocket(port);
      Functor ssFunc = new ObjectFunctor(servSock);
      Atom ssAtom = mem.newAtom(ssFunc);
      mem.relinkAtomArgs(ssAtom,0,me,1);
      me.nthAtom(0).remove();
      me.remove();
    } catch(IOException e) {
      e.printStackTrace();
      Atom failedAtom = mem.newAtom(new Functor("failed",2));
      mem.relinkAtomArgs(failedAtom,0,me.nthAtom(0),0);
      mem.relinkAtomArgs(failedAtom,1,me.nthAtom(1),0);
      me.nthAtom(1).remove();
      me.remove();
    }
  
			break; }
		}
	}
}
