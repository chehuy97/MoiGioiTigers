package com.example.moigioitiges.view.broker;

import android.app.Activity;
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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.moigioitiges.R;
import com.example.moigioitiges.model.BrokerEditObject;
import com.example.moigioitiges.model.Catalog;
import com.example.moigioitiges.model.CatalogDetail;
import com.example.moigioitiges.model.CityIndex;
import com.example.moigioitiges.model.Currency;
import com.example.moigioitiges.model.DistrictIndex;
import com.example.moigioitiges.model.Owner;
import com.example.moigioitiges.model.RequestNewPost;
import com.example.moigioitiges.model.WardIndex;
import com.example.moigioitiges.remote.ApiService;
import com.example.moigioitiges.remote.ApiUlti;
import com.example.moigioitiges.view.customer.SearchResult;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class AddPostFragment extends Fragment {
    ApiService apiService = ApiUlti.getApiService();
    public final static int PICK_IMAGE_REQUEST = 1;
    public final static int READ_EXTERNAL_REQUEST = 2;
    Uri uri;

    ProgressDialog progressDialog;
    String mediaPath;
    private ImageView preView;
    String[] mediaColumns = {MediaStore.Video.Media._ID};

    private EditText edt_Title, edt_Description,edt_Area, edt_Cost, edt_ID_Owner;
    private Spinner sp_Currency, sp_Province, sp_District, sp_Ward, sp_Catalog, sp_CatalogDetail,sp_owner;
    private Button btn_ImageUpload, btn_AddNewPost;
    String zipcode,location;
    int IDCatalogDetail,IDCurrency,IDOwner;
    private String locationCity,locationDistrict,locationWard;
    private String numberCity,numberDistrict,numberWard;

    SharedPreferences sharedPreferences;
    String author;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_post, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences("PREF", Context.MODE_PRIVATE);
        author = sharedPreferences.getString("accessToken", "");
        bindingLayout(view);
        loadCurrency();
        return view;
    }

    private void setOnclick(final int idcurrency, final String zipcode, final String location, final int idCD, final int idOwner) {
        btn_ImageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });
        btn_AddNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "is clicked", Toast.LENGTH_LONG).show();
                addNewPost(idcurrency,zipcode,location,idCD,idOwner);
                FragmentManager fm;
                fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                ft.replace(R.id.frame_layout, new PendingPostFragment());
                ft.commit();
            }
        });
    }
    private void addNewPost(int idcurrency, String zipcode, String location, int idCD, int idOwner){
        progressDialog.show();
        File file = new File(mediaPath);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        /*BrokerEditObject brokerEditObject = new BrokerEditObject();
        brokerEditObject.setDateOfBirth(edt_ChangeDateOfBirth.getText().toString());
        brokerEditObject.setGender(rbtn_Male.isChecked() ? true : false);
        brokerEditObject.setNameBroker(edt_ChangeName.getText().toString());
        brokerEditObject.setPhoneNumber(edt_ChangePhoneNumber.getText().toString());
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), new Gson().toJson(brokerEditObject));*/
        RequestNewPost requestNewPost = new RequestNewPost();
        requestNewPost.setProductName(edt_Title.getText().toString());
        requestNewPost.setProductDescription(edt_Description.getText().toString());
        requestNewPost.setCurrencyId(idcurrency);
        requestNewPost.setZipCode(zipcode);
        requestNewPost.setOwnerId(idOwner);
        requestNewPost.setCatalogDetailId(idCD);
        requestNewPost.setProductAcreage(Integer.parseInt(edt_Area.getText().toString()));
        requestNewPost.setProductCost(Integer.parseInt(edt_Cost.getText().toString()));
        requestNewPost.setProductLocation(numberWard);
        Log.e("name: ",requestNewPost.getProductName());
        Log.e("Description: ",requestNewPost.getProductDescription());
        Log.e("Currency: ",requestNewPost.getCurrencyId()+"");
        Log.e("Zipcode: ",requestNewPost.getZipCode());
        Log.e("OwnerID: ",requestNewPost.getOwnerId()+"");
        Log.e("CatalogDetailID: ",requestNewPost.getCatalogDetailId()+"");
        Log.e("Acreage: ",requestNewPost.getProductAcreage()+"");
        Log.e("Cost: ",requestNewPost.getProductCost()+"");
        Log.e("Location: ",requestNewPost.getProductLocation());
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), new Gson().toJson(requestNewPost));
        Call<String> call = apiService.addNewPost(author, body, fileToUpload);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    Log.v("Response", response.body());
                    progressDialog.dismiss();
                }
                else{
                    Log.e("error", response.code()+"");
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Fail: ","onFailure");
                progressDialog.dismiss();
            }
        });
    }
    private void loadCurrency() {
        final List<Currency> listCurrency = new ArrayList<>();
        ApiService apiService = ApiUlti.getApiService();
        apiService.getListCurrency(author).enqueue(new Callback<List<Currency>>() {
            @Override
            public void onResponse(Call<List<Currency>> call, Response<List<Currency>> response) {
                for (Currency c : response.body()) {
                    Currency c2 = new Currency(c);
                    listCurrency.add(c2);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listCurrency);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                sp_Currency.setAdapter(adapter);
                sp_Currency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                        IDCurrency = listCurrency.get(i).getId();
                        loadCityIndex();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<Currency>> call, Throwable t) {

            }
        });
    }
    private void loadCityIndex() {
        final List<CityIndex> listCity = new ArrayList<>();
        ApiService apiService = ApiUlti.getApiService();
        apiService.getListCity().enqueue(new Callback<List<CityIndex>>() {
            @Override
            public void onResponse(Call<List<CityIndex>> call, Response<List<CityIndex>> response) {
                for (CityIndex c : response.body()) {
                    CityIndex c2 = new CityIndex(c);
                    listCity.add(c2);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listCity);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                sp_Province.setAdapter(adapter);
                sp_Province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                        int indexCity = listCity.get(i).getId();
                        loadDisTrictIndex(indexCity);
                        numberCity =  listCity.get(i).getMaCity();
                        locationCity = listCity.get(i).getNameCity();
                        // zipCode = zipCode +numberCity;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<CityIndex>> call, Throwable t) {

            }
        });
    }

    private void loadDisTrictIndex(int indexCity) {
        final List<DistrictIndex> listDistrict = new ArrayList<>();
        ApiService apiService = ApiUlti.getApiService();
        apiService.getListDistrict(indexCity).enqueue(new Callback<List<DistrictIndex>>() {
            @Override
            public void onResponse(Call<List<DistrictIndex>> call, Response<List<DistrictIndex>> response) {
                for (DistrictIndex d : response.body()) {
                    DistrictIndex d2 = new DistrictIndex(d);
                    listDistrict.add(d2);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listDistrict);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                sp_District.setAdapter(adapter);
                sp_District.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                        int indexDistrict = listDistrict.get(i).getId();
                        numberDistrict = listDistrict.get(i).getMaDistrict();
                        locationDistrict = listDistrict.get(i).getNameDistrict();
                        // zipCode = zipCode+numberDistrict;
                        loadWardIndex(indexDistrict);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<DistrictIndex>> call, Throwable t) {

            }
        });
    }

    private void loadWardIndex(int indexDistrict) {
        final List<WardIndex> listWard = new ArrayList<>();
        ApiService apiService = ApiUlti.getApiService();
        apiService.getListWard(indexDistrict).enqueue(new Callback<List<WardIndex>>() {
            @Override
            public void onResponse(Call<List<WardIndex>> call, Response<List<WardIndex>> response) {
                for (WardIndex w : response.body()) {
                    WardIndex w2 = new WardIndex(w);
                    listWard.add(w2);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listWard);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                sp_Ward.setAdapter(adapter);
                sp_Ward.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                        numberWard = listWard.get(i).getMaWard();
                        locationWard = listWard.get(i).getNameWard();
                        zipcode = numberCity+numberDistrict+numberWard;
                        location = locationCity+"-"+locationDistrict+"-"+locationWard;
                        //zipCode = zipCode+numberWard;
                        loadListCatalog();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<WardIndex>> call, Throwable t) {

            }
        });
    }

    private void loadListCatalog() {
        final List<Catalog> listCatalog = new ArrayList<>();
        ApiService apiService = ApiUlti.getApiService();
        apiService.getListCatalog().enqueue(new Callback<List<Catalog>>() {
            @Override
            public void onResponse(Call<List<Catalog>> call, Response<List<Catalog>> response) {
                for (Catalog c : response.body()) {
                    Catalog c2 = new Catalog(c);
                    listCatalog.add(c2);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listCatalog);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                sp_Catalog.setAdapter(adapter);
                sp_Catalog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                        int indexCatalog = listCatalog.get(i).getId();
                        loadListTypeCatalog(indexCatalog);
                        //zipCode = zipCode +numberCatalog;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<Catalog>> call, Throwable t) {

            }
        });
    }

    private void loadListTypeCatalog(int indexCatalog) {
        final List<CatalogDetail> listTypeCatalog = new ArrayList<>();
        ApiService apiService = ApiUlti.getApiService();
        apiService.getListTypeCatalog(indexCatalog).enqueue(new Callback<List<CatalogDetail>>() {
            @Override
            public void onResponse(Call<List<CatalogDetail>> call, Response<List<CatalogDetail>> response) {
                for (CatalogDetail t : response.body()) {
                    CatalogDetail t2 = new CatalogDetail(t);
                    listTypeCatalog.add(t2);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listTypeCatalog);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                sp_CatalogDetail.setAdapter(adapter);
                sp_CatalogDetail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                        IDCatalogDetail = listTypeCatalog.get(i).getId();
                        loadListOwner();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<CatalogDetail>> call, Throwable t) {

            }
        });
    }
    private void loadListOwner() {
        final List<Owner> listOwner = new ArrayList<>();
        ApiService apiService = ApiUlti.getApiService();
        apiService.getListOwner(author).enqueue(new Callback<List<Owner>>() {
            @Override
            public void onResponse(Call<List<Owner>> call, Response<List<Owner>> response) {
                for (Owner o : response.body()) {
                    Owner o2 = new Owner(o);
                    listOwner.add(o2);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listOwner);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                sp_owner.setAdapter(adapter);
                sp_owner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                        IDOwner = listOwner.get(i).getId();
                        setOnclick(IDCurrency,zipcode,location,IDCatalogDetail,IDOwner);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<Owner>> call, Throwable t) {

            }
        });
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

    private void bindingLayout(View view) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Updating...");
        edt_Title = view.findViewById(R.id.edt_title);
        edt_Description = view.findViewById(R.id.edt_description);
        edt_Area = view.findViewById(R.id.edt_area);
        edt_Cost = view.findViewById(R.id.edt_cost);
        sp_Currency = view.findViewById(R.id.sp_id_currency);
        sp_Province = view.findViewById(R.id.sp_province);
        sp_District = view.findViewById(R.id.sp_district);
        sp_Ward = view.findViewById(R.id.sp_ward);
        sp_Catalog = view.findViewById(R.id.sp_catalog);
        sp_CatalogDetail = view.findViewById(R.id.sp_catalog_detail);
        sp_owner = view.findViewById(R.id.sp_owner);
        btn_ImageUpload = view.findViewById(R.id.btn_upload_image);
        btn_AddNewPost = view.findViewById(R.id.btn_add_new_post);
        preView = view.findViewById(R.id.iv_preview);
    }

}
