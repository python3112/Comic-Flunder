package hoangvhph29660.fpt.edu.asmmob403_client.Adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;


import hoangvhph29660.fpt.edu.asmmob403_client.CallApi.APIServices;
import hoangvhph29660.fpt.edu.asmmob403_client.R;
import hoangvhph29660.fpt.edu.asmmob403_client.fragments.catetory_manger;
import hoangvhph29660.fpt.edu.asmmob403_client.model.cateModel;
import hoangvhph29660.fpt.edu.asmmob403_client.model.cmtModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cateAdaper extends RecyclerView.Adapter<cateAdaper.cateHolder> {

    Context context;
    ArrayList<cateModel> listCate;

    public cateAdaper(Context context, ArrayList<cateModel> listCate) {
        this.context = context;
        this.listCate = listCate;
    }

    @NonNull
    @Override
    public cateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cate_manger, parent, false);

        return new cateHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cateHolder holder, int position) {
        cateModel cateModel = listCate.get(position);
        holder.id_cate.setText("Id : " + cateModel.get_id());
        holder.name_cate.setText("Name : " + cateModel.getNameCate());

        holder.btn_edit_cate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("dialog", "openDialog: ");
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                View view = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_path_cate, null);
                builder.setView(view);
                Button btn_update_dialog = view.findViewById(R.id.btn_update_cate);
                Button btn_close_cmt = view.findViewById(R.id.btn_close_cate);
                TextInputEditText edit_cate_edit = view.findViewById(R.id.edit_cate_edit);
                TextInputLayout til_cmt_edit = view.findViewById(R.id.til_cmt_edit);

                edit_cate_edit.setText(cateModel.getNameCate());
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
                            cateModel cmt = new cateModel();
                            cmt.set_id(cateModel.get_id());
                            cmt.setNameCate(edit_cate_edit.getText().toString());
                            APIServices.apiServices.patchCate(cateModel.get_id(), cmt).enqueue(new Callback<cateModel>() {
                                @Override
                                public void onResponse(Call<cateModel> call, Response<cateModel> response) {
                                    if (response.code() == 200) {
                                        Toast.makeText(context, "sửa thành công", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }

                                @Override
                                public void onFailure(Call<cateModel> call, Throwable t) {

                                }
                            });


//                            if (dialog.getWindow() != null) {
//                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//                            }



                        }
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listCate != null) {
            return listCate.size();
        }
        return 0;
    }

    public class cateHolder extends RecyclerView.ViewHolder {

        TextView id_cate, name_cate;

        ImageButton btn_edit_cate;

        public cateHolder(@NonNull View itemView) {
            super(itemView);

            id_cate = itemView.findViewById(R.id.id_cate);
            name_cate = itemView.findViewById(R.id.name_cate);
            btn_edit_cate = itemView.findViewById(R.id.btn_edit_cate);


        }
    }

    public void openDialog(cateModel cate) {



    }


}