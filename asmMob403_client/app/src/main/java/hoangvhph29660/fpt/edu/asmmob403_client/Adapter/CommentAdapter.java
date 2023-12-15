package hoangvhph29660.fpt.edu.asmmob403_client.Adapter;




import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;
import hoangvhph29660.fpt.edu.asmmob403_client.Activitis.Deital_comic_user;
import hoangvhph29660.fpt.edu.asmmob403_client.CallApi.APIServices;
import hoangvhph29660.fpt.edu.asmmob403_client.MainActivity;
import hoangvhph29660.fpt.edu.asmmob403_client.R;
import hoangvhph29660.fpt.edu.asmmob403_client.model.UserModel;
import hoangvhph29660.fpt.edu.asmmob403_client.model.cmtModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ComicsViewHolder>{

   Context context;
   ArrayList<cmtModel> listCmt;

   String id_user;
   Deital_comic_user main;

    public CommentAdapter(Context context, ArrayList<cmtModel> listCmt) {
        this.context = context;
        this.listCmt = listCmt;
    }

    @NonNull
    @Override
    public ComicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coment , parent , false);
        return new ComicsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicsViewHolder holder, int position) {
        cmtModel cmt = listCmt.get(position);
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
         id_user = sharedPreferences.getString("id_user", "");

            APIServices.apiServices.getOneUser(cmt.getUserId()).enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    if (response.code() == 200){
                        Glide.with(context).load(MainActivity.urlAvata+response.body().getAvata()).into(holder.avatar_ucm);
                        holder.ten_ucm.setText(response.body().getFullname());
                        holder.time_ucm.setText(PaserDate(cmt.getDateTime()));
                    }
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {

                }
            });
            holder.noidung_ucm.setText(cmt.getContent());
            if (cmt.getUserId().equals(id_user)){
                holder.edit_coment.setVisibility(View.VISIBLE);
            }else {
                holder.edit_coment.setVisibility(View.INVISIBLE);
            }

            holder.edit_coment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDialog(cmt);
                }
            });

            holder.edit_coment.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                        openDialogDelete(cmt);
                    return true;
                }
            });



    }

    @Override
    public int getItemCount() {
        if(listCmt != null){
            return  listCmt.size();
        }
        return 0;
    }

    public static  class ComicsViewHolder extends RecyclerView.ViewHolder{
       CircleImageView avatar_ucm;
       TextView ten_ucm,noidung_ucm,time_ucm;
       ImageView edit_coment;
        public ComicsViewHolder(@NonNull View itemView) {
            super(itemView);

            avatar_ucm = itemView.findViewById(R.id.avatar_ucm);
            ten_ucm = itemView.findViewById(R.id.ten_ucm);
            noidung_ucm = itemView.findViewById(R.id.noidung_ucm);
            time_ucm = itemView.findViewById(R.id.time_ucm);
            edit_coment = itemView.findViewById(R.id.edit_coment);


        }


    }


    public void openDialog(cmtModel cmtupdate){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_path_cmt, null);
        builder.setView(view);


        Button btn_update_dialog = view.findViewById(R.id.btn_update_cmt);
        Button btn_close_cmt = view.findViewById(R.id.btn_close_cmt);
        TextInputEditText edit_cmt_edit = view.findViewById(R.id.edit_cmt_edit);
        TextInputLayout til_cmt_edit = view.findViewById(R.id.til_cmt_edit);
        AlertDialog dialog = builder.create();
        edit_cmt_edit.setText(cmtupdate.getContent());

        btn_close_cmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_update_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   if(edit_cmt_edit.getText().toString().isEmpty()){
                       til_cmt_edit.setError("nhập comment");
                   }else {
                       cmtModel cmt = new cmtModel();
                       cmt.set_id(cmtupdate.get_id());
                       cmt.setUserId(cmtupdate.getUserId());
                       cmt.setComicId(cmtupdate.getComicId());
                       cmt.setContent(edit_cmt_edit.getText().toString());
                       cmt.setDateTime(cmtupdate.getDateTime());
                        APIServices.apiServices.patchComent(cmtupdate.get_id() , cmt).enqueue(new Callback<cmtModel>() {
                            @Override
                            public void onResponse(Call<cmtModel> call, Response<cmtModel> response) {
                                if (response.code() == 200){
                                    ((Deital_comic_user) context).reloadRecyclerViewData();
                                    dialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<cmtModel> call, Throwable t) {

                            }
                        });
                   }
            }
        });

        if (dialog.getWindow() != null){
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }


        dialog.show();
    }

    public void openDialogDelete(cmtModel cmt){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_delete2, null);
        builder.setView(view);

        TextView txt_warning = view.findViewById(R.id.txt_warning);
        TextView txt_title_delete = view.findViewById(R.id.txt_title_delete);
        Button btn_no_dialog = view.findViewById(R.id.btn_no_dialog);
        Button btn_yes_dialog = view.findViewById(R.id.btn_yes_dialog);

        txt_title_delete.setText("DELETE COMENT");
        AlertDialog alertDialog = builder.create();

        btn_no_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btn_yes_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APIServices.apiServices.deleteCmt(cmt.get_id()).enqueue(new Callback<cmtModel>() {
                    @Override
                    public void onResponse(Call<cmtModel> call, Response<cmtModel> response) {
                        if(response.code() == 200){
                            Toast.makeText(context, "Xóa thành công ", Toast.LENGTH_SHORT).show();
                            listCmt.remove(cmt);
                            notifyDataSetChanged();
                            alertDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<cmtModel> call, Throwable t) {
                        Log.d("errDeleteCmt", "onFailure: " + t );
                    }
                });
            }
        });













        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }


    public String PaserDate(String inputDateString) {
        try {
            // Định dạng ngày đầu vào
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            // Định dạng ngày đầu ra với TimeZone của Việt Nam
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());
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
