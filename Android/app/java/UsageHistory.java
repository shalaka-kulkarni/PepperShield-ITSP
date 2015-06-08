package com.mycompany.peppershield;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.TextView;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;




public class UsageHistory extends Activity
{





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage_history);

        

        String[] Contacts = {"Contact1", "Contact2", "Contact3"};
        ListAdapter contactsAdapter = new CustomAdapter_UsageHistory(this, Contacts);
        ListView ListView = (ListView) findViewById(R.id.ListView);
        ListView.setAdapter(contactsAdapter);

        ListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String Contact = String.valueOf(parent.getItemAtPosition(position));
//                        Toast.makeText(MainActivity.this,Contact,Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(UsageHistory.this, ContactInfo1_UsageHistory.class);
                        i.putExtra("data", position + 1);
                        startActivity(i);

                    }
                }
        );


    }





    }

