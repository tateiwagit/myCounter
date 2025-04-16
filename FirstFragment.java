package com.example.mycounter;

import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycounter.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private MyCounterViewModel viewModel;

    private FragmentFirstBinding binding;
    private TextView counterText;
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

        viewModel = new ViewModelProvider(requireActivity()).get(MyCounterViewModel.class);

        requireActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);

        counterText = view.findViewById(R.id.counter_text);

        Button plusButton = view.findViewById(R.id.plus_button);
        plusButton.setOnClickListener(v -> viewModel.incrementCounter());

        Button minusButton = view.findViewById(R.id.minus_button);
        minusButton.setOnClickListener(v -> viewModel.decrementCounter());

        Button resetButton = view.findViewById(R.id.reset_button);
        resetButton.setOnClickListener(v -> viewModel.resetCounter());

        viewModel.getCounter().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                counterText.setText(String.valueOf(integer));
            }
        });

    }

    /**
     * MainActivity から呼び出され、カウンター値を更新し TextView に反映するメソッド。
     */
    public void updateCounter() {
        viewModel.incrementCounter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
