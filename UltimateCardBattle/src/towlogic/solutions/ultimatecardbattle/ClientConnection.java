package towlogic.solutions.ultimatecardbattle;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import android.os.Handler;
import android.util.Log;

public class ClientConnection implements Runnable{
	private boolean connected = false;
	
	String serverIpAddress ="";

	private Handler handler = new Handler();
	@Override
	public void run() {
		  try {
              InetAddress serverAddr = InetAddress.getByName(serverIpAddress);
              Log.d("ClientActivity", "C: Connecting...");
              Socket socket = new Socket(serverAddr, 8080);
              connected = true;
              while (connected) {
                  try {
                      Log.d("ClientActivity", "C: Sending command.");
                      PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                                  .getOutputStream())), true);
                          // WHERE YOU ISSUE THE COMMANDS
                          out.println("Hey Server!");
                          Log.d("ClientActivity", "C: Sent.");
                  } catch (Exception e) {
                      Log.e("ClientActivity", "S: Error", e);
                  }
              }
              socket.close();
              Log.d("ClientActivity", "C: Closed.");
          } catch (Exception e) {
              Log.e("ClientActivity", "C: Error", e);
              connected = false;
          }
      }

		
	}
	

