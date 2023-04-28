package com.example.newsite.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.newsite.BR;
import com.example.newsite.R;
import com.example.newsite.add.AlarmInfo;
import com.example.newsite.add.Elements;
import com.example.newsite.add.MarqueeView;
import com.example.newsite.add.SiteInfo;
import com.example.newsite.add.SiteSets;
import com.example.newsite.databinding.ActivityEightT192x96Binding;
import com.example.newsite.databinding.ActivityFourTw192x96Binding;
import com.example.newsite.databinding.ActivityFourTw256x128Binding;
import com.example.newsite.databinding.ActivityFourTw320x160Binding;
import com.example.newsite.databinding.ActivityMainBinding;

import com.example.newsite.databinding.ActivitySixT160x96Binding;
import com.example.newsite.databinding.ActivitySixT192x96Binding;
import com.example.newsite.databinding.ActivitySixT256x128Binding;
import com.example.newsite.databinding.ActivitySixT96x160Binding;
import com.example.newsite.databinding.ActivitySixTw2256x128Binding;
import com.example.newsite.databinding.ActivitySixTwChange192x96Binding;
import com.example.newsite.datamodel.LiveDataBus;
import com.example.newsite.fragments.ElementFragment;
import com.example.newsite.fragments.PMFragment;
import com.example.newsite.service.ElementsService;

import java.util.Timer;

public class MainActivity extends Activity implements LifecycleOwner {
    //ActivitySixTwChange192x96Binding binding;
   // ActivityEightT192x96Binding binding;
   // ActivitySixT160x96Binding binding;
    //ActivitySixT96x160Binding binding;
    //ActivityFourTw320x160Binding binding;
   // ActivityFourTw192x96Binding binding;
   // ActivityFourTw256x128Binding binding;
    ActivitySixT256x128Binding binding;
    //ActivitySixT192x96Binding binding;
    //ActivitySixTw2256x128Binding binding;
    ElementFragment elementFragment;
    PMFragment pmFragment;
    private SiteSets siteSets;
   /* TextView maintitile;
    MarqueeView weatherinfo;
    TextView rightText1, leftText1;*/
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MainActivity", "onCreate");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        requestPermissions();
        initDataBinding();
        //initView();
        inintFragments();
        initSizeAndTypeFace();

        setAllObserve();
        startService();
      //  elementFragment.setDefultText();
    }

    private void initSizeAndTypeFace() {
        if(elementFragment!=null){
            elementFragment.setSizeAndType(SiteSets.getSiteTextSet().midSize,fontFace);
        }
        if(pmFragment!=null){
            pmFragment.setSizeAndType(SiteSets.getSiteTextSet().midSize,fontFace);
        }
        binding.setTypeFace(fontFace);
        binding.setVariable(BR.textSize,SiteSets.getSiteTextSet().topSize);
        FragmentManager manager  = getFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.content_fragment,elementFragment).commitAllowingStateLoss();
    }

    private void initDataBinding() {
        timer = new Timer();
        fontFace = Typeface.createFromAsset(getAssets(), "fonts/simsun.ttc");//HYQiHei-25JF.ttf//simfang.ttf//PixelMplus10-Regular
        binding = DataBindingUtil.setContentView(this, SiteSets.getSiteTextSet().layoutId);
        binding.setMainActivity(this);
        binding.setTimer(null);
        binding.setTypeFace(fontFace);
    }

    public static Typeface fontFace;

    private void inintFragments() {
        elementFragment = new ElementFragment();
        pmFragment = new PMFragment();

       /* binding.setELementFragemnt(elementFragment);
        if(SiteSets.getSiteTextSet().isChange){
            binding.setPmFragment(null);
        }*/

    }

    private void initView() {

       /* maintitile = findViewById(R.id.title);
        weatherinfo = findViewById(R.id.weatherinfo);
        maintitile.setTypeface(fontFace);
        maintitile.getPaint().setAntiAlias(false);
        rightText1 = findViewById(R.id.rightText);
        leftText1 = findViewById(R.id.leftText);
        // Typeface fontFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/simsun.ttc");//HYQiHei-25JF.ttf//simfang.ttf//PixelMplus10-Regular
        rightText1.setTypeface(fontFace);
        leftText1.setTypeface(fontFace);
        leftText1.getPaint().setAntiAlias(false);
        rightText1.getPaint().setAntiAlias(false);
        weatherinfo.getPaint().setAntiAlias(false);
        weatherinfo.setFocusable(true);
        weatherinfo.requestFocus();*/
    }


    @Override
    protected void onDestroy() {
        stopService();
        if(timer!=null){
            timer.cancel();
        }
        super.onDestroy();
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.RECEIVE_BOOT_COMPLETED}, 1);
        }
    }

    private void startService() {
        Intent intent = new Intent(this, ElementsService.class);
        startService(intent);
    }
    private void stopService(){
        Intent intent = new Intent(this, ElementsService.class);
        stopService(intent);
    }
    private void setAllObserve() {
        LiveDataBus.getInstance().getElementsMutableLiveData().observe(this, new Observer<Elements>() {
            @Override
            public void onChanged(Elements elements) {
                //处理六要素
                Log.i("MainActivity", "处理六要素------------");
                Log.i("MainActivity", "时间："+elements.getYear());
                binding.setElements(elements);
                elementFragment.updateText(SiteSets.getSiteTextSet().getLeftText(elements),SiteSets.getSiteTextSet().getRightText(elements));
            }
        });
        LiveDataBus.getInstance().getWeaInfoMutableLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                //处理天气
                Log.i("MainActivity", "处理天气----------------");
                binding.setWeaInfo(s);
            }
        });

        LiveDataBus.getInstance().getAlarmInfoMutableLiveData().observe(this, new Observer<AlarmInfo>() {
            @Override
            public void onChanged(AlarmInfo alarmInfo) {
                //处理预警
                Log.i("MainActivity", "处理预警------------");
            }
        });
        LiveDataBus.getInstance().getSiteInfoMutableLiveData().observe(this, new Observer<SiteInfo>() {
            @Override
            public void onChanged(SiteInfo siteInfo) {
                //处理自动站
                Log.i("MainActivity", "处理自动站--------------");
            }
        });
    }
    private final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

    @Override
    public void onStart() {
        super.onStart();

        dispatch(Lifecycle.Event.ON_START);
    }

    @Override
    public void onResume() {
        super.onResume();
        dispatch(Lifecycle.Event.ON_RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        dispatch(Lifecycle.Event.ON_PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();
        dispatch(Lifecycle.Event.ON_STOP);

    }

    private void dispatch(Lifecycle.Event event) {
        mLifecycleRegistry.handleLifecycleEvent(event);
    }
}