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

import hoangvhph29660.fpt.edu.asmmob403_client.Activitis.Deital_comic;
import hoangvhph29660.fpt.edu.asmmob403_client.Adapter.read_comic_adapter;
import hoangvhph29660.fpt.edu.asmmob403_client.CallApi.APIServices;
import hoangvhph29660.fpt.edu.asmmob403_client.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class read_comic_fragment extends Fragment {
    RecyclerView rcv_readComic;
    read_comic_adapter adapter ;
    ArrayList<String> listImageRead;
    String id_comic;
    Deital_comic deitalComic;
    ProgressBar isloading_readComic;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_read_comic_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv_readComic = view.findViewById(R.id.rcv_readComic);
        isloading_readComic = view.findViewById(R.id.isloading_readComic);
        deitalComic = (Deital_comic) getActivity();
        id_comic = deitalComic.getIdcomic();
        isloading_readComic.setVisibility(View.VISIBLE);
        listImageRead = new ArrayList<>();
        APIServices.apiServices.getReadComic(id_comic).enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                listImageRead = response.body();
                adapter = new read_comic_adapter(view.getContext() , listImageRead);
                rcv_readComic.setAdapter(adapter);
                isloading_readComic.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Log.d("errReadComic", "onFailure: " + t);
            }
        });






    }
}