package com.Benchmarking;

public class Fib {
	static int Recursive(int n)
	{
		if (n <= 1)
			return 1;
		
		return Recursive(n - 1) + Recursive(n - 2);
	}
	
	static int Iterative(int n)
	{
		int v1 = 1;
		int v2 = 1;
		int v3 = 2;
		
		if (n <= 1)
			return 1;
		
		for (int i = 2; i < n; i++)
		{
			v3 = v2 + v1;
			v1 = v2;
			v2 = v3;
		}
		
		return v3;
	}
}
