/*
 * 간략: 상품 카드뷰 관련 ViewModel
 * 최초 작성자: 홍진기
 * 작성일: 2024-11-02
 * 수정일: 2024-11-04
 * 버전: 0.0.4
 * */
package com.cookandroid.test_ui.mainPage;

// ProductViewModel.java

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

    public void removeProducts(List<Product> productsToRemove) {
        List<Product> updatedList = new ArrayList<>(productList.getValue());
        updatedList.removeAll(productsToRemove); // 삭제할 제품들 제거
        productList.setValue(updatedList); // 변경된 리스트를 LiveData에 반영
    }

    public void setProductList(ArrayList<Product> updatedList) {
        productList.setValue(updatedList);
    }
}
