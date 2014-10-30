package towlogic.solutions.ultimatecardbattle;

import towlogic.solutions.ultimatecardbattle.R.id;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu_1 extends Activity {

	private Button btnStart;
	private Button btnInstrutions;
	private Button btnExit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu_1);
		
		getViewComponents();
	}
	
	protected void getViewComponents(){
		btnStart = (Button) findViewById(id.btnStart);
		btnInstrutions = (Button) findViewById(id.btnInstructions);
		btnExit = (Button) findViewById(id.btnExit);
		
		btnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(MainMenu_1.this,Connection.class);
				startActivity(intent);
				
			}
		});
		
		btnInstrutions.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		btnExit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
