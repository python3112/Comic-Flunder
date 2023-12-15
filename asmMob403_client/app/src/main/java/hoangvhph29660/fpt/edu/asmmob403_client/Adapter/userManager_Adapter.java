package hoangvhph29660.fpt.edu.asmmob403_client.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import hoangvhph29660.fpt.edu.asmmob403_client.CallApi.APIServices;
import hoangvhph29660.fpt.edu.asmmob403_client.MainActivity;
import hoangvhph29660.fpt.edu.asmmob403_client.R;
import hoangvhph29660.fpt.edu.asmmob403_client.login_atv;
import hoangvhph29660.fpt.edu.asmmob403_client.model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class userManager_Adapter extends RecyclerView.Adapter<userManager_Adapter.userManagerHolder> {
    String urlimg = "http://192.168.1.89:3000/uploads/avata/";
    Context context;
    ArrayList<UserModel> userModelList;

    public userManager_Adapter(Context context, ArrayList<UserModel> userModelList) {
        this.context = context;
        this.userModelList = userModelList;
    }

    @Override
    public userManagerHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usermanager , parent , false);
        return new userManagerHolder(view);
    }

    @Override
    public void onBindViewHolder( userManagerHolder holder, int position) {
            UserModel userModel = userModelList.get(position);
             Glide.with(context).load(MainActivity.urlAvata +userModel.getAvata()).into(holder.img_Avata_userManager);
             holder.user_manager_username.setText(userModel.getUsername());
             holder.user_manager_fullname.setText(userModel.getFullname());
            holder.user_manager_email.setText(userModel.getEmail());
            holder.user_manager_role.setText(userModel.getRole());

           holder.img_delete.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   AlertDialog.Builder  builder = new AlertDialog.Builder(v.getContext());
                   View view = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_delete2 , null);

                   builder.setView(view);

                   TextView txt_warning = view.findViewById(R.id.txt_warning);
                   Button btn_no_dialog = view.findViewById(R.id.btn_no_dialog);
                   Button btn_yes_dialog = view.findViewById(R.id.btn_yes_dialog);
                    txt_warning.setText("Are you sure delete "+ userModel.get_id()+ " ?");

                   AlertDialog dialog = builder.create();

                    btn_no_dialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    btn_yes_dialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            APIServices.apiServices.deletUser(userModel.get_id()).enqueue(new Callback<UserModel>() {
                                @Override
                                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                                    if (response.code() == 200){
                                        userModelList.remove(userModel);
                                        notifyDataSetChanged();
                                        dialog.dismiss();
                                    }else if(response.code() == 400){
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
                                public void onFailure(Call<UserModel> call, Throwable t) {
                                    Log.d("errDeleteUser", "onFailure: " + t);
                                }
                            });
                        }
                    });
                    if (dialog.getWindow() != null){
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }



                   dialog.show();
               }
           });


           holder.layout_item_user.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Toast.makeText(context, ""+userModel.getEmail(), Toast.LENGTH_SHORT).show();
               }
           });


    }

    @Override
    public int getItemCount() {
        if(userModelList != null){
            return userModelList.size();
        }
        return 0;
    }

    public  class userManagerHolder extends RecyclerView.ViewHolder{
        CardView layout_item_user;
        CircleImageView img_Avata_userManager;
        TextView user_manager_username,  user_manager_fullname , user_manager_email, user_manager_role;
        ImageButton img_delete;
        public userManagerHolder(@NonNull View itemView) {
            super(itemView);
            layout_item_user = itemView.findViewById(R.id.layout_item_user);
            img_Avata_userManager = itemView.findViewById(R.id.img_Avata_userManager);
            user_manager_username = itemView.findViewById(R.id.user_manager_username);
            user_manager_fullname = itemView.findViewById(R.id.user_manager_fullname);
            user_manager_email = itemView.findViewById(R.id.user_manager_email);
            user_manager_role = itemView.findViewById(R.id.user_manager_role);
            img_delete = itemView.findViewById(R.id.img_delete);

        }
    }
}
