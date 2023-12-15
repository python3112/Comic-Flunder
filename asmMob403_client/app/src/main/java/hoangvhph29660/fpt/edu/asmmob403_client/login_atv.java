package hoangvhph29660.fpt.edu.asmmob403_client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import hoangvhph29660.fpt.edu.asmmob403_client.CallApi.APIServices;
import hoangvhph29660.fpt.edu.asmmob403_client.model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login_atv extends AppCompatActivity {
    TextInputLayout til_username_login, til_password_login;
    TextInputEditText txt_username_login, txt_password_login;
    Button btn_login;
    TextView text_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_atv);

        til_username_login = findViewById(R.id.til_username_login);
        til_password_login = findViewById(R.id.til_password_login);
        txt_username_login = findViewById(R.id.txt_username_login);
        txt_password_login = findViewById(R.id.txt_password_login);
        btn_login = findViewById(R.id.btn_login);
        text_signup = findViewById(R.id.text_signup);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (txt_username_login.getText().toString().isEmpty() || txt_password_login.getText().toString().isEmpty()) {
                    if (txt_username_login.getText().toString().isEmpty()) {
                        til_username_login.setError("không được để trống");
                    } else {
                        til_username_login.setError("");
                    }

                    if (txt_password_login.getText().toString().isEmpty()) {
                        til_password_login.setError("không được để trống");
                    } else {
                        til_password_login.setError("");
                    }


                } else {
                    til_username_login.setError("");
                    til_password_login.setError("");
                    APIServices.apiServices.loginUser(txt_username_login.getText().toString(), txt_password_login.getText().toString()).enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                            if (response.code() == 200) {
                                Intent intent = new Intent(login_atv.this, MainActivity.class);
                                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("id_user" , response.body().get_id());
                                editor.putString("role" , response.body().getRole());
                                editor.apply();
                                startActivity(intent);
                                txt_username_login.setText("");
                                txt_password_login.setText("");
                            } else if (response.code() == 400) {
                                try {
                                    // Chuyển đổi body của response thành JSON để lấy thông báo
                                    JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                    String errorMessage = jsonObject.getString("message");
                                    // Hiển thị thông báo lỗi trong Toast
                                    Toast.makeText(login_atv.this, errorMessage, Toast.LENGTH_SHORT).show();
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }


                            }

                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable t) {
                            Toast.makeText(login_atv.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("errLogin", "onFailure: " + t);
                        }
                    });


                }
            }


        });

        text_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_atv.this, signup.class);
                startActivity(intent);
            }
        });
    }


}
