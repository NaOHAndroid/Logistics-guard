package cc.a5156.xdkp.message.fragment;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

import cc.a5156.xdkp.R;
import cc.a5156.xdkp.common.base.BaseFragment;
import cc.a5156.xdkp.common.base.StartActivity;

/**
 * Created by Administrator on 2017/6/6.
 */

public class MessageFragment extends BaseFragment implements View.OnClickListener {


    Button btTest;
    Button btCallHistory;
    Button btStartService;
    Button btStopService;
    Button btPlayRecord;
    private MediaPlayer mediaPlayer;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_message;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btTest = view.findViewById(R.id.btTest);
        btCallHistory = view.findViewById(R.id.btCallHistory);
        btStartService = view.findViewById(R.id.btStartService);
        btStopService = view.findViewById(R.id.btStopService);
        btPlayRecord = view.findViewById(R.id.btPlayRecord);
        mediaPlayer = new MediaPlayer();
        setListener();
    }

    private void setListener() {
        btTest.setOnClickListener(this);
        btCallHistory.setOnClickListener(this);
        btStartService.setOnClickListener(this);
        btStopService.setOnClickListener(this);
        btPlayRecord.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btTest) {
            StartActivity.getInstance().startTestActivity();
        } else if (v.getId() == R.id.btCallHistory) {
            StartActivity.getInstance().startCallHistoryActivity();
        }
// else if (v.getId() == R.id.btStartService) {
//     getContext().startService(new Intent(getContext(), PhoneService.class));
// } else if (v.getId() == R.id.btStopService) {
//     getContext().stopService(new Intent(getContext(), PhoneService.class));
// }
        else if (v.getId() == R.id.btPlayRecord) {
            try {
                playRecord();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void playRecord() throws IOException {

        File dir = new File(Environment.getExternalStorageDirectory() + File.separator + "AAAAAAAAA");
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files.length > 0) {
                mediaPlayer.setDataSource(files[0].getAbsolutePath());
                mediaPlayer.prepare();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
//        getContext().stopService(new Intent(getContext(), PhoneService.class));
    }
}
