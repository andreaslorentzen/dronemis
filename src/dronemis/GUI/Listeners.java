package dronemis.GUI;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by AL on 17-02-2016.
 */
public class Listeners {
    private static Listeners ourInstance = new Listeners();

    public static Listeners getInstance() {
        return ourInstance;
    }

    private Listeners() {
    }


    private List<UpdateImageListener> updateFrontImageListener = new LinkedList<>();
    public void addUpdateFrontImageListener(UpdateImageListener updateImageListener) {
        if(!updateFrontImageListener.contains(updateImageListener))
        updateFrontImageListener.add(updateImageListener);
    }

    public List<UpdateImageListener> getUpdateFrontImageListener() {
        return updateFrontImageListener;
    }

    private List<UpdateImageListener> updateBottomImageListener = new LinkedList<>();
    public void addUpdateBottomImageListener(UpdateImageListener updateImageListener) {
        if(!updateBottomImageListener.contains(updateImageListener))
            updateBottomImageListener.add(updateImageListener);
    }

    public List<UpdateImageListener> getUpdateBottomImageListener() {
        return updateBottomImageListener;
    }



    public interface UpdateImageListener {
        void updateImage(BufferedImage image);
    }
}
