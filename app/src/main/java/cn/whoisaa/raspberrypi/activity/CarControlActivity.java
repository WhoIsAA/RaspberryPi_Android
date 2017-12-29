package cn.whoisaa.raspberrypi.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import cn.whoisaa.raspberrypi.R;
import cn.whoisaa.raspberrypi.base.BaseActivity;
import cn.whoisaa.raspberrypi.http.ControlResponse;
import cn.whoisaa.raspberrypi.http.RspiApi;
import cn.whoisaa.raspberrypi.utils.LogUtils;
import cn.whoisaa.raspberrypi.widget.RockerView;


public class CarControlActivity extends BaseActivity implements OnResponseListener<ControlResponse>, SeekBar.OnSeekBarChangeListener {

    //小车控制
    public static final String CAR_FORWARD = "forward";
    public static final String CAR_BACKWARD = "backward";
    public static final String CAR_FRONT_TURNLEFT = "front_turnleft";
    public static final String CAR_FRONT_TURNRIGHT = "front_turnright";
    public static final String CAR_BACK_TURNLEFT = "back_turnleft";
    public static final String CAR_BACK_TURNRIGHT = "back_turnright";
    public static final String CAR_STOP = "stop";
    //舵机控制
    public static final int SERVO_HORIZONTAL = 0;
    public static final int SERVO_VERTICAL = 1;
    //默认连拍张数
    public static final int DEFAULT_CONTINUOUS_SHOT = 5;
    //默认连拍间隔时长：6秒
    public static final int DEFAULT_CONTINUOUS_SHOT_DELAY = 6;
    //默认录制时长：60秒
    public static final int DEFAULT_RECORD_SECONDS = 60;

    //Handler消息
    public static final int MSG_RECORD = 1000;


    private TextView tvConnectStatus;
    private RockerView mRockerView;
    private TextView tvServoHrStatus, tvServoVtStatus;
    private AppCompatSeekBar mSeekBarHr, mSeekBarVt;

    private Button btnTakePic, btnContinuousShot;
    private Button btnRecordVideo;

    private String mCurAction;
    private boolean mConnectSuccess;

    private int mRecordTime;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_RECORD:
                    mRecordTime ++;
                    btnRecordVideo.setText(String.format(getString(R.string.record_time), mRecordTime));
                    if(mRecordTime == DEFAULT_RECORD_SECONDS) {
                        btnRecordVideo.setText(getString(R.string.record_video));
                        btnRecordVideo.setEnabled(true);
                        removeMessages(MSG_RECORD);
                    } else {
                        sendEmptyMessageDelayed(MSG_RECORD, 1000);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_control);
        //状态显示
        tvConnectStatus = findViewById(R.id.tv_car_control_connect_status);
        //小车控制端
        mRockerView = findViewById(R.id.rv_car_control);
        mRockerView.setCallBackMode(RockerView.CallBackMode.CALL_BACK_MODE_STATE_CHANGE);
        mRockerView.setOnShakeListener(RockerView.DirectionMode.DIRECTION_8, new RockerView.OnShakeListener() {
            @Override
            public void onStart() {}

            @Override
            public void direction(RockerView.Direction direction) {
                carControl(getAction(direction));
            }

            @Override
            public void onFinish() {
                carControl(CAR_STOP);
            }
        });
        //舵机控制端
        tvServoHrStatus = findViewById(R.id.tv_servo_horizontal);
        tvServoVtStatus = findViewById(R.id.tv_servo_vertical);
        mSeekBarHr = findViewById(R.id.sb_servo_horizontal);
        mSeekBarVt = findViewById(R.id.sb_servo_vertical);
        mSeekBarHr.setOnSeekBarChangeListener(this);
        mSeekBarVt.setOnSeekBarChangeListener(this);

        //拍照录像相关
        btnTakePic = findViewById(R.id.btn_take_picture);
        btnContinuousShot = findViewById(R.id.btn_continuous_shot);
        btnRecordVideo = findViewById(R.id.btn_start_record);
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectTest();
    }

    /**
     * 获取当前操作
     * @param direction
     * @return
     */
    private String getAction(RockerView.Direction direction) {
        switch (direction) {
            case DIRECTION_UP:
                //小车前进
                return CAR_FORWARD;
            case DIRECTION_DOWN:
                //小车后退
                return CAR_BACKWARD;
            case DIRECTION_UP_LEFT:
                //小车左前方转弯
                return CAR_FRONT_TURNLEFT;
            case DIRECTION_UP_RIGHT:
                //小车右前方转弯
                return CAR_FRONT_TURNRIGHT;
            case DIRECTION_DOWN_LEFT:
                //小车左后方转弯
                return CAR_BACK_TURNLEFT;
            case DIRECTION_DOWN_RIGHT:
                //小车右后方转弯
                return CAR_BACK_TURNRIGHT;
            default:
                return "";
        }
    }

    /**
     * 服务器返回成功
     * @param response
     */
    private void controlSuccess(int what, @NonNull ControlResponse response) {
        if(response == null) {
            controlFailed(what, null);
            return;
        }

        mConnectSuccess = true;
        tvConnectStatus.setText(getString(R.string.server_status_success));

        if(response.getData() != null && response.getData().getHrAngle() >= 0) {
            tvServoHrStatus.setText(String.format(getString(R.string.servo_horizontal), response.getData().getHrAngle()));
        }

        if(response.getData() != null && response.getData().getVtAngle() >= 0) {
            tvServoVtStatus.setText(String.format(getString(R.string.servo_vertical), response.getData().getVtAngle()));
        }

        switch (what) {
            case RspiApi.REQ_CONNECT_TEST:
                LogUtils.e("connectTest_onSucceed");
                if(response.getData() != null && response.getData().getHrAngle() >= 0) {
                    mSeekBarHr.setProgress(response.getData().getHrAngle());
                }

                if(response.getData() != null && response.getData().getVtAngle() >= 0) {
                    mSeekBarVt.setProgress(response.getData().getVtAngle());
                }
                break;

            case RspiApi.REQ_CAR_CONTROL:
                LogUtils.e("carControl_onSucceed");
                break;

            case RspiApi.REQ_SERVO_CONTROL_HR:
            case RspiApi.REQ_SERVO_CONTROL_VT:
                LogUtils.e("servoControl_onSucceed");
                break;

            case RspiApi.REQ_TAKE_PICTURE:
                //拍照成功
                // TODO: 2017/12/25 获得拍照文件链接地址
                btnTakePic.setEnabled(true);
                break;

            case RspiApi.REQ_CONTINUOUS_SHOT:
                //连拍成功
                btnContinuousShot.setEnabled(true);
                // TODO: 2017/12/25 获得拍照文件链接地址
                break;

            case RspiApi.REQ_RECORD_VIDEO:
                //开始录制成功
                mHandler.sendEmptyMessage(MSG_RECORD);
                break;
        }
    }

    /**
     * 服务器返回失败
     * @param what
     */
    private void controlFailed(int what, Response<ControlResponse> response) {
        mConnectSuccess = false;
        switch (what) {
            case RspiApi.REQ_CONNECT_TEST:
                LogUtils.e("connectTest_onFailed");
                mConnectSuccess = false;
                tvConnectStatus.setText(getString(R.string.server_status_failed));
                break;

            case RspiApi.REQ_CAR_CONTROL:
                LogUtils.e("carControl_onFailed");
                break;

            case RspiApi.REQ_SERVO_CONTROL_HR:
                LogUtils.e("servo_horizontal_failed");
                tvServoHrStatus.setText(getString(R.string.servo_horizontal_failed));
                break;

            case RspiApi.REQ_SERVO_CONTROL_VT:
                LogUtils.e("servo_vertical_failed");
                tvServoVtStatus.setText(getString(R.string.servo_vertical_failed));
                break;

            case RspiApi.REQ_TAKE_PICTURE:
                //拍照失败
                toast(R.string.take_picture_failed);
                btnTakePic.setEnabled(true);
                break;

            case RspiApi.REQ_CONTINUOUS_SHOT:
                //连拍失败
                toast(R.string.continuous_shot_failed);
                btnContinuousShot.setEnabled(true);
                break;

            case RspiApi.REQ_RECORD_VIDEO:
                //开始录制失败
                toast(R.string.start_record_failed);
                btnRecordVideo.setEnabled(true);
                break;
        }
    }

    /**
     * 是否连接服务器成功
     * @return
     */
    private boolean isConnectSuccess() {
        return mConnectSuccess;
    }

    /**
     * 测试连接服务器
     */
    private void connectTest() {
        RspiApi.getInstance().connectTest(RspiApi.REQ_CONNECT_TEST, this);
    }

    /**
     * 小车控制
     * @param action
     */
    private void carControl(String action) {
        LogUtils.e("小车方向：" + action);
        if(isConnectSuccess() && !TextUtils.isEmpty(action) && (mCurAction == null || (mCurAction != null && !mCurAction.equals(action)))) {
            RspiApi.getInstance().carControl(RspiApi.REQ_CAR_CONTROL, action, this);
            mCurAction = action;
        }
    }

    /**
     * 舵机控制
     * @param orientation
     * @param angle
     */
    private void servoControl(int orientation, int angle) {
        if(isConnectSuccess()) {
            if(orientation == SERVO_HORIZONTAL) {
                LogUtils.e("水平舵机转动角度：" + angle);
                RspiApi.getInstance().servoControl(RspiApi.REQ_SERVO_CONTROL_HR, orientation, angle, this);
            } else if(orientation == SERVO_VERTICAL) {
                LogUtils.e("垂直舵机转动角度：" + angle);
                RspiApi.getInstance().servoControl(RspiApi.REQ_SERVO_CONTROL_VT, orientation, angle, this);
            }
        }
    }

    /**
     * 拍一张照
     * @param view
     */
    public void takePicture(View view) {
        view.setEnabled(false);
        RspiApi.getInstance().takePicture(RspiApi.REQ_TAKE_PICTURE, this);
    }

    /**
     * 连拍五张
     * @param view
     */
    public void continuousShot(View view) {
        view.setEnabled(false);
        RspiApi.getInstance().continuousShot(RspiApi.REQ_CONTINUOUS_SHOT, DEFAULT_CONTINUOUS_SHOT, DEFAULT_CONTINUOUS_SHOT_DELAY, this);
    }

    /**
     * 开始录制
     * @param view
     */
    public void startRecord(View view) {
        view.setEnabled(false);
        RspiApi.getInstance().recordVideo(RspiApi.REQ_RECORD_VIDEO, DEFAULT_RECORD_SECONDS, this);
    }

    /**
     * 停止录制
     * @param view
     */
    public void preview(View view) {
        view.setEnabled(false);
        pushActivity(PreviewActivity.class);
        view.setEnabled(true);
    }


    /////////////////////////////////【SeekBar Listener】/////////////////////////////////////
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //四舍五入取十的整数倍
        int progress = seekBar.getProgress();
        progress = (int) (10 * Math.rint(progress / 10.0));
        if (seekBar.equals(mSeekBarHr)) {
            servoControl(SERVO_HORIZONTAL, progress);
        } else if (seekBar.equals(mSeekBarVt)) {
            servoControl(SERVO_VERTICAL, progress);
        }
    }

    /////////////////////////////////【Server Response】/////////////////////////////////////
    @Override
    public void onStart(int what) {
        switch (what) {
            case RspiApi.REQ_CONNECT_TEST:
                tvConnectStatus.setText(getString(R.string.server_connecting));
                break;
        }
    }

    @Override
    public void onSucceed(int what, Response<ControlResponse> response) {
        controlSuccess(what, response.get());
    }

    @Override
    public void onFailed(int what, Response<ControlResponse> response) {
        controlFailed(what, response);
    }

    @Override
    public void onFinish(int what) {

    }
}
