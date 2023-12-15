package hoangvhph29660.fpt.edu.asmmob403_client.Activitis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import hoangvhph29660.fpt.edu.asmmob403_client.Adapter.CommentAdapter;
import hoangvhph29660.fpt.edu.asmmob403_client.CallApi.APIServices;
import hoangvhph29660.fpt.edu.asmmob403_client.CallApi.paserDate;
import hoangvhph29660.fpt.edu.asmmob403_client.MainActivity;
import hoangvhph29660.fpt.edu.asmmob403_client.R;
import hoangvhph29660.fpt.edu.asmmob403_client.login_atv;
import hoangvhph29660.fpt.edu.asmmob403_client.model.cateModel;
import hoangvhph29660.fpt.edu.asmmob403_client.model.cmtModel;
import hoangvhph29660.fpt.edu.asmmob403_client.model.comicModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Deital_comic_user extends AppCompatActivity {
    ImageButton btn_close_deital;
    Toolbar toolbar_deital_comic;
    ImageView btn_sent_comment, img_coverImage;
    ProgressBar isLoading_deital;
    Button btn_read_comic;
    TextView txt_name_comic_deital, txt_author, txt_date, txt_genres, txt_des_deital_comic;
    RecyclerView rcv_comment_deital_comic;
    ArrayList<cmtModel> arrayListCmt;
    EditText edt_cmt_deital_comic;
    String idComic , id_user;

    CommentAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deital_comic_user);
        isLoading_deital = findViewById(R.id.isLoading_deital);
        img_coverImage = findViewById(R.id.img_coverImage);
        btn_read_comic = findViewById(R.id.btn_read_comic);
        toolbar_deital_comic = findViewById(R.id.toolbar_deital_comic);
        btn_close_deital = toolbar_deital_comic.findViewById(R.id.btn_close_deital);
        txt_name_comic_deital = toolbar_deital_comic.findViewById(R.id.txt_name_comic_deital);
        txt_author = findViewById(R.id.txt_author);
        txt_date = findViewById(R.id.txt_date);
        txt_des_deital_comic = findViewById(R.id.txt_des_deital_comic);
        txt_genres = findViewById(R.id.txt_genres);
        rcv_comment_deital_comic = findViewById(R.id.rcv_comment_deital_comic);
        edt_cmt_deital_comic = findViewById(R.id.edt_cmt_deital_comic);
        btn_sent_comment = findViewById(R.id.btn_sent_comment);

        SharedPreferences sharedPreferences = Deital_comic_user.this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        id_user = sharedPreferences.getString("id_user", "");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idComic = extras.getString("id_comic");
            Log.d("id_comic", "onCreate: " + idComic);
            // Bây giờ bạn có thể sử dụng Idcomic theo cách bạn muốn
        }
        //////////////btn đóng //////////////
        btn_close_deital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ///////////////btn đọc chuyện////////////
        btn_read_comic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Deital_comic_user.this , readComic.class);
                intent.putExtra("id_read_comic" , idComic);
                startActivity(intent);
            }
        });

        btn_sent_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_cmt_deital_comic.getText().toString().isEmpty()){

                }else {
                    cmtModel cmt = new cmtModel();
                    cmt.setUserId(id_user);
                    cmt.setComicId(idComic);
                    cmt.setContent(edt_cmt_deital_comic.getText().toString());
                    APIServices.apiServices.postComent(cmt).enqueue(new Callback<cmtModel>() {
                        @Override
                        public void onResponse(Call<cmtModel> call, Response<cmtModel> response) {
                            if(response.code() == 200){
                                Toast.makeText(Deital_comic_user.this, "Thêm cmt thành công ", Toast.LENGTH_SHORT).show();
                                edt_cmt_deital_comic.setText("");
                                getDataCmt();

                            } else if (response.code() == 400) {
                                try {
                                    // Chuyển đổi body của response thành JSON để lấy thông báo
                                    JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                    String errorMessage = jsonObject.getString("message");
                                    // Hiển thị thông báo lỗi trong Toast
                                    Toast.makeText(Deital_comic_user.this, errorMessage, Toast.LENGTH_SHORT).show();
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<cmtModel> call, Throwable t) {

                        }
                    });
                   
                }
            }
        });
        //////////////get dữ liệu /////////////////////////
        APIServices.apiServices.getOneComic(idComic).enqueue(new Callback<comicModel>() {
            @Override
            public void onResponse(Call<comicModel> call, Response<comicModel> response) {
                if (response.code() == 200) {
                    Glide.with(Deital_comic_user.this)
                            .load(MainActivity.urlCover + response.body().getCoverImage())
                            .into(img_coverImage);
                    txt_name_comic_deital.setText(response.body().getNameComic());
                    txt_author.setText("Author : " + response.body().getAuthor());

                    Log.d("dateComic", "onResponse: " + response.body().getDateRelease());
                    String date = PaserDate(response.body().getDateRelease());
                    txt_date.setText("DateRelease : " + date);
                    getCate(response.body().getId_category());
                    txt_des_deital_comic.setText(response.body().getDescription());

                    isLoading_deital.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onFailure(Call<comicModel> call, Throwable t) {

            }
        });

        /// lấy cmt /////////////////
        getDataCmt();




    }

    public void getCate(String id) {
        APIServices.apiServices.getOneCate(id).enqueue(new Callback<cateModel>() {
            @Override
            public void onResponse(Call<cateModel> call, Response<cateModel> response) {
                if (response.code() == 200) {
                    txt_genres.setText("Genres : " + response.body().getNameCate());
                } else if (response.code() == 400) {
                    try {
                        // Chuyển đổi body của response thành JSON để lấy thông báo
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String errorMessage = jsonObject.getString("message");
                        // Hiển thị thông báo lỗi trong Toast
                        Toast.makeText(Deital_comic_user.this, errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onFailure(Call<cateModel> call, Throwable t) {
                Log.d("errGetCateByid", "onFailure: " + t);
            }
        });
    }

    public void reloadRecyclerViewData() {
        getDataCmt();
        adapter.notifyDataSetChanged();
    }

    public  void getDataCmt(){
        APIServices.apiServices.getCmtByid(idComic).enqueue(new Callback<ArrayList<cmtModel>>() {
            @Override
            public void onResponse(Call<ArrayList<cmtModel>> call, Response<ArrayList<cmtModel>> response) {
                arrayListCmt = response.body();
                adapter = new CommentAdapter(Deital_comic_user.this , arrayListCmt);
                rcv_comment_deital_comic.setLayoutManager(new LinearLayoutManager(Deital_comic_user.this));
                rcv_comment_deital_comic.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<ArrayList<cmtModel>> call, Throwable t) {

            }
        });
    }

    public String getIdComic() {
        return idComic;
    }


    public String PaserDate(String inputDateString) {
        try {
            // Định dạng ngày đầu vào
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            // Định dạng ngày đầu ra với TimeZone của Việt Nam
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            outputFormat.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

            // Chuyển đổi ngày từ chuỗi sang đối tượng Date
            Date date = inputFormat.parse(inputDateString);

            // Chuyển đổi đối tượng Date sang chuỗi ngày theo định dạng của Việt Nam
            String outputDateString = outputFormat.format(date);

            return outputDateString;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return inputDateString;
    }


}