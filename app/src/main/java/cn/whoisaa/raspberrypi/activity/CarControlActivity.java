package cn.whoisaa.raspberrypi.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.TextUtils;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import cn.whoisaa.raspberrypi.R;
import cn.whoisaa.raspberrypi.http.ControlResponse;
import cn.whoisaa.raspberrypi.http.RspiApi;
import cn.whoisaa.raspberrypi.utils.LogUtils;
import cn.whoisaa.raspberrypi.widget.RockerView;


public class CarControlActivity extends AppCompatActivity implements OnResponseListener<ControlResponse>, SeekBar.OnSeekBarChangeListener {

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

    public static final int REQ_CONNECT_TEST = 101;
    public static final int REQ_CAR_CONTROL = 201;
    public static final int REQ_SERVO_CONTROL_HR = 301;
    public static final int REQ_SERVO_CONTROL_VT = 302;

    private TextView tvConnectStatus;
    private RockerView mRockerView;
    private TextView tvServoHrStatus, tvServoVtStatus;
    private AppCompatSeekBar mSeekBarHr, mSeekBarVt;

    private int mHrAngle, mVtAngle;
    private String mCurAction;
    private boolean mRunning;
    private boolean mConnectSuccess;


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
            case REQ_CONNECT_TEST:
                LogUtils.e("connectTest_onSucceed");
                if(response.getData() != null && response.getData().getHrAngle() >= 0) {
                    mSeekBarHr.setProgress(response.getData().getHrAngle());
                }

                if(response.getData() != null && response.getData().getVtAngle() >= 0) {
                    mSeekBarVt.setProgress(response.getData().getVtAngle());
                }
                break;

            case REQ_CAR_CONTROL:
                LogUtils.e("carControl_onSucceed");
                break;

            case REQ_SERVO_CONTROL_HR:
            case REQ_SERVO_CONTROL_VT:
                LogUtils.e("servoControl_onSucceed");
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
            case REQ_CONNECT_TEST:
                LogUtils.e("connectTest_onFailed");
                mConnectSuccess = false;
                tvConnectStatus.setText(getString(R.string.server_status_failed));
                break;

            case REQ_CAR_CONTROL:
                LogUtils.e("carControl_onFailed");
                break;

            case REQ_SERVO_CONTROL_HR:
                LogUtils.e("servo_horizontal_failed");
                tvServoHrStatus.setText(getString(R.string.servo_horizontal_failed));
                break;

            case REQ_SERVO_CONTROL_VT:
                LogUtils.e("servo_vertical_failed");
                tvServoVtStatus.setText(getString(R.string.servo_vertical_failed));
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
        RspiApi.getInstance().connectTest(REQ_CONNECT_TEST, this);
    }

    /**
     * 小车控制
     * @param action
     */
    private void carControl(String action) {
        LogUtils.e("小车方向：" + action);
        if(isConnectSuccess() && !TextUtils.isEmpty(action) && (mCurAction == null || (mCurAction != null && !mCurAction.equals(action)))) {
            mRunning = !action.equals(CAR_STOP);
            RspiApi.getInstance().carControl(REQ_CAR_CONTROL, action, this);
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
                RspiApi.getInstance().servoControl(REQ_SERVO_CONTROL_HR, orientation, angle, this);
                mHrAngle = angle;
            } else if(orientation == SERVO_VERTICAL) {
                LogUtils.e("垂直舵机转动角度：" + angle);
                RspiApi.getInstance().servoControl(REQ_SERVO_CONTROL_VT, orientation, angle, this);
                mVtAngle = angle;
            }
        }
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
            case REQ_CONNECT_TEST:
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
