package com.example.moigioitiges.view.broker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moigioitiges.R;
import com.example.moigioitiges.model.Broker;
import com.example.moigioitiges.model.BrokerEditObject;
import com.example.moigioitiges.model.RequestChangePass;
import com.example.moigioitiges.remote.ApiService;
import com.example.moigioitiges.remote.ApiUlti;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class AccountFragment extends Fragment {
    ApiService apiService = ApiUlti.getApiService();
    public final static int PICK_IMAGE_REQUEST = 1;
    public final static int READ_EXTERNAL_REQUEST = 2;
    Uri uri;

    ProgressDialog progressDialog;
    String mediaPath;
    ImageView preView;
    String[] mediaColumns = {MediaStore.Video.Media._ID};

    private ImageView iv_avatar;
    private TextView tv_email, tv_name, tv_address, tv_phoneNumber, tv_gender, tv_birthday;
    private Button btn_update_info, btn_change_pass;

    private Dialog dlg_ChangePass;
    private EditText edt_CurrentPass, edt_NewPass, edt_ConfirmNewPass;
    private Button btn_ChangePass, btn_CancelChangePass;

    private Dialog dialog_UpdateProfile;
    private EditText edt_ChangeName, edt_ChangePhoneNumber, edt_ChangeDateOfBirth,edt_ChangeAddress;
    private Button btn_ChangeAvatar, btn_CancelChangeProfile, btn_ChangeProfile;
    private RadioButton rbtn_Male;

    SharedPreferences sharedPreferences;
    String author;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences("PREF", Context.MODE_PRIVATE);
        author = sharedPreferences.getString("accessToken", "");
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Updating...");
        iv_avatar = view.findViewById(R.id.iv_avatar);
        tv_email = view.findViewById(R.id.tv_email);
        tv_name = view.findViewById(R.id.tv_name);
        tv_address = view.findViewById(R.id.tv_address);
        tv_phoneNumber = view.findViewById(R.id.tv_phoneNumber);
        tv_gender = view.findViewById(R.id.tv_gender);
        tv_birthday = view.findViewById(R.id.tv_birthday);
        btn_change_pass = view.findViewById(R.id.btn_change_pass);
        btn_update_info = view.findViewById(R.id.btn_update_info);
        loadProfile();
        btn_update_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_UpdateProfile = new Dialog(getActivity());
                dialog_UpdateProfile.setContentView(R.layout.dialog_update_profile);
                dialog_UpdateProfile.setTitle("Cập nhật thông tin cá nhân");
                edt_ChangeAddress = dialog_UpdateProfile.findViewById(R.id.edt_change_address);
                edt_ChangeDateOfBirth = dialog_UpdateProfile.findViewById(R.id.edt_change_dateOfBirth);
                edt_ChangeName = dialog_UpdateProfile.findViewById(R.id.edt_change_name);
                edt_ChangePhoneNumber = dialog_UpdateProfile.findViewById(R.id.edt_change_phone_number);
                rbtn_Male = dialog_UpdateProfile.findViewById(R.id.radioMale);
                btn_ChangeAvatar = dialog_UpdateProfile.findViewById(R.id.pick_img);
                preView = dialog_UpdateProfile.findViewById(R.id.preview);
                btn_ChangeProfile = dialog_UpdateProfile.findViewById(R.id.btn_change_profile);
                btn_CancelChangeProfile = dialog_UpdateProfile.findViewById(R.id.btn_cancel_change_profile);
                edt_ChangeName.setText(tv_name.getText().toString());
                edt_ChangeAddress.setText(tv_address.getText().toString());
                edt_ChangeDateOfBirth.setText(tv_birthday.getText().toString());
                edt_ChangePhoneNumber.setText(tv_phoneNumber.getText().toString());
                dialog_UpdateProfile.show();
                edt_ChangeDateOfBirth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ChonNgay();
                    }
                });
                btn_ChangeAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, 0);
                    }
                });
                btn_CancelChangeProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_UpdateProfile.dismiss();
                    }
                });

                btn_ChangeProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "is clicked", Toast.LENGTH_LONG).show();
                        uploadFile();
                        dialog_UpdateProfile.dismiss();
                        Fragment frg = getActivity().getSupportFragmentManager().findFragmentById(R.id.frame_layout);
                        final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.detach(frg);
                        ft.attach(frg);
                        ft.commit();
                    }
                });
            }
        });
        btn_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg_ChangePass = new Dialog(getActivity());
                dlg_ChangePass.setContentView(R.layout.dialog_change_pass);
                dlg_ChangePass.setTitle("THAY ĐỔI MẬT KHẨU");
                edt_CurrentPass = dlg_ChangePass.findViewById(R.id.edt_current_pass);
                edt_NewPass = dlg_ChangePass.findViewById(R.id.edt_new_pass);
                edt_ConfirmNewPass = dlg_ChangePass.findViewById(R.id.edt_confirm_pass);
                btn_ChangePass = dlg_ChangePass.findViewById(R.id.btn_change_pass);
                btn_CancelChangePass = dlg_ChangePass.findViewById(R.id.btn_cancel_change_pass);
                dlg_ChangePass.show();
                btn_CancelChangePass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dlg_ChangePass.dismiss();
                    }
                });
                btn_ChangePass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "is cilcked", Toast.LENGTH_LONG).show();
                        changePass();
                        dlg_ChangePass.dismiss();
                        Fragment frg = getActivity().getSupportFragmentManager().findFragmentById(R.id.frame_layout);
                        final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.detach(frg);
                        ft.attach(frg);
                        ft.commit();
                    }
                });
            }
        });

        return view;
    }

    private void loadProfile() {
        apiService.getBrokerDetail(author).enqueue(new Callback<Broker>() {
            @Override
            public void onResponse(Call<Broker> call, Response<Broker> response) {
                if (response.isSuccessful()) {
                    Broker broker = response.body();
                    Picasso.with(getContext())
                            .load(broker.getLinkImageProfile())
                            .into(iv_avatar);
                    tv_name.setText(broker.getNameBroker());
                    tv_email.setText(broker.getEmail());
                    tv_address.setText(broker.getAddress());
                    tv_phoneNumber.setText(broker.getPhoneNumber());
                    if (broker.getGender()) tv_gender.setText("Nam");
                    else tv_gender.setText("Nữ");
                    tv_birthday.setText(broker.getDateOfBirth());
                } else {
                    Log.e("Fail with status", response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<Broker> call, Throwable t) {
                Log.e("Fail with status", "onFailure"+ t.getMessage());
            }
        });

    }

    private void ChonNgay() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                edt_ChangeDateOfBirth.setText(dateFormat.format(calendar.getTime()));
            }
        }, 1990, 01, 01);
        datePickerDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);

                // Set the Image in ImageView for Previewing the Media
                preView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();

            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    // Providing Thumbnail For Selected Image
    public Bitmap getThumbnailPathForLocalFile(Activity context, Uri fileUri) {
        long fileId = getFileId(context, fileUri);
        return MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(),
                fileId, MediaStore.Video.Thumbnails.MICRO_KIND, null);
    }

    // Getting Selected File ID
    public long getFileId(Activity context, Uri fileUri) {
        Cursor cursor = context.managedQuery(fileUri, mediaColumns, null, null, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
            return cursor.getInt(columnIndex);
        }
        return 0;
    }

    // Uploading Image/Video
    private void uploadFile() {
        progressDialog.show();
        MultipartBody.Part fileToUpload = null;
        if(mediaPath!=null) {
            File file = new File(mediaPath);
            // Parsing any Media type file
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
            fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        }
        BrokerEditObject brokerEditObject = new BrokerEditObject();

        brokerEditObject.setDateOfBirth(edt_ChangeDateOfBirth.getText().toString());
        brokerEditObject.setGender(rbtn_Male.isChecked() ? true : false);
        brokerEditObject.setAddress(edt_ChangeAddress.getText().toString());
        brokerEditObject.setNameBroker(edt_ChangeName.getText().toString());
        brokerEditObject.setPhoneNumber(edt_ChangePhoneNumber.getText().toString());
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), new Gson().toJson(brokerEditObject));
        Call<String> call = apiService.editBroker(author, body, fileToUpload);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Fail with status", "onFailure"+ t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void changePass() {
        RequestChangePass requestChangePass = new RequestChangePass();
        requestChangePass.setPasswordCurrent(edt_CurrentPass.getText().toString());
        requestChangePass.setNewPassword(edt_NewPass.getText().toString());
        requestChangePass.setNewMatchingPassword(edt_ConfirmNewPass.getText().toString());

        apiService.updatePassword(author, requestChangePass).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("response", response.toString());
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), response.body(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Fail with status", "onFailure"+ t.getMessage());
            }
        });
    }
}
