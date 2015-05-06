package org.lhc.thermometer;

import java.math.BigDecimal;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	private SensorManager mSensorManager;
	private Sensor temperature;
	private Sensor humidity;
	
	private SensorEventListener tempListener;
	private SensorEventListener humidityListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//		List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
//		for (Sensor sensor : deviceSensors) {
//			Log.i("sensor", "------------------");
//			Log.i("sensor", sensor.getName());
//			Log.i("sensor", sensor.getVendor());
//			Log.i("sensor", Integer.toString(sensor.getType()));
//			Log.i("sensor", "------------------");
//		}
		temperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
		humidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
				
		this.tempListener = new TempListener();
		this.humidityListener = new HumidityListener();
		
		mSensorManager.registerListener(tempListener, temperature, SensorManager.SENSOR_DELAY_NORMAL);
		mSensorManager.registerListener(humidityListener, humidity, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.onPause();
//			Log.i("Sensor", "exit");
			this.finish();
			System.exit(0);
		}
		
		return super.onKeyDown(keyCode, event);
	}

	
	
	@Override
	protected void onPause() {
		super.onPause();

	    mSensorManager.unregisterListener(this.tempListener);
	    mSensorManager.unregisterListener(this.humidityListener);
	    
//	    Log.i("Sensor", "sensor pause");
	}
	
	private class TempListener implements SensorEventListener {
		@Override
		  public final void onSensorChanged(SensorEvent event) {
		    float temperatureValue = event.values[0];    // 利用这些数据执行一些工作
		    BigDecimal bd = new BigDecimal(temperatureValue);
			double temperature = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//		    Log.i("Sensor", "sensor changed==>" + millibars_of_pressure);
		    TextView tem = (TextView)MainActivity.this.findViewById(R.id.temperature);
		    tem.setText(temperature + "℃");
		  }
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
//			Log.i("Sensor", "onAccuracyChanged");
		}
	}
	
	private class HumidityListener implements SensorEventListener {
		@Override
		public final void onSensorChanged(SensorEvent event) {
			float humidityValue = event.values[0];    // 利用这些数据执行一些工作
			BigDecimal bd = new BigDecimal(humidityValue);
			double humidity = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//			Log.i("Sensor", "sensor changed==>" + millibars_of_pressure);
			TextView tem = (TextView)MainActivity.this.findViewById(R.id.humidity);
			tem.setText(humidity + "%");
		}
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
//			Log.i("Sensor", "onAccuracyChanged");
		}
	}
}
