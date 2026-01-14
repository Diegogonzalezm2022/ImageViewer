package software.ulpgc.imageviewer.architecture;


public class PlusCommand implements Command {
    private final ImageDisplay imageDisplay;

    public PlusCommand(ImageDisplay imageDisplay) {
        this.imageDisplay = imageDisplay;
    }


    @Override
    public void execute() {
        imageDisplay.changeZoom(10);
    }
}
