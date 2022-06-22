package com.depauw.jukebox;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import com.depauw.jukebox.databinding.ActivityPlayerBinding;

public class PlayerActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private ActivityPlayerBinding binding;
    private static int RED_VALUE = 0;
    private static int GREEN_VALUE = 0;
    private static int BLUE_VALUE = 0;
    private static float AVERAGE_ONE = 0;
    private static float AVERAGE_TWO = 0;
    private static float AVERAGE_THREE = 0;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.seekbarRed.setOnSeekBarChangeListener(this);
        binding.seekbarBlue.setOnSeekBarChangeListener(this);
        binding.seekbarGreen.setOnSeekBarChangeListener(this);
        binding.seekbarSongPosition.setOnSeekBarChangeListener(this);
        binding.radiogroupSongs.setOnCheckedChangeListener(this);
        binding.buttonCastVote.setOnClickListener(this);
        int selectedId = binding.radiogroupSongs.getCheckedRadioButtonId();
        buttonChecker(selectedId);
    }

    public void buttonChecker(int id) {
        int songId = 0;
        if (id == R.id.radio_song1) {
            binding.imageviewAlbumCover.setBackground(getResources().getDrawable(R.drawable.track1, getTheme()));
            songId = R.raw.track1;
        } else if (id == R.id.radio_song2) {
            binding.imageviewAlbumCover.setBackground(getResources().getDrawable(R.drawable.track2, getTheme()));
            songId = R.raw.track2;
        } else {
            binding.imageviewAlbumCover.setBackground(getResources().getDrawable(R.drawable.track3, getTheme()));
            songId = R.raw.track3;
        }
        mediaPlayer = MediaPlayer.create(this, songId);
        mediaPlayer.start();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()) {
            case R.id.seekbar_red: {
                binding.textviewRed.setText(String.valueOf(i));
                RED_VALUE = i;
                binding.constraintlayout.setBackgroundColor(Color.rgb(RED_VALUE, GREEN_VALUE, BLUE_VALUE));
                break;
            }
            case R.id.seekbar_blue: {
                binding.textviewBlue.setText(String.valueOf(i));
                BLUE_VALUE = i;
                binding.constraintlayout.setBackgroundColor(Color.rgb(RED_VALUE, GREEN_VALUE, BLUE_VALUE));
                break;
            }
            case R.id.seekbar_green: {
                binding.textviewGreen.setText(String.valueOf(i));
                GREEN_VALUE = i;
                binding.constraintlayout.setBackgroundColor(Color.rgb(RED_VALUE, GREEN_VALUE, BLUE_VALUE));
                break;
            }
            case R.id.seekbar_song_position: {
                mediaPlayer.pause();
                mediaPlayer.seekTo((i * mediaPlayer.getDuration()) / 100);
                mediaPlayer.start();
                break;
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getId()) {
            case R.id.radiogroup_songs: {
                mediaPlayer.stop();
                binding.seekbarSongPosition.setProgress(0);
                buttonChecker(i);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_cast_vote: {
                if(binding.radioSong1.isChecked()){
                    float ratingValue = AVERAGE_ONE * Integer.valueOf((String) binding.textviewNumVotes1.getText());
                    ratingValue = ratingValue + binding.ratingbarVoterRating.getRating();
                    int voteCount = Integer.valueOf((String) binding.textviewNumVotes1.getText()) + 1;
                    AVERAGE_ONE = ratingValue/voteCount;
                    binding.progressbarAverageRating1.setProgress(Math.round(AVERAGE_ONE));
                    binding.textviewNumVotes1.setText(String.valueOf(voteCount));
                    binding.ratingbarVoterRating.setProgress(0);
                }
                else if(binding.radioSong2.isChecked()){
                    float ratingValue = AVERAGE_TWO * Integer.valueOf((String) binding.textviewNumVotes2.getText());
                    ratingValue = ratingValue + binding.ratingbarVoterRating.getRating();
                    int voteCount = Integer.valueOf((String) binding.textviewNumVotes2.getText()) + 1;
                    AVERAGE_TWO = ratingValue/voteCount;
                    binding.progressbarAverageRating2.setProgress(Math.round(AVERAGE_TWO));
                    binding.textviewNumVotes2.setText(String.valueOf(voteCount));
                    binding.ratingbarVoterRating.setProgress(0);
                }
                else{
                    float ratingValue = AVERAGE_THREE * Integer.valueOf((String) binding.textviewNumVotes3.getText());
                    ratingValue = ratingValue + binding.ratingbarVoterRating.getRating();
                    int voteCount = Integer.valueOf((String) binding.textviewNumVotes3.getText()) + 1;
                    AVERAGE_THREE = ratingValue/voteCount;
                    binding.progressbarAverageRating3.setProgress(Math.round(AVERAGE_THREE));
                    binding.textviewNumVotes3.setText(String.valueOf(voteCount));
                    binding.ratingbarVoterRating.setProgress(0);
                }
            }
        }
    }

}