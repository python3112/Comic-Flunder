package hoangvhph29660.fpt.edu.asmmob403_client.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import hoangvhph29660.fpt.edu.asmmob403_client.Adapter.cate_home_Adapter;
import hoangvhph29660.fpt.edu.asmmob403_client.CallApi.APIServices;
import hoangvhph29660.fpt.edu.asmmob403_client.R;
import hoangvhph29660.fpt.edu.asmmob403_client.model.cateModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class list_cate_Fragment extends Fragment {
    RecyclerView rcv_cate_listCate;
    ArrayList<cateModel> arrayListCate;
    cate_home_Adapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_cate_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv_cate_listCate = view.findViewById(R.id.rcv_cate_listCate);
        arrayListCate = new ArrayList<>();
        APIServices.apiServices.getAllCate().enqueue(new Callback<ArrayList<cateModel>>() {
            @Override
            public void onResponse(Call<ArrayList<cateModel>> call, Response<ArrayList<cateModel>> response) {
                if(response.code() == 200){
                    arrayListCate = response.body();
                    Log.d("listcate", "onResponse: "+ response.body());
                    adapter = new cate_home_Adapter(getContext() , arrayListCate);
                    rcv_cate_listCate.setLayoutManager(new GridLayoutManager(getContext() , 4));
                    rcv_cate_listCate.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<ArrayList<cateModel>> call, Throwable t) {
                Toast.makeText(getContext(), ""+t, Toast.LENGTH_SHORT).show();
                Log.d("getCATE", "onFailure: "+ t);
            }
        });


    }
}