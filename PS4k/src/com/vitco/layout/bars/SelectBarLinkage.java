package com.vitco.layout.bars;

import com.jidesoft.action.CommandMenuBar;

import java.awt.*;

/**
 * the tool bar, uses menu generator to load content from file
 *
 * defines interactions
 */
public class SelectBarLinkage extends BarLinkagePrototype {

    @Override
    public final CommandMenuBar buildBar(String key, final Frame frame) {
        CommandMenuBar bar = new CommandMenuBar(key);

        // build the toolbar
        menuGenerator.buildMenuFromXML(bar, "com/vitco/layout/bars/select_bar.xml");

        // register the logic for this menu
        menuLogic.registerLogic(frame);

        return bar;
    }
}
