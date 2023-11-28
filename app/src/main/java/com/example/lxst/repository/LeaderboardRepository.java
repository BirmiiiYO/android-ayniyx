package com.example.lxst.repository;

import androidx.annotation.NonNull;

import com.example.lxst.Model.LeaderboardModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class LeaderboardRepository {

    private onFirestoreTaskComplete onFirestoreTaskComplete;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference reference = firebaseFirestore.collection("Quiz").document("8Os9UtvwjMH3XSpwuYqE").collection("results");

    public LeaderboardRepository(onFirestoreTaskComplete onFirestoreTaskComplete){
        this.onFirestoreTaskComplete = onFirestoreTaskComplete;
    }
    public void getLeaderboardData(){
        reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull  Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    onFirestoreTaskComplete.LeaderboardDataLoaded(task.getResult()
                            .toObjects(LeaderboardModel.class));
                }else{
                    onFirestoreTaskComplete.onError(task.getException());
                }
            }
        });
    }

    public interface onFirestoreTaskComplete{
        void LeaderboardDataLoaded(List<LeaderboardModel> leaderboardModelList);
        void onError(Exception e);
    }
}