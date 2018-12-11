package com.example.usuario.lab01golpe;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ResultActiv extends Activity {

    final String TAG = "Lab01_TAG ";
    final String ACTIV = "ResultActiv ";
    static final String COUNTER = "counter";
    static final Integer PARAM_CODE = 5;
    EditText et_count;
    TextView textView;
    Button kill_but, main_activ_but, param_activ_but, result_activ_but;

    static final String TIMES = "times";
    Integer time;

    MyReceiver receiver=new MyReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Log.d(TAG, ACTIV+"onCreate");

        IntentFilter filter = new IntentFilter(Intent.ACTION_EDIT);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(receiver, filter);

        // variables
        et_count = (EditText) findViewById(R.id.et_count_result);
        main_activ_but = (Button) findViewById(R.id.main_activ_but_result);
        param_activ_but = (Button) findViewById(R.id.param_activ_but_result);
        result_activ_but = (Button) findViewById(R.id.result_activ_but_result);

        textView = (TextView) findViewById(R.id.tv_name_main);
        time=1;

        // pick parameter
        Intent sender = getIntent();
        Integer pick=0;
        if (sender.getExtras()!=null) {
            pick = sender.getExtras().getInt(COUNTER, -1);
        }

        et_count.setText(pick.toString());
    }


    /*  Life cycle methods
     */
    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
        Log.d(TAG, ACTIV+"onDestroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, ACTIV+"onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, ACTIV+"onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, ACTIV+"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, ACTIV+"onPause");
    }


    /* Save and recover state (EditText)
     */
    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        int count=0;
        if(et_count.getText().length()!=0) {
            count = Integer.parseInt(et_count.getText().toString());
        }
        bundle.putInt(COUNTER, count);
        bundle.putInt(TIMES,time);
    }

    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        int count;
        count = bundle.getInt(COUNTER,-10);
        et_count.setText(""+count);
        time = bundle.getInt(TIMES,-10);
        time=time+1;
        textView.append(time.toString());
    }


    /*  Buttons callbacks
     */

    public void callMainActivity(View view) {
        int count=0;
        if(et_count.getText().length()!=0) {
            count = Integer.parseInt(et_count.getText().toString());
        }
        Log.d(TAG, ACTIV+"callMainActivity "+count);

        /* send parameter to Main Activ */
        Intent i = new Intent(this, MainActiv.class );
        startActivity(i);
    }

    public void callParamActivity(View view) {
        int count=0;
        if(et_count.getText().length()!=0) {
            count = Integer.parseInt(et_count.getText().toString());
        }
        Log.d(TAG, ACTIV+"callParamActivity "+count);

        /* send parameter to Param Activ */
        Intent i = new Intent(this, ParamActiv.class );
        i.putExtra(COUNTER,count);
        startActivity(i);

    }

     public void callResultActivity(View view) {
         int count=0;
         if(et_count.getText().length()!=0) {
             count = Integer.parseInt(et_count.getText().toString());
         }
        Log.d(TAG,  ACTIV+"callResultActivity "+count);

        /* send parameter to Result Activ */
        Intent i = new Intent(this, ResultActiv.class );
        i.putExtra(COUNTER,count);
        startActivityForResult(i,PARAM_CODE);
    }

    public void onKill(View view) {
        Log.d(TAG, ACTIV+"onKill");
        int count = Integer.parseInt(et_count.getText().toString());
        Intent intent= new Intent();
        intent.putExtra(COUNTER,count);
        setResult(RESULT_OK,intent);
        finish();
    }

    public void onok(View view){
        Log.d(TAG, ACTIV+"onok");
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        Integer counter=0;
        if (requestCode == PARAM_CODE
                && resultCode == RESULT_OK) {
            counter = data.getIntExtra(COUNTER, -1);
        }
        et_count.setText(counter.toString());
    }
}
