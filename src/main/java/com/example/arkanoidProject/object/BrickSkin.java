package com.example.arkanoidProject.object;

import javafx.scene.image.Image;

public enum BrickSkin {
    BRICK1(new String[]{
            "/com/example/arkanoidProject/view/images/brick/brick1.png"
//            "/com/example/arkanoidProject/view/images/brick1_crack1.png",
//            "/com/example/arkanoidProject/view/images/brick1_crack2.png"
    }),

    BRICK2(new String[]{
            "/com/example/arkanoidProject/view/images/brick/brick2.png",
            "/com/example/arkanoidProject/view/images/brick/brick2_crack1.png"
//            "/com/example/arkanoidProject/view/images/brick2_crack2.png"
    }),

    BRICK3(new String[]{
            "/com/example/arkanoidProject/view/images/brick/brick3.png",
            "/com/example/arkanoidProject/view/images/brick/brick3_crack1.png",
            "/com/example/arkanoidProject/view/images/brick/brick3_crack2.png"
    });

    private final String[] texturePaths;

    BrickSkin(String[] texturePaths) {
        this.texturePaths = texturePaths;
    }

    public Image getTexture(int damageTaken) {
        int index = Math.min(damageTaken, texturePaths.length - 1);
        return new Image(getClass().getResource(texturePaths[index]).toExternalForm());
    }

    public int getMaxState() {
        return texturePaths.length - 1;
    }
}
