package com.example.administrator.myapplication.loadingView;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.loadingView.view.WSCircleArc;
import com.example.administrator.myapplication.loadingView.view.WSCircleBar;
import com.example.administrator.myapplication.loadingView.view.WSCircleCD;
import com.example.administrator.myapplication.loadingView.view.WSCircleFace;
import com.example.administrator.myapplication.loadingView.view.WSCircleJump;
import com.example.administrator.myapplication.loadingView.view.WSCircleRing;
import com.example.administrator.myapplication.loadingView.view.WSCircleRise;
import com.example.administrator.myapplication.loadingView.view.WSCircleSun;
import com.example.administrator.myapplication.loadingView.view.WSEatBeans;
import com.example.administrator.myapplication.loadingView.view.WSFiveStar;
import com.example.administrator.myapplication.loadingView.view.WSGearLoading;
import com.example.administrator.myapplication.loadingView.view.WSGears;
import com.example.administrator.myapplication.loadingView.view.WSJump;
import com.example.administrator.myapplication.loadingView.view.WSLineProgress;
import com.example.administrator.myapplication.loadingView.view.WSMaterialLoading;


public class LoadingViewActivity extends AppCompatActivity {

    private WSCircleCD mWSCircleCD;
    private WSCircleSun mWSCircleSun;
    private WSCircleRing mWSCircleRing;
    private WSCircleFace mWSCircleFace;
    private WSCircleJump mWSCircleJump;
    private WSGears mWSGears;
    private WSJump mWSJump;
    private WSLineProgress mWSLineProgress;
    private WSEatBeans mWSEatBeans;
    private WSFiveStar mWSFiveStar;
    private WSFiveStar mWSFiveStarView;
    private WSCircleRise mWSCircleRise;
    private WSCircleBar mWSCircleBar;
    private WSCircleArc mWSCircleArc;
    private WSMaterialLoading mWSMaterialLoading;
    private WSGearLoading mWSGearLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadingview);

        mWSCircleCD = (WSCircleCD) findViewById(R.id.load_cd);
        mWSCircleCD.startAnimator();


        mWSCircleSun = (WSCircleSun) findViewById(R.id.load_sun);
        mWSCircleSun.startAnimator();

        mWSCircleRing = (WSCircleRing) findViewById(R.id.load_ring);
        mWSCircleRing.startAnimator();

        mWSCircleFace = (WSCircleFace) findViewById(R.id.load_face);
        mWSCircleFace.startAnimator();

        mWSCircleJump = (WSCircleJump) findViewById(R.id.load_jump);
        mWSCircleJump.startAnimator();

        mWSGears = (WSGears) findViewById(R.id.load_gear);
        mWSGears.startAnimator();

        mWSJump = (WSJump) findViewById(R.id.load_mjump);
        mWSJump.startAnimator();

        mWSLineProgress = (WSLineProgress) findViewById(R.id.load_line_progress);
        mWSLineProgress.startAnimator();

        mWSEatBeans = (WSEatBeans) findViewById(R.id.load_eat);
        mWSEatBeans.startAnimator();

        mWSFiveStar = (WSFiveStar) findViewById(R.id.load_five);
        mWSFiveStar.startAnimator();

        mWSFiveStarView = (WSFiveStar) findViewById(R.id.load_mfive);
        mWSFiveStarView.setRegularPolygon(5);
        mWSFiveStarView.startAnimator();

        mWSCircleRise = (WSCircleRise) findViewById(R.id.load_rise);
        mWSCircleRise.startAnimator();

        mWSCircleBar = (WSCircleBar) findViewById(R.id.load_bar);
        mWSCircleBar.startAnimator();

        mWSCircleArc = (WSCircleArc) findViewById(R.id.load_arc);
        mWSCircleArc.startAnimator();


        mWSMaterialLoading = (WSMaterialLoading) findViewById(R.id.load_material);
        mWSMaterialLoading.startAnimator();

        mWSGearLoading = (WSGearLoading) findViewById(R.id.load_gear_loading);
        mWSGearLoading.startAnimator();

    }
}
