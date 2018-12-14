package org.ieselcaminas.pmdm.wifitdataproviderusing;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editTextBSSID = (EditText) findViewById(R.id.editTextBSSID);
        final EditText editTextSSID = (EditText) findViewById(R.id.editTextSSID);
        final EditText editTextSecurity = (EditText) findViewById(R.id.editTextSecurity);
        final EditText editTextDesc = (EditText) findViewById(R.id.editTextDesc);

        final TextView textResult = (TextView) findViewById(R.id.textResult);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("bssid",editTextBSSID.getText().toString());
                values.put("ssid",editTextSSID.getText().toString());
                values.put("security",editTextSecurity.getText().toString());
                values.put("desc",editTextDesc.getText().toString());

                String uriStr = "content://org.ieselcaminas.victor.wifidataprovider/Wifi";
                Uri wifiUri = Uri.parse(uriStr);
                ContentResolver cr = getContentResolver();
                cr.insert(wifiUri, values);

                Toast.makeText(getApplicationContext(),"Record inserted", Toast.LENGTH_SHORT);
            }
        });

        Button buttonList = (Button) findViewById(R.id.buttonList);
        buttonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] projection = new String[] {"bssid", "ssid", "security", "desc"};
                String uriStr = "content://org.ieselcaminas.victor.wifidataprovider/Wifi";
                Uri usersUri = Uri.parse(uriStr);
                ContentResolver cr = getContentResolver();
                Cursor cur = cr.query(usersUri, projection, null, null, null);
                if (cur.moveToFirst()) {
                    String bssid, ssid, security, desc;
                    int colBssid = cur.getColumnIndex("bssid");
                    int colSsid = cur.getColumnIndex("ssid");
                    int colSecurity = cur.getColumnIndex("security");
                    int colDesc = cur.getColumnIndex("desc");
                    textResult.setText("");
                    do {
                        bssid = cur.getString(colBssid);
                        ssid = cur.getString(colSsid);
                        security = cur.getString(colSecurity);
                        desc = cur.getString(colDesc);

                        textResult.append(bssid + " - " + ssid + " - " + security + " - " + desc + "\n");
                    } while (cur.moveToNext());
                }
            }
        });
    }
}