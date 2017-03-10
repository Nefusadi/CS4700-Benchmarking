package com.Benchmarking;

public class MergeSort {
	static int[] Sort(int[] array)
	{
		if (array.length <= 1)
			return array;
		
		int[] A = new int[array.length / 2];
		int[] B = new int[array.length - A.length];
		
		System.arraycopy(array, 0, A, 0, A.length);
		System.arraycopy(array, A.length, B, 0, B.length);
		
		A = Sort(A);
		B = Sort(B);
		
		return Merge(A, B);
	}
	
	private static int[] Merge(int[] A, int[] B)
	{
		int[] result = new int[A.length + B.length];
		
		int indexA = 0;
		int indexB = 0;
		int indexC = 0;
		while (indexA < A.length && indexB < B.length)
		{
			if (A[indexA] > B[indexB])
				result[indexC++] = B[indexB++];
			else
				result[indexC++] = A[indexA++];
		}
		
		while (indexA < A.length)
			result[indexC++] = A[indexA++];
		while (indexB < B.length)
			result[indexC++] = B[indexB++];
		
		return result;
	}
}
