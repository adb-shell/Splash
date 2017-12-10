package com.karthik.splash.Components;

import com.karthik.splash.Modules.PhotoDetailModule;
import com.karthik.splash.Views.PhotoDetail;

import dagger.Subcomponent;

/**
 * Created by karthikrk on 09/12/17.
 */

@Subcomponent(modules = {PhotoDetailModule.class})
public interface PhotoDetailComponent {
    void inject(PhotoDetail photoDetail);
}
