package com.google.android.gms.samples.vision.face.googlyeyes.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.samples.vision.face.googlyeyes.R;

/**
 * Created by User on 11/1/2560.
 */

public class SettingActivity extends AppCompatActivity {

    RelativeLayout relativeLayout;
    TextView nameTextView;
    Switch vibrateSwitch;
    private String chosenRingtone;
    private String titleRingtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        relativeLayout = (RelativeLayout) findViewById(R.id.relative_ringtone);
        nameTextView = (TextView) findViewById(R.id.ringtone_name_text);
        vibrateSwitch = (Switch) findViewById(R.id.switch_vibrate);

        Ringtone ringtone = RingtoneManager.getRingtone(this, Settings.System.DEFAULT_RINGTONE_URI);
        nameTextView.setText(ringtone.getTitle(this));

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this, "Get ringtone", Toast.LENGTH_SHORT).show();
                BringUpNotificationSound();
            }
        });

        vibrateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    /*
                     //Vibration
                        builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });

                     //LED
                          builder.setLights(Color.RED, 3000, 3000);

                     //Ton
                          builder.setSound(Uri.parse("uri://sadfasdfasdf.mp3"));
                     */
//                    Toast.makeText(SettingActivity.this, "Vibrate", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void BringUpNotificationSound() {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Your Ringtone");
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
        startActivityForResult(intent, 5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 5) {
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
//            Log.d("chaiwat", "Uri: " + uri);

            if (uri != null) {
                this.chosenRingtone = uri.toString();
                Ringtone ringtone = RingtoneManager.getRingtone(this, uri);
                titleRingtone = ringtone.getTitle(this);
                nameTextView.setText(titleRingtone);
//                Log.d("chaiwat", "titleRingtone: " + titleRingtone);
            } else
                this.chosenRingtone = null;
        }
    }


}
