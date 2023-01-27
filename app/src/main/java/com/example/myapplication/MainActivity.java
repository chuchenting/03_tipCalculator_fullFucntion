package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import java.text.NumberFormat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    private double billAmount = 0.0;
    private double customPercent = 0.18;

    private EditText amountEditText;
    private SeekBar  customTipSeekBar;
    private TextView percentCustomTextView;

    private TextView tip15TextView;
    private TextView total15TextView;
    private TextView tipCustomTextView;
    private TextView totalCustomTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amountEditText = (EditText) findViewById(R.id.amountTextNumber);
        customTipSeekBar = (SeekBar) findViewById(R.id.customTipSeekBar);
        percentCustomTextView = (TextView) findViewById(R.id.percentCustomTextView);

        tip15TextView = (TextView) findViewById(R.id.tip15TextView);
        total15TextView = (TextView) findViewById(R.id.total15TextView);
        tipCustomTextView = (TextView) findViewById(R.id.tipCustomTextView);
        totalCustomTextView = (TextView) findViewById(R.id.totalCustomTextView);

        TextWatcherHandler editTextHandler = new TextWatcherHandler();
        amountEditText.addTextChangedListener(editTextHandler);

        OnSeekBarChangeHandler seekBarHandler = new OnSeekBarChangeHandler();
        customTipSeekBar.setOnSeekBarChangeListener(seekBarHandler);

        Update15();
        UpdateCustom();
    }

    private void Update15(){
        double tip = billAmount * 0.15;
        double total = billAmount + tip;

        tip15TextView.setText(currencyFormat.format(tip));
        total15TextView.setText(currencyFormat.format(total));
    }

    private void UpdateCustom(){
        double tip = billAmount * customTipSeekBar.getProgress() / 100.0 ;
        double total = billAmount + tip;

        percentCustomTextView.setText(percentFormat.format(customPercent));
        tipCustomTextView.setText(currencyFormat.format(tip));
        totalCustomTextView.setText(currencyFormat.format(total));
    }
    public class OnSeekBarChangeHandler implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            customPercent = progress / 100.0;
            Update15();
            UpdateCustom();

        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    }

    public class TextWatcherHandler implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try{
                billAmount = Double.parseDouble(s.toString());
            }catch(NumberFormatException e){
                billAmount = 0.0;
            }
            UpdateCustom();
        }
        @Override
        public void afterTextChanged(Editable s) {}
    }
}