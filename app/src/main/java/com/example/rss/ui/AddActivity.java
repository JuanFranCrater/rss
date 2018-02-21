package com.example.rss.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rss.R;
import com.example.rss.model.Site;
import com.example.rss.network.ApiAdapter;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends AppCompatActivity implements View.OnClickListener, Callback<Site> {
    public static final int OK = 1;

    @BindView(R.id.nameSite)
    EditText nameSite;
    @BindView(R.id.linkSite)
    EditText linkSite;
    @BindView(R.id.emailSite)
    EditText emailSite;
    @BindView(R.id.accept)
    Button accept;
    @BindView(R.id.cancel)
    Button cancel;
    //EditText nameSite, linkSite, emailSite;
    //Button accept, cancel;

    ProgressDialog progreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ButterKnife.bind(this);
        //nameSite = (EditText) findViewById(R.id.nameSite);
        //linkSite = (EditText) findViewById(R.id.linkSite);
        //emailSite = (EditText) findViewById(R.id.emailSite);
        //accept = (Button) findViewById(R.id.accept);
        //cancel = (Button) findViewById(R.id.cancel);
        accept.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String n, l, e;
        Site s;

        if (v == accept) {
            n = nameSite.getText().toString();
            l = linkSite.getText().toString();
            e = emailSite.getText().toString();
            if (n.isEmpty() || l.isEmpty())
                Toast.makeText(this, "Please, fill the name and the link", Toast.LENGTH_SHORT).show();
            else {
                s = new Site(n, l , e);
                connection(s);
            }
        }
        if (v == cancel)
            finish();
    }

    private void connection(Site s) {
        progreso = new ProgressDialog(this);
        progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progreso.setMessage("Connecting . . .");
        progreso.setCancelable(false);
        progreso.show();

        Call<Site> call = ApiAdapter.getInstance().createSite(s);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Site> call, Response<Site> response) {
        progreso.dismiss();
        if (response.isSuccessful()) {
            Site site = response.body();
            Intent i = new Intent();
            Bundle mBundle = new Bundle();
            mBundle.putInt("id", site.getId());
            mBundle.putString("name", site.getName());
            mBundle.putString("link", site.getLink());
            mBundle.putString("email", site.getEmail());
            i.putExtras(mBundle);
            setResult(OK, i);
            finish();
            showMessage("Added site ok");
        } else {
            StringBuilder message = new StringBuilder();
            message.append("Download error: " + response.code());
            if (response.body() != null)
                message.append("\n" + response.body());
            if (response.errorBody() != null)
                try {
                    message.append("\n" + response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            showMessage(message.toString());
        }
    }

    @Override
    public void onFailure(Call<Site> call, Throwable t) {
        progreso.dismiss();
        if (t != null)
            showMessage("Failure in the communication\n" + t.getMessage());
    }

    private void showMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}

