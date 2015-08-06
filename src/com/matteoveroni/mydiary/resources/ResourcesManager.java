package com.matteoveroni.mydiary.resources;

import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Matteo Veroni
 */
public class ResourcesManager{

    List<Disposable> disposableResources = new ArrayList<>();
    
    public void addResource(Disposable resource){
        disposableResources.add(resource);
    }

    public void disposeAll() {
        for(Disposable disposableResource : disposableResources){
            disposableResource.dispose();
        }
        disposableResources.clear();
    }
        
}
