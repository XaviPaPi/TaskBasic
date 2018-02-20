package com.android.enaitzdam.taskbasic;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBarBarraProgres;
    TascaAsincrona tasca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonSenseFils).setOnClickListener(mClickListener);
        findViewById(R.id.buttonAsyncTask).setOnClickListener(mClickListener);
        findViewById(R.id.buttonCancelar).setOnClickListener(mClickListener);
    }
    View.OnClickListener mClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            progressBarBarraProgres =  (ProgressBar) findViewById(R.id.progressBarBarraProgres);
            progressBarBarraProgres.setMax(100);
            progressBarBarraProgres.setProgress(0);

            tasca = new TascaAsincrona();

            switch (v.getId()){
                case R.id.buttonSenseFils:
                    for(int i=1; i<=10; i++) {
                        longTask();
                        progressBarBarraProgres.incrementProgressBy(10);
                    }
                    Toast.makeText(MainActivity.this, "Tasca finalitzada!",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.buttonAsyncTask:
                    tasca.execute();
                    break;
                case R.id.buttonCancelar:
                    tasca.cancel(true);
                    break;
            }
        }


    };

    private void longTask()
    {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public class TascaAsincrona extends AsyncTask<Void, Integer, Boolean>
    {

        @Override
        protected void onPreExecute() {
            progressBarBarraProgres.setMax(100);
            progressBarBarraProgres.setProgress(0);
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            int i = 1;
            boolean cancel = isCancelled();
            while (i <= 10 && !cancel) {
                longTask();
                publishProgress(i * 10);
                i++;
                cancel = isCancelled();
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progreso = values[0].intValue();
            progressBarBarraProgres.setProgress(progreso);
        }


        @Override
        protected void onPostExecute(Boolean result) {
            if(result)
                Toast.makeText(MainActivity.this, "Tasca finalitzada!",
                        Toast.LENGTH_SHORT).show();
        }

        @Overrides
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(MainActivity.this, "Tasca cancelada!",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
