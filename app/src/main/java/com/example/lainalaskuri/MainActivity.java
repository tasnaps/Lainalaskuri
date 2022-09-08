package com.example.lainalaskuri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    SeekBar sbInterestRate = findViewById(R.id.sbLoanInterest);
    //luodaan kuuntelija
    private void UpdateSeekbarText(){
        TextView tvInterestRate = findViewById(R.id.tvInterestRate);
        SeekBar sbInterestRate = findViewById(R.id.sbLoanInterest);
        String interestRateLocalizedText = getString(R.string.interest_rate);

        //UI update

        interestRateLocalizedText += String.format("%d %%", sbInterestRate);
        tvInterestRate.setText(interestRateLocalizedText);

    }
    private class SeekBarProgressWatcher implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            UpdateSeekbarText();
            UpdateResults();
        }
        public void onStartTrackingTouch(SeekBar seekBar) {}
        public void onStopTrackingTouch(SeekBar seekBar) {}
    }
    private class TextChangeWatcher implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void afterTextChanged(Editable editable) {
            UpdateResults();
        }
    }

    private void UpdateResults() {
        //korkojen kaava (principal loan amount) * (interest rate) * (Number of years in term) = Total interest paid
        try {

            EditText etLoanAmount = findViewById(R.id.etLoanAmount);
            EditText etLoanDuration = findViewById(R.id.etLoanDuration);

            double loanAmount = Double.parseDouble(etLoanAmount.getText().toString());
            int loanDurationMonths = Integer.parseInt(etLoanDuration.getText().toString());


            SeekBar sbLoanInterest = findViewById(R.id.sbLoanInterest);
            int loanInterest = sbLoanInterest.getProgress();
            double normalizedInterest = loanInterest/100d;


            double totalInterest = loanAmount * normalizedInterest * loanDurationMonths/12;
            double totalAmount = loanAmount + totalInterest;


            //Get localized strings

            String strLoanAmount = getString(R.string.loan_amount);
            String strLoanDuration = getString(R.string.loan_duration);
            String strInterest = getString(R.string.interest_rate);
            String strPayableInterest = getString(R.string.payable_interest);
            String strPayableTotal = getString(R.string.payable_total);
            String strLoanInfo = getString(R.string.loan_info);
            String strResults = getString(R.string.results);

            String resultText = String.format("%s\n", strLoanInfo);
            resultText += String.format("%s %.2f\n", strLoanAmount, loanAmount);
            resultText += String.format("%s %d %%\n", strInterest, loanInterest);
            resultText += String.format("%s %d\n\n", strLoanDuration, loanDurationMonths);

            resultText += String.format("%s\n", strResults);
            resultText += String.format("%s %.2f\n", strPayableInterest, totalInterest);
            resultText += String.format("%s %.2f\n", strPayableTotal, totalAmount);

            TextView tvResults = findViewById(R.id.tvResults);
            tvResults.setText(resultText);

        } catch (NumberFormatException e) {
            return;
        }



    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sbInterestRate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                UpdateSeekbarText();
            }

            public void onStartTrackingTouch(SeekBar seekBar){}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        sbInterestRate.findViewById(R.id.sbLoanInterest);
        sbInterestRate.setOnSeekBarChangeListener(new SeekBarProgressWatcher());

        EditText etLoanAmount = findViewById(R.id.etLoanAmount);
        EditText etLoanDurationMonths = findViewById(R.id.etLoanDuration);

        TextChangeWatcher textChanged = new TextChangeWatcher();
        etLoanAmount.addTextChangedListener(textChanged);
        etLoanDurationMonths.addTextChangedListener(textChanged);



    }
}