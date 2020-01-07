package com.example.moigioitiges.view.broker;


import android.app.Activity;
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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moigioitiges.R;
import com.example.moigioitiges.model.BrokerEditObject;
import com.example.moigioitiges.model.PostEditObject;
import com.example.moigioitiges.model.ProductResponse;
import com.example.moigioitiges.remote.ApiService;
import com.example.moigioitiges.remote.ApiUlti;
import com.example.moigioitiges.view.customer.BuyFragment;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class ProductDetailToBrokerFragment extends Fragment {
    ApiService apiService = ApiUlti.getApiService();
    public final static int PICK_IMAGE_REQUEST = 1;
    public final static int READ_EXTERNAL_REQUEST = 2;
    Uri uri;

    Button btnEdit, btnDelete;
    TextView tvName,tvCost,tvLocation,tvAcreage,tvStatus;
    ImageView imgProduct;
    Integer idProduct;

    ProgressDialog progressDialog;
    String mediaPath;
    ImageView preView;
    String[] mediaColumns = {MediaStore.Video.Media._ID};

    private Dialog dialogEditPost;
    private EditText edtChangeName, edtChangeCost, edtChangeArea, edtChangeLocation;
    private Button btnPickImg, btnCancelChangePost, btnChangePost;

    SharedPreferences sharedPreferences;
    String author;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail_to_broker, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences("PREF", Context.MODE_PRIVATE);
        author = sharedPreferences.getString("accessToken", "");
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Updating...");
        bindingLayout(view);

        Bundle arguments = getArguments();

        idProduct = arguments.getInt("productID");

        setOnclicks();

        loadProducDetail(idProduct);

        return view;
    }

    private void setOnclicks() {
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProduct();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct();
            }
        });
    }

    private void editProduct() {
        dialogEditPost = new Dialog(getActivity());
        dialogEditPost.setContentView(R.layout.dialog_edit_post);
        dialogEditPost.setTitle("Cập nhật thông tin");
        edtChangeName = dialogEditPost.findViewById(R.id.edt_change_name);
        edtChangeLocation = dialogEditPost.findViewById(R.id.edt_change_location);
        edtChangeCost = dialogEditPost.findViewById(R.id.edt_change_cost);
        edtChangeArea = dialogEditPost.findViewById(R.id.edt_change_area);

        edtChangeName.setText(tvName.getText().toString());
        edtChangeArea.setText(tvAcreage.getText().toString());
        edtChangeCost.setText(tvCost.getText().toString());
        edtChangeLocation.setText(tvLocation.getText().toString());

        btnPickImg = dialogEditPost.findViewById(R.id.pick_img);
        preView = dialogEditPost.findViewById(R.id.preview);

        btnChangePost = dialogEditPost.findViewById(R.id.btn_change_post);
        btnCancelChangePost = dialogEditPost.findViewById(R.id.btn_cancel_change_post);

        btnPickImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });
        btnCancelChangePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEditPost.dismiss();
            }
        });

        btnChangePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "is clicked", Toast.LENGTH_LONG).show();
                uploadFile();
                dialogEditPost.dismiss();
                Fragment frg = getActivity().getSupportFragmentManager().findFragmentById(R.id.frame_layout);
                final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.detach(frg);
                ft.attach(frg);
                ft.commit();
            }
        });
        dialogEditPost.show();

    }
    private void deleteProduct() {
        ApiService apiService = ApiUlti.getApiService();
        apiService.deletePost(author,idProduct).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("respone is ", response.code()+"");
                getFragmentManager().popBackStackImmediate();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Fail with status", "onFailure"+ t.getMessage());
            }
        });
    }

    private void loadProducDetail(int id){
        ApiService apiService = ApiUlti.getApiService();
        apiService.getProductDetail(id).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                ProductResponse productDetail = response.body();
                tvName.setText(productDetail.getProductName());
                tvCost.setText(productDetail.getProductCost()+"");
                tvLocation.setText(productDetail.getProductLocation());
                tvAcreage.setText(productDetail.getProductAcreage()+"");
                tvStatus.setText(productDetail.getRequestStatus());
                Picasso.with(getContext())
                        .load(productDetail.getLinkAnh())
                        .into(imgProduct);
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.e("Fail with status", "onFailure"+ t.getMessage());
            }
        });
    }
    private void bindingLayout(View view){
        imgProduct = (ImageView) view.findViewById(R.id.img_productdetail_image);
        tvName = (TextView) view.findViewById(R.id.tv_productdetail_productname);
        tvCost = (TextView) view.findViewById(R.id.tv_productdetail_productcost);
        tvLocation = (TextView) view.findViewById(R.id.tv_productdetail_productlocation);
        tvAcreage = (TextView) view.findViewById(R.id.tv_productdetail_productacreage);
        tvStatus = (TextView) view.findViewById(R.id.tv_productdetail_productstatus);
        btnEdit = (Button) view.findViewById(R.id.btn_edit);
        btnDelete = view.findViewById(R.id.btn_delete);
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

        PostEditObject postEditObject = new PostEditObject();
        postEditObject.setId(idProduct);
        postEditObject.setProductName(edtChangeName.getText().toString());
        postEditObject.setProductLocation(edtChangeLocation.getText().toString());
        postEditObject.setProductCost(Integer.parseInt(edtChangeCost.getText().toString()));
        postEditObject.setProductAcreage(Integer.parseInt(edtChangeArea.getText().toString()));
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), new Gson().toJson(postEditObject));
        Call<String> call = apiService.editPost(author, body, fileToUpload);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("response", response.code()+"");
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Fail with status", "onFailure"+ t.getMessage());
                progressDialog.dismiss();

            }
        });
    }
}
