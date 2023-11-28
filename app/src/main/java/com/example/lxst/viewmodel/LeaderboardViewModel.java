package com.example.lxst.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lxst.Model.LeaderboardModel;
import com.example.lxst.repository.LeaderboardRepository;

import java.util.List;

public class LeaderboardViewModel extends ViewModel implements LeaderboardRepository.onFirestoreTaskComplete {

    private MutableLiveData<List<LeaderboardModel>> leaderboardListLiveData = new MutableLiveData<>();

    private LeaderboardRepository repository = new LeaderboardRepository(this);

    public MutableLiveData<List<LeaderboardModel>> getLeaderboardListLiveData() {
        return leaderboardListLiveData;
    }

    public LeaderboardViewModel(){
        repository.getLeaderboardData();
    }

    @Override
    public void LeaderboardDataLoaded(List<LeaderboardModel> leaderboardModelList) {
        leaderboardListLiveData.setValue(leaderboardModelList);
    }

    @Override
    public void onError(Exception e) {
        Log.d("ошибОчка", "onError: " + e.getMessage());
    }


}