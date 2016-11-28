import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class Main implements NativeKeyListener, NativeMouseInputListener
{
	boolean isHoldingSavingKey = false;
	ScriptCreator scriptCreator = new ScriptCreator();
	int x = 1, y = 1;
	
	public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        // check saving key
        if (e.getKeyCode() == Config.SAVE_OPTION_ENABLED_KEY)
        {
        	isHoldingSavingKey = true;
        }
        
        // exiting
        if (e.getKeyCode() == Config.EXIT_KEY) {
            try {
            	scriptCreator.create();
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException e1) {
				e1.printStackTrace();
			}
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        
     // check saving key
        if (e.getKeyCode() == Config.SAVE_OPTION_ENABLED_KEY)
        {
        	isHoldingSavingKey = false;
        }
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Key Typed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();
            
            LogManager.getLogManager().reset();
            
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }
        
        Main myListener = new Main();

        GlobalScreen.addNativeKeyListener(myListener);
        GlobalScreen.addNativeMouseListener(myListener);
        GlobalScreen.addNativeMouseMotionListener(myListener);
    }

	@Override
	public void nativeMouseClicked(NativeMouseEvent e) {
		System.out.println("Mouse Clicked: " + e.getClickCount());
		
		if (isHoldingSavingKey)
			scriptCreator.addMouseClickAction(x, y);
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent e) {
		System.out.println("Mouse Pressed: " + e.getButton());
		
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent e) {
		System.out.println("Mouse Released: " + e.getButton());
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent e) {
		System.out.println("Mouse Dragged: " + e.getX() + ", " + e.getY());
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent e) {
		System.out.println("Mouse Moved: " + e.getX() + ", " + e.getY());
		
		x = e.getX();
		y = e.getY();
	}
	
}
