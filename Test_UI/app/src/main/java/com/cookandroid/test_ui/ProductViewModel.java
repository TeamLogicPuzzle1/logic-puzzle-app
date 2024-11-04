package com.cookandroid.test_ui;

// ProductViewModel.java
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class ProductViewModel extends ViewModel {
    private final MutableLiveData<List<Product>> productList = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<Product>> getProductList() {
        return productList;
    }

    public void addProduct(Product product) {
        List<Product> currentList = productList.getValue();
        if (currentList != null) {
            currentList.add(product);
            productList.setValue(currentList); // 데이터 변경 후 옵저버가 감지
        }
    }

    public void restoreProductList(List<Product> products) {
        productList.setValue(products);
    }
}
