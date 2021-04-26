package com.example.matrixgrpc;

import java.lang.ref.WeakReference;
import java.util.Arrays;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.matrixgrpc.strategy.MatrixFactory;
import com.example.matrixgrpc.strategy.MatrixStrategy;

public class MainActivity extends AppCompatActivity {
    private EditText hostEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NumberPicker np = (NumberPicker) findViewById(R.id.dimPicker);

        // Configure NumberPicker
        String[] valuesArray = new String[12];
        for (int i = 0; i < 11; i++) {
            valuesArray[i] = Integer.toString(i * 100);
        }
        valuesArray[0] = "Test";
        valuesArray[11] = "Benchmark";
        np.setMaxValue(valuesArray.length - 1);
        np.setMinValue(0);
        np.setDisplayedValues(valuesArray);

        hostEdit = (EditText) findViewById(R.id.host_edit_text);

        // Configure RadioButtons
        ((RadioButton) findViewById(R.id.radioButtonAdd)).setChecked(true);
        ((RadioButton) findViewById(R.id.radioButtonMul)).setChecked(false);
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SimpleDateFormat")
    public void setExecTime(String message) {
        ((TextView) findViewById(R.id.exectime)).setText(message);
    }

    public void resetExecTime() {
        ((TextView) findViewById(R.id.exectime)).setText("Execution Time: 00:00.000");
    }

    public void disableButton() {
        ((Button) findViewById(R.id.button1)).setText("Calculating...");
        ((Button) findViewById(R.id.button1)).setClickable(false);
    }

    public void enableButton() {
        ((Button) findViewById(R.id.button1)).setText("Compute");
        ((Button) findViewById(R.id.button1)).setClickable(true);
    }

    /*
     * OPERATION METHODS
     */
    public void setAddOperation(View view) {
        ((RadioButton) findViewById(R.id.radioButtonAdd)).setChecked(true);
        ((RadioButton) findViewById(R.id.radioButtonMul)).setChecked(false);
    }

    public void setMulOperation(View view) {
        ((RadioButton) findViewById(R.id.radioButtonAdd)).setChecked(false);
        ((RadioButton) findViewById(R.id.radioButtonMul)).setChecked(true);
    }

    public void blockClickOnRadioButtons() {
        ((RadioButton) findViewById(R.id.radioButtonAdd)).setClickable(false);
        ((RadioButton) findViewById(R.id.radioButtonMul)).setClickable(false);
    }

    public void unblockClickOnRadioButtons() {
        ((RadioButton) findViewById(R.id.radioButtonAdd)).setClickable(true);
        ((RadioButton) findViewById(R.id.radioButtonMul)).setClickable(true);
    }

    /*
     * On Click event button
     */
    public void calc(View view) {
        // Configure matrix dimension
        int n = ((NumberPicker) findViewById(R.id.dimPicker)).getValue() * 100;

        disableButton();
        blockClickOnRadioButtons();
        new MatrixTask(this, hostEdit.getText().toString(), 50051).execute(String.valueOf(n));
    }

    private class MatrixTask extends AsyncTask<String, String, Void> {
        private final WeakReference<Activity> activityReference;
        Context context;

        private boolean isAdd, isRemote;

        private String host_addr;
        private int host_port;

        private MatrixTask(Activity activity, String ip, int port) {
            this.activityReference = new WeakReference<Activity>(activity);

            host_addr = ip;
            host_port = port;

            this.isAdd = ((RadioButton) activity.findViewById(R.id.radioButtonAdd)).isChecked();
            this.isRemote = ((CheckBox) activity.findViewById(R.id.forceoffloadingBox)).isChecked();

            this.context = activity.getApplicationContext();
        }

        private String processingTask(int qnt, boolean isTest) {

            long startTotalTime = System.nanoTime();

            String reply = new String();

            MatrixStrategy matrixStrategy = null;
            if (isRemote) {
                matrixStrategy = MatrixFactory.getRemoteMethod("GRPC/Proto", host_addr, host_port);
            } else {
                matrixStrategy = MatrixFactory.getLocalMethod("Normal");
            }

            int max_value = isTest ? 10 : 2000;

            int[][] in_matrix = matrixStrategy.random(qnt, qnt, max_value), res_matrix;

            if (isTest) {
                reply += Arrays.deepToString(in_matrix);
                if (this.isAdd) { reply += " + "; } else { reply += " x "; }
                reply += "\n" + Arrays.deepToString(in_matrix) + " = \n";
            }

            long startProcTime = System.nanoTime();
            if (this.isAdd) {
                res_matrix = matrixStrategy.add(in_matrix, in_matrix);
            } else {
                res_matrix = matrixStrategy.multiply(in_matrix, in_matrix);
            }
            long endProcTime = System.nanoTime();

            matrixStrategy.closeChannel();

            long endTotalTime = System.nanoTime();

            System.gc();

            if (isTest) {
                reply += Arrays.deepToString(res_matrix);
            } else {
                String method = isRemote ? "Cloudlet" : "Local";
                reply = method + "," + qnt + "," +
                        ((endProcTime - startProcTime) / 1000000) + "," +
                        ((endTotalTime - startTotalTime) / 1000000) + "\n";
            }

            return reply;
        }

        @Override
        protected Void doInBackground(String... params) {
            String to_write = "Round,Method,MatrixD,TimeCelProc,TimeCelTotal\n";
            if (Integer.parseInt(params[0]) == 0) { // Is Test mode?
                String result = processingTask(2, true);
                System.out.println(result);
                publishProgress(result);
            } else {
                if (Integer.parseInt(params[0]) == 1100) { // Is Benchmark mode?
                    for (int dim = 100; dim < 1001; dim += 300) {
                        for (int round = 1; round <= 50; round++) {
                            publishProgress("Creating random matrix (" + Integer.toString(dim)
                                    + "x" + Integer.toString(dim) + ") and calculating (" + round + "/50)");

                            String result = round + "," + processingTask(dim, false);
                            System.out.println(result);
                            to_write = to_write.concat(result);
                        }
                    }
                } else {
                    int dim = Integer.parseInt(params[0]);

                    for (int round = 1; round <= 50; round++) {
                        publishProgress("Creating random matrix (" +
                                params[0] + "x" + params[0] + ") and calculating (" + round + "/50)");

                        String result = round + "," + processingTask(dim, false);
                        System.out.println(result);
                        to_write = to_write.concat(result);
                    }

                }

                try {
                    publishProgress("End computation");
                    Thread.sleep(5000);
                    publishProgress("");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                MatrixUtils.sendToServer(to_write, host_addr, 5000);
            }
            return null;
        }

        protected void onProgressUpdate(String... progress) {
            ((TextView) findViewById(R.id.exectime)).setText(progress[0]);
        }

        protected void onPostExecute(Void no) {
            enableButton();
            unblockClickOnRadioButtons();
        }
    }

    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Close");
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialogBuilder.setMessage("Close Application?");
        alertDialogBuilder.create().show();
    }
}
