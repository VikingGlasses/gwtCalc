package com.calc.benjamin.client;

public interface Parser<T1, T2> {
	
	T2 parse(T1 expression) throws Exception;

}
