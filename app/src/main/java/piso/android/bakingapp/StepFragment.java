package piso.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;

import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.net.InetAddress;

import piso.android.bakingapp.Models.Recipe;
import piso.android.bakingapp.Models.Step;

import static android.content.ContentValues.TAG;

/**
 * Created by pisoo on 7/4/2017.
 */

public class StepFragment extends Fragment {

    private static final String TAG = "MainActivity";
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private VideoView videoView;
    private Recipe recipe;
    private TextView Description;
    private ImageView photo;

    private int position;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_step, container, false);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(getActivity(), "Land", Toast.LENGTH_SHORT).show();
            Log.v("state", "land");
            startActivity(new Intent(getActivity(), TestAct.class));
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(getActivity(), "port", Toast.LENGTH_SHORT).show();
            Log.v("state", "port");
            //  startActivity(new Intent(getActivity() , TestAct.class));

        }
        recipe = new Recipe();
        mPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.playerView);
        Description = (TextView) view.findViewById(R.id.tv_descriptionStep);
        photo = (ImageView) view.findViewById(R.id.Iv_photoStep);
        if (getArguments() != null) {
            recipe = (Recipe) getArguments().getSerializable("recipe");
            position = getArguments().getInt("position");

        }
        if (getActivity().getIntent() != null) {
            recipe = (Recipe) getActivity().getIntent().getExtras().get("recipe");
        }
        if (recipe.getSteps().get(position).getVideoURL().isEmpty() &&
                !recipe.getSteps().get(position).getThumbnailURL().isEmpty()) {
            Picasso.with(getActivity())
                    .load(recipe.getSteps().get(position).getThumbnailURL())
                    .into(photo);
            mPlayerView.setVisibility(View.GONE);
        }
       /* if (recipe.getSteps().get(position).getVideoURL() == null || recipe.getSteps().get(position).getVideoURL().equals("")) {
            network.setVisibility(View.VISIBLE);
            network.setText(R.string.no_video);
            mPlayerView.setVisibility(View.GONE);
        }
        else {
            initializePlayer(Uri.parse(recipe.getSteps().get(position).getVideoURL()));
            network.setVisibility(View.GONE);
            mPlayerView.setVisibility(View.VISIBLE);
        }*/
        initializePlayer(Uri.parse(recipe.getSteps().get(position).getVideoURL()));
        // network.setVisibility(View.GONE);
        mPlayerView.setVisibility(View.VISIBLE);
        Description.setText(recipe.getSteps().get(position).getDescription());
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Gabriola.ttf");
        Description.setTypeface(tf);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // TODO (2): Set the ExoPlayer.EventListener to this activity

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(false);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }

    }
}
