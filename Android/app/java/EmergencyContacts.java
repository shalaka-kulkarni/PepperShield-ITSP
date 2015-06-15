package com.mycompany.peppershield;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;




public class EmergencyContacts extends Activity {
    SparseBooleanArray mCheckStates ;
    ListView listView;


    ArrayList<Person> EmergencyContactsName;

    class Person {

        public String name;
        public String phoneNumber;

        Person() {

        }

        Person(String name1, String phoneNumber1) {
            name = name1;
            phoneNumber = phoneNumber1;
        }
    }

    CustomAdapter_emergency_contacts contactsAdapter = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);

        EmergencyContactsName = new ArrayList<>();

        contactsAdapter = new CustomAdapter_emergency_contacts();
        listView = (ListView) findViewById(R.id.ListView);



        Button delete=(Button)findViewById(R.id.delete);
        listView.setAdapter(contactsAdapter);

        updateList();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count=listView.getCount();
                for (int i = count-1; i >= 0; i--) {


                    View v2 = listView.getChildAt(i);
                    CheckBox tx = (CheckBox) v2.findViewById(R.id.checkBoxContacts);
                    Log.i("CheckBox" + i, tx.isChecked() + "");
                    if(i==0)
                    {
                        if (tx.isChecked())
                            Toast.makeText(EmergencyContacts.this,"You cannot delete Women's Helpline Number",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if (tx.isChecked())
                        {
                            if (tx.isChecked())
                            {
                                contactsAdapter.remove(contactsAdapter.getItem(i));
                            }
                        }


                    }}

                contactsAdapter.notifyDataSetChanged();
                for (int i=0;i<listView.getCount();i++)
                {
                    View v2 = listView.getChildAt(i);
                    CheckBox tx = (CheckBox) v2.findViewById(R.id.checkBoxContacts);
                    tx.setChecked(false);
                }
            }
        });
    }




    class CustomAdapter_emergency_contacts extends ArrayAdapter<Person> {
        CustomAdapter_emergency_contacts() {

            super(EmergencyContacts.this, android.R.layout.simple_list_item_1, EmergencyContactsName);
            mCheckStates=new SparseBooleanArray();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.custom_row_emergency_contacts, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.populateFrom(EmergencyContactsName.get(position));
            return (convertView);
        }

//        public void onItemClickListener()
//        {
//
//
//        }

        @Override
        public String toString() {
            String rt = "";

            for (Person P : EmergencyContactsName) {

                rt += P.name + "," + P.phoneNumber + ";";
            }
            return rt.length() > 0 ? rt.substring(0, rt.length() - 1) : rt;
        }
    }

    class ViewHolder {
        public TextView name = null;
        public TextView phoneNumber = null;

        ViewHolder(View custom_row_emergency_contacts) {
            name = (TextView) custom_row_emergency_contacts.findViewById(R.id.name);
            phoneNumber = (TextView) custom_row_emergency_contacts.findViewById(R.id.phoneNumber);
        }

        void populateFrom(Person r) {
            name.setText(r.name);
            phoneNumber.setText(r.phoneNumber);
        }
    }


    public static final int CONTACT_PICKER_RESULT = 1001;

    public void doLaunchContactPicker(View view)
    {

        if (EmergencyContactsName.size()<=6)
        {

            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                    ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
        }
        else
        {
            Toast.makeText(EmergencyContacts.this,"Maximum contacts are 7",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        String name = "";
        String phoneNumber = "";
        Person toBeAdded;
        toBeAdded = new Person(name, phoneNumber);


        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (CONTACT_PICKER_RESULT):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if ((Integer.parseInt(hasPhone) == 1)) {
                            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                                    null, null);
                            while (phones.moveToNext()) {
                                phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                                toBeAdded.phoneNumber = phoneNumber;
                                toBeAdded.name = name;
                                EmergencyContactsName.add(toBeAdded);
                                Person p=EmergencyContactsName.get(0);

                                contactsAdapter.notifyDataSetChanged();
                            }
                            phones.close();
                        }

                        // TODO Whatever you want to do with the selected contact name.

                    }
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("List", contactsAdapter.toString());
        editor.apply();

    }
    public  void updateList()
    {
        Person defaultNumber;
        defaultNumber=new Person("Women's Helpline Number ","09969777888");
        EmergencyContactsName.add(defaultNumber);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String List = sharedPref.getString("List", "");
        if (!"".equals(List) && "" != List) {
            contactsAdapter.clear();
            String[] str = List.split(";");
            for (String item : str) {
                String[] name_and_num = item.split(",");
                if (!name_and_num[0].equals(null))
                    contactsAdapter.add(new Person(name_and_num[0], name_and_num[1]));
            }
        }
    }

}
