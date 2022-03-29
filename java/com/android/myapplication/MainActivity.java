package com.android.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView outPutMonitor;
    private TextView outputUnit;
    private EditText et;
    private Spinner unit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outPutMonitor = findViewById(R.id.outPutMonitor);
        outputUnit = findViewById(R.id.outPutUnit);
        et = findViewById(R.id.edit_text);

        unit = findViewById(R.id.unitSp);
        findViewById(R.id.buttonMeter).setOnClickListener(this);
        findViewById(R.id.buttonTem).setOnClickListener(this);
        findViewById(R.id.buttonKg).setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        if(null == view) {
            return;
        }
        String selectType = unit.getSelectedItem().toString();
        String input = et.getText().toString().trim();
        DecimalFormat df = new DecimalFormat(("#.00"));

        if(TextUtils.isEmpty(input)) {
            Toast.makeText(getApplicationContext(), "Warning--Please input number", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (view.getId()){
            case R.id.buttonMeter:
                if(TextUtils.equals(selectType,"meter")) {
                    String r1 = df.format(Double.parseDouble(input) * 100);
                    String r2 = df.format(Double.parseDouble(input) * 3.2808398950131);
                    String r3 = df.format(Double.parseDouble(input) * 39.3700787402);
                    outPutMonitor.setText(r1 + "\n\n" + r2 + "\n\n" + r3);
                    outputUnit.setText("Centimeter " + "\n\n" + "Foot" + "\n\n" + "Inch" );
                } else{
                    outPutMonitor.setText("");
                    outputUnit.setText("");
                    Toast.makeText(getApplicationContext(), "Warning--Please select the correct conversion icon.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonTem:
                if(TextUtils.equals(selectType,"celsius")) {
                    String r1 = df.format(Double.valueOf(input) * 1.8 + 32);
                    String r2 = df.format(Double.valueOf(input) + 273.15);
                    outPutMonitor.setText(r1 + "\n\n" + r2);
                    outputUnit.setText("Fahrenheit " + "\n\n" + "Kelvin" );
                }
                else{

                    outPutMonitor.setText("");

                    outputUnit.setText("");

                    Toast.makeText(getApplicationContext(), "Warning--Please select the correct conversion icon.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonKg:
                if(TextUtils.equals(selectType,"kilograms")) {
                    String r1 = df.format(Double.valueOf(input) * 1000);
                    String r2 = df.format(Double.valueOf(input) * 35.27396194958);
                    String r3 = df.format(Double.valueOf(input) * 2.2046226218488);
                    outPutMonitor.setText(r1 + "\n\n" + r2 + "\n\n" + r3);
                    outputUnit.setText("Gram " + "\n\n" + "Ounce(Oz)" + "\n\n" + "Pound(Ib)");
                }
                else{
                    outPutMonitor.setText("");
                    outputUnit.setText("");
                    Toast.makeText(getApplicationContext(), "Warning--Please select the correct conversion icon.", Toast.LENGTH_SHORT).show();
                }
            break;
            default:
                break;
        }
    }

}
