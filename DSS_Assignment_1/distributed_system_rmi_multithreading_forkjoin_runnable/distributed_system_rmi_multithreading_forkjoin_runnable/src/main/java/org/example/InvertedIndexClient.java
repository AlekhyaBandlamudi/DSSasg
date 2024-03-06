package org.example;


import java.rmi.Naming;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class InvertedIndexClient {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java InvertedIndexClient <file_path>");
			System.exit(1);
		}

		String filePath = args[0];

		try {
			String endpoint = "rmi://127.0.0.1:8099/InvertedIndexService";
			InvertedIndexService service = (InvertedIndexService) Naming.lookup(endpoint);

			Map<String, List<Integer>> invertedIndex = service.getInvertedIndex(filePath);

			System.out.println("Top-5 tokens with the most frequent appearance and their locations:");
			int count = 0;
			for (Entry<String, List<Integer>> entry : invertedIndex.entrySet()) {
				if (count >= 5) {
					break;
				}
				String token = entry.getKey();
				List<Integer> locations = entry.getValue();
				System.out.print(token + ": ");
				for (Integer location : locations) {
					System.out.print(location + " ");
				}
				System.out.println();
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
