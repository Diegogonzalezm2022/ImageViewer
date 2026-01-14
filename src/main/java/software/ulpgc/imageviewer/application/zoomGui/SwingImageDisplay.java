package software.ulpgc.imageviewer.application.zoomGui;

import software.ulpgc.imageviewer.architecture.Canvas;
import software.ulpgc.imageviewer.architecture.ImageDisplay;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SwingImageDisplay extends JPanel implements ImageDisplay {
    private Shift shift;
    private Released released;
    private Paint[] paints;
    private ZoomPaint[] zoomPaints;
    private Zoom zoom;

    public SwingImageDisplay() {
        MouseAdapter mouseAdapter = new MouseAdapter();
        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
    }

    @Override
    public void paint(Paint... paints) {
        this.paints = paints;
        this.repaint();
    }

    @Override
    public void zoomPaint(ZoomPaint... paints) {
        this.zoomPaints=paints;
        this.repaint();
    }

    @Override
    public int width() {
        return this.getWidth();
    }

    @Override
    public void changeZoom(int i) {
        SwingImageDisplay.this.zoom.zoomLevel(this.zoomPaints[0].zoomLevel()*100 + i);
    }

    private final Map<Integer, BufferedImage> images = new HashMap<>();
    private BufferedImage toBufferedImage(byte[] bitmap) {
        return images.computeIfAbsent(Arrays.hashCode(bitmap), _ -> read(bitmap));
    }

    private BufferedImage read(byte[] bitmap) {
        try (InputStream is = new ByteArrayInputStream(bitmap)) {
            return ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0,0,this.getWidth(), this.getHeight());
        for (ZoomPaint paint : zoomPaints) {
            BufferedImage bitmap = toBufferedImage(paint.bitmap());
            Canvas canvas = Canvas.ofSize(this.getWidth(), this.getHeight())
                    .fit(bitmap.getWidth(), bitmap.getHeight());
            int x = (this.getWidth() - canvas.width()) / 2;
            int y = (this.getHeight() - canvas.height()) / 2;
            g.drawImage(bitmap, (int) (x+paint.offset()+canvas.width()*(1-paint.zoomLevel())/2), (int) (y+canvas.height()*(1-paint.zoomLevel())/2), (int) (canvas.width()*paint.zoomLevel()), (int) (canvas.height()*paint.zoomLevel()), null);
        }
    }


    private class MouseAdapter implements MouseListener, MouseMotionListener {
        private int x;

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            x =  e.getX();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            SwingImageDisplay.this.released.offset(e.getX() - x);
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {
            SwingImageDisplay.this.shift.offset(e.getX() - x);
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }

    @Override
    public void on(Shift shift) {
        this.shift = shift;
    }

    @Override
    public void on(Released released) {
        this.released = released;
    }

    public void on(Zoom zoom) {this.zoom=zoom;}

}






















