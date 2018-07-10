package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight;
    EditText etHeight;
    Button btnCalculate;
    Button btnReset;
    TextView tvDate;
    TextView tvBMI;
    TextView tvDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etHeight = findViewById(R.id.editTextHeight);
        etWeight = findViewById(R.id.editTextWeight );
        btnCalculate = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvDes = findViewById(R.id.textViewDes);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float fltWeight = Float.parseFloat(etWeight.getText().toString());
                float fltHeight = Float.parseFloat(etHeight.getText().toString());
                float BMI = fltWeight / (fltHeight * fltHeight);

                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                tvDate.setText("Last Calculated Date:" + datetime);
                tvBMI.setText(String.format("Last Calculated BMI:" +  "%.3f", BMI   ));
                etHeight.setText("");
                etWeight.setText("");
                tvDes.setText("");

                if (BMI == 0.0){
                    tvDes.setText("");
                }
                else if(BMI >= 30){
                    tvDes.setText("You are obese");
                }
                else if( BMI >= 25){
                    tvDes.setText("You are overweight");
                }
                else if ( BMI >= 18.5){
                    tvDes.setText("Your BMI is normal");
                }
                else if( BMI <18.5){
                    tvDes.setText("You are underweight");
                }
                else{
                    tvDes.setText("Error");
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                etHeight.setText("");
                etWeight.setText("");
                tvDate.setText("Last Calculated Date:");
                tvBMI.setText("Last Calculated BMI:");
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

        String StrDate = tvDate.getText().toString();
        String StrBMI = tvBMI.getText().toString();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putString("Date", StrDate);
        prefEdit.putString("BMI", StrBMI);
        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String Date = prefs.getString("Date", "");
        String BMI = prefs.getString("BMI", "");
        tvBMI.setText(BMI);
        tvDate.setText(Date);
    }
}
