package games.android.trivia.Bars;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import games.android.trivia.App;
import games.android.trivia.R;


public class TopBarFragment extends Fragment {
    private TextView scoreTxt = null;
    private TextView timerTxt = null;
    private ImageView heart1 = null;
    private ImageView heart2 = null;
    private ImageView heart3 = null;
    private TopBarListener listener = null;
    private Animation heartPulse = null;
    private Typeface numbersFont = null;
    private int secondAlert = 5;
    private Animation onePulse = null;


    public TopBarFragment() {
        // Required empty public constructor
        onePulse = AnimationUtils.loadAnimation(App.getInstance().getApplicationContext(),R.anim.heart_pulse_big);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Top bar", "created");
    }

    public void setHearts(int hearts) {
        Log.d("top bar", "set " + hearts + " hearts");
        switch (hearts) {
            case 1 :
                heart1.setVisibility(View.VISIBLE);
                heart2.setVisibility(View.INVISIBLE);
                heart2.clearAnimation();
                heart3.setVisibility(View.INVISIBLE);
                break;
            case 2 :
                heart1.setVisibility(View.VISIBLE);
                heart2.setVisibility(View.VISIBLE);
                heart3.setVisibility(View.INVISIBLE);
                heart3.clearAnimation();
                break;
            case 3 :
                heart1.setVisibility(View.VISIBLE);
                heart2.setVisibility(View.VISIBLE);
                heart3.setVisibility(View.VISIBLE);
                break;
            default:
                heart1.clearAnimation();
                heart1.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void setScoreTxt(String scoreTxt) {
        this.scoreTxt.setText(scoreTxt);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_bar, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("TopBar", "Created");
        scoreTxt = (TextView) getView().findViewById(R.id.score_txt);
        heart1 = (ImageView) (getView().findViewById(R.id.heart_1));
        heart2 = (ImageView) getView().findViewById(R.id.heart_2);
        heart3 = (ImageView) getView().findViewById(R.id.heart_3);
        timerTxt = (TextView) getView().findViewById(R.id.timer);
        this.listener.onTopBarCreatedFinished();
        this.startAnimationHears();

        numbersFont = Typeface.createFromAsset(App.getInstance().getAssets(),"fonts/hebrew.ttf");
        scoreTxt.setTypeface(numbersFont);
        timerTxt.setTypeface(numbersFont);
    }

    private void startAnimationHears() {
        heartPulse = AnimationUtils.loadAnimation(App.getInstance().getApplicationContext(),R.anim.heart_pulse_big);
        heart1.startAnimation(heartPulse);
        heart2.startAnimation(heartPulse);
        heart3.startAnimation(heartPulse);
    }

    public void setListener(TopBarListener listener) {
        this.listener = listener;
    }

    public interface TopBarListener {
        void onTopBarCreatedFinished();
    }

    public void setTime(int seconds){
        if(seconds < secondAlert){
            timerTxt.setTextColor(Color.RED);
            timerTxt.startAnimation(onePulse);
        }
        timerTxt.setText(String.valueOf(seconds));
    }
}
