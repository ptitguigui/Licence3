package fil.coo.view;

import fil.coo.controller.AbstractController;

import java.util.Optional;

public abstract class AbstractView {


    protected AbstractController controller;

    public AbstractView() {
    }

    /**
     * @param text what will be change on the on the ScrollPanel
     */
    public abstract void updateText(String text);

    /**
     * @param visible if this instance should be visible or not
     */
    public abstract void setVisible(boolean visible);

    public abstract String getText();

    public abstract void addPlugin(String label);

    public void setController(AbstractController controller) {
        this.controller = controller;
    }
}
