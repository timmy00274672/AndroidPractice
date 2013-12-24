package com.example.wifi;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String TAG = "MyActivity";
	protected static final String FILENAME = "myFile.txt";
	private TextView tv;
	private Button scan;
	public WifiManager wm;
	WifiManager.WifiLock wmlock;// 阻止WIFI進入睡眠
	Handler mHandler;
	int cnt = 0;
	String otherwifi;

	private WifiInfo info;
	int strength;
	int speed;
	private List<ScanResult> results;

	FileWriter fw;
	BufferedWriter bw;

	/*
	 * public MainActivity(Context context){ wm = (WifiManager)
	 * context.getSystemService(context.WIFI_SERVICE); info =
	 * wm.getConnectionInfo(); }
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		wm = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
		scan = (Button) findViewById(R.id.scan);
		scan.setOnClickListener(startClick);
		// String wserviceName = Context.WIFI_SERVICE;
		tv = (TextView) findViewById(R.id.wifiSS);
		results = new ArrayList<ScanResult>();
		mHandler = new Handler();

	}

	// /設定一個按鈕再開始掃描附近AP
	// 並將動作post給runnable

	OnClickListener startClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			try {
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
						openFileOutput(FILENAME, Context.MODE_PRIVATE));
				outputStreamWriter.write("HIHI");
				outputStreamWriter.close();
			} catch (IOException e) {
				Log.e(TAG, "File write failed: " + e.toString());
			}

			// mHandler.post(r1);
			//
			// if (!wm.isWifiEnabled()) {
			//
			// wm.setWifiEnabled(true);
			// // Toast.makeText(MainActivity.this, "WiFi開啟中",
			// // Toast.LENGTH_SHORT).show();
			// }
			//
			// wm.startScan();
			// // 偵測周圍WIFI環境
			// results = wm.getScanResults();
			// // 目前連線WIFI資訊
			// WifiInfo info = wm.getConnectionInfo();
			// // configure = wm.getConfiguredNetworks();
			// strength = info.getRssi();
			// speed = info.getLinkSpeed();
			//
			// // WifiInfo info = wm.getConnectionInfo();
			// /*
			// * int strength = info.getRssi(); int speed = info.getLinkSpeed();
			// * String units = WifiInfo.LINK_SPEED_UNITS; String ssid =
			// * info.getSSID();
			// */

		}

	};
	int i, j, k, m;
	int d = 0;
	int num[][] = new int[50][10];
	String data;
	int sum1, sum2, sum3, sum4, sum5 = 0;
	double avg1, avg2, avg3, avg4, avg5 = 0;
	double avg, sigma, deviation;
	int count = 5;// 取幾台AP
	// double sigma [] = new double[5];
	// double deviation [] = new double[5];

	final Runnable r1 = new Runnable() {
		@Override
		public void run() {
			// 將每次掃描的資料存入txt檔 每一次scan會得到附近所有AP的訊號 目前設定擷取5台AP資訊(count=5)
			// 整個runnable會每delay 0.5秒再run，run50個循環，因此會得到附近5台AP各50個訊號
			// 目前data都寫不進txt檔裡
			wm.startScan(); // 每RUN一次都重新掃描一次訊號
			for (int i = 0; i < count; i++) {
				results = wm.getScanResults();
				Log.d(TAG, data + " ");
				data = results.get(i).SSID + results.get(i).level; // SSID為AP名稱，level為訊號強度
			}

			try {
				FileWriter fw = new FileWriter("D:\\test.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(data);
				bw.newLine();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
				Log.d(TAG, "WTFFFFF");
			}

			// 計算每一台AP 50個樣本的平均強度，以及每台AP標準差
			// 但是這邊我計算失敗了 所以先藏起來 先解決把data存進txt再解決這兒

			/*
			 * for (int j=0; j < 5; j++){
			 * 
			 * num[i][j] = results.get(j).level; Log.d(TAG, num[i][j]+" "); sum1
			 * += num[i][1]; sum2 += num[i][2]; sum3 += num[i][3]; sum4 +=
			 * num[i][4]; sum5 += num[i][5]; //Log.d(TAG,
			 * sum1+" "+sum2+" "+sum3+" "+sum4+" "+sum5); } avg1 = sum1/50; avg2
			 * = sum2/50; avg3 = sum3/50; avg4 = sum4/50; avg5 = sum5/50;
			 * Log.d(TAG, avg1+" "+avg2+" "+avg3+" "+avg4+" "+avg5); //}
			 * 
			 * double[] sigma = {0.0,0.0,0.0,0.0,0.0}; double[] deviation =
			 * {0.0,0.0,0.0,0.0,0.0}; for(int k = 0;k < 5; k++){
			 * 
			 * sigma[k] += Math.pow(results.get(k).level - avg, 2); deviation[k]
			 * = Math.sqrt(sigma[k] / count); ////results.add(results.get(i));
			 * otherwifi += results.get(k).SSID + "\n" +"SS:" + avg +
			 * "  SD:"+deviation[k] + "\n" ; //Log.d(TAG,
			 * results.get(k).SSID+results.get(k).level+"\n"+sigma);
			 * 
			 * }
			 */

			// 每delay 0.5秒 RUN一次
			if (d < 50) {
				d++;
				mHandler.postDelayed(r1, 500);
			}

			tv.setText(otherwifi);

			Log.d(TAG, "helloooooo");

		}

		/*
		 * public boolean onCreateOptionsMenu(Menu menu) { // Inflate the menu;
		 * this adds items to the action bar if it is present.
		 * getMenuInflater().inflate(R.menu.main, menu); return true; }
		 */

	};

}
