package com.example.lxst.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.lxst.Adapter.LeaderboardAdapter;
import com.example.lxst.Model.LeaderboardModel;
import com.example.lxst.R;
import com.example.lxst.viewmodel.AuthViewModel;
import com.example.lxst.viewmodel.LeaderboardViewModel;

import java.util.List;


public class LeaderboardFragment extends Fragment implements LeaderboardAdapter.OnItemClickedListner {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private NavController navController;
    private LeaderboardViewModel viewModel;
    private LeaderboardAdapter adapter;
    private Button leaderboardHomeBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leaderboard, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this , ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(LeaderboardViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.LeaderboardListRecyclerview);
        progressBar = view.findViewById(R.id.LeaderboardListProgressbar);
        navController = Navigation.findNavController(view);

        leaderboardHomeBtn = view.findViewById(R.id.leaderboard_home_btn);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LeaderboardAdapter(this);


        recyclerView.setAdapter(adapter);

        viewModel.getLeaderboardListLiveData().observe(getViewLifecycleOwner(), new Observer<List<LeaderboardModel>>() {
            @Override
            public void onChanged(List<LeaderboardModel> leaderboardModelList) {
                progressBar.setVisibility(View.GONE);
                adapter.setLeaderboardModelList(leaderboardModelList);
                adapter.notifyDataSetChanged();
            }
        });

        leaderboardHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_leaderboardFragment_to_listFragment);
            }
        });

    }

    @Override
    public void onItemClick(int position) {

    }
}