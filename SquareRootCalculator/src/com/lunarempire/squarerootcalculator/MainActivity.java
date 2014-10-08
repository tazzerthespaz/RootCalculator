package com.lunarempire.squarerootcalculator;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.*;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //setting up the variables and Views
        final EditText editOuterNum = (EditText)findViewById(R.id.editOuterNum);
        final EditText editInnerNum = (EditText)findViewById(R.id.editInnerNum);
        final Button btnCalc = (Button)findViewById(R.id.btnCalc);
        final Button btnClear = (Button)findViewById(R.id.btnClear);
        final TextView txtSimpRad = (TextView)findViewById(R.id.txtSimpRad);
        final TextView txtSimpDec = (TextView)findViewById(R.id.txtSimpDec);
        final TextView txtBefore = (TextView)findViewById(R.id.txtBefore);
        final EditText editIndex = (EditText)findViewById(R.id.editIndex);
        final String inbetweenPos = "√(";
		final String simp = "Simplified Form: ";
		final String approx = "About: ";
		
		//This is the setup for the out of range toast error
		Context context = getApplicationContext();
		CharSequence errorText = "The radicand is out of range.";
		final Toast errorToast = Toast.makeText(context, errorText, Toast.LENGTH_SHORT);
		
		//Setup the add
		AdView adView = (AdView)this.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
		
        //All of the output for the text boxes
        editOuterNum.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				txtBefore.setText(Html.fromHtml(editOuterNum.getText().toString() + "<sup><small>"+editIndex.getText().toString()+"</small></sup>"+ "√("+editInnerNum.getText().toString()+")"));
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
        
        editInnerNum.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				txtBefore.setText(Html.fromHtml(editOuterNum.getText().toString() + "<sup><small>"+editIndex.getText().toString()+"</small></sup>"+ "√("+editInnerNum.getText().toString()+")"));
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});

        editIndex.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				txtBefore.setText(Html.fromHtml(editOuterNum.getText().toString() + "<sup><small>"+editIndex.getText().toString()+"</small></sup>"+ "√("+editInnerNum.getText().toString()+")"));
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
        
        btnCalc.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int outerNum = 0, innerNum = 0, index;
				String textIndex;
				
				try{
					outerNum = Integer.valueOf(editOuterNum.getText().toString());
				}catch(NumberFormatException e){
					outerNum = 1;
				}
				try{
					innerNum = Integer.valueOf(editInnerNum.getText().toString());
				}catch(NumberFormatException e){
					innerNum = 1;
				}
				try{
					index = Integer.valueOf(editIndex.getText().toString());
				}catch(NumberFormatException e){
					index = 2;
				}

				
				Radical rad = new Radical(innerNum,outerNum,index);
				rad.simplify();
				if (index == 2){
					textIndex = "";
				}else{
					textIndex = Integer.toString(index);
				}
				
				
				if (rad.imaginary == false) {
					if (rad.cur_out_num == 1){
						txtSimpRad.setText(Html.fromHtml(simp + "<sup><small>"+textIndex+ "</small></sup>" + inbetweenPos + String.valueOf(rad.cur_in_num) + ")"));
						txtSimpDec.setText(approx + String.valueOf(Math.pow(rad.cur_in_num,1.0/(double)rad.index) * rad.cur_out_num));
					}else if(rad.cur_in_num == 1){
						txtSimpRad.setText(simp + String.valueOf(rad.cur_out_num));
						txtSimpDec.setText(approx + String.valueOf(Math.pow(rad.cur_in_num,1.0/(double)rad.index) * rad.cur_out_num));
					}else{
						txtSimpRad.setText(Html.fromHtml(simp + String.valueOf(rad.cur_out_num)+ "<sup><small>"+textIndex+ "</small></sup>" + inbetweenPos + String.valueOf(rad.cur_in_num) + ")"));
						txtSimpDec.setText(approx + String.valueOf(Math.pow(rad.cur_in_num,1.0/(double)rad.index) * rad.cur_out_num));
					}
				}else if (rad.imaginary == true && rad.index <= 2){
					if (rad.cur_out_num == 1){
						txtSimpRad.setText(Html.fromHtml(simp + "i<sup><small>"+textIndex+ "</small></sup>" + inbetweenPos + String.valueOf(rad.cur_in_num) + ")"));
						txtSimpDec.setText(approx + String.valueOf(rad.cur_out_num) + "i + "+String.valueOf(Math.pow(rad.cur_in_num, 1.0/(double)rad.index)));
					}else if(rad.cur_in_num == 1){
						txtSimpRad.setText(simp + String.valueOf(rad.cur_out_num)+ "i");
						txtSimpDec.setText(approx + String.valueOf(rad.cur_out_num) + "i + "+String.valueOf(Math.pow(rad.cur_in_num, 1.0/(double)rad.index)));
					}else{
						txtSimpRad.setText(Html.fromHtml(simp + String.valueOf(rad.cur_out_num)+ "i<sup><small>"+textIndex+ "</small></sup>" + inbetweenPos + String.valueOf(rad.cur_in_num) + ")"));
						txtSimpDec.setText(approx + String.valueOf(rad.cur_out_num) + "i + "+String.valueOf(Math.pow(rad.cur_in_num, 1.0/(double)rad.index)) );
					}
				}else if (rad.imaginary == true && rad.index > 2 && rad.index % 2 == 0){
					txtSimpRad.setText("This cannot be simplified to a real number.");
					txtSimpDec.setText("");
				}else if (rad.imaginary == true && rad.index > 2 && rad.index % 2 != 0){
					txtSimpRad.setText(Html.fromHtml(simp + String.valueOf(rad.cur_out_num * -1)+ "<sup><small>"+textIndex+ "</small></sup>" + inbetweenPos + String.valueOf(rad.cur_in_num) + ")"));
					txtSimpDec.setText(approx + String.valueOf(Math.pow(rad.cur_in_num,1.0/(double)rad.index) * (rad.cur_out_num * -1)));
				}
				
				// Seeing if the simplified version is correct and if it isnt then saying its out of range
				if((rad.cur_out_num * rad.cur_in_num) == (rad.orig_out_num * Math.pow(rad.orig_in_num, 1.0/(double)rad.index))){
					errorToast.show();
				}
			}
		});
        btnClear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				editOuterNum.setText("");
				editInnerNum.setText("");
				editIndex.setText("");
				txtSimpRad.setText("");
				txtSimpDec.setText("");
				txtBefore.setText("");
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
