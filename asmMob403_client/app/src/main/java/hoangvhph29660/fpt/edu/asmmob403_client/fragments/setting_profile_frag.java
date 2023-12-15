package hoangvhph29660.fpt.edu.asmmob403_client.fragments;

import static android.app.Activity.RESULT_OK;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.Result;

import de.hdodenhof.circleimageview.CircleImageView;
import hoangvhph29660.fpt.edu.asmmob403_client.CallApi.APIServices;

import hoangvhph29660.fpt.edu.asmmob403_client.CallApi.RealPathUtil;
import hoangvhph29660.fpt.edu.asmmob403_client.MainActivity;

import hoangvhph29660.fpt.edu.asmmob403_client.R;
import hoangvhph29660.fpt.edu.asmmob403_client.login_atv;
import hoangvhph29660.fpt.edu.asmmob403_client.model.UserModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class setting_profile_frag extends Fragment {
    private static final int REQUEST_CODE_PERMISSION = 1;

    CircleImageView user_avata_setting;
    TextInputLayout til_username_setting, til_fullname_setting, til_email_setting;

    TextInputLayout til_password_setting, til_repassword_setting;
    TextInputEditText txt_username_setting, txt_fullname_setting, txt_email_setting;
    TextInputEditText txt_password_setting, txt_repassword_setting;
    ImageButton btn_camera;
    Button btn_update_setting;
    TextView txt_id_user_Setting;
    ProgressBar isloading;
    private static final int PICK_IMAGE_REQUEST = 1;
    Uri urlImage;
    MainActivity mainActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting_profile_frag, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initUi(view);
        mainActivity = (MainActivity) getActivity();
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String id_user = sharedPreferences.getString("id_user", "");
        String roleUser = sharedPreferences.getString("role", "");
        Log.d("zzzz", "onCreate: " + id_user + "_" + roleUser);
        getData(view.getContext(), id_user);


        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.	READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
//                        // Nếu không có quyền, yêu cầu quyền từ người dùng
//                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
//                    } else {
//                        // Nếu đã có quyền, tiến hành công việc của bạn
//                        openGallery();
//                    }
//                } else {
//                    // Đối với Android 9 (Pie) và thấp hơn
//                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                        // Nếu không có quyền, yêu cầu quyền từ người dùng
//                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
//                    } else {
//                        // Nếu đã có quyền, tiến hành công việc của bạn
//                        openGallery();
//                    }
//                }
                openGallery();
            }


        });


        btn_update_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_email_setting.getText().toString().isEmpty() || txt_fullname_setting.getText().toString().isEmpty() ||
                        txt_password_setting.getText().toString().isEmpty() || txt_repassword_setting.getText().toString().isEmpty() ||
                        !isGmailAddress(txt_email_setting.getText().toString()) ||
                        !txt_password_setting.getText().toString().equals(txt_repassword_setting.getText().toString())
                ) {
                    if (txt_email_setting.getText().toString().isEmpty()) {
                        til_email_setting.setError("Email không được để trống");
                    } else {
                        til_email_setting.setError("");
                    }

                    if (txt_fullname_setting.getText().toString().isEmpty()) {
                        til_fullname_setting.setError(" full không được để trống");
                    } else {
                        til_fullname_setting.setError("");
                    }

                    if (txt_password_setting.getText().toString().isEmpty()) {
                        til_password_setting.setError(" pass không được để trống");
                    } else {
                        til_password_setting.setError("");
                    }

                    if (txt_repassword_setting.getText().toString().isEmpty()) {
                        til_repassword_setting.setError(" pass không được để trống");
                    } else {
                        til_repassword_setting.setError("");
                    }
                    if (!isGmailAddress(txt_email_setting.getText().toString())) {
                        til_email_setting.setError("email phải có @gmail.com");
                    } else {
                        til_email_setting.setError("");
                    }

                    if (!txt_password_setting.getText().toString().equals(txt_repassword_setting.getText().toString())) {
                        til_password_setting.setError("mật khẩu không khớp !");
                        til_repassword_setting.setError("mật khẩu không khớp !");

                    } else {
                        til_password_setting.setError("");
                        til_repassword_setting.setError("");
                    }


                    ///////////// check pass và repass hợp lệ ////////////
                } else {
                    til_email_setting.setError("");
                    til_fullname_setting.setError("");
                    til_password_setting.setError("");
                    til_repassword_setting.setError("");


                    if (urlImage != null) {
                        isloading.setVisibility(View.VISIBLE);
                        RequestBody usernameBody = RequestBody.create(MediaType.parse("multipart/form-data"), txt_username_setting.getText().toString().trim());
                        RequestBody passwordBody = RequestBody.create(MediaType.parse("multipart/form-data"), txt_password_setting.getText().toString().trim());
                        RequestBody emailBody = RequestBody.create(MediaType.parse("multipart/form-data"), txt_email_setting.getText().toString().trim());
                        RequestBody fullnameBody = RequestBody.create(MediaType.parse("multipart/form-data"), txt_fullname_setting.getText().toString().trim());
                        RequestBody roleBody = RequestBody.create(MediaType.parse("multipart/form-data"), roleUser);

                        String stringrRealPath = RealPathUtil.getRealPath(getContext(), urlImage);
                        Log.d("fileString", "updateUser: " + stringrRealPath);
                        File file = new File(stringrRealPath);
                        RequestBody requestBodyAvata = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        MultipartBody.Part part = MultipartBody.Part.createFormData("avata", file.getName(), requestBodyAvata);

                        APIServices.apiServices.patchUser(id_user, usernameBody, passwordBody, emailBody, fullnameBody, roleBody, part).enqueue(new Callback<UserModel>() {
                            @Override
                            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                                if (response.code() == 200) {
                                    getData(getContext(), id_user);
                                        mainActivity.getNav_fulluser().setText(txt_fullname_setting.getText().toString());
                                        mainActivity.getNav_email().setText(txt_email_setting.getText().toString());




                                        String urlnewAvata = MainActivity.urlAvata+txt_username_setting.getText().toString()+"-"+file.getName();
                                    Log.d("img2", "onResponse: "+urlnewAvata);
                                        Glide.with(getContext()).load(urlnewAvata).into(mainActivity.getAvataUser());
                                    isloading.setVisibility(View.INVISIBLE);
                                }


                            }

                            @Override
                            public void onFailure(Call<UserModel> call, Throwable t) {
                                isloading.setVisibility(View.INVISIBLE);
                                Log.d("errPatchUser", "onFailure: " + t);
                            }
                        });
                    } else {
                        isloading.setVisibility(View.VISIBLE);
                        RequestBody usernameBody = RequestBody.create(MediaType.parse("multipart/form-data"), txt_username_setting.getText().toString().trim());
                        RequestBody passwordBody = RequestBody.create(MediaType.parse("multipart/form-data"), txt_password_setting.getText().toString().trim());
                        RequestBody emailBody = RequestBody.create(MediaType.parse("multipart/form-data"), txt_email_setting.getText().toString().trim());
                        RequestBody fullnameBody = RequestBody.create(MediaType.parse("multipart/form-data"), txt_fullname_setting.getText().toString().trim());
                        RequestBody roleBody = RequestBody.create(MediaType.parse("multipart/form-data"), roleUser);

                        APIServices.apiServices.patchUserNoImg(id_user, usernameBody, passwordBody, emailBody, fullnameBody, roleBody).enqueue(new Callback<UserModel>() {
                            @Override
                            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                                if (response.code() == 200) {
                                    getData(getContext(), id_user);
                                    mainActivity.getNav_fulluser().setText(txt_fullname_setting.getText().toString());
                                    mainActivity.getNav_email().setText(txt_email_setting.getText().toString());
                                    isloading.setVisibility(View.INVISIBLE);

                                } else if (response.code() == 400) {
                                    try {
                                        // Chuyển đổi body của response thành JSON để lấy thông báo
                                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                        String errorMessage = jsonObject.getString("message");
                                        // Hiển thị thông báo lỗi trong Toast
                                        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
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
            }
        });


    }


    public void getData(Context context, String id_user) {

        APIServices.apiServices.getOneUser(id_user).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.code() == 200) {
                    Glide.with(getContext()).load(MainActivity.urlAvata + response.body().getAvata()).into(user_avata_setting);
                    txt_id_user_Setting.setText(response.body().get_id());
                    txt_username_setting.setText(response.body().getUsername());
                    txt_fullname_setting.setText(response.body().getFullname());
                    txt_email_setting.setText(response.body().getEmail());
                    txt_password_setting.setText(response.body().getPassword());
                    txt_repassword_setting.setText(response.body().getPassword());
                    isloading.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("errGetInfo", "onFailure: " + t);
            }
        });
    }

    public void updateUser(String userId, String username, String password,
                           String email, String fullname, String role, MultipartBody.Part avatar) {
        isloading.setVisibility(View.VISIBLE);
        RequestBody usernameBody = RequestBody.create(MediaType.parse("multipart/form-data"), username);
        RequestBody passwordBody = RequestBody.create(MediaType.parse("multipart/form-data"), password);
        RequestBody emailBody = RequestBody.create(MediaType.parse("multipart/form-data"), email);
        RequestBody fullnameBody = RequestBody.create(MediaType.parse("multipart/form-data"), fullname);
        RequestBody roleBody = RequestBody.create(MediaType.parse("multipart/form-data"), role);

        String stringrRealPath = RealPathUtil.getRealPath(getContext(), urlImage);
        Log.d("fileString", "updateUser: " + stringrRealPath);
        File file = new File(stringrRealPath);
        RequestBody requestBodyAvata = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("avata", file.getName(), requestBodyAvata);

        APIServices.apiServices.patchUser(userId, usernameBody, passwordBody, emailBody, fullnameBody, roleBody, part).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.code() == 200) {
                    getData(getContext(), userId);
                    mainActivity.getUser();
                    urlImage = null;
                    isloading.setVisibility(View.INVISIBLE);
                }


            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                isloading.setVisibility(View.INVISIBLE);
                Log.d("errPatchUser", "onFailure: " + t);
            }
        });
    }

    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }


    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {

            urlImage = data.getData();
            Log.d("avata", "onActivityResult: " + urlImage);
            Glide.with(getContext()).load(urlImage).into(user_avata_setting);

        }
    }

    public boolean isGmailAddress(String email) {
        String regex = "^[a-zA-Z0-9_]+@gmail\\.com$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền được cấp, thực hiện công việc của bạn
               openGallery();
            } else {
                return;
            }
        }
    }

    public void initUi(View view) {
        user_avata_setting = view.findViewById(R.id.user_avata_setting);
        til_fullname_setting = view.findViewById(R.id.til_fullname_setting);
        til_username_setting = view.findViewById(R.id.til_username_setting);
        til_email_setting = view.findViewById(R.id.til_email_setting);
        til_password_setting = view.findViewById(R.id.til_password_setting);
        til_repassword_setting = view.findViewById(R.id.til_repassword_setting);
        txt_username_setting = view.findViewById(R.id.txt_username_setting);
        txt_fullname_setting = view.findViewById(R.id.txt_fullname_setting);
        txt_email_setting = view.findViewById(R.id.txt_email_setting);
        txt_password_setting = view.findViewById(R.id.txt_password_setting);
        txt_repassword_setting = view.findViewById(R.id.txt_repassword_setting);
        btn_camera = view.findViewById(R.id.btn_camera);
        btn_update_setting = view.findViewById(R.id.btn_update_setting);
        txt_id_user_Setting = view.findViewById(R.id.txt_id_user_Setting);
        isloading = view.findViewById(R.id.isLoading_setting);
    }
}