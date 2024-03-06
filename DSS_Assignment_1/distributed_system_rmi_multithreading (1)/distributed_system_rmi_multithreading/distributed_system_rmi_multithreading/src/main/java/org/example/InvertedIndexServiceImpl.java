package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InvertedIndexServiceImpl extends UnicastRemoteObject implements InvertedIndexService {
	private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();

	public InvertedIndexServiceImpl() throws RemoteException {
		super();
	}

	@Override
	public Map<String, List<Integer>> getInvertedIndex(String fileName) throws RemoteException {
		Map<String, List<Integer>> index = new HashMap<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
			List<Callable<Void>> tasks = new ArrayList<>();
			String line;
			int lineNumber = 1;
			while ((line = reader.readLine()) != null) {
				tasks.add(new InvertedIndexTask(line, index, lineNumber++));
			}
			executor.invokeAll(tasks); // Invoke all tasks
			executor.shutdown(); // Shutdown executor after tasks are done
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return index;
	}

	private static class InvertedIndexTask implements Callable<Void> {
		private final String line;
		private final Map<String, List<Integer>> index;
		private final int lineNumber;

		public InvertedIndexTask(String line, Map<String, List<Integer>> index, int lineNumber) {
			this.line = line;
			this.index = index;
			this.lineNumber = lineNumber;
		}

		@Override
		public Void call() {
			String[] words = line.split("\\s+");
			for (String word : words) {
				synchronized (index) {
					if (!index.containsKey(word)) {
						index.put(word, new ArrayList<>());
					}
					index.get(word).add(lineNumber);
				}
			}
			return null;
		}
	}

	public static void main(String[] args) {
		try {
			InvertedIndexServiceImpl server = new InvertedIndexServiceImpl();
			LocateRegistry.createRegistry(8099);
			Naming.rebind("rmi://127.0.0.1:8099/InvertedIndexService", server);
			System.out.println("InvertedIndexService Ready...");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
