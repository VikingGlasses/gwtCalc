package com.calc.benjamin.client;

import java.util.Queue;

import com.calc.benjamin.client.composite.Calculator;
import com.calc.benjamin.client.exception.InvalidExpressionException;
import com.calc.benjamin.client.exception.MissingValueException;
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
	private Button enterButton;
	private static final Parser<String[], Queue<CalculatorToken>> infixParser = new ShuntingYardParser();
	private static final Evaluator<Queue<CalculatorToken>, Double> evaluator =  new ReversePolishNotationEvaluator();

	/**
	 * Entry point method.
	 */
	public void onModuleLoad() {
		calc = new Calculator();

		FlowPanel keyPad = calc.getKeyPad();
		// TODO change layout
		CalcButtonEnum[] layoutOrder = { 
				CalcButtonEnum.EMPTY, CalcButtonEnum.LEFT_ARROW, CalcButtonEnum.RIGHT_ARROW, CalcButtonEnum.BACKSPACE,
				CalcButtonEnum.MODULUS, CalcButtonEnum.LEFT_PARENTHESIS, CalcButtonEnum.RIGHT_PARENTHESIS ,CalcButtonEnum.DIVISION,  
				CalcButtonEnum.ONE, CalcButtonEnum.TWO, CalcButtonEnum.THREE, CalcButtonEnum.MULTIPLICATION, 
				CalcButtonEnum.FOUR, CalcButtonEnum.FIVE, CalcButtonEnum.SIX, CalcButtonEnum.MINUS,
				CalcButtonEnum.SEVEN, CalcButtonEnum.EIGHT, CalcButtonEnum.NINE, CalcButtonEnum.PLUS,
				CalcButtonEnum.EMPTY, CalcButtonEnum.ZERO, CalcButtonEnum.PERIOD, CalcButtonEnum.ENTER };
		for (CalcButtonEnum btnEnum : layoutOrder) {
			Button btn = new Button();
			btn.setText(btnEnum.getSymbol());
			if (btnEnum == CalcButtonEnum.EMPTY) {
				btn.setStylePrimaryName("empty");
			} else {
				btn.setStylePrimaryName("calcButton");
			}
			btn.addStyleName(btnEnum.getStyle());
			btn.addClickHandler(event -> handle(btnEnum));
			keyPad.add(btn);
			if (btnEnum == CalcButtonEnum.ENTER) {
				enterButton = btn;
			}
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
		enterButton.setFocus(true);
	}

	private void handle(CalcButtonEnum btnEnum) {
		final TextBox activeBox = calcBox;
		final String currentText = activeBox.getText();
		enterButton.setFocus(true);
		
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
		case LEFT_ARROW:
			break;
		case RIGHT_ARROW:
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
			String[] splitInfix = calcBox.getText().split("(?=[+\\-/*%\\(\\)])");
			Queue<CalculatorToken> rPn = infixParser.parse(splitInfix);
			GWT.log(rPn.toString());
			double result = evaluator.eval(rPn);
			String resultText = NumberFormat.getFormat("#.####").format(result);
			calc.setResult(resultText);
			errorMessage.setVisible(false);
		} catch (Throwable e) {
			String details = e.getMessage();
			if (e instanceof InvalidExpressionException) {
				details += "\n" + ((InvalidExpressionException)e).getReason();
			}
			errorMessage.setText(details);
			errorMessage.setVisible(true);
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