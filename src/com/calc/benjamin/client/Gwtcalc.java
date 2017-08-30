package com.calc.benjamin.client;

import java.util.Queue;

import com.calc.benjamin.client.calculator.CalculatorToken;
import com.calc.benjamin.client.calculator.Evaluator;
import com.calc.benjamin.client.calculator.Parser;
import com.calc.benjamin.client.calculator.ReversePolishNotationEvaluator;
import com.calc.benjamin.client.calculator.ShuntingYardParser;
import com.calc.benjamin.client.calculator.exception.EvaluationException;
import com.calc.benjamin.client.calculator.exception.ParseException;
import com.calc.benjamin.client.composite.CalculatorUi;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
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

	private CalculatorUi calc;
	private TextBox calcBox;
	private Label errorMessage;
	private static final Parser<String[], Queue<CalculatorToken>> infixParser = new ShuntingYardParser();
	private static final Evaluator<Queue<CalculatorToken>, Double> evaluator =  new ReversePolishNotationEvaluator();

	/**
	 * Entry point method.
	 */
	public void onModuleLoad() {
		calc = new CalculatorUi();

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
		}
		calcBox = calc.getCalcBox();
		// TODO enable typing with filtering
		calcBox.setReadOnly(true);
		KeyPressHandler h =  event -> {
			char keyPressed = event.getCharCode();
			GWT.log("KeyPressed: '" + keyPressed + "'");
			};
		calcBox.addDomHandler(h, KeyPressEvent.getType());
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
		int cursorPos = activeBox.getCursorPos();
		
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
			activeBox.setText(removeCharAt(currentText, cursorPos));
			if (cursorPos > 0) {
				activeBox.setCursorPos(cursorPos - 1);
			} else {
				activeBox.setCursorPos(cursorPos);
			}
			break;
		case LEFT_ARROW:
			if (cursorPos > 0) {
				activeBox.setCursorPos(cursorPos - 1);
			}
			break;
		case RIGHT_ARROW:
			if (cursorPos < activeBox.getText().length()) {
				activeBox.setCursorPos(cursorPos + 1);
			}
			break;
		case LEFT_PARENTHESIS:
			String parenthesis = btnEnum.getSymbol();
			if (cursorPos == activeBox.getText().length()) {
				parenthesis += ")";
			}
			String newText = currentText.substring(0, cursorPos) + parenthesis + currentText.substring(cursorPos);
			activeBox.setText(newText);
			activeBox.setCursorPos(cursorPos + 1);
			break;
		default:
			newText = currentText.substring(0, cursorPos) + btnEnum.getSymbol() + currentText.substring(cursorPos);
			activeBox.setText(newText);
			activeBox.setCursorPos(cursorPos + 1);
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
//			GWT.log("Postfix:  + "rPn.toString());
			Double result = evaluator.eval(rPn);
			String resultText = NumberFormat.getFormat("#.####").format(result.doubleValue());
			calc.setResult(resultText);
			errorMessage.setVisible(false);
		} catch (EvaluationException | ParseException e) {
			String details = e.getMessage();
			errorMessage.setText(details);
			errorMessage.setVisible(true);
		}
	}

	private String removeCharAt(String text, int index) {
		String result = "";
		if (index == 0) {
			result = text;
		} else {
			result = text.substring(0, index - 1) + text.substring(index);
		}
		return result;
	}

}