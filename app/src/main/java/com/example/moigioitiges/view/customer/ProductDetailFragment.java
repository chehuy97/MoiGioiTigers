package com.example.moigioitiges.view.customer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moigioitiges.R;
import com.example.moigioitiges.model.ProductDetail;
import com.example.moigioitiges.model.ProductResponse;
import com.example.moigioitiges.remote.ApiService;
import com.example.moigioitiges.remote.ApiUlti;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductDetailFragment extends Fragment {
    Button btnBack;
    TextView tvName,tvCost,tvLocation,tvAcreage,tvStatus;
    ImageView imgProduct;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        imgProduct = (ImageView) view.findViewById(R.id.img_productdetail_image);
        tvName = (TextView) view.findViewById(R.id.tv_productdetail_productname);
        tvCost = (TextView) view.findViewById(R.id.tv_productdetail_productcost);
        tvLocation = (TextView) view.findViewById(R.id.tv_productdetail_productlocation);
        tvAcreage = (TextView) view.findViewById(R.id.tv_productdetail_productacreage);
        tvStatus = (TextView) view.findViewById(R.id.tv_productdetail_productstatus);
        btnBack = (Button) view.findViewById(R.id.btn_producdetail_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new BuyFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        Bundle arguments = getArguments();
        Integer id = arguments.getInt("productID");
        loadProducDetail(id);
        return view;
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

            }
        });
    }
}
