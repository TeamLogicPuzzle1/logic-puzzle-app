/*package com.cookandroid.test_ui;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.savedstate.SavedStateRegistryOwner;
import android.os.Bundle;

public class SavedStateViewModelFactory extends ViewModelProvider.AbstractSavedStateViewModelFactory {
    private final Application application;

    public SavedStateViewModelFactory(@NonNull Application application, @NonNull SavedStateRegistryOwner owner, Bundle defaultArgs) {
        super(owner, defaultArgs);
        this.application = application;
    }

    @NonNull
    @Override
    protected <T extends ViewModel> T create(@NonNull String key, @NonNull Class<T> modelClass, @NonNull SavedStateHandle handle) {
        if (modelClass.isAssignableFrom(ProductViewModel.class)) {
            return (T) new ProductViewModel(handle);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
} */


