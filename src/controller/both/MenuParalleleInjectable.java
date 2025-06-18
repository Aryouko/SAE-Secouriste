package controller.both;

/**
 * Interface for controllers that need to inject a MenuParalleleController instance.
 * This is used to allow different controllers to set their own MenuParalleleController.
 */
public interface MenuParalleleInjectable {
    void setMenuParalleleController(MenuParalleleController menuParalleleController);
}

