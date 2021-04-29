package client.gui;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * Author: Dhruv Rajpurohit
 * This class is for having custom pokemon buttons
 */
public class PokeButtons extends Button {
    private int index;
    private ImageView pic;

    /**
     *
     * @param pic the picture
     */
    public PokeButtons(int index, ImageView pic) {
        this.index = index;

        this.setGraphic(pic);

    }


    public ImageView getPic() {
        return pic;
    }

    public void setPic(ImageView pic) {
        this.setGraphic(pic);
    }



    public void setIndx(int index) {
        this.index = index;
    }

    public int getIndx() {
        return index;
    }


}