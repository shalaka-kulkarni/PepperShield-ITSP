package com.mycompany.peppershield;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.TextView;


import java.io.File;
import java.util.ArrayList;


public class UsageHistory extends Activity
{

    ArrayList<String> Contacts;

    CustomAdapter_UsageHistory contactsAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage_history);
        Contacts = new ArrayList<>();
        //       int ctr = new BluetoothReceiver().getCounter();
        int ctr=5;
        if (ctr > 1)
        {
            for (int i = 0; i < ctr; i++)
            {
                Contacts.add("Contacts" + i + "") ;
            }
        }

        contactsAdapter = new CustomAdapter_UsageHistory();
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
    class CustomAdapter_UsageHistory extends ArrayAdapter<String> {

        public CustomAdapter_UsageHistory()
        {
            super(UsageHistory.this, android.R.layout.simple_list_item_1,Contacts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater abhinInflater=LayoutInflater.from(getContext());
            View customView=abhinInflater.inflate(R.layout.custom_row_usagehistory, parent, false);

//         String  singleContactItem=getItem(position);
            TextView textView=(TextView)customView.findViewById(R.id.textView);
//         EditText editText=(EditText)customView.findViewById(R.id.editText);
            ImageView Image=(ImageView) customView.findViewById(R.id.Image);
            File myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "PepperShield");
            myDir.mkdirs();
            File file = new File(myDir,"image"+position+".jpeg");
            Uri uri = Uri.fromFile(file);
            Image.setImageURI(uri);
//        Text.setText(singleContactItem);

//         if (position==0) {
//
//             Image.setImageURI(Uri.parse(new File("/sdcard/bluetooth/image"+position+".jpeg").toString()));
//
////             Image.setImageResource(R.drawable.kohli);
//         }
//         else if(position==1)
//         {
////             Image.setImageResource(R.drawable.yuvi);
//             Image.setImageURI(Uri.parse(new File("/sdcard/bluetooth/image"+position+".jpeg").toString()));
//         }
//         else if (position==2)
//         {
////             Image.setImageResource(R.drawable.sreesanth);
//             Image.setImageURI(Uri.parse(new File("/sdcard/bluetooth/image"+position+".jpeg").toString()));
//
//         }
            return customView;

        }
    }
}
