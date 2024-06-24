package tech.kdgaming1.easyconfigs.gui.guiutils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

public class ECDropdownMenu extends Gui {
    private final Minecraft mc;
    private int x, y, width, height;
    private final List<String> options;
    private boolean isExpanded;
    private int selectedIndex;
    private int scrollOffset;
    private static final int OPTION_HEIGHT = 20;
    private static final int MAX_VISIBLE_OPTIONS = 5;

    public ECDropdownMenu(Minecraft mc, int x, int y, int width, int height, List<String> options) {
        this.mc = mc;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.options = new ArrayList<>(options);
        this.isExpanded = false;
        this.selectedIndex = -1;
        this.scrollOffset = 0;
    }

    public void drawDropdown(int mouseX, int mouseY) {
        drawRect(x, y, x + width, y + height, 0xFFAAAAAA);
        if (selectedIndex >= 0 && selectedIndex < options.size()) {
            String selectedOption = options.get(selectedIndex);
            mc.fontRendererObj.drawString(selectedOption, x + 5, y + 5, 0xFFFFFF);
        } else {
            mc.fontRendererObj.drawString("Select an option", x + 5, y + 5, 0xFFFFFF);
        }

        if (isExpanded) {
            drawRect(x, y + height, x + width, y + height + MAX_VISIBLE_OPTIONS * OPTION_HEIGHT, 0xFF000000);
            int end = Math.min(scrollOffset + MAX_VISIBLE_OPTIONS, options.size());
            for (int i = scrollOffset; i < end; i++) {
                int optionY = y + height + (i - scrollOffset) * OPTION_HEIGHT;
                if (mouseX >= x && mouseX <= x + width && mouseY >= optionY && mouseY <= optionY + OPTION_HEIGHT) {
                    drawRect(x, optionY, x + width, optionY + OPTION_HEIGHT, 0xFF555555);
                }
                mc.fontRendererObj.drawString(options.get(i), x + 5, optionY + 5, 0xFFFFFF);
            }
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            isExpanded = !isExpanded;
        } else if (isExpanded) {
            int end = Math.min(scrollOffset + MAX_VISIBLE_OPTIONS, options.size());
            for (int i = scrollOffset; i < end; i++) {
                int optionY = y + height + (i - scrollOffset) * OPTION_HEIGHT;
                if (mouseX >= x && mouseX <= x + width && mouseY >= optionY && mouseY <= optionY + OPTION_HEIGHT) {
                    selectedIndex = i;
                    isExpanded = false;
                    break;
                }
            }
        } else {
            isExpanded = false;
        }
    }

    public void handleMouseInput() {
        if (isExpanded) {
            int dWheel = Mouse.getEventDWheel();
            if (dWheel != 0) {
                if (dWheel > 0) {
                    scrollOffset = Math.max(0, scrollOffset - 1);
                } else if (dWheel < 0) {
                    scrollOffset = Math.min(options.size() - MAX_VISIBLE_OPTIONS, scrollOffset + 1);
                }
            }
        }
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public String getSelectedOption() {
        return selectedIndex >= 0 && selectedIndex < options.size() ? options.get(selectedIndex) : null;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
