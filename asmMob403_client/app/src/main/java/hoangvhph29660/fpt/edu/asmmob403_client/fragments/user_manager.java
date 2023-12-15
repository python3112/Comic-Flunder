package hoangvhph29660.fpt.edu.asmmob403_client.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import hoangvhph29660.fpt.edu.asmmob403_client.Adapter.userManager_Adapter;
import hoangvhph29660.fpt.edu.asmmob403_client.CallApi.APIServices;
import hoangvhph29660.fpt.edu.asmmob403_client.R;
import hoangvhph29660.fpt.edu.asmmob403_client.model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class user_manager extends Fragment {

    RecyclerView rcv_usermaneger;
    ArrayList<UserModel> listUser;
    userManager_Adapter adapter;
    ProgressBar isLoading_userManager;

    public user_manager() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_manager, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv_usermaneger = view.findViewById(R.id.rcv_usermaneger);
        isLoading_userManager = view.findViewById(R.id.isLoading_userManager);
        getData();
    }


    public void getData(){
                listUser = new ArrayList<>();
                APIServices.apiServices.getAllUser().enqueue(new Callback<ArrayList<UserModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                        if(response.code() == 200){
                            listUser = response.body();
                            adapter = new userManager_Adapter(getContext() , listUser);
                            rcv_usermaneger.setAdapter(adapter);
                            isLoading_userManager.setVisibility(View.INVISIBLE);
                        }



                    }

                    @Override
                    public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {
                        Log.d("errGetAllUser", "onFailure: " +  t);
                    }
                });
    }
}