package com.example.lxst.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.lxst.Model.QuestionModel;
import com.example.lxst.Model.ResultModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionRepository {

    private FirebaseFirestore firebaseFirestore;
    private String quizId;
    private HashMap<String , String> resultMap= new HashMap<>();
    private HashMap<String , String> userMap= new HashMap<>();
    private OnQuestionLoad onQuestionLoad;
    private OnResultAdded onResultAdded;
    private String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private OnResultLoad onResultLoad;

    private String user = FirebaseAuth.getInstance().getCurrentUser().getEmail();


    public void getResults(){
        firebaseFirestore.collection("Quiz").document(quizId)
                .collection("results").document(currentUserId)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            resultMap.put("correct" , task.getResult().getString("correct"));
                            resultMap.put("wrong" , task.getResult().getString("wrong"));
                            resultMap.put("notAnswered" , task.getResult().getString("notAnswered"));
                            onResultLoad.onResultLoad(resultMap);
                        }else{
                            onResultLoad.onError(task.getException());
                        }
                    }
                });
    }

    public void addResults(HashMap<String , Object> resultMap){
        resultMap.put("user" , user);
        firebaseFirestore.collection("Quiz").document(quizId)
                .collection("results").document(currentUserId)
                .set(resultMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            onResultAdded.onSubmit();
                        }else{
                            onResultAdded.onError(task.getException());
                        }
                    }
                });
    }

    public void addUser(HashMap<String , Object> userMap){
        firebaseFirestore.collection("Quiz").document(quizId)
                .collection("results").document(currentUserId)
                .set(userMap);
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }


    public  QuestionRepository(OnQuestionLoad onQuestionLoad , OnResultAdded onResultAdded , OnResultLoad onResultLoad){
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.onQuestionLoad = onQuestionLoad;
        this.onResultAdded = onResultAdded;
        this.onResultLoad = onResultLoad;

    }

    public void getQuestions(){
        firebaseFirestore.collection("Quiz").document(quizId)
                .collection("questions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull  Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            onQuestionLoad.onLoad(task.getResult().toObjects(QuestionModel.class));
                        }else{
                            onQuestionLoad.onError(task.getException());
                        }
                    }
                });
    }

    public interface OnResultLoad{
        void onResultLoad(HashMap<String , String> resultMap);
        void onError(Exception e);
    }

    public interface OnQuestionLoad{
        void onLoad(List<QuestionModel> questionModels);
        void onError(Exception e);
    }
    public interface OnResultAdded{
        boolean onSubmit();
        void onError(Exception e);
    }
}