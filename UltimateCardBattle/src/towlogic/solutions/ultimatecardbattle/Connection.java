package towlogic.solutions.ultimatecardbattle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

import towlogic.solutions.ultimatecardbattle.R.id;
import android.app.Activity;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.text.format.Formatter;

public class Connection extends Activity {

	private Button btnHost;
	private Button btnFind;
	private EditText editNickName;
	private EditText edittIp;
	public static final int SERVERPORT = 8080;
	private Handler handler = new Handler();
	private ServerSocket serverSocket;
	private String SERVERIP;
	private TextView serverStatus;
	private String serverIpAddress ="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connection);

		getViewComponents();

		WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
		SERVERIP = Formatter.formatIpAddress(wm.getConnectionInfo()
				.getIpAddress());

	}

	private void getViewComponents() {

		btnFind = (Button) findViewById(id.btnFind);
		btnHost = (Button) findViewById(id.btnHost);
		editNickName = (EditText) findViewById(id.edittNickName);
		edittIp = (EditText) findViewById(id.edittIp);
		serverStatus = (TextView) findViewById(id.serverStatus);
	
		btnHost.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Thread fst = new Thread(new ServerThread());
				fst.start();

			}
		});

		btnFind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ClientConnection c = new ClientConnection(edittIp.getText().toString());
				
				c.run();	
					
				
			}
		});

	}

	public class ServerThread implements Runnable {

		public void run() {
			try {
				if (SERVERIP != null) {
					handler.post(new Runnable() {
						@Override
						public void run() {
							serverStatus
									.setText("Listening on IP: " + SERVERIP);
						}
					});
					serverSocket = new ServerSocket(SERVERPORT);
					while (true) {
						// LISTEN FOR INCOMING CLIENTS
						Socket client = serverSocket.accept();
						handler.post(new Runnable() {
							@Override
							public void run() {
								serverStatus.setText("Connected.");
							}
						});

						try {
							BufferedReader in = new BufferedReader(
									new InputStreamReader(
											client.getInputStream()));
							String line = null;
							while ((line = in.readLine()) != null) {
								Log.d("ServerActivity", line);
								handler.post(new Runnable() {
									@Override
									public void run() {
										// DO WHATEVER YOU WANT TO THE FRONT END
										// THIS IS WHERE YOU CAN BE CREATIVE
									}
								});
							}
							break;
						} catch (Exception e) {
							handler.post(new Runnable() {
								@Override
								public void run() {
									serverStatus
											.setText("Oops. Connection interrupted. Please reconnect your phones.");
								}
							});
							e.printStackTrace();
						}
					}
				} else {
					handler.post(new Runnable() {
						@Override
						public void run() {
							serverStatus
									.setText("Couldn't detect internet connection.");
						}
					});
				}
			} catch (Exception e) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						serverStatus.setText("Error");
					}
				});
				e.printStackTrace();
			}
		}
	}

	// GETS THE IP ADDRESS OF YOUR PHONE'S NETWORK
	private String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("ServerActivity", ex.toString());
		}
		return null;
	}

}
