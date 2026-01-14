package software.ulpgc.imageviewer.architecture;

import software.ulpgc.imageviewer.application.zoomGui.SwingImageDisplay;

public class MinusCommand implements Command {
    private final ImageDisplay imageDisplay;

    public MinusCommand(SwingImageDisplay imageDisplay) {
        this.imageDisplay = imageDisplay;
    }

    @Override
    public void execute() {
        imageDisplay.changeZoom(-10);
    }
}
