package com.example.moigioitiges.view.customer;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.moigioitiges.R;
import com.example.moigioitiges.model.Catalog;
import com.example.moigioitiges.model.CatalogDetail;
import com.example.moigioitiges.model.CityIndex;
import com.example.moigioitiges.model.DistrictIndex;
import com.example.moigioitiges.model.WardIndex;
import com.example.moigioitiges.remote.ApiService;
import com.example.moigioitiges.remote.ApiUlti;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {
    private Spinner spnCity, spnDistrict, spnWard, spnCatalog,spnTypeCatalog;
    private Button btnSearch;
    private String zipCode = "";
    private TextView tvZipcode;
    private String numberCity,numberDistrict,numberWard;
    private int indexCity,indexDistrict,indexWard,indexCatalog;
    int numberTypeCatalog;

    // private String cityIndex,dixtricIndex,wardIndex;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        btnSearch = (Button) view.findViewById(R.id.btn_Search_search);
        tvZipcode = (TextView) view.findViewById(R.id.tv_Search_zipcode);
        spnCity = (Spinner) view.findViewById(R.id.spnCity);
        spnDistrict = (Spinner) view.findViewById(R.id.spnDictrict);
        spnWard = (Spinner) view.findViewById(R.id.spnWard);
        spnCatalog = (Spinner) view.findViewById(R.id.spnCatalog);
        spnTypeCatalog = (Spinner) view.findViewById(R.id.spnTypeCatalog);
            loadCityIndex();
        //tvZipcode.setText(zipCode);
        return view;
    }

    private void loadCityIndex() {
        final List<CityIndex> listCity = new ArrayList<>();
        CityIndex ci = new CityIndex(0,"Chọn tỉnh,thành phố");
        listCity.add(ci);
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
                spnCity.setAdapter(adapter);
                spnCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                        indexCity = listCity.get(i).getId();
                        loadDisTrictIndex(indexCity);
                        numberCity =  listCity.get(i).getMaCity();
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
        DistrictIndex di = new DistrictIndex(0,"Chọn quận huyện");
        listDistrict.add(di);
        if (indexCity == 0){
            ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listDistrict);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            spnDistrict.setAdapter(adapter);
            spnDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                    indexDistrict = listDistrict.get(i).getId();
                    numberDistrict = listDistrict.get(i).getMaDistrict();
                    // zipCode = zipCode+numberDistrict;
                    loadWardIndex(indexDistrict);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        else {
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
                    spnDistrict.setAdapter(adapter);
                    spnDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                            indexDistrict = listDistrict.get(i).getId();
                            numberDistrict = listDistrict.get(i).getMaDistrict();
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
    }

    private void loadWardIndex(int indexDistrict) {
        final List<WardIndex> listWard = new ArrayList<>();
        WardIndex wi = new WardIndex(0,"Chọn phường,xã");
        listWard.add(wi);
        if (indexDistrict==0){
            ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listWard);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            spnWard.setAdapter(adapter);
            spnWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                    indexWard = listWard.get(i).getId();
                    numberWard = listWard.get(i).getMaWard();
                    //zipCode = zipCode+numberWard;
                    loadListCatalog();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        else {
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
                    spnWard.setAdapter(adapter);
                    spnWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                            indexWard = listWard.get(i).getId();
                            numberWard = listWard.get(i).getMaWard();
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
    }

    private void loadListCatalog() {
        final List<Catalog> listCatalog = new ArrayList<>();
        Catalog ci = new Catalog(0,"Chọn loại bất động sản");
        listCatalog.add(ci);
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
                spnCatalog.setAdapter(adapter);
                spnCatalog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                        indexCatalog = listCatalog.get(i).getId();
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
        CatalogDetail cdi = new CatalogDetail(0,"Chọn loại bất động sản chi tiết");
        listTypeCatalog.add(cdi);
        if(indexCatalog==0){
            ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listTypeCatalog);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            spnTypeCatalog.setAdapter(adapter);
            spnTypeCatalog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                    numberTypeCatalog = listTypeCatalog.get(i).getId();
                    String tmp = "";
                    if (indexCity == 0) {
                        tmp = "";
                    } else if (indexCity != 0 && indexDistrict == 0) {
                        tmp = numberCity;
                    } else if (indexCity != 0 && indexDistrict != 0 && indexWard == 0) {
                        tmp = numberCity + numberDistrict;
                    } else if (indexCity != 0 && indexDistrict != 0 && indexWard != 0) {
                        tmp = numberCity + numberDistrict + numberWard;
                    }
                    zipCode = tmp;
                    tvZipcode.setText(zipCode);
                    btnSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SearchResult searchResult = new SearchResult();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout, searchResult);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            Bundle bundle = new Bundle();
                            bundle.putString("ZipCode", zipCode);
                            bundle.putInt("TypeCatalog", numberTypeCatalog);
                            searchResult.setArguments(bundle);
                        }
                    });

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        else {
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
                    spnTypeCatalog.setAdapter(adapter);
                    spnTypeCatalog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                            numberTypeCatalog = listTypeCatalog.get(i).getId();
                            String tmp = "";
                            if (indexCity == 0) {
                                tmp = "";
                            } else if (indexCity != 0 && indexDistrict == 0) {
                                tmp = numberCity;
                            } else if (indexCity != 0 && indexDistrict != 0 && indexWard == 0) {
                                tmp = numberCity + numberDistrict;
                            } else if (indexCity != 0 && indexDistrict != 0 && indexWard != 0) {
                                tmp = numberCity + numberDistrict + numberWard;
                            }
                            zipCode = tmp;
                            tvZipcode.setText(zipCode);
                            btnSearch.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SearchResult searchResult = new SearchResult();
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    transaction.replace(R.id.frame_layout, searchResult);
                                    transaction.addToBackStack(null);
                                    transaction.commit();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("ZipCode", zipCode);
                                    bundle.putInt("TypeCatalog", numberTypeCatalog);
                                    searchResult.setArguments(bundle);
                                }
                            });

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
    }
}