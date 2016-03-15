package dronemis.GUI;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Listeners {
    private static Listeners ourInstance = new Listeners();

    public static Listeners getInstance() {
        return ourInstance;
    }

    private Listeners() {
    }

    private List<UpdateImageListener> updateFrontImageListeners = new LinkedList<>();

    public void addUpdateFrontImageListener(UpdateImageListener updateImageListener) {
        if (!updateFrontImageListeners.contains(updateImageListener))
            updateFrontImageListeners.add(updateImageListener);
    }

    public void updateImageFront(BufferedImage newImage) {
        for (Listeners.UpdateImageListener object : updateFrontImageListeners) {
            object.updateImage(newImage);
        }
    }


    private List<UpdateImageListener> updateBottomImageListeners = new LinkedList<>();

    public void addUpdateBottomImageListener(UpdateImageListener updateImageListener) {
        if (!updateBottomImageListeners.contains(updateImageListener))
            updateBottomImageListeners.add(updateImageListener);
    }

    public void updateImageBottom(BufferedImage newImage) {
        for (Listeners.UpdateImageListener object : updateBottomImageListeners) {
            object.updateImage(newImage);
        }
    }


    private Map<String, List<UpdateImageListener>> imageListeners = new HashMap<>();
    public void addImageListener(String label, Listeners.UpdateImageListener listener){
        if(!imageListeners.containsKey(label)){
            imageListeners.put(label, new LinkedList<>());
        }
        imageListeners.get(label).add(listener);
    }
    public boolean updateImageListener(String label, BufferedImage image){
        if(imageListeners.containsKey(label)){
            for (Listeners.UpdateImageListener listener : imageListeners.get(label)) {
                listener.updateImage(image);
            }
            return true;
        }
        return false;
    }

    public interface UpdateImageListener {
        void updateImage(BufferedImage image);
    }
}
