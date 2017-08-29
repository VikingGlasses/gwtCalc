package com.calc.benjamin.client;

public interface Evaluator<T1, T2> {
	
	T2 eval(T1 expression) throws Exception;

}
