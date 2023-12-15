package hoangvhph29660.fpt.edu.asmmob403_client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hoangvhph29660.fpt.edu.asmmob403_client.CallApi.APIServices;
import hoangvhph29660.fpt.edu.asmmob403_client.model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signup extends AppCompatActivity {
    Button btn_signup;
    TextInputLayout til_email_register, til_username_register, til_fullname_register;
    TextInputLayout til_repassword_register, til_password_register;

    TextInputEditText txt_email_register, txt_username_register, txt_fullname_register;
    TextInputEditText txt_password_register, txt_repassword_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // anhs xaj ////
        initLayout();
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check trống //////
                if (txt_email_register.getText().toString().isEmpty() || txt_username_register.getText().toString().isEmpty() ||
                        txt_fullname_register.getText().toString().isEmpty() || txt_password_register.getText().toString().isEmpty() ||
                        txt_repassword_register.getText().toString().isEmpty() ||  !isGmailAddress(txt_email_register.getText().toString())||
                        !txt_password_register.getText().toString().equals(txt_repassword_register.getText().toString())
                ) {
                    if (txt_email_register.getText().toString().isEmpty()) {
                        til_email_register.setError("Email không được để trống");
                    } else {
                        til_email_register.setError("");
                    }

                    if (txt_username_register.getText().toString().isEmpty()) {
                        til_username_register.setError(" username không được để trống");
                    } else {
                        til_username_register.setError("");
                    }

                    if (txt_fullname_register.getText().toString().isEmpty()) {
                        til_fullname_register.setError(" fullname  không được để trống");
                    } else {
                        til_fullname_register.setError("");
                    }
                    if (txt_password_register.getText().toString().isEmpty()) {
                        til_fullname_register.setError(" pass không được để trống");
                    } else {
                        til_password_register.setError("");
                    }

                    if (txt_repassword_register.getText().toString().isEmpty()) {
                        til_password_register.setError(" pass không được để trống");
                    } else {
                        til_repassword_register.setError("");
                    }
                    if(!isGmailAddress(txt_email_register.getText().toString())){
                        til_email_register.setError("email phải có @gmail.com");
                    }else {
                        til_email_register.setError("");
                    }

                    if(!txt_password_register.getText().toString().equals(txt_repassword_register.getText().toString())){
                        til_password_register.setError("mật khẩu không khớp !");
                        til_repassword_register.setError("mật khẩu không khớp !");

                    }
                    else {
                        til_password_register.setError("");
                        til_repassword_register.setError("");
                    }


                    ///////////// check pass và repass hợp lệ ////////////
                } else {
                    til_email_register.setError("");
                    til_username_register.setError("");
                    til_fullname_register.setError("");
                    til_password_register.setError("");
                    til_repassword_register.setError("");

                    UserModel userModel = new UserModel();
                    userModel.setEmail(txt_email_register.getText().toString());
                    userModel.setUsername(txt_username_register.getText().toString());
                    userModel.setFullname(txt_fullname_register.getText().toString());
                    userModel.setPassword(txt_password_register.getText().toString());
                    userModel.setRole("user");

                    APIServices.apiServices.PostUser(userModel).enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                            if (response.code() == 200){
                                Toast.makeText(signup.this, "đắng kí thành công !", Toast.LENGTH_SHORT).show();
                                finish();
                            }else if (response.code() == 401  || response.code() == 400 ){
                                try {
                                    // Chuyển đổi body của response thành JSON để lấy thông báo
                                    JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                    String errorMessage = jsonObject.getString("message");

                                    // Hiển thị thông báo lỗi trong Toast
                                    Toast.makeText(signup.this, errorMessage, Toast.LENGTH_SHORT).show();
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }

                            }


                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable t) {

                        }
                    });






                }
            }
        });


    }


    public boolean isGmailAddress(String email) {
        String regex = "^[a-zA-Z0-9_]+@gmail\\.com$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    ///////////check email /////////


    public void initLayout() {
        btn_signup = findViewById(R.id.btn_signup);
        /// text input layout///
        til_email_register = findViewById(R.id.til_email_register);
        til_username_register = findViewById(R.id.til_username_register);
        til_fullname_register = findViewById(R.id.til_fullname_register);
        til_repassword_register = findViewById(R.id.til_repassword_register);
        til_password_register = findViewById(R.id.til_password_register);
        /// edit texxt
        txt_username_register = findViewById(R.id.txt_username_register);
        txt_email_register = findViewById(R.id.txt_email_register);
        txt_fullname_register = findViewById(R.id.txt_fullname_register);
        txt_password_register = findViewById(R.id.txt_password_register);
        txt_repassword_register = findViewById(R.id.txt_repassword_register);
    }


}