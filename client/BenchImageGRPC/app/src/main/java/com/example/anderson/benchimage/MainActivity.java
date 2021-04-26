package com.example.anderson.benchimage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.anderson.benchimage.dao.model.AppConfiguration;
import com.example.anderson.benchimage.dao.model.ResultImage;
import com.example.anderson.benchimage.image.CloudletFilter;
import com.example.anderson.benchimage.image.Filter;
import com.example.anderson.benchimage.image.ImageFilter;
import com.example.anderson.benchimage.image.ImageFilterTask;
import com.example.anderson.benchimage.image.InternetFilter;
import com.example.anderson.benchimage.util.ExportData;
import com.example.anderson.benchimage.util.TaskResultAdapter;

import java.io.File;

public final class MainActivity extends Activity {
    private final String clsName = MainActivity.class.getName();

    private Filter filterLocal = new ImageFilter();

    //@Inject(ImageFilter.class)
    private CloudletFilter cloudletFilter;

    //@Inject(ImageFilter.class)
    private InternetFilter internetFilter;

    private AppConfiguration config;
    private String photoName;

    private EditText hostEdit;

    private boolean quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quit = false;

        config = new AppConfiguration();

        hostEdit = (EditText) findViewById(R.id.host_edit_text);

        configureSpinner();
        getConfigFromSpinner();

        configureButton();
        configureStatusView("Status: No Activity");

        createDirOutput();
        processImage();

        Log.i(clsName, "PicFilter Started");
    }

    public boolean isBenchmarkSeted() {
        return ((CheckBox) findViewById(R.id.benchmarkcheckBox)).isChecked();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        if (quit) {
            Process.killProcess(Process.myPid());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        switch (item.getItemId()) {
            case R.id.menu_action_export:
                alertDialogBuilder.setTitle("Export Results");
                alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialogBuilder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new ExportData(MainActivity.this, "benchimage2_data.csv").execute();
                    }
                });
                alertDialogBuilder.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialogBuilder.setMessage("Do you want export the results?");
                alertDialogBuilder.create().show();
                break;
        }

        return true;
    }

    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit");
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialogBuilder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.i(clsName, "BenchImage Particle finished");
                quit = true;
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialogBuilder.setMessage("Finish application?");
        alertDialogBuilder.create().show();
    }

    private void processImage() {
        getConfigFromSpinner();
        configureStatusViewOnTaskStart();

        config.setIpCloudlet(hostEdit.getText().toString());
        config.setBenchmarkMode(isBenchmarkSeted());

        System.gc();

        if ((config.getFilter().equals("Cartoonizer") || config.getFilter().equals("Benchmark")) && (config.getSize().equals("8MP") || config.getSize().equals("4MP"))) {
            dialogSupportFilter();
        } else {
            if (config.getLocal().equals("Local")) {
                new ImageFilterTask(getApplication(), filterLocal, config, taskResultAdapter).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                config.setRpcMethod("Local");
            } else if (config.getLocal().equals("Cloudlet")) {
                new ImageFilterTask(getApplication(), cloudletFilter, config, taskResultAdapter).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                config.setRpcMethod("Cloudlet");
            }
        }
    }

    private void configureButton() {
        Button but = (Button) findViewById(R.id.button_execute);
        but.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonStatusChange(R.id.button_execute, false, "Processing");
                processImage();
            }
        });
    }

    private void configureSpinner() {
        Spinner spinnerImage = (Spinner) findViewById(R.id.spin_image);
        Spinner spinnerFilter = (Spinner) findViewById(R.id.spin_filter);
        Spinner spinnerSize = (Spinner) findViewById(R.id.spin_size);
        Spinner spinnerLocal = (Spinner) findViewById(R.id.spin_local);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_img, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerImage.setAdapter(adapter);
        spinnerImage.setSelection(2);

        adapter = ArrayAdapter.createFromResource(this, R.array.spinner_filter, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this, R.array.spinner_local, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocal.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this, R.array.spinner_size, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSize.setAdapter(adapter);
        spinnerSize.setSelection(4);
    }

    private void configureStatusViewOnTaskStart() {
        configureStatusView("Status: Submitting Task");

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(null);
    }

    private void configureStatusView(String status) {
        TextView tv_execucao = (TextView) findViewById(R.id.text_exec);
        tv_execucao.setText("Execution Time: 0 ms");

        TextView tv_tamanho = (TextView) findViewById(R.id.text_size);
        tv_tamanho.setText("Resolution/Photo: " + config.getSize() + "/" + photoName);

        TextView tv_status = (TextView) findViewById(R.id.text_status);
        tv_status.setText(status);
    }

    private void getConfigFromSpinner() {
        Spinner spinnerImage = (Spinner) findViewById(R.id.spin_image);
        Spinner spinnerFilter = (Spinner) findViewById(R.id.spin_filter);
        Spinner spinnerSize = (Spinner) findViewById(R.id.spin_size);
        Spinner spinnerLocal = (Spinner) findViewById(R.id.spin_local);

        photoName = (String) spinnerImage.getSelectedItem();
        config.setImage(photoNameToFileName(photoName));
        config.setLocal((String) spinnerLocal.getSelectedItem());

        config.setFilter((String) spinnerFilter.getSelectedItem());
        if (config.getFilter().equals("Benchmark")) {
            config.setSize("All");
        } else {
            config.setSize((String) spinnerSize.getSelectedItem());
        }
    }

    private void dialogSupportFilter() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Limited device!");
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialogBuilder.setNegativeButton(R.string.button_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { dialog.cancel();     }
        });

        alertDialogBuilder.setMessage("Device does not support the Cartoonizer, VMSize minimal recommended is 128MB");
        alertDialogBuilder.create().show();

        buttonStatusChange(R.id.button_execute, true, "COMPUTE");
        TextView tv_status = (TextView) findViewById(R.id.text_status);
        tv_status.setText("Status: Previous request does not support Filter!");
    }

    private void buttonStatusChange(int id, boolean state, String text) {
        Button but = (Button) findViewById(id);
        but.setEnabled(state);
        but.setText(text);
    }

    private String photoNameToFileName(String name) {
        if (name.equals("FAB Show")) {
            return "img1.jpg";
        } else if (name.equals("Cidade")) {
            return "img4.jpg";
        } else if (name.equals("SkyLine")) {
            return "img5.jpg";
        }
        return null;
    }

    private void createDirOutput() {
        File storage = Environment.getExternalStorageDirectory();
        String outputDir = storage.getAbsolutePath() + File.separator + "BenchImageOutput";
        File dir = new File(outputDir);

        if (!dir.exists()) {
            dir.mkdir();
        }

        config.setOutputDirectory(outputDir);
    }

    private TaskResultAdapter<ResultImage> taskResultAdapter = new TaskResultAdapter<ResultImage>() {
        @Override
        public void completedTask(ResultImage obj) {
            if (obj != null) {
                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(obj.getBitmap());

                TextView tv_tamanho = (TextView) findViewById(R.id.text_size);
                tv_tamanho.setText("Resolution/Photo: " + config.getSize() + "/" + photoName);

                TextView tv_execucao = (TextView) findViewById(R.id.text_exec);
                if (obj.getConfig().getFilter().equals("Benchmark")) {
                    tv_execucao.setText("Benchmark Time: " + obj.getTotalTime() + " ms");
                } else {
                    tv_execucao.setText("Execution Time: " + obj.getTotalTime() + " ms");
                }
            } else {
                TextView tv_status = (TextView) findViewById(R.id.text_status);
                tv_status.setText("Status: Error during the transmission!");
            }
            buttonStatusChange(R.id.button_execute, true, "COMPUTE");
        }

        @Override
        public void taskOnGoing(int completed, String statusText) {
            TextView tv_status = (TextView) findViewById(R.id.text_status);
            tv_status.setText("Status: " + statusText);
        }

        @Override
        public void taskOnGoing(int completed, String statusText, String execText) {
            TextView tv_status = (TextView) findViewById(R.id.text_status);
            tv_status.setText("Status: " + statusText);

            TextView tv_execucao = (TextView) findViewById(R.id.text_exec);
            tv_execucao.setText("Execution Time:  " + execText);
        }
    };
}
