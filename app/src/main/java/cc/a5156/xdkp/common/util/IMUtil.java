//package cc.a5156.xdkp.common.util;
//
//import android.app.PendingIntent;
//import android.content.Intent;
//import android.util.Log;
//
//import com.yuntongxun.ecsdk.ECDevice;
//import com.yuntongxun.ecsdk.ECError;
//import com.yuntongxun.ecsdk.ECInitParams;
//import com.yuntongxun.ecsdk.ECMessage;
//import com.yuntongxun.ecsdk.ECVoIPCallManager;
//import com.yuntongxun.ecsdk.OnChatReceiveListener;
//import com.yuntongxun.ecsdk.OnMeetingListener;
//import com.yuntongxun.ecsdk.SdkErrorCode;
//import com.yuntongxun.ecsdk.VideoRatio;
//import com.yuntongxun.ecsdk.VoipMediaChangedInfo;
//import com.yuntongxun.ecsdk.im.ECMessageNotify;
//import com.yuntongxun.ecsdk.im.ECTextMessageBody;
//import com.yuntongxun.ecsdk.im.group.ECGroupNoticeMessage;
//import com.yuntongxun.ecsdk.meeting.intercom.ECInterPhoneMeetingMsg;
//import com.yuntongxun.ecsdk.meeting.video.ECVideoMeetingMsg;
//import com.yuntongxun.ecsdk.meeting.voice.ECVoiceMeetingMsg;
//
//import java.util.List;
//
//import cc.a5156.xdkp.common.IM.VoIPCallActivity;
//import cc.a5156.xdkp.common.base.App;
//import cc.a5156.xdkp.common.base.Constant;
//import cc.a5156.xdkp.common.base.StartActivity;
//import cc.a5156.xdkp.common.sqlite.SQLiteKey;
//
///**
// * Created by Administrator on 2017/6/9.
// */
//
//public class IMUtil {
//
//    public static void initECSDK(final String userID) {
//        //判断SDK是否已经初始化
//        if (!ECDevice.isInitialized()) {
//
//            ECDevice.initial(App.getContext(), new ECDevice.InitListener() {
//                @Override
//                public void onInitialized() {
//                    // SDK已经初始化成功
//                    Log.e("ZZZ", "初始化SDK成功");
//                    SQLiteUtil.save(SQLiteKey.ECSDK_INIT, true);
//                    IMUtil.ECLogin(userID);
//                }
//
//                @Override
//                public void onError(Exception exception) {
//                    //在初始化错误的方法中打印错误原因
//                    Log.e("ZZZ", "初始化SDK失败" + exception.getMessage());
//                    SQLiteUtil.save(SQLiteKey.ECSDK_INIT, false);
//                }
//            });
//        }
//    }
//
//    public static void ECLogin(String userID) {
//        //设置登录参数，可分为自定义方式和通讯账号验密方式，详情点此查看>>
//        ECInitParams params = createParams(userID);
//        setCallBackListener();
//        //验证参数是否正确，登陆SDK，详情点此查看>>
//        if (params.validate()) {
//            // 登录函数
//            setIPs();
//            ECDevice.login(params);
//        }
//    }
//
//    /**
//     * 设置登陆回调监听
//     */
//    private static void setLoginCallback() {
//        //设置登录回调监听
//        ECDevice.setOnDeviceConnectListener(new ECDevice.OnECDeviceConnectListener() {
//            public void onConnect() {
//                //兼容旧版本的方法，不必处理
//            }
//
//            @Override
//            public void onDisconnect(ECError error) {
//                //兼容旧版本的方法，不必处理
//            }
//
//            @Override
//            public void onConnectState(ECDevice.ECConnectState state, ECError error) {
//                if (state == ECDevice.ECConnectState.CONNECT_FAILED) {
//                    if (error.errorCode == SdkErrorCode.SDK_KICKED_OFF) {
//                        Log.e("COP_SDK", "==帐号异地登陆");
//                    } else {
//                        Log.e("COP_SDK", "==其他登录失败,错误码：" + error.errorCode);
//                    }
//                } else if (state == ECDevice.ECConnectState.CONNECT_SUCCESS) {
//                    Log.e("COP_SDK", "==登陆成功");
//                    if (!App.hasHomeActivity()) {
//                        StartActivity.getInstance().startHomeActivity();
//                        App.finishEntranceActivity();
//                    }
//                }
//            }
//        });
//    }
//
//    private InitCOPSDKListener copsdkListener;
//
//    interface InitCOPSDKListener {
//        boolean isLoginOK();
//    }
//
//    /**
//     * IM接收消息监听
//     */
//    private static void setIMRecieveCallback() {
//        ECDevice.setOnChatReceiveListener(new OnChatReceiveListener() {
//            @Override
//            public void OnReceivedMessage(ECMessage msg) {
//                Log.i("", "==收到新消息");
//
//                if (msg == null) {
//                    return;
//                }
//                // 接收到的IM消息，根据IM消息类型做不同的处理(IM消息类型：ECMessage.Type)
//                ECMessage.Type type = msg.getType();
//                if (type == ECMessage.Type.TXT) {
//                    // 在这里处理文本消息
//                    ECTextMessageBody textMessageBody = (ECTextMessageBody) msg.getBody();
//                    Log.e("ZZZ", textMessageBody.getMessage());
//                } else {
//
////                    String thumbnailFileUrl = null;
////                    String remoteUrl = null;
////                    if (type == ECMessage.Type.FILE) {
////                        // 在这里处理附件消息
////                        ECFileMessageBody fileMsgBody = (ECFileMessageBody) msg.getBody();
////                        // 获得下载地址
////                        remoteUrl = fileMsgBody.getRemoteUrl();
////                    } else if (type == ECMessage.Type.IMAGE) {
////                        // 在这里处理图片消息
////                        ECImageMessageBody imageMsgBody = (ECImageMessageBody) msg.getBody();
////                        // 获得缩略图地址
////                        thumbnailFileUrl = imageMsgBody.getThumbnailFileUrl();
////                        // 获得原图地址
////                        remoteUrl = imageMsgBody.getRemoteUrl();
////                    } else if (type == ECMessage.Type.VOICE) {
////                        // 在这里处理语音消息
////                        ECVoiceMessageBody voiceMsgBody = (ECVoiceMessageBody) msg.getBody();
////                        // 获得下载地址
////                        remoteUrl = voiceMsgBody.getRemoteUrl();
////                    } else if (type == ECMessage.Type.VIDEO) {
////                        // 在这里处理语音消息
////                        ECVideoMessageBody videoMessageBody = (ECVideoMessageBody) msg.getBody();
////                        // 获得下载地址
////                        remoteUrl = videoMessageBody.getRemoteUrl();
////                    } else if (type == ECMessage.Type.LOCATION) {
////                        // 在这里处理地理位置消息
////                        ECLocationMessageBody locationMessageBody = (ECLocationMessageBody) msg.getBody();
////                        // 获得下载地址
////                        locationMessageBody.getLatitude(); // 纬度信息
////                        locationMessageBody.getLongitude();// 经度信息
////                    } else {
////                        Log.e("ECSDK_Demo" , "Can't handle msgType=" + type.name()
////                                + " , then ignore.");
////                        // 后续还会支持（自定义等消息类型）
////                    }
////
////                    if(TextUtils.isEmpty(remoteUrl)) {
////                        return ;
////                    }
////                    if(!TextUtils.isEmpty(thumbnailFileUrl)) {
////                        // 先下载缩略图
////                    } else {
////                        // 下载附件
////                    }
//                }
//                // 根据不同类型处理完消息之后，将消息序列化到本地存储（sqlite）
//                // 通知UI有新消息到达
//
//            }
//
//            @Override
//            public void onReceiveMessageNotify(ECMessageNotify ecMessageNotify) {
//
//            }
//
//            @Override
//            public void OnReceiveGroupNoticeMessage(ECGroupNoticeMessage notice) {
//                //收到群组通知消息,可以根据ECGroupNoticeMessage.ECGroupMessageType类型区分不同消息类型
//                Log.i("", "==收到群组通知消息（有人加入、退出...）");
//            }
//
//            @Override
//            public void onOfflineMessageCount(int count) {
//                // 登陆成功之后SDK回调该接口通知帐号离线消息数
//            }
//
//            @Override
//            public int onGetOfflineMessage() {
//                return 0;
//            }
//
//            @Override
//            public void onReceiveOfflineMessage(List msgs) {
//                // SDK根据应用设置的离线消息拉取规则通知应用离线消息
//            }
//
//            @Override
//            public void onReceiveOfflineMessageCompletion() {
//                // SDK通知应用离线消息拉取完成
//            }
//
//            @Override
//            public void onServicePersonVersion(int version) {
//                // SDK通知应用当前帐号的个人信息版本号
//            }
//
//            @Override
//            public void onReceiveDeskMessage(ECMessage ecMessage) {
//
//            }
//
//            @Override
//            public void onSoftVersion(String s, int i) {
//
//            }
//        });
//    }
//
//    public static void setCallBackListener() {
//        setLoginCallback();
//        setIMRecieveCallback();
//        //语音通话状态监听
//        setCallStateCallback();
//        //音频会议回调监听
//        setAudioMeetingCallback();
//        //接手来电监听
//        setOnCallArrivedCallback();
//
//    }
//
//    /**
//     * 接收来电
//     */
//    private static void setOnCallArrivedCallback() {
//        // 接收来电时，需要设置接收来电事件通知Intent。用于SDK回调对应的activity。
//        // 呼入activity在sdk初始化的回 调onInitialized中设置。
//        // 呼入界面activity、开发者需创建或修改VoIPCallActivity类,可参考demo中的
//        //VoIPCallActivity.java(demo中的目录：)
//        Intent intent = new Intent(App.getContext(), VoIPCallActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(App.getContext(), 0, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        ECDevice.setPendingIntent(pendingIntent);
//        //接收来电的Activity若设置无效，可以通过广播的方式接收到来电的通知然后进行处理。
//        //可以参考demo里面YuntxNotifyReceiver类onCallArrived方法。
//        //方法代码如下：
//
////    public void onCallArrived(Context var1, Intent var2) {
////    }
//    }
//
//    /**
//     * 音视频会议回调监听
//     */
//    private static void setAudioMeetingCallback() {
//        if (ECDevice.getECMeetingManager() != null) {
//            ECDevice.getECMeetingManager().setOnMeetingListener(new OnMeetingListener() {
//                @Override
//                public void onVideoRatioChanged(VideoRatio videoRatio) {
//
//                }
//
//                @Override
//                public void onReceiveInterPhoneMeetingMsg(ECInterPhoneMeetingMsg msg) {
//                    // 处理实时对讲消息Push
//                }
//
//                @Override
//                public void onReceiveVoiceMeetingMsg(ECVoiceMeetingMsg msg) {
//                    // 处理语音会议消息push
//                }
//
//                @Override
//                public void onReceiveVideoMeetingMsg(ECVideoMeetingMsg msg) {
//                    // 处理视频会议消息Push（暂未提供）
//                }
//
//                @Override
//                public void onMeetingPermission(String s) {
//
//                }
//            });
//        }
//    }
//
//    /**
//     * 语音通话状态监听
//     */
//    private static void setCallStateCallback() {
//        // 语音通话状态监听，使用语音通话功能的开发者需要设置。
//        ECVoIPCallManager callInterface = ECDevice.getECVoIPCallManager();
//        if (callInterface != null) {
//            callInterface.setOnVoIPCallListener(new ECVoIPCallManager.OnVoIPListener() {
//                @Override
//                public void onVideoRatioChanged(VideoRatio videoRatio) {
//
//                }
//
//                @Override
//                public void onSwitchCallMediaTypeRequest(String s, ECVoIPCallManager.CallType callType) {
//
//                }
//
//                @Override
//                public void onSwitchCallMediaTypeResponse(String s, ECVoIPCallManager.CallType callType) {
//
//                }
//
//                @Override
//                public void onDtmfReceived(String s, char c) {
//
//                }
//
//                @Override
//                public void onCallEvents(ECVoIPCallManager.VoIPCall voipCall) {
//                    // 处理呼叫事件回调
//                    if (voipCall == null) {
//                        Log.e("SDKCoreHelper", "handle call event error , voipCall null");
//                        return;
//                    }
//                    // 根据不同的事件通知类型来处理不同的业务
//                    ECVoIPCallManager.ECCallState callState = voipCall.callState;
//                    switch (callState) {
//                        case ECCALL_PROCEEDING:
//                            Log.i("", "正在连接服务器处理呼叫请求，callid：" + voipCall.callId);
//                            break;
//                        case ECCALL_ALERTING:
//                            Log.i("", "呼叫到达对方，正在振铃，callid：" + voipCall.callId);
//                            break;
//                        case ECCALL_ANSWERED:
//                            Log.i("", "对方接听本次呼叫,callid：" + voipCall.callId);
//                            break;
//                        case ECCALL_FAILED:
//                            // 本次呼叫失败，根据失败原因进行业务处理或跳转
//                            Log.i("", "called:" + voipCall.callId + ",reason:" + voipCall.reason);
//                            break;
//                        case ECCALL_RELEASED:
//                            // 通话释放[完成一次呼叫]
//                            break;
//                        default:
//                            Log.e("SDKCoreHelper", "handle call event error , callState " + callState);
//                            break;
//                    }
//                }
//
//                @Override
//                public void onMediaDestinationChanged(VoipMediaChangedInfo voipMediaChangedInfo) {
//
//                }
//            });
//        }
//    }
//
//    private static ECInitParams createParams(String userId) {
//        ECInitParams params = ECInitParams.createParams();
//        params.setUserid(userId);
//        params.setAppKey(Constant.APPKEY);
//        params.setToken(Constant.TOKEN);
//        //设置登陆验证模式：自定义登录方式
//        params.setAuthType(ECInitParams.LoginAuthType.NORMAL_AUTH);
//        //LoginMode（强制上线：FORCE_LOGIN  默认登录：AUTO。使用方式详见注意事项）
//        params.setMode(ECInitParams.LoginMode.FORCE_LOGIN);
//        return params;
//    }
//
//    private static void setIPs() {
//        String connect = "122.13.2.212";//Connector     ip
//        String connectPort = "8085";//Connector  端口
//        String lvs = "122.13.2.212";//lvs          ip
//        String lvsport = "8888";//lvs          端口
//        String file = "122.13.2.212";//Fileserver      ip
//        String fileport = "8090";//Fileserver      端口
//        StringBuilder sb = new StringBuilder();
//        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
//        sb.append("<ServerAddr version=\"2\">");
//        sb.append("<Connector>");
//        sb.append("<server>");
//        sb.append("<host>");
//        sb.append(connect);
//        sb.append("</host>");
//        sb.append("<port>");
//        sb.append(connectPort);
//        sb.append("</port>");
//        sb.append("</server>");
//        sb.append("</Connector>");
//
//        sb.append("<LVS>");
//        sb.append("<server>");
//        sb.append("<host>");
//        sb.append(lvs);
//        sb.append("</host>");
//        sb.append("<port>");
//        sb.append(lvsport);
//        sb.append("</port>");
//        sb.append("</server>");
//        sb.append("</LVS>");
//
//
//        sb.append("<FileServer>");
//        sb.append("<server>");
//        sb.append("<host>");
//        sb.append(file);
//        sb.append("</host>");
//        sb.append("<port>");
//        sb.append(fileport);
//        sb.append("</port>");
//        sb.append("</server>");
//        sb.append("</FileServer>");
//        sb.append("</ServerAddr>");
//        ECDevice.initServer(App.getContext(), sb.toString());
//    }
//}
