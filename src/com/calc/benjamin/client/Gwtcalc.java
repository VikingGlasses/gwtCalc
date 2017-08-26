package com.calc.benjamin.client;

import com.calc.benjamin.client.composite.Calculator;
import com.calc.benjamin.client.exception.InvalidExpressionException;
import com.calc.benjamin.client.exception.MissingValueException;
import com.calc.benjamin.client.shitty.Evaluator;
import com.calc.benjamin.client.shitty.SimpleEvaluator;
import com.calc.benjamin.shared.CalcButtonEnum;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

public class Gwtcalc implements EntryPoint {

	private Calculator calc;
	private TextBox calcBox;
	private Label errorMessage;
	private static final Evaluator evaluator =  new SimpleEvaluator();

	/**
	 * Entry point method.
	 */
	public void onModuleLoad() {
		calc = new Calculator();

		FlowPanel keyPad = calc.getKeyPad();
		// TODO change layout
		CalcButtonEnum[] layoutOrder = { CalcButtonEnum.MODULUS, CalcButtonEnum.DIVITION, CalcButtonEnum.MULTIPLICATION,
				CalcButtonEnum.BACKSPACE, CalcButtonEnum.ONE, CalcButtonEnum.TWO, CalcButtonEnum.THREE,
				CalcButtonEnum.PLUS, CalcButtonEnum.FOUR, CalcButtonEnum.FIVE, CalcButtonEnum.SIX, CalcButtonEnum.MINUS,
				CalcButtonEnum.SEVEN, CalcButtonEnum.EIGHT, CalcButtonEnum.NINE, CalcButtonEnum.EMPTY,
				CalcButtonEnum.EMPTY, CalcButtonEnum.ZERO, CalcButtonEnum.POINT, CalcButtonEnum.ENTER };
		for (CalcButtonEnum btnEnum : layoutOrder) {
			Button btn = new Button();
			btn.setText(btnEnum.getSymbol());
			btn.setStylePrimaryName("calcButton");
			btn.addStyleName(btnEnum.getStyle());
			btn.addClickHandler(event -> handle(btnEnum));
			keyPad.add(btn);
		}
		// TODO change UI split calcBox to 3 labels
		calcBox = calc.getCalcBox();
		calcBox.setEnabled(false);
		calcBox.addKeyDownHandler(event -> {
			// switch case keyCode to filter out bad keys.
			// if bad key remove from textBox
			// else send enum to handle?
		});
		calcBox.addKeyUpHandler(event -> {
			String calcText = calcBox.getText();
			calcText = calcText.substring(0, calcText.length() - 1);
			calcBox.setText(calcText);
		});
		RootPanel.get("calc").add(calc);
		errorMessage = new Label();
		errorMessage.setVisible(false);
		RootPanel.get("calc").add(errorMessage);
		calcBox.setFocus(true);
	}

	private void handle(CalcButtonEnum btnEnum) {
		final TextBox activeBox = calcBox;
		final String currentText = activeBox.getText();
		activeBox.setFocus(true);
		
		switch(btnEnum) {
		case EMPTY:
			break;
		case ENTER:
			// add new line to history
			calc.addHistoryLine(currentText, calc.getResult());
			activeBox.setText("");
			calc.setResult("");
			break;
		case BACKSPACE:
			activeBox.setText(removeLastChar(currentText));
			break;
		case MODULUS:
		case DIVITION:
		case MULTIPLICATION:
		case PLUS:
		case MINUS:
			if (currentText.isEmpty()) {
				return;
			}
			String newText = currentText;
			// if last was also an operation replace with new operation
			// else if operation already done, start new calculation on result
			if (lastIsOperation(currentText)) {
				newText = removeLastChar(currentText);
			} else if (containsOperation(currentText)) {
//				newText = calc.getResult();
//				handle(CalcButtonEnum.ENTER);
				return;
			}
			// add operation to string
			newText += btnEnum.getSymbol();
			activeBox.setText(newText);
			break;
		default:
			activeBox.setText(currentText + btnEnum.getSymbol());
			break;
		}
		updateResult();
	}

	private void updateResult() {
		if (calcBox.getText().isEmpty()) {
			return;
		}
		try {
			
			double result = evaluator.eval(calcBox.getText());
			String resultText = NumberFormat.getFormat("#.####").format(result);
			calc.setResult(resultText);
			errorMessage.setVisible(false);
		} catch (Throwable e) {
			String details = e.getMessage();
			calc.setResult("ERR");
			if (e instanceof InvalidExpressionException) {
				details += ((InvalidExpressionException)e).getReason();
			}
			errorMessage.setText(details);
			errorMessage.setVisible(true);
			if (e instanceof MissingValueException) {
				calc.setResult("");
				errorMessage.setVisible(false);
			}
		}
	}

	private boolean containsOperation(String text) {
		if (text.length() == 0) {
			return false;
		}
		for (CalcButtonEnum o : CalcButtonEnum.getOperations()) {
			if (text.contains(o.getSymbol())) {
				return true;
			}
		}
		return false;
	}

	private boolean lastIsOperation(String text) {
		if (text.length() == 0) {
			return false;
		}
		for (CalcButtonEnum o : CalcButtonEnum.getOperations()) {
			if (text.endsWith(o.getSymbol())) {
				return true;
			}
		}
		return false;
	}

	private String removeLastChar(String text) {
		return text.substring(0, text.length() - 1);
	}

}