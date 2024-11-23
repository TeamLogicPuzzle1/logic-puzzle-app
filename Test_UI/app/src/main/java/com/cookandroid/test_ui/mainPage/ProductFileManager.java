/*
 * 간략: 내부 저장소 파일 생성
 * 최초 작성자: 홍진기
 * 작성일: 2024-11-04
 * 버전: 0.0.1
 * */
package com.cookandroid.test_ui.mainPage;

import static java.lang.Character.getType;

import android.content.Context;

import com.cookandroid.test_ui.DTO.request.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProductFileManager {
    private File file;

    public ProductFileManager(Context context) {
        // 앱의 내부 저장소에 products.json 파일을 생성
        file = new File(context.getFilesDir(), "products.json");
    }

    // 재품 목록을 Json 파일로 저장하는 메서드
    public void saveProductList(List<Product> productList) {
        Gson gson = new Gson();
        String json = gson.toJson(productList);

        try (FileWriter writer = new FileWriter(file)){
            writer.write(json);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    // JSON 파일에서 제품 목록을 불러오는 메서드
    public ArrayList<Product> loadProductList() {
        if(!file.exists()) {
            return new ArrayList<>(); // 파일이 없을 경우 빈 리스트 반환
        }
        try(FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Product>>() {}.getType();
            return gson.fromJson(reader, type);
        }catch(IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
