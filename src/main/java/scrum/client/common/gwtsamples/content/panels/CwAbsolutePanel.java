/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package scrum.client.common.gwtsamples.content.panels;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import java.util.HashMap;
import java.util.Map;

import scrum.client.common.gwtsamples.ContentWidget;
import scrum.client.common.gwtsamples.Showcase;
import scrum.client.common.gwtsamples.ShowcaseAnnotations.ShowcaseData;
import scrum.client.common.gwtsamples.ShowcaseAnnotations.ShowcaseSource;

/**
 * Example file.
 */
public class CwAbsolutePanel extends ContentWidget {
  /**
   * The constants used in this Content Widget.
   */
  @ShowcaseSource
  public static interface CwConstants extends Constants,
      ContentWidget.CwConstants {
    String cwAbsolutePanelClickMe();

    String cwAbsolutePanelDescription();

    String cwAbsolutePanelHelloWorld();

    String cwAbsolutePanelItemsToMove();

    String cwAbsolutePanelLeft();

    String cwAbsolutePanelName();

    String cwAbsolutePanelTop();

    String[] cwAbsolutePanelWidgetNames();
  }

  /**
   * The absolute panel used in the example.
   */
  private AbsolutePanel absolutePanel = null;

  /**
   * An instance of the constants.
   */
  @ShowcaseData
  private CwConstants constants;

  /**
   * The input field used to set the left position of a {@link Widget}.
   */
  @ShowcaseData
  private TextBox leftPosBox = null;

  /**
   * The list box of items that can be repositioned.
   */
  @ShowcaseData
  private ListBox listBox = new ListBox();

  /**
   * The input field used to set the top position of a {@link Widget}.
   */
  @ShowcaseData
  private TextBox topPosBox = null;

  /**
   * A mapping between the name of a {@link Widget} and the widget in the
   * {@link AbsolutePanel}.
   */
  @ShowcaseData
  private Map<String, Widget> widgetMap = null;

  /**
   * Constructor.
   * 
   * @param constants the constants
   */
  public CwAbsolutePanel(CwConstants constants) {
    super(constants);
    this.constants = constants;
  }

  @Override
  public String getDescription() {
    return constants.cwAbsolutePanelDescription();
  }

  @Override
  public String getName() {
    return constants.cwAbsolutePanelName();
  }

  @Override
  public boolean hasStyle() {
    return false;
  }

  /**
   * Initialize this example.
   */
  @ShowcaseSource
  @Override
  public Widget onInitialize() {
    // Create a new panel
    widgetMap = new HashMap<String, Widget>();
    absolutePanel = new AbsolutePanel();
    absolutePanel.setSize("250px", "250px");
    absolutePanel.ensureDebugId("cwAbsolutePanel");

    // Add an HTML widget to the panel
    String[] widgetNames = constants.cwAbsolutePanelWidgetNames();
    HTML text = new HTML(constants.cwAbsolutePanelHelloWorld());
    absolutePanel.add(text, 10, 20);
    widgetMap.put(widgetNames[0], text);

    // Add a Button to the panel
    Button button = new Button(constants.cwAbsolutePanelClickMe());
    absolutePanel.add(button, 80, 45);
    widgetMap.put(widgetNames[1], button);

    // Add a Button to the panel
    Grid grid = new Grid(2, 3);
    grid.setBorderWidth(1);
    for (int i = 0; i < 3; i++) {
      grid.setHTML(0, i, i + "");
      grid.setWidget(1, i, Showcase.images.gwtLogoThumb().createImage());
    }
    absolutePanel.add(grid, 60, 100);
    widgetMap.put(widgetNames[2], grid);

    // Wrap the absolute panel in a DecoratorPanel
    DecoratorPanel absolutePanelWrapper = new DecoratorPanel();
    absolutePanelWrapper.setWidget(absolutePanel);

    // Create the options bar
    DecoratorPanel optionsWrapper = new DecoratorPanel();
    optionsWrapper.setWidget(createOptionsBar());

    // Add the components to a panel and return it
    HorizontalPanel mainLayout = new HorizontalPanel();
    mainLayout.setSpacing(10);
    mainLayout.add(optionsWrapper);
    mainLayout.add(absolutePanelWrapper);

    return mainLayout;
  }

  /**
   * Create an options panel that allows users to select a widget and reposition
   * it.
   * 
   * @return the new options panel
   */
  @ShowcaseSource
  private Widget createOptionsBar() {
    // Create a panel to move components around
    FlexTable optionsBar = new FlexTable();
    topPosBox = new TextBox();
    topPosBox.setWidth("3em");
    topPosBox.setText("100");
    leftPosBox = new TextBox();
    leftPosBox.setWidth("3em");
    leftPosBox.setText("60");
    listBox = new ListBox();
    optionsBar.setHTML(0, 0, constants.cwAbsolutePanelItemsToMove());
    optionsBar.setWidget(0, 1, listBox);
    optionsBar.setHTML(1, 0, constants.cwAbsolutePanelTop());
    optionsBar.setWidget(1, 1, topPosBox);
    optionsBar.setHTML(2, 0, constants.cwAbsolutePanelLeft());
    optionsBar.setWidget(2, 1, leftPosBox);

    // Add the widgets to the list box
    for (String name : widgetMap.keySet()) {
      listBox.addItem(name);
    }

    // Set the current item position when the user selects an item
    listBox.addChangeListener(new ChangeListener() {
      public void onChange(Widget sender) {
        updateSelectedItem();
      }
    });

    // Move the item as the user changes the value in the left and top boxes
    KeyboardListenerAdapter repositionListener = new KeyboardListenerAdapter() {
      @Override
      public void onKeyPress(Widget sender, char keyCode, int modifiers) {
        repositionItem();
      }
    };
    topPosBox.addKeyboardListener(repositionListener);
    leftPosBox.addKeyboardListener(repositionListener);

    // Return the options bar
    return optionsBar;
  }

  /**
   * Reposition an item when the user changes the value in the top or left
   * position text boxes.
   */
  @ShowcaseSource
  private void repositionItem() {
    // Use a DeferredCommand to allow the key to take effect in the browser
    DeferredCommand.addCommand(new Command() {
      public void execute() {
        // Get the selected item to move
        String name = listBox.getValue(listBox.getSelectedIndex());
        Widget item = widgetMap.get(name);

        // Reposition the item
        try {
          int top = Integer.parseInt(topPosBox.getText());
          int left = Integer.parseInt(leftPosBox.getText());
          absolutePanel.setWidgetPosition(item, left, top);
        } catch (NumberFormatException e) {
          // Ignore invalid user input
          return;
        }
      }
    });
  }

  /**
   * Update the top and left position text fields when the user selects a new
   * item to move.
   */
  @ShowcaseSource
  private void updateSelectedItem() {
    String name = listBox.getValue(listBox.getSelectedIndex());
    Widget item = widgetMap.get(name);
    topPosBox.setText((absolutePanel.getWidgetTop(item) - 1) + "");
    leftPosBox.setText((absolutePanel.getWidgetLeft(item) - 1) + "");
  }
}