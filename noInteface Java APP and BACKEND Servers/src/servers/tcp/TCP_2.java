package servers.tcp;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


public class TCP_2 extends Thread {
    private int timeOut = 5000;
	private int timeToPing = 1000;
	TCPSERVER server= null;
	private int PORT;
	
	TCP_2(TCPSERVER server){
		
		this.server= server;
		PORT=3000;
		
		this.start();
	}
	
	public void run(){
		
		//receive
		DatagramSocket socket=null;
		
		try{
			
			socket=new DatagramSocket(PORT);
			socket.setSoTimeout(timeOut);
                       
			
			
			while(true){
				byte[] buf = new byte[1024];
				DatagramPacket msg = new DatagramPacket(buf,1024);
				socket.receive(msg);
				
				String received = new String(msg.getData(),0,msg.getLength());
				System.out.println("Backup" +received);
				
			}
                        //Servidor inicial foi a baixo
		}catch(SocketTimeoutException e2){
			
	
			socket.close();
			synchronized (server)
			{
				server.notifyAll();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Wait 5 seconds please, and try again.");
		}
		
		
		
		DatagramSocket socket2;
		
		try {
                       
			socket2 = new DatagramSocket();
			while(true){

				String str = "Ping";
				InetAddress ia = InetAddress.getByName("localhost");
				DatagramPacket msg = new DatagramPacket(str.getBytes(), str.length(), ia, PORT);
				socket2.send(msg);


				Thread.sleep(timeToPing);



			}

		} catch (UnknownHostException e) {

			e.printStackTrace();
			System.out.println("Erro no inetaddress");
		}catch (IOException e) {

			e.printStackTrace();
			System.out.println("Erro ao pingar ");
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	

    
}
