package hoangvhph29660.fpt.edu.asmmob403_client.fragments;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import hoangvhph29660.fpt.edu.asmmob403_client.Adapter.cateAdaper;
import hoangvhph29660.fpt.edu.asmmob403_client.Adapter.userManager_Adapter;
import hoangvhph29660.fpt.edu.asmmob403_client.CallApi.APIServices;
import hoangvhph29660.fpt.edu.asmmob403_client.R;
import hoangvhph29660.fpt.edu.asmmob403_client.login_atv;
import hoangvhph29660.fpt.edu.asmmob403_client.model.UserModel;
import hoangvhph29660.fpt.edu.asmmob403_client.model.cateModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class catetory_manger extends Fragment {

    RecyclerView rcv_cate_manager;
    ArrayList<cateModel> listCate;
    ArrayList<String> list_id;
    cateAdaper adapter;
    ProgressBar isLoadingCate;
    FloatingActionButton btn_add_cate_manager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catetory_manger, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv_cate_manager = view.findViewById(R.id.rcv_cate_manager);
        isLoadingCate = view.findViewById(R.id.isLoadingCate);
        btn_add_cate_manager = view.findViewById(R.id.btn_add_cate_manager);


        getData();

        btn_add_cate_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                View view = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_path_cate, null);
                builder.setView(view);
                Button btn_update_dialog = view.findViewById(R.id.btn_update_cate);
                Button btn_close_cmt = view.findViewById(R.id.btn_close_cate);
                TextInputEditText edit_cate_edit = view.findViewById(R.id.edit_cate_edit);
                TextInputLayout til_cmt_edit = view.findViewById(R.id.til_cmt_edit);
                btn_update_dialog.setText("thêm");
                edit_cate_edit.setHint("nhập genres");
                AlertDialog dialog = builder.create();
                btn_close_cmt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btn_update_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (edit_cate_edit.getText().toString().isEmpty()) {
                            til_cmt_edit.setError("nhập tên cate");
                        } else {
                            cateModel cateModel = new cateModel();
                            cateModel.setNameCate(edit_cate_edit.getText().toString());
                            list_id = new ArrayList<>();
                            cateModel.setId_comics(list_id);
                        APIServices.apiServices.postCate(cateModel).enqueue(new Callback<hoangvhph29660.fpt.edu.asmmob403_client.model.cateModel>() {
                            @Override
                            public void onResponse(Call<hoangvhph29660.fpt.edu.asmmob403_client.model.cateModel> call, Response<hoangvhph29660.fpt.edu.asmmob403_client.model.cateModel> response) {
                                if (response.code() == 200) {
                                    dialog.dismiss();
                                    getData();
                                    Toast.makeText(v.getContext(), "Thêm thành công ", Toast.LENGTH_SHORT).show();
                                } else if (response.code() == 400) {
                                    try {
                                        // Chuyển đổi body của response thành JSON để lấy thông báo
                                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                        String errorMessage = jsonObject.getString("message");
                                        // Hiển thị thông báo lỗi trong Toast
                                        Toast.makeText(v.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                                    } catch (JSONException | IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<hoangvhph29660.fpt.edu.asmmob403_client.model.cateModel> call, Throwable t) {
                                Toast.makeText(v.getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.d("errLogin", "onFailure: " + t);
                            }
                        });

                        }
                    }
                });
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
            }
        });
    }
    public void resetData(){
        getData();
        adapter.notifyDataSetChanged();
    }

    private void getData() {
        APIServices.apiServices.getAllCate().enqueue(new Callback<ArrayList<cateModel>>() {
            @Override
            public void onResponse(Call<ArrayList<cateModel>> call, Response<ArrayList<cateModel>> response) {
                if(response.code() == 200){
                    listCate = response.body();
                    adapter = new cateAdaper(getContext() , listCate);
                    rcv_cate_manager.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<cateModel>> call, Throwable t) {
                Log.d("getAllCate", "onFailure: " + t);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }
}