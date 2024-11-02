package com.cookandroid.test_ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductViewModel extends ViewModel {
    private static final String PRODUCT_LIST_KEY = "product_list";
    private final SavedStateHandle savedStateHandle;
    private final MutableLiveData<List<Product>> productList;


    public ProductViewModel(SavedStateHandle savedStateHandle) {
        this.savedStateHandle = savedStateHandle;

        List<Product> initalList = savedStateHandle.get(PRODUCT_LIST_KEY);
        if(initalList == null) {
            initalList = new ArrayList<>();
            savedStateHandle.set(PRODUCT_LIST_KEY, initalList);
        }
        this.productList = new MutableLiveData<>(initalList);
    }

    public LiveData<List<Product>> getProductList() {
        return productList;
    }
    public void addProduct(Product product) {
        List<Product> currentList = productList.getValue();
        if(currentList != null) {
            currentList.add(product);
            productList.setValue(currentList);
            savedStateHandle.set(PRODUCT_LIST_KEY, currentList);
        }
    }


}
