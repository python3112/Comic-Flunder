package hoangvhph29660.fpt.edu.asmmob403_client.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import hoangvhph29660.fpt.edu.asmmob403_client.Adapter.cate_home_Adapter;
import hoangvhph29660.fpt.edu.asmmob403_client.Adapter.comic_home_Adapter;
import hoangvhph29660.fpt.edu.asmmob403_client.CallApi.APIServices;
import hoangvhph29660.fpt.edu.asmmob403_client.MainActivity;
import hoangvhph29660.fpt.edu.asmmob403_client.R;
import hoangvhph29660.fpt.edu.asmmob403_client.model.cateModel;
import hoangvhph29660.fpt.edu.asmmob403_client.model.comicModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_home extends Fragment {
    String urlimg = "http://192.168.1.89:3000/uploads/coverImage/";
    RecyclerView rcv_cate_home , rcv_comic_home;
    ArrayList<cateModel> arrayListCate;
    ArrayList<comicModel> arrayListComic;
    cate_home_Adapter adapter ;

    comic_home_Adapter adapterComic;
    ViewFlipper ViewFlipper;
    private MainActivity mMainActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
        APIServices.apiServices.getSlideComic().enqueue(new Callback<ArrayList<comicModel>>() {
            @Override
            public void onResponse(Call<ArrayList<comicModel>> call, Response<ArrayList<comicModel>> response) {
                if (response.code() == 200){
                    for(comicModel comic :  response.body()){
                        ImageView imageView = new ImageView(getContext());
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        Glide.with(getContext()).load(MainActivity.urlCover+comic.getCoverImage()).into(imageView);
                        ViewFlipper.addView(imageView);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<comicModel>> call, Throwable t) {

            }
        });
        ViewFlipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
        ViewFlipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);

        // Optional: Bắt đầu tự động cuộn
        ViewFlipper.setAutoStart(true);
        ViewFlipper.setFlipInterval(3000); // Đặt thời gian giữa các chuyển động (3 giây trong trường hợp này)
        ViewFlipper.startFlipping();
        getData();
        getDatacomic();









    }

    public void initUi(View view){
        rcv_cate_home = view.findViewById(R.id.rcv_cate_home);
        rcv_comic_home = view.findViewById(R.id.rcv_comic_home);
        ViewFlipper = view.findViewById(R.id.viewFlip);

    }

    public  void getData(){
        arrayListCate = new ArrayList<>();
        APIServices.apiServices.getCatelimit().enqueue(new Callback<ArrayList<cateModel>>() {
            @Override
            public void onResponse(Call<ArrayList<cateModel>> call, Response<ArrayList<cateModel>> response) {
                if (response.code() == 200){
                    arrayListCate = response.body();
                    adapter = new cate_home_Adapter(getContext() , arrayListCate);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext() , RecyclerView.HORIZONTAL , false);
                    rcv_cate_home.setLayoutManager(linearLayoutManager);
                    rcv_cate_home.setAdapter(adapter);

                } else if (response.code() == 400) {
                    Toast.makeText(getContext(), "có lỗi khi lấy cate home", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<cateModel>> call, Throwable t) {
                Toast.makeText(getContext(), ""+ t, Toast.LENGTH_SHORT).show();
                Log.d("zzz", "onFailure: ");
            }
        });
    }

    public  void getDatacomic(){
        APIServices.apiServices.getRamdomComic().enqueue(new Callback<ArrayList<comicModel>>() {
            @Override
            public void onResponse(Call<ArrayList<comicModel>> call, Response<ArrayList<comicModel>> response) {
                if (response.code() == 200){
                    arrayListComic = response.body();
                    adapterComic = new comic_home_Adapter(getContext() , arrayListComic);
                    rcv_comic_home.setLayoutManager(new GridLayoutManager(getContext() , 3));
                    rcv_comic_home.setAdapter(adapterComic);
                }

            }

            @Override
            public void onFailure(Call<ArrayList<comicModel>> call, Throwable t) {
                Log.d("errGetComic_home", "onFailure: ." + t);
            }
        });

    }




    

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }
}