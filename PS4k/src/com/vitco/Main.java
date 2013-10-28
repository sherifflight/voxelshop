package com.vitco;

import com.vitco.logic.shortcut.ShortcutManager;
import com.vitco.util.action.ActionManager;
import com.vitco.util.action.ComplexActionManager;
import com.vitco.util.error.ErrorHandler;
import com.vitco.util.pref.Preferences;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Initially executed class
 */

public class Main {

    private static boolean debug = false;

    // true if the program runs in debug mode
    public static boolean isDebugMode() {
        return debug;
    }

    public static void main(String[] args) throws Exception {
        // the JIDE license
        com.jidesoft.utils.Lm.verifyLicense("Pixelated Games", "PS4K", "__JIDE_PASSWORD__");

        // check if we are in debug mode
        if ((args.length > 0) && args[0].equals("debug")) {
            ErrorHandler.setDebugMode();
            debug = true;
        }

        // build the application
        final ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("com/vitco/glue/config.xml");

        // for debugging
        if (debug) {
            ((ActionManager) context.getBean("ActionManager")).performValidityCheck();
            ((ComplexActionManager) context.getBean("ComplexActionManager")).performValidityCheck();
        }
        // perform shortcut check
        ((ShortcutManager) context.getBean("ShortcutManager")).doSanityCheck(debug);
//        // test console
//        final Console console = ((Console) context.getBean("Console"));
//        new Thread() {
//            public void run() {
//                while (true) {
//                    console.addLine("text");
//                    try {
//                        sleep(2000);
//                    } catch (InterruptedException e) {
//                       //e.printStackTrace();
//                    }
//                }
//            }
//        }.start();

        // add a shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                // make reference so Preferences object doesn't get destroyed
                Preferences pref = ((Preferences) context.getBean("Preferences"));
                // trigger @PreDestroy
                context.close();
                // store the preferences (this needs to be done here, b/c
                // some PreDestroys are used to store preferences!)
                pref.save();
            }
        });
    }
}
