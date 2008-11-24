package scrum.client.common;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class PanelWidget extends Composite {

	private FlowPanel contentPanel;

	public PanelWidget(String title) {
		SimplePanel titlePanel = new SimplePanel();
		titlePanel.setStyleName(StyleSheet.ELEMENT_PANEL_WIDGET_TITLE);
		titlePanel.add(new Label(title));

		contentPanel = new FlowPanel();
		contentPanel.setStyleName(StyleSheet.ELEMENT_PANEL_WIDGET_CONTENT);

		FlowPanel panel = new FlowPanel();
		panel.setStyleName(StyleSheet.ELEMENT_PANEL_WIDGET);
		panel.add(titlePanel);
		panel.add(contentPanel);

		initWidget(panel);
	}

	public PanelWidget(String title, Widget content) {
		this(title);
		setContent(content);
	}

	public PanelWidget setContent(Widget content) {
		contentPanel.clear();
		contentPanel.add(content);
		return this;
	}

}