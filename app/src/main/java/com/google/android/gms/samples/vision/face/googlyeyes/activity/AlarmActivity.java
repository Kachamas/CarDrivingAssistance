package com.google.android.gms.samples.vision.face.googlyeyes.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.samples.vision.face.googlyeyes.R;

public class AlarmActivity extends AppCompatActivity {

    private Button stop_button;
    private Button ringtone_button;

    private boolean isAlarm = false;

    private Uri notification;
    private Ringtone r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        r = RingtoneManager.getRingtone(this, notification);

        isAlarm = getIntent().getBooleanExtra("key_alarm", false);

        if (isAlarm)
            r.play();

        stop_button = (Button) findViewById(R.id.stop_button);
        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r.stop();
                startActivity(new Intent(AlarmActivity.this, SuggestionActivity.class));
                finish();
            }
        });

        ringtone_button = (Button) findViewById(R.id.ringtone_button);
        ringtone_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r.stop();
                BringUpNotificationSound();
                finish();
            }
        });

    }

    private void BringUpNotificationSound() {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Your Ringtone");
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
        startActivityForResult(intent, 5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 5) {
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            if (uri != null) {
                notification = uri;
            } else
                notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        }
    }
}
