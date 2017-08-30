package com.calc.benjamin.client.composite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class CalculatorUi extends Composite {

	private static CalculatorUiBinder uiBinder = GWT.create(CalculatorUiBinder.class);

	interface CalculatorUiBinder extends UiBinder<Widget, CalculatorUi> {
	}

	public CalculatorUi() {
		initWidget(uiBinder.createAndBindUi(this));
		history.getColumnFormatter().addStyleName(0, "calcBox");
		history.getColumnFormatter().addStyleName(1, "resultLabel");
	}

	@UiField
	protected FlexTable history;
	@UiField
	protected HorizontalPanel display;
	@UiField
	protected FlowPanel keyPad;
	@UiField
	protected TextBox calcBox;
	@UiField
	protected Label resultLabel;
	
	public void setResult(String result) {
		resultLabel.setText(result);
	}
	
	public String getResult() {
		return resultLabel.getText();
	}

	public void addHistoryLine(String text, String result) {
		int rowCount = history.getRowCount();
		history.setText(rowCount, 0, text);
		history.setText(rowCount, 1, result);
	}

	public TextBox getCalcBox() {
		return calcBox;
	}

	public FlowPanel getKeyPad() {
		return keyPad;
	}

}
