package controller;

import javax.swing.*;

/**
 * @Description: java
 * @author: Axu
 * @date:2022/5/15 10:44
 */
public class Weaponry {
        String name;
        Icon icon;


        public Weaponry(String name, Icon icon) {
                this.name = name;
                this.icon=icon;
        }

        public Icon getIcon() {
                return icon;
        }

        @Override
        public String toString() {
                return name;
        }
}
