package cc.a5156.xdkp.message.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ewcdma.readersdk.BluetoothReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import cc.a5156.xdkp.R;

/**
 * Created by Administrator on 2017/6/21.
 */

public class TestActivity3 extends Activity {


    private final static String BLUE_ADDRESSKEY = "com.ewcdma.macaddr";

    public static final int READER_STATE_UNKNOWN = 0;
    public static final int READER_STATE_DISCONNECT = 1;
    public static final int READER_STATE_CONNECTING = 2;
    public static final int READER_STATE_CONNECTED = 3;


    public static final int CARD_COLD_RESET = 0;
    public static final int CARD_POWER_DOWN = 1;
    public static final int CARD_WARM_RESET = 2;

    public static final int CARD_UNKNOWN = 0;
    public static final int CARD_ABSENT = 1;
    public static final int CARD_PRESENT = 2;
    public static final int CARD_SWALLOWED = 3;
    public static final int CARD_POWERED = 4;
    public static final int CARD_NEGOTIABLE = 5;
    public static final int CARD_SPECIFIC = 6;

    private static final int CARD_MESSAGE = 1;
    private static final int READER_MESSAGE = 2;
    private static final int PROCESS_MESSAGE = 3;
    private static final int PROCESS_APDU = 4;//处理命令

    public static final int PROCESS_ID_START = 0;
    public static final int PROCESS_ID_READ = 1;
    public static final int PROCESS_ID_NETWORK = 2;
    public static final int PROCESS_ID_AUTH = 3;
    public static final int PROCESS_ID_INFO = 4;
    public static final int PROCESS_ID_INFO2 = 5;
    public static final int PROCESS_ID_COMPLETE = 6;

    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final String TAG = "Reader";

    private BluetoothReader mBluetoothReader;

    private String Blueaddress = null;
    private Handler uiHandler;
    private ByteBuffer photoBuf;

    private TextView infoText;
    private ImageView imgView;
    private AnimationDrawable _animation;

    private static String Reader_State[] = {"wait reader connect", "reader is disconnect", "reader connecting", "reader connected"};
    private static String process_State[] = {"Step1:Start check id...", "Step2:Start read id...", "Step3:init network...", "Step4:auth id...", "Step5:read info...", "Step6:read info2...", "Step7:read success"};

    String title = "Read ID Card";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(title);
        infoText = (TextView) findViewById(R.id.infoView);
        imgView = (ImageView) findViewById(R.id.cardbg);
        imgView.setBackgroundResource(R.drawable.myanim);
        _animation = (AnimationDrawable) imgView.getBackground();
        _animation.setOneShot(false);
        uiHandler = new MyHandler(this);

        mBluetoothReader = new BluetoothReader();
        mBluetoothReader.setOnReaderStateListener(new BluetoothReader.OnReaderStateListener() {
            @Override
            public void onReaderStateChange(String readerName, int readerState) {
                if (readerName.isEmpty() == false) {
                    uiHandler.obtainMessage(READER_MESSAGE, readerState, -1, readerName).sendToTarget();
                }
            }
        });

        View scan = findViewById(R.id.btScan);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoText.setText("Select bluetooth reader for connect...");
                Intent serverIntent = new Intent(TestActivity3.this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
        if (BluetoothAdapter.getDefaultAdapter() == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            return;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        if (Blueaddress != null && mBluetoothReader.isOpened() == false) {
            new OpenTask().execute();
        }
    }

    @Override
    public void onPause() {
        Log.e(TAG, "onPause");
        super.onPause();
        if (mBluetoothReader.isOpened()) {
            mBluetoothReader.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_bluetooth) {
            setTitle(title);
            infoText.setText("Select bluetooth reader for connect...");
            Intent serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayIdInfo(JSONStringer strCardInfo) {
        try {
            JSONObject idInfo = new JSONObject(strCardInfo.toString());
            if (idInfo.getBoolean("resultFlag") == false) {
                return;
            }
            imgView.setBackgroundResource(0);
            float width = imgView.getWidth();
            float height = imgView.getHeight();
            JSONObject idContext = idInfo.getJSONObject("resultContent");
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.id);

            Bitmap alterBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), bitmap.getConfig());

            Canvas canvas = new Canvas(alterBitmap);

            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setFakeBoldText(true);
            canvas.drawBitmap(bitmap, 0, 0, paint);

            float lraw = 160.00f;
            float traw = 82.00f;
            float loff = (width / 718.00f) * lraw;
            float toff = (height / 452.00f) * traw;
            int fontSize = (int) (32.00f * (width / 718.00f));
            paint.setTextSize(fontSize);

            canvas.drawText(idContext.getString("partyName"), loff, toff, paint);          //imgView 1376*904 718*452 //150 //photobg 186*230  372*460
            toff += fontSize * 1.5f;
            canvas.drawText(idContext.getString("gender"), loff, toff, paint);
            float nationloff = loff + fontSize * 4.60f;
            canvas.drawText(idContext.getString("nation"), nationloff + fontSize * 2.00f, toff, paint);
            String birth = new String(idContext.getString("bornDay"));
            toff += fontSize * 2.0f;
            canvas.drawText(birth.substring(0, 4), loff, toff, paint);
            canvas.drawText(birth.substring(4, 6), nationloff, toff, paint);
            canvas.drawText(birth.substring(6, 8), nationloff + fontSize * 1.5f, toff, paint);
            TextPaint textPaint = new TextPaint();
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(fontSize);
            String imageStr = new String(idContext.getString("identityPic"));
            byte[] base64String = Base64.decode(imageStr.getBytes(), imageStr.getBytes().length);
            Bitmap bmp = BitmapFactory.decodeByteArray(base64String, 0, base64String.length);
            Bitmap photobg = BitmapFactory.decodeResource(getResources(), R.drawable.photobg);
            //new Rect(300+320+220+160,140,372+300+320+220+160,460+140),                //620+380 1000/2 500,70,686,600
            canvas.drawBitmap(
                    bmp,                                                                    //126*2+70 460+204
                    null,
                    new Rect((int) (540.00f * (width / 718.00f)),
                            (int) (70.00f * (width / 718.00f)),
                            (int) (720.00f * (width / 718.00f)),
                            (int) (302.00f * (width / 718.00f))), paint);
            textPaint.setFakeBoldText(true);
            StaticLayout layout = new StaticLayout(idContext.getString("certAddress"), textPaint, (int) (fontSize * 11.00f), Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
            canvas.save();
            canvas.translate(loff, (240.00f * width / 718.00f));
            layout.draw(canvas);
            canvas.restore();
            canvas.drawText(idContext.getString("certNumber"), 310.00f * width / 718.00f, 410.00f * width / 718.00f, paint);//166+640
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            Log.e(TAG, "view weight: " + imgView.getWidth() + "height: " + imgView.getHeight() + "bmp w: " + bmp.getWidth() + " bmp h:" + bmp.getHeight() + "photobg w: " + photobg.getWidth() + "photobg h: " + photobg.getHeight());
            imgView.setImageBitmap(alterBitmap);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class readidTask extends AsyncTask<String, Void, JSONStringer> {
        @Override
        protected JSONStringer doInBackground(String... params) {
            JSONStringer map = null;

            mBluetoothReader.setOnCardProcessListener(new BluetoothReader.OnCardProcessListener() {
                @Override
                public void onProcessStateChange(int slotNum, int processState) {

                    uiHandler.obtainMessage(PROCESS_MESSAGE, slotNum, processState, mBluetoothReader.getReaderName()).sendToTarget();
                }
            });
            map = mBluetoothReader.readCert();

            return map;
        }

        @Override
        protected void onPostExecute(JSONStringer strCardInfo) {
            if (strCardInfo != null) {
                displayIdInfo(strCardInfo);
            }
            super.onPostExecute(strCardInfo);
        }
    }

    private class OpenTask extends AsyncTask<String, Void, Map<String, String>> {
        @Override
        protected Map<String, String> doInBackground(String... params) {
            Map<String, String> info = new HashMap<String, String>();
            boolean ret = false;
            if (Blueaddress == null) return null;
            ret = mBluetoothReader.open(Blueaddress);
            if (ret == true) {
                info = mBluetoothReader.getReaderInfo();
            } else
                info.put("name", "Bluetooth Reader");
            mBluetoothReader.setOnSlotStateListener(new BluetoothReader.OnSlotStateListener() {
                @Override
                public void onCardStateChange(int slotNum, int cardState) {
                    uiHandler.obtainMessage(CARD_MESSAGE, slotNum, cardState, mBluetoothReader.getReaderName()).sendToTarget();
                }
            });
            info.put("open", Boolean.toString(ret));
            return info;
        }

        @Override
        protected void onPostExecute(Map<String, String> info) {
            if (info == null) return;
            if (Boolean.valueOf(info.get("open")) == false) {
                uiHandler.obtainMessage(READER_MESSAGE, READER_STATE_UNKNOWN, -1, info.get("name")).sendToTarget();
                return;
            }
            infoText.setText("");
            infoText.append("Reader name: " + info.get("name") + "\r\n");
            infoText.append("Model: " + info.get("model") + "\r\n");
            infoText.append("Serial number: " + info.get("serial number") + "\r\n");
            infoText.append("Battery level: " + info.get("battery") + "\r\n");
            infoText.append("Hardware version: " + info.get("version") + "\r\n");
            infoText.append("Fireware version: " + info.get("fw") + "\r\n");
            infoText.append("SDK version: " + info.get("sdk") + "\r\n");
        }
    }

    class MyHandler extends Handler {
        private Activity activity;

        MyHandler(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CARD_MESSAGE:
                    int slotNum = msg.arg1;
                    int state = msg.arg2;
                    imgView.setImageBitmap(null);
                    if (_animation.isRunning()) {
                        _animation.stop();
                    }
                    Log.e(TAG, "reader: " + msg.obj + "slots: " + slotNum + "state: " + state);
                    Log.e(TAG, "handleMessage: slotNum================" + slotNum);
                    switch (slotNum) {
                        case 2:
                            if (state >= CARD_PRESENT) {
                                imgView.setBackgroundResource(R.drawable.id);
                                new readidTask().execute();
                                return;
                            }
                            break;
                    }
                    imgView.setBackgroundResource(R.drawable.myanim);
                    _animation = (AnimationDrawable) imgView.getBackground();
                    _animation.setOneShot(false);
                    _animation.start();
                    break;
                case READER_MESSAGE:
                    infoText.setText(msg.obj + ": " + Reader_State[msg.arg1]);
                    break;
                case PROCESS_MESSAGE:
                    infoText.setText(msg.obj + ": " + process_State[msg.arg2]);

                case PROCESS_APDU:
                    infoText.setText(String.valueOf(msg.obj));
                    break;
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    Blueaddress = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    if (!Blueaddress.matches("([0-9a-fA-F][0-9a-fA-F]:){5}([0-9a-fA-F][0-9a-fA-F])")) {
                        infoText.setText("address:" + Blueaddress + " is wrong, length = " + Blueaddress.length());
                        return;
                    }

                    Log.e(TAG, "onActivityResult: ");
                    ShareReferenceSaver.saveData(this, BLUE_ADDRESSKEY, Blueaddress);
                    // new OpenTask().execute();
                }
                break;
        }
    }
}
