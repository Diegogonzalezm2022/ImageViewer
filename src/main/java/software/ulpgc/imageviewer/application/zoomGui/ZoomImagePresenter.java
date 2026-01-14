package software.ulpgc.imageviewer.application.zoomGui;

import software.ulpgc.imageviewer.architecture.Image;
import software.ulpgc.imageviewer.architecture.ImageDisplay;
import software.ulpgc.imageviewer.architecture.ImageDisplay.ZoomPaint;
import software.ulpgc.imageviewer.architecture.ImagePresenter;

public class ZoomImagePresenter extends ImagePresenter {
    private final ImageDisplay display;
    private Image image;

    public ZoomImagePresenter(ImageDisplay display) {
        super(display);
        this.display = display;
        this.display.on((ImageDisplay.Shift) offset -> {
            display.zoomPaint(
                    new ZoomPaint(image.bitmap(), offset, 1),
                    new ZoomPaint(
                            offset < 0 ? image.next().bitmap() : image.previous().bitmap(),
                            offset < 0 ? display.width() + offset : offset - display.width(),1));
        });
        this.display.on((ImageDisplay.Released) offset -> {
            if (Math.abs(offset) * 2 > display.width()) image = offset < 0 ? image.next() : image.previous();
            System.out.println(image.id());
            display.zoomPaint(new ZoomPaint(image.bitmap(), 0, 1));
        });
        this.display.on((ImageDisplay.Zoom) zoomLevel -> {
            display.zoomPaint(
                    new ZoomPaint(image.bitmap(), 0, zoomLevel/100)
            );
        });
    }

    private void released(int offset) {

    }

    public void show(Image image) {
        this.image = image;
        this.display.zoomPaint(new ImageDisplay.ZoomPaint(image.bitmap(), 0, 1));
    }

    public Image image() {
        return image;
    }

}
