package com.example.mycounter;

import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mycounter.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private TextView counterText;
    private int counter = 0;
    private VolumeContentObserver volumeObserver;
    private final Handler androidHandler = new Handler();


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);

        counterText = view.findViewById(R.id.counter_text);

        Button plusButton = view.findViewById(R.id.plus_button);
        plusButton.setOnClickListener(v -> {
            counter++;
            counterText.setText(String.valueOf(counter));
        });

        Button minusButton = view.findViewById(R.id.minus_button);
        minusButton.setOnClickListener(v -> {
            counter--;
            counterText.setText(String.valueOf(counter));
        });

        Button resetButton = view.findViewById(R.id.reset_button);
        resetButton.setOnClickListener(v -> {
            counter = 0;
            counterText.setText(String.valueOf(counter));
        });

        // Initialize and register VolumeContentObserver
        volumeObserver = new VolumeContentObserver(requireContext(), androidHandler);
        requireActivity().getContentResolver().registerContentObserver(
                android.provider.Settings.System.CONTENT_URI,
                true,
                volumeObserver
        );
    }

    /**
     * MainActivity から呼び出され、カウンター値を更新し TextView に反映するメソッド。
     * @param change 加算または減算する値 (音量の変化量)
     */
    public void updateCounter(int change) {
        // カウンター値を更新
        counter += change;

        // binding オブジェクトが null でないことを確認（Fragment の View が破棄されていないか）
        if (binding != null) {
            // TextView のテキストを更新後のカウンター値で更新
            binding.counterText.setText(String.valueOf(counter));
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        // Unregister VolumeContentObserver
        requireActivity().getContentResolver().unregisterContentObserver(volumeObserver);
    }

}
