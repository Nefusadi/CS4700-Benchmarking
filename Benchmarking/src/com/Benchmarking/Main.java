package com.Benchmarking;

import java.io.*;
import java.util.Random;
import com.Benchmarking.Dijkstra;
import com.Benchmarking.Fib;
import com.Benchmarking.MergeSort;

public class Main {

	public static void main(String[] args) {
		
		int[] array;
		int[][] graph;
		String graphPath = "";
		String arrayPath = "";
		
		boolean writeGraph = false;
		boolean writeArray = false;
		
		for (int i = 0; i < args.length; i++)
		{
			switch (args[i])
			{
			case "-wg":
				writeGraph = true;
				break;
			case "-g":
				graphPath = args[++i];
				break;
			case "-wa":
				writeArray = true;
				break;
			case "-a":
				arrayPath = args[++i];
				break;
			}
		}
		
		long start = System.nanoTime();
		int f = Fib.Recursive(20);
		System.out.println("Fib Recursive took: " + (System.nanoTime() - start) / 1000.0 + " microseconds.");
		
		start = System.nanoTime();
		f =Fib.Iterative(20);
		System.out.println("Fib Iterative took: " + (System.nanoTime() - start) / 1000.0 + " microseconds.");
		
		System.out.print("Reading sort input...");
		start = System.nanoTime();
		
		// Read in file
		if (arrayPath != "")
		{
			if (writeArray)
			{
				WriteArray(GenerateArray(1000), arrayPath);
			}
			array = ReadArray(arrayPath);
		}
		else
			array = GenerateArray(1000);
		
		System.out.println("\nTook " + (System.nanoTime() - start) / 1000.0 + " microseconds.");
		
		start = System.nanoTime();
		array = MergeSort.Sort(array);
		System.out.println("MergeSort took: " + (System.nanoTime() - start) / 1000.0 + " microseconds.");

		System.out.print("Reading graph input...");
		start = System.nanoTime();
		
		// Read in file
		if (graphPath != "")
		{
			if (writeGraph)
				WriteGraph(GenerateGraph(100), graphPath);
			graph = ReadGraph(graphPath);
		}
		else
			graph = GenerateGraph(100);
		
		System.out.println("\nTook " + (System.nanoTime() - start) / 1000.0 + " microseconds.");
		
		start = System.nanoTime();
		int[] paths = Dijkstra.Traverse(graph, 0);
		System.out.println("Dijkstra took: " + (System.nanoTime() - start) / 1000.0 + " microseconds.");
	}
	
	static int[] GenerateArray(int size)
	{
		int[] result = new int[size];
		
		Random rand = new Random();
		for (int i = 0; i < size; i++)
		{
			result[i] = rand.nextInt(10000);
		}
		
		return result;
	}
	
	static int[][] GenerateGraph(int nodes)
	{
		int[][] graph = new int[nodes][nodes];
		Random rand = new Random();
		
		for (int i = 0; i < nodes; i++)
		{
			int edges = rand.nextInt(nodes);
			for (int j = 0; j < edges; j++)
			{
				int node2 = rand.nextInt(nodes);
				graph[i][node2] = 1;
				graph[node2][i] = 1;
			}
		}
		
		return graph;
	}
	
	static void WriteGraph(int[][] graph, String path)
	{
		FileWriter writer = null;
		try
		{
			writer = new FileWriter(path);
			BufferedWriter buff = new BufferedWriter(writer);
			
			for (int i = 0; i < graph.length; i++)
			{
				for (int j = 0; j < graph[i].length; j++)
				{
					buff.write(Integer.toString(graph[i][j]) + " ");
				}
				buff.newLine();
			}
			buff.close();
		}
		catch (Exception e)
		{
		}
	}
	
	static void WriteArray(int[] array, String path)
	{
		FileWriter writer = null;
		try
		{
			writer = new FileWriter(path);
			BufferedWriter buff = new BufferedWriter(writer);
			for (int i = 0; i < array.length; i++)
			{
				buff.write(Integer.toString(array[i]));
				buff.newLine();
			}
			
			buff.close();
		}
		catch (Exception e)
		{ }
	}
	
	static int[][] ReadGraph(String path)
	{
		try
		{
			FileReader reader = new FileReader(path);

			BufferedReader buff = new BufferedReader(reader);
			String line = buff.readLine();
			if (line == null)
			{
				buff.close();
				return null;
			}
			String[] parts = line.split(" ");
			int lineNum = 0;
			
			int[][] graph = new int[parts.length][parts.length];
			
			while (line != null)
			{
				parts = line.split(" ");
				for (int i = 0; i < parts.length; i++)
					graph[lineNum][i] = Integer.parseInt(parts[i]);
				
				lineNum++;
				line = buff.readLine();
			}
			
			buff.close();
			
			return graph;
		}
		catch (Exception e)
		{ }
		return null;
	}
	
	static int[] ReadArray(String path)
	{
		try
		{
			FileReader reader = new FileReader(path);

			BufferedReader buff = new BufferedReader(reader);
			String line = null;
			int lineNum = 0;
			
			int[] array = new int[10000];
			
			while ((line = buff.readLine()) != null && lineNum < 10000)
			{
				array[lineNum++] = Integer.parseInt(line);
			}
			
			buff.close();
			
			return array;
		}
		catch (Exception e)
		{ }
		return null;
	}

}
