package com.tingler.challenge;

import java.util.HashMap;
import java.util.Map;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.Facebook;
import com.tingler.challenge.api.call.APIS;
import com.tingler.challenge.api.call.Authentication;
import com.tingler.challenge.api.call.FacebookLogin;
import com.tingler.challenge.api.call.GoogleLogin;
import com.tingler.challenge.fragment.About;
import com.tingler.challenge.fragment.AcceptReject;
import com.tingler.challenge.fragment.ChallengeMembers;
import com.tingler.challenge.fragment.ChallengeWithChat;
import com.tingler.challenge.fragment.Dashboard;
import com.tingler.challenge.fragment.Help;
import com.tingler.challenge.fragment.Notification;
import com.tingler.challenge.fragment.Profile;
import com.tingler.challenge.fragment.SelectWinner;
import com.tingler.challenge.fragment.VoteForWitness;
import com.tingler.challenge.fragment.createchallenge.Details;

public class MainActivity extends FragmentActivity implements OnClickListener {
	Toolbar toolbar;
	public static DrawerLayout drawerLayout;
	ImageView imv_action_menu;
	TextView txt_profile, txt_dashboard, txt_notification, txt_helpcenter,
			txt_aboutus, txt_update, txt_signout;
	ImageView etxt_setting,imageview_profile;
	public static TextView toolbar_title;
    Button btn_createchallenge;
    LinearLayout layout_notifi;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
	//	setContentView(R.layout.welcome_test);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		init();
		
	}

	public void init() {
		
		Typeface robotoBoldTF = Typeface.createFromAsset(getAssets(),"fonts/roboto_bold.ttf"); 
		
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		imv_action_menu = (ImageView) findViewById(R.id.imv_action_menu);

		txt_profile = (TextView) findViewById(R.id.txt_profile);
		txt_dashboard = (TextView) findViewById(R.id.txt_dashboard);
		txt_notification = (TextView) findViewById(R.id.txt_notification);
		txt_helpcenter = (TextView) findViewById(R.id.txt_helpcenter);
		txt_aboutus = (TextView) findViewById(R.id.txt_aboutus);
		txt_update = (TextView) findViewById(R.id.txt_update);
		txt_signout = (TextView) findViewById(R.id.txt_signout);
		toolbar_title = (TextView) findViewById(R.id.toolbar_title);
		layout_notifi=(LinearLayout)findViewById(R.id.layout_notifi);
		toolbar_title.setTypeface(robotoBoldTF);
		
		imageview_profile=(ImageView)findViewById(R.id.imageview_profile);
		com.tingler.challenge.util.Profile profile = new com.tingler.challenge.util.Profile(MainActivity.this);
		try {
			Bitmap bitmap=	profile.decodeBase64(profile.getProfileBase64());
			bitmap=profile.getCroppedBitmap(bitmap);
			imageview_profile.setImageBitmap(bitmap);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		etxt_setting = (ImageView) findViewById(R.id.etxt_setting);
		btn_createchallenge=(Button)findViewById(R.id.btn_createchallenge);
		imv_action_menu.setOnClickListener(this);
		txt_profile.setOnClickListener(this);
		txt_dashboard.setOnClickListener(this);
		
		txt_helpcenter.setOnClickListener(this);
		txt_aboutus.setOnClickListener(this);
		txt_update.setOnClickListener(this);
		txt_signout.setOnClickListener(this);
		etxt_setting.setOnClickListener(this);
		btn_createchallenge.setOnClickListener(this);
		layout_notifi.setOnClickListener(this);
	
		toolbar_title.setText("Profile");
		if(getIntent().getExtras()!=null){
			displayView(2);
		}
		displayView(0);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.imv_action_menu) {
			drawerLayout.openDrawer(Gravity.END);
		} else if (v.getId() == R.id.txt_profile) {
			displayView(0);
			toolbar_title.setText("Profile");
		} else if (v.getId() == R.id.txt_dashboard) {
			displayView(1);
			toolbar_title.setText("Dashboard");
		}else if (v.getId() == R.id.layout_notifi) {
			displayView(2);
			toolbar_title.setText("Notification");
		}else if (v.getId() == R.id.txt_helpcenter) {
			displayView(3);
			toolbar_title.setText("Help Center");
		}else if (v.getId() == R.id.txt_aboutus) {
			displayView(4);
			//toolbar_title.setText("About Us");
			toolbar_title.setText("Select a winner");
		}else if (v.getId() == R.id.txt_aboutus) {
			displayView(4);
			toolbar_title.setText("About Us");
		}else if(v.getId()==R.id.btn_createchallenge){
			toolbar_title.setText("Create Challenge");
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, new Details()).commit();
			drawerLayout.closeDrawers();
		}else if(v.getId()==R.id.txt_signout){
			com.tingler.challenge.util.Profile profile=new com.tingler.challenge.util.Profile(MainActivity.this);
			profile.signOut();
			Intent intent=new Intent(MainActivity.this,WelcomeActivity.class);
			MainActivity.this.startActivity(intent);
			MainActivity.this.finish();
			Toast.makeText(MainActivity.this, "Sign out successfully!", Toast.LENGTH_LONG).show();
			
			if(profile.getMediaType().equalsIgnoreCase("google_plus")){
				GoogleLogin googleLogin=new GoogleLogin(MainActivity.this);
				googleLogin.googleLogout();
			
			}else if(profile.getMediaType().equalsIgnoreCase("facebook")){
				FacebookLogin.callFacebookLogout(MainActivity.this);
			}
			
		}

	}

	public void displayView(int position) {
		Fragment fragment = null;
		com.tingler.challenge.util.Profile profile = new com.tingler.challenge.util.Profile(MainActivity.this);
		Authentication authentication = new Authentication(this);
		
		switch (position) {
		case 0:
			fragment = new Profile();
			break;
		case 1:
			//fragment = new Dashboard();
			System.out.println("profiile id" + profile.getId());
			Map<String, String> params = new HashMap<String, String>();
			params.put(APIS.CC_user_id, profile.getId());
			authentication.requestGetUserDashboardAPI(params);
			
			break;
		case 2:
		//	fragment = new Notification();
			Map<String, String> paramsnoti = new HashMap<String, String>();
			paramsnoti.put(APIS.CC_user_id, profile.getId());
			authentication.requestNotificationAPI(paramsnoti, null);
			break;
		case 3:
			
			//fragment = new VoteForWitness();
			//fragment = new Help();
			break;
		case 4:
			//fragment = new About();
			fragment=new SelectWinner();
		//	fragment = new ChallengeMembers();
			break;
		case 5:
			fragment = new Details();
			break;
       
		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			drawerLayout.closeDrawers();
		} else {

			Log.e("MainActivity", "Error in creating fragment");
		}
	}

}
