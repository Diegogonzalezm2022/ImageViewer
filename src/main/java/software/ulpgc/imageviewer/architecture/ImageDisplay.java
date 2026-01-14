package software.ulpgc.imageviewer.architecture;

public interface ImageDisplay {

    void on(Shift shift);
    void on(Released released);
    void on(Zoom zoom);

    void paint(Paint... paints);
    void zoomPaint(ZoomPaint... paints);

    int width();

    void changeZoom(int i);

    interface Shift {
        void offset(int value);
    }

    interface Released {
        void offset(int value);
    }

    interface Zoom {
        void zoomLevel(double value);
    }

    public record Paint(byte[] bitmap, int offset) {}
    public record ZoomPaint(byte[] bitmap, int offset, double zoomLevel) {}
}
