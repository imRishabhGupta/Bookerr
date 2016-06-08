package com.retrofit.user.bookerr;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity implements Callback<StackOverflowQuestions> {

    ListView listView;
    TextView textView;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView=(TextView)findViewById(R.id.text);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                textView.setVisibility(View.INVISIBLE);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.stackexchange.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                // prepare call in Retrofit 2.0
                StackOverflowAPI stackOverflowAPI = retrofit.create(StackOverflowAPI.class);

                Call<StackOverflowQuestions> call = stackOverflowAPI.loadQuestions("android");
                //asynchronous call
                call.enqueue(MainActivity.this);

            }
        });
        listView=(ListView)findViewById(R.id.list);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading questions...");
        progressDialog.setCanceledOnTouchOutside(false);


        ArrayAdapter<Question> arrayAdapter =
                new ArrayAdapter<Question>(this,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        new ArrayList<Question>());
        listView.setAdapter(arrayAdapter);
        progressDialog.dismiss();
listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("no");
    }
});

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }
    @Override
    public void onResponse(Response<StackOverflowQuestions> response, Retrofit retrofit) {
        //setProgressBarIndeterminateVisibility(false);
        ArrayAdapter<Question> adapter = (ArrayAdapter<Question>) listView.getAdapter();
        adapter.clear();
        adapter.addAll(response.body().items);
        progressDialog.dismiss();
    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }
}
