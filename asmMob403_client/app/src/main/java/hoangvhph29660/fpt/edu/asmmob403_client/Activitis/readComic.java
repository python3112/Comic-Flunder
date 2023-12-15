package hoangvhph29660.fpt.edu.asmmob403_client.Activitis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import hoangvhph29660.fpt.edu.asmmob403_client.Adapter.read_comic_adapter;
import hoangvhph29660.fpt.edu.asmmob403_client.CallApi.APIServices;
import hoangvhph29660.fpt.edu.asmmob403_client.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class readComic extends AppCompatActivity {

    RecyclerView rcv_readComic;
    read_comic_adapter adapter ;
    ArrayList<String> listImageRead;
    ImageView close_read_atv;
    ProgressBar isloading_readComic;
    String idComic_read;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_comic);
        initUi();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idComic_read = extras.getString("id_read_comic");
            Log.d("id_comic", "onCreate: " + idComic_read);
            // Bây giờ bạn có thể sử dụng Idcomic theo cách bạn muốn
        }
        close_read_atv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        APIServices.apiServices.getReadComic(idComic_read).enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                if (response.code() == 200){
                    listImageRead = response.body();
                    adapter = new read_comic_adapter(readComic.this , listImageRead);
                    rcv_readComic.setAdapter(adapter);
                    isloading_readComic.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Log.d("errReadComicAtv", "onFailure: " + t );
            }
        });



    }

    public  void initUi(){
        rcv_readComic  = findViewById(R.id.rcv_readComic_atv);
        isloading_readComic = findViewById(R.id.isloading_readComic_atv);
        close_read_atv = findViewById(R.id.close_read_atv);
    }
}