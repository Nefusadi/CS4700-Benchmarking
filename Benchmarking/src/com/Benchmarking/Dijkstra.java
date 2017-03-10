package com.Benchmarking;

import java.util.*;

public class Dijkstra {
	static int[] Traverse(int[][] graph, int source)
	{
		int[] dist = new int[graph.length];
		int[] prev = new int[graph.length];
		
		List<Integer> queue = new ArrayList<Integer>();
		
		for (int i = 0; i < dist.length; i++)
		{
			if (i == source)
				continue;
			
			dist[i] = Integer.MAX_VALUE;
			prev[i] = -1;
			queue.add(i);
		}
		
		dist[source] = 0;
		prev[source] = -1;
		
		queue.add(0, source);
		
		while (!queue.isEmpty())
		{
			int current = queue.get(0);
			queue.remove(0);
			
			for (int i = 0; i < graph[current].length; i++)
			{
				if (!queue.contains(i) || graph[current][i] <= 0)
					continue;
				
				int alt = dist[current] + graph[current][i];
				if (alt < dist[i] && alt > 0)
				{
					dist[i] = alt;
					prev[i] = current;
				}
			}
			
			Collections.sort(queue, new Comparator<Integer>(){
				@Override
				public int compare(Integer A, Integer B)
				{
					if (dist[A] < dist[B])
						return -1;
					else if (dist[A] == dist[B])
						return 0;
					else
						return 1;
				}
			});
		}
		
		return prev;
	}
}
