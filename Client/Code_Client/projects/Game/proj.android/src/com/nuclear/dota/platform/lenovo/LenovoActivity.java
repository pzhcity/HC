package com.nuclear.dota.platform.lenovo;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

import com.nuclear.PlatformAndGameInfo;
import com.nuclear.dota.GameActivity;
import com.nuclear.dota.GameConfig;
import com.nuclear.dota.Config;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class LenovoActivity extends GameActivity {

	public LenovoActivity() {
		super.mGameCfg = new GameConfig(this,
				PlatformAndGameInfo.enPlatform_Lenovo);
	}

//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		LogoutReciver logoutReciver = new LogoutReciver();
//		IntentFilter fliter = new IntentFilter();
//		fliter.addAction(PlatformLenovoLoginAndPay.LOGOUT_NORMAL);
//		fliter.addAction(PlatformLenovoLoginAndPay.LOGOUT_LENOVO);
//		registerReceiver(logoutReciver, fliter);
//
//	}

//	private class LogoutReciver extends BroadcastReceiver {
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			PlatformLenovoLoginAndPay.getInstance().callLoginout(context,
//					intent);
//
//		}
//	}
	
	private boolean doInitWeChat = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(doInitWeChat==false)
		{
			ApplicationInfo appInfo = null;
			try {
				appInfo = this.getPackageManager().getApplicationInfo(this.getPackageName(),PackageManager.GET_META_DATA);
			} catch (NameNotFoundException e) {
		
			}
		
			if(appInfo!=null && appInfo.metaData != null)
			{
				String WX_APP_ID = appInfo.metaData.getString("WX_APP_ID");
				if(WX_APP_ID!=null)
				{
				Config.WX_APP_ID = WX_APP_ID;
				}
			}
			
			doInitWeChat = true;
		}
		if(null!=Config.WX_APP_ID && !Config.WX_APP_ID.equals("")){
			api = WXAPIFactory.createWXAPI(this, Config.WX_APP_ID, false);
			api.registerApp(Config.WX_APP_ID);// 将该app注册到微信
		}
		
    }
}
